package com.cgtsi.action;

import com.cgtsi.admin.Administrator;
import com.cgtsi.admin.User;
import com.cgtsi.common.Log;
import com.cgtsi.common.NoDataException;
import com.cgtsi.mcgs.MCGSProcessor;
import com.cgtsi.application.LogClass;
import com.cgtsi.mcgs.ParticipatingBankLimit;
import com.cgtsi.registration.MLIInfo;
import com.cgtsi.registration.Registration;
import com.cgtsi.risk.DefaultRate;
import com.cgtsi.risk.ExposureDetails;
import com.cgtsi.risk.ExposureReportDetail;
import com.cgtsi.risk.ExposureSummary;
import com.cgtsi.risk.GlobalLimits;
import com.cgtsi.risk.ParameterCombination;
import com.cgtsi.risk.RiskManagementProcessor;
import com.cgtsi.risk.SubSchemeParameters;
import com.cgtsi.risk.SubSchemeValues;
import com.cgtsi.risk.UserLimits;
import com.cgtsi.util.CustomisedDate;
import java.io.PrintStream;
import java.util.ArrayList;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import org.apache.commons.beanutils.BeanUtils;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.action.DynaActionForm;

public class RMAction extends BaseAction
{
  public ActionForward defaultRateReportInput(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response)
    throws Exception
  {
    Log.log(4, "ReportsAction", "defaultRateReportInput", "Entered");

    DynaActionForm dynaForm = (DynaActionForm)form;
    java.util.Date date = new java.util.Date();

    DefaultRate generalReport = new DefaultRate();
    generalReport.setSettlementDate(date);
    BeanUtils.copyProperties(dynaForm, generalReport);
    Log.log(4, "ReportsAction", "defaultRateReportInput", "Exited");

    return mapping.findForward("success");
  }

  public ActionForward defaultRateReport(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response)
    throws Exception
  {
    Log.log(4, "IFAction", "defaultRateReport", "Entered");
    ArrayList dan = new ArrayList();
    DynaActionForm dynaForm = (DynaActionForm)form;
    RiskManagementProcessor rmProcessor = new RiskManagementProcessor();
    java.sql.Date settlementDate = null;
    java.util.Date eDate = (java.util.Date)dynaForm.get("settlementDate");
    settlementDate = new java.sql.Date(eDate.getTime());
    dan = rmProcessor.defaultRateReport(settlementDate);
    dynaForm.set("defaultArray", dan);
    int size = dan.size();
    System.out.println("size:" + size);

    if ((dan == null) || (dan.size() == 0))
    {
      throw new NoDataException("No Data is available for the values entered");
    }

    Log.log(4, "IFAction", "defaultRateReport", "Exited");

    return mapping.findForward("success");
  }

  public ActionForward showUserLimits(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response)
    throws Exception
  {
    Log.log(4, "RMAction", "showUserLimits", "Entered");

    DynaActionForm dynaForm = (DynaActionForm)form;
    dynaForm.initialize(mapping);
    Administrator administrator = new Administrator();

    ArrayList designationsList = new ArrayList();
    designationsList = administrator.getAllDesignations();

    dynaForm.set("designationsList", designationsList);

    designationsList = null;
    administrator = null;

    Log.log(4, "RMAction", "showUserLimits", "Exited");

    return mapping.findForward("display");
  }

  public ActionForward setUserLimits(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response)
    throws Exception
  {
    Log.log(4, "RMAction", "setUserLimits", "Entered");
    String forwardPage = "";

    DynaActionForm dynaForm = (DynaActionForm)form;

    UserLimits userLimits = new UserLimits();
    BeanUtils.populate(userLimits, dynaForm.getMap());

    RiskManagementProcessor rmProcessor = new RiskManagementProcessor();
    User user = getUserInformation(request);

    rmProcessor.updateUserLimits(userLimits, user.getUserId());
    forwardPage = "success";
    request.setAttribute("message", "User Limits Set Successfully");

    userLimits = null;
    rmProcessor = null;
    user = null;

    Log.log(4, "RMAction", "setUserLimits", "Exited");

    return mapping.findForward("success");
  }

