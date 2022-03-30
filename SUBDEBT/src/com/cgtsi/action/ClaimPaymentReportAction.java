package com.cgtsi.action;

import java.io.ByteArrayOutputStream;
import java.io.OutputStream;
import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Types;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import com.cgtsi.actionform.ClaimPaymentReportForm;
import com.cgtsi.actionform.MLIRecoveryReportForm;
import com.cgtsi.actionform.ClaimPaymentTableData;
import com.cgtsi.admin.User;
import com.cgtsi.common.Constants;
import com.cgtsi.common.Log;
import com.cgtsi.util.DBConnection;
import com.mysql.cj.protocol.Resultset;

public class ClaimPaymentReportAction extends BaseAction
{
	public ActionForward getClaimPaymentReport(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) throws Exception 
	{
		ClaimPaymentReportForm form1 = (ClaimPaymentReportForm) form;
		ArrayList claimPaymentReportList = new ArrayList();
		ArrayList promoter = new ArrayList();
		User user = getUserInformation(request);
		Connection conn = null;
		CallableStatement callableStmt = null;
		ResultSet rs = null;
		int status = -1;
		String errorCode = "", message = "", bankId = "", zoneId = "", branchId = "", memberId = "", mliId = "",
				mliName = "", loginType = "", mliLblDtl = "",cgpan = "",claimPaymentFromDate="",claimPaymentToDate="",claimPaymentisSearchClicked="",claimPaymentMLIName="";
		try {
			claimPaymentFromDate = request.getParameter("claimPaymentFromDate");
			claimPaymentToDate = request.getParameter("claimPaymentToDate");
			request.setAttribute("claimPaymentFromDate", claimPaymentFromDate);
			request.setAttribute("claimPaymentToDate", claimPaymentToDate);
			claimPaymentMLIName = form1.getClaimPaymentMLIName();
			if("0".equals(claimPaymentMLIName) || "".equals(claimPaymentMLIName)){
				claimPaymentMLIName = "";
			}
			
			if (conn == null) {
				conn = DBConnection.getConnection(false);
			}
			bankId = user.getBankId();
			zoneId = user.getZoneId();
			branchId = user.getBranchId();
			memberId = bankId + zoneId + branchId;

			callableStmt = conn.prepareCall("{call FUNC_GETMLIDetails(?,?,?)}");
			callableStmt.registerOutParameter(1, Types.VARCHAR);
			callableStmt.setString(2, memberId);
			callableStmt.registerOutParameter(3, Types.VARCHAR);

			callableStmt.execute();
			status = callableStmt.getInt(1);
			errorCode = callableStmt.getString(3);

			if (status == Constants.FUNCTION_FAILURE) {
				message = errorCode;
				if (callableStmt != null) {
					callableStmt.close();
				}
				if (conn != null) {
					conn.rollback();
				}
			} else if (status == Constants.FUNCTION_SUCCESS) 
			{
				rs = callableStmt.executeQuery();
				while (rs.next()) {
					mliId = rs.getString("MLI_id");
					mliName = rs.getString("MLI_Name");
					loginType = rs.getString("LOGINtYPE");
					mliLblDtl = mliId + "-" + loginType;

					Map option = new HashMap();
					option.put("label", mliName);
					option.put("value", mliLblDtl);
					claimPaymentReportList.add(option);
				}
				form1.setClaimPaymentLoginType(loginType);
				form1.setClaimPaymentReportList(claimPaymentReportList);
			}
			
			cgpan = form1.getClaimPaymentCGPAN();
			claimPaymentisSearchClicked = form1.getClaimPaymentisSearchClicked();
			if((cgpan != null && cgpan.trim().length()>0) && (claimPaymentMLIName != null && claimPaymentMLIName.trim().length()>0))
			{
				String memBankId = (form1.getClaimPaymentMLIName() != "0" ? form1.getClaimPaymentMLIName().substring(0, 4) : null);
				promoter = getPromoterDetails(cgpan,conn,memBankId);
				form1.setClaimPaymentPromoterValues(promoter);
				form1.setClaimPaymentCGPAN(cgpan);
			}
			else
			{
				Map option = new HashMap();
				option.put("label", "");
				option.put("value", "Select");
				promoter.add(option);
				form1.setClaimPaymentPromoterValues(promoter);
				form1.setClaimPaymentCGPAN(cgpan);
			}
		} catch (Exception err) {
			Map option = new HashMap();
			option.put("label", "");
			option.put("value", "Select");
			promoter.add(option);
			form1.setClaimPaymentPromoterValues(promoter);
			form1.setClaimPaymentReportList(promoter);
			try {
				conn.rollback();
			} catch (SQLException ignore) {
			}
			message = err.getMessage();
			Log.log(2, "ClaimReportAction", "getClaimReport", err.getMessage());
			err.printStackTrace();
		} finally {
			try {
				if (rs != null) {
					((ResultSet) rs).close();
					rs = null;
				}
				if (callableStmt != null) {
					callableStmt.close();
					callableStmt = null;
				}
				if (conn != null) {
					DBConnection.freeConnection(conn);
				}
			} catch (Exception err) {
				System.out.println(err.getMessage());
				err.printStackTrace();
			}
		}
		if (message != null && message.trim().length() > 0) {
			form1.setMessage(message);}
		return mapping.findForward("claimPaymentReport");
	}
	
