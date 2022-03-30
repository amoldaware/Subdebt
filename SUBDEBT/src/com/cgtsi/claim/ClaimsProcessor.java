// FrontEnd Plus GUI for JAD
// DeCompiled : ClaimsProcessor.class

package com.cgtsi.claim;

import com.cgtsi.admin.Administrator;
import com.cgtsi.admin.ParameterMaster;
import com.cgtsi.application.ApplicationDAO;
import com.cgtsi.application.BorrowerDetails;
import com.cgtsi.application.NoApplicationFoundException;
import com.cgtsi.application.SSIDetails;
import com.cgtsi.application.TermLoan;
import com.cgtsi.common.DatabaseException;
import com.cgtsi.common.Log;
import com.cgtsi.common.MessageException;
import com.cgtsi.guaranteemaintenance.GMProcessor;
import com.cgtsi.investmentfund.ChequeDetails;
import com.cgtsi.receiptspayments.RpProcessor;
import com.cgtsi.registration.MLIInfo;
import com.cgtsi.util.DateHelper;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Hashtable;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;
import java.util.StringTokenizer;
import java.util.Vector;

// Referenced classes of package com.cgtsi.claim:
//            CPDAO, SettlementDetail, LegalProceedingsDetail, ClaimApplication, 
//            ClaimDetail, MemberInfo, BorrowerInfo, OTSRequestDetail, 
//            ClaimFiles

public class ClaimsProcessor
{

    private CPDAO cpDAO;

    public ClaimsProcessor()
    {
        cpDAO = new CPDAO();
    }

    public Hashtable isDisbursementDetailsAvl(String borrowerId)
        throws DatabaseException
    {
        return cpDAO.isDisbursementDetailsAvl(borrowerId);
    }

    public HashMap isRecoveryDetailsAvailable(String borrowerId)
        throws DatabaseException
    {
        return cpDAO.isRecoveryDetailsAvailable(borrowerId);
    }

    public Vector getLockInDetails(String borrowerid)
        throws DatabaseException
    {
        return cpDAO.getLockInDetails(borrowerid);
    }

    public Date getDate(Date date, int months)
    {
        Log.log(4, "ClaimsProcessor", "getDate()", "Entered");
        Calendar cal = Calendar.getInstance();
        cal.setTime(date);
        cal.add(2, months);
        Log.log(4, "ClaimsProcessor", "getDate()", "Exited");
        return cal.getTime();
    }

    public boolean isLockinPeriodOver(String borrowerid)
        throws DatabaseException
    {
        Log.log(4, "ClaimsProcessor", "isLockinPeriodOver()", "Entered");
        Vector lockindetails = getLockInDetails(borrowerid);
        String lckdtl_cgpan = null;
        Date lckdtl_gstartdate = null;
        Date lckdtl_dtlastdsbrsmnt = null;
        Date lckdtl_lockin_start_date = null;
        Date tempdate = null;
        if(lockindetails.size() == 0)
            return false;
        for(int i = 0; i < lockindetails.size(); i++)
        {
            HashMap temp = (HashMap)lockindetails.elementAt(i);
            lckdtl_cgpan = (String)temp.get("CGPAN");
            lckdtl_gstartdate = (Date)temp.get("GUARANTEESTARTDT");
            lckdtl_dtlastdsbrsmnt = (Date)temp.get("LASTDSBRSMNTDT");
            if(lckdtl_cgpan == null)
                continue;
            if(lckdtl_gstartdate != null && lckdtl_dtlastdsbrsmnt != null)
            {
                if(lckdtl_gstartdate.compareTo(lckdtl_dtlastdsbrsmnt) < 0)
                    tempdate = lckdtl_dtlastdsbrsmnt;
                if(lckdtl_gstartdate.compareTo(lckdtl_dtlastdsbrsmnt) > 0)
                    tempdate = lckdtl_gstartdate;
                if(lckdtl_gstartdate.compareTo(lckdtl_dtlastdsbrsmnt) == 0)
                    tempdate = lckdtl_gstartdate;
            } else
            {
                if(lckdtl_gstartdate == null || lckdtl_dtlastdsbrsmnt != null)
                    continue;
                tempdate = lckdtl_gstartdate;
            }
            if(i == 0)
            {
                lckdtl_lockin_start_date = tempdate;
                if(lckdtl_lockin_start_date == null)
                {
                    Log.log(4, "ClaimsProcessor", "isLockinPeriodOver()", "Exited - 1");
                    return false;
                }
            } else
            if(lckdtl_lockin_start_date != null)
            {
                if(lckdtl_lockin_start_date.compareTo(tempdate) < 0)
                    lckdtl_lockin_start_date = tempdate;
            } else
            {
                Log.log(4, "ClaimsProcessor", "isLockinPeriodOver()", "Exited - 2");
                return false;
            }
        }

        Administrator admin = new Administrator();
        ParameterMaster parameterMaster = admin.getParameter();
        if(parameterMaster == null)
        {
            Log.log(4, "ClaimsProcessor", "isLockinPeriodOver", "Could not load Parameter Master");
            throw new DatabaseException("Could not load Parameter Master!");
        }
        int lockinperiodmonths = parameterMaster.getLockInPeriod();
        if(lockinperiodmonths == -1)
            return true;
        Date lockinperiodenddate = getDate(lckdtl_lockin_start_date, lockinperiodmonths);
        Date currentDate = new Date();
        if(lockinperiodenddate.compareTo(currentDate) < 0)
        {
            Log.log(4, "ClaimsProcessor", "isLockinPeriodOver()", "Exited - 3");
            return true;
        } else
        {
            Log.log(4, "ClaimsProcessor", "isLockinPeriodOver()", "Exited - 4");
            return false;
        }
    }

    public Vector getAllRecoveryModes()
        throws DatabaseException
    {
        return cpDAO.getAllRecoveryModes();
    }

    public Vector getOTSRequestDetails(String borrowerId)
        throws DatabaseException
    {
        return cpDAO.getOTSRequestDetails(borrowerId);
    }

