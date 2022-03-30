//Source file: C:\\CGTSI\\Vss\\com\\cgtsi\\common\\Alerter.java

package com.cgtsi.common; 


import com.cgtsi.admin.User;
import java.util.ArrayList;


/**
 * This class is responsible for sending alerts to the concerned users at the 
 * appropriate time and the preset intervals.This class should run at a a 
 * predefined time in a day and check for the alerts to be displayed
 */
public class Alerter 
{
    CommonDAO commonDAO;
   /**
    * @roseuid 39B875CE0013
    */
   public Alerter() 
   {
     commonDAO=new CommonDAO();
   }
   
   /**
    * During day end process this method is invoked to check whether guarantee fee 
    * alert are to be sent or not.
    * This method gets all the guarantee fee defaulters and send alerts to them.
    * @return boolean
    * @roseuid 3973E3F40249
    */
   public void sendGuaranteeFeeAlerts() throws DatabaseException
   {
	   	//Get all Guarantee fee Defaulters.
	   	ArrayList defaulters=commonDAO.getAllGFDefaultList();
		if (defaulters!=null){
		   	//Store the size of Guarantee fee Defaulters.
		   	int NoOfDefaulters=defaulters.size();
		   	String message="Guarantee Fee pending";
		   	int messageType=0;
			//For every defaulter insert message into the database.
		   	for(int i=0;i<=NoOfDefaulters;i++){
		   		User defaultersObj=(User)defaulters.get(i);
				String userId=defaultersObj.getUserId();
				commonDAO.insertMessageForUser(userId,message,messageType);
		   	}
	  	}
   }
   
   /**
    * During day end process this method is invoked to check whether outstanding 
    * details alert are to be sent or not.
    * Members who are not entered outstanding details are called outstanding details 
    * defaulters.
    * This method gets all the outstanding details defaulters and send alerts to them.
    * @roseuid 3974069D0283
    */
   public void sendAlertForOsDefaulters()throws DatabaseException 
   {
		//Get all outstanding details defaulters.
		ArrayList defaulters=commonDAO.getAllOsDefaulters();
		if (defaulters!=null){
			//Store the size of Outstanding details Defaulters.
			int NoOfdefaulters=defaulters.size();
			String message="Outstanding details pending";
			int messageType=1;
			//For every defaulter insert message into the database.
			for(int i=0;i<=NoOfdefaulters;i++){
				User defaultersObj=(User)defaulters.get(i);
				String userId=defaultersObj.getUserId();
				commonDAO.insertMessageForUser(userId,message,messageType);
			}
		}
    
   }
   
   /**
    * During day end process this method is invoked to check whether periodic info 
    * alert are to be sent or not.
    * Members who are not entered periodic info details are called periodic info 
    * details defaulters.
    * This method gets all the periodic info details defaulters and send alerts to 
    * them.
    * @roseuid 3974120B0000
    */
   public void alertPeriodicInfoDefaulters()throws DatabaseException 
   {
		ArrayList defaulters=commonDAO.getPeriodicInfoDefaulters();
		if (defaulters!=null){
			int NoOfdefaulters=defaulters.size();
			String message="Periodic info pending";
			int messageType=2;
			//For every defaulter insert message into the database.
			for(int i=0;i<=NoOfdefaulters;i++){
				User defaultersObj=(User)defaulters.get(i);
				String userId=defaultersObj.getUserId();
				commonDAO.insertMessageForUser(userId,message,messageType);
			}
		}
   }
   
   /**
    * During day end process this method is invoked.
    * This method in turn invokes all other methods to check for guarantee fee 
    * alerts, outstanding detail alerts etc.
    * @roseuid 3983D5D503E2
    */
   public void sendAlerts()throws DatabaseException 
   {
    	//SendAlerts method calls the three methods on itself.
    	sendGuaranteeFeeAlerts();
		sendAlertForOsDefaulters();
		alertPeriodicInfoDefaulters();
   }
}
