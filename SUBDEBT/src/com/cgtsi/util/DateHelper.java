package com.cgtsi.util;


import java.util.*;
import java.text.*;

/**
 * Provides a list of Utlity Methods for checking, creating and formatting of dates.
 *  User     dateformat : 1/12/2000. <br>
 *  Database dateformat : 5-May-2000.<br>
 * 
 * All input/output is as String objects.
 * Internal manipulation is via Date, Calendar etc.
 */
public class DateHelper
{
    /**
     * Format is 2000-Aug-21
    **/
    private static final String DATEFORMAT = "dd-MMM-yyyy";
	/**
	 * The date separator to be used by database.
	 */
	private static final String dbDateSeparator	  = "-";

	/**
	 * The date separator to be used by application and user.
	 */
	private static final String stringDateSeparator = "/";

	/**
	 * The available month strings that can be used by database or user.
	 */
	private static final String [] mthDBFormat = { "JAN", "FEB", "MAR", "APR",
												   "MAY", "JUN", "JUL", "AUG",
												   "SEP", "OCT", "NOV", "DEC" };

	/**
	 * Parameter for comparing dates.
	 */
	private static final int day1_before_day2 = 1;

	/**
	 * Parameter for comparing dates.
	 */
	private static final int day1_equals_day2 = 0;

	/**
	 * Parameter for comparing dates.
	 */
	private static final int day1_after_day2  = -1;

	/**
	 * Private constructor to prevent anyone from creating an instance of this
	 * class.
	 */
	private DateHelper()
	{

	}
    public static boolean isGreaterThanToday(String dateStr)
    {
        return DateHelper.isGreaterThanToday(dateStr, DATEFORMAT);
    }

    public static boolean isGreaterThanToday(Calendar newCal)
    {
        Calendar cal = Calendar.getInstance();
        if (newCal.get(Calendar.YEAR) < cal.get(Calendar.YEAR)) {
            return false;
        } else if (newCal.get(Calendar.YEAR) == cal.get(Calendar.YEAR)) {
            if (newCal.get(Calendar.MONTH) < cal.get(Calendar.MONTH)) {
                return false;
            } else if (newCal.get(Calendar.MONTH) == cal.get(Calendar.MONTH)) {
                if (newCal.get(Calendar.DAY_OF_MONTH) <= cal.get(Calendar.DAY_OF_MONTH)) {
                    return false;
                }
            }
        }
        return true;
    }

	public static boolean isGreaterThanToday(String dateStr, String format)
	{
        SimpleDateFormat dateFormat = new SimpleDateFormat(format);
        try {
            //System.out.println("date string : " + dateStr);
            Date date = dateFormat.parse(dateStr);
            Calendar newCal = Calendar.getInstance();
            newCal.setTime(date);
            return DateHelper.isGreaterThanToday(newCal);
        }
        catch (Exception e) {
            e.printStackTrace();
        }
        return false;

	}

	////////////////////////////////////////////////////////////////////////////
	//									Creation methods					  //
	////////////////////////////////////////////////////////////////////////////
	/**
	 * Get today's date.
	 * Note that the date returned is dependent on the time-zone
	 * setting of the local machine.
	 * @return a date in the format 1/12/2000
	 */
	public static String getTodayDate()
	{
		Calendar c = Calendar.getInstance();

		int day  = c.get(Calendar.DATE);
		int mth  = c.get(Calendar.MONTH) + 1;
		int year = c.get(Calendar.YEAR);

		String today = day + stringDateSeparator + mth + stringDateSeparator + year;
		today = stringToDBDate(today);

		return today;
	}

	public static String getTodayTime()
	{
		Calendar c = Calendar.getInstance();

		int hour = c.get(Calendar.HOUR_OF_DAY);
		int min = c.get(Calendar.MINUTE);
		int sec = c.get(Calendar.SECOND);

		String time = "";
        if (hour < 10)
            time += "0";
        time += hour + ":" ;
        if (min < 10)
            time += "0";
        time += min + ":" ;
        if (sec < 10)
            time += "0";
        time += sec;
		return time;
	}



