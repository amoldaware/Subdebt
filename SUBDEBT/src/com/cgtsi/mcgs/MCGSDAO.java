//Source file: C:\\CHECKOUT\\28OCT2003\\com\\cgtsi\\mcgs\\MCGSDAO.java

package com.cgtsi.mcgs;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Types;
import java.util.ArrayList;

import com.cgtsi.registration.MLIInfo;
import com.cgtsi.util.DBConnection;
import com.cgtsi.common.Constants;
import com.cgtsi.common.DatabaseException;
import com.cgtsi.common.Log;
import com.cgtsi.admin.AdminConstants;
import com.cgtsi.admin.NoOfUsersExceededException;
import com.cgtsi.admin.User;
import com.cgtsi.application.BorrowerDetails;
import com.cgtsi.application.SSIDetails;
import com.cgtsi.util.DateHelper;

/**
 * This class manages the database related activities for MCGF sub-system.
 */
public class MCGSDAO
{

   /**
    * @roseuid 3A1646E8024B
    */
   public MCGSDAO()
   {

   }

   /**
    * This method registers the MCGF with the CGTSI. All the member related data is
    * captured and stored into database. A MCGF member id is generated and given to
    * the user. The generated member id is returned
    * @param memberInfo
    * @param creatingUser
    * @return String
    * @throws DatabaseException
    * @roseuid 39FBC3BA01BE
    */
   public String registerMCGF(MLIInfo memberInfo, User creatingUser) throws DatabaseException
   {
    return null;
   }

   /**
    * This method is used to add the NO of MCGF.The generated user id is returned
    * @param noUser
    * @param creatingUser
    * @return String
    * @throws DatabaseException
    * @roseuid 39FBC96A019F
    */
   public String createNOUser(User noUser, User creatingUser) throws DatabaseException
   {
    return null;
   }

   /**
    * This method creates users for the registered MCGF. Maximum three users are
    * allowed. After storing the user details, this method returns the user id of the
    * user.
    * @param user
    * @param creatingUser
    * @return String
    * @throws DatabaseException
    * @throws NoOfUsersExceededException
    * @roseuid 39FBC3BA01F0
    */
   public String createMCGFUser(User user, User creatingUser) throws DatabaseException, NoOfUsersExceededException
   {
    return null;
   }

   /**
    * This method adds the participating bank details into database.
    * @param participatingBank
    * @param creatingUser
    * @throws DatabaseException
    * @roseuid 39FBC3BA0222
    */
   public void addParticipatingBank(ParticipatingBank participatingBank, User creatingUser) throws DatabaseException
   {
		Log.log(Log.INFO,"MCGSDAO","addParticipatingBank","Entered");

		Connection connection=DBConnection.getConnection(false);

		try
		{
			String memberId=participatingBank.getMemberId();
			Log.log(Log.DEBUG,"MCGSDAO","addParticipatingBank","member Id "+memberId);
			
			String bankId=memberId.substring(1,5);
			String zoneId=memberId.substring(5,9);
			String branchId=memberId.substring(9,13);
			
			CallableStatement callable=
				connection.prepareCall("{?=call funcAddPartBank(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)}");


			callable.registerOutParameter(1,Types.INTEGER);
			callable.setString(2,participatingBank.getBankName());
			callable.setString(3,participatingBank.getBranchName());
			callable.setString(4,participatingBank.getAddress());
			callable.setString(5,participatingBank.getCity());
			callable.setString(6,participatingBank.getDistrict());
			callable.setString(7,participatingBank.getState());
			callable.setString(8,participatingBank.getStdCode());
			callable.setInt(9,participatingBank.getPhoneNo());
			callable.setString(10,participatingBank.getContactPerson());
			callable.setString(11,participatingBank.getEmailId());
			callable.setString(12,participatingBank.getHoAddress());
			callable.setString(13,creatingUser.getUserId());
			callable.setString(14,bankId);
			callable.setString(15,zoneId);
			callable.setString(16,branchId);
			
			callable.registerOutParameter(17,Types.VARCHAR);

			callable.execute();

			int errorCode=callable.getInt(1);

			String error=callable.getString(17);

			Log.log(Log.DEBUG,"MCGSDAO","addParticipatingBank","Error and Error Code "+error+" "+errorCode);

			if(errorCode==Constants.FUNCTION_FAILURE)
			{
				connection.rollback();
				callable.close();
				callable=null;

				Log.log(Log.ERROR,"MCGSDAO","addParticipatingBank",error);

				throw new DatabaseException(error);

			}
			connection.commit();

			callable.close();
			callable=null;


		}
		catch(SQLException e)
		{
			Log.log(Log.ERROR,"MCGSDAO","addParticipatingBank",e.getMessage());
			Log.logException(e);

			throw new DatabaseException("Unable to add participating banks ");
		}
		finally
		{
			DBConnection.freeConnection(connection);
		}


		Log.log(Log.INFO,"MCGSDAO","addParticipatingBank","Exited");

   }