  public ActionForward showGlobalLimits(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response)
    throws Exception
  {
    Log.log(4, "RMAction", "showGlobalLimits", "Entered");
    DynaActionForm dynaForm = (DynaActionForm)form;
    dynaForm.initialize(mapping);
    RiskManagementProcessor rmProcessor = new RiskManagementProcessor();

    ArrayList schemes = new ArrayList();
    ArrayList subSchemes = new ArrayList();

    schemes = rmProcessor.getValuesFromSchemeMaster();
    subSchemes = rmProcessor.getAllSubSchemeNames();

    dynaForm.set("schemeList", schemes);
    dynaForm.set("subSchemeList", subSchemes);

    schemes = null;
    subSchemes = null;

    Log.log(4, "RMAction", "showGlobalLimits", "Exited");

    return mapping.findForward("display");
  }

  public ActionForward setGlobalLimits(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response)
    throws Exception
  {
    Log.log(4, "RMAction", "setGlobalLimits", "Entered");
    String forwardPage = "";

    DynaActionForm dynaForm = (DynaActionForm)form;

    GlobalLimits globalLimits = new GlobalLimits();
    BeanUtils.populate(globalLimits, dynaForm.getMap());

    RiskManagementProcessor rmProcessor = new RiskManagementProcessor();
    User user = getUserInformation(request);

    globalLimits.setIsFundsBasedOrNonFundsBasedOrBoth(2);
    rmProcessor.updateLimitsForGlobalSettings(globalLimits, user.getUserId());
    forwardPage = "success";
    request.setAttribute("message", "Global Limits Set Successfully");

    globalLimits = null;
    rmProcessor = null;
    user = null;

    Log.log(4, "RMAction", "setGlobalLimits", "Exited");

    return mapping.findForward("success");
  }

  public ActionForward showParticipatingBankLimits(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response)
    throws Exception
  {
    Log.log(4, "RMAction", "showParticipatingBankLimits", "Entered");

    HttpSession session = request.getSession(false);
    session.setAttribute("PARTICIPATING_BANK_FLAG", "0");

    DynaActionForm dynaForm = (DynaActionForm)form;
    dynaForm.initialize(mapping);
    MCGSProcessor mcgsProcessor = new MCGSProcessor();

    ArrayList mcgsMlis = new ArrayList();
    ArrayList mcgfs = new ArrayList();

    mcgsMlis = mcgsProcessor.getAllMCGFs();

    for (int i = 0; i < mcgsMlis.size(); i++)
    {
      MLIInfo member = (MLIInfo)mcgsMlis.get(i);
      String text = "(" + member.getBankId() + member.getZoneId() + member.getBranchId() + "" + ")" + member.getShortName() + "," + member.getBankName();

      mcgfs.add(text);
    }

    dynaForm.set("mliList", mcgfs);

    mcgsProcessor = null;

    mcgsMlis = null;
    mcgfs = null;

    Log.log(4, "RMAction", "showParticipatingBankLimits", "Exited");

    return mapping.findForward("display");
  }

  public ActionForward getParticipatingBanks(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response)
    throws Exception
  {
    Log.log(4, "RMAction", "getParticipatingBanks", "Entered");

    HttpSession session = request.getSession(false);
    session.setAttribute("PARTICIPATING_BANK_FLAG", "1");

    DynaActionForm dynaForm = (DynaActionForm)form;
    String mcgfMember = (String)dynaForm.get("memberId");
    MCGSProcessor mcgsProcessor = new MCGSProcessor();

    ArrayList banks = new ArrayList();

    if (!mcgfMember.equals(""))
    {
      mcgfMember = mcgfMember.substring(1, mcgfMember.indexOf(")"));
      banks = mcgsProcessor.getAllParticipatingBanks(mcgfMember);
    }

    dynaForm.set("participatingBanksList", banks);
    dynaForm.set("amount", new Double(0.0D));
    dynaForm.set("validFrom", null);
    dynaForm.set("validTo", null);

    for (int i = 0; i < banks.size(); i++)
    {
      Log.log(4, "RMAction", "getParticipatingBanks", "bank " + (String)banks.get(i));
    }

    mcgsProcessor = null;
    banks = null;

    Log.log(4, "RMAction", "getParticipatingBanks", "Exited");

    return mapping.findForward("display");
  }

