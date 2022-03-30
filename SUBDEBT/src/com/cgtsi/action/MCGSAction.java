package com.cgtsi.action;

import com.cgtsi.admin.Administrator;
import com.cgtsi.admin.User;
import com.cgtsi.application.BorrowerDetails;
import com.cgtsi.application.SSIDetails;
import com.cgtsi.common.Log;
import com.cgtsi.mcgs.DonorDetail;
import com.cgtsi.mcgs.MCGSProcessor;
import com.cgtsi.mcgs.ParticipatingBank;
import com.cgtsi.registration.MLIInfo;
import java.util.ArrayList;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.apache.commons.beanutils.BeanUtils;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.action.DynaActionForm;

public class MCGSAction extends BaseAction
{
  public ActionForward showParticipatingBank(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response)
    throws Exception
  {
    Log.log(4, "MCGSAction", "showParticipatingBank", "Entered");

    Administrator admin = new Administrator();

    ArrayList states = admin.getAllStates();

    MCGSProcessor processor = new MCGSProcessor();

    ArrayList tempMcgfs = processor.getAllMCGFs();

    ArrayList mcgfs = new ArrayList();

    for (int i = 0; i < tempMcgfs.size(); i++)
    {
      MLIInfo member = (MLIInfo)tempMcgfs.get(i);
      String text = "(" + member.getBankId() + member.getZoneId() + member.getBranchId() + "" + ")" + member.getShortName() + "," + member.getBankName();

      mcgfs.add(text);
    }

    DynaActionForm dynaForm = (DynaActionForm)form;

    dynaForm.initialize(mapping);

    dynaForm.set("states", states);
    dynaForm.set("mcgfs", mcgfs);

    request.setAttribute("MCGS_ACTION_FLAG", null);

    processor = null;
    admin = null;

    Log.log(4, "MCGSAction", "showParticipatingBank", "Exited");

    return mapping.findForward("success");
  }

  public ActionForward getDistricts(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response)
    throws Exception
  {
    Log.log(4, "MCGSAction", "getDistricts", "Entered");

    Administrator admin = new Administrator();

    DynaActionForm dynaForm = (DynaActionForm)form;

    String state = (String)dynaForm.get("state");

    Log.log(5, "MCGSAction", "getDistricts", "state " + state);

    ArrayList districts = admin.getAllDistricts(state);

    Log.log(5, "MCGSAction", "getDistricts", "districts " + districts);

    dynaForm.set("districts", districts);

    request.setAttribute("MCGS_ACTION_FLAG", "1");

    Log.log(4, "MCGSAction", "getDistricts", "Exited");

    return mapping.findForward("success");
  }

  public ActionForward addParticipatingBank(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response)
    throws Exception
  {
    Log.log(4, "MCGSAction", "addParticipatingBank", "Entered");

    DynaActionForm dynaForm = (DynaActionForm)form;

    ParticipatingBank participatingBank = new ParticipatingBank();

    BeanUtils.populate(participatingBank, dynaForm.getMap());
    Log.log(4, "MCGSAction", "addParticipatingBank", "MCGF Name " + dynaForm.get("mcgf"));

    participatingBank.setMemberId((String)dynaForm.get("mcgf"));
    MCGSProcessor mcgsProcessor = new MCGSProcessor();

    User user = getUserInformation(request);

    mcgsProcessor.addParticipatingBank(participatingBank, user);

    dynaForm.initialize(mapping);

    Log.log(4, "MCGSAction", "addParticipatingBank", "Exited");

    request.setAttribute("message", "Participating Bank Added Successfully");

    return mapping.findForward("success");
  }

  public ActionForward showAddDonorDetails(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response)
    throws Exception
  {
    Log.log(4, "MCGSAction", "showAddDonorDetails", "Entered");

    MCGSProcessor processor = new MCGSProcessor();

    ArrayList tempMcgfs = processor.getAllMCGFs();

    ArrayList mcgfs = new ArrayList();

    for (int i = 0; i < tempMcgfs.size(); i++)
    {
      MLIInfo member = (MLIInfo)tempMcgfs.get(i);
      String text = "(" + member.getBankId() + member.getZoneId() + member.getBranchId() + "" + ")" + member.getShortName() + "," + member.getBankName();

      mcgfs.add(text);
    }

    DynaActionForm dynaForm = (DynaActionForm)form;

    dynaForm.initialize(mapping);

    dynaForm.set("mcgfs", mcgfs);

    Log.log(4, "MCGSAction", "showAddDonorDetails", "Exited");

    return mapping.findForward("success");
  }