   /**
    * All the SSI members for an MCGF are entered and stored into database. Also,
    * their contribution towards the MCGF is also captured.
    * @param ssiMemberDtls
    * @param creatingUser
    * @throws DatabaseException
    * @roseuid 39FBC3BA0254
    */
   public void addSSIMembers(BorrowerDetails borrowerDetails,String memberId, User creatingUser) throws DatabaseException
   {
		Log.log(Log.INFO,"MCGSDAO","addSSIMembers","Entered");

    	Connection connection=DBConnection.getConnection(false);

    	try
    	{
			CallableStatement callable=
			connection.prepareCall("{?= call funcInsertSSIMembers(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?) }");

			SSIDetails ssiDetails=borrowerDetails.getSsiDetails();

			callable.registerOutParameter(1,Types.INTEGER);
			callable.registerOutParameter(2,Types.VARCHAR);
			callable.setString(3,ssiDetails.getCgbid());
			callable.setString(4,borrowerDetails.getPreviouslyCovered());
			callable.setString(5,borrowerDetails.getAssistedByBank());
			callable.setString(6,borrowerDetails.getAcNo());
			callable.setString(7,borrowerDetails.getNpa());
			callable.setString(8,ssiDetails.getConstitution());
			callable.setString(9,ssiDetails.getSsiType());
			callable.setString(10,ssiDetails.getSsiName());
			callable.setString(11,ssiDetails.getRegNo());

			Log.log(Log.DEBUG,"MCGSDAO","addSSIMembers"," Commencement date "+ssiDetails.getCommencementDate());

			if(ssiDetails.getCommencementDate()!=null && !ssiDetails.getCommencementDate().toString().equals(""))
			{
				callable.setDate(12,new java.sql.Date(ssiDetails.getCommencementDate().getTime()));
			}
			else
			{
				Log.log(Log.DEBUG,"MCGSDAO","addSSIMembers"," Commencement date is null ");
				callable.setDate(12,null);
			}

			callable.setString(13,ssiDetails.getSsiITPan());
			callable.setString(14,ssiDetails.getActivityType());
			callable.setInt(15,ssiDetails.getEmployeeNos());
			callable.setDouble(16,ssiDetails.getProjectedSalesTurnover());
			callable.setDouble(17,ssiDetails.getProjectedExports());
			callable.setString(18,ssiDetails.getAddress());
			callable.setString(19,ssiDetails.getCity());

			callable.setString(20,ssiDetails.getPincode());
			callable.setString(21,ssiDetails.getDisplayDefaultersList());
			callable.setString(22,ssiDetails.getDistrict());
			callable.setString(23,ssiDetails.getState());
			callable.setString(24,ssiDetails.getIndustryNature());
			callable.setString(25,ssiDetails.getIndustrySector());
			callable.setDouble(26,borrowerDetails.getOsAmt());

			callable.setString(27,Constants.YES); //MCGF Flag.
			callable.setDouble(28,ssiDetails.getCorpusContributionAmt());
			callable.setDate(29,new java.sql.Date(ssiDetails.getCorpusContributionDate().getTime()));
			callable.setString(30,creatingUser.getUserId());

			callable.registerOutParameter(31,Types.VARCHAR);

			callable.execute();

			int errorCode=callable.getInt(1);
			String error=callable.getString(31);

			Log.log(Log.DEBUG,"MCGSDAO","addSSIMembers","error code, error "+errorCode+" "+error);

			if(errorCode==Constants.FUNCTION_FAILURE)
			{
				callable.close();
				callable=null;

				connection.rollback();

				Log.log(Log.ERROR,"MCGSDAO","addSSIMembers",error);

				throw new DatabaseException(error);
			}

			String ssiReferenceNumber=callable.getString(2);

			Log.log(Log.DEBUG,"MCGSDAO","addSSIMembers","ssiReferenceNumber "+ssiReferenceNumber);

			callable.close();
			callable=null;


			callable=
			connection.prepareCall("{?= call funcInsertPromoterDtl(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)}");

			callable.registerOutParameter(1,Types.INTEGER);

			callable.setString(2,ssiReferenceNumber);
			callable.setString(3,ssiDetails.getCpTitle());
			callable.setString(4,ssiDetails.getCpFirstName());
			callable.setString(5,ssiDetails.getCpMiddleName());
			callable.setString(6,ssiDetails.getCpLastName());
			callable.setString(7,ssiDetails.getCpITPAN());
			callable.setString(8,ssiDetails.getCpGender());
			callable.setDate(9,new java.sql.Date(ssiDetails.getCpDOB().getTime()));
			callable.setString(10,ssiDetails.getCpLegalID());
			callable.setString(11,ssiDetails.getCpLegalIdValue());
			callable.setString(12,ssiDetails.getFirstName());

			if(ssiDetails.getFirstDOB()!=null && !ssiDetails.getFirstDOB().toString().equals(""))
			{
				callable.setDate(13,new java.sql.Date(ssiDetails.getFirstDOB().getTime()));
			}
			else
			{
				callable.setDate(13,null);
			}


			callable.setString(14,ssiDetails.getFirstItpan());

			callable.setString(15,ssiDetails.getSecondName());

			if(ssiDetails.getSecondDOB()!=null && !ssiDetails.getSecondDOB().toString().equals(""))
			{
				callable.setDate(16,new java.sql.Date(ssiDetails.getSecondDOB().getTime()));
			}
			else
			{
				callable.setDate(16,null);
			}

			callable.setString(17,ssiDetails.getSecondItpan());

			callable.setString(18,ssiDetails.getThirdName());

			if(ssiDetails.getThirdDOB()!=null && !ssiDetails.getThirdDOB().toString().equals(""))
			{
				callable.setDate(19,new java.sql.Date(ssiDetails.getThirdDOB().getTime()));
			}
			else
			{
				callable.setDate(19,null);
			}

			callable.setString(20,ssiDetails.getThirdItpan());
			callable.setString(21,ssiDetails.getSocialCategory());

			callable.registerOutParameter(22,Types.VARCHAR);

			callable.execute();

			errorCode=callable.getInt(1);
			error=callable.getString(22);

			Log.log(Log.DEBUG,"MCGSDAO","addSSIMembers","error code, error "+errorCode+" "+error);

			if(errorCode==Constants.FUNCTION_FAILURE)
			{
				callable.close();
				callable=null;

				connection.rollback();

				Log.log(Log.ERROR,"MCGSDAO","addSSIMembers",error);

				throw new DatabaseException(error);
			}
			
			String bankId=memberId.substring(0,4);
			String zoneId=memberId.substring(4,8);
			String branchId=memberId.substring(8,12);
			
			Log.log(Log.DEBUG,"MCGSDAO","addSSIMembers","member id is  "+memberId);
			Log.log(Log.DEBUG,"MCGSDAO","addSSIMembers","bank id is  "+bankId);
			Log.log(Log.DEBUG,"MCGSDAO","addSSIMembers","zone id is  "+zoneId);
			Log.log(Log.DEBUG,"MCGSDAO","addSSIMembers","branch id is  "+branchId);
			
			callable=connection.prepareCall("{?= call funcInsertMCGFMemBorrower(?,?,?,?,?,?) }");
			callable.registerOutParameter(1,Types.INTEGER);
			callable.setString(2,bankId);
			callable.setString(3,zoneId);
			callable.setString(4,branchId);
			callable.setString(5,ssiReferenceNumber);
			callable.setString(6,null);
			
			callable.registerOutParameter(7,Types.VARCHAR);
			
			callable.execute();
			
			errorCode=callable.getInt(1);
			error=callable.getString(7);
			
			if(errorCode==Constants.FUNCTION_FAILURE)
			{
				callable.close();
				callable=null;

				connection.rollback();

				Log.log(Log.ERROR,"MCGSDAO","addSSIMembers",error);

				throw new DatabaseException(error);
			}

			callable.close();
			callable=null;

			connection.commit();
		}
		catch (SQLException e)
		{
			try
			{
				connection.rollback();
			}
			catch (SQLException ignore){}

			Log.log(Log.INFO,"MCGSDAO","addSSIMembers",e.getMessage());
			Log.logException(e);

			throw new DatabaseException("Unable to add SSI Member Details");
		}
		finally
		{
			DBConnection.freeConnection(connection);
		}

		Log.log(Log.INFO,"MCGSDAO","addSSIMembers","Exited");
   }

