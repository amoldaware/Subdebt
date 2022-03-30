//Source file: C:\\CGTSI\\Vss\\com\\cgtsi\\registration\\RegistrationDAO.java

package com.cgtsi.registration;

import com.cgtsi.admin.DuplicateException;
import com.cgtsi.admin.User;
import com.cgtsi.application.LogClass;
import com.cgtsi.common.*;

import java.sql.PreparedStatement;
import java.util.ArrayList;
import java.util.List;
import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.Types;
import java.sql.SQLException;
import java.sql.ResultSet;
import com.cgtsi.util.DBConnection;




/**
 * This class is to hold the methods in the Registration module. These methods
 * interact with the database for variuos operations.
 */
public class RegistrationDAO
{
	User user=new User();
   /**
    * @roseuid 39BCC934000D
    */
   public RegistrationDAO()
   {

   }

// This method is called to generate member Id before calling register MLI method.
	//12,NOV,2003
	//Ramesh rp14480
	/*
	public String getMemberId(MLIInfo mliDetails)throws DatabaseException
	{
		//System.out.println("entered GetMemberId");
		Connection connection=DBConnection.getConnection();
		String genMemberId=null;
		try{
			CallableStatement memberId=connection.prepareCall(
						"{?=call funcGenerateMemId(?,?,?,?,?,?,?)}");

			memberId.registerOutParameter(1,Types.INTEGER);
			memberId.registerOutParameter(5,Types.VARCHAR);//bankId
			memberId.registerOutParameter(6,Types.VARCHAR);//zoneId
			memberId.registerOutParameter(7,Types.VARCHAR);//branchId
			memberId.registerOutParameter(8,Types.VARCHAR);

			memberId.setString(2,mliDetails.getBankId());
			memberId.setString(3,mliDetails.getZoneName());
			memberId.setString(4,mliDetails.getBranchName());

			memberId.execute();
			//System.out.println("executed GetMemberId");

			String bankId=memberId.getString(5);
			//System.out.println("bankId"+bankId);
			String zoneId=memberId.getString(6);
			//System.out.println("zoneId"+zoneId);
			String branchId=memberId.getString(7);
			//System.out.println("branchId"+branchId);
			genMemberId=bankId.trim()+zoneId.trim()+branchId.trim();
			int functionReturnValue=memberId.getInt(1);
			 if(functionReturnValue==1){
				throw new DatabaseException(memberId.getString(8));
			 }
		}catch(SQLException sqlException){
			sqlException.printStackTrace();
			throw new DatabaseException(sqlException.getMessage());
		}finally{
			DBConnection.freeConnection(connection);
		}


		 return genMemberId;
	}
   */
   /**
    * This method accepts an object of MLIInfo which contains the details of the
    * lending institution. The method adds these details to the database.
    * @param mliDetails
    * @return String
    * @throws DuplicateException
    * @throws DatabaseException
    * @roseuid 397817DC03B5
    */


	/*
	 * public String addMLIDetails(MLIInfo mliDetails,String createdBy) throws
	 * DuplicateException, DatabaseException
	 * 
	 * {
	 * 
	 * String memberId=allotMLIId(mliDetails);
	 * Log.log(Log.DEBUG,"RegistrationDAO","addMLIDetails","memberId "+memberId);
	 * 
	 * //The dan delivery checkbox values are received as a String array and
	 * concatenated.
	 * 
	 * String[]danDelivery=mliDetails.getDanDelivery(); int size=danDelivery.length;
	 * String finalVal=""; for(int i=0;i<size;i++){ String value=danDelivery[i];
	 * if((value!=null)&&!(value.equals(""))){ finalVal=finalVal+value+","; } } int
	 * len= finalVal.length(); String danValue=finalVal.substring(0,len-1);
	 * 
	 * Connection connection=DBConnection.getConnection(false); try{
	 * //CallableStatement MLIRegister=connection.
	 * prepareCall("{?=call funcInsertMLI(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)}"
	 * ); //CallableStatement MLIRegister=connection.
	 * prepareCall("{?=call funcInsertMLI(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)}"
	 * ); CallableStatement MLIRegister=connection.
	 * prepareCall("{?=call funcInsertMLI(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)}"
	 * );
	 * 
	 * Log.log(Log.DEBUG,"RegistrationDAO","addMLIDetails","bankid"+mliDetails.
	 * getBankId()); MLIRegister.registerOutParameter(1,Types.INTEGER);
	 * MLIRegister.registerOutParameter(23,Types.VARCHAR);
	 * 
	 * MLIRegister.setString(2,mliDetails.getBankId());
	 * MLIRegister.setString(3,mliDetails.getZoneId());
	 * MLIRegister.setString(4,mliDetails.getBranchId());
	 * 
	 * MLIRegister.setString(5,mliDetails.getBankName());
	 * 
	 * if(mliDetails.getZoneName()!=null && !mliDetails.getZoneName().equals("")) {
	 * MLIRegister.setString(6,mliDetails.getZoneName()); } else {
	 * MLIRegister.setString(6,null); }
	 * 
	 * if(mliDetails.getBranchName()!=null &&
	 * !mliDetails.getBranchName().equals("")) {
	 * MLIRegister.setString(7,mliDetails.getBranchName()); } else {
	 * MLIRegister.setString(7,null); }
	 * 
	 * MLIRegister.setString(8,mliDetails.getShortName());
	 * MLIRegister.setString(9,mliDetails.getAddress());
	 * MLIRegister.setString(10,mliDetails.getCity());
	 * 
	 * MLIRegister.setString(11,mliDetails.getPin());
	 * MLIRegister.setString(12,mliDetails.getDistrict());
	 * MLIRegister.setString(13,mliDetails.getState());
	 * 
	 * MLIRegister.setString(14,mliDetails.getPhoneStdCode());
	 * MLIRegister.setString(15,mliDetails.getPhone());
	 * 
	 * MLIRegister.setString(16,mliDetails.getFaxStdCode());
	 * MLIRegister.setString(17,mliDetails.getFax());
	 * 
	 * MLIRegister.setString(18,mliDetails.getEmailId());////AAAAAAAAAAAAAAAAA
	 * 
	 * if(mliDetails.getSupportMCGF()!=null &&
	 * mliDetails.getSupportMCGF().equals("Y")){ MLIRegister.setString(19,"Y"); }
	 * else { MLIRegister.setString(19,"N"); }
	 * 
	 * MLIRegister.setString(20,danValue);//delivery of dan...to be done
	 * 
	 * if(mliDetails.getReportingZone()!=null &&
	 * !mliDetails.getReportingZone().equals("")) {
	 * 
	 * Log.log(Log.DEBUG,"RegistrationDAO","addMLIDetails","reportingZone "
	 * +mliDetails.getReportingZone());
	 * MLIRegister.setString(21,mliDetails.getReportingZone()); } else {
	 * MLIRegister.setString(21,null); }
	 * 
	 * MLIRegister.setString(22,createdBy);
	 * 
	 * 
	 * 
	 * // Added By Parmanand 09050202 Start
	 * 
	 * MLIRegister.setString(24,mliDetails.getMliType());
	 * 
	 * MLIRegister.setDouble(25,mliDetails.getZeroToTenLakhNoOfBorrowrs());
	 * MLIRegister.setDouble(26,mliDetails.getZeroToTenLakhTotAmt());
	 * 
	 * MLIRegister.setDouble(27,mliDetails.getTenToFiftyLakhNoOfBorrowrs());
	 * MLIRegister.setDouble(28,mliDetails.getTenToFiftyLakhTotAmt());
	 * 
	 * MLIRegister.setDouble(29,mliDetails.getFiftyLakhToOneCrNoOfBorrowrs());
	 * MLIRegister.setDouble(30,mliDetails.getFiftyLakhToOneCrTotAmt());
	 * 
	 * MLIRegister.setDouble(31,mliDetails.getOneCrToFiveCrNoOfBorrowrs());
	 * MLIRegister.setDouble(32,mliDetails.getOneCrToFiveCrTotAmt());
	 * 
	 * MLIRegister.setDouble(33,mliDetails.getFiveCrToFifteenCrNoOfBorrowrs());
	 * MLIRegister.setDouble(34,mliDetails.getFiveCrToFifteenCrTotAmt());
	 * 
	 * MLIRegister.setDouble(35,mliDetails.getFifteenCrToTwentyFiveCrNoOfBorrowrs())
	 * ; MLIRegister.setDouble(36,mliDetails.getFifteenCrToTwentyFiveCrTotAmt());
	 * 
	 * // Added By Parmanand 09050202 End MLIRegister.execute();
	 * 
	 * int functionReturn=MLIRegister.getInt(1); String
	 * error=MLIRegister.getString(23);
	 * 
	 * 
	 * 
	 * 
	 * if(functionReturn==Constants.FUNCTION_FAILURE){
	 * 
	 * connection.rollback();
	 * 
	 * MLIRegister.close();
	 * 
	 * MLIRegister=null;
	 * 
	 * throw new DatabaseException(error); }
	 * 
	 * MLIRegister.close(); MLIRegister=null; connection.commit();
	 * 
	 * }catch(SQLException e){
	 * 
	 * try { connection.rollback(); } catch (SQLException ignore) { }
	 * 
	 * Log.log(Log.ERROR,"RegDAO","addMLIDetails",e.getMessage());
	 * 
	 * Log.logException(e); e.printStackTrace();
	 * 
	 * throw new DatabaseException("Unable to add MLI details");
	 * 
	 * }finally{ DBConnection.freeConnection(connection); } return memberId; }
	 * 
	 */
   public String addMLIBranchDetails(MLIInfo mliDetails,String createdBy) throws DuplicateException, DatabaseException

	  {

		String memberId=allotMLIId(mliDetails);
		//Log.log(Log.DEBUG,"RegistrationDAO","addMLIDetails","memberId "+memberId);

		//The dan delivery checkbox values are received as a String array and concatenated.

		/*
		 * String[]danDelivery=mliDetails.getDanDelivery(); int size=danDelivery.length;
		 * String finalVal=""; for(int i=0;i<size;i++){ String value=danDelivery[i];
		 * if((value!=null)&&!(value.equals(""))){ finalVal=finalVal+value+","; } } int
		 * len= finalVal.length(); String danValue=finalVal.substring(0,len-1);
		 */

		Connection connection=DBConnection.getConnection(false);
		try{
			CallableStatement MLIRegister=connection.prepareCall(
					"{call FUNCINSERTBRANCHMLI(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)}");

			MLIRegister.registerOutParameter(1,Types.INTEGER);
			MLIRegister.registerOutParameter(24,Types.VARCHAR);
			Log.log(Log.DEBUG,"RegistrationDAO","addMLIDetails","bankid"+mliDetails.getBankId());
			MLIRegister.setString(2,mliDetails.getBankId());
			MLIRegister.setString(3,mliDetails.getZoneId());
			MLIRegister.setString(4,mliDetails.getBranchId());

			MLIRegister.setString(5,mliDetails.getBankName());

			if(mliDetails.getZoneName()!=null && !mliDetails.getZoneName().equals(""))
			{
				MLIRegister.setString(6,mliDetails.getZoneName());
			}
			else
			{
				MLIRegister.setString(6,null);
			}

			if(mliDetails.getBranchName()!=null && !mliDetails.getBranchName().equals(""))
			{
				MLIRegister.setString(7,mliDetails.getBranchName());
			}
			else
			{
				MLIRegister.setString(7,null);
			}

			MLIRegister.setString(8,mliDetails.getShortName());
			MLIRegister.setString(9,mliDetails.getAddress());
			MLIRegister.setString(10,mliDetails.getCity());
			MLIRegister.setString(11,mliDetails.getPin());
			MLIRegister.setString(12,mliDetails.getDistrict());
			MLIRegister.setString(13,mliDetails.getState());
			MLIRegister.setString(14,mliDetails.getPhoneStdCode());
			MLIRegister.setString(15,mliDetails.getPhone());
			MLIRegister.setString(16,mliDetails.getFaxStdCode());
			MLIRegister.setString(17,mliDetails.getFax());
			MLIRegister.setString(18,mliDetails.getEmailId());

			if(mliDetails.getSupportMCGF()!=null && mliDetails.getSupportMCGF().equals("Y"))
			{
				MLIRegister.setString(19,"Y");
			}
			else
			{
				MLIRegister.setString(19,"N");
			}

			MLIRegister.setString(20,"");//delivery of dan...to be done

			if(mliDetails.getReportingZone()!=null && !mliDetails.getReportingZone().equals(""))
						{

							Log.log(Log.DEBUG,"RegistrationDAO","addMLIDetails","reportingZone "+mliDetails.getReportingZone());
							MLIRegister.setString(21,mliDetails.getReportingZone());
						}
						else
						{
							MLIRegister.setString(21,null);
						}

			MLIRegister.setString(22,createdBy);
			
			if(mliDetails.getBrnGstNo()!=null && !mliDetails.getBrnGstNo().equals(""))
			{
				System.out.println("getBrnGstNo"+mliDetails.getBrnGstNo());
				//String bGNo=mliDetails.getBrnGstNo();
				MLIRegister.setString(23,mliDetails.getBrnGstNo());
			}
			else
			{
				MLIRegister.setString(23,null);
			}
			

			MLIRegister.execute();


			int functionReturn=MLIRegister.getInt(1);

			String error=MLIRegister.getString(24);
			

			if(functionReturn==Constants.FUNCTION_FAILURE){

				connection.rollback();

				MLIRegister.close();

				MLIRegister=null;

				throw new DatabaseException(error);
			}

	 MLIRegister.close();
	 MLIRegister=null;
	 connection.commit();

		}catch(SQLException e){

				try {
				  connection.rollback();
				}
				catch (SQLException ignore) {
				}

				Log.log(Log.ERROR,"RegDAO","addMLIDetails",e.getMessage());

				Log.logException(e);

				throw new DatabaseException("Unable to add MLI details");

		}finally{
			DBConnection.freeConnection(connection);
		}
		return memberId;
}



   
   
   public String addMLIDetails(MLIInfo mliDetails,String createdBy) throws DuplicateException, DatabaseException

