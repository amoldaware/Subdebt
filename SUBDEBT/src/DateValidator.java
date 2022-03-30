

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.regex.*;
public class DateValidator{

	public static void main(String[] args) throws Exception {
		
		    Date parsedDate = null;
	        DateFormat df = new SimpleDateFormat("dd/MM/yyyy");
	        SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy");
	        
			String date1="03.12.2011";
			String regex = "^(3[01]|[12][0-9]|0[1-9])[/.-](1[0-2]|0[1-9])[/.-][0-9]{4}$";
			
			Pattern pattern = Pattern.compile(regex);		
		    Matcher matcher = pattern.matcher(date1);
		   if(matcher.matches()){
		    System.out.println(date1 +" : "+ matcher.matches()+":Regex valid date");		    
	        if (date1 != null && !date1.isEmpty()){
		        SimpleDateFormat[] formats =
		                new SimpleDateFormat[] {new SimpleDateFormat("dd-MM-yyyy"), new SimpleDateFormat("ddMMyyyy"), 
		                		new SimpleDateFormat("dd/MM/yyyy"), new SimpleDateFormat("dd.MM.yyyy")};
		        for (int i = 0; i < formats.length; i++){
		            try{
		                parsedDate = formats[i].parse(date1);
		                
		                System.out.println(parsedDate+" : parsedDate");
		                
		                String dateInString = df.format(parsedDate);
		                System.out.println(  dateInString +": formated Date" );
		                
		                Date date = formatter.parse(dateInString);
		    	        System.out.println(date+": date util");
		    	        System.out.println(formatter.format(date)+": after change date util");

		            }catch (ParseException e){	
		            	e.getMessage();
		                //continue;
		            }
		        }
		    }
		   }else{		    	
		    System.out.println(date1 +" : "+ matcher.matches()+": Invalid date");
		   }		
	}
}