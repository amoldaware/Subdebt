package com.cgtsi.guaranteemaintenance;

import com.cgtsi.actionform.NPADateModificationActionForm;
import com.cgtsi.admin.Administrator;
import com.cgtsi.admin.Message;
import com.cgtsi.admin.User;
import com.cgtsi.application.Application;
import com.cgtsi.application.ApplicationProcessor;
import com.cgtsi.application.BorrowerDetails;
import com.cgtsi.application.LogClass;
import com.cgtsi.application.NoApplicationFoundException;
import com.cgtsi.common.DatabaseException;
import com.cgtsi.common.Log;
import com.cgtsi.common.Mailer;
import com.cgtsi.common.MailerException;
import com.cgtsi.common.MessageException;
import com.cgtsi.common.NoDataException;
import com.cgtsi.common.NoUserFoundException;
import com.cgtsi.receiptspayments.RpDAO;
import com.cgtsi.receiptspayments.VoucherDetail;
import com.cgtsi.registration.MLIInfo;
import com.cgtsi.util.DBConnection;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Hashtable;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;
import java.util.TreeMap;
import java.util.Vector;

public class GMProcessor
{
  public OutstandingDetail getOutstandingDetailsForCgpan(String cgpan)
    throws DatabaseException
  {
    Log.log(4, "GMProcessor", "getOutstandingDetailsForCgpan", "Entered");
    OutstandingDetail outstandingDetail = null;
    GMDAO gmDAO = new GMDAO();
    outstandingDetail = gmDAO.getOutstandingDetailsForCgpan(cgpan);
    Log.log(4, "GMProcessor", "getOutstandingDetailsForCgpan", "Exited");

    return outstandingDetail;
  }

  public Disbursement getDisbursementDetailsForCgpan(String cgpan)
    throws DatabaseException
  {
    Log.log(4, "GMProcessor", "getDisbursementDetailsForCgpan", "Entered");
    Disbursement disbursement = null;
    GMDAO gmDAO = new GMDAO();
    disbursement = gmDAO.getDisbursementDetailsForCgpan(cgpan);
    Log.log(4, "GMProcessor", "getDisbursementDetailsForCgpan", "Exited");

    return disbursement;
  }

  public ArrayList getNPADetails(String cgpan, String memberId)
  throws DatabaseException
{
  GMDAO gmDAO = new GMDAO();
  NPADateModificationActionForm objNPADateModificationActionForm[]=null;
  ArrayList<NPADateModificationActionForm> arrayListNPADateModificationActionForm= new ArrayList<NPADateModificationActionForm>();
  arrayListNPADateModificationActionForm=gmDAO.getNPADetailsFromCGPAN(cgpan,memberId);
  
  return arrayListNPADateModificationActionForm;
}
  public ArrayList displayShiftingCgpans(String bankId, String zoneId, String branchId)
    throws DatabaseException
  {
    GMDAO gmDAO = new GMDAO();

    ArrayList danDetails = gmDAO.getShiftCgpanForMember(bankId, zoneId, branchId);

    return danDetails;
  }

  public ArrayList displayRequestedForClosureApproval()
    throws DatabaseException
  {
    GMDAO gmDAO = new GMDAO();

    ArrayList danDetails = gmDAO.displayRequestedForClosureApproval();

    return danDetails;
  }

  public void saveNPADetails(String cgpan, String memberId , String npaxxxDate , String remarks ,String userID,String npaFormType , String newNpaEffectiveDate)
  {
	  LogClass.StepWritter("saveNPADetails called");
	  GMDAO gmDAO = new GMDAO();
	  gmDAO.saveNPAXXXDeatails(cgpan, memberId, npaxxxDate, remarks , userID,npaFormType , newNpaEffectiveDate);
  }
  public Repayment getRepaymentDetailsForCgpan(String cgpan) throws DatabaseException
  {
    Log.log(4, "GMProcessor", "getRepaymentDetailsForCgpan", "Entered");
    Repayment repayment = null;
    GMDAO gmDAO = new GMDAO();
    repayment = gmDAO.getRepaymentDetailsForCgpan(cgpan);
    Log.log(4, "GMProcessor", "getRepaymentDetailsForCgpan", "Exited");

    return repayment;
  }

  public void insertRepaymentDetails(ArrayList newRepaymentAmounts, String userId)
    throws SQLException, DatabaseException
  {
    Log.log(4, "GMProcessor", "insertRepaymentDetails", "Entered");
    GMDAO gmDAO = new GMDAO();
    int sizeOfrepaymentAmounts = 0;

    if (newRepaymentAmounts == null) {
      return;
    }
    sizeOfrepaymentAmounts = newRepaymentAmounts.size();

    for (int i = 0; i < sizeOfrepaymentAmounts; i++) {
      gmDAO.insertRepaymentDetails((RepaymentAmount)newRepaymentAmounts.get(i), userId);
    }

    Log.log(4, "GMProcessor", "insertRepaymentDetails", "Exited");
  }

  public void updateRepaymentDetails(ArrayList modifiedRepaymentAmounts, String userId)
    throws SQLException, DatabaseException
  {
    Log.log(4, "GMProcessor", "upddateRepaymentDetails", "Entered");
    GMDAO gmDAO = new GMDAO();
    int sizeOfrepaymentAmounts = 0;

    if (modifiedRepaymentAmounts == null) {
      return;
    }
    sizeOfrepaymentAmounts = modifiedRepaymentAmounts.size();

    for (int i = 0; i < sizeOfrepaymentAmounts; i++) {
      gmDAO.updateRepaymentDetails((RepaymentAmount)modifiedRepaymentAmounts.get(i), userId);
    }

    Log.log(4, "GMProcessor", "upddateRepaymentDetails", "Exited");
  }

  public void updateDisbursement(ArrayList disbursements, String userId)
    throws SQLException, DatabaseException
  {
    Log.log(4, "GMProcessor", "updateDisbursement", "Entered");
    GMDAO gmDAO = new GMDAO();
    int sizeOfdisbursements = 0;

    if (disbursements == null) {
      return;
    }
    sizeOfdisbursements = disbursements.size();

    for (int i = 0; i < sizeOfdisbursements; i++) {
      gmDAO.updateDisbursement((DisbursementAmount)disbursements.get(i), userId);
      Log.log(5, "GMProcessor", "updateDisbursement", "i=>" + i);
    }
    Log.log(4, "GMProcessor", "updateDisbursement", "Exited");
  }

  public void insertDisbursement(ArrayList disbursements, String userId)
    throws SQLException, DatabaseException
  {
    Log.log(4, "GMProcessor", "insertDisbursement", "Entered");
    GMDAO gmDAO = new GMDAO();
    int sizeOfdisbursements = 0;

    if (disbursements == null) {
      return;
    }
    sizeOfdisbursements = disbursements.size();

    for (int i = 0; i < sizeOfdisbursements; i++) {
      gmDAO.insertDisbursement((DisbursementAmount)disbursements.get(i), userId);
      Log.log(5, "GMProcessor", "insertDisbursement", "i=>" + i);
    }
    Log.log(4, "GMProcessor", "insertDisbursement", "Exited");
  }

  public void insertNPADetails(NPADetails npaDetails,Vector tcVector,Vector wcVector,Map securityMap)throws DatabaseException  {
		Log.log(Log.INFO,"GMProcessor","addNPADETAILS","Entered");
		GMDAO  gmDAO = new GMDAO();
		
		gmDAO.insertNPADetails(npaDetails, tcVector,wcVector,securityMap);
		Log.log(Log.INFO,"GMProcessor","addNPADETAILS","Exited");	
   }

  public void updateNPADetails(NPADetails npaDetails,Vector tcVector,Vector wcVector,Map securityMap)throws DatabaseException  {
                 Log.log(Log.INFO,"GMProcessor","updateNPADetails","Entered");
                 GMDAO  gmDAO = new GMDAO();
                 
                 gmDAO.updateNPADetails(npaDetails,tcVector,wcVector,securityMap);
                 Log.log(Log.INFO,"GMProcessor","updateNPADetails","Exited");    
    }

  public void insertNPAForUpload(NPADetails npaDetails)
    throws DatabaseException
  {
    Log.log(4, "GMProcessor", "insertNPAForUpload", "Entered");
    GMDAO gmDAO = new GMDAO();

    gmDAO.insertNpaForUpload(npaDetails);
    Log.log(4, "GMProcessor", "insertNPAForUpload", "Exited");
  }

  public void updateNPAForUpload(NPADetails npaDetails) throws DatabaseException
  {
    Log.log(4, "GMProcessor", "updateNPAForUpload", "Entered");
    GMDAO gmDAO = new GMDAO();

    gmDAO.updateNpaForUpload(npaDetails);
    Log.log(4, "GMProcessor", "updateNPAForUpload", "Exited");
  }

  public void insertRecAxnForUpload(RecoveryProcedure newRecoveryProcedure)
    throws DatabaseException
  {
    Log.log(4, "GMProcessor", "insertRecAxnForUpload", "Entered");
    GMDAO gmDAO = new GMDAO();

    gmDAO.insertRecAxnForUpload(newRecoveryProcedure);
    Log.log(4, "GMProcessor", "insertRecAxnForUpload", "Exited");
  }

  public void updateRecAxnForUpload(RecoveryProcedure modifiedRecoveryProcedure) throws DatabaseException
  {
    Log.log(4, "GMProcessor", "updateRecAxnForUpload", "Entered");
    GMDAO gmDAO = new GMDAO();

    gmDAO.updateRecAxnForUpload(modifiedRecoveryProcedure);
    Log.log(4, "GMProcessor", "updateRecAxnForUpload", "Exited");
  }

  public void updateLegalForUpload(LegalSuitDetail legalSuitDetail)
    throws DatabaseException
  {
    Log.log(4, "GMProcessor", "updateLegalForUpload", "Entered");
    GMDAO gmDAO = new GMDAO();

    gmDAO.updateLegalForUpload(legalSuitDetail);
    Log.log(4, "GMProcessor", "updateLegalForUpload", "Exited");
  }

  public void insertLegalForUpload(LegalSuitDetail legalSuitDetail) throws DatabaseException
  {
    Log.log(4, "GMProcessor", "insertLegalForUpload", "Entered");
    GMDAO gmDAO = new GMDAO();

    gmDAO.insertLegalForUpload(legalSuitDetail);
    Log.log(4, "GMProcessor", "insertLegalForUpload", "Exited");
  }

  public boolean closure(String cgpan, String reason, String remarks, String userId)
    throws NoApplicationFoundException, DatabaseException, NoUserFoundException
  {
    Log.log(4, "GMProcessor", "Closure ", "Entered");
    GMDAO gmDAO = new GMDAO();

    Log.log(5, "GMProcessor", "Closure ", "reason" + reason);
    Log.log(5, "GMProcessor", "Closure ", "cgpan" + cgpan);
    Log.log(5, "GMProcessor", "Closure ", "remarks" + remarks);
    Log.log(5, "GMProcessor", "Closure ", "userid" + userId);

    boolean closureStatus = false;

    if ((cgpan != null) && (userId != null)) {
      closureStatus = gmDAO.closure(cgpan, reason, remarks, userId);
    }

    Log.log(4, "GMProcessor", "Closure ", "Exited");

    return closureStatus;
  }

