//Source file: C:\\CGTSI\\Vss\\com\\cgtsi\\registration\\Registration.java

package com.cgtsi.registration;
import java.util.ArrayList;
import java.util.List;

import com.cgtsi.admin.DuplicateException;
import com.cgtsi.admin.User;
import com.cgtsi.admin.Administrator;
//import com.cgtsi.admin.Message;
import com.cgtsi.common.DatabaseException;
import com.cgtsi.common.MailerException;
import com.cgtsi.common.Mailer;
import com.cgtsi.common.NoUserFoundException;

import org.apache.commons.logging.LogFactory;
import com.cgtsi.common.Log;
import org.apache.commons.beanutils.BeanUtils;

import java.sql.SQLException;



/**
 * This class holds the methods that interact with the CollectingBank and MLIInfo
 * classes for its operations.
 */
public class Registration
{
   DuplicateException duplicateException;
   RegistrationDAO regDAO;
   /**
    * @roseuid 39B3A3D30174
    */
   public Registration()
   {
	   regDAO=new RegistrationDAO();
	 
   }

   //This method is called to generate member Id before calling register MLI method.
   //12,NOV,2003
   //Ramesh rp14480
   /*
   public String getMemberId(MLIInfo mliDetails)throws DatabaseException
   {
   		String memberId=null;
   		if (mliDetails!=null){
   		 	memberId=regDAO.getMemberId(mliDetails);
   		}
  		return memberId;
   }
*/

   /**
    * This method is to register a lending institution and become a MLI associated
    * with CGTSI. The method takes an object of MLIInfo as the argument and passes
    * this object to the addMLIDetails that interacts with the database and adds this
    * MLI info in the database.
    * @param mliDetails - This argument is the MLI object that comes from the screen
    * with the details of the lending institution.
    * @return boolean
    * @roseuid 397817DC039C
    */
   public String registerMLI(MLIInfo mliDetails,String createdBy) throws DuplicateException,DatabaseException
   {
	   	String memberId=null;

		if (mliDetails!=null){
			memberId=regDAO.addMLIDetails(mliDetails,createdBy); 	//return the mliID for the mli object passed
	   }

	   return memberId;

   }
   
   public String registerBranchMLI(MLIInfo mliDetails,String createdBy) throws DuplicateException,DatabaseException
   {
	   	String memberId=null;

		if (mliDetails!=null){
			memberId=regDAO.addMLIBranchDetails(mliDetails,createdBy); 	//return the mliID for the mli object passed
	   }

	   return memberId;

   }

   /**
    * This method is to register a bank as the Collecting Bank with CGTSI. The method
    * takes an object of CollectingBank as argument. The method passes this object to
    * the addCollectingBankDetails in the DbClass which adds the details in the
    * database.
    * @param collectingBank - This argument is of type CollectingBank. The object is
    * formed from the screen inputs and is populated with the details.
    * @return boolean
    * @roseuid 397817DC03A7
    */
   public boolean registerCollectingBank(CollectingBank collectingBank,String createdBy) throws DuplicateException,DatabaseException,SQLException
   {
		boolean returnAddDB=false;
		if (collectingBank!=null){
		   returnAddDB=regDAO.addCollectingBankDetails(collectingBank,createdBy);

		   if (returnAddDB==false) {
			   throw new DuplicateException("Duplicate Exception");
		   } else  {
		   		returnAddDB=true;
		   }
		}

    return returnAddDB;
   }

   /**
    * This method is to get the Collecting Bank Ids and Names for display, when
    * entering the Organisation Structure. This method in turn calls the
    * getCollectingBanks method of the DbClass which fetches the respective details
    * from the database.
    * @return Collections
    * @roseuid 397817DC03A9
    */
   public ArrayList getCollectingBanks() throws DatabaseException
   {

		ArrayList returnCbBanks=regDAO.getCollectingBanks();
			 return returnCbBanks;
   }

   /**
    * This method is to display the zones of a particular MLI, when entering the
    * Organisation Structure. This method in turn calls the getZones method of the
    * DbClass which fetches the respective details from the database.
    * @param bankId
    * @return Collections
    * @roseuid 397817DC03AA
    */
   public ArrayList getZones(String bankId) throws DatabaseException
   {
	   ArrayList zones=null;

	   if ((bankId!=null)){
			 zones=regDAO.getZones(bankId);
		}
		return zones;
   }

