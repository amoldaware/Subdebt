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
import java.util.Calendar;
import java.util.Date;
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

import com.cgtsi.actionform.MLIClaimRecoveryData;
import com.cgtsi.actionform.NPAReportForm;
import com.cgtsi.actionform.NPAReportTableData;
import com.cgtsi.actionform.ReportActionForm;
import com.cgtsi.admin.User;
import com.cgtsi.common.Constants;
import com.cgtsi.common.DatabaseException;
import com.cgtsi.common.Log;
import com.cgtsi.util.DBConnection;
import com.cgtsi.util.PropertyLoader;
import com.mysql.cj.protocol.Resultset;

import jdk.jfr.events.SecurityPropertyModificationEvent;

public class NPAMarkingReportsAction extends BaseAction {

	public ActionForward getNPAMarkingDetailSummaryReport(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) throws Exception {
		NPAReportForm form1 = (NPAReportForm) form;
		ArrayList mliLblDtlList = new ArrayList();
		ArrayList promoter = new ArrayList();
		User user = getUserInformation(request);
		Connection conn = null;
		CallableStatement callableStmt = null;
		ResultSet rs = null;
		int status = -1;
		String errorCode = "", message = "", bankId = "", zoneId = "", branchId = "", memberId = "", mliId = "",
				mliName = "", mliName1 = "" , loginType = "", mliLblDtl = "",cgpan = "",guaranteeStartDate="",guaranteeEndDate="";
		try {
			guaranteeStartDate = request.getParameter("guaranteeStartDate");
			guaranteeEndDate = request.getParameter("guaranteeEndDate");
			request.setAttribute("guaranteeStartDate", guaranteeStartDate);
			request.setAttribute("guaranteeEndDate", guaranteeEndDate);
			
			if (conn == null) {
				conn = DBConnection.getConnection(false);
			}
			mliName1 = form1.getMliName();
			if("0".equals(mliName1) || "".equals(mliName1)){
				mliName1 = "";
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
					mliLblDtlList.add(option);
				}
				form1.setLoginType(loginType);
				form1.setMliDetals(mliLblDtlList);
			}
			
			cgpan = form1.getCgpan();
			if(cgpan != null && cgpan.trim().length()>0 && (mliName1 != null && mliName1.trim().length()>0))
			{
				String memBankId = (form1.getMliName() != "" ? form1.getMliName().substring(0, 4) : null);
				promoter = getPromoterDetails(cgpan,conn,memBankId);
				form1.setPromoterValues(promoter);
				form1.setCgpan(cgpan);
			}
			else
			{
				Map option = new HashMap();
				option.put("label", "");
				option.put("value", "Select");
				promoter.add(option);
				form1.setPromoterValues(promoter);
				form1.setCgpan(cgpan);
			}
		} catch (Exception err) {
			Map option = new HashMap();
			option.put("label", "");
			option.put("value", "Select");
			promoter.add(option);
			form1.setPromoterValues(promoter);
			form1.setMliDetals(promoter);
			try {
				conn.rollback();
			} catch (SQLException ignore) {}
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
		return mapping.findForward("npaMarkingReport");
	}
	