	public ActionForward getClaimPaymentDetailReport(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) throws Exception 
	{
		ClaimPaymentReportForm form1 = (ClaimPaymentReportForm) form;
		Connection conn = null;
		CallableStatement callableStmt = null;
		ResultSet rs = null;
		String claimPaymentFromDate = "", claimPaymentToDate = "",claimPaymentMLIName = "",claimPaymentMLIId="",claimPaymentClaimStatus="",
				claimPaymentCGPAN="",claimPaymentPromoterName="",claimPaymentClaimRefNo="",claimPaymentPromoterITPAN="",claimPaymentType="";
		int status = -1 , count = 0;
		String errorCode = "", message = "";
		ClaimPaymentTableData claimPaymentTableData = null;
		ArrayList<ClaimPaymentTableData> claimPaymentList = new ArrayList<>();
		try
		{			
			if (conn == null) {
				conn = DBConnection.getConnection(false);
			}
			
			claimPaymentFromDate = request.getParameter("claimPaymentFromDate");
			claimPaymentToDate = request.getParameter("claimPaymentToDate");
			request.setAttribute("claimPaymentFromDate", claimPaymentFromDate);
			request.setAttribute("claimPaymentToDate", claimPaymentToDate);
			
			claimPaymentMLIName = (form1.getClaimPaymentMLIName() != "" ? form1.getClaimPaymentMLIName().substring(0,4) : null);
			claimPaymentMLIId = (form1.getClaimPaymentMLIId() != "" ? form1.getClaimPaymentMLIId() : null);
			claimPaymentClaimStatus = form1.getClaimPaymentClaimStatus();
			claimPaymentType = form1.getClaimPaymentClaimType();
			claimPaymentCGPAN = (form1.getClaimPaymentCGPAN() != "" ? form1.getClaimPaymentCGPAN() : null);
			claimPaymentPromoterName = ((form1.getClaimPaymentPromoterName().equals("0") || form1.getClaimPaymentPromoterName().equalsIgnoreCase("Select"))  ?  null : form1.getClaimPaymentPromoterName());
			claimPaymentClaimRefNo = (form1.getClaimPaymentClaimRefNo() != "" ? form1.getClaimPaymentClaimRefNo() : null);
			claimPaymentPromoterITPAN = (form1.getClaimPaymentPromoterITPAN() != "" ? form1.getClaimPaymentPromoterITPAN() : null);
			
			/*System.out.println("claimPaymentFromDate -["+claimPaymentFromDate+"] \n claimPaymentToDate -["+claimPaymentToDate+"]\n claimPaymentMLIName -["+claimPaymentMLIName+"]\n claimPaymentMLIId -["+claimPaymentMLIId+"]\n"
					+ "claimPaymentClaimStatus -["+claimPaymentClaimStatus+"]\n claimPaymentCGPAN -["+claimPaymentCGPAN+"]\n "
					+ "claimPaymentPromoterName -["+claimPaymentPromoterName+"]\n claimPaymentClaimRefNo -["+claimPaymentClaimRefNo+"]\n "
					+ "claimPaymentPromoterITPAN -["+claimPaymentPromoterITPAN+"]\n ");*/
			
			callableStmt = conn.prepareCall("{call FUNC_GETCLAIMSETTLEMENT_REPORT(?,?,?,?,?,?,?,?,?,?,?,?)}");
			callableStmt.registerOutParameter(1, Types.VARCHAR);
			callableStmt.setString(2, claimPaymentMLIName);
			callableStmt.setString(3, claimPaymentCGPAN);
			callableStmt.setString(4, claimPaymentPromoterName);
			callableStmt.setString(5, claimPaymentClaimStatus);
			callableStmt.setString(6, claimPaymentPromoterITPAN);
			callableStmt.setString(7, claimPaymentMLIId);
			callableStmt.setString(8, claimPaymentClaimRefNo);
			java.util.Date date1 = new SimpleDateFormat("dd/MM/yyyy").parse(claimPaymentFromDate);
			SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
			String dateVal = formatter.format(date1);
			callableStmt.setString(9, dateVal);
			
			java.util.Date date2 = new SimpleDateFormat("dd/MM/yyyy").parse(claimPaymentToDate);
			SimpleDateFormat formatter1 = new SimpleDateFormat("yyyy-MM-dd");
			String dateVa2 = formatter1.format(date2);
			callableStmt.setString(10, dateVa2);
			callableStmt.setString(11, claimPaymentType);
			callableStmt.registerOutParameter(12, Types.VARCHAR);

			callableStmt.execute();
			status = callableStmt.getInt(1);
			errorCode = callableStmt.getString(12);
			if (status == Constants.FUNCTION_FAILURE) {
				message = errorCode;
				if (callableStmt != null) {
					callableStmt.close();
				}
				if (conn != null) {
					conn.rollback();
				}
			} else if (status == Constants.FUNCTION_SUCCESS) {
				rs = callableStmt.executeQuery();
				while (rs.next()) {
					count++;
					claimPaymentTableData = new ClaimPaymentTableData();
					claimPaymentTableData.setClaimPaymentSrNo(count);
					claimPaymentTableData.setMLIName(rs.getString("MLI_NAME"));//1
					claimPaymentTableData.setZoneName(rs.getString("ZONE_NAME"));//2
					claimPaymentTableData.setMemberId(rs.getString("MEMBER_ID"));//3
					claimPaymentTableData.setClaimPaymentCGPAN(rs.getString("CGPAN"));//4
					claimPaymentTableData.setClaimPaymentRefNumber(rs.getString("CLAIM_REF_NUMBER"));//5
					claimPaymentTableData.setClaimPaymentPromoterName(rs.getString("PROMOTER_NAME"));//6
					claimPaymentTableData.setClaimPaymentPromoterITPAN(rs.getString("PROMOTER_ITPAN"));//7
					claimPaymentTableData.setClaimPaymentSSIUnitName(rs.getString("SSI_UNIT_NAME"));//8
					claimPaymentTableData.setClaimPaymentApprovedAmt(rs.getString("APPROVED_AMT"));//9
					claimPaymentTableData.setClaimPaymentRevisedOutstandingAmountAsOnNPA(rs.getString("Revised_OUTSTANDING_AMOUNT_AS_ON_NPA"));//10
					claimPaymentTableData.setClaimPaymentAmountRecoveredAfterNPA(rs.getString("AMOUNT_RECOVERED_AFTER_NPA"));//11
					claimPaymentTableData.setClaimPaymentNetOutstandingAmount(rs.getString("NET_OUTSTANDING_AMOUNT"));//12
					claimPaymentTableData.setClaimPaymentAmountClaimedByMLI(rs.getString("AMOUNT_CLAIMED_BY_MLI"));//13
					claimPaymentTableData.setClaimPaymentClaimEligibleAmount(rs.getString("CLAIM_ELIGIBLE_AMOUNT"));//14
					claimPaymentTableData.setClaimPaymentAmountPaybleInFirstInstalment(rs.getString("AMOUNT_PAYBLE_IN_FIRST_INSTALMENT"));//15
					claimPaymentTableData.setClaimPaymentASFDeductedAmount(rs.getString("ASF_DEDUCTED_AMOUNT"));//16
					claimPaymentTableData.setClaimPaymentASFRefundableAmount(rs.getString("ASF_REFUNDABLE_AMOUNT"));//17
					claimPaymentTableData.setClaimPaymentNETPaidAmount(rs.getString("NET_PAID_AMOUNT"));//18
					claimPaymentTableData.setClaimPaymentUTRNo(rs.getString("UTR_NO"));//19
					claimPaymentTableData.setClaimPaymentAccountNo(rs.getString("ACCOUNT_NO"));//19
					claimPaymentTableData.setClaimPaymentClaimApprovedDate(rs.getString("CLAIM_APPROVED_DATE"));//19
					claimPaymentTableData.setClaimPaymentClaimPaymentDate(rs.getString("CLAIM_PAYMENT_DATE"));//19
					claimPaymentTableData.setClaimPaymentStatus(rs.getString("Status"));//19
					claimPaymentList.add(claimPaymentTableData);
				}
					if(claimPaymentList.size() == 0){form1.setClaimPaymentListCount(1);}
					if(claimPaymentList.size() == 0){
						request.setAttribute("claimPaymentList", null);	
					}else {
					request.setAttribute("claimPaymentList", claimPaymentList);}
			}
			getClaimPaymentReport(mapping, form,request, response);
		}
		catch (Exception err) {
			try {
				conn.rollback();
			} catch (SQLException ignore) {
			}
			Log.log(2, "NPAMarkingReportsAction", "getNPAMarkingDetailSummaryReport", err.getMessage());
			message = err.getMessage();
			err.printStackTrace();
		} finally {
			try {
				if (rs != null) {
					((ResultSet) rs).close();
					rs = null;
				}
				if (callableStmt != null) {
					callableStmt.close();
					callableStmt = null;
				}
				if (conn != null) {
					DBConnection.freeConnection(conn);
				}
			} catch (Exception err) {
				System.out.println(err.getMessage());
				err.printStackTrace();
			}
		}
		if (message != null && message.trim().length() > 0) {
			form1.setMessage(message);}
		return mapping.findForward("claimPaymentReport");
	}
	