  public ActionForward addDonorDetails(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response)
    throws Exception
  {
    Log.log(4, "MCGSAction", "addDonorDetails", "Entered");

    MCGSProcessor mcgsProcessor = new MCGSProcessor();

    DynaActionForm dynaForm = (DynaActionForm)form;

    Log.log(5, "MCGSAction", "addDonorDetails", " corpus contribution " + dynaForm.get("corpusContributionAmt"));

    DonorDetail donorDetail = new DonorDetail();

    Log.log(4, "MCGSAction", "addDonorDetails", "MCGF Name " + dynaForm.get("mcgf"));

    BeanUtils.populate(donorDetail, dynaForm.getMap());
    donorDetail.setMemberId((String)dynaForm.get("mcgf"));

    User user = getUserInformation(request);

    Log.log(5, "MCGSAction", "addDonorDetails", " Address " + donorDetail.getAddress());
    Log.log(5, "MCGSAction", "addDonorDetails", " Name " + donorDetail.getName());
    Log.log(5, "MCGSAction", "addDonorDetails", " corpus contribution " + donorDetail.getCorpusContributionAmt());
    Log.log(5, "MCGSAction", "addDonorDetails", " contribution date " + donorDetail.getCorpusContributionDate());

    mcgsProcessor.addDonorDetails(donorDetail, user);

    dynaForm.initialize(mapping);

    Log.log(4, "MCGSAction", "addDonorDetails", "Exited");

    request.setAttribute("message", "Donor Details Added Successfully");

    return mapping.findForward("success");
  }

  public ActionForward showSSIMemberDetails(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response)
    throws Exception
  {
    Log.log(4, "MCGSAction", "showSSIMemberDetails", "Entered");

    Administrator admin = new Administrator();
    ArrayList states = admin.getAllStates();

    ArrayList industryNatures = admin.getAllIndustryNature();

    ArrayList socialCategories = admin.getAllSocialCategories();
    MCGSProcessor processor = new MCGSProcessor();

    ArrayList tempMcgfs = processor.getAllMCGFs();

    ArrayList mcgfs = new ArrayList();

    for (int i = 0; i < tempMcgfs.size(); i++)
    {
      MLIInfo member = (MLIInfo)tempMcgfs.get(i);
      String text = "(" + member.getBankId() + member.getZoneId() + member.getBranchId() + "" + ")" + member.getShortName() + "," + member.getBankName();

      mcgfs.add(text);
    }

    DynaActionForm dynaForm = (DynaActionForm)form;
    dynaForm.initialize(mapping);
    dynaForm.set("states", states);
    dynaForm.set("industryNatures", industryNatures);
    dynaForm.set("socialCategories", socialCategories);
    dynaForm.set("mcgfs", mcgfs);

    Log.log(4, "MCGSAction", "showSSIMemberDetails", "Exited");

    return mapping.findForward("success");
  }

  public ActionForward getIndustrySectors(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response)
    throws Exception
  {
    Log.log(4, "MCGSAction", "getIndustrySectors", "Entered");

    DynaActionForm dynaForm = (DynaActionForm)form;

    String industryNature = (String)dynaForm.get("industryNature");

    Log.log(5, "MCGSAction", "getIndustrySectors", "industryNature " + industryNature);

    Administrator admin = new Administrator();
    ArrayList industrySectors = new ArrayList();
    if (!industryNature.equals(""))
    {
      industrySectors = admin.getIndustrySectors(industryNature);
    }
    else {
      industrySectors.clear();
    }

    dynaForm.set("industrySectors", industrySectors);

    request.setAttribute("MCGS_ACTION_FLAG", "2");

    Log.log(4, "MCGSAction", "getIndustrySectors", "Exited");

    return mapping.findForward("success");
  }

