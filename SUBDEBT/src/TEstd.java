import java.io.FileReader;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Properties;

import org.apache.struts.action.ActionError;

import com.cgtsi.util.PropertyLoader;

public class TEstd {

	/**
	 * @param args
	 * @throws ParseException 
	 */
	    



	public static void main(String[] args) throws ParseException {
		 Date seltoday = new Date();                   
		 // Date myDate = new Date(today.getYear(),today.getMonth()-1,today.getDay());
		 Date myDate = new Date(2018,12,01);
		  System.out.println("My Date is "+myDate);    
		  System.out.println("seltoday Date is "+seltoday+"\n");
		  if (seltoday.compareTo(myDate)<=0)
		      System.out.println(seltoday+ " seltoday Date is Greater  than my Date "+myDate);
		  else if (seltoday.compareTo(myDate)>0)
		      System.out.println(seltoday+ " seltoday Date is Less than my date "+myDate); 
		  /*else 
		      System.out.println("Both Dates are equal"); */
		
		  try{
			   
			          
			    int promContribution=-1;          
			    String promGAssoNPA1YrFlg="";    
			    int promBussExpYr=0;             
			      
			  
			 /* if ( cibilFirmMsmeRank < 0 || cibilFirmMsmeRank > 11) {
					System.out.println("Value OK -ERROR-1"+cibilFirmMsmeRank);
				} else {System.out.println("Value--55 False 0"+cibilFirmMsmeRank); }
			  
			  
			 if (expCommerScor != 0 && (expCommerScor < 300 || expCommerScor > 900)) {
					//System.out.println("  immovColSecurityAmt...... "+immovColSecurityAmt);
					System.out.println("Error");
					
					
				}else {System.out.println("Valid value"); }
			   */
			  if (promContribution < 0 || promContribution > 100) {
					System.out.println("Value promContribution Error 4");
					
				}else {System.out.println("Value promContribution Valid 4"); } 
			  
			  
			  
			  if (promGAssoNPA1YrFlg == "" || promGAssoNPA1YrFlg == null) {
					System.out.println("Value Error 5");
				} else {System.out.println("Value Valid 4"); } 
			  
			  
			  if (promBussExpYr > -1 && promBussExpYr  > 100) {
					System.out.println("Value Error 6");
				} else {System.out.println("Value Valid 5"); } 
				
	            // If you already have date objects then skip 1

	            //1
	            // Create 2 dates starts
	           /* SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
	            Date date1 = sdf.parse(d1);
	            Date date2 = sdf.parse(d2);

	            System.out.println("Date1"+sdf.format(date1));
	            System.out.println("Date2"+sdf.format(date2));System.out.println();

	            // Create 2 dates ends
	            //1

	            // Date object is having 3 methods namely after,before and equals for comparing
	            // after() will return true if and only if date1 is after date 2
	            if(date1.after(date2)){
	                System.out.println("Date1 is after Date2");
	            }
	            // before() will return true if and only if date1 is before date2
	            if(date1.before(date2)){
	                System.out.println("Date1 is before Date2");
	            }

	            //equals() returns true if both the dates are equal
	            if(date1.equals(date2)){
	                System.out.println("Date1 is equal Date2");
	            }
*/
	            System.out.println();
	        }
		  catch(Exception ex){
	        //catch(ParseException ex){
	            ex.printStackTrace();
	        }
		
		// TODO Auto-generated method stub
	/*	String name = "HELLO HOW R U"+ "~" + "Deepak";
		System.out.println(name);*/
		//String dd =  "234234s30.00";
		//dd = String.valueOf(dd).split("\\.")[0];
		//System.out.println(dd);
		/*int p=name.lastIndexOf("~");
		String e=name.substring(p+1);
		System.out.println(e+"..............");*/
		//Date psDate2= new Date("2009-03-30");
		 // String collateralSenDate = PropertyLoader.getValue("crollateralSenDate"); 
         
		//  String collateralSenDatd = Properties.class.getValue("crollateralSenDate");  
	//	  System.out.println("Action>>>>>>>>>collateralSenDate>-------------------->>"+collateralSenDate);
			
	//	SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
     //   Date date1 = sdf.parse("2018-12-31");
       // Date date2 = sdf.parse("2010-01-31");
//
      /*  System.out.println("date1 : " + sdf.format(date1));
        System.out.println("date2 : " + sdf.format(date2));

        if (date1.compareTo(date2) > 0) {
            System.out.println("Date1 is after Date2"+true);
        } else if (date1.compareTo(date2) < 0) {
            System.out.println("Date1 is before Date2");
        } else if (date1.compareTo(date2) == 0) {
            System.out.println("Date1 is equal to Date2");
        }
        
        Date sentDate = new Date("31-Mar-2018");
    	
    	System.out.println("Check :"+sentDate.after(new Date("31-Mar-2018")));*/
    
      /*  String pattern = "dd-MM-yyyy"; // 18-05-2018
        DateFormat simpleDateFormat = new SimpleDateFormat(pattern);
        java.sql.Date date = (java.sql.Date) simpleDateFormat.parse("01-18-2018");
        System.out.println(date);*/
       /* SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd/MM/yyyy");
        java.util.Date utilDate = new java.util.Date("21/05/2018");
        java.sql.Date sqlDate = new java.sql.Date(utilDate.getTime());
                                
        System.out.println("utilDate:" + utilDate);
        System.out.println("sqlDate:" + sqlDate);
                SimpleDateFormat dmyFormat = new SimpleDateFormat("dd/MM/yyyy");

        String dmy = dmyFormat.format(utilDate.getTime());
        System.out.println("dmy:" + dmy);*/
        /*
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd/MM/yyyy");
        
        java.util.Date utilDate = new java.util.Date("19/05/2018");
        java.sql.Date sqlDate = new java.sql.Date(utilDate.getTime());
                                
        System.out.println("utilDate:" + utilDate);
        System.out.println("sqlDate:" + sqlDate);
        
                SimpleDateFormat dmyFormat = new SimpleDateFormat("DD-MON-YY");

        String dmy = dmyFormat.format(sqlDate.getTime());
        System.out.println("\t dmy:" + dmy);
        */
        //---------------------------------------------------------------
        /*Date today = new Date();
        SimpleDateFormat DATE_FORMAT = new SimpleDateFormat("dd-MM-yyyy");
        String date = DATE_FORMAT.format(today);
       
        DATE_FORMAT = new SimpleDateFormat("dd/MMM/yy");
        date = DATE_FORMAT.format(today);
        System.out.println("Today in dd/MM/yy pattern : " + date);     */  
    }  
	
	
			
			
			
			
	
	
	
	
}