  public ActionForward setParticipatingBankLimits(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response)
    throws Exception
  {
    Log.log(4, "RMAction", "setParticipatingBankLimits", "Entered");
    String forwardPage = "";

    DynaActionForm dynaForm = (DynaActionForm)form;

    ParticipatingBankLimit bankLimits = new ParticipatingBankLimit();
    BeanUtils.populate(bankLimits, dynaForm.getMap());

    RiskManagementProcessor rmProcessor = new RiskManagementProcessor();
    User user = getUserInformation(request);

    rmProcessor.updateParticipatingBankLimits(bankLimits, user.getUserId());
    forwardPage = "success";
    request.setAttribute("message", "Participating Bank Limits Set Successfully");

    bankLimits = null;
    rmProcessor = null;
    user = null;

    Log.log(4, "RMAction", "setParticipatingBankLimits", "Exited");

    return mapping.findForward("success");
  }

  public ActionForward setSubScheme(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response)
    throws Exception
  {
    Log.log(4, "RMAction", "setSubScheme", "Entered");
    DynaActionForm dynaForm = (DynaActionForm)form;

    SubSchemeParameters subSchemeParameters = new SubSchemeParameters();
    SubSchemeValues subSchemeValues = new SubSchemeValues();

    BeanUtils.populate(subSchemeParameters, dynaForm.getMap());
    Log.log(4, "RMAction", "setSubScheme", "valid from date " + subSchemeParameters.getValidFromDate());
    Log.log(4, "RMAction", "setSubScheme", "valid to date " + subSchemeParameters.getValidToDate());
    BeanUtils.populate(subSchemeValues, dynaForm.getMap());
    String rule = (String)dynaForm.get("rule");
    Log.log(5, "RMAction", "setSubScheme", "rule -- " + rule);
    if (rule.equals("Days"))
    {
      int days = ((Integer)dynaForm.get("noOfDays")).intValue();
      Log.log(5, "RMAction", "setSubScheme", "no of days -- " + days);
      subSchemeValues.setAppFilingTimeLimit(days);
    }
    else if (rule.equals("Periodicity"))
    {
      int periodicity = ((Integer)dynaForm.get("periodicity")).intValue();
      Log.log(5, "RMAction", "setSubScheme", "periodicity -- " + periodicity);
      subSchemeValues.setAppFilingTimeLimit(periodicity);
    }

    Administrator administrator = new Administrator();
    Registration registration = new Registration();
    String[] state = subSchemeParameters.getState();
    if (state[0].equals("ALL"))
    {
      ArrayList statesList = administrator.getAllStates();
      //Diksha 29/05/2017
      if(statesList.size() == 0)
          LogClass.StepWritter((new StringBuilder("Null stateslist in ==Line317== in setSubScheme method in 'RMAction'statesList===")).append(statesList).toString());
      int size = statesList.size();
      if(size >= 0)
          LogClass.StepWritter((new StringBuilder("Null stateslist in ==Line322== in setSubScheme method in 'size===")).append(size).toString());
      state = new String[size];

      for (int i = 0; i < size; i++)
      {
        state[i] = ((String)statesList.get(i));
      }
      subSchemeParameters.setState(state);
    }
    String[] industry = subSchemeParameters.getIndustry();
    if (industry[0].equals("ALL"))
    {
      ArrayList indList = administrator.getAllIndustryNature();
      int size = indList.size();
      industry = new String[size];

      for (int i = 0; i < size; i++)
      {
        industry[i] = ((String)indList.get(i));
      }
      subSchemeParameters.setIndustry(industry);
    }
    String[] gender = subSchemeParameters.getGender();
    if (gender[0].equals("A"))
    {
      gender = new String[2];
      gender[0] = "M";
      gender[1] = "F";
      subSchemeParameters.setGender(gender);
    }
    String[] mli = subSchemeParameters.getMli();

    if (mli[0].equals("ALL"))
    {
      ArrayList mliList = registration.getAllMLIs();
      int size = mliList.size();
      mli = new String[size];

      for (int i = 0; i < size; i++)
      {
        MLIInfo mliInfo = new MLIInfo();
        mliInfo = (MLIInfo)mliList.get(i);
        mli[i] = (mliInfo.getShortName() + "(" + mliInfo.getBankId() + mliInfo.getZoneId() + mliInfo.getBranchId() + ")");
      }
      subSchemeParameters.setMli(mli);
    }
    String[] socialCat = subSchemeParameters.getSocialCategory();
    if (socialCat[0].equals("ALL"))
    {
      ArrayList scList = administrator.getAllSocialCategories();
      int size = scList.size();
      socialCat = new String[size];

      for (int i = 0; i < size; i++)
      {
        socialCat[i] = ((String)scList.get(i));
      }
      subSchemeParameters.setSocialCategory(socialCat);
    }

    RiskManagementProcessor rmProcessor = new RiskManagementProcessor();
    User user = getUserInformation(request);
    rmProcessor.setSubSchemeDetails(subSchemeParameters, subSchemeValues, user.getUserId());

    Log.log(4, "RMAction", "setSubScheme", "sub scheme name : " + subSchemeParameters.getSubScheme());
    request.setAttribute("message", "Sub Scheme Set Successfully");

    subSchemeParameters = null;
    subSchemeValues = null;
    administrator = null;
    registration = null;
    rmProcessor = null;
    state = null;
    industry = null;
    gender = null;
    mli = null;
    socialCat = null;

    Log.log(4, "RMAction", "setSubScheme", "Exited");

    return mapping.findForward("success");
  }

