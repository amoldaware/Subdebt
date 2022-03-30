package com.cgtsi.util;

import java.util.*;

public class DateHelp {

    private int iDay;
    private int iMon;
    private int iYear;
    private int iMin;
    private int iHrs;



  public DateHelp() {
  }

/**
  *This method is getting the todays Date
  *@return		 String<BR>
  *Parameters   : none<BR>
  *@exception   : none<BR>
 */
  public static String getTodaysDate()
  {
      System.out.println("in getTodaysDate() in DateHelp");
      int month = 7;
      int year = 1970;
      int hours = 0;
      int day = 0;
      int min = 0;
      String strDay="";
      String strMonth="";
      String rDate = "";
      Calendar cal = Calendar.getInstance();
      //System.out.println("into else"+dateP.get(dateP.DATE)+"year "+dateP.get(dateP.YEAR)+"month"+dateP.get(dateP.HOUR)+"amorp"+dateP.get(dateP.AM_PM));
      try {
         if (cal==null) return "";
         day = cal.get(Calendar.DATE);
         month = cal.get(Calendar.MONTH);
         year = cal.get(Calendar.YEAR);
         //hours = cal.get(cal. 22-02-2000
         month++;

         if(month==0) month=12;
         strDay=day+"";
         strMonth=month+"";
         if(strDay.length()==1)
         {
           strDay="0"+strDay;
         }
         if(strMonth.length()==1)
         {
            strMonth="0"+strMonth;
         }

         rDate = strDay+"/"+strMonth+"/"+year;

     } catch (Exception ert) {return null;}
      return rDate;
   }

   public static String getDefaultExpDate()
     {
         System.out.println("in getTodaysDate() in DateHelp");
         int month = 7;
         int year = 1970;
         int hours = 0;
         int day = 0;
         int min = 0;
         String strDay="";
         String strMonth="";
         String rDate = "";

         try {

            GregorianCalendar cal = new GregorianCalendar();
            if (cal==null)
                  return "";
            cal.add(Calendar.DATE,1);// here adding 1 days ...modified on 06/09/2001 by seshu
            cal.add(Calendar.HOUR,-(cal.get(Calendar.ZONE_OFFSET)/(60*60*1000)));

            day = cal.get(Calendar.DATE);
            month = cal.get(Calendar.MONTH);
            year = cal.get(Calendar.YEAR);
            month++;

            if(month==0) month=12;
            strDay=day+"";
            strMonth=month+"";
            if(strDay.length()==1)
            {
              strDay="0"+strDay;
            }
            if(strMonth.length()==1)
            {
               strMonth="0"+strMonth;
            }

            rDate = strDay+"/"+strMonth+"/"+year;

        } catch (Exception ert) {return null;}
         return rDate;
   }




/**
  * This method is getting the Time<BR>
  * @return 	Time in the form of a String<BR>
  * Parameters  : none<BR>
  * @exception  none
 */
 public static String getTime()
   {

      int day = 1;
      int month = 7;
      int year = 1970;
      int hours = 0;
      int min = 0;
      String rTime = "";
      Calendar cal = Calendar.getInstance();
      try {
         if (cal==null) return "";
         hours = cal.get(Calendar.HOUR_OF_DAY);
         min = cal.get(Calendar.MINUTE);
         rTime = hours+":"+min;


      } catch (Exception ert) {return null;}

      return rTime;
   }
/**
  * This method is getting the todays Date and Time
  * @return		          String<BR>
  * Parameters            : none<BR>
  * Calling Methods       : getTodaysDate,getTime
  * @exception            none
  */
public static String getDateAndTime()
{
        String sDateAndTime="";
        sDateAndTime = getTodaysDate() + " " + getTime();
        return sDateAndTime;
}
/**
  * This method is helping to calculate all the Countries Date.
  * @return     the date as a String<BR>
  * Parameters  none<BR>
  * @exception  none
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
  * This method is helping to calculate all the Countries Time<BR>
  * @return    String containing the time in GMT<BR>
  * Parameters :	none<BR>
  * @exception none<BR>
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
 /**
   * This method is helping to calculate all the Countries Date and Time<BR>
   * @return  GMT Date and Time as a string<BR>
   * Parameters            : none<BR>
   * CallingMethods        : getGMTDate,getGMTTime<BR>
   * @exception            : none<BR>
   */
    public static String getGMTDateAndTime()
    {
        String sDateAndTime="";
        sDateAndTime = getGMTDate() + " " + getGMTTime();
        return sDateAndTime;
    }//end of getGMTDateAndTime method

 /**
   * This method is helping to set the DateFormat(dd/mm/yyyy)<BR>
   * @return               : date as a string in dd/mm/yyyy format<BR>
   * @param  strDate       the date as a String<BR>
   * @exception            : none<BR>
   */

