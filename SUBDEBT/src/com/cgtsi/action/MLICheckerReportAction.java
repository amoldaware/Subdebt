package com.cgtsi.action;

import java.io.ByteArrayOutputStream;
import java.io.OutputStream;
import java.sql.CallableStatement;
import java.sql.Connection;
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

import com.cgtsi.actionform.MLICheckerReportForm;
import com.cgtsi.actionform.NPAReportForm;
import com.cgtsi.actionform.MliCheckerTableData;
import com.cgtsi.admin.User;
import com.cgtsi.common.Constants;
import com.cgtsi.common.Log;
import com.cgtsi.util.DBConnection;

public class MLICheckerReportAction extends BaseAction
{
	public ActionForward getNPAMLICheckerSummaryReport(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) throws Exception 
	{
		MLICheckerReportForm form1 = (MLICheckerReportForm) form;
		ArrayList mliLblDtlList = new ArrayList();
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

					Map option = new HashMap();
					option.put("label", mliName);
					option.put("value", mliLblDtl);
					mliLblDtlList.add(option);
				}
				form1.setMliDetails(mliLblDtlList);
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
		return mapping.findForward("mliCheckerReport");
	}
	
	public ActionForward getNPAMLICheckerList(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) throws Exception 
	{
		MLICheckerReportForm form1 = (MLICheckerReportForm) form;
		Connection conn = null;
		CallableStatement callableStmt = null;
		ResultSet rs = null;
		String approvalStatus = "",checkerMLIName = "",checkerMLIId="",promoterName="",guranteeStatus="",promoter_itpan="",mliId="",loanAccountNo="",npaStatus="";
		int status = -1 , count = 0;
		String errorCode = "", message = "";
		MliCheckerTableData mliCheckerTableData = null;
		ArrayList<MliCheckerTableData> mliCheckerList = new ArrayList<>();
		try
		{			
			if (conn == null) {
				conn = DBConnection.getConnection(false);
			}
			
			checkerMLIName = (form1.getCheckerMLIName() != "" ? form1.getCheckerMLIName().substring(0,4) : null);
			checkerMLIId = (form1.getCheckerMLIId() != "" ? form1.getCheckerMLIId() : null);
			approvalStatus = (form1.getApprovalStatus().equals("0") ?  null : form1.getApprovalStatus());
			
			/*System.out.println("checkerMLIName -["+checkerMLIName+"] \n checkerMLIId -["+checkerMLIId+"]\n "
					+ "approvalStatus -["+approvalStatus+"]");*/
			
			callableStmt = conn.prepareCall("{call FUNC_GETCHECKER_DETAILS(?,?,?,?,?)}");
			callableStmt.registerOutParameter(1, Types.VARCHAR);
			callableStmt.setString(2, checkerMLIName);
			callableStmt.setString(3, checkerMLIId);
			callableStmt.setString(4, approvalStatus);
			callableStmt.registerOutParameter(5, Types.VARCHAR);

			callableStmt.execute();
			status = callableStmt.getInt(1);
			errorCode = callableStmt.getString(5);

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
					mliCheckerTableData = new MliCheckerTableData();
					mliCheckerTableData.setSrNo(count);
					mliCheckerTableData.setMLIName(rs.getString("MLI_Name"));//1
					mliCheckerTableData.setMemberId(rs.getString("MEMBER_ID"));//2
					mliCheckerTableData.setZoneName(rs.getString("ZONE_NAME"));//3
					mliCheckerTableData.setCheckerFirstName(rs.getString("checker_First_Name"));//4
					mliCheckerTableData.setCheckerMiddleName(rs.getString("checker_MIDDLE_Name"));//5
					mliCheckerTableData.setCheckerLastName(rs.getString("checker_LAST_Name"));//6
					mliCheckerTableData.setCheckerEmployeeId(rs.getString("checker_EPLOYEEID"));//7
					mliCheckerTableData.setCheckerDesignation(rs.getString("Checker_Designation"));//8
					mliCheckerTableData.setCheckerPhoneNumber(rs.getString("Checker_Phone_number"));//9
					mliCheckerTableData.setCheckerEmail(rs.getString("Checker_Email"));//10
					mliCheckerTableData.setCheckerUserId(rs.getString("Checker_User_ID"));//11
					mliCheckerTableData.setHintAnswer(rs.getString("Hint_Answer"));//12
					mliCheckerTableData.setStatus(rs.getString("STATUS"));//13
					mliCheckerTableData.setCGTMSECheckerRemark(rs.getString("CGTMSE_Checker_Remark"));//14
					mliCheckerList.add(mliCheckerTableData);
				}
					if(mliCheckerList.size() == 0){form1.setMliCheckerlistCount(1);}
					if(mliCheckerList.size() == 0){
						request.setAttribute("mliCheckerList", null);
					}else {
					request.setAttribute("mliCheckerList", mliCheckerList);}
			}
			getNPAMLICheckerSummaryReport(mapping, form,request, response);
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
		return mapping.findForward("mliCheckerReport");
	}
	
	public ActionForward getMLISummaryReport(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) throws Exception 
	{
		MLICheckerReportForm form1 = (MLICheckerReportForm) form;
		Connection conn = null;
		CallableStatement callableStmt = null;
		ResultSet rs = null;
		String approvalStatus = "",checkerMLIName = "",checkerMLIId="",promoterName="",guranteeStatus="",promoter_itpan="",mliId="",loanAccountNo="",npaStatus="";
		int status = -1 , count = 0;
		String errorCode = "", message = "";
		MliCheckerTableData mliCheckerTableData = null;
		ArrayList<MliCheckerTableData> mliCheckerList = new ArrayList<>();
		String fileName = "MLICheckerList";
		try
		{			
			if (conn == null) {
				conn = DBConnection.getConnection(false);
			}
			
			checkerMLIName = (form1.getCheckerMLIName() != "" ? form1.getCheckerMLIName().substring(0,4) : null);
			checkerMLIId = (form1.getCheckerMLIId() != "" ? form1.getCheckerMLIId() : null);
			approvalStatus = (form1.getApprovalStatus().equals("0") ?  null : form1.getApprovalStatus());
			
			/*System.out.println("checkerMLIName -["+checkerMLIName+"] \n checkerMLIId -["+checkerMLIId+"]\n "
					+ "approvalStatus -["+approvalStatus+"]");*/
			
			callableStmt = conn.prepareCall("{call FUNC_GETCHECKER_DETAILS(?,?,?,?,?)}");
			callableStmt.registerOutParameter(1, Types.VARCHAR);
			callableStmt.setString(2, checkerMLIName);
			callableStmt.setString(3, checkerMLIId);
			callableStmt.setString(4, approvalStatus);
			callableStmt.registerOutParameter(5, Types.VARCHAR);

			callableStmt.execute();
			status = callableStmt.getInt(1);
			errorCode = callableStmt.getString(5);

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
		return mapping.findForward("");
	}
}
