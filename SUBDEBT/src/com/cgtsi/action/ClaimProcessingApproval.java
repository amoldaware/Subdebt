package com.cgtsi.action;

import java.io.OutputStream;
import java.math.BigDecimal;
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
import org.apache.struts.upload.FormFile;

import com.cgtsi.actionform.BankMandateActionForm;
import com.cgtsi.actionform.BankMandateClaimList;
import com.cgtsi.actionform.CheckListForm;
import com.cgtsi.actionform.ClaimProcessingActionForm;
import com.cgtsi.actionform.ClaimProcessingApprovalActionForm;
import com.cgtsi.actionform.NPAMarkingPopulateData;
import com.cgtsi.admin.User;
import com.cgtsi.common.Constants;
import com.cgtsi.common.Log;
import com.cgtsi.util.DBConnection;
import com.mysql.cj.protocol.Resultset;

public class ClaimProcessingApproval extends BaseAction {

	public ActionForward getClaimProcessingApproval(ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception 
	{
		Connection conn = null;
		String message = "";

		ArrayList<ClaimProcessingApprovalActionForm> approvalDtls = null;
		ArrayList<ClaimProcessingApprovalActionForm> approvalApplicationDtls = null;
		ArrayList<CheckListForm> checkListDtls = null;
		ArrayList<BankMandateClaimList> claimList = null;
		ClaimProcessingApprovalActionForm form1 = (ClaimProcessingApprovalActionForm) form;
		User user = getUserInformation(request);

		// Claim approval
		String bankId = request.getParameter("bankIdd");
		String selectedClaim = form1.getSelectedClaim();
		System.out.println("=============bankId===============" + bankId + "Selected Claim is ::::" + selectedClaim);

		// No of application details
		String promoterRefNo = request.getParameter("promoterRefNo");
		String cgpan = request.getParameter("cgpan");

		String userId = request.getParameter("userId");
		String memberId = request.getParameter("memberId");
		// System.out.println("promoterRefNo"+promoterRefNo+"cgpan"+cgpan+"userId"+userId+"memberId"+memberId);

		// claim Referance
		String pmrReferanceNo = request.getParameter("pmrReferanceNo");
		String pcgpan = request.getParameter("pcgpan");
		String cgtmscUserId = request.getParameter("cgtmscUserId");
		String cgpanMemberId = request.getParameter("cgpanMemberId");
		// System.out.println("pmrReferanceNo="+pmrReferanceNo+"pcgpan="+pcgpan+"cgtmscUserId="+cgtmscUserId+"cgpanMemberId="+cgpanMemberId);

		// desision
		String decision = request.getParameter("decision");
		String cgtmscReferanceNo = request.getParameter("cgtmscReferanceNo");
		String cgtmscCgpan = request.getParameter("cgtmscCgpan");
		// String claimStatus=request.getParameter("claimStatus");
		String cgtmscId = request.getParameter("cgtmscId");
		String approveClaimAmount = request.getParameter("approveClaimAmount");
		String cgtmscRemarks = request.getParameter("cgtmscRemarks");
		// System.out.println("cgtmscReferanceNo="+cgtmscReferanceNo+"&cgtmscCgpan="+cgtmscCgpan+"&claimStatus="+decision+"&cgtmscId="+cgtmscId+"&approveClaimAmount="+approveClaimAmount+"&cgtmscRemarks="+cgtmscRemarks+"&decision="+decision);

		try 
		{
			if (conn == null) {
				conn = DBConnection.getConnection(false);
			}
			/*
			 * approvalDtls = getApprovalDetails(conn,form1);
			 * if(approvalDtls!=null){ request.setAttribute("approvalDtls",
			 * approvalDtls); }
			 */

			if ((bankId != null) && !(bankId.equals(""))) 
			{
				if ((selectedClaim == null || selectedClaim.trim().length() == 0) || "1".equals(selectedClaim)) {
					approvalApplicationDtls = getApprovalApplicationDtls(bankId, "1", request, response, form1);
					if (approvalApplicationDtls != null) {
						request.setAttribute("selectedClaim", "1");
						request.setAttribute("approvalApplicationDtls", approvalApplicationDtls);
					}
				} else {
					approvalApplicationDtls = getSecondClaimDtls(bankId, "2", request, response, form1);
					request.setAttribute("selectedClaim", "2");
					request.setAttribute("bankId", bankId);
					if (approvalApplicationDtls != null) {
						request.setAttribute("approvalApplicationDtls", approvalApplicationDtls);
					}
				}
			}

			if ((promoterRefNo != null) && !(promoterRefNo.equals("")) && (cgpan != null) && !(cgpan.equals(""))
					&& (userId != null) && !(userId.equals("")) && (memberId != null) && !(memberId.equals(""))) 
			{
				form1.setMemberId(memberId);
				checkListDtls = getCheckListDtls(mapping, form1, request, response, promoterRefNo, cgpan, userId,
						memberId);
				request.setAttribute("checkListDtls", checkListDtls);

			}
			
			if ((cgtmscReferanceNo != null) && !(cgtmscReferanceNo.equals("")) && (cgtmscCgpan != null)
					&& !(cgtmscCgpan.equals("")) && (decision != null) && !(decision.equals("")) && (cgtmscId != null)
					&& !(cgtmscId.equals(""))) 
			{
				message = updateClaimProcessingApproval(userId, request, mapping, response, cgtmscReferanceNo,
						cgtmscCgpan, decision, cgtmscId, approveClaimAmount, cgtmscRemarks);
			}

			if ((pmrReferanceNo != null) && !(pmrReferanceNo.equals("")) && (pcgpan != null) && !(pcgpan.equals(""))
					&& (cgtmscUserId != null) && !(cgtmscUserId.equals("")) && (cgpanMemberId != null)
					&& !(cgpanMemberId.equals(""))) 
			{
				form1.setMemberId(cgpanMemberId);
				claimList = getCheckListInfo(form, request, response, pmrReferanceNo, pcgpan, cgtmscUserId,cgpanMemberId);
				request.setAttribute("bankClaimDataList", claimList);
			}
		} 
		catch (Exception err) 
		{
			err.printStackTrace();
			Log.log(2, "ClaimProcessingApprovalAction", "WriteToFile", err.getMessage());
			form1.setMessage(err.getMessage());
		}
		finally
		{
			try {
				if (conn != null) {
					DBConnection.freeConnection(conn);
				}
			} catch (Exception e) {
				System.out.println(e.getMessage());
			}
		}
		if (message != null && message.trim().length() >0) {
			form1.setMessage(message);// return mapping.findForward("getchecklist");
		}
		form1.setCgpan(pcgpan);
		/*
		 * if(approvalDtls != null) { return
		 * mapping.findForward("claimProcessingPageApproval"); }
		 */
		if (approvalApplicationDtls != null) {
			form1.setSelectedClaim(selectedClaim);
			return mapping.findForward("success");}
		if (checkListDtls != null) {
			return mapping.findForward("sucessCheckList");}
		if (claimList != null) {
			return mapping.findForward("getchecklist");}
		return mapping.findForward("claimProcessingPageApproval");
	}

	public ActionForward getApprovalDetails(ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		ArrayList<ClaimProcessingApprovalActionForm> approvalDtls = new ArrayList();
		ClaimProcessingApprovalActionForm form1 = (ClaimProcessingApprovalActionForm) form;
		ClaimProcessingApprovalActionForm data = null;
		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		String selectedClaim = "", query = "";
		try {
			if (conn == null) {
				conn = DBConnection.getConnection(false);
			}
			selectedClaim = form1.getSelectedClaim();
			if ((selectedClaim == null || selectedClaim.trim().length() == 0) || ("1".equals(selectedClaim))) {
				query = "SELECT MI.MEM_BNK_ID,MI.MEM_BANK_NAME, COUNT(1) AS NOOFAPPLICATION FROM SUBDEBT_APPLICATION_DETAIL A "
						+ "LEFT JOIN  SUBDEBT_PROMOTER_DETAIL B ON  A.APP_REF_NO=B.APP_REF_NO "
						+ "LEFT JOIN MEMBER_INFO MI ON A.MEM_BNK_ID=MI.MEM_BNK_ID AND A.MEM_ZNE_ID=MI.MEM_ZNE_ID AND A.MEM_BRN_ID=MI.MEM_BRN_ID "
						+ "LEFT JOIN SUBDEBT_CLAIMPROCESSING CL ON A.CGPAN=CL.CGPAN AND B.PMR_REF_NO=CL.PMR_REF_NO "
						+ "WHERE CL.CHECKER_STATUS='APPROVE'  and cl.CHECKER_STATUS ='Approve' and cl.CGTMSC_Status is null GROUP BY MI.MEM_BNK_ID,MI.MEM_BANK_NAME";
			} else {
				query = "SELECT MI.MEM_BNK_ID,MI.MEM_BANK_NAME, COUNT(1) AS NOOFAPPLICATION FROM SUBDEBT_APPLICATION_DETAIL A "
						+ "LEFT JOIN SUBDEBT_PROMOTER_DETAIL B ON A.APP_REF_NO=B.APP_REF_NO LEFT JOIN MEMBER_INFO MI ON A.MEM_BNK_ID=MI.MEM_BNK_ID AND "
						+ "A.MEM_ZNE_ID=MI.MEM_ZNE_ID AND A.MEM_BRN_ID=MI.MEM_BRN_ID LEFT JOIN subdebt_secondclaimprocessing CL ON A.CGPAN=CL.CGPAN "
						+ "AND B.PMR_REF_NO=CL.PMR_REF_NO WHERE CL.CHECKER_STATUS='APPROVE'  and cl.CGTMSC_Status is null GROUP BY MI.MEM_BNK_ID,MI.MEM_BANK_NAME";
			}
			pstmt = conn.prepareStatement(query);
			rs = pstmt.executeQuery();
			if (rs != null) {
				while (rs.next()) {
					data = new ClaimProcessingApprovalActionForm();
					data.setBankId(rs.getString("MEM_BNK_ID"));
					data.setBankName(rs.getString("MEM_BANK_NAME"));
					data.setNoOfApplication(rs.getString("NOOFAPPLICATION"));
					approvalDtls.add(data);
				}
			}
		} catch (Exception err) {
			err.printStackTrace();
			Log.log(2, "ClaimProcessingApprovalAction", "WriteToFile", err.getMessage());
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
				if (pstmt != null) {
					pstmt.close();
					pstmt = null;
				}
				if (conn != null) {
					DBConnection.freeConnection(conn);
				}
			} catch (Exception e) {
				System.out.println(e.getMessage());
			}
		}
		request.setAttribute("approvalDtls", approvalDtls);
		return mapping.findForward("claimProcessingPageApproval");
	}