	/**
	 * For future use.
	 * Note that the return Date object may not be the same for the
	 * same input parameter.
	 * <p><i>
	 *		i.e 1st invoke : 20/6/2000 = Tue Jun 20 00:00:19 CST 2000 <br>
	 *		    2nd invoke : 20/6/2000 = Tue Jun 20 00:00:13 CST 2000 <br>
	 *	</i></p>
	 *
	 * @param date a date in the format 20/6/2000 or 20-Jul-2000.
	 * @return Date object with a precision of up to seconds.
	 */
	private static Date getDate(String date)
	{
		int notFound = -1;
		Calendar c = null;

		if(date.indexOf(dbDateSeparator) != notFound)
		{
			if( (date=dbDateToString(date)) == null )
			{
				return null;
			}
		}

		try
		{
			date = removeLeadingZeros(date);

			StringTokenizer st = new StringTokenizer(date, stringDateSeparator);
			int day  = Integer.parseInt(st.nextToken());
			int mth  = Integer.parseInt(st.nextToken()) - 1;
			int year = Integer.parseInt(st.nextToken());

			// This check can be omitted if use Calendar.setLenient(false)
			// However, this step is introduced for finer control of the
			// date fields and more concise error msgs!
			if(!withinDayRange(day) || !withinMonthRange(mth+1) || !withinYearRange(year))
			{
				return null;
			}

			c = Calendar.getInstance();
			// c.setLenient(false);
			c.set(year, mth, day, 0, 0);   // 0 - Jan, 11 - Dec
		}
		catch(Exception e)
		{
			return null;
		}

		return c.getTime();
	}


	////////////////////////////////////////////////////////////////////////////
	//		Formatting methods	- Checking for validity is first carried out   	  //
	////////////////////////////////////////////////////////////////////////////
	/**
	 * Formats the input to a DB date format.[ie. 20/7/2000 to 20-Jul-2000]
	 * @param date format is of 20/7/2000.
	 * @return date of format 20-Jul-2000 or null if invalid date.
	 */
	public static String stringToDBDate(String date)
	{
		if(!isValidDate(date))
		{
			return null;
		}

		date = removeLeadingZeros(date);

		StringTokenizer st = new StringTokenizer(date, stringDateSeparator);
		String day  = st.nextToken();
		String mth  = mthDBFormat[Integer.parseInt(st.nextToken())-1];
		String year = st.nextToken();

		return day + dbDateSeparator + mth + dbDateSeparator + year;
	}


	/**
	 * Formats the input to a String date.[ie. 20-Jul-2000 to 20/6/2000]
	 * @param date format is of 20-Jul-2000.
	 * @return date of format 20/7/2000 or null if invalid date.
	 */
	public static String dbDateToString(String date)
	{
		if(!isValidDate(date))
		{
			return null;
		}

		StringTokenizer st = new StringTokenizer(date, dbDateSeparator);
		String day  = st.nextToken();
		String mth  = st.nextToken();
		String year = st.nextToken();

		for(int i=0; i<mthDBFormat.length; i++)
		{
			if(mth.toUpperCase().equals(mthDBFormat[i]))
			{
				mth = Integer.toString(i + 1); // 1 - Jan, 12 - Dec
				break;
			}
		}

		return removeLeadingZeros(day + stringDateSeparator + mth + stringDateSeparator + year);
	}


	/**
	 * Formats the date string to a suitable form for conversion to java.sql.Date.
	 * @param date format is "dd/mm/yyyy"
	 * @return a date in the format "yyyy-mm-dd"
	 */
	public static String stringToSQLdate(String date)
	{
		if(!isValidDate(date))
		{
			return null;
		}

		date = removeLeadingZeros(date);

		StringTokenizer st = new StringTokenizer(date, stringDateSeparator);
		String day  = st.nextToken();
		String mth  = st.nextToken();
		String year = st.nextToken();

		return year + dbDateSeparator + mth + dbDateSeparator + day;
	}