   /**
    * This method is invoked when the user chooses to modify the details of an
    * existing collecting bank. It takes an object of type CollectingBank and passes
    * this object to the modifyCollectingBank of the DbClass.
    * @param memberId - This argument gives the details of the collecting bank as
    * modified by the user.
    * @param cbName
    * @roseuid 397817DC03AC
    */
   public void modifyCollectingBank(MLIInfo mliInfo) throws DatabaseException
   {
	   if(mliInfo!=null)
	   {
		   regDAO.modifyCollectingBank(mliInfo);
	   }
   }

   /**
    * This method is used to get all the collecting bank information for the given
    * collecting bank name.
    * @param cbName
    * @return registration.CollectingBank
    * @roseuid 39781F68021E
    */
   public CollectingBank viewCollectingBank(String cbName,String branchName) throws DatabaseException
   {
	    CollectingBank returncollectingBankDtls=null;

	   if (cbName!=null){
			returncollectingBankDtls=regDAO.getCollectingBankDtls(cbName,branchName);
	   }
		return returncollectingBankDtls;

   }

   /**
    * This method is used to get all the branches of the given bank id and zone id.
    * @param bankId
    * @param zoneId
    * @return ArrayList
    * @roseuid 399E55100315
    * ----------------------------------------------------------
    * {N:B the parameter zone id was removed....ramesh rp14480.
    * ----------------------------------------------------------
    */
   public ArrayList getAllBranches(String bankId) throws DatabaseException
   {
		ArrayList allBranches=null;

		if ((bankId!=null)){

			 allBranches=regDAO.getAllBranches(bankId);
		}
	   return allBranches;
   }

   /**
    * This method is used to get all the regions of the given bank id.
    * @param bankId
    * @return ArrayList
    * @roseuid 399E55400274
    */
	 public ArrayList getAllRegions(String bankId)
   {
    return null;
   }

   /**
    * This method is used to retrieve all the MLIs from the database.
    * @return ArrayList
    * @roseuid 399E572203A3
    */
   public ArrayList getAllMLIs() throws DatabaseException
   {
		 ArrayList allMLIs=regDAO.getAllMLIs();
		 return allMLIs;
   }

  /**
    * This method is used to get all the members (HO,ZO,RO, and BO).
    * @return ArrayList
    * @roseuid 399E5C8101A2
    */
   public ArrayList getAllMembers() throws DatabaseException
   {
	   ArrayList allMembers=regDAO.getAllMembers();
		return allMembers;
   }
   /**
   * 
   * @return 
   * @throws com.cgtsi.common.DatabaseException
   */
    public ArrayList getAllMembersNew() throws DatabaseException
   {
	   ArrayList allMembers=regDAO.getAllMembersNew();
		return allMembers;
   }
   /**
   * 
   * @return 
   * @throws com.cgtsi.common.DatabaseException
   */
    public ArrayList getAllHOMembers() throws DatabaseException
   {
	   ArrayList allMembers=regDAO.getAllHOMembers();
		return allMembers;
   }
   //Returns an arraylist of all members who have been assigned collectingBank.
   public ArrayList getMembersWithCb() throws DatabaseException
	  {
		  ArrayList allMembers=regDAO.getMembersWithCb();
		   return allMembers;
	  }

   /**
    * This method is used to create the NO of HO after registering the MLI. User
    * creation will happen in admin module.
    * @param noUserInfo
    * @roseuid 399E68710322
    */
   public String createNO(String createdBy,User noUserInfo,boolean sendEmail) throws MailerException,DatabaseException,NoUserFoundException
   {
	   String noUserId=null;
	   if (noUserInfo!=null){
		   //String subj="MLI Id";
		   //String emailmessage=null;
		   //boolean sendmail=false;

		   Administrator admin=new Administrator();		   
		   ArrayList to=new ArrayList();
		   ArrayList cc=null;
		   ArrayList bcc=null;
		   Mailer mail=new Mailer();
		   MailerException mailerException=new MailerException();

		   noUserId=admin.createNOUser(createdBy,noUserInfo,sendEmail);				//create NO
		   /*String returnMLIID=regDAO.allotMLIId(); 		//retrieve the mliId
		   String email=mliInfo.getEmailId();

		   if (email==null){
			   return;
		   }
		   to.add(email);
		   Message message=new Message(to,null,null,subj,emailmessage);
		   sendmail=mail.sendEmail(message);			//send an email to the NO giving him the MLI ID
		   if (sendmail==false){
			   throw mailerException;
		   }*/
	   }

		return noUserId;
   }