  public void sendMailForClosure(String cgpan, String userId, String fromId, String reason)
    throws MailerException, NoUserFoundException, NoApplicationFoundException, DatabaseException
  {
    Log.log(4, "GMProcessor", "sendMailForClosure ", "Entered");
    Administrator admin = new Administrator();
    Log.log(5, "GMProcessor", "sendMailForClosure ", "cgpan " + cgpan);
    Log.log(5, "GMProcessor", "sendMailForClosure ", "userId " + userId);
    Log.log(5, "GMProcessor", "sendMailForClosure ", "fromId" + fromId);

    String subject = "Closure Of Application";
    String messageBody = "The application " + cgpan + " is closed beacause " + reason;

    ArrayList emailToAddresses = new ArrayList();
    ArrayList mailToAddresses = new ArrayList();

    ApplicationProcessor apProcessor = new ApplicationProcessor();
    Application apps = apProcessor.getPartApplication(null, cgpan, "");

    String mliIdForCgpan = apps.getMliID();

    String userIdForCgpan = apps.getUserId();

    ArrayList activeUsers = admin.getAllUsers(mliIdForCgpan);

    String activeEmailId = null;
    String activeUserMailId = null;
    for (int i = 0; i < activeUsers.size(); i++)
    {
      User activeUserId = (User)activeUsers.get(i);

      activeEmailId = activeUserId.getEmailId();

      activeUserMailId = activeUserId.getUserId();

      emailToAddresses.add(activeEmailId);
      mailToAddresses.add(activeUserMailId);
    }

    Message emailMessage = new Message(emailToAddresses, null, null, subject, messageBody);
    Message mailMessage = new Message(mailToAddresses, null, null, subject, messageBody);

    emailMessage.setFrom(fromId);
    mailMessage.setFrom(userId);

    Mailer mailer = new Mailer();

    admin.sendMail(mailMessage);

    mailer.sendEmail(emailMessage);

    Log.log(4, "GMProcessor", "sendMailForClosure ", "Exited");
  }

  public TreeMap viewMemberIdCgpansForClosure(String feeType)
    throws DatabaseException
  {
    Log.log(4, "GMProcessor", "viewMemberIdCgpansForClosure", "Entered");
    GMDAO gmDAO = new GMDAO();
    TreeMap memberIdCgpans = null;

    memberIdCgpans = gmDAO.getMemberIdCgpansForClosure(feeType);

    Log.log(4, "GMProcessor", "viewMemberIdCgpansForClosure", "Exited");

    return memberIdCgpans;
  }

  public ArrayList viewCgpansForClosure(String memberId, String feeType)
    throws DatabaseException
  {
    Log.log(4, "GMProcessor", "viewCgpansForClosure", "Entered");

    GMDAO gmDAO = new GMDAO();

    ArrayList cgpans = null;

    cgpans = gmDAO.getCgpansForClosure(memberId, feeType);

    Log.log(4, "GMProcessor", "viewCgpansForClosure", "Exited");

    return cgpans;
  }

  public void updateBorrowerDetails(BorrowerDetails borrowerDetail, String userId)
    throws DatabaseException
  {
    Log.log(4, "GMProcessor", "updateBorrowerDetails", "Entered");
    GMDAO gmDAO = new GMDAO();

    if (borrowerDetail == null) {
      return;
    }
    gmDAO.updateBorrowerDetails(borrowerDetail, userId);

    Log.log(4, "GMProcessor", "updateborrowerDetails", "Exited");
  }

  public void addRecoveryDetails(Recovery recoveryDetails)
    throws DatabaseException
  {
    Log.log(4, "GMProcessor", "addRecoverDetails", "Enetred");
    GMDAO gmDAO = new GMDAO();

    if (recoveryDetails == null) {
      return;
    }
    gmDAO.updateRecoveryDetails(recoveryDetails);
    Log.log(4, "GMProcessor", "addRecoveryDetails", "Exited");
  }

  public void modifyRecoveryDetails(Recovery recoveryDetails)
    throws DatabaseException
  {
    Log.log(4, "GMProcessor", "modifyRecoveryDetails", "Enetred");
    GMDAO gmDAO = new GMDAO();

    if (recoveryDetails == null) {
      return;
    }
    gmDAO.modifyRecoveryDetails(recoveryDetails);
    Log.log(4, "GMProcessor", "modifyRecoveryDetails", "Exited");
  }

  public ArrayList updateTcOutstanding(ArrayList outstandingAmounts)
    throws SQLException, DatabaseException
  {
    Log.log(4, "GMProcessor", "updateOutstanding", "Entered");
    GMDAO gmDAO = new GMDAO();
    OutstandingAmount outstandingAmount = null;
    int sizeOfOutstandingAmounts = 0;

    if (outstandingAmounts == null)
    {
      return null;
    }
    sizeOfOutstandingAmounts = outstandingAmounts.size();
    for (int i = 0; i < sizeOfOutstandingAmounts; i++) {
      outstandingAmount = (OutstandingAmount)outstandingAmounts.get(i);

      gmDAO.updateTcOutstandingDetails(outstandingAmount);
    }
    Log.log(4, "GMProcessor", "updateoutstanding", "Exited");

    return null;
  }

  public ArrayList updateWcOutstanding(ArrayList outstandingAmounts)
    throws SQLException, DatabaseException
  {
    Log.log(4, "GMProcessor", "updateWcOutstanding", "Entered");
    GMDAO gmDAO = new GMDAO();
    OutstandingAmount outstandingAmount = null;
    int sizeOfOutstandingAmounts = 0;

    if (outstandingAmounts == null)
    {
      return null;
    }
    sizeOfOutstandingAmounts = outstandingAmounts.size();
    for (int i = 0; i < sizeOfOutstandingAmounts; i++) {
      outstandingAmount = (OutstandingAmount)outstandingAmounts.get(i);

      gmDAO.updateWcOutstandingDetails(outstandingAmount);
    }
    Log.log(4, "GMProcessor", "updateWcOutstanding", "Exited");

    return null;
  }

  public ArrayList insertTcOutstanding(ArrayList outstandingAmounts)
    throws SQLException, DatabaseException
  {
    Log.log(4, "GMProcessor", "insertTcOutstanding", "Entered");
    GMDAO gmDAO = new GMDAO();
    OutstandingAmount outstandingAmount = null;
    int sizeOfOutstandingAmounts = 0;

    if (outstandingAmounts == null)
    {
      return null;
    }
    sizeOfOutstandingAmounts = outstandingAmounts.size();
    for (int i = 0; i < sizeOfOutstandingAmounts; i++) {
      outstandingAmount = (OutstandingAmount)outstandingAmounts.get(i);
      gmDAO.insertTcOutstandingDetails(outstandingAmount);
    }
    Log.log(4, "GMProcessor", "insertTcOutstanding", "Exited");

    return null;
  }

  public ArrayList insertWcOutstanding(ArrayList outstandingAmounts)
    throws SQLException, DatabaseException
  {
    Log.log(4, "GMProcessor", "insertWcOutstanding", "Entered");
    GMDAO gmDAO = new GMDAO();
    OutstandingAmount outstandingAmount = null;
    int sizeOfOutstandingAmounts = 0;

    if (outstandingAmounts == null)
    {
      return null;
    }
    sizeOfOutstandingAmounts = outstandingAmounts.size();
    for (int i = 0; i < sizeOfOutstandingAmounts; i++) {
      outstandingAmount = (OutstandingAmount)outstandingAmounts.get(i);
      gmDAO.insertWcOutstandingDetails(outstandingAmount);
    }
    Log.log(4, "GMProcessor", "insertWcOutstanding", "Exited");

    return null;
  }

  public boolean shiftCgpanBorrower(String srcId, String userId, String cgpan, String destId)
    throws DatabaseException
  {
    Log.log(4, "GMProcessor", "ShiftCgpanBorrower", "Entered");

    GMDAO gmDAO = new GMDAO();

    if ((cgpan == null) || (destId.equals("")))
    {
      return false;
    }

    gmDAO.shiftCgpanBorrower(srcId, userId, cgpan, destId);

    Log.log(4, "GMProcessor", "ShiftCgpanBorrower", "Exited");

    return true;
  }

  public BorrowerDetails viewBorrowerDetails(String memberId, String id, int type)
    throws NoDataException, DatabaseException
  {
	//  ////System.out.println("GMP  viewBorrowerDetails  S");
    Log.log(4, "GMProcessor", "viewBorrowerDetails", "Entered");
    GMDAO gmDAO = new GMDAO();

    BorrowerDetails borrowerDetails = null;

    borrowerDetails = gmDAO.getBorrowerDetails(memberId, id, type);

    if (borrowerDetails == null) {
      throw new NoDataException("BorrowerDetails are not found");
    }

    Log.log(4, "GMProcessor", "viewBorrowerDetails", "Exited");
 // ////System.out.println("GMP   viewBorrowerDetails  E");
    return borrowerDetails;
  }
  
  //niteen
  
  
 /* public ArrayList viewBorrowerCgpan(String borrowerId)
  throws NoUserFoundException, SQLException, DatabaseException
{
  Log.log(4, "GMProcessor", "viewOutstandingDetails", "Entered");

  GMDAO gmDAO = new GMDAO();
  ArrayList periodicInfos = null;

  periodicInfos = gmDAO.getOutstandingDetails(id, type);

  if (periodicInfos == null) {
    throw new NoUserFoundException("User details not found");
  }

  Log.log(4, "GMProcessor", "viewOutstandingDetails", "Exited");

  return periodicInfos;
}


public String insertVoucherDetails(VoucherDetail voucherDetail, 
                                       String userId) throws MessageException, 
                                                             DatabaseException {
        RpDAO rpDAO = new RpDAO();
        return rpDAO.insertVoucherDetails(voucherDetail, userId, null);
    }




  */
  
  
  
  public String viewBorrowerCgpan(String borrowerId)throws DatabaseException, 
  DatabaseException  {
	//  ////System.out.println("GMProcessor   viewBorrowerCgpan  S");
		// TODO Auto-generated method stub
	  String cgpan ="";
	 
      PreparedStatement pStmt = null;
      ArrayList aList = new ArrayList();
      ResultSet rsSet = null;
		
      Connection connection = DBConnection.getConnection();
      try {
    	  
      //query=select cgpan from application_detail a,ssi_detail b where a.ssi_reference_number=b.ssi_reference_number and app_status not in ('RE')  and bid='HA1302350'
          String query = 
              //"SELECT SSI_REFERENCE_NUMBER FROM APPLICATION_DETAIL WHERE CGPAN=?";
        	  "SELECT CGPAN FROM APPLICATION_DETAIL A,SSI_DETAIL B WHERE A.SSI_REFERENCE_NUMBER=B.SSI_REFERENCE_NUMBER AND APP_STATUS NOT IN('RE') AND BID=?";
         // ////System.out.println("Query"+query);
          pStmt = connection.prepareStatement(query);
          pStmt.setString(1, borrowerId);
          rsSet = pStmt.executeQuery();
          while ( rsSet.next() )
          {
      
         
        	  cgpan = cgpan.concat(",").concat(rsSet.getString(1))  ;
          
         // ////System.out.println("cgpan = rsSet.getString(1)"+cgpan);
          }
          rsSet.close();
          pStmt.close();
      } catch (Exception exception) {
    	  throw new DatabaseException("Something ["+exception.getMessage()+"] went wrong, Please contact Support[support@cgtmse.in] team");
      } finally {
          DBConnection.freeConnection(connection);
      }
         ////////System.out.println("viewBorrowerCgpan E"); 
		return cgpan;
	}
  
  
  

  public ArrayList viewOutstandingDetails(String id, int type)
    throws NoUserFoundException, SQLException, DatabaseException
  {
    Log.log(4, "GMProcessor", "viewOutstandingDetails", "Entered");

    GMDAO gmDAO = new GMDAO();
    ArrayList periodicInfos = null;

    periodicInfos = gmDAO.getOutstandingDetails(id, type);

    if (periodicInfos == null) {
      throw new NoUserFoundException("User details not found");
    }

    Log.log(4, "GMProcessor", "viewOutstandingDetails", "Exited");

    return periodicInfos;
  }

  public ArrayList viewDisbursementDetails(String id, int type)
    throws DatabaseException
  {
    Log.log(4, "GMProcessor", "viewDisbursementDetails", "Entered");
    GMDAO gmDAO = new GMDAO();
    ArrayList periodicInfoList = new ArrayList();

    periodicInfoList = gmDAO.getDisbursementDetails(id, type);

    Log.log(4, "GMProcessor", "viewDisbursementDetails", "Exited");

    return periodicInfoList;
  }

