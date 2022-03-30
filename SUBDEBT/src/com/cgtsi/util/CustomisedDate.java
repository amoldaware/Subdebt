package com.cgtsi.util;

import java.util.*;
import java.text.*;

public class CustomisedDate extends Date
{
	private Date date=null;

	public String toString()
	{
		SimpleDateFormat dateFormat=new SimpleDateFormat("dd/MM/yyyy");
		if(date!=null)
		{
			return dateFormat.format(date);
		}
		else
		{
			//System.out.println("to string method is called. returns null");
			return "";
		}
	}

	public void setDate(Date date)
	{
		this.date=date;
	}
	
	public CustomisedDate()
	{
		super();
	}
	
	public CustomisedDate(long time)
	{
		super(time);
	}	
}