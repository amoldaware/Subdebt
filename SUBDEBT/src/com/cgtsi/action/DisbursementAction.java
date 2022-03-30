package com.cgtsi.action;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Types;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import com.cgtsi.actionform.DisbursementActionForm;
import com.cgtsi.actionform.PopuLateData;
import com.cgtsi.admin.User;
import com.cgtsi.common.Constants;
import com.cgtsi.common.Log;
import com.cgtsi.util.DBConnection;
import com.mysql.cj.protocol.Resultset;

public class DisbursementAction extends BaseAction 
{
	public ActionForward getDisbursementDetail(ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		Connection conn = null;
		String message = null;
		PreparedStatement pstmt = null;
		Resultset rs = null;
		DisbursementActionForm form1 = (DisbursementActionForm) form;
		String cgpan = form1.getCgpan();
		User user = (User) getUserInformation(request);
		//System.out.println("user" + user);
		String bankid = (user.getBankId()).trim();
		ArrayList promoter = new ArrayList();
		try {
			if (cgpan != null && !cgpan.isEmpty()) {
				try {
					if (conn == null) {
						conn = DBConnection.getConnection(false);
					}
					promoter = getPromoterDetails(cgpan, conn, bankid);
					if (promoter.size() == 0) {
						message = "Either CGPAN not pertaining to logged in User,Bank,MLI or No Promoter Details Found!!";
					}
					form1.setPromoterValues(promoter);
					form1.setCgpan(cgpan);
					form1.setIsSearchClicked("");

				} catch (Exception err) {
					Log.log(2, "DisbursementAction", "WriteToFile", err.getMessage());
					err.printStackTrace();
					try {
						conn.rollback();
					} catch (SQLException ignore) {
					}
					form1.setMessage(err.getMessage());
					return mapping.findForward("disbursementDetailPage");
				}
			} else {
				Map option = new HashMap();
				option.put("label", "");
				option.put("value", "Select");
				promoter.add(option);
				form1.setPromoterValues(promoter);
				form1.setIsSearchClicked("");
			}
		} catch (Exception err) {
			try {
				conn.rollback();
			} catch (SQLException ignore) {
			}
			Log.log(2, "DisbursementAction", "WriteToFile", err.getMessage());
			err.printStackTrace();

			form1.setMessage(err.getMessage());
			return mapping.findForward("disbursementDetailPage");
		} finally {
			try {
				if (conn != null) {
					DBConnection.freeConnection(conn);
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		if (message != null && message.trim().length()>0) {
			form1.setMessage(message);
		}
		return mapping.findForward("disbursementDetailPage");
	}

	public ArrayList getDisbursementData(ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {

		DisbursementActionForm form1 = (DisbursementActionForm) form;
		ArrayList<PopuLateData> disurseData = new ArrayList<PopuLateData>();

		Connection conn = null;
		CallableStatement callableStmt = null;
		ResultSet rs = null;
		User user = getUserInformation(request);
		int status = -1;
		String errorCode = "", message = "";
		try {
			String cgpan = form1.getCgpan();
			String promoterName = form1.getPromoterName();
			String isSearchClicked = form1.getIsSearchClicked();

			if ((cgpan != null && !cgpan.isEmpty()) && (promoterName != null && !promoterName.isEmpty())) {
				if (conn == null) {
					conn = DBConnection.getConnection(false);
				}
				String bankId = user.getBankId();
				String zoneId = user.getZoneId();
				String branchId = user.getBranchId();
				String memberId = bankId + zoneId + branchId;
				//System.out.println("Member id===657===" + memberId);

				callableStmt = conn.prepareCall("{call FUNC_GET_PROMOTER_DETAILS(?,?,?,?,?,?)}");
				callableStmt.registerOutParameter(1, Types.VARCHAR);
				callableStmt.setString(2, cgpan);
				callableStmt.setString(3, promoterName);
				callableStmt.setString(4, memberId);
				callableStmt.setInt(5, 2);
				callableStmt.registerOutParameter(6, Types.VARCHAR);

				callableStmt.execute();
				status = callableStmt.getInt(1);
				errorCode = callableStmt.getString(6);
				// System.out.println("status::" + status + "\t errorCode" +
				// errorCode);

				if (status == Constants.FUNCTION_FAILURE) {
					Log.log(Log.ERROR, "CPDAO", "getDisbursementSearchDetail()",
							"getDisbursementSearchDetail returns a 1. Error code is :" + errorCode);
					message =  errorCode;

					if (callableStmt != null) {
						callableStmt.close();
					}
					conn.rollback();
				} else if (status == Constants.FUNCTION_SUCCESS) {
					rs = callableStmt.executeQuery();
				}

				while (rs.next()) {
					String Disburse_date = rs.getString("Disburse_date");
					String Disburse_amount = String.valueOf(rs.getDouble("Disburse_amount"));
					String IsFinaldisbursed = rs.getString("IsFinaldisbursed");
					PopuLateData data = new PopuLateData();
					data.setDate(Disburse_date);
					data.setAmount(Disburse_amount);
					if (IsFinaldisbursed.equals("Y")) {
						data.setCheckBoxValue("checked");
					} else {
						data.setCheckBoxValue("");
					}
					disurseData.add(data);
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
		return disurseData;
	}

	public ActionForward getDisbursementSearchDetail(ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {

		DisbursementActionForm form1 = (DisbursementActionForm) form;
		ArrayList promoter = new ArrayList();
		Connection conn = null;
		CallableStatement callableStmt = null;
		ResultSet rs = null;
		User user = getUserInformation(request);
		int status = -1;
		String errorCode = null;
		String message = "";
		try {
			String cgpan = form1.getCgpan();
			String promoterName = form1.getPromoterName();
			String isSearchClicked = form1.getIsSearchClicked();

			/*System.out.println(
					"CGPAN VALUE :: [" + cgpan + "]\t " + " PROMOTER VALUE " + promoterName + "\t Promoter Values ::"
							+ promoter + "\t " + "Object Value is ::" + "" + "\t isSearchClicked" + isSearchClicked);*/

			if ((cgpan != null && !cgpan.isEmpty()) && (promoterName != null && !promoterName.isEmpty())) {
				if (conn == null) {
					conn = DBConnection.getConnection(false);
				}

				String bankId = user.getBankId();
				String zoneId = user.getZoneId();
				String branchId = user.getBranchId();
				String memberId = bankId + zoneId + branchId;
				System.out.println("Member id===656===" + memberId);

				callableStmt = conn.prepareCall("{call FUNC_GET_PROMOTER_DETAILS(?,?,?,?,?,?)}");
				callableStmt.registerOutParameter(1, Types.VARCHAR);
				callableStmt.setString(2, cgpan);
				callableStmt.setString(3, promoterName);
				callableStmt.setString(4, memberId);
				callableStmt.setInt(5, 1);
				callableStmt.registerOutParameter(6, Types.VARCHAR);

				callableStmt.execute();
				status = callableStmt.getInt(1);
				errorCode = callableStmt.getString(6);
				// System.out.println("status::" + status + "\t errorCode" +
				// errorCode);

				if (status == Constants.FUNCTION_FAILURE) {

					Log.log(Log.ERROR, "CPDAO", "getDisbursementSearchDetail()",
							"getDisbursementSearchDetail returns a 1. Error code is :" + errorCode);
					message =  errorCode;
					if (callableStmt != null) {
						callableStmt.close();
					}
					conn.rollback();
				} else if (status == Constants.FUNCTION_SUCCESS) {

					rs = callableStmt.executeQuery();
				}
				// System.out.println("status::" + status + "\t errorCode" +
				// errorCode + "\t message is ::" + message);
				if (rs.next()) {
					String promoterNameValue = rs.getString("PROMOTERNAME");
					double guaranteedAmt = rs.getDouble("GUARANTEED_AMOUNT");
					String roleName = rs.getString("ROLENAME");
					String startGuaranteeDate = rs.getString("GUARATNEE_START_DATE");
					String sanctionDate = rs.getString("SANCTION_DATE");

					/*System.out.println(
							"Table Values are ==" + promoterNameValue + "\t" + guaranteedAmt + "\t" + roleName);

					System.out.println("12313" + startGuaranteeDate + "\t" + sanctionDate);*/
					promoter = getPromoterDetails(cgpan, conn, bankId);

					ArrayList disurseData = getDisbursementData(mapping, form, request, response);
					form1.setPromoterValues(promoter);
					form1.setPromoterNameValue(promoterNameValue);
					form1.setGuaranteedAmt(guaranteedAmt);
					form1.setStartGuaranteeDate(startGuaranteeDate);
					form1.setSanctionDate(sanctionDate);
					form1.setRoleName(roleName);
					form1.setIsSearchClicked(isSearchClicked);
					request.setAttribute("role", roleName);
					request.setAttribute("disurseData", disurseData);
				}
			} else {
				form1.setPromoterValues(promoter);
				form1.setPromoterNameValue("XXXXXXXXXX");
				form1.setGuaranteedAmt(0.00);
				form1.setRoleName("XXXXXXXXXX");
				form1.setIsSearchClicked(isSearchClicked);
			}
		} catch (Exception err) {
			try {
				conn.rollback();
			} catch (SQLException ignore) {}
			err.printStackTrace();
			form1.setPromoterValues(promoter);
			form1.setPromoterNameValue("XXXXXXXXXX");
			form1.setGuaranteedAmt(0.00);
			form1.setRoleName("XXXXXXXXXX");
			form1.setMessage(err.getMessage());

			return mapping.findForward("disbursementDetailPage");
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
				Log.log(2, "DisbursementAction", "WriteToFile", err.getMessage());
				err.printStackTrace();
			}
		}
		if (message != null) {
			form1.setMessage(message);
		}
		return mapping.findForward("disbursementDetailPage");
	}

	public ActionForward getDisbursementJsonDetail(ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		Connection conn = null;
		DisbursementActionForm form1 = (DisbursementActionForm) form;
		String message = "";
		try {
			User user = getUserInformation(request);

			if (conn == null) {
				conn = DBConnection.getConnection(false);
			}
			String userId = user.getUserId();
			message = saveDisbursementDataValues(userId, conn, request, form1);
			getDisbursementSearchDetail(mapping, form, request, response);
		} catch (Exception err) {
			try {
				conn.rollback();
			} catch (SQLException ignore) {}
			Log.log(2, "DisbursementAction", "WriteToFile", err.getMessage());
			err.printStackTrace();
			form1.setMessage(err.getMessage());
			return mapping.findForward("disbursementDetailPage");
		} finally {
			try {
				if (conn != null) {
					DBConnection.freeConnection(conn);
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		if (message != null) {
			form1.setMessage(message);
		}
		return mapping.findForward("disbursementDetailPage");
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

	public String saveDisbursementDataValues(String user, Connection conn, HttpServletRequest request,
			DisbursementActionForm form1) {
		String sqlQuery = "";
		CallableStatement callableStmt = null;
		ResultSet rs = null;

		String[] date;
		String[] amount;
		String[] check;
		String cgpan = "", pmr_refNo = "";
		int status = -1;
		String errorCode = null;
		String message = "";
		try {
			cgpan = form1.getCgpan();
			pmr_refNo = form1.getPromoterName();
			date = request.getParameterValues("date");
			amount = request.getParameterValues("amount");
			check = request.getParameterValues("check");

			//System.out.println(check.length + "\t 121" + check[0]);

			for (int i = 0; i < date.length; i++) {
				callableStmt = conn.prepareCall("{call FUNC_INSERT_DIBURSEMENT_DETAILS(?,?,?,?,?,?,?,?)}");
				callableStmt.registerOutParameter(1, Types.VARCHAR);
				callableStmt.setString(2, cgpan);
				callableStmt.setString(3, pmr_refNo);
				java.util.Date date1 = new SimpleDateFormat("dd/MM/yyyy").parse(date[i]);
				SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
				String dateVal = formatter.format(date1);
				callableStmt.setString(4, dateVal);
				callableStmt.setDouble(5, Double.valueOf(amount[i]));
				if (i == date.length - 1 && check.length > 0) {
					callableStmt.setString(6, check[0]);

				} else {
					callableStmt.setString(6, "N");

				}
				callableStmt.setString(7, user);
				callableStmt.registerOutParameter(8, Types.VARCHAR);
				callableStmt.execute();

				status = callableStmt.getInt(1);
				errorCode = callableStmt.getString(8);
				// System.out.println("status::" + status + "\t errorCode" +
				// errorCode);

				if (status == Constants.FUNCTION_FAILURE) {
					Log.log(Log.ERROR, "CPDAO", "getDisbursementSearchDetail()",
							"getDisbursementSearchDetail returns a 1. Error code is :" + errorCode);
					message =  errorCode;

					if (callableStmt != null) {
						callableStmt.close();
						callableStmt = null;
					}
				} else if (status == Constants.FUNCTION_SUCCESS) {
					message = "Data Inserted Successfully!!";
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
				if (callableStmt != null) {
					callableStmt.close();
					callableStmt = null;
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		return message;
	}
}