   /**
    * If any donors contributed to MCGF's corpus, their details are captured along
    * with their contribution. Donors are excluded from getting guarantee cover.
    * @param donorDtls
    * @param creatingUser
    * @throws DatabaseException
    * @roseuid 39FBC3BA027C
    */
   public void addDonorDetails(DonorDetail donorDtls, User creatingUser) throws DatabaseException
   {
		Log.log(Log.INFO,"MCGSDAO","addDonorDetails","Entered");

		Connection connection=DBConnection.getConnection(false);

		try
		{
			String memberId=donorDtls.getMemberId();
			
			Log.log(Log.DEBUG,"MCGSDAO","addDonorDetails","member Id "+memberId);
			
			String bankId=memberId.substring(1,5);
			String zoneId=memberId.substring(5,9);
			String branchId=memberId.substring(9,13);

			Log.log(Log.DEBUG,"MCGSDAO","addDonorDetails","Ids "+bankId+" , "+zoneId+" , "+branchId);

			CallableStatement callable=
				connection.prepareCall("{?=call funcInsertDonorDetail(?,?,?,?,?,?,?,?,?)}");

			callable.registerOutParameter(1,Types.INTEGER);
			callable.setString(2,donorDtls.getName());
			callable.setString(3,donorDtls.getAddress());
			callable.setDouble(4,donorDtls.getCorpusContributionAmt());


			Log.log(Log.DEBUG,"MCGSDAO","addDonorDetails","CC amt "+donorDtls.getCorpusContributionAmt());

			callable.setDate(5,new java.sql.Date(donorDtls.getCorpusContributionDate().getTime()));

			callable.setString(6,creatingUser.getUserId());
			callable.setString(7,bankId);
			callable.setString(8,zoneId);
			callable.setString(9,branchId);
			
			callable.registerOutParameter(10,Types.VARCHAR);

			callable.execute();

			int errorCode=callable.getInt(1);

			String error=callable.getString(10);

			Log.log(Log.DEBUG,"MCGSDAO","addDonorDetails","error, error codes are "+error+" "+errorCode);

			if(errorCode==Constants.FUNCTION_FAILURE)
			{
				connection.rollback();
				callable.close();
				callable=null;
				Log.log(Log.ERROR,"MCGSDAO","addDonorDetails",error);

				throw new DatabaseException(error);
			}
			connection.commit();

			callable.close();
			callable=null;
		}
		catch(SQLException e)
		{
			Log.log(Log.ERROR,"MCGSDAO","addDonorDetails",e.getMessage());
			Log.logException(e);
			throw new DatabaseException("Unable to add Donor details");
		}
		finally
		{
			DBConnection.freeConnection(connection);
		}

		Log.log(Log.INFO,"MCGSDAO","addDonorDetails","Exited");
   }

