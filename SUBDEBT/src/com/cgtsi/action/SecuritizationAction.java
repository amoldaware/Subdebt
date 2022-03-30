package com.cgtsi.action;

import com.cgtsi.admin.Administrator;
import com.cgtsi.admin.MenuOptions;
import com.cgtsi.admin.User;
import com.cgtsi.common.Log;
import com.cgtsi.registration.MLIInfo;
import com.cgtsi.registration.Registration;
import com.cgtsi.securitization.Investor;
import com.cgtsi.securitization.LoanPool;
import com.cgtsi.securitization.SecuritizationProcessor;
import com.cgtsi.securitization.SelectCriteria;
import com.cgtsi.securitization.StateWise;
import java.util.ArrayList;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import org.apache.commons.beanutils.BeanUtils;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.action.DynaActionForm;

public class SecuritizationAction extends BaseAction
{
  public ActionForward showSelectQuery(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response)
    throws Exception
  {
    Log.log(4, "SecuritizationAction", "showSelectQuery", "Entered");

    DynaActionForm dynaForm = (DynaActionForm)form;

    HttpSession session = request.getSession(false);

    session.removeAttribute("LOAN_POOL_ID");

    dynaForm.initialize(mapping);

    Registration registration = new Registration();

    ArrayList mlis = registration.getAllMLIs();

    ArrayList allMembers = new ArrayList(mlis.size());

    for (int i = 0; i < mlis.size(); i++)
    {
      MLIInfo mliInfo = (MLIInfo)mlis.get(i);
      allMembers.add(mliInfo.getBankId() + mliInfo.getZoneId() + mliInfo.getBranchId() + "(" + mliInfo.getShortName() + "," + mliInfo.getBankName() + ")");

      Log.log(5, "SecuritizationAction", "showSelectQuery", "MLI at " + i + " is " + mliInfo.getShortName() + "," + mliInfo.getBankName());
    }
    dynaForm.set("allMembers", allMembers);

    Administrator admin = new Administrator();

    ArrayList states = admin.getAllStates();

    dynaForm.set("allStates", states);

    ArrayList industrySectors = admin.getAllIndustrySectors();

    dynaForm.set("allSectors", industrySectors);

    Log.log(4, "SecuritizationAction", "showSelectQuery", "Exited");

    return mapping.findForward("success");
  }

