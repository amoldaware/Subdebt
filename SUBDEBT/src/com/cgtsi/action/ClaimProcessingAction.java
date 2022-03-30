package com.cgtsi.action;

import java.io.File;
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
import org.apache.struts.upload.MultipartBoundaryInputStream;
import org.apache.struts.validator.DynaValidatorActionForm;

import com.cgtsi.actionform.CheckListForm;
import com.cgtsi.actionform.ClaimProcessingActionForm;
import com.cgtsi.actionform.NPAMarkingPopulateData;
import com.cgtsi.actionform.NpaMarkingActionForm;
import com.cgtsi.admin.User;
import com.cgtsi.common.Constants;
import com.cgtsi.common.Log;
import com.cgtsi.util.DBConnection;
import com.google.gson.Gson;
import com.mysql.cj.protocol.Resultset;

public class ClaimProcessingAction extends BaseAction {

	public ActionForward getClaimProcessing(ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		Connection conn = null;
		PreparedStatement pstmt = null;
		Resultset rs = null;
		String message = null;

		ClaimProcessingActionForm form1 = (ClaimProcessingActionForm) form;
		// DynaValidatorActionForm appForm = (DynaValidatorActionForm) form;
		String cgpan = form1.getCgpan2();
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
				try {

					bankId = user.getBankId();
					promoter = getPromoterDetails(cgpan, conn, bankId);
					// System.out.println("Promoter Size :::" +
					// promoter.size());
					if (promoter.size() == 0) {
						message = "Either CGPAN not pertaining to logged in User,Bank,MLI or No Promoter Details Found!!";
					}
					// System.out.println("HashMap Contains Values ::" +
					// promoter + "JSON Data ::::" + promoter);
					form1.setPromoterValues(promoter);
					form1.setCgpan2(cgpan);
					form1.setIsSearchClicked("");
				} catch (Exception err) {

					Log.log(2, "ClaimProcessingAction", "WriteToFile", err.getMessage());
				}
			} else {
				Map option = new HashMap();
				option.put("label", "");
				option.put("value", "Select");
				promoter.add(option);
				form1.setPromoterValues(promoter);
				form1.setIsSearchClicked("");

			}
			reproductionValue = getReduction(conn);
			if (reproductionValue != null) {
				form1.setReproductionValue(reproductionValue);
			} else {
				Map option = new HashMap();
				option.put("label", "");
				option.put("value", "Select");
				reproductionValue.add(option);
				// form1.setReasonvalue(reproductionValue);
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

		} catch (Exception err) {
			err.printStackTrace();
			try {
				if (conn != null) {
					conn.rollback();
				}
			} catch (SQLException ignore) {
			}
			Log.log(2, "ClaimProcessingAction", "WriteToFile", err.getMessage());
			form1.setMessage(err.getMessage());

		} finally {
			try {
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
		return mapping.findForward("claimProcessingPage");
	}

	public ActionForward getClaimProcessingSearchDetail(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) throws Exception {

		ClaimProcessingActionForm form1 = (ClaimProcessingActionForm) form;
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

			if ((cgpan != null && !cgpan.isEmpty()) && (promoterName != null && !promoterName.isEmpty())) {
				if (conn == null) {
					conn = DBConnection.getConnection(false);
				}

				bankId = user.getBankId();
				String zoneId = user.getZoneId();
				String branchId = user.getBranchId();
				String memberId = bankId + zoneId + branchId;
				String userId = user.getUserId();
				// System.out.println("Member id===66==="+memberId);

				promoter = getPromoterDetails(cgpan, conn, bankId);
				reproductionValue = getReduction(conn);
				form1.setPromoterValues(promoter);
				if (reproductionValue != null) {
					form1.setReproductionValue(reproductionValue);
				} else {
					Map option = new HashMap();
					option.put("label", "");
					option.put("value", "Select");
					reproductionValue.add(option);
					form1.setReproductionValue(reproductionValue);
				}
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

					Log.log(Log.ERROR, "CPDAO", "getNpaMarkingSearchDetail()",
							"getNpaMarkingSearchDetail returns a 1. Error code is :" + errorCode);
					message = errorCode;
					if (callableStmt != null) {
						callableStmt.close();
					}
					form1.setMessage(message);
				} else if (status == Constants.FUNCTION_SUCCESS) {

					rs = callableStmt.executeQuery();
				}
				if (rs != null && rs.next()) {

					String roleName = rs.getString("ROLENAME");
					request.setAttribute("role", roleName);
					// String referanceNo = rs.getString("APP_REF_NO");
					// String cgpansearch = rs.getString("CGPAN");
					// userId=rs.getString("USER_ID");

					String promoterNameValuee = rs.getString("PROMOTER_NAME");
					String promoterUnitName = rs.getString("SSI_UNIT_NAME");
					String promoterGuaranteeStartDate = rs.getString("GUARATNEE_START_DATE");
					String promoterSactionDate = rs.getString("SANCTION_DATE");
					double promoterGuranteeAmount = rs.getDouble("GUARANTEE_AMT");

					String branchMemberId = rs.getString("MEMBER_ID");
					String branchMliName = rs.getString("MLI_Name");
					String branchCity = rs.getString("City");
					String branchDistrict = rs.getString("District");
					String branchState = rs.getString("State");
					String branchDealingOfficerName = rs.getString("DealingOfficerName");
					String branchGstNo = rs.getString("GSTINNo");

					String borrowerCompleteAddress = rs.getString("BorrowerCompleteAddress");
					String borrowerCity = rs.getString("BorrowercITY");
					String borrowerDistrict = rs.getString("BorrowerdISTRICT");
					String borrowerState = rs.getString("BorrowerSTATE");
					String borrowerPinCode = rs.getString("BorrowerPINCODE");

					// Account Details

					String npaDate = rs.getString("NPA_DATE");
					request.setAttribute("date", npaDate);

					String accountsWilFulDefaulter = rs.getString("WilfuldefaulterYN");
					if (accountsWilFulDefaulter != null && !(accountsWilFulDefaulter.equals(""))) {
						if (accountsWilFulDefaulter.equals("Y")) {
							request.setAttribute("accountsWilFulDefaulter", "Yes");
						} else {
							request.setAttribute("accountsWilFulDefaulter", "No");
						}
					}

					String accountsClassified = rs.getString("classifiedasfraudYN");
					if (accountsClassified != null && !(accountsClassified.equals(""))) {
						if (accountsClassified.equals("Y")) {
							request.setAttribute("accountsClassified", "Yes");
						} else if (accountsClassified.equals("N")) {
							request.setAttribute("accountsClassified", "No");
						} else {
							request.setAttribute("accountsClassified", "");
						}
					}

					String accountsEnquiry = rs.getString("InternalexternalenquiryYN");
					if (accountsEnquiry != null && !(accountsEnquiry.equals(""))) {
						if (accountsEnquiry.equals("Y")) {
							request.setAttribute("accountsEnquiry", "Yes");
						} else {
							request.setAttribute("accountsEnquiry", "No");
						}
					}
					String accountsMliReported = rs.getString("InvolvementofstaffYN");
					if (accountsMliReported != null && !(accountsMliReported.equals(""))) {
						if (accountsMliReported.equals("Y")) {
							request.setAttribute("accountsMliReported", "Yes");
						} else {
							request.setAttribute("accountsMliReported", "No");
						}
					}
					String accountReasonTurning = rs.getString("ReasonsforAccount");
					String accountSatisfactoryReason = rs.getString("satisfactoryreason");
					String dateofIssueofRecallNotice = rs.getString("Dateofissue");
					request.setAttribute("dateofIssueofRecallNotice", dateofIssueofRecallNotice);

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
					String legalSatisfactoryRreason = rs.getString("satisfactoryreason");
					String legalLocation = rs.getString("Location");
					double legalAmountClaimed = rs.getDouble("AmountClaimed");

					String legalProceedings = rs.getString("legalproceedings");
					String dateofpossessionofassets = rs.getString("Dateofpossessionofassets");
					request.setAttribute("dateofpossessionofassets", dateofpossessionofassets);

					String filenameName = rs.getString("FILENAME");
					request.setAttribute("filenameName", filenameName);

					// Term Loans (TC)
					String tcTotalDisbursement = rs.getString("TotalDisbursementAmount");
					String tcDateofLastDisbursement = rs.getString("DateofLastDisbursement");
					String tcPrincipalAmount = rs.getString("RepaymentsPrincipalAmount");
					String tcRepaymentsInterest = rs.getString("RepaymentsInterestandOtherCharges");
					String tcOutstandingAmount = rs.getString("NPAOutstandingPrincipalAmount");
					String tcOutstandingInterest = rs.getString("NPAOutstandingInterestandOtherCharges");
					String tcOutstandingStatedCivil = rs.getString("Outstandingstated");
					String tcOutstandingLodgement = rs.getString("OutstandingAsODateofLodgementofClaim");
					String tcAccountRestructed = rs.getString("AccountRestructed");
					if (tcAccountRestructed != null && !(tcAccountRestructed.equals(""))) {
						if (tcAccountRestructed.equals("Y")) {
							request.setAttribute("tcAccountRestructed", "Yes");
						} else {
							request.setAttribute("tcAccountRestructed", "No");
						}
					}
					// Recovery Details

					String recoveryPrincipal = rs.getString("RecoveryPrincipal");
					String recoveryInterest = rs.getString("RecoveryInterestOtherCharges");
					String recoveryMode = rs.getString("ModeofRecovery");

					// Security and Personal Guarantee
					String personalAmountclaim = rs.getString("Totalamountofguarantee");

					String recoveryafterNPAindicated = rs.getString("recoveryafterNPAindicatedYN");
					if (recoveryafterNPAindicated != null && !(recoveryafterNPAindicated.equals(""))) {
						if (recoveryafterNPAindicated.equals("Y")) {
							request.setAttribute("recoveryafterNPAindicated", "Yes");
						} else {
							request.setAttribute("recoveryafterNPAindicated", "No");
						}
					}
					String valueasrecoveriesafterNPA = rs.getString("valueasrecoveriesafterNPAYN");
					if (valueasrecoveriesafterNPA != null && !(valueasrecoveriesafterNPA.equals(""))) {
						if (valueasrecoveriesafterNPA.equals("Y")) {
							request.setAttribute("valueasrecoveriesafterNPA", "Yes");
						} else {
							request.setAttribute("valueasrecoveriesafterNPA", "No");
						}
					}

					// general Information
					// MLI advise placing,Does the MLI propose

					String generalMLIComment = rs.getString("MLIsComment");
					String generalMLIFinancial = rs.getString("DetailsofFinancialAssistance");
					String generalMLIRemark = rs.getString("Remark");
					String generalMLIBank = rs.getString("FacilityalreadyprovidedtoBorrower");

					String creditsupportforanyotherproject = rs.getString("creditsupportforanyotherprojectYN");
					if (creditsupportforanyotherproject != null && !(creditsupportforanyotherproject.equals(""))) {
						if (creditsupportforanyotherproject.equals("Y")) {
							request.setAttribute("creditsupportforanyotherproject", "Yes");
						} else {
							request.setAttribute("creditsupportforanyotherproject", "No");
						}
					}
					String promoterunderwatchListofCGTMSE = rs.getString("PromoterunderwatchListofCGTMSEYN");
					if (promoterunderwatchListofCGTMSE != null && !(promoterunderwatchListofCGTMSE.equals(""))) {
						if (promoterunderwatchListofCGTMSE.equals("Y")) {
							request.setAttribute("promoterunderwatchListofCGTMSE", "Yes");
						} else if (promoterunderwatchListofCGTMSE.equals("N")) {
							request.setAttribute("promoterunderwatchListofCGTMSE", "No");
						} else {
							request.setAttribute("promoterunderwatchListofCGTMSE", "");
						}
					}

					// ==============land=================

					BigDecimal landSection = rs.getBigDecimal("LANDDATEOFSANCTION");
					String landDataOfNPA = rs.getString("LANDDATEOFNPA");
					String landDataOfClaim = rs.getString("LANDDATEOFCLAIM");
					String landNetWorth = rs.getString("LANDNETWORTH");
					
					String landReason = rs.getString("LANDREASONS");

					// =====bulding==============================

					BigDecimal buildingSection = rs.getBigDecimal("BUILDINGDATEOFSANCTION");
					String buildingDateOfNpa = rs.getString("BUILDINGDATEOFNPA");
					String buildingDateOfClaim = rs.getString("BUILDINGDATEOFCLAIM");
					String buildingNetWorth = rs.getString("BUILDINGNETWORTH");
					String buildingReason = rs.getString("BUILDINGREASONS");// BUILDINGREASONS=left

					// =======Plant and Machinery==========================

					String plantMachinerySection = rs.getString("PLANTMACHINERYDATEOFSANCTION");
					

					String plantMachineryDateNpa = rs.getString("PLANTMACHINERYDATEOFNPA");
					String plantMachineryClaim = rs.getString("PLANTMACHINERYDATEOFCLAIM");
					String plantMachineryNetWorth = rs.getString("PLANTMACHINERYNETWORTH");
					String plantMachineryReason = rs.getString("PLANTMACHINERYREASONS");// PLANTMACHINERYREASONS
																						// left

					// ========other fixed

					String otherFixedMovableSection = rs.getString("OTHERFIXEDMOVABLEASSETSDATEOFSANCTION");
					String otherFixedMovableDateOfNpa = rs.getString("OTHERFIXEDMOVABLEASSETSDATEOFNPA");
					String otherFixedMovableClaim = rs.getString("OTHERFIXEDMOVABLEASSETSDATEOFCLAIM");
					String otherFixedMovableNetWorth = rs.getString("OTHERFIXEDMOVABLEASSETSNETWORTH");
					String otherFixedReason = rs.getString("OTHERFIXEDMOVABLEASSETSREASONS");

					// ================current Asset

					String currentAssetSection =rs.getString("CURRENTASSETSDATEOFSANCTION");
					String currentAssetDateOfNpa = rs.getString("CURRENTASSETSDATEOFNPA");
					String currentAssetClaim = rs.getString("CURRENTASSETSDATEOFCLAIM");
					String currentAssetNetWorth = rs.getString("CURRENTASSETSNETWORTH");
					String currentAssetReson = rs.getString("CURRENTASSETSREASONS");

					// =================other section

					String otherSection = rs.getString("OTHERSDATEOFSANCTION");
					String otherNpa = rs.getString("OTHERSDATEOFNPA");
					String otherClaim = rs.getString("OTHERSDATEOFCLAIM");
					String otherNetWorth = rs.getString("OTHERSNETWORTH");
					String otherReason = rs.getString("OTHERSREASONS");// left

					BigDecimal dateOfSectionTotal = rs.getBigDecimal("DATEOFSANCTIONTOTAL");
					BigDecimal dateOfNpaTotal = rs.getBigDecimal("DATEOFNPATOTAL");
					BigDecimal netWorthTotal = rs.getBigDecimal("NETWORTHTOTAL");
					BigDecimal dateOfClaimTotal = rs.getBigDecimal("DATEOFClaimTOTAL");

					checkerStatus = "APPROVE";// rs.getString("CHECKER_STATUS");

					// CheckList

					// if(roleName.equalsIgnoreCase("CHECKER")){
					String checkList1 = rs.getString("checkList1");

					if (checkList1 != null && !(checkList1.equals(""))) {
						if (checkList1.equals("Y")) {
							request.setAttribute("checkList1", "Yes");
						} else if (checkList1.equals("N")) {
							request.setAttribute("checkList1", "No");
						} else {
							request.setAttribute("checkList1", "");
						}
					}
					String checkList2 = rs.getString("checkList2");

					if (checkList2 != null && !(checkList2.equals(""))) {
						if (checkList2.equals("Y")) {
							request.setAttribute("checkList2", "Yes");
						} else if (checkList2.equals("N")) {
							request.setAttribute("checkList2", "No");
						} else {
							request.setAttribute("checkList2", "");
						}
					}

					String checkList3 = rs.getString("checkList3");
					if (checkList3 != null && !(checkList3.equals(""))) {
						if (checkList3.equals("Y")) {
							request.setAttribute("checkList3", "Yes");
						} else if (checkList3.equals("N")) {
							request.setAttribute("checkList3", "No");
						} else {
							request.setAttribute("checkList3", "");
						}
					}

					String checkList4 = rs.getString("checkList4");
					if (checkList4 != null && !(checkList4.equals(""))) {
						if (checkList4.equals("Y")) {
							request.setAttribute("checkList4", "Yes");
						} else if (checkList4.equals("N")) {
							request.setAttribute("checkList4", "No");
						} else {
							request.setAttribute("checkList4", "");
						}
					}
					String checkList5 = rs.getString("checkList5");
					if (checkList5 != null && !(checkList5.equals(""))) {
						if (checkList5.equals("Y")) {
							request.setAttribute("checkList5", "Yes");
						} else if (checkList5.equals("N")) {
							request.setAttribute("checkList5", "No");
						} else {
							request.setAttribute("checkList5", "");
						}
					}
					String checkList6 = rs.getString("checkList6");
					if (checkList6 != null && !(checkList6.equals(""))) {
						if (checkList6.equals("Y")) {
							request.setAttribute("checkList6", "Yes");
						} else if (checkList6.equals("N")) {
							request.setAttribute("checkList6", "No");
						} else {
							request.setAttribute("checkList6", "");
						}
					}

					String checkList7 = rs.getString("checkList7");
					if (checkList7 != null && !(checkList7.equals(""))) {
						if (checkList7.equals("Y")) {
							request.setAttribute("checkList7", "Yes");
						} else if (checkList7.equals("N")) {
							request.setAttribute("checkList7", "No");
						} else {
							request.setAttribute("checkList7", "");
						}
					}

					String checkList8 = rs.getString("checkList9");
					if (checkList8 != null && !(checkList8.equals(""))) {
						if (checkList8.equals("Y")) {
							request.setAttribute("checkList8", "Yes");
						} else if (checkList8.equals("N")) {
							request.setAttribute("checkList8", "No");
						} else {
							request.setAttribute("checkList8", "");
						}
					}

					String checkList9 = rs.getString("checkList9");
					if (checkList9 != null && !(checkList9.equals(""))) {
						if (checkList9.equals("Y")) {
							request.setAttribute("checkList9", "Yes");
						} else if (checkList9.equals("N")) {
							request.setAttribute("checkList9", "No");
						} else {
							request.setAttribute("checkList9", "");
						}
					}
					String checkList10 = rs.getString("checkList10");
					if (checkList10 != null && !(checkList10.equals(""))) {
						if (checkList10.equals("Y")) {
							request.setAttribute("checkList10", "Yes");
						} else if (checkList10.equals("N")) {
							request.setAttribute("checkList10", "No");
						} else {
							request.setAttribute("checkList10", "");
						}
					}
					String checkList11 = rs.getString("checkList11");
					if (checkList11 != null && !(checkList11.equals(""))) {
						if (checkList11.equals("Y")) {
							request.setAttribute("checkList11", "Yes");
						} else if (checkList11.equals("N")) {
							request.setAttribute("checkList11", "No");
						} else {
							request.setAttribute("checkList11", "");
						}
					}
					// }

					form1.setPromoterNameValuee(promoterNameValuee);
					form1.setPromoterGuaranteeStartDate(promoterGuaranteeStartDate);
					form1.setPromoterGuranteeAmount(promoterGuranteeAmount);
					form1.setPromoterSactionDate(promoterSactionDate);
					form1.setPromoterUnitName(promoterUnitName);

					// Branch Details

					form1.setBranchMemberId(branchMemberId);
					form1.setBranchMliName(branchMliName);
					form1.setBranchDistrict(branchDistrict);
					form1.setBranchCity(branchCity);
					form1.setBranchState(branchState);
					form1.setBranchDealingOfficerName(branchDealingOfficerName);
					form1.setBranchGstNo(branchGstNo);

					form1.setBorrowerCompleteAddress(borrowerCompleteAddress);
					form1.setBorrowerCity(borrowerCity);
					form1.setBorrowerDistrict(borrowerDistrict);
					form1.setBorrowerState(borrowerState);
					form1.setBorrowerPinCode(borrowerPinCode);

					// Account Details
					// form1.setnPADate(nPADate);
					form1.setAccountsWilFulDefaulter(accountsWilFulDefaulter);
					form1.setAccountReasonTurning(accountReasonTurning);
					form1.setAccountSatisfactoryReason(accountSatisfactoryReason);

					// legal Notice
					form1.setLegalOtherForms(legalOtherForms);
					form1.setLegalRegistration(legalRegistration);
					form1.setLegalsuitFilingDate(legalsuitFilingDate);
					form1.setLegalSatisfactoryRreason(legalSatisfactoryRreason);
					form1.setLegalLocation(legalLocation);
					form1.setLegalAmountClaimed(legalAmountClaimed);
					form1.setLegalProceedings(legalProceedings);

					// Term Loans (TC)
					form1.setTcTotalDisbursement(tcTotalDisbursement);
					form1.setTcDateofLastDisbursement(tcDateofLastDisbursement);
					form1.setTcPrincipalAmount(tcPrincipalAmount);
					form1.setTcRepaymentsInterest(tcRepaymentsInterest);
					form1.setTcOutstandingAmount(tcOutstandingAmount);
					form1.setTcOutstandingInterest(tcOutstandingInterest);
					form1.setTcOutstandingStatedCivil(tcOutstandingStatedCivil);
					form1.setTcOutstandingLodgement(tcOutstandingLodgement);
					form1.setTcAccountRestructed(tcAccountRestructed);

					// recovery section
					form1.setRecoveryPrincipal(recoveryPrincipal);
					form1.setRecoveryInterest(recoveryInterest);
					form1.setRecoveryMode(recoveryMode);

					// Security and Personal Guarantee

					form1.setPersonalAmountclaim(personalAmountclaim);

					// general Information
					// MLI advise placing,Does the MLI propose
					form1.setGeneralMLIComment(generalMLIComment);
					form1.setGeneralMLIFinancial(generalMLIFinancial);
					form1.setGeneralMLIBank(generalMLIBank);
					form1.setGeneralMLIRemark(generalMLIRemark);
					// creditsupportforanyotherprojectYN,left
					// PromoterunderwatchListofCGTMSEYN,left

					// land section
					form1.setLandSection(landSection);
					form1.setLandNetWorth(landNetWorth);
					form1.setLandDataOfClaim(landDataOfClaim);
					form1.setLandDataOfNPA(landDataOfNPA);
					form1.setLandReason(landReason);

					// building section
					form1.setBuildingSection(buildingSection);
					form1.setBuildingDateOfNpa(buildingDateOfNpa);
					form1.setBuildingDateOfClaim(buildingDateOfClaim);
					form1.setBuildingNetWorth(buildingNetWorth);
					form1.setBuildingReason(buildingReason);

					// plant and machinery

					form1.setPlantMachinerySection(plantMachinerySection);
					form1.setPlantMachineryDateNpa(plantMachineryDateNpa);
					form1.setPlantMachineryClaim(plantMachineryClaim);
					form1.setPlantMachineryNetWorth(plantMachineryNetWorth);
					form1.setPlantMachineryReason(plantMachineryReason);

					// other fixed movable

					form1.setOtherFixedMovableSection(otherFixedMovableSection);
					form1.setOtherFixedMovableDateOfNpa(otherFixedMovableDateOfNpa);
					form1.setOtherFixedMovableClaim(otherFixedMovableClaim);
					form1.setOtherFixedMovableNetWorth(otherFixedMovableNetWorth);
					form1.setOtherFixedReason(otherFixedReason);

					// current asset

					form1.setCurrentAssetSection(currentAssetSection);
					form1.setCurrentAssetDateOfNpa(currentAssetDateOfNpa);
					form1.setCurrentAssetClaim(currentAssetClaim);
					form1.setCurrentAssetNetWorth(currentAssetNetWorth);
					form1.setCurrentAssetReson(currentAssetReson);

					// other

					form1.setOtherSection(otherSection);
					form1.setOtherNpa(otherNpa);
					form1.setOtherClaim(otherClaim);
					form1.setOtherNetWorth(otherNetWorth);
					form1.setOtherReason(otherReason);

					// total
					form1.setDateOfSectionTotal(dateOfSectionTotal);
					form1.setDateOfNpaTotal(dateOfNpaTotal);
					form1.setNetWorthTotal(netWorthTotal);
					form1.setDateOfClaimTotal(dateOfClaimTotal);
					form1.setPromoterValues(promoter);
					form1.setIsSearchClicked(isSearchClicked);
					form1.setLegalProceedingsValue(legalProceedingsValue);
					form1.setRole(roleName);
					// form1.setUserId(userId);

					ArrayList<NPAMarkingPopulateData> npaPopulateData = getNPAPopulatedData(mapping, form, request,
							response);
					request.setAttribute("npaPopulateData", npaPopulateData);

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
			} catch (SQLException ignore) {
			}
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
		if (message != null && message.trim().length() > 0) {
			form1.setMessage(message);
		}

		/*
		 * if(checkerStatus.equalsIgnoreCase("APPROVE")) { return
		 * mapping.findForward("sucess"); }
		 */

		return mapping.findForward("claimProcessingPage");
	}

	public ActionForward getClaimProcessingDetail(ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		ClaimProcessingActionForm form1 = (ClaimProcessingActionForm) form;
		String message = "";
		Connection conn = null;
		try {

			User user = getUserInformation(request);

			if (conn == null) {
				conn = DBConnection.getConnection(false);
			}

			String userId = user.getUserId();

			message = saveClaimProcessingDataValues(userId, conn, request, form1, mapping, response);
			System.out.println("===============" + form1.getRole());
			if (form1.getRole().equalsIgnoreCase("CHECKER") && !(form1.getCheckerReturn().equalsIgnoreCase("RETURN"))) {

				ArrayList<CheckListForm> checkListDtls = getCheckListDtls(mapping, form1, request, response);
				request.setAttribute("checkListDtls", checkListDtls);
				return mapping.findForward("sucess");
			}

			getClaimProcessingSearchDetail(mapping, form, request, response);

		} catch (Exception err) {
			try {
				if (conn != null) {
					conn.rollback();
				}
			} catch (SQLException ignore) {
			}
			Log.log(2, "NpaMarkingAction", "WriteToFile", err.getMessage());
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
		if (message != null && message.trim().length() > 0) {
			form1.setMessage(message);
		}
		return mapping.findForward("claimProcessingPage");
	}

	/*
	 * PFUNCRETURN , P_CGPAN , P_PMR_REF_NO, P_ClaimType , -- Pass hardcore
	 * “FirstClaim” P_WILFULDEFAULTERYN , P_CLASSIFIEDASFRAUDYN,
	 * P_INTERNALEXTERNALENQUIRYYN , P_INVOLVEMENTOFSTAFFYN ,
	 * P_SATISFACTORYREASON , P_DATEOFISSUE ,
	 * 
	 * P_LEGALPROCEEDINGS , P_OTHERFORUM , P_REGISTRATIONNO ,
	 * 
	 * P_LEGALSATISFACTORYREASON , P_DATEOFPOSSESSIONOFASSETS , P_LOCATION ,
	 * P_AMOUNTCLAIMED, P_ATTACHMENTS ,
	 * 
	 * P_OUTSTANDINGSTATED , P_OUTSTANDINGASODATEOFLODGEMENTOFCLAIM ,
	 * P_ACCOUNTRESTRUCTED ,
	 * 
	 * P_RECOVERYPRINCIPAL, P_RECOVERYINTERESTOTHERCHARGES , P_MODEOFRECOVERY ,
	 * 
	 * 
	 * P_RECOVERYAFTERNPAINDICATEDYN , P_VALUEASRECOVERIESAFTERNPAYN,
	 * P_TOTALAMOUNTOFGUARANTEE,
	 * 
	 * 
	 * P_MLISCOMMENT , P_DETAILSOFFINANCIALASSISTANCE ,
	 * P_CREDITSUPPORTFORANYOTHERPROJECTYN , P_FACILITYALREADYPROVIDEDTOBORROWER
	 * , P_PROMOTERUNDERWATCHLISTOFCGTMSEYN , P_REMARK ,
	 * 
	 * P_LANDDATEOFCLAIM, P_LANDNETWORTH , P_LANDREASONS , P_BUILDINGDATEOFCLAIM
	 * , P_BUILDINGNETWORTH , P_BUILDINGREASONS , P_PLANTMACHINERYDATEOFCLAIM ,
	 * P_PLANTMACHINERYNETWORTH , P_PLANTMACHINERYREASONS ,
	 * P_OTHERFIXEDMOVABLEASSETSDATEOFCLAIM , P_OTHERFIXEDMOVABLEASSETSNETWORTH
	 * , P_OTHERFIXEDMOVABLEASSETSREASONS , P_CURRENTASSETSDATEOFCLAIM,
	 * P_CURRENTASSETSNETWORTH, P_CURRENTASSETSREASONS, P_OTHERSDATEOFCLAIM,
	 * P_OTHERSNETWORTH, P_OTHERSREASONS, P_CREATED_BY, P_CHECKER_STATUS ,
	 * ISMAKER , -- Send RoleName Value POUTERRCODE
	 */

	public String saveClaimProcessingDataValues(String user, Connection conn, HttpServletRequest request,
			ClaimProcessingActionForm form1, ActionMapping mapping, HttpServletResponse response) {
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

		String claimType = "", accountsWilFulDefaulter = "", accountsClassified = "", accountsEnquiry = "",
				accountsMliReported = "", accountSatisfactoryReason = "", dateofIssueofRecallNotice = "",

				legalProceedings = "", legalOtherForms = "", legalRegistration = "", legalSatisfactoryRreason = "",
				legalLocation = "", dateofpossessionofassets = "", legalsuitFilingDate = "",

				tcOutstandingStatedCivil = "", tcOutstandingLodgement = "", tcAccountRestructed = "",
				recoveryPrincipal = "", recoveryInterest = "", recoveryMode = "",

				personalAmountclaim = "", recoveryafterNPAindicated = "", valueasrecoveriesafterNPA = "", roleName = "",

				generalMLIComment = "", generalMLIFinancial = "", generalMLIBank = "",
				creditsupportforanyotherproject = "", promoterunderwatchListofCGTMSE = "", generalMLIRemark = "";

		String cgpan = "", pmr_refNo = "", landReason = "", buildingReason = "", plantMachineryReason = "",

				otherFixedReason = "", currentAssetReson = "", otherReason = "";

		byte[] legalDocument;

		String landNetWorth = "";
		String landDataOfNPA, buildingDateOfNpa, plantMachineryClaim, buildingNetWorth, plantMachineryDateNpa,
				plantMachineryNetWorth,

				otherFixedMovableDateOfNpa, otherFixedMovableNetWorth, landDataOfClaim, buildingDateOfClaim,

				otherFixedMovableClaim, currentAssetClaim, otherClaim,

				currentAssetDateOfNpa, currentAssetNetWorth, otherNpa, otherNetWorth;
		double legalAmountClaimed = 0.0;
		String checkList1 = "", checkList2 = "", checkList3 = "", checkList4 = "", checkList5 = "", checkList6 = "",
				checkList7 = "", checkList8 = "", checkList9 = "", checkList10 = "", checkList11 = "", returnn = "";

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

			dateofIssueofRecallNotice = request.getParameter("dateofIssueofRecallNotice");
			// System.out.println("dateofIssueofRecallNotice========="+dateofIssueofRecallNotice);
			dateofpossessionofassets = request.getParameter("dateofpossessionofassets");
			legalsuitFilingDate = request.getParameter("legalsuitFilingDate");// legalsuitFilingDate
			// System.out.println("legalsuitFilingDate========="+legalsuitFilingDate);

			pmr_refNo = form1.getPromoterNamee();
			cgpan = form1.getCgpan2();

			claimType = "FirstClaim";
			accountsWilFulDefaulter = form1.getAccountsWilFulDefaulter();
			accountsClassified = form1.getAccountsClassified();
			accountsEnquiry = form1.getAccountsEnquiry();
			accountsMliReported = form1.getAccountsMliReported();
			accountSatisfactoryReason = form1.getAccountSatisfactoryReason();

			legalProceedings = form1.getLegalProceedings();
			String legalOtherFormss = "";
			legalOtherForms = form1.getLegalOtherForms();
			if (legalOtherForms != null && !(legalOtherForms.equals(""))) {

				legalOtherFormss = legalOtherForms;
			} else {
				legalOtherFormss = null;
			}

			legalRegistration = form1.getLegalRegistration();
			legalSatisfactoryRreason = form1.getLegalSatisfactoryRreason();
			legalLocation = form1.getLegalLocation();
			legalAmountClaimed = form1.getLegalAmountClaimed();

			tcOutstandingStatedCivil = form1.getTcOutstandingStatedCivil();
			tcOutstandingLodgement = form1.getTcOutstandingLodgement();
			tcAccountRestructed = form1.getTcAccountRestructed();

			recoveryPrincipal = form1.getRecoveryPrincipal();
			recoveryInterest = form1.getRecoveryInterest();
			recoveryMode = form1.getRecoveryMode();

			personalAmountclaim = form1.getPersonalAmountclaim();
			recoveryafterNPAindicated = form1.getRecoveryafterNPAindicated();
			valueasrecoveriesafterNPA = form1.getValueasrecoveriesafterNPA();

			generalMLIComment = form1.getGeneralMLIComment();
			generalMLIFinancial = form1.getGeneralMLIFinancial();
			generalMLIBank = form1.getGeneralMLIBank();
			creditsupportforanyotherproject = form1.getCreditsupportforanyotherproject();
			promoterunderwatchListofCGTMSE = form1.getPromoterunderwatchListofCGTMSE();
			generalMLIRemark = form1.getGeneralMLIRemark();

			landDataOfNPA = form1.getLandDataOfNPA();
			landNetWorth = form1.getLandNetWorth();
			landDataOfClaim = form1.getLandDataOfClaim();
			landReason = form1.getLandReason();

			buildingDateOfNpa = form1.getBuildingDateOfNpa();
			buildingNetWorth = form1.getBuildingNetWorth();
			buildingDateOfClaim = form1.getBuildingDateOfClaim();
			buildingReason = form1.getBuildingReason();

			plantMachineryDateNpa = form1.getPlantMachineryDateNpa();
			plantMachineryNetWorth = form1.getPlantMachineryNetWorth();
			plantMachineryClaim = form1.getPlantMachineryClaim();
			plantMachineryReason = form1.getPlantMachineryReason();

			otherFixedMovableDateOfNpa = form1.getOtherFixedMovableDateOfNpa();
			otherFixedMovableNetWorth = form1.getOtherFixedMovableNetWorth();
			otherFixedMovableClaim = form1.getOtherFixedMovableClaim();
			otherFixedReason = form1.getOtherFixedReason();

			currentAssetDateOfNpa = form1.getCurrentAssetDateOfNpa();
			currentAssetNetWorth = form1.getCurrentAssetNetWorth();
			currentAssetClaim = form1.getCurrentAssetClaim();
			currentAssetReson = form1.getCurrentAssetReson();

			otherNpa = form1.getOtherNpa();
			otherNetWorth = form1.getOtherNetWorth();
			otherClaim = form1.getOtherClaim();
			otherReason = form1.getOtherReason();
			roleName = form1.getRole();

			// checkList

			checkList1 = form1.getCheckList1();
			checkList2 = form1.getCheckList2();
			checkList3 = form1.getCheckList3();
			checkList4 = form1.getCheckList4();
			checkList5 = form1.getCheckList5();
			checkList6 = form1.getCheckList6();
			checkList7 = form1.getCheckList7();
			checkList8 = form1.getCheckList8();
			checkList9 = form1.getCheckList9();
			checkList10 = form1.getCheckList10();
			checkList11 = form1.getCheckList11();

			callableStmt = conn.prepareCall(
					"{call FUNC_INSERT_CLAIM_DETAILS(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)}");// 68
			callableStmt.registerOutParameter(1, Types.VARCHAR);
			callableStmt.setString(2, cgpan);
			callableStmt.setString(3, pmr_refNo);
			callableStmt.setString(4, claimType);

			callableStmt.setString(5, accountsWilFulDefaulter);
			callableStmt.setString(6, accountsClassified);
			callableStmt.setString(7, accountsEnquiry);
			callableStmt.setString(8, accountsMliReported);
			callableStmt.setString(9, accountSatisfactoryReason);
			// callableStmt.setString(4, dateofIssueofRecallNotice);
			java.util.Date date1 = new SimpleDateFormat("dd/MM/yyyy").parse(dateofIssueofRecallNotice);
			SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
			String dateVal = formatter.format(date1);
			callableStmt.setString(10, dateVal);

			callableStmt.setString(11, legalProceedings);
			callableStmt.setString(12, legalOtherFormss);
			callableStmt.setString(13, legalRegistration);

			// System.out.println("======================="+legalsuitFilingDate);
			java.util.Date datee = new SimpleDateFormat("dd/MM/yyyy").parse(legalsuitFilingDate);
			SimpleDateFormat formatter1 = new SimpleDateFormat("yyyy-MM-dd");
			String dateVal1 = formatter1.format(datee);
			callableStmt.setString(14, dateVal1);

			callableStmt.setString(15, legalSatisfactoryRreason);

			if (dateofpossessionofassets != null && !(dateofpossessionofassets == "")) {
				java.util.Date date2 = new SimpleDateFormat("dd/MM/yyyy").parse(dateofpossessionofassets);
				SimpleDateFormat formatterr = new SimpleDateFormat("yyyy-MM-dd");
				String dateVall = formatterr.format(date2);
				callableStmt.setString(16, dateVall);
			} else {
				callableStmt.setString(16, null);
			}

			callableStmt.setString(17, legalLocation);
			callableStmt.setDouble(18, legalAmountClaimed);

			callableStmt.setBytes(19, filename);

			callableStmt.setString(20, tcOutstandingStatedCivil);
			callableStmt.setString(21, tcOutstandingLodgement);
			callableStmt.setString(22, tcAccountRestructed);

			callableStmt.setString(23, recoveryPrincipal);
			callableStmt.setString(24, recoveryInterest);
			callableStmt.setString(25, recoveryMode);

			callableStmt.setString(26, recoveryafterNPAindicated);
			callableStmt.setString(27, valueasrecoveriesafterNPA);
			callableStmt.setString(28, personalAmountclaim);// personalAmountclaim

			callableStmt.setString(29, generalMLIComment);
			callableStmt.setString(30, generalMLIFinancial);
			callableStmt.setString(31, creditsupportforanyotherproject);
			callableStmt.setString(32, generalMLIBank);
			callableStmt.setString(33, promoterunderwatchListofCGTMSE);
			callableStmt.setString(34, generalMLIRemark);

			callableStmt.setString(35, landDataOfClaim);
			callableStmt.setString(36, landNetWorth);
			callableStmt.setString(37, landReason);

			callableStmt.setString(38, buildingDateOfClaim);
			callableStmt.setString(39, buildingNetWorth);
			callableStmt.setString(40, buildingReason);

			callableStmt.setString(41, plantMachineryClaim);
			callableStmt.setString(42, plantMachineryNetWorth);
			callableStmt.setString(43, plantMachineryReason);

			callableStmt.setString(44, otherFixedMovableClaim);
			callableStmt.setString(45, otherFixedMovableNetWorth);
			callableStmt.setString(46, otherFixedReason);

			callableStmt.setString(47, currentAssetClaim);
			callableStmt.setString(48, currentAssetNetWorth);
			callableStmt.setString(49, currentAssetReson);

			callableStmt.setString(50, otherClaim);
			callableStmt.setString(51, otherNetWorth);
			callableStmt.setString(52, otherReason);

			callableStmt.setString(53, user);

			if (roleName.equalsIgnoreCase("CHECKER")) {
				callableStmt.setString(54, form1.getCheckerReturn());
			} else {
				callableStmt.setString(54, "New");
			}

			callableStmt.setString(55, roleName);
			callableStmt.setString(56, filenameName);

			if (roleName.equalsIgnoreCase("MAKER")) {

				callableStmt.setString(57, checkList1);
				callableStmt.setString(58, checkList2);
				callableStmt.setString(59, checkList3);
				callableStmt.setString(60, checkList4);
				callableStmt.setString(61, checkList5);
				callableStmt.setString(62, checkList6);
				callableStmt.setString(63, checkList7);
				callableStmt.setString(64, checkList8);
				callableStmt.setString(65, checkList9);
				callableStmt.setString(66, checkList10);
				callableStmt.setString(67, checkList11);
			} else {
				callableStmt.setString(57, null);
				callableStmt.setString(58, null);
				callableStmt.setString(59, null);
				callableStmt.setString(60, null);
				callableStmt.setString(61, null);
				callableStmt.setString(62, null);
				callableStmt.setString(63, null);
				callableStmt.setString(64, null);
				callableStmt.setString(65, null);
				callableStmt.setString(66, null);
				callableStmt.setString(67, null);
			}

			callableStmt.registerOutParameter(68, Types.VARCHAR);
			callableStmt.execute();

			status = callableStmt.getInt(1);
			errorCode = callableStmt.getString(68);
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
			} catch (Exception err) {
				System.out.println(err.getMessage());
				err.printStackTrace();
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

	public ArrayList getReduction(Connection conn) {

		ArrayList reductions = new ArrayList();
		CallableStatement callableStmt = null;
		ResultSet rs = null;
		String reduction = "Reasons for Reduction";
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
					reductions.add(option);
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
		return reductions;
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

			String cgpan = request.getParameter("cgPan");
			// Integer claimId =
			// Integer.parseInt(request.getParameter("claimId"));*/

			if ((cgpan != null && !cgpan.isEmpty())) {
				conn = DBConnection.getConnection(false);
				String sqlQuery = "select ATTACHMENTS,FILENAME  from  subdebt_claimprocessing where CGPAN=?";
				stmt = conn.prepareStatement(sqlQuery);
				stmt.setString(1, cgpan);
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
		ClaimProcessingActionForm form1 = (ClaimProcessingActionForm) form;
		ArrayList<NPAMarkingPopulateData> npaPopulateData = new ArrayList<NPAMarkingPopulateData>();
		int status = -1;
		String errorCode = "", message = "";
		Connection conn = null;
		try {
			/*
			 * System.out.println(
			 * "Inside method.... getNPAPopulatedData :: CGPAN" +
			 * form1.getCgpan2() + "\t member is ::" +
			 * form1.getPromoterNamee());
			 */
			if (conn == null) {
				conn = DBConnection.getConnection(false);
			}
			callableStmt = conn.prepareCall("{call FUNC_Get_SUBDEBT_AUDIT_HISTORY(?,?,?,?)}");
			callableStmt.registerOutParameter(1, Types.VARCHAR);
			callableStmt.setString(2, form1.getCgpan2());
			callableStmt.setString(3, form1.getPromoterNamee());
			callableStmt.registerOutParameter(4, Types.VARCHAR);

			callableStmt.execute();
			status = callableStmt.getInt(1);
			errorCode = callableStmt.getString(4);
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
			} else if (status == Constants.FUNCTION_SUCCESS) {

				rs = callableStmt.executeQuery();

				int i = rs.getRow();

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

				/*
				 * npaPopulateData.forEach((d) -> { System.out.println(
				 * "Data is ::::" + d.getClm_au_id() + "\t" + d.getClm_au_id() +
				 * "\t" + d.getClm_LVEL()); });
				 */
				message = "";
				// }

			}
		} catch (Exception err) {
			try {
				if (conn != null) {
					conn.rollback();
				}
			} catch (SQLException ignore) {
			}
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

	/*
	 * call
	 * FUNC_GET_FIRSTCLAIM_DETAILS_PRINT(@p,'CG20200000008TC','201001000000010',
	 * '002000180000','SADBEHE0001',@q); select @p,@q;
	 */

	public ArrayList<CheckListForm> getCheckListDtls(ActionMapping mapping, ClaimProcessingActionForm form1,
			HttpServletRequest request, HttpServletResponse respons) {

		ArrayList checkListData = new ArrayList();
		CallableStatement callableStmt = null;
		ResultSet rs = null;

		User user = getUserInformation(request);
		int status = -1;
		String errorCode = null;
		String message = "";
		Connection conn = null;
		try {
			String promoterName = form1.getPromoterNamee();
			String cgpan = form1.getCgpan2();
			String bankId = user.getBankId();
			String zoneId = user.getZoneId();
			String branchId = user.getBranchId();
			String memberId = bankId + zoneId + branchId;
			String userId = user.getUserId();
			// System.out.println("Member id= checkList==66==="+memberId);
			if (conn == null) {
				conn = DBConnection.getConnection(false);
			}

			callableStmt = conn.prepareCall("{call FUNC_GET_FIRSTCLAIM_DETAILS_PRINT(?,?,?,?,?,?)}");
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

				Log.log(Log.ERROR, "CPDAO", "getNpaMarkingSearchDetail()",
						"getNpaMarkingSearchDetail returns a 1. Error code is :" + errorCode);
				message = errorCode;
				if (callableStmt != null) {
					callableStmt.close();
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
				if (conn != null) {
					DBConnection.freeConnection(conn);
				}
			} catch (Exception e) {
				System.out.println(e.getMessage());
			}
		}
		return checkListData;
	}
}