   /**
    * This method sets the limit for participating bank. This is to avoid
    * participating banks to corner most of the funds.
    * @param limit
    * @param creatingUser
    * @throws DatabaseException
    * @roseuid 39FBC3BA02AE
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
    * @roseuid 39FBC3BA02EA
    */
   public void updateMCGFStatus(String mcgfId,String reason,User creatingUser) throws DatabaseException
   {
		Log.log(Log.INFO,"MCGSDAO","updateMCGFStatus","Entered");

		Connection connection=DBConnection.getConnection();

		try
		{
			String bankId=mcgfId.substring(0,4);
			String zoneId=mcgfId.substring(4,8);
			String branchId=mcgfId.substring(8,12);

			Log.log(Log.DEBUG,"MCGSDAO","updateMCGFStatus","bank,zone,branch ids "+bankId+" "+zoneId+" "+branchId);

			CallableStatement callable=
				connection.prepareCall("{?=call funcUpdateStatus(?,?,?,?,?,?,?)}");

			callable.registerOutParameter(1,Types.INTEGER);
			callable.setString(2,bankId);
			callable.setString(3,zoneId);
			callable.setString(4,branchId);
			callable.setString(5,AdminConstants.INACTIVE_FLAG);
			callable.setString(6,reason);
			callable.setString(7,creatingUser.getUserId());

			callable.registerOutParameter(8,Types.VARCHAR);

			callable.execute();

			int errorCode=callable.getInt(1);
			String error=callable.getString(8);

			Log.log(Log.DEBUG,"MCGSDAO","updateMCGFStatus","errorCode and error are  "+errorCode+" "+error);

			if(errorCode==Constants.FUNCTION_FAILURE)
			{
				callable.close();
				callable=null;

				Log.log(Log.ERROR,"MCGSDAO","updateMCGFStatus",error);

				throw new DatabaseException(error);
			}

			callable.close();
			callable=null;
		}
		catch (SQLException e)
		{
			Log.log(Log.ERROR,"MCGSDAO","updateMCGFStatus",e.getMessage());
			Log.logException(e);
			throw new DatabaseException(e.getMessage());
		}
		finally
		{
			DBConnection.freeConnection(connection);
		}

		Log.log(Log.INFO,"MCGSDAO","updateMCGFStatus","Exited");
   }

