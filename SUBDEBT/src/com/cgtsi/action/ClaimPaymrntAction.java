package com.cgtsi.action;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Types;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import com.cgtsi.actionform.BankMandateActionForm;
import com.cgtsi.actionform.BankMandateClaimList;
import com.cgtsi.actionform.CheckListForm;
import com.cgtsi.actionform.ClaimPaymentActionForm;
import com.cgtsi.actionform.ClaimPaymentData;
import com.cgtsi.actionform.ClaimProcessingApprovalActionForm;
import com.cgtsi.admin.User;
import com.cgtsi.common.Constants;
import com.cgtsi.common.Log;
import com.cgtsi.util.DBConnection;
import com.mysql.cj.protocol.Resultset;

public class ClaimPaymrntAction extends BaseAction {

	public ActionForward getClaimPayment(ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		Connection conn = null;
		PreparedStatement pstmt = null;
		Resultset rs = null;
		String message = "";

		ArrayList<ClaimPaymentActionForm> paymentDtls = null;
		ClaimPaymentActionForm form1 = (ClaimPaymentActionForm) form;
		User user = getUserInformation(request);

		String fromeDate = request.getParameter("fromeDate");
		String toDate = request.getParameter("toDate");
		String claimType = request.getParameter("claimType");
		String memberId = request.getParameter("memberId");

		try {
			String isSearchClicked = form1.getIsSearchClicked();
			// System.out.println("==isSearchClicked========"+isSearchClicked);

			if (conn == null) {
				conn = DBConnection.getConnection(false);
			}

			if ((fromeDate != null) && !(fromeDate.equals("")) && (toDate != null) && !(toDate.equals(""))
					&& (claimType != null) && !(claimType.equals("")) || (memberId != null) && !(memberId.equals(""))) {
				// form1.setMemberId(cgpanMemberId);
				paymentDtls = getApprovalApplicationDtls(request, response, form1, fromeDate, toDate, claimType,
						memberId, user.getUserId());
				if (paymentDtls != null) {

					request.setAttribute("paymentDtls", paymentDtls);
					request.setAttribute("userId", user.getUserId());
					request.setAttribute("fromeDate", fromeDate);
					request.setAttribute("toDate", toDate);
					if (claimType.equalsIgnoreCase("FirstClaim")) {
						request.setAttribute("firstClaim", claimType);
					} else {

						request.setAttribute("secondClaim", claimType);
					}
				}
			} else {
				form1.setIsSearchClicked("");
			}

		} catch (Exception err) {
			try {
				if (conn != null) {
					conn.rollback();
				}
			} catch (SQLException ignore) {
			}
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
		// System.out.println("cgpan" + pcgpan);

		if (message != null && message.trim().length() > 0) {
			form1.setMessage(message);
			// return mapping.findForward("getchecklist");
		}
		return mapping.findForward("claimPaymentPage");
	}

	public ArrayList<ClaimPaymentActionForm> getApprovalApplicationDtls(HttpServletRequest request,
			HttpServletResponse respons, ClaimPaymentActionForm form, String fromeDate, String toDate, String claimType,
			String memberId, String userId) 
	{
		ArrayList<ClaimPaymentActionForm> approvalDtls = new ArrayList();
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
		try {// CALL
				// FUNC_GET_CLAIM_PAYMENT_DETAILS(@p,'2021-06-13','2021-06-14','0000','002000010000','ALOPRIY0001',@q);

			java.util.Date date1 = new SimpleDateFormat("dd/MM/yyyy").parse(fromeDate);
			SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
			fromDate = formatter.format(date1);

			java.util.Date date = new SimpleDateFormat("dd/MM/yyyy").parse(toDate);
			SimpleDateFormat formatter1 = new SimpleDateFormat("yyyy-MM-dd");
			tooDate = formatter1.format(date);

			if (conn == null) {
				conn = DBConnection.getConnection(false);
			}
			callableStmt = conn.prepareCall("{call FUNC_GET_CLAIM_PAYMENT_DETAILS(?,?,?,?,?,?,?)}");
			callableStmt.registerOutParameter(1, Types.VARCHAR);
			callableStmt.setString(2, fromDate);
			callableStmt.setString(3, tooDate);
			callableStmt.setString(4, claimType);
			if (memberId.equals("")) {
				callableStmt.setString(5, null);
			} else {
				callableStmt.setString(5, memberId);
			}
			callableStmt.setString(6, userId);
			callableStmt.registerOutParameter(7, Types.VARCHAR);
			callableStmt.execute();

			status = callableStmt.getInt(1);
			errorCode = callableStmt.getString(7);

			if (status == Constants.FUNCTION_FAILURE) {

				Log.log(Log.ERROR, "CPDAO", "getNpaMarkingSearchDetail()",
						"getNpaMarkingSearchDetail returns a 1. Error code is :" + errorCode);
				message = errorCode;
				if (callableStmt != null) {
					callableStmt.close();
				}
				form.setMessage(message);
			} else if (status == Constants.FUNCTION_SUCCESS) {

				rs = callableStmt.executeQuery();
				while (rs.next()) {
					ClaimPaymentActionForm paymentlDtls = new ClaimPaymentActionForm();
					count++;
					paymentlDtls.setSrNo(count);
					paymentlDtls.setRefNumber(checkNull(rs.getString("PMR_REF_NO")));
					paymentlDtls.setcGPAN(checkNull(rs.getString("CGPAN")));
					paymentlDtls.setMemberId(checkNull(rs.getString("MEMBER_ID")));
					paymentlDtls.setBankName(checkNull(rs.getString("BANK_NAME")));
					paymentlDtls.setZoneName(checkNull(rs.getString("ZONE_NAME")));
					paymentlDtls.setUnitName(checkNull(rs.getString("UNIT_NAME")));
					paymentlDtls.setPromoterName(checkNull(rs.getString("PROMOTERNAME")));
					paymentlDtls.setClaimApprovedDate(checkNull(rs.getString("CLAIM_APPROVAL_DATE")));
					paymentlDtls.setApproveClaimAmount(rs.getDouble("APPROVED_CLAIM_AMOUNT"));
					paymentlDtls.setClaimType(checkNull(rs.getString("CLAIMTYPE")));
					paymentlDtls.setApproveAmount(rs.getDouble("APPROVED_AMT"));
					paymentlDtls.setRevisedNPAApprovedAmount(rs.getDouble("REVISED_NPA_APPROVED_AMOUNT"));
					paymentlDtls.setNetOutstandingAmount(rs.getDouble("NET_OUTSTANDING_AMOUNT"));
					paymentlDtls.setClaimAppliedAmount(rs.getDouble("CLAIM_APPLIED_AMOUNT"));
					paymentlDtls.setClaimeligibleAmount(rs.getDouble("CLAIM_ELIGIBLE_AMOUNT"));
					paymentlDtls.setFirstInstallmentPayAmount(rs.getDouble("FIRST_INSTALLMENT_PAY_AMOUNT"));
					paymentlDtls.setaSFDeductableRefundable(rs.getDouble("ASF_DEDUCTABLE_REFUNDABLE"));
					paymentlDtls.setNetPaidAmount(rs.getDouble("NET_PAID_AMOUNT"));
					paymentlDtls.setBeneficiaryBankName(checkNull(rs.getString("beneficiary_bank_name")));
					paymentlDtls.setAccounttype(checkNull(rs.getString("Account_type")));
					paymentlDtls.setAccountNumber(checkNull(rs.getString("Account_Number")));
					paymentlDtls.setiSDISABLE(checkNull(rs.getString("ISDISABLE")));
					paymentlDtls.setRole(checkNull(rs.getString("ROLENAME")));
					approvalDtls.add(paymentlDtls);
				}
			}
		} catch (Exception err) {
			Log.log(2, "ClaimProcessingAction", "WriteToFile", err.getMessage());
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
			} catch (Exception e) {
				System.out.println(e.getMessage());
			}
		}
		return approvalDtls;
	}

