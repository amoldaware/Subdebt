package com.cgtsi.application;

import java.text.ParsePosition;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.commons.validator.GenericValidator;
import org.apache.poi.hssf.usermodel.HSSFCell;

import com.cgtsi.admin.Administrator;
import com.cgtsi.admin.ParameterMaster;
import com.cgtsi.admin.User;
import com.cgtsi.registration.Registration;
import com.cgtsi.util.DateHelper;



public class ExcelModuleMethods {

	 SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
	 
	 
	
	public String chkNameValidation(String value , String fieldName, boolean flag , int indexNo , String mandatoryFor)
	{
		String message="";
		/*if(mandatoryFor.equalsIgnoreCase("BANK_ACC_NO") && chkStringEmptyOrNot(value)== true){
			message=fieldName+ " is Require ";
		}
		if(mandatoryFor.equalsIgnoreCase("STATE_CODE") && chkStringEmptyOrNot(value)== true){
			message=fieldName+ " is Require ";
		}
		if(chkStringEmptyOrNot(value)== true){
			message=fieldName+ " is Require ";
		}*/
		if((value!=null && chkStringEmptyOrNot(value)) && flag==true)
		{	
			
			
			if(mandatoryFor.equalsIgnoreCase("BOTH"))
			{
				message=fieldName+ " is Require ";
			}
			
			if(mandatoryFor.equalsIgnoreCase("BOTH"))
			{
				message=fieldName+ " is Require ";
			}
			else if(mandatoryFor.equalsIgnoreCase("TC"))
			{
				
				if(!(fieldName.equalsIgnoreCase("WC_PLR_TYPE")))
				{
					message=fieldName+ " is Require ";
				}
			}
			else
			{
				message=fieldName+ " is Require ";
				if(fieldName.equalsIgnoreCase("TL_BASR_RATE_TYPE"))
				{
					message="";
				}				
			}
		}
		else
		{
				int maxLength=0;
				if(indexNo==7)
				{
					maxLength=100;
				}
				if(indexNo==8)
				{
					maxLength=500;
				}
				if(indexNo==14)
				{
					maxLength=25;
				}
				if(indexNo==17)
				{
					maxLength=50;
				}
				if(indexNo==22)
				{
					maxLength=20;
				}
				if(indexNo==53)
				{
					maxLength=15;
				}
				if(indexNo==61)
				{
					maxLength=7;
				}
				if(indexNo==64)
				{
					maxLength=10;
				}
				if(indexNo==118)
				{
					maxLength=4000;
				}
				if(indexNo==121)
				{
					maxLength=100;
				}
				if(indexNo==122)
				{
					maxLength=100;
				}
				
				
				
						if(chkLength(value,maxLength))
						{
							if(!fieldName.equalsIgnoreCase("SSI ADDRESS"))
							{
								if(!fieldName.equalsIgnoreCase("TYPE_OF_ACTIVITY"))
								{		
									if(!fieldName.equalsIgnoreCase("SSI NAME"))
									{
										if (!chkSpecialCharFromString(value))
										{
											message="In "+fieldName+" special character is not allowed ";
										}	
									}
								}
							}
						}
						/*else
						{								
							message="Invalid "+fieldName+" , "+fieldName+" char length should not be greater than "+maxLength+" char";					
						}	*/	
			
		}
		
		return message;
	}
	

	public String chkFixNumberValidation(String value , String fieldName, boolean flag , int indexNo ,String mandatoryFor)
	{
		String message="";		
		
		if((value!=null && chkStringEmptyOrNot(value)) && flag==true)
		{
			
				if(mandatoryFor.equalsIgnoreCase("BOTH"))
				{
					message=fieldName+ " is Require ";
				}
				else if(mandatoryFor.equalsIgnoreCase("TC"))
				{				
					if(!(fieldName.equalsIgnoreCase("WC_FB_LIMIT_SANCTIONED") || fieldName.equalsIgnoreCase("WC_FB_CREDIT_TO_GUARANTEE") || fieldName.equalsIgnoreCase("WC_NFB_LIMIT_SANCTIONED") || fieldName.equalsIgnoreCase("WC_NFB_CREDIT_TO_GUARANTEE")))
					{				
						message=fieldName+ " is Require ";
					}
				}
				else
				{					
					if(!(fieldName.equalsIgnoreCase("TL_SANCTIONED AMOUNT") || fieldName.equalsIgnoreCase("TL_CREDIT_TO_GUARANTEE") || fieldName.equalsIgnoreCase("TL_PRINCIPAL_MORATARIUM") || fieldName.equalsIgnoreCase("TL_TENURE") || fieldName.equalsIgnoreCase("NO_OF_INSTALLMENTS")))
					{
						message=fieldName+ " is Require ";
					}					
				}		
		}
		else
		{
			//System.out.println("in else part "+fieldName);
			if(value!=null)
			{
				int maxCounter=0;
				
				if(indexNo==18)
				{
					maxCounter=500;
				}
				if(indexNo==19)
				{
					maxCounter=2000000000;					
				}
				if(indexNo==66)
				{
					maxCounter=10000000;
				}
				if(indexNo==79)
				{
					maxCounter=120;
				}
				if(indexNo==80)
				{
					maxCounter=30;
				}
				if(indexNo==83)
				{
					maxCounter=120;
				}
				if(indexNo==67)
				{
					maxCounter=25000000;
				}

				boolean chkFlag=true;
				int intValue=0;
				value=value.trim();
				try
				{
					if(!(fieldName.equalsIgnoreCase("NO_OF_EMPL")))
					{
						value=value.replace(".", "");
					}
					intValue=Integer.parseInt(value);
				}
				catch(Exception e)
				{	
					
					if(!chkStringEmptyOrNot(value))
					{
				//		System.out.println("in else");
						chkFlag=false;
						message=fieldName+ " is Invalid ";
					}
				}
				if(!(chkFlag==true &&  intValue <=maxCounter))
				{					
					message=fieldName+ " is Invalid ";
				}
			}
		}
		//System.out.println(fieldName+" Exception fieldName "+message);
		return message;
	}
	