  public ArrayList viewRepaymentSchedule(String id, int type)
    throws NoRepaymentScheduleException, DatabaseException
  {
    Log.log(4, "GMProcessor", "viewRepaymentSchedule", "Entered");
    GMDAO gmDAO = new GMDAO();

    Log.log(5, "GMAction", "inside processor --", "Entered");
    ArrayList repaymentSchedules = gmDAO.getRepaymentSchedule(id, type);

    if (repaymentSchedules == null) {
      throw new NoRepaymentScheduleException("No repayment details found");
    }

    Log.log(4, "GMProcessor", "viewRepaymentSchedule", "Exited");

    return repaymentSchedules;
  }

  public ArrayList viewRepaymentDetails(String id, int type)
    throws DatabaseException
  {
    Log.log(4, "GMProcessor", "viewRepaymentDetails", "Entered");

    GMDAO gmDAO = new GMDAO();
    ArrayList periodicInfos = null;

    periodicInfos = gmDAO.getRepaymentDetails(id, type);

    Log.log(4, "GMProcessor", "viewRepaymentDetails", "Exited");

    return periodicInfos;
  }

  public void updateRepaymentSchedule(ArrayList repaymentSchedules, String userId)
    throws DatabaseException
  {
    Log.log(4, "GMProcessor", "updateRepaymentSchedule", "Entered");
    GMDAO gmDAO = new GMDAO();
    int sizeOfrepaymentSchedules = 0;

    if (repaymentSchedules == null)
    {
      return;
    }
    sizeOfrepaymentSchedules = repaymentSchedules.size();
    for (int i = 0; i < sizeOfrepaymentSchedules; i++) {
      gmDAO.updateRepaymentSchedule((RepaymentSchedule)repaymentSchedules.get(i), userId);

      Log.log(5, "GMProcessor", "updateRepaymentSchedule", "i=>" + i);
    }
    Log.log(4, "GMProcessor", "updateRepaymentSchedule", "Exited");
  }

  public HashMap viewClosureDetails(String id, int type, String memberId)
    throws DatabaseException
  {
    Log.log(4, "GMProcessor", "viewClosureDetails", "Entered");

    GMDAO gmDao = new GMDAO();
    HashMap closureDetails = null;

    Log.log(5, "GMProcessor", "viewClosureDetails", "ID : " + id);

    closureDetails = gmDao.getClosureDetails(id, type, memberId);

    Log.log(5, "GMProcessor", "viewClosureDetails", "size of closure details arr :" + closureDetails.size());

    Log.log(4, "GMProcessor", "viewClosureDetails", "Exited");

    return closureDetails;
  }

  public HashMap getClosureDetailsForFeeNotPaid(String cgpan)
    throws DatabaseException
  {
    Log.log(4, "GMProcessor", "getClosureDetailsForFeeNotPaid", "Entered");

    GMDAO gmDao = new GMDAO();
    HashMap closureDetails = null;

    Log.log(5, "GMProcessor", "getClosureDetailsForFeeNotPaid", "cgpan :" + cgpan);

    closureDetails = gmDao.getClosureDetailsForFeeNotPaid(cgpan);

    Log.log(5, "GMProcessor", "getClosureDetailsForFeeNotPaid", "size of closure details arr :" + closureDetails.size());

    Log.log(4, "GMProcessor", "getClosureDetailsForFeeNotPaid", "Exited");

    return closureDetails;
  }

  public ArrayList getRecoveryDetails(String borrowerId)
    throws DatabaseException
  {
    Log.log(4, "GMProcessor", "getRecoveryDetails", "Entered");

    GMDAO gmDAO = new GMDAO();
    ArrayList recoveryDetails = null;

    recoveryDetails = gmDAO.getRecoveryDetails(borrowerId);

    Log.log(4, "GMProcessor", "getRecoveryDetails", "Exited");

    return recoveryDetails;
  }

  public NPADetails getNPADetails(String borrowerId) throws DatabaseException
  {
    Log.log(4, "GMProcessor", "getNPADetails", "Entered");

    GMDAO gmDAO = new GMDAO();
    NPADetails npaDetails = null;

    Log.log(5, "GMProcessor", "getNPADetails", "Borrower Id : " + borrowerId);

    npaDetails = gmDAO.getNPADetails(borrowerId);
    if (npaDetails == null) {
      Log.log(5, "GMProcessor", "getNPADetails", "npaDetails==null");
    }

    Log.log(4, "GMProcessor", "getNPADetails", "Exited");

    return npaDetails;
  }

  public ArrayList getAllActions()
    throws DatabaseException
  {
    Log.log(4, "GMProcessor", "getAllActions", "Entered");
    GMDAO gmDAO = new GMDAO();
    ArrayList actions = null;

    actions = gmDAO.getAllActions();

    Log.log(4, "GMProcessor", "getAllActions", "Exited");

    return actions;
  }

  public ArrayList getAllReasonsForClosure()
    throws DatabaseException
  {
    Log.log(4, "GMProcessor", "getAllReasonsForClosure", "Entered");
    GMDAO gmDAO = new GMDAO();
    ArrayList closureReasons = null;

    closureReasons = gmDAO.getAllReasonsForClosure();

    Log.log(4, "GMProcessor", "getAllReasonsForClosure", "Exited");

    return closureReasons;
  }

  public CgpanDetail getCgpanDetails(String cgpan)
    throws DatabaseException
  {
    Log.log(4, "GMProcessor", "getCgpanDetails", "Entered");
    GMDAO gmDAO = new GMDAO();
    CgpanDetail cgpanDtl = null;
    cgpanDtl = gmDAO.getCgpanDetails(cgpan);

    Log.log(4, "GMProcessor", "getCgpanDetails", "Exited");

    return cgpanDtl;
  }

  public String getBorrowerIdForBorrowerName(String borrowerName)
    throws NoDataException, DatabaseException
  {
    GMDAO gmDAO = new GMDAO();
    String borrowerId = null;

    borrowerId = gmDAO.getBorrowerIdForBorrowerName(borrowerName);
    if (borrowerId == null) {
      throw new NoDataException("No borrower id found for the borrower Name");
    }

    return borrowerId;
  }

  public ArrayList getBorrowerIds(String memberId)
    throws DatabaseException
  {
    Log.log(4, "GMProcessor", "getBorrowerIds", "Entered");

    GMDAO gmDAO = new GMDAO();
    ArrayList borrowerIds = null;

    borrowerIds = gmDAO.getBorrowerIds(memberId);

    Log.log(4, "GMProcessor", "getBorrowerIds", "Exited");

    return borrowerIds;
  }

  public String getMemIdforCgpan(String cgpanToShift)
    throws DatabaseException
  {
    Log.log(4, "GMProcessor", "getMemIdforCgpan", "Entered");

    GMDAO gmDAO = new GMDAO();
    String memIdforCgpan = null;

    memIdforCgpan = gmDAO.getMemIdforCgpan(cgpanToShift);

    Log.log(4, "GMProcessor", "getMemIdforCgpan", "Exited");

    return memIdforCgpan;
  }

  public String getallocationStatusforCgpan(String cgpanToShift)
    throws DatabaseException
  {
    Log.log(4, "GMProcessor", "getallocationStatusforCgpan", "Entered");

    GMDAO gmDAO = new GMDAO();
    String memIdforCgpan = null;

    memIdforCgpan = gmDAO.getallocationStatusforCgpan(cgpanToShift);

    Log.log(4, "GMProcessor", "getallocationStatusforCgpan", "Exited");

    return memIdforCgpan;
  }

  public Vector getCGPANs(String borrowerId)
    throws NoDataException, DatabaseException
  {
    Log.log(4, "GMProcessor", "getCGPANs", "Entered");
    GMDAO gmDAO = new GMDAO();
    Vector cgpans = gmDAO.getCGPANDetailsForBId(borrowerId);
    Log.log(4, "GMProcessor", "getCGPANs", "Exited");

    return cgpans;
  }

