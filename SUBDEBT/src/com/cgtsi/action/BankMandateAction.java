package com.cgtsi.action;

import java.io.File;
import java.io.FileInputStream;
import java.io.OutputStream;
import java.math.BigDecimal;
import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Types;
import java.util.*;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import com.cgtsi.actionform.BankMandateActionForm;
import com.cgtsi.actionform.BankMandateClaimList;
import com.cgtsi.actionform.ClaimProcessingActionForm;
import com.cgtsi.actionform.DisbursementActionForm;
import com.cgtsi.actionform.PopuLateData;
import com.cgtsi.admin.User;
import com.cgtsi.common.Constants;
import com.cgtsi.common.Log;
import com.cgtsi.util.DBConnection;
import com.cgtsi.util.PropertyLoader;

public class BankMandateAction extends BaseAction {
	public ActionForward getBankMandateData(ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		BankMandateActionForm form1 = (BankMandateActionForm) form;
		User user = getUserInformation(request);
		Connection conn = null;
		CallableStatement callableStmt = null;
		ResultSet rs = null;
		String bankId = "", zoneId = "", branchId = "", userId = "", message = "", errorCode = "";
		String memberId = "", mliName = "", zoneName = "", nameOfBeneficiary = "", roleName = "", contactNo = "",
				mobileNo = "", emailId = "", beneficiaryBankName = "", accountType = "", branchCode = "", micrCode = "",
				accountNo = "", rtgsNo = "", neftNo = "", checker_status = "";
		int status = -1;

		bankId = user.getBankId();
		zoneId = user.getZoneId();
		branchId = user.getBranchId();
		userId = user.getUserId();

		// System.out.println("bankId :: ["+bankId+"]\t zoneId :: ["+zoneId+"]\t
		// branchId :: ["+branchId+"]\t userId :: ["+userId+"]");
		try {
			if ((bankId != null && "0000".equals(branchId)) && (zoneId != null && "0000".equals(zoneId))
					&& (branchId != null && "0000".equals(branchId))) {
				getBankMandateApprovalData(mapping, form, request, response);
				return mapping.findForward("bankMandateApproval");
			} else {
				if (conn == null) {
					conn = DBConnection.getConnection(false);
				}
				callableStmt = conn.prepareCall("{call FUNC_GET_BANK_MANDATE_DETAILS(?,?,?,?,?,?,?)}");
				callableStmt.registerOutParameter(1, Types.VARCHAR);
				callableStmt.setString(2, bankId);
				callableStmt.setString(3, zoneId);
				callableStmt.setString(4, branchId);
				callableStmt.setString(5, userId);
				callableStmt.setInt(6, 1);
				callableStmt.registerOutParameter(7, Types.VARCHAR);

				callableStmt.execute();
				status = callableStmt.getInt(1);
				errorCode = callableStmt.getString(7);

				if (status == Constants.FUNCTION_FAILURE) {
					Log.log(Log.ERROR, "CPDAO", "getBankMandateData()",
							"getBankMandateData returns a 1. Error code is :" + errorCode);
					message = errorCode;

					if (callableStmt != null) {
						callableStmt.close();
					}
					if (conn != null) {
						conn.rollback();
					}
				} else if (status == Constants.FUNCTION_SUCCESS) {
					rs = callableStmt.executeQuery();

					if (rs.next()) {
						memberId = rs.getString("MEMBER_ID");
						mliName = rs.getString("MLI_NAME");
						zoneName = rs.getString("ZONE_NAME");
						nameOfBeneficiary = rs.getString("NAMEOFBENEFICIARY");
						roleName = rs.getString("ROLENAME");
						checker_status = rs.getString("CHECKER_STATUS");
						System.out.println("RoleName is :::" + roleName);
						if ((roleName != null && "NOTHING".equalsIgnoreCase(roleName))
								|| (roleName != null && "CHECKER".equalsIgnoreCase(roleName))
								|| (checker_status != null && "RETURN".equalsIgnoreCase(checker_status)))
							;
						{
							contactNo = rs.getString("CONTACT_NO");
							mobileNo = rs.getString("MOBILE_NO");
							emailId = rs.getString("EMAIL_ID");
							beneficiaryBankName = rs.getString("BENEFICIARY_BANK_NAME");
							accountType = rs.getString("ACCOUNT_TYPE");
							branchCode = rs.getString("BRANCH_CODE");
							micrCode = rs.getString("MICR_CODE");
							accountNo = rs.getString("ACCOUNT_NO");
							rtgsNo = rs.getString("RTGS_NO");
							neftNo = rs.getString("NEFT_NO");

							form1.setContactNo(contactNo);
							form1.setMobileNo(mobileNo);
							form1.setEmailId(emailId);
							form1.setBeneficiaryBankName(beneficiaryBankName);
							form1.setAccountType(accountType);
							form1.setBranchCode(branchCode);
							form1.setMicrCode(micrCode);
							form1.setAccountNo(accountNo);
							form1.setRtgsNo(rtgsNo);
							form1.setNeftNo(neftNo);
						}
						form1.setMemberId(memberId);
						form1.setMliName(mliName);
						form1.setZoneName(zoneName);
						form1.setNameOfBeneficiary(nameOfBeneficiary);
						form1.setRoleName(roleName.toUpperCase());
						form1.setContactNo(contactNo);
						form1.setChecker_status(checker_status);
					}
				}
				return mapping.findForward("bankMandatePage");
			}
		} catch (Exception err) {
			message = err.getMessage();
			Log.log(2, "BankMandateAction", "WriteToFile", err.getMessage());
			err.printStackTrace();
			try {
				if (conn != null) {
					conn.rollback();
				}
			} catch (SQLException ignore) {}
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
		return null;
	}

	public ActionForward getBankMandateApprovalData(ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {

		BankMandateActionForm form1 = (BankMandateActionForm) form;
		User user = getUserInformation(request);
		Connection conn = null;
		CallableStatement callableStmt = null;
		ResultSet rs = null;
		String bankId = "", zoneId = "", branchId = "", userId = "", message = "", errorCode = "";
		String memberId = "", mliName = "", zoneName = "", nameOfBeneficiary = "", roleName = "", contactNo = "",
				mobileNo = "", emailId = "", beneficiaryBankName = "", accountType = "", branchCode = "", micrCode = "",
				accountNo = "", rtgsNo = "", neftNo = "", checker_status = "";
		int status = -1;
		int count = 0;
		ArrayList<String> list = null;
		LinkedHashMap<Integer, ArrayList<String>> bankMandateList = new LinkedHashMap<>();
		try {
			if (conn == null) {
				conn = DBConnection.getConnection(false);
			}

			bankId = user.getBankId();
			zoneId = user.getZoneId();
			branchId = user.getBranchId();
			userId = user.getUserId();
			form1.setCgtms_userId(userId);
			// System.out.println("bankId :: ["+bankId+"]\t zoneId ::
			// ["+zoneId+"]\t branchId :: ["+branchId+"]\t userId ::
			// ["+userId+"]");

			callableStmt = conn.prepareCall("{call FUNC_GET_BANK_MANDATE_DETAILS(?,?,?,?,?,?,?)}");
			callableStmt.registerOutParameter(1, Types.VARCHAR);
			callableStmt.setString(2, bankId);
			callableStmt.setString(3, zoneId);
			callableStmt.setString(4, branchId);
			callableStmt.setString(5, userId);
			callableStmt.setInt(6, 2);
			callableStmt.registerOutParameter(7, Types.VARCHAR);

			callableStmt.execute();
			status = callableStmt.getInt(1);
			errorCode = callableStmt.getString(7);

			if (status == Constants.FUNCTION_FAILURE) {
				Log.log(Log.ERROR, "CPDAO", "getBankMandateData()",
						"getBankMandateData returns a 1. Error code is :" + errorCode);
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
					list = new ArrayList<>();
					count++;
					memberId = rs.getString("MEMBER_ID");
					mliName = rs.getString("MLI_NAME");
					zoneName = rs.getString("ZONE_NAME");
					nameOfBeneficiary = rs.getString("NAMEOFBENEFICIARY");
					checker_status = rs.getString("CHECKER_STATUS");
					contactNo = rs.getString("CONTACT_NO");
					mobileNo = rs.getString("MOBILE_NO");
					emailId = rs.getString("EMAIL_ID");
					beneficiaryBankName = rs.getString("BENEFICIARY_BANK_NAME");
					accountType = rs.getString("ACCOUNT_TYPE");
					branchCode = rs.getString("BRANCH_CODE");
					micrCode = rs.getString("MICR_CODE");
					accountNo = rs.getString("ACCOUNT_NO");
					rtgsNo = rs.getString("RTGS_NO");
					neftNo = rs.getString("NEFT_NO");

					list.add(0, String.valueOf(count));
					list.add(1, memberId);
					list.add(2, mliName);
					list.add(3, nameOfBeneficiary);
					list.add(4, beneficiaryBankName);
					list.add(5, accountNo);
					list.add(6, rtgsNo);
					list.add(7, checker_status);

					bankMandateList.put(count, list);
				}
				request.setAttribute("bankMandateList", bankMandateList);
				
				/*bankMandateList.forEach((key, val) -> {

					System.out.println("Key is ::" + key + "\t" + "Value is ::" + val);
				});*/
			}
		} catch (Exception err) {
			message = err.getMessage();
			Log.log(2, "BankMandateAction", "WriteToFile", err.getMessage());
			err.printStackTrace();
			try {
				if (conn != null) {
					conn.rollback();
				}
			} catch (SQLException ignore) {}
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
		return mapping.findForward("bankMandateApproval");
	}

	public ActionForward saveBankMandateData(ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		Connection conn = null;
		CallableStatement callableStmt = null;
		User user = getUserInformation(request);
		BankMandateActionForm form1 = (BankMandateActionForm) form;
		ArrayList<String> bankMandateErr = new ArrayList<>();
		String bankId = "", zoneId = "", branchId = "", userId = "", message = "", errorCode = "";
		String memberId = "", mliName = "", zoneName = "", nameOfBeneficiary = "", roleName = "";
		String contactNo = "", mobileNo = "", emailId = "", beneficiaryBankName = "", accountType = "", branchCode = "",
				micrCode = "", accountNo = "", rtgsNo = "", neftNo = "", checker_status = "";
		int status = -1;
		try {
			if (conn == null) {
				conn = DBConnection.getConnection(false);
			}

			bankId = user.getBankId();
			zoneId = user.getZoneId();
			branchId = user.getBranchId();
			userId = user.getUserId();
			contactNo = form1.getContactNo();
			mobileNo = form1.getMobileNo();
			emailId = form1.getEmailId();
			beneficiaryBankName = form1.getBeneficiaryBankName();
			accountType = form1.getAccountType();
			branchCode = form1.getBranchCode();
			micrCode = form1.getMicrCode();
			accountNo = form1.getAccountNo();
			rtgsNo = form1.getRtgsNo();
			neftNo = form1.getNeftNo();
			roleName = form1.getRoleName();
			checker_status = form1.getChecker_status();
			// System.out.println("checker_status :::" + checker_status);

			callableStmt = conn.prepareCall("{call FUNC_INSERT_BANK_MANDATE_DETAILS(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)}");
			callableStmt.registerOutParameter(1, Types.VARCHAR);
			callableStmt.setString(2, bankId);
			callableStmt.setString(3, zoneId);
			callableStmt.setString(4, branchId);
			callableStmt.setString(5, contactNo);
			callableStmt.setString(6, mobileNo);
			callableStmt.setString(7, emailId);
			callableStmt.setString(8, beneficiaryBankName);
			callableStmt.setString(9, accountType);
			callableStmt.setString(10, branchCode);
			callableStmt.setString(11, micrCode);
			callableStmt.setString(12, accountNo);
			callableStmt.setString(13, rtgsNo);
			callableStmt.setString(14, neftNo);
			callableStmt.setString(15, userId);
			callableStmt.setString(16, checker_status);
			callableStmt.setString(17, roleName);
			callableStmt.registerOutParameter(18, Types.VARCHAR);
			callableStmt.execute();

			status = callableStmt.getInt(1);
			errorCode = callableStmt.getString(18);
			if (status == Constants.FUNCTION_FAILURE) {
				Log.log(Log.ERROR, "CPDAO", "getDisbursementSearchDetail()",
						"getDisbursementSearchDetail returns a 1. Error code is :" + errorCode);
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
			Log.log(2, "BankMandateAction", "saveBankMandateData", err.getMessage());
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
			} catch (Exception err) {
				System.out.println(err.getMessage());
				err.printStackTrace();
			}
		}
		if (message != null && message.trim().length() > 0) {
			form1.setMessage(message);
		}
		return mapping.findForward("bankMandatePage");
	}

	public ActionForward saveBankMandateApprovalData(ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		User user = getUserInformation(request);
		BankMandateActionForm form1 = (BankMandateActionForm) form;
		String cgtms_userId;
		String[] memberId;
		String[] cgtms_status;
		String[] cgtms_remark;
		int status = -1;
		String errorCode = "", message = "", userDetailData = "";
		Connection conn = null;
		CallableStatement callableStmt = null;
		String bankId = "", zoneId = "", branchId = "";
		ArrayList<String> memberIdData = new ArrayList<String>();
		ArrayList<String> remarkData = new ArrayList<String>();
		try {
			if (conn == null) {
				conn = DBConnection.getConnection(false);
			}

			cgtms_userId = user.getUserId();
			userDetailData = form1.getUserDetailData();
			memberId = request.getParameterValues("memberId");
			cgtms_status = request.getParameterValues("cgtms_status");
			cgtms_remark = request.getParameterValues("cgtms_remark");

			for (int j = 0; j < cgtms_remark.length; j++) {
				for (j = 0; j < memberId.length; j++) {
					if ((cgtms_remark[j] == "") && (memberId[j] != "")) {
						continue;
					}
					memberIdData.add(memberId[j]);
					remarkData.add(cgtms_remark[j]);
				}
			}
			Object[] memberIdNew = memberIdData.toArray();
			Object[] remarkDataNew = remarkData.toArray();

			for (int i = 0; i < cgtms_status.length; i++) {
				String memId = String.valueOf(memberIdNew[i]);
				bankId = memId.substring(0, 4);
				zoneId = memId.substring(4, 8);
				branchId = memId.substring(8, 12);

				callableStmt = conn.prepareCall("{call FUNC_INSERT_BANK_MANDATE_APPROVAL_DETAILS(?,?,?,?,?,?,?,?)}");
				callableStmt.registerOutParameter(1, Types.VARCHAR);
				callableStmt.setString(2, bankId);
				callableStmt.setString(3, zoneId);
				callableStmt.setString(4, branchId);
				callableStmt.setString(5, cgtms_userId);
				callableStmt.setString(6, userDetailData);
				callableStmt.setString(7, String.valueOf(remarkDataNew[i]));

				callableStmt.registerOutParameter(8, Types.VARCHAR);
				callableStmt.execute();
				status = callableStmt.getInt(1);
				errorCode = callableStmt.getString(8);

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
			getBankMandateApprovalData(mapping, form, request, response);
		} catch (Exception err) {
			Log.log(2, "BankMandateAction", "saveBankMandateData", err.getMessage());
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
			} catch (Exception err) {
				System.out.println(err.getMessage());
				err.printStackTrace();
			}
		}
		if (message != null && message.trim().length() > 0) {
			form1.setMessage(message);
		}
		return mapping.findForward("bankMandateApproval");
	}

	public ActionForward downloadFile(ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		BankMandateActionForm form1 = (BankMandateActionForm) form;
		String message = "";
		String filename = null;
		try {
			filename = request.getParameter("filename");
			// System.out.println("Inside downloadFile method..." + filename);

			if (filename != null && !filename.isEmpty()) {
				String contextPath1 = request.getSession(false).getServletContext().getRealPath("");
				// System.out.println("contextPath1 :"+contextPath1);

				String contextPath = PropertyLoader.changeToOSpath(contextPath1);
				// System.out.println(contextPath);

				String filepath = contextPath + File.separator + Constants.FILE_DOWNLOAD_DIRECTORY + File.separator
						+ filename;

				// System.out.println(filepath);

				File downloadFile = new File(filepath);
				FileInputStream readFile = new FileInputStream(downloadFile);

				ServletContext context = request.getSession(false).getServletContext();
				String mimeType = context.getMimeType(filepath);

				if (mimeType == null) {
					mimeType = "application/word";
				}
				// System.out.println("mimeType"+mimeType);

				response.setContentType(mimeType);
				response.setContentLength((int) downloadFile.length());

				String headerKey = "Content-Disposition";
				String headerValues = String.format("attachment; filename=\"%s\"", downloadFile.getName());
				response.setHeader(headerKey, headerValues);

				// obtains response's output stream
				OutputStream outStream = response.getOutputStream();
				byte[] buffer = new byte[1024];
				int bytesRead = -1;

				while ((bytesRead = readFile.read(buffer)) != -1) {
					outStream.write(buffer, 0, bytesRead);
				}
				readFile.close();
				outStream.close();
			}
		} catch (Exception err) {
			message = err.getMessage();
			Log.log(2, "BankMandateAction", "saveBankMandateData", err.getMessage());
			err.printStackTrace();
		}
		if (message != null && message.trim().length() > 0) {
			form1.setMessage(message);
		}
		return mapping.findForward("");
	}

	/*public ActionForward getCheckListInfo(ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		String id = "";
		Connection conn = null;
		CallableStatement callableStmt = null;
		ResultSet rs = null;
		int status = -1;
		String errorCode = "", message = "", cgpan = "", promoterName = "", memberId = "", userId = "", bankId = "",
				zoneId = "", branchId = "";
		BankMandateActionForm form1 = (BankMandateActionForm) form;
		User user = getUserInformation(request);
		ArrayList<BankMandateClaimList> claimList = new ArrayList<>();
		BankMandateClaimList claimData = new BankMandateClaimList();
		String checkerStatus = "";
		try {
			id = request.getParameter("id");
			if ("Success".equalsIgnoreCase(id)) {
				try {
					if (conn == null) {
						conn = DBConnection.getConnection(false);
					}
					cgpan = "CG20200000008TC";
					promoterName = "201001000000010";
					memberId = "002000010000";
					userId = "SADBEHE0001";

					cgpan = form1.getCgpan();
					promoterName = form1.getPromoterName();
					bankId = user.getBankId();
					zoneId = user.getZoneId();
					branchId = user.getBranchId();
					memberId = bankId + zoneId + branchId;
					userId = user.getUserId();

					claimData.setCgpan(cgpan);
					claimData.setPromoterName(promoterName);
					claimData.setMemberId(memberId);
					claimData.setUserId(userId);

					callableStmt = conn.prepareCall("{call FUNC_GET_FIRSTCLAIM_DETAILS(?,?,?,?,?,?)}");

					callableStmt.registerOutParameter(1, Types.VARCHAR);
					callableStmt.setString(2, cgpan);
					callableStmt.setString(3, promoterName);
					callableStmt.setString(4, memberId);
					callableStmt.setString(5, userId);
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
						form1.setMessage(message);
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
								// request.setAttribute("accountsClassified",
								// "Yes");
								claimData.setAccountsClassified("Yes");
							} else if (accountsClassified.equals("N")) {
								// request.setAttribute("accountsClassified",
								// "No");
								claimData.setAccountsClassified("No");
							} else {
								// request.setAttribute("accountsClassified",
								// "");
								claimData.setAccountsClassified("");
							}
						}

						String accountsEnquiry = rs.getString("InternalexternalenquiryYN");
						if (accountsEnquiry != null && !(accountsEnquiry.equals(""))) {
							if (accountsEnquiry.equals("Y")) {
								// request.setAttribute("accountsEnquiry",
								// "Yes");
								claimData.setAccountsEnquiry("Yes");
							} else {
								// request.setAttribute("accountsEnquiry",
								// "No");
								claimData.setAccountsEnquiry("No");
							}
						}
						String accountsMliReported = rs.getString("InvolvementofstaffYN");
						if (accountsMliReported != null && !(accountsMliReported.equals(""))) {
							if (accountsMliReported.equals("Y")) {
								// request.setAttribute("accountsMliReported",
								// "Yes");
								claimData.setAccountsMliReported("Yes");
							} else {
								// request.setAttribute("accountsMliReported",
								// "No");
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

							form1.setLegalOtherForms(legalOtherForms);
							claimData.setLegalOtherForms(legalOtherForms);
						} else {
							form1.setLegalOtherForms("");
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
					request.setAttribute("bankClaimDataList", claimList);
				} catch (Exception err) {
					message = err.getMessage();
					Log.log(2, "BankMandateAction", "getCheckListInfo", err.getMessage());
					err.printStackTrace();
					try {
						if (conn != null) {
							conn.rollback();
						}
						;
					} catch (SQLException ignore) {
					}

				}
				if (message != null && message.trim().length() > 0) {
					form1.setMessage(message);
				}
				return mapping.findForward("getchecklist");
			} else {
				if (message != null && message.trim().length() > 0) {
					form1.setMessage(message);
				}
				return mapping.findForward("bankMandatePage");
			}
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
		return null;
	}*/
}