    public Vector getSettlementDetails(String bankId, String zoneId, String branchId, String flag, boolean anotherFlag)
        throws DatabaseException
    {
        RpProcessor rpprocessor = new RpProcessor();
        Date clmApprvdDate = null;
        Administrator admin = new Administrator();
        ParameterMaster pm = admin.getParameter();
        int clmSettWithoutPenaltyInt = pm.getClaimSettlementWithoutPenalty();
        Log.log(4, "ClaimsProcessor", "getAllBorrowerIDs()", "*******************************************");
        Log.log(4, "ClaimsProcessor", "getAllBorrowerIDs()", (new StringBuilder("clmSettWithoutPenaltyInt :")).append(clmSettWithoutPenaltyInt).toString());
        double clmSettWithoutPenalty = (new Integer(clmSettWithoutPenaltyInt)).doubleValue();
        Log.log(4, "ClaimsProcessor", "getAllBorrowerIDs()", (new StringBuilder("clmSettWithoutPenalty :")).append(clmSettWithoutPenalty).toString());
        double clmPenalRate = pm.getClaimPenaltyRate();
        Vector settDetails = cpDAO.getSettlementDetails(bankId, zoneId, branchId, flag, anotherFlag);
        Date currentDate = new Date();
        Date settDtWithoutPenalty = null;
        Calendar cal1 = Calendar.getInstance();
        Calendar cal2 = Calendar.getInstance();
        double penaltyAmnt = 0.0D;
        double clmApprvdAmount = 0.0D;
        HashMap pendingDtls = null;
        Vector pendingSFeeDtls = null;
        if(anotherFlag)
            try
            {
                pendingDtls = rpprocessor.getMLIWiseDANDetails((new StringBuilder(String.valueOf(bankId))).append(zoneId).append(branchId).toString());
            }
            catch(MessageException msgex)
            {
                Log.log(4, "ClaimsProcessor", "getSettlementDetails", (new StringBuilder("No Dan Details for :")).append(bankId).append(zoneId).append(branchId).toString());
            }
        if(pendingDtls != null)
            pendingSFeeDtls = (Vector)pendingDtls.get("pendingsfee_dtls");
        for(int i = 0; i < settDetails.size(); i++)
        {
            SettlementDetail settDtl = (SettlementDetail)settDetails.remove(i);
            if(settDtl != null)
            {
                String borrowerId = settDtl.getCgbid();
                double totalPendingReceivable = 0.0D;
                Vector cgpans = null;
                if(anotherFlag)
                {
                    cgpans = getCGPANSForBid(borrowerId);
                    if(cgpans != null)
                    {
                        for(int k = 0; k < cgpans.size(); k++)
                        {
                            String cgpan = (String)cgpans.elementAt(k);
                            Log.log(2, "ClaimsProcessor", "getSettlementDetails", (new StringBuilder("cgpan :")).append(cgpan).toString());
                            if(cgpan != null && pendingSFeeDtls != null)
                            {
                                Log.log(2, "ClaimsProcessor", "getSettlementDetails", "Control 1");
                                for(int j = 0; j < pendingSFeeDtls.size(); j++)
                                {
                                    Log.log(2, "ClaimsProcessor", "getSettlementDetails", "Control 2");
                                    HashMap dtl = (HashMap)pendingSFeeDtls.elementAt(j);
                                    if(dtl != null)
                                    {
                                        String pan = (String)dtl.get("CGPAN");
                                        Double amnt = (Double)dtl.get("RP_TOTAL_PENDING_AMNT");
                                        if(pan != null && pan.equals(cgpan))
                                        {
                                            Log.log(2, "ClaimsProcessor", "getSettlementDetails", "Control 3");
                                            if(amnt != null)
                                            {
                                                Log.log(2, "ClaimsProcessor", "getSettlementDetails", "Control 4");
                                                totalPendingReceivable += amnt.doubleValue();
                                            }
                                        }
                                    }
                                }

                            }
                        }

                    }
                }
                settDtl.setPendingFromMLI(totalPendingReceivable);
                clmApprvdDate = settDtl.getClmApprvdDate();
                clmApprvdAmount = settDtl.getApprovedClaimAmt();
                double clmSettWithoutPenaltyInDouble = Math.ceil(clmSettWithoutPenalty / 31D);
                Log.log(4, "ClaimsProcessor", "getAllBorrowerIDs()", (new StringBuilder("clmSettWithoutPenaltyInDouble :")).append(clmSettWithoutPenaltyInDouble).toString());
                Double clmSettWithoutPenaltyInDoubleObj = new Double(clmSettWithoutPenaltyInDouble);
                int clmSettWithoutPenaltyInMonths = clmSettWithoutPenaltyInDoubleObj.intValue();
                Log.log(4, "ClaimsProcessor", "getAllBorrowerIDs()", (new StringBuilder("clmSettWithoutPenaltyInMonths :")).append(clmSettWithoutPenaltyInMonths).toString());
                settDtWithoutPenalty = getDate(clmApprvdDate, clmSettWithoutPenaltyInMonths);
                if(currentDate.compareTo(settDtWithoutPenalty) <= 0)
                {
                    penaltyAmnt = 0.0D;
                    settDtl.setPenaltyAmnt(penaltyAmnt);
                }
                if(currentDate.compareTo(settDtWithoutPenalty) > 0)
                {
                    Log.log(4, "ClaimsProcessor", "getAllBorrowerIDs()", (new StringBuilder("settDtWithoutPenalty :")).append(settDtWithoutPenalty).toString());
                    cal1.setTime(settDtWithoutPenalty);
                    cal2.setTime(currentDate);
                    long diffOfMonths = DateHelper.getMonthDifference(cal1, cal2);
                    Log.log(4, "ClaimsProcessor", "getAllBorrowerIDs()", (new StringBuilder("diffOfMonths :")).append(diffOfMonths).toString());
                    Log.log(4, "ClaimsProcessor", "getAllBorrowerIDs()", "**********************************************");
                    penaltyAmnt = (clmApprvdAmount * (double)diffOfMonths * clmPenalRate) / 1200D;
                    settDtl.setPenaltyAmnt(penaltyAmnt);
                }
                settDetails.add(i, settDtl);
            }
        }

        return settDetails;
    }

    public Vector getAllMemberIds()
        throws DatabaseException
    {
        return cpDAO.getAllMemberIds();
    }

    public ArrayList getAllBorrowerIDs(String memberId)
        throws DatabaseException
    {
        Log.log(4, "ClaimsProcessor", "getAllBorrowerIDs()", "Entered");
        String bankId = null;
        String zoneId = null;
        String branchId = null;
        memberId = memberId.trim();
        bankId = memberId.substring(0, 4);
        zoneId = memberId.substring(4, 8);
        branchId = memberId.substring(8, 12);
        Log.log(4, "ClaimsProcessor", "getAllBorrowerIDs()", "Exited");
        return cpDAO.getAllBorrowerIDs(bankId, zoneId, branchId);
    }

    public MemberInfo getMemberInfoDetails(String memberId)
        throws DatabaseException
    {
        Log.log(4, "ClaimsProcessor", "getMemberInfoDetails()", "Entered");
        String bankId = null;
        String zoneId = null;
        String branchId = null;
        memberId = memberId.trim();
        bankId = memberId.substring(0, 4);
        zoneId = memberId.substring(4, 8);
        branchId = memberId.substring(8, 12);
        Log.log(4, "ClaimsProcessor", "getMemberInfoDetails()", "Exited");
        return cpDAO.getMemberInfoDetails(bankId, zoneId, branchId);
    }

    public BorrowerInfo getBorrowerDetails(String borrowerId)
        throws DatabaseException
    {
        return cpDAO.getBorrowerDetails(borrowerId);
    }

    public HashMap isNPADetailsAvailable(String cgbid)
        throws DatabaseException
    {
        return cpDAO.isNPADetailsAvailable(cgbid);
    }

    public LegalProceedingsDetail isLegalProceedingsDetailAvl(String borrowerId)
        throws DatabaseException
    {
        Log.log(4, "ClaimsProcessor", "isLegalProceedingsDetailAvl()", "Entered");
        LegalProceedingsDetail lpd = cpDAO.isLegalProceedingsDetailAvl(borrowerId);
        if(lpd.getForumRecoveryProceedingsInitiated() != null && lpd.getNameOfForum() != null && lpd.getLocation() != null && lpd.getFilingDate() != null)
            lpd.setAreLegalProceedingsDetailsAvl(true);
        else
            lpd.setAreLegalProceedingsDetailsAvl(false);
        Log.log(4, "ClaimsProcessor", "isLegalProceedingsDetailAvl()", "Exited");
        return lpd;
    }

    public Vector getCGPANDetailsForBorrowerId(String borrowerId, String memberId)
        throws DatabaseException
    {
        return cpDAO.getCGPANDetailsForBorrowerId(borrowerId, memberId);
    }

    public Vector getCGPANDetailsForBidClaim(String borrowerId, String memberId)
        throws DatabaseException
    {
        return cpDAO.getCGPANDetailsForBidClaim(borrowerId, memberId);
    }

    public String addClaimApplication(ClaimApplication claimApplication, HashMap map, boolean flag) throws DatabaseException
    {
        String claimRefNumber = null;
        Log.log(4, "ClaimsProcessor", "addClaimApplicationForFirstInstallment()", "Entered");
        String bid = claimApplication.getBorrowerId();
        claimRefNumber = cpDAO.generateClaimRefNumber(bid);
        Log.log(4, "ClaimsProcessor", "addClaimApplicationForFirstInstallment()", (new StringBuilder()).append("Claim Ref Number generated is :").append(claimRefNumber).toString());
        claimApplication.setClaimRefNumber(claimRefNumber);
        cpDAO.saveClaimApplication(claimApplication, map, flag);
        Log.log(4, "ClaimsProcessor", "addClaimApplicationForFirstInstallment()", "Exited");
        return claimRefNumber;
    }

    public String updateClaimApplication(ClaimApplication claimApplication, HashMap map, boolean flag)
        throws DatabaseException
    {
        String claimRefNumber = null;
        Log.log(4, "ClaimsProcessor", "updateClaimApplicationForFirstInstallment()", "Entered");
        claimRefNumber = claimApplication.getClaimRefNumber();
        claimApplication.setClaimRefNumber(claimRefNumber);
        cpDAO.updateClaimApplication(claimApplication, map, flag);
        Log.log(4, "ClaimsProcessor", "addClaimApplicationForFirstInstallment()", "Exited");
        return claimRefNumber;
    }

    public void saveOTSDetail(OTSRequestDetail otsDetail, String userId)
        throws DatabaseException
    {
        cpDAO.saveOTSDetail(otsDetail, userId);
    }

    public Vector getToBeApprovedOTSRequests()
        throws DatabaseException
    {
        return cpDAO.getToBeApprovedOTSRequests();
    }

    public Vector getClaimApprovalDetails(String loggedUsr)
        throws DatabaseException
    {
        return cpDAO.getClaimApprovalDetails(loggedUsr);
    }