	public ArrayList<ClaimProcessingApprovalActionForm> getSecondClaimDtls(String bankId, String selectedClaim,
			HttpServletRequest request, HttpServletResponse respons, ClaimProcessingApprovalActionForm form) {
		ArrayList<ClaimProcessingApprovalActionForm> approvalDtls = new ArrayList();
		ClaimProcessingApprovalActionForm approvalApplDtls = null;
		CallableStatement callableStmt = null;
		ResultSet rs = null;
		User user = getUserInformation(request);
		int status = -1;
		String errorCode = null;
		String message = "";
		Connection conn = null;
		int count = 0;
		try {
			if (conn == null) {
				conn = DBConnection.getConnection(false);
			}
			callableStmt = conn.prepareCall("{call FUNC_GET_FIRSTCLAIM_APPROVAL(?,?,?,?)}");
			callableStmt.registerOutParameter(1, Types.VARCHAR);
			callableStmt.setString(2, bankId);
			callableStmt.setInt(3, 2);
			callableStmt.registerOutParameter(4, Types.VARCHAR);
			callableStmt.execute();

			status = callableStmt.getInt(1);
			errorCode = callableStmt.getString(4);

			if (status == Constants.FUNCTION_FAILURE) {

				Log.log(Log.ERROR, "CPDAO", "getNpaMarkingSearchDetail()",
						"getNpaMarkingSearchDetail returns a 1. Error code is :" + errorCode);
				message = errorCode;
				if (callableStmt != null) {
					callableStmt.close();
				}
				if (conn != null) {
					conn.rollback();
				}
				form.setMessage(message);
			} else if (status == Constants.FUNCTION_SUCCESS) {
				rs = callableStmt.executeQuery();
				while (rs.next()) {
					approvalApplDtls = new ClaimProcessingApprovalActionForm();
					count++;

					approvalApplDtls.setSrNo(count);
					approvalApplDtls.setSelectedClaim("2");
					approvalApplDtls.setBankName(rs.getString("Bank_Name"));
					approvalApplDtls.setZoneName(rs.getString("ZoneName"));
					approvalApplDtls.setPromotoerReferanceNo(rs.getString("PMR_REF_NO"));// 1
					approvalApplDtls.setCgpan(rs.getString("CGPAN"));// 2
					approvalApplDtls.setPromoterName(rs.getString("PROMOTERNAME"));
					approvalApplDtls.setMemberId(rs.getString("MEMBER_ID"));// 4
					approvalApplDtls.setClaimRefNumber(rs.getString("CLAIM_REF_NUMBER"));// 5
					approvalApplDtls.setClaimStatus(rs.getString("CLAIM_STATUS"));// 6
					approvalApplDtls.setUnitName(rs.getString("UNIT_NAME"));// 7
					approvalApplDtls.setApproveAmount(rs.getDouble("APPROVED_AMT"));// 9
					approvalApplDtls.setOsAmtAsOnNpa(rs.getDouble("OS_AMT_AS_ON_NPA"));// 10
					approvalApplDtls.setRecoveryAsOnNpa(rs.getDouble("RECOVERY_AS_ON_NPA"));// 11
					approvalApplDtls.setNet_Outstanding(rs.getDouble("Net_Outstanding"));// 12
					approvalApplDtls.setExtent_of_guarantee(rs.getString("Extent_of_guarantee"));// 13
					approvalApplDtls.setCgtmse_liability(rs.getDouble("CGTMSE_Liability"));// 14
					approvalApplDtls.setElegibleAmtFirstInst(rs.getDouble("ELIGIBL_AMT_FIRST_INST"));// 15
					approvalApplDtls.setRecovery_Recived(rs.getDouble("Recovery_recived"));// 16
					approvalApplDtls.setLegal_Expenses(rs.getDouble("Legal_Expenses"));// 17
					approvalApplDtls.setNetRecovery_RemittedtoCGTMSE(rs.getDouble("NetRecovery_remittedtoCGTMSE"));// 18
					approvalApplDtls.setNet_Amount_in_Default(rs.getDouble("Net_Amount_in_Default"));// 19
					approvalApplDtls.setCGTMSE_liability_II(rs.getDouble("CGTMSE_liability_II"));// 20
					approvalApplDtls.setSecond_installment_payable(rs.getDouble("Second_installment_payable"));// 21
					approvalApplDtls.setFinal_Claim_amount(rs.getDouble("Final_Claim_amount"));// 22
					approvalApplDtls.setDecision(rs.getString("DECISION"));// 23
					approvalApplDtls.setComments(rs.getString("COMMENTS"));// 24
					approvalApplDtls.setRecomendation(rs.getString("RECOMMONDATION"));// 25
					approvalApplDtls.setIsRefund(rs.getInt("Isrefund"));// 26
					approvalDtls.add(approvalApplDtls);

				}
			}
			/*approvalDtls.forEach((mliRecoveryData1) -> {
				System.out.println(mliRecoveryData1.getSrNo() + "\t" + mliRecoveryData1.getClaimRefNumber()
						+ mliRecoveryData1.getCgtmse_liability());
			});*/
		} catch (Exception err) {
			Log.log(2, "ClaimProcessingApprovalAction", "WriteToFile", err.getMessage());
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

	public ActionForward saveSecondClaimDtls(ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) {
		ClaimProcessingApprovalActionForm form1 = (ClaimProcessingApprovalActionForm) form;
		ArrayList<ClaimProcessingApprovalActionForm> approvalApplicationDtls = null;
		User user = getUserInformation(request);
		Connection conn = null;
		CallableStatement callableStmt = null;
		int status = -1;
		String cgpan = "", claimRefNumber = "", promotoerReferanceNo = "", secClmdecision = "", secClmCommect = "",
				errorCode = "", message = "", bankId = "";
		double final_Claim_amount = 0.0, elegibleAmtFirstInst = 0.0, second_installment_payable = 0.0,
				recovery_Recived = 0.0;
		try {
			if (conn == null) {
				conn = DBConnection.getConnection(false);
			}
			cgpan = request.getParameter("cgpan");
			claimRefNumber = request.getParameter("claimRefNumber");
			promotoerReferanceNo = request.getParameter("promotoerReferanceNo");
			secClmdecision = request.getParameter("secClmdecision");
			secClmCommect = request.getParameter("secClmCommect");
			final_Claim_amount = Double.parseDouble(request.getParameter("final_Claim_amount"));
			elegibleAmtFirstInst = Double.parseDouble(request.getParameter("elegibleAmtFirstInst"));
			recovery_Recived = Double.parseDouble(request.getParameter("recovery_Recived"));
			second_installment_payable = Double.parseDouble(request.getParameter("second_installment_payable"));
			bankId = request.getParameter("bankId");

			callableStmt = conn.prepareCall("{call FUNC_INSERT_SECOND_CLAIM_APPROVAL_DETAILS(?,?,?,?,?,?,?,?,?,?,?,?,?)}");
			callableStmt.registerOutParameter(1, Types.VARCHAR);
			callableStmt.setString(2, cgpan);
			callableStmt.setString(3, promotoerReferanceNo);
			callableStmt.setString(4, claimRefNumber);
			callableStmt.setString(5, user.getUserId());
			callableStmt.setString(6, secClmdecision);
			callableStmt.setString(7, secClmCommect);
			callableStmt.setDouble(8, final_Claim_amount);
			callableStmt.setDouble(9, elegibleAmtFirstInst);
			callableStmt.setDouble(10, recovery_Recived);
			callableStmt.setDouble(11, second_installment_payable);
			callableStmt.setString(12, "CHECKER");
			callableStmt.registerOutParameter(13, Types.VARCHAR);
			callableStmt.execute();

			status = callableStmt.getInt(1);
			errorCode = callableStmt.getString(13);
			if (status == Constants.FUNCTION_FAILURE) {
				Log.log(Log.ERROR, "CPDAO", "saveNpaDataValues()",
						"saveNpaDataValues returns a 1. Error code is :" + errorCode);
				message = errorCode;

				if (callableStmt != null) {
					callableStmt.close();
					callableStmt = null;
				}
				if (conn != null) {
					conn.rollback();
				}
			} else if (status == Constants.FUNCTION_SUCCESS) {
				message = errorCode;
			}
		} catch (Exception err) {
			Log.log(2, "ClaimProcessingApprovalAction", "WriteToFile", err.getMessage());
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
				System.out.println(e.getMessage());
			}
		}
		approvalApplicationDtls = getSecondClaimDtls(bankId, "2", request, response, form1);
		request.setAttribute("bankId", bankId);
		request.setAttribute("approvalApplicationDtls", approvalApplicationDtls);
		if (message != null && message.trim().length() > 0) {
			form1.setMessage(message);}
		return mapping.findForward("success");
	}

	public ArrayList<ClaimProcessingApprovalActionForm> getApprovalApplicationDtls(String bankId, String selectedClaim,
			HttpServletRequest request, HttpServletResponse respons, ClaimProcessingApprovalActionForm form) {

		ArrayList<ClaimProcessingApprovalActionForm> approvalDtls = new ArrayList();
		CallableStatement callableStmt = null;
		ResultSet rs = null;
		User user = getUserInformation(request);
		int status = -1;
		String errorCode = null;
		String message = "";
		Connection conn = null;
		int count = 0;
		try {
			if (conn == null) {
				conn = DBConnection.getConnection(false);
			}
			callableStmt = conn.prepareCall("{call FUNC_GET_FIRSTCLAIM_APPROVAL(?,?,?,?)}");
			callableStmt.registerOutParameter(1, Types.VARCHAR);
			callableStmt.setString(2, bankId);
			callableStmt.setInt(3, 1);
			callableStmt.registerOutParameter(4, Types.VARCHAR);
			callableStmt.execute();

			status = callableStmt.getInt(1);
			errorCode = callableStmt.getString(4);

			if (status == Constants.FUNCTION_FAILURE) {

				Log.log(Log.ERROR, "CPDAO", "getNpaMarkingSearchDetail()",
						"getNpaMarkingSearchDetail returns a 1. Error code is :" + errorCode);
				message = errorCode;
				if (callableStmt != null) {
					callableStmt.close();
				}
				if (conn != null) {
					conn.rollback();
				}
				form.setMessage(message);
			} else if (status == Constants.FUNCTION_SUCCESS) {

				rs = callableStmt.executeQuery();
			}
			while (rs.next()) {
				ClaimProcessingApprovalActionForm approvalApplDtls = new ClaimProcessingApprovalActionForm();
				count++;
				approvalApplDtls.setSrNo(count);
				approvalApplDtls.setSelectedClaim("1");
				approvalApplDtls.setPromotoerReferanceNo(rs.getString("PMR_REF_NO"));
				approvalApplDtls.setCgpan(rs.getString("CGPAN"));
				approvalApplDtls.setUserId(rs.getString("CHECKER_USERID"));
				approvalApplDtls.setMemberId(rs.getString("MEMBER_ID"));
				approvalApplDtls.setClaimRefNumber(rs.getString("CLAIM_REF_NUMBER"));
				approvalApplDtls.setClaimStatus(rs.getString("CLAIM_STATUS"));
				approvalApplDtls.setUnitName(rs.getString("UNIT_NAME"));
				approvalApplDtls.setApplicationRemark(rs.getString("APPLIATION_REMARK"));
				approvalApplDtls.setReturnRemark(rs.getString("RETURN_REMARK"));
				approvalApplDtls.setReturnRemarkDate(rs.getString("RETURN_REMARK_DATE"));
				approvalApplDtls.setApproveAmount(rs.getDouble("APPROVED_AMT"));
				approvalApplDtls.setOsAmtAsOnNpa(rs.getDouble("OS_AMT_AS_ON_NPA"));
				approvalApplDtls.setRecoveryAsOnNpa(rs.getDouble("RECOVERY_AS_ON_NPA"));
				approvalApplDtls.setElegibleAmtFirstInst(rs.getDouble("ELIGIBL_AMT_FIRST_INST"));
				approvalApplDtls.setDed(rs.getDouble("DED"));
				approvalApplDtls.setRefund(rs.getDouble("REFUND"));
				approvalApplDtls.setsTax(rs.getDouble("S_TAX"));
				approvalApplDtls.setSbhcess(rs.getDouble("SBHCESS"));
				approvalApplDtls.setKkCess(rs.getDouble("KK_CESS"));
				approvalApplDtls.setStateCode(rs.getString("STATE_CODE"));
				approvalApplDtls.setGstNo(rs.getString("GST_NO"));
				approvalApplDtls.setAsfRecievedOrNot(rs.getNString("ASF_RECEIVED_OR_NOT"));
				approvalApplDtls.setNetPaybleAsFirstInst(rs.getDouble("NET_PAYABLE_AS_FIRST_INST"));
				approvalApplDtls.setDecision(rs.getString("DECISION"));
				approvalApplDtls.setApprovedClaimAmnt(rs.getDouble("APPROVED_CLAIM_AMOUNT"));
				approvalApplDtls.setComments(rs.getString("COMMENTS"));
				approvalApplDtls.setRecomendation(rs.getString("RECOMMONDATION"));
				approvalDtls.add(approvalApplDtls);
			}
		} catch (Exception err) {
			Log.log(2, "ClaimProcessingApprovalAction", "WriteToFile", err.getMessage());
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

	public ArrayList<CheckListForm> getCheckListDtls(ActionMapping mapping, ClaimProcessingApprovalActionForm form1,
			HttpServletRequest request, HttpServletResponse respons, String promoterRefNo, String cgpan, String userId,
			String memberId) {

		ArrayList<CheckListForm> checkListData = new ArrayList<CheckListForm>();
		CallableStatement callableStmt = null;
		ResultSet rs = null;

		User user = getUserInformation(request);
		int status = -1;
		String errorCode = null;
		String message = "";
		Connection conn = null;
		try {
			if (conn == null) {
				conn = DBConnection.getConnection(false);
			}
			callableStmt = conn.prepareCall("{call FUNC_GET_FIRSTCLAIM_DETAILS_PRINT(?,?,?,?,?,?)}");
			callableStmt.registerOutParameter(1, Types.VARCHAR);
			callableStmt.setString(2, cgpan);
			callableStmt.setString(3, promoterRefNo);
			callableStmt.setString(4, memberId);
			callableStmt.setString(5, userId);
			callableStmt.registerOutParameter(6, Types.VARCHAR);
			callableStmt.execute();

			status = callableStmt.getInt(1);
			errorCode = callableStmt.getString(6);

			if (status == Constants.FUNCTION_FAILURE) {

				Log.log(Log.ERROR, "CPDAO", "getNpaMarkingSearchDetail()",
						"getNpaMarkingSearchDetail returns a 1. Error code is :" + errorCode);
				message = errorCode;
				if (callableStmt != null) {
					callableStmt.close();
				}
				if (conn != null) {
					conn.rollback();
				}
				form1.setMessage(message);
			} else if (status == Constants.FUNCTION_SUCCESS) {

				rs = callableStmt.executeQuery();
			}
			if (rs.next()) {
				CheckListForm checklist = new CheckListForm();

				checklist.setcGPAN(rs.getString("CGPAN"));
				checklist.setsSIUNITNAME(rs.getString("SSI_UNIT_NAME"));
				checklist.setgUARANTEEAMT(rs.getString("GUARANTEE_AMT"));
				checklist.setOfficialRAMTRIP(rs.getString("Official_RAM_TRIP"));
				checklist.setmEMBERID(rs.getString("MEMBER_ID"));
				checklist.setDateofissue(rs.getString("Dateofissue"));
				checklist.setmLI_Name(rs.getString("MLI_Name"));
				checklist.setCheckList1(rs.getString("CheckList1"));
				checklist.setCheckList2(rs.getString("CheckList2"));
				checklist.setCheckList3(rs.getString("CheckList3"));
				checklist.setCheckList4(rs.getString("CheckList4"));
				checklist.setCheckList5(rs.getString("CheckList5"));
				checklist.setCheckList6(rs.getString("CheckList6"));
				checklist.setCheckList7(rs.getString("CheckList7"));
				checklist.setCheckList8(rs.getString("CheckList8"));
				checklist.setCheckList9(rs.getString("CheckList9"));
				checklist.setCheckList10(rs.getString("CheckList10"));
				checklist.setCheckList11(rs.getString("CheckList11"));
				checklist.setClaimRefNumber(rs.getString("Claim_Ref_Number"));
				checklist.setSysCurrentDDate(rs.getString("sys_current_Date"));
				checklist.setSysCrrentDate1(rs.getString("sys_current_Date1"));
				checkListData.add(checklist);
			}
		} catch (Exception err) {
			Log.log(2, "ClaimProcessingAction", "WriteToFile", err.getMessage());

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
		return checkListData;
	}

	public String updateClaimProcessingApproval(String user, HttpServletRequest request, ActionMapping mapping,
			HttpServletResponse response, String cgtmscReferanceNo, String cgtmscCgpan, String decision,
			String cgtmscId, String approveClaimAmount, String cgtmscRemarks) {
		Connection conn = null;
		String sqlQuery = "";
		CallableStatement callableStmt = null;
		ResultSet rs = null;
		String message = "";
		int status = -1;
		String errorCode = null;

		try {

			if (conn == null) {
				conn = DBConnection.getConnection(false);
			}
			/*
			 * PROCEDURE `FUNC_INSERT_CLAIM_APPROVAL_DETAILS`( OUT PFUNCRETURN
			 * INT , P_CGPAN VARCHAR(20), P_PMR_REF_NO VARCHAR(20), P_CGTMSC_ID
			 * VARCHAR(1), P_CGTMSC_Status VARCHAR(1), P_CGTMSC_Remark
			 * VARCHAR(500), P_Approve_Claim_amount DECIMAL(17,2), OUT
			 * POUTERRCODE VARCHAR(1000) )
			 */
			callableStmt = conn.prepareCall("{call FUNC_INSERT_CLAIM_APPROVAL_DETAILS(?,?,?,?,?,?,?,?,?)}");// 9
			callableStmt.registerOutParameter(1, Types.VARCHAR);
			callableStmt.setString(2, cgtmscCgpan);
			callableStmt.setString(3, cgtmscReferanceNo);
			callableStmt.setString(4, cgtmscId);
			callableStmt.setString(5, decision);
			callableStmt.setString(6, cgtmscRemarks);
			callableStmt.setString(7, approveClaimAmount);
			callableStmt.setString(8, "CHECKER");
			callableStmt.registerOutParameter(9, Types.VARCHAR);
			callableStmt.execute();

			status = callableStmt.getInt(1);
			errorCode = callableStmt.getString(9);
			if (status == Constants.FUNCTION_FAILURE) {
				Log.log(Log.ERROR, "CPDAO", "saveNpaDataValues()",
						"saveNpaDataValues returns a 1. Error code is :" + errorCode);
				message = errorCode;

				if (callableStmt != null) {
					callableStmt.close();
					callableStmt = null;
				}
				if (conn != null) {
					conn.rollback();
				}
			} else if (status == Constants.FUNCTION_SUCCESS) {
				message = "Data Updated Successfully!!";
			}
		} catch (Exception e) {
			e.printStackTrace();
			System.out.println(e.getMessage());
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
		// return mapping.findForward("disbursementDetailPage");
		return message;
	}

	public ArrayList<BankMandateClaimList> getCheckListInfo(ActionForm form, HttpServletRequest request,
			HttpServletResponse response, String pmrReferanceNo, String pcgpan, String cgtmscUserId,
			String cgpanMemberId) throws Exception {
		String id = "";
		Connection conn = null;
		CallableStatement callableStmt = null;
		ResultSet rs = null;
		int status = -1;
		String errorCode = "", message = "", cgpan = "", promoterName = "", memberId = "", userId = "", bankId = "",
				zoneId = "", branchId = "";
		ClaimProcessingApprovalActionForm form1 = (ClaimProcessingApprovalActionForm) form;
		User user = getUserInformation(request);
		ArrayList<BankMandateClaimList> claimList = new ArrayList<>();
		BankMandateClaimList claimData = new BankMandateClaimList();
		String checkerStatus = "";
		try {

			/* try { */
			if (conn == null) {
				conn = DBConnection.getConnection(false);
			}
			/*
			 * cgpan = "CG20200000008TC"; promoterName = "201001000000010";
			 * memberId = "002000010000"; userId = "SADBEHE0001";
			 */

			/*
			 * cgpan = form1.getCgpan(); promoterName = form1.getPromoterName();
			 * bankId = user.getBankId(); zoneId = user.getZoneId(); branchId =
			 * user.getBranchId(); memberId=bankId+zoneId+branchId; userId =
			 * user.getUserId();
			 */
			form1.setCgpan(cgpan);
			claimData.setCgpan(cgpan);
			claimData.setPromoterName(pmrReferanceNo);
			claimData.setMemberId(cgpanMemberId);
			claimData.setUserId(cgtmscUserId);

			callableStmt = conn.prepareCall("{call FUNC_GET_FIRSTCLAIM_DETAILS(?,?,?,?,?,?)}");

			callableStmt.registerOutParameter(1, Types.VARCHAR);
			callableStmt.setString(2, pcgpan);
			callableStmt.setString(3, pmrReferanceNo);
			callableStmt.setString(4, cgpanMemberId);
			callableStmt.setString(5, cgtmscUserId);
			callableStmt.registerOutParameter(6, Types.VARCHAR);
			callableStmt.execute();

			status = callableStmt.getInt(1);
			errorCode = callableStmt.getString(6);

			if (status == Constants.FUNCTION_FAILURE) {

				Log.log(Log.ERROR, "CPDAO", "getCheckListInfo()",
						"getCheckListInfo returns a 1. Error code is :" + errorCode);
				message = errorCode;
				if (callableStmt != null) {
					callableStmt.close();
				}
				if (conn != null) {
					conn.rollback();
				}
				// form1.setMessage(message);
			} else if (status == Constants.FUNCTION_SUCCESS) {

				rs = callableStmt.executeQuery();
			}
			if (rs.next()) {

				String roleName = rs.getString("ROLENAME");
				claimData.setRole(roleName);
				// request.setAttribute("role", roleName);
				// String referanceNo = rs.getString("APP_REF_NO");
				// String cgpansearch = rs.getString("CGPAN");
				// userId=rs.getString("USER_ID");

				String promoterNameValuee = rs.getString("PROMOTER_NAME");
				claimData.setPromoterNameValuee(promoterNameValuee);
				String promoterUnitName = rs.getString("SSI_UNIT_NAME");
				claimData.setPromoterUnitName(promoterUnitName);
				String promoterGuaranteeStartDate = rs.getString("GUARATNEE_START_DATE");
				claimData.setPromoterGuaranteeStartDate(promoterGuaranteeStartDate);
				String promoterSactionDate = rs.getString("SANCTION_DATE");
				claimData.setPromoterSactionDate(promoterSactionDate);
				double promoterGuranteeAmount = rs.getDouble("GUARANTEE_AMT");
				claimData.setPromoterGuranteeAmount(promoterGuranteeAmount);

				String branchMemberId = rs.getString("MEMBER_ID");
				claimData.setBranchMemberId(branchMemberId);
				String branchMliName = rs.getString("MLI_Name");
				claimData.setBranchMliName(branchMliName);
				String branchCity = rs.getString("City");
				claimData.setBranchCity(branchCity);
				String branchDistrict = rs.getString("District");
				claimData.setBranchDistrict(branchDistrict);
				String branchState = rs.getString("State");
				claimData.setBranchState(branchState);
				String branchDealingOfficerName = rs.getString("DealingOfficerName");
				claimData.setBranchDealingOfficerName(branchDealingOfficerName);
				String branchGstNo = rs.getString("GSTINNo");
				claimData.setBranchGstNo(branchGstNo);

				String borrowerCompleteAddress = rs.getString("BorrowerCompleteAddress");
				claimData.setBorrowerCompleteAddress(borrowerCompleteAddress);
				String borrowerCity = rs.getString("BorrowercITY");
				claimData.setBorrowerCity(borrowerCity);
				String borrowerDistrict = rs.getString("BorrowerdISTRICT");
				claimData.setBorrowerDistrict(borrowerDistrict);
				String borrowerState = rs.getString("BorrowerSTATE");
				claimData.setBorrowerState(borrowerState);
				String borrowerPinCode = rs.getString("BorrowerPINCODE");
				claimData.setBorrowerPinCode(borrowerPinCode);

				// Account Details

				String npaDate = rs.getString("NPA_DATE");
				claimData.setnPADate(npaDate);
				// request.setAttribute("date", npaDate);

				String accountsWilFulDefaulter = rs.getString("WilfuldefaulterYN");
				if (accountsWilFulDefaulter != null && !(accountsWilFulDefaulter.equals(""))) {
					if (accountsWilFulDefaulter.equals("Y")) {
						// request.setAttribute("accountsWilFulDefaulter",
						// "Yes");
						claimData.setAccountsWilFulDefaulter("Yes");
					} else {
						// request.setAttribute("accountsWilFulDefaulter",
						// "No");
						claimData.setAccountsWilFulDefaulter("No");
					}
				}

				String accountsClassified = rs.getString("classifiedasfraudYN");
				if (accountsClassified != null && !(accountsClassified.equals(""))) {
					if (accountsClassified.equals("Y")) {
						// request.setAttribute("accountsClassified", "Yes");
						claimData.setAccountsClassified("Yes");
					} else if (accountsClassified.equals("N")) {
						// request.setAttribute("accountsClassified", "No");
						claimData.setAccountsClassified("No");
					} else {
						// request.setAttribute("accountsClassified", "");
						claimData.setAccountsClassified("");
					}
				}

				String accountsEnquiry = rs.getString("InternalexternalenquiryYN");
				if (accountsEnquiry != null && !(accountsEnquiry.equals(""))) {
					if (accountsEnquiry.equals("Y")) {
						// request.setAttribute("accountsEnquiry", "Yes");
						claimData.setAccountsEnquiry("Yes");
					} else {
						// request.setAttribute("accountsEnquiry", "No");
						claimData.setAccountsEnquiry("No");
					}
				}
				String accountsMliReported = rs.getString("InvolvementofstaffYN");
				if (accountsMliReported != null && !(accountsMliReported.equals(""))) {
					if (accountsMliReported.equals("Y")) {
						// request.setAttribute("accountsMliReported", "Yes");
						claimData.setAccountsMliReported("Yes");
					} else {
						// request.setAttribute("accountsMliReported", "No");
						claimData.setAccountsMliReported("No");
					}
				}
				String accountReasonTurning = rs.getString("ReasonsforAccount");
				claimData.setAccountReasonTurning(accountReasonTurning);
				String accountSatisfactoryReason = rs.getString("satisfactoryreason");
				claimData.setAccountSatisfactoryReason(accountSatisfactoryReason);
				String dateofIssueofRecallNotice = rs.getString("Dateofissue");
				claimData.setDateofIssueofRecallNotice(dateofIssueofRecallNotice);
				request.setAttribute("dateofIssueofRecallNotice", dateofIssueofRecallNotice);

				// Legal Sectiopn

				String legalOtherForms = rs.getString("OtherForum");
				if (legalOtherForms != null && !(legalOtherForms.equals(""))) {

					// form1.setLegalOtherForms(legalOtherForms);
					claimData.setLegalOtherForms(legalOtherForms);
				} else {
					// form1.setLegalOtherForms("");
					claimData.setLegalOtherForms("");
				}
				String legalRegistration = rs.getString("RegistrationNo");
				claimData.setLegalRegistration(legalRegistration);
				String legalsuitFilingDate = rs.getString("Suit_Filing_Date");
				claimData.setLegalsuitFilingDate(legalsuitFilingDate);
				request.setAttribute("legalsuitFilingDate", legalsuitFilingDate);
				String legalSatisfactoryRreason = rs.getString("satisfactoryreason");
				claimData.setLegalSatisfactoryRreason(legalSatisfactoryRreason);
				String legalLocation = rs.getString("Location");
				claimData.setLegalLocation(legalLocation);
				double legalAmountClaimed = rs.getDouble("AmountClaimed");
				claimData.setLegalAmountClaimed(legalAmountClaimed);

				String legalProceedings = rs.getString("legalproceedings");
				claimData.setLegalProceedings(legalProceedings);
				String dateofpossessionofassets = rs.getString("Dateofpossessionofassets");
				claimData.setDateofpossessionofassets(dateofpossessionofassets);
				request.setAttribute("dateofpossessionofassets", dateofpossessionofassets);

				String filenameName = rs.getString("FILENAME");
				claimData.setFilenameName(filenameName);
				request.setAttribute("filenameName", filenameName);

				// Term Loans (TC)
				String tcTotalDisbursement = rs.getString("TotalDisbursementAmount");
				claimData.setTcTotalDisbursement(tcTotalDisbursement);
				String tcDateofLastDisbursement = rs.getString("DateofLastDisbursement");
				claimData.setTcDateofLastDisbursement(tcDateofLastDisbursement);
				String tcPrincipalAmount = rs.getString("RepaymentsPrincipalAmount");
				claimData.setTcPrincipalAmount(tcPrincipalAmount);
				String tcRepaymentsInterest = rs.getString("RepaymentsInterestandOtherCharges");
				claimData.setTcRepaymentsInterest(tcRepaymentsInterest);
				String tcOutstandingAmount = rs.getString("NPAOutstandingPrincipalAmount");
				claimData.setTcOutstandingAmount(tcOutstandingAmount);
				String tcOutstandingInterest = rs.getString("NPAOutstandingInterestandOtherCharges");
				claimData.setTcOutstandingInterest(tcOutstandingInterest);
				String tcOutstandingStatedCivil = rs.getString("Outstandingstated");
				claimData.setTcOutstandingStatedCivil(tcOutstandingStatedCivil);
				String tcOutstandingLodgement = rs.getString("OutstandingAsODateofLodgementofClaim");
				claimData.setTcOutstandingLodgement(tcOutstandingLodgement);
				String tcAccountRestructed = rs.getString("AccountRestructed");
				claimData.setTcAccountRestructed(tcAccountRestructed);

				// Recovery Details

				String recoveryPrincipal = rs.getString("RecoveryPrincipal");
				claimData.setRecoveryPrincipal(recoveryPrincipal);
				String recoveryInterest = rs.getString("RecoveryInterestOtherCharges");
				claimData.setRecoveryInterest(recoveryInterest);
				String recoveryMode = rs.getString("ModeofRecovery");
				claimData.setRecoveryMode(recoveryMode);

				// Security and Personal Guarantee
				String personalAmountclaim = rs.getString("Totalamountofguarantee");
				claimData.setPersonalAmountclaim(personalAmountclaim);

				String recoveryafterNPAindicated = rs.getString("recoveryafterNPAindicatedYN");
				if (recoveryafterNPAindicated != null && !(recoveryafterNPAindicated.equals(""))) {
					if (recoveryafterNPAindicated.equals("Y")) {
						// request.setAttribute("recoveryafterNPAindicated",
						// "Yes");
						claimData.setRecoveryafterNPAindicated("Yes");
					} else {
						// request.setAttribute("recoveryafterNPAindicated",
						// "No");
						claimData.setRecoveryafterNPAindicated("No");
					}
				}
				String valueasrecoveriesafterNPA = rs.getString("valueasrecoveriesafterNPAYN");
				if (valueasrecoveriesafterNPA != null && !(valueasrecoveriesafterNPA.equals(""))) {
					if (valueasrecoveriesafterNPA.equals("Y")) {
						// request.setAttribute("valueasrecoveriesafterNPA",
						// "Yes");
						claimData.setValueasrecoveriesafterNPA("Yes");
					} else {
						// request.setAttribute("valueasrecoveriesafterNPA",
						// "No");
						claimData.setValueasrecoveriesafterNPA("No");
					}
				}

				// general Information
				// MLI advise placing,Does the MLI propose

				String generalMLIComment = rs.getString("MLIsComment");
				claimData.setGeneralMLIComment(generalMLIComment);
				String generalMLIFinancial = rs.getString("DetailsofFinancialAssistance");
				claimData.setGeneralMLIFinancial(generalMLIFinancial);
				String generalMLIRemark = rs.getString("Remark");
				claimData.setGeneralMLIRemark(generalMLIRemark);
				String generalMLIBank = rs.getString("FacilityalreadyprovidedtoBorrower");
				claimData.setGeneralMLIBank(generalMLIBank);

				String creditsupportforanyotherproject = rs.getString("creditsupportforanyotherprojectYN");
				if (creditsupportforanyotherproject != null && !(creditsupportforanyotherproject.equals(""))) {
					if (creditsupportforanyotherproject.equals("Y")) {
						// request.setAttribute("creditsupportforanyotherproject",
						// "Yes");
						claimData.setCreditsupportforanyotherproject("Yes");
					} else {
						// request.setAttribute("creditsupportforanyotherproject",
						// "No");
						claimData.setCreditsupportforanyotherproject("No");
					}
				}
				String promoterunderwatchListofCGTMSE = rs.getString("PromoterunderwatchListofCGTMSEYN");
				if (promoterunderwatchListofCGTMSE != null && !(promoterunderwatchListofCGTMSE.equals(""))) {
					if (promoterunderwatchListofCGTMSE.equals("Y")) {
						// request.setAttribute("promoterunderwatchListofCGTMSE",
						// "Yes");
						claimData.setPromoterunderwatchListofCGTMSE("Yes");
					} else if (promoterunderwatchListofCGTMSE.equals("N")) {
						// request.setAttribute("promoterunderwatchListofCGTMSE",
						// "No");
						claimData.setPromoterunderwatchListofCGTMSE("No");
					} else {
						// request.setAttribute("promoterunderwatchListofCGTMSE",
						// "");
						claimData.setPromoterunderwatchListofCGTMSE("");
					}
				}

				// ==============land=================

				BigDecimal landSection = rs.getBigDecimal("LANDDATEOFSANCTION");
				claimData.setLandSection(landSection);
				double landDataOfNPA = rs.getDouble("LANDDATEOFNPA");
				claimData.setLandDataOfNPA(landDataOfNPA);
				double landDataOfClaim = rs.getDouble("LANDDATEOFCLAIM");
				claimData.setLandDataOfClaim(landDataOfClaim);
				double landNetWorth = rs.getDouble("LANDNETWORTH");
				claimData.setLandNetWorth(landNetWorth);
				String landReason = rs.getString("LANDREASONS");
				claimData.setLandReason(landReason);

				// =====bulding==============================

				BigDecimal buildingSection = rs.getBigDecimal("BUILDINGDATEOFSANCTION");
				claimData.setBuildingSection(buildingSection);
				double buildingDateOfNpa = rs.getDouble("BUILDINGDATEOFNPA");
				claimData.setBuildingDateOfNpa(buildingDateOfNpa);
				double buildingDateOfClaim = rs.getDouble("BUILDINGDATEOFCLAIM");
				claimData.setBuildingDateOfClaim(buildingDateOfClaim);
				double buildingNetWorth = rs.getDouble("BUILDINGNETWORTH");
				claimData.setBuildingNetWorth(buildingNetWorth);
				String buildingReason = rs.getString("BUILDINGREASONS");// BUILDINGREASONS=left
				claimData.setBuildingReason(buildingReason);

				// =======Plant and Machinery==========================

				double plantMachinerySection = rs.getDouble("PLANTMACHINERYDATEOFSANCTION");
				claimData.setPlantMachinerySection(plantMachinerySection);
				double plantMachineryDateNpa = rs.getDouble("PLANTMACHINERYDATEOFNPA");
				claimData.setPlantMachineryDateNpa(plantMachineryDateNpa);
				double plantMachineryClaim = rs.getDouble("PLANTMACHINERYDATEOFCLAIM");
				claimData.setPlantMachineryClaim(plantMachineryClaim);
				double plantMachineryNetWorth = rs.getDouble("PLANTMACHINERYNETWORTH");
				claimData.setPlantMachineryNetWorth(plantMachineryNetWorth);
				String plantMachineryReason = rs.getString("PLANTMACHINERYREASONS");// PLANTMACHINERYREASONS
				claimData.setPlantMachineryReason(plantMachineryReason); // left

				// ========other fixed

				double otherFixedMovableSection = rs.getDouble("OTHERFIXEDMOVABLEASSETSDATEOFSANCTION");
				claimData.setOtherFixedMovableSection(otherFixedMovableSection);
				double otherFixedMovableDateOfNpa = rs.getDouble("OTHERFIXEDMOVABLEASSETSDATEOFNPA");
				claimData.setOtherFixedMovableDateOfNpa(otherFixedMovableDateOfNpa);
				double otherFixedMovableClaim = rs.getDouble("OTHERFIXEDMOVABLEASSETSDATEOFCLAIM");
				claimData.setOtherFixedMovableClaim(otherFixedMovableClaim);
				double otherFixedMovableNetWorth = rs.getDouble("OTHERFIXEDMOVABLEASSETSNETWORTH");
				claimData.setOtherFixedMovableNetWorth(otherFixedMovableNetWorth);
				String otherFixedReason = rs.getString("OTHERFIXEDMOVABLEASSETSREASONS");
				claimData.setOtherFixedReason(otherFixedReason);

				// ================current Asset

				double currentAssetSection = rs.getDouble("CURRENTASSETSDATEOFSANCTION");
				claimData.setCurrentAssetSection(currentAssetSection);
				double currentAssetDateOfNpa = rs.getDouble("CURRENTASSETSDATEOFNPA");
				claimData.setCurrentAssetDateOfNpa(currentAssetDateOfNpa);
				double currentAssetClaim = rs.getDouble("CURRENTASSETSDATEOFCLAIM");
				claimData.setCurrentAssetClaim(currentAssetClaim);
				double currentAssetNetWorth = rs.getDouble("CURRENTASSETSNETWORTH");
				claimData.setCurrentAssetNetWorth(currentAssetNetWorth);
				String currentAssetReson = rs.getString("CURRENTASSETSREASONS");
				claimData.setCurrentAssetReson(currentAssetReson);

				// =================other section

				double otherSection = rs.getDouble("OTHERSDATEOFSANCTION");
				claimData.setOtherSection(otherSection);
				double otherNpa = rs.getDouble("OTHERSDATEOFNPA");
				claimData.setOtherNpa(otherNpa);
				double otherClaim = rs.getDouble("OTHERSDATEOFCLAIM");
				claimData.setOtherClaim(otherClaim);
				double otherNetWorth = rs.getDouble("OTHERSNETWORTH");
				claimData.setOtherNetWorth(otherNetWorth);
				String otherReason = rs.getString("OTHERSREASONS");// left
				claimData.setOtherReason(otherReason);

				BigDecimal dateOfSectionTotal = rs.getBigDecimal("DATEOFSANCTIONTOTAL");
				claimData.setDateOfSectionTotal(dateOfSectionTotal);
				BigDecimal dateOfNpaTotal = rs.getBigDecimal("DATEOFNPATOTAL");
				claimData.setDateOfNpaTotal(dateOfNpaTotal);
				BigDecimal netWorthTotal = rs.getBigDecimal("NETWORTHTOTAL");
				claimData.setNetWorthTotal(netWorthTotal);
				BigDecimal dateOfClaimTotal = rs.getBigDecimal("DATEOFClaimTOTAL");
				claimData.setDateOfClaimTotal(dateOfClaimTotal);

				checkerStatus = "APPROVE";// rs.getString("CHECKER_STATUS");

				// CheckList

				// if(roleName.equalsIgnoreCase("CHECKER")){
				String checkList1 = rs.getString("checkList1");
				claimData.setCheckList1(checkList1);

				if (checkList1 != null && !(checkList1.equals(""))) {
					if (checkList1.equals("Y")) {
						// request.setAttribute("checkList1", "Yes");
						claimData.setCheckList1("Yes");
					} else if (checkList1.equals("N")) {
						// request.setAttribute("checkList1", "No");
						claimData.setCheckList1("No");
					} else {
						// request.setAttribute("checkList1", "");
						claimData.setCheckList1("");
					}
				}
				String checkList2 = rs.getString("checkList2");

				if (checkList2 != null && !(checkList2.equals(""))) {
					if (checkList2.equals("Y")) {
						// request.setAttribute("checkList2", "Yes");
						claimData.setCheckList2("Yes");
					} else if (checkList2.equals("N")) {
						// request.setAttribute("checkList2", "No");
						claimData.setCheckList2("No");
					} else {
						// request.setAttribute("checkList2", "");
						claimData.setCheckList2("");
					}
				}

				String checkList3 = rs.getString("checkList3");
				if (checkList3 != null && !(checkList3.equals(""))) {
					if (checkList3.equals("Y")) {
						// request.setAttribute("checkList3", "Yes");
						claimData.setCheckList3("Yes");
					} else if (checkList3.equals("N")) {
						// request.setAttribute("checkList3", "No");
						claimData.setCheckList3("No");
					} else {
						// request.setAttribute("checkList3", "");
						claimData.setCheckList3("");
					}
				}

				String checkList4 = rs.getString("checkList4");
				if (checkList4 != null && !(checkList4.equals(""))) {
					if (checkList4.equals("Y")) {
						// request.setAttribute("checkList4", "Yes");
						claimData.setCheckList4("Yes");
					} else if (checkList4.equals("N")) {
						// request.setAttribute("checkList4", "No");
						claimData.setCheckList4("No");
					} else {
						// request.setAttribute("checkList4", "");
						claimData.setCheckList4("");
					}
				}
				String checkList5 = rs.getString("checkList5");
				if (checkList5 != null && !(checkList5.equals(""))) {
					if (checkList5.equals("Y")) {
						// request.setAttribute("checkList5", "Yes");
						claimData.setCheckList5("Yes");
					} else if (checkList5.equals("N")) {
						// request.setAttribute("checkList5", "No");
						claimData.setCheckList5("No");
					} else {
						// request.setAttribute("checkList5", "");
						claimData.setCheckList5("");
					}
				}
				String checkList6 = rs.getString("checkList6");
				if (checkList6 != null && !(checkList6.equals(""))) {
					if (checkList6.equals("Y")) {
						// request.setAttribute("checkList6", "Yes");
						claimData.setCheckList6("Yes");
					} else if (checkList6.equals("N")) {
						// request.setAttribute("checkList6", "No");
						claimData.setCheckList6("No");
					} else {
						// request.setAttribute("checkList6", "");
						claimData.setCheckList6("");
					}
				}

				String checkList7 = rs.getString("checkList7");
				if (checkList7 != null && !(checkList7.equals(""))) {
					if (checkList7.equals("Y")) {
						// request.setAttribute("checkList7", "Yes");
						claimData.setCheckList7("Yes");
					} else if (checkList7.equals("N")) {
						// request.setAttribute("checkList7", "No");
						claimData.setCheckList7("No");
					} else {
						// request.setAttribute("checkList7", "");
						claimData.setCheckList7("");
					}
				}

				String checkList8 = rs.getString("checkList9");
				if (checkList8 != null && !(checkList8.equals(""))) {
					if (checkList8.equals("Y")) {
						// request.setAttribute("checkList8", "Yes");
						claimData.setCheckList8("Yes");
					} else if (checkList8.equals("N")) {
						// request.setAttribute("checkList8", "No");
						claimData.setCheckList8("No");
					} else {
						// request.setAttribute("checkList8", "");
						claimData.setCheckList8("");
					}
				}

				String checkList9 = rs.getString("checkList9");
				if (checkList9 != null && !(checkList9.equals(""))) {
					if (checkList9.equals("Y")) {
						// request.setAttribute("checkList9", "Yes");
						claimData.setCheckList9("Yes");
					} else if (checkList9.equals("N")) {
						// request.setAttribute("checkList9", "No");
						claimData.setCheckList9("No");
					} else {
						// request.setAttribute("checkList9", "");
						claimData.setCheckList9("");
					}
				}
				String checkList10 = rs.getString("checkList10");
				if (checkList10 != null && !(checkList10.equals(""))) {
					if (checkList10.equals("Y")) {
						// request.setAttribute("checkList10", "Yes");
						claimData.setCheckList10("Yes");
					} else if (checkList10.equals("N")) {
						// request.setAttribute("checkList10", "No");
						claimData.setCheckList10("No");
					} else {
						// request.setAttribute("checkList10", "");
						claimData.setCheckList10("");
					}
				}
				String checkList11 = rs.getString("checkList11");
				if (checkList11 != null && !(checkList11.equals(""))) {
					if (checkList11.equals("Y")) {
						// request.setAttribute("checkList11", "Yes");
						claimData.setCheckList11("Yes");
					} else if (checkList11.equals("N")) {
						// request.setAttribute("checkList11", "No");
						claimData.setCheckList11("No");
					} else {
						// request.setAttribute("checkList11", "");
						claimData.setCheckList11("");
					}
				}
			}
			claimList.add(claimData);

			/*
			 * } catch (Exception err) { message = err.getMessage(); Log.log(2,
			 * "BankMandateAction", "getCheckListInfo", err.getMessage());
			 * err.printStackTrace(); try { if(conn != null){conn.rollback();};
			 * } catch (SQLException ignore) {}
			 */

			// }
		} catch (Exception err) {
			Log.log(2, "BankMandateAction", "getCheckListInfo", err.getMessage());
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
		return claimList;
	}

	public ActionForward downloadFile(ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		Connection conn = null;
		ResultSet rs = null;
		PreparedStatement stmt = null;
		byte[] fileData = null;
		String fileName = null;
		try {

			String cgpan = request.getParameter("cgPan");
			// Integer claimId =
			// Integer.parseInt(request.getParameter("claimId"));*/
			// System.out.println("CGPAN is ::::" + cgpan);

			if ((cgpan != null && !cgpan.isEmpty())) {
				conn = DBConnection.getConnection();
				String sqlQuery = "select * from  subdebt_claimprocessing where CGPAN=?";
				stmt = conn.prepareStatement(sqlQuery);
				stmt.setString(1, cgpan);
				rs = stmt.executeQuery();
				if (rs.next()) {
					fileData = rs.getBytes("ATTACHMENTS");
					fileName = rs.getString("FILENAME");
				}
				// System.out.println("fileName ::"+fileName + "fileData :::"
				// +fileData);
				response.setContentType("application/pdf");
				response.addHeader("Content-Disposition", "attachment; filename=" + fileName);
				OutputStream os = response.getOutputStream();
				os.write(fileData);
				os.close();
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				if (conn != null) {
					DBConnection.freeConnection(conn);
				}
				if (stmt != null) {
					stmt.close();
				}
				if (rs != null) {
					rs.close();
				}

			} catch (Exception e2) {
				e2.printStackTrace();
			}
		}
		return mapping.findForward("");
	}
}