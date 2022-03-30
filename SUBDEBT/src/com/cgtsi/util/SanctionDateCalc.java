package com.cgtsi.util;

import java.text.ParsePosition;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.validator.Field;
import org.apache.commons.validator.GenericValidator;
import org.apache.commons.validator.ValidatorAction;
import org.apache.commons.validator.ValidatorUtil;
import org.apache.struts.action.ActionError;
import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.DynaActionForm;

import com.cgtsi.util.DateHelper;

public class SanctionDateCalc {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		new SanctionDateCalc().show();
	}
	
	
	public boolean validateFromCurrentDate(String inputDate) {
		boolean result = false;
		if(inputDate!=null)
		{
			try
			{
				SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy");				
				Date date = formatter.parse(inputDate);
				String pattern = "dd/MM/yyyy";
				String dateInString =new SimpleDateFormat(pattern).format(new Date());
				Date CurrentDate = formatter.parse(dateInString);
				
				if (CurrentDate.equals(date)) {
					result = true;
				}
			}
			catch(Exception e)
			{
				result = false;
			}
		}
		return result;
	}
	
	public void show()
	{
try
{
	String creditToG="5000000";
	
	Integer.parseInt(creditToG);
	
		if(!validateSanctionedDates("01/09/2016"))
		{
			System.out.println("sanction date is invalid");
		}
		else
		{
			System.out.println("sanction date is valid");
		}
		
		System.out.println(chkDateIsLessOrNot("27/07/2016"));
}
catch(Exception e)
{
	e.printStackTrace();
}
	}
	
	
	public boolean chkDateIsLessOrNot(String inputDate) {
		boolean result = false;
		if(inputDate!=null)
		{
			try
			{
				SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy");				
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
			catch(Exception e)
			{
				result = false;
			}
		}
		return result;
	}
	
	public boolean validateFromLastQuarterDate(String inputDate) {
		boolean isValid = false;
		
		if(inputDate!=null)
		{
			try
			{
			SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy");
			Date date = formatter.parse(inputDate);
			Calendar today_cal = Calendar.getInstance();
			Calendar input_cal = Calendar.getInstance();
			Date today = new Date();
			today_cal.setTime(today);
			int current_month = today_cal.get(Calendar.MONTH);
			input_cal.setTime(date);
			int input_month = input_cal.get(Calendar.MONTH);
			
			System.out.println("input_month "+input_month);
			System.out.println("current_month "+current_month);
	
			if (((current_month >= 0 && current_month <= 2) && ((input_month >= 0 && input_month <= 2) || (input_month >= 9 && input_month <= 11)))) {
				isValid = true;
				System.out.println("1 st loop "+isValid);
			}
			if (((current_month >= 3 && current_month <= 5) && ((input_month >= 3 && input_month <= 5) || (input_month >= 0 && input_month <= 2)))) {
				isValid = true;
				System.out.println("2nd loop "+isValid);
			}
			if (((current_month >= 9 && current_month <= 11) && ((input_month >= 9 && input_month <= 11) || (input_month >= 6 && input_month <= 8)))) {
				isValid = true;
				System.out.println("3rd loop "+isValid);
			}
			}
			catch(Exception e)
			{
				isValid = false;	
				e.printStackTrace();
			}
		}
		System.out.println("isValid "+isValid);
		return isValid;
	}
	
	
	
	
	public  boolean  validateSanctionedDates(String fromValue) {
	
		boolean isValid = true;
		String toValue = null;
		
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
				isValid=false;
				System.out.println("amountSanctionedDate cannot be before "+toValue);
			
			}
		}
		
		return isValid;
	
	}

}
