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

import com.cgtsi.actionform.CheckListForm;
import com.cgtsi.actionform.ClaimProcessingActionForm;
import com.cgtsi.actionform.NPAMarkingPopulateData;
import com.cgtsi.actionform.SecondClaimProcessingActionForm;
import com.cgtsi.admin.User;
import com.cgtsi.common.Constants;
import com.cgtsi.common.Log;
import com.cgtsi.util.DBConnection;
import com.mysql.cj.protocol.Resultset;

public class SecondClaimProcessingAction extends BaseAction {

	public ActionForward getSecondClaimProcessing(ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		Connection conn = null;
		PreparedStatement pstmt = null;
		Resultset rs = null;
		String message = null;

		SecondClaimProcessingActionForm form1 = (SecondClaimProcessingActionForm) form;
		// DynaValidatorActionForm appForm = (DynaValidatorActionForm) form;
		String cgpan = form1.getCgpan2();
		String claimRefNumber = form1.getClaimReferenceNumber();
		ArrayList promoter = new ArrayList();
		ArrayList legalProceedingsValue = new ArrayList();
		ArrayList reproductionValue = new ArrayList();
		User user = getUserInformation(request);
		String bankId = "";
		try {
			if (conn == null) {
				conn = DBConnection.getConnection(false);
			}
			if (cgpan != null && !cgpan.isEmpty()) {
				bankId = user.getBankId();
				promoter = getPromoterDetails(cgpan, conn, bankId);
				// System.out.println("Promoter Size :::" + promoter.size());
				if (promoter.size() == 0) {
					message = "Either CGPAN not pertaining to logged in User,Bank,MLI or No Promoter Details Found!!";
				}
				// System.out.println("HashMap Contains Values ::" + promoter +
				// "JSON Data ::::" + promoter);
				form1.setPromoterValues(promoter);
				form1.setCgpan2(cgpan);
				form1.setIsSearchClicked("");
			}
			else 
			{
				Map option = new HashMap();
				option.put("label", "");
				option.put("value", "Select");
				promoter.add(option);
				form1.setPromoterValues(promoter);
				form1.setIsSearchClicked("");

			}
			legalProceedingsValue = getLegalProceedings(conn);
			if (legalProceedingsValue != null) {
				form1.setLegalProceedingsValue(legalProceedingsValue);
			} else {
				Map option = new HashMap();
				option.put("label", "");
				option.put("value", "Select");
				reproductionValue.add(option);
				// form1.setReasonvalue(reproductionValue);
			}
		} 
		catch (Exception err) {
			try {
				if (conn != null) {
					conn.rollback();
				}
			} catch (SQLException ignore) {}
			Log.log(2, "ClaimProcessingAction", "WriteToFile", err.getMessage());
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
		}
		return mapping.findForward("secondClaimProcessingPage");
	}