   public ArrayList getAllMCGFs() throws DatabaseException
   {
		Log.log(Log.INFO,"MCGSDAO","getAllMCGFs","Entered");

   		Connection connection=DBConnection.getConnection();

		ArrayList mcgfs=new ArrayList();

   		try
   		{
			CallableStatement callable=connection.prepareCall("{?=call PACKGETALLMCGS.FUNCGETALLMCGS(?,?)}");

			callable.registerOutParameter(1,Types.INTEGER);
			callable.registerOutParameter(2,Constants.CURSOR);
			callable.registerOutParameter(3,Types.VARCHAR);

			callable.execute();

			int errorCode=callable.getInt(1);
			String error=callable.getString(3);

			Log.log(Log.DEBUG,"MCGSDAO","getAllMCGFs","error and error Code "+error+" "+errorCode);



			if(errorCode==Constants.FUNCTION_FAILURE)
			{
				Log.log(Log.ERROR,"MCGSDAO","getAllMCGFs",error);

				callable.close();
				callable=null;

				throw new DatabaseException(error);
			}

			ResultSet mcgfsResults=(ResultSet)callable.getObject(2);

			while(mcgfsResults.next())
			{
				MLIInfo mliInfo=new MLIInfo();
				mliInfo.setBankId(mcgfsResults.getString(1));
				mliInfo.setZoneId(mcgfsResults.getString(2));
				mliInfo.setBranchId(mcgfsResults.getString(3));
				mliInfo.setBankName(mcgfsResults.getString(4));
				mliInfo.setZoneName(mcgfsResults.getString(5));
				mliInfo.setBranchName(mcgfsResults.getString(6));
				mliInfo.setShortName(mcgfsResults.getString(7));
				mliInfo.setAddress(mcgfsResults.getString(8));
				mliInfo.setCity(mcgfsResults.getString(9));

				mliInfo.setPin(mcgfsResults.getString(10));
				mliInfo.setDistrict(mcgfsResults.getString(11));
				mliInfo.setState(mcgfsResults.getString(12));
				mliInfo.setPhoneStdCode(mcgfsResults.getString(13));
				mliInfo.setPhone(mcgfsResults.getString(14));
				mliInfo.setFaxStdCode(mcgfsResults.getString(15));
				mliInfo.setFax(mcgfsResults.getString(16));
				mliInfo.setEmailId(mcgfsResults.getString(17));

				mcgfs.add(mliInfo);
			}

			mcgfsResults.close();
			mcgfsResults=null;

			callable.close();
			callable=null;

		}
		catch (SQLException e)
		{
			Log.log(Log.ERROR,"MCGSDAO","getAllMCGFs",e.getMessage());

			Log.logException(e);

			throw new DatabaseException("Unable to retrieve all MCGFS");
		}
		finally
		{
			DBConnection.freeConnection(connection);
		}
		Log.log(Log.INFO,"MCGSDAO","getAllMCGFs","Exited");

   		return mcgfs;
   }