	public ActionForward saveDamentData(ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		ClaimPaymentActionForm form1 = (ClaimPaymentActionForm) form;
		User user = getUserInformation(request);
		Connection conn = null;
		CallableStatement callableStmt = null;
		ResultSet rs = null;
		String message = "";
		String userId = "", errorCode = "";
		int srNo = 0;
		String cgpan[];
		String referanceNo[];
		String claimTypee[];
		String memberIdd[];
		String checkbox[];
		String roleName = form1.getRole();
		ArrayList<ClaimPaymentActionForm> paymentDtls = null;
		int status = -1, count = 0;
		ArrayList<ClaimPaymentData> claimPaymentList = new ArrayList<>();
		ClaimPaymentData claimPaymentData = null;

		String fromeDate = request.getParameter("fromeDate");
		String toDate = request.getParameter("toDate");
		String claimType = request.getParameter("claimType");
		String memberId = request.getParameter("memberId");
		try {
			userId = user.getUserId();

			if (conn == null) {
				conn = DBConnection.getConnection(false);
			}
			userId = user.getUserId();
			// System.out.println("========userId======="+userId);
			memberIdd = request.getParameterValues("memberIdd");
			// System.out.println("=========memberId======"+memberIdd.length);
			referanceNo = request.getParameterValues("referanceNumber");
			// System.out.println("=========referanceNo======"+referanceNo.length);
			cgpan = request.getParameterValues("ccgpan");
			// System.out.println("=======cgpan========"+cgpan.length);
			claimTypee = request.getParameterValues("claimTypee");
			// System.out.println("========claimTypee======="+claimTypee.length);
			double[] netPaidAmountt = Arrays.stream(request.getParameterValues("netPaidAmountt"))
					.mapToDouble(Double::parseDouble).toArray();
			// System.out.println("========netPaidAmountt======="+netPaidAmountt.length);
			checkbox = request.getParameterValues("claimPayment");
			// System.out.println("========checkbox======="+checkbox.length);

			// System.out.println("claimPayment ::" + checkbox.length);
			for (int j = 0; j < checkbox.length; j++) {
				String[] bits = String.valueOf(checkbox[j]).split("-");
				int lastOne = Integer.valueOf(bits[bits.length - 1]);
				lastOne = lastOne - 1;
				claimPaymentData = new ClaimPaymentData();
				claimPaymentData.setReferanceNumber(referanceNo[lastOne]);
				claimPaymentData.setMemberId(memberIdd[lastOne]);
				claimPaymentData.setCcgpan(cgpan[lastOne]);
				claimPaymentData.setClaimType(claimTypee[lastOne]);
				claimPaymentData.setNetPaidAmountt(netPaidAmountt[lastOne]);
				claimPaymentData.setUserId(userId);
				if (roleName.equalsIgnoreCase("CHECKER")) {
					claimPaymentData.setCheckerStatus(form1.getCheckerReturn());
				} else {
					claimPaymentData.setCheckerStatus("NW");
				}

				claimPaymentList.add(claimPaymentData);
			}

			if (claimPaymentList.size() > 0) {
				try {

					for (ClaimPaymentData claimPaymentData1 : claimPaymentList) {
						callableStmt = conn.prepareCall("{call FUNC_INSERT_CLAIM_PAYMENT_DETAILS(?,?,?,?,?,?,?,?,?)}");

						callableStmt.registerOutParameter(1, Types.VARCHAR);
						callableStmt.setString(2, claimPaymentData1.getReferanceNumber());
						callableStmt.setString(3, claimPaymentData1.getMemberId());
						callableStmt.setString(4, claimPaymentData1.getCcgpan());
						callableStmt.setString(5, claimPaymentData1.getClaimType());
						callableStmt.setDouble(6, claimPaymentData1.getNetPaidAmountt());
						callableStmt.setString(7, claimPaymentData1.getUserId());
						callableStmt.setString(8, claimPaymentData1.getCheckerStatus());
						callableStmt.registerOutParameter(9, Types.VARCHAR);

						callableStmt.execute();
						status = callableStmt.getInt(1);
						errorCode = callableStmt.getString(9);

						if (status == Constants.FUNCTION_FAILURE) {
							Log.log(Log.ERROR, "ClaimPaymrntAction", "saveDamentData()",
									"saveDamentData returns a 1. Error code is :" + errorCode);
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

				} catch (Exception err) {
					message = err.getMessage();
					Log.log(2, "ClaimPaymrntAction", "getClaimRecoveryData", err.getMessage());
					err.printStackTrace();
					try {
						if (conn != null) {
							conn.rollback();
						}
					} catch (SQLException ignore) {}
				}
			}
		} catch (Exception err) {
			message = err.getMessage();
			Log.log(2, "ClaimPaymrntAction", "getClaimRecoveryData", err.getMessage());
			err.printStackTrace();
			try {
				if (conn != null) {
					conn.rollback();
				}
				;
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
		// System.out.println("Message is :::11" + message);
		if (message != null && message.trim().length() > 0) {
			form1.setMessage(message);
		}

		paymentDtls = getApprovalApplicationDtls(request, response, form1, fromeDate, toDate, claimType, memberId,
				user.getUserId());
		if (paymentDtls != null) {

			String isSearchClicked = form1.getIsSearchClicked();

			// System.out.println("==isSearchClicked========"+isSearchClicked);

			request.setAttribute("paymentDtls", paymentDtls);
			request.setAttribute("userId", user.getUserId());
			request.setAttribute("fromeDate", fromeDate);
			request.setAttribute("toDate", toDate);

			if (claimType.equalsIgnoreCase("FirstClaim")) {
				request.setAttribute("firstClaim", claimType);
			} else {

				request.setAttribute("secondClaim", claimType);
			}
			form1.setIsSearchClicked(isSearchClicked);
		} else {
			form1.setIsSearchClicked("");
		}
		return mapping.findForward("claimPaymentPage");
	}

	public String checkNull(String str) {
		if (str != null && str.trim().length() > 0) {
			return str.trim();
		} else {
			return "";
		}
	}

	public ActionForward cancelData(ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		response.sendRedirect(
				request.getContextPath() + "/subHome.do?method=getSubMenu&menuIcon=AP&mainMenu=Claim Processing");
		return mapping.findForward("");
	}

}