	  {

		String memberId=allotMLIId(mliDetails);
		Log.log(Log.DEBUG,"RegistrationDAO","addMLIDetails","memberId "+memberId);

		//The dan delivery checkbox values are received as a String array and concatenated.

		///String[]danDelivery=mliDetails.getDanDelivery();
		///int size=danDelivery.length;
		///String finalVal="";
		/*for(int i=0;i<size;i++){
			String value=danDelivery[i];
			if((value!=null)&&!(value.equals(""))){
				finalVal=finalVal+value+",";
			}
		}*/
		///int len= finalVal.length();
		///String danValue=finalVal.substring(0,len-1);

		Connection connection=DBConnection.getConnection(false);
		try{
			//CallableStatement MLIRegister=connection.prepareCall("{?=call funcInsertMLI(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)}");
			//CallableStatement MLIRegister=connection.prepareCall("{?=call funcInsertMLI(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)}");
			CallableStatement MLIRegister=connection.prepareCall("{call funcInsertMLI(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)}");

			Log.log(Log.DEBUG,"RegistrationDAO","addMLIDetails","bankid"+mliDetails.getBankId());
			MLIRegister.registerOutParameter(1,Types.INTEGER);
			MLIRegister.registerOutParameter(23,Types.VARCHAR);

			MLIRegister.setString(2,mliDetails.getBankId());
			MLIRegister.setString(3,mliDetails.getZoneId());
			MLIRegister.setString(4,mliDetails.getBranchId());

			MLIRegister.setString(5,mliDetails.getBankName());

			if(mliDetails.getZoneName()!=null && !mliDetails.getZoneName().equals(""))
			{
				MLIRegister.setString(6,mliDetails.getZoneName());
			}
			else
			{
				MLIRegister.setString(6,null);
			}

			if(mliDetails.getBranchName()!=null && !mliDetails.getBranchName().equals(""))
			{
				MLIRegister.setString(7,mliDetails.getBranchName());
			}
			else
			{
				MLIRegister.setString(7,null);
			}

			MLIRegister.setString(8,mliDetails.getShortName());
			MLIRegister.setString(9,mliDetails.getAddress());
			MLIRegister.setString(10,mliDetails.getCity());
			
			MLIRegister.setString(11,mliDetails.getPin());
			MLIRegister.setString(12,mliDetails.getDistrict());
			MLIRegister.setString(13,mliDetails.getState());
			
			MLIRegister.setString(14,mliDetails.getPhoneStdCode());
			MLIRegister.setString(15,mliDetails.getPhone());
			
			MLIRegister.setString(16,mliDetails.getFaxStdCode());
			MLIRegister.setString(17,mliDetails.getFax());
			
			MLIRegister.setString(18,mliDetails.getEmailId());////AAAAAAAAAAAAAAAAA
			//supportMCGF
			MLIRegister.setString(19," ");
			//danDelivery
			MLIRegister.setString(20," ");//delivery of dan...to be done

			if(mliDetails.getReportingZone()!=null && !mliDetails.getReportingZone().equals(""))
						{

							Log.log(Log.DEBUG,"RegistrationDAO","addMLIDetails","reportingZone "+mliDetails.getReportingZone());
							MLIRegister.setString(21,mliDetails.getReportingZone());
						}
						else
						{
							MLIRegister.setString(21,null);
						}

			MLIRegister.setString(22,createdBy);

			
			
			// Added By Parmanand 09050202 Start
			
			MLIRegister.setString(24,mliDetails.getMliType());
			
			double vZeroToTenLakhNoOfBorrowrs=Double.parseDouble(mliDetails.getZeroToTenLakhNoOfBorrowrs());
			MLIRegister.setDouble(25,vZeroToTenLakhNoOfBorrowrs);
			double vZeroToTenLakhTotAmt=Double.parseDouble(mliDetails.getZeroToTenLakhTotAmt());
			MLIRegister.setDouble(26,vZeroToTenLakhTotAmt);
			double vZeroToTenLakhNoOfBorrowrsTc=Double.parseDouble(mliDetails.getZeroToTenLakhNoOfBorrowrsTC());
			MLIRegister.setDouble(27,vZeroToTenLakhNoOfBorrowrsTc);
			double vZeroToTenLakhTotAmtTC=Double.parseDouble(mliDetails.getZeroToTenLakhTotAmtTC());
			MLIRegister.setDouble(28,vZeroToTenLakhTotAmtTC);
			double vZeroToTenLakhNoOfBorrowrsWCTL=Double.parseDouble(mliDetails.getZeroToTenLakhNoOfBorrowrsWCTL());
			MLIRegister.setDouble(29,vZeroToTenLakhNoOfBorrowrsWCTL);
			double vZeroToTenLakhTotAmtWCTL=Double.parseDouble(mliDetails.getZeroToTenLakhTotAmtWCTL());
			MLIRegister.setDouble(30,vZeroToTenLakhTotAmtWCTL);
			
		
			double vTenToFiftyLakhNoOfBorrowrs=Double.parseDouble(mliDetails.getTenToFiftyLakhNoOfBorrowrs());
			MLIRegister.setDouble(31,vTenToFiftyLakhNoOfBorrowrs);
			double vTenToFiftyLakhTotAmt=Double.parseDouble(mliDetails.getTenToFiftyLakhTotAmt());
			MLIRegister.setDouble(32,vTenToFiftyLakhTotAmt);
			double vTenToFiftyLakhNoOfBorrowrsTC=Double.parseDouble(mliDetails.getTenToFiftyLakhNoOfBorrowrsTC());
			MLIRegister.setDouble(33,vTenToFiftyLakhNoOfBorrowrsTC);
			double vTenToFiftyLakhTotAmtTC=Double.parseDouble(mliDetails.getTenToFiftyLakhTotAmtTC());
			MLIRegister.setDouble(34,vTenToFiftyLakhTotAmtTC);
			double vTenToFiftyLakhNoOfBorrowrsWCTL=Double.parseDouble(mliDetails.getTenToFiftyLakhNoOfBorrowrsWCTL());
			MLIRegister.setDouble(35,vTenToFiftyLakhNoOfBorrowrsWCTL);
			double vTenToFiftyLakhTotAmtWCTL=Double.parseDouble(mliDetails.getTenToFiftyLakhTotAmtWCTL());
			MLIRegister.setDouble(36,vTenToFiftyLakhTotAmtWCTL);
			
			
			
			
			double vFiftyLakhToOneCrNoOfBorrowrs=Double.parseDouble(mliDetails.getFiftyLakhToOneCrNoOfBorrowrs());
			MLIRegister.setDouble(37,vFiftyLakhToOneCrNoOfBorrowrs);
			double vFiftyLakhToOneCrTotAmt=Double.parseDouble(mliDetails.getFiftyLakhToOneCrTotAmt());
			MLIRegister.setDouble(38,vFiftyLakhToOneCrTotAmt);
			double vFiftyLakhToOneCrNoOfBorrowrsTC=Double.parseDouble(mliDetails.getFiftyLakhToOneCrNoOfBorrowrsTC());
			MLIRegister.setDouble(39,vFiftyLakhToOneCrNoOfBorrowrsTC);
			double vFiftyLakhToOneCrTotAmtTC=Double.parseDouble(mliDetails.getFiftyLakhToOneCrTotAmtTC());
			MLIRegister.setDouble(40,vFiftyLakhToOneCrTotAmtTC);
			double vFiftyLakhToOneCrNoOfBorrowrsWCTL=Double.parseDouble(mliDetails.getFiftyLakhToOneCrNoOfBorrowrsWCTL());
			MLIRegister.setDouble(41,vFiftyLakhToOneCrNoOfBorrowrsWCTL);
			double vFiftyLakhToOneCrTotAmtWCTL=Double.parseDouble(mliDetails.getFiftyLakhToOneCrTotAmtWCTL());
			MLIRegister.setDouble(42,vFiftyLakhToOneCrTotAmtWCTL);
			
			
			double vOneCrToFiveCrNoOfBorrowrs=Double.parseDouble(mliDetails.getOneCrToFiveCrNoOfBorrowrs());
			MLIRegister.setDouble(43,vOneCrToFiveCrNoOfBorrowrs);
			double vOneCrToFiveCrTotAmt=Double.parseDouble(mliDetails.getOneCrToFiveCrTotAmt());
			MLIRegister.setDouble(44,vOneCrToFiveCrTotAmt);
			double vOneCrToFiveCrNoOfBorrowrsTC=Double.parseDouble(mliDetails.getOneCrToFiveCrNoOfBorrowrsTC());
			MLIRegister.setDouble(45,vOneCrToFiveCrNoOfBorrowrsTC);
			double vOneCrToFiveCrTotAmtTC=Double.parseDouble(mliDetails.getOneCrToFiveCrTotAmtTC());
			MLIRegister.setDouble(46,vOneCrToFiveCrTotAmtTC);
		
			double vOneCrToFiveCrNoOfBorrowrsWCTL=Double.parseDouble(mliDetails.getOneCrToFiveCrNoOfBorrowrsWCTL());
			MLIRegister.setDouble(47,vOneCrToFiveCrNoOfBorrowrsWCTL);
			double vOneCrToFiveCrTotAmtWCTL=Double.parseDouble(mliDetails.getOneCrToFiveCrTotAmtWCTL());
			MLIRegister.setDouble(48,vOneCrToFiveCrTotAmtWCTL);
			
			
			double vFiveCrToFifteenCrNoOfBorrowrs=Double.parseDouble(mliDetails.getFiveCrToFifteenCrNoOfBorrowrs());
			MLIRegister.setDouble(49,vFiveCrToFifteenCrNoOfBorrowrs);
			double vFiveCrToFifteenCrTotAmt=Double.parseDouble(mliDetails.getFiveCrToFifteenCrTotAmt());
			MLIRegister.setDouble(50,vFiveCrToFifteenCrTotAmt);
			double vFiveCrToFifteenCrNoOfBorrowrsTC=Double.parseDouble(mliDetails.getFiveCrToFifteenCrNoOfBorrowrsTC());
			MLIRegister.setDouble(51,vFiveCrToFifteenCrNoOfBorrowrsTC);
			double vFiveCrToFifteenCrTotAmtTC=Double.parseDouble(mliDetails.getFiveCrToFifteenCrTotAmtTC());
			MLIRegister.setDouble(52,vFiveCrToFifteenCrTotAmtTC);
			double vFiveCrToFifteenCrNoOfBorrowrsWCTL=Double.parseDouble(mliDetails.getFiveCrToFifteenCrNoOfBorrowrsWCTL());
			MLIRegister.setDouble(53,vFiveCrToFifteenCrNoOfBorrowrsWCTL);
			double vFiveCrToFifteenCrTotAmtWCTL=Double.parseDouble(mliDetails.getFiveCrToFifteenCrTotAmtWCTL());
			MLIRegister.setDouble(54,vFiveCrToFifteenCrTotAmtWCTL);
			
			
			double vFifteenCrToTwentyFiveCrNoOfBorrowrs=Double.parseDouble(mliDetails.getFifteenCrToTwentyFiveCrNoOfBorrowrs());
			MLIRegister.setDouble(55,vFifteenCrToTwentyFiveCrNoOfBorrowrs);
			double vFifteenCrToTwentyFiveCrTotAmt=Double.parseDouble(mliDetails.getFifteenCrToTwentyFiveCrTotAmt());
			MLIRegister.setDouble(56,vFifteenCrToTwentyFiveCrTotAmt);
			double vFifteenCrToTwentyFiveCrNoOfBorrowrsTC=Double.parseDouble(mliDetails.getFifteenCrToTwentyFiveCrNoOfBorrowrsTC());
			MLIRegister.setDouble(57,vFifteenCrToTwentyFiveCrNoOfBorrowrsTC);
			double vFifteenCrToTwentyFiveCrTotAmtTC=Double.parseDouble(mliDetails.getFifteenCrToTwentyFiveCrTotAmtTC());
			MLIRegister.setDouble(58,vFifteenCrToTwentyFiveCrTotAmtTC);
			double vFifteenCrToTwentyFiveCrNoOfBorrowrsWCTL=Double.parseDouble(mliDetails.getFifteenCrToTwentyFiveCrNoOfBorrowrsWCTL());
			MLIRegister.setDouble(59,vFifteenCrToTwentyFiveCrNoOfBorrowrsWCTL);
			double vFifteenCrToTwentyFiveCrTotAmtWCTL=Double.parseDouble(mliDetails.getFifteenCrToTwentyFiveCrTotAmtWCTL());
			MLIRegister.setDouble(60,vFifteenCrToTwentyFiveCrTotAmtWCTL);
			System.out.println("regFees value"+mliDetails.getRegFees());
		    MLIRegister.setFloat(61, mliDetails.getRegFees());
		
				
			// Added By Parmanand 09050202 End
			MLIRegister.execute();

			int functionReturn=MLIRegister.getInt(1);
			String error=MLIRegister.getString(23);
			

			

			if(functionReturn==Constants.FUNCTION_FAILURE){

				connection.rollback();

				MLIRegister.close();

				MLIRegister=null;

				throw new DatabaseException(error);
			}

	 MLIRegister.close();
	 MLIRegister=null;
//	 connection.commit();

		}catch(SQLException e){

				try {
				  connection.rollback();
				}
				catch (SQLException ignore) {
				}

				Log.log(Log.ERROR,"RegDAO","addMLIDetails",e.getMessage());

				Log.logException(e);
				e.printStackTrace();
				System.out.println("e==="+e);

				throw new DatabaseException("Unable to add MLI details");

		}finally{
			DBConnection.freeConnection(connection);
		}
		return memberId;
}

	
   /**
    * The method is to add the Collecting Bank detaills in the database. It takes an
    * object of Collecting Bank as an argument.
    * @param collectingBank - This object stores the details of the Collecting Bank.
    * The details are populated from the screen and is passed on to this method.
    * @return boolean
    * @throws DatabaseException
    * @roseuid 397817DC03B7
    */
   public boolean addCollectingBankDetails(CollectingBank collectingBank,String createdBy) throws DatabaseException,SQLException
   {

		Connection connection=DBConnection.getConnection(false);
		try{
			CallableStatement collectingBankDetails=connection.prepareCall(
					"{?=call funcInsertCollectingBank(?,?,?,?,?,?,?,?,?,?,?,?,?)}");

			collectingBankDetails.registerOutParameter(1,Types.INTEGER);
			collectingBankDetails.registerOutParameter(14,Types.VARCHAR);

			collectingBankDetails.setString(2,collectingBank.getCollectingBankName());
			collectingBankDetails.setString(3,collectingBank.getBranchName());
			collectingBankDetails.setString(4,collectingBank.getAddress());
			collectingBankDetails.setString(5,collectingBank.getCity());
			collectingBankDetails.setString(6,collectingBank.getState());
			collectingBankDetails.setString(7,collectingBank.getPhoneStdCode());
			collectingBankDetails.setString(8,collectingBank.getPhone());
			collectingBankDetails.setString(9,collectingBank.getContactPerson());
			collectingBankDetails.setString(10,collectingBank.getEmailId());
			collectingBankDetails.setString(11,collectingBank.getAccNo());
			collectingBankDetails.setString(12,collectingBank.getHoAddress());
			collectingBankDetails.setString(13,createdBy);

			collectingBankDetails.execute();

			//If error status is 1 throw database exception.

			int functionReturn=collectingBankDetails.getInt(1);

			String error=collectingBankDetails.getString(14);

			if(functionReturn==Constants.FUNCTION_FAILURE){

				connection.rollback();

				collectingBankDetails.close();

				collectingBankDetails=null;

				throw new DatabaseException(error);
			}

		collectingBankDetails.close();
		collectingBankDetails=null;

		}catch(SQLException e){


				try {
				  connection.rollback();
				}
				catch (SQLException ignore) {
				}
				Log.log(Log.ERROR,"RegDAO","addCollectingBankDetails",e.getMessage());

				Log.logException(e);

				throw new DatabaseException("Unable to add collecting bank details");

		}finally{
			DBConnection.freeConnection(connection);
		}

		return true;
   }