  public ArrayList uploadGmApplication(Hashtable gmApp, String userId, String bankId, String zneId, String brnId)
    throws NoApplicationFoundException, NoUserFoundException, SQLException, DatabaseException
  {
    Log.log(4, "GMProcessor", "uploadGmApplication", "Entered");

    GMDAO gmDAO = new GMDAO();
    Set borrowerIdSet = gmApp.keySet();
    Iterator borrowerIdIterator = borrowerIdSet.iterator();

    String borrowerIdKey = null;
    ArrayList outstandingAmounts = null;
    String memberId = bankId + zneId + brnId;

    Log.log(5, "GMProcessor", "uploadGmApplication", "memberId:" + memberId);
    ArrayList borrowerIds = gmDAO.getBorrowerIds(memberId);

    ArrayList errorMessages = new ArrayList();
    ArrayList periodicInfoDetails = new ArrayList();

    Administrator administrator = new Administrator();
    User user = administrator.getUserInfo(userId);
    String fromId = user.getEmailId();

    ArrayList cgpansToBeClosed = new ArrayList();

    while (borrowerIdIterator.hasNext())
    {
      cgpansToBeClosed = new ArrayList();
      borrowerIdKey = (String)borrowerIdIterator.next();
      Log.log(5, "GMProcessor", "uploadGmApplication", "borrowerIdKey:" + borrowerIdKey);

      Log.log(5, "GMProcessor", "uploadGmApplication", "OSDetails-borrower Id:" + borrowerIdKey);
      if (borrowerIds.contains(borrowerIdKey))
      {
        PeriodicInfo periodicInfo = (PeriodicInfo)gmApp.get(borrowerIdKey);
        if (periodicInfo.getIsVerified())
        {
          NPADetails npaDetailsTC = periodicInfo.getNpaDetails();
          double amountClaimedTC = 0.0D;
          String courtNameTC = null;
          String currentStatusTC = null;
          Date dtOfFilingLegalSuitTC = null;
          String forumNameTC = null;
          String legalSuiteNoTC = null;
          String locationTC = null;
          String npaIdTC1 = null;
          String recoveryProceedingsConcludedTC = null;

          if (npaDetailsTC != null)
          {
            String npaIdTC = npaDetailsTC.getNpaId();
            String cgbidTC = npaDetailsTC.getCgbid();
            String bankFacilityDetailTC = npaDetailsTC.getBankFacilityDetail();
            String creditSupportTC = npaDetailsTC.getCreditSupport();
            String detailsOfFinAssistanceTC = npaDetailsTC.getDetailsOfFinAssistance();
            Date effortsConclusionDateTC = npaDetailsTC.getEffortsConclusionDate();
            String effortsTakenTC = npaDetailsTC.getEffortsTaken();
            String isRecoveryInitiatedTC = npaDetailsTC.getIsRecoveryInitiated();
            String mliCommentOnFinPositionTC = npaDetailsTC.getMliCommentOnFinPosition();
            int noOfActionsTC = npaDetailsTC.getNoOfActions();
            Date npaDateTC = npaDetailsTC.getNpaDate();
            String npaReasonTC = npaDetailsTC.getNpaReason();
            double osAmtOnNPATC = npaDetailsTC.getOsAmtOnNPA();
            String placeUnderWatchListTC = npaDetailsTC.getPlaceUnderWatchList();
            String referenceTC = npaDetailsTC.getReference();
            Date reportingDateTC = npaDetailsTC.getReportingDate();
            String remarksOnNpaTC = npaDetailsTC.getRemarksOnNpa();
            String whetherNPAReportedTC = npaDetailsTC.getWhetherNPAReported();
            String willfulDefaulterTC = npaDetailsTC.getWillfulDefaulter();

            LegalSuitDetail legalSuitDetailTC = npaDetailsTC.getLegalSuitDetail();
            if ((legalSuitDetailTC != null) && (legalSuitDetailTC.getCourtName() != null))
            {
              amountClaimedTC = legalSuitDetailTC.getAmountClaimed();
              courtNameTC = legalSuitDetailTC.getCourtName();
              currentStatusTC = legalSuitDetailTC.getCurrentStatus();
              dtOfFilingLegalSuitTC = legalSuitDetailTC.getDtOfFilingLegalSuit();
              forumNameTC = legalSuitDetailTC.getForumName();
              legalSuiteNoTC = legalSuitDetailTC.getLegalSuiteNo();
              locationTC = legalSuitDetailTC.getLocation();
              npaIdTC1 = legalSuitDetailTC.getNpaId();
              recoveryProceedingsConcludedTC = legalSuitDetailTC.getRecoveryProceedingsConcluded();
            }

            NPADetails npaDetailsGM = gmDAO.getNPADetails(borrowerIdKey);

            if (npaDetailsGM != null)
            {
              String npaIdGM = npaDetailsGM.getNpaId();

              if (npaIdTC != null)
              {
                if (npaIdTC.equals(npaIdGM))
                {
                  NPADetails npaDetailsUpdate = new NPADetails();
                  npaDetailsUpdate.setBankFacilityDetail(bankFacilityDetailTC);
                  npaDetailsUpdate.setCgbid(borrowerIdKey);

                  npaDetailsUpdate.setCreditSupport(creditSupportTC);
                  npaDetailsUpdate.setDetailsOfFinAssistance(detailsOfFinAssistanceTC);
                  npaDetailsUpdate.setEffortsConclusionDate(effortsConclusionDateTC);
                  npaDetailsUpdate.setEffortsTaken(effortsTakenTC);
                  npaDetailsUpdate.setIsRecoveryInitiated(isRecoveryInitiatedTC);
                  npaDetailsUpdate.setMliCommentOnFinPosition(mliCommentOnFinPositionTC);
                  npaDetailsUpdate.setNoOfActions(noOfActionsTC);
                  npaDetailsUpdate.setNpaDate(npaDateTC);
                  npaDetailsUpdate.setNpaId(npaIdTC);
                  npaDetailsUpdate.setNpaReason(npaReasonTC);
                  npaDetailsUpdate.setOsAmtOnNPA(osAmtOnNPATC);
                  npaDetailsUpdate.setPlaceUnderWatchList(placeUnderWatchListTC);
                  npaDetailsUpdate.setReference(referenceTC);
                  npaDetailsUpdate.setRemarksOnNpa(remarksOnNpaTC);
                  npaDetailsUpdate.setReportingDate(reportingDateTC);
                  npaDetailsUpdate.setWhetherNPAReported(whetherNPAReportedTC);
                  npaDetailsUpdate.setWillfulDefaulter(willfulDefaulterTC);
                  gmDAO.updateNpaForUpload(npaDetailsUpdate);

                  if (isRecoveryInitiatedTC.equalsIgnoreCase("Y"))
                  {
                    LegalSuitDetail legalSuitDetailUpdate = new LegalSuitDetail();
                    if ((legalSuitDetailTC != null) && (legalSuitDetailTC.getCourtName() != null))
                    {
                      legalSuitDetailUpdate.setAmountClaimed(amountClaimedTC);
                      legalSuitDetailUpdate.setCourtName(courtNameTC);
                      legalSuitDetailUpdate.setCurrentStatus(currentStatusTC);
                      legalSuitDetailUpdate.setDtOfFilingLegalSuit(dtOfFilingLegalSuitTC);
                      legalSuitDetailUpdate.setForumName(forumNameTC);
                      legalSuitDetailUpdate.setLegalSuiteNo(legalSuiteNoTC);
                      legalSuitDetailUpdate.setLocation(locationTC);
                      legalSuitDetailUpdate.setNpaId(npaIdTC);

                      legalSuitDetailUpdate.setRecoveryProceedingsConcluded(recoveryProceedingsConcludedTC);

                      gmDAO.updateLegalForUpload(legalSuitDetailUpdate);
                    }

                    ArrayList recoveryProcedureTC = npaDetailsTC.getRecoveryProcedure();
                    int recoveryProcedureTCSize = recoveryProcedureTC.size();
                    for (int z = 0; z < recoveryProcedureTCSize; z++)
                    {
                      RecoveryProcedure recoveryProcedureObjTC = (RecoveryProcedure)recoveryProcedureTC.get(z);
                      RecoveryProcedure recoveryProcedureObjFinalTC = new RecoveryProcedure();
                      Date actionDate = recoveryProcedureObjTC.getActionDate();
                      String actionDetails = recoveryProcedureObjTC.getActionDetails();
                      String actionType = recoveryProcedureObjTC.getActionType();
                      String attachmentName = recoveryProcedureObjTC.getAttachmentName();
                      String npaIdTC2 = recoveryProcedureObjTC.getNpaId();
                      String radId = recoveryProcedureObjTC.getRadId();
                      recoveryProcedureObjFinalTC.setActionDate(recoveryProcedureObjTC.getActionDate());
                      recoveryProcedureObjFinalTC.setActionDetails(recoveryProcedureObjTC.getActionDetails());
                      recoveryProcedureObjFinalTC.setActionType(recoveryProcedureObjTC.getActionType());
                      recoveryProcedureObjFinalTC.setAttachmentName(recoveryProcedureObjTC.getAttachmentName());
                      recoveryProcedureObjFinalTC.setNpaId(recoveryProcedureObjTC.getNpaId());

                      if ((radId == null) || (radId.equals("")))
                      {
                        gmDAO.insertRecAxnForUpload(recoveryProcedureObjFinalTC);
                      }
                      else
                      {
                        recoveryProcedureObjFinalTC.setRadId(radId);

                        gmDAO.updateRecAxnForUpload(recoveryProcedureObjFinalTC);
                      }
                    }
                  }

                }

              }
              else if (npaIdTC == null)
              {
                NPADetails npaDetailsInsert = new NPADetails();
                npaDetailsInsert.setBankFacilityDetail(bankFacilityDetailTC);
                npaDetailsInsert.setCgbid(borrowerIdKey);
                npaDetailsInsert.setCreditSupport(creditSupportTC);
                npaDetailsInsert.setDetailsOfFinAssistance(detailsOfFinAssistanceTC);
                npaDetailsInsert.setEffortsConclusionDate(effortsConclusionDateTC);
                npaDetailsInsert.setEffortsTaken(effortsTakenTC);
                npaDetailsInsert.setIsRecoveryInitiated(isRecoveryInitiatedTC);
                npaDetailsInsert.setMliCommentOnFinPosition(mliCommentOnFinPositionTC);
                npaDetailsInsert.setNoOfActions(noOfActionsTC);
                npaDetailsInsert.setNpaDate(npaDateTC);

                npaDetailsInsert.setNpaReason(npaReasonTC);
                npaDetailsInsert.setOsAmtOnNPA(osAmtOnNPATC);
                npaDetailsInsert.setPlaceUnderWatchList(placeUnderWatchListTC);
                npaDetailsInsert.setReference(referenceTC);
                npaDetailsInsert.setRemarksOnNpa(remarksOnNpaTC);
                npaDetailsInsert.setReportingDate(reportingDateTC);
                npaDetailsInsert.setWhetherNPAReported(whetherNPAReportedTC);
                npaDetailsInsert.setWillfulDefaulter(willfulDefaulterTC);
                String npaId = gmDAO.insertNpaForUpload(npaDetailsInsert);

                if (isRecoveryInitiatedTC.equalsIgnoreCase("Y"))
                {
                  LegalSuitDetail legalSuitDetailInsert = new LegalSuitDetail();
                  legalSuitDetailInsert.setAmountClaimed(amountClaimedTC);
                  legalSuitDetailInsert.setCourtName(courtNameTC);
                  legalSuitDetailInsert.setCurrentStatus(currentStatusTC);
                  legalSuitDetailInsert.setDtOfFilingLegalSuit(dtOfFilingLegalSuitTC);
                  legalSuitDetailInsert.setForumName(forumNameTC);
                  legalSuitDetailInsert.setLegalSuiteNo(legalSuiteNoTC);
                  legalSuitDetailInsert.setLocation(locationTC);
                  legalSuitDetailInsert.setNpaId(npaId);

                  legalSuitDetailInsert.setRecoveryProceedingsConcluded(recoveryProceedingsConcludedTC);
                  gmDAO.insertLegalForUpload(legalSuitDetailInsert);

                  ArrayList recoveryProcedureTC = npaDetailsTC.getRecoveryProcedure();
                  int recoveryProcedureTCSize = recoveryProcedureTC.size();
                  for (int z = 0; z < recoveryProcedureTCSize; z++)
                  {
                    RecoveryProcedure recoveryProcedureObjTC = (RecoveryProcedure)recoveryProcedureTC.get(z);
                    RecoveryProcedure recoveryProcedureObjFinalTC = new RecoveryProcedure();
                    Date actionDate = recoveryProcedureObjTC.getActionDate();
                    String actionDetails = recoveryProcedureObjTC.getActionDetails();
                    String actionType = recoveryProcedureObjTC.getActionType();
                    String attachmentName = recoveryProcedureObjTC.getAttachmentName();

                    recoveryProcedureObjFinalTC.setActionDate(recoveryProcedureObjTC.getActionDate());
                    recoveryProcedureObjFinalTC.setActionDetails(recoveryProcedureObjTC.getActionDetails());
                    recoveryProcedureObjFinalTC.setActionType(recoveryProcedureObjTC.getActionType());
                    recoveryProcedureObjFinalTC.setAttachmentName(recoveryProcedureObjTC.getAttachmentName());
                    recoveryProcedureObjFinalTC.setNpaId(npaId);

                    gmDAO.insertRecAxnForUpload(recoveryProcedureObjFinalTC);
                  }

                }

              }

            }
            else
            {
              NPADetails npaDetailsInsert = new NPADetails();
              npaDetailsInsert.setBankFacilityDetail(bankFacilityDetailTC);
              npaDetailsInsert.setCgbid(borrowerIdKey);

              npaDetailsInsert.setCreditSupport(creditSupportTC);
              npaDetailsInsert.setDetailsOfFinAssistance(detailsOfFinAssistanceTC);
              npaDetailsInsert.setEffortsConclusionDate(effortsConclusionDateTC);
              npaDetailsInsert.setEffortsTaken(effortsTakenTC);
              npaDetailsInsert.setIsRecoveryInitiated(isRecoveryInitiatedTC);
              npaDetailsInsert.setMliCommentOnFinPosition(mliCommentOnFinPositionTC);
              npaDetailsInsert.setNoOfActions(noOfActionsTC);
              npaDetailsInsert.setNpaDate(npaDateTC);
              npaDetailsInsert.setNpaReason(npaReasonTC);
              npaDetailsInsert.setOsAmtOnNPA(osAmtOnNPATC);
              npaDetailsInsert.setPlaceUnderWatchList(placeUnderWatchListTC);
              npaDetailsInsert.setReference(referenceTC);
              npaDetailsInsert.setRemarksOnNpa(remarksOnNpaTC);
              npaDetailsInsert.setReportingDate(reportingDateTC);
              npaDetailsInsert.setWhetherNPAReported(whetherNPAReportedTC);
              npaDetailsInsert.setWillfulDefaulter(willfulDefaulterTC);
              String npaId = gmDAO.insertNpaForUpload(npaDetailsInsert);

              if (isRecoveryInitiatedTC.equalsIgnoreCase("Y"))
              {
                LegalSuitDetail legalSuitDetailInsert = new LegalSuitDetail();
                legalSuitDetailInsert.setAmountClaimed(amountClaimedTC);
                legalSuitDetailInsert.setCourtName(courtNameTC);
                legalSuitDetailInsert.setCurrentStatus(currentStatusTC);
                legalSuitDetailInsert.setDtOfFilingLegalSuit(dtOfFilingLegalSuitTC);
                legalSuitDetailInsert.setForumName(forumNameTC);
                legalSuitDetailInsert.setLegalSuiteNo(legalSuiteNoTC);
                legalSuitDetailInsert.setLocation(locationTC);
                legalSuitDetailInsert.setNpaId(npaId);
                legalSuitDetailInsert.setRecoveryProceedingsConcluded(recoveryProceedingsConcludedTC);
                gmDAO.insertLegalForUpload(legalSuitDetailInsert);

                ArrayList recoveryProcedureTC = npaDetailsTC.getRecoveryProcedure();
                int recoveryProcedureTCSize = recoveryProcedureTC.size();

                for (int z = 0; z < recoveryProcedureTCSize; z++)
                {
                  RecoveryProcedure recoveryProcedureObjTC = (RecoveryProcedure)recoveryProcedureTC.get(z);
                  RecoveryProcedure recoveryProcedureObjFinalTC = new RecoveryProcedure();
                  Date actionDate = recoveryProcedureObjTC.getActionDate();
                  String actionDetails = recoveryProcedureObjTC.getActionDetails();
                  String actionType = recoveryProcedureObjTC.getActionType();
                  String attachmentName = recoveryProcedureObjTC.getAttachmentName();

                  recoveryProcedureObjFinalTC.setActionDate(recoveryProcedureObjTC.getActionDate());
                  recoveryProcedureObjFinalTC.setActionDetails(recoveryProcedureObjTC.getActionDetails());
                  recoveryProcedureObjFinalTC.setActionType(recoveryProcedureObjTC.getActionType());
                  recoveryProcedureObjFinalTC.setAttachmentName(recoveryProcedureObjTC.getAttachmentName());
                  recoveryProcedureObjFinalTC.setNpaId(npaId);

                  gmDAO.insertRecAxnForUpload(recoveryProcedureObjFinalTC);
                }

              }

            }

          }

          ArrayList disDtlsThinClient = periodicInfo.getDisbursementDetails();

          int disDtlsThinClientSize = disDtlsThinClient.size();

          ArrayList disbDtlsGM = gmDAO.getDisbursementDetails(borrowerIdKey, 0);

          if (disDtlsThinClient != null)
          {
            int disDtlsSize = disDtlsThinClient.size();
            Log.log(5, "GMProcessor", "uploadGmApplication", "disDtlsTCSize:" + disDtlsSize);
            for (int i = 0; i < disDtlsSize; i++)
            {
              Disbursement disDetailThinClient = (Disbursement)disDtlsThinClient.get(i);

              String cgpanThinClient = disDetailThinClient.getCgpan();

              String schemeThinClient = disDetailThinClient.getScheme();

              ArrayList disDtlsArrayThinClient = disDetailThinClient.getDisbursementAmounts();
              if (disDtlsArrayThinClient != null)
              {
                int disDtlsArrayThinClientSize = disDtlsArrayThinClient.size();

                for (int x = 0; x < disDtlsArrayThinClientSize; x++)
                {
                  DisbursementAmount dAmountTC = (DisbursementAmount)disDtlsArrayThinClient.get(x);
                  String disIdThinClient = dAmountTC.getDisbursementId();

                  double dAmountThinClient = dAmountTC.getDisbursementAmount();

                  Date disDateThinClient = dAmountTC.getDisbursementDate();

                  String dfinalThinClient = dAmountTC.getFinalDisbursement();

                  Log.log(5, "GMProcessor", "uploadGmApplication", "DisDtls-cgpanTC:" + cgpanThinClient);

                  PeriodicInfo periodicInfoGM2 = (PeriodicInfo)disbDtlsGM.get(0);
                  Log.log(5, "GMProcessor", "uploadGmApplication", "periodicInfoGM2:" + periodicInfoGM2);

                  ArrayList disDtlsGM = periodicInfoGM2.getDisbursementDetails();

                  int disDtlsGMSize = disDtlsGM.size();
                  Log.log(5, "GMProcessor", "uploadGmApplication", "disDtlsGMSize:" + disDtlsGMSize);

                  if (disDtlsGMSize != 0)
                  {
                    Log.log(5, "GMProcessor", "uploadGmApplication", " disbdtls in DB not null ");
                    for (int k = 0; k < disDtlsGMSize; k++)
                    {
                      Disbursement disDetailGM = (Disbursement)disDtlsGM.get(k);
                      String cgpanGM = disDetailGM.getCgpan();

                      String schemeGM = disDetailGM.getScheme();

                      ArrayList disDtlsArrayGM = disDetailGM.getDisbursementAmounts();

                      int disDtlsArrayGMSize = disDtlsArrayGM.size();

                      if (cgpanGM.equals(cgpanThinClient))
                      {
                        if (disDtlsArrayGM != null)
                        {
                          int p = disDtlsArrayGM.size();
                          if (p == 0)
                          {
                            DisbursementAmount dAmountInsert = new DisbursementAmount();
                            dAmountInsert.setCgpan(cgpanThinClient);
                            dAmountInsert.setDisbursementAmount(dAmountThinClient);
                            dAmountInsert.setDisbursementDate(disDateThinClient);
                            dAmountInsert.setFinalDisbursement(dfinalThinClient);
                            gmDAO.insertDisbursement(dAmountInsert, userId);
                          }
                          else if (disIdThinClient == null)
                          {
                            DisbursementAmount dAmountInsert = new DisbursementAmount();
                            dAmountInsert.setCgpan(cgpanThinClient);
                            dAmountInsert.setDisbursementAmount(dAmountThinClient);
                            dAmountInsert.setDisbursementDate(disDateThinClient);
                            dAmountInsert.setFinalDisbursement(dfinalThinClient);
                            gmDAO.insertDisbursement(dAmountInsert, userId);
                          }
                          else
                          {
                            for (int q = 0; q < p; q++)
                            {
                              DisbursementAmount dAmountGMDao = (DisbursementAmount)disDtlsArrayGM.get(q);
                              String disIdGM = dAmountGMDao.getDisbursementId();

                              double dAmountGM = dAmountGMDao.getDisbursementAmount();

                              Date disDateGM = dAmountGMDao.getDisbursementDate();

                              String dfinalGM = dAmountGMDao.getFinalDisbursement();

                              if (disIdGM.equals(disIdThinClient))
                              {
                                DisbursementAmount dAmountUpdate = new DisbursementAmount();
                                dAmountUpdate.setCgpan(cgpanThinClient);
                                dAmountUpdate.setDisbursementAmount(dAmountThinClient);
                                dAmountUpdate.setDisbursementDate(disDateThinClient);
                                dAmountUpdate.setDisbursementId(disIdThinClient);
                                dAmountUpdate.setFinalDisbursement(dfinalThinClient);
                                gmDAO.updateDisbursement(dAmountUpdate, userId);
                              }

                            }

                          }

                        }
                        else
                        {
                          DisbursementAmount dAmountInsert = new DisbursementAmount();
                          dAmountInsert.setCgpan(cgpanThinClient);
                          dAmountInsert.setDisbursementAmount(dAmountThinClient);
                          dAmountInsert.setDisbursementDate(disDateThinClient);
                          dAmountInsert.setFinalDisbursement(dfinalThinClient);
                          gmDAO.insertDisbursement(dAmountInsert, userId);
                        }
                      }
                    }

                  }
                  else
                  {
                    Log.log(5, "GMProcessor", "uploadGmApplication", "Disbursement dtls not in DB, hence Insert");
                    if ((dAmountThinClient != 0) && (disDateThinClient != null))
                    {
                      DisbursementAmount dAmountInsert = new DisbursementAmount();
                      dAmountInsert.setCgpan(cgpanThinClient);
                      dAmountInsert.setDisbursementAmount(dAmountThinClient);
                      dAmountInsert.setDisbursementDate(disDateThinClient);
                      dAmountInsert.setFinalDisbursement(dfinalThinClient);
                      gmDAO.insertDisbursement(dAmountInsert, userId);
                    }
                  }
                }
              }

            }

          }

          ArrayList repaymentDtlsTC = periodicInfo.getRepaymentDetails();
          ArrayList repayDtlsGM = gmDAO.getRepaymentDetails(borrowerIdKey, 0);

          if (repaymentDtlsTC != null)
          {
            int repaymentDtlsTCSize = repaymentDtlsTC.size();

            Log.log(5, "GMProcessor", "uploadGmApplication", "RepayDtls-repaymentDtlsTCSize:" + repaymentDtlsTCSize);
            for (int k = 0; k < repaymentDtlsTCSize; k++)
            {
              Repayment repaymentDetailThinClient = (Repayment)repaymentDtlsTC.get(k);

              String cgpanTC = repaymentDetailThinClient.getCgpan();

              String schemeTC = repaymentDetailThinClient.getScheme();

              ArrayList repayAmountsTC = repaymentDetailThinClient.getRepaymentAmounts();
              if (repayAmountsTC != null)
              {
                int repaymentAmountsSize = repayAmountsTC.size();

                for (int l = 0; l < repaymentAmountsSize; l++)
                {
                  RepaymentAmount repayAmountThinClient = (RepaymentAmount)repayAmountsTC.get(l);
                  String repayIdTC = repayAmountThinClient.getRepayId();

                  double repayAmtTC = repayAmountThinClient.getRepaymentAmount();

                  Date repayDateTC = repayAmountThinClient.getRepaymentDate();

                  PeriodicInfo periodicInfoGM2 = (PeriodicInfo)repayDtlsGM.get(0);

                  ArrayList repaymentDtlsGM = periodicInfoGM2.getRepaymentDetails();

                  int repaymentDtlsGMSize = repaymentDtlsGM.size();

                  if (repaymentDtlsGMSize != 0)
                  {
                    for (int m = 0; m < repaymentDtlsGMSize; m++)
                    {
                      Repayment repayDetailGM = (Repayment)repaymentDtlsGM.get(m);
                      String cgpanGM = repayDetailGM.getCgpan();

                      String schemeGM = repayDetailGM.getScheme();

                      ArrayList repayAmountsGM = repayDetailGM.getRepaymentAmounts();

                      if (cgpanGM.equals(cgpanTC))
                      {
                        if (repayAmountsGM != null)
                        {
                          int x = repayAmountsGM.size();
                          if (x == 0)
                          {
                            RepaymentAmount repayAmountInsert = new RepaymentAmount();
                            repayAmountInsert.setCgpan(cgpanTC);
                            repayAmountInsert.setRepaymentAmount(repayAmtTC);
                            repayAmountInsert.setRepaymentDate(repayDateTC);
                            gmDAO.insertRepaymentDetails(repayAmountInsert, userId);
                          }
                          else if (repayIdTC == null)
                          {
                            RepaymentAmount repayAmountInsert = new RepaymentAmount();
                            repayAmountInsert.setCgpan(cgpanTC);
                            repayAmountInsert.setRepaymentAmount(repayAmtTC);
                            repayAmountInsert.setRepaymentDate(repayDateTC);
                            gmDAO.insertRepaymentDetails(repayAmountInsert, userId);
                          }
                          else
                          {
                            for (int y = 0; y < x; y++)
                            {
                              RepaymentAmount RepayAmountGM = (RepaymentAmount)repayAmountsGM.get(y);
                              String repayIdGM = RepayAmountGM.getRepayId();

                              double repayAmtGM = RepayAmountGM.getRepaymentAmount();

                              Date repayDateGM = RepayAmountGM.getRepaymentDate();

                              if (repayIdGM.equals(repayIdTC))
                              {
                                RepaymentAmount repayAmountUpdate = new RepaymentAmount();
                                repayAmountUpdate.setRepayId(repayIdTC);
                                repayAmountUpdate.setCgpan(cgpanTC);
                                repayAmountUpdate.setRepaymentAmount(repayAmtTC);
                                repayAmountUpdate.setRepaymentDate(repayDateTC);
                                gmDAO.updateRepaymentDetails(repayAmountUpdate, userId);
                              }

                            }

                          }

                        }
                        else
                        {
                          RepaymentAmount repayAmountInsert = new RepaymentAmount();
                          repayAmountInsert.setCgpan(cgpanTC);
                          repayAmountInsert.setRepaymentAmount(repayAmtTC);
                          repayAmountInsert.setRepaymentDate(repayDateTC);
                          gmDAO.insertRepaymentDetails(repayAmountInsert, userId);
                        }
                      }

                    }

                  }
                  else if ((repayAmtTC != 0) || (repayDateTC != null))
                  {
                    RepaymentAmount repayAmountInsert = new RepaymentAmount();
                    repayAmountInsert.setCgpan(cgpanTC);
                    repayAmountInsert.setRepaymentAmount(repayAmtTC);
                    repayAmountInsert.setRepaymentDate(repayDateTC);
                    gmDAO.insertRepaymentDetails(repayAmountInsert, userId);
                  }
                }

              }

            }

          }

          ArrayList RecoveryDetailsTC = periodicInfo.getRecoveryDetails();
          int RecoveryDetailsTCSize = RecoveryDetailsTC.size();

          if ((RecoveryDetailsTC != null) || (RecoveryDetailsTCSize != 0))
          {
            ArrayList recoveryDtlsGM = gmDAO.getRecoveryDetails(borrowerIdKey);
            int RecoveryDetailsGMSize = recoveryDtlsGM.size();

            for (int p = 0; p < RecoveryDetailsTCSize; p++)
            {
              Recovery recoveryObjTC = (Recovery)RecoveryDetailsTC.get(p);
              if ((recoveryObjTC != null) && (recoveryObjTC.getAmountRecovered() != 0.0D))
              {
                double amountRecovered = recoveryObjTC.getAmountRecovered();
                String cgbid = borrowerIdKey;

                String cgpan = recoveryObjTC.getCgpan();
                Date dateOfRecovery = recoveryObjTC.getDateOfRecovery();

                String detailsOfAssetSold = recoveryObjTC.getDetailsOfAssetSold();
                String isRecoveryByOTS = recoveryObjTC.getIsRecoveryByOTS();
                String isRecoveryBySaleOfAsset = recoveryObjTC.getIsRecoveryBySaleOfAsset();
                double legalCharges = recoveryObjTC.getLegalCharges();
                String recoveryNo = recoveryObjTC.getRecoveryNo();
                String remarks = recoveryObjTC.getRemarks();

                if ((recoveryNo == null) || (recoveryNo.equals("")))
                {
                  Recovery recoveryInsert = new Recovery();
                  recoveryInsert.setCgbid(cgbid);
                  recoveryInsert.setAmountRecovered(amountRecovered);
                  recoveryInsert.setDateOfRecovery(dateOfRecovery);

                  recoveryInsert.setDetailsOfAssetSold(detailsOfAssetSold);
                  recoveryInsert.setIsRecoveryByOTS(isRecoveryByOTS);
                  recoveryInsert.setIsRecoveryBySaleOfAsset(isRecoveryBySaleOfAsset);
                  recoveryInsert.setLegalCharges(legalCharges);
                  recoveryInsert.setRecoveryNo(recoveryNo);
                  recoveryInsert.setRemarks(remarks);
                  gmDAO.updateRecoveryDetails(recoveryInsert);
                }
                else
                {
                  for (int q = 0; q < RecoveryDetailsGMSize; q++)
                  {
                    Recovery recoveryGM = (Recovery)recoveryDtlsGM.get(q);
                    String recoveryNoGM = recoveryGM.getRecoveryNo();

                    if (recoveryNoGM.equals(recoveryNo))
                    {
                      Recovery recoveryUpdate = new Recovery();
                      recoveryUpdate.setCgbid(cgbid);
                      recoveryUpdate.setAmountRecovered(amountRecovered);
                      recoveryUpdate.setDateOfRecovery(dateOfRecovery);

                      recoveryUpdate.setDetailsOfAssetSold(detailsOfAssetSold);
                      recoveryUpdate.setIsRecoveryByOTS(isRecoveryByOTS);
                      recoveryUpdate.setIsRecoveryBySaleOfAsset(isRecoveryBySaleOfAsset);
                      recoveryUpdate.setLegalCharges(legalCharges);
                      recoveryUpdate.setRecoveryNo(recoveryNo);
                      recoveryUpdate.setRemarks(remarks);
                      gmDAO.modifyRecoveryDetails(recoveryUpdate);
                    }
                  }
                }

              }

            }

          }

          ArrayList osDtlsThinClient = periodicInfo.getOutstandingDetails();

          ArrayList prDtlsGM = gmDAO.getOutstandingDetails(borrowerIdKey, 0);

          if (osDtlsThinClient != null)
          {
            int osDtlsSize = osDtlsThinClient.size();
            for (int a = 0; a < osDtlsSize; a++)
            {
              OutstandingDetail osDetailThinClient = (OutstandingDetail)osDtlsThinClient.get(a);

              String cgpanThinClient = osDetailThinClient.getCgpan();

              String schemeThinClient = osDetailThinClient.getScheme();

              ArrayList osAmountsThinClient = osDetailThinClient.getOutstandingAmounts();
              if (osAmountsThinClient != null)
              {
                int osAmountsThinClientSize = osAmountsThinClient.size();

                for (int b = 0; b < osAmountsThinClientSize; b++)
                {
                  OutstandingAmount osAmountThinClient = (OutstandingAmount)osAmountsThinClient.get(b);

                  String tcIdThinClient = osAmountThinClient.getTcoId();

                  double tcPrincipalThinClient = osAmountThinClient.getTcPrincipalOutstandingAmount();

                  Date tcDateThinClient = osAmountThinClient.getTcOutstandingAsOnDate();

                  String wcIdThinClient = osAmountThinClient.getWcoId();

                  double wcPrincipalThinClient = osAmountThinClient.getWcFBPrincipalOutstandingAmount();

                  double wcInterestThinClient = osAmountThinClient.getWcFBInterestOutstandingAmount();

                  Date wcDateThinClient = osAmountThinClient.getWcFBOutstandingAsOnDate();

                  double wcNFBPrincipalThinClient = osAmountThinClient.getWcNFBPrincipalOutstandingAmount();
                  Log.log(4, "GMProcessor", "uploadGmApplication", "OSDetails-wcInterestThinClient*:" + wcNFBPrincipalThinClient);
                  double wcNFBInterestThinClient = osAmountThinClient.getWcNFBInterestOutstandingAmount();
                  Log.log(4, "GMProcessor", "uploadGmApplication", "OSDetails-wcInterestThinClient*:" + wcNFBInterestThinClient);
                  Date wcNFBDateThinClient = osAmountThinClient.getWcNFBOutstandingAsOnDate();
                  Log.log(4, "GMProcessor", "uploadGmApplication", "OSDetails-wcInterestThinClient*:" + wcNFBDateThinClient);

                  PeriodicInfo periodicInfoGM1 = (PeriodicInfo)prDtlsGM.get(0);
                  ArrayList osDtlsGM = periodicInfoGM1.getOutstandingDetails();
                  int osDtlsGMSize = osDtlsGM.size();

                  if (osDtlsGMSize != 0)
                  {
                    Log.log(4, "GMProcessor", "uploadGmApplication", "here 1");
                    int countCgpan = 1;
                    for (int c = 0; c < osDtlsGMSize; c++)
                    {
                      Log.log(4, "GMProcessor", "uploadGmApplication", "here 2");

                      OutstandingDetail osDetailGM = (OutstandingDetail)osDtlsGM.get(c);

                      String cgpanGM = osDetailGM.getCgpan();

                      String schemeGM = osDetailGM.getScheme();

                      ArrayList osAmountsGM = osDetailGM.getOutstandingAmounts();

                      if (cgpanGM.equals(cgpanThinClient))
                      {
                        Log.log(4, "GMProcessor", "uploadGmApplication", "here 3");

                        int cgpanLength = cgpanThinClient.length();
                        int type = cgpanLength - 2;
                        int type1 = cgpanLength - 1;
                        String cgpanType = cgpanThinClient.substring(type, type1);

                        if (cgpanType.equalsIgnoreCase("t"))
                        {
                          Log.log(4, "GMProcessor", "uploadGmApplication", "here 4");

                          if (osAmountsGM != null)
                          {
                            Log.log(4, "GMProcessor", "uploadGmApplication", "here 5");

                            int x = osAmountsGM.size();
                            if (x == 0)
                            {
                              Log.log(4, "GMProcessor", "uploadGmApplication", "here 6");

                              OutstandingAmount osAmountThinClientInsert = new OutstandingAmount();
                              osAmountThinClientInsert.setCgpan(cgpanThinClient);

                              osAmountThinClientInsert.setTcoId(tcIdThinClient);

                              osAmountThinClientInsert.setTcPrincipalOutstandingAmount(tcPrincipalThinClient);

                              osAmountThinClientInsert.setTcOutstandingAsOnDate(tcDateThinClient);

                              gmDAO.insertTcOutstandingDetails(osAmountThinClientInsert);

                              if (tcPrincipalThinClient == 0)
                              {
                                cgpansToBeClosed.add(cgpanThinClient);
                              }

                            }
                            else if (tcIdThinClient == null)
                            {
                              Log.log(4, "GMProcessor", "uploadGmApplication", "here 7");

                              OutstandingAmount osAmountThinClientInsert = new OutstandingAmount();
                              osAmountThinClientInsert.setCgpan(cgpanThinClient);

                              osAmountThinClientInsert.setTcoId(tcIdThinClient);

                              osAmountThinClientInsert.setTcPrincipalOutstandingAmount(tcPrincipalThinClient);

                              osAmountThinClientInsert.setTcOutstandingAsOnDate(tcDateThinClient);

                              gmDAO.insertTcOutstandingDetails(osAmountThinClientInsert);

                              if (tcPrincipalThinClient == 0)
                              {
                                cgpansToBeClosed.add(cgpanThinClient);
                              }
                            }
                            else
                            {
                              Log.log(4, "GMProcessor", "uploadGmApplication", "here 8");
                              for (int y = 0; y < x; y++)
                              {
                                Log.log(4, "GMProcessor", "uploadGmApplication", "here 9");
                                OutstandingAmount OsAmountGM = (OutstandingAmount)osAmountsGM.get(y);

                                String tcIdGM = OsAmountGM.getTcoId();

                                double tcPrincipalGM = OsAmountGM.getTcPrincipalOutstandingAmount();

                                Date tcDateGM = OsAmountGM.getTcOutstandingAsOnDate();

                                String wcIdGM = OsAmountGM.getWcoId();

                                double wcPrincipalGM = OsAmountGM.getWcFBPrincipalOutstandingAmount();

                                double wcInterestGM = OsAmountGM.getWcFBInterestOutstandingAmount();

                                Date wcDateGM = OsAmountGM.getWcFBOutstandingAsOnDate();

                                if (tcIdGM.equals(tcIdThinClient))
                                {
                                  if ((tcPrincipalThinClient != tcPrincipalGM) || (tcDateThinClient.compareTo(tcDateGM) != 0))
                                  {
                                    Log.log(4, "GMProcessor", "uploadGmApplication", "TC - Updated");
                                    OutstandingAmount osAmountThinClientUpdate = new OutstandingAmount();
                                    osAmountThinClientUpdate.setCgpan(cgpanThinClient);

                                    osAmountThinClientUpdate.setTcoId(tcIdThinClient);

                                    osAmountThinClientUpdate.setTcPrincipalOutstandingAmount(tcPrincipalThinClient);

                                    osAmountThinClientUpdate.setTcOutstandingAsOnDate(tcDateThinClient);

                                    gmDAO.updateTcOutstandingDetails(osAmountThinClientUpdate);

                                    if (tcPrincipalThinClient == 0)
                                    {
                                      cgpansToBeClosed.add(cgpanThinClient);
                                    }
                                  }
                                }

                              }

                            }

                          }
                          else
                          {
                            OutstandingAmount osAmountThinClientInsert = new OutstandingAmount();
                            osAmountThinClientInsert.setCgpan(cgpanThinClient);

                            osAmountThinClientInsert.setTcoId(tcIdThinClient);

                            osAmountThinClientInsert.setTcPrincipalOutstandingAmount(tcPrincipalThinClient);

                            osAmountThinClientInsert.setTcOutstandingAsOnDate(tcDateThinClient);

                            gmDAO.insertTcOutstandingDetails(osAmountThinClientInsert);

                            if (tcPrincipalThinClient == 0)
                            {
                              cgpansToBeClosed.add(cgpanThinClient);
                            }
                          }

                        }
                        else
                        {
                          Log.log(4, "GMProcessor", "uploadGmApplication", "here 10");
                          if (osAmountsGM != null)
                          {
                            int x = osAmountsGM.size();
                            if (x == 0)
                            {
                              Log.log(4, "GMProcessor", "uploadGmApplication", "here 11");

                              Log.log(4, "GMProcessor", "uploadGmApplication", "OSDetails-WC Inserted");
                              OutstandingAmount osAmountThinClientInsert = new OutstandingAmount();
                              osAmountThinClientInsert.setCgpan(cgpanThinClient);

                              osAmountThinClientInsert.setWcoId(wcIdThinClient);

                              osAmountThinClientInsert.setWcFBOutstandingAsOnDate(wcDateThinClient);

                              osAmountThinClientInsert.setWcFBPrincipalOutstandingAmount(wcPrincipalThinClient);

                              osAmountThinClientInsert.setWcFBInterestOutstandingAmount(wcInterestThinClient);
                              osAmountThinClientInsert.setWcNFBOutstandingAsOnDate(wcNFBDateThinClient);
                              osAmountThinClientInsert.setWcNFBPrincipalOutstandingAmount(wcNFBPrincipalThinClient);
                              osAmountThinClientInsert.setWcNFBInterestOutstandingAmount(wcNFBInterestThinClient);

                              gmDAO.insertWcOutstandingDetails(osAmountThinClientInsert);
                            }
                            else if (wcIdThinClient == null)
                            {
                              Log.log(4, "GMProcessor", "uploadGmApplication", "here 12");

                              Log.log(4, "GMProcessor", "uploadGmApplication", "OSDetails-WC Inserted");
                              OutstandingAmount osAmountThinClientInsert = new OutstandingAmount();
                              osAmountThinClientInsert.setCgpan(cgpanThinClient);

                              osAmountThinClientInsert.setWcoId(wcIdThinClient);

                              osAmountThinClientInsert.setWcFBOutstandingAsOnDate(wcDateThinClient);

                              osAmountThinClientInsert.setWcFBPrincipalOutstandingAmount(wcPrincipalThinClient);

                              osAmountThinClientInsert.setWcFBInterestOutstandingAmount(wcInterestThinClient);

                              osAmountThinClientInsert.setWcNFBOutstandingAsOnDate(wcNFBDateThinClient);
                              osAmountThinClientInsert.setWcNFBPrincipalOutstandingAmount(wcNFBPrincipalThinClient);
                              osAmountThinClientInsert.setWcNFBInterestOutstandingAmount(wcNFBInterestThinClient);
                              gmDAO.insertWcOutstandingDetails(osAmountThinClientInsert);
                            }
                            else
                            {
                              Log.log(4, "GMProcessor", "uploadGmApplication", "here 13");
                              for (int y = 0; y < x; y++)
                              {
                                Log.log(4, "GMProcessor", "uploadGmApplication", "here 14");
                                OutstandingAmount OsAmountGM = (OutstandingAmount)osAmountsGM.get(y);

                                String tcIdGM = OsAmountGM.getTcoId();

                                double tcPrincipalGM = OsAmountGM.getTcPrincipalOutstandingAmount();

                                Date tcDateGM = OsAmountGM.getTcOutstandingAsOnDate();

                                String wcIdGM = OsAmountGM.getWcoId();

                                double wcPrincipalGM = OsAmountGM.getWcFBPrincipalOutstandingAmount();

                                double wcInterestGM = OsAmountGM.getWcFBInterestOutstandingAmount();

                                Date wcDateGM = OsAmountGM.getWcFBOutstandingAsOnDate();
                                double wcNFBPrincipalGM = OsAmountGM.getWcNFBPrincipalOutstandingAmount();

                                double wcNFBInterestGM = OsAmountGM.getWcNFBInterestOutstandingAmount();

                                Date wcNFBDateGM = OsAmountGM.getWcNFBOutstandingAsOnDate();
                                if (wcIdGM.equals(wcIdThinClient))
                                {
                                  Log.log(4, "GMProcessor", "uploadGmApplication", "here 15");
                                  if ((wcPrincipalThinClient != wcPrincipalGM) || (wcInterestThinClient != wcInterestGM) || (wcDateThinClient.compareTo(wcDateGM) != 0))
                                  {
                                    Log.log(4, "GMProcessor", "uploadGmApplication", "here 16");

                                    Log.log(4, "GMProcessor", "uploadGmApplication", "OSDetails-WC Updated");
                                    OutstandingAmount osAmountThinClientUpdate = new OutstandingAmount();
                                    osAmountThinClientUpdate.setCgpan(cgpanThinClient);

                                    osAmountThinClientUpdate.setWcoId(wcIdThinClient);

                                    osAmountThinClientUpdate.setWcFBOutstandingAsOnDate(wcDateThinClient);

                                    osAmountThinClientUpdate.setWcFBPrincipalOutstandingAmount(wcPrincipalThinClient);

                                    osAmountThinClientUpdate.setWcFBInterestOutstandingAmount(wcInterestThinClient);

                                    Log.log(4, "GMProcessor", "uploadGmApplication", "OSDetails-WC Updated" + wcNFBDateThinClient);
                                    osAmountThinClientUpdate.setWcNFBOutstandingAsOnDate(wcNFBDateThinClient);
                                    Log.log(4, "GMProcessor", "uploadGmApplication", "OSDetails-WC Updated" + wcNFBPrincipalThinClient);
                                    osAmountThinClientUpdate.setWcNFBPrincipalOutstandingAmount(wcNFBPrincipalThinClient);
                                    Log.log(4, "GMProcessor", "uploadGmApplication", "OSDetails-WC Updated" + wcNFBInterestThinClient);
                                    osAmountThinClientUpdate.setWcNFBInterestOutstandingAmount(wcNFBInterestThinClient);
                                    gmDAO.updateWcOutstandingDetails(osAmountThinClientUpdate);
                                  }
                                }
                              }

                            }

                          }
                          else
                          {
                            Log.log(4, "GMProcessor", "uploadGmApplication", "here 17");

                            Log.log(4, "GMProcessor", "uploadGmApplication", "OSDetails-WC Inserted");
                            OutstandingAmount osAmountThinClientInsert = new OutstandingAmount();
                            osAmountThinClientInsert.setCgpan(cgpanThinClient);

                            osAmountThinClientInsert.setWcoId(wcIdThinClient);

                            osAmountThinClientInsert.setWcFBOutstandingAsOnDate(wcDateThinClient);

                            osAmountThinClientInsert.setWcFBPrincipalOutstandingAmount(wcPrincipalThinClient);

                            osAmountThinClientInsert.setWcFBInterestOutstandingAmount(wcInterestThinClient);

                            osAmountThinClientInsert.setWcNFBOutstandingAsOnDate(wcNFBDateThinClient);
                            osAmountThinClientInsert.setWcNFBPrincipalOutstandingAmount(wcNFBPrincipalThinClient);
                            osAmountThinClientInsert.setWcNFBInterestOutstandingAmount(wcNFBInterestThinClient);
                            gmDAO.insertWcOutstandingDetails(osAmountThinClientInsert);
                          }
                        }
                      }

                    }

                  }
                  else
                  {
                    int cgpanLength = cgpanThinClient.length();
                    int type = cgpanLength - 2;
                    int type1 = cgpanLength - 1;
                    String cgpanType = cgpanThinClient.substring(type, type1);

                    if (cgpanType.equalsIgnoreCase("t"))
                    {
                      if ((tcPrincipalThinClient != 0) || (tcDateThinClient != null))
                      {
                        OutstandingAmount osAmountThinClientInsert = new OutstandingAmount();
                        osAmountThinClientInsert.setCgpan(cgpanThinClient);
                        osAmountThinClientInsert.setTcPrincipalOutstandingAmount(tcPrincipalThinClient);
                        osAmountThinClientInsert.setTcOutstandingAsOnDate(tcDateThinClient);
                        gmDAO.insertTcOutstandingDetails(osAmountThinClientInsert);
                      }

                    }
                    else if ((wcPrincipalThinClient != 0) || (wcDateThinClient != null))
                    {
                      OutstandingAmount osAmountThinClientInsert = new OutstandingAmount();
                      osAmountThinClientInsert.setCgpan(cgpanThinClient);
                      osAmountThinClientInsert.setWcFBOutstandingAsOnDate(wcDateThinClient);
                      osAmountThinClientInsert.setWcFBPrincipalOutstandingAmount(wcPrincipalThinClient);
                      osAmountThinClientInsert.setWcFBInterestOutstandingAmount(wcInterestThinClient);
                      osAmountThinClientInsert.setWcNFBOutstandingAsOnDate(wcNFBDateThinClient);
                      osAmountThinClientInsert.setWcNFBPrincipalOutstandingAmount(wcNFBPrincipalThinClient);
                      osAmountThinClientInsert.setWcNFBInterestOutstandingAmount(wcNFBInterestThinClient);
                      gmDAO.insertWcOutstandingDetails(osAmountThinClientInsert);
                    }
                  }
                }
              }

            }

          }

          for (int ctr = 0; ctr < cgpansToBeClosed.size(); ctr++)
          {
            String cgpan = (String)cgpansToBeClosed.get(ctr);
            try
            {
              String reason = "Tenore Expired";
              sendMailForClosure(cgpan, userId, fromId, reason);
            }
            catch (MailerException e)
            {
              String errorMessage = cgpan + " has been closed since Outstanding Amount was ZERO. But Sending E-mails for is failed.";
              errorMessages.add(errorMessage);
            }
            catch (DatabaseException e)
            {
              String errorMessage = cgpan + " has been closed since Outstanding Amount was ZERO. But Sending E-mails for is failed.";
              errorMessages.add(errorMessage);
            }
            closure(cgpan, "Tenor Expired", "Automatic Closure while Upload", userId);
          }
          cgpansToBeClosed = null;

          String successMessage = "Periodic Information Details for the Borrower :" + borrowerIdKey + "has been uploaded successfully";
          errorMessages.add(successMessage);
        }
        else
        {
          String errorDetails = "This Periodic Info Detail has not been verified. Hence cannot upload the applicaton";

          errorMessages.add(errorDetails);
        }

      }
      else
      {
        String errorBorrower = borrowerIdKey + " does not exist in the database";
        Log.log(5, "GMProcessor", "uploadGmApplication", "Error  " + errorBorrower);

        errorMessages.add(errorBorrower);
      }
    }
    Log.log(4, "GMProcessor", "uploadGmApplication", "Exited");

    return errorMessages;
  }