  public ActionForward getHomogenousLoans(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response)
    throws Exception
  {
    Log.log(4, "SecuritizationAction", "getHomogenousLoans", "Entered");

    DynaActionForm dynaForm = (DynaActionForm)form;

    String[] mlis = (String[])dynaForm.get("mlis");

    Log.log(5, "SecuritizationAction", "getHomogenousLoans", "mlis " + mlis);
    if (mlis != null)
    {
      Log.log(5, "SecuritizationAction", "getHomogenousLoans", "mlis length " + mlis.length);

      for (int i = 0; i < mlis.length; i++)
      {
        Log.log(5, "SecuritizationAction", "getHomogenousLoans", "mli at " + i + " is " + mlis[i]);
      }
    }

    String[] states = (String[])dynaForm.get("states");

    Log.log(5, "SecuritizationAction", "getHomogenousLoans", "states " + states);

    if (states != null)
    {
      Log.log(5, "SecuritizationAction", "getHomogenousLoans", "states length " + states.length);

      for (int i = 0; i < states.length; i++)
      {
        Log.log(5, "SecuritizationAction", "getHomogenousLoans", "states at " + i + " is " + states[i]);
      }
    }

    String[] sectors = (String[])dynaForm.get("sectors");

    Log.log(5, "SecuritizationAction", "getHomogenousLoans", "sectors " + sectors);

    if (sectors != null)
    {
      Log.log(5, "SecuritizationAction", "getHomogenousLoans", "sectors length " + sectors.length);

      for (int i = 0; i < sectors.length; i++)
      {
        Log.log(5, "SecuritizationAction", "getHomogenousLoans", "sectors at " + i + " is " + sectors[i]);
      }
    }

    SelectCriteria selectCriteria = new SelectCriteria();

    BeanUtils.populate(selectCriteria, dynaForm.getMap());

    boolean isMliAll = false;
    if (mlis != null)
    {
      Log.log(5, "SecuritizationAction", "getHomogenousLoans", "mlis length " + mlis.length);

      for (int i = 0; i < mlis.length; i++)
      {
        Log.log(5, "SecuritizationAction", "getHomogenousLoans", "mli at " + i + " is " + mlis[i]);

        if (mlis[i].equals("All"))
        {
          isMliAll = true;
          break;
        }
      }
    }

    Log.log(5, "SecuritizationAction", "getHomogenousLoans", "states " + states);

    boolean isStateAll = false;

    if (states != null)
    {
      Log.log(5, "SecuritizationAction", "getHomogenousLoans", "states length " + states.length);

      for (int i = 0; i < states.length; i++)
      {
        Log.log(5, "SecuritizationAction", "getHomogenousLoans", "states at " + i + " is " + states[i]);

        if (states[i].equals("All"))
        {
          isStateAll = true;
          break;
        }
      }
    }

    Log.log(5, "SecuritizationAction", "getHomogenousLoans", "sectors " + sectors);

    boolean isSectorAll = false;

    if (sectors != null)
    {
      Log.log(5, "SecuritizationAction", "getHomogenousLoans", "sectors length " + sectors.length);

      for (int i = 0; i < sectors.length; i++)
      {
        Log.log(5, "SecuritizationAction", "getHomogenousLoans", "sectors at " + i + " is " + sectors[i]);
        if (sectors[i].equals("All"))
        {
          isSectorAll = true;
          break;
        }
      }
    }

    if (isMliAll)
    {
      ArrayList allMembers = (ArrayList)dynaForm.get("allMembers");
      String[] allMemArray = new String[allMembers.size()];

      for (int i = 0; i < allMembers.size(); i++)
      {
        allMemArray[i] = ((String)allMembers.get(i)).substring(0, 12);
        Log.log(5, "SecuritizationAction", "getHomogenousLoans", "members... at " + i + " is " + allMemArray[i]);
      }

      selectCriteria.setMlis(allMemArray);
    }

    if (isStateAll)
    {
      ArrayList allStates = (ArrayList)dynaForm.get("allStates");
      String[] allStatesArray = new String[allStates.size()];

      for (int i = 0; i < allStates.size(); i++)
      {
        allStatesArray[i] = ((String)allStates.get(i));
      }

      selectCriteria.setStates(allStatesArray);
    }

    if (isSectorAll)
    {
      ArrayList allSectors = (ArrayList)dynaForm.get("allSectors");
      String[] allSectorsArray = new String[allSectors.size()];

      for (int i = 0; i < allSectors.size(); i++)
      {
        allSectorsArray[i] = ((String)allSectors.get(i));
      }

      selectCriteria.setSectors(allSectorsArray);
    }

    Log.log(5, "SecuritizationAction", "getHomogenousLoans", "Loan tenure " + selectCriteria.getTenure());
    Log.log(5, "SecuritizationAction", "getHomogenousLoans", "Effective From " + selectCriteria.getEffectiveDate());
    Log.log(5, "SecuritizationAction", "getHomogenousLoans", "Loan type " + selectCriteria.getLoanType());
    Log.log(5, "SecuritizationAction", "getHomogenousLoans", "Interest rate1 " + selectCriteria.getInterestRate());
    Log.log(5, "SecuritizationAction", "getHomogenousLoans", "Interest rate2 " + selectCriteria.getNextInterestRate());
    Log.log(5, "SecuritizationAction", "getHomogenousLoans", "Interest type " + selectCriteria.getTypeOfInterest());
    Log.log(5, "SecuritizationAction", "getHomogenousLoans", "Loan Size1 " + selectCriteria.getLoanSize());
    Log.log(5, "SecuritizationAction", "getHomogenousLoans", "Loan Size2 " + selectCriteria.getNextLoanSize());

    Log.log(5, "SecuritizationAction", "getHomogenousLoans", "Track Record " + selectCriteria.getTrackRecord());

    SecuritizationProcessor secProcessor = new SecuritizationProcessor();

    ArrayList homogeneousLoans = secProcessor.getHomogenousLoans(selectCriteria);

    dynaForm.set("homogeneousLoans", homogeneousLoans);

    Log.log(4, "SecuritizationAction", "getHomogenousLoans", "Exited");

    return mapping.findForward("success");
  }