	/**
	 * Formats the date string to java.sql.Date format.
	 * @param date format is "dd-Month-yyyy". ie 31-Aug-2000
	 * @return a date in the format "yyyy-mm-dd" ie. 2000-8-31
	 */
	public static String DBdateToSQLdate(String date)
	{
		if(!isValidDate(date))
		{
			return null;
		}

		date = dbDateToString(date); // 21-Jun-2000 to 21/6/2000
		date = stringToSQLdate(date); // 21/6/2000 to 2000-6-21

		return date;
	}


	/**
	 * Formats the date string to java.sql.Date format.
	 * @param date format is "yyyy-mm-dd". ie 2000-8-31
	 * @return a date in the format "dd-Month-yyyy". ie "31-Aug-2000"
	 */
	public static String SQLdateToDBdate(String date)
	{
		String day = "";
		String month = "";
		String year = "";
		StringTokenizer st = null;
		try
		{
			st = new StringTokenizer(date, "-");
			year = st.nextToken();
			month = st.nextToken();
			day = st.nextToken();

			date = day + "/" + month + "/" + year;
			date = stringToDBDate(date); // 23/12/2000 to 23-Dec-2000
		}
		catch(ArrayIndexOutOfBoundsException a)
		{
			System.out.println("Input: " + date + "in wrong format, cannot convert.");
			return null;
		}

		return date;
	}


	////////////////////////////////////////////////////////////////////////////
	//									Checking methods					  //
	////////////////////////////////////////////////////////////////////////////
	/**
	 * Checks input date format.
	 * For use by <code>isValidDate()</code> method.
	 * @param date can be in the form "20-Jul-2000" and "20/5/2000".
	 * @return true if correct format, false otherwise.
	 */
	private static boolean correctDateFormat(String date)
	{
		boolean correctFormat = false;
		int notFound = -1;
		if(date.indexOf(dbDateSeparator) != notFound)
		{
			correctFormat = correctDBDateFormat(date);
		}
		else
		if(date.indexOf(stringDateSeparator) != notFound)
		{
			correctFormat = correctStringDateFormat(date);
		}
		else
		{
			System.out.println(
				"Date does not contain compulsory separators : '" +
				 dbDateSeparator + "'  '" + stringDateSeparator + "'.");
		}
		return correctFormat;
	}


	/**
	 * Checks date string is of format "20-Jul-2000".
	 * For use by correctDateFormat()
	 * @param date in the format "20-Jul-2000".
	 * @return true if correct format, false otherwise.
	 */
	private static boolean correctDBDateFormat(String date)
	{
		boolean correctFormat = false;

		StringTokenizer st = new StringTokenizer(date, dbDateSeparator);
		if(st.countTokens()!=3)
		{
			System.out.println("Error: Date field not in correct format!");
			return correctFormat;
		}

		String day = st.nextToken();
		String mth = st.nextToken();
		String yr  = st.nextToken();

		if(!isInteger(day) || !isInteger(yr))
		{
			System.out.println("Error: Day and Year fields must be in digit format.");
		}
		else
		{
			for(int i=0; i<mthDBFormat.length; i++)
			{
				if(mth.toUpperCase().equals(mthDBFormat[i]))
				{
					correctFormat = true;
				}
			}

			if(correctFormat==false)
			{
				System.out.println("Error: Month field is not correct. Check your spelling.");
			}
		}

		return correctFormat;
	}