  public BorrowerDetails getBorrowerDetailsForBID(String memberId, String borrowerId)
    throws DatabaseException
  {
    GMDAO gmDAO = new GMDAO();
    BorrowerDetails borrowerDetails = gmDAO.getBorrowerDetailsForBID(memberId, borrowerId);

    return borrowerDetails;
  }

  public ArrayList getOutstandingsForBid(String borrowerId, String memberId) throws NoApplicationFoundException, DatabaseException
  {
    GMDAO gmDAO = new GMDAO();
    ArrayList cgpanList = new ArrayList();
    ArrayList returnOsDetails = new ArrayList();
    ApplicationProcessor apProcessor = new ApplicationProcessor();

    String bnkId = memberId.substring(0, 4);
    String zneId = memberId.substring(4, 8);
    String brnId = memberId.substring(8, 12);

    ArrayList retList = apProcessor.getDtlForBIDMem(borrowerId, bnkId, zneId, brnId);
    cgpanList = (ArrayList)retList.get(1);

    returnOsDetails = gmDAO.getOutstandingsForApproval(cgpanList);

    return returnOsDetails;
  }

  public ArrayList getDisbursementsForBid(String borrowerId, String memberId) throws NoApplicationFoundException, DatabaseException
  {
    GMDAO gmDAO = new GMDAO();
    ArrayList cgpanList = new ArrayList();
    ArrayList returnDisbursements = new ArrayList();
    ApplicationProcessor apProcessor = new ApplicationProcessor();

    String bnkId = memberId.substring(0, 4);
    String zneId = memberId.substring(4, 8);
    String brnId = memberId.substring(8, 12);

    ArrayList retList = apProcessor.getDtlForBIDMem(borrowerId, bnkId, zneId, brnId);
    cgpanList = (ArrayList)retList.get(1);

    returnDisbursements = gmDAO.getDisbursementsForApproval(cgpanList);

    return returnDisbursements;
  }

