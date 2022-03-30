package com.cgtsi.application;

import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;

import com.cgtsi.admin.Administrator;

public class ExcelTest {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		//readExcelData();
		
		industrySectorIssue();

	}
	
	public static void industrySectorIssue()
	{
		try
		{
			Administrator admin = new Administrator();
			ArrayList industrySectors = new ArrayList();
			industrySectors = admin.getIndustrySectors("SSSBE");
			
			//admin.getIndustrySectors("SSSBE");
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
	}

	public static void readExcelData()
	{
		try
		{			
			String filePath = "D:/ExcelFormatToUploadApplications (4).xls";
			InputStream is = new FileInputStream(new File(filePath));
			//File file = new File(filePath);		
			HSSFWorkbook book;
			book = new HSSFWorkbook(is);
			HSSFSheet sheet = book.getSheetAt(0);			
			Iterator rowItr = sheet.iterator();
			
			while (rowItr.hasNext())
			{				
				HSSFRow row = (HSSFRow) rowItr.next();				
				HSSFCell celVal[] = new HSSFCell[120];
				//System.out.println("array list : "+celVal.length);
				int i=0;
				for (int k = 0; k < 40; k++) {
					HSSFCell cellV = row.getCell(k) != null ? row.getCell(k): null;
					celVal[k] = cellV != null ? cellV : null;
					if(k==32 )
					{
						//System.out.println("I : "+i);
					
					String dateSapmple=celVal[32].toString();
					System.out.println(dateSapmple.length()+"array list : "+celVal[32]);
					
					System.out.println(dateSapmple+"method val"+formatDateCell(dateSapmple));
					
//					if(dateSapmple.length() ==12)
//					{
//						Date date = null;
//						SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
//						//String str = sdf.format(dateSapmple.replace("\"", ""));
//						if(dateSapmple.startsWith("\""))
//						{
//							System.out.println("dateSapmple list : "+dateSapmple.replace("\"", ""));
//						}
//					}
//					else if(dateSapmple.length() == 11)
//					{
//						System.out.println("dateSapmple list :1 "+dateSapmple.replace("\"", ""));
//					}
					
						if(dateSapmple.startsWith("\""))
						{
							dateSapmple=dateSapmple.replace("\"", "");
						}
						
						Map months= new HashMap();
						months.put( "Jan","1");						
						months.put("Feb","2");						
						months.put("Mar","3");						
						months.put("Apr","4");						
						months.put("May","5");						
						months.put("Jun","6");						
						months.put("Jul","7");						
						months.put("Aug","8");						
						months.put("Sep","9");						
						months.put("Oct","10");						
						months.put("Nov","11");						
						months.put("Dec","12");
						
//						
					
						System.out.println("dateSapmple list =: "+dateSapmple.split("-").length);
					
						if(dateSapmple.contains("-") && dateSapmple.split("-").length ==3)
						{
							String splitedStr[] =dateSapmple.split("-");
							//System.out.println("dateSapmple list splitedStr: "+splitedStr[1]);
							if(months.containsKey(splitedStr[1]))
							{
								dateSapmple=dateSapmple.replaceAll(splitedStr[1], months.get(splitedStr[1]).toString());
								String[] dateStr=dateSapmple.split("-");
								dateSapmple=dateStr[1].concat("/").concat(dateStr[0].concat("/").concat(dateStr[2]));
							//	System.out.println(months.get(splitedStr[1])+"dateSapmple format in month format : "+dateSapmple);
							}
							
						}
						
						boolean flag=true;
						Date date = null;
						SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
						try {
							sdf.setLenient(false);
							date = sdf.parse(dateSapmple);
						//	System.out.println("date "+date);
						} catch (ParseException e) {
							flag=false;
							System.out.println(dateSapmple+"date exception"+e.getMessage());
						}
						System.out.println("flag "+flag);
						if(flag==true && dateSapmple.length() >1)
						{
							System.out.println("date is valid"+dateSapmple);
							 SimpleDateFormat format1 = new SimpleDateFormat("dd/MM/yyyy");
							    
							    Date date2 = format1.parse(dateSapmple);
							    
							    
							    
							    
								
							
									String str = sdf.format(date2);
									try {
										date = sdf.parse(str);
										 System.out.println("===="+date);
									} catch (ParseException e) {
										e.printStackTrace();
									}
								
							
						} else if(flag==false && dateSapmple.length() >1)
						{
							//System.out.println("date is Invalid"+dateSapmple);
						}
						
					}
					
					
					//i++;
				}
			}
			//System.out.println("end :");
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
	}
	
	public static Date formatDateCell(String dateValue)
	{
		Date date=null;
		String dateSapmple=dateValue;
		Map months= new HashMap();
		months.put( "Jan","1");						
		months.put("Feb","2");						
		months.put("Mar","3");						
		months.put("Apr","4");						
		months.put("May","5");						
		months.put("Jun","6");						
		months.put("Jul","7");						
		months.put("Aug","8");						
		months.put("Sep","9");						
		months.put("Oct","10");						
		months.put("Nov","11");						
		months.put("Dec","12");
		try
		{
			
			if(dateValue!=null && dateValue.length() > 7)
			{
					if(dateSapmple.startsWith("\""))
					{
						dateSapmple=dateSapmple.replace("\"", "");
					}
					
				
					if(dateSapmple.contains("-") && dateSapmple.split("-").length ==3)
					{
						String splitedStr[] =dateSapmple.split("-");				
						if(months.containsKey(splitedStr[1]))
						{
							dateSapmple=dateSapmple.replaceAll(splitedStr[1], months.get(splitedStr[1]).toString());
							String[] dateStr=dateSapmple.split("-");
							dateSapmple=dateStr[1].concat("/").concat(dateStr[0].concat("/").concat(dateStr[2]));
						
						}
						
					}
				
					SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
					try 
					{
						sdf.setLenient(false);
						date = sdf.parse(dateSapmple);			
					} catch (ParseException e) 
					{
						date=null;
					}
							

			}
		}
		catch(Exception e)
		{
			
		}
		return date;
	}
	
	
}