	public ArrayList getAllParticipatingBanks(String member) throws DatabaseException
	{
		Log.log(Log.INFO,"MCGSDAO","getAllParticipatingBanks","Entered");

		Connection connection = DBConnection.getConnection();
		
   		ArrayList participatingBanks=new ArrayList();

		try
		{
			CallableStatement callable = connection.prepareCall("{call packGetParticipatingBank.funcGetParticipatingBank(?,?,?,?)}");
			callable.registerOutParameter(1, java.sql.Types.INTEGER);
			callable.setString(2, member);				// Member Id
			callable.registerOutParameter(3, Constants.CURSOR);
			callable.registerOutParameter(4, java.sql.Types.VARCHAR);

			callable.execute();

			int errCode = callable.getInt(1);
			String errDesc = callable.getString(4);

			Log.log(Log.DEBUG,"MCGSDAO","getAllParticipatingBanks","error and error Code "+errDesc+" "+errCode);

			if (errCode == Constants.FUNCTION_FAILURE)
			{
				Log.log(Log.ERROR,"MCGSDAO","getAllParticipatingBanks",errDesc);
				
				callable.close();
				callable=null;
				
				throw new DatabaseException(errDesc);
			}

			ResultSet banksResult = (ResultSet) callable.getObject(3);
			String bankName = "";
			while (banksResult.next())
			{
				bankName = banksResult.getString(1) + ", " + banksResult.getString(2);
				Log.log(Log.ERROR,"MCGSDAO","getAllParticipatingBanks","bank " + bankName);
				participatingBanks.add(bankName);
			}
			banksResult.close();
			banksResult=null;
			callable.close();
			callable=null;
		}
		catch (SQLException sqlException)
		{
			Log.log(Log.ERROR,"MCGSDAO","getAllParticipatingBanks",sqlException.getMessage());
			
			Log.logException(sqlException);
			
			throw new DatabaseException("Unable to retrieve Participating Bank Names");
		}
		finally
		{
			DBConnection.freeConnection(connection);
		}
		
		Log.log(Log.INFO,"MCGSDAO","getAllParticipatingBanks","Exited");
		
   		return participatingBanks;
    }