  public ArrayList getRepaymentsForBid(String borrowerId, String memberId) throws NoApplicationFoundException, DatabaseException
  {
    GMDAO gmDAO = new GMDAO();
    ArrayList cgpanList = new ArrayList();
    ArrayList returnRepayments = new ArrayList();
    ApplicationProcessor apProcessor = new ApplicationProcessor();

    String bnkId = memberId.substring(0, 4);
    String zneId = memberId.substring(4, 8);
    String brnId = memberId.substring(8, 12);

    ArrayList retList = apProcessor.getDtlForBIDMem(borrowerId, bnkId, zneId, brnId);
    cgpanList = (ArrayList)retList.get(1);

    returnRepayments = gmDAO.getRepaymentsForApproval(cgpanList);

    return returnRepayments;
  }

  public void approvePeriodicInfo(String memberId, String borrowerId)
    throws NoApplicationFoundException, DatabaseException
  {
    Log.log(4, "GMProcessor", "approvePeriodicInfo", "Entered");

    GMDAO gmDAO = new GMDAO();
    gmDAO.approvePeriodicInfo(memberId, borrowerId);
    Log.log(4, "GMProcessor", "approvePeriodicInfo", "Exited");
  }

  public ArrayList getNpaDetailsForApproval(String borrowerId)
    throws DatabaseException
  {
    Log.log(4, "GMProcessor", "getNpaDetailsForApproval", "Entered");

    GMDAO gmDAO = new GMDAO();
    ArrayList returnNpaDetails = gmDAO.getNpaDetailsForApproval(borrowerId);
    Log.log(4, "GMProcessor", "getNpaDetailsForApproval", "Exited");

    return returnNpaDetails;
  }

