/*
 * Created on Oct 22, 2003
 *
 * To change the template for this generated file go to
 * Window&gt;Preferences&gt;Java&gt;Code Generation&gt;Code and Comments
 */
package com.cgtsi.util;

import java.util.Date;
import java.text.SimpleDateFormat;

/**
 * @author VT8150
 *
 * To change the template for this generated type comment go to
 * Window&gt;Preferences&gt;Java&gt;Code Generation&gt;Code and Comments
 */
public class StringDate extends Date {
	private String str="";

	public String toString()
	{
		return str;
	}
	
	public void setString(String str)
	{
		this.str=str;
	}
  
  //Following Method is returning date and time. used to display the same in Reports
  // Added By Sudeep.Dhiman 24.11.2006
  public static String currentDateTime()
  {
      String SQL_DATE_FORMAT = "yyyy-MM-dd HH:mm:ss";
			SimpleDateFormat sqlDateFormat = null;
			SimpleDateFormat userDateFormat = new SimpleDateFormat("dd-MM-yyyy HH:mm:ss");
	//	SimpleDateFormat userDateFormatRetrieval = new SimpleDateFormat("dd-MM-yyyy");
			sqlDateFormat = new SimpleDateFormat(SQL_DATE_FORMAT);
      return userDateFormat.format(new Date());
  }
  

  
}