   /**
    * This method returns the collecting bank assigned to the given member id.
    * @param memberId
    * @return com.cgtsi.registration.CollectingBank
    * @roseuid 399E6C3803E1
    */
   public CollectingBank getCollectingBank(String memberId) throws DatabaseException
   {
	   CollectingBank returnCollectingBank=null;	 
	   if ((memberId!=null)){
			returnCollectingBank=regDAO.getCollectingBank(memberId);
	   }
		return returnCollectingBank;
   }
   
/**
   * 
   * @param memberId
   * @return 
   * @throws com.cgtsi.common.DatabaseException
   */
public double getPresentExposureLimit(String bankId) throws DatabaseException
   {
	   double exposureLimit=0;	 
	   if ((bankId!=null)){
     
			exposureLimit=regDAO.getExposureLimit(bankId);
	   }
		return exposureLimit;
   }
   /**
    * This method returns an ArrayList of member ids whom are not assigned with any
    * collecting bank
    * @return ArrayList
    * @roseuid 399E6DBF005E
    */
   public ArrayList getCBUnassignedMembers() throws DatabaseException
   {
		 ArrayList returnCbMembers=regDAO.getCBUnassignedMembers();
	 	return returnCbMembers;
   }

   /**
    * This method is used to assign a collecting bank for the selected member ids.
    * @param memberIds
    * @param cbName
    * @roseuid 399E6ED501E4
    */
   public void assignCollectingBank(String memberId, String cbName,String createdBy) throws DatabaseException
   {
	   if ((memberId!=null)&&(cbName!=null)&&(createdBy!=null)){
		   regDAO.assignCollectingBank(memberId,cbName,createdBy);
	   }
   }

/**
   * 
   * @param memberId
   * @param cbName
   * @param createdBy
   * @throws com.cgtsi.common.DatabaseException
   */
  public void assignNewExposureLimit(String bankId, double newExposureLimit) throws DatabaseException
   {
	   if (bankId!=null){
		   regDAO.assignNewExposureLimit(bankId,newExposureLimit);
	   }
   }
   /**
    * This method update the modified collecting bank details into database.
    * @param collectingBank
    * @roseuid 399E837B0221
    */
   public void updateCollectingBankDtls(CollectingBank collectingBank,String createdBy) throws DatabaseException
   {
	   if (collectingBank!=null) {
			regDAO.updateCollectingBank(collectingBank,createdBy);
	   }

   }
   
   /**
   * 
   * added by sukumar@path on 11-jun-2010 for updating remote user login information
   * @param bnkId
   * @param zneId
   * @param brnId
   * @param ipAddress
   * @param remoteHost
   * @param remoteHeader
   * @param sessionId
   * @param createdBy
   * @throws com.cgtsi.common.DatabaseException
   */
   
   public void updateLoginInformation(String bnkId,String zneId,String brnId,
                                      String ipAddress,String hostName,String proxyName,
                                      String sessionId,String createdBy) throws DatabaseException
   {
    Log.log(Log.INFO,"Registration","updateLoginInformation","Entered");
	   try
          {
           regDAO.updateLoginInformation(bnkId,zneId,brnId,ipAddress, hostName, proxyName,sessionId, createdBy);
          }
          catch(Exception exception)
          {
            throw new DatabaseException(exception.getMessage());
          }
          
	    Log.log(Log.INFO,"Registration","updateLoginInformation","Exited");

   }
  
  /**
   * 
   * @param bnkId
   * @param zneId
   * @param brnId
   * @param ipAddress
   * @param hostName
   * @param proxyName
   * @param sessionId
   * @param createdBy
   * @throws com.cgtsi.common.DatabaseException
   */
    public void updateLogoutInformation(String bnkId,String zneId,String brnId,
                                      String ipAddress,String hostName,String proxyName,
                                      String sessionId,String createdBy) throws DatabaseException
   {
    Log.log(Log.INFO,"Registration","updateLogoutInformation","Entered");
	   try
          {
           regDAO.updateLogoutInformation(bnkId,zneId,brnId,ipAddress, hostName, proxyName,sessionId, createdBy);
          }
          catch(Exception exception)
          {
            throw new DatabaseException(exception.getMessage());
          }
          
	    Log.log(Log.INFO,"Registration","updateLogoutInformation","Exited");

   }
   

   /**
    * This method is used to add a member into database. This method returns the
    * member id of the created member.
    * @param memberInfo
    * @return String
    * @roseuid 399E89030386
    */
//   public String addMember(MLIInfo memberInfo)throws DuplicateException,NoMemberFoundException,DatabaseException
//   {
//	   	if (memberInfo!=null) {
//		  // MLIInfo mliDetails=new MLIInfo();
//		  // NoMemberFoundException noMemberFoundException=new NoMemberFoundException();
//		   String mliId=regDAO.addMLIDetails(memberInfo);
//		   if (mliId==null){
//			   throw noMemberFoundException;
//		   } else {
//			   return mliId;
//		   }
//
//	   }else {
//	   		String mliId=null;
//		   return mliId;
//	   }
//
//   }