   /**
    * This method is invoked by the addMLIDetails. After the addMLIDetails has
    * finished its operations, it invokes this method which allots the MLI an Id to
    * uniquely identify itself and mails it to the NO of the HO.
    * @return boolean
    * @throws DatabaseException
    * @roseuid 397817DC03B9
    */
   public String allotMLIId(MLIInfo mliDetails) throws DatabaseException
   {
		Connection connection=DBConnection.getConnection(false);
		String genMemberId=null;
		try{
			CallableStatement memberId=connection.prepareCall(
						"{call funcGenerateMemId(?,?,?,?,?,?,?,?)}");

			memberId.registerOutParameter(1,Types.INTEGER);
			memberId.registerOutParameter(5,Types.VARCHAR);//bankId
			memberId.registerOutParameter(6,Types.VARCHAR);//zoneId
			memberId.registerOutParameter(7,Types.VARCHAR);//branchId
			memberId.registerOutParameter(8,Types.VARCHAR);

			memberId.setString(2,mliDetails.getBankId());
			memberId.setString(3,mliDetails.getZoneName());
			memberId.setString(4,mliDetails.getBranchName());

			memberId.execute();
			int functionReturn=memberId.getInt(1);

			String error=memberId.getString(8);
			

			if(functionReturn==Constants.FUNCTION_FAILURE){

				connection.rollback();
				memberId.close();
				memberId=null;
				throw new DatabaseException(error);
			}
			else{
				if(error !=null) {
					throw new DatabaseException(error);
					
				}else {
				
					
					
					//System.out.println("memberId.getString(7).trim()"+memberId.getString(7));
					String bankId=memberId.getString(5).trim();
					Log.log(Log.DEBUG,"RegistrationDAO","allotMLIId","bankId "+bankId);
					
					String zoneId=memberId.getString(6);
					String branchId=memberId.getString(7);
					//String branchId=(String)memberId.getObject(7);
					//String zoneId="0116";
					//String branchId="0000";
					System.out.println("zoneId@@@@@@"+zoneId);
					Log.log(Log.DEBUG,"RegistrationDAO","allotMLIId","zoneId"+zoneId);
//					String branchId=memberId.getString(7).trim();
					Log.log(Log.DEBUG,"RegistrationDAO","allotMLIId","branchId"+branchId);
					System.out.println("branchId@@@@@@"+branchId);
					

					genMemberId=bankId+zoneId+branchId;

					mliDetails.setBankId(bankId);
					mliDetails.setZoneId(zoneId);
					mliDetails.setBranchId(branchId);
					
				}
				
			
			
			}

	  memberId.close();
	  memberId=null;

		}catch(SQLException e){

			try {
			  connection.rollback();
			}
			catch (SQLException ignore) {
			}

			Log.log(Log.ERROR,"RegDAO","allotMLIId",e.getMessage());

			Log.logException(e);

			throw new DatabaseException("Unable to allotMLIId ");

		}finally{
			DBConnection.freeConnection(connection);
		}
	 return genMemberId;
   }

   /**
    * This method is used to get all the collecting banks available in the database.
    * @return ArrayList
    * @throws DatabaseException
    * @roseuid 397817DC03BA
    */
   public ArrayList getCollectingBanks() throws DatabaseException
   {
	   ArrayList cBanks= new ArrayList();

		Connection connection=DBConnection.getConnection(false);
		try{
			CallableStatement collectingBanksList=connection.prepareCall(
					"{?=call packGetAllCollectingBank.funcGetAllCollectingBank(?,?)}");

			collectingBanksList.registerOutParameter(1,Types.INTEGER);
			collectingBanksList.registerOutParameter(2,Constants.CURSOR);
			collectingBanksList.registerOutParameter(3,Types.VARCHAR);

			collectingBanksList.execute();

			int functionReturn=collectingBanksList.getInt(1);

			String error=collectingBanksList.getString(3);

			if(functionReturn==Constants.FUNCTION_FAILURE){

				connection.rollback();

				collectingBanksList.close();

				collectingBanksList=null;

				throw new DatabaseException(error);
			}

			else{
			 	ResultSet cbResults=(ResultSet)collectingBanksList.getObject(2);

				while(cbResults.next()){
					String bank=cbResults.getString(1);
					String branch=cbResults.getString(2);
					String collectingBank=bank+","+branch;
					cBanks.add(collectingBank);
				}
				cbResults.close();
				cbResults=null;
	        }

		collectingBanksList.close();
		collectingBanksList=null;

		}catch(SQLException e){

					try {
					  connection.rollback();
					}
					catch (SQLException ignore) {
					}

					Log.log(Log.ERROR,"RegDAO","getCollectingBanks",e.getMessage());

					Log.logException(e);

					throw new DatabaseException("Unable to get CollectingBanks");
		}finally{
			DBConnection.freeConnection(connection);
		}

    	return cBanks;
   }

   /**
    * @param strBankId
    * @return ArrayList
    * @throws DatabaseException
    * @roseuid 397817DC03BB
    */
   public ArrayList getZones(String bankId) throws DatabaseException
   {
	Connection connection=DBConnection.getConnection(false);
	ArrayList zoneList=new ArrayList();
		   try{
			   CallableStatement zones=connection.prepareCall(
									   "{call PKGZONEFUNCGETZONESFORMLI(?,?,?)}");

			   zones.registerOutParameter(1,Types.INTEGER);
			   zones.setString(2,bankId);
			   zones.registerOutParameter(3,Types.VARCHAR);

			   zones.execute();

				int functionReturn=zones.getInt(1);

				String error=zones.getString(3);

				if(functionReturn==Constants.FUNCTION_FAILURE){

					zones.close();

					zones=null;

					throw new DatabaseException(error);
				}
			   else{
				   ResultSet zonesSet=zones.executeQuery();
				   while(zonesSet.next()){
						  MLIInfo mliInfo=new MLIInfo();
						  String zoneName=zonesSet.getString(3);
						  String memberId=zonesSet.getString(1).trim();
						  String zoneId=memberId.substring(4,8);
						  String reportingZoneId=zonesSet.getString(4);

							mliInfo.setBankId(bankId);
							mliInfo.setZoneId(zoneId);
							mliInfo.setBranchId("0000");
							mliInfo.setZoneName(zoneName);
							mliInfo.setReportingZoneID(reportingZoneId);
							zoneList.add(mliInfo);
				   }
				   zonesSet.close();
				   zonesSet=null;
			   }
		zones.close();
		zones=null;

		   }catch(SQLException e){
						Log.log(Log.ERROR,"RegDAO","getZones",e.getMessage());

 						Log.logException(e);

 						throw new DatabaseException("Unable to get Zones ");
		   }finally{
			   DBConnection.freeConnection(connection);
   }
   return zoneList;
   }
   /**
    * This method updates the database with the modified details of an existing
    * collecting bank.
    * @throws DatabaseException
    * @roseuid 397817DC03BD
    */
   public void modifyCollectingBank(MLIInfo mliInfo) throws DatabaseException
   {

		Connection connection=DBConnection.getConnection();

		try{
			CallableStatement modifyCB=connection.prepareCall(
								"{?=call funcUpdateCollectingBankForMem(?,?,?,?,?,?,?)}");
			modifyCB.registerOutParameter(1,Types.INTEGER);
			modifyCB.registerOutParameter(8,Types.VARCHAR);

			modifyCB.setString(2,mliInfo.getBankId());
			modifyCB.setString(3,mliInfo.getZoneId());
			modifyCB.setString(4,mliInfo.getBranchId());
			modifyCB.setString(5,mliInfo.getCollectingBankName());
			modifyCB.setString(6,mliInfo.getBranchName());
			modifyCB.setString(7,user.getUserId());

			modifyCB.executeQuery();

			int functionReturn=modifyCB.getInt(1);

			String error=modifyCB.getString(8);

			if(functionReturn==Constants.FUNCTION_FAILURE){

				modifyCB.close();

				modifyCB=null;

				throw new DatabaseException(error);
			}
		}
			catch(SQLException e){

					Log.log(Log.ERROR,"RegDAO","modifyCollectingBank",e.getMessage());

					Log.logException(e);

					throw new DatabaseException("Unable to modify Collecting Bank");
		}finally{
			DBConnection.freeConnection(connection);
		}
   }

   /**
    * This method is used to get all the collecting bank details for the given
    * collecting bank name.
    * @param cbName
    * @return registration.CollectingBank
    * @throws DatabaseException
    * @roseuid 397824C7037A
    */
   public CollectingBank getCollectingBankDtls(String cbName,String branchName) throws DatabaseException
   {
		 CollectingBank collectingBankDtls=new CollectingBank();
		 Connection connection=DBConnection.getConnection();
		 try{
			 CallableStatement cbDetails=connection.prepareCall(
				"{?=call funcGetCollectingBankDetails(?,?,?,?,?,?,?,?,?,?,?,?,?,?)}");
			 cbDetails.registerOutParameter(1,Types.INTEGER);
			 cbDetails.registerOutParameter(15,Types.VARCHAR);

			 cbDetails.registerOutParameter(4,Types.VARCHAR);//bankname
			 cbDetails.registerOutParameter(5,Types.VARCHAR);//branchname
			 cbDetails.registerOutParameter(6,Types.VARCHAR);//address
			 cbDetails.registerOutParameter(7,Types.VARCHAR);//city
			 cbDetails.registerOutParameter(8,Types.VARCHAR);//state
			 cbDetails.registerOutParameter(9,Types.VARCHAR);//phonecode
			 cbDetails.registerOutParameter(10,Types.VARCHAR);//phone no
			 cbDetails.registerOutParameter(11,Types.VARCHAR);//contact person
			 cbDetails.registerOutParameter(12,Types.VARCHAR);//emailid
			 cbDetails.registerOutParameter(13,Types.VARCHAR);//accountno
			 cbDetails.registerOutParameter(14,Types.VARCHAR);//hoaddress
			 cbDetails.setString(2,cbName);
			 cbDetails.setString(3,branchName);

			 cbDetails.execute();

			 int functionReturn=cbDetails.getInt(1);

			 String error=cbDetails.getString(15);

			 if(functionReturn==Constants.FUNCTION_FAILURE){

					cbDetails.close();

					cbDetails=null;

					throw new DatabaseException(error);
			}
			else{
				collectingBankDtls.setCollectingBankName(cbDetails.getString(4));
				collectingBankDtls.setBranchName(cbDetails.getString(5));
				collectingBankDtls.setAddress(cbDetails.getString(6));
				collectingBankDtls.setCity(cbDetails.getString(7));
				collectingBankDtls.setState(cbDetails.getString(8));
				collectingBankDtls.setPhoneStdCode(cbDetails.getString(9));
				collectingBankDtls.setPhone(cbDetails.getString(10));
				collectingBankDtls.setContactPerson(cbDetails.getString(11));
				collectingBankDtls.setEmailId(cbDetails.getString(12));
				collectingBankDtls.setAccNo(cbDetails.getString(13));
				collectingBankDtls.setHoAddress(cbDetails.getString(14));
			 }
	   cbDetails.close();
	   cbDetails=null;
		 }catch(SQLException e){
					Log.log(Log.ERROR,"RegDAO","getCollectingBankDtls",e.getMessage());

					Log.logException(e);

					throw new DatabaseException("Unable to get Collecting Bank Details ");
		}finally{
			DBConnection.freeConnection(connection);
		}
		 return collectingBankDtls;
   }

   /**
    * This method is used to retrieve all the member ids for the given state and
    * district.
    * @param state
    * @param district
    * @return ArrayList
    * @throws DatabaseException
    * @roseuid 39827CEA03C3
    */
   public ArrayList getAllMembers(String state, String district) throws DatabaseException
   {
    return null;
   }

   /**
    * This method is used to retrieve all the MLIs from the database.
    * @return ArrayList
    * @throws DatabaseException
    * @roseuid 3982973D0369
    */
   public ArrayList getAllMLIs() throws DatabaseException
   {

	    ArrayList returnAllMLIs=new ArrayList();
		Connection connection=DBConnection.getConnection();
		try{
				CallableStatement allMLIs=connection.prepareCall(
											"{call packGetAllMLI_funcGetAllMLI(?,?)}");
				allMLIs.registerOutParameter(1,Types.INTEGER);
				allMLIs.registerOutParameter(2,Types.VARCHAR);

				allMLIs.execute();

				int functionReturn=allMLIs.getInt(1);

				String error=allMLIs.getString(2);

				if(functionReturn==Constants.FUNCTION_FAILURE){

					allMLIs.close();

					allMLIs=null;

					throw new DatabaseException(error);
				}
				else{

					ResultSet mliResults=allMLIs.executeQuery();

					while(mliResults.next()){

						MLIInfo mliInfo=new MLIInfo();

						String memberId=mliResults.getString(1).trim();
						String bankId=memberId.substring(0,4);
						String zoneId=memberId.substring(4,8);
						String branchId=memberId.substring(8,12);

						mliInfo.setBankId(bankId);
						mliInfo.setZoneId(zoneId);
						mliInfo.setBranchId(branchId);

						mliInfo.setShortName(mliResults.getString(2));
						mliInfo.setBankName(mliResults.getString(3));

						returnAllMLIs.add(mliInfo);
					}
					mliResults.close();
					mliResults=null;

				}
		}catch(SQLException e){
					Log.log(Log.ERROR,"RegDAO","getAllMLIs",e.getMessage());

					Log.logException(e);

					throw new DatabaseException("Unable to get all MLIs ");
		}finally{
			DBConnection.freeConnection(connection);
		}

		return returnAllMLIs;
   }