  public ActionForward showExposureSummary(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response)
    throws Exception
  {
    Log.log(4, "RMAction", "showExposureSummary", "Entered");

    DynaActionForm dynaForm = (DynaActionForm)form;
    ExposureSummary exposureSummary = new ExposureSummary();
    Administrator administrator = new Administrator();
    Registration registration = new Registration();

    BeanUtils.populate(exposureSummary, dynaForm.getMap());

    String scheme = (String)dynaForm.get("scheme");
    Log.log(5, "RMAction", "showExposureSummary", "scheme -- " + scheme);

    RiskManagementProcessor rmProcessor = new RiskManagementProcessor();

    exposureSummary = rmProcessor.calculateExposure(exposureSummary);

    ArrayList temp = exposureSummary.getTcDetails();
    for (int i = 0; i < temp.size(); i++)
    {
      ExposureDetails exp = (ExposureDetails)temp.get(i);
      Log.log(5, "RIDAO", "getExposureSummary", "tc approved amount " + exp.getAppAmount());
      Log.log(5, "RIDAO", "getExposureSummary", "tc approved count " + exp.getAppCount());
      Log.log(5, "RIDAO", "getExposureSummary", "tc issued amount " + exp.getIssuedAmount());
      Log.log(5, "RIDAO", "getExposureSummary", "tc issued count " + exp.getIssuedCount());
    }

    BeanUtils.copyProperties(dynaForm, exposureSummary);

    exposureSummary = null;
    administrator = null;
    registration = null;

    rmProcessor = null;

    Log.log(4, "RMAction", "showExposureSummary", "Exited");

    return mapping.findForward("success");
  }

  public ActionForward showNewSubScheme(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response)
    throws Exception
  {
    Log.log(4, "RMAction", "showNewSubScheme", "Entered");

    DynaActionForm dynaForm = (DynaActionForm)form;
    dynaForm.initialize(mapping);

    Administrator administrator = new Administrator();
    ArrayList states = new ArrayList();
    ArrayList industryNames = new ArrayList();
    ArrayList socialCategories = new ArrayList();
    ArrayList mlis = new ArrayList();
    ArrayList mliInfos = new ArrayList();
    Registration registration = new Registration();

    states = administrator.getAllStates();
    dynaForm.set("statesList", states);

    industryNames = administrator.getAllIndustryNature();
    dynaForm.set("industryList", industryNames);

    socialCategories = administrator.getAllSocialCategories();
    dynaForm.set("socialCategoriesList", socialCategories);

    String mli = "";

    mliInfos = registration.getAllMLIs();
    int mliInfoSize = mliInfos.size();
    int i = 0;
    for (i = 0; i < mliInfoSize; i++)
    {
      MLIInfo mliInfo = new MLIInfo();
      mliInfo = (MLIInfo)mliInfos.get(i);
      mli = mliInfo.getShortName() + "(" + mliInfo.getBankId() + mliInfo.getZoneId() + mliInfo.getBranchId() + ")";
      mlis.add(mli);
    }
    dynaForm.set("mliList", mlis);

    CustomisedDate customDate = new CustomisedDate();

    dynaForm.set("validFromDate", customDate);

    administrator = null;

    states = null;

    industryNames = null;

    socialCategories = null;

    mlis = null;

    mliInfos = null;
    registration = null;

    Log.log(4, "RMAction", "showNewSubScheme", "Exited");

    return mapping.findForward("display");
  }

