package com.cgtsi.action;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Types;
import java.util.HashMap;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.beanutils.BeanUtils;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.action.DynaActionForm;

import com.cgtsi.actionform.MLIUserActionForm;
import com.cgtsi.admin.AdminDAO;
import com.cgtsi.admin.AdminHelper;
import com.cgtsi.admin.Administrator;
import com.cgtsi.admin.PasswordManager;
import com.cgtsi.admin.User;
import com.cgtsi.common.Constants;
import com.cgtsi.common.DatabaseException;
import com.cgtsi.common.Log;
import com.cgtsi.common.MailerException;
import com.cgtsi.common.NoUserFoundException;
import com.cgtsi.registration.Registration;
import com.cgtsi.util.DBConnection;

import sun.text.resources.cldr.es.FormatData_es_419;

public class MLIUserAction extends BaseAction {
	public ActionForward showMLIUserNew(ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		MLIUserActionForm form1 = (MLIUserActionForm) form;
		try {
			String userId = form1.getUserId();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return mapping.findForward("showMLIUserNewPage");
	}

	public ActionForward saveMLIUserNew(ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		Connection conn = null;
		MLIUserActionForm form1 = (MLIUserActionForm) form;
		MLIUser mliUser = new MLIUser();
		User user = new User();
		String message = "", bankId = "", zoneId = "", branchId = "", memberId = "";
		HashMap<String,String> addUserData = new HashMap<>();
		String userId = null;
		try 
		{
			if (conn == null) 
			{
				conn = DBConnection.getConnection(false);
			}
			userId = form1.getUserId();
			user.setFirstName(form1.getFirstName().toUpperCase());// 1
			user.setMiddleName(form1.getMiddleName()!=""?form1.getMiddleName().toUpperCase():form1.getMiddleName());// 2
			user.setLastName(form1.getLastName().toUpperCase());// 3
			user.setDesignation(form1.getDesignation().toUpperCase());// 4
			user.setBankId(form1.getBankId());// 5
			user.setZoneId(form1.getZoneId());// 6
			user.setBranchId(form1.getBranchId());// 7
			user.setEmailId(form1.getEmailId());// 8
			user.setUser_role(form1.getUser_role());// 9
			user.setEmployee_id(form1.getEmployee_id());// 10
			user.setMobileNo(form1.getMobile_number());// 11
			user.setUserId(form1.getUserId());// 12
			user.setUserType(form1.getUserType());

			bankId = (String) form1.getBankId();
			zoneId = (String) form1.getZoneId();
			branchId = (String) form1.getBranchId();
			memberId = bankId + zoneId + branchId;

			/*System.out.println(":::bankId IS [" + bankId + "] \t :::ZoneId IS [" + zoneId + "] \t :::branchId IS ["
					+ branchId + "] \t ::: memberId IS [" + memberId + "]   form1.getUserType() ["+form1.getUserType()+"]");*/
			
			form1.setFirstName(form1.getFirstName());
			form1.setMiddleName(form1.getMessage());
			form1.setLastName(form1.getLastName());
			form1.setDesignation(form1.getDesignation());
			form1.setBankId(form1.getBankId());
			form1.setZoneId(form1.getZoneId());
			form1.setBranchId(form1.getBranchId());
			
			
			Registration registration = new Registration();
			registration.getMemberDetails(bankId, zoneId, branchId);
			User creatingUser = getUserInformation(request);
			String createdBy = creatingUser.getUserId();
			
			if(userId == null || userId.trim().length() == 0)
			{
				String loggedInUserBankId = creatingUser.getBankId();
				String loggedInUserZoneId = creatingUser.getZoneId();
				String loggedInUserBranchId = creatingUser.getBranchId();

				if ((loggedInUserBankId.equals("0000")) || (loggedInUserBankId.equals(bankId))
					|| ((loggedInUserZoneId.equals("0000"))
					|| (loggedInUserZoneId.equals(zoneId)) && (loggedInUserBranchId.equals("0000"))
					|| (loggedInUserBranchId.equals(branchId)))) 
				{
					try {
						userId = mliUser.createUser(createdBy, user, true);
						if(userId != null && !userId.isEmpty()){
							message = "User Creation request saved succesfully";
							form1.setUserId(userId);
						}
					} catch (MailerException mailerException) {
						if(conn != null){conn.rollback();}
						message = mailerException.getMessage();
					}
				} 
				else 
				{
					message = "Cannot create users for other MLI";
				}
				user = null;
				creatingUser = null;
				Log.log(4, "AdministrationAction", "addMLIUser", "Exited");
			}
		    else
			{
				try 
				{
					addUserData = mliUser.addUser(createdBy, user);
					if(addUserData != null)
					{
						for(String key : addUserData.keySet())
						{
							userId = key; 
							message = addUserData.get(key);
						}
					}
				} 
				catch (Exception mailerException) 
				{
					if(conn != null){conn.rollback();}
					message = mailerException.getMessage();
					mailerException.printStackTrace();
					//System.out.println("Exception is ::::" + mailerException.getMessage());
				}
			}
			
		} catch (Exception err) 
		{
			if(conn != null){conn.rollback();}
			message = err.getMessage();
			Log.log(2, "MLIUserAction", "WriteToFile", err.getMessage());
			err.printStackTrace();
		} finally {
			try {
				if (conn != null) {
					DBConnection.freeConnection(conn);
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		if (message != null && message.trim().length() >0) {
			form1.setMessage(message);
		}
		return mapping.findForward("showMLIUserNewPage");
	}

	public ActionForward getUserInformation(ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		Connection conn = null;
		MLIUserActionForm form1 = (MLIUserActionForm) form;
		String message = "", userId = "", errorCode = "";
		CallableStatement callableStmt = null;
		ResultSet rs = null;
		int status = -1;
		try 
		{
			if (conn == null) {
				conn = DBConnection.getConnection(false);
			}
			userId = form1.getUserId();
			if (userId != null && !userId.isEmpty()) {
				callableStmt = conn.prepareCall("{call FUNC_GET_USER_DETAILMLIHO(?,?,?)}");
				callableStmt.registerOutParameter(1, Types.VARCHAR);
				callableStmt.setString(2, userId);
				callableStmt.registerOutParameter(3, Types.VARCHAR);

				callableStmt.execute();
				status = callableStmt.getInt(1);
				errorCode = callableStmt.getString(3);
				if (status == Constants.FUNCTION_FAILURE) {
					Log.log(Log.ERROR, "MLIUserAction", "getUserInformation()","getUserInformation returns a 1. Error code is :" + errorCode);

					if (callableStmt != null) {
						callableStmt.close();
					}
					if(conn != null){conn.rollback();}
					message = errorCode;
				} 
				else if (status == Constants.FUNCTION_SUCCESS) 
				{
					rs = callableStmt.executeQuery();
					if (rs.next()) {
						System.out.println("First Name ::" + rs.getString("USR_FIRST_NAME") +""+ rs.getString("MOBILE_NO"));
						form1.setFirstName(rs.getString("USR_FIRST_NAME"));
						form1.setMiddleName(rs.getString("USR_MIDDLE_NAME"));
						form1.setLastName(rs.getString("USR_LAST_NAME"));
						form1.setDesignation(rs.getString("USR_DSG_NAME"));
						form1.setBankId(rs.getString("MEM_BNK_ID"));
						form1.setZoneId(rs.getString("MEM_ZNE_ID"));
						form1.setBranchId(rs.getString("MEM_BRN_ID"));
						form1.setEmailId(rs.getString("USR_EMAIL_ID"));
						form1.setUser_role(rs.getString("ROLENAME"));
						form1.setMobile_number(rs.getString("MOBILE_NO"));
						form1.setEmployee_id(rs.getString("EMPLOYEEID"));
						form1.setUserId(rs.getString("USR_ID"));
					} 
					else 
					{
						message = "No Data Found Against Entered UserId";
					}
				}
			}
		} catch (Exception err) {
			if(conn != null){conn.rollback();}
			message = err.getMessage();
			Log.log(2, "MLIUserAction", "WriteToFile", err.getMessage());
			err.printStackTrace();
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
		if (message != null && message.trim().length()>0) {form1.setMessage(message);}
		return mapping.findForward("showMLIUserNewPage");
	}
}