   /**
    * This method is used to get all the branches of the given bank id and zone id.
    * @param bankId
    * @param zoneId
    * @return ArrayList
    * @throws DatabaseException
    * @roseuid 399E57C003DC
    * -----------------------------------------------------------
    * {N:B the parameter zone id was removed....ramesh rp14480.
    * ------------------------------------------------------------
    */
   public ArrayList getAllBranches(String bankId) throws DatabaseException
   {
		Connection connection=DBConnection.getConnection();
		ArrayList branchList=new ArrayList();
		try{
		   CallableStatement callable=connection.prepareCall(
								   "{call PKGBRANCHFUNCGETBRANCHFORMLI(?,?,?)}");

		   callable.registerOutParameter(1,Types.INTEGER);
		   callable.setString(2,bankId);
		  
		   callable.registerOutParameter(3,Types.VARCHAR);

		   callable.execute();

		   int functionReturn=callable.getInt(1);

		   String error=callable.getString(3);

		   if(functionReturn==Constants.FUNCTION_FAILURE){

			   callable.close();

			   callable=null;

			   throw new DatabaseException(error);
		   }
		   else{
					ResultSet result=callable.executeQuery();
					while(result.next()) {
					MLIInfo mliInfo=new MLIInfo();
					String branchName=result.getString(4);
					String memberId=result.getString(1).trim();
					String zoneId=memberId.substring(4,8);
					String branchId=memberId.substring(8,12);

					mliInfo.setBankId(bankId);
					mliInfo.setZoneId(zoneId);
					mliInfo.setBranchId(branchId);
					mliInfo.setBranchName(branchName);
					
					branchList.add(mliInfo);
					
					 }
					 result.close();
					 result=null;
			   }
		callable.close();
		callable=null;

		   }catch(SQLException e){
					Log.log(Log.ERROR,"RegDAO","getAllBranches",e.getMessage());

  					Log.logException(e);

  					throw new DatabaseException("Unable to get all Branches");
		   }finally{
			   DBConnection.freeConnection(connection);
			 }
			return branchList;

   }
   

   /**
    * This method is used to get all the members (HO,ZO,RO, and BO).
    * @return ArrayList
    * @throws DatabaseException
    * @roseuid 399E6B5A00DF
    */
   public ArrayList getAllMembers() throws DatabaseException
   {
		System.out.println("getAllMembers()");
		 ArrayList allMembers=new ArrayList();
		Connection connection=DBConnection.getConnection();
		try{
			CallableStatement allMember=connection.prepareCall(
									"{call PACKGETALLMEMBERSFUNCGETALLMEMBERS(?,?)}");
			allMember.registerOutParameter(1,Types.INTEGER);
//			allMember.registerOutParameter(2,Constants.CURSOR);
			allMember.registerOutParameter(2,Types.VARCHAR);

			allMember.execute();

			int functionReturn=allMember.getInt(1);

			String error=allMember.getString(2);

			if(functionReturn==Constants.FUNCTION_FAILURE){

				allMember.close();

				allMember=null;

				throw new DatabaseException(error);
			}

			else{
					ResultSet membersResult=allMember.executeQuery();
					while(membersResult.next())
					{
						MLIInfo mliInfo=new MLIInfo();

						String memberId=membersResult.getString(1).trim();

						String bankId=memberId.substring(0,4);
						String zoneId=memberId.substring(4,8);
						String branchId=memberId.substring(8,12);

						mliInfo.setBankId(bankId);
						mliInfo.setZoneId(zoneId);
						mliInfo.setBranchId(branchId);

						mliInfo.setShortName(membersResult.getString(2));
						mliInfo.setBankName(membersResult.getString(3));
						mliInfo.setBranchName(membersResult.getString(4));
						mliInfo.setZoneName(membersResult.getString(5));
						allMembers.add(mliInfo);

					}
					membersResult.close();
					membersResult=null;
			}
		allMember.close();
		allMember=null;

		}catch(SQLException e){
					Log.log(Log.ERROR,"RegDAO","getAllMembers",e.getMessage());

					Log.logException(e);

					throw new DatabaseException("Unable to get all Members ");
		}finally{
			DBConnection.freeConnection(connection);
		  }
		 return allMembers;
   }



 public ArrayList getAllMembersNew() throws DatabaseException
   {
		 ArrayList allMembers=new ArrayList();
		Connection connection=DBConnection.getConnection();
		try{
			CallableStatement allMember=connection.prepareCall(
									"{?=call packGetAllMembers.funcGetAllMembers(?,?)}");
			allMember.registerOutParameter(1,Types.INTEGER);
			allMember.registerOutParameter(2,Constants.CURSOR);
			allMember.registerOutParameter(3,Types.VARCHAR);

			allMember.execute();

			int functionReturn=allMember.getInt(1);

			String error=allMember.getString(3);

			if(functionReturn==Constants.FUNCTION_FAILURE){

				allMember.close();

				allMember=null;

				throw new DatabaseException(error);
			}

			else{
					ResultSet membersResult=(ResultSet)allMember.getObject(2);
					while(membersResult.next())
					{
						MLIInfo mliInfo=new MLIInfo();

						String memberId=membersResult.getString(1).trim();

						String bankId=memberId.substring(0,4);
						String zoneId=memberId.substring(4,8);
						String branchId=memberId.substring(8,12);

					//	mliInfo.setBankId(bankId);
					//	mliInfo.setZoneId(zoneId);
				//		mliInfo.setBranchId(branchId);

					//	mliInfo.setShortName(membersResult.getString(2));
						mliInfo.setBankName(membersResult.getString(3));
				//		mliInfo.setBranchName(membersResult.getString(4));
				//		mliInfo.setZoneName(membersResult.getString(5));

						allMembers.add(mliInfo);

					}
					membersResult.close();
					membersResult=null;
			}
		allMember.close();
		allMember=null;

		}catch(SQLException e){
					Log.log(Log.ERROR,"RegDAO","getAllMembers",e.getMessage());

					Log.logException(e);

					throw new DatabaseException("Unable to get all Members ");
		}finally{
			DBConnection.freeConnection(connection);
		  }
		 return allMembers;
   }


/**
   * 
   * @return 
   * @throws com.cgtsi.common.DatabaseException
   */
 public ArrayList getAllHOMembers() throws DatabaseException
 {
		 ArrayList allMembers=new ArrayList();
		Connection connection=DBConnection.getConnection();
		try{
			CallableStatement allMember=connection.prepareCall(
									"{call PACKGETALLMEMBERSFORHOFUNCGETALLMEMBERSFORHO(?,?)}");
			allMember.registerOutParameter(1,Types.INTEGER);
			
			allMember.registerOutParameter(2,Types.VARCHAR);

			allMember.execute();

			int functionReturn=allMember.getInt(1);

			String error=allMember.getString(2);

			if(functionReturn==Constants.FUNCTION_FAILURE){

				allMember.close();

				allMember=null;

				throw new DatabaseException(error);
			}

			else{
					ResultSet membersResult=allMember.executeQuery();
					while(membersResult.next())
					{
						MLIInfo mliInfo=new MLIInfo();

						String memberId=membersResult.getString(1).trim();

						String bankId=memberId.substring(0,4);
						String zoneId=memberId.substring(4,8);
						String branchId=memberId.substring(8,12);

						mliInfo.setBankId(bankId);
						mliInfo.setZoneId(zoneId);
						mliInfo.setBranchId(branchId);

						mliInfo.setShortName(membersResult.getString(2));
						mliInfo.setBankName(membersResult.getString(3));
						mliInfo.setBranchName(membersResult.getString(4));
						mliInfo.setZoneName(membersResult.getString(5));

						allMembers.add(mliInfo);

					}
					membersResult.close();
					membersResult=null;
			}
		allMember.close();
		allMember=null;

		}catch(SQLException e){
					Log.log(Log.ERROR,"RegDAO","getAllHOMembers",e.getMessage());

					Log.logException(e);

					throw new DatabaseException("Unable to get all Members ");
		}finally{
			DBConnection.freeConnection(connection);
		  }
		 return allMembers;
 }


   public ArrayList getMembersWithCb() throws DatabaseException
	  {
			ArrayList allMembers=new ArrayList();
		   Connection connection=DBConnection.getConnection();
		   try{
			   CallableStatement allMember=connection.prepareCall(
									   "{?=call packGetMLIhaveCB.funcGetMLICB(?,?)}");
			   allMember.registerOutParameter(1,Types.INTEGER);
			   allMember.registerOutParameter(2,Constants.CURSOR);
			   allMember.registerOutParameter(3,Types.VARCHAR);

			   allMember.execute();

				int functionReturn=allMember.getInt(1);

				String error=allMember.getString(3);

				if(functionReturn==Constants.FUNCTION_FAILURE){

					allMember.close();

					allMember=null;

					throw new DatabaseException(error);
				}
			  else{
					   ResultSet membersResult=(ResultSet)allMember.getObject(2);
					   while(membersResult.next()){
						   String memberId=membersResult.getString(1);
						   String MLIName=membersResult.getString(2);
						   String branchName=membersResult.getString(4);
						   String MLI=null;
						   if ((branchName!=null)&&!(branchName.equals(""))){

						   		MLI=MLIName +","+branchName+"(" + memberId + ")";
						   }
							   else
							   {
								MLI=MLIName +"(" + memberId + ")";
							   }
						   allMembers.add(MLI);

					   }
						membersResult.close();
						membersResult=null;
			   }
		allMember.close();

		allMember=null;

		   }catch(SQLException e){
			  		 Log.log(Log.ERROR,"RegDAO","getMembersWithCb",e.getMessage());

					Log.logException(e);

					throw new DatabaseException("Unable to get Members With Collecting bank ");
		   }finally{
			   DBConnection.freeConnection(connection);
  			 }
			return allMembers;
	  }

/**
   * 
   * @param appRefNo
   * @return 
   * @throws com.cgtsi.common.DatabaseException
   */
public double getExposureLimit(String bankId)throws DatabaseException
	{
		Log.log(Log.INFO,"ApplicationDAO","getAppRefNoCount","Entered");
		
		double exposureLimit =0;
		 PreparedStatement pStmt = null;
     ArrayList aList        = new ArrayList();
     ResultSet rsSet       = null;
     Connection connection           = DBConnection.getConnection();
     try
            { 
		String query = "SELECT total_limit FROM EXPOSURE_LIMITS where mem_bnk_id=?";
     pStmt   = connection.prepareStatement(query);
     pStmt.setString(1,bankId);
     rsSet = pStmt.executeQuery(); 
     while(rsSet.next()){
      exposureLimit =  rsSet.getDouble(1);
     }
      rsSet.close();
			pStmt.close();
		  System.out.println("exposureLimit-"+exposureLimit);
     }
		  catch(Exception exception)
            {
              Log.logException(exception);
              throw new DatabaseException(exception.getMessage());
            }
            finally
            {
              DBConnection.freeConnection(connection);
            }
		return exposureLimit;
	}





   /**
    * This method returns the collecting bank assigned to the given member id.
    * @param memberID
    * @return registration.CollectingBank
    * @throws DatabaseException
    * @roseuid 399E6CDE0079
    */
  public CollectingBank getCollectingBank(String memberId) throws DatabaseException
   {
		Connection connection=DBConnection.getConnection();

		CollectingBank collectingBank=new CollectingBank();
		
		String member = "";
		
        if(memberId != null && (memberId.contains("(") || memberId.contains(")")))
        {
		int start=memberId.indexOf("(");
		int finish=memberId.indexOf(")");

		 member=memberId.substring(start+1,finish);
		 
        } else
        {
            member = memberId;
        }

		//	Retrieve the bank,zone and branch ids from the memberId.

		String bankId=member.substring(0,4);
		String zoneId=member.substring(4,8);
		String branchId=member.substring(8,12);
			try{

				CallableStatement modifyCB=connection.prepareCall(
									"{?=call packGetCollectingBankForMember.funcGetCollectingBankForMember(?,?,?,?,?,?,?)}");

				modifyCB.registerOutParameter(1,Types.INTEGER);
				modifyCB.registerOutParameter(5,Types.VARCHAR);//bankname
				modifyCB.registerOutParameter(6,Types.VARCHAR);//branchname
				modifyCB.registerOutParameter(7,Types.VARCHAR);//accountno
				modifyCB.registerOutParameter(8,Types.VARCHAR);

				modifyCB.setString(2,bankId);
				modifyCB.setString(3,zoneId);
				modifyCB.setString(4,branchId);

				modifyCB.execute();

				String cbBank=modifyCB.getString(5);
				String cbBranch=modifyCB.getString(6);
				//collectingBank=cbBank+","+cbBranch;

				String accountNo=modifyCB.getString(7);

				int functionReturn=modifyCB.getInt(1);

				String error=modifyCB.getString(8);

				if(functionReturn==Constants.FUNCTION_FAILURE){

					modifyCB.close();

					modifyCB=null;

					throw new DatabaseException(error);
				}

				else{
					collectingBank.setCollectingBankName(cbBank);
					collectingBank.setBranchName(cbBranch);
					collectingBank.setAccNo(accountNo);
				}
		modifyCB.close();
		modifyCB=null;

			}catch(SQLException e){

				LogClass.writeExceptionOnFile(e);
						Log.log(Log.ERROR,"RegDAO","getCollectingBank",e.getMessage());

						Log.logException(e);

						throw new DatabaseException("Unable to get Collecting Bank ");
			}finally{
					DBConnection.freeConnection(connection);
				}

	return collectingBank;


   }