    public Vector getClaimProcessingDetails(String flag)
        throws DatabaseException
    {
        Log.log(4, "ClaimsProcessor", "getClaimProcessingDetails()", "Entered");
        Vector clmProcessingDtls = cpDAO.getClaimProcessingDetails(flag);
        Vector recoveryDtls = null;
        HashMap totalRecDetails = null;
        String bankId = null;
        String zoneId = null;
        String branchId = null;
        String bid = null;
        ClaimDetail claimdetail = null;
        String clmRefNumber = null;
        double apprvdApplicationAmnt = 0.0D;
        double outstandingAmntAsOnNPA = 0.0D;
        double defaultAmnt = 0.0D;
        double firstInstllmntAmnt = 0.0D;
        double cgtsiLiability = 0.0D;
        double secondInstllmntAmnt = 0.0D;
        Date npadate = null;
        Date clmApprvdDt = null;
        double recoveredAmnt = 0.0D;
        double tempTC = 0.0D;
        double tempWC = 0.0D;
        double interestAmount = 0.0D;
        double clmApprvdAmnt = 0.0D;
        double appliedClaimAmt = 0.0D;
        Date recoveryDt = null;
        HashMap recoveryDtl = null;
        Administrator admin = new Administrator();
        ParameterMaster pm = admin.getParameter();
        double firstClmPercentage = pm.getFirstInstallClaim() / 100D;
        if(flag.equals("F"))
        {
            Log.log(4, "ClaimsProcessor", "getClaimProcessingDetails()", (new StringBuilder("clmProcessingDtls.size() :")).append(clmProcessingDtls.size()).toString());
            for(int i = 0; i < clmProcessingDtls.size(); i++)
            {
                claimdetail = (ClaimDetail)clmProcessingDtls.remove(i);
                clmRefNumber = claimdetail.getClaimRefNum();
                bid = claimdetail.getBorrowerId();
                if(bid != null)
                {
                    apprvdApplicationAmnt = claimdetail.getApplicationApprovedAmount();
                    outstandingAmntAsOnNPA = claimdetail.getOutstandingAmntAsOnNPADate();
                    npadate = claimdetail.getNpaDate();
                    appliedClaimAmt = claimdetail.getAppliedClaimAmt();
                    totalRecDetails = isRecoveryDetailsAvailable(clmRefNumber);
                    recoveryDtls = (Vector)totalRecDetails.get("TEMP");
                    if(recoveryDtls.equals("") || recoveryDtls == null)
                        if(outstandingAmntAsOnNPA < apprvdApplicationAmnt)
                            defaultAmnt = outstandingAmntAsOnNPA;
                        else
                        if(outstandingAmntAsOnNPA > apprvdApplicationAmnt)
                            defaultAmnt = apprvdApplicationAmnt;
                        else
                        if(outstandingAmntAsOnNPA == apprvdApplicationAmnt)
                            defaultAmnt = apprvdApplicationAmnt;
                    if(recoveryDtls != null)
                    {
                        for(int j = 0; j < recoveryDtls.size(); j++)
                        {
                            recoveryDtl = (HashMap)recoveryDtls.elementAt(j);
                            if(recoveryDtl != null)
                            {
                                tempTC = ((Double)recoveryDtl.get("TCPRINCIPAL")).doubleValue();
                                interestAmount = ((Double)recoveryDtl.get("TCINTEREST")).doubleValue();
                                tempWC = ((Double)recoveryDtl.get("WC_AMOUNT")).doubleValue();
                                recoveredAmnt = recoveredAmnt + tempTC + tempWC + interestAmount;
                            }
                        }

                        Log.log(4, "ClaimsProcessor", "getClaimProcessingDetails()", (new StringBuilder("Claim Ref Number :")).append(clmRefNumber).append("Oustanding Amount :").append(outstandingAmntAsOnNPA).toString());
                        Log.log(4, "ClaimsProcessor", "getClaimProcessingDetails()", (new StringBuilder("Claim Ref Number :")).append(clmRefNumber).append("Approved Application Amount :").append(apprvdApplicationAmnt).toString());
                        Log.log(4, "ClaimsProcessor", "getClaimProcessingDetails()", (new StringBuilder("Claim Ref Number :")).append(clmRefNumber).append("Claim Applied Amount :").append(appliedClaimAmt).toString());
                        Log.log(4, "ClaimsProcessor", "getClaimProcessingDetails()", (new StringBuilder("Claim Ref Number :")).append(clmRefNumber).append("Recovered Amount :").append(recoveredAmnt).toString());
                        Log.log(4, "ClaimsProcessor", "getClaimProcessingDetails()", (new StringBuilder("Claim Ref Number :")).append(clmRefNumber).append("Default Amount :").append(defaultAmnt).toString());
                        outstandingAmntAsOnNPA -= recoveredAmnt;
                        if(outstandingAmntAsOnNPA < apprvdApplicationAmnt)
                            defaultAmnt = outstandingAmntAsOnNPA;
                        else
                        if(outstandingAmntAsOnNPA > apprvdApplicationAmnt)
                            defaultAmnt = apprvdApplicationAmnt;
                        else
                        if(outstandingAmntAsOnNPA == apprvdApplicationAmnt)
                            defaultAmnt = apprvdApplicationAmnt;
                        Log.log(4, "ClaimsProcessor", "getClaimProcessingDetails()", (new StringBuilder("pm.getCgtsiLiability() :")).append(pm.getCgtsiLiability() / 100D).toString());
                        cgtsiLiability = (pm.getCgtsiLiability() / 100D) * defaultAmnt;
                        firstInstllmntAmnt = firstClmPercentage * cgtsiLiability;
                        firstInstllmntAmnt = Math.rint(firstInstllmntAmnt);
                        if(firstInstllmntAmnt < 0.0D)
                            firstInstllmntAmnt = 0.0D;
                        claimdetail.setEligibleClaimAmt(firstInstllmntAmnt);
                        clmProcessingDtls.add(i, claimdetail);
                    }
                    recoveredAmnt = 0.0D;
                }
            }

        } else
        if(flag.equals("S"))
        {
            for(int i = 0; i < clmProcessingDtls.size(); i++)
            {
                recoveredAmnt = 0.0D;
                tempTC = 0.0D;
                tempWC = 0.0D;
                clmApprvdAmnt = 0.0D;
                recoveryDt = null;
                claimdetail = (ClaimDetail)clmProcessingDtls.remove(i);
                clmRefNumber = claimdetail.getClaimRefNum();
                String memberId = claimdetail.getMliId();
                if(memberId != null && memberId.length() == 12)
                {
                    bankId = memberId.substring(0, 4);
                    zoneId = memberId.substring(4, 8);
                    branchId = memberId.substring(8, 12);
                    bid = claimdetail.getBorrowerId();
                    HashMap firstClmDtls = getFirstClmDtlForBid(bankId, zoneId, branchId, bid);
                    if(firstClmDtls != null)
                    {
                        Double clmApprvdAmntObj = (Double)firstClmDtls.get("CLMAPPRVDAMT");
                        if(clmApprvdAmntObj != null)
                            clmApprvdAmnt = clmApprvdAmntObj.doubleValue();
                        clmApprvdDt = (Date)firstClmDtls.get("CLMAPPRVDDT");
                    }
                    totalRecDetails = isRecoveryDetailsAvailable(clmRefNumber);
                    recoveryDtls = (Vector)totalRecDetails.get("TEMP");
                    apprvdApplicationAmnt = claimdetail.getApplicationApprovedAmount();
                    outstandingAmntAsOnNPA = claimdetail.getOutstandingAmntAsOnNPADate();
                    npadate = claimdetail.getNpaDate();
                    if(outstandingAmntAsOnNPA < apprvdApplicationAmnt)
                        defaultAmnt = outstandingAmntAsOnNPA;
                    else
                    if(outstandingAmntAsOnNPA > apprvdApplicationAmnt)
                        defaultAmnt = apprvdApplicationAmnt;
                    else
                    if(outstandingAmntAsOnNPA == apprvdApplicationAmnt)
                        defaultAmnt = outstandingAmntAsOnNPA;
                    for(int j = 0; j < recoveryDtls.size(); j++)
                    {
                        recoveryDtl = (HashMap)recoveryDtls.elementAt(j);
                        if(recoveryDtl != null)
                        {
                            tempTC = ((Double)recoveryDtl.get("TCPRINCIPAL")).doubleValue();
                            tempWC = ((Double)recoveryDtl.get("WC_AMOUNT")).doubleValue();
                            recoveredAmnt = recoveredAmnt + tempTC + tempWC;
                        }
                    }

                    Log.log(4, "ClaimsProcessor", "getClaimProcessingDetails()", (new StringBuilder("Claim Ref Number :")).append(clmRefNumber).append("Oustanding Amount :").append(outstandingAmntAsOnNPA).toString());
                    Log.log(4, "ClaimsProcessor", "getClaimProcessingDetails()", (new StringBuilder("Claim Ref Number :")).append(clmRefNumber).append("Approved Application Amount :").append(apprvdApplicationAmnt).toString());
                    Log.log(4, "ClaimsProcessor", "getClaimProcessingDetails()", (new StringBuilder("Claim Ref Number :")).append(clmRefNumber).append("Claim Applied Amount :").append(appliedClaimAmt).toString());
                    Log.log(4, "ClaimsProcessor", "getClaimProcessingDetails()", (new StringBuilder("Claim Ref Number :")).append(clmRefNumber).append("Recovered Amount :").append(recoveredAmnt).toString());
                    Log.log(4, "ClaimsProcessor", "getClaimProcessingDetails()", (new StringBuilder("Claim Ref Number :")).append(clmRefNumber).append("Default Amount :").append(defaultAmnt).toString());
                    defaultAmnt -= recoveredAmnt;
                    cgtsiLiability = firstClmPercentage * defaultAmnt;
                    secondInstllmntAmnt = cgtsiLiability - clmApprvdAmnt;
                    secondInstllmntAmnt = Math.rint(secondInstllmntAmnt);
                    if(secondInstllmntAmnt < 0.0D)
                        secondInstllmntAmnt = 0.0D;
                    claimdetail.setEligibleClaimAmt(secondInstllmntAmnt);
                    clmProcessingDtls.add(i, claimdetail);
                    recoveredAmnt = 0.0D;
                }
            }

        }
        Log.log(4, "ClaimsProcessor", "getClaimProcessingDetails()", "Exited");
        return clmProcessingDtls;
    }

