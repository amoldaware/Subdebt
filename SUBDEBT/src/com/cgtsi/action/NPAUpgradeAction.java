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
import com.cgtsi.actionform.NPAUpgradeActionForm;
import com.cgtsi.actionform.NPAUpgradePopulateData;
import com.cgtsi.actionform.PopuLateData;
import com.cgtsi.admin.User;
import com.cgtsi.common.Constants;
import com.cgtsi.common.Log;
import com.cgtsi.util.DBConnection;
import com.mysql.cj.protocol.Resultset;
import com.mysql.cj.protocol.ResultsetRows;

public class NPAUpgradeAction extends BaseAction {
	public ActionForward getNPAUpgradeDetail(ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception 
	{
		Connection conn = null;
		PreparedStatement pstmt = null;
		Resultset rs = null;
		NPAUpgradeActionForm form1 = (NPAUpgradeActionForm) form;
		String cgpan = form1.getCgpan();
		ArrayList promoter = new ArrayList();
		User user = (User) getUserInformation(request);
		// System.out.println("user" + user);
		String bankid = (user.getBankId()).trim();
		String message = "";
		try {
			if (cgpan != null && !cgpan.isEmpty()) {
				if (conn == null) {
					conn = DBConnection.getConnection(false);
				}
				
				promoter = getPromoterDetails(cgpan, bankid, conn);
				// System.out.println(promoter.size() + "bank Id" + bankid);
				if (promoter.size() == 0) {
					message = "Either CGPAN not pertaining to logged in User,Bank,MLI or No Promoter Details Found!!";
				}
				form1.setPromoterValues(promoter);
				form1.setCgpan(cgpan);
				form1.setIsSearchClicked("");
			} else {
				Map option = new HashMap();
				option.put("label", "");
				option.put("value", "Select");
				promoter.add(option);
				form1.setPromoterValues(promoter);
				form1.setIsSearchClicked("");
			}
		} catch (Exception err) {
			form1.setPromoterValues(promoter);
			Log.log(2, "NPAUpgradeAction", "WriteToFile", err.getMessage());
			err.printStackTrace();

			try {
				conn.rollback();
			} catch (SQLException ignore) {
			}
			form1.setMessage(err.getMessage());
			return mapping.findForward("npaUpgradeDetailPage");
		} finally {
			try {
				if (conn != null) {
					DBConnection.freeConnection(conn);
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		// System.out.println("message :::" + message);
		if (message != null && message.trim().length() > 0) {
			form1.setMessage(message);
		}
		return mapping.findForward("npaUpgradeDetailPage");
	}

	public ActionForward getNPAUpgradeSearchDetail(ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception 
	{
		NPAUpgradeActionForm form1 = (NPAUpgradeActionForm) form;
		User user = getUserInformation(request);
		ArrayList promoter = new ArrayList();
		Connection conn = null;
		CallableStatement callableStmt = null;
		ResultSet rs = null;
		int status = -1;
		String cgpan = "", promoterName = "", isSearchClicked = "", errorCode = "", message = "", bankId = "",
				zoneId = "", branchId = "", memberId = "";
		int npaId = 0;
		String npa_date = "", roleName = "", npaUpgradation_date = "", reasonforUpgradation = "";
		try 
		{
			cgpan = form1.getCgpan();
			promoterName = form1.getPromoterName();
			isSearchClicked = form1.getIsSearchClicked();
			/*System.out
					.println("CGPAN ::\t" + cgpan + "\t PROMOTER NAME ::\t" + promoterName + "\t ::" + isSearchClicked);*/

			bankId = user.getBankId();
			zoneId = user.getZoneId();
			branchId = user.getBranchId();
			memberId = bankId + zoneId + branchId;
			System.out.println("Member id===65===" + memberId);

			if ((cgpan != null && (!cgpan.isEmpty())) && (promoterName != null && !(promoterName.isEmpty()))
					&& (isSearchClicked != null && (!isSearchClicked.isEmpty()))) {
				if (conn == null) {
					conn = DBConnection.getConnection(false);
				}
				
				promoter = getPromoterDetails(cgpan, bankId, conn);
				// System.out.println("Promote Size ::" + promoter.size());

				callableStmt = conn.prepareCall("{call FUNC_GET_NPA_Upgradation_DETAILS(?,?,?,?,?)}");
				callableStmt.registerOutParameter(1, Types.VARCHAR);
				callableStmt.setString(2, cgpan);
				callableStmt.setString(3, promoterName);
				callableStmt.setString(4, memberId);
				callableStmt.registerOutParameter(5, Types.VARCHAR);
				
				callableStmt.execute();
				status = callableStmt.getInt(1);
				errorCode = callableStmt.getString(5);
				// System.out.println("status::" + status + "\t errorCode" +
				// errorCode);

				if (status == Constants.FUNCTION_FAILURE) {
					Log.log(Log.ERROR, "CPDAO", "getDisbursementSearchDetail()",
							"getDisbursementSearchDetail returns a 1. Error code is :" + errorCode);
					
					if (callableStmt != null) {
						callableStmt.close();
					}
					conn.rollback();
					
					message = errorCode;
					form1.setMessage(message);
					form1.setPromoterValues(promoter);
					form1.setIsSearchClicked(isSearchClicked);
				} else if (status == Constants.FUNCTION_SUCCESS) {
					rs = callableStmt.executeQuery();

					if (rs.next()) {
						npaId = rs.getInt("NPA_ID");
						npa_date = rs.getString("NPA_DATE");
						roleName = rs.getString("ROLENAME");
						npaUpgradation_date = rs.getString("NPAUpgradation_date");
						reasonforUpgradation = rs.getString("ReasonforUpgradation");

						// System.out.println("NPA_DATE [" + npa_date + "] \t" +
						// "NPA_ID [" + npaId + "]\t ");
						request.setAttribute("role", roleName);

						form1.setIsSearchClicked(isSearchClicked);
						form1.setPromoterValues(promoter);

						form1.setNpaId(npaId);
						form1.setNpa_date(npa_date);
						form1.setRoleName(roleName);
						form1.setNpaUpgradation_date(npaUpgradation_date);
						form1.setReasonforUpgradation(reasonforUpgradation);
						form1.setMessage("");
						
						ArrayList<NPAUpgradePopulateData> npaPopulateData = getNPAPopulatedData(mapping, form, request,
								response);
						request.setAttribute("npaPopulateData", npaPopulateData);
					}
				}
			}
		} catch (Exception err) {
			form1.setPromoterValues(promoter);
			Log.log(2, "NPAUpgradeAction", "WriteToFile", err.getMessage());
			
			err.printStackTrace();

			try {
				conn.rollback();
			} catch (SQLException ignore) {
			}
			message = err.getMessage();
		} finally {
			try {
				if (rs != null) {
					rs.close();
					rs = null;
				}
				if (callableStmt != null) {
					callableStmt.close();
					callableStmt.close();
				}
				if (conn != null) {
					DBConnection.freeConnection(conn);
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		
		if (message != null && message.trim().length() > 0) {
			form1.setMessage(message);
		}
		return mapping.findForward("npaUpgradeDetailPage");
	}

	public ActionForward saveNPAUpgradeDetails(ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		NPAUpgradeActionForm form1 = (NPAUpgradeActionForm) form;
		String message = "";
		Connection conn = null;
		String sqlQuery = "";
		CallableStatement callableStmt = null;
		int status = -1;
		String errorCode = null;
		int npaId = 0;
		String cgpan = "", npaUpgradation_date = "", bankId = "", reasonforUpgradation = "", promoterName = "",
				userId = "", npa_date = "";
		ArrayList promoter = new ArrayList();
		try {
			User user = getUserInformation(request);
			if (conn == null) {
				conn = DBConnection.getConnection(false);
			}
			cgpan = form1.getCgpan();
			npaId = form1.getNpaId();
			npa_date = form1.getNpa_date();
			npaUpgradation_date = form1.getNpaUpgradation_date();
			reasonforUpgradation = form1.getReasonforUpgradation();
			promoterName = form1.getPromoterName();
			userId = user.getUserId();

			//System.out.println("Inside Save NPAUpgradeDetails Method..." + npa_date);
			/*System.out.println("cgpan [" + cgpan + "]\t npaId [" + npaId + "]\t npaUpgradation_date ["
					+ npaUpgradation_date + "]\t reasonforUpgradation [" + reasonforUpgradation + "]\t promoterName ["
					+ promoterName + "]\t userId [" + userId + "]");*/

			if ((cgpan != null && !cgpan.isEmpty()) && (npaUpgradation_date != null && !npaUpgradation_date.isEmpty())
					&& (reasonforUpgradation != null && !reasonforUpgradation.isEmpty())) {
				bankId = user.getBankId();
				promoter = getPromoterDetails(cgpan, bankId, conn);

				form1.setPromoterValues(promoter);

				if (npaId > 0) {
					callableStmt = conn.prepareCall("{call FUNC_INSERT_NPAUpgradation_DETAILS(?,?,?,?,?,?,?,?)}");
					callableStmt.registerOutParameter(1, Types.VARCHAR);
					callableStmt.setInt(2, npaId);
					callableStmt.setString(3, cgpan);
					callableStmt.setString(4, promoterName);
					java.util.Date date1 = new SimpleDateFormat("dd/MM/yyyy").parse(npaUpgradation_date);
					SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
					String dateVal = formatter.format(date1);
					callableStmt.setString(5, dateVal);
					callableStmt.setString(6, reasonforUpgradation);
					callableStmt.setString(7, userId);
					callableStmt.registerOutParameter(8, Types.VARCHAR);
					callableStmt.execute();

					status = callableStmt.getInt(1);
					errorCode = callableStmt.getString(8);

					if (status == Constants.FUNCTION_FAILURE) {
						Log.log(Log.ERROR, "CPDAO", "getDisbursementSearchDetail()",
								"getDisbursementSearchDetail returns a 1. Error code is :" + errorCode);
						conn.rollback();
						message = errorCode;

						if (callableStmt != null) {
							callableStmt.close();
							callableStmt = null;
						}
					} else if (status == Constants.FUNCTION_SUCCESS) {
						
						ArrayList<NPAUpgradePopulateData> npaPopulateData = getNPAPopulatedData(mapping, form, request,
								response);
						request.setAttribute("npaPopulateData", npaPopulateData);
						message = "Data Inserted Successfully!!";
					}
				}
			}
		} catch (Exception err) 
		{
			Log.log(2, "NPAUpgradeAction", "WriteToFile", err.getMessage());
			err.printStackTrace();

			try {
				conn.rollback();
			} catch (SQLException ignore) {}
			form1.setMessage(message);
		} 
		finally {
			try {
				if (callableStmt != null) {
					callableStmt.close();
					callableStmt.close();
				}
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
		return mapping.findForward("npaUpgradeDetailPage");
	}

	public ArrayList getPromoterDetails(String cgpan, String bankId, Connection conn) {
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

			ResultsetRows executedRows = rs.getRows();
			//System.out.println("Rows Are :::" + executedRows.size());

			if (executedRows.size() > 0) {
				while (((ResultSet) rs).next()) {
					String pmr_refNo = ((ResultSet) rs).getString("PMR_REF_NO");
					String promoterName = ((ResultSet) rs).getString("PROMOTERNAME");
					Map option = new HashMap();
					option.put("label", promoterName);
					option.put("value", pmr_refNo);
					promoter.add(option);
				}
			}

		} catch (Exception err) {
			Log.log(2, "NPAUpgradeAction", "WriteToFile", err.getMessage());
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

	public ArrayList<NPAUpgradePopulateData> getNPAPopulatedData(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)

	{
		String sqlQuery = "";
		CallableStatement callableStmt = null;
		ResultSet rs = null;
		NPAUpgradeActionForm form1 = (NPAUpgradeActionForm) form;
		ArrayList<NPAUpgradePopulateData> npaPopulateData = new ArrayList<NPAUpgradePopulateData>();
		int status = -1;
		String errorCode = "", message = "";
		Connection conn = null;
		try {
			/*System.out.println("Inside method.... getNPAPopulatedData :: CGPAN" + form1.getCgpan() + "\t member is ::"
					+ form1.getPromoterName());*/
			if (conn == null) {
				conn = DBConnection.getConnection(false);
			}
			callableStmt = conn.prepareCall("{call FUNC_Get_SUBDEBT_AUDIT_HISTORY(?,?,?,?)}");
			callableStmt.registerOutParameter(1, Types.VARCHAR);
			callableStmt.setString(2, form1.getCgpan());
			callableStmt.setString(3, form1.getPromoterName());
			callableStmt.registerOutParameter(4, Types.VARCHAR);

			callableStmt.execute();
			status = callableStmt.getInt(1);
			errorCode = callableStmt.getString(4);
			//System.out.println("status::" + status + "\t errorCode" + errorCode);

			if (status == Constants.FUNCTION_FAILURE) {
				Log.log(Log.ERROR, "CPDAO", "getDisbursementSearchDetail()",
						"getDisbursementSearchDetail returns a 1. Error code is :" + errorCode);

				if (callableStmt != null) {
					callableStmt.close();
				}
				conn.rollback();
				message = errorCode;
			} else if (status == Constants.FUNCTION_SUCCESS) {
				rs = callableStmt.executeQuery();

				while (rs.next()) {
				
					NPAUpgradePopulateData data = new NPAUpgradePopulateData();
					data.setClm_au_id(rs.getInt("clm_au_id"));
					data.setClm_LVEL(rs.getString("Clm_LVEL"));
					data.setUser_id(rs.getString("User_id"));
					data.setClm_dttime(rs.getString("clm_dttime"));
					data.setClm_status(rs.getString("clm_status"));
					data.setClm_remark(rs.getString("clm_remark"));

					npaPopulateData.add(data);
				}
				/*npaPopulateData.forEach((d) -> {
					System.out.println(
							"Data is ::::" + d.getClm_au_id() + "\t" + d.getClm_au_id() + "\t" + d.getClm_LVEL());
				});*/
				message = "";
			}
		} catch (Exception err) {
			Log.log(2, "DisbursementAction", "WriteToFile", err.getMessage());
			form1.setMessage(message);
			err.printStackTrace();

			try {
				conn.rollback();
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
					callableStmt.close();
				}
				if (conn != null) {
					DBConnection.freeConnection(conn);
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		return npaPopulateData;
	}
}