  public ActionForward createLoanPool(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response)
    throws Exception
  {
    Log.log(4, "SecuritizationAction", "createLoanPool", "Entered");

    DynaActionForm dynaForm = (DynaActionForm)form;
    String[] mlis = (String[])dynaForm.get("mlis");

    boolean isMliAll = false;
    if (mlis != null)
    {
      Log.log(5, "SecuritizationAction", "getHomogenousLoans", "mlis length " + mlis.length);

      for (int i = 0; i < mlis.length; i++)
      {
        Log.log(5, "SecuritizationAction", "getHomogenousLoans", "mli at " + i + " is " + mlis[i]);

        if (mlis[i].equals("All"))
        {
          isMliAll = true;
          break;
        }
      }
    }

    String[] states = (String[])dynaForm.get("states");

    Log.log(5, "SecuritizationAction", "getHomogenousLoans", "states " + states);

    boolean isStateAll = false;

    if (states != null)
    {
      Log.log(5, "SecuritizationAction", "getHomogenousLoans", "states length " + states.length);

      for (int i = 0; i < states.length; i++)
      {
        Log.log(5, "SecuritizationAction", "getHomogenousLoans", "states at " + i + " is " + states[i]);

        if (states[i].equals("All"))
        {
          isStateAll = true;
          break;
        }
      }
    }

    String[] sectors = (String[])dynaForm.get("sectors");

    Log.log(5, "SecuritizationAction", "getHomogenousLoans", "sectors " + sectors);

    boolean isSectorAll = false;

    if (sectors != null)
    {
      Log.log(5, "SecuritizationAction", "getHomogenousLoans", "sectors length " + sectors.length);

      for (int i = 0; i < sectors.length; i++)
      {
        Log.log(5, "SecuritizationAction", "getHomogenousLoans", "sectors at " + i + " is " + sectors[i]);
        if (sectors[i].equals("All"))
        {
          isSectorAll = true;
          break;
        }
      }
    }

    SelectCriteria selectCriteria = new SelectCriteria();

    BeanUtils.populate(selectCriteria, dynaForm.getMap());

    if (isMliAll)
    {
      ArrayList allMembers = (ArrayList)dynaForm.get("allMembers");
      String[] allMemArray = new String[allMembers.size()];

      for (int i = 0; i < allMembers.size(); i++)
      {
        allMemArray[i] = ((String)allMembers.get(i));
        Log.log(5, "SecuritizationAction", "getHomogenousLoans", "members... at " + i + " is " + allMemArray[i]);
      }

      selectCriteria.setMlis(allMemArray);
    }

    if (isStateAll)
    {
      ArrayList allStates = (ArrayList)dynaForm.get("allStates");
      String[] allStatesArray = new String[allStates.size()];

      for (int i = 0; i < allStates.size(); i++)
      {
        allStatesArray[i] = ((String)allStates.get(i));
      }

      selectCriteria.setStates(allStatesArray);
    }

    if (isSectorAll)
    {
      ArrayList allSectors = (ArrayList)dynaForm.get("allSectors");
      String[] allSectorsArray = new String[allSectors.size()];

      for (int i = 0; i < allSectors.size(); i++)
      {
        allSectorsArray[i] = ((String)allSectors.get(i));
      }

      selectCriteria.setSectors(allSectorsArray);
    }
    LoanPool loanPool = new LoanPool();

    BeanUtils.populate(loanPool, dynaForm.getMap());

    double poolRate = ((Double)dynaForm.get("poolInterestRate")).doubleValue();

    loanPool.setInterestRate(poolRate);

    if (selectCriteria.getMlis() != null)
    {
      Log.log(5, "SecuritizationAction", "createLoanPool", "mlis length " + selectCriteria.getMlis().length);

      for (int i = 0; i < selectCriteria.getMlis().length; i++)
      {
        Log.log(5, "SecuritizationAction", "createLoanPool", "mli at " + i + " is " + selectCriteria.getMlis()[i]);
      }

    }

    Log.log(5, "SecuritizationAction", "createLoanPool", "states " + selectCriteria.getStates());

    if (selectCriteria.getStates() != null)
    {
      Log.log(5, "SecuritizationAction", "createLoanPool", "states length " + selectCriteria.getStates().length);

      for (int i = 0; i < selectCriteria.getStates().length; i++)
      {
        Log.log(5, "SecuritizationAction", "createLoanPool", "states at " + i + " is " + selectCriteria.getStates()[i]);
      }

    }

    Log.log(5, "SecuritizationAction", "createLoanPool", "sectors " + selectCriteria.getSectors());

    if (selectCriteria.getSectors() != null)
    {
      Log.log(5, "SecuritizationAction", "createLoanPool", "sectors length " + selectCriteria.getSectors().length);

      for (int i = 0; i < selectCriteria.getSectors().length; i++)
      {
        Log.log(5, "SecuritizationAction", "createLoanPool", "sectors at " + i + " is " + selectCriteria.getSectors()[i]);
      }
    }

    Log.log(5, "SecuritizationAction", "createLoanPool", "interest rate is " + selectCriteria.getInterestRate());

    Log.log(5, "SecuritizationAction", "createLoanPool", "interest rate is " + selectCriteria.getInterestRate());
    Log.log(5, "SecuritizationAction", "createLoanPool", "loan size is " + selectCriteria.getLoanSize());
    Log.log(5, "SecuritizationAction", "createLoanPool", "loan type is " + selectCriteria.getLoanType());
    Log.log(5, "SecuritizationAction", "createLoanPool", "tenure is " + selectCriteria.getTenure());
    Log.log(5, "SecuritizationAction", "createLoanPool", "effective date is " + selectCriteria.getEffectiveDate());

    Log.log(5, "SecuritizationAction", "createLoanPool", "Track record is " + selectCriteria.getTrackRecord());
    Log.log(5, "SecuritizationAction", "createLoanPool", "Type of interest is " + selectCriteria.getTypeOfInterest());

    Log.log(5, "SecuritizationAction", "createLoanPool", "Loan pool details...");

    Log.log(5, "SecuritizationAction", "createLoanPool", "Amount securitized " + loanPool.getAmountSecuritized());
    Log.log(5, "SecuritizationAction", "createLoanPool", "Interest rate " + loanPool.getInterestRate());
    Log.log(5, "SecuritizationAction", "createLoanPool", "Pool Name " + loanPool.getLoanPoolName());
    Log.log(5, "SecuritizationAction", "createLoanPool", "Rating " + loanPool.getRating());
    Log.log(5, "SecuritizationAction", "createLoanPool", "Rating Agency Name " + loanPool.getRatingAgencyName());
    Log.log(5, "SecuritizationAction", "createLoanPool", "SPV Name " + loanPool.getSpvName());
    Log.log(5, "SecuritizationAction", "createLoanPool", "Sec. Issue date Name " + loanPool.getSecuritizationIssueDate());

    User user = getUserInformation(request);

    SecuritizationProcessor processor = new SecuritizationProcessor();

    int poolId = processor.createLoanPool(loanPool, selectCriteria, user);

    Log.log(5, "SecuritizationAction", "createLoanPool", "pool id " + poolId);

    HttpSession session = request.getSession(false);

    session.setAttribute("LOAN_POOL_ID", String.valueOf(poolId));

    String message = "Loan pool created succesfully. Loan pool id is " + poolId;

    request.setAttribute("message", message);
    Log.log(4, "SecuritizationAction", "createLoanPool", "Exited");

    return mapping.findForward("success");
  }