   /**
    * This method is used to get all the collecting bank master details. All the
    * account branches are retrieved and given.
    * @return ArrayList
    * @roseuid 39B152B703E1
    */
   public ArrayList getCBMasterDtls() throws DatabaseException
   {
		ArrayList returnCBMasterDtls=null;

		returnCBMasterDtls=regDAO.getCBMasterDtls();

		if (returnCBMasterDtls!=null){
			returnCBMasterDtls=null;
		}
		return returnCBMasterDtls;
	}

//	Method to get the bank detials on passing the bank,zone,branch Ids
	  //Nov 18 2003
	  //Ramesh rp14480

	  public MLIInfo getMemberDetails(String bankId,String zoneId,String branchId)throws DatabaseException
	  {
	  	MLIInfo mliInfo=new MLIInfo();
	  	if(bankId!=null&&zoneId!=null&&branchId!=null)
	  	{
	  	mliInfo=regDAO.getMemberDetails(bankId,zoneId,branchId);
	  	return mliInfo;
	  	}
	  	return mliInfo;

	  }
	  /*Added By Kailash for GST changes*/
	 /* public ArrayList getBranchCode(String bankId) {
		  ArrayList list=null;
		  if(bankId!=null){
			   list=regDAO.getBranchCode(bankId);
		  }
			return list;
		}*/
	  public List getGSTStateList(String bankId) {
		  List<MLIInfo> list=null;
		
			   try {
				list=regDAO.getGSTStateList(bankId);
			} catch (DatabaseException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		 
			return list;
		}
	  public String getGstNo(String stateCode,String bankId) {
		  String srt=null;
			if(stateCode!=null){
				srt=regDAO.getGstNo(stateCode,bankId);
			}
			return srt;
		}
	  

public String getBranchStateCode(String memberId) {
	  String srt=null;
		if(memberId!=null){
			srt=regDAO.getBranchStateCode(memberId);
		}
		return srt;
	}

public String getBranchGstNo(String memberId) {
	  String srt=null;
		if(memberId!=null){
			srt=regDAO.getBranchGstNo(memberId);
		}
		return srt;
	}


public MLIInfo getMemberDetailsNew(String bankId,String zoneId,String branchId)throws DatabaseException
	  {
	  	MLIInfo mliInfo=new MLIInfo();
	  	if(bankId!=null&&zoneId!=null&&branchId!=null)
	  	{
	  	mliInfo=regDAO.getMemberDetailsNew(bankId,zoneId,branchId);
	  	return mliInfo;
	  	}
	  	return mliInfo;

	  }

/*	This method is used to return the mli names for a given state.
			*	28 NOV 2003
			*	Nithya np13031
			*/
	  public ArrayList getMLINamesForState(String sState)throws DatabaseException
		 {

		   return regDAO.getMLINamesForState(sState);

		 }
		 
		 
	public MLIInfo getAllMemberDetails(String bankId,String zoneId,String branchId)throws DatabaseException
		{
		  MLIInfo mliInfo=new MLIInfo();
		  if(bankId!=null&&zoneId!=null&&branchId!=null)
		  {
		  mliInfo=regDAO.getAllMemberDetails(bankId,zoneId,branchId);
		  return mliInfo;
		  }
		  return mliInfo;

		}	


	public MLIInfo getMemberDetails(String userId) throws DatabaseException
	{
		return regDAO.getMemberDetails(userId);
	}
	
  
    public void updateMemberAddressDetails(String createdby,MLIInfo mliDetails,String emailId)throws DatabaseException
			 {
			 	if(createdby!=null&&mliDetails!=null ){			 	

			   regDAO.updateMemberAddressDetails(createdby,mliDetails,emailId);

			 	}
			 }	 
	
	/*	This method is used to update the details for a member.
				*	28 NOV 2003
				*	Nithya np13031
				*/
		  public void updateMemberDetails(String createdby,MLIInfo mliDetails)throws DatabaseException
			 {
			 	if(createdby!=null&&mliDetails!=null ){			 	

			   regDAO.updateMemberDetails(createdby,mliDetails);

			 	}
			 }
		  
		  
		  public ArrayList getDepartments()
	        throws DatabaseException
	    {
	        ArrayList departments = regDAO.getDepartments();
	        return departments;
	    }

		

		
		 
}