  public ActionForward addSSIMemberDetails(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response)
    throws Exception
  {
    Log.log(4, "MCGSAction", "addSSIMemberDetails", "Entered");

    DynaActionForm dynaForm = (DynaActionForm)form;

    Log.log(4, "MCGSAction", "addSSIMemberDetails", "MCGF Name " + dynaForm.get("mcgf"));

    BorrowerDetails borrowerDetails = new BorrowerDetails();

    BeanUtils.populate(borrowerDetails, dynaForm.getMap());

    Log.log(5, "MCGSAction", "addSSIMemberDetails", "Ac no" + borrowerDetails.getAcNo());
    Log.log(5, "MCGSAction", "addSSIMemberDetails", "Assisted by bank " + borrowerDetails.getAssistedByBank());
    Log.log(5, "MCGSAction", "addSSIMemberDetails", "NPA " + borrowerDetails.getNpa());
    Log.log(5, "MCGSAction", "addSSIMemberDetails", "Os Amount " + borrowerDetails.getOsAmt());
    Log.log(5, "MCGSAction", "addSSIMemberDetails", "Previously covered " + borrowerDetails.getPreviouslyCovered());

    SSIDetails ssiDetails = new SSIDetails();

    BeanUtils.populate(ssiDetails, dynaForm.getMap());

    if ((ssiDetails.getDistrict() == null) || (ssiDetails.getDistrict().equals("")) || (ssiDetails.getDistrict().equals("Others")))
    {
      Log.log(5, "MCGSAction", "addSSIMemberDetails", "Original Dt is  " + ssiDetails.getDistrict());

      String district = (String)dynaForm.get("districtOthers");

      ssiDetails.setDistrict(district);
    }

    String idType = (String)dynaForm.get("idType");

    Log.log(5, "MCGSAction", "addSSIMemberDetails", "idType is  " + idType);

    if ((idType != null) && (!idType.equals("none")))
    {
      Log.log(5, "MCGSAction", "addSSIMemberDetails", "idType is  " + dynaForm.get("idTypeOther"));

      ssiDetails.setCgbid((String)dynaForm.get("idTypeOther"));
    }

    if ((ssiDetails.getConstitution() == null) || (ssiDetails.getConstitution().equals("")) || (ssiDetails.getConstitution().equals("Others")))
    {
      Log.log(5, "MCGSAction", "addSSIMemberDetails", "Constituition is  " + ssiDetails.getConstitution());

      ssiDetails.setConstitution((String)dynaForm.get("constitutionOther"));
    }

    if ((ssiDetails.getCpLegalID() == null) || (ssiDetails.getCpLegalID().equals("")) || (ssiDetails.getCpLegalID().equals("Others")))
    {
      Log.log(5, "MCGSAction", "addSSIMemberDetails", "Legal Id is " + ssiDetails.getCpLegalID());

      ssiDetails.setCpLegalID((String)dynaForm.get("otherCpLegalID"));
    }

    borrowerDetails.setSsiDetails(ssiDetails);

    Log.log(5, "MCGSAction", "addSSIMemberDetails", "Activity Type " + ssiDetails.getActivityType());
    Log.log(5, "MCGSAction", "addSSIMemberDetails", "Address " + ssiDetails.getAddress());
    Log.log(5, "MCGSAction", "addSSIMemberDetails", "Borrower Ref No " + ssiDetails.getBorrowerRefNo());
    Log.log(5, "MCGSAction", "addSSIMemberDetails", "CGBID " + ssiDetails.getCgbid());
    Log.log(5, "MCGSAction", "addSSIMemberDetails", "city " + ssiDetails.getCity());
    Log.log(5, "MCGSAction", "addSSIMemberDetails", "Constituition " + ssiDetails.getConstitution());
    Log.log(5, "MCGSAction", "addSSIMemberDetails", "Constituition other " + ssiDetails.getConstitutionOther());
    Log.log(5, "MCGSAction", "addSSIMemberDetails", "First name " + ssiDetails.getCpFirstName());
    Log.log(5, "MCGSAction", "addSSIMemberDetails", "Gender " + ssiDetails.getCpGender());
    Log.log(5, "MCGSAction", "addSSIMemberDetails", "ITPAN " + ssiDetails.getCpITPAN());
    Log.log(5, "MCGSAction", "addSSIMemberDetails", "Last name " + ssiDetails.getCpLastName());

    Log.log(5, "MCGSAction", "addSSIMemberDetails", "Legal Id " + ssiDetails.getCpLegalID());
    Log.log(5, "MCGSAction", "addSSIMemberDetails", "Legal Id Value " + ssiDetails.getCpLegalIdValue());
    Log.log(5, "MCGSAction", "addSSIMemberDetails", "Middle Name " + ssiDetails.getCpMiddleName());
    Log.log(5, "MCGSAction", "addSSIMemberDetails", "Title " + ssiDetails.getCpTitle());
    Log.log(5, "MCGSAction", "addSSIMemberDetails", "District " + ssiDetails.getDistrict());
    Log.log(5, "MCGSAction", "addSSIMemberDetails", "No of employees " + ssiDetails.getEmployeeNos());
    Log.log(5, "MCGSAction", "addSSIMemberDetails", "First ITPAN " + ssiDetails.getFirstItpan());
    Log.log(5, "MCGSAction", "addSSIMemberDetails", "First name " + ssiDetails.getFirstName());
    Log.log(5, "MCGSAction", "addSSIMemberDetails", "Industry Nature " + ssiDetails.getIndustryNature());
    Log.log(5, "MCGSAction", "addSSIMemberDetails", "Industry sector " + ssiDetails.getIndustrySector());
    Log.log(5, "MCGSAction", "addSSIMemberDetails", "Pin Code " + ssiDetails.getPincode());

    Log.log(5, "MCGSAction", "addSSIMemberDetails", "Project exports " + ssiDetails.getProjectedExports());
    Log.log(5, "MCGSAction", "addSSIMemberDetails", "Turn over " + ssiDetails.getProjectedSalesTurnover());
    Log.log(5, "MCGSAction", "addSSIMemberDetails", "Reg No " + ssiDetails.getRegNo());
    Log.log(5, "MCGSAction", "addSSIMemberDetails", "Second ITPAN " + ssiDetails.getSecondItpan());
    Log.log(5, "MCGSAction", "addSSIMemberDetails", "second name " + ssiDetails.getSecondName());
    Log.log(5, "MCGSAction", "addSSIMemberDetails", "SSI ITPAN " + ssiDetails.getSsiITPan());
    Log.log(5, "MCGSAction", "addSSIMemberDetails", "SSI Name " + ssiDetails.getSsiName());
    Log.log(5, "MCGSAction", "addSSIMemberDetails", "SSI type " + ssiDetails.getSsiType());
    Log.log(5, "MCGSAction", "addSSIMemberDetails", "State " + ssiDetails.getState());
    Log.log(5, "MCGSAction", "addSSIMemberDetails", "Second DOB " + ssiDetails.getSecondDOB());
    Log.log(5, "MCGSAction", "addSSIMemberDetails", "Third ITPAN " + ssiDetails.getThirdItpan());
    Log.log(5, "MCGSAction", "addSSIMemberDetails", "Third Name " + ssiDetails.getThirdName());

    Log.log(5, "MCGSAction", "addSSIMemberDetails", "Commencement Date " + ssiDetails.getCommencementDate());
    Log.log(5, "MCGSAction", "addSSIMemberDetails", "CP DOB " + ssiDetails.getCpDOB());
    Log.log(5, "MCGSAction", "addSSIMemberDetails", "First DOB " + ssiDetails.getFirstDOB());
    Log.log(5, "MCGSAction", "addSSIMemberDetails", "Third DOB " + ssiDetails.getThirdDOB());

    Log.log(5, "MCGSAction", "addSSIMemberDetails", "Corpus Contribution Amt " + ssiDetails.getCorpusContributionAmt());
    Log.log(5, "MCGSAction", "addSSIMemberDetails", "Corpus Contribution Date " + ssiDetails.getCorpusContributionDate());

    Log.log(5, "MCGSAction", "addSSIMemberDetails", "Display Default list " + ssiDetails.getDisplayDefaultersList());
    Log.log(5, "MCGSAction", "addSSIMemberDetails", "social category " + ssiDetails.getSocialCategory());

    MCGSProcessor mcgsProcessor = new MCGSProcessor();

    String memberId = ((String)dynaForm.get("mcgf")).substring(1, 13);

    User user = getUserInformation(request);

    mcgsProcessor.addSSIMembers(borrowerDetails, memberId, user);

    dynaForm.initialize(mapping);
    Log.log(4, "MCGSAction", "addSSIMemberDetails", "Exited");

    request.setAttribute("message", "SSI Members Added Successfully");

    return mapping.findForward("success");
  }