    public Vector getClaimProcessingDetailsMod(String flag, java.sql.Date fromDate, java.sql.Date toDate)
        throws DatabaseException
    {
        Log.log(4, "ClaimsProcessor", "getClaimProcessingDetailsMod()", "Entered");
        Vector clmProcessingDtls = cpDAO.getClaimProcessingDetailsMod(flag, fromDate, toDate);
        Vector recoveryDtls = null;
        HashMap totalRecDetails = null;
        String bankId = null;
        String zoneId = null;
        String branchId = null;
        String bid = null;
        ClaimDetail claimdetail = null;
        String clmRefNumber = null;
        double apprvdApplicationAmnt = 0.0D;
        double outstandingAmntAsOnNPA = 0.0D;
        double defaultAmnt = 0.0D;
        double firstInstllmntAmnt = 0.0D;
        double cgtsiLiability = 0.0D;
        double secondInstllmntAmnt = 0.0D;
        Date npadate = null;
        Date clmApprvdDt = null;
        double recoveredAmnt = 0.0D;
        double tempTC = 0.0D;
        double tempWC = 0.0D;
        double interestAmount = 0.0D;
        double clmApprvdAmnt = 0.0D;
        double appliedClaimAmt = 0.0D;
        Date recoveryDt = null;
        HashMap recoveryDtl = null;
        Administrator admin = new Administrator();
        ParameterMaster pm = admin.getParameter();
        double firstClmPercentage = pm.getFirstInstallClaim() / 100D;
        if(flag.equals("F"))
        {
            Log.log(4, "ClaimsProcessor", "getClaimProcessingDetails()", (new StringBuilder("clmProcessingDtls.size() :")).append(clmProcessingDtls.size()).toString());
            for(int i = 0; i < clmProcessingDtls.size(); i++)
            {
                claimdetail = (ClaimDetail)clmProcessingDtls.remove(i);
                clmRefNumber = claimdetail.getClaimRefNum();
                bid = claimdetail.getBorrowerId();
                if(bid != null)
                {
                    apprvdApplicationAmnt = claimdetail.getApplicationApprovedAmount();
                    outstandingAmntAsOnNPA = claimdetail.getOutstandingAmntAsOnNPADate();
                    npadate = claimdetail.getNpaDate();
                    appliedClaimAmt = claimdetail.getAppliedClaimAmt();
                    totalRecDetails = isRecoveryDetailsAvailable(clmRefNumber);
                    recoveryDtls = (Vector)totalRecDetails.get("TEMP");
                    if(recoveryDtls.equals("") || recoveryDtls == null)
                        if(outstandingAmntAsOnNPA < apprvdApplicationAmnt)
                            defaultAmnt = outstandingAmntAsOnNPA;
                        else
                        if(outstandingAmntAsOnNPA > apprvdApplicationAmnt)
                            defaultAmnt = apprvdApplicationAmnt;
                        else
                        if(outstandingAmntAsOnNPA == apprvdApplicationAmnt)
                            defaultAmnt = apprvdApplicationAmnt;
                    if(recoveryDtls != null)
                    {
                        for(int j = 0; j < recoveryDtls.size(); j++)
                        {
                            recoveryDtl = (HashMap)recoveryDtls.elementAt(j);
                            if(recoveryDtl != null)
                            {
                                tempTC = ((Double)recoveryDtl.get("TCPRINCIPAL")).doubleValue();
                                interestAmount = ((Double)recoveryDtl.get("TCINTEREST")).doubleValue();
                                tempWC = ((Double)recoveryDtl.get("WC_AMOUNT")).doubleValue();
                                recoveredAmnt = recoveredAmnt + tempTC + tempWC + interestAmount;
                            }
                        }

                        Log.log(4, "ClaimsProcessor", "getClaimProcessingDetails()", (new StringBuilder("Claim Ref Number :")).append(clmRefNumber).append("Oustanding Amount :").append(outstandingAmntAsOnNPA).toString());
                        Log.log(4, "ClaimsProcessor", "getClaimProcessingDetails()", (new StringBuilder("Claim Ref Number :")).append(clmRefNumber).append("Approved Application Amount :").append(apprvdApplicationAmnt).toString());
                        Log.log(4, "ClaimsProcessor", "getClaimProcessingDetails()", (new StringBuilder("Claim Ref Number :")).append(clmRefNumber).append("Claim Applied Amount :").append(appliedClaimAmt).toString());
                        Log.log(4, "ClaimsProcessor", "getClaimProcessingDetails()", (new StringBuilder("Claim Ref Number :")).append(clmRefNumber).append("Recovered Amount :").append(recoveredAmnt).toString());
                        Log.log(4, "ClaimsProcessor", "getClaimProcessingDetails()", (new StringBuilder("Claim Ref Number :")).append(clmRefNumber).append("Default Amount :").append(defaultAmnt).toString());
                        outstandingAmntAsOnNPA -= recoveredAmnt;
                        if(outstandingAmntAsOnNPA < apprvdApplicationAmnt)
                            defaultAmnt = outstandingAmntAsOnNPA;
                        else
                        if(outstandingAmntAsOnNPA > apprvdApplicationAmnt)
                            defaultAmnt = apprvdApplicationAmnt;
                        else
                        if(outstandingAmntAsOnNPA == apprvdApplicationAmnt)
                            defaultAmnt = apprvdApplicationAmnt;
                        Log.log(4, "ClaimsProcessor", "getClaimProcessingDetails()", (new StringBuilder("pm.getCgtsiLiability() :")).append(pm.getCgtsiLiability() / 100D).toString());
                        cgtsiLiability = (pm.getCgtsiLiability() / 100D) * defaultAmnt;
                        if(defaultAmnt <= 500000D)
                            cgtsiLiability = 0.80000000000000004D * defaultAmnt;
                        else
                            cgtsiLiability = (pm.getCgtsiLiability() / 100D) * defaultAmnt;
                        firstInstllmntAmnt = firstClmPercentage * cgtsiLiability;
                        firstInstllmntAmnt = Math.rint(firstInstllmntAmnt);
                        if(firstInstllmntAmnt < 0.0D)
                            firstInstllmntAmnt = 0.0D;
                        claimdetail.setEligibleClaimAmt(firstInstllmntAmnt);
                        clmProcessingDtls.add(i, claimdetail);
                    }
                    recoveredAmnt = 0.0D;
                }
            }

        } else
        if(flag.equals("S"))
        {
            for(int i = 0; i < clmProcessingDtls.size(); i++)
            {
                recoveredAmnt = 0.0D;
                tempTC = 0.0D;
                tempWC = 0.0D;
                clmApprvdAmnt = 0.0D;
                recoveryDt = null;
                claimdetail = (ClaimDetail)clmProcessingDtls.remove(i);
                clmRefNumber = claimdetail.getClaimRefNum();
                String memberId = claimdetail.getMliId();
                if(memberId != null && memberId.length() == 12)
                {
                    bankId = memberId.substring(0, 4);
                    zoneId = memberId.substring(4, 8);
                    branchId = memberId.substring(8, 12);
                    bid = claimdetail.getBorrowerId();
                    HashMap firstClmDtls = getFirstClmDtlForBid(bankId, zoneId, branchId, bid);
                    if(firstClmDtls != null)
                    {
                        Double clmApprvdAmntObj = (Double)firstClmDtls.get("CLMAPPRVDAMT");
                        if(clmApprvdAmntObj != null)
                            clmApprvdAmnt = clmApprvdAmntObj.doubleValue();
                        clmApprvdDt = (Date)firstClmDtls.get("CLMAPPRVDDT");
                    }
                    totalRecDetails = isRecoveryDetailsAvailable(clmRefNumber);
                    recoveryDtls = (Vector)totalRecDetails.get("TEMP");
                    apprvdApplicationAmnt = claimdetail.getApplicationApprovedAmount();
                    outstandingAmntAsOnNPA = claimdetail.getOutstandingAmntAsOnNPADate();
                    npadate = claimdetail.getNpaDate();
                    if(outstandingAmntAsOnNPA < apprvdApplicationAmnt)
                        defaultAmnt = outstandingAmntAsOnNPA;
                    else
                    if(outstandingAmntAsOnNPA > apprvdApplicationAmnt)
                        defaultAmnt = apprvdApplicationAmnt;
                    else
                    if(outstandingAmntAsOnNPA == apprvdApplicationAmnt)
                        defaultAmnt = outstandingAmntAsOnNPA;
                    for(int j = 0; j < recoveryDtls.size(); j++)
                    {
                        recoveryDtl = (HashMap)recoveryDtls.elementAt(j);
                        if(recoveryDtl != null)
                        {
                            tempTC = ((Double)recoveryDtl.get("TCPRINCIPAL")).doubleValue();
                            tempWC = ((Double)recoveryDtl.get("WC_AMOUNT")).doubleValue();
                            recoveredAmnt = recoveredAmnt + tempTC + tempWC;
                        }
                    }

                    Log.log(4, "ClaimsProcessor", "getClaimProcessingDetails()", (new StringBuilder("Claim Ref Number :")).append(clmRefNumber).append("Oustanding Amount :").append(outstandingAmntAsOnNPA).toString());
                    Log.log(4, "ClaimsProcessor", "getClaimProcessingDetails()", (new StringBuilder("Claim Ref Number :")).append(clmRefNumber).append("Approved Application Amount :").append(apprvdApplicationAmnt).toString());
                    Log.log(4, "ClaimsProcessor", "getClaimProcessingDetails()", (new StringBuilder("Claim Ref Number :")).append(clmRefNumber).append("Claim Applied Amount :").append(appliedClaimAmt).toString());
                    Log.log(4, "ClaimsProcessor", "getClaimProcessingDetails()", (new StringBuilder("Claim Ref Number :")).append(clmRefNumber).append("Recovered Amount :").append(recoveredAmnt).toString());
                    Log.log(4, "ClaimsProcessor", "getClaimProcessingDetails()", (new StringBuilder("Claim Ref Number :")).append(clmRefNumber).append("Default Amount :").append(defaultAmnt).toString());
                    defaultAmnt -= recoveredAmnt;
                    cgtsiLiability = firstClmPercentage * defaultAmnt;
                    secondInstllmntAmnt = cgtsiLiability - clmApprvdAmnt;
                    secondInstllmntAmnt = Math.rint(secondInstllmntAmnt);
                    if(secondInstllmntAmnt < 0.0D)
                        secondInstllmntAmnt = 0.0D;
                    claimdetail.setEligibleClaimAmt(secondInstllmntAmnt);
                    clmProcessingDtls.add(i, claimdetail);
                    recoveredAmnt = 0.0D;
                }
            }

        }
        Log.log(4, "ClaimsProcessor", "getClaimProcessingDetails()", "Exited");
        return clmProcessingDtls;
    }

