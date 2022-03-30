package com.cgtsi.util;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

import com.cgtsi.actionform.WcValidateBean;
import com.cgtsi.common.Log;
import com.cgtsi.common.MessageException;

public class ApplicationRenewValidator {
	public static int diffMonth = 0;
	public static int diffDay = 0;  	
	public static int validateRenewal(WcValidateBean wcValidateBean) throws MessageException{ 
		Log.log(4, "ApplicationRenewValidator", "validateRenewal", "validateRenewal().. Entered");
		SimpleDateFormat formatter= new SimpleDateFormat("dd/MM/yyyy");	
		try{
	    Date startdate = formatter.parse(wcValidateBean.getAppGurStartDt());
	    Date enddate   = formatter.parse(wcValidateBean.getAppExpDt());
	    Calendar startCalendar = new GregorianCalendar();
	    startCalendar.setTime(startdate);
	    Calendar endCalendar = new GregorianCalendar();
	    endCalendar.setTime(enddate);
		
	    
	    int monthCount = 0;
	    int firstDayInFirstMonth = startCalendar.get(Calendar.DAY_OF_MONTH);
	    startCalendar.set(Calendar.DAY_OF_MONTH, 1);
	    endCalendar.add(Calendar.DAY_OF_YEAR, -firstDayInFirstMonth+1);
	    while (!startCalendar.after(endCalendar)) {     
	        startCalendar.add(Calendar.MONTH, 1);
	        ++monthCount;
	    }
	    startCalendar.add(Calendar.MONTH, -1); --monthCount;
	    int remainingDays = 0;
	    while (!startCalendar.after(endCalendar)) {
	        startCalendar.add(Calendar.DAY_OF_YEAR, 1);
	        ++remainingDays;
	    }
	    startCalendar.add(Calendar.DAY_OF_YEAR, -1);
	    --remainingDays;
	    int lastMonthMaxDays = endCalendar.getActualMaximum(Calendar.DAY_OF_MONTH);
	    if (remainingDays >= lastMonthMaxDays) {
	        ++monthCount;
	        remainingDays -= lastMonthMaxDays;
	    }
	     diffMonth = monthCount; 
	     diffDay = remainingDays;
         }catch(ParseException e){
			throw new MessageException("Unable to parse CGPAN updation date.");
		}
	     String cgpan = wcValidateBean.getCgpan();
	     String status = wcValidateBean.getStatus();
	     
		 String fstCgpanRenw = cgpan.substring(0, 2);
		 String lstCgpanRenw = ""+ cgpan.charAt(cgpan.length()-2) + cgpan.charAt(cgpan.length()-1);			 
		   
		 System.out.println("fstCgpanRenw-->" + fstCgpanRenw+ "......." + "lstCgpanRenw-->" + lstCgpanRenw); 
		 
		    int remainExpMonth = 0;  
		 //	if(lstCgpanRenw.equals("WC") || lstCgpanRenw.equals("TC")||lstCgpanRenw.equals("R1")){
		 		if(lstCgpanRenw.equals("TC") || wcValidateBean.getAppLoanType().equals("CC")){
		 		  if( 0 < diffMonth & diffMonth < 120){		 			
				   remainExpMonth = 120 - diffMonth;		
				   System.out.println("TC................remainExpMonth>>>"+ remainExpMonth);
				   return remainExpMonth;
		 		  } 
		 	     }
		 		
                 if((lstCgpanRenw.equals("WC") || lstCgpanRenw.equals("R1")|| lstCgpanRenw.equals("R2")|| lstCgpanRenw.equals("R3") || lstCgpanRenw.equals("R4")) && status.equals("EX")){		 		    
		 		   	 if(diffMonth == 120 && diffDay == 0){					    
		 		    	throw new MessageException("Application's gurentee is going to completed"); 
					    }else if( 0 < diffMonth && diffMonth < 120){	
					    	 remainExpMonth = 120 - diffMonth;					    	
					    }else{					    	
					    	throw new MessageException("Application is not further eligible for renewal any more."); 
					    }
		 		 }else{
			 			  throw new MessageException("Application is not further eligible for renewal.."); 
			 		 }
				return remainExpMonth;
		 	    	 
		 	    	 /* if(lstCgpanRenw.equals("WC") && status.equals("EX")){		 		    
		 		   //  if(diffMonth == 84 && diffDay == 0){
		 	    	 if(diffMonth == 84 && diffDay == 0){
					    	//dmsg = "Application's gurentee is going to complet";
		 		    	throw new MessageException("Application's gurentee is going to complet"); 
					    }else if( 0 < diffMonth & diffMonth < 84){
					    	//dmsg= "VALID";//"Application is in under Gurentee date";
					    }else{					    	
					    	throw new MessageException("Application is not further eligible for renewal any more."); 
					    }
		 		 }
		 	     else if(lstCgpanRenw.equals("R1") && status.equals("EX")){		 		     
			 		     if(diffMonth == 36 && diffDay == 0){
			 		         	//dmsg = "Application's Renewal gurentee is going to complet";
			 		    	throw new MessageException("Application's gurentee is going to complet"); 
							  }else if( 0 < diffMonth & diffMonth < 36){
								// dmsg = "VALID"; //"Renewal application's is in under Gurentee date";
							  }else{
								  throw new MessageException("Oops!.. Gurentee application is not further eligible for renewal.");
						    }
			 		 }else{
			 			  throw new MessageException("Application is not further eligible for renewal.."); 
			 		 }*/
		 	/*	}else{
		 			throw new MessageException(lstCgpanRenw +" Application is not eligible for renewal.."); 
		 		}*/
 	}
}