	/**
	 * Checks date string is of format "20/7/2000".
	 * For use by <code>correctDateFormat()</code> and <code>removeLeadingZeros()</code>.
	 * @param date in the format "20/7/2000".
	 * @return true if correct format, false otherwise.
	 */
	private static boolean correctStringDateFormat(String date)
	{
		boolean correctFormat = false;

		StringTokenizer st = new StringTokenizer(date, stringDateSeparator);
		if(st.countTokens()!=3)
		{
			System.out.println("Error: Date field not in correct format.");
			return correctFormat;
		}

		while(st.hasMoreTokens())
		{
			if(!isInteger(st.nextToken()))
			{
				System.out.println("Error: Day/Month/Year fields must be integers.");
				return correctFormat;
			}
		}

		return correctFormat = true; // for clarity
	}


	/**
	 * Checks that the day range is from 1-31.
	 * This check is necessary to prevent user from entering 0/0/0 which
	 * would otherwise return a default date from sun's implementation.
	 * For use by <code>isValidDate()</code> and <code>getDate()</code> methods.
	 * @param day the day of the month.
	 * @return true if in the valid range, false otherwise.
	 */
	private static boolean withinDayRange(int day)
	{
		if(day > 31 || day < 1)
		{
			System.out.println("Error: day field should be > 1 and < 31");
			return false;
		}
		return true;
	}

	/**
	 * Checks that the month range is from 1-12.
	 * This check is necessary to prevent user from entering 0/0/0 which
	 * would otherwise return a default date from sun's implementation.
	 * For use by <code>isValidDate()</code> and <code>getDate()</code> methods.
	 * @param month the month of the year.
	 * @return true if in the valid range, false otherwise.
	 */
	private static	boolean withinMonthRange(int month)
	{
		if(month > 12 || month < 1)
		{
			System.out.println("Error: month field should be > 1 and < 12");
			return false;
		}
		return true;
	}

	/**
	 * Checks that the year range is within the user's or system's requirements.
	 * Any limit can be specified as long as it is >1000 and <9999
	 * This check is necessary to prevent user from entering 0/0/0 which
	 * would otherwise return a default date from sun's implementation.
	 * For use by <code>isValidDate()</code> and <code>getDate()</code> methods.
	 * @param year the year to check for validity.
	 * @return true if within valid range, false otherwise.
	 */
	private static boolean withinYearRange(int year)
	{
		if(year < 1990 || year > 2050) // This system not expected to live 50 years!
		{
			System.out.println("Error: year field should be > 1990 or < 2050");
			return false;
		}
		return true;
	}

	/**
	 * Accepts date in the form 31-Jul-2000 or 31/7/2000.
	 * <p><i>
	 * Checks for :
	 *	<ol>
	 *		<li> Correct Format
	 *		<li> Within Range
	 *		<li> Valid Date
	 *	</ol>
	 * </i></p>
	 * Although the DateFormat.parse() function can check for wrong format
	 * we still check for the format so we can tell the user exactly what happen.
	 */
	public static boolean isValidDate(String date)
	{
		int notFound = -1;

		// Correct Format?
		if(!correctDateFormat(date))
		{
			return false;
		}

		// Makes sure the format of the date is dd/mm/yyyy.  Cannot call dbDateToString()
		// because this method is called by that method.
		if(date.indexOf(dbDateSeparator) != notFound)
		{
			StringTokenizer st = new StringTokenizer(date, dbDateSeparator);
			String day  = st.nextToken();
			String mth  = st.nextToken();
			String year = st.nextToken();

			for(int i=0; i<mthDBFormat.length; i++)
			{
				if(mth.toUpperCase().equals(mthDBFormat[i]))
				{
					mth = Integer.toString(i+1); // 1 - Jan, 12 - Dec
					break;
				}
			}

			date = day + stringDateSeparator + mth + stringDateSeparator + year;
		}

		// Remove leading zeros otherwise parsing will fail.
		date = removeLeadingZeros(date);

		// within Range?
		StringTokenizer st = new StringTokenizer(date, stringDateSeparator);
		int dd  = Integer.parseInt(st.nextToken());
		int mm  = Integer.parseInt(st.nextToken()) - 1;
		int yy = Integer.parseInt(st.nextToken());
		// This check can be omitted if use Calendar.setLenient(false)
		// However, this step is introduced for finer control of the
		// date fields and more concise error msgs!
		if(!withinDayRange(dd) || !withinMonthRange(mm+1) || !withinYearRange(yy))
		{
			return false;
		}

		// Checks that the date is valid
		SimpleDateFormat dateFormat = new SimpleDateFormat("d/M/yyyy");
		dateFormat.setLenient(false);
		try
		{
			ParsePosition p = new ParsePosition(0);
         Date d = dateFormat.parse(date, p);

			if(d == null)
			{
				System.out.println("Error: Fail to parse date. Input date : " + date);
				return false;
			}

         Calendar cal = dateFormat.getCalendar();
         if ((cal.get(Calendar.YEAR)>9999) || (cal.get(Calendar.YEAR)<1000))
			{
				System.out.println("Error: Date out of range. Input date : " + date);
				return false;
			}
		}
		catch(Exception e)
		{
			System.out.println("Error: Date verification encounter unexpected error! " +
									 "Input date: " + date + ".\n" + e);
			return false;
		}

		return true;
	}