    public ClaimDetail getDetailsForClaimRefNumber(String claimRefNumber)
        throws DatabaseException
    {
        ClaimDetail claimdetail = cpDAO.getDetailsForClaimRefNumber(claimRefNumber);
        Vector updatedCgpanDetails = new Vector();
        Administrator admin = new Administrator();
        ParameterMaster parameterMaster = admin.getParameter();
        int lockInMonths = parameterMaster.getLockInPeriod();
        Vector cgpanDtls = claimdetail.getCgpanDetails();
        for(int i = 0; i < cgpanDtls.size(); i++)
        {
            HashMap cgpnDtl = (HashMap)cgpanDtls.elementAt(i);
            Date guaranteeStartDate = (Date)cgpnDtl.get("GUARANTEESTARTDT");
            Date lastDisbursementDate = (Date)cgpnDtl.get("LASTDSBRSMNTDT");
            Date lockInPeriodEndDate = null;
            Date lockInPeriodBeginDate = null;
            String applicationType = (String)cgpnDtl.get("LoanType");
            if(applicationType.equals("TC"))
            {
                if(guaranteeStartDate != null && lastDisbursementDate != null)
                {
                    if(guaranteeStartDate.compareTo(lastDisbursementDate) > 0)
                        lockInPeriodBeginDate = guaranteeStartDate;
                    else
                    if(guaranteeStartDate.compareTo(lastDisbursementDate) < 0)
                        lockInPeriodBeginDate = lastDisbursementDate;
                    else
                        lockInPeriodBeginDate = guaranteeStartDate;
                    if(lockInMonths != -1)
                    {
                        lockInPeriodEndDate = getDate(lockInPeriodBeginDate, lockInMonths);
                        cgpnDtl.put("LockInPeriodEndDt", lockInPeriodEndDate);
                    } else
                    {
                        cgpnDtl.put("LockInPeriodEndDt", null);
                    }
                } else
                {
                    cgpnDtl.put("LockInPeriodEndDt", null);
                }
            } else
            if(applicationType.equals("WC"))
                if(guaranteeStartDate != null && lockInMonths != -1)
                {
                    lockInPeriodEndDate = getDate(guaranteeStartDate, lockInMonths);
                    cgpnDtl.put("LockInPeriodEndDt", lockInPeriodEndDate);
                } else
                {
                    cgpnDtl.put("LockInPeriodEndDt", null);
                }
            updatedCgpanDetails.addElement(cgpnDtl);
        }

        claimdetail.setCgpanDetails(updatedCgpanDetails);
        return claimdetail;
    }

    public String getBorowwerForCGPAN(String cgpan)
        throws DatabaseException
    {
        return cpDAO.getBorowwerForCGPAN(cgpan);
    }

    public Vector getOTSDetails(String bid)
        throws DatabaseException
    {
        return cpDAO.getOTSDetails(bid);
    }

    public void saveSettlementDetails(Vector details, Vector voucherdtls, String createdBy, ChequeDetails chequeDetails, String contextPath)
        throws MessageException, DatabaseException
    {
        cpDAO.saveSettlementDetails(details, voucherdtls, createdBy, chequeDetails, contextPath);
    }

    public HashMap getClmRefAndFlagDtls(String bankId, String zoneId, String branchId, String borrowerId)
        throws DatabaseException
    {
        return cpDAO.getClmRefAndFlagDtls(bankId, zoneId, branchId, borrowerId);
    }

    public String generateCGCSANumber()
        throws DatabaseException
    {
        return cpDAO.generateCGCSANumber();
    }

    public Vector getSettlementAdviceDetail(String memberId, String flag)
        throws DatabaseException
    {
        return cpDAO.getSettlementAdviceDetail(memberId, flag);
    }

    public Vector getAllClaimsFiled()
        throws DatabaseException
    {
        return cpDAO.getAllClaimsFiled();
    }

    public void saveSettlementAdviceDetail(Vector settlemntAdviceDtls, String userid)
        throws DatabaseException
    {
        cpDAO.saveSettlementAdviceDetail(settlemntAdviceDtls, userid);
    }

    public void updateLegalProceedingDetails(LegalProceedingsDetail legaldtls)
        throws DatabaseException
    {
        cpDAO.updateLegalProceedingDetails(legaldtls);
    }

