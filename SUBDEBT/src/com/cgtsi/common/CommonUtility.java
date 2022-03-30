//Source file: C:\\CGTSI\\Vss\\com\\cgtsi\\common\\CommonUtility.java

package com.cgtsi.common;
import java.util.ArrayList;
import java.io.*;
import java.sql.SQLException;


/**
 * This class is used to perform any common activity. This class is used across 
 * all modules.
 */
public class CommonUtility 
{
    CommonDAO commonDAO;
   /**
    * @roseuid 39BCC9260002
    */
   public CommonUtility() 
   {
	   commonDAO=new CommonDAO();    
   }
   
   /**
    * This method is used to update the audit remarks entered by the Auditor.
    * @param userId
    * @param remarks
    * @roseuid 3971A8AE0136
    */
   public void enterAuditDetails(String CGPAN,String userId, String remarks) throws DatabaseException,SQLException
   {
	   if ((userId!=null)&&(remarks!=null)){
		   commonDAO.enterAuditDetails(CGPAN,userId,remarks);
	   }		 
   }
   
   /**
    * This method is used to get all the audited details to the purview of the CGTSI 
    * user.
    * @return ArrayList
    * @roseuid 3971B18601CE
    */
    public AuditDetails viewAuditDetails(AuditDetails auditDetails) throws DatabaseException
   {		
	   AuditDetails reviewViewAuditDtls=commonDAO.viewAuditDetails(auditDetails);	   
		return reviewViewAuditDtls;
   }

    /**
	  * This method is used to get the audit details on press of the previous button
	  * on the screen.
	  * 24 NOV 2003
	  * @throws DatabaseException
	  * added by Ramesh rp14480
	  */
	  public AuditDetails viewRevAuditDetails(AuditDetails auditDetails) throws DatabaseException
	  {		
		  AuditDetails reviewViewAuditDtls=commonDAO.viewRevAuditDetails(auditDetails);	   
		   return reviewViewAuditDtls;
	  }
   
   /**
    * This method gets the uploaded file, decrypts and  checks the format of the file 
    * and forwards the contents of  the contents to the respective modules for 
    * further processing.
    * The return value is displayed to the user.
    * @param files
    * @return ArrayList
    * @roseuid 39728A620278
    */
   public ArrayList uploadFiles(File files)
   {
    return null;
   }
   
   /**
    * This method is used to update the CGTSI user comments for the auditor's remarks.
    * @param userID
    * @param reviewCmts
    * @roseuid 3972E5250377
    */
   public void updateAuditReview(String CGPAN,int auditId,String userID, String reviewCmts) throws DatabaseException
   {
	   if ((userID!=null)&&(reviewCmts!=null)){
		   commonDAO.updateAuditReview(CGPAN,auditId,userID,reviewCmts);
	   }
   }
   /**
	  * This method is used to get the auditor comments for the CGPAN entered.
	  * @param userID
	  * @param reviewCmts
	  * @roseuid 3972E5250377
	  */
	 public AuditDetails getAuditForCgpan(String CGPAN) throws DatabaseException
	 {	
	 	 AuditDetails auditDetails=null;
		 if (CGPAN!=null){
			 auditDetails=commonDAO.getAuditForCgpan(CGPAN);			
		 }
		return auditDetails;
	 }
   
   /**
    * This is a generic method used to generate reports.
    * @param reportId
    * @return boolean
    * @roseuid 3973EC35039B
    */
   public boolean generateReport(Integer reportId) 
   {
    return false;
   }
   
   /**
    * @return ArrayList
    * @roseuid 39AFCA9E0309
    */
   public ArrayList getAlerts() 
   {
    return null;
   }
   
   /**
    * This method is used to get all the zones available in the Zone master.
    * @return ArrayList
    * @roseuid 39B103B701B2
    */
   public ArrayList getAllZones() 
   {
    return null;
   }
   
   /**
    * This method is used to get all the regions available in the Region master.
    * @return ArrayList
    * @roseuid 39B1040900FC
    */
   public ArrayList getRegions() 
   {
    return null;
   }
   
   /**
    * This method is used to get all the industry sector available in the database.
    * @return ArrayList
    * @roseuid 39B1051001C2
    */
   public ArrayList getAllIndustrySector() 
   {
    return null;
   }
}
