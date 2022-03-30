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
import com.cgtsi.actionform.MLIRecoveryReportForm;
import com.cgtsi.actionform.MLIRecoveryTableData;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import com.cgtsi.action.BaseAction;
import com.cgtsi.admin.User;
import com.cgtsi.common.Constants;
import com.cgtsi.common.Log;
import com.cgtsi.util.DBConnection;
import com.mysql.cj.protocol.Resultset;

public class ClaimReportAction extends BaseAction
{
		public ActionForward getClaimReport(ActionMapping mapping, ActionForm form,
				HttpServletRequest request, HttpServletResponse response) throws Exception {

			MLIRecoveryReportForm form1 = (MLIRecoveryReportForm) form;
			ArrayList claimReportList = new ArrayList();
			ArrayList promoter = new ArrayList();
			User user = getUserInformation(request);
			Connection conn = null;
			CallableStatement callableStmt = null;
			ResultSet rs = null;
			int status = -1;
			String errorCode = "", message = "", bankId = "", zoneId = "", branchId = "", memberId = "", mliId = "",
					mliName = "", loginType = "", mliLblDtl = "",cgpan = "",claimFromDate="",claimToDate="",claimisSearchClicked="",claimMLIName="";
			try {
				claimFromDate = request.getParameter("claimFromDate");
				claimToDate = request.getParameter("claimToDate");
				request.setAttribute("claimFromDate", claimFromDate);
				request.setAttribute("claimToDate", claimToDate);
				claimMLIName = form1.getClaimMLIName();
				if("0".equals(claimMLIName) || "".equals(claimMLIName)){
					claimMLIName = "";
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
						claimReportList.add(option);
					}
					form1.setClaimLoginType(loginType);
					form1.setClaimReportList(claimReportList);
				}
				
				cgpan = form1.getClaimCGPAN();
				claimisSearchClicked = form1.getClaimisSearchClicked();
				if((cgpan != null && cgpan.trim().length()>0) && (claimMLIName != null && claimMLIName.trim().length()>0))
				{
					String memBankId = (form1.getClaimMLIName() != "0" ? form1.getClaimMLIName().substring(0, 4) : null);
					promoter = getPromoterDetails(cgpan,conn,memBankId);
					form1.setPromoterValues(promoter);
					form1.setClaimCGPAN(cgpan);
				}
				else
				{
					Map option = new HashMap();
					option.put("label", "");
					option.put("value", "Select");
					promoter.add(option);
					form1.setPromoterValues(promoter);
					form1.setClaimCGPAN(cgpan);
				}
			} catch (Exception err) {
				Map option = new HashMap();
				option.put("label", "");
				option.put("value", "Select");
				promoter.add(option);
				form1.setPromoterValues(promoter);
				form1.setClaimReportList(promoter);
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
			return mapping.findForward("claimReport");
		}
		
