package com.cgtsi.guaranteemaintenance;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Types;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.TreeMap;
import java.util.Vector;

import org.apache.commons.validator.GenericValidator;

import com.cgtsi.actionform.APForm;
import com.cgtsi.actionform.NPADateModificationActionForm;
import com.cgtsi.admin.User;
import com.cgtsi.application.ApplicationDAO;
import com.cgtsi.application.ApplicationProcessor;
import com.cgtsi.application.BorrowerDetails;
import com.cgtsi.application.LogClass;
import com.cgtsi.application.NoApplicationFoundException;
import com.cgtsi.application.SSIDetails;
import com.cgtsi.claim.ClaimConstants;
import com.cgtsi.common.Constants;
import com.cgtsi.common.DatabaseException;
import com.cgtsi.common.Log;
import com.cgtsi.receiptspayments.DANSummary;
import com.cgtsi.receiptspayments.DemandAdvice;
import com.cgtsi.registration.MLIInfo;
import com.cgtsi.util.DBConnection;
import com.cgtsi.util.DateHelper;

public class GMDAO
{
  public Disbursement getDisbursementDetailsForCgpan(String cgpan)
    throws DatabaseException
  {
    Log.log(4, "GMDAO", "getDisbursementDetailsForCgpan", "Entered");
    Connection connection = DBConnection.getConnection();
    CallableStatement getDisbursementDetailStmt = null;
    String exception = null;

    Disbursement disbursement = null;
    ArrayList listOfDisbursementAmount = new ArrayList();
    try
    {
      getDisbursementDetailStmt = connection.prepareCall("{?=call packGetDtlsforDBR.funcGetDtlsForCGPAN(?,?,?)}");
      getDisbursementDetailStmt.registerOutParameter(1, 4);
      Log.log(5, "GMDAO", "getDisbursementDetailsForCgpan", "CGPAN :" + cgpan);
      getDisbursementDetailStmt.setString(2, cgpan);
      getDisbursementDetailStmt.registerOutParameter(3, -10);
      getDisbursementDetailStmt.registerOutParameter(4, 12);

      getDisbursementDetailStmt.executeQuery();

      exception = getDisbursementDetailStmt.getString(4);
      Log.log(5, "GMDAO", "getDisbursementDetailsForCgpan", "exception for" + cgpan + "-->" + exception);

      int error = getDisbursementDetailStmt.getInt(1);

      if (error == 1)
      {
        getDisbursementDetailStmt.close();
        getDisbursementDetailStmt = null;
        Log.log(5, "GMDAO", "getDisbursementDetailsForCgpan", "Exception :" + exception);
        connection.rollback();
        throw new DatabaseException(exception);
      }
      ResultSet resultSet = (ResultSet)getDisbursementDetailStmt.getObject(3);

      while (resultSet.next())
      {
        disbursement = new Disbursement();
        disbursement.setCgpan(resultSet.getString(2));
        disbursement.setScheme(resultSet.getString(3));
        disbursement.setSanctionedAmount(resultSet.getDouble(5));
        Log.log(5, "GMDAO", "getDisbursementDetailsForCgpan", "disbursement amt added");
      }
      resultSet.close();
      resultSet = null;

      getDisbursementDetailStmt.close();
      getDisbursementDetailStmt = null;

      getDisbursementDetailStmt = connection.prepareCall("{?=call packGetPIDBRDtlsCGPAN.funcDBRDetailsForCGPAN(?,?,?)}");

      getDisbursementDetailStmt.registerOutParameter(1, 4);
      getDisbursementDetailStmt.setString(2, cgpan);
      getDisbursementDetailStmt.registerOutParameter(3, -10);
      getDisbursementDetailStmt.registerOutParameter(4, 12);

      getDisbursementDetailStmt.execute();

      exception = getDisbursementDetailStmt.getString(4);
      Log.log(5, "GMDAO", "getDisbursementDetailsForCgpan", "exception for" + cgpan + "-->" + exception);

      error = getDisbursementDetailStmt.getInt(1);

      if (error == 1)
      {
        getDisbursementDetailStmt.close();
        getDisbursementDetailStmt = null;
        Log.log(2, "GMDAO", "getDisbursementDetailsForCgpan", "Exception" + exception);
        connection.rollback();
        throw new DatabaseException(exception);
      }

      resultSet = (ResultSet)getDisbursementDetailStmt.getObject(3);
      DisbursementAmount disbursementAmount = null;

      while (resultSet.next())
      {
        disbursementAmount = new DisbursementAmount();
        disbursementAmount.setCgpan(cgpan);
        disbursementAmount.setDisbursementId(resultSet.getString(1));
        disbursementAmount.setDisbursementAmount(resultSet.getDouble(2));
        disbursementAmount.setDisbursementDate(DateHelper.sqlToUtilDate(resultSet.getDate(3)));
        disbursementAmount.setFinalDisbursement(resultSet.getString(4));
        listOfDisbursementAmount.add(disbursementAmount);
      }

      if (listOfDisbursementAmount.size() != 0)
      {
        disbursement.setDisbursementAmounts(listOfDisbursementAmount);
      }

      Log.log(5, "GMDAO", "getDisbursementDetailsForCgpan", "Disbursement Details added");

      resultSet.close();
      resultSet = null;
      getDisbursementDetailStmt.close();
      getDisbursementDetailStmt = null;
    }
    catch (Exception e)
    {
      Log.logException(e);
      throw new DatabaseException(e.getMessage());
    }
    finally
    {
      DBConnection.freeConnection(connection);
    }
    ResultSet resultSet;
    Log.log(4, "GMDAO", "getDisbursementDetailsForCgpan", "Exited");

    return disbursement;
  }
public boolean checkDateParsing(String date)
{
	boolean flag=true;
	SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
	try
	{
		sdf.parse(date);
	}
	catch(Exception e)
	{
		flag=false;
	}
	return flag;
}
  public String CheckNPAUpgradationDateValidation(String cgpan,String npaUpgradationDate,String dateOnwhichAccountBecomeNPA)
  {
	  String message="";
	  Date dtClaimSubmitionDate =null;
	//  //System.out.println(npaUpgradationDate);
	 // //System.out.println(dateOnwhichAccountBecomeNPA);
	  SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
	  String query="SELECT to_char((c.clm_date),'dd/MM/yyyy' ) ,c1.bid FROM claim_detail_temp c left join claim_detail c1 on" +
	  		" c.bid=c1.bid where c.bid in (SELECT bid FROM ssi_detail where SSI_REFERENCE_NUMBER =" +
	  		"(SELECT SSI_REFERENCE_NUMBER FROM application_detail where cgpan='"+cgpan+"'))";
	  
	  String query1="select to_char((clm_date),'dd/MM/yyyy' ) from application_detail a,ssi_detail s,claim_detail_temp c where a.SSI_REFERENCE_NUMBER=s.SSI_REFERENCE_NUMBER and s.bid=c.bid and a.cgpan='"+cgpan+"'";
	  Connection connection =null;
	  ResultSet rsNpaUpGradationDetails=null;
	  ResultSet rsNpaUpGradationDetails1=null;
	  
	 boolean flag=false;
	  
	  try
	  {
		  String currentDate = sdf.format(new Date()); 
		  Date dtCurrentDate = sdf.parse(currentDate);
		  connection = DBConnection.getConnection();
		  Statement statement = connection.createStatement();
		  if(checkDateParsing(npaUpgradationDate)==true)
		  {
			// //System.out.println("inside 1st if");
	      	Date dtNpaUpgradationDate = sdf.parse(npaUpgradationDate);
	    
	     	
	      
	      	if(dtNpaUpgradationDate.compareTo(dtCurrentDate)>0)
	      	{
	      		////System.out.println("Date1 is after Date2");
	      		message="NPA-Upgradation date should not be greater than current date.";
	      	}
	      //	//System.out.println("message value "+message);
	      	if(message.equals(""))
	      	{
	      	//	//System.out.println("message is blank");
	      		Date dtdateOnwhichAccountBecomeNPA = sdf.parse(dateOnwhichAccountBecomeNPA);
	      		
	      	
	      		
	      		if(message.equals(""))
		      	{
	      		 
				  
				  rsNpaUpGradationDetails=statement.executeQuery(query);
				  if(rsNpaUpGradationDetails.next())
				  {
					//  //System.out.println("Claim Submitiondate "+rsNpaUpGradationDetails.getString(1));
					  if(rsNpaUpGradationDetails.getString(2)!=null && rsNpaUpGradationDetails.getString(2).trim().equals(""))
					  {
						 
						  if( checkDateParsing(rsNpaUpGradationDetails.getString(1))==true)
						  {
							//  //System.out.println("Claim Submitiondate else 1 "+dtNpaUpgradationDate.compareTo(dtClaimSubmitionDate));
							  
							  dtClaimSubmitionDate=sdf.parse(rsNpaUpGradationDetails.getString(1));
								if((dtNpaUpgradationDate.compareTo(dtClaimSubmitionDate)<0) || (dtNpaUpgradationDate.compareTo(dtClaimSubmitionDate)==0)){
									message="NPA-Upgradation date should not be less than or equl to claim submition date.";
								}															
						  }					
					  }
					
				  }	
		      	}
	      		

	      		if(message.equals(""))
		      	{
	      			rsNpaUpGradationDetails1=statement.executeQuery(query1);
					  if(rsNpaUpGradationDetails1.next())
					  {
						 // //System.out.println("CLM DATE "+rsNpaUpGradationDetails1.getString(1));
						  // if count > 0 then NPA upgradation date between claim submition date and todays date[including today]
							// else  NPA upgradation date between npa reporting date and todays date[including today]
						  dtClaimSubmitionDate=sdf.parse(rsNpaUpGradationDetails1.getString(1));
						  if((dtNpaUpgradationDate.compareTo(dtClaimSubmitionDate)<0) || (dtNpaUpgradationDate.compareTo(dtClaimSubmitionDate)==0))
						  {
							  message="NPA-Upgradation date should not be less than or equal to claim submition date.";
						  }	
						  flag=true;	
					  }	
					  else
					  {
						   
					  }
					  
					  
		      	}
	      		
	      		if(flag==false)
	      		{
		      		if(dtNpaUpgradationDate.compareTo(dtdateOnwhichAccountBecomeNPA)>0){
		        		////System.out.println("Date1 is after Date2");
		        	}else if(dtNpaUpgradationDate.compareTo(dtdateOnwhichAccountBecomeNPA)<0){
		        		////System.out.println("Date1 is before Date2");
		        		message="NPA-Upgradation date should not be less than Account turned NPA date.";
		        	}else if(dtNpaUpgradationDate.compareTo(dtdateOnwhichAccountBecomeNPA)==0){
		        		////System.out.println("Date1 is equal to Date2");
		        		message="NPA-Upgradation date should not be equal to Account turned NPA date.";
		        	}
	      		}
				
	      	}
		  }
		  else
		  {
			  message="Invalid NPA-Upgradation date.";
		  }
	  }
	  catch(Exception e)
	  {
		 // e.printStackTrace();
	  }
	  finally {
			DBConnection.freeConnection(connection);

		}
	 // //System.out.println("final message value"+message);
	  if(message.equals(""))
	  {
		  message="ok";
	  }
	  return message;
  }
  public void saveNPAXXXDeatails(String cgpan, String memberId , String npaxxxDate , String remarks, String userID,String npaFormType,String newNpaEffectiveDate)
  {
	//  //System.out.println("saveNPAXXXDeatails"+cgpan);
	//  //System.out.println("saveNPAXXXDeatails memberId"+memberId);
	  LogClass.StepWritter("inside saveNPAXXXDeatails");
	  Connection connection =null;
	  try
	  {
		  connection = DBConnection.getConnection();
		  SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy");
		  Date currentDt = new Date();
		  PreparedStatement ps = null;
		  GMProcessor gmProcessor = new GMProcessor();
		  String upgradationInsertQry = "insert into NPA_UPGRADATION_DETAIL" +
		  			"(NUD_ID,NPA_ID,CGPAN,MEM_BNK_ID,MEM_ZNE_ID,MEM_BRN_ID,NPA_EFFECTIVE_DT,NPA_UPGRADE_DT" +
		  			",NUD_USER_REMARKS,NUD_UPGRADE_CHANG_FLAG,NUD_MLI_LWR_LEV_IN_BY,NUD_MLI_LWR_LEV_IN_DT)"+
		  			"values(CGTSITEMPUSER.NPA_UPGRADE_SEQ.nextval,?,?,?,?,?,?,?,?,?,?,?)";
		  
		  String modificationInsertQry = "insert into NPA_DATE_CHANGE_DETAIL" +
			"(NDC_ID,NPA_ID,CGPAN,MEM_BNK_ID,MEM_ZNE_ID,MEM_BRN_ID,NPA_EFFECTIVE_DT,NDC_UPGRADE_DT,NDC_NEW_NPA_EFFECTIVE_DT " +
			",NDC_USER_REMARKS,NDC_NPA_DT_CHANG_FLAG,NDC_MLI_LWR_LEV_IN_BY,NDC_MLI_LWR_LEV_IN_DT)"+
			"values(CGTSITEMPUSER.NPA_UPGRADE_SEQ.nextval,?,?,?,?,?,?,?,?,?,?,?,?)";
		//  //System.out.println("qry"+upgradationInsertQry);
		  SimpleDateFormat format = new SimpleDateFormat("dd/MM/yyyy");
		  Date parsednpaxxxDate = format.parse(npaxxxDate);
		  
		  Statement str = connection
			.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE,
					ResultSet.CONCUR_READ_ONLY);
		  
		 
			NPADateModificationActionForm objNPADateModificationActionForm[]=null;
			ArrayList<NPADateModificationActionForm> arrayListNPADateModificationActionForm= new ArrayList<NPADateModificationActionForm>();
			arrayListNPADateModificationActionForm=gmProcessor.getNPADetails(cgpan,memberId);
			////System.out.println("saveNPAXXXDeatails arrayListNPADateModificationActionForm"+arrayListNPADateModificationActionForm.size());
			Iterator itr=null;
			itr=arrayListNPADateModificationActionForm.iterator();  
		//	LogClass.StepWritter("inside saveNPAXXXDeatails arrayListNPADateModificationActionForm size"+arrayListNPADateModificationActionForm.size());
			while(itr.hasNext())
			{ 
				NPADateModificationActionForm st=(NPADateModificationActionForm)itr.next();  
				////System.out.println("Npa status"+st.getApplicationStatus());	
				LogClass.StepWritter("inside saveNPAXXXDeatails NPA status"+st.getApplicationStatus());
				if(!st.getApplicationStatus().equalsIgnoreCase("CL"))
				{
					if(npaFormType.equalsIgnoreCase("upgradation"))
					{
						ps = connection.prepareStatement(upgradationInsertQry);	
						LogClass.StepWritter("inside saveNPAXXXDeatails upgradation"+upgradationInsertQry);
					}
					else if(npaFormType.equalsIgnoreCase("modification"))
					{
						ps = connection.prepareStatement(modificationInsertQry);
						LogClass.StepWritter("inside saveNPAXXXDeatails modification"+modificationInsertQry);
					}
					
					ps.setString(1, st.getNpaID());
					ps.setString(2, st.getCgPan());
					ps.setString(3, st.getMliID().substring(0, 4));
					ps.setString(4, st.getMliID().substring(4, 8));
					ps.setString(5, st.getMliID().substring(8, 12));
				//	ps.setDate(6, new java.sql.Date(st.getNpaDate().getTime()));
					LogClass.StepWritter("inside saveNPAXXXDeatails getLstNpaDate result"+st.getLstNpaDate());
					Date date = formatter.parse(st.getLstNpaDate());
					//ps.setString(6, st.getLstNpaDate());
					ps.setDate(6, new java.sql.Date(date.getTime()));
					ps.setDate(7, new java.sql.Date(parsednpaxxxDate.getTime()));
					
					if(npaFormType.equalsIgnoreCase("modification"))
					{
						Date parsednewNpaEffectiveDate = format.parse(newNpaEffectiveDate);
						ps.setDate(8, new java.sql.Date(parsednewNpaEffectiveDate.getTime()));
						ps.setString(9, remarks);
						ps.setString(10, "LN");
						ps.setString(11, userID);
						ps.setDate(12, new java.sql.Date(currentDt.getTime()));	
					}
					else if(npaFormType.equalsIgnoreCase("upgradation"))
					{
						ps.setString(8, remarks);
						ps.setString(9, "LN");
						ps.setString(10, userID);
						ps.setDate(11, new java.sql.Date(currentDt.getTime()));		
					}				
							
					int no = ps.executeUpdate();
				//	////System.out.println("update result "+no);
					LogClass.StepWritter("inside saveNPAXXXDeatails update result"+no);
					
				}
				
			}
			connection.commit();

      		
			
		//str.executeUpdate(queryStr);
	  }
	  catch(Exception e)
	  {
		  LogClass.writeExceptionOnFile(e);
		  e.printStackTrace();
	  }
	  finally {
			DBConnection.freeConnection(connection);

		}
  }
  
  public String getNewNPADtWithExpiryDtMethod(String cgpan,String newNPADate)
  {
	  //////System.out.println("getNewNPADtWithExpiryDtMethod called "+newNPADate);
	  SimpleDateFormat mdyFormat = new SimpleDateFormat("dd/MM/yyyy");
	  String message="";
	  int result=0;
	  Connection connection =null;	  
	  ResultSet rsNpaUpGradationDetails=null,rsNpaUpGradationDetails1=null,rsNpaUpGradationDetails2=null;
	  Statement stNpaUpGradationDetails=null;
	  try
	  {
		  connection = DBConnection.getConnection();
		  stNpaUpGradationDetails=connection.createStatement();
		  String query1="select  MAX(APP_EXPIRY_DT)  from  application_detail a,ssi_detail b,npa_detail_temp c where A.SSI_REFERENCE_NUMBER=b.SSI_REFERENCE_NUMBER "+
		  " and b.bid=c.bid  and a.SSI_REFERENCE_NUMBER in (select SSI_REFERENCE_NUMBER from application_detail where cgpan='"+cgpan+"' ) ";
		  rsNpaUpGradationDetails=stNpaUpGradationDetails.executeQuery(query1);
		  if(rsNpaUpGradationDetails.next())
		  {
			  ////System.out.println("New Npa date "+rsNpaUpGradationDetails.getDate(1));
			  String APP_EXPIRY_DT = mdyFormat.format(rsNpaUpGradationDetails.getDate(1));
			  ////System.out.println("New Npa mdy date "+APP_EXPIRY_DT);
			  
			  Date date1 = mdyFormat.parse(newNPADate);
	          Date date2 = mdyFormat.parse(APP_EXPIRY_DT);
	          
	          if(date1.compareTo(date2)>0){
	        	  message="NEW NPA DATE SHOULD BE BEFORE OR EQUAL TO APPLICATION EXPIRY DATE";
	                ////System.out.println("Date1 is after Date2");
	            }else if(date1.compareTo(date2)<0){
	                ////System.out.println("Date1 is before Date2");
	            }else if(date1.compareTo(date2)==0){
	                ////System.out.println("Date1 is equal to Date2");
	            }

		  }
		  else
		  {
			  
		  }
		 
	  }
	  catch(Exception e)
	  {
		  e.printStackTrace();
	  }
	  finally {
			DBConnection.freeConnection(connection);

		}
	  ////System.out.println("checkCGPANForNPAxxxDetail "+message);
	  return message;
  }
  public String checkCGPANForNPAxxxDetail(String cgpan, String memberId , String formType)
  {
	  ////System.out.println("checkCGPANForNPAxxxDetail called");
	  ApplicationDAO applicaitonDAO = new ApplicationDAO();
	 
	    
	  String message="";
	  int result=0;
	  int noofclosed = 0;
	  int count = 0;
	  Connection connection =null;	  
	  ResultSet rsNpaUpGradationDetails=null,rsNpaUpGradationDetails1=null,rsNpaUpGradationDetails2=null,
	  rsNpaUpGradationDetail0=null,rsNpaUpGradationDetailChkExistingCGPan=null,rsNpaUpGradationDetail15=null,rsToCheckerRegister=null;
	  Statement stNpaUpGradationDetails=null;
	  
	  try
	  {
		  
		
		    
		  connection = DBConnection.getConnection(false);
		  stNpaUpGradationDetails=connection.createStatement();
		  String queryTochkExistingCgPan="";
		  String queryToCheckerRegister="";
		  if(formType.equals("upgradation"))
		  {
			  //koteswar start
			  
			  queryToCheckerRegister="select count(*) from mli_checker_info  where mem_bnk_id||mem_zne_id||mem_brn_id='"+memberId+"'  ";
			  
			  //koteswar edn
			  
			  
			  queryTochkExistingCgPan="select * from npa_upgradation_detail where cgpan= '"+cgpan+"'";
		  }
		  else
		  {
			  queryTochkExistingCgPan="select * from npa_date_change_detail where cgpan= '"+cgpan+"'";
		  }
		  ////System.out.println("queryTochkExistingCgPan value "+queryTochkExistingCgPan);
		  
		  rsToCheckerRegister=stNpaUpGradationDetails.executeQuery(queryToCheckerRegister);
		  
		  		  
		  while((rsToCheckerRegister.next()))
		  {
			   count=Integer.parseInt(rsToCheckerRegister.getString(1));
		  if(count==0)
			  
		  {
			  
			  message="Sorry,According to Circular no-111/2015-16 dated on 22/02/2016,You have not Registerd for Creating Checker User Id\n.Please Register for Creating Checker User Id at the path mentioned below \n" +
			  		"SysAdminAudit--->Creation of MLI Checker User Id in our CGTMSE Portal.\nso that you can Give Request for your Account Upgradations";
			  
		  }
		  else
		  {
			  
		 
		  
		  {	
		  
		  rsNpaUpGradationDetailChkExistingCGPan=stNpaUpGradationDetails.executeQuery(queryTochkExistingCgPan);
		  
		  
		  if(rsNpaUpGradationDetailChkExistingCGPan.next())
		  {	
			  //System.out.println("data exist "+rsNpaUpGradationDetailChkExistingCGPan.getString(1));
			 /* if(formType.equals("upgradation"))
			  {
				  message="NPA UPGRADATION IS ALREADY MARKED FOR THE GIVEN "+cgpan+" CGPAN";
			  }
			  else
			  {
				  message="NPA DATE CHANGE IS ALREADY MARKED FOR THE GIVEN "+cgpan+" CGPAN";
			  }*/
		  }
		  else
		  {
			  
			/*  //System.out.println("data from npa_upgradation_detail val");
			  String query0=" SELECT  max( greatest( add_months(greatest(add_months(max(DBR_DT),18),add_months(APP_GUAR_START_DATE_TIME,18)),12), add_months(NPA_EFFECTIVE_DT,12))) claimexpdate FROM APPLICATION_DETAIL A,SSI_DETAIL S,DISBURSEMENT_DETAIL D,npa_detail_temp  n  WHERE  A.SSI_REFERENCE_NUMBER=S.SSI_REFERENCE_NUMBER "+ 
			  " and d.cgpan=a.cgpan   and n.bid=s.bid  and  a.cgpan in (select cgpan from application_detail where SSI_REFERENCE_NUMBER in (select SSI_REFERENCE_NUMBER from application_detail "+
			  " where cgpan='"+cgpan+"' ))  group by  a.cgpan,DBR_DT,APP_GUAR_START_DATE_TIME,NPA_EFFECTIVE_DT";
			  
			 
				  
			  String query1="select cgpan,ssi_unit_name,mem_bank_name,m.mem_bnk_id|| m.mem_zne_id||m.mem_brn_id,app_status,npa_effective_dt,NPA_REASONS_TURNING_NPA,npa_id from application_detail A, ssi_detail B "+
			  " ,npa_detail_temp C,member_info m where a.ssi_reference_number=B.ssi_reference_number and b.bid=C.BID and m.mem_bnk_id||m.mem_zne_id||m.mem_brn_id=a.mem_bnk_id|| "+
			  " a.mem_zne_id||a.mem_brn_id      and  m.mem_bnk_id||m.mem_zne_id||m.mem_brn_id='"+memberId+"'  and a.cgpan "+
			    " in (select cgpan from application_detail  where ssi_reference_number in (select ssi_reference_number  from application_detail where cgpan='"+cgpan+"'))";
			  
			  
			  query1="select cgpan,ssi_unit_name,mem_bank_name,m.mem_bnk_id|| m.mem_zne_id||m.mem_brn_id,app_status,npa_effective_dt,NPA_REASONS_TURNING_NPA,npa_id from application_detail A, "+
 " ssi_detail B ,npa_detail_temp C,member_info m where a.ssi_reference_number=B.ssi_reference_number and b.bid=C.BID and "+ 
         " m.mem_bnk_id||m.mem_zne_id||m.mem_brn_id='"+memberId+"' and   a.cgpan "+ 
           "  in (select cgpan from application_detail  where ssi_reference_number in (select ssi_reference_number  from application_detail where cgpan='"+cgpan+"'))";
			  
			 //System.out.println("query1 value "+query1);
			  rsNpaUpGradationDetails=stNpaUpGradationDetails.executeQuery(query1);		  
			// //System.out.println("query1 value rsNpaUpGradationDetails value"+rsNpaUpGradationDetails.next());
			  if(rsNpaUpGradationDetails.next())
			  {
				  //System.out.println("inside rsNpaUpGradationDetails.next()");
				  rsNpaUpGradationDetail0=stNpaUpGradationDetails.executeQuery(query0);
				  if(rsNpaUpGradationDetail0.next())
				  {
					  //System.out.println("rsNpaUpGradationDetail0 value "+rsNpaUpGradationDetail0.getDate(1));
					  if(rsNpaUpGradationDetail0.getDate(1)!=null)
					  {
						  SimpleDateFormat mdyFormat = new SimpleDateFormat("dd/MM/yyyy");
						  String CLAIM_EXPIRY_DT = mdyFormat.format(rsNpaUpGradationDetail0.getDate(1));	
					
						  if(CLAIM_EXPIRY_DT!=null && !CLAIM_EXPIRY_DT.equals(""))
						  {
							  String strDate=mdyFormat.format(new Date());
							  Date date1 = mdyFormat.parse(CLAIM_EXPIRY_DT);
							  Date date2 = mdyFormat.parse(strDate);
							  
							  //System.out.println("Date1 is "+date1);
							  //System.out.println("Date2 is "+date2);
							  
							  if(date1.compareTo(date2)>0){
					        	 
					                //System.out.println("Date1 is after Date2");
					          }else if(date1.compareTo(date2)<0){
					            	 message="YOUR CLAIM EXPIRY DATE IS EXPIRED";
					                //System.out.println("Date1 is before Date2");
					          }else if(date1.compareTo(date2)==0){
					                //System.out.println("Date1 is equal to Date2");
					          }
						  }
//						  else
//						  {
//							  message="YOUR CLAIM EXPIRY DATE IS EXPIRED..";
//						  }
					  }
				  }
				  
				  if(message.equals(""))
				  {
						  //System.out.println("checkCGPANForNPAxxxDetail data exist in 1 ");
						  String query2="SELECT COUNT(CGPAN) FROM APPLICATION_DETAIL A,SSI_DETAIL B WHERE  A.SSI_REFERENCE_NUMBER=B.SSI_REFERENCE_NUMBER "+
						  	" AND B.BID IN (SELECT BID FROM NPA_DETAIL_TEMP UNION ALL SELECT BID FROM NPA_DETAIL)  AND A.CGPAN='"+cgpan+"'";
						  
						  rsNpaUpGradationDetails1=stNpaUpGradationDetails.executeQuery(query2);
						  if(rsNpaUpGradationDetails1.next())
						  {
							  if(rsNpaUpGradationDetails1.getInt(1)!=0)
							  {
								  //System.out.println("checkCGPANForNPAxxxDetail data exist in 2 ");
								  String query3="SELECT COUNT(CGPAN) FROM APPLICATION_DETAIL A,SSI_DETAIL B WHERE  A.SSI_REFERENCE_NUMBER=B.SSI_REFERENCE_NUMBER "+
								  " AND B.BID IN (SELECT BID FROM CLAIM_DETAIL_TEMP UNION ALL SELECT BID FROM CLAIM_DETAIL)  AND A.CGPAN='"+cgpan+"'";
								  
								  query3="  SELECT COUNT (CGPAN) "+
								"  FROM APPLICATION_DETAIL A, SSI_DETAIL S "+
								"  WHERE     A.SSI_REFERENCE_NUMBER = S.SSI_REFERENCE_NUMBER "+
								"        AND EXISTS(SELECT BID FROM CLAIM_DETAIL_TEMP cdt WHERE cdt.bid = s.bid UNION ALL SELECT BID FROM CLAIM_DETAIL cd WHERE cd.bid = s.bid) "+
								"        AND NOT EXISTS(SELECT BID FROM CLAIM_DETAIL cd WHERE cd.bid = s.bid AND cd.clm_status = 'AP') "+
								"        AND A.CGPAN = '"+cgpan+"''";
								  rsNpaUpGradationDetails2=stNpaUpGradationDetails.executeQuery(query3);
								  if(rsNpaUpGradationDetails2.next())
								  {
									  if(rsNpaUpGradationDetails2.getInt(1)>0)
									  {
										  message="CLAIM ALREAY HAS BEEN SETTLED FOR THE GIVEN "+cgpan+" CGPAN";
									  }						  
									  //System.out.println("checkCGPANForNPAxxxDetail data exist in 3 ");
								  }
								 
							  }
							  else
							  {
								  message="NPA NOT LODGED FOR THE GIVEN CGPAN "+cgpan+" CGPAN";
							  }
						  }
				  }
			  }
			  else
			  {
				  //System.out.println("checkCGPANForNPAxxxDetail data not exist in 1 ");
				  message="No RECORD EXIST FOR THE GIVEN "+cgpan+" CGPAN";
			  }*/
			  
			  //koteswar start
			  
			  ArrayList appclstatuslist=new  ArrayList();
			  String query0="select app_status from application_detail where ssi_reference_number in" +
			  		" (select ssi_reference_number from application_Detail where cgpan='"+cgpan+"' and " +
			  		"mem_bnk_id||mem_zne_id||mem_brn_id='"+memberId+"') ";  
			  rsNpaUpGradationDetail15=stNpaUpGradationDetails.executeQuery(query0);
			  //System.out.println("query1== "+query0);
			  
			 // int ressize=rsNpaUpGradationDetail0.getFetchSize();
			  
			 
			  
			  while(rsNpaUpGradationDetail15.next())
				  
			  {
				  String status=(String) rsNpaUpGradationDetail15.getString(1);
				  
				  appclstatuslist.add(status);
			  }
			  
			  
			  
			if(appclstatuslist.contains("EX"))
			{
				
			}
				
			else
			if(appclstatuslist.contains("AP"))
           {
				
			}
			else
			if(appclstatuslist.contains("RE"))
           {
				
			}
			else
			{
			
				  message="THIS CGPAN IS CLOSED , PLZ ENTER CORRECT CGPAN";
				  }
			
			 		  
				 
				  
			 
			  
			 
			  //koteswar end
			  
			  
			  
			 if(message=="")
			  {
			  String query1="select count(cgpan) from ssi_detail b,application_detail c   where  b.ssi_reference_number=c.ssi_reference_number "+
			  	" and c.cgpan='"+cgpan+"' and c.mem_bnk_id||c.mem_zne_id||c.mem_brn_id='"+memberId+"'";
			  rsNpaUpGradationDetail0=stNpaUpGradationDetails.executeQuery(query1);
			  //System.out.println("query1== "+query1);
			  if(rsNpaUpGradationDetail0.next())
			  {
				  //System.out.println("first query result "+rsNpaUpGradationDetail0.getInt(1));
				  if(rsNpaUpGradationDetail0.getInt(1)!=0)
				  {					  
					  //System.out.println("inside not zero");
				  }
				  else
				  {
					  //System.out.println("query1 "+query1);
					  message="THIS CGPAN["+cgpan+"] IS NOT BELONGS TO YOUR MEMBER ID["+memberId+"] , PLZ ENTER CORRECT CGPAN";
				  }
			  }}
			  
			  if(message=="")
			  {
				  String query2="SELECT COUNT(CGPAN) FROM APPLICATION_DETAIL A,SSI_DETAIL B WHERE  A.SSI_REFERENCE_NUMBER=B.SSI_REFERENCE_NUMBER and " +
//				  		"A.APP_STATUS<>'CL' "+
				  	"  B.BID IN (SELECT BID FROM NPA_DETAIL_TEMP UNION ALL SELECT BID FROM NPA_DETAIL)  AND A.CGPAN='"+cgpan+"'";
				  
				  rsNpaUpGradationDetails1=stNpaUpGradationDetails.executeQuery(query2);
				  if(rsNpaUpGradationDetails1.next())
				  {
					  if(rsNpaUpGradationDetails1.getInt(1)!=0)
					  {					  
					  
					  }
					  else
					  {
						  //System.out.println("query2 "+query2);
						  message="NPA NOT LODGED FOR THE GIVEN CGPAN "+cgpan+" CGPAN OR YOUR ACCOUNT IS ALREADY CLOSED ";
					  }
				  }
			  }
			  
			  if(message=="")
			  {
				  String query3="select count(cgpan) from claim_detail a,ssi_detail b,application_detail c   where a.bid=b.bid and b.ssi_reference_number=c.ssi_reference_number "+
				  " and c.cgpan='"+cgpan+"' and clm_status='AP'";
				  rsNpaUpGradationDetails2=stNpaUpGradationDetails.executeQuery(query3);
				  if(rsNpaUpGradationDetails2.next())
				  {
					  if(rsNpaUpGradationDetails2.getInt(1)!=0)
					  {					  
						  //System.out.println("query3 "+query3);
						  message="YOUR CLAIM IS ALREADY SETTLED , YOU CAN NOT GO FOR NPA UPGRADATION.";
					  }
					  else
					  {
						  
					  }
				  }
			  }
			  
			  
		  }
		  
		 
	  }
		  }
		  }
		  
	  }
	  catch(Exception e)
	  {
		  e.printStackTrace();
	  }
	  finally
	  {
		 
		  try
		  {
			  if(rsNpaUpGradationDetails!=null)
			  {
				  rsNpaUpGradationDetails.close();
			  }
			  if(rsNpaUpGradationDetails1!=null)
			  {
				  rsNpaUpGradationDetails1.close();
			  }
			  if(rsNpaUpGradationDetails2!=null)
			  {
				  rsNpaUpGradationDetails2.close();
			  }
			  if(rsNpaUpGradationDetail0!=null)
			  {
				  rsNpaUpGradationDetail0.close();
			  }
			  if(rsNpaUpGradationDetailChkExistingCGPan!=null)
			  {
				  rsNpaUpGradationDetailChkExistingCGPan.close();
			  }
			  if(stNpaUpGradationDetails!=null)
			  {
				  stNpaUpGradationDetails.close();
			  }
			  if(rsNpaUpGradationDetail15!=null)
			  {
				  rsNpaUpGradationDetail15.close();
			  }
		  }
		  catch(Exception e1)
		  {
			  
		  }
		  DBConnection.freeConnection(connection);
	  }
	  //System.out.println("checkCGPANForNPAxxxDetail "+message);
	  return message;
  }
  public ArrayList getNPADetailsFromCGPAN(String cgpan, String memberId)
  {
	 // Log.log(4, "GMProcessor", "getNPADetailsFromCGPAN", "Entered");
	  NPADateModificationActionForm objNPADateModificationActionForm[]=null;
	  ResultSet rsNpaUpGradationDetails=null,rsNpaUpGradationDetails1=null;
	  Statement stNpaUpGradationDetails=null;
	  //ArrayList<Integer , NPADateModificationActionForm> a= new ArrayList<Integer , NPADateModificationActionForm>();
	  ArrayList<NPADateModificationActionForm> arrayListNPADateModificationActionForm= new ArrayList<NPADateModificationActionForm>();
	  Connection connection =null;
	 
	 
	  try
	  {
		  connection = DBConnection.getConnection();
		  stNpaUpGradationDetails=connection.createStatement();
		  String query="select cgpan,ssi_unit_name,mem_bank_name,m.mem_bnk_id|| m.mem_zne_id||m.mem_brn_id,app_status,TO_DATE(npa_effective_dt,'yyyy-MM-dd'),NPA_REASONS_TURNING_NPA,npa_id from application_detail A, ssi_detail B "+
		  " ,npa_detail_temp C,member_info m where a.ssi_reference_number=B.ssi_reference_number and b.bid=C.BID " +
		  "and m.mem_bnk_id||m.mem_zne_id||m.mem_brn_id=a.mem_bnk_id|| a.mem_zne_id||a.mem_brn_id" +
		  " and  m.mem_bnk_id||m.mem_zne_id||m.mem_brn_id='"+memberId+"' and app_status not in ('RE')  and a.cgpan "+
		    " in (select cgpan from application_detail  where ssi_reference_number in (select ssi_reference_number  from application_detail where cgpan='"+cgpan+"'))";
		  
		  query="select cgpan,ssi_unit_name,mem_bank_name,m.mem_bnk_id|| m.mem_zne_id||m.mem_brn_id,app_status,to_char(npa_effective_dt,'dd/MM/YYYY'),NPA_REASONS_TURNING_NPA,npa_id from application_detail A, ssi_detail B "+
		  " ,npa_detail_temp C,member_info m where a.ssi_reference_number=B.ssi_reference_number and b.bid=C.BID " +	
		  " and  m.mem_bnk_id||m.mem_zne_id||m.mem_brn_id='"+memberId+"' and app_status not in ('RE')  and a.cgpan "+
		    " in (select cgpan from application_detail  where ssi_reference_number in (select ssi_reference_number  from application_detail where cgpan='"+cgpan+"'))";
		  
		  query="select cgpan,ssi_unit_name,mem_bank_name,m.mem_bnk_id|| m.mem_zne_id||m.mem_brn_id,app_status,to_char(npa_effective_dt,'dd/MM/YYYY'),"+
" NPA_REASONS_TURNING_NPA,npa_id , clm_date from application_detail A, ssi_detail B LEFT JOIN claim_detail_temp cd on b.bid=cd.BID ,npa_detail_temp C,member_info m where "+ 
 " a.ssi_reference_number=B.ssi_reference_number and b.bid=C.BID  and  m.mem_bnk_id||m.mem_zne_id||m.mem_brn_id='"+memberId+"' and app_status "+
  " not in ('RE')  and a.cgpan in (select cgpan from application_detail  where ssi_reference_number in (select ssi_reference_number  from application_detail "+
 "where cgpan='"+cgpan+"'))";

		  rsNpaUpGradationDetails=stNpaUpGradationDetails.executeQuery(query);
		  //System.out.println("getNPADetailsFromCGPAN "+query);
		  int i=0;
		  while(rsNpaUpGradationDetails.next())
		  {
			  i++;
			 
		  }
		  //System.out.println("NPA details="+i);
		  objNPADateModificationActionForm= new NPADateModificationActionForm[i];
		  rsNpaUpGradationDetails1=stNpaUpGradationDetails.executeQuery(query);
		  int j=0;
		  while(rsNpaUpGradationDetails1.next())
		  {
			 //System.out.println("NPA date "+rsNpaUpGradationDetails1.getString(6));
			  objNPADateModificationActionForm[j]=new NPADateModificationActionForm();
			  objNPADateModificationActionForm[j].setUnitName(rsNpaUpGradationDetails1.getString(2));
			  objNPADateModificationActionForm[j].setBankName(rsNpaUpGradationDetails1.getString(3));
			  objNPADateModificationActionForm[j].setCgPan(rsNpaUpGradationDetails1.getString(1));
			  objNPADateModificationActionForm[j].setMliID(rsNpaUpGradationDetails1.getString(4));
			  //objNPADateModificationActionForm[j].setNpaDate(rsNpaUpGradationDetails1.getDate(6));
			  objNPADateModificationActionForm[j].setLstNpaDate(rsNpaUpGradationDetails1.getString(6));
			  objNPADateModificationActionForm[j].setReasonForAccNpa(rsNpaUpGradationDetails1.getString(7));
			  objNPADateModificationActionForm[j].setApplicationStatus(rsNpaUpGradationDetails1.getString(5));
			  objNPADateModificationActionForm[j].setNpaID(rsNpaUpGradationDetails1.getString(8));
			  arrayListNPADateModificationActionForm.add(objNPADateModificationActionForm[j]);
			//System.out.println("NPA details CLAIM_EXPIRY_DT ="+rsNpaUpGradationDetails1.getString(6));
		  }
	  
	  }
	  catch(Exception e)
	  {
		//  Log.log(2, "GMProcessor", "getNPADetailsFromCGPAN", "Exception" +e.getMessage());
		  e.printStackTrace();
	  }
	  finally
	  {
	      DBConnection.freeConnection(connection);
	  }
	 // Log.log(4, "GMProcessor", "getNPADetailsFromCGPAN", "Exited");
	  return arrayListNPADateModificationActionForm;
  }
  public OutstandingDetail getOutstandingDetailsForCgpan(String cgpan)
    throws DatabaseException
  {
    Log.log(4, "GMDAO", "getOutstandingDetailsForCgpan", "Entered");
    Connection connection = DBConnection.getConnection();
    CallableStatement getOutstandingDetailStmt = null;
    CallableStatement getOutDetailForCgpanStmt = null;
    OutstandingDetail outstandingDetail = null;
    OutstandingAmount outAmount = null;
    String exception = null;

    ArrayList outAmounts = new ArrayList();
    try
    {
      getOutstandingDetailStmt = connection.prepareCall("{?=call packGetOutstandingDtls.funcGetOutStandingforCGPAN(?,?,?)}");
      getOutstandingDetailStmt.registerOutParameter(1, 4);
      getOutstandingDetailStmt.setString(2, cgpan);
      getOutstandingDetailStmt.registerOutParameter(3, -10);
      getOutstandingDetailStmt.registerOutParameter(4, 12);

      getOutstandingDetailStmt.execute();

      exception = getOutstandingDetailStmt.getString(4);
      Log.log(5, "GMDAO", "getOutstandingDetailsForCgpan", "exception for" + cgpan + "-->" + exception);

      int error = getOutstandingDetailStmt.getInt(1);

      if (error == 0)
      {
        Log.log(5, "GMDAO", "getOutstandingDetailsForCgpan", "Success");
      }

      if (error == 1)
      {
        getOutstandingDetailStmt.close();
        getOutstandingDetailStmt = null;
        Log.log(2, "GMDAO", "getOutstandingDetailsForCgpan", "Exception " + exception);
        connection.rollback();
        throw new DatabaseException(exception);
      }

      ResultSet resultSet = (ResultSet)getOutstandingDetailStmt.getObject(3);
      while (resultSet.next())
      {
        String cgpan1 = resultSet.getString(2);
        Log.log(5, "GMDAO", "getOutstandingDetailsForCgpan", "cgpan from view : " + cgpan1);
        if (cgpan1 != null)
        {
          outstandingDetail = new OutstandingDetail();
          outstandingDetail.setCgpan(cgpan1);

          outstandingDetail.setScheme(resultSet.getString(4));
          if (cgpan1.substring(cgpan1.length() - 2, cgpan1.length() - 1).equalsIgnoreCase("T"))
          {
            outstandingDetail.setTcSanctionedAmount(resultSet.getDouble(5));
          }
          else if (cgpan1.substring(cgpan1.length() - 2, cgpan1.length() - 1).equalsIgnoreCase("W"))
          {
            outstandingDetail.setWcFBSanctionedAmount(resultSet.getDouble(5));
            outstandingDetail.setWcNFBSanctionedAmount(resultSet.getDouble(6));
          }

          Log.log(5, "GMDAO", "getOutstandingDetailsForCgpan", "Outstanding added for" + cgpan);
        }
      }
      getOutstandingDetailStmt.close();
      getOutstandingDetailStmt = null;

      resultSet.close();
      resultSet = null;

      int cgpanLength = cgpan.length();
      String panType = cgpan.substring(cgpanLength - 2, cgpanLength - 1);

      if (panType.equalsIgnoreCase("T"))
      {
        getOutDetailForCgpanStmt = connection.prepareCall("{?=call packGetTCOutStanding.funcTCOutStanding(?,?,?)}");
        getOutDetailForCgpanStmt.registerOutParameter(1, 4);
        getOutDetailForCgpanStmt.setString(2, cgpan);
        getOutDetailForCgpanStmt.registerOutParameter(3, -10);
        getOutDetailForCgpanStmt.registerOutParameter(4, 12);

        getOutDetailForCgpanStmt.execute();

        exception = getOutDetailForCgpanStmt.getString(4);
        Log.log(5, "GMDAO", "getOutstandingDetailsForCgpan", "exception for" + cgpan + "-->" + exception);

        error = getOutDetailForCgpanStmt.getInt(1);

        if (error == 0)
        {
          Log.log(5, "GMDAO", "getOutstandingDetailsForCgpan", "Success");
        }

        if (error == 1)
        {
          getOutDetailForCgpanStmt.close();
          getOutDetailForCgpanStmt = null;
          Log.log(2, "GMDAO", "getOutstandingDetailsForCgpan", "Exception " + exception);
          connection.rollback();
          throw new DatabaseException(exception);
        }

        ResultSet cgpanResultSet = (ResultSet)getOutDetailForCgpanStmt.getObject(3);

        while (cgpanResultSet.next())
        {
          outAmount = new OutstandingAmount();
          outAmount.setCgpan(cgpan);
          outAmount.setTcoId(cgpanResultSet.getString(1));
          outAmount.setTcPrincipalOutstandingAmount(cgpanResultSet.getDouble(2));
          outAmount.setTcOutstandingAsOnDate(cgpanResultSet.getDate(3));
          outAmounts.add(outAmount);
        }

        if (outAmounts.size() != 0)
        {
          outstandingDetail.setOutstandingAmounts(outAmounts);
        }

        Log.log(5, "GMDAO", "getOutstandingDetailsForCgpan", "Outstanding Amounts added for" + cgpan);
        cgpanResultSet.close();
        cgpanResultSet = null;

        getOutDetailForCgpanStmt.close();
        getOutDetailForCgpanStmt = null;
      }
      else
      {
        getOutDetailForCgpanStmt = connection.prepareCall("{?=call packGetWCOutStanding.funcWCOutStanding(?,?,?)}");
        getOutDetailForCgpanStmt.registerOutParameter(1, 4);
        getOutDetailForCgpanStmt.setString(2, cgpan);
        getOutDetailForCgpanStmt.registerOutParameter(3, -10);
        getOutDetailForCgpanStmt.registerOutParameter(4, 12);

        getOutDetailForCgpanStmt.execute();

        exception = getOutDetailForCgpanStmt.getString(4);
        Log.log(5, "GMDAO", "getOutstandingDetailsForCgpan", "exception for" + cgpan + "-->" + exception);

        error = getOutDetailForCgpanStmt.getInt(1);

        if (error == 0)
        {
          Log.log(5, "GMDAO", "getOutstandingDetailsForCgpan", "Success");
        }

        if (error == 1)
        {
          getOutDetailForCgpanStmt.close();
          getOutDetailForCgpanStmt = null;
          Log.log(2, "GMDAO", "getOutstandingDetailsForCgpan", "Exception " + exception);
          connection.rollback();
          throw new DatabaseException(exception);
        }

        ResultSet cgpanResultSet = (ResultSet)getOutDetailForCgpanStmt.getObject(3);

        while (cgpanResultSet.next())
        {
          outAmount = new OutstandingAmount();
          outAmount.setCgpan(cgpan);
          outAmount.setWcoId(cgpanResultSet.getString(1));
          outAmount.setWcFBPrincipalOutstandingAmount(cgpanResultSet.getDouble(2));
          outAmount.setWcFBInterestOutstandingAmount(cgpanResultSet.getDouble(3));
          outAmount.setWcFBOutstandingAsOnDate(cgpanResultSet.getDate(4));
          outAmount.setWcNFBPrincipalOutstandingAmount(cgpanResultSet.getDouble(5));
          outAmount.setWcNFBInterestOutstandingAmount(cgpanResultSet.getDouble(6));
          outAmount.setWcNFBOutstandingAsOnDate(cgpanResultSet.getDate(7));

          outAmounts.add(outAmount);
        }

        if (outAmounts.size() != 0)
        {
          outstandingDetail.setOutstandingAmounts(outAmounts);
        }

        Log.log(5, "GMDAO", "getOutstandingDetailsForCgpan", "Outstanding Amounts added for" + cgpan);

        cgpanResultSet.close();
        cgpanResultSet = null;

        getOutDetailForCgpanStmt.close();
        getOutDetailForCgpanStmt = null;
      }

    }
    catch (Exception e)
    {
      Log.logException(e);
      throw new DatabaseException(e.getMessage());
    }
    finally
    {
      DBConnection.freeConnection(connection);
    }
    ResultSet cgpanResultSet;
    ResultSet resultSet;
    Log.log(4, "GMDAO", "getOutstandingDetailsForCgpan", "Exited");

    return outstandingDetail;
  }

  public ArrayList getShiftCgpanForMember(String bankId, String zoneId, String branchId)
    throws DatabaseException
  {
    DemandAdvice demandAdvice = null;
    Connection connection = null;
    ResultSet rsDanDetails = null;
    ResultSet rsPaidDetails = null;

    int getSchemesStatus = 0;
    String getDanDetailsErr = "";

    ArrayList danDetails = null;

    connection = DBConnection.getConnection(false);
    try
    {
      danDetails = new ArrayList();

      CallableStatement getDanDetailsStmt = connection.prepareCall("{?= call packGetGFDanDetailsModified.funcGetGFDanDetailsModified(?,?,?,?,?,?)}");

      getDanDetailsStmt.registerOutParameter(1, 4);

      getDanDetailsStmt.setString(2, bankId);
      getDanDetailsStmt.setString(3, zoneId);
      getDanDetailsStmt.setString(4, branchId);
      getDanDetailsStmt.registerOutParameter(5, -10);
      getDanDetailsStmt.registerOutParameter(6, -10);
      getDanDetailsStmt.registerOutParameter(7, 12);
      getDanDetailsStmt.execute();

      getSchemesStatus = getDanDetailsStmt.getInt(1);
      if (getSchemesStatus == 0) {
        rsDanDetails = (ResultSet)getDanDetailsStmt.getObject(5);
        DANSummary danSummary = null;
        while (rsDanDetails.next())
        {
          danSummary = new DANSummary();
          danSummary.setCgpan(rsDanDetails.getString(1));
          danSummary.setUnitname(rsDanDetails.getString(2));
          danSummary.setDanId(rsDanDetails.getString(3));
          danSummary.setNoOfCGPANs(rsDanDetails.getInt(4));
          danSummary.setAmountDue(rsDanDetails.getDouble(5));
          danSummary.setDanDate(rsDanDetails.getDate(6));
          danSummary.setBranchName(rsDanDetails.getString(7));

          danDetails.add(danSummary);
          danSummary = null;
        }
        rsPaidDetails = (ResultSet)getDanDetailsStmt.getObject(6);
        while (rsPaidDetails.next())
        {
          String tempDanId = rsPaidDetails.getString(1);
          double tempPaidAmt = rsPaidDetails.getDouble(2);
          for (int i = 0; i < danDetails.size(); i++)
          {
            DANSummary tempSummary = new DANSummary();
            tempSummary = (DANSummary)danDetails.get(i);
            if (tempSummary.getDanId().equals(tempDanId))
            {
              tempSummary.setAmountPaid(tempPaidAmt);
              danDetails.set(i, tempSummary);
              break;
            }
            tempSummary = null;
          }
        }

        rsDanDetails.close();
        rsDanDetails = null;
        rsPaidDetails.close();
        rsPaidDetails = null;
        getDanDetailsStmt.close();
        getDanDetailsStmt = null;
      }
      else
      {
        getDanDetailsErr = getDanDetailsStmt.getString(7);

        getDanDetailsStmt.close();
        getDanDetailsStmt = null;

        connection.rollback();

        throw new DatabaseException(getDanDetailsErr);
      }

      connection.commit();
    }
    catch (Exception exception)
    {
      try
      {
        connection.rollback();
      }
      catch (SQLException localSQLException) {
      }
      throw new DatabaseException(exception.getMessage());
    }
    finally
    {
      DBConnection.freeConnection(connection);
    }
    CallableStatement getDanDetailsStmt;
    return danDetails;
  }

  public ArrayList displayRequestedForClosureApproval()
    throws DatabaseException
  {
    DemandAdvice demandAdvice = null;
    Connection connection = null;

    int getSchemesStatus = 0;
    String getDanDetailsErr = "";

    ArrayList danDetails = new ArrayList();
    ArrayList danDetails1 = new ArrayList();
    ArrayList danDetails2 = new ArrayList();

    connection = DBConnection.getConnection(false);
    try
    {
      danDetails = new ArrayList();

      CallableStatement getDanDetailsStmt = connection.prepareCall("{?= call packGetClosureDetailmod.funcGetClosureDetailmod(?,?,?)}");

      getDanDetailsStmt.registerOutParameter(1, 4);
      getDanDetailsStmt.registerOutParameter(2, -10);
      getDanDetailsStmt.registerOutParameter(3, -10);
      getDanDetailsStmt.registerOutParameter(4, 12);
      getDanDetailsStmt.execute();

      getSchemesStatus = getDanDetailsStmt.getInt(1);
      if (getSchemesStatus == 1)
      {
        String error = getDanDetailsStmt.getString(4);

        getDanDetailsStmt.close();
        getDanDetailsStmt = null;

        connection.rollback();

        throw new DatabaseException(error);
      }

      ResultSet rsDanDetails = (ResultSet)getDanDetailsStmt.getObject(2);

      while (rsDanDetails.next())
      {
        DANSummary danSummary = new DANSummary();
        danSummary.setMemberId(rsDanDetails.getString(1));

        danSummary.setCgpan(rsDanDetails.getString(2));

        danSummary.setClosureDate(rsDanDetails.getDate(3));

        danSummary.setReason(rsDanDetails.getString(4));

        danSummary.setAmountDue(rsDanDetails.getDouble(5));

        danSummary.setAmountBengPaid(rsDanDetails.getDouble(6));

        danDetails1.add(danSummary);
      }

      rsDanDetails.close();
      rsDanDetails = null;

      ResultSet rsPaidDetails = (ResultSet)getDanDetailsStmt.getObject(3);

      while (rsPaidDetails.next())
      {
        DANSummary danSummary1 = new DANSummary();
        danSummary1.setMemberId(rsPaidDetails.getString(1));

        danSummary1.setCgpan(rsPaidDetails.getString(2));

        danSummary1.setClosureDate(rsPaidDetails.getDate(3));

        danSummary1.setReason(rsPaidDetails.getString(4));
        danSummary1.setAmountDue(rsPaidDetails.getDouble(5));
        danSummary1.setAmountBengPaid(rsPaidDetails.getDouble(6));
        danDetails2.add(danSummary1);
      }

      rsPaidDetails.close();
      rsPaidDetails = null;

      getDanDetailsStmt.close();
      getDanDetailsStmt = null;

      danDetails.add(0, danDetails1);
      danDetails.add(1, danDetails2);
      connection.commit();
    }
    catch (Exception exception)
    {
      try
      {
        connection.rollback();
      }
      catch (SQLException localSQLException) {
      }
      throw new DatabaseException(exception.getMessage());
    }
    finally
    {
      DBConnection.freeConnection(connection);
    }
    CallableStatement getDanDetailsStmt;
    return danDetails;
  }

  public DANSummary getRequestedForClosureApplication(String cgpan)
    throws DatabaseException
  {
    Connection connection = null;
    ResultSet rsDanDetails = null;
    ResultSet rsPaidDetails = null;

    DANSummary danSummary = null;
    int getSchemesStatus = 0;
    String getDanDetailsErr = "";
    connection = DBConnection.getConnection(false);
    try
    {
      CallableStatement getDanDetailsStmt = connection.prepareCall("{?= call packGetClosureDetail.funcGetCgpanClosureDetaiNew(?,?,?)}");

      getDanDetailsStmt.registerOutParameter(1, 4);
      getDanDetailsStmt.setString(2, cgpan);
      getDanDetailsStmt.registerOutParameter(3, -10);
      getDanDetailsStmt.registerOutParameter(4, 12);
      getDanDetailsStmt.execute();

      getSchemesStatus = getDanDetailsStmt.getInt(1);
      if (getSchemesStatus == 0) {
        rsDanDetails = (ResultSet)getDanDetailsStmt.getObject(3);

        while (rsDanDetails.next())
        {
          danSummary = new DANSummary();
          danSummary.setMemberId(rsDanDetails.getString(1));

          danSummary.setClosureDate(rsDanDetails.getDate(2));

          danSummary.setReason(rsDanDetails.getString(3));
          danSummary.setAmountDue(rsDanDetails.getDouble(4));
          danSummary.setAmountBengPaid(rsDanDetails.getDouble(5));
          danSummary.setDanId(rsDanDetails.getString(6));
        }

        rsDanDetails.close();
        rsDanDetails = null;

        getDanDetailsStmt.close();
        getDanDetailsStmt = null;
      }
      else
      {
        getDanDetailsErr = getDanDetailsStmt.getString(4);

        getDanDetailsStmt.close();
        getDanDetailsStmt = null;

        connection.rollback();

        throw new DatabaseException(getDanDetailsErr);
      }

      connection.commit();
    }
    catch (Exception exception)
    {
      try
      {
        connection.rollback();
      }
      catch (SQLException localSQLException) {
      }
      throw new DatabaseException(exception.getMessage());
    }
    finally
    {
      DBConnection.freeConnection(connection);
    }
    CallableStatement getDanDetailsStmt;
    return danSummary;
  }

  public void insertDanDetailsForClosure(String danNo, String cgpan, DANSummary danSummary, User user, Connection connection)
    throws DatabaseException
  {
    String methodName = "insertDanDetailsForClosure";

    CallableStatement insertDanDetailsForClosureStmt = null;
    SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");

    int updateStatus = 0;

    String formatedDate = "";
    String userId = "";

    boolean newConn = false;
    try
    {
      if (connection == null)
      {
        connection = DBConnection.getConnection();
        newConn = true;
      }
      insertDanDetailsForClosureStmt = connection.prepareCall("{?= call funcInsDanDetForClos(?,?,?,?,?,?,?,?,?,?,?)}");

      insertDanDetailsForClosureStmt.registerOutParameter(1, 4);

      insertDanDetailsForClosureStmt.setString(2, danNo);

      insertDanDetailsForClosureStmt.setString(3, cgpan);

      insertDanDetailsForClosureStmt.setDouble(4, danSummary.getAmountDue());

      insertDanDetailsForClosureStmt.setDouble(5, danSummary.getAmountBeingPaid());

      insertDanDetailsForClosureStmt.setString(6, danSummary.getReason());

      java.util.Date utilDate = danSummary.getClosureDate();
      formatedDate = dateFormat.format(utilDate);
      java.sql.Date sqlDate = java.sql.Date.valueOf(DateHelper.stringToSQLdate(formatedDate));
      insertDanDetailsForClosureStmt.setDate(7, sqlDate);

      insertDanDetailsForClosureStmt.setString(8, danSummary.getMemberId().substring(0, 4));
      insertDanDetailsForClosureStmt.setString(9, danSummary.getMemberId().substring(4, 8));
      insertDanDetailsForClosureStmt.setString(10, danSummary.getMemberId().substring(8, 12));

      userId = user.getUserId();
      insertDanDetailsForClosureStmt.setString(11, userId);

      insertDanDetailsForClosureStmt.registerOutParameter(12, 12);
      insertDanDetailsForClosureStmt.executeQuery();

      updateStatus = insertDanDetailsForClosureStmt.getInt(1);

      String error = insertDanDetailsForClosureStmt.getString(12);

      if (updateStatus == 1)
      {
        insertDanDetailsForClosureStmt.close();
        insertDanDetailsForClosureStmt = null;
        connection.rollback();
        Log.log(2, "GMDAO", methodName, error);

        throw new DatabaseException(error);
      }

      insertDanDetailsForClosureStmt.close();
      insertDanDetailsForClosureStmt = null;
      connection.commit();
    }
    catch (Exception exception)
    {
      Log.logException(exception);
      throw new DatabaseException(exception.getMessage());
    }
    finally
    {
      DBConnection.freeConnection(connection);
    }
    java.sql.Date sqlDate;
    java.util.Date utilDate;
    Log.log(4, "GMDAO", methodName, "Exited");
  }

  public void updateApplicationStatusForClosedCases(String cgpan, DANSummary danSummary, User user)
    throws DatabaseException
  {
    Log.log(4, "GMDAO", "updateApplicationStatusForClosedCases", "Entered");
    Connection connection = DBConnection.getConnection();
    CallableStatement updateRepaymentStmt = null;
    int updateStatus = 0;

    SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
    String formatedDate = "";
    String formatedDate1 = "";
    java.util.Date utilDate = new java.util.Date();

    String userId = user.getUserId();
    try
    {
      updateRepaymentStmt = connection.prepareCall("{?=call funcUpdateAppDetForClos(?,?,?,?,?)}");

      updateRepaymentStmt.registerOutParameter(1, 4);

      updateRepaymentStmt.setString(2, danSummary.getReason());
      formatedDate = dateFormat.format(danSummary.getClosureDate());
      java.sql.Date sqlDate = java.sql.Date.valueOf(DateHelper.stringToSQLdate(formatedDate));

      updateRepaymentStmt.setDate(3, sqlDate);

      updateRepaymentStmt.setString(4, userId);
      updateRepaymentStmt.setString(5, cgpan);
      updateRepaymentStmt.registerOutParameter(6, 12);
      updateRepaymentStmt.executeQuery();
      updateStatus = Integer.parseInt(updateRepaymentStmt.getObject(1).toString());

      String error = updateRepaymentStmt.getString(6);

      if (updateStatus == 0) {
        updateRepaymentStmt.close();
        updateRepaymentStmt = null;
        connection.commit();
        Log.log(5, "GMDAO", "updateApplicationStatusForClosedCases", "success-SP");
      }
      else if (updateStatus == 1) {
        updateRepaymentStmt.close();
        updateRepaymentStmt = null;
        Log.log(2, "GMDAO", "updateApplicationStatusForClosedCases", "Error : " + error);
        connection.rollback();
        throw new DatabaseException(error);
      }
    }
    catch (Exception exception) {
      Log.logException(exception);
      try
      {
        connection.rollback();
      }
      catch (SQLException localSQLException) {
      }
      throw new DatabaseException(exception.getMessage());
    }
    finally {
      DBConnection.freeConnection(connection);
    }
    java.sql.Date sqlDate;
    Log.log(4, "GMDAO", "updateApplicationStatusForClosedCases", "Exited");
  }

  public Repayment getRepaymentDetailsForCgpan(String cgpan)
    throws DatabaseException
  {
    Log.log(4, "GMDAO", "getRepaymentDetailsForCgpan", "Entered");
    Connection connection = DBConnection.getConnection();
    CallableStatement getRepaymentDetailStmt = null;
    String exception = null;

    Repayment repayment = null;
    ArrayList listOfRepaymentAmount = new ArrayList();
    try
    {
      getRepaymentDetailStmt = connection.prepareCall("{?=call packGetDtlsforRepayment.funcGetDtlsforCGPAN(?,?,?)}");
      getRepaymentDetailStmt.registerOutParameter(1, 4);
      getRepaymentDetailStmt.setString(2, cgpan);
      getRepaymentDetailStmt.registerOutParameter(3, -10);
      getRepaymentDetailStmt.registerOutParameter(4, 12);

      getRepaymentDetailStmt.executeQuery();

      exception = getRepaymentDetailStmt.getString(4);
      Log.log(5, "GMDAO", "getRepaymentDetailsForCgpan", "exception for" + cgpan + "-->" + exception);

      int error = getRepaymentDetailStmt.getInt(1);

      if (error == 1)
      {
        getRepaymentDetailStmt.close();
        getRepaymentDetailStmt = null;
        connection.rollback();
        Log.log(2, "GMDAO", "getRepaymentDetailsForCgpan", "error in SP " + exception);
        throw new DatabaseException(exception);
      }

      ResultSet resultSet = (ResultSet)getRepaymentDetailStmt.getObject(3);

      while (resultSet.next())
      {
        repayment = new Repayment();
        repayment.setCgpan(resultSet.getString(2));

        repayment.setScheme(resultSet.getString(4));

        Log.log(5, "GMDAO", "getRepaymentDetailsForCgpan", "Repayment added for" + cgpan);
      }

      resultSet.close();
      resultSet = null;

      getRepaymentDetailStmt.close();
      getRepaymentDetailStmt = null;

      getRepaymentDetailStmt = connection.prepareCall("{?=call packGetRepaymentDtls.funcGetRepaymentDtl(?,?,?)}");
      getRepaymentDetailStmt.registerOutParameter(1, 4);
      getRepaymentDetailStmt.setString(2, cgpan);
      getRepaymentDetailStmt.registerOutParameter(3, -10);
      getRepaymentDetailStmt.registerOutParameter(4, 12);

      getRepaymentDetailStmt.execute();

      exception = getRepaymentDetailStmt.getString(4);
      Log.log(5, "GMDAO", "getRepaymentDetailsForCgpan", "exception for" + cgpan + "-->" + exception);

      error = getRepaymentDetailStmt.getInt(1);

      if (error == 1)
      {
        getRepaymentDetailStmt.close();
        getRepaymentDetailStmt = null;
        Log.log(2, "getRepaymentDetailsForCgpan", "Exception ", exception);
        connection.rollback();
        throw new DatabaseException(exception);
      }

      resultSet = (ResultSet)getRepaymentDetailStmt.getObject(3);
      RepaymentAmount repaymentAmount = null;
      while (resultSet.next())
      {
        repaymentAmount = new RepaymentAmount();

        repaymentAmount.setCgpan(cgpan);
        repaymentAmount.setRepayId(resultSet.getString(1));
        repaymentAmount.setRepaymentAmount(resultSet.getDouble(2));
        repaymentAmount.setRepaymentDate(resultSet.getDate(3));
        listOfRepaymentAmount.add(repaymentAmount);
      }

      if (listOfRepaymentAmount.size() != 0)
      {
        repayment.setRepaymentAmounts(listOfRepaymentAmount);
      }

      Log.log(5, "GMDAO", "getRepaymentDetailsForCgpan", "Repayment Amounts added for" + cgpan);

      resultSet.close();
      resultSet = null;

      getRepaymentDetailStmt.close();
      getRepaymentDetailStmt = null;
    }
    catch (Exception e)
    {
      Log.logException(e);
      throw new DatabaseException(e.getMessage());
    }
    finally
    {
      DBConnection.freeConnection(connection);
    }
    ResultSet resultSet;
    Log.log(4, "GMDAO", "getRepaymentDetailsForCgpan", "Exited");

    return repayment;
  }

  public boolean insertRepaymentDetails(RepaymentAmount repaymentAmount, String userId)
    throws SQLException, DatabaseException
  {
    Log.log(4, "GMDAO", "insertRepaymentDetails", "Entered");
    Connection connection = DBConnection.getConnection();
    CallableStatement updateRepaymentStmt = null;
    int updateStatus = 0;

    boolean updateRepaymentStatus = false;

    if (repaymentAmount != null)
    {
      try
      {
        updateRepaymentStmt = connection.prepareCall("{?=call funcInsertRepayDetailForCGPAN(?,?,?,?,?)}");

        updateRepaymentStmt.registerOutParameter(1, 4);

        updateRepaymentStmt.setString(2, repaymentAmount.getCgpan());

        updateRepaymentStmt.setDouble(3, repaymentAmount.getRepaymentAmount());

        java.util.Date utilDate = repaymentAmount.getRepaymentDate();
        java.sql.Date sqlDate = new java.sql.Date(utilDate.getTime());

        updateRepaymentStmt.setDate(4, sqlDate);

        updateRepaymentStmt.setString(5, userId);

        updateRepaymentStmt.registerOutParameter(6, 12);

        updateRepaymentStmt.executeQuery();

        updateStatus = Integer.parseInt(updateRepaymentStmt.getObject(1).toString());

        String error = updateRepaymentStmt.getString(6);

        if (updateStatus == 0) {
          updateRepaymentStatus = true;
          Log.log(5, "GMDAO", "insertRepaymentDetails", "success-SP");
        }
        else if (updateStatus == 1) {
          updateRepaymentStatus = false;
          updateRepaymentStmt.close();
          updateRepaymentStmt = null;
          Log.log(2, "GMDAO", "insertRepaymentDetails", "Error : " + error);
          connection.rollback();
          throw new DatabaseException(error);
        }
        updateRepaymentStmt.close();
        updateRepaymentStmt = null;
        connection.commit();
      }
      catch (Exception exception) {
        Log.logException(exception);
        try
        {
          connection.rollback();
        }
        catch (SQLException localSQLException) {
        }
        throw new DatabaseException(exception.getMessage());
      }
      finally {
        DBConnection.freeConnection(connection);
      }
    }
    Log.log(4, "GMDAO", "insertRepaymentDetails", "Exited");

    return updateRepaymentStatus;
  }

  public boolean updateRepaymentDetails(RepaymentAmount modifiedRepaymentAmount, String userId)
    throws SQLException, DatabaseException
  {
    Log.log(4, "GMDAO", "updateRepaymentDetails", "Entered");
    Connection connection = DBConnection.getConnection();
    CallableStatement updateRepaymentStmt = null;
    int updateStatus = 0;

    boolean updateRepaymentStatus = false;

    if (modifiedRepaymentAmount != null)
    {
      try
      {
        updateRepaymentStmt = connection.prepareCall("{?=call funcUpdRepayment(?,?,?,?,?)}");

        updateRepaymentStmt.registerOutParameter(1, 4);

        updateRepaymentStmt.setString(2, modifiedRepaymentAmount.getRepayId());

        updateRepaymentStmt.setDouble(3, modifiedRepaymentAmount.getRepaymentAmount());

        java.util.Date utilDate = modifiedRepaymentAmount.getRepaymentDate();
        java.sql.Date sqlDate = new java.sql.Date(utilDate.getTime());

        updateRepaymentStmt.setDate(4, sqlDate);

        updateRepaymentStmt.setString(5, userId);
        updateRepaymentStmt.registerOutParameter(6, 12);

        updateRepaymentStmt.executeQuery();

        updateStatus = Integer.parseInt(updateRepaymentStmt.getObject(1).toString());

        String error = updateRepaymentStmt.getString(6);

        if (updateStatus == 0) {
          updateRepaymentStatus = true;
          Log.log(5, "GMDAO", "updateRepaymentDetails", "SP success");
        }
        else if (updateStatus == 1) {
          updateRepaymentStatus = false;
          updateRepaymentStmt.close();
          updateRepaymentStmt = null;
          Log.log(2, "GMDAO", "updateRepaymentDetails", "Error " + error);
          connection.rollback();
          throw new DatabaseException(error);
        }
        updateRepaymentStmt.close();
        updateRepaymentStmt = null;
        connection.commit();
      }
      catch (Exception exception) {
        Log.logException(exception);
        try
        {
          connection.rollback();
        } catch (SQLException localSQLException) {
        }
        throw new DatabaseException(exception.getMessage());
      } finally {
        DBConnection.freeConnection(connection);
      }
    }
    Log.log(4, "GMDAO", "updateRepaymentDetails", "Exited");

    return updateRepaymentStatus;
  }

  public void submitClosureDetails(String memberId, String cgpan, java.sql.Date startDate, String closureRemarks, String userId)
    throws DatabaseException
  {
    String methodName = "submitClosureDetails";

    Log.log(4, "GMDAO", methodName, "Entered");
    String bankId = memberId.substring(0, 4);
    String zoneId = memberId.substring(4, 8);
    String branchId = memberId.substring(8, 12);

    Connection connection = DBConnection.getConnection();
    try
    {
      CallableStatement callable = connection.prepareCall("{?=call funcClosureRequest(?,?,?,?,?,?,?,?)}");

      callable.registerOutParameter(1, 4);

      callable.setString(2, bankId);
      callable.setString(3, zoneId);
      callable.setString(4, branchId);
      callable.setString(5, cgpan);
      callable.setDate(6, startDate);
      callable.setString(7, closureRemarks);
      callable.setString(8, userId);
      callable.registerOutParameter(9, 12);
      callable.execute();
      int errorCode = callable.getInt(1);

      String error = callable.getString(9);

      Log.log(5, "GMDAO", methodName, "error code and error" + errorCode + "," + error);

      if (errorCode == 1)
      {
        Log.log(2, "GMDAO", methodName, error);

        callable.close();
        callable = null;
        throw new DatabaseException(error);
      }

      callable.close();
      callable = null;
    }
    catch (SQLException e)
    {
      Log.log(2, "GMDAO", methodName, e.getMessage());

      Log.logException(e);

      throw new DatabaseException("Unable to insert app_closure_request details.");
    }
    finally
    {
      DBConnection.freeConnection(connection);
    }
    Log.log(4, "GMDAO", methodName, "Exited");
  }

  public void submitexceptionalNpaUpdaterequest(String memberId, String cgpan, String remarks, String userId, String ssiRefNo)
    throws DatabaseException
  {
    String methodName = "submitexceptionalNpaUpdaterequest";
    Log.log(4, "GMDAO", methodName, "Entered");
    String bankId = memberId.substring(0, 4);
    String zoneId = memberId.substring(4, 8);
    String branchId = memberId.substring(8, 12);
    Connection connection = DBConnection.getConnection();
    try
    {
      CallableStatement callable = connection.prepareCall("{?=call funcInsertNpaUpdationRequest(?,?,?,?,?,?,?,?)}");
      callable.registerOutParameter(1, 4);
      callable.setString(2, memberId.substring(0, 4));
      callable.setString(3, memberId.substring(4, 8));
      callable.setString(4, memberId.substring(8, 12));
      callable.setString(5, cgpan);
      callable.setString(6, ssiRefNo);
      callable.setString(7, remarks);
      callable.setString(8, userId);
      callable.registerOutParameter(9, 12);
      callable.execute();
      int errorCode = callable.getInt(1);
      String error = callable.getString(9);
      Log.log(5, "GMDAO", methodName, "error code and error" + errorCode + "," + error);
      if (errorCode == 1)
      {
        Log.log(2, "GMDAO", methodName, error);
        callable.close();
        callable = null;
        throw new DatabaseException(error);
      }
      callable.close();
      callable = null;
    }
    catch (SQLException e)
    {
      Log.log(2, "GMDAO", methodName, e.getMessage());
      Log.logException(e);
      throw new DatabaseException("Unable to insert NPA Update Request details for " + cgpan);
    }
    finally
    {
      DBConnection.freeConnection(connection);
    }
    Log.log(4, "GMDAO", methodName, "Exited");
  }

  public int getExceptionBIDCount(String bid)
    throws DatabaseException
  {
    Log.log(4, "GMDAO", "getTotalApprovedAmt", "Entered");

    int bidCount = 0;

    Connection connection = DBConnection.getConnection();
    try
    {
      CallableStatement bidCountStmt = connection.prepareCall("{?=call Funccheckexceptionalbid(?,?,?)}");

      bidCountStmt.setString(2, bid);
      Log.log(4, "GMDAO", "getExceptionBIDCount", "getClaimCount");

      bidCountStmt.registerOutParameter(1, 4);
      bidCountStmt.registerOutParameter(3, 4);
      bidCountStmt.registerOutParameter(4, 12);

      bidCountStmt.execute();

      int totalApprovedStmtValue = bidCountStmt.getInt(1);

      if (totalApprovedStmtValue == 1)
      {
        String error = bidCountStmt.getString(4);

        bidCountStmt.close();
        bidCountStmt = null;

        connection.rollback();

        throw new DatabaseException(error);
      }

      bidCount = bidCountStmt.getInt(3);

      Log.log(4, "GMDAO", "getExceptionBIDCount", "getClaimCount :" + bidCount);

      bidCountStmt.close();
      bidCountStmt = null;
    }
    catch (SQLException sqlException)
    {
      Log.log(4, "GMDAO", "getExceptionBIDCount", sqlException.getMessage());
      Log.logException(sqlException);
      try
      {
        connection.rollback();
      }
      catch (SQLException ignore) {
        Log.log(4, "GMDAO", "getExceptionBIDCount", ignore.getMessage());
      }

      throw new DatabaseException(sqlException.getMessage());
    }
    finally {
      DBConnection.freeConnection(connection);
    }

    return bidCount;
  }

  public boolean updateDisbursement(DisbursementAmount disbursement, String userId)
    throws SQLException, DatabaseException
  {
    Log.log(4, "GMDAO", "updateDisbursement", "Entered");
    Connection connection = DBConnection.getConnection();
    CallableStatement updateDisbursementStmt = null;
    int updateStatus = 0;

    boolean updateDisbursementStatus = false;

    String error = null;

    if (disbursement != null) {
      try
      {
        updateDisbursementStmt = connection.prepareCall("{?=call funcUpdateDBRDetail(?,?,?,?,?,?)}");

        updateDisbursementStmt.registerOutParameter(1, 4);
        updateDisbursementStmt.setString(2, disbursement.getDisbursementId());
        updateDisbursementStmt.setDouble(3, disbursement.getDisbursementAmount());

        java.util.Date utilDate = disbursement.getDisbursementDate();
        java.sql.Date sqlDate = new java.sql.Date(utilDate.getTime());

        updateDisbursementStmt.setDate(4, sqlDate);
        updateDisbursementStmt.setString(5, disbursement.getFinalDisbursement());

        updateDisbursementStmt.setString(6, userId);
        updateDisbursementStmt.registerOutParameter(7, 12);

        updateDisbursementStmt.executeQuery();
        updateStatus = updateDisbursementStmt.getInt(1);
        error = updateDisbursementStmt.getString(7);

        if (updateStatus == 0) {
          updateDisbursementStatus = true;
          Log.log(5, "GMDAO", "updatedisbursement", "SP Success");
        }
        else if (updateStatus == 1) {
          updateDisbursementStatus = false;
          updateDisbursementStmt.close();
          updateDisbursementStmt = null;
          Log.log(2, "GMDAO", "updateDisbursement", "Error " + error);
          connection.rollback();
          throw new DatabaseException(error);
        }
        updateDisbursementStmt.close();
        updateDisbursementStmt = null;
        connection.commit();
      }
      catch (Exception exception) {
        Log.logException(exception);
        try
        {
          connection.rollback();
        }
        catch (SQLException localSQLException) {
            localSQLException.printStackTrace();
        }
        throw new DatabaseException(exception.getMessage());
      }
      finally {
        DBConnection.freeConnection(connection);
      }
    }
    Log.log(4, "GMDAO", "updateDisbursement", "Exited");

    return updateDisbursementStatus;
  }

  public boolean insertDisbursement(DisbursementAmount disbursement, String userId)
    throws SQLException, DatabaseException
  {
    Log.log(4, "GMDAO", "insertDisbursement", "Entered");
    //System.out.println("GM DAO insertDisbursement Entered");
    Connection connection = DBConnection.getConnection();
    CallableStatement updateDisbursementStmt = null;
    int updateStatus = 0;

    boolean updateDisbursementStatus = false;

    String error = null;

    if (disbursement != null) {
      try
      {
        updateDisbursementStmt = connection.prepareCall("{?=call funcInsertDisbursementDtl(?,?,?,?,?,?)}");

        updateDisbursementStmt.registerOutParameter(1, 4);
        updateDisbursementStmt.setString(2, disbursement.getCgpan());
        
        updateDisbursementStmt.setDouble(3, disbursement.getDisbursementAmount());
        java.util.Date utilDate = disbursement.getDisbursementDate();
        java.sql.Date sqlDate = new java.sql.Date(utilDate.getTime());

        updateDisbursementStmt.setDate(4, sqlDate);
        
        updateDisbursementStmt.setString(5, disbursement.getFinalDisbursement());
          //System.out.println("CGPAN:" + disbursement.getCgpan()+ "---Disbursement Amount:" + disbursement.getDisbursementAmount()+"---Disbursement Date:" + sqlDate);
        updateDisbursementStmt.setString(6, userId);
        updateDisbursementStmt.registerOutParameter(7, 12);

        updateDisbursementStmt.executeQuery();
        updateStatus = updateDisbursementStmt.getInt(1);

        error = updateDisbursementStmt.getString(7);

        if (updateStatus == 0) {
          updateDisbursementStatus = true;
          Log.log(5, "GMDAO", "insertdisbursement", "SP Success");
        }
        else if (updateStatus == 1) {
          updateDisbursementStatus = false;
          updateDisbursementStmt.close();
          updateDisbursementStmt = null;
          Log.log(2, "GMDAO", "updateDisbursement", "Exception " + error);
          connection.rollback();
          throw new DatabaseException(error);
        }
        updateDisbursementStmt.close();
        updateDisbursementStmt = null;
        connection.commit();
      }
      catch (Exception exception) {
        Log.logException(exception);
        try
        {
          connection.rollback();
        }
        catch (SQLException localSQLException) {
            localSQLException.printStackTrace();
        }
        throw new DatabaseException(exception.getMessage());
      }
      finally {
        DBConnection.freeConnection(connection);
      }
    }
    Log.log(4, "GMDAO", "updateDisbursement", "Exited");

    return updateDisbursementStatus;
  }

    public boolean insertNPADetails(NPADetails npaDetails,Vector tcVector,Vector wcVector,Map securityMap)throws DatabaseException
       {

                    Log.log(Log.INFO,"GMDAO","insertNPADetails","Entered");
                    Connection connection = DBConnection.getConnection(false);
                    CallableStatement addNPADetailsStmt = null;
                    int updateStatus=0;

                    
                    boolean addNPADetailsStatus = false;
                    //value set to return

                    java.util.Date utilDate;
                    java.sql.Date sqlDate;
                    String npaId = null;
                 
                    String npaexception = null;


                    try
                    {
                            sqlDate = new java.sql.Date(0);
                            utilDate = new java.util.Date();

                            /*Creates a CallableStatement object for calling
                            database stored procedures*/

                            addNPADetailsStmt = connection.prepareCall(
                                                    "{?=call funcInsertNPADtlNew(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)}");
                            addNPADetailsStmt.registerOutParameter(1,java.sql.Types.INTEGER);
                            addNPADetailsStmt.registerOutParameter(16,java.sql.Types.VARCHAR);

                            addNPADetailsStmt.setString(2,npaDetails.getCgbid());
                            Log.log(Log.DEBUG,"GMDAO","insertNPADetails","2----------------"+npaDetails.getCgbid());

                            SimpleDateFormat dateFormat=new SimpleDateFormat("dd/MM/yyyy");
                            String formatedDate=null;
                            //java.util.Date utilDateNpa = npaDetails.getNpaDate();
                            if(npaDetails.getNpaDate()!= null)
                            {
                                    //sqlDate = new java.sql.Date(utilDateNpa.getTime());

                            	SimpleDateFormat format = new SimpleDateFormat("dd/MM/yyyy");
                     	       Date parsednpadate = format.parse(npaDetails.getNpaDate().toString());
                     	       java.sql.Date sqlNpaDate = new java.sql.Date(parsednpadate.getTime());
                                    addNPADetailsStmt.setDate(3,sqlNpaDate);
                                    //System.out.println("npa date:"+npaDetails.getNpaDate()+" npa date in sql:"+new java.sql.Date(npaDetails.getNpaDate().getTime()));
                            } else
                            {
                                    addNPADetailsStmt.setDate(3,null);
                            }
                            Log.log(Log.DEBUG,"GMDAO","insertNPADetails","3----------------"+sqlDate);

                            addNPADetailsStmt.setString(4,npaDetails.getIsAsPerRBI());
                            Log.log(Log.DEBUG,"GMDAO","insertNPADetails","4----------------"+npaDetails.getIsAsPerRBI());
            
                            addNPADetailsStmt.setString(5,npaDetails.getNpaConfirm());
                            Log.log(Log.DEBUG,"GMDAO","insertNPADetails","5----------------");
          
                            addNPADetailsStmt.setString(6,npaDetails.getNpaReason());
                            Log.log(Log.DEBUG,"GMDAO","insertNPADetails","6----------------"+npaDetails.getNpaReason());
     
                            addNPADetailsStmt.setString(7,npaDetails.getEffortsTaken());
                            Log.log(Log.DEBUG,"GMDAO","insertNPADetails","7----------------"+npaDetails.getEffortsTaken());
     
                            addNPADetailsStmt.setString(8,npaDetails.getIsAcctReconstructed());
                            Log.log(Log.DEBUG,"GMDAO","insertNPADetails","8----------------"+npaDetails.getIsAcctReconstructed());
     
                            addNPADetailsStmt.setString(9,npaDetails.getSubsidyFlag());
                            Log.log(Log.DEBUG,"GMDAO","insertNPADetails","9----------------"+npaDetails.getSubsidyFlag());

                            addNPADetailsStmt.setString(10,npaDetails.getIsSubsidyRcvd());
                            Log.log(Log.DEBUG,"GMDAO","insertNPADetails","10----------------"+npaDetails.getIsSubsidyRcvd());
                            
                            addNPADetailsStmt.setString(11,npaDetails.getIsSubsidyAdjusted());
                            Log.log(Log.DEBUG,"GMDAO","insertNPADetails","11----------------"+npaDetails.getIsSubsidyAdjusted());

                            addNPADetailsStmt.setDouble(12,npaDetails.getSubsidyLastRcvdAmt());
                            Log.log(Log.DEBUG,"GMDAO","insertNPADetails","12----------------"+npaDetails.getSubsidyLastRcvdAmt());

                            java.util.Date utilDateSubsidy = npaDetails.getSubsidyLastRcvdDt();
                            if(utilDateSubsidy!=null && !utilDateSubsidy.toString().equals(""))
                            {
                                    //sqlDate = new java.sql.Date(utilDateSubsidy.getTime());
                                    addNPADetailsStmt.setDate(13,new java.sql.Date(utilDateSubsidy.getTime()));                                  
                            }else
                            {
                                    addNPADetailsStmt.setNull(13,java.sql.Types.DATE);
                            }
                            Log.log(Log.DEBUG,"GMDAO","insertNPADetails","13----------------"+sqlDate);

                          //java.util.Date utilDateInspection = npaDetails.getLastInspectionDt();
                            if(npaDetails.getLastInspectionDt()!=null && !npaDetails.getLastInspectionDt().toString().equals(""))
                            {
                                    //sqlDate = new java.sql.Date(utilDateInspection.getTime());
                                    addNPADetailsStmt.setDate(14,new java.sql.Date(npaDetails.getLastInspectionDt().getTime()));
                                    //System.out.println("inspection date:"+npaDetails.getLastInspectionDt()+" inspection date in sql:"+new java.sql.Date(npaDetails.getLastInspectionDt().getTime()));
                            }else
                            {
                                    addNPADetailsStmt.setNull(14,java.sql.Types.DATE);
                            }
                            
                            addNPADetailsStmt.registerOutParameter(15, java.sql.Types.VARCHAR);
                            
                            addNPADetailsStmt.executeQuery();

                            updateStatus=addNPADetailsStmt.getInt(1);
                            npaexception = addNPADetailsStmt.getString(16);
     

                            npaId = addNPADetailsStmt.getString(15);
                            Log.log(Log.DEBUG,"GMDAO","insertNPADetails","22----------------"+npaId);
      
                            if (updateStatus==Constants.FUNCTION_SUCCESS)
                            {
                                    addNPADetailsStatus = true;
                                    Log.log(Log.DEBUG,"GMDAO","insertNPADetails","insertnpadetails - SUCCESS");

                            }
                            else if (updateStatus==Constants.FUNCTION_FAILURE)
                            {
                                    connection.rollback();
                                    addNPADetailsStatus =false;
                                    addNPADetailsStmt.close();
                                    addNPADetailsStmt = null;
                                    Log.log(Log.ERROR,"GMDAO","insert NPA Details","Exception "+npaexception);
                                    throw new DatabaseException(npaexception);
                            }

                            Log.log(Log.DEBUG,"GMDAO","insertNPADetails","npa id befor setng to npa obj"+npaId);
                            npaDetails.setNpaId(npaId);
                            Log.log(Log.DEBUG,"GMDAO","insertNPADetails","npa id sfter setng to npa obj"+npaDetails.getNpaId());
                    //      addNPADetailsStmt.close();
                    //      addNPADetailsStmt = null;

                            
                            
                            //insert tcdetails
                            
                             
                            for(int i=0;i<tcVector.size();i++){
                                Map map = (java.util.Map)tcVector.get(i);
                                
                                addNPADetailsStmt = connection.prepareCall("{?=call funcInsertNPATLDetails(?,?,?,?,?,?,?,?,?,?,?,?,?)}");
                                addNPADetailsStmt.registerOutParameter(1,Types.INTEGER);
                                addNPADetailsStmt.registerOutParameter(14,Types.VARCHAR);
                                
                                addNPADetailsStmt.setString(2,npaId);
                                addNPADetailsStmt.setString(3,(String)map.get("CGPAN"));
                                java.util.Date d = (Date)map.get("FIRST_DISB_DT");
                                addNPADetailsStmt.setDate(4,new java.sql.Date(d.getTime()));
                                d = (Date)map.get("LAST_DISB_DT");
                                addNPADetailsStmt.setDate(5,new java.sql.Date(d.getTime()));
                                d = (Date)map.get("FIRST_INST_DT");
                                addNPADetailsStmt.setDate(6,new java.sql.Date(d.getTime()));
                                addNPADetailsStmt.setDouble(7,(Double)map.get("PRINCIPAL_REPAY"));
                                addNPADetailsStmt.setDouble(8,(Double)map.get("INTEREST_REPAY"));
                                addNPADetailsStmt.setInt(9,(Integer)map.get("PRINCIPAL_MORATORIUM"));
                                addNPADetailsStmt.setInt(10,(Integer)map.get("INTEREST_MORATORIUM"));
                                addNPADetailsStmt.setDouble(11,(Double)map.get("TOTAL_DISB_AMT"));                           
                                addNPADetailsStmt.setDouble(12,(Double)map.get("PRINCIPAL_OS"));
                                addNPADetailsStmt.setDouble(13,(Double)map.get("INTEREST_OS"));
                                
                                addNPADetailsStmt.executeQuery();
                                updateStatus=addNPADetailsStmt.getInt(1);
                                npaexception = addNPADetailsStmt.getString(14);
                                
                                if (updateStatus==Constants.FUNCTION_SUCCESS)
                                {
                                        addNPADetailsStatus = true;
                                        Log.log(Log.DEBUG,"GMDAO","insertNPADetails","insertnpadetails - SUCCESS");

                                }
                                else if (updateStatus==Constants.FUNCTION_FAILURE)
                                {
                                        connection.rollback();
                                        addNPADetailsStatus =false;
                                        addNPADetailsStmt.close();
                                        addNPADetailsStmt = null;
                                        Log.log(Log.ERROR,"GMDAO","insert NPA Details","Exception "+npaexception);
                           
                                        throw new DatabaseException(npaexception);
                                }
                            }
                            
                        addNPADetailsStmt.close();
                        addNPADetailsStmt = null;
                        
                        //insert wcdetails
                        
                              
                            for(int i=0;i<wcVector.size();i++){
                                Map map = (java.util.Map)wcVector.get(i);
                                
                                addNPADetailsStmt = connection.prepareCall("{?=call funcInsertNPAWCDetails(?,?,?,?,?)}");
                                addNPADetailsStmt.registerOutParameter(1,Types.INTEGER);
                                addNPADetailsStmt.registerOutParameter(6,Types.VARCHAR);
                                
                                addNPADetailsStmt.setString(2,npaId);
                                addNPADetailsStmt.setString(3,(String)map.get("CGPAN"));
                                addNPADetailsStmt.setDouble(4,(Double)map.get("PRINCIPAL_OS"));
                                addNPADetailsStmt.setDouble(5,(Double)map.get("INTEREST_OS"));
                                
                                addNPADetailsStmt.executeQuery();
                                updateStatus=addNPADetailsStmt.getInt(1);
                                npaexception = addNPADetailsStmt.getString(6);
                                
                                if (updateStatus==Constants.FUNCTION_SUCCESS)
                                {
                                        addNPADetailsStatus = true;
                                        Log.log(Log.DEBUG,"GMDAO","insertNPADetails","insertnpadetails - SUCCESS");

                                }
                                else if (updateStatus==Constants.FUNCTION_FAILURE)
                                {
                                        connection.rollback();
                                        addNPADetailsStatus =false;
                                        addNPADetailsStmt.close();
                                        addNPADetailsStmt = null;
                                        Log.log(Log.ERROR,"GMDAO","insert NPA Details","Exception "+npaexception);
                                //    //System.out.println("Exception "+npaexception);
                                        throw new DatabaseException(npaexception);
                                }
                            }
                            
                       // addNPADetailsStmt.close();
                       // addNPADetailsStmt = null;
                        
                           //insert security detail
                           
                           Map securityAsOnSancDt = (Map)securityMap.get("securityAsOnSancDt");
                           Map securityAsOnNpaDt = (Map)securityMap.get("securityAsOnNpaDt");
                           Double networthAsOnSancDt = (Double)securityMap.get("networthAsOnSancDt");
                           Double networthAsOnNpaDt = (Double)securityMap.get("networthAsOnNpaDt");
                           String reasonForReductionAsOnNpaDt = (String)securityMap.get("reasonForReductionAsOnNpaDt");
                          
                        Vector securityIds = new Vector();
                           
                        Map securityAsOnSanc = new HashMap();
                            securityAsOnSanc.put("flag","SAN");
                            securityAsOnSanc.put("networth",networthAsOnSancDt);
                            securityAsOnSanc.put("reasonforreduction","NA");
                        
                        Map securityAsOnNpa = new HashMap();
                            securityAsOnNpa.put("flag","NPA");
                            securityAsOnNpa.put("networth",networthAsOnNpaDt);
                            securityAsOnNpa.put("reasonforreduction",reasonForReductionAsOnNpaDt);
                            
                       Vector vector = new Vector();
                       vector.add(securityAsOnSanc);
                       vector.add(securityAsOnNpa);
                        
                           
                       
                        
                        for(int i=0;i<vector.size();i++){
                            Map map = (Map)vector.get(i);
                            
                            addNPADetailsStmt = connection.prepareCall("{?=call funcInsertNPASecDetails(?,?,?,?,?,?)}") ;
                              
                            addNPADetailsStmt.registerOutParameter(1,Types.INTEGER); 
                            addNPADetailsStmt.registerOutParameter(6,Types.INTEGER);
                            addNPADetailsStmt.registerOutParameter(7,Types.VARCHAR);
                            addNPADetailsStmt.setString(2,npaId); 
                            addNPADetailsStmt.setString(3,(String)map.get("flag"));
                            
                            Double networth = (Double)map.get("networth");
                            
                            addNPADetailsStmt.setDouble(4,networth.doubleValue());
                            
                            addNPADetailsStmt.setString(5,(String)map.get("reasonforreduction"));
                            
                            addNPADetailsStmt.executeQuery();
                            updateStatus=addNPADetailsStmt.getInt(1);
                            npaexception = addNPADetailsStmt.getString(7);
                         
                            
                            if (updateStatus==Constants.FUNCTION_SUCCESS)
                            {
                                    addNPADetailsStatus = true;
                                    Log.log(Log.DEBUG,"GMDAO","insertNPADetails","insertnpadetails - SUCCESS");
                                  securityIds.add(addNPADetailsStmt.getInt(6));
                                    
                            }
                            else if (updateStatus==Constants.FUNCTION_FAILURE)
                            {
                                    connection.rollback();
                                    addNPADetailsStatus =false;
                                    addNPADetailsStmt.close();
                                    addNPADetailsStmt = null;
                                    Log.log(Log.ERROR,"GMDAO","insert NPA Details","Exception "+npaexception);
                                    throw new DatabaseException(npaexception);
                            }
                        }
                        
                        addNPADetailsStmt.close();
                        addNPADetailsStmt = null;
                        
                        double landval = 0.0;
                        double bldgval = 0.0;
                        double macval = 0.0;
                        double movval = 0.0;
                        double currval = 0.0;
                        double othval = 0.0;
                        Integer id = (Integer)securityIds.get(0);
             //           Integer id = 123;
                        
                        addNPADetailsStmt = connection.prepareCall("{?= call funcInsertNPASecParticular(?,?,?,?,?)}");
                        addNPADetailsStmt.registerOutParameter(1,Types.INTEGER);
                        addNPADetailsStmt.registerOutParameter(6,Types.VARCHAR);
                       
                        if(id != null || !("".equals(id))){
                            addNPADetailsStmt.setInt(2,id.intValue());
                        }
                        addNPADetailsStmt.setString(3,"LAND");
                        String landstr = (String)securityAsOnSancDt.get("LAND");
                        Double land = 0.0;
                        if(!GenericValidator.isBlankOrNull(landstr)){
                            land = Double.parseDouble(landstr);
                        }
                        
                        if(land != null || !("".equals(land))){
                            landval = land.doubleValue();
                        }
                        addNPADetailsStmt.setDouble(4,landval);
                        addNPADetailsStmt.setString(5,"NA");
                        
                        addNPADetailsStmt.executeQuery();
                        updateStatus=addNPADetailsStmt.getInt(1);
                        npaexception = addNPADetailsStmt.getString(6);
                        
                        if (updateStatus==Constants.FUNCTION_SUCCESS)
                        {
                                addNPADetailsStatus = true;
                                Log.log(Log.DEBUG,"GMDAO","insertNPADetails","insertnpadetails - SUCCESS");
                            
                        }
                        else if (updateStatus==Constants.FUNCTION_FAILURE)
                        {
                                connection.rollback();
                                addNPADetailsStatus =false;
                                addNPADetailsStmt.close();
                                addNPADetailsStmt = null;
                                Log.log(Log.ERROR,"GMDAO","insert NPA Details","Exception "+npaexception);
                        //    //System.out.println("Exception "+npaexception);
                                throw new DatabaseException(npaexception);
                        }
                        addNPADetailsStmt.close();
                        addNPADetailsStmt = null;
                       
                       
                        addNPADetailsStmt = connection.prepareCall("{?= call funcInsertNPASecParticular(?,?,?,?,?)}");
                        addNPADetailsStmt.registerOutParameter(1,Types.INTEGER);
                        addNPADetailsStmt.registerOutParameter(6,Types.VARCHAR);
                        
                        if(id != null || !("".equals(id))){
                            addNPADetailsStmt.setInt(2,id.intValue());
                        }
                        addNPADetailsStmt.setString(3,"BUILDING");
                        Double building = 0.0;
                        String buildingstr = (String)securityAsOnSancDt.get("BUILDING");
                        if(!GenericValidator.isBlankOrNull(buildingstr)){
                            building = Double.parseDouble(buildingstr);
                        }
                        
                        if(building != null || !("".equals(building))){
                            bldgval = building.doubleValue();
                        }
                        addNPADetailsStmt.setDouble(4,bldgval);
                        addNPADetailsStmt.setString(5,"NA");
                        
                        addNPADetailsStmt.executeQuery();
                        updateStatus=addNPADetailsStmt.getInt(1);
                        npaexception = addNPADetailsStmt.getString(6);
                        
                        if (updateStatus==Constants.FUNCTION_SUCCESS)
                        {
                                addNPADetailsStatus = true;
                                Log.log(Log.DEBUG,"GMDAO","insertNPADetails","insertnpadetails - SUCCESS");
                            
                        }
                        else if (updateStatus==Constants.FUNCTION_FAILURE)
                        {
                                connection.rollback();
                                addNPADetailsStatus =false;
                                addNPADetailsStmt.close();
                                addNPADetailsStmt = null;
                                Log.log(Log.ERROR,"GMDAO","insert NPA Details","Exception "+npaexception);
                        //    //System.out.println("Exception "+npaexception);
                                throw new DatabaseException(npaexception);
                        }
                        addNPADetailsStmt.close();
                        addNPADetailsStmt = null;
                        
                        
                        addNPADetailsStmt = connection.prepareCall("{?= call funcInsertNPASecParticular(?,?,?,?,?)}");
                        addNPADetailsStmt.registerOutParameter(1,Types.INTEGER);
                        addNPADetailsStmt.registerOutParameter(6,Types.VARCHAR);
                        
                        if(id != null || !("".equals(id))){
                            addNPADetailsStmt.setInt(2,id.intValue());
                        }
                        addNPADetailsStmt.setString(3,"MACHINE");
                        Double machine = 0.0;
                        String machinestr = (String)securityAsOnSancDt.get("MACHINE");
                        if(!GenericValidator.isBlankOrNull(machinestr)){
                            machine = Double.parseDouble(machinestr);
                        }
                        
                        if(machine != null || !("".equals(machine))){
                            macval = machine.doubleValue();
                        }
                        addNPADetailsStmt.setDouble(4,macval);
                        addNPADetailsStmt.setString(5,"NA");
                        
                        addNPADetailsStmt.executeQuery();
                        updateStatus=addNPADetailsStmt.getInt(1);
                        npaexception = addNPADetailsStmt.getString(6);
                        
                        if (updateStatus==Constants.FUNCTION_SUCCESS)
                        {
                                addNPADetailsStatus = true;
                                Log.log(Log.DEBUG,"GMDAO","insertNPADetails","insertnpadetails - SUCCESS");
                            
                        }
                        else if (updateStatus==Constants.FUNCTION_FAILURE)
                        {
                                connection.rollback();
                                addNPADetailsStatus =false;
                                addNPADetailsStmt.close();
                                addNPADetailsStmt = null;
                                Log.log(Log.ERROR,"GMDAO","insert NPA Details","Exception "+npaexception);
                        //    //System.out.println("Exception "+npaexception);
                                throw new DatabaseException(npaexception);
                        }
                        addNPADetailsStmt.close();
                        addNPADetailsStmt = null;
                        
                        
                        addNPADetailsStmt = connection.prepareCall("{?= call funcInsertNPASecParticular(?,?,?,?,?)}");
                        addNPADetailsStmt.registerOutParameter(1,Types.INTEGER);
                        addNPADetailsStmt.registerOutParameter(6,Types.VARCHAR);
                        
                        if(id != null || !("".equals(id))){
                            addNPADetailsStmt.setInt(2,id.intValue());
                        }
                        addNPADetailsStmt.setString(3,"OTHER FIXED MOVABLE ASSETS");
                        Double movableassets = 0.0;
                        String movableassetsstr = (String)securityAsOnSancDt.get("OTHER_FIXED_MOVABLE_ASSETS");
                        if(!GenericValidator.isBlankOrNull(movableassetsstr)){
                            movableassets = Double.parseDouble(movableassetsstr);
                        }
                        
                        if(movableassets != null || !("".equals(movableassets))){
                            movval = movableassets.doubleValue();
                        }
                        addNPADetailsStmt.setDouble(4,movval);
                        addNPADetailsStmt.setString(5,"NA");
                        
                        addNPADetailsStmt.executeQuery();
                        updateStatus=addNPADetailsStmt.getInt(1);
                        npaexception = addNPADetailsStmt.getString(6);
                        
                        if (updateStatus==Constants.FUNCTION_SUCCESS)
                        {
                                addNPADetailsStatus = true;
                                Log.log(Log.DEBUG,"GMDAO","insertNPADetails","insertnpadetails - SUCCESS");
                            
                        }
                        else if (updateStatus==Constants.FUNCTION_FAILURE)
                        {
                                connection.rollback();
                                addNPADetailsStatus =false;
                                addNPADetailsStmt.close();
                                addNPADetailsStmt = null;
                                Log.log(Log.ERROR,"GMDAO","insert NPA Details","Exception "+npaexception);
                        //    //System.out.println("Exception "+npaexception);
                                throw new DatabaseException(npaexception);
                        }
                        addNPADetailsStmt.close();
                        addNPADetailsStmt = null;
                        
                        
                        addNPADetailsStmt = connection.prepareCall("{?= call funcInsertNPASecParticular(?,?,?,?,?)}");
                        addNPADetailsStmt.registerOutParameter(1,Types.INTEGER);
                        addNPADetailsStmt.registerOutParameter(6,Types.VARCHAR);
                        
                        if(id != null || !("".equals(id))){
                            addNPADetailsStmt.setInt(2,id.intValue());
                        }
                        addNPADetailsStmt.setString(3,"CUR_ASSETS");
                        Double currassets = 0.0;
                        String currassetsstr = (String)securityAsOnSancDt.get("CUR_ASSETS");
                        if(!GenericValidator.isBlankOrNull(currassetsstr)){
                            currassets = Double.parseDouble(currassetsstr);
                        }
                        
                        if(currassets != null || !("".equals(currassets))){
                            currval = currassets.doubleValue();
                        }
                        addNPADetailsStmt.setDouble(4,currval);
                        addNPADetailsStmt.setString(5,"NA");
                        
                        addNPADetailsStmt.executeQuery();
                        updateStatus=addNPADetailsStmt.getInt(1);
                        npaexception = addNPADetailsStmt.getString(6);
                        
                        if (updateStatus==Constants.FUNCTION_SUCCESS)
                        {
                                addNPADetailsStatus = true;
                                Log.log(Log.DEBUG,"GMDAO","insertNPADetails","insertnpadetails - SUCCESS");
                            
                        }
                        else if (updateStatus==Constants.FUNCTION_FAILURE)
                        {
                                connection.rollback();
                                addNPADetailsStatus =false;
                                addNPADetailsStmt.close();
                                addNPADetailsStmt = null;
                                Log.log(Log.ERROR,"GMDAO","insert NPA Details","Exception "+npaexception);
                        //    //System.out.println("Exception "+npaexception);
                                throw new DatabaseException(npaexception);
                        }
                        addNPADetailsStmt.close();
                        addNPADetailsStmt = null;
                        
                        
                        addNPADetailsStmt = connection.prepareCall("{?= call funcInsertNPASecParticular(?,?,?,?,?)}");
                        addNPADetailsStmt.registerOutParameter(1,Types.INTEGER);
                        addNPADetailsStmt.registerOutParameter(6,Types.VARCHAR);
                        
                        if(id != null || !("".equals(id))){
                            addNPADetailsStmt.setInt(2,id.intValue());
                        }
                        addNPADetailsStmt.setString(3,"OTHERS");
                        Double others = 0.0;
                        String othersstr = (String)securityAsOnSancDt.get("OTHERS");
                        if(!GenericValidator.isBlankOrNull(othersstr)){
                            others = Double.parseDouble(othersstr);
                        }
                        
                        if(others != null || !("".equals(others))){
                            othval = others.doubleValue();
                        }
                        addNPADetailsStmt.setDouble(4,othval);
                        addNPADetailsStmt.setString(5,"NA");
                        
                        addNPADetailsStmt.executeQuery();
                        updateStatus=addNPADetailsStmt.getInt(1);
                        npaexception = addNPADetailsStmt.getString(6);
                        
                        if (updateStatus==Constants.FUNCTION_SUCCESS)
                        {
                                addNPADetailsStatus = true;
                                Log.log(Log.DEBUG,"GMDAO","insertNPADetails","insertnpadetails - SUCCESS");
                            
                        }
                        else if (updateStatus==Constants.FUNCTION_FAILURE)
                        {
                                connection.rollback();
                                addNPADetailsStatus =false;
                                addNPADetailsStmt.close();
                                addNPADetailsStmt = null;
                                Log.log(Log.ERROR,"GMDAO","insert NPA Details","Exception "+npaexception);
                        //    //System.out.println("Exception "+npaexception);
                                throw new DatabaseException(npaexception);
                        }
                        addNPADetailsStmt.close();
                        addNPADetailsStmt = null;
                       
                       
                       /*AS ON NPA DATE*/
                      id = (Integer)securityIds.get(1);
                  //      id = 321;
                        
                        addNPADetailsStmt = connection.prepareCall("{?= call funcInsertNPASecParticular(?,?,?,?,?)}");
                        addNPADetailsStmt.registerOutParameter(1,Types.INTEGER);
                        addNPADetailsStmt.registerOutParameter(6,Types.VARCHAR);
                        
                        if(id != null || !("".equals(id))){
                            addNPADetailsStmt.setInt(2,id.intValue());
                        }
                        addNPADetailsStmt.setString(3,"LAND");
                         land = 0.0;
                         landstr = (String)securityAsOnNpaDt.get("LAND");
                         if(!GenericValidator.isBlankOrNull(landstr)){
                            land = Double.parseDouble(landstr);
                         }
                         
                        if(land != null || !("".equals(land))){
                            landval = land.doubleValue();
                        }
                        addNPADetailsStmt.setDouble(4,landval);
                        addNPADetailsStmt.setString(5,reasonForReductionAsOnNpaDt);
                        
                        addNPADetailsStmt.executeQuery();
                        updateStatus=addNPADetailsStmt.getInt(1);
                        npaexception = addNPADetailsStmt.getString(6);
                        
                        if (updateStatus==Constants.FUNCTION_SUCCESS)
                        {
                                addNPADetailsStatus = true;
                                Log.log(Log.DEBUG,"GMDAO","insertNPADetails","insertnpadetails - SUCCESS");
                            
                        }
                        else if (updateStatus==Constants.FUNCTION_FAILURE)
                        {
                                connection.rollback();
                                addNPADetailsStatus =false;
                                addNPADetailsStmt.close();
                                addNPADetailsStmt = null;
                                Log.log(Log.ERROR,"GMDAO","insert NPA Details","Exception "+npaexception);
                        //    //System.out.println("Exception "+npaexception);
                                throw new DatabaseException(npaexception);
                        }
                        addNPADetailsStmt.close();
                        addNPADetailsStmt = null;
                        
                        
                        addNPADetailsStmt = connection.prepareCall("{?= call funcInsertNPASecParticular(?,?,?,?,?)}");
                        addNPADetailsStmt.registerOutParameter(1,Types.INTEGER);
                        addNPADetailsStmt.registerOutParameter(6,Types.VARCHAR);
                        
                        if(id != null || !("".equals(id))){
                            addNPADetailsStmt.setInt(2,id.intValue());
                        }
                        addNPADetailsStmt.setString(3,"BUILDING");
                         building = 0.0;
                         buildingstr = (String)securityAsOnNpaDt.get("BUILDING");
                        if(!GenericValidator.isBlankOrNull(buildingstr)){
                           building = Double.parseDouble(buildingstr);
                        }
                         
                        if(building != null || !("".equals(building))){
                            bldgval = building.doubleValue();
                        }
                        addNPADetailsStmt.setDouble(4,bldgval);
                        addNPADetailsStmt.setString(5,reasonForReductionAsOnNpaDt);
                        
                        addNPADetailsStmt.executeQuery();
                        updateStatus=addNPADetailsStmt.getInt(1);
                        npaexception = addNPADetailsStmt.getString(6);
                        
                        if (updateStatus==Constants.FUNCTION_SUCCESS)
                        {
                                addNPADetailsStatus = true;
                                Log.log(Log.DEBUG,"GMDAO","insertNPADetails","insertnpadetails - SUCCESS");
                            
                        }
                        else if (updateStatus==Constants.FUNCTION_FAILURE)
                        {
                                connection.rollback();
                                addNPADetailsStatus =false;
                                addNPADetailsStmt.close();
                                addNPADetailsStmt = null;
                                Log.log(Log.ERROR,"GMDAO","insert NPA Details","Exception "+npaexception);
                        //    //System.out.println("Exception "+npaexception);
                                throw new DatabaseException(npaexception);
                        }
                        addNPADetailsStmt.close();
                        addNPADetailsStmt = null;
                        
                        
                        addNPADetailsStmt = connection.prepareCall("{?= call funcInsertNPASecParticular(?,?,?,?,?)}");
                        addNPADetailsStmt.registerOutParameter(1,Types.INTEGER);
                        addNPADetailsStmt.registerOutParameter(6,Types.VARCHAR);
                        
                        if(id != null || !("".equals(id))){
                            addNPADetailsStmt.setInt(2,id.intValue());
                        }
                        addNPADetailsStmt.setString(3,"MACHINE");
                         machine = 0.0;
                         machinestr = (String)securityAsOnNpaDt.get("MACHINE");
                        if(!GenericValidator.isBlankOrNull(machinestr)){
                           machine = Double.parseDouble(machinestr);
                        }
                         
                        if(machine != null || !("".equals(machine))){
                            macval = machine.doubleValue();
                        }
                        addNPADetailsStmt.setDouble(4,macval);
                        addNPADetailsStmt.setString(5,reasonForReductionAsOnNpaDt);
                        
                        addNPADetailsStmt.executeQuery();
                        updateStatus=addNPADetailsStmt.getInt(1);
                        npaexception = addNPADetailsStmt.getString(6);
                        
                        if (updateStatus==Constants.FUNCTION_SUCCESS)
                        {
                                addNPADetailsStatus = true;
                                Log.log(Log.DEBUG,"GMDAO","insertNPADetails","insertnpadetails - SUCCESS");
                            
                        }
                        else if (updateStatus==Constants.FUNCTION_FAILURE)
                        {
                                connection.rollback();
                                addNPADetailsStatus =false;
                                addNPADetailsStmt.close();
                                addNPADetailsStmt = null;
                                Log.log(Log.ERROR,"GMDAO","insert NPA Details","Exception "+npaexception);
                        //    //System.out.println("Exception "+npaexception);
                                throw new DatabaseException(npaexception);
                        }
                        addNPADetailsStmt.close();
                        addNPADetailsStmt = null;
                        
                        
                        addNPADetailsStmt = connection.prepareCall("{?= call funcInsertNPASecParticular(?,?,?,?,?)}");
                        addNPADetailsStmt.registerOutParameter(1,Types.INTEGER);
                        addNPADetailsStmt.registerOutParameter(6,Types.VARCHAR);
                        
                        if(id != null || !("".equals(id))){
                            addNPADetailsStmt.setInt(2,id.intValue());
                        }
                        addNPADetailsStmt.setString(3,"OTHER FIXED MOVABLE ASSETS");
                         movableassets = 0.0;
                         movableassetsstr = (String)securityAsOnNpaDt.get("OTHER_FIXED_MOVABLE_ASSETS");
                        if(!GenericValidator.isBlankOrNull(movableassetsstr)){
                           movableassets = Double.parseDouble(movableassetsstr);
                        }
                         
                        if(movableassets != null || !("".equals(movableassets))){
                            movval = movableassets.doubleValue();
                        }
                        addNPADetailsStmt.setDouble(4,movval);
                        addNPADetailsStmt.setString(5,reasonForReductionAsOnNpaDt);
                        
                        addNPADetailsStmt.executeQuery();
                        updateStatus=addNPADetailsStmt.getInt(1);
                        npaexception = addNPADetailsStmt.getString(6);
                        
                        if (updateStatus==Constants.FUNCTION_SUCCESS)
                        {
                                addNPADetailsStatus = true;
                                Log.log(Log.DEBUG,"GMDAO","insertNPADetails","insertnpadetails - SUCCESS");
                            
                        }
                        else if (updateStatus==Constants.FUNCTION_FAILURE)
                        {
                                connection.rollback();
                                addNPADetailsStatus =false;
                                addNPADetailsStmt.close();
                                addNPADetailsStmt = null;
                                Log.log(Log.ERROR,"GMDAO","insert NPA Details","Exception "+npaexception);
                        //    //System.out.println("Exception "+npaexception);
                                throw new DatabaseException(npaexception);
                        }
                        addNPADetailsStmt.close();
                        addNPADetailsStmt = null;
                        
                        
                        addNPADetailsStmt = connection.prepareCall("{?= call funcInsertNPASecParticular(?,?,?,?,?)}");
                        addNPADetailsStmt.registerOutParameter(1,Types.INTEGER);
                        addNPADetailsStmt.registerOutParameter(6,Types.VARCHAR);
                        
                        if(id != null || !("".equals(id))){
                            addNPADetailsStmt.setInt(2,id.intValue());
                        }
                        addNPADetailsStmt.setString(3,"CUR_ASSETS");
                         currassets = 0.0;
                         currassetsstr = (String)securityAsOnNpaDt.get("CUR_ASSETS");
                        if(!GenericValidator.isBlankOrNull(currassetsstr)){
                           currassets = Double.parseDouble(currassetsstr);
                        }
                         
                        if(currassets != null || !("".equals(currassets))){
                            currval = currassets.doubleValue();
                        }
                        addNPADetailsStmt.setDouble(4,currval);
                        addNPADetailsStmt.setString(5,reasonForReductionAsOnNpaDt);
                        
                        addNPADetailsStmt.executeQuery();
                        updateStatus=addNPADetailsStmt.getInt(1);
                        npaexception = addNPADetailsStmt.getString(6);
                        
                        if (updateStatus==Constants.FUNCTION_SUCCESS)
                        {
                                addNPADetailsStatus = true;
                                Log.log(Log.DEBUG,"GMDAO","insertNPADetails","insertnpadetails - SUCCESS");
                            
                        }
                        else if (updateStatus==Constants.FUNCTION_FAILURE)
                        {
                                connection.rollback();
                                addNPADetailsStatus =false;
                                addNPADetailsStmt.close();
                                addNPADetailsStmt = null;
                                Log.log(Log.ERROR,"GMDAO","insert NPA Details","Exception "+npaexception);
                        //    //System.out.println("Exception "+npaexception);
                                throw new DatabaseException(npaexception);
                        }
                        addNPADetailsStmt.close();
                        addNPADetailsStmt = null;
                        
                        
                        addNPADetailsStmt = connection.prepareCall("{?= call funcInsertNPASecParticular(?,?,?,?,?)}");
                        addNPADetailsStmt.registerOutParameter(1,Types.INTEGER);
                        addNPADetailsStmt.registerOutParameter(6,Types.VARCHAR);
                        
                        if(id != null || !("".equals(id))){
                            addNPADetailsStmt.setInt(2,id.intValue());
                        }
                        addNPADetailsStmt.setString(3,"OTHERS");
                         others = 0.0;
                         othersstr = (String)securityAsOnNpaDt.get("OTHERS");
                        if(!GenericValidator.isBlankOrNull(othersstr)){
                           others = Double.parseDouble(othersstr);
                        }
                         
                        if(others != null || !("".equals(others))){
                            othval = others.doubleValue();
                        }
                        addNPADetailsStmt.setDouble(4,othval);
                        addNPADetailsStmt.setString(5,reasonForReductionAsOnNpaDt);
                        
                        addNPADetailsStmt.executeQuery();
                        updateStatus=addNPADetailsStmt.getInt(1);
                        npaexception = addNPADetailsStmt.getString(6);
                        
                        if (updateStatus==Constants.FUNCTION_SUCCESS)
                        {
                                addNPADetailsStatus = true;
                                Log.log(Log.DEBUG,"GMDAO","insertNPADetails","insertnpadetails - SUCCESS");
                            
                        }
                        else if (updateStatus==Constants.FUNCTION_FAILURE)
                        {
                                connection.rollback();
                                addNPADetailsStatus =false;
                                addNPADetailsStmt.close();
                                addNPADetailsStmt = null;
                                Log.log(Log.ERROR,"GMDAO","insert NPA Details","Exception "+npaexception);
                        //    //System.out.println("Exception "+npaexception);
                                throw new DatabaseException(npaexception);
                        }
                        addNPADetailsStmt.close();
                        addNPADetailsStmt = null;
                       
                       
                       
                       
                        

                            connection.commit();
                    }catch (Exception exception)
                    {
                            Log.logException(exception);
                            try
                            {
                                    connection.rollback();
                            }
                            catch (SQLException ignore){}

                            throw new DatabaseException(exception.getMessage());
                    }
                    finally
                    {
                                    DBConnection.freeConnection(connection);
                    }
                    Log.log(Log.INFO,"GMDAO","insertNPADetails","Exited");

                    return addNPADetailsStatus;
            }

    public boolean updateNPADetails(NPADetails npaDetails,Vector tcVector,Vector wcVector,Map securityMap)throws DatabaseException
       {
                    Log.log(Log.INFO,"GMDAO","updateNPADetails","Entered");
                    Connection connection = DBConnection.getConnection(false);
                    CallableStatement addNPADetailsStmt = null;
                    int updateStatus=0;

                   
                    boolean addNPADetailsStatus = false;
                   

                    java.util.Date utilDate;
                    java.sql.Date sqlDate;
                    String npaId = null;
                    String npaexception = null;

                    try
                    {
                            sqlDate = new java.sql.Date(0);
                            utilDate = new java.util.Date();

                            /*Creates a CallableStatement object for calling
                            database stored procedures*/

                            addNPADetailsStmt = connection.prepareCall(
                                                    "{?=call funcUpdateNPADtlMod(?,?,?,?,?,?,?,?,?,?,?,?,?)}");
                        addNPADetailsStmt.registerOutParameter(1,java.sql.Types.INTEGER);
                        addNPADetailsStmt.registerOutParameter(14,java.sql.Types.VARCHAR);

                        addNPADetailsStmt.setString(2,npaDetails.getCgbid());
                        Log.log(Log.DEBUG,"GMDAO","insertNPADetails","2----------------"+npaDetails.getCgbid());

                        SimpleDateFormat dateFormat=new SimpleDateFormat("dd/MM/yyyy");
                        String formatedDate=null;
                        

                        addNPADetailsStmt.setString(3,npaDetails.getIsAsPerRBI());
                        Log.log(Log.DEBUG,"GMDAO","insertNPADetails","3----------------"+npaDetails.getIsAsPerRBI());
                        
                        addNPADetailsStmt.setString(4,npaDetails.getNpaConfirm());
                        Log.log(Log.DEBUG,"GMDAO","insertNPADetails","4----------------");
                       
                        addNPADetailsStmt.setString(5,npaDetails.getEffortsTaken());
                        Log.log(Log.DEBUG,"GMDAO","insertNPADetails","5----------------"+npaDetails.getEffortsTaken());
                        
                        addNPADetailsStmt.setString(6,npaDetails.getIsAcctReconstructed());
                        Log.log(Log.DEBUG,"GMDAO","insertNPADetails","6----------------"+npaDetails.getIsAcctReconstructed());
                        
                        addNPADetailsStmt.setString(7,npaDetails.getSubsidyFlag());
                        Log.log(Log.DEBUG,"GMDAO","insertNPADetails","7----------------"+npaDetails.getSubsidyFlag());

                        addNPADetailsStmt.setString(8,npaDetails.getIsSubsidyRcvd());
                        Log.log(Log.DEBUG,"GMDAO","insertNPADetails","8----------------"+npaDetails.getIsSubsidyRcvd());
                        
                        addNPADetailsStmt.setString(9,npaDetails.getIsSubsidyAdjusted());
                        Log.log(Log.DEBUG,"GMDAO","insertNPADetails","9----------------"+npaDetails.getIsSubsidyAdjusted());

                        addNPADetailsStmt.setDouble(10,npaDetails.getSubsidyLastRcvdAmt());
                        Log.log(Log.DEBUG,"GMDAO","insertNPADetails","10----------------"+npaDetails.getSubsidyLastRcvdAmt());

                        utilDate = npaDetails.getSubsidyLastRcvdDt();
                        if(utilDate!=null && !utilDate.toString().equals(""))
                        {
                                sqlDate = new java.sql.Date(utilDate.getTime());
                                addNPADetailsStmt.setDate(11,sqlDate);
                        }else
                        {
                                addNPADetailsStmt.setNull(11,java.sql.Types.DATE);
                        }
                        Log.log(Log.DEBUG,"GMDAO","insertNPADetails","11----------------"+sqlDate);

                        utilDate = npaDetails.getLastInspectionDt();
                        if(utilDate!=null && !utilDate.toString().equals(""))
                        {
                                sqlDate = new java.sql.Date(utilDate.getTime());
                                addNPADetailsStmt.setDate(12,sqlDate);
                        }else
                        {
                                addNPADetailsStmt.setNull(12,java.sql.Types.DATE);
                        }
                        
                        addNPADetailsStmt.registerOutParameter(13, java.sql.Types.VARCHAR);
                        
                        addNPADetailsStmt.executeQuery();

                        updateStatus=addNPADetailsStmt.getInt(1);
                        npaexception = addNPADetailsStmt.getString(14);
                        

                        npaId = addNPADetailsStmt.getString(13);
             //npaId = "123";
                        Log.log(Log.DEBUG,"GMDAO","insertNPADetails","13----------------"+npaId);
                        
                        if (updateStatus==Constants.FUNCTION_SUCCESS)
                        {
                                addNPADetailsStatus = true;
                                Log.log(Log.DEBUG,"GMDAO","insertNPADetails","insertnpadetails - SUCCESS");

                        }
                        else if (updateStatus==Constants.FUNCTION_FAILURE)
                        {
                                connection.rollback();
                                addNPADetailsStatus =false;
                                addNPADetailsStmt.close();
                                addNPADetailsStmt = null;
                                Log.log(Log.ERROR,"GMDAO","insert NPA Details","Exception "+npaexception);
                                throw new DatabaseException(npaexception);
                        }

                           
                        npaDetails.setNpaId(npaId);
                        
                        addNPADetailsStmt.close();
                        addNPADetailsStmt = null;
                       
                        java.sql.Date disbdate = null;
                        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
                       
                       
                   
                        
                        
                        //insert tcdetails
                        
                         
                        for(int i=0;i<tcVector.size();i++){
                            Map map = (java.util.Map)tcVector.get(i);
                            
                            addNPADetailsStmt = connection.prepareCall("{?=call funcUpdateNPATLDtl(?,?,?,?,?,?,?,?,?,?,?,?,?)}");
                            addNPADetailsStmt.registerOutParameter(1,Types.INTEGER);
                            addNPADetailsStmt.registerOutParameter(14,Types.VARCHAR);
                            
                            addNPADetailsStmt.setString(2,npaId);
                            addNPADetailsStmt.setString(3,(String)map.get("CGPAN"));
                            Date d = (Date)map.get("FIRST_DISB_DT");
                            addNPADetailsStmt.setDate(4,new java.sql.Date(d.getTime()));
                            d = (Date)map.get("LAST_DISB_DT");
                            addNPADetailsStmt.setDate(5,new java.sql.Date(d.getTime()));
                            d = (Date)map.get("FIRST_INST_DT");
                            addNPADetailsStmt.setDate(6,new java.sql.Date(d.getTime()));
                            addNPADetailsStmt.setDouble(7,(Double)map.get("PRINCIPAL_REPAY"));
                            addNPADetailsStmt.setDouble(8,(Double)map.get("INTEREST_REPAY"));
                            addNPADetailsStmt.setInt(9,(Integer)map.get("PRINCIPAL_MORATORIUM"));
                            addNPADetailsStmt.setInt(10,(Integer)map.get("INTEREST_MORATORIUM"));
                            addNPADetailsStmt.setDouble(11,(Double)map.get("TOTAL_DISB_AMT"));                           
                            addNPADetailsStmt.setDouble(12,(Double)map.get("PRINCIPAL_OS"));
                            addNPADetailsStmt.setDouble(13,(Double)map.get("INTEREST_OS"));
                            
                            addNPADetailsStmt.executeQuery();
                            updateStatus=addNPADetailsStmt.getInt(1);
                            npaexception = addNPADetailsStmt.getString(14);
                            
                            if (updateStatus==Constants.FUNCTION_SUCCESS)
                            {
                                    addNPADetailsStatus = true;
                                    Log.log(Log.DEBUG,"GMDAO","insertNPADetails","insertnpadetails - SUCCESS");

                            }
                            else if (updateStatus==Constants.FUNCTION_FAILURE)
                            {
                                    connection.rollback();
                                    addNPADetailsStatus =false;
                                    addNPADetailsStmt.close();
                                    addNPADetailsStmt = null;
                                    Log.log(Log.ERROR,"GMDAO","insert NPA Details","Exception "+npaexception);
                        
                                    throw new DatabaseException(npaexception);
                            }
                        }
                        if(addNPADetailsStmt != null){
                        addNPADetailsStmt.close();
                        addNPADetailsStmt = null;
                        }
                        //insert wcdetails
                        
                          
                        for(int i=0;i<wcVector.size();i++){
                            Map map = (java.util.Map)wcVector.get(i);
                            
                            addNPADetailsStmt = connection.prepareCall("{?=call funcUpdateNPAWCDtl(?,?,?,?,?)}");
                            addNPADetailsStmt.registerOutParameter(1,Types.INTEGER);
                            addNPADetailsStmt.registerOutParameter(6,Types.VARCHAR);
                            
                            addNPADetailsStmt.setString(2,npaId);
                            addNPADetailsStmt.setString(3,(String)map.get("CGPAN"));
                            addNPADetailsStmt.setDouble(4,(Double)map.get("PRINCIPAL_OS"));
                            addNPADetailsStmt.setDouble(5,(Double)map.get("INTEREST_OS"));
                            
                            addNPADetailsStmt.executeQuery();
                            updateStatus=addNPADetailsStmt.getInt(1);
                            npaexception = addNPADetailsStmt.getString(6);
                            
                            if (updateStatus==Constants.FUNCTION_SUCCESS)
                            {
                                    addNPADetailsStatus = true;
                                    Log.log(Log.DEBUG,"GMDAO","insertNPADetails","insertnpadetails - SUCCESS");

                            }
                            else if (updateStatus==Constants.FUNCTION_FAILURE)
                            {
                                    connection.rollback();
                                    addNPADetailsStatus =false;
                                    addNPADetailsStmt.close();
                                    addNPADetailsStmt = null;
                                    Log.log(Log.ERROR,"GMDAO","insert NPA Details","Exception "+npaexception);
                            //    //System.out.println("Exception "+npaexception);
                                    throw new DatabaseException(npaexception);
                            }
                            
                        }
                        if(addNPADetailsStmt != null){
                        addNPADetailsStmt.close();
                        addNPADetailsStmt = null;
                        }
                        //addNPADetailsStmt.close();
                        //addNPADetailsStmt = null;
                        
                        //insert security detail
                        
                        Map securityAsOnSancDt = (Map)securityMap.get("securityAsOnSancDt");
                        Map securityAsOnNpaDt = (Map)securityMap.get("securityAsOnNpaDt");
                        Double networthAsOnSancDt = (Double)securityMap.get("networthAsOnSancDt");
                        Double networthAsOnNpaDt = (Double)securityMap.get("networthAsOnNpaDt");
                        String reasonForReductionAsOnNpaDt = (String)securityMap.get("reasonForReductionAsOnNpaDt");
                        
                        Vector securityIds = new Vector();
                        
                        Map securityAsOnSanc = new HashMap();
                        securityAsOnSanc.put("flag","SAN");
                        securityAsOnSanc.put("networth",networthAsOnSancDt);
                        securityAsOnSanc.put("reasonforreduction","NA");
                        
                        Map securityAsOnNpa = new HashMap();
                        securityAsOnNpa.put("flag","NPA");
                        securityAsOnNpa.put("networth",networthAsOnNpaDt);
                        securityAsOnNpa.put("reasonforreduction",reasonForReductionAsOnNpaDt);
                        
                        Vector vector = new Vector();
                        vector.add(securityAsOnSanc);
                        vector.add(securityAsOnNpa);
                        
                        
                        
                        
                        for(int i=0;i<vector.size();i++){
                        Map map = (Map)vector.get(i);
                        
                        addNPADetailsStmt = connection.prepareCall("{?=call funcUpdateNPASecDet(?,?,?,?,?,?)}") ;
                          
                        addNPADetailsStmt.registerOutParameter(1,Types.INTEGER); 
                        addNPADetailsStmt.registerOutParameter(6,Types.INTEGER);
                        addNPADetailsStmt.registerOutParameter(7,Types.VARCHAR);
                        addNPADetailsStmt.setString(2,npaId); 
                        addNPADetailsStmt.setString(3,(String)map.get("flag"));
                        
                        Double networth = (Double)map.get("networth");
                        
                        addNPADetailsStmt.setDouble(4,networth.doubleValue());
                        
                        addNPADetailsStmt.setString(5,(String)map.get("reasonforreduction"));
                        
                        addNPADetailsStmt.executeQuery();
                        updateStatus=addNPADetailsStmt.getInt(1);
                        npaexception = addNPADetailsStmt.getString(7);
                        
                        
                        if (updateStatus==Constants.FUNCTION_SUCCESS)
                        {
                                addNPADetailsStatus = true;
                                Log.log(Log.DEBUG,"GMDAO","insertNPADetails","insertnpadetails - SUCCESS");
                              securityIds.add(addNPADetailsStmt.getInt(6));
                                
                        }
                        else if (updateStatus==Constants.FUNCTION_FAILURE)
                        {
                                connection.rollback();
                                addNPADetailsStatus =false;
                                addNPADetailsStmt.close();
                                addNPADetailsStmt = null;
                                Log.log(Log.ERROR,"GMDAO","insert NPA Details","Exception "+npaexception);
                                throw new DatabaseException(npaexception);
                        }
                        }
                        if(addNPADetailsStmt != null){
                        addNPADetailsStmt.close();
                        addNPADetailsStmt = null;
                        }
                        //addNPADetailsStmt.close();
                        //addNPADetailsStmt = null;
                        
                        double landval = 0.0;
                        double bldgval = 0.0;
                        double macval = 0.0;
                        double movval = 0.0;
                        double currval = 0.0;
                        double othval = 0.0;
                        Integer id = (Integer)securityIds.get(0);
                        //           Integer id = 123;
                        
                        addNPADetailsStmt = connection.prepareCall("{?= call funcUpdateNPASecParticular(?,?,?,?,?)}");
                        addNPADetailsStmt.registerOutParameter(1,Types.INTEGER);
                        addNPADetailsStmt.registerOutParameter(6,Types.VARCHAR);
                        
                        if(id != null || !("".equals(id))){
                        addNPADetailsStmt.setInt(2,id.intValue());
                        }
                        addNPADetailsStmt.setString(3,"LAND");
                        String landstr = (String)securityAsOnSancDt.get("LAND");
                        Double land = 0.0;
                        if(!GenericValidator.isBlankOrNull(landstr)){
                        land = Double.parseDouble(landstr);
                        }
                        
                        if(land != null || !("".equals(land))){
                        landval = land.doubleValue();
                        }
                        addNPADetailsStmt.setDouble(4,landval);
                        addNPADetailsStmt.setString(5,"NA");
                        
                        addNPADetailsStmt.executeQuery();
                        updateStatus=addNPADetailsStmt.getInt(1);
                        npaexception = addNPADetailsStmt.getString(6);
                        
                        if (updateStatus==Constants.FUNCTION_SUCCESS)
                        {
                            addNPADetailsStatus = true;
                            Log.log(Log.DEBUG,"GMDAO","insertNPADetails","insertnpadetails - SUCCESS");
                        
                        }
                        else if (updateStatus==Constants.FUNCTION_FAILURE)
                        {
                            connection.rollback();
                            addNPADetailsStatus =false;
                            addNPADetailsStmt.close();
                            addNPADetailsStmt = null;
                            Log.log(Log.ERROR,"GMDAO","insert NPA Details","Exception "+npaexception);
                        //    //System.out.println("Exception "+npaexception);
                            throw new DatabaseException(npaexception);
                        }
                        addNPADetailsStmt.close();
                        addNPADetailsStmt = null;
                        
                        
                        addNPADetailsStmt = connection.prepareCall("{?= call funcUpdateNPASecParticular(?,?,?,?,?)}");
                        addNPADetailsStmt.registerOutParameter(1,Types.INTEGER);
                        addNPADetailsStmt.registerOutParameter(6,Types.VARCHAR);
                        
                        if(id != null || !("".equals(id))){
                        addNPADetailsStmt.setInt(2,id.intValue());
                        }
                        addNPADetailsStmt.setString(3,"BUILDING");
                        Double building = 0.0;
                        String buildingstr = (String)securityAsOnSancDt.get("BUILDING");
                        if(!GenericValidator.isBlankOrNull(buildingstr)){
                        building = Double.parseDouble(buildingstr);
                        }
                        
                        if(building != null || !("".equals(building))){
                        bldgval = building.doubleValue();
                        }
                        addNPADetailsStmt.setDouble(4,bldgval);
                        addNPADetailsStmt.setString(5,"NA");
                        
                        addNPADetailsStmt.executeQuery();
                        updateStatus=addNPADetailsStmt.getInt(1);
                        npaexception = addNPADetailsStmt.getString(6);
                        
                        if (updateStatus==Constants.FUNCTION_SUCCESS)
                        {
                            addNPADetailsStatus = true;
                            Log.log(Log.DEBUG,"GMDAO","insertNPADetails","insertnpadetails - SUCCESS");
                        
                        }
                        else if (updateStatus==Constants.FUNCTION_FAILURE)
                        {
                            connection.rollback();
                            addNPADetailsStatus =false;
                            addNPADetailsStmt.close();
                            addNPADetailsStmt = null;
                            Log.log(Log.ERROR,"GMDAO","insert NPA Details","Exception "+npaexception);
                        //    //System.out.println("Exception "+npaexception);
                            throw new DatabaseException(npaexception);
                        }
                        addNPADetailsStmt.close();
                        addNPADetailsStmt = null;
                        
                        
                        addNPADetailsStmt = connection.prepareCall("{?= call funcUpdateNPASecParticular(?,?,?,?,?)}");
                        addNPADetailsStmt.registerOutParameter(1,Types.INTEGER);
                        addNPADetailsStmt.registerOutParameter(6,Types.VARCHAR);
                        
                        if(id != null || !("".equals(id))){
                        addNPADetailsStmt.setInt(2,id.intValue());
                        }
                        addNPADetailsStmt.setString(3,"MACHINE");
                        Double machine = 0.0;
                        String machinestr = (String)securityAsOnSancDt.get("MACHINE");
                        if(!GenericValidator.isBlankOrNull(machinestr)){
                        machine = Double.parseDouble(machinestr);
                        }
                        
                        if(machine != null || !("".equals(machine))){
                        macval = machine.doubleValue();
                        }
                        addNPADetailsStmt.setDouble(4,macval);
                        addNPADetailsStmt.setString(5,"NA");
                        
                        addNPADetailsStmt.executeQuery();
                        updateStatus=addNPADetailsStmt.getInt(1);
                        npaexception = addNPADetailsStmt.getString(6);
                        
                        if (updateStatus==Constants.FUNCTION_SUCCESS)
                        {
                            addNPADetailsStatus = true;
                            Log.log(Log.DEBUG,"GMDAO","insertNPADetails","insertnpadetails - SUCCESS");
                        
                        }
                        else if (updateStatus==Constants.FUNCTION_FAILURE)
                        {
                            connection.rollback();
                            addNPADetailsStatus =false;
                            addNPADetailsStmt.close();
                            addNPADetailsStmt = null;
                            Log.log(Log.ERROR,"GMDAO","insert NPA Details","Exception "+npaexception);
                        //    //System.out.println("Exception "+npaexception);
                            throw new DatabaseException(npaexception);
                        }
                        addNPADetailsStmt.close();
                        addNPADetailsStmt = null;
                        
                        
                        addNPADetailsStmt = connection.prepareCall("{?= call funcUpdateNPASecParticular(?,?,?,?,?)}");
                        addNPADetailsStmt.registerOutParameter(1,Types.INTEGER);
                        addNPADetailsStmt.registerOutParameter(6,Types.VARCHAR);
                        
                        if(id != null || !("".equals(id))){
                        addNPADetailsStmt.setInt(2,id.intValue());
                        }
                        addNPADetailsStmt.setString(3,"OTHER FIXED MOVABLE ASSETS");
                        Double movableassets = 0.0;
                        String movableassetsstr = (String)securityAsOnSancDt.get("OTHER_FIXED_MOVABLE_ASSETS");
                        if(!GenericValidator.isBlankOrNull(movableassetsstr)){
                        movableassets = Double.parseDouble(movableassetsstr);
                        }
                        
                        if(movableassets != null || !("".equals(movableassets))){
                        movval = movableassets.doubleValue();
                        }
                        addNPADetailsStmt.setDouble(4,movval);
                        addNPADetailsStmt.setString(5,"NA");
                        
                        addNPADetailsStmt.executeQuery();
                        updateStatus=addNPADetailsStmt.getInt(1);
                        npaexception = addNPADetailsStmt.getString(6);
                        
                        if (updateStatus==Constants.FUNCTION_SUCCESS)
                        {
                            addNPADetailsStatus = true;
                            Log.log(Log.DEBUG,"GMDAO","insertNPADetails","insertnpadetails - SUCCESS");
                        
                        }
                        else if (updateStatus==Constants.FUNCTION_FAILURE)
                        {
                            connection.rollback();
                            addNPADetailsStatus =false;
                            addNPADetailsStmt.close();
                            addNPADetailsStmt = null;
                            Log.log(Log.ERROR,"GMDAO","insert NPA Details","Exception "+npaexception);
                        //    //System.out.println("Exception "+npaexception);
                            throw new DatabaseException(npaexception);
                        }
                        addNPADetailsStmt.close();
                        addNPADetailsStmt = null;
                        
                        
                        addNPADetailsStmt = connection.prepareCall("{?= call funcUpdateNPASecParticular(?,?,?,?,?)}");
                        addNPADetailsStmt.registerOutParameter(1,Types.INTEGER);
                        addNPADetailsStmt.registerOutParameter(6,Types.VARCHAR);
                        
                        if(id != null || !("".equals(id))){
                        addNPADetailsStmt.setInt(2,id.intValue());
                        }
                        addNPADetailsStmt.setString(3,"CUR_ASSETS");
                        Double currassets = 0.0;
                        String currassetsstr = (String)securityAsOnSancDt.get("CUR_ASSETS");
                        if(!GenericValidator.isBlankOrNull(currassetsstr)){
                        currassets = Double.parseDouble(currassetsstr);
                        }
                        
                        if(currassets != null || !("".equals(currassets))){
                        currval = currassets.doubleValue();
                        }
                        addNPADetailsStmt.setDouble(4,currval);
                        addNPADetailsStmt.setString(5,"NA");
                        
                        addNPADetailsStmt.executeQuery();
                        updateStatus=addNPADetailsStmt.getInt(1);
                        npaexception = addNPADetailsStmt.getString(6);
                        
                        if (updateStatus==Constants.FUNCTION_SUCCESS)
                        {
                            addNPADetailsStatus = true;
                            Log.log(Log.DEBUG,"GMDAO","insertNPADetails","insertnpadetails - SUCCESS");
                        
                        }
                        else if (updateStatus==Constants.FUNCTION_FAILURE)
                        {
                            connection.rollback();
                            addNPADetailsStatus =false;
                            addNPADetailsStmt.close();
                            addNPADetailsStmt = null;
                            Log.log(Log.ERROR,"GMDAO","insert NPA Details","Exception "+npaexception);
                        //    //System.out.println("Exception "+npaexception);
                            throw new DatabaseException(npaexception);
                        }
                        addNPADetailsStmt.close();
                        addNPADetailsStmt = null;
                        
                        
                        addNPADetailsStmt = connection.prepareCall("{?= call funcUpdateNPASecParticular(?,?,?,?,?)}");
                        addNPADetailsStmt.registerOutParameter(1,Types.INTEGER);
                        addNPADetailsStmt.registerOutParameter(6,Types.VARCHAR);
                        
                        if(id != null || !("".equals(id))){
                        addNPADetailsStmt.setInt(2,id.intValue());
                        }
                        addNPADetailsStmt.setString(3,"OTHERS");
                        Double others = 0.0;
                        String othersstr = (String)securityAsOnSancDt.get("OTHERS");
                        if(!GenericValidator.isBlankOrNull(othersstr)){
                        others = Double.parseDouble(othersstr);
                        }
                        
                        if(others != null || !("".equals(others))){
                        othval = others.doubleValue();
                        }
                        addNPADetailsStmt.setDouble(4,othval);
                        addNPADetailsStmt.setString(5,"NA");
                        
                        addNPADetailsStmt.executeQuery();
                        updateStatus=addNPADetailsStmt.getInt(1);
                        npaexception = addNPADetailsStmt.getString(6);
                        
                        if (updateStatus==Constants.FUNCTION_SUCCESS)
                        {
                            addNPADetailsStatus = true;
                            Log.log(Log.DEBUG,"GMDAO","insertNPADetails","insertnpadetails - SUCCESS");
                        
                        }
                        else if (updateStatus==Constants.FUNCTION_FAILURE)
                        {
                            connection.rollback();
                            addNPADetailsStatus =false;
                            addNPADetailsStmt.close();
                            addNPADetailsStmt = null;
                            Log.log(Log.ERROR,"GMDAO","insert NPA Details","Exception "+npaexception);
                        //    //System.out.println("Exception "+npaexception);
                            throw new DatabaseException(npaexception);
                        }
                        addNPADetailsStmt.close();
                        addNPADetailsStmt = null;
                        
                        
                        /*AS ON NPA DATE*/
                        id = (Integer)securityIds.get(1);
                        //      id = 321;
                        
                        addNPADetailsStmt = connection.prepareCall("{?= call funcUpdateNPASecParticular(?,?,?,?,?)}");
                        addNPADetailsStmt.registerOutParameter(1,Types.INTEGER);
                        addNPADetailsStmt.registerOutParameter(6,Types.VARCHAR);
                        
                        if(id != null || !("".equals(id))){
                        addNPADetailsStmt.setInt(2,id.intValue());
                        }
                        addNPADetailsStmt.setString(3,"LAND");
                        land = 0.0;
                        landstr = (String)securityAsOnNpaDt.get("LAND");
                        if(!GenericValidator.isBlankOrNull(landstr)){
                        land = Double.parseDouble(landstr);
                        }
                        
                        if(land != null || !("".equals(land))){
                        landval = land.doubleValue();
                        }
                        addNPADetailsStmt.setDouble(4,landval);
                        addNPADetailsStmt.setString(5,reasonForReductionAsOnNpaDt);
                        
                        addNPADetailsStmt.executeQuery();
                        updateStatus=addNPADetailsStmt.getInt(1);
                        npaexception = addNPADetailsStmt.getString(6);
                        
                        if (updateStatus==Constants.FUNCTION_SUCCESS)
                        {
                            addNPADetailsStatus = true;
                            Log.log(Log.DEBUG,"GMDAO","insertNPADetails","insertnpadetails - SUCCESS");
                        
                        }
                        else if (updateStatus==Constants.FUNCTION_FAILURE)
                        {
                            connection.rollback();
                            addNPADetailsStatus =false;
                            addNPADetailsStmt.close();
                            addNPADetailsStmt = null;
                            Log.log(Log.ERROR,"GMDAO","insert NPA Details","Exception "+npaexception);
                        //    //System.out.println("Exception "+npaexception);
                            throw new DatabaseException(npaexception);
                        }
                        addNPADetailsStmt.close();
                        addNPADetailsStmt = null;
                        
                        
                        addNPADetailsStmt = connection.prepareCall("{?= call funcUpdateNPASecParticular(?,?,?,?,?)}");
                        addNPADetailsStmt.registerOutParameter(1,Types.INTEGER);
                        addNPADetailsStmt.registerOutParameter(6,Types.VARCHAR);
                        
                        if(id != null || !("".equals(id))){
                        addNPADetailsStmt.setInt(2,id.intValue());
                        }
                        addNPADetailsStmt.setString(3,"BUILDING");
                        building = 0.0;
                        buildingstr = (String)securityAsOnNpaDt.get("BUILDING");
                        if(!GenericValidator.isBlankOrNull(buildingstr)){
                        building = Double.parseDouble(buildingstr);
                        }
                        
                        if(building != null || !("".equals(building))){
                        bldgval = building.doubleValue();
                        }
                        addNPADetailsStmt.setDouble(4,bldgval);
                        addNPADetailsStmt.setString(5,reasonForReductionAsOnNpaDt);
                        
                        addNPADetailsStmt.executeQuery();
                        updateStatus=addNPADetailsStmt.getInt(1);
                        npaexception = addNPADetailsStmt.getString(6);
                        
                        if (updateStatus==Constants.FUNCTION_SUCCESS)
                        {
                            addNPADetailsStatus = true;
                            Log.log(Log.DEBUG,"GMDAO","insertNPADetails","insertnpadetails - SUCCESS");
                        
                        }
                        else if (updateStatus==Constants.FUNCTION_FAILURE)
                        {
                            connection.rollback();
                            addNPADetailsStatus =false;
                            addNPADetailsStmt.close();
                            addNPADetailsStmt = null;
                            Log.log(Log.ERROR,"GMDAO","insert NPA Details","Exception "+npaexception);
                        //    //System.out.println("Exception "+npaexception);
                            throw new DatabaseException(npaexception);
                        }
                        addNPADetailsStmt.close();
                        addNPADetailsStmt = null;
                        
                        
                        addNPADetailsStmt = connection.prepareCall("{?= call funcUpdateNPASecParticular(?,?,?,?,?)}");
                        addNPADetailsStmt.registerOutParameter(1,Types.INTEGER);
                        addNPADetailsStmt.registerOutParameter(6,Types.VARCHAR);
                        
                        if(id != null || !("".equals(id))){
                        addNPADetailsStmt.setInt(2,id.intValue());
                        }
                        addNPADetailsStmt.setString(3,"MACHINE");
                        machine = 0.0;
                        machinestr = (String)securityAsOnNpaDt.get("MACHINE");
                        if(!GenericValidator.isBlankOrNull(machinestr)){
                        machine = Double.parseDouble(machinestr);
                        }
                        
                        if(machine != null || !("".equals(machine))){
                        macval = machine.doubleValue();
                        }
                        addNPADetailsStmt.setDouble(4,macval);
                        addNPADetailsStmt.setString(5,reasonForReductionAsOnNpaDt);
                        
                        addNPADetailsStmt.executeQuery();
                        updateStatus=addNPADetailsStmt.getInt(1);
                        npaexception = addNPADetailsStmt.getString(6);
                        
                        if (updateStatus==Constants.FUNCTION_SUCCESS)
                        {
                            addNPADetailsStatus = true;
                            Log.log(Log.DEBUG,"GMDAO","insertNPADetails","insertnpadetails - SUCCESS");
                        
                        }
                        else if (updateStatus==Constants.FUNCTION_FAILURE)
                        {
                            connection.rollback();
                            addNPADetailsStatus =false;
                            addNPADetailsStmt.close();
                            addNPADetailsStmt = null;
                            Log.log(Log.ERROR,"GMDAO","insert NPA Details","Exception "+npaexception);
                        //    //System.out.println("Exception "+npaexception);
                            throw new DatabaseException(npaexception);
                        }
                        addNPADetailsStmt.close();
                        addNPADetailsStmt = null;
                        
                        
                        addNPADetailsStmt = connection.prepareCall("{?= call funcUpdateNPASecParticular(?,?,?,?,?)}");
                        addNPADetailsStmt.registerOutParameter(1,Types.INTEGER);
                        addNPADetailsStmt.registerOutParameter(6,Types.VARCHAR);
                        
                        if(id != null || !("".equals(id))){
                        addNPADetailsStmt.setInt(2,id.intValue());
                        }
                        addNPADetailsStmt.setString(3,"OTHER FIXED MOVABLE ASSETS");
                        movableassets = 0.0;
                        movableassetsstr = (String)securityAsOnNpaDt.get("OTHER_FIXED_MOVABLE_ASSETS");
                        if(!GenericValidator.isBlankOrNull(movableassetsstr)){
                        movableassets = Double.parseDouble(movableassetsstr);
                        }
                        
                        if(movableassets != null || !("".equals(movableassets))){
                        movval = movableassets.doubleValue();
                        }
                        addNPADetailsStmt.setDouble(4,movval);
                        addNPADetailsStmt.setString(5,reasonForReductionAsOnNpaDt);
                        
                        addNPADetailsStmt.executeQuery();
                        updateStatus=addNPADetailsStmt.getInt(1);
                        npaexception = addNPADetailsStmt.getString(6);
                        
                        if (updateStatus==Constants.FUNCTION_SUCCESS)
                        {
                            addNPADetailsStatus = true;
                            Log.log(Log.DEBUG,"GMDAO","insertNPADetails","insertnpadetails - SUCCESS");
                        
                        }
                        else if (updateStatus==Constants.FUNCTION_FAILURE)
                        {
                            connection.rollback();
                            addNPADetailsStatus =false;
                            addNPADetailsStmt.close();
                            addNPADetailsStmt = null;
                            Log.log(Log.ERROR,"GMDAO","insert NPA Details","Exception "+npaexception);
                        //    //System.out.println("Exception "+npaexception);
                            throw new DatabaseException(npaexception);
                        }
                        addNPADetailsStmt.close();
                        addNPADetailsStmt = null;
                        
                        
                        addNPADetailsStmt = connection.prepareCall("{?= call funcUpdateNPASecParticular(?,?,?,?,?)}");
                        addNPADetailsStmt.registerOutParameter(1,Types.INTEGER);
                        addNPADetailsStmt.registerOutParameter(6,Types.VARCHAR);
                        
                        if(id != null || !("".equals(id))){
                        addNPADetailsStmt.setInt(2,id.intValue());
                        }
                        addNPADetailsStmt.setString(3,"CUR_ASSETS");
                        currassets = 0.0;
                        currassetsstr = (String)securityAsOnNpaDt.get("CUR_ASSETS");
                        if(!GenericValidator.isBlankOrNull(currassetsstr)){
                        currassets = Double.parseDouble(currassetsstr);
                        }
                        
                        if(currassets != null || !("".equals(currassets))){
                        currval = currassets.doubleValue();
                        }
                        addNPADetailsStmt.setDouble(4,currval);
                        addNPADetailsStmt.setString(5,reasonForReductionAsOnNpaDt);
                        
                        addNPADetailsStmt.executeQuery();
                        updateStatus=addNPADetailsStmt.getInt(1);
                        npaexception = addNPADetailsStmt.getString(6);
                        
                        if (updateStatus==Constants.FUNCTION_SUCCESS)
                        {
                            addNPADetailsStatus = true;
                            Log.log(Log.DEBUG,"GMDAO","insertNPADetails","insertnpadetails - SUCCESS");
                        
                        }
                        else if (updateStatus==Constants.FUNCTION_FAILURE)
                        {
                            connection.rollback();
                            addNPADetailsStatus =false;
                            addNPADetailsStmt.close();
                            addNPADetailsStmt = null;
                            Log.log(Log.ERROR,"GMDAO","insert NPA Details","Exception "+npaexception);
                        //    //System.out.println("Exception "+npaexception);
                            throw new DatabaseException(npaexception);
                        }
                        addNPADetailsStmt.close();
                        addNPADetailsStmt = null;
                        
                        
                        addNPADetailsStmt = connection.prepareCall("{?= call funcUpdateNPASecParticular(?,?,?,?,?)}");
                        addNPADetailsStmt.registerOutParameter(1,Types.INTEGER);
                        addNPADetailsStmt.registerOutParameter(6,Types.VARCHAR);
                        
                        if(id != null || !("".equals(id))){
                        addNPADetailsStmt.setInt(2,id.intValue());
                        }
                        addNPADetailsStmt.setString(3,"OTHERS");
                        others = 0.0;
                        othersstr = (String)securityAsOnNpaDt.get("OTHERS");
                        if(!GenericValidator.isBlankOrNull(othersstr)){
                        others = Double.parseDouble(othersstr);
                        }
                        
                        if(others != null || !("".equals(others))){
                        othval = others.doubleValue();
                        }
                        addNPADetailsStmt.setDouble(4,othval);
                        addNPADetailsStmt.setString(5,reasonForReductionAsOnNpaDt);
                        
                        addNPADetailsStmt.executeQuery();
                        updateStatus=addNPADetailsStmt.getInt(1);
                        npaexception = addNPADetailsStmt.getString(6);
                        
                        if (updateStatus==Constants.FUNCTION_SUCCESS)
                        {
                            addNPADetailsStatus = true;
                            Log.log(Log.DEBUG,"GMDAO","insertNPADetails","insertnpadetails - SUCCESS");
                        
                        }
                        else if (updateStatus==Constants.FUNCTION_FAILURE)
                        {
                            connection.rollback();
                            addNPADetailsStatus =false;
                            addNPADetailsStmt.close();
                            addNPADetailsStmt = null;
                            Log.log(Log.ERROR,"GMDAO","insert NPA Details","Exception "+npaexception);
                        //    //System.out.println("Exception "+npaexception);
                            throw new DatabaseException(npaexception);
                        }
                        addNPADetailsStmt.close();
                        addNPADetailsStmt = null;
                        
                        
                        
                        
                      
                            
                     connection.commit();  
                    }catch (SQLException exception)
                    {
               try {
                   connection.rollback();
               } catch (SQLException e) {
                   // TODO
               }
               Log.logException(exception);
                            throw new DatabaseException(exception.getMessage());
                    }finally
                    {
                                    DBConnection.freeConnection(connection);
                    }
                    Log.log(Log.INFO,"GMDAO","updateNPADetails","Exited");
                    return addNPADetailsStatus;
       }

  public String insertNpaForUpload(NPADetails npaDetails)
    throws DatabaseException
  {
    Log.log(4, "GMDAO", "insertNpaForUpload", "Entered");
    Connection connection = DBConnection.getConnection();
    CallableStatement insertNpaStmt = null;
    ResultSet resultSet = null;
    int updateStatus = 0;
    java.util.Date utilDate = null;
    java.sql.Date sqlDate = null;

    boolean addNPADetailsStatus = false;
    String npaId = null;
    try
    {
      sqlDate = new java.sql.Date(0L);
      utilDate = new java.util.Date();

      String npaexception = "";

      sqlDate = new java.sql.Date(0L);
      utilDate = new java.util.Date();

      insertNpaStmt = connection.prepareCall("{?=call funcInsertNPADtl(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)}");

      insertNpaStmt.registerOutParameter(1, 4);

      insertNpaStmt.registerOutParameter(23, 12);

      insertNpaStmt.setString(2, npaDetails.getCgbid());
      Log.log(5, "GMDAO", "insertNpaForUpload", "2----------------" + npaDetails.getCgbid());

      SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
      String formatedDate = null;
      utilDate = npaDetails.getNpaDate();
      if (utilDate != null)
      {
        sqlDate = new java.sql.Date(utilDate.getTime());

        insertNpaStmt.setDate(3, sqlDate);
      }
      else {
        insertNpaStmt.setDate(3, null);
      }
      Log.log(5, "GMDAO", "insertNpaForUpload", "3----------------" + sqlDate);

      insertNpaStmt.setString(4, npaDetails.getWhetherNPAReported());
      Log.log(5, "GMDAO", "insertNpaForUpload", "4----------------" + npaDetails.getWhetherNPAReported());
      insertNpaStmt.setString(5, null);
      Log.log(5, "GMDAO", "insertNpaForUpload", "5----------------");

      utilDate = npaDetails.getReportingDate();
      if (utilDate != null)
      {
        sqlDate = new java.sql.Date(utilDate.getTime());

        insertNpaStmt.setDate(6, sqlDate);
      }
      else {
        insertNpaStmt.setDate(6, null);
      }
      Log.log(5, "GMDAO", "insertNpaForUpload", "6----------------" + sqlDate);

      insertNpaStmt.setString(7, npaDetails.getReference());
      Log.log(5, "GMDAO", "insertNpaForUpload", "7----------------" + npaDetails.getReference());
      insertNpaStmt.setDouble(8, npaDetails.getOsAmtOnNPA());
      Log.log(5, "GMDAO", "insertNpaForUpload", "8----------------" + npaDetails.getOsAmtOnNPA());
      insertNpaStmt.setString(9, npaDetails.getNpaReason());
      Log.log(5, "GMDAO", "insertNpaForUpload", "9----------------" + npaDetails.getNpaReason());

      insertNpaStmt.setString(10, npaDetails.getEffortsTaken());
      Log.log(5, "GMDAO", "insertNpaForUpload", "10----------------");
      insertNpaStmt.setString(11, npaDetails.getIsRecoveryInitiated());
      Log.log(5, "GMDAO", "insertNpaForUpload", "11----------------" + npaDetails.getIsRecoveryInitiated());

      insertNpaStmt.setInt(12, npaDetails.getNoOfActions());
      Log.log(5, "GMDAO", "insertNpaForUpload", "12----------------" + npaDetails.getNoOfActions());
      utilDate = npaDetails.getEffortsConclusionDate();
      if (utilDate != null)
      {
        sqlDate = new java.sql.Date(utilDate.getTime());

        insertNpaStmt.setDate(13, sqlDate);
      }
      else {
        insertNpaStmt.setDate(13, null);
      }
      Log.log(5, "GMDAO", "insertNpaForUpload", "13----------------" + sqlDate);

      insertNpaStmt.setString(15, npaDetails.getDetailsOfFinAssistance());
      Log.log(5, "GMDAO", "insertNpaForUpload", "14----------------" + npaDetails.getDetailsOfFinAssistance());
      insertNpaStmt.setString(14, npaDetails.getMliCommentOnFinPosition());
      Log.log(5, "GMDAO", "insertNpaForUpload", "15----------------" + npaDetails.getMliCommentOnFinPosition());

      insertNpaStmt.setString(16, npaDetails.getCreditSupport());
      Log.log(5, "GMDAO", "insertNpaForUpload", "16----------------" + npaDetails.getCreditSupport());

      insertNpaStmt.setString(17, npaDetails.getBankFacilityDetail());
      Log.log(5, "GMDAO", "insertNpaForUpload", "17----------------" + npaDetails.getBankFacilityDetail());

      insertNpaStmt.setString(18, npaDetails.getWillfulDefaulter());
      Log.log(5, "GMDAO", "insertNpaForUpload", "18----------------" + npaDetails.getWillfulDefaulter());

      insertNpaStmt.setString(19, npaDetails.getPlaceUnderWatchList());
      Log.log(5, "GMDAO", "insertNpaForUpload", "19----------------" + npaDetails.getPlaceUnderWatchList());

      insertNpaStmt.setString(20, null);
      Log.log(5, "GMDAO", "insertNpaForUpload", "20----------------");

      insertNpaStmt.setString(21, npaDetails.getRemarksOnNpa());
      Log.log(5, "GMDAO", "insertNpaForUpload", "21----------------" + npaDetails.getRemarksOnNpa());

      insertNpaStmt.registerOutParameter(22, 12);
      Log.log(5, "GMDAO", "insertNpaForUpload", "22----------------");
      insertNpaStmt.executeQuery();

      updateStatus = insertNpaStmt.getInt(1);
      npaexception = insertNpaStmt.getString(23);

      npaId = insertNpaStmt.getString(22);
      Log.log(5, "GMDAO", "insertNpaForUpload", "22----------------" + npaId);

      if (updateStatus == 0) {
        addNPADetailsStatus = true;
        Log.log(5, "GMDAO", "insertNpaForUpload", "insertNpaForUpload - SUCCESS");
      }
      else if (updateStatus == 1)
      {
        connection.rollback();
        addNPADetailsStatus = false;
        insertNpaStmt.close();
        insertNpaStmt = null;
        Log.log(2, "GMDAO", "insertNPADetails ForUpload ", "Exception " + npaexception);
        throw new DatabaseException(npaexception);
      }

      npaDetails.setNpaId(npaId);
      insertNpaStmt.close();
      insertNpaStmt = null;

      connection.commit();
    }
    catch (Exception exception)
    {
      try {
        connection.rollback();
      }
      catch (SQLException localSQLException) {
      }
      throw new DatabaseException(exception.getMessage());
    } finally {
      DBConnection.freeConnection(connection);
    }
    Log.log(4, "GMDAO", "insertNPADetailsForUpload ", "Exited");

    return npaId;
  }

  public NPADetails updateNpaForUpload(NPADetails npaDetails)
    throws DatabaseException
  {
    Log.log(4, "GMDAO", "insertNpaForUpload", "Entered");
    Connection connection = DBConnection.getConnection();
    CallableStatement updateNPAForUploadStmt = null;
    ResultSet resultSet = null;
    int updateStatus = 0;
    java.util.Date utilDate = null;
    java.sql.Date sqlDate = null;

    boolean addNPADetailsStatus = false;
    String npaId = null;
    String npaexception = null;
    try
    {
      sqlDate = new java.sql.Date(0L);
      utilDate = new java.util.Date();

      updateNPAForUploadStmt = connection.prepareCall("{?=call funcUpdateNPADtl(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)}");

      updateNPAForUploadStmt.registerOutParameter(1, 4);

      updateNPAForUploadStmt.registerOutParameter(25, 12);

      updateNPAForUploadStmt.setString(2, npaDetails.getCgbid());
      Log.log(5, "GMDAO", "updateNPAForUpload", "2 Borrrower Id : " + npaDetails.getCgbid());

      SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");

      utilDate = npaDetails.getNpaDate();
      if (utilDate != null)
      {
        sqlDate = new java.sql.Date(utilDate.getTime());

        updateNPAForUploadStmt.setDate(3, sqlDate);
      } else {
        updateNPAForUploadStmt.setDate(3, null);
      }

      Log.log(5, "GMDAO", "updateNPAForUpload", "3 Npa Date: " + sqlDate);

      updateNPAForUploadStmt.setString(4, npaDetails.getWhetherNPAReported());
      Log.log(5, "GMDAO", "updateNPAForUpload", "4 Whether npa reported  : " + npaDetails.getWhetherNPAReported());
      updateNPAForUploadStmt.setString(5, null);
      Log.log(5, "GMDAO", "updateNPAForUpload", "5 mode of reporting ");

      String formatedDate = null;

      utilDate = npaDetails.getReportingDate();
      if (utilDate != null)
      {
        sqlDate = new java.sql.Date(utilDate.getTime());

        updateNPAForUploadStmt.setDate(6, sqlDate);
      } else {
        updateNPAForUploadStmt.setDate(6, null);
      }
      Log.log(5, "GMDAO", "updateNPAForUpload", "6 ReportingDate : " + sqlDate);

      updateNPAForUploadStmt.setString(7, npaDetails.getReference());
      Log.log(5, "GMDAO", "updateNPAForUpload", "7 Reference : " + npaDetails.getReference());

      updateNPAForUploadStmt.setDouble(8, npaDetails.getOsAmtOnNPA());
      Log.log(5, "GMDAO", "updateNPAForUpload", "8 Os Amt on NPA : " + npaDetails.getOsAmtOnNPA());

      updateNPAForUploadStmt.setString(9, npaDetails.getNpaReason());
      Log.log(5, "GMDAO", "updateNPAForUpload", "9 Npa reason : " + npaDetails.getNpaReason());

      updateNPAForUploadStmt.setString(10, null);
      Log.log(5, "GMDAO", "updateNPAForUpload", "10 Remarks ");

      updateNPAForUploadStmt.setString(11, npaDetails.getIsRecoveryInitiated());
      Log.log(5, "GMDAO", "updateNPAForUpload", "11 IsRecoveryInitiated : " + npaDetails.getIsRecoveryInitiated());

      updateNPAForUploadStmt.setInt(12, npaDetails.getNoOfActions());
      Log.log(5, "GMDAO", "updateNPAForUpload", "12 NoOfActions : " + npaDetails.getNoOfActions());

      utilDate = npaDetails.getEffortsConclusionDate();
      if (utilDate != null)
      {
        sqlDate = new java.sql.Date(utilDate.getTime());

        updateNPAForUploadStmt.setDate(13, sqlDate);
      } else {
        updateNPAForUploadStmt.setDate(13, null);
      }
      Log.log(5, "GMDAO", "updateNPAForUpload", "13 EffortsConclusionDate : " + sqlDate);

      updateNPAForUploadStmt.setString(15, npaDetails.getDetailsOfFinAssistance());
      Log.log(5, "GMDAO", "updateNPAForUpload", "14 DetailsOfFinAssistance : " + npaDetails.getDetailsOfFinAssistance());

      updateNPAForUploadStmt.setString(14, npaDetails.getMliCommentOnFinPosition());
      Log.log(5, "GMDAO", "updateNPAForUpload", "15 MliCommentOnFinPosition : " + npaDetails.getMliCommentOnFinPosition());

      updateNPAForUploadStmt.setString(16, npaDetails.getCreditSupport());
      Log.log(5, "GMDAO", "updateNPAForUpload", "16 CreditSupport : " + npaDetails.getCreditSupport());

      updateNPAForUploadStmt.setString(17, npaDetails.getBankFacilityDetail());
      Log.log(5, "GMDAO", "updateNPAForUpload", "17 BankFacilityDetail : " + npaDetails.getBankFacilityDetail());

      updateNPAForUploadStmt.setString(18, npaDetails.getWillfulDefaulter());
      Log.log(5, "GMDAO", "updateNPAForUpload", "18 WillfulDefaulter : " + npaDetails.getWillfulDefaulter());

      updateNPAForUploadStmt.setString(19, npaDetails.getPlaceUnderWatchList());
      Log.log(5, "GMDAO", "updateNPAForUpload", "19 PlaceUnderWatchList : " + npaDetails.getPlaceUnderWatchList());

      updateNPAForUploadStmt.setString(20, null);
      Log.log(5, "GMDAO", "updateNPAForUpload", "20 monitoring details = monitor");

      updateNPAForUploadStmt.setString(21, npaDetails.getRemarksOnNpa());
      Log.log(5, "GMDAO", "updateNPAForUpload", "21 RemarksOnNpa : " + npaDetails.getRemarksOnNpa());

      updateNPAForUploadStmt.setNull(22, 91);
      updateNPAForUploadStmt.setNull(23, 12);
      updateNPAForUploadStmt.setNull(24, 91);

      updateNPAForUploadStmt.registerOutParameter(25, 12);

      updateNPAForUploadStmt.executeQuery();

      updateStatus = updateNPAForUploadStmt.getInt(1);
      npaexception = updateNPAForUploadStmt.getString(25);

      if (updateStatus == 0) {
        addNPADetailsStatus = true;
        Log.log(5, "GMDAO", "updateNPAForUpload", "SUCCESS SP ");
      }
      else if (updateStatus == 1)
      {
        connection.rollback();
        addNPADetailsStatus = false;
        updateNPAForUploadStmt.close();
        updateNPAForUploadStmt = null;
        Log.log(2, "GMDAO", "updateNPAForUpload", "Exception " + npaexception);
        throw new DatabaseException(npaexception);
      }

      updateNPAForUploadStmt.close();
      updateNPAForUploadStmt = null;

      connection.commit();
    }
    catch (Exception exception) {
      try {
        connection.rollback();
      }
      catch (SQLException localSQLException) {
      }
      throw new DatabaseException(exception.getMessage());
    } finally {
      DBConnection.freeConnection(connection);
    }
    Log.log(4, "GMDAO", "updateNPAForUpload", "Exited");

    return npaDetails;
  }

  public void insertRecAxnForUpload(RecoveryProcedure newRecoveryProcedure)
    throws DatabaseException
  {
    Log.log(4, "GMDAO", "insertRecAxnForUpload", "Entered");
    Connection connection = DBConnection.getConnection();
    CallableStatement insertRecAxnForUploadStmt = null;

    java.util.Date utilDate = null;
    java.sql.Date sqlDate = null;
    int updateStatus = 0;
    SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
    try
    {
      sqlDate = new java.sql.Date(0L);
      utilDate = new java.util.Date();

      insertRecAxnForUploadStmt = connection.prepareCall("{?=call funcInsertRecoveryAxnDtl(?,?,?,?,?,?)}");

      insertRecAxnForUploadStmt.registerOutParameter(1, 4);

      insertRecAxnForUploadStmt.registerOutParameter(7, 12);

      Log.log(5, "GMDAO", "insertRecAxnForUploadStmt", "insert Rec Action : Action Type " + newRecoveryProcedure.getActionType());
      insertRecAxnForUploadStmt.setString(2, newRecoveryProcedure.getActionType());

      insertRecAxnForUploadStmt.setString(3, newRecoveryProcedure.getNpaId());
      Log.log(5, "GMDAO", "insertRecAxnForUploadStmt", "insert Rec Action : npa Id  " + newRecoveryProcedure.getNpaId());

      insertRecAxnForUploadStmt.setString(4, newRecoveryProcedure.getActionDetails());
      Log.log(5, "GMDAO", "insertRecAxnForUploadStmt", "insert Rec Action : action details " + newRecoveryProcedure.getActionDetails());
      utilDate = newRecoveryProcedure.getActionDate();

      Log.log(5, "GMDAO", "insertRecAxnForUploadStmt", "insert Rec Action : Date " + newRecoveryProcedure.getActionDate());
      sqlDate = new java.sql.Date(utilDate.getTime());

      insertRecAxnForUploadStmt.setDate(5, sqlDate);

      insertRecAxnForUploadStmt.setString(6, newRecoveryProcedure.getAttachmentName());
      Log.log(5, "GMDAO", "insertRecAxnForUploadStmt", "insert Rec Action : file " + newRecoveryProcedure.getAttachmentName());

      insertRecAxnForUploadStmt.executeQuery();

      updateStatus = insertRecAxnForUploadStmt.getInt(1);
      String recexception = insertRecAxnForUploadStmt.getString(7);

      if (updateStatus == 0) {
        Log.log(5, "GMDAO", "insertRecAxnForUploadStmt", "insertrecoveryaction-SUCCESS SP ");
      }
      else if (updateStatus == 1)
      {
        connection.rollback();
        insertRecAxnForUploadStmt.close();
        insertRecAxnForUploadStmt = null;
        Log.log(2, "GMDAO", "insertRecAxnForUploadStmt -Rec Procedure", "Exception " + recexception);
        throw new DatabaseException(recexception);
      }
      insertRecAxnForUploadStmt.close();
      insertRecAxnForUploadStmt = null;
      connection.commit();
    }
    catch (Exception exception)
    {
      try {
        connection.rollback();
      }
      catch (SQLException localSQLException) {
      }
      throw new DatabaseException(exception.getMessage());
    } finally {
      DBConnection.freeConnection(connection);
    }
    Log.log(4, "GMDAO", "insertRecAxnForUpload", "Exited");
  }

  public void updateRecAxnForUpload(RecoveryProcedure modifiedRecoveryProcedure)
    throws DatabaseException
  {
    Log.log(4, "GMDAO", "updateRecAxnForUpload", "Entered");
    Connection connection = DBConnection.getConnection();
    CallableStatement uploadRecAxnForUploadStmt = null;
    ResultSet resultSet = null;
    SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
    java.util.Date utilDate = null;
    java.sql.Date sqlDate = null;
    int updateStatus = 0;
    try
    {
      sqlDate = new java.sql.Date(0L);
      utilDate = new java.util.Date();

      uploadRecAxnForUploadStmt = connection.prepareCall("{?=call funcUpdRecAXnDtl(?,?,?,?,?,?)}");

      uploadRecAxnForUploadStmt.registerOutParameter(1, 4);

      uploadRecAxnForUploadStmt.registerOutParameter(7, 12);

      uploadRecAxnForUploadStmt.setString(2, modifiedRecoveryProcedure.getRadId());
      Log.log(5, "GMDAO", "updateRecAxnForUpload", " Rad Id  " + modifiedRecoveryProcedure.getRadId());

      Log.log(5, "GMDAO", "updateRecAxnForUpload", " Action Type " + modifiedRecoveryProcedure.getActionType());
      uploadRecAxnForUploadStmt.setString(3, modifiedRecoveryProcedure.getActionType());

      uploadRecAxnForUploadStmt.setString(4, modifiedRecoveryProcedure.getActionDetails());
      Log.log(5, "GMDAO", "updateRecAxnForUpload", " action details " + modifiedRecoveryProcedure.getActionDetails());

      utilDate = modifiedRecoveryProcedure.getActionDate();
      Log.log(5, "GMDAO", "updateRecAxnForUpload", " Date " + modifiedRecoveryProcedure.getActionDate());
      if (utilDate != null)
      {
        sqlDate = new java.sql.Date(utilDate.getTime());

        uploadRecAxnForUploadStmt.setDate(5, sqlDate);
      } else {
        uploadRecAxnForUploadStmt.setDate(5, null);
      }
      uploadRecAxnForUploadStmt.setString(6, modifiedRecoveryProcedure.getAttachmentName());
      Log.log(5, "GMDAO", "updateRecAxnForUpload", " file " + modifiedRecoveryProcedure.getAttachmentName());

      uploadRecAxnForUploadStmt.executeQuery();

      updateStatus = uploadRecAxnForUploadStmt.getInt(1);
      String recexception = uploadRecAxnForUploadStmt.getString(7);

      if (updateStatus == 0) {
        Log.log(5, "GMDAO", "updateRecAxnForUpload", "updateRecoveryAction SP -SUCCESS");
      }
      else if (updateStatus == 1)
      {
        connection.rollback();
        uploadRecAxnForUploadStmt.close();
        uploadRecAxnForUploadStmt = null;
        Log.log(2, "GMDAO", "updateRecAxnForUpload ", "Exception " + recexception);
        throw new DatabaseException(recexception);
      }
      uploadRecAxnForUploadStmt.close();
      uploadRecAxnForUploadStmt = null;
      connection.commit();
    }
    catch (Exception exception)
    {
      try {
        connection.rollback();
      }
      catch (SQLException localSQLException) {
      }
      throw new DatabaseException(exception.getMessage());
    } finally {
      DBConnection.freeConnection(connection);
    }
    Log.log(4, "GMDAO", "updateRecAxnForUpload", "Exited");
  }

  public void updateLegalForUpload(LegalSuitDetail legalSuitDetail)
    throws DatabaseException
  {
    Log.log(4, "GMDAO", "updateLegalForUpload", "Entered");
    Connection connection = DBConnection.getConnection();
    CallableStatement updateLegalForUploadStmt = null;

    SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
    java.util.Date utilDate = null;
    java.sql.Date sqlDate = null;
    int updateStatus = 0;
    try
    {
      sqlDate = new java.sql.Date(0L);
      utilDate = new java.util.Date();

      updateLegalForUploadStmt = connection.prepareCall("{?=call funcUpdateLegalDtl(?,?,?,?,?,?,?,?,?,?)}");

      updateLegalForUploadStmt.registerOutParameter(1, 4);

      updateLegalForUploadStmt.registerOutParameter(11, 12);

      updateLegalForUploadStmt.setString(2, legalSuitDetail.getLegalSuiteNo());
      Log.log(5, "GMDAO", "updateLegalForUpload", " Suit No : " + legalSuitDetail.getLegalSuiteNo());

      updateLegalForUploadStmt.setString(3, legalSuitDetail.getNpaId());
      Log.log(5, "GMDAO", "updateLegalForUpload", " NPA ID : " + legalSuitDetail.getNpaId());

      updateLegalForUploadStmt.setString(4, legalSuitDetail.getCourtName());
      Log.log(5, "GMDAO", "updateLegalForUpload", " Court name : " + legalSuitDetail.getCourtName());

      updateLegalForUploadStmt.setString(5, legalSuitDetail.getForumName());
      Log.log(5, "GMDAO", "updateLegalForUpload", " Forum name : " + legalSuitDetail.getForumName());

      updateLegalForUploadStmt.setString(6, legalSuitDetail.getLocation());
      Log.log(5, "GMDAO", "updateLegalForUpload", " Loaction : " + legalSuitDetail.getLocation());

      utilDate = legalSuitDetail.getDtOfFilingLegalSuit();
      sqlDate = new java.sql.Date(utilDate.getTime());

      updateLegalForUploadStmt.setDate(7, sqlDate);
      Log.log(5, "GMDAO", "updateLegalForUpload", " Legald date : " + legalSuitDetail.getDtOfFilingLegalSuit());

      updateLegalForUploadStmt.setDouble(8, legalSuitDetail.getAmountClaimed());
      Log.log(5, "GMDAO", "updateLegalForUpload", " amt claimed : " + legalSuitDetail.getAmountClaimed());

      updateLegalForUploadStmt.setString(9, legalSuitDetail.getCurrentStatus());
      Log.log(5, "GMDAO", "updateLegalForUpload", " current status : " + legalSuitDetail.getCurrentStatus());

      updateLegalForUploadStmt.setString(10, legalSuitDetail.getRecoveryProceedingsConcluded());
      Log.log(5, "GMDAO", "updateLegalForUpload", " Rec Proceedings Concluded: " + legalSuitDetail.getRecoveryProceedingsConcluded());

      updateLegalForUploadStmt.executeQuery();
      updateStatus = updateLegalForUploadStmt.getInt(1);
      String legalexception = updateLegalForUploadStmt.getString(11);

      if (updateStatus == 0) {
        Log.log(5, "GMDAO", "updateLegalForUpload", "updatelegaldetails SP-SUCCESS");
      }
      else if (updateStatus == 1)
      {
        connection.rollback();
        updateLegalForUploadStmt.close();
        updateLegalForUploadStmt = null;
        Log.log(2, "GMDAO", "update LegaldTL ", "Exception " + legalexception);
        throw new DatabaseException(legalexception);
      }
      updateLegalForUploadStmt.close();
      updateLegalForUploadStmt = null;

      connection.commit();
    }
    catch (Exception exception)
    {
      try
      {
        connection.rollback();
      }
      catch (SQLException localSQLException) {
      }
      throw new DatabaseException(exception.getMessage());
    } finally {
      DBConnection.freeConnection(connection);
    }
    Log.log(4, "GMDAO", "updateLegalForUpload", "Exited");
  }

  public void insertLegalForUpload(LegalSuitDetail legalSuitDetail)
    throws DatabaseException
  {
    Log.log(4, "GMDAO", "insertLegalForUpload", "Entered");
    Connection connection = DBConnection.getConnection();
    CallableStatement insertLegalForUploadStmt = null;
    SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
    java.util.Date utilDate = null;
    java.sql.Date sqlDate = null;
    int updateStatus = 0;
    try
    {
      sqlDate = new java.sql.Date(0L);
      utilDate = new java.util.Date();

      insertLegalForUploadStmt = connection.prepareCall("{?=call funcInsertLegalDtl(?,?,?,?,?,?,?,?,?,?)}");

      insertLegalForUploadStmt.registerOutParameter(1, 4);

      insertLegalForUploadStmt.registerOutParameter(11, 12);

      insertLegalForUploadStmt.setString(2, legalSuitDetail.getLegalSuiteNo());
      Log.log(5, "GMDAO", "insertLegalForUpload", " Suit No : " + legalSuitDetail.getLegalSuiteNo());

      insertLegalForUploadStmt.setString(3, legalSuitDetail.getNpaId());
      Log.log(5, "GMDAO", "insertLegalForUpload", " NPA ID : " + legalSuitDetail.getNpaId());

      insertLegalForUploadStmt.setString(4, legalSuitDetail.getCourtName());
      Log.log(5, "GMDAO", "insertLegalForUpload", " Court name : " + legalSuitDetail.getCourtName());

      insertLegalForUploadStmt.setString(5, legalSuitDetail.getForumName());
      Log.log(5, "GMDAO", "insertLegalForUpload", " Forum name : " + legalSuitDetail.getForumName());

      insertLegalForUploadStmt.setString(6, legalSuitDetail.getLocation());
      Log.log(5, "GMDAO", "insertLegalForUpload", " Loaction : " + legalSuitDetail.getLocation());

      utilDate = legalSuitDetail.getDtOfFilingLegalSuit();

      sqlDate = new java.sql.Date(utilDate.getTime());

      insertLegalForUploadStmt.setDate(7, sqlDate);
      Log.log(5, "GMDAO", "insertLegalForUpload", " Legald date : " + legalSuitDetail.getDtOfFilingLegalSuit());

      insertLegalForUploadStmt.setDouble(8, legalSuitDetail.getAmountClaimed());
      Log.log(5, "GMDAO", "insertLegalForUpload", " amt claimed : " + legalSuitDetail.getAmountClaimed());

      insertLegalForUploadStmt.setString(9, legalSuitDetail.getCurrentStatus());
      Log.log(5, "GMDAO", "insertLegalForUpload", " current status : " + legalSuitDetail.getCurrentStatus());

      insertLegalForUploadStmt.setString(10, legalSuitDetail.getRecoveryProceedingsConcluded());
      Log.log(5, "GMDAO", "insertLegalForUpload", " Rec Proceedings Concluded: " + legalSuitDetail.getRecoveryProceedingsConcluded());

      insertLegalForUploadStmt.executeQuery();
      updateStatus = insertLegalForUploadStmt.getInt(1);
      String legalexception = insertLegalForUploadStmt.getString(11);

      if (updateStatus == 0) {
        Log.log(5, "GMDAO", "insertLegalForUpload", "insertlegaldetails-SUCCESS");
      }
      else if (updateStatus == 1)
      {
        connection.rollback();
        insertLegalForUploadStmt.close();
        insertLegalForUploadStmt = null;
        Log.log(2, "GMDAO", "insert NPA Details Legal dTL  ", "Exception " + legalexception);
        throw new DatabaseException(legalexception);
      }
      insertLegalForUploadStmt.close();
      insertLegalForUploadStmt = null;
      connection.commit();
    }
    catch (Exception exception) {
      try {
        connection.rollback();
      }
      catch (SQLException localSQLException) {
      }
      throw new DatabaseException(exception.getMessage());
    } finally {
      DBConnection.freeConnection(connection);
    }
    Log.log(4, "GMDAO", "insertLegalForUpload", "Exited");
  }

  public boolean closure(String cgpan, String reason, String remarks, String userId)
    throws DatabaseException
  {
    Log.log(4, "GMDAO", "Closure ", "Entered");

    Log.log(5, "GMDAO", "Closure ", "reason" + reason);
    Log.log(5, "GMDAO", "Closure ", "cgpan" + cgpan);
    Log.log(5, "GMDAO", "Closure ", "remarks" + remarks);
    Log.log(5, "GMDAO", "Closure ", "userid" + userId);

    Connection connection = DBConnection.getConnection(false);
    boolean closureStatus = false;
    CallableStatement closureStmt = null;
    try
    {
      String exception = "";

      closureStmt = connection.prepareCall("{?=call funcCloseApplication (?,?,?,?,?)}");

      closureStmt.registerOutParameter(1, 4);
      closureStmt.setString(2, cgpan);
      closureStmt.setString(3, userId);
      closureStmt.setString(4, reason);
      closureStmt.setString(5, remarks);
      closureStmt.registerOutParameter(6, 12);

      closureStmt.executeQuery();

      exception = closureStmt.getString(6);

      int errorcode = closureStmt.getInt(1);

      if (errorcode == 0) {
        closureStatus = true;
        Log.log(5, "GMDAO", "ClosureSP ", "SUCCESS");
      }
      if (errorcode == 1)
      {
        connection.rollback();
        closureStmt.close();
        closureStmt = null;
        Log.log(2, "GMDAO", "Closure", "Exception " + exception);
        throw new DatabaseException(exception);
      }
      closureStmt.close();
      closureStmt = null;
      connection.commit();
    } catch (Exception exception) {
      Log.logException(exception);
      try
      {
        connection.rollback();
      }
      catch (SQLException localSQLException) {
      }
      throw new DatabaseException(exception.getMessage());
    }
    finally {
      DBConnection.freeConnection(connection);
    }
    Log.log(4, "GMDAO", "Closure ", "Exited");

    return closureStatus;
  }

  public TreeMap getMemberIdCgpansForClosure(String feeType)
    throws DatabaseException
  {
    Log.log(4, "GMDAO", "getMemberIdCgpansForClosure", "Entered");

    Log.log(5, "GMDAO", "getMemberIdCgpansForClosure", "fee Type " + feeType);

    ResultSet resultSet = null;

    Connection connection = DBConnection.getConnection(false);
    CallableStatement memberIdCgpansClosureStmt = null;

    String memberId = null;
    int noOfCgpans = 0;

    TreeMap memberIdCgpans = new TreeMap();
    try
    {
      String exception = "";

      memberIdCgpansClosureStmt = connection.prepareCall("{?=call packGetNotPaidNoOfCgpans.funcGetNotPaidNoOfCgpans(?,?,?)}");

      memberIdCgpansClosureStmt.registerOutParameter(1, 4);

      memberIdCgpansClosureStmt.setString(2, feeType);

      memberIdCgpansClosureStmt.registerOutParameter(3, -10);

      memberIdCgpansClosureStmt.registerOutParameter(4, 12);

      memberIdCgpansClosureStmt.executeQuery();

      Log.log(5, "GMDAO", "getMemberIdCgpansForClosure", "Query Executed");

      exception = memberIdCgpansClosureStmt.getString(4);

      int errorcode = memberIdCgpansClosureStmt.getInt(1);

      if (errorcode == 0)
      {
        Log.log(5, "GMDAO", "getMemberIdCgpansForClosure", "SUCCESS");
      }

      if (errorcode == 1)
      {
        connection.rollback();
        memberIdCgpansClosureStmt.close();
        memberIdCgpansClosureStmt = null;
        Log.log(2, "GMDAO", "getMemberIdCgpansForClosure", "Exception " + exception);

        throw new DatabaseException(exception);
      }

      resultSet = (ResultSet)memberIdCgpansClosureStmt.getObject(3);

      Log.log(5, "GMDAO", "getMemberIdCgpansForClosure", "before iterating Result Set");

      while (resultSet.next())
      {
        Log.log(5, "GMDAO", "getMemberIdCgpansForClosure", "Inside Result Set");

        noOfCgpans = resultSet.getInt(1);
        Log.log(5, "GMDAO", "getMemberIdCgpansForClosure", "noOfCgpans " + noOfCgpans);

        memberId = resultSet.getString(2) + resultSet.getString(3) + resultSet.getString(4);
        Log.log(5, "GMDAO", "getMemberIdCgpansForClosure", "mem id" + memberId);

        memberIdCgpans.put(memberId, new Integer(noOfCgpans));
      }
      Log.log(5, "GMDAO", "getMemberIdCgpansForClosure", "memberid-pans map size" + memberIdCgpans.size());

      resultSet.close();
      resultSet = null;
      memberIdCgpansClosureStmt.close();
      memberIdCgpansClosureStmt = null;

      connection.commit();
    }
    catch (Exception exception)
    {
      try
      {
        connection.rollback();
      }
      catch (SQLException localSQLException)
      {
      }
      throw new DatabaseException(exception.getMessage());
    }
    finally {
      DBConnection.freeConnection(connection);
    }
    Log.log(4, "GMDAO", "getMemberIdCgpansForClosure", "Exited");

    return memberIdCgpans;
  }

  public ArrayList getCgpansForClosure(String memberId, String feeType)
    throws DatabaseException
  {
    Log.log(4, "GMDAO", "getCgpansForClosure", "Entered");

    Log.log(5, "GMDAO", "getCgpansForClosure", "fee Type " + feeType);
    Log.log(5, "GMDAO", "getCgpansForClosure", "memberId " + memberId);

    ResultSet resultSet = null;

    Connection connection = DBConnection.getConnection(false);
    CallableStatement cgpansClosureStmt = null;

    String bankId = memberId.substring(0, 4);
    String zoneId = memberId.substring(4, 8);
    String branchId = memberId.substring(8, 12);

    ArrayList cgpans = new ArrayList();
    try
    {
      String exception = "";

      cgpansClosureStmt = connection.prepareCall("{?=call packGetNotPaidCgpans.funcGetNotPaidCgpans(?,?,?,?,?,?)}");

      cgpansClosureStmt.registerOutParameter(1, 4);

      cgpansClosureStmt.setString(2, bankId);
      cgpansClosureStmt.setString(3, zoneId);
      cgpansClosureStmt.setString(4, branchId);
      cgpansClosureStmt.setString(5, feeType);

      cgpansClosureStmt.registerOutParameter(6, -10);

      cgpansClosureStmt.registerOutParameter(7, 12);

      cgpansClosureStmt.executeQuery();

      Log.log(5, "GMDAO", "getCgpansForClosure", "Query Executed");

      exception = cgpansClosureStmt.getString(7);

      int errorcode = cgpansClosureStmt.getInt(1);

      if (errorcode == 0)
      {
        Log.log(5, "GMDAO", "getMemberIdCgpansForClosure", "SUCCESS");
      }

      if (errorcode == 1)
      {
        connection.rollback();
        cgpansClosureStmt.close();
        cgpansClosureStmt = null;
        Log.log(2, "GMDAO", "getCgpansForClosure", "Exception " + exception);

        throw new DatabaseException(exception);
      }

      resultSet = (ResultSet)cgpansClosureStmt.getObject(6);

      Log.log(5, "GMDAO", "getCgpansForClosure", "before iterating Result Set");

      String cgpan = null;

      while (resultSet.next())
      {
        Log.log(5, "GMDAO", "getCgpansForClosure", "Inside Result Set");
        cgpan = resultSet.getString(1);
        Log.log(5, "GMDAO", "getCgpansForClosure", "Cgpan " + cgpan);
        cgpans.add(cgpan);
      }
      Log.log(5, "GMDAO", "getCgpansForClosure", "cgpans list size" + cgpans.size());

      resultSet.close();
      resultSet = null;
      cgpansClosureStmt.close();
      cgpansClosureStmt = null;

      connection.commit();
    }
    catch (Exception exception)
    {
      try
      {
        connection.rollback();
      }
      catch (SQLException localSQLException)
      {
      }
      throw new DatabaseException(exception.getMessage());
    }
    finally {
      DBConnection.freeConnection(connection);
    }
    Log.log(4, "GMDAO", "getCgpansForClosure", "Exited");

    return cgpans;
  }

  public void updateBorrowerDetails(BorrowerDetails borrowerDetail, String userId)
    throws DatabaseException
  {
    Log.log(4, "GMDAO", "updateBorrowerDetails", "Entered");
    Connection connection = DBConnection.getConnection(false);

    CallableStatement updateBorrowerDetailsStmt = null;

    SSIDetails ssiDetails = new SSIDetails();

    java.sql.Date sqlDate = null;
    java.util.Date utilDate = new java.util.Date();

    if (borrowerDetail != null)
    {
      try
      {
        updateBorrowerDetailsStmt = connection.prepareCall("{?=call funcUpdateSSIDetail(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)}");

        updateBorrowerDetailsStmt.registerOutParameter(1, 4);

        updateBorrowerDetailsStmt.registerOutParameter(29, 12);

        updateBorrowerDetailsStmt.setInt(2, borrowerDetail.getSsiDetails().getBorrowerRefNo());

        Log.log(5, "GMDAO", "updateBorrowerDetails", "Ref no" + borrowerDetail.getSsiDetails().getBorrowerRefNo());

        updateBorrowerDetailsStmt.setString(3, borrowerDetail.getPreviouslyCovered());

        updateBorrowerDetailsStmt.setString(4, borrowerDetail.getAssistedByBank());

        updateBorrowerDetailsStmt.setString(5, borrowerDetail.getAcNo());
        updateBorrowerDetailsStmt.setString(6, borrowerDetail.getNpa());
        updateBorrowerDetailsStmt.setString(7, borrowerDetail.getSsiDetails().getConstitution());
        updateBorrowerDetailsStmt.setString(8, borrowerDetail.getSsiDetails().getSsiType());
        updateBorrowerDetailsStmt.setString(9, borrowerDetail.getSsiDetails().getSsiName().toUpperCase());

        updateBorrowerDetailsStmt.setString(10, borrowerDetail.getSsiDetails().getRegNo());

        updateBorrowerDetailsStmt.setDate(11, null);
        updateBorrowerDetailsStmt.setString(12, borrowerDetail.getSsiDetails().getSsiITPan());

        updateBorrowerDetailsStmt.setString(13, borrowerDetail.getSsiDetails().getActivityType());
        updateBorrowerDetailsStmt.setInt(14, borrowerDetail.getSsiDetails().getEmployeeNos());
        updateBorrowerDetailsStmt.setDouble(15, borrowerDetail.getSsiDetails().getProjectedSalesTurnover());

        updateBorrowerDetailsStmt.setDouble(16, borrowerDetail.getSsiDetails().getProjectedExports());
        updateBorrowerDetailsStmt.setString(17, borrowerDetail.getSsiDetails().getAddress().toUpperCase());

        updateBorrowerDetailsStmt.setString(18, borrowerDetail.getSsiDetails().getCity().toUpperCase());
        updateBorrowerDetailsStmt.setString(19, borrowerDetail.getSsiDetails().getPincode());

        updateBorrowerDetailsStmt.setBoolean(20, false);

        updateBorrowerDetailsStmt.setString(21, borrowerDetail.getSsiDetails().getDistrict());

        updateBorrowerDetailsStmt.setString(22, borrowerDetail.getSsiDetails().getState());

        updateBorrowerDetailsStmt.setString(23, borrowerDetail.getSsiDetails().getIndustryNature());

        updateBorrowerDetailsStmt.setInt(24, 0);

        updateBorrowerDetailsStmt.setString(25, borrowerDetail.getSsiDetails().getIndustrySector());

        updateBorrowerDetailsStmt.setDouble(26, borrowerDetail.getOsAmt());
        updateBorrowerDetailsStmt.setBoolean(27, false);

        updateBorrowerDetailsStmt.setString(28, userId);
        updateBorrowerDetailsStmt.executeQuery();

        int updateBorrowerDetailsStmtValue = updateBorrowerDetailsStmt.getInt(1);
        String exception = updateBorrowerDetailsStmt.getString(29);
        if (updateBorrowerDetailsStmtValue == 1)
        {
          connection.rollback();
          updateBorrowerDetailsStmt.close();
          updateBorrowerDetailsStmt = null;
          Log.log(2, "GMDAO", "updatePromoterDetails", "Exception " + exception);
          throw new DatabaseException(exception);
        }
        updateBorrowerDetailsStmt.close();
        updateBorrowerDetailsStmt = null;

        updateBorrowerDetailsStmt = connection.prepareCall("{?=call funcUpdatePromoterDtl(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)}");

        updateBorrowerDetailsStmt.registerOutParameter(1, 4);
        updateBorrowerDetailsStmt.registerOutParameter(23, 12);
        Log.log(5, "GMDAO", "update promoter Details", "Ref no" + borrowerDetail.getSsiDetails().getBorrowerRefNo());

        updateBorrowerDetailsStmt.setInt(2, borrowerDetail.getSsiDetails().getBorrowerRefNo());
        updateBorrowerDetailsStmt.setString(3, borrowerDetail.getSsiDetails().getCpTitle());
        Log.log(5, "GMDAO", "update promoter Details", "title" + borrowerDetail.getSsiDetails().getCpTitle());

        updateBorrowerDetailsStmt.setString(4, borrowerDetail.getSsiDetails().getCpFirstName());
        Log.log(5, "GMDAO", "update promoter Details", "Cp first name" + borrowerDetail.getSsiDetails().getCpFirstName());

        updateBorrowerDetailsStmt.setString(5, borrowerDetail.getSsiDetails().getCpMiddleName());
        Log.log(5, "GMDAO", "update promoter Details", "Cp middle name" + borrowerDetail.getSsiDetails().getCpMiddleName());

        updateBorrowerDetailsStmt.setString(6, borrowerDetail.getSsiDetails().getCpLastName());
        Log.log(5, "GMDAO", "update promoter Details", "CP last name" + borrowerDetail.getSsiDetails().getCpLastName());

        updateBorrowerDetailsStmt.setString(7, borrowerDetail.ssiDetails.getCpITPAN());
        Log.log(5, "GMDAO", "update promoter Details", "it pan" + borrowerDetail.ssiDetails.getCpITPAN());

        updateBorrowerDetailsStmt.setString(8, borrowerDetail.getSsiDetails().getCpGender());
        Log.log(5, "GMDAO", "update promoter Details", "gender" + borrowerDetail.getSsiDetails().getCpGender());

        utilDate = borrowerDetail.getSsiDetails().getCpDOB();
        if ((utilDate != null) && (!utilDate.toString().equals("")))
        {
          sqlDate = new java.sql.Date(utilDate.getTime());

          updateBorrowerDetailsStmt.setDate(9, sqlDate);
        } else {
          updateBorrowerDetailsStmt.setDate(9, null);
        }
        Log.log(5, "GMDAO", "update promoter Details", "CpDOB" + sqlDate);
        updateBorrowerDetailsStmt.setString(10, borrowerDetail.getSsiDetails().getCpLegalID());
        Log.log(5, "GMDAO", "update promoter Details", "Cplegal Id" + borrowerDetail.getSsiDetails().getCpLegalID());
        updateBorrowerDetailsStmt.setString(11, borrowerDetail.getSsiDetails().getCpLegalIdValue());

        updateBorrowerDetailsStmt.setString(12, borrowerDetail.getSsiDetails().getFirstName());
        Log.log(5, "GMDAO", "update promoter Details", "first name" + borrowerDetail.getSsiDetails().getFirstName());
        updateBorrowerDetailsStmt.setString(14, borrowerDetail.getSsiDetails().getFirstItpan());
        Log.log(5, "GMDAO", "update promoter Details", "first it pan" + borrowerDetail.getSsiDetails().getFirstItpan());
        updateBorrowerDetailsStmt.setString(15, borrowerDetail.getSsiDetails().getSecondName());
        Log.log(5, "GMDAO", "update promoter Details", "2 name" + borrowerDetail.getSsiDetails().getSecondName());
        updateBorrowerDetailsStmt.setString(17, borrowerDetail.getSsiDetails().getSecondItpan());
        Log.log(5, "GMDAO", "update promoter Details", "2 itpan" + borrowerDetail.getSsiDetails().getSecondItpan());
        updateBorrowerDetailsStmt.setString(18, borrowerDetail.getSsiDetails().getThirdName());
        Log.log(5, "GMDAO", "update promoter Details", "3 name" + borrowerDetail.getSsiDetails().getThirdName());
        updateBorrowerDetailsStmt.setString(20, borrowerDetail.getSsiDetails().getThirdItpan());
        Log.log(5, "GMDAO", "update promoter Details", "3 it pan" + borrowerDetail.getSsiDetails().getThirdItpan());
        updateBorrowerDetailsStmt.setString(21, borrowerDetail.getSsiDetails().getSocialCategory());
        updateBorrowerDetailsStmt.setString(22, userId);

        utilDate = borrowerDetail.getSsiDetails().getFirstDOB();
        if ((utilDate != null) && (!utilDate.toString().equals("")))
        {
          sqlDate = new java.sql.Date(utilDate.getTime());

          updateBorrowerDetailsStmt.setDate(13, sqlDate);
        } else {
          updateBorrowerDetailsStmt.setDate(13, null);
        }
        Log.log(5, "GMDAO", "update promoter Details", "1 DOB" + sqlDate);

        utilDate = borrowerDetail.getSsiDetails().getSecondDOB();

        if ((utilDate != null) && (!utilDate.toString().equals(""))) {
          sqlDate = new java.sql.Date(utilDate.getTime());

          updateBorrowerDetailsStmt.setDate(16, sqlDate);
          Log.log(5, "GMDAO", "update promoter Details", "2 DOB" + sqlDate);
        } else {
          updateBorrowerDetailsStmt.setDate(16, null);
        }

        utilDate = borrowerDetail.getSsiDetails().getThirdDOB();
        if ((utilDate != null) && (!utilDate.toString().equals("")))
        {
          sqlDate = new java.sql.Date(utilDate.getTime());

          updateBorrowerDetailsStmt.setDate(19, sqlDate);
        } else {
          updateBorrowerDetailsStmt.setDate(19, null);
        }
        Log.log(5, "GMDAO", "update promoter Details", "3 DOB" + sqlDate);

        updateBorrowerDetailsStmt.executeQuery();

        updateBorrowerDetailsStmtValue = updateBorrowerDetailsStmt.getInt(1);
        String error = updateBorrowerDetailsStmt.getString(23);
        if (updateBorrowerDetailsStmtValue == 1)
        {
          connection.rollback();
          updateBorrowerDetailsStmt.close();
          updateBorrowerDetailsStmt = null;
          Log.log(2, "GMDAO", "updateBorrowerDetails", "Update Borrower Detail exception :" + error);
          throw new DatabaseException(error);
        }
        updateBorrowerDetailsStmt.close();
        updateBorrowerDetailsStmt = null;

        connection.commit();
      }
      catch (SQLException exception)
      {
        Log.logException(exception);
        throw new DatabaseException(exception.getMessage());
      }
      finally {
        DBConnection.freeConnection(connection);
      }
    }
    Log.log(4, "GMDAO", "updateBorrowerDetails", "Exited");
  }

  public boolean shiftCgpanBorrower(String srcId, String userId, String cgpan, String destId)
    throws DatabaseException
  {
    Log.log(4, "GMDAO", "shiftCgpanBorrower", "Entered");
    Connection connection = DBConnection.getConnection();
    boolean shiftStatus = true;
    String srcBankId = srcId.substring(0, 4);
    String srcZoneId = srcId.substring(4, 8);
    String srcBranchId = srcId.substring(8, 12);

    String destBankId = destId.substring(0, 4);
    String destZoneId = destId.substring(4, 8);
    String destBranchId = destId.substring(8, 12);
    try
    {
      CallableStatement shiftCgpanStmt = connection.prepareCall("{?=call funcShiftCGPANNEW(?,?,?,?,?)}");

      shiftCgpanStmt.registerOutParameter(1, 4);
      shiftCgpanStmt.registerOutParameter(6, 12);

      shiftCgpanStmt.setString(2, cgpan);
      shiftCgpanStmt.setString(3, srcId);
      shiftCgpanStmt.setString(4, destId);
      shiftCgpanStmt.setString(5, userId);

      shiftStatus = shiftCgpanStmt.execute();

      int functionReturn = shiftCgpanStmt.getInt(1);

      String error = shiftCgpanStmt.getString(6);

      if (functionReturn == 0)
      {
        Log.log(5, "GMDAO", "ShiftCgpanBorrower", "SP SUCCESS");
      }
      if (functionReturn == 1)
      {
        shiftCgpanStmt.close();
        shiftCgpanStmt = null;
        connection.rollback();

        Log.log(2, "GMDAO", "Shift cgpan", "Exception " + error);

        throw new DatabaseException(error);
      }
      shiftCgpanStmt.close();
      shiftCgpanStmt = null;
      connection.commit();
    }
    catch (Exception e)
    {
      Log.log(2, "GMDAO", "Shift cgpan", e.getMessage());

      Log.logException(e);
      try
      {
        connection.rollback();
      }
      catch (SQLException localSQLException)
      {
      }
      throw new DatabaseException();
    }
    finally
    {
      DBConnection.freeConnection(connection);
    }

    Log.log(4, "GMDAO", "shiftCgpanBorrower", "Exited");

    return shiftStatus;
  }

  public BorrowerDetails getBorrowerDetails(String memberId, String id, int type)
    throws DatabaseException
  {
	  //System.out.println("GMDAO getBorrowerDetails   S ");
    Log.log(4, "GMDAO", "getBorrowerDetails", "Entered");
    BorrowerDetails borrowerDetails = null;
    Log.log(5, "GMDAO", "getBorrowerDetails", "mem" + memberId);
    Log.log(5, "GMDAO", "getBorrowerDetails", "  id " + id);

    if (type == 0) {
    	//System.out.println("GMDAO if type=0");
      borrowerDetails = getBorrowerDetailsForBID(memberId, id);
    }
    else if (type == 1)
    {
    	//System.out.println("GMDAO if type=1");
      borrowerDetails = getBorrowerDetailsForCgpan(memberId, id);
    }
    else if (type == 2)
    {
    	//System.out.println("GMDAO if type=3");
      borrowerDetails = getBorrowerDetailsForBorrower(memberId, id);
    }

    Log.log(4, "GMDAO", "getBorrowerDetails", "Exited");

    return borrowerDetails;
  }

  public BorrowerDetails getBorrowerDetailsForBID(String memberId, String borrowerId)
    throws DatabaseException
  {
	  //System.out.println("GMDAO  getBorrowerDetailsForBID S");
    Log.log(4, "GMDAO", "getBorrowerDetailsForBID", "Entered");
    Connection connection = DBConnection.getConnection();

    SSIDetails ssiDetails = new SSIDetails();
    BorrowerDetails borrowerDetails = new BorrowerDetails();

    String bankId = memberId.substring(0, 4);
    String zoneId = memberId.substring(4, 8);
    String branchId = memberId.substring(8, 12);
    int getStatus = 0;
    try
    {
      CallableStatement getBorrowerDetailsStmt = connection.prepareCall("{?=call funcGetSSIDetailforBID(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)}");
   //System.out.println(" funcGetSSIDetailforBID");
      getBorrowerDetailsStmt.setString(2, bankId);
      getBorrowerDetailsStmt.setString(3, zoneId);
      getBorrowerDetailsStmt.setString(4, branchId);
      getBorrowerDetailsStmt.setString(5, borrowerId);
      getBorrowerDetailsStmt.registerOutParameter(1, 4);

      getBorrowerDetailsStmt.registerOutParameter(32, 12);

      getBorrowerDetailsStmt.registerOutParameter(6, 4);
      getBorrowerDetailsStmt.registerOutParameter(7, 12);
      getBorrowerDetailsStmt.registerOutParameter(8, 12);
      getBorrowerDetailsStmt.registerOutParameter(9, 12);

      getBorrowerDetailsStmt.registerOutParameter(10, 12);
      getBorrowerDetailsStmt.registerOutParameter(11, 12);
      getBorrowerDetailsStmt.registerOutParameter(12, 12);
      getBorrowerDetailsStmt.registerOutParameter(13, 12);
      getBorrowerDetailsStmt.registerOutParameter(14, 12);
      getBorrowerDetailsStmt.registerOutParameter(15, 91);
      getBorrowerDetailsStmt.registerOutParameter(16, 12);
      getBorrowerDetailsStmt.registerOutParameter(17, 12);
      getBorrowerDetailsStmt.registerOutParameter(18, 4);
      getBorrowerDetailsStmt.registerOutParameter(19, 8);
      getBorrowerDetailsStmt.registerOutParameter(20, 8);
      getBorrowerDetailsStmt.registerOutParameter(21, 12);
      getBorrowerDetailsStmt.registerOutParameter(22, 12);
      getBorrowerDetailsStmt.registerOutParameter(23, 12);
      getBorrowerDetailsStmt.registerOutParameter(24, 12);
      getBorrowerDetailsStmt.registerOutParameter(25, 12);
      getBorrowerDetailsStmt.registerOutParameter(26, 12);
      getBorrowerDetailsStmt.registerOutParameter(27, 12);
      getBorrowerDetailsStmt.registerOutParameter(28, 12);
      getBorrowerDetailsStmt.registerOutParameter(29, 12);
      getBorrowerDetailsStmt.registerOutParameter(30, 12);
      getBorrowerDetailsStmt.registerOutParameter(31, 8);
      

      getBorrowerDetailsStmt.execute();
      getStatus = getBorrowerDetailsStmt.getInt(1);
      String exception = getBorrowerDetailsStmt.getString(32);

      if (getStatus == 0) {
        Log.log(5, "GMDAO", "getBorroerDetails", "Success-SP");
      }
      if (getStatus == 1) {
        getBorrowerDetailsStmt.close();
        getBorrowerDetailsStmt = null;
        Log.log(2, "GMDAO", "GetBorrwoewerDtls", "Exception" + exception);
      //  System.out.println("5064 Something"+exception);
        throw new DatabaseException("Something ["+exception+"] went wrong, Please contact Support[support@cgtmse.in] team");
      }
      Log.log(5, "GMDAO", "GetBorrwoewerDtls", "Borrower Id : " + borrowerId);
      ssiDetails.setBorrowerRefNo(getBorrowerDetailsStmt.getInt(6));
      Log.log(5, "GMDAO", "GetBorrwoewerDtls", "Borrower ref no :" + ssiDetails.getBorrowerRefNo());

      borrowerDetails.setPreviouslyCovered(getBorrowerDetailsStmt.getString(7).trim());
      Log.log(5, "GMDAO", "GetBorrwoewerDtls", "previously covered :" + borrowerDetails.getPreviouslyCovered());

      borrowerDetails.setAssistedByBank(getBorrowerDetailsStmt.getString(8).trim());
      Log.log(5, "GMDAO", "GetBorrwoewerDtls", "AssistedByBank:" + borrowerDetails.getAssistedByBank());

      borrowerDetails.setAcNo(getBorrowerDetailsStmt.getString(9));
      Log.log(5, "GMDAO", "GetBorrwoewerDtls", "setAcNo:" + borrowerDetails.getAcNo());

      borrowerDetails.setNpa(getBorrowerDetailsStmt.getString(10).trim());
      Log.log(5, "GMDAO", "GetBorrwoewerDtls", "setNpa:" + borrowerDetails.getNpa());

      ssiDetails.setConstitution(getBorrowerDetailsStmt.getString(11));
      Log.log(5, "GMDAO", "GetBorrwoewerDtls", "setConstitution:" + ssiDetails.getConstitution());

      ssiDetails.setSsiType(getBorrowerDetailsStmt.getString(12).trim());
      Log.log(5, "GMDAO", "GetBorrwoewerDtls", "setSsiType:" + ssiDetails.getSsiType());

      ssiDetails.setSsiName(getBorrowerDetailsStmt.getString(13));
      Log.log(5, "GMDAO", "GetBorrwoewerDtls", "setSsiName:" + ssiDetails.getSsiName());

      ssiDetails.setRegNo(getBorrowerDetailsStmt.getString(14));
      Log.log(5, "GMDAO", "GetBorrwoewerDtls", "setRegNo:" + ssiDetails.getRegNo());

      ssiDetails.setCommencementDate(DateHelper.sqlToUtilDate(getBorrowerDetailsStmt.getDate(15)));
      Log.log(5, "GMDAO", "GetBorrwoewerDtls", "setCommencementDate:" + ssiDetails.getCommencementDate());

      ssiDetails.setSsiITPan(getBorrowerDetailsStmt.getString(16));
      Log.log(5, "GMDAO", "GetBorrwoewerDtls", "setSsiITPan:" + ssiDetails.getSsiITPan());

      ssiDetails.setActivityType(getBorrowerDetailsStmt.getString(17));
      Log.log(5, "GMDAO", "GetBorrwoewerDtls", "setActivityType:" + ssiDetails.getActivityType());

      ssiDetails.setEmployeeNos(getBorrowerDetailsStmt.getInt(18));
      Log.log(5, "GMDAO", "GetBorrwoewerDtls", "setEmployeeNos:" + ssiDetails.getEmployeeNos());

      ssiDetails.setProjectedSalesTurnover(getBorrowerDetailsStmt.getDouble(19));
      Log.log(5, "GMDAO", "GetBorrwoewerDtls", "setProjectedSalesTurnover:" + ssiDetails.getProjectedSalesTurnover());

      ssiDetails.setProjectedExports(getBorrowerDetailsStmt.getDouble(20));
      Log.log(5, "GMDAO", "GetBorrwoewerDtls", "setProjectedSalesTurnover:" + ssiDetails.getProjectedExports());

      ssiDetails.setAddress(getBorrowerDetailsStmt.getString(21));
      Log.log(5, "GMDAO", "GetBorrwoewerDtls", "setAddress:" + ssiDetails.getAddress());

      ssiDetails.setCity(getBorrowerDetailsStmt.getString(22));
      Log.log(5, "GMDAO", "GetBorrwoewerDtls", "setCity:" + ssiDetails.getCity());

      ssiDetails.setPincode(getBorrowerDetailsStmt.getString(23).trim());
      Log.log(5, "GMDAO", "GetBorrwoewerDtls", "setPincode:" + ssiDetails.getPincode());

      ssiDetails.setDistrict(getBorrowerDetailsStmt.getString(25));
      Log.log(5, "GMDAO", "GetBorrwoewerDtls", "setDistrict:" + ssiDetails.getDistrict());

      ssiDetails.setState(getBorrowerDetailsStmt.getString(26));
      Log.log(5, "GMDAO", "GetBorrwoewerDtls", "setState:" + ssiDetails.getState());

      ssiDetails.setIndustryNature(getBorrowerDetailsStmt.getString(27));
      Log.log(5, "GMDAO", "GetBorrwoewerDtls", "setIndustryNature:" + ssiDetails.getIndustryNature());

      ssiDetails.setIndustrySector(getBorrowerDetailsStmt.getString(28));
      Log.log(5, "GMDAO", "GetBorrwoewerDtls", "setIndustrySector:" + ssiDetails.getIndustrySector());

      ssiDetails.setCgbid(getBorrowerDetailsStmt.getString(30));
      Log.log(5, "GMDAO", "GetBorrwoewerDtls", "setCgbid:" + ssiDetails.getCgbid());

      borrowerDetails.setOsAmt(getBorrowerDetailsStmt.getDouble(31));
      Log.log(5, "GMDAO", "GetBorrwoewerDtls", "setOsAmt:" + borrowerDetails.getOsAmt());
      borrowerDetails.setSsiDetails(ssiDetails);
      int ssiRefNo = borrowerDetails.getSsiDetails().getBorrowerRefNo();
      Log.log(5, "GMDAO", "GetBorrwoewerDtls", "Borrower ref no :" + ssiRefNo);

      Log.log(5, "GMDAO", "GetBorrwoewerDtls", "Borrower ref no outside:" + ssiRefNo);

      CallableStatement getPromoterDetailsStmt = connection.prepareCall("{?=call funcGetPromoterDtlforSSIRef(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)}");

      getPromoterDetailsStmt.registerOutParameter(1, 4);
      getPromoterDetailsStmt.registerOutParameter(23, 12);

      getPromoterDetailsStmt.setInt(2, ssiRefNo);
      getPromoterDetailsStmt.registerOutParameter(3, 4);
      getPromoterDetailsStmt.registerOutParameter(4, 12);
      getPromoterDetailsStmt.registerOutParameter(5, 12);
      getPromoterDetailsStmt.registerOutParameter(6, 12);
      getPromoterDetailsStmt.registerOutParameter(7, 12);
      getPromoterDetailsStmt.registerOutParameter(8, 12);
      getPromoterDetailsStmt.registerOutParameter(9, 12);
      getPromoterDetailsStmt.registerOutParameter(10, 91);
      getPromoterDetailsStmt.registerOutParameter(11, 12);
      getPromoterDetailsStmt.registerOutParameter(12, 12);
      getPromoterDetailsStmt.registerOutParameter(13, 12);
      getPromoterDetailsStmt.registerOutParameter(14, 91);
      getPromoterDetailsStmt.registerOutParameter(15, 12);
      getPromoterDetailsStmt.registerOutParameter(16, 12);
      getPromoterDetailsStmt.registerOutParameter(17, 91);
      getPromoterDetailsStmt.registerOutParameter(18, 12);
      getPromoterDetailsStmt.registerOutParameter(19, 12);
      getPromoterDetailsStmt.registerOutParameter(20, 91);
      getPromoterDetailsStmt.registerOutParameter(21, 12);
      getPromoterDetailsStmt.registerOutParameter(22, 12);

      getPromoterDetailsStmt.executeQuery();
      int getPromoterDetailsStatus = getPromoterDetailsStmt.getInt(1);
      String prException = getPromoterDetailsStmt.getString(23);

      if (getPromoterDetailsStatus == 1)
      {
        getPromoterDetailsStmt.close();
        getPromoterDetailsStmt = null;
        Log.log(2, "GMDAO", "GetpromotererDtls", "Exception :" + prException);
       // System.out.println("5181 Something"+prException);
        throw new DatabaseException("Something ["+prException+"] went wrong, Please contact Support[support@cgtmse.in] team");
      }

      ssiDetails.setCpTitle(getPromoterDetailsStmt.getString(4));
      Log.log(5, "GMDAO", "GetpromotererDtls", "CP Gender" + ssiDetails.getCpTitle());
      ssiDetails.setCpFirstName(getPromoterDetailsStmt.getString(5));
      ssiDetails.setCpMiddleName(getPromoterDetailsStmt.getString(6));
      ssiDetails.setCpLastName(getPromoterDetailsStmt.getString(7));
      ssiDetails.setCpITPAN(getPromoterDetailsStmt.getString(8));
      ssiDetails.setCpGender(getPromoterDetailsStmt.getString(9).trim());
      Log.log(5, "GMDAO", "GetpromotererDtls", "CP Gender" + ssiDetails.getCpGender());
      ssiDetails.setCpDOB(DateHelper.sqlToUtilDate(getPromoterDetailsStmt.getDate(10)));
      ssiDetails.setCpLegalID(getPromoterDetailsStmt.getString(11));
      Log.log(5, "GMDAO", "GetpromotererDtls", "CP Gender" + ssiDetails.getCpLegalID());
      ssiDetails.setCpLegalIdValue(getPromoterDetailsStmt.getString(12));
      ssiDetails.setFirstName(getPromoterDetailsStmt.getString(13));
      ssiDetails.setFirstDOB(DateHelper.sqlToUtilDate(getPromoterDetailsStmt.getDate(14)));
      ssiDetails.setFirstItpan(getPromoterDetailsStmt.getString(15));
      ssiDetails.setSecondName(getPromoterDetailsStmt.getString(16));
      ssiDetails.setSecondDOB(DateHelper.sqlToUtilDate(getPromoterDetailsStmt.getDate(17)));
      ssiDetails.setSecondItpan(getPromoterDetailsStmt.getString(18));
      ssiDetails.setThirdName(getPromoterDetailsStmt.getString(19));
      ssiDetails.setThirdDOB(DateHelper.sqlToUtilDate(getPromoterDetailsStmt.getDate(20)));
      ssiDetails.setThirdItpan(getPromoterDetailsStmt.getString(21));
      ssiDetails.setSocialCategory(getPromoterDetailsStmt.getString(22));
      borrowerDetails.setSsiDetails(ssiDetails);

      getBorrowerDetailsStmt.close();
      getBorrowerDetailsStmt = null;
      getPromoterDetailsStmt.close();
      getPromoterDetailsStmt = null;
    }
    catch (SQLException exception)
    {
      Log.logException(exception);
     // System.out.println("5217 Something"+exception.getMessage());
      throw new DatabaseException("Something ["+exception.getMessage()+"] went wrong, Please contact Support[support@cgtmse.in] team");
    }
    finally {
      DBConnection.freeConnection(connection);
    }
    int ssiRefNo;
    CallableStatement getBorrowerDetailsStmt;
    Log.log(4, "GMDAO", "getBorrowerDetailsForBID", "Exited");

    return borrowerDetails;
  }

  public BorrowerDetails getBorrowerDetailsForBorrower(String memberId, String borrowerName)
    throws DatabaseException
  {
	  
	  //System.out.println("GMDAO getBorrowerDetailsForBorrower  S");
    Log.log(4, "GMDAO", "getBorrowerDetailsForBorrowerName", "Entered");

    Connection connection = DBConnection.getConnection();
    CallableStatement getBorrowerDetailsStmt = null;

    SSIDetails ssiDetails = new SSIDetails();
    BorrowerDetails borrowerDetails = new BorrowerDetails();

    int ssiRefNo = 0;

    String bankId = memberId.substring(0, 4);
    String zoneId = memberId.substring(4, 8);
    String branchId = memberId.substring(8, 12);
    int getStatus = 0;
    Log.log(5, "GMDAO", "getBorrowerDetailsForBorrowerName", "name" + borrowerName);
    Log.log(5, "GMDAO", "getBorrowerDetailsForBorrowerName", "member" + memberId);
    try
    {
      getBorrowerDetailsStmt = connection.prepareCall("{?=call funcGetSSIDetailforBorr(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)}");
   //System.out.println("funcGetSSIDetailforBorr   S");
      getBorrowerDetailsStmt.setString(2, bankId);
      getBorrowerDetailsStmt.setString(3, zoneId);
      getBorrowerDetailsStmt.setString(4, branchId);
      getBorrowerDetailsStmt.setString(5, borrowerName);

      getBorrowerDetailsStmt.registerOutParameter(1, 4);

      getBorrowerDetailsStmt.registerOutParameter(32, 12);

      getBorrowerDetailsStmt.registerOutParameter(6, 4);
      getBorrowerDetailsStmt.registerOutParameter(7, 12);
      getBorrowerDetailsStmt.registerOutParameter(8, 12);
      getBorrowerDetailsStmt.registerOutParameter(9, 12);

      getBorrowerDetailsStmt.registerOutParameter(10, 12);
      getBorrowerDetailsStmt.registerOutParameter(11, 12);
      getBorrowerDetailsStmt.registerOutParameter(12, 12);
      getBorrowerDetailsStmt.registerOutParameter(13, 12);
      getBorrowerDetailsStmt.registerOutParameter(14, 12);
      getBorrowerDetailsStmt.registerOutParameter(15, 91);
      getBorrowerDetailsStmt.registerOutParameter(16, 12);
      getBorrowerDetailsStmt.registerOutParameter(17, 12);
      getBorrowerDetailsStmt.registerOutParameter(18, 4);
      getBorrowerDetailsStmt.registerOutParameter(19, 8);
      getBorrowerDetailsStmt.registerOutParameter(20, 8);
      getBorrowerDetailsStmt.registerOutParameter(21, 12);
      getBorrowerDetailsStmt.registerOutParameter(22, 12);
      getBorrowerDetailsStmt.registerOutParameter(23, 12);
      getBorrowerDetailsStmt.registerOutParameter(24, 12);
      getBorrowerDetailsStmt.registerOutParameter(25, 12);
      getBorrowerDetailsStmt.registerOutParameter(26, 12);
      getBorrowerDetailsStmt.registerOutParameter(27, 12);
      getBorrowerDetailsStmt.registerOutParameter(28, 12);
      getBorrowerDetailsStmt.registerOutParameter(29, 12);
      getBorrowerDetailsStmt.registerOutParameter(30, 12);
      getBorrowerDetailsStmt.registerOutParameter(31, 8);
      getBorrowerDetailsStmt.execute();
      getStatus = getBorrowerDetailsStmt.getInt(1);

      String exception = getBorrowerDetailsStmt.getString(32);

      if (getStatus == 0) {
        Log.log(5, "borrowerDetails", "get ", "success dao for BorrowerName SP");
      }
      if (getStatus == 1) {
        Log.log(2, "GMDAO", "GetBorrwoewerDtls", "Exception " + exception);
        getBorrowerDetailsStmt.close();
        getBorrowerDetailsStmt = null;
        throw new DatabaseException(exception);
      }

      ssiDetails.setBorrowerRefNo(getBorrowerDetailsStmt.getInt(6));
      borrowerDetails.setPreviouslyCovered(getBorrowerDetailsStmt.getString(7).trim());
      borrowerDetails.setAssistedByBank(getBorrowerDetailsStmt.getString(8).trim());
      borrowerDetails.setAcNo(getBorrowerDetailsStmt.getString(9));
      borrowerDetails.setNpa(getBorrowerDetailsStmt.getString(10).trim());
      ssiDetails.setConstitution(getBorrowerDetailsStmt.getString(11));
      ssiDetails.setSsiType(getBorrowerDetailsStmt.getString(12).trim());
      ssiDetails.setSsiName(getBorrowerDetailsStmt.getString(13));
      ssiDetails.setRegNo(getBorrowerDetailsStmt.getString(14));
      ssiDetails.setCommencementDate(DateHelper.sqlToUtilDate(getBorrowerDetailsStmt.getDate(15)));
      ssiDetails.setSsiITPan(getBorrowerDetailsStmt.getString(16));
      ssiDetails.setActivityType(getBorrowerDetailsStmt.getString(17));
      ssiDetails.setEmployeeNos(getBorrowerDetailsStmt.getInt(18));
      ssiDetails.setProjectedSalesTurnover(getBorrowerDetailsStmt.getDouble(19));
      ssiDetails.setProjectedExports(getBorrowerDetailsStmt.getDouble(20));
      ssiDetails.setAddress(getBorrowerDetailsStmt.getString(21));
      ssiDetails.setCity(getBorrowerDetailsStmt.getString(22));
      ssiDetails.setPincode(getBorrowerDetailsStmt.getString(23).trim());
      ssiDetails.setDistrict(getBorrowerDetailsStmt.getString(25));
      ssiDetails.setState(getBorrowerDetailsStmt.getString(26));
      ssiDetails.setIndustryNature(getBorrowerDetailsStmt.getString(27));
      ssiDetails.setIndustrySector(getBorrowerDetailsStmt.getString(28));
      ssiDetails.setCgbid(getBorrowerDetailsStmt.getString(30));

      borrowerDetails.setOsAmt(getBorrowerDetailsStmt.getDouble(31));

      borrowerDetails.setSsiDetails(ssiDetails);
      ssiRefNo = borrowerDetails.getSsiDetails().getBorrowerRefNo();

      CallableStatement getPromoterDetailsStmt = connection.prepareCall("{?=call funcGetPromoterDtlforSSIRef(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)}");

      Log.log(5, "GMDAO", "getPRDetailsForBname", "ssiref" + ssiRefNo);
      getPromoterDetailsStmt.setInt(2, ssiRefNo);

      getPromoterDetailsStmt.registerOutParameter(1, 4);
      getPromoterDetailsStmt.registerOutParameter(23, 12);

      getPromoterDetailsStmt.registerOutParameter(3, 4);
      getPromoterDetailsStmt.registerOutParameter(4, 12);
      getPromoterDetailsStmt.registerOutParameter(5, 12);
      getPromoterDetailsStmt.registerOutParameter(6, 12);
      getPromoterDetailsStmt.registerOutParameter(7, 12);
      getPromoterDetailsStmt.registerOutParameter(8, 12);
      getPromoterDetailsStmt.registerOutParameter(9, 12);
      getPromoterDetailsStmt.registerOutParameter(10, 91);
      getPromoterDetailsStmt.registerOutParameter(11, 12);
      getPromoterDetailsStmt.registerOutParameter(12, 12);
      getPromoterDetailsStmt.registerOutParameter(13, 12);
      getPromoterDetailsStmt.registerOutParameter(14, 91);
      getPromoterDetailsStmt.registerOutParameter(15, 12);
      getPromoterDetailsStmt.registerOutParameter(16, 12);
      getPromoterDetailsStmt.registerOutParameter(17, 91);
      getPromoterDetailsStmt.registerOutParameter(18, 12);
      getPromoterDetailsStmt.registerOutParameter(19, 12);
      getPromoterDetailsStmt.registerOutParameter(20, 91);
      getPromoterDetailsStmt.registerOutParameter(21, 12);
      getPromoterDetailsStmt.registerOutParameter(22, 12);

      getPromoterDetailsStmt.executeQuery();
      String prException = getPromoterDetailsStmt.getString(23);
      int getPromoterDetailsStatus = getPromoterDetailsStmt.getInt(1);

      if (getPromoterDetailsStatus == 1)
      {
        Log.log(2, "GMDAO", "GetpromotererDtls", "Exception :" + prException);
        getPromoterDetailsStmt.close();
        getPromoterDetailsStmt = null;
        throw new DatabaseException(prException);
      }
      ssiDetails.setCpTitle(getPromoterDetailsStmt.getString(4));
      ssiDetails.setCpFirstName(getPromoterDetailsStmt.getString(5));
      ssiDetails.setCpMiddleName(getPromoterDetailsStmt.getString(6));
      ssiDetails.setCpLastName(getPromoterDetailsStmt.getString(7));
      ssiDetails.setCpITPAN(getPromoterDetailsStmt.getString(8));
      ssiDetails.setCpGender(getPromoterDetailsStmt.getString(9).trim());
      ssiDetails.setCpDOB(DateHelper.sqlToUtilDate(getPromoterDetailsStmt.getDate(10)));
      ssiDetails.setCpLegalID(getPromoterDetailsStmt.getString(11));
      ssiDetails.setCpLegalIdValue(getPromoterDetailsStmt.getString(12));
      ssiDetails.setFirstName(getPromoterDetailsStmt.getString(13));
      ssiDetails.setFirstDOB(DateHelper.sqlToUtilDate(getPromoterDetailsStmt.getDate(14)));
      ssiDetails.setFirstItpan(getPromoterDetailsStmt.getString(15));
      ssiDetails.setSecondName(getPromoterDetailsStmt.getString(16));
      ssiDetails.setSecondDOB(DateHelper.sqlToUtilDate(getPromoterDetailsStmt.getDate(17)));
      ssiDetails.setSecondItpan(getPromoterDetailsStmt.getString(18));
      ssiDetails.setThirdName(getPromoterDetailsStmt.getString(19));
      ssiDetails.setThirdDOB(DateHelper.sqlToUtilDate(getPromoterDetailsStmt.getDate(20)));
      ssiDetails.setThirdItpan(getPromoterDetailsStmt.getString(21));
      ssiDetails.setSocialCategory(getPromoterDetailsStmt.getString(22));

      borrowerDetails.setSsiDetails(ssiDetails);

      getBorrowerDetailsStmt.close();
      getBorrowerDetailsStmt = null;

      getPromoterDetailsStmt.close();
      getPromoterDetailsStmt = null;
    }
    catch (SQLException exception) {
      Log.logException(exception);
      throw new DatabaseException(exception.getMessage());
    } finally {
      DBConnection.freeConnection(connection);
    }
    Log.log(4, "GMDAO", "getBorrowerDetailsForBorrowerName", "Exited");

    return borrowerDetails;
  }

  public BorrowerDetails getBorrowerDetailsForCgpan(String memberId, String cgpan)
    throws DatabaseException
  {
	  
	  //System.out.println("GMDAO getBorrowerDetailsForCgpan  S");
    Log.log(4, "GMDAO", "getBorrowerDetailsForCGPAN", "Entered");
    Connection connection = DBConnection.getConnection();
    CallableStatement getBorrowerDetailsStmt = null;

    SSIDetails ssiDetails = new SSIDetails();
    BorrowerDetails borrowerDetails = new BorrowerDetails();

    int ssiRefNo = 0;

    String bankId = memberId.substring(0, 4);
    String zoneId = memberId.substring(4, 8);
    String branchId = memberId.substring(8, 12);
    int getStatus = 0;
    Log.log(5, "GMDAO", "getBorrowerDetailsForCGPAN", "CGPAN--" + cgpan);
    Log.log(5, "GMDAO", "getBorrowerDetailsForCGPAN", "member --" + memberId);
    try
    {
      getBorrowerDetailsStmt = connection.prepareCall("{?=call funcGetSSIDetailforCGPAN(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)}");
  //System.out.println("GMDAO funcGetSSIDetailforCGPAN  S");
      getBorrowerDetailsStmt.setString(2, bankId);
      getBorrowerDetailsStmt.setString(3, zoneId);
      getBorrowerDetailsStmt.setString(4, branchId);
      getBorrowerDetailsStmt.setString(5, cgpan);

      getBorrowerDetailsStmt.registerOutParameter(1, 4);

      getBorrowerDetailsStmt.registerOutParameter(32, 12);

      getBorrowerDetailsStmt.registerOutParameter(6, 4);
      getBorrowerDetailsStmt.registerOutParameter(7, 12);
      getBorrowerDetailsStmt.registerOutParameter(8, 12);
      getBorrowerDetailsStmt.registerOutParameter(9, 12);

      getBorrowerDetailsStmt.registerOutParameter(10, 12);
      getBorrowerDetailsStmt.registerOutParameter(11, 12);
      getBorrowerDetailsStmt.registerOutParameter(12, 12);
      getBorrowerDetailsStmt.registerOutParameter(13, 12);
      getBorrowerDetailsStmt.registerOutParameter(14, 12);
      getBorrowerDetailsStmt.registerOutParameter(15, 91);
      getBorrowerDetailsStmt.registerOutParameter(16, 12);
      getBorrowerDetailsStmt.registerOutParameter(17, 12);
      getBorrowerDetailsStmt.registerOutParameter(18, 4);
      getBorrowerDetailsStmt.registerOutParameter(19, 8);
      getBorrowerDetailsStmt.registerOutParameter(20, 8);
      getBorrowerDetailsStmt.registerOutParameter(21, 12);
      getBorrowerDetailsStmt.registerOutParameter(22, 12);
      getBorrowerDetailsStmt.registerOutParameter(23, 12);
      getBorrowerDetailsStmt.registerOutParameter(24, 12);
      getBorrowerDetailsStmt.registerOutParameter(25, 12);
      getBorrowerDetailsStmt.registerOutParameter(26, 12);
      getBorrowerDetailsStmt.registerOutParameter(27, 12);
      getBorrowerDetailsStmt.registerOutParameter(28, 12);
      getBorrowerDetailsStmt.registerOutParameter(29, 12);
      getBorrowerDetailsStmt.registerOutParameter(30, 12);
      getBorrowerDetailsStmt.registerOutParameter(31, 8);
      getBorrowerDetailsStmt.execute();
      getStatus = getBorrowerDetailsStmt.getInt(1);

      String exception = getBorrowerDetailsStmt.getString(32);
      if (getStatus == 0) {
        Log.log(5, "getborrowerDetails", "success dao for CGPAN SP", "--");
      }
      if (getStatus == 1) {
        Log.log(2, "GMDAO", "getborrowerDetails", "Exception " + exception);
        getBorrowerDetailsStmt.close();
        getBorrowerDetailsStmt = null;
        //System.out.println("getBorrowerDetailsForCgpan exception "+exception);
        throw new DatabaseException(exception);
      }
      ssiDetails.setBorrowerRefNo(getBorrowerDetailsStmt.getInt(6));
      borrowerDetails.setPreviouslyCovered(getBorrowerDetailsStmt.getString(7).trim());
      borrowerDetails.setAssistedByBank(getBorrowerDetailsStmt.getString(8).trim());
      borrowerDetails.setAcNo(getBorrowerDetailsStmt.getString(9));
      borrowerDetails.setNpa(getBorrowerDetailsStmt.getString(10).trim());
      ssiDetails.setConstitution(getBorrowerDetailsStmt.getString(11));
      ssiDetails.setSsiType(getBorrowerDetailsStmt.getString(12).trim());
      ssiDetails.setSsiName(getBorrowerDetailsStmt.getString(13));
      ssiDetails.setRegNo(getBorrowerDetailsStmt.getString(14));
      ssiDetails.setCommencementDate(DateHelper.sqlToUtilDate(getBorrowerDetailsStmt.getDate(15)));
      ssiDetails.setSsiITPan(getBorrowerDetailsStmt.getString(16));
      ssiDetails.setActivityType(getBorrowerDetailsStmt.getString(17));
      ssiDetails.setEmployeeNos(getBorrowerDetailsStmt.getInt(18));
      ssiDetails.setProjectedSalesTurnover(getBorrowerDetailsStmt.getDouble(19));
      ssiDetails.setProjectedExports(getBorrowerDetailsStmt.getDouble(20));
      ssiDetails.setAddress(getBorrowerDetailsStmt.getString(21));
      ssiDetails.setCity(getBorrowerDetailsStmt.getString(22));
      ssiDetails.setPincode(getBorrowerDetailsStmt.getString(23).trim());
      ssiDetails.setDistrict(getBorrowerDetailsStmt.getString(25));
      ssiDetails.setState(getBorrowerDetailsStmt.getString(26));
      ssiDetails.setIndustryNature(getBorrowerDetailsStmt.getString(27));
      ssiDetails.setIndustrySector(getBorrowerDetailsStmt.getString(28));
      ssiDetails.setCgbid(getBorrowerDetailsStmt.getString(30));
      borrowerDetails.setOsAmt(getBorrowerDetailsStmt.getDouble(31));
      borrowerDetails.setSsiDetails(ssiDetails);
      ssiRefNo = borrowerDetails.getSsiDetails().getBorrowerRefNo();

      CallableStatement getPromoterDetailsStmt = connection.prepareCall("{?=call funcGetPromoterDtlforSSIRef(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)}");

      getPromoterDetailsStmt.registerOutParameter(1, 4);
      getPromoterDetailsStmt.registerOutParameter(23, 12);

      getPromoterDetailsStmt.setInt(2, ssiRefNo);
      getPromoterDetailsStmt.registerOutParameter(3, 4);
      getPromoterDetailsStmt.registerOutParameter(4, 12);
      getPromoterDetailsStmt.registerOutParameter(5, 12);
      getPromoterDetailsStmt.registerOutParameter(6, 12);
      getPromoterDetailsStmt.registerOutParameter(7, 12);
      getPromoterDetailsStmt.registerOutParameter(8, 12);
      getPromoterDetailsStmt.registerOutParameter(9, 12);
      getPromoterDetailsStmt.registerOutParameter(10, 91);
      getPromoterDetailsStmt.registerOutParameter(11, 12);
      getPromoterDetailsStmt.registerOutParameter(12, 12);
      getPromoterDetailsStmt.registerOutParameter(13, 12);
      getPromoterDetailsStmt.registerOutParameter(14, 91);
      getPromoterDetailsStmt.registerOutParameter(15, 12);
      getPromoterDetailsStmt.registerOutParameter(16, 12);
      getPromoterDetailsStmt.registerOutParameter(17, 91);
      getPromoterDetailsStmt.registerOutParameter(18, 12);
      getPromoterDetailsStmt.registerOutParameter(19, 12);
      getPromoterDetailsStmt.registerOutParameter(20, 91);
      getPromoterDetailsStmt.registerOutParameter(21, 12);
      getPromoterDetailsStmt.registerOutParameter(22, 12);

      getPromoterDetailsStmt.executeQuery();

      String prException = getPromoterDetailsStmt.getString(23);
      int getPromoterDetailsStatus = getPromoterDetailsStmt.getInt(1);

      if (getPromoterDetailsStatus == 1)
      {
        Log.log(2, "GMDAO", "GetpromotererDtls", "Exception " + prException);
        getPromoterDetailsStmt.close();
        getPromoterDetailsStmt = null;
        //System.out.println("getBorrowerDetailsForCgpan prException "+prException);
        throw new DatabaseException(prException);
      }

      ssiDetails.setCpTitle(getPromoterDetailsStmt.getString(4));
      ssiDetails.setCpFirstName(getPromoterDetailsStmt.getString(5));
      ssiDetails.setCpMiddleName(getPromoterDetailsStmt.getString(6));
      ssiDetails.setCpLastName(getPromoterDetailsStmt.getString(7));
      ssiDetails.setCpITPAN(getPromoterDetailsStmt.getString(8));
      ssiDetails.setCpGender(getPromoterDetailsStmt.getString(9).trim());
      ssiDetails.setCpDOB(DateHelper.sqlToUtilDate(getPromoterDetailsStmt.getDate(10)));
      ssiDetails.setCpLegalID(getPromoterDetailsStmt.getString(11));
      ssiDetails.setCpLegalIdValue(getPromoterDetailsStmt.getString(12));
      ssiDetails.setFirstName(getPromoterDetailsStmt.getString(13));
      ssiDetails.setFirstDOB(DateHelper.sqlToUtilDate(getPromoterDetailsStmt.getDate(14)));
      ssiDetails.setFirstItpan(getPromoterDetailsStmt.getString(15));
      ssiDetails.setSecondName(getPromoterDetailsStmt.getString(16));
      ssiDetails.setSecondDOB(DateHelper.sqlToUtilDate(getPromoterDetailsStmt.getDate(17)));
      ssiDetails.setSecondItpan(getPromoterDetailsStmt.getString(18));
      ssiDetails.setThirdName(getPromoterDetailsStmt.getString(19));
      ssiDetails.setThirdDOB(DateHelper.sqlToUtilDate(getPromoterDetailsStmt.getDate(20)));
      ssiDetails.setThirdItpan(getPromoterDetailsStmt.getString(21));
      ssiDetails.setSocialCategory(getPromoterDetailsStmt.getString(22));
      borrowerDetails.setSsiDetails(ssiDetails);

      getBorrowerDetailsStmt.close();
      getBorrowerDetailsStmt = null;

      getPromoterDetailsStmt.close();
      getPromoterDetailsStmt = null;
    }
    catch (SQLException exception) {
      throw new DatabaseException(exception.getMessage());
    } finally {
      DBConnection.freeConnection(connection);
    }

    Log.log(4, "GMDAO", "getBorrowerDetailsForCGPAN", "Exited");

    return borrowerDetails;
  }

  public ArrayList getOutstandingDetails(String id, int type)
    throws SQLException, DatabaseException
  {
    Log.log(4, "GMDAO", "getOutstandingDetails", "Entered");

    OutstandingDetail outstandingDetail = null;

    PeriodicInfo periodicInfo = new PeriodicInfo();

    ArrayList periodicInfos = new ArrayList();

    ArrayList listOfOutstandingDetail = new ArrayList();

    Log.log(5, "GMDAO", "getOutstandingDetails", "Id passed is " + id);

    Connection connection = DBConnection.getConnection();

    CallableStatement getOutstandingDetailStmt = null;

    ResultSet resultSet = null;

    ResultSet cgpanResultSet = null;

    CallableStatement getOutDetailForCgpanStmt = null;

    String panType = null;
    int cgpanLength = 0;

    String cgpan = null;
    String appType = null;
    int len = 0;
    int size = 0;
    try
    {
      String exception = "";

      String functionName = null;

      ArrayList outDtls = new ArrayList();

      if (type == 0)
      {
        functionName = "{?=call packGetOutstandingDtls.funcGetOutStandingforBID(?,?,?)}";
        getOutstandingDetailStmt = connection.prepareCall(functionName);
        getOutstandingDetailStmt.registerOutParameter(1, 4);
        getOutstandingDetailStmt.setString(2, id);
        getOutstandingDetailStmt.registerOutParameter(3, -10);
        getOutstandingDetailStmt.registerOutParameter(4, 12);

        getOutstandingDetailStmt.execute();

        exception = getOutstandingDetailStmt.getString(4);
        int error = getOutstandingDetailStmt.getInt(1);

        if (error == 0)
        {
          Log.log(5, "GMDAO", "GET OutstandingForBID", "Success");
        }

        if (error == 1)
        {
          getOutstandingDetailStmt.close();
          getOutstandingDetailStmt = null;
          Log.log(2, "GMDAO", "getOutstandingDetailsForBID", "Exception " + exception);
          connection.rollback();
          throw new DatabaseException(exception);
        }

        resultSet = (ResultSet)getOutstandingDetailStmt.getObject(3);

        boolean firstTime = true;

        String cgpan1 = null;

        while (resultSet.next())
        {
          if (firstTime)
          {
            periodicInfo.setBorrowerId(resultSet.getString(1));
            periodicInfo.setBorrowerName(resultSet.getString(4));
            firstTime = false;
          }
          cgpan1 = resultSet.getString(2);
          Log.log(5, "GMDAO", "getOutstandingDetails", "cgpan from view : " + cgpan1);
          if (cgpan1 != null)
          {
            outstandingDetail = new OutstandingDetail();
            outstandingDetail.setCgpan(cgpan1);
            outstandingDetail.setScheme(resultSet.getString(3));
            outstandingDetail.setTcSanctionedAmount(resultSet.getDouble(5));
            outstandingDetail.setWcFBSanctionedAmount(resultSet.getDouble(6));
            outstandingDetail.setWcNFBSanctionedAmount(resultSet.getDouble(7));
            listOfOutstandingDetail.add(outstandingDetail);
          }
          Log.log(5, "GMDAO", "getOutstandingDetails", "end of one Result Set View");
        }
        resultSet.close();
        resultSet = null;

        getOutstandingDetailStmt.close();
        getOutstandingDetailStmt = null;

        size = listOfOutstandingDetail.size();
        Log.log(5, "GMDAO", "getOutstandingDetails", "size of out dtls : " + size);

        for (int i = 0; i < size; i++)
        {
          OutstandingDetail outDtl = (OutstandingDetail)listOfOutstandingDetail.get(i);
          cgpan = outDtl.getCgpan();
          Log.log(5, "GMDAO", "getOutstandingDetails", "inside for loop cgpan : " + cgpan);

          len = cgpan.length();

          appType = cgpan.substring(len - 2, len - 1);

          ArrayList outAmounts = new ArrayList();

          if (appType.equalsIgnoreCase("T"))
          {
            getOutDetailForCgpanStmt = connection.prepareCall("{?= call packGetTCOutStanding.funcTCOutStanding(?,?,?)}");
            getOutDetailForCgpanStmt.registerOutParameter(1, 4);
            getOutDetailForCgpanStmt.setString(2, cgpan);
            getOutDetailForCgpanStmt.registerOutParameter(3, -10);
            getOutDetailForCgpanStmt.registerOutParameter(4, 12);

            getOutDetailForCgpanStmt.execute();

            exception = getOutDetailForCgpanStmt.getString(4);

            error = getOutDetailForCgpanStmt.getInt(1);

            if (error == 0)
            {
              Log.log(5, "GMDAO", "GETTcOutstanding", "Success");
            }

            if (error == 1)
            {
              getOutDetailForCgpanStmt.close();
              getOutDetailForCgpanStmt = null;
              Log.log(2, "GMDAO", "GETTcOutstanding", "Exception " + exception);
              connection.rollback();
              throw new DatabaseException(exception);
            }

            cgpanResultSet = (ResultSet)getOutDetailForCgpanStmt.getObject(3);

            OutstandingAmount outAmount = null;

            while (cgpanResultSet.next())
            {
              outAmount = new OutstandingAmount();
              outAmount.setCgpan(cgpan);
              outAmount.setTcoId(cgpanResultSet.getString(1));
              outAmount.setTcPrincipalOutstandingAmount(cgpanResultSet.getDouble(2));
              outAmount.setTcOutstandingAsOnDate(cgpanResultSet.getDate(3));

              outAmounts.add(outAmount);
              Log.log(5, "GMDAO", "getOutstandingDetails", "end of one Result Set for Cgpan TC");
            }

            cgpanResultSet.close();
            cgpanResultSet = null;

            getOutDetailForCgpanStmt.close();
            getOutDetailForCgpanStmt = null;
          }
          else if ((appType.equalsIgnoreCase("W")) || (appType.equalsIgnoreCase("R")))
          {
            getOutDetailForCgpanStmt = connection.prepareCall("{?=call packGetWCOutStanding.funcWCOutStanding(?,?,?)}");
            getOutDetailForCgpanStmt.registerOutParameter(1, 4);
            Log.log(5, "GMDAO", "getOutstandingDetails", "Cgpan WC ");
            getOutDetailForCgpanStmt.setString(2, cgpan);

            getOutDetailForCgpanStmt.registerOutParameter(3, -10);
            getOutDetailForCgpanStmt.registerOutParameter(4, 12);

            getOutDetailForCgpanStmt.execute();

            exception = getOutDetailForCgpanStmt.getString(4);

            error = getOutDetailForCgpanStmt.getInt(1);

            if (error == 0)
            {
              Log.log(5, "GMDAO", "GET WC Outstanding", "Success");
            }

            if (error == 1)
            {
              getOutDetailForCgpanStmt.close();
              getOutDetailForCgpanStmt = null;
              Log.log(2, "GMDAO", "GET WC Outstanding", "Exception" + exception);
              connection.rollback();
              throw new DatabaseException(exception);
            }

            cgpanResultSet = (ResultSet)getOutDetailForCgpanStmt.getObject(3);

            OutstandingAmount outAmount = null;

            while (cgpanResultSet.next())
            {
              outAmount = new OutstandingAmount();
              outAmount.setCgpan(cgpan);

              outAmount.setWcoId(cgpanResultSet.getString(1));
              outAmount.setWcFBPrincipalOutstandingAmount(cgpanResultSet.getDouble(2));
              outAmount.setWcFBInterestOutstandingAmount(cgpanResultSet.getDouble(3));
              outAmount.setWcFBOutstandingAsOnDate(cgpanResultSet.getDate(4));
              outAmount.setWcNFBPrincipalOutstandingAmount(cgpanResultSet.getDouble(5));
              outAmount.setWcNFBInterestOutstandingAmount(cgpanResultSet.getDouble(6));
              outAmount.setWcNFBOutstandingAsOnDate(cgpanResultSet.getDate(7));
              outAmounts.add(outAmount);
              Log.log(5, "GMDAO", "getOutstandingDetails", "end of one Result Set for Cgpan WC");
            }
            cgpanResultSet.close();
            cgpanResultSet = null;

            getOutDetailForCgpanStmt.close();
            getOutDetailForCgpanStmt = null;
          }
          outDtl.setOutstandingAmounts(outAmounts);
          outDtls.add(outDtl);
        }
        periodicInfo.setOutstandingDetails(outDtls);
        Log.log(5, "GMDAO", "getOutstandingDetails", "OutDtls size " + outDtls.size());
        periodicInfos.add(periodicInfo);
      }
      else if (type == 1)
      {
        functionName = "{?=call packGetOutstandingDtls.funcGetOutStandingforCGPAN(?,?,?)}";

        getOutstandingDetailStmt = connection.prepareCall(functionName);

        getOutstandingDetailStmt.registerOutParameter(1, 4);
        getOutstandingDetailStmt.setString(2, id);
        getOutstandingDetailStmt.registerOutParameter(3, -10);
        getOutstandingDetailStmt.registerOutParameter(4, 12);

        getOutstandingDetailStmt.execute();

        exception = getOutstandingDetailStmt.getString(4);

        int error = getOutstandingDetailStmt.getInt(1);

        if (error == 0)
        {
          Log.log(5, "GMDAO", "getOutstandinForCGPAN", "Success");
        }

        if (error == 1)
        {
          getOutstandingDetailStmt.close();
          getOutstandingDetailStmt = null;
          Log.log(2, "GMDAO", "getOutstandingDetailsForCGPAN", "Exception " + exception);
          connection.rollback();
          throw new DatabaseException(exception);
        }

        resultSet = (ResultSet)getOutstandingDetailStmt.getObject(3);

        cgpanLength = id.length();

        panType = id.substring(cgpanLength - 2, cgpanLength - 1);

        if (panType.equalsIgnoreCase("T"))
        {
          while (resultSet.next())
          {
            outstandingDetail = new OutstandingDetail();

            periodicInfo.setBorrowerId(resultSet.getString(1));
            outstandingDetail.setCgpan(resultSet.getString(2));
            periodicInfo.setBorrowerName(resultSet.getString(3));
            outstandingDetail.setScheme(resultSet.getString(4));
            outstandingDetail.setTcSanctionedAmount(resultSet.getDouble(5));
            listOfOutstandingDetail.add(outstandingDetail);
          }
          resultSet.close();
          resultSet = null;

          getOutstandingDetailStmt.close();
          getOutstandingDetailStmt = null;

          size = listOfOutstandingDetail.size();

          for (int i = 0; i < size; i++)
          {
            OutstandingDetail outDtl = (OutstandingDetail)listOfOutstandingDetail.get(i);
            cgpan = outDtl.getCgpan();

            len = cgpan.length();

            appType = cgpan.substring(len - 2, len - 1);

            ArrayList outAmounts = new ArrayList();

            getOutDetailForCgpanStmt = connection.prepareCall("{?=call packGetTCOutStanding.funcTCOutStanding(?,?,?)}");
            getOutDetailForCgpanStmt.registerOutParameter(1, 4);
            getOutDetailForCgpanStmt.setString(2, cgpan);
            getOutDetailForCgpanStmt.registerOutParameter(3, -10);
            getOutDetailForCgpanStmt.registerOutParameter(4, 12);

            getOutDetailForCgpanStmt.execute();

            exception = getOutDetailForCgpanStmt.getString(4);

            error = getOutDetailForCgpanStmt.getInt(1);

            if (error == 0)
            {
              Log.log(5, "GMDAO", "GETTcOutstanding", "Success");
            }

            if (error == 1)
            {
              getOutDetailForCgpanStmt.close();
              getOutDetailForCgpanStmt = null;
              Log.log(2, "GMDAO", "GETTcOutstanding", "Exception " + exception);
              connection.rollback();
              throw new DatabaseException(exception);
            }

            cgpanResultSet = (ResultSet)getOutDetailForCgpanStmt.getObject(3);

            OutstandingAmount outAmount = null;

            while (cgpanResultSet.next())
            {
              outAmount = new OutstandingAmount();

              outAmount.setCgpan(cgpan);

              outAmount.setTcoId(cgpanResultSet.getString(1));
              outAmount.setTcPrincipalOutstandingAmount(cgpanResultSet.getDouble(2));
              outAmount.setTcOutstandingAsOnDate(cgpanResultSet.getDate(3));
              outAmounts.add(outAmount);
            }
            cgpanResultSet.close();
            cgpanResultSet = null;

            getOutDetailForCgpanStmt.close();
            getOutDetailForCgpanStmt = null;

            outDtl.setOutstandingAmounts(outAmounts);
            outDtls.add(outDtl);
          }
        }
        else if (panType.equalsIgnoreCase("W"))
        {
          while (resultSet.next())
          {
            outstandingDetail = new OutstandingDetail();

            periodicInfo.setBorrowerId(resultSet.getString(1));
            outstandingDetail.setCgpan(resultSet.getString(2));
            periodicInfo.setBorrowerName(resultSet.getString(3));
            outstandingDetail.setScheme(resultSet.getString(4));
            outstandingDetail.setWcFBSanctionedAmount(resultSet.getDouble(5));
            outstandingDetail.setWcNFBSanctionedAmount(resultSet.getDouble(6));

            listOfOutstandingDetail.add(outstandingDetail);
          }
          resultSet.close();
          resultSet = null;

          getOutstandingDetailStmt.close();
          getOutstandingDetailStmt = null;

          size = listOfOutstandingDetail.size();

          for (int i = 0; i < size; i++)
          {
            OutstandingDetail outDtl = (OutstandingDetail)listOfOutstandingDetail.get(i);
            cgpan = outDtl.getCgpan();

            len = cgpan.length();

            appType = cgpan.substring(len - 2, len - 1);

            ArrayList outAmounts = new ArrayList();

            getOutDetailForCgpanStmt = connection.prepareCall("{?=call packGetWCOutStanding.funcWCOutStanding(?,?,?)}");
            getOutDetailForCgpanStmt.registerOutParameter(1, 4);
            getOutDetailForCgpanStmt.setString(2, cgpan);
            getOutDetailForCgpanStmt.registerOutParameter(3, -10);
            getOutDetailForCgpanStmt.registerOutParameter(4, 12);

            getOutDetailForCgpanStmt.execute();

            exception = getOutDetailForCgpanStmt.getString(4);

            error = getOutDetailForCgpanStmt.getInt(1);

            if (error == 0)
            {
              Log.log(5, "GMDAO", "GETWcOutstanding", "Success");
            }

            if (error == 1)
            {
              getOutDetailForCgpanStmt.close();
              getOutDetailForCgpanStmt = null;
              Log.log(2, "GMDAO", "GETWcOutstanding", "Exception " + exception);
              connection.rollback();
              throw new DatabaseException(exception);
            }

            cgpanResultSet = (ResultSet)getOutDetailForCgpanStmt.getObject(3);

            OutstandingAmount outAmount = null;

            while (cgpanResultSet.next())
            {
              outAmount = new OutstandingAmount();
              outAmount.setCgpan(cgpan);
              outAmount.setWcoId(cgpanResultSet.getString(1));
              outAmount.setWcFBPrincipalOutstandingAmount(cgpanResultSet.getDouble(2));
              outAmount.setWcFBInterestOutstandingAmount(cgpanResultSet.getDouble(3));
              outAmount.setWcFBOutstandingAsOnDate(cgpanResultSet.getDate(4));
              outAmount.setWcNFBPrincipalOutstandingAmount(cgpanResultSet.getDouble(5));
              outAmount.setWcNFBInterestOutstandingAmount(cgpanResultSet.getDouble(6));
              outAmount.setWcNFBOutstandingAsOnDate(cgpanResultSet.getDate(7));

              outAmounts.add(outAmount);
            }

            cgpanResultSet.close();
            cgpanResultSet = null;

            getOutDetailForCgpanStmt.close();
            getOutDetailForCgpanStmt = null;

            outDtl.setOutstandingAmounts(outAmounts);
            outDtls.add(outDtl);
          }
        }
        periodicInfo.setOutstandingDetails(outDtls);
        periodicInfos.add(periodicInfo);
      }
      else if (type == 2)
      {
        functionName = "{?=call packGetOutstandingDtls.funcGetOutStandingforBorr(?,?,?)}";
        getOutstandingDetailStmt = connection.prepareCall(functionName);
        getOutstandingDetailStmt.registerOutParameter(1, 4);

        getOutstandingDetailStmt.setString(2, id);
        getOutstandingDetailStmt.registerOutParameter(3, -10);
        getOutstandingDetailStmt.registerOutParameter(4, 12);

        getOutstandingDetailStmt.execute();

        exception = getOutstandingDetailStmt.getString(4);

        int error = getOutstandingDetailStmt.getInt(1);

        if (error == 0)
        {
          Log.log(5, "GMDAO", "GET OTS For Borr Name SP", "Success");
        }

        if (error == 1)
        {
          getOutstandingDetailStmt.close();
          getOutstandingDetailStmt = null;
          Log.log(2, "GMDAO", "getOutstandingDetailsForBorrower", "Exception " + exception);
          connection.rollback();
          throw new DatabaseException(exception);
        }

        resultSet = (ResultSet)getOutstandingDetailStmt.getObject(3);

        boolean firstTime = true;

        String cgpan1 = null;

        while (resultSet.next())
        {
          outstandingDetail = new OutstandingDetail();

          if (firstTime)
          {
            periodicInfo.setBorrowerId(resultSet.getString(1));
            periodicInfo.setBorrowerName(resultSet.getString(4));
            firstTime = false;
          }
          cgpan1 = resultSet.getString(2);
          if (cgpan1 != null)
          {
            outstandingDetail.setCgpan(cgpan1);
            outstandingDetail.setScheme(resultSet.getString(3));
            outstandingDetail.setTcSanctionedAmount(resultSet.getDouble(5));
            outstandingDetail.setWcFBSanctionedAmount(resultSet.getDouble(6));
            outstandingDetail.setWcNFBSanctionedAmount(resultSet.getDouble(7));

            listOfOutstandingDetail.add(outstandingDetail);
          }
        }
        resultSet.close();
        resultSet = null;

        getOutstandingDetailStmt.close();
        getOutstandingDetailStmt = null;

        size = listOfOutstandingDetail.size();

        for (int i = 0; i < size; i++)
        {
          OutstandingDetail outDtl = (OutstandingDetail)listOfOutstandingDetail.get(i);
          cgpan = outDtl.getCgpan();

          len = cgpan.length();

          appType = cgpan.substring(len - 2, len - 1);

          ArrayList outAmounts = new ArrayList();

          if (appType.equalsIgnoreCase("T"))
          {
            getOutDetailForCgpanStmt = connection.prepareCall("{?=call packGetTCOutStanding.funcTCOutStanding(?,?,?)}");
            getOutDetailForCgpanStmt.registerOutParameter(1, 4);
            getOutDetailForCgpanStmt.setString(2, cgpan);
            getOutDetailForCgpanStmt.registerOutParameter(3, -10);
            getOutDetailForCgpanStmt.registerOutParameter(4, 12);

            getOutDetailForCgpanStmt.execute();

            exception = getOutDetailForCgpanStmt.getString(4);

            error = getOutDetailForCgpanStmt.getInt(1);

            if (error == 0)
            {
              Log.log(5, "GMDAO", "GETTcOutstanding", "Success");
            }

            if (error == 1)
            {
              getOutDetailForCgpanStmt.close();
              getOutDetailForCgpanStmt = null;
              Log.log(2, "GMDAO", "GETTcOutstanding", "Exception " + exception);
              connection.rollback();
              throw new DatabaseException(exception);
            }

            cgpanResultSet = (ResultSet)getOutDetailForCgpanStmt.getObject(3);
            OutstandingAmount outAmount = null;

            while (cgpanResultSet.next())
            {
              outAmount = new OutstandingAmount();
              outAmount.setCgpan(cgpan);
              outAmount.setTcoId(cgpanResultSet.getString(1));
              outAmount.setTcPrincipalOutstandingAmount(cgpanResultSet.getDouble(2));
              outAmount.setTcOutstandingAsOnDate(cgpanResultSet.getDate(3));
              outAmounts.add(outAmount);
            }
            cgpanResultSet.close();
            cgpanResultSet = null;

            getOutDetailForCgpanStmt.close();
            getOutDetailForCgpanStmt = null;
          }
          else if ((appType.equalsIgnoreCase("W")) || (appType.equalsIgnoreCase("R")))
          {
            getOutDetailForCgpanStmt = connection.prepareCall("{?=call packGetWCOutStanding.funcWCOutStanding(?,?,?)}");
            getOutDetailForCgpanStmt.registerOutParameter(1, 4);
            getOutDetailForCgpanStmt.setString(2, cgpan);
            getOutDetailForCgpanStmt.registerOutParameter(3, -10);
            getOutDetailForCgpanStmt.registerOutParameter(4, 12);

            getOutDetailForCgpanStmt.execute();

            exception = getOutDetailForCgpanStmt.getString(4);

            error = getOutDetailForCgpanStmt.getInt(1);

            if (error == 0)
            {
              Log.log(5, "GMDAO", "GET WC Outstanding", "Success");
            }
            if (error == 1)
            {
              getOutDetailForCgpanStmt.close();
              getOutDetailForCgpanStmt = null;
              Log.log(2, "GMDAO", "GET WC Outstanding", "Exception " + exception);
              connection.rollback();
              throw new DatabaseException(exception);
            }

            cgpanResultSet = (ResultSet)getOutDetailForCgpanStmt.getObject(3);

            OutstandingAmount outAmount = null;

            while (cgpanResultSet.next())
            {
              outAmount = new OutstandingAmount();

              outAmount.setCgpan(cgpan);

              outAmount.setWcoId(cgpanResultSet.getString(1));
              outAmount.setWcFBPrincipalOutstandingAmount(cgpanResultSet.getDouble(2));
              outAmount.setWcFBInterestOutstandingAmount(cgpanResultSet.getDouble(3));
              outAmount.setWcFBOutstandingAsOnDate(cgpanResultSet.getDate(4));
              outAmount.setWcNFBPrincipalOutstandingAmount(cgpanResultSet.getDouble(5));
              outAmount.setWcNFBInterestOutstandingAmount(cgpanResultSet.getDouble(6));
              outAmount.setWcNFBOutstandingAsOnDate(cgpanResultSet.getDate(7));
              outAmounts.add(outAmount);
            }
            cgpanResultSet.close();
            cgpanResultSet = null;

            getOutDetailForCgpanStmt.close();
            getOutDetailForCgpanStmt = null;
          }
          outDtl.setOutstandingAmounts(outAmounts);
          outDtls.add(outDtl);
        }
        periodicInfo.setOutstandingDetails(outDtls);
        periodicInfos.add(periodicInfo);
      }
      connection.commit();
    }
    catch (SQLException exception) {
      Log.logException(exception);
      throw new DatabaseException(exception.getMessage());
    }
    finally
    {
      DBConnection.freeConnection(connection);
    }
    Log.log(4, "GMDAO", "getOutstandingDetails", "Exited");

    return periodicInfos;
  }

  public boolean insertTcOutstandingDetails(OutstandingAmount outstandingAmount)
    throws SQLException, DatabaseException
  {
    Log.log(4, "GMDAO", "insertTcOutstandingDetails", "Entered:");
    Connection connection = DBConnection.getConnection();
    CallableStatement insertOutstandingDetailsStmt = null;
    int updateStatus = 0;

    boolean updateOutstandingStatus = false;

    java.util.Date utilDate = null;
    java.sql.Date sqlDate = null;

    String exception = null;

    String cgpan = outstandingAmount.getCgpan();
    int cgpanLen = cgpan.length();
    String cgpanType = cgpan.substring(cgpanLen - 2, cgpanLen - 1);
    Log.log(5, "GMDAO", "insertTcOutstandingDetails", "cgpan :" + cgpan);
    try
    {
      if (outstandingAmount != null)
      {
        if (cgpanType.equalsIgnoreCase("T"))
        {
          sqlDate = new java.sql.Date(0L);
          utilDate = new java.util.Date();

          insertOutstandingDetailsStmt = connection.prepareCall("{?=call funcInsertTCOutStanding(?,?,?,?)}");

          insertOutstandingDetailsStmt.registerOutParameter(1, 4);

          insertOutstandingDetailsStmt.setString(2, outstandingAmount.getCgpan());

          insertOutstandingDetailsStmt.setDouble(3, outstandingAmount.getTcPrincipalOutstandingAmount());

          Log.log(5, "GMDAO", "insertTcOutstandingDetails", "Tc Amt :" + outstandingAmount.getTcPrincipalOutstandingAmount());

          utilDate = outstandingAmount.getTcOutstandingAsOnDate();
          sqlDate = new java.sql.Date(utilDate.getTime());

          Log.log(5, "GMDAO", "insertTcOutstandingDetails", "Tc Date :" + sqlDate);
          insertOutstandingDetailsStmt.setDate(4, sqlDate);

          insertOutstandingDetailsStmt.registerOutParameter(5, 12);

          insertOutstandingDetailsStmt.executeQuery();

          updateStatus = insertOutstandingDetailsStmt.getInt(1);

          exception = insertOutstandingDetailsStmt.getString(5);

          if (updateStatus == 0)
          {
            updateOutstandingStatus = true;
            Log.log(5, "GMDAO", "insertOutstandingDetailsTC", "Success");
          }
          else if (updateStatus == 1)
          {
            updateOutstandingStatus = false;
            insertOutstandingDetailsStmt.close();
            insertOutstandingDetailsStmt = null;
            Log.log(2, "GMDAO", "insertOutstandingDetailsTC", "Error " + exception);
            connection.rollback();
            throw new DatabaseException(exception);
          }
          insertOutstandingDetailsStmt.close();
          insertOutstandingDetailsStmt = null;
          connection.commit();
        }
      }
    }
    catch (Exception sqlexception) {
      Log.logException(sqlexception);
      try
      {
        connection.rollback();
      }
      catch (SQLException localSQLException) {
      }
      throw new DatabaseException(sqlexception.getMessage());
    } finally {
      DBConnection.freeConnection(connection);
    }
    Log.log(4, "GMDAO", "insertTcOutstandingDetails", "Exited");

    return updateOutstandingStatus;
  }

  public boolean insertWcOutstandingDetails(OutstandingAmount outstandingAmount)
    throws SQLException, DatabaseException
  {
    Log.log(4, "GMDAO", "insertWcOutstandingDetails", "Entered:");
    Connection connection = DBConnection.getConnection();
    CallableStatement updateOutstandingDetailsStmt = null;
    int updateStatus = 0;

    boolean updateOutstandingStatus = false;

    java.util.Date utilDate = null;
    java.sql.Date sqlDate = null;

    String exception = null;

    String cgpan = outstandingAmount.getCgpan();
    int cgpanLen = cgpan.length();
    String cgpanType = cgpan.substring(cgpanLen - 2, cgpanLen - 1);

    Log.log(5, "GMDAO", "insertWcOutstandingDetails", "cgpan :" + cgpan);
    try
    {
      if (outstandingAmount != null)
      {
        if ((cgpanType.equalsIgnoreCase("W")) || (cgpanType.equalsIgnoreCase("R")))
        {
          updateOutstandingDetailsStmt = connection.prepareCall("{?=call funcInsertWCOutStanding(?,?,?,?,?,?,?,?)}");

          updateOutstandingDetailsStmt.registerOutParameter(1, 4);

          updateOutstandingDetailsStmt.setString(2, outstandingAmount.getCgpan());

          updateOutstandingDetailsStmt.setDouble(3, outstandingAmount.getWcFBPrincipalOutstandingAmount());

          Log.log(5, "GMDAO", "insertWcOutstandingDetails", "Wc FB Pr Amt :" + outstandingAmount.getWcFBPrincipalOutstandingAmount());

          updateOutstandingDetailsStmt.setDouble(4, outstandingAmount.getWcFBInterestOutstandingAmount());

          utilDate = outstandingAmount.getWcFBOutstandingAsOnDate();
          if ((utilDate != null) && (!utilDate.toString().equals("")))
          {
            sqlDate = new java.sql.Date(utilDate.getTime());
            updateOutstandingDetailsStmt.setDate(5, sqlDate);
          }
          else
          {
            updateOutstandingDetailsStmt.setNull(5, 91);
          }

          Log.log(5, "GMDAO", "insertWcOutstandingDetails", "wc Fb date :" + sqlDate);

          updateOutstandingDetailsStmt.setDouble(6, outstandingAmount.getWcNFBPrincipalOutstandingAmount());

          updateOutstandingDetailsStmt.setDouble(7, outstandingAmount.getWcNFBInterestOutstandingAmount());

          utilDate = outstandingAmount.getWcNFBOutstandingAsOnDate();

          if ((utilDate != null) && (!utilDate.toString().equals("")))
          {
            sqlDate = new java.sql.Date(utilDate.getTime());

            updateOutstandingDetailsStmt.setDate(8, sqlDate);
          }
          else {
            updateOutstandingDetailsStmt.setNull(8, 91);
          }

          Log.log(5, "GMDAO", "insertWcOutstandingDetails", "wc NFb date :" + sqlDate);

          updateOutstandingDetailsStmt.registerOutParameter(9, 12);

          updateOutstandingDetailsStmt.executeQuery();
          updateStatus = Integer.parseInt(updateOutstandingDetailsStmt.getObject(1).toString());

          exception = updateOutstandingDetailsStmt.getString(9);

          if (updateStatus == 0) {
            updateOutstandingStatus = true;
            Log.log(5, "GMDAO", "insertWcOutstandingDetailsWC", "Success");
          }
          else if (updateStatus == 1) {
            updateOutstandingStatus = false;
            updateOutstandingDetailsStmt.close();
            updateOutstandingDetailsStmt = null;
            Log.log(2, "GMDAO", "insertWcOutstandingDetailsWC", "Exception :" + exception);
            connection.rollback();
            throw new DatabaseException(exception);
          }
          updateOutstandingDetailsStmt.close();
          updateOutstandingDetailsStmt = null;
          connection.commit();
        }
      }
    }
    catch (Exception sqlexception) {
      Log.logException(sqlexception);
      try
      {
        connection.rollback();
      }
      catch (SQLException localSQLException) {
      }
      throw new DatabaseException(sqlexception.getMessage());
    } finally {
      DBConnection.freeConnection(connection);
    }
    Log.log(4, "GMDAO", "insertWcOutstandingDetails", "Exited");

    return updateOutstandingStatus;
  }

  public boolean updateTcOutstandingDetails(OutstandingAmount outstandingAmount)
    throws SQLException, DatabaseException
  {
    Log.log(4, "GMDAO", "updateTcOutstandingDetails", "Entered:");
    Connection connection = DBConnection.getConnection();
    CallableStatement updateOutstandingDetailsStmt = null;
    int updateStatus = 0;

    boolean updateOutstandingStatus = false;

    java.util.Date utilDate = null;
    java.sql.Date sqlDate = null;

    String exception = null;

    String cgpan = outstandingAmount.getCgpan();
    int cgpanLen = cgpan.length();
    String cgpanType = cgpan.substring(cgpanLen - 2, cgpanLen - 1);
    Log.log(5, "GMDAO", "updateTcOutstandingDetails", "cgpan :" + cgpan);
    try
    {
      if (outstandingAmount != null)
      {
        if (cgpanType.equalsIgnoreCase("T"))
        {
          sqlDate = new java.sql.Date(0L);
          utilDate = new java.util.Date();

          updateOutstandingDetailsStmt = connection.prepareCall("{?=call funcUpdTCOutStanding(?,?,?,?)}");

          updateOutstandingDetailsStmt.registerOutParameter(1, 4);

          updateOutstandingDetailsStmt.setString(2, outstandingAmount.getTcoId());

          Log.log(5, "GMDAO", "updateTcOutstandingDetails", "Tc ID :" + outstandingAmount.getTcoId());

          updateOutstandingDetailsStmt.setDouble(3, outstandingAmount.getTcPrincipalOutstandingAmount());

          Log.log(5, "GMDAO", "updateTcOutstandingDetails", "Tc Amt :" + outstandingAmount.getTcPrincipalOutstandingAmount());

          utilDate = outstandingAmount.getTcOutstandingAsOnDate();
          sqlDate = new java.sql.Date(utilDate.getTime());

          Log.log(5, "GMDAO", "updateTcOutstandingDetails", "tc date :" + sqlDate);
          updateOutstandingDetailsStmt.setDate(4, sqlDate);

          updateOutstandingDetailsStmt.registerOutParameter(5, 12);

          updateOutstandingDetailsStmt.executeQuery();

          updateStatus = updateOutstandingDetailsStmt.getInt(1);
          exception = updateOutstandingDetailsStmt.getString(5);

          if (updateStatus == 0) {
            updateOutstandingStatus = true;
            Log.log(5, "GMDAO", "updateOutstandingDetailsFor TC", "Success");
          }
          else if (updateStatus == 1) {
            updateOutstandingStatus = false;
            updateOutstandingDetailsStmt.close();
            updateOutstandingDetailsStmt = null;
            Log.log(2, "GMDAO", "updateOutstandingDetailsFor TC", "Exception " + exception);
            connection.rollback();
            throw new DatabaseException(exception);
          }
          updateOutstandingDetailsStmt.close();
          updateOutstandingDetailsStmt = null;
          connection.commit();
        }
      }
    } catch (Exception sqlexception) {
      Log.logException(sqlexception);
      try
      {
        connection.rollback();
      }
      catch (SQLException localSQLException) {
      }
      throw new DatabaseException(sqlexception.getMessage());
    } finally {
      DBConnection.freeConnection(connection);
    }
    Log.log(4, "GMDAO", "updateTcOutstandingDetails", "Exited");

    return updateOutstandingStatus;
  }

  public boolean updateWcOutstandingDetails(OutstandingAmount outstandingAmount)
    throws SQLException, DatabaseException
  {
    Log.log(4, "GMDAO", "updateWcOutstandingDetails", "Entered:");
    Connection connection = DBConnection.getConnection();
    CallableStatement updateOutstandingDetailsStmt = null;
    int updateStatus = 0;

    boolean updateOutstandingStatus = false;

    java.util.Date utilDate = null;
    java.sql.Date sqlDate = null;

    String cgpan = outstandingAmount.getCgpan();
    int cgpanLen = cgpan.length();
    String cgpanType = cgpan.substring(cgpanLen - 2, cgpanLen - 1);
    Log.log(5, "GMDAO", "updateWcOutstandingDetails", "cgpan :" + cgpan);
    try
    {
      if (outstandingAmount != null)
      {
        if ((cgpanType.equalsIgnoreCase("W")) || (cgpanType.equalsIgnoreCase("W")))
        {
          updateOutstandingDetailsStmt = connection.prepareCall("{?=call funcUpdWCOutStanding(?,?,?,?,?,?,?,?)}");

          updateOutstandingDetailsStmt.registerOutParameter(1, 4);

          updateOutstandingDetailsStmt.setString(2, outstandingAmount.getWcoId());

          Log.log(5, "GMDAO", "updateWcOutstandingDetails", "Wc ID *:" + outstandingAmount.getWcoId());

          updateOutstandingDetailsStmt.setDouble(3, outstandingAmount.getWcFBPrincipalOutstandingAmount());

          Log.log(5, "GMDAO", "updateWcOutstandingDetails", "Wc FB Pr Amt *:" + outstandingAmount.getWcFBPrincipalOutstandingAmount());

          updateOutstandingDetailsStmt.setDouble(4, outstandingAmount.getWcFBInterestOutstandingAmount());

          Log.log(5, "GMDAO", "updateWcOutstandingDetails", "Wc FB Int Amt *:" + outstandingAmount.getWcFBInterestOutstandingAmount());

          utilDate = outstandingAmount.getWcFBOutstandingAsOnDate();
          if (utilDate != null)
          {
            sqlDate = new java.sql.Date(utilDate.getTime());
            Log.log(5, "GMDAO", "updateOutstandingDetails", "wc Fb date *:" + sqlDate);
            updateOutstandingDetailsStmt.setDate(5, sqlDate);
          }
          else
          {
            Log.log(5, "GMDAO", "updateOutstandingDetails", "wc Fb date *: null");
            updateOutstandingDetailsStmt.setNull(5, 91);
          }

          Log.log(5, "GMDAO", "updateOutstandingDetails", "wc Fb date *:" + sqlDate);
          updateOutstandingDetailsStmt.setDate(5, sqlDate);

          Log.log(5, "GMDAO", "updateOutstandingDetails", "wc Fb date* :" + outstandingAmount.getWcNFBPrincipalOutstandingAmount());
          updateOutstandingDetailsStmt.setDouble(6, outstandingAmount.getWcNFBPrincipalOutstandingAmount());

          Log.log(5, "GMDAO", "updateOutstandingDetails", "wc Fb date *:" + outstandingAmount.getWcNFBInterestOutstandingAmount());
          updateOutstandingDetailsStmt.setDouble(7, outstandingAmount.getWcNFBInterestOutstandingAmount());

          Log.log(5, "GMDAO", "updateOutstandingDetails", "wc Fb date* :" + outstandingAmount.getWcNFBOutstandingAsOnDate());
          utilDate = outstandingAmount.getWcNFBOutstandingAsOnDate();
          if (utilDate != null)
          {
            sqlDate = new java.sql.Date(utilDate.getTime());
            Log.log(5, "GMDAO--", "out--", "wc NFb date *:" + sqlDate);
            updateOutstandingDetailsStmt.setDate(8, sqlDate);
          }
          else
          {
            Log.log(5, "GMDAO--", "out--", "wc NFb date *:null");
            updateOutstandingDetailsStmt.setNull(8, 91);
          }

          updateOutstandingDetailsStmt.registerOutParameter(9, 12);

          updateOutstandingDetailsStmt.executeQuery();

          updateStatus = Integer.parseInt(updateOutstandingDetailsStmt.getObject(1).toString());

          String exception = updateOutstandingDetailsStmt.getString(9);
          if (updateStatus == 0) {
            updateOutstandingStatus = true;
            Log.log(5, "GMDAO", "updateWcOutstandingDetails", "Success");
          }
          else if (updateStatus == 1) {
            updateOutstandingStatus = false;
            updateOutstandingDetailsStmt.close();
            updateOutstandingDetailsStmt = null;
            Log.log(2, "GMDAO", "updateWcOutstandingDetails", "Exception " + exception);
            connection.rollback();
            throw new DatabaseException(exception);
          }
          updateOutstandingDetailsStmt.close();
          updateOutstandingDetailsStmt = null;
          connection.commit();
        }
      }
    }
    catch (Exception sqlexception) {
      Log.logException(sqlexception);
      try
      {
        connection.rollback();
      }
      catch (SQLException localSQLException) {
      }
      throw new DatabaseException(sqlexception.getMessage());
    } finally {
      DBConnection.freeConnection(connection);
    }
    Log.log(4, "GMDAO", "updateWcOutstandingDetails", "Exited");

    return updateOutstandingStatus;
  }

  public ArrayList getDisbursementDetails(String id, int type)
    throws DatabaseException
  {
    Log.log(4, "GMDAO", "getDisbursementDetails", "Entered");

    ArrayList periodicInfos = new ArrayList();

    Connection connection = DBConnection.getConnection();

    CallableStatement getDisbursementDetailStmt = null;

    ResultSet resultSet = null;
    try
    {
      String exception = "";

      String functionName = null;

      if (type == 0)
      {
        functionName = "{?=call packGetDtlsforDBR.funcGetDtlsForBid(?,?,?)}";
      }
      else if (type == 1)
      {
        functionName = "{?=call packGetDtlsforDBR.funcGetDtlsForCGPAN(?,?,?)}";
      } else if (type == 2)
      {
        functionName = "{?=call packGetDtlsforDBR.funcGetDtlsForBorrower(?,?,?)}";
      }

      getDisbursementDetailStmt = connection.prepareCall(functionName);
      getDisbursementDetailStmt.registerOutParameter(1, 4);
      getDisbursementDetailStmt.setString(2, id);
      getDisbursementDetailStmt.registerOutParameter(3, -10);
      getDisbursementDetailStmt.registerOutParameter(4, 12);

      getDisbursementDetailStmt.executeQuery();

      exception = getDisbursementDetailStmt.getString(4);

      int error = getDisbursementDetailStmt.getInt(1);

      if (error == 1)
      {
        getDisbursementDetailStmt.close();
        getDisbursementDetailStmt = null;
        Log.log(5, "GMDAO", "getDisbursementDetails", "Exception :" + exception);
        connection.rollback();
        throw new DatabaseException(exception);
      }
      resultSet = (ResultSet)getDisbursementDetailStmt.getObject(3);

      PeriodicInfo periodicInfo = new PeriodicInfo();
      Disbursement disbursement = null;
      ArrayList listOfDisbursement = new ArrayList();
      boolean firstTime = true;

      while (resultSet.next())
      {
        disbursement = new Disbursement();

        if (firstTime)
        {
          periodicInfo.setBorrowerId(resultSet.getString(1));
          periodicInfo.setBorrowerName(resultSet.getString(4));
          firstTime = false;
        }

        disbursement.setCgpan(resultSet.getString(2));

        disbursement.setScheme(resultSet.getString(3));

        disbursement.setSanctionedAmount(resultSet.getDouble(5));

        listOfDisbursement.add(disbursement);
      }

      resultSet.close();
      resultSet = null;

      getDisbursementDetailStmt.close();
      getDisbursementDetailStmt = null;

      String cgpan = null;

      int disbSize = listOfDisbursement.size();
      functionName = "{?=call packGetPIDBRDtlsCGPAN.funcDBRDetailsForCGPAN(?,?,?)}";
      getDisbursementDetailStmt = connection.prepareCall(functionName);
      for (int i = 0; i < disbSize; i++) {
        ArrayList listOfDisbursementAmount = new ArrayList();
        disbursement = (Disbursement)listOfDisbursement.get(i);
        cgpan = disbursement.getCgpan();
        if (cgpan != null)
        {
          Log.log(5, "GMDAO", "getDisbursementDetails", "Cgpan" + cgpan);
          getDisbursementDetailStmt.registerOutParameter(1, 4);
          getDisbursementDetailStmt.setString(2, cgpan);

          getDisbursementDetailStmt.registerOutParameter(3, -10);
          getDisbursementDetailStmt.registerOutParameter(4, 12);

          getDisbursementDetailStmt.execute();

          exception = getDisbursementDetailStmt.getString(4);

          error = getDisbursementDetailStmt.getInt(1);
          if (error == 1)
          {
            getDisbursementDetailStmt.close();
            getDisbursementDetailStmt = null;
            Log.log(2, "GMDAO", "getDisbursementDetails", "Exception" + exception);
            connection.rollback();
            throw new DatabaseException(exception);
          }
          resultSet = (ResultSet)getDisbursementDetailStmt.getObject(3);
          DisbursementAmount disbursementAmount = null;

          while (resultSet.next())
          {
            disbursementAmount = new DisbursementAmount();

            disbursementAmount.setCgpan(cgpan);

            disbursementAmount.setDisbursementId(resultSet.getString(1));
            Log.log(5, "GMDAO", "getDisbursementDetails", "disb Id" + disbursementAmount.getDisbursementId());

            disbursementAmount.setDisbursementAmount(resultSet.getDouble(2));
            Log.log(5, "GMDAO", "getDisbursementDetails", "disb Amt" + disbursementAmount.getDisbursementAmount());

            disbursementAmount.setDisbursementDate(DateHelper.sqlToUtilDate(resultSet.getDate(3)));
            Log.log(5, "GMDAO", "getDisbursementDetails", "disb date" + disbursementAmount.getDisbursementDate());

            disbursementAmount.setFinalDisbursement(resultSet.getString(4));
            Log.log(5, "GMDAO", "getDisbursementDetails", "Fin disb " + disbursementAmount.getFinalDisbursement());

            listOfDisbursementAmount.add(disbursementAmount);
          }

          disbursement.setDisbursementAmounts(listOfDisbursementAmount);
          resultSet.close();
          resultSet = null;
        }
      }

      getDisbursementDetailStmt.close();
      getDisbursementDetailStmt = null;

      periodicInfo.setDisbursementDetails(listOfDisbursement);
      periodicInfos.add(periodicInfo);

      connection.commit();
    }
    catch (Exception exception) {
      Log.logException(exception);
      try
      {
        connection.rollback();
      }
      catch (SQLException localSQLException) {
      }
      throw new DatabaseException(exception.getMessage());
    }
    finally
    {
      DBConnection.freeConnection(connection);
    }

    Log.log(4, "GMDAO", "get disbursementDetails", "Exited");

    return periodicInfos;
  }

  public ArrayList getRepaymentDetails(String id, int type)
    throws DatabaseException
  {
    Log.log(4, "GMDAO", "get Repayment Details", "Entered");

    ArrayList periodicInfos = new ArrayList();

    Connection connection = DBConnection.getConnection();
    CallableStatement getRepaymentDetailStmt = null;
    ResultSet resultSet = null;
    try
    {
      String exception = "";
      String functionName = null;

      if (type == 0)
      {
        functionName = "{?=call packGetDtlsforRepayment.funcGetDtlsforBID(?,?,?)}";
      }
      else if (type == 1)
      {
        functionName = "{?=call packGetDtlsforRepayment.funcGetDtlsforCGPAN(?,?,?)}";
      } else if (type == 2) {
        functionName = "{?=call packGetDtlsforRepayment.funcGetDtlsforBorrower(?,?,?)}";
      }

      getRepaymentDetailStmt = connection.prepareCall(functionName);
      getRepaymentDetailStmt.registerOutParameter(1, 4);
      getRepaymentDetailStmt.setString(2, id);
      getRepaymentDetailStmt.registerOutParameter(3, -10);
      getRepaymentDetailStmt.registerOutParameter(4, 12);

      getRepaymentDetailStmt.executeQuery();

      exception = getRepaymentDetailStmt.getString(4);
      Log.log(5, "GMDAO", "repayment detail", "exception " + exception);
      int error = getRepaymentDetailStmt.getInt(1);
      Log.log(5, "GMDAO", "repayment detail", "errorCode " + error);
      if (error == 1)
      {
        getRepaymentDetailStmt.close();
        getRepaymentDetailStmt = null;
        connection.rollback();
        Log.log(2, "GMDAO", "getRepaymentdetail", "error in SP " + exception);
        throw new DatabaseException(exception);
      }
      Log.log(5, "GMDAO", "getRepaymentdetail", "Before ResultSet assign");
      resultSet = (ResultSet)getRepaymentDetailStmt.getObject(3);
      Log.log(5, "GMDAO", "getRepaymentdetail", "resultSet assigned");

      PeriodicInfo periodicInfo = new PeriodicInfo();
      Repayment repayment = null;

      ArrayList listOfRepayment = new ArrayList();

      boolean firstTime = true;
      String tcCgpan = null;
      int len = 0;
      String applType = null;

      while (resultSet.next())
      {
        Log.log(5, "GMDAO", "getRepaymentdetail", "Inside ResultSet");
        repayment = new Repayment();
        tcCgpan = resultSet.getString(2);
        len = tcCgpan.length();
        applType = tcCgpan.substring(len - 2, len - 1);
        if (applType.equalsIgnoreCase("T"))
        {
          if (firstTime)
          {
            periodicInfo.setBorrowerId(resultSet.getString(1));
            Log.log(5, "getRepaymentDetails for Borrower", "Borrower ID", " : " + periodicInfo.getBorrowerId());

            periodicInfo.setBorrowerName(resultSet.getString(3));
            Log.log(5, "getRepaymentDetailsfor Borrower:", "Borrower Name", " : " + periodicInfo.getBorrowerName());
            firstTime = false;
          }

          repayment.setCgpan(tcCgpan);
          Log.log(5, "getRepaymentDetailsfor Borrower:", "CGPAN ", ": " + repayment.getCgpan());
          repayment.setScheme(resultSet.getString(4));
          Log.log(5, "getRepaymentDetailsfor Borrower:", "Scheme", " : " + repayment.getScheme());

          listOfRepayment.add(repayment);
        }
      }

      Log.log(5, "getRepaymentDetails for Borrower:", "size of RepaymentObj", " : " + listOfRepayment.size());

      resultSet.close();
      resultSet = null;

      getRepaymentDetailStmt = null;

      functionName = "{?=call packGetRepaymentDtls.funcGetRepaymentDtl(?,?,?)}";
      getRepaymentDetailStmt = connection.prepareCall(functionName);

      String cgpan = "";
      int size = listOfRepayment.size();
      for (int i = 0; i < size; i++) {
        ArrayList listOfRepaymentAmount = new ArrayList();
        repayment = (Repayment)listOfRepayment.get(i);
        cgpan = repayment.getCgpan();
        Log.log(5, "getRepaymentDetails for cgpan:", "cgpan", " : " + i + " " + cgpan);
        getRepaymentDetailStmt.registerOutParameter(1, 4);
        getRepaymentDetailStmt.setString(2, cgpan);

        getRepaymentDetailStmt.registerOutParameter(3, -10);
        getRepaymentDetailStmt.registerOutParameter(4, 12);

        getRepaymentDetailStmt.execute();

        exception = getRepaymentDetailStmt.getString(4);

        error = getRepaymentDetailStmt.getInt(1);
        if (error == 1)
        {
          getRepaymentDetailStmt.close();
          getRepaymentDetailStmt = null;
          Log.log(2, "getRepaymentDetails for cgpan:", "Exception ", exception);
          connection.rollback();
          throw new DatabaseException(exception);
        }
        resultSet = (ResultSet)getRepaymentDetailStmt.getObject(3);
        RepaymentAmount repaymentAmount = null;
        while (resultSet.next())
        {
          repaymentAmount = new RepaymentAmount();

          repaymentAmount.setCgpan(cgpan);
          Log.log(5, "GMDAO", "RepaymentAmount", "Cgpan " + cgpan);

          repaymentAmount.setRepayId(resultSet.getString(1));
          Log.log(5, "GMDAO", "RepaymentAmount", "RepaymentId " + repaymentAmount.getRepayId());

          repaymentAmount.setRepaymentAmount(resultSet.getDouble(2));
          Log.log(5, "rep Amt: ", "rpAmount", "--" + repaymentAmount.getRepaymentAmount());

          repaymentAmount.setRepaymentDate(resultSet.getDate(3));
          Log.log(5, "rep date:", "date", " " + repaymentAmount.getRepaymentDate());

          listOfRepaymentAmount.add(repaymentAmount);
          Log.log(5, "************", "***********", "****************");
        }
        repayment.setRepaymentAmounts(listOfRepaymentAmount);
        resultSet.close();
        resultSet = null;
      }
      periodicInfo.setRepaymentDetails(listOfRepayment);

      periodicInfos.add(periodicInfo);

      getRepaymentDetailStmt.close();
      getRepaymentDetailStmt = null;

      connection.commit();
    }
    catch (Exception exception) {
      Log.logException(exception);
      try
      {
        connection.rollback();
      }
      catch (SQLException localSQLException) {
      }
      throw new DatabaseException(exception.getMessage());
    }
    finally
    {
      DBConnection.freeConnection(connection);
    }

    Log.log(4, "GMDAO", "get Repayment Details", "Exited");

    return periodicInfos;
  }

  public ArrayList getRepaymentSchedule(String id, int type)
    throws DatabaseException
  {
    Log.log(4, "GMDAO", "get Repayment Scedule", "Entered");
    ArrayList listOfRepaymentSchedule = new ArrayList();

    RepaymentSchedule repaymentSchedule = null;

    Connection connection = DBConnection.getConnection();

    CallableStatement getRepaymentScheduleStmt = null;
    ResultSet resultSet = null;
    String cgpan1 = null;

    ArrayList repaySchedules = new ArrayList();
    try
    {
      String exception = "";
      String functionName = null;

      if (type == 0)
      {
        functionName = "{?=call packGetDtlsforRepayment.funcGetDtlsforBID(?,?,?)}";
      }
      else if (type == 1)
      {
        functionName = "{?=call packGetDtlsforRepayment.funcGetDtlsforCGPAN(?,?,?)}";
      } else if (type == 2) {
        functionName = "{?=call packGetDtlsforRepayment.funcGetDtlsforBorrower(?,?,?)}";
      }

      getRepaymentScheduleStmt = connection.prepareCall(functionName);
      getRepaymentScheduleStmt.registerOutParameter(1, 4);

      getRepaymentScheduleStmt.setString(2, id);
      getRepaymentScheduleStmt.registerOutParameter(3, -10);
      getRepaymentScheduleStmt.registerOutParameter(4, 12);

      getRepaymentScheduleStmt.executeQuery();

      exception = getRepaymentScheduleStmt.getString(4);

      int error = getRepaymentScheduleStmt.getInt(1);

      if (error == 1)
      {
        getRepaymentScheduleStmt.close();
        getRepaymentScheduleStmt = null;
        Log.log(2, "GMDAO", "getRepaymentSchedule", "Exception " + exception);
        connection.rollback();
        throw new DatabaseException(exception);
      }
      resultSet = (ResultSet)getRepaymentScheduleStmt.getObject(3);

      while (resultSet.next())
      {
        cgpan1 = resultSet.getString(2);
        if (cgpan1 != null)
        {
          repaymentSchedule = new RepaymentSchedule();
          repaymentSchedule.setBorrowerId(resultSet.getString(1));
          repaymentSchedule.setBorrowerName(resultSet.getString(3));
          repaymentSchedule.setCgpan(cgpan1);
          repaymentSchedule.setScheme(resultSet.getString(4));
          listOfRepaymentSchedule.add(repaymentSchedule);
        }
      }

      Log.log(5, "GMDAO", "getRepaymentSchedule", "size of the ;list" + listOfRepaymentSchedule.size());

      resultSet.close();
      resultSet = null;

      getRepaymentScheduleStmt.close();
      getRepaymentScheduleStmt = null;

      int size = listOfRepaymentSchedule.size();
      RepaymentSchedule repaySchedule = null;
      RepaymentSchedule rpSchedule = null;
      String cgpan = null;
      boolean firstTime = true;
      for (int i = 0; i < size; i++)
      {
        repaySchedule = (RepaymentSchedule)listOfRepaymentSchedule.get(i);

        cgpan = repaySchedule.getCgpan();

        Log.log(5, "GMDAO", "getRepaymentSchedule", "Cgpan == " + cgpan);
        if (cgpan != null)
        {
          getRepaymentScheduleStmt = connection.prepareCall("{?=call funcGetRepayScheduleForCGPAN(?,?,?,?,?,?)}");

          getRepaymentScheduleStmt.registerOutParameter(1, 4);
          getRepaymentScheduleStmt.registerOutParameter(7, 12);

          getRepaymentScheduleStmt.setString(2, cgpan);
          getRepaymentScheduleStmt.registerOutParameter(3, 4);
          getRepaymentScheduleStmt.registerOutParameter(4, 91);
          getRepaymentScheduleStmt.registerOutParameter(5, 4);
          getRepaymentScheduleStmt.registerOutParameter(6, 4);

          getRepaymentScheduleStmt.execute();
          int errorCode = getRepaymentScheduleStmt.getInt(1);

          String repayError = getRepaymentScheduleStmt.getString(7);

          Log.log(5, "GMDAO", "getRepaymentSchedule", "errorCode, error " + errorCode + "," + repayError);

          if (errorCode == 0)
          {
            Log.log(5, "GMDAO", "getRepaymentSchedule", "SP is SUCCESS for cgpan");
          }
          if (errorCode == 1)
          {
            getRepaymentScheduleStmt.close();
            getRepaymentScheduleStmt = null;
            Log.log(2, "GMDAO", "getRepaymentSchedule", "Exception " + repayError);
            connection.rollback();
          }
          else
          {
            rpSchedule = new RepaymentSchedule();

            if (firstTime)
            {
              rpSchedule.setBorrowerId(repaySchedule.getBorrowerId());
              rpSchedule.setBorrowerName(repaySchedule.getBorrowerName());
              firstTime = false;
            }
            rpSchedule.setCgpan(repaySchedule.getCgpan());
            rpSchedule.setScheme(repaySchedule.getScheme());
            rpSchedule.setMoratorium(getRepaymentScheduleStmt.getInt(3));
            rpSchedule.setFirstInstallmentDueDate(getRepaymentScheduleStmt.getDate(4));

            rpSchedule.setPeriodicity(getRepaymentScheduleStmt.getString(5));
            rpSchedule.setNoOfInstallment(getRepaymentScheduleStmt.getInt(6));
            repaySchedules.add(rpSchedule);
          }
        }

      }

      connection.commit();
    } catch (Exception e) {
      Log.log(2, "GMDAO", "getRepaymentSchedule", e.getMessage());
      Log.logException(e);
      try
      {
        connection.rollback();
      }
      catch (SQLException localSQLException) {
      }
      throw new DatabaseException("Unable to get RepaySchedule Details");
    } finally {
      DBConnection.freeConnection(connection);
    }

    Log.log(4, "GMDAO", "getRepaymentSchedule", "Exited");

    return repaySchedules;
  }

  public boolean updateRepaymentSchedule(RepaymentSchedule repaymentSchedule, String userId)
    throws DatabaseException
  {
    Log.log(4, "GMDAO", "update Repayment Scedule", "Entered");
    Connection connection = DBConnection.getConnection();
    CallableStatement updateRepaymentScheduleStmt = null;
    int updateStatus = 0;

    boolean updateRepaymentScheduleStatus = false;

    if (repaymentSchedule != null)
    {
      try
      {
        updateRepaymentScheduleStmt = connection.prepareCall("{?=call funcUpdateRepaymentSchedule (?,?,?,?,?,?,?)}");

        updateRepaymentScheduleStmt.registerOutParameter(1, 4);

        updateRepaymentScheduleStmt.registerOutParameter(8, 12);

        updateRepaymentScheduleStmt.setString(2, repaymentSchedule.getCgpan());

        updateRepaymentScheduleStmt.setDouble(3, repaymentSchedule.getMoratorium());

        java.util.Date utilDate = repaymentSchedule.getFirstInstallmentDueDate();
        java.sql.Date sqlDate = new java.sql.Date(utilDate.getTime());

        updateRepaymentScheduleStmt.setDate(4, sqlDate);
        updateRepaymentScheduleStmt.setString(5, repaymentSchedule.getPeriodicity());

        updateRepaymentScheduleStmt.setDouble(6, repaymentSchedule.getNoOfInstallment());

        updateRepaymentScheduleStmt.setString(7, userId);

        updateRepaymentScheduleStmt.executeQuery();
        updateStatus = Integer.parseInt(updateRepaymentScheduleStmt.getObject(1).toString());

        String exception = updateRepaymentScheduleStmt.getString(8);

        if (updateStatus == 0)
        {
          Log.log(5, "updateRepaymentSchedule", "SP ", "SUCCESS");
          updateRepaymentScheduleStatus = true;
        }
        else if (updateStatus == 1)
        {
          updateRepaymentScheduleStmt.close();
          updateRepaymentScheduleStmt = null;
          Log.log(2, "updateRepaymentSchedule", "Exception ", exception);
          updateRepaymentScheduleStatus = false;
          connection.rollback();
          throw new DatabaseException(exception);
        }
        updateRepaymentScheduleStmt.close();
        updateRepaymentScheduleStmt = null;
        connection.commit();
      }
      catch (Exception exception)
      {
        Log.logException(exception);
        try
        {
          connection.rollback();
        }
        catch (SQLException localSQLException) {
        }
        throw new DatabaseException(exception.getMessage());
      }
      finally {
        DBConnection.freeConnection(connection);
      }
    }
    Log.log(4, "GMDAO", "update Repayment Scedule", "Exited");

    return updateRepaymentScheduleStatus;
  }

  public HashMap getClosureDetails(String id, int type, String memberId)
    throws DatabaseException
  {
    Log.log(4, "GMDAO", "get Closure Details", "Entered");
    Connection connection = DBConnection.getConnection();
    Log.log(5, "GMDAO", "get Closure Details", "ID : " + id);

    HashMap closureDetails = new HashMap();
    HashMap borrowerInfos = new HashMap();

    ClosureDetail closureDetail = null;
    BorrowerInfo borrowerInfo = null;
    CgpanInfo cgpanInfo = null;

    String borrowerId = "";
    String borrowerName = "";

    CallableStatement getClosureDetailStmt = null;
    ResultSet closureResultSet = null;
    ResultSet tempClosureResultSet = null;
    try
    {
      String exception = "";

      String functionName = null;

      if (type == 0)
      {
        functionName = "{?=call packGetDtlsforClosure.funcGetClosureforBID(?,?,?,?,?,?)}";
        getClosureDetailStmt = connection.prepareCall(functionName);
        getClosureDetailStmt.registerOutParameter(1, 4);
        Log.log(5, "GMDAO", "get Closure Details", "in closure dao -- the Id " + id);
        getClosureDetailStmt.setString(2, id);
        getClosureDetailStmt.setString(3, memberId.substring(0, 4));
        getClosureDetailStmt.setString(4, memberId.substring(4, 8));
        getClosureDetailStmt.setString(5, memberId.substring(8, 12));
        getClosureDetailStmt.registerOutParameter(6, -10);
        getClosureDetailStmt.registerOutParameter(7, 12);

        getClosureDetailStmt.execute();

        exception = getClosureDetailStmt.getString(7);
        int error = getClosureDetailStmt.getInt(1);

        if (error == 0) {
          Log.log(5, "GMDAO", "get Closure Details For Bid", "Success");
        }

        if (error == 1)
        {
          getClosureDetailStmt.close();
          getClosureDetailStmt = null;
          Log.log(5, "GMDAO", "getClosureDetailsForBid", "Exception " + exception);
          connection.rollback();
          throw new DatabaseException(exception);
        }

        closureResultSet = (ResultSet)getClosureDetailStmt.getObject(6);
      }
      else if (type == 1)
      {
        functionName = "{?=call packGetDtlsforClosure.funcGetClosureforCGPAN(?,?,?)}";
        getClosureDetailStmt = connection.prepareCall(functionName);
        getClosureDetailStmt.registerOutParameter(1, 4);
        Log.log(5, "GMDAO", "get Closure Details", "in closure dao -- the Id " + id);
        getClosureDetailStmt.setString(2, id);
        getClosureDetailStmt.registerOutParameter(3, -10);
        getClosureDetailStmt.registerOutParameter(4, 12);

        getClosureDetailStmt.execute();

        exception = getClosureDetailStmt.getString(4);
        int error = getClosureDetailStmt.getInt(1);

        if (error == 0)
        {
          Log.log(5, "GMDAO", "get Closure Details For Cgpan", "Success");
        }

        if (error == 1)
        {
          getClosureDetailStmt.close();
          getClosureDetailStmt = null;
          Log.log(2, "GMDAO", "getClosureDetailsForCgpan", "Exception " + exception);
          connection.rollback();
          throw new DatabaseException(exception);
        }

        closureResultSet = (ResultSet)getClosureDetailStmt.getObject(3);
      }
      else if (type == 2)
      {
        Log.log(5, "GMDAO", "get Closure Details", "in closure dao -- the Id " + id + "type " + type);
        functionName = "{?=call packGetDtlsforClosure.funcGetClosureforBor(?,?,?,?,?,?)}";
        getClosureDetailStmt = connection.prepareCall(functionName);

        getClosureDetailStmt.registerOutParameter(1, 4);

        getClosureDetailStmt.setString(2, id);
        getClosureDetailStmt.setString(3, memberId.substring(0, 4));
        getClosureDetailStmt.setString(4, memberId.substring(4, 8));
        getClosureDetailStmt.setString(5, memberId.substring(8, 12));
        getClosureDetailStmt.registerOutParameter(6, -10);
        getClosureDetailStmt.registerOutParameter(7, 12);

        getClosureDetailStmt.execute();

        exception = getClosureDetailStmt.getString(7);

        int error = getClosureDetailStmt.getInt(1);

        if (error == 0)
        {
          Log.log(5, "GMDAO", "get Closure Details For Borrower", "Success");
        }

        if (error == 1)
        {
          getClosureDetailStmt.close();
          getClosureDetailStmt = null;
          Log.log(2, "GMDAO", "getClosureDetailsForBorrower", "Exception " + exception);
          connection.rollback();
          throw new DatabaseException(exception);
        }

        closureResultSet = (ResultSet)getClosureDetailStmt.getObject(6);
        Log.log(5, "GMDAO", "get Closure Details For Borrower", "resultset--rows" + closureResultSet.getFetchSize());
      }

      int i = 0;

      ArrayList cgpanInfos = new ArrayList();
      closureDetail = new ClosureDetail();
      while (closureResultSet.next())
      {
        borrowerInfo = new BorrowerInfo();
        cgpanInfo = new CgpanInfo();

        closureDetail.setMemberId(closureResultSet.getString(1));
        Log.log(5, "GMDAO", "get Closure Details", " closure:member ID : " + closureDetail.getMemberId());
        memberId = closureDetail.getMemberId();

        borrowerInfo.setBorrowerId(closureResultSet.getString(2));
        Log.log(5, "GMDAO", "get Closure Details", " closure:borr ID : " + borrowerInfo.getBorrowerId());
        borrowerId = borrowerInfo.getBorrowerId();

        borrowerInfo.setBorrowerName(closureResultSet.getString(4));
        Log.log(5, "GMDAO", "get Closure Details", " closure :BName: " + borrowerInfo.getBorrowerName());
        borrowerName = borrowerInfo.getBorrowerName();

        cgpanInfo.setCgpan(closureResultSet.getString(3));
        Log.log(5, "GMDAO", "get Closure Details", " closure :cgpan: " + cgpanInfo.getCgpan());

        cgpanInfo.setScheme(closureResultSet.getString(5));
        Log.log(5, "GMDAO", "get Closure Details", " closure :Scheme : " + cgpanInfo.getScheme());

        cgpanInfo.setSanctionedAmount(closureResultSet.getDouble(6));
        Log.log(5, "GMDAO", "get Closure Details", " closure :Amt : " + cgpanInfo.getSanctionedAmount());

        if (closureDetails.containsKey(memberId))
        {
          borrowerInfos = (HashMap)closureDetails.get(memberId);
          if (borrowerInfos.containsKey(borrowerId))
          {
            borrowerInfo = (BorrowerInfo)borrowerInfos.get(borrowerId);
            cgpanInfos = borrowerInfo.getCgpanInfos();
            cgpanInfos.add(cgpanInfo);
          }
          else
          {
            cgpanInfos = new ArrayList();
            cgpanInfos.add(cgpanInfo);
          }
          borrowerInfo.setCgpanInfos(cgpanInfos);
          borrowerInfos.put(borrowerId, borrowerInfo);
        }
        else
        {
          borrowerInfos = new HashMap();
          cgpanInfos = new ArrayList();
          cgpanInfos.add(cgpanInfo);

          borrowerInfo.setCgpanInfos(cgpanInfos);
          borrowerInfos.put(borrowerId, borrowerInfo);
        }

        closureDetails.put(memberId, borrowerInfos);
        i++;
      }
      closureResultSet.close();
      closureResultSet = null;

      getClosureDetailStmt.close();
      getClosureDetailStmt = null;

      connection.commit();
    }
    catch (Exception exception) {
      Log.logException(exception);
      try
      {
        connection.rollback();
      }
      catch (SQLException localSQLException) {
      }
      throw new DatabaseException(exception.getMessage());
    }
    finally
    {
      DBConnection.freeConnection(connection);
    }
    Log.log(4, "GMDAO", "get Closure Details", "Exited");

    return closureDetails;
  }

  public HashMap getClosureDetailsForFeeNotPaid(String cgpan)
    throws DatabaseException
  {
    Log.log(4, "GMDAO", "getClosureDetailsForFeeNotPaid", "Entered");

    Connection connection = DBConnection.getConnection();
    Log.log(5, "GMDAO", "getClosureDetailsForFeeNotPaid", "cgpan : " + cgpan);

    HashMap closureDetails = new HashMap();
    HashMap borrowerInfos = new HashMap();

    ClosureDetail closureDetail = new ClosureDetail();

    BorrowerInfo borrowerInfo = new BorrowerInfo();

    String memberId = null;
    String borrowerId = null;
    String borrowerName = null;
    double sancAmt = 0.0D;
    String scheme = null;

    CallableStatement getClosureDetailStmt = null;
    ResultSet closureResultSet = null;
    try
    {
      String exception = "";

      String functionName = null;

      functionName = "{?=call packGetDtlsforClosure.funcGetClosureforCGPAN(?,?,?)}";
      getClosureDetailStmt = connection.prepareCall(functionName);
      getClosureDetailStmt.registerOutParameter(1, 4);

      getClosureDetailStmt.setString(2, cgpan);
      getClosureDetailStmt.registerOutParameter(3, -10);
      getClosureDetailStmt.registerOutParameter(4, 12);

      getClosureDetailStmt.execute();

      exception = getClosureDetailStmt.getString(4);
      int error = getClosureDetailStmt.getInt(1);

      if (error == 0)
      {
        Log.log(5, "GMDAO", "getClosureDetailsForFeeNotPaid", "Success");
      }

      if (error == 1)
      {
        getClosureDetailStmt.close();
        getClosureDetailStmt = null;
        Log.log(2, "GMDAO", "getClosureDetailsForFeeNotPaid", "Exception " + exception);
        connection.rollback();
        throw new DatabaseException(exception);
      }

      closureResultSet = (ResultSet)getClosureDetailStmt.getObject(3);

      ArrayList cgpanInfos = new ArrayList();

      while (closureResultSet.next())
      {
        CgpanInfo cgpanInfo = new CgpanInfo();

        Log.log(5, "GMDAO", "getClosureDetailsForFeeNotPaid", "cgpan " + closureResultSet.getString(3));
        memberId = closureResultSet.getString(1);
        Log.log(5, "GMDAO", "getClosureDetailsForFeeNotPaid", "memId" + memberId);
        borrowerId = closureResultSet.getString(2);
        Log.log(5, "GMDAO", "getClosureDetailsForFeeNotPaid", "bId " + borrowerId);
        borrowerName = closureResultSet.getString(4);
        Log.log(5, "GMDAO", "getClosureDetailsForFeeNotPaid", "bname " + borrowerName);
        scheme = closureResultSet.getString(5);
        Log.log(5, "GMDAO", "getClosureDetailsForFeeNotPaid", "scheme " + scheme);
        sancAmt = closureResultSet.getDouble(6);
        Log.log(5, "GMDAO", "getClosureDetailsForFeeNotPaid", "sancAmt " + sancAmt);

        closureDetail.setMemberId(memberId);

        borrowerInfo.setBorrowerId(borrowerId);
        borrowerInfo.setBorrowerName(borrowerName);

        cgpanInfo.setCgpan(cgpan);
        cgpanInfo.setScheme(scheme);
        cgpanInfo.setSanctionedAmount(sancAmt);

        cgpanInfos.add(cgpanInfo);

        borrowerInfo.setCgpanInfos(cgpanInfos);

        borrowerInfos.put(borrowerId, borrowerInfo);

        closureDetails.put(memberId, borrowerInfos);
      }
      closureResultSet.close();
      closureResultSet = null;
      getClosureDetailStmt.close();
      getClosureDetailStmt = null;
    }
    catch (Exception exception)
    {
      Log.logException(exception);
      try
      {
        connection.rollback();
      }
      catch (SQLException localSQLException)
      {
      }
      throw new DatabaseException(exception.getMessage());
    }
    finally
    {
      DBConnection.freeConnection(connection);
    }

    Log.log(4, "GMDAO", "getClosureDetailsForFeeNotPaid", "Exited");

    return closureDetails;
  }

  public ArrayList getAllReasonsForClosure() throws DatabaseException
  {
    Log.log(4, "GMDAO", "getClosureReasons", "Entered");

    Connection connection = DBConnection.getConnection();

    CallableStatement getClosureDetailStmt = null;
    ResultSet closureResultSet = null;
    ArrayList closureReasons = null;
    try
    {
      String exception = "";

      getClosureDetailStmt = connection.prepareCall("{?=call packGetClosureReasons.funcGetClosureReasons(?,?)}");

      getClosureDetailStmt.registerOutParameter(1, 4);
      getClosureDetailStmt.registerOutParameter(2, -10);
      getClosureDetailStmt.registerOutParameter(3, 12);

      getClosureDetailStmt.execute();

      String reasonException = getClosureDetailStmt.getString(3);
      int reasonError = getClosureDetailStmt.getInt(1);

      if (reasonError == 0) {
        Log.log(5, "GMDAO", "get Closure Reasons", "Success");
      }

      if (reasonError == 1)
      {
        getClosureDetailStmt.close();
        getClosureDetailStmt = null;
        Log.log(2, "GMDAO", "getClosureReasons", "Exception " + reasonException);
        connection.rollback();
        throw new DatabaseException(reasonException);
      }

      closureResultSet = (ResultSet)getClosureDetailStmt.getObject(2);
      String reason = null;
      closureReasons = new ArrayList();
      while (closureResultSet.next()) {
        reason = closureResultSet.getString(1);
        if (reason != null)
        {
          Log.log(5, "GMDAO", "getClosureReasons", "inside While");
          closureReasons.add(reason);
        }
      }

      closureResultSet.close();
      closureResultSet = null;

      getClosureDetailStmt.close();
      getClosureDetailStmt = null;

      connection.commit();
    } catch (Exception exception) {
      throw new DatabaseException(exception.getMessage());
    } finally {
      DBConnection.freeConnection(connection);
    }
    Log.log(4, "GMDAO", "getClosureReasons", "Exited");

    return closureReasons;
  }

  public NPADetails getNPADetails(String borrowerId)
    throws DatabaseException
  {
    Log.log(4, "GMDAO", "getNPADetails", "Entered");

    Connection connection = DBConnection.getConnection();
    CallableStatement getNPADetailStmt = null;
    ResultSet resultSet = null;

    NPADetails npaDetail = null;
    try
    {
      Log.log(5, "GMDAO", "get NPA Details For Bid", "test");

      getNPADetailStmt = connection.prepareCall("{?=call packGetNPADetail.funcGetNPADetail(?,?,?)}");

      getNPADetailStmt.registerOutParameter(1, 4);
      getNPADetailStmt.setString(2, borrowerId);

      getNPADetailStmt.registerOutParameter(3, -10);

      getNPADetailStmt.registerOutParameter(4, 12);

      getNPADetailStmt.execute();

      Log.log(5, "GMDAO", "get NPA Details For Bid", "test");

      resultSet = (ResultSet)getNPADetailStmt.getObject(3);
      int error = getNPADetailStmt.getInt(1);
      String exception = getNPADetailStmt.getString(4);
   //   Log.log(2, "GMDAO", "get NPA Details For Bid", "Exception " + error);

      if (error == 0) {
        Log.log(5, "GMDAO", "get NPA Details For Bid", "Success");
      }

      if (error == 1)
      {
        getNPADetailStmt.close();
        getNPADetailStmt = null;
        Log.log(2, "GMDAO", "get NPA Details For Bid", "Exception " + exception);
        connection.rollback();
        throw new DatabaseException(exception);
      }

      LegalSuitDetail legalSuitDetail = null;
      boolean isNpaAvailable = false;
      while (resultSet.next())
      {
        Log.log(5, "GMDAO", "getNPADetails", "inside result set isNpaAvailable = true");
        isNpaAvailable = true;
        npaDetail = new NPADetails();
        legalSuitDetail = new LegalSuitDetail();

        npaDetail.setNpaId(resultSet.getString(1));
        npaDetail.setCgbid(resultSet.getString(2));
        npaDetail.setNpaDate(resultSet.getDate(3));
        npaDetail.setWhetherNPAReported(resultSet.getString(4));
        npaDetail.setReportingDate(resultSet.getDate(6));
        npaDetail.setReference(resultSet.getString(7));
        npaDetail.setOsAmtOnNPA(resultSet.getDouble(8));
        npaDetail.setNpaReason(resultSet.getString(9));
        npaDetail.setEffortsTaken(resultSet.getString(10));
        Log.log(5, "GMDAO", "getNPADetails", "Efforts " + npaDetail.getEffortsTaken());

        npaDetail.setIsRecoveryInitiated(resultSet.getString(11));
        npaDetail.setNoOfActions(resultSet.getInt(12));
        npaDetail.setEffortsConclusionDate(resultSet.getDate(13));
        npaDetail.setMliCommentOnFinPosition(resultSet.getString(15));
        npaDetail.setDetailsOfFinAssistance(resultSet.getString(16));
        npaDetail.setCreditSupport(resultSet.getString(17));
        npaDetail.setBankFacilityDetail(resultSet.getString(18));
        npaDetail.setWillfulDefaulter(resultSet.getString(19));
        npaDetail.setPlaceUnderWatchList(resultSet.getString(20));
        npaDetail.setRemarksOnNpa(resultSet.getString(24));
        Log.log(5, "GMDAO", "getNPADetails", "Remarks" + npaDetail.getRemarksOnNpa());

        legalSuitDetail.setLegalSuiteNo(resultSet.getString(25));
        legalSuitDetail.setCourtName(resultSet.getString(26));
        legalSuitDetail.setForumName(resultSet.getString(27));
        legalSuitDetail.setLocation(resultSet.getString(28));
        legalSuitDetail.setDtOfFilingLegalSuit(resultSet.getDate(29));
        legalSuitDetail.setAmountClaimed(resultSet.getDouble(30));
        legalSuitDetail.setCurrentStatus(resultSet.getString(31));
        legalSuitDetail.setRecoveryProceedingsConcluded(resultSet.getString(32));

        npaDetail.setLegalSuitDetail(legalSuitDetail);
        
          //added on 08-11-2013   
             npaDetail.setIsAsPerRBI(resultSet.getString(33));
             npaDetail.setNpaConfirm(resultSet.getString(34));
             npaDetail.setEffortsTaken(resultSet.getString(35));
             npaDetail.setIsAcctReconstructed(resultSet.getString(36));
             npaDetail.setSubsidyFlag(resultSet.getString(37));
             npaDetail.setIsSubsidyRcvd(resultSet.getString(38));
             npaDetail.setIsSubsidyAdjusted(resultSet.getString(39));
             npaDetail.setSubsidyLastRcvdAmt(resultSet.getDouble(40));
             npaDetail.setSubsidyLastRcvdDt(resultSet.getDate(41));
             npaDetail.setLastInspectionDt(resultSet.getDate(42));
             npaDetail.setNpaCreatedDate(resultSet.getDate(43));
      }
      getNPADetailStmt.close();
      getNPADetailStmt = null;

      resultSet.close();
      resultSet = null;

      if ((npaDetail != null) && (npaDetail.getIsRecoveryInitiated().equals("Y")))
      {
        Log.log(5, "GMDAO", "getNPADetails", "isNpaAvailable = true getRecoveryAxn");

        getNPADetailStmt = connection.prepareCall("{?=call packGetRecoveryAxn.funcGetRecoveryAxn(?,?,?)}");

        getNPADetailStmt.registerOutParameter(1, 4);

        getNPADetailStmt.setString(2, npaDetail.getNpaId());

        getNPADetailStmt.registerOutParameter(3, -10);
        getNPADetailStmt.registerOutParameter(4, 12);

        getNPADetailStmt.execute();

        resultSet = (ResultSet)getNPADetailStmt.getObject(3);

        int recerror = getNPADetailStmt.getInt(1);
        String recexception = getNPADetailStmt.getString(4);

        if (recerror == 0)
        {
          Log.log(5, "GMDAO", "get RecoveryActionDetails For Bid", "Success");
        }

        if (recerror == 1)
        {
          getNPADetailStmt.close();
          getNPADetailStmt = null;
          Log.log(2, "GMDAO", "get RecoveryAction Details For Bid", "Exception " + recexception);
          connection.rollback();
          throw new DatabaseException(exception);
        }

        ArrayList recoProcs = new ArrayList();

        while (resultSet.next())
        {
          Log.log(5, "GMDAO", "get NPA Details..getRecoveryActionDetailsForBid", "Inside Result Set");
          RecoveryProcedure recoProc = new RecoveryProcedure();
          Log.log(5, "GMDAO", "Test", "new R");
          recoProc.setActionType(resultSet.getString(1));
          Log.log(5, "GMDAO", "Test", "1");
          recoProc.setRadId(resultSet.getString(2));
          Log.log(5, "GMDAO", "Test", "2");
          recoProc.setActionDetails(resultSet.getString(3));
          Log.log(5, "GMDAO", "Test", "3");
          recoProc.setActionDate(resultSet.getDate(4));
          Log.log(5, "GMDAO", "Test", "4");
          recoProc.setAttachmentName(resultSet.getString(5));
          Log.log(5, "GMDAO", "Test", "5");
          recoProcs.add(recoProc);
          Log.log(5, "GMDAO", "Test", "one while");
        }
        Log.log(5, "GMDAO", "getRecoveryActionDetailsForBid", "Size is " + recoProcs.size());
        resultSet.close();
        resultSet = null;

        getNPADetailStmt.close();
        getNPADetailStmt = null;
        connection.commit();
        npaDetail.setRecoveryProcedure(recoProcs);
      }
    }
    catch (Exception exception) {
    	
    //	Log.log(2, "GMDAO", "getRecoveryActionDetailsForBid", "exception " + exception.getMessage());
      try {
        connection.rollback();
      }
      catch (SQLException localSQLException) {
    	 // Log.log(2, "GMDAO", "getRecoveryActionDetailsForBid", "localSQLException " + localSQLException.getMessage());
      }
      throw new DatabaseException(exception.getMessage());
    } finally {
      DBConnection.freeConnection(connection);
    }
    Log.log(4, "GMDAO", "getNPADetails", "Exited");

    return npaDetail;
  }

  public boolean updateRecoveryDetails(Recovery recovery)
    throws DatabaseException
  {
    Log.log(4, "GMDAO", "update Recovery Details", "Entered");
    Connection connection = DBConnection.getConnection();
    CallableStatement updateRecoveryDetailsStmt = null;
    int updateStatus = 0;

    boolean updateRecoveryStatus = false;

    if (recovery != null) {
      try {
        java.sql.Date sqlDate = new java.sql.Date(0L);
        java.util.Date utilDate = new java.util.Date();

        updateRecoveryDetailsStmt = connection.prepareCall("{?=call funcInsertRecoveryDtl (?,?,?,?,?,?,?,?,?)}");

        updateRecoveryDetailsStmt.registerOutParameter(1, 4);
        updateRecoveryDetailsStmt.registerOutParameter(10, 12);

        updateRecoveryDetailsStmt.setString(2, recovery.getCgbid());
        Log.log(4, "GMDAO", "update Recovery Details", "recovery.getCgbid() :" + recovery.getCgbid());

        updateRecoveryDetailsStmt.setDouble(3, recovery.getAmountRecovered());

        Log.log(4, "GMDAO", "update Recovery Details", "recovery.getAmountRecovered() :" + recovery.getAmountRecovered());

        utilDate = recovery.getDateOfRecovery();

        if (utilDate != null)
        {
          updateRecoveryDetailsStmt.setDate(4, new java.sql.Date(utilDate.getTime()));
        }
        else {
          updateRecoveryDetailsStmt.setDate(4, null);
        }

        updateRecoveryDetailsStmt.setDouble(5, recovery.getLegalCharges());

        updateRecoveryDetailsStmt.setString(6, recovery.getIsRecoveryByOTS());

        Log.log(4, "GMDAO", "update Recovery Details", "recovery.getIsRecoveryByOTS() :" + recovery.getIsRecoveryByOTS());

        updateRecoveryDetailsStmt.setString(7, recovery.getIsRecoveryBySaleOfAsset());

        updateRecoveryDetailsStmt.setString(8, recovery.getDetailsOfAssetSold());

        updateRecoveryDetailsStmt.setString(9, recovery.getRemarks());

        updateRecoveryDetailsStmt.executeQuery();

        String exception = updateRecoveryDetailsStmt.getString(10);
        updateStatus = updateRecoveryDetailsStmt.getInt(1);

        if (updateStatus == 0) {
          Log.log(5, "GMDAO", "updateRecoveryDetails", "SUCCESS SP ");
        }

        if (updateStatus == 1)
        {
          updateRecoveryDetailsStmt.close();
          updateRecoveryDetailsStmt = null;
          Log.log(2, "GMDAO", "updateRecoveryDetails", "Exception " + exception);
          connection.rollback();
          throw new DatabaseException(exception);
        }
        updateRecoveryDetailsStmt.close();
        updateRecoveryDetailsStmt = null;
        connection.commit();
      }
      catch (Exception exception)
      {
        Log.logException(exception);
        try
        {
          connection.rollback();
        }
        catch (SQLException localSQLException) {
        }
        throw new DatabaseException(exception.getMessage());
      } finally {
        DBConnection.freeConnection(connection);
      }
    }
    Log.log(4, "GMDAO", "update Recovery Details", "Exited");

    return updateRecoveryStatus;
  }

  public boolean modifyRecoveryDetails(Recovery recovery)
    throws DatabaseException
  {
    Log.log(4, "GMDAO", "modifyRecoveryDetails", "Entered");
    Connection connection = DBConnection.getConnection();
    CallableStatement updateRecoveryDetailsStmt = null;
    int updateStatus = 0;

    boolean updateRecoveryStatus = false;

    if (recovery != null)
    {
      try
      {
        updateRecoveryDetailsStmt = connection.prepareCall("{?=call funcUpdRecDtl (?,?,?,?,?,?,?,?,?)}");

        updateRecoveryDetailsStmt.registerOutParameter(1, 4);
        updateRecoveryDetailsStmt.registerOutParameter(10, 12);

        updateRecoveryDetailsStmt.setString(2, recovery.getRecoveryNo());

        updateRecoveryDetailsStmt.setDouble(3, recovery.getAmountRecovered());

        java.util.Date recDate = recovery.getDateOfRecovery();
        if (recDate != null) {
          updateRecoveryDetailsStmt.setDate(4, new java.sql.Date(recDate.getTime()));
        }
        else {
          updateRecoveryDetailsStmt.setDate(4, null);
        }

        updateRecoveryDetailsStmt.setDouble(5, recovery.getLegalCharges());

        updateRecoveryDetailsStmt.setString(6, recovery.getIsRecoveryByOTS());

        updateRecoveryDetailsStmt.setString(7, recovery.getIsRecoveryBySaleOfAsset());

        updateRecoveryDetailsStmt.setString(8, recovery.getDetailsOfAssetSold());

        updateRecoveryDetailsStmt.setString(9, recovery.getRemarks());

        updateRecoveryDetailsStmt.executeQuery();

        String exception = updateRecoveryDetailsStmt.getString(10);
        updateStatus = updateRecoveryDetailsStmt.getInt(1);

        if (updateStatus == 1)
        {
          updateRecoveryDetailsStmt.close();
          updateRecoveryDetailsStmt = null;
          Log.log(2, "GMDAO", "modifyRecoveryDetails", "Exception " + exception);
          connection.rollback();
          throw new DatabaseException(exception);
        }
        if (updateStatus == 0) {
          Log.log(5, "GMDAO", "modifyRecoveryDetails", "SUCCESS SP");
        }
        updateRecoveryDetailsStmt.close();
        updateRecoveryDetailsStmt = null;
        connection.rollback();
      }
      catch (Exception exception) {
        Log.logException(exception);
        try
        {
          connection.rollback();
        }
        catch (SQLException localSQLException) {
        }
        throw new DatabaseException(exception.getMessage());
      } finally {
        DBConnection.freeConnection(connection);
      }
    }
    Log.log(4, "GMDAO", "modifyRecoveryDetails", "Exited");

    return updateRecoveryStatus;
  }

  public ArrayList getRecoveryDetails(String borrowerId)
    throws DatabaseException
  {
    Connection connection = DBConnection.getConnection();
    CallableStatement getRecoveryDetailStmt = null;
    ResultSet resultSet = null;
    Recovery recoveryDetail = null;
    ArrayList recoveryDetails = null;
    try {
      String exception = "";

      getRecoveryDetailStmt = connection.prepareCall("{?=call packGetRecoveryDtls.funcGetREcoveryDtls(?,?,?)}");

      getRecoveryDetailStmt.registerOutParameter(1, 4);
      getRecoveryDetailStmt.setString(2, borrowerId);
      getRecoveryDetailStmt.registerOutParameter(3, -10);

      getRecoveryDetailStmt.registerOutParameter(4, 12);

      getRecoveryDetailStmt.execute();
      int error = getRecoveryDetailStmt.getInt(1);
      exception = getRecoveryDetailStmt.getString(4);

      if (error == 0) {
        Log.log(5, "GMDAO", "get Recovery Details For Bid", "Success");
      }

      if (error == 1)
      {
        getRecoveryDetailStmt.close();
        getRecoveryDetailStmt = null;
        Log.log(5, "GMDAO", "getRecoveryDetailsForBid", "Exception " + exception);
        connection.rollback();
        throw new DatabaseException(exception);
      }

      resultSet = (ResultSet)getRecoveryDetailStmt.getObject(3);

      recoveryDetails = new ArrayList();
      while (resultSet.next())
      {
        recoveryDetail = new Recovery();
        recoveryDetail.setCgbid(borrowerId);
        recoveryDetail.setRecoveryNo(resultSet.getString(1));
        recoveryDetail.setAmountRecovered(resultSet.getDouble(2));
        java.sql.Date sqldate = resultSet.getDate(3);

        recoveryDetail.setDateOfRecovery(sqldate);
        recoveryDetail.setLegalCharges(resultSet.getDouble(4));
        recoveryDetail.setIsRecoveryByOTS(resultSet.getString(5));
        recoveryDetail.setIsRecoveryBySaleOfAsset(resultSet.getString(6));
        recoveryDetail.setDetailsOfAssetSold(resultSet.getString(7));
        recoveryDetail.setRemarks(resultSet.getString(8));
        recoveryDetails.add(recoveryDetail);
      }
      resultSet.close();
      resultSet = null;
      getRecoveryDetailStmt.close();
      getRecoveryDetailStmt = null;

      connection.commit();
    }
    catch (Exception exception)
    {
      try {
        connection.rollback();
      }
      catch (SQLException localSQLException) {
      }
      throw new DatabaseException(exception.getMessage());
    }
    finally
    {
      DBConnection.freeConnection(connection);
    }

    return recoveryDetails;
  }

  public String getBorrowerIdForBorrowerName(String borrowerName)
    throws DatabaseException
  {
    Log.log(4, "GMDAO", "getBorrowerIdForBorrowerName", "Entered");
    Connection connection = DBConnection.getConnection();
    CallableStatement getBorrowerIdForBorrowerNameStmt = null;
    String borrowerId = null;
    try
    {
      getBorrowerIdForBorrowerNameStmt = connection.prepareCall("{? = call funcGetBIDforSSIName(?,?,?)}");

      getBorrowerIdForBorrowerNameStmt.registerOutParameter(1, 4);
      getBorrowerIdForBorrowerNameStmt.setString(2, borrowerName);
      getBorrowerIdForBorrowerNameStmt.registerOutParameter(3, 12);
      getBorrowerIdForBorrowerNameStmt.registerOutParameter(4, 12);

      getBorrowerIdForBorrowerNameStmt.execute();
      int updateStatus = getBorrowerIdForBorrowerNameStmt.getInt(1);

      String error = getBorrowerIdForBorrowerNameStmt.getString(4);

      if (updateStatus == 1) {
        getBorrowerIdForBorrowerNameStmt.close();
        getBorrowerIdForBorrowerNameStmt = null;
        Log.log(2, "GMDAO", "getBorrowerIdForBorrowerName", "Exception " + error);
        connection.rollback();
        throw new DatabaseException(error);
      }
      borrowerId = getBorrowerIdForBorrowerNameStmt.getString(3);

      getBorrowerIdForBorrowerNameStmt.close();
      getBorrowerIdForBorrowerNameStmt = null;
      connection.commit();
    }
    catch (Exception exception) {
      Log.log(2, "GMDAO", "getBorrowerIdForBorrowerName", exception.getMessage());
      Log.logException(exception);
      try
      {
        connection.rollback();
      }
      catch (SQLException localSQLException) {
      }
      throw new DatabaseException(exception.getMessage());
    }
    finally {
      DBConnection.freeConnection(connection);
    }
    Log.log(4, "GMDAO", "getBorrowerIdForBorrowerName", "Exited");

    return borrowerId;
  }

  public ArrayList getBorrowerIds(String memberId)
    throws DatabaseException
  {
    Log.log(4, "GMDAO", "get Borrower IDs", "Entered");
    

    Connection connection = DBConnection.getConnection();
    CallableStatement getBorrowerIdsStmt = null;

    ResultSet resultSetBorrowerIds = null;
    ArrayList borrowerIds = new ArrayList();

    String bankId = memberId.substring(0, 4);
    String zoneId = memberId.substring(4, 8);
    String branchId = memberId.substring(8, 12);
    String borrowerId = "";
    try
    {
      getBorrowerIdsStmt = connection.prepareCall("{? = call packGetAllBorrowerIdsForMemId.funcGetBorrowerIds(?,?,?,?,?)}");

      getBorrowerIdsStmt.registerOutParameter(1, 4);
      getBorrowerIdsStmt.registerOutParameter(5, -10);
      getBorrowerIdsStmt.registerOutParameter(6, 12);

      getBorrowerIdsStmt.setString(2, bankId);
      getBorrowerIdsStmt.setString(3, zoneId);
      getBorrowerIdsStmt.setString(4, branchId);

      getBorrowerIdsStmt.execute();
      int updateStatus = getBorrowerIdsStmt.getInt(1);
      String exception = getBorrowerIdsStmt.getString(6);

      if (updateStatus == 1) {
        getBorrowerIdsStmt.close();
        getBorrowerIdsStmt = null;
        Log.log(2, "GMDAO", "getBorrowerIds", "Exception " + exception);

        connection.rollback();
        throw new DatabaseException(exception);
      }

      resultSetBorrowerIds = (ResultSet)getBorrowerIdsStmt.getObject(5);

      while (resultSetBorrowerIds.next()) {
        borrowerId = resultSetBorrowerIds.getString(1);
        
      //  Log.log(4, "GMDAO", "getBorrowerIds", "borrowerId  under while" + borrowerId);
        if (borrowerId != null) {
          borrowerIds.add(borrowerId);
        }
      }

      resultSetBorrowerIds.close();
      resultSetBorrowerIds = null;
      getBorrowerIdsStmt.close();
      getBorrowerIdsStmt = null;
      connection.commit();
    }
    catch (Exception exception)
    {
      Log.log(2, "GMDAO", "getBorrowerIds", exception.getMessage());
      Log.logException(exception);
      try
      {
        connection.rollback();
      }
      catch (SQLException localSQLException) {
      }
    //  Log.log(2, "GMDAO", "getBorrowerIds  throw new", exception.getMessage());
      throw new DatabaseException(exception.getMessage());
    }
    finally {
      DBConnection.freeConnection(connection);
    }

    Log.log(4, "GMDAO", "get Borrower IDs", "Exited");

    return borrowerIds;
  }

  public String getallocationStatusforCgpan(String cgpan)
    throws DatabaseException
  {
    Log.log(4, "GMDAO", "getallocationStatusforCgpan", "Entered");

    Connection connection = DBConnection.getConnection();
    CallableStatement callableStmt = null;
    Connection conn = null;
    String allocationStatus = null;
    int status = -1;
    String errorCode = null;
    try
    {
      conn = DBConnection.getConnection();
      callableStmt = conn.prepareCall("{? = call funcGetAllStatusforCGPAN(?,?,?)}");
      callableStmt.registerOutParameter(1, 4);
      callableStmt.setString(2, cgpan);
      callableStmt.registerOutParameter(3, 12);
      callableStmt.registerOutParameter(4, 12);

      callableStmt.execute();
      status = callableStmt.getInt(1);
      errorCode = callableStmt.getString(4);

      if (status == 1)
      {
        Log.log(2, "GMDAO", "getallocationStatusforCgpan()", "SP returns a 1. Error code is :" + errorCode);
        callableStmt.close();
        throw new DatabaseException(errorCode);
      }
      if (status == 0)
      {
        allocationStatus = callableStmt.getString(3);

        callableStmt.close();
      }
    }
    catch (SQLException sqlexception)
    {
      Log.log(2, "GMDAO", "getallocationStatusforCgpan()", "Error retrieving MemberID for the CGPAN!");
      throw new DatabaseException(sqlexception.getMessage());
    }
    finally {
      DBConnection.freeConnection(conn);
    }

    Log.log(4, "GMDAO", "getallocationStatusforCgpan", "Exited");

    return allocationStatus;
  }

  public String getMemIdforCgpan(String cgpan)
    throws DatabaseException
  {
    Log.log(4, "GMDAO", "getMemIdforCgpan", "Entered");

    Connection connection = DBConnection.getConnection();
    CallableStatement callableStmt = null;
    Connection conn = null;
    String memIdforCgpan = null;
    int status = -1;
    String errorCode = null;
    try
    {
      conn = DBConnection.getConnection();
      callableStmt = conn.prepareCall("{? = call funcGetMLIIDforCGPAN(?,?,?)}");
      callableStmt.registerOutParameter(1, 4);
      callableStmt.setString(2, cgpan);
      callableStmt.registerOutParameter(3, 12);
      callableStmt.registerOutParameter(4, 12);

      callableStmt.execute();
      status = callableStmt.getInt(1);
      errorCode = callableStmt.getString(4);

      if (status == 1)
      {
        Log.log(2, "GMDAO", "getMemIdforCgpan()", "SP returns a 1. Error code is :" + errorCode);
        callableStmt.close();
        throw new DatabaseException(errorCode);
      }
      if (status == 0)
      {
        memIdforCgpan = callableStmt.getString(3);

        callableStmt.close();
      }
    }
    catch (SQLException sqlexception)
    {
      Log.log(2, "GMDAO", "getMemIdforCgpan()", "Error retrieving MemberID for the CGPAN!");
      throw new DatabaseException(sqlexception.getMessage());
    }
    finally {
      DBConnection.freeConnection(conn);
    }

    Log.log(4, "GMDAO", "getMemIdforCgpan", "Exited");

    return memIdforCgpan;
  }

  public ArrayList getAllActions()
    throws DatabaseException
  {
    Log.log(4, "GMDAO", "getAllActions", "Entered");
    Connection connection = DBConnection.getConnection();
    CallableStatement getAllActionsStmt = null;
    ResultSet resultSet = null;
    ArrayList actions = null;
    try {
      String exception = "";

      getAllActionsStmt = connection.prepareCall("{?=call packGetAllActions.funcGetAllActions(?,?)}");

      getAllActionsStmt.registerOutParameter(1, 4);
      getAllActionsStmt.registerOutParameter(2, -10);
      getAllActionsStmt.registerOutParameter(3, 12);

      getAllActionsStmt.execute();

      int error = getAllActionsStmt.getInt(1);
      exception = getAllActionsStmt.getString(3);

      if (error == 0) {
        Log.log(5, "GMDAO", "funcGetAllActions", "Success");
      }

      if (error == 1)
      {
        getAllActionsStmt.close();
        getAllActionsStmt = null;
        Log.log(2, "GMDAO", "getAllActions", "Exception " + exception);
        connection.rollback();
        throw new DatabaseException(exception);
      }

      resultSet = (ResultSet)getAllActionsStmt.getObject(2);

      actions = new ArrayList();
      String action = null;
      while (resultSet.next())
      {
        action = resultSet.getString(1);
        if (action != null)
        {
          actions.add(action);
        }
      }
      resultSet.close();
      resultSet = null;
      getAllActionsStmt.close();
      getAllActionsStmt = null;
      connection.commit();
    }
    catch (Exception exception)
    {
      try {
        connection.rollback();
      }
      catch (SQLException localSQLException) {
      }
      throw new DatabaseException(exception.getMessage());
    } finally {
      DBConnection.freeConnection(connection);
    }
    Log.log(4, "GMDAO", "getAllActions", "Exited");

    return actions;
  }

  public CgpanDetail getCgpanDetails(String cgpan) throws DatabaseException
  {
    Log.log(4, "GMDAO", "getCgpanDetails", "Entered");
    Connection connection = DBConnection.getConnection();
    CallableStatement getCgpanDetailsStmt = null;
    ResultSet resultSet = null;
    CgpanDetail cgpanDetail = null;
    try {
      String exception = "";

      getCgpanDetailsStmt = connection.prepareCall("{?=call packGetCGPANDtl.funcGetCGPANDtl(?,?,?)}");

      getCgpanDetailsStmt.registerOutParameter(1, 4);
      getCgpanDetailsStmt.setString(2, cgpan);
      getCgpanDetailsStmt.registerOutParameter(3, -10);
      getCgpanDetailsStmt.registerOutParameter(4, 12);

      getCgpanDetailsStmt.execute();

      int error = getCgpanDetailsStmt.getInt(1);
      exception = getCgpanDetailsStmt.getString(4);

      if (error == 0) {
        Log.log(5, "GMDAO", "funcGetCGPANDtl", "Success");
      }

      if (error == 1)
      {
        getCgpanDetailsStmt.close();
        getCgpanDetailsStmt = null;
        Log.log(2, "GMDAO", "getCgpanDetails", "Exception " + exception);
        connection.rollback();
        throw new DatabaseException(exception);
      }

      resultSet = (ResultSet)getCgpanDetailsStmt.getObject(3);

      String prFirstName = null;
      String prSecondName = null;
      String prThirdName = null;

      cgpanDetail = new CgpanDetail();
      while (resultSet.next())
      {
        cgpanDetail.setBorrowerId(resultSet.getString(1));

        cgpanDetail.setBorrowerName(resultSet.getString(2));

        prFirstName = resultSet.getString(3);
        prSecondName = resultSet.getString(4);
        prThirdName = resultSet.getString(5);
        if ((prSecondName == null) || (prSecondName.equals("")))
        {
          cgpanDetail.setChiefPromoterName(prFirstName + " " + prThirdName);
        }
        else
        {
          cgpanDetail.setChiefPromoterName(prFirstName + " " + prSecondName + " " + prThirdName);
        }

        cgpanDetail.setCity(resultSet.getString(6));

        cgpanDetail.setWcAmountSanctioned(resultSet.getDouble(7));

        cgpanDetail.setAmountApproved(resultSet.getDouble(8));

        cgpanDetail.setGuaranteeIssueDate(resultSet.getDate(9));

        cgpanDetail.setTcAmountSanctioned(resultSet.getDouble(10));
      }

      resultSet.close();
      resultSet = null;
      getCgpanDetailsStmt.close();
      getCgpanDetailsStmt = null;

      connection.commit();
    }
    catch (Exception exception)
    {
      try {
        connection.rollback();
      }
      catch (SQLException localSQLException) {
      }
      throw new DatabaseException(exception.getMessage());
    } finally {
      DBConnection.freeConnection(connection);
    }
    Log.log(4, "GMDAO", "getCGpanDetails", "Exited");

    return cgpanDetail;
  }

  public Vector getCGPANDetailsForBId(String borrowerId)
    throws DatabaseException
  {
    Log.log(4, "GMDAO", "getCGPANDetailsForBId", "Entered!");
    ResultSet rs = null;
    HashMap cgpandetails = null;
    Vector allcgpandetails = new Vector();

    CallableStatement callableStmt = null;
    Connection conn = null;

    int status = -1;
    String errorCode = null;
    try
    {
      conn = DBConnection.getConnection();
      callableStmt = conn.prepareCall("{? = call packGetCGPANForBorrower.funcGetCGPANForBorrower(?,?,?)}");
      callableStmt.registerOutParameter(1, 4);
      callableStmt.setString(2, borrowerId);
      callableStmt.registerOutParameter(3, -10);
      callableStmt.registerOutParameter(4, 12);

      callableStmt.execute();
      status = callableStmt.getInt(1);
      errorCode = callableStmt.getString(4);

      if (status == 1)
      {
        Log.log(2, "GMDAO", "getCGPANDetailsForBId", "SP returns a 1. Error code is :" + errorCode);

        callableStmt.close();
        throw new DatabaseException(errorCode);
      }
      if (status == 0)
      {
        rs = (ResultSet)callableStmt.getObject(3);
        while (rs.next())
        {
          String cgpan = null;
          double approvedAmount = 0.0D;
          double enhancedApprovedAmount = 0.0D;
          String loantype = null;

          cgpan = rs.getString(1);
          approvedAmount = rs.getDouble(2);
          enhancedApprovedAmount = rs.getDouble(3);
          loantype = rs.getString(4);

          if (cgpan != null)
          {
            cgpandetails = new HashMap();
            cgpandetails.put("CGPAN", cgpan);
            cgpandetails.put("ApprovedAmount", new Double(approvedAmount));
            cgpandetails.put("EnhancedApprovedAmount", new Double(enhancedApprovedAmount));
            cgpandetails.put("LoanType", loantype);
            allcgpandetails.add(cgpandetails);
          }

        }

        rs.close();

        callableStmt.close();
      }
    }
    catch (SQLException sqlexception)
    {
      Log.log(2, "GMDAO", "getCGPANDetailsForBId", "Error retrieving CGPAN Details for the Borrower!");
      throw new DatabaseException(sqlexception.getMessage());
    }
    finally {
      DBConnection.freeConnection(conn);
    }
    Log.log(4, "GMDAO", "getCGPANDetailsForBId", "Exited!");

    return allcgpandetails;
  }

  public ArrayList getOutstandingsForApproval(ArrayList cgpanList)
    throws DatabaseException
  {
    Log.log(4, "GMDAO", "getOutstandingsForApproval", "Entered");

    Connection connection = DBConnection.getConnection();

    OutstandingDetail oldOsDetail = new OutstandingDetail();
    OutstandingDetail newOsDetail = new OutstandingDetail();
    OutstandingAmount oldOsAmt = new OutstandingAmount();
    OutstandingAmount newOsAmt = new OutstandingAmount();
    ArrayList oldOsAmts = new ArrayList();
    ArrayList newOsAmts = new ArrayList();
    ArrayList oldOsDtls = new ArrayList();
    ArrayList newOsDtls = new ArrayList();

    ArrayList returnDetails = new ArrayList();

    SimpleDateFormat dateFormat = new SimpleDateFormat("dd-MMM-yyyy");
    try
    {
      int size = cgpanList.size();
      for (int i = 0; i < size; i++)
      {
        oldOsDetail = new OutstandingDetail();
        newOsDetail = new OutstandingDetail();

        oldOsAmts = new ArrayList();
        newOsAmts = new ArrayList();

        String cgpan = (String)cgpanList.get(i);
        oldOsDetail.setCgpan(cgpan);
        newOsDetail.setCgpan(cgpan);
        String type = cgpan.substring(cgpan.length() - 2, cgpan.length() - 1);

        Log.log(4, "GMDAO", "getOutstandingsForApproval", "getting os detials for " + cgpan);
        Log.log(4, "GMDAO", "getOutstandingsForApproval", "cgpan type " + type);

        CallableStatement stmt = connection.prepareCall("{?=call packGetAllModifiedOutstandings.funcGetAllModifiedOutstandings(?,?,?,?)}");

        stmt.setString(2, cgpan);
        stmt.registerOutParameter(1, 4);
        stmt.registerOutParameter(3, -10);
        stmt.registerOutParameter(4, -10);
        stmt.registerOutParameter(5, 12);

        stmt.execute();

        int status = stmt.getInt(1);
        if (status == 0)
        {
          ResultSet newOsSet = (ResultSet)stmt.getObject(3);
          ResultSet oldOsSet = (ResultSet)stmt.getObject(4);
          while (newOsSet.next())
          {
            Log.log(4, "GMDAO", "getOutstandingsForApproval", "getting new os details ");
            newOsAmt = new OutstandingAmount();
            newOsAmt.setCgpan(cgpan);
            if (type.equalsIgnoreCase("T"))
            {
              newOsAmt.setTcoId(newOsSet.getString(1));
              Log.log(4, "GMDAO", "getOutstandingsForApproval", "getting new tco id " + newOsAmt.getTcoId());
              newOsAmt.setTcPrincipalOutstandingAmount(newOsSet.getDouble(4));
              Log.log(4, "GMDAO", "getOutstandingsForApproval", "getting new tco amount " + newOsAmt.getTcPrincipalOutstandingAmount());

              newOsAmt.setTcOutstandingAsOnDate(DateHelper.sqlToUtilDate(newOsSet.getDate(5)));
              Log.log(4, "GMDAO", "getOutstandingsForApproval", "getting new tco date " + newOsAmt.getTcOutstandingAsOnDate());
            }
            else if ((type.equalsIgnoreCase("W")) || (type.equalsIgnoreCase("R")))
            {
              newOsAmt.setWcoId(newOsSet.getString(1));
              Log.log(4, "GMDAO", "getOutstandingsForApproval", "getting new wco date " + newOsAmt.getWcoId());
              newOsAmt.setWcFBPrincipalOutstandingAmount(newOsSet.getDouble(6));
              Log.log(4, "GMDAO", "getOutstandingsForApproval", "getting new wco principal amount " + newOsAmt.getWcFBPrincipalOutstandingAmount());
              newOsAmt.setWcFBInterestOutstandingAmount(newOsSet.getDouble(7));
              Log.log(4, "GMDAO", "getOutstandingsForApproval", "getting new wco interest amount " + newOsAmt.getWcFBInterestOutstandingAmount());

              Log.log(4, "GMDAO", "getOutstandingsForApproval", "getting new wco date " + newOsSet.getDate(8));
              newOsAmt.setWcFBOutstandingAsOnDate(DateHelper.sqlToUtilDate(newOsSet.getDate(8)));
              Log.log(4, "GMDAO", "getOutstandingsForApproval", "getting new wco date " + newOsAmt.getWcFBOutstandingAsOnDate());

              newOsAmt.setWcNFBPrincipalOutstandingAmount(newOsSet.getDouble(9));
              Log.log(4, "GMDAO", "getOutstandingsForApproval", "getting new wco principal amount " + newOsAmt.getWcNFBPrincipalOutstandingAmount());
              newOsAmt.setWcNFBInterestOutstandingAmount(newOsSet.getDouble(10));
              Log.log(4, "GMDAO", "getOutstandingsForApproval", "getting new wco interest amount " + newOsAmt.getWcNFBInterestOutstandingAmount());

              newOsAmt.setWcNFBOutstandingAsOnDate(DateHelper.sqlToUtilDate(newOsSet.getDate(11)));
              Log.log(4, "GMDAO", "getOutstandingsForApproval", "getting new wco date " + newOsAmt.getWcFBOutstandingAsOnDate());
            }

            newOsAmts.add(newOsAmt);
            newOsAmt = null;
          }
          int newSize = newOsAmts.size();

          while (oldOsSet.next())
          {
            Log.log(4, "GMDAO", "getOutstandingsForApproval", "getting old os details ");
            oldOsAmt = new OutstandingAmount();
            oldOsAmt.setCgpan(cgpan);
            String tempId = oldOsSet.getString(1);
            Log.log(4, "GMDAO", "getOutstandingsForApproval", "old id " + tempId);
            for (int j = 0; j < newSize; j++)
            {
              OutstandingAmount tempOsAmt = new OutstandingAmount();
              tempOsAmt = (OutstandingAmount)newOsAmts.get(j);

              if (type.equalsIgnoreCase("T"))
              {
                Log.log(4, "GMDAO", "getOutstandingsForApproval", "new tco id " + tempOsAmt.getTcoId());

                if ((tempOsAmt.getTcoId() != null) && (tempOsAmt.getTcoId().equals(tempId)))
                {
                  Log.log(4, "GMDAO", "getOutstandingsForApproval", "getting tc old for new");
                  oldOsAmt.setTcoId(tempOsAmt.getTcoId());
                  Log.log(4, "GMDAO", "getOutstandingsForApproval", "getting old tco id " + oldOsAmt.getTcoId());
                  oldOsAmt.setTcPrincipalOutstandingAmount(oldOsSet.getDouble(4));
                  Log.log(4, "GMDAO", "getOutstandingsForApproval", "getting old tco amount " + oldOsAmt.getTcPrincipalOutstandingAmount());

                  oldOsAmt.setTcOutstandingAsOnDate(DateHelper.sqlToUtilDate(oldOsSet.getDate(5)));
                  Log.log(4, "GMDAO", "getOutstandingsForApproval", "getting old tco date " + oldOsAmt.getTcOutstandingAsOnDate());
                  oldOsAmts.add(oldOsAmt);
                  break;
                }
              }
              else if ((type.equalsIgnoreCase("W")) || (type.equalsIgnoreCase("R")))
              {
                Log.log(4, "GMDAO", "getOutstandingsForApproval", "new wco id " + tempOsAmt.getWcoId());
                if ((tempOsAmt.getWcoId() != null) && (tempOsAmt.getWcoId().equals(tempId)))
                {
                  Log.log(4, "GMDAO", "getOutstandingsForApproval", "getting wc old for new");
                  oldOsAmt.setWcoId(tempOsAmt.getWcoId());
                  Log.log(4, "GMDAO", "getOutstandingsForApproval", "getting old wco id " + oldOsAmt.getWcoId());
                  oldOsAmt.setWcFBPrincipalOutstandingAmount(oldOsSet.getDouble(6));
                  Log.log(4, "GMDAO", "getOutstandingsForApproval", "getting old wco principal amount " + oldOsAmt.getWcFBPrincipalOutstandingAmount());
                  oldOsAmt.setWcFBInterestOutstandingAmount(oldOsSet.getDouble(7));
                  Log.log(4, "GMDAO", "getOutstandingsForApproval", "getting old wco interest amount " + oldOsSet.getDate(8));

                  oldOsAmt.setWcFBOutstandingAsOnDate(DateHelper.sqlToUtilDate(oldOsSet.getDate(8)));
                  Log.log(4, "GMDAO", "getOutstandingsForApproval", "getting old wco date " + oldOsAmt.getWcFBOutstandingAsOnDate());

                  oldOsAmt.setWcNFBPrincipalOutstandingAmount(oldOsSet.getDouble(9));
                  Log.log(4, "GMDAO", "getOutstandingsForApproval", "getting old wco principal amount " + oldOsAmt.getWcNFBPrincipalOutstandingAmount());
                  oldOsAmt.setWcNFBInterestOutstandingAmount(oldOsSet.getDouble(10));
                  Log.log(4, "GMDAO", "getOutstandingsForApproval", "getting old wco interest amount " + oldOsAmt.getWcNFBInterestOutstandingAmount());

                  oldOsAmt.setWcNFBOutstandingAsOnDate(DateHelper.sqlToUtilDate(oldOsSet.getDate(11)));
                  Log.log(4, "GMDAO", "getOutstandingsForApproval", "getting old wco date " + oldOsAmt.getWcNFBOutstandingAsOnDate());

                  oldOsAmts.add(oldOsAmt);
                  break;
                }
              }
              tempOsAmt = null;
            }
            oldOsAmt = null;
          }
          newOsSet.close();
          newOsSet = null;
          oldOsSet.close();
          oldOsSet = null;

          stmt.close();
          stmt = null;

          oldOsDetail.setOutstandingAmounts(oldOsAmts);
          newOsDetail.setOutstandingAmounts(newOsAmts);

          oldOsDtls.add(oldOsDetail);
          newOsDtls.add(newOsDetail);
        }
        else
        {
          String err = stmt.getString(5);
          Log.log(4, "GMDAO", "getOutstandingsForApproval", "error getting os detials for " + cgpan);
          stmt.close();
          stmt = null;
          throw new DatabaseException(err);
        }
        ResultSet newOsSet;
        ResultSet oldOsSet;
        oldOsAmts = null;
        newOsAmts = null;
        oldOsDetail = null;
        newOsDetail = null;
      }
      returnDetails.add(oldOsDtls);
      returnDetails.add(newOsDtls);
    }
    catch (SQLException sqlException)
    {
      Log.log(2, "RIDAO", "getOutstandingsForApproval", sqlException.getMessage());
      Log.logException(sqlException);
      throw new DatabaseException(sqlException.getMessage());
    }
    finally
    {
      DBConnection.freeConnection(connection);
    }

    Log.log(4, "GMDAO", "getOutstandingsForApproval", "Exited");

    return returnDetails;
  }

  public ArrayList getDisbursementsForApproval(ArrayList cgpanList)
    throws DatabaseException
  {
    Log.log(4, "GMDAO", "getDisbursementsForApproval", "Entered");

    Connection connection = DBConnection.getConnection();

    Disbursement oldDisDetail = new Disbursement();
    Disbursement newDisDetail = new Disbursement();
    DisbursementAmount oldDisAmt = new DisbursementAmount();
    DisbursementAmount newDisAmt = new DisbursementAmount();
    ArrayList oldDisAmts = new ArrayList();
    ArrayList newDisAmts = new ArrayList();
    ArrayList oldDisDtls = new ArrayList();
    ArrayList newDisDtls = new ArrayList();

    ArrayList returnDetails = new ArrayList();

    SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MMM/yyyy");
    try
    {
      int size = cgpanList.size();
      for (int i = 0; i < size; i++)
      {
        oldDisDetail = new Disbursement();
        newDisDetail = new Disbursement();

        oldDisAmts = new ArrayList();
        newDisAmts = new ArrayList();

        String cgpan = (String)cgpanList.get(i);
        oldDisDetail.setCgpan(cgpan);
        newDisDetail.setCgpan(cgpan);
        String type = cgpan.substring(cgpan.length() - 2, cgpan.length() - 1);
        String date = "";
        Log.log(4, "GMDAO", "getDisbursementsForApproval", "getting dis detials for " + cgpan);

        CallableStatement stmt = connection.prepareCall("{?=call packGetAllModifiedDis.funcGetAllModifiedDis(?,?,?,?)}");

        stmt.setString(2, cgpan);
        stmt.registerOutParameter(1, 4);
        stmt.registerOutParameter(3, -10);
        stmt.registerOutParameter(4, -10);
        stmt.registerOutParameter(5, 12);

        stmt.execute();
        int status = stmt.getInt(1);
        if (status == 0)
        {
          ResultSet newDisSet = (ResultSet)stmt.getObject(3);
          ResultSet oldDisSet = (ResultSet)stmt.getObject(4);
          while (newDisSet.next())
          {
            newDisAmt = new DisbursementAmount();
            newDisAmt.setCgpan(cgpan);
            newDisAmt.setDisbursementId(newDisSet.getString(1));
            Log.log(4, "GMDAO", "getDisbursementsForApproval", "new dis id " + newDisAmt.getDisbursementId());
            newDisAmt.setDisbursementAmount(newDisSet.getDouble(4));
            Log.log(4, "GMDAO", "getDisbursementsForApproval", "new dis amount " + newDisAmt.getDisbursementAmount());
            newDisAmt.setDisbursementDate(newDisSet.getDate(5));
            Log.log(4, "GMDAO", "getDisbursementsForApproval", "new dis date " + newDisAmt.getDisbursementDate());
            newDisAmt.setFinalDisbursement(newDisSet.getString(6));
            Log.log(4, "GMDAO", "getDisbursementsForApproval", "new dis final flag " + newDisAmt.getFinalDisbursement());

            newDisAmts.add(newDisAmt);
            newDisAmt = null;
          }
          newDisSet.close();
          newDisSet = null;
          int newSize = newDisAmts.size();

          while (oldDisSet.next())
          {
            oldDisAmt = new DisbursementAmount();
            oldDisAmt.setCgpan(cgpan);
            String tempId = oldDisSet.getString(1);
            Log.log(4, "GMDAO", "getDisbursementsForApproval", "getting new id for old " + tempId);
            for (int j = 0; j < newSize; j++)
            {
              DisbursementAmount tempDis = new DisbursementAmount();
              tempDis = (DisbursementAmount)newDisAmts.get(j);

              if (tempDis.getDisbursementId().equals(tempId))
              {
                Log.log(4, "GMDAO", "getDisbursementsForApproval", "new found");
                oldDisAmt.setDisbursementId(tempId);
                Log.log(4, "GMDAO", "getDisbursementsForApproval", "old dis id " + oldDisAmt.getDisbursementId());
                oldDisAmt.setDisbursementAmount(oldDisSet.getDouble(4));
                Log.log(4, "GMDAO", "getDisbursementsForApproval", "old dis amount " + oldDisAmt.getDisbursementAmount());
                oldDisAmt.setDisbursementDate(oldDisSet.getDate(5));
                Log.log(4, "GMDAO", "getDisbursementsForApproval", "old dis date " + oldDisAmt.getDisbursementDate());
                oldDisAmt.setFinalDisbursement(oldDisSet.getString(6));
                Log.log(4, "GMDAO", "getDisbursementsForApproval", "old dis final flag " + oldDisAmt.getFinalDisbursement());

                oldDisAmts.add(oldDisAmt);
                break;
              }
              tempDis = null;
            }
            oldDisAmt = null;
          }
          oldDisSet.close();
          oldDisSet = null;

          stmt.close();
          stmt = null;

          oldDisDetail.setDisbursementAmounts(oldDisAmts);
          newDisDetail.setDisbursementAmounts(newDisAmts);

          oldDisDtls.add(oldDisDetail);
          newDisDtls.add(newDisDetail);
        }
        else
        {
          String err = stmt.getString(5);
          Log.log(4, "GMDAO", "getDisbursementsForApproval", "error getting dis detials for " + cgpan);
          stmt.close();
          stmt = null;
          throw new DatabaseException(err);
        }
        ResultSet newDisSet;
        ResultSet oldDisSet;
        oldDisAmts = null;
        newDisAmts = null;
        oldDisDetail = null;
        newDisDetail = null;
      }
      returnDetails.add(oldDisDtls);
      returnDetails.add(newDisDtls);
    }
    catch (SQLException sqlException)
    {
      Log.log(2, "RIDAO", "getDisbursementsForApproval", sqlException.getMessage());
      Log.logException(sqlException);
      throw new DatabaseException(sqlException.getMessage());
    }
    finally
    {
      DBConnection.freeConnection(connection);
    }

    Log.log(4, "GMDAO", "getDisbursementsForApproval", "Exited");

    return returnDetails;
  }

  public ArrayList getRepaymentsForApproval(ArrayList cgpanList)
    throws DatabaseException
  {
    Log.log(4, "GMDAO", "getRepaymentsForApproval", "Entered");

    Connection connection = DBConnection.getConnection();

    Repayment oldRepayDetail = new Repayment();
    Repayment newRepayDetail = new Repayment();
    RepaymentAmount oldRepayAmt = new RepaymentAmount();
    RepaymentAmount newRepayAmt = new RepaymentAmount();
    ArrayList oldRepayAmts = new ArrayList();
    ArrayList newRepayAmts = new ArrayList();
    ArrayList oldRepayDtls = new ArrayList();
    ArrayList newRepayDtls = new ArrayList();

    ArrayList returnDetails = new ArrayList();

    SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MMM/yyyy");
    try
    {
      int size = cgpanList.size();
      for (int i = 0; i < size; i++)
      {
        oldRepayDetail = new Repayment();
        newRepayDetail = new Repayment();

        oldRepayAmts = new ArrayList();
        newRepayAmts = new ArrayList();

        String cgpan = (String)cgpanList.get(i);
        oldRepayDetail.setCgpan(cgpan);
        newRepayDetail.setCgpan(cgpan);
        String type = cgpan.substring(cgpan.length() - 2, cgpan.length() - 1);
        String date = "";
        Log.log(4, "GMDAO", "getRepaymentsForApproval", "getting repay detials for " + cgpan);
        CallableStatement stmt = connection.prepareCall("{?=call packGetAllModifiedRepayments.funcGetAllModifiedRepayments(?,?,?,?)}");

        stmt.setString(2, cgpan);
        stmt.registerOutParameter(1, 4);
        stmt.registerOutParameter(3, -10);
        stmt.registerOutParameter(4, -10);
        stmt.registerOutParameter(5, 12);

        stmt.execute();
        int status = stmt.getInt(1);
        if (status == 0)
        {
          ResultSet newRepaySet = (ResultSet)stmt.getObject(3);
          ResultSet oldRepaySet = (ResultSet)stmt.getObject(4);

          while (newRepaySet.next())
          {
            newRepayAmt = new RepaymentAmount();
            newRepayAmt.setCgpan(cgpan);
            newRepayAmt.setRepayId(newRepaySet.getString(1));
            Log.log(4, "GMDAO", "getRepaymentsForApproval", "new repay id " + newRepayAmt.getRepayId());
            newRepayAmt.setRepaymentAmount(newRepaySet.getDouble(3));
            Log.log(4, "GMDAO", "getRepaymentsForApproval", "new repay amount " + newRepayAmt.getRepaymentAmount());
            newRepayAmt.setRepaymentDate(newRepaySet.getDate(4));
            Log.log(4, "GMDAO", "getRepaymentsForApproval", "new repay date " + newRepayAmt.getRepaymentDate());

            newRepayAmts.add(newRepayAmt);
            newRepayAmt = null;
          }
          newRepaySet.close();
          newRepaySet = null;
          int newSize = newRepayAmts.size();

          while (oldRepaySet.next())
          {
            oldRepayAmt = new RepaymentAmount();
            oldRepayAmt.setCgpan(cgpan);
            String tempId = oldRepaySet.getString(1);
            Log.log(4, "GMDAO", "getRepaymentsForApproval", "old repay id " + tempId);

            for (int j = 0; j < newSize; j++)
            {
              RepaymentAmount tempAmt = new RepaymentAmount();
              tempAmt = (RepaymentAmount)newRepayAmts.get(j);

              if (tempAmt.getRepayId().equals(tempId))
              {
                Log.log(4, "GMDAO", "getRepaymentsForApproval", "new found for old");
                oldRepayAmt.setRepayId(tempId);
                oldRepayAmt.setRepaymentAmount(oldRepaySet.getDouble(3));
                Log.log(4, "GMDAO", "getRepaymentsForApproval", "old repay amount " + oldRepayAmt.getRepaymentAmount());
                oldRepayAmt.setRepaymentDate(oldRepaySet.getDate(4));
                Log.log(4, "GMDAO", "getRepaymentsForApproval", "old repay date " + oldRepayAmt.getRepaymentDate());

                oldRepayAmts.add(oldRepayAmt);
                break;
              }
              tempAmt = null;
            }
            oldRepayAmt = null;
          }
          oldRepaySet.close();
          oldRepaySet = null;

          stmt.close();
          stmt = null;

          oldRepayDetail.setRepaymentAmounts(oldRepayAmts);
          newRepayDetail.setRepaymentAmounts(newRepayAmts);

          oldRepayDtls.add(oldRepayDetail);
          newRepayDtls.add(newRepayDetail);
        }
        else
        {
          String err = stmt.getString(5);
          Log.log(4, "GMDAO", "getRepaymentsForApproval", "error getting repay detials for " + cgpan);
          stmt.close();
          stmt = null;
          throw new DatabaseException(err);
        }
        ResultSet newRepaySet;
        ResultSet oldRepaySet;
        oldRepayAmts = null;
        newRepayAmts = null;

        oldRepayDetail = null;
        newRepayDetail = null;
      }
      returnDetails.add(oldRepayDtls);
      returnDetails.add(newRepayDtls);
    }
    catch (SQLException sqlException)
    {
      Log.log(2, "RIDAO", "getRepaymentsForApproval", sqlException.getMessage());
      Log.logException(sqlException);
      throw new DatabaseException(sqlException.getMessage());
    }
    finally
    {
      DBConnection.freeConnection(connection);
    }

    Log.log(4, "GMDAO", "getRepaymentsForApproval", "Exited");

    return returnDetails;
  }

  public ArrayList getNpaDetailsForApproval(String borrowerId)
    throws DatabaseException
  {
    Log.log(4, "GMDAO", "getNpaDetailsForApproval", "Entered");

    ArrayList returnDetails = new ArrayList();
    ArrayList oldRecActions = new ArrayList();
    ArrayList newRecActions = new ArrayList();

    NPADetails oldNpaDetail = new NPADetails();
    NPADetails newNpaDetail = new NPADetails();
    LegalSuitDetail oldSuit = new LegalSuitDetail();
    LegalSuitDetail newSuit = new LegalSuitDetail();
    RecoveryProcedure oldRecProc = new RecoveryProcedure();
    RecoveryProcedure newRecProc = new RecoveryProcedure();
    Connection connection = DBConnection.getConnection();

    SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
    try
    {
      CallableStatement stmt = connection.prepareCall("{?=call packGetAllModifiedNPA.funcGetAllModifiedNPA(?,?,?,?)}");

      Log.log(4, "GMDAO", "getNpaDetailsForApproval", "getting npa for " + borrowerId);
      stmt.setString(2, borrowerId);
      stmt.registerOutParameter(1, 4);
      stmt.registerOutParameter(3, -10);
      stmt.registerOutParameter(4, -10);
      stmt.registerOutParameter(5, 12);

      stmt.execute();
      int status = stmt.getInt(1);
      if (status == 0)
      {
        ResultSet newSet = (ResultSet)stmt.getObject(3);
        ResultSet oldSet = (ResultSet)stmt.getObject(4);

        while (newSet.next())
        {
          newNpaDetail = new NPADetails();
          newNpaDetail.setNpaId(newSet.getString(1));
          Log.log(4, "GMDAO", "getNpaDetailsForApproval", "new npa id " + newNpaDetail.getNpaId());
          newNpaDetail.setCgbid(newSet.getString(2));
          Log.log(4, "GMDAO", "getNpaDetailsForApproval", "new npa cgbid " + newNpaDetail.getCgbid());
          newNpaDetail.setNpaDate(newSet.getDate(3));
          Log.log(4, "GMDAO", "getNpaDetailsForApproval", "new npa date " + newNpaDetail.getNpaDate());
          newNpaDetail.setWhetherNPAReported(newSet.getString(4));
          Log.log(4, "GMDAO", "getNpaDetailsForApproval", "new npa reported " + newNpaDetail.getWhetherNPAReported());
          newNpaDetail.setReportingDate(newSet.getDate(6));
          Log.log(4, "GMDAO", "getNpaDetailsForApproval", "new npa reporting date " + newNpaDetail.getReportingDate());
          newNpaDetail.setReference(newSet.getString(7));
          Log.log(4, "GMDAO", "getNpaDetailsForApproval", "new npa reference " + newNpaDetail.getReference());
          newNpaDetail.setOsAmtOnNPA(newSet.getDouble(8));
          Log.log(4, "GMDAO", "getNpaDetailsForApproval", "new npa os amt " + newNpaDetail.getOsAmtOnNPA());
          newNpaDetail.setNpaReason(newSet.getString(9));
          Log.log(4, "GMDAO", "getNpaDetailsForApproval", "new npa reason " + newNpaDetail.getNpaReason());
          newNpaDetail.setRemarksOnNpa(newSet.getString(10));
          Log.log(4, "GMDAO", "getNpaDetailsForApproval", "new npa remarks " + newNpaDetail.getRemarksOnNpa());
          newNpaDetail.setIsRecoveryInitiated(newSet.getString(11));
          Log.log(4, "GMDAO", "getNpaDetailsForApproval", "new npa rec initiated " + newNpaDetail.getIsRecoveryInitiated());
          newNpaDetail.setNoOfActions(newSet.getInt(12));
          Log.log(4, "GMDAO", "getNpaDetailsForApproval", "new npa no of actions " + newNpaDetail.getNoOfActions());
          newNpaDetail.setEffortsConclusionDate(newSet.getDate(13));
          Log.log(4, "GMDAO", "getNpaDetailsForApproval", "new npa eff conc date " + newNpaDetail.getEffortsConclusionDate());
          newNpaDetail.setMliCommentOnFinPosition(newSet.getString(15));
          Log.log(4, "GMDAO", "getNpaDetailsForApproval", "new npa comm on fin position " + newNpaDetail.getMliCommentOnFinPosition());
          newNpaDetail.setDetailsOfFinAssistance(newSet.getString(16));
          Log.log(4, "GMDAO", "getNpaDetailsForApproval", "new npa fin assistance " + newNpaDetail.getDetailsOfFinAssistance());
          newNpaDetail.setCreditSupport(newSet.getString(17));
          Log.log(4, "GMDAO", "getNpaDetailsForApproval", "new npa credit support " + newNpaDetail.getCreditSupport());
          newNpaDetail.setBankFacilityDetail(newSet.getString(18));
          Log.log(4, "GMDAO", "getNpaDetailsForApproval", "new npa bank facility " + newNpaDetail.getBankFacilityDetail());
          newNpaDetail.setWillfulDefaulter(newSet.getString(19));
          Log.log(4, "GMDAO", "getNpaDetailsForApproval", "new npa willfull " + newNpaDetail.getWillfulDefaulter());
          newNpaDetail.setPlaceUnderWatchList(newSet.getString(20));
          Log.log(4, "GMDAO", "getNpaDetailsForApproval", "new npa watchlist " + newNpaDetail.getPlaceUnderWatchList());
          newNpaDetail.setRemarksOnNpa(newSet.getString(24));
          Log.log(4, "GMDAO", "getNpaDetailsForApproval", "new npa remarks " + newNpaDetail.getRemarksOnNpa());
        }

        while (oldSet.next())
        {
          oldNpaDetail = new NPADetails();
          oldNpaDetail.setNpaId(oldSet.getString(1));
          Log.log(4, "GMDAO", "getNpaDetailsForApproval", "old npa id " + oldNpaDetail.getNpaId());
          oldNpaDetail.setCgbid(oldSet.getString(2));
          Log.log(4, "GMDAO", "getNpaDetailsForApproval", "old npa cgbid " + oldNpaDetail.getCgbid());
          oldNpaDetail.setNpaDate(oldSet.getDate(3));
          Log.log(4, "GMDAO", "getNpaDetailsForApproval", "old npa date " + oldNpaDetail.getNpaDate());
          oldNpaDetail.setWhetherNPAReported(oldSet.getString(4));
          Log.log(4, "GMDAO", "getNpaDetailsForApproval", "old npa reported " + oldNpaDetail.getWhetherNPAReported());
          oldNpaDetail.setReportingDate(oldSet.getDate(6));
          Log.log(4, "GMDAO", "getNpaDetailsForApproval", "old npa reporting date " + oldNpaDetail.getReportingDate());
          oldNpaDetail.setReference(oldSet.getString(7));
          Log.log(4, "GMDAO", "getNpaDetailsForApproval", "old npa reference " + oldNpaDetail.getReference());
          oldNpaDetail.setOsAmtOnNPA(oldSet.getDouble(8));
          Log.log(4, "GMDAO", "getNpaDetailsForApproval", "old npa os amt " + oldNpaDetail.getOsAmtOnNPA());
          oldNpaDetail.setNpaReason(oldSet.getString(9));
          Log.log(4, "GMDAO", "getNpaDetailsForApproval", "old npa reason " + oldNpaDetail.getNpaReason());
          oldNpaDetail.setRemarksOnNpa(oldSet.getString(10));
          Log.log(4, "GMDAO", "getNpaDetailsForApproval", "old npa remarks " + oldNpaDetail.getRemarksOnNpa());
          oldNpaDetail.setIsRecoveryInitiated(oldSet.getString(11));
          Log.log(4, "GMDAO", "getNpaDetailsForApproval", "old npa rec initiated " + oldNpaDetail.getIsRecoveryInitiated());
          oldNpaDetail.setNoOfActions(oldSet.getInt(12));
          Log.log(4, "GMDAO", "getNpaDetailsForApproval", "old npa no of actions " + oldNpaDetail.getNoOfActions());
          oldNpaDetail.setEffortsConclusionDate(oldSet.getDate(13));
          Log.log(4, "GMDAO", "getNpaDetailsForApproval", "old npa eff conc date " + oldNpaDetail.getEffortsConclusionDate());
          oldNpaDetail.setMliCommentOnFinPosition(oldSet.getString(15));
          Log.log(4, "GMDAO", "getNpaDetailsForApproval", "old npa comm on fin position " + oldNpaDetail.getMliCommentOnFinPosition());
          oldNpaDetail.setDetailsOfFinAssistance(oldSet.getString(16));
          Log.log(4, "GMDAO", "getNpaDetailsForApproval", "old npa fin assistance " + oldNpaDetail.getDetailsOfFinAssistance());
          oldNpaDetail.setCreditSupport(oldSet.getString(17));
          Log.log(4, "GMDAO", "getNpaDetailsForApproval", "old npa credit support " + oldNpaDetail.getCreditSupport());
          oldNpaDetail.setBankFacilityDetail(oldSet.getString(18));
          Log.log(4, "GMDAO", "getNpaDetailsForApproval", "old npa bank facility " + oldNpaDetail.getBankFacilityDetail());
          oldNpaDetail.setWillfulDefaulter(oldSet.getString(19));
          Log.log(4, "GMDAO", "getNpaDetailsForApproval", "old npa willfull " + oldNpaDetail.getWillfulDefaulter());
          oldNpaDetail.setPlaceUnderWatchList(oldSet.getString(20));
          Log.log(4, "GMDAO", "getNpaDetailsForApproval", "old npa watchlist " + oldNpaDetail.getPlaceUnderWatchList());
          oldNpaDetail.setRemarksOnNpa(oldSet.getString(24));
          Log.log(4, "GMDAO", "getNpaDetailsForApproval", "old npa remarks " + oldNpaDetail.getRemarksOnNpa());
        }
        oldSet.close();
        oldSet = null;
        newSet.close();
        newSet = null;
        stmt.close();
        stmt = null;

        String npaId = oldNpaDetail.getNpaId();
        if ((npaId != null) && (!npaId.equals("")))
        {
          stmt = connection.prepareCall("{?=call packGetAllModifiedRecActions.funcGetAllModifiedRecActions(?,?,?,?)}");

          Log.log(4, "GMDAO", "getNpaDetailsForApproval", "getting rec actions for npa " + npaId);
          stmt.setString(2, npaId);
          stmt.registerOutParameter(1, 4);
          stmt.registerOutParameter(3, -10);
          stmt.registerOutParameter(4, -10);
          stmt.registerOutParameter(5, 12);

          stmt.execute();
          status = stmt.getInt(1);
          if (status == 0)
          {
            oldSet = (ResultSet)stmt.getObject(4);
            newSet = (ResultSet)stmt.getObject(3);
            while (newSet.next())
            {
              newRecProc = new RecoveryProcedure();
              newRecProc.setRadId(newSet.getString(1));
              Log.log(4, "GMDAO", "getNpaDetailsForApproval", "new recproc id " + newRecProc.getRadId());
              newRecProc.setActionType(newSet.getString(2));
              Log.log(4, "GMDAO", "getNpaDetailsForApproval", "new recproc action type " + newRecProc.getActionType());
              newRecProc.setNpaId(newSet.getString(3));
              Log.log(4, "GMDAO", "getNpaDetailsForApproval", "new recproc npa id " + newRecProc.getNpaId());
              newRecProc.setActionDetails(newSet.getString(4));
              Log.log(4, "GMDAO", "getNpaDetailsForApproval", "new recproc action details " + newRecProc.getActionDetails());
              newRecProc.setActionDate(newSet.getDate(5));
              Log.log(4, "GMDAO", "getNpaDetailsForApproval", "new recproc action date " + newRecProc.getActionDate());
              newRecProc.setAttachmentName(newSet.getString(6));
              Log.log(4, "GMDAO", "getNpaDetailsForApproval", "new recproc attachment " + newRecProc.getAttachmentName());

              newRecActions.add(newRecProc);
              newRecProc = null;
            }
            newNpaDetail.setRecoveryProcedure(newRecActions);

            while (oldSet.next())
            {
              oldRecProc = new RecoveryProcedure();
              oldRecProc.setRadId(oldSet.getString(1));
              Log.log(4, "GMDAO", "getNpaDetailsForApproval", "old recproc id " + oldRecProc.getRadId());
              for (int i = 0; i < newRecActions.size(); i++)
              {
                RecoveryProcedure tempRec = (RecoveryProcedure)newRecActions.get(i);
                if (oldRecProc.getRadId().equals(tempRec.getRadId()))
                {
                  oldRecProc.setActionType(oldSet.getString(2));
                  Log.log(4, "GMDAO", "getNpaDetailsForApproval", "old recproc action type " + oldRecProc.getActionType());
                  oldRecProc.setNpaId(oldSet.getString(3));
                  Log.log(4, "GMDAO", "getNpaDetailsForApproval", "old recproc npa id " + oldRecProc.getNpaId());
                  oldRecProc.setActionDetails(oldSet.getString(4));
                  Log.log(4, "GMDAO", "getNpaDetailsForApproval", "old recproc action details " + oldRecProc.getActionDetails());
                  oldRecProc.setActionDate(oldSet.getDate(5));
                  Log.log(4, "GMDAO", "getNpaDetailsForApproval", "old recproc action date " + oldRecProc.getActionDate());
                  oldRecProc.setAttachmentName(oldSet.getString(6));
                  Log.log(4, "GMDAO", "getNpaDetailsForApproval", "old recproc attachment " + oldRecProc.getAttachmentName());

                  oldRecActions.add(oldRecProc);
                  break;
                }
              }
              oldRecProc = null;
            }
            oldNpaDetail.setRecoveryProcedure(oldRecActions);

            oldSet.close();
            oldSet = null;
            newSet.close();
            newSet = null;
            stmt.close();
            stmt = null;
          }
          else
          {
            String err = stmt.getString(5);
            Log.log(4, "GMDAO", "getNpaDetailsForApproval", "error getting recovery action detials for " + npaId);
            stmt.close();
            stmt = null;
            throw new DatabaseException(err);
          }

          stmt = connection.prepareCall("{?=call packGetAllModifiedLegalSuit.funcGetAllModifiedLegalSuit(?,?,?,?)}");
          stmt.setString(2, npaId);
          stmt.registerOutParameter(1, 4);
          stmt.registerOutParameter(3, -10);
          stmt.registerOutParameter(4, -10);
          stmt.registerOutParameter(5, 12);

          stmt.execute();
          status = stmt.getInt(1);
          if (status == 0)
          {
            oldSet = (ResultSet)stmt.getObject(4);
            newSet = (ResultSet)stmt.getObject(3);
            while (newSet.next())
            {
              newSuit = new LegalSuitDetail();
              newSuit.setLegalSuiteNo(newSet.getString(1));
              Log.log(4, "GMDAO", "getNpaDetailsForApproval", "new legalsuit suite no " + newSuit.getLegalSuiteNo());
              newSuit.setNpaId(newSet.getString(2));
              Log.log(4, "GMDAO", "getNpaDetailsForApproval", "new legalsuit npa id " + newSuit.getNpaId());
              newSuit.setCourtName(newSet.getString(3));
              Log.log(4, "GMDAO", "getNpaDetailsForApproval", "new legalsuit court name " + newSuit.getCourtName());
              newSuit.setForumName(newSet.getString(4));
              Log.log(4, "GMDAO", "getNpaDetailsForApproval", "new legalsuit forum name " + newSuit.getForumName());
              newSuit.setLocation(newSet.getString(5));
              Log.log(4, "GMDAO", "getNpaDetailsForApproval", "new legalsuit location " + newSuit.getLocation());
              newSuit.setDtOfFilingLegalSuit(newSet.getDate(6));
              Log.log(4, "GMDAO", "getNpaDetailsForApproval", "new legalsuit filing date " + newSuit.getDtOfFilingLegalSuit());
              newSuit.setAmountClaimed(newSet.getDouble(7));
              Log.log(4, "GMDAO", "getNpaDetailsForApproval", "new legalsuit amount claimed " + newSuit.getAmountClaimed());
              newSuit.setCurrentStatus(newSet.getString(8));
              Log.log(4, "GMDAO", "getNpaDetailsForApproval", "new legalsuit current status " + newSuit.getCurrentStatus());
              newSuit.setRecoveryProceedingsConcluded(newSet.getString(9));
              Log.log(4, "GMDAO", "getNpaDetailsForApproval", "new legalsuit concuded " + newSuit.getRecoveryProceedingsConcluded());
            }
            newNpaDetail.setLegalSuitDetail(newSuit);
            newSuit = null;
            while (oldSet.next())
            {
              oldSuit = new LegalSuitDetail();
              oldSuit.setLegalSuiteNo(oldSet.getString(1));
              Log.log(4, "GMDAO", "getNpaDetailsForApproval", "old legalsuit suite no " + oldSuit.getLegalSuiteNo());
              oldSuit.setNpaId(oldSet.getString(2));
              Log.log(4, "GMDAO", "getNpaDetailsForApproval", "old legalsuit npa id " + oldSuit.getNpaId());
              oldSuit.setCourtName(oldSet.getString(3));
              Log.log(4, "GMDAO", "getNpaDetailsForApproval", "old legalsuit court name " + oldSuit.getCourtName());
              oldSuit.setForumName(oldSet.getString(4));
              Log.log(4, "GMDAO", "getNpaDetailsForApproval", "old legalsuit forum name " + oldSuit.getForumName());
              oldSuit.setLocation(oldSet.getString(5));
              Log.log(4, "GMDAO", "getNpaDetailsForApproval", "old legalsuit location " + oldSuit.getLocation());
              oldSuit.setDtOfFilingLegalSuit(oldSet.getDate(6));
              Log.log(4, "GMDAO", "getNpaDetailsForApproval", "old legalsuit filing date " + oldSuit.getDtOfFilingLegalSuit());
              oldSuit.setAmountClaimed(oldSet.getDouble(7));
              Log.log(4, "GMDAO", "getNpaDetailsForApproval", "old legalsuit amount claimed " + oldSuit.getAmountClaimed());
              oldSuit.setCurrentStatus(oldSet.getString(8));
              Log.log(4, "GMDAO", "getNpaDetailsForApproval", "old legalsuit current status " + oldSuit.getCurrentStatus());
              oldSuit.setRecoveryProceedingsConcluded(oldSet.getString(9));
              Log.log(4, "GMDAO", "getNpaDetailsForApproval", "old legalsuit concuded " + oldSuit.getRecoveryProceedingsConcluded());
            }
            oldNpaDetail.setLegalSuitDetail(oldSuit);
            oldSuit = null;

            oldSet.close();
            oldSet = null;
            newSet.close();
            newSet = null;
            stmt.close();
            stmt = null;

            returnDetails.add(oldNpaDetail);
            returnDetails.add(newNpaDetail);
          }
          else
          {
            String err = stmt.getString(5);
            Log.log(4, "GMDAO", "getNpaDetailsForApproval", "error getting legal suit detials for " + npaId);
            stmt.close();
            stmt = null;
            throw new DatabaseException(err);
          }
        }
      }
      else
      {
        String err = stmt.getString(5);
        Log.log(4, "GMDAO", "getNpaDetailsForApproval", "error getting npa detials for " + borrowerId);
        stmt.close();
        stmt = null;
        throw new DatabaseException(err);
      }
    }
    catch (SQLException sException)
    {
      ResultSet newSet;
      ResultSet oldSet;
      throw new DatabaseException(sException.getMessage());
    }
    finally
    {
      DBConnection.freeConnection(connection);
    }
    ResultSet newSet;
    ResultSet oldSet;
    CallableStatement stmt;
    Log.log(4, "GMDAO", "getNpaDetailsForApproval", "Exited");

    return returnDetails;
  }

  public ArrayList getBorrowerDetailsForApproval(int ssiRefNo)
    throws DatabaseException
  {
    Log.log(4, "GMDAO", "getNpaDetailsForApproval", "Entered");

   // System.out.println(" getBorrowerDetailsForApproval S");
    
    ArrayList returnDetails = new ArrayList();

    BorrowerDetails oldBorrowerDtl = new BorrowerDetails();
    BorrowerDetails newBorrowerDtl = new BorrowerDetails();

    SSIDetails oldSSIDtl = new SSIDetails();
    SSIDetails newSSIDtl = new SSIDetails();
    APForm oldApform=new APForm();
    APForm newApform=new APForm();
   // System.out.println(" connection S");
    Connection connection = DBConnection.getConnection();
  //  System.out.println(" connection E");
    SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
    try
    {
      CallableStatement stmt = connection.prepareCall("{?=call packGetModifiedBorrowerDtl.funcGetModifiedBorrowerDtl(?,?,?,?)}");
      
                                     //  System.out.println("AFTER packGetModifiedBorrowerDtl.funcGetModifiedBorrowerDtl");
      Log.log(4, "GMDAO", "getBorrowerDetailsForApproval", "getting borrower details for " + ssiRefNo);
      stmt.setInt(2, ssiRefNo);
      stmt.registerOutParameter(1, 4);
      stmt.registerOutParameter(3, -10);
      stmt.registerOutParameter(4, -10);
      stmt.registerOutParameter(5, 12);

      stmt.execute();
      int status = stmt.getInt(1);
      if (status == 0)
      {
        ResultSet newBorrowerSet = (ResultSet)stmt.getObject(3);
        ResultSet oldBorrowerSet = (ResultSet)stmt.getObject(4);

        while (newBorrowerSet.next())
        {
          newBorrowerDtl = new BorrowerDetails();
          newSSIDtl = new SSIDetails();
      //    System.out.println("newBorrowerDtl"+newBorrowerDtl);
       //   System.out.println(" newSSIDtl "+newSSIDtl);
          
          
          newSSIDtl.setBorrowerRefNo(newBorrowerSet.getInt(1));
      //    System.out.println(" newBorrowerSet.getInt(1) "+newBorrowerSet.getInt(1));
          newSSIDtl.setCgbid(newBorrowerSet.getString(2));
          newSSIDtl.setSsiName(newBorrowerSet.getString(4));
          Log.log(4, "GMDAO", "getBorrowerDetailsForApproval", "new name " + newSSIDtl.getSsiName());
          newSSIDtl.setAddress(newBorrowerSet.getString(5));
          Log.log(4, "GMDAO", "getBorrowerDetailsForApproval", "new address " + newSSIDtl.getAddress());
          newSSIDtl.setCity(newBorrowerSet.getString(6));
          Log.log(4, "GMDAO", "getBorrowerDetailsForApproval", "new city " + newSSIDtl.getCity());
          newSSIDtl.setState(newBorrowerSet.getString(7));
          Log.log(4, "GMDAO", "getBorrowerDetailsForApproval", "new State " + newSSIDtl.getState());
          newSSIDtl.setDistrict(newBorrowerSet.getString(8));
   //       System.out.println(" newBorrowerSet.getString(8) "+newBorrowerSet.getString(8));
          Log.log(4, "GMDAO", "getBorrowerDetailsForApproval", "new Dist " + newSSIDtl.getDistrict());
          newSSIDtl.setPincode(newBorrowerSet.getString(9));
        //  System.out.println(" newBorrowerSet.getString(9) "+newBorrowerSet.getString(9));
          Log.log(4, "GMDAO", "getBorrowerDetailsForApproval", "new pin code " + newSSIDtl.getPincode());
          newSSIDtl.setSsiType(newBorrowerSet.getString(10));
          Log.log(4, "GMDAO", "getBorrowerDetailsForApproval", "new ssi type " + newSSIDtl.getSsiType());
          newSSIDtl.setIndustryNature(newBorrowerSet.getString(11));
          Log.log(4, "GMDAO", "getBorrowerDetailsForApproval", "new ind nature " + newSSIDtl.getIndustryNature());
          newSSIDtl.setIndustrySector(newBorrowerSet.getString(12));
          Log.log(4, "GMDAO", "getBorrowerDetailsForApproval", "new ind sector " + newSSIDtl.getIndustrySector());
          newSSIDtl.setConstitution(newBorrowerSet.getString(13));
          Log.log(4, "GMDAO", "getBorrowerDetailsForApproval", "new constitution " + newSSIDtl.getConstitution());
          newSSIDtl.setRegNo(newBorrowerSet.getString(14));
          Log.log(4, "GMDAO", "getBorrowerDetailsForApproval", "new reg no " + newSSIDtl.getRegNo());
          newSSIDtl.setEmployeeNos(newBorrowerSet.getInt(15));
    //      System.out.println(" newBorrowerSet.getInt(15) "+newBorrowerSet.getInt(15));
          Log.log(4, "GMDAO", "getBorrowerDetailsForApproval", "new emp no " + newSSIDtl.getEmployeeNos());
          newSSIDtl.setProjectedSalesTurnover(newBorrowerSet.getDouble(16));
          Log.log(4, "GMDAO", "getBorrowerDetailsForApproval", "new turn over " + newSSIDtl.getProjectedSalesTurnover());
          newSSIDtl.setProjectedExports(newBorrowerSet.getDouble(17));
          Log.log(4, "GMDAO", "getBorrowerDetailsForApproval", "new exports " + newSSIDtl.getProjectedExports());
          newSSIDtl.setSsiITPan(newBorrowerSet.getString(18));
          Log.log(4, "GMDAO", "getBorrowerDetailsForApproval", "new ssi itpan " + newSSIDtl.getSsiITPan());
          newSSIDtl.setCommencementDate(newBorrowerSet.getDate(19));
          Log.log(4, "GMDAO", "getBorrowerDetailsForApproval", "new commencement date " + newSSIDtl.getCommencementDate());
          newSSIDtl.setActivityType(newBorrowerSet.getString(20));
   //       System.out.println(" newBorrowerSet.getString(20) "+newBorrowerSet.getString(20));
          Log.log(4, "GMDAO", "getBorrowerDetailsForApproval", "new act type " + newSSIDtl.getActivityType());
          newBorrowerDtl.setNpa(newBorrowerSet.getString(21));
          Log.log(4, "GMDAO", "getBorrowerDetailsForApproval", "new npa " + newBorrowerDtl.getNpa());
          newBorrowerDtl.setAssistedByBank(newBorrowerSet.getString(22));
          Log.log(4, "GMDAO", "getBorrowerDetailsForApproval", "new assisted " + newBorrowerDtl.getAssistedByBank());
          newBorrowerDtl.setPreviouslyCovered(newBorrowerSet.getString(23));
          Log.log(4, "GMDAO", "getBorrowerDetailsForApproval", "new covered " + newBorrowerDtl.getPreviouslyCovered());
          newSSIDtl.setDisplayDefaultersList(newBorrowerSet.getString(24));
          Log.log(4, "GMDAO", "getBorrowerDetailsForApproval", "new defaulter " + newSSIDtl.getDisplayDefaultersList());
          newBorrowerDtl.setOsAmt(newBorrowerSet.getDouble(25));
     //     System.out.println(" newBorrowerSet.getDouble(25) "+newBorrowerSet.getDouble(25));
          Log.log(4, "GMDAO", "getBorrowerDetailsForApproval", "new os amt " + newBorrowerDtl.getOsAmt());
          newSSIDtl.setCpTitle(newBorrowerSet.getString(26));
          Log.log(4, "GMDAO", "getBorrowerDetailsForApproval", "new cp title " + newSSIDtl.getCpTitle());
          newSSIDtl.setCpFirstName(newBorrowerSet.getString(27));
          Log.log(4, "GMDAO", "getBorrowerDetailsForApproval", "new cp first name " + newSSIDtl.getCpFirstName());
          newSSIDtl.setCpMiddleName(newBorrowerSet.getString(28));
          Log.log(4, "GMDAO", "getBorrowerDetailsForApproval", "new cp middle name " + newSSIDtl.getCpMiddleName());
          newSSIDtl.setCpLastName(newBorrowerSet.getString(29));
          Log.log(4, "GMDAO", "getBorrowerDetailsForApproval", "new cp last name " + newSSIDtl.getCpLastName());
          newSSIDtl.setCpITPAN(newBorrowerSet.getString(30));
       //   System.out.println(" newBorrowerSet.getString(30) "+newBorrowerSet.getString(30));
          Log.log(4, "GMDAO", "getBorrowerDetailsForApproval", "new cp itpan " + newSSIDtl.getCpITPAN());
          newSSIDtl.setCpGender(newBorrowerSet.getString(31));
          Log.log(4, "GMDAO", "getBorrowerDetailsForApproval", "new cp gender " + newSSIDtl.getCpGender());
          newSSIDtl.setCpDOB(newBorrowerSet.getDate(32));
          Log.log(4, "GMDAO", "getBorrowerDetailsForApproval", "new cp dob " + newSSIDtl.getCpDOB());
          newSSIDtl.setCpLegalID(newBorrowerSet.getString(33));
          Log.log(4, "GMDAO", "getBorrowerDetailsForApproval", "new cp legal id " + newSSIDtl.getCpLegalID());
          newSSIDtl.setCpLegalIdValue(newBorrowerSet.getString(34));
          Log.log(4, "GMDAO", "getBorrowerDetailsForApproval", "new legal id value " + newSSIDtl.getCpLegalIdValue());
          newSSIDtl.setFirstName(newBorrowerSet.getString(35));
      //    System.out.println(" newBorrowerSet.getString(35) "+newBorrowerSet.getString(35));
          Log.log(4, "GMDAO", "getBorrowerDetailsForApproval", "new other first name " + newSSIDtl.getFirstName());
          newSSIDtl.setFirstDOB(newBorrowerSet.getDate(36));
          Log.log(4, "GMDAO", "getBorrowerDetailsForApproval", "new other first dob " + newSSIDtl.getFirstDOB());
          newSSIDtl.setFirstItpan(newBorrowerSet.getString(37));
          Log.log(4, "GMDAO", "getBorrowerDetailsForApproval", "new other first itpan " + newSSIDtl.getFirstItpan());
          newSSIDtl.setSecondName(newBorrowerSet.getString(38));
          Log.log(4, "GMDAO", "getBorrowerDetailsForApproval", "new other second name " + newSSIDtl.getSecondName());
          newSSIDtl.setSecondDOB(newBorrowerSet.getDate(39));
          Log.log(4, "GMDAO", "getBorrowerDetailsForApproval", "new otehr second dob " + newSSIDtl.getSecondDOB());
          newSSIDtl.setSecondItpan(newBorrowerSet.getString(40));
       //   System.out.println(" newBorrowerSet.getString(40) "+newBorrowerSet.getString(40));
          Log.log(4, "GMDAO", "getBorrowerDetailsForApproval", "new other second itpan " + newSSIDtl.getSecondItpan());
          newSSIDtl.setThirdName(newBorrowerSet.getString(41));
          Log.log(4, "GMDAO", "getBorrowerDetailsForApproval", "new other third name " + newSSIDtl.getThirdName());
          newSSIDtl.setThirdDOB(newBorrowerSet.getDate(42));
          Log.log(4, "GMDAO", "getBorrowerDetailsForApproval", "new other third dob " + newSSIDtl.getThirdDOB());
          newSSIDtl.setThirdItpan(newBorrowerSet.getString(43));
          Log.log(4, "GMDAO", "getBorrowerDetailsForApproval", "new other third itpan " + newSSIDtl.getThirdItpan());
          newSSIDtl.setSocialCategory(newBorrowerSet.getString(44));
     //     System.out.println("newBorrowerSet.getString(44) "+newBorrowerSet.getString(44));
          Log.log(4, "GMDAO", "getBorrowerDetailsForApproval", "new social category " + newSSIDtl.getSocialCategory());
     //     System.out.println("newBorrowerSet.getString(45)"+newBorrowerSet.getString(45));
          newApform.setBranchName(newBorrowerSet.getString(45));
          
          //System.out.println("newBorrowerSet setBankAcNo"+newBorrowerSet.getString(46));
          //System.out.println("newBorrowerSet setUdyogAdharNo"+newBorrowerSet.getString(47));
          newApform.setBankAcNo(newBorrowerSet.getString(46));
          newApform.setUdyogAdharNo(newBorrowerSet.getString(47));
          newBorrowerDtl.setSsiDetails(newSSIDtl);
        }

        while (oldBorrowerSet.next())
        {
          oldBorrowerDtl = new BorrowerDetails();
          oldSSIDtl = new SSIDetails();
          
        //  System.out.println("2while oldBorrowerDtl"+oldBorrowerDtl);
        //  System.out.println("2while oldSSIDtl"+oldSSIDtl);

          oldSSIDtl.setBorrowerRefNo(oldBorrowerSet.getInt(1));
          oldSSIDtl.setCgbid(oldBorrowerSet.getString(2));
          oldSSIDtl.setSsiName(oldBorrowerSet.getString(4));
          Log.log(4, "GMDAO", "getBorrowerDetailsForApproval", "old name " + oldSSIDtl.getSsiName());
          oldSSIDtl.setAddress(oldBorrowerSet.getString(5));
          Log.log(4, "GMDAO", "getBorrowerDetailsForApproval", "old address " + oldSSIDtl.getAddress());
          oldSSIDtl.setCity(oldBorrowerSet.getString(6));
          Log.log(4, "GMDAO", "getBorrowerDetailsForApproval", "old city " + oldSSIDtl.getCity());
          oldSSIDtl.setState(oldBorrowerSet.getString(7));
          Log.log(4, "GMDAO", "getBorrowerDetailsForApproval", "old state " + oldSSIDtl.getState());
          oldSSIDtl.setDistrict(oldBorrowerSet.getString(8));
          Log.log(4, "GMDAO", "getBorrowerDetailsForApproval", "old dist " + oldSSIDtl.getDistrict());
          oldSSIDtl.setPincode(oldBorrowerSet.getString(9));
          Log.log(4, "GMDAO", "getBorrowerDetailsForApproval", "old pincode " + oldSSIDtl.getPincode());
          oldSSIDtl.setSsiType(oldBorrowerSet.getString(10));
          Log.log(4, "GMDAO", "getBorrowerDetailsForApproval", "old ssi type " + oldSSIDtl.getSsiType());
          oldSSIDtl.setIndustryNature(oldBorrowerSet.getString(11));
          Log.log(4, "GMDAO", "getBorrowerDetailsForApproval", "old ind nature " + oldSSIDtl.getIndustryNature());
          oldSSIDtl.setIndustrySector(oldBorrowerSet.getString(12));
          Log.log(4, "GMDAO", "getBorrowerDetailsForApproval", "old ind secotr " + oldSSIDtl.getIndustrySector());
          oldSSIDtl.setConstitution(oldBorrowerSet.getString(13));
          Log.log(4, "GMDAO", "getBorrowerDetailsForApproval", "old const " + oldSSIDtl.getConstitution());
          oldSSIDtl.setRegNo(oldBorrowerSet.getString(14));
          Log.log(4, "GMDAO", "getBorrowerDetailsForApproval", "old reg no " + oldSSIDtl.getRegNo());
          oldSSIDtl.setEmployeeNos(oldBorrowerSet.getInt(15));
          Log.log(4, "GMDAO", "getBorrowerDetailsForApproval", "old emp no " + oldSSIDtl.getEmployeeNos());
          oldSSIDtl.setProjectedSalesTurnover(oldBorrowerSet.getDouble(16));
          Log.log(4, "GMDAO", "getBorrowerDetailsForApproval", "old turnover " + oldSSIDtl.getProjectedSalesTurnover());
          oldSSIDtl.setProjectedExports(oldBorrowerSet.getDouble(17));
          Log.log(4, "GMDAO", "getBorrowerDetailsForApproval", "old exports " + oldSSIDtl.getProjectedExports());
          oldSSIDtl.setSsiITPan(oldBorrowerSet.getString(18));
          Log.log(4, "GMDAO", "getBorrowerDetailsForApproval", "old ssi itpan " + oldSSIDtl.getSsiITPan());
          oldSSIDtl.setCommencementDate(oldBorrowerSet.getDate(19));
          Log.log(4, "GMDAO", "getBorrowerDetailsForApproval", "old commencement date " + oldSSIDtl.getCommencementDate());
          oldSSIDtl.setActivityType(oldBorrowerSet.getString(20));
          Log.log(4, "GMDAO", "getBorrowerDetailsForApproval", "old" + oldSSIDtl.getActivityType());
          oldBorrowerDtl.setNpa(oldBorrowerSet.getString(21));
          Log.log(4, "GMDAO", "getBorrowerDetailsForApproval", "old npa " + oldBorrowerDtl.getNpa());
          oldBorrowerDtl.setAssistedByBank(oldBorrowerSet.getString(22));
          Log.log(4, "GMDAO", "getBorrowerDetailsForApproval", "old assisted " + oldBorrowerDtl.getAssistedByBank());
          oldBorrowerDtl.setPreviouslyCovered(oldBorrowerSet.getString(23));
          Log.log(4, "GMDAO", "getBorrowerDetailsForApproval", "old covered " + oldBorrowerDtl.getPreviouslyCovered());
          oldSSIDtl.setDisplayDefaultersList(oldBorrowerSet.getString(24));
          Log.log(4, "GMDAO", "getBorrowerDetailsForApproval", "old defaulter " + oldSSIDtl.getDisplayDefaultersList());
          oldBorrowerDtl.setOsAmt(oldBorrowerSet.getDouble(25));
          Log.log(4, "GMDAO", "getBorrowerDetailsForApproval", "old os amt " + oldBorrowerDtl.getOsAmt());
          oldSSIDtl.setCpTitle(oldBorrowerSet.getString(26));
          Log.log(4, "GMDAO", "getBorrowerDetailsForApproval", "old cp title " + oldSSIDtl.getCpTitle());
          oldSSIDtl.setCpFirstName(oldBorrowerSet.getString(27));
          Log.log(4, "GMDAO", "getBorrowerDetailsForApproval", "old cp first name " + oldSSIDtl.getCpFirstName());
          oldSSIDtl.setCpMiddleName(oldBorrowerSet.getString(28));
          Log.log(4, "GMDAO", "getBorrowerDetailsForApproval", "old cp middle name " + oldSSIDtl.getCpMiddleName());
          oldSSIDtl.setCpLastName(oldBorrowerSet.getString(29));
          Log.log(4, "GMDAO", "getBorrowerDetailsForApproval", "old cp last name " + oldSSIDtl.getCpLastName());
          oldSSIDtl.setCpITPAN(oldBorrowerSet.getString(30));
          Log.log(4, "GMDAO", "getBorrowerDetailsForApproval", "old cp itpan " + oldSSIDtl.getCpITPAN());
          oldSSIDtl.setCpGender(oldBorrowerSet.getString(31));
          Log.log(4, "GMDAO", "getBorrowerDetailsForApproval", "old cp gender " + oldSSIDtl.getCpGender());
          oldSSIDtl.setCpDOB(oldBorrowerSet.getDate(32));
          Log.log(4, "GMDAO", "getBorrowerDetailsForApproval", "old cp dob " + oldSSIDtl.getCpDOB());
          oldSSIDtl.setCpLegalID(oldBorrowerSet.getString(33));
          Log.log(4, "GMDAO", "getBorrowerDetailsForApproval", "old cp legal id " + oldSSIDtl.getCpLegalID());
          oldSSIDtl.setCpLegalIdValue(oldBorrowerSet.getString(34));
          Log.log(4, "GMDAO", "getBorrowerDetailsForApproval", "old cp legal id vale " + oldSSIDtl.getCpLegalIdValue());
          oldSSIDtl.setFirstName(oldBorrowerSet.getString(35));
          Log.log(4, "GMDAO", "getBorrowerDetailsForApproval", "old other first name " + oldSSIDtl.getFirstName());
          oldSSIDtl.setFirstDOB(oldBorrowerSet.getDate(36));
          Log.log(4, "GMDAO", "getBorrowerDetailsForApproval", "old other first dob " + oldSSIDtl.getFirstDOB());
          oldSSIDtl.setFirstItpan(oldBorrowerSet.getString(37));
          Log.log(4, "GMDAO", "getBorrowerDetailsForApproval", "old other first itpan " + oldSSIDtl.getFirstItpan());
          oldSSIDtl.setSecondName(oldBorrowerSet.getString(38));
          Log.log(4, "GMDAO", "getBorrowerDetailsForApproval", "old other second name " + oldSSIDtl.getSecondName());
          oldSSIDtl.setSecondDOB(oldBorrowerSet.getDate(39));
          Log.log(4, "GMDAO", "getBorrowerDetailsForApproval", "old otehr second dob " + oldSSIDtl.getSecondDOB());
          oldSSIDtl.setSecondItpan(oldBorrowerSet.getString(40));
          Log.log(4, "GMDAO", "getBorrowerDetailsForApproval", "old other second itpan " + oldSSIDtl.getSecondItpan());
          oldSSIDtl.setThirdName(oldBorrowerSet.getString(41));
          Log.log(4, "GMDAO", "getBorrowerDetailsForApproval", "old other thrid name " + oldSSIDtl.getThirdName());
          oldSSIDtl.setThirdDOB(oldBorrowerSet.getDate(42));
          Log.log(4, "GMDAO", "getBorrowerDetailsForApproval", "old other third dob " + oldSSIDtl.getThirdDOB());
          oldSSIDtl.setThirdItpan(oldBorrowerSet.getString(43));
          Log.log(4, "GMDAO", "getBorrowerDetailsForApproval", "old other third itpan " + oldSSIDtl.getThirdItpan());
          oldSSIDtl.setSocialCategory(oldBorrowerSet.getString(44));
          Log.log(4, "GMDAO", "getBorrowerDetailsForApproval", "old social category " + oldSSIDtl.getSocialCategory());
          
          oldApform.setBranchName(oldBorrowerSet.getString(45));
         // System.out.println(" oldApform.setBranchName(oldBorrowerSet.getString(45)"+ oldBorrowerSet.getString(45));
          //System.out.println("oldBorrowerSet setBankAcNo"+oldBorrowerSet.getString(46));
          //System.out.println("oldBorrowerSet setUdyogAdharNo"+oldBorrowerSet.getString(47));
          oldApform.setBankAcNo(oldBorrowerSet.getString(46));
          oldApform.setUdyogAdharNo(oldBorrowerSet.getString(47));
          oldBorrowerDtl.setSsiDetails(oldSSIDtl);
        }
        returnDetails.add(oldBorrowerDtl);
        returnDetails.add(newBorrowerDtl);
        returnDetails.add(oldApform);
        returnDetails.add(newApform);
        newBorrowerSet.close();
        oldBorrowerSet.close();
        newBorrowerSet = null;
        oldBorrowerSet = null;
        stmt.close();
        stmt = null;
      }
      else
      {
        String err = stmt.getString(5);
       
        Log.log(4, "GMDAO", "getBorrowerDetailsForApproval", "error getting borrower detials for " + ssiRefNo);
        stmt.close();
        stmt = null;
         
        throw new DatabaseException("Something ["+err+"] went wrong, Please contact Support[support@cgtmse.in] team");
      }
    }
    catch (SQLException sException)
    {
    	//System.out.println("9607 Something"+sException.getMessage());
    	
    //	sException.printStackTrace();
    
      throw new DatabaseException("Something ["+sException.getMessage()+"] went wrong, Please contact Support[support@cgtmse.in] team");
    }
    finally
    {
      DBConnection.freeConnection(connection);
    }
    
    CallableStatement stmt;
    Log.log(4, "GMDAO", "getBorrowerDetailsForApproval", "Exited");

    return returnDetails;
  }

  public void approvePeriodicInfo(String memberId, String borrowerId)
    throws NoApplicationFoundException, DatabaseException
  {
    Log.log(4, "GMDAO", "approvePeriodicInfo 1", "Entered");

    Connection connection = DBConnection.getConnection(false);

    ArrayList borrowerIds = getBorrowerIds(memberId);
    try
    {
      if ((borrowerId != null) && (!borrowerId.trim().equals("")))
      {
        approvePeriodicInfo(memberId, borrowerId, connection);
      }
      else
      {
        int size = borrowerIds.size();
        for (int i = 0; i < size; i++)
        {
          approvePeriodicInfo(memberId, (String)borrowerIds.get(i), connection);
        }
      }
      connection.commit();
    }
    catch (DatabaseException exception)
    {
      try
      {
        connection.rollback();
      }
      catch (SQLException sException)
      {
        throw new DatabaseException(sException.getMessage());
      }

      throw exception;
    }
    catch (SQLException sException)
    {
      throw new DatabaseException(sException.getMessage());
    }
    finally
    {
      DBConnection.freeConnection(connection);
    }
    Log.log(4, "GMDAO", "approvePeriodicInfo 1", "Exited");
  }

  public void approvePeriodicInfo(String memberId, String borrowerId, Connection connection)
    throws NoApplicationFoundException, DatabaseException
  {
    Log.log(4, "GMDAO", "approvePeriodicInfo 2", "Entered");

    ArrayList cgpans = new ArrayList();
    ApplicationProcessor apProcessor = new ApplicationProcessor();
    try
    {
      String bnkId = memberId.substring(0, 4);
      String zneId = memberId.substring(4, 8);
      String brnId = memberId.substring(8, 12);
      ArrayList retList = apProcessor.getDtlForBIDMem(borrowerId, bnkId, zneId, brnId);
      apProcessor = null;
      cgpans = (ArrayList)retList.get(1);

      int size = cgpans.size();
      int status = 0;
      for (int i = 0; i < size; i++)
      {
        String inCgpan = (String)cgpans.get(i);

        Log.log(4, "GMDAO", "approvePeriodicInfo 2", "Updating Dis for " + inCgpan);
        CallableStatement stmt = connection.prepareCall("{?=call funcDEDBRDetail(?,?)}");
        stmt.setString(2, inCgpan);
        stmt.registerOutParameter(1, 4);
        stmt.registerOutParameter(3, 12);

        stmt.execute();
        status = stmt.getInt(1);
        if (status == 0)
        {
          Log.log(4, "GMDAO", "approvePeriodicInfo 2", "Update Dis Successfull for " + inCgpan);
        }
        else if (status == 1)
        {
          String error = stmt.getString(3);
          Log.log(4, "GMDAO", "approvePeriodicInfo 2", "Error Updating Dis for " + inCgpan);
          Log.log(4, "GMDAO", "approvePeriodicInfo 2", "error message from sp " + error);
          throw new DatabaseException(error);
        }
        stmt.close();
        stmt = null;

        Log.log(4, "GMDAO", "approvePeriodicInfo 2", "Updating OS for " + inCgpan);
        stmt = connection.prepareCall("{?=call funcDEUpdOutstanding(?,?)}");
        stmt.setString(2, inCgpan);
        stmt.registerOutParameter(1, 4);
        stmt.registerOutParameter(3, 12);

        stmt.execute();
        status = stmt.getInt(1);
        if (status == 0)
        {
          Log.log(4, "GMDAO", "approvePeriodicInfo 2", "Update OS Successfull for " + inCgpan);
        }
        else if (status == 1)
        {
          String error = stmt.getString(3);
          Log.log(4, "GMDAO", "approvePeriodicInfo 2", "Error Updating OS for " + inCgpan);
          Log.log(4, "GMDAO", "approvePeriodicInfo 2", "error message from sp " + error);
          throw new DatabaseException(error);
        }
        stmt.close();
        stmt = null;

        Log.log(4, "GMDAO", "approvePeriodicInfo 2", "Updating Repayment for " + inCgpan);
        stmt = connection.prepareCall("{?=call funcDERepaymentDtl(?,?)}");
        stmt.setString(2, inCgpan);
        stmt.registerOutParameter(1, 4);
        stmt.registerOutParameter(3, 12);

        stmt.execute();
        status = stmt.getInt(1);
        if (status == 0)
        {
          Log.log(4, "GMDAO", "approvePeriodicInfo 2", "Update Repayment Successfull for " + inCgpan);
        }
        else if (status == 1)
        {
          String error = stmt.getString(3);
          Log.log(4, "GMDAO", "approvePeriodicInfo 2", "Error Updating Repayment for " + inCgpan);
          Log.log(4, "GMDAO", "approvePeriodicInfo 2", "error message from sp " + error);
          throw new DatabaseException(error);
        }
        stmt.close();
        stmt = null;

        Log.log(4, "GMDAO", "approvePeriodicInfo 2", "Updating Repayment Schedule for " + inCgpan);
        stmt = connection.prepareCall("{?=call funcDERepSchedule(?,?)}");
        stmt.setString(2, inCgpan);
        stmt.registerOutParameter(1, 4);
        stmt.registerOutParameter(3, 12);

        stmt.execute();
        status = stmt.getInt(1);
        if (status == 0)
        {
          Log.log(4, "GMDAO", "approvePeriodicInfo 2", "Update Repayment Schedule Successfull for " + inCgpan);
        }
        else if (status == 1)
        {
          String error = stmt.getString(3);
          Log.log(4, "GMDAO", "approvePeriodicInfo 2", "Error Updating Repayment Schedule for " + inCgpan);
          Log.log(4, "GMDAO", "approvePeriodicInfo 2", "error message from sp " + error);
          throw new DatabaseException(error);
        }
        stmt.close();
        stmt = null;
      }

      Log.log(4, "GMDAO", "approvePeriodicInfo 2", "Updating NPA for " + borrowerId);
      CallableStatement stmt = connection.prepareCall("{?=call funcDENPADetail(?,?)}");
      stmt.setString(2, borrowerId);
      stmt.registerOutParameter(1, 4);
      stmt.registerOutParameter(3, 12);

      stmt.execute();
      status = stmt.getInt(1);
      if (status == 0)
      {
        Log.log(4, "GMDAO", "approvePeriodicInfo 2", "Update NPA Successfull for " + borrowerId);
      }
      else if (status == 1)
      {
        String error = stmt.getString(3);
        Log.log(4, "GMDAO", "approvePeriodicInfo 2", "Error NPA Repayment for " + borrowerId);
        Log.log(4, "GMDAO", "approvePeriodicInfo 2", "error message from sp " + error);
        throw new DatabaseException(error);
      }
      stmt.close();
      stmt = null;

      Log.log(4, "GMDAO", "approvePeriodicInfo 2", "Updating Recovery for " + borrowerId);
      stmt = connection.prepareCall("{?=call funcDERecoveryDtl(?,?)}");
      stmt.setString(2, borrowerId);
      stmt.registerOutParameter(1, 4);
      stmt.registerOutParameter(3, 12);

      stmt.execute();
      status = stmt.getInt(1);
      if (status == 0)
      {
        Log.log(4, "GMDAO", "approvePeriodicInfo 2", "Update Recovery Successfull for " + borrowerId);
      }
      else if (status == 1)
      {
        String error = stmt.getString(3);
        Log.log(4, "GMDAO", "approvePeriodicInfo 2", "Error Recovery Repayment for " + borrowerId);
        Log.log(4, "GMDAO", "approvePeriodicInfo 2", "error message from sp " + error);
        throw new DatabaseException(error);
      }
      stmt.close();
      stmt = null;
    }
    catch (SQLException exception)
    {
      Log.log(4, "GMDAO", "approvePeriodicInfo 2", "exception " + exception.getMessage());
      throw new DatabaseException(exception.getMessage());
    }
    CallableStatement stmt;
    Log.log(4, "GMDAO", "approvePeriodicInfo 2", "Exited");
  }

  public void approveBorrowerDetails(String bID , String userID)
    throws DatabaseException
  {
    Log.log(4, "GMDAO", "approveBorrowerDetails", "Entered");
    Connection connection = null;
    try
    {
      connection = DBConnection.getConnection();
      CallableStatement stmt = connection.prepareCall("{?=call funcDEBorrowerDetailmod(?,?,?)}");

      stmt.registerOutParameter(1, 4);
      stmt.setString(2, bID);
      stmt.setString(3, userID);
      stmt.registerOutParameter(4, 12);
      stmt.execute();

      int status = stmt.getInt(1);
      //System.out.println("GMDAO approveBorrowerDetails status "+status);
      if (status == 1)
      {
        String err = stmt.getString(4);
        //System.out.println("GMDAO approveBorrowerDetails err "+err);
        Log.log(4, "GMDAO", "approveBorrowerDetails", "error from funcdeborrowerdetail " + err);
        stmt.close();
        stmt = null;
       
        //throw new DatabaseException("Something went wrong , kindly contact support team[support@cgtmse.in] ,Reason "+err); 
      }
      stmt.close();
      stmt = null;
    }
    catch (Exception exception)
    {
    //	exception.printStackTrace();
      Log.log(4, "GMDAO", "approveBorrowerDetails", "exception " + exception.getMessage());
      
      throw new DatabaseException("Something went wrong , kindly contact support team[support@cgtmse.in] ,Reason "+exception.getMessage());
    }
    finally
    {
      DBConnection.freeConnection(connection);
    }
   // CallableStatement stmt;
    Log.log(4, "GMDAO", "approveBorrowerDetails", "Exited");
  }
  
  public void rejectBorrowerDetails(String bID, String userName)
  throws DatabaseException
{
  Log.log(4, "GMDAO", "approveBorrowerDetails", "Entered");
  Connection connection = null;
  Statement stmt = null;
  try
  {
    connection = DBConnection .getConnection();
    stmt = connection.createStatement();
    
    String sql = "update ssi_promoter_history  set  SSI_PMR_REQ_STATUS='RE', SSI_PMR_CHECKER_ID='"+userName+"',SSI_PMR_CHECKER_DT=sysdate,SSI_PMR_REMARKS=SSI_PMR_REMARKS||'input remarks' "+
" where ssi_reference_number=(select distinct ssi_reference_number from ssi_detail where bid='"+bID+"')";

    //System.out.println("rejectBorrowerDetails SQL "+sql);
    stmt.executeUpdate(sql);
  
   // int a=10/0;
  }
  catch (Exception exception)
  {
  	//exception.printStackTrace();    
    throw new DatabaseException("Something went wrong , kindly contact support team[support@cgtmse.in] ,Reason "+exception.getMessage());
  }
  finally
  {
	  try{
	         if(stmt!=null)
	        	 stmt.close();
	      }catch(SQLException se){
	      }
    DBConnection.freeConnection(connection);
  }
  
  Log.log(4, "GMDAO", "approveBorrowerDetails", "Exited");
}

  public ArrayList getCgpanMapping(String memberId)
    throws DatabaseException
  {
    Log.log(4, "GMDAO", "getCgpanMapping", "Entered");
    Connection connection = null;

    ArrayList cgpanMapping = new ArrayList();
    ArrayList mapping = new ArrayList();
    try
    {
      connection = DBConnection.getConnection();
      Log.log(4, "GMDAO", "getCgpanMapping", "Connection got");
      CallableStatement stmt = connection.prepareCall("{?=call packGetAllCgpansForMember.funcGetAllCgpansForMember(?,?,?,?,?)}");

      stmt.registerOutParameter(1, 4);
      String bnkId = memberId.substring(0, 4);
      String zneId = memberId.substring(4, 8);
      String brnId = memberId.substring(8, 12);
      stmt.setString(2, bnkId);
      stmt.setString(3, zneId);
      stmt.setString(4, brnId);
      stmt.registerOutParameter(5, -10);
      stmt.registerOutParameter(6, 12);
      stmt.execute();

      int status = stmt.getInt(1);
      if (status == 1)
      {
        String err = stmt.getString(6);
        Log.log(4, "GMDAO", "getCgpanMapping", "error from funcGetAllCgpansForMember " + err);
        stmt.close();
        stmt = null;
        throw new DatabaseException(err);
      }
      ResultSet cgpans = (ResultSet)stmt.getObject(5);
      while (cgpans.next())
      {
        mapping = new ArrayList();
        String newCgpan = cgpans.getString(1);
        String oldCgpan = cgpans.getString(2);
        mapping.add(oldCgpan);
        mapping.add(newCgpan);
        cgpanMapping.add(mapping);
        mapping = null;
      }
      cgpans.close();
      cgpans = null;
      stmt.close();
      stmt = null;
    }
    catch (SQLException exception)
    {
      Log.log(4, "GMDAO", "getCgpanMapping", "exception " + exception.getMessage());
      throw new DatabaseException(exception.getMessage());
    }
    finally
    {
      DBConnection.freeConnection(connection);
    }
    ResultSet cgpans;
    CallableStatement stmt;
    Log.log(4, "GMDAO", "getCgpanMapping", "Exited");

    return cgpanMapping;
  }

  public ArrayList getRecoveryForApproval(String borrowerId)
    throws DatabaseException
  {
    Log.log(4, "GMDAO", "getRecoveryForApproval", "Entered");

    Connection connection = DBConnection.getConnection();

    Recovery oldRecovery = new Recovery();
    Recovery newRecovery = new Recovery();
    ArrayList oldRecDetails = new ArrayList();
    ArrayList newRecDetails = new ArrayList();

    ArrayList returnDetails = new ArrayList();

    SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MMM/yyyy");
    try
    {
      CallableStatement stmt = connection.prepareCall("{?=call packGetAllModifiedRecovery.funcGetAllModifiedRecovery(?,?,?,?)}");

      Log.log(4, "GMDAO", "getRecoveryForApproval", "getting recovery details for " + borrowerId);
      stmt.setString(2, borrowerId);
      stmt.registerOutParameter(1, 4);
      stmt.registerOutParameter(3, -10);
      stmt.registerOutParameter(4, -10);
      stmt.registerOutParameter(5, 12);

      stmt.execute();
      int status = stmt.getInt(1);
      if (status == 0)
      {
        ResultSet newRecSet = (ResultSet)stmt.getObject(3);
        ResultSet oldRecSet = (ResultSet)stmt.getObject(4);

        while (newRecSet.next())
        {
          newRecovery = new Recovery();
          newRecovery.setCgbid(borrowerId);
          newRecovery.setRecoveryNo(newRecSet.getString(1));
          newRecovery.setAmountRecovered(newRecSet.getDouble(3));
          newRecovery.setDateOfRecovery(newRecSet.getDate(4));
          newRecovery.setLegalCharges(newRecSet.getDouble(5));
          newRecovery.setIsRecoveryByOTS(newRecSet.getString(6));
          newRecovery.setIsRecoveryBySaleOfAsset(newRecSet.getString(7));
          newRecovery.setDetailsOfAssetSold(newRecSet.getString(8));
          newRecovery.setRemarks(newRecSet.getString(9));

          newRecDetails.add(newRecovery);
          newRecovery = null;
        }
        newRecSet.close();
        newRecSet = null;
        int newSize = newRecDetails.size();

        while (oldRecSet.next())
        {
          oldRecovery = new Recovery();
          oldRecovery.setCgbid(borrowerId);
          String tempId = oldRecSet.getString(1);
          Log.log(4, "GMDAO", "getRecoveryForApproval", "old recovery id " + tempId);
          for (int i = 0; i < newSize; i++)
          {
            Recovery temp = new Recovery();
            temp = (Recovery)newRecDetails.get(i);
            if (temp.getRecoveryNo().equals(tempId))
            {
              Log.log(4, "GMDAO", "getRecoveryForApproval", "found match " + temp.getRecoveryNo());
              oldRecovery.setRecoveryNo(tempId);
              oldRecovery.setAmountRecovered(oldRecSet.getDouble(3));
              oldRecovery.setDateOfRecovery(oldRecSet.getDate(4));
              oldRecovery.setLegalCharges(oldRecSet.getDouble(5));
              oldRecovery.setIsRecoveryByOTS(oldRecSet.getString(6));
              oldRecovery.setIsRecoveryBySaleOfAsset(oldRecSet.getString(7));
              oldRecovery.setDetailsOfAssetSold(oldRecSet.getString(8));
              oldRecovery.setRemarks(oldRecSet.getString(9));

              oldRecDetails.add(oldRecovery);
              break;
            }
            temp = null;
          }
          oldRecovery = null;
        }
        oldRecSet.close();
        oldRecSet = null;

        stmt.close();
        stmt = null;

        returnDetails.add(oldRecDetails);
        returnDetails.add(newRecDetails);
      }
      else
      {
        String err = stmt.getString(5);
        Log.log(4, "GMDAO", "getRecoveryForApproval", "error getting reecovery detials for " + borrowerId);
        stmt.close();
        stmt = null;
        throw new DatabaseException(err);
      }
    }
    catch (SQLException sqlException)
    {
      ResultSet newRecSet;
      ResultSet oldRecSet;
      Log.log(2, "RIDAO", "getRecoveryForApproval", sqlException.getMessage());
      Log.logException(sqlException);
      throw new DatabaseException(sqlException.getMessage());
    }
    finally
    {
      DBConnection.freeConnection(connection);
    }
    ResultSet newRecSet;
    ResultSet oldRecSet;
    CallableStatement stmt;
    Log.log(4, "GMDAO", "getRecoveryForApproval", "Exited");

    return returnDetails;
  }

  public ArrayList getRepayScheduleForApproval(ArrayList cgpanList)
    throws DatabaseException
  {
    Log.log(4, "GMDAO", "getRepayScheduleForApproval", "Entered");

    Connection connection = DBConnection.getConnection();
    RepaymentSchedule oldRepaySch = new RepaymentSchedule();
    RepaymentSchedule newRepaySch = new RepaymentSchedule();
    ArrayList oldRepaySchDtls = new ArrayList();
    ArrayList newRepaySchDtls = new ArrayList();
    ArrayList returnDetails = new ArrayList();

    SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MMM/yyyy");
    try
    {
      int size = cgpanList.size();
      Log.log(4, "GMDAO", "getRepayScheduleForApproval", "size " + size);
      ResultSet newRepaySchSet;
      ResultSet oldRepaySchSet;
      for (int i = 0; i < size; i++)
      {
        Log.log(4, "GMDAO", "getRepayScheduleForApproval", "i " + i);
        String cgpan = (String)cgpanList.get(i);

        String type = cgpan.substring(cgpan.length() - 2, cgpan.length() - 1);
        String date = "";
        Log.log(4, "GMDAO", "getRepayScheduleForApproval", "getting dis detials for " + cgpan);

        CallableStatement stmt = connection.prepareCall("{?=call packGetAllModifiedRepaySch.funcGetAllModifiedRepaySch(?,?,?,?)}");

        stmt.setString(2, cgpan);
        stmt.registerOutParameter(1, 4);
        stmt.registerOutParameter(3, -10);
        stmt.registerOutParameter(4, -10);
        stmt.registerOutParameter(5, 12);

        stmt.execute();
        int status = stmt.getInt(1);
        if (status == 0)
        {
           newRepaySchSet = (ResultSet)stmt.getObject(3);
           oldRepaySchSet = (ResultSet)stmt.getObject(4);
          while (newRepaySchSet.next())
          {
            newRepaySch = new RepaymentSchedule();
            newRepaySch.setCgpan(cgpan);
            newRepaySch.setMoratorium(newRepaySchSet.getInt(3));
            Log.log(4, "GMDAO", "getRepayScheduleForApproval", "new moratorium " + newRepaySch.getMoratorium());
            newRepaySch.setFirstInstallmentDueDate(newRepaySchSet.getDate(4));
            Log.log(4, "GMDAO", "getRepayScheduleForApproval", "new first installment due date " + newRepaySch.getFirstInstallmentDueDate());
            newRepaySch.setPeriodicity(newRepaySchSet.getString(5));
            Log.log(4, "GMDAO", "getRepayScheduleForApproval", "new periodicity " + newRepaySch.getPeriodicity());
            newRepaySch.setNoOfInstallment(newRepaySchSet.getInt(6));
            Log.log(4, "GMDAO", "getRepayScheduleForApproval", "new no of installments " + newRepaySch.getNoOfInstallment());

            newRepaySchDtls.add(newRepaySch);
            newRepaySch = null;
          }
          newRepaySchSet.close();
          newRepaySchSet = null;

          int newSize = newRepaySchDtls.size();

          while (oldRepaySchSet.next())
          {
            oldRepaySch = new RepaymentSchedule();

            String tempCgpan = oldRepaySchSet.getString(2);
            for (int j = 0; j < newSize; j++)
            {
              Log.log(4, "GMDAO", "getRepayScheduleForApproval", "entering " + j);
              RepaymentSchedule temp = new RepaymentSchedule();
              temp = (RepaymentSchedule)newRepaySchDtls.get(j);
              Log.log(4, "GMDAO", "getRepayScheduleForApproval", "searching old for new cgpan " + temp.getCgpan());
              if (temp.getCgpan().equals(tempCgpan))
              {
                Log.log(4, "GMDAO", "getRepayScheduleForApproval", "founc match ");
                oldRepaySch.setCgpan(tempCgpan);
                Log.log(4, "GMDAO", "getRepayScheduleForApproval", "old cgpan " + oldRepaySch.getCgpan());
                oldRepaySch.setMoratorium(oldRepaySchSet.getInt(3));
                Log.log(4, "GMDAO", "getRepayScheduleForApproval", "old moratorium " + oldRepaySch.getMoratorium());
                oldRepaySch.setFirstInstallmentDueDate(oldRepaySchSet.getDate(4));
                Log.log(4, "GMDAO", "getRepayScheduleForApproval", "old first installment due date " + oldRepaySch.getFirstInstallmentDueDate());
                oldRepaySch.setPeriodicity(oldRepaySchSet.getString(5));
                Log.log(4, "GMDAO", "getRepayScheduleForApproval", "old periodicity " + oldRepaySch.getPeriodicity());
                oldRepaySch.setNoOfInstallment(oldRepaySchSet.getInt(6));
                Log.log(4, "GMDAO", "getRepayScheduleForApproval", "old no of installments " + oldRepaySch.getNoOfInstallment());

                oldRepaySchDtls.add(oldRepaySch);
              }
              Log.log(4, "GMDAO", "getRepayScheduleForApproval", "setting null ");
              temp = null;
              Log.log(4, "GMDAO", "getRepayScheduleForApproval", "after setting null ");
            }
          }
          Log.log(4, "GMDAO", "getRepayScheduleForApproval", "b4 rs close ");
          oldRepaySchSet.close();
          Log.log(4, "GMDAO", "getRepayScheduleForApproval", "after rs close ");
          oldRepaySchSet = null;
          Log.log(4, "GMDAO", "getRepayScheduleForApproval", "after rs null ");

          stmt.close();
          Log.log(4, "GMDAO", "getRepayScheduleForApproval", "after stmt close ");
          stmt = null;
          Log.log(4, "GMDAO", "getRepayScheduleForApproval", "after stmt null ");
        }
        else
        {
          String err = stmt.getString(5);
          Log.log(4, "GMDAO", "getRepayScheduleForApproval", "error getting dis detials for " + cgpan);
          stmt.close();
          stmt = null;
          throw new DatabaseException(err);
        }
      }
      Log.log(4, "GMDAO", "getRepayScheduleForApproval", "b4 adding old ");
      returnDetails.add(oldRepaySchDtls);
      Log.log(4, "GMDAO", "getRepayScheduleForApproval", "after adding old ");
      returnDetails.add(newRepaySchDtls);
      Log.log(4, "GMDAO", "getRepayScheduleForApproval", "after adding new ");
    }
    catch (SQLException sqlException)
    {
      Log.log(2, "RIDAO", "getRepayScheduleForApproval", sqlException.getMessage());
      Log.logException(sqlException);
      throw new DatabaseException(sqlException.getMessage());
    }
    finally
    {
      DBConnection.freeConnection(connection);
    }

    Log.log(4, "GMDAO", "getRepayScheduleForApproval", "Exited");

    return returnDetails;
  }

  public ArrayList getBorrowerIdForBorrowerName(String borrowerName, String memberId)
    throws DatabaseException
  {
    Log.log(4, "GMDAO", "getBorrowerIdForBorrowerName", "Entered");
    Connection connection = DBConnection.getConnection();
    CallableStatement getBorrowerIdForBorrowerNameStmt = null;
    ArrayList bids = new ArrayList();

    String bankId = memberId.substring(0, 4);
    String zoneId = memberId.substring(4, 8);
    String branchId = memberId.substring(8, 12);
    try
    {
      getBorrowerIdForBorrowerNameStmt = connection.prepareCall("{? = call packGetBIDsforSSIName.funcGetBIDsForSSIName(?,?,?,?,?,?)}");

      getBorrowerIdForBorrowerNameStmt.registerOutParameter(1, 4);
      getBorrowerIdForBorrowerNameStmt.setString(2, borrowerName);
      getBorrowerIdForBorrowerNameStmt.setString(3, bankId);
      getBorrowerIdForBorrowerNameStmt.setString(4, zoneId);
      getBorrowerIdForBorrowerNameStmt.setString(5, branchId);
      getBorrowerIdForBorrowerNameStmt.registerOutParameter(6, -10);
      getBorrowerIdForBorrowerNameStmt.registerOutParameter(7, 12);

      getBorrowerIdForBorrowerNameStmt.execute();
      int updateStatus = getBorrowerIdForBorrowerNameStmt.getInt(1);

      String error = getBorrowerIdForBorrowerNameStmt.getString(7);

      if (updateStatus == 1) {
        getBorrowerIdForBorrowerNameStmt.close();
        getBorrowerIdForBorrowerNameStmt = null;
        Log.log(2, "GMDAO", "getBorrowerIdForBorrowerName", "Exception " + error);
        connection.rollback();
        throw new DatabaseException(error);
      }

      ResultSet borrowerIds = (ResultSet)getBorrowerIdForBorrowerNameStmt.getObject(6);
      while (borrowerIds.next())
      {
        String bid = borrowerIds.getString(1);
        String unitName = borrowerIds.getString(2);
        String bidUnitName = bid + "(" + unitName + ")";

        if ((bid != null) && (!bid.equals("")))
        {
          bids.add(bidUnitName);
        }
      }

      borrowerIds.close();
      borrowerIds = null;

      getBorrowerIdForBorrowerNameStmt.close();
      getBorrowerIdForBorrowerNameStmt = null;
      connection.commit();
    }
    catch (Exception exception) {
      Log.log(2, "GMDAO", "getBorrowerIdForBorrowerName", exception.getMessage());
      Log.logException(exception);
      try
      {
        connection.rollback();
      }
      catch (SQLException localSQLException) {
      }
      throw new DatabaseException(exception.getMessage());
    }
    finally {
      DBConnection.freeConnection(connection);
    }
    Log.log(4, "GMDAO", "getBorrowerIdForBorrowerName", "Exited");

    return bids;
  }

  public ArrayList getRepaymentDetailsForReport(String id, int type)
    throws DatabaseException
  {
    Log.log(4, "GMDAO", "get Repayment Details", "Entered");

    ArrayList periodicInfos = new ArrayList();

    Connection connection = DBConnection.getConnection();
    CallableStatement getRepaymentDetailStmt = null;
    ResultSet resultSet = null;
    try
    {
      String exception = "";
      String functionName = null;

      if (type == 0)
      {
        functionName = "{?=call packGetDtlsforRepayment.funcGetDtlsforBID(?,?,?)}";
      }
      else if (type == 1)
      {
        functionName = "{?=call packGetDtlsforRepayment.funcGetDtlsforCGPAN(?,?,?)}";
      } else if (type == 2) {
        functionName = "{?=call packGetDtlsforRepayment.funcGetDtlsforBorrower(?,?,?)}";
      }

      getRepaymentDetailStmt = connection.prepareCall(functionName);
      getRepaymentDetailStmt.registerOutParameter(1, 4);
      getRepaymentDetailStmt.setString(2, id);
      getRepaymentDetailStmt.registerOutParameter(3, -10);
      getRepaymentDetailStmt.registerOutParameter(4, 12);

      getRepaymentDetailStmt.executeQuery();

      exception = getRepaymentDetailStmt.getString(4);
      Log.log(5, "GMDAO", "repayment detail", "exception " + exception);
      int error = getRepaymentDetailStmt.getInt(1);
      Log.log(5, "GMDAO", "repayment detail", "errorCode " + error);
      if (error == 1)
      {
        getRepaymentDetailStmt.close();
        getRepaymentDetailStmt = null;
        connection.rollback();
        Log.log(2, "GMDAO", "getRepaymentdetail", "error in SP " + exception);
        throw new DatabaseException(exception);
      }
      Log.log(5, "GMDAO", "getRepaymentdetail", "Before ResultSet assign");
      resultSet = (ResultSet)getRepaymentDetailStmt.getObject(3);
      Log.log(5, "GMDAO", "getRepaymentdetail", "resultSet assigned");

      PeriodicInfo periodicInfo = new PeriodicInfo();
      Repayment repayment = null;

      ArrayList listOfRepayment = new ArrayList();

      boolean firstTime = true;
      String tcCgpan = null;
      int len = 0;
      String applType = null;

      while (resultSet.next())
      {
        Log.log(5, "GMDAO", "getRepaymentdetail", "Inside ResultSet");
        repayment = new Repayment();
        tcCgpan = resultSet.getString(2);
        len = tcCgpan.length();
        applType = tcCgpan.substring(len - 2, len - 1);
        if (applType.equalsIgnoreCase("T"))
        {
          if (firstTime)
          {
            periodicInfo.setBorrowerId(resultSet.getString(1));
            Log.log(5, "getRepaymentDetails for Borrower", "Borrower ID", " : " + periodicInfo.getBorrowerId());

            periodicInfo.setBorrowerName(resultSet.getString(3));
            Log.log(5, "getRepaymentDetailsfor Borrower:", "Borrower Name", " : " + periodicInfo.getBorrowerName());
            firstTime = false;
          }

          repayment.setCgpan(tcCgpan);
          Log.log(5, "getRepaymentDetailsfor Borrower:", "CGPAN ", ": " + repayment.getCgpan());
          repayment.setScheme(resultSet.getString(4));
          Log.log(5, "getRepaymentDetailsfor Borrower:", "Scheme", " : " + repayment.getScheme());

          listOfRepayment.add(repayment);
        }
      }

      Log.log(5, "getRepaymentDetails for Borrower:", "size of RepaymentObj", " : " + listOfRepayment.size());

      resultSet.close();
      resultSet = null;

      getRepaymentDetailStmt = null;

      functionName = "{?=call packGetRepaymentDtlsForReport.funcGetRepaymentDtlForReport(?,?,?)}";
      getRepaymentDetailStmt = connection.prepareCall(functionName);

      String cgpan = "";
      int size = listOfRepayment.size();
      for (int i = 0; i < size; i++) {
        ArrayList listOfRepaymentAmount = new ArrayList();
        repayment = (Repayment)listOfRepayment.get(i);
        cgpan = repayment.getCgpan();
        Log.log(5, "getRepaymentDetails for cgpan:", "cgpan", " : " + i + " " + cgpan);
        getRepaymentDetailStmt.registerOutParameter(1, 4);
        getRepaymentDetailStmt.setString(2, cgpan);

        getRepaymentDetailStmt.registerOutParameter(3, -10);
        getRepaymentDetailStmt.registerOutParameter(4, 12);

        getRepaymentDetailStmt.execute();

        exception = getRepaymentDetailStmt.getString(4);

        error = getRepaymentDetailStmt.getInt(1);
        if (error == 1)
        {
          getRepaymentDetailStmt.close();
          getRepaymentDetailStmt = null;
          Log.log(2, "getRepaymentDetails for cgpan:", "Exception ", exception);
          connection.rollback();
          throw new DatabaseException(exception);
        }
        resultSet = (ResultSet)getRepaymentDetailStmt.getObject(3);
        RepaymentAmount repaymentAmount = null;
        while (resultSet.next())
        {
          repaymentAmount = new RepaymentAmount();

          repaymentAmount.setCgpan(cgpan);
          Log.log(5, "GMDAO", "RepaymentAmount", "Cgpan " + cgpan);

          repaymentAmount.setRepayId(resultSet.getString(1));
          Log.log(5, "GMDAO", "RepaymentAmount", "RepaymentId " + repaymentAmount.getRepayId());

          repaymentAmount.setRepaymentAmount(resultSet.getDouble(2));
          Log.log(5, "rep Amt: ", "rpAmount", "--" + repaymentAmount.getRepaymentAmount());

          repaymentAmount.setRepaymentDate(resultSet.getDate(3));
          Log.log(5, "rep date:", "date", " " + repaymentAmount.getRepaymentDate());

          listOfRepaymentAmount.add(repaymentAmount);
          Log.log(5, "************", "***********", "****************");
        }
        repayment.setRepaymentAmounts(listOfRepaymentAmount);
        resultSet.close();
        resultSet = null;
      }
      periodicInfo.setRepaymentDetails(listOfRepayment);

      periodicInfos.add(periodicInfo);

      getRepaymentDetailStmt.close();
      getRepaymentDetailStmt = null;

      connection.commit();
    }
    catch (Exception exception) {
      Log.logException(exception);
      try
      {
        connection.rollback();
      }
      catch (SQLException localSQLException) {
      }
      throw new DatabaseException(exception.getMessage());
    }
    finally
    {
      DBConnection.freeConnection(connection);
    }

    Log.log(4, "GMDAO", "get Repayment Details", "Exited");

    return periodicInfos;
  }

  public ArrayList getDisbursementDetailsForReport(String id, int type)
    throws DatabaseException
  {
    Log.log(4, "GMDAO", "getDisbursementDetails", "Entered");

    ArrayList periodicInfos = new ArrayList();

    Connection connection = DBConnection.getConnection();

    CallableStatement getDisbursementDetailStmt = null;

    ResultSet resultSet = null;
    try
    {
      String exception = "";

      String functionName = null;

      if (type == 0)
      {
        functionName = "{?=call packGetDtlsforDBR.funcGetDtlsForBid(?,?,?)}";
      }
      else if (type == 1)
      {
        functionName = "{?=call packGetDtlsforDBR.funcGetDtlsForCGPAN(?,?,?)}";
      } else if (type == 2)
      {
        functionName = "{?=call packGetDtlsforDBR.funcGetDtlsForBorrower(?,?,?)}";
      }

      getDisbursementDetailStmt = connection.prepareCall(functionName);
      getDisbursementDetailStmt.registerOutParameter(1, 4);
      getDisbursementDetailStmt.setString(2, id);
      getDisbursementDetailStmt.registerOutParameter(3, -10);
      getDisbursementDetailStmt.registerOutParameter(4, 12);

      getDisbursementDetailStmt.executeQuery();

      exception = getDisbursementDetailStmt.getString(4);

      int error = getDisbursementDetailStmt.getInt(1);

      if (error == 1)
      {
        getDisbursementDetailStmt.close();
        getDisbursementDetailStmt = null;
        Log.log(5, "GMDAO", "getDisbursementDetails", "Exception :" + exception);
        connection.rollback();
        throw new DatabaseException(exception);
      }
      resultSet = (ResultSet)getDisbursementDetailStmt.getObject(3);

      PeriodicInfo periodicInfo = new PeriodicInfo();
      Disbursement disbursement = null;
      ArrayList listOfDisbursement = new ArrayList();
      boolean firstTime = true;

      while (resultSet.next())
      {
        disbursement = new Disbursement();

        if (firstTime)
        {
          periodicInfo.setBorrowerId(resultSet.getString(1));
          periodicInfo.setBorrowerName(resultSet.getString(4));
          firstTime = false;
        }

        disbursement.setCgpan(resultSet.getString(2));

        disbursement.setScheme(resultSet.getString(3));

        disbursement.setSanctionedAmount(resultSet.getDouble(5));

        listOfDisbursement.add(disbursement);
      }

      resultSet.close();
      resultSet = null;

      getDisbursementDetailStmt.close();
      getDisbursementDetailStmt = null;

      String cgpan = null;

      int disbSize = listOfDisbursement.size();
      functionName = "{?=call packGetPIDBRDtlsCGPANForReport.funcDBRDetailsForCGPANReport(?,?,?)}";
      getDisbursementDetailStmt = connection.prepareCall(functionName);
      for (int i = 0; i < disbSize; i++) {
        ArrayList listOfDisbursementAmount = new ArrayList();
        disbursement = (Disbursement)listOfDisbursement.get(i);
        cgpan = disbursement.getCgpan();
        if (cgpan != null)
        {
          Log.log(5, "GMDAO", "getDisbursementDetails", "Cgpan" + cgpan);
          getDisbursementDetailStmt.registerOutParameter(1, 4);
          getDisbursementDetailStmt.setString(2, cgpan);

          getDisbursementDetailStmt.registerOutParameter(3, -10);
          getDisbursementDetailStmt.registerOutParameter(4, 12);

          getDisbursementDetailStmt.execute();

          exception = getDisbursementDetailStmt.getString(4);

          error = getDisbursementDetailStmt.getInt(1);
          if (error == 1)
          {
            getDisbursementDetailStmt.close();
            getDisbursementDetailStmt = null;
            Log.log(2, "GMDAO", "getDisbursementDetails", "Exception" + exception);
            connection.rollback();
            throw new DatabaseException(exception);
          }
          resultSet = (ResultSet)getDisbursementDetailStmt.getObject(3);
          DisbursementAmount disbursementAmount = null;

          while (resultSet.next())
          {
            disbursementAmount = new DisbursementAmount();

            disbursementAmount.setCgpan(cgpan);

            disbursementAmount.setDisbursementId(resultSet.getString(1));
            Log.log(5, "GMDAO", "getDisbursementDetails", "disb Id" + disbursementAmount.getDisbursementId());

            disbursementAmount.setDisbursementAmount(resultSet.getDouble(2));
            Log.log(5, "GMDAO", "getDisbursementDetails", "disb Amt" + disbursementAmount.getDisbursementAmount());

            disbursementAmount.setDisbursementDate(DateHelper.sqlToUtilDate(resultSet.getDate(3)));
            Log.log(5, "GMDAO", "getDisbursementDetails", "disb date" + disbursementAmount.getDisbursementDate());

            disbursementAmount.setFinalDisbursement(resultSet.getString(4));
            Log.log(5, "GMDAO", "getDisbursementDetails", "Fin disb " + disbursementAmount.getFinalDisbursement());

            listOfDisbursementAmount.add(disbursementAmount);
          }

          disbursement.setDisbursementAmounts(listOfDisbursementAmount);
          resultSet.close();
          resultSet = null;
        }
      }

      getDisbursementDetailStmt.close();
      getDisbursementDetailStmt = null;

      periodicInfo.setDisbursementDetails(listOfDisbursement);
      periodicInfos.add(periodicInfo);

      connection.commit();
    }
    catch (Exception exception) {
      Log.logException(exception);
      try
      {
        connection.rollback();
      }
      catch (SQLException localSQLException) {
      }
      throw new DatabaseException(exception.getMessage());
    }
    finally
    {
      DBConnection.freeConnection(connection);
    }

    Log.log(4, "GMDAO", "get disbursementDetails", "Exited");

    return periodicInfos;
  }

  public NPADetails getNPADetailsForReport(String borrowerId)
    throws DatabaseException
  {
    Log.log(4, "GMDAO", "getNPADetails", "Entered");

    Connection connection = DBConnection.getConnection();
    CallableStatement getNPADetailStmt = null;
    ResultSet resultSet = null;

    NPADetails npaDetail = null;
    try
    {
      Log.log(5, "GMDAO", "get NPA Details For Bid", "test");

      getNPADetailStmt = connection.prepareCall("{?=call packGetNPADetailForReport.funcGetNPADetailForReport(?,?,?)}");

      getNPADetailStmt.registerOutParameter(1, 4);
      getNPADetailStmt.setString(2, borrowerId);

      getNPADetailStmt.registerOutParameter(3, -10);

      getNPADetailStmt.registerOutParameter(4, 12);

      getNPADetailStmt.execute();

      Log.log(5, "GMDAO", "get NPA Details For Bid", "test");

      resultSet = (ResultSet)getNPADetailStmt.getObject(3);
      int error = getNPADetailStmt.getInt(1);
      String exception = getNPADetailStmt.getString(4);

      if (error == 0) {
        Log.log(5, "GMDAO", "get NPA Details For Bid", "Success");
      }

      if (error == 1)
      {
        getNPADetailStmt.close();
        getNPADetailStmt = null;
        Log.log(2, "GMDAO", "get NPA Details For Bid", "Exception " + exception);
        connection.rollback();
        throw new DatabaseException(exception);
      }

      LegalSuitDetail legalSuitDetail = null;
      boolean isNpaAvailable = false;
      while (resultSet.next())
      {
        Log.log(5, "GMDAO", "getNPADetails", "inside result set isNpaAvailable = true");
        isNpaAvailable = true;
        npaDetail = new NPADetails();
        legalSuitDetail = new LegalSuitDetail();

        npaDetail.setNpaId(resultSet.getString(1));
        npaDetail.setCgbid(resultSet.getString(2));
        npaDetail.setNpaDate(resultSet.getDate(3));
        npaDetail.setWhetherNPAReported(resultSet.getString(4));
        npaDetail.setReportingDate(resultSet.getDate(6));
        npaDetail.setReference(resultSet.getString(7));
        npaDetail.setOsAmtOnNPA(resultSet.getDouble(8));
        npaDetail.setNpaReason(resultSet.getString(9));
        npaDetail.setEffortsTaken(resultSet.getString(10));
        Log.log(5, "GMDAO", "getNPADetails", "Efforts " + npaDetail.getEffortsTaken());

        npaDetail.setIsRecoveryInitiated(resultSet.getString(11));
        npaDetail.setNoOfActions(resultSet.getInt(12));
        npaDetail.setEffortsConclusionDate(resultSet.getDate(13));
        npaDetail.setMliCommentOnFinPosition(resultSet.getString(15));
        npaDetail.setDetailsOfFinAssistance(resultSet.getString(16));
        npaDetail.setCreditSupport(resultSet.getString(17));
        npaDetail.setBankFacilityDetail(resultSet.getString(18));
        npaDetail.setWillfulDefaulter(resultSet.getString(19));
        npaDetail.setPlaceUnderWatchList(resultSet.getString(20));
        npaDetail.setRemarksOnNpa(resultSet.getString(24));
        Log.log(5, "GMDAO", "getNPADetails", "Remarks" + npaDetail.getRemarksOnNpa());

        legalSuitDetail.setLegalSuiteNo(resultSet.getString(25));
        legalSuitDetail.setCourtName(resultSet.getString(26));
        legalSuitDetail.setForumName(resultSet.getString(27));
        legalSuitDetail.setLocation(resultSet.getString(28));
        legalSuitDetail.setDtOfFilingLegalSuit(resultSet.getDate(29));
        legalSuitDetail.setAmountClaimed(resultSet.getDouble(30));
        legalSuitDetail.setCurrentStatus(resultSet.getString(31));
        legalSuitDetail.setRecoveryProceedingsConcluded(resultSet.getString(32));

        npaDetail.setLegalSuitDetail(legalSuitDetail);
      }
      getNPADetailStmt.close();
      getNPADetailStmt = null;

      resultSet.close();
      resultSet = null;

      if (isNpaAvailable)
      {
        Log.log(5, "GMDAO", "getNPADetails", "isNpaAvailable = true getRecoveryAxn");

        getNPADetailStmt = connection.prepareCall("{?=call packGetRecoveryAxnForReport.funcGetRecoveryAxnForReport(?,?,?)}");

        getNPADetailStmt.registerOutParameter(1, 4);

        getNPADetailStmt.setString(2, npaDetail.getNpaId());

        getNPADetailStmt.registerOutParameter(3, -10);
        getNPADetailStmt.registerOutParameter(4, 12);

        getNPADetailStmt.execute();

        resultSet = (ResultSet)getNPADetailStmt.getObject(3);

        int recerror = getNPADetailStmt.getInt(1);
        String recexception = getNPADetailStmt.getString(4);

        if (recerror == 0)
        {
          Log.log(5, "GMDAO", "get RecoveryActionDetails For Bid", "Success");
        }

        if (recerror == 1)
        {
          getNPADetailStmt.close();
          getNPADetailStmt = null;
          Log.log(2, "GMDAO", "get RecoveryAction Details For Bid", "Exception " + recexception);
          connection.rollback();
          throw new DatabaseException(exception);
        }

        ArrayList recoProcs = new ArrayList();

        while (resultSet.next())
        {
          Log.log(5, "GMDAO", "get NPA Details..getRecoveryActionDetailsForBid", "Inside Result Set");
          RecoveryProcedure recoProc = new RecoveryProcedure();
          Log.log(5, "GMDAO", "Test", "new R");
          recoProc.setActionType(resultSet.getString(1));
          Log.log(5, "GMDAO", "Test", "1");
          recoProc.setRadId(resultSet.getString(2));
          Log.log(5, "GMDAO", "Test", "2");
          recoProc.setActionDetails(resultSet.getString(3));
          Log.log(5, "GMDAO", "Test", "3");
          recoProc.setActionDate(resultSet.getDate(4));
          Log.log(5, "GMDAO", "Test", "4");
          recoProc.setAttachmentName(resultSet.getString(5));
          Log.log(5, "GMDAO", "Test", "5");
          recoProcs.add(recoProc);
          Log.log(5, "GMDAO", "Test", "one while");
        }
        Log.log(5, "GMDAO", "getRecoveryActionDetailsForBid", "Size is " + recoProcs.size());
        resultSet.close();
        resultSet = null;

        getNPADetailStmt.close();
        getNPADetailStmt = null;
        connection.commit();
        npaDetail.setRecoveryProcedure(recoProcs);
      }
    }
    catch (Exception exception) {
      try {
        connection.rollback();
      }
      catch (SQLException localSQLException) {
      }
      throw new DatabaseException(exception.getMessage());
    } finally {
      DBConnection.freeConnection(connection);
    }
    Log.log(4, "GMDAO", "getNPADetails", "Exited");

    return npaDetail;
  }

  public ArrayList getRecoveryDetailsForReport(String borrowerId)
    throws DatabaseException
  {
    Connection connection = DBConnection.getConnection();
    CallableStatement getRecoveryDetailStmt = null;
    ResultSet resultSet = null;
    Recovery recoveryDetail = null;
    ArrayList recoveryDetails = null;
    try {
      String exception = "";

      getRecoveryDetailStmt = connection.prepareCall("{?=call packGetRecoveryDtlsForReport.funcGetREcoveryDtlsForReport(?,?,?)}");

      getRecoveryDetailStmt.registerOutParameter(1, 4);
      getRecoveryDetailStmt.setString(2, borrowerId);
      getRecoveryDetailStmt.registerOutParameter(3, -10);

      getRecoveryDetailStmt.registerOutParameter(4, 12);

      getRecoveryDetailStmt.execute();
      int error = getRecoveryDetailStmt.getInt(1);
      exception = getRecoveryDetailStmt.getString(4);

      if (error == 0) {
        Log.log(5, "GMDAO", "get Recovery Details For Bid", "Success");
      }

      if (error == 1)
      {
        getRecoveryDetailStmt.close();
        getRecoveryDetailStmt = null;
        Log.log(5, "GMDAO", "getRecoveryDetailsForBid", "Exception " + exception);
        connection.rollback();
        throw new DatabaseException(exception);
      }

      resultSet = (ResultSet)getRecoveryDetailStmt.getObject(3);

      recoveryDetails = new ArrayList();
      while (resultSet.next())
      {
        recoveryDetail = new Recovery();
        recoveryDetail.setCgbid(borrowerId);
        recoveryDetail.setRecoveryNo(resultSet.getString(1));
        recoveryDetail.setAmountRecovered(resultSet.getDouble(2));
        java.sql.Date sqldate = resultSet.getDate(3);

        recoveryDetail.setDateOfRecovery(sqldate);
        recoveryDetail.setLegalCharges(resultSet.getDouble(4));
        recoveryDetail.setIsRecoveryByOTS(resultSet.getString(5));
        recoveryDetail.setIsRecoveryBySaleOfAsset(resultSet.getString(6));
        recoveryDetail.setDetailsOfAssetSold(resultSet.getString(7));
        recoveryDetail.setRemarks(resultSet.getString(8));
        recoveryDetails.add(recoveryDetail);
      }
      resultSet.close();
      resultSet = null;
      getRecoveryDetailStmt.close();
      getRecoveryDetailStmt = null;

      connection.commit();
    }
    catch (Exception exception)
    {
      try {
        connection.rollback();
      }
      catch (SQLException localSQLException) {
      }
      throw new DatabaseException(exception.getMessage());
    }
    finally
    {
      DBConnection.freeConnection(connection);
    }

    return recoveryDetails;
  }

  public ArrayList getOutstandingDetailsForReport(String id, int type)
    throws SQLException, DatabaseException
  {
    Log.log(4, "GMDAO", "getOutstandingDetails", "Entered");

    OutstandingDetail outstandingDetail = null;

    PeriodicInfo periodicInfo = new PeriodicInfo();

    ArrayList periodicInfos = new ArrayList();

    ArrayList listOfOutstandingDetail = new ArrayList();

    Log.log(5, "GMDAO", "getOutstandingDetails", "Id passed is " + id);

    Connection connection = DBConnection.getConnection();

    CallableStatement getOutstandingDetailStmt = null;

    ResultSet resultSet = null;

    ResultSet cgpanResultSet = null;

    CallableStatement getOutDetailForCgpanStmt = null;

    String panType = null;
    int cgpanLength = 0;

    String cgpan = null;
    String appType = null;
    int len = 0;
    int size = 0;
    try
    {
      String exception = "";

      String functionName = null;

      ArrayList outDtls = new ArrayList();

      if (type == 0)
      {
        functionName = "{?=call packGetOutstandingDtls.funcGetOutStandingforBID(?,?,?)}";
        getOutstandingDetailStmt = connection.prepareCall(functionName);
        getOutstandingDetailStmt.registerOutParameter(1, 4);
        getOutstandingDetailStmt.setString(2, id);
        getOutstandingDetailStmt.registerOutParameter(3, -10);
        getOutstandingDetailStmt.registerOutParameter(4, 12);

        getOutstandingDetailStmt.execute();

        exception = getOutstandingDetailStmt.getString(4);
        int error = getOutstandingDetailStmt.getInt(1);

        if (error == 0)
        {
          Log.log(5, "GMDAO", "GET OutstandingForBID", "Success");
        }

        if (error == 1)
        {
          getOutstandingDetailStmt.close();
          getOutstandingDetailStmt = null;
          Log.log(2, "GMDAO", "getOutstandingDetailsForBID", "Exception " + exception);
          connection.rollback();
          throw new DatabaseException(exception);
        }

        resultSet = (ResultSet)getOutstandingDetailStmt.getObject(3);

        boolean firstTime = true;

        String cgpan1 = null;

        while (resultSet.next())
        {
          if (firstTime)
          {
            periodicInfo.setBorrowerId(resultSet.getString(1));
            periodicInfo.setBorrowerName(resultSet.getString(4));
            firstTime = false;
          }
          cgpan1 = resultSet.getString(2);
          Log.log(5, "GMDAO", "getOutstandingDetails", "cgpan from view : " + cgpan1);
          if (cgpan1 != null)
          {
            outstandingDetail = new OutstandingDetail();
            outstandingDetail.setCgpan(cgpan1);
            outstandingDetail.setScheme(resultSet.getString(3));
            outstandingDetail.setTcSanctionedAmount(resultSet.getDouble(5));
            outstandingDetail.setWcFBSanctionedAmount(resultSet.getDouble(6));
            outstandingDetail.setWcNFBSanctionedAmount(resultSet.getDouble(7));
            listOfOutstandingDetail.add(outstandingDetail);
          }
          Log.log(5, "GMDAO", "getOutstandingDetails", "end of one Result Set View");
        }
        resultSet.close();
        resultSet = null;

        getOutstandingDetailStmt.close();
        getOutstandingDetailStmt = null;

        size = listOfOutstandingDetail.size();
        Log.log(5, "GMDAO", "getOutstandingDetails", "size of out dtls : " + size);

        for (int i = 0; i < size; i++)
        {
          OutstandingDetail outDtl = (OutstandingDetail)listOfOutstandingDetail.get(i);
          cgpan = outDtl.getCgpan();
          Log.log(5, "GMDAO", "getOutstandingDetails", "inside for loop cgpan : " + cgpan);

          len = cgpan.length();

          appType = cgpan.substring(len - 2, len - 1);

          ArrayList outAmounts = new ArrayList();

          if (appType.equalsIgnoreCase("T"))
          {
            getOutDetailForCgpanStmt = connection.prepareCall("{?= call packGetTCOutStandingForReport.funcTCOutStandingForReport(?,?,?)}");
            getOutDetailForCgpanStmt.registerOutParameter(1, 4);
            getOutDetailForCgpanStmt.setString(2, cgpan);
            getOutDetailForCgpanStmt.registerOutParameter(3, -10);
            getOutDetailForCgpanStmt.registerOutParameter(4, 12);

            getOutDetailForCgpanStmt.execute();

            exception = getOutDetailForCgpanStmt.getString(4);

            error = getOutDetailForCgpanStmt.getInt(1);

            if (error == 0)
            {
              Log.log(5, "GMDAO", "GETTcOutstanding", "Success");
            }

            if (error == 1)
            {
              getOutDetailForCgpanStmt.close();
              getOutDetailForCgpanStmt = null;
              Log.log(2, "GMDAO", "GETTcOutstanding", "Exception " + exception);
              connection.rollback();
              throw new DatabaseException(exception);
            }

            cgpanResultSet = (ResultSet)getOutDetailForCgpanStmt.getObject(3);

            OutstandingAmount outAmount = null;

            while (cgpanResultSet.next())
            {
              outAmount = new OutstandingAmount();
              outAmount.setCgpan(cgpan);
              outAmount.setTcoId(cgpanResultSet.getString(1));
              outAmount.setTcPrincipalOutstandingAmount(cgpanResultSet.getDouble(2));
              outAmount.setTcOutstandingAsOnDate(cgpanResultSet.getDate(3));

              outAmounts.add(outAmount);
              Log.log(5, "GMDAO", "getOutstandingDetails", "end of one Result Set for Cgpan TC");
            }

            cgpanResultSet.close();
            cgpanResultSet = null;

            getOutDetailForCgpanStmt.close();
            getOutDetailForCgpanStmt = null;
          }
          else if ((appType.equalsIgnoreCase("W")) || (appType.equalsIgnoreCase("R")))
          {
            getOutDetailForCgpanStmt = connection.prepareCall("{?=call packGetWCOutStandingForReport.funcWCOutStandingForReport(?,?,?)}");
            getOutDetailForCgpanStmt.registerOutParameter(1, 4);
            Log.log(5, "GMDAO", "getOutstandingDetails", "Cgpan WC ");
            getOutDetailForCgpanStmt.setString(2, cgpan);

            getOutDetailForCgpanStmt.registerOutParameter(3, -10);
            getOutDetailForCgpanStmt.registerOutParameter(4, 12);

            getOutDetailForCgpanStmt.execute();

            exception = getOutDetailForCgpanStmt.getString(4);

            error = getOutDetailForCgpanStmt.getInt(1);

            if (error == 0)
            {
              Log.log(5, "GMDAO", "GET WC Outstanding", "Success");
            }

            if (error == 1)
            {
              getOutDetailForCgpanStmt.close();
              getOutDetailForCgpanStmt = null;
              Log.log(2, "GMDAO", "GET WC Outstanding", "Exception" + exception);
              connection.rollback();
              throw new DatabaseException(exception);
            }

            cgpanResultSet = (ResultSet)getOutDetailForCgpanStmt.getObject(3);

            OutstandingAmount outAmount = null;

            while (cgpanResultSet.next())
            {
              outAmount = new OutstandingAmount();
              outAmount.setCgpan(cgpan);

              outAmount.setWcoId(cgpanResultSet.getString(1));
              outAmount.setWcFBPrincipalOutstandingAmount(cgpanResultSet.getDouble(2));
              outAmount.setWcFBInterestOutstandingAmount(cgpanResultSet.getDouble(3));
              outAmount.setWcFBOutstandingAsOnDate(cgpanResultSet.getDate(4));
              outAmount.setWcNFBPrincipalOutstandingAmount(cgpanResultSet.getDouble(5));
              outAmount.setWcNFBInterestOutstandingAmount(cgpanResultSet.getDouble(6));
              outAmount.setWcNFBOutstandingAsOnDate(cgpanResultSet.getDate(7));
              outAmounts.add(outAmount);
              Log.log(5, "GMDAO", "getOutstandingDetails", "end of one Result Set for Cgpan WC");
            }
            cgpanResultSet.close();
            cgpanResultSet = null;

            getOutDetailForCgpanStmt.close();
            getOutDetailForCgpanStmt = null;
          }
          outDtl.setOutstandingAmounts(outAmounts);
          outDtls.add(outDtl);
        }
        periodicInfo.setOutstandingDetails(outDtls);
        Log.log(5, "GMDAO", "getOutstandingDetails", "OutDtls size " + outDtls.size());
        periodicInfos.add(periodicInfo);
      }
      else if (type == 1)
      {
        functionName = "{?=call packGetOutstandingDtls.funcGetOutStandingforCGPAN(?,?,?)}";

        getOutstandingDetailStmt = connection.prepareCall(functionName);

        getOutstandingDetailStmt.registerOutParameter(1, 4);
        getOutstandingDetailStmt.setString(2, id);
        getOutstandingDetailStmt.registerOutParameter(3, -10);
        getOutstandingDetailStmt.registerOutParameter(4, 12);

        getOutstandingDetailStmt.execute();

        exception = getOutstandingDetailStmt.getString(4);

        int error = getOutstandingDetailStmt.getInt(1);

        if (error == 0)
        {
          Log.log(5, "GMDAO", "getOutstandinForCGPAN", "Success");
        }

        if (error == 1)
        {
          getOutstandingDetailStmt.close();
          getOutstandingDetailStmt = null;
          Log.log(2, "GMDAO", "getOutstandingDetailsForCGPAN", "Exception " + exception);
          connection.rollback();
          throw new DatabaseException(exception);
        }

        resultSet = (ResultSet)getOutstandingDetailStmt.getObject(3);

        cgpanLength = id.length();

        panType = id.substring(cgpanLength - 2, cgpanLength - 1);

        if (panType.equalsIgnoreCase("T"))
        {
          while (resultSet.next())
          {
            outstandingDetail = new OutstandingDetail();

            periodicInfo.setBorrowerId(resultSet.getString(1));
            outstandingDetail.setCgpan(resultSet.getString(2));
            periodicInfo.setBorrowerName(resultSet.getString(3));
            outstandingDetail.setScheme(resultSet.getString(4));
            outstandingDetail.setTcSanctionedAmount(resultSet.getDouble(5));
            listOfOutstandingDetail.add(outstandingDetail);
          }
          resultSet.close();
          resultSet = null;

          getOutstandingDetailStmt.close();
          getOutstandingDetailStmt = null;

          size = listOfOutstandingDetail.size();

          for (int i = 0; i < size; i++)
          {
            OutstandingDetail outDtl = (OutstandingDetail)listOfOutstandingDetail.get(i);
            cgpan = outDtl.getCgpan();

            len = cgpan.length();

            appType = cgpan.substring(len - 2, len - 1);

            ArrayList outAmounts = new ArrayList();

            getOutDetailForCgpanStmt = connection.prepareCall("{?=call packGetTCOutStanding.funcTCOutStanding(?,?,?)}");
            getOutDetailForCgpanStmt.registerOutParameter(1, 4);
            getOutDetailForCgpanStmt.setString(2, cgpan);
            getOutDetailForCgpanStmt.registerOutParameter(3, -10);
            getOutDetailForCgpanStmt.registerOutParameter(4, 12);

            getOutDetailForCgpanStmt.execute();

            exception = getOutDetailForCgpanStmt.getString(4);

            error = getOutDetailForCgpanStmt.getInt(1);

            if (error == 0)
            {
              Log.log(5, "GMDAO", "GETTcOutstanding", "Success");
            }

            if (error == 1)
            {
              getOutDetailForCgpanStmt.close();
              getOutDetailForCgpanStmt = null;
              Log.log(2, "GMDAO", "GETTcOutstanding", "Exception " + exception);
              connection.rollback();
              throw new DatabaseException(exception);
            }

            cgpanResultSet = (ResultSet)getOutDetailForCgpanStmt.getObject(3);

            OutstandingAmount outAmount = null;

            while (cgpanResultSet.next())
            {
              outAmount = new OutstandingAmount();

              outAmount.setCgpan(cgpan);

              outAmount.setTcoId(cgpanResultSet.getString(1));
              outAmount.setTcPrincipalOutstandingAmount(cgpanResultSet.getDouble(2));
              outAmount.setTcOutstandingAsOnDate(cgpanResultSet.getDate(3));
              outAmounts.add(outAmount);
            }
            cgpanResultSet.close();
            cgpanResultSet = null;

            getOutDetailForCgpanStmt.close();
            getOutDetailForCgpanStmt = null;

            outDtl.setOutstandingAmounts(outAmounts);
            outDtls.add(outDtl);
          }
        }
        else if (panType.equalsIgnoreCase("W"))
        {
          while (resultSet.next())
          {
            outstandingDetail = new OutstandingDetail();

            periodicInfo.setBorrowerId(resultSet.getString(1));
            outstandingDetail.setCgpan(resultSet.getString(2));
            periodicInfo.setBorrowerName(resultSet.getString(3));
            outstandingDetail.setScheme(resultSet.getString(4));
            outstandingDetail.setWcFBSanctionedAmount(resultSet.getDouble(5));
            outstandingDetail.setWcNFBSanctionedAmount(resultSet.getDouble(6));

            listOfOutstandingDetail.add(outstandingDetail);
          }
          resultSet.close();
          resultSet = null;

          getOutstandingDetailStmt.close();
          getOutstandingDetailStmt = null;

          size = listOfOutstandingDetail.size();

          for (int i = 0; i < size; i++)
          {
            OutstandingDetail outDtl = (OutstandingDetail)listOfOutstandingDetail.get(i);
            cgpan = outDtl.getCgpan();

            len = cgpan.length();

            appType = cgpan.substring(len - 2, len - 1);

            ArrayList outAmounts = new ArrayList();

            getOutDetailForCgpanStmt = connection.prepareCall("{?=call packGetWCOutStanding.funcWCOutStanding(?,?,?)}");
            getOutDetailForCgpanStmt.registerOutParameter(1, 4);
            getOutDetailForCgpanStmt.setString(2, cgpan);
            getOutDetailForCgpanStmt.registerOutParameter(3, -10);
            getOutDetailForCgpanStmt.registerOutParameter(4, 12);

            getOutDetailForCgpanStmt.execute();

            exception = getOutDetailForCgpanStmt.getString(4);

            error = getOutDetailForCgpanStmt.getInt(1);

            if (error == 0)
            {
              Log.log(5, "GMDAO", "GETWcOutstanding", "Success");
            }

            if (error == 1)
            {
              getOutDetailForCgpanStmt.close();
              getOutDetailForCgpanStmt = null;
              Log.log(2, "GMDAO", "GETWcOutstanding", "Exception " + exception);
              connection.rollback();
              throw new DatabaseException(exception);
            }

            cgpanResultSet = (ResultSet)getOutDetailForCgpanStmt.getObject(3);

            OutstandingAmount outAmount = null;

            while (cgpanResultSet.next())
            {
              outAmount = new OutstandingAmount();
              outAmount.setCgpan(cgpan);
              outAmount.setWcoId(cgpanResultSet.getString(1));
              outAmount.setWcFBPrincipalOutstandingAmount(cgpanResultSet.getDouble(2));
              outAmount.setWcFBInterestOutstandingAmount(cgpanResultSet.getDouble(3));
              outAmount.setWcFBOutstandingAsOnDate(cgpanResultSet.getDate(4));
              outAmount.setWcNFBPrincipalOutstandingAmount(cgpanResultSet.getDouble(5));
              outAmount.setWcNFBInterestOutstandingAmount(cgpanResultSet.getDouble(6));
              outAmount.setWcNFBOutstandingAsOnDate(cgpanResultSet.getDate(7));

              outAmounts.add(outAmount);
            }

            cgpanResultSet.close();
            cgpanResultSet = null;

            getOutDetailForCgpanStmt.close();
            getOutDetailForCgpanStmt = null;

            outDtl.setOutstandingAmounts(outAmounts);
            outDtls.add(outDtl);
          }
        }
        periodicInfo.setOutstandingDetails(outDtls);
        periodicInfos.add(periodicInfo);
      }
      else if (type == 2)
      {
        functionName = "{?=call packGetOutstandingDtls.funcGetOutStandingforBorr(?,?,?)}";
        getOutstandingDetailStmt = connection.prepareCall(functionName);
        getOutstandingDetailStmt.registerOutParameter(1, 4);

        getOutstandingDetailStmt.setString(2, id);
        getOutstandingDetailStmt.registerOutParameter(3, -10);
        getOutstandingDetailStmt.registerOutParameter(4, 12);

        getOutstandingDetailStmt.execute();

        exception = getOutstandingDetailStmt.getString(4);

        int error = getOutstandingDetailStmt.getInt(1);

        if (error == 0)
        {
          Log.log(5, "GMDAO", "GET OTS For Borr Name SP", "Success");
        }

        if (error == 1)
        {
          getOutstandingDetailStmt.close();
          getOutstandingDetailStmt = null;
          Log.log(2, "GMDAO", "getOutstandingDetailsForBorrower", "Exception " + exception);
          connection.rollback();
          throw new DatabaseException(exception);
        }

        resultSet = (ResultSet)getOutstandingDetailStmt.getObject(3);

        boolean firstTime = true;

        String cgpan1 = null;

        while (resultSet.next())
        {
          outstandingDetail = new OutstandingDetail();

          if (firstTime)
          {
            periodicInfo.setBorrowerId(resultSet.getString(1));
            periodicInfo.setBorrowerName(resultSet.getString(4));
            firstTime = false;
          }
          cgpan1 = resultSet.getString(2);
          if (cgpan1 != null)
          {
            outstandingDetail.setCgpan(cgpan1);
            outstandingDetail.setScheme(resultSet.getString(3));
            outstandingDetail.setTcSanctionedAmount(resultSet.getDouble(5));
            outstandingDetail.setWcFBSanctionedAmount(resultSet.getDouble(6));
            outstandingDetail.setWcNFBSanctionedAmount(resultSet.getDouble(7));

            listOfOutstandingDetail.add(outstandingDetail);
          }
        }
        resultSet.close();
        resultSet = null;

        getOutstandingDetailStmt.close();
        getOutstandingDetailStmt = null;

        size = listOfOutstandingDetail.size();

        for (int i = 0; i < size; i++)
        {
          OutstandingDetail outDtl = (OutstandingDetail)listOfOutstandingDetail.get(i);
          cgpan = outDtl.getCgpan();

          len = cgpan.length();

          appType = cgpan.substring(len - 2, len - 1);

          ArrayList outAmounts = new ArrayList();

          if (appType.equalsIgnoreCase("T"))
          {
            getOutDetailForCgpanStmt = connection.prepareCall("{?=call packGetTCOutStanding.funcTCOutStanding(?,?,?)}");
            getOutDetailForCgpanStmt.registerOutParameter(1, 4);
            getOutDetailForCgpanStmt.setString(2, cgpan);
            getOutDetailForCgpanStmt.registerOutParameter(3, -10);
            getOutDetailForCgpanStmt.registerOutParameter(4, 12);

            getOutDetailForCgpanStmt.execute();

            exception = getOutDetailForCgpanStmt.getString(4);

            error = getOutDetailForCgpanStmt.getInt(1);

            if (error == 0)
            {
              Log.log(5, "GMDAO", "GETTcOutstanding", "Success");
            }

            if (error == 1)
            {
              getOutDetailForCgpanStmt.close();
              getOutDetailForCgpanStmt = null;
              Log.log(2, "GMDAO", "GETTcOutstanding", "Exception " + exception);
              connection.rollback();
              throw new DatabaseException(exception);
            }

            cgpanResultSet = (ResultSet)getOutDetailForCgpanStmt.getObject(3);
            OutstandingAmount outAmount = null;

            while (cgpanResultSet.next())
            {
              outAmount = new OutstandingAmount();
              outAmount.setCgpan(cgpan);
              outAmount.setTcoId(cgpanResultSet.getString(1));
              outAmount.setTcPrincipalOutstandingAmount(cgpanResultSet.getDouble(2));
              outAmount.setTcOutstandingAsOnDate(cgpanResultSet.getDate(3));
              outAmounts.add(outAmount);
            }
            cgpanResultSet.close();
            cgpanResultSet = null;

            getOutDetailForCgpanStmt.close();
            getOutDetailForCgpanStmt = null;
          }
          else if ((appType.equalsIgnoreCase("W")) || (appType.equalsIgnoreCase("R")))
          {
            getOutDetailForCgpanStmt = connection.prepareCall("{?=call packGetWCOutStanding.funcWCOutStanding(?,?,?)}");
            getOutDetailForCgpanStmt.registerOutParameter(1, 4);
            getOutDetailForCgpanStmt.setString(2, cgpan);
            getOutDetailForCgpanStmt.registerOutParameter(3, -10);
            getOutDetailForCgpanStmt.registerOutParameter(4, 12);

            getOutDetailForCgpanStmt.execute();

            exception = getOutDetailForCgpanStmt.getString(4);

            error = getOutDetailForCgpanStmt.getInt(1);

            if (error == 0)
            {
              Log.log(5, "GMDAO", "GET WC Outstanding", "Success");
            }
            if (error == 1)
            {
              getOutDetailForCgpanStmt.close();
              getOutDetailForCgpanStmt = null;
              Log.log(2, "GMDAO", "GET WC Outstanding", "Exception " + exception);
              connection.rollback();
              throw new DatabaseException(exception);
            }

            cgpanResultSet = (ResultSet)getOutDetailForCgpanStmt.getObject(3);

            OutstandingAmount outAmount = null;

            while (cgpanResultSet.next())
            {
              outAmount = new OutstandingAmount();

              outAmount.setCgpan(cgpan);

              outAmount.setWcoId(cgpanResultSet.getString(1));
              outAmount.setWcFBPrincipalOutstandingAmount(cgpanResultSet.getDouble(2));
              outAmount.setWcFBInterestOutstandingAmount(cgpanResultSet.getDouble(3));
              outAmount.setWcFBOutstandingAsOnDate(cgpanResultSet.getDate(4));
              outAmount.setWcNFBPrincipalOutstandingAmount(cgpanResultSet.getDouble(5));
              outAmount.setWcNFBInterestOutstandingAmount(cgpanResultSet.getDouble(6));
              outAmount.setWcNFBOutstandingAsOnDate(cgpanResultSet.getDate(7));
              outAmounts.add(outAmount);
            }
            cgpanResultSet.close();
            cgpanResultSet = null;

            getOutDetailForCgpanStmt.close();
            getOutDetailForCgpanStmt = null;
          }
          outDtl.setOutstandingAmounts(outAmounts);
          outDtls.add(outDtl);
        }
        periodicInfo.setOutstandingDetails(outDtls);
        periodicInfos.add(periodicInfo);
      }
      connection.commit();
    }
    catch (SQLException exception) {
      Log.logException(exception);
      throw new DatabaseException(exception.getMessage());
    }
    finally
    {
      DBConnection.freeConnection(connection);
    }
    Log.log(4, "GMDAO", "getOutstandingDetails", "Exited");

    return periodicInfos;
  }

  public TreeMap getBidsForApproval(String memberID)
    throws DatabaseException
  {
    Log.log(4, "GMDAO", "getBidsForApproval", "Entered");
    ArrayList borrowerIds = new ArrayList();
    TreeMap bidsList = new TreeMap();
   //System.out.println("getBidsForApproval memberID "+memberID);
    Connection connection = DBConnection.getConnection(false);
    try
    {
      CallableStatement memberIdsList = connection.prepareCall("{?=call packGetBidsForModification.funcGetBidsForModification(?,?,?)}");

      memberIdsList.registerOutParameter(1, 4);
      memberIdsList.setString(2, memberID);
      memberIdsList.registerOutParameter(3, -10);
      memberIdsList.registerOutParameter(4, 12);

      memberIdsList.execute();
      int memberIdValue = memberIdsList.getInt(1);

      Log.log(5, "GMDAO", "getBidsForApproval", "memberIdValue :" + memberIdValue);

      if (memberIdValue == 1)
      {
        String error = memberIdsList.getString(4);

        memberIdsList.close();
        memberIdsList = null;

        connection.rollback();

        Log.log(5, "GMDAO", "getBidsForApproval", "error:" + error);
//System.out.println("11584 Something"+error);
        throw new DatabaseException("Something ["+error+"] went wrong, Please contact Support[support@cgtmse.in] team");
       // throw new DatabaseException(error);
      }

      ResultSet memberIdResult = (ResultSet)memberIdsList.getObject(3);
     // ArrayList borrowersList = new ArrayList();
      ////System.out.println("total row");memberIdResult.getRow()
      TreeMap borrowersList = new TreeMap();
      String strborrowersListArr[];
      while (memberIdResult.next())
      {
    	  strborrowersListArr= new String[2];
        String memberId = memberIdResult.getString(1);
        if (bidsList.containsKey(memberId))
        {
         // borrowersList = (ArrayList)bidsList.get(memberId);
        }
        else
        {
        ///  borrowersList = new ArrayList();
        }
       // borrowersList.add(memberIdResult.getString(2));
        strborrowersListArr[0]=memberIdResult.getString(2);
        strborrowersListArr[1]=memberIdResult.getString(3);
        //bidsList.put(memberId, borrowersList);
        borrowersList.put(memberIdResult.getString(2), memberIdResult.getString(3));
        //System.out.println("getBidsForApproval remarks "+memberIdResult.getString(3));
        bidsList.put(memberId, strborrowersListArr);
      }

    }
    catch (SQLException exception)
    {
    	//exception.printStackTrace();
      try
      {
        connection.rollback();
      }
      catch (SQLException localSQLException1)
      {
      }
    //  System.out.println("11626 Something"+exception.getMessage());
      throw new DatabaseException("Something ["+exception.getMessage()+"] went wrong, Please contact Support[support@cgtmse.in] team");
    }
    finally
    {
      DBConnection.freeConnection(connection);
    }

    Log.log(4, "RpDAO", "getMemberIdsForExcess", "Exited");
   // //System.out.println("getBidsForApproval bidsList"+bidsList);
    return bidsList;
  }

  public TreeMap getBidsForPerInfoApproval()
    throws DatabaseException
  {
    Log.log(4, "GMDAO", "getBidsForPerInfoApproval", "Entered");
    ArrayList borrowerIds = new ArrayList();
    TreeMap bidsList = new TreeMap();

    Connection connection = DBConnection.getConnection(false);
    try
    {
      CallableStatement memberIdsList = connection.prepareCall("{?=call packGetBidsForNPARecovery.funcGetBidsForNPARecovery(?,?)}");

      memberIdsList.registerOutParameter(1, 4);
      memberIdsList.registerOutParameter(2, -10);
      memberIdsList.registerOutParameter(3, 12);

      memberIdsList.execute();
      int memberIdValue = memberIdsList.getInt(1);

      Log.log(5, "GMDAO", "getBidsForPerInfoApproval", "memberIdValue :" + memberIdValue);

      if (memberIdValue == 1)
      {
        String error = memberIdsList.getString(3);

        memberIdsList.close();
        memberIdsList = null;

        connection.rollback();

        Log.log(5, "GMDAO", "getBidsForPerInfoApproval", "error:" + error);

        throw new DatabaseException(error);
      }

      ResultSet memberIdResult = (ResultSet)memberIdsList.getObject(2);
      ArrayList borrowersList = new ArrayList();
      while (memberIdResult.next())
      {
        String memberId = memberIdResult.getString(1);
        Log.log(5, "GMDAO", "getBidsForPerInfoApproval", "memberId:" + memberId);
        if (bidsList.containsKey(memberId))
        {
          borrowersList = (ArrayList)bidsList.get(memberId);
        }
        else
        {
          borrowersList = new ArrayList();
        }
        borrowersList.add(memberIdResult.getString(2));
        Log.log(5, "GMDAO", "getBidsForPerInfoApproval", "memberIdResult.getString(2):" + memberIdResult.getString(2));

        bidsList.put(memberId, borrowersList);
      }

      CallableStatement borrowerIdsList = connection.prepareCall("{?=call packGetBidsForOutDisRep.funcGetBidsForOutDisRep(?,?)}");

      borrowerIdsList.registerOutParameter(1, 4);
      borrowerIdsList.registerOutParameter(2, -10);
      borrowerIdsList.registerOutParameter(3, 12);

      borrowerIdsList.execute();
      int borrowerIdValue = borrowerIdsList.getInt(1);

      Log.log(5, "GMDAO", "getBidsForPerInfoApproval", "memberIdValue :" + borrowerIdValue);

      if (borrowerIdValue == 1)
      {
        String error = borrowerIdsList.getString(3);

        borrowerIdsList.close();
        borrowerIdsList = null;

        connection.rollback();

        Log.log(5, "GMDAO", "getBidsForPerInfoApproval", "error:" + error);

        throw new DatabaseException(error);
      }

      ResultSet borrowerIdResult = (ResultSet)borrowerIdsList.getObject(2);
       borrowersList = new ArrayList();
      while (borrowerIdResult.next())
      {
        String memberId = borrowerIdResult.getString(1);
        Log.log(5, "GMDAO", "getBidsForPerInfoApproval", "memberId 1:" + memberId);
        if (bidsList.containsKey(memberId))
        {
          borrowersList = (ArrayList)bidsList.get(memberId);
        }
        else
        {
          borrowersList = new ArrayList();
        }
        if (!borrowersList.contains(borrowerIdResult.getString(2)))
        {
          borrowersList.add(borrowerIdResult.getString(2));
        }

        bidsList.put(memberId, borrowersList);
      }

    }
    catch (SQLException exception)
    {
      Log.log(2, "GMDAO", "getBidsForPerInfoApproval", exception.getMessage());
      Log.logException(exception);
      try
      {
        connection.rollback();
      }
      catch (SQLException localSQLException1)
      {
      }
      throw new DatabaseException("Unable to get Member and Borrower Ids.");
    }
    finally
    {
      DBConnection.freeConnection(connection);
    }

    Log.log(4, "RpDAO", "getBidsForPerInfoApproval", "Exited");

    return bidsList;
  }
  
    /* added by upchar@path*/
             public Vector getCGPANDetailsPeriodicInfo(String borrowerId, String memberId) throws DatabaseException
             {
              //   //System.out.println("----GMDAO----getCGPANDetailsNPA-------");
                 Log.log(Log.INFO,"CPDAO","getCGPANDetailsForBorrowerId()","Entered!");
                 HashMap cgpandetails = null;
                 String query = null;
   
                                 query = " SELECT A.CGPAN,APP_LOAN_TYPE,APP_GUAR_START_DATE_TIME,APP_SANCTION_DT," +
                                 " DECODE(APP_REAPPROVE_AMOUNT,NULL,APP_APPROVED_AMOUNT,APP_REAPPROVE_AMOUNT) app_approved_amount,TRM_INTEREST_RATE RATE,APP_STATUS " + 
                                 " FROM APPLICATION_DETAIL A,SSI_DETAIL S,TERM_LOAN_DETAIL T " + 
                                 " WHERE A.SSI_rEFERENCE_NUMBER = S.SSI_rEFERENCE_NUMBER " + 
                                 " AND A.APP_rEF_NO = T.APP_REF_NO " + 
                                 " AND APP_LOAN_TYPE IN ('TC','CC') " + 
                                 " AND BID = '" + borrowerId +
                                 "' AND  LTRIM(RTRIM(UPPER(A.app_status))) NOT IN ('CL','XE','LC','RE','EX') " + 
                                 " UNION ALL " + 
                                 " SELECT A.CGPAN,APP_LOAN_TYPE,APP_GUAR_START_DATE_TIME,APP_SANCTION_DT," +
                                 " DECODE(APP_REAPPROVE_AMOUNT,NULL,APP_APPROVED_AMOUNT,APP_REAPPROVE_AMOUNT) app_approved_amount,WCP_INTEREST RATE,APP_STATUS " + 
                                 " FROM APPLICATION_DETAIL A,SSI_DETAIL S,WORKING_CAPITAL_DETAIL W " + 
                                 " WHERE A.SSI_rEFERENCE_NUMBER = S.SSI_rEFERENCE_NUMBER " + 
                                 " AND A.APP_rEF_NO = W.APP_REF_NO " + 
                                 " AND APP_LOAN_TYPE IN ('WC') " + 
                                 " AND BID = '" + borrowerId +
                                 "' AND  LTRIM(RTRIM(UPPER(A.app_status))) NOT IN ('CL','XE','LC','RE','EX')";

                 
                 Connection conn = null;
                 Statement stmt = null;
                 ResultSet rs = null; 
                 Vector allcgpandetails = new Vector();
                 Map cgpanMap = null;
                 Vector claimcgpandetails = new Vector();
                 try
                 {                
                      conn = DBConnection.getConnection();
                      stmt = conn.createStatement();
                      rs = stmt.executeQuery(query);
                      System.out.println("getCGPANDetailsPeriodicInfo"+query);
                      while(rs.next()){                   
                          cgpanMap = new HashMap();
                          String cgpan = rs.getString("cgpan");
                          Date guarDate = rs.getDate("app_guar_start_date_time");
                          Date sancDate = rs.getDate("app_sanction_dt");
                          String loanType = rs.getString("app_loan_type");
                //        String cgpanstatus = rs.getString("app_status");
                          double approvedAmount = rs.getDouble("app_approved_amount");
                          double rate = rs.getDouble("rate");
                          String appstatus = (String)rs.getString("app_status");
                          
                          cgpanMap.put("CGPAN",cgpan);
                          cgpanMap.put("GUARANTEE_START_DT",guarDate);
                          cgpanMap.put("SANCTION_DT",sancDate);
                          cgpanMap.put("CGPAN_LOAN_TYPE",loanType); 
              //          cgpanMap.put("APPLICATION_STATUS",cgpanstatus);
                          cgpanMap.put("APPROVED_AMOUNT",new Double(approvedAmount));
                          cgpanMap.put("RATE",rate);
                          cgpanMap.put("STATUS",appstatus);
                          
                          allcgpandetails.add(cgpanMap);                        
                      } 
                      rs.close();
                      rs = null;
                      stmt.close();
                      stmt = null;
                     cgpanMap = null;
                     stmt = conn.createStatement();
                     rs = stmt.executeQuery(query);
                     
                     
                     query = " select c2.cgpan,DECODE(APP_REAPPROVE_AMOUNT,NULL,APP_APPROVED_AMOUNT,APP_REAPPROVE_AMOUNT) app_approved_amount," +
                            " c.clm_status,app_status,t.TRM_INTEREST_RATE RATE,a.APP_LOAN_TYPE,a.APP_SANCTION_DT,a.APP_GUAR_START_DATE_TIME " +
                            " from claim_detail_temp c,claim_tc_detail_temp c2,application_detail a,SSI_DETAIL S,TERM_LOAN_DETAIL T where c.clm_ref_no=c2.clm_ref_no " +
                            " AND A.APP_rEF_NO = T.APP_REF_NO AND APP_LOAN_TYPE IN ('TC','CC') " +
                            " and app_status in('AP','EX') and A.SSI_rEFERENCE_NUMBER = S.SSI_rEFERENCE_NUMBER and a.cgpan=c2.cgpan and s.bid='"+borrowerId+"'"+
                            " union all "+
                            " select c2.cgpan,DECODE(APP_REAPPROVE_AMOUNT,NULL,APP_APPROVED_AMOUNT,APP_REAPPROVE_AMOUNT) app_approved_amount," +
                            " c.clm_status,a.app_status,w.WCP_INTEREST RATE,a.APP_LOAN_TYPE,a.APP_SANCTION_DT,a.APP_GUAR_START_DATE_TIME " +
                            " from claim_detail_temp c,claim_wc_detail_temp c2,application_detail a,SSI_DETAIL S,WORKING_CAPITAL_DETAIL W where c.clm_ref_no=c2.clm_ref_no " +
                            " AND A.APP_rEF_NO = W.APP_REF_NO AND APP_LOAN_TYPE IN ('WC') " +
                            " and app_status in('AP','EX') and A.SSI_rEFERENCE_NUMBER = S.SSI_rEFERENCE_NUMBER and a.cgpan=c2.cgpan and s.bid='"+borrowerId+"'";
                            
                     stmt = conn.createStatement();
                     rs = stmt.executeQuery(query);
                     System.out.println("getCGPANDetailsPeriodicInfo="+query);
                     while(rs.next()){
                         cgpanMap = new HashMap();
                         String cgpan = rs.getString("cgpan");
                         Date guarDate = rs.getDate("app_guar_start_date_time");
                         Date sancDate = rs.getDate("app_sanction_dt");
                         String loanType = rs.getString("app_loan_type");
                         //        String cgpanstatus = rs.getString("app_status");
                         double approvedAmount = rs.getDouble("app_approved_amount");
                         double rate = rs.getDouble("rate");
                         String appstatus = (String)rs.getString("app_status");
                         String clmstatus = rs.getString("clm_status");
                        
                         cgpanMap.put("CGPAN",cgpan);
                         cgpanMap.put("GUARANTEE_START_DT",guarDate);
                         cgpanMap.put("SANCTION_DT",sancDate);
                         cgpanMap.put("CGPAN_LOAN_TYPE",loanType); 
                         //          cgpanMap.put("APPLICATION_STATUS",cgpanstatus);
                         cgpanMap.put("APPROVED_AMOUNT",new Double(approvedAmount));
                         cgpanMap.put("RATE",rate);
                         cgpanMap.put("STATUS",appstatus);
                     
                        claimcgpandetails.add(cgpanMap);
                     }
                     if(claimcgpandetails.size() > 0){
                        //allcgpandetails.clear();
                        allcgpandetails.removeAllElements();
                        allcgpandetails.addAll(claimcgpandetails);
                        //return claimcgpandetails;
                     }
                 }
                 catch(SQLException sqlexception)
                 {
                      Log.log(Log.ERROR,"CPDAO","getCGPANDetailsForBorrowerId()","Error retrieving CGPAN Details for the Borrower!");
                      throw new DatabaseException(sqlexception.getMessage());
                 }
                 finally{
                      DBConnection.freeConnection(conn);
                 }
                 
                return allcgpandetails;
             }
             
             
    public HashMap getPrimarySecurityAndNetworthOfGuarantorsAsOnSanc(String borrowerId, String memberId) throws DatabaseException
            {
                         Log.log(Log.ERROR,"CPDAO","getPrimarySecurityAndNetworthOfGuarantors()","Entered.");
                         // //System.out.println("Control in getPrimarySecurityAndNetworthOfGuarantors method.");
                         com.cgtsi.claim.CPDAO dao = new  com.cgtsi.claim.CPDAO();
                         GMDAO dao2 = new GMDAO();
                         Vector cgpans = dao2.getCGPANDetailsPeriodicInfo(borrowerId,memberId);//dao.getCGPANDetailsForBorrowerId(borrowerId,memberId);
                         
                         HashMap cgpanDtl = null;
                         String cgpan = null;
                         String appRefNumber = null;

                         double totalNetWorth = 0.0;
                         double totalValOfLand = 0.0;
                         double totalValOfMachine = 0.0;
                         double totalValOfBuilding = 0.0;
                         double totalValOfOFMA = 0.0;
                         double totalValOfCurrAssets = 0.0;
                         double totalValOfOthers = 0.0;

                         CallableStatement callableStmt = null;
                         Connection conn = null;
                         HashMap completeDtls = new HashMap();
                         if(cgpans == null)
                         {
                                 return null;
                         }
                         try
                         {
                                 conn = DBConnection.getConnection();
                         for(int j=0; j<cgpans.size(); j++)
                         {
                                 cgpanDtl = (HashMap)cgpans.elementAt(j);
                                 if(cgpanDtl == null)
                                 {
                                         continue;
                                 }
                                 // //System.out.println("From CPDAO -> Printing HashMap :" + cgpanDtl);
                                 cgpan = (String)cgpanDtl.get(ClaimConstants.CLM_CGPAN);
                                 if(cgpan == null)
                                 {
                                         continue;
                                 }
                                 appRefNumber = dao.getAppRefNumber (cgpan);
                                 if(appRefNumber == null)
                                 {
                                         continue;
                                 }
                                 // //System.out.println("1");
                                 callableStmt=conn.prepareCall(
                                                 "{?=call packGetPersonalGuarantee.funcGetPerGuarforAppRef(?,?,?)}");

                                 callableStmt.setString(2,appRefNumber); //Application Ref Number

                                 callableStmt.registerOutParameter(1,Types.INTEGER);
                                 callableStmt.registerOutParameter(4,Types.VARCHAR);

                                 callableStmt.registerOutParameter(3,Constants.CURSOR);
                                 // //System.out.println("2");
                                 callableStmt.executeQuery();
                                 // //System.out.println("3");
                                 int status=callableStmt.getInt(1);

                                 Log.log(Log.DEBUG,"CPDAO","getPrimarySecurityAndNetworthOfGuarantors","Status :" + status);

                                  if(status==Constants.FUNCTION_FAILURE){
                                  // //System.out.println("4");
                                          String error = callableStmt.getString(4);

                                          callableStmt.close();
                                         callableStmt=null;

                                         conn.rollback();

                                         Log.log(Log.ERROR,"ApplicationDAO","getPrimarySecurityAndNetworthOfGuarantors","Error Message:" + error);

                                         throw new DatabaseException(error);
                                  }      else {
                                  // //System.out.println("5");
                                                 ResultSet guarantorsResults=(ResultSet)callableStmt.getObject(3);
                                                 int i=0;
                                                 while(guarantorsResults.next())
                                                 {
                                                 // //System.out.println("6");
                                                                 if (i==0)
                                                                 {
                                                                         totalNetWorth = totalNetWorth + guarantorsResults.getDouble(2);
                                                                 }
                                                                 if (i==1)
                                                                 {
                                                                         totalNetWorth = totalNetWorth + guarantorsResults.getDouble(2);
                                                                 }
                                                                 if (i==2)
                                                                 {
                                                                         totalNetWorth = totalNetWorth + guarantorsResults.getDouble(2);
                                                                 }
                                                                 if (i==3)
                                                                 {
                                                                         totalNetWorth = totalNetWorth + guarantorsResults.getDouble(2);
                                                                 }
                                                                 i++;
                                                 }
                                                 // //System.out.println("7");
                                                 guarantorsResults.close();
                                                 guarantorsResults=null;
                                               callableStmt.close();
                                                 callableStmt=null;
                                         }
                                 }
                                 // //System.out.println("8");
                                 completeDtls.put("networth", new Double(totalNetWorth));
                                 // //System.out.println("1 -> Printing Complete Details :" +completeDtls );
                                 for(int i=0; i<cgpans.size(); i++)
                                 {
                                         cgpanDtl = (HashMap)cgpans.elementAt(i);
                                         if(cgpanDtl == null)
                                         {
                                                 continue;
                                         }
                                         // //System.out.println("From CPDAO -> Printing HashMap :" + cgpanDtl);
                                         cgpan = (String)cgpanDtl.get(ClaimConstants.CLM_CGPAN);
                                         if(cgpan == null)
                                         {
                                                 continue;
                                         }
                                         appRefNumber = dao.getAppRefNumber(cgpan);
                                         if(appRefNumber == null)
                                         {
                                                 continue;
                                         }
                                         // Retrieving the Primary Security Details
                                         callableStmt=conn.prepareCall(
                                                         "{?=call packGetPrimarySecurity.funcGetPriSecforAppRef(?,?,?)}");

                                         callableStmt.setString(2,appRefNumber); //Application Reference Number

                                         callableStmt.registerOutParameter(1,Types.INTEGER);
                                         callableStmt.registerOutParameter(4,Types.VARCHAR);

                                         callableStmt.registerOutParameter(3,Constants.CURSOR);

                                         callableStmt.executeQuery();
                                         int status=callableStmt.getInt(1);

                                          if(status==Constants.FUNCTION_FAILURE){

                                                  String error = callableStmt.getString(4);

                                                  callableStmt.close();
                                                 callableStmt=null;

                                                 conn.rollback();

                                                 throw new DatabaseException(error);
                                          }      else {
                                                         ResultSet psResults=(ResultSet)callableStmt.getObject(3);
                                                                 while(psResults.next())
                                                                 {
                                                                         if ((psResults.getString(1)).equals("Land"))
                                                                         {
                                                                                 totalValOfLand = totalValOfLand + psResults.getDouble(3);
                                                                         }if ((psResults.getString(1)).equals("Building"))
                                                                         {
                                                                                 totalValOfBuilding = totalValOfBuilding + psResults.getDouble(3);

                                                                         }if ((psResults.getString(1)).equals("Machinery"))
                                                                         {
                                                                                 totalValOfMachine = totalValOfMachine + psResults.getDouble(3);

                                                                         }if ((psResults.getString(1)).equals("Fixed Assets"))
                                                                         {
                                                                                 totalValOfOFMA = totalValOfOFMA + psResults.getDouble(3);

                                                                         }if ((psResults.getString(1)).equals("Current Assets"))
                                                                         {
                                                                                 totalValOfCurrAssets = totalValOfCurrAssets + psResults.getDouble(3);

                                                                         }if ((psResults.getString(1)).equals("Others"))
                                                                         {
                                                                                 totalValOfOthers = totalValOfOthers + psResults.getDouble(3);

                                                                         }


                                                                 }
                                                                 psResults.close();
                                                                 psResults=null;
                                                                 callableStmt.close();
                                                                 callableStmt=null;
                                                 }
                                         }

                                 }
                                 catch(SQLException sqlex)
                                 {
                                         // sqlex.getCause();
                                         // sqlex.printStackTrace();
                                         Log.log(Log.ERROR,"CPDAO","getPrimarySecurityAndNetworthOfGuarantors()","Error :" + sqlex.getMessage());
                                         throw new DatabaseException(sqlex.getMessage());
                                 }
                                 finally{
                                         DBConnection.freeConnection(conn);
                                 }

                                 completeDtls.put("land",new Double(totalValOfLand));
                                 completeDtls.put("building",new Double(totalValOfBuilding));
                                 completeDtls.put("machine",new Double(totalValOfMachine));
                                 completeDtls.put("fixed_mov_asset",new Double(totalValOfOFMA));
                                 completeDtls.put("current_asset", new Double(totalValOfCurrAssets));
                                 completeDtls.put("others", new Double(totalValOfOthers));

                                 // //System.out.println("2- >Printing Complete Details :" +completeDtls );
                                 Log.log(Log.ERROR,"CPDAO","getPrimarySecurityAndNetworthOfGuarantors()","Exited");
                                 return completeDtls;
            }
            
    public Vector getCgpanDetailsAsOnNpa(String npaId)throws DatabaseException{
      
           Connection conn = null;
           Statement stmt = null;
           ResultSet rs = null;
           String query = null;
           Vector allCgpans = new Vector();
           Map tcmap = null;
           Map wcmap = null;
           
           conn = DBConnection.getConnection();      

           try {
               stmt = conn.createStatement();
               
               /*logic to get npa tc details*/
               query = "select cgpan,ntd_first_disbursement_dt,ntd_last_disbursement_dt,ntd_first_instalment_dt" +
               ",ntd_principal_repay_amt,ntd_interest_repay_amt,ntd_principal_moratorium,ntd_interest_moratorium" +
               ",ntd_total_disb_amt,ntd_npa_principal_os_amt,ntd_npa_interest_os_amt from npa_tc_detail_temp where npa_id='" + npaId +
               "'" ;
               
               rs = stmt.executeQuery(query);
               
               while(rs.next()){
                   tcmap = new HashMap();
                   tcmap.put("CGPAN",rs.getString(1));
                   tcmap.put("FIRSTDISBDT",rs.getDate(2));
                   tcmap.put("LASTDISBDT",rs.getDate(3));
                   tcmap.put("FIRSTINSTDT",rs.getDate(4));
                   tcmap.put("PRINCIPALREPAY",rs.getDouble(5));
                   tcmap.put("INTERESTREPAY",rs.getDouble(6));
                   tcmap.put("PRINCIPALMORATORIUM",rs.getInt(7));
                   tcmap.put("INTERESTMORATORIUM",rs.getInt(8));
                   tcmap.put("TOTALDISBAMT",rs.getDouble(9));
                   tcmap.put("PRINCIPALOS",rs.getDouble(10));
                   tcmap.put("INTERESTOS",rs.getDouble(11));
                   allCgpans.add(tcmap);
               }
               
              
               /*logic to get npa wc details*/
                query = "select cgpan,nwd_npa_principal_os_amt,nwd_npa_interest_os_amt from npa_wc_detail_temp where npa_id='" + npaId +
                "'";
               
               rs = stmt.executeQuery(query);
               
               while(rs.next()){
                   wcmap = new HashMap();
                   wcmap.put("CGPAN",rs.getString(1));
                   wcmap.put("PRINCIPALOS",rs.getDouble(2));
                   wcmap.put("INTERESTOS",rs.getDouble(3));
                   allCgpans.add(wcmap);
               }
               
              
               
           } catch (SQLException e) {
           throw new DatabaseException();
           }finally{
               try{
               rs.close();
               }catch(SQLException e){
               
               }
               rs = null;
               try{
               stmt.close();
               }catch(SQLException e){
               
               }
               stmt = null;
               if(conn != null){
                 
                   DBConnection.freeConnection(conn);
                   
                   conn = null;
               }
           }
           return allCgpans;
       }
    
    
  //koteswar start
    public boolean updateNPADetailsnew(String userId,NPADetails npaDetails,Vector tcVector,Vector wcVector,Map securityMap)throws DatabaseException
       {
                    Log.log(Log.INFO,"GMDAO","updateNPADetails","Entered");
                    Connection connection = DBConnection.getConnection(false);
                    CallableStatement addNPADetailsStmt = null;
                    int updateStatus=0;

                   
                    boolean addNPADetailsStatus = false;
                   

                    java.util.Date utilDate;
                    java.sql.Date sqlDate;
                 //   String npaId = null;
                    String npaexception = null;

                    try
                    {
                    	
                    	//koteswar start
                        //KOTESWAR FOR TC OUTSAND AMT AND DATE FETCHING
                        
                        for(int i=0;i<tcVector.size();i++){
                            Map map = (java.util.Map)tcVector.get(i);
                            
                            addNPADetailsStmt = connection.prepareCall("{?=call funcInsertNpaTCoutStndDetFYs(?,?,?,?,?)}");
                            addNPADetailsStmt.registerOutParameter(1,Types.INTEGER);
                            addNPADetailsStmt.registerOutParameter(6,Types.VARCHAR);
                            addNPADetailsStmt.setString(2,npaDetails.getNpaId());
                            addNPADetailsStmt.setString(3,(String)map.get("CGPAN"));
                            addNPADetailsStmt.setDouble(4,(Double)map.get("OUTSTAND_FOR_TC_CUR_FY"));
                            addNPADetailsStmt.setString(5,userId);
                            addNPADetailsStmt.executeQuery();
                            updateStatus=addNPADetailsStmt.getInt(1);
                            npaexception = addNPADetailsStmt.getString(6);
                            
                            if (updateStatus==Constants.FUNCTION_SUCCESS)
                            {
                                    addNPADetailsStatus = true;
                                    Log.log(Log.DEBUG,"GMDAO","insertNPADetails","insertnpadetails - SUCCESS");

                                    connection.commit();
                            }
                            else if (updateStatus==Constants.FUNCTION_FAILURE)
                            {
                                    connection.rollback();
                                    addNPADetailsStatus =false;
                                    addNPADetailsStmt.close();
                                    addNPADetailsStmt = null;
                                    Log.log(Log.ERROR,"GMDAO","insert NPA Details","Exception "+npaexception);
                       
                                    throw new DatabaseException(npaexception);
                            }
                        }
                        
                        
                        for(int i=0;i<wcVector.size();i++){
                            Map map = (java.util.Map)wcVector.get(i);
                            
                            addNPADetailsStmt = connection.prepareCall("{?=call funcInsertNpaWCoutStndDetFYs(?,?,?,?,?)}");
                            addNPADetailsStmt.registerOutParameter(1,Types.INTEGER);
                            addNPADetailsStmt.registerOutParameter(6,Types.VARCHAR);
                            addNPADetailsStmt.setString(2,npaDetails.getNpaId());
                            addNPADetailsStmt.setString(3,(String)map.get("CGPAN"));
                            addNPADetailsStmt.setDouble(4,(Double)map.get("OUTSTAND_FOR_WC_CUR_FY"));
                            addNPADetailsStmt.setString(5,userId);
                            addNPADetailsStmt.executeQuery();
                            updateStatus=addNPADetailsStmt.getInt(1);
                            npaexception = addNPADetailsStmt.getString(6);
                            
                            if (updateStatus==Constants.FUNCTION_SUCCESS)
                            {
                                    addNPADetailsStatus = true;
                                    Log.log(Log.DEBUG,"GMDAO","insertNPADetails","insertnpadetails - SUCCESS");

                                    connection.commit();
                            }
                            else if (updateStatus==Constants.FUNCTION_FAILURE)
                            {
                                    connection.rollback();
                                    addNPADetailsStatus =false;
                                    addNPADetailsStmt.close();
                                    addNPADetailsStmt = null;
                                    Log.log(Log.ERROR,"GMDAO","insert NPA Details","Exception "+npaexception);
                       
                                    throw new DatabaseException(npaexception);
                            }
                        }

                        
                        
                      

                    	
                            
                     connection.commit();  
                    }catch (SQLException exception)
                    {
               try {
                   connection.rollback();
               } catch (SQLException e) {
                   // TODO
               }
               Log.logException(exception);
                            throw new DatabaseException(exception.getMessage());
                    }finally
                    {
                                    DBConnection.freeConnection(connection);
                    }
                    Log.log(Log.INFO,"GMDAO","updateNPADetails","Exited");
                    return addNPADetailsStatus;
       }
    
  //DKR validateCgpanForTenureModificationNew  FOR CGPAN

    public String validateCgpanForTenureModificationNew(String cgpan ,String memberId)throws DatabaseException
	{
			String message="";
		    boolean cgpanFlag = false;
			String bankId = memberId.substring(0, 4);
			String zoneId = memberId.substring(4, 8);
			String branchId = memberId.substring(8, 12);			
			
			Connection connection = DBConnection.getConnection(false);
			CallableStatement getTenureDetail = null;		

			try 
			{
         
				String exception = "";
				String functionName = null;
				functionName =  "{?=call Funcgetssidetailfortenure(?,?,?,?,?,?,?,?,?,?,?,?)}";
				//System.out.println("bankId"+bankId+"zoneId"+zoneId+"branchId"+branchId);
				getTenureDetail = connection.prepareCall(functionName);
				getTenureDetail.registerOutParameter(1, java.sql.Types.INTEGER);
				getTenureDetail.setString(2, bankId);
				getTenureDetail.setString(3, zoneId);
				getTenureDetail.setString(4, branchId);
				getTenureDetail.setString(5, cgpan);
				getTenureDetail.registerOutParameter(6, java.sql.Types.VARCHAR);
				getTenureDetail.registerOutParameter(7, java.sql.Types.VARCHAR);
				getTenureDetail.registerOutParameter(8, java.sql.Types.VARCHAR);
				getTenureDetail.registerOutParameter(9, java.sql.Types.VARCHAR);
				getTenureDetail.registerOutParameter(10, java.sql.Types.VARCHAR);
				getTenureDetail.registerOutParameter(11, java.sql.Types.DATE);
				getTenureDetail.registerOutParameter(12, java.sql.Types.VARCHAR);
				getTenureDetail.registerOutParameter(13, java.sql.Types.VARCHAR);
				getTenureDetail.executeQuery();
	
				//  Log.log(Log.DEBUG,"GMDAO","repayment detail","exception "+exception);
				int error = getTenureDetail.getInt(1);
				//System.out.println("inside getUnitForTenureRequest error "+error);
	
				exception = getTenureDetail.getString(13);
				//System.out.println("inside getUnitForTenureRequest exception "+exception);
		
				if(error==1)
				{
					cgpanFlag = false;
					message=exception;
				}	
				if (error == Constants.FUNCTION_FAILURE) {
				cgpanFlag = false;
				getTenureDetail.close();
				getTenureDetail = null;
				connection.rollback();			
				} 
		
				if(error!=1 || error != Constants.FUNCTION_FAILURE){
					cgpanFlag = true;
					
				}else{
					cgpanFlag = false;
      		    }
			} catch (SQLException e) 
			{
				LogClass.StepWritter("Exception in validateCgpanForTenureModificationNew method "+e.getMessage()+" SP exception is "+message);
				LogClass.writeExceptionOnFile(e);
					//System.out.println("exception in getUnitForTenureRequest "+e.getMessage());
				//	e.printStackTrace();
//System.out.println("12364 Something"+e.getMessage());
					message="Something ["+e.getMessage()+"] went wrong, Please contact Support[support@cgtmse.in] team";
				try {
				connection.rollback();
				} catch (SQLException ignore) {
			
				}	
			} finally 
			{
				DBConnection.freeConnection(connection);
			}
			return message;	
	}
       
    public BorrowerDetails getBorrowerDetailsForModifyBorrowerDetails(String memberId, String id, int type)
    throws DatabaseException
  {
    Log.log(4, "GMDAO", "getBorrowerDetails", "Entered");
    BorrowerDetails borrowerDetails = null;
    Log.log(5, "GMDAO", "getBorrowerDetails", "mem" + memberId);
    Log.log(5, "GMDAO", "getBorrowerDetails", "  id " + id);

    if (type == 0) {
      borrowerDetails = getBorrowerDetailsForBIDForModifyBorrowerDetails(memberId, id);
      //System.out.println("type == 0");
    }
    else if (type == 1)
    {
  	   //System.out.println("type == 1");
      borrowerDetails = getBorrowerDetailsForCgpanForModifyBorrowerDetails(memberId, id);
    }
    else if (type == 2)
    {
  	   //System.out.println("type == 2");
      borrowerDetails = getBorrowerDetailsForBorrowerForModifyBorrowerDetails(memberId, id);
    }

    Log.log(4, "GMDAO", "getBorrowerDetails", "Exited");

    return borrowerDetails;
  }
    
    
    public BorrowerDetails getBorrowerDetailsForBIDForModifyBorrowerDetails(String memberId, String borrowerId)
    throws DatabaseException
  {
    Log.log(4, "GMDAO", "getBorrowerDetailsForBID", "Entered");
    Connection connection = DBConnection.getConnection();
    //System.out.println("getBorrowerDetailsForBID executed");
    SSIDetails ssiDetails = new SSIDetails();
    APForm apform= new APForm();
    BorrowerDetails borrowerDetails = new BorrowerDetails();

    String bankId = memberId.substring(0, 4);
    String zoneId = memberId.substring(4, 8);
    String branchId = memberId.substring(8, 12);
    int getStatus = 0;
    try
    {
      CallableStatement getBorrowerDetailsStmt = connection.prepareCall("{?=call funcGetSSIDetailforBID(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)}");

      getBorrowerDetailsStmt.setString(2, bankId);
      getBorrowerDetailsStmt.setString(3, zoneId);
      getBorrowerDetailsStmt.setString(4, branchId);
      getBorrowerDetailsStmt.setString(5, borrowerId);
      getBorrowerDetailsStmt.registerOutParameter(1, 4);

      getBorrowerDetailsStmt.registerOutParameter(32, 12);

      getBorrowerDetailsStmt.registerOutParameter(6, 4);
      getBorrowerDetailsStmt.registerOutParameter(7, 12);
      getBorrowerDetailsStmt.registerOutParameter(8, 12);
      getBorrowerDetailsStmt.registerOutParameter(9, 12);

      getBorrowerDetailsStmt.registerOutParameter(10, 12);
      getBorrowerDetailsStmt.registerOutParameter(11, 12);
      getBorrowerDetailsStmt.registerOutParameter(12, 12);
      getBorrowerDetailsStmt.registerOutParameter(13, 12);
      getBorrowerDetailsStmt.registerOutParameter(14, 12);
      getBorrowerDetailsStmt.registerOutParameter(15, 91);
      getBorrowerDetailsStmt.registerOutParameter(16, 12);
      getBorrowerDetailsStmt.registerOutParameter(17, 12);
      getBorrowerDetailsStmt.registerOutParameter(18, 4);
      getBorrowerDetailsStmt.registerOutParameter(19, 8);
      getBorrowerDetailsStmt.registerOutParameter(20, 8);
      getBorrowerDetailsStmt.registerOutParameter(21, 12);
      getBorrowerDetailsStmt.registerOutParameter(22, 12);
      getBorrowerDetailsStmt.registerOutParameter(23, 12);
      getBorrowerDetailsStmt.registerOutParameter(24, 12);
      getBorrowerDetailsStmt.registerOutParameter(25, 12);
      getBorrowerDetailsStmt.registerOutParameter(26, 12);
      getBorrowerDetailsStmt.registerOutParameter(27, 12);
      getBorrowerDetailsStmt.registerOutParameter(28, 12);
      getBorrowerDetailsStmt.registerOutParameter(29, 12);
      getBorrowerDetailsStmt.registerOutParameter(30, 12);
      getBorrowerDetailsStmt.registerOutParameter(31, 8);

      getBorrowerDetailsStmt.execute();
      getStatus = getBorrowerDetailsStmt.getInt(1);
      String exception = getBorrowerDetailsStmt.getString(32);

      if (getStatus == 0) {
        Log.log(5, "GMDAO", "getBorroerDetails", "Success-SP");
      }
      if (getStatus == 1) {
        getBorrowerDetailsStmt.close();
        getBorrowerDetailsStmt = null;
        Log.log(2, "GMDAO", "GetBorrwoewerDtls", "Exception" + exception);
        throw new DatabaseException(exception);
      }
      Log.log(5, "GMDAO", "GetBorrwoewerDtls", "Borrower Id : " + borrowerId);
      ssiDetails.setBorrowerRefNo(getBorrowerDetailsStmt.getInt(6));
      Log.log(5, "GMDAO", "GetBorrwoewerDtls", "Borrower ref no :" + ssiDetails.getBorrowerRefNo());

      borrowerDetails.setPreviouslyCovered(getBorrowerDetailsStmt.getString(7).trim());
      Log.log(5, "GMDAO", "GetBorrwoewerDtls", "previously covered :" + borrowerDetails.getPreviouslyCovered());

      borrowerDetails.setAssistedByBank(getBorrowerDetailsStmt.getString(8).trim());
      Log.log(5, "GMDAO", "GetBorrwoewerDtls", "AssistedByBank:" + borrowerDetails.getAssistedByBank());

      borrowerDetails.setAcNo(getBorrowerDetailsStmt.getString(9));
      Log.log(5, "GMDAO", "GetBorrwoewerDtls", "setAcNo:" + borrowerDetails.getAcNo());

      borrowerDetails.setNpa(getBorrowerDetailsStmt.getString(10).trim());
      Log.log(5, "GMDAO", "GetBorrwoewerDtls", "setNpa:" + borrowerDetails.getNpa());

      ssiDetails.setConstitution(getBorrowerDetailsStmt.getString(11));
      Log.log(5, "GMDAO", "GetBorrwoewerDtls", "setConstitution:" + ssiDetails.getConstitution());

      ssiDetails.setSsiType(getBorrowerDetailsStmt.getString(12).trim());
      Log.log(5, "GMDAO", "GetBorrwoewerDtls", "setSsiType:" + ssiDetails.getSsiType());

      ssiDetails.setSsiName(getBorrowerDetailsStmt.getString(13));
      Log.log(5, "GMDAO", "GetBorrwoewerDtls", "setSsiName:" + ssiDetails.getSsiName());

      ssiDetails.setRegNo(getBorrowerDetailsStmt.getString(14));
      Log.log(5, "GMDAO", "GetBorrwoewerDtls", "setRegNo:" + ssiDetails.getRegNo());

      ssiDetails.setCommencementDate(DateHelper.sqlToUtilDate(getBorrowerDetailsStmt.getDate(15)));
      Log.log(5, "GMDAO", "GetBorrwoewerDtls", "setCommencementDate:" + ssiDetails.getCommencementDate());

      ssiDetails.setSsiITPan(getBorrowerDetailsStmt.getString(16));
      Log.log(5, "GMDAO", "GetBorrwoewerDtls", "setSsiITPan:" + ssiDetails.getSsiITPan());

      ssiDetails.setActivityType(getBorrowerDetailsStmt.getString(17));
      Log.log(5, "GMDAO", "GetBorrwoewerDtls", "setActivityType:" + ssiDetails.getActivityType());

      ssiDetails.setEmployeeNos(getBorrowerDetailsStmt.getInt(18));
      Log.log(5, "GMDAO", "GetBorrwoewerDtls", "setEmployeeNos:" + ssiDetails.getEmployeeNos());

      ssiDetails.setProjectedSalesTurnover(getBorrowerDetailsStmt.getDouble(19));
      Log.log(5, "GMDAO", "GetBorrwoewerDtls", "setProjectedSalesTurnover:" + ssiDetails.getProjectedSalesTurnover());

      ssiDetails.setProjectedExports(getBorrowerDetailsStmt.getDouble(20));
      Log.log(5, "GMDAO", "GetBorrwoewerDtls", "setProjectedSalesTurnover:" + ssiDetails.getProjectedExports());

      ssiDetails.setAddress(getBorrowerDetailsStmt.getString(21));
      Log.log(5, "GMDAO", "GetBorrwoewerDtls", "setAddress:" + ssiDetails.getAddress());

      ssiDetails.setCity(getBorrowerDetailsStmt.getString(22));
      Log.log(5, "GMDAO", "GetBorrwoewerDtls", "setCity:" + ssiDetails.getCity());

      ssiDetails.setPincode(getBorrowerDetailsStmt.getString(23).trim());
      Log.log(5, "GMDAO", "GetBorrwoewerDtls", "setPincode:" + ssiDetails.getPincode());

      ssiDetails.setDistrict(getBorrowerDetailsStmt.getString(25));
      Log.log(5, "GMDAO", "GetBorrwoewerDtls", "setDistrict:" + ssiDetails.getDistrict());

      ssiDetails.setState(getBorrowerDetailsStmt.getString(26));
      Log.log(5, "GMDAO", "GetBorrwoewerDtls", "setState:" + ssiDetails.getState());

      ssiDetails.setIndustryNature(getBorrowerDetailsStmt.getString(27));
      Log.log(5, "GMDAO", "GetBorrwoewerDtls", "setIndustryNature:" + ssiDetails.getIndustryNature());

      ssiDetails.setIndustrySector(getBorrowerDetailsStmt.getString(28));
      Log.log(5, "GMDAO", "GetBorrwoewerDtls", "setIndustrySector:" + ssiDetails.getIndustrySector());

      ssiDetails.setCgbid(getBorrowerDetailsStmt.getString(30));
      Log.log(5, "GMDAO", "GetBorrwoewerDtls", "setCgbid:" + ssiDetails.getCgbid());

      borrowerDetails.setOsAmt(getBorrowerDetailsStmt.getDouble(31));
      Log.log(5, "GMDAO", "GetBorrwoewerDtls", "setOsAmt:" + borrowerDetails.getOsAmt());
      borrowerDetails.setSsiDetails(ssiDetails);
      int ssiRefNo = borrowerDetails.getSsiDetails().getBorrowerRefNo();
      Log.log(5, "GMDAO", "GetBorrwoewerDtls", "Borrower ref no :" + ssiRefNo);

      Log.log(5, "GMDAO", "GetBorrwoewerDtls", "Borrower ref no outside:" + ssiRefNo);

      CallableStatement getPromoterDetailsStmt = connection.prepareCall("{?=call FUNCGETPROMOTERDTLFORSSIREFMLI(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)}");
      
      getPromoterDetailsStmt.registerOutParameter(1, 4);
      getPromoterDetailsStmt.registerOutParameter(26, 12);

      getPromoterDetailsStmt.setInt(2, ssiRefNo);
      getPromoterDetailsStmt.registerOutParameter(3, 4);
      getPromoterDetailsStmt.registerOutParameter(4, 12);
      getPromoterDetailsStmt.registerOutParameter(5, 12);
      getPromoterDetailsStmt.registerOutParameter(6, 12);
      getPromoterDetailsStmt.registerOutParameter(7, 12);
      getPromoterDetailsStmt.registerOutParameter(8, 12);
      getPromoterDetailsStmt.registerOutParameter(9, 12);
      getPromoterDetailsStmt.registerOutParameter(10, 91);
      getPromoterDetailsStmt.registerOutParameter(11, 12);
      getPromoterDetailsStmt.registerOutParameter(12, 12);
      getPromoterDetailsStmt.registerOutParameter(13, 12);
      getPromoterDetailsStmt.registerOutParameter(14, 91);
      getPromoterDetailsStmt.registerOutParameter(15, 12);
      getPromoterDetailsStmt.registerOutParameter(16, 12);
      getPromoterDetailsStmt.registerOutParameter(17, 91);
      getPromoterDetailsStmt.registerOutParameter(18, 12);
      getPromoterDetailsStmt.registerOutParameter(19, 12);
      getPromoterDetailsStmt.registerOutParameter(20, 91);
      getPromoterDetailsStmt.registerOutParameter(21, 12);
      getPromoterDetailsStmt.registerOutParameter(22, 12);
      getPromoterDetailsStmt.registerOutParameter(23, 12);
      getPromoterDetailsStmt.registerOutParameter(24, 12);
      getPromoterDetailsStmt.registerOutParameter(25, 12);
      getPromoterDetailsStmt.executeQuery();
      int getPromoterDetailsStatus = getPromoterDetailsStmt.getInt(1);
      String prException = getPromoterDetailsStmt.getString(26);
      //System.out.println("prException Name 1"+prException);
      if (getPromoterDetailsStatus == 1)
      {
        getPromoterDetailsStmt.close();
        getPromoterDetailsStmt = null;
        Log.log(2, "GMDAO", "GetpromotererDtls", "Exception :" + prException);
        throw new DatabaseException(prException);
      }

      ssiDetails.setCpTitle(getPromoterDetailsStmt.getString(4));
      Log.log(5, "GMDAO", "GetpromotererDtls", "CP Gender" + ssiDetails.getCpTitle());
      ssiDetails.setCpFirstName(getPromoterDetailsStmt.getString(5));
      ssiDetails.setCpMiddleName(getPromoterDetailsStmt.getString(6));
      ssiDetails.setCpLastName(getPromoterDetailsStmt.getString(7));
      ssiDetails.setCpITPAN(getPromoterDetailsStmt.getString(8));
      ssiDetails.setCpGender(getPromoterDetailsStmt.getString(9).trim());
      Log.log(5, "GMDAO", "GetpromotererDtls", "CP Gender" + ssiDetails.getCpGender());
      ssiDetails.setCpDOB(DateHelper.sqlToUtilDate(getPromoterDetailsStmt.getDate(10)));
      ssiDetails.setCpLegalID(getPromoterDetailsStmt.getString(11));
      Log.log(5, "GMDAO", "GetpromotererDtls", "CP Gender" + ssiDetails.getCpLegalID());
      ssiDetails.setCpLegalIdValue(getPromoterDetailsStmt.getString(12));
      ssiDetails.setFirstName(getPromoterDetailsStmt.getString(13));
      ssiDetails.setFirstDOB(DateHelper.sqlToUtilDate(getPromoterDetailsStmt.getDate(14)));
      ssiDetails.setFirstItpan(getPromoterDetailsStmt.getString(15));
      ssiDetails.setSecondName(getPromoterDetailsStmt.getString(16));
      ssiDetails.setSecondDOB(DateHelper.sqlToUtilDate(getPromoterDetailsStmt.getDate(17)));
      ssiDetails.setSecondItpan(getPromoterDetailsStmt.getString(18));
      ssiDetails.setThirdName(getPromoterDetailsStmt.getString(19));
      ssiDetails.setThirdDOB(DateHelper.sqlToUtilDate(getPromoterDetailsStmt.getDate(20)));
      ssiDetails.setThirdItpan(getPromoterDetailsStmt.getString(21));
      ssiDetails.setSocialCategory(getPromoterDetailsStmt.getString(22));
      
      apform.setUdyogAdharNo(getPromoterDetailsStmt.getString(23));
      apform.setBankAcNo(getPromoterDetailsStmt.getString(24));
      apform.setBranchName(getPromoterDetailsStmt.getString(25));
      //System.out.println("Branch Name "+getPromoterDetailsStmt.getString(25));
      borrowerDetails.setSsiDetails(ssiDetails);
      borrowerDetails.setApform(apform);
      

      getBorrowerDetailsStmt.close();
      getBorrowerDetailsStmt = null;
      getPromoterDetailsStmt.close();
      getPromoterDetailsStmt = null;
    }
    catch (SQLException exception)
    {
    	 //System.out.println("SQLException Name 1"+exception.getMessage());
      Log.logException(exception);
      throw new DatabaseException(exception.getMessage());
    }
    finally {
      DBConnection.freeConnection(connection);
    }
    int ssiRefNo;
    CallableStatement getBorrowerDetailsStmt;
    Log.log(4, "GMDAO", "getBorrowerDetailsForBID", "Exited");

    return borrowerDetails;
  }
    
    
    
    public BorrowerDetails getBorrowerDetailsForCgpanForModifyBorrowerDetails(String memberId, String cgpan)
    throws DatabaseException
  {
  	  //System.out.println("getBorrowerDetailsForCgpan executed");
    Log.log(4, "GMDAO", "getBorrowerDetailsForCGPAN", "Entered");
    Connection connection = DBConnection.getConnection();
    CallableStatement getBorrowerDetailsStmt = null;
    APForm apform=new APForm();
    SSIDetails ssiDetails = new SSIDetails();
    BorrowerDetails borrowerDetails = new BorrowerDetails();

    int ssiRefNo = 0;

    String bankId = memberId.substring(0, 4);
    String zoneId = memberId.substring(4, 8);
    String branchId = memberId.substring(8, 12);
    int getStatus = 0;
    Log.log(5, "GMDAO", "getBorrowerDetailsForCGPAN", "CGPAN--" + cgpan);
    Log.log(5, "GMDAO", "getBorrowerDetailsForCGPAN", "member --" + memberId);
    try
    {
      getBorrowerDetailsStmt = connection.prepareCall("{?=call funcGetSSIDetailforCGPAN(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)}");

      getBorrowerDetailsStmt.setString(2, bankId);
      getBorrowerDetailsStmt.setString(3, zoneId);
      getBorrowerDetailsStmt.setString(4, branchId);
      getBorrowerDetailsStmt.setString(5, cgpan);

      getBorrowerDetailsStmt.registerOutParameter(1, 4);

      getBorrowerDetailsStmt.registerOutParameter(32, 12);

      getBorrowerDetailsStmt.registerOutParameter(6, 4);
      getBorrowerDetailsStmt.registerOutParameter(7, 12);
      getBorrowerDetailsStmt.registerOutParameter(8, 12);
      getBorrowerDetailsStmt.registerOutParameter(9, 12);

      getBorrowerDetailsStmt.registerOutParameter(10, 12);
      getBorrowerDetailsStmt.registerOutParameter(11, 12);
      getBorrowerDetailsStmt.registerOutParameter(12, 12);
      getBorrowerDetailsStmt.registerOutParameter(13, 12);
      getBorrowerDetailsStmt.registerOutParameter(14, 12);
      getBorrowerDetailsStmt.registerOutParameter(15, 91);
      getBorrowerDetailsStmt.registerOutParameter(16, 12);
      getBorrowerDetailsStmt.registerOutParameter(17, 12);
      getBorrowerDetailsStmt.registerOutParameter(18, 4);
      getBorrowerDetailsStmt.registerOutParameter(19, 8);
      getBorrowerDetailsStmt.registerOutParameter(20, 8);
      getBorrowerDetailsStmt.registerOutParameter(21, 12);
      getBorrowerDetailsStmt.registerOutParameter(22, 12);
      getBorrowerDetailsStmt.registerOutParameter(23, 12);
      getBorrowerDetailsStmt.registerOutParameter(24, 12);
      getBorrowerDetailsStmt.registerOutParameter(25, 12);
      getBorrowerDetailsStmt.registerOutParameter(26, 12);
      getBorrowerDetailsStmt.registerOutParameter(27, 12);
      getBorrowerDetailsStmt.registerOutParameter(28, 12);
      getBorrowerDetailsStmt.registerOutParameter(29, 12);
      getBorrowerDetailsStmt.registerOutParameter(30, 12);
      getBorrowerDetailsStmt.registerOutParameter(31, 8);
      getBorrowerDetailsStmt.execute();
      getStatus = getBorrowerDetailsStmt.getInt(1);

      String exception = getBorrowerDetailsStmt.getString(32);
      if (getStatus == 0) {
        Log.log(5, "getborrowerDetails", "success dao for CGPAN SP", "--");
      }
      if (getStatus == 1) {
        Log.log(2, "GMDAO", "getborrowerDetails", "Exception " + exception);
        getBorrowerDetailsStmt.close();
        getBorrowerDetailsStmt = null;
      //  System.out.println("12717 Something"+exception);
        throw new DatabaseException("Something ["+exception+"] went wrong, Please contact Support[support@cgtmse.in] team");
      }
      ssiDetails.setBorrowerRefNo(getBorrowerDetailsStmt.getInt(6));
      borrowerDetails.setPreviouslyCovered(getBorrowerDetailsStmt.getString(7).trim());
      borrowerDetails.setAssistedByBank(getBorrowerDetailsStmt.getString(8).trim());
      borrowerDetails.setAcNo(getBorrowerDetailsStmt.getString(9));
      borrowerDetails.setNpa(getBorrowerDetailsStmt.getString(10).trim());
      ssiDetails.setConstitution(getBorrowerDetailsStmt.getString(11));
      ssiDetails.setSsiType(getBorrowerDetailsStmt.getString(12).trim());
      ssiDetails.setSsiName(getBorrowerDetailsStmt.getString(13));
      ssiDetails.setRegNo(getBorrowerDetailsStmt.getString(14));
      ssiDetails.setCommencementDate(DateHelper.sqlToUtilDate(getBorrowerDetailsStmt.getDate(15)));
      ssiDetails.setSsiITPan(getBorrowerDetailsStmt.getString(16));
      ssiDetails.setActivityType(getBorrowerDetailsStmt.getString(17));
      ssiDetails.setEmployeeNos(getBorrowerDetailsStmt.getInt(18));
      ssiDetails.setProjectedSalesTurnover(getBorrowerDetailsStmt.getDouble(19));
      ssiDetails.setProjectedExports(getBorrowerDetailsStmt.getDouble(20));
      ssiDetails.setAddress(getBorrowerDetailsStmt.getString(21));
      ssiDetails.setCity(getBorrowerDetailsStmt.getString(22));
      ssiDetails.setPincode(getBorrowerDetailsStmt.getString(23).trim());
      ssiDetails.setDistrict(getBorrowerDetailsStmt.getString(25));
      ssiDetails.setState(getBorrowerDetailsStmt.getString(26));
      //System.out.println("setIndustryNature "+getBorrowerDetailsStmt.getString(27));
      ssiDetails.setIndustryNature(getBorrowerDetailsStmt.getString(27));
      ssiDetails.setIndustrySector(getBorrowerDetailsStmt.getString(28));
      ssiDetails.setCgbid(getBorrowerDetailsStmt.getString(30));
      borrowerDetails.setOsAmt(getBorrowerDetailsStmt.getDouble(31));
      borrowerDetails.setSsiDetails(ssiDetails);
      ssiRefNo = borrowerDetails.getSsiDetails().getBorrowerRefNo();

   //   CallableStatement getPromoterDetailsStmt = connection.prepareCall("{?=call funcGetPromoterDtlforSSIRef(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)}");
      CallableStatement getPromoterDetailsStmt = connection.prepareCall("{?=call FUNCGETPROMOTERDTLFORSSIREFMLI(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)}");

      getPromoterDetailsStmt.registerOutParameter(1, 4);
      getPromoterDetailsStmt.registerOutParameter(26, 12);

      getPromoterDetailsStmt.setInt(2, ssiRefNo);
      getPromoterDetailsStmt.registerOutParameter(3, 4);
      getPromoterDetailsStmt.registerOutParameter(4, 12);
      getPromoterDetailsStmt.registerOutParameter(5, 12);
      getPromoterDetailsStmt.registerOutParameter(6, 12);
      getPromoterDetailsStmt.registerOutParameter(7, 12);
      getPromoterDetailsStmt.registerOutParameter(8, 12);
      getPromoterDetailsStmt.registerOutParameter(9, 12);
      getPromoterDetailsStmt.registerOutParameter(10, 91);
      getPromoterDetailsStmt.registerOutParameter(11, 12);
      getPromoterDetailsStmt.registerOutParameter(12, 12);
      getPromoterDetailsStmt.registerOutParameter(13, 12);
      getPromoterDetailsStmt.registerOutParameter(14, 91);
      getPromoterDetailsStmt.registerOutParameter(15, 12);
      getPromoterDetailsStmt.registerOutParameter(16, 12);
      getPromoterDetailsStmt.registerOutParameter(17, 91);
      getPromoterDetailsStmt.registerOutParameter(18, 12);
      getPromoterDetailsStmt.registerOutParameter(19, 12);
      getPromoterDetailsStmt.registerOutParameter(20, 91);
      getPromoterDetailsStmt.registerOutParameter(21, 12);
      getPromoterDetailsStmt.registerOutParameter(22, 12);
      getPromoterDetailsStmt.registerOutParameter(23, 12);
      getPromoterDetailsStmt.registerOutParameter(24, 12);
      getPromoterDetailsStmt.registerOutParameter(25, 12);
      getPromoterDetailsStmt.executeQuery();

      String prException = getPromoterDetailsStmt.getString(26);
      int getPromoterDetailsStatus = getPromoterDetailsStmt.getInt(1);
  //System.out.println("prException 3 "+prException);
      if (getPromoterDetailsStatus == 1)
      {
    	  
        Log.log(2, "GMDAO", "GetpromotererDtls", "Exception " + prException);
        getPromoterDetailsStmt.close();
        getPromoterDetailsStmt = null;
        
       // System.out.println("12790 Something"+prException);
        throw new DatabaseException("Something ["+prException+"] went wrong, Please contact Support[support@cgtmse.in] team");
      }

      ssiDetails.setCpTitle(getPromoterDetailsStmt.getString(4));
      ssiDetails.setCpFirstName(getPromoterDetailsStmt.getString(5));
      ssiDetails.setCpMiddleName(getPromoterDetailsStmt.getString(6));
      ssiDetails.setCpLastName(getPromoterDetailsStmt.getString(7));
      ssiDetails.setCpITPAN(getPromoterDetailsStmt.getString(8));
      ssiDetails.setCpGender(getPromoterDetailsStmt.getString(9).trim());
      ssiDetails.setCpDOB(DateHelper.sqlToUtilDate(getPromoterDetailsStmt.getDate(10)));
      ssiDetails.setCpLegalID(getPromoterDetailsStmt.getString(11));
      ssiDetails.setCpLegalIdValue(getPromoterDetailsStmt.getString(12));
      ssiDetails.setFirstName(getPromoterDetailsStmt.getString(13));
      ssiDetails.setFirstDOB(DateHelper.sqlToUtilDate(getPromoterDetailsStmt.getDate(14)));
      ssiDetails.setFirstItpan(getPromoterDetailsStmt.getString(15));
      ssiDetails.setSecondName(getPromoterDetailsStmt.getString(16));
      ssiDetails.setSecondDOB(DateHelper.sqlToUtilDate(getPromoterDetailsStmt.getDate(17)));
      ssiDetails.setSecondItpan(getPromoterDetailsStmt.getString(18));
      ssiDetails.setThirdName(getPromoterDetailsStmt.getString(19));
      ssiDetails.setThirdDOB(DateHelper.sqlToUtilDate(getPromoterDetailsStmt.getDate(20)));
      ssiDetails.setThirdItpan(getPromoterDetailsStmt.getString(21));
      ssiDetails.setSocialCategory(getPromoterDetailsStmt.getString(22));
      
      apform.setUdyogAdharNo(getPromoterDetailsStmt.getString(23));
      apform.setBankAcNo(getPromoterDetailsStmt.getString(24));
      apform.setBranchName(getPromoterDetailsStmt.getString(25));
     // apform.setRemarks(getPromoterDetailsStmt.getString(25));
      //System.out.println("Branch Name "+getPromoterDetailsStmt.getString(25));
      borrowerDetails.setSsiDetails(ssiDetails);
      borrowerDetails.setApform(apform);

      getBorrowerDetailsStmt.close();
      getBorrowerDetailsStmt = null;

      getPromoterDetailsStmt.close();
      getPromoterDetailsStmt = null;
    }
    catch (SQLException exception) {
    	//System.out.println("exception for FUNCGETPROMOTERDTLFORSSIREFMLI "+exception.getMessage());
    	LogClass.StepWritter("Exception in validateCgpanForTenureModificationNew method "+exception.getMessage());
		LogClass.writeExceptionOnFile(exception);
		//System.out.println("12833 Something "+exception.getMessage());
      throw new DatabaseException("Something ["+exception.getMessage()+"] went wrong, Please contact Support[support@cgtmse.in] team");
    } finally {
      DBConnection.freeConnection(connection);
    }

    Log.log(4, "GMDAO", "getBorrowerDetailsForCGPAN", "Exited");

    return borrowerDetails;
  }
    
    
    
    public BorrowerDetails getBorrowerDetailsForBorrowerForModifyBorrowerDetails(String memberId, String borrowerName)
    throws DatabaseException
  {
    Log.log(4, "GMDAO", "getBorrowerDetailsForBorrowerName", "Entered");
    //System.out.println("getBorrowerDetailsForBorrower executed");
    Connection connection = DBConnection.getConnection();
    CallableStatement getBorrowerDetailsStmt = null;
    APForm apform = new APForm();
    SSIDetails ssiDetails = new SSIDetails();
    BorrowerDetails borrowerDetails = new BorrowerDetails();

    int ssiRefNo = 0;

    String bankId = memberId.substring(0, 4);
    String zoneId = memberId.substring(4, 8);
    String branchId = memberId.substring(8, 12);
    int getStatus = 0;
    Log.log(5, "GMDAO", "getBorrowerDetailsForBorrowerName", "name" + borrowerName);
    Log.log(5, "GMDAO", "getBorrowerDetailsForBorrowerName", "member" + memberId);
    try
    {
      getBorrowerDetailsStmt = connection.prepareCall("{?=call funcGetSSIDetailforBorr(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)}");

      getBorrowerDetailsStmt.setString(2, bankId);
      getBorrowerDetailsStmt.setString(3, zoneId);
      getBorrowerDetailsStmt.setString(4, branchId);
      getBorrowerDetailsStmt.setString(5, borrowerName);

      getBorrowerDetailsStmt.registerOutParameter(1, 4);

      getBorrowerDetailsStmt.registerOutParameter(32, 12);

      getBorrowerDetailsStmt.registerOutParameter(6, 4);
      getBorrowerDetailsStmt.registerOutParameter(7, 12);
      getBorrowerDetailsStmt.registerOutParameter(8, 12);
      getBorrowerDetailsStmt.registerOutParameter(9, 12);

      getBorrowerDetailsStmt.registerOutParameter(10, 12);
      getBorrowerDetailsStmt.registerOutParameter(11, 12);
      getBorrowerDetailsStmt.registerOutParameter(12, 12);
      getBorrowerDetailsStmt.registerOutParameter(13, 12);
      getBorrowerDetailsStmt.registerOutParameter(14, 12);
      getBorrowerDetailsStmt.registerOutParameter(15, 91);
      getBorrowerDetailsStmt.registerOutParameter(16, 12);
      getBorrowerDetailsStmt.registerOutParameter(17, 12);
      getBorrowerDetailsStmt.registerOutParameter(18, 4);
      getBorrowerDetailsStmt.registerOutParameter(19, 8);
      getBorrowerDetailsStmt.registerOutParameter(20, 8);
      getBorrowerDetailsStmt.registerOutParameter(21, 12);
      getBorrowerDetailsStmt.registerOutParameter(22, 12);
      getBorrowerDetailsStmt.registerOutParameter(23, 12);
      getBorrowerDetailsStmt.registerOutParameter(24, 12);
      getBorrowerDetailsStmt.registerOutParameter(25, 12);
      getBorrowerDetailsStmt.registerOutParameter(26, 12);
      getBorrowerDetailsStmt.registerOutParameter(27, 12);
      getBorrowerDetailsStmt.registerOutParameter(28, 12);
      getBorrowerDetailsStmt.registerOutParameter(29, 12);
      getBorrowerDetailsStmt.registerOutParameter(30, 12);
      getBorrowerDetailsStmt.registerOutParameter(31, 8);
      getBorrowerDetailsStmt.execute();
      getStatus = getBorrowerDetailsStmt.getInt(1);

      String exception = getBorrowerDetailsStmt.getString(32);

      if (getStatus == 0) {
        Log.log(5, "borrowerDetails", "get ", "success dao for BorrowerName SP");
      }
      if (getStatus == 1) {
        Log.log(2, "GMDAO", "GetBorrwoewerDtls", "Exception " + exception);
        getBorrowerDetailsStmt.close();
        getBorrowerDetailsStmt = null;
        throw new DatabaseException(exception);
      }

      ssiDetails.setBorrowerRefNo(getBorrowerDetailsStmt.getInt(6));
      borrowerDetails.setPreviouslyCovered(getBorrowerDetailsStmt.getString(7).trim());
      borrowerDetails.setAssistedByBank(getBorrowerDetailsStmt.getString(8).trim());
      borrowerDetails.setAcNo(getBorrowerDetailsStmt.getString(9));
      borrowerDetails.setNpa(getBorrowerDetailsStmt.getString(10).trim());
      ssiDetails.setConstitution(getBorrowerDetailsStmt.getString(11));
      ssiDetails.setSsiType(getBorrowerDetailsStmt.getString(12).trim());
      ssiDetails.setSsiName(getBorrowerDetailsStmt.getString(13));
      ssiDetails.setRegNo(getBorrowerDetailsStmt.getString(14));
      ssiDetails.setCommencementDate(DateHelper.sqlToUtilDate(getBorrowerDetailsStmt.getDate(15)));
      ssiDetails.setSsiITPan(getBorrowerDetailsStmt.getString(16));
      ssiDetails.setActivityType(getBorrowerDetailsStmt.getString(17));
      ssiDetails.setEmployeeNos(getBorrowerDetailsStmt.getInt(18));
      ssiDetails.setProjectedSalesTurnover(getBorrowerDetailsStmt.getDouble(19));
      ssiDetails.setProjectedExports(getBorrowerDetailsStmt.getDouble(20));
      ssiDetails.setAddress(getBorrowerDetailsStmt.getString(21));
      ssiDetails.setCity(getBorrowerDetailsStmt.getString(22));
      ssiDetails.setPincode(getBorrowerDetailsStmt.getString(23).trim());
      ssiDetails.setDistrict(getBorrowerDetailsStmt.getString(25));
      ssiDetails.setState(getBorrowerDetailsStmt.getString(26));
      ssiDetails.setIndustryNature(getBorrowerDetailsStmt.getString(27));
      ssiDetails.setIndustrySector(getBorrowerDetailsStmt.getString(28));
      ssiDetails.setCgbid(getBorrowerDetailsStmt.getString(30));

      borrowerDetails.setOsAmt(getBorrowerDetailsStmt.getDouble(31));

      borrowerDetails.setSsiDetails(ssiDetails);
      ssiRefNo = borrowerDetails.getSsiDetails().getBorrowerRefNo();

      CallableStatement getPromoterDetailsStmt = connection.prepareCall("{?=call FUNCGETPROMOTERDTLFORSSIREFMLI(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)}");

      Log.log(5, "GMDAO", "getPRDetailsForBname", "ssiref" + ssiRefNo);
      getPromoterDetailsStmt.setInt(2, ssiRefNo);

      getPromoterDetailsStmt.registerOutParameter(1, 4);
      getPromoterDetailsStmt.registerOutParameter(26, 12);

      getPromoterDetailsStmt.registerOutParameter(3, 4);
      getPromoterDetailsStmt.registerOutParameter(4, 12);
      getPromoterDetailsStmt.registerOutParameter(5, 12);
      getPromoterDetailsStmt.registerOutParameter(6, 12);
      getPromoterDetailsStmt.registerOutParameter(7, 12);
      getPromoterDetailsStmt.registerOutParameter(8, 12);
      getPromoterDetailsStmt.registerOutParameter(9, 12);
      getPromoterDetailsStmt.registerOutParameter(10, 91);
      getPromoterDetailsStmt.registerOutParameter(11, 12);
      getPromoterDetailsStmt.registerOutParameter(12, 12);
      getPromoterDetailsStmt.registerOutParameter(13, 12);
      getPromoterDetailsStmt.registerOutParameter(14, 91);
      getPromoterDetailsStmt.registerOutParameter(15, 12);
      getPromoterDetailsStmt.registerOutParameter(16, 12);
      getPromoterDetailsStmt.registerOutParameter(17, 91);
      getPromoterDetailsStmt.registerOutParameter(18, 12);
      getPromoterDetailsStmt.registerOutParameter(19, 12);
      getPromoterDetailsStmt.registerOutParameter(20, 91);
      getPromoterDetailsStmt.registerOutParameter(21, 12);
      getPromoterDetailsStmt.registerOutParameter(22, 12);
      getPromoterDetailsStmt.registerOutParameter(23, 12);
      getPromoterDetailsStmt.registerOutParameter(24, 12);
      getPromoterDetailsStmt.registerOutParameter(25, 12);

      

      getPromoterDetailsStmt.executeQuery();
      String prException = getPromoterDetailsStmt.getString(26);
      int getPromoterDetailsStatus = getPromoterDetailsStmt.getInt(1);
      //System.out.println("prException Name 3"+prException);
      if (getPromoterDetailsStatus == 1)
      {
        Log.log(2, "GMDAO", "GetpromotererDtls", "Exception :" + prException);
        getPromoterDetailsStmt.close();
        getPromoterDetailsStmt = null;
        throw new DatabaseException(prException);
      }
      ssiDetails.setCpTitle(getPromoterDetailsStmt.getString(4));
      ssiDetails.setCpFirstName(getPromoterDetailsStmt.getString(5));
      ssiDetails.setCpMiddleName(getPromoterDetailsStmt.getString(6));
      ssiDetails.setCpLastName(getPromoterDetailsStmt.getString(7));
      ssiDetails.setCpITPAN(getPromoterDetailsStmt.getString(8));
      ssiDetails.setCpGender(getPromoterDetailsStmt.getString(9).trim());
      ssiDetails.setCpDOB(DateHelper.sqlToUtilDate(getPromoterDetailsStmt.getDate(10)));
      ssiDetails.setCpLegalID(getPromoterDetailsStmt.getString(11));
      ssiDetails.setCpLegalIdValue(getPromoterDetailsStmt.getString(12));
      ssiDetails.setFirstName(getPromoterDetailsStmt.getString(13));
      ssiDetails.setFirstDOB(DateHelper.sqlToUtilDate(getPromoterDetailsStmt.getDate(14)));
      ssiDetails.setFirstItpan(getPromoterDetailsStmt.getString(15));
      ssiDetails.setSecondName(getPromoterDetailsStmt.getString(16));
      ssiDetails.setSecondDOB(DateHelper.sqlToUtilDate(getPromoterDetailsStmt.getDate(17)));
      ssiDetails.setSecondItpan(getPromoterDetailsStmt.getString(18));
      ssiDetails.setThirdName(getPromoterDetailsStmt.getString(19));
      ssiDetails.setThirdDOB(DateHelper.sqlToUtilDate(getPromoterDetailsStmt.getDate(20)));
      ssiDetails.setThirdItpan(getPromoterDetailsStmt.getString(21));
      ssiDetails.setSocialCategory(getPromoterDetailsStmt.getString(22));
      apform.setUdyogAdharNo(getPromoterDetailsStmt.getString(23));
      apform.setBankAcNo(getPromoterDetailsStmt.getString(24));
      apform.setBranchName(getPromoterDetailsStmt.getString(25));
//System.out.println("Branch Name"+getPromoterDetailsStmt.getString(25));
      
      borrowerDetails.setSsiDetails(ssiDetails);
      borrowerDetails.setApform(apform);
      
      getBorrowerDetailsStmt.close();
      getBorrowerDetailsStmt = null;

      getPromoterDetailsStmt.close();
      getPromoterDetailsStmt = null;
    }
    catch (SQLException exception) {
      Log.logException(exception);
      throw new DatabaseException(exception.getMessage());
    } finally {
      DBConnection.freeConnection(connection);
    }
    Log.log(4, "GMDAO", "getBorrowerDetailsForBorrowerName", "Exited");

    return borrowerDetails;
  }
 // Aadhar updated by DKR
	public void updateBorrowerDetailsNew(BorrowerDetails borrowerDetail,
			String userId) throws DatabaseException {

		Connection connection = DBConnection.getConnection(false);
		CallableStatement updateBorrowerDetailsStmt = null;

		java.sql.Date sqlDate = null;
		java.util.Date utilDate = new java.util.Date();
		String exception = "";
		if (borrowerDetail != null) {
			try {
				// updateBorrowerDetailsStmt =
				// connection.prepareCall("{?=call Funcupdateborrowerdetailsmod(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)}");
				updateBorrowerDetailsStmt = connection
						.prepareCall("{?=call Funcupdateborrowerdetailsmod(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)}");
				updateBorrowerDetailsStmt.registerOutParameter(1, 4);
				updateBorrowerDetailsStmt.registerOutParameter(35, 12);
				updateBorrowerDetailsStmt.setInt(2, borrowerDetail
						.getSsiDetails().getBorrowerRefNo());
				if (borrowerDetail.getSsiDetails().getConstitution()
						.equals("Others")) {
					updateBorrowerDetailsStmt.setString(3, borrowerDetail
							.getSsiDetails().getConstitutionOther());
				} else {
					updateBorrowerDetailsStmt.setString(3, borrowerDetail
							.getSsiDetails().getConstitution());
				}
				updateBorrowerDetailsStmt.setString(4, borrowerDetail
						.getSsiDetails().getSsiType());
				updateBorrowerDetailsStmt.setString(5, borrowerDetail
						.getSsiDetails().getSsiName().toUpperCase());
				updateBorrowerDetailsStmt.setString(6, borrowerDetail
						.getSsiDetails().getSsiITPan());
				// updateBorrowerDetailsStmt.setString(7,
				// borrowerDetail.getSsiDetails().getActivityType());
				updateBorrowerDetailsStmt.setString(7, borrowerDetail
						.getSsiDetails().getAddress().toUpperCase());
				updateBorrowerDetailsStmt.setString(8, borrowerDetail
						.getSsiDetails().getCity().toUpperCase());
				updateBorrowerDetailsStmt.setString(9, borrowerDetail
						.getSsiDetails().getPincode());
				updateBorrowerDetailsStmt.setString(10, borrowerDetail
						.getSsiDetails().getDistrict());
				updateBorrowerDetailsStmt.setString(11, borrowerDetail
						.getSsiDetails().getState());
				// updateBorrowerDetailsStmt.setString(13,
				// borrowerDetail.getSsiDetails().getIndustryNature());
				// updateBorrowerDetailsStmt.setString(14,
				// borrowerDetail.getSsiDetails().getIndustrySector());
				updateBorrowerDetailsStmt.setString(12, userId);
				updateBorrowerDetailsStmt.setString(13, borrowerDetail
						.getSsiDetails().getRemarks());

				updateBorrowerDetailsStmt.setString(14, borrowerDetail
						.getSsiDetails().getCpTitle());
				updateBorrowerDetailsStmt.setString(15, borrowerDetail
						.getSsiDetails().getCpFirstName());
				updateBorrowerDetailsStmt.setString(16, borrowerDetail
						.getSsiDetails().getCpMiddleName());
				updateBorrowerDetailsStmt.setString(17, borrowerDetail
						.getSsiDetails().getCpLastName());
				updateBorrowerDetailsStmt.setString(18, borrowerDetail
						.getSsiDetails().getCpITPAN());
				updateBorrowerDetailsStmt.setString(19, borrowerDetail
						.getSsiDetails().getCpGender());
				utilDate = borrowerDetail.getSsiDetails().getCpDOB();
				if ((utilDate != null) && (!utilDate.toString().equals(""))) {
					sqlDate = new java.sql.Date(utilDate.getTime());
					updateBorrowerDetailsStmt.setDate(20, sqlDate);
				} else {
					updateBorrowerDetailsStmt.setDate(20, null);
				}
				updateBorrowerDetailsStmt.setString(21, borrowerDetail
						.getSsiDetails().getSocialCategory());
				updateBorrowerDetailsStmt.setString(22, borrowerDetail
						.getApform().getUdyogAdharNo());
				updateBorrowerDetailsStmt.setString(23, borrowerDetail
						.getApform().getBankAcNo());
				updateBorrowerDetailsStmt.setString(24, borrowerDetail
						.getSsiDetails().getFirstName());

				if ((borrowerDetail.getSsiDetails().getFirstDOB() != null)
						&& (!borrowerDetail.getSsiDetails().getFirstDOB()
								.toString().equals(""))) {
					sqlDate = new java.sql.Date(borrowerDetail.getSsiDetails()
							.getFirstDOB().getTime());
					updateBorrowerDetailsStmt.setDate(25, sqlDate);
				} else {
					updateBorrowerDetailsStmt.setDate(25, null);
				}

				updateBorrowerDetailsStmt.setString(26, borrowerDetail
						.getSsiDetails().getFirstItpan());
				updateBorrowerDetailsStmt.setString(27, borrowerDetail
						.getSsiDetails().getSecondName());
				if ((borrowerDetail.getSsiDetails().getSecondDOB() != null)
						&& (!borrowerDetail.getSsiDetails().getSecondDOB()
								.toString().equals(""))) {
					sqlDate = new java.sql.Date(borrowerDetail.getSsiDetails()
							.getSecondDOB().getTime());
					updateBorrowerDetailsStmt.setDate(28, sqlDate);
				} else {
					updateBorrowerDetailsStmt.setDate(28, null);
				}
				updateBorrowerDetailsStmt.setString(29, borrowerDetail
						.getSsiDetails().getSecondItpan());
				updateBorrowerDetailsStmt.setString(30, borrowerDetail
						.getSsiDetails().getThirdName());
				if ((borrowerDetail.getSsiDetails().getThirdDOB() != null)
						&& (!borrowerDetail.getSsiDetails().getThirdDOB()
								.toString().equals(""))) {
					sqlDate = new java.sql.Date(borrowerDetail.getSsiDetails()
							.getThirdDOB().getTime());
					updateBorrowerDetailsStmt.setDate(31, sqlDate);
				} else {
					updateBorrowerDetailsStmt.setDate(31, null);
				}
				updateBorrowerDetailsStmt.setString(32, borrowerDetail
						.getSsiDetails().getThirdItpan());
				updateBorrowerDetailsStmt.setString(33, borrowerDetail
						.getApform().getBranchName());
				updateBorrowerDetailsStmt.setString(34, borrowerDetail
						.getApform().getAadharNos());
				System.out.println("borrowerDetail.getApform().getAadharNos()......DAO......."+ borrowerDetail.getApform().getAadharNos());
				updateBorrowerDetailsStmt.executeQuery(); // <<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<< updated brow
				int updateBorrowerDetailsStmtValue = updateBorrowerDetailsStmt.getInt(1);
				// exception = updateBorrowerDetailsStmt.getString(34);
				exception = updateBorrowerDetailsStmt.getString(35);
				if ((borrowerDetail.getSsiDetails().getThirdDOB() != null)
						&& (!borrowerDetail.getSsiDetails().getThirdDOB()
								.toString().equals(""))) {
					sqlDate = new java.sql.Date(borrowerDetail.getSsiDetails()
							.getThirdDOB().getTime());
					updateBorrowerDetailsStmt.setDate(31, sqlDate);
				}
				if (updateBorrowerDetailsStmtValue == 1) {
					// System.out.println("updateBorrowerDetailsNew exception "+exception);
					connection.rollback();
					updateBorrowerDetailsStmt.close();
					updateBorrowerDetailsStmt = null;
					throw new DatabaseException(
							"Something went wrong, kindly contact support team[support@cgtmse.in] "
									+ exception);

				}
				updateBorrowerDetailsStmt.close();
				updateBorrowerDetailsStmt = null;
				connection.commit();
			} catch (Exception e) {
				LogClass.StepWritter("Exception in updateBorrowerDetailsNew method "
						+ e.getMessage() + " and  SP exception is " + exception);
				LogClass.writeExceptionOnFile(e);

				throw new DatabaseException(
						"Something went wrong, kindly contact support team[support@cgtmse.in] "
								+ e.getMessage());
			} finally {
				DBConnection.freeConnection(connection);
			}
		} else {
			throw new DatabaseException("No data for borrower detail updation");
		}
	}
    /*public void updateBorrowerDetailsNew(BorrowerDetails borrowerDetail, String userId)
    throws DatabaseException
  {
  	 
  	  Connection connection = DBConnection.getConnection(false);
  	  CallableStatement updateBorrowerDetailsStmt = null;
        
        java.sql.Date sqlDate = null;
  	  java.util.Date utilDate = new java.util.Date();
  	 String exception ="";
  	  if (borrowerDetail != null)
  	  {
  	     try
  	     {
  	    	 updateBorrowerDetailsStmt = connection.prepareCall("{?=call Funcupdateborrowerdetailsmod(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)}");
  	    	 updateBorrowerDetailsStmt.registerOutParameter(1, 4);
  	         updateBorrowerDetailsStmt.registerOutParameter(35, 12);
  	         updateBorrowerDetailsStmt.setInt(2, borrowerDetail.getSsiDetails().getBorrowerRefNo());
  	         if(borrowerDetail.getSsiDetails().getConstitution().equals("Others"))
  	         {
  	        	 updateBorrowerDetailsStmt.setString(3, borrowerDetail.getSsiDetails().getConstitutionOther());
  	         }   else
  	         {
  	         	updateBorrowerDetailsStmt.setString(3, borrowerDetail.getSsiDetails().getConstitution());
  	         }
  	         updateBorrowerDetailsStmt.setString(4, borrowerDetail.getSsiDetails().getSsiType());
  	         updateBorrowerDetailsStmt.setString(5, borrowerDetail.getSsiDetails().getSsiName().toUpperCase());
  	         updateBorrowerDetailsStmt.setString(6, borrowerDetail.getSsiDetails().getSsiITPan());
  	         //updateBorrowerDetailsStmt.setString(7, borrowerDetail.getSsiDetails().getActivityType());
  	         updateBorrowerDetailsStmt.setString(7, borrowerDetail.getSsiDetails().getAddress().toUpperCase());
  	         updateBorrowerDetailsStmt.setString(8, borrowerDetail.getSsiDetails().getCity().toUpperCase());
  	         updateBorrowerDetailsStmt.setString(9, borrowerDetail.getSsiDetails().getPincode());
  	         updateBorrowerDetailsStmt.setString(10, borrowerDetail.getSsiDetails().getDistrict());
  	         updateBorrowerDetailsStmt.setString(11, borrowerDetail.getSsiDetails().getState());
  	         //updateBorrowerDetailsStmt.setString(13, borrowerDetail.getSsiDetails().getIndustryNature());
  	        // updateBorrowerDetailsStmt.setString(14, borrowerDetail.getSsiDetails().getIndustrySector());
  	         updateBorrowerDetailsStmt.setString(12, userId);
  	         updateBorrowerDetailsStmt.setString(13,borrowerDetail.getSsiDetails().getRemarks());
  	         
  	         updateBorrowerDetailsStmt.setString(14, borrowerDetail.getSsiDetails().getCpTitle());
  	         updateBorrowerDetailsStmt.setString(15, borrowerDetail.getSsiDetails().getCpFirstName());         
  	         updateBorrowerDetailsStmt.setString(16, borrowerDetail.getSsiDetails().getCpMiddleName());      
  	         updateBorrowerDetailsStmt.setString(17, borrowerDetail.getSsiDetails().getCpLastName());
  	         updateBorrowerDetailsStmt.setString(18, borrowerDetail.getSsiDetails().getCpITPAN());         
  	         updateBorrowerDetailsStmt.setString(19, borrowerDetail.getSsiDetails().getCpGender());
  	         utilDate = borrowerDetail.getSsiDetails().getCpDOB();
  	         if ((utilDate != null) && (!utilDate.toString().equals("")))
  	         {
  	           sqlDate = new java.sql.Date(utilDate.getTime());
  	           updateBorrowerDetailsStmt.setDate(20, sqlDate);
  	         } else {
  	           updateBorrowerDetailsStmt.setDate(20, null);
  	         }
  	         updateBorrowerDetailsStmt.setString(21, borrowerDetail.getSsiDetails().getSocialCategory());
  	         updateBorrowerDetailsStmt.setString(22, borrowerDetail.getApform().getUdyogAdharNo());
  	         updateBorrowerDetailsStmt.setString(23, borrowerDetail.getApform().getBankAcNo());
  	         updateBorrowerDetailsStmt.setString(24, borrowerDetail.getSsiDetails().getFirstName());
  	         
  	         if ((borrowerDetail.getSsiDetails().getFirstDOB() != null) && (!borrowerDetail.getSsiDetails().getFirstDOB().toString().equals("")))
  	         {
  	        	 sqlDate = new java.sql.Date(borrowerDetail.getSsiDetails().getFirstDOB().getTime());
  		         updateBorrowerDetailsStmt.setDate(25, sqlDate);
  	         }   else
  	         {
  	        	 updateBorrowerDetailsStmt.setDate(25, null);
  	         }	         
  	         
  	         updateBorrowerDetailsStmt.setString(26, borrowerDetail.getSsiDetails().getFirstItpan());
  	         updateBorrowerDetailsStmt.setString(27, borrowerDetail.getSsiDetails().getSecondName());	         
  	         if ((borrowerDetail.getSsiDetails().getSecondDOB() != null) && (!borrowerDetail.getSsiDetails().getSecondDOB().toString().equals("")))
  	         {
  	        	 sqlDate = new java.sql.Date(borrowerDetail.getSsiDetails().getSecondDOB().getTime());
  		         updateBorrowerDetailsStmt.setDate(28, sqlDate);
  	         }   else
  	         {
  	        	 updateBorrowerDetailsStmt.setDate(28, null);
  	         }	         
  	         updateBorrowerDetailsStmt.setString(29, borrowerDetail.getSsiDetails().getSecondItpan());
  	         updateBorrowerDetailsStmt.setString(30, borrowerDetail.getSsiDetails().getThirdName());	         
  	         if ((borrowerDetail.getSsiDetails().getThirdDOB() != null) && (!borrowerDetail.getSsiDetails().getThirdDOB().toString().equals("")))
  	         {
  	        	 sqlDate = new java.sql.Date(borrowerDetail.getSsiDetails().getThirdDOB().getTime());
  		         updateBorrowerDetailsStmt.setDate(31, sqlDate);
  	         }   else
  	         {
  	        	 updateBorrowerDetailsStmt.setDate(31, null);
  	         }	         
  	         updateBorrowerDetailsStmt.setString(32, borrowerDetail.getSsiDetails().getThirdItpan());
  	         updateBorrowerDetailsStmt.setString(33, borrowerDetail.getApform().getBranchName());
  	         
  	       updateBorrowerDetailsStmt.setString(34, borrowerDetail
					.getApform().getAadharNos());
			System.out.println("borrowerDetail.getApform().getAadharNos()......DAO......."+ borrowerDetail.getApform().getAadharNos());
			updateBorrowerDetailsStmt.executeQuery(); // <<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<< updated brow
			int updateBorrowerDetailsStmtValue = updateBorrowerDetailsStmt.getInt(1);
			// exception = updateBorrowerDetailsStmt.getString(34);
			exception = updateBorrowerDetailsStmt.getString(35);
			
  	        
  	         if (updateBorrowerDetailsStmtValue == 1)
  	         {
  	         	//System.out.println("updateBorrowerDetailsNew exception "+exception);
  	            connection.rollback();
  	            updateBorrowerDetailsStmt.close();
  	            updateBorrowerDetailsStmt = null;	     
  	          throw new DatabaseException("Something went wrong, kindly contact support team[support@cgtmse.in] "+exception);
  	          
  	         }
  	         updateBorrowerDetailsStmt.close();
  	         updateBorrowerDetailsStmt = null;
  	         connection.commit();
  	     }
  	     catch(Exception e)
  	     {
  	    	LogClass.StepWritter("Exception in updateBorrowerDetailsNew method "+e.getMessage()+" and  SP exception is "+exception);
			LogClass.writeExceptionOnFile(e);
  	    	 
  	    	throw new DatabaseException("Something went wrong, kindly contact support team[support@cgtmse.in] "+e.getMessage());
  	     }
  	     finally 
  	     {
  	         DBConnection.freeConnection(connection);
  	     }
  	  }
  	  else
  	  {
  		 throw new DatabaseException("No data for borrower detail updation");
  	  }
  }
    */
    
   /* public Date getTenureApprovedDate(String borrowerId) throws SQLException
    
    {
  	 Date tenureApprovDate=null;
  	 try
  	 {
  	  //System.out.println("getTenureApprovedDate  S");
  	  ResultSet tenureApprovedDateRs=null;
  	  
  	  Connection connection=null;
  	  
  	  
  	  
  	  connection = DBConnection.getConnection(false);
  	  Statement statement = connection.createStatement();
  			 
  	  
    
  	  String query="select max(trunc(REQUEST_CREATED_DT))  from TENURE_UPDATION_DETAIL a,application_detail b,ssi_detail s "
  		  +"  where a.cgpan=b.cgpan and s.ssi_reference_number=b.ssi_reference_number and bid='"+borrowerId+"' ";
  	//System.out.println("Error "+query);
  	
  	  tenureApprovedDateRs=statement.executeQuery(query);
  	  if(tenureApprovedDateRs.next())
  	  {
  	
  		  tenureApprovDate=tenureApprovedDateRs.getDate(1);
  		  //System.out.println("GMDAO  tenureApprovDate"+tenureApprovedDateRs.getDate(1));
  		  DateFormat df = new SimpleDateFormat("dd/MM/yyyy"); 
  		  //System.out.println("getTenureApprovedDate   "+df.format(tenureApprovedDateRs.getDate(1)));
  		  
  		  tenureApprovDate=df.parse(df.format(tenureApprovedDateRs.getDate(1)));
  		  //System.out.println(tenureApprovDate);
  	  
    }
  	 }
  	 catch(Exception e)
  	 {
  		// e.printStackTrace();
  		tenureApprovDate=null;
  	 }
  	  return tenureApprovDate;
  	  
    }*/
    
 public HashMap getTenureApprovedDate(String borrowerId) throws SQLException
    
    {
	 
	// System.out.println("getTenureApprovedDate S");
	 
	 PreparedStatement tenureDetailStmt = null;
		HashMap tenureDetailArray = new HashMap();
		ResultSet tenureDetailResult = null;
		Connection connection = DBConnection.getConnection();
  	  try
  	    {
  		  
  //	System.out.println("borrowerId "+borrowerId);
  	 // String query="select max(trunc(REQUEST_CREATED_DT))  from TENURE_UPDATION_DETAIL a,application_detail b,ssi_detail s "
  		//  +"  where a.cgpan=b.cgpan and s.ssi_reference_number=b.ssi_reference_number and bid='"+borrowerId+"'";
  	 
  	  String query="SELECT trunc (A.TEN_REQ_APPROVED_DT) AS TEN_REQ_APPROVED_DT,"
                 +"  A.MODIFICATION_REMARKS,"
                 +"A.OLD_APP_STATUS"
                +" FROM TENURE_UPDATION_DETAIL a,"
                 +" application_detail b,"
                 +" ssi_detail s"
               +" WHERE a.cgpan = b.cgpan"
                +"  AND s.ssi_reference_number = b.ssi_reference_number"
                 +" AND bid = ?"
             +" AND TEN_REQ_APPROVED_DT ="
               +"(SELECT MAX (A.TEN_REQ_APPROVED_DT)"
            +" FROM TENURE_UPDATION_DETAIL a,"
                 +" application_detail b,"
                 +" ssi_detail s"
                +" WHERE     a.cgpan = b.cgpan"
                 +" AND s.ssi_reference_number = b.ssi_reference_number"
                 +" AND bid =? )";
  	 
     //  System.out.println("Query "+query);
  	
       tenureDetailStmt = connection.prepareStatement(query);
       tenureDetailStmt.setString(1, borrowerId);
       tenureDetailStmt.setString(2, borrowerId);
       tenureDetailResult = tenureDetailStmt.executeQuery();
       
       
       
       while (tenureDetailResult.next()) {
			// Instantiate a Gfee value object
    	//   System.out.println("tenureDetailArray"+tenureDetailResult.getString(1));
         //  System.out.println("tenureDetailArray"+tenureDetailResult.getString(2));
         //  System.out.println("tenureDetailArray"+tenureDetailResult.getString(3));
      

    	   tenureDetailArray.put("TENURE_REQUEST_DATE",tenureDetailResult.getDate(1));
    	   tenureDetailArray.put("TENURE_MODIFICATION_REMARKS",tenureDetailResult.getString(2));
    	   tenureDetailArray.put("OLD_APP_STATUS",tenureDetailResult.getString(3));
    	   
    	//  tenureDetailArray.get("TENURE_MODIFICATION_REMARKS");
    	   
		}
     
      
  
  	 }
  	 catch(Exception e)
  	 {
  		// e.printStackTrace();
  		//tenureApprovDate=null;
  	 }
  	 finally {
			DBConnection.freeConnection(connection);

		}
  	  return tenureDetailArray;
  	  
    }
    

    public String chkInputCGPAN_BID_Status(String memberID, String cgpan , String appRefNo)      throws DatabaseException
    {
    	String validationMessage="";
    	
    	Connection connection = DBConnection.getConnection(false);
     	CallableStatement stmtChkInputStatus = null;
	 	try
	 	{
	 		String exception = "";
			String functionName = null;
			functionName =  "{?=call Func_Borrw_Modifi_validate_chk(?,?,?,?,?)}";
		
			stmtChkInputStatus = connection.prepareCall(functionName);
			stmtChkInputStatus.registerOutParameter(1, java.sql.Types.INTEGER);
			stmtChkInputStatus.setString(2, memberID);
			stmtChkInputStatus.setString(3, cgpan);
			stmtChkInputStatus.setString(4, "");
			
			stmtChkInputStatus.registerOutParameter(5, java.sql.Types.VARCHAR);		
			stmtChkInputStatus.registerOutParameter(6, java.sql.Types.VARCHAR);		
			stmtChkInputStatus.executeQuery();
			
			int error = stmtChkInputStatus.getInt(1);
			//System.out.println("inside chkInputCGPAN_BID_Status error "+error);
			validationMessage= stmtChkInputStatus.getString(5);
			//System.out.println("inside chkInputCGPAN_BID_Status status "+validationMessage);
			
		
				if(validationMessage.startsWith("This"))
				{
					validationMessage="cgPanStatusCheck";
				} else if(validationMessage.startsWith("Request"))
				{
					validationMessage="requestRepittedCheck";
				} else if(validationMessage.startsWith("Npa"))
				{
					validationMessage="npaStatusCheck";
				} else if(validationMessage.startsWith("CGPAN"))
				{
					validationMessage="cgpanMemberIDIncorrect";
				}
		
			exception = stmtChkInputStatus.getString(6);
			
			//System.out.println("inside chkInputCGPAN_BID_Status exception "+exception);
	 	}
	 	catch(Exception e)
	 	{
	 		//System.out.println("inside chkInputCGPAN_BID_Status catch block");
	 		//System.out.println("13327 Something "+e.getMessage());
				throw new DatabaseException("Something ["+e.getMessage()+"] went wrong, Please contact Support[support@cgtmse.in] team");
			
	 	}
	 	finally
	 	{
	 		DBConnection.freeConnection(connection);
	 	}
	 	return validationMessage; 
    }
    
    /*Added by DKR for GSTN0*/
    
	public boolean updateGstNoInApplicationDetails(String gstNo, String gstState,	ArrayList emptyGstCgpAppRefList) {
		 Log.log(Log.INFO, "GMDAO", "updateGstNoInApplicationDetails", "Entered");
		 boolean updAppDtlGSTFlag = false;
		 Connection connection= null;
		 Statement stmt1= null;	
		 Statement stmt= null;
		 Connection connection1=null;			   					
			try {
				if(connection==null){
					  connection = DBConnection.getConnection();
			 }
				for(int i=0;i<emptyGstCgpAppRefList.size();i++){
			
				String query = "update application_detail set gst_no='"+gstNo+"' ,state_code='"+gstState+"' where APP_REF_NO='"+emptyGstCgpAppRefList.get(i)+"'";	
				stmt=connection.createStatement();
				int check = stmt.executeUpdate(query);
				if(check>0){
					System.out.println("value has updated");
					updAppDtlGSTFlag = true;
				}
				}
				stmt.close();				
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
							
			try {
				 if(connection1==null){
					 connection1 = DBConnection.getConnection();
					 }	
				for(int i=0;i<emptyGstCgpAppRefList.size();i++){
			
				String query1 = "update CGTSIINTRANETUSER.application_detail@DB_INTRA set gst_no='"+gstNo+"' ,state_code='"+gstState+"' where APP_REF_NO='"+emptyGstCgpAppRefList.get(i)+"'";	
				stmt1=connection1.createStatement();
				int check1 = stmt1.executeUpdate(query1);
				if(check1>0){
					System.out.println("value has updated");
					updAppDtlGSTFlag = true;
				}
				}
				stmt1.close();				
			} catch (Exception exception) {
				Log.logException(exception);
				try {
					throw new DatabaseException(exception.getMessage());
				} catch (DatabaseException e) {
					e.printStackTrace();
				}
			} finally {
				DBConnection.freeConnection(connection1);
			}
		   return updAppDtlGSTFlag;      
		
	}
	public ArrayList<MLIInfo> getGSTStateList(String bankId) {
	    Connection connection=DBConnection.getConnection();

	    ArrayList<MLIInfo> statesList=new ArrayList<MLIInfo>();
	    
	    Log.log(Log.INFO, "RegistationDAO", "getStateList", "Entered");
		/*MLIInfo mliinfo1=new MLIInfo();
		mliinfo1.setGstNo("");
		mliinfo1.setState("Please Select State of Bank Id");
	    statesList.add(0, mliinfo1);*/
		PreparedStatement pStmt = null;
		ResultSet rsSet = null;
		try {
			String query = "select  gst.STE_CODE as state_code, case when HEAD_OFFICE ='Y' then sm.STE_NAME||' (HEAD OFFICE)' else sm.STE_NAME end as state_name,gst.GST_NO as gstNo  from MEMBER_BANK_STATE_GST gst, state_master sm where gst.ste_code = sm.ste_code AND GST.MEM_BNK_ID=?";
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
	
	// Change by DKR 
	public ArrayList<MLIInfo> getGSTStateAndNo(String cgPan) {		 
		   
		    Connection connection=null;
		    if(connection == null){
		    	connection = DBConnection.getConnection();
		    }
		    ArrayList<MLIInfo> statesList=new ArrayList<MLIInfo>();
		    
		    Log.log(Log.INFO, "CPDAO", "getGSTStateAndNo", "Entered");
			
			PreparedStatement pStmt = null;
			ResultSet rsSet = null;
			try {
				String query = "select state_code,gst_no from application_detail where CGPAN=?";				
				pStmt = connection.prepareStatement(query);
				pStmt.setString(1, cgPan);					
				rsSet = pStmt.executeQuery();
				while (rsSet.next()) {		
					MLIInfo mliinfo=new MLIInfo();						
			        	mliinfo.setStateCode(rsSet.getString(1));
		        	if(rsSet.getString(1)!=null){
		        		mliinfo.setStateName(getStateName(rsSet.getString(1)));
		        	}
		        	   	mliinfo.setGstNo(rsSet.getString(2));			        	
			        	if(rsSet.getString(1)!=null && rsSet.getString(2)!=null){
				        	statesList.add(mliinfo);
			        	}else{
			        		statesList=new ArrayList<MLIInfo>();
			        	}
				}
				rsSet.close();
				pStmt.close();
			} catch (Exception exception) {
				Log.logException(exception);
				try {
					throw new DatabaseException("Exception in getGSTStateAndNo()"+exception.getMessage());
				} catch (DatabaseException e) {
					e.printStackTrace();
				}
			} finally {
				DBConnection.freeConnection(connection);
			}
			return statesList;
		         

}
private String getStateName(String stateCode) {
	   Connection connection=null;
	    if(connection == null){
	    	connection = DBConnection.getConnection();
	    }	    
	    Log.log(Log.INFO, "GMDAO", "getGSTStateAndNo", "Entered");
		String stateName="";
		PreparedStatement pStmt = null;
		ResultSet rsSet = null;
		if(!stateCode.equals("")||stateCode!=null){
		try {
			String query = "select ste_name from state_master where ste_Code=?";	
			pStmt = connection.prepareStatement(query);
			pStmt.setString(1, stateCode);
			rsSet = pStmt.executeQuery();
			while (rsSet.next()) {	
				stateName=rsSet.getString(1);
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
		}}
		return stateName;
}
public Vector cgPanList(String cgPan) {
	  Connection connection=null;
	    if(connection == null){
	    	connection = DBConnection.getConnection();
	    }

	    Vector cgPanlist=new Vector();
	    
	    Log.log(Log.INFO, "GMDAO", "cgPanList", "Entered");
		
		PreparedStatement pStmt,ps = null;
		ResultSet rsSet,rs = null;
		try {
			String query = "select SSI_REFERENCE_NUMBER from application_detail where CGPAN=?";	
			pStmt = connection.prepareStatement(query);
			pStmt.setString(1, cgPan);
			rsSet = pStmt.executeQuery();
			String ssiRefNo=null;
			while (rsSet.next()) {
				ssiRefNo=rsSet.getString(1);
			}
			rsSet.close();
			pStmt.close();
			String query1 = "select CGPAN from application_detail where SSI_REFERENCE_NUMBER=?";	
			ps = connection.prepareStatement(query1);
			ps.setString(1, ssiRefNo);
			rs = ps.executeQuery();
			while (rs.next()) {
				if(rs.getString(1)!=null){
					cgPanlist.add(rs.getString(1));

				}
			}
			rs.close();
			ps.close();			
							
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
		return cgPanlist;		         
}
// Added by DKR for empty Gst fom appDtl
public ArrayList getEmptyGstCgpanAppRefList(String bankId,String zoneId,String branchId) {
	 Log.log(Log.INFO, "GMDAO", "getEmptyGstCgpanList", "getEmptyGstCgpanList Entered");		
	    Connection connection=null;
	    if(connection == null){
	    	connection = DBConnection.getConnection();
	    }
	    ArrayList cgPanlist=new ArrayList();	
	    ArrayList appRefNumlist=new ArrayList();
		PreparedStatement pStmt,ps = null;
		ResultSet rsSet,rs = null;
		try {
			//String memberId = gmActionForm.getMemberIdForClosure();
			/*String bankId = memberId.substring(0, 4);
			String zoneId = memberId.substring(4, 8);
			String branchId = memberId.substring(8, 12);*/
			String query = "select app_ref_no, cgpan from application_detail  where APP_STATUS='EX' AND APP_LOAN_TYPE='TC' AND STATE_CODE IS NULL OR GST_NO IS NULL AND trunc(APP_EXPIRY_DT) BETWEEN trunc(sysdate - 365) and trunc(sysdate) and MEM_BNK_ID||MEM_ZNE_ID||MEM_BRN_ID=?";	
			pStmt = connection.prepareStatement(query);
			pStmt.setString(1, bankId+zoneId+branchId);	
			
			System.out.println(bankId+zoneId+branchId);
			
			rsSet = pStmt.executeQuery();
			//String ssiRefNo=null;
			while (rsSet.next()) {
				appRefNumlist.add(rsSet.getString(1));
				cgPanlist.add(rsSet.getString(2));
			}
			rsSet.close();
			pStmt.close();
			/*String query1 = "select CGPAN from application_detail where SSI_REFERENCE_NUMBER=?";	
			ps = connection.prepareStatement(query1);
			ps.setString(1, ssiRefNo);
			rs = ps.executeQuery();
			while (rs.next()) {
				if(rs.getString(1)!=null){
					cgPanlist.add(rs.getString(1));

				}
			}
			rs.close();
			ps.close();	*/		
							
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
		return appRefNumlist;		         
}


}