  public ActionForward showExposureInput(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response)
    throws Exception
  {
    Log.log(4, "RMAction", "showExposureInput", "Entered");
    DynaActionForm dynaForm = (DynaActionForm)form;
    dynaForm.initialize(mapping);
    RiskManagementProcessor rmProcessor = new RiskManagementProcessor();
    Administrator administrator = new Administrator();
    Registration registration = new Registration();

    ArrayList states = new ArrayList();
    ArrayList schemes = new ArrayList();
    ArrayList subSchemes = new ArrayList();
    ArrayList industry = new ArrayList();
    ArrayList socialCategories = new ArrayList();
    ArrayList mlis = new ArrayList();
    ArrayList mliInfos = new ArrayList();

    states = administrator.getAllStates();
    industry = administrator.getAllIndustryNature();
    schemes = rmProcessor.getValuesFromSchemeMaster();
    socialCategories = administrator.getAllSocialCategories();

    String mli = "";

    mliInfos = registration.getAllMLIs();
    int mliInfoSize = mliInfos.size();
    int i = 0;
    for (i = 0; i < mliInfoSize; i++)
    {
      MLIInfo mliInfo = new MLIInfo();
      mliInfo = (MLIInfo)mliInfos.get(i);
      mli = mliInfo.getShortName() + "(" + mliInfo.getBankId() + mliInfo.getZoneId() + mliInfo.getBranchId() + ")";
      mlis.add(mli);
    }

    dynaForm.set("statesList", states);
    dynaForm.set("industryList", industry);
    dynaForm.set("schemeList", schemes);
    dynaForm.set("mliList", mlis);
    dynaForm.set("socialCategoriesList", socialCategories);

    rmProcessor = null;
    registration = null;
    administrator = null;

    states = null;

    schemes = null;

    subSchemes = null;

    industry = null;

    socialCategories = null;

    mlis = null;
    MLIInfo mliInfo = null;

    Log.log(4, "RMAction", "showExposureInput", "Exited");

    return mapping.findForward("display");
  }

  public ActionForward getMLiNames(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response)
    throws Exception
  {
    Log.log(4, "RMAction", "getMLiNames", "Entered");
    DynaActionForm dynaForm = (DynaActionForm)form;

    ArrayList mlis = new ArrayList();
    ArrayList mliInfos = new ArrayList();
    Registration registration = new Registration();

    String mli = "";

    mliInfos = registration.getAllMLIs();
    int mliInfoSize = mliInfos.size();
    int i = 0;
    for (i = 0; i < mliInfoSize; i++)
    {
      MLIInfo mliInfo = new MLIInfo();
      mliInfo = (MLIInfo)mliInfos.get(i);
      mli = mliInfo.getShortName() + "(" + mliInfo.getBankId() + mliInfo.getZoneId() + mliInfo.getBranchId() + ")";
      mlis.add(mli);
    }
    dynaForm.set("mliList", mlis);

    mlis.clear();
    mliInfos.clear();
    mlis = null;
    mliInfos = null;
    MLIInfo mliInfo = null;
    registration = null;

    Log.log(4, "RMAction", "getMLiNames", "Exited");

    return mapping.findForward("display");
  }