  public ActionForward showMCGFDefault(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response)
    throws Exception
  {
    Log.log(4, "MCGSAction", "showMCGFDefault", "Entered");

    MCGSProcessor mcgsProcessor = new MCGSProcessor();
    ArrayList activeMCGFs = mcgsProcessor.getAllMCGFs();

    ArrayList activeMCGFList = new ArrayList();

    for (int i = 0; i < activeMCGFs.size(); i++)
    {
      MLIInfo mliInfo = (MLIInfo)activeMCGFs.get(i);

      String id = mliInfo.getBankId() + mliInfo.getZoneId() + mliInfo.getBranchId();
      id = id + ", " + mliInfo.getBankName();

      activeMCGFList.add(id);
    }

    DynaActionForm dynaForm = (DynaActionForm)form;

    dynaForm.initialize(mapping);

    dynaForm.set("mcgfs", activeMCGFList);

    Log.log(4, "MCGSAction", "showMCGFDefault", "Exited");

    return mapping.findForward("success");
  }

  public ActionForward updateMCGFStatus(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response)
    throws Exception
  {
    Log.log(4, "MCGSAction", "updateMCGFStatus", "Entered");

    MCGSProcessor mcgsProcessor = new MCGSProcessor();

    DynaActionForm dynaForm = (DynaActionForm)form;

    String mcgfId = (String)dynaForm.get("mcgfName");

    String reason = (String)dynaForm.get("reason");

    User user = getUserInformation(request);

    mcgsProcessor.updateMCGFStatus(mcgfId, reason, user);

    Log.log(5, "MCGSAction", "updateMCGFStatus", "mcgfName,reason " + mcgfId + " " + reason);

    Log.log(4, "MCGSAction", "updateMCGFStatus", "Exited");

    request.setAttribute("message", "MCGF Status Updated Successfully");

    return mapping.findForward("success");
  }
}