	public String VerifyAppRefNumber(String value)
	{	
		String message="";		
		if(value!=null && ( value.trim().length() >=10 && (value.trim().length() <=16) ))
		{
			if(!chkNumericField(value))
			{
				message=" Invalid Application Referance No.[It Should not contains alphabet or special Char ]";
			}
		}
		else if(chkStringEmptyOrNot(value))
		{
			message="Application Referance No. Required";
		}
		else
		{
			message=" Invalid Application Referance No.[It should be greater than 10 char and less than 16 char]";
		}			
		return message;
	}
	
	public String VerifyBranchName(String value)
	{		
		String message="";		
		if(value!=null && ( value.trim().length() > 0 && (value.trim().length() <= 100))  )
		{
			if(!chkSpecialCharFromString(value))
			{
				message="Branch Name should not contains special charater";
			}
		}
		else if(chkStringEmptyOrNot(value))
		{
			message="Branch Name Required";
		}
		else
		{
			message="Invalid Branch Name ";
		}
		return message;
	}
	
	public String VerifyBranchCode(String value)
	{
		String message="";		
		if(value!=null && ( value.trim().length() > 0 && (value.trim().length()<=10)))
		{
			if(!chkSpecialCharFromString(value))
			{
				message="Branch code should not contains special charater";
			}
		}
		else if(!chkStringEmptyOrNot(value))
		{
			message="Invalid Branch Code[branch code should not greater than 10 char]";
		}
		return message;
	}
	public boolean chkLength(String value , int expectedlength)
	{
		boolean flag= false;
		if(value!=null && value.trim().length() <= expectedlength)
		{
			flag= true;
		}
		return flag;
	}
	public boolean chkStringEmptyOrNot(String value)
	{
		boolean flag= false;
		if(value!=null && value.trim().length()==0)
		{
			flag= true;
		}
		return flag;
	}
	public boolean chkNumericField(String value)
	{		
		boolean flag= false;
		  String REGEX = "((-|\\+)?[0-9]+(\\.[0-9]+)?)+";  
			Pattern pattern = Pattern.compile(REGEX);

			Matcher matcher = pattern.matcher(value);
 
			if (matcher.matches()) {
				 flag=true;
				  }
		return flag;
	}
	public boolean chkSpecialCharFromString(String value)
	{
		boolean flag = false;
		String REGEX = "[^&%$#@!~^]*";		 
		 Pattern pattern = Pattern.compile(REGEX);
		 Matcher matcher = pattern.matcher(value);
		 if (matcher.matches()) {
			 flag=true;
		 }
		 else
		 {
			 flag=false;
		 }
		 return flag;
	}
	
	public String panCardValidation(String panValue,String filedValue,boolean flag)
    {
		System.out.println("panCardValidation "+panValue);
		String message="";
		if(panValue!=null)
		{
			panValue=panValue.trim();
			panValue=panValue.toUpperCase();
			if(chkStringEmptyOrNot(panValue) && flag==true)
			{
				message=filedValue+" is Required";
			}
			else if(!chkStringEmptyOrNot(panValue))
			{
				String REGEX = "[A-Z]{5}\\d{4}[A-Z]{1}";  
				Pattern pattern = Pattern.compile(REGEX);
				Matcher matcher = pattern.matcher(panValue);
				if(matcher.matches()==false)
				{
					message=filedValue+" is Invalid";
				}
				
			}
		}
		System.out.println("panCardValidation "+message);
		return message;
	}
	
	public String dateValidation(String value,String filedValue,boolean flag,String mandatoryFor)
	{
		String message="";
		
		if(value!=null && !chkStringEmptyOrNot(value))
		{
			value=value.trim();
			Date date=null;	
			try
			{
				SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
				sdf.setLenient(false);
				date = sdf.parse(value);	
			}
			catch(Exception e)
			{
			
				message=filedValue+" is Invalid";			
			}
		}
		else if(flag==true && chkStringEmptyOrNot(value))
		{			
			if(mandatoryFor.equalsIgnoreCase("BOTH") )
			{
				message=filedValue+ " is Require ";
			}
			else if(mandatoryFor.equalsIgnoreCase("TC"))
			{				
					message=filedValue+ " is Require ";
					if((filedValue.equalsIgnoreCase("WC_NFB_LIMIT_SANCTIONED_DT")))
					{
						message="";
					}
			}
			else
			{
				message=filedValue+ " is Require ";
				if((filedValue.equalsIgnoreCase("TL_SANCTION_DATE")  || filedValue.equalsIgnoreCase("LOAN TERMINATION DATE")))
				{
					message="";
				}
				
			}
		}
		
		return message;
	}
	
	public String chkPersentNumberValidation(String value , String fieldName, boolean flag  ,String mandatoryFor)
	{
		String message="";
		if((value!=null && chkStringEmptyOrNot(value)) && flag==true)
		{		
				if(mandatoryFor.equalsIgnoreCase("BOTH"))
				{
					message=fieldName+ " is Require ";
				}
				else if(mandatoryFor.equalsIgnoreCase("TC"))
				{
					if(!(fieldName.equalsIgnoreCase("WC_PLR")))
					{
						message=fieldName+ " is Require ";
					}
				}
				else
				{
					message=fieldName+ " is Require ";
					if(fieldName.equalsIgnoreCase("TL_INTEREST_RATE") || fieldName.equalsIgnoreCase("TL_BASR_RATE"))
					{
						message="";
					}
				}
		}
		else
		{
			
			if(value!=null)
			{	
				value=value.trim();
				boolean chkFlag=true;
				double intValue=0;
				try
				{				
					intValue=Double.parseDouble(value);
				}
				catch(Exception e)
				{		
					
					if(!chkStringEmptyOrNot(value))
					{				
						chkFlag=false;
						message=fieldName+ " is Invalid ";
					}
				}
				if((chkFlag==true))
				{
					if(intValue > 99.99)
					{
						message=fieldName+ " is Invalid ";
					}
				}
			}
		}		
		return message;
	}
	