  public ArrayList getBorrowerDetailsForApproval(int ssiRefNo)
    throws DatabaseException
  {
    Log.log(4, "GMProcessor", "getBorrowerDetailsForApproval", "Entered");

    GMDAO gmDAO = new GMDAO();
    ArrayList returnBorrowerDetails = gmDAO.getBorrowerDetailsForApproval(ssiRefNo);
    Log.log(4, "GMProcessor", "getBorrowerDetailsForApproval", "Exited");

    return returnBorrowerDetails;
  }

  public void approveBorrowerDetails(String bID,String userid)
    throws DatabaseException
  {
    Log.log(4, "GMProcessor", "approveBorrowerDetails", "Entered");

    GMDAO gmDAO = new GMDAO();
    gmDAO.approveBorrowerDetails(bID,userid);
    Log.log(4, "GMProcessor", "approveBorrowerDetails", "Exited");
  }
  
  public void rejectBorrowerDetails(String bID,String userName)
  throws DatabaseException
{
  Log.log(4, "GMProcessor", "approveBorrowerDetails", "Entered");

  GMDAO gmDAO = new GMDAO();
  gmDAO.rejectBorrowerDetails(bID,userName);
  Log.log(4, "GMProcessor", "approveBorrowerDetails", "Exited");
}

  public ArrayList getCgpanMapping(String memberId) throws DatabaseException
  {
    Log.log(4, "GMProcessor", "approveBorrowerDetails", "Entered");

    GMDAO gmDAO = new GMDAO();

    Log.log(4, "GMProcessor", "approveBorrowerDetails", "Exited");

    return gmDAO.getCgpanMapping(memberId);
  }