   /**
    * This method is used to assign a collecting bank for the selected member ids.
    * @param memberIds
    * @param cbName
    * @throws DatabaseException
    * @roseuid 399E6EFC03DF
    */
   public void assignCollectingBank(String memberId, String cbName,String createdBy) throws DatabaseException
   {

			Connection connection=DBConnection.getConnection();
			int comma=cbName.indexOf(",");
			int length=cbName.length();
			String collectingBank=cbName.substring(0,comma);
			String collectingBranch=cbName.substring(comma+1,length);
			try{
					//Retrieve the bank,zone and branch ids from the memberId.
					String bankId=memberId.substring(0,4);
					String zoneId=memberId.substring(4,8);
					String branchId=memberId.substring(8,12);

					CallableStatement assignCB=connection.prepareCall(
						"{?=call funcUpdateCollectingBankForMem(?,?,?,?,?,?,?)}");

					assignCB.registerOutParameter(1,Types.INTEGER);
					assignCB.registerOutParameter(8,Types.VARCHAR);

					assignCB.setString(2,bankId);
					assignCB.setString(3,zoneId);
					assignCB.setString(4,branchId);
					assignCB.setString(5,collectingBank);
					assignCB.setString(6,collectingBranch);
					assignCB.setString(7,createdBy);

					assignCB.execute();

					int functionReturn=assignCB.getInt(1);

					String error=assignCB.getString(8);

					if(functionReturn==Constants.FUNCTION_FAILURE){

						assignCB.close();

						assignCB=null;

						throw new DatabaseException(error);
					}

			assignCB.close();
			assignCB=null;

			}catch(SQLException e){

						Log.log(Log.ERROR,"RegDAO","assignCollectingBank",e.getMessage());

						Log.logException(e);

						throw new DatabaseException("Unable to assign Collecting Bank ");
				}finally{
					DBConnection.freeConnection(connection);
					}
   }




   public void assignNewExposureLimit(String bankId, double newExposureLimit) throws DatabaseException
   {

			Connection connection=DBConnection.getConnection();
				try{
					//Retrieve the bank,zone and branch ids from the memberId.
				
					CallableStatement assignCB=connection.prepareCall(
						"{call FUNCUPDATEEXPOSURELIMITFORMEM(?,?,?,?)}");

					assignCB.registerOutParameter(1,Types.INTEGER);
					assignCB.registerOutParameter(4,Types.VARCHAR);

					assignCB.setString(2,bankId);
					assignCB.setDouble(3,newExposureLimit);

					assignCB.execute();

					int functionReturn=assignCB.getInt(1);

					String error=assignCB.getString(4);

					if(functionReturn==Constants.FUNCTION_FAILURE){

						assignCB.close();

						assignCB=null;

						throw new DatabaseException(error);
					}

			assignCB.close();
			assignCB=null;

			}catch(SQLException e){

						Log.log(Log.ERROR,"RegDAO","assignNewExposureLimit",e.getMessage());

						Log.logException(e);

						throw new DatabaseException("Unable to assign New Exposure Limit");
				}finally{
					DBConnection.freeConnection(connection);
					}
   }


   /**
    * This method returns an ArrayList of member ids whom are not assigned with any
    * collecting bank
    * @return ArrayList
    * @throws DatabaseException
    * @roseuid 399E6F950321
    */
   public ArrayList getCBUnassignedMembers() throws DatabaseException
   {

	 	ArrayList cbUnassignedMembers=new ArrayList();
		Connection connection=DBConnection.getConnection();

		try{
			CallableStatement allMember=connection.prepareCall(
									"{?=call packGetMLIWithNOCB.funcGetMLIWithNOCB(?,?)}");
			allMember.registerOutParameter(1,Types.INTEGER);
			allMember.registerOutParameter(2,Constants.CURSOR);
			allMember.registerOutParameter(3,Types.VARCHAR);

			allMember.execute();

			int functionReturn=allMember.getInt(1);

			String error=allMember.getString(3);

			if(functionReturn==Constants.FUNCTION_FAILURE){

				allMember.close();

				allMember=null;

				throw new DatabaseException(error);
			}
			else{
					ResultSet membersResult=(ResultSet)allMember.getObject(2);
					while(membersResult.next()){
						String memberId=membersResult.getString(1);
						String MLIName=membersResult.getString(2);
						String branchName=membersResult.getString(4);
						String MLI=null;
						 if ((branchName!=null)&&!(branchName.equals(""))){

							MLI=MLIName +","+branchName+"(" + memberId + ")";
						}
					    else
						  {
							MLI=MLIName +"(" + memberId + ")";
					  }

						cbUnassignedMembers.add(MLI);

					}
					membersResult.close();
					membersResult=null;
			}

	allMember.close();
	allMember=null;

		}catch(SQLException e){
					Log.log(Log.ERROR,"RegDAO","getCBUnassignedMembers",e.getMessage());

					Log.logException(e);

					throw new DatabaseException("Unable to get CB Unassigned Members ");
		}finally{
			DBConnection.freeConnection(connection);
			}
	return cbUnassignedMembers;
   	}

   /**
    * This method update the modified collecting bank details into database.
    * @param collectingBank
    * @throws DatabaseException
    * @roseuid 399E84AA015F
    */
   public void updateCollectingBank(CollectingBank collectingBank,String createdBy) throws DatabaseException
   {
		Connection connection=DBConnection.getConnection();
		try{
			CallableStatement updatecollectingBankDetails=connection.prepareCall(
					"{?=call funcUpdateCollectingBank(?,?,?,?,?,?,?,?,?,?,?,?,?)}");

			updatecollectingBankDetails.registerOutParameter(1,Types.INTEGER);
			updatecollectingBankDetails.registerOutParameter(14,Types.VARCHAR);
			updatecollectingBankDetails.setString(2,collectingBank.getCollectingBankName());
			updatecollectingBankDetails.setString(3,collectingBank.getBranchName());
			updatecollectingBankDetails.setString(4,collectingBank.getAddress());
			updatecollectingBankDetails.setString(5,collectingBank.getCity());

			updatecollectingBankDetails.setString(6,collectingBank.getState());
			updatecollectingBankDetails.setString(7,collectingBank.getPhoneStdCode());

			updatecollectingBankDetails.setString(8,collectingBank.getPhone());

			updatecollectingBankDetails.setString(9,collectingBank.getContactPerson());
			updatecollectingBankDetails.setString(10,collectingBank.getEmailId());
			updatecollectingBankDetails.setString(11,collectingBank.getAccNo());

			updatecollectingBankDetails.setString(12,collectingBank.getHoAddress());
			updatecollectingBankDetails.setString(13,createdBy);

			updatecollectingBankDetails.execute();

			int functionReturn=updatecollectingBankDetails.getInt(1);

			String error=updatecollectingBankDetails.getString(14);

			if(functionReturn==Constants.FUNCTION_FAILURE){

				updatecollectingBankDetails.close();

				updatecollectingBankDetails=null;

				throw new DatabaseException(error);
			}

		updatecollectingBankDetails.close();
		updatecollectingBankDetails=null;

		}catch(SQLException e){

				Log.log(Log.ERROR,"RegDAO","updateCollectingBank",e.getMessage());

				Log.logException(e);

				throw new DatabaseException("Unable to update Collecting Bank ");
		}finally{
			DBConnection.freeConnection(connection);
		}


   }


/**
   * 
   * added by sukumar@path for capturing remote user login details
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
 public void updateLoginInformation(String bnkId,String zneId,String brnId,
                                      String ipAddress,String hostName,String proxyName,
                                      String sessionId,String createdBy) throws DatabaseException
   {
   
	 Connection connection=DBConnection.getConnection();
		try{
			CallableStatement updateLoginInfo=connection.prepareCall(
					"{call funcinslogindet(?,?,?,?,?,?,?,?,?,?)}");

			updateLoginInfo.registerOutParameter(1,Types.INTEGER);
			updateLoginInfo.registerOutParameter(10,Types.VARCHAR);
			updateLoginInfo.setString(2,bnkId);
            updateLoginInfo.setString(3,zneId);
            updateLoginInfo.setString(4,brnId);
			updateLoginInfo.setString(5,createdBy);
             updateLoginInfo.setString(6,sessionId);
            updateLoginInfo.setString(7,ipAddress);
            updateLoginInfo.setString(8,hostName);
            updateLoginInfo.setString(9,proxyName);		   
    	     updateLoginInfo.execute();
      
			int functionReturn=updateLoginInfo.getInt(1);
			String error=updateLoginInfo.getString(10);

			if(functionReturn==Constants.FUNCTION_FAILURE){

				updateLoginInfo.close();
				updateLoginInfo=null;

				throw new DatabaseException(error);
			}

		updateLoginInfo.close();
		updateLoginInfo=null;

		}catch(SQLException e){

				Log.log(Log.ERROR,"RegDAO","updateLoginInformation",e.getMessage());

				Log.logException(e);

				throw new DatabaseException("Unable to update Login Details for User Id:"+ createdBy + e.getMessage());
		}finally{
			DBConnection.freeConnection(connection);
		}

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
   
	 Connection connection=DBConnection.getConnection();
		try{
			CallableStatement updateLogoutInfo=connection.prepareCall(
					"{call funcinslogoutdet(?,?,?,?,?,?,?,?,?,?)}");

			updateLogoutInfo.registerOutParameter(1,Types.INTEGER);
			updateLogoutInfo.registerOutParameter(10,Types.VARCHAR);
			updateLogoutInfo.setString(2,bnkId);
      updateLogoutInfo.setString(3,zneId);
      updateLogoutInfo.setString(4,brnId);
			updateLogoutInfo.setString(5,createdBy);
      updateLogoutInfo.setString(6,sessionId);
      updateLogoutInfo.setString(7,ipAddress);
      updateLogoutInfo.setString(8,hostName);
      updateLogoutInfo.setString(9,proxyName);		   
    	updateLogoutInfo.execute();
      
			int functionReturn=updateLogoutInfo.getInt(1);
			String error=updateLogoutInfo.getString(10);

			if(functionReturn==Constants.FUNCTION_FAILURE){

				updateLogoutInfo.close();
				updateLogoutInfo=null;

				throw new DatabaseException(error);
			}

		updateLogoutInfo.close();
		updateLogoutInfo=null;

		}catch(SQLException e){

				Log.log(Log.ERROR,"RegDAO","updateLogoutInformation",e.getMessage());

				Log.logException(e);

				throw new DatabaseException("Unable to update Logout Details for User Id:"+ createdBy);
		}finally{
			DBConnection.freeConnection(connection);
		}

   }





   /**
    * This method is used to get all the regions of the given bank id.
    * @param bankId
    * @return ArrayList
    * @throws DatabaseException
    * @roseuid 39AF7CAD01F9
    */
   public ArrayList getAllRegions(String bankId) throws DatabaseException
   {
    return null;
   }

   /**
    * This method accepts the list of MLI Ids as parameters and retrieves the Email
    * Ids as an ArrayList.
    * @param mliIds
    * @return ArrayList
    * @throws DatabaseException
    * @roseuid 39B100D4028C
    */
   public ArrayList getEmailIds(ArrayList mliIds) throws DatabaseException
   {
    return null;
   }

   /**
    * This method is used to get all the collecting bank master details. All the
    * account branches are retrieved and given.
    * @return ArrayList
    * @throws DatabaseException
    * @roseuid 39B152D101A3
    */
   public ArrayList getCBMasterDtls() throws DatabaseException
   {
	     ArrayList cbMasterDtls=null;
	     return cbMasterDtls;
   }

	//Method to get the bank detials on passing the bank,zone,branch Ids
	//Nov 18 2003
	//Ramesh rp14480