	public String chkProjectSalesAndExport(String value , String fieldName, boolean flag , int indexNo ,String mandatoryFor)
	{
		String message="";
		if((value!=null && chkStringEmptyOrNot(value)) && flag==true)
		{
			message=fieldName+" is Required. ";
		}
		else
		{
			if(value!=null)
			{				
				value=value.trim();
				if(value.length() > 15)
				{
					message=fieldName+" is Invalid. ";
				}
				else
				{
					if(!chkNumericField(value) && !chkStringEmptyOrNot(value))
					{
						message=fieldName+" is Invalid. ";
					}
				}
			}
		}
		return message;
	}
	
	public String CutPersentValue(String value)
	{		
		String message="";
		if(value!=null && value.trim().length() != 5)
		{
			if(value!=null && value.trim().length() > 4)
			{			
				message=value.substring(0, 4);
			}
			else if (value.trim().length() <= 4)
			{
				message=value;
			}
		}
		else
		{
			message=value;
		}
		return message;
	}
	
	public String validateTC(String app_loan_type, int intApp_tl_sanction_amt,
			int intApp_tl_credit_amt, String app_tl_sanction_dt, 
			  int tenure, int principal_mora,
			String plr_type, double plr , String memberID) {
		boolean isValid = true;
		String message="";
		try
		{
		int count = 0;
				
		//System.out.println("app_loan_type "+app_loan_type+" intApp_tl_sanction_amt "+intApp_tl_sanction_amt+" intApp_tl_credit_amt "+intApp_tl_credit_amt+" app_tl_sanction_dt"+app_tl_sanction_dt+" tenure "+tenure+" principal_mora "+principal_mora+" plr_type "+plr_type+" plr "+plr);
		
		if ("TC".equalsIgnoreCase(app_loan_type)) 
		{			
			if (intApp_tl_sanction_amt < 999 || intApp_tl_credit_amt < 999) 
			{			
				count++;
			//	System.out.println("1st if");
				message="TL sanction amount and TL credit amount values should be greater than 999.";
			}		
			if (tenure < 12 || tenure > 120) 
			{			
				count++;			
				//System.out.println("2nd if");
				message="Tenure should be greater than or equal to 12 and less than or equal to 120.";
			}		
			if (plr_type == null || plr < 0) 
			{			
				count++;
				//System.out.println("3rd if");
				message="PLR type should not be zero or null.";
			}
			
			if ((intApp_tl_sanction_amt < intApp_tl_credit_amt)) 
			{			
				count++;
				//System.out.println("4th if");
				message="TL sanction amount should not be greater than TL credit amount.";
			}
			
			if (app_tl_sanction_dt != null) 
			{
				if (chkDateIsLessOrNot(app_tl_sanction_dt ,  memberID)==true) 
				{	
					if (!validateSanctionedDates(app_tl_sanction_dt, memberID)) 
					{				
						//System.out.println("5th if");
						message="TL sanction date should not be prior to last 2 quarter.";
						count++;
					}
				}
				else
				{
					message="TL sanction date should not be future date.";
					//System.out.println("TL sanction date should not be future date");
					count++;
				}
			}
		
			if (principal_mora < 0 || principal_mora > 30) 
			{
				//System.out.println("6th if");
				message="Principle moratium value should be greater than zero and less than or equal to 30.";
				count++;
			}
		}
		
		if (intApp_tl_sanction_amt > 0 && intApp_tl_sanction_amt < 999) {		
			
			//System.out.println("7th if");
			message="TL sanction amount should be greater than 999.";
			count++;
		}
	
		if (count > 0) {
			isValid = false;
		}	
		}
		catch(Exception e)
		{
			isValid=false;
		}
		return message;
	}
	
	public String validateWC(String app_loan_type,
			int intApp_wc_fb_sanction_amt, int app_wc_nfb_sanction_amt,
			int intApp_wc_fb_credit_amt, int app_wc_nfb_credit_amt,
			String app_wc_fb_sanction_dt, String app_wc_nfb_sanction_dt,
			double wc_fb_interest, double wc_nfb_commission,			
		 String plr_type, double plr , String memberID) {
		boolean isValid = true;
		int count = 0;
		String message="";
		
		Date currentDt = new Date();
		if ("WC".equalsIgnoreCase(app_loan_type)) {
	
			if (plr_type == null || plr < 0)
			{				
				count++;
				message="PLR type should not be zero or null.";
			}
			
			if (intApp_wc_fb_sanction_amt < 999	|| intApp_wc_fb_credit_amt < 999) {				
				count++;
				message="WC FB sanction amount and WC FB credit amount values should be greater than 999.";
			}
			
			if (app_wc_nfb_sanction_amt > 0	&& (app_wc_nfb_credit_amt < 0 || app_wc_nfb_sanction_amt < app_wc_nfb_credit_amt))
			{	
				message="WC NFB sanction amount should not be less than WC NFB credit amount.";
				count++;
			}
			
			if (intApp_wc_fb_sanction_amt > 0 && (wc_fb_interest < 0 || app_wc_fb_sanction_dt == null))	
			{			
				message="WC FB Interest should be greater than zero.";
				count++;
			}
			
			if (app_wc_nfb_sanction_amt > 0	&& (wc_nfb_commission < 0 || app_wc_nfb_sanction_dt == null))		
			{			
				message="WC NFB Commission should be greater than zero.";
				count++;
			}
			
				
			if (intApp_wc_fb_sanction_amt < intApp_wc_fb_credit_amt) 
			{		
				message="WC FB sanction amount should not be less than WC FB credit amount.";
				count++;
			}
			
			if (app_wc_nfb_sanction_amt < app_wc_nfb_credit_amt) 
			{	
				message="WC NFB sanction amount should not be less than WC NFB credit amount.";
				count++;
			}
			
		if (app_wc_fb_sanction_dt != null && app_wc_nfb_sanction_dt != null ) {			
			
			if(!app_wc_fb_sanction_dt.equals(""))
			{
				if (intApp_wc_fb_sanction_amt > 0 && (!chkDateIsLessOrNot(app_wc_fb_sanction_dt , memberID)))
				{	
					//System.out.println("WC FB sanction date should not be future date");
					message="WC FB sanction date should not be future date.";
					count++;
				}
			
			}
			if(!app_wc_nfb_sanction_dt.trim().equals(""))
			{
				if(!validateFromLastQuarterDate(app_wc_nfb_sanction_dt, memberID))
				{		
					message="WC NFB sanction date should not be prior to last 2 quarter.";
					count++;
				}
			}
			System.out.println("WC NFB sanction date should not be future date validateFromCurrentDate "+validateFromCurrentDate(app_wc_nfb_sanction_dt , memberID));
				if ((app_wc_nfb_sanction_amt > 0 && !validateFromCurrentDate(app_wc_nfb_sanction_dt , memberID))) 
				{	
				//	System.out.println("WC NFB sanction date should not be future date");
					message="WC NFB sanction date should not be future date.";
					count++;
				}
				if ((app_wc_nfb_sanction_amt > 0 && !validateFromLastQuarterDate(app_wc_nfb_sanction_dt, memberID))) 
				{			
					message="WC NFB sanction date should not be prior to last 2 quarter.";
					count++;
				}
			}
		}

		if (intApp_wc_fb_sanction_amt > 0 && intApp_wc_fb_sanction_amt < 999) {
			
			message="WC FB sanction amount should not be less than 999.";
			count++;
		}
		
		if (count > 0) {
			isValid = false;
		}

		return message;
	}
	