    public void saveOTSProcessingResults(Vector otsApprovalDetails, String userId)
        throws DatabaseException
    {
        cpDAO.saveOTSProcessingResults(otsApprovalDetails, userId);
    }

    public HashMap saveClaimProcessingResults(Vector claimdetails)
        throws DatabaseException
    {
        return cpDAO.saveClaimProcessingResults(claimdetails);
    }

    public String generateCGCLAN()
        throws DatabaseException
    {
        return cpDAO.generateCGCLAN();
    }

    public HashMap getFirstClmDtlForBid(String bankId, String zoneId, String branchId, String borrowerId)
        throws DatabaseException
    {
        return cpDAO.getFirstClmDtlForBid(bankId, zoneId, branchId, borrowerId);
    }

    public Vector getOTSReferenceDetailsForBorrower(String borrowerId)
        throws DatabaseException
    {
        return cpDAO.getOTSReferenceDetailsForBorrower(borrowerId);
    }

    public ArrayList updateClaimApplication(Hashtable claimApp, String memberId, String userId)
        throws DatabaseException
    {
        Log.log(4, "ClaimsProcessor", "updateClaimApplication()", "Entered");
        StringTokenizer tokenizer = null;
        ArrayList results = new ArrayList();
        String message = "";
        String borrowerId = null;
        ClaimApplication claimApplication = null;
        String whichClmApplicationHasUserFiled = null;
        String bankId = null;
        String zoneId = null;
        String branchId = null;
        Enumeration keys = claimApp.keys();
        if(keys != null)
            while(keys.hasMoreElements()) 
            {
                message = "";
                borrowerId = (String)keys.nextElement();
                claimApplication = (ClaimApplication)claimApp.get(borrowerId);
                if(claimApplication == null)
                    continue;
                if(!claimApplication.getIsVerified())
                {
                    message = (new StringBuilder(String.valueOf(message))).append("The Claim Application for Borrower :").append(borrowerId).append("has not been verified.").toString();
                    results.add(message);
                    continue;
                }
                HashMap whichClmApplicationsHasUserFiled;
                if(claimApplication.getFirstInstallment())
                {
                    bankId = memberId.substring(0, 4);
                    zoneId = memberId.substring(4, 8);
                    branchId = memberId.substring(8, 12);
                    whichClmApplicationsHasUserFiled = getClmRefAndFlagDtls(bankId, zoneId, branchId, borrowerId);
                    if(whichClmApplicationsHasUserFiled != null && whichClmApplicationsHasUserFiled.size() > 0)
                    {
                        Set valuesSet = whichClmApplicationsHasUserFiled.keySet();
                        Iterator valuesIterator = valuesSet.iterator();
                        boolean jumpWhileLoop = false;
                        while(valuesIterator.hasNext()) 
                        {
                            String installmentFlag = null;
                            String cgclan = null;
                            String firstSettlementDate = null;
                            String id = (String)valuesIterator.next();
                            String val = (String)whichClmApplicationsHasUserFiled.get(id);
                            if(id.equals("F"))
                            {
                                if(val == null)
                                {
                                    message = (new StringBuilder(String.valueOf(message))).append("For the borrower :").append(borrowerId).append("Application for First Claim Installment has already been filed and pending CGTSI Processing").toString();
                                    results.add(message);
                                    jumpWhileLoop = true;
                                    break;
                                }
                                if(val.equals("$"))
                                {
                                    message = (new StringBuilder(String.valueOf(message))).append("For the borrower :").append(borrowerId).append("Application for Claim First Installment has already been filed and is rejected.").toString();
                                    results.add(message);
                                    jumpWhileLoop = true;
                                    break;
                                }
                                if(val.equals("^"))
                                {
                                    message = (new StringBuilder(String.valueOf(message))).append("For the borrower :").append(borrowerId).append("Claim Application has been completely settled.").toString();
                                    results.add(message);
                                    jumpWhileLoop = true;
                                    break;
                                }
                                if(val.equals(""))
                                    continue;
                                tokenizer = new StringTokenizer(val, "#");
                                boolean isStatusRead = false;
                                boolean isFlagRead = false;
                                boolean isCGCLANRead = false;
                                boolean isSettlementDateRead = false;
                                while(tokenizer.hasMoreTokens()) 
                                {
                                    String token = tokenizer.nextToken();
                                    if(!isSettlementDateRead)
                                        if(!isCGCLANRead)
                                        {
                                            if(!isFlagRead)
                                            {
                                                installmentFlag = token;
                                                isFlagRead = true;
                                            } else
                                            {
                                                cgclan = token;
                                                isCGCLANRead = true;
                                            }
                                        } else
                                        {
                                            firstSettlementDate = token;
                                            isSettlementDateRead = true;
                                        }
                                }
                                if(firstSettlementDate.equals("-"))
                                {
                                    message = (new StringBuilder(String.valueOf(message))).append("For the Borrower :").append(borrowerId).append("Application for First Claim Installment has already been filed and approved and pending for Settlement by CGTSI").toString();
                                    results.add(message);
                                    jumpWhileLoop = true;
                                } else
                                {
                                    message = (new StringBuilder(String.valueOf(message))).append("For the Borrower :").append(borrowerId).append("Application for First Claim Installment has already been Filed, Approved and Settled by CGTSI").toString();
                                    results.add(message);
                                    jumpWhileLoop = true;
                                }
                                break;
                            }
                            if(!id.equals("S"))
                                continue;
                            jumpWhileLoop = true;
                            break;
                        }
                        if(jumpWhileLoop)
                            break;
                    }
                    Vector gfeeDtlsVector = getLockInDetails(borrowerId);
                    Vector gfeePaidCGPANS = new Vector();
                    boolean gfeepaid = false;
                    for(int i = 0; i < gfeeDtlsVector.size(); i++)
                    {
                        HashMap gfeedtl = (HashMap)gfeeDtlsVector.elementAt(i);
                        String cgpn = (String)gfeedtl.get("CGPAN");
                        Date gfeedate = (Date)gfeedtl.get("GUARANTEESTARTDT");
                        if(gfeedate != null)
                        {
                            gfeepaid = true;
                            gfeePaidCGPANS.addElement(cgpn);
                        } else
                        {
                            gfeepaid = false;
                            message = (new StringBuilder(String.valueOf(message))).append("For the CGPAN ").append(cgpn).append("Guarantee Fee has not been paid. It will not be considered for calculating the Claim Installment.").toString();
                        }
                    }

                    HashMap npadetails = isNPADetailsAvailable(borrowerId);
                    String willfulDefaulter = null;
                    boolean npaDtlsAvl = false;
                    if(npadetails == null)
                    {
                        message = (new StringBuilder(String.valueOf(message))).append("NPA Details are not available for the Borrower ").append(borrowerId).append(". Claim Application for the said Borrower stands rejected.").toString();
                        results.add(message);
                    } else
                    if(npadetails.size() == 0)
                    {
                        message = (new StringBuilder(String.valueOf(message))).append("NPA Details are not available for the Borrower ").append(borrowerId).append(". Claim Application for the said Borrower stands rejected.").toString();
                        results.add(message);
                    } else
                    {
                        HashMap npadtlMainTable = (HashMap)npadetails.get("MAIN");
                        if(npadtlMainTable != null && npadtlMainTable.size() > 0)
                        {
                            willfulDefaulter = (String)npadtlMainTable.get("WillFulDefaulter");
                            if(willfulDefaulter != null)
                            {
                                if(npaDtlsAvl);
                                npaDtlsAvl = true;
                            }
                        }
                        HashMap npadtltemptable = (HashMap)npadetails.get("TEMP");
                        if(npadtltemptable != null && npadtltemptable.size() > 0)
                        {
                            willfulDefaulter = (String)npadtltemptable.get("WillFulDefaulter");
                            if(willfulDefaulter != null)
                            {
                                if(npaDtlsAvl);
                                npaDtlsAvl = true;
                            }
                        }
                        if(!npaDtlsAvl)
                        {
                            message = (new StringBuilder(String.valueOf(message))).append("NPA Details are not available for the Borrower ").append(borrowerId).append(". Claim Application for the said Borrower stands rejected.").toString();
                            results.add(message);
                        } else
                        {
                            LegalProceedingsDetail legaldetails = null;
                            boolean legalDtlsAvl = false;
                            legaldetails = claimApplication.getLegalProceedingsDetails();
                            if(legaldetails != null && legaldetails.getForumRecoveryProceedingsInitiated() != null && legaldetails.getNameOfForum() != null && legaldetails.getLocation() != null && legaldetails.getFilingDate() != null)
                                legalDtlsAvl = true;
                            else
                                legalDtlsAvl = false;
                            if(!legalDtlsAvl)
                            {
                                message = (new StringBuilder(String.valueOf(message))).append("Legal Proceedings Details are not available for the Borrower ").append(borrowerId).append(".The Claim Application is rejected for the said Borrower.").toString();
                                results.add(message);
                            } else
                            {
                                Administrator admin = new Administrator();
                                ParameterMaster pm = admin.getParameter();
                                double minAmntForITPAN = pm.getMinAmtForMandatoryITPAN();
                                Vector details = getOTSRequestDetails(borrowerId);
                                double amntSanctioned = 0.0D;
                                boolean isITPANDtlAvl = false;
                                for(int i = 0; i < details.size(); i++)
                                {
                                    Hashtable dtl = (Hashtable)details.elementAt(i);
                                    if(dtl != null)
                                    {
                                        Log.log(4, "ClaimAction", "forwardToNextPage", (new StringBuilder("Printing the Hashmap to check ITPAN Details :")).append(dtl).toString());
                                        String amntSanctionedStr = (String)dtl.get("ApprovedAmount");
                                        if(amntSanctionedStr != null)
                                        {
                                            amntSanctioned = Double.parseDouble(amntSanctionedStr);
                                            Log.log(4, "ClaimAction", "forwardToNextPage", (new StringBuilder("Printing the Hashmap to check ITPAN Details amntSanctioned:")).append(amntSanctioned).toString());
                                            if(amntSanctioned > minAmntForITPAN)
                                            {
                                                isITPANDtlAvl = checkIfITPANAvailable(memberId, borrowerId);
                                                if(!isITPANDtlAvl)
                                                {
                                                    message = (new StringBuilder(String.valueOf(message))).append("ITPAN Details are not available for the Borrower ").append(borrowerId).append(".The Claim Application is rejected for the said Borrower.").toString();
                                                    results.add(message);
                                                }
                                            }
                                        }
                                    }
                                }

                                boolean islockinperiodover = isLockinPeriodOver(borrowerId);
                                if(!islockinperiodover)
                                {
                                    message = (new StringBuilder(String.valueOf(message))).append("Lock-in Period has not been completed for the Borrower :").append(borrowerId).append(".").toString();
                                    results.add(message);
                                } else
                                {
                                    claimApplication.setMemberId(memberId);
                                    claimApplication.setCreatedModifiedy(userId);
                                    try
                                    {
                                        addClaimApplication(claimApplication, null, true);
                                        message = (new StringBuilder(String.valueOf(message))).append("Claim Application for Borrower ").append(borrowerId).append(" has been successfully saved in the Database.").toString();
                                        results.add(message);
                                    }
                                    catch(DatabaseException ex)
                                    {
                                        message = (new StringBuilder(String.valueOf(message))).append("There is an error filing First Claim Application for Borrower :").append(borrowerId).append(". The reason is ").append(ex.getMessage()).toString();
                                        results.add(message);
                                    }
                                }
                            }
                        }
                    }
                    continue;
                }
                if(!claimApplication.getSecondInstallment())
                    continue;
                bankId = memberId.substring(0, 4);
                zoneId = memberId.substring(4, 8);
                branchId = memberId.substring(8, 12);
                whichClmApplicationsHasUserFiled = getClmRefAndFlagDtls(bankId, zoneId, branchId, borrowerId);
                if(whichClmApplicationsHasUserFiled != null)
                {
                    if(whichClmApplicationsHasUserFiled.size() > 0)
                    {
                        Set valuesSet = whichClmApplicationsHasUserFiled.keySet();
                        Iterator valuesIterator = valuesSet.iterator();
                        boolean jumpSecWhileLoop = false;
                        while(valuesIterator.hasNext()) 
                        {
                            String installmentFlag = null;
                            String cgclan = null;
                            String firstSettlementDate = null;
                            String secondSettlementDate = null;
                            String id = (String)valuesIterator.next();
                            String val = (String)whichClmApplicationsHasUserFiled.get(id);
                            boolean isFlagRead;
                            boolean isCGCLANRead;
                            boolean isSettlementDateRead;
                            if(id.equals("F"))
                            {
                                if(val == null)
                                {
                                    message = (new StringBuilder(String.valueOf(message))).append("For the Borrower ").append(borrowerId).append("Application for Claim First Installment is pending for Approval. Please file Application for Second Claim Installment after the Approval and Settlement of Application of Claim First Installment.").toString();
                                    results.add(message);
                                    jumpSecWhileLoop = true;
                                    break;
                                }
                                if(val.equals("$"))
                                {
                                    message = (new StringBuilder(String.valueOf(message))).append("For the Borrower ").append(borrowerId).append("Application for Claim First Installment has already been filed and is rejected. You cannot file application for Claim Second Installment.").toString();
                                    results.add(message);
                                    jumpSecWhileLoop = true;
                                    break;
                                }
                                if(val.equals("^"))
                                {
                                    message = (new StringBuilder(String.valueOf(message))).append("For the borrower :").append(borrowerId).append("Claim Application has been completely settled.").toString();
                                    results.add(message);
                                    jumpSecWhileLoop = true;
                                    break;
                                }
                                if(val.equals(""))
                                {
                                    message = (new StringBuilder(String.valueOf(message))).append("For the Borrower ").append(borrowerId).append("Please file Application for Claim First Installment before filing for Claim Second Installment.").toString();
                                    results.add(message);
                                    jumpSecWhileLoop = true;
                                    break;
                                }
                                tokenizer = new StringTokenizer(val, "#");
                                isFlagRead = false;
                                isCGCLANRead = false;
                                isSettlementDateRead = false;
                                while(tokenizer.hasMoreTokens()) 
                                {
                                    String token = tokenizer.nextToken();
                                    if(!isSettlementDateRead)
                                        if(!isCGCLANRead)
                                        {
                                            if(!isFlagRead)
                                            {
                                                installmentFlag = token;
                                                isFlagRead = true;
                                            } else
                                            {
                                                cgclan = token;
                                                isCGCLANRead = true;
                                            }
                                        } else
                                        {
                                            firstSettlementDate = token;
                                            isSettlementDateRead = true;
                                        }
                                }
                                if(!firstSettlementDate.equals("-"))
                                    continue;
                                message = (new StringBuilder(String.valueOf(message))).append("For the Borrower ").append(borrowerId).append("Application for First Claim Installment has already been filed and approved and pending for Settlement by CGTSI. Please file Application for Second Claim Installment after the Settlement of First Claim Installment.").toString();
                                results.add(message);
                                jumpSecWhileLoop = true;
                                break;
                            }
                            if(!id.equals("S"))
                                continue;
                            if(val == null)
                            {
                                message = (new StringBuilder(String.valueOf(message))).append("For the Borrower ").append(borrowerId).append("Application for Claim Second Installment is pending processing by CGTSI.").toString();
                                results.add(message);
                                jumpSecWhileLoop = true;
                                break;
                            }
                            if(val.equals("$"))
                            {
                                message = (new StringBuilder(String.valueOf(message))).append("For the Borrower ").append(borrowerId).append("Application for Claim Second Installment has already been filed and is rejected.").toString();
                                results.add(message);
                                jumpSecWhileLoop = true;
                                break;
                            }
                            if(val.equals(""))
                                continue;
                            tokenizer = new StringTokenizer(val, "#");
                            isFlagRead = false;
                            isCGCLANRead = false;
                            isSettlementDateRead = false;
                            while(tokenizer.hasMoreTokens()) 
                            {
                                String token = tokenizer.nextToken();
                                if(!isSettlementDateRead)
                                    if(!isCGCLANRead)
                                    {
                                        if(!isFlagRead)
                                        {
                                            installmentFlag = token;
                                            isFlagRead = true;
                                        } else
                                        {
                                            cgclan = token;
                                            isCGCLANRead = true;
                                        }
                                    } else
                                    {
                                        secondSettlementDate = token;
                                        isSettlementDateRead = true;
                                    }
                            }
                            if(secondSettlementDate.equals("-"))
                            {
                                message = (new StringBuilder(String.valueOf(message))).append("For the Borrower ").append(borrowerId).append("Application for Second Claim Installment has already been filed and approved and is pending for Settlement by CGTSI.").toString();
                                results.add(message);
                                jumpSecWhileLoop = true;
                            } else
                            {
                                message = (new StringBuilder(String.valueOf(message))).append("For the Borrower ").append(borrowerId).append("Application for Second Claim Installment has been filed, approved and settled by CGTSI.").toString();
                                results.add(message);
                                jumpSecWhileLoop = true;
                            }
                            break;
                        }
                        if(jumpSecWhileLoop)
                            break;
                    } else
                    {
                        message = (new StringBuilder(String.valueOf(message))).append("For the Borrower ").append(borrowerId).append("Please file Application for First Claim Installment before filing for Second Claim Installment.").toString();
                        break;
                    }
                } else
                {
                    message = (new StringBuilder(String.valueOf(message))).append("For the Borrower ").append(borrowerId).append("Please file Application for First Claim Installment before filing for Second Claim Installment.").toString();
                    break;
                }
                claimApplication.setMemberId(memberId);
                claimApplication.setCreatedModifiedy(userId);
                try
                {
                    addClaimApplication(claimApplication, null, true);
                    message = (new StringBuilder(String.valueOf(message))).append("Claim Application for Borrower ").append(borrowerId).append(" has been successfully saved in the Database.").toString();
                    results.add(message);
                }
                catch(DatabaseException ex)
                {
                    message = (new StringBuilder(String.valueOf(message))).append("There is an error filing 2nd Claim Application for Borrower :").append(borrowerId).append(". The error is :").append(ex.getMessage()).toString();
                    results.add(message);
                }
            }
        return results;
    }