   public static String getDateFormat(String strDate)
    {
                  String year="";
                  String month="";
          String date="";
                  if(!(strDate == null || strDate.equals("")))
          {
            year=strDate.substring(0,4);
            month=strDate.substring(5,7);
            date=strDate.substring(8);
            return date+"/"+month+"/"+year;
              }
              else
                return date;


    }//end of getDateFormat method

/**
  * This method is helping to set the DateFormat(dd/mm/yyyy) GMT<BR>
  * @return               String of the format dd/mm/yyyy GMT<BR>
  * @param  strDate       the date as a string<BR>
  * @exception            none
  */

    public static String getDateFormatWithGMT(String strDate)
            {
                          String year="";
                          String month="";
                  String date="";
                          if(!(strDate == null || strDate.equals("")))
                  {
                    year=strDate.substring(0,4);
                    month=strDate.substring(5,7);
                    date=strDate.substring(8);
                    return date+"/"+month+"/"+year+"  GMT";
                      }
                      else
                        return date;


    }//end of getDateFormatGMT method




/**
  * This method is helping to set the DateFormat in reverse(yyyy-mm-dd)<BR>
  * @return               String containing the date in the format year-month-date<BR>
  * @param strDate        String containing date in format dd/mm/yyyy<BR>
  * @exception            none
  */
    public static String getReverseDateFormat(String strDate)
            { // input date format should be of type ---> dd/mm/yyyy
                          String year="";
                          String month="";
                  String date="";
                          if(!(strDate == null || strDate.equals("")))
                  {
                    year=strDate.substring(6);
                    month=strDate.substring(3,5);
                    date=strDate.substring(0,2);
                    return year+"-"+month+"-"+date;
                      }
                      else
                        return date;
    }//end of getReverseDateFormat method
/**
  * This method is helping to set the DateAndTime Format in reverse(yyyy-mm-dd hrs).<BR>
  * @return               Date and Time of the formt year-month-date hours.<BR> 
  * @param  strDate       date as a string in the format dd/mm/yyyy hh:mm.<BR>
  * @exception            none
  */
    public static String getReverseDateAndTimeFormat(String strDate)
                    { // input date format should be of type ---> dd/mm/yyyy hh:mm
                    System.out.println("in getReverseDateAndTimeFormat in DateHelp  :"+strDate);
                                  String year="";
                                  String month="";
                          String date="";
                          String Hrs="";
                          if(!(strDate == null || strDate.equals("")))
                          {
                             Hrs = strDate.substring(11);
                             year=strDate.substring(6,10);
                             month=strDate.substring(3,5);
                             date=strDate.substring(0,2);
                             System.out.println("in getReverseDateAndTimeFormat in DateHelp  :"+year+"-"+month+"-"+date+" "+Hrs);
                             return year+"-"+month+"-"+date+" "+Hrs;
                          }
                          else
                                return date;
    }//end of getReverseDateAndTimeFormat method
/**
  * This method is helping to set the DateAndTime Format(dd/mm/yyyy hrs).<BR>
  * @return		String of the type dd/mm/yyyy hrs<BR>
  * @param	strDate	date as a string in the format dd/mm/yyyy hh:mm.<BR>
  * @Exception   none<BR>
  */
 public static String getDateTimeFormat(String strDate)
  {
           String year="";
           String month="";
           String date="";
           String hours="";
           if(!(strDate == null || strDate.equals("")))
           {
             year=strDate.substring(0,4);
             month=strDate.substring(5,7);
             date=strDate.substring(8,10);
             hours=strDate.substring(11,16);
             return date+"/"+month+"/"+year+"   "+hours;
               }
               else
               return date;

   }//getDateTimeFormat

 /**
   * This method is helping to set the DateAndTime Format(dd/mm/yyyy hrs) GMT<BR>
   * @return	String containing the date in date time format with GMT.<BR>
   * @param		strDate		Date as a string.<BR>
   * @exception none
   */

  public static String getDateTimeFormatWithGMT(String strDate)
  {
           String year="";
           String month="";
           String date="";
           String hours="";
           if(!(strDate == null || strDate.equals("")))
           {
             year=strDate.substring(0,4);
             month=strDate.substring(5,7);
             date=strDate.substring(8,10);
             hours=strDate.substring(11,16);
             return date+"/"+month+"/"+year+"   "+hours+ "  GMT" ;
               }
               else
               return date;

   }//getDateTimeFormatWithGMT




/**
  * This method is helping to get the FormattedDate based on parameter<BR>
  * @return               void<BR>
  * @param  strDate       date as a String<BR>
  * @exception            none<BR>
  */
   public void getFormattedDate(String strDate)
   {
      if(!(strDate == null || strDate.equals("")))
         {
               int intIndex=strDate.indexOf("/");
               String strDay=strDate.substring(0,intIndex);
               int intIndex1=strDate.lastIndexOf("/");
               String strMon=strDate.substring(intIndex+1,intIndex1);
               int intIndex3=strDate.indexOf(" ");
               String strYear=strDate.substring(intIndex1+1,intIndex3);
               StringTokenizer st = new StringTokenizer(strDate," ");
               st.nextElement();
               String strTemp=(String)st.nextElement();
               st=null;
               st = new StringTokenizer(strTemp,":");
               String strHrs=(String)st.nextElement();
               String strMin=(String)st.nextElement();
               iDay=new Integer(strDay).intValue();
               System.out.println("Step 1");
               iMon=new Integer(strMon).intValue();
               System.out.println("Step 2");
               iYear=new Integer(strYear).intValue();
               System.out.println("Step 3");
               iHrs=new Integer(strHrs).intValue();
               System.out.println("Step 4"+strMin);
               iMin=new Integer(strMin).intValue();
               System.out.println("Step 5");

               System.out.println("Day"+ iDay);
               System.out.println("Month"+ iMon);
               System.out.println("Year"+ iYear);
               System.out.println("Month"+iMon);
               System.out.println("iMin"+iMin);
               System.out.println("iHrs"+ iHrs);


          }//end of if statement
   }//getFormattedDate
/**
  * This method is helping to compare the FormattedDates<BR>
  * @return               false if the second date is after the first date and true if otherwise<BR>
  * @param  strDate1      first date<BR>
  * @param	strDate2	  second date<BR>
  * @exception            none
  */