	////////////////////////////////////////////////////////////////////////////
	//		Comparison methods	- Checking for validity is first carried out   //
	////////////////////////////////////////////////////////////////////////////
	/**
	 * Tests if 1st date is before 2nd date.
	 * @param day1 the first date in the format 12/12/2000 or 12/Dec/2000
	 * @param day2 the second date in the format 12/12/2000 or 12/Dec/2000
	 * @return true if 1st date is before 2nd date, false otherwise.
	 */
	public static boolean day1BeforeDay2(String day1, String day2)
	{
		boolean result = false;
		try
		{
			int i = compareDates(day1, day2);
			if(i == day1_before_day2)
			{
				result = true;
			}
		}
		catch(Exception e)
		{
			System.out.println("Error: " + e);
			return result;
		}

		return result;
	}

	/**
	 * Tests if 1st date is after 2nd date.
	 * @param day1 the first date in the format 12/12/2000 or 12/Dec/2000
	 * @param day2 the second date in the format 12/12/2000 or 12/Dec/2000
	 * @return true if 1st date is after 2nd date, false otherwise.
	 */
	public static boolean day1AfterDay2(String day1, String day2)
	{
		boolean result = false;
		try
		{
			int i = compareDates(day1, day2);
			if(i == day1_after_day2)
			{
				result = true;
			}
		}
		catch(Exception e)
		{
			System.out.println("Error: " + e);
			return result;
		}

		return result;
	}

	/**
	 * Tests if 1st date is equals 2nd date.
	 * @param day1 the first date in the format 12/12/2000 or 12/Dec/2000
	 * @param day2 the second date in the format 12/12/2000 or 12/Dec/2000
	 * @return true if 1st date is equals 2nd date, false otherwise.
	 */
	public static boolean day1EqualsDay2(String day1, String day2)
	{
		boolean result = false;
		try
		{
			int i = compareDates(day1, day2);
			if(i == day1_equals_day2)
			{
				result = true;
			}
		}
		catch(Exception e)
		{
			System.out.println("Error: " + e);
			return result;
		}

		return result;
	}