  public ActionForward showExposureReport(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response)
    throws Exception
  {
    Log.log(4, "RMAction", "showExposureReport", "Entered");

    DynaActionForm dynaForm = (DynaActionForm)form;
    ParameterCombination parameterCombination = new ParameterCombination();

    BeanUtils.populate(parameterCombination, dynaForm.getMap());

    String scheme = (String)dynaForm.get("scheme");
    Log.log(5, "RMAction", "showExposureReport", "scheme -- " + scheme);

    RiskManagementProcessor rmProcessor = new RiskManagementProcessor();

    ExposureReportDetail exposureReportDetail = new ExposureReportDetail();
    exposureReportDetail = rmProcessor.generateExposureReport(parameterCombination);
    ExposureDetails expDetails = new ExposureDetails();

    if (exposureReportDetail.getTcExposureDetails() != null)
    {
      ArrayList tcExposureDetails = exposureReportDetail.getTcExposureDetails();
      for (int i = 0; i < tcExposureDetails.size(); i++)
      {
        expDetails = (ExposureDetails)tcExposureDetails.get(i);
        Log.log(4, "RMAction", "showExposureReport", "tc exp state " + expDetails.getState());
        Log.log(4, "RMAction", "showExposureReport", "tc exp member id " + expDetails.getMemberId());
        Log.log(4, "RMAction", "showExposureReport", "tc exp bid " + expDetails.getBid());
        Log.log(4, "RMAction", "showExposureReport", "tc exp sanc amt " + expDetails.getSancAmt());
        Log.log(4, "RMAction", "showExposureReport", "tc exp os amt " + expDetails.getOsAmt());
        Log.log(4, "RMAction", "showExposureReport", "tc exp no of claims " + expDetails.getNoOfClaims());
        Log.log(4, "RMAction", "showExposureReport", "tc exp total claim amt " + expDetails.getTotalClaim());
      }
    }
    if (exposureReportDetail.getWcExposureDetails() != null)
    {
      ArrayList wcExposureDetails = exposureReportDetail.getWcExposureDetails();
      for (int i = 0; i < wcExposureDetails.size(); i++)
      {
        expDetails = (ExposureDetails)wcExposureDetails.get(i);
        Log.log(4, "RMAction", "showExposureReport", "wc exp state " + expDetails.getState());
        Log.log(4, "RMAction", "showExposureReport", "wc exp member id " + expDetails.getMemberId());
        Log.log(4, "RMAction", "showExposureReport", "wc exp bid " + expDetails.getBid());
        Log.log(4, "RMAction", "showExposureReport", "wc exp sanc amt " + expDetails.getSancAmt());
        Log.log(4, "RMAction", "showExposureReport", "wc exp os amt " + expDetails.getOsAmt());
        Log.log(4, "RMAction", "showExposureReport", "wc exp no of claims " + expDetails.getNoOfClaims());
        Log.log(4, "RMAction", "showExposureReport", "wc exp total claim amt " + expDetails.getTotalClaim());
      }
    }

    BeanUtils.copyProperties(dynaForm, parameterCombination);
    BeanUtils.copyProperties(dynaForm, exposureReportDetail);
    Log.log(4, "RMAction", "showExposureReport", "tc exp size " + ((ArrayList)dynaForm.get("tcExposureDetails")).size());
    Log.log(4, "RMAction", "showExposureReport", "wc exp size " + ((ArrayList)dynaForm.get("wcExposureDetails")).size());

    Log.log(4, "RMAction", "showExposureReport", "Exited");

    return mapping.findForward("display");
  }

  public ActionForward showSubSchemeReport(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response)
    throws Exception
  {
    Log.log(4, "RMAction", "showSubSchemeReport", "Entered");
    DynaActionForm dynaForm = (DynaActionForm)form;

    java.util.Date fromDate = (java.util.Date)dynaForm.get("reportFromDate");
    java.util.Date toDate = (java.util.Date)dynaForm.get("reportToDate");

    RiskManagementProcessor rmProcessor = new RiskManagementProcessor();

    ArrayList subSchemes = rmProcessor.getSubSchemes(fromDate, toDate);

    dynaForm.set("subSchemes", subSchemes);

    Log.log(4, "RMAction", "showSubSchemeReport", "Exited");

    return mapping.findForward("display");
  }

