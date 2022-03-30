// Decompiled Using: FrontEnd Plus v2.03 and the JAD Engine
// Available From: http://www.reflections.ath.cx
// Decompiler options: packimports(3) 
// Source File Name:   Validator.java

package com.cgtsi.util;

import com.cgtsi.actionform.*;
import com.cgtsi.admin.*;
import com.cgtsi.application.*;
import com.cgtsi.claim.ClaimConstants;
import com.cgtsi.claim.ClaimDetail;
import com.cgtsi.claim.ClaimsProcessor;
import com.cgtsi.claim.MemberInfo;
import com.cgtsi.common.DatabaseException;
import com.cgtsi.common.Log;
import com.cgtsi.common.MessageException;
import com.cgtsi.common.NoDataException;
import com.cgtsi.guaranteemaintenance.GMDAO;
import com.cgtsi.guaranteemaintenance.GMProcessor;
import com.cgtsi.guaranteemaintenance.RecoveryProcedureTemp;
import com.cgtsi.investmentfund.IFProcessor;
import com.cgtsi.investmentfund.TransactionDetail;
import com.cgtsi.inwardoutward.IOProcessor;
import com.cgtsi.inwardoutward.Inward;
import com.cgtsi.registration.NoMemberFoundException;
import com.cgtsi.reports.QueryBuilderFields;
import com.cgtsi.risk.RiskManagementProcessor;
import java.io.*;
import java.text.ParsePosition;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import org.apache.commons.validator.*;
import org.apache.struts.action.*;
import org.apache.struts.upload.FormFile;
import org.apache.struts.validator.DynaValidatorActionForm;
import org.apache.struts.validator.Resources;

import sun.misc.OSEnvironment;

// Referenced classes of package com.cgtsi.util:
//            DateHelper, PropertyLoader, DateHelp

public class CopyOfValidator implements Serializable {

	static SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
static AdminDAO objAdminDAO= new AdminDAO();
static ApplicationDAO objApplicationDAO= new ApplicationDAO();
Administrator admin = new Administrator();
ParameterMaster paramMaster=null;
static double maxApprovedAmt=20000000D;
//maxApprovedAmt = paramMaster.getMaxApprovedAmt();
	private CopyOfValidator() {
	//	System.out.println("Validator constr exeecuted");
		try
		{
		//	paramMaster = admin.getParameter();
		//	  maxApprovedAmt = paramMaster.getMaxApprovedAmt();
		}
		catch(Exception e)
		{
			
		}
	}

	public static boolean verifyBankTypeAndApproveLoanAmountForWcEnhancement(String loanType,String creditGuaranteed,String creditFundBased,String creditNonFundBased,String bankId,HashMap basedOnCondition,String classificationofMLI)
	{
		//System.out.println("verifyBankTypeAndApproveLoanAmount  "+validateNumber(creditFundBased) );
		//System.out.println("verifyBankTypeAndApproveLoanAmount  "+validateNumber(creditNonFundBased) );
		LogClass.StepWritterConnIssue("verifyBankTypeAndApproveLoanAmount loanType"+loanType+"creditGuaranteed"+creditGuaranteed);
		boolean resultFlag=true;
		boolean validationFlag=false;
		double totalAmount=0.0;
		double existingAmountTC=0.0;
		try
		{
			
			 if(loanType.equalsIgnoreCase("wc") && validateNumber(creditFundBased)  && validateNumber(creditNonFundBased))
			{
				if(basedOnCondition.size()>0)
				{
					//System.out.println("sanctionDateAmountValidationAll 222=");
					if(basedOnCondition.get("none")!=null) // cgbid cgpan
					{
							if(basedOnCondition.get("previouslyCovered").equals("Y"))
									{
							//	System.out.println("fetchOldGauranteeAmount 10");
								existingAmountTC=objAdminDAO.fetchOldGauranteeAmountForEnahncementCases((String)basedOnCondition.get("none"),(String)basedOnCondition.get("unitValue"));
									}	
							
				 totalAmount=existingAmountTC+Double.parseDouble(creditFundBased)+Double.parseDouble(creditNonFundBased);
				// System.out.println("verifyBankTypeAndApproveLoanAmount wc totalAmount "+totalAmount );
				 validationFlag=true;
					}
				}
				else
				{
					 totalAmount=existingAmountTC+Double.parseDouble(creditFundBased)+Double.parseDouble(creditNonFundBased);
					// System.out.println("verifyBankTypeAndApproveLoanAmount wc totalAmount "+totalAmount );
					 validationFlag=true;
				}
			}
			
			
			if(validationFlag==true)
			{
				
				//	System.out.println("bankId  "+bankId );
				// String classificationofMLI = objApplicationDAO.getClassificationMLI(bankId);
			//	 System.out.println("classificationofMLI  "+classificationofMLI );
			//	 System.out.println("classificationofMLI  totalAmount"+totalAmount );
				 double rrbValue = 5000000.0D;
				 double fIValue = 10000000.0D;
				 if(classificationofMLI!=null)
				 {
				 if (((classificationofMLI.equals("RRB")) || (classificationofMLI == "RRB")) && 
					      (totalAmount > rrbValue))
				  {			
					// System.out.println("classificationofMLI RRB  totalAmount"+totalAmount );
					  resultFlag=false;
				  }
				  
				  else if (((classificationofMLI.equals("FI")) || (classificationofMLI == "FI")))
				  {			      
					  if(bankId.equals("0036"))
					  {
						  fIValue = maxApprovedAmt;
						  
					  }
					if((totalAmount > fIValue))
					{
						resultFlag=false;
					}
				  }
				  else
				  {
					//  if((totalAmount > fIValue))
						{
					//		resultFlag=false;
						}
				  }
				 }
			}
		}
		catch(Exception e)
		{
			 LogClass.StepWritterConnIssue("Exception in verifyBankTypeAndApproveLoanAmount "+e.getMessage());
			 LogClass.writeExceptionOnFile(e);
			e.printStackTrace();
			resultFlag=false;
		}
		return resultFlag;
		
	}
	
	public static boolean validateFileFormat(Object bean,
			ValidatorAction validAction, Field field, ActionErrors errors,
			HttpServletRequest request) {
		DynaActionForm dynaForm = (DynaActionForm) bean;
		FormFile file = (FormFile) dynaForm.get("bankStatementUploadFile");
		if (file != null && !file.getFileName().endsWith(".xml")) {
			ActionError actionError = new ActionError("enterXmlFile");
			errors.add("org.apache.struts.action.GLOBAL_ERROR", actionError);
		}
		return errors.isEmpty();
	}

	public static boolean validateForBank(Object bean,
			ValidatorAction validAction, Field field, ActionErrors errors,
			HttpServletRequest request) {
		DynaActionForm dynaForm = (DynaActionForm) bean;
		String flag = (String) dynaForm.get("ifChequed");
		if (flag.equals("Y")) {
			String bankName = (String) dynaForm.get("bankName");
			if (bankName == null || bankName.equals("")) {
				ActionError actionError = new ActionError("enterBankName");
				errors.add("org.apache.struts.action.GLOBAL_ERROR", actionError);
			}
		}
		return errors.isEmpty();
	}

	public static boolean validateForChequeNumber(Object bean,
			ValidatorAction validAction, Field field, ActionErrors errors,
			HttpServletRequest request) {
		DynaActionForm dynaForm = (DynaActionForm) bean;
		String flag = (String) dynaForm.get("ifChequed");
		if (flag.equals("Y")) {
			String chequeNumber = (String) dynaForm.get("chequeNumber");
			if (chequeNumber == null || chequeNumber.equals("")) {
				ActionError actionError = new ActionError("enterChequeNumber");
				errors.add("org.apache.struts.action.GLOBAL_ERROR", actionError);
			}
		}
		return errors.isEmpty();
	}

	public static boolean validateForChequeDate(Object bean,
			ValidatorAction validAction, Field field, ActionErrors errors,
			HttpServletRequest request) {
		DynaActionForm dynaForm = (DynaActionForm) bean;
		String flag = (String) dynaForm.get("ifChequed");
		if (flag.equals("Y")) {
			Date chequeDate = (Date) dynaForm.get("chequeDate");
			if (chequeDate == null || chequeDate.toString().equals("")) {
				ActionError actionError = new ActionError("enterChequeDate");
				errors.add("org.apache.struts.action.GLOBAL_ERROR", actionError);
			}
		}
		return errors.isEmpty();
	}

	public static boolean validateForChequeIssued(Object bean,
			ValidatorAction validAction, Field field, ActionErrors errors,
			HttpServletRequest request) {
		DynaActionForm dynaForm = (DynaActionForm) bean;
		String flag = (String) dynaForm.get("ifChequed");
		if (flag.equals("Y")) {
			String chequeIssuedTo = (String) dynaForm.get("chequeIssuedTo");
			if (chequeIssuedTo == null || chequeIssuedTo.equals("")) {
				ActionError actionError = new ActionError("enterChequeIssuedTo");
				errors.add("org.apache.struts.action.GLOBAL_ERROR", actionError);
			}
		}
		return errors.isEmpty();
	}

	public static boolean checkValueEntryForCheque(Object bean,
			ValidatorAction validAction, Field field, ActionErrors errors,
			HttpServletRequest request) {
		DynaActionForm dynaForm = (DynaActionForm) bean;
		String flag = (String) dynaForm.get("ifChequed");
		if (flag.equals("Y")) {
			String doubleFieldName = field.getProperty();
			Double doubleFieldVal = new Double(ValidatorUtil.getValueAsString(
					bean, doubleFieldName));
			double doubleFieldValue = doubleFieldVal.doubleValue();
			if (doubleFieldValue == 0.0D) {
				ActionError error = new ActionError((new StringBuilder())
						.append(doubleFieldName).append("required").toString());
				errors.add("org.apache.struts.action.GLOBAL_ERROR", error);
			} else if (doubleFieldValue == 0.0D) {
				ActionError error = new ActionError((new StringBuilder())
						.append(doubleFieldName).append("required").toString());
				errors.add("org.apache.struts.action.GLOBAL_ERROR", error);
			}
		}
		return errors.isEmpty();
	}

	public static boolean checkSubScheme(Object bean,
			ValidatorAction validAction, Field field, ActionErrors errors,
			HttpServletRequest request) throws DatabaseException {
		RiskManagementProcessor riskManagementProcessor = new RiskManagementProcessor();
		DynaActionForm dynaForm = (DynaActionForm) bean;
		ArrayList danRaisedArray = new ArrayList();
		String subScheme = (String) dynaForm.get("subScheme");
		danRaisedArray = riskManagementProcessor.getAllSubSchemeNames();
		if (danRaisedArray.contains(subScheme)) {
			ActionError actionError = new ActionError("enterDifferentname");
			errors.add("org.apache.struts.action.GLOBAL_ERROR", actionError);
		}
		return errors.isEmpty();
	}

	public static boolean differentBanks(Object bean,
			ValidatorAction validAction, Field field, ActionErrors errors,
			HttpServletRequest request) throws DatabaseException {
		DynaActionForm dynaForm = (DynaActionForm) bean;
		String fromBank = (String) dynaForm.get("bankName");
		String toBank = (String) dynaForm.get("toBankName");
		if (fromBank.equals(toBank)) {
			ActionError actionError = new ActionError("enterDifferentBanks");
			errors.add("org.apache.struts.action.GLOBAL_ERROR", actionError);
		}
		return errors.isEmpty();
	}

	public static boolean checkPrivilegesSelected(Object bean,
			ValidatorAction validAction, Field field, ActionErrors errors,
			HttpServletRequest request) {
		DynaActionForm dynaForm = (DynaActionForm) bean;
		Set privilegeKeys = Privileges.getKeys();
		Iterator iterator = privilegeKeys.iterator();
		boolean isPrivilegeAvl = false;
		while (iterator.hasNext()) {
			String key = (String) iterator.next();
			if (dynaForm.get(key) != null && !dynaForm.get(key).equals("")) {
				isPrivilegeAvl = true;
				break;
			}
		}
		if (!isPrivilegeAvl) {
			ActionError actionMessage = new ActionError("PrivilegeRequired");
			errors.add("org.apache.struts.action.GLOBAL_ERROR", actionMessage);
		}
		return errors.isEmpty();
	}

	public static boolean checkRolesAndPrivilegesSelected(Object bean,
			ValidatorAction validAction, Field field, ActionErrors errors,
			HttpServletRequest request) {
		Log.log(4, "Validator", "checkRolesAndPrivilegesSelected", "Entered");
		AdministrationActionForm adminForm = (AdministrationActionForm) bean;
		Set privilegeKeys = Privileges.getKeys();
		Iterator iterator = privilegeKeys.iterator();
		boolean isPrivilegeAvl = false;
		Map privileges = adminForm.getPrivileges();
		while (iterator.hasNext()) {
			String key = (String) iterator.next();
			if (privileges.get(key) != null && !privileges.get(key).equals("")) {
				isPrivilegeAvl = true;
				break;
			}
		}
		if (!isPrivilegeAvl) {
			Log.log(4, "Validator", "checkRolesAndPrivilegesSelected",
					"Found Errors...");
			ActionError actionMessage = new ActionError("PrivilegeRequired");
			errors.add("org.apache.struts.action.GLOBAL_ERROR", actionMessage);
		}
		Log.log(4, "Validator", "checkRolesAndPrivilegesSelected", "Exited");
		return errors.isEmpty();
	}

	public static boolean checkNewAndConfirmPassword(Object bean,
			ValidatorAction validAction, Field field, ActionErrors errors,
			HttpServletRequest request) {
		DynaActionForm dynaForm = (DynaActionForm) bean;
		String newPassword = (String) dynaForm.get("newPassword");
		String oldPassword = (String) dynaForm.get("oldPassword");
		String confirm = (String) dynaForm.get("confirm");
		if (!newPassword.equals(confirm)) {
			dynaForm.set("oldPassword", "");
			dynaForm.set("confirm", "");
			dynaForm.set("newPassword", "");
			ActionError actionMessage = new ActionError("passwordNotSame");
			errors.add("org.apache.struts.action.GLOBAL_ERROR", actionMessage);
			return errors.isEmpty();
		}
		if (oldPassword.equalsIgnoreCase(newPassword)) {
			dynaForm.set("oldPassword", "");
			dynaForm.set("confirm", "");
			dynaForm.set("newPassword", "");
			if (newPassword == null || newPassword.equals("")
					|| oldPassword == null || oldPassword.equals("")
					|| oldPassword == null || oldPassword.equals(""))
				return errors.isEmpty();
			ActionError actionMessage = new ActionError(
					"oldAndNewPasswordsSame");
			errors.add("org.apache.struts.action.GLOBAL_ERROR", actionMessage);
		}
		return errors.isEmpty();
	}

	public static boolean validateFromToDates(Object bean,
			ValidatorAction validAction, Field field, ActionErrors errors,
			HttpServletRequest request) {
		String fromValue = ValidatorUtil.getValueAsString(bean,
				field.getProperty());
		String sProperty2 = field.getVarValue("toDate");
		String toValue = ValidatorUtil.getValueAsString(bean, sProperty2);
		SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
		boolean fromDateValue = false;
		boolean toDateValue = false;
		if (!GenericValidator.isBlankOrNull(fromValue)
				&& !GenericValidator.isBlankOrNull(toValue)) {
			try {
				Date fromDate = sdf.parse(fromValue, new ParsePosition(0));
				if (fromDate == null)
					fromDateValue = false;
				else
					fromDateValue = true;
			} catch (Exception e) {
				fromDateValue = false;
			}
			try {
				Date toDate = sdf.parse(toValue, new ParsePosition(0));
				if (toDate == null)
					toDateValue = false;
				else
					toDateValue = true;
			} catch (Exception e) {
				toDateValue = false;
			}
			if (fromDateValue && toDateValue
					&& !DateHelper.day1BeforeDay2(fromValue, toValue)) {
				ActionError actionMessage = new ActionError(
						(new StringBuilder()).append("fromDateGT")
								.append(sProperty2).toString());
				errors.add("org.apache.struts.action.GLOBAL_ERROR",
						actionMessage);
			}
		}
		return errors.isEmpty();
	}

	public static boolean validateSanctionedDates(Object bean,
			ValidatorAction validAction, Field field, ActionErrors errors,
			HttpServletRequest request) {
		DynaActionForm dynaForm = (DynaActionForm) bean;
		String fromValue = ValidatorUtil.getValueAsString(bean,
				field.getProperty());
		String toValue = null;
		String dateString = "08/12/2008";
		Date dt = new Date();
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(dt);
		int year = calendar.get(Calendar.YEAR);
		int month = calendar.get(Calendar.MARCH);
		int day = calendar.get(Calendar.DATE);
		if (month >= 0 && month <= 2) {
			year--;
			calendar.set(Calendar.MONTH, 6);
			calendar.set(Calendar.DATE, 1);
			calendar.set(Calendar.YEAR, year);
		} else if (month >= 3 && month <= 5) {
			year--;
			calendar.set(Calendar.MONTH, 9);
			calendar.set(Calendar.DATE, 1);
			calendar.set(Calendar.YEAR, year);
		} else if (month >= 6 && month <= 8) {
			calendar.set(Calendar.MONTH, 0);
			calendar.set(Calendar.DATE, 1);
			calendar.set(Calendar.YEAR, year);
		} else if (month >= 9 && month <= 11) {
			calendar.set(Calendar.MONTH, 3);
			calendar.set(Calendar.DATE, 1);
			calendar.set(Calendar.YEAR, year);
		}
		SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
		boolean fromDateValue = false;
		boolean toDateValue = false;
		if (!GenericValidator.isBlankOrNull(fromValue)) {
			try {
				Date fromDate = sdf.parse(fromValue, new ParsePosition(0));
				if (fromDate == null)
					fromDateValue = false;
				else
					fromDateValue = true;
			} catch (Exception e) {
				fromDateValue = false;
			}
			try {
				Date toDate = calendar.getTime();
				toValue = sdf.format(toDate);
				if (toDate == null)
					toDateValue = false;
				else
					toDateValue = true;
			} catch (Exception e) {
				toDateValue = false;
			}
			if (fromDateValue && toDateValue
					&& DateHelper.day1BeforeDay2(fromValue, toValue)) {
				ActionError actionMessage = new ActionError(
						(new StringBuilder())
								.append("amountSanctionedDate cannot be before ")
								.append(toValue).toString());
				errors.add("org.apache.struts.action.GLOBAL_ERROR",
						actionMessage);
			}
		}
		return errors.isEmpty();
	}

	public static boolean validateUserLimits(Object bean,
			ValidatorAction validAction, Field field, ActionErrors errors,
			HttpServletRequest request) {
		String appLimitValue = ValidatorUtil.getValueAsString(bean,
				field.getProperty());
		String sProperty2 = field.getVarValue("claimLimitValue");
		String claimLimitValue = ValidatorUtil.getValueAsString(bean,
				sProperty2);
		String sAppFrom = field.getVarValue("appFromDate");
		String sAppTo = field.getVarValue("appToDate");
		String appFromDate = ValidatorUtil.getValueAsString(bean, sAppFrom);
		String appToDate = ValidatorUtil.getValueAsString(bean, sAppTo);
		String sClaimFrom = field.getVarValue("claimFromDate");
		String sClaimTo = field.getVarValue("claimToDate");
		String claimFromDate = ValidatorUtil.getValueAsString(bean, sClaimFrom);
		String claimToDate = ValidatorUtil.getValueAsString(bean, sClaimTo);
		boolean appFromValue = false;
		boolean appToValue = false;
		boolean claimFromValue = false;
		boolean claimToValue = false;
		SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
		if (!GenericValidator.isBlankOrNull(appLimitValue)
				&& Double.parseDouble(appLimitValue) == 0.0D)
			appLimitValue = "";
		if ((!GenericValidator.isBlankOrNull(appFromDate) || !GenericValidator
				.isBlankOrNull(appToDate))
				&& GenericValidator.isBlankOrNull(appLimitValue)) {
			ActionError actionMessage = new ActionError("appLimitRequired");
			errors.add("org.apache.struts.action.GLOBAL_ERROR", actionMessage);
		}
		if (!GenericValidator.isBlankOrNull(claimLimitValue)
				&& Double.parseDouble(claimLimitValue) == 0.0D)
			claimLimitValue = "";
		if ((!GenericValidator.isBlankOrNull(claimFromDate) || !GenericValidator
				.isBlankOrNull(claimToDate))
				&& GenericValidator.isBlankOrNull(claimLimitValue)) {
			ActionError actionMessage = new ActionError("claimLimitRequired");
			errors.add("org.apache.struts.action.GLOBAL_ERROR", actionMessage);
		}
		if (GenericValidator.isBlankOrNull(appLimitValue)
				&& GenericValidator.isBlankOrNull(claimLimitValue)) {
			ActionError actionMessage = new ActionError("appOrClaimLimit");
			errors.add("org.apache.struts.action.GLOBAL_ERROR", actionMessage);
		}
		if (!GenericValidator.isBlankOrNull(appLimitValue)) {
			if (GenericValidator.isBlankOrNull(appFromDate)) {
				ActionError actionMessage = new ActionError("appFromDate");
				errors.add("org.apache.struts.action.GLOBAL_ERROR",
						actionMessage);
			}
			if (!GenericValidator.isBlankOrNull(appFromDate)
					&& !GenericValidator.isBlankOrNull(appToDate)) {
				try {
					Date appValidFromDate = sdf.parse(appFromDate,
							new ParsePosition(0));
					if (appValidFromDate == null)
						appFromValue = false;
					else
						appFromValue = true;
				} catch (Exception n) {
					appFromValue = false;
				}
				try {
					Date appValidToDate = sdf.parse(appToDate,
							new ParsePosition(0));
					if (appValidToDate == null)
						appToValue = false;
					else
						appToValue = true;
				} catch (Exception n) {
					appToValue = false;
				}
				if (appFromValue && appToValue
						&& !DateHelper.day1BeforeDay2(appFromDate, appToDate)) {
					ActionError actionMessage = new ActionError(
							"appFromGTToDate");
					errors.add("org.apache.struts.action.GLOBAL_ERROR",
							actionMessage);
				}
			}
		}
		if (!GenericValidator.isBlankOrNull(claimLimitValue)) {
			if (GenericValidator.isBlankOrNull(claimFromDate)) {
				ActionError actionMessage = new ActionError("claimFromDate");
				errors.add("org.apache.struts.action.GLOBAL_ERROR",
						actionMessage);
			}
			if (!GenericValidator.isBlankOrNull(claimFromDate)
					&& !GenericValidator.isBlankOrNull(claimToDate)) {
				try {
					Date claimValidFromDate = sdf.parse(claimFromDate,
							new ParsePosition(0));
					if (claimValidFromDate == null)
						claimFromValue = false;
					else
						claimFromValue = true;
				} catch (Exception n) {
					claimFromValue = false;
				}
				try {
					Date claimValidToDate = sdf.parse(claimToDate,
							new ParsePosition(0));
					if (claimValidToDate == null)
						claimToValue = false;
					else
						claimToValue = true;
				} catch (Exception n) {
					claimToValue = false;
				}
				if (claimFromValue
						&& claimToValue
						&& !DateHelper.day1BeforeDay2(claimFromDate,
								claimToDate)) {
					ActionError actionMessage = new ActionError(
							"claimFromGTToDate");
					errors.add("org.apache.struts.action.GLOBAL_ERROR",
							actionMessage);
				}
			}
		}
		return errors.isEmpty();
	}

	public static boolean checkSFCalculationDate(Object bean,
			ValidatorAction validAction, Field field, ActionErrors errors,
			HttpServletRequest request) {
		Log.log(4, "Validator", "checkSFCalculationDate", "Entered");
		DynaActionForm dynaForm = (DynaActionForm) bean;
		Integer day = (Integer) dynaForm.get("serviceFeeCalculationDay");
		String month = (String) dynaForm.get("serviceFeeCalculationMonth");
		Log.log(4, "Validator", "checkSFCalculationDate",
				(new StringBuilder()).append("Day and Months are ").append(day)
						.append(" ").append(month).toString());
		if (day == null || day.equals("0"))
			return false;
		int dayInt = day.intValue();
		if (dayInt == 0)
			return false;
		Log.log(4, "Validator", "checkSFCalculationDate", (new StringBuilder())
				.append("Day integer is ").append(dayInt).toString());
		boolean isAvl = DateHelp.isDayAvlInMonth(dayInt, month);
		if (!isAvl) {
			Log.log(4, "Validator", "checkSFCalculationDate",
					"Day is not available in Month");
			ActionError actionError = new ActionError("serviceDayAndMonth");
			errors.add("org.apache.struts.action.GLOBAL_ERROR", actionError);
		}
		Log.log(4, "Validator", "checkSFCalculationDate", "Exited");
		return errors.isEmpty();
	}

	public static boolean checkIdType(Object bean, ValidatorAction validAction,
			Field field, ActionErrors errors, HttpServletRequest request) {
		Log.log(4, "Validator", "checkIdType", "Entered");
		DynaActionForm dynaForm = (DynaActionForm) bean;
		String idType = (String) dynaForm.get("idType");
		String idOther = (String) dynaForm.get("idTypeOther");
		Log.log(4, "Validator", "checkIdType",
				(new StringBuilder()).append("idType,idOther ").append(idType)
						.append(" ").append(idOther).toString());
		if (!idType.equals("none") && (idOther == null || idOther.equals(""))) {
			Log.log(4, "Validator", "checkIdType", "Id other is not entered.");
			ActionError error = null;
			if (idType.equals("cgbid")) {
				Log.log(4, "Validator", "checkIdType", "CGBID");
				error = new ActionError("cgbidRequired");
			} else {
				Log.log(4, "Validator", "checkIdType", "CGPAN");
				error = new ActionError("cgpanRequired");
			}
			errors.add("org.apache.struts.action.GLOBAL_ERROR", error);
		}
		Log.log(4, "Validator", "checkIdType", "Exited");
		return errors.isEmpty();
	}

	/*
	 * public static boolean checkConstitution(Object bean, ValidatorAction
	 * validAction, Field field, ActionErrors errors, HttpServletRequest
	 * request) { Log.log(4, "Validator", "checkConstitution", "Entered");
	 * DynaActionForm dynaForm = (DynaActionForm)bean; String constituition =
	 * (String)dynaForm.get("constitution"); String constituitionOther =
	 * (String)dynaForm.get("constitutionOther"); if(constituition == null ||
	 * constituition.equals("") || constituition.equals("Others")) { Log.log(4,
	 * "Validator", "checkConstitution", "constituition is not selected.");
	 * if(constituitionOther == null || constituitionOther.equals("")) {
	 * Log.log(4, "Validator", "checkConstitution",
	 * "constituitionOther is not entered"); ActionError error = new
	 * ActionError("constituitionRequired");
	 * errors.add("org.apache.struts.action.GLOBAL_ERROR", error); } }
	 * Log.log(4, "Validator", "checkConstitution", "Exited"); return
	 * errors.isEmpty(); }
	 */

	public static boolean checkConstitution(Object bean,
			ValidatorAction validAction, Field field, ActionErrors errors,
			HttpServletRequest request) {
		Log.log(4, "Validator", "checkConstitution", "Entered");
		DynaActionForm dynaForm = (DynaActionForm) bean;
		String constituition = (String) dynaForm.get("constitution");
		// System.out.println("checkConstitution:"+constituition);
		 
		// System.out.println("termsAndConditionCheck:"+dynaForm.get("termsAndConditionCheck"));
		// String constituitionOther =
		// (String)dynaForm.get("constitutionOther");
//		String PartnershipDeedDate = (String) ValidatorUtil.getValueAsString(
	//			bean, "PartnershipDeedDate");
		/*
		 * if(constituition == null || constituition.equals("") ||
		 * constituition.equals("Others")) { Log.log(4, "Validator",
		 * "checkConstitution", "constituition is not selected.");
		 * if(constituitionOther == null || constituitionOther.equals("")) {
		 * Log.log(4, "Validator", "checkConstitution",
		 * "constituitionOther is not entered"); ActionError error = new
		 * ActionError("constituitionRequired");
		 * errors.add("org.apache.struts.action.GLOBAL_ERROR", error); } }
		 */
		if (!GenericValidator.isBlankOrNull(constituition)) {
			/*if ("Partnership".equals(constituition)
					|| "Limited liability Partnership".equals(constituition)
					|| "private".equals(constituition)
					&& GenericValidator.isBlankOrNull(PartnershipDeedDate)) {

				//System.out.println("came to check partbnership date");
			//	ActionError error = new ActionError("PartnershipDeedDate");
			//	errors.add(ActionErrors.GLOBAL_ERROR, error);

			}*/
		} else {
			ActionError error = new ActionError("constituitionRequired");
			errors.add("org.apache.struts.action.GLOBAL_ERROR", error);
		}
		Log.log(4, "Validator", "checkConstitution", "Exited");
		return errors.isEmpty();
	}

	public static boolean checkDistrict(Object bean,
			ValidatorAction validAction, Field field, ActionErrors errors,
			HttpServletRequest request) {
		Log.log(4, "Validator", "checkDistrict", "Entered");
		DynaActionForm dynaForm = (DynaActionForm) bean;
		String district = (String) dynaForm.get("district");
		String districtOther = (String) dynaForm.get("districtOthers");
		if (district == null || district.equals("")
				|| district.equals("Others")) {
			Log.log(4, "Validator", "checkDistrict", "Distict is not selected.");
			if (districtOther == null || districtOther.equals("")) {
				Log.log(4, "Validator", "checkDistrict",
						"Distict Other is not entered");
				ActionError error = new ActionError("districtRequired");
				errors.add("org.apache.struts.action.GLOBAL_ERROR", error);
			}
		}
		Log.log(4, "Validator", "checkDistrict", "Exited");
		return errors.isEmpty();
	}

	public static boolean checkLegalId(Object bean,
			ValidatorAction validAction, Field field, ActionErrors errors,
			HttpServletRequest request) {
		Log.log(4, "Validator", "checkLegalId", "Entered");
		DynaActionForm dynaForm = (DynaActionForm) bean;
		String legalId = (String) dynaForm.get("cpLegalID");
		String legalIdOther = (String) dynaForm.get("otherCpLegalID");
		String legalIdValue = (String) dynaForm.get("cpLegalIdValue");
		if (legalId != null && !legalId.equals("") && legalId.equals("Others")) {
			Log.log(4, "Validator", "checkLegalId", "Legal Id is not selected.");
			if (legalIdOther == null || legalIdOther.equals("")) {
				Log.log(4, "Validator", "checkLegalId",
						"Legal Id Other is not entered");
				ActionError error = new ActionError("legalIdRequired");
				errors.add("org.apache.struts.action.GLOBAL_ERROR", error);
			} else if ((legalIdOther != null || !legalIdOther.equals(""))
					&& (legalIdValue == null || legalIdValue.equals(""))) {
				ActionError error = new ActionError("errors.required",
						"Legal Id Value");
				errors.add("org.apache.struts.action.GLOBAL_ERROR", error);
			}
		} else if (legalId != null && !legalId.equals("")
				&& !legalId.equals("Others")
				&& (legalIdValue == null || legalIdValue.equals(""))) {
			ActionError error = new ActionError("errors.required",
					"Legal Id Value");
			errors.add("org.apache.struts.action.GLOBAL_ERROR", error);
		}
		Log.log(4, "Validator", "checkLegalId", "Exited");
		return errors.isEmpty();
	}

	public static boolean checkSubsidyName(Object bean,
			ValidatorAction validAction, Field field, ActionErrors errors,
			HttpServletRequest request) {
		Log.log(4, "Validator", "checkSubsidyName", "Entered");
		DynaActionForm dynaForm = (DynaActionForm) bean;
		String subsidyName = (String) dynaForm.get("subsidyName");
		String subsidyNameOther = (String) dynaForm
				.get("otherSubsidyEquityName");
		// if((subsidyName == null || subsidyName.equals("")) &&
		// subsidyName.equals("Others"))
		if (!GenericValidator.isBlankOrNull(subsidyName)
				&& "Others".equals(subsidyName)) {
			Log.log(4, "Validator", "checkSubsidyName",
					"Subsidy Name is not selected.");
			// if(subsidyNameOther == null || subsidyNameOther.equals(""))
			if (GenericValidator.isBlankOrNull(subsidyNameOther)) {
				Log.log(4, "Validator", "checkSubsidyName",
						"Subsidy Name is not entered");
				ActionError error = new ActionError("subsidyNamerequired");
				errors.add("org.apache.struts.action.GLOBAL_ERROR", error);
			}
		}
		Log.log(4, "Validator", "checkSubsidyName", "Exited");
		return errors.isEmpty();
	}

	public static boolean checkWorthForName(Object bean,
			ValidatorAction validAction, Field field, ActionErrors errors,
			HttpServletRequest request) {
		String name = field.getProperty();

		String fieldName = ValidatorUtil.getValueAsString(bean,
				field.getProperty());
		String fieldWorth = field.getVarValue("worth");
		java.lang.Double doubleWorth = new Double(
				ValidatorUtil.getValueAsString(bean, fieldWorth));
		double nameWorth = doubleWorth.doubleValue();

		if ((fieldName != null) && (!(fieldName.equals("")))) {
			if (nameWorth == 0) {
				Log.log(Log.DEBUG, "Validator", "checkWorthForName", fieldWorth
						+ "for" + fieldName);

				ActionError error = new ActionError(fieldWorth);

				errors.add(ActionErrors.GLOBAL_ERROR, error);
			}
		} else if ((fieldName == null) || (fieldName.equals(""))) {
			if (nameWorth != 0) {
				ActionError error = new ActionError(name);

				errors.add(ActionErrors.GLOBAL_ERROR, error);
			}
		}
		return errors.isEmpty();

	}

	public static boolean checkMoreField(Object bean,
			ValidatorAction validAction, Field field, ActionErrors errors,
			HttpServletRequest request) {
		String mliID = ValidatorUtil
				.getValueAsString(bean, field.getProperty());
		String cgbid = field.getVarValue("cgbid");
		String cgbidVal = ValidatorUtil.getValueAsString(bean, cgbid);
		String cgpan = field.getVarValue("cgpan");
		String cgpanVal = ValidatorUtil.getValueAsString(bean, cgpan);
		String applicationRefNo = field.getVarValue("applicationRefNo");
		String applicationRefNoVal = ValidatorUtil.getValueAsString(bean,
				applicationRefNo);
		String borrowerName = field.getVarValue("borrowerName");
		String borrowerNameVal = ValidatorUtil.getValueAsString(bean,
				borrowerName);
		if (!GenericValidator.isBlankOrNull(mliID))
			if (GenericValidator.isBlankOrNull(cgbidVal)
					&& GenericValidator.isBlankOrNull(cgpanVal)
					&& GenericValidator.isBlankOrNull(applicationRefNoVal)
					&& GenericValidator.isBlankOrNull(borrowerNameVal)) {
				ActionError error = new ActionError("oneFieldRequired");
				errors.add("org.apache.struts.action.GLOBAL_ERROR", error);
			} else if (!GenericValidator.isBlankOrNull(cgbidVal)
					&& !GenericValidator.isBlankOrNull(applicationRefNoVal)
					|| !GenericValidator.isBlankOrNull(cgbidVal)
					&& !GenericValidator.isBlankOrNull(borrowerNameVal)
					|| !GenericValidator.isBlankOrNull(cgbidVal)
					&& !GenericValidator.isBlankOrNull(cgpanVal)
					|| !GenericValidator.isBlankOrNull(applicationRefNoVal)
					&& !GenericValidator.isBlankOrNull(borrowerNameVal)
					|| !GenericValidator.isBlankOrNull(applicationRefNoVal)
					&& !GenericValidator.isBlankOrNull(cgpanVal)
					|| !GenericValidator.isBlankOrNull(cgpanVal)
					&& !GenericValidator.isBlankOrNull(borrowerNameVal)
					|| !GenericValidator.isBlankOrNull(cgbidVal)
					&& !GenericValidator.isBlankOrNull(applicationRefNoVal)
					&& !GenericValidator.isBlankOrNull(borrowerNameVal)
					|| !GenericValidator.isBlankOrNull(cgbidVal)
					&& !GenericValidator.isBlankOrNull(cgpanVal)
					&& !GenericValidator.isBlankOrNull(borrowerNameVal)
					|| !GenericValidator.isBlankOrNull(cgbidVal)
					&& !GenericValidator.isBlankOrNull(applicationRefNoVal)
					&& !GenericValidator.isBlankOrNull(cgpanVal)
					|| !GenericValidator.isBlankOrNull(borrowerNameVal)
					&& !GenericValidator.isBlankOrNull(applicationRefNoVal)
					&& !GenericValidator.isBlankOrNull(cgpanVal)
					|| !GenericValidator.isBlankOrNull(borrowerNameVal)
					&& !GenericValidator.isBlankOrNull(applicationRefNoVal)
					&& !GenericValidator.isBlankOrNull(cgpanVal)
					&& !GenericValidator.isBlankOrNull(borrowerNameVal)
					&& !GenericValidator.isBlankOrNull(applicationRefNoVal)
					&& !GenericValidator.isBlankOrNull(cgpanVal)) {
				ActionError error = new ActionError("oneFieldRequired");
				errors.add("org.apache.struts.action.GLOBAL_ERROR", error);
			}
		return errors.isEmpty();
	}

	public static boolean checkAnyOneField(Object bean,
			ValidatorAction validAction, Field field, ActionErrors errors,
			HttpServletRequest request) {
		String mliID = ValidatorUtil
				.getValueAsString(bean, field.getProperty());
		String cgbid = field.getVarValue("cgbid");
		String cgbidVal = ValidatorUtil.getValueAsString(bean, cgbid);
		String cgpan = field.getVarValue("cgpan");
		String cgpanVal = ValidatorUtil.getValueAsString(bean, cgpan);
		if (!GenericValidator.isBlankOrNull(mliID))
			if (GenericValidator.isBlankOrNull(cgbidVal)
					&& GenericValidator.isBlankOrNull(cgpanVal)) {
				ActionError error = new ActionError("anyOneFieldRequired");
				errors.add("org.apache.struts.action.GLOBAL_ERROR", error);
			} else if (!GenericValidator.isBlankOrNull(cgbidVal)
					&& !GenericValidator.isBlankOrNull(cgpanVal)) {
				ActionError error = new ActionError("anyOneFieldRequired");
				errors.add("org.apache.struts.action.GLOBAL_ERROR", error);
			}
		return errors.isEmpty();
	}

	public static boolean checkEntryField(Object bean,
			ValidatorAction validAction, Field field, ActionErrors errors,
			HttpServletRequest request) {
		DynaActionForm dynaForm = (DynaActionForm) bean;
		String loanType = dynaForm.get("loanType").toString();
		Double fundBasedInterestVal = new Double(
				ValidatorUtil.getValueAsString(bean, field.getProperty()));
		double fundBasedInt = fundBasedInterestVal.doubleValue();
		String fundBasedDate = field.getVarValue("fundBasedSanctionedDate");
		String fundBasedDateVal = ValidatorUtil.getValueAsString(bean,
				fundBasedDate);
		String wcFundBasedSanctionedVal = field
				.getVarValue("fundBasedSanctioned");
		Double wcFundBasedSanctioned = new Double(
				ValidatorUtil.getValueAsString(bean, wcFundBasedSanctionedVal));
		double wcFundBasedSanctionedValue = wcFundBasedSanctioned.doubleValue();
		String creditFundBasedVal = field.getVarValue("creditFundBased");
		Double creditFundBased = new Double(ValidatorUtil.getValueAsString(
				bean, creditFundBasedVal));
		double creditFundBasedValue = creditFundBased.doubleValue();
		String creditNonFundBasedVal = field.getVarValue("creditNonFundBased");
		Double creditNonFundBased = new Double(ValidatorUtil.getValueAsString(
				bean, creditNonFundBasedVal));
		double creditNonFundBasedValue = creditNonFundBased.doubleValue();
		String nonFundBasedDate = field
				.getVarValue("nonFundBasedSanctionedDate");
		String nonFundBasedDateVal = ValidatorUtil.getValueAsString(bean,
				nonFundBasedDate);
		String wcNonFundBasedSanctionedVal = field
				.getVarValue("nonfundBasedSanctioned");
		Double wcNonFundBasedSanctioned = new Double(
				ValidatorUtil.getValueAsString(bean,
						wcNonFundBasedSanctionedVal));
		double wcNonFundBasedSanctionedValue = wcNonFundBasedSanctioned
				.doubleValue();
		String wcFundBasedCommissionVal = field
				.getVarValue("nonFundBasedCommission");
		Double wcFundBasedCommission = new Double(
				ValidatorUtil.getValueAsString(bean, wcFundBasedCommissionVal));
		double wcFundBasedCommissionValue = wcFundBasedCommission.doubleValue();
		if (wcFundBasedSanctionedValue != 0.0D) {
			if (fundBasedInt == 0.0D) {
				ActionError error = new ActionError("interestRequired");
				errors.add("org.apache.struts.action.GLOBAL_ERROR", error);
			}
			if (fundBasedDateVal.equals("")) {
				ActionError error = new ActionError("errors.required",
						"Date of Sanction For Fund Based");
				errors.add("org.apache.struts.action.GLOBAL_ERROR", error);
			}
			if (creditFundBasedValue == 0.0D) {
				ActionError error = new ActionError("errors.required",
						"Credit to be Guaranteed Fund Based Value ");
				errors.add("org.apache.struts.action.GLOBAL_ERROR", error);
			}
		} else {
			if ((fundBasedInt != 0.0D || !fundBasedDateVal.equals(""))
					&& wcFundBasedSanctionedValue == 0.0D) {
				boolean remarksVal = false;
				for (Iterator errorsIterator = errors.get(); errorsIterator
						.hasNext();) {
					ActionError error = (ActionError) errorsIterator.next();
					if (error.getKey().equals("fundBasedRequired")) {
						remarksVal = true;
						break;
					}
				}

				if (!remarksVal) {
					ActionError actionError = new ActionError(
							"fundBasedRequired");
					errors.add("org.apache.struts.action.GLOBAL_ERROR",
							actionError);
				}
			}
			if (creditFundBasedValue != 0.0D
					&& wcNonFundBasedSanctionedValue != 0.0D
					&& wcFundBasedSanctionedValue == 0.0D) {
				boolean remarksVal = false;
				for (Iterator errorsIterator = errors.get(); errorsIterator
						.hasNext();) {
					ActionError error = (ActionError) errorsIterator.next();
					if (error.getKey().equals("fundBasedRequired")) {
						remarksVal = true;
						break;
					}
				}

				if (!remarksVal) {
					ActionError actionError = new ActionError(
							"fundBasedRequired");
					errors.add("org.apache.struts.action.GLOBAL_ERROR",
							actionError);
				}
			}
		}
		if (wcNonFundBasedSanctionedValue != 0.0D) {
			if (wcFundBasedCommissionValue == 0.0D) {
				ActionError error = new ActionError("commissionRequired");
				errors.add("org.apache.struts.action.GLOBAL_ERROR", error);
			}
			if (nonFundBasedDateVal.equals("")) {
				ActionError error = new ActionError("sanctionedDateNfbRequired");
				errors.add("org.apache.struts.action.GLOBAL_ERROR", error);
			}
			if (creditNonFundBasedValue == 0.0D) {
				ActionError error = new ActionError("errors.required",
						"Credit Non Fund Based Value");
				errors.add("org.apache.struts.action.GLOBAL_ERROR", error);
			}
		} else {
			if ((wcFundBasedCommissionValue != 0.0D || !nonFundBasedDateVal
					.equals("")) && wcNonFundBasedSanctionedValue == 0.0D) {
				boolean remarksVal = false;
				for (Iterator errorsIterator = errors.get(); errorsIterator
						.hasNext();) {
					ActionError error = (ActionError) errorsIterator.next();
					if (error.getKey().equals("nonFundBasedRequired")) {
						remarksVal = true;
						break;
					}
				}

				if (!remarksVal) {
					ActionError actionError = new ActionError(
							"nonFundBasedRequired");
					errors.add("org.apache.struts.action.GLOBAL_ERROR",
							actionError);
				}
			}
			if (creditNonFundBasedValue != 0.0D
					&& wcNonFundBasedSanctionedValue == 0.0D
					&& wcFundBasedSanctionedValue != 0.0D) {
				boolean remarksVal = false;
				for (Iterator errorsIterator = errors.get(); errorsIterator
						.hasNext();) {
					ActionError error = (ActionError) errorsIterator.next();
					if (error.getKey().equals("nonFundBasedRequired")) {
						remarksVal = true;
						break;
					}
				}

				if (!remarksVal) {
					ActionError actionError = new ActionError(
							"nonFundBasedRequired");
					errors.add("org.apache.struts.action.GLOBAL_ERROR",
							actionError);
				}
			}
		}
		return errors.isEmpty();
	}

	public static boolean checkOsField(Object bean,
			ValidatorAction validAction, Field field, ActionErrors errors,
			HttpServletRequest request) {
		Double osFundBasedPplVal = new Double(ValidatorUtil.getValueAsString(
				bean, field.getProperty()));
		double osFundBasedPpl = osFundBasedPplVal.doubleValue();
		String osFBAsOnDateVal = field.getVarValue("osFBAsOnDate");
		String osFBAsOnDateValue = ValidatorUtil.getValueAsString(bean,
				osFBAsOnDateVal);
		String osNonFBPplVal = field.getVarValue("osNonFBPpl");
		Double osNonFBPpl = new Double(ValidatorUtil.getValueAsString(bean,
				osNonFBPplVal));
		double osNonFBPplValue = osNonFBPpl.doubleValue();
		String osNFBAsOnDateVal = field.getVarValue("osNFBAsOnDate");
		String osNFBAsOnDateValue = ValidatorUtil.getValueAsString(bean,
				osNFBAsOnDateVal);
		String wcFundBasedSanctionedVal = field
				.getVarValue("fundBasedSanctioned");
		Double wcFundBasedSanctioned = new Double(
				ValidatorUtil.getValueAsString(bean, wcFundBasedSanctionedVal));
		double wcFundBasedSanctionedValue = wcFundBasedSanctioned.doubleValue();
		String wcNonFundBasedSanctionedVal = field
				.getVarValue("nonfundBasedSanctioned");
		Double wcNonFundBasedSanctioned = new Double(
				ValidatorUtil.getValueAsString(bean,
						wcNonFundBasedSanctionedVal));
		double wcNonFundBasedSanctionedValue = wcNonFundBasedSanctioned
				.doubleValue();
		String fbSancDate = field.getVarValue("limitFundBasedSanctionedDate");
		String fbSancDateValue = ValidatorUtil.getValueAsString(bean,
				fbSancDate);
		String nfbSancDate = field
				.getVarValue("limitNonFundBasedSanctionedDate");
		String nfbSancDateValue = ValidatorUtil.getValueAsString(bean,
				nfbSancDate);
		HttpSession session = request.getSession(false);
		if (session.getAttribute("APPLICATION_TYPE_FLAG") != null
				&& (session.getAttribute("APPLICATION_TYPE_FLAG").equals("9")
						|| session.getAttribute("APPLICATION_TYPE_FLAG")
								.equals("8") || (session.getAttribute(
						"APPLICATION_TYPE_FLAG").equals("18") || session
						.getAttribute("APPLICATION_TYPE_FLAG").equals("19"))
						&& (session.getAttribute("APPLICATION_LOAN_TYPE")
								.equals("WC")
								|| session
										.getAttribute("APPLICATION_LOAN_TYPE")
										.equals("CC") || session.getAttribute(
								"APPLICATION_LOAN_TYPE").equals("BO")))) {
			SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
			if (osFundBasedPpl != 0.0D) {
				if (wcFundBasedSanctionedValue == 0.0D) {
					boolean remarksVal = false;
					for (Iterator errorsIterator = errors.get(); errorsIterator
							.hasNext();) {
						ActionError error = (ActionError) errorsIterator.next();
						if (error.getKey().equals("fundBasedRequired")) {
							remarksVal = true;
							break;
						}
					}

					if (!remarksVal) {
						ActionError actionError = new ActionError(
								"fundBasedRequired");
						errors.add("org.apache.struts.action.GLOBAL_ERROR",
								actionError);
					}
				} else if (wcFundBasedSanctionedValue != 0.0D
						&& osFBAsOnDateValue.equals("")) {
					ActionError error = new ActionError("outFBDate");
					errors.add("org.apache.struts.action.GLOBAL_ERROR", error);
				}
			} else if (osFundBasedPpl == 0.0D && !osFBAsOnDateValue.equals("")) {
				ActionError error = new ActionError("errors.required",
						"Outstanding Fund Based Principal");
				errors.add("org.apache.struts.action.GLOBAL_ERROR", error);
			}
			if (osNonFBPplValue != 0.0D) {
				if (wcNonFundBasedSanctionedValue == 0.0D) {
					boolean remarksVal = false;
					for (Iterator errorsIterator = errors.get(); errorsIterator
							.hasNext();) {
						ActionError error = (ActionError) errorsIterator.next();
						if (error.getKey().equals("nonFundBasedRequired")) {
							remarksVal = true;
							break;
						}
					}

					if (!remarksVal) {
						ActionError actionError = new ActionError(
								"nonFundBasedRequired");
						errors.add("org.apache.struts.action.GLOBAL_ERROR",
								actionError);
					}
				} else if (wcNonFundBasedSanctionedValue != 0.0D
						&& osNFBAsOnDateValue.equals("")) {
					ActionError error = new ActionError("outNFBDate");
					errors.add("org.apache.struts.action.GLOBAL_ERROR", error);
				}
			} else if (osNonFBPplValue == 0.0D
					&& !osNFBAsOnDateValue.equals("")) {
				ActionError error = new ActionError("errors.required",
						"Outstanding Non Fund Based Principal");
				errors.add("org.apache.struts.action.GLOBAL_ERROR", error);
			}
		}
		return errors.isEmpty();
	}

	public static boolean checkEnhancedTotal(Object bean,
			ValidatorAction validAction, Field field, ActionErrors errors,
			HttpServletRequest request) {
		Double existingTotalVal = new Double(ValidatorUtil.getValueAsString(
				bean, field.getProperty()));
		double existingTotalValue = existingTotalVal.doubleValue();
		String enhanceTotalVal = field.getVarValue("enhanceTotal");
		Double enhanceTotal = new Double(ValidatorUtil.getValueAsString(bean,
				enhanceTotalVal));
		double enhanceTotalValue = enhanceTotal.doubleValue();
		if (existingTotalValue > enhanceTotalValue) {
			ActionError error = new ActionError("enhancedTotalCheck");
			errors.add("org.apache.struts.action.GLOBAL_ERROR", error);
		}
		return errors.isEmpty();
	}

	public static boolean checkValueEntry(Object bean,
			ValidatorAction validAction, Field field, ActionErrors errors,
			HttpServletRequest request) {
		String doubleFieldName = field.getProperty();
		Double doubleFieldVal = new Double(ValidatorUtil.getValueAsString(bean,
				doubleFieldName));
		double doubleFieldValue = doubleFieldVal.doubleValue();
		if (doubleFieldValue == 0.0D) {
			ActionError error = new ActionError((new StringBuilder())
					.append(doubleFieldName).append("required").toString());
			errors.add("org.apache.struts.action.GLOBAL_ERROR", error);
		} else if (doubleFieldValue == 0.0D) {
			ActionError error = new ActionError((new StringBuilder())
					.append(doubleFieldName).append("required").toString());
			errors.add("org.apache.struts.action.GLOBAL_ERROR", error);
		}
		return errors.isEmpty();
	}

	public static boolean checkCoveredValue(Object bean,
			ValidatorAction validAction, Field field, ActionErrors errors,
			HttpServletRequest request) throws DatabaseException {
		String cgtsiCoveredValue = ValidatorUtil.getValueAsString(bean,
				field.getProperty());
		String typeValue = field.getVarValue("noneValue");
		String typeVal = ValidatorUtil.getValueAsString(bean, typeValue);
		String unitTypeVal = field.getVarValue("unit");
		String unittypeValue = ValidatorUtil
				.getValueAsString(bean, unitTypeVal);
		HttpSession session = request.getSession(false);
		if (cgtsiCoveredValue.equals("Y")) {
			if (session.getAttribute("APPLICATION_TYPE_FLAG").equals("7")
					|| session.getAttribute("APPLICATION_TYPE_FLAG")
							.equals("8")
					|| session.getAttribute("APPLICATION_TYPE_FLAG")
							.equals("9")
					|| session.getAttribute("APPLICATION_TYPE_FLAG").equals(
							"10"))
				if (typeVal.equals("cgpan")) {
					if (unittypeValue == null || unittypeValue.equals("")) {
						ActionError error = new ActionError("cgpanRequired");
						errors.add("org.apache.struts.action.GLOBAL_ERROR",
								error);
					}
				} else if (typeVal.equals("cgbid")) {
					if (unittypeValue == null || unittypeValue.equals("")) {
						ActionError error = new ActionError("cgbidRequired");
						errors.add("org.apache.struts.action.GLOBAL_ERROR",
								error);
					}
				} else if (typeVal.equals("none")) {
					ActionError error = new ActionError("cgpanCgbidSelected");
					errors.add("org.apache.struts.action.GLOBAL_ERROR", error);
				}
		} else if (cgtsiCoveredValue.equals("N")
				&& !session.getAttribute("APPLICATION_TYPE_FLAG").equals("3")
				&& !session.getAttribute("APPLICATION_TYPE_FLAG").equals("4")
				&& !session.getAttribute("APPLICATION_TYPE_FLAG").equals("5")
				&& !session.getAttribute("APPLICATION_TYPE_FLAG").equals("6")
				&& !session.getAttribute("APPLICATION_TYPE_FLAG").equals("14")
				&& !session.getAttribute("APPLICATION_TYPE_FLAG").equals("18")
				&& !session.getAttribute("APPLICATION_TYPE_FLAG").equals("19")) {
			DynaActionForm dynaForm = (DynaActionForm) bean;
			if ((dynaForm.get("constitution") == null
					|| dynaForm.get("constitution").equals("") || dynaForm.get(
					"constitution").equals("Others"))
					&& (dynaForm.get("constitutionOther") == null || dynaForm
							.get("constitutionOther").equals(""))) {
				Log.log(4, "Validator", "checkConstitution",
						"constituitionOther is not entered");
				ActionError error = new ActionError("constituitionRequired");
				errors.add("org.apache.struts.action.GLOBAL_ERROR", error);
			}
			if (dynaForm.get("state") == null
					|| dynaForm.get("state").equals("")) {
				ActionError error = new ActionError("errors.required", "State");
				errors.add("org.apache.struts.action.GLOBAL_ERROR", error);
			}
			if (dynaForm.get("city") == null || dynaForm.get("city").equals("")) {
				ActionError error = new ActionError("errors.required", "City");
				errors.add("org.apache.struts.action.GLOBAL_ERROR", error);
			}
			if ((dynaForm.get("district") == null
					|| dynaForm.get("district").equals("") || dynaForm.get(
					"district").equals("Others"))
					&& (dynaForm.get("districtOthers") == null || dynaForm.get(
							"districtOthers").equals(""))) {
				Log.log(4, "Validator", "checkDistrict",
						"Distict Other is not entered");
				ActionError error = new ActionError("districtRequired");
				errors.add("org.apache.struts.action.GLOBAL_ERROR", error);
			}
			if (dynaForm.get("ssiType") == null
					|| dynaForm.get("ssiType").equals("")) {
				ActionError error = new ActionError("errors.required",
						"Unit Type");
				errors.add("org.apache.struts.action.GLOBAL_ERROR", error);
			}
			if (dynaForm.get("ssiName") == null
					|| dynaForm.get("ssiName").equals("")) {
				ActionError error = new ActionError("errors.required",
						"SSI Name");
				errors.add("org.apache.struts.action.GLOBAL_ERROR", error);
			}
			if (dynaForm.get("employeeNos") == null
					|| dynaForm.get("employeeNos").equals("")) {
				ActionError error = new ActionError("errors.required",
						"noOfEmployees");
				errors.add("org.apache.struts.action.GLOBAL_ERROR", error);
			}
			if (dynaForm.get("projectedSalesTurnover") == null
					|| dynaForm.get("projectedSalesTurnover").equals("")) {
				ActionError error = new ActionError("errors.required",
						"turnover");
				errors.add("org.apache.struts.action.GLOBAL_ERROR", error);
			}
			if (dynaForm.get("projectedExports") == null
					|| dynaForm.get("projectedExports").equals("")) {
				ActionError error = new ActionError("errors.required",
						"exports");
				errors.add("org.apache.struts.action.GLOBAL_ERROR", error);
			}
			if (dynaForm.get("activityType") == null
					|| dynaForm.get("activityType").equals("")) {
				ActionError error = new ActionError("errors.required",
						"Activity Type");
				errors.add("org.apache.struts.action.GLOBAL_ERROR", error);
			}
			if (dynaForm.get("address") == null
					|| dynaForm.get("address").equals("")) {
				ActionError error = new ActionError("errors.required",
						"Unit Address");
				errors.add("org.apache.struts.action.GLOBAL_ERROR", error);
			}
			if (dynaForm.get("pincode") == null
					|| dynaForm.get("pincode").equals("")) {
				ActionError error = new ActionError("errors.required",
						"Pincode");
				errors.add("org.apache.struts.action.GLOBAL_ERROR", error);
			}
			if (dynaForm.get("cpTitle") == null
					|| dynaForm.get("cpTitle").equals("")) {
				ActionError error = new ActionError("errors.required",
						"Promoter Title");
				errors.add("org.apache.struts.action.GLOBAL_ERROR", error);
			}
			if (dynaForm.get("cpFirstName") == null
					|| dynaForm.get("cpFirstName").equals("")) {
				ActionError error = new ActionError("errors.required",
						"Chief Promoter First Name");
				errors.add("org.apache.struts.action.GLOBAL_ERROR", error);
			}
			if (dynaForm.get("cpLastName") == null
					|| dynaForm.get("cpLastName").equals("")) {
				ActionError error = new ActionError("errors.required",
						"Chief Promoter Last Name (Surname)");
				errors.add("org.apache.struts.action.GLOBAL_ERROR", error);
			}
			if (dynaForm.get("cpLegalID") != null
					&& dynaForm.get("cpLegalID").equals("Others")) {
				if (dynaForm.get("otherCpLegalID") == null
						|| dynaForm.get("otherCpLegalID").equals("")) {
					Log.log(4, "Validator", "checkLegalId",
							"Legal Id Other is not entered");
					ActionError error = new ActionError("legalIdRequired");
					errors.add("org.apache.struts.action.GLOBAL_ERROR", error);
				} else if (dynaForm.get("otherCpLegalID") != null
						&& !dynaForm.get("otherCpLegalID").equals("")
						&& (dynaForm.get("cpLegalIdValue") == null || dynaForm
								.get("cpLegalIdValue").equals(""))) {
					ActionError error = new ActionError("errors.required",
							"Legal Id Value");
					errors.add("org.apache.struts.action.GLOBAL_ERROR", error);
				}
			} else if (dynaForm.get("cpLegalID") != null
					&& !dynaForm.get("cpLegalID").equals("Others")
					&& !dynaForm.get("cpLegalID").equals("")
					&& (dynaForm.get("cpLegalIdValue") == null || dynaForm.get(
							"cpLegalIdValue").equals(""))) {
				ActionError error = new ActionError("errors.required",
						"Legal Id Value");
				errors.add("org.apache.struts.action.GLOBAL_ERROR", error);
			}
			if (dynaForm.get("subsidyName") != null
					&& dynaForm.get("subsidyName").equals("Others")
					&& (dynaForm.get("otherSubsidyEquityName") == null || dynaForm
							.get("otherSubsidyEquityName").equals(""))) {
				Log.log(4, "Validator", "checkSubsidyName",
						"Subsidy Name is not entered");
				ActionError error = new ActionError("subsidyNamerequired");
				errors.add("org.apache.struts.action.GLOBAL_ERROR", error);
			}
			Administrator admin = new Administrator();
			ParameterMaster parameterMaster = admin.getParameter();
			double minAmount = parameterMaster.getMinAmtForMandatoryITPAN();
			 SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
			 Date fixDateForITPan=null;
			 Date appSactionDate=null;
			 Date fundBaseDateForITPan=null;
			 Date nonFundBaseDateForITPan=null;
			 Date maxDateForBothLoanType=null;
			 SortedSet<Date> dates = new TreeSet<Date>();
			    try
				{
					fixDateForITPan =dateFormat.parse("01/04/2016");	
					//System.out.println("loanType is "+dynaForm.get("loanType"));
					if(dynaForm.get("loanType").equals("TC"))
					{
						//System.out.println("amountSanctionedDate "+dynaForm.get("amountSanctionedDate"));
						if(!dynaForm.get("amountSanctionedDate").toString().trim().equals(""))
						{
							//System.out.println("amountSanctionedDate is not blank");
							boolean flagAppTC=false;
							try
							{							
								appSactionDate=dateFormat.parse(dynaForm.get("amountSanctionedDate").toString());
							}
							catch(Exception e)
							{	
								//System.out.println("exception in"+e.getMessage());
								flagAppTC=true;
							}
							//System.out.println("flagAppTC value"+flagAppTC);
							if(flagAppTC==false)
							{
								if(appSactionDate.compareTo(fixDateForITPan)>=0)
								{
									// System.out.println("sac date is greater than or equal");
									 minAmount=500000;
								} else { System.out.println("loanType is TC==");}		
							}
						}
					}
					else if(dynaForm.get("loanType").equals("WC"))
					{
						if((!dynaForm.get("limitFundBasedSanctionedDate").toString().trim().equals("")) && (!dynaForm.get("limitNonFundBasedSanctionedDate").toString().trim().equals("")))
						{
							//System.out.println("WC both are not blank");
							boolean flagAppWC=false;
							try
							{
								fundBaseDateForITPan=dateFormat.parse(dynaForm.get("limitFundBasedSanctionedDate").toString());
								nonFundBaseDateForITPan=dateFormat.parse(dynaForm.get("limitNonFundBasedSanctionedDate").toString());
							}
							catch(Exception e) { flagAppWC=true; }
							
							if(flagAppWC==false)
							{
									if(fundBaseDateForITPan.compareTo(nonFundBaseDateForITPan)==1)
									{
										if(fundBaseDateForITPan.compareTo(fixDateForITPan)>=0)
										{
											// System.out.println("date is greater than or equal");
											 minAmount=500000;
										}	
									}
									else if(fundBaseDateForITPan.compareTo(nonFundBaseDateForITPan)==-1)
									{
										if(nonFundBaseDateForITPan.compareTo(fixDateForITPan)>=0)
										{
											// System.out.println("date is greater than or equal");
											 minAmount=500000;
										}	
									}
									else if(fundBaseDateForITPan.compareTo(nonFundBaseDateForITPan)==0)
									{
										if(nonFundBaseDateForITPan.compareTo(fixDateForITPan)>=0)
										{
											// System.out.println("date is greater than or equal");
											 minAmount=500000;
										}	
									}
							}
							
							
						}
						else if((dynaForm.get("limitFundBasedSanctionedDate").toString().trim().equals("")) && (!dynaForm.get("limitNonFundBasedSanctionedDate").toString().trim().equals("")))
						{
							//System.out.println("WC limitFundBasedSanctionedDate is blank");
							boolean flagAppWC=false;
							try
							{								
								nonFundBaseDateForITPan=dateFormat.parse(dynaForm.get("limitNonFundBasedSanctionedDate").toString());
							}
							catch(Exception e) { flagAppWC=true; }
							//System.out.println("WC limitFundBasedSanctionedDate is blank and flagAppWC value is "+flagAppWC);
							if(flagAppWC==false)
							{
								if(nonFundBaseDateForITPan.compareTo(fixDateForITPan)>=0)
								{
									 //System.out.println("date is greater than or equal");
									 minAmount=500000;
								}
							}
						}
						else if((!dynaForm.get("limitFundBasedSanctionedDate").toString().trim().equals("")) && (dynaForm.get("limitNonFundBasedSanctionedDate").toString().trim().equals("")))
						{
							//System.out.println("WC 1st is blank and 2nd is not blank");
							boolean flagAppWC=false;
							try
							{								
								fundBaseDateForITPan=dateFormat.parse(dynaForm.get("limitFundBasedSanctionedDate").toString());
							}
							catch(Exception e) { flagAppWC=true; }
							
							if(flagAppWC==false)
							{
								if(fundBaseDateForITPan.compareTo(fixDateForITPan)>=0)
								{
									// System.out.println("date is greater than or equal");
									 minAmount=500000;
								}
							}
						}
						
					}
					else if(dynaForm.get("loanType").equals("CC") || dynaForm.get("loanType").equals("BO"))
					{
						if(!dynaForm.get("amountSanctionedDate").toString().trim().equals(""))
						{
							boolean flagAppTC=false;
							try
							{							
								appSactionDate=dateFormat.parse(dynaForm.get("amountSanctionedDate").toString());
							}
							catch(Exception e)
							{			
								flagAppTC=true;
							}
							
							if(flagAppTC==false)
							{
								boolean flagAppBothValid=false;
								if((!dynaForm.get("limitFundBasedSanctionedDate").toString().trim().equals("")) && (!dynaForm.get("limitNonFundBasedSanctionedDate").toString().trim().equals("")))
								{
									try
									{
										fundBaseDateForITPan=dateFormat.parse(dynaForm.get("limitFundBasedSanctionedDate").toString());
										nonFundBaseDateForITPan=dateFormat.parse(dynaForm.get("limitNonFundBasedSanctionedDate").toString());
									}
									catch(Exception e)
									{
										flagAppBothValid=true;
									}
									
									if(flagAppBothValid==false)
									{
										 dates.add(appSactionDate);
										 dates.add(fundBaseDateForITPan);
										 dates.add(nonFundBaseDateForITPan);
										 
										 Date earliest = dates.last();
										 String BothTypeMaxDtae= dateFormat.format(earliest);
										 maxDateForBothLoanType=dateFormat.parse(BothTypeMaxDtae);
										 
										 if(maxDateForBothLoanType.compareTo(fixDateForITPan)>=0)
											{
												 //System.out.println("date is greater than or equal");
												 minAmount=500000;
											}	
									}
									 
								}
								if((dynaForm.get("limitFundBasedSanctionedDate").toString().trim().equals("")) && (!dynaForm.get("limitNonFundBasedSanctionedDate").toString().trim().equals("")))
								{
									try
									{
										
										nonFundBaseDateForITPan=dateFormat.parse(dynaForm.get("limitNonFundBasedSanctionedDate").toString());
									}
									catch(Exception e)
									{
										flagAppBothValid=true;
									}
									
									if(flagAppBothValid==false)
									{										 
										 dates.add(nonFundBaseDateForITPan);
										 dates.add(appSactionDate);
										 Date earliest = dates.last();
										 String BothTypeMaxDtae= dateFormat.format(earliest);
										 maxDateForBothLoanType=dateFormat.parse(BothTypeMaxDtae);
										 
										 if(maxDateForBothLoanType.compareTo(fixDateForITPan)>=0)
											{
												// System.out.println("date is greater than or equal");
												 minAmount=500000;
											}	
									}
									 
								}
								if((!dynaForm.get("limitFundBasedSanctionedDate").toString().trim().equals("")) && (dynaForm.get("limitNonFundBasedSanctionedDate").toString().trim().equals("")))
								{
									try
									{
										
										fundBaseDateForITPan=dateFormat.parse(dynaForm.get("limitFundBasedSanctionedDate").toString());
									}
									catch(Exception e)
									{
										flagAppBothValid=true;
									}
									
									if(flagAppBothValid==false)
									{										 
										 dates.add(fundBaseDateForITPan);
										 dates.add(appSactionDate);
										 Date earliest = dates.last();
										 String BothTypeMaxDtae= dateFormat.format(earliest);
										 maxDateForBothLoanType=dateFormat.parse(BothTypeMaxDtae);
										 
										 if(maxDateForBothLoanType.compareTo(fixDateForITPan)>=0)
											{
												 //System.out.println("date is greater than or equal");
												 minAmount=500000;
											}	
									}
									 
								}
								if((dynaForm.get("limitFundBasedSanctionedDate").toString().trim().equals("")) && (dynaForm.get("limitNonFundBasedSanctionedDate").toString().trim().equals("")))
								{
									 if(appSactionDate.compareTo(fixDateForITPan)>=0)
									 {
											//System.out.println("date is greater than or equal");
											minAmount=500000;
									 }	
									
									 
								}
							}
							
						}
					}
				}
				catch(Exception e)
				{					
				}
			//System.out.println("min amount for it pan"+minAmount);
			//System.out.println("amountSanctionedDate"+dynaForm.get("amountSanctionedDate"));
			//System.out.println("limitFundBasedSanctionedDate"+dynaForm.get("limitFundBasedSanctionedDate"));
			//System.out.println("limitNonFundBasedSanctionedDate"+dynaForm.get("limitNonFundBasedSanctionedDate"));
			if (dynaForm.get("cpITPAN") == null
					|| dynaForm.get("cpITPAN").equals(""))
				if (dynaForm.get("loanType").equals("TC")) {
					
					
					
					if (((Double) dynaForm.get("termCreditSanctioned"))
							.doubleValue() >= minAmount) {
						
						//System.out.println("Chief Promoter ITPAN");
						Log.log(4, "Validator", "checkSubsidyName",
								"ITPAN is not entered");
						ActionError error = new ActionError("errors.required",
								"Chief Promoter ITPAN");
						errors.add("org.apache.struts.action.GLOBAL_ERROR",
								error);
					}
				} else if (dynaForm.get("loanType").equals("CC")
						|| dynaForm.get("loanType").equals("BO")) {
					if (((Double) dynaForm.get("termCreditSanctioned"))
							.doubleValue()
							+ ((Double) dynaForm.get("wcFundBasedSanctioned"))
									.doubleValue()
							+ ((Double) dynaForm
									.get("wcNonFundBasedSanctioned"))
									.doubleValue() >= minAmount) {
						Log.log(4, "Validator", "checkSubsidyName",
								"ITPAN is not entered");
						ActionError error = new ActionError("errors.required",
								"Chief Promoter ITPAN");
						errors.add("org.apache.struts.action.GLOBAL_ERROR",
								error);
					}
				} else if (dynaForm.get("loanType").equals("WC")
						&& ((Double) dynaForm.get("wcFundBasedSanctioned"))
								.doubleValue()
								+ ((Double) dynaForm
										.get("wcNonFundBasedSanctioned"))
										.doubleValue() >= minAmount) {
					
					Log.log(4, "Validator", "checkSubsidyName",
							"ITPAN is not entered");
					ActionError error = new ActionError("errors.required",
							"Chief Promoter ITPAN");
					errors.add("org.apache.struts.action.GLOBAL_ERROR", error);
				}
			if (dynaForm.get("cpITPAN") != null
					&& !dynaForm.get("cpITPAN").equals("")) {
				String itpan = (String) dynaForm.get("cpITPAN");
				boolean itpanCheck = false;
				char array1[] = itpan.toCharArray();
				if (array1.length == 10 && Character.isLetter(array1[0])
						&& Character.isLetter(array1[1])
						&& Character.isLetter(array1[2])
						&& Character.isLetter(array1[3])
						&& Character.isLetter(array1[4])
						&& Character.isDigit(array1[5])
						&& Character.isDigit(array1[6])
						&& Character.isDigit(array1[7])
						&& Character.isDigit(array1[8])
						&& Character.isLetter(array1[9])) {
					itpanCheck = true;
				} else {
					Log.log(4, "Validator", "checkSubsidyName",
							"ITPAN is not entered");
					itpanCheck = false;
					ActionError error = new ActionError("errors.itpan",
							"Chief Promoter ITPAN");
					errors.add("org.apache.struts.action.GLOBAL_ERROR", error);
				}
			}
		}
		return errors.isEmpty();
	}

	public static boolean checkDBREntry(Object bean,
			ValidatorAction validAction, Field field, ActionErrors errors,
			HttpServletRequest request) {
		HttpSession session = request.getSession(false);
		Double amtDisbursedVal = new Double(ValidatorUtil.getValueAsString(
				bean, field.getProperty()));
		double amtDisbursedValue = amtDisbursedVal.doubleValue();
		String firstDBRDate = field.getVarValue("firstDate");
		String firstDBRDateValue = ValidatorUtil.getValueAsString(bean,
				firstDBRDate);
		String finalDBRDate = field.getVarValue("finalDate");
		String finalDBRDateValue = ValidatorUtil.getValueAsString(bean,
				finalDBRDate);
		if (session.getAttribute("APPLICATION_TYPE_FLAG") != null
				&& session.getAttribute("APPLICATION_TYPE_FLAG").equals("8")
				|| session.getAttribute("APPLICATION_TYPE_FLAG").equals("7")
				|| session.getAttribute("APPLICATION_TYPE_FLAG").equals("0"))
			if (amtDisbursedValue != 0.0D) {
				if (firstDBRDateValue == null || firstDBRDateValue.equals("")) {
					ActionError actionError = new ActionError(
							"firstDBRDateRequired");
					errors.add("org.apache.struts.action.GLOBAL_ERROR",
							actionError);
				} else if (finalDBRDateValue != null
						&& !finalDBRDateValue.equals("")
						&& (firstDBRDateValue == null || firstDBRDateValue
								.equals(""))) {
					ActionError actionError = new ActionError(
							"firstDBRDateRequired");
					errors.add("org.apache.struts.action.GLOBAL_ERROR",
							actionError);
				}
			} else if (amtDisbursedValue == 0.0D)
				if (firstDBRDateValue != null && !firstDBRDateValue.equals("")) {
					ActionError actionError = new ActionError(
							"amtDisbursedRequired");
					errors.add("org.apache.struts.action.GLOBAL_ERROR",
							actionError);
				} else if (finalDBRDateValue != null
						&& !finalDBRDateValue.equals("")
						&& (firstDBRDateValue == null || firstDBRDateValue
								.equals(""))) {
					ActionError actionError = new ActionError(
							"amtDisbursedDateRequired");
					errors.add("org.apache.struts.action.GLOBAL_ERROR",
							actionError);
				}
		return errors.isEmpty();
	}

	public static boolean checkWcEntry(Object bean,
			ValidatorAction validAction, Field field, ActionErrors errors,
			HttpServletRequest request) {
		DynaActionForm dynaForm = (DynaActionForm) bean;
		String loanType = (String) dynaForm.get("loanType");
		HttpSession session = request.getSession(false);
		Double wcFBVal = new Double(ValidatorUtil.getValueAsString(bean,
				field.getProperty()));
		double wcFBValue = wcFBVal.doubleValue();
		String wcNFBSanctioned = field.getVarValue("wcNFB");
		Double wcNFBSanctionedVal = new Double(ValidatorUtil.getValueAsString(
				bean, wcNFBSanctioned));
		double wcNFBSanctionedValue = wcNFBSanctionedVal.doubleValue();
		String wcPromoterCont = field.getVarValue("wcPCont");
		Double wcPromoterContVal = new Double(ValidatorUtil.getValueAsString(
				bean, wcPromoterCont));
		double wcPromoterContValue = wcPromoterContVal.doubleValue();
		String wcSubsidyEquity = field.getVarValue("wcSubsidy");
		Double wcSubsidyEquityVal = new Double(ValidatorUtil.getValueAsString(
				bean, wcSubsidyEquity));
		double wcSubsidyEquityValue = wcSubsidyEquityVal.doubleValue();
		String others = field.getVarValue("wcOther");
		Double wcOthersVal = new Double(ValidatorUtil.getValueAsString(bean,
				others));
		double wcOthersValue = wcOthersVal.doubleValue();
		Log.log(4,
				"Validator",
				"checkWcEntry",
				(new StringBuilder())
						.append("session.getAttribute(SessionConstants.APPLICATION_TYPE) :")
						.append(session.getAttribute("APPLICATION_TYPE"))
						.toString());
		if (loanType.equals("WC") || loanType.equals("CC")
				&& session.getAttribute("APPLICATION_TYPE") != null
				&& !session.getAttribute("APPLICATION_TYPE").equals("TCE")
				|| loanType.equals("CC")
				&& session.getAttribute("APPLICATION_TYPE") == null
				|| loanType.equals("BO")) {
			Log.log(4, "Validator", "checkWcEntry", (new StringBuilder())
					.append("loan type :").append(loanType).toString());
			if (wcFBValue == 0.0D && wcNFBSanctionedValue == 0.0D) {
				Log.log(4, "Validator", "checkWcEntry",
						"both WC Values not entered");
				ActionError actionError = new ActionError("fbNFBRequired");
				errors.add("org.apache.struts.action.GLOBAL_ERROR", actionError);
			}
		}
		if ((wcPromoterContValue != 0.0D || wcSubsidyEquityValue != 0.0D || wcOthersValue != 0.0D)
				&& wcFBValue == 0.0D && wcNFBSanctionedValue == 0.0D) {
			ActionError actionError = new ActionError("fbNFBRequired");
			errors.add("org.apache.struts.action.GLOBAL_ERROR", actionError);
		}
		return errors.isEmpty();
	}

	public static boolean checkTcEntry(Object bean,
			ValidatorAction validAction, Field field, ActionErrors errors,
			HttpServletRequest request) {
		DynaActionForm dynaForm = (DynaActionForm) bean;
		String loanType = (String) dynaForm.get("loanType");
		Double sanctionedVal = new Double(ValidatorUtil.getValueAsString(bean,
				field.getProperty()));
		double sanctionedValue = sanctionedVal.doubleValue();
		String tcPromoterCont = field.getVarValue("tcPromoterContribution");
		Double tcPromoterContVal = new Double(ValidatorUtil.getValueAsString(
				bean, tcPromoterCont));
		double tcPromoterContValue = tcPromoterContVal.doubleValue();
		String tcSubsidyEquity = field.getVarValue("tcSubsidyOrEquity");
		Double tcSubsidyEquityVal = new Double(ValidatorUtil.getValueAsString(
				bean, tcSubsidyEquity));
		double tcSubsidyEquityValue = tcSubsidyEquityVal.doubleValue();
		String tcother = field.getVarValue("tcOthers");
		Double tcotherVal = new Double(ValidatorUtil.getValueAsString(bean,
				tcother));
		double tcotherValue = tcotherVal.doubleValue();
		if ((tcPromoterContValue != 0.0D || tcSubsidyEquityValue != 0.0D || tcotherValue != 0.0D)
				&& sanctionedValue == 0.0D) {
			ActionError actionError = new ActionError(
					"termCreditSanctionedrequired");
			errors.add("org.apache.struts.action.GLOBAL_ERROR", actionError);
		}
		return errors.isEmpty();
	}

	public static boolean checkRateValue(Object bean,
			ValidatorAction validAction, Field field, ActionErrors errors,
			HttpServletRequest request) {
	//	System.out.println("checkRateValue ");
		String rateProperty = field.getProperty();
		Double rateVal = new Double(ValidatorUtil.getValueAsString(bean,
				field.getProperty()));
		double rateValue = rateVal.doubleValue();
		if (rateValue != 0.0D)
			if (rateValue > 100D) {
				ActionError actionError = new ActionError((new StringBuilder())
						.append(rateProperty).append("greater").toString());
				errors.add("org.apache.struts.action.GLOBAL_ERROR", actionError);
			} else if (rateValue == 100D) {
				ActionError actionError = new ActionError((new StringBuilder())
						.append(rateProperty).append("equal").toString());
				errors.add("org.apache.struts.action.GLOBAL_ERROR", actionError);
			}
		return errors.isEmpty();
	}

	public static boolean checkEnhanceValue(Object bean,
			ValidatorAction validAction, Field field, ActionErrors errors,
			HttpServletRequest request) {
		Double existingVal = new Double(ValidatorUtil.getValueAsString(bean,
				field.getProperty()));
		double existingValue = existingVal.doubleValue();
		String enhanceTotal = field.getVarValue("enhanceTotal");
		Double enhanceTotalVal = new Double(ValidatorUtil.getValueAsString(
				bean, enhanceTotal));
		double enhanceTotalValue = enhanceTotalVal.doubleValue();
		if (enhanceTotalValue < existingValue) {
			ActionError actionError = new ActionError("enhanceTotalgreater");
			errors.add("org.apache.struts.action.GLOBAL_ERROR", actionError);
		}
		return errors.isEmpty();
	}

	public static boolean checkEnhanceEntry(Object bean,
			ValidatorAction validAction, Field field, ActionErrors errors,
			HttpServletRequest request) {
		String enhanceRenewFbValue = field.getProperty();
		Double enhancedRenewFB = new Double(ValidatorUtil.getValueAsString(
				bean, field.getProperty()));
		double enhancedRenewFBValue = enhancedRenewFB.doubleValue();
		String enhancedRenewNFB = field.getVarValue("enhancedRenewNFB");
		Double enhancedRenewNFBVal = new Double(ValidatorUtil.getValueAsString(
				bean, enhancedRenewNFB));
		double enhancedRenewNFBValue = enhancedRenewNFBVal.doubleValue();
		String enhancedrenewFBInterest = field
				.getVarValue("enhancedrenewFBInterest");
		Double enhancedrenewFBInterestVal = new Double(
				ValidatorUtil.getValueAsString(bean, enhancedrenewFBInterest));
		double enhancedrenewFBInterestValue = enhancedrenewFBInterestVal
				.doubleValue();
		String enhancedrenewNFBComission = field
				.getVarValue("enhancedrenewNFBComission");
		Double enhancedrenewNFBComissionVal = new Double(
				ValidatorUtil.getValueAsString(bean, enhancedrenewNFBComission));
		double enhancedrenewNFBComissionValue = enhancedrenewNFBComissionVal
				.doubleValue();
		if (enhancedRenewFBValue == 0.0D && enhancedRenewNFBValue == 0.0D) {
			ActionError actionError = new ActionError("errors.required",
					"Working Capital Limit Sanctioned");
			errors.add("org.apache.struts.action.GLOBAL_ERROR", actionError);
		} else {
			if (enhancedRenewFBValue != 0.0D) {
				if (enhancedrenewFBInterestValue == 0.0D
						|| enhancedrenewFBInterestValue == 0.0D) {
					ActionError actionError = new ActionError(
							"errors.required", "Fund Based Interest");
					errors.add("org.apache.struts.action.GLOBAL_ERROR",
							actionError);
				}
			} else if (enhancedRenewFBValue == 0.0D
					&& enhancedrenewFBInterestValue != 0.0D) {
				ActionError actionError = new ActionError("errors.required",
						"Fund Based Limit Sanctioned");
				errors.add("org.apache.struts.action.GLOBAL_ERROR", actionError);
			}
			if (enhancedRenewNFBValue != 0.0D) {
				if (enhancedrenewNFBComissionValue == 0.0D) {
					ActionError actionError = new ActionError(
							"errors.required", "Non Fund Based Commission");
					errors.add("org.apache.struts.action.GLOBAL_ERROR",
							actionError);
				}
			} 
			 else
		            if(enhancedRenewNFBValue == 0.0D && enhancedrenewNFBComissionValue != 0.0D)
		            {
		                ActionError actionError = new ActionError("errors.required", "Non Fund Based Limit Sanctioned");
		                errors.add("org.apache.struts.action.GLOBAL_ERROR", actionError);
		            }
			
			
			
			
			/*else if (enhancedRenewNFBValue == 0.0D && enhancedrenewNFBComissionValue != 0.0D) 
			{ctionError actionError = new ActionError("errors.required","Non Fund Based Limit Sanctioned");
				errors.add("org.apache.struts.action.GLOBAL_ERROR", actionError);
			}*/
				
		}
		return errors.isEmpty();
	}

	public static boolean checkMarginMoneyAsTL(Object bean,
			ValidatorAction validAction, Field field, ActionErrors errors,
			HttpServletRequest request) {
		String marginMoneyValue = ValidatorUtil.getValueAsString(bean,
				field.getProperty());
		String wcFBSanctioned = field.getVarValue("wcFB");
		Double wcFBSanctionedVal = new Double(ValidatorUtil.getValueAsString(
				bean, wcFBSanctioned));
		double wcFBSanctionedValue = wcFBSanctionedVal.doubleValue();
		if ("Y".equals(marginMoneyValue) && wcFBSanctionedValue == 0.0D) {
			ActionError actionError = new ActionError("wcFBrequired");
			errors.add("org.apache.struts.action.GLOBAL_ERROR", actionError);
		}
		return errors.isEmpty();
	}

	public static boolean checkClearStatusComments(Object bean,
			ValidatorAction validAction, Field field, ActionErrors errors,
			HttpServletRequest request) {
		APForm apForm = (APForm) bean;
		Map clearRemarks = apForm.getClearRemarks();
		Set clearRemarksSet = clearRemarks.keySet();
		Iterator clearRemarksIterator = clearRemarksSet.iterator();
		Map clearStatus = apForm.getClearStatus();
		Set clearStatusSet = clearStatus.keySet();
		Iterator clearStatusIterator = clearStatusSet.iterator();
		Map clearApprovedAmt = apForm.getClearApprovedAmt();
		Set clearApprovedAmtSet = clearApprovedAmt.keySet();
		Iterator clearApprovedAmtIterator = clearApprovedAmtSet.iterator();
		Map clearCreditAmt = apForm.getClearCreditAmt();
		Set clearCreditAmtSet = clearCreditAmt.keySet();
		Iterator clearCreditAmtIterator = clearCreditAmtSet.iterator();
		Map clearAppRefNo = apForm.getClearAppRefNo();
		Set clearAppRefNoSet = clearAppRefNo.keySet();
		Iterator clearAppRefNoIterator = clearAppRefNoSet.iterator();
		boolean clearStatusVal = false;
		if (clearStatus != null && clearStatus.size() != 0)
			while (clearStatusIterator.hasNext()) {
				String key = (String) clearStatusIterator.next();
				if (clearStatus.get(key).equals("AP")) {
					if (clearApprovedAmt.get(key).equals("")
							|| !clearApprovedAmt.get(key).equals("")
							&& Double.parseDouble((String) clearApprovedAmt
									.get(key)) == 0.0D) {
						boolean remarksVal = false;
						for (Iterator errorsIterator = errors.get(); errorsIterator
								.hasNext();) {
							ActionError error = (ActionError) errorsIterator
									.next();
							if (error.getKey().equals("approvedAmtRequired")) {
								remarksVal = true;
								break;
							}
						}

						if (!remarksVal) {
							ActionError actionError = new ActionError(
									"approvedAmtRequired");
							errors.add("org.apache.struts.action.GLOBAL_ERROR",
									actionError);
						}
					} else if ((new Double((String) clearApprovedAmt.get(key)))
							.doubleValue() > (new Double(
							(String) clearCreditAmt.get(key))).doubleValue()) {
						Log.log(4, "Validator", "checkClearStatusComments",
								"Entered if greater");
						ActionError actionError = new ActionError(
								"checkClearStatusComments",
								clearAppRefNo.get(key));
						errors.add("org.apache.struts.action.GLOBAL_ERROR",
								actionError);
					}
				} else if (clearStatus.get(key).equals("HO")
						|| clearStatus.get(key).equals("RE")
						|| clearStatus.get(key).equals("PE")) {
					if (clearRemarks.get(key) == null
							|| clearRemarks.get(key).equals("")) {
						boolean remarksVal = false;
						for (Iterator errorsIterator = errors.get(); errorsIterator
								.hasNext();) {
							ActionError error = (ActionError) errorsIterator
									.next();
							if (error.getKey().equals("remarksRequired")) {
								remarksVal = true;
								break;
							}
						}

						if (!remarksVal) {
							ActionError actionError = new ActionError(
									"remarksRequired");
							errors.add("org.apache.struts.action.GLOBAL_ERROR",
									actionError);
						}
					}
					if (!clearApprovedAmt.get(key).equals("")
							&& Double.parseDouble((String) clearApprovedAmt
									.get(key)) != 0.0D) {
						boolean remarksVal = false;
						for (Iterator errorsIterator = errors.get(); errorsIterator
								.hasNext();) {
							ActionError error = (ActionError) errorsIterator
									.next();
							if (error.getKey().equals("amountNotRequired")) {
								remarksVal = true;
								break;
							}
						}

						if (!remarksVal) {
							ActionError actionError = new ActionError(
									"amountNotRequired");
							errors.add("org.apache.struts.action.GLOBAL_ERROR",
									actionError);
						}
					}
				}
			}
		return errors.isEmpty();
	}

	public static boolean checkDupStatusComments(Object bean,
			ValidatorAction validAction, Field field, ActionErrors errors,
			HttpServletRequest request) {
		APForm apForm = (APForm) bean;
		Map duplicateRemarks = apForm.getDuplicateRemarks();
		Map duplicateStatus = apForm.getDuplicateStatus();
		Map duplicateApprovedAmt = apForm.getDuplicateApprovedAmt();
		Set duplicateRemarksSet = duplicateRemarks.keySet();
		Set duplicateStatusSet = duplicateStatus.keySet();
		Set duplicateApprovedAmtSet = duplicateApprovedAmt.keySet();
		Iterator duplicateRemarksIterator = duplicateRemarksSet.iterator();
		Iterator duplicateStatusIterator = duplicateStatusSet.iterator();
		Iterator duplicateApprovedAmtIterator = duplicateApprovedAmtSet
				.iterator();
		Map dupCreditAmt = apForm.getDuplicateCreditAmt();
		Set dupCreditAmtSet = dupCreditAmt.keySet();
		Iterator dupCreditAmtIterator = dupCreditAmtSet.iterator();
		Map dupAppRefNo = apForm.getDuplicateAppRefNo();
		Set dupAppRefNoSet = dupAppRefNo.keySet();
		Iterator dupAppRefNoIterator = dupAppRefNoSet.iterator();
		ApplicationProcessor appProcessor = new ApplicationProcessor();
		boolean statusVal = false;
		if (duplicateStatus != null && duplicateStatus.size() != 0)
			while (duplicateStatusIterator.hasNext()) {
				String key = (String) duplicateStatusIterator.next();
				if (duplicateStatus.get(key).equals("AP")
						|| duplicateStatus.get(key).equals("ATL")
						|| duplicateStatus.get(key).equals("WCR")) {
					Application testApplication = new Application();
					try {
						testApplication = appProcessor.getApplication(null, "",
								(String) dupAppRefNo.get(key));
					} catch (NoApplicationFoundException e) {
					} catch (DatabaseException e) {
					}
					if (duplicateApprovedAmt.get(key).equals("")
							|| !duplicateApprovedAmt.get(key).equals("")
							&& Double.parseDouble((String) duplicateApprovedAmt
									.get(key)) == 0.0D) {
						boolean remarksVal = false;
						for (Iterator errorsIterator = errors.get(); errorsIterator
								.hasNext();) {
							ActionError error = (ActionError) errorsIterator
									.next();
							if (error.getKey().equals(
									"approvedAmtforATLRequired")) {
								remarksVal = true;
								break;
							}
						}

						if (!remarksVal) {
							ActionError actionError = new ActionError(
									"approvedAmtforATLRequired");
							errors.add("org.apache.struts.action.GLOBAL_ERROR",
									actionError);
						}
					} else if (duplicateRemarks.get(key) == null
							&& duplicateRemarks.get(key).equals("")) {
						boolean remarksVal = false;
						for (Iterator errorsIterator = errors.get(); errorsIterator
								.hasNext();) {
							ActionError error = (ActionError) errorsIterator
									.next();
							if (error.getKey().equals("remarksForATLRequired")) {
								remarksVal = true;
								break;
							}
						}

						if (!remarksVal) {
							ActionError actionError = new ActionError(
									"remarksForATLRequired");
							errors.add("org.apache.struts.action.GLOBAL_ERROR",
									actionError);
						}
					} else if ((new Double(
							(String) duplicateApprovedAmt.get(key)))
							.doubleValue() > (new Double(
							(String) dupCreditAmt.get(key))).doubleValue()) {
						Log.log(4, "Validator", "checkClearStatusComments",
								"Entered if greater");
						ActionError actionError = new ActionError(
								"checkClearStatusComments",
								dupAppRefNo.get(key));
						errors.add("org.apache.struts.action.GLOBAL_ERROR",
								actionError);
					} else if (testApplication.getStatus().equals("EN")
							&& testApplication.getApprovedAmount() >= (new Double(
									(String) duplicateApprovedAmt.get(key)))
									.doubleValue()) {
						ActionError actionError = new ActionError(
								"checkEnhancedApprovedAmount",
								dupAppRefNo.get(key));
						errors.add("org.apache.struts.action.GLOBAL_ERROR",
								actionError);
					}
					testApplication = null;
				} else if (duplicateStatus.get(key).equals("HO")
						|| duplicateStatus.get(key).equals("RE")
						|| duplicateStatus.get(key).equals("PE")) {
					if (duplicateRemarks.get(key) == null
							|| duplicateRemarks.get(key).equals("")) {
						boolean remarksVal = false;
						for (Iterator errorsIterator = errors.get(); errorsIterator
								.hasNext();) {
							ActionError error = (ActionError) errorsIterator
									.next();
							if (error.getKey().equals("remarksRequired")) {
								remarksVal = true;
								break;
							}
						}

						if (!remarksVal) {
							ActionError actionError = new ActionError(
									"remarksRequired");
							errors.add("org.apache.struts.action.GLOBAL_ERROR",
									actionError);
						}
					}
					if (!duplicateApprovedAmt.get(key).equals("")
							&& Double.parseDouble((String) duplicateApprovedAmt
									.get(key)) != 0.0D) {
						boolean remarksVal = false;
						for (Iterator errorsIterator = errors.get(); errorsIterator
								.hasNext();) {
							ActionError error = (ActionError) errorsIterator
									.next();
							if (error.getKey().equals("amountNotRequired")) {
								remarksVal = true;
								break;
							}
						}

						if (!remarksVal) {
							ActionError actionError = new ActionError(
									"amountNotRequired");
							errors.add("org.apache.struts.action.GLOBAL_ERROR",
									actionError);
						}
					}
				} else if (duplicateStatus.get(key).equals("EN")
						&& (duplicateRemarks.get(key) == null || duplicateRemarks
								.get(key).equals(""))) {
					boolean remarksVal = false;
					for (Iterator errorsIterator = errors.get(); errorsIterator
							.hasNext();) {
						ActionError error = (ActionError) errorsIterator.next();
						if (error.getKey().equals("remarksForENRequired")) {
							remarksVal = true;
							break;
						}
					}

					if (!remarksVal) {
						ActionError actionError = new ActionError(
								"remarksForENRequired");
						errors.add("org.apache.struts.action.GLOBAL_ERROR",
								actionError);
					}
				}
			}
		return errors.isEmpty();
	}

	public static boolean checkIneligibleStatusComments(Object bean,
			ValidatorAction validAction, Field field, ActionErrors errors,
			HttpServletRequest request) {
		APForm apForm = (APForm) bean;
		Map ineligibleRemarks = apForm.getIneligibleRemarks();
		Map ineligibleStatus = apForm.getIneligibleStatus();
		Map ineligibleApprovedAmt = apForm.getIneligibleApprovedAmt();
		Set ineligibleRemarksSet = ineligibleRemarks.keySet();
		Set ineligibleStatusSet = ineligibleStatus.keySet();
		Set ineligibleApprovedAmtSet = ineligibleApprovedAmt.keySet();
		Iterator ineligibleRemarksIterator = ineligibleRemarksSet.iterator();
		Iterator ineligibleStatusIterator = ineligibleStatusSet.iterator();
		Iterator ineligibleApprovedAmtIterator = ineligibleApprovedAmtSet
				.iterator();
		Map ineligibleCreditAmt = apForm.getIneligibleCreditAmt();
		Set ineligibleCreditAmtSet = ineligibleCreditAmt.keySet();
		Iterator ineligibleCreditAmtIterator = ineligibleCreditAmtSet
				.iterator();
		Map ineligibleAppRefNo = apForm.getIneligibleAppRefNo();
		Set ineligibleAppRefNoSet = ineligibleAppRefNo.keySet();
		Iterator ineligibleAppRefNoIterator = ineligibleAppRefNoSet.iterator();
		ApplicationProcessor appProcessor = new ApplicationProcessor();
		boolean statusVal = false;
		if (ineligibleStatus != null && ineligibleStatus.size() != 0)
			while (ineligibleStatusIterator.hasNext()) {
				String key = (String) ineligibleStatusIterator.next();
				if (ineligibleStatus.get(key).equals("AP")
						|| ineligibleStatus.get(key).equals("ATL")
						|| ineligibleStatus.get(key).equals("WCR")) {
					Application testApplication = new Application();
					try {
						testApplication = appProcessor.getApplication(null, "",
								(String) ineligibleAppRefNo.get(key));
					} catch (NoApplicationFoundException e) {
					} catch (DatabaseException e) {
					}
					if (ineligibleApprovedAmt.get(key).equals("")
							|| !ineligibleApprovedAmt.get(key).equals("")
							&& Double
									.parseDouble((String) ineligibleApprovedAmt
											.get(key)) == 0.0D) {
						boolean remarksVal = false;
						for (Iterator errorsIterator = errors.get(); errorsIterator
								.hasNext();) {
							ActionError error = (ActionError) errorsIterator
									.next();
							if (error.getKey().equals(
									"approvedAmtforATLRequired")) {
								remarksVal = true;
								break;
							}
						}

						if (!remarksVal) {
							ActionError actionError = new ActionError(
									"approvedAmtforATLRequired");
							errors.add("org.apache.struts.action.GLOBAL_ERROR",
									actionError);
						}
					} else if (ineligibleRemarks.get(key) == null
							&& ineligibleRemarks.get(key).equals("")) {
						boolean remarksVal = false;
						for (Iterator errorsIterator = errors.get(); errorsIterator
								.hasNext();) {
							ActionError error = (ActionError) errorsIterator
									.next();
							if (error.getKey().equals("remarksForATLRequired")) {
								remarksVal = true;
								break;
							}
						}

						if (!remarksVal) {
							ActionError actionError = new ActionError(
									"remarksForATLRequired");
							errors.add("org.apache.struts.action.GLOBAL_ERROR",
									actionError);
						}
					} else if ((new Double(
							(String) ineligibleApprovedAmt.get(key)))
							.doubleValue() > (new Double(
							(String) ineligibleCreditAmt.get(key)))
							.doubleValue()) {
						Log.log(4, "Validator", "checkClearStatusComments",
								"Entered if greater");
						ActionError actionError = new ActionError(
								"checkClearStatusComments",
								ineligibleAppRefNo.get(key));
						errors.add("org.apache.struts.action.GLOBAL_ERROR",
								actionError);
					} else if (testApplication.getStatus().equals("EN")
							&& testApplication.getApprovedAmount() >= (new Double(
									(String) ineligibleApprovedAmt.get(key)))
									.doubleValue()) {
						ActionError actionError = new ActionError(
								"checkEnhancedApprovedAmount",
								ineligibleAppRefNo.get(key));
						errors.add("org.apache.struts.action.GLOBAL_ERROR",
								actionError);
					}
					testApplication = null;
				} else if (ineligibleStatus.get(key).equals("HO")
						|| ineligibleStatus.get(key).equals("RE")
						|| ineligibleStatus.get(key).equals("PE")) {
					if (ineligibleRemarks.get(key) == null
							|| ineligibleRemarks.get(key).equals("")) {
						boolean remarksVal = false;
						for (Iterator errorsIterator = errors.get(); errorsIterator
								.hasNext();) {
							ActionError error = (ActionError) errorsIterator
									.next();
							if (error.getKey().equals("remarksRequired")) {
								remarksVal = true;
								break;
							}
						}

						if (!remarksVal) {
							ActionError actionError = new ActionError(
									"remarksRequired");
							errors.add("org.apache.struts.action.GLOBAL_ERROR",
									actionError);
						}
					}
					if (!ineligibleApprovedAmt.get(key).equals("")
							&& Double
									.parseDouble((String) ineligibleApprovedAmt
											.get(key)) != 0.0D) {
						boolean remarksVal = false;
						for (Iterator errorsIterator = errors.get(); errorsIterator
								.hasNext();) {
							ActionError error = (ActionError) errorsIterator
									.next();
							if (error.getKey().equals("amountNotRequired")) {
								remarksVal = true;
								break;
							}
						}

						if (!remarksVal) {
							ActionError actionError = new ActionError(
									"amountNotRequired");
							errors.add("org.apache.struts.action.GLOBAL_ERROR",
									actionError);
						}
					}
				} else if (ineligibleStatus.get(key).equals("EN")
						&& (ineligibleRemarks.get(key) == null || ineligibleRemarks
								.get(key).equals(""))) {
					boolean remarksVal = false;
					for (Iterator errorsIterator = errors.get(); errorsIterator
							.hasNext();) {
						ActionError error = (ActionError) errorsIterator.next();
						if (error.getKey().equals("remarksForENRequired")) {
							remarksVal = true;
							break;
						}
					}

					if (!remarksVal) {
						ActionError actionError = new ActionError(
								"remarksForENRequired");
						errors.add("org.apache.struts.action.GLOBAL_ERROR",
								actionError);
					}
				}
			}
		return errors.isEmpty();
	}

	public static boolean checkIneligibleDupStatusComments(Object bean,
			ValidatorAction validAction, Field field, ActionErrors errors,
			HttpServletRequest request) {
		APForm apForm = (APForm) bean;
		Map ineligibleDupRemarks = apForm.getIneligibleDupRemarks();
		Map ineligibleDupStatus = apForm.getIneligibleDupStatus();
		Map ineligibleDupApprovedAmt = apForm.getIneligibleDupApprovedAmt();
		Set ineligibleDupRemarksSet = ineligibleDupRemarks.keySet();
		Set ineligibleDupStatusSet = ineligibleDupStatus.keySet();
		Set ineligibleDupApprovedAmtSet = ineligibleDupApprovedAmt.keySet();
		Iterator ineligibleDupRemarksIterator = ineligibleDupRemarksSet
				.iterator();
		Iterator ineligibleDupStatusIterator = ineligibleDupStatusSet
				.iterator();
		Iterator ineligibleDupApprovedAmtIterator = ineligibleDupApprovedAmtSet
				.iterator();
		Map ineligibleDupCreditAmt = apForm.getIneligibleDupCreditAmt();
		Set ineligibleDupCreditAmtSet = ineligibleDupCreditAmt.keySet();
		Iterator ineligibleDupCreditAmtIterator = ineligibleDupCreditAmtSet
				.iterator();
		Map ineligibleDupAppRefNo = apForm.getIneligibleDupAppRefNo();
		Set ineligibleDupAppRefNoSet = ineligibleDupAppRefNo.keySet();
		Iterator ineligibleDupAppRefNoIterator = ineligibleDupAppRefNoSet
				.iterator();
		ApplicationProcessor appProcessor = new ApplicationProcessor();
		boolean statusVal = false;
		if (ineligibleDupStatus != null && ineligibleDupStatus.size() != 0)
			while (ineligibleDupStatusIterator.hasNext()) {
				String key = (String) ineligibleDupStatusIterator.next();
				if (ineligibleDupStatus.get(key).equals("AP")
						|| ineligibleDupStatus.get(key).equals("ATL")
						|| ineligibleDupStatus.get(key).equals("WCR")) {
					Application testApplication = new Application();
					try {
						testApplication = appProcessor.getApplication(null, "",
								(String) ineligibleDupAppRefNo.get(key));
					} catch (NoApplicationFoundException e) {
					} catch (DatabaseException e) {
					}
					if (ineligibleDupApprovedAmt.get(key).equals("")
							|| !ineligibleDupApprovedAmt.get(key).equals("")
							&& Double
									.parseDouble((String) ineligibleDupApprovedAmt
											.get(key)) == 0.0D) {
						boolean remarksVal = false;
						for (Iterator errorsIterator = errors.get(); errorsIterator
								.hasNext();) {
							ActionError error = (ActionError) errorsIterator
									.next();
							if (error.getKey().equals(
									"approvedAmtforATLRequired")) {
								remarksVal = true;
								break;
							}
						}

						if (!remarksVal) {
							ActionError actionError = new ActionError(
									"approvedAmtforATLRequired");
							errors.add("org.apache.struts.action.GLOBAL_ERROR",
									actionError);
						}
					} else if (ineligibleDupRemarks.get(key) == null
							&& ineligibleDupRemarks.get(key).equals("")) {
						boolean remarksVal = false;
						for (Iterator errorsIterator = errors.get(); errorsIterator
								.hasNext();) {
							ActionError error = (ActionError) errorsIterator
									.next();
							if (error.getKey().equals("remarksForATLRequired")) {
								remarksVal = true;
								break;
							}
						}

						if (!remarksVal) {
							ActionError actionError = new ActionError(
									"remarksForATLRequired");
							errors.add("org.apache.struts.action.GLOBAL_ERROR",
									actionError);
						}
					} else if ((new Double(
							(String) ineligibleDupApprovedAmt.get(key)))
							.doubleValue() > (new Double(
							(String) ineligibleDupCreditAmt.get(key)))
							.doubleValue()) {
						Log.log(4, "Validator", "checkClearStatusComments",
								"Entered if greater");
						ActionError actionError = new ActionError(
								"checkClearStatusComments",
								ineligibleDupAppRefNo.get(key));
						errors.add("org.apache.struts.action.GLOBAL_ERROR",
								actionError);
					} else if (testApplication.getStatus().equals("EN")
							&& testApplication.getApprovedAmount() >= (new Double(
									(String) ineligibleDupApprovedAmt.get(key)))
									.doubleValue()) {
						ActionError actionError = new ActionError(
								"checkEnhancedApprovedAmount",
								ineligibleDupAppRefNo.get(key));
						errors.add("org.apache.struts.action.GLOBAL_ERROR",
								actionError);
					}
				} else if (ineligibleDupStatus.get(key).equals("HO")
						|| ineligibleDupStatus.get(key).equals("RE")
						|| ineligibleDupStatus.get(key).equals("PE")) {
					if (ineligibleDupRemarks.get(key) == null
							|| ineligibleDupRemarks.get(key).equals("")) {
						boolean remarksVal = false;
						for (Iterator errorsIterator = errors.get(); errorsIterator
								.hasNext();) {
							ActionError error = (ActionError) errorsIterator
									.next();
							if (error.getKey().equals("remarksRequired")) {
								remarksVal = true;
								break;
							}
						}

						if (!remarksVal) {
							ActionError actionError = new ActionError(
									"remarksRequired");
							errors.add("org.apache.struts.action.GLOBAL_ERROR",
									actionError);
						}
					}
					if (!ineligibleDupApprovedAmt.get(key).equals("")
							&& Double
									.parseDouble((String) ineligibleDupApprovedAmt
											.get(key)) != 0.0D) {
						boolean remarksVal = false;
						for (Iterator errorsIterator = errors.get(); errorsIterator
								.hasNext();) {
							ActionError error = (ActionError) errorsIterator
									.next();
							if (error.getKey().equals("amountNotRequired")) {
								remarksVal = true;
								break;
							}
						}

						if (!remarksVal) {
							ActionError actionError = new ActionError(
									"amountNotRequired");
							errors.add("org.apache.struts.action.GLOBAL_ERROR",
									actionError);
						}
					}
				} else if (ineligibleDupStatus.get(key).equals("EN")
						&& (ineligibleDupRemarks.get(key) == null || ineligibleDupRemarks
								.get(key).equals(""))) {
					boolean remarksVal = false;
					for (Iterator errorsIterator = errors.get(); errorsIterator
							.hasNext();) {
						ActionError error = (ActionError) errorsIterator.next();
						if (error.getKey().equals("remarksForENRequired")) {
							remarksVal = true;
							break;
						}
					}

					if (!remarksVal) {
						ActionError actionError = new ActionError(
								"remarksForENRequired");
						errors.add("org.apache.struts.action.GLOBAL_ERROR",
								actionError);
					}
				}
			}
		return errors.isEmpty();
	}

	public static boolean checkStatusSelect(Object bean,
			ValidatorAction validAction, Field field, ActionErrors errors,
			HttpServletRequest request) {
		APForm apForm = (APForm) bean;
		Map clearStatus = apForm.getClearStatus();
		Set clearStatusSet = clearStatus.keySet();
		Iterator clearStatusIterator = clearStatusSet.iterator();
		Map duplicateStatus = apForm.getDuplicateStatus();
		Set duplicateStatusSet = duplicateStatus.keySet();
		Iterator duplicateStatusIterator = duplicateStatusSet.iterator();
		Map ineligibleStatus = apForm.getIneligibleStatus();
		Set ineligibleStatusSet = ineligibleStatus.keySet();
		Iterator ineligibleStatusIterator = ineligibleStatusSet.iterator();
		Map ineligibleDupStatus = apForm.getIneligibleDupStatus();
		Set ineligibleDupStatusSet = ineligibleDupStatus.keySet();
		Iterator ineligibleDupStatusIterator = ineligibleDupStatusSet
				.iterator();
		boolean clearVal = false;
		boolean dupVal = false;
		boolean ineligibleVal = false;
		boolean ineligibleDupVal = false;
		if (clearStatus != null && clearStatus.size() != 0)
			while (clearStatusIterator.hasNext()) {
				String key = (String) clearStatusIterator.next();
				if (!clearStatus.get(key).equals("")) {
					clearVal = true;
					break;
				}
			}
		if (duplicateStatus != null && duplicateStatus.size() != 0)
			while (duplicateStatusIterator.hasNext()) {
				String key = (String) duplicateStatusIterator.next();
				if (!duplicateStatus.get(key).equals("")) {
					dupVal = true;
					break;
				}
			}
		if (ineligibleStatus != null && ineligibleStatus.size() != 0)
			while (ineligibleStatusIterator.hasNext()) {
				String key = (String) ineligibleStatusIterator.next();
				if (!ineligibleStatus.get(key).equals("")) {
					ineligibleVal = true;
					break;
				}
			}
		if (ineligibleDupStatus != null && ineligibleDupStatus.size() != 0)
			while (ineligibleDupStatusIterator.hasNext()) {
				String key = (String) ineligibleDupStatusIterator.next();
				if (!ineligibleDupStatus.get(key).equals("")) {
					ineligibleDupVal = true;
					break;
				}
			}
		if (!clearVal && !dupVal && !ineligibleVal && !ineligibleDupVal) {
			ActionError actionError = new ActionError("statusRequired");
			errors.add("org.apache.struts.action.GLOBAL_ERROR", actionError);
		}
		return errors.isEmpty();
	}

	public static boolean checkReapproveStatusComments(Object bean,
			ValidatorAction validAction, Field field, ActionErrors errors,
			HttpServletRequest request) {
		APForm apForm = (APForm) bean;
		Map reapprovedStatus = apForm.getReapprovalStatus();
		Map reapprovedAmt = apForm.getReApprovedAmt();
		Map reapprovedRemarks = apForm.getReApprovalRemarks();
		Map reapprovedCreditAmt = apForm.getCreditAmt();
		Map reapprovedCgpan = apForm.getCgpanNo();
		Set statusSet = reapprovedStatus.keySet();
		Set amountSet = reapprovedAmt.keySet();
		Set remarksSet = reapprovedRemarks.keySet();
		Set reapprovedCreditAmtSet = reapprovedCreditAmt.keySet();
		Set reapprovedCgpanSet = reapprovedCgpan.keySet();
		Iterator statusIterator = statusSet.iterator();
		Iterator amountIterator = amountSet.iterator();
		Iterator remarksIterator = remarksSet.iterator();
		Iterator reapprovedCreditIterator = reapprovedCreditAmtSet.iterator();
		Iterator reapprovedCgpanIterator = reapprovedCgpanSet.iterator();
		while (statusIterator.hasNext()) {
			String key = (String) statusIterator.next();
			if (reapprovedStatus.get(key).equals("AP")
					|| reapprovedStatus.get(key).equals("EN")) {
				if (reapprovedAmt.get(key).equals("")
						|| !reapprovedAmt.get(key).equals("")
						&& Double.parseDouble((String) reapprovedAmt.get(key)) == 0.0D) {
					boolean remarksVal = false;
					for (Iterator errorsIterator = errors.get(); errorsIterator
							.hasNext();) {
						ActionError error = (ActionError) errorsIterator.next();
						if (error.getKey().equals("reApprovedAmtRequired")) {
							remarksVal = true;
							break;
						}
					}

					if (!remarksVal) {
						ActionError actionError = new ActionError(
								"reApprovedAmtRequired");
						errors.add("org.apache.struts.action.GLOBAL_ERROR",
								actionError);
					}
				}
				if (reapprovedRemarks.get(key) == null
						|| reapprovedRemarks.get(key).equals("")) {
					boolean remarksVal = false;
					for (Iterator errorsIterator = errors.get(); errorsIterator
							.hasNext();) {
						ActionError error = (ActionError) errorsIterator.next();
						if (error.getKey().equals("reapprovalRemarksRequired")) {
							remarksVal = true;
							break;
						}
					}

					if (!remarksVal) {
						ActionError actionError = new ActionError(
								"reapprovalRemarksRequired");
						errors.add("org.apache.struts.action.GLOBAL_ERROR",
								actionError);
					}
				}
				if ((new Double((String) reapprovedAmt.get(key))).doubleValue() > (new Double(
						(String) reapprovedCreditAmt.get(key))).doubleValue()) {
					ActionError actionError = new ActionError(
							"checkReapproveStatusComments",
							reapprovedCgpan.get(key));
					errors.add("org.apache.struts.action.GLOBAL_ERROR",
							actionError);
				}
			} else if (reapprovedStatus.get(key).equals("HO")
					|| reapprovedStatus.get(key).equals("RE")) {
				if (reapprovedRemarks.get(key) == null
						|| reapprovedRemarks.get(key).equals("")) {
					boolean remarksVal = false;
					for (Iterator errorsIterator = errors.get(); errorsIterator
							.hasNext();) {
						ActionError error = (ActionError) errorsIterator.next();
						if (error.getKey().equals("reapprovalRemarksRequired")) {
							remarksVal = true;
							break;
						}
					}

					if (!remarksVal) {
						ActionError actionError = new ActionError(
								"reapprovalRemarksRequired");
						errors.add("org.apache.struts.action.GLOBAL_ERROR",
								actionError);
					}
				}
				if (!reapprovedAmt.get(key).equals("")
						&& Double.parseDouble((String) reapprovedAmt.get(key)) != 0.0D) {
					boolean remarksVal = false;
					for (Iterator errorsIterator = errors.get(); errorsIterator
							.hasNext();) {
						ActionError error = (ActionError) errorsIterator.next();
						if (error.getKey()
								.equals("reapprovedAmountNotRequired")) {
							remarksVal = true;
							break;
						}
					}

					if (!remarksVal) {
						ActionError actionError = new ActionError(
								"reapprovedAmountNotRequired");
						errors.add("org.apache.struts.action.GLOBAL_ERROR",
								actionError);
					}
				}
			}
		}
		return errors.isEmpty();
	}

	public static boolean checkReapproveStatus(Object bean,
			ValidatorAction validAction, Field field, ActionErrors errors,
			HttpServletRequest request) {
		APForm apForm = (APForm) bean;
		Map reapprovedStatus = apForm.getReapprovalStatus();
		Set reapproveStatusSet = reapprovedStatus.keySet();
		Iterator reapproveStatusIterator = reapproveStatusSet.iterator();
		boolean reapproveStatus = false;
		if (reapprovedStatus != null && reapprovedStatus.size() != 0)
			while (reapproveStatusIterator.hasNext()) {
				String key = (String) reapproveStatusIterator.next();
				if (!reapprovedStatus.get(key).equals("")) {
					reapproveStatus = true;
					break;
				}
			}
		if (!reapproveStatus) {
			ActionError actionError = new ActionError("statusRequired");
			errors.add("org.apache.struts.action.GLOBAL_ERROR", actionError);
		}
		return errors.isEmpty();
	}

	public static boolean checkCreditAmtGreater(Object bean,
			ValidatorAction validAction, Field field, ActionErrors errors,
			HttpServletRequest request) {
		

	//	System.out.println("checkCreditAmtGreater");
		Double creditVal = new Double(ValidatorUtil.getValueAsString(bean,
				field.getProperty()));
		String creditString = field.getProperty();
		double creditValue = creditVal.doubleValue();
		String termCreditSanctionedVal = field
				.getVarValue("termCreditSanctioned");
		Double termCreditSanctioned = new Double(
				ValidatorUtil.getValueAsString(bean, termCreditSanctionedVal));
		double termCreditSanctionedValue = termCreditSanctioned.doubleValue();
		if (creditValue != 0.0D && termCreditSanctionedValue != 0.0D
				&& creditValue > termCreditSanctionedValue) {
			ActionError error = new ActionError((new StringBuilder())
					.append("creditAmtLess").append(creditString).toString());
			errors.add("org.apache.struts.action.GLOBAL_ERROR", error);
		}
		
	
		
		return errors.isEmpty();
	}
	
	
	

	
	
	

	public static boolean checkDisburseOsEntry(Object bean,
			ValidatorAction validAction, Field field, ActionErrors errors,
			HttpServletRequest request) throws Exception {
		Double amtdisbursedVal = new Double(ValidatorUtil.getValueAsString(
				bean, field.getProperty()));
		double amtdisbursed = amtdisbursedVal.doubleValue();
		String pplOSVal = field.getVarValue("pplOS");
		Double pplOS = new Double(
				ValidatorUtil.getValueAsString(bean, pplOSVal));
		double pplOSValue = pplOS.doubleValue();
		String pplOsAsOnDateVal = field.getVarValue("pplOsAsOnDate");
		String pplOsAsOnDateValue = ValidatorUtil.getValueAsString(bean,
				pplOsAsOnDateVal);
		String firstInstallmentDueDateVal = field
				.getVarValue("firstInstallmentDueDate");
		String firstInstallmentDueDateValue = ValidatorUtil.getValueAsString(
				bean, firstInstallmentDueDateVal);
		String finalDisDateVal = field.getVarValue("finalDate");
		String finalDisDate = ValidatorUtil.getValueAsString(bean,
				finalDisDateVal);
		String firstDisDateVal = field.getVarValue("firstDisbursementDate");
		String firstDBRDateValue = ValidatorUtil.getValueAsString(bean,
				firstDisDateVal);
		HttpSession session = request.getSession(false);
		if (session.getAttribute("APPLICATION_TYPE_FLAG") == null
				|| session.getAttribute("APPLICATION_TYPE_FLAG").equals(""))
			System.out
					.println("Line Number 3790 Application Loan Type is Null");
		else if (session.getAttribute("APPLICATION_TYPE_FLAG") != null
				|| session.getAttribute("APPLICATION_TYPE_FLAG").toString()
						.equals(""))
			//System.out.println((new StringBuilder())
				//	.append("Line number 3792 Loan Type:")
				//	.append(session.getAttribute("APPLICATION_TYPE_FLAG")
					//		.toString()).toString());
		if ((session.getAttribute("APPLICATION_TYPE_FLAG").equals("3")
				|| session.getAttribute("APPLICATION_TYPE_FLAG").equals("7")
				|| session.getAttribute("APPLICATION_TYPE_FLAG").equals("0")
				|| session.getAttribute("APPLICATION_TYPE_FLAG").equals("8")
				|| session.getAttribute("APPLICATION_TYPE_FLAG").equals("10")
				|| session.getAttribute("APPLICATION_TYPE_FLAG").equals("14")
				|| session.getAttribute("APPLICATION_TYPE_FLAG").equals("19")
				|| session.getAttribute("APPLICATION_LOAN_TYPE").equals("TC")
				|| session.getAttribute("APPLICATION_LOAN_TYPE").equals("CC")
				|| session.getAttribute("APPLICATION_LOAN_TYPE").equals("WC") || session
				.getAttribute("APPLICATION_LOAN_TYPE").equals("BO"))
				&& amtdisbursed != 0.0D) {
			SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
			boolean finaldateValue = false;
			boolean firstdateValue = false;
			boolean firstInstdateValue = false;
			boolean pplOsdateValue = false;
			Date pplOsAsOnDate = null;
			Date firstDisDate = null;
			if (pplOsAsOnDateValue != null && !pplOsAsOnDateValue.equals("")) {
				pplOsdateValue = true;
				try {
					pplOsAsOnDate = sdf.parse(pplOsAsOnDateValue,
							new ParsePosition(0));
					if (pplOsAsOnDate == null) {
						ActionError actionError = new ActionError(
								"errors.date", "Outstanding As On Date");
						errors.add("org.apache.struts.action.GLOBAL_ERROR",
								actionError);
						pplOsdateValue = false;
					}
				} catch (Exception n) {
					ActionError actionError = new ActionError("errors.date",
							"Outstanding As On Date");
					errors.add("org.apache.struts.action.GLOBAL_ERROR",
							actionError);
					pplOsdateValue = false;
				}
			}
			if (finalDisDate != null && !finalDisDate.equals("")) {
				finaldateValue = true;
				try {
					Date finalDisDateValue = sdf.parse(finalDisDate,
							new ParsePosition(0));
					if (finalDisDateValue == null) {
						ActionError actionError = new ActionError(
								"errors.date", "Final Disbursement Date");
						errors.add("org.apache.struts.action.GLOBAL_ERROR",
								actionError);
						finaldateValue = false;
					}
				} catch (Exception n) {
					ActionError actionError = new ActionError("errors.date",
							"Final Disbursement Date");
					errors.add("org.apache.struts.action.GLOBAL_ERROR",
							actionError);
					finaldateValue = false;
				}
			}
			if (firstDBRDateValue != null && !firstDBRDateValue.equals("")) {
				firstdateValue = true;
				try {
					firstDisDate = sdf.parse(firstDBRDateValue,
							new ParsePosition(0));
					if (firstDisDate == null) {
						ActionError actionError = new ActionError(
								"errors.date", "First Disbursement Date");
						errors.add("org.apache.struts.action.GLOBAL_ERROR",
								actionError);
						firstdateValue = false;
					}
				} catch (Exception n) {
					ActionError actionError = new ActionError("errors.date",
							"First Disbursement Date");
					errors.add("org.apache.struts.action.GLOBAL_ERROR",
							actionError);
					firstdateValue = false;
				}
			}
			if (firstInstallmentDueDateValue != null
					&& !firstInstallmentDueDateValue.equals("")) {
				firstInstdateValue = true;
				try {
					Date firstInstallmentDueDate = sdf.parse(
							firstInstallmentDueDateValue, new ParsePosition(0));
					if (firstInstallmentDueDate == null) {
						ActionError actionError = new ActionError(
								"errors.date", "First Instalment Date");
						errors.add("org.apache.struts.action.GLOBAL_ERROR",
								actionError);
						firstInstdateValue = false;
					}
				} catch (NumberFormatException n) {
					ActionError actionError = new ActionError("errors.date",
							"First Instalment Due Date");
					errors.add("org.apache.struts.action.GLOBAL_ERROR",
							actionError);
					firstInstdateValue = false;
				}
			}
			if (GenericValidator.isBlankOrNull(firstDBRDateValue)
					&& GenericValidator.isBlankOrNull(finalDisDate)) {
				ActionError error = new ActionError("firstDBRDateRequired");
				errors.add("org.apache.struts.action.GLOBAL_ERROR", error);
			}
			if (GenericValidator.isBlankOrNull(firstDBRDateValue)
					&& !GenericValidator.isBlankOrNull(finalDisDate)) {
				ActionError error = new ActionError("firstDBRDateRequired");
				errors.add("org.apache.struts.action.GLOBAL_ERROR", error);
			}
			if (firstdateValue && finaldateValue
					&& !GenericValidator.isBlankOrNull(firstDBRDateValue))
				if (GenericValidator.isBlankOrNull(finalDisDate))
					;
			if (firstdateValue
					&& firstInstdateValue
					&& !GenericValidator.isBlankOrNull(firstDBRDateValue)
					&& !GenericValidator
							.isBlankOrNull(firstInstallmentDueDateValue)
					&& !DateHelper.day1BeforeDay2(firstDBRDateValue,
							firstInstallmentDueDateValue)) {
				ActionError actionMessage = new ActionError(
						"fromDateGTfirstInstalmentDueDate");
				errors.add("org.apache.struts.action.GLOBAL_ERROR",
						actionMessage);
			}
			if (firstdateValue && pplOsdateValue
					&& !GenericValidator.isBlankOrNull(pplOsAsOnDateValue)
					&& !GenericValidator.isBlankOrNull(firstDBRDateValue)
					&& firstDisDate.compareTo(pplOsAsOnDate) == 1) {
				ActionError error = new ActionError("dbrDateBeforeOsDate");
				errors.add("org.apache.struts.action.GLOBAL_ERROR", error);
			}
			if (pplOSValue == 0.0D) {
				ActionError error = new ActionError("pplOsrequired");
				errors.add("org.apache.struts.action.GLOBAL_ERROR", error);
			} else if (pplOSValue != 0.0D
					&& GenericValidator.isBlankOrNull(pplOsAsOnDateValue)) {
				ActionError error = new ActionError("pplOsDaterequired");
				errors.add("org.apache.struts.action.GLOBAL_ERROR", error);
			}
			if (firstInstallmentDueDateValue == null
					|| firstInstallmentDueDateValue.equals("")) {
				ActionError error = new ActionError("installmentDateRequired");
				errors.add("org.apache.struts.action.GLOBAL_ERROR", error);
			}
		}
		return errors.isEmpty();
	}

	public static boolean validatePromoterEntry(Object bean,
			ValidatorAction validAction, Field field, ActionErrors errors,
			HttpServletRequest request) {
		String pName = ValidatorUtil
				.getValueAsString(bean, field.getProperty());
		String pNameValue = field.getProperty();
		String pItpan = field.getVarValue("itpan");
		String pItpanValue = ValidatorUtil.getValueAsString(bean, pItpan);
		String pDob = field.getVarValue("dob");
		String pDobValue = ValidatorUtil.getValueAsString(bean, pDob);
		if ((pItpanValue != null && !pItpanValue.equals("") || pDobValue != null
				&& !pDobValue.equals(""))
				&& (pName == null || pName.equals(""))) {
			ActionError error = new ActionError((new StringBuilder())
					.append(pNameValue).append("required").toString());
			errors.add("org.apache.struts.action.GLOBAL_ERROR", error);
		}
		return errors.isEmpty();
	}

	public static boolean validateMcgfBankEntry(Object bean,
			ValidatorAction validAction, Field field, ActionErrors errors,
			HttpServletRequest request) {
		HttpSession session = request.getSession(false);
		if (session.getAttribute("MCGF_FLAG").equals("M")) {
			String pBankName = ValidatorUtil.getValueAsString(bean,
					field.getProperty());
			if (pBankName == null || pBankName.equals("")) {
				ActionError error = new ActionError("participatingBankrequired");
				errors.add("org.apache.struts.action.GLOBAL_ERROR", error);
			}
		}
		return errors.isEmpty();
	}

	public static boolean validateFromToEqualDates(Object bean,
			ValidatorAction validAction, Field field, ActionErrors errors,
			HttpServletRequest request) throws Exception {
		String fromValue = ValidatorUtil.getValueAsString(bean,
				field.getProperty());
		String sProperty2 = field.getVarValue("toDate");
		String toValue = ValidatorUtil.getValueAsString(bean, sProperty2);
		SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
		boolean fromDateValue = false;
		boolean toDateValue = false;
		if (!GenericValidator.isBlankOrNull(fromValue)
				&& !GenericValidator.isBlankOrNull(toValue)) {
			try {
				Date fromDate = sdf.parse(fromValue, new ParsePosition(0));
				if (fromDate == null)
					fromDateValue = false;
				else
					fromDateValue = true;
			} catch (Exception e) {
				fromDateValue = false;
			}
			try {
				Date toDate = sdf.parse(toValue, new ParsePosition(0));
				if (toDate == null)
					toDateValue = false;
				else
					toDateValue = true;
			} catch (Exception e) {
				toDateValue = false;
			}
			if (fromDateValue && toDateValue) {
				Date fromDate = sdf.parse(fromValue, new ParsePosition(0));
				Date toDate = sdf.parse(toValue, new ParsePosition(0));
				if (fromDate.compareTo(toDate) == 1) {
					ActionError error = new ActionError((new StringBuilder())
							.append("fromDate").append(sProperty2).toString());
					errors.add("org.apache.struts.action.GLOBAL_ERROR", error);
				}
			}
		}
		return errors.isEmpty();
	}

	public static boolean checkNpaEntry(Object bean,
			ValidatorAction validAction, Field field, ActionErrors errors,
			HttpServletRequest request) throws Exception {
		String npaValue = ValidatorUtil.getValueAsString(bean,
				field.getProperty());
		if (npaValue.equals("Y")) {
			ActionError error = new ActionError("applicationNoEntry");
			errors.add("org.apache.struts.action.GLOBAL_ERROR", error);
		}
		return errors.isEmpty();
	}

	public static boolean checkIcardEntry(Object bean,
			ValidatorAction validAction, Field field, ActionErrors errors,
			HttpServletRequest request) throws Exception {
		String handiCrafts = request.getParameter("handiCrafts");
		String dcHandicrafts = request.getParameter("dcHandicrafts");
		if (handiCrafts.equals("Y") && dcHandicrafts.equals("Y")) {
			ActionError error = new ActionError("applicationIcardEntry");
			errors.add("org.apache.struts.action.GLOBAL_ERROR", error);
		}
		return errors.isEmpty();
	}

	public static boolean checkInstallmentEntry(Object bean,
			ValidatorAction validAction, Field field, ActionErrors errors,
			HttpServletRequest request) throws Exception {
		Integer installmentVal = new Integer(ValidatorUtil.getValueAsString(
				bean, field.getProperty()));
		int installmentValue = installmentVal.intValue();
		HttpSession session = request.getSession(false);
		if (session.getAttribute("APPLICATION_TYPE_FLAG") != null
				&& (session.getAttribute("APPLICATION_TYPE_FLAG").equals("8")
						|| session.getAttribute("APPLICATION_TYPE_FLAG")
								.equals("7")
						|| session.getAttribute("APPLICATION_TYPE_FLAG")
								.equals("0") || (session.getAttribute(
						"APPLICATION_TYPE_FLAG").equals("14") || session
						.getAttribute("APPLICATION_TYPE_FLAG").equals("19"))
						&& (session.getAttribute("APPLICATION_LOAN_TYPE")
								.equals("TC")
								|| session
										.getAttribute("APPLICATION_LOAN_TYPE")
										.equals("CC") || session.getAttribute(
								"APPLICATION_LOAN_TYPE").equals("BO")))
				&& installmentValue == 0) {
			ActionError actionError = new ActionError(
					"noOfInstallmentsrequired");
			errors.add("org.apache.struts.action.GLOBAL_ERROR", actionError);
		}
		return errors.isEmpty();
	}

	public static boolean checkEnhancedAmount(Object bean,
			ValidatorAction validAction, Field field, ActionErrors errors,
			HttpServletRequest request) throws Exception {
		String property = field.getProperty();
		Double existingVal = new Double(ValidatorUtil.getValueAsString(bean,
				field.getProperty()));
		// double existingAmount = existingVal.doubleValue();
		double existingAmount = 0.0;
		if (existingVal != null) {
			existingAmount = existingVal.doubleValue();
		}
		String wcFundBasedVal = field.getVarValue("wcFundBasedSanctioned");
		Double wcFundBasedValue = new Double(ValidatorUtil.getValueAsString(
				bean, wcFundBasedVal));
		// double wcFundBased = wcFundBasedValue.doubleValue();
		double wcFundBased = 0.0;
		if (wcFundBasedValue != null) {
			wcFundBased = wcFundBasedValue.doubleValue();
		}
		String wcNonFundBasedVal = field
				.getVarValue("wcNonFundBasedSanctioned");
		Double wcNonFundBasedValue = new Double(ValidatorUtil.getValueAsString(
				bean, wcNonFundBasedVal));
		// double wcNonFundBased = wcNonFundBasedValue.doubleValue();
		double wcNonFundBased = 0.0;
		if (wcNonFundBasedValue != null) {
			wcNonFundBased = wcNonFundBasedValue.doubleValue();
		}
		String existingNFBVal = field.getVarValue("existingNonFundBasedTotal");
		Double existingNFBValue = new Double(ValidatorUtil.getValueAsString(
				bean, existingNFBVal));
		// double existingNFB = existingNFBValue.doubleValue();
		double existingNFB = 0.0;
		if (existingNFBValue != null) {
			existingNFB = existingNFBValue.doubleValue();
		}
		Log.log(4, "Validator", "checkEnhancedAmount", (new StringBuilder())
				.append("wcFundBased ;").append(wcFundBased).toString());
		Log.log(4, "Validator", "checkEnhancedAmount", (new StringBuilder())
				.append("existingAmount ;").append(existingAmount).toString());
		Log.log(4, "Validator", "checkEnhancedAmount", (new StringBuilder())
				.append("wcNonFundBased ;").append(wcNonFundBased).toString());
		Log.log(4, "Validator", "checkEnhancedAmount", (new StringBuilder())
				.append("existingNFB ;").append(existingNFB).toString());
		Log.log(4, "Validator", "checkEnhancedAmount", (new StringBuilder())
				.append("sum FB ;").append(wcFundBased + wcNonFundBased)
				.toString());
		Log.log(4, "Validator", "checkEnhancedAmount", (new StringBuilder())
				.append("sum NFB;").append(existingAmount + existingNFB)
				.toString());
		if (existingAmount < wcFundBased) {
			ActionError actionError = new ActionError((new StringBuilder())
					.append("enhancedAmountGreater").append(property)
					.toString());
			errors.add("org.apache.struts.action.GLOBAL_ERROR", actionError);
		}
		if (wcNonFundBased < existingNFB) {
			ActionError actionError = new ActionError(
					"enhancedAmountGreaterwcNonFundBasedSanctioned");
			errors.add("org.apache.struts.action.GLOBAL_ERROR", actionError);
		}
		if (existingAmount + wcNonFundBased <= wcFundBased + existingNFB) {
			ActionError actionError = new ActionError("enhancedSumGreater");
			errors.add("org.apache.struts.action.GLOBAL_ERROR", actionError);
		}
		return errors.isEmpty();
	}

	public static boolean validateAmtSanctioned(Object bean,
			ValidatorAction validAction, Field field, ActionErrors errors,
			HttpServletRequest request) throws Exception {
		String amtSanctioned = ValidatorUtil.getValueAsString(bean,
				field.getProperty());
		String pplOsAsOnDateVal = field.getVarValue("pplOsAsOnDate");
		String pplOsAsOnDateValue = ValidatorUtil.getValueAsString(bean,
				pplOsAsOnDateVal);
		String firstInstallmentDueDateVal = field
				.getVarValue("firstInstallmentDueDate");
		String firstInstallmentDueDateValue = ValidatorUtil.getValueAsString(
				bean, firstInstallmentDueDateVal);
		String finalDisDateVal = field.getVarValue("finalDate");
		String finalDisDate = ValidatorUtil.getValueAsString(bean,
				finalDisDateVal);
		String firstDisDateVal = field.getVarValue("firstDisbursementDate");
		String firstDBRDateValue = ValidatorUtil.getValueAsString(bean,
				firstDisDateVal);
		HttpSession session = request.getSession(false);
		if (session.getAttribute("APPLICATION_TYPE_FLAG").equals("7")
				|| session.getAttribute("APPLICATION_TYPE_FLAG").equals("0")
				|| session.getAttribute("APPLICATION_TYPE_FLAG").equals("8")
				|| session.getAttribute("APPLICATION_TYPE_FLAG").equals("10")
				|| (session.getAttribute("APPLICATION_TYPE_FLAG").equals("14") || session
						.getAttribute("APPLICATION_TYPE_FLAG").equals("19"))
				&& (session.getAttribute("APPLICATION_LOAN_TYPE").equals("TC")
						|| session.getAttribute("APPLICATION_LOAN_TYPE")
								.equals("CC") || session.getAttribute(
						"APPLICATION_LOAN_TYPE").equals("BO"))) {
			SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
			boolean amtSanctionedValue = false;
			boolean finaldateValue = false;
			boolean firstdateValue = false;
			boolean firstInstdateValue = false;
			boolean pplOsdateValue = false;
			if (amtSanctioned != null && !amtSanctioned.equals("")) {
				amtSanctionedValue = true;
				try {
					Date amtSanctionedDate = sdf.parse(amtSanctioned,
							new ParsePosition(0));
					if (amtSanctionedDate == null) {
						ActionError actionError = new ActionError(
								"errors.date", "Date of Sanction");
						errors.add("org.apache.struts.action.GLOBAL_ERROR",
								actionError);
						amtSanctionedValue = false;
					} else {
						amtSanctionedValue = true;
						Date currentDate = new Date();
						amtSanctionedDate = sdf.parse(amtSanctioned,
								new ParsePosition(0));
						try {
							String stringDate = sdf.format(currentDate);
							if (amtSanctionedDate.compareTo(currentDate) == 1) {
								ActionError actionError = new ActionError(
										"currentDateamountSanctionedDate");
								errors.add(
										"org.apache.struts.action.GLOBAL_ERROR",
										actionError);
							}
						} catch (NumberFormatException numberFormatException) {
							ActionError actionError = new ActionError(
									"Date of Sanction is not a valid Date");
							errors.add("org.apache.struts.action.GLOBAL_ERROR",
									actionError);
						}
					}
				} catch (Exception n) {
					ActionError actionError = new ActionError("errors.date",
							"Date of Sanction");
					errors.add("org.apache.struts.action.GLOBAL_ERROR",
							actionError);
					amtSanctionedValue = false;
				}
			}
			if (pplOsAsOnDateValue != null && !pplOsAsOnDateValue.equals("")) {
				pplOsdateValue = true;
				try {
					Date pplOsAsOnDate = sdf.parse(pplOsAsOnDateValue,
							new ParsePosition(0));
					if (pplOsAsOnDate == null)
						pplOsdateValue = false;
					else
						pplOsdateValue = true;
				} catch (Exception n) {
					pplOsdateValue = false;
				}
			}
			if (finalDisDate != null && !finalDisDate.equals("")) {
				finaldateValue = true;
				try {
					Date finalDisDateValue = sdf.parse(finalDisDate,
							new ParsePosition(0));
					if (finalDisDateValue == null)
						finaldateValue = false;
					else
						finaldateValue = true;
				} catch (Exception n) {
					finaldateValue = false;
				}
			}
			if (firstDBRDateValue != null && !firstDBRDateValue.equals("")) {
				firstdateValue = true;
				try {
					Date firstDisDate = sdf.parse(firstDBRDateValue,
							new ParsePosition(0));
					if (firstDisDate == null)
						firstdateValue = false;
					else
						firstdateValue = true;
				} catch (Exception n) {
					firstdateValue = false;
				}
			}
			if (firstInstallmentDueDateValue != null
					&& !firstInstallmentDueDateValue.equals("")) {
				firstInstdateValue = true;
				try {
					Date firstInstallmentDueDate = sdf.parse(
							firstInstallmentDueDateValue, new ParsePosition(0));
					if (firstInstallmentDueDate == null)
						firstInstdateValue = false;
					else
						firstInstdateValue = true;
				} catch (NumberFormatException n) {
					firstInstdateValue = false;
				}
			}
			if (amtSanctionedValue) {
				Date amtSanctionedDate = sdf.parse(amtSanctioned,
						new ParsePosition(0));
				if (firstdateValue) {
					Date firstDBRDate = sdf.parse(firstDBRDateValue,
							new ParsePosition(0));
					if (amtSanctionedDate.compareTo(firstDBRDate) == 1) {
						ActionError error = new ActionError(
								"fromDatefirstDisbursementDate");
						errors.add("org.apache.struts.action.GLOBAL_ERROR",
								error);
					}
				}
				if (pplOsdateValue) {
					Date pplOsAsOnDate = sdf.parse(pplOsAsOnDateValue,
							new ParsePosition(0));
					if (amtSanctionedDate.compareTo(pplOsAsOnDate) == 1) {
						ActionError error = new ActionError(
								"fromDatepplOsAsOnDate");
						errors.add("org.apache.struts.action.GLOBAL_ERROR",
								error);
					}
				}
				if (finaldateValue) {
					Date finalDisDateValue = sdf.parse(finalDisDate,
							new ParsePosition(0));
					if (amtSanctionedDate.compareTo(finalDisDateValue) == 1) {
						ActionError error = new ActionError(
								"fromDatefinalDisbursementDate");
						errors.add("org.apache.struts.action.GLOBAL_ERROR",
								error);
					}
				}
				if (firstInstdateValue
						&& !DateHelper.day1BeforeDay2(amtSanctioned,
								firstInstallmentDueDateValue)) {
					ActionError actionMessage = new ActionError(
							"fromDateGTfirstInstallmentDueDate");
					errors.add("org.apache.struts.action.GLOBAL_ERROR",
							actionMessage);
				}
			}
		}
		return errors.isEmpty();
	}

	public static boolean checkTcConvStatus(Object bean,
			ValidatorAction validAction, Field field, ActionErrors errors,
			HttpServletRequest request) {
		APForm apForm = (APForm) bean;
		Map tcStatus = apForm.getTcDecision();
		Set tcStatusSet = tcStatus.keySet();
		Iterator tcStatusIterator = tcStatusSet.iterator();
		Map tcCgpan = apForm.getTcCgpan();
		Set tcCgpanSet = tcCgpan.keySet();
		Iterator tcCgpanIterator = tcCgpanSet.iterator();
		boolean clearVal = false;
		if (tcStatus != null && tcStatus.size() != 0)
			while (tcStatusIterator.hasNext()) {
				String key = (String) tcStatusIterator.next();
				if (request.getParameter((new StringBuilder())
						.append("tcDecision(").append(key).append(")")
						.toString()) != null
						&& tcStatus.get(key) != null
						&& !tcStatus.get(key).equals("")) {
					clearVal = true;
					break;
				}
			}
		if (!clearVal) {
			ActionError actionError = new ActionError("statusRequired");
			errors.add("org.apache.struts.action.GLOBAL_ERROR", actionError);
		}
		return errors.isEmpty();
	}

	public static boolean checkWcConvStatus(Object bean,
			ValidatorAction validAction, Field field, ActionErrors errors,
			HttpServletRequest request) {
		APForm apForm = (APForm) bean;
		Map wcStatus = apForm.getWcDecision();
		Set wcStatusSet = wcStatus.keySet();
		Iterator wcStatusIterator = wcStatusSet.iterator();
		Map wcCgpan = apForm.getWcCgpan();
		Set wcCgpanSet = wcCgpan.keySet();
		Iterator wcCgpanIterator = wcCgpanSet.iterator();
		boolean clearVal = false;
		if (wcStatus != null && wcStatus.size() != 0)
			while (wcStatusIterator.hasNext()) {
				String key = (String) wcStatusIterator.next();
				if (!wcStatus.get(key).equals("")) {
					clearVal = true;
					break;
				}
			}
		if (!clearVal) {
			ActionError actionError = new ActionError("statusRequired");
			errors.add("org.apache.struts.action.GLOBAL_ERROR", actionError);
		}
		return errors.isEmpty();
	}

	public static boolean checkTcCgpanEntry(Object bean,
			ValidatorAction validAction, Field field, ActionErrors errors,
			HttpServletRequest request) {
		APForm apForm = (APForm) bean;
		Map tcStatus = apForm.getTcDecision();
		Set tcStatusSet = tcStatus.keySet();
		Iterator tcStatusIterator = tcStatusSet.iterator();
		Map tcCgpan = apForm.getTcCgpan();
		Set tcCgpanSet = tcCgpan.keySet();
		Iterator tcCgpanIterator = tcCgpanSet.iterator();
		boolean clearVal = false;
		if (tcStatus != null && tcStatus.size() != 0)
			while (tcStatusIterator.hasNext()) {
				String key = (String) tcStatusIterator.next();
				if (request.getParameter((new StringBuilder())
						.append("tcDecision(").append(key).append(")")
						.toString()) != null
						&& tcStatus.get(key) != null
						&& tcStatus.get(key).equals("ATL")
						&& (tcCgpan.get(key) == null || tcCgpan.get(key)
								.equals(""))) {
					boolean remarksVal = false;
					for (Iterator errorsIterator = errors.get(); errorsIterator
							.hasNext();) {
						ActionError error = (ActionError) errorsIterator.next();
						if (error.getKey().equals("cgpanRequired")) {
							remarksVal = true;
							break;
						}
					}

					if (!remarksVal) {
						ActionError actionError = new ActionError(
								"cgpanRequired");
						errors.add("org.apache.struts.action.GLOBAL_ERROR",
								actionError);
					}
				}
			}
		return errors.isEmpty();
	}

	public static boolean checkWcCgpanEntry(Object bean,
			ValidatorAction validAction, Field field, ActionErrors errors,
			HttpServletRequest request) {
		APForm apForm = (APForm) bean;
		Map wcStatus = apForm.getWcDecision();
		Set wcStatusSet = wcStatus.keySet();
		Iterator wcStatusIterator = wcStatusSet.iterator();
		Map wcCgpan = apForm.getWcCgpan();
		Set wcCgpanSet = wcCgpan.keySet();
		Iterator wcCgpanIterator = wcCgpanSet.iterator();
		boolean clearVal = false;
		if (wcStatus != null && wcStatus.size() != 0)
			while (wcStatusIterator.hasNext()) {
				String key = (String) wcStatusIterator.next();
				if ((wcStatus.get(key).equals("WCE") || wcStatus.get(key)
						.equals("WCR"))
						&& (wcCgpan.get(key) == null || wcCgpan.get(key)
								.equals(""))) {
					boolean remarksVal = false;
					for (Iterator errorsIterator = errors.get(); errorsIterator
							.hasNext();) {
						ActionError error = (ActionError) errorsIterator.next();
						if (error.getKey().equals("cgpanReqd")) {
							remarksVal = true;
							break;
						}
					}

					if (!remarksVal) {
						ActionError actionError = new ActionError("cgpanReqd");
						errors.add("org.apache.struts.action.GLOBAL_ERROR",
								actionError);
					}
				}
			}
		return errors.isEmpty();
	}

	public static boolean checkAmtDisbursedGreater(Object bean,
			ValidatorAction validAction, Field field, ActionErrors errors,
			HttpServletRequest request) {
		Double creditVal = new Double(ValidatorUtil.getValueAsString(bean,
				field.getProperty()));
		String creditString = field.getProperty();
		double creditValue = creditVal.doubleValue();
		String termCreditSanctionedVal = field
				.getVarValue("termCreditSanctioned");
		Double termCreditSanctioned = new Double(
				ValidatorUtil.getValueAsString(bean, termCreditSanctionedVal));
		double termCreditSanctionedValue = termCreditSanctioned.doubleValue();
		HttpSession session = request.getSession(false);
		if ((session.getAttribute("APPLICATION_TYPE_FLAG").equals("7")
				|| session.getAttribute("APPLICATION_TYPE_FLAG").equals("0")
				|| session.getAttribute("APPLICATION_TYPE_FLAG").equals("8")
				|| session.getAttribute("APPLICATION_TYPE_FLAG").equals("10") || (session
				.getAttribute("APPLICATION_TYPE_FLAG").equals("14") || session
				.getAttribute("APPLICATION_TYPE_FLAG").equals("19"))
				&& (session.getAttribute("APPLICATION_LOAN_TYPE").equals("TC")
						|| session.getAttribute("APPLICATION_LOAN_TYPE")
								.equals("CC") || session.getAttribute(
						"APPLICATION_LOAN_TYPE").equals("BO")))
				&& creditValue != 0.0D
				&& termCreditSanctionedValue != 0.0D
				&& creditValue > termCreditSanctionedValue) {
			ActionError error = new ActionError((new StringBuilder())
					.append("amtDisbursedLess").append(creditString).toString());
			errors.add("org.apache.struts.action.GLOBAL_ERROR", error);
		}
		return errors.isEmpty();
	}

	public static boolean checkCoveredValueOnView(Object bean,
			ValidatorAction validAction, Field field, ActionErrors errors,
			HttpServletRequest request) throws DatabaseException {
		String cgtsiCoveredValue = ValidatorUtil.getValueAsString(bean,
				field.getProperty());
		String typeValue = field.getVarValue("noneValue");
		String typeVal = ValidatorUtil.getValueAsString(bean, typeValue);
		String unitTypeVal = field.getVarValue("unit");
		String unittypeValue = ValidatorUtil
				.getValueAsString(bean, unitTypeVal);
		HttpSession session = request.getSession(false);
		if (cgtsiCoveredValue.equals("Y")
				&& (session.getAttribute("APPLICATION_TYPE_FLAG").equals("7")
						|| session.getAttribute("APPLICATION_TYPE_FLAG")
								.equals("8")
						|| session.getAttribute("APPLICATION_TYPE_FLAG")
								.equals("9") || session.getAttribute(
						"APPLICATION_TYPE_FLAG").equals("10"))) {
			if (!typeVal.equals("cgpan") && !typeVal.equals("cgbid")) {
				ActionError error = new ActionError("cgpanCgbidSelected");
				errors.add("org.apache.struts.action.GLOBAL_ERROR", error);
			} else if (typeVal.equals("cgpan") && unittypeValue == null
					&& unittypeValue.equals("")) {
				ActionError error = new ActionError("cgpanRequired");
				errors.add("org.apache.struts.action.GLOBAL_ERROR", error);
			} else if (typeVal.equals("cgbid") && unittypeValue == null
					&& unittypeValue.equals("")) {
				ActionError error = new ActionError("cgbidRequired");
				errors.add("org.apache.struts.action.GLOBAL_ERROR", error);
			}
		}
		return errors.isEmpty();
	}

	public static boolean validateSubSchemeParameters(Object bean,
			ValidatorAction validAction, Field field, ActionErrors errors,
			HttpServletRequest request) {
		Log.log(4, "Validator", "validateSubSchemeParameters", "Entered");
		String mliProperty = field.getVarValue("mli");
		String industryProperty = field.getVarValue("industry");
		String genderProperty = field.getVarValue("gender");
		String socialCategoryProperty = field.getVarValue("socialCategory");
		String stateValue = ValidatorUtil.getValueAsString(bean,
				field.getProperty());
		Log.log(4, "Validator", "validateSubSchemeParameters",
				(new StringBuilder()).append("state -- ").append(stateValue)
						.toString());
		String mliValue = ValidatorUtil.getValueAsString(bean, mliProperty);
		Log.log(4, "Validator", "validateSubSchemeParameters",
				(new StringBuilder()).append("mli -- ").append(mliValue)
						.toString());
		String industryValue = ValidatorUtil.getValueAsString(bean,
				industryProperty);
		Log.log(4,
				"Validator",
				"validateSubSchemeParameters",
				(new StringBuilder()).append("industry -- ")
						.append(industryValue).toString());
		String genderValue = ValidatorUtil.getValueAsString(bean,
				genderProperty);
		Log.log(4, "Validator", "validateSubSchemeParameters",
				(new StringBuilder()).append("gender -- ").append(genderValue)
						.toString());
		String socialCategoryValue = ValidatorUtil.getValueAsString(bean,
				socialCategoryProperty);
		Log.log(4,
				"Validator",
				"validateSubSchemeParameters",
				(new StringBuilder()).append("social category -- ")
						.append(socialCategoryValue).toString());
		if (GenericValidator.isBlankOrNull(stateValue)
				&& GenericValidator.isBlankOrNull(mliValue)
				&& GenericValidator.isBlankOrNull(industryValue)
				&& GenericValidator.isBlankOrNull(genderValue)
				&& GenericValidator.isBlankOrNull(socialCategoryValue)) {
			Log.log(4, "Validator", "validateSubSchemeParameters",
					"adding error message");
			ActionError actionMessage = new ActionError("subSchemeErrorMessage");
			errors.add("org.apache.struts.action.GLOBAL_ERROR", actionMessage);
		}
		Log.log(4, "Validator", "validateSubSchemeParameters", "Exited");
		return errors.isEmpty();
	}

	public static boolean validateCgpanOrSSi(Object bean,
			ValidatorAction validAction, Field field, ActionErrors errors,
			HttpServletRequest request) {
		DynaActionForm dynaForm = (DynaActionForm) bean;
		String cgpan = (String) dynaForm.get("enterCgpan");
		String ssi = (String) dynaForm.get("enterSSI");
		if ((ssi == null || ssi.equals(""))
				&& (cgpan == null || cgpan.equals(""))) {
			ActionError actionError = new ActionError("enterOne");
			errors.add("org.apache.struts.action.GLOBAL_ERROR", actionError);
		}
		return errors.isEmpty();
	}

	
	 public static boolean checkLegalForumName(Object bean, ValidatorAction validAction, Field field, ActionErrors errors, HttpServletRequest request)
     throws Exception
 {
     Log.log(4, "Validator", "checkLegalForumName", "Entered");
     ClaimActionForm claimForm = (ClaimActionForm)bean;
     String headedByOfficer = claimForm.getCommHeadedByOfficerOrAbove();
     if(headedByOfficer != null)
     {
         if(headedByOfficer.equals("Y"))
             return errors.isEmpty();
         String legalForumName = claimForm.getForumthrulegalinitiated();
         Log.log(4, "Validator", "checkLegalForumName", (new StringBuilder()).append("legalForumName :").append(legalForumName).toString());
         String legalForumNameOther = claimForm.getOtherforums();
         Log.log(4, "Validator", "checkLegalForumName", (new StringBuilder()).append("legalForumNameOther :").append(legalForumNameOther).toString());
         if(!GenericValidator.isBlankOrNull(legalForumName))
         {
             if("Others".equals(legalForumName) && GenericValidator.isBlankOrNull(legalForumNameOther))
             {
                 ActionError error = new ActionError("legalForumRequired");
                 errors.add("org.apache.struts.action.GLOBAL_ERROR", error);
             }
         } else
         {
             ActionError error = new ActionError("legalForumRequired");
             errors.add("org.apache.struts.action.GLOBAL_ERROR", error);
         }
         String caseregnumber = claimForm.getCaseregnumber();
         String filingDate = claimForm.getLegaldate();
         String location = claimForm.getLocation();
         if(location == null || location.equals(""))
         {
             Log.log(4, "Validator", "validateClaimStatus", "Forum Name is not selected.");
             ActionError error = new ActionError("locationReq");
             errors.add("org.apache.struts.action.GLOBAL_ERROR", error);
         }
         FormFile legalAttachmentPath = claimForm.getLegalAttachmentPath();
         if(legalAttachmentPath.getFileName().equals(""))
         {
             Log.log(4, "Validator", "validateClaimStatus", "Forum Name is not selected.");
             ActionError error = new ActionError("legalAttchPath");
             errors.add("org.apache.struts.action.GLOBAL_ERROR", error);
         }
         String amountclaimed = claimForm.getAmountclaimed();
         if(amountclaimed == null || amountclaimed.equals("0.0"))
         {
             Log.log(4, "Validator", "validateClaimStatus", "Forum Name is not selected.");
             ActionError error = new ActionError("amountClaimedReq");
             errors.add("org.apache.struts.action.GLOBAL_ERROR", error);
         }
         String assetPossessionDate = claimForm.getAssetPossessionDate();
         if(assetPossessionDate == null || assetPossessionDate.equals(""))
         {
             Log.log(4, "Validator", "validateClaimStatus", "Forum Name is not selected.");
             ActionError error = new ActionError("assetPossessionDateReq");
             errors.add("org.apache.struts.action.GLOBAL_ERROR", error);
         }
         String legaldate = claimForm.getLegaldate();
         if(legaldate == null || legaldate.equals(""))
         {
             Log.log(4, "Validator", "validateClaimStatus", "Forum Name is not selected.");
             ActionError error = new ActionError("LegalDt");
             errors.add("org.apache.struts.action.GLOBAL_ERROR", error);
         }
         String reasonForFilingSuit = claimForm.getReasonForFilingSuit();
         Date currentDate = new Date();
         SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
         String stringDate = dateFormat.format(currentDate);
         //if(legaldate.compareTo(stringDate) > 0)
         
         Date stringDate1 = null;
         Date legaldate1 = null;
         
         SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
         try
         {
        	 //kot
        	 stringDate1 = sdf.parse(stringDate);
             legaldate1 = sdf.parse(legaldate);
             if((legaldate1.compareTo(stringDate1)) > 0) 
             {
                 ActionError actionError = new ActionError("npaDateNotAfterTodaysDat");
                 errors.add("org.apache.struts.action.GLOBAL_ERROR", actionError);
             }
         }
         catch(NumberFormatException numberFormatException)
         {
             ActionError actionError = new ActionError("errors.date");
             errors.add("org.apache.struts.action.GLOBAL_ERROR", actionError);
         }
         
        	/* if(stringDate.compareTo(legaldate) > 0)
         {
             ActionError actionError = new ActionError("npaDateNotAfterTodaysDat");
             errors.add("org.apache.struts.action.GLOBAL_ERROR", actionError);
         }*/
         HashMap npaDetails = claimForm.getNpaDetails();
         String npaDtStr = (String)npaDetails.get("NPAClassifiedDate");
         Date npaDt = null;
         Date legalDt = null;
         SimpleDateFormat sdf3 = new SimpleDateFormat("dd/MM/yyyy");
         try
         {
             npaDt = sdf3.parse(npaDtStr);
             legalDt = sdf3.parse(legaldate);
             if(npaDt.compareTo(legalDt) > 0 && GenericValidator.isBlankOrNull(reasonForFilingSuit))
             {
                 ActionError actionError = new ActionError("reasonForFilingSuit");
                 errors.add("org.apache.struts.action.GLOBAL_ERROR", actionError);
             }
         }
         catch(NumberFormatException numberFormatException)
         {
             ActionError actionError = new ActionError("errors.date");
             errors.add("org.apache.struts.action.GLOBAL_ERROR", actionError);
         }
         String forumName1 = claimForm.getForumthrulegalinitiated();
         String assetDt1 = claimForm.getAssetPossessionDate();
         String assetDtStr1 = ValidatorUtil.getValueAsString(bean, "assetPossessionDate");
         SimpleDateFormat sdf1 = new SimpleDateFormat("dd/MM/yyyy");
         Date dt = sdf.parse(assetDt1);
         HashMap npaDetails1 = claimForm.getNpaDetails();
         String npaDtStr1 = (String)npaDetails.get("NPAClassifiedDate");
         Date npaDt1 = null;
         Date legalDt1 = null;
         try
         {
             if("Securitisation Act, 2002".equals(forumName1))
             {
                 if(!GenericValidator.isBlankOrNull(npaDtStr))
                     npaDt = sdf.parse(npaDtStr);
                 if(!GenericValidator.isBlankOrNull(assetDtStr1))
                 {
                     legalDt = sdf.parse(assetDtStr1);
                 } else
                 {
                     ActionError actionError = new ActionError("assetPossessionDateRequired");
                     errors.add("org.apache.struts.action.GLOBAL_ERROR", actionError);
                 }
                 if(!GenericValidator.isBlankOrNull(assetDtStr1) && npaDt.compareTo(dt) > 0)
                 {
                     ActionError actionError = new ActionError("assetPossessionDate");
                     errors.add("org.apache.struts.action.GLOBAL_ERROR", actionError);
                 }
             }
         }
         catch(NumberFormatException numberFormatException)
         {
             ActionError actionError = new ActionError("errors.date");
             errors.add("org.apache.struts.action.GLOBAL_ERROR", actionError);
         }
     } else
     {
         String legalForumName = claimForm.getForumthrulegalinitiated();
         Log.log(4, "Validator", "checkLegalForumName", (new StringBuilder()).append("legalForumName :").append(legalForumName).toString());
         String legalForumNameOther = claimForm.getOtherforums();
         Log.log(4, "Validator", "checkLegalForumName", (new StringBuilder()).append("legalForumNameOther :").append(legalForumNameOther).toString());
         if(!GenericValidator.isBlankOrNull(legalForumName))
         {
             if("Others".equals(legalForumName) && GenericValidator.isBlankOrNull(legalForumNameOther))
             {
                 ActionError error = new ActionError("legalForumRequired");
                 errors.add("org.apache.struts.action.GLOBAL_ERROR", error);
             }
         } else
         {
             ActionError error = new ActionError("legalForumRequired");
             errors.add("org.apache.struts.action.GLOBAL_ERROR", error);
         }
         
         //kot
         
         /*try
         {
             if("Securitisation Act, 2002".equals(legalForumName))
             {
                 
                     ActionError actionError = new ActionError("assetPossessionDateRequired");
                     errors.add("org.apache.struts.action.GLOBAL_ERROR", actionError);
                 }
             
                         
               
         }
         catch(NumberFormatException numberFormatException)
         {
             ActionError actionError = new ActionError("errors.date");
             errors.add("org.apache.struts.action.GLOBAL_ERROR", actionError);
         }
         */
         
         
         
         
         
         
         
         
         
         
         
         String caseregnumber = claimForm.getCaseregnumber();
         String filingDate = claimForm.getLegaldate();
         String location = claimForm.getLocation();
         if(location == null || location.equals(""))
         {
             Log.log(4, "Validator", "validateClaimStatus", "Forum Name is not selected.");
             ActionError error = new ActionError("locationReq");
             errors.add("org.apache.struts.action.GLOBAL_ERROR", error);
         }
         FormFile legalAttachmentPath = claimForm.getLegalAttachmentPath();
         if(legalAttachmentPath.getFileName().equals(""))
         {
             Log.log(4, "Validator", "validateClaimStatus", "Forum Name is not selected.");
             ActionError error = new ActionError("legalAttchPath");
             errors.add("org.apache.struts.action.GLOBAL_ERROR", error);
         }
         String amountclaimed = claimForm.getAmountclaimed();
         if(amountclaimed == null || amountclaimed.equals("0.0"))
         {
             Log.log(4, "Validator", "validateClaimStatus", "Forum Name is not selected.");
             ActionError error = new ActionError("amountClaimedReq");
             errors.add("org.apache.struts.action.GLOBAL_ERROR", error);
         }
         
         
         
         String assetPossessionDate = claimForm.getAssetPossessionDate();
         
         if("Securitisation Act, 2002".equals(legalForumName))
         {
         
         if(assetPossessionDate == null || assetPossessionDate.equals(""))
         {
             Log.log(4, "Validator", "validateClaimStatus", "Forum Name is not selected.");
             ActionError error = new ActionError("assetPossessionDateReq");
             errors.add("org.apache.struts.action.GLOBAL_ERROR", error);
         }
         
         }
         String legaldate = claimForm.getLegaldate();
         if(legaldate == null || legaldate.equals(""))
         {
             Log.log(4, "Validator", "validateClaimStatus", "Forum Name is not selected.");
             ActionError error = new ActionError("LegalDt");
             errors.add("org.apache.struts.action.GLOBAL_ERROR", error);
         }
         String reasonForFilingSuit = claimForm.getReasonForFilingSuit();
         Date currentDate = new Date();
         SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
         String stringDate = dateFormat.format(currentDate);
         
         
         Date stringDate1 = null;
         Date legaldate1 = null;
         
         SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
         try
         {
        	 //kot
        	 stringDate1 = sdf.parse(stringDate);
             legaldate1 = sdf.parse(legaldate);
           //  if((stringDate1.compareTo(legaldate1)) > 0) 
            // {
              //   ActionError actionError = new ActionError("npaDateNotAfterTodaysDat");
              //   errors.add("org.apache.struts.action.GLOBAL_ERROR", actionError);
           //  }
             
             if((legaldate1.compareTo(stringDate1)) > 0) 
             {
                 ActionError actionError = new ActionError("npaDateNotAfterTodaysDat");
                 errors.add("org.apache.struts.action.GLOBAL_ERROR", actionError);
             }
             
         }
         catch(NumberFormatException numberFormatException)
         {
             ActionError actionError = new ActionError("errors.date");
             errors.add("org.apache.struts.action.GLOBAL_ERROR", actionError);
         }
         
         
         //if(legaldate.compareTo(stringDate) > 0)
        // if((stringDate.compareTo(legaldate)) > 0) 
         
        /* if((stringDate.compareTo(legaldate)) > 0) 
         {
             ActionError actionError = new ActionError("npaDateNotAfterTodaysDat");
             errors.add("org.apache.struts.action.GLOBAL_ERROR", actionError);
         }
         
         if((legaldate.compareTo(stringDate)) > 0) 
         {
             ActionError actionError = new ActionError("npaDateNotAfterTodaysDat");
             errors.add("org.apache.struts.action.GLOBAL_ERROR", actionError);
         }*/
         HashMap npaDetails = claimForm.getNpaDetails();
         String npaDtStr = (String)npaDetails.get("NPAClassifiedDate");
         Date npaDt = null;
         Date legalDt = null;
         SimpleDateFormat sdf2 = new SimpleDateFormat("dd/MM/yyyy");
         try
         {
             npaDt = sdf2.parse(npaDtStr);
             legalDt = sdf2.parse(legaldate);
             if(npaDt.compareTo(legalDt) > 0 && GenericValidator.isBlankOrNull(reasonForFilingSuit))
             {
                 ActionError actionError = new ActionError("reasonForFilingSuit");
                 errors.add("org.apache.struts.action.GLOBAL_ERROR", actionError);
             }
         }
         catch(NumberFormatException numberFormatException)
         {
             ActionError actionError = new ActionError("errors.date");
             errors.add("org.apache.struts.action.GLOBAL_ERROR", actionError);
         }
       /*  String forumName1 = claimForm.getForumthrulegalinitiated();
         String assetDt1 = claimForm.getAssetPossessionDate();
         String assetDtStr1 = ValidatorUtil.getValueAsString(bean, "assetPossessionDate");
         SimpleDateFormat sdf1 = new SimpleDateFormat("dd/MM/yyyy");
         Date dt = sdf1.parse(assetDt1);
         HashMap npaDetails1 = claimForm.getNpaDetails();
         String npaDtStr1 = (String)npaDetails.get("NPAClassifiedDate");
         Date npaDt1 = null;
         Date legalDt1 = null;
         try
         {
             if("Securitisation Act, 2002".equals(forumName1))
             {
                 if(!GenericValidator.isBlankOrNull(npaDtStr1))
                     npaDt1 = sdf1.parse(npaDtStr);
                 if(!GenericValidator.isBlankOrNull(assetDtStr1))
                 {
                     legalDt1 = sdf1.parse(assetDtStr1);
                 } else
                 {
                     ActionError actionError = new ActionError("assetPossessionDateRequired");
                     errors.add("org.apache.struts.action.GLOBAL_ERROR", actionError);
                 }
                 if(!GenericValidator.isBlankOrNull(assetDtStr1) && npaDt1.compareTo(dt) > 0)
                 {
                     ActionError actionError = new ActionError("assetPossessionDate");
                     errors.add("org.apache.struts.action.GLOBAL_ERROR", actionError);
                 }
             }
         }
         catch(NumberFormatException numberFormatException)
         {
             ActionError actionError = new ActionError("errors.date");
             errors.add("org.apache.struts.action.GLOBAL_ERROR", actionError);
         }*/
     }
     Log.log(4, "Validator", "checkLegalForumName", "Exited");
     return errors.isEmpty();
 }

	
	
	
	
	
	public static boolean checkLegalForumNameold(Object bean,
			ValidatorAction validAction, Field field, ActionErrors errors,
			HttpServletRequest request) {
		Log.log(4, "Validator", "checkLegalForumName", "Entered");
		ClaimActionForm claimForm = (ClaimActionForm) bean;
		String legalForumName = claimForm.getForumthrulegalinitiated();
		Log.log(4, "Validator", "checkLegalForumName", (new StringBuilder())
				.append("legalForumName :").append(legalForumName).toString());
		String legalForumNameOther = claimForm.getOtherforums();
		Log.log(4, "Validator", "checkLegalForumName", (new StringBuilder())
				.append("legalForumNameOther :").append(legalForumNameOther)
				.toString());
		if (!GenericValidator.isBlankOrNull(legalForumName)) {
			if ("Others".equals(legalForumName)
					&& GenericValidator.isBlankOrNull(legalForumNameOther)) {
				ActionError error = new ActionError("legalForumRequired");
				errors.add("org.apache.struts.action.GLOBAL_ERROR", error);
			}
		} else {
			ActionError error = new ActionError("legalForumRequired");
			errors.add("org.apache.struts.action.GLOBAL_ERROR", error);
		}
		String caseregnumber = claimForm.getCaseregnumber();
		String filingDate = claimForm.getLegaldate();
		if (!("Securitisation Act, 2002".equals(legalForumName))) {
			if (GenericValidator.isBlankOrNull(caseregnumber)) {
				ActionError error = new ActionError("caseregnumber");
				errors.add("org.apache.struts.action.GLOBAL_ERROR", error);
			}
			if (GenericValidator.isBlankOrNull(filingDate)) {
				ActionError error = new ActionError("legalDate");
				errors.add("org.apache.struts.action.GLOBAL_ERROR", error);
			}
		}
		// if(legalForumName == null || legalForumName.equals("") ||
		// legalForumName.equals("Others"))
		// {
		// Log.log(4, "Validator", "checkLegalForumName",
		// "Forum Name is not selected.");
		// if(legalForumNameOther == null || legalForumNameOther.equals(""))
		// {
		// Log.log(4, "Validator", "checkLegalForumName",
		// "Legal Forum is not entered");
		// ActionError error = new ActionError("legalForumRequired");
		// errors.add("org.apache.struts.action.GLOBAL_ERROR", error);
		// }
		// }
		Log.log(4, "Validator", "checkLegalForumName", "Exited");
		return errors.isEmpty();
	}

	public static boolean validateClaimStatus(Object bean,
			ValidatorAction validAction, Field field, ActionErrors errors,
			HttpServletRequest request) {
		Log.log(4, "Validator", "validateClaimStatus", "Entered");
	//	System.out.println("validateClaimStatus Entered");
		ClaimActionForm claimForm = (ClaimActionForm) bean;
		String claimRefNum = claimForm.getClaimRefNum();
		String enterCgpan = claimForm.getEnterCgpan();
		if ((claimRefNum == null || claimRefNum.equals(""))
				&& (enterCgpan == null || enterCgpan.equals(""))) {
			Log.log(4, "Validator", "validateClaimStatus",
					"Forum Name is not selected.");
			ActionError error = new ActionError("cgpanorclaimref");
			errors.add("org.apache.struts.action.GLOBAL_ERROR", error);
		}
		Log.log(4, "Validator", "validateClaimStatus", "Exited");
		return errors.isEmpty();
	}

	public static boolean validateTCandWCDetails(Object bean,
			ValidatorAction validAction, Field field, ActionErrors errors,
			HttpServletRequest request) {
		HttpSession session = request.getSession(false);
		ClaimActionForm claimForm = (ClaimActionForm) bean;
		Vector cgpanDetails = claimForm.getCgpnDetails();
		HashMap hashmap = null;
		String cgpanType = null;
		boolean isTC = false;
		boolean isWC = false;
		if (cgpanDetails != null && cgpanDetails.size() > 0) {
			for (int i = 0; i < cgpanDetails.size(); i++) {
				hashmap = (HashMap) cgpanDetails.elementAt(i);
				if (hashmap != null) {
					cgpanType = (String) hashmap.get("LoanType");
					if (cgpanType != null) {
						if (cgpanType.equals("TC") || "CC".equals(cgpanType)) {
							isTC = true;
						} else if (cgpanType.equals("WC")) {
							isWC = true;
						}
					}
				}
			}
		}
		if (isTC)
			validateTCDetailsFields(bean, validAction, field, errors, request);
		if (isWC)
			validateWCDetailsFields(bean, validAction, field, errors, request);
		return true;
	}

	private static boolean validateTCDetailsFields(Object bean,
			ValidatorAction validAction, Field field, ActionErrors errors,
			HttpServletRequest request) {
		HttpSession session = request.getSession(false);
		ActionError actionError = null;
		ClaimActionForm claimForm = (ClaimActionForm) bean;
		Map tclastDisbursementDts = claimForm.getLastDisbursementDate();
		Set tclastDisbursementDtsSet = tclastDisbursementDts.keySet();
		Iterator tclastDisbursementDtsIterator = tclastDisbursementDtsSet
				.iterator();
		boolean isAmntFieldValid = true;
		boolean isDtFieldValid = true;
		boolean isClaimFlagValid = true;
		int count = 0;
		while (tclastDisbursementDtsIterator.hasNext()) {
			count++;
			String key = (String) tclastDisbursementDtsIterator.next();
			String lastDisbursmntField = (String) claimForm
					.getLastDisbursementDate(key);
			if (lastDisbursmntField.equals("")) {
				isDtFieldValid = false;
				break;
			}
			SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
			try {
				Date temp = sdf
						.parse(lastDisbursmntField, new ParsePosition(0));
				if (temp != null) {
					Date currDate = new Date();
					if (temp.compareTo(currDate) > 0) {
						isDtFieldValid = false;
						break;
					}
					isDtFieldValid = true;
				} else if (temp == null) {
					isDtFieldValid = false;
					break;
				}
			} catch (Exception iaeException) {
				iaeException.printStackTrace();
			}

			String disbAmnt = (String) claimForm.getTotalDisbursementAmt(key);
			if (disbAmnt.equals("") || disbAmnt.equals("0.0")) {
				isAmntFieldValid = false;
				break;
			}
			isAmntFieldValid = true;
			String tcPrincipalField = (String) claimForm.getTcprincipal(key);
			if (tcPrincipalField.equals("") || tcPrincipalField.equals("0.0")) {
				isAmntFieldValid = false;
				break;
			}
			isAmntFieldValid = true;
			String tcInterestChargesField = (String) claimForm
					.getTcInterestCharge(key);
			if (tcInterestChargesField.equals("")
					|| tcInterestChargesField.equals("0.0")) {
				isAmntFieldValid = false;
				break;
			}
			isAmntFieldValid = true;
			String claimFlag = (String) claimForm.getClaimFlagsTc(key);
			String osAsOnDtOfNPAField = (String) claimForm
					.getTcOsAsOnDateOfNPA(key);

			if ((GenericValidator.isBlankOrNull(osAsOnDtOfNPAField)
					|| osAsOnDtOfNPAField.equals("0.0") || osAsOnDtOfNPAField
					.equals("0")) && "Y".equals(claimFlag)) {

				isAmntFieldValid = false;
				break;
			} else if (Double.parseDouble(osAsOnDtOfNPAField) < 0
					&& "Y".equals(claimFlag)) {

				isAmntFieldValid = false;
				break;
			}
			isAmntFieldValid = true;
			String osStatedInCivilSuitField = (String) claimForm
					.getTcOsAsStatedInCivilSuit(key);
			// if(osStatedInCivilSuitField.equals(""))
			if ((GenericValidator.isBlankOrNull(osStatedInCivilSuitField)
					|| osStatedInCivilSuitField.equals("0.0") || osStatedInCivilSuitField
					.equals("0")) && "Y".equals(claimFlag)) {

				isAmntFieldValid = false;
				break;
			}
			isAmntFieldValid = true;
			String osAsOnLodgmntOfClaimField = (String) claimForm
					.getTcOsAsOnLodgementOfClaim(key);
			// if(osAsOnLodgmntOfClaimField.equals(""))
			if ((GenericValidator.isBlankOrNull(osAsOnLodgmntOfClaimField)
					|| osAsOnLodgmntOfClaimField.equals("0.0") || osAsOnLodgmntOfClaimField
					.equals("0")) && "Y".equals(claimFlag)) {

				isAmntFieldValid = false;
				break;
			}
			isAmntFieldValid = true;

			String cgNo = (String) claimForm.getCgpandetails(key);
			String flagMSG = "<li>Please confirm that whether you are claiming or not for cgpan:"
					+ cgNo
					+ " in (Term Loan/Composite Loan Details Section)</li>";
			String flagMSG2 = "<li>Please enter valid outstanding amount as on claim lodgement for cgpan:"
					+ cgNo
					+ " in (Term Loan/Composite Loan Details Section)</li>";
			/* check here claimFlagsTc */

			if (GenericValidator.isBlankOrNull(claimFlag)) {
				// isClaimFlagValid = false;
				// actionError = new ActionError("invalidFlagMsg");
				actionError = new ActionError(flagMSG);
				errors.add("org.apache.struts.action.GLOBAL_ERROR", actionError);
				break;
			} else if ("Y".equals(claimFlag)
					&& Double.parseDouble(osAsOnLodgmntOfClaimField) == 0) {
				actionError = new ActionError(flagMSG2);
				errors.add("org.apache.struts.action.GLOBAL_ERROR", actionError);
				break;
			}

			if (!((String) session.getAttribute("mainMenu")).equals(MenuOptions
					.getMenu("CP_CLAIM_FOR"))
					|| !session
							.getAttribute("subMenuItem")
							.equals(MenuOptions
									.getMenu("CP_CLAIM_FOR_SECOND_INSTALLMENT")))
				continue;
			String osAsOnLodgmntOfSecClaimField = (String) claimForm
					.getTcOsAsOnLodgementOfSecondClaim(key);
			if (osAsOnLodgmntOfSecClaimField.equals("")) {
				isAmntFieldValid = false;
				break;
			}
			isAmntFieldValid = true;
		}
		if (count > 0) {
			if (!isDtFieldValid) {
				actionError = new ActionError("invaliddtmsg");
				errors.add("org.apache.struts.action.GLOBAL_ERROR", actionError);
				// actionError = new ActionError("invalidAmntMsg");
				// errors.add("org.apache.struts.action.GLOBAL_ERROR",
				// actionError);
			}
			if (!isAmntFieldValid) {
				actionError = new ActionError("invalidAmntMsg");
				errors.add("org.apache.struts.action.GLOBAL_ERROR", actionError);
			}
			/*
			 * if(!isClaimFlagValid){ actionError = new
			 * ActionError("invalidFlagMsg");
			 * errors.add("org.apache.struts.action.GLOBAL_ERROR", actionError);
			 * }
			 */
		} else {
			actionError = new ActionError("invaliddtmsg");
			errors.add("org.apache.struts.action.GLOBAL_ERROR", actionError);
			actionError = new ActionError("invalidAmntMsg");
			errors.add("org.apache.struts.action.GLOBAL_ERROR", actionError);
		}
		return errors.isEmpty();
	}

	private static boolean validateWCDetailsFields(Object bean,
			ValidatorAction validAction, Field field, ActionErrors errors,
			HttpServletRequest request) {
		HttpSession session = request.getSession(false);
		ClaimActionForm claimForm = (ClaimActionForm) bean;
		ActionError actionError = null;
		Map wcOsAsOnNPADtls = claimForm.getWcOsAsOnDateOfNPA();
		Set wcOsAsOnNPADtlsSet = wcOsAsOnNPADtls.keySet();
		Iterator wcOsAsOnNPADtlsIterator = wcOsAsOnNPADtlsSet.iterator();
		String dtOfReleaseOfWC = claimForm.getDateOfReleaseOfWC();
		boolean isAmntFieldValid = true;
		boolean isClaimFlagValid = true;
		int count;
		for (count = 0; wcOsAsOnNPADtlsIterator.hasNext(); count++) {
			String key = (String) wcOsAsOnNPADtlsIterator.next();
			String claimFlag = (String) claimForm.getClaimFlagsWc(key);

			String osnpawc = (String) claimForm.getWcOsAsOnDateOfNPA(key);
			if ((osnpawc.equals("") || "0.0".equals(osnpawc) || "0"
					.equals(osnpawc)) && "Y".equals(claimFlag)) {
				isAmntFieldValid = false;
				break;
			} else if (Double.parseDouble(osnpawc) < 0 && "Y".equals(claimFlag)) {
				isAmntFieldValid = false;
				break;
			}
			isAmntFieldValid = true;
			String oswccivilsuit = (String) claimForm
					.getWcOsAsStatedInCivilSuit(key);
			if ((oswccivilsuit.equals("") || "0.0".equals(oswccivilsuit) || "0"
					.equals(oswccivilsuit)) && "Y".equals(claimFlag)) {
				isAmntFieldValid = false;
				break;
			}
			isAmntFieldValid = true;
			String oswclodgement = (String) claimForm
					.getWcOsAsOnLodgementOfClaim(key);
			if ((oswclodgement.equals("") || "0.0".equals(oswclodgement) || "0"
					.equals(oswclodgement)) && "Y".equals(claimFlag)) {
				isAmntFieldValid = false;
				break;
			}
			isAmntFieldValid = true;
			String cgNo = (String) claimForm.getWcCgpan(key);
			String flagMSG = "<li>Please confirm that whether you are claiming or not for cgpan:"
					+ cgNo + " in (Working Capital Details Section)</li>";
			String flagMSG2 = "<li>Please enter valid outstanding amount as on claim lodgement for cgpan:"
					+ cgNo + " in (Working Capital Details Section)</li>";

			if (GenericValidator.isBlankOrNull(claimFlag)) {
				// isClaimFlagValid = false;
				// break;
				actionError = new ActionError(flagMSG);
				errors.add("org.apache.struts.action.GLOBAL_ERROR", actionError);
				break;
			} else if ("Y".equals(claimFlag)
					&& Double.parseDouble(oswclodgement) <= 0) {
				actionError = new ActionError(flagMSG2);
				errors.add("org.apache.struts.action.GLOBAL_ERROR", actionError);
				break;
			}

			if (!((String) session.getAttribute("mainMenu")).equals(MenuOptions
					.getMenu("CP_CLAIM_FOR"))
					|| !session
							.getAttribute("subMenuItem")
							.equals(MenuOptions
									.getMenu("CP_CLAIM_FOR_SECOND_INSTALLMENT")))
				continue;
			String oswclodgementSecClm = (String) claimForm
					.getWcOsAsOnLodgementOfSecondClaim(key);
			if (oswclodgementSecClm.equals("")) {
				isAmntFieldValid = false;
				break;
			}
			isAmntFieldValid = true;
		}

		if (count > 0) {
			if (!isAmntFieldValid) {
				actionError = new ActionError("invalidwcamntmsg");
				errors.add("org.apache.struts.action.GLOBAL_ERROR", actionError);
			}
			/*
			 * if(!isClaimFlagValid){ actionError = new
			 * ActionError("invalidFlagMsg");
			 * errors.add("org.apache.struts.action.GLOBAL_ERROR", actionError);
			 * }
			 */
		} else {
			actionError = new ActionError("invalidwcamntmsg");
			errors.add("org.apache.struts.action.GLOBAL_ERROR", actionError);
		}
		return errors.isEmpty();
	}

	public static boolean validateRecoveryDetails(Object bean,
			ValidatorAction validAction, Field field, ActionErrors errors,
			HttpServletRequest request) {
		Log.log(4, "Validator", "validateRecoveryDetails", "Entered");
		ActionError actionError = null;
		ClaimActionForm claimForm = (ClaimActionForm) bean;
		Map recoveryDetails = claimForm.getCgpandetails();
		Set recoveryDetailsSet = recoveryDetails.keySet();
		Iterator recoveryDetailsIterator = recoveryDetailsSet.iterator();
		// String dtOfSeekingOTS = claimForm.getDateOfSeekingOTS();
		boolean isTCPFieldValidFlag = true;
		boolean isWCPFieldValidFlag = true;
		boolean isTCIFieldValidFlag = true;
		boolean isWCIFieldValidFlag = true;
		boolean isRecoveyModeValid = true;
		boolean allFieldsValid = true;
		boolean isOTSFlag = true;
		String thisfield = null;
		int count = 0;
		String substring = "$recovery#key-".trim();
		String tcfield = "TC".trim();
		String wcfield = "WC".trim();
		String prinField = null;
		String interestField = null;
		String amountField = null;
		String otherChrgs = null;
		String recModeField = null;
		int num = -1;
		Vector recDtls = new Vector();
		HashMap dtl = null;
		String lastCh = null;
		String otsSeekingDate = claimForm.getDateOfSeekingOTS();
		while (recoveryDetailsIterator.hasNext()) {
			boolean elementAdded = false;
			String key = (String) recoveryDetailsIterator.next();
			if (key != null) {
				String ch = key.substring(key.length() - 1, key.length());
				if (key.indexOf("tcprincipal$recovery".trim()) >= 0) {
					prinField = (String) claimForm.getCgpandetails(key);
					if (ch != null) {
						if (count == 0) {
							dtl = new HashMap();
							dtl.put("REC_FLAG", ch);
							dtl.put("TCPRINCIPAL", prinField);
							recDtls.addElement(dtl);
						}
						if (count > 0) {
							for (int i = 0; i < recDtls.size(); i++) {
								HashMap tempMap = (HashMap) recDtls
										.elementAt(i);
								String tempFlag = (String) tempMap
										.get("REC_FLAG");
								if (tempFlag != null && tempFlag.equals(ch)) {
									tempMap = (HashMap) recDtls.remove(i);
									tempMap.put("TCPRINCIPAL", prinField);
									recDtls.addElement(tempMap);
									elementAdded = true;
								}
							}

							if (!elementAdded) {
								dtl = new HashMap();
								dtl.put("REC_FLAG", ch);
								dtl.put("TCPRINCIPAL", prinField);
								recDtls.addElement(dtl);
								elementAdded = true;
							}
						}
					}
				}
				if (key.indexOf("tcInterestCharges$recovery".trim()) >= 0) {
					interestField = (String) claimForm.getCgpandetails(key);
					if (ch != null) {
						if (count == 0) {
							dtl = new HashMap();
							dtl.put("REC_FLAG", ch);
							dtl.put("TCINTEREST", interestField);
							recDtls.addElement(dtl);
						}
						if (count > 0) {
							for (int i = 0; i < recDtls.size(); i++) {
								HashMap tempMap = (HashMap) recDtls
										.elementAt(i);
								String tempFlag = (String) tempMap
										.get("REC_FLAG");
								if (tempFlag != null && tempFlag.equals(ch)) {
									tempMap = (HashMap) recDtls.remove(i);
									tempMap.put("TCINTEREST", interestField);
									recDtls.addElement(tempMap);
									elementAdded = true;
								}
							}

							if (!elementAdded) {
								dtl = new HashMap();
								dtl.put("REC_FLAG", ch);
								dtl.put("TCINTEREST", interestField);
								recDtls.addElement(dtl);
								elementAdded = true;
							}
						}
					}
				}
				if (key.indexOf("wcAmount$recovery".trim()) >= 0) {
					amountField = (String) claimForm.getCgpandetails(key);
					if (ch != null) {
						if (count == 0) {
							dtl = new HashMap();
							dtl.put("REC_FLAG", ch);
							dtl.put("WC_AMOUNT", amountField);
							recDtls.addElement(dtl);
						}
						if (count > 0) {
							for (int i = 0; i < recDtls.size(); i++) {
								HashMap tempMap = (HashMap) recDtls
										.elementAt(i);
								String tempFlag = (String) tempMap
										.get("REC_FLAG");
								if (tempFlag != null && tempFlag.equals(ch)) {
									tempMap = (HashMap) recDtls.remove(i);
									tempMap.put("WC_AMOUNT", amountField);
									recDtls.addElement(tempMap);
									elementAdded = true;
								}
							}

							if (!elementAdded) {
								dtl = new HashMap();
								dtl.put("REC_FLAG", ch);
								dtl.put("WC_AMOUNT", amountField);
								recDtls.addElement(dtl);
								elementAdded = true;
							}
						}
					}
				}
				if (key.indexOf("wcOtherCharges$recovery".trim()) >= 0) {
					otherChrgs = (String) claimForm.getCgpandetails(key);
					if (ch != null) {
						if (count == 0) {
							dtl = new HashMap();
							dtl.put("REC_FLAG", ch);
							dtl.put("WC_OTHER", otherChrgs);
							recDtls.addElement(dtl);
						}
						if (count > 0) {
							for (int i = 0; i < recDtls.size(); i++) {
								HashMap tempMap = (HashMap) recDtls
										.elementAt(i);
								String tempFlag = (String) tempMap
										.get("REC_FLAG");
								if (tempFlag != null && tempFlag.equals(ch)) {
									tempMap = (HashMap) recDtls.remove(i);
									tempMap.put("WC_OTHER", otherChrgs);
									recDtls.addElement(tempMap);
									elementAdded = true;
								}
							}

							if (!elementAdded) {
								dtl = new HashMap();
								dtl.put("REC_FLAG", ch);
								dtl.put("WC_OTHER", otherChrgs);
								recDtls.addElement(dtl);
								elementAdded = true;
							}
						}
					}
				}
				if (key.indexOf("recoveryMode$recovery".trim()) >= 0) {
					recModeField = (String) claimForm.getCgpandetails(key);
					if (ch != null) {
						if (count == 0) {
							dtl = new HashMap();
							dtl.put("REC_FLAG", ch);
							dtl.put("REC_MODE", recModeField);
							recDtls.addElement(dtl);
						}
						if (count > 0) {
							for (int i = 0; i < recDtls.size(); i++) {
								HashMap tempMap = (HashMap) recDtls
										.elementAt(i);
								String tempFlag = (String) tempMap
										.get("REC_FLAG");
								if (tempFlag != null && tempFlag.equals(ch)) {
									tempMap = (HashMap) recDtls.remove(i);
									tempMap.put("REC_MODE", recModeField);
									recDtls.addElement(tempMap);
									elementAdded = true;
								}
							}

							if (!elementAdded) {
								dtl = new HashMap();
								dtl.put("REC_FLAG", ch);
								dtl.put("REC_MODE", recModeField);
								recDtls.addElement(dtl);
								elementAdded = true;
							}
						}
					}
				}
				count++;
				dtl = null;
			}
		}
		boolean isRecModeValid = true;
		boolean OTSPresent = false;
		String lastRecoveryModeField = null;
		int counter = 0;
		boolean jumpOutOfForLoop = false;
		for (int k = 0; k < recDtls.size(); k++) {
			HashMap map = (HashMap) recDtls.elementAt(k);
			if (map == null)
				continue;
			Set mapSet = map.keySet();
			Iterator mapIterator = mapSet.iterator();
			boolean finalFieldsValidity = true;
			boolean areFieldsValid = false;
			Vector tempVec = new Vector();
			while (mapIterator.hasNext()) {
				String temFlag = (String) mapIterator.next();
				if (temFlag == null || temFlag.equals("REC_FLAG"))
					continue;
				String value = (String) map.get(temFlag);
				if (value == null || value.equals(""))
					value = "-";
				tempVec.addElement(value);
				if (!temFlag.equals("REC_MODE"))
					continue;
				if (value != null && value.indexOf("OTS".trim()) != -1)
					OTSPresent = true;
				if (counter == 0 && !value.equals("-"))
					lastRecoveryModeField = value;
				if (counter <= 0)
					continue;
				if (lastRecoveryModeField != null
						&& value != null
						&& !value.equals("-")
						&& (lastRecoveryModeField.indexOf("OTS".trim()) == -1
								&& value.indexOf("OTS".trim()) != -1 || lastRecoveryModeField
								.indexOf("OTS".trim()) != -1
								&& value.indexOf("OTS".trim()) == -1)) {
					isRecModeValid = false;
					jumpOutOfForLoop = true;
					break;
				}
				if (!value.equals("-"))
					lastRecoveryModeField = value;
			}
			if (jumpOutOfForLoop)
				break;
			if (tempVec.size() == 3
					&& ((String) tempVec.elementAt(0)).equals("-")
					&& ((String) tempVec.elementAt(1)).equals("-")
					&& ((String) tempVec.elementAt(2)).equals("-"))
				areFieldsValid = true;
			if (tempVec.size() == 3 && (String) tempVec.elementAt(0) != null
					&& !((String) tempVec.elementAt(0)).equals("-")
					&& (String) tempVec.elementAt(1) != null
					&& !((String) tempVec.elementAt(1)).equals("-")
					&& (String) tempVec.elementAt(2) != null
					&& !((String) tempVec.elementAt(2)).equals("-"))
				areFieldsValid = true;
			if (!areFieldsValid) {
				actionError = new ActionError("validrecoverydetailsmsg");
				errors.add("org.apache.struts.action.GLOBAL_ERROR", actionError);
				break;
			}
			counter++;
			map = null;
		}

		if (!isRecModeValid) {
			actionError = new ActionError("validrecoverymodemsg");
			errors.add("org.apache.struts.action.GLOBAL_ERROR", actionError);
		}
		if (OTSPresent && (otsSeekingDate == null || otsSeekingDate.equals(""))) {
			actionError = new ActionError("invalidOTSDate");
			errors.add("org.apache.struts.action.GLOBAL_ERROR", actionError);
		}
		recDtls = null;
		recoveryDetails = null;
		recoveryDetailsSet = null;
		recoveryDetailsIterator = null;
		// dtOfSeekingOTS = null;
		thisfield = null;
		substring = null;
		tcfield = null;
		wcfield = null;
		prinField = null;
		interestField = null;
		amountField = null;
		otherChrgs = null;
		recModeField = null;
		recDtls = null;
		dtl = null;
		lastCh = null;
		otsSeekingDate = claimForm.getDateOfSeekingOTS();
		Log.log(4, "Validator", "validateRecoveryDetails", "Exited");
		return errors.isEmpty();
	}

	public static boolean validateSPGDetails(Object bean,
			ValidatorAction validAction, Field field, ActionErrors errors,
			HttpServletRequest request) {
		ActionError actionError = null;
		ClaimActionForm claimForm = (ClaimActionForm) bean;
		Map asOnDtOfSanctionDtls = claimForm.getAsOnDtOfSanctionDtl();
		Set asOnDtOfSanctionDtlsSet = asOnDtOfSanctionDtls.keySet();
		Iterator asOnDtOfSanctionDtlsIterator = asOnDtOfSanctionDtlsSet
				.iterator();
		Map asOnNPADtls = null;
		Set asOnNPADtlsSet = null;
		Iterator asOnNPADtlsIterator = null;
		Map asOnLodgemntDtls = null;
		Set asOnLodgemntDtlsSet = null;
		Iterator asOnLodgemntDtlsIterator = null;
		boolean isNetWorthFieldValid = true;
		boolean isLandfieldvalid = false;
		boolean ismcfieldvalid = false;
		boolean isbldgfieldvalid = false;
		boolean isofmafieldvalid = false;
		boolean iscurrassetsvalid = false;
		boolean isothersvalid = false;
		boolean isreasonforreductionfieldvalid = false;
		boolean sanctionChk = true;
		boolean npaChk = true;
		boolean lodgementChk = true;

		double landval = 0.0;
		double bldgval = 0.0;
		double machineval = 0.0;
		double movassetval = 0.0;
		double curassetval = 0.0;
		double othersval = 0.0;
		double totSecAsOnSanc = 0.0;
		double totSecAsOnNPA = 0.0;
		double totSecAsOnClaim = 0.0;
		String totSecAsOnSancStr = null;
		String totSecAsOnNPAStr = null;
		String totSecAsOnClaimStr = null;

		String landStr = null;
		String bldgStr = null;
		String machineStr = null;
		String movassetStr = null;
		String curassetStr = null;
		String otherStr = null;

		landStr = (String) claimForm
				.getAsOnDtOfSanctionDtl(ClaimConstants.CLM_SAPGD_PARTICULAR_LAND);
		bldgStr = (String) claimForm
				.getAsOnDtOfSanctionDtl(ClaimConstants.CLM_SAPGD_PARTICULAR_BLDG);
		machineStr = (String) claimForm
				.getAsOnDtOfSanctionDtl(ClaimConstants.CLM_SAPGD_PARTICULAR_MC);
		movassetStr = (String) claimForm
				.getAsOnDtOfSanctionDtl(ClaimConstants.CLM_SAPGD_PARTICULAR_OTHER_FIXED_MOV_ASSETS);
		curassetStr = (String) claimForm
				.getAsOnDtOfSanctionDtl(ClaimConstants.CLM_SAPGD_PARTICULAR_CUR_ASSETS);
		otherStr = (String) claimForm
				.getAsOnDtOfSanctionDtl(ClaimConstants.CLM_SAPGD_PARTICULAR_OTHERS);

		if (!GenericValidator.isBlankOrNull(landStr)) {
			landval = Double.parseDouble(landStr);
		}
		if (!GenericValidator.isBlankOrNull(bldgStr)) {
			bldgval = Double.parseDouble(bldgStr);
		}
		if (!GenericValidator.isBlankOrNull(machineStr)) {
			machineval = Double.parseDouble(machineStr);
		}
		if (!GenericValidator.isBlankOrNull(movassetStr)) {
			movassetval = Double.parseDouble(movassetStr);
		}
		if (!GenericValidator.isBlankOrNull(curassetStr)) {
			curassetval = Double.parseDouble(curassetStr);
		}
		if (!GenericValidator.isBlankOrNull(otherStr)) {
			othersval = Double.parseDouble(otherStr);
		}

		if (landval > 0.0 || bldgval > 0.0 || machineval > 0.0
				|| movassetval > 0.0 || curassetval > 0.0 || othersval > 0.0) {

		} else {
			actionError = new ActionError("secuirtyValuesAsOnSanc");
			errors.add("org.apache.struts.action.GLOBAL_ERROR", actionError);
		}

		landval = 0.0;
		bldgval = 0.0;
		machineval = 0.0;
		movassetval = 0.0;
		curassetval = 0.0;
		othersval = 0.0;
		landStr = null;
		bldgStr = null;
		machineStr = null;
		movassetStr = null;
		curassetStr = null;
		otherStr = null;

		landStr = (String) claimForm
				.getAsOnDtOfNPA(ClaimConstants.CLM_SAPGD_PARTICULAR_LAND);
		bldgStr = (String) claimForm
				.getAsOnDtOfNPA(ClaimConstants.CLM_SAPGD_PARTICULAR_BLDG);
		machineStr = (String) claimForm
				.getAsOnDtOfNPA(ClaimConstants.CLM_SAPGD_PARTICULAR_MC);
		movassetStr = (String) claimForm
				.getAsOnDtOfNPA(ClaimConstants.CLM_SAPGD_PARTICULAR_OTHER_FIXED_MOV_ASSETS);
		curassetStr = (String) claimForm
				.getAsOnDtOfNPA(ClaimConstants.CLM_SAPGD_PARTICULAR_CUR_ASSETS);
		otherStr = (String) claimForm
				.getAsOnDtOfNPA(ClaimConstants.CLM_SAPGD_PARTICULAR_OTHERS);

		if (!GenericValidator.isBlankOrNull(landStr)) {
			landval = Double.parseDouble(landStr);
		}
		if (!GenericValidator.isBlankOrNull(bldgStr)) {
			bldgval = Double.parseDouble(bldgStr);
		}
		if (!GenericValidator.isBlankOrNull(machineStr)) {
			machineval = Double.parseDouble(machineStr);
		}
		if (!GenericValidator.isBlankOrNull(movassetStr)) {
			movassetval = Double.parseDouble(movassetStr);
		}
		if (!GenericValidator.isBlankOrNull(curassetStr)) {
			curassetval = Double.parseDouble(curassetStr);
		}
		if (!GenericValidator.isBlankOrNull(otherStr)) {
			othersval = Double.parseDouble(otherStr);
		}

		if (landval > 0.0 || bldgval > 0.0 || machineval > 0.0
				|| movassetval > 0.0 || curassetval > 0.0 || othersval > 0.0) {

		} else {
			actionError = new ActionError("secuirtyValuesAsOnNpa");
			errors.add("org.apache.struts.action.GLOBAL_ERROR", actionError);
		}

		totSecAsOnSanc = (Double) claimForm.getTotSecAsOnSanc();
		totSecAsOnNPA = (Double) claimForm.getTotSecAsOnNpa();
		totSecAsOnClaim = claimForm.getTotSecAsOnClaim();
		String reasonForReductionNpa = (String) claimForm
				.getAsOnDtOfNPA(ClaimConstants.CLM_SAPGD_REASONS_FOR_REDUCTION);
		String resonForReductionClaim = (String) claimForm
				.getAsOnLodgemntOfCredit(ClaimConstants.CLM_SAPGD_REASONS_FOR_REDUCTION);

		if (GenericValidator.isBlankOrNull(reasonForReductionNpa)
				&& totSecAsOnNPA < totSecAsOnSanc) {
			actionError = new ActionError("ReasonForReduction");
			errors.add("org.apache.struts.action.GLOBAL_ERROR", actionError);
		}
		if (GenericValidator.isBlankOrNull(resonForReductionClaim)
				&& totSecAsOnClaim < totSecAsOnNPA) {
			actionError = new ActionError("ReasonForReduction");
			errors.add("org.apache.struts.action.GLOBAL_ERROR", actionError);
		}

		landval = 0.0;
		bldgval = 0.0;
		machineval = 0.0;
		movassetval = 0.0;
		curassetval = 0.0;
		othersval = 0.0;
		landStr = null;
		bldgStr = null;
		machineStr = null;
		movassetStr = null;
		curassetStr = null;
		otherStr = null;

		/*
		 * while(asOnDtOfSanctionDtlsIterator.hasNext()) { String
		 * thisfieldSanction = ""; String thisfieldNPA = ""; String
		 * thisfieldClmLodgemnt = ""; String keySanction =
		 * (String)asOnDtOfSanctionDtlsIterator.next();
		 * if(keySanction.equals("LAND")) { thisfieldSanction =
		 * (String)claimForm.getAsOnDtOfSanctionDtl(keySanction); asOnNPADtls =
		 * claimForm.getAsOnDtOfNPA(); asOnNPADtlsSet = asOnNPADtls.keySet();
		 * for(asOnNPADtlsIterator = asOnNPADtlsSet.iterator();
		 * asOnNPADtlsIterator.hasNext();) { String keyNPA =
		 * (String)asOnNPADtlsIterator.next(); if(keyNPA.equals("LAND"))
		 * thisfieldNPA = (String)claimForm.getAsOnDtOfNPA(keyNPA); }
		 * 
		 * asOnLodgemntDtls = claimForm.getAsOnLodgemntOfCredit();
		 * asOnLodgemntDtlsSet = asOnLodgemntDtls.keySet();
		 * for(asOnLodgemntDtlsIterator = asOnLodgemntDtlsSet.iterator();
		 * asOnLodgemntDtlsIterator.hasNext();) { String keyClmLodgemnt =
		 * (String)asOnLodgemntDtlsIterator.next();
		 * if(keyClmLodgemnt.equals("LAND")) thisfieldClmLodgemnt =
		 * (String)claimForm.getAsOnLodgemntOfCredit(keyClmLodgemnt); }
		 * 
		 * if(thisfieldSanction.equals("") && thisfieldNPA.equals("") &&
		 * thisfieldClmLodgemnt.equals("")) isLandfieldvalid = true; else
		 * if(!thisfieldSanction.equals("") && !thisfieldNPA.equals("") &&
		 * !thisfieldClmLodgemnt.equals("")) isLandfieldvalid = true;
		 * if(!thisfieldSanction.equals("")) { if(thisfieldNPA.equals(""))
		 * npaChk = false; else if(!thisfieldNPA.equals("")) {
		 * if(Double.parseDouble(thisfieldSanction) >
		 * Double.parseDouble(thisfieldNPA)) npaChk = false;
		 * if(thisfieldClmLodgemnt.equals("")) lodgementChk = false; else
		 * if(!thisfieldClmLodgemnt.equals("") &&
		 * Double.parseDouble(thisfieldNPA) >
		 * Double.parseDouble(thisfieldClmLodgemnt)) lodgementChk = false; } }
		 * else if(thisfieldSanction.equals("") && !thisfieldNPA.equals(""))
		 * if(thisfieldClmLodgemnt.equals("")) lodgementChk = false; else
		 * if(!thisfieldClmLodgemnt.equals("") &&
		 * Double.parseDouble(thisfieldNPA) >
		 * Double.parseDouble(thisfieldClmLodgemnt)) lodgementChk = false;
		 * thisfieldSanction = ""; thisfieldNPA = ""; thisfieldClmLodgemnt = "";
		 * } else if(keySanction.equals("networth")) { thisfieldSanction =
		 * (String)claimForm.getAsOnDtOfSanctionDtl(keySanction);
		 * if(!thisfieldSanction.equals("")) { asOnNPADtls =
		 * claimForm.getAsOnDtOfNPA(); asOnNPADtlsSet = asOnNPADtls.keySet();
		 * for(asOnNPADtlsIterator = asOnNPADtlsSet.iterator();
		 * asOnNPADtlsIterator.hasNext();) { String keyNPA =
		 * (String)asOnNPADtlsIterator.next(); if(keyNPA.equals("networth")) {
		 * thisfieldNPA = (String)claimForm.getAsOnDtOfNPA(keyNPA);
		 * if(thisfieldNPA.equals("")) isNetWorthFieldValid = false; } }
		 * 
		 * asOnLodgemntDtls = claimForm.getAsOnLodgemntOfCredit();
		 * asOnLodgemntDtlsSet = asOnLodgemntDtls.keySet();
		 * for(asOnLodgemntDtlsIterator = asOnLodgemntDtlsSet.iterator();
		 * asOnLodgemntDtlsIterator.hasNext();) { String keyClmLodgemnt =
		 * (String)asOnLodgemntDtlsIterator.next();
		 * if(keyClmLodgemnt.equals("networth")) { thisfieldClmLodgemnt =
		 * (String)claimForm.getAsOnLodgemntOfCredit(keyClmLodgemnt);
		 * if(thisfieldClmLodgemnt.equals("")) isNetWorthFieldValid = false; } }
		 * 
		 * 
		 * } else { isNetWorthFieldValid = false; } } else
		 * if(keySanction.equals("BUILDING")) { thisfieldSanction =
		 * (String)claimForm.getAsOnDtOfSanctionDtl(keySanction); asOnNPADtls =
		 * claimForm.getAsOnDtOfNPA(); asOnNPADtlsSet = asOnNPADtls.keySet();
		 * for(asOnNPADtlsIterator = asOnNPADtlsSet.iterator();
		 * asOnNPADtlsIterator.hasNext();) { String keyNPA =
		 * (String)asOnNPADtlsIterator.next(); if(keyNPA.equals("BUILDING"))
		 * thisfieldNPA = (String)claimForm.getAsOnDtOfNPA(keyNPA); }
		 * 
		 * asOnLodgemntDtls = claimForm.getAsOnLodgemntOfCredit();
		 * asOnLodgemntDtlsSet = asOnLodgemntDtls.keySet();
		 * for(asOnLodgemntDtlsIterator = asOnLodgemntDtlsSet.iterator();
		 * asOnLodgemntDtlsIterator.hasNext();) { String keyClmLodgemnt =
		 * (String)asOnLodgemntDtlsIterator.next();
		 * if(keyClmLodgemnt.equals("BUILDING")) thisfieldClmLodgemnt =
		 * (String)claimForm.getAsOnLodgemntOfCredit(keyClmLodgemnt); }
		 * 
		 * if(thisfieldSanction.equals("") && thisfieldNPA.equals("") &&
		 * thisfieldClmLodgemnt.equals("")) isbldgfieldvalid = true;
		 * if(!thisfieldSanction.equals("") && !thisfieldNPA.equals("") &&
		 * !thisfieldClmLodgemnt.equals("")) isbldgfieldvalid = true;
		 * if(!thisfieldSanction.equals("")) { if(thisfieldNPA.equals(""))
		 * npaChk = false; else if(!thisfieldNPA.equals("")) {
		 * if(Double.parseDouble(thisfieldSanction) >
		 * Double.parseDouble(thisfieldNPA)) npaChk = false;
		 * if(thisfieldClmLodgemnt.equals("")) lodgementChk = false; else
		 * if(!thisfieldClmLodgemnt.equals("") &&
		 * Double.parseDouble(thisfieldNPA) >
		 * Double.parseDouble(thisfieldClmLodgemnt)) lodgementChk = false; } }
		 * else if(thisfieldSanction.equals("") && !thisfieldNPA.equals(""))
		 * if(thisfieldClmLodgemnt.equals("")) lodgementChk = false; else
		 * if(!thisfieldClmLodgemnt.equals("") &&
		 * Double.parseDouble(thisfieldNPA) >
		 * Double.parseDouble(thisfieldClmLodgemnt)) lodgementChk = false;
		 * thisfieldSanction = ""; thisfieldNPA = ""; thisfieldClmLodgemnt = "";
		 * } else if(keySanction.equals("MACHINE")) { thisfieldSanction =
		 * (String)claimForm.getAsOnDtOfSanctionDtl(keySanction); asOnNPADtls =
		 * claimForm.getAsOnDtOfNPA(); asOnNPADtlsSet = asOnNPADtls.keySet();
		 * for(asOnNPADtlsIterator = asOnNPADtlsSet.iterator();
		 * asOnNPADtlsIterator.hasNext();) { String keyNPA =
		 * (String)asOnNPADtlsIterator.next(); if(keyNPA.equals("MACHINE"))
		 * thisfieldNPA = (String)claimForm.getAsOnDtOfNPA(keyNPA); }
		 * 
		 * asOnLodgemntDtls = claimForm.getAsOnLodgemntOfCredit();
		 * asOnLodgemntDtlsSet = asOnLodgemntDtls.keySet();
		 * for(asOnLodgemntDtlsIterator = asOnLodgemntDtlsSet.iterator();
		 * asOnLodgemntDtlsIterator.hasNext();) { String keyClmLodgemnt =
		 * (String)asOnLodgemntDtlsIterator.next();
		 * if(keyClmLodgemnt.equals("MACHINE")) thisfieldClmLodgemnt =
		 * (String)claimForm.getAsOnLodgemntOfCredit(keyClmLodgemnt); }
		 * 
		 * if(thisfieldSanction.equals("") && thisfieldNPA.equals("") &&
		 * thisfieldClmLodgemnt.equals("")) ismcfieldvalid = true;
		 * if(!thisfieldSanction.equals("") && !thisfieldNPA.equals("") &&
		 * !thisfieldClmLodgemnt.equals("")) ismcfieldvalid = true;
		 * if(!thisfieldSanction.equals("")) { if(thisfieldNPA.equals(""))
		 * npaChk = false; else if(!thisfieldNPA.equals("")) {
		 * if(Double.parseDouble(thisfieldSanction) >
		 * Double.parseDouble(thisfieldNPA)) npaChk = false;
		 * if(thisfieldClmLodgemnt.equals("")) lodgementChk = false; else
		 * if(!thisfieldClmLodgemnt.equals("") &&
		 * Double.parseDouble(thisfieldNPA) >
		 * Double.parseDouble(thisfieldClmLodgemnt)) lodgementChk = false; } }
		 * else if(thisfieldSanction.equals("") && !thisfieldNPA.equals(""))
		 * if(thisfieldClmLodgemnt.equals("")) lodgementChk = false; else
		 * if(!thisfieldClmLodgemnt.equals("") &&
		 * Double.parseDouble(thisfieldNPA) >
		 * Double.parseDouble(thisfieldClmLodgemnt)) lodgementChk = false;
		 * thisfieldSanction = ""; thisfieldNPA = ""; thisfieldClmLodgemnt = "";
		 * } else if(keySanction.equals("OTHER FIXED MOVABLE ASSETS")) {
		 * thisfieldSanction =
		 * (String)claimForm.getAsOnDtOfSanctionDtl(keySanction); asOnNPADtls =
		 * claimForm.getAsOnDtOfNPA(); asOnNPADtlsSet = asOnNPADtls.keySet();
		 * for(asOnNPADtlsIterator = asOnNPADtlsSet.iterator();
		 * asOnNPADtlsIterator.hasNext();) { String keyNPA =
		 * (String)asOnNPADtlsIterator.next();
		 * if(keyNPA.equals("OTHER FIXED MOVABLE ASSETS")) thisfieldNPA =
		 * (String)claimForm.getAsOnDtOfNPA(keyNPA); }
		 * 
		 * asOnLodgemntDtls = claimForm.getAsOnLodgemntOfCredit();
		 * asOnLodgemntDtlsSet = asOnLodgemntDtls.keySet();
		 * for(asOnLodgemntDtlsIterator = asOnLodgemntDtlsSet.iterator();
		 * asOnLodgemntDtlsIterator.hasNext();) { String keyClmLodgemnt =
		 * (String)asOnLodgemntDtlsIterator.next();
		 * if(keyClmLodgemnt.equals("OTHER FIXED MOVABLE ASSETS"))
		 * thisfieldClmLodgemnt =
		 * (String)claimForm.getAsOnLodgemntOfCredit(keyClmLodgemnt); }
		 * 
		 * if(thisfieldSanction.equals("") && thisfieldNPA.equals("") &&
		 * thisfieldClmLodgemnt.equals("")) isofmafieldvalid = true;
		 * if(!thisfieldSanction.equals("") && !thisfieldNPA.equals("") &&
		 * !thisfieldClmLodgemnt.equals("")) isofmafieldvalid = true;
		 * if(!thisfieldSanction.equals("")) { if(thisfieldNPA.equals(""))
		 * npaChk = false; else if(!thisfieldNPA.equals("")) {
		 * if(Double.parseDouble(thisfieldSanction) >
		 * Double.parseDouble(thisfieldNPA)) npaChk = false;
		 * if(thisfieldClmLodgemnt.equals("")) lodgementChk = false; else
		 * if(!thisfieldClmLodgemnt.equals("") &&
		 * Double.parseDouble(thisfieldNPA) >
		 * Double.parseDouble(thisfieldClmLodgemnt)) lodgementChk = false; } }
		 * else if(thisfieldSanction.equals("") && !thisfieldNPA.equals(""))
		 * if(thisfieldClmLodgemnt.equals("")) lodgementChk = false; else
		 * if(!thisfieldClmLodgemnt.equals("") && Integer.parseInt(thisfieldNPA)
		 * > Integer.parseInt(thisfieldClmLodgemnt)) lodgementChk = false;
		 * thisfieldSanction = ""; thisfieldNPA = ""; thisfieldClmLodgemnt = "";
		 * } else if(keySanction.equals("CURRENT ASSETS")) { thisfieldSanction =
		 * (String)claimForm.getAsOnDtOfSanctionDtl(keySanction); asOnNPADtls =
		 * claimForm.getAsOnDtOfNPA(); asOnNPADtlsSet = asOnNPADtls.keySet();
		 * for(asOnNPADtlsIterator = asOnNPADtlsSet.iterator();
		 * asOnNPADtlsIterator.hasNext();) { String keyNPA =
		 * (String)asOnNPADtlsIterator.next();
		 * if(keyNPA.equals("CURRENT ASSETS")) { thisfieldNPA =
		 * (String)claimForm.getAsOnDtOfNPA(keyNPA); if(thisfieldNPA.equals(""))
		 * iscurrassetsvalid = false; } }
		 * 
		 * asOnLodgemntDtls = claimForm.getAsOnLodgemntOfCredit();
		 * asOnLodgemntDtlsSet = asOnLodgemntDtls.keySet();
		 * for(asOnLodgemntDtlsIterator = asOnLodgemntDtlsSet.iterator();
		 * asOnLodgemntDtlsIterator.hasNext();) { String keyClmLodgemnt =
		 * (String)asOnLodgemntDtlsIterator.next();
		 * if(keyClmLodgemnt.equals("CURRENT ASSETS")) { thisfieldClmLodgemnt =
		 * (String)claimForm.getAsOnLodgemntOfCredit(keyClmLodgemnt);
		 * if(thisfieldClmLodgemnt.equals("")) iscurrassetsvalid = false; } }
		 * 
		 * if(thisfieldSanction.equals("") && thisfieldNPA.equals("") &&
		 * thisfieldClmLodgemnt.equals("")) iscurrassetsvalid = true;
		 * if(!thisfieldSanction.equals("") && !thisfieldNPA.equals("") &&
		 * !thisfieldClmLodgemnt.equals("")) iscurrassetsvalid = true;
		 * if(!thisfieldSanction.equals("")) { if(thisfieldNPA.equals(""))
		 * npaChk = false; else if(!thisfieldNPA.equals("")) {
		 * if(Double.parseDouble(thisfieldSanction) >
		 * Double.parseDouble(thisfieldNPA)) npaChk = false;
		 * if(thisfieldClmLodgemnt.equals("")) lodgementChk = false; else
		 * if(!thisfieldClmLodgemnt.equals("") &&
		 * Double.parseDouble(thisfieldNPA) >
		 * Double.parseDouble(thisfieldClmLodgemnt)) lodgementChk = false; } }
		 * else if(thisfieldSanction.equals("") && !thisfieldNPA.equals(""))
		 * if(thisfieldClmLodgemnt.equals("")) lodgementChk = false; else
		 * if(!thisfieldClmLodgemnt.equals("") &&
		 * Double.parseDouble(thisfieldNPA) >
		 * Double.parseDouble(thisfieldClmLodgemnt)) lodgementChk = false;
		 * thisfieldSanction = ""; thisfieldNPA = ""; thisfieldClmLodgemnt = "";
		 * } else if(keySanction.equals("OTHERS")) { thisfieldSanction =
		 * (String)claimForm.getAsOnDtOfSanctionDtl(keySanction); asOnNPADtls =
		 * claimForm.getAsOnDtOfNPA(); asOnNPADtlsSet = asOnNPADtls.keySet();
		 * for(asOnNPADtlsIterator = asOnNPADtlsSet.iterator();
		 * asOnNPADtlsIterator.hasNext();) { String keyNPA =
		 * (String)asOnNPADtlsIterator.next(); if(keyNPA.equals("OTHERS"))
		 * thisfieldNPA = (String)claimForm.getAsOnDtOfNPA(keyNPA); }
		 * 
		 * asOnLodgemntDtls = claimForm.getAsOnLodgemntOfCredit();
		 * asOnLodgemntDtlsSet = asOnLodgemntDtls.keySet();
		 * for(asOnLodgemntDtlsIterator = asOnLodgemntDtlsSet.iterator();
		 * asOnLodgemntDtlsIterator.hasNext();) { String keyClmLodgemnt =
		 * (String)asOnLodgemntDtlsIterator.next();
		 * if(keyClmLodgemnt.equals("OTHERS")) thisfieldClmLodgemnt =
		 * (String)claimForm.getAsOnLodgemntOfCredit(keyClmLodgemnt); }
		 * 
		 * if(thisfieldSanction.equals("") && thisfieldNPA.equals("") &&
		 * thisfieldClmLodgemnt.equals("")) isothersvalid = true;
		 * if(!thisfieldSanction.equals("") && !thisfieldNPA.equals("") &&
		 * !thisfieldClmLodgemnt.equals("")) isothersvalid = true;
		 * if(!thisfieldSanction.equals("")) { if(thisfieldNPA.equals(""))
		 * npaChk = false; else if(!thisfieldNPA.equals("")) {
		 * if(Double.parseDouble(thisfieldSanction) >
		 * Double.parseDouble(thisfieldNPA)) npaChk = false;
		 * if(thisfieldClmLodgemnt.equals("")) lodgementChk = false; else
		 * if(!thisfieldClmLodgemnt.equals("") &&
		 * Double.parseDouble(thisfieldNPA) >
		 * Double.parseDouble(thisfieldClmLodgemnt)) lodgementChk = false; } }
		 * else if(thisfieldSanction.equals("") && !thisfieldNPA.equals(""))
		 * if(thisfieldClmLodgemnt.equals("")) lodgementChk = false; else
		 * if(!thisfieldClmLodgemnt.equals("") && Integer.parseInt(thisfieldNPA)
		 * > Integer.parseInt(thisfieldClmLodgemnt)) lodgementChk = false;
		 * thisfieldSanction = ""; thisfieldNPA = ""; thisfieldClmLodgemnt = "";
		 * } else if(keySanction.equals("reasonReduction")) { thisfieldSanction
		 * = (String)claimForm.getAsOnDtOfSanctionDtl(keySanction); asOnNPADtls
		 * = claimForm.getAsOnDtOfNPA(); asOnNPADtlsSet = asOnNPADtls.keySet();
		 * for(asOnNPADtlsIterator = asOnNPADtlsSet.iterator();
		 * asOnNPADtlsIterator.hasNext();) { String keyNPA =
		 * (String)asOnNPADtlsIterator.next();
		 * if(keyNPA.equals("reasonReduction")) thisfieldNPA =
		 * (String)claimForm.getAsOnDtOfNPA(keyNPA); }
		 * 
		 * asOnLodgemntDtls = claimForm.getAsOnLodgemntOfCredit();
		 * asOnLodgemntDtlsSet = asOnLodgemntDtls.keySet();
		 * for(asOnLodgemntDtlsIterator = asOnLodgemntDtlsSet.iterator();
		 * asOnLodgemntDtlsIterator.hasNext();) { String keyClmLodgemnt =
		 * (String)asOnLodgemntDtlsIterator.next();
		 * if(keyClmLodgemnt.equals("reasonReduction")) thisfieldClmLodgemnt =
		 * (String)claimForm.getAsOnLodgemntOfCredit(keyClmLodgemnt); }
		 * 
		 * if(!sanctionChk && thisfieldSanction.equals("")) { actionError = new
		 * ActionError("reasonforreductionmsgsanction");
		 * errors.add("org.apache.struts.action.GLOBAL_ERROR", actionError); }
		 * if(!npaChk && thisfieldNPA.equals("")) { actionError = new
		 * ActionError("reasonforreductionmsgnpa");
		 * errors.add("org.apache.struts.action.GLOBAL_ERROR", actionError); }
		 * if(!lodgementChk && thisfieldClmLodgemnt.equals("")) { actionError =
		 * new ActionError("reasonforreductionmsgclmlodgmnt");
		 * errors.add("org.apache.struts.action.GLOBAL_ERROR", actionError); }
		 * if(thisfieldSanction.equals("") && thisfieldNPA.equals("") &&
		 * thisfieldClmLodgemnt.equals("")) isreasonforreductionfieldvalid =
		 * true; if(!thisfieldSanction.equals("") && !thisfieldNPA.equals("") &&
		 * !thisfieldClmLodgemnt.equals("")) isreasonforreductionfieldvalid =
		 * true; } } if(!isLandfieldvalid) { actionError = new
		 * ActionError("sapgforland");
		 * errors.add("org.apache.struts.action.GLOBAL_ERROR", actionError); }
		 * if(!isbldgfieldvalid) { actionError = new ActionError("sapgforbldg");
		 * errors.add("org.apache.struts.action.GLOBAL_ERROR", actionError); }
		 * if(!ismcfieldvalid) { actionError = new
		 * ActionError("sapgformachine");
		 * errors.add("org.apache.struts.action.GLOBAL_ERROR", actionError); }
		 * if(!isofmafieldvalid) { actionError = new ActionError("sapgforofma");
		 * errors.add("org.apache.struts.action.GLOBAL_ERROR", actionError); }
		 * if(!iscurrassetsvalid) { actionError = new
		 * ActionError("sapgforparticularassets");
		 * errors.add("org.apache.struts.action.GLOBAL_ERROR", actionError); }
		 * if(!isothersvalid) { actionError = new
		 * ActionError("sapgforotherassets");
		 * errors.add("org.apache.struts.action.GLOBAL_ERROR", actionError); }
		 * if(!isreasonforreductionfieldvalid) { actionError = new
		 * ActionError("sapgforreasonforreduction");
		 * errors.add("org.apache.struts.action.GLOBAL_ERROR", actionError); }
		 */
		return errors.isEmpty();
	}

	public static boolean validateSPGDetailsAsOnSecondLodgement(Object bean,
			ValidatorAction validAction, Field field, ActionErrors errors,
			HttpServletRequest request) {
		ActionError actionError = null;
		ClaimActionForm claimForm = (ClaimActionForm) bean;
		Map asOnDtOfSanctionDtls = claimForm.getAsOnDtOfSanctionDtl();
		Set asOnDtOfSanctionDtlsSet = asOnDtOfSanctionDtls.keySet();
		Iterator asOnDtOfSanctionDtlsIterator = asOnDtOfSanctionDtlsSet
				.iterator();
		Map asOnNPADtls = null;
		Set asOnNPADtlsSet = null;
		Iterator asOnNPADtlsIterator = null;
		Map asOnLodgemntDtls = null;
		Set asOnLodgemntDtlsSet = null;
		Iterator asOnLodgemntDtlsIterator = null;
		Map asOnDateOfLodgemntOfSecondClm = null;
		Set asOnDateOfLodgemntOfSecondClmSet = null;
		Iterator asOnDateOfLodgemntOfSecondClmIterator = null;
		Map amntRealizedThruDisposalOfSecurity = null;
		Set amntRealizedThruDisposalOfSecuritySet = null;
		Iterator amntRealizedThruDisposalOfSecIterator = null;
		boolean isNetWorthFieldValid = true;
		boolean isLandfieldvalid = false;
		boolean ismcfieldvalid = false;
		boolean isbldgfieldvalid = false;
		boolean isofmafieldvalid = false;
		boolean iscurrassetsvalid = false;
		boolean isothersvalid = false;
		boolean isreasonforreductionfieldvalid = false;
		while (asOnDtOfSanctionDtlsIterator.hasNext()) {
			String thisfieldSanction = "";
			String thisfieldNPA = "";
			String thisfieldClmLodgemnt = "";
			String thisfieldSecClmLodgemnt = "";
			String thisfieldAmntRealzedThruSec = "";
			String keySanction = (String) asOnDtOfSanctionDtlsIterator.next();
			if (keySanction.equals("LAND")) {
				thisfieldSanction = (String) claimForm
						.getAsOnDtOfSanctionDtl(keySanction);
				asOnNPADtls = claimForm.getAsOnDtOfNPA();
				asOnNPADtlsSet = asOnNPADtls.keySet();
				for (asOnNPADtlsIterator = asOnNPADtlsSet.iterator(); asOnNPADtlsIterator
						.hasNext();) {
					String keyNPA = (String) asOnNPADtlsIterator.next();
					if (keyNPA.equals("LAND"))
						thisfieldNPA = (String) claimForm
								.getAsOnDtOfNPA(keyNPA);
				}

				asOnLodgemntDtls = claimForm.getAsOnLodgemntOfCredit();
				asOnLodgemntDtlsSet = asOnLodgemntDtls.keySet();
				for (asOnLodgemntDtlsIterator = asOnLodgemntDtlsSet.iterator(); asOnLodgemntDtlsIterator
						.hasNext();) {
					String keyClmLodgemnt = (String) asOnLodgemntDtlsIterator
							.next();
					if (keyClmLodgemnt.equals("LAND"))
						thisfieldClmLodgemnt = (String) claimForm
								.getAsOnLodgemntOfCredit(keyClmLodgemnt);
				}

				asOnDateOfLodgemntOfSecondClm = claimForm
						.getAsOnDateOfLodgemntOfSecondClm();
				asOnDateOfLodgemntOfSecondClmSet = asOnDateOfLodgemntOfSecondClm
						.keySet();
				for (asOnDateOfLodgemntOfSecondClmIterator = asOnDateOfLodgemntOfSecondClmSet
						.iterator(); asOnDateOfLodgemntOfSecondClmIterator
						.hasNext();) {
					String keySecClmLodgemnt = (String) asOnDateOfLodgemntOfSecondClmIterator
							.next();
					if (keySecClmLodgemnt.equals("LAND"))
						thisfieldSecClmLodgemnt = (String) claimForm
								.getAsOnDateOfLodgemntOfSecondClm(keySecClmLodgemnt);
				}

				amntRealizedThruDisposalOfSecurity = claimForm
						.getAmntRealizedThruDisposalOfSecurity();
				amntRealizedThruDisposalOfSecuritySet = amntRealizedThruDisposalOfSecurity
						.keySet();
				for (amntRealizedThruDisposalOfSecIterator = amntRealizedThruDisposalOfSecuritySet
						.iterator(); amntRealizedThruDisposalOfSecIterator
						.hasNext();) {
					String amntRealzedThruSec = (String) amntRealizedThruDisposalOfSecIterator
							.next();
					if (amntRealzedThruSec.equals("LAND"))
						thisfieldAmntRealzedThruSec = (String) claimForm
								.getAmntRealizedThruDisposalOfSecurity(amntRealzedThruSec);
				}

				if (thisfieldSanction.equals("") && thisfieldNPA.equals("")
						&& thisfieldClmLodgemnt.equals("")
						&& thisfieldSecClmLodgemnt.equals("")
						&& thisfieldAmntRealzedThruSec.equals(""))
					isLandfieldvalid = true;
				else if (!thisfieldSanction.equals("")
						&& !thisfieldNPA.equals("")
						&& !thisfieldClmLodgemnt.equals("")
						&& !thisfieldSecClmLodgemnt.equals("")
						&& !thisfieldAmntRealzedThruSec.equals(""))
					isLandfieldvalid = true;
				thisfieldSanction = "";
				thisfieldNPA = "";
				thisfieldClmLodgemnt = "";
				thisfieldSecClmLodgemnt = "";
				thisfieldAmntRealzedThruSec = "";
			} else if (keySanction.equals("networth")) {
				thisfieldSanction = (String) claimForm
						.getAsOnDtOfSanctionDtl(keySanction);
				if (!thisfieldSanction.equals("")) {
					asOnNPADtls = claimForm.getAsOnDtOfNPA();
					asOnNPADtlsSet = asOnNPADtls.keySet();
					for (asOnNPADtlsIterator = asOnNPADtlsSet.iterator(); asOnNPADtlsIterator
							.hasNext();) {
						String keyNPA = (String) asOnNPADtlsIterator.next();
						if (keyNPA.equals("networth")) {
							thisfieldNPA = (String) claimForm
									.getAsOnDtOfNPA(keyNPA);
							if (thisfieldNPA.equals(""))
								isNetWorthFieldValid = false;
						}
					}

					asOnLodgemntDtls = claimForm.getAsOnLodgemntOfCredit();
					asOnLodgemntDtlsSet = asOnLodgemntDtls.keySet();
					for (asOnLodgemntDtlsIterator = asOnLodgemntDtlsSet
							.iterator(); asOnLodgemntDtlsIterator.hasNext();) {
						String keyClmLodgemnt = (String) asOnLodgemntDtlsIterator
								.next();
						if (keyClmLodgemnt.equals("networth")) {
							thisfieldClmLodgemnt = (String) claimForm
									.getAsOnLodgemntOfCredit(keyClmLodgemnt);
							if (thisfieldClmLodgemnt.equals(""))
								isNetWorthFieldValid = false;
						}
					}

					asOnDateOfLodgemntOfSecondClm = claimForm
							.getAsOnDateOfLodgemntOfSecondClm();
					asOnDateOfLodgemntOfSecondClmSet = asOnDateOfLodgemntOfSecondClm
							.keySet();
					for (asOnDateOfLodgemntOfSecondClmIterator = asOnDateOfLodgemntOfSecondClmSet
							.iterator(); asOnDateOfLodgemntOfSecondClmIterator
							.hasNext();) {
						String keySecClmLodgemnt = (String) asOnDateOfLodgemntOfSecondClmIterator
								.next();
						if (keySecClmLodgemnt.equals("networth")) {
							thisfieldSecClmLodgemnt = (String) claimForm
									.getAsOnDateOfLodgemntOfSecondClm(keySecClmLodgemnt);
							if (thisfieldSecClmLodgemnt.equals(""))
								isNetWorthFieldValid = false;
						}
					}

				} else {
					isNetWorthFieldValid = false;
				}
				thisfieldSanction = "";
				thisfieldNPA = "";
				thisfieldClmLodgemnt = "";
				thisfieldSecClmLodgemnt = "";
			} else if (keySanction.equals("BUILDING")) {
				thisfieldSanction = (String) claimForm
						.getAsOnDtOfSanctionDtl(keySanction);
				asOnNPADtls = claimForm.getAsOnDtOfNPA();
				asOnNPADtlsSet = asOnNPADtls.keySet();
				for (asOnNPADtlsIterator = asOnNPADtlsSet.iterator(); asOnNPADtlsIterator
						.hasNext();) {
					String keyNPA = (String) asOnNPADtlsIterator.next();
					if (keyNPA.equals("BUILDING"))
						thisfieldNPA = (String) claimForm
								.getAsOnDtOfNPA(keyNPA);
				}

				asOnLodgemntDtls = claimForm.getAsOnLodgemntOfCredit();
				asOnLodgemntDtlsSet = asOnLodgemntDtls.keySet();
				for (asOnLodgemntDtlsIterator = asOnLodgemntDtlsSet.iterator(); asOnLodgemntDtlsIterator
						.hasNext();) {
					String keyClmLodgemnt = (String) asOnLodgemntDtlsIterator
							.next();
					if (keyClmLodgemnt.equals("BUILDING"))
						thisfieldClmLodgemnt = (String) claimForm
								.getAsOnLodgemntOfCredit(keyClmLodgemnt);
				}

				asOnDateOfLodgemntOfSecondClm = claimForm
						.getAsOnDateOfLodgemntOfSecondClm();
				asOnDateOfLodgemntOfSecondClmSet = asOnDateOfLodgemntOfSecondClm
						.keySet();
				for (asOnDateOfLodgemntOfSecondClmIterator = asOnDateOfLodgemntOfSecondClmSet
						.iterator(); asOnDateOfLodgemntOfSecondClmIterator
						.hasNext();) {
					String keySecClmLodgemnt = (String) asOnDateOfLodgemntOfSecondClmIterator
							.next();
					if (keySecClmLodgemnt.equals("BUILDING"))
						thisfieldSecClmLodgemnt = (String) claimForm
								.getAsOnDateOfLodgemntOfSecondClm(keySecClmLodgemnt);
				}

				amntRealizedThruDisposalOfSecurity = claimForm
						.getAmntRealizedThruDisposalOfSecurity();
				amntRealizedThruDisposalOfSecuritySet = amntRealizedThruDisposalOfSecurity
						.keySet();
				for (amntRealizedThruDisposalOfSecIterator = amntRealizedThruDisposalOfSecuritySet
						.iterator(); amntRealizedThruDisposalOfSecIterator
						.hasNext();) {
					String amntRealzedThruSec = (String) amntRealizedThruDisposalOfSecIterator
							.next();
					if (amntRealzedThruSec.equals("BUILDING"))
						thisfieldAmntRealzedThruSec = (String) claimForm
								.getAmntRealizedThruDisposalOfSecurity(amntRealzedThruSec);
				}

				if (thisfieldSanction.equals("") && thisfieldNPA.equals("")
						&& thisfieldClmLodgemnt.equals("")
						&& thisfieldSecClmLodgemnt.equals("")
						&& thisfieldAmntRealzedThruSec.equals(""))
					isbldgfieldvalid = true;
				else if (!thisfieldSanction.equals("")
						&& !thisfieldNPA.equals("")
						&& !thisfieldClmLodgemnt.equals("")
						&& !thisfieldSecClmLodgemnt.equals("")
						&& !thisfieldAmntRealzedThruSec.equals(""))
					isbldgfieldvalid = true;
				thisfieldSanction = "";
				thisfieldNPA = "";
				thisfieldClmLodgemnt = "";
				thisfieldSecClmLodgemnt = "";
				thisfieldAmntRealzedThruSec = "";
			} else if (keySanction.equals("MACHINE")) {
				thisfieldSanction = (String) claimForm
						.getAsOnDtOfSanctionDtl(keySanction);
				asOnNPADtls = claimForm.getAsOnDtOfNPA();
				asOnNPADtlsSet = asOnNPADtls.keySet();
				for (asOnNPADtlsIterator = asOnNPADtlsSet.iterator(); asOnNPADtlsIterator
						.hasNext();) {
					String keyNPA = (String) asOnNPADtlsIterator.next();
					if (keyNPA.equals("MACHINE"))
						thisfieldNPA = (String) claimForm
								.getAsOnDtOfNPA(keyNPA);
				}

				asOnLodgemntDtls = claimForm.getAsOnLodgemntOfCredit();
				asOnLodgemntDtlsSet = asOnLodgemntDtls.keySet();
				for (asOnLodgemntDtlsIterator = asOnLodgemntDtlsSet.iterator(); asOnLodgemntDtlsIterator
						.hasNext();) {
					String keyClmLodgemnt = (String) asOnLodgemntDtlsIterator
							.next();
					if (keyClmLodgemnt.equals("MACHINE"))
						thisfieldClmLodgemnt = (String) claimForm
								.getAsOnLodgemntOfCredit(keyClmLodgemnt);
				}

				asOnDateOfLodgemntOfSecondClm = claimForm
						.getAsOnDateOfLodgemntOfSecondClm();
				asOnDateOfLodgemntOfSecondClmSet = asOnDateOfLodgemntOfSecondClm
						.keySet();
				for (asOnDateOfLodgemntOfSecondClmIterator = asOnDateOfLodgemntOfSecondClmSet
						.iterator(); asOnDateOfLodgemntOfSecondClmIterator
						.hasNext();) {
					String keySecClmLodgemnt = (String) asOnDateOfLodgemntOfSecondClmIterator
							.next();
					if (keySecClmLodgemnt.equals("MACHINE"))
						thisfieldSecClmLodgemnt = (String) claimForm
								.getAsOnDateOfLodgemntOfSecondClm(keySecClmLodgemnt);
				}

				amntRealizedThruDisposalOfSecurity = claimForm
						.getAmntRealizedThruDisposalOfSecurity();
				amntRealizedThruDisposalOfSecuritySet = amntRealizedThruDisposalOfSecurity
						.keySet();
				for (amntRealizedThruDisposalOfSecIterator = amntRealizedThruDisposalOfSecuritySet
						.iterator(); amntRealizedThruDisposalOfSecIterator
						.hasNext();) {
					String amntRealzedThruSec = (String) amntRealizedThruDisposalOfSecIterator
							.next();
					if (amntRealzedThruSec.equals("MACHINE"))
						thisfieldAmntRealzedThruSec = (String) claimForm
								.getAmntRealizedThruDisposalOfSecurity(amntRealzedThruSec);
				}

				if (thisfieldSanction.equals("") && thisfieldNPA.equals("")
						&& thisfieldClmLodgemnt.equals("")
						&& thisfieldSecClmLodgemnt.equals("")
						&& thisfieldAmntRealzedThruSec.equals(""))
					ismcfieldvalid = true;
				else if (!thisfieldSanction.equals("")
						&& !thisfieldNPA.equals("")
						&& !thisfieldClmLodgemnt.equals("")
						&& !thisfieldSecClmLodgemnt.equals("")
						&& !thisfieldAmntRealzedThruSec.equals(""))
					ismcfieldvalid = true;
				thisfieldSanction = "";
				thisfieldNPA = "";
				thisfieldClmLodgemnt = "";
				thisfieldSecClmLodgemnt = "";
				thisfieldAmntRealzedThruSec = "";
			} else if (keySanction.equals("OTHER FIXED MOVABLE ASSETS")) {
				thisfieldSanction = (String) claimForm
						.getAsOnDtOfSanctionDtl(keySanction);
				asOnNPADtls = claimForm.getAsOnDtOfNPA();
				asOnNPADtlsSet = asOnNPADtls.keySet();
				for (asOnNPADtlsIterator = asOnNPADtlsSet.iterator(); asOnNPADtlsIterator
						.hasNext();) {
					String keyNPA = (String) asOnNPADtlsIterator.next();
					if (keyNPA.equals("OTHER FIXED MOVABLE ASSETS"))
						thisfieldNPA = (String) claimForm
								.getAsOnDtOfNPA(keyNPA);
				}

				asOnLodgemntDtls = claimForm.getAsOnLodgemntOfCredit();
				asOnLodgemntDtlsSet = asOnLodgemntDtls.keySet();
				for (asOnLodgemntDtlsIterator = asOnLodgemntDtlsSet.iterator(); asOnLodgemntDtlsIterator
						.hasNext();) {
					String keyClmLodgemnt = (String) asOnLodgemntDtlsIterator
							.next();
					if (keyClmLodgemnt.equals("OTHER FIXED MOVABLE ASSETS"))
						thisfieldClmLodgemnt = (String) claimForm
								.getAsOnLodgemntOfCredit(keyClmLodgemnt);
				}

				asOnDateOfLodgemntOfSecondClm = claimForm
						.getAsOnDateOfLodgemntOfSecondClm();
				asOnDateOfLodgemntOfSecondClmSet = asOnDateOfLodgemntOfSecondClm
						.keySet();
				for (asOnDateOfLodgemntOfSecondClmIterator = asOnDateOfLodgemntOfSecondClmSet
						.iterator(); asOnDateOfLodgemntOfSecondClmIterator
						.hasNext();) {
					String keySecClmLodgemnt = (String) asOnDateOfLodgemntOfSecondClmIterator
							.next();
					if (keySecClmLodgemnt.equals("OTHER FIXED MOVABLE ASSETS"))
						thisfieldSecClmLodgemnt = (String) claimForm
								.getAsOnDateOfLodgemntOfSecondClm(keySecClmLodgemnt);
				}

				amntRealizedThruDisposalOfSecurity = claimForm
						.getAmntRealizedThruDisposalOfSecurity();
				amntRealizedThruDisposalOfSecuritySet = amntRealizedThruDisposalOfSecurity
						.keySet();
				for (amntRealizedThruDisposalOfSecIterator = amntRealizedThruDisposalOfSecuritySet
						.iterator(); amntRealizedThruDisposalOfSecIterator
						.hasNext();) {
					String amntRealzedThruSec = (String) amntRealizedThruDisposalOfSecIterator
							.next();
					if (amntRealzedThruSec.equals("OTHER FIXED MOVABLE ASSETS"))
						thisfieldAmntRealzedThruSec = (String) claimForm
								.getAmntRealizedThruDisposalOfSecurity(amntRealzedThruSec);
				}

				if (thisfieldSanction.equals("") && thisfieldNPA.equals("")
						&& thisfieldClmLodgemnt.equals("")
						&& thisfieldSecClmLodgemnt.equals("")
						&& thisfieldAmntRealzedThruSec.equals(""))
					isofmafieldvalid = true;
				else if (!thisfieldSanction.equals("")
						&& !thisfieldNPA.equals("")
						&& !thisfieldClmLodgemnt.equals("")
						&& !thisfieldSecClmLodgemnt.equals("")
						&& !thisfieldAmntRealzedThruSec.equals(""))
					isofmafieldvalid = true;
				thisfieldSanction = "";
				thisfieldNPA = "";
				thisfieldClmLodgemnt = "";
				thisfieldSecClmLodgemnt = "";
				thisfieldAmntRealzedThruSec = "";
			} else if (keySanction.equals("CURRENT ASSETS")) {
				thisfieldSanction = (String) claimForm
						.getAsOnDtOfSanctionDtl(keySanction);
				asOnNPADtls = claimForm.getAsOnDtOfNPA();
				asOnNPADtlsSet = asOnNPADtls.keySet();
				for (asOnNPADtlsIterator = asOnNPADtlsSet.iterator(); asOnNPADtlsIterator
						.hasNext();) {
					String keyNPA = (String) asOnNPADtlsIterator.next();
					if (keyNPA.equals("CURRENT ASSETS")) {
						thisfieldNPA = (String) claimForm
								.getAsOnDtOfNPA(keyNPA);
						if (thisfieldNPA.equals(""))
							iscurrassetsvalid = false;
					}
				}

				asOnLodgemntDtls = claimForm.getAsOnLodgemntOfCredit();
				asOnLodgemntDtlsSet = asOnLodgemntDtls.keySet();
				for (asOnLodgemntDtlsIterator = asOnLodgemntDtlsSet.iterator(); asOnLodgemntDtlsIterator
						.hasNext();) {
					String keyClmLodgemnt = (String) asOnLodgemntDtlsIterator
							.next();
					if (keyClmLodgemnt.equals("CURRENT ASSETS")) {
						thisfieldClmLodgemnt = (String) claimForm
								.getAsOnLodgemntOfCredit(keyClmLodgemnt);
						if (thisfieldClmLodgemnt.equals(""))
							iscurrassetsvalid = false;
					}
				}

				asOnDateOfLodgemntOfSecondClm = claimForm
						.getAsOnDateOfLodgemntOfSecondClm();
				asOnDateOfLodgemntOfSecondClmSet = asOnDateOfLodgemntOfSecondClm
						.keySet();
				for (asOnDateOfLodgemntOfSecondClmIterator = asOnDateOfLodgemntOfSecondClmSet
						.iterator(); asOnDateOfLodgemntOfSecondClmIterator
						.hasNext();) {
					String keySecClmLodgemnt = (String) asOnDateOfLodgemntOfSecondClmIterator
							.next();
					if (keySecClmLodgemnt.equals("CURRENT ASSETS"))
						thisfieldSecClmLodgemnt = (String) claimForm
								.getAsOnDateOfLodgemntOfSecondClm(keySecClmLodgemnt);
				}

				amntRealizedThruDisposalOfSecurity = claimForm
						.getAmntRealizedThruDisposalOfSecurity();
				amntRealizedThruDisposalOfSecuritySet = amntRealizedThruDisposalOfSecurity
						.keySet();
				for (amntRealizedThruDisposalOfSecIterator = amntRealizedThruDisposalOfSecuritySet
						.iterator(); amntRealizedThruDisposalOfSecIterator
						.hasNext();) {
					String amntRealzedThruSec = (String) amntRealizedThruDisposalOfSecIterator
							.next();
					if (amntRealzedThruSec.equals("CURRENT ASSETS"))
						thisfieldAmntRealzedThruSec = (String) claimForm
								.getAmntRealizedThruDisposalOfSecurity(amntRealzedThruSec);
				}

				if (thisfieldSanction.equals("") && thisfieldNPA.equals("")
						&& thisfieldClmLodgemnt.equals("")
						&& thisfieldSecClmLodgemnt.equals("")
						&& thisfieldAmntRealzedThruSec.equals(""))
					iscurrassetsvalid = true;
				else if (!thisfieldSanction.equals("")
						&& !thisfieldNPA.equals("")
						&& !thisfieldClmLodgemnt.equals("")
						&& !thisfieldSecClmLodgemnt.equals("")
						&& !thisfieldAmntRealzedThruSec.equals(""))
					iscurrassetsvalid = true;
				thisfieldSanction = "";
				thisfieldNPA = "";
				thisfieldClmLodgemnt = "";
				thisfieldSecClmLodgemnt = "";
				thisfieldAmntRealzedThruSec = "";
			} else if (keySanction.equals("OTHERS")) {
				thisfieldSanction = (String) claimForm
						.getAsOnDtOfSanctionDtl(keySanction);
				asOnNPADtls = claimForm.getAsOnDtOfNPA();
				asOnNPADtlsSet = asOnNPADtls.keySet();
				for (asOnNPADtlsIterator = asOnNPADtlsSet.iterator(); asOnNPADtlsIterator
						.hasNext();) {
					String keyNPA = (String) asOnNPADtlsIterator.next();
					if (keyNPA.equals("OTHERS"))
						thisfieldNPA = (String) claimForm
								.getAsOnDtOfNPA(keyNPA);
				}

				asOnLodgemntDtls = claimForm.getAsOnLodgemntOfCredit();
				asOnLodgemntDtlsSet = asOnLodgemntDtls.keySet();
				for (asOnLodgemntDtlsIterator = asOnLodgemntDtlsSet.iterator(); asOnLodgemntDtlsIterator
						.hasNext();) {
					String keyClmLodgemnt = (String) asOnLodgemntDtlsIterator
							.next();
					if (keyClmLodgemnt.equals("OTHERS"))
						thisfieldClmLodgemnt = (String) claimForm
								.getAsOnLodgemntOfCredit(keyClmLodgemnt);
				}

				asOnDateOfLodgemntOfSecondClm = claimForm
						.getAsOnDateOfLodgemntOfSecondClm();
				asOnDateOfLodgemntOfSecondClmSet = asOnDateOfLodgemntOfSecondClm
						.keySet();
				for (asOnDateOfLodgemntOfSecondClmIterator = asOnDateOfLodgemntOfSecondClmSet
						.iterator(); asOnDateOfLodgemntOfSecondClmIterator
						.hasNext();) {
					String keySecClmLodgemnt = (String) asOnDateOfLodgemntOfSecondClmIterator
							.next();
					if (keySecClmLodgemnt.equals("OTHERS"))
						thisfieldSecClmLodgemnt = (String) claimForm
								.getAsOnDateOfLodgemntOfSecondClm(keySecClmLodgemnt);
				}

				amntRealizedThruDisposalOfSecurity = claimForm
						.getAmntRealizedThruDisposalOfSecurity();
				amntRealizedThruDisposalOfSecuritySet = amntRealizedThruDisposalOfSecurity
						.keySet();
				for (amntRealizedThruDisposalOfSecIterator = amntRealizedThruDisposalOfSecuritySet
						.iterator(); amntRealizedThruDisposalOfSecIterator
						.hasNext();) {
					String amntRealzedThruSec = (String) amntRealizedThruDisposalOfSecIterator
							.next();
					if (amntRealzedThruSec.equals("OTHERS"))
						thisfieldAmntRealzedThruSec = (String) claimForm
								.getAmntRealizedThruDisposalOfSecurity(amntRealzedThruSec);
				}

				if (thisfieldSanction.equals("") && thisfieldNPA.equals("")
						&& thisfieldClmLodgemnt.equals("")
						&& thisfieldSecClmLodgemnt.equals("")
						&& thisfieldAmntRealzedThruSec.equals(""))
					isothersvalid = true;
				else if (!thisfieldSanction.equals("")
						&& !thisfieldNPA.equals("")
						&& !thisfieldClmLodgemnt.equals("")
						&& !thisfieldSecClmLodgemnt.equals("")
						&& !thisfieldAmntRealzedThruSec.equals(""))
					isothersvalid = true;
				thisfieldSanction = "";
				thisfieldNPA = "";
				thisfieldClmLodgemnt = "";
				thisfieldSecClmLodgemnt = "";
				thisfieldAmntRealzedThruSec = "";
			} else if (keySanction.equals("reasonReduction")) {
				thisfieldSanction = (String) claimForm
						.getAsOnDtOfSanctionDtl(keySanction);
				asOnNPADtls = claimForm.getAsOnDtOfNPA();
				asOnNPADtlsSet = asOnNPADtls.keySet();
				for (asOnNPADtlsIterator = asOnNPADtlsSet.iterator(); asOnNPADtlsIterator
						.hasNext();) {
					String keyNPA = (String) asOnNPADtlsIterator.next();
					if (keyNPA.equals("reasonReduction"))
						thisfieldNPA = (String) claimForm
								.getAsOnDtOfNPA(keyNPA);
				}

				asOnLodgemntDtls = claimForm.getAsOnLodgemntOfCredit();
				asOnLodgemntDtlsSet = asOnLodgemntDtls.keySet();
				for (asOnLodgemntDtlsIterator = asOnLodgemntDtlsSet.iterator(); asOnLodgemntDtlsIterator
						.hasNext();) {
					String keyClmLodgemnt = (String) asOnLodgemntDtlsIterator
							.next();
					if (keyClmLodgemnt.equals("reasonReduction"))
						thisfieldClmLodgemnt = (String) claimForm
								.getAsOnLodgemntOfCredit(keyClmLodgemnt);
				}

				asOnDateOfLodgemntOfSecondClm = claimForm
						.getAsOnDateOfLodgemntOfSecondClm();
				asOnDateOfLodgemntOfSecondClmSet = asOnDateOfLodgemntOfSecondClm
						.keySet();
				for (asOnDateOfLodgemntOfSecondClmIterator = asOnDateOfLodgemntOfSecondClmSet
						.iterator(); asOnDateOfLodgemntOfSecondClmIterator
						.hasNext();) {
					String keySecClmLodgemnt = (String) asOnDateOfLodgemntOfSecondClmIterator
							.next();
					if (keySanction.equals("LAND")
							&& keySecClmLodgemnt.equals("reasonReduction#LAND"))
						thisfieldSecClmLodgemnt = (String) claimForm
								.getAsOnDateOfLodgemntOfSecondClm(keySecClmLodgemnt);
					else if (keySanction.equals("BUILDING")
							&& keySecClmLodgemnt
									.equals("reasonReduction#BUILDING"))
						thisfieldSecClmLodgemnt = (String) claimForm
								.getAsOnDateOfLodgemntOfSecondClm(keySecClmLodgemnt);
					else if (keySanction.equals("MACHINE")
							&& keySecClmLodgemnt
									.equals("reasonReduction#MACHINE"))
						thisfieldSecClmLodgemnt = (String) claimForm
								.getAsOnDateOfLodgemntOfSecondClm(keySecClmLodgemnt);
					else if (keySanction.equals("OTHER FIXED MOVABLE ASSETS")
							&& keySecClmLodgemnt
									.equals("reasonReduction#OTHER FIXED MOVABLE ASSETS"))
						thisfieldSecClmLodgemnt = (String) claimForm
								.getAsOnDateOfLodgemntOfSecondClm(keySecClmLodgemnt);
					else if (keySanction.equals("CURRENT ASSETS")
							&& keySecClmLodgemnt
									.equals("reasonReduction#CURRENT ASSETS"))
						thisfieldSecClmLodgemnt = (String) claimForm
								.getAsOnDateOfLodgemntOfSecondClm(keySecClmLodgemnt);
					else if (keySanction.equals("OTHERS")
							&& keySecClmLodgemnt
									.equals("reasonReduction#OTHERS"))
						thisfieldSecClmLodgemnt = (String) claimForm
								.getAsOnDateOfLodgemntOfSecondClm(keySecClmLodgemnt);
				}

				if (thisfieldSanction.equals("") && thisfieldNPA.equals("")
						&& thisfieldClmLodgemnt.equals("")
						&& thisfieldSecClmLodgemnt.equals(""))
					isreasonforreductionfieldvalid = true;
				if (!thisfieldSanction.equals("") && !thisfieldNPA.equals("")
						&& !thisfieldClmLodgemnt.equals("")
						&& !thisfieldSecClmLodgemnt.equals(""))
					isreasonforreductionfieldvalid = true;
			}
		}
		if (!isLandfieldvalid) {
			actionError = new ActionError("sapgforlandSecClm");
			errors.add("org.apache.struts.action.GLOBAL_ERROR", actionError);
		}
		if (!isbldgfieldvalid) {
			actionError = new ActionError("sapgforbldgSecClm");
			errors.add("org.apache.struts.action.GLOBAL_ERROR", actionError);
		}
		if (!ismcfieldvalid) {
			actionError = new ActionError("sapgformachineSecClm");
			errors.add("org.apache.struts.action.GLOBAL_ERROR", actionError);
		}
		if (!isofmafieldvalid) {
			actionError = new ActionError("sapgforofmaSecClm");
			errors.add("org.apache.struts.action.GLOBAL_ERROR", actionError);
		}
		if (!iscurrassetsvalid) {
			actionError = new ActionError("sapgforparcurrSecClm");
			errors.add("org.apache.struts.action.GLOBAL_ERROR", actionError);
		}
		if (!isothersvalid) {
			actionError = new ActionError("sapgforOthersSecClm");
			errors.add("org.apache.struts.action.GLOBAL_ERROR", actionError);
		}
		return errors.isEmpty();
	}

	public static boolean validateClaimAmounts(Object bean,
			ValidatorAction validAction, Field field, ActionErrors errors,
			HttpServletRequest request) {
		ActionError actionError = null;
		ClaimActionForm claimForm = (ClaimActionForm) bean;
		Map claimSummaryDtls = claimForm.getClaimSummaryDetails();
		Set claimSummaryDtlsSet = claimSummaryDtls.keySet();
		Iterator claimSummaryDtlsIterator = claimSummaryDtlsSet.iterator();
		boolean isValueValid = true;
		double value = 0.0D;
		int counter;
		for (counter = 0; claimSummaryDtlsIterator.hasNext(); counter++) {
			String key = (String) claimSummaryDtlsIterator.next();
			String thisfield = (String) claimForm.getClaimSummaryDetails(key);
			if (thisfield != null && thisfield.equals("")) {
				isValueValid = false;
				break;
			}
			if (thisfield == null || thisfield.equals(""))
				continue;
			try {
				double val = Double.parseDouble(thisfield);
				if (val <= 0.0D)
					break;
				continue;
			} catch (Exception e) {
				e.printStackTrace();
			}
			break;
		}

		if (counter == 0) {
			actionError = new ActionError("invalidclaimamntmsg");
			errors.add("org.apache.struts.action.GLOBAL_ERROR", actionError);
		} else if (counter > 0 && !isValueValid) {
			actionError = new ActionError("invalidclaimamntmsg");
			errors.add("org.apache.struts.action.GLOBAL_ERROR", actionError);
		}
		return errors.isEmpty();
	}

	public static boolean validateBorrowerIdOrCgpan(Object bean,
			ValidatorAction validAction, Field field, ActionErrors errors,
			HttpServletRequest request) {
		HttpSession session = request.getSession(false);
		ClaimActionForm claimForm = (ClaimActionForm) bean;
		ActionError actionError = null;
		String memid = null;
		String borrowerid = null;
		String cgpan = null;
		String srcmenu = (String) session.getAttribute("mainMenu");
		if (srcmenu.equals(MenuOptions.getMenu("CP_CLAIM_FOR"))
				|| srcmenu.equals(MenuOptions.getMenu("CP_OTS"))
				|| srcmenu.equals(MenuOptions.getMenu("CP_EXPORT_CLAIM_FILE"))) {
			memid = claimForm.getMemberId();
			borrowerid = claimForm.getBorrowerID();
			cgpan = claimForm.getCgpan();
		}
		if (srcmenu.equals(MenuOptions.getMenu("GM_PERIODIC_INFO"))) {
			memid = (String) session.getAttribute("MEMBERID");
			borrowerid = (String) session.getAttribute("BORROWERID");
			claimForm.setMemberId(memid);
			claimForm.setBorrowerID(borrowerid);
		}
		if (memid == null || memid.equals("")) {
			actionError = new ActionError("memreqd");
			errors.add("org.apache.struts.action.GLOBAL_ERROR", actionError);
		}
		if ((borrowerid == null || borrowerid.equals(""))
				&& (cgpan == null || cgpan.equals(""))) {
			actionError = new ActionError("enterbidorcgpan");
			errors.add("org.apache.struts.action.GLOBAL_ERROR", actionError);
		}
		return errors.isEmpty();
	}

	public static boolean validateOTSAmounts(Object bean,
			ValidatorAction validAction, Field field, ActionErrors errors,
			HttpServletRequest request) {
		ClaimActionForm claimForm = (ClaimActionForm) bean;
		Map proposedAmntsPaidByBorrower = claimForm
				.getProposedAmntPaidByBorrower();
		Set proposedAmntsPaidByBorrowerSet = proposedAmntsPaidByBorrower
				.keySet();
		Iterator proposedAmntsPaidByBorrowerIterator = proposedAmntsPaidByBorrowerSet
				.iterator();
		Map proposedAmntsSacrificed = claimForm.getProposedAmntSacrificed();
		Set proposedAmntsSacrificedSet = proposedAmntsSacrificed.keySet();
		Iterator proposedAmntsSacrificedIterator = proposedAmntsSacrificedSet
				.iterator();
		Map osAmntsOnDateForOTS = claimForm.getOsAmntOnDateForOTS();
		Set osAmntsOnDateForOTSSet = osAmntsOnDateForOTS.keySet();
		Iterator osAmntsOnDateForOTSIterator = osAmntsOnDateForOTSSet
				.iterator();
		boolean areFieldsValid = false;
		boolean proposedGreaterOs = false;
		boolean sacrificedAmntGreaterThanDiff = false;
		while (proposedAmntsPaidByBorrowerIterator.hasNext()) {
			areFieldsValid = false;
			proposedGreaterOs = false;
			double amount = 0.0D;
			String key = (String) proposedAmntsPaidByBorrowerIterator.next();
			String proposedValue = ((String) proposedAmntsPaidByBorrower
					.get(key)).trim();
			String sacrificedValue = ((String) proposedAmntsSacrificed.get(key))
					.trim();
			String osValue = ((String) osAmntsOnDateForOTS.get(key)).trim();
			if (proposedValue.equals("") && sacrificedValue.equals("")
					&& osValue.equals(""))
				areFieldsValid = true;
			if (!proposedValue.equals("") && !sacrificedValue.equals("")
					&& !osValue.equals("")) {
				areFieldsValid = true;
				double proposedVal = Double.parseDouble(proposedValue);
				double sacrificedVal = Double.parseDouble(sacrificedValue);
				double osVal = Double.parseDouble(osValue);
				if (proposedVal > osVal) {
					proposedGreaterOs = true;
					break;
				}
				if (osVal - proposedVal < sacrificedVal) {
					sacrificedAmntGreaterThanDiff = true;
					break;
				}
			}
			if (!areFieldsValid)
				break;
		}
		if (!areFieldsValid) {
			ActionError actionError = new ActionError("invalidOTSAmounts");
			errors.add("org.apache.struts.action.GLOBAL_ERROR", actionError);
		}
		if (proposedGreaterOs) {
			ActionError actionError = new ActionError(
					"proposedAmntGreaterThanOS");
			errors.add("org.apache.struts.action.GLOBAL_ERROR", actionError);
		}
		if (sacrificedAmntGreaterThanDiff) {
			ActionError actionError = new ActionError(
					"sacrificedAmntGreaterThanDiff");
			errors.add("org.apache.struts.action.GLOBAL_ERROR", actionError);
		}
		return errors.isEmpty();
	}

	public static boolean validateProposedAmntToBePaidByBorrower(Object bean,
			ValidatorAction validAction, Field field, ActionErrors errors,
			HttpServletRequest request) {
		ClaimActionForm claimForm = (ClaimActionForm) bean;
		Map proposedAmntsPaidByBorrower = claimForm
				.getProposedAmntPaidByBorrower();
		Set proposedAmntsPaidByBorrowerSet = proposedAmntsPaidByBorrower
				.keySet();
		Iterator proposedAmntsPaidByBorrowerIterator = proposedAmntsPaidByBorrowerSet
				.iterator();
		boolean proposedAmntToBePaidByBorrowerFlag = true;
		while (proposedAmntsPaidByBorrowerIterator.hasNext()) {
			double amount = 0.0D;
			String key = (String) proposedAmntsPaidByBorrowerIterator.next();
			String value = ((String) proposedAmntsPaidByBorrower.get(key))
					.trim();
			if (value.equals(""))
				proposedAmntToBePaidByBorrowerFlag = false;
			if (!proposedAmntToBePaidByBorrowerFlag) {
				ActionError actionError = new ActionError(
						"proposedamntpaidbyBorrower");
				errors.add("org.apache.struts.action.GLOBAL_ERROR", actionError);
				break;
			}
		}
		return errors.isEmpty();
	}

	public static boolean validateProposedAmntSacrificed(Object bean,
			ValidatorAction validAction, Field field, ActionErrors errors,
			HttpServletRequest request) {
		ClaimActionForm claimForm = (ClaimActionForm) bean;
		Map proposedAmntsSacrificed = claimForm.getProposedAmntSacrificed();
		Set proposedAmntsSacrificedSet = proposedAmntsSacrificed.keySet();
		Iterator proposedAmntsSacrificedIterator = proposedAmntsSacrificedSet
				.iterator();
		boolean proposedAmntSacrificedFlag = true;
		while (proposedAmntsSacrificedIterator.hasNext()) {
			double amount = 0.0D;
			String key = (String) proposedAmntsSacrificedIterator.next();
			String value = ((String) proposedAmntsSacrificed.get(key)).trim();
			if (value.equals(""))
				proposedAmntSacrificedFlag = false;
			if (!proposedAmntSacrificedFlag) {
				ActionError actionError = new ActionError(
						"proposedamttobesacrificed");
				errors.add("org.apache.struts.action.GLOBAL_ERROR", actionError);
				break;
			}
		}
		return errors.isEmpty();
	}

	public static boolean validateOsAmntOnDateForOTS(Object bean,
			ValidatorAction validAction, Field field, ActionErrors errors,
			HttpServletRequest request) {
		ClaimActionForm claimForm = (ClaimActionForm) bean;
		Map osAmntsOnDateForOTS = claimForm.getOsAmntOnDateForOTS();
		Set osAmntsOnDateForOTSSet = osAmntsOnDateForOTS.keySet();
		Iterator osAmntsOnDateForOTSIterator = osAmntsOnDateForOTSSet
				.iterator();
		boolean osAmntOnDateForOTSFlag = true;
		while (osAmntsOnDateForOTSIterator.hasNext()) {
			double amount = 0.0D;
			String key = (String) osAmntsOnDateForOTSIterator.next();
			String value = ((String) osAmntsOnDateForOTS.get(key)).trim();
			if (value.equals(""))
				osAmntOnDateForOTSFlag = false;
			if (!osAmntOnDateForOTSFlag) {
				ActionError actionError = new ActionError("osamntasondate");
				errors.add("org.apache.struts.action.GLOBAL_ERROR", actionError);
				break;
			}
		}
		return errors.isEmpty();
	}

	public static boolean validateRecoveryOrOTSFlag(Object bean,
			ValidatorAction validAction, Field field, ActionErrors errors,
			HttpServletRequest request) {
		ClaimActionForm claimForm = (ClaimActionForm) bean;
		String recoveryFlag = claimForm.getRecoveryFlag();
		String otsFlag = claimForm.getOtsFlag();
		if (recoveryFlag == null && otsFlag == null) {
			ActionError error = new ActionError("enteraflag");
			errors.add("org.apache.struts.action.GLOBAL_ERROR", error);
		}
		return errors.isEmpty();
	}

	public static boolean validateSaveOTSDetails(Object bean,
			ValidatorAction validAction, Field field, ActionErrors errors,
			HttpServletRequest request) {
		ActionError actionError = null;
		ClaimActionForm claimForm = (ClaimActionForm) bean;
		Map decisions = claimForm.getDecision();
		Set decisionset = decisions.keySet();
		Iterator decisionIterator = decisionset.iterator();
		boolean isDecisionFieldValid = true;
		boolean isRemarksFieldValid = true;
		boolean areFieldsValid = false;
		String decisionfield = null;
		String remarksfield = null;
		double thisvalue = 0.0D;
		int counter = 0;
		while (decisionIterator.hasNext()) {
			counter++;
			areFieldsValid = false;
			String key = (String) decisionIterator.next();
			decisionfield = (String) claimForm.getDecision(key);
			remarksfield = (String) claimForm.getRemarks(key);
			if (decisionfield.equals("") && remarksfield.equals(""))
				areFieldsValid = true;
			if (decisionfield.equals("AP") && remarksfield.equals(""))
				areFieldsValid = true;
			if (!decisionfield.equals("") && !remarksfield.equals(""))
				areFieldsValid = true;
			if (!areFieldsValid)
				break;
		}
		if (counter == 0)
			areFieldsValid = true;
		if (counter > 0 && !areFieldsValid) {
			actionError = new ActionError("invaliddecisionmsg");
			errors.add("org.apache.struts.action.GLOBAL_ERROR", actionError);
		}
		return errors.isEmpty();
	}

	public static boolean validateDisplayClaimApproval(Object bean,
			ValidatorAction validAction, Field field, ActionErrors errors,
			HttpServletRequest request) {
		double clmEligibleAmnt = 0.0D;
		Log.log(4, "Validator", "validateDisplayClaimApproval", "Entered");
		ClaimActionForm claimForm = (ClaimActionForm) bean;
		String flag = claimForm.getClmRefDtlSet();
		ClaimDetail clmdtl = claimForm.getClaimdetail();
		if (clmdtl != null)
			clmEligibleAmnt = clmdtl.getEligibleClaimAmt();
		String payAmntNow = claimForm.getTotalAmntPayableNow();
		Log.log(4, "Validator", "validateDisplayClaimApproval()",
				"*******************************");
		Log.log(4, "Validator", "validateDisplayClaimApproval()",
				(new StringBuilder()).append("clmdtl :").append(clmdtl)
						.toString());
		Log.log(4,
				"Validator",
				"validateDisplayClaimApproval()",
				(new StringBuilder()).append("clmEligibleAmnt :")
						.append(clmEligibleAmnt).toString());
		Log.log(4, "Validator", "validateDisplayClaimApproval()",
				(new StringBuilder()).append("payAmntNow :").append(payAmntNow)
						.toString());
		Log.log(4, "Validator", "validateDisplayClaimApproval()",
				"*******************************");
		Log.log(4, "Validator", "validateDisplayClaimApproval", "Exited");
		if (flag != null && flag.equals("Y") && payAmntNow != null
				&& !payAmntNow.equals("")
				&& Double.parseDouble(payAmntNow) > clmEligibleAmnt) {
			claimForm.setTotalAmntPayableNow(payAmntNow);
			ActionError actionError = new ActionError("invalidClmPayable");
			errors.add("org.apache.struts.action.GLOBAL_ERROR", actionError);
			Log.log(4,
					"Validator",
					"validateDisplayClaimApproval",
					(new StringBuilder())
							.append("claimForm.setTotalAmntPayableNow() :")
							.append(claimForm.getTotalAmntPayableNow())
							.toString());
			Log.log(4,
					"Validator",
					"validateDisplayClaimApproval",
					(new StringBuilder())
							.append("claimForm.getClmRefDtlSet() :")
							.append(claimForm.getClmRefDtlSet()).toString());
			return errors.isEmpty();
		} else {
			return true;
		}
	}

	public static boolean validateClaimProcessingDtls(Object bean,
			ValidatorAction validAction, Field field, ActionErrors errors,
			HttpServletRequest request) {
		Log.log(4, "Validator", "validateClaimProcessingDtls", "Entered");
		ActionError actionerror = null;
		ClaimActionForm claimForm = (ClaimActionForm) bean;
		Map approvedClaimAmounts = claimForm.getApprovedClaimAmount();
		Set approvedClaimAmountsSet = approvedClaimAmounts.keySet();
		Iterator approvedClaimAmountIterator = approvedClaimAmountsSet
				.iterator();
		boolean areFieldsValid = true;
		double apprvdClmAmnt = 0.0D;
		String decisionfield = null;
		String remarksfield = null;
		String clmamntfield = null;
		String forwardedToUser = null;
		int counter = 0;
		while (approvedClaimAmountIterator.hasNext()) {
			counter++;
			String key = (String) approvedClaimAmountIterator.next();
			decisionfield = (String) claimForm.getDecision(key);
		//	System.out.println((new StringBuilder()).append("decisionfield:")
			//		.append(decisionfield).toString());
			clmamntfield = (String) claimForm.getApprovedClaimAmount(key);
			Log.log(4,
					"Validator",
					"validateClaimProcessingDtls",
					(new StringBuilder()).append("clmamntfield :")
							.append(clmamntfield).toString());
			remarksfield = (String) claimForm.getRemarks(key);
			Log.log(4,
					"Validator",
					"validateClaimProcessingDtls",
					(new StringBuilder()).append("remarksfield :")
							.append(remarksfield).toString());
			forwardedToUser = (String) claimForm.getForwardedToIds(key);
			Log.log(4,
					"Validator",
					"validateClaimProcessingDtls",
					(new StringBuilder()).append("forwardedToUser :")
							.append(forwardedToUser).toString());
			Log.log(4, "Validator", "validateClaimProcessingDtls", "Control 1");
			if (decisionfield != null && decisionfield.equals("RE")
					&& !remarksfield.equals("") && clmamntfield.equals("")) {
				areFieldsValid = true;
				Log.log(4, "Validator", "validateClaimProcessingDtls",
						"Control 2");
			} else if (decisionfield != null && decisionfield.equals("HO")
					&& !remarksfield.equals("") && clmamntfield.equals("")) {
				areFieldsValid = true;
				Log.log(4, "Validator", "validateClaimProcessingDtls",
						"Control 3");
			} else if (decisionfield != null && decisionfield.equals("FW")
					&& !remarksfield.equals("") && clmamntfield.equals("")
					&& forwardedToUser != null && !forwardedToUser.equals("")) {
				areFieldsValid = true;
				Log.log(4, "Validator", "validateClaimProcessingDtls",
						"Control 4");
			} else if ((decisionfield == null || decisionfield != null
					&& decisionfield.equals(""))
					&& remarksfield.equals("") && clmamntfield.equals("")) {
				areFieldsValid = true;
				Log.log(4, "Validator", "validateClaimProcessingDtls",
						"Control 5");
			} else if (decisionfield != null && decisionfield.equals("AP")
					&& !clmamntfield.equals("")
					&& Double.parseDouble(clmamntfield) > 0.0D) {
				areFieldsValid = true;
				Log.log(4, "Validator", "validateClaimProcessingDtls",
						"Control 6");
			} else if (decisionfield != null && decisionfield.equals("")
					&& (!remarksfield.equals("") || !clmamntfield.equals(""))) {
				areFieldsValid = false;
				Log.log(4, "Validator", "validateClaimProcessingDtls",
						"Control 7");
			} else if (decisionfield != null && decisionfield.equals("TC")
					&& !remarksfield.equals("") && clmamntfield.equals("")) {
				areFieldsValid = true;
				Log.log(4, "Validator", "validateClaimProcessingDtls",
						"Control 8");
			} else if (decisionfield != null && decisionfield.equals("TC")
					&& remarksfield.equals("") && clmamntfield.equals("")) {
				areFieldsValid = true;
				Log.log(4, "Validator", "validateClaimProcessingDtls",
						"Control 8");
			} else if (decisionfield != null && decisionfield.equals("TR")
					&& !remarksfield.equals("") && clmamntfield.equals("")) {
				areFieldsValid = true;
				Log.log(4, "Validator", "validateClaimProcessingDtls",
						"Control 9");
			} else if (decisionfield != null && decisionfield.equals("WD")
					&& !remarksfield.equals("") && clmamntfield.equals("")) {
				areFieldsValid = true;
				Log.log(4, "Validator", "validateClaimProcessingDtls",
						"Control 10");
			} else {
				areFieldsValid = false;
				Log.log(4, "Validator", "validateClaimProcessingDtls",
						"Control 11");
			}
			if (!areFieldsValid) {
				Log.log(4, "Validator", "validateClaimProcessingDtls",
						"Control 12");
				actionerror = new ActionError("invalidapprvldecision");
				errors.add("org.apache.struts.action.GLOBAL_ERROR", actionerror);
				break;
			}
		}
		Log.log(4, "Validator", "validateClaimProcessingDtls", "Control 13");
		Log.log(4, "Validator", "validateClaimProcessingDtls", "Exited");
		return errors.isEmpty();
	}

	public static boolean validateLtrRefNoDtls(Object bean,
			ValidatorAction validAction, Field field, ActionErrors errors,
			HttpServletRequest request) {
		Log.log(4, "Validator", "validateLtrRefNoDtls", "Entered");
		ActionError actionerror = null;
		ClaimActionForm claimForm = (ClaimActionForm) bean;
		Map ltrRefNoSet = claimForm.getLtrRefNoSet();
		Set ltrRefNoList = ltrRefNoSet.keySet();
		Iterator ltrRefNoDtls = ltrRefNoList.iterator();
		boolean areFieldsValid = true;
		double apprvdClmAmnt = 0.0D;
		String ltrRefNo = null;
		String ltrDt = null;
		int counter = 0;
		while (ltrRefNoDtls.hasNext()) {
			counter++;
			String key = (String) ltrRefNoDtls.next();
			// ltrRefNo = (String)claimForm.getLtrRefNoSet(key);
			// ltrDt = (String)claimForm.getLtrDtSet(key);
			Log.log(4, "Validator", "validateLtrRefNoDtls",
					(new StringBuilder()).append("ltrDt :").append(ltrDt)
							.toString());
			if (ltrRefNo != null && !ltrRefNo.toString().equals("")) {
				areFieldsValid = true;
				Log.log(4, "Validator", "validateLtrRefNoDtls", "Control 1");
			} else {
				areFieldsValid = false;
				Log.log(4, "Validator", "validateLtrRefNoDtls", "Control 2");
			}
			if (ltrDt != null && !ltrDt.toString().equals("")) {
				areFieldsValid = true;
				Log.log(4, "Validator", "validateLtrRefNoDtls", "Control 1");
			} else {
				areFieldsValid = false;
				Log.log(4, "Validator", "validateLtrRefNoDtls", "Control 2");
			}
			if (!areFieldsValid) {
				Log.log(4, "Validator", "validateLtrRefNoDtls", "Control 3");
				actionerror = new ActionError("invalidLtrRefDtls");
				errors.add("org.apache.struts.action.GLOBAL_ERROR", actionerror);
				break;
			}
		}
		Log.log(4, "Validator", "validateLtrRefNoDtls", "Exited");
		return errors.isEmpty();
	}

	public static boolean validateSettlementDetails(Object bean,
			ValidatorAction validAction, Field field, ActionErrors errors,
			HttpServletRequest request) {
		Log.log(4, "CPDAO", "validateSettlementDetails", "Entered");
		ActionError error = null;
		ClaimActionForm claimForm = (ClaimActionForm) bean;
		Map settlementDetails = claimForm.getSettlementAmounts();
		Set settlementDetailsSet = settlementDetails.keySet();
		Iterator settlementDetailsIterator = settlementDetailsSet.iterator();
		SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
		Date date = null;
		String settlemntField = null;
		boolean areFieldsValid = true;
		double settlementAmnt = 0.0D;
		String settlemntDtField = null;
		String finalInstallmentFlag = null;
		StringTokenizer tokenizer = null;
		int count = 0;
		String dayField = null;
		String monthField = null;
		String yearField = null;
		while (settlementDetailsIterator.hasNext()) {
			boolean dayFieldRead = false;
			boolean monthFieldRead = false;
			boolean yearFieldRead = false;
			int day = -1;
			int month = -1;
			int year = -1;
			count++;
			String key = (String) settlementDetailsIterator.next();
			settlemntField = (String) claimForm.getSettlementAmounts(key);
			settlemntDtField = (String) claimForm.getSettlementDates(key);
			for (tokenizer = new StringTokenizer(settlemntDtField, "/"); tokenizer
					.hasMoreTokens();)
				if (!yearFieldRead)
					if (!monthFieldRead) {
						if (!dayFieldRead) {
							dayField = tokenizer.nextToken();
							dayFieldRead = true;
						} else {
							monthField = tokenizer.nextToken();
							monthFieldRead = true;
						}
					} else {
						yearField = tokenizer.nextToken();
						yearFieldRead = true;
					}

			finalInstallmentFlag = (String) claimForm
					.getFinalSettlementFlags(key);
			Log.log(4,
					"CPDAO",
					"validateSettlementDetails",
					(new StringBuilder()).append("1- settlemntField :")
							.append(settlemntField).toString());
			Log.log(4, "CPDAO", "validateSettlementDetails",
					(new StringBuilder()).append("1- settlemntDtField :")
							.append(settlemntDtField).toString());
			if ((settlemntField.equals("0") || settlemntField.equals("0.0") || settlemntField
					.equals("")) && settlemntDtField.equals("")) {
				Log.log(4, "CPDAO", "validateSettlementDetails",
						(new StringBuilder()).append("2- settlemntField :")
								.append(settlemntField).toString());
				areFieldsValid = true;
			} else if (!settlemntField.equals("0")
					&& !settlemntDtField.equals("")) {
				try {
					date = sdf.parse(settlemntDtField, new ParsePosition(0));
					Date currDate = new Date();
					if (date != null) {
						if (date.compareTo(currDate) > 0) {
							Log.log(4,
									"CPDAO",
									"validateSettlementDetails",
									(new StringBuilder())
											.append("3- settlemntField :")
											.append(settlemntField).toString());
							areFieldsValid = false;
						} else {
							Log.log(4,
									"CPDAO",
									"validateSettlementDetails",
									(new StringBuilder())
											.append("4- settlemntField :")
											.append(settlemntField).toString());
							areFieldsValid = true;
						}
					} else {
						areFieldsValid = false;
					}
					if (Double.parseDouble(settlemntField) < 0.0D) {
						Log.log(4,
								"CPDAO",
								"validateSettlementDetails",
								(new StringBuilder())
										.append("5- settlemntField :")
										.append(settlemntField).toString());
						areFieldsValid = false;
					}
				} catch (Exception exception) {
					Log.log(4, "CPDAO", "validateSettlementDetails",
							(new StringBuilder()).append("7- settlemntField :")
									.append(settlemntField).toString());
					exception.printStackTrace();
					areFieldsValid = false;
				}
			} else {
				Log.log(4, "CPDAO", "validateSettlementDetails",
						(new StringBuilder()).append("8- settlemntField :")
								.append(settlemntField).toString());
				areFieldsValid = false;
			}
			if (!areFieldsValid) {
				error = new ActionError("invalidsettlementamntmsg");
				errors.add("org.apache.struts.action.GLOBAL_ERROR", error);
				break;
			}
		}
		return errors.isEmpty();
	}

	public static boolean validateAllandSpecificFlag(Object bean,
			ValidatorAction validAction, Field field, ActionErrors errors,
			HttpServletRequest request) {
		ClaimActionForm claimForm = (ClaimActionForm) bean;
		String memberIdFlag = claimForm.getMemberIdFlag();
		String memberId = claimForm.getMemberId().trim();
		ActionError error = null;
		if (memberIdFlag.equals("")) {
			error = new ActionError("allorspecific");
			errors.add("org.apache.struts.action.GLOBAL_ERROR", error);
		} else if (memberIdFlag.equals("Specific") && memberId.equals("")) {
			error = new ActionError("specificnomemid");
			errors.add("org.apache.struts.action.GLOBAL_ERROR", error);
		}
		return errors.isEmpty();
	}

	public static boolean validateMemIdAndBid(Object bean,
			ValidatorAction validAction, Field field, ActionErrors errors,
			HttpServletRequest request) {
		GMActionForm gmActionForm = (GMActionForm) bean;
		HttpSession session = request.getSession(false);
		ActionError error = null;
		String memid = null;
		String bid = null;
		String srcmenu = (String) session.getAttribute("mainMenu");
		if (srcmenu.equals(MenuOptions.getMenu("CP_CLAIM_FOR"))) {
			memid = (String) session.getAttribute("MEMBERID");
			bid = (String) session.getAttribute("BORROWERID");
			gmActionForm.setMemberId(memid);
			gmActionForm.setBorrowerId(bid);
			if (memid.equals("") || bid.equals("")) {
				Log.log(4,
						"Validator",
						"validateMemIdAndBid",
						(new StringBuilder()).append("Member Id is :")
								.append(memid).toString());
				Log.log(4, "Validator", "validateMemIdAndBid",
						(new StringBuilder()).append("Borrower Id is :")
								.append(bid).toString());
				error = new ActionError("entermemidandbid");
				errors.add("org.apache.struts.action.GLOBAL_ERROR", error);
			}
		}
		if (srcmenu.equals(MenuOptions.getMenu("GM_PERIODIC_INFO")))
			validateBIdOrCgpanOrBName(bean, validAction, field, errors, request);
		Log.log(4, "Validator", "validateMemIdAndBid", "Exited");
		return errors.isEmpty();
	}

	public static boolean validateVoucherIds(Object bean,
			ValidatorAction validAction, Field field, ActionErrors errors,
			HttpServletRequest request) {
		ActionError error = null;
		String voucherId = null;
		ClaimsProcessor processor = new ClaimsProcessor();
		boolean areVouchersInDB = true;
		boolean isVoucherValid = true;
		Vector voucherIdsFromDB = null;
		try {
			voucherIdsFromDB = processor.getAllVoucherIds();
			for (int i = 0; i < voucherIdsFromDB.size(); i++)
				;
		} catch (DatabaseException dbex) {
		}
		if (voucherIdsFromDB == null) {
			areVouchersInDB = false;
			error = new ActionError("novoucheridsindb");
			errors.add("org.apache.struts.action.GLOBAL_ERROR", error);
			return errors.isEmpty();
		}
		if (voucherIdsFromDB.size() == 0) {
			areVouchersInDB = false;
			error = new ActionError("novoucheridsindb");
			errors.add("org.apache.struts.action.GLOBAL_ERROR", error);
			return errors.isEmpty();
		}
		ClaimActionForm claimForm = (ClaimActionForm) bean;
		Map paymentVoucherIds = claimForm.getPaymentVoucherIds();
		Set paymentVoucherIdsSet = paymentVoucherIds.keySet();
		for (Iterator paymentVoucherIdsIterator = paymentVoucherIdsSet
				.iterator(); paymentVoucherIdsIterator.hasNext();) {
			String key = (String) paymentVoucherIdsIterator.next();
			if (key == null) {
				isVoucherValid = false;
				break;
			}
			voucherId = (String) claimForm.getPaymentVoucherIds(key);
			if (voucherId == null || voucherId.equals("")) {
				isVoucherValid = false;
				break;
			}
			int lvoucherId = Integer.parseInt(voucherId);
			if (voucherIdsFromDB.contains(new Integer(lvoucherId)))
				isVoucherValid = true;
			else
				isVoucherValid = false;
		}

		if (!isVoucherValid) {
			error = new ActionError("invalidvoucherid");
			errors.add("org.apache.struts.action.GLOBAL_ERROR", error);
			return errors.isEmpty();
		} else {
			return errors.isEmpty();
		}
	}

	public static boolean validateIfZero(Object bean,
			ValidatorAction validAction, Field field, ActionErrors errors,
			HttpServletRequest request) {
		Log.log(4, "Validator", "validateIfZero", "Entered");
		String fieldValue = ValidatorUtil.getValueAsString(bean,
				field.getProperty());
		Log.log(4,
				"Validator",
				"validateIfZero",
				(new StringBuilder()).append("value entered -- ")
						.append(fieldValue).toString());
		if (!GenericValidator.isBlankOrNull(fieldValue)
				&& (fieldValue.equals("0") || Double.parseDouble(fieldValue) == 0.0D)) {
			Log.log(4, "Validator", "validateIfZero", "adding error message");
			ActionError actionMessage = new ActionError("zeroNotAllowed");
			errors.add(field.getKey(),
					Resources.getActionError(request, validAction, field));
		}
		Log.log(4, "Validator", "validateIfZero", "Exited");
		return errors.isEmpty();
	}

	public static boolean validateAppFilingLimitRule(Object bean,
			ValidatorAction validAction, Field field, ActionErrors errors,
			HttpServletRequest request) {
		Log.log(4, "Validator", "validateAppFilingLimitRule", "Entered");
		String rule = ValidatorUtil.getValueAsString(bean, field.getProperty());
		Log.log(4, "Validator", "validateAppFilingLimitRule",
				(new StringBuilder()).append("filing rule -- ").append(rule)
						.toString());
		if (rule.equals("Days")) {
			String limit = field.getVarValue("days");
			String limitValue = ValidatorUtil.getValueAsString(bean, limit);
			if (limitValue.equals("0")) {
				Log.log(4, "Validator", "validateAppFilingLimitRule",
						"No of days is zero");
				ActionError actionMessage = new ActionError(
						"zeroAppFilingDaysNotAllowed");
				errors.add("org.apache.struts.action.GLOBAL_ERROR",
						actionMessage);
			}
		} else if (rule.equals("Periodicity")) {
			String limit = field.getVarValue("period");
			String limitValue = ValidatorUtil.getValueAsString(bean, limit);
			Log.log(4, "Validator", "validateAppFilingLimitRule",
					(new StringBuilder()).append("Period ").append(limitValue)
							.toString());
			if (limitValue.equals("0")) {
				Log.log(4, "Validator", "validateAppFilingLimitRule",
						"Periodicity not selected");
				ActionError actionMessage = new ActionError(
						"zeroAppFilingPeriodNotAllowed");
				errors.add("org.apache.struts.action.GLOBAL_ERROR",
						actionMessage);
			}
		}
		Log.log(4, "Validator", "validateAppFilingLimitRule", "Exited");
		return errors.isEmpty();
	}

	public static boolean validateDefaultRate(Object bean,
			ValidatorAction validAction, Field field, ActionErrors errors,
			HttpServletRequest request) {
		Log.log(4, "Validator", "validateDefaultRate", "Entered");
		String applicable = ValidatorUtil.getValueAsString(bean,
				field.getProperty());
		Log.log(4, "Validator", "validateDefaultRate", (new StringBuilder())
				.append("default rate applicable -- ").append(applicable)
				.toString());
		if (applicable.equals("Y")) {
			String rate = field.getVarValue("defaultRate");
			String rateValue = ValidatorUtil.getValueAsString(bean, rate);
			if (Double.parseDouble(rateValue) == 0.0D
					|| Double.parseDouble(rateValue) > 99D) {
				Log.log(4, "Validator", "validateDefaultRate",
						"default rate invalid");
				ActionError actionMessage = new ActionError(
						"invalidDefaultRate");
				errors.add("org.apache.struts.action.GLOBAL_ERROR",
						actionMessage);
			}
			String validFrom = field.getVarValue("validFromDate");
			String validFromValue = ValidatorUtil.getValueAsString(bean,
					validFrom);
			String validTo = field.getVarValue("validToDate");
			String validToValue = ValidatorUtil.getValueAsString(bean, validTo);
			if (GenericValidator.isBlankOrNull(validFromValue)) {
				Log.log(4, "Validator", "validateDefaultRate",
						"default rate valid from is required");
				ActionError actionMessage = new ActionError(
						"defFromDateRequired");
				errors.add("org.apache.struts.action.GLOBAL_ERROR",
						actionMessage);
			}
			if (!GenericValidator.isBlankOrNull(validFromValue)
					&& !GenericValidator.isBlankOrNull(validToValue)
					&& !DateHelper.day1BeforeDay2(validFromValue, validToValue)) {
				Log.log(4, "Validator", "validateDefaultRate",
						"default rate valid from not earlier than valid to");
				ActionError actionMessage = new ActionError(
						(new StringBuilder()).append("fromDateGT")
								.append(validTo).toString());
				errors.add("org.apache.struts.action.GLOBAL_ERROR",
						actionMessage);
			}
		}
		Log.log(4, "Validator", "validateDefaultRate", "Exited");
		return errors.isEmpty();
	}

	public static boolean validateBIdOrCgpanOrBName(Object bean,
			ValidatorAction validAction, Field field, ActionErrors errors,
			HttpServletRequest request) {
		Log.log(4, "Validator", "validateBIdOrCgpanOrBName", "Enetred");
		GMActionForm gmActionForm = (GMActionForm) bean;
		String memId = gmActionForm.getMemberId().trim();
		String borrowerId = gmActionForm.getBorrowerId().trim();
		String cgpan = gmActionForm.getCgpan().trim();
		String borrowerName = gmActionForm.getBorrowerName();
		Log.log(4, "Validator", "bid --", (new StringBuilder()).append("-->")
				.append(borrowerId).toString());
		boolean remainingFieldsValid = true;
		if ((borrowerId == null || borrowerId.equals(""))
				&& (cgpan == null || cgpan.equals(""))
				&& (borrowerName == null || borrowerName.equals("")))
			remainingFieldsValid = false;
		if (borrowerId != null && !borrowerId.equals("")) {
			if (cgpan != null && !cgpan.equals("") || borrowerName != null
					&& !borrowerName.equals(""))
				remainingFieldsValid = false;
		} else if (borrowerName != null && !borrowerName.equals("")) {
			if (cgpan != null && !cgpan.equals("") || borrowerId != null
					&& !borrowerId.equals(""))
				remainingFieldsValid = false;
		} else if (cgpan != null
				&& !cgpan.equals("")
				&& (borrowerName != null && !borrowerName.equals("") || borrowerId != null
						&& !borrowerId.equals("")))
			remainingFieldsValid = false;
		if ((memId == null || memId.equals("")) && remainingFieldsValid) {
			ActionError actionError = new ActionError("entermemid");
			errors.add("org.apache.struts.action.GLOBAL_ERROR", actionError);
		}
		if ((memId == null || memId.equals("")) && !remainingFieldsValid) {
			ActionError actionError = new ActionError(
					"entermemidbidorcgpanorbName");
			errors.add("org.apache.struts.action.GLOBAL_ERROR", actionError);
		}
		if (memId != null && !memId.equals("") && !remainingFieldsValid) {
			ActionError actionError = new ActionError("enterbidorcgpanorbName");
			errors.add("org.apache.struts.action.GLOBAL_ERROR", actionError);
		}
		Log.log(4, "Validator", "validateBIdOrCgpanOrBName", "Exited");
		return errors.isEmpty();
	}

	public static boolean validateIdsForSchedule(Object bean,
			ValidatorAction validAction, Field field, ActionErrors errors,
			HttpServletRequest request) {
		Log.log(4, "Validator", "validateIdsForSchedule", "Enetred");
		GMActionForm gmActionForm = (GMActionForm) bean;
		String borrowerId = gmActionForm.getBorrowerIdForSchedule().trim();
		String cgpan = gmActionForm.getCgpanForSchedule().trim();
		String borrowerName = gmActionForm.getBorrowerNameForSchedule().trim();
		Log.log(4, "Validator", "bid --", (new StringBuilder()).append("-->")
				.append(borrowerId).toString());
		if ((borrowerId == null || borrowerId.equals(""))
				&& (cgpan == null || cgpan.equals(""))
				&& (borrowerName == null || borrowerName.equals(""))) {
			ActionError actionError = new ActionError("enterbidorcgpanorbName");
			errors.add("org.apache.struts.action.GLOBAL_ERROR", actionError);
		}
		if (borrowerId != null && !borrowerId.equals("")) {
			if (cgpan != null && !cgpan.equals("") || borrowerName != null
					&& !borrowerName.equals("")) {
				ActionError actionError = new ActionError(
						"enterbidorcgpanorbName");
				errors.add("org.apache.struts.action.GLOBAL_ERROR", actionError);
			}
		} else if (borrowerName != null && !borrowerName.equals("")) {
			if (cgpan != null && !cgpan.equals("") || borrowerId != null
					&& !borrowerId.equals("")) {
				ActionError actionError = new ActionError(
						"enterbidorcgpanorbName");
				errors.add("org.apache.struts.action.GLOBAL_ERROR", actionError);
			}
		} else if (cgpan != null
				&& !cgpan.equals("")
				&& (borrowerName != null && !borrowerName.equals("") || borrowerId != null
						&& !borrowerId.equals(""))) {
			ActionError actionError = new ActionError("enterbidorcgpanorbName");
			errors.add("org.apache.struts.action.GLOBAL_ERROR", actionError);
		}
		Log.log(4, "Validator", "validateIdsForSchedule", "Exited");
		return errors.isEmpty();
	}

	public static boolean validateIdsForShifting(Object bean,
			ValidatorAction validAction, Field field, ActionErrors errors,
			HttpServletRequest request) {
		Log.log(4, "Validator", "validateIdsForShifting", "Enetred");
		GMActionForm gmActionForm = (GMActionForm) bean;
		String borrowerId = gmActionForm.getBorrowerIdForShifting();
		String cgpan = gmActionForm.getCgpanForShifting();
		Log.log(4, "Validator", "bid --", (new StringBuilder()).append("-->")
				.append(borrowerId).toString());
		if ((borrowerId == null || borrowerId.equals(""))
				&& (cgpan == null || cgpan.equals(""))) {
			ActionError actionError = new ActionError("enterbidorcgpan");
			errors.add("org.apache.struts.action.GLOBAL_ERROR", actionError);
		} else if (!borrowerId.equals("") && !cgpan.equals("")) {
			ActionError actionError = new ActionError("enterbidorcgpan");
			errors.add("org.apache.struts.action.GLOBAL_ERROR", actionError);
		}
		Log.log(4, "Validator", "validateIdsForShifting", "Exited");
		return errors.isEmpty();
	}

	public static boolean validateIdsForModifyBorrDtl(Object bean,
			ValidatorAction validAction, Field field, ActionErrors errors,
			HttpServletRequest request) {

		try {
			DynaActionForm dynaActionForm = (DynaActionForm) bean;

			String strMemberId = dynaActionForm.get("memberIdForModifyBorrDtl")
					.toString();
			String strBorrowerId = dynaActionForm.get(
					"borrowerIdForModifyBorrDtl").toString();
			String strCgPan = dynaActionForm.get("cgpanForModifyBorrDtl")
					.toString();
			String strBorrowerName = dynaActionForm.get(
					"borrowerNameForModifyBorrDtl").toString();

			GMDAO gmDao = new GMDAO();
			
			ApplicationProcessor appProcessor = new ApplicationProcessor();

			ClaimsProcessor processor = new ClaimsProcessor();
			
			//System.out.println("errors.isEmpty() "+errors.isEmpty());
			Vector memberids = processor.getAllMemberIds();

			if (!memberids.contains(strMemberId) && errors.isEmpty()==true) {
				ActionError error = new ActionError("memberIDCheck",
						strMemberId);
				errors.add("org.apache.struts.action.GLOBAL_ERROR", error);
			}

			if(errors.isEmpty()==true)
			{
				String validateMessage = gmDao.chkInputCGPAN_BID_Status(
						strMemberId, strCgPan, "");
				if (validateMessage != null ) {
					ActionError error = new ActionError(validateMessage);
					errors.add("org.apache.struts.action.GLOBAL_ERROR", error);
				}
			}

		} catch (Exception e) {
			//e.printStackTrace();
		}
		return errors.isEmpty();
	}

	public static boolean validateTcOutstanding(Object bean,
			ValidatorAction validAction, Field field, ActionErrors errors,
			HttpServletRequest request) throws Exception {
		Log.log(4, "Validator", "validateTcOutstanding", "Entered");
		GMActionForm gmActionForm = (GMActionForm) bean;
		Map cgpanTcMap = gmActionForm.getCgpansForTc();
		Set cgpanTcSet = cgpanTcMap.keySet();
		Iterator cgpanTcIterator = cgpanTcSet.iterator();
		Log.log(4, "Validator", "validateTcOutstanding", (new StringBuilder())
				.append("cgpanTcMap ").append(cgpanTcMap.size()).toString());
		Map tcPrAmountMap = gmActionForm.getTcPrincipalOutstandingAmount();
		Set tcPrAmountSet = tcPrAmountMap.keySet();
		Iterator tcPrAmountIterator = tcPrAmountSet.iterator();
		Map tcDateMap = gmActionForm.getTcOutstandingAsOnDate();
		SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd/MM/yyyy");
		String cgpan = null;
		int count = 0;
		String tcKey = null;
		Date tcDate = null;
		boolean finalFlag = true;
		while (cgpanTcIterator.hasNext()) {
			cgpan = (String) cgpanTcMap.get(cgpanTcIterator.next());
			Log.log(4,
					"Validator",
					"validateTcOutstanding",
					(new StringBuilder())
							.append("Inside CgpanIterator cgpan  :")
							.append(cgpan).toString());
			tcPrAmountIterator = tcPrAmountSet.iterator();
			count = 0;
			boolean dateFlag = false;
			while (tcPrAmountIterator.hasNext()) {
				tcPrAmountIterator.next();
				tcKey = (new StringBuilder()).append(cgpan).append("-")
						.append(count).toString();
				Log.log(4, "Validator", "validateTcOutstanding",
						(new StringBuilder()).append("key ->").append(tcKey)
								.toString());
				if (tcKey != null && !tcKey.equals("")
						&& tcPrAmountMap.containsKey(tcKey)) {
					String tcPrAmtVal = (String) tcPrAmountMap.get(tcKey);
					String tcDateVal = (String) tcDateMap.get(tcKey);
					if (!tcDateVal.equals("") && tcPrAmtVal.equals("")) {
						Log.log(4, "Validator", "validateTcOutstanding",
								"Key is matched  Date :");
						ActionError actionError = new ActionError(
								"enterTcOutAmtandDate");
						errors.add("org.apache.struts.action.GLOBAL_ERROR",
								actionError);
					} else if (!tcPrAmtVal.equals("") && tcDateVal.equals("")) {
						Log.log(4, "Validator", "validateTcOutstanding",
								"Key is matched  Date :");
						ActionError actionError = new ActionError(
								"enterTcOutAmtandDate");
						errors.add("org.apache.struts.action.GLOBAL_ERROR",
								actionError);
					} else if (!tcPrAmtVal.equals("") && !tcDateVal.equals("")
							&& finalFlag) {
						Date currentDate = new Date();
						SimpleDateFormat dateFormat = new SimpleDateFormat(
								"dd/MM/yyyy");
						try {
							String stringDate = dateFormat.format(currentDate);
							if (!tcDateVal.equals("")
									&& !tcPrAmtVal.equals("")
									&& DateHelper.compareDates(tcDateVal,
											stringDate) != 0
									&& DateHelper.compareDates(tcDateVal,
											stringDate) != 1) {
								ActionError actionError = new ActionError(
										"futureDate", tcDateVal);
								errors.add(
										"org.apache.struts.action.GLOBAL_ERROR",
										actionError);
							}
						} catch (NumberFormatException numberFormatException) {
							boolean remarksVal = false;
							for (Iterator errorsIterator = errors.get(); errorsIterator
									.hasNext();) {
								ActionError error = (ActionError) errorsIterator
										.next();
							//	System.out.println(error.getKey());
								if (error.getKey().equals("errors.date")) {
									remarksVal = true;
									break;
								}
							}

							if (!remarksVal) {
								ActionError actionError = new ActionError(
										"errors.date",
										"Term Loan Outstanding As On Date");
								errors.add(
										"org.apache.struts.action.GLOBAL_ERROR",
										actionError);
							}
						}
					}
				}
				count++;
			}
		}
		Log.log(4, "Validator", "validateTcOutstanding", "Exited");
		return errors.isEmpty();
	}

	public static boolean validateTcOutstandings(Object bean,
			ValidatorAction validAction, Field field, ActionErrors errors,
			HttpServletRequest request) {
		Log.log(4, "Validator", "validateTcOutstandings", "Entered");
		GMActionForm gmActionForm = (GMActionForm) bean;
		Map cgpanTcMap = gmActionForm.getCgpansForTc();
		Set cgpanTcSet = cgpanTcMap.keySet();
		Iterator cgpanTcIterator = cgpanTcSet.iterator();
		Log.log(4, "Validator", "validateTcOutstandings", (new StringBuilder())
				.append("cgpanTcMap ").append(cgpanTcMap.size()).toString());
		Map tcPrAmountMap = gmActionForm.getTcPrincipalOutstandingAmount();
		Set tcPrAmountSet = tcPrAmountMap.keySet();
		Iterator tcPrAmountIterator = tcPrAmountSet.iterator();
		Map tcDateMap = gmActionForm.getTcOutstandingAsOnDate();
		SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd/MM/yyyy");
		String cgpan = null;
		int count = 0;
		String tcKey = null;
		Date tcDate = null;
		boolean dateFlag = false;
		while (cgpanTcIterator.hasNext()) {
			cgpan = (String) cgpanTcMap.get(cgpanTcIterator.next());
			Log.log(4,
					"Validator",
					"validateTcOutstandings",
					(new StringBuilder())
							.append("Inside CgpanIterator cgpan  :")
							.append(cgpan).toString());
			tcPrAmountIterator = tcPrAmountSet.iterator();
			Log.log(4,
					"Validator",
					"validateTcOutstandings",
					(new StringBuilder()).append("tcPrAmountMap")
							.append(tcPrAmountMap.size()).toString());
			for (count = 0; tcPrAmountIterator.hasNext(); count++) {
				tcPrAmountIterator.next();
				tcKey = (new StringBuilder()).append(cgpan).append("-")
						.append(count).toString();
				Log.log(4, "Validator", "validateTcOutstandings",
						(new StringBuilder()).append("key ->").append(tcKey)
								.toString());
				if (tcKey == null || tcKey.equals("")
						|| !tcPrAmountMap.containsKey(tcKey))
					continue;
				String tcPrAmtVal = (String) tcPrAmountMap.get(tcKey);
				String tcDateVal = (String) tcDateMap.get(tcKey);
				if (tcDateVal.equals("") || tcPrAmtVal.equals(""))
					continue;
				dateFlag = true;
				break;
			}

		}
		if (cgpanTcMap.size() > 0 && !dateFlag) {
			ActionError actionError = new ActionError("enterTcOutValues");
			errors.add("org.apache.struts.action.GLOBAL_ERROR", actionError);
		}
		Log.log(4, "Validator", "validateTcOutstandings", "Exited");
		return errors.isEmpty();
	}

	public static boolean validateWcOutstanding(Object bean,
			ValidatorAction validAction, Field field, ActionErrors errors,
			HttpServletRequest request) throws Exception {
		Log.log(4, "Validator", "validateWcOutstanding", "Entered");
		GMActionForm gmActionForm = (GMActionForm) bean;
		Map cgpanWcMap = gmActionForm.getCgpansForWc();
		Set cgpanWcSet = cgpanWcMap.keySet();
		Iterator cgpanWcIterator = cgpanWcSet.iterator();
		Log.log(4, "GMAction", "saveOutstandingDetails", (new StringBuilder())
				.append("cgpanWcMap ").append(cgpanWcMap.size()).toString());
		Map wcFBPrAmountMap = gmActionForm.getWcFBPrincipalOutstandingAmount();
		Set wcFBPrAmountSet = wcFBPrAmountMap.keySet();
		Iterator wcFBPrAmountIterator = wcFBPrAmountSet.iterator();
		Map wcNFBPrAmountMap = gmActionForm
				.getWcNFBPrincipalOutstandingAmount();
		Set wcNFBPrAmountSet = wcNFBPrAmountMap.keySet();
		Iterator wcNFBPrAmountIterator = wcNFBPrAmountSet.iterator();
		Map wcFBIntAmountMap = gmActionForm.getWcFBInterestOutstandingAmount();
		Map wcNFBIntAmountMap = gmActionForm
				.getWcNFBInterestOutstandingAmount();
		Map wcFBDateMap = gmActionForm.getWcFBOutstandingAsOnDate();
		Map wcNFBDateMap = gmActionForm.getWcNFBOutstandingAsOnDate();
		SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd/MM/yyyy");
		String cgpan = null;
		int count = 0;
		String wcKey = null;
		boolean finalFlag = true;
		Date wcDate = null;
		Date wcNFBDate = null;
		while (cgpanWcIterator.hasNext()) {
			cgpan = (String) cgpanWcMap.get(cgpanWcIterator.next());
			Log.log(4,
					"GMAction",
					"saveOutstandingDetails",
					(new StringBuilder())
							.append("Inside CgpanIterator cgpan  :")
							.append(cgpan).toString());
			wcFBPrAmountIterator = wcFBPrAmountSet.iterator();
			boolean dateFlag = false;
			for (count = 0; wcFBPrAmountIterator.hasNext(); count++) {
				wcFBPrAmountIterator.next();
				wcKey = (new StringBuilder()).append(cgpan).append("-")
						.append(count).toString();
				Log.log(4, "GMAction", "saveOutstandingDetails",
						(new StringBuilder()).append("key ->").append(wcKey)
								.toString());
				if (wcKey != null && !wcKey.equals("")
						&& wcFBPrAmountMap.containsKey(wcKey)) {
					String wcFBPrAmtVal = (String) wcFBPrAmountMap.get(wcKey);
					String wcFBIntAmtVal = (String) wcFBIntAmountMap.get(wcKey);
					String wcFBDateVal = (String) wcFBDateMap.get(wcKey);
					String wcNFBPrAmtVal = (String) wcNFBPrAmountMap.get(wcKey);
					String wcNFBIntAmtVal = (String) wcNFBIntAmountMap
							.get(wcKey);
					String wcNFBDateVal = (String) wcNFBDateMap.get(wcKey);
					if ((!wcFBPrAmtVal.equals("")
							&& Double.parseDouble(wcFBPrAmtVal) >= 0.0D || !wcFBIntAmtVal
							.equals("")
							&& Double.parseDouble(wcFBIntAmtVal) >= 0.0D)
							&& wcFBDateVal != null
							&& wcFBDateVal.toString().equals("")) {
					//	System.out.println("entering");
						boolean remarksVal = false;
						for (Iterator errorsIterator = errors.get(); errorsIterator
								.hasNext();) {
							ActionError error = (ActionError) errorsIterator
									.next();
							if (error.getKey().equals("errors.required")) {
								remarksVal = true;
								break;
							}
						}

						if (!remarksVal) {
							ActionError actionError = new ActionError(
									"errors.required",
									"Working Capital Outstanding As On Date ");
							errors.add("org.apache.struts.action.GLOBAL_ERROR",
									actionError);
						}
					} else if (wcFBDateVal != null
							&& !wcFBDateVal.toString().equals("")
							&& (wcFBPrAmtVal.equals("") || wcFBIntAmtVal
									.equals(""))) {
						ActionError actionError = new ActionError(
								"enterWcOutAmtandDate");
						errors.add("org.apache.struts.action.GLOBAL_ERROR",
								actionError);
					} else if (wcFBDateVal != null
							&& !wcFBDateVal.toString().equals("") && finalFlag) {
						Date currentDate = new Date();
						SimpleDateFormat dateFormat = new SimpleDateFormat(
								"dd/MM/yyyy");
						try {
							wcDate = simpleDateFormat.parse(wcFBDateVal,
									new ParsePosition(0));
							if (wcDate == null) {
								dateFlag = false;
								boolean remarksVal = false;
								for (Iterator errorsIterator = errors.get(); errorsIterator
										.hasNext();) {
									ActionError error = (ActionError) errorsIterator
											.next();
									if (error.getKey().equals("errors.date")) {
										remarksVal = true;
										break;
									}
								}

								if (!remarksVal) {
									ActionError actionError = new ActionError(
											"errors.date",
											"Working Capital Outstanding As On Date ");
									errors.add(
											"org.apache.struts.action.GLOBAL_ERROR",
											actionError);
								}
								finalFlag = false;
							} else {
								dateFlag = true;
								try {
									String stringDate = dateFormat
											.format(currentDate);
									if (!wcFBDateVal.equals("")
											&& !wcFBPrAmtVal.equals("")
											&& DateHelper.compareDates(
													wcFBDateVal, stringDate) != 0
											&& DateHelper.compareDates(
													wcFBDateVal, stringDate) != 1) {
										ActionError actionError = new ActionError(
												"futureDate", wcFBDateVal);
										errors.add(
												"org.apache.struts.action.GLOBAL_ERROR",
												actionError);
									}
								} catch (NumberFormatException numberFormatException) {
									boolean remarksVal = false;
									for (Iterator errorsIterator = errors.get(); errorsIterator
											.hasNext();) {
										ActionError error = (ActionError) errorsIterator
												.next();
									//	System.out.println(error.getKey());
										if (error.getKey()
												.equals("errors.date")) {
											remarksVal = true;
											break;
										}
									}

									if (!remarksVal) {
										ActionError actionError = new ActionError(
												"errors.date",
												"Working Capital Outstanding As On Date ");
										errors.add(
												"org.apache.struts.action.GLOBAL_ERROR",
												actionError);
									}
								}
							}
						} catch (NumberFormatException numberFormatException) {
							boolean remarksVal = false;
							for (Iterator errorsIterator = errors.get(); errorsIterator
									.hasNext();) {
								ActionError error = (ActionError) errorsIterator
										.next();
								//System.out.println(error.getKey());
								if (error.getKey().equals("errors.date")) {
									remarksVal = true;
									break;
								}
							}

							if (!remarksVal) {
								ActionError actionError = new ActionError(
										"errors.date",
										"Working Capital Outstanding As On Date ");
								errors.add(
										"org.apache.struts.action.GLOBAL_ERROR",
										actionError);
							}
						}
					}
					if (wcNFBPrAmtVal == null || wcNFBPrAmtVal.equals("")
							|| wcNFBIntAmtVal == null
							|| wcNFBIntAmtVal.equals(""))
						System.out
								.println("wcNFBPrAmtVal and  wcNFBIntAmtVal is null");
					else if ((!wcNFBPrAmtVal.equals("")
							&& Double.parseDouble(wcNFBPrAmtVal) >= 0.0D || !wcNFBIntAmtVal
							.equals("")
							&& Double.parseDouble(wcNFBIntAmtVal) >= 0.0D)
							&& wcNFBDateVal != null
							&& wcNFBDateVal.toString().equals("")) {
						boolean remarksVal = false;
						for (Iterator errorsIterator = errors.get(); errorsIterator
								.hasNext();) {
							ActionError error = (ActionError) errorsIterator
									.next();
							if (error.getKey().equals("errors.required")) {
								remarksVal = true;
								break;
							}
						}

						if (!remarksVal) {
							ActionError actionError = new ActionError(
									"errors.required",
									"Working Capital Non Fund Based Outstanding As On Date ");
							errors.add("org.apache.struts.action.GLOBAL_ERROR",
									actionError);
						}
					} else if (wcNFBDateVal != null
							&& !wcNFBDateVal.toString().equals("")
							&& (wcNFBPrAmtVal.equals("") || wcNFBIntAmtVal
									.equals(""))) {
						ActionError actionError = new ActionError(
								"enterWcOutAmtandDate");
						errors.add("org.apache.struts.action.GLOBAL_ERROR",
								actionError);
					} else if (wcNFBDateVal != null
							&& !wcNFBDateVal.toString().equals("") && finalFlag) {
						Date currentDate = new Date();
						SimpleDateFormat dateFormat = new SimpleDateFormat(
								"dd/MM/yyyy");
						try {
							wcDate = simpleDateFormat.parse(wcNFBDateVal,
									new ParsePosition(0));
							if (wcDate == null) {
								dateFlag = false;
								boolean remarksVal = false;
								for (Iterator errorsIterator = errors.get(); errorsIterator
										.hasNext();) {
									ActionError error = (ActionError) errorsIterator
											.next();
									if (error.getKey().equals("errors.date")) {
										remarksVal = true;
										break;
									}
								}

								if (!remarksVal) {
									ActionError actionError = new ActionError(
											"errors.date",
											"Working Capital Non Fund Based Outstanding As On Date ");
									errors.add(
											"org.apache.struts.action.GLOBAL_ERROR",
											actionError);
								}
								finalFlag = false;
							} else {
								dateFlag = true;
								try {
									String stringDate = dateFormat
											.format(currentDate);
									if (!wcNFBDateVal.equals("")
											&& !wcNFBPrAmtVal.equals("")
											&& DateHelper.compareDates(
													wcNFBDateVal, stringDate) != 0
											&& DateHelper.compareDates(
													wcNFBDateVal, stringDate) != 1) {
										ActionError actionError = new ActionError(
												"futureDate", wcNFBDateVal);
										errors.add(
												"org.apache.struts.action.GLOBAL_ERROR",
												actionError);
									}
								} catch (NumberFormatException numberFormatException) {
									boolean remarksVal = false;
									for (Iterator errorsIterator = errors.get(); errorsIterator
											.hasNext();) {
										ActionError error = (ActionError) errorsIterator
												.next();
										if (error.getKey()
												.equals("errors.date")) {
											remarksVal = true;
											break;
										}
									}

									if (!remarksVal) {
										ActionError actionError = new ActionError(
												"errors.date",
												"Working Capital Non Fund Based Outstanding As On Date ");
										errors.add(
												"org.apache.struts.action.GLOBAL_ERROR",
												actionError);
									}
								}
							}
						} catch (NumberFormatException numberFormatException) {
							boolean remarksVal = false;
							for (Iterator errorsIterator = errors.get(); errorsIterator
									.hasNext();) {
								ActionError error = (ActionError) errorsIterator
										.next();
								if (error.getKey().equals("errors.date")) {
									remarksVal = true;
									break;
								}
							}

							if (!remarksVal) {
								ActionError actionError = new ActionError(
										"errors.date",
										"Working Capital Non Fund Based Outstanding As On Date ");
								errors.add(
										"org.apache.struts.action.GLOBAL_ERROR",
										actionError);
							}
						}
					}
				}
			}

		}
		Log.log(4, "Validator", "validateWcOutstanding", "Exited");
		return errors.isEmpty();
	}

	public static boolean validateWcOutstandings(Object bean,
			ValidatorAction validAction, Field field, ActionErrors errors,
			HttpServletRequest request) {
		Log.log(4, "Validator", "validateWcOutstanding", "Entered");
		GMActionForm gmActionForm = (GMActionForm) bean;
		Map cgpanWcMap = gmActionForm.getCgpansForWc();
		Set cgpanWcSet = cgpanWcMap.keySet();
		Iterator cgpanWcIterator = cgpanWcSet.iterator();
		Log.log(4, "GMAction", "saveOutstandingDetails", (new StringBuilder())
				.append("cgpanWcMap ").append(cgpanWcMap.size()).toString());
		Map wcFBPrAmountMap = gmActionForm.getWcFBPrincipalOutstandingAmount();
		Set wcFBPrAmountSet = wcFBPrAmountMap.keySet();
		Iterator wcFBPrAmountIterator = wcFBPrAmountSet.iterator();
		Map wcNFBPrAmountMap = gmActionForm
				.getWcNFBPrincipalOutstandingAmount();
		Set wcNFBPrAmountSet = wcNFBPrAmountMap.keySet();
		Iterator wcNFBPrAmountIterator = wcNFBPrAmountSet.iterator();
		Map wcFBIntAmountMap = gmActionForm.getWcFBInterestOutstandingAmount();
		Map wcNFBIntAmountMap = gmActionForm
				.getWcNFBInterestOutstandingAmount();
		Map wcFBDateMap = gmActionForm.getWcFBOutstandingAsOnDate();
		Map wcNFBDateMap = gmActionForm.getWcNFBOutstandingAsOnDate();
		SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd/MM/yyyy");
		String cgpan = null;
		int count = 0;
		String wcKey = null;
		boolean dateFlag = false;
		Date wcDate = null;
		while (cgpanWcIterator.hasNext()) {
			cgpan = (String) cgpanWcMap.get(cgpanWcIterator.next());
			Log.log(4,
					"GMAction",
					"saveOutstandingDetails",
					(new StringBuilder())
							.append("Inside CgpanIterator cgpan  :")
							.append(cgpan).toString());
			wcFBPrAmountIterator = wcFBPrAmountSet.iterator();
			for (count = 0; wcFBPrAmountIterator.hasNext(); count++) {
				wcFBPrAmountIterator.next();
				wcKey = (new StringBuilder()).append(cgpan).append("-")
						.append(count).toString();
				Log.log(4, "GMAction", "saveOutstandingDetails",
						(new StringBuilder()).append("key ->").append(wcKey)
								.toString());
				if (wcKey == null || wcKey.equals("")
						|| !wcFBPrAmountMap.containsKey(wcKey))
					continue;
				String wcFBPrAmtVal = (String) wcFBPrAmountMap.get(wcKey);
				String wcFBIntAmtVal = (String) wcFBIntAmountMap.get(wcKey);
				String wcFBDateVal = (String) wcFBDateMap.get(wcKey);
				String wcNFBPrAmtVal = (String) wcNFBPrAmountMap.get(wcKey);
				String wcNFBIntAmtVal = (String) wcNFBIntAmountMap.get(wcKey);
				String wcNFBDateVal = (String) wcNFBDateMap.get(wcKey);
				if ((wcFBPrAmtVal.equals("") || wcFBIntAmtVal.equals("") || wcFBDateVal
						.equals(""))
						&& (wcNFBPrAmtVal.equals("")
								|| wcNFBIntAmtVal.equals("") || wcNFBDateVal
								.equals("")))
					continue;
				dateFlag = true;
				break;
			}

		}
		if (cgpanWcMap.size() > 0 && !dateFlag) {
			ActionError actionError = new ActionError("enterWcOutValues");
			errors.add("org.apache.struts.action.GLOBAL_ERROR", actionError);
		}
		Log.log(4, "Validator", "validateWcOutstanding", "Exited");
		return errors.isEmpty();
	}

	public static boolean validateDisbursementAmount(Object bean,
			ValidatorAction validAction, Field field, ActionErrors errors,
			HttpServletRequest request) throws Exception {
		Log.log(4, "Validator", "validateDisbursementAmount", "Entered");
		GMActionForm gmActionForm = (GMActionForm) bean;
		Map cgpanMap = gmActionForm.getCgpans();
		Set cgpanSet = cgpanMap.keySet();
		Iterator cgpanIterator = cgpanSet.iterator();
		Log.log(4,
				"Validator",
				"validateDisbursementAmount",
				(new StringBuilder()).append("cgpan Map size = ")
						.append(cgpanMap.size()).toString());
		Map disbAmtMap = gmActionForm.getDisbursementAmount();
		Set disbAmtSet = disbAmtMap.keySet();
		Iterator disbAmtIterator = disbAmtSet.iterator();
		Log.log(4,
				"Validator",
				"validateDisbursementAmount",
				(new StringBuilder()).append("Amount map size = ")
						.append(disbAmtMap.size()).toString());
		Map disbDateMap = gmActionForm.getDisbursementDate();
		Log.log(4,
				"Validator",
				"validateDisbursementAmount",
				(new StringBuilder()).append("Date map size = ")
						.append(disbDateMap.size()).toString());
		SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd/MM/yyyy");
		Date disbDate = null;
		String cgpan = null;
		int count = 0;
		String key = null;
		boolean finalFlag = true;
		while (cgpanIterator.hasNext()) {
			cgpan = (String) cgpanMap.get(cgpanIterator.next());
			Log.log(4, "Validator", "validateDisbursementAmount",
					(new StringBuilder()).append("cgpanIterator cgpan: ")
							.append(cgpan).toString());
			disbAmtIterator = disbAmtSet.iterator();
			count = 0;
			boolean dateFlag = false;
			while (disbAmtIterator.hasNext()) {
				disbAmtIterator.next();
				key = (new StringBuilder()).append(cgpan).append("-")
						.append(count).toString();
				Log.log(4, "Validator", "validateDisbursementAmount",
						(new StringBuilder()).append("Amount Iterator key : ")
								.append(key).toString());
				if (key != null && !key.equals("")
						&& disbAmtMap.containsKey(key)) {
					String disbAmount = (String) disbAmtMap.get(key);
					Log.log(4,
							"Validator",
							"validateDisbursementAmount",
							(new StringBuilder())
									.append("Key is matched Amount :")
									.append(disbAmount).toString());
					String disbursementDate = (String) disbDateMap.get(key);
					Log.log(4,
							"Validator",
							"validateDisbursementAmount",
							(new StringBuilder())
									.append("Key is matched Date :")
									.append(disbursementDate).toString());
					if (!disbAmount.equals("") && disbursementDate.equals("")) {
						Log.log(4, "Validator", "validateDisbursementAmount",
								"Key is matched Amount :");
						ActionError actionError = new ActionError(
								"enterDisbursementAmtandDate");
						errors.add("org.apache.struts.action.GLOBAL_ERROR",
								actionError);
					} else if (disbAmount.equals("")
							&& !disbursementDate.equals("")) {
						Log.log(4, "Validator", "validateDisbursementAmount",
								"Key is matched  Date :");
						ActionError actionError = new ActionError(
								"enterDisbursementAmtandDate");
						errors.add("org.apache.struts.action.GLOBAL_ERROR",
								actionError);
					} else if (!disbAmount.equals("")
							&& !disbursementDate.equals("") && finalFlag) {
						Date currentDate = new Date();
						SimpleDateFormat dateFormat = new SimpleDateFormat(
								"dd/MM/yyyy");
						try {
							disbDate = simpleDateFormat.parse(disbursementDate,
									new ParsePosition(0));
							Log.log(4,
									"Validator",
									"validateDisbursementAmount",
									(new StringBuilder())
											.append("inside try block date format :")
											.append(disbursementDate)
											.toString());
							Log.log(4,
									"Validator",
									"validateDisbursementAmount",
									(new StringBuilder())
											.append("after set ing true:")
											.append(disbursementDate)
											.toString());
							if (disbDate == null) {
								dateFlag = false;
								ActionError actionError = new ActionError(
										"errors.date", "Disbursement Date");
								errors.add(
										"org.apache.struts.action.GLOBAL_ERROR",
										actionError);
								finalFlag = false;
							} else {
								dateFlag = true;
								try {
									String stringDate = dateFormat
											.format(currentDate);
									if (!disbursementDate.equals("")
											&& !disbursementDate.equals("")
											&& DateHelper.compareDates(
													disbursementDate,
													stringDate) != 0
											&& DateHelper.compareDates(
													disbursementDate,
													stringDate) != 1) {
										ActionError actionError = new ActionError(
												"futureDate", disbursementDate);
										errors.add(
												"org.apache.struts.action.GLOBAL_ERROR",
												actionError);
									}
								} catch (NumberFormatException numberFormatException) {
									boolean remarksVal = false;
									for (Iterator errorsIterator = errors.get(); errorsIterator
											.hasNext();) {
										ActionError error = (ActionError) errorsIterator
												.next();
									//	System.out.println(error.getKey());
										if (error.getKey()
												.equals("errors.date")) {
											remarksVal = true;
											break;
										}
									}

									if (!remarksVal) {
										ActionError actionError = new ActionError(
												"errors.date",
												"Disbursement Date ");
										errors.add(
												"org.apache.struts.action.GLOBAL_ERROR",
												actionError);
									}
								}
							}
						} catch (NumberFormatException numberFormatException) {
							boolean remarksVal = false;
							for (Iterator errorsIterator = errors.get(); errorsIterator
									.hasNext();) {
								ActionError error = (ActionError) errorsIterator
										.next();
								//System.out.println(error.getKey());
								if (error.getKey().equals("errors.date")) {
									remarksVal = true;
									break;
								}
							}

							if (!remarksVal) {
								ActionError actionError = new ActionError(
										"errors.date", "Disbursement Date");
								errors.add(
										"org.apache.struts.action.GLOBAL_ERROR",
										actionError);
							}
						}
					}
				}
				count++;
			}
		}
		return errors.isEmpty();
	}

	public static boolean validateDisbursementAmounts(Object bean,
			ValidatorAction validAction, Field field, ActionErrors errors,
			HttpServletRequest request) {
		Log.log(4, "Validator", "validateDisbursementAmounts", "Entered");
		GMActionForm gmActionForm = (GMActionForm) bean;
		Map cgpanMap = gmActionForm.getCgpans();
		Set cgpanSet = cgpanMap.keySet();
		Iterator cgpanIterator = cgpanSet.iterator();
		Log.log(4,
				"Validator",
				"validateDisbursementAmounts",
				(new StringBuilder()).append("cgpan Map size = ")
						.append(cgpanMap.size()).toString());
		Map disbAmtMap = gmActionForm.getDisbursementAmount();
		Set disbAmtSet = disbAmtMap.keySet();
		Iterator disbAmtIterator = disbAmtSet.iterator();
		Log.log(4,
				"Validator",
				"validateDisbursementAmounts",
				(new StringBuilder()).append("Amount map size = ")
						.append(disbAmtMap.size()).toString());
		Map disbDateMap = gmActionForm.getDisbursementDate();
		Log.log(4,
				"Validator",
				"validateDisbursementAmounts",
				(new StringBuilder()).append("Date map size = ")
						.append(disbDateMap.size()).toString());
		Map finalDisFlag = gmActionForm.getFinalDisbursement();
		Log.log(4,
				"Validator",
				"validateDisbursementAmounts",
				(new StringBuilder()).append("finalDisFlag size = ")
						.append(finalDisFlag.size()).toString());
		SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd/MM/yyyy");
		Date disbDate = null;
		String cgpan = null;
		int count = 0;
		String key = null;
		boolean dateFlag = false;
		while (cgpanIterator.hasNext()) {
			cgpan = (String) cgpanMap.get(cgpanIterator.next());
			Log.log(4, "Validator", "validateDisbursementAmounts",
					(new StringBuilder()).append("cgpanIterator cgpan: ")
							.append(cgpan).toString());
			disbAmtIterator = disbAmtSet.iterator();
			for (count = 0; disbAmtIterator.hasNext(); count++) {
				disbAmtIterator.next();
				key = (new StringBuilder()).append(cgpan).append("-")
						.append(count).toString();
				Log.log(4, "Validator", "validateDisbursementAmounts",
						(new StringBuilder()).append("Amount Iterator key : ")
								.append(key).toString());
				if (key == null || key.equals("")
						|| !disbAmtMap.containsKey(key))
					continue;
				String disbAmount = (String) disbAmtMap.get(key);
				Log.log(4, "Validator", "validateDisbursementAmounts",
						(new StringBuilder()).append("Key is matched Amount :")
								.append(disbAmount).toString());
				String disbursementDate = (String) disbDateMap.get(key);
				Log.log(4, "Validator", "validateDisbursementAmount",
						(new StringBuilder()).append("Key is matched Date :")
								.append(disbursementDate).toString());
				if (disbAmount.equals("") || disbursementDate.equals(""))
					continue;
				dateFlag = true;
				break;
			}

			if (!dateFlag) {
				ActionError actionError = new ActionError(
						"enterDisbursementDetail");
				errors.add("org.apache.struts.action.GLOBAL_ERROR", actionError);
			}
		}
		return errors.isEmpty();
	}

	public static boolean validateRepaymentAmount(Object bean,
			ValidatorAction validAction, Field field, ActionErrors errors,
			HttpServletRequest request) throws Exception {
		GMActionForm gmActionForm = (GMActionForm) bean;
		Map cgpanMap = gmActionForm.getCgpans();
		String cgpan = null;
		int count = 0;
		String repayAmtKey = null;
		String key = null;
		Set cgpanSet = cgpanMap.keySet();
		Iterator cgpanIterator = cgpanSet.iterator();
		Log.log(4,
				"Validator",
				" validateRepaymentAmount ",
				(new StringBuilder()).append("cgpan Map size = ")
						.append(cgpanMap.size()).toString());
		Map repayAmountMap = gmActionForm.getRepaymentAmount();
		Set repayAmountSet = repayAmountMap.keySet();
		Iterator repayAmountIterator = repayAmountSet.iterator();
		Log.log(4,
				"Validator",
				"validateRepaymentAmount",
				(new StringBuilder()).append("Amount map size = ")
						.append(repayAmountMap.size()).toString());
		Map repayDateMap = gmActionForm.getRepaymentDate();
		Log.log(4,
				"Validator",
				"validateRepaymentAmount",
				(new StringBuilder()).append("Date map size = ")
						.append(repayDateMap.size()).toString());
		Date repmtDate = null;
		SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd/MM/yyyy");
		boolean finalFlag = true;
		while (cgpanIterator.hasNext()) {
			cgpan = (String) cgpanMap.get(cgpanIterator.next());
			Log.log(4,
					"Validator",
					"validateRepaymentAmount",
					(new StringBuilder())
							.append("Inside CgpanIterator cgpan  :")
							.append(cgpan).toString());
			repayAmountIterator = repayAmountSet.iterator();
			count = 0;
			if (repayDateMap.size() == 1)
				count = 1;
			boolean dateFlag = false;
			while (repayAmountIterator.hasNext()) {
				repayAmtKey = (String) repayAmountIterator.next();
				key = (new StringBuilder()).append(cgpan).append("-")
						.append(count).toString();
				Log.log(4, "Validator", "validateRepaymentAmount",
						(new StringBuilder()).append("key ->").append(key)
								.toString());
				if (key != null && !key.equals("")
						&& repayAmountMap.containsKey(key)) {
					String repamt = (String) repayAmountMap.get(key);
					String repdate = (String) repayDateMap.get(key);
					Log.log(4,
							"Validator",
							"validateRepaymentAmount",
							(new StringBuilder()).append("repdate :")
									.append(repdate).toString());
					if (!repamt.equals("") && repdate.toString().equals("")) {
						Log.log(4, "Validator", "validateRepaymentAmount",
								"Key is matched Amount :");
						ActionError actionError = new ActionError(
								"enterRepaymentAmtandDate");
						errors.add("org.apache.struts.action.GLOBAL_ERROR",
								actionError);
					} else if (repamt.equals("") && !repdate.equals("")) {
						Log.log(4, "Validator", "validateRepaymentAmount",
								"Key is matched  Date :");
						ActionError actionError = new ActionError(
								"enterRepaymentAmtandDate");
						errors.add("org.apache.struts.action.GLOBAL_ERROR",
								actionError);
					} else if (!repamt.equals("") && !repdate.equals("")
							&& finalFlag) {
						Date currentDate = new Date();
						SimpleDateFormat dateFormat = new SimpleDateFormat(
								"dd/MM/yyyy");
						try {
							repmtDate = simpleDateFormat.parse(
									repdate.toString(), new ParsePosition(0));
							if (repmtDate == null) {
								dateFlag = false;
								ActionError actionError = new ActionError(
										"errors.date", "Repayment Date ");
								errors.add(
										"org.apache.struts.action.GLOBAL_ERROR",
										actionError);
								finalFlag = false;
							} else {
								dateFlag = true;
								try {
									String stringDate = dateFormat
											.format(currentDate);
									if (DateHelper.compareDates(repdate,
											stringDate) != 0
											&& DateHelper.compareDates(repdate,
													stringDate) != 1) {
										ActionError actionError = new ActionError(
												"futureDate", repdate);
										errors.add(
												"org.apache.struts.action.GLOBAL_ERROR",
												actionError);
									}
								} catch (NumberFormatException numberFormatException) {
									boolean remarksVal = false;
									for (Iterator errorsIterator = errors.get(); errorsIterator
											.hasNext();) {
										ActionError error = (ActionError) errorsIterator
												.next();
									//	System.out.println(error.getKey());
										if (error.getKey()
												.equals("errors.date")) {
											remarksVal = true;
											break;
										}
									}

									if (!remarksVal) {
										ActionError actionError = new ActionError(
												"errors.date",
												"Repayment Date ");
										errors.add(
												"org.apache.struts.action.GLOBAL_ERROR",
												actionError);
									}
								}
							}
						} catch (NumberFormatException numberFormatException) {
							boolean remarksVal = false;
							for (Iterator errorsIterator = errors.get(); errorsIterator
									.hasNext();) {
								ActionError error = (ActionError) errorsIterator
										.next();
							//	System.out.println(error.getKey());
								if (error.getKey().equals("errors.date")) {
									remarksVal = true;
									break;
								}
							}

							if (!remarksVal) {
								ActionError actionError = new ActionError(
										"errors.date", "Repayment Date ");
								errors.add(
										"org.apache.struts.action.GLOBAL_ERROR",
										actionError);
							}
						}
					}
				}
				count++;
			}
		}
		return errors.isEmpty();
	}

	public static boolean validateRepaymentAmounts(Object bean,
			ValidatorAction validAction, Field field, ActionErrors errors,
			HttpServletRequest request) {
		GMActionForm gmActionForm = (GMActionForm) bean;
		Map cgpanMap = gmActionForm.getCgpans();
		String cgpan = null;
		int count = 0;
		String repayAmtKey = null;
		String key = null;
		Set cgpanSet = cgpanMap.keySet();
		Iterator cgpanIterator = cgpanSet.iterator();
		Log.log(4,
				"Validator",
				" validateRepaymentAmounts ",
				(new StringBuilder()).append("cgpan Map size = ")
						.append(cgpanMap.size()).toString());
		Map repayAmountMap = gmActionForm.getRepaymentAmount();
		Set repayAmountSet = repayAmountMap.keySet();
		Iterator repayAmountIterator = repayAmountSet.iterator();
		Log.log(4,
				"Validator",
				"validateRepaymentAmounts",
				(new StringBuilder()).append("Amount map size = ")
						.append(repayAmountMap.size()).toString());
		Map repayDateMap = gmActionForm.getRepaymentDate();
		Log.log(4,
				"Validator",
				"validateRepaymentAmounts",
				(new StringBuilder()).append("Date map size = ")
						.append(repayDateMap.size()).toString());
		Date repmtDate = null;
		SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd/MM/yyyy");
		boolean dateFlag = false;
		while (cgpanIterator.hasNext()) {
			cgpan = (String) cgpanMap.get(cgpanIterator.next());
			Log.log(4,
					"Validator",
					"validateRepaymentAmounts",
					(new StringBuilder())
							.append("Inside CgpanIterator cgpan  :")
							.append(cgpan).toString());
			repayAmountIterator = repayAmountSet.iterator();
			count = 0;
			if (repayDateMap.size() == 1)
				count = 1;
			for (; repayAmountIterator.hasNext(); count++) {
				repayAmtKey = (String) repayAmountIterator.next();
				key = (new StringBuilder()).append(cgpan).append("-")
						.append(count).toString();
				Log.log(4, "Validator", "validateRepaymentAmounts",
						(new StringBuilder()).append("key ->").append(key)
								.toString());
				if (key == null || key.equals("")
						|| !repayAmountMap.containsKey(key))
					continue;
				String repamt = (String) repayAmountMap.get(key);
				String repdate = (String) repayDateMap.get(key);
				Log.log(4,
						"Validator",
						"validateRepaymentAmounts",
						(new StringBuilder()).append("repdate :")
								.append(repdate).toString());
				Log.log(4, "Validator", "validateRepaymentAmounts",
						(new StringBuilder()).append("repamt :").append(repamt)
								.toString());
				if (repamt.equals("") || repdate.toString().equals(""))
					continue;
				dateFlag = true;
				break;
			}

		}
		if (!dateFlag) {
			ActionError actionError = new ActionError("enterRepaymentValues");
			errors.add("org.apache.struts.action.GLOBAL_ERROR", actionError);
		}
		return errors.isEmpty();
	}

	public static boolean validateRepaymentSchedule(Object bean,
			ValidatorAction validAction, Field field, ActionErrors errors,
			HttpServletRequest request) {
		GMActionForm gmActionForm = (GMActionForm) bean;
		SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd/MM/yyyy");
		Map cgpanMap = gmActionForm.getCgpans();
		Set cgpanSet = cgpanMap.keySet();
		Iterator cgpanIterator = cgpanSet.iterator();
		Map dueDateMap = gmActionForm.getFirstInstallmentDueDate();
		Log.log(4, "GMAction", "saveRepaymentSchedule",
				(new StringBuilder()).append("first installment due date ")
						.append(dueDateMap.toString()).toString());
		String key = null;
		boolean isDtFieldValid = true;
		while (cgpanIterator.hasNext()) {
			key = (String) cgpanIterator.next();
			String dateValue = (String) dueDateMap.get(key);
			SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
			Date tempDate = sdf.parse(dateValue, new ParsePosition(0));
			if (tempDate == null)
				isDtFieldValid = false;
			else
				isDtFieldValid = true;
			if (!isDtFieldValid) {
				ActionError actionError = new ActionError("enterValiddueDate");
				errors.add("org.apache.struts.action.GLOBAL_ERROR", actionError);
				break;
			}
		}
		return errors.isEmpty();
	}

	public static boolean validateIdsForClosure(Object bean,
			ValidatorAction validAction, Field field, ActionErrors errors,
			HttpServletRequest request) {
		Log.log(4, "Validator", "validateIdsForClosure", "Enetred");
		GMActionForm gmActionForm = (GMActionForm) bean;
		String memberId = gmActionForm.getMemberIdForClosure();
		String borrowerId = gmActionForm.getBorrowerIdForClosure();
		String cgpan = gmActionForm.getCgpanForClosure();
		String borrowerName = gmActionForm.getBorrowerNameForClosure();
		if (cgpan.equals("") && borrowerName.equals("")
				&& borrowerId.equals("")) {
			ActionError actionError = new ActionError("enterbidorcgpanorbName");
			errors.add("org.apache.struts.action.GLOBAL_ERROR", actionError);
		}
		if (!borrowerId.equals("")) {
			if (!borrowerName.equals("") || !cgpan.equals("")) {
				ActionError actionError = new ActionError(
						"enterbidorcgpanorbName");
				errors.add("org.apache.struts.action.GLOBAL_ERROR", actionError);
			}
		} else if (!cgpan.equals("")) {
			if (!borrowerName.equals("") || !borrowerId.equals("")) {
				ActionError actionError = new ActionError(
						"enterbidorcgpanorbName");
				errors.add("org.apache.struts.action.GLOBAL_ERROR", actionError);
			}
		} else if (!borrowerName.equals("")
				&& (!cgpan.equals("") || !borrowerId.equals(""))) {
			ActionError actionError = new ActionError("enterbidorcgpanorbName");
			errors.add("org.apache.struts.action.GLOBAL_ERROR", actionError);
		}
		Log.log(4, "Validator", "validateIdsForClosure", "Exited");
		return errors.isEmpty();
	}

	public static boolean checkForCl(Object bean, ValidatorAction validAction,
			Field field, ActionErrors errors, HttpServletRequest request) {
		Log.log(4, "Validator", "checkForCl", "Entered");
		GMActionForm gmActionForm = (GMActionForm) bean;
		String cgpan = null;
		Map cgpanMap = gmActionForm.getClCgpan();
		Set cgpanSet = cgpanMap.keySet();
		Iterator cgpanIterator = cgpanSet.iterator();
		Map reasonMap = gmActionForm.getReasonForCl();
		Map closeFlagMap = gmActionForm.getClFlag();
		Log.log(4,
				"Validator",
				"checkForCl",
				(new StringBuilder()).append("closeFlagMap Size")
						.append(closeFlagMap.size()).toString());
		if (closeFlagMap.size() == 0) {
			ActionError actionError = new ActionError("enterAtleastOneClosure");
			errors.add("org.apache.struts.action.GLOBAL_ERROR", actionError);
			return errors.isEmpty();
		}
		int i = 0;
		while (cgpanIterator.hasNext()) {
			cgpan = (String) cgpanIterator.next();
			Log.log(4,
					"Validator",
					"checkForCl",
					(new StringBuilder())
							.append("inside cgpan iterator cgpan : ")
							.append(cgpan).toString());
			Log.log(4, "Validator", "checkForCl",
					(new StringBuilder()).append("inside cgpan iterator CNT :")
							.append(++i).toString());
			Log.log(4,
					"Validator",
					"checkForCl",
					(new StringBuilder())
							.append("inside cgpan iterator reason :")
							.append(reasonMap.get(cgpan)).toString());
			Log.log(4,
					"Validator",
					"checkForCl",
					(new StringBuilder())
							.append("inside cgpan iterator Cl Flag :")
							.append(closeFlagMap.get(cgpan)).toString());
			if (reasonMap.get(cgpan).equals("")
					&& closeFlagMap.get(cgpan) != null) {
				Log.log(4, "Validator", "checkForCl", "inside if loop ");
				ActionError actionError = new ActionError(
						"enterReasonAndClosureflag");
				errors.add("org.apache.struts.action.GLOBAL_ERROR", actionError);
				break;
			}
		}
		Log.log(4, "Validator", "checkForCl", "Exited");
		return errors.isEmpty();
	}

	public static boolean checkForClosure(Object bean,
			ValidatorAction validAction, Field field, ActionErrors errors,
			HttpServletRequest request) {
		Log.log(4, "Validator", "checkForClosure", "Entered");
		GMActionForm gmActionForm = (GMActionForm) bean;
		String cgpan = null;
		Map cgpanMap = gmActionForm.getClosureCgpans();
		Set cgpanSet = cgpanMap.keySet();
		Iterator cgpanIterator = cgpanSet.iterator();
		Map reasonMap = gmActionForm.getReasonForClosure();
		Map closeFlagMap = gmActionForm.getClosureFlag();
		Log.log(4, "Validator", "checkForClosure", (new StringBuilder())
				.append("closeFlagMap Size").append(closeFlagMap.size())
				.toString());
		if (closeFlagMap.size() == 0) {
			ActionError actionError = new ActionError("enterAtleastOneClosure");
			errors.add("org.apache.struts.action.GLOBAL_ERROR", actionError);
			return errors.isEmpty();
		}
		int i = 0;
		while (cgpanIterator.hasNext()) {
			cgpan = (String) cgpanIterator.next();
			Log.log(4, "Validator", "checkForClosure", (new StringBuilder())
					.append("inside cgpan iterator cgpan : ").append(cgpan)
					.toString());
			Log.log(4, "Validator", "checkForClosure", (new StringBuilder())
					.append("inside cgpan iterator CNT :").append(++i)
					.toString());
			Log.log(4,
					"Validator",
					"checkForClosure",
					(new StringBuilder())
							.append("inside cgpan iterator reason :")
							.append(reasonMap.get(cgpan)).toString());
			Log.log(4,
					"Validator",
					"checkForClosure",
					(new StringBuilder())
							.append("inside cgpan iterator Flag :")
							.append(closeFlagMap.get(cgpan)).toString());
			if (reasonMap.get(cgpan).equals("")
					&& !closeFlagMap.get(cgpan).equals("")) {
				Log.log(4, "Validator", "checkForClosure", "inside if loop ");
				ActionError actionError = new ActionError(
						"enterReasonAndClosureflag");
				errors.add("org.apache.struts.action.GLOBAL_ERROR", actionError);
				break;
			}
		}
		Log.log(4, "Validator", "checkForClosure", "Exited");
		return errors.isEmpty();
	}

	public static boolean validateRecProcs(Object bean,
			ValidatorAction validAction, Field field, ActionErrors errors,
			HttpServletRequest request) {
		Log.log(4, "Validator", "validateRecProcs", "Entered");
		GMActionForm gmActionForm = (GMActionForm) bean;
		Map recMap = gmActionForm.getRecProcedures();
		Set recSet = recMap.keySet();
		Iterator recIterator = recSet.iterator();
		int size = recMap.size();
		Log.log(4, "Validator", "validateRecProcs", (new StringBuilder())
				.append("rec Map Size :").append(size).toString());
		while (recIterator.hasNext()) {
			String key = (String) recIterator.next();
			Log.log(4, "Validator", "validateRecProcs", (new StringBuilder())
					.append("key : ").append(key).toString());
			RecoveryProcedureTemp recTemp = (RecoveryProcedureTemp) recMap
					.get(key);
			Log.log(4, "Validator", "validateRecProcs", (new StringBuilder())
					.append("recTemp :").append(recTemp.getActionType())
					.toString());
			Log.log(4, "Validator", "validateRecProcs", (new StringBuilder())
					.append("recTemp :").append(recTemp.getActionDetails())
					.toString());
			Log.log(4, "Validator", "validateRecProcs", (new StringBuilder())
					.append("recTemp :").append(recTemp.getActionDate())
					.toString());
			String addFlag = request.getParameter("addmoreflag");
			if (!recTemp.getActionType().equals("")
					|| !recTemp.getActionDetails().equals("")
					|| !recTemp.getActionDate().toString().equals(""))
				continue;
			Log.log(4, "Validator", "validateRecProcs", "inside if loop ");
			if (size > 1) {
				Log.log(4, "Validator", "validateRecProcs",
						"inside if size >1 ");
				break;
			}
			ActionError actionError = new ActionError("enteratleastonerecproc");
			errors.add("org.apache.struts.action.GLOBAL_ERROR", actionError);
		}
		Log.log(4, "Validator", "validateRecProcs", "Exited");
		return errors.isEmpty();
	}

	public static boolean validateRecProcDate(Object bean,
			ValidatorAction validAction, Field field, ActionErrors errors,
			HttpServletRequest request) {
		Log.log(4, "Validator", "validateRecProcDate", "Entered");
		GMActionForm gmActionForm = (GMActionForm) bean;
		Map recMap = gmActionForm.getRecProcedures();
		Set recSet = recMap.keySet();
		Iterator recIterator = recSet.iterator();
		Date aDate = null;
		SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
		while (recIterator.hasNext()) {
			String key = (String) recIterator.next();
			Log.log(4, "Validator", "validateRecProcDate",
					(new StringBuilder()).append("key : ").append(key)
							.toString());
			RecoveryProcedureTemp recTemp = (RecoveryProcedureTemp) recMap
					.get(key);
			Log.log(4,
					"Validator",
					"validateRecProcDate",
					(new StringBuilder()).append("recTemp :")
							.append(recTemp.getActionType()).toString());
			Log.log(4,
					"Validator",
					"validateRecProcDate",
					(new StringBuilder()).append("recTemp :")
							.append(recTemp.getActionDetails()).toString());
			Log.log(4,
					"Validator",
					"validateRecProcDate",
					(new StringBuilder()).append("recTemp :")
							.append(recTemp.getActionDate()).toString());
			if (recTemp.getActionType() != null
					&& !recTemp.getActionType().equals("")
					&& (recTemp.getActionDetails() == null || recTemp
							.getActionDetails().equals(""))) {
				boolean remarksVal = false;
				for (Iterator errorsIterator = errors.get(); errorsIterator
						.hasNext();) {
					ActionError error = (ActionError) errorsIterator.next();
					if (error.getKey().equals("enterRecDetails")) {
						remarksVal = true;
						break;
					}
				}

				if (!remarksVal) {
					ActionError actionError = new ActionError("enterRecDetails");
					errors.add("org.apache.struts.action.GLOBAL_ERROR",
							actionError);
				}
			}
			if (recTemp.getActionType() != null
					&& !recTemp.getActionType().equals("")
					&& recTemp.getActionDetails() != null
					&& !recTemp.getActionDetails().equals("")
					&& (recTemp.getActionDate() == null || recTemp
							.getActionDate().toString().equals(""))) {
				boolean remarksVal = false;
				for (Iterator errorsIterator = errors.get(); errorsIterator
						.hasNext();) {
					ActionError error = (ActionError) errorsIterator.next();
					if (error.getKey().equals("enterRecdate")) {
						remarksVal = true;
						break;
					}
				}

				if (!remarksVal) {
					ActionError actionError = new ActionError("enterRecdate");
					errors.add("org.apache.struts.action.GLOBAL_ERROR",
							actionError);
				}
			}
			if (recTemp.getActionDate() != null
					&& !recTemp.getActionDate().toString().equals("")) {
				Log.log(4, "Validator", "validateRecProcDate",
						"inside if loop ");
				aDate = dateFormat.parse(recTemp.getActionDate().toString(),
						new ParsePosition(0));
				Log.log(4, "Validator", "validateRecProcDate",
						"dateFormat parse ");
				if (aDate == null) {
					Log.log(4, "Validator", "validateRecProcDate",
							"inside if aDate is null ");
					ActionError actionError = new ActionError(
							"entervalidrecdate");
					errors.add("org.apache.struts.action.GLOBAL_ERROR",
							actionError);
				} else {
					Date currentDate = new Date();
					aDate = dateFormat.parse(
							recTemp.getActionDate().toString(),
							new ParsePosition(0));
					if (aDate.compareTo(currentDate) == 1) {
						//System.out.println((new StringBuilder())
						//		.append("compare value :")
						//		.append(aDate.compareTo(currentDate))
						//		.toString());
						boolean remarksVal = false;
						for (Iterator errorsIterator = errors.get(); errorsIterator
								.hasNext();) {
							ActionError error = (ActionError) errorsIterator
									.next();
							if (error.getKey().equals(
									"currentDateRecActionDate")) {
								remarksVal = true;
								break;
							}
						}

						if (!remarksVal) {
							ActionError actionError = new ActionError(
									"currentDateRecActionDate");
							errors.add("org.apache.struts.action.GLOBAL_ERROR",
									actionError);
						}
					}
				}
			}
		}
		Log.log(4, "Validator", "validateRecProcs", "Exited");
		return errors.isEmpty();
	}

	public static boolean validateOneRowRecProcs(Object bean,
			ValidatorAction validAction, Field field, ActionErrors errors,
			HttpServletRequest request) {
		Log.log(4, "Validator", "validateRecProcs", "Entered");
		GMActionForm gmActionForm = (GMActionForm) bean;
		if (gmActionForm.getIsRecoveryInitiated().equals("N"))
			return true;
		Map recMap = gmActionForm.getRecProcedures();
		Set recSet = recMap.keySet();
		Iterator recIterator = recSet.iterator();
		Log.log(4, "Validator", "validateRecProcs", (new StringBuilder())
				.append("rec Map Size").append(recMap.size()).toString());
		while (recIterator.hasNext()) {
			String key = (String) recIterator.next();
			Log.log(4, "Validator", "validateRecProcs", (new StringBuilder())
					.append("key : ").append(key).toString());
			RecoveryProcedureTemp recTemp = (RecoveryProcedureTemp) recMap
					.get(key);
			Log.log(4, "Validator", "validateRecProcs", (new StringBuilder())
					.append("recTemp :").append(recTemp.getActionType())
					.toString());
			Log.log(4, "Validator", "validateRecProcs", (new StringBuilder())
					.append("recTemp :").append(recTemp.getActionDetails())
					.toString());
			Log.log(4, "Validator", "validateRecProcs", (new StringBuilder())
					.append("recTemp :").append(recTemp.getActionDate())
					.toString());
			String addFlag = request.getParameter("addmoreflag");
			if (recTemp.getActionType().equals("")
					&& recTemp.getActionDetails().equals("")
					&& recTemp.getActionDate().toString().equals("")) {
				Log.log(4, "Validator", "validateRecProcs", "inside if loop ");
				ActionError actionError = new ActionError("enterrecproc");
				errors.add("org.apache.struts.action.GLOBAL_ERROR", actionError);
				break;
			}
		}
		Log.log(4, "Validator", "validateRecProcs", "Exited");
		return errors.isEmpty();
	}

	public static boolean checkNPAValidations(Object bean,
			ValidatorAction validAction, Field field, ActionErrors errors,
			HttpServletRequest request) {
		String recInitiated = ValidatorUtil.getValueAsString(bean,
				field.getProperty());
		GMActionForm gmActionForm = (GMActionForm) bean;
		if (recInitiated.equals("Y")) {
			if (gmActionForm.getCourtName() == null
					|| gmActionForm.getCourtName().equals("")) {
				//System.out.println((new StringBuilder())
					//	.append("Court Name in Validation :")
					//	.append(gmActionForm.getCourtName()).toString());
				ActionError error = new ActionError("errors.required",
						"Forum through which legal proceedings were\tinitiated against borrower");
				errors.add("org.apache.struts.action.GLOBAL_ERROR", error);
			} else if (gmActionForm.getCourtName() != null
					&& !gmActionForm.getCourtName().equals("")
					&& gmActionForm.getCourtName().equals("others")
					&& (gmActionForm.getInitiatedName() == null || gmActionForm
							.getInitiatedName().equals(""))) {
				ActionError error = new ActionError("errors.required",
						"Forum through which legal proceedings were\tinitiated against borrower");
				errors.add("org.apache.struts.action.GLOBAL_ERROR", error);
			}
			if (gmActionForm.getLegalSuitNo() == null
					|| gmActionForm.getLegalSuitNo().equals("")) {
				ActionError error = new ActionError("errors.required",
						"Suit/Case Registration No");
				errors.add("org.apache.struts.action.GLOBAL_ERROR", error);
			}
			if (gmActionForm.getDtOfFilingLegalSuit() == null
					|| gmActionForm.getDtOfFilingLegalSuit().toString()
							.equals("")) {
				ActionError error = new ActionError("errors.required",
						"Filing Date ");
				errors.add("org.apache.struts.action.GLOBAL_ERROR", error);
			} else if (gmActionForm.getDtOfFilingLegalSuit() != null
					&& !gmActionForm.getDtOfFilingLegalSuit().equals("")) {
				SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
				try {
					Date filingdate = dateFormat.parse(gmActionForm
							.getDtOfFilingLegalSuit().toString(),
							new ParsePosition(0));
					if (filingdate == null) {
						ActionError actionError = new ActionError(
								"errors.date", "Filing Date");
						errors.add("org.apache.struts.action.GLOBAL_ERROR",
								actionError);
					} else {
						Date currentDate = new Date();
						filingdate = dateFormat.parse(gmActionForm
								.getDtOfFilingLegalSuit().toString(),
								new ParsePosition(0));
						try {
							String stringDate = dateFormat.format(currentDate);
							if (filingdate.compareTo(currentDate) == 1) {
								ActionError actionError = new ActionError(
										"currentDatelegaldate");
								errors.add(
										"org.apache.struts.action.GLOBAL_ERROR",
										actionError);
							}
						} catch (NumberFormatException numberFormatException) {
							ActionError actionError = new ActionError(
									"errors.date", "Filing Date");
							errors.add("org.apache.struts.action.GLOBAL_ERROR",
									actionError);
						}
					}
				} catch (Exception n) {
					ActionError actionError = new ActionError("errors.date",
							"Filing Date");
					errors.add("org.apache.struts.action.GLOBAL_ERROR",
							actionError);
				}
			}
			if (gmActionForm.getForumName() == null
					|| gmActionForm.getForumName().equals("")) {
				ActionError error = new ActionError("errors.required",
						"Forum Name");
				errors.add("org.apache.struts.action.GLOBAL_ERROR", error);
			}
			if (gmActionForm.getLocation() == null
					|| gmActionForm.getLocation().equals("")) {
				ActionError error = new ActionError("errors.required",
						"Location");
				errors.add("org.apache.struts.action.GLOBAL_ERROR", error);
			}
			Map recMap = gmActionForm.getRecProcedures();
			Set recSet = recMap.keySet();
			Iterator recIterator = recSet.iterator();
			int size = recMap.size();
			Log.log(4, "Validator", "validateRecProcs", (new StringBuilder())
					.append("rec Map Size :").append(size).toString());
			while (recIterator.hasNext()) {
				String key = (String) recIterator.next();
				Log.log(4, "Validator", "validateRecProcs",
						(new StringBuilder()).append("key : ").append(key)
								.toString());
				RecoveryProcedureTemp recTemp = (RecoveryProcedureTemp) recMap
						.get(key);
				Log.log(4,
						"Validator",
						"validateRecProcs",
						(new StringBuilder()).append("recTemp :")
								.append(recTemp.getActionType()).toString());
				Log.log(4,
						"Validator",
						"validateRecProcs",
						(new StringBuilder()).append("recTemp :")
								.append(recTemp.getActionDetails()).toString());
				Log.log(4,
						"Validator",
						"validateRecProcs",
						(new StringBuilder()).append("recTemp :")
								.append(recTemp.getActionDate()).toString());
				String addFlag = request.getParameter("addmoreflag");
				if (recTemp.getActionType().equals("")
						&& recTemp.getActionDetails().equals("")
						&& recTemp.getActionDate().toString().equals("")) {
					Log.log(4, "Validator", "validateRecProcs",
							"inside if loop ");
					if (size > 1) {
						Log.log(4, "Validator", "validateRecProcs",
								"inside if size >1 ");
						break;
					}
					ActionError actionError = new ActionError(
							"enteratleastonerecproc");
					errors.add("org.apache.struts.action.GLOBAL_ERROR",
							actionError);
				} else {
					Date aDate = null;
					SimpleDateFormat dateFormat = new SimpleDateFormat(
							"dd/MM/yyyy");
					if (recTemp.getActionType() != null
							&& !recTemp.getActionType().equals("")
							&& (recTemp.getActionDetails() == null || recTemp
									.getActionDetails().equals(""))) {
						boolean remarksVal = false;
						for (Iterator errorsIterator = errors.get(); errorsIterator
								.hasNext();) {
							ActionError error = (ActionError) errorsIterator
									.next();
							if (error.getKey().equals("enterRecDetails")) {
								remarksVal = true;
								break;
							}
						}

						if (!remarksVal) {
							ActionError actionError = new ActionError(
									"enterRecDetails");
							errors.add("org.apache.struts.action.GLOBAL_ERROR",
									actionError);
						}
					}
					if (recTemp.getActionType() != null
							&& !recTemp.getActionType().equals("")
							&& recTemp.getActionDetails() != null
							&& !recTemp.getActionDetails().equals("")
							&& (recTemp.getActionDate() == null || recTemp
									.getActionDate().toString().equals(""))) {
						boolean remarksVal = false;
						for (Iterator errorsIterator = errors.get(); errorsIterator
								.hasNext();) {
							ActionError error = (ActionError) errorsIterator
									.next();
							if (error.getKey().equals("enterRecdate")) {
								remarksVal = true;
								break;
							}
						}

						if (!remarksVal) {
							ActionError actionError = new ActionError(
									"enterRecdate");
							errors.add("org.apache.struts.action.GLOBAL_ERROR",
									actionError);
						}
					}
					if (recTemp.getActionDate() != null
							&& !recTemp.getActionDate().toString().equals("")) {
						Log.log(4, "Validator", "validateRecProcDate",
								"inside if loop ");
						aDate = dateFormat.parse(recTemp.getActionDate()
								.toString(), new ParsePosition(0));
						Log.log(4, "Validator", "validateRecProcDate",
								"dateFormat parse ");
						if (aDate == null) {
							Log.log(4, "Validator", "validateRecProcDate",
									"inside if aDate is null ");
							ActionError actionError = new ActionError(
									"entervalidrecdate");
							errors.add("org.apache.struts.action.GLOBAL_ERROR",
									actionError);
						} else {
							Date currentDate = new Date();
							aDate = dateFormat.parse(recTemp.getActionDate()
									.toString(), new ParsePosition(0));
							//System.out.println((new StringBuilder())
								//	.append("compare value :")
								//	.append(aDate.compareTo(currentDate))
								//	.toString());
							if (aDate.compareTo(currentDate) == 1) {
								//System.out.println((new StringBuilder())
								//		.append("compare value :")
								//		.append(aDate.compareTo(currentDate))
								//		.toString());
								boolean remarksVal = false;
								for (Iterator errorsIterator = errors.get(); errorsIterator
										.hasNext();) {
									ActionError error = (ActionError) errorsIterator
											.next();
									if (error.getKey().equals(
											"currentDateRecActionDate")) {
										remarksVal = true;
										break;
									}
								}

								if (!remarksVal) {
									ActionError actionError = new ActionError(
											"currentDateRecActionDate");
									errors.add(
											"org.apache.struts.action.GLOBAL_ERROR",
											actionError);
								}
							}
						}
					}
				}
			}
		}
		return errors.isEmpty();
	}

	public static boolean checkNpaDateRequired(Object bean,
			ValidatorAction validAction, Field field, ActionErrors errors,
			HttpServletRequest request) {
		String npaReported = ValidatorUtil.getValueAsString(bean,
				field.getProperty());
		String reportingDateVal = field.getVarValue("reportingDate");
		String reportingDate = ValidatorUtil.getValueAsString(bean,
				reportingDateVal);
		if (npaReported.equals("Y")
				&& (reportingDate == null || reportingDate.equals(""))) {
			ActionError error = new ActionError("npaDateRequired");
			errors.add("org.apache.struts.action.GLOBAL_ERROR", error);
		}
		return errors.isEmpty();
	}

	public static boolean checkReportingZoneSelected(Object bean,
			ValidatorAction validAction, Field field, ActionErrors errors,
			HttpServletRequest request) {
		Log.log(4, "Validator", "checkReportingZoneSelected", "Entered");
		DynaActionForm dynaForm = (DynaActionForm) bean;
		String zoOrRo = (String) dynaForm.get("setZoRo");
		Log.log(4,
				"Validator",
				"checkReportingZoneSelected",
				(new StringBuilder()).append("zone(ZO)or Region(RO) ")
						.append(zoOrRo).toString());
		if (zoOrRo != null && zoOrRo.equals("RO")) {
			String reportingZone = (String) dynaForm.get("reportingZone");
			Log.log(4,
					"Validator",
					"checkReportingZoneSelected",
					(new StringBuilder()).append("reportingZone ")
							.append(reportingZone).toString());
			if (reportingZone == null || reportingZone.equals("")) {
				ActionError actionError = new ActionError(
						"reportingZoneSelected");
				errors.add("org.apache.struts.action.GLOBAL_ERROR", actionError);
			}
		}
		Log.log(4, "Validator", "checkReportingZoneSelected", "Exited");
		return errors.isEmpty();
	}

	public static boolean selectZoneForBranch(Object bean,
			ValidatorAction validAction, Field field, ActionErrors errors,
			HttpServletRequest request) {
		Log.log(4, "Validator", "selectZoneForBranch", "Entered");
		DynaActionForm dynaForm = (DynaActionForm) bean;
		String zoOrRo = (String) dynaForm.get("setZoRo");
		Log.log(4,
				"Validator",
				"checkReportingZoneSelected",
				(new StringBuilder()).append("zone(ZO)or Region(RO) ")
						.append(zoOrRo).toString());
		if (zoOrRo != null && zoOrRo.equals("NBR")) {
			String zoneList = (String) dynaForm.get("zoneList");
			Log.log(4, "Validator", "selectZoneForBranch",
					(new StringBuilder()).append("zone  ").append(zoneList)
							.toString());
			if (zoneList == null || zoneList.equals("")) {
				ActionError actionError = new ActionError("selectZoneForBranch");
				errors.add("org.apache.struts.action.GLOBAL_ERROR", actionError);
			}
		}
		Log.log(4, "Validator", "selectZoneForBranch", "Exited");
		return errors.isEmpty();
	}

	public static boolean checkAlreadyExist(Object bean,
			ValidatorAction validAction, Field field, ActionErrors errors,
			HttpServletRequest request) {
		Log.log(4, "Validator", "checkAlreadyExist", "Entered");
		HttpSession session = request.getSession(false);
		session.setAttribute("modifiedInvesteeGroup", null);
		String textBoxProperty = field.getProperty();
		Log.log(4, "Validator", "checkAlreadyExist", (new StringBuilder())
				.append("textBoxProperty :").append(textBoxProperty).toString());
		String textBoxValue = ValidatorUtil.getValueAsString(bean,
				textBoxProperty).trim();
		Log.log(4, "Validator", "checkAlreadyExist", (new StringBuilder())
				.append("textBoxValue :").append(textBoxValue).toString());
		String comboBoxProperty = field.getVarValue("fromCombo");
		Log.log(4, "Validator", "checkAlreadyExist", (new StringBuilder())
				.append("textBoxProperty ").append(textBoxProperty).toString());
		Log.log(4, "Validator", "checkAlreadyExist", (new StringBuilder())
				.append("textBoxValue ").append(textBoxValue).toString());
		Log.log(4, "Validator", "checkAlreadyExist", (new StringBuilder())
				.append("comboBoxProperty ").append(comboBoxProperty)
				.toString());
		DynaActionForm dynaForm = (DynaActionForm) bean;
		Collection comboBox = null;
		if (dynaForm.get(comboBoxProperty) instanceof ArrayList)
			comboBox = (ArrayList) dynaForm.get(comboBoxProperty);
		else if (dynaForm.get(comboBoxProperty) instanceof Vector)
			comboBox = (Vector) dynaForm.get(comboBoxProperty);
		String modValueProperty = field.getVarValue("modValue");
		String modValue = ValidatorUtil
				.getValueAsString(bean, modValueProperty).trim();
		String mainProperty = field.getVarValue("mainProp");
		String mainValue = ValidatorUtil.getValueAsString(bean, mainProperty);
		if (mainValue == null)
			mainValue = "";
		if (modValue == null)
			modValue = "";
		if (textBoxValue == null)
			textBoxValue = "";
		Log.log(4, "Validator", "checkAlreadyExist", (new StringBuilder())
				.append("main value ").append(mainValue).toString());
		Log.log(4, "Validator", "checkAlreadyExist", (new StringBuilder())
				.append("mod value ").append(modValue).toString());
		Log.log(4, "Validator", "checkAlreadyExist", (new StringBuilder())
				.append("textbox value ").append(textBoxValue).toString());
		if (!mainValue.equals("") && modValue.equals("")) {
			Log.log(4, "Validator", "checkAlreadyExist",
					"combo has value but mod value is empty ");
			ActionError actionError = new ActionError((new StringBuilder())
					.append(modValueProperty).append("req").toString());
			errors.add("org.apache.struts.action.GLOBAL_ERROR", actionError);
		} else if (!textBoxValue.equals("")) {
			if (comboBox.contains(textBoxValue)) {
				Log.log(4, "Validator", "checkAlreadyExist",
						"new value is entered. but it is already present in combo ");
				ActionError actionError = new ActionError((new StringBuilder())
						.append(mainProperty).append("exist").toString());
				errors.add("org.apache.struts.action.GLOBAL_ERROR", actionError);
			} else if ((mainProperty.equalsIgnoreCase("budgetHead") || mainProperty
					.equalsIgnoreCase("budgetSubHeadTitle"))
					&& textBoxValue.indexOf('.') > -1) {
				Log.log(4, "Validator", "checkAlreadyExist",
						"new value is entered for budget head / sub head and contains '.'");
				ActionError actionError = new ActionError((new StringBuilder())
						.append(mainProperty).append("Dot").toString());
				errors.add("org.apache.struts.action.GLOBAL_ERROR", actionError);
			}
		} else if (!mainValue.equals("") && !modValue.equals("")
				&& !mainValue.equalsIgnoreCase(modValue))
			if (comboBox.contains(modValue)) {
				Log.log(4, "Validator", "checkAlreadyExist",
						"mod value is entered. but it is already present in combo ");
				ActionError actionError = new ActionError((new StringBuilder())
						.append(mainProperty).append("exist").toString());
				errors.add("org.apache.struts.action.GLOBAL_ERROR", actionError);
			} else if ((mainProperty.equalsIgnoreCase("budgetHead") || mainProperty
					.equalsIgnoreCase("budgetSubHeadTitle"))
					&& modValue.indexOf('.') > -1) {
				Log.log(4, "Validator", "checkAlreadyExist",
						"mod value is entered for budget head / sub head and contains '.'");
				ActionError actionError = new ActionError((new StringBuilder())
						.append(mainProperty).append("Dot").toString());
				errors.add("org.apache.struts.action.GLOBAL_ERROR", actionError);
			}
		session.setAttribute("modifiedInvesteeGroup", textBoxValue.trim());
		Log.log(4, "Validator", "checkAlreadyExist", "Exited");
		return errors.isEmpty();
	}

	public static boolean multiboxRequired(Object bean,
			ValidatorAction validAction, Field field, ActionErrors errors,
			HttpServletRequest request) {
		Log.log(4, "Validator", "multiboxRequired", "Entered");
		String multiboxProperty = field.getProperty();
		DynaActionForm dynaForm = (DynaActionForm) bean;
		String multiBoxArray[] = (String[]) dynaForm.get(multiboxProperty);
		int size = multiBoxArray.length;
		Log.log(4, "Validator", "multiboxRequired", (new StringBuilder())
				.append("multiBoxArray is   ").append(size).toString());
		if (size == 0) {
			ActionError actionError = new ActionError((new StringBuilder())
					.append("multibox").append(multiboxProperty).toString());
			errors.add("org.apache.struts.action.GLOBAL_ERROR", actionError);
		}
		Log.log(4, "Validator", "multiboxRequired", "Exited");
		return errors.isEmpty();
	}

	public static boolean bankRequired(Object bean,
			ValidatorAction validAction, Field field, ActionErrors errors,
			HttpServletRequest request) {
		Log.log(4, "Validator", "bankRequired", "Entered");
		DynaActionForm dynaForm = (DynaActionForm) bean;
		String bankName = (String) dynaForm.get("bankName");
		String radioChosen = (String) dynaForm.get("selectBM");
		Log.log(4, "Validator", "bankRequired",
				(new StringBuilder()).append("bankName is").append(bankName)
						.toString());
		Log.log(4,
				"Validator",
				"bankRequired",
				(new StringBuilder()).append("radioChosen is")
						.append(radioChosen).toString());
		if (!radioChosen.equals("AllHos") && bankName.equals("Select")) {
			ActionError actionError = new ActionError("bankRequired");
			errors.add("org.apache.struts.action.GLOBAL_ERROR", actionError);
		}
		Log.log(4, "Validator", "bankRequired", "Exited");
		return errors.isEmpty();
	}

	public static boolean zoneRequired(Object bean,
			ValidatorAction validAction, Field field, ActionErrors errors,
			HttpServletRequest request) {
		Log.log(4, "Validator", "zoneRequired", "Entered");
		DynaActionForm dynaForm = (DynaActionForm) bean;
		String zones[] = (String[]) dynaForm.get("zoneRegionNames");
		String zoneRegionName = null;
		if (zones.length != 0)
			zoneRegionName = zones[0];
		String radioChosen = (String) dynaForm.get("selectBM");
		Log.log(4,
				"Validator",
				"zoneRequired",
				(new StringBuilder()).append("zoneRegionName is")
						.append(zoneRegionName).toString());
		Log.log(4,
				"Validator",
				"zoneRequired",
				(new StringBuilder()).append("radioChosen is")
						.append(radioChosen).toString());
		if ((radioChosen.equals("membersOfZone") || radioChosen
				.equals("noOfZones")) && zoneRegionName.equals("None")) {
			ActionError actionError = new ActionError("zoneRequired");
			errors.add("org.apache.struts.action.GLOBAL_ERROR", actionError);
		}
		Log.log(4, "Validator", "zoneRequired", "Exited");
		return errors.isEmpty();
	}

	public static boolean branchRequired(Object bean,
			ValidatorAction validAction, Field field, ActionErrors errors,
			HttpServletRequest request) {
		Log.log(4, "Validator", "branchRequired", "Entered");
		DynaActionForm dynaForm = (DynaActionForm) bean;
		String branches[] = (String[]) dynaForm.get("branchNames");
		String branchName = null;
		if (branches.length != 0)
			branchName = branches[0];
		String radioChosen = (String) dynaForm.get("selectBM");
		Log.log(4, "Validator", "branchRequired",
				(new StringBuilder()).append("branchName is")
						.append(branchName).toString());
		Log.log(4,
				"Validator",
				"branchRequired",
				(new StringBuilder()).append("radioChosen is")
						.append(radioChosen).toString());
		if ((radioChosen.equals("membersOfBranch") || radioChosen
				.equals("noOfBranches")) && branchName.equals("None")) {
			ActionError actionError = new ActionError("branchRequired");
			errors.add("org.apache.struts.action.GLOBAL_ERROR", actionError);
		}
		Log.log(4, "Validator", "branchRequired", "Exited");
		return errors.isEmpty();
	}

	public static boolean bankIdNotZeros(Object bean,
			ValidatorAction validAction, Field field, ActionErrors errors,
			HttpServletRequest request) {
		Log.log(4, "Validator", "bankIdNotZeros", "Entered");
		String fieldValue = ValidatorUtil.getValueAsString(bean,
				field.getProperty());
		String bankId = fieldValue.substring(0, 4);
		Log.log(4,
				"Validator",
				"bankIdNotZeros",
				(new StringBuilder()).append("value entered -- ")
						.append(fieldValue).toString());
		if (bankId.equals("0000")) {
			Log.log(4, "Validator", "bankIdNotZeros", "adding error message");
			ActionError actionMessage = new ActionError("bankIdNotZeros");
			errors.add(field.getKey(),
					Resources.getActionError(request, validAction, field));
		}
		Log.log(4, "Validator", "bankIdNotZeros", "Exited");
		return errors.isEmpty();
	}

	public static boolean anyOneRequired(Object bean,
			ValidatorAction validAction, Field field, ActionErrors errors,
			HttpServletRequest request) {
		Log.log(4, "Validator", "anyOneRequired", "Entered");
		String comboBoxValue = ValidatorUtil.getValueAsString(bean,
				field.getProperty());
		String textBoxValue = field.getVarValue("textBoxValue");
		String textValue = ValidatorUtil.getValueAsString(bean, textBoxValue);
		Log.log(4,
				"Validator",
				"anyOneRequired",
				(new StringBuilder()).append("comboBoxValue ")
						.append(comboBoxValue).toString());
		Log.log(4,
				"Validator",
				"anyOneRequired",
				(new StringBuilder()).append("textBoxValue ")
						.append(textBoxValue).toString());
		Log.log(4, "Validator", "anyOneRequired",
				(new StringBuilder()).append("textValue ").append(textValue)
						.toString());
		Arg keyValue = field.getArg0();
		String key = keyValue.toString();
		Log.log(4, "Validator", "anyOneRequired",
				(new StringBuilder()).append("key ").append(key).toString());
		if (comboBoxValue.equals("") && textValue.equals("")) {
			ActionError actionMessage = new ActionError(key);
			errors.add(field.getKey(),
					Resources.getActionError(request, validAction, field));
		}
		Log.log(4, "Validator", "anyOneRequired", "Exited");
		return errors.isEmpty();
	}

	public static boolean defineOrgStr(Object bean,
			ValidatorAction validAction, Field field, ActionErrors errors,
			HttpServletRequest request) {
		Log.log(4, "Validator", "defineOrgStr", "Entered");
		DynaActionForm dynaForm = (DynaActionForm) bean;
		String radioChosen = (String) dynaForm.get("setZoRo");
		String newZone = (String) dynaForm.get("zoneName");
		String branch = (String) dynaForm.get("branchName");
		String zoneList = (String) dynaForm.get("zoneList");
		String reportingZone = (String) dynaForm.get("reportingZone");
		Log.log(4,
				"Validator",
				"defineOrgStr",
				(new StringBuilder()).append("radioChosen  ")
						.append(radioChosen).toString());
		Log.log(4, "Validator", "defineOrgStr",
				(new StringBuilder()).append("newZone").append(newZone)
						.toString());
		Log.log(4, "Validator", "defineOrgStr",
				(new StringBuilder()).append("branch ").append(branch)
						.toString());
		Log.log(4, "Validator", "defineOrgStr",
				(new StringBuilder()).append("zoneList").append(zoneList)
						.toString());
		Log.log(4,
				"Validator",
				"defineOrgStr",
				(new StringBuilder()).append("reportingZone")
						.append(reportingZone).toString());
		if (radioChosen.equals("ZO") && (newZone == null || newZone.equals(""))) {
			ActionError actionError = new ActionError("newZoneRequired");
			errors.add("org.apache.struts.action.GLOBAL_ERROR", actionError);
		}
		if (radioChosen.equals("RO") && (newZone == null || newZone.equals(""))) {
			ActionError actionError = new ActionError("newRegionRequired");
			errors.add("org.apache.struts.action.GLOBAL_ERROR", actionError);
		}
		if (radioChosen.equals("RO")
				&& (reportingZone == null || reportingZone.equals(""))) {
			ActionError actionError = new ActionError("reportingZoneRequired");
			errors.add("org.apache.struts.action.GLOBAL_ERROR", actionError);
		}
		if (radioChosen.equals("NBR") && (branch == null || branch.equals(""))) {
			ActionError actionError = new ActionError("branchRequired");
			errors.add("org.apache.struts.action.GLOBAL_ERROR", actionError);
		}
		if (radioChosen.equals("NBR")
				&& (zoneList == null || zoneList.equals(""))) {
			ActionError actionError = new ActionError("zoneRequired");
			errors.add("org.apache.struts.action.GLOBAL_ERROR", actionError);
		}
		if (radioChosen.equals("BRB") && (branch == null || branch.equals(""))) {
			ActionError actionError = new ActionError("branchRequired");
			errors.add("org.apache.struts.action.GLOBAL_ERROR", actionError);
		}
		Log.log(4, "Validator", "defineOrgStr", "Exited");
		return errors.isEmpty();
	}

	public static boolean plrChosenInModify(Object bean,
			ValidatorAction validAction, Field field, ActionErrors errors,
			HttpServletRequest request) {
		Log.log(4, "Validator", "plrChosenInModify", "Entered");
		AdministrationActionForm dynaForm = (AdministrationActionForm) bean;
		PLRMaster plrMaster = dynaForm.getPlrMaster();
		if (plrMaster == null) {
			Log.log(2, "Validator", "plrChosenInModify", "PLR master is empty");
			return false;
		}
		String PLR = plrMaster.getPLR();
		Log.log(4, "Validator", "plrChosenInModify", (new StringBuilder())
				.append("PLR :").append(PLR).toString());
		double BPLR = 0.0D;
		BPLR = plrMaster.getBPLR();
		Log.log(4, "Validator", "plrChosenInModify", (new StringBuilder())
				.append("PLR ").append(PLR).toString());
		Log.log(4, "Validator", "plrChosenInModify", (new StringBuilder())
				.append("BPLR ").append(BPLR).toString());
		if (PLR.equals("B") && BPLR == 0.0D) {
			ActionError actionError = new ActionError("BPLRRequired");
			errors.add("org.apache.struts.action.GLOBAL_ERROR", actionError);
		}
		Log.log(4, "Validator", "plrChosenInModify", "Exited");
		return errors.isEmpty();
	}

	public static boolean plrChosen(Object bean, ValidatorAction validAction,
			Field field, ActionErrors errors, HttpServletRequest request) {
		Log.log(4, "Validator", "plrChosen", "Entered");
		DynaActionForm dynaForm = (DynaActionForm) bean;
		String PLR = (String) dynaForm.get("PLR");
		Log.log(4, "Validator", "plrChosen",
				(new StringBuilder()).append("PLR :").append(PLR).toString());
		Double bplrObj = (Double) dynaForm.get("BPLR");
		double BPLR = 0.0D;
		if (bplrObj != null)
			BPLR = bplrObj.doubleValue();
		Log.log(4, "Validator", "plrChosen",
				(new StringBuilder()).append("PLR ").append(PLR).toString());
		Log.log(4, "Validator", "plrChosen",
				(new StringBuilder()).append("BPLR ").append(BPLR).toString());
		if (PLR.equals("B") && BPLR == 0.0D) {
			ActionError actionError = new ActionError("BPLRRequired");
			errors.add("org.apache.struts.action.GLOBAL_ERROR", actionError);
		}
		Log.log(4, "Validator", "plrChosen", "Exited");
		return errors.isEmpty();
	}

	public static boolean validateFromNPACurrentDates(Object bean,
			ValidatorAction validAction, Field field, ActionErrors errors,
			HttpServletRequest request) throws Exception {
		GMActionForm gmPeriodicInfoForm = (GMActionForm) bean;
		GMDAO gmDAO = new GMDAO();
		String fromValue = ValidatorUtil.getValueAsString(bean,
				field.getProperty());
		String fromString = field.getProperty();
		Date npaDate = gmPeriodicInfoForm.getNpaDate();
		String borrowerId = gmPeriodicInfoForm.getBorrowerId().toUpperCase();
		int count = gmDAO.getExceptionBIDCount(borrowerId);
	///	System.out.println((new StringBuilder())
		//		.append("Borrower Id for Update Npa details:")
		//		.append(borrowerId).toString());
		Date currentDate = new Date();
		SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
		String toValue = null;
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(currentDate);
		int year = calendar.get(Calendar.YEAR);
		int month = calendar.get(Calendar.MONTH);
		int day = calendar.get(Calendar.DATE);
		int temp = 0;
		if (month >= 0 && month <= 2) {
			year--;
			calendar.set(Calendar.MONTH, 9);
			calendar.set(Calendar.DATE, 1);
			calendar.set(Calendar.YEAR, year);
			temp = 0;
		} else if (month >= 3 && month <= 5) {
			calendar.set(Calendar.MONTH, 0);
			calendar.set(Calendar.DATE, 1);
			calendar.set(Calendar.YEAR, year);
			temp = 1;
		} else if (month >= 6 && month <= 8) {
			calendar.set(Calendar.MONTH, 3);
			calendar.set(Calendar.DATE, 1);
			calendar.set(1, year);
			temp = 2;
		} else if (month >= 9 && month <= 11) {
			calendar.set(Calendar.MONTH, 6);
			calendar.set(Calendar.DATE, 1);
			calendar.set(1, year);
			temp = 3;
		}
		Date toDate = calendar.getTime();
		toValue = dateFormat.format(toDate);
		try {
			String stringDate = dateFormat.format(currentDate);
			if (!GenericValidator.isBlankOrNull(fromValue)) {
				if (DateHelper.compareDates(fromValue, stringDate) != 0
						&& DateHelper.compareDates(fromValue, stringDate) != 1) {
					ActionError actionError = new ActionError(
							(new StringBuilder()).append("currentDate")
									.append(fromString).toString());
					errors.add("org.apache.struts.action.GLOBAL_ERROR",
							actionError);
				}
				if (count == 0
						&& DateHelper.compareDates(toValue, fromValue) != 0
						&& DateHelper.compareDates(toValue, fromValue) != 1) {
					ActionError actionError = new ActionError(
							(new StringBuilder()).append("newNpaDate")
									.append(temp).toString());
					errors.add("org.apache.struts.action.GLOBAL_ERROR",
							actionError);
				}
			}
		} catch (NumberFormatException numberFormatException) {
			ActionError actionError = new ActionError("errors.date", fromString);
			errors.add("org.apache.struts.action.GLOBAL_ERROR", actionError);
		}
		return errors.isEmpty();
	}

	public static boolean validateFromCurrentDates(Object bean,
			ValidatorAction validAction, Field field, ActionErrors errors,
			HttpServletRequest request) throws Exception {
		String fromValue = ValidatorUtil.getValueAsString(bean,
				field.getProperty());
		String fromString = field.getProperty();
		Date currentDate = new Date();
		SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
		String stringDate = dateFormat.format(currentDate);
		if (!GenericValidator.isBlankOrNull(fromValue)
				&& DateHelper.compareDates(fromValue, stringDate) != 0
				&& DateHelper.compareDates(fromValue, stringDate) != 1) {
			ActionError actionError = new ActionError((new StringBuilder())
					.append("currentDate").append(fromString).toString());
			errors.add("org.apache.struts.action.GLOBAL_ERROR", actionError);
		}
		return errors.isEmpty();
	}

	public static boolean validateAmountValue(Object bean,
			ValidatorAction validAction, Field field, ActionErrors errors,
			HttpServletRequest request) throws Exception {
		String amountValue = ValidatorUtil.getValueAsString(bean,
				field.getProperty());
		String amountProperty = field.getProperty();
		Double amountVal = new Double(amountValue);
		double amtValue = 0.0;
		if (amountVal != null) {
			amtValue = amountVal.doubleValue();
		}
		if ((amtValue > 0.0D || amtValue > 0.0D) && amtValue < 100D) {
			ActionError actionError = new ActionError((new StringBuilder())
					.append("amtgreater").append(amountProperty).toString());
			errors.add("org.apache.struts.action.GLOBAL_ERROR", actionError);
		}
		return errors.isEmpty();
	}

	public static boolean validateRSFAmountValue(Object bean,
			ValidatorAction validAction, Field field, ActionErrors errors,
			HttpServletRequest request) throws Exception {
		String amountValue = ValidatorUtil.getValueAsString(bean,
				field.getProperty());
		String amountProperty = field.getProperty();
		Double amountVal = new Double(amountValue);
		double amtValue = amountVal.doubleValue();
		if ((amtValue > 0.0D || amtValue > 0.0D) && amtValue < 5000000D) {
			ActionError actionError = new ActionError((new StringBuilder())
					.append("amtgreater").append(amountProperty).toString());
			errors.add("org.apache.struts.action.GLOBAL_ERROR", actionError);
		}
		return errors.isEmpty();
	}

	public static boolean validateAfterCurrentDates(Object bean,
			ValidatorAction validAction, Field field, ActionErrors errors,
			HttpServletRequest request) throws Exception {
		String fromValue = ValidatorUtil.getValueAsString(bean,
				field.getProperty());
		String fromString = field.getProperty();
		Date currentDate = new Date();
		SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
		try {
			String stringDate = dateFormat.format(currentDate);
			if (!GenericValidator.isBlankOrNull(fromValue)
					&& DateHelper.compareDates(fromValue, stringDate) != -1
					&& DateHelper.compareDates(fromValue, stringDate) != 0) {
				ActionError actionError = new ActionError((new StringBuilder())
						.append("futureDate").append(fromString).toString());
				errors.add("org.apache.struts.action.GLOBAL_ERROR", actionError);
			}
		} catch (NumberFormatException numberFormatException) {
			ActionError actionError = new ActionError((new StringBuilder())
					.append(fromString).append(" is not a valid Date")
					.toString());
			errors.add("org.apache.struts.action.GLOBAL_ERROR", actionError);
		}
		return errors.isEmpty();
	}

	public static boolean validateApplicationReport(Object bean,
			ValidatorAction validAction, Field field, ActionErrors errors,
			HttpServletRequest request) {
		DynaActionForm dynaForm = (DynaActionForm) bean;
		String selectAll = request.getParameter("selectAll");
		String applicationDate = request.getParameter("applicationDate");
		String promoter = request.getParameter("promoter");
		String itpan = request.getParameter("itpan");
		String ssiDetails = request.getParameter("ssiDetails");
		String industryType = request.getParameter("industryType");
		String termCreditSanctioned = request
				.getParameter("termCreditSanctioned");
		String tcInterest = request.getParameter("tcInterest");
		String tcTenure = request.getParameter("tcTenure");
		String tcPlr = request.getParameter("tcPlr");
		String tcOutlay = request.getParameter("tcOutlay");
		String workingCapitalSanctioned = request.getParameter("wcPlr");
		String wcPlr = request.getParameter("wcPlr");
		String wcOutlay = request.getParameter("wcOutlay");
		String rejection = request.getParameter("rejection");
		if ((applicationDate == null || applicationDate.equals(""))
				&& (promoter == null || promoter.equals(""))
				&& (itpan == null || itpan.equals(""))
				&& (ssiDetails == null || ssiDetails.equals(""))
				&& (industryType == null || industryType.equals(""))
				&& (termCreditSanctioned == null || termCreditSanctioned
						.equals(""))
				&& (tcInterest == null || tcInterest.equals(""))
				&& (tcTenure == null || tcTenure.equals(""))
				&& (tcPlr == null || tcPlr.equals(""))
				&& (tcOutlay == null || tcOutlay.equals(""))
				&& (workingCapitalSanctioned == null || workingCapitalSanctioned
						.equals("")) && (wcPlr == null || wcPlr.equals(""))
				&& (wcOutlay == null || wcOutlay.equals(""))
				&& (rejection == null || rejection.equals(""))) {
			ActionError actionError = new ActionError("enterAnyOneField");
			errors.add("org.apache.struts.action.GLOBAL_ERROR", actionError);
		}
		return errors.isEmpty();
	}

	public static boolean searchDocument(Object bean,
			ValidatorAction validAction, Field field, ActionErrors errors,
			HttpServletRequest request) {
		DynaActionForm dynaForm = (DynaActionForm) bean;
		String fileNumber = (String) dynaForm.get("fileNumber");
		String fileTitle = (String) dynaForm.get("fileTitle");
		String subject = (String) dynaForm.get("subject");
		String category = (String) dynaForm.get("category");
		Date dateOfTheDocument = (Date) dynaForm.get("dateOfTheDocument");
		String documentDate = dateOfTheDocument.toString();
		String remarks = (String) dynaForm.get("remarks");
		if ((fileNumber == null || fileNumber.equals(""))
				&& (fileTitle == null || fileTitle.equals(""))
				&& (subject == null || subject.equals(""))
				&& (category == null || category.equals(""))
				&& (documentDate == null || documentDate.equals(""))
				&& (remarks == null || remarks.equals(""))) {
			ActionError actionError = new ActionError("enterAnyOneField");
			errors.add("org.apache.struts.action.GLOBAL_ERROR", actionError);
		}
		return errors.isEmpty();
	}

	public static boolean outwardIdValidation(Object bean,
			ValidatorAction validAction, Field field, ActionErrors errors,
			HttpServletRequest request) throws DatabaseException {
		DynaActionForm dynaForm = (DynaActionForm) bean;
		String mappedOutward = (String) dynaForm.get("mappedOutwardID");
		String memberId = null;
		ArrayList OfmemberIds = new ArrayList();
		IOProcessor ioprocessor = new IOProcessor();
		ArrayList outwardIds = new ArrayList();
		outwardIds = ioprocessor.getAllOutwardIds();
		int count = 0;
		String tempId = "";
		Inward inward = new Inward();
		if (mappedOutward != null && !mappedOutward.equals("")) {
			for (StringTokenizer stringTokenizer = new StringTokenizer(
					mappedOutward, ","); stringTokenizer.hasMoreTokens();) {
				memberId = stringTokenizer.nextToken();
				String newMemberId1 = memberId.trim();
				String newMemberId2 = newMemberId1.toUpperCase();
				if (!OfmemberIds.contains(newMemberId2))
					OfmemberIds.add(newMemberId2);
			}

			int OfmemberIdsSize = OfmemberIds.size();
			for (int i = 0; i < OfmemberIdsSize; i++) {
				String id = (String) OfmemberIds.get(i);
				String newId = id.trim();
				String newOutwardId = newId.toUpperCase();
				if (outwardIds.contains(newOutwardId)) {
					if (++count == OfmemberIdsSize) {
						for (int j = 0; j < OfmemberIdsSize; j++) {
							String mappedInwardId = (String) OfmemberIds.get(j);
							String newMappedInwardId = mappedInwardId.trim();
							String mappedInwardId1 = newMappedInwardId
									.toUpperCase();
							String mappedInwardId2 = (new StringBuilder())
									.append(mappedInwardId1).append(",")
									.toString();
							tempId = (new StringBuilder()).append(tempId)
									.append(mappedInwardId2).toString();
						}

					}
					continue;
				}
				ActionError actionError = new ActionError("enterValidOutwardId");
				errors.add("org.apache.struts.action.GLOBAL_ERROR", actionError);
				break;
			}

		}
		return errors.isEmpty();
	}

	public static boolean inwardIdValidation(Object bean,
			ValidatorAction validAction, Field field, ActionErrors errors,
			HttpServletRequest request) throws DatabaseException {
		DynaActionForm dynaForm = (DynaActionForm) bean;
		String mappedInward = (String) dynaForm.get("mappedInward");
		String memberId = null;
		ArrayList OfmemberIds = new ArrayList();
		IOProcessor ioprocessor = new IOProcessor();
		ArrayList outwardIds = new ArrayList();
		outwardIds = ioprocessor.getAllInwardIds();
		int count = 0;
		String tempId = "";
		Inward inward = new Inward();
		if (mappedInward != null && !mappedInward.equals("")) {
			for (StringTokenizer stringTokenizer = new StringTokenizer(
					mappedInward, ","); stringTokenizer.hasMoreTokens();) {
				memberId = stringTokenizer.nextToken();
				String newMemberId1 = memberId.trim();
				String newMemberId2 = newMemberId1.toUpperCase();
				if (!OfmemberIds.contains(newMemberId2))
					OfmemberIds.add(newMemberId2);
			}

			int OfmemberIdsSize = OfmemberIds.size();
			for (int i = 0; i < OfmemberIdsSize; i++) {
				String id = (String) OfmemberIds.get(i);
				String newId = id.trim();
				String newOutwardId = newId.toUpperCase();
				if (outwardIds.contains(newOutwardId)) {
					if (++count == OfmemberIdsSize) {
						for (int j = 0; j < OfmemberIdsSize; j++) {
							String mappedInwardId = (String) OfmemberIds.get(j);
							String newMappedInwardId = mappedInwardId.trim();
							String mappedInwardId1 = newMappedInwardId
									.toUpperCase();
							String mappedInwardId2 = (new StringBuilder())
									.append(mappedInwardId1).append(",")
									.toString();
							tempId = (new StringBuilder()).append(tempId)
									.append(mappedInwardId2).toString();
						}

					}
					continue;
				}
				ActionError actionError = new ActionError("enterValidInwardId");
				errors.add("org.apache.struts.action.GLOBAL_ERROR", actionError);
				break;
			}

		}
		return errors.isEmpty();
	}

	public static boolean idForCategory(Object bean,
			ValidatorAction validAction, Field field, ActionErrors errors,
			HttpServletRequest request) throws DatabaseException {
		DynaActionForm dynaForm = (DynaActionForm) bean;
		String category = (String) dynaForm.get("category");
		String inResponseToID = (String) dynaForm.get("inResponseToID");
		String newId = inResponseToID.toUpperCase();
		ArrayList outwardIds = new ArrayList();
		ArrayList inwardIds = new ArrayList();
		IOProcessor ioprocessor = new IOProcessor();
		outwardIds = ioprocessor.getAllOutwardIds();
		inwardIds = ioprocessor.getAllInwardIds();
		if (category.equals("Inward")) {
			if (!inwardIds.contains(newId)) {
				ActionError actionError = new ActionError("enterValidInwardId");
				errors.add("org.apache.struts.action.GLOBAL_ERROR", actionError);
			}
		} else if (category.equals("Outward") && !outwardIds.contains(newId)) {
			ActionError actionError = new ActionError("enterValidOutwardId");
			errors.add("org.apache.struts.action.GLOBAL_ERROR", actionError);
		}
		return errors.isEmpty();
	}

	public static boolean fileInwardValidation(Object bean,
			ValidatorAction validAction, Field field, ActionErrors errors,
			HttpServletRequest request) throws Exception {
		DynaActionForm dynaForm = (DynaActionForm) bean;
		FormFile file = (FormFile) dynaForm.get("filePathInward");
		if (file != null && !file.toString().equals("")) {
			String contextPath = request.getSession().getServletContext()
					.getRealPath("");
			String path = PropertyLoader.changeToOSpath((new StringBuilder())
					.append(contextPath).append("/")
					.append("WEB-INF/FileUpload").append(File.separator)
					.append(file.getFileName()).toString());
			String fileName = file.getFileName();
			int index = fileName.lastIndexOf(".");
			if (index == -1) {
				ActionError actionError = new ActionError("enterValidFile");
				errors.add("org.apache.struts.action.GLOBAL_ERROR", actionError);
			}
		}
		return errors.isEmpty();
	}

	public static boolean fileOutwardValidation(Object bean,
			ValidatorAction validAction, Field field, ActionErrors errors,
			HttpServletRequest request) throws Exception {
		DynaActionForm dynaForm = (DynaActionForm) bean;
		FormFile file = (FormFile) dynaForm.get("filePathOutward");
		if (file != null && !file.toString().equals("")) {
			String contextPath = request.getSession().getServletContext()
					.getRealPath("");
			String path = PropertyLoader.changeToOSpath((new StringBuilder())
					.append(contextPath).append("/")
					.append("WEB-INF/FileUpload").append(File.separator)
					.append(file.getFileName()).toString());
			String fileName = file.getFileName();
			int index = fileName.lastIndexOf(".");
			if (index == -1) {
				ActionError actionError = new ActionError("enterValidFile");
				errors.add("org.apache.struts.action.GLOBAL_ERROR", actionError);
			}
		}
		return errors.isEmpty();
	}

	public static boolean checkQueryFieldsSelected(Object bean,
			ValidatorAction validAction, Field field, ActionErrors errors,
			HttpServletRequest request) {
		Log.log(4, "Validator", "checkQueryFieldsSelected", "Entered");
		ReportActionForm reportForm = (ReportActionForm) bean;
		QueryBuilderFields queryFields = reportForm.getQueryBuilderFields();
		if (!queryFields.isApplnRefnoSelChkBox()
				&& !queryFields.isCgpanSelChkBox()
				&& !queryFields.isApprovedAmtSelChkBox()
				&& !queryFields.isApprovedDateSelChkBox()
				&& !queryFields.isAppSubmittedSelChkBox()
				&& !queryFields.isBankRefNoSelChkBox()
				&& !queryFields.isChiefPromoterSelChkBox()
				&& !queryFields.isGuarFeeSelChkBox()
				&& !queryFields.isGuarFeeStartDateSelChkBox()
				&& !queryFields.isItPANSelChkBox()
				&& !queryFields.isProjectOutlaySelChkBox()
				&& !queryFields.isSsiDetailsSelChkBox()
				&& !queryFields.isTcInterestRateSelChkBox()
				&& !queryFields.isTcPLRSelChkBox()
				&& !queryFields.isTcSanctionedSelChkBox()
				&& !queryFields.isTcTenureSelChkBox()
				&& !queryFields.isWcPLRSelChkBox()
				&& !queryFields.isWcSanctionedSelChkBox()) {
			Log.log(4, "Validator", "checkQueryFieldsSelected",
					"Found Errors...");
			ActionError actionMessage = new ActionError(
					"QuerySelectionRequired");
			errors.add("org.apache.struts.action.GLOBAL_ERROR", actionMessage);
		}
		Log.log(4, "Validator", "checkQueryFieldsSelected", "Exited");
		return errors.isEmpty();
	}

	public static boolean validateAmounts(Object bean,
			ValidatorAction validAction, Field field, ActionErrors errors,
			HttpServletRequest request) {
		String amount1 = ValidatorUtil.getValueAsString(bean,
				field.getProperty());
		String sProperty2 = field.getVarValue("amount2");
		String amount2 = ValidatorUtil.getValueAsString(bean, sProperty2);
		if (!GenericValidator.isBlankOrNull(amount1)
				&& Double.parseDouble(amount1) == 0.0D)
			amount1 = "";
		if (!GenericValidator.isBlankOrNull(amount2)
				&& Double.parseDouble(amount2) == 0.0D)
			amount2 = "";
		if (!amount1.equals("") && !amount2.equals("")
				&& Double.parseDouble(amount1) > Double.parseDouble(amount2)) {
			ActionError actionMessage = new ActionError((new StringBuilder())
					.append("amount1GT").append(sProperty2).toString());
			errors.add("org.apache.struts.action.GLOBAL_ERROR", actionMessage);
		}
		return errors.isEmpty();
	}

	public static boolean checkTransactionDetails(Object bean,
			ValidatorAction validAction, Field field, ActionErrors errors,
			HttpServletRequest request) {
		Log.log(4, "Validator", "checkTransactionDetails", "Entered");
		DynaActionForm form = (DynaActionForm) bean;
		Map transactions = (Map) form.get("transactions");
		Set keys = transactions.keySet();
		Iterator iterator = keys.iterator();
		boolean fromToRequired = true;
		boolean natureRequired = true;
		boolean dateRequired = true;
		boolean valueDateRequired = true;
		boolean chequeNumberRequired = true;
		boolean amountRequired = true;
		while (iterator.hasNext()) {
			Object key = iterator.next();
			TransactionDetail transactionDetail = (TransactionDetail) transactions
					.get(key);
			if (fromToRequired
					&& (transactionDetail.getTransactionFromTo() == null || transactionDetail
							.getTransactionFromTo().equals(""))) {
				ActionError actionMessage = new ActionError(
						"transactionFromToRequired");
				errors.add("org.apache.struts.action.GLOBAL_ERROR",
						actionMessage);
				fromToRequired = false;
			}
			Log.log(4,
					"Validator",
					"checkTransactionDetails",
					(new StringBuilder()).append(" Transaction Date ")
							.append(transactionDetail.getTransactionDate())
							.toString());
			if (dateRequired)
				if (transactionDetail.getTransactionDate() == null
						|| transactionDetail.getTransactionDate().equals("")) {
					Log.log(4,
							"Validator",
							"checkTransactionDetails",
							(new StringBuilder())
									.append("Date not entered:")
									.append(transactionDetail
											.getTransactionDate()).toString());
					ActionError actionMessage = new ActionError(
							"transactionDateRequired");
					errors.add("org.apache.struts.action.GLOBAL_ERROR",
							actionMessage);
					dateRequired = false;
				} else {
					String transactionDate = transactionDetail
							.getTransactionDate();
					if (transactionDate.trim().length() < 10) {
						String errorStrs[] = new String[2];
						errorStrs[0] = "Transaction Date ";
						errorStrs[1] = "10";
						ActionError error = new ActionError("errors.minlength",
								errorStrs);
						errors.add("org.apache.struts.action.GLOBAL_ERROR",
								error);
						dateRequired = false;
						Log.log(4, "checkTransactionDetails", "validate",
								" length is less than zero");
					} else {
						SimpleDateFormat dateFormat = new SimpleDateFormat(
								"dd/MM/yyyy");
						Date date = dateFormat.parse(transactionDate,
								new ParsePosition(0));
						Log.log(4,
								"checkTransactionDetails",
								"validate",
								(new StringBuilder()).append(" date ")
										.append(date).toString());
						if (date == null) {
							ActionError error = new ActionError("errors.date",
									"Transaction Date");
							errors.add("org.apache.struts.action.GLOBAL_ERROR",
									error);
							dateRequired = false;
						}
					}
				}
			if (amountRequired
					&& transactionDetail.getTransactionAmount() == 0.0D) {
				ActionError actionMessage = new ActionError(
						"transactionAmountRequired");
				errors.add("org.apache.struts.action.GLOBAL_ERROR",
						actionMessage);
				amountRequired = false;
			}
		}
		Log.log(4, "Validator", "checkTransactionDetails", "Exited");
		return errors.isEmpty();
	}

	public static boolean validateDefaulterReportFields(Object bean,
			ValidatorAction validAction, Field field, ActionErrors errors,
			HttpServletRequest request) {
		Log.log(4, "Validator", "validateDefaulterReportFields", "Entered");
		DynaActionForm dynaForm = (DynaActionForm) bean;
		String borrowerUnitName = ((String) dynaForm.get("borrUnitName"))
				.trim();
		String itpanOfTheUnit = ((String) dynaForm.get("itpanOfTheUnit"))
				.trim();
		String chiefPromoterName = ((String) dynaForm.get("chiefPromoterName"))
				.trim();
		Date chiefPromoterDOB = (Date) dynaForm.get("chiefPromoterDOB");
		String itpanOfChiefPromoter = ((String) dynaForm
				.get("itpanOfTheChiefPromoter")).trim();
		String legalIdOfChiefPromoter = ((String) dynaForm
				.get("legalIDOfTheChiefPromoter")).trim();
		Log.log(4,
				"Validator",
				"validateDefaulterReportFields",
				(new StringBuilder()).append("borrowerUnitName :")
						.append(borrowerUnitName).toString());
		Log.log(4,
				"Validator",
				"validateDefaulterReportFields",
				(new StringBuilder()).append("itpanOfTheUnit :")
						.append(itpanOfTheUnit).toString());
		Log.log(4,
				"Validator",
				"validateDefaulterReportFields",
				(new StringBuilder()).append("chiefPromoterName :")
						.append(chiefPromoterName).toString());
		Log.log(4,
				"Validator",
				"validateDefaulterReportFields",
				(new StringBuilder()).append("chiefPromoterDOB :")
						.append(chiefPromoterDOB).toString());
		Log.log(4,
				"Validator",
				"validateDefaulterReportFields",
				(new StringBuilder()).append("itpanOfChiefPromoter :")
						.append(itpanOfChiefPromoter).toString());
		Log.log(4, "Validator", "validateDefaulterReportFields",
				(new StringBuilder()).append("legalIdOfChiefPromoter :")
						.append(legalIdOfChiefPromoter).toString());
		if (borrowerUnitName != null && borrowerUnitName.equals("")
				&& itpanOfTheUnit != null && itpanOfTheUnit.equals("")
				&& chiefPromoterName != null && chiefPromoterName.equals("")
				&& chiefPromoterDOB != null
				&& chiefPromoterDOB.toString().equals("")
				&& itpanOfChiefPromoter != null
				&& itpanOfChiefPromoter.equals("")
				&& legalIdOfChiefPromoter != null
				&& legalIdOfChiefPromoter.equals("")) {
			Log.log(4, "Validator", "validateDefaulterReportFields",
					"INVALID INPUT");
			ActionError actionMessage = new ActionError(
					"invalidDefaulterReportInput");
			errors.add("org.apache.struts.action.GLOBAL_ERROR", actionMessage);
		}
		return errors.isEmpty();
	}

	public static boolean validateBuySell(Object bean,
			ValidatorAction validAction, Field field, ActionErrors errors,
			HttpServletRequest request) {
		String instValue = ValidatorUtil.getValueAsString(bean,
				field.getProperty());
		String sProperty2 = field.getVarValue("noOfUnits");
		String noOfUnitsValue = ValidatorUtil
				.getValueAsString(bean, sProperty2);
		String sProperty3 = field.getVarValue("isBuyOrSellRequest");
		String buySellValue = ValidatorUtil.getValueAsString(bean, sProperty3);
		String sProperty4 = field.getVarValue("investmentReferenceNumber");
		String invRefNoValue = ValidatorUtil.getValueAsString(bean, sProperty4);
		if (buySellValue.equalsIgnoreCase("S")
				&& GenericValidator.isBlankOrNull(invRefNoValue)) {
			Log.log(4, "Validator", "validateBuySell",
					"inv ref no not selected");
			ActionError actionMessage = new ActionError("errors.required",
					"Investment Reference Number");
			errors.add("org.apache.struts.action.GLOBAL_ERROR", actionMessage);
		}
		if (!GenericValidator.isBlankOrNull(instValue)
				&& !instValue.equalsIgnoreCase("FIXED DEPOSIT")
				&& GenericValidator.isBlankOrNull(noOfUnitsValue)) {
			Log.log(4, "Validator", "validateBuySell", "units not entered");
			ActionError actionMessage = new ActionError("errors.required",
					"Number Of Units");
			errors.add("org.apache.struts.action.GLOBAL_ERROR", actionMessage);
		}
		return errors.isEmpty();
	}

	public static boolean checkDefaultRate(Object bean,
			ValidatorAction validAction, Field field, ActionErrors errors,
			HttpServletRequest request) {
		Log.log(4, "Validator", "checkDefaultRate", "entered");
		String isDefaultRateApplicable = ValidatorUtil.getValueAsString(bean,
				field.getProperty());
		Log.log(4, "Validator", "checkDefaultRate",
				(new StringBuilder()).append("isDefaultRateApplicable :")
						.append(isDefaultRateApplicable).toString());
		String defaultRate = field.getVarValue("defaultRate");
		Log.log(4, "Validator", "checkDefaultRate", (new StringBuilder())
				.append("defaultRate :").append(defaultRate).toString());
		Double defaultRateValue = new Double(ValidatorUtil.getValueAsString(
				bean, defaultRate));
		double defltRate = defaultRateValue.doubleValue();
		Log.log(4, "Validator", "checkDefaultRate", (new StringBuilder())
				.append("defaultRate :").append(defltRate).toString());
		if (isDefaultRateApplicable != null
				&& isDefaultRateApplicable.equals("Y") && defltRate <= 0.0D) {
			ActionError error = new ActionError("defRateMoreThanZero");
			errors.add("org.apache.struts.action.GLOBAL_ERROR", error);
		}
		return errors.isEmpty();
	}

	public static boolean checkAlreadyExistInCombo(Object bean,
			ValidatorAction validAction, Field field, ActionErrors errors,
			HttpServletRequest request) {
		Log.log(4, "Validator", "checkAlreadyExistInCombo", "Entered");
		String textBoxProperty = field.getProperty();
		String textBoxValue = ValidatorUtil.getValueAsString(bean,
				textBoxProperty);
		String comboBoxProperty = field.getVarValue("fromCombo");
		Log.log(4,
				"Validator",
				"checkAlreadyExistInCombo",
				(new StringBuilder()).append("textBoxProperty ")
						.append(textBoxProperty).toString());
		Log.log(4,
				"Validator",
				"checkAlreadyExistInCombo",
				(new StringBuilder()).append("textBoxValue ")
						.append(textBoxValue).toString());
		Log.log(4,
				"Validator",
				"checkAlreadyExistInCombo",
				(new StringBuilder()).append("comboBoxProperty ")
						.append(comboBoxProperty).toString());
		DynaActionForm dynaForm = (DynaActionForm) bean;
		ArrayList comboBox = (ArrayList) dynaForm.get(comboBoxProperty);
		if (textBoxValue != null && !textBoxValue.equals("")) {
			int size = comboBox.size();
			for (int i = 0; i < size; i++) {
				String comboValue = (String) comboBox.get(i);
				if (textBoxValue.trim().equalsIgnoreCase(comboValue)) {
					ActionError actionError = new ActionError(
							(new StringBuilder()).append("fromCombo")
									.append(textBoxProperty).toString());
					errors.add("org.apache.struts.action.GLOBAL_ERROR",
							actionError);
				}
			}

		}
		Log.log(4, "Validator", "checkAlreadyExistInCombo", "Exited");
		return errors.isEmpty();
	}

	public static boolean checkGFCardRateEntry(Object bean,
			ValidatorAction validAction, Field field, ActionErrors errors,
			HttpServletRequest request) {
		RPActionForm actionForm = (RPActionForm) bean;
		Map rateId = actionForm.getRateId();
		Map gfLowAmount = actionForm.getLowAmount();
		Map gfLowHigh = actionForm.getHighAmount();
		Map gfCardRate = actionForm.getGfRate();
		Set rateIdSet = rateId.keySet();
		Iterator rateIdIterator = rateIdSet.iterator();
		boolean rateValue = false;
		while (rateIdIterator.hasNext()) {
			String key = (String) rateIdIterator.next();
			Iterator errorsIterator = errors.get();
			rateValue = false;
			if (gfCardRate.get(key).equals("")
					|| !gfCardRate.get(key).equals("")
					&& Double.parseDouble((String) gfCardRate.get(key)) == 0.0D) {
				rateValue = true;
				break;
			}
		}
		if (rateValue) {
			ActionError actionError = new ActionError("cardRateRequired");
			errors.add("org.apache.struts.action.GLOBAL_ERROR", actionError);
		}
		return errors.isEmpty();
	}

	public static boolean calMaturityDateAmt(Object bean,
			ValidatorAction validAction, Field field, ActionErrors errors,
			HttpServletRequest request) {
		Log.log(4, "Validator", "calMaturityDateAmt", "Entered");
		IFProcessor ifProcessor = new IFProcessor();
		int balDays = 0;
		String tenureType = "";
		Date invDate = null;
		Date matDate = null;
		DynaActionForm dynaForm = (DynaActionForm) bean;
		tenureType = (String) dynaForm.get("tenureType");
		if (((String) dynaForm.get("instrumentName"))
				.equalsIgnoreCase("Commercial Papers"))
			tenureType = "M";
		if (!((String) dynaForm.get("tenure")).equals("")) {
			int iTenure = Integer.parseInt((String) dynaForm.get("tenure"));
			Log.log(4, "Validator", "calMaturityDateAmt", (new StringBuilder())
					.append("type from form ").append(tenureType).toString());
			Log.log(4, "Validator", "calMaturityDateAmt", (new StringBuilder())
					.append("tenure from form ").append(iTenure).toString());
			matDate = (Date) dynaForm.get("maturityDate");
			Log.log(4, "Validator", "calMaturityDateAmt", (new StringBuilder())
					.append("date from form ").append(matDate).toString());
			Calendar calendar = Calendar.getInstance();
			if (dynaForm.get("dateOfInvestment") != null
					&& !((Date) dynaForm.get("dateOfInvestment")).toString()
							.equals(""))
				invDate = (Date) dynaForm.get("dateOfInvestment");
			else
				invDate = (Date) dynaForm.get("dateOfDeposit");
			Log.log(4, "Validator", "calMaturityDateAmt", (new StringBuilder())
					.append("inv date from form ").append(invDate).toString());
			calendar.setTime(invDate);
			if (tenureType.equalsIgnoreCase("D"))
				calendar.add(Calendar.DATE, iTenure);
			else if (tenureType.equalsIgnoreCase("M"))
				calendar.add(Calendar.MONTH, iTenure);
			else if (tenureType.equalsIgnoreCase("Y"))
				calendar.add(Calendar.YEAR, iTenure);
			matDate = calendar.getTime();
			int dayOfWeek = calendar.get(Calendar.DAY_OF_WEEK);
			Log.log(4, "Validator", "calMaturityDateAmt", (new StringBuilder())
					.append("day ").append(dayOfWeek).toString());
			if (dayOfWeek == 1) {
				Log.log(4, "Validator", "calMaturityDateAmt", "day sun");
				ActionError actionError = new ActionError("matDateSunday");
				errors.add("org.apache.struts.action.GLOBAL_ERROR", actionError);
				dynaForm.set("maturityDate", null);
			}
			if (errors.isEmpty()) {
				matDate = calendar.getTime();
				dayOfWeek = calendar.get(Calendar.DAY_OF_WEEK);
				Log.log(4, "Validator", "calMaturityDateAmt",
						(new StringBuilder()).append("day ").append(dayOfWeek)
								.toString());
				Log.log(4, "Validator", "calMaturityDateAmt",
						(new StringBuilder()).append("date ").append(matDate)
								.toString());
				ArrayList holidays = new ArrayList();
				try {
					holidays = ifProcessor.getAllHolidays();
				} catch (DatabaseException de) {
					Log.log(4,
							"Validator",
							"calMaturityDateAmt",
							(new StringBuilder())
									.append("exception while getting holiday list ")
									.append(de.getMessage()).toString());
				}
				SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
				Log.log(4,
						"Validator",
						"calMaturityDateAmt",
						(new StringBuilder()).append("contains ")
								.append(holidays.contains(matDate)).toString());
				if (holidays.contains(matDate)) {
					ActionError actionError = new ActionError("matDateHoliday");
					errors.add("org.apache.struts.action.GLOBAL_ERROR",
							actionError);
					dynaForm.set("maturityDate", null);
				}
			}
		}
		Log.log(4, "Validator", "calMaturityDateAmt", "Exited");
		return errors.isEmpty();
	}

	public static boolean checkExposureEntry(Object bean,
			ValidatorAction validAction, Field field, ActionErrors errors,
			HttpServletRequest request) {
		DynaActionForm dynaForm = (DynaActionForm) bean;
		double liveInv = 0.0D;
		double investedAmt = 0.0D;
		double maturedAmt = 0.0D;
		double corpusAmt = 0.0D;
		double otherAmt = 0.0D;
		double expAmt = 0.0D;
		if (dynaForm.get("availableLiveInv").equals("Y"))
			liveInv = ((Double) dynaForm.get("liveInvtAmount")).doubleValue();
		if (dynaForm.get("availableInvAmount").equals("Y"))
			investedAmt = ((Double) dynaForm.get("investedAmount"))
					.doubleValue();
		if (dynaForm.get("availableMaturingAmount").equals("Y"))
			maturedAmt = ((Double) dynaForm.get("maturedAmount")).doubleValue();
		if (dynaForm.get("availableCorpusAmount").equals("Y"))
			corpusAmt = ((Double) dynaForm.get("exposureCorpusAmount"))
					.doubleValue();
		if (dynaForm.get("otherReceiptsAmount").equals("Y"))
			otherAmt = ((Double) dynaForm.get("otherReceiptsAmount"))
					.doubleValue();
		if (dynaForm.get("availableExpAmount").equals("Y"))
			expAmt = ((Double) dynaForm.get("expenditureAmount")).doubleValue();
		double surplusAmount = (liveInv + investedAmt + maturedAmt + corpusAmt + otherAmt)
				- expAmt;
		if (surplusAmount <= 0.0D) {
			ActionError actionError = new ActionError("surplusAmountRequired");
			errors.add("org.apache.struts.action.GLOBAL_ERROR", actionError);
		}
		return errors.isEmpty();
	}

	public static boolean checkStartEndNumber(Object bean,
			ValidatorAction validAction, Field field, ActionErrors errors,
			HttpServletRequest request) {
		DynaActionForm dynaForm = (DynaActionForm) bean;
		int startNo = ((Integer) dynaForm.get("chqStartNo")).intValue();
		int endNo = ((Integer) dynaForm.get("chqEndingNo")).intValue();
		if (startNo > endNo) {
			ActionError actionError = new ActionError("startNoLesser");
			errors.add("org.apache.struts.action.GLOBAL_ERROR", actionError);
		}
		return errors.isEmpty();
	}

	public static boolean validateNumberEntry(Object bean,
			ValidatorAction validAction, Field field, ActionErrors errors,
			HttpServletRequest request) {
		InvestmentForm ifForm = (InvestmentForm) bean;
		Map chqId = ifForm.getChequeId();
		Set chqIdSet = chqId.keySet();
		Iterator chqIdSetIterator = chqIdSet.iterator();
		Map startNo = ifForm.getStartNo();
		Set startNoSet = startNo.keySet();
		Iterator startNoSetIterator = startNoSet.iterator();
		Map endNo = ifForm.getEndNo();
		Set endNoSet = endNo.keySet();
		Iterator endNoSetIterator = endNoSet.iterator();
		while (chqIdSetIterator.hasNext()) {
			String key = (String) chqIdSetIterator.next();
			if (startNo.get(key) == null || startNo.get(key).equals("")
					|| startNo.get(key) != null && !startNo.get(key).equals("")
					&& Integer.parseInt((String) startNo.get(key)) == 0) {
				boolean remarksVal = false;
				for (Iterator errorsIterator = errors.get(); errorsIterator
						.hasNext();) {
					ActionError error = (ActionError) errorsIterator.next();
					if (error.getKey().equals("startNoRequired")) {
						remarksVal = true;
						break;
					}
				}

				if (!remarksVal) {
					ActionError actionError = new ActionError("startNoRequired");
					errors.add("org.apache.struts.action.GLOBAL_ERROR",
							actionError);
				}
			}
			if (endNo.get(key) == null || endNo.get(key).equals("")
					|| endNo.get(key) != null && !endNo.get(key).equals("")
					&& Integer.parseInt((String) endNo.get(key)) == 0) {
				boolean remarksVal = false;
				for (Iterator errorsIterator = errors.get(); errorsIterator
						.hasNext();) {
					ActionError error = (ActionError) errorsIterator.next();
					if (error.getKey().equals("endNoRequired")) {
						remarksVal = true;
						break;
					}
				}

				if (!remarksVal) {
					ActionError actionError = new ActionError("endNoRequired");
					errors.add("org.apache.struts.action.GLOBAL_ERROR",
							actionError);
				}
			}
			if (endNo.get(key) != null
					&& !endNo.get(key).equals("")
					&& Integer.parseInt((String) endNo.get(key)) != 0
					&& startNo.get(key) != null
					&& !startNo.get(key).equals("")
					&& Integer.parseInt((String) startNo.get(key)) != 0
					&& Integer.parseInt((String) startNo.get(key)) > Integer
							.parseInt((String) endNo.get(key))) {
				boolean remarksVal = false;
				for (Iterator errorsIterator = errors.get(); errorsIterator
						.hasNext();) {
					ActionError error = (ActionError) errorsIterator.next();
					if (error.getKey().equals("startNoLesser")) {
						remarksVal = true;
						break;
					}
				}

				if (!remarksVal) {
					ActionError actionError = new ActionError("startNoLesser");
					errors.add("org.apache.struts.action.GLOBAL_ERROR",
							actionError);
				}
			}
		}
		return errors.isEmpty();
	}

	public static boolean validateChequeCancelled(Object bean,
			ValidatorAction validAction, Field field, ActionErrors errors,
			HttpServletRequest request) {
		InvestmentForm ifForm = (InvestmentForm) bean;
		Map cancelledChq = ifForm.getCancelledChq();
		Set cancelledChqSet = cancelledChq.keySet();
		Iterator cancelledChqIterator = cancelledChqSet.iterator();
		boolean clearVal = false;
		if (cancelledChq != null && cancelledChq.size() != 0)
			while (cancelledChqIterator.hasNext()) {
				String key = (String) cancelledChqIterator.next();
				if (request.getParameter((new StringBuilder())
						.append("cancelledChq(").append(key).append(")")
						.toString()) != null
						&& cancelledChq.get(key) != null
						&& !cancelledChq.get(key).equals("")) {
					clearVal = true;
					break;
				}
			}
		if (!clearVal) {
			ActionError actionError = new ActionError("cancelledChqRequired");
			errors.add("org.apache.struts.action.GLOBAL_ERROR", actionError);
		}
		return errors.isEmpty();
	}

	public static boolean validateBankEntry(Object bean,
			ValidatorAction validAction, Field field, ActionErrors errors,
			HttpServletRequest request) {
		DynaActionForm dynaForm = (DynaActionForm) bean;
		String bankName = (String) dynaForm.get("bnkName");
		String instrumentType = (String) dynaForm.get("instrumentType");
		if (instrumentType.equals("CHEQUE")
				&& (bankName == null || bankName.equals(""))) {
			ActionError actionError = new ActionError("bankNameRequired");
			errors.add("org.apache.struts.action.GLOBAL_ERROR", actionError);
		}
		return errors.isEmpty();
	}

	public static boolean validateClaimBankEntry(Object bean,
			ValidatorAction validAction, Field field, ActionErrors errors,
			HttpServletRequest request) {
		ClaimActionForm dynaForm = (ClaimActionForm) bean;
		String bankName = dynaForm.getBnkName();
		String instrumentType = dynaForm.getModeOfPayment();
		if (instrumentType.equals("CHEQUE")
				&& (bankName == null || bankName.equals(""))) {
			ActionError actionError = new ActionError("bankNameRequired");
			errors.add("org.apache.struts.action.GLOBAL_ERROR", actionError);
		}
		return errors.isEmpty();
	}

	public static boolean validateRatingAgency(Object bean,
			ValidatorAction validAction, Field field, ActionErrors errors,
			HttpServletRequest request) {
		DynaActionForm dynaForm = (DynaActionForm) bean;
		String agencyName = (String) dynaForm.get("ratingAgency");
		String ratings[] = (String[]) dynaForm.get("allowableRating");
		IFProcessor ifProcessor = new IFProcessor();
		ArrayList ratingsList = new ArrayList();
		try {
			ArrayList agencyRatings = ifProcessor.showRatingAgencyWithRatings();
			for (int i = 0; i < agencyRatings.size(); i++) {
				String rating = (String) agencyRatings.get(i);
				ratingsList.add(rating);
			}

			if (agencyName != null && !agencyName.equals("")
					&& !ratingsList.contains(agencyName)
					&& (ratings == null || ratings.length == 0)) {
				ActionError actionError = new ActionError("ratingsRequired");
				errors.add("org.apache.struts.action.GLOBAL_ERROR", actionError);
			}
		} catch (DatabaseException e) {
			Log.logException(e);
		}
		return errors.isEmpty();
	}

	public static boolean validateDateFormat(Object bean,
			ValidatorAction validAction, Field field, ActionErrors errors,
			HttpServletRequest request) throws Exception {
		String fromValue = ValidatorUtil.getValueAsString(bean,
				field.getProperty());
		String fromString = field.getProperty();
		SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
		try {
			Log.log(4, "Validator", "validateDateFormat", (new StringBuilder())
					.append("date ").append(fromValue).toString());
			Date date = dateFormat.parse(fromValue, new ParsePosition(0));
			Log.log(4, "Validator", "validateDateFormat", (new StringBuilder())
					.append("date ").append(date).toString());
		} catch (NumberFormatException numberFormatException) {
			ActionError actionError = new ActionError("errors.date", fromString);
			errors.add("org.apache.struts.action.GLOBAL_ERROR", actionError);
		}
		return errors.isEmpty();
	}

	public static boolean checkMaturityDate(Object bean,
			ValidatorAction validAction, Field field, ActionErrors errors,
			HttpServletRequest request) {
		Log.log(4, "Validator", "calMaturityDateAmt", "Entered");
		IFProcessor ifProcessor = new IFProcessor();
		Date invDate = null;
		Date matDate = null;
		DynaActionForm dynaForm = (DynaActionForm) bean;
		if (matDate != null && !matDate.toString().equals("")) {
			Calendar calendar = Calendar.getInstance();
			Log.log(4, "Validator", "calMaturityDateAmt",
					"date from form not null");
			calendar.setTime(matDate);
			int dayOfWeek = calendar.get(Calendar.DAY_OF_WEEK);
			Log.log(4, "Validator", "calMaturityDateAmt", (new StringBuilder())
					.append("day ").append(dayOfWeek).toString());
			if (dayOfWeek == 1) {
				Log.log(4, "Validator", "calMaturityDateAmt", "day sun");
				ActionError actionError = new ActionError("matDateSunday");
				errors.add("org.apache.struts.action.GLOBAL_ERROR", actionError);
			}
			if (errors.isEmpty()) {
				matDate = calendar.getTime();
				dayOfWeek = calendar.get(Calendar.DAY_OF_WEEK);
				Log.log(4, "Validator", "calMaturityDateAmt",
						(new StringBuilder()).append("day ").append(dayOfWeek)
								.toString());
				Log.log(4, "Validator", "calMaturityDateAmt",
						(new StringBuilder()).append("date ").append(matDate)
								.toString());
				ArrayList holidays = new ArrayList();
				try {
					holidays = ifProcessor.getAllHolidays();
				} catch (DatabaseException de) {
					Log.log(4,
							"Validator",
							"calMaturityDateAmt",
							(new StringBuilder())
									.append("exception while getting holiday list ")
									.append(de.getMessage()).toString());
				}
				SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
				Log.log(4,
						"Validator",
						"calMaturityDateAmt",
						(new StringBuilder()).append("contains ")
								.append(holidays.contains(matDate)).toString());
				if (holidays.contains(matDate)) {
					ActionError actionError = new ActionError("matDateHoliday");
					errors.add("org.apache.struts.action.GLOBAL_ERROR",
							actionError);
				}
			}
		}
		Log.log(4, "Validator", "calMaturityDateAmt", "Exited");
		return errors.isEmpty();
	}

	public static boolean validateSanctionedDatesrsf2wcnfb(Object bean,
			ValidatorAction validAction, Field field, ActionErrors errors,
			HttpServletRequest request) {
		DynaActionForm dynaForm = (DynaActionForm) bean;
		String fromValue = ValidatorUtil.getValueAsString(bean,
				field.getProperty());
		String toValue = null;
		String dateString = "01/01/2013";
	//	System.out.println((new StringBuilder()).append("from value:")
		//		.append(fromValue).toString());
		Date dt = new Date();
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(dt);
		int year = calendar.get(Calendar.YEAR);
	//	System.out.println((new StringBuilder()).append("Year:").append(year)
		//		.toString());
		int month = calendar.get(Calendar.MONTH);
	//	System.out.println((new StringBuilder()).append("Month:").append(month)
		//		.toString());
		int day = calendar.get(Calendar.DATE);
		//System.out.println((new StringBuilder()).append("Day:").append(day)
		//		.toString());
		//System.out.println((new StringBuilder()).append("Result Date:")
			//	.append(calendar.getTime()).toString());
		SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
		boolean fromDateValue = false;
		boolean toDateValue = false;
		if (!GenericValidator.isBlankOrNull(fromValue)) {
			try {
				Date fromDate = sdf.parse(fromValue, new ParsePosition(0));
				//System.out.println((new StringBuilder()).append("From Date:")
					//	.append(fromDate).toString());
				if (fromDate == null)
					fromDateValue = false;
				else
					fromDateValue = true;
			} catch (Exception e) {
				fromDateValue = false;
			}
			try {
				Calendar myCal = Calendar.getInstance();
				myCal.set(Calendar.YEAR, 2013);
				myCal.set(Calendar.MONTH, 0);
				myCal.set(Calendar.DATE, 1);
				Date toDate = myCal.getTime();
				//System.out.println((new StringBuilder()).append("To Date:")
					//	.append(toDate).toString());
				toValue = sdf.format(toDate);
				if (toDate == null)
					toDateValue = false;
				else
					toDateValue = true;
			} catch (Exception e) {
				toDateValue = false;
			}
			if (fromDateValue && toDateValue
					&& DateHelper.day1BeforeDay2(fromValue, toValue)) {
			//	System.out.println("Final");
				ActionError actionMessage = new ActionError(
						"notbeforejan2013forwcnfb");
				errors.add("org.apache.struts.action.GLOBAL_ERROR",
						actionMessage);
			}
		}
		return errors.isEmpty();
	}

	public static boolean validateSanctionedDatesrsf2tc(Object bean,
			ValidatorAction validAction, Field field, ActionErrors errors,
			HttpServletRequest request) {
		DynaActionForm dynaForm = (DynaActionForm) bean;
		String fromValue = ValidatorUtil.getValueAsString(bean,
				field.getProperty());
		String toValue = null;
		String dateString = "01/01/2013";
		//System.out.println((new StringBuilder()).append("from value:")
		//		.append(fromValue).toString());
		Date dt = new Date();
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(dt);
		int year = calendar.get(Calendar.YEAR);
	//	System.out.println((new StringBuilder()).append("Year:").append(year)
		//		.toString());
		int month = calendar.get(Calendar.MONTH);
	//	System.out.println((new StringBuilder()).append("Month:").append(month)
	//			.toString());
		int day = calendar.get(Calendar.DATE);
	//	System.out.println((new StringBuilder()).append("Day:").append(day)
	//			.toString());
	//	System.out.println((new StringBuilder()).append("Result Date:")
	//			.append(calendar.getTime()).toString());
		SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
		boolean fromDateValue = false;
		boolean toDateValue = false;
		if (!GenericValidator.isBlankOrNull(fromValue)) {
			try {
				Date fromDate = sdf.parse(fromValue, new ParsePosition(0));
			//	System.out.println((new StringBuilder()).append("From Date:")
				//		.append(fromDate).toString());
				if (fromDate == null)
					fromDateValue = false;
				else
					fromDateValue = true;
			} catch (Exception e) {
				fromDateValue = false;
			}
			try {
				Calendar myCal = Calendar.getInstance();
				myCal.set(Calendar.YEAR, 2013);
				myCal.set(Calendar.MONTH, 0);
				myCal.set(Calendar.DATE, 1);
				Date toDate = myCal.getTime();
			//	System.out.println((new StringBuilder()).append("To Date:")
			//			.append(toDate).toString());
				toValue = sdf.format(toDate);
				if (toDate == null)
					toDateValue = false;
				else
					toDateValue = true;
			} catch (Exception e) {
				toDateValue = false;
			}
			if (fromDateValue && toDateValue
					&& DateHelper.day1BeforeDay2(fromValue, toValue)) {
			//	System.out.println("Final");
				ActionError actionMessage = new ActionError("notbeforejan2013");
				errors.add("org.apache.struts.action.GLOBAL_ERROR",
						actionMessage);
			}
		}
		return errors.isEmpty();
	}

	public static boolean validateSanctionedDatesrsf2wcfb(Object bean,
			ValidatorAction validAction, Field field, ActionErrors errors,
			HttpServletRequest request) {
		DynaActionForm dynaForm = (DynaActionForm) bean;
		String fromValue = ValidatorUtil.getValueAsString(bean,
				field.getProperty());
		String toValue = null;
		String dateString = "01/01/2013";
		//System.out.println((new StringBuilder()).append("from value:")
		//		.append(fromValue).toString());
		Date dt = new Date();
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(dt);
		int year = calendar.get(Calendar.YEAR);
		//System.out.println((new StringBuilder()).append("Year:").append(year)
		//		.toString());
		int month = calendar.get(Calendar.MONTH);
		//System.out.println((new StringBuilder()).append("Month:").append(month)
			//	.toString());
		int day = calendar.get(Calendar.DATE);
		//System.out.println((new StringBuilder()).append("Day:").append(day)
		//		.toString());
	//	System.out.println((new StringBuilder()).append("Result Date:")
	//			.append(calendar.getTime()).toString());
		SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
		boolean fromDateValue = false;
		boolean toDateValue = false;
		if (!GenericValidator.isBlankOrNull(fromValue)) {
			try {
				Date fromDate = sdf.parse(fromValue, new ParsePosition(0));
			//	System.out.println((new StringBuilder()).append("From Date:")
				//		.append(fromDate).toString());
				if (fromDate == null)
					fromDateValue = false;
				else
					fromDateValue = true;
			} catch (Exception e) {
				fromDateValue = false;
			}
			try {
				Calendar myCal = Calendar.getInstance();
				myCal.set(Calendar.YEAR, 2013);
				myCal.set(Calendar.MONTH, 0);
				myCal.set(Calendar.DATE, 1);
				Date toDate = myCal.getTime();
				//System.out.println((new StringBuilder()).append("To Date:")
				//		.append(toDate).toString());
				toValue = sdf.format(toDate);
				if (toDate == null)
					toDateValue = false;
				else
					toDateValue = true;
			} catch (Exception e) {
				toDateValue = false;
			}
			if (fromDateValue && toDateValue
					&& DateHelper.day1BeforeDay2(fromValue, toValue)) {
			//	System.out.println("Final");
				ActionError actionMessage = new ActionError(
						"notbeforejan2013forwcfb");
				errors.add("org.apache.struts.action.GLOBAL_ERROR",
						actionMessage);
			}
		}
		return errors.isEmpty();
	}

	// added on 23/10/2013

	public static boolean validateFromNPACurrentDatesNew(Object bean,
			ValidatorAction validAction, Field field, ActionErrors errors,
			HttpServletRequest request) throws Exception {
		// System.out.println("validateFromNPACurrentDatesNew called");
		DynaValidatorActionForm gmPeriodicInfoForm = (DynaValidatorActionForm) bean;
		GMDAO gmDAO = new GMDAO();
		String fromValue = ValidatorUtil.getValueAsString(bean,
				field.getProperty());
		String fromString = field.getProperty();

		java.util.Date npaDate = (java.util.Date) gmPeriodicInfoForm
				.get("npaDt");

		String borrowerId = ((String) gmPeriodicInfoForm.get("borrowerId"))
				.toUpperCase();
		int count = gmDAO.getExceptionBIDCount(borrowerId);
	

		Date npaCreatedDate = (java.util.Date) gmPeriodicInfoForm
				.get("npaCreatedDate");
		String npaId = (String) gmPeriodicInfoForm.get("npaId");

		// System.out.println("NPA Date:"+npaDate);
		java.util.Date currentDate = new java.util.Date();
		SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");

		String toValue = null;
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(currentDate);
		int year = calendar.get(Calendar.YEAR);
		int month = calendar.get(Calendar.MONTH);
		int day = calendar.get(Calendar.DATE);
		int temp = 0;
		if (month >= 0 && month <= 2) {
			year = year - 1;
			calendar.set(Calendar.MONTH, 9);
			calendar.set(Calendar.DATE, 1);
			calendar.set(Calendar.YEAR, year);
			temp = 0;
		} else if (month >= 3 && month <= 5) {
			calendar.set(Calendar.MONTH, 0);
			calendar.set(Calendar.DATE, 1);
			calendar.set(Calendar.YEAR, year);
			temp = 1;
		} else if (month >= 6 && month <= 8) {
			calendar.set(Calendar.MONTH, 3);
			calendar.set(Calendar.DATE, 1);
			calendar.set(Calendar.YEAR, year);
			temp = 2;
		} else if (month >= 9 && month <= 11) {
			calendar.set(Calendar.MONTH, 6);
			calendar.set(Calendar.DATE, 1);
			calendar.set(Calendar.YEAR, year);
			temp = 3;
		}
		
		
	//	Date tenureAppprvedDate = gmDAO.getTenureApprovedDate(borrowerId);

	//	Date tenureApprovDate=null;

		java.util.Date toDate = calendar.getTime();
		toValue = dateFormat.format(toDate);
		
		  HashMap tennureAppDate = gmDAO.getTenureApprovedDate(borrowerId);
          Date tennureDAte = (Date)tennureAppDate.get("TENURE_REQUEST_DATE");
          String tennureRemark = (String)tennureAppDate.get("TENURE_MODIFICATION_REMARKS");
          String tennureStatus = (String)tennureAppDate.get("OLD_APP_STATUS");
          Date tennureDAte1 = null;
		 //System.out.println("To Date:"+npaDate.compareTo(tenureAppprvedDate));
		try {
		      SimpleDateFormat format = new SimpleDateFormat("dd/MM/yyyy");
		/*	 if (tenureAppprvedDate!=null && (npaDate.compareTo(tenureAppprvedDate) <= 0)) {
					//NpaTenDate
					ActionError actionError = new ActionError("NpaTenDate",dateFormat.format(tenureAppprvedDate));
					
					errors.add(ActionErrors.GLOBAL_ERROR, actionError);
					 
				}  */

			String stringDate = dateFormat.format(currentDate);

			if (!(GenericValidator.isBlankOrNull(fromValue))) {

				if (DateHelper.compareDates(fromValue, stringDate) != 0
						&& DateHelper.compareDates(fromValue, stringDate) != 1) {
					ActionError actionError = new ActionError("currentDate"
							+ fromString);
					errors.add(ActionErrors.GLOBAL_ERROR, actionError);
				}
				if (GenericValidator.isBlankOrNull(npaId) && count == 0
						&& DateHelper.compareDates(toValue, fromValue) != 0
						&& DateHelper.compareDates(toValue, fromValue) != 1) {
					ActionError actionError = new ActionError(
							(new StringBuilder("newNpaDate")).append(temp)
									.toString());
					
					
					errors.add("org.apache.struts.action.GLOBAL_ERROR",
							actionError);
				}
				 
			}
			 if(!tennureRemark.equals("Reschedulement_Rephasement") && tennureStatus.equals("AP") && tennureDAte.compareTo(npaDate) > 0)
             {
                 ActionError actionError = new ActionError("NpaTennureDate");
                 errors.add("org.apache.struts.action.GLOBAL_ERROR", actionError);
             }
             if(!tennureRemark.equals("Reschedulement_Rephasement") && tennureStatus.equals("EX") && tennureDAte.compareTo(npaDate) > 0)
             {
                 ActionError actionError = new ActionError("NpaTennureDate");
                 errors.add("org.apache.struts.action.GLOBAL_ERROR", actionError);
             }
             if(tennureRemark.equals("Reschedulement_Rephasement") && tennureStatus.equals("EX") && tennureDAte.compareTo(npaDate) > 0)
             {
                 ActionError actionError = new ActionError("NpaTennureDate");
                 errors.add("org.apache.struts.action.GLOBAL_ERROR", actionError);
             }
		} catch (NumberFormatException numberFormatException) {

			ActionError actionError = new ActionError("errors.date", fromString);

			//System.out.println("validateFromNPACurrentDatesNew  called actionError "+actionError.getKey());
			//System.out.println("validateFromNPACurrentDatesNew  called actionError "+actionError.getValues());
			//System.out.println(fromString+"validateFromNPACurrentDatesNew  called actionError "+actionError);
			errors.add(ActionErrors.GLOBAL_ERROR, actionError);

		}

		return errors.isEmpty();
	}

	public static boolean validateRequiredFields(Object bean,
			ValidatorAction validAction, Field field, ActionErrors errors,
			HttpServletRequest request) throws Exception {
	//	System.out.println("validateRequiredFields called");
		DynaValidatorActionForm newNpaForm = (DynaValidatorActionForm) bean;
		double totalApprovedAmount = (java.lang.Double) newNpaForm
				.get("totalApprovedAmount");
		// java.util.Date lastInspectionDt =
		// (java.util.Date)newNpaForm.get("lastInspectionDt");
		int total = (Integer) newNpaForm.get("size");
		double totalSecurityAsOnSanc = (java.lang.Double) newNpaForm
				.get("totalSecurityAsOnSanc");
		double totalSecurityAsOnNpa = (java.lang.Double) newNpaForm
				.get("totalSecurityAsOnNpa");

		String insdt = ValidatorUtil
				.getValueAsString(bean, field.getProperty());

		String lastInspectionDt = ValidatorUtil.getValueAsString(bean,
				"lastInspectionDt");
		String networthAsOnSancDtStr = ValidatorUtil.getValueAsString(bean,
				"networthAsOnSancDt");
		String networthAsOnNpaDtStr = ValidatorUtil.getValueAsString(bean,
				"networthAsOnNpaDt");
		String reasonForReductionNpa = ValidatorUtil.getValueAsString(bean,
				"reasonForReductionAsOnNpaDt");

		/* checking subsidy date and amount */
		String subsidyFlag = (String) newNpaForm.get("subsidyFlag");
		String subsidyRcvdFlag = ValidatorUtil.getValueAsString(bean,
				"isSubsidyRcvd");
		String subAdjustedFlag = (String) newNpaForm.get("isSubsidyAdjusted");
		String subsidyDt = ValidatorUtil.getValueAsString(bean,
				"subsidyLastRcvdDt");
		String subsidyAmt = ValidatorUtil.getValueAsString(bean,
				"subsidyLastRcvdAmt");

		if ("Y".equals(subsidyFlag)) {
			if ("Y".equals(subsidyRcvdFlag)) {
				if ("Y".equals(subAdjustedFlag)) {
					if (GenericValidator.isBlankOrNull(subsidyDt)) {
						ActionError actionError = new ActionError("subsidyDate");
						errors.add(ActionErrors.GLOBAL_ERROR, actionError);
					}
					if (!GenericValidator.isBlankOrNull(subsidyAmt)) {
						if (((Double) Double.parseDouble(subsidyAmt))
								.doubleValue() <= 0.0) {
							ActionError actionError = new ActionError(
									"subsidyAmount");
							errors.add(ActionErrors.GLOBAL_ERROR, actionError);
						}
					} else {
						ActionError actionError = new ActionError(
								"subsidyAmount");
						errors.add(ActionErrors.GLOBAL_ERROR, actionError);
					}
				} else if (GenericValidator.isBlankOrNull(subAdjustedFlag)) {
					ActionError actionError = new ActionError("subAdjustedFlag");
					errors.add(ActionErrors.GLOBAL_ERROR, actionError);
				}
			} else if (GenericValidator.isBlankOrNull(subsidyRcvdFlag)) {
				ActionError actionError = new ActionError("subsidyRcvdFlag");
				errors.add(ActionErrors.GLOBAL_ERROR, actionError);
			}
		}
		/* checking for last inspection date */

		if (totalApprovedAmount > 750000) {
			if (GenericValidator.isBlankOrNull(lastInspectionDt)) {
				ActionError actionError = new ActionError("LastInspectionDate");
				errors.add(ActionErrors.GLOBAL_ERROR, actionError);
			}
		}

		/* checking for reason for reduction in security amount */
		if (GenericValidator.isBlankOrNull(reasonForReductionNpa)) {
			if (totalSecurityAsOnNpa < totalSecurityAsOnSanc) {
				ActionError actionError = new ActionError("ReasonForReduction");
				errors.add(ActionErrors.GLOBAL_ERROR, actionError);
			}
		}

		/* checking networth */

		if (GenericValidator.isBlankOrNull(networthAsOnSancDtStr)
				|| ((Double) Double.parseDouble(networthAsOnSancDtStr))
						.doubleValue() == 0.0) {
			ActionError actionError = new ActionError("NetworthAmountSanc");
			errors.add(ActionErrors.GLOBAL_ERROR, actionError);
		}

		if (GenericValidator.isBlankOrNull(networthAsOnNpaDtStr)
				|| ((Double) Double.parseDouble(networthAsOnNpaDtStr))
						.doubleValue() == 0.0) {
			ActionError actionError = new ActionError("NetworthAmountNpa");
			errors.add(ActionErrors.GLOBAL_ERROR, actionError);
		}

		/* checking security vales */
		double landval = 0.0;
		double buildval = 0.0;
		double machineval = 0.0;
		double movval = 0.0;
		double currval = 0.0;
		double othersval = 0.0;

		Map securityAsOnSancDt = (Map) newNpaForm.get("securityAsOnSancDt");
		Map securityAsOnNpaDt = (Map) newNpaForm.get("securityAsOnNpaDt");
		String landvalstr = (String) securityAsOnSancDt.get("LAND");
		String buildvalstr = (String) securityAsOnSancDt.get("BUILDING");
		String machinevalstr = (String) securityAsOnSancDt.get("MACHINE");
		String movvalstr = (String) securityAsOnSancDt
				.get("OTHER_FIXED_MOVABLE_ASSETS");
		String currvalstr = (String) securityAsOnSancDt.get("CUR_ASSETS");
		String othersvalstr = (String) securityAsOnSancDt.get("OTHERS");

		if (!GenericValidator.isBlankOrNull(landvalstr)) {
			landval = Double.parseDouble(landvalstr);
		}
		if (!GenericValidator.isBlankOrNull(buildvalstr)) {
			buildval = Double.parseDouble(buildvalstr);
		}
		if (!GenericValidator.isBlankOrNull(machinevalstr)) {
			machineval = Double.parseDouble(machinevalstr);
		}
		if (!GenericValidator.isBlankOrNull(movvalstr)) {
			movval = Double.parseDouble(movvalstr);
		}
		if (!GenericValidator.isBlankOrNull(currvalstr)) {
			currval = Double.parseDouble(currvalstr);
		}
		if (!GenericValidator.isBlankOrNull(othersvalstr)) {
			othersval = Double.parseDouble(othersvalstr);
		}

		if (landval <= 0 && buildval <= 0 && machineval <= 0 && movval <= 0
				&& currval <= 0 && othersval <= 0) {
			ActionError actionError = new ActionError("secuirtyValuesAsOnSanc");
			errors.add(ActionErrors.GLOBAL_ERROR, actionError);
		}

		/*
		 * landvalstr = (String)securityAsOnNpaDt.get("LAND"); buildvalstr =
		 * (String)securityAsOnNpaDt.get("BUILDING"); machinevalstr =
		 * (String)securityAsOnNpaDt.get("MACHINE"); movvalstr =
		 * (String)securityAsOnNpaDt.get("OTHER_FIXED_MOVABLE_ASSETS");
		 * currvalstr = (String)securityAsOnNpaDt.get("CUR_ASSETS");
		 * othersvalstr = (String)securityAsOnNpaDt.get("OTHERS");
		 * 
		 * landval = 0.0; buildval = 0.0; machineval = 0.0; movval = 0.0;
		 * currval = 0.0; othersval = 0.0;
		 * 
		 * if(!GenericValidator.isBlankOrNull(landvalstr)){ landval =
		 * Double.parseDouble(landvalstr); }
		 * if(!GenericValidator.isBlankOrNull(buildvalstr)){ buildval =
		 * Double.parseDouble(buildvalstr); }
		 * if(!GenericValidator.isBlankOrNull(machinevalstr)){ machineval =
		 * Double.parseDouble(machinevalstr); }
		 * if(!GenericValidator.isBlankOrNull(movvalstr)){ movval =
		 * Double.parseDouble(movvalstr); }
		 * if(!GenericValidator.isBlankOrNull(currvalstr)){ currval =
		 * Double.parseDouble(currvalstr); }
		 * if(!GenericValidator.isBlankOrNull(othersvalstr)){ othersval =
		 * Double.parseDouble(othersvalstr); }
		 * 
		 * if(landval <= 0 && buildval <= 0 && machineval <= 0 && movval <= 0 &&
		 * currval <= 0 && othersval <= 0){ ActionError actionError=new
		 * ActionError("secuirtyValuesAsOnNpa");
		 * errors.add(ActionErrors.GLOBAL_ERROR,actionError); }
		 */

		/*
		 * checking for instalment date,disbursement date,moratorium,total
		 * disbursed amt,repayment amt,outstanding amt
		 */
		boolean isFirstDisbTaken = false;
		boolean isLastDisbTaken = false;
		boolean isFirstInstTaken = false;
		boolean isPMoratoriumTaken = false;
		boolean isIMoratoriumTaken = false;
		boolean isDisbAmtTaken = false;
		boolean isPRepayTaken = false;
		boolean isIRepayTaken = false;
		boolean isPOSTaken = false;
		boolean isIOSTaken = false;

		String cgpan = null;
		String guarStartDt = null;
		String sanctionDt = null;
		String firstDisbDt = null;
		String lastDisbDt = null;
		String firstInstDt = null;
		String moratoriumPrincipal = null;
		String moratoriumInterest = null;

		String totalDisbAmt = null;
		String repayPrincipal = null;
		String repayInterest = null;
		String outstandingPrincipal = null;
		String outstandingInterest = null;

		// String approvedAmount = null;
		String interestRate = null;

		for (int i = 1; i <= total; i++) {

			cgpan = "cgpan" + i;
			guarStartDt = "guarStartDt" + i;
			sanctionDt = "sanctionDt" + i;
			firstDisbDt = "firstDisbDt" + i;
			lastDisbDt = "lastDisbDt" + i;
			firstInstDt = "firstInstDt" + i;
			moratoriumPrincipal = "moratoriumPrincipal" + i;
			// moratoriumInterest = "moratoriumInterest"+i;

			totalDisbAmt = "totalDisbAmt" + i;
			repayPrincipal = "repayPrincipal" + i;
			repayInterest = "repayInterest" + i;
			outstandingPrincipal = "outstandingPrincipal" + i;
			outstandingInterest = "outstandingInterest" + i;

			// approvedAmount = "approvedAmount"+i;
			interestRate = "interestRate" + i;

			String cgpanNo = (String) newNpaForm.get(cgpan);
			String loanType = cgpanNo.substring(cgpanNo.length() - 2);

			java.util.Date firstDisbursedmentDate = (java.util.Date) newNpaForm
					.get(firstDisbDt);
			java.util.Date firstInstalmentDate = (java.util.Date) newNpaForm
					.get(firstInstDt);

			String firstDisbursedmentDateStr = ValidatorUtil.getValueAsString(
					bean, firstDisbDt);
			String lastDisbursedmentDateStr = ValidatorUtil.getValueAsString(
					bean, lastDisbDt);
			String firstInstalmentDateStr = ValidatorUtil.getValueAsString(
					bean, firstInstDt);
			/*
			 * String moratoriumPrincipalStr =
			 * ValidatorUtil.getValueAsString(bean,moratoriumPrincipal); String
			 * moratoriumInterestStr =
			 * ValidatorUtil.getValueAsString(bean,moratoriumInterest);
			 */
			String totalDisbAmtStr = ValidatorUtil.getValueAsString(bean,
					totalDisbAmt);
			String repayPrincipalStr = ValidatorUtil.getValueAsString(bean,
					repayPrincipal);
			String repayInterestStr = ValidatorUtil.getValueAsString(bean,
					repayInterest);
			String outstandingPrincipalStr = ValidatorUtil.getValueAsString(
					bean, outstandingPrincipal);
			String outstandingInterestStr = ValidatorUtil.getValueAsString(
					bean, outstandingInterest);
			// String approvedAmountStr =
			// ValidatorUtil.getValueAsString(bean,approvedAmount);
			// String interestRateStr =
			// ValidatorUtil.getValueAsString(bean,interestRate);
			System.out.println("interestRate name"+interestRate);

			double rate = (Double) newNpaForm.get(interestRate);
			double pos = (Double) newNpaForm.get(outstandingPrincipal);
			double ios = (Double) newNpaForm.get(outstandingInterest);
			
			System.out.println("interestRate"+rate);
			System.out.println("outstandingPrincipal"+outstandingPrincipal);
			System.out.println("outstandingInterest"+outstandingInterest);
			
			
			System.out.println("rate"+rate);
			System.out.println("pos"+pos);
			System.out.println("ios"+ios);

			double amt = Math.round((pos * rate * 3) / (12 * 100));
			// double minAmt = Math.round(95*amt/100);
			double minAmt = Math.round(70 * amt / 100);
			// double maxAmt = Math.round(105*amt/100);
			double maxAmt = Math.round(130 * amt / 100);
			
			System.out.println("amt"+amt);
			System.out.println("minAmt"+minAmt);
			System.out.println("maxAmt"+maxAmt);
			if (ios <= maxAmt && ios >= minAmt) {

			} else {
				String msg = "interestOverDues";
				ActionError actionError = new ActionError(msg);
				errors.add(ActionErrors.GLOBAL_ERROR, actionError);
			}

			if ("TC".equals(loanType) || "CC".equals(loanType)) {
				int principalMoratorium = (Integer) newNpaForm
						.get(moratoriumPrincipal);
				if (!(GenericValidator.isBlankOrNull(firstDisbursedmentDateStr))
						&& !(GenericValidator
								.isBlankOrNull(firstInstalmentDateStr))) {

					Calendar cal = Calendar.getInstance();
					cal.setTime(firstDisbursedmentDate);
					// System.out.println("firstDisbursedmentDate after adding moratorium:"+cal.getTime());
					cal.add(Calendar.MONTH, principalMoratorium);
					// System.out.println("firstDisbursedmentDate after adding moratorium:"+cal.getTime());

					SimpleDateFormat dateFormat = new SimpleDateFormat(
							"dd/MM/yyyy");
					Date d = cal.getTime();
					String dstr = dateFormat.format(d);
					Date disbDateWithPeriod = dateFormat.parse(dstr);

					Date instalmentDate = dateFormat
							.parse(firstInstalmentDateStr);

					long difference = instalmentDate.getTime()
							- disbDateWithPeriod.getTime();
					long dayDiff = difference / (1000 * 24 * 60 * 60);
					// System.out.println("difference in days:"+dayDiff);

					if (dayDiff > 45) {
						ActionError actionError = new ActionError(
								"DisbursementAndInstalmentDate");
						errors.add(ActionErrors.GLOBAL_ERROR, actionError);
					}
				}

				if (GenericValidator.isBlankOrNull(firstDisbursedmentDateStr)) {
					if (!isFirstDisbTaken) {
						isFirstDisbTaken = true;
						ActionError actionError = new ActionError(
								"FirstDisbursementDate");
						errors.add(ActionErrors.GLOBAL_ERROR, actionError);
					}
				}

				if (GenericValidator.isBlankOrNull(lastDisbursedmentDateStr)) {
					if (!isLastDisbTaken) {
						isLastDisbTaken = true;
						ActionError actionError = new ActionError(
								"LastDisbursementDate");
						errors.add(ActionErrors.GLOBAL_ERROR, actionError);
					}
				}

				if (GenericValidator.isBlankOrNull(firstInstalmentDateStr)) {
					if (!isFirstInstTaken) {
						isFirstInstTaken = true;
						ActionError actionError = new ActionError(
								"FirstInstalmentDate");
						errors.add(ActionErrors.GLOBAL_ERROR, actionError);
					}
				}

				/*
				 * if(GenericValidator.isBlankOrNull(moratoriumPrincipalStr)){
				 * if(!isPMoratoriumTaken){ isPMoratoriumTaken = true;
				 * ActionError actionError=new
				 * ActionError("PrincipalMoratorium");
				 * errors.add(ActionErrors.GLOBAL_ERROR,actionError); } }
				 * 
				 * if(GenericValidator.isBlankOrNull(moratoriumInterestStr)){
				 * if(!isIMoratoriumTaken){ isIMoratoriumTaken = true;
				 * ActionError actionError=new
				 * ActionError("InterestMoratorium");
				 * errors.add(ActionErrors.GLOBAL_ERROR,actionError); } }
				 */

				if (GenericValidator.isBlankOrNull(totalDisbAmtStr)
						|| ((Double) Double.parseDouble(totalDisbAmtStr))
								.doubleValue() == 0.0) {
					if (!isDisbAmtTaken) {
						isDisbAmtTaken = true;
						ActionError actionError = new ActionError(
								"TotalDisbursedAmount");
						errors.add(ActionErrors.GLOBAL_ERROR, actionError);
					}
				}

				if (GenericValidator.isBlankOrNull(repayPrincipalStr)
						|| ((Double) Double.parseDouble(repayPrincipalStr))
								.doubleValue() == 0.0) {
					if (!isPRepayTaken) {
						isPRepayTaken = true;
						ActionError actionError = new ActionError(
								"PrincipalRepayAmount");
						errors.add(ActionErrors.GLOBAL_ERROR, actionError);
					}
				}

				if (GenericValidator.isBlankOrNull(repayInterestStr)
						|| ((Double) Double.parseDouble(repayInterestStr))
								.doubleValue() == 0.0) {
					if (!isIRepayTaken) {
						isIRepayTaken = true;
						ActionError actionError = new ActionError(
								"InterestRepayAmount");
						errors.add(ActionErrors.GLOBAL_ERROR, actionError);
					}
				}

			}

			if (GenericValidator.isBlankOrNull(outstandingPrincipalStr)
					|| ((Double) Double.parseDouble(outstandingPrincipalStr))
							.doubleValue() == 0.0) {
				if (!isPOSTaken) {
					isPOSTaken = true;
					ActionError actionError = new ActionError(
							"PrincipalOutstandingAmount");
					errors.add(ActionErrors.GLOBAL_ERROR, actionError);
				}
			}

			if (GenericValidator.isBlankOrNull(outstandingInterestStr)
					|| ((Double) Double.parseDouble(outstandingInterestStr))
							.doubleValue() == 0.0) {
				if (!isIOSTaken) {
					isIOSTaken = true;
					ActionError actionError = new ActionError(
							"InterestOutstandingAmount");
					errors.add(ActionErrors.GLOBAL_ERROR, actionError);
				}
			}

		}

		return errors.isEmpty();
	}

	/* this mathod will compare npa date with earliest guarantee start date */
	public static boolean validateDates(Object bean,
			ValidatorAction validAction, Field field, ActionErrors errors,
			HttpServletRequest request) throws Exception {
	//	 System.out.println("validateDates  called");
		DynaValidatorActionForm newNpaForm = (DynaValidatorActionForm) bean;
		GMDAO gmDAO = new GMDAO();
		// String fromValue=ValidatorUtil.getValueAsString(bean,
		// field.getProperty());
		java.util.Date npaDate = (java.util.Date) newNpaForm.get("npaDt");
		String npaDateStr = ValidatorUtil.getValueAsString(bean, "npaDt");
		String propertyName = field.getProperty();
		// System.out.println("fromString:"+propertyName);
		// System.out.println("npaDateStr:"+npaDateStr);
		String borrowerId = ((String) newNpaForm.get("borrowerId"))
				.toUpperCase();
		int count = gmDAO.getExceptionBIDCount(borrowerId);

		int total = (Integer) newNpaForm.get("size");

		String guarStartDt = null;
		ArrayList dates = new ArrayList();
		for (int i = 1; i <= total; i++) {
			guarStartDt = "guarStartDt" + i;
			// String guarStartDateStr =
			// ValidatorUtil.getValueAsString(bean,guarStartDt);
			Date gsd = (Date) newNpaForm.get(guarStartDt);
			dates.add(gsd);

		}
		Collections.sort(dates);
		Date guarDate = null;
		Iterator itr = dates.iterator();
		if (itr.hasNext()) {
			guarDate = (Date) itr.next();
		}

		// System.out.println("NPA Date:"+npaDate);
		// java.util.Date currentDate=new java.util.Date();//here in place of
		// current date, take earliest guarstartdate
		SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");

		String toValue = null;
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(guarDate);
		int year = calendar.get(Calendar.YEAR);
		int month = calendar.get(Calendar.MONTH);
		int day = calendar.get(Calendar.DATE);
		int temp = 0;
		if (month >= 0 && month <= 2) {
			year = year - 1;
			calendar.set(Calendar.MONTH, 9);
			calendar.set(Calendar.DATE, 1);
			calendar.set(Calendar.YEAR, year);
			temp = 0;
		} else if (month >= 3 && month <= 5) {
			calendar.set(Calendar.MONTH, 0);
			calendar.set(Calendar.DATE, 1);
			calendar.set(Calendar.YEAR, year);
			temp = 1;
		} else if (month >= 6 && month <= 8) {
			calendar.set(Calendar.MONTH, 3);
			calendar.set(Calendar.DATE, 1);
			calendar.set(Calendar.YEAR, year);
			temp = 2;
		} else if (month >= 9 && month <= 11) {
			calendar.set(Calendar.MONTH, 6);
			calendar.set(Calendar.DATE, 1);
			calendar.set(Calendar.YEAR, year);
			temp = 3;
		}

		java.util.Date toDate = calendar.getTime();
		toValue = dateFormat.format(toDate);
		// System.out.println("To Date:"+toValue);
		try {

			String guarDateStr = dateFormat.format(guarDate);// guarDate

			if (!(GenericValidator.isBlankOrNull(npaDateStr)))// if npa date is
																// not null
			{

				// if(DateHelper.compareDates(npaDateStr,guarDateStr)< 0)
				if (!GenericValidator.isBlankOrNull(npaDateStr)) {
					if ((npaDate.compareTo(guarDate) < 0)) {
						ActionError actionError = new ActionError("NpaGuarDate");
						errors.add(ActionErrors.GLOBAL_ERROR, actionError);
					}
				}
				// if(count == 0) {
				// if(DateHelper.compareDates(toValue,npaDateStr)!=0 &&
				// DateHelper.compareDates(toValue,npaDateStr)!=1)
				// {
				// ActionError actionError=new ActionError("newNpaDate"+temp);
				// errors.add(ActionErrors.GLOBAL_ERROR,actionError);
				// }
				// }
			}
		} catch (NumberFormatException numberFormatException) {

			ActionError actionError = new ActionError("errors.date",
					propertyName);
			//System.out.println("validateDates  called actionError "+actionError.getKey());
			//System.out.println("validateDates  called actionError "+actionError.getValues());
			//System.out.println("validateDates  called actionError "+actionError);
			errors.add(ActionErrors.GLOBAL_ERROR, actionError);

		}

		return errors.isEmpty();
	}

	public static boolean npaRecallDates(Object bean,
			ValidatorAction validAction, Field field, ActionErrors errors,
			HttpServletRequest request) throws Exception {
		ClaimActionForm cpTcDetailsForm = (ClaimActionForm) bean;

		String fromValue = ValidatorUtil.getValueAsString(bean,
				field.getProperty());

		String recallDtStr = cpTcDetailsForm.getDateOfRecallNotice();
		String reasonForIssueRecallNotice = cpTcDetailsForm
				.getReasonForIssueRecallNotice();
		HashMap npaDetails = cpTcDetailsForm.getNpaDetails();
		String npaDtStr = (String) npaDetails.get("NPAClassifiedDate");
		// Date npaDt = (java.util.Date)npaDetails.get("NPAClassifiedDate");
		Date npaDt = null;
		Date recallDt = null;
		SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
		try {
			if (!GenericValidator.isBlankOrNull(npaDtStr)) {
				npaDt = sdf.parse(npaDtStr);
			}
			if (!GenericValidator.isBlankOrNull(recallDtStr)) {
				recallDt = sdf.parse(recallDtStr);
			}
			if (!(GenericValidator.isBlankOrNull(recallDtStr))) {

				if ((npaDt.compareTo(recallDt) > 0)
						&& GenericValidator
								.isBlankOrNull(reasonForIssueRecallNotice)) {
					ActionError actionError = new ActionError(
							"reasonForIssueRecallNotice");
					errors.add(ActionErrors.GLOBAL_ERROR, actionError);
				}
			}
		} catch (NumberFormatException numberFormatException) {

			ActionError actionError = new ActionError("errors.date");
			errors.add(ActionErrors.GLOBAL_ERROR, actionError);

		}

		return errors.isEmpty();
	}

	public static boolean npaLegalDate(Object bean,
			ValidatorAction validAction, Field field, ActionErrors errors,
			HttpServletRequest request) throws Exception {
		ClaimActionForm cpTcDetailsForm = (ClaimActionForm) bean;

		String fromValue = ValidatorUtil.getValueAsString(bean,
				field.getProperty());

		String legalDtStr = cpTcDetailsForm.getLegaldate();
		String reasonForFilingSuit = cpTcDetailsForm.getReasonForFilingSuit();
		HashMap npaDetails = cpTcDetailsForm.getNpaDetails();
		String npaDtStr = (String) npaDetails.get("NPAClassifiedDate");
		Date npaDt = null;
		Date legalDt = null;
		SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
		try {
			if (!GenericValidator.isBlankOrNull(npaDtStr)) {
				npaDt = sdf.parse(npaDtStr);
			}
			if (!GenericValidator.isBlankOrNull(legalDtStr)) {
				legalDt = sdf.parse(legalDtStr);
			}
			if (!(GenericValidator.isBlankOrNull(legalDtStr))) {
				if ((npaDt.compareTo(legalDt) > 0)
						&& GenericValidator.isBlankOrNull(reasonForFilingSuit)) {
					ActionError actionError = new ActionError(
							"reasonForFilingSuit");
					errors.add(ActionErrors.GLOBAL_ERROR, actionError);
				}
			}
		} catch (NumberFormatException numberFormatException) {
			ActionError actionError = new ActionError("errors.date");
			errors.add(ActionErrors.GLOBAL_ERROR, actionError);
		}
		return errors.isEmpty();
	}

	public static boolean npaAssetPossessionDate(Object bean,
			ValidatorAction validAction, Field field, ActionErrors errors,
			HttpServletRequest request) throws Exception {
		ClaimActionForm cpTcDetailsForm = (ClaimActionForm) bean;
		String fromValue = ValidatorUtil.getValueAsString(bean,
				field.getProperty());
		String forumName = cpTcDetailsForm.getForumthrulegalinitiated();
		// Date assetDt = cpTcDetailsForm.getAssetPossessionDate();
		String assetDt = cpTcDetailsForm.getAssetPossessionDate();
		String assetDtStr = ValidatorUtil.getValueAsString(bean,
				"assetPossessionDate");
		SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
		Date dt = sdf.parse(assetDt);
		HashMap npaDetails = cpTcDetailsForm.getNpaDetails();
		String npaDtStr = (String) npaDetails.get("NPAClassifiedDate");
		Date npaDt = null;
		Date legalDt = null;
		try {
			if ("Securitisation Act, 2002".equals(forumName)) {
				if (!GenericValidator.isBlankOrNull(npaDtStr)) {
					npaDt = sdf.parse(npaDtStr);
				}
				if (!GenericValidator.isBlankOrNull(assetDtStr)) {
					legalDt = sdf.parse(assetDtStr);
				} else {
					ActionError actionError = new ActionError(
							"assetPossessionDateRequired");
					errors.add(ActionErrors.GLOBAL_ERROR, actionError);
				}
				if (!(GenericValidator.isBlankOrNull(assetDtStr))) {
					if ((npaDt.compareTo(dt) > 0)) {
						ActionError actionError = new ActionError(
								"assetPossessionDate");
						errors.add(ActionErrors.GLOBAL_ERROR, actionError);
					}
				}
			}
		} catch (NumberFormatException numberFormatException) {
			ActionError actionError = new ActionError("errors.date");
			errors.add(ActionErrors.GLOBAL_ERROR, actionError);
		}
		return errors.isEmpty();
	}

	public static boolean checkSuitAmount(Object bean,
			ValidatorAction validAction, Field field, ActionErrors errors,
			HttpServletRequest request) throws Exception {
		ClaimActionForm cpTcDetailsForm = (ClaimActionForm) bean;
		String suitAmount = (String) cpTcDetailsForm.getAmountclaimed();
		String forumName = (String) cpTcDetailsForm
				.getForumthrulegalinitiated();
		if (!GenericValidator.isBlankOrNull(suitAmount)) {
			double suitamt = Double.parseDouble(suitAmount);
			if (suitamt <= 0 && !"Securitisation Act, 2002".equals(forumName)) {
				ActionError actionError = new ActionError("suitAmount");
				errors.add(ActionErrors.GLOBAL_ERROR, actionError);
			}
		}
		return errors.isEmpty();
	}
//kot
	
	 public static boolean validateClaimPageFields(Object bean, ValidatorAction validAction, Field field, ActionErrors errors, HttpServletRequest request)
     throws Exception
 {
     ClaimActionForm cpForm = (ClaimActionForm)bean;
     String headedByOfficer = cpForm.getCommHeadedByOfficerOrAbove();
     if(headedByOfficer != null)
     {
         if(headedByOfficer.equals("Y"))
         {
             String inclusionOfReciept = cpForm.getInclusionOfReciept();
             if(GenericValidator.isBlankOrNull(inclusionOfReciept))
             {
                 ActionError actionError = new ActionError("inclusionOfReciept");
                 errors.add("org.apache.struts.action.GLOBAL_ERROR", actionError);
             }
             String forumName = cpForm.getForumthrulegalinitiated();
             String assetDtStr = ValidatorUtil.getValueAsString(bean, "assetPossessionDate");
             if("Securitisation Act, 2002".equals(forumName) && GenericValidator.isBlankOrNull(assetDtStr))
             {
                 ActionError actionError = new ActionError("assetPossessionDateRequired");
                 errors.add("org.apache.struts.action.GLOBAL_ERROR", actionError);
             }
             String officerName = cpForm.getDealingOfficerName();
             if(GenericValidator.isBlankOrNull(officerName))
             {
                 ActionError actionError = new ActionError("officerName");
                 errors.add("org.apache.struts.action.GLOBAL_ERROR", actionError);
             }
             String subsidyFlag = cpForm.getSubsidyFlag();
             String subsidyRcvdFlag = cpForm.getIsSubsidyRcvdAfterNpa();
             String subAdjustedFlag = cpForm.getIsSubsidyAdjustedOnDues();
             String subsidyDt = ValidatorUtil.getValueAsString(bean, "subsidyDate");
             String subsidyAmt = ValidatorUtil.getValueAsString(bean, "subsidyAmt");
             if("Y".equals(subsidyFlag))
                 if("Y".equals(subsidyRcvdFlag))
                 {
                     if("Y".equals(subAdjustedFlag))
                     {
                         if(GenericValidator.isBlankOrNull(subsidyDt))
                         {
                             ActionError actionError = new ActionError("subsidyDate");
                             errors.add("org.apache.struts.action.GLOBAL_ERROR", actionError);
                         }
                         if(!GenericValidator.isBlankOrNull(subsidyAmt))
                         {
                             if(Double.valueOf(Double.parseDouble(subsidyAmt)).doubleValue() <= 0.0D)
                             {
                                 ActionError actionError = new ActionError("subsidyAmount");
                                 errors.add("org.apache.struts.action.GLOBAL_ERROR", actionError);
                             }
                         } else
                         {
                             ActionError actionError = new ActionError("subsidyAmount");
                             errors.add("org.apache.struts.action.GLOBAL_ERROR", actionError);
                         }
                     } else
                     if(GenericValidator.isBlankOrNull(subAdjustedFlag))
                     {
                         ActionError actionError = new ActionError("subAdjustedFlag");
                         errors.add("org.apache.struts.action.GLOBAL_ERROR", actionError);
                     }
                 } else
                 if(GenericValidator.isBlankOrNull(subsidyRcvdFlag))
                 {
                     ActionError actionError = new ActionError("subsidyRcvdFlag");
                     errors.add("org.apache.struts.action.GLOBAL_ERROR", actionError);
                 }
             String isAcctFraud = cpForm.getIsAcctFraud();
             String isEnquiryConcluded = cpForm.getIsEnquiryConcluded();
             String isMliInvolved = cpForm.getIsMLIInvolved();
             if("Y".equals(isAcctFraud))
                 if("Y".equals(isEnquiryConcluded))
                 {
                     if(GenericValidator.isBlankOrNull(isMliInvolved))
                     {
                         ActionError actionError = new ActionError("isMliInvolved");
                         errors.add("org.apache.struts.action.GLOBAL_ERROR", actionError);
                     }
                 } else
                 if(GenericValidator.isBlankOrNull(isEnquiryConcluded))
                 {
                     ActionError actionError = new ActionError("isEnquiryConcluded");
                     errors.add("org.apache.struts.action.GLOBAL_ERROR", actionError);
                 }
         } else
         {
             String inclusionOfReciept = cpForm.getInclusionOfReciept();
             if(GenericValidator.isBlankOrNull(inclusionOfReciept))
             {
                 ActionError actionError = new ActionError("inclusionOfReciept");
                 errors.add("org.apache.struts.action.GLOBAL_ERROR", actionError);
             }
             String forumName = cpForm.getForumthrulegalinitiated();
             String suitAmount = cpForm.getAmountclaimed();
             if(!GenericValidator.isBlankOrNull(suitAmount))
             {
                 double suitamt = Double.parseDouble(suitAmount);
                 if(suitamt <= 0.0D && !"Securitisation Act, 2002".equals(forumName))
                 {
                     ActionError actionError = new ActionError("suitAmount");
                     errors.add("org.apache.struts.action.GLOBAL_ERROR", actionError);
                 }
             }
             String assetDtStr = ValidatorUtil.getValueAsString(bean, "assetPossessionDate");
             if("Securitisation Act, 2002".equals(forumName) && GenericValidator.isBlankOrNull(assetDtStr))
             {
                 ActionError actionError = new ActionError("assetPossessionDateRequired");
                 errors.add("org.apache.struts.action.GLOBAL_ERROR", actionError);
             }
             String officerName = cpForm.getDealingOfficerName();
             if(GenericValidator.isBlankOrNull(officerName))
             {
                 ActionError actionError = new ActionError("officerName");
                 errors.add("org.apache.struts.action.GLOBAL_ERROR", actionError);
             }
             String subsidyFlag = cpForm.getSubsidyFlag();
             String subsidyRcvdFlag = cpForm.getIsSubsidyRcvdAfterNpa();
             String subAdjustedFlag = cpForm.getIsSubsidyAdjustedOnDues();
             String subsidyDt = ValidatorUtil.getValueAsString(bean, "subsidyDate");
             String subsidyAmt = ValidatorUtil.getValueAsString(bean, "subsidyAmt");
             if("Y".equals(subsidyFlag))
                 if("Y".equals(subsidyRcvdFlag))
                 {
                     if("Y".equals(subAdjustedFlag))
                     {
                         if(GenericValidator.isBlankOrNull(subsidyDt))
                         {
                             ActionError actionError = new ActionError("subsidyDate");
                             errors.add("org.apache.struts.action.GLOBAL_ERROR", actionError);
                         }
                         if(!GenericValidator.isBlankOrNull(subsidyAmt))
                         {
                             if(Double.valueOf(Double.parseDouble(subsidyAmt)).doubleValue() <= 0.0D)
                             {
                                 ActionError actionError = new ActionError("subsidyAmount");
                                 errors.add("org.apache.struts.action.GLOBAL_ERROR", actionError);
                             }
                         } else
                         {
                             ActionError actionError = new ActionError("subsidyAmount");
                             errors.add("org.apache.struts.action.GLOBAL_ERROR", actionError);
                         }
                     } else
                     if(GenericValidator.isBlankOrNull(subAdjustedFlag))
                     {
                         ActionError actionError = new ActionError("subAdjustedFlag");
                         errors.add("org.apache.struts.action.GLOBAL_ERROR", actionError);
                     }
                 } else
                 if(GenericValidator.isBlankOrNull(subsidyRcvdFlag))
                 {
                     ActionError actionError = new ActionError("subsidyRcvdFlag");
                     errors.add("org.apache.struts.action.GLOBAL_ERROR", actionError);
                 }
             String isAcctFraud = cpForm.getIsAcctFraud();
             String isEnquiryConcluded = cpForm.getIsEnquiryConcluded();
             String isMliInvolved = cpForm.getIsMLIInvolved();
             if("Y".equals(isAcctFraud))
                 if("Y".equals(isEnquiryConcluded))
                 {
                     if(GenericValidator.isBlankOrNull(isMliInvolved))
                     {
                         ActionError actionError = new ActionError("isMliInvolved");
                         errors.add("org.apache.struts.action.GLOBAL_ERROR", actionError);
                     }
                 } else
                 if(GenericValidator.isBlankOrNull(isEnquiryConcluded))
                 {
                     ActionError actionError = new ActionError("isEnquiryConcluded");
                     errors.add("org.apache.struts.action.GLOBAL_ERROR", actionError);
                 }
         }
     } else
     {
         String inclusionOfReciept = cpForm.getInclusionOfReciept();
         if(GenericValidator.isBlankOrNull(inclusionOfReciept))
         {
             ActionError actionError = new ActionError("inclusionOfReciept");
             errors.add("org.apache.struts.action.GLOBAL_ERROR", actionError);
         }
         String forumName = cpForm.getForumthrulegalinitiated();
         String suitAmount = cpForm.getAmountclaimed();
         if(!GenericValidator.isBlankOrNull(suitAmount))
         {
             double suitamt = Double.parseDouble(suitAmount);
             if(suitamt <= 0.0D && !"Securitisation Act, 2002".equals(forumName))
             {
                 ActionError actionError = new ActionError("suitAmount");
                 errors.add("org.apache.struts.action.GLOBAL_ERROR", actionError);
             }
         }
         String assetDtStr = ValidatorUtil.getValueAsString(bean, "assetPossessionDate");
         if("Securitisation Act, 2002".equals(forumName) && GenericValidator.isBlankOrNull(assetDtStr))
         {
             ActionError actionError = new ActionError("assetPossessionDateRequired");
             errors.add("org.apache.struts.action.GLOBAL_ERROR", actionError);
         }
         String officerName = cpForm.getDealingOfficerName();
         if(GenericValidator.isBlankOrNull(officerName))
         {
             ActionError actionError = new ActionError("officerName");
             errors.add("org.apache.struts.action.GLOBAL_ERROR", actionError);
         }
         String subsidyFlag = cpForm.getSubsidyFlag();
         String subsidyRcvdFlag = cpForm.getIsSubsidyRcvdAfterNpa();
         String subAdjustedFlag = cpForm.getIsSubsidyAdjustedOnDues();
         String subsidyDt = ValidatorUtil.getValueAsString(bean, "subsidyDate");
         String subsidyAmt = ValidatorUtil.getValueAsString(bean, "subsidyAmt");
         if("Y".equals(subsidyFlag))
             if("Y".equals(subsidyRcvdFlag))
             {
                 if("Y".equals(subAdjustedFlag))
                 {
                     if(GenericValidator.isBlankOrNull(subsidyDt))
                     {
                         ActionError actionError = new ActionError("subsidyDate");
                         errors.add("org.apache.struts.action.GLOBAL_ERROR", actionError);
                     }
                     if(!GenericValidator.isBlankOrNull(subsidyAmt))
                     {
                         if(Double.valueOf(Double.parseDouble(subsidyAmt)).doubleValue() <= 0.0D)
                         {
                             ActionError actionError = new ActionError("subsidyAmount");
                             errors.add("org.apache.struts.action.GLOBAL_ERROR", actionError);
                         }
                     } else
                     {
                         ActionError actionError = new ActionError("subsidyAmount");
                         errors.add("org.apache.struts.action.GLOBAL_ERROR", actionError);
                     }
                 } else
                 if(GenericValidator.isBlankOrNull(subAdjustedFlag))
                 {
                     ActionError actionError = new ActionError("subAdjustedFlag");
                     errors.add("org.apache.struts.action.GLOBAL_ERROR", actionError);
                 }
             } else
             if(GenericValidator.isBlankOrNull(subsidyRcvdFlag))
             {
                 ActionError actionError = new ActionError("subsidyRcvdFlag");
                 errors.add("org.apache.struts.action.GLOBAL_ERROR", actionError);
             }
         String isAcctFraud = cpForm.getIsAcctFraud();
         String isEnquiryConcluded = cpForm.getIsEnquiryConcluded();
         String isMliInvolved = cpForm.getIsMLIInvolved();
         if("Y".equals(isAcctFraud))
             if("Y".equals(isEnquiryConcluded))
             {
                 if(GenericValidator.isBlankOrNull(isMliInvolved))
                 {
                     ActionError actionError = new ActionError("isMliInvolved");
                     errors.add("org.apache.struts.action.GLOBAL_ERROR", actionError);
                 }
             } else
             if(GenericValidator.isBlankOrNull(isEnquiryConcluded))
             {
                 ActionError actionError = new ActionError("isEnquiryConcluded");
                 errors.add("org.apache.struts.action.GLOBAL_ERROR", actionError);
             }
     }
     return errors.isEmpty();
 }

	public static boolean validateClaimPageFieldsold(Object bean,
			ValidatorAction validAction, Field field, ActionErrors errors,
			HttpServletRequest request) throws Exception {
		ClaimActionForm cpForm = (ClaimActionForm) bean;
		String inclusionOfReciept = cpForm.getInclusionOfReciept();
		if (GenericValidator.isBlankOrNull(inclusionOfReciept)) {
			ActionError actionError = new ActionError("inclusionOfReciept");
			errors.add(ActionErrors.GLOBAL_ERROR, actionError);
		}
		String forumName = (String) cpForm.getForumthrulegalinitiated();
		String suitAmount = (String) cpForm.getAmountclaimed();
		if (!GenericValidator.isBlankOrNull(suitAmount)) {
			double suitamt = Double.parseDouble(suitAmount);
			if (suitamt <= 0 && !"Securitisation Act, 2002".equals(forumName)) {
				ActionError actionError = new ActionError("suitAmount");
				errors.add(ActionErrors.GLOBAL_ERROR, actionError);
			}
		}

		String assetDtStr = ValidatorUtil.getValueAsString(bean,
				"assetPossessionDate");

		if ("Securitisation Act, 2002".equals(forumName)) {
			if (GenericValidator.isBlankOrNull(assetDtStr)) {
				ActionError actionError = new ActionError(
						"assetPossessionDateRequired");
				errors.add(ActionErrors.GLOBAL_ERROR, actionError);
			}
		}

		String officerName = cpForm.getDealingOfficerName();
		// if(GenericValidator.isBlankOrNull(phone)){
		// ActionError actionError=new ActionError("phoneNumber");
		// errors.add(ActionErrors.GLOBAL_ERROR,actionError);
		// }
		// if(GenericValidator.isBlankOrNull(email)){
		// ActionError actionError=new ActionError("emailAddress");
		// errors.add(ActionErrors.GLOBAL_ERROR,actionError);
		// }
		if (GenericValidator.isBlankOrNull(officerName)) {
			ActionError actionError = new ActionError("officerName");
			errors.add(ActionErrors.GLOBAL_ERROR, actionError);
		}

		String subsidyFlag = cpForm.getSubsidyFlag();
		String subsidyRcvdFlag = cpForm.getIsSubsidyRcvdAfterNpa();
		String subAdjustedFlag = cpForm.getIsSubsidyAdjustedOnDues();
		String subsidyDt = ValidatorUtil.getValueAsString(bean, "subsidyDate");
		String subsidyAmt = ValidatorUtil.getValueAsString(bean, "subsidyAmt");

		if ("Y".equals(subsidyFlag)) {
			if ("Y".equals(subsidyRcvdFlag)) {
				if ("Y".equals(subAdjustedFlag)) {
					if (GenericValidator.isBlankOrNull(subsidyDt)) {
						ActionError actionError = new ActionError("subsidyDate");
						errors.add(ActionErrors.GLOBAL_ERROR, actionError);
					}
					if (!GenericValidator.isBlankOrNull(subsidyAmt)) {
						if (((Double) Double.parseDouble(subsidyAmt))
								.doubleValue() <= 0.0) {
							ActionError actionError = new ActionError(
									"subsidyAmount");
							errors.add(ActionErrors.GLOBAL_ERROR, actionError);
						}
					} else {
						ActionError actionError = new ActionError(
								"subsidyAmount");
						errors.add(ActionErrors.GLOBAL_ERROR, actionError);
					}
				} else if (GenericValidator.isBlankOrNull(subAdjustedFlag)) {
					ActionError actionError = new ActionError("subAdjustedFlag");
					errors.add(ActionErrors.GLOBAL_ERROR, actionError);
				}
			} else if (GenericValidator.isBlankOrNull(subsidyRcvdFlag)) {
				ActionError actionError = new ActionError("subsidyRcvdFlag");
				errors.add(ActionErrors.GLOBAL_ERROR, actionError);
			}
		}

		String isAcctFraud = cpForm.getIsAcctFraud();
		String isEnquiryConcluded = cpForm.getIsEnquiryConcluded();
		String isMliInvolved = cpForm.getIsMLIInvolved();

		if ("Y".equals(isAcctFraud)) {
			if ("Y".equals(isEnquiryConcluded)) {
				if (GenericValidator.isBlankOrNull(isMliInvolved)) {
					ActionError actionError = new ActionError("isMliInvolved");
					errors.add(ActionErrors.GLOBAL_ERROR, actionError);
				}
			} else if (GenericValidator.isBlankOrNull(isEnquiryConcluded)) {
				ActionError actionError = new ActionError("isEnquiryConcluded");
				errors.add(ActionErrors.GLOBAL_ERROR, actionError);
			}
		}

		return errors.isEmpty();
	}

	public static boolean checkInterestRateForWCEnhancement(Object bean,
			ValidatorAction validAction, Field field, ActionErrors errors,
			HttpServletRequest request) throws Exception {
		DynaValidatorActionForm dynaForm = (DynaValidatorActionForm) bean;
		double interestRate = 0.0D;
		double baseRate = 0.0D;
		String loanType = (String) dynaForm.get("loanType");
		String message="";
		Date renewalEnhancementDate=null;
		String interestRateFiled = field.getProperty();
		//System.out.println(loanType+"checkInterestRate interestRateFiled "+interestRateFiled);
		if ("renewalFBInterest".equals(interestRateFiled)) {
			interestRate = ((Double) dynaForm.get("renewalFBInterest"))
					.doubleValue();
			baseRate = ((Double) dynaForm.get("wcPlr")).doubleValue();
			renewalEnhancementDate= (Date)dynaForm.get("renewalDate");
			message="Renewal Date ";
		} else if ("enhancedFBInterest".equals(interestRateFiled)) {
			interestRate = ((Double) dynaForm.get("enhancedFBInterest"))
					.doubleValue();
			renewalEnhancementDate= (Date)dynaForm.get("enhancementDate");
			//	System.out.println(loanType+"checkInterestRateForWCEnhancement before wcPlr");
				if(!"".equals(dynaForm.get("cgpan")))
				{
					baseRate = objAdminDAO.fetchOldOLDBaseRate((String)dynaForm.get("cgpan"));
				}
				else if(!"".equals(dynaForm.get("cgbid")))
				{
					baseRate = objAdminDAO.fetchOldOLDBaseRate((String)dynaForm.get("cgbid"));
				}
					
			
			//	System.out.println(loanType+"checkInterestRateForWCEnhancement after wcPlr"+baseRate);
				message="Enhancement Date ";
		}
		
	//	System.out.println("checkInterestRateForWCEnhancement d="+renewalEnhancementDate);
		try
		{
			if(loanType.equalsIgnoreCase("wc") && !"".equals(renewalEnhancementDate))
			{
			//	System.out.println("checkInterestRateForWCEnhancement d!=null"+renewalEnhancementDate);
				
				if(validatedateSanctionDate(renewalEnhancementDate))
				{
					
					if (interestRate > 14D) {   //  inserted due to cir dtd 06-01-2017
						//System.out.println("if checkInterestRate "+interestRate);
						ActionError error = new ActionError("baseRateAndInterestRateNewCir" , message);  
						errors.add("org.apache.struts.action.GLOBAL_ERROR", error);
						dynaForm.set("wcPlr",0.0);
					}
				
				}
				else
				{
					//System.out.println("interestRate ="+interestRate);
					//System.out.println("baseRate ="+(baseRate+4D));
					if (interestRate > (baseRate + 4D)) {
						ActionError error = new ActionError("baseRateAndInterestRate",baseRate,interestRate); //  removed due to cir dtd 06-01-2017
						errors.add("org.apache.struts.action.GLOBAL_ERROR", error);
					}
					else
					{
						//System.out.println("interestRate > baseRate + 4D else =");
					}
				}
			}
		}
		catch(Exception e)
		{
			 LogClass.StepWritterConnIssue("Exception in checkInterestRateForWCEnhancement "+e.getMessage());
			 LogClass.writeExceptionOnFile(e);
		}
		return errors.isEmpty();
	}
	public static boolean checkInterestRate(Object bean,
			ValidatorAction validAction, Field field, ActionErrors errors,
			HttpServletRequest request) throws Exception {
		
		try
		{
	//	System.out.println("checkInterestRate executed");
		DynaValidatorActionForm dynaForm = (DynaValidatorActionForm) bean;
		String loanType = (String) dynaForm.get("loanType");
		double interestRate = 0.0D;
		double baseRate = 0.0D;
		String interestRateFiled = field.getProperty();
	//	System.out.println(loanType+"checkInterestRate interestRateFiled "+interestRateFiled);
		if ("interestRate".equals(interestRateFiled)) {
			interestRate = ((Double) dynaForm.get("interestRate"))
					.doubleValue();
			baseRate = ((Double) dynaForm.get("plr")).doubleValue();
		} else if ("renewalFBInterest".equals(interestRateFiled)) {
			interestRate = ((Double) dynaForm.get("renewalFBInterest"))
					.doubleValue();
			baseRate = ((Double) dynaForm.get("wcPlr")).doubleValue();
		} else if ("limitFundBasedInterest".equals(interestRateFiled)) {
			interestRate = ((Double) dynaForm.get("limitFundBasedInterest"))
					.doubleValue();
			baseRate = ((Double) dynaForm.get("wcPlr")).doubleValue();
		} 
	/*	if (interestRate > baseRate + 4D) {
			ActionError error = new ActionError("baseRateAndInterestRate"); //  removed due to cir dtd 06-01-2017
			errors.add("org.apache.struts.action.GLOBAL_ERROR", error);
		} */
		Date sanctionedDate=null;
		Date funcBasedSanctionedDate=null;
		Date nonFuncBasedSanctionedDate=null;
		
		if(loanType.equalsIgnoreCase("tc") && !("").equals(dynaForm.get("amountSanctionedDate")))
		{
			sanctionedDate= (Date)dynaForm.get("amountSanctionedDate");
			if(validatedateSanctionDate(sanctionedDate))
			{
				if (interestRate > 14D) {   //  inserted due to cir dtd 06-01-2017
				//	System.out.println("if checkInterestRate "+interestRate);
					ActionError error = new ActionError("baseRateAndInterestRateNewCir"," Date of Sanction ");  
					errors.add("org.apache.struts.action.GLOBAL_ERROR", error);
					dynaForm.set("plr",0.0);
				}
			
			}
			else
			{
				//System.out.println("interestRate ="+interestRate);
				//System.out.println("baseRate ="+(baseRate+4D));
				if (interestRate > (baseRate + 4D)) {
					ActionError error = new ActionError("baseRateAndInterestRate",baseRate,interestRate); //  removed due to cir dtd 06-01-2017
					errors.add("org.apache.struts.action.GLOBAL_ERROR", error);
				}
				else
				{
					//System.out.println("interestRate > baseRate + 4D else =");
				}
			}
		}
		
		else if(loanType.equalsIgnoreCase("wc") && !dynaForm.get("limitFundBasedSanctionedDate").equals("") && !dynaForm.get("limitNonFundBasedSanctionedDate").equals(""))
		{
		//	System.out.println("checkInterestRate wc");
			funcBasedSanctionedDate= (Date)dynaForm.get("limitFundBasedSanctionedDate");
			nonFuncBasedSanctionedDate= (Date)dynaForm.get("limitNonFundBasedSanctionedDate");
			//System.out.println("validatedateForWC(loanType,d) "+validatedateSanctionDate(funcBasedSanctionedDate));
			//System.out.println("validatedateForWC(loanType,d1) "+validatedateSanctionDate(nonFuncBasedSanctionedDate));
			if(validatedateSanctionDate(funcBasedSanctionedDate) || validatedateSanctionDate(nonFuncBasedSanctionedDate))
			{
				if (interestRate > 14D) {   //  inserted due to cir dtd 06-01-2017
				//	System.out.println("if checkInterestRate "+interestRate);
					ActionError error = new ActionError("baseRateAndInterestRateNewCirfundAndNonFundbased" , funcBasedSanctionedDate, nonFuncBasedSanctionedDate);  
					errors.add("org.apache.struts.action.GLOBAL_ERROR", error);
					dynaForm.set("wcPlr",0.0);
				}
			
			}
			else
			{
				//System.out.println("interestRate ="+interestRate);
				///System.out.println("baseRate ="+(baseRate+4D));
				if (interestRate > (baseRate + 4D)) {
					ActionError error = new ActionError("baseRateAndInterestRate" , baseRate , interestRate); //  removed due to cir dtd 06-01-2017
					errors.add("org.apache.struts.action.GLOBAL_ERROR", error);
				}
				else
				{
				//	System.out.println("interestRate > baseRate + 4D else =");
				}
			}
		}
		else
		{
			if(!dynaForm.get("amountSanctionedDate").equals(""))
			{
				sanctionedDate= (Date)dynaForm.get("amountSanctionedDate");
				interestRate = ((Double) dynaForm.get("interestRate"))
				.doubleValue();
				if(validatedateSanctionDate(sanctionedDate))
				{
					if (interestRate > 14D) {   //  inserted due to cir dtd 06-01-2017
						//System.out.println("if checkInterestRate "+interestRate);
						ActionError error = new ActionError("baseRateAndInterestRateNewCir" ,"Sanction Date ");  
						errors.add("org.apache.struts.action.GLOBAL_ERROR", error);
						dynaForm.set("plr",0.0);
					}
				
				}
				else
				{
				//	System.out.println("interestRate ="+interestRate);
				//	System.out.println("baseRate ="+(baseRate+4D));
					baseRate = ((Double) dynaForm.get("plr")).doubleValue();
					if (interestRate > (baseRate + 4D)) {
						ActionError error = new ActionError("baseRateAndInterestRate", baseRate ,interestRate ); //  removed due to cir dtd 06-01-2017
						errors.add("org.apache.struts.action.GLOBAL_ERROR", error);
					}
					else
					{
					//	System.out.println("interestRate > baseRate + 4D else =");
					}
				}
			}
			
			if(!dynaForm.get("limitFundBasedSanctionedDate").equals("") && !dynaForm.get("limitNonFundBasedSanctionedDate").equals(""))
			{
				funcBasedSanctionedDate= (Date)dynaForm.get("limitFundBasedSanctionedDate");
				nonFuncBasedSanctionedDate= (Date)dynaForm.get("limitNonFundBasedSanctionedDate");
			//	System.out.println("validatedateForWC(loanType,d) "+validatedateSanctionDate(funcBasedSanctionedDate));
			//	System.out.println("validatedateForWC(loanType,d1) "+validatedateSanctionDate(nonFuncBasedSanctionedDate));
				interestRate = ((Double) dynaForm.get("limitFundBasedInterest"))
				.doubleValue();
				if(validatedateSanctionDate(funcBasedSanctionedDate) || validatedateSanctionDate(nonFuncBasedSanctionedDate))
				{
					if (interestRate > 14D) {   //  inserted due to cir dtd 06-01-2017
					//	System.out.println("if checkInterestRate "+interestRate);
						ActionError error = new ActionError("baseRateAndInterestRateNewCirfundAndNonFundbased",funcBasedSanctionedDate,nonFuncBasedSanctionedDate);  
						errors.add("org.apache.struts.action.GLOBAL_ERROR", error);
						dynaForm.set("wcPlr",0.0);
					}
				
				}
				else
				{
				//	System.out.println("interestRate ="+interestRate);
				//	System.out.println("baseRate ="+(baseRate+4D));
					baseRate = ((Double) dynaForm.get("wcPlr")).doubleValue();
					if (interestRate > (baseRate + 4D)) {
						ActionError error = new ActionError("baseRateAndInterestRate" , baseRate , interestRate ); //  removed due to cir dtd 06-01-2017
						errors.add("org.apache.struts.action.GLOBAL_ERROR", error);
					}
					else
					{
					//	System.out.println("interestRate > baseRate + 4D else =");
					}
				}
			}
		}
		
	//	System.out.println("checkInterestRate "+interestRate);
		}
		catch(Exception e)
		{
			 LogClass.StepWritterConnIssue("Exception in checkInterestRate "+e.getMessage());
			 LogClass.writeExceptionOnFile(e);
		}
		
		
		return errors.isEmpty();
	}
	
	
	  public static boolean PrimarySecurityCheck(Object bean, ValidatorAction validAction, Field field, ActionErrors errors, HttpServletRequest request)
	    {
	        DynaActionForm dynaForm = (DynaActionForm)bean;
	        String flag = (String)dynaForm.get("pSecurity");
	        if(flag.equals("N"))
	        {
	            ActionError actionError = new ActionError("PlsEnterSecFalg");
	            errors.add("org.apache.struts.action.GLOBAL_ERROR", actionError);
	        }
	        return errors.isEmpty();
	    }

	  
		public static boolean validatePanCardNo(Object bean,
				ValidatorAction validAction, Field field, ActionErrors errors,
				HttpServletRequest request) throws Exception {
		
		
		DynaActionForm dynaForm = (DynaActionForm) bean;
		String pancardNo = (String) dynaForm.get(field.getProperty());
		System.out.println("validatePanCardNo"+pancardNo);
		if(!pancardNo.trim().equals(""))
		{
			
			  Pattern pattern = Pattern.compile("[A-Z]{5}\\d{4}[A-Z]{1}");
		      Matcher matcher = pattern.matcher(pancardNo.toUpperCase());
		      
		      if (matcher.matches()) {
		    	  System.out.println("validatePanCardNo is valid"+pancardNo);
		      }
		      else
		      {
		    	  System.out.println("validatePanCardNo is invalid"+pancardNo);
		    	  ActionError actionError = new ActionError(field.getKey());
		          errors.add("org.apache.struts.action.GLOBAL_ERROR", actionError);		          
		      }
		}
			return errors.isEmpty();
		
		}
		
		
		
		/*public static boolean recordSelectionBorrowerApprovalDecision(Object bean,
				ValidatorAction validAction, Field field, ActionErrors errors,
				HttpServletRequest request) throws Exception {
		
			System.out.println("recordSelectionBorrowerApprovalDecision called");
		GMActionForm gmActionForm = (GMActionForm)bean;
		
		Map approveFlags = gmActionForm.getApproveBorrowerFlag();
		System.out.println("recordSelectionBorrowerApprovalDecision"+approveFlags.size());
		
		Set approveFlagSet = approveFlags.keySet();
		Iterator approveFlagIterator = approveFlagSet.iterator();
		
	
		
		int i=0;
	//	String remarksArray[]=gmActionForm.getBorrowerApprovalRemarks();
		//System.out.println("approveBorrowerDetails remarksArray "+remarksArray.length);
		boolean flag= true;
		while (approveFlagIterator.hasNext()) 
		{
		    String  memBorrowerId= (String)approveFlagIterator.next();   
		    String key = (String)approveFlags.get(memBorrowerId); 
		    if(key.equalsIgnoreCase("AP") || key.equalsIgnoreCase("RE"))
		    {
		    	flag=false;
		    }
		    if(flag==true)
		    {
		    	ActionError actionError = new ActionError("approveBorrowerFlag");
		          errors.add("org.apache.struts.action.GLOBAL_ERROR", actionError);	
		          System.out.println("No record selected");
		    }
		    System.out.println(key+"recordSelectionBorrowerApprovalDecision called value "+memBorrowerId);
		}
			return errors.isEmpty();
		
		} */
	
	/*	public static boolean constitutionvalidationforPartner(Object bean,
				ValidatorAction validAction, Field field, ActionErrors errors,
				HttpServletRequest request) {
	//		System.out.println("constitutionvalidationforPartner executed ");
			try
			{
			DynaActionForm dynaForm = (DynaActionForm) bean;
			String constituition = (String) dynaForm.get("constitution");
			String firstName= (String) dynaForm.get("firstName");
			String firstITPan= (String) dynaForm.get("firstItpan");
			Object firstDob=  dynaForm.get("firstDOB");
			 System.out.println("constitutionvalidationforPartner:--"+firstDob+"--");
			 
		

			if (!GenericValidator.isBlankOrNull(constituition)) {
			
				} else {
					ActionError error = new ActionError("constituitionRequired");
					errors.add("org.apache.struts.action.GLOBAL_ERROR", error);
				}
			
			
			 if(!constituition.equals(""))
			 {
				 if((constituition.equals("partnership")) || (constituition.equals("partnershipLimited")) || (constituition.equals("private"))
					 || (constituition.equals("private")) || (constituition.equals("public")) || (constituition.equals("Trust"))
					 || (constituition.equals("CoopSociety")) )
					 {
				//	 System.out.println("firstName "+firstName);
						 if(firstName.equals(""))
						 {
							 ActionError actionError = new ActionError("partnerFirstname");
					          errors.add("org.apache.struts.action.GLOBAL_ERROR", actionError);		
						 }
						 if(firstITPan.equals(""))
						 {
							 ActionError actionError = new ActionError("partnerFirstItpan");
					          errors.add("org.apache.struts.action.GLOBAL_ERROR", actionError);		
						 }
						 if(firstDob.toString().equals(""))
						 {
							 System.out.println("firstDob "+firstDob);
							 ActionError actionError = new ActionError("partnerFirstDob");
					          errors.add("org.apache.struts.action.GLOBAL_ERROR", actionError);		
						 }
						 else
						 {
							 System.out.println("firstDob not black "+firstDob);
						 }
					 }
			 }
			}
			catch(Exception e)
			{
				e.printStackTrace();
			}
			return errors.isEmpty();
		}
*/
		
		
		public static boolean validateForInternalRating(Object bean,
				ValidatorAction validAction, Field field, ActionErrors errors,
				HttpServletRequest request) {
			DynaActionForm dynaForm = (DynaActionForm) bean;
			
			try
			{
				String internalRating = (String) dynaForm.get("internalRating");
				//System.out.println("validateForInternalRating "+internalRating);
				
				double creditFundBased_new=0.00;
				double creditNonFundBased_new=0.00;
				double FundBased_nonFundBased_sum=0.00;
				double creditGuaranteed_new=0.00;
				String loanType = (String) dynaForm.get("loanType");
			//	System.out.println("loanType "+loanType);
				if(loanType!=null)
				{
					if(loanType.equalsIgnoreCase("tc") && dynaForm.get("creditGuaranteed")!=null)
					{
						//System.out.println("creditGuaranteed ="+dynaForm.get("creditGuaranteed"));
						String creditGuaranteed=dynaForm.get("creditGuaranteed").toString();		
						
						//System.out.println("creditGuaranteed "+creditGuaranteed);
						if(creditGuaranteed!=null && creditGuaranteed.length()>0)
						{
							creditGuaranteed_new=Double.parseDouble(dynaForm.get("creditGuaranteed").toString());
							//creditGuaranteed_new=dynaForm.get("creditGuaranteed");
							//System.out.println("creditGuaranteed_new "+creditGuaranteed_new);
							if(creditGuaranteed_new>=5000000 && internalRating.equalsIgnoreCase("N"))
							{
								ActionError actionError = new ActionError("selectInternalRating");
								errors.add("org.apache.struts.action.GLOBAL_ERROR", actionError);
							}
							else
							{
								if(creditGuaranteed_new < 5000000 && internalRating.equalsIgnoreCase("Y"))
								{
									ActionError actionError = new ActionError("lessThan50lakhInternalratingCondi");
									errors.add("org.apache.struts.action.GLOBAL_ERROR", actionError);
								}
								//System.out.println("creditGuaranteed_new>=500000 else loop");
							}
						}
						
					}
					else if(loanType.equalsIgnoreCase("wc"))
					{
						if(dynaForm.get("creditFundBased")!=null && dynaForm.get("creditNonFundBased")!=null)
						{
							String creditFundBased=dynaForm.get("creditFundBased").toString();
							String creditNonFundBased=dynaForm.get("creditNonFundBased").toString();					
							if((creditFundBased.length()>0) && (creditNonFundBased.length()>0))
							{
								creditFundBased_new=Double.parseDouble(creditFundBased);
								creditNonFundBased_new=Double.parseDouble(creditNonFundBased);
								FundBased_nonFundBased_sum=creditFundBased_new + creditNonFundBased_new;
								if(FundBased_nonFundBased_sum>=5000000 && internalRating.equalsIgnoreCase("N"))
								{
									ActionError actionError = new ActionError("selectInternalRating");
									errors.add("org.apache.struts.action.GLOBAL_ERROR", actionError);
								}
								else 
								{
									if(FundBased_nonFundBased_sum < 5000000 && internalRating.equalsIgnoreCase("Y"))
									{
										ActionError actionError = new ActionError("lessThan50lakhInternalratingCondi");
										errors.add("org.apache.struts.action.GLOBAL_ERROR", actionError);
									}
								}
							}
						}	
					} 
					else
					{
						if(dynaForm.get("creditFundBased")!=null && dynaForm.get("creditNonFundBased")!=null && dynaForm.get("creditGuaranteed")!=null)
						{
							String creditFundBased=dynaForm.get("creditFundBased").toString();
							String creditNonFundBased=dynaForm.get("creditNonFundBased").toString();	
							String creditGuaranteed=dynaForm.get("creditGuaranteed").toString();	
							if((creditGuaranteed.length()>0) && (creditFundBased.length()>0) && (creditNonFundBased.length()>0))
							{
								creditFundBased_new=Double.parseDouble(creditFundBased);
								creditNonFundBased_new=Double.parseDouble(creditNonFundBased);
								creditGuaranteed_new=Double.parseDouble(creditGuaranteed);
								FundBased_nonFundBased_sum=creditGuaranteed_new + creditFundBased_new + creditNonFundBased_new;
								if(FundBased_nonFundBased_sum>=5000000 && internalRating.equalsIgnoreCase("N"))
								{
									ActionError actionError = new ActionError("selectInternalRating");
									errors.add("org.apache.struts.action.GLOBAL_ERROR", actionError);
								}
								else
								{
									if(FundBased_nonFundBased_sum < 5000000 && internalRating.equalsIgnoreCase("Y"))
									{
										ActionError actionError = new ActionError("lessThan50lakhInternalratingCondi");
										errors.add("org.apache.struts.action.GLOBAL_ERROR", actionError);
									}
								}
							}
						}
					}
				}
			}
			catch(Exception e)
			{
				//e.printStackTrace();
			}
			return errors.isEmpty();
		}
		

		
		public static boolean validatedateSanctionDate(Date amountSanctionedDate)
		{
			boolean result=true;
			Date dtAmountSanctionedDate =null;
			try {
				Date date = sdf.parse("01/01/2017");
					if(amountSanctionedDate.before(date))
					{
						result=false;
					}
			
			}
				catch(Exception e)
				{
					e.printStackTrace();
					result=false;
				}
				//System.out.println("validatedateForTC result "+result);
				return result;
			
		}
		
		public static boolean validatedateSanctionDateForWC(Object fundBasedSanctionedDate ,Object nonFundBasedSanctionedDate)
		{
		//	System.out.println("validatedateForWC ");
			boolean result=true;
			Date fundBasedSanctionedDt =null;
			Date nonFundBasedSanctionedDt =null;
			try {
				Date constantDate = sdf.parse("01/01/2017");
				fundBasedSanctionedDt=(Date)fundBasedSanctionedDate;
				nonFundBasedSanctionedDt=(Date)nonFundBasedSanctionedDate;
				//	System.out.println("validatedateForWC d1.before(date) "+fundBasedSanctionedDt.before(constantDate));
				//	System.out.println("validatedateForWC d2.before(date) "+nonFundBasedSanctionedDt.before(constantDate));
						if(fundBasedSanctionedDt.before(constantDate) || nonFundBasedSanctionedDt.before(constantDate))
					{
						result=false;
					}
			
			}
				catch(Exception e)
				{
					 LogClass.StepWritterConnIssue("Exception in validatedateSanctionDateForWC "+e.getMessage());
					 LogClass.writeExceptionOnFile(e);
					e.printStackTrace();
					result=false;
				}
		//		System.out.println("validatedateForWC result "+result);
				return result;
			
		}
		
		public static boolean validatedateForCC(String loanType,Object SanctionedDate ,Object fundBasedSanctionedDate ,Object nonFundBasedSanctionedDate)
		{
			//System.out.println("validatedateForWC ");
			boolean result=true;
			
			try {
				Date date = sdf.parse("01/01/2017");
				Date fundBasedSanctionedDt=(Date)fundBasedSanctionedDate;
				Date nonFundBasedSanctionedDt=(Date)nonFundBasedSanctionedDate;
				Date sanctionedDt=(Date)SanctionedDate;
				if(loanType.equalsIgnoreCase("cc"))
				{
					
				//	if(((Date)fundBasedSanctionedDate.before(date)) || ((Date)nonFundBasedSanctionedDate.before(date)))
					//System.out.println("validatedateForWC d1.before(date) "+fundBasedSanctionedDt.before(date));
				//	System.out.println("validatedateForWC d2.before(date) "+nonFundBasedSanctionedDt.before(date));
						if(fundBasedSanctionedDt.before(date) || nonFundBasedSanctionedDt.before(date) || sanctionedDt.before(date))
					{
						result=false;
					}
				}
			}
				catch(Exception e)
				{
					LogClass.StepWritterConnIssue("Exception in validatedateForCC "+e.getMessage());
					 LogClass.writeExceptionOnFile(e);
					e.printStackTrace();
					result=false;
				}
			//	System.out.println("validatedateForWC result "+result);
				return result;
			
		}
		
		
		
		public static boolean validateNumber(String number)
		{
			boolean result=true;
			Double NumberParser=0.0;
			
				try
				{
				 NumberParser = Double.parseDouble(number);			
				}
				catch(Exception e)
				{					
					result=false;
				}
			
			//	System.out.println("validateNumber number" +number+" result"+result);
						return result;
		
		}
	
		public static boolean checkCreditAmtAndSanctionDateForTC(Object bean,
				ValidatorAction validAction, Field field, ActionErrors errors,
				HttpServletRequest request) {
			
			try
			{
	
			DynaValidatorActionForm dynaForm=(DynaValidatorActionForm) bean;
			 String memberId = (String)dynaForm.get("selectMember");
			 
			//	System.out.println("checkCreditAmtAndSanctionDate1111 memberId="+memberId );
		//	GauranteeAmountIncrementForm objGauranteeAmountIncrementForm=application.getGaurenteeIncrementForm();
			HttpSession session= request.getSession();
			String userId = (String)session.getAttribute("USER_ID");
			LogClass.StepWritterConnIssue(userId+"checkCreditAmtAndSanctionDateForTC memberId"+memberId);
			//System.out.println("TC creditGuaranteed getPreviouslyCovered "+dynaForm.get("previouslyCovered"));
			//	System.out.println("TC amountSanctionedDate getNone"+dynaForm.get("none"));
		//	System.out.println("TC creditFundBased getUnitValue"+dynaForm.get("unitValue"));
		//	System.out.println("TC creditFundBased cgbid"+dynaForm.get("cgbid"));
			
			
		//	System.out.println("TC creditFundBased userid"+dynaForm.get("USER_ID"));
		//	System.out.println("TC creditFundBased userid"+dynaForm.get("userID"));
		//	System.out.println("TC creditFundBased creditGuaranteed"+dynaForm.get("creditGuaranteed"));
		//	System.out.println("TC creditFundBased amountSanctionedDate"+dynaForm.get("amountSanctionedDate"));
			
			if(dynaForm.get("creditGuaranteed")!=null && dynaForm.get("amountSanctionedDate")!=null)
			{
				//if((Double)dynaForm.get("creditGuaranteed") > 0 && validatedate("TC", (String)dynaForm.get("amountSanctionedDate"), "", ""))
				if((Double)dynaForm.get("creditGuaranteed") > 0 )
				{
			
			
			HashMap dateAndAmountDtl =new HashMap();
			
			dateAndAmountDtl.put("amountSanctionedDate", dynaForm.get("amountSanctionedDate"));			
			dateAndAmountDtl.put("creditGuaranteed", dynaForm.get("creditGuaranteed"));
		
				HashMap basedOnCondition = new HashMap();
				
				basedOnCondition.put("previouslyCovered", dynaForm.get("previouslyCovered"));
				basedOnCondition.put("none", dynaForm.get("none"));
				basedOnCondition.put("cgbid", dynaForm.get("cgbid"));
				basedOnCondition.put("unitValue", dynaForm.get("unitValue"));
				
				if(sanctionDateAmountValidationAll("tc",dateAndAmountDtl,basedOnCondition)==false)
				{
					ActionError error = new ActionError((new StringBuilder())
							.append("creditAmountAndSanctionDateVali").toString() ,(Date)dynaForm.get("amountSanctionedDate") , dynaForm.get("creditGuaranteed"));
					errors.add("org.apache.struts.action.GLOBAL_ERROR", error);
					
				}
				 String bankId="";
			//	String userId = (String)session.getAttribute("USER_ID");
				 User user = (User)session.getAttribute(userId);
				     bankId = user.getBankId();
					 String validatorStr="FIvalidationForgauranteeAmt";
					 String amountRange="";
					
					 if(memberId.length()>1)
					 {
					   bankId =memberId.substring(0, 4);
					 }
					 
					 if(bankId.equals("0036"))
					  {
						 amountRange="1 Cr.";
						  validatorStr="OthervalidationForgauranteeAmt";
					  }
					 String classificationofMLI = objApplicationDAO.getClassificationMLI(bankId);
					 if(classificationofMLI.equalsIgnoreCase("RRB"))
					  {						
						  validatorStr="RRBvalidationForgauranteeAmt";
					  }
					 double existingAmountCC=0.0;
					 if("Y".equals(basedOnCondition.get("previouslyCovered")))
						{
					//	 System.out.println("fetchOldGauranteeAmount 1");
					existingAmountCC=objAdminDAO.fetchOldGauranteeAmount((String)basedOnCondition.get("none"),(String)basedOnCondition.get("unitValue"));
						}
					 boolean flag=true;
					 	if ( dynaForm.get("creditGuaranteed") instanceof Double )
					    {
					 		
					 		if((existingAmountCC+(Double)dynaForm.get("creditGuaranteed")) > maxApprovedAmt)
					 		{
					 			ActionError error = new ActionError("2crLimit",existingAmountCC,dynaForm.get("creditGuaranteed"));
								errors.add("org.apache.struts.action.GLOBAL_ERROR", error);
								flag=false;
					 		}
					 		if(flag==true  && verifyBankTypeAndApproveLoanAmount("tc",Double.toString((Double)dynaForm.get("creditGuaranteed")),"","",bankId,basedOnCondition,classificationofMLI)==false)
							{ 
						    ActionError error = new ActionError((new StringBuilder())
									.append(validatorStr).toString());
							errors.add("org.apache.struts.action.GLOBAL_ERROR", error);
							}
					 		
					    }
					    else if ( dynaForm.get("creditGuaranteed") instanceof String )
					    {
					    	if((existingAmountCC+(Double)dynaForm.get("creditGuaranteed")) > maxApprovedAmt)
					 		{
					 			ActionError error = new ActionError("2crLimit",existingAmountCC,dynaForm.get("creditGuaranteed"));
								errors.add("org.apache.struts.action.GLOBAL_ERROR", error);
								flag=false;
					 		}
					    	
					    	if(flag==true  && verifyBankTypeAndApproveLoanAmount("tc",(String)dynaForm.get("creditGuaranteed"),"","",bankId,basedOnCondition,classificationofMLI)==false)
							{ 
						    ActionError error = new ActionError((new StringBuilder())
									.append(validatorStr).toString());
							errors.add("org.apache.struts.action.GLOBAL_ERROR", error);
							}
					    }
					 	
				    
					 	dateAndAmountDtl.clear();
					 	basedOnCondition.clear();
				}
			}
			}
			catch(Exception e)
			{
				LogClass.StepWritterConnIssue("Exception in checkCreditAmtAndSanctionDateForTC "+e.getMessage());
				 LogClass.writeExceptionOnFile(e);
				e.printStackTrace();
			}
			return errors.isEmpty();
		} 
		
		
		public static boolean checkCreditAmtAndSanctionDateForWC(Object bean,
				ValidatorAction validAction, Field field, ActionErrors errors,
				HttpServletRequest request) {
			
			try
			{
			
			DynaValidatorActionForm dynaForm=(DynaValidatorActionForm) bean;
			 String memberId = (String)dynaForm.get("selectMember");
			// System.out.println("checkCreditAmtAndSanctionDateForWC WC memberId"+memberId );
		//	GauranteeAmountIncrementForm objGauranteeAmountIncrementForm=application.getGaurenteeIncrementForm();
			HttpSession session= request.getSession();
		
		//	System.out.println("TC creditFundBased userid"+dynaForm.get("USER_ID"));
		//	System.out.println("TC creditFundBased userid"+dynaForm.get("userID"));
		//	System.out.println("WC creditFundBased "+dynaForm.get("creditFundBased"));
		//	System.out.println("WC creditNonFundBased "+dynaForm.get("creditNonFundBased"));
			
		//	System.out.println("WC limitFundBasedSanctionedDate "+dynaForm.get("limitFundBasedSanctionedDate"));
		//	System.out.println("WC limitNonFundBasedSanctionedDate "+dynaForm.get("limitNonFundBasedSanctionedDate"));
			
			if(dynaForm.get("creditFundBased")!=null && dynaForm.get("creditNonFundBased")!=null)
			{
				//if((Double)dynaForm.get("creditGuaranteed") > 0 && validatedate("TC", (String)dynaForm.get("amountSanctionedDate"), "", ""))
			//	if((Double)dynaForm.get("creditFundBased") > 0 )
				{
			
			
			HashMap dateAndAmountDtl =new HashMap();
			
			dateAndAmountDtl.put("creditFundBased", dynaForm.get("creditFundBased"));			
			dateAndAmountDtl.put("creditNonFundBased", dynaForm.get("creditNonFundBased"));
			
			dateAndAmountDtl.put("limitFundBasedSanctionedDate", dynaForm.get("limitFundBasedSanctionedDate"));			
			dateAndAmountDtl.put("limitNonFundBasedSanctionedDate", dynaForm.get("limitNonFundBasedSanctionedDate"));
		
				HashMap basedOnCondition = new HashMap();
				
				basedOnCondition.put("previouslyCovered", dynaForm.get("previouslyCovered"));
				basedOnCondition.put("none", dynaForm.get("none"));
				basedOnCondition.put("cgbid", dynaForm.get("cgbid"));
				basedOnCondition.put("unitValue", dynaForm.get("unitValue"));
				
				if(sanctionDateAmountValidationAll("wc",dateAndAmountDtl,basedOnCondition)==false)
				{
					ActionError error = new ActionError((new StringBuilder())
							.append("creditAmountAndSanctionDateValiWC").toString() , dynaForm.get("limitFundBasedSanctionedDate") , dynaForm.get("limitNonFundBasedSanctionedDate") ,dynaForm.get("creditFundBased") ,dynaForm.get("creditNonFundBased"));
					errors.add("org.apache.struts.action.GLOBAL_ERROR", error);
					
				}
				 String bankId="";

				String userId = (String)session.getAttribute("USER_ID");
				 User user = (User)session.getAttribute(userId);
				     bankId = user.getBankId();
					 String validatorStr="FIvalidationForgauranteeAmt";
					 if(memberId.length()>1)
					 {
					  bankId =memberId.substring(0, 4);
					 }
					 if(bankId.equals("0036"))
					  {
						  
						  validatorStr="OthervalidationForgauranteeAmt";
					  }
					 String classificationofMLI = objApplicationDAO.getClassificationMLI(bankId);
					 if(classificationofMLI.equalsIgnoreCase("RRB"))
					  {						
						  validatorStr="RRBvalidationForgauranteeAmt";
					  }
					 
					 double existingAmountCC=0.0;
					 if("Y".equals(basedOnCondition.get("previouslyCovered")))
						{
					//	 System.out.println("fetchOldGauranteeAmount 2");
					existingAmountCC=objAdminDAO.fetchOldGauranteeAmount((String)basedOnCondition.get("none"),(String)basedOnCondition.get("unitValue"));
						}
					 boolean flag=true;
					 	if ( dynaForm.get("creditFundBased") instanceof Double )
					    {

					 		if(( existingAmountCC + (Double)dynaForm.get("creditFundBased") +(Double)dynaForm.get("creditNonFundBased") ) > maxApprovedAmt)
					 							 		{
					 							 			ActionError error = new ActionError("2crLimit" , existingAmountCC , ((Double)dynaForm.get("creditFundBased") +(Double)dynaForm.get("creditNonFundBased")) );
					 										errors.add("org.apache.struts.action.GLOBAL_ERROR", error);
					 										flag=false;
					 							 		}
					 		if( flag=true && verifyBankTypeAndApproveLoanAmount("wc","",Double.toString((Double)dynaForm.get("creditFundBased")),Double.toString((Double)dynaForm.get("creditNonFundBased")),bankId,basedOnCondition,classificationofMLI)==false)
							{ 
						    ActionError error = new ActionError((new StringBuilder())
									.append(validatorStr).toString());
							errors.add("org.apache.struts.action.GLOBAL_ERROR", error);
							}
					    }
					    else if ( dynaForm.get("creditFundBased") instanceof String )
					    {
					    	
					    	if(( (existingAmountCC + Integer.parseInt((String)dynaForm.get("creditFundBased") +Integer.parseInt((String)dynaForm.get("creditNonFundBased")))) > maxApprovedAmt))
						 		{
						 			ActionError error = new ActionError("2crLimit" ,existingAmountCC, (Integer.parseInt((String)dynaForm.get("creditFundBased") +Integer.parseInt((String)dynaForm.get("creditNonFundBased")))));
									errors.add("org.apache.struts.action.GLOBAL_ERROR", error);
									flag=false;
						 		}
					    	if( flag=true && verifyBankTypeAndApproveLoanAmount("wc","",(String)dynaForm.get("creditFundBased"),(String)dynaForm.get("creditNonFundBased"),bankId,basedOnCondition,classificationofMLI)==false)
							{ 
						    ActionError error = new ActionError((new StringBuilder())
									.append(validatorStr).toString());
							errors.add("org.apache.struts.action.GLOBAL_ERROR", error);
							}
					    }
					 	
				    
					 	dateAndAmountDtl.clear();
					 	basedOnCondition.clear(); 
				}
				
			}
			}
			catch(Exception e)
			{
				LogClass.StepWritterConnIssue("Exception in checkCreditAmtAndSanctionDateForWC "+e.getMessage());
				 LogClass.writeExceptionOnFile(e);
				e.printStackTrace();
			}
			return errors.isEmpty();
		} 
		
		public static boolean checkCreditAmtAndSanctionDateForAdditionalTC(Object bean,
				ValidatorAction validAction, Field field, ActionErrors errors,
				HttpServletRequest request) {
			
			HttpSession session= request.getSession();
		//	System.out.println("checkCreditAmtAndSanctionDate1111" );
			DynaValidatorActionForm dynaForm=(DynaValidatorActionForm) bean;
		//	GauranteeAmountIncrementForm objGauranteeAmountIncrementForm=application.getGaurenteeIncrementForm();
			 String memberId = (String)dynaForm.get("selectMember");
		//	 System.out.println("checkCreditAmtAndSanctionDate1111 WC memberId"+memberId );
			 
		//	System.out.println("Additional TC creditGuaranteed getPreviouslyCovered "+dynaForm.get("previouslyCovered"));
		//		System.out.println("Additional TC amountSanctionedDate getNone"+dynaForm.get("none"));
		//	System.out.println("Additional TC creditFundBased getUnitValue"+dynaForm.get("unitValue"));
		//	System.out.println("Additional TC creditFundBased cgbid"+dynaForm.get("cgbid"));
			
			
			try
			{
				if(dynaForm.get("creditGuaranteed")!=null && dynaForm.get("amountSanctionedDate")!=null)
				{
					//if((Double)dynaForm.get("creditGuaranteed") > 0 && validatedate("TC", (String)dynaForm.get("amountSanctionedDate"), "", ""))
				
					if((Double)dynaForm.get("creditGuaranteed") > 0 )
					{
				
				
				HashMap dateAndAmountDtl =new HashMap();
				
				dateAndAmountDtl.put("amountSanctionedDate", dynaForm.get("amountSanctionedDate"));			
				dateAndAmountDtl.put("creditGuaranteed", dynaForm.get("creditGuaranteed"));
			
					HashMap basedOnCondition = new HashMap();
					
					basedOnCondition.put("previouslyCovered", dynaForm.get("previouslyCovered"));
					basedOnCondition.put("none", dynaForm.get("none"));
					basedOnCondition.put("cgbid", dynaForm.get("cgbid"));
					basedOnCondition.put("unitValue", dynaForm.get("unitValue"));
					
					if(sanctionDateAmountValidationAll("tc",dateAndAmountDtl,basedOnCondition)==false)
					{
						ActionError error = new ActionError((new StringBuilder())
								.append("creditAmountAndSanctionDateVali").toString(), dynaForm.get("amountSanctionedDate") , dynaForm.get("creditGuaranteed"));
						errors.add("org.apache.struts.action.GLOBAL_ERROR", error);
						
					}
					
					String bankId="";
					//HttpSession session= request.getSession();
					String userId = (String)session.getAttribute("USER_ID");
									 User user = (User)session.getAttribute(userId);
									     bankId = user.getBankId();
									     if(memberId.length()>1)
									     {
					 bankId=memberId.substring(0, 4);
									     }
						 String validatorStr="FIvalidationForgauranteeAmt";
						 if(bankId.equals("0036"))
						  {
							  
							  validatorStr="OthervalidationForgauranteeAmt";
						  }
						 String classificationofMLI = objApplicationDAO.getClassificationMLI(bankId);
						 if(classificationofMLI.equalsIgnoreCase("RRB"))
						  {						
							  validatorStr="RRBvalidationForgauranteeAmt";
						  }
						 
						 double existingAmountCC=0.0;
						 if("Y".equals(basedOnCondition.get("previouslyCovered")))
							{
					//		 System.out.println("fetchOldGauranteeAmount 3");
						existingAmountCC=objAdminDAO.fetchOldGauranteeAmount((String)basedOnCondition.get("none"),(String)basedOnCondition.get("unitValue"));
							}
						 boolean flag=true;
						 	if ( dynaForm.get("creditGuaranteed") instanceof Double )
						    {
						 		
						 		if((existingAmountCC+(Double)dynaForm.get("creditGuaranteed")) > maxApprovedAmt)
						 		{
						 			ActionError error = new ActionError("2crLimit" , existingAmountCC , (Double)dynaForm.get("creditGuaranteed") );
									errors.add("org.apache.struts.action.GLOBAL_ERROR", error);
									flag=false;
						 		}
						 		
						 		if(flag=true && verifyBankTypeAndApproveLoanAmount("tc",Double.toString((Double)dynaForm.get("creditGuaranteed")),"","",bankId,basedOnCondition,classificationofMLI)==false)
								{ 
							    ActionError error = new ActionError((new StringBuilder())
										.append(validatorStr).toString());
								errors.add("org.apache.struts.action.GLOBAL_ERROR", error);
								}
						    }
						    else if ( dynaForm.get("creditGuaranteed") instanceof String )
						    {
						    	
						    	if( (existingAmountCC+Integer.parseInt((String)dynaForm.get("creditGuaranteed"))) > maxApprovedAmt)
						 		{
						 			ActionError error = new ActionError("2crLimit",existingAmountCC,Integer.parseInt((String)dynaForm.get("creditGuaranteed")));
									errors.add("org.apache.struts.action.GLOBAL_ERROR", error);
									flag=false;
						 		}
						    	
						    	if(flag=true && verifyBankTypeAndApproveLoanAmount("tc",(String)dynaForm.get("creditGuaranteed"),"","",bankId,basedOnCondition,classificationofMLI)==false)
								{ 
							    ActionError error = new ActionError((new StringBuilder())
										.append(validatorStr).toString());
								errors.add("org.apache.struts.action.GLOBAL_ERROR", error);
								}
						    }
						 	
						 	dateAndAmountDtl.clear();
						 	basedOnCondition.clear();
						
					}
				}
			}
			catch(Exception e)
			{
				LogClass.StepWritterConnIssue("Exception in checkCreditAmtAndSanctionDateForAdditionalTC "+e.getMessage());
				 LogClass.writeExceptionOnFile(e);
				e.printStackTrace();
			}
			return errors.isEmpty();
		} 
		
		public static boolean checkCreditAmtAndSanctionDateForEnhancementWC(Object bean,
				ValidatorAction validAction, Field field, ActionErrors errors,
				HttpServletRequest request) {
			try
			{
			System.out.println("checkCreditAmtAndSanctionDate1111" );
			DynaValidatorActionForm dynaForm=(DynaValidatorActionForm) bean;
			 String memberId = (String)dynaForm.get("selectMember");
			// System.out.println("checkCreditAmtAndSanctionDate1111 WC memberId"+memberId );
		//	GauranteeAmountIncrementForm objGauranteeAmountIncrementForm=application.getGaurenteeIncrementForm();
			//HttpSession session= request.getSession();
		//	System.out.println("Additional TC creditGuaranteed getPreviouslyCovered "+dynaForm.get("previouslyCovered"));
		//		System.out.println("Additional TC amountSanctionedDate getNone"+dynaForm.get("none"));
		//	System.out.println("Additional TC creditFundBased getUnitValue"+dynaForm.get("unitValue"));
		//	System.out.println("Additional TC creditFundBased cgbid"+dynaForm.get("cgbid"));
			

		//	System.out.println("Additional TC creditFundBased wcFundBasedSanctioned"+request.getParameter("wcFundBasedSanctioned"));
		//	System.out.println("Additional TC creditFundBased wcNonFundBasedSanctioned"+request.getParameter("wcNonFundBasedSanctioned"));
			
			
			
			if(dynaForm.get("wcFundBasedSanctioned")!=null && dynaForm.get("wcNonFundBasedSanctioned")!=null)
			{
				//if((Double)dynaForm.get("creditGuaranteed") > 0 && validatedate("TC", (String)dynaForm.get("amountSanctionedDate"), "", ""))
			//	if((Double)dynaForm.get("creditFundBased") > 0 )
				{
			
			
			HashMap dateAndAmountDtl =new HashMap();
			
			dateAndAmountDtl.put("wcFundBasedSanctioned", dynaForm.get("wcFundBasedSanctioned"));			
			dateAndAmountDtl.put("wcNonFundBasedSanctioned", dynaForm.get("wcNonFundBasedSanctioned"));
			
			dateAndAmountDtl.put("enhancementDate", dynaForm.get("enhancementDate"));			
			
		
				HashMap basedOnCondition = new HashMap();
				
				basedOnCondition.put("previouslyCovered", dynaForm.get("previouslyCovered"));
				basedOnCondition.put("none", dynaForm.get("none"));
				basedOnCondition.put("cgbid", dynaForm.get("cgbid"));
				basedOnCondition.put("unitValue", dynaForm.get("unitValue"));
				
				if(sanctionDateAmountValidationForWCEnhancement("wc",dateAndAmountDtl,basedOnCondition)==false)
				{
					ActionError error = new ActionError((new StringBuilder())
							.append("creditAmountAndSanctionDateValiWCEnhancement").toString() , dynaForm.get("enhancementDate") , dynaForm.get("wcFundBasedSanctioned"), dynaForm.get("wcNonFundBasedSanctioned"));
					errors.add("org.apache.struts.action.GLOBAL_ERROR", error);
					
				}
				 String bankId="";
				HttpSession session= request.getSession();
				String userId = (String)session.getAttribute("USER_ID");
								 User user = (User)session.getAttribute(userId);
								     bankId = user.getBankId();
								     
								     if(memberId.length()>1)
								     {
								    	 	bankId=memberId.substring(0, 4);
								     }
					 String validatorStr="FIvalidationForgauranteeAmt";
					 if(bankId.equals("0036"))
					  {
						  
						  validatorStr="OthervalidationForgauranteeAmt";
					  }
					 String classificationofMLI = objApplicationDAO.getClassificationMLI(bankId);
					 if(classificationofMLI.equalsIgnoreCase("RRB"))
					  {						
						  validatorStr="RRBvalidationForgauranteeAmt";
					  }
					 double existingAmountCC=0.0;
					 System.out.println("checkCreditAmtAndSanctionDate1111 WC previouslyCovered"+basedOnCondition.get("previouslyCovered") );
					 if("Y".equals(basedOnCondition.get("previouslyCovered")))
						{
					existingAmountCC=objAdminDAO.fetchOldGauranteeAmountForEnahncementCases((String)basedOnCondition.get("none"),(String)basedOnCondition.get("unitValue"));
						}
					 System.out.println("checkCreditAmtAndSanctionDate1111 WC existingAmountCC"+existingAmountCC );
					 boolean flag=true;
					 	if ( dynaForm.get("wcFundBasedSanctioned") instanceof Double )
					    {
					 		if(( existingAmountCC + (Double)dynaForm.get("wcFundBasedSanctioned") +(Double)dynaForm.get("wcNonFundBasedSanctioned") ) > maxApprovedAmt)
						 		{
						 			ActionError error = new ActionError("2crLimit", existingAmountCC ,((Double)dynaForm.get("wcFundBasedSanctioned") +(Double)dynaForm.get("wcNonFundBasedSanctioned")));
									errors.add("org.apache.struts.action.GLOBAL_ERROR", error);
									 flag=false;
						 		}


					 		if( flag=true && verifyBankTypeAndApproveLoanAmountForWcEnhancement("wc","",Double.toString((Double)dynaForm.get("wcFundBasedSanctioned")),Double.toString((Double)dynaForm.get("wcNonFundBasedSanctioned")),bankId,basedOnCondition,classificationofMLI)==false)
							{ 
						    ActionError error = new ActionError((new StringBuilder())
									.append(validatorStr).toString());
							errors.add("org.apache.struts.action.GLOBAL_ERROR", error);
							}
					    }
					    else if ( dynaForm.get("wcFundBasedSanctioned") instanceof String )
					    {
					    	
					    	if( (existingAmountCC +(Integer.parseInt((String)dynaForm.get("wcFundBasedSanctioned") +Integer.parseInt((String)dynaForm.get("wcNonFundBasedSanctioned")))) > maxApprovedAmt))
					 		{
					 			ActionError error = new ActionError("2crLimit" , existingAmountCC ,((Integer.parseInt((String)dynaForm.get("wcFundBasedSanctioned") +Integer.parseInt((String)dynaForm.get("wcNonFundBasedSanctioned"))))));
								errors.add("org.apache.struts.action.GLOBAL_ERROR", error);
								flag=false;
					 		}
					    	
					    	if(  flag=true && verifyBankTypeAndApproveLoanAmountForWcEnhancement("wc","",(String)dynaForm.get("wcFundBasedSanctioned"),(String)dynaForm.get("wcNonFundBasedSanctioned"),bankId,basedOnCondition,classificationofMLI)==false)
							{ 
						    ActionError error = new ActionError((new StringBuilder())
									.append(validatorStr).toString());
							errors.add("org.apache.struts.action.GLOBAL_ERROR", error);
							}
					    }
					 	
				    
					 	basedOnCondition.clear();
					 	dateAndAmountDtl.clear();	
				}
				
			}
			}
			catch(Exception e)
			{
				LogClass.StepWritterConnIssue("Exception in checkCreditAmtAndSanctionDateForEnhancementWC "+e.getMessage());
				 LogClass.writeExceptionOnFile(e);
				e.printStackTrace();
			}
			
			return errors.isEmpty();
		} 
		
		
		public static boolean checkCreditAmtAndSanctionDateForRenewalWC(Object bean,
				ValidatorAction validAction, Field field, ActionErrors errors,
				HttpServletRequest request) {
			try
			{
		//	System.out.println("checkCreditAmtAndSanctionDate1111" );
			DynaValidatorActionForm dynaForm=(DynaValidatorActionForm) bean;
	//		HttpSession session= request.getSession();
		//	GauranteeAmountIncrementForm objGauranteeAmountIncrementForm=application.getGaurenteeIncrementForm();
			 String memberId = (String)dynaForm.get("selectMember");
			// System.out.println("checkCreditAmtAndSanctionDate1111 WC memberId"+memberId );
			//System.out.println("Renewal WC creditGuaranteed getPreviouslyCovered "+dynaForm.get("previouslyCovered"));
			//	System.out.println("Renewal WC amountSanctionedDate getNone"+dynaForm.get("none"));
			//	
			//	System.out.println("Renewal WC creditFundBased wcFBsanctioned"+dynaForm.get("wcFundBasedSanctioned"));
			//	System.out.println("Renewal WC creditFundBased wcNFBsanctioned"+dynaForm.get("wcNonFundBasedSanctioned"));
			//	
			//System.out.println("Renewal WC creditFundBased getUnitValue"+dynaForm.get("unitValue"));
			//System.out.println("Renewal WC creditFundBased cgbid"+dynaForm.get("cgbid"));
			

			Date d= (Date)dynaForm.get("renewalDate");
			double sumofFundbasedNonFundBased=0.0;
			if ( dynaForm.get("wcFundBasedSanctioned") instanceof Double )
			{
			//	System.out.println("wc instanceof Double " );
				sumofFundbasedNonFundBased=(Double)dynaForm.get("wcFundBasedSanctioned")+(Double)dynaForm.get("wcNonFundBasedSanctioned");
			}
			else 	if ( dynaForm.get("wcFundBasedSanctioned") instanceof String )
			{
			//	System.out.println("wc instanceof String " );
				sumofFundbasedNonFundBased=Double.parseDouble((String)dynaForm.get("wcFundBasedSanctioned")+Double.parseDouble((String)dynaForm.get("wcNonFundBasedSanctioned")));
			}
			
			//System.out.println("checkCreditAmtAndSanctionDateForRenewalWC"+sumofFundbasedNonFundBased);
			//System.out.println("checkCreditAmtAndSanctionDateForRenewalWC validatedateForTC"+validatedateSanctionDate(d));
			if(sumofFundbasedNonFundBased > 10000000 && validatedateSanctionDate(d)==false)
			{
				ActionError error = new ActionError((new StringBuilder())
						.append("creditAmountAndSanctionDateValiWCRenewal").toString() , dynaForm.get("renewalDate") , dynaForm.get("wcFundBasedSanctioned") ,dynaForm.get("wcNonFundBasedSanctioned"));
				errors.add("org.apache.struts.action.GLOBAL_ERROR", error);
			}
			HashMap basedOnCondition= new HashMap();
			 String bankId="";
			HttpSession session= request.getSession();
			String userId = (String)session.getAttribute("USER_ID");
							 User user = (User)session.getAttribute(userId);
							     bankId = user.getBankId();
							     if(memberId.length()>1)
							     {
			bankId=memberId.substring(0, 4);
							     }
				 String validatorStr="FIvalidationForgauranteeAmt";
				 if(bankId.equals("0036"))
				  {
					  
					  validatorStr="OthervalidationForgauranteeAmt";
				  }
				 String classificationofMLI = objApplicationDAO.getClassificationMLI(bankId);
				 if(classificationofMLI.equalsIgnoreCase("RRB"))
				  {						
					  validatorStr="RRBvalidationForgauranteeAmt";
				  }
				 
				 double existingAmountCC=0.0;
				 if("Y".equals(basedOnCondition.get("previouslyCovered")))
					{
					 System.out.println("fetchOldGauranteeAmount 4");
			//	existingAmountCC=objAdminDAO.fetchOldGauranteeAmount((String)basedOnCondition.get("none"),(String)basedOnCondition.get("unitValue"));
					}
				 boolean flag=true;
					if ( dynaForm.get("wcFundBasedSanctioned") instanceof Double )
				    {
						if( ( existingAmountCC + (Double)dynaForm.get("wcFundBasedSanctioned") +(Double)dynaForm.get("wcNonFundBasedSanctioned") ) > maxApprovedAmt)
				 		{
				 			ActionError error = new ActionError("2crLimit" , existingAmountCC ,((Double)dynaForm.get("wcFundBasedSanctioned") +(Double)dynaForm.get("wcNonFundBasedSanctioned")) );
							errors.add("org.apache.struts.action.GLOBAL_ERROR", error);
							flag=false;
				 		}
				 		if(flag=true && verifyBankTypeAndApproveLoanAmount("wc","",Double.toString((Double)dynaForm.get("wcFundBasedSanctioned")),Double.toString((Double)dynaForm.get("wcNonFundBasedSanctioned")),bankId,basedOnCondition,classificationofMLI)==false)
						{ 
					    ActionError error = new ActionError((new StringBuilder())
								.append(validatorStr).toString());
						errors.add("org.apache.struts.action.GLOBAL_ERROR", error);
						}
				    }
				    else if ( dynaForm.get("wcFundBasedSanctioned") instanceof String )
				    {
				    	if( (existingAmountCC + (Integer.parseInt((String)dynaForm.get("wcFundBasedSanctioned") +Integer.parseInt((String)dynaForm.get("wcNonFundBasedSanctioned")))) > maxApprovedAmt))
				 		{
				 			ActionError error = new ActionError("2crLimit" , existingAmountCC , ((Integer.parseInt((String)dynaForm.get("wcFundBasedSanctioned") +Integer.parseInt((String)dynaForm.get("wcNonFundBasedSanctioned"))))));
							errors.add("org.apache.struts.action.GLOBAL_ERROR", error);
							flag=false;
				 		}
				    	
				    	if(flag=true &&  verifyBankTypeAndApproveLoanAmount("wc","",(String)dynaForm.get("wcFundBasedSanctioned"),(String)dynaForm.get("wcNonFundBasedSanctioned"),bankId,basedOnCondition,classificationofMLI)==false)
						{ 
					    ActionError error = new ActionError((new StringBuilder())
								.append(validatorStr).toString());
						errors.add("org.apache.struts.action.GLOBAL_ERROR", error);
						}
				    }
			 
		}
		catch(Exception e)
		{
			LogClass.StepWritterConnIssue("Exception in checkCreditAmtAndSanctionDateForRenewalWC "+e.getMessage());
			 LogClass.writeExceptionOnFile(e);
			e.printStackTrace();
		}
			return errors.isEmpty();
		} 
		
		public static boolean checkCreditAmtAndSanctionDate(Object bean,
				ValidatorAction validAction, Field field, ActionErrors errors,
				HttpServletRequest request) {
			try
			{
			
			DynaValidatorActionForm dynaForm=(DynaValidatorActionForm) bean;
			String memberId = (String)dynaForm.get("selectMember");
			//System.out.println("checkCreditAmtAndSanctionDate1111 CC memberId="+memberId );
		//	GauranteeAmountIncrementForm objGauranteeAmountIncrementForm=application.getGaurenteeIncrementForm();
			
		//	System.out.println("creditGuaranteed getPreviouslyCovered "+dynaForm.get("previouslyCovered"));
		//		System.out.println("amountSanctionedDate getNone"+dynaForm.get("previouslyCovered"));
		//	System.out.println("creditFundBased getUnitValue"+dynaForm.get("unitValue"));
		//	System.out.println("creditFundBased cgbid"+dynaForm.get("cgbid"));
			
			
		//	HttpSession session= request.getSession();
	//		System.out.println("checkCreditAmtAndSanctionDate "+request.getParameter("BankId"));
			
			
HashMap dateAndAmountDtl =new HashMap();
			
			dateAndAmountDtl.put("creditGuaranteed", dynaForm.get("creditGuaranteed"));			
			dateAndAmountDtl.put("amountSanctionedDate", dynaForm.get("amountSanctionedDate"));
			dateAndAmountDtl.put("creditFundBased", dynaForm.get("creditFundBased"));			
			dateAndAmountDtl.put("creditNonFundBased", dynaForm.get("creditNonFundBased"));
			dateAndAmountDtl.put("limitFundBasedSanctionedDate", dynaForm.get("limitFundBasedSanctionedDate"));			
			dateAndAmountDtl.put("limitNonFundBasedSanctionedDate", dynaForm.get("limitNonFundBasedSanctionedDate"));
		
				HashMap basedOnCondition = new HashMap();
				
				basedOnCondition.put("previouslyCovered", dynaForm.get("previouslyCovered"));
				basedOnCondition.put("none", dynaForm.get("none"));
				basedOnCondition.put("cgbid", dynaForm.get("cgbid"));
				basedOnCondition.put("unitValue", dynaForm.get("unitValue"));
				
				double existingAmountCC=0.0;
				
			String creditGuaranteed = request.getParameter("creditGuaranteed");
			String amountSanctionedDate = request.getParameter("amountSanctionedDate");
			String creditFundBased = request.getParameter("creditFundBased");
			String creditNonFundBased = request.getParameter("creditNonFundBased");
			String limitFundBasedSanctionedDate = request.getParameter("limitFundBasedSanctionedDate");
			String limitNonFundBasedSanctionedDate = request.getParameter("limitNonFundBasedSanctionedDate");
			
			String limitNonFundBasedCommission = request.getParameter("limitNonFundBasedCommission");
			
			String previouslyCovered = request.getParameter("previouslyCovered");
			
			String none = request.getParameter("none");
			String cgbid = request.getParameter("cgbid");
			String unitValue = request.getParameter("unitValue");
			String loanType = request.getParameter("loanType");
			
			if(loanType==null)
			{
				loanType=(String)dynaForm.get("loanType");
			}
			LogClass.StepWritterConnIssue("checkCreditAmtAndSanctionDate loanType"+loanType);
			LogClass.StepWritterConnIssue("checkCreditAmtAndSanctionDate loanType=="+dynaForm.get("loanType"));
			
	/*	System.out.println("creditGuaranteed"+creditGuaranteed);
		System.out.println("amountSanctionedDate"+amountSanctionedDate);
		System.out.println("creditFundBased"+creditFundBased);
		System.out.println("creditNonFundBased"+creditNonFundBased);
		System.out.println("limitFundBasedSanctionedDate"+limitFundBasedSanctionedDate);
		System.out.println("limitNonFundBasedCommission"+limitNonFundBasedCommission);
		
		System.out.println("previouslyCovered"+previouslyCovered);
		
		System.out.println("none"+none);
		System.out.println("cgbid"+cgbid);*/
		
		/*String strPreviouslyCovered="";
		String strNone="";
		String strCgbid="";
		
		if(previouslyCovered!=null && previouslyCovered.equalsIgnoreCase("Y"))
		{
			session.setAttribute("previouslyCovered", previouslyCovered);
			session.setAttribute("none", none);
			session.setAttribute("cgbid", cgbid);
			session.setAttribute("unitValue", unitValue);
			
			
		}
		
		
		if(previouslyCovered==null && none==null && cgbid==null)
		{
			previouslyCovered=(String)session.getAttribute("previouslyCovered");
			none=(String)session.getAttribute("none");
			cgbid=(String)session.getAttribute("cgbid");
			unitValue=(String)session.getAttribute("unitValue");
		}
		
		
		System.out.println("1111previouslyCovered"+previouslyCovered);
		
		System.out.println("11111none"+none);
		System.out.println("11111cgbid"+cgbid);
		
	//	HashMap
		HashMap dateAndAmountDtl =new HashMap();
		
	dateAndAmountDtl.put("amountSanctionedDate", amountSanctionedDate);
	dateAndAmountDtl.put("limitFundBasedSanctionedDate", limitFundBasedSanctionedDate);
	dateAndAmountDtl.put("limitNonFundBasedSanctionedDate", limitNonFundBasedCommission);
	dateAndAmountDtl.put("creditGuaranteed", creditGuaranteed);
	dateAndAmountDtl.put("creditFundBased", creditFundBased);
	dateAndAmountDtl.put("creditNonFundBased", creditNonFundBased);
	
		HashMap basedOnCondition = new HashMap();
		
		basedOnCondition.put("previouslyCovered", previouslyCovered);
		basedOnCondition.put("none", none);
		basedOnCondition.put("cgbid", cgbid);
		basedOnCondition.put("unitValue", unitValue); */
		
		if(sanctionDateAmountValidationAll(loanType,dateAndAmountDtl,basedOnCondition)==false)
		{
			ActionError error = new ActionError((new StringBuilder())
					.append("guarenteeAmountIncrementvalidationForAll").toString(),amountSanctionedDate,limitFundBasedSanctionedDate,limitNonFundBasedSanctionedDate);
			errors.add("org.apache.struts.action.GLOBAL_ERROR", error);
			
		}
		
		//String userId = (String)session.getAttribute("USER_ID");
	//	 User user = (User)session.getAttribute(userId);
		  //  String bankId = user.getBankId();
		String bankId="";
		HttpSession session= request.getSession();
		String userId = (String)session.getAttribute("USER_ID");
						 User user = (User)session.getAttribute(userId);
						     bankId = user.getBankId();
						     if(memberId.length()>1)
						     {
		  bankId=memberId.substring(0, 4);
						     }
			 String validatorStr="FIvalidationForgauranteeAmt";
			 if(bankId.equals("0036"))
			  {
				  
				  validatorStr="OthervalidationForgauranteeAmt";
			  }
			 String classificationofMLI = objApplicationDAO.getClassificationMLI(bankId);
			 if(classificationofMLI.equalsIgnoreCase("RRB"))
			  {						
				  validatorStr="RRBvalidationForgauranteeAmt";
			  }
			 boolean flag=true;
			 if("Y".equals(basedOnCondition.get("previouslyCovered")))
				{
			//	 System.out.println("fetchOldGauranteeAmount 5");
			existingAmountCC=objAdminDAO.fetchOldGauranteeAmount((String)basedOnCondition.get("none"),(String)basedOnCondition.get("unitValue"));
				}	
			 if ( dateAndAmountDtl.get("creditGuaranteed") instanceof Double )
				{
		//		 System.out.println("checkCreditAmtAndSanctionDate1111 CC instanceof Double="+((Double)dateAndAmountDtl.get("creditGuaranteed")+(Double)dateAndAmountDtl.get("creditFundBased")+(Double)dateAndAmountDtl.get("creditFundBased")) );
				 if(((Double)dateAndAmountDtl.get("creditGuaranteed")+(Double)dateAndAmountDtl.get("creditFundBased")+(Double)dateAndAmountDtl.get("creditNonFundBased")+existingAmountCC) > maxApprovedAmt)
				 {
					 ActionError error = new ActionError("2crLimit", existingAmountCC , (((Double)dateAndAmountDtl.get("creditGuaranteed")+(Double)dateAndAmountDtl.get("creditFundBased")+(Double)dateAndAmountDtl.get("creditNonFundBased")))) ;
						errors.add("org.apache.struts.action.GLOBAL_ERROR", error);
						flag=false;
				 }
				}
			 else  if ( dateAndAmountDtl.get("creditGuaranteed") instanceof String )
				{
			//	 System.out.println("checkCreditAmtAndSanctionDate1111 CC instanceof String="+((Integer.parseInt((String)dateAndAmountDtl.get("creditGuaranteed"))+Integer.parseInt((String)dateAndAmountDtl.get("creditFundBased"))+Integer.parseInt((String)dateAndAmountDtl.get("creditFundBased")))) );
				 if(((Integer.parseInt((String)dateAndAmountDtl.get("creditGuaranteed"))+Integer.parseInt((String)dateAndAmountDtl.get("creditFundBased"))+Integer.parseInt((String)dateAndAmountDtl.get("creditNonFundBased") +existingAmountCC ))) > maxApprovedAmt)
				 {
					 ActionError error = new ActionError("2crLimit" , existingAmountCC ,(((Integer.parseInt((String)dateAndAmountDtl.get("creditGuaranteed"))+Integer.parseInt((String)dateAndAmountDtl.get("creditFundBased"))+Integer.parseInt((String)dateAndAmountDtl.get("creditNonFundBased") +existingAmountCC )))));
						errors.add("org.apache.struts.action.GLOBAL_ERROR", error);
						flag=false;
				 }
				}
		    if(flag=true && verifyBankTypeAndApproveLoanAmount(loanType,creditGuaranteed,creditFundBased,creditNonFundBased,bankId,basedOnCondition,classificationofMLI)==false)
			{ 
			    ActionError error = new ActionError((new StringBuilder())
						.append(validatorStr).toString());
				errors.add("org.apache.struts.action.GLOBAL_ERROR", error);
			}
		    dateAndAmountDtl.clear();
		    basedOnCondition.clear();
			}
			catch(Exception e)
			{
				LogClass.StepWritterConnIssue("Exception in checkCreditAmtAndSanctionDate "+e.getMessage());
				 LogClass.writeExceptionOnFile(e);
				e.printStackTrace();
			}
			return errors.isEmpty();
		} 
		public static boolean sanctionDateAmountValidationForWCEnhancement(String loanType, HashMap dateAndAmountDtl ,HashMap basedOnCondition)
		{
			boolean resultFlag=true;
			try
			{
				
				
				double sumofFundbasedNonFundBased=0.0;
				if(loanType!=null)
				{
			
					if(loanType.equalsIgnoreCase("wc"))
					{
						Date wcEnhancementDate= (Date)dateAndAmountDtl.get("enhancementDate");
						//if(previouslyCovered!=null)
						{
							
							if ( dateAndAmountDtl.get("wcFundBasedSanctioned") instanceof Double )
							{
							//	System.out.println("wc instanceof Double " );
								sumofFundbasedNonFundBased=(Double)dateAndAmountDtl.get("wcFundBasedSanctioned")+(Double)dateAndAmountDtl.get("wcNonFundBasedSanctioned");
							}
							else 	if ( dateAndAmountDtl.get("wcFundBasedSanctioned") instanceof String )
							{
							//	System.out.println("wc instanceof String " );
								sumofFundbasedNonFundBased=Double.parseDouble((String)dateAndAmountDtl.get("wcFundBasedSanctioned")+Double.parseDouble((String)dateAndAmountDtl.get("wcNonFundBasedSanctioned")));
							}
							
							//totalAmount=totalAmount+Double.parseDouble((String)dateAndAmountDtl.get("creditFundBased")+Double.parseDouble((String)dateAndAmountDtl.get("creditNonFundBased")));
							
							//System.out.println("WC sumofFundbasedNonFundBased "+sumofFundbasedNonFundBased );
	
							if((sumofFundbasedNonFundBased) > 10000000 && validatedateSanctionDate(wcEnhancementDate)==false)
							//	if((sumofFundbasedNonFundBased) > 10000000 )
							{
							//	System.out.println("wc if  enhancement" );
								resultFlag=false;
							}
						}
					}
				}
			}
			catch(Exception e)
			{
				LogClass.StepWritterConnIssue("Exception in sanctionDateAmountValidationForWCEnhancement "+e.getMessage());
				 LogClass.writeExceptionOnFile(e);
				 
				e.printStackTrace();
			}
			return resultFlag;
		}
		public static boolean sanctionDateAmountValidationAll(String loanType, HashMap dateAndAmountDtl ,HashMap basedOnCondition)
		{
		//	System.out.println("sanctionDateAmountValidationAll executed == " );
			boolean resultFlag=true;
			try
			{
			
			String previouslyCovered="" , none="" , cgbid="" , unitValue="";
			double totalAmount=0.0;
		//	System.out.println("sanctionDateAmountValidationAll loanType =="+loanType+"=" );
			if(loanType!=null)
			{
				previouslyCovered=(String)basedOnCondition.get("previouslyCovered");
				none=(String)basedOnCondition.get("none");
				cgbid=(String)basedOnCondition.get("cgbid");
				unitValue=(String)basedOnCondition.get("unitValue");
				
			//	System.out.println("previouslyCovered== +previouslyCovered" );
			//	System.out.println("none == "+none );
			//	System.out.println("cgbid == "+cgbid );
			//	System.out.println("unitValue =="+unitValue );
				
				if(loanType.equalsIgnoreCase("tc"))
				{					
					Date amountSanctionedDt= (Date)dateAndAmountDtl.get("amountSanctionedDate");
				//	System.out.println("sanctionDateAmountValidationAll 111=");			
					if(previouslyCovered!=null)
					{
					//	System.out.println("sanctionDateAmountValidationAll 222=");
						if(none!=null) // cgbid cgpan
						{
					//		System.out.println("sanctionDateAmountValidationAll 333=");
							try
							{	
								//System.out.println("chkEmptydate amountSanctionedDate=="+dateAndAmountDtl.get("amountSanctionedDate") );								if(chkEmptydate((String)dateAndAmountDtl.get("amountSanctionedDate")))
								{
									//	System.out.println("1st if " );
								//	if(validateNumber((String)dateAndAmountDtl.get("creditGuaranteed")))
									{
										if(previouslyCovered.equalsIgnoreCase("Y"))
										{
											System.out.println("fetchOldGauranteeAmount 6");
											totalAmount=objAdminDAO.fetchOldGauranteeAmount(none,unitValue);
										}		
										
										if ( dateAndAmountDtl.get("creditGuaranteed") instanceof Double )
									    {
											totalAmount=totalAmount+(Double)dateAndAmountDtl.get("creditGuaranteed");
									    }
										else if ( dateAndAmountDtl.get("creditGuaranteed") instanceof String )
										{
											totalAmount=totalAmount+Double.parseDouble((String)dateAndAmountDtl.get("creditGuaranteed"));
										}
									
										if(totalAmount > 10000000 && validatedateSanctionDate(amountSanctionedDt)==false)
										{
											resultFlag=false;
										}
									}
								}
							}
							catch(Exception e)
							{					
								e.printStackTrace();
							}
						}
					}					
					
				}
				else if(loanType.equalsIgnoreCase("wc"))
				{
					//if(previouslyCovered!=null )
					{						
						//if(none!=null) // cgbid cgpan
						{
							try
							{	
								//if(chkEmptydate((String)dateAndAmountDtl.get("limitFundBasedSanctionedDate")) && chkEmptydate((String)dateAndAmountDtl.get("limitNonFundBasedSanctionedDate")))
								{
								//	System.out.println("1st if WC " + dateAndAmountDtl.get("creditFundBased"));
								//	System.out.println("1st if WC " + dateAndAmountDtl.get("creditNonFundBased"));
										//if(validateNumber((String)dateAndAmountDtl.get("creditFundBased")) && validateNumber((String)dateAndAmountDtl.get("creditNonFundBased")))
									if(previouslyCovered!=null)
									{
									//	System.out.println("sanctionDateAmountValidationAll 222=");
										if(none!=null) // cgbid cgpan
										{
											
														if(previouslyCovered.equalsIgnoreCase("Y"))
														{
															//System.out.println("fetchOldGauranteeAmount 7");
															totalAmount=objAdminDAO.fetchOldGauranteeAmount(none,unitValue);
														}	
											if ( dateAndAmountDtl.get("creditFundBased") instanceof Double )
											{
											//	System.out.println("wc instanceof Double " );
												totalAmount=totalAmount+(Double)dateAndAmountDtl.get("creditFundBased")+(Double)dateAndAmountDtl.get("creditNonFundBased");
											}
											else 	if ( dateAndAmountDtl.get("creditFundBased") instanceof String )
											{
											//	System.out.println("wc instanceof String " );
												totalAmount=totalAmount+Double.parseDouble((String)dateAndAmountDtl.get("creditFundBased")+Double.parseDouble((String)dateAndAmountDtl.get("creditNonFundBased")));
											}
											
											//totalAmount=totalAmount+Double.parseDouble((String)dateAndAmountDtl.get("creditFundBased")+Double.parseDouble((String)dateAndAmountDtl.get("creditNonFundBased")));
										//	System.out.println("WC total "+totalAmount );
											if(totalAmount > 10000000 && validatedateSanctionDateForWC(dateAndAmountDtl.get("limitFundBasedSanctionedDate"),dateAndAmountDtl.get("limitNonFundBasedSanctionedDate"))==false)
											{
										//		System.out.println("wc if  " );
												resultFlag=false;
											}
											
										}
									}
											
									
								}
							}
							catch(Exception e)
							{
								e.printStackTrace();
							}
						}
					}
					
				}
				else 
				{
					if(previouslyCovered!=null )
					{						
						if(none!=null) // cgbid cgpan
						{
							try
							{	
						//	if(validatedateForTC("tc",(Date)dateAndAmountDtl.get("amountSanctionedDate")) && validatedateForWC("wc",(Date)dateAndAmountDtl.get("limitFundBasedSanctionedDate")) && chkEmptydate((String)dateAndAmountDtl.get("limitNonFundBasedSanctionedDate")))
							{
							//	System.out.println("1st if " );
								//	if(validateNumber((String)dateAndAmountDtl.get("creditGuaranteed")) && validateNumber((String)dateAndAmountDtl.get("creditFundBased")) && validateNumber((String)dateAndAmountDtl.get("creditNonFundBased")))
									{		
										if(previouslyCovered.equalsIgnoreCase("Y"))
										{
										//	System.out.println("fetchOldGauranteeAmount 8");
											totalAmount=objAdminDAO.fetchOldGauranteeAmount(none,unitValue);	
										}
										if ( dateAndAmountDtl.get("creditFundBased") instanceof Double )
										{
											totalAmount=totalAmount+(Double)dateAndAmountDtl.get("creditGuaranteed")+(Double)dateAndAmountDtl.get("creditFundBased")+(Double)dateAndAmountDtl.get("creditNonFundBased");
										}
										else 	if ( dateAndAmountDtl.get("creditFundBased") instanceof String )
										{
											totalAmount=totalAmount+Double.parseDouble((String)dateAndAmountDtl.get("creditGuaranteed")+Double.parseDouble((String)dateAndAmountDtl.get("creditFundBased")+Double.parseDouble((String)dateAndAmountDtl.get("creditNonFundBased"))));
										}
											
										
											if(totalAmount > 10000000 && validatedateForCC("CC",dateAndAmountDtl.get("amountSanctionedDate"),dateAndAmountDtl.get("limitFundBasedSanctionedDate"),dateAndAmountDtl.get("limitNonFundBasedSanctionedDate"))==false)
											{
												resultFlag=false;
											}
									}
								}
							}
							catch(Exception e)
							{
								e.printStackTrace();
							}
						}
					}
				
					
				}
			}
			}
			catch(Exception e)
			{
				LogClass.StepWritterConnIssue("Exception in sanctionDateAmountValidationAll "+e.getMessage());
				 LogClass.writeExceptionOnFile(e);
			
				e.printStackTrace();
				
			}
			return resultFlag;
		}
		
		
		public static boolean verifyBankTypeAndApproveLoanAmount(String loanType,String creditGuaranteed,String creditFundBased,String creditNonFundBased,String bankId,HashMap basedOnCondition,String classificationofMLI)
		{
			//System.out.println("verifyBankTypeAndApproveLoanAmount  "+validateNumber(creditFundBased) );
			//System.out.println("verifyBankTypeAndApproveLoanAmount  "+validateNumber(creditNonFundBased) );
			LogClass.StepWritterConnIssue("verifyBankTypeAndApproveLoanAmount loanType"+loanType+"creditGuaranteed"+creditGuaranteed);
			boolean resultFlag=true;
			boolean validationFlag=false;
			double totalAmount=0.0;
			double existingAmountTC=0.0;
			try
			{
				if(loanType.equalsIgnoreCase("tc") && validateNumber(creditGuaranteed))
				{
							
						if(basedOnCondition.size()>0)
						{
						//	System.out.println("sanctionDateAmountValidationAll 222=");
							if(basedOnCondition.get("none")!=null) // cgbid cgpan
							{
									if(basedOnCondition.get("previouslyCovered").equals("Y"))
									{
										//System.out.println("fetchOldGauranteeAmount 9");
										existingAmountTC=objAdminDAO.fetchOldGauranteeAmount((String)basedOnCondition.get("none"),(String)basedOnCondition.get("unitValue"));
									}	
								//	System.out.println("sanctionDateAmountValidationAll existingAmountTC="+existingAmountTC);
								//	System.out.println("sanctionDateAmountValidationAll creditGuaranteed="+creditGuaranteed);
									totalAmount=existingAmountTC+Double.parseDouble(creditGuaranteed);
									validationFlag=true;
							}
						}
						else
						{
							totalAmount=existingAmountTC+Double.parseDouble(creditGuaranteed);
							validationFlag=true;
						}
					
					 
				}
				else if(loanType.equalsIgnoreCase("wc") && validateNumber(creditFundBased)  && validateNumber(creditNonFundBased))
				{
					if(basedOnCondition.size()>0)
					{
						//System.out.println("sanctionDateAmountValidationAll 222=");
						if(basedOnCondition.get("none")!=null) // cgbid cgpan
						{
								if(basedOnCondition.get("previouslyCovered").equals("Y"))
										{
									//System.out.println("fetchOldGauranteeAmount 10");
									existingAmountTC=objAdminDAO.fetchOldGauranteeAmount((String)basedOnCondition.get("none"),(String)basedOnCondition.get("unitValue"));
										}	
								
					 totalAmount=existingAmountTC+Double.parseDouble(creditFundBased)+Double.parseDouble(creditNonFundBased);
					// System.out.println("verifyBankTypeAndApproveLoanAmount wc totalAmount "+totalAmount );
					 validationFlag=true;
						}
					}
					else
					{
						 totalAmount=existingAmountTC+Double.parseDouble(creditFundBased)+Double.parseDouble(creditNonFundBased);
						// System.out.println("verifyBankTypeAndApproveLoanAmount wc totalAmount "+totalAmount );
						 validationFlag=true;
					}
				}
				else
				{
					//System.out.println("verifyBankTypeAndApproveLoanAmount else  " );
					if(validateNumber(creditGuaranteed) && validateNumber(creditFundBased) && validateNumber(creditNonFundBased))
					{
						if(basedOnCondition.size()>0)
						{
						//	System.out.println("sanctionDateAmountValidationAll 222=");
							if(basedOnCondition.get("none")!=null) // cgbid cgpan
							{
									if(basedOnCondition.get("previouslyCovered").equals("Y"))
											{
									//	System.out.println("fetchOldGauranteeAmount 11");
										existingAmountTC=objAdminDAO.fetchOldGauranteeAmount((String)basedOnCondition.get("none"),(String)basedOnCondition.get("unitValue"));
											}	
							
						 totalAmount=existingAmountTC+Double.parseDouble(creditGuaranteed)+Double.parseDouble(creditFundBased)+Double.parseDouble(creditNonFundBased);
						// System.out.println("sanctionDateAmountValidationAll other totalAmount="+totalAmount);
						 validationFlag=true;
							}
						}
						else
						{
							 totalAmount=existingAmountTC+Double.parseDouble(creditFundBased)+Double.parseDouble(creditNonFundBased);
						//	 System.out.println("verifyBankTypeAndApproveLoanAmount wc totalAmount "+totalAmount );
							 validationFlag=true;
						}
						
					}
				}
				
				if(validationFlag==true)
				{
					
					//	System.out.println("bankId  "+bankId );
					// String classificationofMLI = objApplicationDAO.getClassificationMLI(bankId);
				//	 System.out.println("classificationofMLI  "+classificationofMLI );
				//	 System.out.println("classificationofMLI  totalAmount"+totalAmount );
					 double rrbValue = 5000000.0D;
					 double fIValue = 10000000.0D;
					 if(classificationofMLI!=null)
					 {
					 if (((classificationofMLI.equals("RRB")) || (classificationofMLI == "RRB")) && 
						      (totalAmount > rrbValue))
					  {			
						// System.out.println("classificationofMLI RRB  totalAmount"+totalAmount );
						  resultFlag=false;
					  }
					  
					  else if (((classificationofMLI.equals("FI")) || (classificationofMLI == "FI")))
					  {			      
						  if(bankId.equals("0036"))
						  {
							  fIValue = maxApprovedAmt;
							  
						  }
						if((totalAmount > fIValue))
						{
							resultFlag=false;
						}
					  }
					  else
					  {
						//  if((totalAmount > fIValue))
							{
						//		resultFlag=false;
							}
					  }
					 }
				}
			}
			catch(Exception e)
			{
				 LogClass.StepWritterConnIssue("Exception in verifyBankTypeAndApproveLoanAmount "+e.getMessage());
				 LogClass.writeExceptionOnFile(e);
				e.printStackTrace();
				resultFlag=false;
			}
			return resultFlag;
			
		}
		
		
		

}