	public ActionForward claimPaymentExportToExcel(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) throws Exception 
	{
		ClaimPaymentReportForm form1 = (ClaimPaymentReportForm) form;
		Connection conn = null;
		CallableStatement callableStmt = null;
		ResultSet rs = null;
		String claimPaymentFromDate = "", claimPaymentToDate = "",claimPaymentMLIName = "",claimPaymentMLIId="",claimPaymentClaimStatus="",
				claimPaymentCGPAN="",claimPaymentPromoterName="",claimPaymentClaimRefNo="",claimPaymentPromoterITPAN="",claimPaymentType="";
		int status = -1 , count = 0;
		String errorCode = "", message = "";
		ClaimPaymentTableData claimPaymentTableData = null;
		ArrayList<ClaimPaymentTableData> claimPaymentList = new ArrayList<>();
		String fileName = "ClaimPaymentReport";
		try
		{			
			if (conn == null) {
				conn = DBConnection.getConnection(false);
			}
			
			claimPaymentFromDate = request.getParameter("claimPaymentFromDate");
			claimPaymentToDate = request.getParameter("claimPaymentToDate");
			request.setAttribute("claimPaymentFromDate", claimPaymentFromDate);
			request.setAttribute("claimPaymentToDate", claimPaymentToDate);
			
			claimPaymentMLIName = (form1.getClaimPaymentMLIName() != "" ? form1.getClaimPaymentMLIName().substring(0,4) : null);
			claimPaymentMLIId = (form1.getClaimPaymentMLIId() != "" ? form1.getClaimPaymentMLIId() : null);
			claimPaymentClaimStatus = form1.getClaimPaymentClaimStatus();
			claimPaymentType = form1.getClaimPaymentClaimType();
			claimPaymentCGPAN = (form1.getClaimPaymentCGPAN() != "" ? form1.getClaimPaymentCGPAN() : null);
			claimPaymentPromoterName = ((form1.getClaimPaymentPromoterName().equals("0") || form1.getClaimPaymentPromoterName().equalsIgnoreCase("Select"))  ?  null : form1.getClaimPaymentPromoterName());
			claimPaymentClaimRefNo = (form1.getClaimPaymentClaimRefNo() != "" ? form1.getClaimPaymentClaimRefNo() : null);
			claimPaymentPromoterITPAN = (form1.getClaimPaymentPromoterITPAN() != "" ? form1.getClaimPaymentPromoterITPAN() : null);
			
			/*System.out.println("claimPaymentFromDate -["+claimPaymentFromDate+"] \n claimPaymentToDate -["+claimPaymentToDate+"]\n claimPaymentMLIName -["+claimPaymentMLIName+"]\n claimPaymentMLIId -["+claimPaymentMLIId+"]\n"
					+ "claimPaymentClaimStatus -["+claimPaymentClaimStatus+"]\n claimPaymentCGPAN -["+claimPaymentCGPAN+"]\n "
				    + "claimPaymentPromoterName -["+claimPaymentPromoterName+"]\n claimPaymentClaimRefNo -["+claimPaymentClaimRefNo+"]\n "
				    +"claimPaymentPromoterITPAN -["+claimPaymentPromoterITPAN+"]\n ");*/
			
			callableStmt = conn.prepareCall("{call FUNC_GETCLAIMSETTLEMENT_REPORT(?,?,?,?,?,?,?,?,?,?,?,?)}");
			callableStmt.registerOutParameter(1, Types.VARCHAR);
			callableStmt.setString(2, claimPaymentMLIName);
			callableStmt.setString(3, claimPaymentCGPAN);
			callableStmt.setString(4, claimPaymentPromoterName);
			callableStmt.setString(5, claimPaymentClaimStatus);
			callableStmt.setString(6, claimPaymentPromoterITPAN);
			callableStmt.setString(7, claimPaymentMLIId);
			callableStmt.setString(8, claimPaymentClaimRefNo);
			java.util.Date date1 = new SimpleDateFormat("dd/MM/yyyy").parse(claimPaymentFromDate);
			SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
			String dateVal = formatter.format(date1);
			callableStmt.setString(9, dateVal);
			
			java.util.Date date2 = new SimpleDateFormat("dd/MM/yyyy").parse(claimPaymentToDate);
			SimpleDateFormat formatter1 = new SimpleDateFormat("yyyy-MM-dd");
			String dateVa2 = formatter1.format(date2);
			callableStmt.setString(10, dateVa2);
			callableStmt.setString(11, claimPaymentType);
			callableStmt.registerOutParameter(12, Types.VARCHAR);

			callableStmt.execute();
			status = callableStmt.getInt(1);
			errorCode = callableStmt.getString(12);
			if (status == Constants.FUNCTION_FAILURE) {
				
				message = errorCode;
				if (callableStmt != null) {
					callableStmt.close();
				}
				if (conn != null) {
					conn.rollback();
				}
			} else if (status == Constants.FUNCTION_SUCCESS) {
				rs = callableStmt.executeQuery();
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
					for (int j = 1; j <= coulmnCount; j++) {
						cell = row.createCell(j);
						cell.setCellValue(rs.getString(j));
					}
					i++;
				}

				ByteArrayOutputStream outByteStream = new ByteArrayOutputStream();
				workbook.write(outByteStream);
				byte[] outArray = outByteStream.toByteArray();
				response.setContentType("application/ms-excel");
				response.setContentLength(outArray.length);
				response.setHeader("Expires:", "0"); // eliminates browser
														// caching
				response.setHeader("Content-Disposition", "attachment; filename=" + fileName + ".xls");
				OutputStream outStream = response.getOutputStream();
				outStream.write(outArray);
				outStream.flush();
			}
			getClaimPaymentReport(mapping, form,request, response);
		}
		catch (Exception err) {
			try {
				conn.rollback();
			} catch (SQLException ignore) {
			}
			message = err.getMessage();
			Log.log(2, "NPAMarkingReportsAction", "getNPAMarkingDetailSummaryReport", err.getMessage());
			err.printStackTrace();
		} finally {
			try {
				if (rs != null) {
					((ResultSet) rs).close();
					rs = null;
				}
				if (callableStmt != null) {
					callableStmt.close();
					callableStmt = null;
				}
				if (conn != null) {
					DBConnection.freeConnection(conn);
				}
			} catch (Exception err) {
				System.out.println(err.getMessage());
				err.printStackTrace();
			}
		}
		if (message != null && message.trim().length() > 0) {
			form1.setMessage(message);}
		return mapping.findForward("");
	}
	
	public ArrayList getPromoterDetails(String cgpan, Connection conn, String bankId) {
		ArrayList promoter = new ArrayList();
		PreparedStatement pstmt = null;
		Resultset rs = null;
		try {
			String query = "SELECT B.PMR_REF_NO,CONCAT(CPFIRSTNAME,' ',CPLASTNAME) "
					+ "PROMOTERNAME FROM SUBDEBT_APPLICATION_DETAIL A LEFT JOIN "
					+ "SUBDEBT_PROMOTER_DETAIL B ON A.APP_REF_NO=B.APP_REF_NO WHERE CGPAN=? and MEM_BNK_ID=?";
			pstmt = conn.prepareStatement(query);
			pstmt.setString(1, cgpan);
			pstmt.setString(2, bankId);
			rs = (Resultset) pstmt.executeQuery();

			while (((ResultSet) rs).next()) {
				{
					String pmr_refNo = ((ResultSet) rs).getString("PMR_REF_NO");
					String promoterName = ((ResultSet) rs).getString("PROMOTERNAME");
					
					Map option = new HashMap();
					option.put("label", promoterName);
					option.put("value", pmr_refNo);
					promoter.add(option);
				}
			}
		} catch (Exception err) {
			Log.log(2, "DisbursementAction", "WriteToFile", err.getMessage());
			err.printStackTrace();
			try {
				conn.rollback();
			} catch (SQLException ignore) {}
		} finally {
			try {
				if (rs != null) {
					((ResultSet) rs).close();
					rs = null;
				}
				if (pstmt != null) {
					pstmt.close();
					pstmt = null;
				}
			} catch (Exception err) {
				Log.log(2, "DisbursementAction", "WriteToFile", err.getMessage());
				err.printStackTrace();
			}
		}
		return promoter;
	}
}
