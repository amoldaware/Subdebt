package com.cgtsi.action;

import java.io.BufferedWriter;
import java.io.ByteArrayOutputStream;

import com.itextpdf.text.DocumentException;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.OutputStream;
import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Types;
import java.text.DateFormat;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Vector;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.commons.beanutils.BeanUtils;
import org.apache.commons.validator.GenericValidator;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.action.DynaActionForm;
import org.apache.struts.validator.DynaValidatorActionForm;

import com.cgtsi.actionform.ApplicationStatusActionForm;
import com.cgtsi.actionform.AppropriationActionForm;
import com.cgtsi.actionform.ClaimActionForm;
import com.cgtsi.actionform.ReportActionForm;
import com.cgtsi.admin.User;
import com.cgtsi.claim.ClaimApplication;
import com.cgtsi.claim.ClaimConstants;
import com.cgtsi.claim.ClaimDetail;
import com.cgtsi.claim.ClaimSummaryDtls;
import com.cgtsi.claim.ClaimsProcessor;
import com.cgtsi.claim.UploadFileProperties;
import com.cgtsi.common.Constants;
import com.cgtsi.common.DatabaseException;
import com.cgtsi.common.Log;
import com.cgtsi.common.MessageException;
import com.cgtsi.common.NoDataException;
import com.cgtsi.guaranteemaintenance.CgpanInfo;
import com.cgtsi.guaranteemaintenance.Disbursement;
import com.cgtsi.guaranteemaintenance.DisbursementAmount;
import com.cgtsi.guaranteemaintenance.GMProcessor;
import com.cgtsi.guaranteemaintenance.NPADetails;
import com.cgtsi.guaranteemaintenance.OutstandingAmount;
import com.cgtsi.guaranteemaintenance.OutstandingDetail;
import com.cgtsi.guaranteemaintenance.PeriodicInfo;
import com.cgtsi.guaranteemaintenance.Repayment;
import com.cgtsi.guaranteemaintenance.RepaymentAmount;
import com.cgtsi.registration.MLIInfo;
import com.cgtsi.registration.NoMemberFoundException;
import com.cgtsi.registration.Registration;
import com.cgtsi.reports.ApplicationReport;
import com.cgtsi.reports.DefaulterInputFields;
import com.cgtsi.reports.GeneralReport;
import com.cgtsi.reports.QueryBuilderFields;
import com.cgtsi.reports.ReportDAO;
import com.cgtsi.reports.ReportManager;
import com.cgtsi.util.CustomisedDate;
import com.cgtsi.util.DBConnection;
import com.cgtsi.util.DateHelper;
import com.cgtsi.util.PropertyLoader;

public class ReportsAction extends BaseAction {
	private ReportDAO reportDao;
	private ReportManager reportManager;

	private void $init$() {
		this.reportDao = null;
		this.reportManager = null;
	}

	public ReportsAction() {
		$init$();

		this.reportDao = new ReportDAO();
		this.reportManager = new ReportManager();
	}

	public ActionForward otsReportInput(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		Log.log(4, "ReportsAction", "otsReportInput", "Entered");

		DynaActionForm dynaForm = (DynaActionForm) form;
		java.util.Date date = new java.util.Date();
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(date);
		int month = calendar.get(2);
		int day = calendar.get(5);
		month -= 1;
		day += 1;
		calendar.set(2, month);
		calendar.set(5, day);
		java.util.Date prevDate = calendar.getTime();
		GeneralReport generalReport = new GeneralReport();
		generalReport.setDateOfTheDocument32(prevDate);
		generalReport.setDateOfTheDocument33(date);
		BeanUtils.copyProperties(dynaForm, generalReport);
		Log.log(4, "ReportsAction", "otsReportInput", "Exited");

		return mapping.findForward("success");
	}

	public ActionForward otsReport(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		Log.log(4, "ReportsAction", "otsReport", "Entered");
		ArrayList dan = new ArrayList();
		DynaActionForm dynaForm = (DynaActionForm) form;
		java.sql.Date startDate = null;
		java.sql.Date endDate = null;
		java.util.Date sDate = (java.util.Date) dynaForm
				.get("dateOfTheDocument32");
		String stDate = String.valueOf(sDate);

		if ((stDate == null) || (stDate.equals("")))
			startDate = null;
		else if (stDate != null) {
			startDate = new java.sql.Date(sDate.getTime());
		}
		java.util.Date eDate = (java.util.Date) dynaForm
				.get("dateOfTheDocument33");
		endDate = new java.sql.Date(eDate.getTime());
		dan = this.reportManager.otsReport(startDate, endDate);
		dynaForm.set("danRaised", dan);

		if ((dan == null) || (dan.size() == 0)) {
			throw new NoDataException(
					"No Data is available for the values entered, Please Enter Any Other Value ");
		}

		dan = null;
		Log.log(4, "ReportsAction", "otsReport", "Exited");

		return mapping.findForward("success");
	}

	// Diksha
	public ActionForward showApplicationStatus(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		// System.out.println("showApplicationStatus");
		 ReportActionForm reportForm = (ReportActionForm )form;
		//ReportActionForm actionFormobj = (ReportActionForm) form;
		ArrayList<ReportActionForm> reportArray = new ArrayList<ReportActionForm>();
		String appRefNO = request.getParameter("appRefNo");
		 System.out.println("mliID :"+appRefNO);


		PreparedStatement stmt = null;
		ResultSet rs1 = null;
		Connection conn = null;
		String type = request.getParameter("stream");
		try {
			String appDetails = null;
			conn = DBConnection.getConnection();
			
			if(type.equalsIgnoreCase("ApplicationReferenceNo")){
				
			 appDetails = "select APP_REF_NO,a.cgpan,APP_APPROVED_AMOUNT,D.DAN_ID, Round(D.DCI_GFEE_AMT,0) DCI_GFEE_AMT,APP_STATUS,SSI_IT_PAN,APP_REMARKS,D.Payment_ID,IFNULL(DATE_FORMAT(D.PMT_APPROPRIATION_Date, '%d/%m/%Y'),'')PMT_APPROPRIATION_Date,SSI_UNIT_NAME,D.DAN_STATUS,IFNULL(DATE_FORMAT(D.PMT_BANKCLERING_Date, '%d/%m/%Y'),'')PMT_BANKCLERING_Date,D.Modified_By from SUBDEBT_APPLICATION_DETAIL A,DAN_CGPAN_INFO D where A.CGPAN=D.CGPAN AND APP_REF_NO='"
						+ appRefNO + "'";
			}
			if(type.equalsIgnoreCase("Cgpan")){
				/*appDetails = "select APP_REF_NO,a.cgpan,APP_APPROVED_AMOUNT,D.DAN_ID, Round(D.DCI_GFEE_AMT,0) DCI_GFEE_AMT,APP_STATUS,SSI_IT_PAN,APP_REMARKS,D.Payment_ID,IFNULL(DATE_FORMAT(D.PMT_APPROPRIATION_Date, '%d/%m/%Y'),'')PMT_APPROPRIATION_Date,SSI_UNIT_NAME,D.DAN_STATUS,IFNULL(DATE_FORMAT(D.PMT_BANKCLERING_Date, '%d/%m/%Y'),'')PMT_BANKCLERING_Date,D.Modified_By from SUBDEBT_APPLICATION_DETAIL A,DAN_CGPAN_INFO D where A.CGPAN=D.CGPAN AND A.CGPAN='"
						+ appRefNO + "'";*/
				appDetails = "select APP_REF_NO,A.cgpan,APP_APPROVED_AMOUNT,D.DAN_ID, Round(D.DCI_GFEE_AMT,0) DCI_GFEE_AMT,APP_STATUS,"
						+ "SSI_IT_PAN,(CASE WHEN D.DAN_ID like '%GF%' THEN APP_REMARKS ELSE REMARKS END) AS APP_REMARKS,D.Payment_ID,IFNULL(DATE_FORMAT(D.PMT_APPROPRIATION_Date, '%d/%m/%Y'),'')PMT_APPROPRIATION_Date,"
						+ "SSI_UNIT_NAME,D.DAN_STATUS,IFNULL(DATE_FORMAT(D.PMT_BANKCLERING_Date, '%d/%m/%Y'),'')PMT_BANKCLERING_Date,"
						+ "D.Modified_By from subdebt_application_detail A,dan_cgpan_info D where A.CGPAN=D.CGPAN AND A.CGPAN='"
						+ appRefNO + "'";
			}
			if(type.equalsIgnoreCase("PaymentId")){
				appDetails = "select APP_REF_NO,a.cgpan,APP_APPROVED_AMOUNT,D.DAN_ID, Round(D.DCI_GFEE_AMT,0) DCI_GFEE_AMT,APP_STATUS,SSI_IT_PAN,APP_REMARKS,D.Payment_ID,IFNULL(DATE_FORMAT(D.PMT_APPROPRIATION_Date, '%d/%m/%Y'),'')PMT_APPROPRIATION_Date,SSI_UNIT_NAME,D.DAN_STATUS,IFNULL(DATE_FORMAT(D.PMT_BANKCLERING_Date, '%d/%m/%Y'),'')PMT_BANKCLERING_Date,D.Modified_By from SUBDEBT_APPLICATION_DETAIL A,DAN_CGPAN_INFO D where A.CGPAN=D.CGPAN AND D.Payment_ID='"
						+ appRefNO + "'";
			}
			 
					

			// System.out.println("getDanRecord : "+appDetails);
            String APP_REF_NO = "";
			String CGPAN = "";
			double approved_amt=0.0D;
			String danId="";
			double danAmount=0.0D;
			String unitItpan="";
			String APP_STATUS = "";
			String APP_REMARKS = "";
			String RP_NUMBER = "";
			String UNIT_NAME = "";
			String app_date = null;
			String APPROPRIATION_DATE = "";
			String DAN_STATUS = "";
			String guarantee_date = null;
			String GUARANTEE_DATE = "";
			String APPROPRIATED_BY = "";
			
			stmt = conn.prepareStatement(appDetails);
			rs1 = stmt.executeQuery();
            while(rs1.next()) {
            	ReportActionForm actionForm = new ReportActionForm();
				APP_REF_NO = rs1.getString("APP_REF_NO");
				actionForm.setAPP_REF_NO(APP_REF_NO);
				
				CGPAN = rs1.getString("CGPAN");
				actionForm.setCGPAN(CGPAN);
				
				approved_amt=rs1.getDouble("APP_APPROVED_AMOUNT");
				actionForm.setApproved_amt(approved_amt);
				
				danId=rs1.getString("DAN_ID");
				actionForm.setDanId(danId);
				
				danAmount=rs1.getDouble("DCI_GFEE_AMT");
				actionForm.setDanAmount(danAmount);
				
				APP_STATUS = rs1.getString("APP_STATUS");
				DAN_STATUS = rs1.getString("DAN_STATUS");
				app_date = rs1.getString("PMT_APPROPRIATION_Date");
				RP_NUMBER = rs1.getString("Payment_ID");
				if(app_date!=null && DAN_STATUS!=null && !DAN_STATUS.equalsIgnoreCase("RP") && !DAN_STATUS.equalsIgnoreCase("NE")){
					actionForm.setAPP_STATUS(DAN_STATUS);
				}
				else{
					actionForm.setAPP_STATUS("GA");
				}
				unitItpan=rs1.getString("SSI_IT_PAN");
				actionForm.setUnitItpan(unitItpan);
				
				APP_REMARKS = rs1.getString("APP_REMARKS");
				if(APP_REMARKS!=null){
					actionForm.setAPP_REMARKS(APP_REMARKS);
				}
				else{
					actionForm.setAPP_REMARKS("");
				}	
				
				RP_NUMBER = rs1.getString("Payment_ID");
				if(RP_NUMBER == null || RP_NUMBER.length() == 0){
					actionForm.setRP_NUMBER("");
				}
				else
				{
					if("RP".equalsIgnoreCase(DAN_STATUS))
					{
						actionForm.setAPP_STATUS("IP");
					}
					actionForm.setRP_NUMBER(RP_NUMBER);
				}
				
				UNIT_NAME = rs1.getString("SSI_UNIT_NAME");
				
				guarantee_date = rs1.getString("PMT_BANKCLERING_Date");
				APPROPRIATED_BY = rs1.getString("Modified_By");
				if(app_date!=null && DAN_STATUS!=null && DAN_STATUS.equalsIgnoreCase("AP")){
					APPROPRIATION_DATE = app_date.toString();
					GUARANTEE_DATE = guarantee_date.toString();
					actionForm.setAPPROPRIATED_BY(APPROPRIATED_BY);
					
				}
				else{
					APPROPRIATION_DATE = "";
					GUARANTEE_DATE = "";
					actionForm.setAPPROPRIATED_BY("");
				}
				actionForm.setGUARANTEE_DATE(GUARANTEE_DATE);
				actionForm.setAPPROPRIATION_DATE(APPROPRIATION_DATE);
				actionForm.setUNIT_NAME(UNIT_NAME);
				reportArray.add(actionForm);
			} 
            if(reportArray.size()>0){
            	reportForm.setAllocatepaymentFinal(reportArray);
            }           
             if (type.equalsIgnoreCase("ApplicationReferenceNo") && appRefNO.equals("")) {
				throw new NoMemberFoundException(
						"Entered  Number not Available. Please enter correct Application Reference Number");
			}
			else if (type.equalsIgnoreCase("Cgpan") && appRefNO.equals("")) {
				throw new NoMemberFoundException(
						"Entered  Cgpan not Available. Please enter correct Cgpan");
			}
			else if (type.equalsIgnoreCase("PaymentId") && appRefNO.equals("")) {
				throw new NoMemberFoundException(
						"Entered  PaymentId not Available. Please enter correct PaymentId");
			}
           

		} catch (Exception e) {
			throw new NoMemberFoundException(e.getMessage());
		}

		finally {
			DBConnection.freeConnection(conn);
		}
		return mapping.findForward("success");

	}

	public ActionForward displayClaimLodgedReport(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		Log.log(Log.INFO, "ClaimAction", "displayClaimProcessingInput",
				"Entered");
		Connection connection = DBConnection.getConnection();

		HttpSession session = request.getSession();
		PreparedStatement claimsubmitduStmt = null;
		ResultSet claimlodgedResult = null;
		ClaimActionForm claimForm = null;
		User user = (User) getUserInformation(request);
		String bankid = (user.getBankId()).trim();
		String zoneid = (user.getZoneId()).trim();
		String branchid = (user.getBranchId()).trim();
		String memberId = bankid + zoneid + branchid;
		System.out.println("raaj" + memberId);
		ResultSet rs = null;
		PreparedStatement pst = null;
		// SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
		Log.log(Log.INFO, "ClaimAction", "displayClaimProcessingInput",
				"Exited");
		ArrayList claimArray = new ArrayList();

		ClaimActionForm claimFormobj = (ClaimActionForm) form;

		try {

			String Query = (new StringBuilder(
					"SELECT mem_bank_name,ssi_unit_name,c.mem_bnk_id || c.mem_zne_id || c.mem_brn_id, mem_zone_name, ssi_unit_name,cgpan,clm_ref_no,DECODE (APP_REAPPROVE_AMOUNT,NULL, APP_APPROVED_AMOUNT,APP_REAPPROVE_AMOUNT)guaamt,DECODE (clm_status, 'MR', 'CHECKER RETURNED', 'PENDING AT CHECKER')STATUS,CLM_CHECKER_REMARKS FROM application_detail a,ssi_detail b,claim_detail_temp c,member_info m where  a.ssi_reference_number=b.ssi_reference_number and b.bid=c.bid  and CLM_DECL_RECVD_DT is  null and c.mem_bnk_id||c.mem_zne_id||c.mem_brn_id=m.mem_bnk_id||m.mem_zne_id||m.mem_brn_id  and  c.mem_bnk_id||c.mem_zne_id||c.mem_brn_id='"))
					.append(memberId).append("' AND CLM_DATE >= '01-DEC-2016'")
					.toString();

			/*
			 * String Query=
			 * "SELECT mem_bank_name,ssi_unit_name,c.mem_bnk_id || c.mem_zne_id || c.mem_brn_id, mem_zone_name, ssi_unit_name,"
			 * +
			 * "cgpan,clm_ref_no,DECODE (APP_REAPPROVE_AMOUNT,NULL, APP_APPROVED_AMOUNT,APP_REAPPROVE_AMOUNT)guaamt,"
			 * +
			 * "DECODE (clm_status, 'MR', 'CHECKER RETURNED', 'PENDING AT CHECKER')STATUS,CLM_CHECKER_REMARKS "
			 * +
			 * "FROM application_detail a,ssi_detail b,claim_detail_temp c,member_info m where  a.ssi_reference_number=b.ssi_reference_number "
			 * +
			 * "and b.bid=c.bid  and CLM_DECL_RECVD_DT is  null and c.mem_bnk_id||c.mem_zne_id||c.mem_brn_id=m.mem_bnk_id||m.mem_zne_id||m.mem_brn_id"
			 * +
			 * "  and  c.mem_bnk_id||c.mem_zne_id||c.mem_brn_id='"+memberId+"'";
			 */

			/*
			 * String Query=
			 * "SELECT mem_bank_name,ssi_unit_name,c.mem_bnk_id || c.mem_zne_id || c.mem_brn_id, mem_zone_name, ssi_unit_name,"
			 * +
			 * "cgpan,clm_ref_no,DECODE (APP_REAPPROVE_AMOUNT,NULL, APP_APPROVED_AMOUNT,APP_REAPPROVE_AMOUNT)guaamt,"
			 * +
			 * "DECODE (clm_status, 'MR', 'CHECKER RETURNED', 'PENDING AT CHECKER')STATUS,CLM_CHECKER_REMARKS "
			 * +
			 * "FROM application_detail a,ssi_detail b,claim_detail_temp c,member_info m where  a.ssi_reference_number=b.ssi_reference_number "
			 * +
			 * "and b.bid=c.bid  and CLM_DECL_RECVD_DT is  null and c.mem_bnk_id||c.mem_zne_id||c.mem_brn_id=m.mem_bnk_id||m.mem_zne_id||m.mem_brn_id"
			 * +
			 * "  and  c.mem_bnk_id||c.mem_zne_id||c.mem_brn_id='001900310000'";
			 */

			// System.out.println("Query raju:" + Query);

			pst = connection.prepareStatement(Query);
			// pst.setString(1, memberId);
			// System.out.println("ÄA npaRegistQuery : "+npaRegistQuery);
			rs = pst.executeQuery();
			// while(rs.next())
			// claimsubmitduStmt = connection.prepareStatement(Query);
			// claimsubmitduStmt.setString(1, memberId);
			// claimlodgedResult = claimsubmitduStmt.executeQuery();

			while (rs.next()) {

				claimForm = new ClaimActionForm();
				// ClaimActionForm claimForm = new ClaimActionForm();
				claimForm.setBankName(rs.getString(1));
				claimForm.setUnitNAME(rs.getString(2));
				claimForm.setMemberids(rs.getString(3));
				claimForm.setZone(rs.getString(4));

				claimForm.setCgPAN(rs.getString(6));
				claimForm.setClaimRefNo(rs.getString(7));
				claimForm.setGuaranteedAmount(rs.getString(8));
				// claimForm.setClaimSubmitCGTMSE(rs.getString(9));
				claimForm.setClaimReturnedForLodgement(rs.getString(9));
				claimForm.setClaimReturnedRemarks(rs.getString(10));

				claimArray.add(claimForm);
			}
			claimFormobj.setClaimLodgedReport(claimArray);
		} catch (Exception exception) {
			Log.logException(exception);
			throw new DatabaseException(exception.getMessage());
		} finally {
			DBConnection.freeConnection(connection);
		}

		return mapping.findForward("displayClaimsApprovalPagesreport");

	}

	// ended By Rajuk

	// rajuk added
	public ActionForward displayClmRefNumberDetail(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {

		ReportManager manager;
		ClaimActionForm claimForm;
		String clmApplicationStatus;
		String memberId;
		String cgpanno;
		String claimRefNumber = "";
		Connection connection;
		Log.log(4, "ReportsAction", "displayClmRefNumberDetail", "Entered");
		manager = new ReportManager();
		claimForm = (ClaimActionForm) form;
		clmApplicationStatus = "";
		Log.log(4,
				"ReportsAction",
				"displayClmRefNumberDetail",
				(new StringBuilder())
						.append("Claim Application Status being queried :")
						.append(clmApplicationStatus).toString());
		User user = getUserInformation(request);
		String bankid = user.getBankId().trim();
		String zoneid = user.getZoneId().trim();
		String branchid = user.getBranchId().trim();
		memberId = (new StringBuilder()).append(bankid).append(zoneid)
				.append(branchid).toString();
		request.setAttribute("CLAIMREFNO", request.getParameter("clmRefNumber"));

		claimRefNumber = request.getParameter("clmRefNumber");

		PreparedStatement pstmt = null;
		ResultSet rst = null;
		String clmRefNo = "";

		if (!GenericValidator.isBlankOrNull(claimRefNumber)) {
			pstmt = null;
			rst = null;
			clmRefNo = "";
			connection = DBConnection.getConnection();

			try {
				String query = "select distinct clm_status \nfrom claim_detail_temp ct,application_detail a,ssi_detail s\nwhere ct.bid = s.bid\nand s.ssi_reference_number = a.ssi_reference_number\nand ct.clm_ref_no = ?\nunion all\nselect distinct clm_status \nfrom claim_detail c,application_detail a,ssi_detail s\nwhere c.bid = s.bid\nand s.ssi_reference_number = a.ssi_reference_number\nand c.clm_ref_no = ? ";
				pstmt = connection.prepareStatement(query);
				pstmt.setString(1, claimRefNumber);
				pstmt.setString(2, claimRefNumber);

				for (rst = pstmt.executeQuery(); rst.next();)
					clmApplicationStatus = rst.getString(1);

				if (clmApplicationStatus.equals(""))
					throw new DatabaseException("Enter a valid Claim Ref No.");
				rst.close();
				rst = null;
				pstmt.close();
				pstmt = null;
			} catch (Exception exception) {
				Log.logException(exception);
				throw new DatabaseException(exception.getMessage());
			} finally {
				DBConnection.freeConnection(connection);
			}
		}
		if ("AP".equals(clmApplicationStatus))
			request.setAttribute("radioValue", "Approved");
		else if ("RE".equals(clmApplicationStatus))
			request.setAttribute("radioValue", "Rejected");
		else if ("NE".equals(clmApplicationStatus))
			request.setAttribute("radioValue", "NEW");
		else if ("HO".equals(clmApplicationStatus))
			request.setAttribute("radioValue", "Hold");
		else if ("FW".equals(clmApplicationStatus))
			request.setAttribute("radioValue", "Forwarded");
		else if ("TC".equals(clmApplicationStatus))
			request.setAttribute("radioValue", "Temporary Closed");
		else if ("TR".equals(clmApplicationStatus))
			request.setAttribute("radioValue", "Temporary Rejected");
		else if ("RR".equals(clmApplicationStatus))
			request.setAttribute("radioValue", "Reply Received");
		else if ("RT".equals(clmApplicationStatus))
			request.setAttribute("radioValue", "Retruned");
		else if ("WD".equals(clmApplicationStatus))
			request.setAttribute("radioValue", "Claim Withdrawn");

		// System.out.println((new
		// StringBuilder()).append("clmApplicationStatus:").append(clmApplicationStatus).toString());
		ClaimsProcessor processor = new ClaimsProcessor();
		ClaimApplication claimapplication = manager.displayClmRefNumberDtls(
				claimRefNumber, clmApplicationStatus, memberId);
		ArrayList clmSummryDtls = claimapplication.getClaimSummaryDtls();
		User userInfo = getUserInformation(request);
		if (claimapplication.getFirstInstallment()) {
			String thiscgpn = null;
			String bid = claimapplication.getBorrowerId();
			String memId = claimapplication.getMemberId();
			// Vector cgpnDetails = processor.getCGPANDetailsForBidClaim(bid,
			// memId);
			// System.out.println("ARAAJ");
			claimapplication.setClaimSummaryDtls(clmSummryDtls);
			claimForm.setClaimapplication(claimapplication);
			boolean internetUser = true;
			if ((new StringBuilder()).append(userInfo.getBankId())
					.append(userInfo.getZoneId())
					.append(userInfo.getBranchId()).toString()
					.equals("000000000000")
					&& !userInfo.getUserId().equalsIgnoreCase("DEMOUSER"))
				internetUser = false;
			Map attachments = manager.getClaimAttachments(claimRefNumber,
					clmApplicationStatus, internetUser);
			if (attachments.get("recallNotice") != null) {
				UploadFileProperties uploadFile = (UploadFileProperties) attachments
						.get("recallNotice");
				String formattedToOSPath = createNewFile(request,
						uploadFile.getFileName(), uploadFile.getFileSize());
				Log.log(5,
						"ReportsAction",
						"createNewFile",
						(new StringBuilder())
								.append(" Recall Notice Attachment path ")
								.append(formattedToOSPath).toString());
				request.setAttribute("recallNoticeAttachment",
						formattedToOSPath);
				// System.out.println("BRAAJ");
			}
			if (attachments.get("legalDetails") != null) {
				UploadFileProperties uploadFile = (UploadFileProperties) attachments
						.get("legalDetails");
				String formattedToOSPath = createNewFile(request,
						uploadFile.getFileName(), uploadFile.getFileSize());
				Log.log(5,
						"ReportsAction",
						"createNewFile",
						(new StringBuilder())
								.append(" Legal Details Attachment path ")
								.append(formattedToOSPath).toString());
				request.setAttribute("legalDetailsAttachment",
						formattedToOSPath);
				// System.out.println("CRAAJ");
			}
			return mapping.findForward("showFirstClmDetails");
		}
		if (claimapplication.getSecondInstallment()) {
			claimForm.setClaimapplication(claimapplication);
			String bid = claimapplication.getBorrowerId();
			String memId = claimapplication.getMemberId();
			Vector cgpnDetails = processor.getCGPANDetailsForBorrowerId(bid,
					memId);
			Vector clmAppliedAmnts = processor.getClaimAppliedAmounts(bid, "F");
			Vector updateClmDtls = new Vector();
			String thiscgpn = null;
			// System.out.println("DRAAJ");
			for (int i = 0; i < cgpnDetails.size(); i++) {
				HashMap dtl = (HashMap) cgpnDetails.elementAt(i);
				if (dtl != null) {
					thiscgpn = (String) dtl.get("CGPAN");
					if (thiscgpn != null) {
						for (int j = 0; j < clmAppliedAmnts.size(); j++) {
							HashMap clmAppliedDtl = (HashMap) clmAppliedAmnts
									.elementAt(j);
							String cgpnInAppliedAmntsVec = null;
							System.out.println("ERAAJ");
							if (clmAppliedDtl == null)
								continue;
							cgpnInAppliedAmntsVec = (String) clmAppliedDtl
									.get("CGPAN");
							if (cgpnInAppliedAmntsVec == null
									|| !cgpnInAppliedAmntsVec.equals(thiscgpn))
								continue;
							double clmAppliedAmnt = 0.0D;
							Double clmAppAmntObj = (Double) clmAppliedDtl
									.get("ClaimAppliedAmnt");
							if (clmAppAmntObj != null)
								clmAppliedAmnt = clmAppAmntObj.doubleValue();
							else
								clmAppliedAmnt = 0.0D;
							dtl.put("ClaimAppliedAmnt", new Double(
									clmAppliedAmnt));
							if (!updateClmDtls.contains(dtl))
								updateClmDtls.addElement(dtl);
							clmAppliedDtl = null;
							break;

						}

						dtl = null;
					}
				}
			}

			HashMap settlmntDetails = processor
					.getClaimSettlementDetailForBorrower(bid);
			double firstSettlementAmnt = 0.0D;
			Double firstSettlementAmntObj = (Double) settlmntDetails
					.get("FirstSettlmntAmnt");
			if (firstSettlementAmntObj != null)
				firstSettlementAmnt = firstSettlementAmntObj.doubleValue();
			Date firstSettlementDt = (Date) settlmntDetails
					.get("FirstSettlmntDt");
			HashMap dtl = null;
			Vector finalUpdatedDtls = new Vector();
			// System.out.println("FRAAJ");
			for (int i = 0; i < updateClmDtls.size(); i++) {
				dtl = (HashMap) updateClmDtls.elementAt(i);
				if (dtl != null) {
					dtl.put("FirstSettlmntAmnt",
							new Double(firstSettlementAmnt));
					dtl.put("FirstSettlmntDt", firstSettlementDt);
					if (!finalUpdatedDtls.contains(dtl))
						finalUpdatedDtls.addElement(dtl);
					dtl = null;
				}
			}

			ArrayList clmSummaryDtls = claimapplication.getClaimSummaryDtls();
			for (int j = 0; j < clmSummaryDtls.size(); j++) {
				ClaimSummaryDtls dtls = (ClaimSummaryDtls) clmSummaryDtls
						.get(j);
				String cgpan = null;
				double clmappliedamnt = 0.0D;
				// System.out.println("GRAAJ");
				if (dtls != null) {
					cgpan = dtls.getCgpan();
					clmappliedamnt = dtls.getAmount();
				}
				for (int i = 0; i < updateClmDtls.size(); i++) {
					dtl = (HashMap) updateClmDtls.elementAt(i);
					if (dtl != null) {
						String pan = (String) dtl.get("CGPAN");
						if (pan.equals(cgpan)) {
							dtl = (HashMap) updateClmDtls.remove(i);
							dtl.put("FirstSettlmntAmnt", new Double(
									firstSettlementAmnt));
							dtl.put("FirstSettlmntDt", firstSettlementDt);
							dtl.put("SECClaimAppliedAmnt", new Double(
									clmappliedamnt));
						}
						dtl = null;
					}
				}

			}

			claimForm.setUpdatedClaimDtls(finalUpdatedDtls);
			boolean internetUser = true;
			if ((new StringBuilder()).append(userInfo.getBankId())
					.append(userInfo.getZoneId())
					.append(userInfo.getBranchId()).toString()
					.equals("000000000000")
					&& !userInfo.getUserId().equalsIgnoreCase("DEMOUSER"))
				internetUser = false;
			Map attachments = manager.getClaimAttachments(claimRefNumber,
					clmApplicationStatus, internetUser);
			// System.out.println("HRAAJ");
			if (attachments.get("recallNotice") != null) {
				UploadFileProperties uploadFile = (UploadFileProperties) attachments
						.get("recallNotice");
				String formattedToOSPath = createNewFile(request,
						uploadFile.getFileName(), uploadFile.getFileSize());
				Log.log(5,
						"ReportsAction",
						"createNewFile",
						(new StringBuilder())
								.append(" Recall Notice Attachment path ")
								.append(formattedToOSPath).toString());
				request.setAttribute("recallNoticeAttachment",
						formattedToOSPath);
				// System.out.println("jRAAJ");
			}
			if (attachments.get("legalDetails") != null) {
				UploadFileProperties uploadFile = (UploadFileProperties) attachments
						.get("legalDetails");
				String formattedToOSPath = createNewFile(request,
						uploadFile.getFileName(), uploadFile.getFileSize());
				Log.log(5,
						"ReportsAction",
						"createNewFile",
						(new StringBuilder())
								.append(" Legal Details Attachment path ")
								.append(formattedToOSPath).toString());
				request.setAttribute("legalDetailsAttachment",
						formattedToOSPath);
				// System.out.println("IRAAJ");
			}
			return mapping.findForward("showSecClmDetails");
		} else {
			Log.log(4, "ReportsAction", "displayClmRefNumberDtl", "Exited");
			return null;
		}
	}

	// Diskha.......

	/*
	 * //niteen //rajuk
	 * 
	 * public ActionForward epcsPaymentReport(ActionMapping mapping, ActionForm
	 * form, HttpServletRequest request, HttpServletResponse response) throws
	 * Exception { Log.log(4, "ReportsAction", "stateWiseReport", "Entered");
	 * 
	 * DynaActionForm dynaForm = (DynaActionForm)form; java.util.Date date = new
	 * java.util.Date(); Calendar calendar = Calendar.getInstance();
	 * calendar.setTime(date); int month = calendar.get(2); int day =
	 * calendar.get(5); month -= 1; day += 1; calendar.set(2, month);
	 * calendar.set(5, day); java.util.Date prevDate = calendar.getTime();
	 * 
	 * GeneralReport generalReport = new GeneralReport();
	 * generalReport.setDateOfTheDocument(prevDate);
	 * generalReport.setDateOfTheDocument1(date);
	 * BeanUtils.copyProperties(dynaForm, generalReport); return
	 * mapping.findForward("success");
	 * 
	 * }
	 */
	// Diksha end
	public ActionForward otsReportDetails(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		Log.log(4, "ReportsAction", "otsReportDetails", "Exited");
		ArrayList danReport = new ArrayList();
		DynaActionForm dynaForm = (DynaActionForm) form;
		java.sql.Date startDate = null;
		java.sql.Date endDate = null;
		String flag = (String) dynaForm.get("danValue");
		java.util.Date sDate = (java.util.Date) dynaForm
				.get("dateOfTheDocument32");
		String stDate = String.valueOf(sDate);

		if ((stDate == null) || (stDate.equals("")))
			startDate = null;
		else if (stDate != null) {
			startDate = new java.sql.Date(sDate.getTime());
		}
		request.setAttribute("OTSDAN", flag);
		java.util.Date eDate = (java.util.Date) dynaForm
				.get("dateOfTheDocument33");
		endDate = new java.sql.Date(eDate.getTime());
		danReport = this.reportManager.otsReportDetails(startDate, endDate,
				flag);
		dynaForm.set("danRaisedReport", danReport);

		if ((danReport == null) || (danReport.size() == 0)) {
			throw new NoDataException(
					"No Data is available for the values entered, Please Enter Any Other Value ");
		}

		danReport = null;
		Log.log(4, "ReportsAction", "otsReportDetails", "Exited");

		return mapping.findForward("success");
	}

	public ActionForward applicationReport(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		Log.log(4, "ReportsAction", "applicationReport", "Entered");
		DynaActionForm dynaForm = (DynaActionForm) form;
		dynaForm.initialize(mapping);
		Log.log(4, "ReportsAction", "applicationReport", "Exited");

		return mapping.findForward("success");
	}

	public ActionForward rpCancelledReport(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		Log.log(4, "ReportsAction", "rpCancelledReport", "Entered");
		DynaActionForm dynaForm = (DynaActionForm) form;
		dynaForm.initialize(mapping);
		User user = getUserInformation(request);
		String bankId = user.getBankId();
		String zoneId = user.getZoneId();
		String branchId = user.getBranchId();
		String memberId = bankId.concat(zoneId).concat(branchId);
		java.util.Date date = new java.util.Date();
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(date);
		int month = calendar.get(2);
		int day = calendar.get(5);
		month -= 1;
		day += 1;
		calendar.set(2, month);
		calendar.set(5, day);
		java.util.Date prevDate = calendar.getTime();

		GeneralReport generalReport = new GeneralReport();
		generalReport.setDateOfTheDocument16(prevDate);
		generalReport.setDateOfTheDocument17(date);
		BeanUtils.copyProperties(dynaForm, generalReport);
		dynaForm.set("bankId", bankId);
		if (bankId.equals("0000")) {
			memberId = "";
		}
		dynaForm.set("memberId", memberId);
		Log.log(4, "ReportsAction", "rpCancelledReport", "Exited");

		return mapping.findForward("success");
	}

	public ActionForward rpCancelledReportDetails(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		Log.log(4, "ReportsAction", "rpCancelledReportDetails", "Entered");
		ArrayList rpCancelledReport = new ArrayList();
		DynaActionForm dynaForm = (DynaActionForm) form;
		java.sql.Date startDate = null;
		java.sql.Date endDate = null;
		java.util.Date sDate = (java.util.Date) dynaForm
				.get("dateOfTheDocument16");
		String stDate = String.valueOf(sDate);

		if ((stDate == null) || (stDate.equals("")))
			startDate = null;
		else if (stDate != null)
			startDate = new java.sql.Date(sDate.getTime());
		java.util.Date eDate = (java.util.Date) dynaForm
				.get("dateOfTheDocument17");
		endDate = new java.sql.Date(eDate.getTime());
		String id = (String) dynaForm.get("memberId");
		String payId = (String) dynaForm.get("payId");

		User user = getUserInformation(request);
		String bankId = user.getBankId();
		String zoneId = user.getZoneId();
		String branchId = user.getBranchId();
		String memberId = bankId + zoneId + branchId;
		ReportDAO rpDao = new ReportDAO();
		String newRp = payId.toUpperCase();

		if ((newRp != null) && (!newRp.equals(""))) {
			rpCancelledReport = rpDao.getAllocationCancelledReport(newRp, id);
			if ((rpCancelledReport == null) || (rpCancelledReport.size() == 0)) {
				throw new NoDataException(
						"No Data is available for the values entered RP and MemberId, Please Enter Any Other Details ");
			}

		} else if ((newRp == null) || (newRp.equals(""))) {
			rpCancelledReport = rpDao.getAllocationCancelledList(id, startDate,
					endDate);
			if ((rpCancelledReport == null) || (rpCancelledReport.size() == 0)) {
				throw new NoDataException(
						"No Data is available for the values entered, Please Enter Any Other Values ");
			}

			dynaForm.set("rpCancelledReport", rpCancelledReport);
			rpCancelledReport = null;

			return mapping.findForward("cancelledList");
		}

		if ((rpCancelledReport == null) || (rpCancelledReport.size() == 0)) {
			throw new NoDataException(
					"No Data is available for the values entered RP, Please Enter Any Other Payment Id ");
		}

		dynaForm.set("rpCancelledReport", rpCancelledReport);
		rpCancelledReport = null;
		Log.log(4, "ReportsAction", "rpCancelledReportDetails", "Exited");

		return mapping.findForward("success");
	}

	public ActionForward applicationHistoryReport(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		Log.log(4, "ReportsAction", "applicationReport", "Entered");
		DynaActionForm dynaForm = (DynaActionForm) form;
		dynaForm.initialize(mapping);
		Log.log(4, "ReportsAction", "applicationReport", "Exited");

		return mapping.findForward("success");
	}

	public ActionForward cgpanHistoryReport(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		Log.log(4, "ReportsAction", "cgpanHistoryReport", "Entered");
		DynaActionForm dynaForm = (DynaActionForm) form;
		dynaForm.initialize(mapping);
		Log.log(4, "ReportsAction", "cgpanHistoryReport", "Exited");

		return mapping.findForward("success");
	}

	public ActionForward shortReportInput(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		Log.log(4, "ReportsAction", "shortReportInput", "Entered");

		DynaActionForm dynaForm = (DynaActionForm) form;
		java.util.Date date = new java.util.Date();
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(date);
		int month = calendar.get(2);
		int day = calendar.get(5);
		month -= 1;
		day += 1;
		calendar.set(2, month);
		calendar.set(5, day);
		java.util.Date prevDate = calendar.getTime();

		GeneralReport generalReport = new GeneralReport();
		generalReport.setDateOfTheDocument30(prevDate);
		generalReport.setDateOfTheDocument31(date);
		BeanUtils.copyProperties(dynaForm, generalReport);
		Log.log(4, "ReportsAction", "shortReportInput", "Exited");

		return mapping.findForward("success");
	}

	public ActionForward dayReportInput(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		Log.log(4, "ReportsAction", "dayReportInput", "Entered");

		DynaActionForm dynaForm = (DynaActionForm) form;
		java.util.Date date = new java.util.Date();
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(date);
		int month = calendar.get(2);
		int day = calendar.get(5);
		month -= 1;
		day += 1;
		calendar.set(2, month);
		calendar.set(5, day);
		java.util.Date prevDate = calendar.getTime();

		GeneralReport generalReport = new GeneralReport();

		generalReport.setDateOfTheDocument31(date);
		BeanUtils.copyProperties(dynaForm, generalReport);
		Log.log(4, "ReportsAction", "dayReportInput", "Exited");

		return mapping.findForward("success");
	}

	public ActionForward shortReport(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		Log.log(4, "ReportsAction", "shortReport", "Entered");
		ArrayList dan = new ArrayList();
		DynaActionForm dynaForm = (DynaActionForm) form;
		java.sql.Date startDate = null;
		java.sql.Date endDate = null;
		java.util.Date sDate = (java.util.Date) dynaForm
				.get("dateOfTheDocument30");
		String stDate = String.valueOf(sDate);

		if ((stDate == null) || (stDate.equals("")))
			startDate = null;
		else if (stDate != null) {
			startDate = new java.sql.Date(sDate.getTime());
		}
		java.util.Date eDate = (java.util.Date) dynaForm
				.get("dateOfTheDocument31");
		endDate = new java.sql.Date(eDate.getTime());
		dan = this.reportManager.shortReport(startDate, endDate);
		dynaForm.set("danRaised", dan);
		if ((dan == null) || (dan.size() == 0)) {
			throw new NoDataException(
					"No Data is available for the values entered, Please Enter Any Other Value ");
		}

		dan = null;
		Log.log(4, "ReportsAction", "shortReport", "Exited");

		return mapping.findForward("success");
	}

	public ActionForward dayReport(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		Log.log(4, "ReportsAction", "dayReport", "Entered");
		ArrayList dayDetails = new ArrayList();
		DynaActionForm dynaForm = (DynaActionForm) form;

		java.sql.Date endDate = null;

		java.util.Date eDate = (java.util.Date) dynaForm
				.get("dateOfTheDocument31");
		endDate = new java.sql.Date(eDate.getTime());

		dayDetails = this.reportManager.dayReport(endDate);
		dynaForm.set("dayDetails", dayDetails);
		if ((dayDetails == null) || (dayDetails.size() == 0)) {
			throw new NoDataException(
					"No Data is available for the values entered, Please Enter Any Other Value ");
		}

		dayDetails = null;
		Log.log(4, "ReportsAction", "dayReport", "Exited");

		return mapping.findForward("success");
	}

	public ActionForward shortReportDetails(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		Log.log(4, "ReportsAction", "shortReportDetails", "Exited");
		ArrayList danReport = new ArrayList();
		DynaActionForm dynaForm = (DynaActionForm) form;
		java.sql.Date startDate = null;
		java.sql.Date endDate = null;
		String flag = (String) dynaForm.get("paymentValue");
		java.util.Date sDate = (java.util.Date) dynaForm
				.get("dateOfTheDocument30");
		String stDate = String.valueOf(sDate);
		request.setAttribute("PAYMENTVALUE", flag);
		if ((stDate == null) || (stDate.equals("")))
			startDate = null;
		else if (stDate != null) {
			startDate = new java.sql.Date(sDate.getTime());
		}
		java.util.Date eDate = (java.util.Date) dynaForm
				.get("dateOfTheDocument31");
		endDate = new java.sql.Date(eDate.getTime());
		danReport = this.reportManager.shortReportDetails(startDate, endDate,
				flag);
		dynaForm.set("danRaisedReport", danReport);
		if ((danReport == null) || (danReport.size() == 0)) {
			throw new NoDataException(
					"No Data is available for the values entered, Please Enter Any Other Value ");
		}

		danReport = null;
		Log.log(4, "ReportsAction", "shortReportDetails", "Exited");

		return mapping.findForward("success");
	}

	public ActionForward excessReportInput(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		Log.log(4, "ReportsAction", "excessReportInput", "Entered");

		DynaActionForm dynaForm = (DynaActionForm) form;
		java.util.Date date = new java.util.Date();
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(date);
		int month = calendar.get(2);
		int day = calendar.get(5);
		month -= 1;
		day += 1;
		calendar.set(2, month);
		calendar.set(5, day);
		java.util.Date prevDate = calendar.getTime();
		GeneralReport generalReport = new GeneralReport();
		generalReport.setDateOfTheDocument28(prevDate);
		generalReport.setDateOfTheDocument29(date);
		BeanUtils.copyProperties(dynaForm, generalReport);
		Log.log(4, "ReportsAction", "excessReportInput", "Exited");

		return mapping.findForward("success");
	}

	public ActionForward excessReport(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		Log.log(4, "ReportsAction", "excessReport", "Entered");
		ArrayList dan = new ArrayList();
		DynaActionForm dynaForm = (DynaActionForm) form;
		java.sql.Date startDate = null;
		java.sql.Date endDate = null;
		java.util.Date sDate = (java.util.Date) dynaForm
				.get("dateOfTheDocument28");
		String stDate = String.valueOf(sDate);

		if ((stDate == null) || (stDate.equals("")))
			startDate = null;
		else if (stDate != null) {
			startDate = new java.sql.Date(sDate.getTime());
		}
		java.util.Date eDate = (java.util.Date) dynaForm
				.get("dateOfTheDocument29");
		endDate = new java.sql.Date(eDate.getTime());
		dan = this.reportManager.excessReport(startDate, endDate);
		dynaForm.set("danRaised", dan);

		if ((dan == null) || (dan.size() == 0)) {
			throw new NoDataException(
					"No Data is available for the values entered, Please Enter Any Other Value ");
		}

		dan = null;
		Log.log(4, "ReportsAction", "excessReport", "Exited");

		return mapping.findForward("success");
	}

	public ActionForward excessReportDetails(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		Log.log(4, "ReportsAction", "excessReportDetails", "Exited");
		ArrayList danReport = new ArrayList();
		DynaActionForm dynaForm = (DynaActionForm) form;
		java.sql.Date startDate = null;
		java.sql.Date endDate = null;
		String flag = (String) dynaForm.get("danValue");
		java.util.Date sDate = (java.util.Date) dynaForm
				.get("dateOfTheDocument28");
		String stDate = String.valueOf(sDate);

		if ((stDate == null) || (stDate.equals("")))
			startDate = null;
		else if (stDate != null) {
			startDate = new java.sql.Date(sDate.getTime());
		}
		java.util.Date eDate = (java.util.Date) dynaForm
				.get("dateOfTheDocument29");
		endDate = new java.sql.Date(eDate.getTime());
		danReport = this.reportManager.excessReportDetails(startDate, endDate,
				flag);
		dynaForm.set("danRaisedReport", danReport);

		request.setAttribute("DANVALUE", flag);

		if ((danReport == null) || (danReport.size() == 0)) {
			throw new NoDataException(
					"No Data is available for the values entered, Please Enter Any Other Value ");
		}

		danReport = null;
		Log.log(4, "ReportsAction", "excessReportDetails", "Exited");

		return mapping.findForward("success");
	}

	public ActionForward sectorWiseReport(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		Log.log(4, "ReportsAction", "sectorWiseReport", "Entered");

		DynaActionForm dynaForm = (DynaActionForm) form;
		java.util.Date date = new java.util.Date();
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(date);
		int month = calendar.get(2);
		int day = calendar.get(5);
		month -= 1;
		day += 1;
		calendar.set(2, month);
		calendar.set(5, day);
		java.util.Date prevDate = calendar.getTime();

		GeneralReport generalReport = new GeneralReport();
		generalReport.setDateOfTheDocument8(prevDate);
		generalReport.setDateOfTheDocument9(date);
		BeanUtils.copyProperties(dynaForm, generalReport);
		Log.log(4, "ReportsAction", "sectorWiseReport", "Exited");

		return mapping.findForward("success");
	}

	public ActionForward applicationRecievedReportInput(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		Log.log(4, "ReportsAction", "applicationRecievedReportInput", "Entered");

		DynaActionForm dynaForm = (DynaActionForm) form;
		java.util.Date date = new java.util.Date();
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(date);
		int month = calendar.get(2);
		int day = calendar.get(5);
		month -= 1;
		day += 1;
		calendar.set(2, month);
		calendar.set(5, day);
		java.util.Date prevDate = calendar.getTime();

		GeneralReport generalReport = new GeneralReport();
		generalReport.setDateOfTheDocument22(prevDate);
		generalReport.setDateOfTheDocument23(date);
		BeanUtils.copyProperties(dynaForm, generalReport);

		Log.log(4, "ReportsAction", "applicationRecievedReportInput", "Exited");

		return mapping.findForward("success");
	}

	public ActionForward slabWiseClaimReport(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		DynaValidatorActionForm Form = (DynaValidatorActionForm) form;
		HttpSession session = request.getSession();
		ArrayList stateList = new ArrayList();
		ArrayList districtList = new ArrayList();
		ArrayList sectorList = new ArrayList();
		ArrayList rangeList = new ArrayList();

		Log.log(4, "slabWiseClaimReport", "slabWiseClaimReport", "Entered");
		Statement stmt = null;
		ResultSet result = null;
		Connection connection = DBConnection.getConnection();

		DynaActionForm dynaForm = (DynaActionForm) form;
		java.util.Date date = new java.util.Date();
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(date);
		int month = calendar.get(2);
		int day = calendar.get(5);
		month -= 1;
		day += 1;
		calendar.set(2, month);
		calendar.set(5, day);
		java.util.Date prevDate = calendar.getTime();
		GeneralReport generalReport = new GeneralReport();
		generalReport.setDateOfTheDocument42(prevDate);
		generalReport.setDateOfTheDocument43(date);
		BeanUtils.copyProperties(dynaForm, generalReport);
		try {
			String query = " SELECT ste_code,ste_name FROM state_master order by ste_name";
			stmt = connection.createStatement();
			result = stmt.executeQuery(query);
			String[] state = null;
			while (result.next()) {
				state = new String[2];
				state[0] = result.getString(1);
				state[1] = result.getString(2);
				stateList.add(state);
			}

			session.setAttribute("stateList", stateList);
			result = null;
			stmt = null;
		} catch (Exception exception) {
			Log.logException(exception);
			throw new DatabaseException(exception.getMessage());
		}

		if (request.getParameter("hiddenvalue") != null) {
			String state = (String) Form.get("slabState");
			String stateCode = "";
			try {
				String query = " select ste_code FROM state_master where ste_name='"
						+ state + "'";
				stmt = connection.createStatement();
				result = stmt.executeQuery(query);

				while (result.next()) {
					stateCode = result.getString(1);
				}
				result = null;
				stmt = null;
			} catch (Exception exception) {
				Log.logException(exception);
				throw new DatabaseException(exception.getMessage());
			}

			try {
				String query = " select dst_code,dst_name,ste_code  from district_master where ste_code='"
						+ stateCode + "' order by dst_name";
				stmt = connection.createStatement();
				result = stmt.executeQuery(query);
				String[] district = null;
				while (result.next()) {
					district = new String[2];
					district[0] = (result.getInt(1) + "");
					district[1] = result.getString(2);
					districtList.add(district);
				}
				session.setAttribute("districtList", districtList);
				result = null;
				stmt = null;
			} catch (Exception exception) {
				Log.logException(exception);
				throw new DatabaseException(exception.getMessage());
			}

			request.setAttribute("districtSet", "districtSet");
		}

		try {
			String query = " SELECT isc_code, isc_name FROM INDUSTRY_SECTOR ORDER BY isc_name";
			stmt = connection.createStatement();
			result = stmt.executeQuery(query);
			String[] sector = null;

			while (result.next()) {
				sector = new String[2];
				sector[0] = (result.getInt(1) + "");
				sector[1] = result.getString(2);
				sectorList.add(sector);
			}

			session.setAttribute("sectorList", sectorList);
			result = null;
			stmt = null;
		} catch (Exception exception) {
			Log.logException(exception);
			throw new DatabaseException(exception.getMessage());
		}

		try {
			String query = "SELECT range_id,range_desc FROM range_master";
			stmt = connection.createStatement();
			result = stmt.executeQuery(query);
			String[] range = null;

			while (result.next()) {
				range = new String[2];
				range[0] = result.getString(1);
				range[1] = result.getString(2);
				rangeList.add(range);
			}

			session.setAttribute("rangeList", rangeList);
			result = null;
			stmt = null;
		} catch (Exception exception) {
			Log.logException(exception);
			throw new DatabaseException(exception.getMessage());
		} finally {
			DBConnection.freeConnection(connection);
		}

		return mapping.findForward("success");
	}

	public ActionForward slabWiseClaimReportOutPut(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		DynaActionForm Form = (DynaActionForm) form;

		Log.log(4, "SlabWiseOutPutReportAction", "slabWiseClaimReportOutPut",
				"Entered");
		Statement stmt = null;
		ResultSet result = null;
		Connection connection = DBConnection.getConnection();
		Vector list = new Vector();
		double amount = 0.0D;
		int proposals = 0;

		DecimalFormat decimalFormat = new DecimalFormat("##########0.00");

		java.util.Date resta = (java.util.Date) Form.get("dateOfTheDocument42");

		java.util.Date resend = (java.util.Date) Form
				.get("dateOfTheDocument43");
		String state = (String) Form.get("slabState");
		String district = (String) Form.get("slabDistrict");
		String sector = (String) Form.get("slabIndustrySector");
		String mliID = ((String) Form.get("mliID")).trim();
		String rangeFrom = ((String) Form.get("rangeFrom")).trim();
		String rangeTo = ((String) Form.get("rangeTo")).trim();
		String range = "";

		java.util.Date toDate = null;
		java.util.Date fromDate = null;

		if ((!resend.equals("")) && (resend != null)) {
			toDate = (java.util.Date) Form.get("dateOfTheDocument43");
		}
		if ((!resta.equals("")) && (resta != null)) {
			fromDate = (java.util.Date) Form.get("dateOfTheDocument42");
		}

		java.sql.Date startDate = null;
		java.sql.Date endDate = null;

		if ((fromDate != null) && (!fromDate.equals(""))) {
			startDate = new java.sql.Date(fromDate.getTime());
		}
		if ((toDate != null) && (!toDate.equals(""))) {
			endDate = new java.sql.Date(toDate.getTime());
		}

		if (state.equals("select"))
			state = null;
		if (district.equals("select"))
			district = null;
		if (sector.equals("select"))
			sector = null;
		if (mliID.equals(""))
			mliID = null;
		if (rangeTo.equals(""))
			rangeTo = null;
		if (rangeFrom.equals("")) {
			rangeFrom = null;
		}
		if ((rangeFrom == null) || (rangeTo == null))
			range = null;
		else {
			range = rangeFrom + "-" + rangeTo;
		}

		CallableStatement slab = null;
		try {
			slab = connection
					.prepareCall("call Packallslabforclaim.PROCCLAIMSLABREPORT(?,?,?,?,?,?,?,?)");
			slab.setDate(1, startDate);
			slab.setDate(2, endDate);
			slab.setString(3, state);
			slab.setString(4, district);
			slab.setString(5, range);
			slab.setString(6, sector);
			slab.setString(7, mliID);
			slab.registerOutParameter(8, -10);
			slab.execute();
			result = (ResultSet) slab.getObject(8);
			String[] slabList = null;

			while (result.next()) {
				slabList = new String[3];
				slabList[0] = result.getString(1);
				slabList[1] = (result.getInt(2) + "");
				slabList[2] = (decimalFormat.format(result.getDouble(3)) + "");

				amount += result.getDouble(3);

				proposals += result.getInt(2);
				list.add(slabList);
			}

			request.setAttribute("slabList", list);
			request.setAttribute("totalProposals", proposals + "");
			request.setAttribute("totalLoan", decimalFormat.format(amount));
			result = null;
			stmt = null;
		} catch (Exception exception) {
			exception.printStackTrace();
			Log.logException(exception);
			throw new DatabaseException(exception.getMessage());
		} finally {
			DBConnection.freeConnection(connection);
		}

		return mapping.findForward("success");
	}

	public ActionForward applicationApprovedReportInput(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		Log.log(4, "ReportsAction", "applicationApprovedReportInput", "Entered");
		DynaActionForm dynaForm = (DynaActionForm) form;
		java.util.Date date = new java.util.Date();
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(date);
		int month = calendar.get(2);
		int day = calendar.get(5);
		month -= 1;
		day += 1;
		calendar.set(2, month);
		calendar.set(5, day);
		java.util.Date prevDate = calendar.getTime();
		GeneralReport generalReport = new GeneralReport();
		generalReport.setDateOfTheDocument(prevDate);
		generalReport.setDateOfTheDocument1(date);
		BeanUtils.copyProperties(dynaForm, generalReport);

		Log.log(4, "ReportsAction", "applicationApprovedReportInput", "Exited");

		return mapping.findForward("success");
	}

	public ActionForward applicationApprovedReport(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		Log.log(4, "ReportsAction", "applicationApprovedReport", "Entered");
		ArrayList applicationApprovedReport = new ArrayList();
		DynaActionForm dynaForm = (DynaActionForm) form;
		java.sql.Date startDate = null;
		java.sql.Date endDate = null;
		java.util.Date sDate = (java.util.Date) dynaForm
				.get("dateOfTheDocument");
		String stDate = String.valueOf(sDate);

		if ((stDate == null) || (stDate.equals("")))
			startDate = null;
		else if (stDate != null) {
			startDate = new java.sql.Date(sDate.getTime());
		}

		java.util.Date eDate = (java.util.Date) dynaForm
				.get("dateOfTheDocument1");
		endDate = new java.sql.Date(eDate.getTime());

		applicationApprovedReport = this.reportManager
				.getapplicationApprovedReport(startDate, endDate);
		dynaForm.set("applicationApprovedReport", applicationApprovedReport);
		if ((applicationApprovedReport == null)
				|| (applicationApprovedReport.size() == 0)) {
			throw new NoDataException(
					"No Data is available for the values entered, Please Enter Any Other Value ");
		}

		Log.log(4, "ReportsAction", "applicationApprovedReport", "Exited");

		return mapping.findForward("success");
	}

	public ActionForward applicationWiseReportInput(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		Log.log(4, "ReportsAction", "applicationWiseReportInput", "Entered");

		DynaActionForm dynaForm = (DynaActionForm) form;
		java.util.Date date = new java.util.Date();
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(date);
		int month = calendar.get(2);
		int day = calendar.get(5);
		month -= 1;
		day += 1;
		calendar.set(2, month);
		calendar.set(5, day);
		java.util.Date prevDate = calendar.getTime();

		GeneralReport generalReport = new GeneralReport();
		generalReport.setDateOfTheDocument24(prevDate);
		generalReport.setDateOfTheDocument25(date);
		BeanUtils.copyProperties(dynaForm, generalReport);

		Log.log(4, "ReportsAction", "applicationWiseReportInput", "Exited");

		return mapping.findForward("success");
	}

	public ActionForward securitizationReportInput(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		Log.log(4, "ReportsAction", "securitizationReportInput", "Entered");

		DynaActionForm dynaForm = (DynaActionForm) form;
		java.util.Date date = new java.util.Date();
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(date);
		int month = calendar.get(2);
		int day = calendar.get(5);
		month -= 1;
		day += 1;
		calendar.set(2, month);
		calendar.set(5, day);
		java.util.Date prevDate = calendar.getTime();

		GeneralReport generalReport = new GeneralReport();
		generalReport.setDateOfTheDocument26(prevDate);
		generalReport.setDateOfTheDocument27(date);
		BeanUtils.copyProperties(dynaForm, generalReport);

		Log.log(4, "ReportsAction", "securitizationReportInput", "Exited");

		return mapping.findForward("success");
	}

	public ActionForward NotAppropriatedDetailsfromInward(
			ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		Log.log(4, "ReportsAction", "NotAppropriatedDetailsfromInward",
				"Entered");

		DynaActionForm dynaForm = (DynaActionForm) form;
		java.util.Date date = new java.util.Date();
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(date);
		int month = calendar.get(2);
		int day = calendar.get(5);
		month -= 1;
		day += 1;
		calendar.set(2, month);
		calendar.set(5, day);
		java.util.Date prevDate = calendar.getTime();

		GeneralReport generalReport = new GeneralReport();
		generalReport.setDateOfTheDocument26(prevDate);
		generalReport.setDateOfTheDocument27(date);
		BeanUtils.copyProperties(dynaForm, generalReport);

		Log.log(4, "ReportsAction", "NotAppropriatedDetailsfromInward",
				"Exited");

		return mapping.findForward("success");
	}

	public ActionForward AfterNotAppropriatedDetailsfromInward(
			ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		Log.log(4, "ReportsAction", "AfterNotAppropriatedDetailsfromInward",
				"Entered");
		ArrayList notAppropriatedCasesList = new ArrayList();
		DynaActionForm dynaForm = (DynaActionForm) form;
		java.sql.Date startDate = null;
		java.sql.Date endDate = null;
		java.util.Date sDate = (java.util.Date) dynaForm
				.get("dateOfTheDocument26");
		String stDate = String.valueOf(sDate);

		if ((stDate == null) || (stDate.equals("")))
			startDate = null;
		else if (stDate != null) {
			startDate = new java.sql.Date(sDate.getTime());
		}
		java.util.Date eDate = (java.util.Date) dynaForm
				.get("dateOfTheDocument27");
		endDate = new java.sql.Date(eDate.getTime());
		notAppropriatedCasesList = this.reportManager
				.AfterNotAppropriatedDetailsfromInward(startDate, endDate);
		dynaForm.set("danRaised", notAppropriatedCasesList);

		if ((notAppropriatedCasesList == null)
				|| (notAppropriatedCasesList.size() == 0)) {
			throw new NoDataException(
					"No Data is available for the values entered, Please Enter Any Other Value ");
		}

		notAppropriatedCasesList = null;
		Log.log(4, "ReportsAction", "AfterNotAppropriatedDetailsfromInward",
				"Exited");

		return mapping.findForward("success");
	}

	public ActionForward investmentInputNew(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		Log.log(4, "ReportsAction", "investmentInputNew", "Entered");

		DynaActionForm dynaForm = (DynaActionForm) form;
		java.util.Date date = new java.util.Date();
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(date);
		int month = calendar.get(2);
		int day = calendar.get(5);
		month -= 1;
		day += 1;
		calendar.set(2, month);
		calendar.set(5, day);
		java.util.Date prevDate = calendar.getTime();

		GeneralReport generalReport = new GeneralReport();
		generalReport.setDateOfTheDocument26(prevDate);
		generalReport.setDateOfTheDocument27(date);
		BeanUtils.copyProperties(dynaForm, generalReport);

		Log.log(4, "ReportsAction", "investmentInputNew", "Exited");

		return mapping.findForward("success");
	}

	public ActionForward investmentReportNew(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		Log.log(4, "ReportsAction", "investmentReportNew", "Entered");

		ArrayList investmentDetails = new ArrayList();
		DynaActionForm dynaForm = (DynaActionForm) form;
		java.sql.Date startDate = null;
		java.sql.Date endDate = null;
		java.util.Date sDate = (java.util.Date) dynaForm
				.get("dateOfTheDocument26");
		String stDate = String.valueOf(sDate);

		if ((stDate == null) || (stDate.equals("")))
			startDate = null;
		else if (stDate != null) {
			startDate = new java.sql.Date(sDate.getTime());
		}
		java.util.Date eDate = (java.util.Date) dynaForm
				.get("dateOfTheDocument27");
		endDate = new java.sql.Date(eDate.getTime());
		investmentDetails = this.reportManager.investmentReport(startDate,
				endDate);
		dynaForm.set("danRaised", investmentDetails);

		HttpSession session = request.getSession(true);
		session.setAttribute("danRaised", investmentDetails);

		if ((investmentDetails == null) || (investmentDetails.size() == 0)) {
			throw new NoDataException(
					"No Data is available for the values entered, Please Enter Any Other Value ");
		}

		investmentDetails = null;
		Log.log(4, "ReportsAction", "investmentReportNew", "Exited");

		return mapping.findForward("success");
	}

	public ActionForward workshopReportInput(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		Log.log(4, "ReportsAction", "workshopReportInput", "Entered");

		DynaActionForm dynaForm = (DynaActionForm) form;
		java.util.Date date = new java.util.Date();
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(date);
		int month = calendar.get(2);
		int day = calendar.get(5);
		month -= 1;
		day += 1;
		calendar.set(2, month);
		calendar.set(5, day);
		java.util.Date prevDate = calendar.getTime();

		GeneralReport generalReport = new GeneralReport();
		generalReport.setDateOfTheDocument26(prevDate);
		generalReport.setDateOfTheDocument27(date);
		BeanUtils.copyProperties(dynaForm, generalReport);

		Log.log(4, "ReportsAction", "workshopReportInput", "Exited");

		return mapping.findForward("success");
	}

	public ActionForward workshopReport(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		Log.log(4, "ReportsAction", "workshopReport", "Entered");
		ArrayList workshopDetails = new ArrayList();
		DynaActionForm dynaForm = (DynaActionForm) form;
		java.sql.Date startDate = null;
		java.sql.Date endDate = null;
		java.util.Date sDate = (java.util.Date) dynaForm
				.get("dateOfTheDocument26");
		String stDate = String.valueOf(sDate);

		if ((stDate == null) || (stDate.equals("")))
			startDate = null;
		else if (stDate != null) {
			startDate = new java.sql.Date(sDate.getTime());
		}
		java.util.Date eDate = (java.util.Date) dynaForm
				.get("dateOfTheDocument27");
		endDate = new java.sql.Date(eDate.getTime());
		workshopDetails = this.reportManager.workshopReport(startDate, endDate);
		dynaForm.set("danRaised", workshopDetails);

		HttpSession session = request.getSession(true);
		session.setAttribute("danRaised", workshopDetails);

		if ((workshopDetails == null) || (workshopDetails.size() == 0)) {
			throw new NoDataException(
					"No Data is available for the values entered, Please Enter Any Other Value ");
		}

		workshopDetails = null;
		Log.log(4, "ReportsAction", "workshopReport", "Exited");

		return mapping.findForward("success");
	}

	public ActionForward mliwiseWorkshopReportInput(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		Log.log(4, "ReportsAction", "mliwiseWorkshopReportInput", "Entered");

		DynaActionForm dynaForm = (DynaActionForm) form;
		java.util.Date date = new java.util.Date();
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(date);
		int month = calendar.get(2);
		int day = calendar.get(5);
		month -= 1;
		day += 1;
		calendar.set(2, month);
		calendar.set(5, day);
		java.util.Date prevDate = calendar.getTime();

		GeneralReport generalReport = new GeneralReport();
		generalReport.setDateOfTheDocument26(prevDate);
		generalReport.setDateOfTheDocument27(date);
		BeanUtils.copyProperties(dynaForm, generalReport);

		Log.log(4, "ReportsAction", "mliwiseWorkshopReportInput", "Exited");

		return mapping.findForward("success");
	}

	public ActionForward mliworkshopReport(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		Log.log(4, "ReportsAction", "mliworkshopReport", "Entered");
		ArrayList mliworkshopReports = new ArrayList();
		DynaActionForm dynaForm = (DynaActionForm) form;
		java.sql.Date startDate = null;
		java.sql.Date endDate = null;
		java.util.Date sDate = (java.util.Date) dynaForm
				.get("dateOfTheDocument26");
		String stDate = String.valueOf(sDate);

		if ((stDate == null) || (stDate.equals("")))
			startDate = null;
		else if (stDate != null) {
			startDate = new java.sql.Date(sDate.getTime());
		}
		java.util.Date eDate = (java.util.Date) dynaForm
				.get("dateOfTheDocument27");
		endDate = new java.sql.Date(eDate.getTime());
		mliworkshopReports = this.reportManager.mliworkshopReport(startDate,
				endDate);
		dynaForm.set("danRaised", mliworkshopReports);

		HttpSession session = request.getSession(true);
		session.setAttribute("danRaised", mliworkshopReports);

		if ((mliworkshopReports == null) || (mliworkshopReports.size() == 0)) {
			throw new NoDataException(
					"No Data is available for the values entered, Please Enter Any Other Value ");
		}

		mliworkshopReports = null;
		Log.log(4, "ReportsAction", "mliworkshopReport", "Exited");

		return mapping.findForward("success");
	}

	public ActionForward statewiseWorkshopReportInput(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		Log.log(4, "ReportsAction", "statewiseWorkshopReportInput", "Entered");

		DynaActionForm dynaForm = (DynaActionForm) form;
		java.util.Date date = new java.util.Date();
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(date);
		int month = calendar.get(2);
		int day = calendar.get(5);
		month -= 1;
		day += 1;
		calendar.set(2, month);
		calendar.set(5, day);
		java.util.Date prevDate = calendar.getTime();

		GeneralReport generalReport = new GeneralReport();
		generalReport.setDateOfTheDocument26(prevDate);
		generalReport.setDateOfTheDocument27(date);
		BeanUtils.copyProperties(dynaForm, generalReport);

		Log.log(4, "ReportsAction", "statewiseWorkshopReportInput", "Exited");

		return mapping.findForward("success");
	}

	public ActionForward stateworkshopReport(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		Log.log(4, "ReportsAction", "stateworkshopReport", "Entered");
		ArrayList statewiseWorkshopDtls = new ArrayList();
		DynaActionForm dynaForm = (DynaActionForm) form;
		java.sql.Date startDate = null;
		java.sql.Date endDate = null;
		java.util.Date sDate = (java.util.Date) dynaForm
				.get("dateOfTheDocument26");
		String stDate = String.valueOf(sDate);

		if ((stDate == null) || (stDate.equals("")))
			startDate = null;
		else if (stDate != null) {
			startDate = new java.sql.Date(sDate.getTime());
		}
		java.util.Date eDate = (java.util.Date) dynaForm
				.get("dateOfTheDocument27");
		endDate = new java.sql.Date(eDate.getTime());
		statewiseWorkshopDtls = this.reportManager.stateworkshopReportDetails(
				startDate, endDate);
		dynaForm.set("danRaised", statewiseWorkshopDtls);

		HttpSession session = request.getSession(true);
		session.setAttribute("danRaised", statewiseWorkshopDtls);

		if ((statewiseWorkshopDtls == null)
				|| (statewiseWorkshopDtls.size() == 0)) {
			throw new NoDataException(
					"No Data is available for the values entered, Please Enter Any Other Value ");
		}

		statewiseWorkshopDtls = null;
		Log.log(4, "ReportsAction", "stateworkshopReport", "Exited");

		return mapping.findForward("success");
	}

	public ActionForward asf2011notallocatedSummaryInput(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		Log.log(4, "ReportsAction", "asf2011notallocatedSummaryInput",
				"Entered");

		DynaActionForm dynaForm = (DynaActionForm) form;
		java.util.Date date = new java.util.Date();
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(date);
		int month = calendar.get(2);
		int day = calendar.get(5);
		month -= 1;
		day += 1;
		calendar.set(2, month);
		calendar.set(5, day);
		java.util.Date prevDate = calendar.getTime();

		GeneralReport generalReport = new GeneralReport();
		generalReport.setDateOfTheDocument26(prevDate);
		generalReport.setDateOfTheDocument27(date);
		BeanUtils.copyProperties(dynaForm, generalReport);

		Log.log(4, "ReportsAction", "asf2011notallocatedSummaryInput", "Exited");

		return mapping.findForward("success");
	}

	public ActionForward asf2011notallocatedSummary(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		Log.log(4, "ReportsAction", "asf2011notallocatedSummary", "Entered");
		ArrayList asf2011notallocatedSummaryDtls = new ArrayList();
		DynaActionForm dynaForm = (DynaActionForm) form;
		java.sql.Date startDate = null;
		java.sql.Date endDate = null;
		java.util.Date sDate = (java.util.Date) dynaForm
				.get("dateOfTheDocument26");
		String stDate = String.valueOf(sDate);

		if ((stDate == null) || (stDate.equals("")))
			startDate = null;
		else if (stDate != null) {
			startDate = new java.sql.Date(sDate.getTime());
		}
		java.util.Date eDate = (java.util.Date) dynaForm
				.get("dateOfTheDocument27");
		endDate = new java.sql.Date(eDate.getTime());
		asf2011notallocatedSummaryDtls = this.reportManager
				.asf2011notallocatedSummaryDtl(startDate, endDate);
		dynaForm.set("danRaised", asf2011notallocatedSummaryDtls);

		HttpSession session = request.getSession(true);
		session.setAttribute("danRaised", asf2011notallocatedSummaryDtls);

		if ((asf2011notallocatedSummaryDtls == null)
				|| (asf2011notallocatedSummaryDtls.size() == 0)) {
			throw new NoDataException(
					"No Data is available for the values entered, Please Enter Any Other Value ");
		}

		asf2011notallocatedSummaryDtls = null;
		Log.log(4, "ReportsAction", "asf2011notallocatedSummary", "Exited");

		return mapping.findForward("success");
	}

	public ActionForward agencywiseWorkshopReportInput(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		Log.log(4, "ReportsAction", "agencywiseWorkshopReportInput", "Entered");

		DynaActionForm dynaForm = (DynaActionForm) form;
		java.util.Date date = new java.util.Date();
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(date);
		int month = calendar.get(2);
		int day = calendar.get(5);
		month -= 1;
		day += 1;
		calendar.set(2, month);
		calendar.set(5, day);
		java.util.Date prevDate = calendar.getTime();

		GeneralReport generalReport = new GeneralReport();
		generalReport.setDateOfTheDocument26(prevDate);
		generalReport.setDateOfTheDocument27(date);
		BeanUtils.copyProperties(dynaForm, generalReport);

		Log.log(4, "ReportsAction", "agencywiseWorkshopReportInput", "Exited");

		return mapping.findForward("success");
	}

	public ActionForward agencyworkshopReport(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		Log.log(4, "ReportsAction", "agencyworkshopReport", "Entered");
		ArrayList agencywiseWorkshopDtls = new ArrayList();
		DynaActionForm dynaForm = (DynaActionForm) form;
		java.sql.Date startDate = null;
		java.sql.Date endDate = null;
		java.util.Date sDate = (java.util.Date) dynaForm
				.get("dateOfTheDocument26");
		String stDate = String.valueOf(sDate);

		if ((stDate == null) || (stDate.equals("")))
			startDate = null;
		else if (stDate != null) {
			startDate = new java.sql.Date(sDate.getTime());
		}
		java.util.Date eDate = (java.util.Date) dynaForm
				.get("dateOfTheDocument27");
		endDate = new java.sql.Date(eDate.getTime());
		agencywiseWorkshopDtls = this.reportManager
				.agencyworkshopReportDetails(startDate, endDate);
		dynaForm.set("danRaised", agencywiseWorkshopDtls);

		HttpSession session = request.getSession(true);
		session.setAttribute("danRaised", agencywiseWorkshopDtls);

		if ((agencywiseWorkshopDtls == null)
				|| (agencywiseWorkshopDtls.size() == 0)) {
			throw new NoDataException(
					"No Data is available for the values entered, Please Enter Any Other Value ");
		}

		agencywiseWorkshopDtls = null;
		Log.log(4, "ReportsAction", "agencyworkshopReport", "Exited");

		return mapping.findForward("success");
	}

	public ActionForward schemewiseWorkshopReportInput(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		Log.log(4, "ReportsAction", "schemewiseWorkshopReportInput", "Entered");

		DynaActionForm dynaForm = (DynaActionForm) form;
		java.util.Date date = new java.util.Date();
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(date);
		int month = calendar.get(2);
		int day = calendar.get(5);
		month -= 1;
		day += 1;
		calendar.set(2, month);
		calendar.set(5, day);
		java.util.Date prevDate = calendar.getTime();

		GeneralReport generalReport = new GeneralReport();
		generalReport.setDateOfTheDocument26(prevDate);
		generalReport.setDateOfTheDocument27(date);
		BeanUtils.copyProperties(dynaForm, generalReport);

		Log.log(4, "ReportsAction", "schemewiseWorkshopReportInput", "Exited");

		return mapping.findForward("success");
	}

	public ActionForward schemeworkshopReport(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		Log.log(4, "ReportsAction", "schemeworkshopReport", "Entered");
		ArrayList schemewiseWorkshopDtls = new ArrayList();
		DynaActionForm dynaForm = (DynaActionForm) form;
		java.sql.Date startDate = null;
		java.sql.Date endDate = null;
		java.util.Date sDate = (java.util.Date) dynaForm
				.get("dateOfTheDocument26");
		String stDate = String.valueOf(sDate);

		if ((stDate == null) || (stDate.equals("")))
			startDate = null;
		else if (stDate != null) {
			startDate = new java.sql.Date(sDate.getTime());
		}
		java.util.Date eDate = (java.util.Date) dynaForm
				.get("dateOfTheDocument27");
		endDate = new java.sql.Date(eDate.getTime());
		schemewiseWorkshopDtls = this.reportManager
				.schemeworkshopReportDetails(startDate, endDate);
		dynaForm.set("danRaised", schemewiseWorkshopDtls);

		HttpSession session = request.getSession(true);
		session.setAttribute("danRaised", schemewiseWorkshopDtls);

		if ((schemewiseWorkshopDtls == null)
				|| (schemewiseWorkshopDtls.size() == 0)) {
			throw new NoDataException(
					"No Data is available for the values entered, Please Enter Any Other Value ");
		}

		schemewiseWorkshopDtls = null;
		Log.log(4, "ReportsAction", "schemeworkshopReport", "Exited");

		return mapping.findForward("success");
	}

	public ActionForward stateworkshopReportNew(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		Log.log(4, "ReportsAction", "stateworkshopReportNew", "Entered");
		ArrayList statesWiseReport = new ArrayList();
		DynaActionForm dynaForm = (DynaActionForm) form;
		java.sql.Date startDate = null;
		java.sql.Date endDate = null;
		java.util.Date sDate = (java.util.Date) dynaForm
				.get("dateOfTheDocument26");
		String stDate = String.valueOf(sDate);

		if ((stDate == null) || (stDate.equals("")))
			startDate = null;
		else if (stDate != null) {
			startDate = new java.sql.Date(sDate.getTime());
		}
		java.util.Date eDate = (java.util.Date) dynaForm
				.get("dateOfTheDocument27");
		endDate = new java.sql.Date(eDate.getTime());
		String state = request.getParameter("state");
		request.setAttribute("bankName", state);

		statesWiseReport = this.reportManager.getStatesWorkshopWiseReportNew(
				state.toUpperCase(), startDate, endDate);
		dynaForm.set("statesWiseReport", statesWiseReport);
		if ((statesWiseReport == null) || (statesWiseReport.size() == 0)) {
			throw new NoDataException(
					"No Data is available for the values entered, Please Enter Any Other Value ");
		}

		statesWiseReport = null;
		Log.log(4, "ReportsAction", "stateworkshopReportNew", "Exited");

		return mapping.findForward("success");
	}

	public ActionForward schemeworkshopReportNew(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		Log.log(4, "ReportsAction", "schemeworkshopReportNew", "Entered");
		ArrayList schemeworkshopReport = new ArrayList();
		DynaActionForm dynaForm = (DynaActionForm) form;
		java.sql.Date startDate = null;
		java.sql.Date endDate = null;
		java.util.Date sDate = (java.util.Date) dynaForm
				.get("dateOfTheDocument26");
		String stDate = String.valueOf(sDate);

		if ((stDate == null) || (stDate.equals("")))
			startDate = null;
		else if (stDate != null) {
			startDate = new java.sql.Date(sDate.getTime());
		}
		java.util.Date eDate = (java.util.Date) dynaForm
				.get("dateOfTheDocument27");
		endDate = new java.sql.Date(eDate.getTime());
		String state = request.getParameter("state");
		request.setAttribute("bankName", state);

		schemeworkshopReport = this.reportManager
				.schemeworkshopReportDetailsNew(state.toUpperCase(), startDate,
						endDate);
		dynaForm.set("statesWiseReport", schemeworkshopReport);
		if ((schemeworkshopReport == null)
				|| (schemeworkshopReport.size() == 0)) {
			throw new NoDataException(
					"No Data is available for the values entered, Please Enter Any Other Value ");
		}

		schemeworkshopReport = null;
		Log.log(4, "ReportsAction", "schemeworkshopReportNew", "Exited");

		return mapping.findForward("success");
	}

	public ActionForward districtworkshopReportNew(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		Log.log(4, "ReportsAction", "districtworkshopReportNew", "Entered");
		ArrayList districtworkshopReport = new ArrayList();
		DynaActionForm dynaForm = (DynaActionForm) form;
		java.sql.Date startDate = null;
		java.sql.Date endDate = null;
		java.util.Date sDate = (java.util.Date) dynaForm
				.get("dateOfTheDocument26");
		String stDate = String.valueOf(sDate);

		if ((stDate == null) || (stDate.equals("")))
			startDate = null;
		else if (stDate != null) {
			startDate = new java.sql.Date(sDate.getTime());
		}
		java.util.Date eDate = (java.util.Date) dynaForm
				.get("dateOfTheDocument27");
		endDate = new java.sql.Date(eDate.getTime());
		String state = request.getParameter("state");
		request.setAttribute("bankName", state);

		districtworkshopReport = this.reportManager.districtworkshopReportDtls(
				state.toUpperCase(), startDate, endDate);
		dynaForm.set("statesWiseReport", districtworkshopReport);
		if ((districtworkshopReport == null)
				|| (districtworkshopReport.size() == 0)) {
			throw new NoDataException(
					"No Data is available for the values entered, Please Enter Any Other Value ");
		}

		districtworkshopReport = null;
		Log.log(4, "ReportsAction", "districtworkshopReportNew", "Exited");

		return mapping.findForward("success");
	}

	public ActionForward statemliworkshopReportNew(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		Log.log(4, "ReportsAction", "statemliworkshopReportNew", "Entered");
		ArrayList statemliworkshopReport = new ArrayList();
		DynaActionForm dynaForm = (DynaActionForm) form;
		java.sql.Date startDate = null;
		java.sql.Date endDate = null;
		java.util.Date sDate = (java.util.Date) dynaForm
				.get("dateOfTheDocument26");
		String stDate = String.valueOf(sDate);

		if ((stDate == null) || (stDate.equals("")))
			startDate = null;
		else if (stDate != null) {
			startDate = new java.sql.Date(sDate.getTime());
		}
		java.util.Date eDate = (java.util.Date) dynaForm
				.get("dateOfTheDocument27");
		endDate = new java.sql.Date(eDate.getTime());
		String state = request.getParameter("state");
		request.setAttribute("bankName", state);

		statemliworkshopReport = this.reportManager.statemliworkshopReportDtls(
				state.toUpperCase(), startDate, endDate);
		dynaForm.set("statesWiseReport", statemliworkshopReport);
		if ((statemliworkshopReport == null)
				|| (statemliworkshopReport.size() == 0)) {
			throw new NoDataException(
					"No Data is available for the values entered, Please Enter Any Other Value ");
		}

		statemliworkshopReport = null;
		Log.log(4, "ReportsAction", "statemliworkshopReportNew", "Exited");

		return mapping.findForward("success");
	}

	public ActionForward stateagencyworkshopReportNew(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		Log.log(4, "ReportsAction", "stateagencyworkshopReportNew", "Entered");
		ArrayList stateagencyworkshopReport = new ArrayList();
		DynaActionForm dynaForm = (DynaActionForm) form;
		java.sql.Date startDate = null;
		java.sql.Date endDate = null;
		java.util.Date sDate = (java.util.Date) dynaForm
				.get("dateOfTheDocument26");
		String stDate = String.valueOf(sDate);

		if ((stDate == null) || (stDate.equals("")))
			startDate = null;
		else if (stDate != null) {
			startDate = new java.sql.Date(sDate.getTime());
		}
		java.util.Date eDate = (java.util.Date) dynaForm
				.get("dateOfTheDocument27");
		endDate = new java.sql.Date(eDate.getTime());
		String state = request.getParameter("state");
		request.setAttribute("bankName", state);

		stateagencyworkshopReport = this.reportManager
				.stateagencyworkshopReportDtls(state.toUpperCase(), startDate,
						endDate);
		dynaForm.set("statesWiseReport", stateagencyworkshopReport);
		if ((stateagencyworkshopReport == null)
				|| (stateagencyworkshopReport.size() == 0)) {
			throw new NoDataException(
					"No Data is available for the values entered, Please Enter Any Other Value ");
		}

		stateagencyworkshopReport = null;
		Log.log(4, "ReportsAction", "stateagencyworkshopReportNew", "Exited");

		return mapping.findForward("success");
	}

	public ActionForward stateprogaramworkshopReportNew(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		Log.log(4, "ReportsAction", "stateprogaramworkshopReportNew", "Entered");
		ArrayList stateprogaramworkshopReport = new ArrayList();
		DynaActionForm dynaForm = (DynaActionForm) form;
		java.sql.Date startDate = null;
		java.sql.Date endDate = null;
		java.util.Date sDate = (java.util.Date) dynaForm
				.get("dateOfTheDocument26");
		String stDate = String.valueOf(sDate);

		if ((stDate == null) || (stDate.equals("")))
			startDate = null;
		else if (stDate != null) {
			startDate = new java.sql.Date(sDate.getTime());
		}
		java.util.Date eDate = (java.util.Date) dynaForm
				.get("dateOfTheDocument27");
		endDate = new java.sql.Date(eDate.getTime());
		String state = request.getParameter("state");
		request.setAttribute("bankName", state);

		stateprogaramworkshopReport = this.reportManager
				.stateprogaramworkshopReportDtls(state.toUpperCase(),
						startDate, endDate);
		dynaForm.set("statesWiseReport", stateprogaramworkshopReport);
		if ((stateprogaramworkshopReport == null)
				|| (stateprogaramworkshopReport.size() == 0)) {
			throw new NoDataException(
					"No Data is available for the values entered, Please Enter Any Other Value ");
		}

		stateprogaramworkshopReport = null;
		Log.log(4, "ReportsAction", "stateprogaramworkshopReportNew", "Exited");

		return mapping.findForward("success");
	}

	public ActionForward propagationmliworkshopReportNew(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		Log.log(4, "ReportsAction", "propagationmliworkshopReportNew",
				"Entered");
		ArrayList propagationmliworkshopReport = new ArrayList();
		DynaActionForm dynaForm = (DynaActionForm) form;
		java.sql.Date startDate = null;
		java.sql.Date endDate = null;
		java.util.Date sDate = (java.util.Date) dynaForm
				.get("dateOfTheDocument26");
		String stDate = String.valueOf(sDate);

		if ((stDate == null) || (stDate.equals("")))
			startDate = null;
		else if (stDate != null) {
			startDate = new java.sql.Date(sDate.getTime());
		}
		java.util.Date eDate = (java.util.Date) dynaForm
				.get("dateOfTheDocument27");
		endDate = new java.sql.Date(eDate.getTime());
		String state = request.getParameter("state");
		request.setAttribute("bankName", state);

		propagationmliworkshopReport = this.reportManager
				.propagationmliworkshopReportDtls(state.toUpperCase(),
						startDate, endDate);
		dynaForm.set("statesWiseReport", propagationmliworkshopReport);
		if ((propagationmliworkshopReport == null)
				|| (propagationmliworkshopReport.size() == 0)) {
			throw new NoDataException(
					"No Data is available for the values entered, Please Enter Any Other Value ");
		}

		propagationmliworkshopReport = null;
		Log.log(4, "ReportsAction", "propagationmliworkshopReportNew", "Exited");

		return mapping.findForward("success");
	}

	public ActionForward propagationstateworkshopReportNew(
			ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		Log.log(4, "ReportsAction", "propagationstateworkshopReportNew",
				"Entered");
		ArrayList propagationstateworkshopReport = new ArrayList();
		DynaActionForm dynaForm = (DynaActionForm) form;
		java.sql.Date startDate = null;
		java.sql.Date endDate = null;
		java.util.Date sDate = (java.util.Date) dynaForm
				.get("dateOfTheDocument26");
		String stDate = String.valueOf(sDate);

		if ((stDate == null) || (stDate.equals("")))
			startDate = null;
		else if (stDate != null) {
			startDate = new java.sql.Date(sDate.getTime());
		}
		java.util.Date eDate = (java.util.Date) dynaForm
				.get("dateOfTheDocument27");
		endDate = new java.sql.Date(eDate.getTime());
		String state = request.getParameter("state");
		request.setAttribute("bankName", state);

		propagationstateworkshopReport = this.reportManager
				.propagationstateworkshopReportDtls(state.toUpperCase(),
						startDate, endDate);
		dynaForm.set("statesWiseReport", propagationstateworkshopReport);
		if ((propagationstateworkshopReport == null)
				|| (propagationstateworkshopReport.size() == 0)) {
			throw new NoDataException(
					"No Data is available for the values entered, Please Enter Any Other Value ");
		}

		propagationstateworkshopReport = null;
		Log.log(4, "ReportsAction", "propagationstateworkshopReportNew",
				"Exited");

		return mapping.findForward("success");
	}

	public ActionForward propagationagencyworkshopReportNew(
			ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		Log.log(4, "ReportsAction", "propagationagencyworkshopReportNew",
				"Entered");
		ArrayList propagationagencyworkshopReport = new ArrayList();
		DynaActionForm dynaForm = (DynaActionForm) form;
		java.sql.Date startDate = null;
		java.sql.Date endDate = null;
		java.util.Date sDate = (java.util.Date) dynaForm
				.get("dateOfTheDocument26");
		String stDate = String.valueOf(sDate);

		if ((stDate == null) || (stDate.equals("")))
			startDate = null;
		else if (stDate != null) {
			startDate = new java.sql.Date(sDate.getTime());
		}
		java.util.Date eDate = (java.util.Date) dynaForm
				.get("dateOfTheDocument27");
		endDate = new java.sql.Date(eDate.getTime());
		String state = request.getParameter("state");
		request.setAttribute("bankName", state);

		propagationagencyworkshopReport = this.reportManager
				.propagationagencyworkshopReportDtls(state.toUpperCase(),
						startDate, endDate);
		dynaForm.set("statesWiseReport", propagationagencyworkshopReport);
		if ((propagationagencyworkshopReport == null)
				|| (propagationagencyworkshopReport.size() == 0)) {
			throw new NoDataException(
					"No Data is available for the values entered, Please Enter Any Other Value ");
		}

		propagationagencyworkshopReport = null;
		Log.log(4, "ReportsAction", "propagationagencyworkshopReportNew",
				"Exited");

		return mapping.findForward("success");
	}

	public ActionForward asf2011notallocatedSummaryDtls(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		Log.log(4, "ReportsAction", "asf2011notallocatedSummaryDtls", "Entered");
		ArrayList asf2011notallocatedSummaryDetails = new ArrayList();
		DynaActionForm dynaForm = (DynaActionForm) form;
		java.sql.Date startDate = null;
		java.sql.Date endDate = null;
		java.util.Date sDate = (java.util.Date) dynaForm
				.get("dateOfTheDocument26");
		String stDate = String.valueOf(sDate);

		String mliAddress = null;

		if ((stDate == null) || (stDate.equals("")))
			startDate = null;
		else if (stDate != null) {
			startDate = new java.sql.Date(sDate.getTime());
		}
		java.util.Date eDate = (java.util.Date) dynaForm
				.get("dateOfTheDocument27");
		endDate = new java.sql.Date(eDate.getTime());
		String state = request.getParameter("state");
		request.setAttribute("bankName", state);

		asf2011notallocatedSummaryDetails = this.reportManager
				.unitwiseasf2011notallocatedSummaryDtls(state.toUpperCase(),
						startDate, endDate);
		mliAddress = this.reportManager.getMLIAddressforMemberId(state
				.toUpperCase());
		dynaForm.set("statesWiseReport", asf2011notallocatedSummaryDetails);
		dynaForm.set("ssiDetails", mliAddress);
		if ((asf2011notallocatedSummaryDetails == null)
				|| (asf2011notallocatedSummaryDetails.size() == 0)) {
			throw new NoDataException(
					"No Data is available for the values entered, Please Enter Any Other Value ");
		}

		asf2011notallocatedSummaryDetails = null;
		mliAddress = null;
		Log.log(4, "ReportsAction", "asf2011notallocatedSummaryDtls", "Exited");

		return mapping.findForward("success");
	}

	public ActionForward agencystateworkshopReportNew(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		Log.log(4, "ReportsAction", "agencystateworkshopReportNew", "Entered");
		ArrayList agencystateworkshopReport = new ArrayList();
		DynaActionForm dynaForm = (DynaActionForm) form;
		java.sql.Date startDate = null;
		java.sql.Date endDate = null;
		java.util.Date sDate = (java.util.Date) dynaForm
				.get("dateOfTheDocument26");
		String stDate = String.valueOf(sDate);

		if ((stDate == null) || (stDate.equals("")))
			startDate = null;
		else if (stDate != null) {
			startDate = new java.sql.Date(sDate.getTime());
		}
		java.util.Date eDate = (java.util.Date) dynaForm
				.get("dateOfTheDocument27");
		endDate = new java.sql.Date(eDate.getTime());
		String state = request.getParameter("state");
		request.setAttribute("bankName", state);

		agencystateworkshopReport = this.reportManager
				.agencystateworkshopReportDtls(state.toUpperCase(), startDate,
						endDate);
		dynaForm.set("statesWiseReport", agencystateworkshopReport);
		if ((agencystateworkshopReport == null)
				|| (agencystateworkshopReport.size() == 0)) {
			throw new NoDataException(
					"No Data is available for the values entered, Please Enter Any Other Value ");
		}

		agencystateworkshopReport = null;
		Log.log(4, "ReportsAction", "agencystateworkshopReportNew", "Exited");

		return mapping.findForward("success");
	}

	public ActionForward agencypropagationworkshopReportNew(
			ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		Log.log(4, "ReportsAction", "agencypropagationworkshopReportNew",
				"Entered");
		ArrayList agencypropagationworkshopReport = new ArrayList();
		DynaActionForm dynaForm = (DynaActionForm) form;
		java.sql.Date startDate = null;
		java.sql.Date endDate = null;
		java.util.Date sDate = (java.util.Date) dynaForm
				.get("dateOfTheDocument26");
		String stDate = String.valueOf(sDate);

		if ((stDate == null) || (stDate.equals("")))
			startDate = null;
		else if (stDate != null) {
			startDate = new java.sql.Date(sDate.getTime());
		}
		java.util.Date eDate = (java.util.Date) dynaForm
				.get("dateOfTheDocument27");
		endDate = new java.sql.Date(eDate.getTime());
		String state = request.getParameter("state");
		request.setAttribute("bankName", state);

		agencypropagationworkshopReport = this.reportManager
				.agencypropagationworkshopReportDtls(state.toUpperCase(),
						startDate, endDate);
		dynaForm.set("statesWiseReport", agencypropagationworkshopReport);
		if ((agencypropagationworkshopReport == null)
				|| (agencypropagationworkshopReport.size() == 0)) {
			throw new NoDataException(
					"No Data is available for the values entered, Please Enter Any Other Value ");
		}

		agencypropagationworkshopReport = null;
		Log.log(4, "ReportsAction", "agencypropagationworkshopReportNew",
				"Exited");

		return mapping.findForward("success");
	}

	public ActionForward inwardReportInput(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		Log.log(4, "ReportsAction", "inwardReportInput", "Entered");

		DynaActionForm dynaForm = (DynaActionForm) form;
		java.util.Date date = new java.util.Date();
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(date);
		int month = calendar.get(2);
		int day = calendar.get(5);
		month -= 1;
		day += 1;
		calendar.set(2, month);
		calendar.set(5, day);
		java.util.Date prevDate = calendar.getTime();

		GeneralReport generalReport = new GeneralReport();
		generalReport.setDateOfTheDocument26(prevDate);
		generalReport.setDateOfTheDocument27(date);
		BeanUtils.copyProperties(dynaForm, generalReport);

		Log.log(4, "ReportsAction", "inwardReportInput", "Exited");

		return mapping.findForward("success");
	}

	public ActionForward agencymliworkshopReportNew(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		Log.log(4, "ReportsAction", "agencymliworkshopReportNew", "Entered");
		ArrayList agencymliworkshopReport = new ArrayList();
		DynaActionForm dynaForm = (DynaActionForm) form;
		java.sql.Date startDate = null;
		java.sql.Date endDate = null;
		java.util.Date sDate = (java.util.Date) dynaForm
				.get("dateOfTheDocument26");
		String stDate = String.valueOf(sDate);

		if ((stDate == null) || (stDate.equals("")))
			startDate = null;
		else if (stDate != null) {
			startDate = new java.sql.Date(sDate.getTime());
		}
		java.util.Date eDate = (java.util.Date) dynaForm
				.get("dateOfTheDocument27");
		endDate = new java.sql.Date(eDate.getTime());
		String state = request.getParameter("state");
		request.setAttribute("bankName", state);

		agencymliworkshopReport = this.reportManager
				.agencymliworkshopReportDtls(state.toUpperCase(), startDate,
						endDate);
		dynaForm.set("statesWiseReport", agencymliworkshopReport);
		if ((agencymliworkshopReport == null)
				|| (agencymliworkshopReport.size() == 0)) {
			throw new NoDataException(
					"No Data is available for the values entered, Please Enter Any Other Value ");
		}

		agencymliworkshopReport = null;
		Log.log(4, "ReportsAction", "agencymliworkshopReportNew", "Exited");

		return mapping.findForward("success");
	}

	public ActionForward inwardReport(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		Log.log(4, "ReportsAction", "inwardReport", "Entered");
		ArrayList inward = new ArrayList();
		DynaActionForm dynaForm = (DynaActionForm) form;
		java.sql.Date startDate = null;
		java.sql.Date endDate = null;
		java.util.Date sDate = (java.util.Date) dynaForm
				.get("dateOfTheDocument26");
		String stDate = String.valueOf(sDate);

		if ((stDate == null) || (stDate.equals("")))
			startDate = null;
		else if (stDate != null) {
			startDate = new java.sql.Date(sDate.getTime());
		}
		java.util.Date eDate = (java.util.Date) dynaForm
				.get("dateOfTheDocument27");
		endDate = new java.sql.Date(eDate.getTime());
		inward = this.reportManager.inwardReport(startDate, endDate);
		dynaForm.set("danRaised", inward);

		HttpSession session = request.getSession(true);
		session.setAttribute("danRaised", inward);

		if ((inward == null) || (inward.size() == 0)) {
			throw new NoDataException(
					"No Data is available for the values entered, Please Enter Any Other Value ");
		}

		inward = null;
		Log.log(4, "ReportsAction", "inwardReport", "Exited");

		return mapping.findForward("success");
	}

	public ActionForward npaReportInput(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		Log.log(4, "ReportsAction", "npaReportInput", "Entered");

		DynaActionForm dynaForm = (DynaActionForm) form;

		User user = getUserInformation(request);
		String bankId = user.getBankId();
		String zoneId = user.getZoneId();
		String branchId = user.getBranchId();
		String memberId = bankId.concat(zoneId).concat(branchId);

		java.util.Date date = new java.util.Date();
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(date);
		int month = calendar.get(2);
		int day = calendar.get(5);
		month -= 1;
		day += 1;
		calendar.set(2, month);
		calendar.set(5, day);
		java.util.Date prevDate = calendar.getTime();

		GeneralReport generalReport = new GeneralReport();
		generalReport.setDateOfTheDocument26(prevDate);
		generalReport.setDateOfTheDocument27(date);
		BeanUtils.copyProperties(dynaForm, generalReport);
		dynaForm.set("bankId", bankId);
		if (bankId.equals("0000")) {
			memberId = "";
		}
		dynaForm.set("memberId", memberId);
		Log.log(4, "ReportsAction", "npaReportInput", "Exited");

		return mapping.findForward("success");
	}

	public ActionForward npaReport(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		Log.log(4, "ReportsAction", "npaReport", "Entered");
		ArrayList npaDetails = new ArrayList();
		DynaActionForm dynaForm = (DynaActionForm) form;
		java.sql.Date startDate = null;
		java.sql.Date endDate = null;
		java.util.Date sDate = (java.util.Date) dynaForm
				.get("dateOfTheDocument26");
		String stDate = String.valueOf(sDate);

		if ((stDate == null) || (stDate.equals("")))
			startDate = null;
		else if (stDate != null) {
			startDate = new java.sql.Date(sDate.getTime());
		}
		java.util.Date eDate = (java.util.Date) dynaForm
				.get("dateOfTheDocument27");
		endDate = new java.sql.Date(eDate.getTime());

		String id = (String) dynaForm.get("memberId");

		npaDetails = this.reportManager.npaReport(startDate, endDate, id);
		dynaForm.set("danRaised", npaDetails);

		if ((npaDetails == null) || (npaDetails.size() == 0)) {
			throw new NoDataException(
					"No Data is available for the values entered, Please Enter Any Other Value ");
		}

		npaDetails = null;
		Log.log(4, "ReportsAction", "npaReport", "Exited");

		return mapping.findForward("success");
	}

	public ActionForward ddDepositReportInput(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		Log.log(4, "ReportsAction", "ddDepositReportInput", "Entered");

		DynaActionForm dynaForm = (DynaActionForm) form;
		java.util.Date date = new java.util.Date();
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(date);
		int month = calendar.get(2);
		int day = calendar.get(5);
		month -= 1;
		day += 1;
		calendar.set(2, month);
		calendar.set(5, day);
		java.util.Date prevDate = calendar.getTime();

		GeneralReport generalReport = new GeneralReport();
		generalReport.setDateOfTheDocument26(prevDate);
		generalReport.setDateOfTheDocument27(date);
		BeanUtils.copyProperties(dynaForm, generalReport);

		Log.log(4, "ReportsAction", "ddDepositReportInput", "Exited");

		return mapping.findForward("success");
	}

	public ActionForward ddDepositReport(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		Log.log(4, "ReportsAction", "ddDepositReport", "Entered");
		ArrayList dddepositedDetails = new ArrayList();
		DynaActionForm dynaForm = (DynaActionForm) form;
		java.sql.Date startDate = null;
		java.sql.Date endDate = null;
		java.util.Date sDate = (java.util.Date) dynaForm
				.get("dateOfTheDocument26");
		String stDate = String.valueOf(sDate);

		if ((stDate == null) || (stDate.equals("")))
			startDate = null;
		else if (stDate != null) {
			startDate = new java.sql.Date(sDate.getTime());
		}
		java.util.Date eDate = (java.util.Date) dynaForm
				.get("dateOfTheDocument27");
		endDate = new java.sql.Date(eDate.getTime());
		dddepositedDetails = this.reportManager.ddDepositReport(startDate,
				endDate);
		dynaForm.set("danRaised", dddepositedDetails);

		if ((dddepositedDetails == null) || (dddepositedDetails.size() == 0)) {
			throw new NoDataException(
					"No Data is available for the values entered, Please Enter Any Other Value ");
		}

		dddepositedDetails = null;
		Log.log(4, "ReportsAction", "ddDepositReport", "Exited");

		return mapping.findForward("success");
	}

	public ActionForward dcHandicraftInput(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		Log.log(4, "ReportsAction", "dcHandicraftInput", "Entered");

		DynaActionForm dynaForm = (DynaActionForm) form;
		dynaForm.initialize(mapping);
		User user = getUserInformation(request);
		String bankId = user.getBankId();
		String zoneId = user.getZoneId();
		String branchId = user.getBranchId();
		String memberId = bankId.concat(zoneId).concat(branchId);
		java.util.Date date = new java.util.Date();
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(date);
		int month = calendar.get(2);
		int day = calendar.get(5);
		month -= 1;
		day += 1;
		calendar.set(2, month);
		calendar.set(5, day);
		java.util.Date prevDate = calendar.getTime();
		GeneralReport generalReport = new GeneralReport();
		generalReport.setDateOfTheDocument26(prevDate);
		generalReport.setDateOfTheDocument27(date);
		BeanUtils.copyProperties(dynaForm, generalReport);
		dynaForm.set("bankId", bankId);
		if (bankId.equals("0000")) {
			memberId = "";
		}
		dynaForm.set("memberId", memberId);

		Log.log(4, "ReportsAction", "dcHandicraftInput", "Exited");

		return mapping.findForward("success");
	}

	public ActionForward dcHandicraftReport(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		Log.log(4, "ReportsAction", "dcHandicraftReport", "Entered");
		ArrayList dcHandicraftReportDetails = new ArrayList();
		DynaActionForm dynaForm = (DynaActionForm) form;
		java.sql.Date startDate = null;
		java.sql.Date endDate = null;
		java.util.Date sDate = (java.util.Date) dynaForm
				.get("dateOfTheDocument26");
		String stDate = String.valueOf(sDate);

		if ((stDate == null) || (stDate.equals("")))
			startDate = null;
		else if (stDate != null) {
			startDate = new java.sql.Date(sDate.getTime());
		}
		java.util.Date eDate = (java.util.Date) dynaForm
				.get("dateOfTheDocument27");
		endDate = new java.sql.Date(eDate.getTime());

		String id = (String) dynaForm.get("memberId");

		dcHandicraftReportDetails = this.reportManager.dcHandicraftReport(
				startDate, endDate, id);
		dynaForm.set("danRaised", dcHandicraftReportDetails);

		if ((dcHandicraftReportDetails == null)
				|| (dcHandicraftReportDetails.size() == 0)) {
			throw new NoDataException(
					"No Data is available for the values entered, Please Enter Any Other Value ");
		}

		dcHandicraftReportDetails = null;
		Log.log(4, "ReportsAction", "dcHandicraftReport", "Exited");

		return mapping.findForward("success");
	}

	public ActionForward dcHandloomInput(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		Log.log(4, "ReportsAction", "dcHandicraftInput", "Entered");

		DynaActionForm dynaForm = (DynaActionForm) form;
		dynaForm.initialize(mapping);
		User user = getUserInformation(request);
		String bankId = user.getBankId();
		String zoneId = user.getZoneId();
		String branchId = user.getBranchId();
		String memberId = bankId.concat(zoneId).concat(branchId);
		java.util.Date date = new java.util.Date();
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(date);
		int month = calendar.get(2);
		int day = calendar.get(5);
		month -= 1;
		day += 1;
		calendar.set(2, month);
		calendar.set(5, day);
		java.util.Date prevDate = calendar.getTime();
		GeneralReport generalReport = new GeneralReport();
		generalReport.setDateOfTheDocument26(prevDate);
		generalReport.setDateOfTheDocument27(date);
		BeanUtils.copyProperties(dynaForm, generalReport);
		dynaForm.set("bankId", bankId);
		if (bankId.equals("0000")) {
			memberId = "";
		}
		dynaForm.set("memberId", memberId);

		Log.log(4, "ReportsAction", "dcHandicraftInput", "Exited");

		return mapping.findForward("success");
	}

	public ActionForward dcHandloomReport(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		Log.log(4, "ReportsAction", "dcHandloomReport", "Entered");
		ArrayList dcHandloomReportDetails = new ArrayList();
		DynaActionForm dynaForm = (DynaActionForm) form;
		java.sql.Date startDate = null;
		java.sql.Date endDate = null;
		java.util.Date sDate = (java.util.Date) dynaForm
				.get("dateOfTheDocument26");
		String stDate = String.valueOf(sDate);

		if ((stDate == null) || (stDate.equals("")))
			startDate = null;
		else if (stDate != null) {
			startDate = new java.sql.Date(sDate.getTime());
		}
		java.util.Date eDate = (java.util.Date) dynaForm
				.get("dateOfTheDocument27");
		endDate = new java.sql.Date(eDate.getTime());

		String id = (String) dynaForm.get("memberId");

		dcHandloomReportDetails = this.reportManager.dcHandloomReport(
				startDate, endDate, id);
		dynaForm.set("danRaised", dcHandloomReportDetails);

		if ((dcHandloomReportDetails == null)
				|| (dcHandloomReportDetails.size() == 0)) {
			throw new NoDataException(
					"No Data is available for the values entered, Please Enter Any Other Value ");
		}

		dcHandloomReportDetails = null;
		Log.log(4, "ReportsAction", "dcHandicraftReport", "Exited");

		return mapping.findForward("success");
	}

	public ActionForward paymentReportInput(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		Log.log(4, "ReportsAction", "paymentReportInput", "Entered");

		DynaActionForm dynaForm = (DynaActionForm) form;
		User user = getUserInformation(request);
		String bankId = user.getBankId();
		String zoneId = user.getZoneId();
		String branchId = user.getBranchId();
		String memberId = bankId.concat(zoneId).concat(branchId);

		java.util.Date date = new java.util.Date();
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(date);
		int month = calendar.get(2);
		int day = calendar.get(5);
		month -= 1;
		day += 1;
		calendar.set(2, month);
		calendar.set(5, day);
		java.util.Date prevDate = calendar.getTime();

		GeneralReport generalReport = new GeneralReport();
		generalReport.setDateOfTheDocument16(prevDate);
		generalReport.setDateOfTheDocument17(date);
		BeanUtils.copyProperties(dynaForm, generalReport);

		dynaForm.set("bankId", bankId);
		if (bankId.equals("0000")) {
			memberId = "";
		}
		dynaForm.set("memberId", memberId);

		Log.log(4, "ReportsAction", "paymentReportInput", "Exited");

		return mapping.findForward("success");
	}

	public ActionForward dailypaymentReportInput(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		Log.log(4, "ReportsAction", "dailypaymentReportInput", "Entered");

		DynaActionForm dynaForm = (DynaActionForm) form;
		User user = getUserInformation(request);
		String bankId = user.getBankId();
		String zoneId = user.getZoneId();
		String branchId = user.getBranchId();
		String memberId = bankId.concat(zoneId).concat(branchId);

		java.util.Date date = new java.util.Date();
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(date);
		int month = calendar.get(2);
		int day = calendar.get(5);
		month -= 1;
		day += 1;
		calendar.set(2, month);
		calendar.set(5, day);
		java.util.Date prevDate = calendar.getTime();

		GeneralReport generalReport = new GeneralReport();
		generalReport.setDateOfTheDocument16(prevDate);
		generalReport.setDateOfTheDocument17(date);
		BeanUtils.copyProperties(dynaForm, generalReport);

		dynaForm.set("bankId", bankId);
		if (bankId.equals("0000")) {
			memberId = "";
		}
		dynaForm.set("memberId", memberId);

		Log.log(4, "ReportsAction", "dailypaymentReportInput", "Exited");

		return mapping.findForward("success");
	}

	public ActionForward dailydchpaymentReportInput(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		Log.log(4, "ReportsAction", "dailydchpaymentReportInput", "Entered");

		DynaActionForm dynaForm = (DynaActionForm) form;
		User user = getUserInformation(request);
		String bankId = user.getBankId();
		String zoneId = user.getZoneId();
		String branchId = user.getBranchId();
		String memberId = bankId.concat(zoneId).concat(branchId);

		java.util.Date date = new java.util.Date();
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(date);
		int month = calendar.get(2);
		int day = calendar.get(5);
		month -= 1;
		day += 1;
		calendar.set(2, month);
		calendar.set(5, day);
		java.util.Date prevDate = calendar.getTime();

		GeneralReport generalReport = new GeneralReport();
		generalReport.setDateOfTheDocument16(prevDate);
		generalReport.setDateOfTheDocument17(date);
		BeanUtils.copyProperties(dynaForm, generalReport);

		dynaForm.set("bankId", bankId);
		if (bankId.equals("0000")) {
			memberId = "";
		}
		dynaForm.set("memberId", memberId);

		Log.log(4, "ReportsAction", "dailydchpaymentReportInput", "Exited");

		return mapping.findForward("success");
	}

	public ActionForward dailyasfpaymentReportInput(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		Log.log(4, "ReportsAction", "dailyasfpaymentReportInput", "Entered");

		DynaActionForm dynaForm = (DynaActionForm) form;
		User user = getUserInformation(request);
		String bankId = user.getBankId();
		String zoneId = user.getZoneId();
		String branchId = user.getBranchId();
		String memberId = bankId.concat(zoneId).concat(branchId);

		java.util.Date date = new java.util.Date();
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(date);
		int month = calendar.get(2);
		int day = calendar.get(5);
		month -= 1;
		day += 1;
		calendar.set(2, month);
		calendar.set(5, day);
		java.util.Date prevDate = calendar.getTime();

		GeneralReport generalReport = new GeneralReport();
		generalReport.setDateOfTheDocument16(prevDate);
		generalReport.setDateOfTheDocument17(date);
		BeanUtils.copyProperties(dynaForm, generalReport);

		dynaForm.set("bankId", bankId);
		if (bankId.equals("0000")) {
			memberId = "";
		}
		dynaForm.set("memberId", memberId);

		Log.log(4, "ReportsAction", "dailyasfpaymentReportInput", "Exited");

		return mapping.findForward("success");
	}

	public ActionForward asfpaymentReportInput(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		Log.log(4, "ReportsAction", "asfpaymentReportInput", "Entered");

		DynaActionForm dynaForm = (DynaActionForm) form;
		User user = getUserInformation(request);
		String bankId = user.getBankId();
		String zoneId = user.getZoneId();
		String branchId = user.getBranchId();
		String memberId = bankId.concat(zoneId).concat(branchId);

		java.util.Date date = new java.util.Date();
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(date);
		int month = calendar.get(2);
		int day = calendar.get(5);
		month -= 1;
		day += 1;
		calendar.set(2, month);
		calendar.set(5, day);
		java.util.Date prevDate = calendar.getTime();

		GeneralReport generalReport = new GeneralReport();
		generalReport.setDateOfTheDocument16(prevDate);
		generalReport.setDateOfTheDocument17(date);
		BeanUtils.copyProperties(dynaForm, generalReport);

		dynaForm.set("bankId", bankId);
		if (bankId.equals("0000")) {
			memberId = "";
		}
		dynaForm.set("memberId", memberId);

		Log.log(4, "ReportsAction", "asfpaymentReportInput", "Exited");

		return mapping.findForward("success");
	}

	public ActionForward asfallocatedpaymentReportInput(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		Log.log(4, "ReportsAction", "asfallocatedpaymentReportInput", "Entered");

		DynaActionForm dynaForm = (DynaActionForm) form;
		User user = getUserInformation(request);
		String bankId = user.getBankId();
		String zoneId = user.getZoneId();
		String branchId = user.getBranchId();
		String memberId = bankId.concat(zoneId).concat(branchId);

		java.util.Date date = new java.util.Date();
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(date);
		int month = calendar.get(2);
		int day = calendar.get(5);
		month -= 1;
		day += 1;
		calendar.set(2, month);
		calendar.set(5, day);
		java.util.Date prevDate = calendar.getTime();

		GeneralReport generalReport = new GeneralReport();
		generalReport.setDateOfTheDocument16(prevDate);
		generalReport.setDateOfTheDocument17(date);
		BeanUtils.copyProperties(dynaForm, generalReport);

		dynaForm.set("bankId", bankId);
		if (bankId.equals("0000")) {
			memberId = "";
		}
		dynaForm.set("memberId", memberId);

		Log.log(4, "ReportsAction", "asfallocatedpaymentReportInput", "Exited");

		return mapping.findForward("success");
	}

	public ActionForward gfallocatedpaymentReportInput(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		Log.log(4, "ReportsAction", "gfallocatedpaymentReportInput", "Entered");

		DynaActionForm dynaForm = (DynaActionForm) form;
		User user = getUserInformation(request);
		String bankId = user.getBankId();
		String zoneId = user.getZoneId();
		String branchId = user.getBranchId();
		String memberId = bankId.concat(zoneId).concat(branchId);

		java.util.Date date = new java.util.Date();
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(date);
		int month = calendar.get(2);
		int day = calendar.get(5);
		month -= 1;
		day += 1;
		calendar.set(2, month);
		calendar.set(5, day);
		java.util.Date prevDate = calendar.getTime();

		GeneralReport generalReport = new GeneralReport();
		generalReport.setDateOfTheDocument16(prevDate);
		generalReport.setDateOfTheDocument17(date);
		BeanUtils.copyProperties(dynaForm, generalReport);

		dynaForm.set("bankId", bankId);
		if (bankId.equals("0000")) {
			memberId = "";
		}
		dynaForm.set("memberId", memberId);

		Log.log(4, "ReportsAction", "gfallocatedpaymentReportInput", "Exited");

		return mapping.findForward("success");
	}

	public ActionForward disbursementReportInput(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		Log.log(4, "ReportsAction", "disbursementReportInput", "Entered");

		DynaActionForm dynaForm = (DynaActionForm) form;
		java.util.Date date = new java.util.Date();
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(date);
		int month = calendar.get(2);
		int day = calendar.get(5);
		month -= 1;
		day += 1;
		calendar.set(2, month);
		calendar.set(5, day);
		java.util.Date prevDate = calendar.getTime();

		GeneralReport generalReport = new GeneralReport();
		generalReport.setDateOfTheDocument18(prevDate);
		generalReport.setDateOfTheDocument19(date);
		BeanUtils.copyProperties(dynaForm, generalReport);

		User user = getUserInformation(request);
		String bankId = user.getBankId();
		String zoneId = user.getZoneId();
		String branchId = user.getBranchId();
		String memberId = bankId.concat(zoneId).concat(branchId);

		dynaForm.set("bankId", bankId);

		if (bankId.equals("0000")) {
			memberId = "";
		}
		dynaForm.set("memberId", memberId);

		Log.log(4, "ReportsAction", "disbursementReportInput", "Exited");

		return mapping.findForward("success");
	}

	// rajuk

	public ActionForward claimRecoveryReportInput(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		Log.log(4, "ReportsAction", "claimRecoveryReportInput", "Entered");
	
		//System.out.println("entereddd==");
										
		DynaActionForm dynaForm = (DynaActionForm) form;
		java.util.Date date = new java.util.Date();
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(date);
		int month = calendar.get(2);
		int day = calendar.get(5);
		month -= 1;
		day += 1;
		calendar.set(2, month);
		calendar.set(5, day);
		java.util.Date prevDate = calendar.getTime();

		GeneralReport generalReport = new GeneralReport();
		generalReport.setDateOfTheDocument18(prevDate);
		generalReport.setDateOfTheDocument19(date);
		BeanUtils.copyProperties(dynaForm, generalReport);

		User user = getUserInformation(request);
		String bankId = user.getBankId();
		String zoneId = user.getZoneId();
		String branchId = user.getBranchId();
		String memberId = bankId.concat(zoneId).concat(branchId);

		dynaForm.set("bankId", bankId);

		if (bankId.equals("0000")) {
			memberId = "";
		}
		dynaForm.set("memberId", memberId);
		
		
		

		return mapping.findForward("success");
	}
	// DKR RCV REPORT	
	public ActionForward claimRecoveryReport(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
	
	DynaActionForm dynaForm = (DynaActionForm) form;
	java.sql.Date startDate = null;
	java.sql.Date endDate = null;
	java.util.Date sDate = null;
	java.util.Date eDate = null;
	sDate = (java.util.Date) dynaForm.get("dateOfTheDocument18");	
	//System.out.println("sDate:::::::::::::::::::::"+sDate);
	String stDate = String.valueOf(sDate);	
	if ((stDate == null) || (stDate.equals("")))
		startDate = null;
	else if (stDate != null)
		startDate = new java.sql.Date(sDate.getTime());	
	
	eDate = (java.util.Date) dynaForm.get("dateOfTheDocument19");
	endDate = new java.sql.Date(eDate.getTime());
	String id = (String) dynaForm.get("memberId");
	//memberids = claimsProcessor.getAllMemberIds();
	if((endDate==null && eDate==null) && id.equals(""))
	{
		throw new NoDataException("From date and To Date cann't be blank.");     
	}
    
	 if((null!=endDate && null!=eDate) && !id.equals("")) {
	    ArrayList recoveryDisbursement = this.reportManager.getClaimRecoveryReport(sDate,eDate, id);	 
		dynaForm.set("recoveryDisbursement", recoveryDisbursement);
		if ((recoveryDisbursement == null) || (recoveryDisbursement.size() == 0)) {
			throw new NoDataException(
					"Data not available for this existing Date, Please select any other Date ");
		}	
	}
		
	return mapping.findForward("success");
	
	}

	//deepak

	public ActionForward sanctionedApplicationReportInput(
			ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		Log.log(4, "ReportsAction", "sanctionedApplicationReportInput",
				"Entered");
		DynaActionForm dynaForm = (DynaActionForm) form;

		java.util.Date date = new java.util.Date();
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(date);
		int month = calendar.get(2);
		int day = calendar.get(5);
		month -= 1;
		day += 1;
		calendar.set(2, month);
		calendar.set(5, day);
		java.util.Date prevDate = calendar.getTime();

		GeneralReport generalReport = new GeneralReport();
		generalReport.setDateOfTheDocument(prevDate);
		generalReport.setDateOfTheDocument1(date);
		BeanUtils.copyProperties(dynaForm, generalReport);

		User user = getUserInformation(request);
		String bankId = user.getBankId();
		String zoneId = user.getZoneId();
		String branchId = user.getBranchId();
		String memberId = bankId.concat(zoneId).concat(branchId);

		dynaForm.set("bankId", bankId);

		if (bankId.equals("0000")) {
			memberId = "";
		}
		dynaForm.set("memberId", memberId);

		Log.log(4, "ReportsAction", "sanctionedApplicationReportInput",
				"Exited");

		return mapping.findForward("success");
	}

	public ActionForward danReportInput(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		Log.log(4, "ReportsAction", "danReportInput", "Entered");

		DynaActionForm dynaForm = (DynaActionForm) form;
		java.util.Date date = new java.util.Date();
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(date);
		int month = calendar.get(2);
		int day = calendar.get(5);
		month -= 1;
		day += 1;
		calendar.set(2, month);
		calendar.set(5, day);
		java.util.Date prevDate = calendar.getTime();

		GeneralReport generalReport = new GeneralReport();
		generalReport.setDateOfTheDocument20(prevDate);
		generalReport.setDateOfTheDocument21(date);

		dynaForm.set("memberId", "");
		dynaForm.set("ssi", "");

		BeanUtils.copyProperties(dynaForm, generalReport);

		User user = getUserInformation(request);
		String bankId = user.getBankId();
		String zoneId = user.getZoneId();
		String branchId = user.getBranchId();
		String memberId = bankId.concat(zoneId).concat(branchId);

		dynaForm.set("bankId", bankId);

		if (bankId.equals("0000")) {
			memberId = "";
		}
		dynaForm.set("memberId", memberId);

		Log.log(4, "ReportsAction", "danReportInput", "Exited");

		return mapping.findForward("success");
	}

	public ActionForward gfdanReportInput(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		Log.log(4, "ReportsAction", "gfdanReportInput", "Entered");

		DynaActionForm dynaForm = (DynaActionForm) form;
		java.util.Date date = new java.util.Date();
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(date);
		int month = calendar.get(2);
		int day = calendar.get(5);
		month -= 1;
		day += 1;
		calendar.set(2, month);
		calendar.set(5, day);
		java.util.Date prevDate = calendar.getTime();

		GeneralReport generalReport = new GeneralReport();
		generalReport.setDateOfTheDocument20(prevDate);
		generalReport.setDateOfTheDocument21(date);

		dynaForm.set("memberId", "");
		dynaForm.set("ssi", "");

		BeanUtils.copyProperties(dynaForm, generalReport);

		User user = getUserInformation(request);
		String bankId = user.getBankId();
		String zoneId = user.getZoneId();
		String branchId = user.getBranchId();
		String memberId = bankId.concat(zoneId).concat(branchId);

		dynaForm.set("bankId", bankId);

		if (bankId.equals("0000")) {
			memberId = "";
		}
		dynaForm.set("memberId", memberId);

		Log.log(4, "ReportsAction", "gfdanReportInput", "Exited");

		return mapping.findForward("success");
	}

	public ActionForward asfdanReportInput(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		Log.log(4, "ReportsAction", "asfdanReportInput", "Entered");

		DynaActionForm dynaForm = (DynaActionForm) form;
		java.util.Date date = new java.util.Date();
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(date);
		int month = calendar.get(2);
		int day = calendar.get(5);
		month -= 1;
		day += 1;
		calendar.set(2, month);
		calendar.set(5, day);
		java.util.Date prevDate = calendar.getTime();

		GeneralReport generalReport = new GeneralReport();
		generalReport.setDateOfTheDocument20(prevDate);
		generalReport.setDateOfTheDocument21(date);

		dynaForm.set("memberId", "");
		dynaForm.set("ssi", "");

		BeanUtils.copyProperties(dynaForm, generalReport);

		User user = getUserInformation(request);
		String bankId = user.getBankId();
		String zoneId = user.getZoneId();
		String branchId = user.getBranchId();
		String memberId = bankId.concat(zoneId).concat(branchId);

		dynaForm.set("bankId", bankId);

		if (bankId.equals("0000")) {
			memberId = "";
		}
		dynaForm.set("memberId", memberId);

		Log.log(4, "ReportsAction", "asfdanReportInput", "Exited");

		return mapping.findForward("success");
	}

	public ActionForward securitizationReport(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		Log.log(4, "ReportsAction", "securitizationReport", "Entered");
		ArrayList dan = new ArrayList();
		DynaActionForm dynaForm = (DynaActionForm) form;
		java.sql.Date startDate = null;
		java.sql.Date endDate = null;
		java.util.Date sDate = (java.util.Date) dynaForm
				.get("dateOfTheDocument26");
		String stDate = String.valueOf(sDate);

		if ((stDate == null) || (stDate.equals("")))
			startDate = null;
		else if (stDate != null) {
			startDate = new java.sql.Date(sDate.getTime());
		}
		java.util.Date eDate = (java.util.Date) dynaForm
				.get("dateOfTheDocument27");
		endDate = new java.sql.Date(eDate.getTime());
		dan = this.reportManager.securitizationReport(startDate, endDate);
		dynaForm.set("danRaised", dan);

		if ((dan == null) || (dan.size() == 0)) {
			throw new NoDataException(
					"No Data is available for the values entered, Please Enter Any Other Value ");
		}

		dan = null;
		Log.log(4, "ReportsAction", "securitizationReport", "Exited");

		return mapping.findForward("success");
	}

	public ActionForward securitizationReportDetails(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		Log.log(4, "ReportsAction", "securitizationReportDetails", "Exited");
		ArrayList danReport = new ArrayList();
		ArrayList securitizationArray = new ArrayList();
		DynaActionForm dynaForm = (DynaActionForm) form;
		java.sql.Date startDate = null;
		java.sql.Date endDate = null;
		String flag = (String) dynaForm.get("bank");
		java.util.Date sDate = (java.util.Date) dynaForm
				.get("dateOfTheDocument26");
		String stDate = String.valueOf(sDate);
		request.setAttribute("SECURITIZATIONBANK", flag);

		if ((stDate == null) || (stDate.equals(""))) {
			startDate = null;
		} else if (stDate != null) {
			startDate = new java.sql.Date(sDate.getTime());
		}
		java.util.Date eDate = (java.util.Date) dynaForm
				.get("dateOfTheDocument27");
		endDate = new java.sql.Date(eDate.getTime());
		danReport = this.reportManager.securitizationReportDetails(startDate,
				endDate, flag);
		int danReportLength = danReport.size();
		for (int i = 0; i < danReportLength; i++) {
			securitizationArray = (ArrayList) danReport.get(i);
			if (i == 0) {
				dynaForm.set("securitizationReport1", securitizationArray);
			} else if (i == 1) {
				dynaForm.set("securitizationReport2", securitizationArray);
			} else if (i == 2) {
				dynaForm.set("securitizationReport3", securitizationArray);
			} else if (i == 3) {
				dynaForm.set("securitizationReport4", securitizationArray);
			} else if (i == 4) {
				dynaForm.set("securitizationReport5", securitizationArray);
			} else if (i == 5) {
				dynaForm.set("securitizationReport6", securitizationArray);
			}
		}

		if ((danReport == null) || (danReport.size() == 0)) {
			throw new NoDataException(
					"No Data is available for the values entered, Please Enter Any Other Value ");
		}

		danReport = null;
		Log.log(4, "ReportsAction", "securitizationReportDetails", "Exited");

		return mapping.findForward("success");
	}

	public ActionForward securitizationReportDetailsForCgpan(
			ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		Log.log(4, "ReportsAction", "securitizationReportDetailsForCgpan",
				"Entered");
		ApplicationReport dan = new ApplicationReport();
		DynaActionForm dynaForm = (DynaActionForm) form;
		String application = (String) dynaForm.get("number");

		dan = this.reportManager
				.securitizationReportDetailsForCgpan(application);
		dynaForm.set("statusDetails", dan);
		String key = dan.getMemberId();

		request.setAttribute("radioValue", "");
		if ((key == null) || (key.equals(""))) {
			throw new NoDataException(
					"No Data is available for this value, Please Choose Any Other Value ");
		}

		dan = null;
		Log.log(4, "ReportsAction", "securitizationReportDetailsForCgpan",
				"Exited");

		return mapping.findForward("success");
	}

	public ActionForward applicationWiseReport(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		Log.log(4, "ReportsAction", "applicationWiseReport", "Entered");
		ArrayList status = new ArrayList();
		DynaActionForm dynaForm = (DynaActionForm) form;
		java.sql.Date startDate = null;
		java.sql.Date endDate = null;

		java.util.Date sDate = (java.util.Date) dynaForm
				.get("dateOfTheDocument24");
		String stDate = String.valueOf(sDate);
		String application = (String) dynaForm.get("applicationStatus1");

		if (application.equals("generation"))
			request.setAttribute("radioValue", "DAN Generated");
		else if (application.equals("allocation"))
			request.setAttribute("radioValue", "Allocated");
		else if (application.equals("appropriation")) {
			request.setAttribute("radioValue", "Appropriated");
		}
		if ((stDate == null) || (stDate.equals("")))
			startDate = null;
		else if (stDate != null) {
			startDate = new java.sql.Date(sDate.getTime());
		}
		java.util.Date eDate = (java.util.Date) dynaForm
				.get("dateOfTheDocument25");
		endDate = new java.sql.Date(eDate.getTime());
		status = this.reportManager.applicationWiseReport(startDate, endDate,
				application);
		dynaForm.set("danRaised", status);
		request.setAttribute("STARTDATE", dynaForm.get("dateOfTheDocument24"));
		request.setAttribute("ENDDATE", dynaForm.get("dateOfTheDocument25"));
		if ((status == null) || (status.size() == 0)) {
			throw new NoDataException(
					"No Data is available for the values entered, Please Enter Any Other Value ");
		}

		status = null;
		Log.log(4, "ReportsAction", "applicationWiseReport", "Exited");

		return mapping.findForward("success");
	}

	public ActionForward applicationWiseReportDetails(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		Log.log(4, "ReportsAction", "applicationWiseReportDetails", "Entered");
		ApplicationReport dan = new ApplicationReport();
		DynaActionForm dynaForm = (DynaActionForm) form;
		java.sql.Date startDate = null;
		java.sql.Date endDate = null;
		String application = (String) dynaForm.get("number");
		java.util.Date sDate = (java.util.Date) dynaForm
				.get("dateOfTheDocument24");
		String stDate = String.valueOf(sDate);
		String radio = (String) dynaForm.get("applicationStatus1");

		if (radio.equals("generation"))
			request.setAttribute("radioValue", "DAN Generated");
		else if (radio.equals("allocation"))
			request.setAttribute("radioValue", "Allocated");
		else if (radio.equals("appropriation")) {
			request.setAttribute("radioValue", "Appropriated");
		}

		if ((stDate == null) || (stDate.equals("")))
			startDate = null;
		else if (stDate != null) {
			startDate = new java.sql.Date(sDate.getTime());
		}
		java.util.Date eDate = (java.util.Date) dynaForm
				.get("dateOfTheDocument25");
		endDate = new java.sql.Date(eDate.getTime());
		dan = this.reportManager.applicationWiseReportDetails(startDate,
				endDate, application);
		dynaForm.set("statusDetails", dan);
		String key = dan.getMemberId();

		if ((key == null) || (key.equals(""))) {
			throw new NoDataException(
					"No Data is available for this value, Please Choose Any Other Value ");
		}

		dan = null;
		Log.log(4, "ReportsAction", "applicationWiseReportDetails", "Exited");

		return mapping.findForward("success");
	}

	public ActionForward applicationRecievedReportDetails(
			ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		Log.log(4, "ReportsAction", "applicationRecievedReportDetails",
				"Entered");
		ArrayList dan = new ArrayList();
		DynaActionForm dynaForm = (DynaActionForm) form;
		java.sql.Date startDate = null;
		java.sql.Date endDate = null;
		java.util.Date sDate = (java.util.Date) dynaForm
				.get("dateOfTheDocument22");
		String stDate = String.valueOf(sDate);

		if ((stDate == null) || (stDate.equals("")))
			startDate = null;
		else if (stDate != null) {
			startDate = new java.sql.Date(sDate.getTime());
		}
		java.util.Date eDate = (java.util.Date) dynaForm
				.get("dateOfTheDocument23");
		endDate = new java.sql.Date(eDate.getTime());
		dan = this.reportManager.applicationRecievedReportDetails(startDate,
				endDate);
		dynaForm.set("danRaised", dan);

		if ((dan == null) || (dan.size() == 0)) {
			throw new NoDataException(
					"No Data is available for the values entered, Please Enter Any Other Value ");
		}

		dan = null;
		Log.log(4, "ReportsAction", "applicationRecievedReportDetails",
				"Exited");

		return mapping.findForward("success");
	}

	public ActionForward applicationRecievedReport(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		Log.log(4, "ReportsAction", "applicationRecievedReport", "Entered");
		DynaActionForm dynaForm = (DynaActionForm) form;
		dynaForm.set("selectAll", "");
		dynaForm.set("applicationDate", "");
		dynaForm.set("promoter", "");
		dynaForm.set("itpan", "");
		dynaForm.set("ssiDetails", "");
		dynaForm.set("industryType", "");
		dynaForm.set("termCreditSanctioned", "");
		dynaForm.set("tcInterest", "");
		dynaForm.set("tcTenure", "");
		dynaForm.set("tcPlr", "");
		dynaForm.set("tcOutlay", "");
		dynaForm.set("workingCapitalSanctioned", "");
		dynaForm.set("wcPlr", "");
		dynaForm.set("wcOutlay", "");
		dynaForm.set("rejection", "");

		java.sql.Date startDate = null;
		java.sql.Date endDate = null;
		java.util.Date sDate = (java.util.Date) dynaForm
				.get("dateOfTheDocument22");
		String stDate = String.valueOf(sDate);

		if ((stDate == null) || (stDate.equals("")))
			startDate = null;
		else if (stDate != null) {
			startDate = new java.sql.Date(sDate.getTime());
		}
		java.util.Date eDate = (java.util.Date) dynaForm
				.get("dateOfTheDocument23");
		endDate = new java.sql.Date(eDate.getTime());
		Log.log(4, "ReportsAction", "applicationRecievedReport", "Exited");

		return mapping.findForward("success");
	}

	public ActionForward applicationStatusWiseReportDetails(
			ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		Log.log(4, "ReportsAction", "applicationStatusWiseReportDetails",
				"Entered");
		ApplicationReport dan = new ApplicationReport();
		DynaActionForm dynaForm = (DynaActionForm) form;
		java.sql.Date startDate = null;
		java.sql.Date endDate = null;
		String application = (String) dynaForm.get("number");
		java.util.Date sDate = (java.util.Date) dynaForm
				.get("dateOfTheDocument14");
		String stDate = String.valueOf(sDate);

		if ((stDate == null) || (stDate.equals("")))
			startDate = null;
		else if (stDate != null) {
			startDate = new java.sql.Date(sDate.getTime());
		}
		java.util.Date eDate = (java.util.Date) dynaForm
				.get("dateOfTheDocument15");
		endDate = new java.sql.Date(eDate.getTime());
		dan = this.reportManager.getApplicationStatusWiseReportDetails(
				startDate, endDate, application);
		dynaForm.set("statusDetails", dan);
		String key = dan.getMemberId();

		String radio = (String) dynaForm.get("applicationStatus");

		if (radio.equals("NE"))
			request.setAttribute("radioValue", "New");
		else if (radio.equals("AP"))
			request.setAttribute("radioValue", "Approved");
		else if (radio.equals("PE"))
			request.setAttribute("radioValue", "Pending");
		else if (radio.equals("CL"))
			request.setAttribute("radioValue", "Closed");
		else if (radio.equals("HO"))
			request.setAttribute("radioValue", "Hold");
		else if (radio.equals("MO"))
			request.setAttribute("radioValue", "Modified");
		else if (radio.equals("RE"))
			request.setAttribute("radioValue", "Rejected");
		String member = request.getParameter("number");
		request.setAttribute("MEMBERID", member);

		if ((key == null) || (key.equals(""))) {
			throw new NoDataException(
					"No Data is available for this value, Please Choose Any Other Value ");
		}

		dan = null;
		Log.log(4, "ReportsAction", "applicationStatusWiseReportDetails",
				"Exited");

		return mapping.findForward("success");
	}

	public ActionForward applicationStatusWiseReportDetails1(
			ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		Log.log(4, "ReportsAction", "applicationStatusWiseReportDetails1",
				"Entered");
		ApplicationReport dan = new ApplicationReport();
		DynaActionForm dynaForm = (DynaActionForm) form;
		java.sql.Date startDate = null;
		java.sql.Date endDate = null;
		String application = (String) dynaForm.get("number");

		java.util.Date sDate = (java.util.Date) dynaForm
				.get("dateOfTheDocument14");
		String stDate = String.valueOf(sDate);
		if ((stDate == null) || (stDate.equals(""))) {
			startDate = null;
		} else if (stDate != null) {
			startDate = new java.sql.Date(sDate.getTime());
		}

		java.util.Date eDate = (java.util.Date) dynaForm
				.get("dateOfTheDocument15");
		endDate = new java.sql.Date(eDate.getTime());

		dan = this.reportManager.getApplicationStatusWiseReportDetails1(
				startDate, endDate, application);
		dynaForm.set("statusDetails", dan);
		String key = dan.getMemberId();

		if ((key == null) || (key.equals(""))) {
			throw new NoDataException(
					"No Data is available for this value, Please Choose Any Other Value ");
		}

		dan = null;
		Log.log(4, "ReportsAction", "applicationStatusWiseReportDetails1",
				"Exited");

		return mapping.findForward("success");
	}

	public ActionForward applicationHistoryReportDetails(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		Log.log(4, "ReportsAction", "applicationHistoryReportDetails",
				"Entered");
		ArrayList applicationHistory = new ArrayList();
		ArrayList cgpans = new ArrayList();
		ApplicationReport appReport = new ApplicationReport();
		DynaActionForm dynaForm = (DynaActionForm) form;
		String cgpan = (String) dynaForm.get("enterCgpan");

		User user = getUserInformation(request);
		String bankId = user.getBankId();
		String zoneId = user.getZoneId();
		String branchId = user.getBranchId();
		String memberId = bankId + zoneId + branchId;
		ReportDAO rpDao = new ReportDAO();

		// cgpans = this.reportManager.getAllCgpans();
		String newCgpan = cgpan.toUpperCase();
		applicationHistory = rpDao.getCgpanHistoryReportDetails(newCgpan);
		dynaForm.set("cgpanHistoryReport", applicationHistory);
		// System.out.println("newCgpan:" + newCgpan);
		appReport = this.reportManager.getApplicationReportForCgpan(newCgpan);
		String key = appReport.getMemberId();

		if ((key == null) || (key.equals(""))) {
			throw new NoDataException(
					"No Data is available for the values entered, Please Enter Any Other Value ");
		}
		dynaForm.set("applicationReport", appReport);

		return mapping.findForward("success");
	}

	public ActionForward cgpanHistoryReportDetails(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		Log.log(4, "ReportsAction", "cgpanHistoryReportDetails", "Entered");
		ArrayList applicationHistory = new ArrayList();
		ArrayList cgpans = new ArrayList();
		ApplicationReport appReport = new ApplicationReport();
		DynaActionForm dynaForm = (DynaActionForm) form;
		String cgpan = (String) dynaForm.get("enterCgpan");

		User user = getUserInformation(request);
		String bankId = user.getBankId();
		String zoneId = user.getZoneId();
		String branchId = user.getBranchId();
		String memberId = bankId + zoneId + branchId;
		ReportDAO rpDao = new ReportDAO();

		cgpans = this.reportManager.getAllCgpans();
		String newCgpan = cgpan.toUpperCase();
		applicationHistory = rpDao.getCgpanHistoryReportDetails(newCgpan);
		dynaForm.set("cgpanHistoryReport", applicationHistory);

		appReport = this.reportManager.getApplicationReportForCgpan(newCgpan);
		dynaForm.set("applicationReport", appReport);
		Log.log(4, "ReportsAction", "cgpanHistoryReportDetails", "Exited");

		return mapping.findForward("success");
	}

	public ActionForward applicationReportDetails(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		Log.log(4, "ReportsAction", "applicationReportDetails", "Entered");
		ArrayList apr = new ArrayList();
		ArrayList cgpans = new ArrayList();
		ApplicationReport appReport = new ApplicationReport();
		DynaActionForm dynaForm = (DynaActionForm) form;
		String cgpan = (String) dynaForm.get("enterCgpan");
		String ssiName = (String) dynaForm.get("enterSSI");
		User user = getUserInformation(request);
		String bankId = user.getBankId();
		String zoneId = user.getZoneId();
		String branchId = user.getBranchId();
		String memberId = bankId + zoneId + branchId;

		if (!branchId.equals("0000")) {
			if ((cgpan == null) || (cgpan.equals(""))) {
				apr = this.reportManager.getCgpanForBranch(ssiName, memberId);
				dynaForm.set("cgpanList", apr);
				if ((apr == null) || (apr.size() == 0)) {
					throw new NoDataException(
							"No Data is available for the values entered, Please Enter Any Other Value ");
				}

				apr = null;
				Log.log(4, "applicationReportDetails",
						"applicationReportDetails", "Exited");

				return mapping.findForward("success3");
			}

			// cgpans = this.reportManager.getAllCgpans();
			String newCgpan = cgpan.toUpperCase();

			// if (cgpans.contains(newCgpan)) {
			appReport = this.reportManager.applicationReportForBranch(cgpan,
					ssiName, memberId);
			dynaForm.set("applicationReport", appReport);
			String key = appReport.getMemberId();

			if ((key == null) || (key.equals(""))) {
				throw new NoDataException(
						"No Data is available for the values entered, Please Enter Any Other Value ");
			}

			appReport = null;
			Log.log(4, "ReportsAction", "applicationReportDetails", "Exited");

			return mapping.findForward("success4");
			// }else{
			// throw new NoDataException("Enter a Valid CGPAN");
			// }
		}

		if (!zoneId.equals("0000")) {
			if ((cgpan == null) || (cgpan.equals(""))) {
				apr = this.reportManager.getCgpanForZone(ssiName, bankId,
						zoneId);
				dynaForm.set("cgpanList", apr);
				if ((apr == null) || (apr.size() == 0)) {
					throw new NoDataException(
							"No Data is available for the values entered, Please Enter Any Other Value ");
				}
				apr = null;
				Log.log(4, "applicationReportDetails",
						"applicationReportDetails", "Exited");

				return mapping.findForward("success5");
			}

			// cgpans = this.reportManager.getAllCgpans();
			String newCgpan = cgpan.toUpperCase();

			// if (cgpans.contains(newCgpan)) {
			appReport = this.reportManager.applicationReportForZone(cgpan,
					ssiName, bankId, zoneId);
			dynaForm.set("applicationReport", appReport);
			String key = appReport.getMemberId();

			if ((key == null) || (key.equals(""))) {
				throw new NoDataException(
						"No Data is available for the values entered, Please Enter Any Other Value ");
			}

			appReport = null;
			Log.log(4, "ReportsAction", "applicationReportDetails", "Exited");

			return mapping.findForward("success6");
			// }else{
			// throw new NoDataException("Enter a Valid CGPAN");
			// }
		}

		if (!bankId.equals("0000")) {
			String newBankId = bankId;
			if ((cgpan == null) || (cgpan.equals(""))) {
				apr = this.reportManager.getCgpanForMember(ssiName, newBankId);
				dynaForm.set("cgpanList", apr);
				if ((apr == null) || (apr.size() == 0)) {
					throw new NoDataException(
							"No Data is available for the values entered, Please Enter Any Other Value ");
				}

				apr = null;
				Log.log(4, "applicationReportDetails",
						"applicationReportDetails", "Exited");

				return mapping.findForward("success7");
			}

			// cgpans = this.reportManager.getAllCgpans();
			String newCgpan = cgpan.toUpperCase();

			// if (cgpans.contains(newCgpan)) {
			appReport = this.reportManager.getApplicationReportForMember(cgpan,
					ssiName, newBankId);
			dynaForm.set("applicationReport", appReport);
			String key = appReport.getMemberId();

			if ((key == null) || (key.equals(""))) {
				throw new NoDataException(
						"No Data is available for the values entered, Please Enter Any Other Value ");
			}

			appReport = null;
			Log.log(4, "ReportsAction", "applicationReportDetails", "Exited");

			return mapping.findForward("success8");
			// }else{
			// throw new NoDataException("Enter a Valid CGPAN");
			// }
		}

		if ((cgpan == null) || (cgpan.equals(""))) {
			apr = this.reportManager.getCgpan(ssiName);
			dynaForm.set("cgpanList", apr);
			if ((apr == null) || (apr.size() == 0)) {
				throw new NoDataException(
						"No Data is available for the values entered, Please Enter Any Other Value ");
			}

			apr = null;
			Log.log(4, "applicationReportDetails", "applicationReportDetails",
					"Exited");

			return mapping.findForward("success1");
		}

		// cgpans = this.reportManager.getAllCgpans();
		String newCgpan = cgpan.toUpperCase();

		// if (cgpans.contains(newCgpan)) {
		appReport = this.reportManager.getApplicationReport(cgpan, ssiName);
		dynaForm.set("applicationReport", appReport);
		String key = appReport.getMemberId();

		if ((key == null) || (key.equals(""))) {
			throw new NoDataException(
					"No Data is available for the values entered, Please Enter Any Other Value ");
		}

		appReport = null;
		Log.log(4, "ReportsAction", "applicationReportDetails", "Exited");

		return mapping.findForward("success2");
		// }else{
		// throw new NoDataException("Enter a Valid CGPAN");
		// }
	}

	public ActionForward cgpanList(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		Log.log(4, "ReportsAction", "cgpanList", "Entered");
		ApplicationReport appReport = new ApplicationReport();
		DynaActionForm dynaForm = (DynaActionForm) form;
		String cgpan = (String) dynaForm.get("enterCgpan");
		String ssiName = (String) dynaForm.get("enterSSI");

		appReport = this.reportManager.getApplicationReport(cgpan, ssiName);
		dynaForm.set("applicationReport", appReport);
		if (appReport == null) {
			throw new NoDataException(
					"No Data is available for the values entered, Please Enter Any Other Value ");
		}

		appReport = null;
		Log.log(4, "ReportsAction", "cgpanList", "Exited");

		return mapping.findForward("success");
	}

	public ActionForward applicationReportDetails1(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		Log.log(4, "ReportsAction", "applicationReportDetails1", "Entered");
		ApplicationReport appReport = new ApplicationReport();
		DynaActionForm dynaForm = (DynaActionForm) form;
		String cgpan = request.getParameter("cgpan");
		appReport = this.reportManager.getApplicationReportForCgpan(cgpan);
		dynaForm.set("applicationReport", appReport);
		if (appReport == null) {
			throw new NoDataException(
					"No Data is available for the values entered, Please Enter Any Other Value ");
		}

		appReport = null;
		Log.log(4, "ReportsAction", "applicationReportDetails1", "Exited");

		return mapping.findForward("success");
	}

	public ActionForward danRaised(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		Log.log(4, "ReportsAction", "danRaised", "Entered");

		DynaActionForm dynaForm = (DynaActionForm) form;
		java.util.Date date = new java.util.Date();
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(date);
		int month = calendar.get(2);
		int day = calendar.get(5);
		month -= 1;
		day += 1;
		calendar.set(2, month);
		calendar.set(5, day);
		java.util.Date prevDate = calendar.getTime();

		GeneralReport generalReport = new GeneralReport();
		generalReport.setDateOfTheDocument2(prevDate);

		generalReport.setDateOfTheDocument3(date);

		BeanUtils.copyProperties(dynaForm, generalReport);
		Log.log(4, "ReportsAction", "danRaised", "Exited");

		return mapping.findForward("success");
	}

	public ActionForward danRaisedMlis(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		Log.log(4, "ReportsAction", "danRaisedMlis", "Entered");
		ArrayList dan = new ArrayList();
		DynaActionForm dynaForm = (DynaActionForm) form;
		java.sql.Date startDate = null;
		java.sql.Date endDate = null;
		java.util.Date sDate = (java.util.Date) dynaForm
				.get("dateOfTheDocument2");
		String stDate = String.valueOf(sDate);

		if ((stDate == null) || (stDate.equals("")))
			startDate = null;
		else if (stDate != null) {
			startDate = new java.sql.Date(sDate.getTime());
		}
		java.util.Date eDate = (java.util.Date) dynaForm
				.get("dateOfTheDocument3");
		endDate = new java.sql.Date(eDate.getTime());
		dan = this.reportManager.getDanRaisedMlis(startDate, endDate);
		dynaForm.set("danRaised", dan);
		if ((dan == null) || (dan.size() == 0)) {
			throw new NoDataException(
					"No Data is available for the values entered, Please Enter Any Other Value ");
		}

		dan = null;
		Log.log(4, "ReportsAction", "danRaisedMlis", "Exited");

		return mapping.findForward("success");
	}

	public ActionForward danRaisedReport(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		Log.log(4, "ReportsAction", "danRaisedReport", "Exited");
		ArrayList danReport = new ArrayList();
		HttpSession session = request.getSession();
		DynaActionForm dynaForm = (DynaActionForm) form;
		java.sql.Date startDate = null;
		java.sql.Date endDate = null;
		java.util.Date sDate = (java.util.Date) dynaForm
				.get("dateOfTheDocument2");
		String stDate = String.valueOf(sDate);
		if ((stDate == null) || (stDate.equals(""))) {
			startDate = null;
		} else if (stDate != null) {
			startDate = new java.sql.Date(sDate.getTime());
		}
		java.util.Date eDate = (java.util.Date) dynaForm
				.get("dateOfTheDocument3");
		endDate = new java.sql.Date(eDate.getTime());
		String flag = (String) dynaForm.get("bank");
		session.setAttribute("BankForDispaly", flag);

		int i = flag.indexOf("$");

		if (i != -1) {
			String newFlag = flag.replace('$', '&');
			danReport = this.reportManager.getDanRaisedReport(startDate,
					endDate, newFlag);
			dynaForm.set("danRaisedReport", danReport);
			if ((danReport == null) || (danReport.size() == 0)) {
				throw new NoDataException(
						"No Data is available for the values entered, Please Enter Any Other Value ");
			}

			danReport = null;
			Log.log(4, "ReportsAction", "danRaisedReport", "Exited");

			return mapping.findForward("success");
		}

		danReport = this.reportManager.getDanRaisedReport(startDate, endDate,
				flag);
		dynaForm.set("danRaisedReport", danReport);
		if ((danReport == null) || (danReport.size() == 0)) {
			throw new NoDataException(
					"No Data is available for the values entered, Please Enter Any Other Value ");
		}

		danReport = null;
		Log.log(4, "ReportsAction", "danRaisedReport", "Exited");

		return mapping.findForward("success");
	}

	public ActionForward danDetails(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		Log.log(4, "ReportsAction", "danDetails", "Entered");
		ArrayList danDetails = new ArrayList();
		DynaActionForm dynaForm = (DynaActionForm) form;
		java.sql.Date startDate = null;
		java.sql.Date endDate = null;

		String cgdan = (String) dynaForm.get("danDetail");
		java.util.Date sDate = (java.util.Date) dynaForm
				.get("dateOfTheDocument2");
		String stDate = String.valueOf(sDate);
		if ((stDate == null) || (stDate.equals(""))) {
			startDate = null;
		} else if (stDate != null) {
			startDate = new java.sql.Date(sDate.getTime());
		}

		java.util.Date eDate = (java.util.Date) dynaForm
				.get("dateOfTheDocument3");
		endDate = new java.sql.Date(eDate.getTime());

		String bank = (String) dynaForm.get("bank");
		int i = bank.indexOf("$");

		if (i != -1) {
			String newBank = bank.replace('$', '&');
			danDetails = this.reportManager.getDanDetails(startDate, endDate,
					newBank, cgdan);
			dynaForm.set("danDetails", danDetails);

			if ((danDetails == null) || (danDetails.size() == 0)) {
				throw new NoDataException(
						"No Data is available for the values entered, Please Enter Any Other Value ");
			}

			Log.log(4, "ReportsAction", "danDetails", "Exited");

			return mapping.findForward("success");
		}

		danDetails = this.reportManager.getDanDetails(startDate, endDate, bank,
				cgdan);
		dynaForm.set("danDetails", danDetails);

		if ((danDetails == null) || (danDetails.size() == 0)) {
			throw new NoDataException(
					"No Data is available for the values entered, Please Enter Any Other Value ");
		}

		Log.log(4, "ReportsAction", "danDetails", "Exited");

		return mapping.findForward("success");
	}

	public ActionForward gfOutstanding(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		Log.log(4, "ReportsAction", "gfOutstanding", "Entered");

		DynaActionForm dynaForm = (DynaActionForm) form;
		java.util.Date date = new java.util.Date();
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(date);
		int month = calendar.get(2);
		int day = calendar.get(5);
		month -= 1;
		day += 1;
		calendar.set(2, month);
		calendar.set(5, day);
		java.util.Date prevDate = calendar.getTime();

		GeneralReport generalReport = new GeneralReport();
		generalReport.setDateOfTheDocument4(prevDate);
		generalReport.setDateOfTheDocument5(date);
		BeanUtils.copyProperties(dynaForm, generalReport);
		Log.log(4, "ReportsAction", "gfOutstanding", "Exited");

		return mapping.findForward("success");
	}

	public ActionForward gfOutstandingMlis(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		Log.log(4, "ReportsAction", "gfOutstandingMlis", "Entered");
		ArrayList dan = new ArrayList();
		DynaActionForm dynaForm = (DynaActionForm) form;
		java.sql.Date startDate = null;
		java.sql.Date endDate = null;
		java.util.Date sDate = (java.util.Date) dynaForm
				.get("dateOfTheDocument4");
		String stDate = String.valueOf(sDate);

		if ((stDate == null) || (stDate.equals("")))
			startDate = null;
		else if (stDate != null) {
			startDate = new java.sql.Date(sDate.getTime());
		}
		java.util.Date eDate = (java.util.Date) dynaForm
				.get("dateOfTheDocument5");
		endDate = new java.sql.Date(eDate.getTime());
		dan = this.reportManager.getGfOutstandingMli(startDate, endDate);
		dynaForm.set("guaranteeFee", dan);
		if ((dan == null) || (dan.size() == 0)) {
			throw new NoDataException(
					"No Data is available for the values entered, Please Enter Any Other Value ");
		}

		dan = null;
		Log.log(4, "ReportsAction", "gfOutstandingMlis", "Exited");

		return mapping.findForward("success");
	}

	public ActionForward gfOutstandingReport(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		Log.log(4, "ReportsAction", "gfOutstandingReport", "Entered");
		ArrayList gFee = new ArrayList();
		HttpSession session = request.getSession();
		DynaActionForm dynaForm = (DynaActionForm) form;
		java.sql.Date startDate = null;
		java.sql.Date endDate = null;
		java.util.Date sDate = (java.util.Date) dynaForm
				.get("dateOfTheDocument4");
		String stDate = String.valueOf(sDate);

		if ((stDate == null) || (stDate.equals(""))) {
			startDate = null;
		} else if (stDate != null) {
			startDate = new java.sql.Date(sDate.getTime());
		}

		java.util.Date eDate = (java.util.Date) dynaForm
				.get("dateOfTheDocument5");
		endDate = new java.sql.Date(eDate.getTime());
		String bank = (String) dynaForm.get("bank");
		session.removeAttribute("BankForDispaly");
		session.setAttribute("BankForDispaly", bank);
		int i = bank.indexOf("$");

		if (i != -1) {
			String newBank = bank.replace('$', '&');
			gFee = this.reportManager.getGfOutstanding(startDate, endDate,
					newBank);
			dynaForm.set("gFeeReport", gFee);
			if ((gFee == null) || (gFee.size() == 0)) {
				throw new NoDataException(
						"There are no outstanding Demand Advices  available for this MLI ");
			}

			gFee = null;
			Log.log(4, "ReportsAction", "gfOutstandingReport", "Exited");

			return mapping.findForward("success");
		}

		gFee = this.reportManager.getGfOutstanding(startDate, endDate, bank);
		dynaForm.set("gFeeReport", gFee);
		if ((gFee == null) || (gFee.size() == 0)) {
			throw new NoDataException(
					"There are no outstanding Demand Advices  available for this MLI ");
		}

		gFee = null;
		Log.log(4, "ReportsAction", "gfOutstandingReport", "Exited");

		return mapping.findForward("success");
	}

	public ActionForward danDetailsGf(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		Log.log(4, "ReportsAction", "danDetailsGf", "Entered");
		ArrayList danDetails = new ArrayList();
		DynaActionForm dynaForm = (DynaActionForm) form;
		java.sql.Date startDate = null;
		java.sql.Date endDate = null;
		String bank = (String) dynaForm.get("bank");
		String cgdan = (String) dynaForm.get("danDetail");
		java.util.Date sDate = (java.util.Date) dynaForm
				.get("dateOfTheDocument4");
		String stDate = String.valueOf(sDate);
		if ((stDate == null) || (stDate.equals(""))) {
			startDate = null;
		} else if (stDate != null) {
			startDate = new java.sql.Date(sDate.getTime());
		}

		java.util.Date eDate = (java.util.Date) dynaForm
				.get("dateOfTheDocument5");
		endDate = new java.sql.Date(eDate.getTime());
		danDetails = this.reportManager.getDanDetailsGf(startDate, endDate,
				bank, cgdan);
		dynaForm.set("danDetails", danDetails);
		if ((danDetails == null) || (danDetails.size() == 0)) {
			throw new NoDataException(
					"No Data is available for the values entered, Please Enter Any Other Value ");
		}

		Log.log(4, "ReportsAction", "danDetailsGf", "Exited");

		return mapping.findForward("success");
	}

	public ActionForward monthlyProgressReport(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		Log.log(4, "ReportsAction", "monthlyProgressReport", "Entered");
		DynaActionForm dynaForm = (DynaActionForm) form;
		java.util.Date date = new java.util.Date();
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(date);
		int month = calendar.get(2);
		int day = calendar.get(5);
		month -= 1;
		day += 1;
		calendar.set(2, month);
		calendar.set(5, day);
		java.util.Date prevDate = calendar.getTime();
		GeneralReport generalReport = new GeneralReport();
		generalReport.setDateOfTheDocument(prevDate);
		generalReport.setDateOfTheDocument1(date);
		BeanUtils.copyProperties(dynaForm, generalReport);

		Log.log(4, "ReportsAction", "monthlyProgressReport", "Exited");

		return mapping.findForward("success");
	}

	public ActionForward turnoverReport(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		Log.log(4, "ReportsAction", "turnoverReport", "Entered");
		DynaActionForm dynaForm = (DynaActionForm) form;
		java.util.Date date = new java.util.Date();
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(date);
		int month = calendar.get(2);
		int day = calendar.get(5);
		month -= 1;
		day += 1;
		calendar.set(2, month);
		calendar.set(5, day);
		java.util.Date prevDate = calendar.getTime();
		GeneralReport generalReport = new GeneralReport();
		generalReport.setDateOfTheDocument(prevDate);
		generalReport.setDateOfTheDocument1(date);
		BeanUtils.copyProperties(dynaForm, generalReport);

		Log.log(4, "ReportsAction", "turnoverReport", "Exited");

		return mapping.findForward("success");
	}

	public ActionForward minorityReport(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		Log.log(4, "ReportsAction", "minorityReport", "Entered");
		DynaActionForm dynaForm = (DynaActionForm) form;
		java.util.Date date = new java.util.Date();
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(date);
		int month = calendar.get(2);
		int day = calendar.get(5);
		month -= 1;
		day += 1;
		calendar.set(2, month);
		calendar.set(5, day);
		java.util.Date prevDate = calendar.getTime();
		GeneralReport generalReport = new GeneralReport();
		generalReport.setDateOfTheDocument(prevDate);
		generalReport.setDateOfTheDocument1(date);
		BeanUtils.copyProperties(dynaForm, generalReport);

		Log.log(4, "ReportsAction", "minorityReport", "Exited");

		return mapping.findForward("success");
	}

	public ActionForward minorityStateReport(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		Log.log(4, "ReportsAction", "minorityStateReport", "Entered");
		DynaActionForm dynaForm = (DynaActionForm) form;
		java.util.Date date = new java.util.Date();
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(date);
		int month = calendar.get(2);
		int day = calendar.get(5);
		month -= 1;
		day += 1;
		calendar.set(2, month);
		calendar.set(5, day);
		java.util.Date prevDate = calendar.getTime();
		GeneralReport generalReport = new GeneralReport();
		generalReport.setDateOfTheDocument(prevDate);
		generalReport.setDateOfTheDocument1(date);
		BeanUtils.copyProperties(dynaForm, generalReport);

		Log.log(4, "ReportsAction", "minorityStateReport", "Exited");

		return mapping.findForward("success");
	}

	public ActionForward stateWiseReportAll(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		Log.log(4, "ReportsAction", "stateWiseReportAll", "Entered");

		DynaActionForm dynaForm = (DynaActionForm) form;
		java.util.Date date = new java.util.Date();
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(date);
		int month = calendar.get(2);
		int day = calendar.get(5);
		month -= 1;
		day += 1;
		calendar.set(2, month);
		calendar.set(5, day);
		java.util.Date prevDate = calendar.getTime();

		GeneralReport generalReport = new GeneralReport();
		generalReport.setDateOfTheDocument10(prevDate);
		generalReport.setDateOfTheDocument11(date);
		BeanUtils.copyProperties(dynaForm, generalReport);
		Log.log(4, "ReportsAction", "stateWiseReportAll", "Exited");

		return mapping.findForward("success");
	}

	public ActionForward stateWiseReportDetailsAll(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		Log.log(4, "ReportsAction", "stateWiseReportDetailsAll", "Entered");
		DynaActionForm dynaForm = (DynaActionForm) form;
		ArrayList mliReport = new ArrayList();
		java.sql.Date startDate = null;
		java.sql.Date endDate = null;
		java.util.Date sDate = (java.util.Date) dynaForm
				.get("dateOfTheDocument10");
		String stDate = String.valueOf(sDate);

		if ((stDate == null) || (stDate.equals("")))
			startDate = null;
		else if (stDate != null) {
			startDate = new java.sql.Date(sDate.getTime());
		}
		java.util.Date eDate = (java.util.Date) dynaForm
				.get("dateOfTheDocument11");
		endDate = new java.sql.Date(eDate.getTime());

		String id = (String) dynaForm.get("checkValue");

		BaseAction baseAction = new BaseAction();
		MLIInfo mliInfo = baseAction.getMemberInfo(request);
		String bankId = mliInfo.getBankId();
		String zoneId = mliInfo.getZoneId();
		String branchId = mliInfo.getBranchId();

		if (id.equals("yes"))
			request.setAttribute("radioValue", "Guarantee Approved");
		else if (id.equals("no")) {
			request.setAttribute("radioValue", "Guarantee Issued");
		}
		mliReport = this.reportManager.getStateWiseReportDetailsNew(startDate,
				endDate, id);
		dynaForm.set("mliWiseReport", mliReport);
		if ((mliReport == null) || (mliReport.size() == 0)) {
			throw new NoDataException(
					"No Data is available for the values entered, Please Enter Any Other Value ");
		}

		mliReport = null;
		Log.log(4, "ReportsAction", "stateWiseReportDetailsAll", "Exited");

		return mapping.findForward("success");
	}

	public ActionForward categorywiseguaranteeissuedReport(
			ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		Log.log(4, "ReportsAction", "categorywiseguaranteeissuedReport",
				"Entered");
		DynaActionForm dynaForm = (DynaActionForm) form;
		java.util.Date date = new java.util.Date();
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(date);
		int month = calendar.get(2);
		int day = calendar.get(5);
		month -= 1;
		day += 1;
		calendar.set(2, month);
		calendar.set(5, day);
		java.util.Date prevDate = calendar.getTime();
		GeneralReport generalReport = new GeneralReport();
		generalReport.setDateOfTheDocument(prevDate);
		generalReport.setDateOfTheDocument1(date);
		BeanUtils.copyProperties(dynaForm, generalReport);

		Log.log(4, "ReportsAction", "categorywiseguaranteeissuedReport",
				"Exited");

		return mapping.findForward("success");
	}

	public ActionForward minorityprogressReport(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		Log.log(4, "ReportsAction", "minorityprogressReport", "Entered");
		ArrayList minorityprogressReport = new ArrayList();
		DynaActionForm dynaForm = (DynaActionForm) form;
		java.sql.Date startDate = null;
		java.sql.Date endDate = null;
		java.util.Date sDate = (java.util.Date) dynaForm
				.get("dateOfTheDocument");
		String stDate = String.valueOf(sDate);

		if ((stDate == null) || (stDate.equals("")))
			startDate = null;
		else if (stDate != null) {
			startDate = new java.sql.Date(sDate.getTime());
		}

		java.util.Date eDate = (java.util.Date) dynaForm
				.get("dateOfTheDocument1");
		endDate = new java.sql.Date(eDate.getTime());

		minorityprogressReport = this.reportManager.getminorityProgressReport(
				startDate, endDate);
		dynaForm.set("minorityprogressReport", minorityprogressReport);
		if ((minorityprogressReport == null)
				|| (minorityprogressReport.size() == 0)) {
			throw new NoDataException(
					"No Data is available for the values entered, Please Enter Any Other Value ");
		}

		Log.log(4, "ReportsAction", "minorityprogressReport", "Exited");

		return mapping.findForward("success");
	}

	public ActionForward turnoverprogressReport(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		Log.log(4, "ReportsAction", "turnoverprogressReport", "Entered");
		ArrayList turnoverprogressReport = new ArrayList();
		DynaActionForm dynaForm = (DynaActionForm) form;
		java.sql.Date startDate = null;
		java.sql.Date endDate = null;
		java.util.Date sDate = (java.util.Date) dynaForm
				.get("dateOfTheDocument");
		String stDate = String.valueOf(sDate);

		if ((stDate == null) || (stDate.equals("")))
			startDate = null;
		else if (stDate != null) {
			startDate = new java.sql.Date(sDate.getTime());
		}

		java.util.Date eDate = (java.util.Date) dynaForm
				.get("dateOfTheDocument1");
		endDate = new java.sql.Date(eDate.getTime());

		turnoverprogressReport = this.reportManager.getturnoverProgressReport(
				startDate, endDate);
		dynaForm.set("turnoverprogressReport", turnoverprogressReport);
		if ((turnoverprogressReport == null)
				|| (turnoverprogressReport.size() == 0)) {
			throw new NoDataException(
					"No Data is available for the values entered, Please Enter Any Other Value ");
		}

		Log.log(4, "ReportsAction", "turnoverprogressReport", "Exited");

		return mapping.findForward("success");
	}

	public ActionForward minoritystateprogressReport(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		Log.log(4, "ReportsAction", "minoritystateprogressReport", "Entered");
		ArrayList minoritystateprogressReport = new ArrayList();
		DynaActionForm dynaForm = (DynaActionForm) form;
		java.sql.Date startDate = null;
		java.sql.Date endDate = null;
		java.util.Date sDate = (java.util.Date) dynaForm
				.get("dateOfTheDocument");
		String stDate = String.valueOf(sDate);

		if ((stDate == null) || (stDate.equals("")))
			startDate = null;
		else if (stDate != null) {
			startDate = new java.sql.Date(sDate.getTime());
		}

		java.util.Date eDate = (java.util.Date) dynaForm
				.get("dateOfTheDocument1");
		endDate = new java.sql.Date(eDate.getTime());

		minoritystateprogressReport = this.reportManager
				.getminorityStateProgressReport(startDate, endDate);
		dynaForm.set("minoritystateprogressReport", minoritystateprogressReport);
		if ((minoritystateprogressReport == null)
				|| (minoritystateprogressReport.size() == 0)) {
			throw new NoDataException(
					"No Data is available for the values entered, Please Enter Any Other Value ");
		}

		Log.log(4, "ReportsAction", "minoritystateprogressReport", "Exited");

		return mapping.findForward("success");
	}

	public ActionForward categorywiseguaranteeissuedprogressReport(
			ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		Log.log(4, "ReportsAction",
				"categorywiseguaranteeissuedprogressReport", "Entered");
		ArrayList categorywiseguaranteeissuedprogressReport = new ArrayList();
		DynaActionForm dynaForm = (DynaActionForm) form;
		java.sql.Date startDate = null;
		java.sql.Date endDate = null;
		java.util.Date sDate = (java.util.Date) dynaForm
				.get("dateOfTheDocument");
		String stDate = String.valueOf(sDate);

		if ((stDate == null) || (stDate.equals("")))
			startDate = null;
		else if (stDate != null) {
			startDate = new java.sql.Date(sDate.getTime());
		}

		java.util.Date eDate = (java.util.Date) dynaForm
				.get("dateOfTheDocument1");
		endDate = new java.sql.Date(eDate.getTime());

		categorywiseguaranteeissuedprogressReport = this.reportManager
				.getcategorywiseguaranteeissuedprogressReport(startDate,
						endDate);
		dynaForm.set("categorywiseguaranteeissuedprogressReport",
				categorywiseguaranteeissuedprogressReport);
		if ((categorywiseguaranteeissuedprogressReport == null)
				|| (categorywiseguaranteeissuedprogressReport.size() == 0)) {
			throw new NoDataException(
					"No Data is available for the values entered, Please Enter Any Other Value ");
		}

		Log.log(4, "ReportsAction",
				"categorywiseguaranteeissuedprogressReport", "Exited");

		return mapping.findForward("success");
	}

	public ActionForward turnoverandexportsReport(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		Log.log(4, "ReportsAction", "turnoverandexportsReport", "Entered");
		ArrayList turnoverandexportprogressreport = new ArrayList();
		DynaActionForm dynaForm = (DynaActionForm) form;
		java.sql.Date startDate = null;
		java.sql.Date endDate = null;
		java.util.Date sDate = (java.util.Date) dynaForm
				.get("dateOfTheDocument");
		String stDate = String.valueOf(sDate);

		if ((stDate == null) || (stDate.equals("")))
			startDate = null;
		else if (stDate != null) {
			startDate = new java.sql.Date(sDate.getTime());
		}

		java.util.Date eDate = (java.util.Date) dynaForm
				.get("dateOfTheDocument1");
		endDate = new java.sql.Date(eDate.getTime());

		turnoverandexportprogressreport = this.reportManager
				.getcategorywiseguaranteeissuedprogressReport(startDate,
						endDate);
		dynaForm.set("turnoverandexportprogressreport",
				turnoverandexportprogressreport);
		if ((turnoverandexportprogressreport == null)
				|| (turnoverandexportprogressreport.size() == 0)) {
			throw new NoDataException(
					"No Data is available for the values entered, Please Enter Any Other Value ");
		}

		Log.log(4, "ReportsAction", "turnoverandexportsReport", "Exited");

		return mapping.findForward("success");
	}

	public ActionForward progressReport(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		Log.log(4, "ReportsAction", "progressReport", "Entered");
		ArrayList progressReport = new ArrayList();
		DynaActionForm dynaForm = (DynaActionForm) form;
		java.sql.Date startDate = null;
		java.sql.Date endDate = null;
		java.util.Date sDate = (java.util.Date) dynaForm
				.get("dateOfTheDocument");
		String stDate = String.valueOf(sDate);
		User user = getUserInformation(request);
		String bankId = user.getBankId();
		String zoneId = user.getZoneId();
		String branchId = user.getBranchId();
		String userId = user.getUserId();
		if ((stDate == null) || (stDate.equals("")))
			startDate = null;
		else if (stDate != null) {
			startDate = new java.sql.Date(sDate.getTime());
		}

		java.util.Date eDate = (java.util.Date) dynaForm
				.get("dateOfTheDocument1");
		endDate = new java.sql.Date(eDate.getTime());

		if (bankId.equals("0000")) {
			progressReport = this.reportManager.getMonthlyProgressReport(
					startDate, endDate);
		} else {
			progressReport = this.reportManager.getMonthlyProgressReportNew(
					startDate, endDate);
		}
		dynaForm.set("progressReport", progressReport);
		if ((progressReport == null) || (progressReport.size() == 0)) {
			throw new NoDataException(
					"No Data is available for the values entered, Please Enter Any Other Value ");
		}

		Log.log(4, "ReportsAction", "progressReport", "Exited");

		return mapping.findForward("success");
	}

	public ActionForward sanctionedApplicationReport(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		Log.log(4, "ReportsAction", "sanctionedApplicationReport", "Entered");
		ArrayList sar = new ArrayList();
		DynaActionForm dynaForm = (DynaActionForm) form;
		String memberId = (String) dynaForm.get("memberId");
		String flag = (String) dynaForm.get("checkValue");
		java.sql.Date startDate = null;
		java.sql.Date endDate = null;
		java.util.Date sDate = (java.util.Date) dynaForm
				.get("dateOfTheDocument");
		String stDate = String.valueOf(sDate);

		if ((stDate == null) || (stDate.equals("")))
			startDate = null;
		else if (stDate != null) {
			startDate = new java.sql.Date(sDate.getTime());
		}

		if (flag.equals("yes"))
			request.setAttribute("radioValue", "Guarantee Approved");
		else if (flag.equals("no")) {
			request.setAttribute("radioValue", "Guarantee Issued");
		}

		java.util.Date eDate = (java.util.Date) dynaForm
				.get("dateOfTheDocument1");
		endDate = new java.sql.Date(eDate.getTime());
		ClaimsProcessor claimsProcessor = new ClaimsProcessor();
		Vector memberids = new Vector();
		memberids = claimsProcessor.getAllMemberIds();

		if ((memberId == null) || (memberId.equals(""))) {
			sar = this.reportManager.getSanctionedApplicationReport(startDate,
					endDate, flag);
			dynaForm.set("sar", sar);
			if ((sar == null) || (sar.size() == 0)) {
				throw new NoDataException(
						"No Data is available for the values entered, Please Enter Any Other Value ");
			}

			Log.log(4, "ReportsAction", "sanctionedApplicationReport", "Exited");

			return mapping.findForward("success");
		}

		if (memberids.contains(memberId)) {
			String bankId = memberId.substring(0, 4);
			String zoneId = memberId.substring(4, 8);
			String branchId = memberId.substring(8, 12);

			if (!branchId.equals("0000")) {
				String id = bankId + zoneId + branchId;
				sar = this.reportManager
						.getSanctionedApplicationReportForBranch(startDate,
								endDate, id, flag);
				dynaForm.set("sar", sar);
				if ((sar == null) || (sar.size() == 0)) {
					throw new NoDataException(
							"No Data is available for the values entered, Please Enter Any Other Value ");
				}

				claimsProcessor = null;
				memberids = null;
				Log.log(4, "ReportsAction", "sanctionedApplicationReport",
						"Exited");

				return mapping.findForward("success");
			}

			if (!zoneId.equals("0000")) {
				String id = bankId + zoneId;
				sar = this.reportManager.getSanctionedApplicationReportForZone(
						startDate, endDate, id, flag);
				dynaForm.set("sar", sar);
				if ((sar == null) || (sar.size() == 0)) {
					throw new NoDataException(
							"No Data is available for the values entered, Please Enter Any Other Value ");
				}

				claimsProcessor = null;
				memberids = null;
				Log.log(4, "ReportsAction", "sanctionedApplicationReport",
						"Exited");

				return mapping.findForward("success");
			}

			String id = bankId;
			sar = this.reportManager.getSanctionedApplicationReportForBank(
					startDate, endDate, id, flag);
			dynaForm.set("sar", sar);
			if ((sar == null) || (sar.size() == 0)) {
				throw new NoDataException(
						"No Data is available for the values entered, Please Enter Any Other Value ");
			}

			claimsProcessor = null;
			memberids = null;
			Log.log(4, "ReportsAction", "sanctionedApplicationReport", "Exited");

			return mapping.findForward("success");
		}

		throw new NoDataException("Please Enter Valid Member Id");
	}

	public ActionForward danReport(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		Log.log(4, "ReportsAction", "danReport", "Entered");
		ArrayList dan = new ArrayList();
		DynaActionForm dynaForm = (DynaActionForm) form;
		java.sql.Date startDate = null;
		java.sql.Date endDate = null;
		java.util.Date sDate = (java.util.Date) dynaForm
				.get("dateOfTheDocument20");
		String stDate = String.valueOf(sDate);
		if ((stDate == null) || (stDate.equals(""))) {
			startDate = null;
		} else if (stDate != null) {
			startDate = new java.sql.Date(sDate.getTime());
		}

		java.util.Date eDate = (java.util.Date) dynaForm
				.get("dateOfTheDocument21");
		endDate = new java.sql.Date(eDate.getTime());

		String id = (String) dynaForm.get("memberId");

		String ssi = (String) dynaForm.get("ssi");

		ClaimsProcessor claimsProcessor = new ClaimsProcessor();
		Vector memberids = new Vector();
		memberids = claimsProcessor.getAllMemberIds();

		if ((id == null) || (id.equals(""))) {
			dan = this.reportManager.getDanReport(startDate, endDate, id, ssi);
			dynaForm.set("dan", dan);
			if ((dan == null) || (dan.size() == 0)) {
				throw new NoDataException(
						"No Data is available for the values entered, Please Enter Any Other Value ");
			}

			memberids = null;
			claimsProcessor = null;
			Log.log(4, "ReportsAction", "danReport", "Exited");

			return mapping.findForward("success");
		}

		if (memberids.contains(id)) {
			String bankId = id.substring(0, 4);

			String zoneId = id.substring(4, 8);

			String branchId = id.substring(8, 12);

			if (!branchId.equals("0000")) {
				String memberId = bankId + zoneId + branchId;
				dan = this.reportManager.getDanReportForBranch(startDate,
						endDate, memberId, ssi);
				dynaForm.set("dan", dan);
				if ((dan == null) || (dan.size() == 0)) {
					throw new NoDataException(
							"No Data is available for the values entered, Please Enter Any Other Value ");
				}

				memberids = null;
				claimsProcessor = null;

				Log.log(4, "ReportsAction", "danReport", "Exited");

				return mapping.findForward("success");
			}

			if (!zoneId.equals("0000")) {
				dan = this.reportManager.getDanReportForZone(startDate,
						endDate, bankId, ssi, zoneId);
				dynaForm.set("dan", dan);
				if ((dan == null) || (dan.size() == 0)) {
					throw new NoDataException(
							"No Data is available for the values entered, Please Enter Any Other Value ");
				}

				memberids = null;
				claimsProcessor = null;

				Log.log(4, "ReportsAction", "danReport", "Exited");

				return mapping.findForward("success");
			}

			String memberId = bankId;
			dan = this.reportManager.getDanReportForBank(startDate, endDate,
					memberId, ssi);
			dynaForm.set("dan", dan);
			if ((dan == null) || (dan.size() == 0)) {
				throw new NoDataException(
						"No Data is available for the values entered, Please Enter Any Other Value ");
			}

			memberids = null;
			claimsProcessor = null;

			Log.log(4, "ReportsAction", "danReport", "Exited");

			return mapping.findForward("success");
		}

		throw new NoDataException("Enter Valid Member Id");
	}

	public ActionForward gfdanReport(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		Log.log(4, "ReportsAction", "gfdanReport", "Entered");
		ArrayList gfdan = new ArrayList();
		DynaActionForm dynaForm = (DynaActionForm) form;
		java.sql.Date startDate = null;
		java.sql.Date endDate = null;
		java.util.Date sDate = (java.util.Date) dynaForm
				.get("dateOfTheDocument20");
		String stDate = String.valueOf(sDate);
		if ((stDate == null) || (stDate.equals(""))) {
			startDate = null;
		} else if (stDate != null) {
			startDate = new java.sql.Date(sDate.getTime());
		}

		java.util.Date eDate = (java.util.Date) dynaForm
				.get("dateOfTheDocument21");
		if (eDate != null || !eDate.equals("")) {
			endDate = new java.sql.Date(eDate.getTime());
		}

		String id = (String) dynaForm.get("memberId");

		String ssi = (String) dynaForm.get("ssi");

		ClaimsProcessor claimsProcessor = new ClaimsProcessor();
		Vector memberids = new Vector();
		memberids = claimsProcessor.getAllMemberIds();

		if ((id == null) || (id.equals(""))) {
			gfdan = this.reportManager.getGFDanReport(startDate, endDate, id,
					ssi);
			dynaForm.set("gfdan", gfdan);
			if ((gfdan == null) || (gfdan.size() == 0)) {
				throw new NoDataException(
						"No Data is available for the values entered, Please Enter Any Other Value ");
			}

			memberids = null;
			claimsProcessor = null;
			Log.log(4, "ReportsAction", "gfdanReport", "Exited");

			return mapping.findForward("success");
		}

		if (memberids.contains(id)) {
			String bankId = id.substring(0, 4);

			String zoneId = id.substring(4, 8);

			String branchId = id.substring(8, 12);

			if (!branchId.equals("0000")) {
				String memberId = bankId + zoneId + branchId;
				gfdan = this.reportManager.getGFDanReportForBranch(startDate,
						endDate, memberId, ssi);
				dynaForm.set("gfdan", gfdan);
				if ((gfdan == null) || (gfdan.size() == 0)) {
					throw new NoDataException(
							"No Data is available for the values entered, Please Enter Any Other Value ");
				}

				memberids = null;
				claimsProcessor = null;

				Log.log(4, "ReportsAction", "gfdanReport", "Exited");

				return mapping.findForward("success");
			}

			if (!zoneId.equals("0000")) {
				gfdan = this.reportManager.getGFDanReportForZone(startDate,
						endDate, bankId, ssi, zoneId);
				dynaForm.set("gfdan", gfdan);
				if ((gfdan == null) || (gfdan.size() == 0)) {
					throw new NoDataException(
							"No Data is available for the values entered, Please Enter Any Other Value ");
				}

				memberids = null;
				claimsProcessor = null;

				Log.log(4, "ReportsAction", "gfdanReport", "Exited");

				return mapping.findForward("success");
			}

			String memberId = bankId;
			gfdan = this.reportManager.getGFDanReportForBank(startDate,
					endDate, memberId, ssi);
			dynaForm.set("gfdan", gfdan);
			if ((gfdan == null) || (gfdan.size() == 0)) {
				throw new NoDataException(
						"No Data is available for the values entered, Please Enter Any Other Value ");
			}

			memberids = null;
			claimsProcessor = null;

			Log.log(4, "ReportsAction", "gfdanReport", "Exited");

			return mapping.findForward("success");
		}

		throw new NoDataException("Enter Valid Member Id");
	}

	public ActionForward asfdanReport(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		Log.log(4, "ReportsAction", "asfdanReport", "Entered");
		//System.out.println("asfdanReport");
		ArrayList asfdan = new ArrayList();
		DynaActionForm dynaForm = (DynaActionForm) form;
		java.sql.Date startDate = null;
		java.sql.Date endDate = null;
		java.util.Date sDate = (java.util.Date) dynaForm
				.get("dateOfTheDocument20");
		String stDate = String.valueOf(sDate);
		if ((stDate == null) || (stDate.equals(""))) {
			startDate = null;
		} else if (stDate != null) {
			startDate = new java.sql.Date(sDate.getTime());
		}

		java.util.Date eDate = (java.util.Date) dynaForm
				.get("dateOfTheDocument21");
		endDate = new java.sql.Date(eDate.getTime());

		String id = (String) dynaForm.get("memberId");

		String ssi = (String) dynaForm.get("ssi");

		ClaimsProcessor claimsProcessor = new ClaimsProcessor();
		Vector memberids = new Vector();
		memberids = claimsProcessor.getAllMemberIds();

		if ((id == null) || (id.equals(""))) {
			asfdan = this.reportManager.getASFDanReport(startDate, endDate, id,
					ssi);
			dynaForm.set("asfdan", asfdan);
			if ((asfdan == null) || (asfdan.size() == 0)) {
				throw new NoDataException(
						"No Data is available for the values entered, Please Enter Any Other Value ");
			}

			memberids = null;
			claimsProcessor = null;
			Log.log(4, "ReportsAction", "asfdanReport", "Exited");

			return mapping.findForward("success");
		}

		if (memberids.contains(id)) {
			String bankId = id.substring(0, 4);

			String zoneId = id.substring(4, 8);

			String branchId = id.substring(8, 12);

			if (!branchId.equals("0000")) {
				String memberId = bankId + zoneId + branchId;
				asfdan = this.reportManager.getASFDanReportForBranch(startDate,
						endDate, memberId, ssi);
				dynaForm.set("asfdan", asfdan);
				if ((asfdan == null) || (asfdan.size() == 0)) {
					throw new NoDataException(
							"No Data is available for the values entered, Please Enter Any Other Value ");
				}

				memberids = null;
				claimsProcessor = null;

				Log.log(4, "ReportsAction", "asfdanReport", "Exited");

				return mapping.findForward("success");
			}

			if (!zoneId.equals("0000")) {
				asfdan = this.reportManager.getASFDanReportForZone(startDate,
						endDate, bankId, ssi, zoneId);
				dynaForm.set("asfdan", asfdan);
				if ((asfdan == null) || (asfdan.size() == 0)) {
					throw new NoDataException(
							"No Data is available for the values entered, Please Enter Any Other Value ");
				}

				memberids = null;
				claimsProcessor = null;

				Log.log(4, "ReportsAction", "asfdanReport", "Exited");

				return mapping.findForward("success");
			}

			String memberId = bankId;
			asfdan = this.reportManager.getASFDanReportForBank(startDate,
					endDate, memberId, ssi);
			dynaForm.set("asfdan", asfdan);
			if ((asfdan == null) || (asfdan.size() == 0)) {
				throw new NoDataException(
						"No Data is available for the values entered, Please Enter Any Other Value ");
			}

			memberids = null;
			claimsProcessor = null;

			Log.log(4, "ReportsAction", "asfdanReport", "Exited");

			return mapping.findForward("success");
		}

		throw new NoDataException("Enter Valid Member Id");
	}

	public ActionForward danReportDetails(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		Log.log(4, "ReportsAction", "danReportDetails", "Entered");
		ArrayList danDetails = new ArrayList();
		DynaActionForm dynaForm = (DynaActionForm) form;

		String danId = request.getParameter("danValue");

		String ssiName = (String) dynaForm.get("ssi");

		if ((ssiName == null) || (ssiName.equals(""))) {
			danDetails = this.reportManager.getDanReportDetails(danId);
			dynaForm.set("dan", danDetails);
			if ((danDetails == null) || (danDetails.size() == 0)) {
				throw new NoDataException(
						"No Data is available for the values entered, Please Enter Any Other Value ");
			}

			Log.log(4, "ReportsAction", "danReportDetails", "Exited");

			return mapping.findForward("success");
		}

		danDetails = this.reportManager.getDanReportDetailsForSsi(danId,
				ssiName);
		dynaForm.set("dan", danDetails);
		if ((danDetails == null) || (danDetails.size() == 0)) {
			throw new NoDataException(
					"No Data is available for the values entered, Please Enter Any Other Value ");
		}

		Log.log(4, "ReportsAction", "danReportDetails", "Exited");

		return mapping.findForward("success");
	}

	public ActionForward disbursementReport(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		Log.log(4, "ReportsAction", "disbursementReport", "Entered");
		ArrayList disbursement = new ArrayList();
		DynaActionForm dynaForm = (DynaActionForm) form;
		java.sql.Date startDate = null;
		java.sql.Date endDate = null;
		java.util.Date sDate = (java.util.Date) dynaForm
				.get("dateOfTheDocument18");
		String stDate = String.valueOf(sDate);

		if ((stDate == null) || (stDate.equals("")))
			startDate = null;
		else if (stDate != null)
			startDate = new java.sql.Date(sDate.getTime());
		java.util.Date eDate = (java.util.Date) dynaForm
				.get("dateOfTheDocument19");

		endDate = new java.sql.Date(eDate.getTime());
		String id = (String) dynaForm.get("memberId");
		ClaimsProcessor claimsProcessor = new ClaimsProcessor();
		Vector memberids = new Vector();
		memberids = claimsProcessor.getAllMemberIds();

		if ((id == null) || (id.equals(""))) {
			disbursement = this.reportManager.getDisbursementReport(startDate,
					endDate, id);
			dynaForm.set("disbursement", disbursement);
			if ((disbursement == null) || (disbursement.size() == 0)) {
				throw new NoDataException(
						"No Data is available for the values entered, Please Enter Any Other Value ");
			}

			claimsProcessor = null;
			memberids = null;
			Log.log(4, "ReportsAction", "disbursementReport", "Exited");

			return mapping.findForward("success");
		}

		if (memberids.contains(id)) {
			String bankId = id.substring(0, 4);
			String zoneId = id.substring(4, 8);
			String branchId = id.substring(8, 12);

			if (!branchId.equals("0000")) {
				String memberId = bankId + zoneId + branchId;
				disbursement = this.reportManager
						.getDisbursementReportForBranch(startDate, endDate,
								memberId);
				dynaForm.set("disbursement", disbursement);

				if ((disbursement == null) || (disbursement.size() == 0)) {
					throw new NoDataException(
							"No Data is available for the values entered, Please Enter Any Other Value ");
				}

				claimsProcessor = null;
				memberids = null;
				Log.log(4, "ReportsAction", "disbursementReport", "Exited");

				return mapping.findForward("success");
			}

			if (!zoneId.equals("0000")) {
				disbursement = this.reportManager.getDisbursementReportForZone(
						startDate, endDate, bankId, zoneId);
				dynaForm.set("disbursement", disbursement);

				if ((disbursement == null) || (disbursement.size() == 0)) {
					throw new NoDataException(
							"No Data is available for the values entered, Please Enter Any Other Value ");
				}

				claimsProcessor = null;
				memberids = null;
				Log.log(4, "ReportsAction", "disbursementReport", "Exited");

				return mapping.findForward("success");
			}

			String memberId = bankId;
			disbursement = this.reportManager.getDisbursementReportForBank(
					startDate, endDate, memberId);
			dynaForm.set("disbursement", disbursement);
			if ((disbursement == null) || (disbursement.size() == 0)) {
				throw new NoDataException(
						"No Data is available for the values entered, Please Enter Any Other Value ");
			}

			claimsProcessor = null;
			memberids = null;

			Log.log(4, "ReportsAction", "disbursementReport", "Exited");

			return mapping.findForward("success");
		}

		throw new NoDataException("Please Enter Valid Member Id");
	}

	public ActionForward asfallocatedpaymentReport(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		Log.log(4, "ReportsAction", "asfallocatedpaymentReport", "Entered");
		ArrayList asfallocatedpayment = new ArrayList();
		DynaActionForm dynaForm = (DynaActionForm) form;
		java.sql.Date startDate = null;
		java.sql.Date endDate = null;
		java.util.Date sDate = (java.util.Date) dynaForm
				.get("dateOfTheDocument16");
		String stDate = String.valueOf(sDate);

		if ((stDate == null) || (stDate.equals("")))
			startDate = null;
		else if (stDate != null) {
			startDate = new java.sql.Date(sDate.getTime());
		}
		java.util.Date eDate = (java.util.Date) dynaForm
				.get("dateOfTheDocument17");
		endDate = new java.sql.Date(eDate.getTime());
		String id = (String) dynaForm.get("memberId");

		ClaimsProcessor claimsProcessor = new ClaimsProcessor();
		Vector memberids = new Vector();
		memberids = claimsProcessor.getAllMemberIds();

		if ((id == null) || (id.equals(""))) {
			asfallocatedpayment = this.reportManager.getAllocatedPaymentReport(
					startDate, endDate, id);
			dynaForm.set("asfallocatedpayment", asfallocatedpayment);
			if ((asfallocatedpayment == null)
					|| (asfallocatedpayment.size() == 0)) {
				throw new NoDataException(
						"No Data is available for the values entered, Please Enter Any Other Value ");
			}

			claimsProcessor = null;
			memberids = null;
			Log.log(4, "ReportsAction", "asfallocatedpaymentReport", "Exited");

			return mapping.findForward("success");
		}

		if (memberids.contains(id)) {
			String bankId = id.substring(0, 4);
			String zoneId = id.substring(4, 8);
			String branchId = id.substring(8, 12);

			if (!branchId.equals("0000")) {
				String memberId = bankId + zoneId + branchId;

				asfallocatedpayment = this.reportManager
						.getAllocatedPaymentReportForBranch(startDate, endDate,
								memberId);
				dynaForm.set("asfallocatedpayment", asfallocatedpayment);
				if ((asfallocatedpayment == null)
						|| (asfallocatedpayment.size() == 0)) {
					throw new NoDataException(
							"No Data is available for the values entered, Please Enter Any Other Value ");
				}

				claimsProcessor = null;
				memberids = null;
				Log.log(4, "ReportsAction", "asfallocatedpaymentReport",
						"Exited");

				return mapping.findForward("success");
			}

			if (!zoneId.equals("0000")) {
				asfallocatedpayment = this.reportManager
						.getAllocatePaymentReportForZone(startDate, endDate,
								bankId, zoneId);
				dynaForm.set("asfallocatedpayment", asfallocatedpayment);
				if ((asfallocatedpayment == null)
						|| (asfallocatedpayment.size() == 0)) {
					throw new NoDataException(
							"No Data is available for the values entered, Please Enter Any Other Value ");
				}

				claimsProcessor = null;
				memberids = null;
				Log.log(4, "ReportsAction", "asfallocatedpaymentReport",
						"Exited");

				return mapping.findForward("success");
			}

			String memberId = bankId;
			asfallocatedpayment = this.reportManager
					.getAllocatePaymentReportForBank(startDate, endDate,
							memberId);
			dynaForm.set("asfallocatedpayment", asfallocatedpayment);
			if ((asfallocatedpayment == null)
					|| (asfallocatedpayment.size() == 0)) {
				throw new NoDataException(
						"No Data is available for the values entered, Please Enter Any Other Value ");
			}

			claimsProcessor = null;
			memberids = null;
			Log.log(4, "ReportsAction", "asfallocatedpaymentReport", "Exited");

			return mapping.findForward("success");
		}

		throw new NoDataException("Please Enter Valid Member Id");
	}

	// bhu bug fix
	public ActionForward gfallocatedpaymentReport(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		Log.log(4, "ReportsAction", "gfallocatedpaymentReport", "Entered");
		ArrayList gfallocatedpayment = new ArrayList();
		DynaActionForm dynaForm = (DynaActionForm) form;
		User user = getUserInformation(request);
		String bank = user.getBankId();
		String zone = user.getZoneId();
		String branch = user.getBranchId();

		java.sql.Date startDate = null;
		java.sql.Date endDate = null;
		java.util.Date sDate = (java.util.Date) dynaForm
				.get("dateOfTheDocument16");
		String stDate = String.valueOf(sDate);

		if ((stDate == null) || (stDate.equals("")))
			startDate = null;
		else if (stDate != null) {
			startDate = new java.sql.Date(sDate.getTime());
		}
		java.util.Date eDate = (java.util.Date) dynaForm
				.get("dateOfTheDocument17");
		endDate = new java.sql.Date(eDate.getTime());
		String id = (String) dynaForm.get("memberId");

		ClaimsProcessor claimsProcessor = new ClaimsProcessor();
		Vector memberids = new Vector();
		memberids = claimsProcessor.getAllMemberIds();

		if ((id == null) || (id.equals(""))) {
			gfallocatedpayment = this.reportManager
					.getGFAllocatedPaymentReport(startDate, endDate, id);
			dynaForm.set("gfallocatedpayment", gfallocatedpayment);
			if ((gfallocatedpayment == null)
					|| (gfallocatedpayment.size() == 0)) {
				throw new NoDataException(
						"No Data is available for the values entered, Please Enter Any Other Value ");
			}

			claimsProcessor = null;
			memberids = null;
			Log.log(4, "ReportsAction", "gfallocatedpaymentReport", "Exited");

			return mapping.findForward("success");
		}

		if (memberids.contains(id)) {
			String bankId = id.substring(0, 4);
			String zoneId = id.substring(4, 8);
			String branchId = id.substring(8, 12);

			if (!branchId.equals("0000")) {
				String memberId = bankId + zoneId + branchId;

				gfallocatedpayment = this.reportManager
						.getGFAllocatedPaymentReportForBranch(startDate,
								endDate, memberId);
				dynaForm.set("gfallocatedpayment", gfallocatedpayment);
				if ((gfallocatedpayment == null)
						|| (gfallocatedpayment.size() == 0)) {
					throw new NoDataException(
							"No Data is available for the values entered, Please Enter Any Other Value ");
				}

				claimsProcessor = null;
				memberids = null;
				Log.log(4, "ReportsAction", "gfallocatedpaymentReport",
						"Exited");

				return mapping.findForward("success");
			}

			if (!zoneId.equals("0000")) {
				gfallocatedpayment = this.reportManager
						.getGFAllocatePaymentReportForZone(startDate, endDate,
								bankId, zoneId);
				dynaForm.set("gfallocatedpayment", gfallocatedpayment);
				if ((gfallocatedpayment == null)
						|| (gfallocatedpayment.size() == 0)) {
					throw new NoDataException(
							"No Data is available for the values entered, Please Enter Any Other Value ");
				}

				claimsProcessor = null;
				memberids = null;
				Log.log(4, "ReportsAction", "gfallocatedpaymentReport",
						"Exited");

				return mapping.findForward("success");
			}

			String memberId = bankId;
			gfallocatedpayment = this.reportManager
					.getGFAllocatePaymentReportForBank(startDate, endDate,
							memberId);
			dynaForm.set("gfallocatedpayment", gfallocatedpayment);
			if ((gfallocatedpayment == null)
					|| (gfallocatedpayment.size() == 0)) {
				throw new NoDataException(
						"No Data is available for the values entered, Please Enter Any Other Value ");
			}

			claimsProcessor = null;
			memberids = null;
			Log.log(4, "ReportsAction", "gfallocatedpaymentReport", "Exited");

			return mapping.findForward("success");
		}

		throw new NoDataException("Please Enter Valid Member Id");
	}

	public ActionForward paymentReport(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		Log.log(4, "ReportsAction", "paymentReport", "Entered");
		ArrayList payment = new ArrayList();
		DynaActionForm dynaForm = (DynaActionForm) form;
		java.sql.Date startDate = null;
		java.sql.Date endDate = null;
		java.util.Date sDate = (java.util.Date) dynaForm
				.get("dateOfTheDocument16");
		String stDate = String.valueOf(sDate);

		if ((stDate == null) || (stDate.equals("")))
			startDate = null;
		else if (stDate != null) {
			startDate = new java.sql.Date(sDate.getTime());
		}
		java.util.Date eDate = (java.util.Date) dynaForm
				.get("dateOfTheDocument17");
		endDate = new java.sql.Date(eDate.getTime());
		String id = (String) dynaForm.get("memberId");

		ClaimsProcessor claimsProcessor = new ClaimsProcessor();
		Vector memberids = new Vector();
		memberids = claimsProcessor.getAllMemberIds();

		if ((id == null) || (id.equals(""))) {
			payment = this.reportManager.getPaymentReport(startDate, endDate,
					id);
			dynaForm.set("payment", payment);
			if ((payment == null) || (payment.size() == 0)) {
				throw new NoDataException(
						"No Data is available for the values entered, Please Enter Any Other Value ");
			}

			claimsProcessor = null;
			memberids = null;
			Log.log(4, "ReportsAction", "paymentReport", "Exited");

			return mapping.findForward("success");
		}

		if (memberids.contains(id)) {
			String bankId = id.substring(0, 4);
			String zoneId = id.substring(4, 8);
			String branchId = id.substring(8, 12);

			if (!branchId.equals("0000")) {
				String memberId = bankId + zoneId + branchId;

				payment = this.reportManager.getPaymentReportForBranch(
						startDate, endDate, memberId);
				dynaForm.set("payment", payment);
				if ((payment == null) || (payment.size() == 0)) {
					throw new NoDataException(
							"No Data is available for the values entered, Please Enter Any Other Value ");
				}

				claimsProcessor = null;
				memberids = null;
				Log.log(4, "ReportsAction", "paymentReport", "Exited");

				return mapping.findForward("success");
			}

			if (!zoneId.equals("0000")) {
				payment = this.reportManager.getPaymentReportForZone(startDate,
						endDate, bankId, zoneId);
				dynaForm.set("payment", payment);
				if ((payment == null) || (payment.size() == 0)) {
					throw new NoDataException(
							"No Data is available for the values entered, Please Enter Any Other Value ");
				}

				claimsProcessor = null;
				memberids = null;
				Log.log(4, "ReportsAction", "paymentReport", "Exited");

				return mapping.findForward("success");
			}

			String memberId = bankId;

			payment = this.reportManager.getPaymentReportForBank(startDate,
					endDate, memberId);
			dynaForm.set("payment", payment);
			if ((payment == null) || (payment.size() == 0)) {
				throw new NoDataException(
						"No Data is available for the values entered, Please Enter Any Other Value ");
			}

			claimsProcessor = null;
			memberids = null;
			Log.log(4, "ReportsAction", "paymentReport", "Exited");

			return mapping.findForward("success");
		}

		throw new NoDataException("Please Enter Valid Member Id");
	}

	public ActionForward dailypaymentReportDetails(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		Log.log(4, "ReportsAction", "dailypaymentReportDetails", "Entered");
		ArrayList dailypaymentDetails = new ArrayList();
		DynaActionForm dynaForm = (DynaActionForm) form;

		String id = request.getParameter("memId");

		java.sql.Date startDate = null;
		java.sql.Date endDate = null;

		String stDate = request.getParameter("date");

		SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
		try {
			java.util.Date d = dateFormat.parse(stDate);
			dateFormat.applyPattern("yyyy-MM-dd");
			stDate = dateFormat.format(d);
		} catch (Exception e) {
			e.printStackTrace();
		}

		startDate = java.sql.Date.valueOf(stDate);
		endDate = java.sql.Date.valueOf(stDate);

		ClaimsProcessor claimsProcessor = new ClaimsProcessor();
		Vector memberids = new Vector();
		memberids = claimsProcessor.getAllMemberIds();

		if ((id == null) || (id.equals(""))) {
			dailypaymentDetails = this.reportManager.getPaymentReport(
					startDate, endDate, id);
			dynaForm.set("dailypaymentDetails", dailypaymentDetails);
			if ((dailypaymentDetails == null)
					|| (dailypaymentDetails.size() == 0)) {
				throw new NoDataException(
						"No Data is available for the values entered, Please Enter Any Other Value ");
			}

			claimsProcessor = null;
			memberids = null;
			Log.log(4, "ReportsAction", "dailypaymentReportDetails", "Exited");

			return mapping.findForward("success");
		}
		if (memberids.contains(id)) {
			String bankId = id.substring(0, 4);
			String zoneId = id.substring(4, 8);
			String branchId = id.substring(8, 12);

			if (!branchId.equals("0000")) {
				String memberId = bankId + zoneId + branchId;

				dailypaymentDetails = this.reportManager
						.getPaymentReportForBranch(startDate, endDate, memberId);
				dynaForm.set("dailypaymentDetails", dailypaymentDetails);
				if ((dailypaymentDetails == null)
						|| (dailypaymentDetails.size() == 0)) {
					throw new NoDataException(
							"No Data is available for the values entered, Please Enter Any Other Value ");
				}

				claimsProcessor = null;
				memberids = null;
				Log.log(4, "ReportsAction", "dailypaymentReportDetails",
						"Exited");

				return mapping.findForward("success");
			}

			if (!zoneId.equals("0000")) {
				dailypaymentDetails = this.reportManager
						.getPaymentReportForZone(startDate, endDate, bankId,
								zoneId);
				dynaForm.set("dailypaymentDetails", dailypaymentDetails);
				if ((dailypaymentDetails == null)
						|| (dailypaymentDetails.size() == 0)) {
					throw new NoDataException(
							"No Data is available for the values entered, Please Enter Any Other Value ");
				}

				claimsProcessor = null;
				memberids = null;
				Log.log(4, "ReportsAction", "dailypaymentReportDetails",
						"Exited");

				return mapping.findForward("success");
			}

			String memberId = bankId;

			dailypaymentDetails = this.reportManager.getPaymentReportForBank(
					startDate, endDate, memberId);
			dynaForm.set("dailypaymentDetails", dailypaymentDetails);
			if ((dailypaymentDetails == null)
					|| (dailypaymentDetails.size() == 0)) {
				throw new NoDataException(
						"No Data is available for the values entered, Please Enter Any Other Value ");
			}

			claimsProcessor = null;
			memberids = null;
			Log.log(4, "ReportsAction", "dailypaymentReportDetails", "Exited");

			return mapping.findForward("success");
		}

		return mapping.findForward("success");
	}

	public ActionForward dailyDCHpaymentReportDetails(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		Log.log(4, "ReportsAction", "dailyDCHpaymentReportDetails", "Entered");
		ArrayList dailydchpaymentDetails = new ArrayList();
		DynaActionForm dynaForm = (DynaActionForm) form;
		String id = request.getParameter("memId");
		java.sql.Date startDate = null;
		java.sql.Date endDate = null;

		String stDate = request.getParameter("date");

		SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
		try {
			java.util.Date d = dateFormat.parse(stDate);
			dateFormat.applyPattern("yyyy-MM-dd");
			stDate = dateFormat.format(d);
		} catch (Exception e) {
			e.printStackTrace();
		}

		startDate = java.sql.Date.valueOf(stDate);
		endDate = java.sql.Date.valueOf(stDate);

		ClaimsProcessor claimsProcessor = new ClaimsProcessor();
		Vector memberids = new Vector();
		memberids = claimsProcessor.getAllMemberIds();

		if ((id == null) || (id.equals(""))) {
			dailydchpaymentDetails = this.reportManager.getDCHPaymentReport(
					startDate, endDate, id);
			dynaForm.set("dailydchpaymentDetails", dailydchpaymentDetails);
			if ((dailydchpaymentDetails == null)
					|| (dailydchpaymentDetails.size() == 0)) {
				throw new NoDataException(
						"No Data is available for the values entered, Please Enter Any Other Value ");
			}

			claimsProcessor = null;
			memberids = null;
			Log.log(4, "ReportsAction", "dailypaymentReportDetails", "Exited");

			return mapping.findForward("success");
		}
		if (memberids.contains(id)) {
			String bankId = id.substring(0, 4);
			String zoneId = id.substring(4, 8);
			String branchId = id.substring(8, 12);

			if (!branchId.equals("0000")) {
				String memberId = bankId + zoneId + branchId;

				dailydchpaymentDetails = this.reportManager
						.getDCHPaymentReportForBranch(startDate, endDate,
								memberId);
				dynaForm.set("dailydchpaymentDetails", dailydchpaymentDetails);
				if ((dailydchpaymentDetails == null)
						|| (dailydchpaymentDetails.size() == 0)) {
					throw new NoDataException(
							"No Data is available for the values entered, Please Enter Any Other Value ");
				}

				claimsProcessor = null;
				memberids = null;
				Log.log(4, "ReportsAction", "dailypaymentReportDetails",
						"Exited");

				return mapping.findForward("success");
			}

			if (!zoneId.equals("0000")) {
				dailydchpaymentDetails = this.reportManager
						.getDCHPaymentReportForZone(startDate, endDate, bankId,
								zoneId);
				dynaForm.set("dailydchpaymentDetails", dailydchpaymentDetails);
				if ((dailydchpaymentDetails == null)
						|| (dailydchpaymentDetails.size() == 0)) {
					throw new NoDataException(
							"No Data is available for the values entered, Please Enter Any Other Value ");
				}

				claimsProcessor = null;
				memberids = null;
				Log.log(4, "ReportsAction", "dailypaymentReportDetails",
						"Exited");

				return mapping.findForward("success");
			}

			String memberId = bankId;

			dailydchpaymentDetails = this.reportManager
					.getDCHPaymentReportForBank(startDate, endDate, memberId);
			dynaForm.set("dailydchpaymentDetails", dailydchpaymentDetails);
			if ((dailydchpaymentDetails == null)
					|| (dailydchpaymentDetails.size() == 0)) {
				throw new NoDataException(
						"No Data is available for the values entered, Please Enter Any Other Value ");
			}

			claimsProcessor = null;
			memberids = null;
			Log.log(4, "ReportsAction", "dailyDCHpaymentReportDetails",
					"Exited");

			return mapping.findForward("success");
		}

		return mapping.findForward("success");
	}

	public ActionForward dailyasfpaymentReportDetails(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		Log.log(4, "ReportsAction", "dailyasfpaymentReportDetails", "Entered");
		ArrayList dailypaymentDetails = new ArrayList();
		DynaActionForm dynaForm = (DynaActionForm) form;

		String id = request.getParameter("memId");

		java.sql.Date startDate = null;
		java.sql.Date endDate = null;

		String stDate = request.getParameter("date");

		SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
		try {
			java.util.Date d = dateFormat.parse(stDate);
			dateFormat.applyPattern("yyyy-MM-dd");
			stDate = dateFormat.format(d);
		} catch (Exception e) {
			e.printStackTrace();
		}

		startDate = java.sql.Date.valueOf(stDate);
		endDate = java.sql.Date.valueOf(stDate);

		ClaimsProcessor claimsProcessor = new ClaimsProcessor();
		Vector memberids = new Vector();
		memberids = claimsProcessor.getAllMemberIds();

		if ((id == null) || (id.equals(""))) {
			dailypaymentDetails = this.reportManager.getASFPaymentReportNew(
					startDate, endDate, id);
			dynaForm.set("dailypaymentDetails", dailypaymentDetails);
			if ((dailypaymentDetails == null)
					|| (dailypaymentDetails.size() == 0)) {
				throw new NoDataException(
						"No Data is available for the values entered, Please Enter Any Other Value ");
			}

			claimsProcessor = null;
			memberids = null;
			Log.log(4, "ReportsAction", "dailyasfpaymentReportDetails",
					"Exited");

			return mapping.findForward("success");
		}
		if (memberids.contains(id)) {
			String bankId = id.substring(0, 4);
			String zoneId = id.substring(4, 8);
			String branchId = id.substring(8, 12);

			if (!branchId.equals("0000")) {
				String memberId = bankId + zoneId + branchId;

				dailypaymentDetails = this.reportManager
						.getASFPaymentReportForBranchNew(startDate, endDate,
								memberId);
				dynaForm.set("dailypaymentDetails", dailypaymentDetails);
				if ((dailypaymentDetails == null)
						|| (dailypaymentDetails.size() == 0)) {
					throw new NoDataException(
							"No Data is available for the values entered, Please Enter Any Other Value ");
				}

				claimsProcessor = null;
				memberids = null;
				Log.log(4, "ReportsAction", "dailyasfpaymentReportDetails",
						"Exited");

				return mapping.findForward("success");
			}

			if (!zoneId.equals("0000")) {
				dailypaymentDetails = this.reportManager
						.getASFPaymentReportForZoneNew(startDate, endDate,
								bankId, zoneId);
				dynaForm.set("dailypaymentDetails", dailypaymentDetails);
				if ((dailypaymentDetails == null)
						|| (dailypaymentDetails.size() == 0)) {
					throw new NoDataException(
							"No Data is available for the values entered, Please Enter Any Other Value ");
				}

				claimsProcessor = null;
				memberids = null;
				Log.log(4, "ReportsAction", "dailyasfpaymentReportDetails",
						"Exited");

				return mapping.findForward("success");
			}

			String memberId = bankId;

			dailypaymentDetails = this.reportManager
					.getASFPaymentReportForBankNew(startDate, endDate, memberId);
			dynaForm.set("dailypaymentDetails", dailypaymentDetails);
			if ((dailypaymentDetails == null)
					|| (dailypaymentDetails.size() == 0)) {
				throw new NoDataException(
						"No Data is available for the values entered, Please Enter Any Other Value ");
			}

			claimsProcessor = null;
			memberids = null;
			Log.log(4, "ReportsAction", "dailyasfpaymentReportDetails",
					"Exited");

			return mapping.findForward("success");
		}

		return mapping.findForward("success");
	}

	public ActionForward dailypaymentReport(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		Log.log(4, "ReportsAction", "dailypaymentReport", "Entered");
		ArrayList dailypayment = new ArrayList();
		DynaActionForm dynaForm = (DynaActionForm) form;
		java.sql.Date startDate = null;
		java.sql.Date endDate = null;
		java.util.Date sDate = (java.util.Date) dynaForm
				.get("dateOfTheDocument16");
		String stDate = String.valueOf(sDate);

		if ((stDate == null) || (stDate.equals("")))
			startDate = null;
		else if (stDate != null) {
			startDate = new java.sql.Date(sDate.getTime());
		}
		java.util.Date eDate = (java.util.Date) dynaForm
				.get("dateOfTheDocument17");

		endDate = new java.sql.Date(eDate.getTime());
		String id = (String) dynaForm.get("memberId");

		ClaimsProcessor claimsProcessor = new ClaimsProcessor();
		Vector memberids = new Vector();
		memberids = claimsProcessor.getAllMemberIds();

		if ((id == null) || (id.equals(""))) {
			dailypayment = this.reportManager.getdailyPaymentReport(startDate,
					endDate, id);
			dynaForm.set("dailypayment", dailypayment);
			if ((dailypayment == null) || (dailypayment.size() == 0)) {
				throw new NoDataException(
						"No Data is available for the values entered, Please Enter Any Other Value ");
			}

			claimsProcessor = null;
			memberids = null;
			Log.log(4, "ReportsAction", "dailypaymentReport", "Exited");

			return mapping.findForward("success");
		}

		if (memberids.contains(id)) {
			String bankId = id.substring(0, 4);
			String zoneId = id.substring(4, 8);
			String branchId = id.substring(8, 12);

			if (!branchId.equals("0000")) {
				String memberId = bankId + zoneId + branchId;

				dailypayment = this.reportManager
						.getdailyPaymentReportForBranch(startDate, endDate,
								memberId);
				dynaForm.set("dailypayment", dailypayment);
				if ((dailypayment == null) || (dailypayment.size() == 0)) {
					throw new NoDataException(
							"No Data is available for the values entered, Please Enter Any Other Value ");
				}

				claimsProcessor = null;
				memberids = null;
				Log.log(4, "ReportsAction", "dailypaymentReport", "Exited");

				return mapping.findForward("success");
			}

			if (!zoneId.equals("0000")) {
				dailypayment = this.reportManager.getdailyPaymentReportForZone(
						startDate, endDate, bankId, zoneId);
				dynaForm.set("dailypayment", dailypayment);
				if ((dailypayment == null) || (dailypayment.size() == 0)) {
					throw new NoDataException(
							"No Data is available for the values entered, Please Enter Any Other Value ");
				}

				claimsProcessor = null;
				memberids = null;
				Log.log(4, "ReportsAction", "dailypaymentReport", "Exited");

				return mapping.findForward("success");
			}

			String memberId = bankId;

			dailypayment = this.reportManager.getdailyPaymentReportForBank(
					startDate, endDate, memberId);
			dynaForm.set("dailypayment", dailypayment);
			if ((dailypayment == null) || (dailypayment.size() == 0)) {
				throw new NoDataException(
						"No Data is available for the values entered, Please Enter Any Other Value ");
			}

			claimsProcessor = null;
			memberids = null;
			Log.log(4, "ReportsAction", "dailypaymentReport", "Exited");

			return mapping.findForward("success");
		}

		throw new NoDataException("Please Enter Valid Member Id");
	}

	public ActionForward dailydchpaymentReport(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		Log.log(4, "ReportsAction", "dailydchpaymentReport", "Entered");
		ArrayList dailydchpayment = new ArrayList();
		DynaActionForm dynaForm = (DynaActionForm) form;
		java.sql.Date startDate = null;
		java.sql.Date endDate = null;
		java.util.Date sDate = (java.util.Date) dynaForm
				.get("dateOfTheDocument16");
		String stDate = String.valueOf(sDate);

		if ((stDate == null) || (stDate.equals("")))
			startDate = null;
		else if (stDate != null) {
			startDate = new java.sql.Date(sDate.getTime());
		}
		java.util.Date eDate = (java.util.Date) dynaForm
				.get("dateOfTheDocument17");

		endDate = new java.sql.Date(eDate.getTime());
		String id = (String) dynaForm.get("memberId");

		ClaimsProcessor claimsProcessor = new ClaimsProcessor();
		Vector memberids = new Vector();
		memberids = claimsProcessor.getAllMemberIds();

		if ((id == null) || (id.equals(""))) {
			dailydchpayment = this.reportManager.getdailydchPaymentReport(
					startDate, endDate, id);
			dynaForm.set("dailydchpayment", dailydchpayment);
			if ((dailydchpayment == null) || (dailydchpayment.size() == 0)) {
				throw new NoDataException(
						"No Data is available for the values entered, Please Enter Any Other Value ");
			}

			claimsProcessor = null;
			memberids = null;
			Log.log(4, "ReportsAction", "dailydchpaymentReport", "Exited");

			return mapping.findForward("success");
		}

		if (memberids.contains(id)) {
			String bankId = id.substring(0, 4);
			String zoneId = id.substring(4, 8);
			String branchId = id.substring(8, 12);

			if (!branchId.equals("0000")) {
				String memberId = bankId + zoneId + branchId;

				dailydchpayment = this.reportManager
						.getdailyDCHPaymentReportForBranch(startDate, endDate,
								memberId);
				dynaForm.set("dailydchpayment", dailydchpayment);
				if ((dailydchpayment == null) || (dailydchpayment.size() == 0)) {
					throw new NoDataException(
							"No Data is available for the values entered, Please Enter Any Other Value ");
				}

				claimsProcessor = null;
				memberids = null;
				Log.log(4, "ReportsAction", "dailydchpaymentReport", "Exited");

				return mapping.findForward("success");
			}

			if (!zoneId.equals("0000")) {
				dailydchpayment = this.reportManager
						.getdailyDCHPaymentReportForZone(startDate, endDate,
								bankId, zoneId);
				dynaForm.set("dailydchpayment", dailydchpayment);
				if ((dailydchpayment == null) || (dailydchpayment.size() == 0)) {
					throw new NoDataException(
							"No Data is available for the values entered, Please Enter Any Other Value ");
				}

				claimsProcessor = null;
				memberids = null;
				Log.log(4, "ReportsAction", "dailydchpaymentReport", "Exited");

				return mapping.findForward("success");
			}

			String memberId = bankId;

			dailydchpayment = this.reportManager
					.getdailyDCHPaymentReportForBank(startDate, endDate,
							memberId);
			dynaForm.set("dailydchpayment", dailydchpayment);
			if ((dailydchpayment == null) || (dailydchpayment.size() == 0)) {
				throw new NoDataException(
						"No Data is available for the values entered, Please Enter Any Other Value ");
			}

			claimsProcessor = null;
			memberids = null;
			Log.log(4, "ReportsAction", "dailydchpaymentReport", "Exited");

			return mapping.findForward("success");
		}

		throw new NoDataException("Please Enter Valid Member Id");
	}

	public ActionForward dailyasfpaymentReport(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		Log.log(4, "ReportsAction", "dailyasfpaymentReport", "Entered");
		ArrayList dailypayment = new ArrayList();
		DynaActionForm dynaForm = (DynaActionForm) form;
		java.sql.Date startDate = null;
		java.sql.Date endDate = null;
		java.util.Date sDate = (java.util.Date) dynaForm
				.get("dateOfTheDocument16");
		String stDate = String.valueOf(sDate);

		if ((stDate == null) || (stDate.equals("")))
			startDate = null;
		else if (stDate != null) {
			startDate = new java.sql.Date(sDate.getTime());
		}
		java.util.Date eDate = (java.util.Date) dynaForm
				.get("dateOfTheDocument17");

		endDate = new java.sql.Date(eDate.getTime());
		String id = (String) dynaForm.get("memberId");

		ClaimsProcessor claimsProcessor = new ClaimsProcessor();
		Vector memberids = new Vector();
		memberids = claimsProcessor.getAllMemberIds();

		if ((id == null) || (id.equals(""))) {
			dailypayment = this.reportManager.getasfdailyPaymentReport(
					startDate, endDate, id);
			dynaForm.set("dailypayment", dailypayment);
			if ((dailypayment == null) || (dailypayment.size() == 0)) {
				throw new NoDataException(
						"No Data is available for the values entered, Please Enter Any Other Value ");
			}

			claimsProcessor = null;
			memberids = null;
			Log.log(4, "ReportsAction", "dailyasfpaymentReport", "Exited");

			return mapping.findForward("success");
		}

		if (memberids.contains(id)) {
			String bankId = id.substring(0, 4);
			String zoneId = id.substring(4, 8);
			String branchId = id.substring(8, 12);

			if (!branchId.equals("0000")) {
				String memberId = bankId + zoneId + branchId;

				dailypayment = this.reportManager
						.getASFdailyPaymentReportForBranch(startDate, endDate,
								memberId);
				dynaForm.set("dailypayment", dailypayment);
				if ((dailypayment == null) || (dailypayment.size() == 0)) {
					throw new NoDataException(
							"No Data is available for the values entered, Please Enter Any Other Value ");
				}

				claimsProcessor = null;
				memberids = null;
				Log.log(4, "ReportsAction", "dailyasfpaymentReport", "Exited");

				return mapping.findForward("success");
			}

			if (!zoneId.equals("0000")) {
				dailypayment = this.reportManager
						.getASFdailyPaymentReportForZone(startDate, endDate,
								bankId, zoneId);
				dynaForm.set("dailypayment", dailypayment);
				if ((dailypayment == null) || (dailypayment.size() == 0)) {
					throw new NoDataException(
							"No Data is available for the values entered, Please Enter Any Other Value ");
				}

				claimsProcessor = null;
				memberids = null;
				Log.log(4, "ReportsAction", "dailyasfpaymentReport", "Exited");

				return mapping.findForward("success");
			}

			String memberId = bankId;

			dailypayment = this.reportManager.getASFdailyPaymentReportForBank(
					startDate, endDate, memberId);
			dynaForm.set("dailypayment", dailypayment);
			if ((dailypayment == null) || (dailypayment.size() == 0)) {
				throw new NoDataException(
						"No Data is available for the values entered, Please Enter Any Other Value ");
			}

			claimsProcessor = null;
			memberids = null;
			Log.log(4, "ReportsAction", "dailyasfpaymentReport", "Exited");

			return mapping.findForward("success");
		}

		throw new NoDataException("Please Enter Valid Member Id");
	}

	public ActionForward asfpaymentReport(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		Log.log(4, "ReportsAction", "asfpaymentReport", "Entered");
		ArrayList asfpayment = new ArrayList();
		DynaActionForm dynaForm = (DynaActionForm) form;
		java.sql.Date startDate = null;
		java.sql.Date endDate = null;
		java.util.Date sDate = (java.util.Date) dynaForm
				.get("dateOfTheDocument16");
		String stDate = String.valueOf(sDate);

		if ((stDate == null) || (stDate.equals("")))
			startDate = null;
		else if (stDate != null) {
			startDate = new java.sql.Date(sDate.getTime());
		}
		java.util.Date eDate = (java.util.Date) dynaForm
				.get("dateOfTheDocument17");
		endDate = new java.sql.Date(eDate.getTime());
		String id = (String) dynaForm.get("memberId");

		ClaimsProcessor claimsProcessor = new ClaimsProcessor();
		Vector memberids = new Vector();
		memberids = claimsProcessor.getAllMemberIds();

		if ((id == null) || (id.equals(""))) {
			asfpayment = this.reportManager.getASFPaymentReport(startDate,
					endDate, id);
			dynaForm.set("asfpayment", asfpayment);
			if ((asfpayment == null) || (asfpayment.size() == 0)) {
				throw new NoDataException(
						"No Data is available for the values entered, Please Enter Any Other Value ");
			}

			claimsProcessor = null;
			memberids = null;
			Log.log(4, "ReportsAction", "asfpaymentReport", "Exited");

			return mapping.findForward("success");
		}

		if (memberids.contains(id)) {
			String bankId = id.substring(0, 4);
			String zoneId = id.substring(4, 8);
			String branchId = id.substring(8, 12);

			if (!branchId.equals("0000")) {
				String memberId = bankId + zoneId + branchId;

				asfpayment = this.reportManager.getASFPaymentReportForBranch(
						startDate, endDate, memberId);
				dynaForm.set("asfpayment", asfpayment);
				if ((asfpayment == null) || (asfpayment.size() == 0)) {
					throw new NoDataException(
							"No Data is available for the values entered, Please Enter Any Other Value ");
				}

				claimsProcessor = null;
				memberids = null;
				Log.log(4, "ReportsAction", "asfpaymentReport", "Exited");

				return mapping.findForward("success");
			}

			if (!zoneId.equals("0000")) {
				asfpayment = this.reportManager.getASFPaymentReportForZone(
						startDate, endDate, bankId, zoneId);
				dynaForm.set("asfpayment", asfpayment);
				if ((asfpayment == null) || (asfpayment.size() == 0)) {
					throw new NoDataException(
							"No Data is available for the values entered, Please Enter Any Other Value ");
				}

				claimsProcessor = null;
				memberids = null;
				Log.log(4, "ReportsAction", "asfpaymentReport", "Exited");

				return mapping.findForward("success");
			}

			String memberId = bankId;

			asfpayment = this.reportManager.getASFPaymentReportForBank(
					startDate, endDate, memberId);
			dynaForm.set("asfpayment", asfpayment);
			if ((asfpayment == null) || (asfpayment.size() == 0)) {
				throw new NoDataException(
						"No Data is available for the values entered, Please Enter Any Other Value ");
			}

			claimsProcessor = null;
			memberids = null;
			Log.log(4, "ReportsAction", "asfpaymentReport", "Exited");

			return mapping.findForward("success");
		}

		throw new NoDataException("Please Enter Valid Member Id");
	}

	public ActionForward listOfMLI(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		Log.log(4, "ReportsAction", "listOfMLI", "Entered");
		ArrayList mli = new ArrayList();
		DynaActionForm dynaForm = (DynaActionForm) form;
		BaseAction baseAction = new BaseAction();
		MLIInfo mliInfo = baseAction.getMemberInfo(request);
		String bankId = mliInfo.getBankId();
		String zoneId = mliInfo.getZoneId();
		String branchId = mliInfo.getBranchId();

		if (!branchId.equals("0000")) {
			String memberId = bankId + zoneId + branchId;
			mli = this.reportManager.getMliListForBranch(memberId);
			dynaForm.set("mli", mli);

			if ((mli == null) || (mli.size() == 0)) {
				throw new NoDataException(
						"No Data is available for the values entered, Please Enter Any Other Value ");
			}

			Log.log(4, "ReportsAction", "listOfMLI", "Exited");

			return mapping.findForward("success1");
		}

		if (!zoneId.equals("0000")) {
			mli = this.reportManager.getMliListForZone(bankId, zoneId);
			dynaForm.set("mli", mli);
			if ((mli == null) || (mli.size() == 0)) {
				throw new NoDataException(
						"No Data is available for the values entered, Please Enter Any Other Value ");
			}

			Log.log(4, "ReportsAction", "listOfMLI", "Exited");

			return mapping.findForward("success1");
		}

		if (!bankId.equals("0000")) {
			String memberId = bankId;
			mli = this.reportManager.getMliListForBank(memberId);
			dynaForm.set("mli", mli);
			if ((mli == null) || (mli.size() == 0)) {
				throw new NoDataException(
						"No Data is available for the values entered, Please Enter Any Other Value ");
			}

			Log.log(4, "ReportsAction", "listOfMLI", "Exited");

			return mapping.findForward("success1");
		}

		mli = this.reportManager.getMliList();
		dynaForm.set("mli", mli);
		if ((mli == null) || (mli.size() == 0)) {
			throw new NoDataException(
					"No Data is available for the values entered, Please Enter Any Other Value ");
		}

		Log.log(4, "ReportsAction", "listOfMLI", "Exited");

		return mapping.findForward("success");
	}

	public ActionForward listOfMLIPath(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		Log.log(4, "ReportsAction", "listOfMLIPath", "Entered");
		ArrayList mli = new ArrayList();
		DynaActionForm dynaForm = (DynaActionForm) form;

		BaseAction baseAction = new BaseAction();
		MLIInfo mliInfo = baseAction.getMemberInfo(request);

		mli = this.reportManager.getMliList();
		dynaForm.set("mli", mli);
		if ((mli == null) || (mli.size() == 0)) {
			throw new NoDataException(
					"No Data is available for the values entered, Please Enter Any Other Value ");
		}

		mli = null;
		Log.log(4, "ReportsAction", "listOfMLIPath", "Exited");

		return mapping.findForward("success1");
	}

	public ActionForward showMliListPath(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		Log.log(4, "ReportsAction", "showMliListPath", "Entered");
		ArrayList mli = new ArrayList();
		DynaActionForm dynaForm = (DynaActionForm) form;
		mli = this.reportManager.getAppListMliWise();
		dynaForm.set("mli", mli);
		String forward = "";
		if ((mli == null) || (mli.size() == 0)) {
			Log.log(4, "ReportsAction", "showMliListPath", "Exited");
			request.setAttribute("message",
					"No Applications for Approval Available");
			forward = "success";
		} else {
			mli = null;
			Log.log(4, "ReportsAction", "showMliListPath", "Exited");
			forward = "approvalPageList";
		}

		return mapping.findForward(forward);
	}

	public ActionForward showClaimMliListPath(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		Log.log(4, "ReportsAction", "showClaimMliListPath", "Entered");
		ArrayList mli = new ArrayList();
		DynaActionForm dynaForm = (DynaActionForm) form;
		mli = this.reportManager.getClaimAppListMliWise();
		dynaForm.set("mli", mli);
		String forward = "";
		if ((mli == null) || (mli.size() == 0)) {
			Log.log(4, "ReportsAction", "showClaimMliListPath", "Exited");
			request.setAttribute("message",
					"No Forwarded Applications for Claim Approval Available");
			forward = "success";
		} else {
			mli = null;
			Log.log(4, "ReportsAction", "showClaimMliListPath", "Exited");
			forward = "clmapprovalPageList";
		}

		return mapping.findForward(forward);
	}

	public ActionForward listOfMLIReport(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		Log.log(4, "ReportsAction", "listOfMLIReport", "Entered");
		ArrayList mliReport = new ArrayList();
		DynaActionForm dynaForm = (DynaActionForm) form;
		String bank = request.getParameter("Link");

		mliReport = this.reportManager.getMliListReport(bank);
		dynaForm.set("mliReport", mliReport);

		request.setAttribute("BANKNAME", bank);

		if ((mliReport == null) || (mliReport.size() == 0)) {
			throw new NoDataException(
					"No Data is available for the values entered, Please Enter Any Other Value ");
		}

		Log.log(4, "ReportsAction", "listOfMLIReport", "Exited");

		return mapping.findForward("success");
	}

	public ActionForward mliWisePendingReport(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		Log.log(4, "ReportsAction", "mliWisePendingReport", "Entered");
		ArrayList mliPendingReport = new ArrayList();
		DynaActionForm dynaForm = (DynaActionForm) form;
		mliPendingReport = this.reportManager.getMliPendingReport();
		dynaForm.set("mliPending", mliPendingReport);
		if ((mliPendingReport == null) || (mliPendingReport.size() == 0)) {
			throw new NoDataException("There are no Pending  Applications");
		}

		mliPendingReport = null;
		Log.log(4, "ReportsAction", "mliWisePendingReport", "Exited");

		return mapping.findForward("success");
	}

	public ActionForward stateWiseReport(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		Log.log(4, "ReportsAction", "stateWiseReport", "Entered");

		DynaActionForm dynaForm = (DynaActionForm) form;
		java.util.Date date = new java.util.Date();
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(date);
		int month = calendar.get(2);
		int day = calendar.get(5);
		month -= 1;
		day += 1;
		calendar.set(2, month);
		calendar.set(5, day);
		java.util.Date prevDate = calendar.getTime();

		GeneralReport generalReport = new GeneralReport();
		generalReport.setDateOfTheDocument10(prevDate);
		generalReport.setDateOfTheDocument11(date);
		BeanUtils.copyProperties(dynaForm, generalReport);
		Log.log(4, "ReportsAction", "stateWiseReport", "Exited");

		return mapping.findForward("success");
	}

	public ActionForward stateWiseReportDetails(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		Log.log(4, "ReportsAction", "stateWiseReportDetails", "Entered");
		DynaActionForm dynaForm = (DynaActionForm) form;
		ArrayList mliReport = new ArrayList();
		java.sql.Date startDate = null;
		java.sql.Date endDate = null;
		java.util.Date sDate = (java.util.Date) dynaForm
				.get("dateOfTheDocument10");
		String stDate = String.valueOf(sDate);

		if ((stDate == null) || (stDate.equals("")))
			startDate = null;
		else if (stDate != null) {
			startDate = new java.sql.Date(sDate.getTime());
		}
		java.util.Date eDate = (java.util.Date) dynaForm
				.get("dateOfTheDocument11");
		endDate = new java.sql.Date(eDate.getTime());

		String id = (String) dynaForm.get("checkValue");

		BaseAction baseAction = new BaseAction();
		MLIInfo mliInfo = baseAction.getMemberInfo(request);
		String bankId = mliInfo.getBankId();
		String zoneId = mliInfo.getZoneId();
		String branchId = mliInfo.getBranchId();

		if (id.equals("yes"))
			request.setAttribute("radioValue", "Guarantee Approved");
		else if (id.equals("no")) {
			request.setAttribute("radioValue", "Guarantee Issued");
		}
		if (!branchId.equals("0000")) {
			String memberId = bankId + zoneId + branchId;

			mliReport = this.reportManager.getStateBranchApplicationDetails(
					startDate, endDate, id, memberId);
			dynaForm.set("mliWiseReport", mliReport);
			if ((mliReport == null) || (mliReport.size() == 0)) {
				throw new NoDataException(
						"No Data is available for the values entered, Please Enter Any Other Value ");
			}

			mliReport = null;
			Log.log(4, "ReportsAction", "stateWiseReportDetails", "Exited");

			return mapping.findForward("success1");
		}

		if (!zoneId.equals("0000")) {
			String memberId = bankId + zoneId;

			mliReport = this.reportManager.getStateZoneApplicationDetails(
					startDate, endDate, id, memberId);
			dynaForm.set("mliWiseReport", mliReport);
			if ((mliReport == null) || (mliReport.size() == 0)) {
				throw new NoDataException(
						"No Data is available for the values entered, Please Enter Any Other Value ");
			}

			mliReport = null;

			Log.log(4, "ReportsAction", "stateWiseReportDetails", "Exited");

			return mapping.findForward("success2");
		}

		if (!bankId.equals("0000")) {
			String memberId = bankId;
			mliReport = this.reportManager.getStateBankApplicationDetails(
					startDate, endDate, id, memberId);
			dynaForm.set("mliWiseReport", mliReport);
			if ((mliReport == null) || (mliReport.size() == 0)) {
				throw new NoDataException(
						"No Data is available for the values entered, Please Enter Any Other Value ");
			}

			mliReport = null;
			Log.log(4, "ReportsAction", "stateWiseReportDetails", "Exited");

			return mapping.findForward("success3");
		}

		mliReport = this.reportManager.getStateWiseReportDetails(startDate,
				endDate, id);
		dynaForm.set("mliWiseReport", mliReport);
		if ((mliReport == null) || (mliReport.size() == 0)) {
			throw new NoDataException(
					"No Data is available for the values entered, Please Enter Any Other Value ");
		}

		mliReport = null;
		Log.log(4, "ReportsAction", "stateWiseReportDetails", "Exited");

		return mapping.findForward("success");
	}

	public ActionForward stateDetailsForState(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		Log.log(4, "ReportsAction", "stateDetailsForState", "Entered");
		DynaActionForm dynaForm = (DynaActionForm) form;
		ArrayList mliReport = new ArrayList();
		java.sql.Date startDate = null;
		java.sql.Date endDate = null;
		java.util.Date sDate = (java.util.Date) dynaForm
				.get("dateOfTheDocument10");
		String stDate = String.valueOf(sDate);
		if ((stDate == null) || (stDate.equals(""))) {
			startDate = null;
		} else if (stDate != null) {
			startDate = new java.sql.Date(sDate.getTime());
		}

		java.util.Date eDate = (java.util.Date) dynaForm
				.get("dateOfTheDocument11");
		endDate = new java.sql.Date(eDate.getTime());
		String id = (String) dynaForm.get("checkValue");

		BaseAction baseAction = new BaseAction();
		MLIInfo mliInfo = baseAction.getMemberInfo(request);
		String bankId = mliInfo.getBankId();
		String zoneId = mliInfo.getZoneId();
		String branchId = mliInfo.getBranchId();

		if (!branchId.equals("0000")) {
			String memberId = bankId + zoneId + branchId;
			mliReport = this.reportManager.getBranchApplicationDetailsForState(
					startDate, endDate, id, memberId);
			dynaForm.set("mliWiseReport", mliReport);
			if ((mliReport == null) || (mliReport.size() == 0)) {
				throw new NoDataException(
						"No Data is available for the values entered, Please Enter Any Other Value ");
			}

			mliReport = null;
			Log.log(4, "ReportsAction", "stateDetailsForState", "Exited");

			return mapping.findForward("success1");
		}

		if (!zoneId.equals("0000")) {
			String memberId = bankId + zoneId;
			mliReport = this.reportManager.getZoneApplicationDetailsForState(
					startDate, endDate, id, memberId);
			dynaForm.set("mliWiseReport", mliReport);
			if ((mliReport == null) || (mliReport.size() == 0)) {
				throw new NoDataException(
						"No Data is available for the values entered, Please Enter Any Other Value ");
			}

			mliReport = null;
			Log.log(4, "ReportsAction", "stateDetailsForState", "Exited");

			return mapping.findForward("success2");
		}

		if (!bankId.equals("0000")) {
			String memberId = bankId;
			mliReport = this.reportManager.getBankApplicationDetailsForState(
					startDate, endDate, id, memberId);
			dynaForm.set("mliWiseReport", mliReport);
			if ((mliReport == null) || (mliReport.size() == 0)) {
				throw new NoDataException(
						"No Data is available for the values entered, Please Enter Any Other Value ");
			}

			mliReport = null;
			Log.log(4, "ReportsAction", "stateDetailsForState", "Exited");

			return mapping.findForward("success3");
		}

		mliReport = this.reportManager.getMliApplicationDetails(startDate,
				endDate, id);
		dynaForm.set("mliWiseReport", mliReport);
		if ((mliReport == null) || (mliReport.size() == 0)) {
			throw new NoDataException(
					"No Data is available for the values entered, Please Enter Any Other Value ");
		}

		mliReport = null;
		Log.log(4, "ReportsAction", "stateDetailsForState", "Exited");

		return mapping.findForward("success");
	}

	public ActionForward districtDetailsForState(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		Log.log(4, "ReportsAction", "districtDetailsForState", "Entered");
		DynaActionForm dynaForm = (DynaActionForm) form;
		ArrayList mliReport = new ArrayList();
		java.sql.Date startDate = null;
		java.sql.Date endDate = null;
		java.util.Date sDate = (java.util.Date) dynaForm
				.get("dateOfTheDocument10");
		String stDate = String.valueOf(sDate);
		if ((stDate == null) || (stDate.equals(""))) {
			startDate = null;
		} else if (stDate != null) {
			startDate = new java.sql.Date(sDate.getTime());
		}

		java.util.Date eDate = (java.util.Date) dynaForm
				.get("dateOfTheDocument11");
		endDate = new java.sql.Date(eDate.getTime());
		String id = (String) dynaForm.get("checkValue");

		BaseAction baseAction = new BaseAction();
		MLIInfo mliInfo = baseAction.getMemberInfo(request);
		String bankId = mliInfo.getBankId();
		String zoneId = mliInfo.getZoneId();
		String branchId = mliInfo.getBranchId();

		if (!branchId.equals("0000")) {
			String memberId = bankId + zoneId + branchId;
			mliReport = this.reportManager
					.getBranchApplicationDetailsForDistrict(startDate, endDate,
							id, memberId);
			dynaForm.set("mliWiseReport", mliReport);
			if ((mliReport == null) || (mliReport.size() == 0)) {
				throw new NoDataException(
						"No Data is available for the values entered, Please Enter Any Other Value ");
			}

			mliReport = null;
			Log.log(4, "ReportsAction", "districtDetailsForState", "Exited");

			return mapping.findForward("success1");
		}

		if (!zoneId.equals("0000")) {
			String memberId = bankId + zoneId;
			mliReport = this.reportManager
					.getZoneApplicationDetailsForDistrict(startDate, endDate,
							id, memberId);
			dynaForm.set("mliWiseReport", mliReport);
			if ((mliReport == null) || (mliReport.size() == 0)) {
				throw new NoDataException(
						"No Data is available for the values entered, Please Enter Any Other Value ");
			}

			mliReport = null;
			Log.log(4, "ReportsAction", "districtDetailsForState", "Exited");

			return mapping.findForward("success1");
		}

		String memberId = bankId;
		mliReport = this.reportManager.getBankApplicationDetailsForDistrict(
				startDate, endDate, id, memberId);
		dynaForm.set("mliWiseReport", mliReport);
		if ((mliReport == null) || (mliReport.size() == 0)) {
			throw new NoDataException(
					"No Data is available for the values entered, Please Enter Any Other Value ");
		}

		mliReport = null;
		Log.log(4, "ReportsAction", "districtDetailsForState", "Exited");

		return mapping.findForward("success2");
	}

	public ActionForward statusWiseReport(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		Log.log(4, "ReportsAction", "statusWiseReport", "Entered");

		DynaActionForm dynaForm = (DynaActionForm) form;
		java.util.Date date = new java.util.Date();
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(date);
		int month = calendar.get(2);
		int day = calendar.get(5);
		month -= 1;
		day += 1;
		calendar.set(2, month);
		calendar.set(5, day);
		java.util.Date prevDate = calendar.getTime();

		GeneralReport generalReport = new GeneralReport();
		generalReport.setDateOfTheDocument14(prevDate);
		generalReport.setDateOfTheDocument15(date);
		BeanUtils.copyProperties(dynaForm, generalReport);
		Log.log(4, "ReportsAction", "statusWiseReport", "Exited");

		return mapping.findForward("success");
	}

	public ActionForward statusWiseReportDetails(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		Log.log(4, "ReportsAction", "statusWiseReportDetails", "Entered");
		ArrayList mliReport = new ArrayList();
		DynaActionForm dynaForm = (DynaActionForm) form;
		java.sql.Date startDate = null;
		java.sql.Date endDate = null;
		String application = (String) dynaForm.get("applicationStatus");

		java.util.Date sDate = (java.util.Date) dynaForm
				.get("dateOfTheDocument14");
		String stDate = String.valueOf(sDate);

		if (application.equals("NE"))
			request.setAttribute("radioValue", "New");
		else if (application.equals("AP"))
			request.setAttribute("radioValue", "Approved");
		else if (application.equals("PE"))
			request.setAttribute("radioValue", "Pending");
		else if (application.equals("CL"))
			request.setAttribute("radioValue", "CLosed");
		else if (application.equals("HO"))
			request.setAttribute("radioValue", "HOld");
		else if (application.equals("MO"))
			request.setAttribute("radioValue", "Modified");
		else if (application.equals("RE"))
			request.setAttribute("radioValue", "Rejected");
		else if (application.equals("EX")) {
			request.setAttribute("radioValue", "Expired");
		}
		if ((stDate == null) || (stDate.equals(""))) {
			startDate = null;
		} else if (stDate != null) {
			startDate = new java.sql.Date(sDate.getTime());
		}
		java.util.Date eDate = (java.util.Date) dynaForm
				.get("dateOfTheDocument15");
		endDate = new java.sql.Date(eDate.getTime());

		BaseAction baseAction = new BaseAction();
		MLIInfo mliInfo = baseAction.getMemberInfo(request);
		String bankId = mliInfo.getBankId();
		String zoneId = mliInfo.getZoneId();
		String branchId = mliInfo.getBranchId();

		if (!branchId.equals("0000")) {
			if ((application.equals("PE")) || (application.equals("RE"))
					|| (application.equals("NE")) || (application.equals("MO"))
					|| (application.equals("HO"))) {
				String memberId = bankId + zoneId + branchId;

				mliReport = this.reportManager.getStatusDetailsForBranch(
						startDate, endDate, memberId, application);

				dynaForm.set("mliWiseReport", mliReport);
				if ((mliReport == null) || (mliReport.size() == 0)) {
					throw new NoDataException(
							"No Data is available for the values entered, Please Enter Any Other Value ");
				}

				mliReport = null;
				Log.log(4, "ReportsAction", "statusWiseReportDetails", "Exited");

				return mapping.findForward("success1");
			}

			String memberId = bankId + zoneId + branchId;

			mliReport = this.reportManager.StatusDetailsForBranchMod(startDate,
					endDate, memberId, application);

			dynaForm.set("mliWiseReport", mliReport);
			if ((mliReport == null) || (mliReport.size() == 0)) {
				throw new NoDataException(
						"No Data is available for the values entered, Please Enter Any Other Value ");
			}

			mliReport = null;
			Log.log(4, "ReportsAction", "statusWiseReportDetails", "Exited");

			return mapping.findForward("success4");
		}

		if (!zoneId.equals("0000")) {
			if ((application.equals("PE")) || (application.equals("RE"))
					|| (application.equals("NE")) || (application.equals("MO"))
					|| (application.equals("HO"))) {
				mliReport = this.reportManager.getStatusDetailsForZone(
						startDate, endDate, bankId, zoneId, application);
				dynaForm.set("mliWiseReport", mliReport);
				if ((mliReport == null) || (mliReport.size() == 0)) {
					throw new NoDataException(
							"No Data is available for the values entered, Please Enter Any Other Value ");
				}

				mliReport = null;
				Log.log(4, "ReportsAction", "statusWiseReportDetails", "Exited");

				return mapping.findForward("success2");
			}

			mliReport = this.reportManager.getStatusDetailsForZone1(startDate,
					endDate, bankId, zoneId, application);
			dynaForm.set("mliWiseReport", mliReport);
			if ((mliReport == null) || (mliReport.size() == 0)) {
				throw new NoDataException(
						"No Data is available for the values entered, Please Enter Any Other Value ");
			}

			mliReport = null;
			Log.log(4, "ReportsAction", "statusWiseReportDetails", "Exited");

			return mapping.findForward("success5");
		}

		if (!bankId.equals("0000")) {
			if ((application.equals("PE")) || (application.equals("RE"))
					|| (application.equals("NE")) || (application.equals("MO"))
					|| (application.equals("HO"))) {
				String memberId = bankId;
				mliReport = this.reportManager.getStatusDetailsForBank(
						startDate, endDate, memberId, application);
				dynaForm.set("mliWiseReport", mliReport);
				if ((mliReport == null) || (mliReport.size() == 0)) {
					throw new NoDataException(
							"No Data is available for the values entered, Please Enter Any Other Value ");
				}

				mliReport = null;
				Log.log(4, "ReportsAction", "statusWiseReportDetails", "Exited");

				return mapping.findForward("success3");
			}

			String memberId = bankId;
			mliReport = this.reportManager.getStatusDetailsForBank1(startDate,
					endDate, memberId, application);
			dynaForm.set("mliWiseReport", mliReport);
			if ((mliReport == null) || (mliReport.size() == 0)) {
				throw new NoDataException(
						"No Data is available for the values entered, Please Enter Any Other Value ");
			}

			mliReport = null;
			Log.log(4, "ReportsAction", "statusWiseReportDetails", "Exited");

			return mapping.findForward("success6");
		}

		if ((application.equals("PE")) || (application.equals("RE"))
				|| (application.equals("NE")) || (application.equals("MO"))
				|| (application.equals("HO"))) {
			mliReport = this.reportManager.getStatusWiseReportDetails(
					startDate, endDate, application);
			dynaForm.set("mliWiseReport", mliReport);
			if ((mliReport == null) || (mliReport.size() == 0)) {
				throw new NoDataException(
						"No Data is available for the values entered, Please Enter Any Other Value ");
			}

			mliReport = null;
			Log.log(4, "ReportsAction", "statusWiseReportDetails", "Exited");

			return mapping.findForward("success");
		}

		mliReport = this.reportManager.getStatusWiseReportDetails1(startDate,
				endDate, application);
		dynaForm.set("mliWiseReport", mliReport);
		if ((mliReport == null) || (mliReport.size() == 0)) {
			throw new NoDataException(
					"No Data is available for the values entered, Please Enter Any Other Value ");
		}

		mliReport = null;
		Log.log(4, "ReportsAction", "statusWiseReportDetails", "Exited");

		return mapping.findForward("success7");
	}

	public ActionForward applicationWiseReportDetailsForCgpan(
			ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		Log.log(4, "ReportsAction", "applicationWiseReportDetailsForCgpan",
				"Entered");
		ApplicationReport dan = new ApplicationReport();
		DynaActionForm dynaForm = (DynaActionForm) form;
		java.sql.Date startDate = null;
		java.sql.Date endDate = null;
		String application = (String) dynaForm.get("number");
		java.util.Date sDate = (java.util.Date) dynaForm
				.get("dateOfTheDocument14");
		String stDate = String.valueOf(sDate);

		if ((stDate == null) || (stDate.equals("")))
			startDate = null;
		else if (stDate != null) {
			startDate = new java.sql.Date(sDate.getTime());
		}
		String radio = (String) dynaForm.get("applicationStatus");

		if (radio.equals("NE"))
			request.setAttribute("radioValue", "New");
		else if (radio.equals("AP"))
			request.setAttribute("radioValue", "Approved");
		else if (radio.equals("PE"))
			request.setAttribute("radioValue", "Pending");
		else if (radio.equals("CL"))
			request.setAttribute("radioValue", "Closed");
		else if (radio.equals("HO"))
			request.setAttribute("radioValue", "Hold");
		else if (radio.equals("MO"))
			request.setAttribute("radioValue", "Modified");
		else if (radio.equals("RE")) {
			request.setAttribute("radioValue", "Rejected");
		}
		java.util.Date eDate = (java.util.Date) dynaForm
				.get("dateOfTheDocument15");
		endDate = new java.sql.Date(eDate.getTime());
		dan = this.reportManager.applicationWiseReportDetails(startDate,
				endDate, application);
		dynaForm.set("statusDetails", dan);
		String key = dan.getMemberId();
		String member = request.getParameter("number");
		request.setAttribute("MEMBERID", member);

		if ((key == null) || (key.equals(""))) {
			throw new NoDataException(
					"No Data is available for this value, Please Choose Any Other Value ");
		}

		dan = null;
		Log.log(4, "ReportsAction", "applicationWiseReportDetailsForCgpan",
				"Exited");

		return mapping.findForward("success");
	}

	public ActionForward stateApplicationDetails1(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		Log.log(4, "ReportsAction", "stateApplicationDetails1", "Entered");
		ArrayList stateReport = new ArrayList();
		DynaActionForm dynaForm = (DynaActionForm) form;
		java.sql.Date startDate = null;
		java.sql.Date endDate = null;
		String flag = (String) dynaForm.get("checkValue");
		java.util.Date sDate = (java.util.Date) dynaForm
				.get("dateOfTheDocument10");
		String stDate = String.valueOf(sDate);

		if ((stDate == null) || (stDate.equals("")))
			startDate = null;
		else if (stDate != null) {
			startDate = new java.sql.Date(sDate.getTime());
		}
		java.util.Date eDate = (java.util.Date) dynaForm
				.get("dateOfTheDocument11");
		endDate = new java.sql.Date(eDate.getTime());
		String state = request.getParameter("State");

		if (flag.equals("yes"))
			request.setAttribute("radioValue", "Guarantee Approved");
		else if (flag.equals("no")) {
			request.setAttribute("radioValue", "Guarantee Issued");
		}
		request.setAttribute("stateName", state);

		int i = state.indexOf("$");
		if (i != -1) {
			String newState = state.replace('$', '&');
			stateReport = this.reportManager.getStateDetailsNew(newState, flag,
					startDate, endDate);
			dynaForm.set("stateReport", stateReport);
			if ((stateReport == null) || (stateReport.size() == 0)) {
				throw new NoDataException(
						"No Data is available for the values entered, Please Enter Any Other Value ");
			}

			Log.log(4, "ReportsAction", "stateApplicationDetails1", "Exited");

			return mapping.findForward("success");
		}

		stateReport = this.reportManager.getStateDetailsNew(state, flag,
				startDate, endDate);
		dynaForm.set("stateReport", stateReport);
		if ((stateReport == null) || (stateReport.size() == 0)) {
			throw new NoDataException(
					"No Data is available for the values entered, Please Enter Any Other Value ");
		}

		Log.log(4, "ReportsAction", "stateApplicationDetails1", "Exited");

		return mapping.findForward("success");
	}

	public ActionForward districtApplicationDetails(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		Log.log(4, "ReportsAction", "districtApplicationDetails", "Entered");
		ArrayList districtReport = new ArrayList();
		DynaActionForm dynaForm = (DynaActionForm) form;
		java.sql.Date startDate = null;
		java.sql.Date endDate = null;
		String flag = (String) dynaForm.get("checkValue");
		java.util.Date sDate = (java.util.Date) dynaForm
				.get("dateOfTheDocument10");
		String stDate = String.valueOf(sDate);

		if ((stDate == null) || (stDate.equals("")))
			startDate = null;
		else if (stDate != null) {
			startDate = new java.sql.Date(sDate.getTime());
		}
		java.util.Date eDate = (java.util.Date) dynaForm
				.get("dateOfTheDocument11");
		endDate = new java.sql.Date(eDate.getTime());
		String district = request.getParameter("District");

		if (flag.equals("yes"))
			request.setAttribute("radioValue", "Guarantee Approved");
		else if (flag.equals("no")) {
			request.setAttribute("radioValue", "Guarantee Issued");
		}
		request.setAttribute("stateName", district);

		int i = district.indexOf("$");

		if (i != -1) {
			String newDistrict = district.replace('$', '&');
			districtReport = this.reportManager.getStateDistrictDetails(
					newDistrict, flag, startDate, endDate);
			dynaForm.set("districtReport", districtReport);
			if ((districtReport == null) || (districtReport.size() == 0)) {
				throw new NoDataException(
						"No Data is available for the values entered, Please Enter Any Other Value ");
			}

			Log.log(4, "ReportsAction", "districtApplicationDetails", "Exited");

			return mapping.findForward("success");
		}

		districtReport = this.reportManager.getStateDistrictDetails(district,
				flag, startDate, endDate);
		dynaForm.set("districtReport", districtReport);
		if ((districtReport == null) || (districtReport.size() == 0)) {
			throw new NoDataException(
					"No Data is available for the values entered, Please Enter Any Other Value ");
		}

		Log.log(4, "ReportsAction", "districtApplicationDetails", "Exited");

		return mapping.findForward("success");
	}

	public ActionForward districtApplicationDetailsNew(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		Log.log(4, "ReportsAction", "districtApplicationDetailsNew", "Entered");
		ArrayList mliWiseNEDistrictReportDetailsNew = new ArrayList();
		DynaActionForm dynaForm = (DynaActionForm) form;
		java.sql.Date startDate = null;
		java.sql.Date endDate = null;

		java.util.Date sDate = (java.util.Date) dynaForm
				.get("dateOfTheDocument10");
		String stDate = String.valueOf(sDate);

		if ((stDate == null) || (stDate.equals("")))
			startDate = null;
		else if (stDate != null) {
			startDate = new java.sql.Date(sDate.getTime());
		}
		java.util.Date eDate = (java.util.Date) dynaForm
				.get("dateOfTheDocument11");
		endDate = new java.sql.Date(eDate.getTime());
		String District = request.getParameter("State");

		request.setAttribute("stateName", District);

		int i = District.indexOf("$");

		if (i != -1) {
			String newDistrict = District.replace('$', '&');
			mliWiseNEDistrictReportDetailsNew = this.reportManager
					.getStateDistrictDetailsNew(newDistrict, startDate, endDate);
			dynaForm.set("mliWiseNEDistrictReportDetailsNew",
					mliWiseNEDistrictReportDetailsNew);
			if ((mliWiseNEDistrictReportDetailsNew == null)
					|| (mliWiseNEDistrictReportDetailsNew.size() == 0)) {
				throw new NoDataException(
						"No Data is available for the values entered, Please Enter Any Other Value ");
			}

			Log.log(4, "ReportsAction", "districtApplicationDetailsNew",
					"Exited");

			return mapping.findForward("success");
		}

		mliWiseNEDistrictReportDetailsNew = this.reportManager
				.getStateDistrictDetailsNew(District, startDate, endDate);
		dynaForm.set("mliWiseNEDistrictReportDetailsNew",
				mliWiseNEDistrictReportDetailsNew);
		if ((mliWiseNEDistrictReportDetailsNew == null)
				|| (mliWiseNEDistrictReportDetailsNew.size() == 0)) {
			throw new NoDataException(
					"No Data is available for the values entered, Please Enter Any Other Value ");
		}

		Log.log(4, "ReportsAction", "districtApplicationDetailsNew", "Exited");

		return mapping.findForward("success");
	}

	public ActionForward mliWiseReport(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		Log.log(4, "ReportsAction", "mliWiseReport", "Entered");
		DynaActionForm dynaForm = (DynaActionForm) form;
		java.util.Date date = new java.util.Date();
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(date);
		int month = calendar.get(2);
		int day = calendar.get(5);
		month -= 1;
		day += 1;
		calendar.set(2, month);
		calendar.set(5, day);
		java.util.Date prevDate = calendar.getTime();
		GeneralReport generalReport = new GeneralReport();
		generalReport.setDateOfTheDocument12(prevDate);
		generalReport.setDateOfTheDocument13(date);
		BeanUtils.copyProperties(dynaForm, generalReport);
		Log.log(4, "ReportsAction", "mliWiseReport", "Exited");

		return mapping.findForward("success");
	}

	public ActionForward mliWiseClaimReport(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		Log.log(4, "ReportsAction", "mliWiseClaimReport", "Entered");
		DynaActionForm dynaForm = (DynaActionForm) form;
		java.util.Date date = new java.util.Date();
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(date);
		int month = calendar.get(2);
		int day = calendar.get(5);
		month -= 1;
		day += 1;
		calendar.set(2, month);
		calendar.set(5, day);
		java.util.Date prevDate = calendar.getTime();
		GeneralReport generalReport = new GeneralReport();
		generalReport.setDateOfTheDocument12(prevDate);
		generalReport.setDateOfTheDocument13(date);
		BeanUtils.copyProperties(dynaForm, generalReport);
		Log.log(4, "ReportsAction", "mliWiseClaimReport", "Exited");

		return mapping.findForward("success");
	}

	public ActionForward mliWiseClaimSummaryReport(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		Log.log(4, "ReportsAction", "mliWiseClaimSummaryReport", "Entered");

		DynaActionForm dynaForm = (DynaActionForm) form;

		User user = getUserInformation(request);
		String bankId = user.getBankId();
		String zoneId = user.getZoneId();
		String branchId = user.getBranchId();
		String memberId = bankId.concat(zoneId).concat(branchId);

		java.util.Date date = new java.util.Date();
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(date);
		int month = calendar.get(2);
		int day = calendar.get(5);
		month -= 1;
		day += 1;
		calendar.set(2, month);
		calendar.set(5, day);
		java.util.Date prevDate = calendar.getTime();
		GeneralReport generalReport = new GeneralReport();
		generalReport.setDateOfTheDocument12(prevDate);
		generalReport.setDateOfTheDocument13(date);
		BeanUtils.copyProperties(dynaForm, generalReport);
		dynaForm.set("bankId", bankId);
		if (bankId.equals("0000")) {
			memberId = "";
		}
		dynaForm.set("memberId", memberId);
		Log.log(4, "ReportsAction", "mliWiseClaimSummaryReport", "Exited");

		return mapping.findForward("success");
	}

	public ActionForward mliWiseClaimPendigCasesReport(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		Log.log(4, "ReportsAction", "mliWiseClaimPendigCasesReport", "Entered");

		DynaActionForm dynaForm = (DynaActionForm) form;
		java.util.Date date = new java.util.Date();
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(date);
		int month = calendar.get(2);
		int day = calendar.get(5);
		month -= 1;
		day += 1;
		calendar.set(2, month);
		calendar.set(5, day);
		java.util.Date prevDate = calendar.getTime();
		GeneralReport generalReport = new GeneralReport();
		generalReport.setDateOfTheDocument12(prevDate);
		generalReport.setDateOfTheDocument13(date);
		BeanUtils.copyProperties(dynaForm, generalReport);
		Log.log(4, "ReportsAction", "mliWiseClaimPendigCasesReport", "Exited");

		return mapping.findForward("success");
	}

	public ActionForward mliWiseClaimPendingReportDetails(
			ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		Log.log(4, "ReportsAction", "mliWiseClaimPendingReportDetails",
				"Entered");

		ArrayList mliWiseClaimSummaryReport = new ArrayList();
		DynaActionForm dynaForm = (DynaActionForm) form;
		java.sql.Date startDate = null;
		java.sql.Date endDate = null;
		java.util.Date sDate = (java.util.Date) dynaForm
				.get("dateOfTheDocument12");
		String stDate = String.valueOf(sDate);

		if ((stDate == null) || (stDate.equals("")))
			startDate = null;
		else if (stDate != null) {
			startDate = new java.sql.Date(sDate.getTime());
		}
		request.setAttribute("STARTDATE", dynaForm.get("dateOfTheDocument12"));
		request.setAttribute("ENDDATE", dynaForm.get("dateOfTheDocument13"));

		java.util.Date eDate = (java.util.Date) dynaForm
				.get("dateOfTheDocument13");
		endDate = new java.sql.Date(eDate.getTime());
		BaseAction baseAction = new BaseAction();
		MLIInfo mliInfo = baseAction.getMemberInfo(request);
		String bankId = mliInfo.getBankId();
		String zoneId = mliInfo.getZoneId();
		String branchId = mliInfo.getBranchId();

		mliWiseClaimSummaryReport = this.reportManager
				.mliWiseClaimPendingReportDetails(endDate);
		dynaForm.set("mliWiseClaimSummaryReport", mliWiseClaimSummaryReport);
		if ((mliWiseClaimSummaryReport == null)
				|| (mliWiseClaimSummaryReport.size() == 0)) {
			throw new NoDataException(
					"No Data is available for the values entered, Please Enter Any Other Value ");
		}

		mliWiseClaimSummaryReport = null;
		Log.log(4, "ReportsAction", "mliWiseClaimPendingReportDetails",
				"Exited");

		return mapping.findForward("success");
	}

	public ActionForward mliWiseClaimSummaryReportDetails(
			ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		Log.log(4, "ReportsAction", "mliWiseClaimSummaryReportDetails",
				"Entered");

		ArrayList mliWiseClaimSummaryReport = new ArrayList();
		DynaActionForm dynaForm = (DynaActionForm) form;
		java.sql.Date startDate = null;
		java.sql.Date endDate = null;
		java.util.Date sDate = (java.util.Date) dynaForm
				.get("dateOfTheDocument12");
		String stDate = String.valueOf(sDate);
		String id = (String) dynaForm.get("memberId");
		if ((id == null) || (id.equals(""))) {
			id = "";
		}
		if ((stDate == null) || (stDate.equals("")))
			startDate = null;
		else if (stDate != null) {
			startDate = new java.sql.Date(sDate.getTime());
		}
		request.setAttribute("STARTDATE", dynaForm.get("dateOfTheDocument12"));
		request.setAttribute("ENDDATE", dynaForm.get("dateOfTheDocument13"));

		java.util.Date eDate = (java.util.Date) dynaForm
				.get("dateOfTheDocument13");
		endDate = new java.sql.Date(eDate.getTime());
		BaseAction baseAction = new BaseAction();
		MLIInfo mliInfo = baseAction.getMemberInfo(request);
		String bankId = mliInfo.getBankId();
		String zoneId = mliInfo.getZoneId();
		String branchId = mliInfo.getBranchId();

		mliWiseClaimSummaryReport = this.reportManager
				.mliWiseClaimSummaryReportDetails(endDate, id);
		dynaForm.set("mliWiseClaimSummaryReport", mliWiseClaimSummaryReport);
		if ((mliWiseClaimSummaryReport == null)
				|| (mliWiseClaimSummaryReport.size() == 0)) {
			throw new NoDataException(
					"No Data is available for the values entered, Please Enter Any Other Value ");
		}

		mliWiseClaimSummaryReport = null;
		Log.log(4, "ReportsAction", "mliWiseClaimSummaryReportDetails",
				"Exited");

		return mapping.findForward("success");
	}

	public ActionForward stateWiseClaimReport(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		Log.log(4, "ReportsAction", "stateWiseClaimReport", "Entered");
		DynaActionForm dynaForm = (DynaActionForm) form;
		java.util.Date date = new java.util.Date();
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(date);
		int month = calendar.get(2);
		int day = calendar.get(5);
		month -= 1;
		day += 1;
		calendar.set(2, month);
		calendar.set(5, day);
		java.util.Date prevDate = calendar.getTime();
		GeneralReport generalReport = new GeneralReport();
		generalReport.setDateOfTheDocument12(prevDate);
		generalReport.setDateOfTheDocument13(date);
		BeanUtils.copyProperties(dynaForm, generalReport);
		Log.log(4, "ReportsAction", "stateWiseClaimReport", "Exited");

		return mapping.findForward("success");
	}

	public ActionForward sectorWiseClaimReport(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		Log.log(4, "ReportsAction", "sectorWiseClaimReport", "Entered");
		DynaActionForm dynaForm = (DynaActionForm) form;
		java.util.Date date = new java.util.Date();
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(date);
		int month = calendar.get(2);
		int day = calendar.get(5);
		month -= 1;
		day += 1;
		calendar.set(2, month);
		calendar.set(5, day);
		java.util.Date prevDate = calendar.getTime();
		GeneralReport generalReport = new GeneralReport();
		generalReport.setDateOfTheDocument12(prevDate);
		generalReport.setDateOfTheDocument13(date);
		BeanUtils.copyProperties(dynaForm, generalReport);
		Log.log(4, "ReportsAction", "sectorWiseClaimReport", "Exited");

		return mapping.findForward("success");
	}

	public ActionForward mliWiseClaimApplReport(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		Log.log(4, "ReportsAction", "mliWiseClaimApplReport", "Entered");
		DynaActionForm dynaForm = (DynaActionForm) form;
		java.util.Date date = new java.util.Date();
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(date);
		int month = calendar.get(2);
		int day = calendar.get(5);
		month -= 1;
		day += 1;
		calendar.set(2, month);
		calendar.set(5, day);
		java.util.Date prevDate = calendar.getTime();
		GeneralReport generalReport = new GeneralReport();
		generalReport.setDateOfTheDocument12(prevDate);
		generalReport.setDateOfTheDocument13(date);
		BeanUtils.copyProperties(dynaForm, generalReport);
		Log.log(4, "ReportsAction", "mliWiseClaimReport", "Exited");

		return mapping.findForward("success");
	}

	public ActionForward mliWiseReportForRsf(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		Log.log(4, "ReportsAction", "mliWiseReportForRsf", "Entered");
		DynaActionForm dynaForm = (DynaActionForm) form;
		java.util.Date date = new java.util.Date();
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(date);
		int month = calendar.get(2);
		int day = calendar.get(5);
		month -= 1;
		day += 1;
		calendar.set(2, month);
		calendar.set(5, day);
		java.util.Date prevDate = calendar.getTime();
		GeneralReport generalReport = new GeneralReport();
		generalReport.setDateOfTheDocument12(prevDate);
		generalReport.setDateOfTheDocument13(date);
		BeanUtils.copyProperties(dynaForm, generalReport);
		Log.log(4, "ReportsAction", "mliWiseReportForRsf", "Exited");

		return mapping.findForward("success");
	}

	public ActionForward mliWiseReportNew(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		Log.log(4, "ReportsAction", "mliWiseReportNew", "Entered");
		DynaActionForm dynaForm = (DynaActionForm) form;
		java.util.Date date = new java.util.Date();
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(date);
		int month = calendar.get(2);
		int day = calendar.get(5);
		month -= 1;
		day += 1;
		calendar.set(2, month);
		calendar.set(5, day);
		java.util.Date prevDate = calendar.getTime();
		GeneralReport generalReport = new GeneralReport();
		generalReport.setDateOfTheDocument12(prevDate);
		generalReport.setDateOfTheDocument13(date);
		BeanUtils.copyProperties(dynaForm, generalReport);
		Log.log(4, "ReportsAction", "mliWiseReportNew", "Exited");

		return mapping.findForward("success");
	}

	public ActionForward mliWiseNEReportNew(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		Log.log(4, "ReportsAction", "mliWiseNEReportNew", "Entered");
		DynaActionForm dynaForm = (DynaActionForm) form;
		java.util.Date date = new java.util.Date();
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(date);
		int month = calendar.get(2);
		int day = calendar.get(5);
		month -= 1;
		day += 1;
		calendar.set(2, month);
		calendar.set(5, day);
		java.util.Date prevDate = calendar.getTime();
		GeneralReport generalReport = new GeneralReport();
		generalReport.setDateOfTheDocument12(prevDate);
		generalReport.setDateOfTheDocument13(date);
		BeanUtils.copyProperties(dynaForm, generalReport);
		Log.log(4, "ReportsAction", "mliWiseNEReportNew", "Exited");

		return mapping.findForward("success");
	}

	public ActionForward mliWiseNEStateReportNew(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		Log.log(4, "ReportsAction", "mliWiseNEStateReportNew", "Entered");
		DynaActionForm dynaForm = (DynaActionForm) form;
		java.util.Date date = new java.util.Date();
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(date);
		int month = calendar.get(2);
		int day = calendar.get(5);
		month -= 1;
		day += 1;
		calendar.set(2, month);
		calendar.set(5, day);
		java.util.Date prevDate = calendar.getTime();
		GeneralReport generalReport = new GeneralReport();
		generalReport.setDateOfTheDocument12(prevDate);
		generalReport.setDateOfTheDocument13(date);
		BeanUtils.copyProperties(dynaForm, generalReport);
		Log.log(4, "ReportsAction", "mliWiseNEStateReportNew", "Exited");

		return mapping.findForward("success");
	}

	public ActionForward mliWiseReportDetails(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		Log.log(4, "ReportsAction", "mliWiseReportDetails", "Entered");

		ArrayList mliReport = new ArrayList();
		DynaActionForm dynaForm = (DynaActionForm) form;
		java.sql.Date startDate = null;
		java.sql.Date endDate = null;
		java.util.Date sDate = (java.util.Date) dynaForm
				.get("dateOfTheDocument12");
		String stDate = String.valueOf(sDate);

		if ((stDate == null) || (stDate.equals("")))
			startDate = null;
		else if (stDate != null) {
			startDate = new java.sql.Date(sDate.getTime());
		}
		request.setAttribute("STARTDATE", dynaForm.get("dateOfTheDocument12"));
		request.setAttribute("ENDDATE", dynaForm.get("dateOfTheDocument13"));

		java.util.Date eDate = (java.util.Date) dynaForm
				.get("dateOfTheDocument13");
		endDate = new java.sql.Date(eDate.getTime());

		String id = (String) dynaForm.get("checkValue");

		BaseAction baseAction = new BaseAction();
		MLIInfo mliInfo = baseAction.getMemberInfo(request);
		String bankId = mliInfo.getBankId();
		String zoneId = mliInfo.getZoneId();
		String branchId = mliInfo.getBranchId();

		if (id.equals("yes"))
			request.setAttribute("radioValue", "Guarantee Approved");
		else if (id.equals("no")) {
			request.setAttribute("radioValue", "Guarantee Issued");
		}
		if (!branchId.equals("0000")) {
			String memberId = bankId + zoneId + branchId;
			mliReport = this.reportManager.getBranchApplicationDetails(
					startDate, endDate, id, memberId);
			dynaForm.set("mliWiseReport", mliReport);

			if ((mliReport == null) || (mliReport.size() == 0)) {
				throw new NoDataException(
						"No Data is available for the values entered, Please Enter Any Other Value ");
			}

			mliReport = null;
			Log.log(4, "ReportsAction", "mliWiseReportDetails", "Exited");

			return mapping.findForward("success1");
		}

		if (!zoneId.equals("0000")) {
			String memberId = bankId + zoneId;
			mliReport = this.reportManager.getZoneApplicationDetails(startDate,
					endDate, id, memberId);
			dynaForm.set("mliWiseReport", mliReport);
			if ((mliReport == null) || (mliReport.size() == 0)) {
				throw new NoDataException(
						"No Data is available for the values entered, Please Enter Any Other Value ");
			}

			mliReport = null;
			Log.log(4, "ReportsAction", "mliWiseReportDetails", "Exited");

			return mapping.findForward("success2");
		}

		if (!bankId.equals("0000")) {
			String memberId = bankId;
			mliReport = this.reportManager.getBankApplicationDetails(startDate,
					endDate, id, memberId);
			dynaForm.set("mliWiseReport", mliReport);
			if ((mliReport == null) || (mliReport.size() == 0)) {
				throw new NoDataException(
						"No Data is available for the values entered, Please Enter Any Other Value ");
			}

			mliReport = null;
			Log.log(4, "ReportsAction", "mliWiseReportDetails", "Exited");

			return mapping.findForward("success3");
		}

		mliReport = this.reportManager.getMliApplicationDetails(startDate,
				endDate, id);
		dynaForm.set("mliWiseReport", mliReport);
		if ((mliReport == null) || (mliReport.size() == 0)) {
			throw new NoDataException(
					"No Data is available for the values entered, Please Enter Any Other Value ");
		}

		mliReport = null;
		Log.log(4, "ReportsAction", "mliWiseReportDetails", "Exited");

		return mapping.findForward("success");
	}

	public ActionForward mliWiseClaimReportDetails(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		Log.log(4, "ReportsAction", "mliWiseClaimReportDetails", "Entered");

		ArrayList mliReport = new ArrayList();
		DynaActionForm dynaForm = (DynaActionForm) form;
		java.sql.Date startDate = null;
		java.sql.Date endDate = null;
		java.util.Date sDate = (java.util.Date) dynaForm
				.get("dateOfTheDocument12");
		String stDate = String.valueOf(sDate);

		if ((stDate == null) || (stDate.equals("")))
			startDate = null;
		else if (stDate != null) {
			startDate = new java.sql.Date(sDate.getTime());
		}
		request.setAttribute("STARTDATE", dynaForm.get("dateOfTheDocument12"));
		request.setAttribute("ENDDATE", dynaForm.get("dateOfTheDocument13"));

		java.util.Date eDate = (java.util.Date) dynaForm
				.get("dateOfTheDocument13");
		endDate = new java.sql.Date(eDate.getTime());
		BaseAction baseAction = new BaseAction();
		MLIInfo mliInfo = baseAction.getMemberInfo(request);
		String bankId = mliInfo.getBankId();
		String zoneId = mliInfo.getZoneId();
		String branchId = mliInfo.getBranchId();

		if (!branchId.equals("0000")) {
			String memberId = bankId + zoneId + branchId;
			mliReport = this.reportManager.getBranchClaimApplicationDetails(
					startDate, endDate, memberId);
			dynaForm.set("mliWiseReport", mliReport);

			if ((mliReport == null) || (mliReport.size() == 0)) {
				throw new NoDataException(
						"No Data is available for the values entered, Please Enter Any Other Value ");
			}

			mliReport = null;
			Log.log(4, "ReportsAction", "mliWiseClaimReportDetails", "Exited");

			return mapping.findForward("success");
		}

		if (!zoneId.equals("0000")) {
			String memberId = bankId + zoneId;
			mliReport = this.reportManager.getZoneClaimApplicationDetails(
					startDate, endDate, memberId);
			dynaForm.set("mliWiseReport", mliReport);
			if ((mliReport == null) || (mliReport.size() == 0)) {
				throw new NoDataException(
						"No Data is available for the values entered, Please Enter Any Other Value ");
			}

			mliReport = null;
			Log.log(4, "ReportsAction", "mliWiseClaimReportDetails", "Exited");

			return mapping.findForward("success");
		}

		if (!bankId.equals("0000")) {
			String memberId = bankId;
			mliReport = this.reportManager.getBankClaimApplicationDetails(
					startDate, endDate, memberId);
			dynaForm.set("mliWiseReport", mliReport);
			if ((mliReport == null) || (mliReport.size() == 0)) {
				throw new NoDataException(
						"No Data is available for the values entered, Please Enter Any Other Value ");
			}

			mliReport = null;
			Log.log(4, "ReportsAction", "mliWiseClaimReportDetails", "Exited");

			return mapping.findForward("success");
		}

		mliReport = this.reportManager.getMliClaimApplicationDetails(startDate,
				endDate);
		dynaForm.set("mliWiseReport", mliReport);
		if ((mliReport == null) || (mliReport.size() == 0)) {
			throw new NoDataException(
					"No Data is available for the values entered, Please Enter Any Other Value ");
		}

		mliReport = null;
		Log.log(4, "ReportsAction", "mliWiseClaimReportDetails", "Exited");

		return mapping.findForward("success");
	}

	public ActionForward stateWiseClaimReportDetails(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		Log.log(4, "ReportsAction", "stateWiseClaimReportDetails", "Entered");

		ArrayList mliReport = new ArrayList();
		DynaActionForm dynaForm = (DynaActionForm) form;
		java.sql.Date startDate = null;
		java.sql.Date endDate = null;
		java.util.Date sDate = (java.util.Date) dynaForm
				.get("dateOfTheDocument12");
		String stDate = String.valueOf(sDate);

		if ((stDate == null) || (stDate.equals("")))
			startDate = null;
		else if (stDate != null) {
			startDate = new java.sql.Date(sDate.getTime());
		}
		request.setAttribute("STARTDATE", dynaForm.get("dateOfTheDocument12"));
		request.setAttribute("ENDDATE", dynaForm.get("dateOfTheDocument13"));

		java.util.Date eDate = (java.util.Date) dynaForm
				.get("dateOfTheDocument13");
		endDate = new java.sql.Date(eDate.getTime());
		BaseAction baseAction = new BaseAction();
		MLIInfo mliInfo = baseAction.getMemberInfo(request);
		String bankId = mliInfo.getBankId();
		String zoneId = mliInfo.getZoneId();
		String branchId = mliInfo.getBranchId();

		if (!branchId.equals("0000")) {
			String memberId = bankId + zoneId + branchId;
			mliReport = this.reportManager
					.getStateBranchClaimApplicationDetails(startDate, endDate,
							memberId);
			dynaForm.set("mliWiseReport", mliReport);

			if ((mliReport == null) || (mliReport.size() == 0)) {
				throw new NoDataException(
						"No Data is available for the values entered, Please Enter Any Other Value ");
			}

			mliReport = null;
			Log.log(4, "ReportsAction", "stateWiseClaimReportDetails", "Exited");

			return mapping.findForward("success");
		}

		if (!zoneId.equals("0000")) {
			String memberId = bankId + zoneId;
			mliReport = this.reportManager.getStateZoneClaimApplicationDetails(
					startDate, endDate, memberId);
			dynaForm.set("mliWiseReport", mliReport);
			if ((mliReport == null) || (mliReport.size() == 0)) {
				throw new NoDataException(
						"No Data is available for the values entered, Please Enter Any Other Value ");
			}

			mliReport = null;
			Log.log(4, "ReportsAction", "stateWiseClaimReportDetails", "Exited");

			return mapping.findForward("success");
		}

		if (!bankId.equals("0000")) {
			String memberId = bankId;
			mliReport = this.reportManager.getStateBankClaimApplicationDetails(
					startDate, endDate, memberId);
			dynaForm.set("mliWiseReport", mliReport);
			if ((mliReport == null) || (mliReport.size() == 0)) {
				throw new NoDataException(
						"No Data is available for the values entered, Please Enter Any Other Value ");
			}

			mliReport = null;
			Log.log(4, "ReportsAction", "stateWiseClaimReportDetails", "Exited");

			return mapping.findForward("success");
		}

		mliReport = this.reportManager.getStateClaimApplicationDetails(
				startDate, endDate);
		dynaForm.set("mliWiseReport", mliReport);
		if ((mliReport == null) || (mliReport.size() == 0)) {
			throw new NoDataException(
					"No Data is available for the values entered, Please Enter Any Other Value ");
		}

		mliReport = null;
		Log.log(4, "ReportsAction", "stateWiseClaimReportDetails", "Exited");

		return mapping.findForward("success");
	}

	public ActionForward sectorWiseClaimReportDetails(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		Log.log(4, "ReportsAction", "sectorWiseClaimReportDetails", "Entered");

		ArrayList mliReport = new ArrayList();
		DynaActionForm dynaForm = (DynaActionForm) form;
		java.sql.Date startDate = null;
		java.sql.Date endDate = null;
		java.util.Date sDate = (java.util.Date) dynaForm
				.get("dateOfTheDocument12");
		String stDate = String.valueOf(sDate);

		if ((stDate == null) || (stDate.equals("")))
			startDate = null;
		else if (stDate != null) {
			startDate = new java.sql.Date(sDate.getTime());
		}
		request.setAttribute("STARTDATE", dynaForm.get("dateOfTheDocument12"));
		request.setAttribute("ENDDATE", dynaForm.get("dateOfTheDocument13"));

		java.util.Date eDate = (java.util.Date) dynaForm
				.get("dateOfTheDocument13");
		endDate = new java.sql.Date(eDate.getTime());
		BaseAction baseAction = new BaseAction();
		MLIInfo mliInfo = baseAction.getMemberInfo(request);
		String bankId = mliInfo.getBankId();
		String zoneId = mliInfo.getZoneId();
		String branchId = mliInfo.getBranchId();

		if (!branchId.equals("0000")) {
			String memberId = bankId + zoneId + branchId;
			mliReport = this.reportManager
					.getSectorBranchClaimApplicationDetails(startDate, endDate,
							memberId);
			dynaForm.set("mliWiseReport", mliReport);

			if ((mliReport == null) || (mliReport.size() == 0)) {
				throw new NoDataException(
						"No Data is available for the values entered, Please Enter Any Other Value ");
			}

			mliReport = null;
			Log.log(4, "ReportsAction", "sectorWiseClaimReportDetails",
					"Exited");

			return mapping.findForward("success");
		}

		if (!zoneId.equals("0000")) {
			String memberId = bankId + zoneId;
			mliReport = this.reportManager
					.getSectorZoneClaimApplicationDetails(startDate, endDate,
							memberId);
			dynaForm.set("mliWiseReport", mliReport);
			if ((mliReport == null) || (mliReport.size() == 0)) {
				throw new NoDataException(
						"No Data is available for the values entered, Please Enter Any Other Value ");
			}

			mliReport = null;
			Log.log(4, "ReportsAction", "sectorWiseClaimReportDetails",
					"Exited");

			return mapping.findForward("success");
		}

		if (!bankId.equals("0000")) {
			String memberId = bankId;
			mliReport = this.reportManager
					.getSectorBankClaimApplicationDetails(startDate, endDate,
							memberId);
			dynaForm.set("mliWiseReport", mliReport);
			if ((mliReport == null) || (mliReport.size() == 0)) {
				throw new NoDataException(
						"No Data is available for the values entered, Please Enter Any Other Value ");
			}

			mliReport = null;
			Log.log(4, "ReportsAction", "sectorWiseClaimReportDetails",
					"Exited");

			return mapping.findForward("success");
		}

		mliReport = this.reportManager.getSectorClaimApplicationDetails(
				startDate, endDate);
		dynaForm.set("mliWiseReport", mliReport);
		if ((mliReport == null) || (mliReport.size() == 0)) {
			throw new NoDataException(
					"No Data is available for the values entered, Please Enter Any Other Value ");
		}

		mliReport = null;
		Log.log(4, "ReportsAction", "sectorWiseClaimReportDetails", "Exited");

		return mapping.findForward("success");
	}

	public ActionForward mliWiseClaimApplReportDetails(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		Log.log(4, "ReportsAction", "mliWiseClaimApplReportDetails", "Entered");

		ArrayList mliReport = new ArrayList();
		DynaActionForm dynaForm = (DynaActionForm) form;
		java.sql.Date startDate = null;
		java.sql.Date endDate = null;
		java.util.Date sDate = (java.util.Date) dynaForm
				.get("dateOfTheDocument12");
		String stDate = String.valueOf(sDate);

		if ((stDate == null) || (stDate.equals("")))
			startDate = null;
		else if (stDate != null) {
			startDate = new java.sql.Date(sDate.getTime());
		}
		request.setAttribute("STARTDATE", dynaForm.get("dateOfTheDocument12"));
		request.setAttribute("ENDDATE", dynaForm.get("dateOfTheDocument13"));

		java.util.Date eDate = (java.util.Date) dynaForm
				.get("dateOfTheDocument13");
		endDate = new java.sql.Date(eDate.getTime());
		BaseAction baseAction = new BaseAction();
		MLIInfo mliInfo = baseAction.getMemberInfo(request);
		String bankId = mliInfo.getBankId();
		String zoneId = mliInfo.getZoneId();
		String branchId = mliInfo.getBranchId();

		if (!branchId.equals("0000")) {
			String memberId = bankId + zoneId + branchId;
			mliReport = this.reportManager.getBranchClaimApplicationDetailsNew(
					startDate, endDate, memberId);
			dynaForm.set("mliWiseReport", mliReport);

			if ((mliReport == null) || (mliReport.size() == 0)) {
				throw new NoDataException(
						"No Data is available for the values entered, Please Enter Any Other Value ");
			}

			mliReport = null;
			Log.log(4, "ReportsAction", "mliWiseClaimApplReportDetails",
					"Exited");

			return mapping.findForward("success1");
		}

		if (!zoneId.equals("0000")) {
			String memberId = bankId + zoneId;
			mliReport = this.reportManager.getZoneClaimApplicationDetailsNew(
					startDate, endDate, memberId);
			dynaForm.set("mliWiseReport", mliReport);
			if ((mliReport == null) || (mliReport.size() == 0)) {
				throw new NoDataException(
						"No Data is available for the values entered, Please Enter Any Other Value ");
			}

			mliReport = null;
			Log.log(4, "ReportsAction", "mliWiseClaimApplReportDetails",
					"Exited");

			return mapping.findForward("success2");
		}

		if (!bankId.equals("0000")) {
			String memberId = bankId;
			mliReport = this.reportManager.getBankClaimApplicationDetailsNew(
					startDate, endDate, memberId);
			dynaForm.set("mliWiseReport", mliReport);
			if ((mliReport == null) || (mliReport.size() == 0)) {
				throw new NoDataException(
						"No Data is available for the values entered, Please Enter Any Other Value ");
			}

			mliReport = null;
			Log.log(4, "ReportsAction", "mliWiseClaimApplReportDetails",
					"Exited");

			return mapping.findForward("success3");
		}

		mliReport = this.reportManager.getMliClaimApplicationDetailsNew(
				startDate, endDate);
		dynaForm.set("mliWiseReport", mliReport);
		if ((mliReport == null) || (mliReport.size() == 0)) {
			throw new NoDataException(
					"No Data is available for the values entered, Please Enter Any Other Value ");
		}

		mliReport = null;
		Log.log(4, "ReportsAction", "mliWiseClaimApplReportDetails", "Exited");

		return mapping.findForward("success");
	}

	public ActionForward mliWiseReportDetailsForRsf(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		Log.log(4, "ReportsAction", "mliWiseReportDetailsForRsf", "Entered");

		ArrayList mliReportForRsf = new ArrayList();
		ArrayList mliReportForRsfPend = new ArrayList();
		DynaActionForm dynaForm = (DynaActionForm) form;
		java.sql.Date startDate = null;
		java.sql.Date endDate = null;
		java.util.Date sDate = (java.util.Date) dynaForm
				.get("dateOfTheDocument12");
		String stDate = String.valueOf(sDate);

		if ((stDate == null) || (stDate.equals("")))
			startDate = null;
		else if (stDate != null) {
			startDate = new java.sql.Date(sDate.getTime());
		}
		request.setAttribute("STARTDATE", dynaForm.get("dateOfTheDocument12"));
		request.setAttribute("ENDDATE", dynaForm.get("dateOfTheDocument13"));

		java.util.Date eDate = (java.util.Date) dynaForm
				.get("dateOfTheDocument13");
		endDate = new java.sql.Date(eDate.getTime());

		String id = (String) dynaForm.get("checkValue");

		BaseAction baseAction = new BaseAction();
		MLIInfo mliInfo = baseAction.getMemberInfo(request);
		String bankId = mliInfo.getBankId();
		String zoneId = mliInfo.getZoneId();
		String branchId = mliInfo.getBranchId();

		if (id.equals("yes"))
			request.setAttribute("radioValue", "Guarantee Approved");
		else if (id.equals("no")) {
			request.setAttribute("radioValue", "Guarantee Issued");
		}

		mliReportForRsf = this.reportManager.getMliApplicationDetailsForRsf(
				startDate, endDate, id);
		dynaForm.set("mliReportForRsf", mliReportForRsf);
		mliReportForRsfPend = this.reportManager
				.getMliApplicationDetailsForRsfPend(startDate, endDate, id);
		dynaForm.set("mliReportForRsfPend", mliReportForRsfPend);
		if ((mliReportForRsf == null) || (mliReportForRsf.size() == 0)) {
			throw new NoDataException(
					"No Data is available for the values entered, Please Enter Any Other Value ");
		}

		mliReportForRsf = null;
		Log.log(4, "ReportsAction", "mliWiseReportDetailsForRsf", "Exited");

		return mapping.findForward("success");
	}

	public ActionForward mliWiseReportDetailsNew(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		Log.log(4, "ReportsAction", "mliWiseReportDetailsNew", "Entered");

		ArrayList mliWiseReportNew = new ArrayList();
		DynaActionForm dynaForm = (DynaActionForm) form;
		java.sql.Date startDate = null;
		java.sql.Date endDate = null;
		java.util.Date sDate = (java.util.Date) dynaForm
				.get("dateOfTheDocument12");
		String stDate = String.valueOf(sDate);

		if ((stDate == null) || (stDate.equals("")))
			startDate = null;
		else if (stDate != null) {
			startDate = new java.sql.Date(sDate.getTime());
		}
		request.setAttribute("STARTDATE", dynaForm.get("dateOfTheDocument12"));
		request.setAttribute("ENDDATE", dynaForm.get("dateOfTheDocument13"));

		java.util.Date eDate = (java.util.Date) dynaForm
				.get("dateOfTheDocument13");
		endDate = new java.sql.Date(eDate.getTime());

		String id = (String) dynaForm.get("checkValue");

		BaseAction baseAction = new BaseAction();
		MLIInfo mliInfo = baseAction.getMemberInfo(request);
		String bankId = mliInfo.getBankId();
		String zoneId = mliInfo.getZoneId();
		String branchId = mliInfo.getBranchId();

		if (id.equals("yes"))
			request.setAttribute("radioValue", "Guarantee Approved");
		else if (id.equals("no")) {
			request.setAttribute("radioValue", "Guarantee Issued");
		}

		if (startDate != null) {
			mliWiseReportNew = this.reportManager.getMliApplicationDetails(
					startDate, endDate, id);
			dynaForm.set("mliWiseReportNew", mliWiseReportNew);
		} else {
			mliWiseReportNew = this.reportManager.getMliApplicationDetails(
					startDate, endDate, id);
			dynaForm.set("mliWiseReportNew", mliWiseReportNew);

			return mapping.findForward("success1");
		}

		if ((mliWiseReportNew == null) || (mliWiseReportNew.size() == 0)) {
			throw new NoDataException(
					"No Data is available for the values entered, Please Enter Any Other Value ");
		}

		mliWiseReportNew = null;
		Log.log(4, "ReportsAction", "mliWiseReportDetailsNew", "Exited");

		return mapping.findForward("success");
	}

	public ActionForward mliWiseNEReportDetailsNew(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		Log.log(4, "ReportsAction", "mliWiseNEReportDetailsNew", "Entered");

		ArrayList mliWiseNEReportNew = new ArrayList();
		DynaActionForm dynaForm = (DynaActionForm) form;
		java.sql.Date startDate = null;
		java.sql.Date endDate = null;
		java.util.Date sDate = (java.util.Date) dynaForm
				.get("dateOfTheDocument12");
		String stDate = String.valueOf(sDate);

		if ((stDate == null) || (stDate.equals("")))
			startDate = null;
		else if (stDate != null) {
			startDate = new java.sql.Date(sDate.getTime());
		}
		request.setAttribute("STARTDATE", dynaForm.get("dateOfTheDocument12"));
		request.setAttribute("ENDDATE", dynaForm.get("dateOfTheDocument13"));

		java.util.Date eDate = (java.util.Date) dynaForm
				.get("dateOfTheDocument13");
		endDate = new java.sql.Date(eDate.getTime());

		BaseAction baseAction = new BaseAction();
		MLIInfo mliInfo = baseAction.getMemberInfo(request);
		String bankId = mliInfo.getBankId();
		String zoneId = mliInfo.getZoneId();
		String branchId = mliInfo.getBranchId();
		String mliId = bankId + zoneId + branchId;

		dynaForm.set("memberId", mliId);
		dynaForm.set("bankId", bankId);
		mliWiseNEReportNew = this.reportManager.getmliWiseNEReportDetailsNew(
				startDate, endDate);
		dynaForm.set("mliWiseNEReportNew", mliWiseNEReportNew);
		if ((mliWiseNEReportNew == null) || (mliWiseNEReportNew.size() == 0)) {
			throw new NoDataException(
					"No Data is available for the values entered, Please Enter Any Other Value ");
		}

		mliWiseNEReportNew = null;
		Log.log(4, "ReportsAction", "mliWiseNEReportDetailsNew", "Exited");

		return mapping.findForward("success");
	}

	public ActionForward mliWiseNEStateReportDetailsNew(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		Log.log(4, "ReportsAction", "mliWiseNEStateReportDetailsNew", "Entered");

		ArrayList mliWiseNEStateReportNew = new ArrayList();
		DynaActionForm dynaForm = (DynaActionForm) form;
		java.sql.Date startDate = null;
		java.sql.Date endDate = null;
		java.util.Date sDate = (java.util.Date) dynaForm
				.get("dateOfTheDocument12");
		String stDate = String.valueOf(sDate);

		if ((stDate == null) || (stDate.equals("")))
			startDate = null;
		else if (stDate != null) {
			startDate = new java.sql.Date(sDate.getTime());
		}
		request.setAttribute("STARTDATE", dynaForm.get("dateOfTheDocument12"));
		request.setAttribute("ENDDATE", dynaForm.get("dateOfTheDocument13"));

		java.util.Date eDate = (java.util.Date) dynaForm
				.get("dateOfTheDocument13");
		endDate = new java.sql.Date(eDate.getTime());

		BaseAction baseAction = new BaseAction();
		MLIInfo mliInfo = baseAction.getMemberInfo(request);
		String bankId = mliInfo.getBankId();
		String zoneId = mliInfo.getZoneId();
		String branchId = mliInfo.getBranchId();
		String mliId = bankId + zoneId + branchId;

		dynaForm.set("memberId", mliId);
		dynaForm.set("bankId", bankId);
		mliWiseNEStateReportNew = this.reportManager
				.getmliWiseNEStateReportDetailsNew(startDate, endDate);
		dynaForm.set("mliWiseNEStateReportNew", mliWiseNEStateReportNew);
		if ((mliWiseNEStateReportNew == null)
				|| (mliWiseNEStateReportNew.size() == 0)) {
			throw new NoDataException(
					"No Data is available for the values entered, Please Enter Any Other Value ");
		}

		mliWiseNEStateReportNew = null;
		Log.log(4, "ReportsAction", "mliWiseNEStateReportDetailsNew", "Exited");

		return mapping.findForward("success");
	}

	public ActionForward mliWiseNEDistrictReportDetailsNew1(
			ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		Log.log(4, "ReportsAction", "mliWiseNEDistrictReportDetailsNew",
				"Entered");
		ArrayList mliWiseNEDistrictReportDetailsNew = new ArrayList();
		DynaActionForm dynaForm = (DynaActionForm) form;
		java.sql.Date startDate = null;
		java.sql.Date endDate = null;
		java.util.Date sDate = (java.util.Date) dynaForm
				.get("dateOfTheDocument12");
		String stDate = String.valueOf(sDate);

		if ((stDate == null) || (stDate.equals("")))
			startDate = null;
		else if (stDate != null) {
			startDate = new java.sql.Date(sDate.getTime());
		}
		java.util.Date eDate = (java.util.Date) dynaForm
				.get("dateOfTheDocument13");
		endDate = new java.sql.Date(eDate.getTime());
		String district = request.getParameter("District");

		mliWiseNEDistrictReportDetailsNew = this.reportManager
				.mliWiseNEDistrictReportDetailsNew1(startDate, endDate,
						district);

		dynaForm.set("mliWiseNEDistrictReportDetailsNew",
				mliWiseNEDistrictReportDetailsNew);

		if ((mliWiseNEDistrictReportDetailsNew == null)
				|| (mliWiseNEDistrictReportDetailsNew.size() == 0)) {
			throw new NoDataException(
					"No Data is available for the values entered, Please Enter Any Other Value ");
		}

		mliWiseNEDistrictReportDetailsNew = null;
		Log.log(4, "ReportsAction", "mliWiseNEDistrictReportDetailsNew1",
				"Exited");

		return mapping.findForward("success1");
	}

	public ActionForward zoneDetailsForMli(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		Log.log(4, "ReportsAction", "zoneDetailsForMli", "Entered");
		ArrayList mliReport = new ArrayList();
		DynaActionForm dynaForm = (DynaActionForm) form;
		java.sql.Date startDate = null;
		java.sql.Date endDate = null;
		java.util.Date sDate = (java.util.Date) dynaForm
				.get("dateOfTheDocument12");
		String stDate = String.valueOf(sDate);
		if ((stDate == null) || (stDate.equals(""))) {
			startDate = null;
		} else if (stDate != null) {
			startDate = new java.sql.Date(sDate.getTime());
		}

		java.util.Date eDate = (java.util.Date) dynaForm
				.get("dateOfTheDocument13");
		endDate = new java.sql.Date(eDate.getTime());
		String id = (String) dynaForm.get("checkValue");

		BaseAction baseAction = new BaseAction();
		MLIInfo mliInfo = baseAction.getMemberInfo(request);
		String bankId = mliInfo.getBankId();
		String zoneId = mliInfo.getZoneId();
		String branchId = mliInfo.getBranchId();

		if (!branchId.equals("0000")) {
			String memberId = bankId + zoneId + branchId;
			mliReport = this.reportManager.ZoneDetailsForBranch(startDate,
					endDate, id, memberId);
			dynaForm.set("zoneWiseReport", mliReport);
			if ((mliReport == null) || (mliReport.size() == 0)) {
				throw new NoDataException(
						"No Data is available for the values entered, Please Enter Any Other Value ");
			}

			mliReport = null;
			Log.log(4, "ReportsAction", "zoneDetailsForMli", "Exited");

			return mapping.findForward("success1");
		}

		if (!zoneId.equals("0000")) {
			String memberId = bankId + zoneId;
			mliReport = this.reportManager.ZoneDetailsForZone(startDate,
					endDate, id, memberId);
			dynaForm.set("zoneWiseReport", mliReport);
			if ((mliReport == null) || (mliReport.size() == 0)) {
				throw new NoDataException(
						"No Data is available for the values entered, Please Enter Any Other Value ");
			}

			mliReport = null;
			Log.log(4, "ReportsAction", "zoneDetailsForMli", "Exited");

			return mapping.findForward("success2");
		}

		String memberId = bankId;
		mliReport = this.reportManager.ZoneDetailsForBank(startDate, endDate,
				id, memberId);
		dynaForm.set("zoneWiseReport", mliReport);
		if ((mliReport == null) || (mliReport.size() == 0)) {
			throw new NoDataException(
					"No Data is available for the values entered, Please Enter Any Other Value ");
		}

		mliReport = null;
		Log.log(4, "ReportsAction", "zoneDetailsForMli", "Exited");

		return mapping.findForward("success3");
	}

	public ActionForward sectorDetailsForMli(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		Log.log(4, "ReportsAction", "sectorDetailsForMli", "Entered");
		ArrayList mliReport = new ArrayList();
		DynaActionForm dynaForm = (DynaActionForm) form;
		java.sql.Date startDate = null;
		java.sql.Date endDate = null;
		java.util.Date sDate = (java.util.Date) dynaForm
				.get("dateOfTheDocument12");
		String stDate = String.valueOf(sDate);
		if ((stDate == null) || (stDate.equals(""))) {
			startDate = null;
		} else if (stDate != null) {
			startDate = new java.sql.Date(sDate.getTime());
		}

		java.util.Date eDate = (java.util.Date) dynaForm
				.get("dateOfTheDocument13");
		endDate = new java.sql.Date(eDate.getTime());
		String id = (String) dynaForm.get("checkValue");

		BaseAction baseAction = new BaseAction();
		MLIInfo mliInfo = baseAction.getMemberInfo(request);
		String bankId = mliInfo.getBankId();
		String zoneId = mliInfo.getZoneId();
		String branchId = mliInfo.getBranchId();

		if (!branchId.equals("0000")) {
			String memberId = bankId + zoneId + branchId;
			mliReport = this.reportManager.sectorDetailsForBranch(startDate,
					endDate, id, memberId);
			dynaForm.set("sectorWiseReport", mliReport);
			if ((mliReport == null) || (mliReport.size() == 0)) {
				throw new NoDataException(
						"No Data is available for the values entered, Please Enter Any Other Value ");
			}

			mliReport = null;
			Log.log(4, "ReportsAction", "sectorDetailsForMli", "Exited");

			return mapping.findForward("success1");
		}

		if (!zoneId.equals("0000")) {
			String memberId = bankId + zoneId;
			mliReport = this.reportManager.sectorDetailsForZone(startDate,
					endDate, id, memberId);
			dynaForm.set("sectorWiseReport", mliReport);
			if ((mliReport == null) || (mliReport.size() == 0)) {
				throw new NoDataException(
						"No Data is available for the values entered, Please Enter Any Other Value ");
			}

			mliReport = null;
			Log.log(4, "ReportsAction", "sectorDetailsForMli", "Exited");

			return mapping.findForward("success2");
		}

		String memberId = bankId;
		mliReport = this.reportManager.sectorDetailsForBank(startDate, endDate,
				id, memberId);
		dynaForm.set("sectorWiseReport", mliReport);
		if ((mliReport == null) || (mliReport.size() == 0)) {
			throw new NoDataException(
					"No Data is available for the values entered, Please Enter Any Other Value ");
		}

		mliReport = null;
		Log.log(4, "ReportsAction", "sectorDetailsForMli", "Exited");

		return mapping.findForward("success3");
	}

	public ActionForward stateDetailsForMli(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		Log.log(4, "ReportsAction", "stateDetailsForMli", "Entered");
		ArrayList mliReport = new ArrayList();
		DynaActionForm dynaForm = (DynaActionForm) form;
		java.sql.Date startDate = null;
		java.sql.Date endDate = null;
		java.util.Date sDate = (java.util.Date) dynaForm
				.get("dateOfTheDocument12");
		String stDate = String.valueOf(sDate);
		if ((stDate == null) || (stDate.equals(""))) {
			startDate = null;
		} else if (stDate != null) {
			startDate = new java.sql.Date(sDate.getTime());
		}

		java.util.Date eDate = (java.util.Date) dynaForm
				.get("dateOfTheDocument13");
		endDate = new java.sql.Date(eDate.getTime());
		String id = (String) dynaForm.get("checkValue");

		BaseAction baseAction = new BaseAction();
		MLIInfo mliInfo = baseAction.getMemberInfo(request);
		String bankId = mliInfo.getBankId();
		String zoneId = mliInfo.getZoneId();
		String branchId = mliInfo.getBranchId();

		if (!branchId.equals("0000")) {
			String memberId = bankId + zoneId + branchId;
			mliReport = this.reportManager.stateDetailsForBranch(startDate,
					endDate, id, memberId);
			dynaForm.set("stateWiseReport", mliReport);
			if ((mliReport == null) || (mliReport.size() == 0)) {
				throw new NoDataException(
						"No Data is available for the values entered, Please Enter Any Other Value ");
			}

			mliReport = null;
			Log.log(4, "ReportsAction", "stateDetailsForMli", "Exited");

			return mapping.findForward("success1");
		}

		if (!zoneId.equals("0000")) {
			String memberId = bankId + zoneId;
			mliReport = this.reportManager.stateDetailsForZone(startDate,
					endDate, id, memberId);
			dynaForm.set("stateWiseReport", mliReport);
			if ((mliReport == null) || (mliReport.size() == 0)) {
				throw new NoDataException(
						"No Data is available for the values entered, Please Enter Any Other Value ");
			}

			mliReport = null;
			Log.log(4, "ReportsAction", "stateDetailsForMli", "Exited");

			return mapping.findForward("success2");
		}

		String memberId = bankId;
		mliReport = this.reportManager.stateDetailsForBank(startDate, endDate,
				id, memberId);
		dynaForm.set("stateWiseReport", mliReport);
		if ((mliReport == null) || (mliReport.size() == 0)) {
			throw new NoDataException(
					"No Data is available for the values entered, Please Enter Any Other Value ");
		}

		mliReport = null;
		Log.log(4, "ReportsAction", "stateDetailsForMli", "Exited");

		return mapping.findForward("success3");
	}

	public ActionForward districtDetailsForMli(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		Log.log(4, "ReportsAction", "districtDetailsForMli", "Entered");
		ArrayList mliReport = new ArrayList();
		DynaActionForm dynaForm = (DynaActionForm) form;
		java.sql.Date startDate = null;
		java.sql.Date endDate = null;
		java.util.Date sDate = (java.util.Date) dynaForm
				.get("dateOfTheDocument12");
		String stDate = String.valueOf(sDate);
		if ((stDate == null) || (stDate.equals(""))) {
			startDate = null;
		} else if (stDate != null) {
			startDate = new java.sql.Date(sDate.getTime());
		}

		java.util.Date eDate = (java.util.Date) dynaForm
				.get("dateOfTheDocument13");
		endDate = new java.sql.Date(eDate.getTime());
		String id = (String) dynaForm.get("checkValue");

		BaseAction baseAction = new BaseAction();
		MLIInfo mliInfo = baseAction.getMemberInfo(request);
		String bankId = mliInfo.getBankId();
		String zoneId = mliInfo.getZoneId();
		String branchId = mliInfo.getBranchId();

		String state = (String) dynaForm.get("state");

		int i = state.indexOf("$");
		if (i != -1) {
			String newState = state.replace('$', '&');
			if (!branchId.equals("0000")) {
				String memberId = bankId + zoneId + branchId;
				mliReport = this.reportManager.districtDetailsForBranch(
						startDate, endDate, id, memberId, newState);
				dynaForm.set("districtWiseReport", mliReport);
				if ((mliReport == null) || (mliReport.size() == 0)) {
					throw new NoDataException(
							"No Data is available for the values entered, Please Enter Any Other Value ");
				}

				mliReport = null;
				Log.log(4, "ReportsAction", "districtDetailsForMli", "Exited");

				return mapping.findForward("success1");
			}

			if (!zoneId.equals("0000")) {
				String memberId = bankId + zoneId;
				mliReport = this.reportManager.districtDetailsForZone(
						startDate, endDate, id, memberId, newState);
				dynaForm.set("districtWiseReport", mliReport);
				if ((mliReport == null) || (mliReport.size() == 0)) {
					throw new NoDataException(
							"No Data is available for the values entered, Please Enter Any Other Value ");
				}

				mliReport = null;
				Log.log(4, "ReportsAction", "districtDetailsForMli", "Exited");

				return mapping.findForward("success2");
			}

			String memberId = bankId;
			mliReport = this.reportManager.districtDetailsForBank(startDate,
					endDate, id, memberId, newState);
			dynaForm.set("districtWiseReport", mliReport);
			if ((mliReport == null) || (mliReport.size() == 0)) {
				throw new NoDataException(
						"No Data is available for the values entered, Please Enter Any Other Value ");
			}

			mliReport = null;
			Log.log(4, "ReportsAction", "districtDetailsForMli", "Exited");

			return mapping.findForward("success3");
		}

		if (!branchId.equals("0000")) {
			String memberId = bankId + zoneId + branchId;
			mliReport = this.reportManager.districtDetailsForBranch(startDate,
					endDate, id, memberId, state);
			dynaForm.set("districtWiseReport", mliReport);
			if ((mliReport == null) || (mliReport.size() == 0)) {
				throw new NoDataException(
						"No Data is available for the values entered, Please Enter Any Other Value ");
			}

			mliReport = null;
			Log.log(4, "ReportsAction", "districtDetailsForMli", "Exited");

			return mapping.findForward("success1");
		}

		if (!zoneId.equals("0000")) {
			String memberId = bankId + zoneId;
			mliReport = this.reportManager.districtDetailsForZone(startDate,
					endDate, id, memberId, state);
			dynaForm.set("districtWiseReport", mliReport);
			if ((mliReport == null) || (mliReport.size() == 0)) {
				throw new NoDataException(
						"No Data is available for the values entered, Please Enter Any Other Value ");
			}

			mliReport = null;
			Log.log(4, "ReportsAction", "districtDetailsForMli", "Exited");

			return mapping.findForward("success2");
		}

		String memberId = bankId;
		mliReport = this.reportManager.districtDetailsForBank(startDate,
				endDate, id, memberId, state);
		dynaForm.set("districtWiseReport", mliReport);
		if ((mliReport == null) || (mliReport.size() == 0)) {
			throw new NoDataException(
					"No Data is available for the values entered, Please Enter Any Other Value ");
		}

		mliReport = null;
		Log.log(4, "ReportsAction", "districtDetailsForMli", "Exited");

		return mapping.findForward("success3");
	}

	public ActionForward zoneWiseReportDetails(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		Log.log(4, "ReportsAction", "zoneWiseReportDetails", "Entered");
		ArrayList zoneReport = new ArrayList();
		DynaActionForm dynaForm = (DynaActionForm) form;
		java.sql.Date startDate = null;
		java.sql.Date endDate = null;
		java.util.Date sDate = (java.util.Date) dynaForm
				.get("dateOfTheDocument12");
		String stDate = String.valueOf(sDate);

		if ((stDate == null) || (stDate.equals("")))
			startDate = null;
		else if (stDate != null) {
			startDate = new java.sql.Date(sDate.getTime());
		}
		java.util.Date eDate = (java.util.Date) dynaForm
				.get("dateOfTheDocument13");
		endDate = new java.sql.Date(eDate.getTime());
		String id = (String) dynaForm.get("checkValue");
		String zone = request.getParameter("Zone");
		int i = zone.indexOf("$");
		request.setAttribute("STARTDATE", dynaForm.get("dateOfTheDocument12"));
		request.setAttribute("ENDDATE", dynaForm.get("dateOfTheDocument13"));
		if (id.equals("yes"))
			request.setAttribute("radioValue", "Guarantee Approved");
		else if (id.equals("no")) {
			request.setAttribute("radioValue", "Guarantee Issued");
		}
		request.setAttribute("bankName", zone);

		if (i != -1) {
			String newZone = zone.replace('$', '&');
			zoneReport = this.reportManager.getZoneDetails(newZone, id,
					startDate, endDate);
			dynaForm.set("zoneReport", zoneReport);

			if ((zoneReport == null) || (zoneReport.size() == 0)) {
				throw new NoDataException(
						"No Data is available for the values entered, Please Enter Any Other Value ");
			}

			Log.log(4, "ReportsAction", "zoneWiseReportDetails", "Exited");

			return mapping.findForward("success");
		}
		zoneReport = this.reportManager.getZoneDetails(zone, id, startDate,
				endDate);
		dynaForm.set("zoneReport", zoneReport);
		if ((zoneReport == null) || (zoneReport.size() == 0)) {
			throw new NoDataException(
					"No Data is available for the values entered, Please Enter Any Other Value ");
		}

		Log.log(4, "ReportsAction", "zoneWiseReportDetails", "Exited");

		return mapping.findForward("success");
	}

	public ActionForward mliWiseRSFReportDetails(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		Log.log(4, "ReportsAction", "mliWiseRSFReportDetails", "Entered");
		ArrayList mliWiseRSFReport = new ArrayList();
		DynaActionForm dynaForm = (DynaActionForm) form;
		java.sql.Date startDate = null;
		java.sql.Date endDate = null;
		java.util.Date sDate = (java.util.Date) dynaForm
				.get("dateOfTheDocument12");
		String stDate = String.valueOf(sDate);

		if ((stDate == null) || (stDate.equals("")))
			startDate = null;
		else if (stDate != null) {
			startDate = new java.sql.Date(sDate.getTime());
		}
		java.util.Date eDate = (java.util.Date) dynaForm
				.get("dateOfTheDocument13");
		endDate = new java.sql.Date(eDate.getTime());
		String id = (String) dynaForm.get("checkValue");
		String zone = request.getParameter("Zone");

		int i = zone.indexOf("$");
		request.setAttribute("STARTDATE", dynaForm.get("dateOfTheDocument12"));
		request.setAttribute("ENDDATE", dynaForm.get("dateOfTheDocument13"));
		if (id.equals("yes"))
			request.setAttribute("radioValue", "Guarantee Approved");
		else if (id.equals("no")) {
			request.setAttribute("radioValue", "Guarantee Issued");
		}
		request.setAttribute("bankName", zone);

		if (i != -1) {
			String newZone = zone.replace('$', '&');
			mliWiseRSFReport = this.reportManager.getRSFDetails(newZone, id,
					startDate, endDate);
			dynaForm.set("mliWiseRSFReport", mliWiseRSFReport);

			if ((mliWiseRSFReport == null) || (mliWiseRSFReport.size() == 0)) {
				throw new NoDataException(
						"No Data is available for the values entered, Please Enter Any Other Value ");
			}

			Log.log(4, "ReportsAction", "mliWiseRSFReportDetails", "Exited");

			return mapping.findForward("success");
		}

		mliWiseRSFReport = this.reportManager.getRSFDetails(zone, id,
				startDate, endDate);
		dynaForm.set("mliWiseRSFReport", mliWiseRSFReport);
		if ((mliWiseRSFReport == null) || (mliWiseRSFReport.size() == 0)) {
			throw new NoDataException(
					"No Data is available for the values entered, Please Enter Any Other Value ");
		}

		Log.log(4, "ReportsAction", "mliWiseRSFReportDetails", "Exited");

		return mapping.findForward("success");
	}

	public ActionForward gfAllocatedReportDetails(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		Log.log(4, "ReportsAction", "gfAllocatedReportDetails", "Entered");
		ArrayList gfallocatedpaymentdetails = new ArrayList();
		DynaActionForm dynaForm = (DynaActionForm) form;
		String payId = request.getParameter("payId");

		gfallocatedpaymentdetails = this.reportManager
				.getGFAllocatedReportDetails(payId);
		dynaForm.set("gfallocatedpaymentdetails", gfallocatedpaymentdetails);
		if ((gfallocatedpaymentdetails == null)
				|| (gfallocatedpaymentdetails.size() == 0)) {
			throw new NoDataException(
					"No Data is available for the values entered, Please Enter Any Other Value ");
		}

		Log.log(4, "ReportsAction", "gfAllocatedReportDetails", "Exited");

		return mapping.findForward("success");
	}

	public ActionForward payInstrumentDetails(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		Log.log(4, "ReportsAction", "payInstrumentDetails", "Entered");
		ArrayList gfallocatedpaymentdetails = new ArrayList();
		DynaActionForm dynaForm = (DynaActionForm) form;
		String payInstrumentNo = request.getParameter("payInstrumentNo");

		String memberId = request.getParameter("memberId");

		gfallocatedpaymentdetails = this.reportManager.getPayInstrumentDetails(
				payInstrumentNo, memberId);
		dynaForm.set("gfallocatedpaymentdetails", gfallocatedpaymentdetails);
		if ((gfallocatedpaymentdetails == null)
				|| (gfallocatedpaymentdetails.size() == 0)) {
			throw new NoDataException(
					"No Data is available for the values entered, Please Enter Any Other Value ");
		}

		Log.log(4, "ReportsAction", "payInstrumentDetails", "Exited");

		return mapping.findForward("success");
	}

	public ActionForward asfAllocatedReportDetails(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		Log.log(4, "ReportsAction", "asfAllocatedReportDetails", "Entered");
		ArrayList asfallocatedpaymentdetails = new ArrayList();
		DynaActionForm dynaForm = (DynaActionForm) form;
		String payId = request.getParameter("payId");

		asfallocatedpaymentdetails = this.reportManager
				.getASFAllocatedReportDetails(payId);
		dynaForm.set("asfallocatedpaymentdetails", asfallocatedpaymentdetails);
		if ((asfallocatedpaymentdetails == null)
				|| (asfallocatedpaymentdetails.size() == 0)) {
			throw new NoDataException(
					"No Data is available for the values entered, Please Enter Any Other Value ");
		}

		Log.log(4, "ReportsAction", "asfAllocatedReportDetails", "Exited");

		return mapping.findForward("success");
	}

	public ActionForward branchWiseReportDetails(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		Log.log(4, "ReportsAction", "branchWiseReportDetails", "Entered");
		ArrayList branchReport = new ArrayList();
		DynaActionForm dynaForm = (DynaActionForm) form;
		java.sql.Date startDate = null;
		java.sql.Date endDate = null;
		java.util.Date sDate = (java.util.Date) dynaForm
				.get("dateOfTheDocument12");
		String stDate = String.valueOf(sDate);

		if ((stDate == null) || (stDate.equals("")))
			startDate = null;
		else if (stDate != null) {
			startDate = new java.sql.Date(sDate.getTime());
		}
		java.util.Date eDate = (java.util.Date) dynaForm
				.get("dateOfTheDocument13");
		endDate = new java.sql.Date(eDate.getTime());
		String id = (String) dynaForm.get("checkValue");

		String bank = request.getParameter("bank");
		String zone = request.getParameter("Zone");

		request.setAttribute("STARTDATE", dynaForm.get("dateOfTheDocument12"));
		request.setAttribute("ENDDATE", dynaForm.get("dateOfTheDocument13"));
		if (id.equals("yes"))
			request.setAttribute("radioValue", "Guarantee Approved");
		else if (id.equals("no")) {
			request.setAttribute("radioValue", "Guarantee Issued");
		}
		request.setAttribute("zoneName", zone);

		branchReport = this.reportManager.getbranchDetails(bank, zone, id,
				startDate, endDate);
		dynaForm.set("branchReport", branchReport);
		if ((branchReport == null) || (branchReport.size() == 0)) {
			throw new NoDataException(
					"No Data is available for the values entered, Please Enter Any Other Value ");
		}

		Log.log(4, "ReportsAction", "branchWiseReportDetails", "Exited");

		return mapping.findForward("success");
	}

	public ActionForward sectorWiseReportDetails1(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		Log.log(4, "ReportsAction", "sectorWiseReportDetails1", "Entered");
		ArrayList sectorReport = new ArrayList();
		DynaActionForm dynaForm = (DynaActionForm) form;
		java.sql.Date startDate = null;
		java.sql.Date endDate = null;
		java.util.Date sDate = (java.util.Date) dynaForm
				.get("dateOfTheDocument12");
		String stDate = String.valueOf(sDate);
		if ((stDate == null) || (stDate.equals("")))
			startDate = null;
		else if (stDate != null) {
			startDate = new java.sql.Date(sDate.getTime());
		}
		java.util.Date eDate = (java.util.Date) dynaForm
				.get("dateOfTheDocument13");
		endDate = new java.sql.Date(eDate.getTime());
		String id = (String) dynaForm.get("checkValue");
		String sector = request.getParameter("Sector");
		if (id.equals("yes"))
			request.setAttribute("radioValue", "Guarantee Approved");
		else if (id.equals("no")) {
			request.setAttribute("radioValue", "Guarantee Issued");
		}
		request.setAttribute("bankName", sector);

		int i = sector.indexOf("$");
		if (i != -1) {
			String newSector = sector.replace('$', '&');
			sectorReport = this.reportManager.getSectorDetails(newSector, id,
					startDate, endDate);

			dynaForm.set("sectorReport", sectorReport);
			if ((sectorReport == null) || (sectorReport.size() == 0)) {
				throw new NoDataException(
						"No Data is available for the values entered, Please Enter Any Other Value ");
			}

			Log.log(4, "ReportsAction", "sectorWiseReportDetails1", "Exited");

			return mapping.findForward("success");
		}

		sectorReport = this.reportManager.getSectorDetails(sector, id,
				startDate, endDate);
		dynaForm.set("sectorReport", sectorReport);
		if ((sectorReport == null) || (sectorReport.size() == 0)) {
			throw new NoDataException(
					"No Data is available for the values entered, Please Enter Any Other Value ");
		}

		Log.log(4, "ReportsAction", "sectorWiseReportDetails1", "Exited");

		return mapping.findForward("success");
	}

	public ActionForward statesWiseReport(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		Log.log(4, "ReportsAction", "statesWiseReport", "Entered");
		ArrayList statesWiseReport = new ArrayList();
		DynaActionForm dynaForm = (DynaActionForm) form;
		java.sql.Date startDate = null;
		java.sql.Date endDate = null;
		java.util.Date sDate = (java.util.Date) dynaForm
				.get("dateOfTheDocument12");
		String stDate = String.valueOf(sDate);

		if ((stDate == null) || (stDate.equals("")))
			startDate = null;
		else if (stDate != null) {
			startDate = new java.sql.Date(sDate.getTime());
		}
		java.util.Date eDate = (java.util.Date) dynaForm
				.get("dateOfTheDocument13");
		endDate = new java.sql.Date(eDate.getTime());
		String id = (String) dynaForm.get("checkValue");
		String state = request.getParameter("state");

		if (id.equals("yes"))
			request.setAttribute("radioValue", "Guarantee Approved");
		else if (id.equals("no")) {
			request.setAttribute("radioValue", "Guarantee Issued");
		}
		request.setAttribute("bankName", state);

		int i = state.indexOf("$");
		if (i != -1) {
			String newState = state.replace('$', '&');
			statesWiseReport = this.reportManager.getStatesWiseReport(newState,
					id, startDate, endDate);
			dynaForm.set("statesWiseReport", statesWiseReport);
			if ((statesWiseReport == null) || (statesWiseReport.size() == 0)) {
				throw new NoDataException(
						"No Data is available for the values entered, Please Enter Any Other Value ");
			}

			Log.log(4, "ReportsAction", "statesWiseReport", "Exited");

			return mapping.findForward("success");
		}
		statesWiseReport = this.reportManager.getStatesWiseReport(state, id,
				startDate, endDate);
		dynaForm.set("statesWiseReport", statesWiseReport);
		if ((statesWiseReport == null) || (statesWiseReport.size() == 0)) {
			throw new NoDataException(
					"No Data is available for the values entered, Please Enter Any Other Value ");
		}

		Log.log(4, "ReportsAction", "statesWiseReport", "Exited");

		return mapping.findForward("success");
	}

	public ActionForward statesWiseReportNew(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		Log.log(4, "ReportsAction", "statesWiseReportNew", "Entered");
		ArrayList statesWiseReport = new ArrayList();
		DynaActionForm dynaForm = (DynaActionForm) form;
		java.sql.Date startDate = null;
		java.sql.Date endDate = null;
		java.util.Date sDate = (java.util.Date) dynaForm
				.get("dateOfTheDocument12");
		String stDate = String.valueOf(sDate);

		if ((stDate == null) || (stDate.equals("")))
			startDate = null;
		else if (stDate != null) {
			startDate = new java.sql.Date(sDate.getTime());
		}
		java.util.Date eDate = (java.util.Date) dynaForm
				.get("dateOfTheDocument13");
		endDate = new java.sql.Date(eDate.getTime());
		String id = (String) dynaForm.get("checkValue");
		String state = request.getParameter("state");

		if (id.equals("yes"))
			request.setAttribute("radioValue", "Guarantee Approved");
		else if (id.equals("no")) {
			request.setAttribute("radioValue", "Guarantee Issued");
		}
		request.setAttribute("bankName", state);

		int i = state.indexOf("$");
		if (i != -1) {
			String newState = state.replace('$', '&');
			statesWiseReport = this.reportManager.getStatesWiseReportNew(
					newState, id, startDate, endDate);
			dynaForm.set("statesWiseReport", statesWiseReport);
			if ((statesWiseReport == null) || (statesWiseReport.size() == 0)) {
				throw new NoDataException(
						"No Data is available for the values entered, Please Enter Any Other Value ");
			}

			Log.log(4, "ReportsAction", "statesWiseReportNew", "Exited");

			return mapping.findForward("success");
		}

		statesWiseReport = this.reportManager.getStatesWiseReportNew(state, id,
				startDate, endDate);
		dynaForm.set("statesWiseReport", statesWiseReport);
		if ((statesWiseReport == null) || (statesWiseReport.size() == 0)) {
			throw new NoDataException(
					"No Data is available for the values entered, Please Enter Any Other Value ");
		}

		Log.log(4, "ReportsAction", "statesWiseReportNew", "Exited");

		return mapping.findForward("success");
	}

	public ActionForward sectorWiseReportDetailsNew(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		Log.log(4, "ReportsAction", "sectorWiseReportDetailsNew", "Entered");
		ArrayList sectorReport = new ArrayList();
		DynaActionForm dynaForm = (DynaActionForm) form;
		java.sql.Date startDate = null;
		java.sql.Date endDate = null;
		java.util.Date sDate = (java.util.Date) dynaForm
				.get("dateOfTheDocument12");
		String stDate = String.valueOf(sDate);

		if ((stDate == null) || (stDate.equals("")))
			startDate = null;
		else if (stDate != null) {
			startDate = new java.sql.Date(sDate.getTime());
		}
		java.util.Date eDate = (java.util.Date) dynaForm
				.get("dateOfTheDocument13");
		endDate = new java.sql.Date(eDate.getTime());
		String id = (String) dynaForm.get("checkValue");
		String sector = request.getParameter("Sector");

		if (id.equals("yes"))
			request.setAttribute("radioValue", "Guarantee Approved");
		else if (id.equals("no")) {
			request.setAttribute("radioValue", "Guarantee Issued");
		}
		request.setAttribute("bankName", sector);

		int i = sector.indexOf("$");
		if (i != -1) {
			String newSector = sector.replace('$', '&');
			sectorReport = this.reportManager.getSectorDetailsNew(newSector,
					id, startDate, endDate);
			dynaForm.set("sectorReport", sectorReport);
			if ((sectorReport == null) || (sectorReport.size() == 0)) {
				throw new NoDataException(
						"No Data is available for the values entered, Please Enter Any Other Value ");
			}

			Log.log(4, "ReportsAction", "sectorWiseReportDetailsNew", "Exited");

			return mapping.findForward("success");
		}

		sectorReport = this.reportManager.getSectorDetailsNew(sector, id,
				startDate, endDate);
		dynaForm.set("sectorReport", sectorReport);
		if ((sectorReport == null) || (sectorReport.size() == 0)) {
			throw new NoDataException(
					"No Data is available for the values entered, Please Enter Any Other Value ");
		}

		Log.log(4, "ReportsAction", "sectorWiseReportDetailsNew", "Exited");

		return mapping.findForward("success");
	}

	public ActionForward zoneWiseReportDetailsNew(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		Log.log(4, "ReportsAction", "zoneWiseReportDetailsNew", "Entered");
		ArrayList zoneReport = new ArrayList();
		DynaActionForm dynaForm = (DynaActionForm) form;
		java.sql.Date startDate = null;
		java.sql.Date endDate = null;
		java.util.Date sDate = (java.util.Date) dynaForm
				.get("dateOfTheDocument12");
		String stDate = String.valueOf(sDate);

		if ((stDate == null) || (stDate.equals("")))
			startDate = null;
		else if (stDate != null) {
			startDate = new java.sql.Date(sDate.getTime());
		}
		java.util.Date eDate = (java.util.Date) dynaForm
				.get("dateOfTheDocument13");
		endDate = new java.sql.Date(eDate.getTime());
		String id = (String) dynaForm.get("checkValue");
		String zone = request.getParameter("Zone");

		int i = zone.indexOf("$");
		request.setAttribute("STARTDATE", dynaForm.get("dateOfTheDocument12"));
		request.setAttribute("ENDDATE", dynaForm.get("dateOfTheDocument13"));
		if (id.equals("yes"))
			request.setAttribute("radioValue", "Guarantee Approved");
		else if (id.equals("no")) {
			request.setAttribute("radioValue", "Guarantee Issued");
		}
		request.setAttribute("bankName", zone);

		if (i != -1) {
			String newZone = zone.replace('$', '&');
			zoneReport = this.reportManager.getZoneDetailsNew(newZone, id,
					startDate, endDate);
			dynaForm.set("zoneReport", zoneReport);

			if ((zoneReport == null) || (zoneReport.size() == 0)) {
				throw new NoDataException(
						"No Data is available for the values entered, Please Enter Any Other Value ");
			}

			Log.log(4, "ReportsAction", "zoneWiseReportDetails", "Exited");

			return mapping.findForward("success");
		}

		zoneReport = this.reportManager.getZoneDetailsNew(zone, id, startDate,
				endDate);
		dynaForm.set("zoneReport", zoneReport);
		if ((zoneReport == null) || (zoneReport.size() == 0)) {
			throw new NoDataException(
					"No Data is available for the values entered, Please Enter Any Other Value ");
		}

		Log.log(4, "ReportsAction", "zoneWiseReportDetailsNew", "Exited");

		return mapping.findForward("success");
	}

	public ActionForward stateApplicationDetails(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		Log.log(4, "ReportsAction", "stateApplicationDetails", "Entered");
		ArrayList stateReport = new ArrayList();
		DynaActionForm dynaForm = (DynaActionForm) form;

		String state = request.getParameter("State");
		java.sql.Date startDate = null;
		java.sql.Date endDate = null;
		java.util.Date sDate = (java.util.Date) dynaForm
				.get("dateOfTheDocument");
		String stDate = String.valueOf(sDate);
		if ((stDate == null) || (stDate.equals(""))) {
			startDate = null;
		} else if (stDate != null) {
			startDate = new java.sql.Date(sDate.getTime());
		}

		java.util.Date eDate = (java.util.Date) dynaForm
				.get("dateOfTheDocument1");
		endDate = new java.sql.Date(eDate.getTime());
		String id = (String) dynaForm.get("checkValue");
		stateReport = this.reportManager.getStateDetails(state, id, startDate,
				endDate);
		dynaForm.set("stateReport", stateReport);
		if ((stateReport == null) || (stateReport.size() == 0)) {
			throw new NoDataException(
					"No Data is available for the values entered, Please Enter Any Other Value ");
		}

		Log.log(4, "ReportsAction", "stateApplicationDetails", "Exited");

		return mapping.findForward("success");
	}

	public ActionForward districtApplicationDetails1(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		Log.log(4, "ReportsAction", "districtApplicationDetails1", "Entered");
		ArrayList mliDistrictReport = new ArrayList();
		DynaActionForm dynaForm = (DynaActionForm) form;
		java.sql.Date startDate = null;
		java.sql.Date endDate = null;
		String flag = (String) dynaForm.get("checkValue");
		String bank = (String) dynaForm.get("state");
		java.util.Date sDate = (java.util.Date) dynaForm
				.get("dateOfTheDocument12");
		String stDate = String.valueOf(sDate);
		if ((stDate == null) || (stDate.equals(""))) {
			startDate = null;
		} else if (stDate != null) {
			startDate = new java.sql.Date(sDate.getTime());
		}
		java.util.Date eDate = (java.util.Date) dynaForm
				.get("dateOfTheDocument13");
		endDate = new java.sql.Date(eDate.getTime());
		String district = request.getParameter("District");
		String id = (String) dynaForm.get("checkValue");
		if (id.equals("yes"))
			request.setAttribute("radioValue", "Guarantee Approved");
		else if (id.equals("no")) {
			request.setAttribute("radioValue", "Guarantee Issued");
		}
		request.setAttribute("sate", district);

		int i = district.indexOf("$");
		int j = bank.indexOf("$");

		if (j != -1) {
			String newBank = bank.replace('$', '&');
			if (i != -1) {
				String newDistrict = district.replace('$', '&');
				mliDistrictReport = this.reportManager.getMliDistrictDetails(
						newDistrict, flag, newBank, startDate, endDate);
				dynaForm.set("mliDistrictReport", mliDistrictReport);
				if ((mliDistrictReport == null)
						|| (mliDistrictReport.size() == 0)) {
					throw new NoDataException(
							"No Data is available for the values entered, Please Enter Any Other Value ");
				}

				Log.log(4, "ReportsAction", "districtApplicationDetails1",
						"Exited");

				return mapping.findForward("success");
			}

			mliDistrictReport = this.reportManager.getMliDistrictDetails(
					district, flag, newBank, startDate, endDate);
			dynaForm.set("mliDistrictReport", mliDistrictReport);
			if ((mliDistrictReport == null) || (mliDistrictReport.size() == 0)) {
				throw new NoDataException(
						"No Data is available for the values entered, Please Enter Any Other Value ");
			}

			Log.log(4, "ReportsAction", "districtApplicationDetails1", "Exited");

			return mapping.findForward("success");
		}

		if (i != -1) {
			String newDistrict = district.replace('$', '&');
			mliDistrictReport = this.reportManager.getMliDistrictDetails(
					newDistrict, flag, bank, startDate, endDate);
			dynaForm.set("mliDistrictReport", mliDistrictReport);
			if ((mliDistrictReport == null) || (mliDistrictReport.size() == 0)) {
				throw new NoDataException(
						"No Data is available for the values entered, Please Enter Any Other Value ");
			}

			Log.log(4, "ReportsAction", "districtApplicationDetails1", "Exited");

			return mapping.findForward("success");
		}

		mliDistrictReport = this.reportManager.getMliDistrictDetails(district,
				flag, bank, startDate, endDate);
		dynaForm.set("mliDistrictReport", mliDistrictReport);
		if ((mliDistrictReport == null) || (mliDistrictReport.size() == 0)) {
			throw new NoDataException(
					"No Data is available for the values entered, Please Enter Any Other Value ");
		}

		Log.log(4, "ReportsAction", "districtApplicationDetails1", "Exited");

		return mapping.findForward("success");
	}

	public ActionForward guaranteeCover(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		Log.log(4, "ReportsAction", "guaranteeCover", "Entered");

		DynaActionForm dynaForm = (DynaActionForm) form;
		java.util.Date date = new java.util.Date();
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(date);
		int month = calendar.get(2);
		int day = calendar.get(5);
		month -= 1;
		day += 1;
		calendar.set(2, month);
		calendar.set(5, day);
		java.util.Date prevDate = calendar.getTime();

		GeneralReport generalReport = new GeneralReport();
		generalReport.setDateOfTheDocument(prevDate);
		generalReport.setDateOfTheDocument1(date);
		BeanUtils.copyProperties(dynaForm, generalReport);
		Log.log(4, "ReportsAction", "guaranteeCover", "Exited");

		return mapping.findForward("success");
	}

	public ActionForward guaranteeCoverReport(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		Log.log(4, "ReportsAction", "guaranteeCoverReport", "Entered");

		ArrayList guaranteeDetails = new ArrayList();
		DynaActionForm dynaForm = (DynaActionForm) form;
		java.sql.Date startDate = null;
		java.sql.Date endDate = null;

		java.util.Date sDate = (java.util.Date) dynaForm
				.get("dateOfTheDocument");
		String stDate = String.valueOf(sDate);

		if ((stDate == null) || (stDate.equals("")))
			startDate = null;
		else if (stDate != null) {
			startDate = new java.sql.Date(sDate.getTime());
		}
		String guarantee = (String) dynaForm.get("check");

		if (guarantee.equals("yes"))
			request.setAttribute("radioValue", "CGPAN");
		else if (guarantee.equals("no")) {
			request.setAttribute("radioValue", "BORROWER/Unit Name");
		}
		java.util.Date eDate = (java.util.Date) dynaForm
				.get("dateOfTheDocument1");
		endDate = new java.sql.Date(eDate.getTime());

		if (guarantee.equals("yes")) {
			guaranteeDetails = this.reportManager.guaranteeCoverMli(startDate,
					endDate);
			dynaForm.set("guaranteeCoverSsi", guaranteeDetails);

			if ((guaranteeDetails == null) || (guaranteeDetails.size() == 0)) {
				throw new NoDataException(
						"No Data is available for the values entered, Please Enter Any Other Value ");
			}

			guaranteeDetails = null;
			Log.log(4, "ReportsAction", "guaranteeCoverReport", "Exited");

			return mapping.findForward("success1");
		}

		guaranteeDetails = this.reportManager.guaranteeCoverSsiMli(startDate,
				endDate);
		dynaForm.set("guaranteeCoverSsi", guaranteeDetails);
		if ((guaranteeDetails == null) || (guaranteeDetails.size() == 0)) {
			throw new NoDataException(
					"No Data is available for the values entered, Please Enter Any Other Value ");
		}

		guaranteeDetails = null;
		Log.log(4, "ReportsAction", "guaranteeCoverReport", "Exited");

		return mapping.findForward("success2");
	}

	public ActionForward guaranteeCoverReportDetails(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		Log.log(4, "ReportsAction", "guaranteeCoverReportDetails", "Entered");
		ArrayList guaranteeDetails = new ArrayList();
		DynaActionForm dynaForm = (DynaActionForm) form;
		java.sql.Date startDate = null;
		java.sql.Date endDate = null;
		String bank = (String) dynaForm.get("bank");
		java.util.Date sDate = (java.util.Date) dynaForm
				.get("dateOfTheDocument");
		String stDate = String.valueOf(sDate);

		String guarantee = (String) dynaForm.get("check");

		if (guarantee.equals("yes"))
			request.setAttribute("radioValue", "CGPAN");
		else if (guarantee.equals("no")) {
			request.setAttribute("radioValue", "BORROWER/Unit Name");
		}
		request.setAttribute("BANKNAME", request.getParameter("name"));

		if ((stDate == null) || (stDate.equals(""))) {
			startDate = null;
		} else if (stDate != null) {
			startDate = new java.sql.Date(sDate.getTime());
		}

		java.util.Date eDate = (java.util.Date) dynaForm
				.get("dateOfTheDocument1");
		endDate = new java.sql.Date(eDate.getTime());

		guaranteeDetails = this.reportManager.getGuaranteeCover(startDate,
				endDate, bank);
		dynaForm.set("guaranteeCoverSsi", guaranteeDetails);
		if ((guaranteeDetails == null) || (guaranteeDetails.size() == 0)) {
			throw new NoDataException(
					"No Data is available for the values entered, Please Enter Any Other Value ");
		}

		guaranteeDetails = null;
		Log.log(4, "ReportsAction", "guaranteeCoverReportDetails", "Exited");

		return mapping.findForward("success");
	}

	public ActionForward guaranteeCoverReportDetailsSsi(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		Log.log(4, "ReportsAction", "guaranteeCoverReportDetailsSsi", "Entered");
		ArrayList guaranteeDetails = new ArrayList();
		DynaActionForm dynaForm = (DynaActionForm) form;
		java.sql.Date startDate = null;
		java.sql.Date endDate = null;
		String bank = (String) dynaForm.get("bank");
		java.util.Date sDate = (java.util.Date) dynaForm
				.get("dateOfTheDocument");
		String stDate = String.valueOf(sDate);

		String guarantee = (String) dynaForm.get("check");

		if (guarantee.equals("yes"))
			request.setAttribute("radioValue", "CGPAN");
		else if (guarantee.equals("no")) {
			request.setAttribute("radioValue", "BORROWER/Unit Name");
		}
		request.setAttribute("BANKNAME", request.getParameter("name"));

		if ((stDate == null) || (stDate.equals(""))) {
			startDate = null;
		} else if (stDate != null) {
			startDate = new java.sql.Date(sDate.getTime());
		}

		java.util.Date eDate = (java.util.Date) dynaForm
				.get("dateOfTheDocument1");
		endDate = new java.sql.Date(eDate.getTime());

		guaranteeDetails = this.reportManager.getGuaranteeCoverSsi(startDate,
				endDate, bank);
		dynaForm.set("guaranteeCoverSsi", guaranteeDetails);
		if ((guaranteeDetails == null) || (guaranteeDetails.size() == 0)) {
			throw new NoDataException(
					"No Data is available for the values entered, Please Enter Any Other Value ");
		}

		guaranteeDetails = null;
		Log.log(4, "ReportsAction", "guaranteeCoverReportDetailsSsi", "Exited");

		return mapping.findForward("success");
	}

	public ActionForward sizeWiseReport(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		Log.log(4, "ReportsAction", "sizeWiseReport", "Entered");

		DynaActionForm dynaForm = (DynaActionForm) form;
		java.util.Date date = new java.util.Date();
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(date);
		int month = calendar.get(2);
		int day = calendar.get(5);
		month -= 1;
		day += 1;
		calendar.set(2, month);
		calendar.set(5, day);
		java.util.Date prevDate = calendar.getTime();

		GeneralReport generalReport = new GeneralReport();
		generalReport.setDateOfTheDocument6(prevDate);
		generalReport.setDateOfTheDocument7(date);
		BeanUtils.copyProperties(dynaForm, generalReport);

		Log.log(4, "ReportsAction", "sizeWiseReport", "Exited");

		return mapping.findForward("success");
	}

	public ActionForward sizeWiseReportDetails(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		Log.log(4, "ReportsAction", "sizeWiseReportDetails", "Entered");
		ArrayList ProposalDistrictReport = new ArrayList();
		DynaActionForm dynaForm = (DynaActionForm) form;
		java.sql.Date startDate = null;
		java.sql.Date endDate = null;
		String guarantee = (String) dynaForm.get("checkValue");
		java.util.Date sDate = (java.util.Date) dynaForm
				.get("dateOfTheDocument6");
		String stDate = String.valueOf(sDate);

		if ((stDate == null) || (stDate.equals("")))
			startDate = null;
		else if (stDate != null) {
			startDate = new java.sql.Date(sDate.getTime());
		}

		if (guarantee.equals("yes"))
			request.setAttribute("radioValue", "Guarantee Approved");
		else if (guarantee.equals("no")) {
			request.setAttribute("radioValue", "Guarantee Issued");
		}
		java.util.Date eDate = (java.util.Date) dynaForm
				.get("dateOfTheDocument7");
		endDate = new java.sql.Date(eDate.getTime());
		ProposalDistrictReport = this.reportManager.getProposalSizeReport(
				startDate, endDate, guarantee);
		dynaForm.set("proposalSizeReport", ProposalDistrictReport);
		if ((ProposalDistrictReport == null)
				|| (ProposalDistrictReport.size() == 0)) {
			throw new NoDataException(
					"No Data is available for the values entered, Please Enter Any Other Value ");
		}

		ProposalDistrictReport = null;
		Log.log(4, "ReportsAction", "sizeWiseReportDetails", "Exited");

		return mapping.findForward("success");
	}

	public ActionForward sectorWiseReportDetails(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		Log.log(4, "ReportsAction", "sectorWiseReportDetails", "Entered");
		ArrayList ProposalDistrictReport = new ArrayList();
		DynaActionForm dynaForm = (DynaActionForm) form;
		java.sql.Date startDate = null;
		java.sql.Date endDate = null;
		String guarantee = (String) dynaForm.get("checkValue");
		java.util.Date sDate = (java.util.Date) dynaForm
				.get("dateOfTheDocument8");
		String stDate = String.valueOf(sDate);

		if (guarantee.equals("yes"))
			request.setAttribute("radioValue", "Guarantee Approved");
		else if (guarantee.equals("no")) {
			request.setAttribute("radioValue", "Guarantee Issued");
		}
		if ((stDate == null) || (stDate.equals("")))
			startDate = null;
		else if (stDate != null) {
			startDate = new java.sql.Date(sDate.getTime());
		}
		java.util.Date eDate = (java.util.Date) dynaForm
				.get("dateOfTheDocument9");
		endDate = new java.sql.Date(eDate.getTime());
		ProposalDistrictReport = this.reportManager.getProposalSectorReport(
				startDate, endDate, guarantee);
		dynaForm.set("proposalDistrictReport", ProposalDistrictReport);
		if ((ProposalDistrictReport == null)
				|| (ProposalDistrictReport.size() == 0)) {
			throw new NoDataException(
					"No Data is available for the values entered, Please Enter Any Other Value ");
		}

		ProposalDistrictReport = null;
		Log.log(4, "ReportsAction", "sectorWiseReportDetails", "Exited");

		return mapping.findForward("success");
	}

	public ActionForward showFilterForClaimDetails(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		ClaimActionForm claimForm = (ClaimActionForm) form;
		java.util.Date date = new java.util.Date();
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(date);
		int month = calendar.get(2);
		int day = calendar.get(5);
		month -= 1;
		day += 1;
		calendar.set(2, month);
		calendar.set(5, day);
		java.util.Date prevDate = calendar.getTime();
		CustomisedDate customDate = new CustomisedDate();
		customDate.setDate(prevDate);

		CustomisedDate customToDate = new CustomisedDate();
		customToDate.setDate(date);

		claimForm.setFromDate(customDate);
		claimForm.setToDate(customToDate);
		claimForm.setClmApplicationStatus("AP");

		return mapping.findForward("displayFilterForClaimDtls");
	}

	public ActionForward displayListOfClaimRefNumbers(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {

		Log.log(Log.INFO, "NewReportsAction", "displayListOfClaimRefNumbers",
				"Entered");
		ClaimActionForm claimForm = (ClaimActionForm) form;

		User user = getUserInformation(request);
		String bankid = (user.getBankId()).trim();
		String zoneid = (user.getZoneId()).trim();
		String branchid = (user.getBranchId()).trim();
		String memberId = bankid + zoneid + branchid;

		java.util.Date fromDate = (java.util.Date) claimForm.getFromDate();
		java.util.Date toDate = (java.util.Date) claimForm.getToDate();
		String clmApplicationStatus = (String) claimForm
				.getClmApplicationStatus();

		claimForm.setstatusFlag(clmApplicationStatus);
		ReportManager manager = new ReportManager();
		Vector listOfClmRefNumbers = null;
		java.sql.Date sqlFromDate = null;
		ReportsAction ra = new ReportsAction();

		if (fromDate.toString().equals("")) {
			listOfClmRefNumbers = ra.getListOfClaimRefNumbersNewLatest(null,
					new java.sql.Date(toDate.getTime()), clmApplicationStatus,
					memberId);
		} else {
			sqlFromDate = new java.sql.Date(fromDate.getTime());
			listOfClmRefNumbers = ra.getListOfClaimRefNumbersNewLatest(
					sqlFromDate, new java.sql.Date(toDate.getTime()),
					clmApplicationStatus, memberId);
		}

		if (clmApplicationStatus.equals("AP"))
			request.setAttribute("radioValue", "Approved");
		else if (clmApplicationStatus.equals("NE"))
			request.setAttribute("radioValue", "NEW");
		else if (clmApplicationStatus.equals("RE"))
			request.setAttribute("radioValue", "Rejected");
		else if (clmApplicationStatus.equals("HO"))
			request.setAttribute("radioValue", "Hold");
		else if (clmApplicationStatus.equals("FW"))
			request.setAttribute("radioValue", "Forwarded");
		else if (clmApplicationStatus.equals("TC"))
			request.setAttribute("radioValue", "Temporary Closed");
		else if (clmApplicationStatus.equals("TR"))
			request.setAttribute("radioValue", "Temporary Rejected");
		else if (clmApplicationStatus.equals("WD"))
			request.setAttribute("radioValue", "Claim Withdrawn");

		// added by upchar@path on 03/07/2013
		else if (clmApplicationStatus.equals("RR"))
			request.setAttribute("radioValue", "Reply Received");

		/* end */
		else if (clmApplicationStatus.equals("RT"))
			request.setAttribute("radioValue", "Returned");

		else if (clmApplicationStatus.equals("RTD"))
			request.setAttribute("radioValue", "TempororyRejected/Rejcted");

		else if (clmApplicationStatus.equals("AS"))
			request.setAttribute("radioValue", "All Statuses Report");

		claimForm.setListOfClmRefNumbers(listOfClmRefNumbers);
		if (listOfClmRefNumbers != null) {
			if (listOfClmRefNumbers.size() == 0) {
				throw new NoDataException("There are no Claim Ref Numbers "
						+ "that match the query.");
			}
		}
		Log.log(Log.INFO, "NewReportsAction", "displayListOfClaimRefNumbers",
				"Exited");
		return mapping.findForward("getListOfClmRefNumbers");
	}

	// kot
	public Vector getListOfClaimRefNumbersNewLatest(java.sql.Date fromDate,
			java.sql.Date toDate, String clmApplicationStatusFlag,
			String memberId) throws DatabaseException {

		String bankId = memberId.substring(0, 4);
		String zoneId = memberId.substring(4, 8);
		String branchId = memberId.substring(8, 12);

		ReportsAction ra = new ReportsAction();

		if (bankId.equals(Constants.CGTSI_USER_BANK_ID)) {
			return ra.getListOfClaimRefNumbersNew1(fromDate, toDate,
					clmApplicationStatusFlag);
		}
		if ((zoneId.equals("0000")) && (branchId.equals("0000"))) {
			return ra.getListOfClaimRefNumbersNew2(fromDate, toDate,
					clmApplicationStatusFlag, bankId);
		}
		if (branchId.equals("0000")) {
			return ra.getListOfClaimRefNumbersNew3(fromDate, toDate,
					clmApplicationStatusFlag, bankId, zoneId);
		} else {
			return ra.getListOfClaimRefNumbersNew4(fromDate, toDate,
					clmApplicationStatusFlag, bankId, zoneId, branchId);
		}
	}

	// kot end

	public ActionForward displayListOfClaimRefNumbersold(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		Log.log(4, "ReportsAction", "displayListOfClaimRefNumbers", "Entered");
		ClaimActionForm claimForm = (ClaimActionForm) form;

		User user = getUserInformation(request);
		String bankid = user.getBankId().trim();
		String zoneid = user.getZoneId().trim();
		String branchid = user.getBranchId().trim();
		String memberId = bankid + zoneid + branchid;

		java.util.Date fromDate = claimForm.getFromDate();
		java.util.Date toDate = claimForm.getToDate();
		String clmApplicationStatus = claimForm.getClmApplicationStatus();
		// System.out.println("clmApplicationStatus:" + clmApplicationStatus);
		claimForm.setstatusFlag(clmApplicationStatus);
		ReportManager manager = new ReportManager();
		Vector listOfClmRefNumbers = null;
		java.sql.Date sqlFromDate = null;

		if (fromDate.toString().equals("")) {
			listOfClmRefNumbers = manager.getListOfClaimRefNumbersNew(null,
					new java.sql.Date(toDate.getTime()), clmApplicationStatus,
					memberId);
		} else {
			sqlFromDate = new java.sql.Date(fromDate.getTime());
			listOfClmRefNumbers = manager.getListOfClaimRefNumbersNew(
					sqlFromDate, new java.sql.Date(toDate.getTime()),
					clmApplicationStatus, memberId);
		}

		if (clmApplicationStatus.equals("AP"))
			request.setAttribute("radioValue", "Approved");
		else if (clmApplicationStatus.equals("NE"))
			request.setAttribute("radioValue", "NEW");
		else if (clmApplicationStatus.equals("RE"))
			request.setAttribute("radioValue", "Rejected");
		else if (clmApplicationStatus.equals("HO"))
			request.setAttribute("radioValue", "Hold");
		else if (clmApplicationStatus.equals("FW"))
			request.setAttribute("radioValue", "Forwarded");
		else if (clmApplicationStatus.equals("TC"))
			request.setAttribute("radioValue", "Temporary Closed");
		else if (clmApplicationStatus.equals("TR"))
			request.setAttribute("radioValue", "Temporary Rejected");
		else if (clmApplicationStatus.equals("WD")) {
			request.setAttribute("radioValue", "Claim Withdrawn");
		} else if (clmApplicationStatus.equals("RT")) {
			request.setAttribute("radioValue", "Returned");
		}
		if (listOfClmRefNumbers != null) {
			if (listOfClmRefNumbers.size() == 0) {
				throw new NoDataException(
						"There are no Claim Ref Numbers that match the query.");
			}
		}
		claimForm.setListOfClmRefNumbers(listOfClmRefNumbers);
		listOfClmRefNumbers = null;
		Log.log(4, "ReportsAction", "displayListOfClaimRefNumbers", "Exited");
		return mapping.findForward("getListOfClmRefNumbers");
	}

	public ActionForward displayClmRefNumberDtl(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		Log.log(4, "ReportsAction", "displayClmRefNumberDtl", "Entered");
		ReportManager manager = new ReportManager();
		ClaimActionForm claimForm = (ClaimActionForm) form;

		String clmApplicationStatus = claimForm.getClmApplicationStatus();
		Log.log(4, "ReportsAction", "displayClmRefNumberDtl",
				"Claim Application Status being queried :"
						+ clmApplicationStatus);

		User user = getUserInformation(request);
		String bankid = user.getBankId().trim();
		String zoneid = user.getZoneId().trim();
		String branchid = user.getBranchId().trim();
		String memberId = bankid + zoneid + branchid;

		if (clmApplicationStatus.equals("AP"))
			request.setAttribute("radioValue", "Approved");
		else if (clmApplicationStatus.equals("NE"))
			request.setAttribute("radioValue", "NEW");
		else if (clmApplicationStatus.equals("RE"))
			request.setAttribute("radioValue", "Rejected");
		else if (clmApplicationStatus.equals("HO"))
			request.setAttribute("radioValue", "Hold");
		else if (clmApplicationStatus.equals("FW"))
			request.setAttribute("radioValue", "Forwarded");
		else if (clmApplicationStatus.equals("TC"))
			request.setAttribute("radioValue", "Temporary Closed");
		else if (clmApplicationStatus.equals("TR")) {
			request.setAttribute("radioValue", "Temporary Rejected");
		} else if (clmApplicationStatus.equals("RT")) {
			request.setAttribute("radioValue", "Returned");
		}
		// System.out.println("clmApplicationStatus:" + clmApplicationStatus);
		request.setAttribute("CLAIMREFNO",
				request.getParameter("ClaimRefNumber"));

		ClaimsProcessor processor = new ClaimsProcessor();
		String claimRefNumber = request.getParameter("ClaimRefNumber");

		ClaimApplication claimapplication = manager.displayClmRefNumberDtl(
				claimRefNumber, clmApplicationStatus, memberId);
		ArrayList clmSummryDtls = claimapplication.getClaimSummaryDtls();

		User userInfo = getUserInformation(request);
		if (claimapplication.getFirstInstallment()) {
			String thiscgpn = null;
			String bid = claimapplication.getBorrowerId();

			String memId = claimapplication.getMemberId();

			// Vector cgpnDetails = processor.getCGPANDetailsForBorrowerId(bid,
			// memId);
			claimapplication.setClaimSummaryDtls(clmSummryDtls);
			claimForm.setClaimapplication(claimapplication);

			boolean internetUser = true;

			if (((userInfo.getBankId() + userInfo.getZoneId() + userInfo
					.getBranchId()).equals("000000000000"))
					&& (!userInfo.getUserId().equalsIgnoreCase("DEMOUSER"))) {
				internetUser = false;
			}

			Map attachments = manager.getClaimAttachments(claimRefNumber,
					clmApplicationStatus, internetUser);

			if (attachments.get("recallNotice") != null) {
				UploadFileProperties uploadFile = (UploadFileProperties) attachments
						.get("recallNotice");

				String formattedToOSPath = createNewFile(request,
						uploadFile.getFileName(), uploadFile.getFileSize());

				Log.log(5, "ReportsAction", "createNewFile",
						" Recall Notice Attachment path " + formattedToOSPath);

				request.setAttribute("recallNoticeAttachment",
						formattedToOSPath);
			}

			if (attachments.get("legalDetails") != null) {
				UploadFileProperties uploadFile = (UploadFileProperties) attachments
						.get("legalDetails");

				String formattedToOSPath = createNewFile(request,
						uploadFile.getFileName(), uploadFile.getFileSize());

				Log.log(5, "ReportsAction", "createNewFile",
						" Legal Details Attachment path " + formattedToOSPath);

				request.setAttribute("legalDetailsAttachment",
						formattedToOSPath);
			}

			if (claimapplication == null) {
				throw new NoDataException(
						"Unable to get Claim Application details.");
			}

			return mapping.findForward("showFirstClmDetails");
		}
		if (claimapplication.getSecondInstallment()) {
			claimForm.setClaimapplication(claimapplication);

			String bid = claimapplication.getBorrowerId();
			String memId = claimapplication.getMemberId();
			Vector cgpnDetails = processor.getCGPANDetailsForBorrowerId(bid,
					memId);
			Vector clmAppliedAmnts = processor.getClaimAppliedAmounts(bid, "F");
			Vector updateClmDtls = new Vector();
			String thiscgpn = null;

			for (int i = 0; i < cgpnDetails.size(); i++) {
				HashMap dtl = (HashMap) cgpnDetails.elementAt(i);

				if (dtl != null) {
					thiscgpn = (String) dtl.get("CGPAN");
					if (thiscgpn != null) {
						for (int j = 0; j < clmAppliedAmnts.size(); j++) {
							HashMap clmAppliedDtl = (HashMap) clmAppliedAmnts
									.elementAt(j);
							String cgpnInAppliedAmntsVec = null;
							if (clmAppliedDtl != null) {
								cgpnInAppliedAmntsVec = (String) clmAppliedDtl
										.get("CGPAN");
								if (cgpnInAppliedAmntsVec != null) {
									if (cgpnInAppliedAmntsVec.equals(thiscgpn)) {
										double clmAppliedAmnt = 0.0D;
										Double clmAppAmntObj = (Double) clmAppliedDtl
												.get("ClaimAppliedAmnt");
										if (clmAppAmntObj != null) {
											clmAppliedAmnt = clmAppAmntObj
													.doubleValue();
										} else {
											clmAppliedAmnt = 0.0D;
										}

										dtl.put("ClaimAppliedAmnt", new Double(
												clmAppliedAmnt));
										if (!updateClmDtls.contains(dtl)) {
											updateClmDtls.addElement(dtl);
										}

										clmAppliedDtl = null;
										break;
									}

								}

							} else
								;
						}

					} else {
						continue;
					}
				} else {
					continue;
				}

				dtl = null;
			}

			HashMap settlmntDetails = processor
					.getClaimSettlementDetailForBorrower(bid);
			double firstSettlementAmnt = 0.0D;
			Double firstSettlementAmntObj = (Double) settlmntDetails
					.get("FirstSettlmntAmnt");
			if (firstSettlementAmntObj != null) {
				firstSettlementAmnt = firstSettlementAmntObj.doubleValue();
			}
			java.util.Date firstSettlementDt = (java.util.Date) settlmntDetails
					.get("FirstSettlmntDt");

			HashMap dtl = null;
			Vector finalUpdatedDtls = new Vector();

			for (int i = 0; i < updateClmDtls.size(); i++) {
				dtl = (HashMap) updateClmDtls.elementAt(i);

				if (dtl != null) {
					dtl.put("FirstSettlmntAmnt",
							new Double(firstSettlementAmnt));
					dtl.put("FirstSettlmntDt", firstSettlementDt);
					if (!finalUpdatedDtls.contains(dtl)) {
						finalUpdatedDtls.addElement(dtl);
					}

					dtl = null;
				}
			}
			ArrayList clmSummaryDtls = claimapplication.getClaimSummaryDtls();
			for (int j = 0; j < clmSummaryDtls.size(); j++) {
				ClaimSummaryDtls dtls = (ClaimSummaryDtls) clmSummaryDtls
						.get(j);
				String cgpan = null;
				double clmappliedamnt = 0.0D;
				if (dtls != null) {
					cgpan = dtls.getCgpan();
					clmappliedamnt = dtls.getAmount();
				}
				for (int i = 0; i < updateClmDtls.size(); i++) {
					dtl = (HashMap) updateClmDtls.elementAt(i);

					if (dtl != null) {
						String pan = (String) dtl.get("CGPAN");
						if (pan.equals(cgpan)) {
							dtl = (HashMap) updateClmDtls.remove(i);
							dtl.put("FirstSettlmntAmnt", new Double(
									firstSettlementAmnt));
							dtl.put("FirstSettlmntDt", firstSettlementDt);
							dtl.put("SECClaimAppliedAmnt", new Double(
									clmappliedamnt));
						}

						dtl = null;
					}
				}
			}

			claimForm.setUpdatedClaimDtls(finalUpdatedDtls);
			boolean internetUser = true;

			if (((userInfo.getBankId() + userInfo.getZoneId() + userInfo
					.getBranchId()).equals("000000000000"))
					&& (!userInfo.getUserId().equalsIgnoreCase("DEMOUSER"))) {
				internetUser = false;
			}

			Map attachments = manager.getClaimAttachments(claimRefNumber,
					clmApplicationStatus, internetUser);

			if (attachments.get("recallNotice") != null) {
				UploadFileProperties uploadFile = (UploadFileProperties) attachments
						.get("recallNotice");

				String formattedToOSPath = createNewFile(request,
						uploadFile.getFileName(), uploadFile.getFileSize());

				Log.log(5, "ReportsAction", "createNewFile",
						" Recall Notice Attachment path " + formattedToOSPath);

				request.setAttribute("recallNoticeAttachment",
						formattedToOSPath);
			}

			if (attachments.get("legalDetails") != null) {
				UploadFileProperties uploadFile = (UploadFileProperties) attachments
						.get("legalDetails");

				String formattedToOSPath = createNewFile(request,
						uploadFile.getFileName(), uploadFile.getFileSize());

				Log.log(5, "ReportsAction", "createNewFile",
						" Legal Details Attachment path " + formattedToOSPath);

				request.setAttribute("legalDetailsAttachment",
						formattedToOSPath);
			}

			return mapping.findForward("showSecClmDetails");
		}
		Log.log(4, "ReportsAction", "displayClmRefNumberDtl", "Exited");

		return null;
	}

	public ActionForward displayClmApplicationDtlNew(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		Log.log(4, "ReportsAction", "displayClmApplicationDtlNew", "Entered");
		ReportManager manager = new ReportManager();
		ClaimActionForm claimForm = (ClaimActionForm) form;

		String clmApplicationStatus = claimForm.getClmApplicationStatus();
		clmApplicationStatus = "NE";
		Log.log(4, "ReportsAction", "displayClmApplicationDtlNew",
				"Claim Application Status being queried :"
						+ clmApplicationStatus);

		User user = getUserInformation(request);
		String bankid = user.getBankId().trim();
		String zoneid = user.getZoneId().trim();
		String branchid = user.getBranchId().trim();
		String memberId = bankid + zoneid + branchid;

		request.setAttribute("CLAIMREFNO",
				request.getParameter("ClaimRefNumber"));

		ClaimsProcessor processor = new ClaimsProcessor();
		String claimRefNumber = request.getParameter("ClaimRefNumber");

		ClaimApplication claimapplication = manager.displayClmRefNumberDtl(
				claimRefNumber, clmApplicationStatus, memberId);
		ArrayList clmSummryDtls = claimapplication.getClaimSummaryDtls();

		User userInfo = getUserInformation(request);
		if (claimapplication.getFirstInstallment()) {
			String thiscgpn = null;
			String bid = claimapplication.getBorrowerId();

			String memId = claimapplication.getMemberId();

			// Vector cgpnDetails = processor.getCGPANDetailsForBorrowerId(bid,
			// memId);

			claimapplication.setClaimSummaryDtls(clmSummryDtls);
			claimForm.setClaimapplication(claimapplication);

			boolean internetUser = true;

			if (((userInfo.getBankId() + userInfo.getZoneId() + userInfo
					.getBranchId()).equals("000000000000"))
					&& (!userInfo.getUserId().equalsIgnoreCase("DEMOUSER"))) {
				internetUser = false;
			}

			Map attachments = manager.getClaimAttachments(claimRefNumber,
					clmApplicationStatus, internetUser);

			if (attachments.get("recallNotice") != null) {
				UploadFileProperties uploadFile = (UploadFileProperties) attachments
						.get("recallNotice");

				String formattedToOSPath = createNewFile(request,
						uploadFile.getFileName(), uploadFile.getFileSize());

				Log.log(5, "ReportsAction", "createNewFile",
						" Recall Notice Attachment path " + formattedToOSPath);

				request.setAttribute("recallNoticeAttachment",
						formattedToOSPath);
			}

			if (attachments.get("legalDetails") != null) {
				UploadFileProperties uploadFile = (UploadFileProperties) attachments
						.get("legalDetails");

				String formattedToOSPath = createNewFile(request,
						uploadFile.getFileName(), uploadFile.getFileSize());

				Log.log(5, "ReportsAction", "createNewFile",
						" Legal Details Attachment path " + formattedToOSPath);

				request.setAttribute("legalDetailsAttachment",
						formattedToOSPath);
			}

			return mapping.findForward("showFirstClmDetails");
		}
		if (claimapplication.getSecondInstallment()) {
			claimForm.setClaimapplication(claimapplication);

			String bid = claimapplication.getBorrowerId();
			String memId = claimapplication.getMemberId();
			Vector cgpnDetails = processor.getCGPANDetailsForBorrowerId(bid,
					memId);
			Vector clmAppliedAmnts = processor.getClaimAppliedAmounts(bid, "F");
			Vector updateClmDtls = new Vector();
			String thiscgpn = null;

			for (int i = 0; i < cgpnDetails.size(); i++) {
				HashMap dtl = (HashMap) cgpnDetails.elementAt(i);

				if (dtl != null) {
					thiscgpn = (String) dtl.get("CGPAN");
					if (thiscgpn != null) {
						for (int j = 0; j < clmAppliedAmnts.size(); j++) {
							HashMap clmAppliedDtl = (HashMap) clmAppliedAmnts
									.elementAt(j);
							String cgpnInAppliedAmntsVec = null;
							if (clmAppliedDtl != null) {
								cgpnInAppliedAmntsVec = (String) clmAppliedDtl
										.get("CGPAN");
								if (cgpnInAppliedAmntsVec != null) {
									if (cgpnInAppliedAmntsVec.equals(thiscgpn)) {
										double clmAppliedAmnt = 0.0D;
										Double clmAppAmntObj = (Double) clmAppliedDtl
												.get("ClaimAppliedAmnt");
										if (clmAppAmntObj != null) {
											clmAppliedAmnt = clmAppAmntObj
													.doubleValue();
										} else {
											clmAppliedAmnt = 0.0D;
										}

										dtl.put("ClaimAppliedAmnt", new Double(
												clmAppliedAmnt));
										if (!updateClmDtls.contains(dtl)) {
											updateClmDtls.addElement(dtl);
										}

										clmAppliedDtl = null;
										break;
									}

								}

							} else
								;
						}

					} else {
						continue;
					}
				} else {
					continue;
				}

				dtl = null;
			}

			HashMap settlmntDetails = processor
					.getClaimSettlementDetailForBorrower(bid);
			double firstSettlementAmnt = 0.0D;
			Double firstSettlementAmntObj = (Double) settlmntDetails
					.get("FirstSettlmntAmnt");
			if (firstSettlementAmntObj != null) {
				firstSettlementAmnt = firstSettlementAmntObj.doubleValue();
			}
			java.util.Date firstSettlementDt = (java.util.Date) settlmntDetails
					.get("FirstSettlmntDt");

			HashMap dtl = null;
			Vector finalUpdatedDtls = new Vector();

			for (int i = 0; i < updateClmDtls.size(); i++) {
				dtl = (HashMap) updateClmDtls.elementAt(i);

				if (dtl != null) {
					dtl.put("FirstSettlmntAmnt",
							new Double(firstSettlementAmnt));
					dtl.put("FirstSettlmntDt", firstSettlementDt);
					if (!finalUpdatedDtls.contains(dtl)) {
						finalUpdatedDtls.addElement(dtl);
					}

					dtl = null;
				}
			}
			ArrayList clmSummaryDtls = claimapplication.getClaimSummaryDtls();
			for (int j = 0; j < clmSummaryDtls.size(); j++) {
				ClaimSummaryDtls dtls = (ClaimSummaryDtls) clmSummaryDtls
						.get(j);
				String cgpan = null;
				double clmappliedamnt = 0.0D;
				if (dtls != null) {
					cgpan = dtls.getCgpan();
					clmappliedamnt = dtls.getAmount();
				}
				for (int i = 0; i < updateClmDtls.size(); i++) {
					dtl = (HashMap) updateClmDtls.elementAt(i);

					if (dtl != null) {
						String pan = (String) dtl.get("CGPAN");
						if (pan.equals(cgpan)) {
							dtl = (HashMap) updateClmDtls.remove(i);
							dtl.put("FirstSettlmntAmnt", new Double(
									firstSettlementAmnt));
							dtl.put("FirstSettlmntDt", firstSettlementDt);
							dtl.put("SECClaimAppliedAmnt", new Double(
									clmappliedamnt));
						}

						dtl = null;
					}
				}
			}

			claimForm.setUpdatedClaimDtls(finalUpdatedDtls);
			boolean internetUser = true;

			if (((userInfo.getBankId() + userInfo.getZoneId() + userInfo
					.getBranchId()).equals("000000000000"))
					&& (!userInfo.getUserId().equalsIgnoreCase("DEMOUSER"))) {
				internetUser = false;
			}

			Map attachments = manager.getClaimAttachments(claimRefNumber,
					clmApplicationStatus, internetUser);

			if (attachments.get("recallNotice") != null) {
				UploadFileProperties uploadFile = (UploadFileProperties) attachments
						.get("recallNotice");

				String formattedToOSPath = createNewFile(request,
						uploadFile.getFileName(), uploadFile.getFileSize());

				Log.log(5, "ReportsAction", "createNewFile",
						" Recall Notice Attachment path " + formattedToOSPath);

				request.setAttribute("recallNoticeAttachment",
						formattedToOSPath);
			}

			if (attachments.get("legalDetails") != null) {
				UploadFileProperties uploadFile = (UploadFileProperties) attachments
						.get("legalDetails");

				String formattedToOSPath = createNewFile(request,
						uploadFile.getFileName(), uploadFile.getFileSize());

				Log.log(5, "ReportsAction", "createNewFile",
						" Legal Details Attachment path " + formattedToOSPath);

				request.setAttribute("legalDetailsAttachment",
						formattedToOSPath);
			}

			return mapping.findForward("showSecClmDetails");
		}
		Log.log(4, "ReportsAction", "displayClmApplicationDtlNew", "Exited");

		return null;
	}

	public ActionForward displayClmApplicationDtlNewRev(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		Log.log(4, "ReportsAction", "displayClmApplicationDtlNewRev", "Entered");
		ReportManager manager = new ReportManager();
		ClaimActionForm claimForm = (ClaimActionForm) form;

		String clmApplicationStatus = claimForm.getClmApplicationStatus();
		clmApplicationStatus = "NE";
		Log.log(4, "ReportsAction", "displayClmApplicationDtlNewRev",
				"Claim Application Status being queried :"
						+ clmApplicationStatus);

		User user = getUserInformation(request);
		String bankid = user.getBankId().trim();
		String zoneid = user.getZoneId().trim();
		String branchid = user.getBranchId().trim();
		String memberId = bankid + zoneid + branchid;

		request.setAttribute("CLAIMREFNO",
				request.getParameter("ClaimRefNumber"));

		ClaimsProcessor processor = new ClaimsProcessor();
		String claimRefNumber = request.getParameter("ClaimRefNumber");

		ClaimApplication claimapplication = manager.displayClmRefNumberDtl(
				claimRefNumber, clmApplicationStatus, memberId);
		ArrayList clmSummryDtls = claimapplication.getClaimSummaryDtls();

		User userInfo = getUserInformation(request);
		if (claimapplication.getFirstInstallment()) {
			String thiscgpn = null;
			String bid = claimapplication.getBorrowerId();

			String memId = claimapplication.getMemberId();

			// Vector cgpnDetails = processor.getCGPANDetailsForBorrowerId(bid,
			// memId);

			claimapplication.setClaimSummaryDtls(clmSummryDtls);
			claimForm.setClaimapplication(claimapplication);

			boolean internetUser = true;

			if (((userInfo.getBankId() + userInfo.getZoneId() + userInfo
					.getBranchId()).equals("000000000000"))
					&& (!userInfo.getUserId().equalsIgnoreCase("DEMOUSER"))) {
				internetUser = false;
			}

			Map attachments = manager.getClaimAttachments(claimRefNumber,
					clmApplicationStatus, internetUser);

			if (attachments.get("recallNotice") != null) {
				UploadFileProperties uploadFile = (UploadFileProperties) attachments
						.get("recallNotice");

				String formattedToOSPath = createNewFile(request,
						uploadFile.getFileName(), uploadFile.getFileSize());

				Log.log(5, "ReportsAction", "createNewFile",
						" Recall Notice Attachment path " + formattedToOSPath);

				request.setAttribute("recallNoticeAttachment",
						formattedToOSPath);
			}

			if (attachments.get("legalDetails") != null) {
				UploadFileProperties uploadFile = (UploadFileProperties) attachments
						.get("legalDetails");

				String formattedToOSPath = createNewFile(request,
						uploadFile.getFileName(), uploadFile.getFileSize());

				Log.log(5, "ReportsAction", "createNewFile",
						" Legal Details Attachment path " + formattedToOSPath);

				request.setAttribute("legalDetailsAttachment",
						formattedToOSPath);
			}

			return mapping.findForward("showFirstClmDetails");
		}
		if (claimapplication.getSecondInstallment()) {
			claimForm.setClaimapplication(claimapplication);

			String bid = claimapplication.getBorrowerId();
			String memId = claimapplication.getMemberId();
			Vector cgpnDetails = processor.getCGPANDetailsForBorrowerId(bid,
					memId);
			Vector clmAppliedAmnts = processor.getClaimAppliedAmounts(bid, "F");
			Vector updateClmDtls = new Vector();
			String thiscgpn = null;

			for (int i = 0; i < cgpnDetails.size(); i++) {
				HashMap dtl = (HashMap) cgpnDetails.elementAt(i);

				if (dtl != null) {
					thiscgpn = (String) dtl.get("CGPAN");
					if (thiscgpn != null) {
						for (int j = 0; j < clmAppliedAmnts.size(); j++) {
							HashMap clmAppliedDtl = (HashMap) clmAppliedAmnts
									.elementAt(j);
							String cgpnInAppliedAmntsVec = null;
							if (clmAppliedDtl != null) {
								cgpnInAppliedAmntsVec = (String) clmAppliedDtl
										.get("CGPAN");
								if (cgpnInAppliedAmntsVec != null) {
									if (cgpnInAppliedAmntsVec.equals(thiscgpn)) {
										double clmAppliedAmnt = 0.0D;
										Double clmAppAmntObj = (Double) clmAppliedDtl
												.get("ClaimAppliedAmnt");
										if (clmAppAmntObj != null) {
											clmAppliedAmnt = clmAppAmntObj
													.doubleValue();
										} else {
											clmAppliedAmnt = 0.0D;
										}

										dtl.put("ClaimAppliedAmnt", new Double(
												clmAppliedAmnt));
										if (!updateClmDtls.contains(dtl)) {
											updateClmDtls.addElement(dtl);
										}

										clmAppliedDtl = null;
										break;
									}

								}

							} else
								;
						}

					} else {
						continue;
					}
				} else {
					continue;
				}

				dtl = null;
			}

			HashMap settlmntDetails = processor
					.getClaimSettlementDetailForBorrower(bid);
			double firstSettlementAmnt = 0.0D;
			Double firstSettlementAmntObj = (Double) settlmntDetails
					.get("FirstSettlmntAmnt");
			if (firstSettlementAmntObj != null) {
				firstSettlementAmnt = firstSettlementAmntObj.doubleValue();
			}
			java.util.Date firstSettlementDt = (java.util.Date) settlmntDetails
					.get("FirstSettlmntDt");

			HashMap dtl = null;
			Vector finalUpdatedDtls = new Vector();

			for (int i = 0; i < updateClmDtls.size(); i++) {
				dtl = (HashMap) updateClmDtls.elementAt(i);

				if (dtl != null) {
					dtl.put("FirstSettlmntAmnt",
							new Double(firstSettlementAmnt));
					dtl.put("FirstSettlmntDt", firstSettlementDt);
					if (!finalUpdatedDtls.contains(dtl)) {
						finalUpdatedDtls.addElement(dtl);
					}

					dtl = null;
				}
			}
			ArrayList clmSummaryDtls = claimapplication.getClaimSummaryDtls();
			for (int j = 0; j < clmSummaryDtls.size(); j++) {
				ClaimSummaryDtls dtls = (ClaimSummaryDtls) clmSummaryDtls
						.get(j);
				String cgpan = null;
				double clmappliedamnt = 0.0D;
				if (dtls != null) {
					cgpan = dtls.getCgpan();
					clmappliedamnt = dtls.getAmount();
				}
				for (int i = 0; i < updateClmDtls.size(); i++) {
					dtl = (HashMap) updateClmDtls.elementAt(i);

					if (dtl != null) {
						String pan = (String) dtl.get("CGPAN");
						if (pan.equals(cgpan)) {
							dtl = (HashMap) updateClmDtls.remove(i);
							dtl.put("FirstSettlmntAmnt", new Double(
									firstSettlementAmnt));
							dtl.put("FirstSettlmntDt", firstSettlementDt);
							dtl.put("SECClaimAppliedAmnt", new Double(
									clmappliedamnt));
						}

						dtl = null;
					}
				}
			}

			claimForm.setUpdatedClaimDtls(finalUpdatedDtls);
			boolean internetUser = true;

			if (((userInfo.getBankId() + userInfo.getZoneId() + userInfo
					.getBranchId()).equals("000000000000"))
					&& (!userInfo.getUserId().equalsIgnoreCase("DEMOUSER"))) {
				internetUser = false;
			}

			Map attachments = manager.getClaimAttachments(claimRefNumber,
					clmApplicationStatus, internetUser);

			if (attachments.get("recallNotice") != null) {
				UploadFileProperties uploadFile = (UploadFileProperties) attachments
						.get("recallNotice");

				String formattedToOSPath = createNewFile(request,
						uploadFile.getFileName(), uploadFile.getFileSize());

				Log.log(5, "ReportsAction", "createNewFile",
						" Recall Notice Attachment path " + formattedToOSPath);

				request.setAttribute("recallNoticeAttachment",
						formattedToOSPath);
			}

			if (attachments.get("legalDetails") != null) {
				UploadFileProperties uploadFile = (UploadFileProperties) attachments
						.get("legalDetails");

				String formattedToOSPath = createNewFile(request,
						uploadFile.getFileName(), uploadFile.getFileSize());

				Log.log(5, "ReportsAction", "createNewFile",
						" Legal Details Attachment path " + formattedToOSPath);

				request.setAttribute("legalDetailsAttachment",
						formattedToOSPath);
			}

			return mapping.findForward("showSecClmDetails");
		}
		Log.log(4, "ReportsAction", "displayClmApplicationDtlNewRev", "Exited");

		return null;
	}

	public ActionForward displayClmApplicationDtlModified(
			ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		Log.log(4, "ReportsAction", "displayClmApplicationDtlModified",
				"Entered");
		ReportManager manager = new ReportManager();
		ClaimActionForm claimForm = (ClaimActionForm) form;

		String clmApplicationStatus = claimForm.getClmApplicationStatus();
		clmApplicationStatus = "NE";
		Log.log(4, "ReportsAction", "displayClmApplicationDtlNew",
				"Claim Application Status being queried :"
						+ clmApplicationStatus);

		User user = getUserInformation(request);
		String bankid = user.getBankId().trim();
		String zoneid = user.getZoneId().trim();
		String branchid = user.getBranchId().trim();
		String memberId = bankid + zoneid + branchid;

		request.setAttribute("CLAIMREFNO",
				request.getParameter("ClaimRefNumber"));

		ClaimsProcessor processor = new ClaimsProcessor();
		String claimRefNumber = request.getParameter("ClaimRefNumber");

		ClaimApplication claimapplication = manager.displayClmRefNumberDtl(
				claimRefNumber, clmApplicationStatus, memberId);
		ArrayList clmSummryDtls = claimapplication.getClaimSummaryDtls();

		User userInfo = getUserInformation(request);
		if (claimapplication.getFirstInstallment()) {
			String thiscgpn = null;
			String bid = claimapplication.getBorrowerId();

			String memId = claimapplication.getMemberId();

			// Vector cgpnDetails = processor.getCGPANDetailsForBorrowerId(bid,
			// memId);

			claimapplication.setClaimSummaryDtls(clmSummryDtls);
			claimForm.setClaimapplication(claimapplication);

			boolean internetUser = true;

			if (((userInfo.getBankId() + userInfo.getZoneId() + userInfo
					.getBranchId()).equals("000000000000"))
					&& (!userInfo.getUserId().equalsIgnoreCase("DEMOUSER"))) {
				internetUser = false;
			}

			Map attachments = manager.getClaimAttachments(claimRefNumber,
					clmApplicationStatus, internetUser);

			if (attachments.get("recallNotice") != null) {
				UploadFileProperties uploadFile = (UploadFileProperties) attachments
						.get("recallNotice");

				String formattedToOSPath = createNewFile(request,
						uploadFile.getFileName(), uploadFile.getFileSize());

				Log.log(5, "ReportsAction", "createNewFile",
						" Recall Notice Attachment path " + formattedToOSPath);

				request.setAttribute("recallNoticeAttachment",
						formattedToOSPath);
			}

			if (attachments.get("legalDetails") != null) {
				UploadFileProperties uploadFile = (UploadFileProperties) attachments
						.get("legalDetails");

				String formattedToOSPath = createNewFile(request,
						uploadFile.getFileName(), uploadFile.getFileSize());

				Log.log(5, "ReportsAction", "createNewFile",
						" Legal Details Attachment path " + formattedToOSPath);

				request.setAttribute("legalDetailsAttachment",
						formattedToOSPath);
			}

			return mapping.findForward("showFirstClmDetails");
		}
		if (claimapplication.getSecondInstallment()) {
			claimForm.setClaimapplication(claimapplication);

			String bid = claimapplication.getBorrowerId();
			String memId = claimapplication.getMemberId();
			Vector cgpnDetails = processor.getCGPANDetailsForBorrowerId(bid,
					memId);
			Vector clmAppliedAmnts = processor.getClaimAppliedAmounts(bid, "F");
			Vector updateClmDtls = new Vector();
			String thiscgpn = null;

			for (int i = 0; i < cgpnDetails.size(); i++) {
				HashMap dtl = (HashMap) cgpnDetails.elementAt(i);

				if (dtl != null) {
					thiscgpn = (String) dtl.get("CGPAN");
					if (thiscgpn != null) {
						for (int j = 0; j < clmAppliedAmnts.size(); j++) {
							HashMap clmAppliedDtl = (HashMap) clmAppliedAmnts
									.elementAt(j);
							String cgpnInAppliedAmntsVec = null;
							if (clmAppliedDtl != null) {
								cgpnInAppliedAmntsVec = (String) clmAppliedDtl
										.get("CGPAN");
								if (cgpnInAppliedAmntsVec != null) {
									if (cgpnInAppliedAmntsVec.equals(thiscgpn)) {
										double clmAppliedAmnt = 0.0D;
										Double clmAppAmntObj = (Double) clmAppliedDtl
												.get("ClaimAppliedAmnt");
										if (clmAppAmntObj != null) {
											clmAppliedAmnt = clmAppAmntObj
													.doubleValue();
										} else {
											clmAppliedAmnt = 0.0D;
										}

										dtl.put("ClaimAppliedAmnt", new Double(
												clmAppliedAmnt));
										if (!updateClmDtls.contains(dtl)) {
											updateClmDtls.addElement(dtl);
										}

										clmAppliedDtl = null;
										break;
									}

								}

							} else
								;
						}

					} else {
						continue;
					}
				} else {
					continue;
				}

				dtl = null;
			}

			HashMap settlmntDetails = processor
					.getClaimSettlementDetailForBorrower(bid);
			double firstSettlementAmnt = 0.0D;
			Double firstSettlementAmntObj = (Double) settlmntDetails
					.get("FirstSettlmntAmnt");
			if (firstSettlementAmntObj != null) {
				firstSettlementAmnt = firstSettlementAmntObj.doubleValue();
			}
			java.util.Date firstSettlementDt = (java.util.Date) settlmntDetails
					.get("FirstSettlmntDt");

			HashMap dtl = null;
			Vector finalUpdatedDtls = new Vector();

			for (int i = 0; i < updateClmDtls.size(); i++) {
				dtl = (HashMap) updateClmDtls.elementAt(i);

				if (dtl != null) {
					dtl.put("FirstSettlmntAmnt",
							new Double(firstSettlementAmnt));
					dtl.put("FirstSettlmntDt", firstSettlementDt);
					if (!finalUpdatedDtls.contains(dtl)) {
						finalUpdatedDtls.addElement(dtl);
					}

					dtl = null;
				}
			}
			ArrayList clmSummaryDtls = claimapplication.getClaimSummaryDtls();
			for (int j = 0; j < clmSummaryDtls.size(); j++) {
				ClaimSummaryDtls dtls = (ClaimSummaryDtls) clmSummaryDtls
						.get(j);
				String cgpan = null;
				double clmappliedamnt = 0.0D;
				if (dtls != null) {
					cgpan = dtls.getCgpan();
					clmappliedamnt = dtls.getAmount();
				}
				for (int i = 0; i < updateClmDtls.size(); i++) {
					dtl = (HashMap) updateClmDtls.elementAt(i);

					if (dtl != null) {
						String pan = (String) dtl.get("CGPAN");
						if (pan.equals(cgpan)) {
							dtl = (HashMap) updateClmDtls.remove(i);
							dtl.put("FirstSettlmntAmnt", new Double(
									firstSettlementAmnt));
							dtl.put("FirstSettlmntDt", firstSettlementDt);
							dtl.put("SECClaimAppliedAmnt", new Double(
									clmappliedamnt));
						}

						dtl = null;
					}
				}
			}

			claimForm.setUpdatedClaimDtls(finalUpdatedDtls);
			boolean internetUser = true;

			if (((userInfo.getBankId() + userInfo.getZoneId() + userInfo
					.getBranchId()).equals("000000000000"))
					&& (!userInfo.getUserId().equalsIgnoreCase("DEMOUSER"))) {
				internetUser = false;
			}

			Map attachments = manager.getClaimAttachments(claimRefNumber,
					clmApplicationStatus, internetUser);

			if (attachments.get("recallNotice") != null) {
				UploadFileProperties uploadFile = (UploadFileProperties) attachments
						.get("recallNotice");

				String formattedToOSPath = createNewFile(request,
						uploadFile.getFileName(), uploadFile.getFileSize());

				Log.log(5, "ReportsAction", "createNewFile",
						" Recall Notice Attachment path " + formattedToOSPath);

				request.setAttribute("recallNoticeAttachment",
						formattedToOSPath);
			}

			if (attachments.get("legalDetails") != null) {
				UploadFileProperties uploadFile = (UploadFileProperties) attachments
						.get("legalDetails");

				String formattedToOSPath = createNewFile(request,
						uploadFile.getFileName(), uploadFile.getFileSize());

				Log.log(5, "ReportsAction", "createNewFile",
						" Legal Details Attachment path " + formattedToOSPath);

				request.setAttribute("legalDetailsAttachment",
						formattedToOSPath);
			}

			return mapping.findForward("showSecClmDetails");
		}
		Log.log(4, "ReportsAction", "displayClmApplicationDtlModified",
				"Exited");

		return null;
	}

	public ActionForward showFilterForSettlementReport(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		ClaimActionForm claimForm = (ClaimActionForm) form;
		java.util.Date date = new java.util.Date();
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(date);
		int month = calendar.get(2);
		int day = calendar.get(5);
		month -= 1;
		day += 1;
		calendar.set(2, month);
		calendar.set(5, day);
		java.util.Date prevDate = calendar.getTime();
		CustomisedDate customDate = new CustomisedDate();
		customDate.setDate(prevDate);

		CustomisedDate customToDate = new CustomisedDate();
		customToDate.setDate(date);

		claimForm.setFromDate(customDate);
		claimForm.setToDate(customToDate);

		return mapping.findForward("showFilter");
	}

	public ActionForward declarationReceivedReport(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		Log.log(4, "ReportsAction", "declarationReceivedReport", "Entered");

		DynaActionForm dynaForm = (DynaActionForm) form;
		java.util.Date date = new java.util.Date();
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(date);
		int month = calendar.get(2);
		int day = calendar.get(5);
		month -= 1;
		day += 1;
		calendar.set(2, month);
		calendar.set(5, day);
		java.util.Date prevDate = calendar.getTime();

		GeneralReport generalReport = new GeneralReport();
		generalReport.setDateOfTheDocument26(prevDate);
		generalReport.setDateOfTheDocument27(date);
		dynaForm.set("memberId", "");
		dynaForm.set("ssi", "");
		BeanUtils.copyProperties(dynaForm, generalReport);
		User user = getUserInformation(request);
		String bankId = user.getBankId();
		String zoneId = user.getZoneId();
		String branchId = user.getBranchId();
		String memberId = bankId.concat(zoneId).concat(branchId);

		dynaForm.set("bankId", bankId);

		if (bankId.equals("0000")) {
			memberId = "";
		}
		dynaForm.set("memberId", memberId);

		BeanUtils.copyProperties(dynaForm, generalReport);

		Log.log(4, "ReportsAction", "declarationReceivedReport", "Exited");

		return mapping.findForward("showFilter");
	}

	public ActionForward declarationReceivedReportDetails(
			ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		Log.log(4, "ReportsAction", "declarationReceivedReportDetails",
				"Entered");
		ArrayList declarationReceivedCases = new ArrayList();
		DynaActionForm dynaForm = (DynaActionForm) form;
		java.sql.Date startDate = null;
		java.sql.Date endDate = null;
		java.util.Date sDate = (java.util.Date) dynaForm
				.get("dateOfTheDocument26");
		String stDate = String.valueOf(sDate);

		if ((stDate == null) || (stDate.equals("")))
			startDate = null;
		else if (stDate != null) {
			startDate = new java.sql.Date(sDate.getTime());
		}
		java.util.Date eDate = (java.util.Date) dynaForm
				.get("dateOfTheDocument27");
		endDate = new java.sql.Date(eDate.getTime());

		String id = (String) dynaForm.get("memberId");

		String ssi = (String) dynaForm.get("ssi");

		ClaimsProcessor processor = new ClaimsProcessor();
		Vector memberids = processor.getAllMemberIds();
		if (((id == null) || (id.equals("")))
				&& ((ssi == null) || (ssi.equals("")))) {
			declarationReceivedCases = this.reportManager
					.declarationReceivedCases(startDate, endDate);
			dynaForm.set("danRaised", declarationReceivedCases);
		} else if (((ssi == null) || (ssi.equals(""))) && (id != null)) {
			if (!memberids.contains(id)) {
				throw new NoMemberFoundException("Member Id :" + id
						+ " does not exist in the database.");
			}

			declarationReceivedCases = this.reportManager
					.declarationReceivedCasesNew(startDate, endDate, id, ssi);
			dynaForm.set("danRaised", declarationReceivedCases);
		} else {
			declarationReceivedCases = this.reportManager
					.declarationReceivedCasesNew(startDate, endDate, id, ssi);
			dynaForm.set("danRaised", declarationReceivedCases);
		}

		if ((declarationReceivedCases == null)
				|| (declarationReceivedCases.size() == 0)) {
			throw new NoDataException(
					"No Data is available for the values entered, Please Enter Any Other Value ");
		}

		declarationReceivedCases = null;
		Log.log(4, "ReportsAction", "declarationReceivedReportDetails",
				"Exited");

		return mapping.findForward("success");
	}

	public ActionForward displayMemberSettlementDtls(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		Log.log(4, "ReportsAction", "displayMemberSettlementDtls", "Entered");
		ClaimActionForm claimForm = (ClaimActionForm) form;
		java.util.Date fromDate = claimForm.getFromDate();
		java.util.Date toDate = claimForm.getToDate();
		Vector settlementDetails = null;

		ReportManager manager = new ReportManager();
		java.sql.Date sqlFromDate = null;

		if (fromDate.toString().equals("")) {
			settlementDetails = manager.displayMemberSettlementDtls(null,
					new java.sql.Date(toDate.getTime()));
		} else {
			sqlFromDate = new java.sql.Date(fromDate.getTime());
			settlementDetails = manager.displayMemberSettlementDtls(
					sqlFromDate, new java.sql.Date(toDate.getTime()));
		}
		claimForm.setSettledClms(settlementDetails);
		Log.log(4, "ReportsAction", "displayMemberSettlementDtls", "Exited");

		return mapping.findForward("showMemberDetails");
	}

	public ActionForward displaySettlementDetails(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		Log.log(4, "ReportsAction", "displaySettlementDetails", "Entered");
		ClaimActionForm claimForm = (ClaimActionForm) form;

		java.util.Date fromDate = claimForm.getFromDate();
		java.util.Date toDate = claimForm.getToDate();
		String memberId = request.getParameter("MEMBERID");
		ReportManager manager = new ReportManager();
		Vector firstClaimSettlementDetails = null;
		Vector secClaimSettlementDetails = null;
		java.sql.Date sqlFromDate = null;
		request.setAttribute("MEMBERID", request.getParameter("MEMBERID"));
		if (fromDate.toString().equals("")) {
			firstClaimSettlementDetails = manager.getSettlementDetails(null,
					new java.sql.Date(toDate.getTime()), memberId, "F");
			secClaimSettlementDetails = manager.getSettlementDetails(null,
					new java.sql.Date(toDate.getTime()), memberId, "S");
		} else {
			sqlFromDate = new java.sql.Date(fromDate.getTime());
			firstClaimSettlementDetails = manager.getSettlementDetails(
					sqlFromDate, new java.sql.Date(toDate.getTime()), memberId,
					"F");
			secClaimSettlementDetails = manager.getSettlementDetails(
					sqlFromDate, new java.sql.Date(toDate.getTime()), memberId,
					"S");
		}
		if ((firstClaimSettlementDetails.size() == 0)
				&& (secClaimSettlementDetails.size() == 0)) {
			request.setAttribute("message",
					"There are no Settlement Details that match the query..");

			return mapping.findForward("success");
		}
		claimForm.setSettlementsOfFirstClaim(firstClaimSettlementDetails);
		claimForm.setSettlementsOfSecondClaim(secClaimSettlementDetails);
		Log.log(4, "ReportsAction", "displaySettlementDetails", "Exited");

		return mapping.findForward("showDetails");
	}

	public ActionForward queryBuilderResult(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		Log.log(4, "ReportsAction", "queryBuilderResult", "Entered");
		ReportActionForm reportForm = (ReportActionForm) form;
		QueryBuilderFields qb = reportForm.getQueryBuilderFields();

		ArrayList report = this.reportManager.getQueryReport(qb);
		if ((report == null) || (report.size() == 0)) {
			throw new NoDataException(
					"No Data is available for the values entered, Please Enter Any Other Value ");
		}

		reportForm.setQueryReport(report);
		Log.log(4, "ReportsAction", "queryBuilderResult", "Exited");

		return mapping.findForward("success");
	}

	public ActionForward defaulterReport(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		Log.log(4, "ReportsAction", "defaulterReport", "Entered");

		ArrayList report = null;

		DefaulterInputFields di = new DefaulterInputFields();
		DynaActionForm dynaForm = (DynaActionForm) form;

		BeanUtils.populate(di, dynaForm.getMap());
		report = this.reportManager.getDefaulterReport(di);
		dynaForm.set("default", report);

		if ((report == null) || (report.size() == 0)) {
			throw new NoDataException(
					"No Data is availabale for the data entered,Please Enter Any Other value");
		}

		Log.log(4, "ReportsAction", "defaulterReport", "Exited");

		return mapping.findForward("success");
	}

	public ActionForward queryBuilderInput(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		Log.log(4, "ReportsAction", "queryBuilderInput", "Entered");
		HttpSession session = request.getSession(false);
		Object reportForm = session.getAttribute("reportForm");
		if (reportForm != null) {
			session.removeAttribute("reportForm");
		}

		Log.log(4, "ReportsAction", "queryBuilderInput", "Exited. mapping : "
				+ mapping.findForward("success"));

		return mapping.findForward("success");
	}

	public ActionForward queryBuilderSelection(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		Log.log(4, "ReportsAction", "queryBuilderSelection", "Entered");
		ReportActionForm reportForm = (ReportActionForm) form;
		QueryBuilderFields qb = reportForm.getQueryBuilderFields();

		Log.log(4, "ReportsAction", "queryBuilderSelection",
				"Exited. mapping : " + mapping.findForward("success"));

		return mapping.findForward("success");
	}

	public ActionForward queryBuilderCancel(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		Log.log(4, "ReportsAction", "queryBuilderCancel", "Entered");
		HttpSession session = request.getSession(false);
		session.removeAttribute("reportForm");
		Log.log(4, "ReportsAction", "queryBuilderCancel", "Exited");

		return mapping.findForward("success");
	}

	public ActionForward showFilterForPIReport(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		Log.log(4, "ReportsAction", "showFilterForPIReport", "Entered");

		ReportActionForm rpActionForm = (ReportActionForm) form;

		User user = getUserInformation(request);
		String bankId = user.getBankId();
		String zoneId = user.getZoneId();
		String branchId = user.getBranchId();
		String memberId = bankId.concat(zoneId).concat(branchId);

		Log.log(5, "ReportsAction", "showFilterForPIReport", "memberId "
				+ memberId);
		GMProcessor gmProcessor = new GMProcessor();

		String forwardPage = "";

		if (bankId.equals("0000")) {
			rpActionForm.setMemberId("");
			forwardPage = "success1";
		} else {
			ArrayList borrowerIds = gmProcessor.getBorrowerIds(memberId);
			int sizeOfList = borrowerIds.size();
			Log.log(5, "ReportsAction", "showFilterForPIReport",
					"borrowerIds.size " + sizeOfList);

			String borrowerName = null;

			ClaimsProcessor cl = new ClaimsProcessor();

			ArrayList borrInfos = new ArrayList();

			for (int i = 0; i < sizeOfList; i++) {
				com.cgtsi.guaranteemaintenance.BorrowerInfo bInfoGm = new com.cgtsi.guaranteemaintenance.BorrowerInfo();

				String borrowerId = (String) borrowerIds.get(i);
				Log.log(5, "ReportsAction", "showFilterForPIReport",
						"borrowerId " + borrowerId);
				com.cgtsi.claim.BorrowerInfo bInfoCl = cl
						.getBorrowerDetails(borrowerId);

				borrowerName = bInfoCl.getBorrowerName();
				Log.log(5, "ReportsAction", "showFilterForPIReport",
						"borrowerName " + borrowerName);

				Vector cgpans = gmProcessor.getCGPANs(borrowerId);
				Log.log(5, "ReportsAction", "showFilterForPIReport",
						"cgpans  size " + cgpans.size());

				ArrayList cgpanInfos = new ArrayList();
				String cgpan = null;

				if ((cgpans != null) && (cgpans.size() != 0)) {
					for (int j = 0; j < cgpans.size(); j++) {
						HashMap cgpanMap = (HashMap) cgpans.get(j);
						cgpan = (String) cgpanMap.get("CGPAN");
						Log.log(5, "ReportsAction", "showFilterForPIReport",
								"cgpan " + cgpan);
						CgpanInfo cgpanInfo = new CgpanInfo();
						cgpanInfo.setCgpan(cgpan);
						cgpanInfos.add(cgpanInfo);
					}
					bInfoGm.setBorrowerId(borrowerId);
					bInfoGm.setBorrowerName(borrowerName);
					bInfoGm.setCgpanInfos(cgpanInfos);

					borrInfos.add(bInfoGm);
				}

			}

			Log.log(5, "ReportsAction", "showFilterForPIReport",
					"borrInfos size " + borrInfos.size());
			rpActionForm.setBorrowerDetailsForPIReport(borrInfos);

			borrInfos = null;
			borrowerIds = null;

			forwardPage = "success";
		}

		Log.log(4, "ReportsAction", "showFilterForPIReport", "Exited");

		return mapping.findForward(forwardPage);
	}

	public ActionForward afterFilterForPIReport(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		Log.log(4, "ReportsAction", "showFilterForPIReport", "Entered");

		ReportActionForm rpActionForm = (ReportActionForm) form;

		GMProcessor gmProcessor = new GMProcessor();
		Registration registration = new Registration();

		String memberId = rpActionForm.getMemberId();

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
			Log.log(5, "ReportsAction", "afterFilterForPIReport",
					"mli Info.. :" + mliInfo);

			String statusFlag = mliInfo.getStatus();
			if (statusFlag.equals("I")) {
				throw new NoDataException("Member Id:" + memberId
						+ "  has been deactivated.");
			}

		}

		ArrayList borrowerIds = gmProcessor.getBorrowerIds(memberId);
		int sizeOfList = borrowerIds.size();
		Log.log(5, "ReportsAction", "afterFilterForPIReport",
				"borrowerIds.size " + sizeOfList);

		String borrowerName = null;

		ClaimsProcessor cl = new ClaimsProcessor();

		ArrayList borrInfos = new ArrayList();

		for (int i = 0; i < sizeOfList; i++) {
			com.cgtsi.guaranteemaintenance.BorrowerInfo bInfoGm = new com.cgtsi.guaranteemaintenance.BorrowerInfo();

			String borrowerId = (String) borrowerIds.get(i);
			Log.log(5, "ReportsAction", "afterFilterForPIReport", "borrowerId "
					+ borrowerId);
			com.cgtsi.claim.BorrowerInfo bInfoCl = cl
					.getBorrowerDetails(borrowerId);

			borrowerName = bInfoCl.getBorrowerName();
			Log.log(5, "ReportsAction", "afterFilterForPIReport",
					"borrowerName " + borrowerName);

			Vector cgpans = gmProcessor.getCGPANs(borrowerId);
			Log.log(5, "ReportsAction", "afterFilterForPIReport",
					"cgpans  size " + cgpans.size());

			ArrayList cgpanInfos = new ArrayList();
			String cgpan = null;

			if ((cgpans != null) && (cgpans.size() != 0)) {
				for (int j = 0; j < cgpans.size(); j++) {
					HashMap cgpanMap = (HashMap) cgpans.get(j);
					cgpan = (String) cgpanMap.get("CGPAN");
					Log.log(5, "ReportsAction", "afterFilterForPIReport",
							"cgpan " + cgpan);
					CgpanInfo cgpanInfo = new CgpanInfo();
					cgpanInfo.setCgpan(cgpan);
					cgpanInfos.add(cgpanInfo);
				}
				bInfoGm.setBorrowerId(borrowerId);
				bInfoGm.setBorrowerName(borrowerName);
				bInfoGm.setCgpanInfos(cgpanInfos);

				borrInfos.add(bInfoGm);
			}

		}

		Log.log(5, "ReportsAction", "afterFilterForPIReport", "borrInfos size "
				+ borrInfos.size());
		rpActionForm.setBorrowerDetailsForPIReport(borrInfos);

		borrInfos = null;
		borrowerIds = null;

		return mapping.findForward("success");
	}

	public ActionForward showPeriodicInfoReport(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		Log.log(4, "ReportsAction", "showRepaymentReport", "Entered");

		ReportActionForm rpActionForm = (ReportActionForm) form;

		HttpSession reportSession = request.getSession(false);

		GMProcessor gmProcessor = new GMProcessor();

		ArrayList repayPeriodicInfoDetailsTemp = null;

		ArrayList osPeriodicInfoDetails = null;

		ArrayList disbPeriodicInfoDetailsTemp = null;

		NPADetails npaDetails = null;
		ArrayList recoveryDetails = null;

		String borrowerId = request.getParameter("bidForPIReport");
		Log.log(5, "ReportsAction", "showRepaymentReport", "borrowerId "
				+ borrowerId);
		rpActionForm.setBorrowerId(borrowerId);

		repayPeriodicInfoDetailsTemp = gmProcessor
				.viewRepaymentDetailsForReport(borrowerId, 0);

		reportSession.removeAttribute("reportFlag");
		reportSession.removeAttribute("outFlag");
		reportSession.removeAttribute("disFlag");
		reportSession.removeAttribute("recFlag");

		for (int i = 0; i < repayPeriodicInfoDetailsTemp.size(); i++) {
			PeriodicInfo pfTemp = (PeriodicInfo) repayPeriodicInfoDetailsTemp
					.get(i);
			ArrayList repaymentDtlTemp = pfTemp.getRepaymentDetails();
			for (int j = 0; j < repaymentDtlTemp.size(); j++) {
				Repayment rDtlTemp = (Repayment) repaymentDtlTemp.get(j);
				ArrayList repaymentAmtTemp = rDtlTemp.getRepaymentAmounts();
				for (int k = 0; k < repaymentAmtTemp.size(); k++) {
					RepaymentAmount rpAmtTemp = (RepaymentAmount) repaymentAmtTemp
							.get(k);
					if (rpAmtTemp != null) {
						rpActionForm
								.setRepayPeriodicInfoDetails(repayPeriodicInfoDetailsTemp);
					}
				}
				if ((repaymentAmtTemp != null) && (repaymentAmtTemp.size() > 0)) {
					reportSession.setAttribute("reportFlag", "Yes");
				} else {
					reportSession.setAttribute("reportFlag", "No");
				}
			}
		}

		osPeriodicInfoDetails = gmProcessor.viewOutstandingDetailsForReport(
				borrowerId, 0);

		for (int i = 0; i < osPeriodicInfoDetails.size(); i++) {
			PeriodicInfo pfTemp = (PeriodicInfo) osPeriodicInfoDetails.get(i);
			ArrayList outDtlTemp = pfTemp.getOutstandingDetails();
			for (int j = 0; j < outDtlTemp.size(); j++) {
				OutstandingDetail outStDtlTemp = (OutstandingDetail) outDtlTemp
						.get(j);
				ArrayList outAmtTemp = outStDtlTemp.getOutstandingAmounts();
				for (int k = 0; k < outAmtTemp.size(); k++) {
					OutstandingAmount outstAmtTemp = (OutstandingAmount) outAmtTemp
							.get(k);
					if (outstAmtTemp != null) {
						rpActionForm
								.setOsPeriodicInfoDetails(osPeriodicInfoDetails);
					}
				}
				if ((outAmtTemp != null) && (outAmtTemp.size() > 0)) {
					reportSession.setAttribute("outFlag", "Yes");
				} else {
					reportSession.setAttribute("outFlag", "No");
				}
			}

		}

		disbPeriodicInfoDetailsTemp = gmProcessor
				.viewDisbursementDetailsForReport(borrowerId, 0);

		PeriodicInfo pfTemp = (PeriodicInfo) disbPeriodicInfoDetailsTemp.get(0);
		ArrayList disburseDtlTemp = pfTemp.getDisbursementDetails();
		for (int j = 0; j < disburseDtlTemp.size(); j++) {
			Disbursement rDtlTemp = (Disbursement) disburseDtlTemp.get(j);
			ArrayList disburseAmtTemp = rDtlTemp.getDisbursementAmounts();
			for (int k = 0; k < disburseAmtTemp.size(); k++) {
				DisbursementAmount rpAmtTemp = (DisbursementAmount) disburseAmtTemp
						.get(k);
				if (rpAmtTemp != null) {
					rpActionForm
							.setDisbPeriodicInfoDetails(disbPeriodicInfoDetailsTemp);
				}
			}
			if ((disburseAmtTemp != null) && (disburseAmtTemp.size() > 0)) {
				reportSession.setAttribute("disFlag", "Yes");
			} else {
				reportSession.setAttribute("disFlag", "No");
			}

		}

		npaDetails = gmProcessor.getNPADetailsForReport(borrowerId);
		HttpSession session = request.getSession(false);

		session.setAttribute("isNPAAvailable", "Yes");
		if (npaDetails == null) {
			session.setAttribute("isNPAAvailable", "No");
		}

		recoveryDetails = gmProcessor.getRecoveryDetailsForReport(borrowerId);
		if ((recoveryDetails != null) && (recoveryDetails.size() > 0)) {
			reportSession.setAttribute("recFlag", "Yes");
		} else {
			reportSession.setAttribute("recFlag", "No");
		}

		rpActionForm.setNpaDetails(npaDetails);
		rpActionForm.setRecoveryDetails(recoveryDetails);

		Log.log(4, "ReportsAction", "showRepaymentReport", "Exited");

		return mapping.findForward("success");
	}

	private String createNewFile(HttpServletRequest request, String fileName,
			byte[] data) {
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

	public ActionForward getOtsReport(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		//System.out.println("<-=-----Inside Method getOTSRerport Report------>");
		Log.log(4, "ReportsAction", "getOTSRerport", "Entered");

		ClaimActionForm clmActionform = (ClaimActionForm) form;
		clmActionform.setCp_ots_enterMember("");
		clmActionform.setCp_ots_unitName("");

		Log.log(4, "ReportsAction", "getOTSRerport", "Exited");

		return mapping.findForward("success");
	}

	public ActionForward getOtsReportDetails(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		ClaimActionForm claimform = (ClaimActionForm) form;
		try {
			Connection connection = DBConnection.getConnection();
			Statement str = connection.createStatement(1005, 1007);
			ArrayList claimformdata = new ArrayList();
			ClaimActionForm claimFormmainObj = new ClaimActionForm();
			java.util.Date fromdate = null;
			java.util.Date todate = null;
			String memberId = "";

			String appRovDate = "";
			String mem_condition = "";
			String unit_like = "";
			String forUnitName = "";

			String fdate = claimform.getCp_ots_fromDate();
			//System.out.println("The from Date  is :--->" + fdate);
			String tdate = claimform.getCp_ots_toDate();
			//System.out.println("The to Date is :--->" + tdate);

			String member = claimform.getCp_ots_enterMember();
			//System.out.println("The memberId is :-->" + member);

			String claimDecision = "";
			try {
				claimDecision = claimform.getClm_decision();
				//System.out.println("The selected Deciion is :-->"+ claimDecision);
				if ((claimDecision.equals("")) || (claimDecision.equals(null))) {
					throw new NoMemberFoundException(
							" Please  Select any Decision Type. ");
				}
			} catch (Exception e) {
				throw new NoMemberFoundException(
						" Please  Select any Decision Type. ");
			}

			//System.out.println("The selected Deciion is 2 :-->" + claimDecision);

			claimDecision = claimDecision.trim();

			String uniName = claimform.getCp_ots_unitName().toUpperCase();
			//System.out.println("the Inserted String is :--->" + uniName);

			java.util.Date da = Calendar.getInstance().getTime();
			java.util.Date dm = new java.util.Date();

			String fmdat = fdate;
			String tmdate = tdate;
			if (!fmdat.equals("")) {
				String frmdate = DateHelper.stringToDBDate(fmdat);
				String trmdate = DateHelper.stringToDBDate(tmdate);

				appRovDate = " AND CA.APPROVER_DATE BETWEEN '" + frmdate
						+ "' AND '" + trmdate + "'";
			} else if (fmdat.equals("")) {
				String trmdate1 = DateHelper.stringToDBDate(tmdate);
				appRovDate = " AND CA.APPROVER_DATE <= '" + trmdate1 + "'";
			} else if (tmdate.equals("")) {
				throw new NoMemberFoundException(" plz provide to date . ");
			}

			if (!member.equals("")) {
				memberId = member;
				mem_condition = " AND T.MEMBERID='" + memberId + "'";
			}
			String query = "";
			if (!uniName.equals("")) {
				forUnitName = " AND T.UNIT_NAME LIKE '" + uniName + "%'";
			} else if (uniName.equals("")) {
				forUnitName = "";
			}

			String a = "SELECT T.MEMBERID,T.CLM_REF_NO,T.CGPAN, T.UNIT_NAME,T.GURANTEE_AMT,T.FIRST_INSTALL_PAID_AMT, \nT.NET_REC_AMT,T.SEC_INSTALL_AMT,T.FINAL_PAY_AMT,CTD_TC_CLM_ELIG_AMT CLM_ELIGI  FROM OTS T,CLAIM_TC_DETAIL TC,OTS_TOTAL CA \nWHERE T.CGPAN=TC.CGPAN \nAND CA.CLM_REF_NO=T.CLM_REF_NO \nAND CA.STATUS='"
					+ claimDecision + "' \n" + "" + appRovDate + "\n" + "";

			String b = "" + mem_condition + " \n" + "";

			query = a + b;
			query = query.trim();

			String c = "" + forUnitName + " \n" + "";

			query = query + c;
			query = query.trim();

			String d = " UNION ALL \nSELECT T.MEMBERID,T.CLM_REF_NO,T.CGPAN,T.UNIT_NAME,T.GURANTEE_AMT,T.FIRST_INSTALL_PAID_AMT,T.NET_REC_AMT,T.SEC_INSTALL_AMT,T.FINAL_PAY_AMT,CWD_WC_CLM_ELIG_AMT CLM_ELIGI FROM OTS T,CLAIM_WC_DETAIL W,OTS_TOTAL CA \nWHERE W.CGPAN=T.CGPAN \nAND CA.CLM_REF_NO=T.CLM_REF_NO \nAND CA.STATUS='"
					+ claimDecision + "' \n" + "";

			query = query + d;
			query = query.trim();

			String e = "" + appRovDate + "\n" + "";

			query = query + e;
			query = query.trim();

			String f = "" + mem_condition + "\n" + "";

			query = query + f;
			query = query.trim();

			String g = "" + forUnitName + "\n" + "";

			query = query + g;
			query = query.trim();

			//System.out.println("===>" + query);

			ResultSet rsData = str.executeQuery(query);
			while (!rsData.next()) {
				throw new NoMemberFoundException(" There Is No Data . ");
			}
			rsData.beforeFirst();

			while (rsData.next()) {
				ClaimActionForm claimActionForm = new ClaimActionForm();

				String memid = rsData.getString(1);
				claimActionForm.setCp_ots_enterMember(memid);
				//System.out.println("The Member Id is ==>" + memid);
				String clmrefno = rsData.getString(2);
				claimActionForm.setCp_ots_appRefNo(clmrefno);
				//System.out.println("The Claim Ref Number is ==>" + clmrefno);
				String cgpan = rsData.getString(3);
				claimActionForm.setCp_ots_enterCgpan(cgpan);
				//System.out.println("The CGPAN is==>" + cgpan);
				String unitname = rsData.getString(4);
				claimActionForm.setCp_ots_unitName(unitname);
				//System.out.println("The Unit Name==>" + unitname);
				double gaurnteeamt = rsData.getDouble(5);
				claimActionForm.setCp_ots_cgpanGaurnteeAmt(gaurnteeamt);
				//System.out.println("The Gaurntee Amount is ==>" + gaurnteeamt);
				double firstisntallamt = rsData.getDouble(6);
				claimActionForm.setCp_ots_firstIntalpaidAmount(firstisntallamt);
				//System.out.println("The First Installment Amount is ==>"+ firstisntallamt);
				double netrecamt = rsData.getDouble(7);
				claimActionForm.setCp_ots_netRecovAmt(netrecamt);
				//System.out.println("The Net Recovery is ==>" + netrecamt);

				double secondinstall = rsData.getDouble(8);
				claimActionForm.setCp_ots_secIntalAMt(secondinstall);
				//System.out.println("The Second Installment Amount is ==>"+ secondinstall);

				double finalpay = rsData.getDouble(9);
				claimActionForm.setCp_ots_finalPayout(finalpay);
				System.out.println("The Final Pay Out is ==>" + finalpay);

				double clmelgiamt = rsData.getDouble(10);
				claimActionForm.setCp_ots_clmeligibleamt(clmelgiamt);
				System.out.println("The Claim Amount is ==>" + clmelgiamt);

				claimformdata.add(claimActionForm);
			}
			claimFormmainObj.setClaimformdataReport(claimformdata);

			BeanUtils.copyProperties(claimform, claimFormmainObj);
		} finally {
		}

		return mapping.findForward("xyz");
	}

	public ActionForward displayTCQueryDetail(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		Log.log(4, "ReportsAction", "displayTCQueryDetail", "Entered");
		ReportManager manager = new ReportManager();
		ClaimActionForm claimForm = (ClaimActionForm) form;
		String clmApplicationStatus = "";
		Log.log(4, "ReportsAction", "displayTCQueryDetail",
				"Claim Application Status being queried ");
		User user = getUserInformation(request);
		String bankid = user.getBankId().trim();
		String zoneid = user.getZoneId().trim();
		String branchid = user.getBranchId().trim();
		String memberId = bankid + zoneid + branchid;
		String cgpanno = null;
		String claimRefNumber = null;

		cgpanno = request.getParameter("cgpan").toUpperCase().trim();

		claimRefNumber = request.getParameter("clmRefNumber").toUpperCase()
				.trim();
		if ((cgpanno != null) && (!cgpanno.equals(""))) {
			PreparedStatement pstmt = null;
			ResultSet rst = null;
			String clmRefNo = "";
			Connection connection = DBConnection.getConnection();
			try {
				String query = "select distinct clm_ref_no,clm_status \nfrom claim_detail_temp ct,application_detail a,ssi_detail s\nwhere ct.bid = s.bid\nand s.ssi_reference_number = a.ssi_reference_number\nand a.cgpan = ?\nunion all\nselect distinct clm_ref_no,clm_status \nfrom claim_detail c,application_detail a,ssi_detail s\nwhere c.bid = s.bid\nand s.ssi_reference_number = a.ssi_reference_number\nand a.cgpan = ? ";
				pstmt = connection.prepareStatement(query);
				pstmt.setString(1, cgpanno);
				pstmt.setString(2, cgpanno);
				for (rst = pstmt.executeQuery(); rst.next();) {
					claimRefNumber = rst.getString(1);
					clmApplicationStatus = rst.getString(2);
				}

				if (clmApplicationStatus.equals(""))
					throw new DatabaseException("Enter a valid cgpan.");
				rst.close();
				rst = null;
				pstmt.close();
				pstmt = null;
			} catch (Exception exception) {
				Log.logException(exception);
				throw new DatabaseException(exception.getMessage());
			} finally {
				DBConnection.freeConnection(connection);
			}
		} else {
			claimRefNumber = request.getParameter("clmRefNumber").toUpperCase()
					.trim();
			PreparedStatement pstmt = null;
			ResultSet rst = null;
			String clmRefNo = "";
			Connection connection = DBConnection.getConnection();
			try {
				String query = "select distinct clm_status \nfrom claim_detail_temp ct,application_detail a,ssi_detail s\nwhere ct.bid = s.bid\nand s.ssi_reference_number = a.ssi_reference_number\nand ct.clm_ref_no = ?\nunion all\nselect distinct clm_status \nfrom claim_detail c,application_detail a,ssi_detail s\nwhere c.bid = s.bid\nand s.ssi_reference_number = a.ssi_reference_number\nand c.clm_ref_no = ? ";
				pstmt = connection.prepareStatement(query);
				pstmt.setString(1, claimRefNumber);
				pstmt.setString(2, claimRefNumber);
				for (rst = pstmt.executeQuery(); rst.next();) {
					clmApplicationStatus = rst.getString(1);
				}
				if (clmApplicationStatus.equals(""))
					throw new DatabaseException("Enter a valid Claim Ref No.");
				rst.close();
				rst = null;
				pstmt.close();
				pstmt = null;
			} catch (Exception exception) {
				Log.logException(exception);
				throw new DatabaseException(exception.getMessage());
			} finally {
				DBConnection.freeConnection(connection);
			}
		}
		if ((!clmApplicationStatus.equals("TC"))
				&& (!clmApplicationStatus.equals("")))
			throw new DatabaseException(
					"Please enter correct details. Claim should in Temporary Closed Status");
		Connection connection = DBConnection.getConnection(false);
		ArrayList rsfBanks = new ArrayList();
		ArrayList tcQueryList = new ArrayList();
		try {
			CallableStatement callable = connection
					.prepareCall("{?=call Packgetclmqrydetails.funcGetClmQryDetails(?,?,?,?)}");
			callable.registerOutParameter(1, 4);
			callable.setString(2, cgpanno);
			callable.setString(3, claimRefNumber);
			callable.registerOutParameter(4, -10);
			callable.registerOutParameter(5, 12);
			callable.execute();
			int errorCode = callable.getInt(1);
			String error = callable.getString(5);
			Log.log(5, "ReportAction", "displayTCQueryDetail",
					"Error code and error are " + errorCode + " " + error);
			if (errorCode == 1) {
				connection.rollback();
				callable.close();
				callable = null;
				throw new DatabaseException(error);
			}
			tcQueryList = new ArrayList();
			String[] str;
			ResultSet result = null;
			for (result = (ResultSet) callable.getObject(4); result.next(); tcQueryList
					.add(str)) {
				str = new String[19];
				str[0] = result.getString(1);
				str[1] = result.getString(2);
				str[2] = result.getString(3);
				str[3] = result.getString(4);
				str[4] = result.getString(5);
				str[5] = result.getString(6);
				str[6] = result.getString(7);
				str[7] = result.getString(8);
				str[8] = result.getString(9);
				str[9] = result.getString(10);
				str[10] = result.getString(11);
				str[11] = result.getString(12);
				str[12] = result.getString(13);
				str[13] = result.getString(14);
				str[14] = result.getString(15);
				str[15] = result.getString(16);
				str[16] = result.getString(17);
				str[17] = result.getString(18);
			}

			claimForm.setDanSummary(tcQueryList);
			result.close();
			result = null;
			callable.close();
			callable = null;
			connection.commit();
		} catch (SQLException e) {
			try {
				connection.rollback();
			} catch (SQLException ignore) {
				Log.log(2, "ReportAction", "displayTCQueryDetail",
						ignore.getMessage());
			}
			Log.log(2, "ReportAction", "displayTCQueryDetail", e.getMessage());
			Log.logException(e);
		} finally {
			DBConnection.freeConnection(connection);
		}
		request.setAttribute("danSummary", tcQueryList);

		return mapping.findForward("success");
	}

	public ActionForward tcQueryClaimDetail(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		Log.log(4, "ReportsAction", "tcQueryClaimDetail", "Entered");
		DynaActionForm dynaForm = (DynaActionForm) form;
		dynaForm.initialize(mapping);
		Log.log(4, "ReportsAction", "tcQueryClaimDetail", "Exited");

		return mapping.findForward("success");
	}

	public Vector getListOfClaimRefNumbersNew1(java.sql.Date fromDate,
			java.sql.Date toDate, String clmApplicationStatusFlag)
			throws DatabaseException {

		Log.log(Log.INFO, "ReportDAO", "getListOfClaimRefNumbersNew()",
				"Entered");
		Log.log(Log.INFO, "ReportDAO", "getListOfClaimRefNumbersNew()",
				"From Date :" + fromDate);
		Log.log(Log.INFO, "ReportDAO", "getListOfClaimRefNumbersNew()",
				"To Date :" + toDate);
		Log.log(Log.INFO, "ReportDAO", "getListOfClaimRefNumbersNew()",
				"clmApplicationStatusFlag :" + clmApplicationStatusFlag);
		Connection conn = null;
		PreparedStatement prepStatement = null;
		ResultSet rs = null;
		Vector clmRefNumbersList = new Vector();
		String query = null;

		try {
			conn = DBConnection.getConnection();

			if ((fromDate != null)
					&& (!(clmApplicationStatusFlag
							.equals(ClaimConstants.CLM_PENDING_STATUS) || clmApplicationStatusFlag
							.equals(ClaimConstants.CLM_HOLD_STATUS)))) {
				if (clmApplicationStatusFlag
						.equals(ClaimConstants.CLM_APPROVAL_STATUS)) {
					Log.log(Log.INFO, "ReportDAO",
							"getListOfClaimRefNumbers()",
							"From Date is NULL, Status is Approval");

					// new mod query kot1
					query = "SELECT m.mem_bnk_id || m.mem_zne_id || m.mem_brn_id memberid, m.mem_bank_name bankname, m.mem_zone_name,\n"
							+ "         DECODE (m.MEM_BRANCH_NAME,\n"
							+ "                 NULL, a.APP_MLI_BRANCH_NAME,\n"
							+ "                 m.MEM_BRANCH_NAME)\n"
							+ "            BRANCH,a.cgpan,S.SSI_UNIT_NAME, DECODE (a.app_reapprove_amount,\n"
							+ "                 NULL, a.APP_approved_amount,\n"
							+ "                 a.app_reapprove_amount)\n"
							+ "            GuarntAmt, c.clm_ref_no, c.cgclan,  c.CLM_DATE claimFilDt,ctd_tc_first_inst_pay_amt clmApprvdAmt,TRUNC (C.CLM_APPROVED_DT) clmApprovdDt\n"
							+ "                   FROM claim_detail c,\n"
							+ "         member_info m,\n"
							+ "         SSI_DETAIL S,\n"
							+ "         application_detail a,\n"
							+ "         claim_tc_detail ct\n"
							+ "   WHERE     TRUNC (c.clm_approved_dt) BETWEEN ? AND ?  \n"
							+ "         AND C.BID = S.BID\n"
							+ "         AND s.ssi_reference_number = a.ssi_reference_number\n"
							+ "         AND c.mem_bnk_id || c.mem_brn_id || c.mem_zne_id =\n"
							+ "                a.mem_bnk_id || a.mem_brn_id || a.mem_zne_id\n"
							+ "         AND LTRIM (\n"
							+ "                RTRIM (UPPER (c.mem_bnk_id || c.mem_zne_id || c.mem_brn_id))) =\n"
							+ "                LTRIM (\n"
							+ "                   RTRIM (UPPER (m.mem_bnk_id || m.mem_zne_id || m.mem_brn_id)))\n"
							+ "         AND c.clm_status = ? \n"
							+ "         and ct.clm_ref_no = c.clm_ref_no\n"
							+ "         and ct.cgpan = a.cgpan\n"
							+ "         and A.APP_LOAN_TYPE in ('TC')   \n"
							+ "         union all\n"
							+ "          SELECT m.mem_bnk_id || m.mem_zne_id || m.mem_brn_id memberid, m.mem_bank_name bankname, m.mem_zone_name,\n"
							+ "         DECODE (m.MEM_BRANCH_NAME,\n"
							+ "                 NULL, a.APP_MLI_BRANCH_NAME,\n"
							+ "                 m.MEM_BRANCH_NAME)\n"
							+ "            BRANCH,a.cgpan,S.SSI_UNIT_NAME, DECODE (a.app_reapprove_amount,\n"
							+ "                 NULL, a.APP_approved_amount,\n"
							+ "                 a.app_reapprove_amount)\n"
							+ "            GuarntAmt,c.clm_ref_no, c.cgclan,  c.CLM_DATE claimFilDt,cwd_wc_first_inst_pay_amt clmApprvdAmt,TRUNC (C.CLM_APPROVED_DT) clmApprovdDt\n"
							+ "    FROM claim_detail c,\n"
							+ "         member_info m,\n"
							+ "         SSI_DETAIL S,\n"
							+ "         application_detail a,\n"
							+ "         claim_wc_detail cw\n"
							+ "   WHERE     TRUNC (c.clm_approved_dt) BETWEEN ? AND ? \n"
							+ "         AND C.BID = S.BID\n"
							+ "         AND s.ssi_reference_number = a.ssi_reference_number\n"
							+ "         AND c.mem_bnk_id || c.mem_brn_id || c.mem_zne_id =\n"
							+ "                a.mem_bnk_id || a.mem_brn_id || a.mem_zne_id\n"
							+ "         AND LTRIM (\n"
							+ "                RTRIM (UPPER (c.mem_bnk_id || c.mem_zne_id || c.mem_brn_id))) =\n"
							+ "                LTRIM (\n"
							+ "                   RTRIM (UPPER (m.mem_bnk_id || m.mem_zne_id || m.mem_brn_id)))\n"
							+ "         AND c.clm_status = ? \n"
							+ "         and cw.clm_ref_no = c.clm_ref_no\n"
							+ "         and cw.cgpan = a.cgpan\n"
							+ "         and app_loan_type in ('WC')  \n"
							+ "union all\n"
							+ " SELECT m.mem_bnk_id || m.mem_zne_id || m.mem_brn_id memberid, m.mem_bank_name bankname, m.mem_zone_name,\n"
							+ "         DECODE (m.MEM_BRANCH_NAME,\n"
							+ "                 NULL, a.APP_MLI_BRANCH_NAME,\n"
							+ "                 m.MEM_BRANCH_NAME)\n"
							+ "            BRANCH,a.cgpan,S.SSI_UNIT_NAME, DECODE (a.app_reapprove_amount,\n"
							+ "                 NULL, a.APP_approved_amount,\n"
							+ "                 a.app_reapprove_amount)\n"
							+ "            GuarntAmt,c.clm_ref_no, c.cgclan,  c.CLM_DATE claimFilDt,  nvl(cwd_wc_first_inst_pay_amt,0)+nvl(ctd_tc_first_inst_pay_amt,0) clmApprvdAmt,TRUNC (C.CLM_APPROVED_DT) clmApprovdDt\n"
							+ "    FROM claim_detail c,\n"
							+ "         member_info m,\n"
							+ "         SSI_DETAIL S,\n"
							+ "         application_detail a,\n"
							+ "         claim_wc_detail cw,\n"
							+ "         claim_tc_detail ct\n"
							+ "   WHERE     TRUNC (c.clm_approved_dt) BETWEEN ? AND ?\n"
							+ "         AND C.BID = S.BID\n"
							+ "         AND s.ssi_reference_number = a.ssi_reference_number\n"
							+ "         AND c.mem_bnk_id || c.mem_brn_id || c.mem_zne_id =\n"
							+ "                a.mem_bnk_id || a.mem_brn_id || a.mem_zne_id\n"
							+ "         AND LTRIM (\n"
							+ "                RTRIM (UPPER (c.mem_bnk_id || c.mem_zne_id || c.mem_brn_id))) =\n"
							+ "                LTRIM (\n"
							+ "                   RTRIM (UPPER (m.mem_bnk_id || m.mem_zne_id || m.mem_brn_id)))\n"
							+ "         AND c.clm_status = ? \n"
							+ "         and cw.clm_ref_no = c.clm_ref_no\n"
							+ "         and cw.cgpan = a.cgpan\n"
							+ "         and ct.clm_ref_no = c.clm_ref_no\n"
							+ "         and ct.cgpan = a.cgpan\n"
							+ "         and app_loan_type in ('CC')   order by clmApprovdDt,bankname ";

					prepStatement = conn.prepareStatement(query);
					prepStatement.setDate(1, fromDate);
					prepStatement.setDate(2, toDate);
					prepStatement.setString(3, clmApplicationStatusFlag);
					prepStatement.setDate(4, fromDate);
					prepStatement.setDate(5, toDate);
					prepStatement.setString(6, clmApplicationStatusFlag);
					prepStatement.setDate(7, fromDate);
					prepStatement.setDate(8, toDate);
					prepStatement.setString(9, clmApplicationStatusFlag);
					rs = (ResultSet) prepStatement.executeQuery();
				}

				// kotttttt

				if (clmApplicationStatusFlag
						.equals(ClaimConstants.CLM_TEMPORARY_CLOSE)) {
					// kot5
					Log.log(Log.INFO, "ReportDAO",
							"getListOfClaimRefNumbers()",
							"From Date is NULL, Status is Reject");

					query = "SELECT a.mem_bnk_id || a.mem_zne_id || a.mem_brn_id,  \n"
							+ "                                d.mem_bank_name,  \n"
							+ "                                d.mem_zone_name,  \n"
							+ "                                DECODE (d.MEM_BRANCH_NAME,  \n"
							+ "                                        NULL, e.APP_MLI_BRANCH_NAME,  \n"
							+ "                                        d.MEM_BRANCH_NAME)  \n"
							+ "                                   branch,  \n"
							+ "                                e.cgpan,  \n"
							+ "                                c.ssi_unit_name,  \n"
							+ "                                DECODE (e.app_reapprove_amount,  \n"
							+ "                                        NULL, e.APP_approved_amount,  \n"
							+ "                                        e.app_reapprove_amount)  \n"
							+ "                                   guarantamt,  \n"
							+ "                                A.clm_ref_no,  \n"
							+ "                                a.clm_date,  \n"
							+ "                                b.caa_applied_amount clmAppdAmt,TRUNC(a.CLM_CREATED_MODIFIED_DT), \n"
							+ "                                (select distinct max(trim(upper(replace(clm_ltr_ref_no,' ',''))))\n"
							+ "                                from claim_query_detail cqd\n"
							+ "                                where a.clm_ref_no = cqd.clm_ref_no\n"
							+ "                                and clm_ltr_dt in \n"
							+ "                                (select max(clm_ltr_dt) from claim_query_detail cd where cd.clm_ref_no = cqd.clm_ref_no)\n"
							+ "                                ) clmqryrefno,\n"
							+ "                                (select distinct clm_ltr_dt\n"
							+ "                                from claim_query_detail cq\n"
							+ "                                where a.clm_ref_no = cq.clm_ref_no\n"
							+ "                                and clm_ltr_dt in \n"
							+ "                                (select max(clm_ltr_dt) from claim_query_detail cd where cd.clm_ref_no = cq.clm_ref_no)\n"
							+ "                                ) clmqrydt  \n"
							+ "                           FROM claim_detail_temp a,  \n"
							+ "                                claim_application_amount_temp b,  \n"
							+ "                                ssi_detail c,  \n"
							+ "                                member_info d,  \n"
							+ "                                application_detail e  \n"
							+ "                          WHERE     b.cgpan = e.cgpan  \n"
							+ "                                AND a.clm_ref_no = b.clm_ref_no  \n"
							+ "                                AND a.bid = c.bid  \n"
							+ "                                AND a.mem_bnk_id || a.mem_zne_id || a.mem_brn_id =  \n"
							+ "                                       d.mem_bnk_id || d.mem_zne_id || d.mem_brn_id  \n"
							+ "                               AND TRUNC (CLM_CREATED_MODIFIED_DT) BETWEEN ?  AND ?   \n"
							+ "                                AND a.clm_status = ?  order by TRUNC(CLM_CREATED_MODIFIED_DT),d.mem_bank_name ";

					prepStatement = conn.prepareStatement(query);
					prepStatement.setDate(1, fromDate);
					prepStatement.setDate(2, toDate);
					prepStatement.setString(3, clmApplicationStatusFlag);

					rs = (ResultSet) prepStatement.executeQuery();
				}

				if (clmApplicationStatusFlag.equals("RT")) {
					// kot9
					query = " SELECT  m.mem_bnk_id || m.mem_zne_id || m.mem_brn_id memberid, m.mem_bank_name bnkname,mem_zone_name zone,\n"
							+ "         decode(mem_branch_name,null,app_mli_branch_name,mem_branch_name) branch,\n"
							+ "                 a.cgpan, S.SSI_UNIT_NAME,\n"
							+ "         decode(nvl(app_reapprove_amount,0),0,app_approved_amount,app_Reapprove_amount) appamt,\n"
							+ "         c.clm_ref_no,\n"
							+ "                 caa_applied_amount clmedamt,\n"
							+ "         TRUNC (CLM_CREATED_MODIFIED_DT) rtdate,\n"
							+ "         clm_return_remarks\n"
							+ "    FROM claim_detail_temp c,\n"
							+ "         member_info m,\n"
							+ "         SSI_DETAIL S,\n"
							+ "         claim_application_amount_temp caa,\n"
							+ "         application_detail a\n"
							+ "   WHERE     TRUNC(C.CLM_CREATED_MODIFIED_DT) between ?  and ?  AND\n"
							+ "             C.BID = S.BID\n"
							+ "         AND c.clm_ref_no = caa.clm_ref_no\n"
							+ "         AND caa.cgpan = a.cgpan\n"
							+ "         AND LTRIM (\n"
							+ "                RTRIM (UPPER (c.mem_bnk_id || c.mem_zne_id || c.mem_brn_id))) =\n"
							+ "                LTRIM (\n"
							+ "                   RTRIM (UPPER (m.mem_bnk_id || m.mem_zne_id || m.mem_brn_id)))\n"
							+ "         AND c.clm_status = ?  \n" +

							"ORDER BY TRUNC (CLM_CREATED_MODIFIED_DT), bnkname";

					prepStatement = conn.prepareStatement(query);
					prepStatement.setDate(1, fromDate);
					prepStatement.setDate(2, toDate);
					prepStatement.setString(3, clmApplicationStatusFlag);

					rs = (ResultSet) prepStatement.executeQuery();
				}

				if (clmApplicationStatusFlag.equals("AS")) {
					// kot11

					query = " SELECT  m.mem_bnk_id || m.mem_zne_id || m.mem_brn_id memberid, m.mem_bank_name bnkname,mem_zone_name zone,\n"
							+ "         decode(mem_branch_name,null,app_mli_branch_name,mem_branch_name) branch,\n"
							+ "                 a.cgpan, S.SSI_UNIT_NAME,\n"
							+ "         decode(nvl(app_reapprove_amount,0),0,app_approved_amount,app_Reapprove_amount) appamt,\n"
							+ "         c.clm_ref_no,\n"
							+ "                 caa_applied_amount clmApplAmt,CLM_DATE,\n"
							+ "         clm_status,\n"
							+ "clm_decl_recvd_dt,TRUNC(c.CLM_CREATED_MODIFIED_DT)  withdrawndt  \n"
							+ "         \n"
							+ "    FROM claim_detail_temp c,\n"
							+ "         member_info m,\n"
							+ "         SSI_DETAIL S,\n"
							+ "         claim_application_amount_temp caa,\n"
							+ "         application_detail a\n"
							+ "   WHERE     TRUNC(C.CLM_date) between ?  and ?   AND\n"
							+ "             C.BID = S.BID\n"
							+ "         AND c.clm_ref_no = caa.clm_ref_no\n"
							+ "         AND caa.cgpan = a.cgpan\n"
							+ "         AND LTRIM (\n"
							+ "                RTRIM (UPPER (c.mem_bnk_id || c.mem_zne_id || c.mem_brn_id))) =\n"
							+ "                LTRIM (\n"
							+ "                   RTRIM (UPPER (m.mem_bnk_id || m.mem_zne_id || m.mem_brn_id)))  \n"
							+ "        \n"
							+ " union  all \n"
							+ " \n"
							+ "  SELECT  m.mem_bnk_id || m.mem_zne_id || m.mem_brn_id memberid, m.mem_bank_name bnkname,mem_zone_name zone,\n"
							+ "         decode(mem_branch_name,null,app_mli_branch_name,mem_branch_name) branch,\n"
							+ "                 a.cgpan, S.SSI_UNIT_NAME,\n"
							+ "         decode(nvl(app_reapprove_amount,0),0,app_approved_amount,app_Reapprove_amount) appamt,\n"
							+ "         c.clm_ref_no,\n"
							+ "                 caa_applied_amount clmApplAmt,CLM_DATE,\n"
							+ "         clm_status,\n"
							+ "clm_decl_recvd_dt,TRUNC(c.CLM_CREATED_MODIFIED_DT) withdrawndt \n"
							+ "        \n"
							+ "    FROM claim_detail c,\n"
							+ "         member_info m,\n"
							+ "         SSI_DETAIL S,\n"
							+ "         claim_application_amount caa,\n"
							+ "         application_detail a\n"
							+ "   WHERE     TRUNC(C.CLM_date) between ?  and ?  AND\n"
							+ "             C.BID = S.BID\n"
							+ "         AND c.clm_ref_no = caa.clm_ref_no\n"
							+ "         AND caa.cgpan = a.cgpan\n"
							+ "         AND LTRIM (\n"
							+ "                RTRIM (UPPER (c.mem_bnk_id || c.mem_zne_id || c.mem_brn_id))) =\n"
							+ "                LTRIM (\n"
							+ "                   RTRIM (UPPER (m.mem_bnk_id || m.mem_zne_id || m.mem_brn_id)))  order by withdrawndt, bnkname \n"
							+ "        ";

					prepStatement = conn.prepareStatement(query);
					prepStatement.setDate(1, fromDate);
					prepStatement.setDate(2, toDate);
					prepStatement.setDate(3, fromDate);
					prepStatement.setDate(4, toDate);
					// prepStatement.setString(3,clmApplicationStatusFlag);

					rs = (ResultSet) prepStatement.executeQuery();
				}

				if (clmApplicationStatusFlag.equals("RTD")) {
					// kot10
					query = "SELECT a.mem_bnk_id || a.mem_zne_id || a.mem_brn_id memberid,  \n"
							+ "                                d.mem_bank_name bnkname,  \n"
							+ "                                d.mem_zone_name,  \n"
							+ "                                DECODE (d.MEM_BRANCH_NAME,  \n"
							+ "                                        NULL, e.APP_MLI_BRANCH_NAME,  \n"
							+ "                                        d.MEM_BRANCH_NAME)  \n"
							+ "                                   branch,                                  e.cgpan,                                  c.ssi_unit_name,  \n"
							+ "                                DECODE (e.app_reapprove_amount,  \n"
							+ "                                        NULL, e.APP_approved_amount,  \n"
							+ "                                        e.app_reapprove_amount)  \n"
							+ "                                   guarantamt,  \n"
							+ "                                A.clm_ref_no,  \n"
							+ "                                a.clm_date,  \n"
							+ "                                b.caa_applied_amount clmApplAmt,TRUNC(a.CLM_CREATED_MODIFIED_DT) dt \n"
							+ "                           FROM claim_detail_temp a,  \n"
							+ "                                claim_application_amount_temp b,  \n"
							+ "                                ssi_detail c,  \n"
							+ "                                member_info d,  \n"
							+ "                                application_detail e  \n"
							+ "                          WHERE     b.cgpan = e.cgpan  \n"
							+ "                                AND a.clm_ref_no = b.clm_ref_no  \n"
							+ "                                AND a.bid = c.bid  \n"
							+ "                                AND a.mem_bnk_id || a.mem_zne_id || a.mem_brn_id =  \n"
							+ "                                       d.mem_bnk_id || d.mem_zne_id || d.mem_brn_id  \n"
							+ "                            AND TRUNC (a.CLM_CREATED_MODIFIED_DT) bETWEEN ?  AND ?   \n"
							+ "                                AND a.clm_status in ('RE','TR')    union all \n"
							+ " SELECT a.mem_bnk_id || a.mem_zne_id || a.mem_brn_id memberid,d.mem_bank_name bnkname,d.mem_zone_name, DECODE (d.MEM_BRANCH_NAME,\n"
							+ "                                           NULL, e.APP_MLI_BRANCH_NAME, \n"
							+ "                                            d.MEM_BRANCH_NAME)\n"
							+ "                                       branch,e.cgpan,c.ssi_unit_name, DECODE (e.app_reapprove_amount,\n"
							+ "                                            NULL, e.APP_approved_amount,\n"
							+ "                                            e.app_reapprove_amount)\n"
							+ "                                       guarantamt,A.clm_ref_no, a.clm_date,b.caa_applied_amount clmApplAmt,TRUNC(a.CLM_CREATED_MODIFIED_DT) dt \n"
							+ "                                                 FROM claim_detail a,\n"
							+ "                                    claim_application_amount b,\n"
							+ "                                    ssi_detail c,\n"
							+ "                                    member_info d,\n"
							+ "                                    application_detail e\n"
							+ "                              WHERE   \n"
							+ "                                   b.cgpan = e.cgpan\n"
							+ "                                    AND a.clm_ref_no = b.clm_ref_no\n"
							+ "                                    AND a.bid = c.bid\n"
							+ "                                    AND a.mem_bnk_id || a.mem_zne_id || a.mem_brn_id =\n"
							+ "                                           d.mem_bnk_id || d.mem_zne_id || d.mem_brn_id\n"
							+ "                                    AND TRUNC (a.CLM_CREATED_MODIFIED_DT) BETWEEN ?  AND ? \n"
							+ "                                    AND a.clm_status in ('RE','TR')  order by dt,bnkname ";

					prepStatement = conn.prepareStatement(query);
					prepStatement.setDate(1, fromDate);
					prepStatement.setDate(2, toDate);
					prepStatement.setDate(3, fromDate);
					prepStatement.setDate(4, toDate);
					// prepStatement.setString(3,clmApplicationStatusFlag);

					rs = (ResultSet) prepStatement.executeQuery();
				}

				if (clmApplicationStatusFlag
						.equals(ClaimConstants.CLM_TEMPORARY_REJECT)) {
					// kot6
					Log.log(Log.INFO, "ReportDAO",
							"getListOfClaimRefNumbers()",
							"From Date is NULL, Status is Reject");

					query = "SELECT a.mem_bnk_id || a.mem_zne_id || a.mem_brn_id memberid,  \n"
							+ "                                d.mem_bank_name,  \n"
							+ "                                d.mem_zone_name,  \n"
							+ "                                DECODE (d.MEM_BRANCH_NAME,  \n"
							+ "                                        NULL, e.APP_MLI_BRANCH_NAME,  \n"
							+ "                                        d.MEM_BRANCH_NAME)  \n"
							+ "                                   branch,  \n"
							+ "                                e.cgpan,  \n"
							+ "                                c.ssi_unit_name,  \n"
							+ "                                DECODE (e.app_reapprove_amount,  \n"
							+ "                                        NULL, e.APP_approved_amount,  \n"
							+ "                                        e.app_reapprove_amount)  \n"
							+ "                                   guarantamt,  \n"
							+ "                                A.clm_ref_no,  \n"
							+ "                                a.clm_date,  \n"
							+ "                                b.caa_applied_amount clmRejAmt,TRUNC(a.CLM_CREATED_MODIFIED_DT) ,clm_return_remarks\n"
							+ "                           FROM claim_detail_temp a,  \n"
							+ "                                claim_application_amount_temp b,  \n"
							+ "                                ssi_detail c,  \n"
							+ "                                member_info d,  \n"
							+ "                                application_detail e  \n"
							+ "                          WHERE     b.cgpan = e.cgpan  \n"
							+ "                                AND a.clm_ref_no = b.clm_ref_no  \n"
							+ "                                AND a.bid = c.bid  \n"
							+ "                                AND a.mem_bnk_id || a.mem_zne_id || a.mem_brn_id =  \n"
							+ "                                       d.mem_bnk_id || d.mem_zne_id || d.mem_brn_id  \n"
							+ "                               AND TRUNC (CLM_CREATED_MODIFIED_DT) BETWEEN ?  AND ?   \n"
							+ "                                AND a.clm_status = ?  order by TRUNC(CLM_CREATED_MODIFIED_DT), d.mem_bank_name ";
					prepStatement = conn.prepareStatement(query);
					prepStatement.setDate(1, fromDate);
					prepStatement.setDate(2, toDate);
					prepStatement.setString(3, clmApplicationStatusFlag);

					rs = (ResultSet) prepStatement.executeQuery();
				}
				if (clmApplicationStatusFlag
						.equals(ClaimConstants.CLM_WITHDRAWN)) {
					// kot7
					Log.log(Log.INFO, "ReportDAO",
							"getListOfClaimRefNumbers()",
							"From Date is NULL, Status is Reject");

					query = "SELECT a.mem_bnk_id || a.mem_zne_id || a.mem_brn_id memberid,  \n"
							+ "                                d.mem_bank_name bnkname,  \n"
							+ "                                d.mem_zone_name,  \n"
							+ "                                DECODE (d.MEM_BRANCH_NAME,  \n"
							+ "                                        NULL, e.APP_MLI_BRANCH_NAME,  \n"
							+ "                                        d.MEM_BRANCH_NAME)  \n"
							+ "                                   branch,  \n"
							+ "                                e.cgpan,  \n"
							+ "                                c.ssi_unit_name,  \n"
							+ "                                DECODE (e.app_reapprove_amount,  \n"
							+ "                                        NULL, e.APP_approved_amount,  \n"
							+ "                                        e.app_reapprove_amount)  \n"
							+ "                                   guarantamt,  \n"
							+ "                                A.clm_ref_no,  \n"
							+ "                                a.clm_date,  \n"
							+ "                                b.caa_applied_amount clmAPPAmt , TRUNC(a.CLM_CREATED_MODIFIED_DT) withdrawndt \n"
							+ "                           FROM claim_detail_temp a,  \n"
							+ "                                claim_application_amount_temp b,  \n"
							+ "                                ssi_detail c,  \n"
							+ "                                member_info d,  \n"
							+ "                                application_detail e  \n"
							+ "                          WHERE     b.cgpan = e.cgpan  \n"
							+ "                                AND a.clm_ref_no = b.clm_ref_no  \n"
							+ "                                AND a.bid = c.bid  \n"
							+ "                                AND a.mem_bnk_id || a.mem_zne_id || a.mem_brn_id =  \n"
							+ "                                       d.mem_bnk_id || d.mem_zne_id || d.mem_brn_id  \n"
							+ "                               AND TRUNC (a.CLM_CREATED_MODIFIED_DT) BETWEEN ?  AND ?   \n"
							+ "                                AND a.clm_status = ?     \n"
							+ "union all \n"
							+ "SELECT a.mem_bnk_id || a.mem_zne_id || a.mem_brn_id memberid,  \n"
							+ "                                d.mem_bank_name bnkname,  \n"
							+ "                                d.mem_zone_name,  \n"
							+ "                                DECODE (d.MEM_BRANCH_NAME,  \n"
							+ "                                        NULL, e.APP_MLI_BRANCH_NAME,  \n"
							+ "                                        d.MEM_BRANCH_NAME)  \n"
							+ "                                   branch,  \n"
							+ "                                e.cgpan,  \n"
							+ "                                c.ssi_unit_name,  \n"
							+ "                                DECODE (e.app_reapprove_amount,  \n"
							+ "                                        NULL, e.APP_approved_amount,  \n"
							+ "                                        e.app_reapprove_amount)  \n"
							+ "                                   guarantamt,  \n"
							+ "                                A.clm_ref_no,  \n"
							+ "                                a.clm_date,  \n"
							+ "                                b.caa_applied_amount clmappAmt,TRUNC(a.CLM_CREATED_MODIFIED_DT) withdrawndt  \n"
							+ "                           FROM claim_detail a,  \n"
							+ "                                claim_application_amount b,  \n"
							+ "                                ssi_detail c,  \n"
							+ "                                member_info d,  \n"
							+ "                                application_detail e  \n"
							+ "                          WHERE     b.cgpan = e.cgpan  \n"
							+ "                                AND a.clm_ref_no = b.clm_ref_no  \n"
							+ "                                AND a.bid = c.bid  \n"
							+ "                                AND a.mem_bnk_id || a.mem_zne_id || a.mem_brn_id =  \n"
							+ "                                       d.mem_bnk_id || d.mem_zne_id || d.mem_brn_id  \n"
							+ "                                AND TRUNC (CLM_CREATED_MODIFIED_DT) BETWEEN ?  AND ?   \n"
							+ "                                AND a.clm_status = ?  order by withdrawndt, bnkname  ";
					prepStatement = conn.prepareStatement(query);
					prepStatement.setDate(1, fromDate);
					prepStatement.setDate(2, toDate);
					prepStatement.setString(3, clmApplicationStatusFlag);
					prepStatement.setDate(4, fromDate);
					prepStatement.setDate(5, toDate);
					prepStatement.setString(6, clmApplicationStatusFlag);

					rs = (ResultSet) prepStatement.executeQuery();
				}

				if (clmApplicationStatusFlag
						.equals(ClaimConstants.CLM_REJECT_STATUS)) {
					// kot3
					Log.log(Log.INFO, "ReportDAO",
							"getListOfClaimRefNumbers()",
							"From Date is NULL, Status is Reject");

					query = "  SELECT a.mem_bnk_id || a.mem_zne_id || a.mem_brn_id,d.mem_bank_name,d.mem_zone_name, DECODE (d.MEM_BRANCH_NAME,\n"
							+ "                 NULL, e.APP_MLI_BRANCH_NAME,\n"
							+ "                 d.MEM_BRANCH_NAME)\n"
							+ "            branch,e.cgpan,c.ssi_unit_name, DECODE (e.app_reapprove_amount,\n"
							+ "                 NULL, e.APP_approved_amount,\n"
							+ "                 e.app_reapprove_amount)\n"
							+ "            guarantamt,A.clm_ref_no, a.clm_date,b.caa_applied_amount clmAppdAmt, TRUNC(a.CLM_CREATED_MODIFIED_DT),clm_return_remarks \n"
							+ "                      FROM claim_detail a,\n"
							+ "         claim_application_amount b,\n"
							+ "         ssi_detail c,\n"
							+ "         member_info d,\n"
							+ "         application_detail e\n"
							+ "   WHERE   \n"
							+ "        b.cgpan = e.cgpan\n"
							+ "         AND a.clm_ref_no = b.clm_ref_no\n"
							+ "         AND a.bid = c.bid\n"
							+ "         AND a.mem_bnk_id || a.mem_zne_id || a.mem_brn_id =\n"
							+ "                d.mem_bnk_id || d.mem_zne_id || d.mem_brn_id\n"
							+ "         AND TRUNC (a.CLM_CREATED_MODIFIED_DT) BETWEEN ?  AND ? \n"
							+ "         AND a.clm_status = ?   order by TRUNC(CLM_CREATED_MODIFIED_DT), d.mem_bank_name  ";

					prepStatement = conn.prepareStatement(query);
					prepStatement.setDate(1, fromDate);
					prepStatement.setDate(2, toDate);
					prepStatement.setString(3, clmApplicationStatusFlag);

					rs = (ResultSet) prepStatement.executeQuery();
				}
				// ADDED FORWARD REPORT BY SUKUMAR@PATH ON 12-09-2009
				if (clmApplicationStatusFlag
						.equals(ClaimConstants.CLM_FORWARD_STATUS)) {
					Log.log(Log.INFO, "ReportDAO",
							"getListOfClaimRefNumbers()",
							"From Date is NULL, Status is  Forward");
					// kot4
					query = "SELECT m.mem_bnk_id || m.mem_zne_id || m.mem_brn_id,\n"
							+ "       m.mem_bank_name bnkname,\n"
							+ "       m.mem_zone_name,\n"
							+ "       DECODE (m.MEM_BRANCH_NAME,\n"
							+ "               NULL, a.APP_MLI_BRANCH_NAME,\n"
							+ "               m.MEM_BRANCH_NAME)\n"
							+ "          BRANCH,\n"
							+ "       d.cgpan,\n"
							+ "       S.SSI_UNIT_NAME,\n"
							+ "       DECODE (a.app_reapprove_amount,\n"
							+ "               NULL, a.APP_approved_amount,\n"
							+ "               a.app_reapprove_amount)\n"
							+ "          guartAmt,\n"
							+ "       c.clm_ref_no,\n"
							+ "       C.CLM_DATE clmFilgDt,\n"
							+ "       d.ctd_tc_first_inst_pay_amt clmForwrdAmt, \n"
							+ "       TRUNC (CLM_CREATED_MODIFIED_DT) clmForwdDt \n"
							+ "  FROM claim_detail_temp c,\n"
							+ "       member_info m,\n"
							+ "       SSI_DETAIL S,\n"
							+ "       application_detail a,\n"
							+ "       CLAIM_TC_DETAIL_TEMP D\n"
							+ " WHERE     TRUNC (C.CLM_CREATED_MODIFIED_DT) BETWEEN ? \n"
							+ "                                                 AND  ? \n"
							+ "       AND C.BID = S.BID\n"
							+ "       AND s.ssi_reference_number = a.ssi_reference_number\n"
							+ "       AND c.mem_bnk_id || c.mem_brn_id || c.mem_zne_id =\n"
							+ "              a.mem_bnk_id || a.mem_brn_id || a.mem_zne_id\n"
							+ "       AND LTRIM (\n"
							+ "              RTRIM (UPPER (c.mem_bnk_id || c.mem_zne_id || c.mem_brn_id))) =\n"
							+ "              LTRIM (\n"
							+ "                 RTRIM (UPPER (m.mem_bnk_id || m.mem_zne_id || m.mem_brn_id)))\n"
							+ "       AND c.clm_status = ? \n"
							+ "       AND C.CLM_REF_NO = D.CLM_REF_NO\n"
							+ "       AND d.cgpan = a.cgpan\n"
							+ "       AND app_loan_type IN ('TC')   \n"
							+ "UNION ALL\n"
							+ "SELECT m.mem_bnk_id || m.mem_zne_id || m.mem_brn_id,\n"
							+ "       m.mem_bank_name bnkname,\n"
							+ "       m.mem_zone_name,\n"
							+ "       DECODE (m.MEM_BRANCH_NAME,\n"
							+ "               NULL, a.APP_MLI_BRANCH_NAME,\n"
							+ "               m.MEM_BRANCH_NAME)\n"
							+ "          BRANCH,\n"
							+ "       d.cgpan,\n"
							+ "       S.SSI_UNIT_NAME,\n"
							+ "       DECODE (a.app_reapprove_amount,\n"
							+ "               NULL, a.APP_approved_amount,\n"
							+ "               a.app_reapprove_amount)\n"
							+ "          guartAmt,\n"
							+ "       c.clm_ref_no,\n"
							+ "       C.CLM_DATE clmFilgDt,\n"
							+ "       d.cwd_wc_first_inst_pay_amt clmForwrdAmt,\n"
							+ "       TRUNC (CLM_CREATED_MODIFIED_DT) clmForwdDt \n"
							+ "  FROM claim_detail_temp c,\n"
							+ "       member_info m,\n"
							+ "       SSI_DETAIL S,\n"
							+ "       application_detail a,\n"
							+ "       CLAIM_WC_DETAIL_TEMP D\n"
							+ " WHERE     TRUNC (C.CLM_CREATED_MODIFIED_DT) BETWEEN ? \n"
							+ "                                                 AND ?  \n"
							+ "       AND C.BID = S.BID\n"
							+ "       AND s.ssi_reference_number = a.ssi_reference_number\n"
							+ "       AND c.mem_bnk_id || c.mem_brn_id || c.mem_zne_id =\n"
							+ "              a.mem_bnk_id || a.mem_brn_id || a.mem_zne_id\n"
							+ "       AND LTRIM (\n"
							+ "              RTRIM (UPPER (c.mem_bnk_id || c.mem_zne_id || c.mem_brn_id))) =\n"
							+ "              LTRIM (\n"
							+ "                 RTRIM (UPPER (m.mem_bnk_id || m.mem_zne_id || m.mem_brn_id)))\n"
							+ "       AND c.clm_status = ? \n"
							+ "       AND C.CLM_REF_NO = D.CLM_REF_NO\n"
							+ "       AND d.cgpan = a.cgpan\n"
							+ "       AND app_loan_type IN ('WC')   \n"
							+ "UNION ALL\n"
							+ "SELECT m.mem_bnk_id || m.mem_zne_id || m.mem_brn_id,\n"
							+ "       m.mem_bank_name bnkname,\n"
							+ "       m.mem_zone_name,\n"
							+ "       DECODE (m.MEM_BRANCH_NAME,\n"
							+ "               NULL, a.APP_MLI_BRANCH_NAME,\n"
							+ "               m.MEM_BRANCH_NAME)\n"
							+ "          BRANCH,\n"
							+ "       d.cgpan,\n"
							+ "       S.SSI_UNIT_NAME,\n"
							+ "       DECODE (a.app_reapprove_amount,\n"
							+ "               NULL, a.APP_approved_amount,\n"
							+ "               a.app_reapprove_amount)\n"
							+ "          guartAmt,\n"
							+ "       c.clm_ref_no,\n"
							+ "       C.CLM_DATE clmFilgDt,\n"
							+ "         NVL (d.cwd_wc_first_inst_pay_amt, 0)\n"
							+ "       + NVL (CWD_WC_FIRST_INST_PAY_AMT, 0)\n"
							+ "          clmForwrdAmt,\n"
							+ "       TRUNC (CLM_CREATED_MODIFIED_DT) clmForwdDt \n"
							+ "  FROM claim_detail_temp c,\n"
							+ "       member_info m,\n"
							+ "       SSI_DETAIL S,\n"
							+ "       application_detail a,\n"
							+ "       CLAIM_WC_DETAIL_TEMP D,\n"
							+ "       CLAIM_TC_DETAIL_TEMP E\n"
							+ " WHERE     TRUNC (C.CLM_CREATED_MODIFIED_DT) BETWEEN ? \n"
							+ "                                                 AND ? \n"
							+ "       AND C.BID = S.BID\n"
							+ "       AND s.ssi_reference_number = a.ssi_reference_number\n"
							+ "       AND c.mem_bnk_id || c.mem_brn_id || c.mem_zne_id =\n"
							+ "              a.mem_bnk_id || a.mem_brn_id || a.mem_zne_id\n"
							+ "       AND LTRIM (\n"
							+ "              RTRIM (UPPER (c.mem_bnk_id || c.mem_zne_id || c.mem_brn_id))) =\n"
							+ "              LTRIM (\n"
							+ "                 RTRIM (UPPER (m.mem_bnk_id || m.mem_zne_id || m.mem_brn_id)))\n"
							+ "       AND c.clm_status = ? \n"
							+ "       AND C.CLM_REF_NO = D.CLM_REF_NO\n"
							+ "       AND D.cgpan = a.cgpan\n"
							+ "       AND C.CLM_REF_NO = E.CLM_REF_NO\n"
							+ "       AND E.cgpan = a.cgpan\n"
							+ "       AND app_loan_type IN ('CC') order by clmForwdDt, bnkname ";

					prepStatement = conn.prepareStatement(query);
					prepStatement.setDate(1, fromDate);
					prepStatement.setDate(2, toDate);
					prepStatement.setString(3, clmApplicationStatusFlag);
					prepStatement.setDate(4, fromDate);
					prepStatement.setDate(5, toDate);
					prepStatement.setString(6, clmApplicationStatusFlag);
					prepStatement.setDate(7, fromDate);
					prepStatement.setDate(8, toDate);
					prepStatement.setString(9, clmApplicationStatusFlag);
					rs = (ResultSet) prepStatement.executeQuery();
				}
				/*
				 * ADDED BY SUKUMAR@PATH ON 20-FEB-2010 FOR DISPLAY TEMPORARY
				 * CLOSE & TEMPORARY REJECT APPLICATION
				 */
				if (clmApplicationStatusFlag.equals("KTC")
						|| clmApplicationStatusFlag.equals("KWC")
						|| clmApplicationStatusFlag.equals("KWD")) {
					Log.log(Log.INFO, "ReportDAO",
							"getListOfClaimRefNumbers()",
							"From Date is NULL, Status is  TEMPORARY CLOSED");
					// new kot
					query = "SELECT a.mem_bnk_id || a.mem_zne_id || a.mem_brn_id,\n"
							+ "       d.mem_bank_name,\n"
							+ "       d.mem_zone_name,\n"
							+ "       DECODE (d.MEM_BRANCH_NAME,\n"
							+ "               NULL, e.APP_MLI_BRANCH_NAME,\n"
							+ "               d.MEM_BRANCH_NAME)\n"
							+ "          branch,\n"
							+ "       e.cgpan,\n"
							+ "       c.ssi_unit_name,\n"
							+ "       DECODE (e.app_reapprove_amount,\n"
							+ "               NULL, e.APP_approved_amount,\n"
							+ "               e.app_reapprove_amount)\n"
							+ "          guarantamt,\n"
							+ "       A.clm_ref_no,\n"
							+ "       a.clm_date,\n"
							+ "       b.caa_applied_amount clmAppdAmt\n"
							+ "  FROM claim_detail_temp a,\n"
							+ "       claim_application_amount_temp b,\n"
							+ "       ssi_detail c,\n"
							+ "       member_info d,\n"
							+ "       application_detail e\n"
							+ " WHERE     b.cgpan = e.cgpan\n"
							+ "       AND a.clm_ref_no = b.clm_ref_no\n"
							+ "       AND a.bid = c.bid\n"
							+ "       AND a.mem_bnk_id || a.mem_zne_id || a.mem_brn_id =\n"
							+ "              d.mem_bnk_id || d.mem_zne_id || d.mem_brn_id\n"
							+ "       AND TRUNC (CLM_DATE) BETWEEN ?  AND ? \n"
							+ "       AND a.clm_status = ? \n";

					prepStatement = conn.prepareStatement(query);
					prepStatement.setDate(1, fromDate);
					prepStatement.setDate(2, toDate);
					prepStatement.setString(3, clmApplicationStatusFlag);

					rs = (ResultSet) prepStatement.executeQuery();
				}
				// added by upchar@path on 03/07/2013 to display reply received
				// claim applications
				if (clmApplicationStatusFlag
						.equals(ClaimConstants.CLM_REPLY_RECEIVED)) {
					// kot8
					Log.log(Log.INFO, "ReportDAO",
							"getListOfClaimRefNumbers()",
							"From Date is NULL, Status is  TEMPORARY CLOSED");

					query = "SELECT a.mem_bnk_id || a.mem_zne_id || a.mem_brn_id memberid,  \n"
							+ "                                d.mem_bank_name,  \n"
							+ "                                d.mem_zone_name,  \n"
							+ "                                DECODE (d.MEM_BRANCH_NAME,  \n"
							+ "                                        NULL, e.APP_MLI_BRANCH_NAME,  \n"
							+ "                                        d.MEM_BRANCH_NAME)  \n"
							+ "                                   branch,  \n"
							+ "                                e.cgpan,  \n"
							+ "                                c.ssi_unit_name,  \n"
							+ "                                DECODE (e.app_reapprove_amount,  \n"
							+ "                                        NULL, e.APP_approved_amount,  \n"
							+ "                                        e.app_reapprove_amount)  \n"
							+ "                                   guarantamt,  \n"
							+ "                                A.clm_ref_no,  \n"
							+ "                                a.clm_date,  \n"
							+ "                                b.caa_applied_amount clmAppdAmt,TRUNC(a.CLM_CREATED_MODIFIED_DT), \n"
							+ "                                (select distinct max(trim(upper(replace(clm_ltr_ref_no,' ',''))))\n"
							+ "                                from claim_query_detail cqd\n"
							+ "                                where a.clm_ref_no = cqd.clm_ref_no\n"
							+ "                                and clm_ltr_dt in \n"
							+ "                                (select max(clm_ltr_dt) from claim_query_detail cd where cd.clm_ref_no = cqd.clm_ref_no)\n"
							+ "                                ) clmqryrefno,\n"
							+ "                                (select distinct clm_ltr_dt\n"
							+ "                                from claim_query_detail cq\n"
							+ "                                where a.clm_ref_no = cq.clm_ref_no\n"
							+ "                                and clm_ltr_dt in \n"
							+ "                                (select max(clm_ltr_dt) from claim_query_detail cd where cd.clm_ref_no = cq.clm_ref_no)\n"
							+ "                                ) clmqrydt,\n"
							+ "                                (select inward_id from claim_reply_detail crd\n"
							+ "                                where crd.clm_ref_no = a.clm_ref_no\n"
							+ "                                and inward_dt in \n"
							+ "                                (select max(inward_dt) from claim_reply_detail cd where cd.clm_ref_no = crd.clm_ref_no)\n"
							+ "                                ) rplyinwid,\n"
							+ "                                (select inward_dt from claim_reply_detail crd\n"
							+ "                                where crd.clm_ref_no = a.clm_ref_no\n"
							+ "                                and inward_dt in \n"
							+ "                                (select max(inward_dt) from claim_reply_detail cd where cd.clm_ref_no = crd.clm_ref_no)\n"
							+ "                                ) rplyinwdt ,TRUNC(a.CLM_CREATED_MODIFIED_DT) \n"
							+ "                           FROM claim_detail_temp a,  \n"
							+ "                                claim_application_amount_temp b,  \n"
							+ "                                ssi_detail c,  \n"
							+ "                                member_info d,  \n"
							+ "                                application_detail e  \n"
							+ "                          WHERE     b.cgpan = e.cgpan  \n"
							+ "                                AND a.clm_ref_no = b.clm_ref_no  \n"
							+ "                                AND a.bid = c.bid  \n"
							+ "                                AND a.mem_bnk_id || a.mem_zne_id || a.mem_brn_id =  \n"
							+ "                                       d.mem_bnk_id || d.mem_zne_id || d.mem_brn_id  \n"
							+ "                               AND TRUNC (CLM_CREATED_MODIFIED_DT) BETWEEN ?  AND ?   \n"
							+ "                                AND a.clm_status = ?   order by TRUNC(CLM_CREATED_MODIFIED_DT),d.mem_bank_name ";

					prepStatement = conn.prepareStatement(query);
					prepStatement.setDate(1, fromDate);
					prepStatement.setDate(2, toDate);
					prepStatement.setString(3, clmApplicationStatusFlag);
					rs = (ResultSet) prepStatement.executeQuery();
				}
			}
			if ((fromDate != null)
					&& ((clmApplicationStatusFlag
							.equals(ClaimConstants.CLM_PENDING_STATUS) || clmApplicationStatusFlag
							.equals(ClaimConstants.CLM_HOLD_STATUS)
					// clmApplicationStatusFlag.equals(ClaimConstants.CLM_FORWARD_STATUS)
					))) {
				Log.log(Log.INFO, "ReportDAO", "getListOfClaimRefNumbers()",
						"From Date is NULL, Status is Pending or Forward or Hold");
				// new kot2
				query = "SELECT  m.mem_bnk_id || m.mem_zne_id || m.mem_brn_id,m.mem_bank_name bnkname,m.mem_zone_name, DECODE (m.MEM_BRANCH_NAME,\n"
						+ "                 NULL, a.APP_MLI_BRANCH_NAME,\n"
						+ "                 m.MEM_BRANCH_NAME)\n"
						+ "            BRANCH,a.cgpan,S.SSI_UNIT_NAME,DECODE (a.app_reapprove_amount,\n"
						+ "                 NULL, a.APP_approved_amount,\n"
						+ "                 a.app_reapprove_amount)\n"
						+ "            GuarAmt,c.clm_ref_no, C.CLM_DATE, b.caa_applied_amount clmAppliedAmt,C.CLM_DECLARATION_RECVD clmDeclRcvdFlg, C.CLM_DECL_RECVD_DT clmDeclRecvdDt\n"
						+ "                                      \n"
						+ "    FROM claim_detail_temp c,\n"
						+ "    claim_application_amount_temp b,\n"
						+ "         member_info m,\n"
						+ "         SSI_DETAIL S,\n"
						+ "         application_detail a\n"
						+ "         \n"
						+ "   WHERE     TRUNC (c.clm_date) BETWEEN ?  AND ? \n"
						+ "         AND C.BID = S.BID\n"
						+ "         AND s.ssi_reference_number = a.ssi_reference_number\n"
						+ "         AND c.mem_bnk_id || c.mem_brn_id || c.mem_zne_id =\n"
						+ "                a.mem_bnk_id || a.mem_brn_id || a.mem_zne_id\n"
						+ "         AND LTRIM (\n"
						+ "                RTRIM (UPPER (c.mem_bnk_id || c.mem_zne_id || c.mem_brn_id))) =\n"
						+ "                LTRIM (\n"
						+ "                   RTRIM (UPPER (m.mem_bnk_id || m.mem_zne_id || m.mem_brn_id)))\n"
						+ "         AND c.clm_status = ? \n"
						+ "         AND C.CLM_REF_NO = b.CLM_REF_NO\n"
						+ "         AND b.CGPAN = a.CGPAN  order by TRUNC(CLM_CREATED_MODIFIED_DT),bnkname ";

				prepStatement = conn.prepareStatement(query);
				prepStatement.setDate(1, fromDate);
				prepStatement.setDate(2, toDate);
				prepStatement.setString(3, clmApplicationStatusFlag);

				rs = (ResultSet) prepStatement.executeQuery();
			}
			if ((fromDate == null)
					&& (!(clmApplicationStatusFlag
							.equals(ClaimConstants.CLM_PENDING_STATUS) || clmApplicationStatusFlag
							.equals(ClaimConstants.CLM_HOLD_STATUS)
					// clmApplicationStatusFlag.equals(ClaimConstants.CLM_FORWARD_STATUS)
					))) {
				if (clmApplicationStatusFlag
						.equals(ClaimConstants.CLM_APPROVAL_STATUS)) {
					Log.log(Log.INFO, "ReportDAO",
							"getListOfClaimRefNumbers()",
							"From Date is NULL, Status is Approval");
					query = "select m.mem_bank_name bnkname, m.mem_bnk_id || m.mem_zne_id || m.mem_brn_id, c.clm_ref_no, c.cgclan,S.SSI_UNIT_NAME,TRUNC(CLM_APPROVED_DT),CLM_APPROVED_AMT "
							+ " from claim_detail c, member_info m,SSI_DETAIL S "
							+
							// code changed clm_date to clm_approved_dt by
							// sukumar@path on 11-09-2009
							// " where c.clm_date <= ? AND C.BID=S.BID " +
							" where TRUNC(c.clm_approved_dt) <= ? AND C.BID=S.BID "
							+ " and LTRIM(RTRIM(UPPER(c.mem_bnk_id || c.mem_zne_id || c.mem_brn_id))) = LTRIM(RTRIM(UPPER(m.mem_bnk_id || m.mem_zne_id || m.mem_brn_id))) "
							+ " and c.clm_status = ?"
							+ " group by m.mem_bank_name,"
							+ " m.mem_bnk_id || m.mem_zne_id || m.mem_brn_id,"
							+ " c.clm_ref_no,"
							+ " c.cgclan,S.SSI_UNIT_NAME,TRUNC(CLM_APPROVED_DT),CLM_APPROVED_AMT order by Trunc(c.clm_approved_dt),bnkname";
					prepStatement = conn.prepareStatement(query);
					// prepStatement.setDate(1,fromDate);
					prepStatement.setDate(1, toDate);
					prepStatement.setString(2, clmApplicationStatusFlag);
					rs = (ResultSet) prepStatement.executeQuery();
				} else if (clmApplicationStatusFlag
						.equals(ClaimConstants.CLM_REJECT_STATUS)) {
					Log.log(Log.INFO, "ReportDAO",
							"getListOfClaimRefNumbers()",
							"From Date is NULL, Status is Reject");

					query = "  SELECT a.mem_bnk_id || a.mem_zne_id || a.mem_brn_id,d.mem_bank_name,d.mem_zone_name, DECODE (d.MEM_BRANCH_NAME,\n"
							+ "                 NULL, e.APP_MLI_BRANCH_NAME,\n"
							+ "                 d.MEM_BRANCH_NAME)\n"
							+ "            branch,e.cgpan,c.ssi_unit_name, DECODE (e.app_reapprove_amount,\n"
							+ "                 NULL, e.APP_approved_amount,\n"
							+ "                 e.app_reapprove_amount)\n"
							+ "            guarantamt,A.clm_ref_no, a.clm_date,b.caa_applied_amount clmAppdAmt \n"
							+ "                      FROM claim_detail a,\n"
							+ "         claim_application_amount b,\n"
							+ "         ssi_detail c,\n"
							+ "         member_info d,\n"
							+ "         application_detail e\n"
							+ "   WHERE   \n"
							+ "        b.cgpan = e.cgpan\n"
							+ "         AND a.clm_ref_no = b.clm_ref_no\n"
							+ "         AND a.bid = c.bid\n"
							+ "         AND a.mem_bnk_id || a.mem_zne_id || a.mem_brn_id =\n"
							+ "                d.mem_bnk_id || d.mem_zne_id || d.mem_brn_id\n"
							+ "         AND TRUNC (a.CLM_CREATED_MODIFIED_DT) BETWEEN ?  AND ? \n"
							+ "         AND a.clm_status = ? ";

					prepStatement = conn.prepareStatement(query);
					prepStatement.setDate(1, fromDate);
					prepStatement.setDate(2, toDate);
					prepStatement.setString(3, clmApplicationStatusFlag);

					rs = (ResultSet) prepStatement.executeQuery();
				} else if (clmApplicationStatusFlag
						.equals(ClaimConstants.CLM_FORWARD_STATUS)) {
					Log.log(Log.INFO, "ReportDAO",
							"getListOfClaimRefNumbers()",
							"From Date is NULL, Status is Forward");
					query = "select m.mem_bank_name bnkname, m.mem_bnk_id || m.mem_zne_id || m.mem_brn_id, c.clm_ref_no,S.SSI_UNIT_NAME,TRUNC(CLM_CREATED_MODIFIED_DT) "
							+ " from claim_detail_temp c, member_info m,SSI_DETAIL S"
							+ " where TRUNC(C.CLM_CREATED_MODIFIED_DT) <= ? AND C.BID=S.BID "
							+ " and LTRIM(RTRIM(UPPER(c.mem_bnk_id || c.mem_zne_id || c.mem_brn_id))) = LTRIM(RTRIM(UPPER(m.mem_bnk_id || m.mem_zne_id || m.mem_brn_id))) "
							+ " and c.clm_status = ?"
							+ " group by m.mem_bank_name,"
							+ " m.mem_bnk_id || m.mem_zne_id || m.mem_brn_id,"
							+ " c.clm_ref_no,S.SSI_UNIT_NAME,TRUNC(CLM_CREATED_MODIFIED_DT) order by TRUNC(CLM_CREATED_MODIFIED_DT),bnkname";

					prepStatement = conn.prepareStatement(query);
					prepStatement.setDate(1, toDate);
					prepStatement.setString(2, clmApplicationStatusFlag);
					rs = (ResultSet) prepStatement.executeQuery();
				} else if (clmApplicationStatusFlag
						.equals(ClaimConstants.CLM_TEMPORARY_CLOSE)
						|| clmApplicationStatusFlag
								.equals(ClaimConstants.CLM_TEMPORARY_REJECT)
						|| clmApplicationStatusFlag
								.equals(ClaimConstants.CLM_WITHDRAWN)) {
					Log.log(Log.INFO, "ReportDAO",
							"getListOfClaimRefNumbers()",
							"From Date is NULL, Status is Forward");
					query = "select m.mem_bank_name bnkname, m.mem_bnk_id || m.mem_zne_id || m.mem_brn_id, c.clm_ref_no,S.SSI_UNIT_NAME,TRUNC(CLM_CREATED_MODIFIED_DT) "
							+ " from claim_detail_temp c, member_info m,SSI_DETAIL S"
							+ " where TRUNC(C.CLM_CREATED_MODIFIED_DT) <= ? AND C.BID=S.BID "
							+ " and LTRIM(RTRIM(UPPER(c.mem_bnk_id || c.mem_zne_id || c.mem_brn_id))) = LTRIM(RTRIM(UPPER(m.mem_bnk_id || m.mem_zne_id || m.mem_brn_id))) "
							+ " and c.clm_status = ?"
							+ " group by m.mem_bank_name,"
							+ " m.mem_bnk_id || m.mem_zne_id || m.mem_brn_id,"
							+ " c.clm_ref_no,S.SSI_UNIT_NAME,TRUNC(CLM_CREATED_MODIFIED_DT) order by TRUNC(CLM_CREATED_MODIFIED_DT),bnkname";
					prepStatement = conn.prepareStatement(query);
					prepStatement.setDate(1, toDate);
					prepStatement.setString(2, clmApplicationStatusFlag);
					rs = (ResultSet) prepStatement.executeQuery();
				} else if (clmApplicationStatusFlag
						.equals(ClaimConstants.CLM_REPLY_RECEIVED)) {
					Log.log(Log.INFO, "ReportDAO",
							"getListOfClaimRefNumbers()",
							"From Date is NULL, Status is  TEMPORARY CLOSED");
					query = "select m.mem_bank_name bnkname, m.mem_bnk_id || m.mem_zne_id || m.mem_brn_id, c.clm_ref_no,S.SSI_UNIT_NAME,TRUNC(CLM_CREATED_MODIFIED_DT) "
							+ " from claim_detail_temp c, member_info m,SSI_DETAIL S"
							+ " where TRUNC(C.CLM_CREATED_MODIFIED_DT) <= ? AND C.BID=S.BID "
							+ " and LTRIM(RTRIM(UPPER(c.mem_bnk_id || c.mem_zne_id || c.mem_brn_id))) = LTRIM(RTRIM(UPPER(m.mem_bnk_id || m.mem_zne_id || m.mem_brn_id))) "
							+ " and c.clm_status = ?"
							+ " group by m.mem_bank_name,"
							+ " m.mem_bnk_id || m.mem_zne_id || m.mem_brn_id,"
							+ " c.clm_ref_no,S.SSI_UNIT_NAME,TRUNC(CLM_CREATED_MODIFIED_DT) order by TRUNC(CLM_CREATED_MODIFIED_DT),bnkname";
					prepStatement = conn.prepareStatement(query);
					prepStatement.setDate(1, toDate);
					prepStatement.setString(2, clmApplicationStatusFlag);
					rs = (ResultSet) prepStatement.executeQuery();
				}
			}
			if ((fromDate == null)
					&& ((clmApplicationStatusFlag
							.equals(ClaimConstants.CLM_PENDING_STATUS) || clmApplicationStatusFlag
							.equals(ClaimConstants.CLM_HOLD_STATUS)
					// clmApplicationStatusFlag.equals(ClaimConstants.CLM_FORWARD_STATUS)
					))) {
				Log.log(Log.INFO, "ReportDAO", "getListOfClaimRefNumbers()",
						"From Date is NULL, Status is Pending or Forward or Hold");
				query = "select m.mem_bank_name bnkname, m.mem_bnk_id || m.mem_zne_id || m.mem_brn_id, c.clm_ref_no,S.SSI_UNIT_NAME,CLM_DATE "
						+ " from claim_detail_temp c, member_info m,SSI_DETAIL S"
						+ " where c.clm_date <= ? AND C.BID=S.BID "
						+ " and LTRIM(RTRIM(UPPER(c.mem_bnk_id || c.mem_zne_id || c.mem_brn_id))) = LTRIM(RTRIM(UPPER(m.mem_bnk_id || m.mem_zne_id || m.mem_brn_id))) "
						+ " and c.clm_status = ?"
						+ " group by m.mem_bank_name,"
						+ " m.mem_bnk_id || m.mem_zne_id || m.mem_brn_id,"
						+ " c.clm_ref_no,S.SSI_UNIT_NAME,CLM_DATE order by CLM_DATE,bnkname";
				prepStatement = conn.prepareStatement(query);
				prepStatement.setDate(1, toDate);
				prepStatement.setString(2, clmApplicationStatusFlag);
				rs = (ResultSet) prepStatement.executeQuery();
			}
			String memberBankName = null;
			String memberId = null;
			String clmRefNumber = null;
			String cgclan = "";
			ClaimDetail clmDtl = null;
			String unitName = "";
			java.util.Date submittedDt = null;
			java.util.Date clmLodgmentdDt = null;
			java.util.Date claimForwardedDt = null;

			String claimRetRemarks = "";
			java.util.Date claimReturnDate = null;

			Double claimForwdAmt = null;

			java.util.Date claimRejectedDt = null;

			java.util.Date tempRejOrRejDt = null;

			java.util.Date claimReplyRecvdDate = null;

			double claimAppliedAmt = 0.0;

			double claimRejAmount = 0.0;

			String clmQryRefNumber = "";

			java.util.Date clamQryDate = null;

			String clmDeclRecvdFlag = null;
			java.util.Date clmDeclRecvdDt = null;

			java.util.Date lastActionTakenDt = null;

			String claimStatus = "";

			String replyInwardId = "";

			java.util.Date replyInwardDt = null;

			java.util.Date tempClosedDate = null;

			java.util.Date tempRejectedDate = null;

			double claimApprovedAmt = 0.0;

			double totalClmApprovedAmt = 0.0;

			double totGrandClmApprovedAmtTemp = 0.0;

			double totGrandClmApprovedAmt = 0.0;

			String zoneName = null;
			String branchName = null;
			double guarApprdAmount = 0.0;

			double totalGuarnteAmt = 0.0;

			double totgrandTotal = 0.0;

			double totgrandTotalTemp = 0.0;

			double claimDecRecvdAmt = 0.0;
			String claimDecRecvdflag = null;
			java.util.Date claimDecRecvdDt = null;
			java.util.Date claimWithDrawnDt = null;
			String claimRemarks = null;
			String cgpan = null;
			java.util.Date clmApprovedDt = null;

			if (clmApplicationStatusFlag
					.equals(ClaimConstants.CLM_APPROVAL_STATUS)) {
				while (rs.next()) {

					memberId = (String) rs.getString(1);
					memberBankName = (String) rs.getString(2);
					zoneName = (String) rs.getString(3);
					branchName = (String) rs.getString(4);
					cgpan = (String) rs.getString(5);
					unitName = (String) rs.getString(6);
					guarApprdAmount = (Double) rs.getDouble(7);
					clmRefNumber = (String) rs.getString(8);
					cgclan = (String) rs.getString(9);
					clmLodgmentdDt = (Date) rs.getDate(10);
					claimApprovedAmt = (double) rs.getDouble(11);
					clmApprovedDt = (Date) rs.getDate(12);

					totalGuarnteAmt = totalGuarnteAmt + guarApprdAmount;
					totgrandTotalTemp = totalGuarnteAmt / 100000;
					totalClmApprovedAmt = totalClmApprovedAmt
							+ claimApprovedAmt;
					totGrandClmApprovedAmtTemp = totalClmApprovedAmt / 100000;
					DecimalFormat decFormat = new DecimalFormat("#.00");
					totGrandClmApprovedAmt = new Double(
							decFormat.format(totGrandClmApprovedAmtTemp))
							.doubleValue();
					totgrandTotal = new Double(
							decFormat.format(totgrandTotalTemp)).doubleValue();

					clmDtl = new ClaimDetail();

					clmDtl.setMliId(memberId);
					clmDtl.setMliName(memberBankName);
					clmDtl.setBranchName(branchName);
					clmDtl.setZoneName(zoneName);
					clmDtl.setCgpan(cgpan);
					clmDtl.setSsiUnitName(unitName);
					clmDtl.setApplicationApprovedAmount(guarApprdAmount);
					clmDtl.setClaimRefNum(clmRefNumber);
					clmDtl.setCGCLAN(cgclan);
					clmDtl.setClmSubmittedDt(clmLodgmentdDt);
					clmDtl.setClaimApprovedAmount(claimApprovedAmt);
					clmDtl.setClaimApprovedDt(clmApprovedDt);
					clmDtl.setTotgrandTotal(totgrandTotal);
					clmDtl.setTotGrandClmApprovedAmt(totGrandClmApprovedAmt);

					// Adding the ClaimDetail object to the vector
					clmRefNumbersList.addElement(clmDtl);
				}
				rs.close();
				rs = null;
			} else if

			(clmApplicationStatusFlag.equals(ClaimConstants.CLM_PENDING_STATUS)) {
				while (rs.next()) {

					memberId = (String) rs.getString(1);
					memberBankName = (String) rs.getString(2);
					zoneName = (String) rs.getString(3);
					branchName = (String) rs.getString(4);
					cgpan = (String) rs.getString(5);
					unitName = (String) rs.getString(6);
					guarApprdAmount = (Double) rs.getDouble(7);
					clmRefNumber = (String) rs.getString(8);
					clmLodgmentdDt = (Date) rs.getDate(9);
					claimAppliedAmt = (Double) rs.getDouble(10);
					clmDeclRecvdFlag = (String) rs.getString(11);
					clmDeclRecvdDt = (Date) rs.getDate(12);

					totalGuarnteAmt = totalGuarnteAmt + guarApprdAmount;
					totgrandTotalTemp = totalGuarnteAmt / 100000;
					totalClmApprovedAmt = totalClmApprovedAmt + claimAppliedAmt;
					totGrandClmApprovedAmtTemp = totalClmApprovedAmt / 100000;
					DecimalFormat decFormat = new DecimalFormat("#.00");
					totGrandClmApprovedAmt = new Double(
							decFormat.format(totGrandClmApprovedAmtTemp))
							.doubleValue();
					totgrandTotal = new Double(
							decFormat.format(totgrandTotalTemp)).doubleValue();

					clmDtl = new ClaimDetail();

					clmDtl.setMliId(memberId);
					clmDtl.setMliName(memberBankName);
					clmDtl.setBranchName(branchName);
					clmDtl.setZoneName(zoneName);
					clmDtl.setCgpan(cgpan);
					clmDtl.setSsiUnitName(unitName);
					clmDtl.setApplicationApprovedAmount(guarApprdAmount);
					clmDtl.setClaimRefNum(clmRefNumber);
					clmDtl.setClmSubmittedDt(clmLodgmentdDt);
					clmDtl.setClaimAppliedAmt(claimAppliedAmt);
					clmDtl.setClmDeclRecvdFlag(clmDeclRecvdFlag);
					clmDtl.setClmDeclRecvdDt(clmDeclRecvdDt);

					clmDtl.setTotgrandTotal(totgrandTotal);
					clmDtl.setTotGrandClmApprovedAmt(totGrandClmApprovedAmt);

					// Adding the ClaimDetail object to the vector
					clmRefNumbersList.addElement(clmDtl);
				}
				rs.close();
				rs = null;
			} else if

			(clmApplicationStatusFlag.equals(ClaimConstants.CLM_WITHDRAWN)) {
				while (rs.next()) {

					memberId = (String) rs.getString(1);
					memberBankName = (String) rs.getString(2);
					zoneName = (String) rs.getString(3);
					branchName = (String) rs.getString(4);
					cgpan = (String) rs.getString(5);
					unitName = (String) rs.getString(6);
					guarApprdAmount = (Double) rs.getDouble(7);
					clmRefNumber = (String) rs.getString(8);
					clmLodgmentdDt = (Date) rs.getDate(9);
					claimAppliedAmt = (Double) rs.getDouble(10);
					claimWithDrawnDt = (Date) rs.getDate(11);

					totalGuarnteAmt = totalGuarnteAmt + guarApprdAmount;
					totgrandTotalTemp = totalGuarnteAmt / 100000;
					totalClmApprovedAmt = totalClmApprovedAmt + claimAppliedAmt;
					totGrandClmApprovedAmtTemp = totalClmApprovedAmt / 100000;
					DecimalFormat decFormat = new DecimalFormat("#.00");
					totGrandClmApprovedAmt = new Double(
							decFormat.format(totGrandClmApprovedAmtTemp))
							.doubleValue();
					totgrandTotal = new Double(
							decFormat.format(totgrandTotalTemp)).doubleValue();

					clmDtl = new ClaimDetail();

					clmDtl.setMliId(memberId);
					clmDtl.setMliName(memberBankName);
					clmDtl.setBranchName(branchName);
					clmDtl.setZoneName(zoneName);
					clmDtl.setCgpan(cgpan);
					clmDtl.setSsiUnitName(unitName);
					clmDtl.setApplicationApprovedAmount(guarApprdAmount);
					clmDtl.setClaimRefNum(clmRefNumber);
					clmDtl.setClmSubmittedDt(clmLodgmentdDt);
					clmDtl.setClaimAppliedAmt(claimAppliedAmt);
					clmDtl.setClaimWithDrawnDate(claimWithDrawnDt);

					clmDtl.setTotgrandTotal(totgrandTotal);
					clmDtl.setTotGrandClmApprovedAmt(totGrandClmApprovedAmt);

					// clmDtl.setClaimLodgmentDate(clmLodgmentdDt);

					// Adding the ClaimDetail object to the vector
					clmRefNumbersList.addElement(clmDtl);

				}
				rs.close();
				rs = null;
			} else if

			(clmApplicationStatusFlag
					.equals(ClaimConstants.CLM_TEMPORARY_CLOSE)) {
				while (rs.next()) {

					memberId = (String) rs.getString(1);
					memberBankName = (String) rs.getString(2);
					zoneName = (String) rs.getString(3);
					branchName = (String) rs.getString(4);
					cgpan = (String) rs.getString(5);
					unitName = (String) rs.getString(6);
					guarApprdAmount = (Double) rs.getDouble(7);
					clmRefNumber = (String) rs.getString(8);
					clmLodgmentdDt = (Date) rs.getDate(9);
					claimAppliedAmt = (Double) rs.getDouble(10);
					tempClosedDate = (Date) rs.getDate(11);

					clmQryRefNumber = (String) rs.getString(12);

					clamQryDate = (Date) rs.getDate(13);

					totalGuarnteAmt = totalGuarnteAmt + guarApprdAmount;
					totgrandTotalTemp = totalGuarnteAmt / 100000;
					totalClmApprovedAmt = totalClmApprovedAmt + claimAppliedAmt;
					totGrandClmApprovedAmtTemp = totalClmApprovedAmt / 100000;
					DecimalFormat decFormat = new DecimalFormat("#.00");
					totGrandClmApprovedAmt = new Double(
							decFormat.format(totGrandClmApprovedAmtTemp))
							.doubleValue();
					totgrandTotal = new Double(
							decFormat.format(totgrandTotalTemp)).doubleValue();

					clmDtl = new ClaimDetail();

					clmDtl.setMliId(memberId);
					clmDtl.setMliName(memberBankName);
					clmDtl.setBranchName(branchName);
					clmDtl.setZoneName(zoneName);
					clmDtl.setCgpan(cgpan);
					clmDtl.setSsiUnitName(unitName);
					clmDtl.setApplicationApprovedAmount(guarApprdAmount);
					clmDtl.setClaimRefNum(clmRefNumber);
					clmDtl.setClmSubmittedDt(clmLodgmentdDt);
					clmDtl.setClaimAppliedAmt(claimAppliedAmt);
					clmDtl.setTempClosedDate(tempClosedDate);

					clmDtl.setClmQryRefNumber(clmQryRefNumber);
					clmDtl.setClamQryDate(clamQryDate);
					clmDtl.setTotgrandTotal(totgrandTotal);
					clmDtl.setTotGrandClmApprovedAmt(totGrandClmApprovedAmt);

					// Adding the ClaimDetail object to the vector
					clmRefNumbersList.addElement(clmDtl);

				}
				rs.close();
				rs = null;
			}

			else if

			(clmApplicationStatusFlag
					.equals(ClaimConstants.CLM_TEMPORARY_REJECT)) {
				while (rs.next()) {

					memberId = (String) rs.getString(1);
					memberBankName = (String) rs.getString(2);
					zoneName = (String) rs.getString(3);
					branchName = (String) rs.getString(4);
					cgpan = (String) rs.getString(5);
					unitName = (String) rs.getString(6);
					guarApprdAmount = (Double) rs.getDouble(7);
					clmRefNumber = (String) rs.getString(8);
					clmLodgmentdDt = (Date) rs.getDate(9);
					claimRejAmount = (Double) rs.getDouble(10);
					tempRejectedDate = (Date) rs.getDate(11);
					claimRetRemarks = rs.getString(12);

					totalGuarnteAmt = totalGuarnteAmt + guarApprdAmount;
					totgrandTotalTemp = totalGuarnteAmt / 100000;
					totalClmApprovedAmt = totalClmApprovedAmt + claimRejAmount;
					totGrandClmApprovedAmtTemp = totalClmApprovedAmt / 100000;
					DecimalFormat decFormat = new DecimalFormat("#.00");
					totGrandClmApprovedAmt = new Double(
							decFormat.format(totGrandClmApprovedAmtTemp))
							.doubleValue();
					totgrandTotal = new Double(
							decFormat.format(totgrandTotalTemp)).doubleValue();

					clmDtl = new ClaimDetail();

					clmDtl.setMliId(memberId);
					clmDtl.setMliName(memberBankName);
					clmDtl.setBranchName(branchName);
					clmDtl.setZoneName(zoneName);
					clmDtl.setCgpan(cgpan);
					clmDtl.setSsiUnitName(unitName);
					clmDtl.setApplicationApprovedAmount(guarApprdAmount);
					clmDtl.setClaimRefNum(clmRefNumber);
					clmDtl.setClmSubmittedDt(clmLodgmentdDt);
					clmDtl.setClaimAppliedAmt(claimRejAmount);
					clmDtl.setTempRejectedDate(tempRejectedDate);

					clmDtl.setClaimRetRemarks(claimRetRemarks);
					clmDtl.setTotgrandTotal(totgrandTotal);
					clmDtl.setTotGrandClmApprovedAmt(totGrandClmApprovedAmt);

					// clmDtl.setClaimLodgmentDate(clmLodgmentdDt);

					// Adding the ClaimDetail object to the vector
					clmRefNumbersList.addElement(clmDtl);

				}
				rs.close();
				rs = null;
			}

			else if

			(clmApplicationStatusFlag.equals(ClaimConstants.CLM_REPLY_RECEIVED)) {
				while (rs.next()) {

					memberId = (String) rs.getString(1);
					memberBankName = (String) rs.getString(2);
					zoneName = (String) rs.getString(3);
					branchName = (String) rs.getString(4);
					cgpan = (String) rs.getString(5);
					unitName = (String) rs.getString(6);
					guarApprdAmount = (Double) rs.getDouble(7);
					clmRefNumber = (String) rs.getString(8);
					clmLodgmentdDt = (Date) rs.getDate(9);
					claimApprovedAmt = (Double) rs.getDouble(10);
					claimReplyRecvdDate = (Date) rs.getDate(11);
					clmQryRefNumber = (String) rs.getString(12);
					clamQryDate = (Date) rs.getDate(13);
					replyInwardId = (String) rs.getString(14);
					replyInwardDt = (Date) rs.getDate(15);

					totalGuarnteAmt = totalGuarnteAmt + guarApprdAmount;
					totgrandTotalTemp = totalGuarnteAmt / 100000;
					totalClmApprovedAmt = totalClmApprovedAmt
							+ claimApprovedAmt;
					totGrandClmApprovedAmtTemp = totalClmApprovedAmt / 100000;
					DecimalFormat decFormat = new DecimalFormat("#.00");
					totGrandClmApprovedAmt = new Double(
							decFormat.format(totGrandClmApprovedAmtTemp))
							.doubleValue();
					totgrandTotal = new Double(
							decFormat.format(totgrandTotalTemp)).doubleValue();

					clmDtl = new ClaimDetail();

					clmDtl.setMliId(memberId);
					clmDtl.setMliName(memberBankName);
					clmDtl.setBranchName(branchName);
					clmDtl.setZoneName(zoneName);
					clmDtl.setCgpan(cgpan);
					clmDtl.setSsiUnitName(unitName);
					clmDtl.setApplicationApprovedAmount(guarApprdAmount);
					clmDtl.setClaimRefNum(clmRefNumber);
					clmDtl.setClmSubmittedDt(clmLodgmentdDt);
					clmDtl.setClaimApprovedAmount(claimApprovedAmt);
					clmDtl.setClaimReplyRecvdDate(claimReplyRecvdDate);
					clmDtl.setClmQryRefNumber(clmQryRefNumber);
					clmDtl.setClamQryDate(clamQryDate);

					clmDtl.setReplyInwardId(replyInwardId);
					clmDtl.setReplyInwardDt(replyInwardDt);
					clmDtl.setTotgrandTotal(totgrandTotal);
					clmDtl.setTotGrandClmApprovedAmt(totGrandClmApprovedAmt);

					// clmDtl.setClaimLodgmentDate(clmLodgmentdDt);

					// Adding the ClaimDetail object to the vector
					clmRefNumbersList.addElement(clmDtl);

				}
				rs.close();
				rs = null;
			}

			else if

			(clmApplicationStatusFlag.equals(ClaimConstants.CLM_REJECT_STATUS)) {
				while (rs.next()) {

					memberId = (String) rs.getString(1);
					memberBankName = (String) rs.getString(2);
					zoneName = (String) rs.getString(3);
					branchName = (String) rs.getString(4);
					cgpan = (String) rs.getString(5);
					unitName = (String) rs.getString(6);
					guarApprdAmount = (Double) rs.getDouble(7);
					clmRefNumber = (String) rs.getString(8);
					clmLodgmentdDt = (Date) rs.getDate(9);
					claimAppliedAmt = (Double) rs.getDouble(10);
					claimRejectedDt = (Date) rs.getDate(11);
					claimRetRemarks = rs.getString(12);

					totalGuarnteAmt = totalGuarnteAmt + guarApprdAmount;
					totgrandTotalTemp = totalGuarnteAmt / 100000;
					totalClmApprovedAmt = totalClmApprovedAmt + claimAppliedAmt;
					totGrandClmApprovedAmtTemp = totalClmApprovedAmt / 100000;
					DecimalFormat decFormat = new DecimalFormat("#.00");
					totGrandClmApprovedAmt = new Double(
							decFormat.format(totGrandClmApprovedAmtTemp))
							.doubleValue();
					totgrandTotal = new Double(
							decFormat.format(totgrandTotalTemp)).doubleValue();

					clmDtl = new ClaimDetail();

					clmDtl.setMliId(memberId);
					clmDtl.setMliName(memberBankName);
					clmDtl.setBranchName(branchName);
					clmDtl.setZoneName(zoneName);
					clmDtl.setCgpan(cgpan);
					clmDtl.setSsiUnitName(unitName);
					clmDtl.setApplicationApprovedAmount(guarApprdAmount);
					clmDtl.setClaimRefNum(clmRefNumber);
					clmDtl.setClmSubmittedDt(clmLodgmentdDt);
					clmDtl.setClaimAppliedAmt(claimAppliedAmt);
					clmDtl.setClaimRejectedDt(claimRejectedDt);

					clmDtl.setTotgrandTotal(totgrandTotal);
					clmDtl.setTotGrandClmApprovedAmt(totGrandClmApprovedAmt);
					clmDtl.setClaimRetRemarks(claimRetRemarks);

					// Adding the ClaimDetail object to the vector
					clmRefNumbersList.addElement(clmDtl);

				}
				rs.close();
				rs = null;
			}

			else if

			(clmApplicationStatusFlag.equals("RT")) {
				while (rs.next()) {

					memberId = (String) rs.getString(1);
					memberBankName = (String) rs.getString(2);
					zoneName = (String) rs.getString(3);
					branchName = (String) rs.getString(4);
					cgpan = (String) rs.getString(5);
					unitName = (String) rs.getString(6);
					guarApprdAmount = (Double) rs.getDouble(7);
					clmRefNumber = (String) rs.getString(8);
					claimAppliedAmt = (Double) rs.getDouble(9);
					claimReturnDate = (Date) rs.getDate(10);
					claimRetRemarks = (String) rs.getString(11);

					totalGuarnteAmt = totalGuarnteAmt + guarApprdAmount;
					totgrandTotalTemp = totalGuarnteAmt / 100000;
					totalClmApprovedAmt = totalClmApprovedAmt + claimAppliedAmt;
					totGrandClmApprovedAmtTemp = totalClmApprovedAmt / 100000;
					DecimalFormat decFormat = new DecimalFormat("#.00");
					totGrandClmApprovedAmt = new Double(
							decFormat.format(totGrandClmApprovedAmtTemp))
							.doubleValue();
					totgrandTotal = new Double(
							decFormat.format(totgrandTotalTemp)).doubleValue();

					clmDtl = new ClaimDetail();

					clmDtl.setMliId(memberId);
					clmDtl.setMliName(memberBankName);
					clmDtl.setBranchName(branchName);
					clmDtl.setZoneName(zoneName);
					clmDtl.setCgpan(cgpan);
					clmDtl.setSsiUnitName(unitName);
					clmDtl.setApplicationApprovedAmount(guarApprdAmount);
					clmDtl.setClaimRefNum(clmRefNumber);
					clmDtl.setClaimAppliedAmt(claimAppliedAmt);
					clmDtl.setClaimReturnDate(claimReturnDate);
					clmDtl.setClaimRetRemarks(claimRetRemarks);
					clmDtl.setTotgrandTotal(totgrandTotal);
					clmDtl.setTotGrandClmApprovedAmt(totGrandClmApprovedAmt);

					clmRefNumbersList.addElement(clmDtl);
				}
				rs.close();
				rs = null;
			}

			else if

			(clmApplicationStatusFlag.equals("RTD")) {
				while (rs.next()) {

					memberId = (String) rs.getString(1);
					memberBankName = (String) rs.getString(2);
					zoneName = (String) rs.getString(3);
					branchName = (String) rs.getString(4);
					cgpan = (String) rs.getString(5);
					unitName = (String) rs.getString(6);
					guarApprdAmount = (Double) rs.getDouble(7);
					clmRefNumber = (String) rs.getString(8);
					clmLodgmentdDt = (Date) rs.getDate(9);
					claimAppliedAmt = (Double) rs.getDouble(10);
					tempRejOrRejDt = (Date) rs.getDate(11);

					totalGuarnteAmt = totalGuarnteAmt + guarApprdAmount;
					totgrandTotalTemp = totalGuarnteAmt / 100000;
					totalClmApprovedAmt = totalClmApprovedAmt + claimAppliedAmt;
					totGrandClmApprovedAmtTemp = totalClmApprovedAmt / 100000;
					DecimalFormat decFormat = new DecimalFormat("#.00");
					totGrandClmApprovedAmt = new Double(
							decFormat.format(totGrandClmApprovedAmtTemp))
							.doubleValue();
					totgrandTotal = new Double(
							decFormat.format(totgrandTotalTemp)).doubleValue();

					clmDtl = new ClaimDetail();

					clmDtl.setMliId(memberId);
					clmDtl.setMliName(memberBankName);
					clmDtl.setBranchName(branchName);
					clmDtl.setZoneName(zoneName);
					clmDtl.setCgpan(cgpan);
					clmDtl.setSsiUnitName(unitName);
					clmDtl.setApplicationApprovedAmount(guarApprdAmount);
					clmDtl.setClaimRefNum(clmRefNumber);
					clmDtl.setClmSubmittedDt(clmLodgmentdDt);
					clmDtl.setClaimAppliedAmt(claimAppliedAmt);

					clmDtl.setTempRejOrRejDt(tempRejOrRejDt);
					clmDtl.setTotgrandTotal(totgrandTotal);
					clmDtl.setTotGrandClmApprovedAmt(totGrandClmApprovedAmt);

					clmRefNumbersList.addElement(clmDtl);
				}
				rs.close();
				rs = null;
			}

			else if

			(clmApplicationStatusFlag.equals("AS")) {
				while (rs.next()) {

					memberId = (String) rs.getString(1);
					memberBankName = (String) rs.getString(2);
					zoneName = (String) rs.getString(3);
					branchName = (String) rs.getString(4);
					cgpan = (String) rs.getString(5);
					unitName = (String) rs.getString(6);
					guarApprdAmount = (Double) rs.getDouble(7);
					clmRefNumber = (String) rs.getString(8);
					claimAppliedAmt = (Double) rs.getDouble(9);
					clmLodgmentdDt = (Date) rs.getDate(10);
					claimStatus = (String) rs.getString(11);

					clmDeclRecvdDt = (Date) rs.getDate(12);

					lastActionTakenDt = (Date) rs.getDate(13);

					totalGuarnteAmt = totalGuarnteAmt + guarApprdAmount;
					totgrandTotalTemp = totalGuarnteAmt / 100000;
					totalClmApprovedAmt = totalClmApprovedAmt + claimAppliedAmt;
					totGrandClmApprovedAmtTemp = totalClmApprovedAmt / 100000;
					DecimalFormat decFormat = new DecimalFormat("#.00");
					totGrandClmApprovedAmt = new Double(
							decFormat.format(totGrandClmApprovedAmtTemp))
							.doubleValue();
					totgrandTotal = new Double(
							decFormat.format(totgrandTotalTemp)).doubleValue();

					clmDtl = new ClaimDetail();

					clmDtl.setMliId(memberId);
					clmDtl.setMliName(memberBankName);
					clmDtl.setBranchName(branchName);
					clmDtl.setZoneName(zoneName);
					clmDtl.setCgpan(cgpan);
					clmDtl.setSsiUnitName(unitName);
					clmDtl.setApplicationApprovedAmount(guarApprdAmount);
					clmDtl.setClaimRefNum(clmRefNumber);
					clmDtl.setClaimAppliedAmt(claimAppliedAmt);
					clmDtl.setClmSubmittedDt(clmLodgmentdDt);
					clmDtl.setClmStatus(claimStatus);
					clmDtl.setClmDeclRecvdDt(clmDeclRecvdDt);

					clmDtl.setLastActionTakenDt(lastActionTakenDt);

					clmDtl.setTotgrandTotal(totgrandTotal);
					clmDtl.setTotGrandClmApprovedAmt(totGrandClmApprovedAmt);

					clmRefNumbersList.addElement(clmDtl);
				}
				rs.close();
				rs = null;
			}

			else if

			(clmApplicationStatusFlag.equals(ClaimConstants.CLM_FORWARD_STATUS)) {
				while (rs.next()) {

					memberId = (String) rs.getString(1);
					memberBankName = (String) rs.getString(2);
					zoneName = (String) rs.getString(3);
					branchName = (String) rs.getString(4);
					cgpan = (String) rs.getString(5);
					unitName = (String) rs.getString(6);
					guarApprdAmount = (Double) rs.getDouble(7);
					clmRefNumber = (String) rs.getString(8);
					clmLodgmentdDt = (Date) rs.getDate(9);
					claimForwdAmt = (double) rs.getDouble(10);
					claimForwardedDt = (Date) rs.getDate(11);

					totalGuarnteAmt = totalGuarnteAmt + guarApprdAmount;
					totgrandTotalTemp = totalGuarnteAmt / 100000;
					totalClmApprovedAmt = totalClmApprovedAmt + claimForwdAmt;
					totGrandClmApprovedAmtTemp = totalClmApprovedAmt / 100000;
					DecimalFormat decFormat = new DecimalFormat("#.00");
					totGrandClmApprovedAmt = new Double(
							decFormat.format(totGrandClmApprovedAmtTemp))
							.doubleValue();
					totgrandTotal = new Double(
							decFormat.format(totgrandTotalTemp)).doubleValue();

					clmDtl = new ClaimDetail();

					clmDtl.setMliId(memberId);
					clmDtl.setMliName(memberBankName);
					clmDtl.setBranchName(branchName);
					clmDtl.setZoneName(zoneName);
					clmDtl.setCgpan(cgpan);
					clmDtl.setSsiUnitName(unitName);
					clmDtl.setApplicationApprovedAmount(guarApprdAmount);
					clmDtl.setClaimRefNum(clmRefNumber);
					clmDtl.setClmSubmittedDt(clmLodgmentdDt);
					clmDtl.setClaimForwdAmt(claimForwdAmt);
					clmDtl.setClaimForwardedDt(claimForwardedDt);
					clmDtl.setTotgrandTotal(totgrandTotal);
					clmDtl.setTotGrandClmApprovedAmt(totGrandClmApprovedAmt);

					// Adding the ClaimDetail object to the vector
					clmRefNumbersList.addElement(clmDtl);
				}
				rs.close();
				rs = null;
			}

			else {
				while (rs.next()) {
					memberBankName = (String) rs.getString(1);
					memberId = (String) rs.getString(2);
					clmRefNumber = (String) rs.getString(3);
					unitName = (String) rs.getString(4);
					submittedDt = (Date) rs.getDate(5);

					zoneName = (String) rs.getString(6);
					branchName = (String) rs.getString(7);
					guarApprdAmount = (Double) rs.getDouble(8);

					clmDtl = new ClaimDetail();
					clmDtl.setMliName(memberBankName);
					clmDtl.setMliId(memberId);
					clmDtl.setClaimRefNum(clmRefNumber);
					clmDtl.setCGCLAN(cgclan);
					clmDtl.setSsiUnitName(unitName);
					clmDtl.setClmSubmittedDt(submittedDt);

					clmDtl.setZoneName(zoneName);
					clmDtl.setBranchName(branchName);
					clmDtl.setApplicationApprovedAmount(guarApprdAmount);

					// Adding the ClaimDetail object to the vector
					clmRefNumbersList.addElement(clmDtl);
				}
				rs.close();
				rs = null;
			}
			prepStatement.close();
			prepStatement = null;
		} catch (SQLException sqlexception) {
			// sqlexception.printStackTrace();
			throw new DatabaseException(sqlexception.getMessage());
		} finally {
			DBConnection.freeConnection(conn);
		}
		return clmRefNumbersList;
	}

	public Vector getListOfClaimRefNumbers(java.sql.Date fromDate,
			java.sql.Date toDate, String clmApplicationStatusFlag,
			String bankId, String zoneId) throws DatabaseException {

		Log.log(Log.INFO, "ReportDAO", "getListOfClaimRefNumbers()", "Entered");
		Log.log(Log.INFO, "ReportDAO", "getListOfClaimRefNumbers()",
				"From Date :" + fromDate);
		Log.log(Log.INFO, "ReportDAO", "getListOfClaimRefNumbers()",
				"To Date :" + toDate);
		Log.log(Log.INFO, "ReportDAO", "getListOfClaimRefNumbers()",
				"clmApplicationStatusFlag :" + clmApplicationStatusFlag);
		Log.log(Log.INFO, "ReportDAO", "getListOfClaimRefNumbers()",
				"Bank Id :" + bankId);
		Log.log(Log.INFO, "ReportDAO", "getListOfClaimRefNumbers()",
				"Zone Id :" + zoneId);
		Connection conn = null;
		PreparedStatement prepStatement = null;
		ResultSet rs = null;
		Vector clmRefNumbersList = new Vector();
		String query = null;

		try {
			conn = DBConnection.getConnection();
			if ((fromDate != null)
					&& (!(clmApplicationStatusFlag
							.equals(ClaimConstants.CLM_PENDING_STATUS)
							|| clmApplicationStatusFlag
									.equals(ClaimConstants.CLM_HOLD_STATUS) || clmApplicationStatusFlag
							.equals(ClaimConstants.CLM_FORWARD_STATUS)))) {
				if (clmApplicationStatusFlag
						.equals(ClaimConstants.CLM_APPROVAL_STATUS)) {
					Log.log(Log.INFO, "ReportDAO",
							"getListOfClaimRefNumbers()",
							"From Date is NULL, Status is Approval");
					/*
					 * query modification@sudeep.dhiman to get resultset in
					 * sorted order
					 */
					query = "select m.mem_bank_name bnkname, m.mem_bnk_id || m.mem_zne_id || m.mem_brn_id, c.clm_ref_no, c.cgclan"
							+ " from claim_detail c, member_info m"
							+ " where c.clm_date between ? and ?"
							+ " and LTRIM(RTRIM(UPPER(c.mem_bnk_id || c.mem_zne_id || c.mem_brn_id))) = LTRIM(RTRIM(UPPER(m.mem_bnk_id || m.mem_zne_id || m.mem_brn_id))) "
							+ " and c.clm_status = ?"
							+ " and m.mem_bnk_id = ?"
							+ " and (m.mem_reporting_zone_id = ? or m.mem_zne_id = ?) "
							+ " group by m.mem_bank_name,"
							+ " m.mem_bnk_id || m.mem_zne_id || m.mem_brn_id,"
							+ " c.clm_ref_no," + " c.cgclan order by bnkname";
					prepStatement = conn.prepareStatement(query);
					prepStatement.setDate(1, fromDate);
					prepStatement.setDate(2, toDate);
					prepStatement.setString(3, clmApplicationStatusFlag);
					prepStatement.setString(4, bankId);
					prepStatement.setString(5, zoneId);
					prepStatement.setString(6, zoneId);
					rs = (ResultSet) prepStatement.executeQuery();
				}
				if (clmApplicationStatusFlag
						.equals(ClaimConstants.CLM_REJECT_STATUS)) {
					Log.log(Log.INFO, "ReportDAO",
							"getListOfClaimRefNumbers()",
							"From Date is NULL, Status is Reject");
					/*
					 * query modification@sudeep.dhiman to get resultset in
					 * sorted order
					 */
					query = "select m.mem_bank_name bnkname, m.mem_bnk_id || m.mem_zne_id || m.mem_brn_id, c.clm_ref_no"
							+ " from claim_detail c, member_info m"
							+ " where c.clm_date between ? and ?"
							+ " and LTRIM(RTRIM(UPPER(c.mem_bnk_id || c.mem_zne_id || c.mem_brn_id))) = LTRIM(RTRIM(UPPER(m.mem_bnk_id || m.mem_zne_id || m.mem_brn_id))) "
							+ " and c.clm_status = ?"
							+ " and m.mem_bnk_id = ?"
							+ " and (m.mem_reporting_zone_id = ? or m.mem_zne_id = ?) "
							+ " group by m.mem_bank_name,"
							+ " m.mem_bnk_id || m.mem_zne_id || m.mem_brn_id,"
							+ " c.clm_ref_no order by bnkname";
					prepStatement = conn.prepareStatement(query);
					prepStatement.setDate(1, fromDate);
					prepStatement.setDate(2, toDate);
					prepStatement.setString(3, clmApplicationStatusFlag);
					prepStatement.setString(4, bankId);
					prepStatement.setString(5, zoneId);
					prepStatement.setString(6, zoneId);
					rs = (ResultSet) prepStatement.executeQuery();
				}
			}
			if ((fromDate != null)
					&& ((clmApplicationStatusFlag
							.equals(ClaimConstants.CLM_PENDING_STATUS)
							|| clmApplicationStatusFlag
									.equals(ClaimConstants.CLM_HOLD_STATUS) || clmApplicationStatusFlag
							.equals(ClaimConstants.CLM_FORWARD_STATUS)))) {
				Log.log(Log.INFO, "ReportDAO", "getListOfClaimRefNumbers()",
						"From Date is NULL, Status is Pending or Forward or Hold");
				/*
				 * query modification@sudeep.dhiman to get resultset in sorted
				 * order
				 */
				query = "select m.mem_bank_name bnkname, m.mem_bnk_id || m.mem_zne_id || m.mem_brn_id, c.clm_ref_no"
						+ " from claim_detail_temp c, member_info m"
						+ " where c.clm_date between ? and ?"
						+ " and LTRIM(RTRIM(UPPER(c.mem_bnk_id || c.mem_zne_id || c.mem_brn_id))) = LTRIM(RTRIM(UPPER(m.mem_bnk_id || m.mem_zne_id || m.mem_brn_id))) "
						+ " and c.clm_status = ?"
						+ " and m.mem_bnk_id = ?"
						+ " and (m.mem_reporting_zone_id = ? or m.mem_zne_id = ?) "
						+ " group by m.mem_bank_name,"
						+ " m.mem_bnk_id || m.mem_zne_id || m.mem_brn_id,"
						+ " c.clm_ref_no order by bnkname";
				prepStatement = conn.prepareStatement(query);
				prepStatement.setDate(1, fromDate);
				prepStatement.setDate(2, toDate);
				prepStatement.setString(3, clmApplicationStatusFlag);
				prepStatement.setString(4, bankId);
				prepStatement.setString(5, zoneId);
				prepStatement.setString(6, zoneId);
				rs = (ResultSet) prepStatement.executeQuery();
			}
			if ((fromDate == null)
					&& (!(clmApplicationStatusFlag
							.equals(ClaimConstants.CLM_PENDING_STATUS)
							|| clmApplicationStatusFlag
									.equals(ClaimConstants.CLM_HOLD_STATUS) || clmApplicationStatusFlag
							.equals(ClaimConstants.CLM_FORWARD_STATUS)))) {
				if (clmApplicationStatusFlag
						.equals(ClaimConstants.CLM_APPROVAL_STATUS)) {
					Log.log(Log.INFO, "ReportDAO",
							"getListOfClaimRefNumbers()",
							"From Date is NULL, Status is Approval");
					/*
					 * query modification@sudeep.dhiman to get resultset in
					 * sorted order
					 */
					query = "select m.mem_bank_name bnkname, m.mem_bnk_id || m.mem_zne_id || m.mem_brn_id, c.clm_ref_no, c.cgclan"
							+ " from claim_detail c, member_info m"
							+ " where c.clm_date <= ?"
							+ " and LTRIM(RTRIM(UPPER(c.mem_bnk_id || c.mem_zne_id || c.mem_brn_id))) = LTRIM(RTRIM(UPPER(m.mem_bnk_id || m.mem_zne_id || m.mem_brn_id))) "
							+ " and c.clm_status = ?"
							+ " and m.mem_bnk_id = ?"
							+ " and (m.mem_reporting_zone_id = ? or m.mem_zne_id = ?) "
							+ " group by m.mem_bank_name,"
							+ " m.mem_bnk_id || m.mem_zne_id || m.mem_brn_id,"
							+ " c.clm_ref_no," + " c.cgclan order by bnkname";
					prepStatement = conn.prepareStatement(query);
					// prepStatement.setDate(1,fromDate);
					prepStatement.setDate(1, toDate);
					prepStatement.setString(2, clmApplicationStatusFlag);
					prepStatement.setString(3, bankId);
					prepStatement.setString(4, zoneId);
					prepStatement.setString(5, zoneId);
					rs = (ResultSet) prepStatement.executeQuery();
				} else if (clmApplicationStatusFlag
						.equals(ClaimConstants.CLM_REJECT_STATUS)) {
					Log.log(Log.INFO, "ReportDAO",
							"getListOfClaimRefNumbers()",
							"From Date is NULL, Status is Reject");
					/*
					 * query modification@sudeep.dhiman to get resultset in
					 * sorted order
					 */
					query = "select m.mem_bank_name bnkname, m.mem_bnk_id || m.mem_zne_id || m.mem_brn_id, c.clm_ref_no"
							+ " from claim_detail c, member_info m"
							+ " where c.clm_date <= ?"
							+ " and LTRIM(RTRIM(UPPER(c.mem_bnk_id || c.mem_zne_id || c.mem_brn_id))) = LTRIM(RTRIM(UPPER(m.mem_bnk_id || m.mem_zne_id || m.mem_brn_id))) "
							+ " and c.clm_status = ?"
							+ " and m.mem_bnk_id = ?"
							+ " and (m.mem_reporting_zone_id = ? or m.mem_zne_id = ?) "
							+ " group by m.mem_bank_name,"
							+ " m.mem_bnk_id || m.mem_zne_id || m.mem_brn_id,"
							+ " c.clm_ref_no order by bnkname";
					prepStatement = conn.prepareStatement(query);
					prepStatement.setDate(1, toDate);
					prepStatement.setString(2, clmApplicationStatusFlag);
					prepStatement.setString(3, bankId);
					prepStatement.setString(4, zoneId);
					prepStatement.setString(5, zoneId);
					rs = (ResultSet) prepStatement.executeQuery();
				}
			}
			if ((fromDate == null)
					&& ((clmApplicationStatusFlag
							.equals(ClaimConstants.CLM_PENDING_STATUS)
							|| clmApplicationStatusFlag
									.equals(ClaimConstants.CLM_HOLD_STATUS) || clmApplicationStatusFlag
							.equals(ClaimConstants.CLM_FORWARD_STATUS)))) {
				Log.log(Log.INFO, "ReportDAO", "getListOfClaimRefNumbers()",
						"From Date is NULL, Status is Pending or Forward or Hold");
				/*
				 * query modification@sudeep.dhiman to get resultset in sorted
				 * order
				 */
				query = "select m.mem_bank_name bnkname, m.mem_bnk_id || m.mem_zne_id || m.mem_brn_id, c.clm_ref_no"
						+ " from claim_detail_temp c, member_info m"
						+ " where c.clm_date <= ?"
						+ " and LTRIM(RTRIM(UPPER(c.mem_bnk_id || c.mem_zne_id || c.mem_brn_id))) = LTRIM(RTRIM(UPPER(m.mem_bnk_id || m.mem_zne_id || m.mem_brn_id))) "
						+ " and c.clm_status = ?"
						+ " and m.mem_bnk_id = ?"
						+ " and (m.mem_reporting_zone_id = ? or m.mem_zne_id = ?) "
						+ " group by m.mem_bank_name,"
						+ " m.mem_bnk_id || m.mem_zne_id || m.mem_brn_id,"
						+ " c.clm_ref_no order by bnkname";
				prepStatement = conn.prepareStatement(query);
				prepStatement.setDate(1, toDate);
				prepStatement.setString(2, clmApplicationStatusFlag);
				prepStatement.setString(3, bankId);
				prepStatement.setString(4, zoneId);
				prepStatement.setString(5, zoneId);
				rs = (ResultSet) prepStatement.executeQuery();
			}
			String memberBankName = null;
			String memberId = null;
			String clmRefNumber = null;
			String cgclan = "";
			ClaimDetail clmDtl = null;

			if (clmApplicationStatusFlag
					.equals(ClaimConstants.CLM_APPROVAL_STATUS)) {
				while (rs.next()) {
					memberBankName = (String) rs.getString(1);
					memberId = (String) rs.getString(2);
					clmRefNumber = (String) rs.getString(3);
					cgclan = (String) rs.getString(4);
					clmDtl = new ClaimDetail();
					clmDtl.setMliName(memberBankName);
					clmDtl.setMliId(memberId);
					clmDtl.setClaimRefNum(clmRefNumber);
					clmDtl.setCGCLAN(cgclan);
					// Adding the ClaimDetail object to the vector
					clmRefNumbersList.addElement(clmDtl);
				}
				rs.close();
				rs = null;
			} else {
				while (rs.next()) {
					memberBankName = (String) rs.getString(1);
					memberId = (String) rs.getString(2);
					clmRefNumber = (String) rs.getString(3);
					clmDtl = new ClaimDetail();
					clmDtl.setMliName(memberBankName);
					clmDtl.setMliId(memberId);
					clmDtl.setClaimRefNum(clmRefNumber);
					clmDtl.setCGCLAN(cgclan);
					// Adding the ClaimDetail object to the vector
					clmRefNumbersList.addElement(clmDtl);
				}
				rs.close();
				rs = null;
			}
			prepStatement.close();
			prepStatement = null;
		} catch (SQLException sqlexception) {
			throw new DatabaseException(sqlexception.getMessage());
		} finally {
			DBConnection.freeConnection(conn);
		}
		return clmRefNumbersList;
	}

	public Vector getListOfClaimRefNumbersNew2(java.sql.Date fromDate,
			java.sql.Date toDate, String clmApplicationStatusFlag, String bankId)
			throws DatabaseException {

		Log.log(Log.INFO, "ReportDAO", "getListOfClaimRefNumbersNew()",
				"Entered");
		Log.log(Log.INFO, "ReportDAO", "getListOfClaimRefNumbersNew()",
				"From Date :" + fromDate);
		Log.log(Log.INFO, "ReportDAO", "getListOfClaimRefNumbersNew()",
				"To Date :" + toDate);
		Log.log(Log.INFO, "ReportDAO", "getListOfClaimRefNumbersNew()",
				"clmApplicationStatusFlag :" + clmApplicationStatusFlag);
		Connection conn = null;
		PreparedStatement prepStatement = null;
		ResultSet rs = null;
		Vector clmRefNumbersList = new Vector();
		String query = null;

		try {
			conn = DBConnection.getConnection();

			if ((fromDate != null)
					&& (!(clmApplicationStatusFlag
							.equals(ClaimConstants.CLM_PENDING_STATUS) || clmApplicationStatusFlag
							.equals(ClaimConstants.CLM_HOLD_STATUS)))) {
				if (clmApplicationStatusFlag
						.equals(ClaimConstants.CLM_APPROVAL_STATUS)) {
					Log.log(Log.INFO, "ReportDAO",
							"getListOfClaimRefNumbers()",
							"From Date is NULL, Status is Approval");

					// new mod query kot1
					query = "SELECT m.mem_bnk_id || m.mem_zne_id || m.mem_brn_id memberid, m.mem_bank_name bankname, m.mem_zone_name,\n"
							+ "         DECODE (m.MEM_BRANCH_NAME,\n"
							+ "                 NULL, a.APP_MLI_BRANCH_NAME,\n"
							+ "                 m.MEM_BRANCH_NAME)\n"
							+ "            BRANCH,a.cgpan,S.SSI_UNIT_NAME, DECODE (a.app_reapprove_amount,\n"
							+ "                 NULL, a.APP_approved_amount,\n"
							+ "                 a.app_reapprove_amount)\n"
							+ "            GuarntAmt, c.clm_ref_no, c.cgclan,  c.CLM_DATE claimFilDt,ctd_tc_first_inst_pay_amt clmApprvdAmt,TRUNC (C.CLM_APPROVED_DT) clmApprovdDt\n"
							+ "                   FROM claim_detail c,\n"
							+ "         member_info m,\n"
							+ "         SSI_DETAIL S,\n"
							+ "         application_detail a,\n"
							+ "         claim_tc_detail ct\n"
							+ "   WHERE     TRUNC (c.clm_approved_dt) BETWEEN ? AND ?  \n"
							+ "         AND C.BID = S.BID\n"
							+ "         AND s.ssi_reference_number = a.ssi_reference_number\n"
							+ "         AND c.mem_bnk_id || c.mem_brn_id || c.mem_zne_id =\n"
							+ "                a.mem_bnk_id || a.mem_brn_id || a.mem_zne_id\n"
							+ "         AND LTRIM (\n"
							+ "                RTRIM (UPPER (c.mem_bnk_id || c.mem_zne_id || c.mem_brn_id))) =\n"
							+ "                LTRIM (\n"
							+ "                   RTRIM (UPPER (m.mem_bnk_id || m.mem_zne_id || m.mem_brn_id)))\n"
							+ "         AND c.clm_status = ? \n"
							+ "   and c.mem_bnk_id = ? \n"
							+ "         and ct.clm_ref_no = c.clm_ref_no\n"
							+ "         and ct.cgpan = a.cgpan\n"
							+ "         and A.APP_LOAN_TYPE in ('TC')   \n"
							+ "         union all\n"
							+ "          SELECT m.mem_bnk_id || m.mem_zne_id || m.mem_brn_id memberid, m.mem_bank_name bankname, m.mem_zone_name,\n"
							+ "         DECODE (m.MEM_BRANCH_NAME,\n"
							+ "                 NULL, a.APP_MLI_BRANCH_NAME,\n"
							+ "                 m.MEM_BRANCH_NAME)\n"
							+ "            BRANCH,a.cgpan,S.SSI_UNIT_NAME, DECODE (a.app_reapprove_amount,\n"
							+ "                 NULL, a.APP_approved_amount,\n"
							+ "                 a.app_reapprove_amount)\n"
							+ "            GuarntAmt,c.clm_ref_no, c.cgclan,  c.CLM_DATE claimFilDt,cwd_wc_first_inst_pay_amt clmApprvdAmt,TRUNC (C.CLM_APPROVED_DT) clmApprovdDt\n"
							+ "    FROM claim_detail c,\n"
							+ "         member_info m,\n"
							+ "         SSI_DETAIL S,\n"
							+ "         application_detail a,\n"
							+ "         claim_wc_detail cw\n"
							+ "   WHERE     TRUNC (c.clm_approved_dt) BETWEEN ? AND ? \n"
							+ "         AND C.BID = S.BID\n"
							+ "         AND s.ssi_reference_number = a.ssi_reference_number\n"
							+ "         AND c.mem_bnk_id || c.mem_brn_id || c.mem_zne_id =\n"
							+ "                a.mem_bnk_id || a.mem_brn_id || a.mem_zne_id\n"
							+ "         AND LTRIM (\n"
							+ "                RTRIM (UPPER (c.mem_bnk_id || c.mem_zne_id || c.mem_brn_id))) =\n"
							+ "                LTRIM (\n"
							+ "                   RTRIM (UPPER (m.mem_bnk_id || m.mem_zne_id || m.mem_brn_id)))\n"
							+ "         AND c.clm_status = ? \n"
							+ "  and c.mem_bnk_id = ? \n"
							+ "         and cw.clm_ref_no = c.clm_ref_no\n"
							+ "         and cw.cgpan = a.cgpan\n"
							+ "         and app_loan_type in ('WC')  \n"
							+ "union all\n"
							+ " SELECT m.mem_bnk_id || m.mem_zne_id || m.mem_brn_id memberid, m.mem_bank_name bankname, m.mem_zone_name,\n"
							+ "         DECODE (m.MEM_BRANCH_NAME,\n"
							+ "                 NULL, a.APP_MLI_BRANCH_NAME,\n"
							+ "                 m.MEM_BRANCH_NAME)\n"
							+ "            BRANCH,a.cgpan,S.SSI_UNIT_NAME, DECODE (a.app_reapprove_amount,\n"
							+ "                 NULL, a.APP_approved_amount,\n"
							+ "                 a.app_reapprove_amount)\n"
							+ "            GuarntAmt,c.clm_ref_no, c.cgclan,  c.CLM_DATE claimFilDt,  nvl(cwd_wc_first_inst_pay_amt,0)+nvl(ctd_tc_first_inst_pay_amt,0) clmApprvdAmt,TRUNC (C.CLM_APPROVED_DT) clmApprovdDt\n"
							+ "    FROM claim_detail c,\n"
							+ "         member_info m,\n"
							+ "         SSI_DETAIL S,\n"
							+ "         application_detail a,\n"
							+ "         claim_wc_detail cw,\n"
							+ "         claim_tc_detail ct\n"
							+ "   WHERE     TRUNC (c.clm_approved_dt) BETWEEN ? AND ?\n"
							+ "         AND C.BID = S.BID\n"
							+ "         AND s.ssi_reference_number = a.ssi_reference_number\n"
							+ "         AND c.mem_bnk_id || c.mem_brn_id || c.mem_zne_id =\n"
							+ "                a.mem_bnk_id || a.mem_brn_id || a.mem_zne_id\n"
							+ "         AND LTRIM (\n"
							+ "                RTRIM (UPPER (c.mem_bnk_id || c.mem_zne_id || c.mem_brn_id))) =\n"
							+ "                LTRIM (\n"
							+ "                   RTRIM (UPPER (m.mem_bnk_id || m.mem_zne_id || m.mem_brn_id)))\n"
							+ "         AND c.clm_status = ? \n"
							+ "  and c.mem_bnk_id = ? \n"
							+ "         and cw.clm_ref_no = c.clm_ref_no\n"
							+ "         and cw.cgpan = a.cgpan\n"
							+ "         and ct.clm_ref_no = c.clm_ref_no\n"
							+ "         and ct.cgpan = a.cgpan\n"
							+ "         and app_loan_type in ('CC')   order by clmApprovdDt,bankname ";

					prepStatement = conn.prepareStatement(query);
					prepStatement.setDate(1, fromDate);
					prepStatement.setDate(2, toDate);
					prepStatement.setString(3, clmApplicationStatusFlag);

					prepStatement.setString(4, bankId);

					prepStatement.setDate(5, fromDate);
					prepStatement.setDate(6, toDate);
					prepStatement.setString(7, clmApplicationStatusFlag);

					prepStatement.setString(8, bankId);
					prepStatement.setDate(9, fromDate);
					prepStatement.setDate(10, toDate);
					prepStatement.setString(11, clmApplicationStatusFlag);

					prepStatement.setString(12, bankId);
					rs = (ResultSet) prepStatement.executeQuery();
				}

				// kotttttt

				if (clmApplicationStatusFlag
						.equals(ClaimConstants.CLM_TEMPORARY_CLOSE)) {
					// kot5
					Log.log(Log.INFO, "ReportDAO",
							"getListOfClaimRefNumbers()",
							"From Date is NULL, Status is Reject");

					query = "SELECT a.mem_bnk_id || a.mem_zne_id || a.mem_brn_id,  \n"
							+ "                                d.mem_bank_name,  \n"
							+ "                                d.mem_zone_name,  \n"
							+ "                                DECODE (d.MEM_BRANCH_NAME,  \n"
							+ "                                        NULL, e.APP_MLI_BRANCH_NAME,  \n"
							+ "                                        d.MEM_BRANCH_NAME)  \n"
							+ "                                   branch,  \n"
							+ "                                e.cgpan,  \n"
							+ "                                c.ssi_unit_name,  \n"
							+ "                                DECODE (e.app_reapprove_amount,  \n"
							+ "                                        NULL, e.APP_approved_amount,  \n"
							+ "                                        e.app_reapprove_amount)  \n"
							+ "                                   guarantamt,  \n"
							+ "                                A.clm_ref_no,  \n"
							+ "                                a.clm_date,  \n"
							+ "                                b.caa_applied_amount clmAppdAmt,TRUNC(a.CLM_CREATED_MODIFIED_DT), \n"
							+ "                                (select distinct max(trim(upper(replace(clm_ltr_ref_no,' ',''))))\n"
							+ "                                from claim_query_detail cqd\n"
							+ "                                where a.clm_ref_no = cqd.clm_ref_no\n"
							+ "                                and clm_ltr_dt in \n"
							+ "                                (select max(clm_ltr_dt) from claim_query_detail cd where cd.clm_ref_no = cqd.clm_ref_no)\n"
							+ "                                ) clmqryrefno,\n"
							+ "                                (select distinct clm_ltr_dt\n"
							+ "                                from claim_query_detail cq\n"
							+ "                                where a.clm_ref_no = cq.clm_ref_no\n"
							+ "                                and clm_ltr_dt in \n"
							+ "                                (select max(clm_ltr_dt) from claim_query_detail cd where cd.clm_ref_no = cq.clm_ref_no)\n"
							+ "                                ) clmqrydt  \n"
							+ "                           FROM claim_detail_temp a,  \n"
							+ "                                claim_application_amount_temp b,  \n"
							+ "                                ssi_detail c,  \n"
							+ "                                member_info d,  \n"
							+ "                                application_detail e  \n"
							+ "                          WHERE     b.cgpan = e.cgpan  \n"
							+ "                                AND a.clm_ref_no = b.clm_ref_no  \n"
							+ "                                AND a.bid = c.bid  \n"
							+

							"                                AND a.mem_bnk_id || a.mem_zne_id || a.mem_brn_id =  \n"
							+ "                                       d.mem_bnk_id || d.mem_zne_id || d.mem_brn_id  \n"
							+ "                               AND TRUNC (CLM_CREATED_MODIFIED_DT) BETWEEN ?  AND ?   \n"
							+ "                                AND a.clm_status = ?      and a.mem_bnk_id = ?   order by TRUNC(CLM_CREATED_MODIFIED_DT),d.mem_bank_name ";

					prepStatement = conn.prepareStatement(query);
					prepStatement.setDate(1, fromDate);
					prepStatement.setDate(2, toDate);
					prepStatement.setString(3, clmApplicationStatusFlag);
					prepStatement.setString(4, bankId);

					rs = (ResultSet) prepStatement.executeQuery();
				}

				if (clmApplicationStatusFlag.equals("RT")) {
					// kot9
					query = " SELECT  m.mem_bnk_id || m.mem_zne_id || m.mem_brn_id memberid, m.mem_bank_name bnkname,mem_zone_name zone,\n"
							+ "         decode(mem_branch_name,null,app_mli_branch_name,mem_branch_name) branch,\n"
							+ "                 a.cgpan, S.SSI_UNIT_NAME,\n"
							+ "         decode(nvl(app_reapprove_amount,0),0,app_approved_amount,app_Reapprove_amount) appamt,\n"
							+ "         c.clm_ref_no,\n"
							+ "                 caa_applied_amount clmedamt,\n"
							+ "         TRUNC (CLM_CREATED_MODIFIED_DT) rtdate,\n"
							+ "         clm_return_remarks\n"
							+ "    FROM claim_detail_temp c,\n"
							+ "         member_info m,\n"
							+ "         SSI_DETAIL S,\n"
							+ "         claim_application_amount_temp caa,\n"
							+ "         application_detail a\n"
							+ "   WHERE     TRUNC(C.CLM_CREATED_MODIFIED_DT) between ?  and ?  AND\n"
							+ "             C.BID = S.BID\n"
							+ "         AND c.clm_ref_no = caa.clm_ref_no\n"
							+ "         AND caa.cgpan = a.cgpan\n"
							+ "         AND LTRIM (\n"
							+ "                RTRIM (UPPER (c.mem_bnk_id || c.mem_zne_id || c.mem_brn_id))) =\n"
							+ "                LTRIM (\n"
							+ "                   RTRIM (UPPER (m.mem_bnk_id || m.mem_zne_id || m.mem_brn_id)))\n"
							+ "         AND c.clm_status = ?  \n"
							+ "    and c.mem_bnk_id = ?  \n" +

							"ORDER BY TRUNC (CLM_CREATED_MODIFIED_DT), bnkname";

					prepStatement = conn.prepareStatement(query);
					prepStatement.setDate(1, fromDate);
					prepStatement.setDate(2, toDate);
					prepStatement.setString(3, clmApplicationStatusFlag);
					prepStatement.setString(4, bankId);
					rs = (ResultSet) prepStatement.executeQuery();
				}

				if (clmApplicationStatusFlag.equals("AS")) {
					// kot11

					query = " SELECT  m.mem_bnk_id || m.mem_zne_id || m.mem_brn_id memberid, m.mem_bank_name bnkname,mem_zone_name zone,\n"
							+ "         decode(mem_branch_name,null,app_mli_branch_name,mem_branch_name) branch,\n"
							+ "                 a.cgpan, S.SSI_UNIT_NAME,\n"
							+ "         decode(nvl(app_reapprove_amount,0),0,app_approved_amount,app_Reapprove_amount) appamt,\n"
							+ "         c.clm_ref_no,\n"
							+ "                 caa_applied_amount clmApplAmt,CLM_DATE,\n"
							+ "         clm_status,\n"
							+ "clm_decl_recvd_dt,TRUNC(c.CLM_CREATED_MODIFIED_DT)  withdrawndt  \n"
							+ "         \n"
							+ "    FROM claim_detail_temp c,\n"
							+ "         member_info m,\n"
							+ "         SSI_DETAIL S,\n"
							+ "         claim_application_amount_temp caa,\n"
							+ "         application_detail a\n"
							+ "   WHERE     TRUNC(C.CLM_date) between ?  and ?   AND\n"
							+ "   c.mem_bnk_id = ?   and   \n"
							+ "             C.BID = S.BID\n"
							+ "         AND c.clm_ref_no = caa.clm_ref_no\n"
							+ "         AND caa.cgpan = a.cgpan\n"
							+ "         AND LTRIM (\n"
							+ "                RTRIM (UPPER (c.mem_bnk_id || c.mem_zne_id || c.mem_brn_id))) =\n"
							+ "                LTRIM (\n"
							+ "                   RTRIM (UPPER (m.mem_bnk_id || m.mem_zne_id || m.mem_brn_id)))  \n"
							+ "        \n"
							+ " union  all \n"
							+ " \n"
							+ "  SELECT  m.mem_bnk_id || m.mem_zne_id || m.mem_brn_id memberid, m.mem_bank_name bnkname,mem_zone_name zone,\n"
							+ "         decode(mem_branch_name,null,app_mli_branch_name,mem_branch_name) branch,\n"
							+ "                 a.cgpan, S.SSI_UNIT_NAME,\n"
							+ "         decode(nvl(app_reapprove_amount,0),0,app_approved_amount,app_Reapprove_amount) appamt,\n"
							+ "         c.clm_ref_no,\n"
							+ "                 caa_applied_amount clmApplAmt,CLM_DATE,\n"
							+ "         clm_status,\n"
							+ "clm_decl_recvd_dt,TRUNC(c.CLM_CREATED_MODIFIED_DT) withdrawndt \n"
							+ "        \n"
							+ "    FROM claim_detail c,\n"
							+ "         member_info m,\n"
							+ "         SSI_DETAIL S,\n"
							+ "         claim_application_amount caa,\n"
							+ "         application_detail a\n"
							+ "   WHERE     TRUNC(C.CLM_date) between ?  and ?  AND\n"
							+ "   c.mem_bnk_id = ?   and   \n"
							+ "             C.BID = S.BID\n"
							+ "         AND c.clm_ref_no = caa.clm_ref_no\n"
							+ "         AND caa.cgpan = a.cgpan\n"
							+ "         AND LTRIM (\n"
							+ "                RTRIM (UPPER (c.mem_bnk_id || c.mem_zne_id || c.mem_brn_id))) =\n"
							+ "                LTRIM (\n"
							+ "                   RTRIM (UPPER (m.mem_bnk_id || m.mem_zne_id || m.mem_brn_id)))  order by withdrawndt, bnkname \n"
							+ "        ";

					prepStatement = conn.prepareStatement(query);
					prepStatement.setDate(1, fromDate);
					prepStatement.setDate(2, toDate);
					prepStatement.setString(3, bankId);
					prepStatement.setDate(4, fromDate);
					prepStatement.setDate(5, toDate);
					prepStatement.setString(6, bankId);
					// prepStatement.setString(3,clmApplicationStatusFlag);

					rs = (ResultSet) prepStatement.executeQuery();
				}

				if (clmApplicationStatusFlag.equals("RTD")) {
					// kot10
					query = "SELECT a.mem_bnk_id || a.mem_zne_id || a.mem_brn_id memberid,  \n"
							+ "                                d.mem_bank_name bnkname,  \n"
							+ "                                d.mem_zone_name,  \n"
							+ "                                DECODE (d.MEM_BRANCH_NAME,  \n"
							+ "                                        NULL, e.APP_MLI_BRANCH_NAME,  \n"
							+ "                                        d.MEM_BRANCH_NAME)  \n"
							+ "                                   branch,                                  e.cgpan,                                  c.ssi_unit_name,  \n"
							+ "                                DECODE (e.app_reapprove_amount,  \n"
							+ "                                        NULL, e.APP_approved_amount,  \n"
							+ "                                        e.app_reapprove_amount)  \n"
							+ "                                   guarantamt,  \n"
							+ "                                A.clm_ref_no,  \n"
							+ "                                a.clm_date,  \n"
							+ "                                b.caa_applied_amount clmApplAmt,TRUNC(a.CLM_CREATED_MODIFIED_DT) dt \n"
							+ "                           FROM claim_detail_temp a,  \n"
							+ "                                claim_application_amount_temp b,  \n"
							+ "                                ssi_detail c,  \n"
							+ "                                member_info d,  \n"
							+ "                                application_detail e  \n"
							+ "                          WHERE     b.cgpan = e.cgpan  \n"
							+ "                                AND a.clm_ref_no = b.clm_ref_no  \n"
							+ "                                AND a.bid = c.bid  \n"
							+ "                                AND a.mem_bnk_id || a.mem_zne_id || a.mem_brn_id =  \n"
							+ "                                       d.mem_bnk_id || d.mem_zne_id || d.mem_brn_id  \n"
							+ "                            AND TRUNC (a.CLM_CREATED_MODIFIED_DT) bETWEEN ?  AND ?   \n"
							+ "                                  and a.mem_bnk_id = ?   and   \n"
							+ "                                AND a.clm_status in ('RE','TR')    union all \n"
							+ " SELECT a.mem_bnk_id || a.mem_zne_id || a.mem_brn_id memberid,d.mem_bank_name bnkname,d.mem_zone_name, DECODE (d.MEM_BRANCH_NAME,\n"
							+ "                                           NULL, e.APP_MLI_BRANCH_NAME, \n"
							+ "                                            d.MEM_BRANCH_NAME)\n"
							+ "                                       branch,e.cgpan,c.ssi_unit_name, DECODE (e.app_reapprove_amount,\n"
							+ "                                            NULL, e.APP_approved_amount,\n"
							+ "                                            e.app_reapprove_amount)\n"
							+ "                                       guarantamt,A.clm_ref_no, a.clm_date,b.caa_applied_amount clmApplAmt,TRUNC(a.CLM_CREATED_MODIFIED_DT) dt \n"
							+ "                                                 FROM claim_detail a,\n"
							+ "                                    claim_application_amount b,\n"
							+ "                                    ssi_detail c,\n"
							+ "                                    member_info d,\n"
							+ "                                    application_detail e\n"
							+ "                              WHERE   \n"
							+ "                                   b.cgpan = e.cgpan\n"
							+ "                                    AND a.clm_ref_no = b.clm_ref_no\n"
							+ "                                    AND a.bid = c.bid\n"
							+ "                                    AND a.mem_bnk_id || a.mem_zne_id || a.mem_brn_id =\n"
							+ "                                           d.mem_bnk_id || d.mem_zne_id || d.mem_brn_id\n"
							+ "                                    AND TRUNC (a.CLM_CREATED_MODIFIED_DT) BETWEEN ?  AND ? \n"
							+ "                                        a.mem_bnk_id = ?   and   \n"
							+ "                                    AND a.clm_status in ('RE','TR')  order by dt,bnkname ";

					prepStatement = conn.prepareStatement(query);
					prepStatement.setDate(1, fromDate);
					prepStatement.setDate(2, toDate);
					prepStatement.setString(3, bankId);
					prepStatement.setDate(4, fromDate);
					prepStatement.setDate(5, toDate);
					prepStatement.setString(6, bankId);
					// prepStatement.setString(3,clmApplicationStatusFlag);

					rs = (ResultSet) prepStatement.executeQuery();
				}

				if (clmApplicationStatusFlag
						.equals(ClaimConstants.CLM_TEMPORARY_REJECT)) {
					// kot6
					Log.log(Log.INFO, "ReportDAO",
							"getListOfClaimRefNumbers()",
							"From Date is NULL, Status is Reject");

					query = "SELECT a.mem_bnk_id || a.mem_zne_id || a.mem_brn_id memberid,  \n"
							+ "                                d.mem_bank_name,  \n"
							+ "                                d.mem_zone_name,  \n"
							+ "                                DECODE (d.MEM_BRANCH_NAME,  \n"
							+ "                                        NULL, e.APP_MLI_BRANCH_NAME,  \n"
							+ "                                        d.MEM_BRANCH_NAME)  \n"
							+ "                                   branch,  \n"
							+ "                                e.cgpan,  \n"
							+ "                                c.ssi_unit_name,  \n"
							+ "                                DECODE (e.app_reapprove_amount,  \n"
							+ "                                        NULL, e.APP_approved_amount,  \n"
							+ "                                        e.app_reapprove_amount)  \n"
							+ "                                   guarantamt,  \n"
							+ "                                A.clm_ref_no,  \n"
							+ "                                a.clm_date,  \n"
							+ "                                b.caa_applied_amount clmRejAmt,TRUNC(a.CLM_CREATED_MODIFIED_DT),clm_return_remarks \n"
							+ "                           FROM claim_detail_temp a,  \n"
							+ "                                claim_application_amount_temp b,  \n"
							+ "                                ssi_detail c,  \n"
							+ "                                member_info d,  \n"
							+ "                                application_detail e  \n"
							+ "                          WHERE     b.cgpan = e.cgpan  \n"
							+ "                                AND a.clm_ref_no = b.clm_ref_no  \n"
							+ "                                AND a.bid = c.bid  \n"
							+ "                                AND a.mem_bnk_id || a.mem_zne_id || a.mem_brn_id =  \n"
							+ "                                       d.mem_bnk_id || d.mem_zne_id || d.mem_brn_id  \n"
							+ "                               AND TRUNC (CLM_CREATED_MODIFIED_DT) BETWEEN ?  AND ?   \n"
							+ "                                AND a.clm_status = ?     and   a.mem_bnk_id = ?      order by TRUNC(CLM_CREATED_MODIFIED_DT), d.mem_bank_name ";
					prepStatement = conn.prepareStatement(query);
					prepStatement.setDate(1, fromDate);
					prepStatement.setDate(2, toDate);
					prepStatement.setString(3, clmApplicationStatusFlag);
					prepStatement.setString(4, bankId);

					rs = (ResultSet) prepStatement.executeQuery();
				}
				if (clmApplicationStatusFlag
						.equals(ClaimConstants.CLM_WITHDRAWN)) {
					// kot7
					Log.log(Log.INFO, "ReportDAO",
							"getListOfClaimRefNumbers()",
							"From Date is NULL, Status is Reject");

					query = "SELECT a.mem_bnk_id || a.mem_zne_id || a.mem_brn_id memberid,  \n"
							+ "                                d.mem_bank_name bnkname,  \n"
							+ "                                d.mem_zone_name,  \n"
							+ "                                DECODE (d.MEM_BRANCH_NAME,  \n"
							+ "                                        NULL, e.APP_MLI_BRANCH_NAME,  \n"
							+ "                                        d.MEM_BRANCH_NAME)  \n"
							+ "                                   branch,  \n"
							+ "                                e.cgpan,  \n"
							+ "                                c.ssi_unit_name,  \n"
							+ "                                DECODE (e.app_reapprove_amount,  \n"
							+ "                                        NULL, e.APP_approved_amount,  \n"
							+ "                                        e.app_reapprove_amount)  \n"
							+ "                                   guarantamt,  \n"
							+ "                                A.clm_ref_no,  \n"
							+ "                                a.clm_date,  \n"
							+ "                                b.caa_applied_amount clmAPPAmt , TRUNC(a.CLM_CREATED_MODIFIED_DT) withdrawndt \n"
							+ "                           FROM claim_detail_temp a,  \n"
							+ "                                claim_application_amount_temp b,  \n"
							+ "                                ssi_detail c,  \n"
							+ "                                member_info d,  \n"
							+ "                                application_detail e  \n"
							+ "                          WHERE     b.cgpan = e.cgpan  \n"
							+ "                                AND a.clm_ref_no = b.clm_ref_no  \n"
							+ "                                AND a.bid = c.bid  \n"
							+ "                                AND a.mem_bnk_id || a.mem_zne_id || a.mem_brn_id =  \n"
							+ "                                       d.mem_bnk_id || d.mem_zne_id || d.mem_brn_id  \n"
							+ "                               AND TRUNC (a.CLM_CREATED_MODIFIED_DT) BETWEEN ?  AND ?   \n"
							+ "                                AND a.clm_status = ?     \n"
							+ "    and   a.mem_bnk_id = ?   \n"
							+ "union all \n"
							+ "SELECT a.mem_bnk_id || a.mem_zne_id || a.mem_brn_id memberid,  \n"
							+ "                                d.mem_bank_name bnkname,  \n"
							+ "                                d.mem_zone_name,  \n"
							+ "                                DECODE (d.MEM_BRANCH_NAME,  \n"
							+ "                                        NULL, e.APP_MLI_BRANCH_NAME,  \n"
							+ "                                        d.MEM_BRANCH_NAME)  \n"
							+ "                                   branch,  \n"
							+ "                                e.cgpan,  \n"
							+ "                                c.ssi_unit_name,  \n"
							+ "                                DECODE (e.app_reapprove_amount,  \n"
							+ "                                        NULL, e.APP_approved_amount,  \n"
							+ "                                        e.app_reapprove_amount)  \n"
							+ "                                   guarantamt,  \n"
							+ "                                A.clm_ref_no,  \n"
							+ "                                a.clm_date,  \n"
							+ "                                b.caa_applied_amount clmappAmt,TRUNC(a.CLM_CREATED_MODIFIED_DT) withdrawndt  \n"
							+ "                           FROM claim_detail a,  \n"
							+ "                                claim_application_amount b,  \n"
							+ "                                ssi_detail c,  \n"
							+ "                                member_info d,  \n"
							+ "                                application_detail e  \n"
							+ "                          WHERE     b.cgpan = e.cgpan  \n"
							+ "                                AND a.clm_ref_no = b.clm_ref_no  \n"
							+ "                                AND a.bid = c.bid  \n"
							+ "                                AND a.mem_bnk_id || a.mem_zne_id || a.mem_brn_id =  \n"
							+ "                                       d.mem_bnk_id || d.mem_zne_id || d.mem_brn_id  \n"
							+ "                                AND TRUNC (CLM_CREATED_MODIFIED_DT) BETWEEN ?  AND ?   \n"
							+ "                                AND a.clm_status = ?      and   a.mem_bnk_id = ?   order by withdrawndt, bnkname  ";
					prepStatement = conn.prepareStatement(query);
					prepStatement.setDate(1, fromDate);
					prepStatement.setDate(2, toDate);
					prepStatement.setString(3, clmApplicationStatusFlag);
					prepStatement.setString(4, bankId);
					prepStatement.setDate(5, fromDate);
					prepStatement.setDate(6, toDate);
					prepStatement.setString(7, clmApplicationStatusFlag);
					prepStatement.setString(8, bankId);

					rs = (ResultSet) prepStatement.executeQuery();
				}

				if (clmApplicationStatusFlag
						.equals(ClaimConstants.CLM_REJECT_STATUS)) {
					// kot3
					Log.log(Log.INFO, "ReportDAO",
							"getListOfClaimRefNumbers()",
							"From Date is NULL, Status is Reject");

					query = "  SELECT a.mem_bnk_id || a.mem_zne_id || a.mem_brn_id,d.mem_bank_name,d.mem_zone_name, DECODE (d.MEM_BRANCH_NAME,\n"
							+ "                 NULL, e.APP_MLI_BRANCH_NAME,\n"
							+ "                 d.MEM_BRANCH_NAME)\n"
							+ "            branch,e.cgpan,c.ssi_unit_name, DECODE (e.app_reapprove_amount,\n"
							+ "                 NULL, e.APP_approved_amount,\n"
							+ "                 e.app_reapprove_amount)\n"
							+ "            guarantamt,A.clm_ref_no, a.clm_date,b.caa_applied_amount clmAppdAmt, TRUNC(a.CLM_CREATED_MODIFIED_DT)  \n"
							+ "                      FROM claim_detail a,\n"
							+ "         claim_application_amount b,\n"
							+ "         ssi_detail c,\n"
							+ "         member_info d,\n"
							+ "         application_detail e\n"
							+ "   WHERE   \n"
							+ "        b.cgpan = e.cgpan\n"
							+ "         AND a.clm_ref_no = b.clm_ref_no\n"
							+ "         AND a.bid = c.bid\n"
							+ "         AND a.mem_bnk_id || a.mem_zne_id || a.mem_brn_id =\n"
							+ "                d.mem_bnk_id || d.mem_zne_id || d.mem_brn_id\n"
							+ "         AND TRUNC (a.CLM_CREATED_MODIFIED_DT) BETWEEN ?  AND ? \n"
							+ "         AND a.clm_status = ?   and   a.mem_bnk_id = ?  order by TRUNC(CLM_CREATED_MODIFIED_DT), d.mem_bank_name  ";

					prepStatement = conn.prepareStatement(query);
					prepStatement.setDate(1, fromDate);
					prepStatement.setDate(2, toDate);
					prepStatement.setString(3, clmApplicationStatusFlag);
					prepStatement.setString(4, bankId);

					rs = (ResultSet) prepStatement.executeQuery();
				}
				// ADDED FORWARD REPORT BY SUKUMAR@PATH ON 12-09-2009
				if (clmApplicationStatusFlag
						.equals(ClaimConstants.CLM_FORWARD_STATUS)) {
					Log.log(Log.INFO, "ReportDAO",
							"getListOfClaimRefNumbers()",
							"From Date is NULL, Status is  Forward");
					// kot4
					query = "SELECT m.mem_bnk_id || m.mem_zne_id || m.mem_brn_id,\n"
							+ "       m.mem_bank_name bnkname,\n"
							+ "       m.mem_zone_name,\n"
							+ "       DECODE (m.MEM_BRANCH_NAME,\n"
							+ "               NULL, a.APP_MLI_BRANCH_NAME,\n"
							+ "               m.MEM_BRANCH_NAME)\n"
							+ "          BRANCH,\n"
							+ "       d.cgpan,\n"
							+ "       S.SSI_UNIT_NAME,\n"
							+ "       DECODE (a.app_reapprove_amount,\n"
							+ "               NULL, a.APP_approved_amount,\n"
							+ "               a.app_reapprove_amount)\n"
							+ "          guartAmt,\n"
							+ "       c.clm_ref_no,\n"
							+ "       C.CLM_DATE clmFilgDt,\n"
							+ "       d.ctd_tc_first_inst_pay_amt clmForwrdAmt, \n"
							+ "       TRUNC (CLM_CREATED_MODIFIED_DT) clmForwdDt \n"
							+ "  FROM claim_detail_temp c,\n"
							+ "       member_info m,\n"
							+ "       SSI_DETAIL S,\n"
							+ "       application_detail a,\n"
							+ "       CLAIM_TC_DETAIL_TEMP D\n"
							+ " WHERE     TRUNC (C.CLM_CREATED_MODIFIED_DT) BETWEEN ? \n"
							+ "                                                 AND  ? \n"
							+ "       AND C.BID = S.BID\n"
							+ "       AND s.ssi_reference_number = a.ssi_reference_number\n"
							+ "       AND c.mem_bnk_id || c.mem_brn_id || c.mem_zne_id =\n"
							+ "              a.mem_bnk_id || a.mem_brn_id || a.mem_zne_id\n"
							+ "       AND LTRIM (\n"
							+ "              RTRIM (UPPER (c.mem_bnk_id || c.mem_zne_id || c.mem_brn_id))) =\n"
							+ "              LTRIM (\n"
							+ "                 RTRIM (UPPER (m.mem_bnk_id || m.mem_zne_id || m.mem_brn_id)))\n"
							+ "       AND c.clm_status = ? \n"
							+ "  and   c.mem_bnk_id = ?   \n"
							+ "       AND C.CLM_REF_NO = D.CLM_REF_NO\n"
							+ "       AND d.cgpan = a.cgpan\n"
							+ "       AND app_loan_type IN ('TC')   \n"
							+ "UNION ALL\n"
							+ "SELECT m.mem_bnk_id || m.mem_zne_id || m.mem_brn_id,\n"
							+ "       m.mem_bank_name bnkname,\n"
							+ "       m.mem_zone_name,\n"
							+ "       DECODE (m.MEM_BRANCH_NAME,\n"
							+ "               NULL, a.APP_MLI_BRANCH_NAME,\n"
							+ "               m.MEM_BRANCH_NAME)\n"
							+ "          BRANCH,\n"
							+ "       d.cgpan,\n"
							+ "       S.SSI_UNIT_NAME,\n"
							+ "       DECODE (a.app_reapprove_amount,\n"
							+ "               NULL, a.APP_approved_amount,\n"
							+ "               a.app_reapprove_amount)\n"
							+ "          guartAmt,\n"
							+ "       c.clm_ref_no,\n"
							+ "       C.CLM_DATE clmFilgDt,\n"
							+ "       d.cwd_wc_first_inst_pay_amt clmForwrdAmt,\n"
							+ "       TRUNC (CLM_CREATED_MODIFIED_DT) clmForwdDt \n"
							+ "  FROM claim_detail_temp c,\n"
							+ "       member_info m,\n"
							+ "       SSI_DETAIL S,\n"
							+ "       application_detail a,\n"
							+ "       CLAIM_WC_DETAIL_TEMP D\n"
							+ " WHERE     TRUNC (C.CLM_CREATED_MODIFIED_DT) BETWEEN ? \n"
							+ "                                                 AND ?  \n"
							+ "       AND C.BID = S.BID\n"
							+ "       AND s.ssi_reference_number = a.ssi_reference_number\n"
							+ "       AND c.mem_bnk_id || c.mem_brn_id || c.mem_zne_id =\n"
							+ "              a.mem_bnk_id || a.mem_brn_id || a.mem_zne_id\n"
							+ "       AND LTRIM (\n"
							+ "              RTRIM (UPPER (c.mem_bnk_id || c.mem_zne_id || c.mem_brn_id))) =\n"
							+ "              LTRIM (\n"
							+ "                 RTRIM (UPPER (m.mem_bnk_id || m.mem_zne_id || m.mem_brn_id)))\n"
							+ "       AND c.clm_status = ? \n"
							+ "  and   c.mem_bnk_id = ?   \n"
							+ "       AND C.CLM_REF_NO = D.CLM_REF_NO\n"
							+ "       AND d.cgpan = a.cgpan\n"
							+ "       AND app_loan_type IN ('WC')   \n"
							+ "UNION ALL\n"
							+ "SELECT m.mem_bnk_id || m.mem_zne_id || m.mem_brn_id,\n"
							+ "       m.mem_bank_name bnkname,\n"
							+ "       m.mem_zone_name,\n"
							+ "       DECODE (m.MEM_BRANCH_NAME,\n"
							+ "               NULL, a.APP_MLI_BRANCH_NAME,\n"
							+ "               m.MEM_BRANCH_NAME)\n"
							+ "          BRANCH,\n"
							+ "       d.cgpan,\n"
							+ "       S.SSI_UNIT_NAME,\n"
							+ "       DECODE (a.app_reapprove_amount,\n"
							+ "               NULL, a.APP_approved_amount,\n"
							+ "               a.app_reapprove_amount)\n"
							+ "          guartAmt,\n"
							+ "       c.clm_ref_no,\n"
							+ "       C.CLM_DATE clmFilgDt,\n"
							+ "         NVL (d.cwd_wc_first_inst_pay_amt, 0)\n"
							+ "       + NVL (CWD_WC_FIRST_INST_PAY_AMT, 0)\n"
							+ "          clmForwrdAmt,\n"
							+ "       TRUNC (CLM_CREATED_MODIFIED_DT) clmForwdDt \n"
							+ "  FROM claim_detail_temp c,\n"
							+ "       member_info m,\n"
							+ "       SSI_DETAIL S,\n"
							+ "       application_detail a,\n"
							+ "       CLAIM_WC_DETAIL_TEMP D,\n"
							+ "       CLAIM_TC_DETAIL_TEMP E\n"
							+ " WHERE     TRUNC (C.CLM_CREATED_MODIFIED_DT) BETWEEN ? \n"
							+ "                                                 AND ? \n"
							+ "       AND C.BID = S.BID\n"
							+ "       AND s.ssi_reference_number = a.ssi_reference_number\n"
							+ "       AND c.mem_bnk_id || c.mem_brn_id || c.mem_zne_id =\n"
							+ "              a.mem_bnk_id || a.mem_brn_id || a.mem_zne_id\n"
							+ "       AND LTRIM (\n"
							+ "              RTRIM (UPPER (c.mem_bnk_id || c.mem_zne_id || c.mem_brn_id))) =\n"
							+ "              LTRIM (\n"
							+ "                 RTRIM (UPPER (m.mem_bnk_id || m.mem_zne_id || m.mem_brn_id)))\n"
							+ "       AND c.clm_status = ? \n"
							+ "  and   c.mem_bnk_id = ?   \n"
							+ "       AND C.CLM_REF_NO = D.CLM_REF_NO\n"
							+ "       AND D.cgpan = a.cgpan\n"
							+ "       AND C.CLM_REF_NO = E.CLM_REF_NO\n"
							+ "       AND E.cgpan = a.cgpan\n"
							+ "       AND app_loan_type IN ('CC') order by clmForwdDt, bnkname ";

					prepStatement = conn.prepareStatement(query);
					prepStatement.setDate(1, fromDate);
					prepStatement.setDate(2, toDate);
					prepStatement.setString(3, clmApplicationStatusFlag);
					prepStatement.setString(4, bankId);
					prepStatement.setDate(5, fromDate);
					prepStatement.setDate(6, toDate);
					prepStatement.setString(7, clmApplicationStatusFlag);
					prepStatement.setString(8, bankId);
					prepStatement.setDate(9, fromDate);
					prepStatement.setDate(10, toDate);
					prepStatement.setString(11, clmApplicationStatusFlag);
					prepStatement.setString(12, bankId);
					rs = (ResultSet) prepStatement.executeQuery();
				}
				/*
				 * ADDED BY SUKUMAR@PATH ON 20-FEB-2010 FOR DISPLAY TEMPORARY
				 * CLOSE & TEMPORARY REJECT APPLICATION
				 */
				if (clmApplicationStatusFlag.equals("KTC")
						|| clmApplicationStatusFlag.equals("KWC")
						|| clmApplicationStatusFlag.equals("KWD")) {
					Log.log(Log.INFO, "ReportDAO",
							"getListOfClaimRefNumbers()",
							"From Date is NULL, Status is  TEMPORARY CLOSED");
					// new kot
					query = "SELECT a.mem_bnk_id || a.mem_zne_id || a.mem_brn_id,\n"
							+ "       d.mem_bank_name,\n"
							+ "       d.mem_zone_name,\n"
							+ "       DECODE (d.MEM_BRANCH_NAME,\n"
							+ "               NULL, e.APP_MLI_BRANCH_NAME,\n"
							+ "               d.MEM_BRANCH_NAME)\n"
							+ "          branch,\n"
							+ "       e.cgpan,\n"
							+ "       c.ssi_unit_name,\n"
							+ "       DECODE (e.app_reapprove_amount,\n"
							+ "               NULL, e.APP_approved_amount,\n"
							+ "               e.app_reapprove_amount)\n"
							+ "          guarantamt,\n"
							+ "       A.clm_ref_no,\n"
							+ "       a.clm_date,\n"
							+ "       b.caa_applied_amount clmAppdAmt\n"
							+ "  FROM claim_detail_temp a,\n"
							+ "       claim_application_amount_temp b,\n"
							+ "       ssi_detail c,\n"
							+ "       member_info d,\n"
							+ "       application_detail e\n"
							+ " WHERE     b.cgpan = e.cgpan\n"
							+ "       AND a.clm_ref_no = b.clm_ref_no\n"
							+ "       AND a.bid = c.bid\n"
							+ "       AND a.mem_bnk_id || a.mem_zne_id || a.mem_brn_id =\n"
							+ "              d.mem_bnk_id || d.mem_zne_id || d.mem_brn_id\n"
							+ "       AND TRUNC (CLM_DATE) BETWEEN ?  AND ? \n"
							+ "       AND a.clm_status = ? \n";

					prepStatement = conn.prepareStatement(query);
					prepStatement.setDate(1, fromDate);
					prepStatement.setDate(2, toDate);
					prepStatement.setString(3, clmApplicationStatusFlag);

					rs = (ResultSet) prepStatement.executeQuery();
				}
				// added by upchar@path on 03/07/2013 to display reply received
				// claim applications
				if (clmApplicationStatusFlag
						.equals(ClaimConstants.CLM_REPLY_RECEIVED)) {
					// kot8
					Log.log(Log.INFO, "ReportDAO",
							"getListOfClaimRefNumbers()",
							"From Date is NULL, Status is  TEMPORARY CLOSED");

					query = "SELECT a.mem_bnk_id || a.mem_zne_id || a.mem_brn_id memberid,  \n"
							+ "                                d.mem_bank_name,  \n"
							+ "                                d.mem_zone_name,  \n"
							+ "                                DECODE (d.MEM_BRANCH_NAME,  \n"
							+ "                                        NULL, e.APP_MLI_BRANCH_NAME,  \n"
							+ "                                        d.MEM_BRANCH_NAME)  \n"
							+ "                                   branch,  \n"
							+ "                                e.cgpan,  \n"
							+ "                                c.ssi_unit_name,  \n"
							+ "                                DECODE (e.app_reapprove_amount,  \n"
							+ "                                        NULL, e.APP_approved_amount,  \n"
							+ "                                        e.app_reapprove_amount)  \n"
							+ "                                   guarantamt,  \n"
							+ "                                A.clm_ref_no,  \n"
							+ "                                a.clm_date,  \n"
							+ "                                b.caa_applied_amount clmAppdAmt,TRUNC(a.CLM_CREATED_MODIFIED_DT), \n"
							+ "                                (select distinct max(trim(upper(replace(clm_ltr_ref_no,' ',''))))\n"
							+ "                                from claim_query_detail cqd\n"
							+ "                                where a.clm_ref_no = cqd.clm_ref_no\n"
							+ "                                and clm_ltr_dt in \n"
							+ "                                (select max(clm_ltr_dt) from claim_query_detail cd where cd.clm_ref_no = cqd.clm_ref_no)\n"
							+ "                                ) clmqryrefno,\n"
							+ "                                (select distinct clm_ltr_dt\n"
							+ "                                from claim_query_detail cq\n"
							+ "                                where a.clm_ref_no = cq.clm_ref_no\n"
							+ "                                and clm_ltr_dt in \n"
							+ "                                (select max(clm_ltr_dt) from claim_query_detail cd where cd.clm_ref_no = cq.clm_ref_no)\n"
							+ "                                ) clmqrydt,\n"
							+ "                                (select inward_id from claim_reply_detail crd\n"
							+ "                                where crd.clm_ref_no = a.clm_ref_no\n"
							+ "                                and inward_dt in \n"
							+ "                                (select max(inward_dt) from claim_reply_detail cd where cd.clm_ref_no = crd.clm_ref_no)\n"
							+ "                                ) rplyinwid,\n"
							+ "                                (select inward_dt from claim_reply_detail crd\n"
							+ "                                where crd.clm_ref_no = a.clm_ref_no\n"
							+ "                                and inward_dt in \n"
							+ "                                (select max(inward_dt) from claim_reply_detail cd where cd.clm_ref_no = crd.clm_ref_no)\n"
							+ "                                ) rplyinwdt ,TRUNC(a.CLM_CREATED_MODIFIED_DT) \n"
							+ "                           FROM claim_detail_temp a,  \n"
							+ "                                claim_application_amount_temp b,  \n"
							+ "                                ssi_detail c,  \n"
							+ "                                member_info d,  \n"
							+ "                                application_detail e  \n"
							+ "                          WHERE     b.cgpan = e.cgpan  \n"
							+ "                                AND a.clm_ref_no = b.clm_ref_no  \n"
							+ "                                AND a.bid = c.bid  \n"
							+ "                                AND a.mem_bnk_id || a.mem_zne_id || a.mem_brn_id =  \n"
							+ "                                       d.mem_bnk_id || d.mem_zne_id || d.mem_brn_id  \n"
							+ "                               AND TRUNC (CLM_CREATED_MODIFIED_DT) BETWEEN ?  AND ?   \n"
							+ "                                AND a.clm_status = ?     and   a.mem_bnk_id = ?    order by TRUNC(CLM_CREATED_MODIFIED_DT),d.mem_bank_name ";

					prepStatement = conn.prepareStatement(query);
					prepStatement.setDate(1, fromDate);
					prepStatement.setDate(2, toDate);
					prepStatement.setString(3, clmApplicationStatusFlag);
					prepStatement.setString(4, bankId);
					rs = (ResultSet) prepStatement.executeQuery();
				}
			}
			if ((fromDate != null)
					&& ((clmApplicationStatusFlag
							.equals(ClaimConstants.CLM_PENDING_STATUS) || clmApplicationStatusFlag
							.equals(ClaimConstants.CLM_HOLD_STATUS)
					// clmApplicationStatusFlag.equals(ClaimConstants.CLM_FORWARD_STATUS)
					))) {
				Log.log(Log.INFO, "ReportDAO", "getListOfClaimRefNumbers()",
						"From Date is NULL, Status is Pending or Forward or Hold");
				// new kot2
				query = "SELECT  m.mem_bnk_id || m.mem_zne_id || m.mem_brn_id,m.mem_bank_name bnkname,m.mem_zone_name, DECODE (m.MEM_BRANCH_NAME,\n"
						+ "                 NULL, a.APP_MLI_BRANCH_NAME,\n"
						+ "                 m.MEM_BRANCH_NAME)\n"
						+ "            BRANCH,a.cgpan,S.SSI_UNIT_NAME,DECODE (a.app_reapprove_amount,\n"
						+ "                 NULL, a.APP_approved_amount,\n"
						+ "                 a.app_reapprove_amount)\n"
						+ "            GuarAmt,c.clm_ref_no, C.CLM_DATE, b.caa_applied_amount clmAppliedAmt,C.CLM_DECLARATION_RECVD clmDeclRcvdFlg, C.CLM_DECL_RECVD_DT clmDeclRecvdDt\n"
						+ "                                      \n"
						+ "    FROM claim_detail_temp c,\n"
						+ "    claim_application_amount_temp b,\n"
						+ "         member_info m,\n"
						+ "         SSI_DETAIL S,\n"
						+ "         application_detail a\n"
						+ "         \n"
						+ "   WHERE     TRUNC (c.clm_date) BETWEEN ?  AND ? \n"
						+ "         AND C.BID = S.BID\n"
						+ "         AND s.ssi_reference_number = a.ssi_reference_number\n"
						+ "         AND c.mem_bnk_id || c.mem_brn_id || c.mem_zne_id =\n"
						+ "                a.mem_bnk_id || a.mem_brn_id || a.mem_zne_id\n"
						+ "         AND LTRIM (\n"
						+ "                RTRIM (UPPER (c.mem_bnk_id || c.mem_zne_id || c.mem_brn_id))) =\n"
						+ "                LTRIM (\n"
						+ "                   RTRIM (UPPER (m.mem_bnk_id || m.mem_zne_id || m.mem_brn_id)))\n"
						+ "         AND c.clm_status = ? \n"
						+ "    and   c.mem_bnk_id = ? \n"
						+ "         AND C.CLM_REF_NO = b.CLM_REF_NO\n"
						+ "         AND b.CGPAN = a.CGPAN  order by TRUNC(CLM_CREATED_MODIFIED_DT),bnkname ";

				prepStatement = conn.prepareStatement(query);
				prepStatement.setDate(1, fromDate);
				prepStatement.setDate(2, toDate);
				prepStatement.setString(3, clmApplicationStatusFlag);
				prepStatement.setString(4, bankId);

				rs = (ResultSet) prepStatement.executeQuery();
			}
			if ((fromDate == null)
					&& (!(clmApplicationStatusFlag
							.equals(ClaimConstants.CLM_PENDING_STATUS) || clmApplicationStatusFlag
							.equals(ClaimConstants.CLM_HOLD_STATUS)
					// clmApplicationStatusFlag.equals(ClaimConstants.CLM_FORWARD_STATUS)
					))) {
				if (clmApplicationStatusFlag
						.equals(ClaimConstants.CLM_APPROVAL_STATUS)) {
					Log.log(Log.INFO, "ReportDAO",
							"getListOfClaimRefNumbers()",
							"From Date is NULL, Status is Approval");
					query = "select m.mem_bank_name bnkname, m.mem_bnk_id || m.mem_zne_id || m.mem_brn_id, c.clm_ref_no, c.cgclan,S.SSI_UNIT_NAME,TRUNC(CLM_APPROVED_DT),CLM_APPROVED_AMT "
							+ " from claim_detail c, member_info m,SSI_DETAIL S "
							+
							// code changed clm_date to clm_approved_dt by
							// sukumar@path on 11-09-2009
							// " where c.clm_date <= ? AND C.BID=S.BID " +
							" where TRUNC(c.clm_approved_dt) <= ? AND C.BID=S.BID "
							+ " and LTRIM(RTRIM(UPPER(c.mem_bnk_id || c.mem_zne_id || c.mem_brn_id))) = LTRIM(RTRIM(UPPER(m.mem_bnk_id || m.mem_zne_id || m.mem_brn_id))) "
							+ " and c.clm_status = ?"
							+ " group by m.mem_bank_name,"
							+ " m.mem_bnk_id || m.mem_zne_id || m.mem_brn_id,"
							+ " c.clm_ref_no,"
							+ " c.cgclan,S.SSI_UNIT_NAME,TRUNC(CLM_APPROVED_DT),CLM_APPROVED_AMT order by Trunc(c.clm_approved_dt),bnkname";
					prepStatement = conn.prepareStatement(query);
					// prepStatement.setDate(1,fromDate);
					prepStatement.setDate(1, toDate);
					prepStatement.setString(2, clmApplicationStatusFlag);
					rs = (ResultSet) prepStatement.executeQuery();
				} else if (clmApplicationStatusFlag
						.equals(ClaimConstants.CLM_REJECT_STATUS)) {
					Log.log(Log.INFO, "ReportDAO",
							"getListOfClaimRefNumbers()",
							"From Date is NULL, Status is Reject");

					query = "  SELECT a.mem_bnk_id || a.mem_zne_id || a.mem_brn_id,d.mem_bank_name,d.mem_zone_name, DECODE (d.MEM_BRANCH_NAME,\n"
							+ "                 NULL, e.APP_MLI_BRANCH_NAME,\n"
							+ "                 d.MEM_BRANCH_NAME)\n"
							+ "            branch,e.cgpan,c.ssi_unit_name, DECODE (e.app_reapprove_amount,\n"
							+ "                 NULL, e.APP_approved_amount,\n"
							+ "                 e.app_reapprove_amount)\n"
							+ "            guarantamt,A.clm_ref_no, a.clm_date,b.caa_applied_amount clmAppdAmt \n"
							+ "                      FROM claim_detail a,\n"
							+ "         claim_application_amount b,\n"
							+ "         ssi_detail c,\n"
							+ "         member_info d,\n"
							+ "         application_detail e\n"
							+ "   WHERE   \n"
							+ "        b.cgpan = e.cgpan\n"
							+ "         AND a.clm_ref_no = b.clm_ref_no\n"
							+ "         AND a.bid = c.bid\n"
							+ "         AND a.mem_bnk_id || a.mem_zne_id || a.mem_brn_id =\n"
							+ "                d.mem_bnk_id || d.mem_zne_id || d.mem_brn_id\n"
							+ "         AND TRUNC (a.CLM_CREATED_MODIFIED_DT) BETWEEN ?  AND ? \n"
							+ "         AND a.clm_status = ? ";

					prepStatement = conn.prepareStatement(query);
					prepStatement.setDate(1, fromDate);
					prepStatement.setDate(2, toDate);
					prepStatement.setString(3, clmApplicationStatusFlag);

					rs = (ResultSet) prepStatement.executeQuery();
				} else if (clmApplicationStatusFlag
						.equals(ClaimConstants.CLM_FORWARD_STATUS)) {
					Log.log(Log.INFO, "ReportDAO",
							"getListOfClaimRefNumbers()",
							"From Date is NULL, Status is Forward");
					query = "select m.mem_bank_name bnkname, m.mem_bnk_id || m.mem_zne_id || m.mem_brn_id, c.clm_ref_no,S.SSI_UNIT_NAME,TRUNC(CLM_CREATED_MODIFIED_DT) "
							+ " from claim_detail_temp c, member_info m,SSI_DETAIL S"
							+ " where TRUNC(C.CLM_CREATED_MODIFIED_DT) <= ? AND C.BID=S.BID "
							+ " and LTRIM(RTRIM(UPPER(c.mem_bnk_id || c.mem_zne_id || c.mem_brn_id))) = LTRIM(RTRIM(UPPER(m.mem_bnk_id || m.mem_zne_id || m.mem_brn_id))) "
							+ " and c.clm_status = ?"
							+ " group by m.mem_bank_name,"
							+ " m.mem_bnk_id || m.mem_zne_id || m.mem_brn_id,"
							+ " c.clm_ref_no,S.SSI_UNIT_NAME,TRUNC(CLM_CREATED_MODIFIED_DT) order by TRUNC(CLM_CREATED_MODIFIED_DT),bnkname";

					prepStatement = conn.prepareStatement(query);
					prepStatement.setDate(1, toDate);
					prepStatement.setString(2, clmApplicationStatusFlag);
					rs = (ResultSet) prepStatement.executeQuery();
				} else if (clmApplicationStatusFlag
						.equals(ClaimConstants.CLM_TEMPORARY_CLOSE)
						|| clmApplicationStatusFlag
								.equals(ClaimConstants.CLM_TEMPORARY_REJECT)
						|| clmApplicationStatusFlag
								.equals(ClaimConstants.CLM_WITHDRAWN)) {
					Log.log(Log.INFO, "ReportDAO",
							"getListOfClaimRefNumbers()",
							"From Date is NULL, Status is Forward");
					query = "select m.mem_bank_name bnkname, m.mem_bnk_id || m.mem_zne_id || m.mem_brn_id, c.clm_ref_no,S.SSI_UNIT_NAME,TRUNC(CLM_CREATED_MODIFIED_DT) "
							+ " from claim_detail_temp c, member_info m,SSI_DETAIL S"
							+ " where TRUNC(C.CLM_CREATED_MODIFIED_DT) <= ? AND C.BID=S.BID "
							+ " and LTRIM(RTRIM(UPPER(c.mem_bnk_id || c.mem_zne_id || c.mem_brn_id))) = LTRIM(RTRIM(UPPER(m.mem_bnk_id || m.mem_zne_id || m.mem_brn_id))) "
							+ " and c.clm_status = ?"
							+ " group by m.mem_bank_name,"
							+ " m.mem_bnk_id || m.mem_zne_id || m.mem_brn_id,"
							+ " c.clm_ref_no,S.SSI_UNIT_NAME,TRUNC(CLM_CREATED_MODIFIED_DT) order by TRUNC(CLM_CREATED_MODIFIED_DT),bnkname";
					prepStatement = conn.prepareStatement(query);
					prepStatement.setDate(1, toDate);
					prepStatement.setString(2, clmApplicationStatusFlag);
					rs = (ResultSet) prepStatement.executeQuery();
				} else if (clmApplicationStatusFlag
						.equals(ClaimConstants.CLM_REPLY_RECEIVED)) {
					Log.log(Log.INFO, "ReportDAO",
							"getListOfClaimRefNumbers()",
							"From Date is NULL, Status is  TEMPORARY CLOSED");
					query = "select m.mem_bank_name bnkname, m.mem_bnk_id || m.mem_zne_id || m.mem_brn_id, c.clm_ref_no,S.SSI_UNIT_NAME,TRUNC(CLM_CREATED_MODIFIED_DT) "
							+ " from claim_detail_temp c, member_info m,SSI_DETAIL S"
							+ " where TRUNC(C.CLM_CREATED_MODIFIED_DT) <= ? AND C.BID=S.BID "
							+ " and LTRIM(RTRIM(UPPER(c.mem_bnk_id || c.mem_zne_id || c.mem_brn_id))) = LTRIM(RTRIM(UPPER(m.mem_bnk_id || m.mem_zne_id || m.mem_brn_id))) "
							+ " and c.clm_status = ?"
							+ " group by m.mem_bank_name,"
							+ " m.mem_bnk_id || m.mem_zne_id || m.mem_brn_id,"
							+ " c.clm_ref_no,S.SSI_UNIT_NAME,TRUNC(CLM_CREATED_MODIFIED_DT) order by TRUNC(CLM_CREATED_MODIFIED_DT),bnkname";
					prepStatement = conn.prepareStatement(query);
					prepStatement.setDate(1, toDate);
					prepStatement.setString(2, clmApplicationStatusFlag);
					rs = (ResultSet) prepStatement.executeQuery();
				}
			}
			if ((fromDate == null)
					&& ((clmApplicationStatusFlag
							.equals(ClaimConstants.CLM_PENDING_STATUS) || clmApplicationStatusFlag
							.equals(ClaimConstants.CLM_HOLD_STATUS)
					// clmApplicationStatusFlag.equals(ClaimConstants.CLM_FORWARD_STATUS)
					))) {
				Log.log(Log.INFO, "ReportDAO", "getListOfClaimRefNumbers()",
						"From Date is NULL, Status is Pending or Forward or Hold");
				query = "select m.mem_bank_name bnkname, m.mem_bnk_id || m.mem_zne_id || m.mem_brn_id, c.clm_ref_no,S.SSI_UNIT_NAME,CLM_DATE "
						+ " from claim_detail_temp c, member_info m,SSI_DETAIL S"
						+ " where c.clm_date <= ? AND C.BID=S.BID "
						+ " and LTRIM(RTRIM(UPPER(c.mem_bnk_id || c.mem_zne_id || c.mem_brn_id))) = LTRIM(RTRIM(UPPER(m.mem_bnk_id || m.mem_zne_id || m.mem_brn_id))) "
						+ " and c.clm_status = ?"
						+ " group by m.mem_bank_name,"
						+ " m.mem_bnk_id || m.mem_zne_id || m.mem_brn_id,"
						+ " c.clm_ref_no,S.SSI_UNIT_NAME,CLM_DATE order by CLM_DATE,bnkname";
				prepStatement = conn.prepareStatement(query);
				prepStatement.setDate(1, toDate);
				prepStatement.setString(2, clmApplicationStatusFlag);
				rs = (ResultSet) prepStatement.executeQuery();
			}
			String memberBankName = null;
			String memberId = null;
			String clmRefNumber = null;
			String cgclan = "";
			ClaimDetail clmDtl = null;
			String unitName = "";
			java.util.Date submittedDt = null;
			java.util.Date clmLodgmentdDt = null;
			java.util.Date claimForwardedDt = null;

			String claimRetRemarks = "";
			java.util.Date claimReturnDate = null;

			Double claimForwdAmt = null;

			java.util.Date claimRejectedDt = null;

			java.util.Date tempRejOrRejDt = null;

			java.util.Date claimReplyRecvdDate = null;

			double claimAppliedAmt = 0.0;

			double claimRejAmount = 0.0;

			String clmQryRefNumber = "";

			java.util.Date clamQryDate = null;

			String clmDeclRecvdFlag = null;
			java.util.Date clmDeclRecvdDt = null;

			java.util.Date lastActionTakenDt = null;

			String claimStatus = "";

			String replyInwardId = "";

			java.util.Date replyInwardDt = null;

			java.util.Date tempClosedDate = null;

			java.util.Date tempRejectedDate = null;

			double claimApprovedAmt = 0.0;

			double totalClmApprovedAmt = 0.0;

			double totGrandClmApprovedAmtTemp = 0.0;

			double totGrandClmApprovedAmt = 0.0;

			String zoneName = null;
			String branchName = null;
			double guarApprdAmount = 0.0;

			double totalGuarnteAmt = 0.0;

			double totgrandTotal = 0.0;

			double totgrandTotalTemp = 0.0;

			double claimDecRecvdAmt = 0.0;
			String claimDecRecvdflag = null;
			java.util.Date claimDecRecvdDt = null;
			java.util.Date claimWithDrawnDt = null;
			String claimRemarks = null;
			String cgpan = null;
			java.util.Date clmApprovedDt = null;

			if (clmApplicationStatusFlag
					.equals(ClaimConstants.CLM_APPROVAL_STATUS)) {
				while (rs.next()) {

					memberId = (String) rs.getString(1);
					memberBankName = (String) rs.getString(2);
					zoneName = (String) rs.getString(3);
					branchName = (String) rs.getString(4);
					cgpan = (String) rs.getString(5);
					unitName = (String) rs.getString(6);
					guarApprdAmount = (Double) rs.getDouble(7);
					clmRefNumber = (String) rs.getString(8);
					cgclan = (String) rs.getString(9);
					clmLodgmentdDt = (Date) rs.getDate(10);
					claimApprovedAmt = (double) rs.getDouble(11);
					clmApprovedDt = (Date) rs.getDate(12);

					totalGuarnteAmt = totalGuarnteAmt + guarApprdAmount;
					totgrandTotalTemp = totalGuarnteAmt / 100000;
					totalClmApprovedAmt = totalClmApprovedAmt
							+ claimApprovedAmt;
					totGrandClmApprovedAmtTemp = totalClmApprovedAmt / 100000;
					DecimalFormat decFormat = new DecimalFormat("#.00");
					totGrandClmApprovedAmt = new Double(
							decFormat.format(totGrandClmApprovedAmtTemp))
							.doubleValue();
					totgrandTotal = new Double(
							decFormat.format(totgrandTotalTemp)).doubleValue();

					clmDtl = new ClaimDetail();

					clmDtl.setMliId(memberId);
					clmDtl.setMliName(memberBankName);
					clmDtl.setBranchName(branchName);
					clmDtl.setZoneName(zoneName);
					clmDtl.setCgpan(cgpan);
					clmDtl.setSsiUnitName(unitName);
					clmDtl.setApplicationApprovedAmount(guarApprdAmount);
					clmDtl.setClaimRefNum(clmRefNumber);
					clmDtl.setCGCLAN(cgclan);
					clmDtl.setClmSubmittedDt(clmLodgmentdDt);
					clmDtl.setClaimApprovedAmount(claimApprovedAmt);
					clmDtl.setClaimApprovedDt(clmApprovedDt);
					clmDtl.setTotgrandTotal(totgrandTotal);
					clmDtl.setTotGrandClmApprovedAmt(totGrandClmApprovedAmt);

					// Adding the ClaimDetail object to the vector
					clmRefNumbersList.addElement(clmDtl);
				}
				rs.close();
				rs = null;
			} else if

			(clmApplicationStatusFlag.equals(ClaimConstants.CLM_PENDING_STATUS)) {
				while (rs.next()) {

					memberId = (String) rs.getString(1);
					memberBankName = (String) rs.getString(2);
					zoneName = (String) rs.getString(3);
					branchName = (String) rs.getString(4);
					cgpan = (String) rs.getString(5);
					unitName = (String) rs.getString(6);
					guarApprdAmount = (Double) rs.getDouble(7);
					clmRefNumber = (String) rs.getString(8);
					clmLodgmentdDt = (Date) rs.getDate(9);
					claimAppliedAmt = (Double) rs.getDouble(10);
					clmDeclRecvdFlag = (String) rs.getString(11);
					clmDeclRecvdDt = (Date) rs.getDate(12);

					totalGuarnteAmt = totalGuarnteAmt + guarApprdAmount;
					totgrandTotalTemp = totalGuarnteAmt / 100000;
					totalClmApprovedAmt = totalClmApprovedAmt + claimAppliedAmt;
					totGrandClmApprovedAmtTemp = totalClmApprovedAmt / 100000;
					DecimalFormat decFormat = new DecimalFormat("#.00");
					totGrandClmApprovedAmt = new Double(
							decFormat.format(totGrandClmApprovedAmtTemp))
							.doubleValue();
					totgrandTotal = new Double(
							decFormat.format(totgrandTotalTemp)).doubleValue();

					clmDtl = new ClaimDetail();

					clmDtl.setMliId(memberId);
					clmDtl.setMliName(memberBankName);
					clmDtl.setBranchName(branchName);
					clmDtl.setZoneName(zoneName);
					clmDtl.setCgpan(cgpan);
					clmDtl.setSsiUnitName(unitName);
					clmDtl.setApplicationApprovedAmount(guarApprdAmount);
					clmDtl.setClaimRefNum(clmRefNumber);
					clmDtl.setClmSubmittedDt(clmLodgmentdDt);
					clmDtl.setClaimAppliedAmt(claimAppliedAmt);
					clmDtl.setClmDeclRecvdFlag(clmDeclRecvdFlag);
					clmDtl.setClmDeclRecvdDt(clmDeclRecvdDt);

					clmDtl.setTotgrandTotal(totgrandTotal);
					clmDtl.setTotGrandClmApprovedAmt(totGrandClmApprovedAmt);

					// Adding the ClaimDetail object to the vector
					clmRefNumbersList.addElement(clmDtl);
				}
				rs.close();
				rs = null;
			} else if

			(clmApplicationStatusFlag.equals(ClaimConstants.CLM_WITHDRAWN)) {
				while (rs.next()) {

					memberId = (String) rs.getString(1);
					memberBankName = (String) rs.getString(2);
					zoneName = (String) rs.getString(3);
					branchName = (String) rs.getString(4);
					cgpan = (String) rs.getString(5);
					unitName = (String) rs.getString(6);
					guarApprdAmount = (Double) rs.getDouble(7);
					clmRefNumber = (String) rs.getString(8);
					clmLodgmentdDt = (Date) rs.getDate(9);
					claimAppliedAmt = (Double) rs.getDouble(10);
					claimWithDrawnDt = (Date) rs.getDate(11);

					totalGuarnteAmt = totalGuarnteAmt + guarApprdAmount;
					totgrandTotalTemp = totalGuarnteAmt / 100000;
					totalClmApprovedAmt = totalClmApprovedAmt + claimAppliedAmt;
					totGrandClmApprovedAmtTemp = totalClmApprovedAmt / 100000;
					DecimalFormat decFormat = new DecimalFormat("#.00");
					totGrandClmApprovedAmt = new Double(
							decFormat.format(totGrandClmApprovedAmtTemp))
							.doubleValue();
					totgrandTotal = new Double(
							decFormat.format(totgrandTotalTemp)).doubleValue();

					clmDtl = new ClaimDetail();

					clmDtl.setMliId(memberId);
					clmDtl.setMliName(memberBankName);
					clmDtl.setBranchName(branchName);
					clmDtl.setZoneName(zoneName);
					clmDtl.setCgpan(cgpan);
					clmDtl.setSsiUnitName(unitName);
					clmDtl.setApplicationApprovedAmount(guarApprdAmount);
					clmDtl.setClaimRefNum(clmRefNumber);
					clmDtl.setClmSubmittedDt(clmLodgmentdDt);
					clmDtl.setClaimAppliedAmt(claimAppliedAmt);
					clmDtl.setClaimWithDrawnDate(claimWithDrawnDt);

					clmDtl.setTotgrandTotal(totgrandTotal);
					clmDtl.setTotGrandClmApprovedAmt(totGrandClmApprovedAmt);

					// clmDtl.setClaimLodgmentDate(clmLodgmentdDt);

					// Adding the ClaimDetail object to the vector
					clmRefNumbersList.addElement(clmDtl);

				}
				rs.close();
				rs = null;
			} else if

			(clmApplicationStatusFlag
					.equals(ClaimConstants.CLM_TEMPORARY_CLOSE)) {
				while (rs.next()) {

					memberId = (String) rs.getString(1);
					memberBankName = (String) rs.getString(2);
					zoneName = (String) rs.getString(3);
					branchName = (String) rs.getString(4);
					cgpan = (String) rs.getString(5);
					unitName = (String) rs.getString(6);
					guarApprdAmount = (Double) rs.getDouble(7);
					clmRefNumber = (String) rs.getString(8);
					clmLodgmentdDt = (Date) rs.getDate(9);
					claimAppliedAmt = (Double) rs.getDouble(10);
					tempClosedDate = (Date) rs.getDate(11);

					clmQryRefNumber = (String) rs.getString(12);

					clamQryDate = (Date) rs.getDate(13);

					totalGuarnteAmt = totalGuarnteAmt + guarApprdAmount;
					totgrandTotalTemp = totalGuarnteAmt / 100000;
					totalClmApprovedAmt = totalClmApprovedAmt + claimAppliedAmt;
					totGrandClmApprovedAmtTemp = totalClmApprovedAmt / 100000;
					DecimalFormat decFormat = new DecimalFormat("#.00");
					totGrandClmApprovedAmt = new Double(
							decFormat.format(totGrandClmApprovedAmtTemp))
							.doubleValue();
					totgrandTotal = new Double(
							decFormat.format(totgrandTotalTemp)).doubleValue();

					clmDtl = new ClaimDetail();

					clmDtl.setMliId(memberId);
					clmDtl.setMliName(memberBankName);
					clmDtl.setBranchName(branchName);
					clmDtl.setZoneName(zoneName);
					clmDtl.setCgpan(cgpan);
					clmDtl.setSsiUnitName(unitName);
					clmDtl.setApplicationApprovedAmount(guarApprdAmount);
					clmDtl.setClaimRefNum(clmRefNumber);
					clmDtl.setClmSubmittedDt(clmLodgmentdDt);
					clmDtl.setClaimAppliedAmt(claimAppliedAmt);
					clmDtl.setTempClosedDate(tempClosedDate);

					clmDtl.setClmQryRefNumber(clmQryRefNumber);
					clmDtl.setClamQryDate(clamQryDate);
					clmDtl.setTotgrandTotal(totgrandTotal);
					clmDtl.setTotGrandClmApprovedAmt(totGrandClmApprovedAmt);

					// Adding the ClaimDetail object to the vector
					clmRefNumbersList.addElement(clmDtl);

				}
				rs.close();
				rs = null;
			}

			else if

			(clmApplicationStatusFlag
					.equals(ClaimConstants.CLM_TEMPORARY_REJECT)) {
				while (rs.next()) {

					memberId = (String) rs.getString(1);
					memberBankName = (String) rs.getString(2);
					zoneName = (String) rs.getString(3);
					branchName = (String) rs.getString(4);
					cgpan = (String) rs.getString(5);
					unitName = (String) rs.getString(6);
					guarApprdAmount = (Double) rs.getDouble(7);
					clmRefNumber = (String) rs.getString(8);
					clmLodgmentdDt = (Date) rs.getDate(9);
					claimRejAmount = (Double) rs.getDouble(10);
					tempRejectedDate = (Date) rs.getDate(11);
					claimRetRemarks = rs.getString(12);

					totalGuarnteAmt = totalGuarnteAmt + guarApprdAmount;
					totgrandTotalTemp = totalGuarnteAmt / 100000;
					totalClmApprovedAmt = totalClmApprovedAmt + claimRejAmount;
					totGrandClmApprovedAmtTemp = totalClmApprovedAmt / 100000;
					DecimalFormat decFormat = new DecimalFormat("#.00");
					totGrandClmApprovedAmt = new Double(
							decFormat.format(totGrandClmApprovedAmtTemp))
							.doubleValue();
					totgrandTotal = new Double(
							decFormat.format(totgrandTotalTemp)).doubleValue();

					clmDtl = new ClaimDetail();

					clmDtl.setMliId(memberId);
					clmDtl.setMliName(memberBankName);
					clmDtl.setBranchName(branchName);
					clmDtl.setZoneName(zoneName);
					clmDtl.setCgpan(cgpan);
					clmDtl.setSsiUnitName(unitName);
					clmDtl.setApplicationApprovedAmount(guarApprdAmount);
					clmDtl.setClaimRefNum(clmRefNumber);
					clmDtl.setClmSubmittedDt(clmLodgmentdDt);
					clmDtl.setClaimAppliedAmt(claimRejAmount);
					clmDtl.setTempRejectedDate(tempRejectedDate);
					clmDtl.setClaimRetRemarks(claimRetRemarks);

					clmDtl.setTotgrandTotal(totgrandTotal);
					clmDtl.setTotGrandClmApprovedAmt(totGrandClmApprovedAmt);

					// clmDtl.setClaimLodgmentDate(clmLodgmentdDt);

					// Adding the ClaimDetail object to the vector
					clmRefNumbersList.addElement(clmDtl);

				}
				rs.close();
				rs = null;
			}

			else if

			(clmApplicationStatusFlag.equals(ClaimConstants.CLM_REPLY_RECEIVED)) {
				while (rs.next()) {

					memberId = (String) rs.getString(1);
					memberBankName = (String) rs.getString(2);
					zoneName = (String) rs.getString(3);
					branchName = (String) rs.getString(4);
					cgpan = (String) rs.getString(5);
					unitName = (String) rs.getString(6);
					guarApprdAmount = (Double) rs.getDouble(7);
					clmRefNumber = (String) rs.getString(8);
					clmLodgmentdDt = (Date) rs.getDate(9);
					claimApprovedAmt = (Double) rs.getDouble(10);
					claimReplyRecvdDate = (Date) rs.getDate(11);
					clmQryRefNumber = (String) rs.getString(12);
					clamQryDate = (Date) rs.getDate(13);
					replyInwardId = (String) rs.getString(14);
					replyInwardDt = (Date) rs.getDate(15);

					totalGuarnteAmt = totalGuarnteAmt + guarApprdAmount;
					totgrandTotalTemp = totalGuarnteAmt / 100000;
					totalClmApprovedAmt = totalClmApprovedAmt
							+ claimApprovedAmt;
					totGrandClmApprovedAmtTemp = totalClmApprovedAmt / 100000;
					DecimalFormat decFormat = new DecimalFormat("#.00");
					totGrandClmApprovedAmt = new Double(
							decFormat.format(totGrandClmApprovedAmtTemp))
							.doubleValue();
					totgrandTotal = new Double(
							decFormat.format(totgrandTotalTemp)).doubleValue();

					clmDtl = new ClaimDetail();

					clmDtl.setMliId(memberId);
					clmDtl.setMliName(memberBankName);
					clmDtl.setBranchName(branchName);
					clmDtl.setZoneName(zoneName);
					clmDtl.setCgpan(cgpan);
					clmDtl.setSsiUnitName(unitName);
					clmDtl.setApplicationApprovedAmount(guarApprdAmount);
					clmDtl.setClaimRefNum(clmRefNumber);
					clmDtl.setClmSubmittedDt(clmLodgmentdDt);
					clmDtl.setClaimApprovedAmount(claimApprovedAmt);
					clmDtl.setClaimReplyRecvdDate(claimReplyRecvdDate);
					clmDtl.setClmQryRefNumber(clmQryRefNumber);
					clmDtl.setClamQryDate(clamQryDate);

					clmDtl.setReplyInwardId(replyInwardId);
					clmDtl.setReplyInwardDt(replyInwardDt);
					clmDtl.setTotgrandTotal(totgrandTotal);
					clmDtl.setTotGrandClmApprovedAmt(totGrandClmApprovedAmt);

					// clmDtl.setClaimLodgmentDate(clmLodgmentdDt);

					// Adding the ClaimDetail object to the vector
					clmRefNumbersList.addElement(clmDtl);

				}
				rs.close();
				rs = null;
			}

			else if

			(clmApplicationStatusFlag.equals(ClaimConstants.CLM_REJECT_STATUS)) {
				while (rs.next()) {

					memberId = (String) rs.getString(1);
					memberBankName = (String) rs.getString(2);
					zoneName = (String) rs.getString(3);
					branchName = (String) rs.getString(4);
					cgpan = (String) rs.getString(5);
					unitName = (String) rs.getString(6);
					guarApprdAmount = (Double) rs.getDouble(7);
					clmRefNumber = (String) rs.getString(8);
					clmLodgmentdDt = (Date) rs.getDate(9);
					claimAppliedAmt = (Double) rs.getDouble(10);
					claimRejectedDt = (Date) rs.getDate(11);
					claimRetRemarks = rs.getString(12);
					totalGuarnteAmt = totalGuarnteAmt + guarApprdAmount;
					totgrandTotalTemp = totalGuarnteAmt / 100000;
					totalClmApprovedAmt = totalClmApprovedAmt + claimAppliedAmt;
					totGrandClmApprovedAmtTemp = totalClmApprovedAmt / 100000;
					DecimalFormat decFormat = new DecimalFormat("#.00");
					totGrandClmApprovedAmt = new Double(
							decFormat.format(totGrandClmApprovedAmtTemp))
							.doubleValue();
					totgrandTotal = new Double(
							decFormat.format(totgrandTotalTemp)).doubleValue();

					clmDtl = new ClaimDetail();

					clmDtl.setMliId(memberId);
					clmDtl.setMliName(memberBankName);
					clmDtl.setBranchName(branchName);
					clmDtl.setZoneName(zoneName);
					clmDtl.setCgpan(cgpan);
					clmDtl.setSsiUnitName(unitName);
					clmDtl.setApplicationApprovedAmount(guarApprdAmount);
					clmDtl.setClaimRefNum(clmRefNumber);
					clmDtl.setClmSubmittedDt(clmLodgmentdDt);
					clmDtl.setClaimAppliedAmt(claimAppliedAmt);
					clmDtl.setClaimRejectedDt(claimRejectedDt);
					clmDtl.setClaimRetRemarks(claimRetRemarks);

					clmDtl.setTotgrandTotal(totgrandTotal);
					clmDtl.setTotGrandClmApprovedAmt(totGrandClmApprovedAmt);

					// Adding the ClaimDetail object to the vector
					clmRefNumbersList.addElement(clmDtl);

				}
				rs.close();
				rs = null;
			}

			else if

			(clmApplicationStatusFlag.equals("RT")) {
				while (rs.next()) {

					memberId = (String) rs.getString(1);
					memberBankName = (String) rs.getString(2);
					zoneName = (String) rs.getString(3);
					branchName = (String) rs.getString(4);
					cgpan = (String) rs.getString(5);
					unitName = (String) rs.getString(6);
					guarApprdAmount = (Double) rs.getDouble(7);
					clmRefNumber = (String) rs.getString(8);
					claimAppliedAmt = (Double) rs.getDouble(9);
					claimReturnDate = (Date) rs.getDate(10);
					claimRetRemarks = (String) rs.getString(11);

					totalGuarnteAmt = totalGuarnteAmt + guarApprdAmount;
					totgrandTotalTemp = totalGuarnteAmt / 100000;
					totalClmApprovedAmt = totalClmApprovedAmt + claimAppliedAmt;
					totGrandClmApprovedAmtTemp = totalClmApprovedAmt / 100000;
					DecimalFormat decFormat = new DecimalFormat("#.00");
					totGrandClmApprovedAmt = new Double(
							decFormat.format(totGrandClmApprovedAmtTemp))
							.doubleValue();
					totgrandTotal = new Double(
							decFormat.format(totgrandTotalTemp)).doubleValue();

					clmDtl = new ClaimDetail();

					clmDtl.setMliId(memberId);
					clmDtl.setMliName(memberBankName);
					clmDtl.setBranchName(branchName);
					clmDtl.setZoneName(zoneName);
					clmDtl.setCgpan(cgpan);
					clmDtl.setSsiUnitName(unitName);
					clmDtl.setApplicationApprovedAmount(guarApprdAmount);
					clmDtl.setClaimRefNum(clmRefNumber);
					clmDtl.setClaimAppliedAmt(claimAppliedAmt);
					clmDtl.setClaimReturnDate(claimReturnDate);
					clmDtl.setClaimRetRemarks(claimRetRemarks);
					clmDtl.setTotgrandTotal(totgrandTotal);
					clmDtl.setTotGrandClmApprovedAmt(totGrandClmApprovedAmt);

					clmRefNumbersList.addElement(clmDtl);
				}
				rs.close();
				rs = null;
			}

			else if

			(clmApplicationStatusFlag.equals("RTD")) {
				while (rs.next()) {

					memberId = (String) rs.getString(1);
					memberBankName = (String) rs.getString(2);
					zoneName = (String) rs.getString(3);
					branchName = (String) rs.getString(4);
					cgpan = (String) rs.getString(5);
					unitName = (String) rs.getString(6);
					guarApprdAmount = (Double) rs.getDouble(7);
					clmRefNumber = (String) rs.getString(8);
					clmLodgmentdDt = (Date) rs.getDate(9);
					claimAppliedAmt = (Double) rs.getDouble(10);
					tempRejOrRejDt = (Date) rs.getDate(11);

					totalGuarnteAmt = totalGuarnteAmt + guarApprdAmount;
					totgrandTotalTemp = totalGuarnteAmt / 100000;
					totalClmApprovedAmt = totalClmApprovedAmt + claimAppliedAmt;
					totGrandClmApprovedAmtTemp = totalClmApprovedAmt / 100000;
					DecimalFormat decFormat = new DecimalFormat("#.00");
					totGrandClmApprovedAmt = new Double(
							decFormat.format(totGrandClmApprovedAmtTemp))
							.doubleValue();
					totgrandTotal = new Double(
							decFormat.format(totgrandTotalTemp)).doubleValue();

					clmDtl = new ClaimDetail();

					clmDtl.setMliId(memberId);
					clmDtl.setMliName(memberBankName);
					clmDtl.setBranchName(branchName);
					clmDtl.setZoneName(zoneName);
					clmDtl.setCgpan(cgpan);
					clmDtl.setSsiUnitName(unitName);
					clmDtl.setApplicationApprovedAmount(guarApprdAmount);
					clmDtl.setClaimRefNum(clmRefNumber);
					clmDtl.setClmSubmittedDt(clmLodgmentdDt);
					clmDtl.setClaimAppliedAmt(claimAppliedAmt);

					clmDtl.setTempRejOrRejDt(tempRejOrRejDt);
					clmDtl.setTotgrandTotal(totgrandTotal);
					clmDtl.setTotGrandClmApprovedAmt(totGrandClmApprovedAmt);

					clmRefNumbersList.addElement(clmDtl);
				}
				rs.close();
				rs = null;
			}

			else if

			(clmApplicationStatusFlag.equals("AS")) {
				while (rs.next()) {

					memberId = (String) rs.getString(1);
					memberBankName = (String) rs.getString(2);
					zoneName = (String) rs.getString(3);
					branchName = (String) rs.getString(4);
					cgpan = (String) rs.getString(5);
					unitName = (String) rs.getString(6);
					guarApprdAmount = (Double) rs.getDouble(7);
					clmRefNumber = (String) rs.getString(8);
					claimAppliedAmt = (Double) rs.getDouble(9);
					clmLodgmentdDt = (Date) rs.getDate(10);
					claimStatus = (String) rs.getString(11);

					clmDeclRecvdDt = (Date) rs.getDate(12);

					lastActionTakenDt = (Date) rs.getDate(13);

					totalGuarnteAmt = totalGuarnteAmt + guarApprdAmount;
					totgrandTotalTemp = totalGuarnteAmt / 100000;
					totalClmApprovedAmt = totalClmApprovedAmt + claimAppliedAmt;
					totGrandClmApprovedAmtTemp = totalClmApprovedAmt / 100000;
					DecimalFormat decFormat = new DecimalFormat("#.00");
					totGrandClmApprovedAmt = new Double(
							decFormat.format(totGrandClmApprovedAmtTemp))
							.doubleValue();
					totgrandTotal = new Double(
							decFormat.format(totgrandTotalTemp)).doubleValue();

					clmDtl = new ClaimDetail();

					clmDtl.setMliId(memberId);
					clmDtl.setMliName(memberBankName);
					clmDtl.setBranchName(branchName);
					clmDtl.setZoneName(zoneName);
					clmDtl.setCgpan(cgpan);
					clmDtl.setSsiUnitName(unitName);
					clmDtl.setApplicationApprovedAmount(guarApprdAmount);
					clmDtl.setClaimRefNum(clmRefNumber);
					clmDtl.setClaimAppliedAmt(claimAppliedAmt);
					clmDtl.setClmSubmittedDt(clmLodgmentdDt);
					clmDtl.setClmStatus(claimStatus);
					clmDtl.setClmDeclRecvdDt(clmDeclRecvdDt);

					clmDtl.setLastActionTakenDt(lastActionTakenDt);

					clmDtl.setTotgrandTotal(totgrandTotal);
					clmDtl.setTotGrandClmApprovedAmt(totGrandClmApprovedAmt);

					clmRefNumbersList.addElement(clmDtl);
				}
				rs.close();
				rs = null;
			}

			else if

			(clmApplicationStatusFlag.equals(ClaimConstants.CLM_FORWARD_STATUS)) {
				while (rs.next()) {

					memberId = (String) rs.getString(1);
					memberBankName = (String) rs.getString(2);
					zoneName = (String) rs.getString(3);
					branchName = (String) rs.getString(4);
					cgpan = (String) rs.getString(5);
					unitName = (String) rs.getString(6);
					guarApprdAmount = (Double) rs.getDouble(7);
					clmRefNumber = (String) rs.getString(8);
					clmLodgmentdDt = (Date) rs.getDate(9);
					claimForwdAmt = (double) rs.getDouble(10);
					claimForwardedDt = (Date) rs.getDate(11);

					totalGuarnteAmt = totalGuarnteAmt + guarApprdAmount;
					totgrandTotalTemp = totalGuarnteAmt / 100000;
					totalClmApprovedAmt = totalClmApprovedAmt + claimForwdAmt;
					totGrandClmApprovedAmtTemp = totalClmApprovedAmt / 100000;
					DecimalFormat decFormat = new DecimalFormat("#.00");
					totGrandClmApprovedAmt = new Double(
							decFormat.format(totGrandClmApprovedAmtTemp))
							.doubleValue();
					totgrandTotal = new Double(
							decFormat.format(totgrandTotalTemp)).doubleValue();

					clmDtl = new ClaimDetail();

					clmDtl.setMliId(memberId);
					clmDtl.setMliName(memberBankName);
					clmDtl.setBranchName(branchName);
					clmDtl.setZoneName(zoneName);
					clmDtl.setCgpan(cgpan);
					clmDtl.setSsiUnitName(unitName);
					clmDtl.setApplicationApprovedAmount(guarApprdAmount);
					clmDtl.setClaimRefNum(clmRefNumber);
					clmDtl.setClmSubmittedDt(clmLodgmentdDt);
					clmDtl.setClaimForwdAmt(claimForwdAmt);
					clmDtl.setClaimForwardedDt(claimForwardedDt);
					clmDtl.setTotgrandTotal(totgrandTotal);
					clmDtl.setTotGrandClmApprovedAmt(totGrandClmApprovedAmt);

					// Adding the ClaimDetail object to the vector
					clmRefNumbersList.addElement(clmDtl);
				}
				rs.close();
				rs = null;
			}

			else {
				while (rs.next()) {
					memberBankName = (String) rs.getString(1);
					memberId = (String) rs.getString(2);
					clmRefNumber = (String) rs.getString(3);
					unitName = (String) rs.getString(4);
					submittedDt = (Date) rs.getDate(5);

					zoneName = (String) rs.getString(6);
					branchName = (String) rs.getString(7);
					guarApprdAmount = (Double) rs.getDouble(8);

					clmDtl = new ClaimDetail();
					clmDtl.setMliName(memberBankName);
					clmDtl.setMliId(memberId);
					clmDtl.setClaimRefNum(clmRefNumber);
					clmDtl.setCGCLAN(cgclan);
					clmDtl.setSsiUnitName(unitName);
					clmDtl.setClmSubmittedDt(submittedDt);

					clmDtl.setZoneName(zoneName);
					clmDtl.setBranchName(branchName);
					clmDtl.setApplicationApprovedAmount(guarApprdAmount);

					// Adding the ClaimDetail object to the vector
					clmRefNumbersList.addElement(clmDtl);
				}
				rs.close();
				rs = null;
			}
			prepStatement.close();
			prepStatement = null;
		} catch (SQLException sqlexception) {
			// sqlexception.printStackTrace();
			throw new DatabaseException(sqlexception.getMessage());
		} finally {
			DBConnection.freeConnection(conn);
		}
		return clmRefNumbersList;
	}

	public Vector getListOfClaimRefNumbersNew3(java.sql.Date fromDate,
			java.sql.Date toDate, String clmApplicationStatusFlag,
			String bankId, String zoneId) throws DatabaseException {

		Log.log(Log.INFO, "ReportDAO", "getListOfClaimRefNumbersNew()",
				"Entered");
		Log.log(Log.INFO, "ReportDAO", "getListOfClaimRefNumbersNew()",
				"From Date :" + fromDate);
		Log.log(Log.INFO, "ReportDAO", "getListOfClaimRefNumbersNew()",
				"To Date :" + toDate);
		Log.log(Log.INFO, "ReportDAO", "getListOfClaimRefNumbersNew()",
				"clmApplicationStatusFlag :" + clmApplicationStatusFlag);
		Connection conn = null;
		PreparedStatement prepStatement = null;
		ResultSet rs = null;
		Vector clmRefNumbersList = new Vector();
		String query = null;

		try {
			conn = DBConnection.getConnection();

			if ((fromDate != null)
					&& (!(clmApplicationStatusFlag
							.equals(ClaimConstants.CLM_PENDING_STATUS) || clmApplicationStatusFlag
							.equals(ClaimConstants.CLM_HOLD_STATUS)))) {
				if (clmApplicationStatusFlag
						.equals(ClaimConstants.CLM_APPROVAL_STATUS)) {
					Log.log(Log.INFO, "ReportDAO",
							"getListOfClaimRefNumbers()",
							"From Date is NULL, Status is Approval");

					// new mod query kot1
					query = "SELECT m.mem_bnk_id || m.mem_zne_id || m.mem_brn_id memberid, m.mem_bank_name bankname, m.mem_zone_name,\n"
							+ "         DECODE (m.MEM_BRANCH_NAME,\n"
							+ "                 NULL, a.APP_MLI_BRANCH_NAME,\n"
							+ "                 m.MEM_BRANCH_NAME)\n"
							+ "            BRANCH,a.cgpan,S.SSI_UNIT_NAME, DECODE (a.app_reapprove_amount,\n"
							+ "                 NULL, a.APP_approved_amount,\n"
							+ "                 a.app_reapprove_amount)\n"
							+ "            GuarntAmt, c.clm_ref_no, c.cgclan,  c.CLM_DATE claimFilDt,ctd_tc_first_inst_pay_amt clmApprvdAmt,TRUNC (C.CLM_APPROVED_DT) clmApprovdDt\n"
							+ "                   FROM claim_detail c,\n"
							+ "         member_info m,\n"
							+ "         SSI_DETAIL S,\n"
							+ "         application_detail a,\n"
							+ "         claim_tc_detail ct\n"
							+ "   WHERE     TRUNC (c.clm_approved_dt) BETWEEN ? AND ?  \n"
							+ "         AND C.BID = S.BID\n"
							+ "         AND s.ssi_reference_number = a.ssi_reference_number\n"
							+ "         AND c.mem_bnk_id || c.mem_brn_id || c.mem_zne_id =\n"
							+ "                a.mem_bnk_id || a.mem_brn_id || a.mem_zne_id\n"
							+ "         AND LTRIM (\n"
							+ "                RTRIM (UPPER (c.mem_bnk_id || c.mem_zne_id || c.mem_brn_id))) =\n"
							+ "                LTRIM (\n"
							+ "                   RTRIM (UPPER (m.mem_bnk_id || m.mem_zne_id || m.mem_brn_id)))\n"
							+ "         AND c.clm_status = ? \n"
							+ "    and m.mem_bnk_id = ?  \n"
							+ "    and (m.mem_reporting_zone_id = ? or m.mem_zne_id = ? ) \n"
							+ "         and ct.clm_ref_no = c.clm_ref_no\n"
							+ "         and ct.cgpan = a.cgpan\n"
							+ "         and A.APP_LOAN_TYPE in ('TC')   \n"
							+ "         union all\n"
							+ "          SELECT m.mem_bnk_id || m.mem_zne_id || m.mem_brn_id memberid, m.mem_bank_name bankname, m.mem_zone_name,\n"
							+ "         DECODE (m.MEM_BRANCH_NAME,\n"
							+ "                 NULL, a.APP_MLI_BRANCH_NAME,\n"
							+ "                 m.MEM_BRANCH_NAME)\n"
							+ "            BRANCH,a.cgpan,S.SSI_UNIT_NAME, DECODE (a.app_reapprove_amount,\n"
							+ "                 NULL, a.APP_approved_amount,\n"
							+ "                 a.app_reapprove_amount)\n"
							+ "            GuarntAmt,c.clm_ref_no, c.cgclan,  c.CLM_DATE claimFilDt,cwd_wc_first_inst_pay_amt clmApprvdAmt,TRUNC (C.CLM_APPROVED_DT) clmApprovdDt\n"
							+ "    FROM claim_detail c,\n"
							+ "         member_info m,\n"
							+ "         SSI_DETAIL S,\n"
							+ "         application_detail a,\n"
							+ "         claim_wc_detail cw\n"
							+ "   WHERE     TRUNC (c.clm_approved_dt) BETWEEN ? AND ? \n"
							+ "         AND C.BID = S.BID\n"
							+ "         AND s.ssi_reference_number = a.ssi_reference_number\n"
							+ "         AND c.mem_bnk_id || c.mem_brn_id || c.mem_zne_id =\n"
							+ "                a.mem_bnk_id || a.mem_brn_id || a.mem_zne_id\n"
							+ "         AND LTRIM (\n"
							+ "                RTRIM (UPPER (c.mem_bnk_id || c.mem_zne_id || c.mem_brn_id))) =\n"
							+ "                LTRIM (\n"
							+ "                   RTRIM (UPPER (m.mem_bnk_id || m.mem_zne_id || m.mem_brn_id)))\n"
							+ "         AND c.clm_status = ? \n"
							+

							"    and m.mem_bnk_id = ?  \n"
							+ "    and (m.mem_reporting_zone_id = ? or m.mem_zne_id = ? ) \n"
							+

							"         and cw.clm_ref_no = c.clm_ref_no\n"
							+ "         and cw.cgpan = a.cgpan\n"
							+ "         and app_loan_type in ('WC')  \n"
							+

							"union all\n"
							+ " SELECT m.mem_bnk_id || m.mem_zne_id || m.mem_brn_id memberid, m.mem_bank_name bankname, m.mem_zone_name,\n"
							+ "         DECODE (m.MEM_BRANCH_NAME,\n"
							+ "                 NULL, a.APP_MLI_BRANCH_NAME,\n"
							+ "                 m.MEM_BRANCH_NAME)\n"
							+ "            BRANCH,a.cgpan,S.SSI_UNIT_NAME, DECODE (a.app_reapprove_amount,\n"
							+ "                 NULL, a.APP_approved_amount,\n"
							+ "                 a.app_reapprove_amount)\n"
							+ "            GuarntAmt,c.clm_ref_no, c.cgclan,  c.CLM_DATE claimFilDt,  nvl(cwd_wc_first_inst_pay_amt,0)+nvl(ctd_tc_first_inst_pay_amt,0) clmApprvdAmt,TRUNC (C.CLM_APPROVED_DT) clmApprovdDt\n"
							+ "    FROM claim_detail c,\n"
							+ "         member_info m,\n"
							+ "         SSI_DETAIL S,\n"
							+ "         application_detail a,\n"
							+ "         claim_wc_detail cw,\n"
							+ "         claim_tc_detail ct\n"
							+ "   WHERE     TRUNC (c.clm_approved_dt) BETWEEN ? AND ?\n"
							+ "         AND C.BID = S.BID\n"
							+ "         AND s.ssi_reference_number = a.ssi_reference_number\n"
							+ "         AND c.mem_bnk_id || c.mem_brn_id || c.mem_zne_id =\n"
							+ "                a.mem_bnk_id || a.mem_brn_id || a.mem_zne_id\n"
							+ "         AND LTRIM (\n"
							+ "                RTRIM (UPPER (c.mem_bnk_id || c.mem_zne_id || c.mem_brn_id))) =\n"
							+ "                LTRIM (\n"
							+ "                   RTRIM (UPPER (m.mem_bnk_id || m.mem_zne_id || m.mem_brn_id)))\n"
							+ "         AND c.clm_status = ? \n"
							+ "    and m.mem_bnk_id = ?  \n"
							+ "    and (m.mem_reporting_zone_id = ? or m.mem_zne_id = ? ) \n"
							+ "         and cw.clm_ref_no = c.clm_ref_no\n"
							+ "         and cw.cgpan = a.cgpan\n"
							+ "         and ct.clm_ref_no = c.clm_ref_no\n"
							+ "         and ct.cgpan = a.cgpan\n"
							+ "         and app_loan_type in ('CC')   order by clmApprovdDt,bankname ";

					prepStatement = conn.prepareStatement(query);
					prepStatement.setDate(1, fromDate);
					prepStatement.setDate(2, toDate);
					prepStatement.setString(3, clmApplicationStatusFlag);

					prepStatement.setString(4, bankId);
					prepStatement.setString(5, zoneId);
					prepStatement.setString(6, zoneId);

					prepStatement.setDate(7, fromDate);
					prepStatement.setDate(8, toDate);
					prepStatement.setString(9, clmApplicationStatusFlag);

					prepStatement.setString(10, bankId);
					prepStatement.setString(11, zoneId);
					prepStatement.setString(12, zoneId);

					prepStatement.setDate(13, fromDate);
					prepStatement.setDate(14, toDate);
					prepStatement.setString(15, clmApplicationStatusFlag);

					prepStatement.setString(16, bankId);
					prepStatement.setString(17, zoneId);
					prepStatement.setString(18, zoneId);

					rs = (ResultSet) prepStatement.executeQuery();
				}

				// kotttttt

				if (clmApplicationStatusFlag
						.equals(ClaimConstants.CLM_TEMPORARY_CLOSE)) {
					// kot5
					Log.log(Log.INFO, "ReportDAO",
							"getListOfClaimRefNumbers()",
							"From Date is NULL, Status is Reject");

					query = "SELECT a.mem_bnk_id || a.mem_zne_id || a.mem_brn_id,  \n"
							+ "                                d.mem_bank_name,  \n"
							+ "                                d.mem_zone_name,  \n"
							+ "                                DECODE (d.MEM_BRANCH_NAME,  \n"
							+ "                                        NULL, e.APP_MLI_BRANCH_NAME,  \n"
							+ "                                        d.MEM_BRANCH_NAME)  \n"
							+ "                                   branch,  \n"
							+ "                                e.cgpan,  \n"
							+ "                                c.ssi_unit_name,  \n"
							+ "                                DECODE (e.app_reapprove_amount,  \n"
							+ "                                        NULL, e.APP_approved_amount,  \n"
							+ "                                        e.app_reapprove_amount)  \n"
							+ "                                   guarantamt,  \n"
							+ "                                A.clm_ref_no,  \n"
							+ "                                a.clm_date,  \n"
							+ "                                b.caa_applied_amount clmAppdAmt,TRUNC(a.CLM_CREATED_MODIFIED_DT), \n"
							+ "                                (select distinct max(trim(upper(replace(clm_ltr_ref_no,' ',''))))\n"
							+ "                                from claim_query_detail cqd\n"
							+ "                                where a.clm_ref_no = cqd.clm_ref_no\n"
							+ "                                and clm_ltr_dt in \n"
							+ "                                (select max(clm_ltr_dt) from claim_query_detail cd where cd.clm_ref_no = cqd.clm_ref_no)\n"
							+ "                                ) clmqryrefno,\n"
							+ "                                (select distinct clm_ltr_dt\n"
							+ "                                from claim_query_detail cq\n"
							+ "                                where a.clm_ref_no = cq.clm_ref_no\n"
							+ "                                and clm_ltr_dt in \n"
							+ "                                (select max(clm_ltr_dt) from claim_query_detail cd where cd.clm_ref_no = cq.clm_ref_no)\n"
							+ "                                ) clmqrydt  \n"
							+ "                           FROM claim_detail_temp a,  \n"
							+ "                                claim_application_amount_temp b,  \n"
							+ "                                ssi_detail c,  \n"
							+ "                                member_info d,  \n"
							+ "                                application_detail e  \n"
							+ "                          WHERE     b.cgpan = e.cgpan  \n"
							+ "                                AND a.clm_ref_no = b.clm_ref_no  \n"
							+ "                                AND a.bid = c.bid  \n"
							+ "                                AND a.mem_bnk_id || a.mem_zne_id || a.mem_brn_id =  \n"
							+ "                                       d.mem_bnk_id || d.mem_zne_id || d.mem_brn_id  \n"
							+ "                               AND TRUNC (CLM_CREATED_MODIFIED_DT) BETWEEN ?  AND ?   \n"
							+ "                                AND a.clm_status = ?  \n"
							+

							"    and d.mem_bnk_id = ?  \n"
							+ "    and (d.mem_reporting_zone_id = ? or d.mem_zne_id = ? ) \n"
							+ " order by TRUNC(CLM_CREATED_MODIFIED_DT),d.mem_bank_name ";

					prepStatement = conn.prepareStatement(query);
					prepStatement.setDate(1, fromDate);
					prepStatement.setDate(2, toDate);
					prepStatement.setString(3, clmApplicationStatusFlag);
					prepStatement.setString(4, bankId);
					prepStatement.setString(5, zoneId);
					prepStatement.setString(6, zoneId);

					rs = (ResultSet) prepStatement.executeQuery();
				}

				if (clmApplicationStatusFlag.equals("RT")) {
					// kot9
					query = " SELECT  m.mem_bnk_id || m.mem_zne_id || m.mem_brn_id memberid, m.mem_bank_name bnkname,mem_zone_name zone,\n"
							+ "         decode(mem_branch_name,null,app_mli_branch_name,mem_branch_name) branch,\n"
							+ "                 a.cgpan, S.SSI_UNIT_NAME,\n"
							+ "         decode(nvl(app_reapprove_amount,0),0,app_approved_amount,app_Reapprove_amount) appamt,\n"
							+ "         c.clm_ref_no,\n"
							+ "                 caa_applied_amount clmedamt,\n"
							+ "         TRUNC (CLM_CREATED_MODIFIED_DT) rtdate,\n"
							+ "         clm_return_remarks\n"
							+ "    FROM claim_detail_temp c,\n"
							+ "         member_info m,\n"
							+ "         SSI_DETAIL S,\n"
							+ "         claim_application_amount_temp caa,\n"
							+ "         application_detail a\n"
							+ "   WHERE     TRUNC(C.CLM_CREATED_MODIFIED_DT) between ?  and ?  AND\n"
							+ "             C.BID = S.BID\n"
							+ "         AND c.clm_ref_no = caa.clm_ref_no\n"
							+ "         AND caa.cgpan = a.cgpan\n"
							+ "         AND LTRIM (\n"
							+ "                RTRIM (UPPER (c.mem_bnk_id || c.mem_zne_id || c.mem_brn_id))) =\n"
							+ "                LTRIM (\n"
							+ "                   RTRIM (UPPER (m.mem_bnk_id || m.mem_zne_id || m.mem_brn_id)))\n"
							+ "         AND c.clm_status = ?  \n"
							+ "    and m.mem_bnk_id = ?  \n"
							+ "    and (m.mem_reporting_zone_id = ? or m.mem_zne_id = ? ) \n"
							+

							"ORDER BY TRUNC (CLM_CREATED_MODIFIED_DT), bnkname";

					prepStatement = conn.prepareStatement(query);
					prepStatement.setDate(1, fromDate);
					prepStatement.setDate(2, toDate);
					prepStatement.setString(3, clmApplicationStatusFlag);
					prepStatement.setString(4, bankId);
					prepStatement.setString(5, zoneId);
					prepStatement.setString(6, zoneId);

					rs = (ResultSet) prepStatement.executeQuery();
				}

				if (clmApplicationStatusFlag.equals("AS")) {
					// kot11

					query = " SELECT  m.mem_bnk_id || m.mem_zne_id || m.mem_brn_id memberid, m.mem_bank_name bnkname,mem_zone_name zone,\n"
							+ "         decode(mem_branch_name,null,app_mli_branch_name,mem_branch_name) branch,\n"
							+ "                 a.cgpan, S.SSI_UNIT_NAME,\n"
							+ "         decode(nvl(app_reapprove_amount,0),0,app_approved_amount,app_Reapprove_amount) appamt,\n"
							+ "         c.clm_ref_no,\n"
							+ "                 caa_applied_amount clmApplAmt,CLM_DATE,\n"
							+ "         clm_status,\n"
							+ "clm_decl_recvd_dt,TRUNC(c.CLM_CREATED_MODIFIED_DT)  withdrawndt  \n"
							+ "         \n"
							+ "    FROM claim_detail_temp c,\n"
							+ "         member_info m,\n"
							+ "         SSI_DETAIL S,\n"
							+ "         claim_application_amount_temp caa,\n"
							+ "         application_detail a\n"
							+ "   WHERE     TRUNC(C.CLM_date) between ?  and ?   AND\n"
							+ "     m.mem_bnk_id = ?  \n"
							+ "           and (m.mem_reporting_zone_id = ? or m.mem_zne_id = ? ) \n"
							+ "          and   C.BID = S.BID\n"
							+ "         AND c.clm_ref_no = caa.clm_ref_no\n"
							+ "         AND caa.cgpan = a.cgpan\n"
							+ "         AND LTRIM (\n"
							+ "                RTRIM (UPPER (c.mem_bnk_id || c.mem_zne_id || c.mem_brn_id))) =\n"
							+ "                LTRIM (\n"
							+ "                   RTRIM (UPPER (m.mem_bnk_id || m.mem_zne_id || m.mem_brn_id)))  \n"
							+ "        \n"
							+ " union  all \n"
							+ " \n"
							+ "  SELECT  m.mem_bnk_id || m.mem_zne_id || m.mem_brn_id memberid, m.mem_bank_name bnkname,mem_zone_name zone,\n"
							+ "         decode(mem_branch_name,null,app_mli_branch_name,mem_branch_name) branch,\n"
							+ "                 a.cgpan, S.SSI_UNIT_NAME,\n"
							+ "         decode(nvl(app_reapprove_amount,0),0,app_approved_amount,app_Reapprove_amount) appamt,\n"
							+ "         c.clm_ref_no,\n"
							+ "                 caa_applied_amount clmApplAmt,CLM_DATE,\n"
							+ "         clm_status,\n"
							+ "clm_decl_recvd_dt,TRUNC(c.CLM_CREATED_MODIFIED_DT) withdrawndt \n"
							+ "        \n"
							+ "    FROM claim_detail c,\n"
							+ "         member_info m,\n"
							+ "         SSI_DETAIL S,\n"
							+ "         claim_application_amount caa,\n"
							+ "         application_detail a\n"
							+ "   WHERE     TRUNC(C.CLM_date) between ?  and ?  AND\n"
							+

							"     m.mem_bnk_id = ?  \n"
							+ "           and (m.mem_reporting_zone_id = ? or m.mem_zne_id = ? ) \n"
							+ "          and   C.BID = S.BID\n"
							+ "         AND c.clm_ref_no = caa.clm_ref_no\n"
							+ "         AND caa.cgpan = a.cgpan\n"
							+ "         AND LTRIM (\n"
							+ "                RTRIM (UPPER (c.mem_bnk_id || c.mem_zne_id || c.mem_brn_id))) =\n"
							+ "                LTRIM (\n"
							+ "                   RTRIM (UPPER (m.mem_bnk_id || m.mem_zne_id || m.mem_brn_id)))  order by withdrawndt, bnkname \n"
							+ "        ";

					prepStatement = conn.prepareStatement(query);
					prepStatement.setDate(1, fromDate);
					prepStatement.setDate(2, toDate);

					prepStatement.setString(3, bankId);
					prepStatement.setString(4, zoneId);
					prepStatement.setString(5, zoneId);
					prepStatement.setDate(6, fromDate);
					prepStatement.setDate(7, toDate);
					prepStatement.setString(8, bankId);
					prepStatement.setString(9, zoneId);
					prepStatement.setString(10, zoneId);
					// prepStatement.setString(3,clmApplicationStatusFlag);

					rs = (ResultSet) prepStatement.executeQuery();
				}

				if (clmApplicationStatusFlag.equals("RTD")) {
					// kot10
					query = "SELECT a.mem_bnk_id || a.mem_zne_id || a.mem_brn_id memberid,  \n"
							+ "                                d.mem_bank_name bnkname,  \n"
							+ "                                d.mem_zone_name,  \n"
							+ "                                DECODE (d.MEM_BRANCH_NAME,  \n"
							+ "                                        NULL, e.APP_MLI_BRANCH_NAME,  \n"
							+ "                                        d.MEM_BRANCH_NAME)  \n"
							+ "                                   branch,                                  e.cgpan,                                  c.ssi_unit_name,  \n"
							+ "                                DECODE (e.app_reapprove_amount,  \n"
							+ "                                        NULL, e.APP_approved_amount,  \n"
							+ "                                        e.app_reapprove_amount)  \n"
							+ "                                   guarantamt,  \n"
							+ "                                A.clm_ref_no,  \n"
							+ "                                a.clm_date,  \n"
							+ "                                b.caa_applied_amount clmApplAmt,TRUNC(a.CLM_CREATED_MODIFIED_DT) dt \n"
							+ "                           FROM claim_detail_temp a,  \n"
							+ "                                claim_application_amount_temp b,  \n"
							+ "                                ssi_detail c,  \n"
							+ "                                member_info d,  \n"
							+ "                                application_detail e  \n"
							+ "                          WHERE     b.cgpan = e.cgpan  \n"
							+ "                                AND a.clm_ref_no = b.clm_ref_no  \n"
							+ "                                AND a.bid = c.bid  \n"
							+ "                                AND a.mem_bnk_id || a.mem_zne_id || a.mem_brn_id =  \n"
							+ "                                       d.mem_bnk_id || d.mem_zne_id || d.mem_brn_id  \n"
							+ "                            AND TRUNC (a.CLM_CREATED_MODIFIED_DT) bETWEEN ?  AND ?   \n"
							+ "     d.mem_bnk_id = ?  \n"
							+ "           and (d.mem_reporting_zone_id = ? or d.mem_zne_id = ? ) \n"
							+ "                                AND a.clm_status in ('RE','TR')    union all \n"
							+ " SELECT a.mem_bnk_id || a.mem_zne_id || a.mem_brn_id memberid,d.mem_bank_name bnkname,d.mem_zone_name, DECODE (d.MEM_BRANCH_NAME,\n"
							+ "                                           NULL, e.APP_MLI_BRANCH_NAME, \n"
							+ "                                            d.MEM_BRANCH_NAME)\n"
							+ "                                       branch,e.cgpan,c.ssi_unit_name, DECODE (e.app_reapprove_amount,\n"
							+ "                                            NULL, e.APP_approved_amount,\n"
							+ "                                            e.app_reapprove_amount)\n"
							+ "                                       guarantamt,A.clm_ref_no, a.clm_date,b.caa_applied_amount clmApplAmt,TRUNC(a.CLM_CREATED_MODIFIED_DT) dt \n"
							+ "                                                 FROM claim_detail a,\n"
							+ "                                    claim_application_amount b,\n"
							+ "                                    ssi_detail c,\n"
							+ "                                    member_info d,\n"
							+ "                                    application_detail e\n"
							+ "                              WHERE   \n"
							+ "                                   b.cgpan = e.cgpan\n"
							+ "                                    AND a.clm_ref_no = b.clm_ref_no\n"
							+ "                                    AND a.bid = c.bid\n"
							+ "                                    AND a.mem_bnk_id || a.mem_zne_id || a.mem_brn_id =\n"
							+ "                                           d.mem_bnk_id || d.mem_zne_id || d.mem_brn_id\n"
							+ "                                    AND TRUNC (a.CLM_CREATED_MODIFIED_DT) BETWEEN ?  AND ? \n"
							+ "     d.mem_bnk_id = ?  \n"
							+ "           and (d.mem_reporting_zone_id = ? or d.mem_zne_id = ? ) \n"
							+ "                                    AND a.clm_status in ('RE','TR')  order by dt,bnkname ";

					prepStatement = conn.prepareStatement(query);
					prepStatement.setDate(1, fromDate);
					prepStatement.setDate(2, toDate);

					prepStatement.setString(3, bankId);
					prepStatement.setString(4, zoneId);
					prepStatement.setString(5, zoneId);
					prepStatement.setDate(6, fromDate);
					prepStatement.setDate(7, toDate);
					prepStatement.setString(8, bankId);
					prepStatement.setString(9, zoneId);
					prepStatement.setString(10, zoneId);
					// prepStatement.setString(3,clmApplicationStatusFlag);

					rs = (ResultSet) prepStatement.executeQuery();
				}

				if (clmApplicationStatusFlag
						.equals(ClaimConstants.CLM_TEMPORARY_REJECT)) {
					// kot6
					Log.log(Log.INFO, "ReportDAO",
							"getListOfClaimRefNumbers()",
							"From Date is NULL, Status is Reject");

					query = "SELECT a.mem_bnk_id || a.mem_zne_id || a.mem_brn_id memberid,  \n"
							+ "                                d.mem_bank_name,  \n"
							+ "                                d.mem_zone_name,  \n"
							+ "                                DECODE (d.MEM_BRANCH_NAME,  \n"
							+ "                                        NULL, e.APP_MLI_BRANCH_NAME,  \n"
							+ "                                        d.MEM_BRANCH_NAME)  \n"
							+ "                                   branch,  \n"
							+ "                                e.cgpan,  \n"
							+ "                                c.ssi_unit_name,  \n"
							+ "                                DECODE (e.app_reapprove_amount,  \n"
							+ "                                        NULL, e.APP_approved_amount,  \n"
							+ "                                        e.app_reapprove_amount)  \n"
							+ "                                   guarantamt,  \n"
							+ "                                A.clm_ref_no,  \n"
							+ "                                a.clm_date,  \n"
							+ "                                b.caa_applied_amount clmRejAmt,TRUNC(a.CLM_CREATED_MODIFIED_DT),clm_return_remarks \n"
							+ "                           FROM claim_detail_temp a,  \n"
							+ "                                claim_application_amount_temp b,  \n"
							+ "                                ssi_detail c,  \n"
							+ "                                member_info d,  \n"
							+ "                                application_detail e  \n"
							+ "                          WHERE     b.cgpan = e.cgpan  \n"
							+ "                                AND a.clm_ref_no = b.clm_ref_no  \n"
							+ "                                AND a.bid = c.bid  \n"
							+ "                                AND a.mem_bnk_id || a.mem_zne_id || a.mem_brn_id =  \n"
							+ "                                       d.mem_bnk_id || d.mem_zne_id || d.mem_brn_id  \n"
							+ "                               AND TRUNC (CLM_CREATED_MODIFIED_DT) BETWEEN ?  AND ?   \n"
							+ "                                AND a.clm_status = ?  \n"
							+ "     d.mem_bnk_id = ?  \n"
							+ "           and (d.mem_reporting_zone_id = ? or d.mem_zne_id = ? ) \n"
							+ " order by TRUNC(CLM_CREATED_MODIFIED_DT), d.mem_bank_name ";
					prepStatement = conn.prepareStatement(query);
					prepStatement.setDate(1, fromDate);
					prepStatement.setDate(2, toDate);
					prepStatement.setString(3, clmApplicationStatusFlag);

					prepStatement.setString(4, bankId);
					prepStatement.setString(5, zoneId);
					prepStatement.setString(6, zoneId);

					rs = (ResultSet) prepStatement.executeQuery();
				}
				if (clmApplicationStatusFlag
						.equals(ClaimConstants.CLM_WITHDRAWN)) {
					// kot7
					Log.log(Log.INFO, "ReportDAO",
							"getListOfClaimRefNumbers()",
							"From Date is NULL, Status is Reject");

					query = "SELECT a.mem_bnk_id || a.mem_zne_id || a.mem_brn_id memberid,  \n"
							+ "                                d.mem_bank_name bnkname,  \n"
							+ "                                d.mem_zone_name,  \n"
							+ "                                DECODE (d.MEM_BRANCH_NAME,  \n"
							+ "                                        NULL, e.APP_MLI_BRANCH_NAME,  \n"
							+ "                                        d.MEM_BRANCH_NAME)  \n"
							+ "                                   branch,  \n"
							+ "                                e.cgpan,  \n"
							+ "                                c.ssi_unit_name,  \n"
							+ "                                DECODE (e.app_reapprove_amount,  \n"
							+ "                                        NULL, e.APP_approved_amount,  \n"
							+ "                                        e.app_reapprove_amount)  \n"
							+ "                                   guarantamt,  \n"
							+ "                                A.clm_ref_no,  \n"
							+ "                                a.clm_date,  \n"
							+ "                                b.caa_applied_amount clmAPPAmt , TRUNC(a.CLM_CREATED_MODIFIED_DT) withdrawndt \n"
							+ "                           FROM claim_detail_temp a,  \n"
							+ "                                claim_application_amount_temp b,  \n"
							+ "                                ssi_detail c,  \n"
							+ "                                member_info d,  \n"
							+ "                                application_detail e  \n"
							+ "                          WHERE     b.cgpan = e.cgpan  \n"
							+ "                                AND a.clm_ref_no = b.clm_ref_no  \n"
							+ "                                AND a.bid = c.bid  \n"
							+ "                                AND a.mem_bnk_id || a.mem_zne_id || a.mem_brn_id =  \n"
							+ "                                       d.mem_bnk_id || d.mem_zne_id || d.mem_brn_id  \n"
							+ "                               AND TRUNC (a.CLM_CREATED_MODIFIED_DT) BETWEEN ?  AND ?   \n"
							+ "                                AND a.clm_status = ?     \n"
							+

							"    and d.mem_bnk_id = ?  \n"
							+ "    and (d.mem_reporting_zone_id = ? or d.mem_zne_id = ? ) \n"
							+ "union all \n"
							+ "SELECT a.mem_bnk_id || a.mem_zne_id || a.mem_brn_id memberid,  \n"
							+ "                                d.mem_bank_name bnkname,  \n"
							+ "                                d.mem_zone_name,  \n"
							+ "                                DECODE (d.MEM_BRANCH_NAME,  \n"
							+ "                                        NULL, e.APP_MLI_BRANCH_NAME,  \n"
							+ "                                        d.MEM_BRANCH_NAME)  \n"
							+ "                                   branch,  \n"
							+ "                                e.cgpan,  \n"
							+ "                                c.ssi_unit_name,  \n"
							+ "                                DECODE (e.app_reapprove_amount,  \n"
							+ "                                        NULL, e.APP_approved_amount,  \n"
							+ "                                        e.app_reapprove_amount)  \n"
							+ "                                   guarantamt,  \n"
							+ "                                A.clm_ref_no,  \n"
							+ "                                a.clm_date,  \n"
							+ "                                b.caa_applied_amount clmappAmt,TRUNC(a.CLM_CREATED_MODIFIED_DT) withdrawndt  \n"
							+ "                           FROM claim_detail a,  \n"
							+ "                                claim_application_amount b,  \n"
							+ "                                ssi_detail c,  \n"
							+ "                                member_info d,  \n"
							+ "                                application_detail e  \n"
							+ "                          WHERE     b.cgpan = e.cgpan  \n"
							+ "                                AND a.clm_ref_no = b.clm_ref_no  \n"
							+ "                                AND a.bid = c.bid  \n"
							+ "                                AND a.mem_bnk_id || a.mem_zne_id || a.mem_brn_id =  \n"
							+ "                                       d.mem_bnk_id || d.mem_zne_id || d.mem_brn_id  \n"
							+ "                                AND TRUNC (CLM_CREATED_MODIFIED_DT) BETWEEN ?  AND ?   \n"
							+ "                                AND a.clm_status = ?  \n"
							+ "    and d.mem_bnk_id = ?  \n"
							+ "    and (d.mem_reporting_zone_id = ? or d.mem_zne_id = ? ) \n"
							+ " order by withdrawndt, bnkname  ";
					prepStatement = conn.prepareStatement(query);
					prepStatement.setDate(1, fromDate);
					prepStatement.setDate(2, toDate);
					prepStatement.setString(3, clmApplicationStatusFlag);

					prepStatement.setString(4, bankId);
					prepStatement.setString(5, zoneId);
					prepStatement.setString(6, zoneId);

					prepStatement.setDate(7, fromDate);
					prepStatement.setDate(8, toDate);
					prepStatement.setString(9, clmApplicationStatusFlag);

					prepStatement.setString(10, bankId);
					prepStatement.setString(11, zoneId);
					prepStatement.setString(12, zoneId);

					rs = (ResultSet) prepStatement.executeQuery();
				}

				if (clmApplicationStatusFlag
						.equals(ClaimConstants.CLM_REJECT_STATUS)) {
					// kot3
					Log.log(Log.INFO, "ReportDAO",
							"getListOfClaimRefNumbers()",
							"From Date is NULL, Status is Reject");

					query = "  SELECT a.mem_bnk_id || a.mem_zne_id || a.mem_brn_id,d.mem_bank_name,d.mem_zone_name, DECODE (d.MEM_BRANCH_NAME,\n"
							+ "                 NULL, e.APP_MLI_BRANCH_NAME,\n"
							+ "                 d.MEM_BRANCH_NAME)\n"
							+ "            branch,e.cgpan,c.ssi_unit_name, DECODE (e.app_reapprove_amount,\n"
							+ "                 NULL, e.APP_approved_amount,\n"
							+ "                 e.app_reapprove_amount)\n"
							+ "            guarantamt,A.clm_ref_no, a.clm_date,b.caa_applied_amount clmAppdAmt, TRUNC(a.CLM_CREATED_MODIFIED_DT),clm_return_remarks \n"
							+ "                      FROM claim_detail a,\n"
							+ "         claim_application_amount b,\n"
							+ "         ssi_detail c,\n"
							+ "         member_info d,\n"
							+ "         application_detail e\n"
							+ "   WHERE   \n"
							+ "        b.cgpan = e.cgpan\n"
							+ "         AND a.clm_ref_no = b.clm_ref_no\n"
							+ "         AND a.bid = c.bid\n"
							+ "         AND a.mem_bnk_id || a.mem_zne_id || a.mem_brn_id =\n"
							+ "                d.mem_bnk_id || d.mem_zne_id || d.mem_brn_id\n"
							+ "         AND TRUNC (a.CLM_CREATED_MODIFIED_DT) BETWEEN ?  AND ? \n"
							+ "         AND a.clm_status = ? \n"
							+ "    and d.mem_bnk_id = ?  \n"
							+ "    and (d.mem_reporting_zone_id = ? or d.mem_zne_id = ? ) \n"
							+ "  order by TRUNC(CLM_CREATED_MODIFIED_DT), d.mem_bank_name  ";

					prepStatement = conn.prepareStatement(query);
					prepStatement.setDate(1, fromDate);
					prepStatement.setDate(2, toDate);
					prepStatement.setString(3, clmApplicationStatusFlag);
					prepStatement.setString(4, bankId);
					prepStatement.setString(5, zoneId);
					prepStatement.setString(6, zoneId);

					rs = (ResultSet) prepStatement.executeQuery();
				}
				// ADDED FORWARD REPORT BY SUKUMAR@PATH ON 12-09-2009
				if (clmApplicationStatusFlag
						.equals(ClaimConstants.CLM_FORWARD_STATUS)) {
					Log.log(Log.INFO, "ReportDAO",
							"getListOfClaimRefNumbers()",
							"From Date is NULL, Status is  Forward");
					// kot4
					query = "SELECT m.mem_bnk_id || m.mem_zne_id || m.mem_brn_id,\n"
							+ "       m.mem_bank_name bnkname,\n"
							+ "       m.mem_zone_name,\n"
							+ "       DECODE (m.MEM_BRANCH_NAME,\n"
							+ "               NULL, a.APP_MLI_BRANCH_NAME,\n"
							+ "               m.MEM_BRANCH_NAME)\n"
							+ "          BRANCH,\n"
							+ "       d.cgpan,\n"
							+ "       S.SSI_UNIT_NAME,\n"
							+ "       DECODE (a.app_reapprove_amount,\n"
							+ "               NULL, a.APP_approved_amount,\n"
							+ "               a.app_reapprove_amount)\n"
							+ "          guartAmt,\n"
							+ "       c.clm_ref_no,\n"
							+ "       C.CLM_DATE clmFilgDt,\n"
							+ "       d.ctd_tc_first_inst_pay_amt clmForwrdAmt, \n"
							+ "       TRUNC (CLM_CREATED_MODIFIED_DT) clmForwdDt \n"
							+ "  FROM claim_detail_temp c,\n"
							+ "       member_info m,\n"
							+ "       SSI_DETAIL S,\n"
							+ "       application_detail a,\n"
							+ "       CLAIM_TC_DETAIL_TEMP D\n"
							+ " WHERE     TRUNC (C.CLM_CREATED_MODIFIED_DT) BETWEEN ? \n"
							+ "                                                 AND  ? \n"
							+ "       AND C.BID = S.BID\n"
							+ "       AND s.ssi_reference_number = a.ssi_reference_number\n"
							+ "       AND c.mem_bnk_id || c.mem_brn_id || c.mem_zne_id =\n"
							+ "              a.mem_bnk_id || a.mem_brn_id || a.mem_zne_id\n"
							+ "       AND LTRIM (\n"
							+ "              RTRIM (UPPER (c.mem_bnk_id || c.mem_zne_id || c.mem_brn_id))) =\n"
							+ "              LTRIM (\n"
							+ "                 RTRIM (UPPER (m.mem_bnk_id || m.mem_zne_id || m.mem_brn_id)))\n"
							+ "       AND c.clm_status = ? \n"
							+ "    and m.mem_bnk_id = ?  \n"
							+ "    and (m.mem_reporting_zone_id = ? or m.mem_zne_id = ? ) \n"
							+ "       AND C.CLM_REF_NO = D.CLM_REF_NO\n"
							+ "       AND d.cgpan = a.cgpan\n"
							+ "       AND app_loan_type IN ('TC')   \n"
							+ "UNION ALL\n"
							+ "SELECT m.mem_bnk_id || m.mem_zne_id || m.mem_brn_id,\n"
							+ "       m.mem_bank_name bnkname,\n"
							+ "       m.mem_zone_name,\n"
							+ "       DECODE (m.MEM_BRANCH_NAME,\n"
							+ "               NULL, a.APP_MLI_BRANCH_NAME,\n"
							+ "               m.MEM_BRANCH_NAME)\n"
							+ "          BRANCH,\n"
							+ "       d.cgpan,\n"
							+ "       S.SSI_UNIT_NAME,\n"
							+ "       DECODE (a.app_reapprove_amount,\n"
							+ "               NULL, a.APP_approved_amount,\n"
							+ "               a.app_reapprove_amount)\n"
							+ "          guartAmt,\n"
							+ "       c.clm_ref_no,\n"
							+ "       C.CLM_DATE clmFilgDt,\n"
							+ "       d.cwd_wc_first_inst_pay_amt clmForwrdAmt,\n"
							+ "       TRUNC (CLM_CREATED_MODIFIED_DT) clmForwdDt \n"
							+ "  FROM claim_detail_temp c,\n"
							+ "       member_info m,\n"
							+ "       SSI_DETAIL S,\n"
							+ "       application_detail a,\n"
							+ "       CLAIM_WC_DETAIL_TEMP D\n"
							+ " WHERE     TRUNC (C.CLM_CREATED_MODIFIED_DT) BETWEEN ? \n"
							+ "                                                 AND ?  \n"
							+ "       AND C.BID = S.BID\n"
							+ "       AND s.ssi_reference_number = a.ssi_reference_number\n"
							+ "       AND c.mem_bnk_id || c.mem_brn_id || c.mem_zne_id =\n"
							+ "              a.mem_bnk_id || a.mem_brn_id || a.mem_zne_id\n"
							+ "       AND LTRIM (\n"
							+ "              RTRIM (UPPER (c.mem_bnk_id || c.mem_zne_id || c.mem_brn_id))) =\n"
							+ "              LTRIM (\n"
							+ "                 RTRIM (UPPER (m.mem_bnk_id || m.mem_zne_id || m.mem_brn_id)))\n"
							+ "       AND c.clm_status = ? \n"
							+ "    and m.mem_bnk_id = ?  \n"
							+ "    and (m.mem_reporting_zone_id = ? or m.mem_zne_id = ? ) \n"
							+ "       AND C.CLM_REF_NO = D.CLM_REF_NO\n"
							+ "       AND d.cgpan = a.cgpan\n"
							+ "       AND app_loan_type IN ('WC')   \n"
							+ "UNION ALL\n"
							+ "SELECT m.mem_bnk_id || m.mem_zne_id || m.mem_brn_id,\n"
							+ "       m.mem_bank_name bnkname,\n"
							+ "       m.mem_zone_name,\n"
							+ "       DECODE (m.MEM_BRANCH_NAME,\n"
							+ "               NULL, a.APP_MLI_BRANCH_NAME,\n"
							+ "               m.MEM_BRANCH_NAME)\n"
							+ "          BRANCH,\n"
							+ "       d.cgpan,\n"
							+ "       S.SSI_UNIT_NAME,\n"
							+ "       DECODE (a.app_reapprove_amount,\n"
							+ "               NULL, a.APP_approved_amount,\n"
							+ "               a.app_reapprove_amount)\n"
							+ "          guartAmt,\n"
							+ "       c.clm_ref_no,\n"
							+ "       C.CLM_DATE clmFilgDt,\n"
							+ "         NVL (d.cwd_wc_first_inst_pay_amt, 0)\n"
							+ "       + NVL (CWD_WC_FIRST_INST_PAY_AMT, 0)\n"
							+ "          clmForwrdAmt,\n"
							+ "       TRUNC (CLM_CREATED_MODIFIED_DT) clmForwdDt \n"
							+ "  FROM claim_detail_temp c,\n"
							+ "       member_info m,\n"
							+ "       SSI_DETAIL S,\n"
							+ "       application_detail a,\n"
							+ "       CLAIM_WC_DETAIL_TEMP D,\n"
							+ "       CLAIM_TC_DETAIL_TEMP E\n"
							+ " WHERE     TRUNC (C.CLM_CREATED_MODIFIED_DT) BETWEEN ? \n"
							+ "                                                 AND ? \n"
							+ "       AND C.BID = S.BID\n"
							+ "       AND s.ssi_reference_number = a.ssi_reference_number\n"
							+ "       AND c.mem_bnk_id || c.mem_brn_id || c.mem_zne_id =\n"
							+ "              a.mem_bnk_id || a.mem_brn_id || a.mem_zne_id\n"
							+ "       AND LTRIM (\n"
							+ "              RTRIM (UPPER (c.mem_bnk_id || c.mem_zne_id || c.mem_brn_id))) =\n"
							+ "              LTRIM (\n"
							+ "                 RTRIM (UPPER (m.mem_bnk_id || m.mem_zne_id || m.mem_brn_id)))\n"
							+ "       AND c.clm_status = ? \n"
							+ "    and m.mem_bnk_id = ?  \n"
							+ "    and (m.mem_reporting_zone_id = ? or m.mem_zne_id = ? ) \n"
							+ "       AND C.CLM_REF_NO = D.CLM_REF_NO\n"
							+ "       AND D.cgpan = a.cgpan\n"
							+ "       AND C.CLM_REF_NO = E.CLM_REF_NO\n"
							+ "       AND E.cgpan = a.cgpan\n"
							+ "       AND app_loan_type IN ('CC') order by clmForwdDt, bnkname ";

					prepStatement = conn.prepareStatement(query);
					prepStatement.setDate(1, fromDate);
					prepStatement.setDate(2, toDate);
					prepStatement.setString(3, clmApplicationStatusFlag);

					prepStatement.setString(4, bankId);
					prepStatement.setString(5, zoneId);
					prepStatement.setString(6, zoneId);

					prepStatement.setDate(7, fromDate);
					prepStatement.setDate(8, toDate);
					prepStatement.setString(9, clmApplicationStatusFlag);

					prepStatement.setString(10, bankId);
					prepStatement.setString(11, zoneId);
					prepStatement.setString(12, zoneId);

					prepStatement.setDate(13, fromDate);
					prepStatement.setDate(14, toDate);
					prepStatement.setString(15, clmApplicationStatusFlag);

					prepStatement.setString(16, bankId);
					prepStatement.setString(17, zoneId);
					prepStatement.setString(18, zoneId);
					rs = (ResultSet) prepStatement.executeQuery();
				}
				/*
				 * ADDED BY SUKUMAR@PATH ON 20-FEB-2010 FOR DISPLAY TEMPORARY
				 * CLOSE & TEMPORARY REJECT APPLICATION
				 */
				if (clmApplicationStatusFlag.equals("KTC")
						|| clmApplicationStatusFlag.equals("KWC")
						|| clmApplicationStatusFlag.equals("KWD")) {
					Log.log(Log.INFO, "ReportDAO",
							"getListOfClaimRefNumbers()",
							"From Date is NULL, Status is  TEMPORARY CLOSED");
					// new kot
					query = "SELECT a.mem_bnk_id || a.mem_zne_id || a.mem_brn_id,\n"
							+ "       d.mem_bank_name,\n"
							+ "       d.mem_zone_name,\n"
							+ "       DECODE (d.MEM_BRANCH_NAME,\n"
							+ "               NULL, e.APP_MLI_BRANCH_NAME,\n"
							+ "               d.MEM_BRANCH_NAME)\n"
							+ "          branch,\n"
							+ "       e.cgpan,\n"
							+ "       c.ssi_unit_name,\n"
							+ "       DECODE (e.app_reapprove_amount,\n"
							+ "               NULL, e.APP_approved_amount,\n"
							+ "               e.app_reapprove_amount)\n"
							+ "          guarantamt,\n"
							+ "       A.clm_ref_no,\n"
							+ "       a.clm_date,\n"
							+ "       b.caa_applied_amount clmAppdAmt\n"
							+ "  FROM claim_detail_temp a,\n"
							+ "       claim_application_amount_temp b,\n"
							+ "       ssi_detail c,\n"
							+ "       member_info d,\n"
							+ "       application_detail e\n"
							+ " WHERE     b.cgpan = e.cgpan\n"
							+ "       AND a.clm_ref_no = b.clm_ref_no\n"
							+ "       AND a.bid = c.bid\n"
							+ "       AND a.mem_bnk_id || a.mem_zne_id || a.mem_brn_id =\n"
							+ "              d.mem_bnk_id || d.mem_zne_id || d.mem_brn_id\n"
							+ "       AND TRUNC (CLM_DATE) BETWEEN ?  AND ? \n"
							+ "       AND a.clm_status = ? \n";

					prepStatement = conn.prepareStatement(query);
					prepStatement.setDate(1, fromDate);
					prepStatement.setDate(2, toDate);
					prepStatement.setString(3, clmApplicationStatusFlag);

					rs = (ResultSet) prepStatement.executeQuery();
				}
				// added by upchar@path on 03/07/2013 to display reply received
				// claim applications
				if (clmApplicationStatusFlag
						.equals(ClaimConstants.CLM_REPLY_RECEIVED)) {
					// kot8
					Log.log(Log.INFO, "ReportDAO",
							"getListOfClaimRefNumbers()",
							"From Date is NULL, Status is  TEMPORARY CLOSED");

					query = "SELECT a.mem_bnk_id || a.mem_zne_id || a.mem_brn_id memberid,  \n"
							+ "                                d.mem_bank_name,  \n"
							+ "                                d.mem_zone_name,  \n"
							+ "                                DECODE (d.MEM_BRANCH_NAME,  \n"
							+ "                                        NULL, e.APP_MLI_BRANCH_NAME,  \n"
							+ "                                        d.MEM_BRANCH_NAME)  \n"
							+ "                                   branch,  \n"
							+ "                                e.cgpan,  \n"
							+ "                                c.ssi_unit_name,  \n"
							+ "                                DECODE (e.app_reapprove_amount,  \n"
							+ "                                        NULL, e.APP_approved_amount,  \n"
							+ "                                        e.app_reapprove_amount)  \n"
							+ "                                   guarantamt,  \n"
							+ "                                A.clm_ref_no,  \n"
							+ "                                a.clm_date,  \n"
							+ "                                b.caa_applied_amount clmAppdAmt,TRUNC(a.CLM_CREATED_MODIFIED_DT), \n"
							+ "                                (select distinct max(trim(upper(replace(clm_ltr_ref_no,' ',''))))\n"
							+ "                                from claim_query_detail cqd\n"
							+ "                                where a.clm_ref_no = cqd.clm_ref_no\n"
							+ "                                and clm_ltr_dt in \n"
							+ "                                (select max(clm_ltr_dt) from claim_query_detail cd where cd.clm_ref_no = cqd.clm_ref_no)\n"
							+ "                                ) clmqryrefno,\n"
							+ "                                (select distinct clm_ltr_dt\n"
							+ "                                from claim_query_detail cq\n"
							+ "                                where a.clm_ref_no = cq.clm_ref_no\n"
							+ "                                and clm_ltr_dt in \n"
							+ "                                (select max(clm_ltr_dt) from claim_query_detail cd where cd.clm_ref_no = cq.clm_ref_no)\n"
							+ "                                ) clmqrydt,\n"
							+ "                                (select inward_id from claim_reply_detail crd\n"
							+ "                                where crd.clm_ref_no = a.clm_ref_no\n"
							+ "                                and inward_dt in \n"
							+ "                                (select max(inward_dt) from claim_reply_detail cd where cd.clm_ref_no = crd.clm_ref_no)\n"
							+ "                                ) rplyinwid,\n"
							+ "                                (select inward_dt from claim_reply_detail crd\n"
							+ "                                where crd.clm_ref_no = a.clm_ref_no\n"
							+ "                                and inward_dt in \n"
							+ "                                (select max(inward_dt) from claim_reply_detail cd where cd.clm_ref_no = crd.clm_ref_no)\n"
							+ "                                ) rplyinwdt ,TRUNC(a.CLM_CREATED_MODIFIED_DT) \n"
							+ "                           FROM claim_detail_temp a,  \n"
							+ "                                claim_application_amount_temp b,  \n"
							+ "                                ssi_detail c,  \n"
							+ "                                member_info d,  \n"
							+ "                                application_detail e  \n"
							+ "                          WHERE     b.cgpan = e.cgpan  \n"
							+ "                                AND a.clm_ref_no = b.clm_ref_no  \n"
							+ "                                AND a.bid = c.bid  \n"
							+ "                                AND a.mem_bnk_id || a.mem_zne_id || a.mem_brn_id =  \n"
							+ "                                       d.mem_bnk_id || d.mem_zne_id || d.mem_brn_id  \n"
							+ "                               AND TRUNC (CLM_CREATED_MODIFIED_DT) BETWEEN ?  AND ?   \n"
							+ "                                AND a.clm_status = ?  \n"
							+ "    and d.mem_bnk_id = ?  \n"
							+ "    and (d.mem_reporting_zone_id = ? or d.mem_zne_id = ? ) \n"
							+ " order by TRUNC(CLM_CREATED_MODIFIED_DT),d.mem_bank_name ";

					prepStatement = conn.prepareStatement(query);
					prepStatement.setDate(1, fromDate);
					prepStatement.setDate(2, toDate);
					prepStatement.setString(3, clmApplicationStatusFlag);
					prepStatement.setString(4, bankId);
					prepStatement.setString(5, zoneId);
					prepStatement.setString(6, zoneId);

					rs = (ResultSet) prepStatement.executeQuery();
				}
			}
			if ((fromDate != null)
					&& ((clmApplicationStatusFlag
							.equals(ClaimConstants.CLM_PENDING_STATUS) || clmApplicationStatusFlag
							.equals(ClaimConstants.CLM_HOLD_STATUS)
					// clmApplicationStatusFlag.equals(ClaimConstants.CLM_FORWARD_STATUS)
					))) {
				Log.log(Log.INFO, "ReportDAO", "getListOfClaimRefNumbers()",
						"From Date is NULL, Status is Pending or Forward or Hold");
				// new kot2
				query = "SELECT  m.mem_bnk_id || m.mem_zne_id || m.mem_brn_id,m.mem_bank_name bnkname,m.mem_zone_name, DECODE (m.MEM_BRANCH_NAME,\n"
						+ "                 NULL, a.APP_MLI_BRANCH_NAME,\n"
						+ "                 m.MEM_BRANCH_NAME)\n"
						+ "            BRANCH,a.cgpan,S.SSI_UNIT_NAME,DECODE (a.app_reapprove_amount,\n"
						+ "                 NULL, a.APP_approved_amount,\n"
						+ "                 a.app_reapprove_amount)\n"
						+ "            GuarAmt,c.clm_ref_no, C.CLM_DATE, b.caa_applied_amount clmAppliedAmt,C.CLM_DECLARATION_RECVD clmDeclRcvdFlg, C.CLM_DECL_RECVD_DT clmDeclRecvdDt\n"
						+ "                                      \n"
						+ "    FROM claim_detail_temp c,\n"
						+ "    claim_application_amount_temp b,\n"
						+ "         member_info m,\n"
						+ "         SSI_DETAIL S,\n"
						+ "         application_detail a\n"
						+ "         \n"
						+ "   WHERE     TRUNC (c.clm_date) BETWEEN ?  AND ? \n"
						+ "         AND C.BID = S.BID\n"
						+ "         AND s.ssi_reference_number = a.ssi_reference_number\n"
						+ "         AND c.mem_bnk_id || c.mem_brn_id || c.mem_zne_id =\n"
						+ "                a.mem_bnk_id || a.mem_brn_id || a.mem_zne_id\n"
						+ "         AND LTRIM (\n"
						+ "                RTRIM (UPPER (c.mem_bnk_id || c.mem_zne_id || c.mem_brn_id))) =\n"
						+ "                LTRIM (\n"
						+ "                   RTRIM (UPPER (m.mem_bnk_id || m.mem_zne_id || m.mem_brn_id)))\n"
						+ "         AND c.clm_status = ? \n"
						+ "    and m.mem_bnk_id = ?  \n"
						+ "    and (m.mem_reporting_zone_id = ? or m.mem_zne_id = ? ) \n"
						+ "         AND C.CLM_REF_NO = b.CLM_REF_NO\n"
						+ "         AND b.CGPAN = a.CGPAN  order by TRUNC(CLM_CREATED_MODIFIED_DT),bnkname ";

				prepStatement = conn.prepareStatement(query);
				prepStatement.setDate(1, fromDate);
				prepStatement.setDate(2, toDate);
				prepStatement.setString(3, clmApplicationStatusFlag);
				prepStatement.setString(4, bankId);
				prepStatement.setString(5, zoneId);
				prepStatement.setString(6, zoneId);

				rs = (ResultSet) prepStatement.executeQuery();
			}
			if ((fromDate == null)
					&& (!(clmApplicationStatusFlag
							.equals(ClaimConstants.CLM_PENDING_STATUS) || clmApplicationStatusFlag
							.equals(ClaimConstants.CLM_HOLD_STATUS)
					// clmApplicationStatusFlag.equals(ClaimConstants.CLM_FORWARD_STATUS)
					))) {
				if (clmApplicationStatusFlag
						.equals(ClaimConstants.CLM_APPROVAL_STATUS)) {
					Log.log(Log.INFO, "ReportDAO",
							"getListOfClaimRefNumbers()",
							"From Date is NULL, Status is Approval");
					query = "select m.mem_bank_name bnkname, m.mem_bnk_id || m.mem_zne_id || m.mem_brn_id, c.clm_ref_no, c.cgclan,S.SSI_UNIT_NAME,TRUNC(CLM_APPROVED_DT),CLM_APPROVED_AMT "
							+ " from claim_detail c, member_info m,SSI_DETAIL S "
							+
							// code changed clm_date to clm_approved_dt by
							// sukumar@path on 11-09-2009
							// " where c.clm_date <= ? AND C.BID=S.BID " +
							" where TRUNC(c.clm_approved_dt) <= ? AND C.BID=S.BID "
							+ " and LTRIM(RTRIM(UPPER(c.mem_bnk_id || c.mem_zne_id || c.mem_brn_id))) = LTRIM(RTRIM(UPPER(m.mem_bnk_id || m.mem_zne_id || m.mem_brn_id))) "
							+ " and c.clm_status = ?"
							+ " group by m.mem_bank_name,"
							+ " m.mem_bnk_id || m.mem_zne_id || m.mem_brn_id,"
							+ " c.clm_ref_no,"
							+ " c.cgclan,S.SSI_UNIT_NAME,TRUNC(CLM_APPROVED_DT),CLM_APPROVED_AMT order by Trunc(c.clm_approved_dt),bnkname";
					prepStatement = conn.prepareStatement(query);
					// prepStatement.setDate(1,fromDate);
					prepStatement.setDate(1, toDate);
					prepStatement.setString(2, clmApplicationStatusFlag);
					rs = (ResultSet) prepStatement.executeQuery();
				} else if (clmApplicationStatusFlag
						.equals(ClaimConstants.CLM_REJECT_STATUS)) {
					Log.log(Log.INFO, "ReportDAO",
							"getListOfClaimRefNumbers()",
							"From Date is NULL, Status is Reject");

					query = "  SELECT a.mem_bnk_id || a.mem_zne_id || a.mem_brn_id,d.mem_bank_name,d.mem_zone_name, DECODE (d.MEM_BRANCH_NAME,\n"
							+ "                 NULL, e.APP_MLI_BRANCH_NAME,\n"
							+ "                 d.MEM_BRANCH_NAME)\n"
							+ "            branch,e.cgpan,c.ssi_unit_name, DECODE (e.app_reapprove_amount,\n"
							+ "                 NULL, e.APP_approved_amount,\n"
							+ "                 e.app_reapprove_amount)\n"
							+ "            guarantamt,A.clm_ref_no, a.clm_date,b.caa_applied_amount clmAppdAmt \n"
							+ "                      FROM claim_detail a,\n"
							+ "         claim_application_amount b,\n"
							+ "         ssi_detail c,\n"
							+ "         member_info d,\n"
							+ "         application_detail e\n"
							+ "   WHERE   \n"
							+ "        b.cgpan = e.cgpan\n"
							+ "         AND a.clm_ref_no = b.clm_ref_no\n"
							+ "         AND a.bid = c.bid\n"
							+ "         AND a.mem_bnk_id || a.mem_zne_id || a.mem_brn_id =\n"
							+ "                d.mem_bnk_id || d.mem_zne_id || d.mem_brn_id\n"
							+ "         AND TRUNC (a.CLM_CREATED_MODIFIED_DT) BETWEEN ?  AND ? \n"
							+ "         AND a.clm_status = ? ";

					prepStatement = conn.prepareStatement(query);
					prepStatement.setDate(1, fromDate);
					prepStatement.setDate(2, toDate);
					prepStatement.setString(3, clmApplicationStatusFlag);

					rs = (ResultSet) prepStatement.executeQuery();
				} else if (clmApplicationStatusFlag
						.equals(ClaimConstants.CLM_FORWARD_STATUS)) {
					Log.log(Log.INFO, "ReportDAO",
							"getListOfClaimRefNumbers()",
							"From Date is NULL, Status is Forward");
					query = "select m.mem_bank_name bnkname, m.mem_bnk_id || m.mem_zne_id || m.mem_brn_id, c.clm_ref_no,S.SSI_UNIT_NAME,TRUNC(CLM_CREATED_MODIFIED_DT) "
							+ " from claim_detail_temp c, member_info m,SSI_DETAIL S"
							+ " where TRUNC(C.CLM_CREATED_MODIFIED_DT) <= ? AND C.BID=S.BID "
							+ " and LTRIM(RTRIM(UPPER(c.mem_bnk_id || c.mem_zne_id || c.mem_brn_id))) = LTRIM(RTRIM(UPPER(m.mem_bnk_id || m.mem_zne_id || m.mem_brn_id))) "
							+ " and c.clm_status = ?"
							+ " group by m.mem_bank_name,"
							+ " m.mem_bnk_id || m.mem_zne_id || m.mem_brn_id,"
							+ " c.clm_ref_no,S.SSI_UNIT_NAME,TRUNC(CLM_CREATED_MODIFIED_DT) order by TRUNC(CLM_CREATED_MODIFIED_DT),bnkname";

					prepStatement = conn.prepareStatement(query);
					prepStatement.setDate(1, toDate);
					prepStatement.setString(2, clmApplicationStatusFlag);
					rs = (ResultSet) prepStatement.executeQuery();
				} else if (clmApplicationStatusFlag
						.equals(ClaimConstants.CLM_TEMPORARY_CLOSE)
						|| clmApplicationStatusFlag
								.equals(ClaimConstants.CLM_TEMPORARY_REJECT)
						|| clmApplicationStatusFlag
								.equals(ClaimConstants.CLM_WITHDRAWN)) {
					Log.log(Log.INFO, "ReportDAO",
							"getListOfClaimRefNumbers()",
							"From Date is NULL, Status is Forward");
					query = "select m.mem_bank_name bnkname, m.mem_bnk_id || m.mem_zne_id || m.mem_brn_id, c.clm_ref_no,S.SSI_UNIT_NAME,TRUNC(CLM_CREATED_MODIFIED_DT) "
							+ " from claim_detail_temp c, member_info m,SSI_DETAIL S"
							+ " where TRUNC(C.CLM_CREATED_MODIFIED_DT) <= ? AND C.BID=S.BID "
							+ " and LTRIM(RTRIM(UPPER(c.mem_bnk_id || c.mem_zne_id || c.mem_brn_id))) = LTRIM(RTRIM(UPPER(m.mem_bnk_id || m.mem_zne_id || m.mem_brn_id))) "
							+ " and c.clm_status = ?"
							+ " group by m.mem_bank_name,"
							+ " m.mem_bnk_id || m.mem_zne_id || m.mem_brn_id,"
							+ " c.clm_ref_no,S.SSI_UNIT_NAME,TRUNC(CLM_CREATED_MODIFIED_DT) order by TRUNC(CLM_CREATED_MODIFIED_DT),bnkname";
					prepStatement = conn.prepareStatement(query);
					prepStatement.setDate(1, toDate);
					prepStatement.setString(2, clmApplicationStatusFlag);
					rs = (ResultSet) prepStatement.executeQuery();
				} else if (clmApplicationStatusFlag
						.equals(ClaimConstants.CLM_REPLY_RECEIVED)) {
					Log.log(Log.INFO, "ReportDAO",
							"getListOfClaimRefNumbers()",
							"From Date is NULL, Status is  TEMPORARY CLOSED");
					query = "select m.mem_bank_name bnkname, m.mem_bnk_id || m.mem_zne_id || m.mem_brn_id, c.clm_ref_no,S.SSI_UNIT_NAME,TRUNC(CLM_CREATED_MODIFIED_DT) "
							+ " from claim_detail_temp c, member_info m,SSI_DETAIL S"
							+ " where TRUNC(C.CLM_CREATED_MODIFIED_DT) <= ? AND C.BID=S.BID "
							+ " and LTRIM(RTRIM(UPPER(c.mem_bnk_id || c.mem_zne_id || c.mem_brn_id))) = LTRIM(RTRIM(UPPER(m.mem_bnk_id || m.mem_zne_id || m.mem_brn_id))) "
							+ " and c.clm_status = ?"
							+ " group by m.mem_bank_name,"
							+ " m.mem_bnk_id || m.mem_zne_id || m.mem_brn_id,"
							+ " c.clm_ref_no,S.SSI_UNIT_NAME,TRUNC(CLM_CREATED_MODIFIED_DT) order by TRUNC(CLM_CREATED_MODIFIED_DT),bnkname";
					prepStatement = conn.prepareStatement(query);
					prepStatement.setDate(1, toDate);
					prepStatement.setString(2, clmApplicationStatusFlag);
					rs = (ResultSet) prepStatement.executeQuery();
				}
			}
			if ((fromDate == null)
					&& ((clmApplicationStatusFlag
							.equals(ClaimConstants.CLM_PENDING_STATUS) || clmApplicationStatusFlag
							.equals(ClaimConstants.CLM_HOLD_STATUS)
					// clmApplicationStatusFlag.equals(ClaimConstants.CLM_FORWARD_STATUS)
					))) {
				Log.log(Log.INFO, "ReportDAO", "getListOfClaimRefNumbers()",
						"From Date is NULL, Status is Pending or Forward or Hold");
				query = "select m.mem_bank_name bnkname, m.mem_bnk_id || m.mem_zne_id || m.mem_brn_id, c.clm_ref_no,S.SSI_UNIT_NAME,CLM_DATE "
						+ " from claim_detail_temp c, member_info m,SSI_DETAIL S"
						+ " where c.clm_date <= ? AND C.BID=S.BID "
						+ " and LTRIM(RTRIM(UPPER(c.mem_bnk_id || c.mem_zne_id || c.mem_brn_id))) = LTRIM(RTRIM(UPPER(m.mem_bnk_id || m.mem_zne_id || m.mem_brn_id))) "
						+ " and c.clm_status = ?"
						+ " group by m.mem_bank_name,"
						+ " m.mem_bnk_id || m.mem_zne_id || m.mem_brn_id,"
						+ " c.clm_ref_no,S.SSI_UNIT_NAME,CLM_DATE order by CLM_DATE,bnkname";
				prepStatement = conn.prepareStatement(query);
				prepStatement.setDate(1, toDate);
				prepStatement.setString(2, clmApplicationStatusFlag);
				rs = (ResultSet) prepStatement.executeQuery();
			}
			String memberBankName = null;
			String memberId = null;
			String clmRefNumber = null;
			String cgclan = "";
			ClaimDetail clmDtl = null;
			String unitName = "";
			java.util.Date submittedDt = null;
			java.util.Date clmLodgmentdDt = null;
			java.util.Date claimForwardedDt = null;

			String claimRetRemarks = "";
			java.util.Date claimReturnDate = null;

			Double claimForwdAmt = null;

			java.util.Date claimRejectedDt = null;

			java.util.Date tempRejOrRejDt = null;

			java.util.Date claimReplyRecvdDate = null;

			double claimAppliedAmt = 0.0;

			double claimRejAmount = 0.0;

			String clmQryRefNumber = "";

			java.util.Date clamQryDate = null;

			String clmDeclRecvdFlag = null;
			java.util.Date clmDeclRecvdDt = null;

			java.util.Date lastActionTakenDt = null;

			String claimStatus = "";

			String replyInwardId = "";

			java.util.Date replyInwardDt = null;

			java.util.Date tempClosedDate = null;

			java.util.Date tempRejectedDate = null;

			double claimApprovedAmt = 0.0;

			double totalClmApprovedAmt = 0.0;

			double totGrandClmApprovedAmtTemp = 0.0;

			double totGrandClmApprovedAmt = 0.0;

			String zoneName = null;
			String branchName = null;
			double guarApprdAmount = 0.0;

			double totalGuarnteAmt = 0.0;

			double totgrandTotal = 0.0;

			double totgrandTotalTemp = 0.0;

			double claimDecRecvdAmt = 0.0;
			String claimDecRecvdflag = null;
			java.util.Date claimDecRecvdDt = null;
			java.util.Date claimWithDrawnDt = null;
			String claimRemarks = null;
			String cgpan = null;
			java.util.Date clmApprovedDt = null;

			if (clmApplicationStatusFlag
					.equals(ClaimConstants.CLM_APPROVAL_STATUS)) {
				while (rs.next()) {

					memberId = (String) rs.getString(1);
					memberBankName = (String) rs.getString(2);
					zoneName = (String) rs.getString(3);
					branchName = (String) rs.getString(4);
					cgpan = (String) rs.getString(5);
					unitName = (String) rs.getString(6);
					guarApprdAmount = (Double) rs.getDouble(7);
					clmRefNumber = (String) rs.getString(8);
					cgclan = (String) rs.getString(9);
					clmLodgmentdDt = (Date) rs.getDate(10);
					claimApprovedAmt = (double) rs.getDouble(11);
					clmApprovedDt = (Date) rs.getDate(12);

					totalGuarnteAmt = totalGuarnteAmt + guarApprdAmount;
					totgrandTotalTemp = totalGuarnteAmt / 100000;
					totalClmApprovedAmt = totalClmApprovedAmt
							+ claimApprovedAmt;
					totGrandClmApprovedAmtTemp = totalClmApprovedAmt / 100000;
					DecimalFormat decFormat = new DecimalFormat("#.00");
					totGrandClmApprovedAmt = new Double(
							decFormat.format(totGrandClmApprovedAmtTemp))
							.doubleValue();
					totgrandTotal = new Double(
							decFormat.format(totgrandTotalTemp)).doubleValue();

					clmDtl = new ClaimDetail();

					clmDtl.setMliId(memberId);
					clmDtl.setMliName(memberBankName);
					clmDtl.setBranchName(branchName);
					clmDtl.setZoneName(zoneName);
					clmDtl.setCgpan(cgpan);
					clmDtl.setSsiUnitName(unitName);
					clmDtl.setApplicationApprovedAmount(guarApprdAmount);
					clmDtl.setClaimRefNum(clmRefNumber);
					clmDtl.setCGCLAN(cgclan);
					clmDtl.setClmSubmittedDt(clmLodgmentdDt);
					clmDtl.setClaimApprovedAmount(claimApprovedAmt);
					clmDtl.setClaimApprovedDt(clmApprovedDt);
					clmDtl.setTotgrandTotal(totgrandTotal);
					clmDtl.setTotGrandClmApprovedAmt(totGrandClmApprovedAmt);

					// Adding the ClaimDetail object to the vector
					clmRefNumbersList.addElement(clmDtl);
				}
				rs.close();
				rs = null;
			} else if

			(clmApplicationStatusFlag.equals(ClaimConstants.CLM_PENDING_STATUS)) {
				while (rs.next()) {

					memberId = (String) rs.getString(1);
					memberBankName = (String) rs.getString(2);
					zoneName = (String) rs.getString(3);
					branchName = (String) rs.getString(4);
					cgpan = (String) rs.getString(5);
					unitName = (String) rs.getString(6);
					guarApprdAmount = (Double) rs.getDouble(7);
					clmRefNumber = (String) rs.getString(8);
					clmLodgmentdDt = (Date) rs.getDate(9);
					claimAppliedAmt = (Double) rs.getDouble(10);
					clmDeclRecvdFlag = (String) rs.getString(11);
					clmDeclRecvdDt = (Date) rs.getDate(12);

					totalGuarnteAmt = totalGuarnteAmt + guarApprdAmount;
					totgrandTotalTemp = totalGuarnteAmt / 100000;
					totalClmApprovedAmt = totalClmApprovedAmt + claimAppliedAmt;
					totGrandClmApprovedAmtTemp = totalClmApprovedAmt / 100000;
					DecimalFormat decFormat = new DecimalFormat("#.00");
					totGrandClmApprovedAmt = new Double(
							decFormat.format(totGrandClmApprovedAmtTemp))
							.doubleValue();
					totgrandTotal = new Double(
							decFormat.format(totgrandTotalTemp)).doubleValue();

					clmDtl = new ClaimDetail();

					clmDtl.setMliId(memberId);
					clmDtl.setMliName(memberBankName);
					clmDtl.setBranchName(branchName);
					clmDtl.setZoneName(zoneName);
					clmDtl.setCgpan(cgpan);
					clmDtl.setSsiUnitName(unitName);
					clmDtl.setApplicationApprovedAmount(guarApprdAmount);
					clmDtl.setClaimRefNum(clmRefNumber);
					clmDtl.setClmSubmittedDt(clmLodgmentdDt);
					clmDtl.setClaimAppliedAmt(claimAppliedAmt);
					clmDtl.setClmDeclRecvdFlag(clmDeclRecvdFlag);
					clmDtl.setClmDeclRecvdDt(clmDeclRecvdDt);

					clmDtl.setTotgrandTotal(totgrandTotal);
					clmDtl.setTotGrandClmApprovedAmt(totGrandClmApprovedAmt);

					// Adding the ClaimDetail object to the vector
					clmRefNumbersList.addElement(clmDtl);
				}
				rs.close();
				rs = null;
			} else if

			(clmApplicationStatusFlag.equals(ClaimConstants.CLM_WITHDRAWN)) {
				while (rs.next()) {

					memberId = (String) rs.getString(1);
					memberBankName = (String) rs.getString(2);
					zoneName = (String) rs.getString(3);
					branchName = (String) rs.getString(4);
					cgpan = (String) rs.getString(5);
					unitName = (String) rs.getString(6);
					guarApprdAmount = (Double) rs.getDouble(7);
					clmRefNumber = (String) rs.getString(8);
					clmLodgmentdDt = (Date) rs.getDate(9);
					claimAppliedAmt = (Double) rs.getDouble(10);
					claimWithDrawnDt = (Date) rs.getDate(11);

					totalGuarnteAmt = totalGuarnteAmt + guarApprdAmount;
					totgrandTotalTemp = totalGuarnteAmt / 100000;
					totalClmApprovedAmt = totalClmApprovedAmt + claimAppliedAmt;
					totGrandClmApprovedAmtTemp = totalClmApprovedAmt / 100000;
					DecimalFormat decFormat = new DecimalFormat("#.00");
					totGrandClmApprovedAmt = new Double(
							decFormat.format(totGrandClmApprovedAmtTemp))
							.doubleValue();
					totgrandTotal = new Double(
							decFormat.format(totgrandTotalTemp)).doubleValue();

					clmDtl = new ClaimDetail();

					clmDtl.setMliId(memberId);
					clmDtl.setMliName(memberBankName);
					clmDtl.setBranchName(branchName);
					clmDtl.setZoneName(zoneName);
					clmDtl.setCgpan(cgpan);
					clmDtl.setSsiUnitName(unitName);
					clmDtl.setApplicationApprovedAmount(guarApprdAmount);
					clmDtl.setClaimRefNum(clmRefNumber);
					clmDtl.setClmSubmittedDt(clmLodgmentdDt);
					clmDtl.setClaimAppliedAmt(claimAppliedAmt);
					clmDtl.setClaimWithDrawnDate(claimWithDrawnDt);

					clmDtl.setTotgrandTotal(totgrandTotal);
					clmDtl.setTotGrandClmApprovedAmt(totGrandClmApprovedAmt);

					// clmDtl.setClaimLodgmentDate(clmLodgmentdDt);

					// Adding the ClaimDetail object to the vector
					clmRefNumbersList.addElement(clmDtl);

				}
				rs.close();
				rs = null;
			} else if

			(clmApplicationStatusFlag
					.equals(ClaimConstants.CLM_TEMPORARY_CLOSE)) {
				while (rs.next()) {

					memberId = (String) rs.getString(1);
					memberBankName = (String) rs.getString(2);
					zoneName = (String) rs.getString(3);
					branchName = (String) rs.getString(4);
					cgpan = (String) rs.getString(5);
					unitName = (String) rs.getString(6);
					guarApprdAmount = (Double) rs.getDouble(7);
					clmRefNumber = (String) rs.getString(8);
					clmLodgmentdDt = (Date) rs.getDate(9);
					claimAppliedAmt = (Double) rs.getDouble(10);
					tempClosedDate = (Date) rs.getDate(11);

					clmQryRefNumber = (String) rs.getString(12);

					clamQryDate = (Date) rs.getDate(13);

					totalGuarnteAmt = totalGuarnteAmt + guarApprdAmount;
					totgrandTotalTemp = totalGuarnteAmt / 100000;
					totalClmApprovedAmt = totalClmApprovedAmt + claimAppliedAmt;
					totGrandClmApprovedAmtTemp = totalClmApprovedAmt / 100000;
					DecimalFormat decFormat = new DecimalFormat("#.00");
					totGrandClmApprovedAmt = new Double(
							decFormat.format(totGrandClmApprovedAmtTemp))
							.doubleValue();
					totgrandTotal = new Double(
							decFormat.format(totgrandTotalTemp)).doubleValue();

					clmDtl = new ClaimDetail();

					clmDtl.setMliId(memberId);
					clmDtl.setMliName(memberBankName);
					clmDtl.setBranchName(branchName);
					clmDtl.setZoneName(zoneName);
					clmDtl.setCgpan(cgpan);
					clmDtl.setSsiUnitName(unitName);
					clmDtl.setApplicationApprovedAmount(guarApprdAmount);
					clmDtl.setClaimRefNum(clmRefNumber);
					clmDtl.setClmSubmittedDt(clmLodgmentdDt);
					clmDtl.setClaimAppliedAmt(claimAppliedAmt);
					clmDtl.setTempClosedDate(tempClosedDate);

					clmDtl.setClmQryRefNumber(clmQryRefNumber);
					clmDtl.setClamQryDate(clamQryDate);
					clmDtl.setTotgrandTotal(totgrandTotal);
					clmDtl.setTotGrandClmApprovedAmt(totGrandClmApprovedAmt);

					// Adding the ClaimDetail object to the vector
					clmRefNumbersList.addElement(clmDtl);

				}
				rs.close();
				rs = null;
			}

			else if

			(clmApplicationStatusFlag
					.equals(ClaimConstants.CLM_TEMPORARY_REJECT)) {
				while (rs.next()) {

					memberId = (String) rs.getString(1);
					memberBankName = (String) rs.getString(2);
					zoneName = (String) rs.getString(3);
					branchName = (String) rs.getString(4);
					cgpan = (String) rs.getString(5);
					unitName = (String) rs.getString(6);
					guarApprdAmount = (Double) rs.getDouble(7);
					clmRefNumber = (String) rs.getString(8);
					clmLodgmentdDt = (Date) rs.getDate(9);
					claimRejAmount = (Double) rs.getDouble(10);
					tempRejectedDate = (Date) rs.getDate(11);
					claimRetRemarks = rs.getString(12);

					totalGuarnteAmt = totalGuarnteAmt + guarApprdAmount;
					totgrandTotalTemp = totalGuarnteAmt / 100000;
					totalClmApprovedAmt = totalClmApprovedAmt + claimRejAmount;
					totGrandClmApprovedAmtTemp = totalClmApprovedAmt / 100000;
					DecimalFormat decFormat = new DecimalFormat("#.00");
					totGrandClmApprovedAmt = new Double(
							decFormat.format(totGrandClmApprovedAmtTemp))
							.doubleValue();
					totgrandTotal = new Double(
							decFormat.format(totgrandTotalTemp)).doubleValue();

					clmDtl = new ClaimDetail();

					clmDtl.setMliId(memberId);
					clmDtl.setMliName(memberBankName);
					clmDtl.setBranchName(branchName);
					clmDtl.setZoneName(zoneName);
					clmDtl.setCgpan(cgpan);
					clmDtl.setSsiUnitName(unitName);
					clmDtl.setApplicationApprovedAmount(guarApprdAmount);
					clmDtl.setClaimRefNum(clmRefNumber);
					clmDtl.setClmSubmittedDt(clmLodgmentdDt);
					clmDtl.setClaimAppliedAmt(claimRejAmount);
					clmDtl.setTempRejectedDate(tempRejectedDate);

					clmDtl.setClaimRetRemarks(claimRetRemarks);
					clmDtl.setTotgrandTotal(totgrandTotal);
					clmDtl.setTotGrandClmApprovedAmt(totGrandClmApprovedAmt);

					// clmDtl.setClaimLodgmentDate(clmLodgmentdDt);

					// Adding the ClaimDetail object to the vector
					clmRefNumbersList.addElement(clmDtl);

				}
				rs.close();
				rs = null;
			}

			else if

			(clmApplicationStatusFlag.equals(ClaimConstants.CLM_REPLY_RECEIVED)) {
				while (rs.next()) {

					memberId = (String) rs.getString(1);
					memberBankName = (String) rs.getString(2);
					zoneName = (String) rs.getString(3);
					branchName = (String) rs.getString(4);
					cgpan = (String) rs.getString(5);
					unitName = (String) rs.getString(6);
					guarApprdAmount = (Double) rs.getDouble(7);
					clmRefNumber = (String) rs.getString(8);
					clmLodgmentdDt = (Date) rs.getDate(9);
					claimApprovedAmt = (Double) rs.getDouble(10);
					claimReplyRecvdDate = (Date) rs.getDate(11);
					clmQryRefNumber = (String) rs.getString(12);
					clamQryDate = (Date) rs.getDate(13);
					replyInwardId = (String) rs.getString(14);
					replyInwardDt = (Date) rs.getDate(15);

					totalGuarnteAmt = totalGuarnteAmt + guarApprdAmount;
					totgrandTotalTemp = totalGuarnteAmt / 100000;
					totalClmApprovedAmt = totalClmApprovedAmt
							+ claimApprovedAmt;
					totGrandClmApprovedAmtTemp = totalClmApprovedAmt / 100000;
					DecimalFormat decFormat = new DecimalFormat("#.00");
					totGrandClmApprovedAmt = new Double(
							decFormat.format(totGrandClmApprovedAmtTemp))
							.doubleValue();
					totgrandTotal = new Double(
							decFormat.format(totgrandTotalTemp)).doubleValue();

					clmDtl = new ClaimDetail();

					clmDtl.setMliId(memberId);
					clmDtl.setMliName(memberBankName);
					clmDtl.setBranchName(branchName);
					clmDtl.setZoneName(zoneName);
					clmDtl.setCgpan(cgpan);
					clmDtl.setSsiUnitName(unitName);
					clmDtl.setApplicationApprovedAmount(guarApprdAmount);
					clmDtl.setClaimRefNum(clmRefNumber);
					clmDtl.setClmSubmittedDt(clmLodgmentdDt);
					clmDtl.setClaimApprovedAmount(claimApprovedAmt);
					clmDtl.setClaimReplyRecvdDate(claimReplyRecvdDate);
					clmDtl.setClmQryRefNumber(clmQryRefNumber);
					clmDtl.setClamQryDate(clamQryDate);

					clmDtl.setReplyInwardId(replyInwardId);
					clmDtl.setReplyInwardDt(replyInwardDt);
					clmDtl.setTotgrandTotal(totgrandTotal);
					clmDtl.setTotGrandClmApprovedAmt(totGrandClmApprovedAmt);

					// clmDtl.setClaimLodgmentDate(clmLodgmentdDt);

					// Adding the ClaimDetail object to the vector
					clmRefNumbersList.addElement(clmDtl);

				}
				rs.close();
				rs = null;
			}

			else if

			(clmApplicationStatusFlag.equals(ClaimConstants.CLM_REJECT_STATUS)) {
				while (rs.next()) {

					memberId = (String) rs.getString(1);
					memberBankName = (String) rs.getString(2);
					zoneName = (String) rs.getString(3);
					branchName = (String) rs.getString(4);
					cgpan = (String) rs.getString(5);
					unitName = (String) rs.getString(6);
					guarApprdAmount = (Double) rs.getDouble(7);
					clmRefNumber = (String) rs.getString(8);
					clmLodgmentdDt = (Date) rs.getDate(9);
					claimAppliedAmt = (Double) rs.getDouble(10);
					claimRejectedDt = (Date) rs.getDate(11);
					claimRetRemarks = rs.getString(12);

					totalGuarnteAmt = totalGuarnteAmt + guarApprdAmount;
					totgrandTotalTemp = totalGuarnteAmt / 100000;
					totalClmApprovedAmt = totalClmApprovedAmt + claimAppliedAmt;
					totGrandClmApprovedAmtTemp = totalClmApprovedAmt / 100000;
					DecimalFormat decFormat = new DecimalFormat("#.00");
					totGrandClmApprovedAmt = new Double(
							decFormat.format(totGrandClmApprovedAmtTemp))
							.doubleValue();
					totgrandTotal = new Double(
							decFormat.format(totgrandTotalTemp)).doubleValue();

					clmDtl = new ClaimDetail();

					clmDtl.setMliId(memberId);
					clmDtl.setMliName(memberBankName);
					clmDtl.setBranchName(branchName);
					clmDtl.setZoneName(zoneName);
					clmDtl.setCgpan(cgpan);
					clmDtl.setSsiUnitName(unitName);
					clmDtl.setApplicationApprovedAmount(guarApprdAmount);
					clmDtl.setClaimRefNum(clmRefNumber);
					clmDtl.setClmSubmittedDt(clmLodgmentdDt);
					clmDtl.setClaimAppliedAmt(claimAppliedAmt);
					clmDtl.setClaimRejectedDt(claimRejectedDt);
					clmDtl.setClaimRetRemarks(claimRetRemarks);

					clmDtl.setTotgrandTotal(totgrandTotal);
					clmDtl.setTotGrandClmApprovedAmt(totGrandClmApprovedAmt);

					// Adding the ClaimDetail object to the vector
					clmRefNumbersList.addElement(clmDtl);

				}
				rs.close();
				rs = null;
			}

			else if

			(clmApplicationStatusFlag.equals("RT")) {
				while (rs.next()) {

					memberId = (String) rs.getString(1);
					memberBankName = (String) rs.getString(2);
					zoneName = (String) rs.getString(3);
					branchName = (String) rs.getString(4);
					cgpan = (String) rs.getString(5);
					unitName = (String) rs.getString(6);
					guarApprdAmount = (Double) rs.getDouble(7);
					clmRefNumber = (String) rs.getString(8);
					claimAppliedAmt = (Double) rs.getDouble(9);
					claimReturnDate = (Date) rs.getDate(10);
					claimRetRemarks = (String) rs.getString(11);

					totalGuarnteAmt = totalGuarnteAmt + guarApprdAmount;
					totgrandTotalTemp = totalGuarnteAmt / 100000;
					totalClmApprovedAmt = totalClmApprovedAmt + claimAppliedAmt;
					totGrandClmApprovedAmtTemp = totalClmApprovedAmt / 100000;
					DecimalFormat decFormat = new DecimalFormat("#.00");
					totGrandClmApprovedAmt = new Double(
							decFormat.format(totGrandClmApprovedAmtTemp))
							.doubleValue();
					totgrandTotal = new Double(
							decFormat.format(totgrandTotalTemp)).doubleValue();

					clmDtl = new ClaimDetail();

					clmDtl.setMliId(memberId);
					clmDtl.setMliName(memberBankName);
					clmDtl.setBranchName(branchName);
					clmDtl.setZoneName(zoneName);
					clmDtl.setCgpan(cgpan);
					clmDtl.setSsiUnitName(unitName);
					clmDtl.setApplicationApprovedAmount(guarApprdAmount);
					clmDtl.setClaimRefNum(clmRefNumber);
					clmDtl.setClaimAppliedAmt(claimAppliedAmt);
					clmDtl.setClaimReturnDate(claimReturnDate);
					clmDtl.setClaimRetRemarks(claimRetRemarks);
					clmDtl.setTotgrandTotal(totgrandTotal);
					clmDtl.setTotGrandClmApprovedAmt(totGrandClmApprovedAmt);

					clmRefNumbersList.addElement(clmDtl);
				}
				rs.close();
				rs = null;
			}

			else if

			(clmApplicationStatusFlag.equals("RTD")) {
				while (rs.next()) {

					memberId = (String) rs.getString(1);
					memberBankName = (String) rs.getString(2);
					zoneName = (String) rs.getString(3);
					branchName = (String) rs.getString(4);
					cgpan = (String) rs.getString(5);
					unitName = (String) rs.getString(6);
					guarApprdAmount = (Double) rs.getDouble(7);
					clmRefNumber = (String) rs.getString(8);
					clmLodgmentdDt = (Date) rs.getDate(9);
					claimAppliedAmt = (Double) rs.getDouble(10);
					tempRejOrRejDt = (Date) rs.getDate(11);

					totalGuarnteAmt = totalGuarnteAmt + guarApprdAmount;
					totgrandTotalTemp = totalGuarnteAmt / 100000;
					totalClmApprovedAmt = totalClmApprovedAmt + claimAppliedAmt;
					totGrandClmApprovedAmtTemp = totalClmApprovedAmt / 100000;
					DecimalFormat decFormat = new DecimalFormat("#.00");
					totGrandClmApprovedAmt = new Double(
							decFormat.format(totGrandClmApprovedAmtTemp))
							.doubleValue();
					totgrandTotal = new Double(
							decFormat.format(totgrandTotalTemp)).doubleValue();

					clmDtl = new ClaimDetail();

					clmDtl.setMliId(memberId);
					clmDtl.setMliName(memberBankName);
					clmDtl.setBranchName(branchName);
					clmDtl.setZoneName(zoneName);
					clmDtl.setCgpan(cgpan);
					clmDtl.setSsiUnitName(unitName);
					clmDtl.setApplicationApprovedAmount(guarApprdAmount);
					clmDtl.setClaimRefNum(clmRefNumber);
					clmDtl.setClmSubmittedDt(clmLodgmentdDt);
					clmDtl.setClaimAppliedAmt(claimAppliedAmt);

					clmDtl.setTempRejOrRejDt(tempRejOrRejDt);
					clmDtl.setTotgrandTotal(totgrandTotal);
					clmDtl.setTotGrandClmApprovedAmt(totGrandClmApprovedAmt);

					clmRefNumbersList.addElement(clmDtl);
				}
				rs.close();
				rs = null;
			}

			else if

			(clmApplicationStatusFlag.equals("AS")) {
				while (rs.next()) {

					memberId = (String) rs.getString(1);
					memberBankName = (String) rs.getString(2);
					zoneName = (String) rs.getString(3);
					branchName = (String) rs.getString(4);
					cgpan = (String) rs.getString(5);
					unitName = (String) rs.getString(6);
					guarApprdAmount = (Double) rs.getDouble(7);
					clmRefNumber = (String) rs.getString(8);
					claimAppliedAmt = (Double) rs.getDouble(9);
					clmLodgmentdDt = (Date) rs.getDate(10);
					claimStatus = (String) rs.getString(11);

					clmDeclRecvdDt = (Date) rs.getDate(12);

					lastActionTakenDt = (Date) rs.getDate(13);

					totalGuarnteAmt = totalGuarnteAmt + guarApprdAmount;
					totgrandTotalTemp = totalGuarnteAmt / 100000;
					totalClmApprovedAmt = totalClmApprovedAmt + claimAppliedAmt;
					totGrandClmApprovedAmtTemp = totalClmApprovedAmt / 100000;
					DecimalFormat decFormat = new DecimalFormat("#.00");
					totGrandClmApprovedAmt = new Double(
							decFormat.format(totGrandClmApprovedAmtTemp))
							.doubleValue();
					totgrandTotal = new Double(
							decFormat.format(totgrandTotalTemp)).doubleValue();

					clmDtl = new ClaimDetail();

					clmDtl.setMliId(memberId);
					clmDtl.setMliName(memberBankName);
					clmDtl.setBranchName(branchName);
					clmDtl.setZoneName(zoneName);
					clmDtl.setCgpan(cgpan);
					clmDtl.setSsiUnitName(unitName);
					clmDtl.setApplicationApprovedAmount(guarApprdAmount);
					clmDtl.setClaimRefNum(clmRefNumber);
					clmDtl.setClaimAppliedAmt(claimAppliedAmt);
					clmDtl.setClmSubmittedDt(clmLodgmentdDt);
					clmDtl.setClmStatus(claimStatus);
					clmDtl.setClmDeclRecvdDt(clmDeclRecvdDt);

					clmDtl.setLastActionTakenDt(lastActionTakenDt);

					clmDtl.setTotgrandTotal(totgrandTotal);
					clmDtl.setTotGrandClmApprovedAmt(totGrandClmApprovedAmt);

					clmRefNumbersList.addElement(clmDtl);
				}
				rs.close();
				rs = null;
			}

			else if

			(clmApplicationStatusFlag.equals(ClaimConstants.CLM_FORWARD_STATUS)) {
				while (rs.next()) {

					memberId = (String) rs.getString(1);
					memberBankName = (String) rs.getString(2);
					zoneName = (String) rs.getString(3);
					branchName = (String) rs.getString(4);
					cgpan = (String) rs.getString(5);
					unitName = (String) rs.getString(6);
					guarApprdAmount = (Double) rs.getDouble(7);
					clmRefNumber = (String) rs.getString(8);
					clmLodgmentdDt = (Date) rs.getDate(9);
					claimForwdAmt = (double) rs.getDouble(10);
					claimForwardedDt = (Date) rs.getDate(11);

					totalGuarnteAmt = totalGuarnteAmt + guarApprdAmount;
					totgrandTotalTemp = totalGuarnteAmt / 100000;
					totalClmApprovedAmt = totalClmApprovedAmt + claimForwdAmt;
					totGrandClmApprovedAmtTemp = totalClmApprovedAmt / 100000;
					DecimalFormat decFormat = new DecimalFormat("#.00");
					totGrandClmApprovedAmt = new Double(
							decFormat.format(totGrandClmApprovedAmtTemp))
							.doubleValue();
					totgrandTotal = new Double(
							decFormat.format(totgrandTotalTemp)).doubleValue();

					clmDtl = new ClaimDetail();

					clmDtl.setMliId(memberId);
					clmDtl.setMliName(memberBankName);
					clmDtl.setBranchName(branchName);
					clmDtl.setZoneName(zoneName);
					clmDtl.setCgpan(cgpan);
					clmDtl.setSsiUnitName(unitName);
					clmDtl.setApplicationApprovedAmount(guarApprdAmount);
					clmDtl.setClaimRefNum(clmRefNumber);
					clmDtl.setClmSubmittedDt(clmLodgmentdDt);
					clmDtl.setClaimForwdAmt(claimForwdAmt);
					clmDtl.setClaimForwardedDt(claimForwardedDt);
					clmDtl.setTotgrandTotal(totgrandTotal);
					clmDtl.setTotGrandClmApprovedAmt(totGrandClmApprovedAmt);

					// Adding the ClaimDetail object to the vector
					clmRefNumbersList.addElement(clmDtl);
				}
				rs.close();
				rs = null;
			}

			else {
				while (rs.next()) {
					memberBankName = (String) rs.getString(1);
					memberId = (String) rs.getString(2);
					clmRefNumber = (String) rs.getString(3);
					unitName = (String) rs.getString(4);
					submittedDt = (Date) rs.getDate(5);

					zoneName = (String) rs.getString(6);
					branchName = (String) rs.getString(7);
					guarApprdAmount = (Double) rs.getDouble(8);

					clmDtl = new ClaimDetail();
					clmDtl.setMliName(memberBankName);
					clmDtl.setMliId(memberId);
					clmDtl.setClaimRefNum(clmRefNumber);
					clmDtl.setCGCLAN(cgclan);
					clmDtl.setSsiUnitName(unitName);
					clmDtl.setClmSubmittedDt(submittedDt);

					clmDtl.setZoneName(zoneName);
					clmDtl.setBranchName(branchName);
					clmDtl.setApplicationApprovedAmount(guarApprdAmount);

					// Adding the ClaimDetail object to the vector
					clmRefNumbersList.addElement(clmDtl);
				}
				rs.close();
				rs = null;
			}
			prepStatement.close();
			prepStatement = null;
		} catch (SQLException sqlexception) {
			// sqlexception.printStackTrace();
			throw new DatabaseException(sqlexception.getMessage());
		} finally {
			DBConnection.freeConnection(conn);
		}
		return clmRefNumbersList;
	}

	public Vector getListOfClaimRefNumbersNew4(java.sql.Date fromDate,
			java.sql.Date toDate, String clmApplicationStatusFlag,
			String bankId, String zoneId, String branchId)
			throws DatabaseException {

		Log.log(Log.INFO, "ReportDAO", "getListOfClaimRefNumbersNew()",
				"Entered");
		Log.log(Log.INFO, "ReportDAO", "getListOfClaimRefNumbersNew()",
				"From Date :" + fromDate);
		Log.log(Log.INFO, "ReportDAO", "getListOfClaimRefNumbersNew()",
				"To Date :" + toDate);
		Log.log(Log.INFO, "ReportDAO", "getListOfClaimRefNumbersNew()",
				"clmApplicationStatusFlag :" + clmApplicationStatusFlag);
		Connection conn = null;
		PreparedStatement prepStatement = null;
		ResultSet rs = null;
		Vector clmRefNumbersList = new Vector();
		String query = null;

		try {
			conn = DBConnection.getConnection();

			if ((fromDate != null)
					&& (!(clmApplicationStatusFlag
							.equals(ClaimConstants.CLM_PENDING_STATUS) || clmApplicationStatusFlag
							.equals(ClaimConstants.CLM_HOLD_STATUS)))) {
				if (clmApplicationStatusFlag
						.equals(ClaimConstants.CLM_APPROVAL_STATUS)) {
					Log.log(Log.INFO, "ReportDAO",
							"getListOfClaimRefNumbers()",
							"From Date is NULL, Status is Approval");

					// new mod query kot1
					query = "SELECT m.mem_bnk_id || m.mem_zne_id || m.mem_brn_id memberid, m.mem_bank_name bankname, m.mem_zone_name,\n"
							+ "         DECODE (m.MEM_BRANCH_NAME,\n"
							+ "                 NULL, a.APP_MLI_BRANCH_NAME,\n"
							+ "                 m.MEM_BRANCH_NAME)\n"
							+ "            BRANCH,a.cgpan,S.SSI_UNIT_NAME, DECODE (a.app_reapprove_amount,\n"
							+ "                 NULL, a.APP_approved_amount,\n"
							+ "                 a.app_reapprove_amount)\n"
							+ "            GuarntAmt, c.clm_ref_no, c.cgclan,  c.CLM_DATE claimFilDt,ctd_tc_first_inst_pay_amt clmApprvdAmt,TRUNC (C.CLM_APPROVED_DT) clmApprovdDt\n"
							+ "                   FROM claim_detail c,\n"
							+ "         member_info m,\n"
							+ "         SSI_DETAIL S,\n"
							+ "         application_detail a,\n"
							+ "         claim_tc_detail ct\n"
							+ "   WHERE     TRUNC (c.clm_approved_dt) BETWEEN ? AND ?  \n"
							+ "         AND C.BID = S.BID\n"
							+ "         AND s.ssi_reference_number = a.ssi_reference_number\n"
							+ "         AND c.mem_bnk_id || c.mem_brn_id || c.mem_zne_id =\n"
							+ "                a.mem_bnk_id || a.mem_brn_id || a.mem_zne_id\n"
							+ "         AND LTRIM (\n"
							+ "                RTRIM (UPPER (c.mem_bnk_id || c.mem_zne_id || c.mem_brn_id))) =\n"
							+ "                LTRIM (\n"
							+ "                   RTRIM (UPPER (m.mem_bnk_id || m.mem_zne_id || m.mem_brn_id)))\n"
							+ "         AND c.clm_status = ? \n"
							+ "    and c.mem_bnk_id = ? and c.mem_zne_id = ? and c.mem_brn_id = ? \n"
							+ "         and ct.clm_ref_no = c.clm_ref_no\n"
							+ "         and ct.cgpan = a.cgpan\n"
							+ "         and A.APP_LOAN_TYPE in ('TC')   \n"
							+ "         union all\n"
							+ "          SELECT m.mem_bnk_id || m.mem_zne_id || m.mem_brn_id memberid, m.mem_bank_name bankname, m.mem_zone_name,\n"
							+ "         DECODE (m.MEM_BRANCH_NAME,\n"
							+ "                 NULL, a.APP_MLI_BRANCH_NAME,\n"
							+ "                 m.MEM_BRANCH_NAME)\n"
							+ "            BRANCH,a.cgpan,S.SSI_UNIT_NAME, DECODE (a.app_reapprove_amount,\n"
							+ "                 NULL, a.APP_approved_amount,\n"
							+ "                 a.app_reapprove_amount)\n"
							+ "            GuarntAmt,c.clm_ref_no, c.cgclan,  c.CLM_DATE claimFilDt,cwd_wc_first_inst_pay_amt clmApprvdAmt,TRUNC (C.CLM_APPROVED_DT) clmApprovdDt\n"
							+ "    FROM claim_detail c,\n"
							+ "         member_info m,\n"
							+ "         SSI_DETAIL S,\n"
							+ "         application_detail a,\n"
							+ "         claim_wc_detail cw\n"
							+ "   WHERE     TRUNC (c.clm_approved_dt) BETWEEN ? AND ? \n"
							+ "         AND C.BID = S.BID\n"
							+ "         AND s.ssi_reference_number = a.ssi_reference_number\n"
							+ "         AND c.mem_bnk_id || c.mem_brn_id || c.mem_zne_id =\n"
							+ "                a.mem_bnk_id || a.mem_brn_id || a.mem_zne_id\n"
							+ "         AND LTRIM (\n"
							+ "                RTRIM (UPPER (c.mem_bnk_id || c.mem_zne_id || c.mem_brn_id))) =\n"
							+ "                LTRIM (\n"
							+ "                   RTRIM (UPPER (m.mem_bnk_id || m.mem_zne_id || m.mem_brn_id)))\n"
							+ "         AND c.clm_status = ? \n"
							+ "    and c.mem_bnk_id = ? and c.mem_zne_id = ? and c.mem_brn_id = ? \n"
							+ "         and cw.clm_ref_no = c.clm_ref_no\n"
							+ "         and cw.cgpan = a.cgpan\n"
							+ "         and app_loan_type in ('WC')  \n"
							+ "union all\n"
							+ " SELECT m.mem_bnk_id || m.mem_zne_id || m.mem_brn_id memberid, m.mem_bank_name bankname, m.mem_zone_name,\n"
							+ "         DECODE (m.MEM_BRANCH_NAME,\n"
							+ "                 NULL, a.APP_MLI_BRANCH_NAME,\n"
							+ "                 m.MEM_BRANCH_NAME)\n"
							+ "            BRANCH,a.cgpan,S.SSI_UNIT_NAME, DECODE (a.app_reapprove_amount,\n"
							+ "                 NULL, a.APP_approved_amount,\n"
							+ "                 a.app_reapprove_amount)\n"
							+ "            GuarntAmt,c.clm_ref_no, c.cgclan,  c.CLM_DATE claimFilDt,  nvl(cwd_wc_first_inst_pay_amt,0)+nvl(ctd_tc_first_inst_pay_amt,0) clmApprvdAmt,TRUNC (C.CLM_APPROVED_DT) clmApprovdDt\n"
							+ "    FROM claim_detail c,\n"
							+ "         member_info m,\n"
							+ "         SSI_DETAIL S,\n"
							+ "         application_detail a,\n"
							+ "         claim_wc_detail cw,\n"
							+ "         claim_tc_detail ct\n"
							+ "   WHERE     TRUNC (c.clm_approved_dt) BETWEEN ? AND ?\n"
							+ "         AND C.BID = S.BID\n"
							+ "         AND s.ssi_reference_number = a.ssi_reference_number\n"
							+ "         AND c.mem_bnk_id || c.mem_brn_id || c.mem_zne_id =\n"
							+ "                a.mem_bnk_id || a.mem_brn_id || a.mem_zne_id\n"
							+ "         AND LTRIM (\n"
							+ "                RTRIM (UPPER (c.mem_bnk_id || c.mem_zne_id || c.mem_brn_id))) =\n"
							+ "                LTRIM (\n"
							+ "                   RTRIM (UPPER (m.mem_bnk_id || m.mem_zne_id || m.mem_brn_id)))\n"
							+ "         AND c.clm_status = ? \n"
							+ "    and c.mem_bnk_id = ? and c.mem_zne_id = ? and c.mem_brn_id = ? \n"
							+ "         and cw.clm_ref_no = c.clm_ref_no\n"
							+ "         and cw.cgpan = a.cgpan\n"
							+ "         and ct.clm_ref_no = c.clm_ref_no\n"
							+ "         and ct.cgpan = a.cgpan\n"
							+ "         and app_loan_type in ('CC')   order by clmApprovdDt,bankname ";

					prepStatement = conn.prepareStatement(query);
					prepStatement.setDate(1, fromDate);
					prepStatement.setDate(2, toDate);
					prepStatement.setString(3, clmApplicationStatusFlag);

					prepStatement.setString(4, bankId);
					prepStatement.setString(5, zoneId);
					prepStatement.setString(6, branchId);

					prepStatement.setDate(7, fromDate);
					prepStatement.setDate(8, toDate);
					prepStatement.setString(9, clmApplicationStatusFlag);

					prepStatement.setString(10, bankId);
					prepStatement.setString(11, zoneId);
					prepStatement.setString(12, branchId);

					prepStatement.setDate(13, fromDate);
					prepStatement.setDate(14, toDate);
					prepStatement.setString(15, clmApplicationStatusFlag);
					prepStatement.setString(16, bankId);
					prepStatement.setString(17, zoneId);
					prepStatement.setString(18, branchId);
					rs = (ResultSet) prepStatement.executeQuery();
				}

				// kotttttt

				if (clmApplicationStatusFlag
						.equals(ClaimConstants.CLM_TEMPORARY_CLOSE)) {
					// kot5
					Log.log(Log.INFO, "ReportDAO",
							"getListOfClaimRefNumbers()",
							"From Date is NULL, Status is Reject");

					query = "SELECT a.mem_bnk_id || a.mem_zne_id || a.mem_brn_id,  \n"
							+ "                                d.mem_bank_name,  \n"
							+ "                                d.mem_zone_name,  \n"
							+ "                                DECODE (d.MEM_BRANCH_NAME,  \n"
							+ "                                        NULL, e.APP_MLI_BRANCH_NAME,  \n"
							+ "                                        d.MEM_BRANCH_NAME)  \n"
							+ "                                   branch,  \n"
							+ "                                e.cgpan,  \n"
							+ "                                c.ssi_unit_name,  \n"
							+ "                                DECODE (e.app_reapprove_amount,  \n"
							+ "                                        NULL, e.APP_approved_amount,  \n"
							+ "                                        e.app_reapprove_amount)  \n"
							+ "                                   guarantamt,  \n"
							+ "                                A.clm_ref_no,  \n"
							+ "                                a.clm_date,  \n"
							+ "                                b.caa_applied_amount clmAppdAmt,TRUNC(a.CLM_CREATED_MODIFIED_DT), \n"
							+ "                                (select distinct max(trim(upper(replace(clm_ltr_ref_no,' ',''))))\n"
							+ "                                from claim_query_detail cqd\n"
							+ "                                where a.clm_ref_no = cqd.clm_ref_no\n"
							+ "                                and clm_ltr_dt in \n"
							+ "                                (select max(clm_ltr_dt) from claim_query_detail cd where cd.clm_ref_no = cqd.clm_ref_no)\n"
							+ "                                ) clmqryrefno,\n"
							+ "                                (select distinct clm_ltr_dt\n"
							+ "                                from claim_query_detail cq\n"
							+ "                                where a.clm_ref_no = cq.clm_ref_no\n"
							+ "                                and clm_ltr_dt in \n"
							+ "                                (select max(clm_ltr_dt) from claim_query_detail cd where cd.clm_ref_no = cq.clm_ref_no)\n"
							+ "                                ) clmqrydt  \n"
							+ "                           FROM claim_detail_temp a,  \n"
							+ "                                claim_application_amount_temp b,  \n"
							+ "                                ssi_detail c,  \n"
							+ "                                member_info d,  \n"
							+ "                                application_detail e  \n"
							+ "                          WHERE     b.cgpan = e.cgpan  \n"
							+ "                                AND a.clm_ref_no = b.clm_ref_no  \n"
							+ "                                AND a.bid = c.bid  \n"
							+ "                                AND a.mem_bnk_id || a.mem_zne_id || a.mem_brn_id =  \n"
							+ "                                       d.mem_bnk_id || d.mem_zne_id || d.mem_brn_id  \n"
							+ "                               AND TRUNC (CLM_CREATED_MODIFIED_DT) BETWEEN ?  AND ?   \n"
							+ "                                AND a.clm_status = ? \n"
							+ "    and a.mem_bnk_id = ? and a.mem_zne_id = ? and a.mem_brn_id = ? \n"
							+ " order by TRUNC(CLM_CREATED_MODIFIED_DT),d.mem_bank_name ";

					prepStatement = conn.prepareStatement(query);
					prepStatement.setDate(1, fromDate);
					prepStatement.setDate(2, toDate);
					prepStatement.setString(3, clmApplicationStatusFlag);
					prepStatement.setString(4, bankId);
					prepStatement.setString(5, zoneId);
					prepStatement.setString(6, branchId);

					rs = (ResultSet) prepStatement.executeQuery();
				}

				if (clmApplicationStatusFlag.equals("RT")) {
					// kot9
					query = " SELECT  m.mem_bnk_id || m.mem_zne_id || m.mem_brn_id memberid, m.mem_bank_name bnkname,mem_zone_name zone,\n"
							+ "         decode(mem_branch_name,null,app_mli_branch_name,mem_branch_name) branch,\n"
							+ "                 a.cgpan, S.SSI_UNIT_NAME,\n"
							+ "         decode(nvl(app_reapprove_amount,0),0,app_approved_amount,app_Reapprove_amount) appamt,\n"
							+ "         c.clm_ref_no,\n"
							+ "                 caa_applied_amount clmedamt,\n"
							+ "         TRUNC (CLM_CREATED_MODIFIED_DT) rtdate,\n"
							+ "         clm_return_remarks\n"
							+ "    FROM claim_detail_temp c,\n"
							+ "         member_info m,\n"
							+ "         SSI_DETAIL S,\n"
							+ "         claim_application_amount_temp caa,\n"
							+ "         application_detail a\n"
							+ "   WHERE     TRUNC(C.CLM_CREATED_MODIFIED_DT) between ?  and ?  AND\n"
							+ "             C.BID = S.BID\n"
							+ "         AND c.clm_ref_no = caa.clm_ref_no\n"
							+ "         AND caa.cgpan = a.cgpan\n"
							+ "         AND LTRIM (\n"
							+ "                RTRIM (UPPER (c.mem_bnk_id || c.mem_zne_id || c.mem_brn_id))) =\n"
							+ "                LTRIM (\n"
							+ "                   RTRIM (UPPER (m.mem_bnk_id || m.mem_zne_id || m.mem_brn_id)))\n"
							+ "         AND c.clm_status = ?  \n"
							+ "   and c.mem_bnk_id = ? and c.mem_zne_id = ? and c.mem_brn_id = ? \n"
							+

							"ORDER BY TRUNC (CLM_CREATED_MODIFIED_DT), bnkname";

					prepStatement = conn.prepareStatement(query);
					prepStatement.setDate(1, fromDate);
					prepStatement.setDate(2, toDate);
					prepStatement.setString(3, clmApplicationStatusFlag);
					prepStatement.setString(4, bankId);
					prepStatement.setString(5, zoneId);
					prepStatement.setString(6, branchId);

					rs = (ResultSet) prepStatement.executeQuery();
				}

				if (clmApplicationStatusFlag.equals("AS")) {
					// kot11

					query = " SELECT  m.mem_bnk_id || m.mem_zne_id || m.mem_brn_id memberid, m.mem_bank_name bnkname,mem_zone_name zone,\n"
							+ "         decode(mem_branch_name,null,app_mli_branch_name,mem_branch_name) branch,\n"
							+ "                 a.cgpan, S.SSI_UNIT_NAME,\n"
							+ "         decode(nvl(app_reapprove_amount,0),0,app_approved_amount,app_Reapprove_amount) appamt,\n"
							+ "         c.clm_ref_no,\n"
							+ "                 caa_applied_amount clmApplAmt,CLM_DATE,\n"
							+ "         clm_status,\n"
							+ "clm_decl_recvd_dt,TRUNC(c.CLM_CREATED_MODIFIED_DT)  withdrawndt  \n"
							+ "         \n"
							+ "    FROM claim_detail_temp c,\n"
							+ "         member_info m,\n"
							+ "         SSI_DETAIL S,\n"
							+ "         claim_application_amount_temp caa,\n"
							+ "         application_detail a\n"
							+ "   WHERE     TRUNC(C.CLM_date) between ?  and ?   AND\n"
							+ "             C.BID = S.BID\n"
							+ "         AND c.clm_ref_no = caa.clm_ref_no\n"
							+ "   and c.mem_bnk_id = ? and c.mem_zne_id = ? and c.mem_brn_id = ? \n"
							+ "         AND caa.cgpan = a.cgpan\n"
							+ "         AND LTRIM (\n"
							+ "                RTRIM (UPPER (c.mem_bnk_id || c.mem_zne_id || c.mem_brn_id))) =\n"
							+ "                LTRIM (\n"
							+ "                   RTRIM (UPPER (m.mem_bnk_id || m.mem_zne_id || m.mem_brn_id)))  \n"
							+ "        \n"
							+ " union  all \n"
							+ " \n"
							+ "  SELECT  m.mem_bnk_id || m.mem_zne_id || m.mem_brn_id memberid, m.mem_bank_name bnkname,mem_zone_name zone,\n"
							+ "         decode(mem_branch_name,null,app_mli_branch_name,mem_branch_name) branch,\n"
							+ "                 a.cgpan, S.SSI_UNIT_NAME,\n"
							+ "         decode(nvl(app_reapprove_amount,0),0,app_approved_amount,app_Reapprove_amount) appamt,\n"
							+ "         c.clm_ref_no,\n"
							+ "                 caa_applied_amount clmApplAmt,CLM_DATE,\n"
							+ "         clm_status,\n"
							+ "clm_decl_recvd_dt,TRUNC(c.CLM_CREATED_MODIFIED_DT) withdrawndt \n"
							+ "        \n"
							+ "    FROM claim_detail c,\n"
							+ "         member_info m,\n"
							+ "         SSI_DETAIL S,\n"
							+ "         claim_application_amount caa,\n"
							+ "         application_detail a\n"
							+ "   WHERE     TRUNC(C.CLM_date) between ?  and ?  AND\n"
							+ "             C.BID = S.BID\n"
							+ "         AND c.clm_ref_no = caa.clm_ref_no\n"
							+ "   and c.mem_bnk_id = ? and c.mem_zne_id = ? and c.mem_brn_id = ? \n"
							+ "         AND caa.cgpan = a.cgpan\n"
							+ "         AND LTRIM (\n"
							+ "                RTRIM (UPPER (c.mem_bnk_id || c.mem_zne_id || c.mem_brn_id))) =\n"
							+ "                LTRIM (\n"
							+ "                   RTRIM (UPPER (m.mem_bnk_id || m.mem_zne_id || m.mem_brn_id)))  order by withdrawndt, bnkname \n"
							+ "        ";

					prepStatement = conn.prepareStatement(query);
					prepStatement.setDate(1, fromDate);
					prepStatement.setDate(2, toDate);
					prepStatement.setString(3, bankId);
					prepStatement.setString(4, zoneId);
					prepStatement.setString(5, branchId);
					prepStatement.setDate(6, fromDate);
					prepStatement.setDate(7, toDate);
					prepStatement.setString(8, bankId);
					prepStatement.setString(9, zoneId);
					prepStatement.setString(10, branchId);
					// prepStatement.setString(3,clmApplicationStatusFlag);

					rs = (ResultSet) prepStatement.executeQuery();
				}

				if (clmApplicationStatusFlag.equals("RTD")) {
					// kot10
					query = "SELECT a.mem_bnk_id || a.mem_zne_id || a.mem_brn_id memberid,  \n"
							+ "                                d.mem_bank_name bnkname,  \n"
							+ "                                d.mem_zone_name,  \n"
							+ "                                DECODE (d.MEM_BRANCH_NAME,  \n"
							+ "                                        NULL, e.APP_MLI_BRANCH_NAME,  \n"
							+ "                                        d.MEM_BRANCH_NAME)  \n"
							+ "                                   branch,                                  e.cgpan,                                  c.ssi_unit_name,  \n"
							+ "                                DECODE (e.app_reapprove_amount,  \n"
							+ "                                        NULL, e.APP_approved_amount,  \n"
							+ "                                        e.app_reapprove_amount)  \n"
							+ "                                   guarantamt,  \n"
							+ "                                A.clm_ref_no,  \n"
							+ "                                a.clm_date,  \n"
							+ "                                b.caa_applied_amount clmApplAmt,TRUNC(a.CLM_CREATED_MODIFIED_DT) dt \n"
							+ "                           FROM claim_detail_temp a,  \n"
							+ "                                claim_application_amount_temp b,  \n"
							+ "                                ssi_detail c,  \n"
							+ "                                member_info d,  \n"
							+ "                                application_detail e  \n"
							+ "                          WHERE     b.cgpan = e.cgpan  \n"
							+ "                                AND a.clm_ref_no = b.clm_ref_no  \n"
							+ "                                AND a.bid = c.bid  \n"
							+ "                                AND a.mem_bnk_id || a.mem_zne_id || a.mem_brn_id =  \n"
							+ "                                       d.mem_bnk_id || d.mem_zne_id || d.mem_brn_id  \n"
							+ "                            AND TRUNC (a.CLM_CREATED_MODIFIED_DT) bETWEEN ?  AND ?   \n"
							+ "  and a.mem_bnk_id = ? and a.mem_zne_id = ? and a.mem_brn_id = ? \n"
							+ "                                AND a.clm_status in ('RE','TR')    union all \n"
							+ " SELECT a.mem_bnk_id || a.mem_zne_id || a.mem_brn_id memberid,d.mem_bank_name bnkname,d.mem_zone_name, DECODE (d.MEM_BRANCH_NAME,\n"
							+ "                                           NULL, e.APP_MLI_BRANCH_NAME, \n"
							+ "                                            d.MEM_BRANCH_NAME)\n"
							+ "                                       branch,e.cgpan,c.ssi_unit_name, DECODE (e.app_reapprove_amount,\n"
							+ "                                            NULL, e.APP_approved_amount,\n"
							+ "                                            e.app_reapprove_amount)\n"
							+ "                                       guarantamt,A.clm_ref_no, a.clm_date,b.caa_applied_amount clmApplAmt,TRUNC(a.CLM_CREATED_MODIFIED_DT) dt \n"
							+ "                                                 FROM claim_detail a,\n"
							+ "                                    claim_application_amount b,\n"
							+ "                                    ssi_detail c,\n"
							+ "                                    member_info d,\n"
							+ "                                    application_detail e\n"
							+ "                              WHERE   \n"
							+ "                                   b.cgpan = e.cgpan\n"
							+ "                                    AND a.clm_ref_no = b.clm_ref_no\n"
							+ "                                    AND a.bid = c.bid\n"
							+ "                                    AND a.mem_bnk_id || a.mem_zne_id || a.mem_brn_id =\n"
							+ "                                           d.mem_bnk_id || d.mem_zne_id || d.mem_brn_id\n"
							+ "                                    AND TRUNC (a.CLM_CREATED_MODIFIED_DT) BETWEEN ?  AND ? \n"
							+ "  and a.mem_bnk_id = ? and a.mem_zne_id = ? and a.mem_brn_id = ? \n"
							+ "                                    AND a.clm_status in ('RE','TR')  order by dt,bnkname ";

					prepStatement = conn.prepareStatement(query);
					prepStatement.setDate(1, fromDate);
					prepStatement.setDate(2, toDate);
					prepStatement.setString(3, bankId);
					prepStatement.setString(4, zoneId);
					prepStatement.setString(5, branchId);
					prepStatement.setDate(6, fromDate);
					prepStatement.setDate(7, toDate);
					prepStatement.setString(8, bankId);
					prepStatement.setString(9, zoneId);
					prepStatement.setString(10, branchId);
					// prepStatement.setString(3,clmApplicationStatusFlag);

					rs = (ResultSet) prepStatement.executeQuery();
				}

				if (clmApplicationStatusFlag
						.equals(ClaimConstants.CLM_TEMPORARY_REJECT)) {
					// kot6
					Log.log(Log.INFO, "ReportDAO",
							"getListOfClaimRefNumbers()",
							"From Date is NULL, Status is Reject");

					query = "SELECT a.mem_bnk_id || a.mem_zne_id || a.mem_brn_id memberid,  \n"
							+ "                                d.mem_bank_name,  \n"
							+ "                                d.mem_zone_name,  \n"
							+ "                                DECODE (d.MEM_BRANCH_NAME,  \n"
							+ "                                        NULL, e.APP_MLI_BRANCH_NAME,  \n"
							+ "                                        d.MEM_BRANCH_NAME)  \n"
							+ "                                   branch,  \n"
							+ "                                e.cgpan,  \n"
							+ "                                c.ssi_unit_name,  \n"
							+ "                                DECODE (e.app_reapprove_amount,  \n"
							+ "                                        NULL, e.APP_approved_amount,  \n"
							+ "                                        e.app_reapprove_amount)  \n"
							+ "                                   guarantamt,  \n"
							+ "                                A.clm_ref_no,  \n"
							+ "                                a.clm_date,  \n"
							+ "                                b.caa_applied_amount clmRejAmt,TRUNC(a.CLM_CREATED_MODIFIED_DT),clm_return_remarks \n"
							+ "                           FROM claim_detail_temp a,  \n"
							+ "                                claim_application_amount_temp b,  \n"
							+ "                                ssi_detail c,  \n"
							+ "                                member_info d,  \n"
							+ "                                application_detail e  \n"
							+ "                          WHERE     b.cgpan = e.cgpan  \n"
							+ "                                AND a.clm_ref_no = b.clm_ref_no  \n"
							+ "                                AND a.bid = c.bid  \n"
							+ "                                AND a.mem_bnk_id || a.mem_zne_id || a.mem_brn_id =  \n"
							+ "                                       d.mem_bnk_id || d.mem_zne_id || d.mem_brn_id  \n"
							+ "                               AND TRUNC (CLM_CREATED_MODIFIED_DT) BETWEEN ?  AND ?   \n"
							+ "                                AND a.clm_status = ?  "
							+ "  and a.mem_bnk_id = ? and a.mem_zne_id = ? and a.mem_brn_id = ? \n"
							+ "order by TRUNC(CLM_CREATED_MODIFIED_DT), d.mem_bank_name ";
					prepStatement = conn.prepareStatement(query);
					prepStatement.setDate(1, fromDate);
					prepStatement.setDate(2, toDate);
					prepStatement.setString(3, clmApplicationStatusFlag);
					prepStatement.setString(4, bankId);
					prepStatement.setString(5, zoneId);
					prepStatement.setString(6, branchId);

					rs = (ResultSet) prepStatement.executeQuery();
				}
				if (clmApplicationStatusFlag
						.equals(ClaimConstants.CLM_WITHDRAWN)) {
					// kot7
					Log.log(Log.INFO, "ReportDAO",
							"getListOfClaimRefNumbers()",
							"From Date is NULL, Status is Reject");

					query = "SELECT a.mem_bnk_id || a.mem_zne_id || a.mem_brn_id memberid,  \n"
							+ "                                d.mem_bank_name bnkname,  \n"
							+ "                                d.mem_zone_name,  \n"
							+ "                                DECODE (d.MEM_BRANCH_NAME,  \n"
							+ "                                        NULL, e.APP_MLI_BRANCH_NAME,  \n"
							+ "                                        d.MEM_BRANCH_NAME)  \n"
							+ "                                   branch,  \n"
							+ "                                e.cgpan,  \n"
							+ "                                c.ssi_unit_name,  \n"
							+ "                                DECODE (e.app_reapprove_amount,  \n"
							+ "                                        NULL, e.APP_approved_amount,  \n"
							+ "                                        e.app_reapprove_amount)  \n"
							+ "                                   guarantamt,  \n"
							+ "                                A.clm_ref_no,  \n"
							+ "                                a.clm_date,  \n"
							+ "                                b.caa_applied_amount clmAPPAmt , TRUNC(a.CLM_CREATED_MODIFIED_DT) withdrawndt \n"
							+ "                           FROM claim_detail_temp a,  \n"
							+ "                                claim_application_amount_temp b,  \n"
							+ "                                ssi_detail c,  \n"
							+ "                                member_info d,  \n"
							+ "                                application_detail e  \n"
							+ "                          WHERE     b.cgpan = e.cgpan  \n"
							+ "                                AND a.clm_ref_no = b.clm_ref_no  \n"
							+ "                                AND a.bid = c.bid  \n"
							+ "                                AND a.mem_bnk_id || a.mem_zne_id || a.mem_brn_id =  \n"
							+ "                                       a.mem_bnk_id || a.mem_zne_id || a.mem_brn_id  \n"
							+ "                               AND TRUNC (a.CLM_CREATED_MODIFIED_DT) BETWEEN ?  AND ?   \n"
							+ "                                AND a.clm_status = ?     \n"
							+ "  and a.mem_bnk_id = ? and a.mem_zne_id = ? and a.mem_brn_id = ? \n"
							+ "union all \n"
							+ "SELECT a.mem_bnk_id || a.mem_zne_id || a.mem_brn_id memberid,  \n"
							+ "                                d.mem_bank_name bnkname,  \n"
							+ "                                d.mem_zone_name,  \n"
							+ "                                DECODE (d.MEM_BRANCH_NAME,  \n"
							+ "                                        NULL, e.APP_MLI_BRANCH_NAME,  \n"
							+ "                                        d.MEM_BRANCH_NAME)  \n"
							+ "                                   branch,  \n"
							+ "                                e.cgpan,  \n"
							+ "                                c.ssi_unit_name,  \n"
							+ "                                DECODE (e.app_reapprove_amount,  \n"
							+ "                                        NULL, e.APP_approved_amount,  \n"
							+ "                                        e.app_reapprove_amount)  \n"
							+ "                                   guarantamt,  \n"
							+ "                                A.clm_ref_no,  \n"
							+ "                                a.clm_date,  \n"
							+ "                                b.caa_applied_amount clmappAmt,TRUNC(a.CLM_CREATED_MODIFIED_DT) withdrawndt  \n"
							+ "                           FROM claim_detail a,  \n"
							+ "                                claim_application_amount b,  \n"
							+ "                                ssi_detail c,  \n"
							+ "                                member_info d,  \n"
							+ "                                application_detail e  \n"
							+ "                          WHERE     b.cgpan = e.cgpan  \n"
							+ "                                AND a.clm_ref_no = b.clm_ref_no  \n"
							+ "                                AND a.bid = c.bid  \n"
							+ "                                AND a.mem_bnk_id || a.mem_zne_id || a.mem_brn_id =  \n"
							+ "                                       a.mem_bnk_id || a.mem_zne_id || a.mem_brn_id  \n"
							+ "                                AND TRUNC (CLM_CREATED_MODIFIED_DT) BETWEEN ?  AND ?   \n"
							+ "                                AND a.clm_status = ? "
							+ "  and   a.mem_bnk_id = ? and a.mem_zne_id = ? and a.mem_brn_id = ?  \n"
							+ " order by withdrawndt, bnkname  ";
					prepStatement = conn.prepareStatement(query);
					prepStatement.setDate(1, fromDate);
					prepStatement.setDate(2, toDate);
					prepStatement.setString(3, clmApplicationStatusFlag);
					prepStatement.setString(4, bankId);
					prepStatement.setString(5, zoneId);
					prepStatement.setString(6, branchId);
					prepStatement.setDate(7, fromDate);
					prepStatement.setDate(8, toDate);
					prepStatement.setString(9, clmApplicationStatusFlag);
					prepStatement.setString(10, bankId);
					prepStatement.setString(11, zoneId);
					prepStatement.setString(12, branchId);

					rs = (ResultSet) prepStatement.executeQuery();
				}

				if (clmApplicationStatusFlag
						.equals(ClaimConstants.CLM_REJECT_STATUS)) {
					// kot3
					Log.log(Log.INFO, "ReportDAO",
							"getListOfClaimRefNumbers()",
							"From Date is NULL, Status is Reject");

					query = "  SELECT a.mem_bnk_id || a.mem_zne_id || a.mem_brn_id,d.mem_bank_name,d.mem_zone_name, DECODE (d.MEM_BRANCH_NAME,\n"
							+ "                 NULL, e.APP_MLI_BRANCH_NAME,\n"
							+ "                 d.MEM_BRANCH_NAME)\n"
							+ "            branch,e.cgpan,c.ssi_unit_name, DECODE (e.app_reapprove_amount,\n"
							+ "                 NULL, e.APP_approved_amount,\n"
							+ "                 e.app_reapprove_amount)\n"
							+ "            guarantamt,A.clm_ref_no, a.clm_date,b.caa_applied_amount clmAppdAmt, TRUNC(a.CLM_CREATED_MODIFIED_DT),clm_return_remarks \n"
							+ "                      FROM claim_detail a,\n"
							+ "         claim_application_amount b,\n"
							+ "         ssi_detail c,\n"
							+ "         member_info d,\n"
							+ "         application_detail e\n"
							+ "   WHERE   \n"
							+ "        b.cgpan = e.cgpan\n"
							+ "         AND a.clm_ref_no = b.clm_ref_no\n"
							+ "         AND a.bid = c.bid\n"
							+ "         AND a.mem_bnk_id || a.mem_zne_id || a.mem_brn_id =\n"
							+ "                d.mem_bnk_id || d.mem_zne_id || d.mem_brn_id\n"
							+ "         AND TRUNC (a.CLM_CREATED_MODIFIED_DT) BETWEEN ?  AND ? \n"
							+ "         AND a.clm_status = ? \n"
							+ "  and   a.mem_bnk_id = ? and a.mem_zne_id = ? and a.mem_brn_id = ?  \n"
							+ "  order by TRUNC(CLM_CREATED_MODIFIED_DT), d.mem_bank_name  ";

					prepStatement = conn.prepareStatement(query);
					prepStatement.setDate(1, fromDate);
					prepStatement.setDate(2, toDate);
					prepStatement.setString(3, clmApplicationStatusFlag);
					prepStatement.setString(4, bankId);
					prepStatement.setString(5, zoneId);
					prepStatement.setString(6, branchId);

					rs = (ResultSet) prepStatement.executeQuery();
				}
				// ADDED FORWARD REPORT BY SUKUMAR@PATH ON 12-09-2009
				if (clmApplicationStatusFlag
						.equals(ClaimConstants.CLM_FORWARD_STATUS)) {
					Log.log(Log.INFO, "ReportDAO",
							"getListOfClaimRefNumbers()",
							"From Date is NULL, Status is  Forward");
					// kot4
					query = "SELECT m.mem_bnk_id || m.mem_zne_id || m.mem_brn_id,\n"
							+ "       m.mem_bank_name bnkname,\n"
							+ "       m.mem_zone_name,\n"
							+ "       DECODE (m.MEM_BRANCH_NAME,\n"
							+ "               NULL, a.APP_MLI_BRANCH_NAME,\n"
							+ "               m.MEM_BRANCH_NAME)\n"
							+ "          BRANCH,\n"
							+ "       d.cgpan,\n"
							+ "       S.SSI_UNIT_NAME,\n"
							+ "       DECODE (a.app_reapprove_amount,\n"
							+ "               NULL, a.APP_approved_amount,\n"
							+ "               a.app_reapprove_amount)\n"
							+ "          guartAmt,\n"
							+ "       c.clm_ref_no,\n"
							+ "       C.CLM_DATE clmFilgDt,\n"
							+ "       d.ctd_tc_first_inst_pay_amt clmForwrdAmt, \n"
							+ "       TRUNC (CLM_CREATED_MODIFIED_DT) clmForwdDt \n"
							+ "  FROM claim_detail_temp c,\n"
							+ "       member_info m,\n"
							+ "       SSI_DETAIL S,\n"
							+ "       application_detail a,\n"
							+ "       CLAIM_TC_DETAIL_TEMP D\n"
							+ " WHERE     TRUNC (C.CLM_CREATED_MODIFIED_DT) BETWEEN ? \n"
							+ "                                                 AND  ? \n"
							+ "       AND C.BID = S.BID\n"
							+ "       AND s.ssi_reference_number = a.ssi_reference_number\n"
							+ "       AND c.mem_bnk_id || c.mem_brn_id || c.mem_zne_id =\n"
							+ "              a.mem_bnk_id || a.mem_brn_id || a.mem_zne_id\n"
							+ "       AND LTRIM (\n"
							+ "              RTRIM (UPPER (c.mem_bnk_id || c.mem_zne_id || c.mem_brn_id))) =\n"
							+ "              LTRIM (\n"
							+ "                 RTRIM (UPPER (m.mem_bnk_id || m.mem_zne_id || m.mem_brn_id)))\n"
							+ "       AND c.clm_status = ? \n"
							+ "  and   c.mem_bnk_id = ? and c.mem_zne_id = ? and c.mem_brn_id = ?  \n"
							+ "       AND C.CLM_REF_NO = D.CLM_REF_NO\n"
							+ "       AND d.cgpan = a.cgpan\n"
							+ "       AND app_loan_type IN ('TC')   \n"
							+ "UNION ALL\n"
							+ "SELECT m.mem_bnk_id || m.mem_zne_id || m.mem_brn_id,\n"
							+ "       m.mem_bank_name bnkname,\n"
							+ "       m.mem_zone_name,\n"
							+ "       DECODE (m.MEM_BRANCH_NAME,\n"
							+ "               NULL, a.APP_MLI_BRANCH_NAME,\n"
							+ "               m.MEM_BRANCH_NAME)\n"
							+ "          BRANCH,\n"
							+ "       d.cgpan,\n"
							+ "       S.SSI_UNIT_NAME,\n"
							+ "       DECODE (a.app_reapprove_amount,\n"
							+ "               NULL, a.APP_approved_amount,\n"
							+ "               a.app_reapprove_amount)\n"
							+ "          guartAmt,\n"
							+ "       c.clm_ref_no,\n"
							+ "       C.CLM_DATE clmFilgDt,\n"
							+ "       d.cwd_wc_first_inst_pay_amt clmForwrdAmt,\n"
							+ "       TRUNC (CLM_CREATED_MODIFIED_DT) clmForwdDt \n"
							+ "  FROM claim_detail_temp c,\n"
							+ "       member_info m,\n"
							+ "       SSI_DETAIL S,\n"
							+ "       application_detail a,\n"
							+ "       CLAIM_WC_DETAIL_TEMP D\n"
							+ " WHERE     TRUNC (C.CLM_CREATED_MODIFIED_DT) BETWEEN ? \n"
							+ "                                                 AND ?  \n"
							+ "       AND C.BID = S.BID\n"
							+ "       AND s.ssi_reference_number = a.ssi_reference_number\n"
							+ "       AND c.mem_bnk_id || c.mem_brn_id || c.mem_zne_id =\n"
							+ "              a.mem_bnk_id || a.mem_brn_id || a.mem_zne_id\n"
							+ "       AND LTRIM (\n"
							+ "              RTRIM (UPPER (c.mem_bnk_id || c.mem_zne_id || c.mem_brn_id))) =\n"
							+ "              LTRIM (\n"
							+ "                 RTRIM (UPPER (m.mem_bnk_id || m.mem_zne_id || m.mem_brn_id)))\n"
							+ "       AND c.clm_status = ? \n"
							+ "  and   c.mem_bnk_id = ? and c.mem_zne_id = ? and c.mem_brn_id = ?  \n"
							+ "       AND C.CLM_REF_NO = D.CLM_REF_NO\n"
							+ "       AND d.cgpan = a.cgpan\n"
							+ "       AND app_loan_type IN ('WC')   \n"
							+ "UNION ALL\n"
							+ "SELECT m.mem_bnk_id || m.mem_zne_id || m.mem_brn_id,\n"
							+ "       m.mem_bank_name bnkname,\n"
							+ "       m.mem_zone_name,\n"
							+ "       DECODE (m.MEM_BRANCH_NAME,\n"
							+ "               NULL, a.APP_MLI_BRANCH_NAME,\n"
							+ "               m.MEM_BRANCH_NAME)\n"
							+ "          BRANCH,\n"
							+ "       d.cgpan,\n"
							+ "       S.SSI_UNIT_NAME,\n"
							+ "       DECODE (a.app_reapprove_amount,\n"
							+ "               NULL, a.APP_approved_amount,\n"
							+ "               a.app_reapprove_amount)\n"
							+ "          guartAmt,\n"
							+ "       c.clm_ref_no,\n"
							+ "       C.CLM_DATE clmFilgDt,\n"
							+ "         NVL (d.cwd_wc_first_inst_pay_amt, 0)\n"
							+ "       + NVL (CWD_WC_FIRST_INST_PAY_AMT, 0)\n"
							+ "          clmForwrdAmt,\n"
							+ "       TRUNC (CLM_CREATED_MODIFIED_DT) clmForwdDt \n"
							+ "  FROM claim_detail_temp c,\n"
							+ "       member_info m,\n"
							+ "       SSI_DETAIL S,\n"
							+ "       application_detail a,\n"
							+ "       CLAIM_WC_DETAIL_TEMP D,\n"
							+ "       CLAIM_TC_DETAIL_TEMP E\n"
							+ " WHERE     TRUNC (C.CLM_CREATED_MODIFIED_DT) BETWEEN ? \n"
							+ "                                                 AND ? \n"
							+ "       AND C.BID = S.BID\n"
							+ "       AND s.ssi_reference_number = a.ssi_reference_number\n"
							+ "       AND c.mem_bnk_id || c.mem_brn_id || c.mem_zne_id =\n"
							+ "              a.mem_bnk_id || a.mem_brn_id || a.mem_zne_id\n"
							+ "       AND LTRIM (\n"
							+ "              RTRIM (UPPER (c.mem_bnk_id || c.mem_zne_id || c.mem_brn_id))) =\n"
							+ "              LTRIM (\n"
							+ "                 RTRIM (UPPER (m.mem_bnk_id || m.mem_zne_id || m.mem_brn_id)))\n"
							+ "       AND c.clm_status = ? \n"
							+ "  and   c.mem_bnk_id = ? and c.mem_zne_id = ? and c.mem_brn_id = ?  \n"
							+ "       AND C.CLM_REF_NO = D.CLM_REF_NO\n"
							+ "       AND D.cgpan = a.cgpan\n"
							+ "       AND C.CLM_REF_NO = E.CLM_REF_NO\n"
							+ "       AND E.cgpan = a.cgpan\n"
							+ "       AND app_loan_type IN ('CC') order by clmForwdDt, bnkname ";

					prepStatement = conn.prepareStatement(query);
					prepStatement.setDate(1, fromDate);
					prepStatement.setDate(2, toDate);
					prepStatement.setString(3, clmApplicationStatusFlag);

					prepStatement.setString(4, bankId);
					prepStatement.setString(5, zoneId);
					prepStatement.setString(6, branchId);

					prepStatement.setDate(7, fromDate);
					prepStatement.setDate(8, toDate);
					prepStatement.setString(9, clmApplicationStatusFlag);

					prepStatement.setString(10, bankId);
					prepStatement.setString(11, zoneId);
					prepStatement.setString(12, branchId);

					prepStatement.setDate(13, fromDate);
					prepStatement.setDate(14, toDate);
					prepStatement.setString(15, clmApplicationStatusFlag);
					prepStatement.setString(16, bankId);
					prepStatement.setString(17, zoneId);
					prepStatement.setString(18, branchId);
					rs = (ResultSet) prepStatement.executeQuery();
				}
				/*
				 * ADDED BY SUKUMAR@PATH ON 20-FEB-2010 FOR DISPLAY TEMPORARY
				 * CLOSE & TEMPORARY REJECT APPLICATION
				 */
				if (clmApplicationStatusFlag.equals("KTC")
						|| clmApplicationStatusFlag.equals("KWC")
						|| clmApplicationStatusFlag.equals("KWD")) {
					Log.log(Log.INFO, "ReportDAO",
							"getListOfClaimRefNumbers()",
							"From Date is NULL, Status is  TEMPORARY CLOSED");
					// new kot
					query = "SELECT a.mem_bnk_id || a.mem_zne_id || a.mem_brn_id,\n"
							+ "       d.mem_bank_name,\n"
							+ "       d.mem_zone_name,\n"
							+ "       DECODE (d.MEM_BRANCH_NAME,\n"
							+ "               NULL, e.APP_MLI_BRANCH_NAME,\n"
							+ "               d.MEM_BRANCH_NAME)\n"
							+ "          branch,\n"
							+ "       e.cgpan,\n"
							+ "       c.ssi_unit_name,\n"
							+ "       DECODE (e.app_reapprove_amount,\n"
							+ "               NULL, e.APP_approved_amount,\n"
							+ "               e.app_reapprove_amount)\n"
							+ "          guarantamt,\n"
							+ "       A.clm_ref_no,\n"
							+ "       a.clm_date,\n"
							+ "       b.caa_applied_amount clmAppdAmt\n"
							+ "  FROM claim_detail_temp a,\n"
							+ "       claim_application_amount_temp b,\n"
							+ "       ssi_detail c,\n"
							+ "       member_info d,\n"
							+ "       application_detail e\n"
							+ " WHERE     b.cgpan = e.cgpan\n"
							+ "       AND a.clm_ref_no = b.clm_ref_no\n"
							+ "       AND a.bid = c.bid\n"
							+ "       AND a.mem_bnk_id || a.mem_zne_id || a.mem_brn_id =\n"
							+ "              d.mem_bnk_id || d.mem_zne_id || d.mem_brn_id\n"
							+ "       AND TRUNC (CLM_DATE) BETWEEN ?  AND ? \n"
							+ "       AND a.clm_status = ? \n";

					prepStatement = conn.prepareStatement(query);
					prepStatement.setDate(1, fromDate);
					prepStatement.setDate(2, toDate);
					prepStatement.setString(3, clmApplicationStatusFlag);

					rs = (ResultSet) prepStatement.executeQuery();
				}
				// added by upchar@path on 03/07/2013 to display reply received
				// claim applications
				if (clmApplicationStatusFlag
						.equals(ClaimConstants.CLM_REPLY_RECEIVED)) {
					// kot8
					Log.log(Log.INFO, "ReportDAO",
							"getListOfClaimRefNumbers()",
							"From Date is NULL, Status is  TEMPORARY CLOSED");

					query = "SELECT a.mem_bnk_id || a.mem_zne_id || a.mem_brn_id memberid,  \n"
							+ "                                d.mem_bank_name,  \n"
							+ "                                d.mem_zone_name,  \n"
							+ "                                DECODE (d.MEM_BRANCH_NAME,  \n"
							+ "                                        NULL, e.APP_MLI_BRANCH_NAME,  \n"
							+ "                                        d.MEM_BRANCH_NAME)  \n"
							+ "                                   branch,  \n"
							+ "                                e.cgpan,  \n"
							+ "                                c.ssi_unit_name,  \n"
							+ "                                DECODE (e.app_reapprove_amount,  \n"
							+ "                                        NULL, e.APP_approved_amount,  \n"
							+ "                                        e.app_reapprove_amount)  \n"
							+ "                                   guarantamt,  \n"
							+ "                                A.clm_ref_no,  \n"
							+ "                                a.clm_date,  \n"
							+ "                                b.caa_applied_amount clmAppdAmt,TRUNC(a.CLM_CREATED_MODIFIED_DT), \n"
							+ "                                (select distinct max(trim(upper(replace(clm_ltr_ref_no,' ',''))))\n"
							+ "                                from claim_query_detail cqd\n"
							+ "                                where a.clm_ref_no = cqd.clm_ref_no\n"
							+ "                                and clm_ltr_dt in \n"
							+ "                                (select max(clm_ltr_dt) from claim_query_detail cd where cd.clm_ref_no = cqd.clm_ref_no)\n"
							+ "                                ) clmqryrefno,\n"
							+ "                                (select distinct clm_ltr_dt\n"
							+ "                                from claim_query_detail cq\n"
							+ "                                where a.clm_ref_no = cq.clm_ref_no\n"
							+ "                                and clm_ltr_dt in \n"
							+ "                                (select max(clm_ltr_dt) from claim_query_detail cd where cd.clm_ref_no = cq.clm_ref_no)\n"
							+ "                                ) clmqrydt,\n"
							+ "                                (select inward_id from claim_reply_detail crd\n"
							+ "                                where crd.clm_ref_no = a.clm_ref_no\n"
							+ "                                and inward_dt in \n"
							+ "                                (select max(inward_dt) from claim_reply_detail cd where cd.clm_ref_no = crd.clm_ref_no)\n"
							+ "                                ) rplyinwid,\n"
							+ "                                (select inward_dt from claim_reply_detail crd\n"
							+ "                                where crd.clm_ref_no = a.clm_ref_no\n"
							+ "                                and inward_dt in \n"
							+ "                                (select max(inward_dt) from claim_reply_detail cd where cd.clm_ref_no = crd.clm_ref_no)\n"
							+ "                                ) rplyinwdt ,TRUNC(a.CLM_CREATED_MODIFIED_DT) \n"
							+ "                           FROM claim_detail_temp a,  \n"
							+ "                                claim_application_amount_temp b,  \n"
							+ "                                ssi_detail c,  \n"
							+ "                                member_info d,  \n"
							+ "                                application_detail e  \n"
							+ "                          WHERE     b.cgpan = e.cgpan  \n"
							+ "                                AND a.clm_ref_no = b.clm_ref_no  \n"
							+ "                                AND a.bid = c.bid  \n"
							+ "                                AND a.mem_bnk_id || a.mem_zne_id || a.mem_brn_id =  \n"
							+ "                                       d.mem_bnk_id || d.mem_zne_id || d.mem_brn_id  \n"
							+ "                               AND TRUNC (CLM_CREATED_MODIFIED_DT) BETWEEN ?  AND ?   \n"
							+ "                                AND a.clm_status = ?   \n"
							+ "  and   a.mem_bnk_id = ? and a.mem_zne_id = ? and a.mem_brn_id = ?  \n"
							+ " order by TRUNC(CLM_CREATED_MODIFIED_DT),d.mem_bank_name ";

					prepStatement = conn.prepareStatement(query);
					prepStatement.setDate(1, fromDate);
					prepStatement.setDate(2, toDate);
					prepStatement.setString(3, clmApplicationStatusFlag);
					prepStatement.setString(4, bankId);
					prepStatement.setString(5, zoneId);
					prepStatement.setString(6, branchId);
					rs = (ResultSet) prepStatement.executeQuery();
				}
			}
			if ((fromDate != null)
					&& ((clmApplicationStatusFlag
							.equals(ClaimConstants.CLM_PENDING_STATUS) || clmApplicationStatusFlag
							.equals(ClaimConstants.CLM_HOLD_STATUS)
					// clmApplicationStatusFlag.equals(ClaimConstants.CLM_FORWARD_STATUS)
					))) {
				Log.log(Log.INFO, "ReportDAO", "getListOfClaimRefNumbers()",
						"From Date is NULL, Status is Pending or Forward or Hold");
				// new kot2
				query = "SELECT  m.mem_bnk_id || m.mem_zne_id || m.mem_brn_id,m.mem_bank_name bnkname,m.mem_zone_name, DECODE (m.MEM_BRANCH_NAME,\n"
						+ "                 NULL, a.APP_MLI_BRANCH_NAME,\n"
						+ "                 m.MEM_BRANCH_NAME)\n"
						+ "            BRANCH,a.cgpan,S.SSI_UNIT_NAME,DECODE (a.app_reapprove_amount,\n"
						+ "                 NULL, a.APP_approved_amount,\n"
						+ "                 a.app_reapprove_amount)\n"
						+ "            GuarAmt,c.clm_ref_no, C.CLM_DATE, b.caa_applied_amount clmAppliedAmt,C.CLM_DECLARATION_RECVD clmDeclRcvdFlg, C.CLM_DECL_RECVD_DT clmDeclRecvdDt\n"
						+ "                                      \n"
						+ "    FROM claim_detail_temp c,\n"
						+ "    claim_application_amount_temp b,\n"
						+ "         member_info m,\n"
						+ "         SSI_DETAIL S,\n"
						+ "         application_detail a\n"
						+ "         \n"
						+ "   WHERE     TRUNC (c.clm_date) BETWEEN ?  AND ? \n"
						+ "         AND C.BID = S.BID\n"
						+ "         AND s.ssi_reference_number = a.ssi_reference_number\n"
						+ "         AND c.mem_bnk_id || c.mem_brn_id || c.mem_zne_id =\n"
						+ "                a.mem_bnk_id || a.mem_brn_id || a.mem_zne_id\n"
						+ "         AND LTRIM (\n"
						+ "                RTRIM (UPPER (c.mem_bnk_id || c.mem_zne_id || c.mem_brn_id))) =\n"
						+ "                LTRIM (\n"
						+ "                   RTRIM (UPPER (m.mem_bnk_id || m.mem_zne_id || m.mem_brn_id)))\n"
						+ "         AND c.clm_status = ? \n"
						+ "  and   c.mem_bnk_id = ? and c.mem_zne_id = ? and c.mem_brn_id = ?  \n"
						+ "         AND C.CLM_REF_NO = b.CLM_REF_NO\n"
						+ "         AND b.CGPAN = a.CGPAN  order by TRUNC(CLM_CREATED_MODIFIED_DT),bnkname ";

				prepStatement = conn.prepareStatement(query);
				prepStatement.setDate(1, fromDate);
				prepStatement.setDate(2, toDate);
				prepStatement.setString(3, clmApplicationStatusFlag);
				prepStatement.setString(4, bankId);
				prepStatement.setString(5, zoneId);
				prepStatement.setString(6, branchId);

				rs = (ResultSet) prepStatement.executeQuery();
			}
			if ((fromDate == null)
					&& (!(clmApplicationStatusFlag
							.equals(ClaimConstants.CLM_PENDING_STATUS) || clmApplicationStatusFlag
							.equals(ClaimConstants.CLM_HOLD_STATUS)
					// clmApplicationStatusFlag.equals(ClaimConstants.CLM_FORWARD_STATUS)
					))) {
				if (clmApplicationStatusFlag
						.equals(ClaimConstants.CLM_APPROVAL_STATUS)) {
					Log.log(Log.INFO, "ReportDAO",
							"getListOfClaimRefNumbers()",
							"From Date is NULL, Status is Approval");
					query = "select m.mem_bank_name bnkname, m.mem_bnk_id || m.mem_zne_id || m.mem_brn_id, c.clm_ref_no, c.cgclan,S.SSI_UNIT_NAME,TRUNC(CLM_APPROVED_DT),CLM_APPROVED_AMT "
							+ " from claim_detail c, member_info m,SSI_DETAIL S "
							+
							// code changed clm_date to clm_approved_dt by
							// sukumar@path on 11-09-2009
							// " where c.clm_date <= ? AND C.BID=S.BID " +
							" where TRUNC(c.clm_approved_dt) <= ? AND C.BID=S.BID "
							+ " and LTRIM(RTRIM(UPPER(c.mem_bnk_id || c.mem_zne_id || c.mem_brn_id))) = LTRIM(RTRIM(UPPER(m.mem_bnk_id || m.mem_zne_id || m.mem_brn_id))) "
							+ " and c.clm_status = ?"
							+ " group by m.mem_bank_name,"
							+ " m.mem_bnk_id || m.mem_zne_id || m.mem_brn_id,"
							+ " c.clm_ref_no,"
							+ " c.cgclan,S.SSI_UNIT_NAME,TRUNC(CLM_APPROVED_DT),CLM_APPROVED_AMT order by Trunc(c.clm_approved_dt),bnkname";
					prepStatement = conn.prepareStatement(query);
					// prepStatement.setDate(1,fromDate);
					prepStatement.setDate(1, toDate);
					prepStatement.setString(2, clmApplicationStatusFlag);
					rs = (ResultSet) prepStatement.executeQuery();
				} else if (clmApplicationStatusFlag
						.equals(ClaimConstants.CLM_REJECT_STATUS)) {
					Log.log(Log.INFO, "ReportDAO",
							"getListOfClaimRefNumbers()",
							"From Date is NULL, Status is Reject");

					query = "  SELECT a.mem_bnk_id || a.mem_zne_id || a.mem_brn_id,d.mem_bank_name,d.mem_zone_name, DECODE (d.MEM_BRANCH_NAME,\n"
							+ "                 NULL, e.APP_MLI_BRANCH_NAME,\n"
							+ "                 d.MEM_BRANCH_NAME)\n"
							+ "            branch,e.cgpan,c.ssi_unit_name, DECODE (e.app_reapprove_amount,\n"
							+ "                 NULL, e.APP_approved_amount,\n"
							+ "                 e.app_reapprove_amount)\n"
							+ "            guarantamt,A.clm_ref_no, a.clm_date,b.caa_applied_amount clmAppdAmt \n"
							+ "                      FROM claim_detail a,\n"
							+ "         claim_application_amount b,\n"
							+ "         ssi_detail c,\n"
							+ "         member_info d,\n"
							+ "         application_detail e\n"
							+ "   WHERE   \n"
							+ "        b.cgpan = e.cgpan\n"
							+ "         AND a.clm_ref_no = b.clm_ref_no\n"
							+ "         AND a.bid = c.bid\n"
							+ "         AND a.mem_bnk_id || a.mem_zne_id || a.mem_brn_id =\n"
							+ "                d.mem_bnk_id || d.mem_zne_id || d.mem_brn_id\n"
							+ "         AND TRUNC (a.CLM_CREATED_MODIFIED_DT) BETWEEN ?  AND ? \n"
							+ "         AND a.clm_status = ? ";

					prepStatement = conn.prepareStatement(query);
					prepStatement.setDate(1, fromDate);
					prepStatement.setDate(2, toDate);
					prepStatement.setString(3, clmApplicationStatusFlag);

					rs = (ResultSet) prepStatement.executeQuery();
				} else if (clmApplicationStatusFlag
						.equals(ClaimConstants.CLM_FORWARD_STATUS)) {
					Log.log(Log.INFO, "ReportDAO",
							"getListOfClaimRefNumbers()",
							"From Date is NULL, Status is Forward");
					query = "select m.mem_bank_name bnkname, m.mem_bnk_id || m.mem_zne_id || m.mem_brn_id, c.clm_ref_no,S.SSI_UNIT_NAME,TRUNC(CLM_CREATED_MODIFIED_DT) "
							+ " from claim_detail_temp c, member_info m,SSI_DETAIL S"
							+ " where TRUNC(C.CLM_CREATED_MODIFIED_DT) <= ? AND C.BID=S.BID "
							+ " and LTRIM(RTRIM(UPPER(c.mem_bnk_id || c.mem_zne_id || c.mem_brn_id))) = LTRIM(RTRIM(UPPER(m.mem_bnk_id || m.mem_zne_id || m.mem_brn_id))) "
							+ " and c.clm_status = ?"
							+ " group by m.mem_bank_name,"
							+ " m.mem_bnk_id || m.mem_zne_id || m.mem_brn_id,"
							+ " c.clm_ref_no,S.SSI_UNIT_NAME,TRUNC(CLM_CREATED_MODIFIED_DT) order by TRUNC(CLM_CREATED_MODIFIED_DT),bnkname";

					prepStatement = conn.prepareStatement(query);
					prepStatement.setDate(1, toDate);
					prepStatement.setString(2, clmApplicationStatusFlag);
					rs = (ResultSet) prepStatement.executeQuery();
				} else if (clmApplicationStatusFlag
						.equals(ClaimConstants.CLM_TEMPORARY_CLOSE)
						|| clmApplicationStatusFlag
								.equals(ClaimConstants.CLM_TEMPORARY_REJECT)
						|| clmApplicationStatusFlag
								.equals(ClaimConstants.CLM_WITHDRAWN)) {
					Log.log(Log.INFO, "ReportDAO",
							"getListOfClaimRefNumbers()",
							"From Date is NULL, Status is Forward");
					query = "select m.mem_bank_name bnkname, m.mem_bnk_id || m.mem_zne_id || m.mem_brn_id, c.clm_ref_no,S.SSI_UNIT_NAME,TRUNC(CLM_CREATED_MODIFIED_DT) "
							+ " from claim_detail_temp c, member_info m,SSI_DETAIL S"
							+ " where TRUNC(C.CLM_CREATED_MODIFIED_DT) <= ? AND C.BID=S.BID "
							+ " and LTRIM(RTRIM(UPPER(c.mem_bnk_id || c.mem_zne_id || c.mem_brn_id))) = LTRIM(RTRIM(UPPER(m.mem_bnk_id || m.mem_zne_id || m.mem_brn_id))) "
							+ " and c.clm_status = ?"
							+ " group by m.mem_bank_name,"
							+ " m.mem_bnk_id || m.mem_zne_id || m.mem_brn_id,"
							+ " c.clm_ref_no,S.SSI_UNIT_NAME,TRUNC(CLM_CREATED_MODIFIED_DT) order by TRUNC(CLM_CREATED_MODIFIED_DT),bnkname";
					prepStatement = conn.prepareStatement(query);
					prepStatement.setDate(1, toDate);
					prepStatement.setString(2, clmApplicationStatusFlag);
					rs = (ResultSet) prepStatement.executeQuery();
				} else if (clmApplicationStatusFlag
						.equals(ClaimConstants.CLM_REPLY_RECEIVED)) {
					Log.log(Log.INFO, "ReportDAO",
							"getListOfClaimRefNumbers()",
							"From Date is NULL, Status is  TEMPORARY CLOSED");
					query = "select m.mem_bank_name bnkname, m.mem_bnk_id || m.mem_zne_id || m.mem_brn_id, c.clm_ref_no,S.SSI_UNIT_NAME,TRUNC(CLM_CREATED_MODIFIED_DT) "
							+ " from claim_detail_temp c, member_info m,SSI_DETAIL S"
							+ " where TRUNC(C.CLM_CREATED_MODIFIED_DT) <= ? AND C.BID=S.BID "
							+ " and LTRIM(RTRIM(UPPER(c.mem_bnk_id || c.mem_zne_id || c.mem_brn_id))) = LTRIM(RTRIM(UPPER(m.mem_bnk_id || m.mem_zne_id || m.mem_brn_id))) "
							+ " and c.clm_status = ?"
							+ " group by m.mem_bank_name,"
							+ " m.mem_bnk_id || m.mem_zne_id || m.mem_brn_id,"
							+ " c.clm_ref_no,S.SSI_UNIT_NAME,TRUNC(CLM_CREATED_MODIFIED_DT) order by TRUNC(CLM_CREATED_MODIFIED_DT),bnkname";
					prepStatement = conn.prepareStatement(query);
					prepStatement.setDate(1, toDate);
					prepStatement.setString(2, clmApplicationStatusFlag);
					rs = (ResultSet) prepStatement.executeQuery();
				}
			}
			if ((fromDate == null)
					&& ((clmApplicationStatusFlag
							.equals(ClaimConstants.CLM_PENDING_STATUS) || clmApplicationStatusFlag
							.equals(ClaimConstants.CLM_HOLD_STATUS)
					// clmApplicationStatusFlag.equals(ClaimConstants.CLM_FORWARD_STATUS)
					))) {
				Log.log(Log.INFO, "ReportDAO", "getListOfClaimRefNumbers()",
						"From Date is NULL, Status is Pending or Forward or Hold");
				query = "select m.mem_bank_name bnkname, m.mem_bnk_id || m.mem_zne_id || m.mem_brn_id, c.clm_ref_no,S.SSI_UNIT_NAME,CLM_DATE "
						+ " from claim_detail_temp c, member_info m,SSI_DETAIL S"
						+ " where c.clm_date <= ? AND C.BID=S.BID "
						+ " and LTRIM(RTRIM(UPPER(c.mem_bnk_id || c.mem_zne_id || c.mem_brn_id))) = LTRIM(RTRIM(UPPER(m.mem_bnk_id || m.mem_zne_id || m.mem_brn_id))) "
						+ " and c.clm_status = ?"
						+ " group by m.mem_bank_name,"
						+ " m.mem_bnk_id || m.mem_zne_id || m.mem_brn_id,"
						+ " c.clm_ref_no,S.SSI_UNIT_NAME,CLM_DATE order by CLM_DATE,bnkname";
				prepStatement = conn.prepareStatement(query);
				prepStatement.setDate(1, toDate);
				prepStatement.setString(2, clmApplicationStatusFlag);
				rs = (ResultSet) prepStatement.executeQuery();
			}
			String memberBankName = null;
			String memberId = null;
			String clmRefNumber = null;
			String cgclan = "";
			ClaimDetail clmDtl = null;
			String unitName = "";
			java.util.Date submittedDt = null;
			java.util.Date clmLodgmentdDt = null;
			java.util.Date claimForwardedDt = null;

			String claimRetRemarks = "";
			java.util.Date claimReturnDate = null;

			Double claimForwdAmt = null;

			java.util.Date claimRejectedDt = null;

			java.util.Date tempRejOrRejDt = null;

			java.util.Date claimReplyRecvdDate = null;

			double claimAppliedAmt = 0.0;

			double claimRejAmount = 0.0;

			String clmQryRefNumber = "";

			java.util.Date clamQryDate = null;

			String clmDeclRecvdFlag = null;
			java.util.Date clmDeclRecvdDt = null;

			java.util.Date lastActionTakenDt = null;

			String claimStatus = "";

			String replyInwardId = "";

			java.util.Date replyInwardDt = null;

			java.util.Date tempClosedDate = null;

			java.util.Date tempRejectedDate = null;

			double claimApprovedAmt = 0.0;

			double totalClmApprovedAmt = 0.0;

			double totGrandClmApprovedAmtTemp = 0.0;

			double totGrandClmApprovedAmt = 0.0;

			String zoneName = null;
			String branchName = null;
			double guarApprdAmount = 0.0;

			double totalGuarnteAmt = 0.0;

			double totgrandTotal = 0.0;

			double totgrandTotalTemp = 0.0;

			double claimDecRecvdAmt = 0.0;
			String claimDecRecvdflag = null;
			java.util.Date claimDecRecvdDt = null;
			java.util.Date claimWithDrawnDt = null;
			String claimRemarks = null;
			String cgpan = null;
			java.util.Date clmApprovedDt = null;

			if (clmApplicationStatusFlag
					.equals(ClaimConstants.CLM_APPROVAL_STATUS)) {
				while (rs.next()) {

					memberId = (String) rs.getString(1);
					memberBankName = (String) rs.getString(2);
					zoneName = (String) rs.getString(3);
					branchName = (String) rs.getString(4);
					cgpan = (String) rs.getString(5);
					unitName = (String) rs.getString(6);
					guarApprdAmount = (Double) rs.getDouble(7);
					clmRefNumber = (String) rs.getString(8);
					cgclan = (String) rs.getString(9);
					clmLodgmentdDt = (Date) rs.getDate(10);
					claimApprovedAmt = (double) rs.getDouble(11);
					clmApprovedDt = (Date) rs.getDate(12);

					totalGuarnteAmt = totalGuarnteAmt + guarApprdAmount;
					totgrandTotalTemp = totalGuarnteAmt / 100000;
					totalClmApprovedAmt = totalClmApprovedAmt
							+ claimApprovedAmt;
					totGrandClmApprovedAmtTemp = totalClmApprovedAmt / 100000;
					DecimalFormat decFormat = new DecimalFormat("#.00");
					totGrandClmApprovedAmt = new Double(
							decFormat.format(totGrandClmApprovedAmtTemp))
							.doubleValue();
					totgrandTotal = new Double(
							decFormat.format(totgrandTotalTemp)).doubleValue();

					clmDtl = new ClaimDetail();

					clmDtl.setMliId(memberId);
					clmDtl.setMliName(memberBankName);
					clmDtl.setBranchName(branchName);
					clmDtl.setZoneName(zoneName);
					clmDtl.setCgpan(cgpan);
					clmDtl.setSsiUnitName(unitName);
					clmDtl.setApplicationApprovedAmount(guarApprdAmount);
					clmDtl.setClaimRefNum(clmRefNumber);
					clmDtl.setCGCLAN(cgclan);
					clmDtl.setClmSubmittedDt(clmLodgmentdDt);
					clmDtl.setClaimApprovedAmount(claimApprovedAmt);
					clmDtl.setClaimApprovedDt(clmApprovedDt);
					clmDtl.setTotgrandTotal(totgrandTotal);
					clmDtl.setTotGrandClmApprovedAmt(totGrandClmApprovedAmt);

					// Adding the ClaimDetail object to the vector
					clmRefNumbersList.addElement(clmDtl);
				}
				rs.close();
				rs = null;
			} else if

			(clmApplicationStatusFlag.equals(ClaimConstants.CLM_PENDING_STATUS)) {
				while (rs.next()) {

					memberId = (String) rs.getString(1);
					memberBankName = (String) rs.getString(2);
					zoneName = (String) rs.getString(3);
					branchName = (String) rs.getString(4);
					cgpan = (String) rs.getString(5);
					unitName = (String) rs.getString(6);
					guarApprdAmount = (Double) rs.getDouble(7);
					clmRefNumber = (String) rs.getString(8);
					clmLodgmentdDt = (Date) rs.getDate(9);
					claimAppliedAmt = (Double) rs.getDouble(10);
					clmDeclRecvdFlag = (String) rs.getString(11);
					clmDeclRecvdDt = (Date) rs.getDate(12);

					totalGuarnteAmt = totalGuarnteAmt + guarApprdAmount;
					totgrandTotalTemp = totalGuarnteAmt / 100000;
					totalClmApprovedAmt = totalClmApprovedAmt + claimAppliedAmt;
					totGrandClmApprovedAmtTemp = totalClmApprovedAmt / 100000;
					DecimalFormat decFormat = new DecimalFormat("#.00");
					totGrandClmApprovedAmt = new Double(
							decFormat.format(totGrandClmApprovedAmtTemp))
							.doubleValue();
					totgrandTotal = new Double(
							decFormat.format(totgrandTotalTemp)).doubleValue();

					clmDtl = new ClaimDetail();

					clmDtl.setMliId(memberId);
					clmDtl.setMliName(memberBankName);
					clmDtl.setBranchName(branchName);
					clmDtl.setZoneName(zoneName);
					clmDtl.setCgpan(cgpan);
					clmDtl.setSsiUnitName(unitName);
					clmDtl.setApplicationApprovedAmount(guarApprdAmount);
					clmDtl.setClaimRefNum(clmRefNumber);
					clmDtl.setClmSubmittedDt(clmLodgmentdDt);
					clmDtl.setClaimAppliedAmt(claimAppliedAmt);
					clmDtl.setClmDeclRecvdFlag(clmDeclRecvdFlag);
					clmDtl.setClmDeclRecvdDt(clmDeclRecvdDt);

					clmDtl.setTotgrandTotal(totgrandTotal);
					clmDtl.setTotGrandClmApprovedAmt(totGrandClmApprovedAmt);

					// Adding the ClaimDetail object to the vector
					clmRefNumbersList.addElement(clmDtl);
				}
				rs.close();
				rs = null;
			} else if

			(clmApplicationStatusFlag.equals(ClaimConstants.CLM_WITHDRAWN)) {
				while (rs.next()) {

					memberId = (String) rs.getString(1);
					memberBankName = (String) rs.getString(2);
					zoneName = (String) rs.getString(3);
					branchName = (String) rs.getString(4);
					cgpan = (String) rs.getString(5);
					unitName = (String) rs.getString(6);
					guarApprdAmount = (Double) rs.getDouble(7);
					clmRefNumber = (String) rs.getString(8);
					clmLodgmentdDt = (Date) rs.getDate(9);
					claimAppliedAmt = (Double) rs.getDouble(10);
					claimWithDrawnDt = (Date) rs.getDate(11);

					totalGuarnteAmt = totalGuarnteAmt + guarApprdAmount;
					totgrandTotalTemp = totalGuarnteAmt / 100000;
					totalClmApprovedAmt = totalClmApprovedAmt + claimAppliedAmt;
					totGrandClmApprovedAmtTemp = totalClmApprovedAmt / 100000;
					DecimalFormat decFormat = new DecimalFormat("#.00");
					totGrandClmApprovedAmt = new Double(
							decFormat.format(totGrandClmApprovedAmtTemp))
							.doubleValue();
					totgrandTotal = new Double(
							decFormat.format(totgrandTotalTemp)).doubleValue();

					clmDtl = new ClaimDetail();

					clmDtl.setMliId(memberId);
					clmDtl.setMliName(memberBankName);
					clmDtl.setBranchName(branchName);
					clmDtl.setZoneName(zoneName);
					clmDtl.setCgpan(cgpan);
					clmDtl.setSsiUnitName(unitName);
					clmDtl.setApplicationApprovedAmount(guarApprdAmount);
					clmDtl.setClaimRefNum(clmRefNumber);
					clmDtl.setClmSubmittedDt(clmLodgmentdDt);
					clmDtl.setClaimAppliedAmt(claimAppliedAmt);
					clmDtl.setClaimWithDrawnDate(claimWithDrawnDt);

					clmDtl.setTotgrandTotal(totgrandTotal);
					clmDtl.setTotGrandClmApprovedAmt(totGrandClmApprovedAmt);

					// clmDtl.setClaimLodgmentDate(clmLodgmentdDt);

					// Adding the ClaimDetail object to the vector
					clmRefNumbersList.addElement(clmDtl);

				}
				rs.close();
				rs = null;
			} else if

			(clmApplicationStatusFlag
					.equals(ClaimConstants.CLM_TEMPORARY_CLOSE)) {
				while (rs.next()) {

					memberId = (String) rs.getString(1);
					memberBankName = (String) rs.getString(2);
					zoneName = (String) rs.getString(3);
					branchName = (String) rs.getString(4);
					cgpan = (String) rs.getString(5);
					unitName = (String) rs.getString(6);
					guarApprdAmount = (Double) rs.getDouble(7);
					clmRefNumber = (String) rs.getString(8);
					clmLodgmentdDt = (Date) rs.getDate(9);
					claimAppliedAmt = (Double) rs.getDouble(10);
					tempClosedDate = (Date) rs.getDate(11);

					clmQryRefNumber = (String) rs.getString(12);

					clamQryDate = (Date) rs.getDate(13);

					totalGuarnteAmt = totalGuarnteAmt + guarApprdAmount;
					totgrandTotalTemp = totalGuarnteAmt / 100000;
					totalClmApprovedAmt = totalClmApprovedAmt + claimAppliedAmt;
					totGrandClmApprovedAmtTemp = totalClmApprovedAmt / 100000;
					DecimalFormat decFormat = new DecimalFormat("#.00");
					totGrandClmApprovedAmt = new Double(
							decFormat.format(totGrandClmApprovedAmtTemp))
							.doubleValue();
					totgrandTotal = new Double(
							decFormat.format(totgrandTotalTemp)).doubleValue();

					clmDtl = new ClaimDetail();

					clmDtl.setMliId(memberId);
					clmDtl.setMliName(memberBankName);
					clmDtl.setBranchName(branchName);
					clmDtl.setZoneName(zoneName);
					clmDtl.setCgpan(cgpan);
					clmDtl.setSsiUnitName(unitName);
					clmDtl.setApplicationApprovedAmount(guarApprdAmount);
					clmDtl.setClaimRefNum(clmRefNumber);
					clmDtl.setClmSubmittedDt(clmLodgmentdDt);
					clmDtl.setClaimAppliedAmt(claimAppliedAmt);
					clmDtl.setTempClosedDate(tempClosedDate);

					clmDtl.setClmQryRefNumber(clmQryRefNumber);
					clmDtl.setClamQryDate(clamQryDate);
					clmDtl.setTotgrandTotal(totgrandTotal);
					clmDtl.setTotGrandClmApprovedAmt(totGrandClmApprovedAmt);

					// Adding the ClaimDetail object to the vector
					clmRefNumbersList.addElement(clmDtl);

				}
				rs.close();
				rs = null;
			}

			else if

			(clmApplicationStatusFlag
					.equals(ClaimConstants.CLM_TEMPORARY_REJECT)) {
				while (rs.next()) {

					memberId = (String) rs.getString(1);
					memberBankName = (String) rs.getString(2);
					zoneName = (String) rs.getString(3);
					branchName = (String) rs.getString(4);
					cgpan = (String) rs.getString(5);
					unitName = (String) rs.getString(6);
					guarApprdAmount = (Double) rs.getDouble(7);
					clmRefNumber = (String) rs.getString(8);
					clmLodgmentdDt = (Date) rs.getDate(9);
					claimRejAmount = (Double) rs.getDouble(10);
					tempRejectedDate = (Date) rs.getDate(11);
					claimRetRemarks = rs.getString(12);
					totalGuarnteAmt = totalGuarnteAmt + guarApprdAmount;
					totgrandTotalTemp = totalGuarnteAmt / 100000;
					totalClmApprovedAmt = totalClmApprovedAmt + claimRejAmount;
					totGrandClmApprovedAmtTemp = totalClmApprovedAmt / 100000;
					DecimalFormat decFormat = new DecimalFormat("#.00");
					totGrandClmApprovedAmt = new Double(
							decFormat.format(totGrandClmApprovedAmtTemp))
							.doubleValue();
					totgrandTotal = new Double(
							decFormat.format(totgrandTotalTemp)).doubleValue();

					clmDtl = new ClaimDetail();

					clmDtl.setMliId(memberId);
					clmDtl.setMliName(memberBankName);
					clmDtl.setBranchName(branchName);
					clmDtl.setZoneName(zoneName);
					clmDtl.setCgpan(cgpan);
					clmDtl.setSsiUnitName(unitName);
					clmDtl.setApplicationApprovedAmount(guarApprdAmount);
					clmDtl.setClaimRefNum(clmRefNumber);
					clmDtl.setClmSubmittedDt(clmLodgmentdDt);
					clmDtl.setClaimAppliedAmt(claimRejAmount);
					clmDtl.setTempRejectedDate(tempRejectedDate);

					clmDtl.setClaimRetRemarks(claimRetRemarks);
					clmDtl.setTotgrandTotal(totgrandTotal);
					clmDtl.setTotGrandClmApprovedAmt(totGrandClmApprovedAmt);

					// clmDtl.setClaimLodgmentDate(clmLodgmentdDt);

					// Adding the ClaimDetail object to the vector
					clmRefNumbersList.addElement(clmDtl);

				}
				rs.close();
				rs = null;
			}

			else if

			(clmApplicationStatusFlag.equals(ClaimConstants.CLM_REPLY_RECEIVED)) {
				while (rs.next()) {

					memberId = (String) rs.getString(1);
					memberBankName = (String) rs.getString(2);
					zoneName = (String) rs.getString(3);
					branchName = (String) rs.getString(4);
					cgpan = (String) rs.getString(5);
					unitName = (String) rs.getString(6);
					guarApprdAmount = (Double) rs.getDouble(7);
					clmRefNumber = (String) rs.getString(8);
					clmLodgmentdDt = (Date) rs.getDate(9);
					claimApprovedAmt = (Double) rs.getDouble(10);
					claimReplyRecvdDate = (Date) rs.getDate(11);
					clmQryRefNumber = (String) rs.getString(12);
					clamQryDate = (Date) rs.getDate(13);
					replyInwardId = (String) rs.getString(14);
					replyInwardDt = (Date) rs.getDate(15);

					totalGuarnteAmt = totalGuarnteAmt + guarApprdAmount;
					totgrandTotalTemp = totalGuarnteAmt / 100000;
					totalClmApprovedAmt = totalClmApprovedAmt
							+ claimApprovedAmt;
					totGrandClmApprovedAmtTemp = totalClmApprovedAmt / 100000;
					DecimalFormat decFormat = new DecimalFormat("#.00");
					totGrandClmApprovedAmt = new Double(
							decFormat.format(totGrandClmApprovedAmtTemp))
							.doubleValue();
					totgrandTotal = new Double(
							decFormat.format(totgrandTotalTemp)).doubleValue();

					clmDtl = new ClaimDetail();

					clmDtl.setMliId(memberId);
					clmDtl.setMliName(memberBankName);
					clmDtl.setBranchName(branchName);
					clmDtl.setZoneName(zoneName);
					clmDtl.setCgpan(cgpan);
					clmDtl.setSsiUnitName(unitName);
					clmDtl.setApplicationApprovedAmount(guarApprdAmount);
					clmDtl.setClaimRefNum(clmRefNumber);
					clmDtl.setClmSubmittedDt(clmLodgmentdDt);
					clmDtl.setClaimApprovedAmount(claimApprovedAmt);
					clmDtl.setClaimReplyRecvdDate(claimReplyRecvdDate);
					clmDtl.setClmQryRefNumber(clmQryRefNumber);
					clmDtl.setClamQryDate(clamQryDate);

					clmDtl.setReplyInwardId(replyInwardId);
					clmDtl.setReplyInwardDt(replyInwardDt);
					clmDtl.setTotgrandTotal(totgrandTotal);
					clmDtl.setTotGrandClmApprovedAmt(totGrandClmApprovedAmt);

					// clmDtl.setClaimLodgmentDate(clmLodgmentdDt);

					// Adding the ClaimDetail object to the vector
					clmRefNumbersList.addElement(clmDtl);

				}
				rs.close();
				rs = null;
			}

			else if

			(clmApplicationStatusFlag.equals(ClaimConstants.CLM_REJECT_STATUS)) {
				while (rs.next()) {

					memberId = (String) rs.getString(1);
					memberBankName = (String) rs.getString(2);
					zoneName = (String) rs.getString(3);
					branchName = (String) rs.getString(4);
					cgpan = (String) rs.getString(5);
					unitName = (String) rs.getString(6);
					guarApprdAmount = (Double) rs.getDouble(7);
					clmRefNumber = (String) rs.getString(8);
					clmLodgmentdDt = (Date) rs.getDate(9);
					claimAppliedAmt = (Double) rs.getDouble(10);
					claimRejectedDt = (Date) rs.getDate(11);
					claimRetRemarks = rs.getString(12);

					totalGuarnteAmt = totalGuarnteAmt + guarApprdAmount;
					totgrandTotalTemp = totalGuarnteAmt / 100000;
					totalClmApprovedAmt = totalClmApprovedAmt + claimAppliedAmt;
					totGrandClmApprovedAmtTemp = totalClmApprovedAmt / 100000;
					DecimalFormat decFormat = new DecimalFormat("#.00");
					totGrandClmApprovedAmt = new Double(
							decFormat.format(totGrandClmApprovedAmtTemp))
							.doubleValue();
					totgrandTotal = new Double(
							decFormat.format(totgrandTotalTemp)).doubleValue();

					clmDtl = new ClaimDetail();

					clmDtl.setMliId(memberId);
					clmDtl.setMliName(memberBankName);
					clmDtl.setBranchName(branchName);
					clmDtl.setZoneName(zoneName);
					clmDtl.setCgpan(cgpan);
					clmDtl.setSsiUnitName(unitName);
					clmDtl.setApplicationApprovedAmount(guarApprdAmount);
					clmDtl.setClaimRefNum(clmRefNumber);
					clmDtl.setClmSubmittedDt(clmLodgmentdDt);
					clmDtl.setClaimAppliedAmt(claimAppliedAmt);
					clmDtl.setClaimRejectedDt(claimRejectedDt);
					clmDtl.setClaimRetRemarks(claimRetRemarks);
					clmDtl.setTotgrandTotal(totgrandTotal);
					clmDtl.setTotGrandClmApprovedAmt(totGrandClmApprovedAmt);

					// Adding the ClaimDetail object to the vector
					clmRefNumbersList.addElement(clmDtl);

				}
				rs.close();
				rs = null;
			}

			else if

			(clmApplicationStatusFlag.equals("RT")) {
				while (rs.next()) {

					memberId = (String) rs.getString(1);
					memberBankName = (String) rs.getString(2);
					zoneName = (String) rs.getString(3);
					branchName = (String) rs.getString(4);
					cgpan = (String) rs.getString(5);
					unitName = (String) rs.getString(6);
					guarApprdAmount = (Double) rs.getDouble(7);
					clmRefNumber = (String) rs.getString(8);
					claimAppliedAmt = (Double) rs.getDouble(9);
					claimReturnDate = (Date) rs.getDate(10);
					claimRetRemarks = (String) rs.getString(11);

					totalGuarnteAmt = totalGuarnteAmt + guarApprdAmount;
					totgrandTotalTemp = totalGuarnteAmt / 100000;
					totalClmApprovedAmt = totalClmApprovedAmt + claimAppliedAmt;
					totGrandClmApprovedAmtTemp = totalClmApprovedAmt / 100000;
					DecimalFormat decFormat = new DecimalFormat("#.00");
					totGrandClmApprovedAmt = new Double(
							decFormat.format(totGrandClmApprovedAmtTemp))
							.doubleValue();
					totgrandTotal = new Double(
							decFormat.format(totgrandTotalTemp)).doubleValue();

					clmDtl = new ClaimDetail();

					clmDtl.setMliId(memberId);
					clmDtl.setMliName(memberBankName);
					clmDtl.setBranchName(branchName);
					clmDtl.setZoneName(zoneName);
					clmDtl.setCgpan(cgpan);
					clmDtl.setSsiUnitName(unitName);
					clmDtl.setApplicationApprovedAmount(guarApprdAmount);
					clmDtl.setClaimRefNum(clmRefNumber);
					clmDtl.setClaimAppliedAmt(claimAppliedAmt);
					clmDtl.setClaimReturnDate(claimReturnDate);
					clmDtl.setClaimRetRemarks(claimRetRemarks);
					clmDtl.setTotgrandTotal(totgrandTotal);
					clmDtl.setTotGrandClmApprovedAmt(totGrandClmApprovedAmt);

					clmRefNumbersList.addElement(clmDtl);
				}
				rs.close();
				rs = null;
			}

			else if

			(clmApplicationStatusFlag.equals("RTD")) {
				while (rs.next()) {

					memberId = (String) rs.getString(1);
					memberBankName = (String) rs.getString(2);
					zoneName = (String) rs.getString(3);
					branchName = (String) rs.getString(4);
					cgpan = (String) rs.getString(5);
					unitName = (String) rs.getString(6);
					guarApprdAmount = (Double) rs.getDouble(7);
					clmRefNumber = (String) rs.getString(8);
					clmLodgmentdDt = (Date) rs.getDate(9);
					claimAppliedAmt = (Double) rs.getDouble(10);
					tempRejOrRejDt = (Date) rs.getDate(11);

					totalGuarnteAmt = totalGuarnteAmt + guarApprdAmount;
					totgrandTotalTemp = totalGuarnteAmt / 100000;
					totalClmApprovedAmt = totalClmApprovedAmt + claimAppliedAmt;
					totGrandClmApprovedAmtTemp = totalClmApprovedAmt / 100000;
					DecimalFormat decFormat = new DecimalFormat("#.00");
					totGrandClmApprovedAmt = new Double(
							decFormat.format(totGrandClmApprovedAmtTemp))
							.doubleValue();
					totgrandTotal = new Double(
							decFormat.format(totgrandTotalTemp)).doubleValue();

					clmDtl = new ClaimDetail();

					clmDtl.setMliId(memberId);
					clmDtl.setMliName(memberBankName);
					clmDtl.setBranchName(branchName);
					clmDtl.setZoneName(zoneName);
					clmDtl.setCgpan(cgpan);
					clmDtl.setSsiUnitName(unitName);
					clmDtl.setApplicationApprovedAmount(guarApprdAmount);
					clmDtl.setClaimRefNum(clmRefNumber);
					clmDtl.setClmSubmittedDt(clmLodgmentdDt);
					clmDtl.setClaimAppliedAmt(claimAppliedAmt);

					clmDtl.setTempRejOrRejDt(tempRejOrRejDt);
					clmDtl.setTotgrandTotal(totgrandTotal);
					clmDtl.setTotGrandClmApprovedAmt(totGrandClmApprovedAmt);

					clmRefNumbersList.addElement(clmDtl);
				}
				rs.close();
				rs = null;
			}

			else if

			(clmApplicationStatusFlag.equals("AS")) {
				while (rs.next()) {

					memberId = (String) rs.getString(1);
					memberBankName = (String) rs.getString(2);
					zoneName = (String) rs.getString(3);
					branchName = (String) rs.getString(4);
					cgpan = (String) rs.getString(5);
					unitName = (String) rs.getString(6);
					guarApprdAmount = (Double) rs.getDouble(7);
					clmRefNumber = (String) rs.getString(8);
					claimAppliedAmt = (Double) rs.getDouble(9);
					clmLodgmentdDt = (Date) rs.getDate(10);
					claimStatus = (String) rs.getString(11);

					clmDeclRecvdDt = (Date) rs.getDate(12);

					lastActionTakenDt = (Date) rs.getDate(13);

					totalGuarnteAmt = totalGuarnteAmt + guarApprdAmount;
					totgrandTotalTemp = totalGuarnteAmt / 100000;
					totalClmApprovedAmt = totalClmApprovedAmt + claimAppliedAmt;
					totGrandClmApprovedAmtTemp = totalClmApprovedAmt / 100000;
					DecimalFormat decFormat = new DecimalFormat("#.00");
					totGrandClmApprovedAmt = new Double(
							decFormat.format(totGrandClmApprovedAmtTemp))
							.doubleValue();
					totgrandTotal = new Double(
							decFormat.format(totgrandTotalTemp)).doubleValue();

					clmDtl = new ClaimDetail();

					clmDtl.setMliId(memberId);
					clmDtl.setMliName(memberBankName);
					clmDtl.setBranchName(branchName);
					clmDtl.setZoneName(zoneName);
					clmDtl.setCgpan(cgpan);
					clmDtl.setSsiUnitName(unitName);
					clmDtl.setApplicationApprovedAmount(guarApprdAmount);
					clmDtl.setClaimRefNum(clmRefNumber);
					clmDtl.setClaimAppliedAmt(claimAppliedAmt);
					clmDtl.setClmSubmittedDt(clmLodgmentdDt);
					clmDtl.setClmStatus(claimStatus);
					clmDtl.setClmDeclRecvdDt(clmDeclRecvdDt);

					clmDtl.setLastActionTakenDt(lastActionTakenDt);

					clmDtl.setTotgrandTotal(totgrandTotal);
					clmDtl.setTotGrandClmApprovedAmt(totGrandClmApprovedAmt);

					clmRefNumbersList.addElement(clmDtl);
				}
				rs.close();
				rs = null;
			}

			else if

			(clmApplicationStatusFlag.equals(ClaimConstants.CLM_FORWARD_STATUS)) {
				while (rs.next()) {

					memberId = (String) rs.getString(1);
					memberBankName = (String) rs.getString(2);
					zoneName = (String) rs.getString(3);
					branchName = (String) rs.getString(4);
					cgpan = (String) rs.getString(5);
					unitName = (String) rs.getString(6);
					guarApprdAmount = (Double) rs.getDouble(7);
					clmRefNumber = (String) rs.getString(8);
					clmLodgmentdDt = (Date) rs.getDate(9);
					claimForwdAmt = (double) rs.getDouble(10);
					claimForwardedDt = (Date) rs.getDate(11);

					totalGuarnteAmt = totalGuarnteAmt + guarApprdAmount;
					totgrandTotalTemp = totalGuarnteAmt / 100000;
					totalClmApprovedAmt = totalClmApprovedAmt + claimForwdAmt;
					totGrandClmApprovedAmtTemp = totalClmApprovedAmt / 100000;
					DecimalFormat decFormat = new DecimalFormat("#.00");
					totGrandClmApprovedAmt = new Double(
							decFormat.format(totGrandClmApprovedAmtTemp))
							.doubleValue();
					totgrandTotal = new Double(
							decFormat.format(totgrandTotalTemp)).doubleValue();

					clmDtl = new ClaimDetail();

					clmDtl.setMliId(memberId);
					clmDtl.setMliName(memberBankName);
					clmDtl.setBranchName(branchName);
					clmDtl.setZoneName(zoneName);
					clmDtl.setCgpan(cgpan);
					clmDtl.setSsiUnitName(unitName);
					clmDtl.setApplicationApprovedAmount(guarApprdAmount);
					clmDtl.setClaimRefNum(clmRefNumber);
					clmDtl.setClmSubmittedDt(clmLodgmentdDt);
					clmDtl.setClaimForwdAmt(claimForwdAmt);
					clmDtl.setClaimForwardedDt(claimForwardedDt);
					clmDtl.setTotgrandTotal(totgrandTotal);
					clmDtl.setTotGrandClmApprovedAmt(totGrandClmApprovedAmt);

					// Adding the ClaimDetail object to the vector
					clmRefNumbersList.addElement(clmDtl);
				}
				rs.close();
				rs = null;
			}

			else {
				while (rs.next()) {
					memberBankName = (String) rs.getString(1);
					memberId = (String) rs.getString(2);
					clmRefNumber = (String) rs.getString(3);
					unitName = (String) rs.getString(4);
					submittedDt = (Date) rs.getDate(5);

					zoneName = (String) rs.getString(6);
					branchName = (String) rs.getString(7);
					guarApprdAmount = (Double) rs.getDouble(8);

					clmDtl = new ClaimDetail();
					clmDtl.setMliName(memberBankName);
					clmDtl.setMliId(memberId);
					clmDtl.setClaimRefNum(clmRefNumber);
					clmDtl.setCGCLAN(cgclan);
					clmDtl.setSsiUnitName(unitName);
					clmDtl.setClmSubmittedDt(submittedDt);

					clmDtl.setZoneName(zoneName);
					clmDtl.setBranchName(branchName);
					clmDtl.setApplicationApprovedAmount(guarApprdAmount);

					// Adding the ClaimDetail object to the vector
					clmRefNumbersList.addElement(clmDtl);
				}
				rs.close();
				rs = null;
			}
			prepStatement.close();
			prepStatement = null;
		} catch (SQLException sqlexception) {
			// sqlexception.printStackTrace();
			throw new DatabaseException(sqlexception.getMessage());
		} finally {
			DBConnection.freeConnection(conn);
		}
		return clmRefNumbersList;
	}
	
	
	//Diksha Bulk upload Report
	

				public ActionForward clmSettledExcelReportNew(ActionMapping mapping,
						ActionForm form, HttpServletRequest request,
						HttpServletResponse response) throws Exception {
					
					
					
					ReportActionForm dynaForm = (ReportActionForm) form;
					HttpSession session = request.getSession(false);
					Log.log(4, "NewReportsAction", "Claim settled", "Entered");
					HttpSession sess = request.getSession();
					User user = getUserInformation(request);
					//System.out.println("== NEW REPORT=====19121====");
					
					
					
					Date fromDt = (Date) dynaForm.getDateOfTheDocument26();
					Date toDt = (Date) dynaForm.getDateOfTheDocument27();
					java.sql.Date sqlfromdate = new java.sql.Date(fromDt.getTime());
					java.sql.Date sqltodate = new java.sql.Date(toDt.getTime());
					//String memberid=dynaForm.getMemberId();
					//String memberid = request.getParameter("memberId");
					//String memberid = (String) session.getAttribute("memberId");
					
					String memberid = (String) dynaForm.getMemberId();
					//System.out.println("memberid=="+memberid);
					String bankId = user.getBankId();
					//String application.setBankId(bankId);
					String zoneId = user.getZoneId();
					//application.setZoneId(zoneId);
					String branchId = user.getBranchId();
					if(memberid.equals(null)||memberid.equals(""))
					{
						 memberid = request.getParameter("memberId");
						 //System.out.println("memberid==inner=="+memberid);
						 if (!memberid.equals(null)|| !memberid.equals(""))
						 {
							 
						String bankId1 = memberid.substring(0, 4);
						String	zoneId1 = memberid.substring(4, 8);
						String	branchId1 = memberid.substring(8, 12);
						if(!bankId.equals("0000"))
						{
						if(!bankId.equals(bankId1))
						{
							throw new MessageException("Member id does not belongs to your Bank.");
						}
						 }
						 }
						 else {
							 throw new MessageException("Member id is required.");
						 }
					}
					
					
					 //System.out.println("memberid==inner=="+memberid);
					//String cgpan=dynaForm.getCgpan();
					//System.out.println("toDt=="+toDt);
					//System.out.println("fromDt=="+fromDt);
					//String type = request.getParameter("checkValue");
					String itPanValue = request.getParameter("itPanVal");
					String unitNameValue = request.getParameter("unitNameVal");
					String appRefNoValue = request.getParameter("appRefNoVal");
					String CGPANValue = request.getParameter("CGPANVal");
					String loanAccValue = request.getParameter("loanAccVal");
					
					//String type=dynaForm.getCheckValue();
					//System.out.println("itPanValue=="+itPanValue);
					//System.out.println("unitNameValue=="+unitNameValue);
					Connection connection = null;
					Statement stmt = null;
					ResultSet rs = null;
					String query = null;
					List clmSettledExcelReport = new ArrayList();
					String flag=request.getParameter("Flag");
					System.out.println("Flag---"+flag);
					ClaimsProcessor claimsProcessor = new ClaimsProcessor();
					Vector memberids = new Vector();
					memberids = claimsProcessor.getAllMemberIds();
					String fileType = request.getParameter("fileType");
					
					//System.out.println("fileType----"+fileType);
					String FlowLevel = request.getParameter("FlowLevel");
					//String d=(String)request.getAttribute("FileReport");
					//System.out.println("d--"+d);
					System.out.println("@@@@@@@@@@@@FlowLevel :" + FlowLevel);
					
					if (memberid.length() == 12 && !memberids.contains(memberid)) {
						Log.log(2, "CPDAO", "getAllMemberIds()",
								"No Member Ids in the database!");
						throw new DatabaseException("No Member Ids in the database");
					}
					
					
					if (sqlfromdate == null && sqltodate == null) 
					{
							throw new MessageException("Please select Date.");
					}
					if(flag!=null && flag.equals("A"))
							{
							clmSettledExcelReport = claimsettledReportDetail(connection, memberid, itPanValue,unitNameValue,appRefNoValue,CGPANValue,loanAccValue,	sqlfromdate, sqltodate);
							System.out.println("FileReport LIST----"+clmSettledExcelReport.size());
							sess.setAttribute("ClmSettledExcelReport", clmSettledExcelReport);
							//RPAction r=new RPAction();
							//r.bulkUploadReportFile(mapping, dynaForm, request, response);
							this.clmSettledExportToFile(mapping, dynaForm, request, response);
							if(clmSettledExcelReport==null||clmSettledExcelReport.equals("")){
								throw new MessageException("Data not available");
							}
							else{
								
							dynaForm.setColletralCoulmnName((ArrayList) clmSettledExcelReport.get(0));
							dynaForm.setColletralCoulmnValue((ArrayList) clmSettledExcelReport.get(1));
							}	
						}
					
					return mapping.findForward("success");
				}
	
				
				private List claimsettledReportDetail(Connection conn, String memberID,
						String itPanValue,String unitNameValue,String appRefNoValue,
						String CGPANValue,String loanAccValue,
						java.sql.Date sqlfromdate, java.sql.Date sqltodate)
						throws DatabaseException {
					Log.log(Log.INFO, "reportaction", "actionHistoryDetailReport()", "Entered!");
					CallableStatement callableStmt = null;
					// Connection conn = null;
					ResultSet resultset = null;
					ResultSetMetaData resultSetMetaData = null;
					ArrayList coulmName = new ArrayList();
					ArrayList nestData = new ArrayList();
					ArrayList claimdata = new ArrayList();
					int status = -1;
					String errorCode = null;
					try {
						conn = DBConnection.getConnection();
						callableStmt = conn
								.prepareCall("{call   FUN_CLAIM_SETTLED_REPORT(?,?,?,?,?,?,?,?,?,?)}");
						callableStmt.registerOutParameter(1, java.sql.Types.INTEGER);

						callableStmt.setDate(2, sqlfromdate);
						callableStmt.setDate(3, sqltodate);
						callableStmt.setString(4, memberID);
						//callableStmt.setString(5, cgpan);
						callableStmt.setString(5, unitNameValue);
						callableStmt.setString(6, appRefNoValue);
						callableStmt.setString(7, CGPANValue);
						callableStmt.setString(8, itPanValue);
						callableStmt.setString(9, loanAccValue);
						
						callableStmt.registerOutParameter(10, java.sql.Types.VARCHAR);

						callableStmt.execute();
						status = callableStmt.getInt(1);
						errorCode = callableStmt.getString(10);

						if (status == Constants.FUNCTION_FAILURE) {
							Log.log(Log.ERROR, "CPDAO", "colletralHybridRetailReport()",
									"SP returns a 1. Error code is :" + errorCode);
							callableStmt.close();
							throw new DatabaseException(errorCode);
						} else if (status == Constants.FUNCTION_SUCCESS) {
							resultset =  callableStmt.executeQuery();
							resultSetMetaData = resultset.getMetaData();
							int coulmnCount = resultSetMetaData.getColumnCount();
							for (int i = 1; i <= coulmnCount; i++) {
								coulmName.add(resultSetMetaData.getColumnName(i));
							}

							while (resultset.next()) {

								ArrayList columnValue = new ArrayList();
								for (int i = 1; i <= coulmnCount; i++) {
									columnValue.add(resultset.getString(i));
								}

								nestData.add(columnValue);

							}
							System.out.println("list data " + nestData);
							claimdata.add(0, coulmName);
							claimdata.add(1, nestData);
						}
						resultset.close();
						resultset = null;
						callableStmt.close();
						callableStmt = null;
						resultSetMetaData = null;
					} catch (SQLException sqlexception) {
						Log.log(Log.ERROR, "CPDAO", "colletralHybridRetailReport()",
								"Error retrieving all colletral data!");
						throw new DatabaseException(sqlexception.getMessage());
					} finally {
						DBConnection.freeConnection(conn);
					}
					return claimdata;
				}
				
				
				
				public ActionForward clmSettledExportToFile(ActionMapping mapping, ActionForm form,
						HttpServletRequest request, HttpServletResponse response)
						throws Exception {
					OutputStream os = response.getOutputStream();

					Calendar cal = Calendar.getInstance();
					SimpleDateFormat sdf = new SimpleDateFormat("ddMMyyyyHHmmss");
					String strDate = sdf.format(cal.getTime());

					// System.out.println("ExportToFile Calling..");

					String contextPath1 = request.getSession(false).getServletContext().getRealPath("");
					String contextPath = PropertyLoader.changeToOSpath(contextPath1);

					// System.out.println("contextPath1 :"+contextPath1);
					// System.out.println("contextPath :"+contextPath);

					HttpSession sess = request.getSession(false);
					String fileType = request.getParameter("fileType");
					String FlowLevel = request.getParameter("FlowLevel");

					//System.out.println("@@@@@@@@@@@@FlowLevel :" + FlowLevel);
					// ArrayList ClmDataList =
					// (ArrayList)sess.getAttribute("ClaimSettledDatalist");
					ArrayList ClmDataList = (ArrayList) sess.getAttribute(FlowLevel);
					System.out.println("@@@@@@@@@@@@ClmDataList:" + ClmDataList);
					ArrayList HeaderArrLst = (ArrayList) ClmDataList.get(0);
					//System.out.println("@@@@@@@@@@@@HeaderArrLst:" + HeaderArrLst);
					int NoColumn = HeaderArrLst.size();

					// System.out.println("fileType:"+fileType);

					if (fileType.equals("CSVType")) {
						byte[] b = generateCSV(ClmDataList, NoColumn, contextPath);

						if (response != null)
							response.setContentType("APPLICATION/OCTET-STREAM");
						response.setHeader("Content-Disposition",
								"attachment; filename=ClmSettledExcelData" + strDate
										+ ".csv");
						os.write(b);
						os.flush();
					}
					if(fileType.equals("XLSType")){
						System.out.println("XLS====File==");
						byte[] b = generateEXL(ClmDataList,NoColumn,contextPath);			
						if (response != null)
						    response.setContentType("APPLICATION/OCTET-STREAM");
						    response.setHeader("Content-Disposition","attachment; filename=ExcelData"+strDate+".xls");
				            os.write(b);
				            os.flush();			
					}
					return null;
				}

				public byte[] generateCSV(ArrayList<ArrayList> ParamDataList,
						int No_Column, String contextPath) throws IOException {

					System.out.println("---generateCSV()---");
					StringBuffer strbuff = new StringBuffer();
					//System.out.println("ParamDataList:" + ParamDataList);
					//System.out.println("contextPath :" + contextPath);
					ArrayList<String> rowDataLst = new ArrayList<String>();
					ArrayList<String> HeaderLst = (ArrayList) ParamDataList.get(0);
					ArrayList<ArrayList> RecordWiseLst = (ArrayList) ParamDataList.get(1);
					//System.out.println("HeaderLst" + HeaderLst);
					//System.out.println("RecordWiseLst" + RecordWiseLst);
					// #### Header List
					for (String headerdata : HeaderLst) {
						rowDataLst.add(headerdata);
						//System.out.println("Loop--headerdata:" + headerdata);
					}
					//System.out.println("rowDataLst:" + rowDataLst);
					// #### Header List

					// #### Data List
					for (ArrayList<String> RecordWiseLstObj : RecordWiseLst) {
						//System.out.println("RecordWiseLstObj:" + RecordWiseLstObj);
						for (String SingleRecordDataObj : RecordWiseLstObj) {
							//System.out.println("DataLstInnerObj :" + SingleRecordDataObj);
							if (null != SingleRecordDataObj) {
								// rowDataLst.add(SingleRecordDataObj.replace("<b>","").replace("</b>",""));
								rowDataLst.add(SingleRecordDataObj.replace("<b>", "")
										.replace("</b>", ""));
							} else {
								rowDataLst.add(SingleRecordDataObj);
							}
						}
						// System.out.println("DataLstObj :"+DataLstObj);
					}
					//System.out.println("rowDataLst::" + rowDataLst);
					// #### Data List

					ArrayList FinalrowDatalist = new ArrayList<String>();
					//System.out.println("1");
					int y = 0;
					// System.out.println("2"+No_Column);
					for (int n = 0; n < rowDataLst.size(); n++) {

						if (n % No_Column == 0 && n != 0) {
							FinalrowDatalist.add(rowDataLst.get(n));
							FinalrowDatalist.add(n + y, "\n");
							// System.out.println("2n value inside if:"+n);
						//	System.out.println("n:" + n);
							y++;
						} else {
							// System.out.println("2n inside else:"+n);
							if (null != rowDataLst.get(n)) {
								if (rowDataLst.get(n).contains(",")) {
									rowDataLst.set(n, rowDataLst.get(n).replace(",", ";"));
								}
							}
							FinalrowDatalist.add(rowDataLst.get(n));
						}
						// System.out.println("rowDataLst.get "+rowDataLst.get(n)+"    "+n%3);
					}
					// System.out.println("rowDataLst :"+rowDataLst.toString().replace("\n,","\n"));
					// String tempStr = rowDataLst.toString().replace("\n,", "\n");
					//System.out.println("3");

					String tempStr = FinalrowDatalist.toString().replace("\n,", "\n").replace(" ,", ",").replace(", ", ",");
					// String tempStr = FinalrowDatalist.toString().replace("\n,", "\n");

					//System.out.println("4");
					// strbuff.append(ParamDataList.toString().substring(2,
					// ParamDataList.toString().length() - 2).replace("endrow,", "\n"));
					strbuff.append(tempStr.substring(1, tempStr.length() - 1));
					// System.out.println("strbuff :"+strbuff);
					///System.out.println("5");
					Calendar cal = Calendar.getInstance();
					SimpleDateFormat sdf = new SimpleDateFormat("ddMMyyyyHHmmss");
					String strDate = sdf.format(cal.getTime());
					BufferedWriter output = null;
					OutputStream outStrm;
					// File genfile = new File("D:\\GenerateFiles\\SampleFile" + strDate+
					// ".csv");
					File genfile = new File(contextPath + "\\Download\\DataCSVFile"
							+ strDate + ".csv");

					//System.out.println("6");
					output = new BufferedWriter(new FileWriter(genfile));
					output.write(strbuff.toString());
					//System.out.println("7");
					output.flush();
					output.close();
					//System.out.println("8");

					// ##
					// FileInputStream fis = new
					// FileInputStream("D:\\GenerateFiles\\SampleFile" + strDate+ ".csv");
					FileInputStream fis = new FileInputStream(contextPath
							+ "\\Download\\DataCSVFile" + strDate + ".csv");

					//System.out.println("9");
					byte b[];
					int x = fis.available();
					b = new byte[x];
					// System.out.println(" b size"+b.length);

					fis.read(b);
					// ##
					return b;
					// genfile.setReadOnly();
				}
				
				
				public byte[] generateEXL(ArrayList<ArrayList> ParamDataList,int No_Column, String contextPath) throws DocumentException,IOException {
							System.out.println("---generateEXL()---");
							StringBuffer strbuff = new StringBuffer();
							System.out.println("ParamDataList:" + ParamDataList);
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
									cell.setCellValue(SingleRecordDataObj);
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
								FileOutputStream out = new FileOutputStream(new File(contextPath+ "\\Download\\DataCSVFile" + strDate + ".xls"));
								workbook.write(out);
								out.close();
								//System.out.println("Excel written successfully..");
							} catch (FileNotFoundException e) {
								e.printStackTrace();
							} catch (IOException e) {
								e.printStackTrace();
							}		
							FileInputStream fis = new FileInputStream(contextPath+ "\\Download\\DataCSVFile" + strDate + ".xls");
							//System.out.println("9");
							byte b[];
							int x = fis.available();
							b = new byte[x];
							// System.out.println(" b size"+b.length);
							fis.read(b);		
							return b;
			}
				//End	
				
				
				public ActionForward downLoadDataInExcel(ActionMapping mapping,
						ActionForm form, HttpServletRequest request,
						HttpServletResponse response) throws Exception {
					
					
					
					ReportActionForm dynaForm = (ReportActionForm) form;
					HttpSession session = request.getSession(false);
					Log.log(4, "NewReportsAction", "Claim settled", "Entered");
					HttpSession sess = request.getSession();
					User user =getUserInformation(request);
					System.out.println("== NEW REPORT=====19121====");
					String uploadId = request.getParameter("uploadId");
					String procesName = request.getParameter("procesName");
					
			      //String UploadId= (String)request.getAttribute("uploadId");
				  System.out.println("UploadId on report action===="+uploadId);
					//String memberid=dynaForm.getMemberId();
					//String memberid = request.getParameter("memberId");
					//String memberid = (String) session.getAttribute("memberId");
					
					//String memberid = (String) dynaForm.getMemberId();
					//System.out.println("memberid=="+memberid);
					String bankId = user.getBankId();
					//String application.setBankId(bankId);
					String zoneId = user.getZoneId();
					//application.setZoneId(zoneId);
					String branchId = user.getBranchId();
					String memberid=bankId+zoneId+branchId;
					if(memberid.equals(null)||memberid.equals(""))
					{
						// memberid = request.getParameter("memberId");
						 System.out.println("memberid==inner=="+memberid);
						 if (!memberid.equals(null)|| !memberid.equals(""))
						 {
							 
						String bankId1 = memberid.substring(0, 4);
						String	zoneId1 = memberid.substring(4, 8);
						String	branchId1 = memberid.substring(8, 12);
						if(!bankId.equals("0000"))
						{
						if(!bankId.equals(bankId1))
						{
							throw new MessageException("Member id does not belongs to your Bank.");
						}
						 }
						 }
						 else {
							 throw new MessageException("Member id is required.");
						 }
					}
					
					
					 System.out.println("memberid==inner=="+memberid);
					//String cgpan=dynaForm.getCgpan();
					
					//String type = request.getParameter("checkValue");
					
					
					//String type=dynaForm.getCheckValue();
				
					Connection connection = null;
					Statement stmt = null;
					ResultSet rs = null;
					String query = null;
					List clmSettledExcelReport = new ArrayList();
					String flag=request.getParameter("Flag");
					System.out.println("Flag---"+flag);
					ClaimsProcessor claimsProcessor = new ClaimsProcessor();
					//Vector memberids = new Vector();
					//memberids = claimsProcessor.getAllMemberIds();
					String fileType = request.getParameter("fileType");
					
					System.out.println("fileType----"+fileType);
					String FlowLevel = request.getParameter("FlowLevel");
					//String d=(String)request.getAttribute("FileReport");
					//System.out.println("d--"+d);
					System.out.println("@@@@@@@@@@@@FlowLevel :" + FlowLevel);
					
		/*
		 * ?if (memberid.length() == 12 && !memberids.contains(memberid)) { Log.log(2,
		 * "CPDAO", "getAllMemberIds()", "No Member Ids in the database!"); throw new
		 * DatabaseException("No Member Ids in the database"); }
		 */
					System.out.println("UploadId=="+uploadId);
					
					
					if(flag!=null && flag.equals("A"))
							{
							clmSettledExcelReport = uploadIdDetails(connection, uploadId,procesName,user.getUserId());
							System.out.println("FileReport LIST----"+clmSettledExcelReport.size());
							sess.setAttribute("ClmSettledExcelReport", clmSettledExcelReport);
							//RPAction r=new RPAction();
							//r.bulkUploadReportFile(mapping, dynaForm, request, response);
							this.uploadIdDetailsFile(mapping, dynaForm, request, response);
							if(clmSettledExcelReport==null||clmSettledExcelReport.equals("")){
								throw new MessageException("Data not available");
							}
							else{
								
							dynaForm.setColletralCoulmnName((ArrayList) clmSettledExcelReport.get(0));
							dynaForm.setColletralCoulmnValue((ArrayList) clmSettledExcelReport.get(1));
							}	
						}
					
					return mapping.findForward("success");
				}
	
				
				private List uploadIdDetails(Connection conn, String UploadId,String processName,String userId)
						throws DatabaseException {
					Log.log(Log.INFO, "reportaction", "actionHistoryDetailReport()", "Entered!");
					CallableStatement callableStmt = null;
					// Connection conn = null;
					ResultSet resultset = null;
					ResultSetMetaData resultSetMetaData = null;
					ArrayList coulmName = new ArrayList();
					ArrayList nestData = new ArrayList();
					ArrayList claimdata = new ArrayList();
					int status = -1;
					String errorCode = null;
					
					System.out.println("UploadId=="+UploadId);
					
					
					try {
						conn = DBConnection.getConnection();
						callableStmt = conn
								.prepareCall("{call   FUNC_GET_UPLOADED_DATA_2D(?,?,?,?,?)}");
						callableStmt.registerOutParameter(1, java.sql.Types.INTEGER);
						callableStmt.setString(2, userId);
						callableStmt.setString(3, UploadId);
						callableStmt.setString(4, processName);

						callableStmt.registerOutParameter(5, java.sql.Types.VARCHAR);
						callableStmt.execute();
			     		status = callableStmt.getInt(1);
						errorCode = callableStmt.getString(5);
						

						if (status == Constants.FUNCTION_FAILURE) {
							Log.log(Log.ERROR, "CPDAO", "colletralHybridRetailReport()",
									"SP returns a 1. Error code is :" + errorCode);
							callableStmt.close();
							throw new DatabaseException(errorCode);
						} else if (status == Constants.FUNCTION_SUCCESS) {
							resultset =  callableStmt.executeQuery();
							resultSetMetaData = resultset.getMetaData();
							int coulmnCount = resultSetMetaData.getColumnCount();
							for (int i = 1; i <= coulmnCount; i++) {
								coulmName.add(resultSetMetaData.getColumnName(i));
							}

							while (resultset.next()) {

								ArrayList columnValue = new ArrayList();
								for (int i = 1; i <= coulmnCount; i++) {
									columnValue.add(resultset.getString(i));
								}

								nestData.add(columnValue);

							}
							System.out.println("list data " + nestData);
							claimdata.add(0, coulmName);
							claimdata.add(1, nestData);
						}
						resultset.close();
						resultset = null;
						callableStmt.close();
						callableStmt = null;
						resultSetMetaData = null;
					} catch (SQLException sqlexception) {
						Log.log(Log.ERROR, "CPDAO", "colletralHybridRetailReport()",
								"Error retrieving all colletral data!");
						throw new DatabaseException(sqlexception.getMessage());
					} finally {
						DBConnection.freeConnection(conn);
					}
					return claimdata;
						
				}		
				
				
				
				public ActionForward uploadIdDetailsFile(ActionMapping mapping, ActionForm form,
						HttpServletRequest request, HttpServletResponse response)
						throws Exception {
					OutputStream os = response.getOutputStream();

					Calendar cal = Calendar.getInstance();
					SimpleDateFormat sdf = new SimpleDateFormat("ddMMyyyyHHmmss");
					String strDate = sdf.format(cal.getTime());

					// System.out.println("ExportToFile Calling..");

					String contextPath1 = request.getSession(false).getServletContext().getRealPath("");
					String contextPath = PropertyLoader.changeToOSpath(contextPath1);

					// System.out.println("contextPath1 :"+contextPath1);
					// System.out.println("contextPath :"+contextPath);

					HttpSession sess = request.getSession(false);
					String fileType = request.getParameter("fileType");
					String FlowLevel = request.getParameter("FlowLevel");

					System.out.println("@@@@@@@@@@@@FlowLevel :" + FlowLevel);
					// ArrayList ClmDataList =
					// (ArrayList)sess.getAttribute("ClaimSettledDatalist");
					ArrayList ClmDataList = (ArrayList) sess.getAttribute(FlowLevel);
					System.out.println("@@@@@@@@@@@@ClmDataList:" + ClmDataList);
					ArrayList HeaderArrLst = (ArrayList) ClmDataList.get(0);
					System.out.println("@@@@@@@@@@@@HeaderArrLst:" + HeaderArrLst);
					int NoColumn = HeaderArrLst.size();

					// System.out.println("fileType:"+fileType);

					if (fileType.equals("CSVType")) {
						byte[] b = generateCSVType(ClmDataList, NoColumn, contextPath);

						if (response != null)
							response.setContentType("APPLICATION/OCTET-STREAM");
						response.setHeader("Content-Disposition",
								"attachment; filename=ClmSettledExcelData" + strDate
										+ ".csv");
						os.write(b);
						os.flush();
					}
					if(fileType.equals("XLSType")){
						System.out.println("XLS====File==");
						byte[] b = generateEXLType(ClmDataList,NoColumn,contextPath);			
						if (response != null)
						    response.setContentType("APPLICATION/OCTET-STREAM");
						    response.setHeader("Content-Disposition","attachment; filename=ExcelData"+strDate+".xls");
				            os.write(b);
				            os.flush();			
					}
					return null;
				}

				public byte[] generateCSVType(ArrayList<ArrayList> ParamDataList,
						int No_Column, String contextPath) throws IOException {

					System.out.println("---generateCSV()---");
					StringBuffer strbuff = new StringBuffer();
					//System.out.println("ParamDataList:" + ParamDataList);
					//System.out.println("contextPath :" + contextPath);
					ArrayList<String> rowDataLst = new ArrayList<String>();
					ArrayList<String> HeaderLst = (ArrayList) ParamDataList.get(0);
					ArrayList<ArrayList> RecordWiseLst = (ArrayList) ParamDataList.get(1);
					//System.out.println("HeaderLst" + HeaderLst);
					//System.out.println("RecordWiseLst" + RecordWiseLst);
					// #### Header List
					for (String headerdata : HeaderLst) {
						rowDataLst.add(headerdata);
						//System.out.println("Loop--headerdata:" + headerdata);
					}
					//System.out.println("rowDataLst:" + rowDataLst);
					// #### Header List

					// #### Data List
					for (ArrayList<String> RecordWiseLstObj : RecordWiseLst) {
						//System.out.println("RecordWiseLstObj:" + RecordWiseLstObj);
						for (String SingleRecordDataObj : RecordWiseLstObj) {
							//System.out.println("DataLstInnerObj :" + SingleRecordDataObj);
							if (null != SingleRecordDataObj) {
								// rowDataLst.add(SingleRecordDataObj.replace("<b>","").replace("</b>",""));
								rowDataLst.add(SingleRecordDataObj.replace("<b>", "")
										.replace("</b>", ""));
							} else {
								rowDataLst.add(SingleRecordDataObj);
							}
						}
						// System.out.println("DataLstObj :"+DataLstObj);
					}
					//System.out.println("rowDataLst::" + rowDataLst);
					// #### Data List

					ArrayList FinalrowDatalist = new ArrayList<String>();
					//System.out.println("1");
					int y = 0;
					// System.out.println("2"+No_Column);
					for (int n = 0; n < rowDataLst.size(); n++) {

						if (n % No_Column == 0 && n != 0) {
							FinalrowDatalist.add(rowDataLst.get(n));
							FinalrowDatalist.add(n + y, "\n");
							// System.out.println("2n value inside if:"+n);
						//	System.out.println("n:" + n);
							y++;
						} else {
							// System.out.println("2n inside else:"+n);
							if (null != rowDataLst.get(n)) {
								if (rowDataLst.get(n).contains(",")) {
									rowDataLst.set(n, rowDataLst.get(n).replace(",", ";"));
								}
							}
							FinalrowDatalist.add(rowDataLst.get(n));
						}
						// System.out.println("rowDataLst.get "+rowDataLst.get(n)+"    "+n%3);
					}
					// System.out.println("rowDataLst :"+rowDataLst.toString().replace("\n,","\n"));
					// String tempStr = rowDataLst.toString().replace("\n,", "\n");
					//System.out.println("3");

					String tempStr = FinalrowDatalist.toString().replace("\n,", "\n").replace(" ,", ",").replace(", ", ",");
					// String tempStr = FinalrowDatalist.toString().replace("\n,", "\n");

					//System.out.println("4");
					// strbuff.append(ParamDataList.toString().substring(2,
					// ParamDataList.toString().length() - 2).replace("endrow,", "\n"));
					strbuff.append(tempStr.substring(1, tempStr.length() - 1));
					// System.out.println("strbuff :"+strbuff);
					///System.out.println("5");
					Calendar cal = Calendar.getInstance();
					SimpleDateFormat sdf = new SimpleDateFormat("ddMMyyyyHHmmss");
					String strDate = sdf.format(cal.getTime());
					BufferedWriter output = null;
					OutputStream outStrm;
					// File genfile = new File("D:\\GenerateFiles\\SampleFile" + strDate+
					// ".csv");
					File genfile = new File(contextPath + "\\Download\\DataCSVFile"
							+ strDate + ".csv");

					//System.out.println("6");
					output = new BufferedWriter(new FileWriter(genfile));
					output.write(strbuff.toString());
					//System.out.println("7");
					output.flush();
					output.close();
					//System.out.println("8");

					// ##
					// FileInputStream fis = new
					// FileInputStream("D:\\GenerateFiles\\SampleFile" + strDate+ ".csv");
					FileInputStream fis = new FileInputStream(contextPath
							+ "\\Download\\DataCSVFile" + strDate + ".csv");

					//System.out.println("9");
					byte b[];
					int x = fis.available();
					b = new byte[x];
					// System.out.println(" b size"+b.length);

					fis.read(b);
					// ##
					return b;
					// genfile.setReadOnly();
				}
				
				
				public byte[] generateEXLType(ArrayList<ArrayList> ParamDataList,int No_Column, String contextPath) throws DocumentException,IOException {
							System.out.println("---generateEXL()---");
							StringBuffer strbuff = new StringBuffer();
							System.out.println("ParamDataList:" + ParamDataList);
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
									cell.setCellValue(SingleRecordDataObj);
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
								FileOutputStream out = new FileOutputStream(new File(contextPath+ "\\Download\\DataCSVFile" + strDate + ".xls"));
								workbook.write(out);
								out.close();
								//System.out.println("Excel written successfully..");
							} catch (FileNotFoundException e) {
								e.printStackTrace();
							} catch (IOException e) {
								e.printStackTrace();
							}		
							FileInputStream fis = new FileInputStream(contextPath+ "\\Download\\DataCSVFile" + strDate + ".xls");
							//System.out.println("9");
							byte b[];
							int x = fis.available();
							b = new byte[x];
							// System.out.println(" b size"+b.length);
							fis.read(b);		
							return b;
			}
				
				
				public ActionForward viewInvoice(ActionMapping mapping, ActionForm form,
						HttpServletRequest request, HttpServletResponse response)
						throws Exception {
					
					return mapping.findForward("success");
				}
				public ActionForward getInvoiceDetails(ActionMapping mapping, ActionForm form,
						HttpServletRequest request, HttpServletResponse response)
						throws Exception {
					String res=null;
					ArrayList invoiceDetails = null;
					DynaActionForm dynaForm = (DynaActionForm) form;
					String invoicenumber=null;
					String enterCgpan=null;
					java.util.Date toDate=null;
					java.util.Date fromDate=null;
					String MLI_id=null;
					fromDate=(java.util.Date)dynaForm.get("invoiceFromDate");
					toDate=(java.util.Date)dynaForm.get("invoiceToDate");
					 MLI_id=(String)dynaForm.get("MLI_id");
					invoicenumber=(String)dynaForm.get("invoicenumber");
					enterCgpan=(String)dynaForm.get("enterCgpan");
					java.sql.Date startDate = null;
					java.sql.Date endDate = null;
			        System.out.println("fromDate="+fromDate+"toDate="+toDate);
					
						//startDate = new java.sql.Date(fromDate.getTime());
					
					
						//endDate = new java.sql.Date(toDate.getTime());
					if(invoicenumber.trim().length()==0)
					{
						invoicenumber=null;
					}
					else{
						invoicenumber=invoicenumber;
					}
					if(MLI_id.trim().length()==0)
						{
							MLI_id=null;
						}
					else{
						MLI_id = MLI_id;
					}
				     if(enterCgpan.trim().length()==0)
						{
						enterCgpan=null;
						}
				     else{
				    	 enterCgpan = enterCgpan;
				     }
					 if(fromDate.toString().trim().length()==0)
					 {
						startDate=null;
					 }
					 else{
						 startDate=new java.sql.Date(fromDate.getTime());
					 }
					 if(toDate.toString().trim().length()==0){
						
						endDate=null;						
					}
					 else{
						 endDate=new java.sql.Date(toDate.getTime());	
					 }
					
					
					Connection connection = DBConnection.getConnection();
					
					
			     System.out.println("startDate"+startDate+" endDate="+endDate+" MLI_id="+MLI_id+" invoicenumber="+invoicenumber+" enterCgpan="+enterCgpan);
			     
					CallableStatement slab = null;
					try {
						float taxAmt = 0.0f;
						invoiceDetails=new ArrayList();
						slab = connection
								.prepareCall("call FUN_SEARCH_INVOICE_DETAIL_NEW(?,?,?,?,?)");
						slab.setString(1,MLI_id);
						slab.setDate(2, startDate);
						slab.setDate(3, endDate);
						slab.setString(4,invoicenumber);
						slab.setString(5,enterCgpan);
					 
						slab.execute();
						ResultSet result = slab.executeQuery();
						System.out.println("resultset="+result.getRow());
						SimpleDateFormat sdfr = new SimpleDateFormat("dd-MM-yyyy");
						DateFormat sourceFormat = new SimpleDateFormat("dd/MM/yyyy");
						while (result.next()) {							
							Date approved_date = result.getDate(24);
							Date app_submit_date = result.getDate(26);
							Date invoice_date = result.getDate(3);
							String danNumber = result.getString(4);//Added by Sarita 23-02-2022
							System.out.println(result.getString(1));
							ReportActionForm raf=new ReportActionForm();							
							//String format_approved_date =  sourceFormat.format(approved_date);
							//String format_app_submit_date = sdfr.format(app_submit_date);
							//String format_invoice_date = sdfr.format(invoice_date);
							//Date final_approved_date = sdfr.parse(format_approved_date);
							//Date final_app_submit_date = sdfr.parse(format_app_submit_date);
							//Date final_invoice_date = sdfr.parse(format_invoice_date);
							
							//raf.setCgpan(result.getString(1));
							raf.setMLI_Name_Report(result.getString(1));
							raf.setInvoice_ID(result.getString(2));
							raf.setInvoice_date(invoice_date);
							raf.setDAN_NUMBER(result.getString(4));
							raf.setGuarantee_Amount(result.getFloat(5));
							raf.setSource_State(result.getString(6));
							raf.setSupply_State(result.getString(7));
							raf.setIGST_RATE(result.getFloat(10));
							raf.setIGST_AMT(result.getFloat(11));
							raf.setCGST_RATE(result.getFloat(12));
							raf.setCGST_AMT(result.getFloat(13));
							raf.setSGST_RATE(result.getFloat(14));
							raf.setSGST_AMT(result.getFloat(15));
							raf.setInvoice_Amount(result.getFloat(16));
							raf.setCreated_By(result.getString(17));
							raf.setCGPAN(result.getString(18));
							raf.setApp_ref_no(result.getString(19));
							raf.setSsi_unit_name(result.getString(20));
							raf.setSsi_state_name(result.getString(21));
							raf.setGst_no(result.getString(22));
							raf.setApp_approved_amount(result.getFloat(23));
							raf.setApp_approved_date_time(app_submit_date);
							raf.setTerm_tenure(result.getString(25));
							raf.setApplication_submit_date(approved_date);
							raf.setTaxable_amount(result.getFloat(23)*.015);
							raf.setState(result.getString(27));
							raf.setZoneBranch(result.getString(28));
							raf.setAddress(result.getString(29));
							raf.setCity(result.getString(30));
							raf.setPinCode(result.getString(31));
							//raf.setBaseAmount(result.getFloat(32));
							//Commented & Added by Sarita on 23022022 [START]
							//raf.setTaxable_amount(result.getFloat(23)*.015);
							if(danNumber != null && danNumber.trim().length()>0 && danNumber.startsWith("GF")){
								taxAmt = (float)(result.getFloat(23)*.015);
							}
							else if(danNumber != null && danNumber.trim().length()>0 && danNumber.startsWith("AF")){
								taxAmt = (float)(result.getFloat(32));
							}
							raf.setTaxable_amount(taxAmt);
							//Commented & Added by Sarita on 23022022 [END]
							invoiceDetails.add(raf);
							
						}
						
						
						
						
					}catch(Exception e)
					{
						e.printStackTrace();
					}
					finally {
						DBConnection.freeConnection(connection);
					}
					
							 request.setAttribute("invoiceDetails", invoiceDetails);
					 return mapping.findForward("success");
				}
				public ActionForward getSummaryReport(ActionMapping mapping,
						ActionForm form, HttpServletRequest request,
						HttpServletResponse response) throws Exception {
					
					String val = request.getParameter("val");
					String type [] = request.getParameterValues("type");
					if(type!=null){
						System.out.println("type:::::"+type[0]);
					}
					ReportActionForm actionFormObj = (ReportActionForm) form;
					Connection connection = DBConnection.getConnection();
					PreparedStatement ps;
					ResultSet rs;
					String contextPath1 = request.getSession(false).getServletContext().getRealPath("");
					String contextPath = PropertyLoader.changeToOSpath(contextPath1);
					Calendar cal = Calendar.getInstance();
					SimpleDateFormat sdf = new SimpleDateFormat("ddMMyyyyHHmmss");
					String strDate = sdf.format(cal.getTime());
					String query = null;
					String fileName = null;
					
					try{
						if(val!= null && val.equalsIgnoreCase("download")){
							Date date =	actionFormObj.getDtFromDate();
							Date endDate = actionFormObj.getDtToDate();
							 SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");  
							    String strDate1 = formatter.format(date); 
							    String strDate2 = formatter.format(endDate);
							    System.out.println(strDate1);
							    System.out.println(strDate2);
							    if(type[0].equalsIgnoreCase("MliWise")){
									 query = "select MEM_BANK_NAME AS MEMBANKNAME,count(1)AS NOOFAPPLICATION,sum(APP_APPROVED_AMOUNT) AS APPROVEAMOUNT from subdebt_application_detail A inner join member_info B on A.MEM_BNK_ID=B.MEM_BNK_ID AND A.MEM_ZNE_ID=B.MEM_ZNE_ID AND A.MEM_BRN_ID=B.MEM_BRN_ID where date(APP_CREATED_MODIFIED_DT)>=date('" + strDate1 + "') and date(APP_CREATED_MODIFIED_DT)<=date('" + strDate2 + "') GROUP BY MEM_BANK_NAME;";
									 fileName = "SummaryReportMliWise";
							    }
							    if(type[0].equalsIgnoreCase("StateWise")){
									 query = "select SSI_STATE_NAME AS STATENAME,count(1) as NOOFAPPLICATION,sum(APP_APPROVED_AMOUNT) AS APPROVEAMOUNT from subdebt_application_detail A where date(APP_CREATED_MODIFIED_DT)>=date('" + strDate1 + "') and date(APP_CREATED_MODIFIED_DT)<=date('" + strDate2 + "') GROUP BY SSI_STATE_NAME";
									 fileName = "SummaryReportStateWise";
							    }
							    if(type[0].equalsIgnoreCase("StateMonthWise")){
									 query = "select SSI_STATE_NAME AS STATENAME,concat(month(APP_SUBMITTED_DT),'-',year(APP_SUBMITTED_DT)) as Month,count(1) AS NOOFAPPLICATION,sum(APP_APPROVED_AMOUNT) AS APPROVEAMOUNT from subdebt_application_detail where date(APP_CREATED_MODIFIED_DT)>=date('" + strDate1 + "') and date(APP_CREATED_MODIFIED_DT)<=date('" + strDate2 + "') GROUP BY SSI_STATE_NAME,concat(month(APP_SUBMITTED_DT),'-',year(APP_SUBMITTED_DT));";
									 fileName = "SummaryReportStateMonthWise";
							    }
								ps = connection.prepareStatement(query);
								rs = ps.executeQuery();
								ResultSetMetaData rsmd = rs.getMetaData();
								int coulmnCount = rsmd.getColumnCount();
								 
								   
					            XSSFWorkbook workbook = new XSSFWorkbook();
								 XSSFSheet spreadsheet;
							      spreadsheet = workbook.createSheet(fileName);
							      
							      XSSFRow row = spreadsheet.createRow(1);
							      XSSFCell cell;
							      
							      for (int i = 1; i <= coulmnCount; i++) {
								    	 cell = row.createCell(i);
									     cell.setCellValue(rsmd.getColumnName(i));
										}
								   
								      
								      int i = 2;
									while (rs.next()) {
										 row = spreadsheet.createRow(i);
										 for(int j = 1;j<=coulmnCount;j++){
											 cell = row.createCell(j);
									         cell.setCellValue(rs.getString(j));
										 }
										 i++;
									}
							      
								ByteArrayOutputStream outByteStream = new ByteArrayOutputStream();
								workbook.write(outByteStream);
								byte [] outArray = outByteStream.toByteArray();
								response.setContentType("application/ms-excel");
								response.setContentLength(outArray.length);
								response.setHeader("Expires:", "0"); // eliminates browser caching
								response.setHeader("Content-Disposition", "attachment; filename="+fileName+""+strDate+".xls");
								OutputStream outStream = response.getOutputStream();
								outStream.write(outArray);
								outStream.flush();
						}
					}
					catch (Exception exception) {
						Log.logException(exception);
						throw new DatabaseException(exception.getMessage());
					} 
					/*finally {
						DBConnection.freeConnection(connection);
					}*/

					
					return mapping.findForward("displaySummaryReport");
				}
				
				public ActionForward getGstReport(ActionMapping mapping,
						ActionForm form, HttpServletRequest request,
						HttpServletResponse response) throws Exception {
					
					String val = request.getParameter("val");
					ReportActionForm actionFormObj = (ReportActionForm) form;
					Connection connection = DBConnection.getConnection();
					PreparedStatement ps;
					ResultSet rs = null;
					String contextPath1 = request.getSession(false).getServletContext().getRealPath("");
					String contextPath = PropertyLoader.changeToOSpath(contextPath1);
					Calendar cal = Calendar.getInstance();
					SimpleDateFormat sdf = new SimpleDateFormat("ddMMyyyyHHmmss");
					String strDate = sdf.format(cal.getTime());
					CallableStatement cStmt = null;
					ResultSetMetaData resultSetMetaData1 = null;
					String errorCode = "";
					ArrayList coulmName = new ArrayList();
					
					try{
						if(val!= null && val.equalsIgnoreCase("download")){
							Date date =	actionFormObj.getDtFromDate();
							Date date1 =	actionFormObj.getDtToDate();
							
							 SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");  
							    String strDate1 = formatter.format(date); 
							    String strDate2 = formatter.format(date1); 
							    System.out.println(strDate1);
							    System.out.println(strDate2);
							    cStmt = connection.prepareCall("{call FUNC_GETINVOICEDATA(?,?,?)}");
								cStmt.setString(1, strDate1);
								cStmt.setString(2, strDate2);
								cStmt.registerOutParameter(3, Types.INTEGER);
								cStmt.execute();
								
								rs = (ResultSet) cStmt.getResultSet();
								ResultSetMetaData rsmd = rs.getMetaData();
								int coulmnCount = rsmd.getColumnCount();

								for (int i = 1; i <= coulmnCount; i++) {

									coulmName.add(rsmd.getColumnName(i));

								}
								String error = cStmt.getString(3);
					            
								
								XSSFWorkbook workbook = new XSSFWorkbook();
								XSSFSheet spreadsheet;
							    spreadsheet = workbook.createSheet("GSTReport");
							      
							     XSSFRow row = spreadsheet.createRow(1);
							     XSSFCell cell;
							     for (int i = 1; i <= coulmnCount; i++) {
							    	 cell = row.createCell(i);
								     cell.setCellValue(rsmd.getColumnName(i));
									}
							   
							      
							      int i = 2;
								while (rs.next()) {
									 row = spreadsheet.createRow(i);
									 for(int j = 1;j<=coulmnCount;j++){
										 cell = row.createCell(j);
								         cell.setCellValue(rs.getString(j));
									 }
									 i++;
								}
								ByteArrayOutputStream outByteStream = new ByteArrayOutputStream();
								workbook.write(outByteStream);
								byte [] outArray = outByteStream.toByteArray();
								response.setContentType("application/ms-excel");
								response.setContentLength(outArray.length);
								response.setHeader("Expires:", "0"); // eliminates browser caching
								response.setHeader("Content-Disposition", "attachment; filename=GSTInvoiceData"+strDate+".xls");
								OutputStream outStream = response.getOutputStream();
								outStream.write(outArray);
								outStream.flush();
						}
					}
					catch (Exception exception) {
						Log.logException(exception);
						throw new DatabaseException(exception.getMessage());
					} 
					/*finally {
						DBConnection.freeConnection(connection);
					}*/

					
					return mapping.findForward("displayGstReport");
				}
				
				public ActionForward getDetailSummaryReport(ActionMapping mapping,
						ActionForm form, HttpServletRequest request,
						HttpServletResponse response) throws Exception {
					
					String val = request.getParameter("val");
					String type [] = request.getParameterValues("type");
					if(type!=null){
						System.out.println("type:::::"+type[0]);
					}
					ReportActionForm actionFormObj = (ReportActionForm) form;
					Connection connection = DBConnection.getConnection();
					PreparedStatement ps;
					ResultSet rs;
					String contextPath1 = request.getSession(false).getServletContext().getRealPath("");
					String contextPath = PropertyLoader.changeToOSpath(contextPath1);
					Calendar cal = Calendar.getInstance();
					SimpleDateFormat sdf = new SimpleDateFormat("ddMMyyyyHHmmss");
					String strDate = sdf.format(cal.getTime());
					String query = null;
					String fileName = null;
					
					try{
						if(val!= null && val.equalsIgnoreCase("download")){
							Date date =	actionFormObj.getDtFromDate();
							Date endDate = actionFormObj.getDtToDate();
							 SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");  
							    String strDate1 = formatter.format(date); 
							    String strDate2 = formatter.format(endDate);
							    System.out.println(strDate1);
							    System.out.println(strDate2);
							    if(type[0].equalsIgnoreCase("MliWise")){
									 query = "select MEM_BANK_NAME AS MEMBANKNAME,count(1)AS NOOFAPPLICATION,sum(APP_APPROVED_AMOUNT) AS APPROVEAMOUNT from subdebt_application_detail A inner join member_info B on A.MEM_BNK_ID=B.MEM_BNK_ID AND A.MEM_ZNE_ID=B.MEM_ZNE_ID AND A.MEM_BRN_ID=B.MEM_BRN_ID where date(APP_CREATED_MODIFIED_DT)>=date('" + strDate1 + "') and date(APP_CREATED_MODIFIED_DT)<=date('" + strDate2 + "') GROUP BY MEM_BANK_NAME;";
									 fileName = "SummaryReportMliWise";
							    }
							    if(type[0].equalsIgnoreCase("StateWise")){
									 query = "select SSI_STATE_NAME AS STATENAME,count(1) as NOOFAPPLICATION,sum(APP_APPROVED_AMOUNT) AS APPROVEAMOUNT from subdebt_application_detail A where date(APP_CREATED_MODIFIED_DT)>=date('" + strDate1 + "') and date(APP_CREATED_MODIFIED_DT)<=date('" + strDate2 + "') GROUP BY SSI_STATE_NAME";
									 fileName = "SummaryReportStateWise";
							    }
							    if(type[0].equalsIgnoreCase("StateMonthWise")){
									 query = "select SSI_STATE_NAME AS STATENAME,concat(month(APP_SUBMITTED_DT),'-',year(APP_SUBMITTED_DT)) as Month,count(1) AS NOOFAPPLICATION,sum(APP_APPROVED_AMOUNT) AS APPROVEAMOUNT from subdebt_application_detail where date(APP_CREATED_MODIFIED_DT)>=date('" + strDate1 + "') and date(APP_CREATED_MODIFIED_DT)<=date('" + strDate2 + "') GROUP BY SSI_STATE_NAME,concat(month(APP_SUBMITTED_DT),'-',year(APP_SUBMITTED_DT));";
									 fileName = "SummaryReportStateMonthWise";
							    }
								ps = connection.prepareStatement(query);
								rs = ps.executeQuery();
								ResultSetMetaData rsmd = rs.getMetaData();
								int coulmnCount = rsmd.getColumnCount();
								 
								   
					            XSSFWorkbook workbook = new XSSFWorkbook();
								 XSSFSheet spreadsheet;
							      spreadsheet = workbook.createSheet(fileName);
							      
							      XSSFRow row = spreadsheet.createRow(1);
							      XSSFCell cell;
							      
							      for (int i = 1; i <= coulmnCount; i++) {
								    	 cell = row.createCell(i);
									     cell.setCellValue(rsmd.getColumnName(i));
										}
								   
								      
								      int i = 2;
									while (rs.next()) {
										 row = spreadsheet.createRow(i);
										 for(int j = 1;j<=coulmnCount;j++){
											 cell = row.createCell(j);
									         cell.setCellValue(rs.getString(j));
										 }
										 i++;
									}
							      
								ByteArrayOutputStream outByteStream = new ByteArrayOutputStream();
								workbook.write(outByteStream);
								byte [] outArray = outByteStream.toByteArray();
								response.setContentType("application/ms-excel");
								response.setContentLength(outArray.length);
								response.setHeader("Expires:", "0"); // eliminates browser caching
								response.setHeader("Content-Disposition", "attachment; filename="+fileName+""+strDate+".xls");
								OutputStream outStream = response.getOutputStream();
								outStream.write(outArray);
								outStream.flush();
						}
					}
					catch (Exception exception) {
						Log.logException(exception);
						throw new DatabaseException(exception.getMessage());
					} 
					/*finally {
						DBConnection.freeConnection(connection);
					}*/

					
					return mapping.findForward("detailSummaryReport");
				}
}