   public void updateMCGSDetails(MCGFDetails mcgfDetails,String userId,Connection connection) throws DatabaseException
   {
		Log.log(Log.INFO,"MCGSDAO","updateMCGSDetails","Entered");

		//Connection connection=DBConnection.getConnection();

		try
		{
			CallableStatement callable=
				connection.prepareCall("{?= call funcUpdateMCGSDtl(?,?,?,?,?,?,?,?,?,?)}");

			callable.registerOutParameter(1,Types.INTEGER);
			callable.setString(2,mcgfDetails.getApplicationReferenceNumber());
			Log.log(Log.INFO,"MCGSDAO","updateMCGSDetails","MCGF App Ref No" + mcgfDetails.getApplicationReferenceNumber());
			if (mcgfDetails.getMcgfName()!=null && !(mcgfDetails.getMcgfName().equals("")))
			{
				callable.setString(3,mcgfDetails.getMcgfName());
			}else {
				
				callable.setString(3,null);
			}
			Log.log(Log.INFO,"MCGSDAO","updateMCGSDetails","MCGF Name" + mcgfDetails.getMcgfName());
			
			if (mcgfDetails.getParticipatingBank()!=null && !(mcgfDetails.getParticipatingBank().equals("")))
			{			
				callable.setString(4,mcgfDetails.getParticipatingBank());
			}else {
				
				callable.setString(4,null);
			}
			Log.log(Log.INFO,"MCGSDAO","updateMCGSDetails","MCGF PArticipating Bank" + mcgfDetails.getParticipatingBank());
			
			if (mcgfDetails.getParticipatingBankBranch()!=null && !(mcgfDetails.getParticipatingBankBranch().equals("")))
			{		

				callable.setString(5,mcgfDetails.getParticipatingBankBranch());
			} else {
				
				callable.setString(5,null);
			}
			Log.log(Log.INFO,"MCGSDAO","updateMCGSDetails","MCGF PArticipating Bank branch" + mcgfDetails.getParticipatingBankBranch());
			
			if (mcgfDetails.getMcgfDistrict()!=null && !(mcgfDetails.getMcgfDistrict().equals("")))
			{		

				callable.setString(6,mcgfDetails.getMcgfDistrict());
			} else {
				
				callable.setString(6,null);				
			}
			Log.log(Log.INFO,"MCGSDAO","updateMCGSDetails","MCGF PArticipating district" + mcgfDetails.getMcgfDistrict());
			
			
			if (mcgfDetails.getMcgfApprovedAmt()!=0)
			{
				callable.setDouble(7,mcgfDetails.getMcgfApprovedAmt());
			}else {
				
				callable.setNull(7,java.sql.Types.DOUBLE);			
			}
			Log.log(Log.INFO,"MCGSDAO","updateMCGSDetails","MCGF approved amt" + mcgfDetails.getMcgfApprovedAmt());
			
			if (mcgfDetails.getMcgfApprovedDate()!=null && !((mcgfDetails.getMcgfApprovedDate()).toString().equals("")))
			{
				callable.setDate(8,new java.sql.Date((mcgfDetails.getMcgfApprovedDate()).getTime()));
			}else {
				
				callable.setDate(8,null);
			}
			Log.log(Log.INFO,"MCGSDAO","updateMCGSDetails","MCGF approved date" + mcgfDetails.getMcgfApprovedDate());
			
			if (mcgfDetails.getMcgfGuaranteeCoverStartDate()!=null && !((mcgfDetails.getMcgfGuaranteeCoverStartDate()).toString().equals("")))
			{
			
				callable.setDate(9,new java.sql.Date((mcgfDetails.getMcgfGuaranteeCoverStartDate()).getTime()));
			}else {
				
				callable.setDate(9,null);
			}
			Log.log(Log.INFO,"MCGSDAO","updateMCGSDetails","MCGF start date" + mcgfDetails.getMcgfGuaranteeCoverStartDate());
			callable.setString(10,userId);
			callable.registerOutParameter(11,Types.VARCHAR);

			callable.execute();

			int errorCode=callable.getInt(1);

			String error=callable.getString(11);

			Log.log(Log.DEBUG,"MCGSDAO","updateMCGSDetails","errorCode, error "+errorCode+","+error);

			if(errorCode==Constants.FUNCTION_FAILURE)
			{
				callable.close();
				callable=null;

				connection.rollback();

				Log.log(Log.ERROR,"MCGSDAO","updateMCGSDetails",error);
				throw new DatabaseException(error);
			}
			callable.close();
			callable=null;

		}
		catch (SQLException e)
		{
			Log.log(Log.ERROR,"MCGSDAO","updateMCGSDetails",e.getMessage());
			Log.logException(e);

			try
				{
					connection.rollback();
				}
				catch (SQLException ignore) {}

			throw new DatabaseException("Unable to add MCGF Details");
		}		

		Log.log(Log.INFO,"MCGSDAO","updateMCGSDetails","Exited");
   }

/*
 * This method is used to retrieve the MCGF Details from the DB
 */
   public MCGFDetails getMcgsDetails(String appRefNo,Connection connection)throws DatabaseException
	{
	   MCGFDetails mcgfDetails= new MCGFDetails();
	   Log.log(Log.INFO,"MCGSDAO","getMcgsDetails","Entered");

	   try
	   {
		   CallableStatement callable=
				connection.prepareCall("{?= call funcGetMCGSDtl(?,?,?,?,?,?,?,?,?)}");

			callable.registerOutParameter(1,Types.INTEGER);
			callable.registerOutParameter(10,Types.VARCHAR);

			callable.setString(2,appRefNo);	
			callable.registerOutParameter(3,Types.VARCHAR);	//MCGF Name
			callable.registerOutParameter(4,Types.VARCHAR);	//Bank Name
			callable.registerOutParameter(5,Types.VARCHAR);	//Branch Name
			callable.registerOutParameter(6,Types.VARCHAR);	//District
			callable.registerOutParameter(7,Types.DOUBLE);	//Approved Amount
			callable.registerOutParameter(8,Types.DATE);	//Approved Date
			callable.registerOutParameter(9,Types.DATE);	//Guar Start Date

			callable.execute();

			int errorCode=callable.getInt(1);

			String error=callable.getString(10);

			Log.log(Log.DEBUG,"MCGSDAO","getMcgsDetails","errorCode, error "+errorCode+","+error);

			if(errorCode==Constants.FUNCTION_FAILURE)
			{
				callable.close();
				callable=null;

				connection.rollback();

				Log.log(Log.ERROR,"MCGSDAO","getMcgsDetails",error);
				throw new DatabaseException(error);
			}
			else {

				mcgfDetails.setMcgfName(callable.getString(3));
				mcgfDetails.setParticipatingBank(callable.getString(4));
				mcgfDetails.setParticipatingBankBranch(callable.getString(5));
				mcgfDetails.setMcgfDistrict(callable.getString(6));
				mcgfDetails.setMcgfApprovedAmt(callable.getDouble(7));
				Log.log(Log.INFO,"MCGSDAO","getMcgsDetails","approved Amount :" + mcgfDetails.getMcgfApprovedAmt());
				mcgfDetails.setMcgfApprovedDate(DateHelper.sqlToUtilDate(callable.getDate(8)));
				mcgfDetails.setMcgfGuaranteeCoverStartDate(DateHelper.sqlToUtilDate(callable.getDate(9)));
			}
	   	
	   }
	   catch (SQLException e)
		{
			Log.log(Log.ERROR,"MCGSDAO","getMcgsDetails",e.getMessage());
			Log.logException(e);

			try
				{
					connection.rollback();
				}
				catch (SQLException ignore) {}

			throw new DatabaseException("Unable to retrieve MCGF Details");
		}	
		Log.log(Log.INFO,"MCGSDAO","getMcgsDetails","Exited");

	   return mcgfDetails;


	}