	public ActionForward getClaimProcessingSearchDetail(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) throws Exception {

		SecondClaimProcessingActionForm form1 = (SecondClaimProcessingActionForm) form;
		ArrayList promoter = new ArrayList();
		ArrayList reason = new ArrayList();
		Connection conn = null;
		CallableStatement callableStmt = null;
		ResultSet rs = null;
		User user = getUserInformation(request);
		ArrayList creditRiskvalue = new ArrayList();
		ArrayList reproductionValue = new ArrayList();
		ArrayList legalProceedingsValue = new ArrayList();
		String checkerStatus = "";
		String errorCode = "", message = "", bankId = "";
		int status = -1;
		try {
			String cgpan = form1.getCgpan2();
			String promoterName = form1.getPromoterNamee();
			String isSearchClicked = form1.getIsSearchClicked();
			String claimRefNumberr = form1.getClaimReferenceNumber();

			if ((cgpan != null && !cgpan.isEmpty()) && (promoterName != null && !promoterName.isEmpty())
					|| (claimRefNumberr != null && !claimRefNumberr.isEmpty())) {
				if (conn == null) {
					conn = DBConnection.getConnection(false);
				}

				bankId = user.getBankId();
				String zoneId = user.getZoneId();
				String branchId = user.getBranchId();
				String memberId = bankId + zoneId + branchId;
				String userId = user.getUserId();
				System.out.println("Member id===66===" + memberId);

				promoter = getPromoterDetails(cgpan, conn, bankId);

				form1.setPromoterValues(promoter);

				if (promoter != null) {
					form1.setPromoterValues(promoter);
				} else {
					Map option = new HashMap();
					option.put("label", "");
					option.put("value", "Select");
					promoter.add(option);
					form1.setPromoterValues(promoter);
				}

				legalProceedingsValue = getLegalProceedings(conn);
				if (legalProceedingsValue != null) {
					form1.setLegalProceedingsValue(legalProceedingsValue);
				} else {
					Map option = new HashMap();
					option.put("label", "");
					option.put("value", "Select");
					reproductionValue.add(option);
					form1.setLegalProceedingsValue(legalProceedingsValue);
				}

				// CALL FUNC_GET_SECONDCLAIM_DETAILS(
				// @P,'CG20200000008TC','201001000000010','002000180000',null,'ALOPRIY0001',@Q);

				callableStmt = conn.prepareCall("{call FUNC_GET_SECONDCLAIM_DETAILS(?,?,?,?,?,?,?)}");

				callableStmt.registerOutParameter(1, Types.VARCHAR);
				if (cgpan != null && !(cgpan.equals(""))) {

					callableStmt.setString(2, cgpan);
				} else {
					callableStmt.setString(2, null);
				}

				callableStmt.setString(3, promoterName);
				callableStmt.setString(4, memberId);
				if (form1.getClaimReferenceNumber() != null && !(form1.getClaimReferenceNumber().equals(""))) {
					callableStmt.setString(5, form1.getClaimReferenceNumber());
				} else {
					callableStmt.setString(5, null);
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
					form1.setMessage(message);
				} else if (status == Constants.FUNCTION_SUCCESS) {

					rs = callableStmt.executeQuery();
				   if (rs != null && rs.next()) {

					String roleName = rs.getString("ROLENAME");
					request.setAttribute("role", roleName);
					// String referanceNo = rs.getString("APP_REF_NO");
					// String cgpansearch = rs.getString("CGPAN");
					// userId=rs.getString("USER_ID");
					String claimRefNumber = rs.getString("CLAIM_REF_NUMBER");
					String applicationReferenceNumber = rs.getString("APP_REF_NO");
					/*
					 * APP_REF_NO, CLAIM_REF_NUMBER, CGPAN, PMR_CGPAN,
					 * SSI_UNIT_NAME, UNIT_TYPE, CONSTITUTION, GUARANTEE_AMT,
					 * GUARATNEE_START_DATE, LAST_DISBURSEMENT_DATE,
					 * SANCTION_AMOUNT, SANCTION_DATE, PROMOTER_NAME, ITPAN,
					 * ADHAR_NUMBER, INVESTED_EQUITY, INVESTED_DEBT_LOAN, TOTAL,
					 * PMR_GUARANTEE_AMT, PMR_GUARATNEE_START_DATE, NPA_DATE,
					 * NETOUTSTANDINGAMOUNT, CLAIM_ELIGIBLEAMOUNT,
					 * FIRST_INSTALLMENTPAYAMOUNT, legalproceedings, OtherForum,
					 * RegistrationNo, Suit_Filing_Date, RecoveryAmount,
					 * FILENAME, ROLENAME
					 */

					String cGPan = rs.getString("CGPAN");
					String pmarCgpan = rs.getString("PMR_CGPAN");
					String unitName = rs.getString("SSI_UNIT_NAME");
					String typeofEntity = rs.getString("UNIT_TYPE");
					String constitution = rs.getString("CONSTITUTION");
					double guaranteeAmount = rs.getDouble("GUARANTEE_AMT");
					String guaranteeStartDate = rs.getString("GUARATNEE_START_DATE");
					String lastDisbursementDate = rs.getString("LAST_DISBURSEMENT_DATE");
					double sanctionAmount = rs.getDouble("SANCTION_AMOUNT");
					String sanctionDate = rs.getString("SANCTION_DATE");

					String iTPAN = rs.getString("ITPAN");
					String adharNumber = rs.getString("ADHAR_NUMBER");
					String investedEquity = rs.getString("INVESTED_EQUITY");
					String investedDebtUnsecuredLoan = rs.getString("INVESTED_DEBT_LOAN");
					Float total = rs.getFloat("TOTAL");

					Long totaldata = total.longValue();
					String nPADate = rs.getString("NPA_DATE");
					String promoterNameValuee = rs.getString("PROMOTER_NAME");

					double firstInstallmentsSettledAmount = rs.getDouble("FIRST_INSTALLMENTPAYAMOUNT");
					double recoveryAmount = rs.getDouble("RecoveryAmount");
					// String typeOfRecovery = rs.getString("PROMOTER_NAME");
					double netOutstandingAmount = rs.getDouble("NETOUTSTANDINGAMOUNT");
					double claimEligibleAmount = rs.getDouble("CLAIM_ELIGIBLEAMOUNT");
					;
					double firstInstallmentPayAmount = rs.getDouble("FIRST_INSTALLMENTPAYAMOUNT");
					String legalForumAsPerFirstClaim = rs.getString("PROMOTER_NAME");

					// String promoterNameValuee =
					// rs.getString("PROMOTER_NAME");
					// Account Details

					String npaDate = rs.getString("NPA_DATE");
					request.setAttribute("date", npaDate);

					// Legal Sectiopn

					String legalOtherForms = rs.getString("OtherForum");
					if (legalOtherForms != null && !(legalOtherForms.equals(""))) {

						form1.setLegalOtherForms(legalOtherForms);
					} else {
						form1.setLegalOtherForms("");
					}
					String legalRegistration = rs.getString("RegistrationNo");
					String legalsuitFilingDate = rs.getString("Suit_Filing_Date");
					request.setAttribute("legalsuitFilingDate", legalsuitFilingDate);
					String legalProceedings = rs.getString("legalproceedings");

					String filenameName = rs.getString("FILENAME");
					request.setAttribute("filenameName", filenameName);

					/*
					 * String cGPan = rs.getString("CGPAN"); String pmarCgpan =
					 * rs.getString("PMR_CGPAN"); String unitName =
					 * rs.getString("SSI_UNIT_NAME"); String typeofEntity =
					 * rs.getString("UNIT_TYPE"); String constitution =
					 * rs.getString("CONSTITUTION"); double guaranteeAmount =
					 * rs.getDouble("GUARANTEE_AMT"); String guaranteeStartDate
					 * = rs.getString("GUARATNEE_START_DATE"); String
					 * lastDisbursementDate =
					 * rs.getString("LAST_DISBURSEMENT_DATE"); double
					 * sanctionAmount = rs.getDouble("SANCTION_AMOUNT"); String
					 * sanctionDate = rs.getString("SANCTION_DATE");
					 */

					form1.setcGPan(cGPan);
					form1.setPromoterNameValuee(promoterNameValuee);
					form1.setPmarCgpan(pmarCgpan);
					form1.setUnitName(unitName);
					form1.setTypeofEntity(typeofEntity);
					form1.setConstitution(constitution);
					form1.setGuaranteeAmount(guaranteeAmount);
					form1.setGuaranteeStartDate(guaranteeStartDate);
					form1.setLastDisbursementDate(lastDisbursementDate);
					form1.setSanctionAmount(sanctionAmount);
					form1.setSanctionDate(sanctionDate);
					form1.setClaimRefNumber(claimRefNumber);
					form1.setApplicationReferenceNumber(applicationReferenceNumber);

					// Branch Details

					form1.setiTPAN(iTPAN);
					form1.setAdharNumber(adharNumber);
					form1.setInvestedEquity(investedEquity);
					form1.setInvestedDebtUnsecuredLoan(investedDebtUnsecuredLoan);
					form1.setTotal(totaldata);
					form1.setnPADate(npaDate);
					form1.setPromoterNameValuee(promoterNameValuee);
					form1.setFirstInstallmentsSettledAmount(firstInstallmentsSettledAmount);
					form1.setRecoveryAmount(recoveryAmount);
					form1.setNetOutstandingAmount(netOutstandingAmount);
					form1.setClaimEligibleAmount(claimEligibleAmount);
					form1.setFirstInstallmentPayAmount(firstInstallmentPayAmount);
					form1.setLegalForumAsPerFirstClaim(legalForumAsPerFirstClaim);
					form1.setLegalRegistration(legalRegistration);

					form1.setLegalProceedings(legalProceedings);

					/*
					 * form1.setBorrowerCompleteAddress(borrowerCompleteAddress)
					 * ; form1.setBorrowerCity(borrowerCity);
					 * form1.setBorrowerDistrict(borrowerDistrict);
					 * form1.setBorrowerState(borrowerState);
					 * form1.setBorrowerPinCode(borrowerPinCode);
					 * 
					 * //Account Details //form1.setnPADate(nPADate);
					 * form1.setAccountsWilFulDefaulter(accountsWilFulDefaulter)
					 * ; form1.setAccountReasonTurning(accountReasonTurning);
					 * form1.setAccountSatisfactoryReason(
					 * accountSatisfactoryReason);
					 * 
					 * 
					 * //legal Notice form1.setLegalOtherForms(legalOtherForms);
					 * form1.setLegalRegistration(legalRegistration);
					 * form1.setLegalsuitFilingDate(legalsuitFilingDate);
					 * form1.setLegalSatisfactoryRreason(
					 * legalSatisfactoryRreason);
					 * form1.setLegalLocation(legalLocation);
					 * form1.setLegalAmountClaimed(legalAmountClaimed);
					 * form1.setLegalProceedings(legalProceedings);
					 * 
					 * //Term Loans (TC)
					 * form1.setTcTotalDisbursement(tcTotalDisbursement);
					 * form1.setTcDateofLastDisbursement(
					 * tcDateofLastDisbursement);
					 * form1.setTcPrincipalAmount(tcPrincipalAmount);
					 * form1.setTcRepaymentsInterest(tcRepaymentsInterest);
					 * form1.setTcOutstandingAmount(tcOutstandingAmount);
					 * form1.setTcOutstandingInterest(tcOutstandingInterest);
					 * form1.setTcOutstandingStatedCivil(
					 * tcOutstandingStatedCivil);
					 * form1.setTcOutstandingLodgement(tcOutstandingLodgement);
					 * form1.setTcAccountRestructed(tcAccountRestructed);
					 * 
					 * //recovery section
					 * form1.setRecoveryPrincipal(recoveryPrincipal);
					 * form1.setRecoveryInterest(recoveryInterest);
					 * form1.setRecoveryMode(recoveryMode);
					 * 
					 * //Security and Personal Guarantee
					 * 
					 * form1.setPersonalAmountclaim(personalAmountclaim);
					 * 
					 * //general Information //MLI advise placing,Does the MLI
					 * propose form1.setGeneralMLIComment(generalMLIComment);
					 * form1.setGeneralMLIFinancial(generalMLIFinancial);
					 * form1.setGeneralMLIBank(generalMLIBank);
					 * form1.setGeneralMLIRemark(generalMLIRemark);
					 * //creditsupportforanyotherprojectYN,left
					 * //PromoterunderwatchListofCGTMSEYN,left
					 * 
					 * 
					 * //land section form1.setLandSection(landSection);
					 * form1.setLandNetWorth(landNetWorth);
					 * form1.setLandDataOfClaim(landDataOfClaim);
					 * form1.setLandDataOfNPA(landDataOfNPA);
					 * form1.setLandReason(landReason);
					 * 
					 * //building section
					 * form1.setBuildingSection(buildingSection);
					 * form1.setBuildingDateOfNpa(buildingDateOfNpa);
					 * form1.setBuildingDateOfClaim(buildingDateOfClaim);
					 * form1.setBuildingNetWorth(buildingNetWorth);
					 * form1.setBuildingReason(buildingReason);
					 * 
					 * //plant and machinery
					 * 
					 * form1.setPlantMachinerySection(plantMachinerySection);
					 * form1.setPlantMachineryDateNpa(plantMachineryDateNpa);
					 * form1.setPlantMachineryClaim(plantMachineryClaim);
					 * form1.setPlantMachineryNetWorth(plantMachineryNetWorth);
					 * form1.setPlantMachineryReason(plantMachineryReason);
					 * 
					 * //other fixed movable
					 * 
					 * form1.setOtherFixedMovableSection(
					 * otherFixedMovableSection);
					 * form1.setOtherFixedMovableDateOfNpa(
					 * otherFixedMovableDateOfNpa);
					 * form1.setOtherFixedMovableClaim(otherFixedMovableClaim);
					 * form1.setOtherFixedMovableNetWorth(
					 * otherFixedMovableNetWorth);
					 * form1.setOtherFixedReason(otherFixedReason);
					 * 
					 * //current asset
					 * 
					 * form1.setCurrentAssetSection(currentAssetSection);
					 * form1.setCurrentAssetDateOfNpa(currentAssetDateOfNpa);
					 * form1.setCurrentAssetClaim(currentAssetClaim);
					 * form1.setCurrentAssetNetWorth(currentAssetNetWorth);
					 * form1.setCurrentAssetReson(currentAssetReson);
					 * 
					 * //other
					 * 
					 * form1.setOtherSection(otherSection);
					 * form1.setOtherNpa(otherNpa);
					 * form1.setOtherClaim(otherClaim);
					 * form1.setOtherNetWorth(otherNetWorth);
					 * form1.setOtherReason(otherReason);
					 * 
					 * //total form1.setDateOfSectionTotal(dateOfSectionTotal);
					 * form1.setDateOfNpaTotal(dateOfNpaTotal);
					 * form1.setNetWorthTotal(netWorthTotal);
					 * form1.setDateOfClaimTotal(dateOfClaimTotal);
					 */
					form1.setPromoterValues(promoter);
					form1.setIsSearchClicked(isSearchClicked);
					form1.setLegalProceedingsValue(legalProceedingsValue);
					form1.setRole(roleName);
					// form1.setUserId(userId);

					ArrayList<NPAMarkingPopulateData> npaPopulateData = getNPAPopulatedData(mapping, form, request,
							response);
					request.setAttribute("npaPopulateData", npaPopulateData);

				}
			}

			} else {
				form1.setPromoterValues(promoter);
				form1.setReproductionValue(reproductionValue);
				form1.setLegalProceedingsValue(legalProceedingsValue);
			}

		} catch (Exception err) {
			try {
				if (conn != null) {
					conn.rollback();
				}
			} catch (SQLException ignore) {}
			Log.log(2, "ClaimProcessingAction", "WriteToFile", err.getMessage());
			err.printStackTrace();
			Map option = new HashMap();
			option.put("label", "");
			option.put("value", "Select");
			reason.add(option);
			form1.setPromoterValues(promoter);
			form1.setReproductionValue(reproductionValue);
			request.setAttribute("dMessage", err.getMessage());
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
		if (message != null) {
			form1.setMessage(message);
		}

		/*
		 * if(checkerStatus.equalsIgnoreCase("APPROVE")) { return
		 * mapping.findForward("sucess"); }
		 */

		return mapping.findForward("secondClaimProcessingPage");
	}

	public ActionForward getClaimProcessingDetail(ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		SecondClaimProcessingActionForm form1 = (SecondClaimProcessingActionForm) form;
		String message = "";
		Connection conn = null;
		try {

			User user = getUserInformation(request);
			
			if (conn == null) {
				conn = DBConnection.getConnection();
			}
			String userId = user.getUserId();
			message = saveClaimProcessingDataValues(userId, conn, request, form1, mapping, response);
			/*
			 * System.out.println("==============="+form1.getRole());
			 * if(form1.getRole().equalsIgnoreCase("CHECKER") &&
			 * !(form1.getCheckerReturn().equalsIgnoreCase("RETURN"))){
			 * 
			 * ArrayList<CheckListForm> checkListDtls =
			 * getCheckListDtls(mapping, form1, request,response);
			 * request.setAttribute("checkListDtls", checkListDtls); return
			 * mapping.findForward("sucess"); }
			 */

			getClaimProcessingSearchDetail(mapping, form, request, response);

		} catch (Exception err) {
			try {
				if (conn != null) {
					conn.rollback();
				}
			} catch (SQLException ignore) {}
			Log.log(2, "ClaimProcessingAction", "WriteToFile", err.getMessage());
			Log.log(2, "NpaMarkingAction", "WriteToFile", err.getMessage());
			err.printStackTrace();
		}

		form1.setMessage(message);
		return mapping.findForward("secondClaimProcessingPage");
	}

	public String saveClaimProcessingDataValues(String user, Connection conn, HttpServletRequest request,
			SecondClaimProcessingActionForm form1, ActionMapping mapping, HttpServletResponse response) {
		String sqlQuery = "";
		CallableStatement callableStmt = null;
		ResultSet rs = null;
		String date;
		String amount;
		String[] check;
		int status = -1;
		String errorCode = null;
		String message = "";
		String npaDate = "";
		String cgpan = "", pmr_refNo = "", claimType = "", roleName = "", claimRefNumber = "",
				applicationReferenceNumber = "";
		byte[] legalDocument;

		try {
			// System.out.println(form1.getCheckerReturn());

			FormFile legalDoc = (FormFile) form1.getNocDocument1();
			String filenameName = null;
			int fileSize;
			byte[] filename = null;

			if (legalDoc != null) {
				filename = legalDoc.getFileData();
				// System.out.println("filename=byte====="+filename);
				filenameName = legalDoc.getFileName();
				// System.out.println("filenameName======"+filenameName);
				fileSize = legalDoc.getFileSize();
				// System.out.println("fileSize======"+fileSize);
			}

			pmr_refNo = form1.getPromoterNamee();
			cgpan = form1.getCgpan2();
			roleName = form1.getRole();
			claimType = "SecondClaim";
			claimRefNumber = form1.getClaimReferenceNumber();
			applicationReferenceNumber = form1.getApplicationReferenceNumber();

			callableStmt = conn.prepareCall("{call FUNC_INSERT_Second_CLAIM_DETAILS(?,?,?,?,?,?,?,?,?,?,?)}");// 68
			callableStmt.registerOutParameter(1, Types.VARCHAR);
			if (cgpan != null && !(cgpan.equals(""))) {
				callableStmt.setString(2, cgpan);
			} else {
				callableStmt.setString(2, null);
			}
			if (pmr_refNo != null && !(pmr_refNo.equals(""))) {
				callableStmt.setString(3, pmr_refNo);
			} else {
				callableStmt.setString(3, null);
			}
			if (claimRefNumber != null && !(claimRefNumber.equals(""))) {

				callableStmt.setString(4, claimRefNumber);
			} else {
				callableStmt.setString(4, null);
			}

			callableStmt.setString(5, claimType);
			callableStmt.setString(6, user);
			if (roleName.equalsIgnoreCase("CHECKER")) {
				callableStmt.setString(7, form1.getCheckerReturn());
			} else {
				callableStmt.setString(7, "New");
			}
			callableStmt.setString(8, roleName);
			callableStmt.setString(9, filenameName);
			callableStmt.setBytes(10, filename);

			callableStmt.registerOutParameter(11, Types.VARCHAR);
			callableStmt.execute();

			status = callableStmt.getInt(1);
			errorCode = callableStmt.getString(11);
			if (status == Constants.FUNCTION_FAILURE) {
				Log.log(Log.ERROR, "CPDAO", "saveNpaDataValues()",
						"saveNpaDataValues returns a 1. Error code is :" + errorCode);
				message = errorCode;

				if (callableStmt != null) {
					callableStmt.close();
					callableStmt = null;
				}
			} else if (status == Constants.FUNCTION_SUCCESS) {

				ArrayList<NPAMarkingPopulateData> npaPopulateData = getNPAPopulatedData(mapping, form1, request,
						response);
				request.setAttribute("npaPopulateData", npaPopulateData);
				message = "Data Inserted Successfully!!";
			}

		} catch (Exception e) {
			try {
				if (conn != null) {
					conn.rollback();
				}
			} catch (SQLException ignore) {
			}
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
			} catch (Exception e) {
				System.out.println(e.getMessage());
			}
		}

		// return mapping.findForward("disbursementDetailPage");
		return message;
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
			try {
				if (conn != null) {
					conn.rollback();
				}
			} catch (SQLException ignore) {
			}
			Log.log(2, "ClaimProcessingAction", "WriteToFile", err.getMessage());
			err.printStackTrace();
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
			} catch (Exception e) {
				System.out.println(e.getMessage());
			}
		}
		return promoter;
	}

	public ArrayList getLegalProceedings(Connection conn) {

		ArrayList legalProceedings = new ArrayList();
		CallableStatement callableStmt = null;
		ResultSet rs = null;
		String reduction = "Legal Proceedings";
		int status = -1;
		String errorCode = null;
		String message = "";
		try {

			callableStmt = conn.prepareCall("{call FUNC_GET_NPA_MASTER_DETAILS(?,?,?)}");
			callableStmt.registerOutParameter(1, Types.VARCHAR);
			callableStmt.setString(2, reduction);
			callableStmt.registerOutParameter(3, Types.VARCHAR);
			rs = callableStmt.executeQuery();

			if (status == Constants.FUNCTION_FAILURE) {

				Log.log(Log.ERROR, "CPDAO", "getReduction", "getReduction returns a 1. Error code is :" + errorCode);
				message = errorCode;
				if (callableStmt != null) {
					callableStmt.close();
				}
			} else if (status == Constants.FUNCTION_SUCCESS) {

				rs = callableStmt.executeQuery();
			}
			while (((ResultSet) rs).next()) {
				{
					String getreductions = rs.getString("npa_mst_description");
					// String promoterName = ((ResultSet)
					// rs).getString("PROMOTERNAME");
					Map option = new HashMap();
					option.put("label", getreductions);
					option.put("value", getreductions);
					legalProceedings.add(option);
				}

			}

		} catch (Exception err) {
			try {
				if (conn != null) {
					conn.rollback();
				}
			} catch (SQLException ignore) {
			}
			Log.log(2, "ClaimProcessingAction", "WriteToFile", err.getMessage());
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
			} catch (Exception e) {
				System.out.println(e.getMessage());
			}
		}
		return legalProceedings;
	}

	public ActionForward downloadFile(ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		Connection conn = null;
		ResultSet rs = null;
		PreparedStatement stmt = null;
		byte[] fileData = null;
		String fileName = null;
		try {

			// String cgpan = request.getParameter("cgPan");
			String claimReferenceNumber = request.getParameter("claimReferenceNumber");

			// Integer claimId =
			// Integer.parseInt(request.getParameter("claimId"));*/

			if ((claimReferenceNumber != null && !claimReferenceNumber.isEmpty())) {
				conn = DBConnection.getConnection(false);
				String sqlQuery = "select ATTACHMENTS,FILENAME from  subdebt_secondclaimprocessing where Claim_Ref_Number=?";
				stmt = conn.prepareStatement(sqlQuery);
				stmt.setString(1, claimReferenceNumber);

				rs = stmt.executeQuery();
				if (rs.next()) {
					fileData = rs.getBytes(1);
					fileName = rs.getString(2);
				}

				response.setContentType("application/pdf");
				response.addHeader("Content-Disposition", "attachment; filename=" + fileName);
				OutputStream os = response.getOutputStream();
				os.write(fileData);
				os.close();
			}
		} catch (Exception e) {
			try {
				if (conn != null) {
					conn.rollback();
				}
			} catch (SQLException ignore) {
			}
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

	public ArrayList<NPAMarkingPopulateData> getNPAPopulatedData(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)

	{
		String sqlQuery = "";
		CallableStatement callableStmt = null;
		ResultSet rs = null;
		SecondClaimProcessingActionForm form1 = (SecondClaimProcessingActionForm) form;
		ArrayList<NPAMarkingPopulateData> npaPopulateData = new ArrayList<NPAMarkingPopulateData>();
		int status = -1;
		String errorCode = "", message = "";
		Connection conn = null;
		try {
			/*System.out.println("Inside method.... getNPAPopulatedData :: CGPAN" + form1.getcGPan() + "\t member is ::"
					+ form1.getPromoterNamee());
			System.out.println("Claim Reference Number :::" + form1.getClaimReferenceNumber());*/
			if (conn == null) {
				conn = DBConnection.getConnection(false);
			}
			callableStmt = conn.prepareCall("{call FUNC_Get_SUBDEBT_AUDIT_HISTORY(?,?,?,?)}");
			callableStmt.registerOutParameter(1, Types.VARCHAR);
			if (form1.getPromoterNamee() != null && "0".equals(form1.getPromoterNamee())) {
				callableStmt.setString(2, form1.getClaimReferenceNumber());
				callableStmt.setString(3, null);
			} else {
				callableStmt.setString(2, form1.getcGPan());
				callableStmt.setString(3, form1.getPromoterNamee());
			}
			callableStmt.registerOutParameter(4, Types.VARCHAR);
			callableStmt.execute();
			status = callableStmt.getInt(1);
			errorCode = callableStmt.getString(4);
			//System.out.println("status::" + status + "\t errorCode" + errorCode);

			if (status == Constants.FUNCTION_FAILURE) {
				Log.log(Log.ERROR, "CPDAO", "getDisbursementSearchDetail()",
						"getDisbursementSearchDetail returns a 1. Error code is :" + errorCode);

				//System.out.println("Getting Error....");

				if (callableStmt != null) {
					callableStmt.close();
				}
				conn.rollback();
				message = errorCode;
			} else if (status == Constants.FUNCTION_SUCCESS) {

				//System.out.println("Inside Successs");

				rs = callableStmt.executeQuery();

				int i = rs.getRow();
				//System.out.println("I is " + i);
				/*
				 * if(i == 0) { NPAMarkingPopulateData data = new
				 * NPAMarkingPopulateData();
				 * 
				 * data.setClm_au_id(0); data.setClm_LVEL("");
				 * data.setUser_id(""); data.setClm_dttime("");
				 * data.setClm_status(""); data.setClm_remark("");
				 * 
				 * //npaPopulateData.add(data); } else {
				 */
				while (rs.next()) {
					NPAMarkingPopulateData data = new NPAMarkingPopulateData();
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
				// }
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