  public ActionForward showSubSchemeValuesReport(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response)
    throws Exception
  {
    Log.log(4, "RMAction", "showSubSchemeValueReport", "Entered");
    DynaActionForm dynaForm = (DynaActionForm)form;

    String subSchemeId = request.getParameter("id");
    Log.log(4, "RMAction", "showSubSchemeValueReport", "values for id " + subSchemeId);

    RiskManagementProcessor rmProcessor = new RiskManagementProcessor();

    SubSchemeValues subSchemeValues = rmProcessor.getSubSchemeValuesForId(subSchemeId);
    BeanUtils.copyProperties(dynaForm, subSchemeValues);
    int appFilingTimeLimit = subSchemeValues.getAppFilingTimeLimit();
    if (appFilingTimeLimit > 0)
    {
      dynaForm.set("rule", "Days");
      dynaForm.set("noOfDays", new Integer(appFilingTimeLimit));
      dynaForm.set("appFilingTimeLimitValue", appFilingTimeLimit + "");
    }
    else if (appFilingTimeLimit < 0)
    {
      dynaForm.set("rule", "Periodicity");
      String value = "";
      if (appFilingTimeLimit == -1)
      {
        value = "Monthly";
      }
      else if (appFilingTimeLimit == -2)
      {
        value = "Bi-Monthly";
      }
      else if (appFilingTimeLimit == -3)
      {
        value = "Quarterly";
      }
      else if (appFilingTimeLimit == -4)
      {
        value = "Half Yearly";
      }
      else if (appFilingTimeLimit == -6)
      {
        value = "Yearly";
      }
      dynaForm.set("periodicity", new Integer(appFilingTimeLimit));
      dynaForm.set("appFilingTimeLimitValue", value);
    }

    Log.log(4, "RMAction", "showSubSchemeValueReport", "Exited");

    return mapping.findForward("display");
  }

  public ActionForward showGlobalLimitValue(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response)
    throws Exception
  {
    DynaActionForm dynaForm = (DynaActionForm)form;

    RiskManagementProcessor rmProcessor = new RiskManagementProcessor();

    String schemeName = (String)dynaForm.get("scheme");
    String subSchemeName = (String)dynaForm.get("subScheme");

    GlobalLimits globalLimits = rmProcessor.getGlobalLimits(schemeName, subSchemeName);

    globalLimits.setScheme(schemeName);
    globalLimits.setSubScheme(subSchemeName);
    BeanUtils.copyProperties(dynaForm, globalLimits);

    return mapping.findForward("display");
  }

  public ActionForward showUserLimitValue(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response)
    throws Exception
  {
    DynaActionForm dynaForm = (DynaActionForm)form;

    RiskManagementProcessor rmProcessor = new RiskManagementProcessor();

    String dsgName = (String)dynaForm.get("designation");

    UserLimits userLimits = rmProcessor.getUserLimits(dsgName);

    userLimits.setDesignation(dsgName);
    BeanUtils.copyProperties(dynaForm, userLimits);

    return mapping.findForward("display");
  }

  public ActionForward getParticipatingBanksLimits(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response)
    throws Exception
  {
    DynaActionForm dynaForm = (DynaActionForm)form;

    HttpSession session = request.getSession(false);
    session.setAttribute("PARTICIPATING_BANK_FLAG", "2");

    RiskManagementProcessor rmProcessor = new RiskManagementProcessor();

    String memberId = (String)dynaForm.get("memberId");
    String bankName = (String)dynaForm.get("bankName");

    ParticipatingBankLimit participatingBankLimit = new ParticipatingBankLimit();

    if ((!memberId.equals("")) && (!bankName.equals("")))
    {
      participatingBankLimit = rmProcessor.getParticipatingBanksLimits(memberId, bankName);
    }

    participatingBankLimit.setMemberId(memberId);
    participatingBankLimit.setBankName(bankName);
    BeanUtils.copyProperties(dynaForm, participatingBankLimit);

    return mapping.findForward("display");
  }

  public ActionForward showSubSchemeReportInput(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response)
    throws Exception
  {
    Log.log(4, "RMAction", "showSubSchemeReportInput", "Entered");
    DynaActionForm dynaForm = (DynaActionForm)form;

    dynaForm.initialize(mapping);

    Log.log(4, "RMAction", "showSubSchemeReportInput", "Exited");

    return mapping.findForward("display");
  }
}