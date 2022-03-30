package com.cgtsi.action;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Types;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import com.cgtsi.actionform.MLIUserActionForm;
import com.cgtsi.admin.User;
import com.cgtsi.common.Constants;
import com.cgtsi.common.Log;
import com.cgtsi.util.DBConnection;

import net.sf.json.JSONObject;

public class CheckerRequestApprovalAction extends BaseAction
{
	public ActionForward checkMLIUserNew(ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception 
	{
		MLIUserActionForm form1 = (MLIUserActionForm) form;
		Connection conn = null;
		String query = "";
		ArrayList<User> mliUserData = new ArrayList<>();
		User user = getUserInformation(request);
		CallableStatement callableStmt = null;
		ResultSet rs = null;
		int count = 0;
		int status = -1;
		String errorCode = "", message = "";
		try {
			if (conn == null) {
				conn = DBConnection.getConnection(false);
			}
	
			String bankId = user.getBankId();
			String zoneId = user.getZoneId();
			String branchId = user.getBranchId();
			String memberId = bankId + zoneId + branchId;
			System.out.println("Member id===657===" + memberId);

			callableStmt = conn.prepareCall("{call FUNC_GET_USERRequest_DETAILMLIHO(?,?,?)}");
			callableStmt.registerOutParameter(1, Types.VARCHAR);
			callableStmt.setString(2, memberId);
			callableStmt.registerOutParameter(3, Types.VARCHAR);

			callableStmt.execute();
			status = callableStmt.getInt(1);
			errorCode = callableStmt.getString(3);

			if (status == Constants.FUNCTION_FAILURE) 
			{
				Log.log(Log.ERROR, "CheckerRequestApprovalAction", "checkMLIUserNew()",
						"checkMLIUserNew returns a 1. Error code is :" + errorCode);
				message = errorCode;

				if (callableStmt != null) {
					callableStmt.close();
				}
				if (conn != null) {
					conn.rollback();
				}
			} 
			else if (status == Constants.FUNCTION_SUCCESS) 
			{
				rs = callableStmt.executeQuery();
				while (rs.next()) 
				{
					count++;
					String userId = rs.getString("USR_ID");
					String userName = rs.getString("UserName");
					String designation = rs.getString("USR_DSG_NAME");
					String employeeId = rs.getString("EMPLOYEEID");
					String mliId = rs.getString("MLI_Id");
					String emailId = rs.getString("USR_EMAIL_ID");
					String mobileNo = rs.getString("MOBILE_NO");
					String cgtms_status = rs.getString("CGTMS_STATUS");
					String approval_remark = rs.getString("Approval_Remark");
					String isAdmin = rs.getString("isAdmin");

					User userData = new User();
					userData.setSrlId(count);
					userData.setUserId(userId);
					userData.setUserName(userName);
					userData.setDesignation(designation);
					userData.setEmployee_id(employeeId);
					userData.setMliId(mliId);
					userData.setEmailId(emailId);
					userData.setMobileNo(mobileNo);
					userData.setCgtms_status(cgtms_status);
					userData.setApproval_remark(approval_remark);
					userData.setIsAdmin(isAdmin);

					mliUserData.add(userData);
				}
				request.setAttribute("memberInfo", memberId);
				request.setAttribute("mliUserData", mliUserData);
			}

			/*mliUserData.forEach((userData) -> {
			System.out.println("Data ::: "+userData.getSrlId() + "\t" + userData.getUserName() + "\t" + userData.getDesignation());
			});*/
		} 
		catch (Exception err) 
		{
			message = err.getMessage();
			Log.log(2, "CheckerRequestApprovalAction", "checkMLIUserNew", err.getMessage());
			err.printStackTrace();
			try {
				if(conn != null) {
					conn.rollback();
				}
			} catch (SQLException ignore) {
			}
		} 
		finally 
		{
			try 
			{
				if (rs != null) {
					rs.close();
					rs = null;
				}
				if (callableStmt != null) {
					callableStmt.close();
					callableStmt = null;
				}
				if (conn != null) 
				{
					DBConnection.freeConnection(conn);
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		if(message != null && message.trim().length() > 0){form1.setMessage(message);}
		return mapping.findForward("checkerRequestAppproval");
	}
	
	public ActionForward savecheckerMLIUserNew(ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception 
	{
		MLIUserActionForm form1 = (MLIUserActionForm) form;
		String[] userId;
		String[] cgtms_status;
		String[] approval_remark;
		CallableStatement callableStmt = null;
		int status = -1;
		String errorCode = "", message = "";
		Connection conn = null;
		ArrayList<String> userIdData = new ArrayList<String>();
		ArrayList<String> remarkData = new ArrayList<String>();
		try
		{
			if (conn == null) {
				conn = DBConnection.getConnection(false);
			}
			String userDetailData = form1.getUserDetailData();
			userId = request.getParameterValues("userId");
			cgtms_status = request.getParameterValues("cgtms_status");
			approval_remark = request.getParameterValues("approval_remark");
			
			for(int j = 0; j<approval_remark.length;j++)
			{
				for (j = 0; j < userId.length; j++) {
					if ((approval_remark[j] == "") && (userId[j] != "")) {
						continue;
					}
					userIdData.add(userId[j]);
					remarkData.add(approval_remark[j]);
				}
			}
			Object[] userIdNew = userIdData.toArray();
			Object[] approval_remarkNew = remarkData.toArray();
			
			for(int i=0;i<cgtms_status.length;i++)
			{
				callableStmt = conn.prepareCall("{call FUNCINSERTUSERDETAIL_Update(?,?,?,?,?)}");
				callableStmt.registerOutParameter(1, Types.VARCHAR);
				callableStmt.setString(2, String.valueOf(userIdNew[i]));
				callableStmt.setString(3, String.valueOf(approval_remarkNew[i]));
				if(userDetailData != null && !userDetailData.isEmpty())
				{
					callableStmt.setString(4, userDetailData);
				}
				callableStmt.registerOutParameter(5, Types.VARCHAR);
				callableStmt.execute();
				status = callableStmt.getInt(1);
				errorCode = callableStmt.getString(5);
				
				if (status == Constants.FUNCTION_FAILURE) {
					Log.log(Log.ERROR, "CheckerRequestApprovalAction", "savecheckerMLIUserNew()",
							"savecheckerMLIUserNew returns a 1. Error code is :" + errorCode);
					message =  errorCode;

					if (callableStmt != null) {
						callableStmt.close();
					}
					if(conn != null) {
						conn.rollback();
					}
				} else if (status == Constants.FUNCTION_SUCCESS) {
					message = errorCode;
				}
			}
			checkMLIUserNew(mapping, form, request,response);
		}
		catch(Exception err)
		{
			message = err.getMessage();
			Log.log(2, "CheckerRequestApprovalAction", "savecheckerMLIUserNew", err.getMessage());
			err.printStackTrace();
			try {
				if(conn != null) {
					conn.rollback();
				}
			} catch (SQLException ignore) {
			}
		}
		finally
		{
			try {
				if (callableStmt != null) {
					callableStmt.close();
					callableStmt = null;
				}
				if (conn != null) 
				{
					DBConnection.freeConnection(conn);
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		if(message != null && message.trim().length() > 0){form1.setMessage(message);}
		return mapping.findForward("checkerRequestAppproval");
	}
}