  public ActionForward showInvestor(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response)
    throws Exception
  {
    Log.log(4, "SecuritizationAction", "showInvestor", "Entered");

    SecuritizationProcessor processor = new SecuritizationProcessor();
    ArrayList poolNames = processor.getLoanPoolNames();
    DynaActionForm dynaForm = (DynaActionForm)form;
    dynaForm.initialize(mapping);
    dynaForm.set("poolNames", poolNames);

    Log.log(4, "SecuritizationAction", "showInvestor", "Exited");

    return mapping.findForward("success");
  }

  public ActionForward addInvestor(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response)
    throws Exception
  {
    Log.log(4, "SecuritizationAction", "addInvestor", "Entered");

    DynaActionForm dynaForm = (DynaActionForm)form;

    HttpSession session = request.getSession(false);

    Investor investor = new Investor();

    BeanUtils.populate(investor, dynaForm.getMap());

    Log.log(5, "SecuritizationAction", "addInvestor", "Investor name " + investor.getInvestorName());

    Log.log(5, "SecuritizationAction", "addInvestor", "Investor name " + investor.getInvestedAmount());
    Log.log(5, "SecuritizationAction", "addInvestor", "Loan Pool name " + investor.getLoanPoolName());
    String loanPoolName = investor.getLoanPoolName();
    int index = loanPoolName.indexOf(")");

    int poolId = Integer.parseInt(loanPoolName.substring(0, index));

    Log.log(5, "SecuritizationAction", "addInvestor", "pool Id " + poolId);

    SecuritizationProcessor processor = new SecuritizationProcessor();

    processor.addInvestor(poolId, investor);

    dynaForm.initialize(mapping);

    Log.log(4, "SecuritizationAction", "addInvestor", "Exited");
    String message = "Investor added successfully. <br> <a href=" + MenuOptions.getMenuAction("SC_ADD_INVESTOR") + ">Add more Investors!</a>";

    request.setAttribute("message", message);

    return mapping.findForward("success");
  }

