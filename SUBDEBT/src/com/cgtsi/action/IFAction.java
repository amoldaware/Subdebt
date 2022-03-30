package com.cgtsi.action;

import com.cgtsi.actionform.InvestmentForm;
import com.cgtsi.admin.MenuOptions;
import com.cgtsi.admin.User;
import com.cgtsi.common.DatabaseException;
import com.cgtsi.common.Log;
import com.cgtsi.common.MessageException;
import com.cgtsi.common.NoDataException;
import com.cgtsi.guaranteemaintenance.CgpanDetail;
import com.cgtsi.guaranteemaintenance.GMProcessor;
import com.cgtsi.investmentfund.ActualIOHeadDetail;
import com.cgtsi.investmentfund.ActualIOSubHeadDetail;
import com.cgtsi.investmentfund.ActualInflowOutflowDetails;
import com.cgtsi.investmentfund.BankAccountDetail;
import com.cgtsi.investmentfund.BankReconcilation;
import com.cgtsi.investmentfund.BondsDetail;
import com.cgtsi.investmentfund.BudgetDetails;
import com.cgtsi.investmentfund.BudgetHead;
import com.cgtsi.investmentfund.BudgetHeadDetails;
import com.cgtsi.investmentfund.BudgetSubHead;
import com.cgtsi.investmentfund.BudgetSubHeadDetails;
import com.cgtsi.investmentfund.BuySellDetail;
import com.cgtsi.investmentfund.ChequeDetails;
import com.cgtsi.investmentfund.ChequeLeavesDetails;
import com.cgtsi.investmentfund.CommercialPaperDetail;
import com.cgtsi.investmentfund.CorpusDetail;
import com.cgtsi.investmentfund.Dates;
import com.cgtsi.investmentfund.DebentureDetail;
import com.cgtsi.investmentfund.ExposureDetails;
import com.cgtsi.investmentfund.FDDetail;
import com.cgtsi.investmentfund.ForecastDetails;
import com.cgtsi.investmentfund.ForecastHeadDetails;
import com.cgtsi.investmentfund.ForecastSubHeadDetails;
import com.cgtsi.investmentfund.FundTransferDetail;
import com.cgtsi.investmentfund.GovtSecurityDetail;
import com.cgtsi.investmentfund.IFDAO;
import com.cgtsi.investmentfund.IFProcessor;
import com.cgtsi.investmentfund.InflowOutflowReport;
import com.cgtsi.investmentfund.InstrumentCategory;
import com.cgtsi.investmentfund.InstrumentCategoryWiseCeiling;
import com.cgtsi.investmentfund.InstrumentDetail;
import com.cgtsi.investmentfund.InstrumentFeature;
import com.cgtsi.investmentfund.InvesteeDetail;
import com.cgtsi.investmentfund.InvesteeGroupWiseCeiling;
import com.cgtsi.investmentfund.InvesteeGrpDetail;
import com.cgtsi.investmentfund.InvesteeWiseCeiling;
import com.cgtsi.investmentfund.InvestmentDetails;
import com.cgtsi.investmentfund.InvestmentFulfillmentDetail;
import com.cgtsi.investmentfund.InvestmentMaturityDetails;
import com.cgtsi.investmentfund.InvestmentPlanningDetail;
import com.cgtsi.investmentfund.MaturityDetail;
import com.cgtsi.investmentfund.MaturityWiseCeiling;
import com.cgtsi.investmentfund.MiscReceipts;
import com.cgtsi.investmentfund.MutualFundDetail;
import com.cgtsi.investmentfund.PaymentDetails;
import com.cgtsi.investmentfund.PlanInvestment;
import com.cgtsi.investmentfund.ProjectExpectedClaimDetail;
import com.cgtsi.investmentfund.RatingDetails;
import com.cgtsi.investmentfund.RatingWiseCeiling;
import com.cgtsi.investmentfund.StatementDetail;
import com.cgtsi.investmentfund.TDSDetail;
import com.cgtsi.investmentfund.TransactionDetail;
import com.cgtsi.investmentfund.XMLParser;
import com.cgtsi.util.CustomisedDate;
import com.cgtsi.util.PropertyLoader;
import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.PrintStream;
import java.text.DecimalFormat;
import java.text.ParsePosition;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Hashtable;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;
import java.util.TreeMap;
import java.util.Vector;
import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import org.apache.commons.beanutils.BeanUtils;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.action.DynaActionForm;
import org.apache.struts.upload.FormFile;

public class IFAction extends BaseAction
{
  public ActionForward updateAllowableRatingsForAgency(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response)
    throws Exception
  {
    Log.log(4, "IFAction", "updateAllowableRatingsForAgency", "Entered");
    DynaActionForm dynaForm = (DynaActionForm)form;
    IFProcessor ifProcessor = new IFProcessor();
    RatingDetails ratingDetails = null;
    ArrayList ratingDetailsArray = new ArrayList();

    String agencyName = (String)dynaForm.get("ratingAgency");

    String[] ratingName = (String[])dynaForm.get("allowableRating");
    ArrayList ratings = ifProcessor.getAllRatings();
    int ratingsSize = ratings.size();

    for (int j = 0; j < ratingsSize; j++)
    {
      String mainRating = (String)ratings.get(j);

      for (int i = 0; i < ratingName.length; i++)
      {
        if (mainRating.equals(ratingName[i]))
        {
          ratingDetails = new RatingDetails();
          ratingDetails.setRating(mainRating);
          ratingDetails.setStatus("A");
          ratingDetails.setRatingAgency(agencyName);
          break;
        }

        ratingDetails = new RatingDetails();
        ratingDetails.setStatus("I");
      }

      if (ratingDetails.getStatus().equals("I"))
      {
        ratingDetails = new RatingDetails();
        ratingDetails.setRating(mainRating);
        ratingDetails.setStatus("I");
        ratingDetails.setRatingAgency(agencyName);
      }
      ratingDetailsArray.add(ratingDetails);
     int i = ratingDetailsArray.size();
    }
    ArrayList agencyNames = ifProcessor.showRatingAgencyWithRatings();
    if (agencyNames.contains(agencyName))
    {
      ifProcessor.updateAllowableRatingsForAgency(ratingDetailsArray);
      request.setAttribute("message", "Rating Agency Details Updated Successfully");
    }
    else
    {
      ifProcessor.insertAllowableRatingsForAgency(ratingDetailsArray);
      request.setAttribute("message", "Rating Agency Details Inserted Successfully");
    }
    Log.log(4, "IFAction", "updateAllowableRatingsForAgency", "Exited");

    return mapping.findForward("success");
  }

  public ActionForward showAllowableRatingsForAgency(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response)
    throws Exception
  {
    Log.log(4, "IFAction", "showAllowableRatingsForAgency", "Entered");
    HttpSession session = request.getSession(false);
    session.setAttribute("statusFlag", "0");

    ArrayList agencyNames = new ArrayList();
    ArrayList ratings = new ArrayList();

    DynaActionForm dynaForm = (DynaActionForm)form;
    dynaForm.initialize(mapping);
    IFProcessor ifProcessor = new IFProcessor();
    agencyNames = ifProcessor.showRatingAgency();
    dynaForm.set("agencies", agencyNames);
    ratings = ifProcessor.getAllRatings();
    dynaForm.set("allowableRatings", ratings);

    Log.log(4, "IFAction", "showAllowableRatingsForAgency", "Exited");

    return mapping.findForward("success");
  }

  public ActionForward showAllowableRatingsForAgencyDetails(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response)
    throws Exception
  {
    Log.log(4, "IFAction", "showAllowableRatingsForAgencyDetails", "Entered");
    IFProcessor ifProcessor = new IFProcessor();
    HttpSession session = request.getSession(false);
    session.setAttribute("statusFlag", "1");

    ArrayList agencyNames = new ArrayList();
    ArrayList ratings = new ArrayList();

    DynaActionForm dynaForm = (DynaActionForm)form;
    String agencyName = (String)dynaForm.get("ratingAgency");
    ratings = ifProcessor.getAllRatings();
    dynaForm.set("allowableRatings", ratings);

    if ((agencyName != null) && (!agencyName.equals("")))
    {
      agencyNames = ifProcessor.getRatingsForAgency(agencyName);

      String[] agencyArr = new String[agencyNames.size()];
      for (int i = 0; i < agencyNames.size(); i++)
      {
        agencyArr[i] = ((String)agencyNames.get(i));
      }

      dynaForm.set("allowableRating", agencyArr);
    }
    else {
      dynaForm.set("allowableRatings", ratings);
    }

    Log.log(4, "IFAction", "showAllowableRatingsForAgencyDetails", "Exited");

    return mapping.findForward("success");
  }

  public ActionForward updateRatingAgency(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response)
    throws Exception
  {
    Log.log(4, "IFAction", "updateRatingAgency", "Entered");
    DynaActionForm dynaForm = (DynaActionForm)form;
    IFProcessor ifProcessor = new IFProcessor();
    RatingDetails ratingDetails = new RatingDetails();
    BeanUtils.populate(ratingDetails, dynaForm.getMap());

    String oldAgency = ratingDetails.getAgency();

    String agency = (String)dynaForm.get("newAgency");

    User creatingUser = getUserInformation(request);
    String user = creatingUser.getUserId();
    ratingDetails.setUser(user);

    String desc = ratingDetails.getModAgencyDesc();

    String modName = ratingDetails.getModAgencyName();

    if ((agency == null) || (agency.equals("")))
    {
      ifProcessor.updateRatingAgency(ratingDetails);
      Log.log(4, "IFAction", "updateRatingAgency", "Exited");
      request.setAttribute("message", "Rating Agency Details Updated Successfully");

      return mapping.findForward("success");
    }

    ifProcessor.insertRatingAgency(ratingDetails);
    Log.log(4, "IFAction", "updateRatingAgency", "Exited");

    request.setAttribute("message", "Rating Agency Details Inserted Successfully");

    return mapping.findForward("success");
  }

  public ActionForward showRatingAgencyDetails(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response)
    throws Exception
  {
    Log.log(4, "IFAction", "showRatingAgencyDetails", "Entered");

    HttpSession session = request.getSession(false);
    session.setAttribute("statusFlag", "1");

    DynaActionForm dynaForm = (DynaActionForm)form;
    String agencyName = (String)dynaForm.get("agency");

    RatingDetails ratDtl = new RatingDetails();
    IFProcessor ifProcessor = new IFProcessor();
    Log.log(4, "IFAction", "showRatingAgencyDetails", "mat type " + agencyName);
    if ((agencyName != null) && (!agencyName.equals("")))
    {
      ratDtl = ifProcessor.showRatingAgencyDetails(agencyName);

      dynaForm.set("modAgencyName", ratDtl.getRatingAgency());
      dynaForm.set("modAgencyDesc", ratDtl.getRatingDescription());
      dynaForm.set("newAgency", "");
    }
    else
    {
      dynaForm.set("modAgencyName", "");
      dynaForm.set("modAgencyDesc", "");
      dynaForm.set("newAgency", "");
    }

    Log.log(4, "IFAction", "showRatingAgencyDetails", "Exited");

    return mapping.findForward("success");
  }

  public ActionForward showRatingAgency(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response)
    throws Exception
  {
    Log.log(4, "IFAction", "showRatingAgency", "Entered");
    HttpSession session = request.getSession(false);
    session.setAttribute("statusFlag", "0");

    ArrayList agencyNames = new ArrayList();
    DynaActionForm dynaForm = (DynaActionForm)form;
    dynaForm.initialize(mapping);
    dynaForm.initialize(mapping);
    IFProcessor ifProcessor = new IFProcessor();
    agencyNames = ifProcessor.showRatingAgency();

    dynaForm.set("agencies", agencyNames);
    Log.log(4, "IFAction", "showRatingAgency", "Exited");

    return mapping.findForward("success");
  }

  public ActionForward bankStatementUploadResult(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response)
    throws Exception
  {
    Log.log(4, "IFAction", "bankStatementUploadResult", "Entered");
    IFProcessor ifProcessor = new IFProcessor();
    DynaActionForm dynaForm = (DynaActionForm)form;
    XMLParser xmlParser = new XMLParser();
    HttpSession session = request.getSession(false);
    StatementDetail statementDetail = new StatementDetail();
    String path = "";
    User creatingUser = getUserInformation(request);
    String user = creatingUser.getUserId();

    FormFile formFile = (FormFile)dynaForm.get("bankStatementUploadFile");

    String contextPath1 = request.getSession(false).getServletContext().getRealPath("");
    String contextPath = PropertyLoader.changeToOSpath(contextPath1);

    String fileName = String.valueOf(contextPath) + File.separator + "Download" + File.separator + formFile.getFileName();

    FileOutputStream fileOut = new FileOutputStream(fileName);
    InputStream input = formFile.getInputStream();
    int readByte = 0;
    byte[] buffer = new byte[1024];
    while ((readByte = input.read(buffer, 0, buffer.length)) != -1)
    {
      fileOut.write(buffer, 0, readByte);
    }

    buffer = null;
    fileOut.flush();
    fileOut.close();
    input.close();
    formFile.destroy();
    File file = new File(fileName);
    String abPath = file.getAbsolutePath();

    statementDetail = xmlParser.xmlParse(abPath);
    statementDetail.setUserId(user);

    ifProcessor.bankStatementUploadResult(statementDetail);

    Log.log(4, "IFAction", "bankStatementUploadResult", "Exited");
    request.setAttribute("message", "Bank Statement Uploaded successfully");

    return mapping.findForward("success");
  }

  public ActionForward accruedInterestIncomeReportInput(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response)
    throws Exception
  {
    Log.log(4, "IFAction", "accruedInterestIncomeReportInput", "Entered");
    DynaActionForm dynaForm = (DynaActionForm)form;
    java.util.Date date = new java.util.Date();
    Calendar calendar = Calendar.getInstance();
    Dates dates = new Dates();
    dates.setDateOfTheDocument17(date);
    BeanUtils.copyProperties(dynaForm, dates);

    Log.log(4, "IFAction", "accruedInterestIncomeReportInput", "Exited");

    return mapping.findForward("success");
  }

  public ActionForward accruedInterestIncomeReport(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response)
    throws Exception
  {
    Log.log(4, "IFAction", "accruedInterestIncomeReport", "Entered");
    DynaActionForm dynaForm = (DynaActionForm)form;
    ArrayList dan = new ArrayList();
    IFProcessor ifProcessor = new IFProcessor();
    java.sql.Date endDate = null;
    java.util.Date eDate = (java.util.Date)dynaForm.get("dateOfTheDocument17");
    endDate = new java.sql.Date(eDate.getTime());
    dan = ifProcessor.accruedInterestIncomeReport(endDate);
    dynaForm.set("mliDetails", dan);

    if ((dan == null) || (dan.size() == 0))
    {
      throw new NoDataException("No Data is available for the values entered");
    }

    Log.log(4, "IFAction", "accruedInterestIncomeReport", "Exited");

    return mapping.findForward("success");
  }

  public ActionForward investeeWiseReportInput(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response)
    throws Exception
  {
    Log.log(4, "IFAction", "investeeWiseReportInput", "Entered");

    DynaActionForm dynaForm = (DynaActionForm)form;
    java.util.Date date = new java.util.Date();
    Calendar calendar = Calendar.getInstance();
    calendar.setTime(date);
    int month = calendar.get(2);
    int day = calendar.get(5);
    month -= 1;
    day += 1;
    calendar.set(2, month);
    calendar.set(5, day);
    java.util.Date prevDate = calendar.getTime();

    Dates generalReport = new Dates();
    generalReport.setDateOfTheDocument15(prevDate);
    generalReport.setDateOfTheDocument16(date);
    BeanUtils.copyProperties(dynaForm, generalReport);
    Log.log(4, "IFAction", "investeeWiseReportInput", "Exited");

    return mapping.findForward("success");
  }

  public ActionForward investeeWiseReport(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response)
    throws Exception
  {
    Log.log(4, "IFAction", "investeeWiseReport", "Entered");

    ArrayList fdReport = new ArrayList();
    IFProcessor ifProcessor = new IFProcessor();
    DynaActionForm dynaForm = (DynaActionForm)form;
    java.sql.Date startDate = null;
    java.sql.Date endDate = null;
    java.util.Date sDate = (java.util.Date)dynaForm.get("dateOfTheDocument15");
    String stDate = String.valueOf(sDate);
    if ((stDate == null) || (stDate.equals("")))
    {
      startDate = null;
    }
    else if (stDate != null)
    {
      startDate = new java.sql.Date(sDate.getTime());
    }
    java.util.Date eDate = (java.util.Date)dynaForm.get("dateOfTheDocument16");
    endDate = new java.sql.Date(eDate.getTime());

    fdReport = ifProcessor.getFdReportForMaturity(startDate, endDate);
    dynaForm.set("fdReport", fdReport);
    if ((fdReport == null) || (fdReport.size() == 0))
    {
      throw new NoDataException("No Data is available for the values entered, Please Enter Any Other Value ");
    }

    Log.log(4, "IFAction", "investeeWiseReport", "Exited");

    return mapping.findForward("success");
  }

  public ActionForward investeeWiseReportDetails(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response)
    throws Exception
  {
    Log.log(4, "IFAction", "investeeWiseReportDetails", "Entered");
    ArrayList fdReceiptDetails = new ArrayList();
    IFProcessor ifProcessor = new IFProcessor();
    DynaActionForm dynaForm = (DynaActionForm)form;
    String investee = request.getParameter("investee");
    java.sql.Date startDate = null;
    java.sql.Date endDate = null;
    java.util.Date sDate = (java.util.Date)dynaForm.get("dateOfTheDocument15");
    String stDate = String.valueOf(sDate);
    if ((stDate == null) || (stDate.equals("")))
    {
      startDate = null;
    }
    else if (stDate != null)
    {
      startDate = new java.sql.Date(sDate.getTime());
    }
    java.util.Date eDate = (java.util.Date)dynaForm.get("dateOfTheDocument16");
    endDate = new java.sql.Date(eDate.getTime());

    fdReceiptDetails = ifProcessor.investeeWiseReportDetails(investee, startDate, endDate);
    dynaForm.set("fdReport", fdReceiptDetails);

    if ((fdReceiptDetails == null) || (fdReceiptDetails.size() == 0))
    {
      throw new NoDataException("No Data is available for the values entered, Please Enter Any Other Value ");
    }

    Log.log(4, "IFAction", "investeeWiseReportDetails", "Exited");

    return mapping.findForward("success");
  }

  public ActionForward investeeWiseReportDetailsSummary(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response)
    throws Exception
  {
    Log.log(4, "IFAction", "investeeWiseReportDetailsSummary", "Entered");
    ArrayList fdReceiptDetails = new ArrayList();
    IFProcessor ifProcessor = new IFProcessor();
    DynaActionForm dynaForm = (DynaActionForm)form;
    String investee = request.getParameter("investee");
    java.sql.Date startDate = null;
    java.sql.Date endDate = null;
    java.util.Date sDate = (java.util.Date)dynaForm.get("dateOfTheDocument15");
    String stDate = String.valueOf(sDate);
    if ((stDate == null) || (stDate.equals("")))
    {
      startDate = null;
    }
    else if (stDate != null)
    {
      startDate = new java.sql.Date(sDate.getTime());
    }
    java.util.Date eDate = (java.util.Date)dynaForm.get("dateOfTheDocument16");
    endDate = new java.sql.Date(eDate.getTime());

    fdReceiptDetails = ifProcessor.investeeWiseReportDetailsSummary(investee, startDate, endDate);
    dynaForm.set("fdReport", fdReceiptDetails);

    if ((fdReceiptDetails == null) || (fdReceiptDetails.size() == 0))
    {
      throw new NoDataException("No Data is available for the values entered, Please Enter Any Other Value ");
    }

    Log.log(4, "IFAction", "investeeWiseReportDetailsSummary", "Exited");

    return mapping.findForward("success");
  }

  public ActionForward chequeDetailsUpdateSuccessForPayment(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response)
    throws Exception
  {
    Log.log(4, "IFAction", "chequeDetailsUpdateSuccessForPayment", "Entered");
    IFProcessor ifProcessor = new IFProcessor();
    ChequeDetails chequeDetails = new ChequeDetails();
    DynaActionForm dynaForm = (DynaActionForm)form;
    String chequeNumber = (String)dynaForm.get("number");
    User creatingUser = getUserInformation(request);
    String user = creatingUser.getUserId();
    BeanUtils.populate(chequeDetails, dynaForm.getMap());
    String bank = (String)dynaForm.get("bankName");

    int i = bank.indexOf(",");

    String newBank = bank.substring(0, i);

    chequeDetails.setBankName(newBank);

    int j = bank.indexOf("(");

    String newBranch = bank.substring(i + 1, j);

    chequeDetails.setBranchName(newBranch);

    chequeDetails.setUserId(user);
    ifProcessor.chequeDetailsUpdateSuccess(chequeDetails, chequeNumber);

    Log.log(4, "IFAction", "chequeDetailsUpdateSuccessForPayment", "Exited");

    return mapping.findForward("success");
  }

  public ActionForward fdiReportInput(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response)
    throws Exception
  {
    Log.log(4, "IFAction", "fdiReportInput", "Entered");

    DynaActionForm dynaForm = (DynaActionForm)form;
    java.util.Date date = new java.util.Date();
    Calendar calendar = Calendar.getInstance();
    calendar.setTime(date);
    int month = calendar.get(2);
    int day = calendar.get(5);
    month -= 1;
    day += 1;
    calendar.set(2, month);
    calendar.set(5, day);
    java.util.Date prevDate = calendar.getTime();

    Dates generalReport = new Dates();
    generalReport.setDateOfTheDocument(prevDate);
    generalReport.setDateOfTheDocument1(date);
    BeanUtils.copyProperties(dynaForm, generalReport);
    Log.log(4, "IFAction", "fdiReportInput", "Exited");

    return mapping.findForward("success");
  }

  public ActionForward chequeDetailsReportInput(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response)
    throws Exception
  {
    Log.log(4, "IFAction", "chequeDetailsReportInput", "Entered");

    DynaActionForm dynaForm = (DynaActionForm)form;
    java.util.Date date = new java.util.Date();
    Calendar calendar = Calendar.getInstance();
    calendar.setTime(date);
    int month = calendar.get(2);
    int day = calendar.get(5);
    month -= 1;
    day += 1;
    calendar.set(2, month);
    calendar.set(5, day);
    java.util.Date prevDate = calendar.getTime();

    Dates generalReport = new Dates();
    generalReport.setDateOfTheDocument2(prevDate);
    generalReport.setDateOfTheDocument3(date);
    BeanUtils.copyProperties(dynaForm, generalReport);
    Log.log(4, "IFAction", "chequeDetailsReportInput", "Exited");

    return mapping.findForward("success");
  }

  public ActionForward investmentReportInput(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response)
    throws Exception
  {
    Log.log(4, "IFAction", "investmentReportInput", "Entered");

    DynaActionForm dynaForm = (DynaActionForm)form;
    java.util.Date date = new java.util.Date();
    Calendar calendar = Calendar.getInstance();
    calendar.setTime(date);
    int month = calendar.get(2);
    int day = calendar.get(5);
    month -= 1;
    day += 1;
    calendar.set(2, month);
    calendar.set(5, day);
    java.util.Date prevDate = calendar.getTime();

    Dates generalReport = new Dates();
    generalReport.setDateOfTheDocument5(prevDate);
    generalReport.setDateOfTheDocument6(date);
    BeanUtils.copyProperties(dynaForm, generalReport);
    Log.log(4, "IFAction", "investmentReportInput", "Exited");

    return mapping.findForward("success");
  }

  public ActionForward investmentMaturityReportInput(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response)
    throws Exception
  {
    Log.log(4, "IFAction", "investmentMaturityReportInput", "Entered");
    DynaActionForm dynaForm = (DynaActionForm)form;
    java.util.Date date = new java.util.Date();
    Calendar calendar = Calendar.getInstance();
    Dates dates = new Dates();
    dates.setDateOfTheDocument7(date);
    BeanUtils.copyProperties(dynaForm, dates);

    Log.log(4, "IFAction", "investmentMaturityReportInput", "Exited");

    return mapping.findForward("display");
  }

  public ActionForward investmentMaturityReport(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response)
    throws Exception
  {
    Log.log(4, "IFAction", "investmentMaturityReport", "Entered");

    ArrayList investment = new ArrayList();
    IFProcessor ifProcessor = new IFProcessor();
    DynaActionForm dynaForm = (DynaActionForm)form;
    InvestmentDetails investmentDetails = new InvestmentDetails();

    java.sql.Date startDate = null;
    java.util.Date sDate = (java.util.Date)dynaForm.get("dateOfTheDocument7");
    String stDate = String.valueOf(sDate);
    if ((stDate == null) || (stDate.equals("")))
    {
      startDate = null;
    }
    else if (stDate != null)
    {
      startDate = new java.sql.Date(sDate.getTime());
      dynaForm.set("documentDate", startDate);
    }

    Calendar calendar = Calendar.getInstance();
    calendar.setTime(startDate);
    int month = calendar.get(2);
    int day = calendar.get(5);
    int year = calendar.get(1);

    day += 15;
    calendar.set(5, day);
    java.util.Date date1 = calendar.getTime();
    java.sql.Date sqlDate1 = new java.sql.Date(date1.getTime());
    dynaForm.set("documentDate1", date1);

    day += 1;
    calendar.set(5, day);
    java.util.Date date2 = calendar.getTime();
    java.sql.Date sqlDate2 = new java.sql.Date(date2.getTime());
    dynaForm.set("documentDate2", date2);

    day += 14;
    calendar.set(5, day);
    java.util.Date date3 = calendar.getTime();
    java.sql.Date sqlDate3 = new java.sql.Date(date3.getTime());
    dynaForm.set("documentDate3", date3);

    day += 1;
    calendar.set(5, day);
    java.util.Date date4 = calendar.getTime();
    java.sql.Date sqlDate4 = new java.sql.Date(date4.getTime());
    dynaForm.set("documentDate4", date4);

    month += 2;
    day -= 1;
    calendar.set(5, day);
    calendar.set(2, month);
    java.util.Date date5 = calendar.getTime();
    java.sql.Date sqlDate5 = new java.sql.Date(date5.getTime());
    dynaForm.set("documentDate5", date5);

    day += 1;
    calendar.set(5, day);
    java.util.Date date6 = calendar.getTime();
    java.sql.Date sqlDate6 = new java.sql.Date(date6.getTime());
    dynaForm.set("documentDate6", date6);

    month += 3;
    day -= 1;
    calendar.set(5, day);
    calendar.set(2, month);
    java.util.Date date7 = calendar.getTime();
    java.sql.Date sqlDate7 = new java.sql.Date(date7.getTime());
    dynaForm.set("documentDate7", date7);

    day += 1;
    calendar.set(5, day);
    java.util.Date date8 = calendar.getTime();
    java.sql.Date sqlDate8 = new java.sql.Date(date8.getTime());
    dynaForm.set("documentDate8", date8);

    month += 6;
    day -= 1;
    calendar.set(5, day);
    calendar.set(2, month);
    java.util.Date date9 = calendar.getTime();
    java.sql.Date sqlDate9 = new java.sql.Date(date9.getTime());
    dynaForm.set("documentDate9", date9);

    day += 1;
    calendar.set(5, day);
    java.util.Date date10 = calendar.getTime();
    java.sql.Date sqlDate10 = new java.sql.Date(date10.getTime());
    dynaForm.set("documentDate10", date10);

    year += 2;
    day -= 1;
    calendar.set(5, day);
    calendar.set(1, year);
    java.util.Date date11 = calendar.getTime();
    java.sql.Date sqlDate11 = new java.sql.Date(date11.getTime());
    dynaForm.set("documentDate11", date11);

    day += 1;
    calendar.set(5, day);
    java.util.Date date12 = calendar.getTime();
    java.sql.Date sqlDate12 = new java.sql.Date(date12.getTime());
    dynaForm.set("documentDate12", date12);

    year += 2;
    day -= 1;
    calendar.set(5, day);
    calendar.set(1, year);
    java.util.Date date13 = calendar.getTime();
    java.sql.Date sqlDate13 = new java.sql.Date(date13.getTime());
    dynaForm.set("documentDate13", date13);

    day += 1;
    calendar.set(5, day);
    java.util.Date date14 = calendar.getTime();
    java.sql.Date sqlDate14 = new java.sql.Date(date14.getTime());
    dynaForm.set("documentDate14", date14);

    investment = ifProcessor.investmentMaturityReport(startDate, sqlDate1, sqlDate2, sqlDate3, sqlDate4, sqlDate5, sqlDate6, sqlDate7, sqlDate8, sqlDate9, sqlDate10, sqlDate11, sqlDate12, sqlDate13, sqlDate14);

    dynaForm.set("investmentArray", investment);

    if ((investment == null) || (investment.size() == 0))
    {
      throw new NoDataException("No Data is available for the values entered, Please Enter Any Other Value ");
    }

    Log.log(4, "IFAction", "investmentMaturityReport", "Exited");

    return mapping.findForward("success");
  }

  public ActionForward investmentMaturityReportDetails(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response)
    throws Exception
  {
    Log.log(4, "IFAction", "investmentMaturityReportDetails", "Entered");

    ArrayList investment = new ArrayList();
    IFProcessor ifProcessor = new IFProcessor();
    DynaActionForm dynaForm = (DynaActionForm)form;
    InvestmentDetails investmentDetails = new InvestmentDetails();
    String instrument = (String)dynaForm.get("number");

    String range = (String)dynaForm.get("flag");

    java.sql.Date startDate = null;
    java.util.Date sDate = (java.util.Date)dynaForm.get("dateOfTheDocument7");
    String stDate = String.valueOf(sDate);
    if ((stDate == null) || (stDate.equals("")))
    {
      startDate = null;
    }
    else if (stDate != null)
    {
      startDate = new java.sql.Date(sDate.getTime());
    }

    Calendar calendar = Calendar.getInstance();
    calendar.setTime(startDate);
    int month = calendar.get(2);
    int day = calendar.get(5);
    int year = calendar.get(1);

    day += 15;
    calendar.set(5, day);
    java.util.Date date1 = calendar.getTime();
    java.sql.Date sqlDate1 = new java.sql.Date(date1.getTime());

    day += 1;
    calendar.set(5, day);
    java.util.Date date2 = calendar.getTime();
    java.sql.Date sqlDate2 = new java.sql.Date(date2.getTime());

    day += 14;
    calendar.set(5, day);
    java.util.Date date3 = calendar.getTime();
    java.sql.Date sqlDate3 = new java.sql.Date(date3.getTime());

    day += 1;
    calendar.set(5, day);
    java.util.Date date4 = calendar.getTime();
    java.sql.Date sqlDate4 = new java.sql.Date(date4.getTime());

    month += 2;
    day -= 1;
    calendar.set(5, day);
    calendar.set(2, month);
    java.util.Date date5 = calendar.getTime();
    java.sql.Date sqlDate5 = new java.sql.Date(date5.getTime());

    day += 1;
    calendar.set(5, day);
    java.util.Date date6 = calendar.getTime();
    java.sql.Date sqlDate6 = new java.sql.Date(date6.getTime());

    month += 3;
    day -= 1;
    calendar.set(5, day);
    calendar.set(2, month);
    java.util.Date date7 = calendar.getTime();
    java.sql.Date sqlDate7 = new java.sql.Date(date7.getTime());

    day += 1;
    calendar.set(5, day);
    java.util.Date date8 = calendar.getTime();
    java.sql.Date sqlDate8 = new java.sql.Date(date8.getTime());

    month += 6;
    day -= 1;
    calendar.set(5, day);
    calendar.set(2, month);
    java.util.Date date9 = calendar.getTime();
    java.sql.Date sqlDate9 = new java.sql.Date(date9.getTime());

    day += 1;
    calendar.set(5, day);
    java.util.Date date10 = calendar.getTime();
    java.sql.Date sqlDate10 = new java.sql.Date(date10.getTime());

    year += 2;
    day -= 1;
    calendar.set(5, day);
    calendar.set(1, year);
    java.util.Date date11 = calendar.getTime();
    java.sql.Date sqlDate11 = new java.sql.Date(date11.getTime());

    day += 1;
    calendar.set(5, day);
    java.util.Date date12 = calendar.getTime();
    java.sql.Date sqlDate12 = new java.sql.Date(date12.getTime());

    year += 2;
    day -= 1;
    calendar.set(5, day);
    calendar.set(1, year);
    java.util.Date date13 = calendar.getTime();
    java.sql.Date sqlDate13 = new java.sql.Date(date13.getTime());

    day += 1;
    calendar.set(5, day);
    java.util.Date date14 = calendar.getTime();
    java.sql.Date sqlDate14 = new java.sql.Date(date14.getTime());

    if (range.equals("range1"))
    {
      investment = ifProcessor.investmentMaturityReportDetails(startDate, sqlDate1, instrument);
    }
    else if (range.equals("range2"))
    {
      investment = ifProcessor.investmentMaturityReportDetails(sqlDate2, sqlDate3, instrument);
    }
    else if (range.equals("range3"))
    {
      investment = ifProcessor.investmentMaturityReportDetails(sqlDate4, sqlDate5, instrument);
    }
    else if (range.equals("range4"))
    {
      investment = ifProcessor.investmentMaturityReportDetails(sqlDate6, sqlDate7, instrument);
    }
    else if (range.equals("range5"))
    {
      investment = ifProcessor.investmentMaturityReportDetails(sqlDate8, sqlDate9, instrument);
    }
    else if (range.equals("range6"))
    {
      investment = ifProcessor.investmentMaturityReportDetails(sqlDate10, sqlDate11, instrument);
    }
    else if (range.equals("range7"))
    {
      investment = ifProcessor.investmentMaturityReportDetails(sqlDate12, sqlDate13, instrument);
    }
    else
    {
      investment = ifProcessor.investmentMaturityReportDetailsForEndDate(sqlDate14, instrument);
    }
    dynaForm.set("investmentArray", investment);

    if ((investment == null) || (investment.size() == 0))
    {
      throw new NoDataException("No Data is available for the values entered, Please Enter Any Other Value ");
    }

    Log.log(4, "IFAction", "investmentMaturityReportDetails", "Exited");

    return mapping.findForward("success");
  }

  public ActionForward investmentReport(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response)
    throws Exception
  {
    Log.log(4, "IFAction", "investmentReport", "Entered");
    ArrayList investmentDetails = new ArrayList();
    IFProcessor ifProcessor = new IFProcessor();
    DynaActionForm dynaForm = (DynaActionForm)form;
    java.sql.Date startDate = null;
    java.sql.Date endDate = null;
    java.util.Date sDate = (java.util.Date)dynaForm.get("dateOfTheDocument5");
    String stDate = String.valueOf(sDate);
    if ((stDate == null) || (stDate.equals("")))
    {
      startDate = null;
    }
    else if (stDate != null)
    {
      startDate = new java.sql.Date(sDate.getTime());
    }
    java.util.Date eDate = (java.util.Date)dynaForm.get("dateOfTheDocument6");
    endDate = new java.sql.Date(eDate.getTime());
    investmentDetails = ifProcessor.investmentReport(startDate, endDate);
    dynaForm.set("investmentArray", investmentDetails);

    if ((investmentDetails == null) || (investmentDetails.size() == 0))
    {
      throw new NoDataException("No Data is available for the values entered, Please Enter Any Other Value ");
    }

    Log.log(4, "IFAction", "investmentReport", "Exited");

    return mapping.findForward("success");
  }

  public ActionForward investmentReportDetails(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response)
    throws Exception
  {
    Log.log(4, "IFAction", "investmentReportDetails", "Entered");
    ArrayList investmentDetails = new ArrayList();
    IFProcessor ifProcessor = new IFProcessor();
    DynaActionForm dynaForm = (DynaActionForm)form;
    java.sql.Date startDate = null;
    java.sql.Date endDate = null;
    String type = (String)dynaForm.get("number");
    java.util.Date sDate = (java.util.Date)dynaForm.get("dateOfTheDocument5");
    String stDate = String.valueOf(sDate);
    if ((stDate == null) || (stDate.equals("")))
    {
      startDate = null;
    }
    else if (stDate != null)
    {
      startDate = new java.sql.Date(sDate.getTime());
    }
    java.util.Date eDate = (java.util.Date)dynaForm.get("dateOfTheDocument6");
    endDate = new java.sql.Date(eDate.getTime());
    investmentDetails = ifProcessor.investmentReportDetails(startDate, endDate, type);
    dynaForm.set("investmentArray", investmentDetails);

    if ((investmentDetails == null) || (investmentDetails.size() == 0))
    {
      throw new NoDataException("No Data is available for the values entered, Please Enter Any Other Value ");
    }

    Log.log(4, "IFAction", "investmentReportDetails", "Exited");

    return mapping.findForward("success");
  }

  public ActionForward investmentReportDetailsFinal(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response)
    throws Exception
  {
    Log.log(4, "IFAction", "investmentReportDetailsFinal", "Entered");
    ArrayList investmentDetails = new ArrayList();
    IFProcessor ifProcessor = new IFProcessor();
    DynaActionForm dynaForm = (DynaActionForm)form;
    java.sql.Date startDate = null;
    java.sql.Date endDate = null;

    String type = (String)dynaForm.get("number");
    String investmentType = type.substring(0, 2);
    if (investmentType.equals("FI"))
    {
      investmentDetails = ifProcessor.investmentReportDetailsForFixedDeposit(type);
      dynaForm.set("investmentArray", investmentDetails);

      if ((investmentDetails == null) || (investmentDetails.size() == 0))
      {
        throw new NoDataException("No Data is available for the values entered, Please Enter Any Other Value ");
      }

      Log.log(4, "IFAction", "investmentReportDetailsFinal", "Exited");

      return mapping.findForward("success1");
    }

    if (investmentType.equals("MU"))
    {
      investmentDetails = ifProcessor.investmentReportDetailsForMutualFund(type);
      dynaForm.set("investmentArray", investmentDetails);

      if ((investmentDetails == null) || (investmentDetails.size() == 0))
      {
        throw new NoDataException("No Data is available for the values entered, Please Enter Any Other Value ");
      }

      Log.log(4, "IFAction", "investmentReportDetailsFinal", "Exited");

      return mapping.findForward("success2");
    }

    if (investmentType.equals("BO"))
    {
      investmentDetails = ifProcessor.investmentReportDetailsForBonds(type);
      dynaForm.set("investmentArray", investmentDetails);

      if ((investmentDetails == null) || (investmentDetails.size() == 0))
      {
        throw new NoDataException("No Data is available for the values entered, Please Enter Any Other Value ");
      }

      Log.log(4, "IFAction", "investmentReportDetailsFinal", "Exited");

      return mapping.findForward("success3");
    }

    if (investmentType.equals("CO"))
    {
      investmentDetails = ifProcessor.investmentReportDetailsForCommercialpapers(type);
      dynaForm.set("investmentArray", investmentDetails);

      if ((investmentDetails == null) || (investmentDetails.size() == 0))
      {
        throw new NoDataException("No Data is available for the values entered, Please Enter Any Other Value ");
      }

      Log.log(4, "IFAction", "investmentReportDetailsFinal", "Exited");

      return mapping.findForward("success4");
    }

    if (investmentType.equals("DE"))
    {
      investmentDetails = ifProcessor.investmentReportDetailsForDebentures(type);
      dynaForm.set("investmentArray", investmentDetails);

      if ((investmentDetails == null) || (investmentDetails.size() == 0))
      {
        throw new NoDataException("No Data is available for the values entered, Please Enter Any Other Value ");
      }

      Log.log(4, "IFAction", "investmentReportDetailsFinal", "Exited");

      return mapping.findForward("success5");
    }

    investmentDetails = ifProcessor.investmentReportDetailsForGSecurities(type);
    dynaForm.set("investmentArray", investmentDetails);

    if ((investmentDetails == null) || (investmentDetails.size() == 0))
    {
      throw new NoDataException("No Data is available for the values entered, Please Enter Any Other Value ");
    }

    Log.log(4, "IFAction", "investmentReportDetailsFinal", "Exited");

    return mapping.findForward("success6");
  }

  public ActionForward chequeDetailsInsertInput(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response)
    throws Exception
  {
    Log.log(4, "IFAction", "chequeDetailsInsertInput", "Entered");
    ArrayList bankDetails = new ArrayList();
    IFProcessor ifProcessor = new IFProcessor();
    DynaActionForm dynaForm = (DynaActionForm)form;
    dynaForm.initialize(mapping);
    bankDetails = ifProcessor.getAllBanksWithAccountNumbers();
    ArrayList bankNames = new ArrayList(bankDetails.size());
    String bankName = "";
    for (int i = 0; i < bankDetails.size(); i++)
    {
      BankAccountDetail bankAccountDetail = (BankAccountDetail)bankDetails.get(i);
      bankName = String.valueOf(bankAccountDetail.getBankName()) + " ," + bankAccountDetail.getBankBranchName() + "(" + bankAccountDetail.getAccountNumber() + ")";

      bankNames.add(bankName);
    }
    dynaForm.set("banks", bankNames);
    bankDetails = null;
    ifProcessor = null;
    Log.log(4, "IFAction", "chequeDetailsInsertInput", "Exited");

    return mapping.findForward("success");
  }

  public ActionForward chequeDetailsInsertSuccess(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response)
    throws Exception
  {
    Log.log(4, "IFAction", "chequeDetailsInsertSuccess", "Entered");
    IFProcessor ifProcessor = new IFProcessor();

    ChequeDetails chequeDetails = new ChequeDetails();

    DynaActionForm dynaForm = (DynaActionForm)form;
    User creatingUser = getUserInformation(request);
    String user = creatingUser.getUserId();
    String bank = (String)dynaForm.get("bankName");
    System.out.println("bank:" + bank);

    BeanUtils.populate(chequeDetails, dynaForm.getMap());
    int i = bank.indexOf(",");

    String newBank = bank.substring(0, i);

    chequeDetails.setBankName(newBank);

    int j = bank.indexOf("(");

    String newBranch = bank.substring(i + 1, j);

    chequeDetails.setBranchName(newBranch);

    chequeDetails.setUserId(user);

    String contextPath = request.getSession(false).getServletContext().getRealPath("");
    Log.log(5, "RPAction", "getPaymentsMade", "path " + contextPath);
    ifProcessor.chequeDetailsInsertSuccess(chequeDetails, contextPath, user);

    Log.log(4, "IFAction", "chequeDetailsInsertSuccess", "Exited");

    return mapping.findForward("success");
  }

  public ActionForward chequeDetailsUpdate(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response)
    throws Exception
  {
    Log.log(4, "IFAction", "chequeDetailsUpdate", "Entered");
    ArrayList cheque = new ArrayList();
    IFProcessor ifProcessor = new IFProcessor();
    DynaActionForm dynaForm = (DynaActionForm)form;
    java.sql.Date startDate = null;
    java.sql.Date endDate = null;
    java.util.Date sDate = (java.util.Date)dynaForm.get("dateOfTheDocument2");
    String stDate = String.valueOf(sDate);
    if ((stDate == null) || (stDate.equals("")))
    {
      startDate = null;
    }
    else if (stDate != null)
    {
      startDate = new java.sql.Date(sDate.getTime());
    }

    java.util.Date eDate = (java.util.Date)dynaForm.get("dateOfTheDocument3");
    endDate = new java.sql.Date(eDate.getTime());
    cheque = ifProcessor.chequeDetailsModify(startDate, endDate);
    dynaForm.set("chequeArray", cheque);

    if ((cheque == null) || (cheque.size() == 0))
    {
      throw new NoDataException("No Data is available for the values entered, Please Enter Any Other Value ");
    }

    Log.log(4, "IFAction", "chequeDetailsUpdate", "Exited");

    return mapping.findForward("success");
  }

  public ActionForward chequeDetailsUpdatePage(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response)
    throws Exception
  {
    Log.log(4, "IFAction", "chequeDetailsUpdatePage", "Entered");
    ChequeDetails cheque = new ChequeDetails();
    IFProcessor ifProcessor = new IFProcessor();
    DynaActionForm dynaForm = (DynaActionForm)form;

    String chequeNumber = request.getParameter("number");
    if (chequeNumber == null)
    {
      chequeNumber = (String)dynaForm.get("chequeId");
    }
    cheque = ifProcessor.chequeDetailsUpdatePageReport(chequeNumber);
    ArrayList bankDetails = ifProcessor.getAllBanksWithAccountNumbers();
    ArrayList bankNames = new ArrayList(bankDetails.size());
    String bankName = "";
    for (int i = 0; i < bankDetails.size(); i++)
    {
      BankAccountDetail bankAccountDetail = (BankAccountDetail)bankDetails.get(i);
      bankName = String.valueOf(bankAccountDetail.getBankName()) + " ," + bankAccountDetail.getBankBranchName() + "(" + bankAccountDetail.getAccountNumber() + ")";

      bankNames.add(bankName);
    }
    dynaForm.set("banks", bankNames);

    dynaForm.set("bankName", cheque.getBankName());
    BeanUtils.copyProperties(dynaForm, cheque);

    Log.log(4, "IFAction", "chequeDetailsUpdatePage", "Exited");

    return mapping.findForward("success");
  }

  public ActionForward chequeDetailsUpdateSuccess(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response)
    throws Exception
  {
    Log.log(4, "IFAction", "chequeDetailsUpdateSuccess", "Entered");
    IFProcessor ifProcessor = new IFProcessor();
    ChequeDetails chequeDetails = new ChequeDetails();
    DynaActionForm dynaForm = (DynaActionForm)form;
    String chequeNumber = (String)dynaForm.get("number");
    User creatingUser = getUserInformation(request);
    String user = creatingUser.getUserId();
    BeanUtils.populate(chequeDetails, dynaForm.getMap());
    String bank = (String)dynaForm.get("bankName");

    int i = bank.indexOf(",");

    String newBank = bank.substring(0, i);

    chequeDetails.setBankName(newBank);

    int j = bank.indexOf("(");

    String newBranch = bank.substring(i + 1, j);

    chequeDetails.setBranchName(newBranch);

    chequeDetails.setUserId(user);
    ifProcessor.chequeDetailsUpdateSuccess(chequeDetails, chequeNumber);

    Log.log(4, "IFAction", "chequeDetailsUpdateSuccess", "Exited");

    return mapping.findForward("success");
  }

  public ActionForward chequeDetailsReport(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response)
    throws Exception
  {
    Log.log(4, "IFAction", "chequeDetailsReport", "Entered");
    ArrayList cheque = new ArrayList();
    IFProcessor ifProcessor = new IFProcessor();
    DynaActionForm dynaForm = (DynaActionForm)form;
    java.sql.Date startDate = null;
    java.sql.Date endDate = null;
    java.util.Date sDate = (java.util.Date)dynaForm.get("dateOfTheDocument2");
    String stDate = String.valueOf(sDate);
    if ((stDate == null) || (stDate.equals("")))
    {
      startDate = null;
    }
    else if (stDate != null)
    {
      startDate = new java.sql.Date(sDate.getTime());
    }
    java.util.Date eDate = (java.util.Date)dynaForm.get("dateOfTheDocument3");
    endDate = new java.sql.Date(eDate.getTime());
    cheque = ifProcessor.chequeDetailsUpdate(startDate, endDate);
    dynaForm.set("chequeArray", cheque);

    if ((cheque == null) || (cheque.size() == 0))
    {
      throw new NoDataException("No Data is available for the values entered, Please Enter Any Other Value ");
    }

    Log.log(4, "IFAction", "chequeDetailsReport", "Exited");

    return mapping.findForward("success");
  }

  public ActionForward chequeDetailsReportPage(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response)
    throws Exception
  {
    Log.log(4, "IFAction", "chequeDetailsReportPage", "Entered");
    ChequeDetails cheque = new ChequeDetails();
    IFProcessor ifProcessor = new IFProcessor();
    DynaActionForm dynaForm = (DynaActionForm)form;

    String chequeNumber = request.getParameter("number");
    Log.log(4, "IFAction", "chequeDetailsReportPage", "number *" + chequeNumber + "*");
    if ((chequeNumber == null) || (chequeNumber.trim().equals("")))
    {
      chequeNumber = (String)dynaForm.get("chqId");
    }
    Log.log(4, "IFAction", "chequeDetailsReportPage", "number #" + chequeNumber + "#");
    cheque = ifProcessor.chequeDetailsUpdatePageReport(chequeNumber);
    cheque.setChqId(chequeNumber);

    BeanUtils.copyProperties(dynaForm, cheque);
    Log.log(4, "IFAction", "chequeDetailsReportPage", "Exited");

    return mapping.findForward("success");
  }

  public ActionForward hvcInsertInput(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response)
    throws Exception
  {
    Log.log(4, "IFAction", "hvcInsertInput", "Entered");
    ArrayList bankDetails = new ArrayList();
    IFProcessor ifProcessor = new IFProcessor();
    DynaActionForm dynaForm = (DynaActionForm)form;
    dynaForm.initialize(mapping);
    bankDetails = ifProcessor.getAllBanksWithAccountNumbers();
    ArrayList bankNames = new ArrayList(bankDetails.size());
    String bankName = "";
    for (int i = 0; i < bankDetails.size(); i++)
    {
      BankAccountDetail bankAccountDetail = (BankAccountDetail)bankDetails.get(i);
      bankName = String.valueOf(bankAccountDetail.getBankName()) + " ," + bankAccountDetail.getBankBranchName() + "(" + bankAccountDetail.getAccountNumber() + ")";

      bankNames.add(bankName);
    }
    dynaForm.set("banks", bankNames);
    bankDetails = null;
    ifProcessor = null;
    Log.log(4, "IFAction", "hvcInsertInput", "Exited");

    return mapping.findForward("success");
  }

  public ActionForward hvcInsertSuccess(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response)
    throws Exception
  {
    Log.log(4, "IFAction", "hvcInsertSuccess", "Entered");
    IFProcessor ifProcessor = new IFProcessor();
    ChequeDetails chequeDetails = new ChequeDetails();
    DynaActionForm dynaForm = (DynaActionForm)form;
    User creatingUser = getUserInformation(request);
    String user = creatingUser.getUserId();
    BeanUtils.populate(chequeDetails, dynaForm.getMap());
    String bank = (String)dynaForm.get("bankName");

    String toBank = (String)dynaForm.get("toBankName");

    int i = bank.indexOf(",");

    String newBank = bank.substring(0, i);

    chequeDetails.setBankName(newBank);

    int k = bank.indexOf("(");

    String newBranch = bank.substring(i + 1, k);

    chequeDetails.setBranchName(newBranch);

    int j = toBank.indexOf(",");

    String toNewBank = toBank.substring(0, j);

    chequeDetails.setToBankName(toNewBank);

    int l = toBank.indexOf("(");

    String newToBranch = toBank.substring(j + 1, l);

    chequeDetails.setToBranchName(newToBranch);

    chequeDetails.setUserId(user);
    String remarks = chequeDetails.getChequeRemarks();

    ifProcessor.hvcInsertSuccess(chequeDetails);

    Log.log(4, "IFAction", "hvcInsertSuccess", "Exited");

    return mapping.findForward("success");
  }

  public ActionForward hvcUpdate(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response)
    throws Exception
  {
    Log.log(4, "IFAction", "hvcUpdate", "Entered");
    ArrayList cheque = new ArrayList();
    IFProcessor ifProcessor = new IFProcessor();
    DynaActionForm dynaForm = (DynaActionForm)form;
    java.sql.Date startDate = null;
    java.sql.Date endDate = null;
    java.util.Date sDate = (java.util.Date)dynaForm.get("dateOfTheDocument2");
    String stDate = String.valueOf(sDate);
    if ((stDate == null) || (stDate.equals("")))
    {
      startDate = null;
    }
    else if (stDate != null)
    {
      startDate = new java.sql.Date(sDate.getTime());
    }

    java.util.Date eDate = (java.util.Date)dynaForm.get("dateOfTheDocument3");
    endDate = new java.sql.Date(eDate.getTime());
    cheque = ifProcessor.hvcUpdate(startDate, endDate);
    dynaForm.set("chequeArray", cheque);

    if ((cheque == null) || (cheque.size() == 0))
    {
      throw new NoDataException("No Data is available for the values entered, Please Enter Any Other Value ");
    }

    Log.log(4, "IFAction", "hvcUpdate", "Exited");

    return mapping.findForward("success");
  }

  public ActionForward hvcUpdatePage(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response)
    throws Exception
  {
    Log.log(4, "IFAction", "hvcUpdatePage", "Entered");
    ChequeDetails cheque = new ChequeDetails();
    IFProcessor ifProcessor = new IFProcessor();
    DynaActionForm dynaForm = (DynaActionForm)form;
    String chequeNumber = (String)dynaForm.get("number");

    ArrayList bankDetails = ifProcessor.getAllBanksWithAccountNumbers();
    ArrayList bankNames = new ArrayList(bankDetails.size());
    String bankName = "";
    for (int i = 0; i < bankDetails.size(); i++)
    {
      BankAccountDetail bankAccountDetail = (BankAccountDetail)bankDetails.get(i);
      bankName = String.valueOf(bankAccountDetail.getBankName()) + " ," + bankAccountDetail.getBankBranchName() + "(" + bankAccountDetail.getAccountNumber() + ")";

      bankNames.add(bankName);
    }
    dynaForm.set("banks", bankNames);

    cheque = ifProcessor.hvcUpdatePage(chequeNumber);
    BeanUtils.copyProperties(dynaForm, cheque);

    Log.log(4, "IFAction", "hvcUpdatePage", "Exited");

    return mapping.findForward("success");
  }

  public ActionForward hvcUpdateSuccess(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response)
    throws Exception
  {
    Log.log(4, "IFAction", "hvcUpdateSuccess", "Entered");
    IFProcessor ifProcessor = new IFProcessor();
    ChequeDetails chequeDetails = new ChequeDetails();
    DynaActionForm dynaForm = (DynaActionForm)form;
    String chequeNumber = (String)dynaForm.get("number");
    User creatingUser = getUserInformation(request);
    String user = creatingUser.getUserId();
    BeanUtils.populate(chequeDetails, dynaForm.getMap());
    String bank = (String)dynaForm.get("bankName");

    String toBank = (String)dynaForm.get("toBankName");

    int i = bank.indexOf(",");

    String newBank = bank.substring(0, i);

    chequeDetails.setBankName(newBank);

    int k = bank.indexOf("(");

    String newBranch = bank.substring(i + 1, k);

    chequeDetails.setBranchName(newBranch);

    int j = toBank.indexOf(",");

    String toNewBank = toBank.substring(0, j);

    chequeDetails.setToBankName(toNewBank);

    int l = toBank.indexOf("(");

    String newToBranch = toBank.substring(j + 1, l);

    chequeDetails.setToBranchName(newToBranch);

    chequeDetails.setUserId(user);
    ifProcessor.hvcUpdateSuccess(chequeDetails, chequeNumber);

    Log.log(4, "IFAction", "hvcUpdateSuccess", "Exited");

    return mapping.findForward("success");
  }

  public ActionForward showInflow(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response)
    throws Exception
  {
    Log.log(4, "IFAction", "showInflow", "Entered");
    DynaActionForm dynaForm = (DynaActionForm)form;

    dynaForm.initialize(mapping);

    dynaForm.set("inflowOrOutflow", "INFLOW");
    Log.log(4, "IFAction", "showInflow", "Exited");

    return mapping.findForward("budgetDetails");
  }

  public ActionForward showInflowForecastDetails(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response)
    throws Exception
  {
    Log.log(4, "IFAction", "showInflowForecastDetails", "Entered");

    InvestmentForm ifForm = (InvestmentForm)form;
    ifForm.setInflowOrOutflow("I");
    getInvestmentForm(ifForm, "I");

    Log.log(4, "IFAction", "showInflowForecastDetails", "Exited");

    return mapping.findForward("success");
  }

  public ActionForward showOutflowForecastDetails(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response)
    throws Exception
  {
    Log.log(4, "IFAction", "showOutflowForecastDetails", "Entered");

    InvestmentForm ifForm = (InvestmentForm)form;
    ifForm.setInflowOrOutflow("O");
    getInvestmentForm(ifForm, "O");

    Log.log(4, "IFAction", "showOutflowForecastDetails", "Exited");

    return mapping.findForward("success");
  }

  public ActionForward showAnnualFundsInflowDetails(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response)
    throws Exception
  {
    Log.log(4, "IFAction", "showAnnualFundsInflowDetails", "Entered");

    InvestmentForm ifForm = (InvestmentForm)form;

    ifForm.setInflowOrOutflow("I");
    ifForm.setAnnualOrShortTerm("ANNUAL");

    ifForm.getHeadsToRender().clear();
    ifForm.getSubHeadsToRender().clear();
    ifForm.setDateOfFlow(null);
    getInvestmentForm(ifForm, "I");

    ifForm.getHeads().clear();
    ifForm.getSubHeads().clear();

    Log.log(4, "IFAction", "showAnnualFundsInflowDetails", "Exited");

    return mapping.findForward("success");
  }

  public ActionForward showShortTermFundsInflowDetails(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response)
    throws Exception
  {
    Log.log(4, "IFAction", "showShortTermFundsInflowDetails", "Entered");

    InvestmentForm ifForm = (InvestmentForm)form;
    ifForm.setInflowOrOutflow("I");
    ifForm.setAnnualOrShortTerm("SHORTTERM");
    getInvestmentForm(ifForm, "I");

    Log.log(4, "IFAction", "showShortTermFundsInflowDetails", "Exited");

    return mapping.findForward("success");
  }

  public ActionForward showAnnualFundsOutflowDetails(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response)
    throws Exception
  {
    Log.log(4, "IFAction", "showAnnualFundsOutflowDetails", "Entered");

    InvestmentForm ifForm = (InvestmentForm)form;

    ifForm.setInflowOrOutflow("O");

    ifForm.setAnnualOrShortTerm("ANNUAL");

    ifForm.getHeadsToRender().clear();
    ifForm.getSubHeadsToRender().clear();
    ifForm.setDateOfFlow(null);
    getInvestmentForm(ifForm, "O");

    ifForm.getHeads().clear();
    ifForm.getSubHeads().clear();

    Log.log(4, "IFAction", "showAnnualFundsOutflowDetails", "Exited");

    return mapping.findForward("success");
  }

  public ActionForward showShortTermFundsOutflowDetails(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response)
    throws Exception
  {
    Log.log(4, "IFAction", "showShortTermFundsOutflowDetails", "Entered");
    InvestmentForm ifForm = (InvestmentForm)form;
    ifForm.setInflowOrOutflow("O");
    ifForm.setAnnualOrShortTerm("SHORTTERM");
    getInvestmentForm(ifForm, "O");

    Log.log(4, "IFAction", "showShortTermFundsOutflowDetails", "Exited");

    return mapping.findForward("success");
  }

  public ActionForward showBudgetOutflowDetails(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response)
    throws Exception
  {
    Log.log(4, "IFAction", "showBudgetOutflowDetails", "Entered");
    String forwardPage = "";
    String budget = "";
    InvestmentForm ifForm = (InvestmentForm)form;
    budget = ifForm.getAnnualOrShortTerm();

    ifForm.resetWhenRequired(mapping, request);
    ifForm.setAnnualOrShortTerm(budget);

    ifForm.setInflowOrOutflow("O");
    getInvestmentForm(ifForm, "O");

    if (budget.equals("Annual"))
    {
      forwardPage = "annualOutflowBudget";
    }
    else if (budget.equals("ShortTerm"))
    {
      forwardPage = "shortTermOutflowBudget";
    }
    else
    {
      forwardPage = "notSelected";
    }
    Log.log(4, "IFAction", "showBudgetOutflowDetails", "Exited");

    return mapping.findForward(forwardPage);
  }

  public ActionForward showBudgetInflowDetails(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response)
    throws Exception
  {
    Log.log(4, "IFAction", "showBudgetInflowDetails", "Entered");
    String forwardPage = "";
    String budget = "";

    InvestmentForm ifForm = (InvestmentForm)form;

    budget = ifForm.getAnnualOrShortTerm();
    ifForm.resetWhenRequired(mapping, request);
    ifForm.setAnnualOrShortTerm(budget);

    Log.log(5, "IFAction", "showBudgetInflowDetails", "Budget Type " + ifForm.getAnnualOrShortTerm());

    ifForm.setInflowOrOutflow("I");
    getInvestmentForm(ifForm, "I");

    if (budget.equals("Annual"))
    {
      forwardPage = "annualInflowBudget";
    }
    else if (budget.equals("ShortTerm"))
    {
      forwardPage = "shortTermInflowBudget";
    }
    else
    {
      forwardPage = "notSelected";
    }
    Log.log(4, "IFAction", "showBudgetInflowDetails", "Exited");

    return mapping.findForward(forwardPage);
  }

  public ActionForward updateBudgetHeadMaster(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response)
    throws Exception
  {
    Log.log(4, "IFAction", "updateBudgetHeadMaster", "Entered");
    String budgetHeadType = "";
    String budgetInOutFlag = "";

    DynaActionForm dynaForm = (DynaActionForm)form;
    BudgetHead budgetHead = new BudgetHead();
    BeanUtils.populate(budgetHead, dynaForm.getMap());

    IFProcessor ifProcessor = new IFProcessor();

    User loggedInUser = getUserInformation(request);
    String loggedUserId = loggedInUser.getUserId();

    String newBudgetHead = budgetHead.getNewBudgetHead();
    Log.log(4, "IFAction", "updateBudgetHeadMaster", "Entered " + newBudgetHead);
    while (true)
    {
      if (budgetHead.getBudgetHeadType().equalsIgnoreCase("B"))
      {
        Log.log(4, "IFAction", "updateBudgetHeadMaster", "Entered Both new");
        ArrayList budgetHeads = ifProcessor.getBudgetHeadTypes("I");
        for (int i = 0; i < budgetHeads.size(); i++)
        {
          if (((String)budgetHeads.get(i)).equalsIgnoreCase(newBudgetHead))
          {
            throw new MessageException("Budget Head Already Exists.");
          }
        }

        budgetHeads = ifProcessor.getBudgetHeadTypes("O");
        for (int i = 0; i < budgetHeads.size(); i++)
        {
          if (((String)budgetHeads.get(i)).equalsIgnoreCase(budgetHead.getNewBudgetHead()))
          {
            throw new MessageException("Budget Head Already Exists.");
          }
        }
      }

      int indexOfLastAnd = newBudgetHead.lastIndexOf(".".trim());
      if (indexOfLastAnd != -1)
      {
        String firstPartOfBudgetHead = newBudgetHead.substring(0, indexOfLastAnd);
        String secPartOfBudgetHead = newBudgetHead.substring(indexOfLastAnd + 1, newBudgetHead.length());
        newBudgetHead = String.valueOf(firstPartOfBudgetHead) + secPartOfBudgetHead;
      }
      if (indexOfLastAnd == -1)
      {
        break;
      }
    }
    String modBudgetHead = budgetHead.getModBudgetHead();
    Log.log(4, "IFAction", "updateBudgetHeadMaster", "Entered " + modBudgetHead);
    while (true)
    {
      if (budgetHead.getBudgetHeadType().equalsIgnoreCase("B"))
      {
        Log.log(4, "IFAction", "updateBudgetHeadMaster", "Entered Both mod");
        ArrayList budgetHeads = ifProcessor.getBudgetHeadTypes("I");
        for (int i = 0; i < budgetHeads.size(); i++)
        {
          if (((String)budgetHeads.get(i)).equalsIgnoreCase(modBudgetHead))
          {
            throw new MessageException("Budget Head Already Exists.");
          }
        }

        budgetHeads = ifProcessor.getBudgetHeadTypes("O");
        for (int i = 0; i < budgetHeads.size(); i++)
        {
          if (((String)budgetHeads.get(i)).equalsIgnoreCase(modBudgetHead))
          {
            throw new MessageException("Budget Head Already Exists.");
          }
        }
      }

      int indexOfLastAnd = modBudgetHead.lastIndexOf(".".trim());
      if (indexOfLastAnd != -1)
      {
        String firstPartOfBudgetHead = modBudgetHead.substring(0, indexOfLastAnd);
        String secPartOfBudgetHead = modBudgetHead.substring(indexOfLastAnd + 1, modBudgetHead.length());
        modBudgetHead = String.valueOf(firstPartOfBudgetHead) + secPartOfBudgetHead;
      }
      if (indexOfLastAnd == -1)
      {
        break;
      }
    }
    budgetHead.setNewBudgetHead(newBudgetHead);
    budgetHead.setModBudgetHead(modBudgetHead);

    ifProcessor.updateBudgetHeadsMaster(budgetHead, loggedUserId);
    Log.log(4, "IFAction", "updateBudgetHeadMaster", "Exited");

    String message = "Budget Head saved successfully";

    request.setAttribute("message", message);

    return mapping.findForward("success");
  }

  public ActionForward showBudgetSubHeadMaster(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response)
    throws Exception
  {
    Log.log(4, "IFAction", "showBudgetSubHeadMaster", "Entered");

    HttpSession session = request.getSession(false);
    session.setAttribute("modFlag", "0");

    DynaActionForm dynaForm = (DynaActionForm)form;
    dynaForm.initialize(mapping);
    getBudgetHeadTypes(dynaForm);

    Log.log(4, "IFAction", "showBudgetSubHeadMaster", "Exited");

    return mapping.findForward("success");
  }

  public ActionForward updateBudgetSubHeadMaster(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response)
    throws Exception
  {
    Log.log(4, "IFAction", "updateBudgetSubHeadMaster", "Entered");

    String budgetHeadType = "";
    String budgetSubHeadTitle = "";
    Hashtable budgetSubHeadDetails = new Hashtable();
    DynaActionForm dynaForm = (DynaActionForm)form;

    BudgetSubHead subHead = new BudgetSubHead();

    BeanUtils.populate(subHead, dynaForm.getMap());

    Log.log(4, "IFAction", "updateBudgetSubHeadMaster", "Head " + subHead.getBudgetHead());
    Log.log(4, "IFAction", "updateBudgetSubHeadMaster", "Sub-Head  " + subHead.getBudgetSubHeadTitle());
    Log.log(4, "IFAction", "updateBudgetSubHeadMaster", " Head Type " + subHead.getBudgetSubHeadType());

    User loggedInUser = getUserInformation(request);
    String loggedUserId = loggedInUser.getUserId();

    IFProcessor ifProcessor = new IFProcessor();
    String newBudgetSubHeadTitle = subHead.getNewBudgetSubHeadTitle();
    while (true)
    {
      int indexOfLastAnd = newBudgetSubHeadTitle.lastIndexOf(".".trim());
      if (indexOfLastAnd != -1)
      {
        String firstPartOfBudgetSubHead = newBudgetSubHeadTitle.substring(0, indexOfLastAnd);
        String secPartOfBudgetSubHead = newBudgetSubHeadTitle.substring(indexOfLastAnd + 1, newBudgetSubHeadTitle.length());
        newBudgetSubHeadTitle = String.valueOf(firstPartOfBudgetSubHead) + secPartOfBudgetSubHead;
      }
      if (indexOfLastAnd == -1)
      {
        break;
      }
    }
    subHead.setNewBudgetSubHeadTitle(newBudgetSubHeadTitle);

    String modBudgetSubHeadTitle = subHead.getModBudgetSubHeadTitle();
    while (true)
    {
      int indexOfLastAnd = modBudgetSubHeadTitle.lastIndexOf(".".trim());
      if (indexOfLastAnd != -1)
      {
        String firstPartOfBudgetSubHead = modBudgetSubHeadTitle.substring(0, indexOfLastAnd);
        String secPartOfBudgetSubHead = modBudgetSubHeadTitle.substring(indexOfLastAnd + 1, modBudgetSubHeadTitle.length());
        modBudgetSubHeadTitle = String.valueOf(firstPartOfBudgetSubHead) + secPartOfBudgetSubHead;
      }
      if (indexOfLastAnd == -1)
      {
        break;
      }
    }
    subHead.setModBudgetSubHeadTitle(modBudgetSubHeadTitle);

    ifProcessor.updateBudgetSubHeadMaster(subHead, loggedUserId);

    String message = "Budget Sub Head saved successfully";

    request.setAttribute("message", message);

    Log.log(4, "IFAction", "updateBudgetSubHeadMaster", "Exited");

    return mapping.findForward("success");
  }

  public ActionForward updateHolidayMaster(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response)
    throws Exception
  {
    Log.log(4, "IFAction", "updateHolidayMaster", "Entered");

    String holidayDate = "";
    String holidayDesc = "";
    Hashtable holidayDetails = new Hashtable();
    DynaActionForm dynaForm = (DynaActionForm)form;
    holidayDate = (String)dynaForm.get("holidayDate");
    holidayDesc = (String)dynaForm.get("holidayDescription");
    String newHolDate = (String)dynaForm.get("newHolidayDate");
    String modHolDate = (String)dynaForm.get("modHolidayDate");

    User loggedInUser = getUserInformation(request);
    String loggedUserId = loggedInUser.getUserId();
    holidayDetails.put("Updated By", loggedUserId);
    IFProcessor ifProcessor = new IFProcessor();
    if (holidayDate.equals(""))
    {
      holidayDetails.put("New Holiday Date", newHolDate);
      holidayDetails.put("Holiday Description", holidayDesc);
      ifProcessor.insertHolidayMaster(holidayDetails);
    }
    else
    {
      holidayDetails.put("Holiday Date", holidayDate);
      holidayDetails.put("Mod Holiday Date", modHolDate);
      holidayDetails.put("Holiday Description", holidayDesc);
      ifProcessor.updateHolidayMaster(holidayDetails);
    }

    String message = "Holiday details saved Successfully";

    request.setAttribute("message", message);

    Log.log(4, "IFAction", "updateHolidayMaster", "Exited");

    return mapping.findForward("success");
  }

  public ActionForward updateCorpusMaster(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response)
    throws Exception
  {
    Log.log(4, "IFAction", "updateCorpusMaster", "Entered");

    String corpusContributor = "";

    Hashtable corpusDetails = new Hashtable();
    DynaActionForm dynaForm = (DynaActionForm)form;
    String corpusId = (String)dynaForm.get("corpusId");
    corpusContributor = (String)dynaForm.get("corpusContributor");
    Double corpusAmount = (Double)dynaForm.get("corpusAmount");
    java.util.Date corpusDate = (java.util.Date)dynaForm.get("corpusDate");
    corpusDetails.put("Corpus Contributor", corpusContributor);
    corpusDetails.put("Corpus Contribution", corpusAmount);
    corpusDetails.put("Corpus Date", corpusDate);
    corpusDetails.put("Corpus Id", corpusId);

    User user = getUserInformation(request);

    corpusDetails.put("Updated By", user.getUserId());
    IFProcessor ifProcessor = new IFProcessor();
    ifProcessor.updateCorpusMaster(corpusDetails);

    String message = "Corpus details saved Successfully";

    request.setAttribute("message", message);

    Log.log(4, "IFAction", "updateCorpusMaster", "Exited");

    return mapping.findForward("success");
  }

  public ActionForward showInstrumentMaster(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response)
    throws Exception
  {
    Log.log(4, "IFAction", "showInstrumentMaster", "Entered");

    HttpSession session = request.getSession(false);
    session.setAttribute("modFlag", "0");

    DynaActionForm dynaForm = (DynaActionForm)form;
    dynaForm.initialize(mapping);

    IFProcessor ifProcessor = new IFProcessor();
    ArrayList instruments = ifProcessor.getInstrumentTypes("G");

    dynaForm.set("instrumentNames", instruments);

    Log.log(4, "IFAction", "showInstrumentMaster", "Exited");

    return mapping.findForward("success");
  }

  public ActionForward updateInstrumentMaster(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response)
    throws Exception
  {
    Log.log(4, "IFAction", "updateInstrumentMaster", "Entered");

    User loggedInUser = getUserInformation(request);
    String loggedUserId = loggedInUser.getUserId();

    DynaActionForm dynaForm = (DynaActionForm)form;
    InstrumentDetail instrumentDetail = new InstrumentDetail();

    BeanUtils.populate(instrumentDetail, dynaForm.getMap());

    IFProcessor ifProcessor = new IFProcessor();
    ifProcessor.updateInstrumentMaster(instrumentDetail, loggedUserId);

    String message = "Instrument details saved Successfully";

    request.setAttribute("message", message);

    Log.log(4, "IFAction", "updateInstrumentMaster", "Exited");

    return mapping.findForward("success");
  }

  public ActionForward updateInstrumentFeatureMaster(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response)
    throws Exception
  {
    Log.log(4, "IFAction", "updateInstrumentFeatureMaster", "Entered");

    DynaActionForm dynaForm = (DynaActionForm)form;

    InstrumentFeature instrumentFeature = new InstrumentFeature();

    BeanUtils.populate(instrumentFeature, dynaForm.getMap());

    User loggedInUser = getUserInformation(request);
    String loggedUserId = loggedInUser.getUserId();
    IFProcessor ifProcessor = new IFProcessor();
    ifProcessor.updateInstrumentFeature(instrumentFeature, loggedUserId);

    String message = "Instrument Feature details saved Successfully";

    request.setAttribute("message", message);

    Log.log(4, "IFAction", "updateInstrumentFeatureMaster", "Exited");

    return mapping.findForward("success");
  }

  public ActionForward showInstrumentSchemeMaster(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response)
    throws Exception
  {
    Log.log(4, "IFAction", "showInstrumentSchemeMaster", "Entered");

    HttpSession session = request.getSession(false);
    session.setAttribute("modFlag", "0");

    DynaActionForm dynaForm = (DynaActionForm)form;
    dynaForm.initialize(mapping);
    getInstrumentTypes(dynaForm);

    Log.log(4, "IFAction", "showInstrumentSchemeMaster", "Exited");

    return mapping.findForward("success");
  }

  public ActionForward updateInstrumentSchemeMaster(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response)
    throws Exception
  {
    Log.log(4, "IFAction", "updateInstrumentSchemeMaster", "Entered");

    String instrument = "";
    String instrumentSchemeType = "";
    String instrumentSchemeDescription = "";
    Hashtable instrumentSchemeDetails = new Hashtable();
    DynaActionForm dynaForm = (DynaActionForm)form;
    instrument = (String)dynaForm.get("instrument");
    instrumentSchemeType = (String)dynaForm.get("instrumentSchemeType");
    instrumentSchemeDescription = (String)dynaForm.get("instrumentSchemeDescription");
    String newInstScheme = (String)dynaForm.get("newInstrumentSchemeType");
    String modInstScheme = (String)dynaForm.get("modInstrumentSchemeType");
    instrumentSchemeDetails.put("Instrument", instrument);
    instrumentSchemeDetails.put("Instrument Scheme Type", instrumentSchemeType);
    instrumentSchemeDetails.put("New Instrument Scheme Type", newInstScheme);
    instrumentSchemeDetails.put("Mod Instrument Scheme Type", modInstScheme);
    instrumentSchemeDetails.put("Instrument Scheme Description", instrumentSchemeDescription);

    User loggedInUser = getUserInformation(request);
    String loggedUserId = loggedInUser.getUserId();
    instrumentSchemeDetails.put("Updated By", loggedUserId);
    IFProcessor ifProcessor = new IFProcessor();
    ifProcessor.updateInstrumentScheme(instrumentSchemeDetails);

    String message = "Instrument Scheme details saved Successfully";

    request.setAttribute("message", message);

    Log.log(4, "IFAction", "updateInstrumentSchemeMaster", "Exited");

    return mapping.findForward("success");
  }

  public ActionForward showInvesteeMaster(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response)
    throws Exception
  {
    Log.log(4, "IFAction", "showInvesteeMaster", "Entered");

    HttpSession session = request.getSession(false);
    session.setAttribute("modFlag", "0");

    DynaActionForm dynaForm = (DynaActionForm)form;
    dynaForm.initialize(mapping);
    getAllInvesteeGroups(dynaForm);

    Log.log(4, "IFAction", "showInvesteeMaster", "Exited");

    return mapping.findForward("success");
  }

  public ActionForward updateInvesteeMaster(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response)
    throws Exception
  {
    Log.log(4, "IFAction", "updateInvesteeMaster", "Entered");

    DynaActionForm dynaForm = (DynaActionForm)form;
    InvesteeDetail investeeDetail = new InvesteeDetail();

    BeanUtils.populate(investeeDetail, dynaForm.getMap());
    investeeDetail.setInvestee((String)dynaForm.get("investee1"));
    Log.log(5, "IFAction", "updateInvesteeMaster", "net worth " + investeeDetail.getInvesteeNetWorth());
    Log.log(5, "IFAction", "updateInvesteeMaster", "form...net worth " + dynaForm.get("investeeNetWorth"));

    User loggedInUser = getUserInformation(request);
    String loggedUserId = loggedInUser.getUserId();

    IFProcessor ifProcessor = new IFProcessor();
    Log.log(5, "IFAction", "updateInvesteeMaster", "investee " + investeeDetail.getInvestee());
    Log.log(5, "IFAction", "updateInvesteeMaster", "mod investee " + investeeDetail.getModInvestee());
    Log.log(5, "IFAction", "updateInvesteeMaster", "new investee " + investeeDetail.getNewInvestee());
    ifProcessor.updateInvesteeMaster(investeeDetail, loggedUserId);

    String message = "Investee details saved Successfully";

    request.setAttribute("message", message);

    Log.log(4, "IFAction", "updateInvesteeMaster", "Exited");

    return mapping.findForward("success");
  }

  public ActionForward updateMaturityMaster(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response)
    throws Exception
  {
    Log.log(4, "IFAction", "updateMaturityMaster", "Entered");

    String maturityType = "";
    String maturityDescription = "";
    DynaActionForm dynaForm = (DynaActionForm)form;

    MaturityDetail matDetail = new MaturityDetail();
    BeanUtils.populate(matDetail, dynaForm.getMap());

    Log.log(4, "IFAction", "updateMaturityMaster", "mat type " + matDetail.getMaturityType());
    Log.log(4, "IFAction", "updateMaturityMaster", "mod mat type " + matDetail.getModMaturityType());
    Log.log(4, "IFAction", "updateMaturityMaster", "new mat type " + matDetail.getNewMaturityType());
    Log.log(4, "IFAction", "updateMaturityMaster", "mat desc " + matDetail.getMaturityDescription());

    IFProcessor ifProcessor = new IFProcessor();
    User loggedInUser = getUserInformation(request);
    String loggedUserId = loggedInUser.getUserId();
    ifProcessor.updateMaturityMaster(matDetail, loggedUserId);

    String message = "Maturity details saved Successfully";

    request.setAttribute("message", message);

    Log.log(4, "IFAction", "updateMaturityMaster", "Exited");

    return mapping.findForward("success");
  }

  public ActionForward updateRatingMaster(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response)
    throws Exception
  {
    Log.log(4, "IFAction", "updateRatingMaster", "Entered");
    String rating = "";
    String ratingDescription = "";
    String ratingGivenBy = "";

    Hashtable ratingDetails = new Hashtable();
    DynaActionForm dynaForm = (DynaActionForm)form;
    rating = (String)dynaForm.get("rating");
    String newRating = (String)dynaForm.get("newRating");
    String modRating = (String)dynaForm.get("modRating");
    ratingDescription = (String)dynaForm.get("ratingDescription");

    ratingDetails.put("Rating", rating);
    ratingDetails.put("New Rating", newRating);
    ratingDetails.put("Mod Rating", modRating);
    ratingDetails.put("Rating Description", ratingDescription);

    User loggedInUser = getUserInformation(request);
    String loggedUserId = loggedInUser.getUserId();
    ratingDetails.put("Updated By", loggedUserId);

    IFProcessor ifProcessor = new IFProcessor();
    ifProcessor.updateRatingMaster(ratingDetails);

    String message = "Rating details saved Successfully";

    request.setAttribute("message", message);

    Log.log(4, "IFAction", "updateRatingMaster", "Exited");

    return mapping.findForward("success");
  }

  public ActionForward showPeriodicProjection(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response)
    throws Exception
  {
    Log.log(4, "IFAction", "showPeriodicProjection", "Entered");

    DynaActionForm dynaForm = (DynaActionForm)form;

    dynaForm.initialize(mapping);

    Log.log(4, "IFAction", "showPeriodicProjection", "Exited");

    return mapping.findForward("success");
  }

  public ActionForward getPeriodicProjectionDetails(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response)
    throws Exception
  {
    Log.log(4, "IFAction", "getPeriodicProjectionDetails", "Entered");

    DynaActionForm dynaForm = (DynaActionForm)form;

    String startDate = (String)dynaForm.get("startDate");

    String endDate = (String)dynaForm.get("endDate");

    SimpleDateFormat format = new SimpleDateFormat("dd/MM/yyyy");

    java.util.Date stDate = format.parse(startDate, new ParsePosition(0));

    java.util.Date edDate = format.parse(endDate, new ParsePosition(0));

    IFProcessor processor = new IFProcessor();

    ArrayList projections = processor.getClaimProjection(stDate, edDate);

    if ((projections == null) || (projections.size() == 0))
    {
      throw new DatabaseException("No Data Available For Periodic Projection.");
    }
    if (projections != null)
    {
      Log.log(5, "IFAction", "getPeriodicProjectionDetails", "projections " + projections.size());
    }

    dynaForm.set("projections", projections);

    HttpSession session = request.getSession(false);
    session.setAttribute("projectionFlag", "1");

    Log.log(4, "IFAction", "getPeriodicProjectionDetails", "Exited");

    return mapping.findForward("success");
  }

  public ActionForward getCumulativeProjectionDetails(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response)
    throws Exception
  {
    DynaActionForm dynaForm = (DynaActionForm)form;

    String endDate = (String)dynaForm.get("endDate");

    SimpleDateFormat format = new SimpleDateFormat("dd/MM/yyyy");

    java.util.Date edDate = format.parse(endDate, new ParsePosition(0));

    IFProcessor processor = new IFProcessor();

    ArrayList projections = processor.getClaimProjection(null, edDate);

    if ((projections == null) || (projections.size() == 0))
    {
      throw new DatabaseException("No Data Available for Cumulative Projection.");
    }

    if (projections != null)
    {
      Log.log(5, "IFAction", "getPeriodicProjectionDetails", "projections " + projections.size());
    }
    dynaForm.set("projections", projections);

    HttpSession session = request.getSession(false);
    session.setAttribute("projectionFlag", "1");

    return mapping.findForward("success");
  }

  public ActionForward updateCumulativeProjectionDetails(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response)
    throws Exception
  {
    Log.log(4, "IFAction", "updateCumulativeProjectionDetails", "Entered");

    DynaActionForm dynaForm = (DynaActionForm)form;

    ArrayList projections = (ArrayList)dynaForm.get("projections");

    Log.log(5, "IFAction", "updateCumulativeProjectionDetails", "projections " + projections.size());

    double totalAmount = 0.0D;
    for (int i = 0; i < projections.size(); i++)
    {
      ProjectExpectedClaimDetail projection = (ProjectExpectedClaimDetail)projections.get(i);

      totalAmount += projection.getProjectedClaimAmount();
    }

    Log.log(5, "IFAction", "updateCumulativeProjectionDetails", "totalAmount " + totalAmount);

    String startDate = (String)dynaForm.get("startDate");

    String endDate = (String)dynaForm.get("endDate");

    Log.log(5, "IFAction", "updateCumulativeProjectionDetails", "startDate,endDate " + startDate + "," + endDate);

    SimpleDateFormat format = new SimpleDateFormat("dd/MM/yyyy");

    java.util.Date stDate = format.parse(startDate, new ParsePosition(0));

    java.util.Date edDate = format.parse(endDate, new ParsePosition(0));

    ProjectExpectedClaimDetail projectExpectedClaimDetail = new ProjectExpectedClaimDetail();
    projectExpectedClaimDetail.setStartDate(stDate);
    projectExpectedClaimDetail.setEndDate(edDate);
    projectExpectedClaimDetail.setProjectedClaimAmount(totalAmount);

    User loggedInUser = getUserInformation(request);
    String loggedUserId = loggedInUser.getUserId();
    IFProcessor ifProcessor = new IFProcessor();

    ifProcessor.saveProjectExpectedClaimDetail(projectExpectedClaimDetail, loggedUserId);

    String message = "Cumulative Projection details saved Successfully";

    request.setAttribute("message", message);

    Log.log(4, "IFAction", "updateCumulativeProjectionDetails", "Exited");

    return mapping.findForward("success");
  }

  public ActionForward updatePeriodicProjectionDetails(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response)
    throws Exception
  {
    Log.log(4, "IFAction", "updatePeriodicProjectionDetails", "Entered");

    DynaActionForm dynaForm = (DynaActionForm)form;

    ArrayList projections = (ArrayList)dynaForm.get("projections");

    Log.log(5, "IFAction", "updatePeriodicProjectionDetails", "projections " + projections.size());

    double totalAmount = 0.0D;
    for (int i = 0; i < projections.size(); i++)
    {
      ProjectExpectedClaimDetail projection = (ProjectExpectedClaimDetail)projections.get(i);

      totalAmount += projection.getProjectedClaimAmount();
    }

    Log.log(5, "IFAction", "updatePeriodicProjectionDetails", "totalAmount " + totalAmount);

    String startDate = (String)dynaForm.get("startDate");

    String endDate = (String)dynaForm.get("endDate");

    Log.log(5, "IFAction", "updatePeriodicProjectionDetails", "startDate,endDate " + startDate + "," + endDate);

    SimpleDateFormat format = new SimpleDateFormat("dd/MM/yyyy");

    java.util.Date stDate = format.parse(startDate, new ParsePosition(0));

    java.util.Date edDate = format.parse(endDate, new ParsePosition(0));

    ProjectExpectedClaimDetail projectExpectedClaimDetail = new ProjectExpectedClaimDetail();
    projectExpectedClaimDetail.setStartDate(stDate);
    projectExpectedClaimDetail.setEndDate(edDate);
    projectExpectedClaimDetail.setProjectedClaimAmount(totalAmount);

    User loggedInUser = getUserInformation(request);
    String loggedUserId = loggedInUser.getUserId();

    IFProcessor ifProcessor = new IFProcessor();
    ifProcessor.saveProjectExpectedClaimDetail(projectExpectedClaimDetail, loggedUserId);

    String message = "";

    HttpSession session = request.getSession(false);

    if ((((String)session.getAttribute("mainMenu")).equals(MenuOptions.getMenu("IF_PROJECT_EXPECTED_CLAIMS"))) && (session.getAttribute("subMenuItem").equals(MenuOptions.getMenu("IF_PROJECT_EXPECTED_CLAIMS_PERIODIC"))))
    {
      message = "Periodic Projection details saved Successfully";
    }
    if ((((String)session.getAttribute("mainMenu")).equals(MenuOptions.getMenu("IF_PROJECT_EXPECTED_CLAIMS"))) && (session.getAttribute("subMenuItem").equals(MenuOptions.getMenu("IF_PROJECT_EXPECTED_CLAIMS_CUMULATIVE"))))
    {
      message = "Cumulative Projection details saved Successfully";
    }

    request.setAttribute("message", message);

    Log.log(4, "IFAction", "updatePeriodicProjectionDetails", "Exited");

    return mapping.findForward("success");
  }

  public ActionForward showTDSDetails(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response)
    throws Exception
  {
    Log.log(4, "IFAction", "showTDSDetails", "Entered");
    DynaActionForm dynaForm = (DynaActionForm)form;
    dynaForm.initialize(mapping);
    HttpSession session = request.getSession(false);
    session.setAttribute("flag", "I");
    getAllInvestees(dynaForm);
    getInstrumentTypes(dynaForm);
    getReceiptNumbers(dynaForm);

    Log.log(4, "IFAction", "showTDSDetails", "Exited");

    return mapping.findForward("success");
  }

  public ActionForward updateTDSDetails(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response)
    throws Exception
  {
    Log.log(4, "IFAction", "updateTDSDetails", "Entered");
    Hashtable ratingDetails = new Hashtable();

    DynaActionForm dynaForm = (DynaActionForm)form;
    TDSDetail tdsDetail = new TDSDetail();
    String modifiedByUser = null;

    User loggedInUser = getUserInformation(request);
    String loggedUserId = loggedInUser.getUserId();

    BeanUtils.populate(tdsDetail, dynaForm.getMap());
    tdsDetail.setModifiedBy(loggedUserId);

    Log.log(5, "IFAction", "updateTDSDetails", "getInvestmentRefNumber " + tdsDetail.getInvestmentRefNumber());
    Log.log(5, "IFAction", "updateTDSDetails", "getTDSAmount " + tdsDetail.getTDSAmount());
    Log.log(5, "IFAction", "updateTDSDetails", "getReminderDate " + tdsDetail.getReminderDate());
    Log.log(5, "IFAction", "updateTDSDetails", "getTDSCertificateReceivedORNot " + tdsDetail.getTDSCertificateReceivedORNot());
    Log.log(5, "IFAction", "updateTDSDetails", "getTDSDeductedDate " + tdsDetail.getTDSDeductedDate());

    IFProcessor ifProcessor = new IFProcessor();
    ifProcessor.saveTDSDetail(tdsDetail);

    String message = "TDS details saved Successfully";

    request.setAttribute("message", message);

    Log.log(4, "IFAction", "updateTDSDetails", "Exited");

    return mapping.findForward("success");
  }

  public ActionForward showPlanInvestmentDetails(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response)
    throws Exception
  {
    Log.log(4, "IFAction", "showPlanInvestmentDetails", "Entered");

    DynaActionForm dynaForm = (DynaActionForm)form;
    IFProcessor ifProcessor = new IFProcessor();

    PlanInvestment planInvestment = ifProcessor.getPlanInvestmentDetails();

    BeanUtils.copyProperties(dynaForm, planInvestment);

    Log.log(4, "IFAction", "showPlanInvestmentDetails", "Exited");

    return mapping.findForward("success");
  }

  public ActionForward showPlanInvestmentBuyOrSell(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response)
    throws Exception
  {
    Log.log(4, "IFAction", "showPlanInvestmentBuyOrSell", "Entered");
    DynaActionForm dynaForm = (DynaActionForm)form;
    getAllInvestees(dynaForm);
    getInstrumentTypes(dynaForm);
    Log.log(4, "IFAction", "showPlanInvestmentBuyOrSell", "Exited");

    return mapping.findForward("success");
  }

  public ActionForward updatePlanInvestmentDetails(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response)
    throws Exception
  {
    Log.log(4, "IFAction", "updatePlanInvestmentDetails", "Entered");
    DynaActionForm dynaForm = (DynaActionForm)form;

    String flag = (String)dynaForm.get("isBuyOrSellRequest");

    String forwardFlag = "";
    if (flag.equals("Y"))
    {
      forwardFlag = "showBuySellRequest";
    }
    else
    {
      String message = "No Buy/Sell request is made.";
      request.setAttribute("message", message);
      forwardFlag = "success";
    }
    Log.log(4, "IFAction", "updatePlanInvestmentDetails", "Exited");

    return mapping.findForward(forwardFlag);
  }

  public ActionForward updatePlanInvestmentBuyOrSell(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response)
    throws Exception
  {
    Log.log(4, "IFAction", "updatePlanInvestmentBuyOrSell", "Entered");
    Hashtable ratingDetails = new Hashtable();
    DynaActionForm dynaForm = (DynaActionForm)form;
    InvestmentPlanningDetail investmentPlanningDetail = new InvestmentPlanningDetail();

    User loggedInUser = getUserInformation(request);
    String loggedUserId = loggedInUser.getUserId();

    BeanUtils.populate(investmentPlanningDetail, dynaForm.getMap());
    investmentPlanningDetail.setModifiedBy(loggedUserId);

    Log.log(5, "IFAction", "updatePlanInvestmentBuyOrSell", "getInvesteeName " + investmentPlanningDetail.getInvesteeName());
    Log.log(5, "IFAction", "updatePlanInvestmentBuyOrSell", "getInstrumentName " + investmentPlanningDetail.getInstrumentName());
    Log.log(5, "IFAction", "updatePlanInvestmentBuyOrSell", "getNoOfUnits " + investmentPlanningDetail.getNoOfUnits());
    Log.log(5, "IFAction", "updatePlanInvestmentBuyOrSell", "getIsBuyOrSellRequest " + investmentPlanningDetail.getIsBuyOrSellRequest());

    IFProcessor ifProcessor = new IFProcessor();
    ifProcessor.saveInvestmentPlanningDetail(investmentPlanningDetail);

    String message = "Investment Planning details saved Successfully";

    request.setAttribute("message", message);

    Log.log(4, "IFAction", "updatePlanInvestmentBuyOrSell", "Exited");

    return mapping.findForward("success");
  }

  public ActionForward showMakeRequestBuyOrSell(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response)
    throws Exception
  {
    Log.log(4, "IFAction", "showMakeRequestBuyOrSell", "Entered");

    HttpSession session = request.getSession(false);
    session.setAttribute("flag", "0");

    DynaActionForm dynaForm = (DynaActionForm)form;

    dynaForm.initialize(mapping);

    getAllInvestees(dynaForm);
    getInstrumentTypes(dynaForm);

    Log.log(4, "IFAction", "showMakeRequestBuyOrSell", "Exited");

    return mapping.findForward("success");
  }

  public ActionForward updateMakeRequestBuyOrSell(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response)
    throws Exception
  {
    Log.log(4, "IFAction", "updateMakeRequestBuyOrSell", "Entered");
    Hashtable ratingDetails = new Hashtable();
    DynaActionForm dynaForm = (DynaActionForm)form;
    BuySellDetail buySellDetail = new BuySellDetail();

    User loggedInUser = getUserInformation(request);
    String loggedUserId = loggedInUser.getUserId();

    BeanUtils.populate(buySellDetail, dynaForm.getMap());
    buySellDetail.setModifiedBy(loggedUserId);

    Log.log(5, "IFAction", "updateMakeRequestBuyOrSell", "getInvesteeName " + buySellDetail.getInvesteeName());
    Log.log(5, "IFAction", "updateMakeRequestBuyOrSell", "getInstrumentName " + buySellDetail.getInstrumentName());
    Log.log(5, "IFAction", "updateMakeRequestBuyOrSell", "getNoOfUnits " + buySellDetail.getNoOfUnits());
    if (buySellDetail.getInstrumentName().equalsIgnoreCase("FIXED DEPOSIT"))
    {
      buySellDetail.setNoOfUnits("1");
    }
    Log.log(5, "IFAction", "updateMakeRequestBuyOrSell", "getNoOfUnits " + buySellDetail.getNoOfUnits());
    Log.log(5, "IFAction", "updateMakeRequestBuyOrSell", "getWorthOfUnits " + buySellDetail.getWorthOfUnits());
    Log.log(5, "IFAction", "updateMakeRequestBuyOrSell", "getInvestmentRefNumber " + buySellDetail.getInvestmentRefNumber());
    Log.log(5, "IFAction", "updateMakeRequestBuyOrSell", "getIsBuyOrSellRequest " + buySellDetail.getIsBuyOrSellRequest());

    IFDAO ifDAO = new IFDAO();
    IFProcessor ifProcessor = new IFProcessor();
    if (buySellDetail.getIsBuyOrSellRequest().equalsIgnoreCase("B"))
    {
      String investeeName = buySellDetail.getInvesteeName();
      double corpusAmt = ifDAO.getCorpusAmount();
      Log.log(5, "IFAction", "updateMakeRequestBuyOrSell", "corpus amount " + corpusAmt);
      java.util.Date date = new java.util.Date();
      ArrayList investeeWiseExpDetails = ifProcessor.getExposure(date, date, corpusAmt);

      double ceilingAmt = 0.0D;
      boolean ceilingAvail = false;

      for (int i = 0; i < investeeWiseExpDetails.size(); i++)
      {
        ExposureDetails exposureDetailsTemp = (ExposureDetails)investeeWiseExpDetails.get(i);
        String invName = exposureDetailsTemp.getInvesteeName();
        Log.log(5, "IFAction", "updateMakeRequestBuyOrSell", "inv name " + invName);
        if (invName.equalsIgnoreCase(investeeName))
        {
          ceilingAvail = true;
          ceilingAmt = exposureDetailsTemp.getGapAvailableAmount();
          Log.log(5, "IFAction", "updateMakeRequestBuyOrSell", "ceiling amount " + ceilingAmt);
          break;
        }
      }

      if (!ceilingAvail)
      {
        throw new MessageException("Ceiling for Investee " + investeeName + " not available.");
      }
      if ((ceilingAmt == 0) || (Double.parseDouble(buySellDetail.getWorthOfUnits()) > ceilingAmt))
      {
        throw new MessageException("Invested Amount for " + investeeName + " has exceeded the Ceiling Limit");
      }
    }

    String buySellId = ifProcessor.saveBuyOrSellDetails(buySellDetail, request.getSession(false).getServletContext().getRealPath(""));

    String message = "Investment Reference number is " + buySellId + ". <br><b>Please note down the number for future reference</b>";

    request.setAttribute("message", message);

    Log.log(4, "IFAction", "updateMakeRequestBuyOrSell", "Exited");

    return mapping.findForward("success");
  }

  public ActionForward updateMakeRquestFundTransfer(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response)
    throws Exception
  {
    Log.log(4, "IFAction", "updateMakeRquestFundTransfer", "Entered");
    Hashtable ratingDetails = new Hashtable();
    DynaActionForm dynaForm = (DynaActionForm)form;
    FundTransferDetail fundTransferDetail = new FundTransferDetail();
    IFProcessor ifProcessor = new IFProcessor();
    ifProcessor.saveFundTransferDetail(fundTransferDetail);
    Log.log(4, "IFAction", "updateMakeRquestFundTransfer", "Exited");

    return mapping.findForward("success");
  }

  public ActionForward showFixedDetails(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response)
    throws Exception
  {
    Log.log(4, "IFAction", "showFixedDetails", "Entered");

    HttpSession session = request.getSession(false);
    IFProcessor ifProcessor = new IFProcessor();
    session.setAttribute("invFlag", "0");

    DynaActionForm dynaForm = (DynaActionForm)form;
    dynaForm.initialize(mapping);
    dynaForm.set("instrumentName", "Fixed Deposit");

    getAllInvestees(dynaForm);
    getInvestmentReferenceNumbers(dynaForm);
    getAllMaturities(dynaForm);
    getAllRatings(dynaForm);
    getInstrumentCategories(dynaForm);
    ArrayList agencyNames = ifProcessor.showRatingAgency();
    dynaForm.set("agencies", agencyNames);

    Log.log(4, "IFAction", "showFixedDetails", "Exited");

    return mapping.findForward("success");
  }

  public ActionForward showRatingDetailsForFixedDeposit(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response)
    throws Exception
  {
    Log.log(4, "IFAction", "showRatingDetailsForFixedDeposit", "Exited");

    HttpSession session = request.getSession(false);
    session.setAttribute("invFlag", "3");
    IFProcessor ifProcessor = new IFProcessor();
    ArrayList empty = new ArrayList();

    ArrayList allowableRatings = new ArrayList();

    DynaActionForm dynaForm = (DynaActionForm)form;
    String agencyName = (String)dynaForm.get("agency");

    String investee = (String)dynaForm.get("investeeName");

    String instrument = (String)dynaForm.get("instrumentName");

    String newInstrument = instrument.toUpperCase();

    String ceiling = null;

    if (agencyName.equals(""))
    {
      dynaForm.set("instrumentRatings", allowableRatings);
      dynaForm.set("ratingCeiling", "");
    }
    else
    {
      ifProcessor = new IFProcessor();
      allowableRatings = ifProcessor.getRatingsForAgency(agencyName);
      dynaForm.set("instrumentRatings", allowableRatings);
      ceiling = ifProcessor.getCeiling(agencyName, investee, newInstrument);
      dynaForm.set("ratingCeiling", ceiling);
    }

    Log.log(4, "IFAction", "showRatingDetailsForFixedDeposit", "Exited");

    return mapping.findForward("success");
  }

  private void getAllMaturities(DynaActionForm form)
    throws DatabaseException
  {
    IFProcessor ifProcessor = new IFProcessor();
    ArrayList maturities = ifProcessor.getAllMaturities();
    form.set("maturities", maturities);
  }

  public ActionForward updateFixedDetails(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response)
    throws Exception
  {
    Log.log(4, "IFAction", "updateFixedDetails", "Entered");
    Hashtable ratingDetails = new Hashtable();

    User loggedInUser = getUserInformation(request);
    String loggedUserId = loggedInUser.getUserId();

    DynaActionForm dynaForm = (DynaActionForm)form;
    FDDetail fdDetail = new FDDetail();
    BeanUtils.populate(fdDetail, dynaForm.getMap());

    Log.log(5, "IFAction", "updateFixedDetails", "getInvesteeName " + fdDetail.getInvesteeName());
    Log.log(5, "IFAction", "updateFixedDetails", "getInstrumentName " + fdDetail.getInstrumentName());
    Log.log(5, "IFAction", "updateFixedDetails", "getInvestmentName " + fdDetail.getInvestmentName());
    Log.log(5, "IFAction", "updateFixedDetails", "getPrincipalAmount " + fdDetail.getPrincipalAmount());
    Log.log(5, "IFAction", "updateFixedDetails", "getCompoundingFrequency " + fdDetail.getCompoundingFrequency());
    Log.log(5, "IFAction", "updateFixedDetails", "getInterestRate " + fdDetail.getInterestRate());

    Log.log(5, "IFAction", "updateFixedDetails", "getTenure " + fdDetail.getTenure());
    Log.log(5, "IFAction", "updateFixedDetails", "getTenureType " + fdDetail.getTenureType());
    Log.log(5, "IFAction", "updateFixedDetails", "getReceiptNumber " + fdDetail.getReceiptNumber());
    Log.log(5, "IFAction", "updateFixedDetails", "getDateOfDeposit " + fdDetail.getDateOfDeposit());
    Log.log(5, "IFAction", "updateFixedDetails", "getMaturityName " + fdDetail.getMaturityName());
    Log.log(5, "IFAction", "updateFixedDetails", "getMaturityDate " + fdDetail.getMaturityDate());

    IFProcessor ifProcessor = new IFProcessor();
    ArrayList receiptNos = ifProcessor.getFDReceiptNumbers();
    if (receiptNos.contains(fdDetail.getReceiptNumber()))
    {
      throw new MessageException("FD Receipt Number is not unique.");
    }

    Calendar calendar = Calendar.getInstance();
    calendar.setTime(fdDetail.getMaturityDate());
    int dayOfWeek = calendar.get(7);

    if (dayOfWeek == 1)
    {
      throw new MessageException("Maturity Date falls on Sunday. Please Change.");
    }

    ArrayList holidays = ifProcessor.getAllHolidays();

    if (holidays.contains(fdDetail.getMaturityDate()))
    {
      throw new MessageException("Maturity Date falls on a Holiday. Please change.");
    }

    IFDAO ifDAO = new IFDAO();
    double corpusAmt = ifDAO.getCorpusAmount();
    java.util.Date date = new java.util.Date();

    Map matDetails = new TreeMap();
    Map ioReportDetails = ifProcessor.showInflowOutflowReport(date, matDetails, "");
    Map mainDetails = (Map)ioReportDetails.get("DT");
    Set mainSet = mainDetails.keySet();
    Iterator mainIterator = mainSet.iterator();
    InflowOutflowReport ioReport = new InflowOutflowReport();
    while (mainIterator.hasNext())
    {
      ioReport = (InflowOutflowReport)mainDetails.get(mainIterator.next());
      if (ioReport.getBankName().substring(0, 9).equalsIgnoreCase("IDBI Bank"))
      {
        break;
      }
    }

    ExposureDetails exposureDetails = ifProcessor.getPositionDetails(date, date);

    Log.log(5, "IFAction", "updateFixedDetails", "matuirty amount " + exposureDetails.getMaturedAmount());
    Log.log(5, "IFAction", "updateFixedDetails", "stmt balance " + ioReport.getStmtBalance());
    Log.log(5, "IFAction", "updateFixedDetails", "fund transfer amount " + ioReport.getFundTransferInflow());
    Log.log(5, "IFAction", "updateFixedDetails", "chq issued but not presented amount " + ioReport.getChqissuedAmt());
    Log.log(5, "IFAction", "updateFixedDetails", "provisions " + ioReport.getProvisionFundsAmt());
    Log.log(5, "IFAction", "updateFixedDetails", "min balance " + ioReport.getMinBalance());
    Log.log(5, "IFAction", "updateFixedDetails", "invested amount " + exposureDetails.getInvestedAmount());

    double surplusAmt = exposureDetails.getMaturedAmount() + Double.parseDouble(ioReport.getStmtBalance()) + Double.parseDouble(ioReport.getFundTransferInflow()) - Double.parseDouble(ioReport.getChqissuedAmt()) - Double.parseDouble(ioReport.getProvisionFundsAmt()) - Double.parseDouble(ioReport.getMinBalance()) - exposureDetails.getInvestedAmount();

    double invCeilingAmt = 0.0D;
    double matCeilingAmt = 0.0D;
    double instCeilingAmt = 0.0D;
    double ceilingAmt = 0.0D;
    boolean ceilingAvail = false;

    String investeeName = fdDetail.getInvesteeName();
    ArrayList investeeWiseExpDetails = ifProcessor.getExposure(date, date, corpusAmt);

    for (int i = 0; i < investeeWiseExpDetails.size(); i++)
    {
      ExposureDetails exposureDetailsTemp = (ExposureDetails)investeeWiseExpDetails.get(i);
      String invName = exposureDetailsTemp.getInvesteeName();
      Log.log(5, "IFAction", "updateFixedDetails", "inv name " + invName);
      if (invName.equalsIgnoreCase(investeeName))
      {
        ceilingAvail = true;
        invCeilingAmt = exposureDetailsTemp.getGapAvailableAmount();
        break;
      }
    }

    if (!ceilingAvail)
    {
      throw new MessageException("Ceiling for Investee " + investeeName + " not available.");
    }

    ArrayList maturityCeilingArr = ifProcessor.getMaturityWiseDetails(date, date, surplusAmt);
    String maturityName = fdDetail.getMaturityName();
    ceilingAvail = false;

    for (int i = 0; i < maturityCeilingArr.size(); i++)
    {
      ExposureDetails exposureDetailsTemp = (ExposureDetails)maturityCeilingArr.get(i);
      String matName = exposureDetailsTemp.getMaturityName();
      Log.log(5, "IFAction", "updateFixedDetails", "mat name " + matName);
      if (matName.equalsIgnoreCase(maturityName))
      {
        ceilingAvail = true;
        matCeilingAmt = exposureDetailsTemp.getGapAvailableAmount();
        break;
      }
    }

    if (!ceilingAvail)
    {
      throw new MessageException("Ceiling for Maturity " + maturityName + " not available.");
    }

    ArrayList instCatCeilingArr = ifProcessor.getInstCategoryWiseDetails(date, date, surplusAmt);
    String instCatName = fdDetail.getInstrumentCategory();
    ceilingAvail = false;

    for (int i = 0; i < instCatCeilingArr.size(); i++)
    {
      ExposureDetails exposureDetailsTemp = (ExposureDetails)instCatCeilingArr.get(i);
      String instName = exposureDetailsTemp.getInstCatName();
      Log.log(5, "IFAction", "updateFixedDetails", "inst cat name " + instName);
      if (instName.equalsIgnoreCase(instCatName))
      {
        ceilingAvail = true;
        instCeilingAmt = exposureDetailsTemp.getGapAvailableAmount();
        break;
      }
    }

    if (!ceilingAvail)
    {
      throw new MessageException("Ceiling for Instrument Category " + instCatName + " not available.");
    }
    String limitExceeded = "";
    if ((instCeilingAmt == -1) && (matCeilingAmt == -1))
    {
      ceilingAmt = invCeilingAmt;
      limitExceeded = " for Investee - " + fdDetail.getInvesteeName();
    }
    else if (matCeilingAmt < instCeilingAmt)
    {
      if (invCeilingAmt < matCeilingAmt)
      {
        ceilingAmt = invCeilingAmt;
        limitExceeded = " for Investee - " + fdDetail.getInvesteeName();
      }
      else
      {
        ceilingAmt = matCeilingAmt;
        limitExceeded = " for Maturity - " + fdDetail.getMaturityName();
      }

    }
    else if (instCeilingAmt < invCeilingAmt)
    {
      ceilingAmt = instCeilingAmt;
      limitExceeded = " for Instrument Category - " + fdDetail.getInstrumentCategory();
    }
    else
    {
      ceilingAmt = invCeilingAmt;
      limitExceeded = " for Investee - " + fdDetail.getInvesteeName();
    }

    if ((ceilingAmt == 0) || (fdDetail.getPrincipalAmount() > ceilingAmt))
    {
      throw new MessageException("Invested Amount has exceeded the Ceiling Limits" + limitExceeded);
    }

    fdDetail.setModifiedBy(loggedUserId);

    ifProcessor.saveInvestmentDetail(fdDetail);
    Log.log(4, "IFAction", "updateFixedDetails", "Exited");

    String message = "Fixed deposit details saved Successfully";

    request.setAttribute("message", message);

    return mapping.findForward("success");
  }

  public ActionForward showCommercialPapersDetails(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response)
    throws Exception
  {
    Log.log(4, "IFAction", "showCommercialPapersDetails", "Entered");

    HttpSession session = request.getSession(false);
    session.setAttribute("invFlag", "0");
    IFProcessor ifProcessor = new IFProcessor();

    DynaActionForm dynaForm = (DynaActionForm)form;
    dynaForm.initialize(mapping);
    dynaForm.set("instrumentName", "Commercial Papers");

    getAllInvestees(dynaForm);
    getInstrumentFeatures(dynaForm);
    getInvestmentReferenceNumbers(dynaForm);
    getAllMaturities(dynaForm);
    getAllRatings(dynaForm);
    getInstrumentCategories(dynaForm);
    ArrayList agencyNames = ifProcessor.showRatingAgency();
    dynaForm.set("agencies", agencyNames);
    Log.log(4, "IFAction", "showCommercialPapersDetails", "Exited");

    return mapping.findForward("success");
  }

  public ActionForward showRatingDetailsForCommercialPapers(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response)
    throws Exception
  {
    Log.log(4, "IFAction", "showRatingDetailsForCommercialPapers", "Exited");

    HttpSession session = request.getSession(false);
    session.setAttribute("invFlag", "3");
    IFProcessor ifProcessor = new IFProcessor();
    ArrayList empty = new ArrayList();

    ArrayList allowableRatings = new ArrayList();

    DynaActionForm dynaForm = (DynaActionForm)form;
    String agencyName = (String)dynaForm.get("agency");

    String investee = (String)dynaForm.get("investeeName");

    String instrument = (String)dynaForm.get("instrumentName");

    String newInstrument = instrument.toUpperCase();

    String ceiling = null;

    if (agencyName.equals(""))
    {
      dynaForm.set("instrumentRatings", allowableRatings);
      dynaForm.set("ratingCeiling", "");
    }
    else
    {
      ifProcessor = new IFProcessor();
      allowableRatings = ifProcessor.getRatingsForAgency(agencyName);
      dynaForm.set("instrumentRatings", allowableRatings);
      ceiling = ifProcessor.getCeiling(agencyName, investee, newInstrument);
      dynaForm.set("ratingCeiling", ceiling);
    }

    Log.log(4, "IFAction", "showRatingDetailsForCommercialPapers", "Exited");

    return mapping.findForward("success");
  }

  public ActionForward updateCommercialPapersDetails(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response)
    throws Exception
  {
    Log.log(4, "IFAction", "updateCommercialPapersDetails", "Entered");
    Hashtable ratingDetails = new Hashtable();

    User loggedInUser = getUserInformation(request);
    String loggedUserId = loggedInUser.getUserId();

    DynaActionForm dynaForm = (DynaActionForm)form;
    CommercialPaperDetail commercialPaperDetail = new CommercialPaperDetail();
    BeanUtils.populate(commercialPaperDetail, dynaForm.getMap());
    commercialPaperDetail.setModifiedBy(loggedUserId);
    commercialPaperDetail.setTenureType("M");

    Log.log(5, "IFAction", "updateCommercialPapersDetails", "getInvesteeName " + commercialPaperDetail.getInvesteeName());
    Log.log(5, "IFAction", "updateCommercialPapersDetails", "getInstrumentName " + commercialPaperDetail.getInstrumentName());
    Log.log(5, "IFAction", "updateCommercialPapersDetails", "getInvestmentName " + commercialPaperDetail.getInvestmentName());
    Log.log(5, "IFAction", "updateCommercialPapersDetails", "getCommercialPaperNumber " + commercialPaperDetail.getCommercialPaperNumber());
    Log.log(5, "IFAction", "updateCommercialPapersDetails", "getNameOfCompany " + commercialPaperDetail.getNameOfCompany());
    Log.log(5, "IFAction", "updateCommercialPapersDetails", "getFaceValue " + commercialPaperDetail.getFaceValue());

    Log.log(5, "IFAction", "updateCommercialPapersDetails", "getNoOfCommercialPapers = " + commercialPaperDetail.getNoOfCommercialPapers());
    Log.log(5, "IFAction", "updateCommercialPapersDetails", "costOfPurchase = " + commercialPaperDetail.getCostOfPurchase());
    Log.log(5, "IFAction", "updateCommercialPapersDetails", "couponRate = " + commercialPaperDetail.getCouponRate());
    Log.log(5, "IFAction", "updateCommercialPapersDetails", "tenure = " + commercialPaperDetail.getTenure());
    Log.log(5, "IFAction", "updateCommercialPapersDetails", "tenureType = " + commercialPaperDetail.getTenureType());
    Log.log(5, "IFAction", "updateCommercialPapersDetails", "maturityName = " + commercialPaperDetail.getMaturityName());
    Log.log(5, "IFAction", "updateCommercialPapersDetails", "dateOfInvestment = " + commercialPaperDetail.getDateOfInvestment());
    Log.log(5, "IFAction", "updateCommercialPapersDetails", "dateOfMaturity = " + commercialPaperDetail.getMaturityDate());
    Log.log(5, "IFAction", "updateCommercialPapersDetails", "maturityAmount = " + commercialPaperDetail.getMaturityAmount());
    Log.log(5, "IFAction", "updateCommercialPapersDetails", "callOrPutOption = " + commercialPaperDetail.getCallOrPutOption());
    Log.log(5, "IFAction", "updateCommercialPapersDetails", "callOrPutDuration = " + commercialPaperDetail.getCallOrPutDuration());

    Calendar calendar = Calendar.getInstance();
    calendar.setTime(commercialPaperDetail.getMaturityDate());
    int dayOfWeek = calendar.get(7);

    if (dayOfWeek == 1)
    {
      throw new MessageException("Maturity Date falls on Sunday. Please Change.");
    }

    IFProcessor ifProcessor = new IFProcessor();
    ArrayList holidays = ifProcessor.getAllHolidays();

    if (holidays.contains(commercialPaperDetail.getMaturityDate()))
    {
      throw new MessageException("Maturity Date falls on a Holiday. Please change.");
    }

    IFDAO ifDAO = new IFDAO();
    double corpusAmt = ifDAO.getCorpusAmount();
    java.util.Date date = new java.util.Date();

    Map matDetails = new TreeMap();
    Map ioReportDetails = ifProcessor.showInflowOutflowReport(date, matDetails, "");
    Map mainDetails = (Map)ioReportDetails.get("DT");
    Set mainSet = mainDetails.keySet();
    Iterator mainIterator = mainSet.iterator();
    InflowOutflowReport ioReport = new InflowOutflowReport();
    while (mainIterator.hasNext())
    {
      ioReport = (InflowOutflowReport)mainDetails.get(mainIterator.next());
      if (ioReport.getBankName().substring(0, 9).equalsIgnoreCase("IDBI Bank"))
      {
        break;
      }
    }

    ExposureDetails exposureDetails = ifProcessor.getPositionDetails(date, date);

    Log.log(5, "IFAction", "updateCommercialPapersDetails", "matuirty amount " + exposureDetails.getMaturedAmount());
    Log.log(5, "IFAction", "updateCommercialPapersDetails", "stmt balance " + ioReport.getStmtBalance());
    Log.log(5, "IFAction", "updateCommercialPapersDetails", "fund transfer amount " + ioReport.getFundTransferInflow());
    Log.log(5, "IFAction", "updateCommercialPapersDetails", "chq issued but not presented amount " + ioReport.getChqissuedAmt());
    Log.log(5, "IFAction", "updateCommercialPapersDetails", "provisions " + ioReport.getProvisionFundsAmt());
    Log.log(5, "IFAction", "updateCommercialPapersDetails", "min balance " + ioReport.getMinBalance());
    Log.log(5, "IFAction", "updateCommercialPapersDetails", "invested amount " + exposureDetails.getInvestedAmount());

    double surplusAmt = exposureDetails.getMaturedAmount() + Double.parseDouble(ioReport.getStmtBalance()) + Double.parseDouble(ioReport.getFundTransferInflow()) - Double.parseDouble(ioReport.getChqissuedAmt()) - Double.parseDouble(ioReport.getProvisionFundsAmt()) - Double.parseDouble(ioReport.getMinBalance()) - exposureDetails.getInvestedAmount();

    double invCeilingAmt = 0.0D;
    double matCeilingAmt = 0.0D;
    double instCeilingAmt = 0.0D;
    double ceilingAmt = 0.0D;
    boolean ceilingAvail = false;

    String investeeName = commercialPaperDetail.getInvesteeName();
    ArrayList investeeWiseExpDetails = ifProcessor.getExposure(date, date, corpusAmt);

    for (int i = 0; i < investeeWiseExpDetails.size(); i++)
    {
      ExposureDetails exposureDetailsTemp = (ExposureDetails)investeeWiseExpDetails.get(i);
      String invName = exposureDetailsTemp.getInvesteeName();
      Log.log(5, "IFAction", "updateCommercialPapersDetails", "inv name " + invName);
      if (invName.equalsIgnoreCase(investeeName))
      {
        ceilingAvail = true;
        invCeilingAmt = exposureDetailsTemp.getGapAvailableAmount();
        break;
      }
    }

    if (!ceilingAvail)
    {
      throw new MessageException("Ceiling for Investee " + investeeName + " not available.");
    }

    ArrayList maturityCeilingArr = ifProcessor.getMaturityWiseDetails(date, date, surplusAmt);
    String maturityName = commercialPaperDetail.getMaturityName();
    ceilingAvail = false;

    for (int i = 0; i < maturityCeilingArr.size(); i++)
    {
      ExposureDetails exposureDetailsTemp = (ExposureDetails)maturityCeilingArr.get(i);
      String matName = exposureDetailsTemp.getMaturityName();
      Log.log(5, "IFAction", "updateCommercialPapersDetails", "mat name " + matName);
      if (matName.equalsIgnoreCase(maturityName))
      {
        ceilingAvail = true;
        matCeilingAmt = exposureDetailsTemp.getGapAvailableAmount();
        break;
      }
    }

    if (!ceilingAvail)
    {
      throw new MessageException("Ceiling for Maturity " + maturityName + " not available.");
    }

    ArrayList instCatCeilingArr = ifProcessor.getInstCategoryWiseDetails(date, date, surplusAmt);
    String instCatName = commercialPaperDetail.getInstrumentCategory();
    ceilingAvail = false;

    for (int i = 0; i < instCatCeilingArr.size(); i++)
    {
      ExposureDetails exposureDetailsTemp = (ExposureDetails)instCatCeilingArr.get(i);
      String instName = exposureDetailsTemp.getInstCatName();
      Log.log(5, "IFAction", "updateCommercialPapersDetails", "inst cat name " + instName);
      if (instName.equalsIgnoreCase(instCatName))
      {
        ceilingAvail = true;
        instCeilingAmt = exposureDetailsTemp.getGapAvailableAmount();
        break;
      }
    }

    if (!ceilingAvail)
    {
      throw new MessageException("Ceiling for Instrument Category " + instCatName + " not available.");
    }
    String limitExceeded = "";
    if ((instCeilingAmt == -1) && (matCeilingAmt == -1))
    {
      ceilingAmt = invCeilingAmt;
      limitExceeded = " for Investee - " + commercialPaperDetail.getInvesteeName();
    }
    else if (matCeilingAmt < instCeilingAmt)
    {
      if (invCeilingAmt < matCeilingAmt)
      {
        ceilingAmt = invCeilingAmt;
        limitExceeded = " for Investee - " + commercialPaperDetail.getInvesteeName();
      }
      else
      {
        ceilingAmt = matCeilingAmt;
        limitExceeded = " for Maturity - " + commercialPaperDetail.getMaturityName();
      }

    }
    else if (instCeilingAmt < invCeilingAmt)
    {
      ceilingAmt = instCeilingAmt;
      limitExceeded = " for Instrument Category - " + commercialPaperDetail.getInstrumentCategory();
    }
    else
    {
      ceilingAmt = invCeilingAmt;
      limitExceeded = " for Investee - " + commercialPaperDetail.getInvesteeName();
    }

    if ((ceilingAmt == 0) || (commercialPaperDetail.getCostOfPurchase() > ceilingAmt))
    {
      throw new MessageException("Invested Amount has exceeded the Ceiling Limits" + limitExceeded);
    }

    String[] instrfeatureArrayTemp = commercialPaperDetail.getInstrumentFeature();
    int cnt = instrfeatureArrayTemp.length;
    ifProcessor.saveInvestmentDetail(commercialPaperDetail);

    String message = "Commercial Paper details saved Successfully";

    request.setAttribute("message", message);

    Log.log(4, "IFAction", "updateCommercialPapersDetails", "Exited");

    return mapping.findForward("success");
  }

  public ActionForward showMutualFundsDetails(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response)
    throws Exception
  {
    Log.log(4, "IFAction", "showMutualFundsDetails", "Entered");

    HttpSession session = request.getSession(false);
    IFProcessor ifProcessor = new IFProcessor();
    session.setAttribute("invFlag", "0");

    DynaActionForm dynaForm = (DynaActionForm)form;

    dynaForm.initialize(mapping);
    dynaForm.set("instrumentName", "Mutual Funds");

    getAllInvestees(dynaForm);
    dynaForm.set("instrument", "Mutual Funds");
    getInstrumentSchemeTypes(dynaForm);
    getInvestmentReferenceNumbers(dynaForm);
    getAllMaturities(dynaForm);
    getAllRatings(dynaForm);
    getInstrumentCategories(dynaForm);
    ArrayList agencyNames = ifProcessor.showRatingAgency();
    dynaForm.set("agencies", agencyNames);
    Log.log(4, "IFAction", "showMutualFundsDetails", "Exited");

    return mapping.findForward("success");
  }

  public ActionForward showRatingDetailsForMutualFunds(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response)
    throws Exception
  {
    Log.log(4, "IFAction", "showRatingDetailsForMutualFunds", "Exited");

    HttpSession session = request.getSession(false);
    session.setAttribute("invFlag", "1");
    IFProcessor ifProcessor = new IFProcessor();
    ArrayList empty = new ArrayList();

    ArrayList allowableRatings = new ArrayList();

    DynaActionForm dynaForm = (DynaActionForm)form;
    String agencyName = (String)dynaForm.get("agency");

    String investee = (String)dynaForm.get("investeeName");

    String instrument = (String)dynaForm.get("instrumentName");

    String newInstrument = instrument.toUpperCase();

    String ceiling = null;

    if (agencyName.equals(""))
    {
      dynaForm.set("instrumentRatings", allowableRatings);
      dynaForm.set("ratingCeiling", "");
    }
    else
    {
      ifProcessor = new IFProcessor();
      allowableRatings = ifProcessor.getRatingsForAgency(agencyName);
      dynaForm.set("instrumentRatings", allowableRatings);
      ceiling = ifProcessor.getCeiling(agencyName, investee, newInstrument);
      dynaForm.set("ratingCeiling", ceiling);
    }

    Log.log(4, "IFAction", "showRatingDetailsForMutualFunds", "Exited");

    return mapping.findForward("success");
  }

  public ActionForward updateMutualFundsDetails(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response)
    throws Exception
  {
    Log.log(4, "IFAction", "updateMutualFundsDetails", "Entered");
    Hashtable ratingDetails = new Hashtable();

    User loggedInUser = getUserInformation(request);
    String loggedUserId = loggedInUser.getUserId();

    DynaActionForm dynaForm = (DynaActionForm)form;
    MutualFundDetail mutualFundDetail = new MutualFundDetail();
    BeanUtils.populate(mutualFundDetail, dynaForm.getMap());

    mutualFundDetail.setModifiedBy(loggedUserId);

    Log.log(5, "IFAction", "updateMutualFundsDetails", "getInvesteeName = " + mutualFundDetail.getInvesteeName());
    Log.log(5, "IFAction", "updateMutualFundsDetails", "getInstrumentName = " + mutualFundDetail.getInstrumentName());
    Log.log(5, "IFAction", "updateMutualFundsDetails", "getInvestmentName = " + mutualFundDetail.getInvestmentName());
    Log.log(5, "IFAction", "updateMutualFundsDetails", "mutualFundId = " + mutualFundDetail.getMutualFundId());
    Log.log(5, "IFAction", "updateMutualFundsDetails", "getParValue = " + mutualFundDetail.getParValue());
    Log.log(5, "IFAction", "updateMutualFundsDetails", "getCostOfPurchase = " + mutualFundDetail.getCostOfPurchase());
    Log.log(5, "IFAction", "updateMutualFundsDetails", "getDateOfPurchase = " + mutualFundDetail.getDateOfPurchase());
    Log.log(5, "IFAction", "updateMutualFundsDetails", "getNavAsOnDateOfPurchase = " + mutualFundDetail.getNavAsOnDateOfPurchase());
    Log.log(5, "IFAction", "updateMutualFundsDetails", "getNavAsOnDate = " + mutualFundDetail.getNavAsOnDate());
    Log.log(5, "IFAction", "updateMutualFundsDetails", "getNoOfUnits = " + mutualFundDetail.getNoOfUnits());
    Log.log(5, "IFAction", "updateMutualFundsDetails", "getIsinNumber = " + mutualFundDetail.getIsinNumber());
    Log.log(5, "IFAction", "updateMutualFundsDetails", "getMutualFundName = " + mutualFundDetail.getMutualFundName());
    Log.log(5, "IFAction", "updateMutualFundsDetails", "getOpenOrClose = " + mutualFundDetail.getOpenOrClose());
    Log.log(5, "IFAction", "updateMutualFundsDetails", "getSchemeNature = " + mutualFundDetail.getSchemeNature());
    Log.log(5, "IFAction", "updateMutualFundsDetails", "getExitLoad = " + mutualFundDetail.getExitLoad());
    Log.log(5, "IFAction", "updateMutualFundsDetails", "getEntryLoad = " + mutualFundDetail.getEntryLoad());
    Log.log(5, "IFAction", "updateMutualFundsDetails", "getMarkToMarket = " + mutualFundDetail.getMarkToMarket());
    Log.log(5, "IFAction", "updateMutualFundsDetails", "getDateOfSelling = " + mutualFundDetail.getDateOfSelling());
    Log.log(5, "IFAction", "updateMutualFundsDetails", "getReferenceNumber = " + mutualFundDetail.getReferenceNumber());

    IFProcessor ifProcessor = new IFProcessor();

    IFDAO ifDAO = new IFDAO();
    double corpusAmt = ifDAO.getCorpusAmount();
    java.util.Date date = new java.util.Date();

    Map matDetails = new TreeMap();
    Map ioReportDetails = ifProcessor.showInflowOutflowReport(date, matDetails, "");
    Map mainDetails = (Map)ioReportDetails.get("DT");
    Set mainSet = mainDetails.keySet();
    Iterator mainIterator = mainSet.iterator();
    InflowOutflowReport ioReport = new InflowOutflowReport();
    while (mainIterator.hasNext())
    {
      ioReport = (InflowOutflowReport)mainDetails.get(mainIterator.next());
      if (ioReport.getBankName().substring(0, 9).equalsIgnoreCase("IDBI Bank"))
      {
        break;
      }
    }

    ExposureDetails exposureDetails = ifProcessor.getPositionDetails(date, date);

    Log.log(5, "IFAction", "updateMutualFundsDetails", "matuirty amount " + exposureDetails.getMaturedAmount());
    Log.log(5, "IFAction", "updateMutualFundsDetails", "stmt balance " + ioReport.getStmtBalance());
    Log.log(5, "IFAction", "updateMutualFundsDetails", "fund transfer amount " + ioReport.getFundTransferInflow());
    Log.log(5, "IFAction", "updateMutualFundsDetails", "chq issued but not presented amount " + ioReport.getChqissuedAmt());
    Log.log(5, "IFAction", "updateMutualFundsDetails", "provisions " + ioReport.getProvisionFundsAmt());
    Log.log(5, "IFAction", "updateMutualFundsDetails", "min balance " + ioReport.getMinBalance());
    Log.log(5, "IFAction", "updateMutualFundsDetails", "invested amount " + exposureDetails.getInvestedAmount());

    double surplusAmt = exposureDetails.getMaturedAmount() + Double.parseDouble(ioReport.getStmtBalance()) + Double.parseDouble(ioReport.getFundTransferInflow()) - Double.parseDouble(ioReport.getChqissuedAmt()) - Double.parseDouble(ioReport.getProvisionFundsAmt()) - Double.parseDouble(ioReport.getMinBalance()) - exposureDetails.getInvestedAmount();

    double invCeilingAmt = 0.0D;
    double instCeilingAmt = 0.0D;
    double ceilingAmt = 0.0D;
    boolean ceilingAvail = false;

    String investeeName = mutualFundDetail.getInvesteeName();
    ArrayList investeeWiseExpDetails = ifProcessor.getExposure(date, date, corpusAmt);

    for (int i = 0; i < investeeWiseExpDetails.size(); i++)
    {
      ExposureDetails exposureDetailsTemp = (ExposureDetails)investeeWiseExpDetails.get(i);
      String invName = exposureDetailsTemp.getInvesteeName();
      Log.log(5, "IFAction", "updateMutualFundsDetails", "inv name " + invName);
      if (invName.equalsIgnoreCase(investeeName))
      {
        ceilingAvail = true;
        invCeilingAmt = exposureDetailsTemp.getGapAvailableAmount();
        break;
      }
    }

    if (!ceilingAvail)
    {
      throw new MessageException("Ceiling for Investee " + investeeName + " not available.");
    }

    ArrayList instCatCeilingArr = ifProcessor.getInstCategoryWiseDetails(date, date, surplusAmt);
    String instCatName = mutualFundDetail.getInstrumentCategory();
    ceilingAvail = false;

    for (int i = 0; i < instCatCeilingArr.size(); i++)
    {
      ExposureDetails exposureDetailsTemp = (ExposureDetails)instCatCeilingArr.get(i);
      String instName = exposureDetailsTemp.getInstCatName();
      Log.log(5, "IFAction", "updateMutualFundsDetails", "inst cat name " + instName);
      if (instName.equalsIgnoreCase(instCatName))
      {
        ceilingAvail = true;
        instCeilingAmt = exposureDetailsTemp.getGapAvailableAmount();
        break;
      }
    }

    if (!ceilingAvail)
    {
      throw new MessageException("Ceiling for Instrument Category " + instCatName + " not available.");
    }
    String limitExceeded = "";
    if (instCeilingAmt == -1)
    {
      ceilingAmt = invCeilingAmt;
      limitExceeded = " for Investee - " + mutualFundDetail.getInvesteeName();
    }
    else if (instCeilingAmt < invCeilingAmt)
    {
      ceilingAmt = instCeilingAmt;
      limitExceeded = " for Instrument Category - " + mutualFundDetail.getInstrumentCategory();
    }
    else
    {
      ceilingAmt = invCeilingAmt;
      limitExceeded = " for Investee - " + mutualFundDetail.getInvesteeName();
    }

    Log.log(4, "IFAction", "updateMutualFundsDetails", "mutualFundDetail.getCostOfPurchase() " + mutualFundDetail.getCostOfPurchase());
    Log.log(4, "IFAction", "updateMutualFundsDetails", "ceilingAmt " + ceilingAmt);

    if ((ceilingAmt == 0) || (mutualFundDetail.getCostOfPurchase() > ceilingAmt))
    {
      throw new MessageException("Invested Amount has exceeded the Ceiling Limits" + limitExceeded);
    }

    ifProcessor.saveInvestmentDetail(mutualFundDetail);

    String message = "Mutual Fund details saved Successfully";

    request.setAttribute("message", message);

    Log.log(4, "IFAction", "updateMutualFundsDetails", "Exited");

    return mapping.findForward("success");
  }

  public ActionForward showGovtSecuritiesDetails(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response)
    throws Exception
  {
    Log.log(4, "IFAction", "showGovtSecuritiesDetails", "Entered");

    HttpSession session = request.getSession(false);
    session.setAttribute("invFlag", "0");
    IFProcessor ifProcessor = new IFProcessor();

    DynaActionForm dynaForm = (DynaActionForm)form;
    dynaForm.initialize(mapping);
    dynaForm.set("instrumentName", "Government Securities");

    getAllInvestees(dynaForm);
    getInvestmentReferenceNumbers(dynaForm);
    getAllMaturities(dynaForm);
    getAllRatings(dynaForm);
    getInstrumentCategories(dynaForm);
    ArrayList agencyNames = ifProcessor.showRatingAgency();
    dynaForm.set("agencies", agencyNames);
    Log.log(4, "IFAction", "showGovtSecuritiesDetails", "Exited");

    return mapping.findForward("success");
  }

  public ActionForward showRatingDetailsForGovtSecurities(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response)
    throws Exception
  {
    Log.log(4, "IFAction", "showRatingDetailsForGovtSecurities", "Exited");

    HttpSession session = request.getSession(false);
    session.setAttribute("invFlag", "3");
    IFProcessor ifProcessor = new IFProcessor();
    ArrayList empty = new ArrayList();

    ArrayList allowableRatings = new ArrayList();

    DynaActionForm dynaForm = (DynaActionForm)form;
    String agencyName = (String)dynaForm.get("agency");

    String investee = (String)dynaForm.get("investeeName");

    String instrument = (String)dynaForm.get("instrumentName");

    String newInstrument = instrument.toUpperCase();

    String ceiling = null;

    if (agencyName.equals(""))
    {
      dynaForm.set("instrumentRatings", allowableRatings);
      dynaForm.set("ratingCeiling", "");
    }
    else
    {
      ifProcessor = new IFProcessor();
      allowableRatings = ifProcessor.getRatingsForAgency(agencyName);
      dynaForm.set("instrumentRatings", allowableRatings);
      ceiling = ifProcessor.getCeiling(agencyName, investee, newInstrument);
      dynaForm.set("ratingCeiling", ceiling);
    }

    Log.log(4, "IFAction", "showRatingDetailsForGovtSecurities", "Exited");

    return mapping.findForward("success");
  }

  public ActionForward updateGovtSecuritiesDetails(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response)
    throws Exception
  {
    Log.log(4, "IFAction", "updateGovtSecuritiesDetails", "Entered");
    Hashtable ratingDetails = new Hashtable();

    User loggedInUser = getUserInformation(request);
    String loggedUserId = loggedInUser.getUserId();

    DynaActionForm dynaForm = (DynaActionForm)form;
    GovtSecurityDetail govtSecurityDetail = new GovtSecurityDetail();
    BeanUtils.populate(govtSecurityDetail, dynaForm.getMap());

    govtSecurityDetail.setModifiedBy(loggedUserId);

    Log.log(5, "IFAction", "updateGovtSecuritiesDetails", "getInvesteeName = " + govtSecurityDetail.getInvesteeName());
    Log.log(5, "IFAction", "updateGovtSecuritiesDetails", "getInstrumentName = " + govtSecurityDetail.getInstrumentName());
    Log.log(5, "IFAction", "updateGovtSecuritiesDetails", "getInvestmentName = " + govtSecurityDetail.getInvestmentName());

    Log.log(5, "IFAction", "updateGovtSecuritiesDetails", "getSeriesName = " + govtSecurityDetail.getSeriesName());
    Log.log(5, "IFAction", "updateGovtSecuritiesDetails", "faceValue = " + govtSecurityDetail.getFaceValue());
    Log.log(5, "IFAction", "updateGovtSecuritiesDetails", "numberOfSecurities = " + govtSecurityDetail.getNumberOfSecurities());
    Log.log(5, "IFAction", "updateGovtSecuritiesDetails", "folioNumber = " + govtSecurityDetail.getFolioNumber());
    Log.log(5, "IFAction", "updateGovtSecuritiesDetails", "certificateNumber = " + govtSecurityDetail.getCertificateNumber());
    Log.log(5, "IFAction", "updateGovtSecuritiesDetails", "costOfPurchase = " + govtSecurityDetail.getCostOfPurchase());
    Log.log(5, "IFAction", "updateGovtSecuritiesDetails", "couponRate = " + govtSecurityDetail.getCouponRate());
    Log.log(5, "IFAction", "updateGovtSecuritiesDetails", "tenure = " + govtSecurityDetail.getTenure());
    Log.log(5, "IFAction", "updateGovtSecuritiesDetails", "tenureType = " + govtSecurityDetail.getTenureType());
    Log.log(5, "IFAction", "updateGovtSecuritiesDetails", "dateOfInvestment = " + govtSecurityDetail.getDateOfInvestment());
    Log.log(5, "IFAction", "updateGovtSecuritiesDetails", "dateOfMaturity = " + govtSecurityDetail.getMaturityDate());
    Log.log(5, "IFAction", "updateGovtSecuritiesDetails", "maturityAmount = " + govtSecurityDetail.getMaturityAmount());
    Log.log(5, "IFAction", "updateGovtSecuritiesDetails", "callOrPutOption = " + govtSecurityDetail.getCallOrPutOption());
    Log.log(5, "IFAction", "updateGovtSecuritiesDetails", "callOrPutDuration = " + govtSecurityDetail.getCallOrPutDuration());
    Log.log(5, "IFAction", "updateGovtSecuritiesDetails", "maturityName = " + govtSecurityDetail.getMaturityName());

    Calendar calendar = Calendar.getInstance();
    calendar.setTime(govtSecurityDetail.getMaturityDate());
    int dayOfWeek = calendar.get(7);

    if (dayOfWeek == 1)
    {
      throw new MessageException("Maturity Date falls on Sunday. Please Change.");
    }

    IFProcessor ifProcessor = new IFProcessor();
    ArrayList holidays = ifProcessor.getAllHolidays();

    if (holidays.contains(govtSecurityDetail.getMaturityDate()))
    {
      throw new MessageException("Maturity Date falls on a Holiday. Please change.");
    }

    IFDAO ifDAO = new IFDAO();
    double corpusAmt = ifDAO.getCorpusAmount();
    java.util.Date date = new java.util.Date();

    Map matDetails = new TreeMap();
    Map ioReportDetails = ifProcessor.showInflowOutflowReport(date, matDetails, "");
    Map mainDetails = (Map)ioReportDetails.get("DT");
    Set mainSet = mainDetails.keySet();
    Iterator mainIterator = mainSet.iterator();
    InflowOutflowReport ioReport = new InflowOutflowReport();
    while (mainIterator.hasNext())
    {
      ioReport = (InflowOutflowReport)mainDetails.get(mainIterator.next());
      if (ioReport.getBankName().substring(0, 9).equalsIgnoreCase("IDBI Bank"))
      {
        break;
      }
    }

    ExposureDetails exposureDetails = ifProcessor.getPositionDetails(date, date);

    Log.log(5, "IFAction", "updateGovtSecuritiesDetails", "matuirty amount " + exposureDetails.getMaturedAmount());
    Log.log(5, "IFAction", "updateGovtSecuritiesDetails", "stmt balance " + ioReport.getStmtBalance());
    Log.log(5, "IFAction", "updateGovtSecuritiesDetails", "fund transfer amount " + ioReport.getFundTransferInflow());
    Log.log(5, "IFAction", "updateGovtSecuritiesDetails", "chq issued but not presented amount " + ioReport.getChqissuedAmt());
    Log.log(5, "IFAction", "updateGovtSecuritiesDetails", "provisions " + ioReport.getProvisionFundsAmt());
    Log.log(5, "IFAction", "updateGovtSecuritiesDetails", "min balance " + ioReport.getMinBalance());
    Log.log(5, "IFAction", "updateGovtSecuritiesDetails", "invested amount " + exposureDetails.getInvestedAmount());

    double surplusAmt = exposureDetails.getMaturedAmount() + Double.parseDouble(ioReport.getStmtBalance()) + Double.parseDouble(ioReport.getFundTransferInflow()) - Double.parseDouble(ioReport.getChqissuedAmt()) - Double.parseDouble(ioReport.getProvisionFundsAmt()) - Double.parseDouble(ioReport.getMinBalance()) - exposureDetails.getInvestedAmount();

    double invCeilingAmt = 0.0D;
    double matCeilingAmt = 0.0D;
    double instCeilingAmt = 0.0D;
    double ceilingAmt = 0.0D;
    boolean ceilingAvail = false;

    String investeeName = govtSecurityDetail.getInvesteeName();
    ArrayList investeeWiseExpDetails = ifProcessor.getExposure(date, date, corpusAmt);

    for (int i = 0; i < investeeWiseExpDetails.size(); i++)
    {
      ExposureDetails exposureDetailsTemp = (ExposureDetails)investeeWiseExpDetails.get(i);
      String invName = exposureDetailsTemp.getInvesteeName();
      Log.log(5, "IFAction", "updateGovtSecuritiesDetails", "inv name " + invName);
      if (invName.equalsIgnoreCase(investeeName))
      {
        ceilingAvail = true;
        invCeilingAmt = exposureDetailsTemp.getGapAvailableAmount();
        break;
      }
    }

    if (!ceilingAvail)
    {
      throw new MessageException("Ceiling for Investee " + investeeName + " not available.");
    }

    ArrayList maturityCeilingArr = ifProcessor.getMaturityWiseDetails(date, date, surplusAmt);
    String maturityName = govtSecurityDetail.getMaturityName();
    ceilingAvail = false;

    for (int i = 0; i < maturityCeilingArr.size(); i++)
    {
      ExposureDetails exposureDetailsTemp = (ExposureDetails)maturityCeilingArr.get(i);
      String matName = exposureDetailsTemp.getMaturityName();
      Log.log(5, "IFAction", "updateGovtSecuritiesDetails", "mat name " + matName);
      if (matName.equalsIgnoreCase(maturityName))
      {
        ceilingAvail = true;
        matCeilingAmt = exposureDetailsTemp.getGapAvailableAmount();
        break;
      }
    }

    if (!ceilingAvail)
    {
      throw new MessageException("Ceiling for Maturity " + maturityName + " not available.");
    }

    ArrayList instCatCeilingArr = ifProcessor.getInstCategoryWiseDetails(date, date, surplusAmt);
    String instCatName = govtSecurityDetail.getInstrumentCategory();
    ceilingAvail = false;

    for (int i = 0; i < instCatCeilingArr.size(); i++)
    {
      ExposureDetails exposureDetailsTemp = (ExposureDetails)instCatCeilingArr.get(i);
      String instName = exposureDetailsTemp.getInstCatName();
      Log.log(5, "IFAction", "updateGovtSecuritiesDetails", "inst cat name " + instName);
      if (instName.equalsIgnoreCase(instCatName))
      {
        ceilingAvail = true;
        instCeilingAmt = exposureDetailsTemp.getGapAvailableAmount();
        break;
      }
    }

    if (!ceilingAvail)
    {
      throw new MessageException("Ceiling for Instrument Category " + instCatName + " not available.");
    }
    String limitExceeded = "";
    if ((instCeilingAmt == -1) && (matCeilingAmt == -1))
    {
      ceilingAmt = invCeilingAmt;
      limitExceeded = " for Investee - " + govtSecurityDetail.getInvesteeName();
    }
    else if (matCeilingAmt < instCeilingAmt)
    {
      if (invCeilingAmt < matCeilingAmt)
      {
        ceilingAmt = invCeilingAmt;
        limitExceeded = " for Investee - " + govtSecurityDetail.getInvesteeName();
      }
      else
      {
        ceilingAmt = matCeilingAmt;
        limitExceeded = " for Maturity - " + govtSecurityDetail.getMaturityName();
      }

    }
    else if (instCeilingAmt < invCeilingAmt)
    {
      ceilingAmt = instCeilingAmt;
      limitExceeded = " for Instrument Category - " + govtSecurityDetail.getInstrumentCategory();
    }
    else
    {
      ceilingAmt = invCeilingAmt;
      limitExceeded = " for Investee - " + govtSecurityDetail.getInvesteeName();
    }

    if ((ceilingAmt == 0) || (govtSecurityDetail.getCostOfPurchase() > ceilingAmt))
    {
      throw new MessageException("Invested Amount has exceeded the Ceiling Limits" + limitExceeded);
    }

    ifProcessor.saveInvestmentDetail(govtSecurityDetail);

    String message = "Government Security details saved Successfully";

    request.setAttribute("message", message);

    Log.log(4, "IFAction", "updateGovtSecuritiesDetails", "Exited");

    return mapping.findForward("success");
  }

  public ActionForward showDebenturesDetails(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response)
    throws Exception
  {
    Log.log(4, "IFAction", "showDebenturesDetails", "Entered");

    HttpSession session = request.getSession(false);
    session.setAttribute("invFlag", "0");
    IFProcessor ifProcessor = new IFProcessor();

    DynaActionForm dynaForm = (DynaActionForm)form;
    dynaForm.initialize(mapping);
    dynaForm.set("instrumentName", "Debentures");

    getAllInvestees(dynaForm);
    getInstrumentFeatures(dynaForm);
    getInvestmentReferenceNumbers(dynaForm);
    getAllMaturities(dynaForm);
    getAllRatings(dynaForm);
    getInstrumentCategories(dynaForm);
    ArrayList agencyNames = ifProcessor.showRatingAgency();
    dynaForm.set("agencies", agencyNames);
    Log.log(4, "IFAction", "showDebenturesDetails", "Exited");

    return mapping.findForward("success");
  }

  public ActionForward showRatingDetailsForDebentures(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response)
    throws Exception
  {
    Log.log(4, "IFAction", "showRatingDetailsForDebentures", "Exited");

    HttpSession session = request.getSession(false);
    session.setAttribute("invFlag", "3");
    IFProcessor ifProcessor = new IFProcessor();
    ArrayList empty = new ArrayList();

    ArrayList allowableRatings = new ArrayList();

    DynaActionForm dynaForm = (DynaActionForm)form;
    String agencyName = (String)dynaForm.get("agency");

    String investee = (String)dynaForm.get("investeeName");

    String instrument = (String)dynaForm.get("instrumentName");

    String newInstrument = instrument.toUpperCase();

    String ceiling = null;

    if (agencyName.equals(""))
    {
      dynaForm.set("instrumentRatings", allowableRatings);
      dynaForm.set("ratingCeiling", "");
    }
    else
    {
      ifProcessor = new IFProcessor();
      allowableRatings = ifProcessor.getRatingsForAgency(agencyName);
      dynaForm.set("instrumentRatings", allowableRatings);
      ceiling = ifProcessor.getCeiling(agencyName, investee, newInstrument);
      dynaForm.set("ratingCeiling", ceiling);
    }

    Log.log(4, "IFAction", "showRatingDetailsForDebentures", "Exited");

    return mapping.findForward("success");
  }

  public ActionForward updateDebenturesDetails(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response)
    throws Exception
  {
    Log.log(4, "IFAction", "updateDebenturesDetails", "Entered");
    Hashtable ratingDetails = new Hashtable();

    User loggedInUser = getUserInformation(request);
    String loggedUserId = loggedInUser.getUserId();

    DynaActionForm dynaForm = (DynaActionForm)form;
    DebentureDetail debentureDetail = new DebentureDetail();
    BeanUtils.populate(debentureDetail, dynaForm.getMap());
    debentureDetail.setModifiedBy(loggedUserId);

    Log.log(5, "IFAction", "updateDebenturesDetails", "getInvesteeName = " + debentureDetail.getInvesteeName());
    Log.log(5, "IFAction", "updateDebenturesDetails", "getInvesteeName = " + debentureDetail.getInstrumentName());
    Log.log(5, "IFAction", "updateDebenturesDetails", "getInvesteeName = " + debentureDetail.getInvestmentName());
    Log.log(5, "IFAction", "updateDebenturesDetails", "debentureName = " + debentureDetail.getDebentureName());
    Log.log(5, "IFAction", "updateDebenturesDetails", "faceValue = " + debentureDetail.getFaceValue());
    Log.log(5, "IFAction", "updateDebenturesDetails", "numberOfSecurities = " + debentureDetail.getNumberOfSecurities());
    Log.log(5, "IFAction", "updateDebenturesDetails", "folioNumber = " + debentureDetail.getFolioNumber());
    Log.log(5, "IFAction", "updateDebenturesDetails", "certificateNumber = " + debentureDetail.getCertificateNumber());
    Log.log(5, "IFAction", "updateDebenturesDetails", "costOfPurchase = " + debentureDetail.getCostOfPurchase());
    Log.log(5, "IFAction", "updateDebenturesDetails", "couponRate = " + debentureDetail.getCouponRate());
    Log.log(5, "IFAction", "updateDebenturesDetails", "tenure = " + debentureDetail.getTenure());
    Log.log(5, "IFAction", "updateDebenturesDetails", "tenureType = " + debentureDetail.getTenureType());
    Log.log(5, "IFAction", "updateDebenturesDetails", "maturityName = " + debentureDetail.getMaturityName());
    Log.log(5, "IFAction", "updateDebenturesDetails", "dateOfInvestment = " + debentureDetail.getDateOfInvestment());
    Log.log(5, "IFAction", "updateDebenturesDetails", "dateOfMaturity = " + debentureDetail.getMaturityDate());
    Log.log(5, "IFAction", "updateDebenturesDetails", "maturityAmount = " + debentureDetail.getMaturityAmount());
    Log.log(5, "IFAction", "updateDebenturesDetails", "callOrPutOption = " + debentureDetail.getCallOrPutOption());
    Log.log(5, "IFAction", "updateDebenturesDetails", "callOrPutDuration = " + debentureDetail.getCallOrPutDuration());

    Calendar calendar = Calendar.getInstance();
    calendar.setTime(debentureDetail.getMaturityDate());
    int dayOfWeek = calendar.get(7);

    if (dayOfWeek == 1)
    {
      throw new MessageException("Maturity Date falls on Sunday. Please Change.");
    }

    IFProcessor ifProcessor = new IFProcessor();
    ArrayList holidays = ifProcessor.getAllHolidays();

    if (holidays.contains(debentureDetail.getMaturityDate()))
    {
      throw new MessageException("Maturity Date falls on a Holiday. Please change.");
    }

    IFDAO ifDAO = new IFDAO();
    double corpusAmt = ifDAO.getCorpusAmount();
    java.util.Date date = new java.util.Date();

    Map matDetails = new TreeMap();
    Map ioReportDetails = ifProcessor.showInflowOutflowReport(date, matDetails, "");
    Map mainDetails = (Map)ioReportDetails.get("DT");
    Set mainSet = mainDetails.keySet();
    Iterator mainIterator = mainSet.iterator();
    InflowOutflowReport ioReport = new InflowOutflowReport();
    while (mainIterator.hasNext())
    {
      ioReport = (InflowOutflowReport)mainDetails.get(mainIterator.next());
      if (ioReport.getBankName().substring(0, 9).equalsIgnoreCase("IDBI Bank"))
      {
        break;
      }
    }

    ExposureDetails exposureDetails = ifProcessor.getPositionDetails(date, date);

    Log.log(5, "IFAction", "updateDebenturesDetails", "matuirty amount " + exposureDetails.getMaturedAmount());
    Log.log(5, "IFAction", "updateDebenturesDetails", "stmt balance " + ioReport.getStmtBalance());
    Log.log(5, "IFAction", "updateDebenturesDetails", "fund transfer amount " + ioReport.getFundTransferInflow());
    Log.log(5, "IFAction", "updateDebenturesDetails", "chq issued but not presented amount " + ioReport.getChqissuedAmt());
    Log.log(5, "IFAction", "updateDebenturesDetails", "provisions " + ioReport.getProvisionFundsAmt());
    Log.log(5, "IFAction", "updateDebenturesDetails", "min balance " + ioReport.getMinBalance());
    Log.log(5, "IFAction", "updateDebenturesDetails", "invested amount " + exposureDetails.getInvestedAmount());

    double surplusAmt = exposureDetails.getMaturedAmount() + Double.parseDouble(ioReport.getStmtBalance()) + Double.parseDouble(ioReport.getFundTransferInflow()) - Double.parseDouble(ioReport.getChqissuedAmt()) - Double.parseDouble(ioReport.getProvisionFundsAmt()) - Double.parseDouble(ioReport.getMinBalance()) - exposureDetails.getInvestedAmount();

    double invCeilingAmt = 0.0D;
    double matCeilingAmt = 0.0D;
    double instCeilingAmt = 0.0D;
    double ceilingAmt = 0.0D;
    boolean ceilingAvail = false;

    String investeeName = debentureDetail.getInvesteeName();
    ArrayList investeeWiseExpDetails = ifProcessor.getExposure(date, date, corpusAmt);

    for (int i = 0; i < investeeWiseExpDetails.size(); i++)
    {
      ExposureDetails exposureDetailsTemp = (ExposureDetails)investeeWiseExpDetails.get(i);
      String invName = exposureDetailsTemp.getInvesteeName();
      Log.log(5, "IFAction", "updateDebenturesDetails", "inv name " + invName);
      if (invName.equalsIgnoreCase(investeeName))
      {
        ceilingAvail = true;
        invCeilingAmt = exposureDetailsTemp.getGapAvailableAmount();
        break;
      }
    }

    if (!ceilingAvail)
    {
      throw new MessageException("Ceiling for Investee " + investeeName + " not available.");
    }

    ArrayList maturityCeilingArr = ifProcessor.getMaturityWiseDetails(date, date, surplusAmt);
    String maturityName = debentureDetail.getMaturityName();
    ceilingAvail = false;

    for (int i = 0; i < maturityCeilingArr.size(); i++)
    {
      ExposureDetails exposureDetailsTemp = (ExposureDetails)maturityCeilingArr.get(i);
      String matName = exposureDetailsTemp.getMaturityName();
      Log.log(5, "IFAction", "updateDebenturesDetails", "mat name " + matName);
      if (matName.equalsIgnoreCase(maturityName))
      {
        ceilingAvail = true;
        matCeilingAmt = exposureDetailsTemp.getGapAvailableAmount();
        break;
      }
    }

    if (!ceilingAvail)
    {
      throw new MessageException("Ceiling for Maturity " + maturityName + " not available.");
    }

    ArrayList instCatCeilingArr = ifProcessor.getInstCategoryWiseDetails(date, date, surplusAmt);
    String instCatName = debentureDetail.getInstrumentCategory();
    ceilingAvail = false;

    for (int i = 0; i < instCatCeilingArr.size(); i++)
    {
      ExposureDetails exposureDetailsTemp = (ExposureDetails)instCatCeilingArr.get(i);
      String instName = exposureDetailsTemp.getInstCatName();
      Log.log(5, "IFAction", "updateDebenturesDetails", "inst cat name " + instName);
      if (instName.equalsIgnoreCase(instCatName))
      {
        ceilingAvail = true;
        instCeilingAmt = exposureDetailsTemp.getGapAvailableAmount();
        break;
      }
    }

    if (!ceilingAvail)
    {
      throw new MessageException("Ceiling for Instrument Category " + instCatName + " not available.");
    }
    String limitExceeded = "";
    if ((instCeilingAmt == -1) && (matCeilingAmt == -1))
    {
      ceilingAmt = invCeilingAmt;
      limitExceeded = " for Investee - " + debentureDetail.getInvesteeName();
    }
    else if (matCeilingAmt < instCeilingAmt)
    {
      if (invCeilingAmt < matCeilingAmt)
      {
        ceilingAmt = invCeilingAmt;
        limitExceeded = " for Investee - " + debentureDetail.getInvesteeName();
      }
      else
      {
        ceilingAmt = matCeilingAmt;
        limitExceeded = " for Maturity - " + debentureDetail.getMaturityName();
      }

    }
    else if (instCeilingAmt < invCeilingAmt)
    {
      ceilingAmt = instCeilingAmt;
      limitExceeded = " for Instrument Category - " + debentureDetail.getInstrumentCategory();
    }
    else
    {
      ceilingAmt = invCeilingAmt;
      limitExceeded = " for Investee - " + debentureDetail.getInvesteeName();
    }

    if ((ceilingAmt == 0) || (debentureDetail.getCostOfPurchase() > ceilingAmt))
    {
      throw new MessageException("Invested Amount has exceeded the Ceiling Limits" + limitExceeded);
    }

    ifProcessor.saveInvestmentDetail(debentureDetail);

    String message = "Debenture details saved Successfully";

    request.setAttribute("message", message);

    Log.log(4, "IFAction", "updateDebenturesDetails", "Exited");

    return mapping.findForward("success");
  }

  public ActionForward showBondDetails(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response)
    throws Exception
  {
    Log.log(4, "IFAction", "showBondDetails", "Entered");

    HttpSession session = request.getSession(false);
    session.setAttribute("invFlag", "0");
    IFProcessor ifProcessor = new IFProcessor();
    DynaActionForm dynaForm = (DynaActionForm)form;
    dynaForm.initialize(mapping);
    dynaForm.set("instrumentName", "Bonds");
    getAllInvestees(dynaForm);
    getInvestmentReferenceNumbers(dynaForm);
    getAllMaturities(dynaForm);
    getAllRatings(dynaForm);
    getInstrumentCategories(dynaForm);
    ArrayList agencyNames = ifProcessor.showRatingAgency();
    dynaForm.set("agencies", agencyNames);
    Log.log(4, "IFAction", "showBondDetails", "Exited");

    return mapping.findForward("success");
  }

  public ActionForward showRatingDetailsForBonds(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response)
    throws Exception
  {
    Log.log(4, "IFAction", "showRatingDetailsForBonds", "Exited");

    HttpSession session = request.getSession(false);
    session.setAttribute("invFlag", "3");
    IFProcessor ifProcessor = new IFProcessor();
    ArrayList empty = new ArrayList();

    ArrayList allowableRatings = new ArrayList();

    DynaActionForm dynaForm = (DynaActionForm)form;
    String agencyName = (String)dynaForm.get("agency");

    String investee = (String)dynaForm.get("investeeName");

    String instrument = (String)dynaForm.get("instrumentName");

    String newInstrument = instrument.toUpperCase();

    String ceiling = null;

    if (agencyName.equals(""))
    {
      dynaForm.set("instrumentRatings", allowableRatings);
      dynaForm.set("ratingCeiling", "");
    }
    else
    {
      ifProcessor = new IFProcessor();
      allowableRatings = ifProcessor.getRatingsForAgency(agencyName);
      dynaForm.set("instrumentRatings", allowableRatings);
      ceiling = ifProcessor.getCeiling(agencyName, investee, newInstrument);
      dynaForm.set("ratingCeiling", ceiling);
    }

    Log.log(4, "IFAction", "showRatingDetailsForBonds", "Exited");

    return mapping.findForward("success");
  }

  public ActionForward updateBondDetails(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response)
    throws Exception
  {
    Log.log(4, "IFAction", "updateBondDetails", "Entered");
    Hashtable ratingDetails = new Hashtable();

    User loggedInUser = getUserInformation(request);
    String loggedUserId = loggedInUser.getUserId();

    DynaActionForm dynaForm = (DynaActionForm)form;
    BondsDetail bondsDetail = new BondsDetail();
    BeanUtils.populate(bondsDetail, dynaForm.getMap());
    bondsDetail.setModifiedBy(loggedUserId);

    Log.log(5, "IFAction", "updateBondDetails", "getInvesteeName = " + bondsDetail.getInvesteeName());
    Log.log(5, "IFAction", "updateBondDetails", "getInstrumentName = " + bondsDetail.getInstrumentName());
    Log.log(5, "IFAction", "updateBondDetails", "getInvestmentName = " + bondsDetail.getInvestmentName());
    Log.log(5, "IFAction", "updateBondDetails", "bondName = " + bondsDetail.getBondName());
    Log.log(5, "IFAction", "updateBondDetails", "faceValue = " + bondsDetail.getFaceValue());
    Log.log(5, "IFAction", "updateBondDetails", "noOfSecurities = " + bondsDetail.getNumberOfSecurities());
    Log.log(5, "IFAction", "updateBondDetails", "folioNumber = " + bondsDetail.getFolioNumber());
    Log.log(5, "IFAction", "updateBondDetails", "certificateNumber = " + bondsDetail.getCertificateNumber());
    Log.log(5, "IFAction", "updateBondDetails", "costOfPurchase = " + bondsDetail.getCostOfPurchase());
    Log.log(5, "IFAction", "updateBondDetails", "couponRate = " + bondsDetail.getCouponRate());
    Log.log(5, "IFAction", "updateBondDetails", "tenure = " + bondsDetail.getTenure());
    Log.log(5, "IFAction", "updateBondDetails", "tenureType = " + bondsDetail.getTenureType());
    Log.log(5, "IFAction", "updateBondDetails", "maturityName = " + bondsDetail.getMaturityName());
    Log.log(5, "IFAction", "updateBondDetails", "dateOfInvestment = " + bondsDetail.getDateOfInvestment());
    Log.log(5, "IFAction", "updateBondDetails", "dateOfMaturity = " + bondsDetail.getMaturityDate());
    Log.log(5, "IFAction", "updateBondDetails", "maturityAmount = " + bondsDetail.getMaturityAmount());
    Log.log(5, "IFAction", "updateBondDetails", "callOrPutOption = " + bondsDetail.getCallOrPutOption());
    Log.log(5, "IFAction", "updateBondDetails", "callOrPutDuration = " + bondsDetail.getCallOrPutDuration());

    Calendar calendar = Calendar.getInstance();
    calendar.setTime(bondsDetail.getMaturityDate());
    int dayOfWeek = calendar.get(7);

    if (dayOfWeek == 1)
    {
      throw new MessageException("Maturity Date falls on Sunday. Please Change.");
    }

    IFProcessor ifProcessor = new IFProcessor();
    ArrayList holidays = ifProcessor.getAllHolidays();

    if (holidays.contains(bondsDetail.getMaturityDate()))
    {
      throw new MessageException("Maturity Date falls on a Holiday. Please change.");
    }

    IFDAO ifDAO = new IFDAO();
    double corpusAmt = ifDAO.getCorpusAmount();
    java.util.Date date = new java.util.Date();

    Map matDetails = new TreeMap();
    Map ioReportDetails = ifProcessor.showInflowOutflowReport(date, matDetails, "");
    Map mainDetails = (Map)ioReportDetails.get("DT");
    Set mainSet = mainDetails.keySet();
    Iterator mainIterator = mainSet.iterator();
    InflowOutflowReport ioReport = new InflowOutflowReport();
    while (mainIterator.hasNext())
    {
      ioReport = (InflowOutflowReport)mainDetails.get(mainIterator.next());
      if (ioReport.getBankName().substring(0, 9).equalsIgnoreCase("IDBI Bank"))
      {
        break;
      }
    }

    ExposureDetails exposureDetails = ifProcessor.getPositionDetails(date, date);

    Log.log(5, "IFAction", "updateBondDetails", "matuirty amount " + exposureDetails.getMaturedAmount());
    Log.log(5, "IFAction", "updateBondDetails", "stmt balance " + ioReport.getStmtBalance());
    Log.log(5, "IFAction", "updateBondDetails", "fund transfer amount " + ioReport.getFundTransferInflow());
    Log.log(5, "IFAction", "updateBondDetails", "chq issued but not presented amount " + ioReport.getChqissuedAmt());
    Log.log(5, "IFAction", "updateBondDetails", "provisions " + ioReport.getProvisionFundsAmt());
    Log.log(5, "IFAction", "updateBondDetails", "min balance " + ioReport.getMinBalance());
    Log.log(5, "IFAction", "updateBondDetails", "invested amount " + exposureDetails.getInvestedAmount());

    double surplusAmt = exposureDetails.getMaturedAmount() + Double.parseDouble(ioReport.getStmtBalance()) + Double.parseDouble(ioReport.getFundTransferInflow()) - Double.parseDouble(ioReport.getChqissuedAmt()) - Double.parseDouble(ioReport.getProvisionFundsAmt()) - Double.parseDouble(ioReport.getMinBalance()) - exposureDetails.getInvestedAmount();

    Log.log(5, "IFAction", "updateBondDetails", "surplus amount " + surplusAmt);

    double invCeilingAmt = 0.0D;
    double matCeilingAmt = 0.0D;
    double instCeilingAmt = 0.0D;
    double ceilingAmt = 0.0D;
    boolean ceilingAvail = false;

    String investeeName = bondsDetail.getInvesteeName();
    ArrayList investeeWiseExpDetails = ifProcessor.getExposure(date, date, corpusAmt);

    for (int i = 0; i < investeeWiseExpDetails.size(); i++)
    {
      ExposureDetails exposureDetailsTemp = (ExposureDetails)investeeWiseExpDetails.get(i);
      String invName = exposureDetailsTemp.getInvesteeName();
      Log.log(5, "IFAction", "updateBondDetails", "inv name " + invName);
      if (invName.equalsIgnoreCase(investeeName))
      {
        ceilingAvail = true;
        invCeilingAmt = exposureDetailsTemp.getGapAvailableAmount();
        break;
      }
    }

    if (!ceilingAvail)
    {
      throw new MessageException("Ceiling for Investee " + investeeName + " not available.");
    }

    ArrayList maturityCeilingArr = ifProcessor.getMaturityWiseDetails(date, date, surplusAmt);
    String maturityName = bondsDetail.getMaturityName();
    ceilingAvail = false;

    for (int i = 0; i < maturityCeilingArr.size(); i++)
    {
      ExposureDetails exposureDetailsTemp = (ExposureDetails)maturityCeilingArr.get(i);
      String matName = exposureDetailsTemp.getMaturityName();
      Log.log(5, "IFAction", "updateBondDetails", "mat name " + matName);
      if (matName.equalsIgnoreCase(maturityName))
      {
        ceilingAvail = true;
        matCeilingAmt = exposureDetailsTemp.getGapAvailableAmount();
        break;
      }
    }

    if (!ceilingAvail)
    {
      throw new MessageException("Ceiling for Maturity " + maturityName + " not available.");
    }

    ArrayList instCatCeilingArr = ifProcessor.getInstCategoryWiseDetails(date, date, surplusAmt);
    String instCatName = bondsDetail.getInstrumentCategory();
    ceilingAvail = false;

    for (int i = 0; i < instCatCeilingArr.size(); i++)
    {
      ExposureDetails exposureDetailsTemp = (ExposureDetails)instCatCeilingArr.get(i);
      String instName = exposureDetailsTemp.getInstCatName();
      Log.log(5, "IFAction", "updateBondDetails", "inst cat name " + instName);
      if (instName.equalsIgnoreCase(instCatName))
      {
        ceilingAvail = true;
        instCeilingAmt = exposureDetailsTemp.getGapAvailableAmount();
        break;
      }
    }

    if (!ceilingAvail)
    {
      throw new MessageException("Ceiling for Instrument Category " + instCatName + " not available.");
    }
    String limitExceeded = "";
    if ((instCeilingAmt == -1) && (matCeilingAmt == -1))
    {
      ceilingAmt = invCeilingAmt;
      limitExceeded = " for Investee - " + bondsDetail.getInvesteeName();
    }
    else if (matCeilingAmt < instCeilingAmt)
    {
      if (invCeilingAmt < matCeilingAmt)
      {
        ceilingAmt = invCeilingAmt;
        limitExceeded = " for Investee - " + bondsDetail.getInvesteeName();
      }
      else
      {
        ceilingAmt = matCeilingAmt;
        limitExceeded = " for Maturity - " + bondsDetail.getMaturityName();
      }

    }
    else if (instCeilingAmt < invCeilingAmt)
    {
      ceilingAmt = instCeilingAmt;
      limitExceeded = " for Instrument Category - " + bondsDetail.getInstrumentCategory();
    }
    else
    {
      ceilingAmt = invCeilingAmt;
      limitExceeded = " for Investee - " + bondsDetail.getInvesteeName();
    }

    if ((ceilingAmt == 0) || (bondsDetail.getCostOfPurchase() > ceilingAmt))
    {
      throw new MessageException("Invested Amount has exceeded the Ceiling Limits" + limitExceeded);
    }

    ifProcessor.saveInvestmentDetail(bondsDetail);

    String message = "Bond details saved Successfully";

    request.setAttribute("message", message);

    Log.log(4, "IFAction", "updateBondDetails", "Exited");

    return mapping.findForward("success");
  }

  public ActionForward showInvestementFullfillment(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response)
    throws Exception
  {
    Log.log(4, "IFAction", "showInvestementFullfillment", "Entered");

    HttpSession session = request.getSession(false);
    session.setAttribute("fullfilmentFlag", "0");

    DynaActionForm dynaForm = (DynaActionForm)form;
    dynaForm.initialize(mapping);

    IFProcessor ifProcessor = new IFProcessor();

    getAllInvestees(dynaForm);
    getInstrumentTypes(dynaForm);
    getGenInstrumentTypes(dynaForm);
    getInstrumentFeatures(dynaForm);

    getInstrumentSchemes(dynaForm);
    getInvestmentReferenceNumbers(dynaForm);

    ArrayList bankDetails = ifProcessor.getBankAccounts();

    ArrayList bankNames = new ArrayList();

    for (int i = 0; i < bankDetails.size(); i++)
    {
      BankAccountDetail bankAccountDetail = (BankAccountDetail)bankDetails.get(i);
      String bankName = bankAccountDetail.getBankName();
      String branchName = bankAccountDetail.getBankBranchName();

      String bankBranchName = String.valueOf(bankName) + "," + branchName;
      bankNames.add(bankBranchName);
    }

    dynaForm.set("bankAcctDetails", bankNames);

    Log.log(4, "IFAction", "showInvestementFullfillment", "Exited");

    return mapping.findForward("success");
  }

  public ActionForward updateInvestementFullfillment(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response)
    throws Exception
  {
    Log.log(4, "IFAction", "updateInvestementFullfillment", "Entered");
    Hashtable ratingDetails = new Hashtable();
    DynaActionForm dynaForm = (DynaActionForm)form;
    InvestmentFulfillmentDetail investmentFulfillmentDetail = new InvestmentFulfillmentDetail();

    User loggedInUser = getUserInformation(request);
    String loggedUserId = loggedInUser.getUserId();

    BeanUtils.populate(investmentFulfillmentDetail, dynaForm.getMap());
    investmentFulfillmentDetail.setModifiedBy(loggedUserId);

    String contextPath = request.getSession(false).getServletContext().getRealPath("");

    Log.log(4, "IFAction", "updateInvestementFullfillment", "getInstrumentName = " + investmentFulfillmentDetail.getInstrumentName());
    Log.log(4, "IFAction", "updateInvestementFullfillment", "getInflowOutFlowFlag = " + investmentFulfillmentDetail.getInflowOutFlowFlag());

    Log.log(4, "IFAction", "updateInvestementFullfillment", "getInstrumentType = " + investmentFulfillmentDetail.getInstrumentType());
    Log.log(4, "IFAction", "updateInvestementFullfillment", "getInstrumentNumber = " + investmentFulfillmentDetail.getInstrumentNumber());
    Log.log(4, "IFAction", "updateInvestementFullfillment", "getInstrumentDate = " + investmentFulfillmentDetail.getInstrumentDate());
    Log.log(4, "IFAction", "updateInvestementFullfillment", "getInstrumentAmount = " + investmentFulfillmentDetail.getInstrumentAmount());
    Log.log(4, "IFAction", "updateInvestementFullfillment", "getDrawnBank = " + investmentFulfillmentDetail.getDrawnBank());
    Log.log(4, "IFAction", "updateInvestementFullfillment", "getDrawnBranch = " + investmentFulfillmentDetail.getDrawnBranch());
    Log.log(4, "IFAction", "updateInvestementFullfillment", "getPayableAt = " + investmentFulfillmentDetail.getPayableAt());
    IFProcessor ifProcessor = new IFProcessor();
    HttpSession session = request.getSession(false);
    String updateFlag = "";
    if ((session.getAttribute("updateFlag") != null) && (!session.getAttribute("updateFlag").equals("")));
    updateFlag = (String)session.getAttribute("updateFlag");

    ChequeDetails chequeDetails = new ChequeDetails();

    if (investmentFulfillmentDetail.getInstrumentType().equals("CHEQUE"))
    {
      if ((dynaForm.get("bnkName") == null) || (dynaForm.get("bnkName").equals("")))
      {
        throw new MessageException("Since no bank names are available,Cheque Details cannot be inserted");
      }
      String bankBranchName = (String)dynaForm.get("bnkName");
      int start = bankBranchName.indexOf(",");
      String bankName = bankBranchName.substring(0, start);

      String branchName = bankBranchName.substring(start + 1);

      chequeDetails.setUserId(loggedUserId);
      chequeDetails.setChequeAmount(investmentFulfillmentDetail.getInstrumentAmount());
      chequeDetails.setChequeDate(investmentFulfillmentDetail.getInstrumentDate());
      chequeDetails.setChequeNumber(investmentFulfillmentDetail.getInstrumentNumber());
      chequeDetails.setChequeIssuedTo("CG");
      chequeDetails.setBankName(bankName);
      chequeDetails.setBranchName(branchName);
      chequeDetails.setChequeRemarks(null);
      chequeDetails.setPayId(null);
    }
    else
    {
      chequeDetails = null;
    }

    ifProcessor.saveInvestmentFulfillmentDetail(investmentFulfillmentDetail, updateFlag, chequeDetails, contextPath, loggedUserId);

    String message = "Investment Fulfillment details saved Successfully";

    request.setAttribute("message", message);

    Log.log(4, "IFAction", "updateInvestementFullfillment", "Exited");

    return mapping.findForward("success");
  }

  public ActionForward setBudgetInflowDetails(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response)
    throws Exception
  {
    Log.log(4, "IFAction", "setBudgetInflowDetails", "Entered");
    String budget = "";
    HashMap heads = new HashMap();
    HashMap subHeads = new HashMap();

    InvestmentForm ifForm = (InvestmentForm)form;
    heads = (HashMap)ifForm.getHeads();

    Log.log(5, "IFAction", "setBudgetInflowDetails", "Budget Type " + ifForm.getAnnualOrShortTerm());

    HashMap subHeads1 = new HashMap();

    subHeads1 = (HashMap)ifForm.getSubHeads();
    Set subHeadsSet1 = subHeads1.keySet();
    Iterator subHeadsIterator1 = subHeadsSet1.iterator();

    while (subHeadsIterator1.hasNext())
    {
      String key = (String)subHeadsIterator1.next();
      String value = (String)subHeads1.get(key);
      Log.log(5, "IFAction", "setBudgetInflowDetails", "key, value " + key + ", " + value);
      int index = key.indexOf("_");
      Log.log(5, "IFAction", "setBudgetInflowDetails", "index " + index);
      String key1 = key.substring(0, index);
      Log.log(5, "IFAction", "setBudgetInflowDetails", "key, value " + key1);
      heads.put(key1, "");
    }
    Set headsSet = heads.keySet();
    Iterator headsIterator = headsSet.iterator();
    subHeadsIterator1 = subHeadsSet1.iterator();

    Log.log(5, "IFAction", "setBudgetInflowDetails", "sub-heads = " + subHeads1);
    Log.log(5, "IFAction", "setBudgetInflowDetails", "sub-headsSet = " + subHeadsSet1);
    Log.log(5, "IFAction", "setBudgetInflowDetails", "sub-headsIterator = " + subHeadsIterator1);
    Log.log(5, "IFAction", "setBudgetInflowDetails", "heads = " + heads);
    Log.log(5, "IFAction", "setBudgetInflowDetails", "headsSet = " + headsSet);
    Log.log(5, "IFAction", "setBudgetInflowDetails", "headsIterator = " + headsIterator);

    HashMap headsToRender = new HashMap();
    HashMap subHeadsToRender = new HashMap();

    headsToRender = (HashMap)ifForm.getHeadsToRender();

    String headTitle = "";
    BudgetDetails budgetDetails = new BudgetDetails();
    ArrayList aBudgetHeadDetails = new ArrayList();

    String inflowOrOutflow = null;
    String term = null;
    String annualToDate = null;
    String annualFromDate = null;
    String month = null;
    String year = null;
    String modifiedByUser = null;

    inflowOrOutflow = ifForm.getInflowOrOutflow();
    term = ifForm.getAnnualOrShortTerm();
    annualToDate = ifForm.getAnnualToDate();
    annualFromDate = ifForm.getAnnualFromDate();
    month = ifForm.getMonth();
    year = ifForm.getYear();

    User loggedInUser = getUserInformation(request);
    String loggedUserId = loggedInUser.getUserId();

    String headsAndVals = ifForm.getSubHeadsRam();

    Log.log(5, "IFAction", "setBudgetInflowDetails", "inflowOrOutflow = " + inflowOrOutflow);
    Log.log(5, "IFAction", "setBudgetInflowDetails", "loggerd man");
    Log.log(5, "IFAction", "setBudgetInflowDetails", "term = " + term);
    Log.log(5, "IFAction", "setBudgetInflowDetails", "annualToDate = " + annualToDate);
    Log.log(5, "IFAction", "setBudgetInflowDetails", "annualFromDate = " + annualFromDate);
    Log.log(5, "IFAction", "setBudgetInflowDetails", "month = " + month);
    Log.log(5, "IFAction", "setBudgetInflowDetails", "year = " + year);
    Log.log(5, "IFAction", "setBudgetInflowDetails", "Heads and Val = " + headsAndVals);
    budgetDetails.setAnnualOrShortTerm(term);
    budgetDetails.setAnnualToDate(annualToDate);
    budgetDetails.setAnnualFromDate(annualFromDate);
    budgetDetails.setMonth(month);
    budgetDetails.setYear(year);
    budgetDetails.setModifiedBy(loggedUserId);
    budgetDetails.setInflowOrOutflow(inflowOrOutflow);

    while (headsIterator.hasNext())
    {
      BudgetHeadDetails budgetHeadDetails = new BudgetHeadDetails();
      headTitle = (String)headsIterator.next();

      Log.log(4, "IFAction", "setBudgetInflowDetails", "headTitle " + headTitle);

      if (heads.get(headTitle) != null)
      {
        budgetHeadDetails.setBudgetHead(headTitle);
        ArrayList aBudgetSubHeadDetails = null;
        subHeadsToRender = (HashMap)headsToRender.get(headTitle);
        String budgetAmount = (String)heads.get(headTitle);

        if ((budgetAmount == null) || (budgetAmount.equals("")))
        {
          budgetAmount = "0.0";
        }

        budgetHeadDetails.setBudgetAmount(Double.parseDouble(budgetAmount));
        Log.log(5, "IFAction", "setBudgetInflowDetails", "subHeadsToRender " + subHeadsToRender);

        if (subHeadsToRender != null)
        {
          aBudgetSubHeadDetails = new ArrayList();
          Set subHeadsSetToRender = subHeadsToRender.keySet();
          Iterator subHeadsIteratorToRender = subHeadsSetToRender.iterator();
          String subHeadTitle = "";
          while (subHeadsIteratorToRender.hasNext())
          {
            BudgetSubHeadDetails budgetSubHeadDetails = new BudgetSubHeadDetails();
            subHeadTitle = subHeadsIteratorToRender.next().toString();
            budgetSubHeadDetails.setSubHeadTitle(subHeadTitle);

            Log.log(5, "IFAction", "setBudgetInflowDetails", "subHeadTitle " + subHeadTitle);

            budgetAmount = (String)subHeads1.get(String.valueOf(headTitle) + "_" + subHeadTitle);

            if ((budgetAmount == null) || (budgetAmount.equals("")))
            {
              budgetAmount = "0.0";
            }

            budgetSubHeadDetails.setBudgetAmount(Double.parseDouble(budgetAmount));
            aBudgetSubHeadDetails.add(budgetSubHeadDetails);
            budgetSubHeadDetails = null;
          }
        }
        budgetHeadDetails.setBudgetSubHeadDetails(aBudgetSubHeadDetails);
      }
      else
      {
        Log.log(4, "IFAction", "setBudgetInflowDetails", "No head details ");
      }

      aBudgetHeadDetails.add(budgetHeadDetails);
    }
    budgetDetails.setBudgetHeadDetails(aBudgetHeadDetails);
    ArrayList budgetHeadDetailsram = budgetDetails.getBudgetHeadDetails();
    IFProcessor ifProcessor = new IFProcessor();
    ifProcessor.saveBudgetDetails(budgetDetails);

    String message = "Budget Inflow details saved Successfully";

    if (inflowOrOutflow.equals("O"))
    {
      message = "Budget Outflow details saved Successfully";
    }

    request.setAttribute("message", message);

    Log.log(4, "IFAction", "setBudgetInflowDetails", "Exited");

    return mapping.findForward("success");
  }

  public ActionForward setInflowForecastDetails(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response)
    throws Exception
  {
    Log.log(4, "IFAction", "setInflowForecastDetails", "Entered");
    String budget = "";
    HashMap heads = new HashMap();
    HashMap subHeads = new HashMap();

    InvestmentForm ifForm = (InvestmentForm)form;
    heads = (HashMap)ifForm.getHeads();
    Set headsSet = heads.keySet();
    Iterator headsIterator = headsSet.iterator();
    HashMap subHeads1 = new HashMap();

    subHeads1 = (HashMap)ifForm.getSubHeads();
    Set subHeadsSet1 = subHeads1.keySet();
    Iterator subHeadsIterator1 = subHeadsSet1.iterator();

    HashMap headsToRender = new HashMap();
    HashMap subHeadsToRender = new HashMap();

    headsToRender = (HashMap)ifForm.getHeadsToRender();

    String headTitle = "";
    ForecastDetails forecastDetails = new ForecastDetails();
    ArrayList aForecastHeadDetails = new ArrayList();

    String inflowOrOutflow = null;
    String term = null;
    String startDate = null;
    String endDate = null;
    String modifiedByUser = null;

    inflowOrOutflow = ifForm.getInflowOrOutflow();
    endDate = ifForm.getEndDate();
    startDate = ifForm.getStartDate();

    User loggedInUser = getUserInformation(request);
    String loggedUserId = loggedInUser.getUserId();

    String headsAndVals = ifForm.getSubHeadsRam();

    Log.log(5, "IFAction", "setInflowForecastDetails", "inflowOrOutflow = " + inflowOrOutflow);
    Log.log(5, "IFAction", "setInflowForecastDetails", "loggerd man");
    Log.log(5, "IFAction", "setInflowForecastDetails", "startDate = " + startDate);
    Log.log(5, "IFAction", "setInflowForecastDetails", "endDate = " + endDate);
    Log.log(5, "IFAction", "setInflowForecastDetails", "Heads and Val = " + headsAndVals);

    forecastDetails.setStartDate(startDate);
    forecastDetails.setEndDate(endDate);
    forecastDetails.setModifiedBy(modifiedByUser);
    forecastDetails.setModifiedBy(loggedUserId);

    while (headsIterator.hasNext())
    {
      ForecastHeadDetails forecastHeadDetails = new ForecastHeadDetails();
      headTitle = (String)headsIterator.next();
      if (heads.get(headTitle) != null)
      {
        forecastHeadDetails.setForecastHead(headTitle);
        ArrayList aForecastSubHeadDetails = null;
        subHeadsToRender = (HashMap)headsToRender.get(headTitle);
        if (subHeadsToRender != null)
        {
          aForecastSubHeadDetails = new ArrayList();
          Set subHeadsSetToRender = subHeadsToRender.keySet();
          Iterator subHeadsIteratorToRender = subHeadsSetToRender.iterator();
          String subHeadTitle = "";
          while (subHeadsIteratorToRender.hasNext())
          {
            ForecastSubHeadDetails forecastSubHeadDetails = new ForecastSubHeadDetails();
            subHeadTitle = subHeadsIteratorToRender.next().toString();
            forecastSubHeadDetails.setSubHeadTitle(subHeadTitle);
            String forecastAmount = (String)subHeads1.get(String.valueOf(headTitle) + "_" + subHeadTitle);
            if (forecastAmount == null) forecastAmount = "0.0";
            forecastSubHeadDetails.setForecastAmount(Double.parseDouble(forecastAmount));
            aForecastSubHeadDetails.add(forecastSubHeadDetails);
            forecastSubHeadDetails = null;
          }
        }
        forecastHeadDetails.setForecastSubHeadDetails(aForecastSubHeadDetails);
      }
      else {
        Log.log(4, "IFAction", "setInflowForecastDetails", "No head details ");
      }
      aForecastHeadDetails.add(forecastHeadDetails);
    }
    forecastDetails.setForecastHeadDetails(aForecastHeadDetails);
    IFProcessor ifProcessor = new IFProcessor();
    ifProcessor.saveForecastingDetail(forecastDetails);
    Log.log(4, "IFAction", "setInflowForecastDetails", "Exited");

    return mapping.findForward("success");
  }

  public ActionForward setAnnualFundsInflowDetails(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response)
    throws Exception
  {
    Log.log(4, "IFAction", "setAnnualFundsInflowDetails", "Entered");

    InvestmentForm investmentForm = (InvestmentForm)form;

    saveFundsInflowOutflowDetails(investmentForm, request);

    Log.log(4, "IFAction", "setAnnualFundsInflowDetails", "Exited");

    String message = "Inflow details saved Successfully";

    request.setAttribute("message", message);

    return mapping.findForward("success");
  }

  private void saveFundsInflowOutflowDetails(InvestmentForm ifForm, HttpServletRequest request) throws DatabaseException
  {
    Log.log(4, "IFAction", "saveFundsInflowOutflowDetails", "Entered");
    String budget = "";
    HashMap heads = new HashMap();
    HashMap subHeads = new HashMap();

    heads = (HashMap)ifForm.getHeads();
    HashMap subHeads1 = new HashMap();

    subHeads1 = (HashMap)ifForm.getSubHeads();
    Set subHeadsSet1 = subHeads1.keySet();
    Iterator subHeadsIterator1 = subHeadsSet1.iterator();

    while (subHeadsIterator1.hasNext())
    {
      String key = (String)subHeadsIterator1.next();
      String value = (String)subHeads1.get(key);
      Log.log(5, "IFAction", "saveFundsInflowOutflowDetails", "key, value " + key + ", " + value);
      int index = key.indexOf("_");

      if (index != -1)
      {
        Log.log(5, "IFAction", "saveFundsInflowOutflowDetails", "index " + index);
        String key1 = key.substring(0, index);
        Log.log(5, "IFAction", "saveFundsInflowOutflowDetails", "key, value " + key1);
        heads.put(key1, "");
      }
    }
    subHeadsIterator1 = subHeadsSet1.iterator();

    Set headsSet = heads.keySet();
    Iterator headsIterator = headsSet.iterator();

    HashMap headsToRender = new HashMap();
    HashMap subHeadsToRender = new HashMap();

    headsToRender = (HashMap)ifForm.getHeadsToRender();

    String headTitle = "";
    ActualInflowOutflowDetails actualInflowOutflowDetails = new ActualInflowOutflowDetails();
    ArrayList aActualIOHeadDetails = new ArrayList();

    String inflowOrOutflow = null;
    String term = null;
    String dateOfFlow = null;
    String month = null;
    String year = null;
    String modifiedByUser = null;

    inflowOrOutflow = ifForm.getInflowOrOutflow();
    term = ifForm.getAnnualOrShortTerm();
    dateOfFlow = ifForm.getDateOfFlow();
    month = ifForm.getMonth();
    year = ifForm.getYear();

    User loggedInUser = getUserInformation(request);
    String loggedUserId = loggedInUser.getUserId();

    Log.log(4, "IFAction", "saveFundsInflowOutflowDetails", "Entered");

    Log.log(5, "IFAction", "saveFundsInflowOutflowDetails", "inflowOrOutflow = " + inflowOrOutflow);
    Log.log(5, "IFAction", "saveFundsInflowOutflowDetails", "term = " + term);
    Log.log(5, "IFAction", "saveFundsInflowOutflowDetails", "dateOfFlow= " + dateOfFlow);

    actualInflowOutflowDetails.setIsInflowOrOutflow(inflowOrOutflow);
    actualInflowOutflowDetails.setIsAnnualOrShortTerm(term);
    actualInflowOutflowDetails.setDateOfFlow(dateOfFlow);

    actualInflowOutflowDetails.setModifiedBy(loggedUserId);

    String budgetAmount = null;

    while (headsIterator.hasNext())
    {
      ActualIOHeadDetail actualIOHeadDetails = new ActualIOHeadDetail();
      headTitle = (String)headsIterator.next();

      Log.log(5, "IFAction", "saveFundsInflowOutflowDetails", "headTitle= " + headTitle);

      if (heads.get(headTitle) != null)
      {
        actualIOHeadDetails.setBudgetHead(headTitle);
        ArrayList aActualIOSubHeadDetails = null;
        subHeadsToRender = (HashMap)headsToRender.get(headTitle);

        budgetAmount = (String)heads.get(headTitle);

        if ((budgetAmount == null) || (budgetAmount.equals("")))
        {
          budgetAmount = "0.0";
        }

        Log.log(5, "IFAction", "saveFundsInflowOutflowDetails", "budget amount = " + budgetAmount);

        actualIOHeadDetails.setBudgetAmount(Double.parseDouble(budgetAmount));

        if (subHeadsToRender != null)
        {
          Log.log(5, "IFAction", "saveFundsInflowOutflowDetails", "sub-heads available ");

          aActualIOSubHeadDetails = new ArrayList();
          Set subHeadsSetToRender = subHeadsToRender.keySet();
          Iterator subHeadsIteratorToRender = subHeadsSetToRender.iterator();
          String subHeadTitle = "";

          while (subHeadsIteratorToRender.hasNext())
          {
            ActualIOSubHeadDetail actualIOSubHeadDetails = new ActualIOSubHeadDetail();
            subHeadTitle = subHeadsIteratorToRender.next().toString();
            actualIOSubHeadDetails.setSubHeadTitle(subHeadTitle);
            budgetAmount = (String)subHeads1.get(String.valueOf(headTitle) + "_" + subHeadTitle);

            if ((budgetAmount == null) || (budgetAmount.equals("")))
            {
              budgetAmount = "0.0";
            }
            actualIOSubHeadDetails.setBudgetAmount(Double.parseDouble(budgetAmount));
            aActualIOSubHeadDetails.add(actualIOSubHeadDetails);
            actualIOSubHeadDetails = null;

            Log.log(5, "IFAction", "saveFundsInflowOutflowDetails", "subHeadTitle,budgetAmount " + subHeadTitle + ", " + budgetAmount);
          }
        }
        actualIOHeadDetails.setActualIOSubHeadDetails(aActualIOSubHeadDetails);
      }
      else
      {
        Log.log(4, "IFAction", "saveFundsInflowOutflowDetails", "No head details ");
      }
      aActualIOHeadDetails.add(actualIOHeadDetails);
    }
    actualInflowOutflowDetails.setActualIOHeadDetails(aActualIOHeadDetails);
    IFProcessor ifProcessor = new IFProcessor();
    ifProcessor.saveActualInflowOutflowDetails(actualInflowOutflowDetails);

    String message = "Outflow details saved Successfully";

    if (inflowOrOutflow.equals("I"))
    {
      message = "Inflow details saved Successfully";
    }

    request.setAttribute("message", message);

    Log.log(4, "IFAction", "saveFundsInflowOutflowDetails", "Exited");
  }

  public ActionForward setAnnualFundsOutflowDetails(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response)
    throws Exception
  {
    Log.log(4, "IFAction", "setAnnualFundsOutflowDetails", "Entered");
    InvestmentForm ifForm = (InvestmentForm)form;

    saveFundsInflowOutflowDetails(ifForm, request);

    String message = "Outflow details saved Successfully";

    request.setAttribute("message", message);

    Log.log(4, "IFAction", "setAnnualFundsOutflowDetails", "Exited");

    return mapping.findForward("success");
  }

  private void getInvestmentForm(InvestmentForm form, String ioFlag) throws MessageException, DatabaseException
  {
    IFProcessor ifProcessor = new IFProcessor();
    Vector budgetHeads = new Vector();
    Vector budgetSubHeads = new Vector();
    String headTitle = "";
    int noOfHeads = 0;
    int noOfSubHeads = 0;
    int i = 0;
    int j = 0;

    budgetHeads = ifProcessor.getBudgetHeadTitles(ioFlag);
    noOfHeads = budgetHeads.size();

    Log.log(5, "IFAction", "getInvestmentForm", "No of Heads " + noOfHeads);

    if (noOfHeads == 0)
    {
      throw new MessageException("Budget Heads are not available.");
    }

    for (i = 0; i < noOfHeads; i++)
    {
      headTitle = budgetHeads.get(i).toString();
      Log.log(5, "IFAction", "getInvestmentForm", "Head: " + headTitle);
      budgetSubHeads = ifProcessor.getBudgetSubHeadTitles(headTitle, ioFlag);
      noOfSubHeads = budgetSubHeads.size();
      if (noOfSubHeads == 0)
      {
        form.setHeadsToRender(headTitle, null);
        Log.log(4, "IFAction", "getInvestmentForm", "No Sub Heads");
      }
      else
      {
        Map subHeadMap = new HashMap();
        Log.log(4, "IFAction", "getInvestmentForm", "Has Sub Heads");
        for (j = 0; j < noOfSubHeads; j++)
        {
          String s = budgetSubHeads.get(j).toString();
          form.setSubHeadsToRender(s, null);
          subHeadMap.put(s, null);
        }

        Log.log(5, "IFAction", "getInvestmentForm", "Sub Head Size " + subHeadMap.size());

        form.setHeadsToRender(headTitle, subHeadMap);
      }
    }
  }

  private void getAllInvestees(DynaActionForm form)
    throws DatabaseException
  {
    ArrayList investeeNames1 = new ArrayList();
    IFProcessor ifProcessor = new IFProcessor();
    investeeNames1 = ifProcessor.getAllInvesteeNames();
    form.set("investeeNames", investeeNames1);
  }

  private void getAllInvesteeNamesForGroup(DynaActionForm form) throws DatabaseException
  {
    ArrayList investeeNames1 = new ArrayList();
    IFProcessor ifProcessor = new IFProcessor();
    investeeNames1 = ifProcessor.getAllInvesteeNamesForGroup((String)form.get("investeeGroup"));
    form.set("investeeNames", investeeNames1);
  }

  private void getAllInvesteeGroups(DynaActionForm form)
    throws DatabaseException
  {
    ArrayList investeeGroups = null;
    IFProcessor ifProcessor = new IFProcessor();
    investeeGroups = ifProcessor.getInvesteeGroups();
    form.set("investeeGroups", investeeGroups);
  }

  private void getInstrumentTypes(DynaActionForm form) throws DatabaseException
  {
    ArrayList instrtypes = new ArrayList();
    IFProcessor ifProcessor = new IFProcessor();

    instrtypes = ifProcessor.getInstrumentTypes("I");

    form.set("instrumentNames", instrtypes);
  }

  private void getGenInstrumentTypes(DynaActionForm form)
    throws DatabaseException
  {
    ArrayList instrtypes = new ArrayList();
    IFProcessor ifProcessor = new IFProcessor();
    instrtypes = ifProcessor.getInstrumentTypes("G");

    form.set("instrumentTypes", instrtypes);
  }

  private void getAllRatings(DynaActionForm form)
    throws DatabaseException
  {
    ArrayList ratings = new ArrayList();
    IFProcessor ifProcessor = new IFProcessor();
    ratings = ifProcessor.getAllRatings();
    form.set("instrumentRatings", ratings);
  }

  private void getBudgetHeadTypes(DynaActionForm form) throws DatabaseException
  {
    ArrayList budgetHeads = new ArrayList();
    IFProcessor ifProcessor = new IFProcessor();
    String headType = (String)form.get("budgetHeadType");

    budgetHeads = ifProcessor.getBudgetHeadTypes(headType);
    form.set("budgetHeadsList", budgetHeads);
  }

  private void getInstrumentSchemeTypes(DynaActionForm form) throws DatabaseException
  {
    ArrayList instrumentSchemeTypes = new ArrayList();
    IFProcessor ifProcessor = new IFProcessor();
    String inst = (String)form.get("instrument");
    instrumentSchemeTypes = ifProcessor.getInstrumentSchemeTypes(inst);
    form.set("instrumentSchemeTypes", instrumentSchemeTypes);
  }

  private void getInstrumentFeatures(DynaActionForm form) throws DatabaseException
  {
    ArrayList instrumentFeatures = new ArrayList();
    IFProcessor ifProcessor = new IFProcessor();
    instrumentFeatures = ifProcessor.getInstrumentFeatures();
    form.set("instrumentFeaturesList", instrumentFeatures);
  }

  private void getReceiptNumbers(DynaActionForm form) throws DatabaseException
  {
    ArrayList receiptNos = new ArrayList();
    IFProcessor ifProcessor = new IFProcessor();
    receiptNos = ifProcessor.getReceiptNumbers();
    form.set("receiptNumbers", receiptNos);
  }

  private void getAvailableBalance(DynaActionForm form, String acctNo) throws DatabaseException
  {
    IFProcessor ifProcessor = new IFProcessor();

    form.set("dClosingBalance", "12212.0");
  }

  private void getMonthlyExpense(DynaActionForm form, String acctNo, String frmDate, String toDate)
    throws DatabaseException
  {
    IFProcessor ifProcessor = new IFProcessor();
    form.set("dExpensesForTheMonth", ifProcessor.getMonthlyExpense(acctNo, frmDate, toDate));
  }

  private void getTodayExpense(DynaActionForm form, String acctNo) throws DatabaseException
  {
    IFProcessor ifProcessor = new IFProcessor();
    form.set("dCreditPendingForDay", ifProcessor.getTodayExpense(acctNo));
  }

  private void getInstrumentSchemes(DynaActionForm form) throws DatabaseException {
    IFProcessor ifProcessor = new IFProcessor();
    ArrayList instrumentSchemes = ifProcessor.getInstrumentSchemes();

    form.set("instrumentSchemes", instrumentSchemes);
  }

  private void getInvestmentReferenceNumbers(DynaActionForm dynaForm) throws DatabaseException {
    IFProcessor ifProcessor = new IFProcessor();
    String inst = (String)dynaForm.get("instrumentName");
    ArrayList investmentRefNumbers = ifProcessor.getInvestmentRefNumbers(inst);

    dynaForm.set("investmentRefNumbers", investmentRefNumbers);
  }

  public ActionForward saveInvesteeGroup(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response)
    throws Exception
  {
    Log.log(4, "IFAction", "saveInvesteeGroup", "Entered");
    DynaActionForm dynaForm = (DynaActionForm)form;
    User user = getUserInformation(request);
    String investeeGroup = (String)dynaForm.get("investeeGroup");
    String newInvesteeGroup = (String)dynaForm.get("newInvesteeGrp");
    String modInvesteeGroup = (String)dynaForm.get("modInvesteeGroup");
    InvesteeGrpDetail invGrp = new InvesteeGrpDetail();
    invGrp.setSIGRName(investeeGroup);
    invGrp.setSNewIGRName(newInvesteeGroup);
    invGrp.setSModIGRName(modInvesteeGroup);
    Log.log(4, "IFAction", "saveInvesteeGroup", "investee grp " + invGrp.getSIGRName());
    Log.log(4, "IFAction", "saveInvesteeGroup", "new investee grp " + invGrp.getSNewIGRName());
    Log.log(4, "IFAction", "saveInvesteeGroup", "mod investee grp " + invGrp.getSModIGRName());
    IFProcessor ifProcessor = new IFProcessor();

    ifProcessor.saveInvesteeGroup(invGrp, user.getUserId());

    String message = "Investee Group details saved Successfully";

    request.setAttribute("message", message);

    Log.log(4, "IFAction", "saveInvesteeGroup", "Investee Group name " + investeeGroup);
    Log.log(4, "IFAction", "saveInvesteeGroup", "Exited");

    return mapping.findForward("success");
  }

  public ActionForward getAnnualBudgetInflowDetails(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response)
    throws Exception
  {
    Log.log(4, "IFAction", "getAnnualInflowDetails", "Entered");
    InvestmentForm ifForm = (InvestmentForm)form;

    getBudgetInflowOutflowDetails(ifForm, "I");

    Log.log(4, "IFAction", "getAnnualInflowDetails", "Exited");

    return mapping.findForward("success");
  }

  public ActionForward getAnnualBudgetOutflowDetails(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response)
    throws Exception
  {
    Log.log(4, "IFAction", "getAnnualOutflowDetails", "Entered");
    InvestmentForm ifForm = (InvestmentForm)form;

    getBudgetInflowOutflowDetails(ifForm, "O");

    Log.log(4, "IFAction", "getAnnualOutflowDetails", "Exited");

    return mapping.findForward("success");
  }

  private void getBudgetInflowOutflowDetails(InvestmentForm ifForm, String flag) throws DatabaseException
  {
    Log.log(4, "IFAction", "getBudgetInflowOutflowDetails", "Entered ");

    String fromDate = ifForm.getAnnualFromDate();
    String toDate = ifForm.getAnnualToDate();

    ifForm.getHeads().clear();
    ifForm.getSubHeads().clear();

    Log.log(5, "IFAction", "getBudgetInflowOutflowDetails", "fromDate " + fromDate);
    Log.log(5, "IFAction", "getAnnuagetBudgetInflowOutflowDetailslInflowDetails", "toDate " + toDate);

    SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");

    java.util.Date annualFromDate = dateFormat.parse(fromDate, new ParsePosition(0));
    java.util.Date annualToDate = dateFormat.parse(toDate, new ParsePosition(0));

    Log.log(5, "IFAction", "getBudgetInflowOutflowDetails", "annualFromDate " + annualFromDate);
    Log.log(5, "IFAction", "getBudgetInflowOutflowDetails", "annualToDate " + annualToDate);

    IFProcessor ifProcessor = new IFProcessor();

    HashMap headDetails = ifProcessor.getAnnualHeadDetails(annualFromDate, annualToDate, flag);

    Set headSet = headDetails.keySet();

    Iterator headIterator = headSet.iterator();

    Vector budgetHeads = ifProcessor.getBudgetHeadTitles(flag);

    for (int i = 0; i < budgetHeads.size(); i++)
    {
      String head = (String)budgetHeads.get(i);

      HashMap subHeadDetails = ifProcessor.getAnnualSubHeadDetails(annualFromDate, annualToDate, head, flag);

      if ((subHeadDetails != null) && (subHeadDetails.size() != 0))
      {
        Log.log(5, "IFAction", "getBudgetInflowOutflowDetails", "Sub-Heads are available ");

        Set subHeadSet = subHeadDetails.keySet();
        Iterator subHeadIterator = subHeadSet.iterator();

        while (subHeadIterator.hasNext())
        {
          String subHeadKey = (String)subHeadIterator.next();

          Log.log(5, "IFAction", "getBudgetInflowOutflowDetails", "Sub-Heads Key " + subHeadKey);

          ifForm.setSubHead(subHeadKey, subHeadDetails.get(subHeadKey));
        }
      }
    }

    while (headIterator.hasNext())
    {
      String key = (String)headIterator.next();
      ifForm.setHead(key, headDetails.get(key));
    }

    Log.log(4, "IFAction", "getBudgetInflowOutflowDetails", "Exited ");
  }

  private void getShortInflowOutflowDetails(InvestmentForm ifForm, String flag) throws DatabaseException
  {
    Log.log(4, "IFAction", "getShortInflowOutflowDetails", "Entered ");

    String year = ifForm.getYear();
    String month = ifForm.getMonth();

    Log.log(5, "IFAction", "getShortInflowOutflowDetails", "year " + year);
    Log.log(5, "IFAction", "getShortInflowOutflowDetails", "month " + month);

    IFProcessor ifProcessor = new IFProcessor();

    HashMap headDetails = ifProcessor.getShortHeadDetails(year, month, flag);

    Set headSet = headDetails.keySet();

    Iterator headIterator = headSet.iterator();

    Vector budgetHeads = ifProcessor.getBudgetHeadTitles(flag);

    for (int i = 0; i < budgetHeads.size(); i++)
    {
      String head = (String)budgetHeads.get(i);

      HashMap subHeadDetails = ifProcessor.getShortSubHeadDetails(year, month, head, flag);

      if ((subHeadDetails != null) && (subHeadDetails.size() != 0))
      {
        Log.log(5, "IFAction", "getShortInflowOutflowDetails", "Sub-Heads are available ");

        Set subHeadSet = subHeadDetails.keySet();
        Iterator subHeadIterator = subHeadSet.iterator();

        while (subHeadIterator.hasNext())
        {
          String subHeadKey = (String)subHeadIterator.next();

          Log.log(5, "IFAction", "getShortInflowOutflowDetails", "Sub-Heads Key,amount " + subHeadKey + ", " + subHeadDetails.get(subHeadKey));

          ifForm.setShortSubHead(subHeadKey, subHeadDetails.get(subHeadKey));
        }
      }
    }

    while (headIterator.hasNext())
    {
      String key = (String)headIterator.next();
      ifForm.setShortHead(key, headDetails.get(key));
    }

    Log.log(4, "IFAction", "getShortInflowOutflowDetails", "Exited ");
  }

  public ActionForward getShortTermInflowDetails(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response)
    throws Exception
  {
    Log.log(4, "IFAction", "getShortTermInflowDetails", "Entered ");

    InvestmentForm investmentForm = (InvestmentForm)form;

    String year = investmentForm.getYear();
    String month = investmentForm.getMonth();

    Map headsToRender = investmentForm.getHeadsToRender();

    Map tempMap = new HashMap(headsToRender);

    investmentForm.resetWhenRequired(mapping, request);

    Set headsSet = tempMap.keySet();
    Iterator headsIterator = headsSet.iterator();

    while (headsIterator.hasNext())
    {
      String key = (String)headsIterator.next();

      investmentForm.setHeadsToRender(key, tempMap.get(key));
    }

    int intYear = Integer.parseInt(year);
    intYear++;

    String annualFromDate = "01/01/" + year;
    String annualToDate = "01/01/" + intYear;

    investmentForm.setAnnualFromDate(annualFromDate);
    investmentForm.setAnnualToDate(annualToDate);
    investmentForm.setYear(year);
    investmentForm.setMonth(month);

    getBudgetInflowOutflowDetails(investmentForm, "I");

    investmentForm.getShortHeads().clear();
    investmentForm.getShortSubHeads().clear();

    getShortInflowOutflowDetails(investmentForm, "I");

    Log.log(4, "IFAction", "getShortTermInflowDetails", "Exited ");

    return mapping.findForward("success");
  }

  public ActionForward getShortTermOutflowDetails(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response)
    throws Exception
  {
    Log.log(4, "IFAction", "getShortTermOutflowDetails", "Entered ");

    InvestmentForm investmentForm = (InvestmentForm)form;

    String year = investmentForm.getYear();
    String month = investmentForm.getMonth();

    Map headsToRender = investmentForm.getHeadsToRender();

    Map tempMap = new HashMap(headsToRender);

    investmentForm.resetWhenRequired(mapping, request);

    Set headsSet = tempMap.keySet();
    Iterator headsIterator = headsSet.iterator();

    while (headsIterator.hasNext())
    {
      String key = (String)headsIterator.next();

      investmentForm.setHeadsToRender(key, tempMap.get(key));
    }

    int intYear = Integer.parseInt(year);
    intYear++;

    String annualFromDate = "01/01/" + year;
    String annualToDate = "01/01/" + intYear;

    investmentForm.setAnnualFromDate(annualFromDate);
    investmentForm.setAnnualToDate(annualToDate);
    investmentForm.setYear(year);
    investmentForm.setMonth(month);

    getBudgetInflowOutflowDetails(investmentForm, "O");

    investmentForm.getShortHeads().clear();
    investmentForm.getShortSubHeads().clear();

    getShortInflowOutflowDetails(investmentForm, "O");

    Log.log(4, "IFAction", "getShortTermOutflowDetails", "Exited ");

    return mapping.findForward("success");
  }

  public ActionForward setShortTermBudgetInflowDetails(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response)
    throws Exception
  {
    Log.log(4, "IFAction", "setShortTermBudgetInflowDetails", "Entered ");

    InvestmentForm ifForm = (InvestmentForm)form;

    saveShortTermBudgetDetails(ifForm, "I", request);

    String message = "Short Term Budget Inflow details saved Successfully";

    request.setAttribute("message", message);

    Log.log(4, "IFAction", "setShortTermBudgetInflowDetails", "Exited ");

    return mapping.findForward("success");
  }

  public ActionForward setShortTermBudgetOutflowDetails(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response)
    throws Exception
  {
    Log.log(4, "IFAction", "setShortTermBudgetOutflowDetails", "Entered ");

    InvestmentForm ifForm = (InvestmentForm)form;

    saveShortTermBudgetDetails(ifForm, "O", request);

    String message = "Short Term Budget Outflow details saved Successfully";

    request.setAttribute("message", message);

    Log.log(4, "IFAction", "setShortTermBudgetOutflowDetails", "Exited ");

    return mapping.findForward("success");
  }

  private void saveShortTermBudgetDetails(InvestmentForm ifForm, String flag, HttpServletRequest request)
    throws DatabaseException
  {
    String budget = "";
    HashMap heads = null;
    HashMap subHeads = null;

    heads = (HashMap)ifForm.getShortHeads();

    Log.log(5, "IFAction", "saveShortTermBudgetDetails", "heads " + heads);

    Log.log(5, "IFAction", "saveShortTermBudgetDetails", "Budget Type " + ifForm.getAnnualOrShortTerm());

    HashMap subHeads1 = null;

    subHeads1 = (HashMap)ifForm.getShortSubHeads();

    Log.log(5, "IFAction", "saveShortTermBudgetDetails", "subHeads1 " + subHeads1);

    Log.log(5, "IFAction", "saveShortTermBudgetDetails", "short sub heads " + ifForm.getShortSubHeads());

    Set subHeadsSet1 = subHeads1.keySet();
    Iterator subHeadsIterator1 = subHeadsSet1.iterator();

    while (subHeadsIterator1.hasNext())
    {
      String key = (String)subHeadsIterator1.next();
      String value = (String)subHeads1.get(key);
      Log.log(5, "IFAction", "saveShortTermBudgetDetails", "key, value " + key + ", " + value);
      int index = key.indexOf("_");

      if (index != -1)
      {
        Log.log(5, "IFAction", "saveShortTermBudgetDetails", "index " + index);
        String key1 = key.substring(0, index);
        Log.log(5, "IFAction", "saveShortTermBudgetDetails", "key, value " + key1);
        heads.put(key1, "");
      }
    }
    Set headsSet = heads.keySet();
    Iterator headsIterator = headsSet.iterator();

    subHeads1 = (HashMap)ifForm.getShortSubHeads();
    subHeadsSet1 = subHeads1.keySet();
    subHeadsIterator1 = subHeadsSet1.iterator();

    subHeadsIterator1 = subHeadsSet1.iterator();

    Log.log(5, "IFAction", "saveShortTermBudgetDetails", "sub-heads = " + subHeads1);
    Log.log(5, "IFAction", "saveShortTermBudgetDetails", "sub-headsSet = " + subHeadsSet1);
    Log.log(5, "IFAction", "saveShortTermBudgetDetails", "sub-headsIterator = " + subHeadsIterator1);
    Log.log(5, "IFAction", "saveShortTermBudgetDetails", "heads = " + heads);
    Log.log(5, "IFAction", "saveShortTermBudgetDetails", "headsSet = " + headsSet);
    Log.log(5, "IFAction", "saveShortTermBudgetDetails", "headsIterator = " + headsIterator);

    HashMap headsToRender = new HashMap();
    HashMap subHeadsToRender = new HashMap();

    headsToRender = (HashMap)ifForm.getHeadsToRender();

    String headTitle = "";
    BudgetDetails budgetDetails = new BudgetDetails();
    ArrayList aBudgetHeadDetails = new ArrayList();

    String inflowOrOutflow = null;
    String term = null;
    String annualToDate = null;
    String annualFromDate = null;
    String month = null;
    String year = null;
    String modifiedByUser = null;

    inflowOrOutflow = ifForm.getInflowOrOutflow();
    term = ifForm.getAnnualOrShortTerm();
    annualToDate = ifForm.getAnnualToDate();
    annualFromDate = ifForm.getAnnualFromDate();
    month = ifForm.getMonth();
    year = ifForm.getYear();

    User loggedInUser = getUserInformation(request);
    String loggedUserId = loggedInUser.getUserId();

    Log.log(5, "IFAction", "saveShortTermBudgetDetails", "inflowOrOutflow = " + inflowOrOutflow);
    Log.log(5, "IFAction", "saveShortTermBudgetDetails", "loggerd man");
    Log.log(5, "IFAction", "saveShortTermBudgetDetails", "term = " + term);
    Log.log(5, "IFAction", "saveShortTermBudgetDetails", "annualToDate = " + annualToDate);
    Log.log(5, "IFAction", "saveShortTermBudgetDetails", "annualFromDate = " + annualFromDate);
    Log.log(5, "IFAction", "saveShortTermBudgetDetails", "month = " + month);
    Log.log(5, "IFAction", "saveShortTermBudgetDetails", "year = " + year);

    budgetDetails.setAnnualOrShortTerm(term);
    budgetDetails.setAnnualToDate(annualToDate);
    budgetDetails.setAnnualFromDate(annualFromDate);
    budgetDetails.setMonth(month);
    budgetDetails.setYear(year);
    budgetDetails.setModifiedBy(loggedUserId);
    budgetDetails.setInflowOrOutflow(inflowOrOutflow);

    while (headsIterator.hasNext())
    {
      BudgetHeadDetails budgetHeadDetails = new BudgetHeadDetails();
      headTitle = (String)headsIterator.next();

      Log.log(4, "IFAction", "saveShortTermBudgetDetails", "headTitle " + headTitle);

      if (heads.get(headTitle) != null)
      {
        budgetHeadDetails.setBudgetHead(headTitle);
        ArrayList aBudgetSubHeadDetails = null;
        subHeadsToRender = (HashMap)headsToRender.get(headTitle);
        String budgetAmount = (String)heads.get(headTitle);

        if ((budgetAmount == null) || (budgetAmount.equals("")))
        {
          budgetAmount = "0.0";
        }

        Log.log(5, "IFAction", "saveShortTermBudgetDetails", "budgetAmount " + budgetAmount);

        budgetHeadDetails.setBudgetAmount(Double.parseDouble(budgetAmount));

        Log.log(5, "IFAction", "saveShortTermBudgetDetails", "subHeadsToRender " + subHeadsToRender);

        if (subHeadsToRender != null)
        {
          aBudgetSubHeadDetails = new ArrayList();
          Set subHeadsSetToRender = subHeadsToRender.keySet();
          Iterator subHeadsIteratorToRender = subHeadsSetToRender.iterator();
          String subHeadTitle = "";
          while (subHeadsIteratorToRender.hasNext())
          {
            BudgetSubHeadDetails budgetSubHeadDetails = new BudgetSubHeadDetails();
            subHeadTitle = subHeadsIteratorToRender.next().toString();
            budgetSubHeadDetails.setSubHeadTitle(subHeadTitle);

            Log.log(5, "IFAction", "saveShortTermBudgetDetails", "subHeadTitle " + subHeadTitle);

            budgetAmount = (String)subHeads1.get(String.valueOf(headTitle) + "_" + subHeadTitle);

            if ((budgetAmount == null) || (budgetAmount.equals("")))
            {
              budgetAmount = "0.0";
            }

            Log.log(5, "IFAction", "saveShortTermBudgetDetails", "budgetAmount " + budgetAmount);

            budgetSubHeadDetails.setBudgetAmount(Double.parseDouble(budgetAmount));
            aBudgetSubHeadDetails.add(budgetSubHeadDetails);
            budgetSubHeadDetails = null;
          }
        }
        budgetHeadDetails.setBudgetSubHeadDetails(aBudgetSubHeadDetails);
      }
      else
      {
        Log.log(4, "IFAction", "saveShortTermBudgetDetails", "No head details ");
      }
      aBudgetHeadDetails.add(budgetHeadDetails);
    }
    budgetDetails.setBudgetHeadDetails(aBudgetHeadDetails);
    budgetDetails.setAnnualOrShortTerm("SHORTTERM");

    IFProcessor ifProcessor = new IFProcessor();
    ifProcessor.saveBudgetDetails(budgetDetails);

    String message = "Short Term Budget details saved Successfully";

    request.setAttribute("message", message);

    Log.log(4, "IFAction", "saveShortTermBudgetDetails", "Exited");
  }

  public ActionForward getFundsInflowDetails(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response)
    throws Exception
  {
    Log.log(4, "IFAction", "getFundsInflowDetails", "Entered");

    InvestmentForm investmentForm = (InvestmentForm)form;

    investmentForm.getHeads().clear();
    investmentForm.getSubHeads().clear();

    getInflowOutflowDetails(investmentForm, "I");

    Log.log(4, "IFAction", "getFundsInflowDetails", "Exited");

    return mapping.findForward("success");
  }

  public ActionForward getFundsOutflowDetails(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response)
    throws Exception
  {
    Log.log(4, "IFAction", "getFundsOutflowDetails", "Entered");

    InvestmentForm investmentForm = (InvestmentForm)form;

    investmentForm.getHeads().clear();
    investmentForm.getSubHeads().clear();

    getInflowOutflowDetails(investmentForm, "O");

    Log.log(4, "IFAction", "getFundsOutflowDetails", "Exited");

    return mapping.findForward("success");
  }

  private void getInflowOutflowDetails(InvestmentForm ifForm, String flag) throws DatabaseException {
    Log.log(4, "IFAction", "getInflowOutflowDetails", "Entered ");

    String dateOfFlow = ifForm.getDateOfFlow();

    Log.log(5, "IFAction", "getInflowOutflowDetails", "dateOfFlow " + dateOfFlow);

    SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");

    java.util.Date convertedDateOfFlow = dateFormat.parse(dateOfFlow, new ParsePosition(0));

    Log.log(5, "IFAction", "getInflowOutflowDetails", "conveted dateOfFlow " + convertedDateOfFlow);

    IFProcessor ifProcessor = new IFProcessor();

    HashMap headDetails = ifProcessor.getInflowOutflowHeadDetails(convertedDateOfFlow, flag);

    Set headSet = headDetails.keySet();

    Iterator headIterator = headSet.iterator();

    Vector budgetHeads = ifProcessor.getBudgetHeadTitles(flag);

    for (int i = 0; i < budgetHeads.size(); i++)
    {
      String head = (String)budgetHeads.get(i);

      HashMap subHeadDetails = ifProcessor.getInflowOutflowSubHeadDetails(head, convertedDateOfFlow, flag);

      if ((subHeadDetails != null) && (subHeadDetails.size() != 0))
      {
        Log.log(5, "IFAction", "getInflowOutflowDetails", "Sub-Heads are available ");

        Set subHeadSet = subHeadDetails.keySet();
        Iterator subHeadIterator = subHeadSet.iterator();

        while (subHeadIterator.hasNext())
        {
          String subHeadKey = (String)subHeadIterator.next();

          Log.log(5, "IFAction", "getInflowOutflowDetails", "Sub-Heads Key " + subHeadKey);

          ifForm.setSubHead(subHeadKey, subHeadDetails.get(subHeadKey));
        }
      }
    }

    while (headIterator.hasNext())
    {
      String key = (String)headIterator.next();
      ifForm.setHead(key, headDetails.get(key));
    }

    Log.log(4, "IFAction", "getInflowOutflowDetails", "Exited ");
  }

  public ActionForward updateBankAccountMaster(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response)
    throws Exception
  {
    Log.log(4, "IFAction", "updateBankAccountMaster", "Entered ");

    DynaActionForm dynaForm = (DynaActionForm)form;

    BankAccountDetail accountDetail = new BankAccountDetail();

    User user = getUserInformation(request);
    String userId = user.getUserId();

    IFProcessor ifProcessor = new IFProcessor();

    String accChosen = (String)dynaForm.get("accComb");
    BeanUtils.populate(accountDetail, dynaForm.getMap());

    String idbi = "IDBI BANK";
    Log.log(5, "IFAction", "updateBankAccountMaster", "acc chosen " + accChosen);

    if (!accChosen.equals(""))
    {
      accountDetail.setModAccountNumber(accountDetail.getAccountNumber());
      accountDetail.setModBankBranchName(accountDetail.getBankBranchName());
      accountDetail.setModBankName(accountDetail.getBankName());
      int index = accChosen.indexOf(",");
      String bankName = accChosen.substring(0, index).trim();
      int index1 = accChosen.indexOf("(");
      String branchName = accChosen.substring(index + 1, index1).trim();
      index = accChosen.indexOf(")");
      String accNumber = accChosen.substring(index1 + 1, index).trim();
      accountDetail.setAccountNumber(accNumber);
      accountDetail.setBankBranchName(branchName);
      accountDetail.setBankName(bankName);
    }
    else
    {
      ArrayList names = (ArrayList)dynaForm.get("bankNames");
      String comb = String.valueOf(accountDetail.getBankName().trim()) + " ," + accountDetail.getBankBranchName().trim() + "(" + accountDetail.getAccountNumber().trim() + ")";
      for (int i = 0; i < names.size(); i++)
      {
        Log.log(5, "IFAction", "updateBankAccountMaster", "1 " + idbi.indexOf(accountDetail.getBankName().trim().toUpperCase()));
        Log.log(5, "IFAction", "updateBankAccountMaster", "2 " + ((String)names.get(i)).toUpperCase().indexOf(accountDetail.getBankName().trim().toUpperCase()));
        if ((idbi.indexOf(accountDetail.getBankName().trim().toUpperCase()) >= 0) && (((String)names.get(i)).toUpperCase().indexOf(accountDetail.getBankName().trim().toUpperCase()) >= 0))
        {
          throw new MessageException("Bank Account Already Exists.");
        }
        if (((String)names.get(i)).equalsIgnoreCase(comb))
        {
          throw new MessageException("Bank Account Already Exists.");
        }
      }
    }

    ifProcessor.addBankAccountDetail(accountDetail, userId);

    String message = "Bank Account details saved Successfully";

    request.setAttribute("message", message);
    Log.log(4, "IFAction", "updateBankAccountMaster", "Exited ");

    return mapping.findForward("success");
  }

  public ActionForward showStatementDetails(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response)
    throws Exception
  {
    Log.log(4, "IFAction", "showStatementDetails", "Entered ");

    IFProcessor ifProcessor = new IFProcessor();
    ArrayList bankDetails = ifProcessor.getAllBanksWithAccountNumbers();

    ArrayList bankNames = new ArrayList(bankDetails.size());
    String bankName = "";
    for (int i = 0; i < bankDetails.size(); i++)
    {
      BankAccountDetail bankAccountDetail = (BankAccountDetail)bankDetails.get(i);
      bankName = String.valueOf(bankAccountDetail.getBankName()) + " ," + bankAccountDetail.getBankBranchName() + "(" + bankAccountDetail.getAccountNumber() + ")";

      bankNames.add(bankName);
    }
    Log.log(4, "IFAction", "showStatementDetails", "Exited");

    DynaActionForm dynaForm = (DynaActionForm)form;

    dynaForm.initialize(mapping);

    TransactionDetail transactionDetail = new TransactionDetail();
    Map transactionMap = new TreeMap();

    transactionMap.put("key-0", transactionDetail);

    java.util.Date currDate = new java.util.Date();

    CustomisedDate customToDate = new CustomisedDate();
    customToDate.setDate(currDate);

    dynaForm.set("transactions", transactionMap);

    dynaForm.set("bankNames", bankNames);

    dynaForm.set("statementDate", customToDate);

    return mapping.findForward("success");
  }

  public ActionForward addMoreTransactionDetails(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response)
    throws Exception
  {
    Log.log(4, "IFAction", "addMoreTransactionDetails", "Entered ");

    DynaActionForm dynaForm = (DynaActionForm)form;

    TransactionDetail transactionDetail = new TransactionDetail();

    Map transactions = (Map)dynaForm.get("transactions");

    Set keys = transactions.keySet();

    Iterator iterator = keys.iterator();

    int keyValue = 0;

    while (iterator.hasNext())
    {
      String key = (String)iterator.next();

      Log.log(5, "IFAction", "addMoreTransactionDetails", "key " + key);

      Log.log(5, "IFAction", "addMoreTransactionDetails", "key " + key);

      String substr = key.substring(key.indexOf("-") + 1, key.length());

      Log.log(5, "IFAction", "addMoreTransactionDetails", "substr " + substr);

      if (keyValue <= Integer.parseInt(substr))
      {
        keyValue = Integer.parseInt(substr);
        keyValue++;
      }
    }

    Log.log(5, "IFAction", "addMoreTransactionDetails", "keyValue " + keyValue);

    Map transactionMap = (Map)dynaForm.get("transactions");

    transactionMap.put("key-" + keyValue, transactionDetail);

    dynaForm.set("transactions", transactionMap);

    Log.log(4, "IFAction", "addMoreTransactionDetails", "Exited");

    return mapping.findForward("success");
  }

  public ActionForward updateStatementDetails(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response)
    throws Exception
  {
    Log.log(4, "IFAction", "updateStatementDetails", "Entered ");

    IFProcessor ifProcessor = new IFProcessor();

    DynaActionForm dynaForm = (DynaActionForm)form;

    Map transactionDetails = (Map)dynaForm.get("transactions");

    ArrayList transactions = new ArrayList();

    Set transactionSet = transactionDetails.keySet();

    Iterator transactionIterator = transactionSet.iterator();

    while (transactionIterator.hasNext())
    {
      Object key = transactionIterator.next();
      Log.log(5, "IFAction", "updateStatementDetails", "Key " + key);

      TransactionDetail transDetails = (TransactionDetail)transactionDetails.get(key);
      Log.log(5, "IFAction", "updateStatementDetails", "Transaction object details " + transDetails.getChequeNumber() + " " + transDetails.getTransactionDate() + " " + transDetails.getValueDate());

      transactions.add(transactionDetails.get(key));
    }

    StatementDetail statementDetail = new StatementDetail();

    BeanUtils.populate(statementDetail, dynaForm.getMap());

    statementDetail.setTransactionDetail(transactions);

    User user = getUserInformation(request);
    String userId = user.getUserId();
    String bankName = statementDetail.getBankName();

    int index = bankName.indexOf(",");

    int index1 = bankName.indexOf("(");

    String actualBankName = bankName.substring(0, index);

    String actualBranchName = bankName.substring(index + 1, index1);

    String accountNumber = bankName.substring(index1 + 1, bankName.length() - 1);

    Log.log(5, "IFAction", "updateStatementDetails", "actual bank name, branch name, account numbers" + index + "," + index1 + "," + actualBankName + "," + actualBranchName + "," + accountNumber);

    statementDetail.setBankName(actualBankName);
    statementDetail.setBankBranchName(actualBranchName);
    statementDetail.setAccountNumber(accountNumber);

    ifProcessor.addStatementDetail(statementDetail, userId);

    String message = "Bank Statement Details stored sucessfully";

    request.setAttribute("message", message);

    Log.log(4, "IFAction", "updateStatementDetails", "Exited ");

    return mapping.findForward("success");
  }

  public ActionForward showMaturityWiseCeiling(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response)
    throws Exception
  {
    Log.log(4, "IFAction", "showMaturityWiseCeiling", "Entered ");

    HttpSession session = request.getSession(false);
    session.setAttribute("ceilingFlag", "0");

    DynaActionForm dynaForm = (DynaActionForm)form;

    dynaForm.initialize(mapping);

    IFProcessor ifProcessor = new IFProcessor();

    ArrayList maturities = ifProcessor.getAllMaturities();
    dynaForm.set("maturities", maturities);

    Log.log(4, "IFAction", "showMaturityWiseCeiling", "Exited ");

    return mapping.findForward("success");
  }

  public ActionForward fetchMaturityWiseCeiling(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response)
    throws Exception
  {
    Log.log(4, "IFAction", "fetchMaturityWiseCeiling", "Entered ");

    HttpSession session = request.getSession(false);
    session.setAttribute("ceilingFlag", "1");

    DynaActionForm dynaForm = (DynaActionForm)form;
    String maturityType = (String)dynaForm.get("maturityType");
    IFProcessor ifProcessor = new IFProcessor();
    MaturityWiseCeiling matWiseCeiling = ifProcessor.getMaturityWiseCeiling(maturityType);
    if (matWiseCeiling != null)
    {
      BeanUtils.copyProperties(dynaForm, matWiseCeiling);
    }
    else
    {
      dynaForm.set("ceilingStartDate", null);
      dynaForm.set("ceilingEndDate", null);
      dynaForm.set("ceilingLimit", "0.0");
    }
    Log.log(4, "IFAction", "fetchMaturityWiseCeiling", "Exited ");

    return mapping.findForward("success");
  }

  public ActionForward setMaturityWiseCeiling(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response)
    throws Exception
  {
    Log.log(4, "IFAction", "setMaturityWiseCeiling", "Entered");

    DynaActionForm dynaForm = (DynaActionForm)form;

    MaturityWiseCeiling maturityCeiling = new MaturityWiseCeiling();

    BeanUtils.populate(maturityCeiling, dynaForm.getMap());

    Log.log(5, "IFAction", "setMaturityWiseCeiling", "end date " + maturityCeiling.getCeilingEndDate());

    IFProcessor ifProcessor = new IFProcessor();

    User user = getUserInformation(request);
    String userId = user.getUserId();

    ifProcessor.addMaturityWiseCeiling(maturityCeiling, userId);

    String message = "Maturity wise ceiling set Successfully";

    request.setAttribute("message", message);

    Log.log(4, "IFAction", "setMaturityWiseCeiling", "Exited");

    return mapping.findForward("success");
  }

  public ActionForward showInstrumenetWiseCeiling(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response)
    throws Exception
  {
    Log.log(4, "IFAction", "showInstrumenetWiseCeiling", "Entered ");

    DynaActionForm dynaForm = (DynaActionForm)form;

    dynaForm.initialize(mapping);

    IFProcessor ifProcessor = new IFProcessor();
    getInstrumentCategories(dynaForm);

    Log.log(4, "IFAction", "showInstrumenetWiseCeiling", "Exited ");

    return mapping.findForward("success");
  }

  public ActionForward fetchInstrumentWiseCeiling(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response)
    throws Exception
  {
    Log.log(4, "IFAction", "fetchInstrumentWiseCeiling", "Entered ");
    DynaActionForm dynaForm = (DynaActionForm)form;
    String instrName = (String)dynaForm.get("instrumentName");
    IFProcessor ifProcessor = new IFProcessor();
    InstrumentCategoryWiseCeiling instrWiseCeiling = ifProcessor.getInstrumentWiseCeiling(instrName);
    if (instrWiseCeiling != null)
    {
      BeanUtils.copyProperties(dynaForm, instrWiseCeiling);
    }
    else
    {
      dynaForm.set("ceilingStartDate", null);
      dynaForm.set("ceilingEndDate", null);
      dynaForm.set("ceilingLimit", "0.0");
    }
    Log.log(4, "IFAction", "fetchInstrumentWiseCeiling", "Exited ");

    return mapping.findForward("success");
  }

  public ActionForward setInstrumentWiseCeiling(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response)
    throws Exception
  {
    Log.log(4, "IFAction", "setInstrumenetWiseCeiling", "Entered");

    DynaActionForm dynaForm = (DynaActionForm)form;

    InstrumentCategoryWiseCeiling instCategory = new InstrumentCategoryWiseCeiling();

    BeanUtils.populate(instCategory, dynaForm.getMap());

    IFProcessor ifProcessor = new IFProcessor();

    User user = getUserInformation(request);
    String userId = user.getUserId();

    ifProcessor.addInstrumentCatWiseCeiling(instCategory, userId);

    String message = "Instrument Category Wise ceiling set Successfully";

    request.setAttribute("message", message);

    Log.log(4, "IFAction", "setInstrumenetWiseCeiling", "Exited");

    return mapping.findForward("success");
  }

  public ActionForward showInvesteeGroupWiseCeiling(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response)
    throws Exception
  {
    Log.log(4, "IFAction", "showInvesteeGroupWiseCeiling", "Entered ");

    DynaActionForm dynaForm = (DynaActionForm)form;

    dynaForm.initialize(mapping);

    IFProcessor ifProcessor = new IFProcessor();
    getAllInvesteeGroups(dynaForm);

    Log.log(4, "IFAction", "showInvesteeGroupWiseCeiling", "Exited ");

    return mapping.findForward("success");
  }

  public ActionForward fetchInvestGrpWiseCeiling(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response)
    throws Exception
  {
    Log.log(4, "IFAction", "fetchInvestGrpWiseCeiling", "Entered ");
    DynaActionForm dynaForm = (DynaActionForm)form;
    String investGroup = (String)dynaForm.get("investeeGroup");
    IFProcessor ifProcessor = new IFProcessor();
    InvesteeGroupWiseCeiling investGrpWiseCeiling = ifProcessor.getIGroupWiseCeiling(investGroup);
    if (investGrpWiseCeiling != null)
    {
      BeanUtils.copyProperties(dynaForm, investGrpWiseCeiling);
    }
    else
    {
      dynaForm.set("ceilingStartDate", null);
      dynaForm.set("ceilingEndDate", null);
      dynaForm.set("ceilingLimit", "0.0");
      dynaForm.set("ceilingAmount", "0.0");
    }
    Log.log(4, "IFAction", "fetchInvestGrpWiseCeiling", "Exited ");

    return mapping.findForward("success");
  }

  public ActionForward setInvesteeGroupWiseCeiling(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response)
    throws Exception
  {
    Log.log(4, "IFAction", "setInvesteeGroupWiseCeiling", "Entered");

    DynaActionForm dynaForm = (DynaActionForm)form;

    InvesteeGroupWiseCeiling investeeGroupCeiling = new InvesteeGroupWiseCeiling();

    BeanUtils.populate(investeeGroupCeiling, dynaForm.getMap());

    IFProcessor ifProcessor = new IFProcessor();

    User user = getUserInformation(request);
    String userId = user.getUserId();

    ifProcessor.addInvesteeGroupWiseCeiling(investeeGroupCeiling, userId);

    String message = "Investee group wise ceiling set Successfully";

    request.setAttribute("message", message);

    Log.log(4, "IFAction", "setInvesteeGroupWiseCeiling", "Exited");

    return mapping.findForward("success");
  }

  public ActionForward showInvesteeWiseCeiling(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response)
    throws Exception
  {
    Log.log(4, "IFAction", "showInvesteeWiseCeiling", "Entered ");

    DynaActionForm dynaForm = (DynaActionForm)form;

    dynaForm.initialize(mapping);

    IFProcessor ifProcessor = new IFProcessor();
    getAllInvesteeGroups(dynaForm);

    Log.log(4, "IFAction", "showInvesteeWiseCeiling", "Exited ");

    return mapping.findForward("success");
  }

  public ActionForward fetchInvesteeWiseCeiling(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response)
    throws Exception
  {
    Log.log(4, "IFAction", "fetchInvesteeWiseCeiling", "Entered ");
    DynaActionForm dynaForm = (DynaActionForm)form;
    String investeeGroup = (String)dynaForm.get("investeeGroup");
    String investeeName = (String)dynaForm.get("investeeName");
    IFProcessor ifProcessor = new IFProcessor();
    InvesteeWiseCeiling investeWiseCeiling = ifProcessor.getInvesteeWiseCeiling(investeeGroup, investeeName);
    if (investeWiseCeiling != null)
    {
      BeanUtils.copyProperties(dynaForm, investeWiseCeiling);
    }
    else
    {
      dynaForm.set("ceilingStartDate", null);
      dynaForm.set("ceilingEndDate", null);
      dynaForm.set("networth", "0.0");
      dynaForm.set("tangibleAssets", "0.0");
      dynaForm.set("ceilingLimit", "0.0");
      dynaForm.set("ceilingAmount", "0.0");
    }
    Log.log(4, "IFAction", "fetchInvesteeWiseCeiling", "Exited ");

    return mapping.findForward("success");
  }

  public ActionForward getInvesteeNames(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response)
    throws Exception
  {
    Log.log(4, "IFAction", "getInvesteeNames", "Entered ");

    DynaActionForm dynaForm = (DynaActionForm)form;

    IFProcessor ifProcessor = new IFProcessor();
    getAllInvesteeNamesForGroup(dynaForm);

    Log.log(4, "IFAction", "getInvesteeNames", "Exited ");

    return mapping.findForward("success");
  }

  public ActionForward setInvesteeWiseCeiling(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response)
    throws Exception
  {
    Log.log(4, "IFAction", "setInvesteeWiseCeiling", "Entered");

    DynaActionForm dynaForm = (DynaActionForm)form;

    InvesteeWiseCeiling investeeCeiling = new InvesteeWiseCeiling();

    BeanUtils.populate(investeeCeiling, dynaForm.getMap());

    IFProcessor ifProcessor = new IFProcessor();

    User user = getUserInformation(request);
    String userId = user.getUserId();

    ifProcessor.addInvesteeWiseCeiling(investeeCeiling, userId);

    String message = "Investee wise ceiling set Successfully";

    request.setAttribute("message", message);

    Log.log(4, "IFAction", "setInvesteeWiseCeiling", "Exited");

    return mapping.findForward("success");
  }

  public ActionForward showRatingWiseCeiling(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response)
    throws Exception
  {
    Log.log(4, "IFAction", "showRatingWiseCeiling", "Entered ");

    DynaActionForm dynaForm = (DynaActionForm)form;

    dynaForm.initialize(mapping);

    IFProcessor ifProcessor = new IFProcessor();
    getAllInvesteeGroups(dynaForm);
    getInstrumentTypes(dynaForm);
    getAllRatings(dynaForm);

    ArrayList agencyNames = ifProcessor.showRatingAgency();
    dynaForm.set("agencies", agencyNames);

    Log.log(4, "IFAction", "showRatingWiseCeiling", "Exited ");

    return mapping.findForward("success");
  }

  public ActionForward fetchRatingWiseCeiling(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response)
    throws Exception
  {
    Log.log(4, "IFAction", "fetchRatingWiseCeiling", "Entered ");
    DynaActionForm dynaForm = (DynaActionForm)form;
    String investeeGroup = (String)dynaForm.get("investeeGroup");
    String investeeName = (String)dynaForm.get("investeeName");
    String instrumentName = (String)dynaForm.get("instrumentName");
    IFProcessor ifProcessor = new IFProcessor();
    RatingWiseCeiling ratingWiseCeiling = ifProcessor.getRatingWiseCeiling(investeeGroup, investeeName, instrumentName);
    if (ratingWiseCeiling != null)
    {
      String agencyName = ratingWiseCeiling.getRatingAgency();
      Log.log(4, "IFAction", "fetchRatingWiseCeiling", "agencyName " + agencyName);
      ArrayList ratings = ifProcessor.getRatingsForAgency(agencyName);
      Log.log(4, "IFAction", "fetchRatingWiseCeiling", "ratings " + ratings.size());

      BeanUtils.copyProperties(dynaForm, ratingWiseCeiling);
      dynaForm.set("instrumentRatings", ratings);
      String rating = ratingWiseCeiling.getRating();
      Log.log(4, "IFAction", "fetchRatingWiseCeiling", "rating " + rating);
      if (ratings.contains(rating))
      {
        dynaForm.set("rating", rating);
      }

    }
    else
    {
      dynaForm.set("rating", "");
      dynaForm.set("ratingAgency", "");
      dynaForm.set("ceilingStartDate", null);
      dynaForm.set("ceilingEndDate", null);
    }
    Log.log(4, "IFAction", "fetchRatingWiseCeiling", "Exited ");

    return mapping.findForward("success");
  }

  public ActionForward setRatingWiseCeiling(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response)
    throws Exception
  {
    Log.log(4, "IFAction", "setRatingWiseCeiling", "Entered");

    DynaActionForm dynaForm = (DynaActionForm)form;

    RatingWiseCeiling ratingCeiling = new RatingWiseCeiling();

    BeanUtils.populate(ratingCeiling, dynaForm.getMap());

    IFProcessor ifProcessor = new IFProcessor();

    User user = getUserInformation(request);
    String userId = user.getUserId();

    ifProcessor.setRatingWiseCeiling(ratingCeiling, userId);

    String message = "Rating wise ceiling set Successfully";

    request.setAttribute("message", message);

    Log.log(4, "IFAction", "setRatingWiseCeiling", "Exited");

    return mapping.findForward("success");
  }

  public ActionForward chooseInvestee(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response)
    throws Exception
  {
    Log.log(4, "IFAction", "chooseInvestee", "Entered");

    DynaActionForm dynaForm = (DynaActionForm)form;
    Log.log(4, "IFAction", "chooseInvestee", "Entered " + dynaForm);
    dynaForm.initialize(mapping);

    dynaForm.set("currentDate", new java.util.Date());

    Log.log(4, "IFAction", "chooseInvestee", "Exited");

    return mapping.findForward("success");
  }

  public ActionForward getExposureDetails(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response)
    throws Exception
  {
    Log.log(4, "IFAction", "getExposureDetails", "Entered");

    DynaActionForm dynaForm = (DynaActionForm)form;

    java.util.Date proposedDate = (java.util.Date)dynaForm.get("proposedDate");

    java.util.Date sysDate = (java.util.Date)dynaForm.get("currentDate");
    double corpusAmount = ((Double)dynaForm.get("exposureCorpusAmount")).doubleValue();

    IFProcessor ifProcessor = new IFProcessor();

    ArrayList investeeWiseExpDetails = ifProcessor.getExposure(sysDate, proposedDate, corpusAmount);
    dynaForm.set("investeeWiseDetails", investeeWiseExpDetails);

    ArrayList investeeGrpWiseDetails = ifProcessor.getInvesteeGrpWiseDetails(sysDate, proposedDate, corpusAmount);
    dynaForm.set("investeeGrpWiseDetails", investeeGrpWiseDetails);

    double liveInv = 0.0D;
    double investedAmt = 0.0D;
    double maturedAmt = 0.0D;
    double corpusAmt = 0.0D;
    double otherAmt = 0.0D;
    double expAmt = 0.0D;

    if (dynaForm.get("availableLiveInv").equals("Y"))
    {
      liveInv = ((Double)dynaForm.get("liveInvtAmount")).doubleValue();
    }
    if (dynaForm.get("availableInvAmount").equals("Y"))
    {
      investedAmt = ((Double)dynaForm.get("investedAmount")).doubleValue();
    }
    if (dynaForm.get("availableMaturingAmount").equals("Y"))
    {
      maturedAmt = ((Double)dynaForm.get("maturedAmount")).doubleValue();
    }
    if (dynaForm.get("availableCorpusAmount").equals("Y"))
    {
      corpusAmt = ((Double)dynaForm.get("exposureCorpusAmount")).doubleValue();
    }
    if (dynaForm.get("otherReceiptsAmount").equals("Y"))
    {
      otherAmt = ((Double)dynaForm.get("otherReceiptsAmount")).doubleValue();
    }
    if (dynaForm.get("availableExpAmount").equals("Y"))
    {
      expAmt = ((Double)dynaForm.get("expenditureAmount")).doubleValue();
    }

    double surplusAmount = liveInv + investedAmt + maturedAmt + corpusAmt + otherAmt - expAmt;

    ArrayList maturityWiseDetails = ifProcessor.getMaturityWiseDetails(sysDate, proposedDate, surplusAmount);
    dynaForm.set("maturityWiseDetails", maturityWiseDetails);

    ArrayList instCatWiseExpReport = ifProcessor.getInstCategoryWiseDetails(sysDate, proposedDate, surplusAmount);
    dynaForm.set("instCatWiseDetails", instCatWiseExpReport);

    Log.log(4, "IFAction", "getExposureDetails", "Exited");

    return mapping.findForward("success");
  }

  public ActionForward getPositionDetails(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response)
    throws Exception
  {
    Log.log(4, "IFAction", "getPositionDetails", "Entered");
    DynaActionForm dynaForm = (DynaActionForm)form;

    IFProcessor ifProcessor = new IFProcessor();

    java.util.Date sysDate = (java.util.Date)dynaForm.get("currentDate");
    java.util.Date proposedDate = (java.util.Date)dynaForm.get("proposedDate");

    Log.log(4, "IFAction", "getPositionDetails", "proposedDate :" + proposedDate);

    ExposureDetails exposureDetails = ifProcessor.getPositionDetails(sysDate, proposedDate);

    BeanUtils.copyProperties(dynaForm, exposureDetails);

    Log.log(4, "IFAction", "getPositionDetails", "Exited");

    return mapping.findForward("success");
  }

  public ActionForward statementReport(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response)
    throws Exception
  {
    Log.log(4, "IFAction", "statementReport", "Entered");
    ArrayList dan = new ArrayList();
    IFProcessor ifProcessor = new IFProcessor();
    DynaActionForm dynaForm = (DynaActionForm)form;
    java.sql.Date endDate = null;
    java.util.Date eDate = (java.util.Date)dynaForm.get("dateOfTheDocument");
    endDate = new java.sql.Date(eDate.getTime());
    dan = ifProcessor.statementReport(endDate);
    dynaForm.set("mliDetails", dan);

    if ((dan == null) || (dan.size() == 0))
    {
      throw new NoDataException("No Data is available for the values entered");
    }

    Log.log(4, "IFAction", "statementReport", "Exited");

    return mapping.findForward("success");
  }

  public ActionForward statementReportDetails(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response)
    throws Exception
  {
    Log.log(4, "IFAction", "statementReportDetails", "Entered");
    ArrayList dan = new ArrayList();
    IFProcessor ifProcessor = new IFProcessor();
    DynaActionForm dynaForm = (DynaActionForm)form;
    String cgpan = request.getParameter("cgpan");
    java.sql.Date endDate = null;
    java.util.Date eDate = (java.util.Date)dynaForm.get("dateOfTheDocument");
    endDate = new java.sql.Date(eDate.getTime());
    dan = ifProcessor.statementReportDetails(cgpan, endDate);
    dynaForm.set("statementDetails", dan);

    if ((dan == null) || (dan.size() == 0))
    {
      throw new NoDataException("No Data is available for this particular date, Please Enter Any Other Date ");
    }

    Log.log(4, "IFAction", "statementReportDetails", "Exited");

    return mapping.findForward("success");
  }

  public ActionForward fdiReport(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response)
    throws Exception
  {
    Log.log(4, "IFAction", "fdiReport", "Entered");
    ArrayList fdReport = new ArrayList();
    IFProcessor ifProcessor = new IFProcessor();
    DynaActionForm dynaForm = (DynaActionForm)form;
    String status = (String)dynaForm.get("status");
    java.sql.Date startDate = null;
    java.sql.Date endDate = null;
    java.util.Date sDate = (java.util.Date)dynaForm.get("dateOfTheDocument");
    String stDate = String.valueOf(sDate);
    if ((stDate == null) || (stDate.equals("")))
    {
      startDate = null;
    }
    else if (stDate != null)
    {
      startDate = new java.sql.Date(sDate.getTime());
    }
    java.util.Date eDate = (java.util.Date)dynaForm.get("dateOfTheDocument1");
    endDate = new java.sql.Date(eDate.getTime());

    if (status.equals("depositDate"))
    {
      fdReport = ifProcessor.getFdReportForDepositDate(startDate, endDate);

      dynaForm.set("fdReport", fdReport);
      if ((fdReport == null) || (fdReport.size() == 0))
      {
        throw new NoDataException("No Data is available for the values entered, Please Enter Any Other Value ");
      }

      Log.log(4, "IFAction", "fdiReport", "Exited");

      return mapping.findForward("success2");
    }

    if (status.equals("maturityDate"))
    {
      fdReport = ifProcessor.getFdReportForMaturityDate(startDate, endDate);
      dynaForm.set("fdReport", fdReport);
      if ((fdReport == null) || (fdReport.size() == 0))
      {
        throw new NoDataException("No Data is available for the values entered, Please Enter Any Other Value ");
      }

      Log.log(4, "IFAction", "fdiReport", "Exited");

      return mapping.findForward("success3");
    }

    fdReport = ifProcessor.getFdReport(startDate, endDate);

    dynaForm.set("fdReport", fdReport);
    if ((fdReport == null) || (fdReport.size() == 0))
    {
      throw new NoDataException("No Data is available for the values entered, Please Enter Any Other Value ");
    }

    Log.log(4, "IFAction", "fdiReport", "Exited");

    return mapping.findForward("success1");
  }

  public ActionForward fdReceiptDetails(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response)
    throws Exception
  {
    Log.log(4, "IFAction", "fdReceiptDetails", "Entered");
    ArrayList fdReceiptDetails = new ArrayList();
    IFProcessor ifProcessor = new IFProcessor();
    DynaActionForm dynaForm = (DynaActionForm)form;
    String number = (String)dynaForm.get("number");
    java.sql.Date startDate = null;
    java.sql.Date endDate = null;
    java.util.Date sDate = (java.util.Date)dynaForm.get("dateOfTheDocument");
    String stDate = String.valueOf(sDate);
    if ((stDate == null) || (stDate.equals("")))
    {
      startDate = null;
    }
    else if (stDate != null)
    {
      startDate = new java.sql.Date(sDate.getTime());
    }
    java.util.Date eDate = (java.util.Date)dynaForm.get("dateOfTheDocument1");
    endDate = new java.sql.Date(eDate.getTime());
    fdReceiptDetails = ifProcessor.fdReceiptDetails(number, startDate, endDate);
    dynaForm.set("fdReceiptDetails", fdReceiptDetails);

    if ((fdReceiptDetails == null) || (fdReceiptDetails.size() == 0))
    {
      throw new NoDataException("No Data is available for the values entered, Please Enter Any Other Value ");
    }

    Log.log(4, "IFAction", "fdReceiptDetails", "Exited");

    return mapping.findForward("success");
  }

  public ActionForward fdDetailsForDeposit(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response)
    throws Exception
  {
    Log.log(4, "IFAction", "fdDetailsForDeposit", "Entered");
    ArrayList fdReceiptDetails = new ArrayList();
    IFProcessor ifProcessor = new IFProcessor();
    DynaActionForm dynaForm = (DynaActionForm)form;
    String investee = request.getParameter("investee");
    java.sql.Date startDate = null;
    java.sql.Date endDate = null;
    java.util.Date sDate = (java.util.Date)dynaForm.get("dateOfTheDocument");
    String stDate = String.valueOf(sDate);
    if ((stDate == null) || (stDate.equals("")))
    {
      startDate = null;
    }
    else if (stDate != null)
    {
      startDate = new java.sql.Date(sDate.getTime());
    }
    java.util.Date eDate = (java.util.Date)dynaForm.get("dateOfTheDocument1");
    endDate = new java.sql.Date(eDate.getTime());
    fdReceiptDetails = ifProcessor.fdDetailsForDeposit(investee, startDate, endDate);
    dynaForm.set("fdReport", fdReceiptDetails);

    if ((fdReceiptDetails == null) || (fdReceiptDetails.size() == 0))
    {
      throw new NoDataException("No Data is available for the values entered, Please Enter Any Other Value ");
    }

    Log.log(4, "IFAction", "fdDetailsForDeposit", "Exited");

    return mapping.findForward("success");
  }

  public ActionForward fdDetailsForMaturity(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response)
    throws Exception
  {
    Log.log(4, "IFAction", "fdDetailsForMaturity", "Entered");
    ArrayList fdReceiptDetails = new ArrayList();
    IFProcessor ifProcessor = new IFProcessor();
    DynaActionForm dynaForm = (DynaActionForm)form;
    String investee = request.getParameter("investee");
    java.sql.Date startDate = null;
    java.sql.Date endDate = null;
    java.util.Date sDate = (java.util.Date)dynaForm.get("dateOfTheDocument");
    String stDate = String.valueOf(sDate);
    if ((stDate == null) || (stDate.equals("")))
    {
      startDate = null;
    }
    else if (stDate != null)
    {
      startDate = new java.sql.Date(sDate.getTime());
    }
    java.util.Date eDate = (java.util.Date)dynaForm.get("dateOfTheDocument1");
    endDate = new java.sql.Date(eDate.getTime());

    fdReceiptDetails = ifProcessor.fdDetailsForMaturity(investee, startDate, endDate);
    dynaForm.set("fdReport", fdReceiptDetails);

    if ((fdReceiptDetails == null) || (fdReceiptDetails.size() == 0))
    {
      throw new NoDataException("No Data is available for the values entered, Please Enter Any Other Value ");
    }

    Log.log(4, "IFAction", "fdDetailsForMaturity", "Exited");

    return mapping.findForward("success");
  }

  public ActionForward showInvesteeGroup(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response)
    throws Exception
  {
    Log.log(4, "IFAction", "showInvesteeGroup", "Entered");

    HttpSession session = request.getSession(false);
    session.setAttribute("modFlag", "0");

    DynaActionForm dynaForm = (DynaActionForm)form;
    dynaForm.initialize(mapping);
    getAllInvesteeGroups(dynaForm);

    Log.log(4, "IFAction", "showInvesteeGroup", "Exited");

    return mapping.findForward("success");
  }

  public ActionForward showModInvesteeGroup(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response)
    throws Exception
  {
    Log.log(4, "IFAction", "showModInvesteeGroup", "Entered");

    HttpSession session = request.getSession(false);
    session.setAttribute("modFlag", "1");

    DynaActionForm dynaForm = (DynaActionForm)form;

    getAllInvesteeGroups(dynaForm);

    dynaForm.set("modInvesteeGroup", dynaForm.get("investeeGroup"));

    Log.log(4, "IFAction", "showModInvesteeGroup", "Exited");

    return mapping.findForward("success");
  }

  public ActionForward showInvesteeList(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response)
    throws Exception
  {
    Log.log(4, "IFAction", "showInvesteeMaster", "Entered");

    HttpSession session = request.getSession(false);
    session.setAttribute("modFlag", "1");

    DynaActionForm dynaForm = (DynaActionForm)form;
    getAllInvesteeNamesForGroup(dynaForm);
    dynaForm.set("investeeTangibleAssets", new Double(0.0D).toString());
    dynaForm.set("investeeNetWorth", new Double(0.0D).toString());
    dynaForm.set("modInvestee", "");
    dynaForm.set("newInvestee", "");

    Log.log(4, "IFAction", "showInvesteeMaster", "Exited");

    return mapping.findForward("success");
  }

  public ActionForward showInvesteeDetail(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response)
    throws Exception
  {
    Log.log(4, "IFAction", "showInvesteeDetail", "Entered");

    HttpSession session = request.getSession(false);
    session.setAttribute("modFlag", "2");

    DynaActionForm dynaForm = (DynaActionForm)form;
    String invGrp = (String)dynaForm.get("investeeGroup");
    String invName = (String)dynaForm.get("investee1");

    InvesteeDetail invDtl = new InvesteeDetail();
    IFProcessor ifProcessor = new IFProcessor();
    if ((invName != null) && (!invName.equals("")))
    {
      invDtl = ifProcessor.getInvesteeDetails(invGrp, invName);

      DecimalFormat df = new DecimalFormat("#############.##");
      df.setDecimalSeparatorAlwaysShown(false);

      dynaForm.set("investeeTangibleAssets", df.format(invDtl.getInvesteeTangibleAssets()));
      dynaForm.set("investeeNetWorth", df.format(invDtl.getInvesteeNetWorth()));

      dynaForm.set("modInvestee", invName);
      dynaForm.set("newInvestee", "");

      Log.log(4, "IFAction", "showInvesteeDetail", "ta " + dynaForm.get("investeeTangibleAssets"));
      Log.log(4, "IFAction", "showInvesteeDetail", "nw " + dynaForm.get("investeeNetWorth"));
    }
    else
    {
      dynaForm.set("investeeTangibleAssets", "");
      dynaForm.set("investeeNetWorth", "");
      dynaForm.set("modInvestee", "");
      dynaForm.set("newInvestee", "");
    }

    Log.log(4, "IFAction", "showInvesteeDetail", "Exited");

    return mapping.findForward("success");
  }

  public ActionForward showMaturityMaster(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response)
    throws Exception
  {
    Log.log(4, "IFAction", "showMaturityMaster", "Entered");

    HttpSession session = request.getSession(false);
    session.setAttribute("modFlag", "0");

    DynaActionForm dynaForm = (DynaActionForm)form;
    dynaForm.initialize(mapping);
    getAllMaturities(dynaForm);

    Log.log(4, "IFAction", "showMaturityMaster", "Exited");

    return mapping.findForward("success");
  }

  public ActionForward showMaturityDetails(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response)
    throws Exception
  {
    Log.log(4, "IFAction", "showMaturityDetails", "Entered");

    HttpSession session = request.getSession(false);
    session.setAttribute("modFlag", "1");

    DynaActionForm dynaForm = (DynaActionForm)form;
    String matType = (String)dynaForm.get("maturityType");

    MaturityDetail matDtl = new MaturityDetail();
    IFProcessor ifProcessor = new IFProcessor();
    Log.log(4, "IFAction", "showMaturityDetails", "mat type " + matType);
    if ((matType != null) && (!matType.equals("")))
    {
      matDtl = ifProcessor.getMaturityDetails(matType);

      dynaForm.set("modMaturityType", matDtl.getMaturityType());
      dynaForm.set("maturityDescription", matDtl.getMaturityDescription());
      dynaForm.set("newMaturityType", "");
    }
    else
    {
      dynaForm.set("modMaturityType", "");
      dynaForm.set("maturityDescription", "");
      dynaForm.set("newMaturityType", "");
    }

    Log.log(4, "IFAction", "showMaturityDetails", "Exited");

    return mapping.findForward("success");
  }

  public ActionForward showBudgetHeadMaster(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response)
    throws Exception
  {
    Log.log(4, "IFAction", "showBudgetHeadMaster", "Entered");

    HttpSession session = request.getSession(false);
    session.setAttribute("modFlag", "0");

    DynaActionForm dynaForm = (DynaActionForm)form;
    dynaForm.initialize(mapping);

    IFProcessor ifProcessor = new IFProcessor();
    ArrayList budgetHeads = ifProcessor.getBudgetHeadTypes("I");

    dynaForm.set("budgetHeadsList", budgetHeads);

    Log.log(4, "IFAction", "showBudgetHeadMaster", "Exited");

    return mapping.findForward("success");
  }

  public ActionForward showBudgetHeadList(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response)
    throws Exception
  {
    Log.log(4, "IFAction", "showBudgetHeadList", "Entered");

    HttpSession session = request.getSession(false);
    session.setAttribute("modFlag", "1");

    DynaActionForm dynaForm = (DynaActionForm)form;
    String headType = (String)dynaForm.get("budgetHeadType");

    IFProcessor ifProcessor = new IFProcessor();
    Log.log(4, "IFAction", "showBudgetHeadList", "head type " + headType);
    ArrayList budgetHeads = ifProcessor.getBudgetHeadTypes(headType);

    dynaForm.set("budgetHeadsList", budgetHeads);

    Log.log(4, "IFAction", "showBudgetHeadList", "Exited");

    return mapping.findForward("success");
  }

  public ActionForward showBudgetHeadDetail(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response)
    throws Exception
  {
    Log.log(4, "IFAction", "showBudgetHeadDetail", "Entered");

    HttpSession session = request.getSession(false);
    session.setAttribute("modFlag", "2");

    DynaActionForm dynaForm = (DynaActionForm)form;

    dynaForm.set("modBudgetHead", dynaForm.get("budgetHead"));

    Log.log(4, "IFAction", "showBudgetHeadDetail", "Exited");

    return mapping.findForward("success");
  }

  public ActionForward showBudgetSubHeadList(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response)
    throws Exception
  {
    Log.log(4, "IFAction", "showBudgetSubHeadList", "Entered");

    HttpSession session = request.getSession(false);
    session.setAttribute("modFlag", "1");

    DynaActionForm dynaForm = (DynaActionForm)form;
    String headType = (String)dynaForm.get("budgetHeadType");
    String headTitle = (String)dynaForm.get("budgetHead");

    IFProcessor ifProcessor = new IFProcessor();
    Log.log(4, "IFAction", "showBudgetSubHeadList", "head type " + headType);
    Log.log(4, "IFAction", "showBudgetSubHeadList", "head title " + headTitle);
    Vector budgetSubHeads = ifProcessor.getBudgetSubHeadTitles(headTitle, headType);

    dynaForm.set("budgetSubHeadsList", budgetSubHeads);

    Log.log(4, "IFAction", "showBudgetSubHeadList", "Exited");

    return mapping.findForward("success");
  }

  public ActionForward showBudgetSubHeadDetail(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response)
    throws Exception
  {
    Log.log(4, "IFAction", "showBudgetSubHeadDetail", "Entered");

    HttpSession session = request.getSession(false);
    session.setAttribute("modFlag", "2");

    DynaActionForm dynaForm = (DynaActionForm)form;

    dynaForm.set("modBudgetSubHeadTitle", dynaForm.get("budgetSubHeadTitle"));

    Log.log(4, "IFAction", "showBudgetSubHeadDetail", "Exited");

    return mapping.findForward("success");
  }

  public ActionForward showInstrumentList(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response)
    throws Exception
  {
    Log.log(4, "IFAction", "showInstrumentList", "Entered");

    HttpSession session = request.getSession(false);
    session.setAttribute("modFlag", "1");

    DynaActionForm dynaForm = (DynaActionForm)form;
    String instType = (String)dynaForm.get("instrumentType");

    IFProcessor ifProcessor = new IFProcessor();
    Log.log(4, "IFAction", "showInstrumentList", "instrument type " + instType);
    ArrayList instruments = ifProcessor.getInstrumentTypes(instType);

    dynaForm.set("instrumentNames", instruments);
    dynaForm.set("newInstrumentName", "");
    dynaForm.set("modInstrumentName", "");
    dynaForm.set("instrumentDescription", "");

    Log.log(4, "IFAction", "showInstrumentList", "Exited");

    return mapping.findForward("success");
  }

  public ActionForward showInstrumentDetails(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response)
    throws Exception
  {
    Log.log(4, "IFAction", "showInstrumentDetails", "Entered");

    HttpSession session = request.getSession(false);
    session.setAttribute("modFlag", "2");

    DynaActionForm dynaForm = (DynaActionForm)form;
    String instName = (String)dynaForm.get("instrumentName");

    IFProcessor ifProcessor = new IFProcessor();
    Log.log(4, "IFAction", "showInstrumentDetails", "instrument name " + instName);
    InstrumentDetail instDetail = new InstrumentDetail();

    if (!instName.equals(""))
    {
      instDetail = ifProcessor.getInstrumentDetail(instName);
      dynaForm.set("newInstrumentName", "");
      dynaForm.set("modInstrumentName", instName);
      dynaForm.set("instrumentDescription", instDetail.getInstrumentDescription());
    }
    else
    {
      dynaForm.set("newInstrumentName", "");
      dynaForm.set("modInstrumentName", "");
      dynaForm.set("instrumentDescription", "");
    }

    Log.log(4, "IFAction", "showInstrumentList", "Exited");

    return mapping.findForward("success");
  }

  public ActionForward showInstrumentFeatures(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response)
    throws Exception
  {
    Log.log(4, "IFAction", "showInstrumentFeatures", "Entered");

    HttpSession session = request.getSession(false);
    session.setAttribute("modFlag", "0");

    DynaActionForm dynaForm = (DynaActionForm)form;
    getInstrumentFeatures(dynaForm);

    Log.log(4, "IFAction", "showInstrumentFeatures", "Exited");

    return mapping.findForward("success");
  }

  public ActionForward showInstFeaturesDetails(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response)
    throws Exception
  {
    Log.log(4, "IFAction", "showInstFeaturesDetails", "Entered");

    HttpSession session = request.getSession(false);
    session.setAttribute("modFlag", "1");

    DynaActionForm dynaForm = (DynaActionForm)form;
    String instFeature = (String)dynaForm.get("instrumentFeatures");
    IFProcessor ifProcessor = new IFProcessor();

    InstrumentFeature instrumentFeature = new InstrumentFeature();
    if (!instFeature.equals(""))
    {
      instrumentFeature = ifProcessor.getInstFeaturesDetails(instFeature);
      BeanUtils.copyProperties(dynaForm, instrumentFeature);
      dynaForm.set("newInstrumentFeatures", "");
    }
    else
    {
      dynaForm.set("newInstrumentFeatures", "");
      dynaForm.set("modInstrumentFeatures", "");
      dynaForm.set("instrumentFeatureDescription", "");
    }

    Log.log(4, "IFAction", "showInstFeaturesDetails", "Exited");

    return mapping.findForward("success");
  }

  public ActionForward showInstSchemeDetails(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response)
    throws Exception
  {
    Log.log(4, "IFAction", "showInstSchemeDetails", "Entered");

    HttpSession session = request.getSession(false);
    session.setAttribute("modFlag", "2");

    DynaActionForm dynaForm = (DynaActionForm)form;
    String instScheme = (String)dynaForm.get("instrumentSchemeType");
    IFProcessor ifProcessor = new IFProcessor();

    if (!instScheme.equals(""))
    {
      String instSchemeDesc = ifProcessor.getInstSchemeDetails(instScheme);
      dynaForm.set("newInstrumentSchemeType", "");
      dynaForm.set("modInstrumentSchemeType", instScheme);
      dynaForm.set("instrumentSchemeDescription", instSchemeDesc);
    }
    else
    {
      dynaForm.set("newInstrumentSchemeType", "");
      dynaForm.set("modInstrumentSchemeType", "");
      dynaForm.set("instrumentSchemeDescription", "");
    }

    Log.log(4, "IFAction", "showInstSchemeDetails", "Exited");

    return mapping.findForward("success");
  }

  public ActionForward getInstrumentSchemes(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response)
    throws Exception
  {
    HttpSession session = request.getSession(false);
    session.setAttribute("modFlag", "1");

    DynaActionForm dynaForm = (DynaActionForm)form;
    String inst = (String)dynaForm.get("instrument");
    if (!inst.equals(""))
    {
      getInstrumentSchemeTypes(dynaForm);
    }
    else
    {
      dynaForm.set("instrumentSchemeTypes", new ArrayList());
      dynaForm.set("instrumentSchemeType", "");
      dynaForm.set("newInstrumentSchemeType", "");
      dynaForm.set("modInstrumentSchemeType", "");
      dynaForm.set("instrumentSchemeDescription", "");
    }

    return mapping.findForward("success");
  }

  public ActionForward showRatingMaster(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response)
    throws Exception
  {
    Log.log(4, "IFAction", "showRatingMaster", "Exited");

    HttpSession session = request.getSession(false);
    session.setAttribute("modFlag", "0");

    DynaActionForm dynaForm = (DynaActionForm)form;
    getAllRatings(dynaForm);
    Log.log(4, "IFAction", "showRatingMaster", "Exited");

    return mapping.findForward("success");
  }

  public ActionForward showRatingDetails(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response)
    throws Exception
  {
    Log.log(4, "IFAction", "showRatingDetails", "Entered");

    HttpSession session = request.getSession(false);
    session.setAttribute("modFlag", "1");

    DynaActionForm dynaForm = (DynaActionForm)form;
    String rating = (String)dynaForm.get("rating");
    IFProcessor ifProcessor = new IFProcessor();

    if (!rating.equals(""))
    {
      Hashtable ratingDetails = ifProcessor.getRatingDetails(rating);
      dynaForm.set("newRating", "");
      dynaForm.set("modRating", rating);
      dynaForm.set("ratingDescription", ratingDetails.get("Description"));
      dynaForm.set("ratingGivenBy", ratingDetails.get("Given By"));
    }
    else
    {
      dynaForm.set("newRating", "");
      dynaForm.set("modRating", "");
      dynaForm.set("ratingDescription", "");
      dynaForm.set("ratingGivenBy", "");
    }

    Log.log(4, "IFAction", "showRatingDetails", "Exited");

    return mapping.findForward("success");
  }

  public ActionForward showBankAccountMaster(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response)
    throws Exception
  {
    Log.log(4, "IFAction", "showBankAccountMaster", "Entered");

    DynaActionForm dynaForm = (DynaActionForm)form;
    dynaForm.initialize(mapping);
    HttpSession session = request.getSession(false);
    session.setAttribute("flag", "2");

    IFProcessor ifProcessor = new IFProcessor();
    ArrayList bankDetails = ifProcessor.getAllBanksWithAccountNumbers();

    ArrayList bankNames = new ArrayList(bankDetails.size());
    String bankName = "";
    for (int i = 0; i < bankDetails.size(); i++)
    {
      BankAccountDetail bankAccountDetail = (BankAccountDetail)bankDetails.get(i);
      if (bankAccountDetail.getAccountType().equals("S"))
      {
        bankName = String.valueOf(bankAccountDetail.getBankName()) + " ," + bankAccountDetail.getBankBranchName() + "(" + bankAccountDetail.getAccountNumber() + ")";

        bankNames.add(bankName);
      }
    }
    dynaForm.set("bankNames", bankNames);
    dynaForm.set("minBalance", new Double(0.0D));

    Log.log(4, "IFAction", "showBankAccountMaster", "Exited");

    return mapping.findForward("success");
  }

  public ActionForward showBankAccountList(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response)
    throws Exception
  {
    Log.log(4, "IFAction", "showBankAccountList", "Entered");

    DynaActionForm dynaForm = (DynaActionForm)form;
    String accType = (String)dynaForm.get("accountType");

    IFProcessor ifProcessor = new IFProcessor();
    ArrayList bankDetails = ifProcessor.getAllBanksWithAccountNumbers();

    ArrayList bankNames = new ArrayList(bankDetails.size());
    String bankName = "";
    for (int i = 0; i < bankDetails.size(); i++)
    {
      BankAccountDetail bankAccountDetail = (BankAccountDetail)bankDetails.get(i);
      if (bankAccountDetail.getAccountType().equals(accType))
      {
        bankName = String.valueOf(bankAccountDetail.getBankName()) + " ," + bankAccountDetail.getBankBranchName() + "(" + bankAccountDetail.getAccountNumber() + ")";

        bankNames.add(bankName);
      }
    }
    dynaForm.set("bankNames", bankNames);
    dynaForm.set("accountNumber", "");
    dynaForm.set("bankName", "");
    dynaForm.set("bankBranchName", "");
    dynaForm.set("minBalance", new Double(0.0D));
    HttpSession session = request.getSession(false);
    session.setAttribute("flag", "2");

    Log.log(4, "IFAction", "showBankAccountList", "Exited");

    return mapping.findForward("success");
  }

  public ActionForward showBankAccountDetails(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response)
    throws Exception
  {
    Log.log(4, "IFAction", "showBankAccountDetails", "Entered");

    DynaActionForm dynaForm = (DynaActionForm)form;
    String accType = (String)dynaForm.get("accountType");
    String accChosen = (String)dynaForm.get("accComb");
    String bankName = "";
    String branchName = "";
    String accNumber = "";
    double minBal = 0.0D;

    if ((accChosen != null) && (!accChosen.equals("")))
    {
      int index = accChosen.indexOf(",");
      bankName = accChosen.substring(0, index).trim();
      int index1 = accChosen.indexOf("(");
      branchName = accChosen.substring(index + 1, index1).trim();
      index = accChosen.indexOf(")");
      accNumber = accChosen.substring(index1 + 1, index).trim();
      IFProcessor ifProcessor = new IFProcessor();
      minBal = ifProcessor.getBankAccountDetails(bankName, branchName, accNumber);
    }

    BankAccountDetail accDetail = new BankAccountDetail();
    accDetail.setAccountNumber(accNumber);
    accDetail.setBankName(bankName);
    accDetail.setBankBranchName(branchName);
    accDetail.setMinBalance(minBal);

    BeanUtils.copyProperties(dynaForm, accDetail);
    dynaForm.set("accountType", accType);
    HttpSession session = request.getSession(false);
    session.setAttribute("flag", "3");

    Log.log(4, "IFAction", "showBankAccountDetails", "Exited");

    return mapping.findForward("success");
  }

  public ActionForward showUpdateCorpusMaster(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response)
    throws Exception
  {
    Log.log(4, "IFAction", "showUpdateCorpusMaster", "Entered");

    DynaActionForm dynaForm = (DynaActionForm)form;
    dynaForm.initialize(mapping);
    HttpSession session = request.getSession(false);
    session.setAttribute("flag", "U");

    java.util.Date date = new java.util.Date();
    Calendar calendar = Calendar.getInstance();
    calendar.setTime(date);
    int month = calendar.get(2);
    int day = calendar.get(5);
    month -= 1;
    day += 1;
    calendar.set(2, month);
    calendar.set(5, day);
    java.util.Date prevDate = calendar.getTime();

    Dates generalReport = new Dates();
    generalReport.setCorpusFromDate(prevDate);
    generalReport.setCorpusToDate(date);
    BeanUtils.copyProperties(dynaForm, generalReport);

    Log.log(4, "IFAction", "showUpdateCorpusMaster", "Exited");

    return mapping.findForward("success");
  }

  public ActionForward showInsertCorpusMaster(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response)
    throws Exception
  {
    Log.log(4, "IFAction", "showInsertCorpusMaster", "Entered");

    DynaActionForm dynaForm = (DynaActionForm)form;
    dynaForm.initialize(mapping);
    HttpSession session = request.getSession(false);
    session.setAttribute("flag", "I");

    Log.log(4, "IFAction", "showInsertCorpusMaster", "Exited");

    return mapping.findForward("success");
  }

  public ActionForward showCorpusList(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response)
    throws Exception
  {
    Log.log(4, "IFAction", "showCorpusList", "Entered");

    DynaActionForm dynaForm = (DynaActionForm)form;

    java.util.Date fromDate = (java.util.Date)dynaForm.get("corpusFromDate");
    java.util.Date toDate = (java.util.Date)dynaForm.get("corpusToDate");

    IFProcessor ifProcessor = new IFProcessor();
    ArrayList corpusList = ifProcessor.getCorpusList(fromDate, toDate);
    dynaForm.set("corpusList", corpusList);

    Log.log(4, "IFAction", "showCorpusList", "Exited");

    return mapping.findForward("displayList");
  }

  public ActionForward showCorpusDetail(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response)
    throws Exception
  {
    Log.log(4, "IFAction", "showCorpusDetail", "Entered");

    DynaActionForm dynaForm = (DynaActionForm)form;
    CorpusDetail corpus = new CorpusDetail();

    String corpusId = request.getParameter("id");
    if (corpusId == null)
    {
      corpusId = (String)dynaForm.get("corpusId");
    }

    IFProcessor ifProcessor = new IFProcessor();
    corpus = ifProcessor.getCorpusDetails(corpusId);

    BeanUtils.copyProperties(dynaForm, corpus);

    Log.log(4, "IFAction", "showCorpusDetail", "Exited");

    return mapping.findForward("display");
  }

  public ActionForward showHolidayMaster(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response)
    throws Exception
  {
    Log.log(4, "IFAction", "showHolidayMaster", "Entered");

    HttpSession session = request.getSession(false);
    session.setAttribute("modFlag", "0");

    DynaActionForm dynaForm = (DynaActionForm)form;

    IFProcessor ifProcessor = new IFProcessor();
    ArrayList holDates = ifProcessor.getHolidayDates();

    dynaForm.set("holidayDates", holDates);
    dynaForm.set("holidayDescription", "");
    dynaForm.set("modHolidayDate", "");
    dynaForm.set("holidayDate", "");
    dynaForm.set("newHolidayDate", "");

    Log.log(4, "IFAction", "showHolidayMaster", "Exited");

    return mapping.findForward("display");
  }

  public ActionForward showHolidayDetail(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response)
    throws Exception
  {
    Log.log(4, "IFAction", "showHolidayDetail", "Entered");

    HttpSession session = request.getSession(false);
    session.setAttribute("modFlag", "1");

    DynaActionForm dynaForm = (DynaActionForm)form;

    IFProcessor ifProcessor = new IFProcessor();
    String strDate = (String)dynaForm.get("holidayDate");
    java.util.Date holDate = new SimpleDateFormat("dd/MM/yyyy").parse(strDate, new ParsePosition(0));
    if ((holDate != null) && (!holDate.toString().equals("")))
    {
      String holDesc = ifProcessor.getHolidayDetail(holDate);
      dynaForm.set("holidayDescription", holDesc);
      dynaForm.set("modHolidayDate", strDate);
      dynaForm.set("holidayDate", strDate);
      dynaForm.set("newHolidayDate", "");
    }
    else
    {
      dynaForm.set("holidayDescription", "");
      dynaForm.set("modHolidayDate", "");
      dynaForm.set("holidayDate", "");
      dynaForm.set("newHolidayDate", "");
    }

    Log.log(4, "IFAction", "showHolidayDetail", "Exited");

    return mapping.findForward("display");
  }

  public ActionForward showEnterPaymentDetails(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response)
    throws Exception
  {
    Log.log(4, "IFAction", "showEnterPaymentDetails", "Entered");
    ArrayList bankDetails = new ArrayList();
    IFProcessor ifProcessor = new IFProcessor();
    DynaActionForm dynaForm = (DynaActionForm)form;
    dynaForm.initialize(mapping);
    bankDetails = ifProcessor.getAllBanksWithAccountNumbers();
    ArrayList bankNames = new ArrayList(bankDetails.size());
    String bankName = "";
    for (int i = 0; i < bankDetails.size(); i++)
    {
      BankAccountDetail bankAccountDetail = (BankAccountDetail)bankDetails.get(i);
      bankName = String.valueOf(bankAccountDetail.getBankName()) + " , " + bankAccountDetail.getBankBranchName() + "(" + bankAccountDetail.getAccountNumber() + ")";

      bankNames.add(bankName);
    }
    dynaForm.set("banks", bankNames);
    bankDetails = null;
    ifProcessor = null;
    Log.log(4, "IFAction", "showEnterpaymentDetails", "Exited");

    return mapping.findForward("success");
  }

  public ActionForward saveEnterPaymentDetails(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response)
    throws Exception
  {
    Log.log(4, "IFAction", "saveEnterPaymentDetails", "Entered");

    DynaActionForm dynaForm = (DynaActionForm)form;

    PaymentDetails paydetails = new PaymentDetails();
    ChequeDetails chequeDetails = null;

    String contextPath = request.getSession(false).getServletContext().getRealPath("");
    Log.log(5, "RPAction", "getPaymentsMade", "path " + contextPath);

    User user = getUserInformation(request);
    String userId = user.getUserId();

    BeanUtils.populate(paydetails, dynaForm.getMap());
    String ifChequed = (String)dynaForm.get("ifChequed");
    if (ifChequed.equals("Y"))
    {
      chequeDetails = new ChequeDetails();
      BeanUtils.populate(chequeDetails, dynaForm.getMap());
      String bankName = chequeDetails.getBankName();

      int i = bankName.indexOf(",");

      String newBank = bankName.substring(0, i);

      chequeDetails.setBankName(newBank);

      int j = bankName.indexOf("(");

      String newBranch = bankName.substring(i + 1, j);

      chequeDetails.setBranchName(newBranch);

      chequeDetails.setUserId(userId);
      paydetails.setUserId(userId);
      Log.log(4, "IFAction", "saveEnterPaymentDetails", "bank " + chequeDetails.getBankName());
      Log.log(4, "IFAction", "saveEnterPaymentDetails", "chequ no " + chequeDetails.getChequeNumber());
      Log.log(4, "IFAction", "saveEnterPaymentDetails", "date " + chequeDetails.getChequeDate().toString());
      Log.log(4, "IFAction", "saveEnterPaymentDetails", "amount " + chequeDetails.getChequeAmount());
      Log.log(4, "IFAction", "saveEnterPaymentDetails", "to " + chequeDetails.getChequeIssuedTo());
    }
    paydetails.setUserId(userId);

    IFProcessor ifProcessor = new IFProcessor();

    ifProcessor.saveEnterPaymentDetails(paydetails, chequeDetails, contextPath);

    String message = "Payment Details are entered Successfully";

    request.setAttribute("message", message);

    Log.log(4, "IFAction", "saveEnterPaymentDetails", "Exited");

    return mapping.findForward("success");
  }

  public ActionForward saveModifyPaymentDetails(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response)
    throws Exception
  {
    Log.log(4, "IFAction", "saveModifyPaymentDetails", "Entered");

    DynaActionForm dynaForm = (DynaActionForm)form;

    PaymentDetails paydetails = new PaymentDetails();
    User user = getUserInformation(request);
    String userId = user.getUserId();

    BeanUtils.populate(paydetails, dynaForm.getMap());
    paydetails.setUserId(userId);

    IFProcessor ifProcessor = new IFProcessor();

    ifProcessor.saveModifyPaymentDetails(paydetails);

    String message = "Payment Details are updated succesfully";

    request.setAttribute("message", message);

    Log.log(4, "IFAction", "saveModifyPaymentDetails", "Exited");

    return mapping.findForward("success");
  }

  public ActionForward showPaymentDetails(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response)
    throws Exception
  {
    Log.log(4, "IFAction", "showPaymentDetails", "Entered");
    DynaActionForm dynaForm = (DynaActionForm)form;
    dynaForm.initialize(mapping);
    Log.log(4, "IFAction", "showPaymentDetails", "Entered");

    return mapping.findForward("success");
  }

  public ActionForward showListOfPaymentDetails(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response)
    throws Exception
  {
    Log.log(4, "IFAction", "showListOfPaymentDetails", "Displayed");

    DynaActionForm dynaForm = (DynaActionForm)form;

    IFProcessor ifProcessor = new IFProcessor();

    PaymentDetails paydetails = new PaymentDetails();
    BeanUtils.populate(paydetails, dynaForm.getMap());

    ArrayList payments = ifProcessor.showListOfPaymentDetails(paydetails);
    dynaForm.set("payments", payments);

    if ((payments.size() == 0) || (payments == null))
    {
      throw new NoDataException("No Data is available for this value, Please Choose Any Other Value ");
    }

    payments = null;
    Log.log(4, "IFAction", "showListOfPaymentDetails", "Exited");

    return mapping.findForward("success");
  }

  public ActionForward getPaymentDetails(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response)
    throws Exception
  {
    Log.log(4, "IFAction", "getPaymentDetails", "Entered");
    SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
    DynaActionForm dynaForm = (DynaActionForm)form;
    PaymentDetails paydetails = new PaymentDetails();
    String payId = request.getParameter("Id");
    IFProcessor ifProcessor = new IFProcessor();
    paydetails = ifProcessor.getPaymentDetails(payId);
    BeanUtils.copyProperties(dynaForm, paydetails);

    Log.log(4, "IFAction", "getPaymentDetails", "Exited");

    return mapping.findForward("success");
  }

  public ActionForward chequeDetailsForPayment(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response)
    throws Exception
  {
    Log.log(4, "IFAction", "chequeDetailsForPayment", "Entered");
    ChequeDetails cheque = new ChequeDetails();
    IFProcessor ifProcessor = new IFProcessor();
    DynaActionForm dynaForm = (DynaActionForm)form;
    String chequeNumber = (String)dynaForm.get("number");
    cheque = ifProcessor.chequeDetailsForPayment(chequeNumber);
    ArrayList bankDetails = ifProcessor.getAllBanksWithAccountNumbers();
    ArrayList bankNames = new ArrayList(bankDetails.size());
    String bankName = "";
    for (int i = 0; i < bankDetails.size(); i++)
    {
      BankAccountDetail bankAccountDetail = (BankAccountDetail)bankDetails.get(i);
      bankName = String.valueOf(bankAccountDetail.getBankName()) + " ," + bankAccountDetail.getBankBranchName() + "(" + bankAccountDetail.getAccountNumber() + ")";

      bankNames.add(bankName);
    }
    dynaForm.set("banks", bankNames);

    dynaForm.set("bankName", cheque.getBankName());
    BeanUtils.copyProperties(dynaForm, cheque);

    Log.log(4, "IFAction", "chequeDetailsForPayment", "Exited");

    return mapping.findForward("success");
  }

  public ActionForward showInflowOutflowReportInput(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response)
    throws Exception
  {
    Log.log(4, "IFAction", "showInflowOutflowReportInput", "Entered");
    InvestmentForm dynaForm = (InvestmentForm)form;

    java.util.Date date = new java.util.Date();
    Calendar calendar = Calendar.getInstance();
    Dates dates = new Dates();
    dates.setValueDate(date);
    BeanUtils.copyProperties(dynaForm, dates);

    Log.log(4, "IFAction", "showInflowOutflowReportInput", "Exited");

    return mapping.findForward("display");
  }

  public ActionForward showInvstMaturingDetails(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response)
    throws Exception
  {
    Log.log(4, "IFAction", "showInvstMaturingDetails", "Entered");
    InvestmentForm ifForm = (InvestmentForm)form;
    java.util.Date date = ifForm.getValueDate();
    SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");

    if ((date != null) && (!date.toString().equals("")))
    {
      IFProcessor ifProcessor = new IFProcessor();
      Map reportDetails = ifProcessor.showInvstMaturingDetails(date);

      ifForm.setInvstMaturingDetails(reportDetails);
      ifForm.setValueDate(date);
    }

    Log.log(4, "IFAction", "showInvstMaturingDetails", "Exited");

    return mapping.findForward("display");
  }

  public ActionForward showInflowOutflowReport(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response)
    throws Exception
  {
    Log.log(4, "IFAction", "showInflowOutflowReport", "Entered");
    InvestmentForm ifForm = (InvestmentForm)form;
    java.util.Date date = ifForm.getValueDate();
    Map invMatDetails = ifForm.getInvstMaturingDetails();

    Set imSet = invMatDetails.keySet();
    Iterator imIterator = imSet.iterator();
    int notPresentCount = 0;
    boolean toAdd = false;
    String removeKey = "";
    while (imIterator.hasNext())
    {
      String key = (String)imIterator.next();
      InvestmentMaturityDetails imDetail = (InvestmentMaturityDetails)invMatDetails.get(key);
      if ((imDetail.getPliId() == 0) && ((imDetail.getInvName() == null) || (imDetail.getInvName().equals(""))) && ((imDetail.getMaturityAmt() == null) || (imDetail.getMaturityAmt().equals("")) || (Double.parseDouble(imDetail.getMaturityAmt()) == 0)) && ((imDetail.getOtherDesc() == null) || (imDetail.getOtherDesc().equals(""))))
      {
        toAdd = false;
        removeKey = key;
      }
      else
      {
        toAdd = true;
      }
    }

    if (!toAdd)
    {
      invMatDetails.remove(removeKey);
    }

    User user = getUserInformation(request);
    String userId = user.getUserId();

    IFProcessor ifProcessor = new IFProcessor();
    Map ioReport = ifProcessor.showInflowOutflowReport(date, invMatDetails, userId);

    ArrayList maturityDetails = (ArrayList)ioReport.get("MA");
    ArrayList fundTransfers = (ArrayList)ioReport.get("FT");
    ArrayList provisionDetails = (ArrayList)ioReport.get("PR");
    Map mainDetails = (Map)ioReport.get("DT");

    ifForm.setValueDate(date);
    ifForm.setPlanInvMainDetails(mainDetails);
    ifForm.setPlanInvMatDetails(maturityDetails);
    ifForm.setPlanInvFTDetails(fundTransfers);
    ifForm.setPlanInvProvisionDetails(provisionDetails);

    Log.log(4, "IFAction", "showInflowOutflowReport", "Exited");

    return mapping.findForward("display");
  }

  public ActionForward saveInflowOutflowReportInput(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response)
    throws Exception
  {
    Log.log(4, "IFAction", "saveInflowOutflowReportInput", "Entered");
    InvestmentForm ifForm = (InvestmentForm)form;
    java.util.Date date = ifForm.getValueDate();
    SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
    Map planInvProvisionDetails = ifForm.getProvisionRemarks();
    Map planInvMainDetails = ifForm.getPlanInvMainDetails();

    User user = getUserInformation(request);
    String userId = user.getUserId();

    IFProcessor ifProcessor = new IFProcessor();
    ifProcessor.saveInflowOutflowReport(date, planInvMainDetails, planInvProvisionDetails, userId);

    request.setAttribute("message", "Plan Investment Report Details saved successfully");

    Log.log(4, "IFAction", "saveInflowOutflowReportInput", "Exited");

    return mapping.findForward("success");
  }

  public ActionForward showInvDetails(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response)
    throws Exception
  {
    Log.log(4, "IFAction", "showInvDetails", "Exited");

    HttpSession session = request.getSession(false);
    session.setAttribute("invFlag", "1");

    DynaActionForm dynaForm = (DynaActionForm)form;
    String invRefNo = (String)dynaForm.get("investmentReferenceNumber");

    if (invRefNo.equals(""))
    {
      dynaForm.set("investeeName", "");
      dynaForm.set("principalAmount", "");
      dynaForm.set("costOfPurchase", "");
      dynaForm.set("noOfUnits", "");
      dynaForm.set("numberOfSecurities", "");
      dynaForm.set("noOfCommercialPapers", "");
    }
    else
    {
      IFProcessor ifProcessor = new IFProcessor();
      ArrayList details = ifProcessor.getInvDetails(invRefNo);
      String invName = (String)details.get(0);
      Double amt = (Double)details.get(1);
      int units = ((Integer)details.get(2)).intValue();
      dynaForm.set("investeeName", invName);
      dynaForm.set("principalAmount", "" + amt);
      dynaForm.set("costOfPurchase", "" + amt);
      dynaForm.set("noOfUnits", "" + units);
      dynaForm.set("numberOfSecurities", "" + units);
      dynaForm.set("noOfCommercialPapers", "" + units);
    }

    Log.log(4, "IFAction", "showInvDetails", "Exited");

    return mapping.findForward("success");
  }

  public ActionForward showInvFullfilmentRefNos(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response)
    throws Exception
  {
    Log.log(4, "IFAction", "showInvFullfilmentRefNos", "Entered");

    HttpSession session = request.getSession(false);
    session.setAttribute("fullfilmentFlag", "1");

    IFProcessor ifProcessor = new IFProcessor();

    DynaActionForm dynaForm = (DynaActionForm)form;

    getAllInvestees(dynaForm);
    getInstrumentTypes(dynaForm);
    getGenInstrumentTypes(dynaForm);
    getInstrumentFeatures(dynaForm);

    getInstrumentSchemes(dynaForm);
    getInvRefNosForFullfilment(dynaForm);

    Log.log(4, "IFAction", "showInvFullfilmentRefNos", "Exited");

    return mapping.findForward("success");
  }

  private void getInvRefNosForFullfilment(DynaActionForm dynaForm) throws DatabaseException
  {
    IFProcessor ifProcessor = new IFProcessor();
    String inst = (String)dynaForm.get("instrumentName");
    String inv = (String)dynaForm.get("investeeName");
    Log.log(5, "IFAction", "getInvestmentReferenceNumbers", "inv name  " + inv);
    ArrayList investmentRefNumbers = ifProcessor.getInvRefNosForFullfilment(inst, inv);

    dynaForm.set("investmentRefNumbers", investmentRefNumbers);
  }

  public ActionForward showTDSInvRefNos(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response)
    throws Exception
  {
    Log.log(4, "IFAction", "showTDSInvRefNos", "Entered");
    DynaActionForm dynaForm = (DynaActionForm)form;

    getAllInvestees(dynaForm);
    getInstrumentTypes(dynaForm);
    getInvRefNosForTDS(dynaForm);

    Log.log(4, "IFAction", "showTDSInvRefNos", "Exited");

    return mapping.findForward("success");
  }

  private void getInvRefNosForTDS(DynaActionForm dynaForm) throws DatabaseException
  {
    IFProcessor ifProcessor = new IFProcessor();
    String inst = (String)dynaForm.get("instrumentName");
    String inv = (String)dynaForm.get("investeeName");
    Log.log(5, "IFAction", "getInvestmentReferenceNumbers", "inv name  " + inv);
    ArrayList investmentRefNumbers = ifProcessor.getInvRefNosForTDS(inst, inv);

    dynaForm.set("investmentRefNumbers", investmentRefNumbers);
  }

  public ActionForward calMaturityDateAmt(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response)
    throws Exception
  {
    Log.log(4, "IFAction", "calMaturityDateAmt", "Entered");

    HttpSession session = request.getSession(false);
    session.setAttribute("invFlag", "2");

    IFProcessor ifProcessor = new IFProcessor();
    double prlAmt = 0.0D;
    int compFreq = 0;
    double intRate = 0.0D;
    double tenure = 0.0D;
    double amount = 0.0D;
    double intAmt = 0.0D;
    double matAmt = 0.0D;
    int balDays = 0;
    String tenureType = "";
    java.util.Date invDate = null;
    java.util.Date matDate = null;
    String instName = "";

    DynaActionForm dynaForm = (DynaActionForm)form;

    InvestmentDetails invDetail = new InvestmentDetails();

    BeanUtils.populate(invDetail, dynaForm.getMap());

    if (!((String)dynaForm.get("principalAmount")).equals(""))
    {
      prlAmt = Double.parseDouble((String)dynaForm.get("principalAmount"));
    }
    else
    {
      prlAmt = Double.parseDouble((String)dynaForm.get("faceValue"));
    }

    if (!((String)dynaForm.get("compoundingFrequency")).equals(""))
    {
      compFreq = Integer.parseInt((String)dynaForm.get("compoundingFrequency"));
    }

    if ((((String)dynaForm.get("interestRate")).equals("")) || (Double.parseDouble((String)dynaForm.get("interestRate")) == 0))
    {
      intRate = Double.parseDouble((String)dynaForm.get("couponRate"));
    }
    else
    {
      intRate = Double.parseDouble((String)dynaForm.get("interestRate"));
    }

    tenure = Double.parseDouble((String)dynaForm.get("tenure"));
    tenureType = (String)dynaForm.get("tenureType");
    if (((String)dynaForm.get("instrumentName")).equalsIgnoreCase("Commercial Papers"))
    {
      tenureType = "M";
    }

    Log.log(5, "IFAction", "calMaturityDateAmt", "prl amt " + prlAmt);
    Log.log(5, "IFAction", "calMaturityDateAmt", "comp Freq " + compFreq);
    Log.log(5, "IFAction", "calMaturityDateAmt", "int rate " + intRate);
    Log.log(5, "IFAction", "calMaturityDateAmt", "tenure " + tenure);
    Log.log(5, "IFAction", "calMaturityDateAmt", "tenure type " + tenureType);

    if (tenureType.equalsIgnoreCase("M"))
    {
      tenure /= 12;
    }

    Log.log(5, "IFAction", "calMaturityDateAmt", "tenure " + tenure);

    if (compFreq == 4)
    {
      intRate /= 4;
      tenure *= 4;
    }
    else if (compFreq == 2)
    {
      intRate /= 2;
      tenure *= 2;
    }
    else if (compFreq == 12)
    {
      intRate /= 12;
      tenure *= 12;
    }

    Log.log(5, "IFAction", "calMaturityDateAmt", "tenure " + tenure);

    Log.log(5, "IFAction", "calMaturityDateAmt", "bal days " + balDays);
    Log.log(5, "IFAction", "calMaturityDateAmt", "rate " + intRate);
    Log.log(5, "IFAction", "calMaturityDateAmt", "tenure " + tenure);

    if (tenureType.equalsIgnoreCase("D"))
    {
      matAmt = prlAmt + prlAmt * (tenure / 365) * intRate / 100;
    }
    else
    {
      Log.log(5, "IFAction", "calMaturityDateAmt", "1 " + (1 + intRate / 100));
      Log.log(5, "IFAction", "calMaturityDateAmt", "2 " + Math.pow(1 + intRate / 100, tenure));

      amount = prlAmt * Math.pow(1 + intRate / 100, tenure);
      Log.log(5, "IFAction", "calMaturityDateAmt", "3 " + amount);

      matAmt = amount + intAmt;
    }

    Log.log(5, "IFAction", "calMaturityDateAmt", "mat amt" + matAmt);
    DecimalFormat df = new DecimalFormat("###############.##");
    df.setDecimalSeparatorAlwaysShown(false);

    Log.log(5, "IFAction", "calMaturityDateAmt", "mat amt" + df.format(matAmt));
    invDetail.setMaturityAmount(matAmt);

    int iTenure = Integer.parseInt((String)dynaForm.get("tenure"));
    if ((dynaForm.get("dateOfInvestment") != null) && (!((java.util.Date)dynaForm.get("dateOfInvestment")).toString().equals("")))
    {
      invDate = (java.util.Date)dynaForm.get("dateOfInvestment");
    }
    else
    {
      invDate = (java.util.Date)dynaForm.get("dateOfDeposit");
    }

    Calendar calendar = Calendar.getInstance();
    calendar.setTime(invDate);
    if (tenureType.equalsIgnoreCase("D"))
    {
      calendar.add(5, iTenure);
    }
    else if (tenureType.equalsIgnoreCase("M"))
    {
      calendar.add(2, iTenure);
    }
    else if (tenureType.equalsIgnoreCase("Y"))
    {
      calendar.add(1, iTenure);
    }
    matDate = calendar.getTime();

    int dayOfWeek = calendar.get(7);
    Log.log(5, "IFAction", "calMaturityDateAmt", "day " + dayOfWeek);

    matDate = calendar.getTime();
    dayOfWeek = calendar.get(7);
    Log.log(5, "IFAction", "calMaturityDateAmt", "day " + dayOfWeek);

    Log.log(5, "IFAction", "calMaturityDateAmt", "date " + matDate);
    ArrayList holidays = ifProcessor.getAllHolidays();
    SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");

    Log.log(5, "IFAction", "calMaturityDateAmt", "contains " + holidays.contains(matDate));

    matDate = calendar.getTime();
    Log.log(5, "IFAction", "calMaturityDateAmt", "mat date" + matDate);
    invDetail.setMaturityDate(matDate);

    instName = invDetail.getInstrumentName();
    double ytm = 0.0D;
    if (!instName.equalsIgnoreCase("Fixed Deposit"))
    {
      double parValue = 0.0D;
      if (invDetail.getParValue() > 0)
      {
        parValue = invDetail.getParValue();
      }
      else
      {
        parValue = invDetail.getFaceValue();
      }
      double purchasePrice = invDetail.getCostOfPurchase();

      if ((((String)dynaForm.get("interestRate")).equals("")) || (Double.parseDouble((String)dynaForm.get("interestRate")) == 0))
      {
        intRate = Double.parseDouble((String)dynaForm.get("couponRate"));
      }
      else
      {
        intRate = Double.parseDouble((String)dynaForm.get("interestRate"));
      }

      tenure = invDetail.getTenure();
      tenureType = invDetail.getTenureType();
      if (invDetail.getInstrumentName().equalsIgnoreCase("Commercial Papers"))
      {
        tenureType = "M";
      }
      if (tenureType.equalsIgnoreCase("D"))
      {
        tenure /= 365;
        tenure = Double.parseDouble(df.format(tenure));
      }
      else if (tenureType.equalsIgnoreCase("M"))
      {
        tenure /= 12;
      }
      Log.log(5, "IFAction", "calMaturityDateAmt", "ytm calc parvalue" + parValue);
      Log.log(5, "IFAction", "calMaturityDateAmt", "ytm calc purchaseprice" + purchasePrice);
      Log.log(5, "IFAction", "calMaturityDateAmt", "ytm calc intrate" + intRate);
      Log.log(5, "IFAction", "calMaturityDateAmt", "ytm calc tenure" + tenure);
      ytm = calculateYTM(parValue, purchasePrice, intRate, tenure);
      if (new Double(ytm).isNaN())
      {
        ytm = 0.0D;
      }
      Log.log(5, "IFAction", "calMaturityDateAmt", "ytm value" + df.format(ytm));
    }

    invDetail.setYtmValue(ytm);

    BeanUtils.copyProperties(dynaForm, invDetail);
    dynaForm.set("maturityAmount", df.format(matAmt));

    Log.log(4, "IFAction", "calMaturityDateAmt", "Exited");

    return mapping.findForward("display");
  }

  public ActionForward showInvFullfilmentDetails(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response)
    throws Exception
  {
    Log.log(4, "IFAction", "showInvFullfilmentDetails", "Entered");

    HttpSession session = request.getSession(false);
    session.setAttribute("fullfilmentFlag", "2");

    DynaActionForm dynaForm = (DynaActionForm)form;

    getAllInvestees(dynaForm);
    getInstrumentTypes(dynaForm);
    getGenInstrumentTypes(dynaForm);
    getInstrumentFeatures(dynaForm);

    getInstrumentSchemes(dynaForm);
    getInvRefNosForFullfilment(dynaForm);

    InvestmentFulfillmentDetail investmentFulfillmentDetail = new InvestmentFulfillmentDetail();
    BeanUtils.populate(investmentFulfillmentDetail, dynaForm.getMap());
    Log.log(4, "IFAction", "showInvFullfilmentDetails", "ref no " + dynaForm.get("investmentRefNumber"));
    IFProcessor ifProcessor = new IFProcessor();
    investmentFulfillmentDetail = ifProcessor.getInvFullfilmentDetails(investmentFulfillmentDetail);
    session.setAttribute("updateFlag", "0");
    if (investmentFulfillmentDetail != null)
    {
      session.setAttribute("updateFlag", "1");
      BeanUtils.copyProperties(dynaForm, investmentFulfillmentDetail);
    }
    else
    {
      dynaForm.set("instrumentType", "");
      dynaForm.set("instrumentNumber", "");
      dynaForm.set("instrumentDate", null);
      dynaForm.set("instrumentAmount", "");
      dynaForm.set("drawnBank", "");
      dynaForm.set("drawnBranch", "");
      dynaForm.set("payableAt", "");
    }

    Log.log(4, "IFAction", "showInvFullfilmentDetails", "ref no " + dynaForm.get("investmentRefNumber"));

    Log.log(4, "IFAction", "showInvFullfilmentDetails", "Exited");

    return mapping.findForward("success");
  }

  public ActionForward showBuyOrSellInvRefNos(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response)
    throws Exception
  {
    Log.log(4, "IFAction", "showBuyOrSellInvRefNos", "Entered");

    HttpSession session = request.getSession(false);
    session.setAttribute("flag", "1");

    DynaActionForm dynaForm = (DynaActionForm)form;

    getAllInvestees(dynaForm);
    getInstrumentTypes(dynaForm);

    ArrayList investmentRefNumbers = new ArrayList();
    String inst = (String)dynaForm.get("instrumentName");
    String inv = (String)dynaForm.get("investeeName");
    if ((((String)dynaForm.get("isBuyOrSellRequest")).equalsIgnoreCase("S")) && (!inst.equals("")) && (!inv.equals("")))
    {
      IFProcessor ifProcessor = new IFProcessor();
      Log.log(5, "IFAction", "getInvestmentReferenceNumbers", "inv name  " + inv);
      investmentRefNumbers = ifProcessor.getInvRefNosForBuySell(inst, inv);
    }
    else
    {
      dynaForm.set("noOfUnits", "");
      dynaForm.set("worthOfUnits", "");
    }

    dynaForm.set("investmentRefNumbers", investmentRefNumbers);

    Log.log(4, "IFAction", "showBuyOrSellInvRefNos", "Exited");

    return mapping.findForward("success");
  }

  public ActionForward statementReportInput(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response)
    throws Exception
  {
    Log.log(4, "IFAction", "statementReportInput", "Entered");
    DynaActionForm dynaForm = (DynaActionForm)form;
    java.util.Date date = new java.util.Date();
    Calendar calendar = Calendar.getInstance();
    Dates dates = new Dates();
    dates.setDateOfTheDocument(date);
    BeanUtils.copyProperties(dynaForm, dates);

    Log.log(4, "IFAction", "statementReportInput", "Exited");

    return mapping.findForward("success");
  }

  public ActionForward showCgpanDetails(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response)
    throws Exception
  {
    Log.log(4, "IFAction", "showCgpanDetails", "Entered");

    GMProcessor gmProcessor = new GMProcessor();
    CgpanDetail cgpanDetail = null;

    String cgpan = request.getParameter("cgpanDetail");
    Log.log(5, "GMAction", "showCgpanDetailsLink", "cgpan" + cgpan);
    cgpanDetail = gmProcessor.getCgpanDetails(cgpan);
    DynaActionForm dynaForm = (DynaActionForm)form;
    dynaForm.set("cgpanDtl", cgpanDetail);

    return mapping.findForward("success");
  }

  public ActionForward investmentROI(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response)
    throws Exception
  {
    Log.log(4, "IFAction", "investmentROI", "Entered");

    DynaActionForm dynaForm = (DynaActionForm)form;
    dynaForm.initialize(mapping);
    CustomisedDate customDate = new CustomisedDate();
    customDate.setDate(new java.util.Date());
    dynaForm.set("referenceDate", customDate);
    Log.log(4, "IFAction", "investmentROI", "Exited");

    return mapping.findForward("success");
  }

  public ActionForward getInvestmentROI(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response)
    throws Exception
  {
    Log.log(4, "IFAction", "getInvestmentROI", "Entered");

    DynaActionForm dynaForm = (DynaActionForm)form;
    IFProcessor ifProcessor = new IFProcessor();
    java.util.Date referenceDate = (java.util.Date)dynaForm.get("referenceDate");
    ArrayList rois = ifProcessor.getROI(referenceDate);

    dynaForm.set("rateOfInterests", rois);

    Log.log(4, "IFAction", "getInvestmentROI", "Exited");

    return mapping.findForward("success");
  }

  public ActionForward showInstCategoryMaster(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response)
    throws Exception
  {
    DynaActionForm dynaForm = (DynaActionForm)form;
    dynaForm.initialize(mapping);

    HttpSession session = request.getSession(false);

    IFProcessor ifProcessor = new IFProcessor();
    getInstrumentCategories(dynaForm);

    session.setAttribute("modFlag", "0");

    return mapping.findForward("success");
  }

  public ActionForward showModInstCategoryMaster(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response)
    throws Exception
  {
    DynaActionForm dynaForm = (DynaActionForm)form;

    IFProcessor ifProcessor = new IFProcessor();
    InstrumentCategory instCategory = new InstrumentCategory();

    HttpSession session = request.getSession(false);
    session.setAttribute("modFlag", "1");

    String instCatName = (String)dynaForm.get("instrumentCategory");

    if ((instCatName != null) && (!instCatName.equals("")))
    {
      instCategory = ifProcessor.getInstCategoryDetails(instCatName);

      dynaForm.set("modInstrumentCat", instCategory.getIctName());
      dynaForm.set("ictDesc", instCategory.getIctDesc());
      dynaForm.set("newInstrumentCat", "");
    }
    else
    {
      dynaForm.set("modInstrumentCat", "");
      dynaForm.set("ictDesc", "");
      dynaForm.set("newInstrumentCat", "");
    }

    return mapping.findForward("success");
  }

  private void getInstrumentCategories(DynaActionForm form) throws DatabaseException
  {
    ArrayList investeeCats = null;
    IFProcessor ifProcessor = new IFProcessor();
    investeeCats = ifProcessor.getInstrumentCategories();
    form.set("instrumentCategories", investeeCats);
  }

  public ActionForward saveInstrumentCategory(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response)
    throws Exception
  {
    Log.log(4, "IFAction", "saveInstrumentCategory", "Entered");
    DynaActionForm dynaForm = (DynaActionForm)form;
    User user = getUserInformation(request);
    String instrumentCat = (String)dynaForm.get("instrumentCategory");
    String newInstrumentCat = (String)dynaForm.get("newInstrumentCat");
    String modInstrumentCat = (String)dynaForm.get("modInstrumentCat");
    String instCatDesc = (String)dynaForm.get("ictDesc");
    InstrumentCategory instCategory = new InstrumentCategory();

    instCategory.setIctName(instrumentCat);
    instCategory.setIctNewName(newInstrumentCat);
    instCategory.setIctModName(modInstrumentCat);
    instCategory.setIctDesc(instCatDesc);
    IFProcessor ifProcessor = new IFProcessor();

    ifProcessor.saveInstrumentCategory(instCategory, user.getUserId());

    String message = "Instrument Category details saved Successfully";

    request.setAttribute("message", message);

    Log.log(4, "IFAction", "saveInstrumentCategory", "Exited");

    return mapping.findForward("success");
  }

  public ActionForward showModTDSFilter(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response)
    throws Exception
  {
    Log.log(4, "IFAction", "showModTDSFilter", "Entered");

    DynaActionForm dynaForm = (DynaActionForm)form;
    dynaForm.initialize(mapping);
    HttpSession session = request.getSession(false);
    session.setAttribute("flag", "U");

    java.util.Date date = new java.util.Date();
    Calendar calendar = Calendar.getInstance();
    calendar.setTime(date);
    int month = calendar.get(2);
    int day = calendar.get(5);
    month -= 1;
    day += 1;
    calendar.set(2, month);
    calendar.set(5, day);
    java.util.Date prevDate = calendar.getTime();

    Dates generalReport = new Dates();
    generalReport.setTdsStartDate(prevDate);
    generalReport.setTdsEndDate(date);
    BeanUtils.copyProperties(dynaForm, generalReport);

    Log.log(4, "IFAction", "showModTDSFilter", "Exited");

    return mapping.findForward("success");
  }

  public ActionForward showTDSList(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response)
    throws Exception
  {
    Log.log(4, "IFAction", "showTDSList", "Entered");

    DynaActionForm dynaForm = (DynaActionForm)form;

    java.util.Date fromDate = (java.util.Date)dynaForm.get("tdsStartDate");
    java.util.Date toDate = (java.util.Date)dynaForm.get("tdsEndDate");

    IFProcessor ifProcessor = new IFProcessor();
    ArrayList tdsList = ifProcessor.getTDSList(fromDate, toDate);
    if (tdsList.isEmpty())
    {
      throw new MessageException("No TDS Detail available for the given Dates.");
    }
    dynaForm.set("tdsList", tdsList);

    Log.log(4, "IFAction", "showTDSList", "Exited");

    return mapping.findForward("displayList");
  }

  public ActionForward showUpdateTDSDetail(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response)
    throws Exception
  {
    Log.log(4, "IFAction", "showUpdateTDSDetail", "Entered");

    DynaActionForm dynaForm = (DynaActionForm)form;
    TDSDetail tdsDetail = new TDSDetail();

    String tdsId = request.getParameter("id");

    IFProcessor ifProcessor = new IFProcessor();
    tdsDetail = ifProcessor.getTDSDetails(tdsId);

    BeanUtils.copyProperties(dynaForm, tdsDetail);

    Log.log(4, "IFAction", "showUpdateTDSDetail", "tds id " + dynaForm.get("tdsID"));
    Log.log(4, "IFAction", "showUpdateTDSDetail", "tds id " + tdsDetail.getTdsID());

    Log.log(4, "IFAction", "showUpdateTDSDetail", "Exited");

    return mapping.findForward("displayTDS");
  }

  public ActionForward showMiscReceiptsFilter(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response)
    throws Exception
  {
    Log.log(4, "IFAction", "showMiscReceiptsFilter", "Entered");

    InvestmentForm ifForm = (InvestmentForm)form;

    java.util.Date date = new java.util.Date();
    Calendar calendar = Calendar.getInstance();
    calendar.setTime(date);

    Dates generalReport = new Dates();
    generalReport.setMiscReceiptsDate(date);
    BeanUtils.copyProperties(ifForm, generalReport);

    Log.log(4, "IFAction", "showMiscReceiptsFilter", "Exited");

    return mapping.findForward("success");
  }

  public ActionForward showMiscReceipts(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response)
    throws Exception
  {
    Log.log(4, "IFAction", "showMiscReceipts", "Entered");
    InvestmentForm ifForm = (InvestmentForm)form;
    java.util.Date miscDate = ifForm.getMiscReceiptsDate();

    ifForm.resetWhenRequired(mapping, request);
    IFProcessor ifProcessor = new IFProcessor();
    Map receipts = ifProcessor.getMiscReceiptsForDate(miscDate);
    Log.log(5, "IFAction", "showMiscReceipts", "size " + receipts.size());
    ifForm.setMiscReceipts(receipts);
    if (receipts.size() == 0)
    {
      request.setAttribute("IsRequired", Boolean.valueOf(true));
    }

    Log.log(4, "IFAction", "showMiscReceipts", "Exited");

    return mapping.findForward("success");
  }

  public ActionForward addMoreMiscReceipts(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response)
    throws Exception
  {
    Log.log(4, "IFAction", "addMoreMiscReceipts", "Entered");

    InvestmentForm ifForm = (InvestmentForm)form;

    Map miscReceiptsDetails = ifForm.getMiscReceipts();

    Set miscReceiptsDetailsSet = miscReceiptsDetails.keySet();

    Iterator miscReceiptsDetailsIterator = miscReceiptsDetailsSet.iterator();
    String count = null;
    int counter = 0;
    boolean toAdd = false;
    String removeKey = "";

    while (miscReceiptsDetailsIterator.hasNext())
    {
      String key = (String)miscReceiptsDetailsIterator.next();

      Log.log(5, "IFAction", "addMoreMiscReceipts", " key " + key);

      count = key.substring(key.indexOf("-") + 1, key.length());

      Log.log(5, "IFAction", "addMoreMiscReceipts", " count " + count);

      MiscReceipts miscReceipts = (MiscReceipts)miscReceiptsDetails.get(key);
      if ((miscReceipts.getId() == 0) && ((miscReceipts.getSourceOfFund() == null) || (miscReceipts.getSourceOfFund().equals(""))) && ((miscReceipts.getInstrumentNo() == null) || (miscReceipts.getInstrumentNo().equals(""))) && ((miscReceipts.getInstrumentDate() == null) || (miscReceipts.getInstrumentDate().toString().equals(""))) && ((miscReceipts.getDateOfReceipt() == null) || (miscReceipts.getDateOfReceipt().toString().equals(""))))
      {
        toAdd = false;
        removeKey = key;
      }
      else
      {
        toAdd = true;
      }
    }

    Log.log(5, "IFAction", "addMoreMiscReceipts", " counter " + counter);

    if (toAdd)
    {
      request.setAttribute("IsRequired", Boolean.valueOf(true));
    }
    else
    {
      miscReceiptsDetails.remove(removeKey);
      request.setAttribute("IsRequired", Boolean.valueOf(false));
    }

    Log.log(4, "IFAction", "addMoreMiscReceipts", "Exited");

    return mapping.findForward("success");
  }

  public ActionForward insertMiscReceipts(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response)
    throws Exception
  {
    Log.log(4, "IFAction", "insertMoreMiscReceipts", "Entered");

    InvestmentForm ifForm = (InvestmentForm)form;
    java.util.Date date = ifForm.getMiscReceiptsDate();

    Map receipts = ifForm.getMiscReceipts();
    Set miscReceiptsDetailsSet = receipts.keySet();

    Iterator miscReceiptsDetailsIterator = miscReceiptsDetailsSet.iterator();
    String count = null;
    int counter = 0;
    boolean toAdd = false;
    String removeKey = "";

    User user = getUserInformation(request);
    String userId = user.getUserId();

    while (miscReceiptsDetailsIterator.hasNext())
    {
      String key = (String)miscReceiptsDetailsIterator.next();

      Log.log(5, "IFAction", "addMoreMiscReceipts", " key " + key);

      count = key.substring(key.indexOf("-") + 1, key.length());

      Log.log(5, "IFAction", "addMoreMiscReceipts", " count " + count);

      MiscReceipts miscReceipts = (MiscReceipts)receipts.get(key);
      if ((miscReceipts.getId() == 0) && ((miscReceipts.getSourceOfFund() == null) || (miscReceipts.getSourceOfFund().equals(""))) && ((miscReceipts.getInstrumentNo() == null) || (miscReceipts.getInstrumentNo().equals(""))) && ((miscReceipts.getInstrumentDate() == null) || (miscReceipts.getInstrumentDate().toString().equals(""))) && ((miscReceipts.getDateOfReceipt() == null) || (miscReceipts.getDateOfReceipt().toString().equals(""))))
      {
        toAdd = false;
        removeKey = key;
      }
      else
      {
        toAdd = true;
      }
    }

    Log.log(5, "IFAction", "addMoreMiscReceipts", " counter " + counter);

    if (!toAdd)
    {
      receipts.remove(removeKey);
    }

    IFProcessor ifProcessor = new IFProcessor();
    ifProcessor.insertMiscReceipts(date, receipts, userId);

    String message = "Miscellaneous Receipts saved successfully";
    request.setAttribute("message", message);

    Log.log(4, "IFAction", "insertMoreMiscReceipts", "Exited");

    return mapping.findForward("success");
  }

  public ActionForward showFundTransferFilter(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response)
    throws Exception
  {
    Log.log(4, "IFAction", "showFundTransferFilter", "Entered");

    InvestmentForm ifForm = (InvestmentForm)form;

    java.util.Date date = new java.util.Date();
    Calendar calendar = Calendar.getInstance();
    calendar.setTime(date);

    Dates generalReport = new Dates();
    generalReport.setStatementDate(date);
    BeanUtils.copyProperties(ifForm, generalReport);

    Log.log(4, "IFAction", "showFundTransferFilter", "Exited");

    return mapping.findForward("success");
  }

  public ActionForward showFundTransfer(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response)
    throws Exception
  {
    Log.log(4, "IFAction", "showFundTransfer", "Entered");

    InvestmentForm ifForm = (InvestmentForm)form;
    ifForm.resetWhenRequired(mapping, request);

    java.util.Date date = ifForm.getStatementDate();

    IFProcessor ifProcessor = new IFProcessor();
    Map fundTransfers = ifProcessor.getFundTransfersForDate(date);
    Log.log(5, "IFAction", "showFundTransfer", "size " + fundTransfers.size());
    ifForm.setFundTransfers(fundTransfers);

    Log.log(4, "IFAction", "showFundTransfer", "Exited");

    return mapping.findForward("success");
  }

  public ActionForward updateFundTransfer(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response)
    throws Exception
  {
    Log.log(4, "IFAction", "updateFundTransfer", "Entered");

    InvestmentForm ifForm = (InvestmentForm)form;
    java.util.Date date = ifForm.getStatementDate();

    Map ftDetails = ifForm.getFundTransfers();
    Map addFtDetails = new TreeMap();
    Set ftSet = ftDetails.keySet();

    Iterator ftIterator = ftSet.iterator();
    String count = null;
    int counter = 0;
    boolean toAdd = false;
    ArrayList removeKey = new ArrayList();

    User user = getUserInformation(request);
    String userId = user.getUserId();

    while (ftIterator.hasNext())
    {
      String key = (String)ftIterator.next();

      Log.log(5, "IFAction", "updateFundTransfer", " key " + key);

      count = key.substring(key.indexOf("-") + 1, key.length());

      Log.log(5, "IFAction", "updateFundTransfer", " count " + count);

      FundTransferDetail fundTransfer = (FundTransferDetail)ftDetails.get(key);
      if ((fundTransfer.getId() == 0) && ((fundTransfer.getClosingBalanceDate() == null) || (fundTransfer.getClosingBalanceDate().equals(""))) && ((fundTransfer.getBalanceAsPerStmt() == null) || (fundTransfer.getBalanceAsPerStmt().equals(""))) && ((fundTransfer.getUnclearedBalance() == null) || (fundTransfer.getUnclearedBalance().equals(""))) && ((fundTransfer.getAmtCANotReflected() == null) || (fundTransfer.getAmtCANotReflected().equals(""))))
      {
        Log.log(5, "IFAction", "updateFundTransfer", " remove " + key);
        toAdd = false;
        removeKey.add(key);
      }
      else
      {
        addFtDetails.put(key, fundTransfer);
      }
    }

    Log.log(5, "IFAction", "updateFundTransfer", " counter " + counter);

    Log.log(5, "IFAction", "updateFundTransfer", " counter " + addFtDetails.size());

    IFProcessor ifProcessor = new IFProcessor();
    ifProcessor.updateFundTransfers(date, addFtDetails, userId);

    String message = "Fund Transfers saved successfully";
    request.setAttribute("message", message);

    Log.log(4, "IFAction", "updateFundTransfer", "Exited");

    return mapping.findForward("success");
  }

  public ActionForward showClaimProjection(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response)
    throws Exception
  {
    DynaActionForm dynaForm = (DynaActionForm)form;

    dynaForm.initialize(mapping);

    return mapping.findForward("success");
  }

  public ActionForward showClaimProjectionDetails(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response)
    throws Exception
  {
    Log.log(4, "IFAction", "showClaimProjectionDetails", "Entered");

    IFProcessor ifProcessor = new IFProcessor();

    DynaActionForm dynaForm = (DynaActionForm)form;

    String forward = "";
    java.util.Date filterDate = (java.util.Date)dynaForm.get("filterDate");

    if (dynaForm.get("npaAccounts").equals("N"))
    {
      ArrayList projectionMap = ifProcessor.getApplicationsForProjection(filterDate);

      dynaForm.set("nonNPAAccountsMap", projectionMap.get(0));

      Map map = (Map)projectionMap.get(0);
      Set set = map.keySet();
      Iterator iterator = set.iterator();
      while (iterator.hasNext())
      {
        String key = (String)iterator.next();
        Log.log(4, "IFAction", "showClaimProjectionDetails", "key :" + key);
        Log.log(4, "IFAction", "showClaimProjectionDetails", "valu :" + map.get(key));
      }

      dynaForm.set("nonNPAAccountsArrayList", projectionMap.get(1));

      forward = "nonNPAPage";
    }
    else if (dynaForm.get("npaAccounts").equals("A"))
    {
      ArrayList npaProjectionMap = ifProcessor.getApplicationsForNPAAccounts(filterDate);

      dynaForm.set("npaAccountsMap", npaProjectionMap.get(0));
      dynaForm.set("npaAccountsArrayList", npaProjectionMap.get(1));

      for (int i = 0; i < ((ArrayList)npaProjectionMap.get(1)).size(); i++)
      {
        String year = (String)((ArrayList)npaProjectionMap.get(1)).get(i);
        Log.log(4, "IFAction", "showClaimProjectionDetails", "year :" + year);
      }

      forward = "nonNPAPage";
    }
    else if (dynaForm.get("npaAccounts").equals("B"))
    {
      ArrayList projectionMap = ifProcessor.getApplicationsForProjection(filterDate);

      dynaForm.set("nonNPAAccountsMap", projectionMap.get(0));
      dynaForm.set("nonNPAAccountsArrayList", projectionMap.get(1));

      ArrayList npaProjectionMap = ifProcessor.getApplicationsForNPAAccounts(filterDate);

      dynaForm.set("npaAccountsMap", npaProjectionMap.get(0));
      dynaForm.set("npaAccountsArrayList", npaProjectionMap.get(1));

      for (int i = 0; i < ((ArrayList)npaProjectionMap.get(1)).size(); i++)
      {
        String year = (String)((ArrayList)npaProjectionMap.get(1)).get(i);
        Log.log(4, "IFAction", "showClaimProjectionDetails", "year :" + year);
      }
      forward = "nonNPAPage";
    }

    Log.log(4, "IFAction", "showClaimProjectionDetails", "Exited");

    return mapping.findForward(forward);
  }

  private String getMonth(int calendarMonth)
  {
    String month = "";
    if (calendarMonth == 0)
    {
      month = "JAN";
    }
    else if (calendarMonth == 1)
    {
      month = "FEB";
    }
    else if (calendarMonth == 2)
    {
      month = "MAR";
    }
    else if (calendarMonth == 3)
    {
      month = "APR";
    }
    else if (calendarMonth == 4)
    {
      month = "MAY";
    }
    else if (calendarMonth == 5)
    {
      month = "JUN";
    }
    else if (calendarMonth == 6)
    {
      month = "JUL";
    }
    else if (calendarMonth == 7)
    {
      month = "AUG";
    }
    else if (calendarMonth == 8)
    {
      month = "SEP";
    }
    else if (calendarMonth == 9)
    {
      month = "OCT";
    }
    else if (calendarMonth == 10)
    {
      month = "NOV";
    }
    else if (calendarMonth == 11)
    {
      month = "DEC";
    }

    return month;
  }

  public ActionForward showVoucherDetails(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response)
    throws Exception
  {
    Log.log(4, "IFAction", "showVoucherDetails", "Entered");
    DynaActionForm ifForm = (DynaActionForm)form;

    ifForm.initialize(mapping);

    return mapping.findForward("success");
  }

  public ActionForward generateVoucherForInv(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response)
    throws Exception
  {
    Log.log(4, "IFAction", "generateVoucherForInv", "Entered");
    DynaActionForm ifForm = (DynaActionForm)form;

    IFProcessor ifProcessor = new IFProcessor();

    SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");

    User user = getUserInformation(request);
    String contextPath = request.getSession(false).getServletContext().getRealPath("");

    java.util.Date fromDate = (java.util.Date)ifForm.get("voucherFromDate");
    Log.log(4, "IFAction", "showVoucherDetails", "fromDate :" + fromDate);

    java.util.Date toDate = (java.util.Date)ifForm.get("voucherToDate");

    if ((toDate == null) || (toDate.toString().equals("")))
    {
      Log.log(4, "IFAction", "showVoucherDetails", "before toDate :" + toDate);
      toDate = new java.util.Date();

      Log.log(4, "IFAction", "showVoucherDetails", "after toDate :" + toDate);
    }
    Log.log(4, "IFAction", "showVoucherDetails", "toDate :" + toDate);

    ArrayList invVoucherDetails = ifProcessor.getMaturedVoucherDetails(fromDate, toDate);

    if ((invVoucherDetails == null) || (invVoucherDetails.size() == 0))
    {
      throw new MessageException("There are no Instruments available for Voucher Generation");
    }

    Log.log(4, "IFAction", "showVoucherDetails", "cinvVoucherDetails :" + invVoucherDetails.size());

    ifProcessor.insertVoucherInvDetails(invVoucherDetails, fromDate, toDate, contextPath, user);
    Log.log(4, "IFAction", "showVoucherDetails", "Exited");

    request.setAttribute("message", "Vouchers Generated Successfully");

    return mapping.findForward("success");
  }

  public ActionForward showBankReconFilter(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response)
    throws Exception
  {
    Log.log(4, "IFAction", "showBankReconFilter", "Entered");

    DynaActionForm ifForm = (DynaActionForm)form;

    java.util.Date date = new java.util.Date();
    Calendar calendar = Calendar.getInstance();
    calendar.setTime(date);

    Dates generalReport = new Dates();
    generalReport.setReconDate(date);
    BeanUtils.copyProperties(ifForm, generalReport);

    Log.log(4, "IFAction", "showBankReconFilter", "Exited");

    return mapping.findForward("success");
  }

  public ActionForward showBankRecon(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response)
    throws Exception
  {
    Log.log(4, "IFAction", "showBankRecon", "Entered");

    DynaActionForm ifForm = (DynaActionForm)form;

    java.util.Date date = (java.util.Date)ifForm.get("reconDate");

    IFProcessor ifProcessor = new IFProcessor();
    BankReconcilation bankReconcilation = ifProcessor.getBankReconDetails(date);
    BeanUtils.copyProperties(ifForm, bankReconcilation);

    Log.log(4, "IFAction", "showBankRecon", "Exited");

    return mapping.findForward("success");
  }

  public ActionForward updateBankRecon(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response)
    throws Exception
  {
    Log.log(4, "IFAction", "updateBankRecon", "Entered");

    DynaActionForm ifForm = (DynaActionForm)form;

    java.util.Date date = (java.util.Date)ifForm.get("reconDate");
    BankReconcilation bankReconcilation = new BankReconcilation();
    BeanUtils.populate(bankReconcilation, ifForm.getMap());

    Log.log(5, "IFAction", "updateBankRecon", "1 " + bankReconcilation.getCgtsiBalance());
    Log.log(5, "IFAction", "updateBankRecon", "2 " + bankReconcilation.getChequeIssuedAmount());
    Log.log(5, "IFAction", "updateBankRecon", "3 " + bankReconcilation.getDirectCredit());
    Log.log(5, "IFAction", "updateBankRecon", "4 " + bankReconcilation.getDirectDebit());
    Log.log(5, "IFAction", "updateBankRecon", "5 " + bankReconcilation.getClosingBalanceIDBI());

    User user = getUserInformation(request);
    String userId = user.getUserId();

    IFProcessor ifProcessor = new IFProcessor();
    ifProcessor.updateBankRecon(date, bankReconcilation, userId);

    request.setAttribute("message", "Bank Reconciliation Details saved successfully");

    Log.log(4, "IFAction", "updateBankRecon", "Exited");

    return mapping.findForward("success");
  }

  public ActionForward showDtlsForChqLeavesInsert(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response)
    throws Exception
  {
    DynaActionForm ifForm = (DynaActionForm)form;

    IFProcessor ifProcessor = new IFProcessor();

    ArrayList bankNames = new ArrayList();

    ifForm.initialize(mapping);

    Log.log(4, "IFAction", "showDtlsForChqLeavesInsert", "Entered");

    ArrayList bankDetails = ifProcessor.getBankAccounts();

    for (int i = 0; i < bankDetails.size(); i++)
    {
      BankAccountDetail bankAccountDetail = (BankAccountDetail)bankDetails.get(i);
      String bankName = bankAccountDetail.getBankName();
      String branchName = bankAccountDetail.getBankBranchName();

      String bankBranchName = String.valueOf(bankName) + "," + branchName + "(" + bankAccountDetail.getAccountNumber() + ")";
      bankNames.add(bankBranchName);
    }

    ifForm.set("bankAcctDetails", bankNames);

    Log.log(4, "IFAction", "showDtlsForChqLeavesInsert", "Exited");

    return mapping.findForward("success");
  }

  public ActionForward insertChqDetails(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response)
    throws Exception
  {
    Log.log(4, "IFAction", "insertChqDetails", "Entered");

    DynaActionForm ifForm = (DynaActionForm)form;

    ChequeLeavesDetails chequeLeavesDetails = new ChequeLeavesDetails();

    Log.log(4, "IFAction", "insertChqDetails", "banrkBranchName :" + ifForm.get("bnkName"));
    Log.log(4, "IFAction", "insertChqDetails", "start no :" + ifForm.get("chqStartNo"));
    Log.log(4, "IFAction", "insertChqDetails", "end no :" + ifForm.get("chqEndingNo"));

    BeanUtils.populate(chequeLeavesDetails, ifForm.getMap());

    User user = getUserInformation(request);
    String userId = user.getUserId();

    String bankBranchName = (String)ifForm.get("bnkName");
    int start = bankBranchName.indexOf(",");
    String bankName = bankBranchName.substring(0, start);
    chequeLeavesDetails.setChqBankName(bankName);

    int start1 = bankBranchName.indexOf("(");
    int finish1 = bankBranchName.indexOf(")");

    String branchName = bankBranchName.substring(start + 1, start1);
    chequeLeavesDetails.setChqBranchName(branchName);

    String bnkAcctNo = bankBranchName.substring(start1 + 1, finish1);

    Log.log(4, "IFAction", "insertChqDetails", "bnkAcctNo :" + bnkAcctNo);

    chequeLeavesDetails.setBnkAccountNumber(bnkAcctNo);

    IFProcessor ifProcessor = new IFProcessor();
    ifProcessor.insertChqLeavesDetails(chequeLeavesDetails, user);

    request.setAttribute("message", "Cheque Details Inserted Successfully");

    Log.log(4, "IFAction", "insertChqDetails", "Exited");

    return mapping.findForward("success");
  }

  public ActionForward showDtlsForChqLeavesUpdate(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response)
    throws Exception
  {
    Log.log(4, "IFAction", "showDtlsForChqLeavesUpdate", "Entered");

    InvestmentForm actionForm = (InvestmentForm)form;

    actionForm.resetMaps();

    IFProcessor ifProcessor = new IFProcessor();

    ArrayList bankNames = new ArrayList();

    Log.log(4, "IFAction", "showDtlsForChqLeavesUpdate", "Entered");

    ArrayList bankDetails = ifProcessor.getBankAccounts();

    actionForm.setBankBranchName("");

    String bankName = "";
    String branchName = "";
    String bankBranchName = "";

    for (int i = 0; i < bankDetails.size(); i++)
    {
      BankAccountDetail bankAccountDetail = (BankAccountDetail)bankDetails.get(i);
      bankName = bankAccountDetail.getBankName();
      branchName = bankAccountDetail.getBankBranchName();

      bankBranchName = String.valueOf(bankName) + "," + branchName + "(" + bankAccountDetail.getAccountNumber() + ")";
      bankNames.add(bankBranchName);
    }

    actionForm.setBnkChqDetails(bankNames);

    Log.log(4, "IFAction", "showDtlsForChqLeavesUpdate", "Exited");

    return mapping.findForward("success");
  }

  public ActionForward showBankChqDetails(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response)
    throws Exception
  {
    Log.log(4, "IFAction", "showDtlsForChqLeavesUpdate", "Entered");

    InvestmentForm actionForm = (InvestmentForm)form;

    IFProcessor ifProcessor = new IFProcessor();

    TreeMap startNoMap = new TreeMap();
    TreeMap endNoMap = new TreeMap();

    ArrayList bankNames = new ArrayList();

    String bankBranchName = actionForm.getBankBranchName();
    int start = bankBranchName.indexOf(",");
    String bankName = bankBranchName.substring(0, start);

    int start1 = bankBranchName.indexOf("(");
    int finish1 = bankBranchName.indexOf(")");

    String branchName = bankBranchName.substring(start + 1, start1);

    ArrayList bnkChqDetails = new ArrayList();

    String bnkAcctNo = bankBranchName.substring(start1 + 1, finish1);

    bnkChqDetails = ifProcessor.getBankChqLeavesDetails(bankName, branchName, bnkAcctNo);

    if ((bnkChqDetails == null) || (bnkChqDetails.size() == 0))
    {
      throw new MessageException("There are no Cheque Leaves For Updation");
    }

    for (int i = 0; i < bnkChqDetails.size(); i++)
    {
      ChequeLeavesDetails chequeLeavesDetails = (ChequeLeavesDetails)bnkChqDetails.get(i);
      int chqId = chequeLeavesDetails.getChqId();
      int startNo = chequeLeavesDetails.getChqStartNo();
      int endNo = chequeLeavesDetails.getChqEndingNo();

      String key = "key-" + i;

      startNoMap.put(key, new Integer(startNo));
      endNoMap.put(key, new Integer(endNo));

      actionForm.setStartNo(startNoMap);
      actionForm.setEndNo(endNoMap);
    }

    actionForm.setBnkChqDetails(bnkChqDetails);

    Log.log(4, "IFAction", "showDtlsForChqLeavesUpdate", "Exited");

    return mapping.findForward("success");
  }

  public ActionForward saveUpdatedChqDetails(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response)
    throws Exception
  {
    Log.log(4, "IFAction", "showDtlsForChqLeavesUpdate", "Entered");

    InvestmentForm actionForm = (InvestmentForm)form;

    IFProcessor ifProcessor = new IFProcessor();

    User user = getUserInformation(request);

    Map chqId = new TreeMap();

    chqId = actionForm.getChequeId();
    Map startNo = actionForm.getStartNo();
    Map endNo = actionForm.getEndNo();
    Map bankId = actionForm.getBankId();

    Set chqIdSet = chqId.keySet();
    Set startNoSet = startNo.keySet();
    Set endNoSet = endNo.keySet();

    Iterator chqIdSetIterator = chqIdSet.iterator();

    while (chqIdSetIterator.hasNext())
    {
      ChequeLeavesDetails chqLeavesDetails = new ChequeLeavesDetails();
      String key = (String)chqIdSetIterator.next();

      Log.log(4, "IFAction", "showDtlsForChqLeavesUpdate", "key :" + key);
      int chequeId = Integer.parseInt((String)chqId.get(key));
      Log.log(4, "IFAction", "showDtlsForChqLeavesUpdate", "chequeId :" + chequeId);
      Log.log(4, "IFAction", "showDtlsForChqLeavesUpdate", "cqlBankId :" + (String)bankId.get(key));
      int cqlBankId = Integer.parseInt((String)bankId.get(key));
      Log.log(4, "IFAction", "showDtlsForChqLeavesUpdate", "cqlBankId :" + cqlBankId);
      int startNumber = Integer.parseInt((String)startNo.get(key));
      Log.log(4, "IFAction", "showDtlsForChqLeavesUpdate", "startNumber :" + startNumber);
      int endNumber = Integer.parseInt((String)endNo.get(key));
      Log.log(4, "IFAction", "showDtlsForChqLeavesUpdate", "endNumber :" + endNumber);

      chqLeavesDetails.setChqId(chequeId);
      chqLeavesDetails.setChqBankId(cqlBankId);
      chqLeavesDetails.setChqStartNo(startNumber);
      chqLeavesDetails.setChqEndingNo(endNumber);

      ifProcessor.updateChqLeavesDetails(chqLeavesDetails, user);
    }

    Log.log(4, "IFAction", "showDtlsForChqLeavesUpdate", "Exited");

    request.setAttribute("message", "Cheque Details Updated Successfully");

    return mapping.findForward("success");
  }

  public ActionForward showUnUtilizedChqDetails(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response)
    throws Exception
  {
    Log.log(4, "IFAction", "showUnUtilizedChqDetails", "Entered");

    InvestmentForm actionForm = (InvestmentForm)form;

    IFProcessor ifProcessor = new IFProcessor();

    ArrayList bankNames = new ArrayList();

    String bankBranchName = actionForm.getBankBranchName();
    int start = bankBranchName.indexOf(",");
    String bankName = bankBranchName.substring(0, start);

    int start1 = bankBranchName.indexOf("(");
    int finish1 = bankBranchName.indexOf(")");

    String branchName = bankBranchName.substring(start + 1, start1);

    String bnkAcctNo = bankBranchName.substring(start1 + 1, finish1);

    ArrayList unutilizedChqDetails = ifProcessor.getUnUtilizedChqLeaves(bankName, branchName, bnkAcctNo);

    if ((unutilizedChqDetails == null) || (unutilizedChqDetails.size() == 0))
    {
      throw new MessageException("There are no UnUtilized Cheque Leaves For Conversion");
    }

    actionForm.setUnutilizedChqDetails(unutilizedChqDetails);

    Log.log(4, "IFAction", "showUnUtilizedChqDetails", "Exited");

    return mapping.findForward("success");
  }

  public ActionForward saveCancelledCheques(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response)
    throws Exception
  {
    Log.log(4, "IFAction", "saveCancelledCheques", "Exited");

    InvestmentForm actionForm = (InvestmentForm)form;

    IFProcessor ifProcessor = new IFProcessor();

    String bankBranchName = actionForm.getBankBranchName();
    int start = bankBranchName.indexOf(",");
    String bankName = bankBranchName.substring(0, start);

    int start1 = bankBranchName.indexOf("(");
    int finish1 = bankBranchName.indexOf(")");

    String branchName = bankBranchName.substring(start + 1, start1);

    String bnkAcctNo = bankBranchName.substring(start1 + 1, finish1);

    User user = getUserInformation(request);

    Map chqId = actionForm.getChequeId();

    Map cancelledChq = actionForm.getCancelledChq();

    Set chqIdSet = chqId.keySet();

    Iterator chqIdSetIterator = chqIdSet.iterator();

    while (chqIdSetIterator.hasNext())
    {
      ChequeDetails chequeDetails = new ChequeDetails();

      chequeDetails.setBankName(bankName);
      chequeDetails.setBranchName(branchName);

      Log.log(4, "IFAction", "saveCancelledCheques", "bankName :" + bankName);
      Log.log(4, "IFAction", "saveCancelledCheques", "branchName :" + branchName);

      String key = (String)chqIdSetIterator.next();

      Log.log(4, "IFAction", "saveCancelledCheques", "key :" + key);
      int chequeId = Integer.parseInt((String)chqId.get(key));

      Log.log(4, "IFAction", "saveCancelledCheques", "chequeId :" + chequeId);

      chequeDetails.setChqNumber(chequeId);

      String cancelledFlag = (String)cancelledChq.get(key);

      if ((cancelledFlag != null) && (cancelledFlag.equals("Y")))
      {
        ifProcessor.saveCancelledChqDetails(chequeDetails, user);
      }

    }

    Log.log(4, "IFAction", "saveCancelledCheques", "Exited");

    actionForm.setBankBranchName("");

    request.setAttribute("message", "Cheque Cancelled Successfully");

    return mapping.findForward("success");
  }

  private double calculateYTM(double parValue, double purchacePrice, double couponRate, double maturityYears)
  {
    double z = couponRate / 100;
    double c = couponRate * parValue / 100;
    double r = couponRate / 100;
    double E = 1.E-005D;

    for (int i = 0; i < 100; i++)
    {
      if (Math.abs(fYTM(z, purchacePrice, c, parValue, maturityYears)) < E)
        break;
      while (Math.abs(dfYTM(z, purchacePrice, c, parValue, maturityYears)) < E) z += 0.1D;
      z -= fYTM(z, purchacePrice, c, parValue, maturityYears) / dfYTM(z, purchacePrice, c, parValue, maturityYears);
    }
    if (Math.abs(fYTM(z, purchacePrice, c, parValue, maturityYears)) >= E)
    {
      return -1.0D;
    }

    return 1 / z - 1;
  }

  private double fYTM(double z, double p, double c, double b, double y)
  {
    return (c + b) * Math.pow(z, y + 1) - b * Math.pow(z, y) - (c + p) * z + p;
  }

  private double dfYTM(double z, double p, double c, double b, double y)
  {
    return (y + 1) * (c + b) * Math.pow(z, y) - y * b * Math.pow(z, y - 1) - (c + p);
  }

  public ActionForward showRatingsForAgencyName(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response)
    throws Exception
  {
    DynaActionForm actionForm = (DynaActionForm)form;
    String agencyName = (String)actionForm.get("ratingAgency");

    IFProcessor ifProcessor = new IFProcessor();

    ArrayList ratingsList = new ArrayList();
    if ((agencyName != null) && (!agencyName.equals("")))
    {
      ratingsList = ifProcessor.getRatingsForAgency(agencyName);

      Log.log(4, "IFAction", "showRatingsForAgencyName", "ratingsList :" + ratingsList.size());
    }

    actionForm.set("instrumentRatings", ratingsList);

    return mapping.findForward("success");
  }
}