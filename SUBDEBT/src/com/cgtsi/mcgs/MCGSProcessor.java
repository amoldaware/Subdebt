//Source file: C:\\CHECKOUT\\28OCT2003\\com\\cgtsi\\mcgs\\MCGSProcessor.java

package com.cgtsi.mcgs;

import java.util.ArrayList;

import com.cgtsi.registration.MLIInfo;
import com.cgtsi.application.BorrowerDetails;
import com.cgtsi.common.DatabaseException;
import com.cgtsi.common.Log;
import com.cgtsi.admin.NoOfUsersExceededException;
import com.cgtsi.admin.User;

import java.sql.Connection;

/**
 * This class manages all the MCGS related work flow. Common work flows such as 
 * application, claims, periodic info etc are managed by the individual 
 * sub-systems.
 */
public class MCGSProcessor 
{
   
   /**
    * @roseuid 3A1646E8015B
    */
   public MCGSProcessor() 
   {
    
   }
   
   /**
    * This method registers the MCGF with the CGTSI. All the member related data is 
    * captured and stored into database. A MCGF member id is generated and given to 
    * the user. The generated member id is returned.
    * @param memberInfo
    * @param creatingUser
    * @return String
    * @throws DatabaseException
    * @roseuid 39FBBF0801AB
    */
   public String registerMCGF(MLIInfo memberInfo, User creatingUser) throws DatabaseException 
   {
    return null;
   }
   
   /**
    * This method is used to add the NO of MCGF. The generated user id is returned.
    * @param noUser
    * @param creatingUser
    * @return String
    * @throws DatabaseException
    * @roseuid 39FBC8D70270
    */
   public String createNOUser(User noUser, User creatingUser) throws DatabaseException 
   {
    return null;
   }
   
   /**
    * This method creates users for the registered MCGF. Maximum three users are 
    * allowed. 
    * @param user
    * @param creatingUser
    * @throws DatabaseException
    * @throws NoOfUsersExceededException
    * @roseuid 39FBBF7400B6
    */
   public void createMCGFUser(User user, User creatingUser) throws DatabaseException, NoOfUsersExceededException 
   {
    
   }
   
   /**
    * This method adds the participating bank details into database.
    * @param participatingBank
    * @param creatingUser
    * @throws DatabaseException
    * @roseuid 39FBBF9103D9
    */
   public void addParticipatingBank(ParticipatingBank participatingBank, User creatingUser) throws DatabaseException 
   {
   		Log.log(Log.INFO,"MCGSProcessor","addParticipatingBank","Entered");
    	
    	MCGSDAO mcgsDAO=new MCGSDAO();
    	mcgsDAO.addParticipatingBank(participatingBank,creatingUser);
		
		Log.log(Log.INFO,"MCGSProcessor","addParticipatingBank","Exited");
   }
   
   /**
    * All the SSI members for an MCGF are entered and stored into database. Also, 
    * their contribution towards the MCGF is also captured. 
    * @param ssiMemberDtls
    * @param creatingUser
    * @throws DatabaseException
    * @roseuid 39FBBFC501FD
    */
   public void addSSIMembers(BorrowerDetails borrowerDetails,String memberId, User creatingUser) throws DatabaseException 
   {
		Log.log(Log.INFO,"MCGSProcessor","addSSIMembers","Entered");
		
    	MCGSDAO mcgsDAO=new MCGSDAO();
    	mcgsDAO.addSSIMembers(borrowerDetails,memberId,creatingUser);
    	
		Log.log(Log.INFO,"MCGSProcessor","addSSIMembers","Exited");
   }
   
   /**
    * If any donors contributed to MCGF's corpus, their details are captured along 
    * with their contribution. Donors are excluded from getting guarantee cover.
    * @param donorDtls
    * @param creatingUser
    * @throws DatabaseException
    * @roseuid 39FBBFED03A9
    */
   public void addDonorDetails(DonorDetail donorDtls, User creatingUser) throws DatabaseException 
   {
   		Log.log(Log.INFO,"MCGSProcessor","addDonorDetails","Entered");
     	
     	MCGSDAO mcgsDAO=new MCGSDAO();
     	mcgsDAO.addDonorDetails(donorDtls,creatingUser);
		
		Log.log(Log.INFO,"MCGSProcessor","addDonorDetails","Exited");
   }
   