  public ActionForward getStateWiseLoans(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response)
    throws Exception
  {
    Log.log(4, "SecuritizationAction", "getStateWiseLoans", "Entered");

    DynaActionForm dynaForm = (DynaActionForm)form;

    String sector = request.getParameter("sector");

    String state = request.getParameter("state");

    Log.log(5, "SecuritizationAction", "getStateWiseLoans", " sector,state " + sector + " " + state);

    SelectCriteria selectCriteria = new SelectCriteria();

    BeanUtils.populate(selectCriteria, dynaForm.getMap());

    String[] mlis = (String[])dynaForm.get("mlis");

    boolean isMliAll = false;
    if (mlis != null)
    {
      Log.log(5, "SecuritizationAction", "getHomogenousLoans", "mlis length " + mlis.length);

      for (int i = 0; i < mlis.length; i++)
      {
        Log.log(5, "SecuritizationAction", "getHomogenousLoans", "mli at " + i + " is " + mlis[i]);

        if (mlis[i].equals("All"))
        {
          isMliAll = true;
          break;
        }
      }
    }

    if (isMliAll)
    {
      ArrayList allMembers = (ArrayList)dynaForm.get("allMembers");
      String[] allMemArray = new String[allMembers.size()];

      for (int i = 0; i < allMembers.size(); i++)
      {
        allMemArray[i] = ((String)allMembers.get(i));
        Log.log(5, "SecuritizationAction", "getHomogenousLoans", "members... at " + i + " is " + allMemArray[i]);
      }

      selectCriteria.setMlis(allMemArray);
    }

    Log.log(5, "SecuritizationAction", "getStateWiseLoans", "states ... " + selectCriteria.getStates());

    SecuritizationProcessor securitizationProcessor = new SecuritizationProcessor();

    StateWise stateWise = securitizationProcessor.getStateWiseLoans(sector, state, selectCriteria);

    dynaForm.set("stateWise", stateWise);

    Log.log(4, "SecuritizationAction", "getStateWiseLoans", "Exited");

    return mapping.findForward("success");
  }

  public ActionForward showPoolDetails(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response)
    throws Exception
  {
    Log.log(4, "SecuritizationAction", "showPoolDetails", "Entered");

    DynaActionForm dynaForm = (DynaActionForm)form;
    LoanPool loanPool = new LoanPool();

    BeanUtils.copyProperties(dynaForm, loanPool);

    Log.log(4, "SecuritizationAction", "showPoolDetails", "Exited");

    return mapping.findForward("success");
  }
}