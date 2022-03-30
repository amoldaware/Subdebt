package com.cgtsi.action;

import java.util.ArrayList;
import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Types;
import java.text.SimpleDateFormat;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import com.cgtsi.actionform.MLIClaimRecoveryData;
import com.cgtsi.actionform.MLIClaimRecoveryForm;
import com.cgtsi.admin.User;
import com.cgtsi.common.Constants;
import com.cgtsi.common.Log;
import com.cgtsi.util.DBConnection;
import com.mysql.cj.protocol.Resultset;
import com.mysql.cj.protocol.ResultsetRows;

public class MLIClaimRecoveryAction extends BaseAction
{
	public ActionForward getClaimRecoveryData(ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception{
		return mapping.findForward("mliClaimRecovery");	}
	
	public ActionForward getUTRUpdateData(ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception{
		return mapping.findForward("mliClaimRecoveryList");	}
	
	public ActionForward getClaimRecoveryDataSave(ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception 
	{
		MLIClaimRecoveryForm form1 = (MLIClaimRecoveryForm) form;
		ArrayList<MLIClaimRecoveryData> mliRecoveryList = new ArrayList<>();
		User user = getUserInformation(request);
		MLIClaimRecoveryData mliRecoveryData = null;
		CallableStatement callableStmt = null;
		ResultSet rs = null;
		Connection conn = null;
		String message = "", bankId = "" , userId = "",errorCode="",claimRefNo = "";
		int status = -1 , count = 0,flag = 1;
		try
		{
			bankId = user.getBankId();
			userId = user.getUserId();
			claimRefNo = request.getParameter("claimRefNo");
			
			if (conn == null) {
				conn = DBConnection.getConnection(false);
			}
			
			if (claimRefNo != null && claimRefNo.trim().length() > 0) 
			{
				callableStmt = conn.prepareCall("{call FUNC_GET_CLAIM_RECOVERY_DETAILS(?,?,?,?,?,?)}");
				callableStmt.registerOutParameter(1, Types.VARCHAR);
				callableStmt.setString(2, bankId);
				callableStmt.setInt(3, flag);
				callableStmt.setString(4, userId);
				callableStmt.setString(5, claimRefNo.trim());
				callableStmt.registerOutParameter(6, Types.VARCHAR);

				callableStmt.execute();
				status = callableStmt.getInt(1);
				errorCode = callableStmt.getString(6);

				if (status == Constants.FUNCTION_FAILURE) {
					Log.log(Log.ERROR, "MLIClaimRecoveryAction", "getClaimRecoveryDataSave()",
							"getClaimRecoveryDataSave returns a 1. Error code is :" + errorCode);
					message = errorCode;

					if (callableStmt != null) {
						callableStmt.close();}
					if (conn != null) {
						conn.rollback();}
				} else if (status == Constants.FUNCTION_SUCCESS) {
					rs = callableStmt.executeQuery();
					while (rs.next()) {
						mliRecoveryData = new MLIClaimRecoveryData();
						count++;
						mliRecoveryData.setSrNo(count);
						mliRecoveryData.setPmr_ref_No(rs.getString("PMR_REF_NO"));
						mliRecoveryData.setCgpan(rs.getString("CGPAN"));
						mliRecoveryData.setMemberId(rs.getString("MEMBER_ID"));
						mliRecoveryData.setClaim_ref_number(rs.getString("CLAIM_REF_NUMBER"));
						mliRecoveryData.setPromoterName(rs.getString("PROMOTERNAME"));
						mliRecoveryData.setUnitName(rs.getString("UNIT_NAME"));
						mliRecoveryData.setPrevious_Recovery(rs.getDouble("Previous_Recovery"));
						mliRecoveryData.setEligibl_Amt_First_Inst(rs.getDouble("ELIGIBL_AMT_FIRST_INST"));
						mliRecoveryData.setType_of_Recovery(rs.getString("TYPE_OF_RECOVERY"));
						mliRecoveryData.setAdditional_Recovery(rs.getDouble("Additional_Recovery"));
						mliRecoveryData.setLegal_Expense_Advocate_Fee(rs.getDouble("Legal_Expense_Advocate_Fee"));
						mliRecoveryData.setLegal_Expense_Court_Fee(rs.getDouble("Legal_Expense_Court_Fee"));
						mliRecoveryData.setDescription_of_Other_Recovery(rs.getString("Description_of_Other_Recovery"));
						mliRecoveryData.setOther_Recovery_Expenses(rs.getDouble("Other_Recovery_Expenses"));
						mliRecoveryData.setTotal_Legal_Expenses(rs.getDouble("Total_Legal_Expenses"));
						mliRecoveryData.setRecoveryAmounttoberemittedtoCGTMSE(rs.getDouble("RecoveryAmounttoberemittedtoCGTMSE"));
						mliRecoveryData.setDateOfrecovery(rs.getString("DateOfrecovery"));
						mliRecoveryData.setTotalAmount(rs.getDouble("TotalAmount"));
						mliRecoveryData.setRec_id(rs.getInt("rec_id"));
						mliRecoveryList.add(mliRecoveryData);
					}
					/*
					 * mliRecoveryList.forEach((mliRecoveryData1) -> {
					 * System.out.println(mliRecoveryData1.getType_of_Recovery()
					 * + "\t" + mliRecoveryData1.getDateOfrecovery()); });
					 */
				}
				request.setAttribute("mliRecoveryList", mliRecoveryList);
			}
		}
		catch(Exception err)
		{
			message = err.getMessage();
			Log.log(2, "MLIClaimRecoveryAction", "getClaimRecoveryData", err.getMessage());
			err.printStackTrace();
			try {
				if (conn != null) {
					conn.rollback();};
			} catch (SQLException ignore) {}
		}
		finally
		{
			try {
				if (rs != null) {
					rs.close();
					rs = null;
				}
				if (callableStmt != null) {
					callableStmt.close();
					callableStmt = null;
				}
				if (conn != null) {
					DBConnection.freeConnection(conn);
				}
			} catch (Exception e) {
				e.printStackTrace();}
		}
		if(message != null && message.trim().length() > 0){form1.setMessage(message);}
		return mapping.findForward("mliClaimRecovery");
	}
	
	public ActionForward getClaimRecoveryDataUpdate(ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception 
	{
		MLIClaimRecoveryForm form1 = (MLIClaimRecoveryForm) form;
		User user = getUserInformation(request);
		ArrayList<MLIClaimRecoveryData> mliRecoveryList = new ArrayList<>();
		MLIClaimRecoveryData mliRecoveryData = null;
		CallableStatement callableStmt = null;
		ResultSet rs = null;
		Connection conn = null;
		String message = "", bankId = "", userId = "", errorCode = "", claimRefNo = "";
		int status = -1, count = 0;
		int flag = 2;
		try 
		{
			bankId = user.getBankId();
			userId = user.getUserId();
			claimRefNo = form1.getClaimRefNo();
			if (conn == null) {
				conn = DBConnection.getConnection(false);
			}
			if (claimRefNo != null && claimRefNo.trim().length() > 0) {
				callableStmt = conn.prepareCall("{call FUNC_GET_CLAIM_RECOVERY_DETAILS(?,?,?,?,?,?)}");
				callableStmt.registerOutParameter(1, Types.VARCHAR);
				callableStmt.setString(2, bankId);
				callableStmt.setInt(3, flag);
				callableStmt.setString(4, userId);
				callableStmt.setString(5, claimRefNo.trim());
				callableStmt.registerOutParameter(6, Types.VARCHAR);

				callableStmt.execute();
				status = callableStmt.getInt(1);
				errorCode = callableStmt.getString(6);

				if (status == Constants.FUNCTION_FAILURE) {
					Log.log(Log.ERROR, "MLIClaimRecoveryAction", "getClaimRecoveryData()",
							"getClaimRecoveryData returns a 1. Error code is :" + errorCode);
					message = errorCode;

					if (callableStmt != null) {
						callableStmt.close();}
					if (conn != null) {
						conn.rollback();}
				} else if (status == Constants.FUNCTION_SUCCESS) {
					rs = callableStmt.executeQuery();
					while (rs.next()) {
						mliRecoveryData = new MLIClaimRecoveryData();
						count++;
						mliRecoveryData.setSrNo(count);
						mliRecoveryData.setRec_id(rs.getInt("REC_ID"));
						mliRecoveryData.setPmr_ref_No(rs.getString("PMR_REF_NO"));
						mliRecoveryData.setCgpan(rs.getString("CGPAN"));
						mliRecoveryData.setMemberId(rs.getString("MEMBER_ID"));
						mliRecoveryData.setClaim_ref_number(rs.getString("CLAIM_REF_NUMBER"));
						mliRecoveryData.setPromoterName(rs.getString("PROMOTERNAME"));
						mliRecoveryData.setUnitName(rs.getString("UNIT_NAME"));
						mliRecoveryData.setTotalAmount(rs.getDouble("TotalAmount"));
						mliRecoveryList.add(mliRecoveryData);
					}
				}
				request.setAttribute("mliRecoveryList", mliRecoveryList);
			}
		} catch (Exception err) {
			message = err.getMessage();
			Log.log(2, "MLIClaimRecoveryAction", "getClaimRecoveryData", err.getMessage());
			err.printStackTrace();
			try {
				if (conn != null) {
					conn.rollback();
				}
			} catch (SQLException ignore) {
			}
		} finally {
			try {
				if (rs != null) {
					rs.close();
					rs = null;
				}
				if (callableStmt != null) {
					callableStmt.close();
					callableStmt = null;
				}
				if (conn != null) {
					DBConnection.freeConnection(conn);
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		if (message != null && message.trim().length() > 0) {
			form1.setMessage(message);}
		return mapping.findForward("mliClaimRecoveryList");
	}
	
	public ActionForward saveClaimRecoveryData(ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception 
	{
		MLIClaimRecoveryForm form1 = (MLIClaimRecoveryForm) form;
		User user = getUserInformation(request);
		ArrayList<MLIClaimRecoveryData> mliRecoveryList = new ArrayList<>();
		MLIClaimRecoveryData mliRecoveryData = null;
		Connection conn = null;
		CallableStatement callableStmt = null;
		String message = "",userId = "" , errorCode="";
		String updateRecovery[] , rec_id[],dateOfrecovery[] ,pmr_ref_No[], claim_ref_number[] , cgpan[], type_of_Recovery[],additional_Recovery[],
		legal_Expense_Advocate_Fee[],legal_Expense_Court_Fee[],description_of_Other_Recovery[],other_Recovery_Expenses[],
		total_Legal_Expenses[],recoveryAmounttoberemittedtoCGTMSE[],totalAmount[];
		String claimRefNo = "";
		int status = -1;
		try
		{
			userId = user.getUserId();
			claimRefNo = form1.getClaimRefNo();
			System.out.println("Claim Reference Number ::" +claimRefNo);
			if (conn == null) {
				conn = DBConnection.getConnection(false);
			}
			updateRecovery = request.getParameterValues("updateRecovery");
			dateOfrecovery = request.getParameterValues("dateOfrecovery");
			pmr_ref_No = request.getParameterValues("pmr_ref_No");
			claim_ref_number = request.getParameterValues("claim_ref_number");
			cgpan = request.getParameterValues("cgpan");
			type_of_Recovery = request.getParameterValues("type_of_Recovery");
			additional_Recovery = request.getParameterValues("additional_Recovery");
			legal_Expense_Advocate_Fee = request.getParameterValues("legal_Expense_Advocate_Fee");
			legal_Expense_Court_Fee = request.getParameterValues("legal_Expense_Court_Fee");
			description_of_Other_Recovery = request.getParameterValues("description_of_Other_Recovery");
			other_Recovery_Expenses = request.getParameterValues("other_Recovery_Expenses");
			total_Legal_Expenses = request.getParameterValues("total_Legal_Expenses");
			recoveryAmounttoberemittedtoCGTMSE = request.getParameterValues("recoveryAmounttoberemittedtoCGTMSE");
			totalAmount = request.getParameterValues("totalAmount");
			rec_id = request.getParameterValues("rec_id");
			
			mliRecoveryData = new MLIClaimRecoveryData();
			for (int j = 0; j < updateRecovery.length; j++) 
			{	
				String[] bits = String.valueOf(updateRecovery[j]).split("-");
				int lastOne = Integer.valueOf(bits[bits.length-1]);
				lastOne = lastOne-1;
				mliRecoveryData.setPmr_ref_No(pmr_ref_No[lastOne]);
				mliRecoveryData.setCgpan(cgpan[lastOne]);
				mliRecoveryData.setClaim_ref_number(claim_ref_number[lastOne]);
				mliRecoveryData.setType_of_Recovery(type_of_Recovery[lastOne]);
				mliRecoveryData.setAdditional_Recovery(Double.valueOf(additional_Recovery[lastOne]));
				mliRecoveryData.setLegal_Expense_Advocate_Fee(Double.valueOf(legal_Expense_Advocate_Fee[lastOne]));
				mliRecoveryData.setLegal_Expense_Court_Fee(Double.valueOf(legal_Expense_Court_Fee[lastOne]));
				mliRecoveryData.setDescription_of_Other_Recovery(description_of_Other_Recovery[lastOne]);
				mliRecoveryData.setOther_Recovery_Expenses(Double.valueOf(other_Recovery_Expenses[lastOne]));
				mliRecoveryData.setTotal_Legal_Expenses(Double.valueOf(total_Legal_Expenses[lastOne]));
				mliRecoveryData.setRecoveryAmounttoberemittedtoCGTMSE(Double.valueOf(recoveryAmounttoberemittedtoCGTMSE[lastOne]));
				mliRecoveryData.setDateOfrecovery(dateOfrecovery[lastOne]);
				mliRecoveryData.setTotalAmount(Double.valueOf(totalAmount[lastOne]));
				mliRecoveryData.setRec_id(Integer.valueOf(rec_id[lastOne]));
				mliRecoveryList.add(mliRecoveryData);
			}
			
			if(mliRecoveryList.size() > 0)
			{
				try {

					for (MLIClaimRecoveryData mliRecoveryData1 : mliRecoveryList) {
						callableStmt = conn.prepareCall("{call FUNC_INSERT_RECOVERY_DETAILS(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)}");

						callableStmt.registerOutParameter(1, Types.VARCHAR);
						callableStmt.setString(2, mliRecoveryData1.getPmr_ref_No());
						callableStmt.setString(3, mliRecoveryData1.getCgpan());
						callableStmt.setString(4, mliRecoveryData1.getClaim_ref_number());
						callableStmt.setString(5, mliRecoveryData1.getType_of_Recovery());
						callableStmt.setDouble(6, mliRecoveryData1.getAdditional_Recovery());
						callableStmt.setDouble(7, mliRecoveryData1.getLegal_Expense_Advocate_Fee());
						callableStmt.setDouble(8, mliRecoveryData1.getLegal_Expense_Court_Fee());
						callableStmt.setString(9, mliRecoveryData1.getDescription_of_Other_Recovery());
						callableStmt.setDouble(10, mliRecoveryData1.getOther_Recovery_Expenses());
						callableStmt.setDouble(11, mliRecoveryData1.getTotal_Legal_Expenses());
						callableStmt.setDouble(12, mliRecoveryData1.getRecoveryAmounttoberemittedtoCGTMSE());
						java.util.Date date1 = new SimpleDateFormat("dd/MM/yyyy").parse(mliRecoveryData1.getDateOfrecovery());
						SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
						String dateVal = formatter.format(date1);
						callableStmt.setString(13, dateVal);
						callableStmt.setDouble(14, mliRecoveryData1.getTotalAmount());
						callableStmt.setString(15, userId);
						callableStmt.setInt(16, mliRecoveryData1.getRec_id());
						callableStmt.registerOutParameter(17, Types.VARCHAR);

						callableStmt.execute();
						status = callableStmt.getInt(1);
						errorCode = callableStmt.getString(17);

						if (status == Constants.FUNCTION_FAILURE) {
							Log.log(Log.ERROR, "CheckerRequestApprovalAction", "savecheckerMLIUserNew()",
									"savecheckerMLIUserNew returns a 1. Error code is :" + errorCode);
							message = errorCode;

							if (callableStmt != null) {
								callableStmt.close();}
							if (conn != null) {
								conn.rollback();}
							form1.setMessage(message);
							return mapping.findForward("mliClaimRecovery");
						} else if (status == Constants.FUNCTION_SUCCESS) 
						{
							message = errorCode;
							form1.setMessage(message);
							getClaimRecoveryDataUpdate(mapping, form,  request, response);
							request.setAttribute("claimRefNo", claimRefNo);
							return mapping.findForward("mliClaimRecoveryList");
						}
					}
					getClaimRecoveryData(mapping, form, request, response);
				} catch (Exception err) {
					message = err.getMessage();
					Log.log(2, "MLIClaimRecoveryAction", "getClaimRecoveryData", err.getMessage());
					err.printStackTrace();
					try {
						if (conn != null) {
							conn.rollback();}
					} catch (SQLException ignore) {}
				}
			}
		}
		catch(Exception err)
		{
			message = err.getMessage();
			Log.log(2, "MLIClaimRecoveryAction", "getClaimRecoveryData", err.getMessage());
			err.printStackTrace();
			try {
				if (conn != null) {
					conn.rollback();}
			} catch (SQLException ignore) {}
		}
		finally
		{
			try {
				if (callableStmt != null) {
					callableStmt.close();callableStmt = null;}
				if (conn != null) {
					DBConnection.freeConnection(conn);}
			} catch (Exception e) {e.printStackTrace();}
		}
		if(message != null && message.trim().length() > 0){
			form1.setMessage(message);
		}
		return mapping.findForward("mliClaimRecovery");	
	}
	
	public ActionForward updateClaimRecoveryData(ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception 
	{
		MLIClaimRecoveryForm form1 = (MLIClaimRecoveryForm) form;
		User user = getUserInformation(request);
		Connection conn = null;
		CallableStatement callableStmt = null;
		ResultSet rs = null;
		String message = ""; 
		String	userId = "" , errorCode="";
		String update_status[], rec_id[] , utr_number[],utr_date[],claim_ref_number[];
		ArrayList<MLIClaimRecoveryData> mliRecoveryList = new ArrayList<>();
		MLIClaimRecoveryData mliRecoveryData = null;
		int status = 0;
		try
		{
			userId = user.getUserId();
			if (conn == null) {
				conn = DBConnection.getConnection(false);
			}
			claim_ref_number = request.getParameterValues("claim_ref_number");
			rec_id = request.getParameterValues("rec_id");
			utr_number = request.getParameterValues("utr_number");
			utr_date = request.getParameterValues("utr_date");
			update_status = request.getParameterValues("status");
			
			for (int j = 0; j < update_status.length; j++) 
			{
				String[] bits = String.valueOf(update_status[j]).split("-");
				int lastOne = Integer.valueOf(bits[bits.length-1]);
				lastOne = lastOne-1;
				mliRecoveryData = new MLIClaimRecoveryData();
				mliRecoveryData.setClaim_ref_number(claim_ref_number[lastOne]);
				mliRecoveryData.setRec_id(Integer.valueOf(rec_id[lastOne]));
				mliRecoveryData.setUtr_number(utr_number[lastOne]);
				mliRecoveryData.setUtr_date(utr_date[lastOne]);
				mliRecoveryList.add(mliRecoveryData);
			}
			
			if(mliRecoveryList.size() > 0)
			{
				try {

					for (MLIClaimRecoveryData mliRecoveryData1 : mliRecoveryList) {
						callableStmt = conn.prepareCall("{call FUNC_Update_RECOVERY_DETAILS(?,?,?,?,?,?,?,?,?,?)}");

						callableStmt.registerOutParameter(1, Types.VARCHAR);
						callableStmt.setString(2, mliRecoveryData1.getClaim_ref_number());
						callableStmt.setString(3, mliRecoveryData1.getUtr_number());
						java.util.Date date1 = new SimpleDateFormat("dd/MM/yyyy").parse(mliRecoveryData1.getUtr_date());
						SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
						String dateVal = formatter.format(date1);
						callableStmt.setString(4, dateVal);
						callableStmt.setString(5, null);
						callableStmt.setString(6, null);
						callableStmt.setInt(7, 1);
						callableStmt.setString(8, userId);
						callableStmt.setInt(9, mliRecoveryData1.getRec_id());
						callableStmt.registerOutParameter(10, Types.VARCHAR);

						callableStmt.execute();
						status = callableStmt.getInt(1);
						errorCode = callableStmt.getString(10);

						if (status == Constants.FUNCTION_FAILURE) {
							Log.log(Log.ERROR, "CheckerRequestApprovalAction", "savecheckerMLIUserNew()",
									"savecheckerMLIUserNew returns a 1. Error code is :" + errorCode);
							message = errorCode;

							if (callableStmt != null) {
								callableStmt.close();}
							if (conn != null) {
								conn.rollback();}
						} else if (status == Constants.FUNCTION_SUCCESS) 
						{
							message = errorCode;
						}
					}
					getClaimRecoveryDataUpdate(mapping, form, request, response);
				} catch (Exception err) {
					message = err.getMessage();
					Log.log(2, "MLIClaimRecoveryAction", "getClaimRecoveryData", err.getMessage());
					err.printStackTrace();
					try {
						if (conn != null) {
							conn.rollback();}
					} catch (SQLException ignore) {
					}
				}
			}
		}
		catch(Exception err)
		{
			message = err.getMessage();
			Log.log(2, "MLIClaimRecoveryAction", "getClaimRecoveryData", err.getMessage());
			err.printStackTrace();
			try {
				if (conn != null) {
					conn.rollback();}
			} catch (SQLException ignore) {}
		}
		finally
		{
			try {
				if (callableStmt != null) {
					callableStmt.close();callableStmt = null;}
				if (conn != null) {
					DBConnection.freeConnection(conn);}
			} catch (Exception e) {e.printStackTrace();}
		}
		if(message != null && message.trim().length() > 0){
			form1.setMessage(message);}
		return mapping.findForward("mliClaimRecoveryList");	
	}
	public ActionForward cancelData(ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		response.sendRedirect(request.getContextPath() + "/subHome.do?method=getSubMenu&menuIcon=AP&mainMenu=Claim Processing");
		return mapping.findForward("");
	}
}