	public MLIInfo getMemberDetails(String bankId,String zoneId,String branchId)throws DatabaseException
	 {
		 Connection connection=DBConnection.getConnection();
		 MLIInfo mliInfo=new MLIInfo();
		 try{

			 CallableStatement memberDtls=connection.prepareCall(
 "{call funcGetDetailsForMember_limit(?,?,?,?,?,?,?,?,?,?,?,"
                             + "?,?,?,?,?,?,?,?,?,?,?,"
                             + "?,?,?,?,?,?,?,?,?,?,?,"
                             + "?,?,?,?,?,?,?,?,?,?,?,"
                             + "?,?,?,?,?,?,?,?,?,?,?,"
                             + "?,?,?,?)}");

			 memberDtls.registerOutParameter(1,Types.INTEGER);


			 memberDtls.setString(2,bankId);
			 memberDtls.setString(3,zoneId);
			 memberDtls.setString(4,branchId);
			 memberDtls.registerOutParameter(5,Types.VARCHAR);//bankName
			 memberDtls.registerOutParameter(6,Types.VARCHAR);//branch name

			 memberDtls.registerOutParameter(7,Types.VARCHAR);//zone name
			 memberDtls.registerOutParameter(8,Types.VARCHAR);//short name
			 memberDtls.registerOutParameter(9,Types.VARCHAR);//city
			 memberDtls.registerOutParameter(10,Types.VARCHAR);//district
			 memberDtls.registerOutParameter(11,Types.VARCHAR);//state
			 memberDtls.registerOutParameter(12,Types.VARCHAR);//phonecode
			 memberDtls.registerOutParameter(13,Types.VARCHAR);//phone no
			 memberDtls.registerOutParameter(14,Types.VARCHAR);//email
			 memberDtls.registerOutParameter(15,Types.VARCHAR);//mcgf
			 memberDtls.registerOutParameter(16,Types.VARCHAR);//Dandelivery
			memberDtls.registerOutParameter(17,Types.VARCHAR);//Member Status

			 memberDtls.registerOutParameter(18,Types.VARCHAR);//Address
			memberDtls.registerOutParameter(19,Types.VARCHAR);//Pincode
			memberDtls.registerOutParameter(20,Types.VARCHAR);//Fax code
			memberDtls.registerOutParameter(21,Types.VARCHAR);//Fax number
			memberDtls.registerOutParameter(22,Types.VARCHAR);//ReportingZoneID
			memberDtls.registerOutParameter(23,Types.VARCHAR);
			
			//// New fields 28/06/2020
			
			
			
			
			memberDtls.registerOutParameter(24,Types.VARCHAR);
            memberDtls.registerOutParameter(25,Types.VARCHAR);
			memberDtls.registerOutParameter(26,Types.VARCHAR);
			memberDtls.registerOutParameter(27,Types.VARCHAR);
			memberDtls.registerOutParameter(28,Types.VARCHAR);
			memberDtls.registerOutParameter(29,Types.VARCHAR);
			
			memberDtls.registerOutParameter(30,Types.VARCHAR);
			memberDtls.registerOutParameter(31,Types.VARCHAR);
			memberDtls.registerOutParameter(32,Types.VARCHAR);
			memberDtls.registerOutParameter(33,Types.VARCHAR);
			memberDtls.registerOutParameter(34,Types.VARCHAR);
			memberDtls.registerOutParameter(35,Types.VARCHAR);
			
			memberDtls.registerOutParameter(36,Types.VARCHAR);
			memberDtls.registerOutParameter(37,Types.VARCHAR);
			memberDtls.registerOutParameter(38,Types.VARCHAR);
			memberDtls.registerOutParameter(39,Types.VARCHAR);
			memberDtls.registerOutParameter(40,Types.VARCHAR);
			memberDtls.registerOutParameter(41,Types.VARCHAR);
			
			memberDtls.registerOutParameter(42,Types.VARCHAR);
			memberDtls.registerOutParameter(43,Types.VARCHAR);
			memberDtls.registerOutParameter(44,Types.VARCHAR);
			memberDtls.registerOutParameter(45,Types.VARCHAR);
			memberDtls.registerOutParameter(46,Types.VARCHAR);
			memberDtls.registerOutParameter(47,Types.VARCHAR);
			
			memberDtls.registerOutParameter(48,Types.VARCHAR);
			memberDtls.registerOutParameter(49,Types.VARCHAR);
			memberDtls.registerOutParameter(50,Types.VARCHAR);
			memberDtls.registerOutParameter(51,Types.VARCHAR);
			memberDtls.registerOutParameter(52,Types.VARCHAR);
			memberDtls.registerOutParameter(53,Types.VARCHAR);
			
			memberDtls.registerOutParameter(54,Types.VARCHAR);
			memberDtls.registerOutParameter(55,Types.VARCHAR);
			memberDtls.registerOutParameter(56,Types.VARCHAR);
			memberDtls.registerOutParameter(57,Types.VARCHAR);
			memberDtls.registerOutParameter(58,Types.VARCHAR);
			memberDtls.registerOutParameter(59,Types.VARCHAR);
			
			
			
			
			//28/06/2020 NEW fields
			
			
			 memberDtls.execute();

			 mliInfo.setBankName(memberDtls.getString(5));
			 mliInfo.setBranchName(memberDtls.getString(6));
			 mliInfo.setZoneName(memberDtls.getString(7));
			 mliInfo.setShortName(memberDtls.getString(8));
			 mliInfo.setCity(memberDtls.getString(9));
			 mliInfo.setDistrict(memberDtls.getString(10));
			 mliInfo.setState(memberDtls.getString(11));
			 mliInfo.setPhoneStdCode(memberDtls.getString(12));
			 mliInfo.setPhone(memberDtls.getString(13));
			 mliInfo.setEmailId(memberDtls.getString(14));
			 mliInfo.setSupportMCGF(memberDtls.getString(15));
			 mliInfo.setDanDelivery(new String[]{memberDtls.getString(16)});
			 mliInfo.setStatus(memberDtls.getString(17));

			mliInfo.setAddress(memberDtls.getString(18));
			mliInfo.setPin(memberDtls.getString(19));
			mliInfo.setFaxStdCode(memberDtls.getString(20));
			mliInfo.setFax(memberDtls.getString(21));
			mliInfo.setReportingZoneID(memberDtls.getString(22));
			
			
           mliInfo.setZeroToTenLakhNoOfBorrowrs(memberDtls.getString(23));
			 mliInfo.setZeroToTenLakhTotAmt(memberDtls.getString(24));
           mliInfo.setZeroToTenLakhNoOfBorrowrsTC(memberDtls.getString(25));
           mliInfo.setZeroToTenLakhTotAmtTC(memberDtls.getString(26));
           mliInfo.setZeroToTenLakhNoOfBorrowrsWCTL(memberDtls.getString(27));
           mliInfo.setZeroToTenLakhTotAmtWCTL(memberDtls.getString(28));
           
           mliInfo.setTenToFiftyLakhNoOfBorrowrs(memberDtls.getString(29));
           mliInfo.setTenToFiftyLakhTotAmt(memberDtls.getString(30));
           mliInfo.setTenToFiftyLakhNoOfBorrowrsTC(memberDtls.getString(31));
           mliInfo.setTenToFiftyLakhTotAmtTC(memberDtls.getString(32));
           mliInfo.setTenToFiftyLakhNoOfBorrowrsWCTL(memberDtls.getString(33));
           mliInfo.setTenToFiftyLakhTotAmtWCTL(memberDtls.getString(34));
           
           
           mliInfo.setFiftyLakhToOneCrNoOfBorrowrs(memberDtls.getString(35));
           mliInfo.setFiftyLakhToOneCrTotAmt(memberDtls.getString(36));
           mliInfo.setFiftyLakhToOneCrNoOfBorrowrsTC(memberDtls.getString(37));
           mliInfo.setFiftyLakhToOneCrTotAmtTC(memberDtls.getString(38));
           mliInfo.setFiftyLakhToOneCrNoOfBorrowrsWCTL(memberDtls.getString(39));
           mliInfo.setFiftyLakhToOneCrTotAmtWCTL(memberDtls.getString(40));
           
           
           mliInfo.setOneCrToFiveCrNoOfBorrowrs(memberDtls.getString(41));
           mliInfo.setOneCrToFiveCrTotAmt(memberDtls.getString(42));
          mliInfo.setOneCrToFiveCrNoOfBorrowrsTC(memberDtls.getString(43));
           mliInfo.setOneCrToFiveCrTotAmtTC(memberDtls.getString(44));
           mliInfo.setOneCrToFiveCrNoOfBorrowrsWCTL(memberDtls.getString(45));
           mliInfo.setOneCrToFiveCrTotAmtWCTL(memberDtls.getString(46));
           
           
           mliInfo.setFiveCrToFifteenCrNoOfBorrowrs(memberDtls.getString(47));
           mliInfo.setFiveCrToFifteenCrTotAmt(memberDtls.getString(48));
           mliInfo.setFiveCrToFifteenCrNoOfBorrowrsTC(memberDtls.getString(49));
           mliInfo.setFiveCrToFifteenCrTotAmtTC(memberDtls.getString(50));
           mliInfo.setFiveCrToFifteenCrNoOfBorrowrsWCTL(memberDtls.getString(51));
           mliInfo.setFiveCrToFifteenCrTotAmtWCTL(memberDtls.getString(52));
           
           
           mliInfo.setFifteenCrToTwentyFiveCrNoOfBorrowrs(memberDtls.getString(53));
           mliInfo.setFifteenCrToTwentyFiveCrTotAmt(memberDtls.getString(54));
           mliInfo.setFifteenCrToTwentyFiveCrNoOfBorrowrsTC(memberDtls.getString(55));
           mliInfo.setFifteenCrToTwentyFiveCrTotAmtTC(memberDtls.getString(56));
           mliInfo.setFifteenCrToTwentyFiveCrNoOfBorrowrsWCTL(memberDtls.getString(57));
           mliInfo.setFifteenCrToTwentyFiveCrTotAmtWCTL(memberDtls.getString(58));
           
           


			int functionReturn=memberDtls.getInt(1);

			String error=memberDtls.getString(59);

			if(functionReturn==Constants.FUNCTION_FAILURE){

				memberDtls.close();

				memberDtls=null;

				throw new DatabaseException(error);
			}
		memberDtls.close();
		memberDtls=null;
		}catch(SQLException e){

				Log.log(Log.ERROR,"RegDAO","getMemberDetails",e.getMessage());

				Log.logException(e);

				throw new DatabaseException("Unable to get Member Details ");
		 	} finally{
			 DBConnection.freeConnection(connection);
		 }
		 return mliInfo;
	 }
   
   
 /**
   * 
   * @param bankId
   * @param zoneId
   * @param branchId
   * @return 
   * @throws com.cgtsi.common.DatabaseException
   */
   
   public MLIInfo getMemberDetailsNew(String bankId,String zoneId,String branchId)throws DatabaseException
	 {

		 Connection connection=DBConnection.getConnection();
		 MLIInfo mliInfo=new MLIInfo();
		 try{

			 CallableStatement memberDtls=connection.prepareCall(
						 "{?=call funcGetDetailsForMemberNew(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)}");

			 memberDtls.registerOutParameter(1,Types.INTEGER);


			 memberDtls.setString(2,bankId);
			 memberDtls.setString(3,zoneId);
			 memberDtls.setString(4,branchId);
			 memberDtls.registerOutParameter(5,Types.VARCHAR);//bankName
			 memberDtls.registerOutParameter(6,Types.VARCHAR);//branch name

			 memberDtls.registerOutParameter(7,Types.VARCHAR);//zone name
			 memberDtls.registerOutParameter(8,Types.VARCHAR);//short name
			 memberDtls.registerOutParameter(9,Types.VARCHAR);//city
			 memberDtls.registerOutParameter(10,Types.VARCHAR);//district
			 memberDtls.registerOutParameter(11,Types.VARCHAR);//state
			 memberDtls.registerOutParameter(12,Types.VARCHAR);//phonecode
			 memberDtls.registerOutParameter(13,Types.VARCHAR);//phone no
			 memberDtls.registerOutParameter(14,Types.VARCHAR);//email
			 memberDtls.registerOutParameter(15,Types.VARCHAR);//mcgf
			 memberDtls.registerOutParameter(16,Types.VARCHAR);//Dandelivery
			memberDtls.registerOutParameter(17,Types.VARCHAR);//Member Status

			 memberDtls.registerOutParameter(18,Types.VARCHAR);//Address
			memberDtls.registerOutParameter(19,Types.VARCHAR);//Pincode
			memberDtls.registerOutParameter(20,Types.VARCHAR);//Fax code
			memberDtls.registerOutParameter(21,Types.VARCHAR);//Fax number
			memberDtls.registerOutParameter(22,Types.VARCHAR);//ReportingZoneID
      memberDtls.registerOutParameter(23,Types.VARCHAR);
			memberDtls.registerOutParameter(24,Types.VARCHAR);
			 memberDtls.execute();

			 mliInfo.setBankName(memberDtls.getString(5));
			 mliInfo.setBranchName(memberDtls.getString(6));
			 mliInfo.setZoneName(memberDtls.getString(7));
			 mliInfo.setShortName(memberDtls.getString(8));
			 mliInfo.setCity(memberDtls.getString(9));
			 mliInfo.setDistrict(memberDtls.getString(10));
			 mliInfo.setState(memberDtls.getString(11));
			 mliInfo.setPhoneStdCode(memberDtls.getString(12));
			 mliInfo.setPhone(memberDtls.getString(13));
			 mliInfo.setEmailId(memberDtls.getString(14));
			 mliInfo.setSupportMCGF(memberDtls.getString(15));
			 mliInfo.setDanDelivery(new String[]{memberDtls.getString(16)});
			 mliInfo.setStatus(memberDtls.getString(17));

			mliInfo.setAddress(memberDtls.getString(18));
			mliInfo.setPin(memberDtls.getString(19));
			mliInfo.setFaxStdCode(memberDtls.getString(20));
			mliInfo.setFax(memberDtls.getString(21));
			mliInfo.setReportingZoneID(memberDtls.getString(22));
      mliInfo.setFlag(memberDtls.getString(23));

			int functionReturn=memberDtls.getInt(1);

			String error=memberDtls.getString(24);

			if(functionReturn==Constants.FUNCTION_FAILURE){

				memberDtls.close();

				memberDtls=null;

				throw new DatabaseException(error);
			}
		memberDtls.close();
		memberDtls=null;
		}catch(SQLException e){

				Log.log(Log.ERROR,"RegDAO","getMemberDetailsNew",e.getMessage());

				Log.logException(e);

				throw new DatabaseException("Unable to get Member Details ");
		 	} finally{
			 DBConnection.freeConnection(connection);
		 }
		 return mliInfo;
	 }
   /**
    * This method returns all the MLI Names for a given State.
	*
    * @return ArrayList
    */
   public ArrayList getMLINamesForState(String sState) throws DatabaseException
   {
	   Connection connection;
	   CallableStatement getMliStmt;
	   ResultSet getMli;
	   ArrayList mliNames=null;
	   int getMliStatus=0;
	   String getMliErr="";
	   		connection=DBConnection.getConnection();
	   try
	   {
			getMliStmt=connection.prepareCall("{?= call packGetExposureCriteria.funcGetAllMLIs(?,?,?)}");

			getMliStmt.registerOutParameter(1, java.sql.Types.INTEGER);
			getMliStmt.setString(2, sState);
			getMliStmt.registerOutParameter(3, Constants.CURSOR);
			getMliStmt.registerOutParameter(4, java.sql.Types.VARCHAR);

			getMliStmt.execute();
			getMliStatus=getMliStmt.getInt(1);
			//System.out.println("get mli status -- " + getMliStatus);
			if (getMliStatus!=0)
			{
				getMliErr=getMliStmt.getString(4);
				//System.out.println("get mli exception -- " + getMliErr);
				throw new DatabaseException(getMliErr);
			}
			mliNames=new ArrayList();
			getMli=(ResultSet) getMliStmt.getObject(3);
			while (getMli.next())
			{
				String mli=getMli.getString("mem_short_name")+" ("+getMli.getString("member")+")";
				mliNames.add(mli);
			}
			getMli.close();
			getMli = null;
	   }
	   catch (SQLException sqlException)
	   {
		   throw new DatabaseException("Unable to get MLIs for state");
	   }
	   finally
	   {
		   DBConnection.freeConnection(connection);
	   }
		//System.out.println("mli names from dao -- " + mliNames);
		return mliNames;
   }