	public boolean validateFromCurrentDate(String inputDate , String memberID) {
		boolean result = false;
		SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy");	
		System.out.println("validateFromCurrentDate inputDate "+inputDate);
		if(inputDate!=null)
		{
			try
			{
				//if(!memberID.equals("001500030000"))
				{
					
								
						Date date = formatter.parse(inputDate);
						String pattern = "dd/MM/yyyy";
						String dateInString =new SimpleDateFormat(pattern).format(new Date());
						Date CurrentDate = formatter.parse(dateInString);
						
						if (CurrentDate.equals(date)) {
							result = true;
						}
						System.out.println("validateFromCurrentDate result "+result);
					
				}
				//else
				{
					//result=dateValidationOnBetweenBOI(formatter.parse(inputDate));
				}
			}
			catch(Exception e)
			{
				System.out.println("validateFromCurrentDate Exception "+e.getMessage());
				result = false;
			}
		}
		return result;
	}
	
	public boolean chkDateIsLessOrNot(String inputDate  , String memberID) {
		boolean result = false;
		SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy");	
		try
		{
			if(inputDate!=null)
			{
			//	if(!memberID.equals("001500030000"))
				{											
							Date date = formatter.parse(inputDate);
							String pattern = "dd/MM/yyyy";
							String dateInString =new SimpleDateFormat(pattern).format(new Date());
							Date CurrentDate = formatter.parse(dateInString);
							
							if (CurrentDate.compareTo(date)==-1) {
								result = false;
							}
							else
							{
								result = true;
							}		
				}
				//else
				{
				//	result=dateValidationOnBetweenBOI(formatter.parse(inputDate));
				}
			}
		}
		catch(Exception e)
		{
			result = false;
		}
		return result;
	}
	
	
	public boolean validateFromLastQuarterDate(String inputDate ,String memberID) {
		boolean isValid = false;
		SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy");
		
		if(inputDate!=null)
		{
			try
			{
				//if(!memberID.equals("001500030000"))
				{
					
				
					Date date = formatter.parse(inputDate);
					Calendar today_cal = Calendar.getInstance();
					Calendar input_cal = Calendar.getInstance();
					Date today = new Date();
					today_cal.setTime(today);
					int current_month = today_cal.get(Calendar.MONTH);
					input_cal.setTime(date);
					int input_month = input_cal.get(Calendar.MONTH);
			
					if (((current_month >= 0 && current_month <= 2) && ((input_month >= 0 && input_month <= 2) || (input_month >= 9 && input_month <= 11)))) {
						isValid = true;
					}
					if (((current_month >= 3 && current_month <= 5) && ((input_month >= 3 && input_month <= 5) || (input_month >= 0 && input_month <= 2)))) {
						isValid = true;
					}
					if (((current_month >= 9 && current_month <= 11) && ((input_month >= 9 && input_month <= 11) || (input_month >= 6 && input_month <= 8)))) {
						isValid = true;
					}
				}
				//else
				{
					//isValid=dateValidationOnBetweenBOI(formatter.parse(inputDate));
				}
			}
			catch(Exception e)
			{
				isValid = false;			
			}
		}
		return isValid;
	}
	