  public ArrayList getRecoveryForApproval(String borrowerId)
    throws DatabaseException
  {
    Log.log(4, "GMProcessor", "getRecoveryForApproval", "Entered");

    GMDAO gmDAO = new GMDAO();
    ArrayList returnRecDetails = gmDAO.getRecoveryForApproval(borrowerId);
    Log.log(4, "GMProcessor", "getRecoveryForApproval", "Exited");

    return returnRecDetails;
  }

  public ArrayList getRepayScheduleForApproval(String borrowerId, String memberId)
    throws NoApplicationFoundException, DatabaseException
  {
    Log.log(4, "GMProcessor", "getRepayScheduleForApproval", "Entered");

    GMDAO gmDAO = new GMDAO();
    ArrayList cgpanList = new ArrayList();
    ArrayList returnRepaySchDetails = new ArrayList();
    ApplicationProcessor apProcessor = new ApplicationProcessor();

    String bnkId = memberId.substring(0, 4);
    String zneId = memberId.substring(4, 8);
    String brnId = memberId.substring(8, 12);

    ArrayList retList = apProcessor.getDtlForBIDMem(borrowerId, bnkId, zneId, brnId);
    cgpanList = (ArrayList)retList.get(1);

    returnRepaySchDetails = gmDAO.getRepayScheduleForApproval(cgpanList);

    Log.log(4, "GMProcessor", "getRepayScheduleForApproval", "Exited");

    return returnRepaySchDetails;
  }

  public ArrayList getBorrowerIdForBorrowerName(String borrowerName, String memberId)
    throws NoDataException, DatabaseException
  {
    GMDAO gmDAO = new GMDAO();
    ArrayList borrowerIds = null;

    borrowerIds = gmDAO.getBorrowerIdForBorrowerName(borrowerName, memberId);
    if (borrowerIds == null) {
      throw new NoDataException("No borrower id found for the borrower Name");
    }

    return borrowerIds;
  }

  public ArrayList viewRepaymentDetailsForReport(String id, int type)
    throws DatabaseException
  {
    Log.log(4, "GMProcessor", "viewRepaymentDetails", "Entered");

    GMDAO gmDAO = new GMDAO();
    ArrayList periodicInfos = null;

    periodicInfos = gmDAO.getRepaymentDetailsForReport(id, type);

    Log.log(4, "GMProcessor", "viewRepaymentDetails", "Exited");

    return periodicInfos;
  }

  public ArrayList viewDisbursementDetailsForReport(String id, int type)
    throws DatabaseException
  {
    Log.log(4, "GMProcessor", "viewDisbursementDetails", "Entered");
    GMDAO gmDAO = new GMDAO();
    ArrayList periodicInfoList = new ArrayList();

    periodicInfoList = gmDAO.getDisbursementDetailsForReport(id, type);

    Log.log(4, "GMProcessor", "viewDisbursementDetails", "Exited");

    return periodicInfoList;
  }

  public NPADetails getNPADetailsForReport(String borrowerId) throws DatabaseException
  {
    Log.log(4, "GMProcessor", "getNPADetails", "Entered");

    GMDAO gmDAO = new GMDAO();
    NPADetails npaDetails = null;

    Log.log(5, "GMProcessor", "getNPADetails", "Borrower Id : " + borrowerId);

    npaDetails = gmDAO.getNPADetailsForReport(borrowerId);
    if (npaDetails == null) {
      Log.log(5, "GMProcessor", "getNPADetails", "npaDetails==null");
    }

    Log.log(4, "GMProcessor", "getNPADetails", "Exited");

    return npaDetails;
  }

  public ArrayList getRecoveryDetailsForReport(String borrowerId)
    throws DatabaseException
  {
    Log.log(4, "GMProcessor", "getRecoveryDetails", "Entered");

    GMDAO gmDAO = new GMDAO();
    ArrayList recoveryDetails = null;

    recoveryDetails = gmDAO.getRecoveryDetailsForReport(borrowerId);

    Log.log(4, "GMProcessor", "getRecoveryDetails", "Exited");

    return recoveryDetails;
  }

  public ArrayList viewOutstandingDetailsForReport(String id, int type)
    throws NoUserFoundException, SQLException, DatabaseException
  {
    Log.log(4, "GMProcessor", "viewOutstandingDetails", "Entered");

    GMDAO gmDAO = new GMDAO();
    ArrayList periodicInfos = null;

    periodicInfos = gmDAO.getOutstandingDetailsForReport(id, type);

    if (periodicInfos == null) {
      throw new NoUserFoundException("User details not found");
    }

    Log.log(4, "GMProcessor", "viewOutstandingDetails", "Exited");

    return periodicInfos;
  }

  public TreeMap getBidsForApproval(String memberId)
    throws NoUserFoundException, SQLException, DatabaseException
  {
    Log.log(4, "GMProcessor", "getBidsForApproval", "Entered");

    GMDAO gmDAO = new GMDAO();
    TreeMap bidsList = gmDAO.getBidsForApproval(memberId);

    Log.log(4, "GMProcessor", "getBidsForApproval", "Exited");

    return bidsList;
  }

  public TreeMap getBidsForPerInfoApproval()
    throws NoUserFoundException, SQLException, DatabaseException
  {
    Log.log(4, "GMProcessor", "getBidsForApproval", "Entered");

    GMDAO gmDAO = new GMDAO();
    TreeMap bidsList = gmDAO.getBidsForPerInfoApproval();

    Log.log(4, "GMProcessor", "getBidsForApproval", "Exited");

    return bidsList;
  }

  //koteswar start

  public void updateNPADetailsnew(String userId,NPADetails npaDetails,Vector tcVector,Vector wcVector,Map securityMap)throws DatabaseException  {
                 Log.log(Log.INFO,"GMProcessor","updateNPADetails","Entered");
                 GMDAO  gmDAO = new GMDAO();
                 
                 gmDAO.updateNPADetailsnew(userId,npaDetails,tcVector,wcVector,securityMap);
                 Log.log(Log.INFO,"GMProcessor","updateNPADetails","Exited");    
    }

 
  
  //koteswar end
  
  public BorrowerDetails viewBorrowerDetailsForModifyBorrowerDetails(String memberId, String id, int type)
  throws NoDataException, DatabaseException
{
  Log.log(4, "GMProcessor", "viewBorrowerDetails", "Entered");
  GMDAO gmDAO = new GMDAO();

  BorrowerDetails borrowerDetails = null;

  borrowerDetails = gmDAO.getBorrowerDetailsForModifyBorrowerDetails(memberId, id, type);

  if (borrowerDetails == null) {
    throw new NoDataException("BorrowerDetails are not found");
  }

  Log.log(4, "GMProcessor", "viewBorrowerDetails", "Exited");

  return borrowerDetails;
}
  
  public void updateBorrowerDetailsNew(BorrowerDetails borrowerDetail, String userId)
  throws DatabaseException
{
 
  GMDAO gmDAO = new GMDAO();

  if (borrowerDetail == null) {
    return;
  }
  gmDAO.updateBorrowerDetailsNew(borrowerDetail, userId);


}

public Vector cgPanList(String cgPan) {
	GMDAO gmDAO = new GMDAO();
	return gmDAO.cgPanList(cgPan);	
}

public ArrayList<MLIInfo> getGSTStateAndNo(String cgPan) {
	GMDAO gmDAO = new GMDAO();
	return  gmDAO.getGSTStateAndNo(cgPan);
}

public ArrayList<MLIInfo> getGSTStateList(String bankId) {
	// TODO Auto-generated method stub
	GMDAO gmDAO = new GMDAO();
	
	return gmDAO.getGSTStateList(bankId);
}

public boolean updateGstNoInApplicationDetails(String gstNo, String gstState,ArrayList emptyGstCgpan) {
	// TODO Auto-generated method stub
	GMDAO gmDAO = new GMDAO();
	return gmDAO.updateGstNoInApplicationDetails(gstNo,gstState,emptyGstCgpan);
}


public ArrayList getEmptyGstCgpanAppRefList(String bankId,String zoneId,String branchId) {
	// TODO Auto-generated method stub
	GMDAO gmDAO = new GMDAO();	
	return gmDAO.getEmptyGstCgpanAppRefList(bankId,zoneId,branchId);
}  

}