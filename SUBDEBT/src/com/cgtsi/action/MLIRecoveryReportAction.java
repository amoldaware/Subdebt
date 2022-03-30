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

import com.cgtsi.actionform.MLIRecoveryReportForm;
import com.cgtsi.actionform.NPAReportForm;
import com.cgtsi.actionform.MLIRecoveryTableData;
import com.cgtsi.admin.User;
import com.cgtsi.common.Constants;
import com.cgtsi.common.Log;
import com.cgtsi.util.DBConnection;
import com.mysql.cj.protocol.Resultset;

public class MLIRecoveryReportAction extends BaseAction
{
	public ActionForward getRecoveryReport(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) throws Exception {

		MLIRecoveryReportForm form1 = (MLIRecoveryReportForm) form;
		ArrayList mliRecList = new ArrayList();
		ArrayList promoter = new ArrayList();
		User user = getUserInformation(request);
		Connection conn = null;
		CallableStatement callableStmt = null;
		ResultSet rs = null;
		int status = -1;
		String errorCode = "", message = "", bankId = "", zoneId = "", branchId = "", memberId = "", mliId = "",
				mliName = "", loginType = "", mliLblDtl = "",cgpan = "",recFromDate="",recToDate="",isSearchClicked="",recmliName="";
		try {
			recFromDate = request.getParameter("recFromDate");
			recToDate = request.getParameter("recToDate");
			request.setAttribute("recFromDate", recFromDate);
			request.setAttribute("recToDate", recToDate);
			recmliName = form1.getRecMLIName();
			if("0".equals(recmliName) || "".equals(recmliName)){
				recmliName = "";
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
					mliRecList.add(option);
				}
				form1.setRecLoginTypel(loginType);
				form1.setMliRecList(mliRecList);
			}
			
			cgpan = form1.getRecCGPAN();
			isSearchClicked = form1.getIsSearchClicked();
			if((cgpan != null && cgpan.trim().length()>0) && (recmliName != null && recmliName.trim().length()>0))
			{
				String memBankId = (form1.getRecMLIName() != "0" ? form1.getRecMLIName().substring(0, 4) : null);
				promoter = getPromoterDetails(cgpan,conn,memBankId);
				form1.setPromoterValues(promoter);
				form1.setRecCGPAN(cgpan);
			}
			else
			{
				Map option = new HashMap();
				option.put("label", "");
				option.put("value", "Select");
				promoter.add(option);
				form1.setPromoterValues(promoter);
				form1.setRecCGPAN(cgpan);
			}
		} catch (Exception err) {
			Map option = new HashMap();
			option.put("label", "");
			option.put("value", "Select");
			promoter.add(option);
			form1.setPromoterValues(promoter);
			form1.setMliRecList(promoter);
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
			form1.setMessage(message);
		}
		return mapping.findForward("recoveryReport");
	}
	
	public ActionForward getClaimRecoveryDetailReport(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) throws Exception 
	{
		MLIRecoveryReportForm form1 = (MLIRecoveryReportForm) form;
		Connection conn = null;
		CallableStatement callableStmt = null;
		ResultSet rs = null;
		String recFromDate = "", recToDate = "",recMLIName = "",recCGPAN="",recPromoterName="",
				recClaimStatus="",recMLIId="",recClaimRefNo="",recPromoterITPAN="";
		int status = -1 , count = 0;
		String errorCode = "", message = "";
		MLIRecoveryTableData mliRecoveryTableData = null;
		ArrayList<MLIRecoveryTableData> recReportList = new ArrayList<>();
		try
		{			
			if (conn == null) {
				conn = DBConnection.getConnection(false);
			}
			
			recFromDate = request.getParameter("recFromDate");
			recToDate = request.getParameter("recToDate");
			request.setAttribute("recFromDate", recFromDate);
			request.setAttribute("recToDate", recToDate);
			
			recMLIName = (form1.getRecMLIName() != "" ? form1.getRecMLIName().substring(0,4) : null);
			recMLIId = (form1.getRecMLIId() != "" ? form1.getRecMLIId() : null);
			recClaimStatus = form1.getRecClaimStatus();
			recCGPAN = (form1.getRecCGPAN() != "" ? form1.getRecCGPAN() : null);
			recPromoterName = ((form1.getRecPromoterName().equals("0") || form1.getRecPromoterName().equalsIgnoreCase("Select"))  ?  null : form1.getRecPromoterName());
			recClaimRefNo = (form1.getRecClaimRefNo() != "" ? form1.getRecClaimRefNo() : null);
			recPromoterITPAN = (form1.getRecPromoterITPAN() != "" ? form1.getRecPromoterITPAN() : null);
			
			/*System.out.println("recFromDate -["+recFromDate+"] \n recToDate -["+recToDate+"]\n mliName -["+recMLIName+"]\n mliId -["+recMLIId+"]\n"
					+ "recClaimStatus -["+recClaimStatus+"]\n recCGPAN -["+recCGPAN+"]\n recPromoterName -["+recPromoterName+"]\n "
					+ "recClaimRefNo -["+recClaimRefNo+"]\n recPromoterITPAN -["+recPromoterITPAN+"]\n ");*/
			
			callableStmt = conn.prepareCall("{call FUNC_GETRECOVERY_REPORT(?,?,?,?,?,?,?,?,?,?,?)}");
			callableStmt.registerOutParameter(1, Types.VARCHAR);
			callableStmt.setString(2, recMLIName);
			callableStmt.setString(3, recCGPAN);
			callableStmt.setString(4, recPromoterName);
			callableStmt.setString(5, recClaimStatus);
			callableStmt.setString(6, recPromoterITPAN);
			callableStmt.setString(7, recMLIId);
			callableStmt.setString(8, recClaimRefNo);
			java.util.Date date1 = new SimpleDateFormat("dd/MM/yyyy").parse(recFromDate);
			SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
			String dateVal = formatter.format(date1);
			callableStmt.setString(9, dateVal);
			
			java.util.Date date2 = new SimpleDateFormat("dd/MM/yyyy").parse(recToDate);
			SimpleDateFormat formatter1 = new SimpleDateFormat("yyyy-MM-dd");
			String dateVa2 = formatter1.format(date2);
			callableStmt.setString(10, dateVa2);
			callableStmt.registerOutParameter(11, Types.VARCHAR);

			callableStmt.execute();
			status = callableStmt.getInt(1);
			errorCode = callableStmt.getString(11);
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
					mliRecoveryTableData = new MLIRecoveryTableData();
					mliRecoveryTableData.setRecSrNo(count);
					mliRecoveryTableData.setRecMLIName(rs.getString("MLI_NAME"));//1
					mliRecoveryTableData.setRecMemberId(rs.getString("MEMBER_ID"));//2
					mliRecoveryTableData.setRecCgpan(rs.getString("CGPAN"));//3
					mliRecoveryTableData.setRecClaimRefNumber(rs.getString("CLAIM_REF_NUMBER"));//4
					mliRecoveryTableData.setRecPromoterName(rs.getString("PROMOTER_NAME"));//5
					mliRecoveryTableData.setRecPromoterITPAN(rs.getString("PROMOTER_ITPAN"));//6
					mliRecoveryTableData.setRecSSIUnitName(rs.getString("SSI_UNIT_NAME"));//7
					mliRecoveryTableData.setRecPayId(rs.getString("PAY_ID"));//8
					mliRecoveryTableData.setRecTypeOfRecovery(rs.getString("TYPE_OF_RECOVERY"));//9
					mliRecoveryTableData.setRecAmount(rs.getString("AMOUNT"));//10
					mliRecoveryTableData.setRecPaymentInitiateDate(rs.getString("PAYMENT_INITIATE_DATE"));//11
					mliRecoveryTableData.setRecPaymentDate(rs.getString("PAYMENT_DATE"));//12
					mliRecoveryTableData.setRecPaymentCreditedDate(rs.getString("PAYMENT_CREDITED_DATE"));//13
					mliRecoveryTableData.setRecAppropereatedStatus(rs.getString("APPROPEREATED_STATUS"));//14
					mliRecoveryTableData.setRecTotalRemettedAmount(rs.getString("TOTAL_REMETTED_AMOUNT"));//15
					recReportList.add(mliRecoveryTableData);
				}
					if(recReportList.size() == 0){form1.setRecListCount(1);}
					if(recReportList.size() == 0){
						request.setAttribute("recReportList", null);	
					}else {
					request.setAttribute("recReportList", recReportList);}
			}
			getRecoveryReport(mapping, form,request, response);
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
			form1.setMessage(message);
		}
		return mapping.findForward("recoveryReport");
	}
	public ActionForward getClaimRecoveryExportToExcel(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) throws Exception 
	{
		MLIRecoveryReportForm form1 = (MLIRecoveryReportForm) form;
		Connection conn = null;
		CallableStatement callableStmt = null;
		ResultSet rs = null;
		String recFromDate = "", recToDate = "",recMLIName = "",recCGPAN="",recPromoterName="",
				recClaimStatus="",recMLIId="",recClaimRefNo="",recPromoterITPAN="";
		int status = -1 , count = 0;
		String errorCode = "", message = "";
		MLIRecoveryTableData npaReportTableData = null;
		ArrayList<MLIRecoveryTableData> recReportList = new ArrayList<>();
		String fileName = "RecoveryReportList";
		try
		{			
			if (conn == null) {
				conn = DBConnection.getConnection(false);
			}
			
			recFromDate = request.getParameter("recFromDate");
			recToDate = request.getParameter("recToDate");
			request.setAttribute("recFromDate", recFromDate);
			request.setAttribute("recToDate", recToDate);
			
			recMLIName = (form1.getRecMLIName() != "" ? form1.getRecMLIName().substring(0,4) : null);
			recMLIId = (form1.getRecMLIId() != "" ? form1.getRecMLIId() : null);
			recClaimStatus =((form1.getRecClaimStatus().equals("0") || form1.getRecClaimStatus().equalsIgnoreCase("Select"))  ?  null : form1.getRecClaimStatus());
			recCGPAN = (form1.getRecCGPAN() != "" ? form1.getRecCGPAN() : null);
			recPromoterName = ((form1.getRecPromoterName().equals("0") || form1.getRecPromoterName().equalsIgnoreCase("Select"))  ?  null : form1.getRecPromoterName());
			recClaimRefNo = (form1.getRecClaimRefNo() != "" ? form1.getRecClaimRefNo() : null);
			recPromoterITPAN = (form1.getRecPromoterITPAN() != "" ? form1.getRecPromoterITPAN() : null);
			
			/*System.out.println("recFromDate -["+recFromDate+"] \n recToDate -["+recToDate+"]\n mliName -["+recMLIName+"]\n mliId -["+recMLIName+"]\n"
					+ "recClaimStatus -["+recClaimStatus+"]\n recCGPAN -["+recCGPAN+"]\n recPromoterName -["+recPromoterName+"]\n "
					+ "recClaimRefNo -["+recClaimRefNo+"]\n recPromoterITPAN -["+recPromoterITPAN+"]\n ");*/
			
			callableStmt = conn.prepareCall("{call FUNC_GETRECOVERY_REPORT(?,?,?,?,?,?,?,?,?,?,?)}");
			callableStmt.registerOutParameter(1, Types.VARCHAR);
			callableStmt.setString(2, recMLIName);
			callableStmt.setString(3, recCGPAN);
			callableStmt.setString(4, recPromoterName);
			callableStmt.setString(5, recClaimStatus);
			callableStmt.setString(6, recPromoterITPAN);
			callableStmt.setString(7, recMLIId);
			callableStmt.setString(8, recClaimRefNo);
			java.util.Date date1 = new SimpleDateFormat("dd/MM/yyyy").parse(recFromDate);
			SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
			String dateVal = formatter.format(date1);
			callableStmt.setString(9, dateVal);
			
			java.util.Date date2 = new SimpleDateFormat("dd/MM/yyyy").parse(recToDate);
			SimpleDateFormat formatter1 = new SimpleDateFormat("yyyy-MM-dd");
			String dateVa2 = formatter1.format(date2);
			callableStmt.setString(10, dateVa2);
			callableStmt.registerOutParameter(11, Types.VARCHAR);

			callableStmt.execute();
			status = callableStmt.getInt(1);
			errorCode = callableStmt.getString(11);
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
			getRecoveryReport(mapping, form,request, response);
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
			form1.setMessage(message);
		}
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