	//DKR Interface upload
	public ArrayList assignExcelDataToObjects(String []recordInfo,User user)
	{
		ArrayList lstAssignedData = new ArrayList();
		try
		{
			if(recordInfo!=null && recordInfo.length==122)
			{
				
				Application app = new Application();
				Registration registration = new Registration();
				BorrowerDetails bd = new BorrowerDetails();
				SSIDetails sd = new SSIDetails();
				PrimarySecurityDetails ps = new PrimarySecurityDetails();
				TermLoan tl = new TermLoan();
				WorkingCapital wc = new WorkingCapital();
				ProjectOutlayDetails pd = new ProjectOutlayDetails();
				Securitization sec = new Securitization();
				
				
					sd.setConstitution(recordInfo[5]);// app.getBorrowerDetails().getSsiDetails().setConstitution("TESTCONST");
					sd.setSsiType(recordInfo[6]);// app.getBorrowerDetails().getSsiDetails().setSsiType("TEST");//
					sd.setSsiName(recordInfo[7]);// app.getBorrowerDetails().getSsiDetails().setSsiName("TESTUNIT");
					sd.setAddress(recordInfo[8]);// app.getBorrowerDetails().getSsiDetails().setAddress("UNIT@MUMBAI");
					sd.setState(recordInfo[9]);// app.getBorrowerDetails().getSsiDetails().setState("MH");
					sd.setDistrict(recordInfo[10]);// app.getBorrowerDetails().getSsiDetails().setDistrict("MUMBAI");
					sd.setCity(recordInfo[11]);// app.getBorrowerDetails().getSsiDetails().setCity("MUMBAI");
					sd.setPincode(recordInfo[12]);// app.getBorrowerDetails().getSsiDetails().setPincode("400051");
					sd.setSsiITPan(recordInfo[13]);
					sd.setRegNo(recordInfo[14]);
					sd.setIndustryNature(recordInfo[15]);// app.getBorrowerDetails().getSsiDetails().setIndustryNature("IT SERVICES");
					sd.setIndustrySector(recordInfo[16]);// app.getBorrowerDetails().getSsiDetails().setIndustrySector("BPO");
					sd.setActivityType(recordInfo[17]);// app.getBorrowerDetails().getSsiDetails().setActivityType("TESTING");
					sd.setEmployeeNos(ConvertStrToInt(recordInfo[18]));// app.getBorrowerDetails().getSsiDetails().setEmployeeNos(10);
					sd.setProjectedSalesTurnover(ConvertStrToDouble(recordInfo[19]));// app.getBorrowerDetails().getSsiDetails().setProjectedSalesTurnover(1000);
					sd.setProjectedExports(ConvertStrToDouble(recordInfo[20]));// app.getBorrowerDetails().getSsiDetails().setProjectedExports(1000);
					sd.setCpTitle(recordInfo[21]);// app.getBorrowerDetails().getSsiDetails().setCpTitle("MR");
					sd.setCpFirstName(recordInfo[22]);// app.getBorrowerDetails().getSsiDetails().setCpFirstName("TEST");
					sd.setCpMiddleName(recordInfo[23]);// app.getBorrowerDetails().getSsiDetails().setCpMiddleName("FILE");
					sd.setCpLastName(recordInfo[24]);// app.getBorrowerDetails().getSsiDetails().setCpLastName("UPLOAD");
					sd.setCpParTitle(recordInfo[25]);// app.getBorrowerDetails().getSsiDetails().setCpParTitle("MR");
					sd.setCpParFirstName(recordInfo[26]);// app.getBorrowerDetails().getSsiDetails().setCpParFirstName("FILE");
					sd.setCpParMiddleName(recordInfo[27]);// app.getBorrowerDetails().getSsiDetails().setCpParMiddleName("UPLOAD");
					sd.setCpParLastName(recordInfo[28]);// app.getBorrowerDetails().getSsiDetails().setCpParLastName("TEST");
					sd.setCpGender(recordInfo[29]);// app.getBorrowerDetails().getSsiDetails().setCpGender("M");
					sd.setCpITPAN(recordInfo[30]);// app.getBorrowerDetails().getSsiDetails().setCpITPAN("TEST101");
					sd.setReligion(recordInfo[31]);// app.getBorrowerDetails().getSsiDetails().setReligion("N");//DOUBT
					sd.setCpDOB(ConvertStrToDate(recordInfo[32])); // app.getBorrwerDetails().getSsiDetails().setCpDOB(new
					sd.setCpLegalID(recordInfo[33]);// app.getBorrowerDetails().getSsiDetails().setCpLegalID("L1");
					sd.setCpLegalIdValue(recordInfo[34]); // app.getBorrowerDetails().getSsiDetails().setCpLegalIdValue("11");
					sd.setFirstName(recordInfo[35]);// app.getBorrowerDetails().getSsiDetails().setFirstName("F1");
					sd.setFirstDOB(ConvertStrToDate(recordInfo[36]));// app.getBorrowerDetails().getSsiDetails().setFirstDOB(new
					sd.setFirstItpan(recordInfo[37]);// app.getBorrowerDetails().getSsiDetails().setFirstItpan("I1");
					sd.setSecondName(recordInfo[38]);// app.getBorrowerDetails().getSsiDetails().setSecondName("F2");
					sd.setSecondDOB(ConvertStrToDate(recordInfo[39]));// app.getBorrowerDetails().getSsiDetails().setSecondDOB(new
					sd.setSecondItpan(recordInfo[40]);// app.getBorrowerDetails().getSsiDetails().setSecondItpan("I2");
					sd.setThirdName(recordInfo[41]);// app.getBorrowerDetails().getSsiDetails().setThirdName("F3");
					sd.setThirdDOB(ConvertStrToDate(recordInfo[42]));// app.getBorrowerDetails().getSsiDetails().setThirdDOB(new
					sd.setThirdItpan(recordInfo[43]);// app.getBorrowerDetails().getSsiDetails().setThirdItpan("I3");
					sd.setWomenOperated(recordInfo[45]);// app.getBorrowerDetails().getSsiDetails().setWomenOperated("N");
					sd.setMSE(recordInfo[46]);// app.getBorrowerDetails().getSsiDetails().setMSE("N");
					sd.setEnterprise(recordInfo[47]);// app.getBorrowerDetails().getSsiDetails().setEnterprise("N");
					sd.setPhysicallyHandicapped(recordInfo[54]);// app.getBorro
					sd.setConditionAccepted(recordInfo[119]);// app.getBorrowerDetails().getSsiDetails().setConditionAccepted("");//NOT
				
					bd.setPreviouslyCovered(recordInfo[4]);					
					bd.setSsiDetails(sd);
					
					sec.setSpreadOverPLR(ConvertStrToDouble(recordInfo[110]));// app.getSecuritization().setSpreadOverPLR(0);
					sec.setPplRepaymentInEqual(recordInfo[111]);// app.getSecuritization().setPplRepaymentInEqual(null);
					sec.setTangibleNetWorth(ConvertStrToDouble(recordInfo[112]));// app.getSecuritization().setTangibleNetWorth(0);
					sec.setFixedACR(ConvertStrToDouble(recordInfo[113]));
					sec.setCurrentRatio(ConvertStrToDouble(recordInfo[114]));// app.getSecuritization().setCurrentRatio(0);
					sec.setMinimumDSCR(ConvertStrToInt(recordInfo[115]));// app.getSecuritization().setMinimumDSCR(0);
					sec.setAvgDSCR(ConvertStrToInt(recordInfo[116]));// app.getSecuritization().setAvgDSCR(0);
					
					
					pd.setCollateralSecurityTaken(recordInfo[48]);// app.getProjectOutlayDetails().setCollateralSecurityTaken("N");
					pd.setThirdPartyGuaranteeTaken(recordInfo[49]);
					pd.setTcPromoterContribution(ConvertStrToDouble(recordInfo[67]));// app.getProjectOutlayDetails().setTcPromoterContribution(10);
					pd.setTcSubsidyOrEquity(ConvertStrToDouble(recordInfo[68]));// app.getProjectOutlayDetails().setTcSubsidyOrEquity(10);
					pd.setTcOthers(ConvertStrToDouble(recordInfo[69]));//
					pd.setWcPromoterContribution(ConvertStrToInt(recordInfo[92]));// app.getProjectOutlayDetails().setWcPromoterContribution(10);
					pd.setWcSubsidyOrEquity(ConvertStrToInt(recordInfo[93]));// app.getProjectOutlayDetails().setWcSubsidyOrEquity(10);
					pd.setWcOthers(ConvertStrToInt(recordInfo[94]));// app.getProjectOutlayDetails().setWcOthers(10);
					
					double project_cost = ConvertStrToInt(recordInfo[66])
					+ ConvertStrToInt(recordInfo[67]) + ConvertStrToInt(recordInfo[68]) + ConvertStrToInt(recordInfo[69]);
					pd.setProjectCost(project_cost);// app.getProjectOutlayDetails().setProjectCost(10);
					
					double wc_assets = ConvertStrToInt(recordInfo[87])
					+ ConvertStrToInt(recordInfo[89]) + ConvertStrToInt(recordInfo[92])
					+ ConvertStrToInt(recordInfo[93]) + ConvertStrToInt(recordInfo[94]);
					
					pd.setWcAssessed(wc_assets);// app.getProjectOutlayDetails().setWcAssessed(10);
					pd.setProjectOutlay((project_cost + wc_assets));// app.getProjectOutlayDetails().setProjectOutlay(100);
					pd.setIsPrimarySecurity(recordInfo[117]);
					pd.setPrimarySecurityDetails(ps);
					
					
					tl.setAmountSanctioned(ConvertStrToInt(recordInfo[66]));// app.getTermLoan().setAmountSanctioned(1000);
					tl.setAmountSanctionedDate(ConvertStrToDate(recordInfo[70]));// app.getTermLoan().setAmountSanctionedDate(new
					tl.setCreditGuaranteed(ConvertStrToInt(recordInfo[71]));// app.getTermLoan().setCreditGuaranteed(1000);
					tl.setAmtDisbursed(ConvertStrToInt(recordInfo[72]));// app.getTermLoan().setAmtDisbursed(0);
					tl.setFirstDisbursementDate(ConvertStrToDate(recordInfo[73]));// app.getTermLoan().setFirstDisbursementDate(new
					tl.setFinalDisbursementDate(ConvertStrToDate(recordInfo[74]));// app.getTermLoan().setFinalDisbursementDate(new
					tl.setTypeOfPLR(recordInfo[75]);// app.getTermLoan().setTypeOfPLR("PL");
					tl.setPlr(ConvertStrToDouble(recordInfo[76]));// app.getTermLoan().setPlr(10);
					tl.setInterestType(recordInfo[77]);// app.getTermLoan().setInterestType("F");
					tl.setInterestRate(ConvertStrToDouble(recordInfo[78]));// app.getTermLoan().setInterestRate(10);
					tl.setTenure(ConvertStrToInt(recordInfo[79]));// app.getTermLoan().setTenure(60);
					tl.setRepaymentMoratorium(ConvertStrToInt(recordInfo[80]));// app.getTermLoan().setRepaymentMoratorium(0);
					tl.setInterestMoratorium(ConvertStrToInt(recordInfo[81]));// app.getTermLoan().setInterestMoratorium(0);
					tl.setPeriodicity(ConvertStrToInt(recordInfo[82]));// app.getTermLoan().setPeriodicity(0);
					tl.setNoOfInstallments(ConvertStrToInt(recordInfo[83]));// app.getTermLoan().setNoOfInstallments(0);
					tl.setFirstInstallmentDueDate(ConvertStrToDate(recordInfo[84]));// app.getTermLoan().setFirstInstallmentDueDate(null);
					tl.setPplOS(ConvertStrToInt(recordInfo[85]));// app.getTermLoan().setPplOS(0);
					tl.setPplOsAsOnDate(ConvertStrToDate(recordInfo[86]));// app.getTermLoan().setPplOsAsOnDate(null);
					
					wc.setFundBasedLimitSanctioned(ConvertStrToInt(recordInfo[87]));// app.getWc().setFundBasedLimitSanctioned(0);
					wc.setCreditFundBased(ConvertStrToInt(recordInfo[88]));// app.getWc().setCreditFundBased(0);
					wc.setNonFundBasedLimitSanctioned(ConvertStrToInt(recordInfo[89]));// app.getWc().setNonFundBasedLimitSanctioned(0);
					wc.setCreditNonFundBased(ConvertStrToInt(recordInfo[90]));// app.getWc().setCreditNonFundBased(0);
					wc.setIsTLMarginMoney(recordInfo[91]);
					wc.setWcTypeOfPLR(recordInfo[95]);// app.getWc().setWcTypeOfPLR("PL");
					wc.setWcPlr(ConvertStrToDouble(recordInfo[96]));// app.getWc().setWcPlr(0);
					wc.setWcInterestType(recordInfo[97]);// app.getWc().setWcInterestType("F");
					wc.setWcInterestRate(ConvertStrToDouble(recordInfo[98]));// app.getWc().setWcInterestRate(10);
					wc.setLimitFundBasedSanctionedDate(ConvertStrToDate(recordInfo[99]));// app.getWc().setLimitFundBasedSanctionedDate(new
					wc.setLimitNonFundBasedCommission(ConvertStrToDouble(recordInfo[100]));// app.getWc().setLimitNonFundBasedCommission(0);
					wc.setLimitNonFundBasedSanctionedDate(ConvertStrToDate(recordInfo[101]));// app.getWc().setLimitNonFundBasedSanctionedDate(new
					wc.setOsFundBasedPpl(ConvertStrToInt(recordInfo[102]));// app.getWc().setOsFundBasedPpl(0);
					wc.setOsFundBasedAsOnDate(ConvertStrToDate(recordInfo[103]));// app.getWc().setOsFundBasedAsOnDate(new
					wc.setOsNonFundBasedPpl(ConvertStrToInt(recordInfo[104]));// app.getWc().setOsNonFundBasedPpl(0);
					wc.setOsNonFundBasedAsOnDate(ConvertStrToDate(recordInfo[105]));// app.getWc().setOsNonFundBasedAsOnDate(new
					wc.setWcDisbAmt(ConvertStrToInt(recordInfo[106]));
					wc.setWcFirstDisbDt(ConvertStrToDate(recordInfo[107]));
					wc.setWcFinalDisbDt(ConvertStrToDate(recordInfo[108]));
				
					app.setBankId(user.getBankId());
					app.setZoneId(user.getZoneId());
					app.setBranchId(user.getBranchId());					
					app.setLoanType(recordInfo[0]);					
					app.setMliRefNo(recordInfo[1]);
					app.setMliBranchName(recordInfo[2]);
					app.setMliBranchCode(recordInfo[3]);
					app.setPrevSSI(recordInfo[44]);// app.setPrevSSI("N");
					app.setDcHandicrafts(recordInfo[50]);// app.setDcHandicrafts("N");
					app.setDcHandicraftsStatus("");// app.setDcHandicraftsStatus("N");
					app.setIcardNo(recordInfo[52]);// app.setIcardNo("I01");
					app.setIcardIssueDate(ConvertStrToDate(recordInfo[53]));// app.setIcardIssueDate(new
					app.setJointFinance(recordInfo[55]);// app.setJointFinance("N");
					app.setJointcgpan(recordInfo[56]);// app.setJointcgpan("");
					app.setDcHandlooms(recordInfo[57]);// app.setDcHandlooms("N");
					app.setWeaverCreditScheme(recordInfo[58]);
					app.setHandloomchk(recordInfo[59]);// app.setHandloomchk("N");
					app.setHandloomSchName(recordInfo[60]);
					app.setInternalRating(recordInfo[61]);// app.setInternalRating("R");
					app.setInternalratingProposal(recordInfo[62]);// app.setInternalratingProposal("IP");
					app.setInvestmentGrade(recordInfo[63]);// app.setInvestmentGrade("IG");
					app.setSubsidyType(recordInfo[64]);
					app.setSubsidyOther(recordInfo[65]);
					app.setAppExpiryDate(ConvertStrToDate(recordInfo[109]));// app.setAppExpiryDate
					app.setRemarks(recordInfo[118]);					
					app.setBorrowerDetails(bd); //pending
					app.setSecuritization(sec); // pending
				
					app.setProjectOutlayDetails(pd); //pending
					app.setTermLoan(tl);
					app.setWc(wc);
					app.setUserId(user.getUserId());
					app.setUdyogAdharNo(recordInfo[120]);
					String bankAccNo = recordInfo[121].trim();
					System.out.println("--------------------->EXLMM........................>>> "+bankAccNo);
					
					app.setBankAcNo(bankAccNo);

					app.setUserId(user.getUserId());
					String stateCodeVal = recordInfo[122].toString();
					app.setStateCode(stateCodeVal.trim());
					if(!stateCodeVal.equals("") || !stateCodeVal.equals(null))
					//gstNo = registration.getGstNo(stateCodes,user.getBankId());
					app.setGstNo(registration.getGstNo(stateCodeVal,user.getBankId()).trim());
					
					System.out.println(app.getGstNo()+"......Deepak..exlmm.....gstNo.....");
					//System.out.println("udyog adhar no"+recordInfo[121]+" bank Acc "+recordInfo[120]);
					lstAssignedData.add(app);

			}
			
		}
		catch(Exception e)
		{
			//e.printStackTrace();
		}
		//System.out.println("lstAssignedData size "+lstAssignedData.size());
		return lstAssignedData;
	}
	
