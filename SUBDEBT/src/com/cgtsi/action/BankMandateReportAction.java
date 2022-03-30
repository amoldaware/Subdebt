package com.cgtsi.action;

import java.io.ByteArrayOutputStream;
import java.io.OutputStream;
import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Types;
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

import com.cgtsi.actionform.MLICheckerReportForm;
import com.cgtsi.actionform.MliCheckerTableData;
import com.cgtsi.admin.User;
import com.cgtsi.common.Constants;
import com.cgtsi.common.Log;
import com.cgtsi.util.DBConnection;

public class BankMandateReportAction extends BaseAction
{
	public ActionForward getBankMandateSummaryReport(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) throws Exception 
	{
		MLICheckerReportForm form1 = (MLICheckerReportForm) form;
		ArrayList bankMandateDtlList = new ArrayList();
		ArrayList promoter = new ArrayList();
		User user = getUserInformation(request);
		Connection conn = null;
		CallableStatement callableStmt = null;
		ResultSet rs = null;
		int status = -1;
		String errorCode = "", message = "", bankId = "", zoneId = "", branchId = "", memberId = "", mliId = "",
				mliName = "", loginType = "", mliLblDtl = "",cgpan = "",guaranteeStartDate="",guaranteeEndDate="";
		try {
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
					form1.setBankMandateLoginType(loginType);

					Map option = new HashMap();
					option.put("label", mliName);
					option.put("value", mliLblDtl);
					bankMandateDtlList.add(option);
					
				}
				form1.setBankMandateDtls(bankMandateDtlList);
			}
		} catch (Exception err) {
			Map option = new HashMap();
			option.put("label", "");
			option.put("value", "Select");
			bankMandateDtlList.add(option);
			try {
				conn.rollback();
			} catch (SQLException ignore) {}
			message = err.getMessage();
			Log.log(2, "BankMandateReportAction", "getBankMandateSummaryReport", err.getMessage());
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
		return mapping.findForward("bankMandateReport");
	}
	
	public ActionForward getBankMandateList(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) throws Exception 
	{
		MLICheckerReportForm form1 = (MLICheckerReportForm) form;
		Connection conn = null;
		CallableStatement callableStmt = null;
		ResultSet rs = null;
		String bankMandateMLIName = "",bankMandateMLIId = "";
		int status = -1 , count = 0;
		String errorCode = "", message = "";
		MliCheckerTableData bankMandateTableData = null;
		ArrayList<MliCheckerTableData> bankMandateList = new ArrayList<>();
		try
		{			
			if (conn == null) {
				conn = DBConnection.getConnection(false);
			}
			
			bankMandateMLIName = (form1.getBankMandateMLIName() != "" ? form1.getBankMandateMLIName().substring(0,4) : null);
			bankMandateMLIId = (form1.getBankMandateMLIId() != "" ? form1.getBankMandateMLIId() : null);
			
			callableStmt = conn.prepareCall("{call FUNC_BANKMANDATE_DETAILS(?,?,?,?)}");
			callableStmt.registerOutParameter(1, Types.VARCHAR);
			callableStmt.setString(2, bankMandateMLIName);
			callableStmt.setString(3, bankMandateMLIId);
			callableStmt.registerOutParameter(4, Types.VARCHAR);

			callableStmt.execute();
			status = callableStmt.getInt(1);
			errorCode = callableStmt.getString(4);

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
					bankMandateTableData = new MliCheckerTableData();
					bankMandateTableData.setBankMandateSrNo(count);
					bankMandateTableData.setBankMandateMemberId(rs.getString("MEMBER_ID"));
					bankMandateTableData.setBankMandateMLIName(rs.getString("MLI_NAME"));
					bankMandateTableData.setBankMandateZoneName(rs.getString("ZONE_NAME"));
					bankMandateTableData.setBankMandateContactNo(rs.getString("CONTACT_NO"));
					bankMandateTableData.setBankMandateMobileNo(rs.getString("MOBILE_NO"));
					bankMandateTableData.setBankMandateEmailId(rs.getString("EMAIL_ID"));
					bankMandateTableData.setBankMandateNameOfBeneficiary(rs.getString("NAMEOFBENEFICIARY"));
					bankMandateTableData.setBankMandateBeneficiaryBankName(rs.getString("BENEFICIARY_BANK_NAME"));
					bankMandateTableData.setBankMandateAccountType(rs.getString("ACCOUNT_TYPE"));
					bankMandateTableData.setBankMandateBranchCode(rs.getString("BRANCH_CODE"));
					bankMandateTableData.setBankMandateMICRCode(rs.getString("MICR_CODE"));
					bankMandateTableData.setBankMandateAccountNo(rs.getString("ACCOUNT_NO"));
					bankMandateTableData.setBankMandateRTGSNo(rs.getString("RTGS_NO"));
					bankMandateTableData.setBankMandateNEFTNo(rs.getString("NEFT_NO"));
					bankMandateTableData.setBankMandateCheckerStatus(rs.getString("CHECKER_STATUS"));
					bankMandateTableData.setBankMandateCGTMSCStatus(rs.getString("CGTMSC_Status"));
					bankMandateTableData.setBankMandateCGTMSCRemark(rs.getString("CGTMSC_Remark"));
					bankMandateList.add(bankMandateTableData);
				}
					if(bankMandateList.size() == 0){form1.setBankMandatelistCount(1);}
					if(bankMandateList.size() == 0){
						request.setAttribute("bankMandateList", null);
					}
					else
					{
						request.setAttribute("bankMandateList", bankMandateList);
					}
			}
			getBankMandateSummaryReport(mapping, form,request, response);
		}
		catch (Exception err) {
			try {
				conn.rollback();
			} catch (SQLException ignore) {}
			message = err.getMessage();
			Log.log(2, "BankMandateReportAction", "getBankMandateList", err.getMessage());
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
		return mapping.findForward("bankMandateReport");
	}
	
	
	public ActionForward getBankMandateEXLReport(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) throws Exception 
	{
		MLICheckerReportForm form1 = (MLICheckerReportForm) form;
		Connection conn = null;
		CallableStatement callableStmt = null;
		ResultSet rs = null;
		String bankMandateMLIName = "",bankMandateMLIId = "";
		int status = -1 , count = 0;
		String errorCode = "", message = "";
		MliCheckerTableData bankMandateTableData = null;
		ArrayList<MliCheckerTableData> bankMandateList = new ArrayList<>();
		String fileName = "BankMandateReport";
		try
		{			
			if (conn == null) {
				conn = DBConnection.getConnection(false);
			}
			
			bankMandateMLIName = (form1.getBankMandateMLIName() != "" ? form1.getBankMandateMLIName().substring(0,4) : null);
			bankMandateMLIId = (form1.getBankMandateMLIId() != "" ? form1.getBankMandateMLIId() : null);
						
			callableStmt = conn.prepareCall("{call FUNC_BANKMANDATE_DETAILS(?,?,?,?)}");
			callableStmt.registerOutParameter(1, Types.VARCHAR);
			callableStmt.setString(2, bankMandateMLIName);
			callableStmt.setString(3, bankMandateMLIId);
			callableStmt.registerOutParameter(4, Types.VARCHAR);

			callableStmt.execute();
			status = callableStmt.getInt(1);
			errorCode = callableStmt.getString(4);

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
			getBankMandateSummaryReport(mapping, form,request, response);
		}
		catch (Exception err) {
			try {
				conn.rollback();
			} catch (SQLException ignore) {}
			message = err.getMessage();
			Log.log(2, "BankMandateReportAction", "getBankMandateEXLReport", err.getMessage());
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
			form1.setMessage(message);
		}
		return mapping.findForward("");
	}
}
