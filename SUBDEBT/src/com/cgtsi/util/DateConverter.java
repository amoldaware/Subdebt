package com.cgtsi.util;


import java.text.ParsePosition;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.apache.commons.beanutils.ConversionException;
import org.apache.commons.beanutils.Converter;

public final class DateConverter implements Converter {


    // ----------------------------------------------------------- Constructors


    /**
     * Create a {@link Converter} that will throw a {@link ConversionException}
     * if a conversion error occurs.
     */
    public DateConverter() {

        this.defaultValue = null;
        this.useDefault = false;

    }


    /**
     * Create a {@link Converter} that will return the specified default value
     * if a conversion error occurs.
     *
     * @param defaultValue The default value to be returned
     */
    public DateConverter(Object defaultValue) {

        this.defaultValue = defaultValue;
        this.useDefault = true;

    }


    // ----------------------------------------------------- Instance Variables


    /**
     * The default value specified to our Constructor, if any.
     */
    private Object defaultValue = null;


    /**
     * Should we return the default value on conversion errors?
     */
    private boolean useDefault = true;


    // --------------------------------------------------------- Public Methods


    /**
     * Convert the specified input object into an output object of the
     * specified type.
     *
     * @param type Data type to which this value should be converted
     * @param value The input value to be converted
     *
     * @exception ConversionException if conversion cannot be performed
     *  successfully
     */
    public Object convert(Class type, Object value) {

        if (value == null) {
            if (useDefault) {
                return (defaultValue);
            } else {
                throw new ConversionException("No value specified");
            }
        }

        if (value instanceof Date) {
			//System.out.println("Value is instance of date");
			return getCorrectDate((Date)value);
        }
        try {
            return (getDate(value.toString()));
        } catch (Exception e) {
            if (useDefault) {
                return (defaultValue);
            } else {
                throw new ConversionException(e);
            }
        }

    }

    private Date getCorrectDate(Date date)
    {
    	/*
		SimpleDateFormat dateFormat=new SimpleDateFormat("dd/MM/yyyy");

		String utilDate=dateFormat.format(date);

		System.out.println("formated date "+utilDate);

		CustomisedDate customDate=new CustomisedDate();
		customDate.setDate(utilDate);
		*/
		CustomisedDate customDate=new CustomisedDate(date.getTime());
		customDate.setDate(date);
		return customDate;

    }

	private Date getDate(String date)
	{
		Date returnDate=null;

		try
		{
			if(!isDateFormat(date))
			{
				StringDate strDate=new StringDate();
				strDate.setString(date);
				return strDate;
			}
			SimpleDateFormat dateFormat=new SimpleDateFormat("dd/MM/yyyy");

			Date utilDate=dateFormat.parse(date, new ParsePosition(0));
			
			CustomisedDate customDate=new CustomisedDate(utilDate.getTime());

			customDate.setDate(utilDate);
			returnDate=customDate;
			//System.out.println("formated date is "+customDate);
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}

		return returnDate;
	}
	public boolean isDateFormat(String sDate)
	 { // method returns a true when the entered date is in the format DD/MM/YYYY
		
			 if( sDate != null && !sDate.equals("") && sDate.trim().length()==10)
			 {
			 		String firstIndex=sDate.trim();
					if(firstIndex.indexOf("/") == 2 && firstIndex.lastIndexOf("/") == 5 )
					{
						try
						{
							int day=Integer.parseInt(firstIndex.substring(0,2));
							int month=Integer.parseInt(firstIndex.substring(3,5));
							int year=Integer.parseInt(firstIndex.substring(6,10));
							return validateDate(day,month,year);
						}
						catch(NumberFormatException exp)
						{
							return false;
						}
					}
					else
	 				{
						return false;
	 				}
			 }
			 else
			 {
				return false;
			 }
	 }
	private boolean validateDate(int day, int month, int year)
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
}