   /**
    * This method sets the limit for participating bank. This is to avoid 
    * participating banks to corner most of the funds.
    * @param limit
    * @param creatingUser
    * @throws DatabaseException
    * @roseuid 39FBC02703AD
    */
   public void setLimitForPB(ParticipatingBankLimit limit, User creatingUser) throws DatabaseException 
   {
    
   }
   
   /**
    * This method is used to update the MCGF's status from performing to 
    * non-performaing. Means, MCGF has defaulted.
    * @param mcgfName
    * @param status
    * @param creatingUser
    * @throws DatabaseException
    * @roseuid 39FBC2CF0007
    */
   public void updateMCGFStatus(String mcgfId, String reason, User creatingUser) throws DatabaseException 
   {
		Log.log(Log.INFO,"MCGSProcessor","updateMCGFStatus","Entered");
     	
     	MCGSDAO mcgsDAO=new MCGSDAO();
     	mcgsDAO.updateMCGFStatus(mcgfId,reason,creatingUser);
		
		Log.log(Log.INFO,"MCGSProcessor","updateMCGFStatus","Exited");
   }
   
   public ArrayList getAllMCGFs() throws DatabaseException
   {
		Log.log(Log.INFO,"MCGSProcessor","getAllMCGFs","Entered");
	     	
		MCGSDAO mcgsDAO=new MCGSDAO();
		
		ArrayList mcgfs=mcgsDAO.getAllMCGFs();
		
		Log.log(Log.INFO,"MCGSProcessor","getAllMCGFs","Exited");
		
		return mcgfs;
		   		
   }
   
   public ArrayList getAllParticipatingBanks(String member) throws DatabaseException
   {
		Log.log(Log.INFO,"MCGSProcessor","getAllParticipatingBanks","Entered");
		     	
		MCGSDAO mcgsDAO=new MCGSDAO();
			
		ArrayList mcgfs=mcgsDAO.getAllParticipatingBanks(member);
			
		Log.log(Log.INFO,"MCGSProcessor","getAllParticipatingBanks","Exited");
			
		return mcgfs;   		
   }

   public void updateMCGSDetails(MCGFDetails mcgfDetails,String userId,Connection connection) throws DatabaseException
	{
	   Log.log(Log.INFO,"MCGSProcessor","updateMCGSDetails","Entered");

	   MCGSDAO mcgsDAO=new MCGSDAO();

	   mcgsDAO.updateMCGSDetails(mcgfDetails,userId,connection);

	   Log.log(Log.INFO,"MCGSProcessor","updateMCGSDetails","Exited");

	}

	public MCGFDetails getMcgsDetails(String appRefNo,Connection connection) throws DatabaseException
	{
	   Log.log(Log.INFO,"MCGSProcessor","getMcgsDetails","Entered");

	   MCGSDAO mcgsDAO=new MCGSDAO();

	   MCGFDetails mcgfDetails=mcgsDAO.getMcgsDetails(appRefNo,connection);

	   if (mcgfDetails==null)
	   {
		   mcgfDetails=null;
	   }

	   Log.log(Log.INFO,"MCGSProcessor","getMcgsDetails","Exited");

	   return mcgfDetails;

	}

	public MCGFDetails getOldMcgsDetails(String appRefNo,Connection connection) throws DatabaseException
	{
	   Log.log(Log.INFO,"MCGSProcessor","getOldMcgsDetails","Entered");

	   MCGSDAO mcgsDAO=new MCGSDAO();

	   MCGFDetails mcgfDetails=mcgsDAO.getOldMcgsDetails(appRefNo,connection);

	   if (mcgfDetails==null)
	   {
		   mcgfDetails=null;
	   }

	   Log.log(Log.INFO,"MCGSProcessor","getOldMcgsDetails","Exited");

	   return mcgfDetails;

	}

}