 public boolean CompareDate(String strDate1,String strDate2)
  {     
        getFormattedDate(strDate1);
        GregorianCalendar  dt1=new GregorianCalendar (iYear,iMon,iDay,iHrs,iMin);
        getFormattedDate(strDate2);
        GregorianCalendar dt2=new GregorianCalendar (iYear,iMon,iDay,iHrs,iMin);
        if(dt2.after(dt1))
            return true;
        if(dt2.before(dt1))
            return false;
        return true;
  }//end of CompareDateMethod

 
 /**
   * Takes an input of the form days hrs:mts or -days hrs:mts and returns the date and time left<BR>
   * @return	A string containg the date and time left.<BR>
   * @param  sDate	String of the form days hrs:mts or -days hrs:mts<BR>
   * @exception	none
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

 /**
  * Takes an input of the form days hrs:mts or -days hrs:mts and returns the date and time left<BR>
  * @return		true if the entered date is greater than the current GMT date and false if otherwise.<BR>
  * @param  sDate	date in the form days hrs:mts or -days hrs:mts.<BR>
  * @exception	none<BR>
  */
 public boolean isValidDate(String sDate)
 { // method returns a true depending on whether the entered date is more than the current GMT date
         String sGmtDateAndTime = "";
         sGmtDateAndTime = getGMTDateAndTime();
         if(!(sDate == null || sDate.equals("")))
                return CompareDate(sGmtDateAndTime,sDate);
         else
                return false;

 }

/**
  * Takes a date and returns true when the entered date is in the format DD/MM/YYYY<BR>
  * @return		true when the entered date is in the format DD/MM/YYYY
  * @param	sDate	date in the form of a string.<BR>
  * @exception	none<BR>
  */

public static boolean isDateFormat(String sDate)
 { // method returns a true when the entered date is in the format DD/MM/YYYY

         if( sDate != null && !sDate.equals("") )
         {
                        if( sDate.trim().indexOf("/") == 2 && sDate.trim().lastIndexOf("/") == 5 )
                        return true;
                else
                        return false;
                 }
         else
                return false;

 }
 
 
/**
  * Takes a date and returns a true when the entered date is in the format DD/MM/YYYY<BR>
  * @return		true if the date is valid<BR>
  * @param day	day as integer<BR>
  * @param month	month as integer<BR>
  * @param year		year as integer<BR>
  * @exception	none<BR>
  */
public static boolean validateDate(int day, int month, int year)
 { 

        if (year>9999 || year <1000)
		{
			return false;
		}
		
		if ((month < 1 || month > 12) || (day < 1 || day > 31)) { // check month range 
			return false;
		} 
		if ((month==4 || month==6 || month==9 || month==11) && day==31) {
			return false;
		}
		
		if (month == 2) {
		
			// check for february 29th 
			if (year % 4 == 0 && (year % 100 != 0 || year % 400 == 0))
			{
				if (day>29 || (day==29)) 
				return false;
			}	
			
		}
		return true;
		
 }
	private static final String[] MONTHS={"January","February","March","April",
											"May","June","July","August","September",
											"October","November","December"};
											
	private static final int[] DAYS={31,28,31,30,31,30,31,31,30,31,30,31};
	
	public static final String getMonth(int month)
	{
		if(month<0 || month>MONTHS.length)
		{
			throw new ArrayIndexOutOfBoundsException("Invalid month. "+month);
		}
		
		return MONTHS[month];
	}

	public static boolean isDayAvlInMonth(int day,String month)
	{
		if(day<0 || day>31 || month==null || month.equals(""))
		{
			return false;
		}
		
		for(int i=0;i<MONTHS.length;i++)
		{
			if(MONTHS[i].equals(month))
			{
				if(DAYS[i]>=day)
				{
					return true;
				}
			}
		}

		return false;
	}
	
 
}
/**************************************
                End of class DateHelp
 **************************************/