	/**
	 * Compares 2 dates
	 * @param day1 the first date in the format 12/12/2000 or 12/Dec/2000
	 * @param day2 the second date in the format 12/12/2000 or 12/Dec/2000
	 * @return
	 * <p><i>
	 *  1 if day1 > day2 <br>
	 * -1 if day1 < day2 <br>
	 *  0 if day1 = day2 <br>
	 * </i></p>
	 * @exception Exception if input dates are invalid.
	 */
	public static int compareDates(String day1, String day2)
		throws Exception
	{
		int result = 2; // should not have this result

		int notFound = -1;
		if(day1.indexOf(dbDateSeparator)!= notFound)
		{
			day1 = dbDateToString(day1); // validation is automatically carried out.
		}
		if(day2.indexOf(dbDateSeparator)!= notFound)
		{
			day2 = dbDateToString(day2); // validation is automatically carried out.
		}
		if(day1==null || day2==null)
		{
			throw new Exception("Error: Invalid date used for comparison.");
		}

		// After validation
		int [] dayA = dateStringToIntArray(day1);
		int [] dayB = dateStringToIntArray(day2);

		// check year
		if(dayA[2] < dayB[2])
		{
			result = day1_before_day2;
		}
		else
		if(dayA[2] > dayB[2])
		{
			result = day1_after_day2;
		}
		else
		if(dayA[2] == dayB[2]) // same year
		{
			if(dayA[1] < dayB[1])
			{
				result = day1_before_day2;
			}
			else
			if(dayA[1] > dayB[1])
			{
				result = day1_after_day2;
			}
			else
			if(dayA[1] == dayB[1]) // same month
			{
				if(dayA[0] < dayB[0])
				{
					result = day1_before_day2;
				}
				else
				if(dayA[0] > dayB[0])
				{
					result = day1_after_day2;
				}
				else
				if(dayA[0] == dayB[0]) // same day
				{
					result = day1_equals_day2;
				}
			}
		}
		return result;
	}

	////////////////////////////////////////////////////////////////////////////
	//									Utility methods						  //
	////////////////////////////////////////////////////////////////////////////

	/**
	 * Always make sure that <code>isValidDate()</code> is called before invoking this method.
	 * @param date in the format 12/12/2000
	 * @return an array of integers in the order day, month, year.
	 */
	private static int [] dateStringToIntArray(String date)
	{
		StringTokenizer st = new StringTokenizer(date, stringDateSeparator);
		int [] aDate = new int [3];
		for(int i=0; i<aDate.length; i++)
		{
			aDate[i] = Integer.parseInt(st.nextToken());
		}

		return aDate;
	}

	/**
	 * Checks that a given string contains only integers.
	 * @param s the date to check.
	 * @return true if input String with an int value, false otherwise.
	 */
	private static boolean isInteger(String s)
	{
		try
		{
			int i = Integer.parseInt(s);
		}
		catch(NumberFormatException nfe)
		{
			return false;
		}
		return true;
	}