		public ActionForward getClaimDetailReport(ActionMapping mapping, ActionForm form,
				HttpServletRequest request, HttpServletResponse response) throws Exception 
		{
			MLIRecoveryReportForm form1 = (MLIRecoveryReportForm) form;
			Connection conn = null;
			CallableStatement callableStmt = null;
			ResultSet rs = null;
			String claimFromDate = "", claimToDate = "",claimMLIName = "",claimMLIId="",claimClaimStatus="",
					claimCGPAN="",claimPromoterName="",claimClaimRefNo="",claimPromoterITPAN="";
			int status = -1 , count = 0;
			String errorCode = "", message = "";
			MLIRecoveryTableData mliClaimTableData = null;
			ArrayList<MLIRecoveryTableData> claimReportList = new ArrayList<>();
			try
			{			
				if (conn == null) {
					conn = DBConnection.getConnection(false);
				}
				
				claimFromDate = request.getParameter("claimFromDate");
				claimToDate = request.getParameter("claimToDate");
				request.setAttribute("claimFromDate", claimFromDate);
				request.setAttribute("claimToDate", claimToDate);
				
				claimMLIName = (form1.getClaimMLIName() != "" ? form1.getClaimMLIName().substring(0,4) : null);
				claimMLIId = (form1.getClaimMLIId() != "" ? form1.getClaimMLIId() : null);
				claimClaimStatus = form1.getClaimClaimStatus();
				claimCGPAN = (form1.getClaimCGPAN() != "" ? form1.getClaimCGPAN() : null);
				claimPromoterName = ((form1.getClaimPromoterName().equals("0") || form1.getClaimPromoterName().equalsIgnoreCase("Select"))  ?  null : form1.getClaimPromoterName());
				claimClaimRefNo = (form1.getClaimClaimRefNo() != "" ? form1.getClaimClaimRefNo() : null);
				claimPromoterITPAN = (form1.getClaimPromoterITPAN() != "" ? form1.getClaimPromoterITPAN() : null);
				
				/*System.out.println("claimFromDate -["+claimFromDate+"] \n claimToDate -["+claimToDate+"]\n claimMLIName -["+claimMLIName+"]\n claimMLIId -["+claimMLIId+"]\n"
						+ "claimClaimStatus -["+claimClaimStatus+"]\n claimCGPAN -["+claimCGPAN+"]\n "
						+ "claimPromoterName -["+claimPromoterName+"]\n claimClaimRefNo -["+claimClaimRefNo+"]\n "
						+ "claimPromoterITPAN -["+claimPromoterITPAN+"]\n ");*/
				
				callableStmt = conn.prepareCall("{call FUNC_GETCLAIM_REPORT(?,?,?,?,?,?,?,?,?,?,?)}");
				callableStmt.registerOutParameter(1, Types.VARCHAR);
				callableStmt.setString(2, claimMLIName);
				callableStmt.setString(3, claimCGPAN);
				callableStmt.setString(4, claimPromoterName);
				callableStmt.setString(5, claimClaimStatus);
				callableStmt.setString(6, claimPromoterITPAN);
				callableStmt.setString(7, claimMLIId);
				callableStmt.setString(8, claimClaimRefNo);
				java.util.Date date1 = new SimpleDateFormat("dd/MM/yyyy").parse(claimFromDate);
				SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
				String dateVal = formatter.format(date1);
				callableStmt.setString(9, dateVal);
				
				java.util.Date date2 = new SimpleDateFormat("dd/MM/yyyy").parse(claimToDate);
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
						mliClaimTableData = new MLIRecoveryTableData();
						mliClaimTableData.setClaimSrNo(count);
						mliClaimTableData.setClaimMLIName(rs.getString("MLI_NAME"));//1
						mliClaimTableData.setClaimZoneName(rs.getString("ZONE_NAME"));//2
						mliClaimTableData.setClaimMemberId(rs.getString("MEMBER_ID"));//3
						mliClaimTableData.setClaimCGPAN(rs.getString("CGPAN"));//4
						mliClaimTableData.setClaimRefNumber(rs.getString("CLAIM_REF_NUMBER"));//5
						mliClaimTableData.setClaimPromoterName(rs.getString("PROMOTER_NAME"));//6
						mliClaimTableData.setClaimPromoterITPAN(rs.getString("PROMOTER_ITPAN"));//7
						mliClaimTableData.setClaimSSIUnitName(rs.getString("SSI_UNIT_NAME"));//8
						mliClaimTableData.setClaimDateOfClaimIntiation(rs.getString("Date_of_Claim_Intiation"));//9
						mliClaimTableData.setClaimGuaranteeStatus1(rs.getString("GUARANTEE_STATUS1"));//10
						mliClaimTableData.setClaimGuaranteeAmount(rs.getString("Guarantee_Amount"));//11
						mliClaimTableData.setClaimOutstandingAmountAsOnNPADate(rs.getString("Outstanding_Amount_as_on_NPA_Date"));//12
						mliClaimTableData.setClaimOutstandingAmountAsOnClaimDate(rs.getString("Outstanding_Amount_as_on_Claim_Date"));//13
						mliClaimTableData.setClaimStatus(rs.getString("STATUS"));//14
						mliClaimTableData.setClaimReturnDate(rs.getString("Claim_return_date"));//15
						mliClaimTableData.setClaimReturnReason(rs.getString("Claim_return_reason"));//16
						mliClaimTableData.setClaimLodgementDate(rs.getString("Claim_lodgement_Date"));//17
						mliClaimTableData.setClaimMLICheckerRemark(rs.getString("MLI_Checker_Remark"));//18
						mliClaimTableData.setCGTMSECheckerRemark(rs.getString("CGTMSE_Checker_Remark"));//19
						claimReportList.add(mliClaimTableData);
					}
						
						if(claimReportList.size() == 0){form1.setClaimListCount(1);}
						if(claimReportList.size() == 0){
							request.setAttribute("claimReportList", null);	
						}else {
						request.setAttribute("claimReportList", claimReportList);}
				}
				getClaimReport(mapping, form,request, response);
			}
			catch (Exception err) {
				try {
					conn.rollback();
				} catch (SQLException ignore) {
				}
				message = err.getMessage();
				Log.log(2, "ClaimReportAction", "getClaimDetailReport", err.getMessage());
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
			return mapping.findForward("claimReport");
		}
		
		public ActionForward claimExportToExcel(ActionMapping mapping, ActionForm form,
				HttpServletRequest request, HttpServletResponse response) throws Exception 
		{
			MLIRecoveryReportForm form1 = (MLIRecoveryReportForm) form;
			Connection conn = null;
			CallableStatement callableStmt = null;
			ResultSet rs = null;
			String claimFromDate = "", claimToDate = "",claimMLIName = "",claimMLIId="",claimClaimStatus="",
					claimCGPAN="",claimPromoterName="",claimClaimRefNo="",claimPromoterITPAN="";
			int status = -1 , count = 0;
			String errorCode = "", message = "";
			MLIRecoveryTableData mliClaimTableData = null;
			ArrayList<MLIRecoveryTableData> claimReportList = new ArrayList<>();
			String fileName = "ClaimReport";
			try
			{			
				if (conn == null) {
					conn = DBConnection.getConnection(false);
				}
				
				claimFromDate = request.getParameter("claimFromDate");
				claimToDate = request.getParameter("claimToDate");
				request.setAttribute("claimFromDate", claimFromDate);
				request.setAttribute("claimToDate", claimToDate);
				
				claimMLIName = (form1.getClaimMLIName() != "" ? form1.getClaimMLIName().substring(0,4) : null);
				claimMLIId = (form1.getClaimMLIId() != "" ? form1.getClaimMLIId() : null);
				claimClaimStatus = form1.getClaimClaimStatus();
				claimCGPAN = (form1.getClaimCGPAN() != "" ? form1.getClaimCGPAN() : null);
				claimPromoterName = ((form1.getClaimPromoterName().equals("0") || form1.getClaimPromoterName().equalsIgnoreCase("Select"))  ?  null : form1.getClaimPromoterName());
				claimClaimRefNo = (form1.getClaimClaimRefNo() != "" ? form1.getClaimClaimRefNo() : null);
				claimPromoterITPAN = (form1.getClaimPromoterITPAN() != "" ? form1.getClaimPromoterITPAN() : null);
				
				/*System.out.println("claimFromDate -["+claimFromDate+"] \n claimToDate -["+claimToDate+"]\n claimMLIName -["+claimMLIName+"]\n claimMLIId -["+claimMLIId+"]\n"
						+ "claimClaimStatus -["+claimClaimStatus+"]\n claimCGPAN -["+claimCGPAN+"]\n "
						+ "claimPromoterName -["+claimPromoterName+"]\n claimClaimRefNo -["+claimClaimRefNo+"]\n "
						+ "claimPromoterITPAN -["+claimPromoterITPAN+"]\n ");*/
				
				callableStmt = conn.prepareCall("{call FUNC_GETCLAIM_REPORT(?,?,?,?,?,?,?,?,?,?,?)}");
				callableStmt.registerOutParameter(1, Types.VARCHAR);
				callableStmt.setString(2, claimMLIName);
				callableStmt.setString(3, claimCGPAN);
				callableStmt.setString(4, claimPromoterName);
				callableStmt.setString(5, claimClaimStatus);
				callableStmt.setString(6, claimPromoterITPAN);
				callableStmt.setString(7, claimMLIId);
				callableStmt.setString(8, claimClaimRefNo);
				java.util.Date date1 = new SimpleDateFormat("dd/MM/yyyy").parse(claimFromDate);
				SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
				String dateVal = formatter.format(date1);
				callableStmt.setString(9, dateVal);
				
				java.util.Date date2 = new SimpleDateFormat("dd/MM/yyyy").parse(claimToDate);
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
				getClaimReport(mapping, form,request, response);
			}
			catch (Exception err) {
				try {
					conn.rollback();
				} catch (SQLException ignore) {}
				message = err.getMessage();
				Log.log(2, "ClaimReportAction", "claimExportToExcel", err.getMessage());
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
				Log.log(2, "ClaimReportAction", "getPromoterDetails", err.getMessage());
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
					Log.log(2, "ClaimReportAction", "getPromoterDetails", err.getMessage());
					err.printStackTrace();
				}
			}
			return promoter;
		}
}