	public Date ConvertStrToDate(String strDate)
	{
		Date date=null;	
		if(strDate!=null)
		{
			try
			{
				SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
				sdf.setLenient(false);
				date = sdf.parse(strDate);	
			}
			catch(Exception e)
			{
				date=null;						
			}
		}
		return date;
	}
	public int ConvertStrToInt(String strInt)
	{
		int no=0;
		if(strInt!=null)
		{
			try
			{
				no=Integer.parseInt(strInt);
			}
			catch(Exception e)
			{
				no=0;						
			}
		}
		return no;
	}
	public double ConvertStrToDouble(String strDouble)
	{
		double no=0.0;
		if(strDouble!=null)
		{
			try
			{
				no=Double.parseDouble(strDouble);
			}
			catch(Exception e)
			{
				no=0.0;						
			}
		}
		return no;
	}
	
	
	public  boolean  validateSanctionedDates(String fromValue , String memberID) {
		
		boolean isValid = true;
		String toValue = null;
		SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
		if(fromValue!=null)
		{
			//System.out.println("validateSanctionedDates "+memberID);
		//	if(!memberID.equals("001500030000"))
			{
				//System.out.println("validateSanctionedDates if loop");
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
						isValid=false;
						//System.out.println("amountSanctionedDate cannot be before "+toValue);
					
					}
				}
			}
			//else
			{
				//System.out.println("validateSanctionedDates else loop");
				try
				{
					//isValid=dateValidationOnBetweenBOI(sdf.parse(fromValue));
				}
				catch(Exception e)
				{
				//System.out.println("validateSanctionedDates esle loop"+e.getMessage());
					isValid=false;
				}
			}
		}
		return isValid;
	
	}
	
	public String validateInternalRating(String loanType , HashMap creaditValues  )
	{
		String validationMessage="";
	String internalRatingValidaChk="";
		try
		{
		if(loanType.equalsIgnoreCase("tc"))
		{
			//System.out.println("3 validateInternalRating ="+creaditValues.get("TL_CREDIT_TO_GUARANTEE"));
			// System.out.println("4 validateInternalRating ="+ConvertStrToInt(creaditValues.get("TL_CREDIT_TO_GUARANTEE").toString()));
			 internalRatingValidaChk=creaditValues.get("INTERNAL_RATING").toString().toUpperCase();
			if(ConvertStrToInt(creaditValues.get("TL_CREDIT_TO_GUARANTEE").toString()) >= 5000000 && !internalRatingValidaChk.equals("Y") )
			{
				validationMessage="INTERNAL_RATING should be 'Y' , When TL_CREDIT_TO_GUARANTEE amout is above or equal to 50 lakh. ";
			}
		}
		else if (loanType.equalsIgnoreCase("wc"))
		{
			double FBAndNFBAmount=ConvertStrToInt(creaditValues.get("WC_FB_CREDIT_TO_GUARANTEE").toString())+ConvertStrToInt(creaditValues.get("WC_NFB_CREDIT_TO_GUARANTEE").toString());
			 internalRatingValidaChk=creaditValues.get("INTERNAL_RATING").toString().toUpperCase();
			if(FBAndNFBAmount >= 5000000 && !internalRatingValidaChk.equals("Y"))
			{
				validationMessage="INTERNAL_RATING should be 'Y' , When ( WC_FB_CREDIT_TO_GUARANTEE + WC_NFB_CREDIT_TO_GUARANTEE ) amout is above or equal to 50 lakh. ";
			}
		}
		}
		catch(Exception e)
		{
			//e.printStackTrace();
		}
		return validationMessage;
	} 
	
	public static boolean dateValidationOnBetweenBOI( Date dateForCompare ) 
	{	    
		// System.out.println("dateValidationOnBetweenBOI"+dateForCompare);
		boolean flag=true;		
		try
		{
			SimpleDateFormat formater = new SimpleDateFormat("dd/MM/yyyy");			
			Date startdate = formater.parse("01/07/2015");
	        Date enddate = formater.parse("31/12/2015");
	     //System.out.println("dateForCompare.compareTo(startdate) "+dateForCompare.compareTo(startdate));
	     //System.out.println("dateForCompare.compareTo(enddate) "+dateForCompare.compareTo(enddate));
	        if (dateForCompare.compareTo(startdate) < 0) {
	           // System.out.println(formater.format(dateForCompare) + " is less than " + formater.format(enddate));
	            flag=false;
	        }
	
	        if (dateForCompare.compareTo(enddate) > 0) {
	          //  System.out.println(formater.format(dateForCompare) + " is greater than " + formater.format(enddate));
	            flag=false;
	        }
		}
		catch(Exception e)
		{
			 flag=false;
	      
	    }
		return flag;
	}
	
	public static Set<String> crunchifyPermutation(String str) {
		Set<String> crunchifyResult = new HashSet<String>();
		if (str == null) {
			return null;
		} else if (str.length() == 0) {
			crunchifyResult.add("");
			return crunchifyResult;
		}
 
		char firstChar = str.charAt(0);
		String rem = str.substring(1);
		Set<String> words = crunchifyPermutation(rem);
		for (String newString : words) {
			for (int i = 0; i <= newString.length(); i++) {
				crunchifyResult.add(crunchifyCharAdd(newString, firstChar, i));
			}
		}
		return crunchifyResult;
	}
 
	public static String crunchifyCharAdd(String str, char c, int j) {
		String first = str.substring(0, j);
		String last = str.substring(j);
		return first + c + last;
	}
	
	
	public Map userID(String name)
	{
		HashMap<String,ArrayList<String>> userIDSet=new HashMap<String,ArrayList<String>>();	
		List sortedList = new ArrayList(crunchifyPermutation(name));
		Collections.sort(sortedList);
		ArrayList<String> primaryData=new ArrayList<String>();
		ArrayList<String> secondaryData=new ArrayList<String>();		
		
		for (int i = 0; i < sortedList.size(); i++) 
		{						
			if( sortedList.get(i).toString().startsWith(name.substring(0,4)) || sortedList.get(i).toString().startsWith(name.substring(4,8)) )
			{
				primaryData.add(sortedList.get(i).toString().trim());		
			}
			else
			{
				secondaryData.add(sortedList.get(i).toString().trim());
			}
		}
		Collections.reverse(primaryData);
		Collections.reverse(secondaryData);		
		userIDSet.put("primaryUserIDData", primaryData);
		userIDSet.put("secondaryUserIDData", secondaryData);
		return userIDSet;
	}
	
	public boolean validatedateSanctionDate(Date amountSanctionedDate)
	{
		boolean result=true;
		//System.out.println("amountSanctionedDate"+amountSanctionedDate+"=");
		if(amountSanctionedDate!=null)
		{
		
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
			//	e.printStackTrace();
				result=false;
			}
		}
		else
		{
			result=false;
		}
		//	System.out.println("validatedateForTC result "+result);
			return result;
		
	}
}