	/**
	 * The input date must be in the format : 12/12/2000
	 * Format is checked but not validated because validation requires this function.
	 *	Leading zeros are removed to prevent any errors during comparisons.
	 * @param date should be in the format "02/12/2000"
	 * @return date without leading zeros. ie "2/12/2000"
	 */
	private static String removeLeadingZeros(String date)
	{
		if(correctStringDateFormat(date))
		{
			StringTokenizer st = new StringTokenizer(date, stringDateSeparator);
			try
			{
				int day  = Integer.parseInt(st.nextToken());
				int mth  = Integer.parseInt(st.nextToken());
				int year = Integer.parseInt(st.nextToken());
				date = day + stringDateSeparator + mth + stringDateSeparator + year;
			}
			catch(Exception e)
			{
				System.out.println("Failed to remove leading zeros.");
				date = null;
			}
		}
		else
		{
			date = null;
		}
		return date;
	}


/**Gets the Time left from the given date arguement.
  * MethodType            : public<BR>
  * @return               : A string containg the date and time left.<BR>
  * @param  sDate         : String of the form days hrs:mts or -days hrs:mts
  * Description           : Takes an input of the form days hrs:mts or -days hrs:mts and returns the date and time left<BR>
  * @exception            : none
 */
 public static String getTimeLeft(String sDate)
 { // returns the date and time left. where sDate will  be of type --> days hrs:mts or -days hrs:mts
    String sDateTime="";
    String strDays="";
    String strHrs="";
    sDate=sDate!=null?sDate:"";
    if(sDate.startsWith("-"))
    {
      return "0 Days" ;
    }
    strDays=sDate.substring(0,sDate.indexOf(" "));
    strHrs=sDate.substring(sDate.indexOf(" ")+1);
    int days=Integer.parseInt(strDays);

    if(!(days==0))
    {
     sDateTime=strDays+" Days "+ strHrs+ " Hrs. ";
    }
    else
    {
      sDateTime=strHrs+ " Hrs. ";

    }
    return sDateTime;

  }

/** Gets the GMTTime at any given instant.
  * MethodType            : public
  * @return               : String<BR>
  * Parameters            : none<BR>
  * Description           : this method is helping to calculate all the Countries Time<BR>
  * @exception             : none<BR>
 */
    public static String getGMTTime()
    {
        String rDate = "";
        String strHOUR = "";
        String strMIN = "";
        try
        {
            GregorianCalendar cal = new GregorianCalendar();
            if (cal==null)
                return "";
            //int x=(cal.get(Calendar.ZONE_OFFSET)/(60*60*1000));
            cal.add(Calendar.HOUR,-(cal.get(Calendar.ZONE_OFFSET)/(60*60*1000)));
            strHOUR=""+cal.get(Calendar.HOUR_OF_DAY);
            strMIN=""+cal.get(Calendar.MINUTE);
                        if(strHOUR.length()==1)
                        {
                           strHOUR="0"+strHOUR;
                        }
                        if(strMIN.length()==1)
                        {
                           strMIN="0"+strMIN;
            }
            return  strHOUR+":"+strMIN;
        } catch (Exception ert) {return "";}
    }//end of getGMTTime method

/** Gets the GMT Date at the given instant.
  * MethodType            : public
  * @return               : String<BR>
  * Parameters            : none<BR>
  * Description           : this method is helping to calculate all the Countries Date<BR>
  * @exception            : none
 */
    public static String getGMTDate()
    {
        String strDay="";
        String strMonth="";
        try
        {
            GregorianCalendar cal = new GregorianCalendar();
            if (cal==null)
                return "";
            cal.add(Calendar.HOUR,-(cal.get(Calendar.ZONE_OFFSET)/(60*60*1000)));
            strDay=""+cal.get(Calendar.DATE);
            strMonth=""+(cal.get(Calendar.MONTH)+1);
            if(strDay.length()==1)
            {
               strDay="0"+strDay;
            }
            if(strMonth.length()==1)
            {
               strMonth="0"+strMonth;
            }
            return strDay+"/"+strMonth+"/"+cal.get(Calendar.YEAR);
        } catch (Exception ert) {return "";}
    }//end of getGMTDate



	/**
	 * Provides a common interface for all programs to log their errors.
	 * Overload this method if additional logging is required.
	 * This allows error logging for this class to be easily redirected.
	 * @param errMsg The error message to log
	 */
	private static void logError(String errMsg)
	{
		System.out.println("Error : " + errMsg);
	}


	// to check mm/yyyy or m/yyyy or m/yy  format for date entered