    public Vector getClaimAppliedAmounts(String borrowerid, String flag)
        throws DatabaseException
    {
        return cpDAO.getClaimAppliedAmounts(borrowerid, flag);
    }

    public HashMap getClaimSettlementDetailForBorrower(String borrowerId)
        throws DatabaseException
    {
        return cpDAO.getClaimSettlementDetailForBorrower(borrowerId);
    }

    public Vector getAllVoucherIds()
        throws DatabaseException
    {
        return cpDAO.getAllVoucherIds();
    }

    public HashMap getPrimarySecurityAndNetworthOfGuarantors(String borrowerId, String memberId)
        throws DatabaseException
    {
        return cpDAO.getPrimarySecurityAndNetworthOfGuarantors(borrowerId, memberId);
    }

    public HashMap getRepaymentDetails(String cgpan)
        throws DatabaseException
    {
        return cpDAO.getRepaymentDetails(cgpan);
    }

    public HashMap getClaimLimitDtls(String designation)
        throws DatabaseException
    {
        return cpDAO.getClaimLimitDtls(designation);
    }

    public Vector getFirstClmPerGaurSecDtls(String clmRefNumber)
        throws DatabaseException
    {
        return cpDAO.getFirstClmPerGaurSecDtls(clmRefNumber);
    }