	public ActionForward getGuaranteeDetailSummaryReport(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) throws Exception 
	{
		NPAReportForm form1 = (NPAReportForm) form;
		Connection conn = null;
		CallableStatement callableStmt = null;
		ResultSet rs = null;
		String guaranteeStartDate = "", guaranteeEndDate = "",mliName = "",cgpan="",promoterName="",guranteeStatus="",promoter_itpan="",mliId="",loanAccountNo="",npaStatus="";
		int status = -1 , count = 0;
		String errorCode = "", message = "";
		NPAReportTableData npaReportTableData = null;
		ArrayList<NPAReportTableData> mliReportList = new ArrayList<>();
		try
		{			
			if (conn == null) {
				conn = DBConnection.getConnection(false);
			}
			
			guaranteeStartDate = request.getParameter("guaranteeStartDate");
			guaranteeEndDate = request.getParameter("guaranteeEndDate");
			request.setAttribute("guaranteeStartDate", guaranteeStartDate);
			request.setAttribute("guaranteeEndDate", guaranteeEndDate);
			
			mliName = (form1.getMliName() != "" ? form1.getMliName().substring(0,4) : null);
			mliId = (form1.getMliId() != "" ? form1.getMliId() : null);
			npaStatus = form1.getNpaStatus();
			cgpan = (form1.getCgpan() != "" ? form1.getCgpan() : null);
			loanAccountNo = (form1.getLoanAccountNo() != "" ? form1.getLoanAccountNo() : null);
			promoterName = ((form1.getPromoterName().equals("0") || form1.getPromoterName().equalsIgnoreCase("Select"))  ?  null : form1.getPromoterName());
			guranteeStatus = (form1.getGuranteeStatus().equals("0") ?  null : form1.getGuranteeStatus());
			promoter_itpan = (form1.getPromoter_itpan() != "" ? form1.getPromoter_itpan() : null);
			
			/*System.out.println("guaranteeStartDate -["+guaranteeStartDate+"] \n guaranteeEndDate -["+guaranteeEndDate+"]\n mliName -["+mliName+"]\n mliId -["+mliId+"]\n"
					+ "npaStatus -["+npaStatus+"]\n cgpan -["+cgpan+"]\n loanAccountNo -["+loanAccountNo+"]\n promoterName -["+promoterName+"]\n promoterName -["+promoterName+"]\n "
							+ "guranteeStatus -["+guranteeStatus+"]\n promoter_itpan -["+promoter_itpan+"] ");*/
			
			callableStmt = conn.prepareCall("{call FUNC_GETGURANTEE_REPORT(?,?,?,?,?,?,?,?,?,?,?,?)}");
			callableStmt.registerOutParameter(1, Types.VARCHAR);
			callableStmt.setString(2, mliName);
			callableStmt.setString(3, cgpan);
			callableStmt.setString(4, promoterName);
			callableStmt.setString(5, guranteeStatus);
			callableStmt.setString(6, promoter_itpan);
			callableStmt.setString(7, mliId);
			callableStmt.setString(8, loanAccountNo);
			java.util.Date date1 = new SimpleDateFormat("dd/MM/yyyy").parse(guaranteeStartDate);
			SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
			String dateVal = formatter.format(date1);
			callableStmt.setString(9, dateVal);
			
			java.util.Date date2 = new SimpleDateFormat("dd/MM/yyyy").parse(guaranteeEndDate);
			SimpleDateFormat formatter1 = new SimpleDateFormat("yyyy-MM-dd");
			String dateVa2 = formatter1.format(date2);
			callableStmt.setString(10, dateVa2);
			callableStmt.setString(11, npaStatus);
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
					npaReportTableData = new NPAReportTableData();
					npaReportTableData.setSrNo(count);
					npaReportTableData.setMLIName(rs.getString("MLI_Name"));//1
					npaReportTableData.setMemberId(rs.getString("MEMBER_ID"));//2
					npaReportTableData.setZoneName(rs.getString("ZONE_NAME"));//3
					npaReportTableData.setSSIUnitName(rs.getString("SSI_UNIT_NAME"));//4
					npaReportTableData.setLoanAccountNo(rs.getString("LOAN_ACCOUNT_NO"));//5
					npaReportTableData.setLoanSanctionDate(rs.getString("LOAN_SANCTION_DATE"));//6
					npaReportTableData.setPromoterName(rs.getString("PROMOTER_NAME"));//7
					npaReportTableData.setPromoterITPAN(rs.getString("Promoter_ITPAN"));//8
					npaReportTableData.setGuaranteeAmt(rs.getString("GUARANTEE_AMT"));//9
					npaReportTableData.setGuaranteeStartDate(rs.getString("GUARATNEE_START_DATE"));//10
					npaReportTableData.setExpiryDate(rs.getString("EXPIRY_DATE"));//11
					npaReportTableData.setGuaranteeStatus(rs.getString("GUARANTEE_STATUS"));//12
					npaReportTableData.setNPADate(rs.getString("NPA_DATE"));//13
					npaReportTableData.setNPAMarkedDate(rs.getString("NPA_Marked_DATE"));//14
					npaReportTableData.setNPAUpgradedOn(rs.getString("NPA_Upgraded_On"));//15
					npaReportTableData.setNPAReason(rs.getString("NPA_Reason"));//16
					npaReportTableData.setNPAOutstandingPrincipalAmount(rs.getString("NPAOutstandingPrincipalAmount"));//17
					npaReportTableData.setCLAIMLodgedDate(rs.getString("CLAIM_LODGED_Date"));//18
					npaReportTableData.setCLAIMStatus(rs.getString("CLAIM_STATUS"));//19
					npaReportTableData.setCLAIMApprovedDate(rs.getString("CLAIM_APPROVED_DATE"));//20
					mliReportList.add(npaReportTableData);
				}
					if(mliReportList.size() == 0){form1.setListCount(1);}
					if(mliReportList.size() == 0){
						request.setAttribute("mliReportList", null);
					}else {
					request.setAttribute("mliReportList", mliReportList);}
			}
			getNPAMarkingDetailSummaryReport(mapping, form,request, response);
		}
		catch (Exception err) {
			try {
				conn.rollback();
			} catch (SQLException ignore) {}
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
		return mapping.findForward("npaMarkingReport");
	}
	
	public ActionForward getSummaryReport(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) throws Exception 
	{
		NPAReportForm form1 = (NPAReportForm) form;
		Connection conn = null;
		CallableStatement callableStmt = null;
		ResultSet rs = null;
		String guaranteeStartDate = "", guaranteeEndDate = "", mliName = "", cgpan = "", promoterName = "",
				guranteeStatus = "", promoter_itpan = "", mliId = "", loanAccountNo = "", npaStatus = "";
		int status = -1, count = 0;
		String errorCode = "", message = "";
		NPAReportTableData npaReportTableData = null;
		ArrayList<NPAReportTableData> mliReportList = new ArrayList<>();
		String fileName = "guaranteeReport";
		try {
			if (conn == null) {
				conn = DBConnection.getConnection(false);
			}

			guaranteeStartDate = request.getParameter("guaranteeStartDate");
			guaranteeEndDate = request.getParameter("guaranteeEndDate");

			mliName = (form1.getMliName() != "" ? form1.getMliName().substring(0, 4) : null);
			mliId = (form1.getMliId() != "" ? form1.getMliId() : null);
			npaStatus = form1.getNpaStatus();
			cgpan = (form1.getCgpan() != "" ? form1.getCgpan() : null);
			loanAccountNo = (form1.getLoanAccountNo() != "" ? form1.getLoanAccountNo() : null);
			promoterName = (form1.getPromoterName().equals("0") ? null : form1.getPromoterName());
			guranteeStatus = (form1.getGuranteeStatus().equals("0") ? null : form1.getGuranteeStatus());
			promoter_itpan = (form1.getPromoter_itpan() != "" ? form1.getPromoter_itpan() : null);

			/*System.out.println("guaranteeStartDate -[" + guaranteeStartDate + "] \n guaranteeEndDate -["
					+ guaranteeEndDate + "]\n mliName -[" + mliName + "]\n mliId -[" + mliId + "]\n" + "npaStatus -["
					+ npaStatus + "]\n cgpan -[" + cgpan + "]\n loanAccountNo -[" + loanAccountNo
					+ "]\n promoterName -[" + promoterName + "]\n promoterName -[" + promoterName + "]\n "
					+ "guranteeStatus -[" + guranteeStatus + "]\n promoter_itpan -[" + promoter_itpan + "] ");*/

			callableStmt = conn.prepareCall("{call FUNC_GETGURANTEE_REPORT(?,?,?,?,?,?,?,?,?,?,?,?)}");
			callableStmt.registerOutParameter(1, Types.VARCHAR);
			callableStmt.setString(2, mliName);
			callableStmt.setString(3, cgpan);
			callableStmt.setString(4, promoterName);
			callableStmt.setString(5, guranteeStatus);
			callableStmt.setString(6, promoter_itpan);
			callableStmt.setString(7, mliId);
			callableStmt.setString(8, loanAccountNo);
			java.util.Date date1 = new SimpleDateFormat("dd/MM/yyyy").parse(guaranteeStartDate);
			SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
			String dateVal = formatter.format(date1);
			callableStmt.setString(9, dateVal);

			java.util.Date date2 = new SimpleDateFormat("dd/MM/yyyy").parse(guaranteeEndDate);
			SimpleDateFormat formatter1 = new SimpleDateFormat("yyyy-MM-dd");
			String dateVa2 = formatter1.format(date2);
			callableStmt.setString(10, dateVa2);
			callableStmt.setString(11, npaStatus);
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
		} catch (Exception err) {
			try {
				conn.rollback();
			} catch (SQLException ignore) {}
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
			} catch (SQLException ignore) {
			}
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