	////////////////////////////////////////////////////////////////////////////
	//										Testing method					  //
	////////////////////////////////////////////////////////////////////////////
	/**
	 * For testing purposes. All output will be directed to stdout.
	 */
/*	public static void main(String args [])
	{
		*/
		/*
			[ Test cases ]
			---------------------------
			12/12/2000 - normal
			01/01/2000 - leading zeros
			31/02/2000 - invalid date
			12/13/2000 - out-of-range
			12/Jul/2000 - wrong format
			00/01/2000 - invalid date
			00/00/2000 - invalid date
			00/00/0000 - invalid date

			Category		 public methods			Others
			---------------------------------------
			Creation     getTodayDate()
			Validation	 isValidDate()
			Formatting 	 stringToDBDate()		includes validation
			Formatting	 dbDateToString()		includes validation
			Comparison	 day1BeforeDay2()		includes validation
			Comparison	 day1AfterDay2()		includes validation
			Comparison	 day1EqualsDay2()		includes validation
		*/
		/*
		if(args.length!=2)
		{
			logError("Please supply 2 dates for comparison.");
		}
		else
		{
			try
			{
				if(day1BeforeDay2(args[0], args[1])==true)
				{
					logError("Day1 < Day2");
				}
				if(day1AfterDay2(args[0], args[1])==true)
				{
					logError("Day1 > Day2");
				}
				if(day1EqualsDay2(args[0], args[1])==true)
				{
					logError("Day1 = Day2");
				}
			}
			catch(Exception e)
			{
				logError("Error: " + e);
			}
		}
		*/

/*		String d1 = "11-JUL-2000";
		String d2 = "20-SEP-200";

		if (DateHelper.isGreaterThanToday(d1)) System.out.println(d1 + " is greater than today");
		if (DateHelper.isGreaterThanToday(d2)) System.out.println(d2 + " is greater than today");

		logError("The date is  " + stringToSQLdate("03/05/2000"));
		logError("The date is  " + DBdateToSQLdate("21-Jun-2000"));
		logError("The date is  " + SQLdateToDBdate("2000-12-1"));

		logError("Today's date is : " + getTodayDate());

		if(args.length!=0)
		{
			//logError("isValidDate(" + args[0] + ") = " + isValidDate(args[0]));
			logError("isValidDate(asdfasdf) = " + isValidDate("asdfasdf"));
		}
	} */
	public static Date sqlToUtilDate(java.sql.Date SqlDate)
   {
		java.util.Date returnUtilDate=null;
	   try
	   {
			 returnUtilDate=new java.util.Date(SqlDate.getTime());
	   }
	   catch (Exception e)
	   {
		   return null;
	   }
		
	   return returnUtilDate;

   }
   
   public static long getMonthDifference(Calendar date1, Calendar date2) {
   		long noOfMonths = 0 ;
   	
		int year1 = date1.get(Calendar.YEAR) ;
		int month1 = date1.get(Calendar.MONTH) ;

		int year2 = date2.get(Calendar.YEAR) ;
		int month2 = date2.get(Calendar.MONTH) ;
		 
		long noOfMonthsDate1 = (year1 * 12) + month1 ;    		
		long noOfMonthsDate2 = (year2 * 12) + month2 ;
		
		noOfMonths = noOfMonthsDate2 - noOfMonthsDate1 ;
		 
   		return (noOfMonths+1) ;	 
   }

  // Start Code By Path 0n 11Oct06
  public static long getDays(Calendar date1, Calendar date2)
     {
        long noOfMonths = 0 ;
        
        int year1 = date1.get(Calendar.YEAR) ;
        int month1 = date1.get(Calendar.MONTH) ;
        int day1 = date1.get(Calendar.DAY_OF_MONTH);
        
        int year2 = date2.get(Calendar.YEAR) ;
        int month2 = date2.get(Calendar.MONTH) ;
        int day2 =  date2.get(Calendar.DAY_OF_MONTH); 
        
        long from = new GregorianCalendar(year1, month1+1,day1).getTime().getTime();
        
        long to   = new GregorianCalendar(year2, month2+1,day2).getTime().getTime();
        long difference = to - from;
        long days1 = (difference/(1000*60*60*24)); 
        // no. of days between the two dates
        return days1+1; 
     }

  
  // Start Code By Path 0n 11Oct06


// End Code by Path on 11Oct06
  
  public static  java.util.Date getFixedDate(String date) 
  {
	  Date retDate = null ;
	  try
	 {
	  SimpleDateFormat sdf = new SimpleDateFormat("dd/mm/yyyy");
	  retDate = sdf.parse(date);
	  System.out.println("Date retDate:"+retDate);
	 }
	 catch (Exception e)
	 {
		 e.printStackTrace();
	 }
	 
	  return retDate ;
	  
  }



// End Code by Path on 11Oct06


}// end class DateHelper