   public MLIInfo getMemberDetails(String userId)throws DatabaseException
   {
  	   Log.log(Log.INFO,"RegistrationDAO","getMemberDetails","Entered");

	   Connection connection=DBConnection.getConnection();
	   MLIInfo mliInfo=new MLIInfo();

	   try
	   {
		   CallableStatement memberDtls=connection.prepareCall(
					   "{call PKGgETmeminfoforusRfuncGetMemberInfoForUsr(?,?,?)}");

		   memberDtls.registerOutParameter(1,Types.INTEGER);
		   memberDtls.setString(2,userId);
		   //memberDtls.registerOutParameter(3,Constants.CURSOR);
		   memberDtls.registerOutParameter(3,Types.VARCHAR);

		   memberDtls.execute();

		   int errorCode=memberDtls.getInt(1);
		   String error=memberDtls.getString(3);

		   Log.log(Log.DEBUG,"RegistrationDAO","getMemberDetails","error code and error are "+errorCode+" "+error);

		   if(errorCode==Constants.FUNCTION_FAILURE)
		   {
		   		memberDtls.close();
				memberDtls=null;

				Log.log(Log.ERROR,"RegistrationDAO","getMemberDetails",error);
		   		throw new DatabaseException(error);
		   }

		   ResultSet memberInfo=memberDtls.executeQuery();

		   while(memberInfo.next())
		   {
				mliInfo.setBankId(memberInfo.getString(1));
				mliInfo.setZoneId(memberInfo.getString(2));
				mliInfo.setBranchId(memberInfo.getString(3));
				mliInfo.setBankName(memberInfo.getString(4));
				mliInfo.setZoneName(memberInfo.getString(5));
				mliInfo.setBranchName(memberInfo.getString(6));
				mliInfo.setShortName(memberInfo.getString(7));
				mliInfo.setAddress(memberInfo.getString(8));
				mliInfo.setCity(memberInfo.getString(9));
				mliInfo.setPin(memberInfo.getString(10));
				mliInfo.setDistrict(memberInfo.getString(11));
				mliInfo.setState(memberInfo.getString(12));
				mliInfo.setPhoneStdCode(memberInfo.getString(13));
				mliInfo.setPhone(memberInfo.getString(14));

				mliInfo.setFaxStdCode(memberInfo.getString(15));
				mliInfo.setFax(memberInfo.getString(16));
				mliInfo.setEmailId(memberInfo.getString(17));

				mliInfo.setStatus(memberInfo.getString(18));

				mliInfo.setMcgf(memberInfo.getString(19));
				mliInfo.setDanDelivery(new String[]{memberInfo.getString(20)});
				mliInfo.setReportingZone(memberInfo.getString(21));
				Log.log(Log.DEBUG,"RegistrationDAO","getMemberDetails","bank id "+mliInfo.getBankId());
				Log.log(Log.DEBUG,"RegistrationDAO","getMemberDetails","zone id "+mliInfo.getZoneId());
				Log.log(Log.DEBUG,"RegistrationDAO","getMemberDetails","branch id "+mliInfo.getBranchId());
				Log.log(Log.DEBUG,"RegistrationDAO","getMemberDetails","address "+mliInfo.getAddress());
				Log.log(Log.DEBUG,"RegistrationDAO","getMemberDetails","bank Name "+mliInfo.getBankName());
				Log.log(Log.DEBUG,"RegistrationDAO","getMemberDetails","MCGF "+mliInfo.getMcgf());
				Log.log(Log.DEBUG,"RegistrationDAO","getMemberDetails","support MCGF "+mliInfo.getSupportMCGF());
				Log.log(Log.DEBUG,"RegistrationDAO","getMemberDetails","Dan Delivery "+mliInfo.getDanDelivery());
		   }

		   memberInfo.close();
		   memberInfo=null;

		   memberDtls.close();
		   memberDtls=null;

	   }
	   catch(SQLException sqlException)
	   {
			Log.log(Log.ERROR,"RegistrationDAO","getMemberDetails",sqlException.getMessage());
			Log.logException(sqlException);

		    throw new DatabaseException("Unable to get Member Information...");
	   }
	   finally
	   {
		   DBConnection.freeConnection(connection);
	   }

	   Log.log(Log.INFO,"RegistrationDAO","getMemberDetails","Exited");

	   return mliInfo;
   }

   public MLIInfo getAllMemberDetails(String bankId,String zoneId,String branchId)throws DatabaseException
	   {

		   Connection connection=DBConnection.getConnection();
		   MLIInfo mliInfo=new MLIInfo();
		   try{

			   CallableStatement memberDtls=connection.prepareCall(
						   "{?=call funcGetAllDetailsForMember(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)}");

			   memberDtls.registerOutParameter(1,Types.INTEGER);


			   memberDtls.setString(2,bankId);
			   memberDtls.setString(3,zoneId);
			   memberDtls.setString(4,branchId);
			   memberDtls.registerOutParameter(5,Types.VARCHAR);//bankName
			   memberDtls.registerOutParameter(6,Types.VARCHAR);//branch name

			   memberDtls.registerOutParameter(7,Types.VARCHAR);//zone name
			   memberDtls.registerOutParameter(8,Types.VARCHAR);//short name
			   memberDtls.registerOutParameter(9,Types.VARCHAR);//city
			   memberDtls.registerOutParameter(10,Types.VARCHAR);//district
			   memberDtls.registerOutParameter(11,Types.VARCHAR);//state
			   memberDtls.registerOutParameter(12,Types.VARCHAR);//phonecode
			   memberDtls.registerOutParameter(13,Types.VARCHAR);//phone no
			   memberDtls.registerOutParameter(14,Types.VARCHAR);//email
			   memberDtls.registerOutParameter(15,Types.VARCHAR);//mcgf
			   memberDtls.registerOutParameter(16,Types.VARCHAR);//Dandelivery
			  memberDtls.registerOutParameter(17,Types.VARCHAR);//Member Status
			   memberDtls.registerOutParameter(18,Types.VARCHAR);

			   memberDtls.execute();

			   mliInfo.setBankName(memberDtls.getString(5));
			   mliInfo.setBranchName(memberDtls.getString(6));
			   mliInfo.setZoneName(memberDtls.getString(7));
			   mliInfo.setShortName(memberDtls.getString(8));
			   mliInfo.setCity(memberDtls.getString(9));
			   mliInfo.setDistrict(memberDtls.getString(10));
			   mliInfo.setState(memberDtls.getString(11));
			   mliInfo.setPhoneStdCode(memberDtls.getString(12));
			   mliInfo.setPhone(memberDtls.getString(13));
			   mliInfo.setEmailId(memberDtls.getString(14));
			   mliInfo.setMcgf(memberDtls.getString(15));
			   mliInfo.setDanDelivery(new String[]{memberDtls.getString(16)});
			   mliInfo.setStatus(memberDtls.getString(17));

			  int functionReturn=memberDtls.getInt(1);

			  String error=memberDtls.getString(18);

			  if(functionReturn==Constants.FUNCTION_FAILURE){

				  memberDtls.close();

				  memberDtls=null;

				  throw new DatabaseException(error);
			  }
		  memberDtls.close();
		  memberDtls=null;
		  }catch(SQLException e){

				  Log.log(Log.ERROR,"RegDAO","getMemberDetails",e.getMessage());

				  Log.logException(e);

				  throw new DatabaseException("Unable to get Member Details ");
			  } finally{
			   DBConnection.freeConnection(connection);
		   }
		   return mliInfo;
	   }

/**
   * 
   * @param createdby
   * @param mliDetails
   * @throws com.cgtsi.common.DatabaseException
   */
   public void updateMemberAddressDetails(String createdby,MLIInfo mliDetails,String emailId)throws DatabaseException
   {

	   Connection connection=DBConnection.getConnection();
		//The dan delivery checkbox values are received as a String array and concatenated.

	   try{

		   CallableStatement memberDtls=connection.prepareCall(
					   "{call funcMLIAddrDetail(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)}");

		   memberDtls.registerOutParameter(1,Types.INTEGER);

		    memberDtls.setString(2,mliDetails.getBankId());
		    memberDtls.setString(3,mliDetails.getZoneId());
		    memberDtls.setString(4,mliDetails.getBranchId());
		//	memberDtls.setString(5,mliDetails.getBankName());
		//	memberDtls.setString(6,mliDetails.getZoneName());
		//	memberDtls.setString(7,mliDetails.getBranchName());
			memberDtls.setString(5,mliDetails.getAddress());
			memberDtls.setString(6,mliDetails.getCity());
			memberDtls.setString(7,mliDetails.getPin());
			memberDtls.setString(8,mliDetails.getDistrict());
			memberDtls.setString(9,mliDetails.getState());
			memberDtls.setString(10,mliDetails.getPhoneStdCode());
		    memberDtls.setString(11,mliDetails.getPhone());
			memberDtls.setString(12,mliDetails.getFaxStdCode());
		    memberDtls.setString(13,mliDetails.getFax());
		    memberDtls.setString(14,emailId);
		    memberDtls.setString(15,createdby);
		
		   memberDtls.registerOutParameter(16,Types.VARCHAR);

		   memberDtls.execute();

		  int functionReturn=memberDtls.getInt(1);

		  String error=memberDtls.getString(16);

		  if(functionReturn==Constants.FUNCTION_FAILURE){

		  	 connection.rollback();

			  memberDtls.close();

			  memberDtls=null;

			  throw new DatabaseException(error);
		  }

	  connection.commit();
	  memberDtls.close();
	  memberDtls=null;

	  }catch(SQLException e){

			  Log.log(Log.ERROR,"RegDAO","updateMemberAddressDetails",e.getMessage());

			  Log.logException(e);

			  throw new DatabaseException("Unable to update Member Address Details ");
		  }

		   finally{
		   DBConnection.freeConnection(connection);
	   }
   }

/*This method is used to update the member details.
 *
 * @author RP14480
 *  *
 *
 *
 */