	/*
	 * This method is used to retrieve the Old MCGF Details from the DB
	 */
	   public MCGFDetails getOldMcgsDetails(String appRefNo,Connection connection)throws DatabaseException
		{
		   MCGFDetails mcgfDetails= new MCGFDetails();
		   Log.log(Log.INFO,"MCGSDAO","getOldMcgsDetails","Entered");

		   try
		   {
			   CallableStatement callable=
					connection.prepareCall("{?= call funcGetOldMCGSDtl(?,?,?,?,?,?,?,?,?)}");

				callable.registerOutParameter(1,Types.INTEGER);
				callable.registerOutParameter(10,Types.VARCHAR);

				callable.setString(2,appRefNo);	
				callable.registerOutParameter(3,Types.VARCHAR);	//MCGF Name
				callable.registerOutParameter(4,Types.VARCHAR);	//Bank Name
				callable.registerOutParameter(5,Types.VARCHAR);	//Branch Name
				callable.registerOutParameter(6,Types.VARCHAR);	//District
				callable.registerOutParameter(7,Types.DOUBLE);	//Approved Amount
				callable.registerOutParameter(8,Types.DATE);	//Approved Date
				callable.registerOutParameter(9,Types.DATE);	//Guar Start Date

				callable.execute();

				int errorCode=callable.getInt(1);

				String error=callable.getString(10);

				Log.log(Log.DEBUG,"MCGSDAO","getOldMcgsDetails","errorCode, error "+errorCode+","+error);

				if(errorCode==Constants.FUNCTION_FAILURE)
				{
					callable.close();
					callable=null;

					connection.rollback();

					Log.log(Log.ERROR,"MCGSDAO","getOldMcgsDetails",error);
					throw new DatabaseException(error);
				}
				else {

					mcgfDetails.setMcgfName(callable.getString(3));
					mcgfDetails.setParticipatingBank(callable.getString(4));
					mcgfDetails.setParticipatingBankBranch(callable.getString(5));
					mcgfDetails.setMcgfDistrict(callable.getString(6));
					mcgfDetails.setMcgfApprovedAmt(callable.getDouble(7));
					Log.log(Log.INFO,"MCGSDAO","getMcgsDetails","approved Amount :" + mcgfDetails.getMcgfApprovedAmt());
					mcgfDetails.setMcgfApprovedDate(DateHelper.sqlToUtilDate(callable.getDate(8)));
					mcgfDetails.setMcgfGuaranteeCoverStartDate(DateHelper.sqlToUtilDate(callable.getDate(9)));
				}
	   	
		   }
		   catch (SQLException e)
			{
				Log.log(Log.ERROR,"MCGSDAO","getMcgsDetails",e.getMessage());
				Log.logException(e);

				try
					{
						connection.rollback();
					}
					catch (SQLException ignore) {}

				throw new DatabaseException("Unable to retrieve MCGF Details");
			}	
			Log.log(Log.INFO,"MCGSDAO","getMcgsDetails","Exited");

		   return mcgfDetails;


		}

}
