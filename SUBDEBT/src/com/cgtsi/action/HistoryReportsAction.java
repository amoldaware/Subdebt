package com.cgtsi.action;

import com.cgtsi.actionform.ClaimActionForm;
import com.cgtsi.admin.User;
import com.cgtsi.claim.ClaimApplication;
import com.cgtsi.claim.ClaimSummaryDtls;
import com.cgtsi.claim.ClaimsProcessor;
import com.cgtsi.common.Log;
import com.cgtsi.common.NoDataException;
import com.cgtsi.reports.ApplicationReport;
import com.cgtsi.reports.ReportDAO;
import com.cgtsi.util.DBConnection;
import java.io.PrintStream;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Vector;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

public class HistoryReportsAction extends BaseAction
{
  public ActionForward historyReport(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response)
    throws Exception
  {
    Log.log(4, "HistoryReportsAction", "historyReport", "Entered");
    Log.log(4, "HistoryReportsAction", "historyReport", "Exited");

    return mapping.findForward("success");
  }

  public ActionForward historyReportDetails(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response)
    throws Exception
  {
    Log.log(4, "HistoryReportsAction", "historyReportDetails", "Entered");

    ReportDAO reportDao = new ReportDAO();

    ClaimActionForm claimForm = (ClaimActionForm)form;

    String status = "NEW";
    String memberId = "";
    ClaimsProcessor processor = new ClaimsProcessor();
    String cgpan1 = request.getParameter("enterCgpan");
    String ssiName = request.getParameter("enterSSI");
    ArrayList historyReport = new ArrayList();
    String[] claimRefNumberStringArray = null;
    String claimRefNumber = null;
    ArrayList claimRefNumberArray = new ArrayList();
    Connection connection = DBConnection.getConnection();
    ApplicationReport appReport = null;

    PreparedStatement applicationStmt = null;

    if (((cgpan1 == null) || (cgpan1.equals(""))) && ((ssiName == null) || (ssiName.equals(""))))
    {
      throw new NoDataException("Please Enter Any Other Value ");
    }

    if ((cgpan1 == null) || (cgpan1.equals("")))
    {
      historyReport = reportDao.getCgpan(ssiName);
      claimForm.setHistoryReport(historyReport);
      if ((historyReport == null) || (historyReport.size() == 0))
      {
        throw new NoDataException("No Data is available for the values entered, Please Enter Any Other Value ");
      }

      historyReport = null;

      return mapping.findForward("success1");
    }

    if ((cgpan1 != null) || (!cgpan1.equals("")))
    {
      try
      {
        String query = " select s.SSI_UNIT_NAME,s.SSI_ADDRESS,s.SSI_DISTRICT_NAME, s.SSI_STATE_NAME,s.SSI_CONSTITUTION, s.SSI_REGN_NUMBER, s.SSI_COMMENCEMENT_DT,s.SSI_NO_OF_EMPLOYEES,s.SSI_STATUS,  s.SSI_EXISTING_OUTSTANDING_AMT,s.SSI_INDUSTRY_NATURE, s.SSI_PROJECTED_SALES_TURNOVER, s.SSI_PROJECTED_EXPORTS, p.PMR_CHIEF_FIRST_NAME||' '||p.PMR_CHIEF_MIDDLE_NAME||' '||p.PMR_CHIEF_LAST_NAME, p.PMR_CHIEF_IT_PAN, p.PMR_CHIEF_GENDER,  p.PMR_FIRST_NAME || ',' || p.PMR_SECOND_NAME || ',' || p.PMR_THIRD_NAME, a.CGPAN,a.MEM_BNK_ID || a.MEM_ZNE_ID || a.MEM_BRN_ID, a.APP_REF_NO, a.APP_SUBMITTED_DT,a.APP_REMARKS,a.APP_STATUS,a.APP_LOAN_TYPE, a.APP_BANK_APP_REF_NO, s.SSI_INDUSTRY_SECTOR, s.BID,a.APP_PROJECT_OUTLAY, s.SSI_CITY || '-' || s.SSI_PINCODE   from ssi_detail s, promoter_detail p, application_detail a where s.SSI_REFERENCE_NUMBER = a.SSI_REFERENCE_NUMBER and  s.SSI_REFERENCE_NUMBER = p.SSI_REFERENCE_NUMBER and LTRIM(RTRIM(UPPER(a.CGPAN))) = LTRIM(RTRIM(UPPER(?)))";

        applicationStmt = connection.prepareStatement(query);
        applicationStmt.setString(1, cgpan1);
        ResultSet applicationResult = applicationStmt.executeQuery();
        appReport = new ApplicationReport();

        while (applicationResult.next())
        {
          appReport.setSsiName(applicationResult.getString(1));
          appReport.setAddress(applicationResult.getString(2));
          appReport.setDistrict(applicationResult.getString(3));
          appReport.setState(applicationResult.getString(4));
          appReport.setUnitType(applicationResult.getString(5));
          appReport.setRegistrationNumber(applicationResult.getString(6));
          appReport.setStartDate(applicationResult.getDate(7));
          appReport.setEmployees(applicationResult.getInt(8));
          appReport.setStatus(applicationResult.getString(9));
          appReport.setOutstanding(applicationResult.getDouble(10));
          appReport.setIndustryType(applicationResult.getString(11));
          appReport.setTurnover(applicationResult.getDouble(12));
          appReport.setExport(applicationResult.getDouble(13));
          appReport.setChiefPromoter(applicationResult.getString(14));
          appReport.setItpan(applicationResult.getString(15));
          appReport.setGender(applicationResult.getString(16));
          appReport.setOthers(applicationResult.getString(17));
          appReport.setMemberId(applicationResult.getString(19));
          appReport.setCgpan(applicationResult.getString(18));
          appReport.setReferenceNumber(applicationResult.getString(25));
          appReport.setApplicationDate(applicationResult.getDate(21));
          appReport.setRemarks(applicationResult.getString(22));
          appReport.setStatus(applicationResult.getString(23));
          appReport.setLoanType(applicationResult.getString(24));
          appReport.setIndustrySector(applicationResult.getString(26));
          appReport.setBid(applicationResult.getString(27));
          appReport.setProjectOutlay(applicationResult.getDouble(28));
        }

        applicationResult.close();
        applicationResult = null;
        applicationStmt.close();
        applicationStmt = null;

        String query1 = " select t.TRM_AMOUNT_SANCTIONED,t.TRM_AMOUNT_SANCTIONED_DT,  t.TRM_INTEREST_RATE, t.TRM_PLR, t.TRM_PROMOTER_CONTRIBUTION,  t.TRM_SUBSIDY_EQUITY_SUPPORT,  t.TRM_OTHERS,  a.APP_PROJECT_OUTLAY,  t.TRM_REPAYMENT_MORATORIUM,  t.TRM_FIRST_INSTALLMENT_DUE_DT,  t.TRM_NO_OF_INSTALLMENTS,  t.TRM_REPAYMENT_PERIODICITY from  term_loan_Detail t , application_Detail a  where LTRIM(RTRIM(UPPER(t.CGPAN))) = LTRIM(RTRIM(UPPER(?)))  and t.APP_REF_NO = a.APP_REF_NO ";

        applicationStmt = connection.prepareStatement(query1);
        applicationStmt.setString(1, cgpan1);
        applicationResult = applicationStmt.executeQuery();

        while (applicationResult.next())
        {
          appReport.setTcSanctionedOn(applicationResult.getDate(2));
          appReport.setTcSanctioned(applicationResult.getDouble(1));
          appReport.setTcRate(applicationResult.getDouble(3));
          appReport.setTcPlr(applicationResult.getDouble(4));
          appReport.setTcPromoterContribution(applicationResult.getDouble(5));
          appReport.setTcSubsidy(applicationResult.getDouble(6));
          appReport.setTcEquity(applicationResult.getDouble(7));
          appReport.setTcProjectCost(applicationResult.getDouble(8));
          appReport.setRepaymentMoratorium(applicationResult.getInt(9));
          appReport.setFirstInstallmentDueDate(applicationResult.getDate(10));
          appReport.setNumberOfInstallments(applicationResult.getInt(11));
          int repayment = applicationResult.getInt(12);
          if (repayment == 0)
          {
            appReport.setRepaymentPeriodicity("");
          }
          if (repayment == 1)
          {
            appReport.setRepaymentPeriodicity("Monthly");
          }
          if (repayment == 2)
          {
            appReport.setRepaymentPeriodicity("Quarterly");
          }
          if (repayment == 3)
          {
            appReport.setRepaymentPeriodicity("Half yearly");
          }
        }
        applicationResult.close();
        applicationResult = null;
        applicationStmt.close();
        applicationStmt = null;

        String query2 = " select t.WCP_FB_LIMIT_SANCTIONED,  t.WCP_FB_LIMIT_SANCTIONED_DT, t.WCP_NFB_LIMIT_SANCTIONED,  t.WCP_NFB_LIMIT_SANCTIONED_DT, t.WCP_INTEREST, t.WCP_PLR,  t.WCP_PROMOTERS_CONTRIBUTION, t.WCP_SUBSIDY_EQUITY_SUPPORT,t.WCP_OTHERS,  NVL(t.WCP_PROMOTERS_CONTRIBUTION,0)+NVL(t.WCP_SUBSIDY_EQUITY_SUPPORT,0)+  NVL(t.WCP_OTHERS,0),NVL(tc.WCO_FB_PRINCIPAL_OUTSTAND_AMT,0)+  NVL(tc.WCO_FB_INTEREST_OUTSTAND_AMT,0),  NVL(tc.WCO_NFB_PRINCIPAL_OUTSTAND_AMT,0)+NVL(tc.WCO_NFB_COMM_OUTSTAND_AMT,0)  from working_capital_Detail t, wc_outstanding_detail tc  where LTRIM(RTRIM(UPPER(tc.CGPAN))) = LTRIM(RTRIM(UPPER(?))) and LTRIM(RTRIM(UPPER(t.CGPAN))) = LTRIM(RTRIM(UPPER(?)))";

        applicationStmt = connection.prepareStatement(query2);
        applicationStmt.setString(1, cgpan1);
        applicationStmt.setString(2, cgpan1);
        applicationResult = applicationStmt.executeQuery();

        while (applicationResult.next())
        {
          appReport.setWcFbSanctioned(applicationResult.getDouble(1));
          appReport.setWcFbSanctionedOn(applicationResult.getDate(2));
          appReport.setWcNfbSanctioned(applicationResult.getDouble(3));
          appReport.setWcNfbSanctionedOn(applicationResult.getDate(4));
          appReport.setWcInterest(applicationResult.getDouble(5));
          appReport.setWcPlr(applicationResult.getDouble(6));
          appReport.setWcPromoterContribution(applicationResult.getDouble(7));
          appReport.setWcSubsidy(applicationResult.getDouble(8));
          appReport.setWcEquity(applicationResult.getDouble(9));
          appReport.setWcProjectCost(applicationResult.getDouble(10));
          appReport.setWcFbPrincipalOutstanding(applicationResult.getDouble(11));
          appReport.setWcNfbPrincipalOutstanding(applicationResult.getDouble(12));
        }
        applicationResult.close();
        applicationResult = null;
        applicationStmt.close();
        applicationStmt = null;

        String query3 = " select DBR_AMOUNT, DBR_DT, DBR_FINAL_DISBURSEMENT_FLAG from  disbursement_detail where DBR_DT = (select max(DBR_DT) from  disbursement_detail where LTRIM(RTRIM(UPPER(CGPAN))) = LTRIM(RTRIM(UPPER(?)))) and LTRIM(RTRIM(UPPER(CGPAN))) = LTRIM(RTRIM(UPPER(?)))";

        applicationStmt = connection.prepareStatement(query3);
        applicationStmt.setString(1, cgpan1);
        applicationStmt.setString(2, cgpan1);
        applicationResult = applicationStmt.executeQuery();

        while (applicationResult.next())
        {
          appReport.setDisbursementAmount(applicationResult.getDouble(1));
          appReport.setDisbursementDate(applicationResult.getDate(2));
          appReport.setFinalDisbursement(applicationResult.getString(3));
        }

        applicationResult.close();
        applicationResult = null;
        applicationStmt.close();
        applicationStmt = null;

        String query4 = " select tc.TCO_OUTSTANDING_AMOUNT from TC_OUTSTANDING_DETAIL tc  where tc.CGPAN = ? and tc.tco_outstanding_on_dt =(select max(tco_outstanding_on_dt) from  TC_OUTSTANDING_DETAIL where CGPAN = ?)";

        applicationStmt = connection.prepareStatement(query4);
        applicationStmt.setString(1, cgpan1);
        applicationStmt.setString(2, cgpan1);
        applicationResult = applicationStmt.executeQuery();

        while (applicationResult.next())
        {
          appReport.setTcOutstanding(applicationResult.getDouble(1));
        }
        applicationResult.close();
        applicationResult = null;
        applicationStmt.close();
        applicationStmt = null;

        String query5 = " select clm_ref_no from claim_detail c, application_detail app where c.MEM_BNK_ID||c.MEM_ZNE_ID||c.MEM_BRN_ID=app.MEM_BNK_ID||app.MEM_ZNE_ID||app.MEM_BRN_ID and app.cgpan=LTRIM(RTRIM(UPPER(?)))";

        applicationStmt = connection.prepareStatement(query5);
        applicationStmt.setString(1, cgpan1);
        applicationResult = applicationStmt.executeQuery();

        while (applicationResult.next())
        {
          claimRefNumberStringArray = new String[1];
          claimRefNumberStringArray[0] = applicationResult.getString(1);
        }
        applicationResult.close();
        applicationResult = null;
        applicationStmt.close();
        applicationStmt = null;

        String query6 = " SELECT A.APP_REF_NO, A.SSI_REFERENCE_NUMBER, A.APP_MLI_BRANCH_CODE, A.APP_BANK_APP_REF_NO,A.APP_COMPOSITE_LOAN,A.USR_ID,A.APP_LOAN_TYPE, A.APP_COLLATERAL_SECURITY_TAKEN, A.APP_THIRD_PARTY_GUAR_TAKEN, A.APP_SUBSIDY_SCHEME_NAME,A.APP_REHABILITATION, A.app_approved_date_time, A.app_approved_amount, A.app_guarantee_fee,A.APP_GUAR_START_DATE_TIME, D.DCI_AMOUNT_RAISED,D.DCI_APPROPRIATION_FLAG,E.DBR_AMOUNT,E.DBR_FINAL_DISBURSEMENT_FLAG, E.DBR_DT, E.DBR_CREATED_MODIFIED_BY,E.DBR_CREATED_MODIFIED_DT  FROM application_detail A,DAN_CGPAN_INFO D,DISBURSEMENT_DETAIL E where A.CGPAN=D.CGPAN AND A.APP_REF_NO=E.APP_REF_NO AND D.DCI_APPROPRIATION_FLAG='Y' AND A.cgpan=LTRIM(RTRIM(UPPER(?)))";

        applicationStmt = connection.prepareStatement(query6);
        applicationStmt.setString(1, cgpan1);
        applicationResult = applicationStmt.executeQuery();

        while (applicationResult.next())
        {
          appReport.setAppRefNo(applicationResult.getString(1));
          appReport.setSsiReferenceNumber(applicationResult.getString(2));
          appReport.setAppMliBranchCode(applicationResult.getString(3));
          appReport.setAppBankAppRefNo(applicationResult.getString(4));
          appReport.setAppCompositeLoan(applicationResult.getString(5));
          appReport.setUsrId(applicationResult.getString(6));
          appReport.setAppLoanType(applicationResult.getString(7));
          appReport.setAppCollateralSecurityTaken(applicationResult.getString(8));
          appReport.setAppThirdPartyGuarTaken(applicationResult.getString(9));
          appReport.setAppSubsidySchemeName(applicationResult.getString(10));
          appReport.setAppRehabilitation(applicationResult.getString(11));
          appReport.setAppApprovedDateTime(applicationResult.getDate(12));
          appReport.setAppApprovedAmount(applicationResult.getString(13));
          appReport.setAppGuaranteeFee(applicationResult.getString(14));
          appReport.setAppGuarStartDateTime(applicationResult.getDate(15));
          appReport.setDciAmountRaised(applicationResult.getString(16));
          appReport.setDciAppropriationFlag(applicationResult.getString(17));
          appReport.setDbrAmount(applicationResult.getString(18));
          appReport.setDbrFinalDisbursementFlag(applicationResult.getString(19));
          appReport.setDbrDt(applicationResult.getDate(20));
          appReport.setDbrCreatedModifiedBy(applicationResult.getString(21));
          appReport.setDbrCreatedModifiedDt(applicationResult.getDate(22));
        }

        applicationResult.close();
        applicationResult = null;
        applicationStmt.close();
        applicationStmt = null;
      }
      catch (Exception e)
      {
        System.out.println("Excetion generating during state fill" + e.getMessage());
      }
      finally
      {
        DBConnection.freeConnection(connection);
      }
      String s = appReport.getCgpan();
      if ((s == null) || (s.equals("")))
      {
        throw new NoDataException("No Data is available for the values entered, Please Enter Any Other Value ");
      }

    }
    else
    {
      throw new NoDataException("No Data is available for the values entered, Please Enter Any Other Value ");
    }

    claimForm.setAppReport(appReport);
    int total1 = claimRefNumberArray.size();
    String total = new Integer(total1).toString();
    if ((total != "") && (total != "0"))
    {
      for (int count = 0; count < claimRefNumberArray.size(); count++)
      {
        claimRefNumber = claimRefNumberStringArray[0];
        ClaimApplication claimapplication = reportDao.displayClmRefNumberDetail(claimRefNumber, status, memberId);
        ArrayList clmSummryDtls = claimapplication.getClaimSummaryDtls();
        User userInfo = getUserInformation(request);
        if (claimapplication.getFirstInstallment())
        {
          String thiscgpn = null;
          String bid = claimapplication.getBorrowerId();
          String memId = claimapplication.getMemberId();
          Vector cgpnDetails = processor.getCGPANDetailsForBorrowerId(bid, memId);
          ArrayList updateClmDtls = new ArrayList();
          for (int j = 0; j < clmSummryDtls.size(); j++)
          {
            ClaimSummaryDtls csdtl = (ClaimSummaryDtls)clmSummryDtls.remove(j);
            if (csdtl != null)
            {
              String pan = csdtl.getCgpan();
              for (int i = 0; i < cgpnDetails.size(); i++)
              {
                double apprvdAmnt = 0.0D;
                HashMap dtl = (HashMap)cgpnDetails.remove(i);
                if (dtl != null)
                {
                  thiscgpn = (String)dtl.get("CGPAN");
                  if (thiscgpn != null)
                  {
                    if (thiscgpn.equals(pan))
                    {
                      Double db = (Double)dtl.get("ApprovedAmount");
                      if (db != null)
                      {
                        apprvdAmnt = db.doubleValue();
                      }
                      csdtl.setLimitCoveredUnderCGFSI(String.valueOf(apprvdAmnt));
                      updateClmDtls.add(csdtl);
                    }
                    dtl = null;
                  }
                }
              }
            }
          }
          claimapplication.setClaimSummaryDtls(updateClmDtls);
          claimForm.setClaimapplication(claimapplication);
          request.setAttribute("claimRefNumber", claimRefNumber);

          return mapping.findForward("showFirstClmDetails");
        }
      }
    }
    Log.log(4, "HistoryReportsAction", "historyReportDetails", "Exited");

    return mapping.findForward("success");
  }
}