    public boolean checkIfMemCanBeDeactivated(String bankId, String zoneId, String branchId)
        throws DatabaseException
    {
        Vector claimProcessingDtls = getClaimProcessingDetails("F");
        String memIdForDeactivation = (new StringBuilder(String.valueOf(bankId))).append(zoneId).append(branchId).toString();
        String memberId = null;
        if(claimProcessingDtls != null && claimProcessingDtls.size() > 0)
        {
            for(int i = 0; i < claimProcessingDtls.size(); i++)
            {
                ClaimDetail clmDtl = (ClaimDetail)claimProcessingDtls.elementAt(i);
                if(clmDtl != null)
                {
                    memberId = clmDtl.getMliId();
                    if(memberId != null && memberId.equals(memIdForDeactivation))
                        return false;
                }
            }

        }
        claimProcessingDtls = getClaimProcessingDetails("S");
        if(claimProcessingDtls != null && claimProcessingDtls.size() > 0)
        {
            for(int i = 0; i < claimProcessingDtls.size(); i++)
            {
                ClaimDetail clmDtl = (ClaimDetail)claimProcessingDtls.elementAt(i);
                if(clmDtl != null)
                {
                    memberId = clmDtl.getMliId();
                    if(memberId != null && memberId.equals(memIdForDeactivation))
                        return false;
                }
            }

        }
        String bnkId = memIdForDeactivation.substring(0, 4);
        String zneId = memIdForDeactivation.substring(4, 8);
        String brnchId = memIdForDeactivation.substring(8, 12);
        Vector dtls = getSettlementDetails(bnkId, zneId, brnchId, "F", false);
        if(dtls != null && dtls.size() > 0)
            return false;
        dtls = getSettlementDetails(bnkId, zneId, brnchId, "S", false);
        return dtls == null || dtls.size() <= 0;
    }

    public Vector getCGPANSForBid(String bid)
        throws DatabaseException
    {
        return cpDAO.getCGPANSForBid(bid);
    }

    public void saveITPANDetail(String borrowerId, String itpan)
        throws DatabaseException
    {
        cpDAO.saveITPANDetail(borrowerId, itpan);
    }

    //commented by AbhishekSikarwar
    public void insertRecallAndLegalAttachments(ClaimApplication claimApplication, String claimRefNumber, boolean internetUser)
        throws DatabaseException
    {
        //cpDAO.insertRecallAndLegalAttachments(claimApplication, claimRefNumber, internetUser);
    }

    private boolean checkIfITPANAvailable(String memberId, String borrowerId)
        throws DatabaseException
    {
        Log.log(4, "ClaimsProcessor", "checkIfITPANAvailable", "Entered");
        GMProcessor processor = new GMProcessor();
        BorrowerDetails bidDtls = processor.getBorrowerDetailsForBID(memberId, borrowerId);
        SSIDetails ssiDtls = bidDtls.getSsiDetails();
        String cpITPAN = ssiDtls.getCpITPAN();
        Log.log(4, "ClaimsProcessor", "checkIfITPANAvailable", (new StringBuilder("Chief Promoter ITPAN is :")).append(cpITPAN).toString());
        if(cpITPAN != null && !cpITPAN.equals(""))
        {
            return true;
        } else
        {
            Log.log(4, "ClaimsProcessor", "checkIfITPANAvailable", "Exited");
            return false;
        }
    }

    public TermLoan getTermLoan(String appRefNo, String applicationLoanType)
        throws DatabaseException, NoApplicationFoundException
    {
        ApplicationDAO applicationDAO = new ApplicationDAO();
        TermLoan termLoanObj = applicationDAO.getTermLoan(appRefNo, applicationLoanType);
        return termLoanObj;
    }

    public HashMap getWorkingCapital(String cgpan)
        throws DatabaseException
    {
        return cpDAO.getWorkingCapital(cgpan);
    }

    public String getAppRefNumber(String cgpan)
        throws DatabaseException
    {
        return cpDAO.getAppRefNumber(cgpan);
    }

    public ArrayList getAllCgpansForClmRefNum(String clmRefNum)
        throws DatabaseException
    {
        return cpDAO.getAllCgpansForClmRefNum(clmRefNum);
    }

    public Vector getClaimApprovalDetails(String loggedUsr, String bankName)
        throws DatabaseException
    {
        return cpDAO.getClaimApprovalDetails(loggedUsr, bankName);
    }

    //commented by Abhishek Sikarwar
    public void uploadClaimFiles(String clmReferenceNumber, String bid, String userID, ArrayList claimFiles, ClaimFiles claimFile)
        throws FileNotFoundException, IOException, DatabaseException
    {
        //cpDAO.uploadClaimFiles(clmReferenceNumber, bid, userID, claimFiles, claimFile);
    }

    public Map getAppSanctionDate(String borrwerId, String memberId)
        throws DatabaseException
    {
        return cpDAO.getAppSanctionDate(borrwerId, memberId);
    }
    
    public Vector getSancDetails(String borrowerid)
    throws DatabaseException
{
    return cpDAO.getSancDetails(borrowerid);
}
    
    public String updateClaimApplicationforResub(ClaimApplication claimApplication,
			HashMap map, boolean flag) throws DatabaseException, SQLException {
		String claimRefNumber = null;
		Log.log(4, "ClaimsProcessor",
				"updateClaimApplicationForFirstInstallment()", "Entered");
		claimRefNumber = claimApplication.getClaimRefNumber();
		claimApplication.setClaimRefNumber(claimRefNumber);
		cpDAO.updateClaimApplicationforResub(claimApplication, map, flag);
		Log.log(4, "ClaimsProcessor",
				"addClaimApplicationForFirstInstallment()", "Exited");
		return claimRefNumber;
	}
/*Added for GST*/
	public ArrayList<MLIInfo> getGSTStateList(String bankId) {
		// TODO Auto-generated method stub
		 return cpDAO.getGSTStateList(bankId);
	}
}
