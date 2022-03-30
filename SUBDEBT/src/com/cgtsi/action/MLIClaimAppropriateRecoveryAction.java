package com.cgtsi.action;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Types;
import java.text.SimpleDateFormat;
import java.util.ArrayList;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import com.cgtsi.actionform.MLIAppropriateClaimRecoveryData;
import com.cgtsi.actionform.MLIClaimAppropriateRecoveryForm;
import com.cgtsi.admin.User;
import com.cgtsi.common.Constants;
import com.cgtsi.common.Log;
import com.cgtsi.util.DBConnection;
import com.mysql.cj.protocol.Resultset;

public class MLIClaimAppropriateRecoveryAction extends BaseAction {
	public ActionForward getAppropriateClaimRecoveryData(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) throws Exception {

		MLIClaimAppropriateRecoveryForm form1 = (MLIClaimAppropriateRecoveryForm) form;
		User user = getUserInformation(request);
		Connection conn = null;
		CallableStatement callableStmt = null;
		ResultSet rs = null;
		String message = "", bankId = "", userId = "", errorCode = "";
		int status = -1, count = 0;
		;
		ArrayList<MLIAppropriateClaimRecoveryData> mliRecoveryList = new ArrayList<>();
		MLIAppropriateClaimRecoveryData mliRecoveryData = null;
		// System.out.println("0ooooooooooooooooooo");
		String claimRefNo = request.getParameter("claimRefNo");
		// System.out.println("Claim Reference Number :::" + claimRefNo);

		try {
			String isSearchClicked = form1.getIsSearchClicked();
			// System.out.println("==isSearchClicked========"+isSearchClicked);
			if (conn == null) {
				conn = DBConnection.getConnection(false);
			}
			bankId = user.getBankId();
			userId = user.getUserId();
			if ((claimRefNo != null) && !(claimRefNo.equals(""))) {
				// form1.setMemberId(cgpanMemberId);
				mliRecoveryList = getApprovalApplicationDtls(request, response, form1, claimRefNo, bankId,
						user.getUserId());
				System.out.println("mliRecoveryList ::" + mliRecoveryList);
				if (mliRecoveryList != null) {

					request.setAttribute("claimRefNo", claimRefNo);
					// request.setAttribute("mliRecoveryList",
					// mliRecoveryList);
				}

			} else {
				form1.setIsSearchClicked("");
			}

		} catch (Exception err) {
			try {
				if (conn != null) {
					conn.rollback();
				}
			} catch (SQLException ignore) {}
			Log.log(2, "ClaimPaymrntAction", "WriteToFile", err.getMessage());
			form1.setMessage(err.getMessage());
			err.printStackTrace();
		} finally {
			try {
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

			// return mapping.findForward("getchecklist");
		}
		// System.out.println("cgpan" + pcgpan);

		return mapping.findForward("mliClaimAppropriateRecoverypage");
	}

	public ArrayList<MLIAppropriateClaimRecoveryData> getApprovalApplicationDtls(HttpServletRequest request,
			HttpServletResponse respons, MLIClaimAppropriateRecoveryForm form, String claimRefNo, String bankId,
			String userId) {

		ArrayList<MLIAppropriateClaimRecoveryData> approvalDtls = new ArrayList();
		CallableStatement callableStmt = null;
		ResultSet rs = null;

		User user = getUserInformation(request);
		int status = -1;
		String errorCode = null;
		String message = "";
		Connection conn = null;
		int count = 0;
		String fromDate = "";
		String tooDate = "";
		MLIAppropriateClaimRecoveryData mliRecoveryData = null;
		ArrayList<MLIAppropriateClaimRecoveryData> mliRecoveryList = new ArrayList<>();
		try {// CALL
				// FUNC_GET_CLAIM_PAYMENT_DETAILS(@p,'2021-06-13','2021-06-14','0000','002000010000','ALOPRIY0001',@q);

			if (conn == null) {
				conn = DBConnection.getConnection(false);
			}
			callableStmt = conn.prepareCall("{call FUNC_GET_CLAIM_RECOVERY_DETAILS(?,?,?,?,?,?)}");
			callableStmt.registerOutParameter(1, Types.VARCHAR);
			callableStmt.setString(2, bankId);
			callableStmt.setInt(3, 3);
			callableStmt.setString(4, userId);
			callableStmt.setString(5, claimRefNo);
			callableStmt.registerOutParameter(6, Types.VARCHAR);

			callableStmt.execute();
			status = callableStmt.getInt(1);
			errorCode = callableStmt.getString(6);

			if (status == Constants.FUNCTION_FAILURE) {
				Log.log(Log.ERROR, "MLIClaimRecoveryAction", "getClaimRecoveryData()",
						"getClaimRecoveryData returns a 1. Error code is :" + errorCode);
				message = errorCode;

				if (callableStmt != null) {
					callableStmt.close();
				}
				if (conn != null) {
					conn.rollback();
				}
			} else if (status == Constants.FUNCTION_SUCCESS) {
				rs = callableStmt.executeQuery();
				// , CGPAN, MEMBER_ID, CLAIM_REF_NUMBER, PROMOTERNAME,
				// UNIT_NAME, TotalAmount, UTR_number, UTR_DATE
				while (rs.next()) {
					mliRecoveryData = new MLIAppropriateClaimRecoveryData();
					count++;
					mliRecoveryData.setRecId(rs.getInt("Rec_id"));
					mliRecoveryData.setSrNo(count);
					mliRecoveryData.setPmr_ref_No(rs.getString("PMR_REF_NO"));
					mliRecoveryData.setCgpan(rs.getString("CGPAN"));
					mliRecoveryData.setMemberId(rs.getString("MEMBER_ID"));
					mliRecoveryData.setClaim_ref_number(rs.getString("CLAIM_REF_NUMBER"));
					mliRecoveryData.setPromoterName(rs.getString("PROMOTERNAME"));
					mliRecoveryData.setUnitName(rs.getString("UNIT_NAME"));
					mliRecoveryData.setBankName(rs.getString("Bank_Name"));
					/*
					 * mliRecoveryData.setPrevious_Recovery(rs.getDouble(
					 * "Previous_Recovery"));
					 * mliRecoveryData.setEligibl_Amt_First_Inst(rs.getDouble(
					 * "ELIGIBL_AMT_FIRST_INST"));
					 * mliRecoveryData.setAdditional_Recovery(rs.getDouble(
					 * "Additional_Recovery"));
					 * mliRecoveryData.setLegal_Expense_Advocate_Fee(rs.
					 * getDouble("Legal_Expense_Advocate_Fee"));
					 * mliRecoveryData.setLegal_Expense_Court_Fee(rs.getDouble(
					 * "Legal_Expense_Court_Fee"));
					 * mliRecoveryData.setDescription_of_Other_Recovery(rs.
					 * getString("Description_of_Other_Recovery"));
					 * mliRecoveryData.setOther_Recovery_Expenses(rs.getDouble(
					 * "Other_Recovery_Expenses"));
					 * mliRecoveryData.setTotal_Legal_Expenses(rs.getDouble(
					 * "Total_Legal_Expenses"));
					 * mliRecoveryData.setRecoveryAmounttoberemittedtoCGTMSE(rs.
					 * getDouble("RecoveryAmounttoberemittedtoCGTMSE"));
					 */
					mliRecoveryData.setTotalAmount(rs.getDouble("TotalAmount"));

					if ((rs.getString("UTR_number") != null) && !(rs.getString("UTR_number").equals(""))) {

						mliRecoveryData.setUtrNumber(rs.getString("UTR_number"));
					} else {
						mliRecoveryData.setUtrNumber("");
					}

					if ((rs.getString("UTR_DATE") != null) && !(rs.getString("UTR_DATE").equals(""))) {
						mliRecoveryData.setUtrDate(rs.getString("UTR_DATE"));
					} else {
						mliRecoveryData.setUtrDate("");

					}

					if ((rs.getString("Payment_recv_Date") != null)
							&& !(rs.getString("Payment_recv_Date").equals(""))) {
						mliRecoveryData.setPaymentReceiveDate(rs.getString("Payment_recv_Date"));
					} else {
						mliRecoveryData.setPaymentReceiveDate("");

					}
					mliRecoveryList.add(mliRecoveryData);

					request.setAttribute("mliRecoveryList", mliRecoveryList);
				}
				/*
				 * mliRecoveryList.forEach((mliRecoveryData1) -> {
				 * System.out.println(mliRecoveryData1.getCgpan() + "\t" +
				 * mliRecoveryData1.getPromoterName()); });
				 */
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
		return approvalDtls;
	}

	public ActionForward saveClaimRecoveryData(ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		MLIClaimAppropriateRecoveryForm form1 = (MLIClaimAppropriateRecoveryForm) form;
		User user = getUserInformation(request);
		Connection conn = null;
		CallableStatement callableStmt = null;
		ResultSet rs = null;
		String message = "";
		String userId = "", errorCode = "";
		int srNo = 0;
		String[] recId;
		String updateRecovery[], dateOfrecovery[], pmr_ref_No[], claim_ref_number[], cgpan[], type_of_Recovery[],
				additional_Recovery[], legal_Expense_Advocate_Fee[], legal_Expense_Court_Fee[],
				description_of_Other_Recovery[], other_Recovery_Expenses[], total_Legal_Expenses[],
				recoveryAmounttoberemittedtoCGTMSE[], totalAmount[], bankName[], utrNumber[];
		int status = -1, count = 0;
		ArrayList<MLIAppropriateClaimRecoveryData> mliRecoveryList = new ArrayList<>();
		MLIAppropriateClaimRecoveryData mliRecoveryData = null;
		try {
			userId = user.getUserId();

			if (conn == null) {
				conn = DBConnection.getConnection(false);
			}
			recId = request.getParameterValues("recId");

			updateRecovery = request.getParameterValues("updateRecovery");
			dateOfrecovery = request.getParameterValues("dateOfrecovery");
			pmr_ref_No = request.getParameterValues("pmr_ref_No");
			claim_ref_number = request.getParameterValues("claim_ref_number");
			cgpan = request.getParameterValues("cgpan");
			bankName = request.getParameterValues("bankName");
			type_of_Recovery = request.getParameterValues("type_of_Recovery");
			totalAmount = request.getParameterValues("totalAmount");
			utrNumber = request.getParameterValues("utrNumber");

			for (int j = 0; j < updateRecovery.length; j++) {
				String[] bits = String.valueOf(updateRecovery[j]).split("-");
				int lastOne = Integer.valueOf(bits[bits.length - 1]);
				lastOne = lastOne - 1;
				mliRecoveryData = new MLIAppropriateClaimRecoveryData();
				mliRecoveryData.setPmr_ref_No(pmr_ref_No[lastOne]);
				mliRecoveryData.setCgpan(cgpan[lastOne]);
				mliRecoveryData.setClaim_ref_number(claim_ref_number[lastOne]);
				mliRecoveryData.setType_of_Recovery(type_of_Recovery[lastOne]);
				mliRecoveryData.setDateOfrecovery(dateOfrecovery[lastOne]);
				mliRecoveryData.setTotalAmount(Double.valueOf(totalAmount[lastOne]));
				mliRecoveryData.setRecId(Integer.valueOf(recId[lastOne]));
				mliRecoveryList.add(mliRecoveryData);
			}

			if (mliRecoveryList.size() > 0) {
				try {

					for (MLIAppropriateClaimRecoveryData mliRecoveryData1 : mliRecoveryList) {
						callableStmt = conn.prepareCall("{call FUNC_Update_RECOVERY_DETAILS(?,?,?,?,?,?,?,?,?,?)}");
						callableStmt.registerOutParameter(1, Types.VARCHAR);

						callableStmt.setString(2, mliRecoveryData1.getClaim_ref_number());
						callableStmt.setString(3, null);
						callableStmt.setString(4, null);
						java.util.Date date1 = new SimpleDateFormat("dd/MM/yyyy")
								.parse(mliRecoveryData1.getDateOfrecovery());
						SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
						String dateVal = formatter.format(date1);
						callableStmt.setString(5, dateVal);
						callableStmt.setString(6, mliRecoveryData1.getType_of_Recovery());// status
						callableStmt.setInt(7, 2);
						callableStmt.setString(8, userId);
						callableStmt.setInt(9, mliRecoveryData1.getRecId());
						callableStmt.registerOutParameter(10, Types.VARCHAR);

						callableStmt.execute();
						status = callableStmt.getInt(1);
						errorCode = callableStmt.getString(10);

						if (status == Constants.FUNCTION_FAILURE) {
							Log.log(Log.ERROR, "CheckerRequestApprovalAction", "savecheckerMLIUserNew()",
									"savecheckerMLIUserNew returns a 1. Error code is :" + errorCode);
							message = errorCode;

							if (callableStmt != null) {
								callableStmt.close();
							}
							if (conn != null) {
								conn.rollback();
							}
						} else if (status == Constants.FUNCTION_SUCCESS) {
							message = errorCode;
						}
					}
					getAppropriateClaimRecoveryData(mapping, form, request, response);
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
				}
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
			form1.setMessage(message);
		}
		return mapping.findForward("mliClaimAppropriateRecoverypage");
	}

	public ActionForward cancelData(ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		response.sendRedirect(
				request.getContextPath() + "/subHome.do?method=getSubMenu&menuIcon=AP&mainMenu=Claim Processing");
		return mapping.findForward("");
	}
}