   public void updateMemberDetails(String createdby,MLIInfo mliDetails)throws DatabaseException
   {

	   Connection connection=DBConnection.getConnection();
		//The dan delivery checkbox values are received as a String array and concatenated.

	/*
	 * String[]danDelivery=mliDetails.getDanDelivery(); int size=danDelivery.length;
	 * String finalVal=""; for(int i=0;i<size;i++){ String value=danDelivery[i];
	 * if((value!=null)&&!(value.equals(""))){ finalVal=finalVal+value+","; } } int
	 * len= finalVal.length(); String danValue=finalVal.substring(0,len-1);
	 */
	   try{

		   CallableStatement memberDtls=connection.prepareCall(
 "{call FUNCUPDMEMDETAIL(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)}");

		   memberDtls.registerOutParameter(1,Types.INTEGER);

		    memberDtls.setString(2,mliDetails.getBankId());
		    memberDtls.setString(3,mliDetails.getZoneId());
		    memberDtls.setString(4,mliDetails.getBranchId());
			memberDtls.setString(5,mliDetails.getBankName());
			memberDtls.setString(6,mliDetails.getZoneName());
			memberDtls.setString(7,mliDetails.getBranchName());
			memberDtls.setString(8,mliDetails.getAddress());
			memberDtls.setString(9,mliDetails.getCity());
			memberDtls.setString(10,mliDetails.getPin());
			memberDtls.setString(11,mliDetails.getDistrict());
			memberDtls.setString(12,mliDetails.getState());
			memberDtls.setString(13,mliDetails.getPhoneStdCode());
		    memberDtls.setString(14,mliDetails.getPhone());
			memberDtls.setString(15,mliDetails.getFaxStdCode());
		    memberDtls.setString(16,mliDetails.getFax());
		    memberDtls.setString(17,mliDetails.getEmail());
		    memberDtls.setString(18,mliDetails.getSupportMCGF());
			memberDtls.setString(19,"");
			memberDtls.setString(20,createdby);
			
			double vZeroToTenLakhNoOfBorrowrs=Double.parseDouble(mliDetails.getZeroToTenLakhNoOfBorrowrs());
			memberDtls.setDouble(21,vZeroToTenLakhNoOfBorrowrs);
			double vZeroToTenLakhTotAmt=Double.parseDouble(mliDetails.getZeroToTenLakhTotAmt());
			memberDtls.setDouble(22,vZeroToTenLakhTotAmt);
			double vZeroToTenLakhNoOfBorrowrsTc=Double.parseDouble(mliDetails.getZeroToTenLakhNoOfBorrowrsTC());
			memberDtls.setDouble(23,vZeroToTenLakhNoOfBorrowrsTc);
			double vZeroToTenLakhTotAmtTC=Double.parseDouble(mliDetails.getZeroToTenLakhTotAmtTC());
			memberDtls.setDouble(24,vZeroToTenLakhTotAmtTC);
			double vZeroToTenLakhNoOfBorrowrsWCTL=Double.parseDouble(mliDetails.getZeroToTenLakhNoOfBorrowrsWCTL());
			memberDtls.setDouble(25,vZeroToTenLakhNoOfBorrowrsWCTL);
			double vZeroToTenLakhTotAmtWCTL=Double.parseDouble(mliDetails.getZeroToTenLakhTotAmtWCTL());
			memberDtls.setDouble(26,vZeroToTenLakhTotAmtWCTL);
			
		
			double vTenToFiftyLakhNoOfBorrowrs=Double.parseDouble(mliDetails.getTenToFiftyLakhNoOfBorrowrs());
			memberDtls.setDouble(27,vTenToFiftyLakhNoOfBorrowrs);
			double vTenToFiftyLakhTotAmt=Double.parseDouble(mliDetails.getTenToFiftyLakhTotAmt());
			memberDtls.setDouble(28,vTenToFiftyLakhTotAmt);
			double vTenToFiftyLakhNoOfBorrowrsTC=Double.parseDouble(mliDetails.getTenToFiftyLakhNoOfBorrowrsTC());
			memberDtls.setDouble(29,vTenToFiftyLakhNoOfBorrowrsTC);
			double vTenToFiftyLakhTotAmtTC=Double.parseDouble(mliDetails.getTenToFiftyLakhTotAmtTC());
			memberDtls.setDouble(30,vTenToFiftyLakhTotAmtTC);
			double vTenToFiftyLakhNoOfBorrowrsWCTL=Double.parseDouble(mliDetails.getTenToFiftyLakhNoOfBorrowrsWCTL());
			memberDtls.setDouble(31,vTenToFiftyLakhNoOfBorrowrsWCTL);
			double vTenToFiftyLakhTotAmtWCTL=Double.parseDouble(mliDetails.getTenToFiftyLakhTotAmtWCTL());
			memberDtls.setDouble(32,vTenToFiftyLakhTotAmtWCTL);
			
			
			
			
			double vFiftyLakhToOneCrNoOfBorrowrs=Double.parseDouble(mliDetails.getFiftyLakhToOneCrNoOfBorrowrs());
			memberDtls.setDouble(33,vFiftyLakhToOneCrNoOfBorrowrs);
			double vFiftyLakhToOneCrTotAmt=Double.parseDouble(mliDetails.getFiftyLakhToOneCrTotAmt());
			memberDtls.setDouble(34,vFiftyLakhToOneCrTotAmt);
			double vFiftyLakhToOneCrNoOfBorrowrsTC=Double.parseDouble(mliDetails.getFiftyLakhToOneCrNoOfBorrowrsTC());
			memberDtls.setDouble(35,vFiftyLakhToOneCrNoOfBorrowrsTC);
			double vFiftyLakhToOneCrTotAmtTC=Double.parseDouble(mliDetails.getFiftyLakhToOneCrTotAmtTC());
			memberDtls.setDouble(36,vFiftyLakhToOneCrTotAmtTC);
			double vFiftyLakhToOneCrNoOfBorrowrsWCTL=Double.parseDouble(mliDetails.getFiftyLakhToOneCrNoOfBorrowrsWCTL());
			memberDtls.setDouble(37,vFiftyLakhToOneCrNoOfBorrowrsWCTL);
			double vFiftyLakhToOneCrTotAmtWCTL=Double.parseDouble(mliDetails.getFiftyLakhToOneCrTotAmtWCTL());
			memberDtls.setDouble(38,vFiftyLakhToOneCrTotAmtWCTL);
			
			
			double vOneCrToFiveCrNoOfBorrowrs=Double.parseDouble(mliDetails.getOneCrToFiveCrNoOfBorrowrs());
			memberDtls.setDouble(39,vOneCrToFiveCrNoOfBorrowrs);
			double vOneCrToFiveCrTotAmt=Double.parseDouble(mliDetails.getOneCrToFiveCrTotAmt());
			memberDtls.setDouble(40,vOneCrToFiveCrTotAmt);
			double vOneCrToFiveCrNoOfBorrowrsTC=Double.parseDouble(mliDetails.getOneCrToFiveCrNoOfBorrowrsTC());
			memberDtls.setDouble(41,vOneCrToFiveCrNoOfBorrowrsTC);
			double vOneCrToFiveCrTotAmtTC=Double.parseDouble(mliDetails.getOneCrToFiveCrTotAmtTC());
			memberDtls.setDouble(42,vOneCrToFiveCrTotAmtTC);
		
			double vOneCrToFiveCrNoOfBorrowrsWCTL=Double.parseDouble(mliDetails.getOneCrToFiveCrNoOfBorrowrsWCTL());
			memberDtls.setDouble(43,vOneCrToFiveCrNoOfBorrowrsWCTL);
			double vOneCrToFiveCrTotAmtWCTL=Double.parseDouble(mliDetails.getOneCrToFiveCrTotAmtWCTL());
			memberDtls.setDouble(44,vOneCrToFiveCrTotAmtWCTL);
			
			
			double vFiveCrToFifteenCrNoOfBorrowrs=Double.parseDouble(mliDetails.getFiveCrToFifteenCrNoOfBorrowrs());
			memberDtls.setDouble(45,vFiveCrToFifteenCrNoOfBorrowrs);
			double vFiveCrToFifteenCrTotAmt=Double.parseDouble(mliDetails.getFiveCrToFifteenCrTotAmt());
			memberDtls.setDouble(46,vFiveCrToFifteenCrTotAmt);
			double vFiveCrToFifteenCrNoOfBorrowrsTC=Double.parseDouble(mliDetails.getFiveCrToFifteenCrNoOfBorrowrsTC());
			memberDtls.setDouble(47,vFiveCrToFifteenCrNoOfBorrowrsTC);
			double vFiveCrToFifteenCrTotAmtTC=Double.parseDouble(mliDetails.getFiveCrToFifteenCrTotAmtTC());
			memberDtls.setDouble(48,vFiveCrToFifteenCrTotAmtTC);
			double vFiveCrToFifteenCrNoOfBorrowrsWCTL=Double.parseDouble(mliDetails.getFiveCrToFifteenCrNoOfBorrowrsWCTL());
			memberDtls.setDouble(49,vFiveCrToFifteenCrNoOfBorrowrsWCTL);
			double vFiveCrToFifteenCrTotAmtWCTL=Double.parseDouble(mliDetails.getFiveCrToFifteenCrTotAmtWCTL());
			memberDtls.setDouble(50,vFiveCrToFifteenCrTotAmtWCTL);
			
			
			double vFifteenCrToTwentyFiveCrNoOfBorrowrs=Double.parseDouble(mliDetails.getFifteenCrToTwentyFiveCrNoOfBorrowrs());
			memberDtls.setDouble(51,vFifteenCrToTwentyFiveCrNoOfBorrowrs);
			double vFifteenCrToTwentyFiveCrTotAmt=Double.parseDouble(mliDetails.getFifteenCrToTwentyFiveCrTotAmt());
			memberDtls.setDouble(52,vFifteenCrToTwentyFiveCrTotAmt);
			double vFifteenCrToTwentyFiveCrNoOfBorrowrsTC=Double.parseDouble(mliDetails.getFifteenCrToTwentyFiveCrNoOfBorrowrsTC());
			memberDtls.setDouble(53,vFifteenCrToTwentyFiveCrNoOfBorrowrsTC);
			double vFifteenCrToTwentyFiveCrTotAmtTC=Double.parseDouble(mliDetails.getFifteenCrToTwentyFiveCrTotAmtTC());
			memberDtls.setDouble(54,vFifteenCrToTwentyFiveCrTotAmtTC);
			double vFifteenCrToTwentyFiveCrNoOfBorrowrsWCTL=Double.parseDouble(mliDetails.getFifteenCrToTwentyFiveCrNoOfBorrowrsWCTL());
			memberDtls.setDouble(55,vFifteenCrToTwentyFiveCrNoOfBorrowrsWCTL);
			double vFifteenCrToTwentyFiveCrTotAmtWCTL=Double.parseDouble(mliDetails.getFifteenCrToTwentyFiveCrTotAmtWCTL());
			memberDtls.setDouble(56,vFifteenCrToTwentyFiveCrTotAmtWCTL);


		   memberDtls.registerOutParameter(57,Types.VARCHAR);

		   memberDtls.execute();

		  int functionReturn=memberDtls.getInt(1);

		  String error=memberDtls.getString(57);

		  if(functionReturn==Constants.FUNCTION_FAILURE){

		  	 connection.rollback();

			  memberDtls.close();

			  memberDtls=null;

			  throw new DatabaseException(error);
		  }

//	  connection.commit();
	  memberDtls.close();
	  memberDtls=null;

	  }catch(SQLException e){

			  Log.log(Log.ERROR,"RegDAO","updateMemberDetails",e.getMessage());

			  Log.logException(e);

			  throw new DatabaseException("Unable to update Member Details ");
		  }

		   finally{
		   DBConnection.freeConnection(connection);
	   }
   }

 
 public ArrayList getDepartments()
 throws DatabaseException
{
 ArrayList departments;
 Connection connection;
 departments = new ArrayList();
 connection = DBConnection.getConnection();
 try
 {
     CallableStatement allMember = connection.prepareCall("{?=call PACKQUERYTRANSACTIONS.funcGetCGTDepartments(?,?)}");
     allMember.registerOutParameter(1, 4);
     allMember.registerOutParameter(2, -10);
     allMember.registerOutParameter(3, 12);
     String department = "";
     allMember.execute();
     int functionReturn = allMember.getInt(1);
     String error = allMember.getString(3);
     if(functionReturn == 1)
     {
         allMember.close();
         allMember = null;
         throw new DatabaseException(error);
     }
     ResultSet membersResult;
     MLIInfo mliInfo;
     for(membersResult = (ResultSet)allMember.getObject(2); membersResult.next(); departments.add(mliInfo))
     {
         mliInfo = new MLIInfo();
         department = membersResult.getString(1);
         System.out.println(department);
         mliInfo.setDepartment(department);
     }

     membersResult.close();
     membersResult = null;
     allMember.close();
     allMember = null;
 }
 catch(SQLException e)
 {
     Log.log(2, "RegDAO", "getAllMembers", e.getMessage());
     Log.logException(e);
     throw new DatabaseException("Unable to get all Members ");
 }
finally
{
 DBConnection.freeConnection(connection);
}
 return departments;
}
/*Added by Kailash For GST Changes*/
/*public ArrayList getBranchCode(String bankId) {

		Log.log(Log.INFO, "RegistationDAO", "getBranchCode", "Entered");

		PreparedStatement pStmt = null;
		ArrayList aList = new ArrayList();
		ResultSet rsSet = null;
		Connection connection = DBConnection.getConnection();
		try {
			String query = "SELECT BRANCH_CODE FROM REPUSER.MEMBER_BANK_BRANCH_MASTER where MEM_BNK_ID=?";
			pStmt = connection.prepareStatement(query);
			pStmt.setString(1, bankId);
			rsSet = pStmt.executeQuery();
			while (rsSet.next()) {				
				aList.add(rsSet.getString(1));
			}
			rsSet.close();
			pStmt.close();
		} catch (Exception exception) {
			Log.logException(exception);
			try {
				throw new DatabaseException(exception.getMessage());
			} catch (DatabaseException e) {
				e.printStackTrace();
			}
		} finally {
			DBConnection.freeConnection(connection);
		}
		return aList;

	}*/

public List getGSTStateList(String bankId) throws DatabaseException {

	Connection connection=DBConnection.getConnection();

    List<MLIInfo> statesList=new ArrayList<MLIInfo>();
    /*  MLIInfo info=new MLIInfo();
    info.setStateCode("");
    info.setStateName("Branch State");
    statesList.add(0, info);*/
    
    Log.log(Log.INFO, "RegistationDAO", "getStateList", "Entered");
	/*MLIInfo mliinfo1=new MLIInfo();
	mliinfo1.setGstNo("");
	mliinfo1.setState("Please Select State of Bank Id");
    statesList.add(0, mliinfo1);*/
	PreparedStatement pStmt = null;
	ResultSet rsSet = null;	
	try {
		String query = "select  gst.STE_CODE as state_code, case when HEAD_OFFICE ='Y' then sm.STE_NAME||'(HEAD OFFICE)' else sm.STE_NAME end as state_name,gst.GST_NO as gstNo  from MEMBER_BANK_STATE_GST gst, state_master sm where gst.ste_code = sm.ste_code AND GST.MEM_BNK_ID=?";
		pStmt = connection.prepareStatement(query);
		pStmt.setString(1, bankId);
		rsSet = pStmt.executeQuery();
		while (rsSet.next()) {		
			MLIInfo mliinfo=new MLIInfo();

        	mliinfo.setStateCode(rsSet.getString(1));
        	mliinfo.setStateName(rsSet.getString(2));
        	mliinfo.setGstNo(rsSet.getString(3));

        	statesList.add(mliinfo);
		}
		rsSet.close();
		pStmt.close();
	} catch (Exception exception) {
		Log.logException(exception);
		try {
			throw new DatabaseException(exception.getMessage());
		} catch (DatabaseException e) {
			e.printStackTrace();
		}
	} finally {
		DBConnection.freeConnection(connection);
	
	}
	return statesList;
         
						
}
public String getGstNo(String stateCode,String bankId) {
    Connection connection=DBConnection.getConnection();

    System.out.println("Bank id and State Code "+stateCode+" "+bankId);
    Log.log(Log.INFO, "RegistationDAO", "getGstNo", "Entered");
	String gstNo="";
	PreparedStatement pStmt = null;
	ResultSet rsSet = null;
	if((stateCode != null || !stateCode.equals("")) && (bankId != null || !bankId.equals(""))){

	try {
		String query = "select gst.GST_NO from MEMBER_BANK_STATE_GST gst, state_master sm where gst.ste_code = sm.ste_code AND gst.ste_code= ? and MEM_BNK_ID=? ";
		
		System.out.println("query ---"+query);
		pStmt = connection.prepareStatement(query);
		pStmt.setString(1, stateCode);
		pStmt.setString(2, bankId);

		rsSet = pStmt.executeQuery();
		while (rsSet.next()) {		

			gstNo=rsSet.getString(1);
		}
		rsSet.close();
		pStmt.close();
	} catch (Exception exception) {
		Log.logException(exception);
		try {
			throw new DatabaseException(exception.getMessage());
		} catch (DatabaseException e) {
			e.printStackTrace();
		}
	} finally {
		DBConnection.freeConnection(connection);
	}
	}
	return gstNo;						
  }

public String getBranchStateCode(String memberId) {
    Connection connection=DBConnection.getConnection();

    System.out.println("Bank id and State Code "+memberId+" ");
    Log.log(Log.INFO, "RegistationDAO", "getGstNo", "Entered");
	String brnStateCode="";
	PreparedStatement pStmt = null;
	ResultSet rsSet = null;
	if(memberId != null){

	try {
		//String query = "select gst.GST_NO from MEMBER_BANK_STATE_GST gst, state_master sm where gst.ste_code = sm.ste_code AND gst.ste_code= ? and MEM_BNK_ID=? ";
		String query = "select MEM_STATE_NAME from member_info where  CONCAT(MEM_BNK_ID,mem_zne_id,mem_brn_id)=? ";
		
		System.out.println("query ---"+query);
		pStmt = connection.prepareStatement(query);
		pStmt.setString(1, memberId);

		rsSet = pStmt.executeQuery();
		while (rsSet.next()) {		

			brnStateCode=rsSet.getString(1);
		}
		rsSet.close();
		pStmt.close();
	} catch (Exception exception) {
		Log.logException(exception);
		try {
			throw new DatabaseException(exception.getMessage());
		} catch (DatabaseException e) {
			e.printStackTrace();
		}
	} finally {
		DBConnection.freeConnection(connection);
	}
	}
	return brnStateCode;						
  }

public String getBranchGstNo(String memberId) {
    Connection connection=DBConnection.getConnection();

    System.out.println("Bank id and State Code "+memberId+" ");
    Log.log(Log.INFO, "RegistationDAO", "getGstNo", "Entered");
	String brnGstNO="";
	PreparedStatement pStmt = null;
	ResultSet rsSet = null;
	if(memberId != null){

	try {
		//String query = "select gst.GST_NO from MEMBER_BANK_STATE_GST gst, state_master sm where gst.ste_code = sm.ste_code AND gst.ste_code= ? and MEM_BNK_ID=? ";
		String query = "select BRN_GST_NO from member_info where  CONCAT(MEM_BNK_ID,mem_zne_id,mem_brn_id)=? ";
		
		System.out.println("query ---"+query);
		pStmt = connection.prepareStatement(query);
		pStmt.setString(1, memberId);

		rsSet = pStmt.executeQuery();
		while (rsSet.next()) {		

			brnGstNO=rsSet.getString(1);
		}
		rsSet.close();
		pStmt.close();
	} catch (Exception exception) {
		Log.logException(exception);
		try {
			throw new DatabaseException(exception.getMessage());
		} catch (DatabaseException e) {
			e.printStackTrace();
		}
	} finally {
		DBConnection.freeConnection(connection);
	}
	}
	return brnGstNO;						
  }


}





