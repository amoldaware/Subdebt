package com.cgtsi.actionform;

import com.cgtsi.common.Log;
import com.cgtsi.investmentfund.FundTransferDetail;
import com.cgtsi.investmentfund.InvestmentMaturityDetails;
import com.cgtsi.investmentfund.MiscReceipts;
import java.text.ParsePosition;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;
import java.util.TreeMap;
import javax.servlet.http.HttpServletRequest;
import org.apache.struts.action.ActionError;
import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.validator.ValidatorActionForm;

public class InvestmentForm extends ValidatorActionForm
{
  private Map heads = new HashMap();
  private Map subHeads = new HashMap();

  private Map shortHeads = new HashMap();
  private Map shortSubHeads = new HashMap();

  private Map headsToRender = new HashMap();
  private Map subHeadsToRender = new HashMap();

  private String annualOrShortTerm = "";
  private String inflowOrOutflow = "";
  private String month = "";
  private String year = "";
  private String annualFromDate = "";
  private String annualToDate = "";
  private String startDate = "";
  private String endDate = "";
  private String dateOfFlow = "";
  private Date miscReceiptsDate;
  private int counter;
  private Date statementDate;
  private Map fundTransfers = new TreeMap();

  private Map miscReceipts = new HashMap();

  private final Map values = new HashMap();

  private ArrayList bnkChqDetails = new ArrayList();

  private ArrayList unutilizedChqDetails = new ArrayList();

  private String test = "";
  private String bankBranchName;
  private Map chequeId = new TreeMap();
  private Map startNo = new TreeMap();
  private Map endNo = new TreeMap();
  private Map bankId = new TreeMap();

  private Map cancelledChq = new TreeMap();

  private Map tempStartNo = new TreeMap();
  private Map tempEndNo = new TreeMap();
  private Date valueDate;
  private Map invstMaturingDetails = new TreeMap();

  private Map planInvMainDetails = new TreeMap();
  private ArrayList planInvMatDetails = new ArrayList();
  private ArrayList planInvFTDetails = new ArrayList();
  private ArrayList planInvProvisionDetails = new ArrayList();

  private Map provisionRemarks = new TreeMap();
  private Date dateOfTheDocument2;
  private Date dateOfTheDocument3;
  private ArrayList chequeArray = new ArrayList();
  private String chqId = "";

  public void setValue(String key, Object value)
  {
    this.values.put(key, value);
  }

  public Object getValue(String key)
  {
    return this.values.get(key);
  }

  public Map getValues()
  {
    return this.values;
  }

  public void setHead(String key, Object value)
  {
    this.heads.put(key, value);
  }

  public Object getHead(String key)
  {
    return this.heads.get(key);
  }

  public Map getHeads()
  {
    return this.heads;
  }

  public void setSubHead(String key, Object value)
  {
    this.subHeads.put(key, value);
  }

  public Object getSubHead(String key)
  {
    return this.subHeads.get(key);
  }

  public Map getSubHeads()
  {
    return this.subHeads;
  }

  public void setHeadsToRender(String key, Object value)
  {
    this.headsToRender.put(key, value);
  }

  public Object getHeadsToRender(String key)
  {
    return this.headsToRender.get(key);
  }

  public Map getHeadsToRender()
  {
    return this.headsToRender;
  }

  public void setSubHeadsToRender(String key, Object value)
  {
    this.subHeadsToRender.put(key, value);
  }

  public Object getSubHeadsToRender(String key)
  {
    return this.subHeadsToRender.get(key);
  }

  public Map getSubHeadsToRender()
  {
    return this.subHeadsToRender;
  }

  public void setAnnualOrShortTerm(String aAnnualOrShortTerm)
  {
    this.annualOrShortTerm = aAnnualOrShortTerm;
  }

  public String getAnnualOrShortTerm()
  {
    return this.annualOrShortTerm;
  }

  public void setInflowOrOutflow(String aInflowOrOutflow)
  {
    this.inflowOrOutflow = aInflowOrOutflow;
  }

  public String getInflowOrOutflow()
  {
    return this.inflowOrOutflow;
  }

  public void setMonth(String aMonth)
  {
    this.month = aMonth;
  }

  public String getMonth()
  {
    return this.month;
  }

  public void setYear(String aYear)
  {
    this.year = aYear;
  }

  public String getYear()
  {
    return this.year;
  }

  public void setAnnualFromDate(String aAnnualFromDate)
  {
    this.annualFromDate = aAnnualFromDate;
  }

  public String getAnnualFromDate()
  {
    return this.annualFromDate;
  }

  public void setAnnualToDate(String aAnnualToDate)
  {
    this.annualToDate = aAnnualToDate;
  }

  public String getAnnualToDate()
  {
    return this.annualToDate;
  }

  public void setStartDate(String aStartDate)
  {
    this.startDate = aStartDate;
  }

  public String getStartDate()
  {
    return this.startDate;
  }

  public void setEndDate(String aEndDate)
  {
    this.endDate = aEndDate;
  }

  public String getEndDate()
  {
    return this.endDate;
  }

  public void setDateOfFlow(String aDateOfFlow)
  {
    this.dateOfFlow = aDateOfFlow;
  }

  public String getDateOfFlow()
  {
    return this.dateOfFlow;
  }

  public Object getHeadsMapped(String key) {
    return getHead(key);
  }

  public void setHeadsMapped(String key, Object value) {
    setHead(key, value);
  }

  public Object getSubHeadsMapped(String key)
  {
    return getSubHead(key);
  }

  public void setSubHeadsMapped(String key, Object value) {
    setSubHead(key, value);
  }

  public String getHeadsRam()
  {
    StringBuffer sb = new StringBuffer();
    if ((this.heads != null) && (!this.heads.isEmpty())) {
      Iterator it = this.heads.keySet().iterator();
      while (it.hasNext()) {
        String paramName = (String)it.next();

        sb.append(paramName);
      }

    }

    return sb.toString();
  }

  public String getSubHeadsRam()
  {
    StringBuffer sb = new StringBuffer();
    if ((this.subHeads != null) && (!this.subHeads.isEmpty())) {
      Iterator it = this.subHeads.keySet().iterator();
      while (it.hasNext()) {
        String paramName = (String)it.next();
        String paramValue = (String)this.subHeads.get(paramName);
        sb.append(paramName);
        sb.append(paramValue);
      }

    }

    return sb.toString();
  }

  public ActionErrors validate(ActionMapping mapping, HttpServletRequest request)
  {
    Log.log(4, "InvestmentForm", "validate", "Entered");

    ActionErrors errors = super.validate(mapping, request);

    if ((mapping.getPath().equals("/setBudgetInflowDetails")) || (mapping.getPath().equals("/setBudgetOutflowDetails")) || (mapping.getPath().equals("/setAnnualFundsInflowDetails")) || (mapping.getPath().equals("/setAnnualFundsOutflowDetails")))
    {
      Log.log(5, "InvestmentForm", "validate", "Validating set budget inflow details ");

      if (errors.isEmpty())
      {
        Log.log(5, "InvestmentForm", "validate", "No Errors from super class");

        boolean returnValue = false;
        boolean returnValue1 = false;

        if ((mapping.getPath().equals("/setAnnualFundsInflowDetails")) || (mapping.getPath().equals("/setAnnualFundsOutflowDetails")))
        {
          returnValue = checkValidDate(getDateOfFlow());
          returnValue1 = true;
        }
        else
        {
          returnValue = checkValidDate(getAnnualFromDate());

          returnValue1 = checkValidDate(getAnnualToDate());
        }

        if ((!returnValue) || (!returnValue1))
        {
          errors.add("org.apache.struts.action.GLOBAL_ERROR", new ActionError("InvalidYear"));
        }
      }

      Set headSet = this.heads.keySet();

      Iterator iterator = headSet.iterator();
      int counter = 0;
      while (iterator.hasNext())
      {
        String key = (String)iterator.next();

        String value = (String)this.heads.get(key);
        Log.log(5, "InvestmentForm", "validate", "Head key and value " + key + "," + value);

        if ((value == null) || (value.equals("")))
        {
          Log.log(5, "InvestmentForm", "validate", "value is null or empty ");
        }
        else
        {
          counter++;
        }
      }

      Set subHeadSet = this.subHeads.keySet();
      Iterator subHeadIterator = subHeadSet.iterator();

      while (subHeadIterator.hasNext())
      {
        String key = (String)subHeadIterator.next();

        String value = (String)this.subHeads.get(key);

        Log.log(5, "InvestmentForm", "validate", "Sub Head key and value " + key + "," + value);

        if ((value == null) || (value.equals("")))
        {
          Log.log(5, "InvestmentForm", "validate", "value is null or empty ");
        }
        else
        {
          Log.log(5, "InvestmentForm", "validate", "value are available. increment the counter");
          counter++;
        }
      }

      Log.log(5, "InvestmentForm", "validate", "Counter value " + counter);

      Log.log(5, "InvestmentForm", "validate", "size of heads and sub heads " + this.heads.size() + ", " + this.subHeads.size());

      if (counter == 0)
      {
        ActionError error = new ActionError("oneValueRequired");
        errors.add("org.apache.struts.action.GLOBAL_ERROR", error);
      }
    }
    else if ((mapping.getPath().equals("/setShortTermBudgetInflowDetails")) || (mapping.getPath().equals("/setShortTermBudgetOutflowDetails")))
    {
      Log.log(5, "InvestmentForm", "validate", "Validating set short term budget inflow details ");

      Set headSet = this.shortHeads.keySet();

      Iterator iterator = headSet.iterator();
      int counter = 0;
      while (iterator.hasNext())
      {
        String key = (String)iterator.next();

        String value = (String)this.shortHeads.get(key);
        Log.log(5, "InvestmentForm", "validate", "Head key and value " + key + "," + value);

        if ((value == null) || (value.equals("")))
        {
          Log.log(5, "InvestmentForm", "validate", "value is null or empty ");
        }
        else
        {
          counter++;
        }
      }

      Set subHeadSet = this.shortSubHeads.keySet();
      Iterator subHeadIterator = subHeadSet.iterator();

      while (subHeadIterator.hasNext())
      {
        String key = (String)subHeadIterator.next();

        String value = (String)this.shortSubHeads.get(key);

        Log.log(5, "InvestmentForm", "validate", "Sub Head key and value " + key + "," + value);

        if ((value == null) || (value.equals("")))
        {
          Log.log(5, "InvestmentForm", "validate", "value is null or empty ");
        }
        else
        {
          Log.log(5, "InvestmentForm", "validate", "value are available. increment the counter");
          counter++;
        }
      }

      Log.log(5, "InvestmentForm", "validate", "Counter value " + counter);

      Log.log(5, "InvestmentForm", "validate", "size of heads and sub heads " + this.heads.size() + ", " + this.subHeads.size());

      if (counter == 0)
      {
        ActionError error = new ActionError("oneValueRequired");
        errors.add("org.apache.struts.action.GLOBAL_ERROR", error);
      }
    }
    else if ((mapping.getPath().equals("/addMoreMiscReceipts")) || (mapping.getPath().equals("/insertMiscReceipts")))
    {
      Log.log(5, "InvestmentForm", "validate", " Misc Receipts");
      Set miscReceiptsSet = this.miscReceipts.keySet();
      Iterator miscReceiptsIterator = miscReceiptsSet.iterator();
      boolean source = false;
      boolean instDate = false;
      boolean instNo = false;
      boolean amt = false;
      boolean receiptDate = false;

      while (miscReceiptsIterator.hasNext())
      {
        String key = (String)miscReceiptsIterator.next();
        Log.log(5, "InvestmentForm", "validate", " key " + key);

        MiscReceipts receipts = (MiscReceipts)this.miscReceipts.get(key);

        boolean sourceVal = true;
        boolean instDateVal = true;
        boolean instNoVal = true;
        boolean amtVal = true;
        boolean rectDateVal = true;

        if ((receipts.getSourceOfFund() == null) || (receipts.getSourceOfFund().equals("")))
        {
          sourceVal = false;
        }
        if ((receipts.getInstrumentDate() == null) || (receipts.getInstrumentDate().equals("")))
        {
          instDateVal = false;
        }
        if ((receipts.getInstrumentNo() == null) || (receipts.getInstrumentNo().equals("")))
        {
          instNoVal = false;
        }
        if ((receipts.getAmount() == null) || (receipts.getAmount().equals("")) || (Double.parseDouble(receipts.getAmount()) == 0))
        {
          amtVal = false;
        }
        if ((receipts.getDateOfReceipt() == null) || (receipts.getDateOfReceipt().equals("")))
        {
          rectDateVal = false;
        }

        if ((!source) && (!sourceVal))
        {
          ActionError error = new ActionError("errors.required", "Source of Fund");
          errors.add("org.apache.struts.action.GLOBAL_ERROR", error);
          source = true;
          Log.log(5, "InvestmentForm", "validate", " source is null ");
        }

        if ((!instDate) && (!instDateVal))
        {
          ActionError error = new ActionError("errors.required", "Instrument Date ");
          errors.add("org.apache.struts.action.GLOBAL_ERROR", error);
          Log.log(5, "InvestmentForm", "validate", " Instrument date is null ");
          instDate = true;
        }
        else if ((instDateVal) && (!instDate))
        {
          String instrumentDate = receipts.getInstrumentDate();

          if (instrumentDate.trim().length() < 10)
          {
            String[] errorStrs = new String[2];
            errorStrs[0] = "Instrument Date ";
            errorStrs[1] = "10";

            ActionError error = new ActionError("errors.minlength", errorStrs);

            errors.add("org.apache.struts.action.GLOBAL_ERROR", error);
            instDate = true;

            Log.log(5, "InvestmentForm", "validate", " length is less than zero");
          }
          else
          {
            SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");

            Date date = dateFormat.parse(instrumentDate, new ParsePosition(0));
            Log.log(5, "InvestmentForm", "validate", " date " + date);

            if (date == null)
            {
              ActionError error = new ActionError("errors.date", "Instrument Date");

              errors.add("org.apache.struts.action.GLOBAL_ERROR", error);
              instDate = true;
            }
            else if (date.after(this.miscReceiptsDate))
            {
              ActionError error = new ActionError("instDtGTReceiptDt");
              errors.add("org.apache.struts.action.GLOBAL_ERROR", error);
              instDate = true;
            }
          }
        }

        if ((!instNo) && (!instNoVal))
        {
          ActionError error = new ActionError("errors.required", "Instrument Number");
          errors.add("org.apache.struts.action.GLOBAL_ERROR", error);
          instNo = true;
          Log.log(5, "InvestmentForm", "validate", " inst no is null ");
        }

        if ((!amt) && (!amtVal))
        {
          ActionError error = new ActionError("errors.required", "Amount");
          errors.add("org.apache.struts.action.GLOBAL_ERROR", error);
          Log.log(5, "InvestmentForm", "validate", " Amount in Rs ");

          amt = true;
        }

        if ((!receiptDate) && (!rectDateVal))
        {
          ActionError error = new ActionError("errors.required", "Date of Receipt ");
          errors.add("org.apache.struts.action.GLOBAL_ERROR", error);
          Log.log(5, "InvestmentForm", "validate", " Instrument date is null ");
          receiptDate = true;
        }
        else if (rectDateVal)
        {
          String instrumentDate = receipts.getDateOfReceipt();

          if (instrumentDate.trim().length() < 10)
          {
            String[] errorStrs = new String[2];
            errorStrs[0] = "Date of Receipt";
            errorStrs[1] = "10";

            ActionError error = new ActionError("errors.minlength", errorStrs);

            errors.add("org.apache.struts.action.GLOBAL_ERROR", error);
            receiptDate = true;

            Log.log(5, "InvestmentForm", "validate", " length is less than zero");
          }
          else
          {
            SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");

            Date date = dateFormat.parse(instrumentDate, new ParsePosition(0));
            Log.log(5, "InvestmentForm", "validate", " date " + date);

            if (date == null)
            {
              ActionError error = new ActionError("errors.date", "Date of Receipt");

              errors.add("org.apache.struts.action.GLOBAL_ERROR", error);
              receiptDate = true;
            }
            else if (date.after(this.miscReceiptsDate))
            {
              ActionError error = new ActionError("miscDtGTReceiptDt");
              errors.add("org.apache.struts.action.GLOBAL_ERROR", error);
              receiptDate = true;
            }
          }

        }

        if ((!instDate) && (!receiptDate))
        {
          SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");

          String receiptDateStr = receipts.getDateOfReceipt();
          String instrumentDateStr = receipts.getInstrumentDate();

          if ((receiptDateStr != null) && (instrumentDateStr != null))
          {
            Date receiptDateDt = dateFormat.parse(receiptDateStr, new ParsePosition(0));
            Date instDateDt = dateFormat.parse(instrumentDateStr, new ParsePosition(0));

            Log.log(5, "InvestmentForm", "validate", " instdate " + instDateDt);
            Log.log(5, "InvestmentForm", "validate", " rectdate " + receiptDateDt);

            if ((instDateDt != null) && (receiptDateDt != null) && (instDateDt.after(receiptDateDt)))
            {
              ActionError actionMessage = new ActionError("receiptDtGTInstDt");
              errors.add("org.apache.struts.action.GLOBAL_ERROR", actionMessage);
              instDate = true;
              receiptDate = true;
            }

          }

        }

        if (((source) || (instDate) || (instNo) || (amt) || (receiptDate)) && ((!source) || (!instDate) || (!instNo) || (!amt) || (!receiptDate) || (receipts.getId() == 0)));
      }

      if ((mapping.getPath().equals("/insertMiscReceipts")) && (this.miscReceipts.size() == 1))
      {
        MiscReceipts misc = (MiscReceipts)this.miscReceipts.get("key-0");
        if ((misc.getId() == 0) && ((misc.getSourceOfFund() == null) || (misc.getSourceOfFund().equals(""))) && ((misc.getInstrumentNo() == null) || (misc.getInstrumentNo().equals(""))) && ((misc.getInstrumentDate() == null) || (misc.getInstrumentDate().toString().equals(""))) && ((misc.getDateOfReceipt() == null) || (misc.getDateOfReceipt().toString().equals(""))))
        {
          ActionError actionMessage = new ActionError("atleastOneReceipt");
          errors.add("org.apache.struts.action.GLOBAL_ERROR", actionMessage);
        }
      }
    }
    else if (mapping.getPath().equals("/updateFundTransfer"))
    {
      Log.log(5, "InvestmentForm", "validate", " Fund Transfer");
      Set ftSet = this.fundTransfers.keySet();
      Iterator ftIterator = ftSet.iterator();
      boolean closeBalDate = false;
      boolean balanceStmt = false;
      boolean balanceUncleared = false;
      boolean amtCA = false;
      boolean remarks = false;

      while (ftIterator.hasNext())
      {
        String key = (String)ftIterator.next();
        Log.log(5, "InvestmentForm", "validate", " key " + key);

        FundTransferDetail ftDetail = (FundTransferDetail)this.fundTransfers.get(key);

        boolean closeBalDateVal = true;
        boolean balanceStmtVal = true;
        boolean balanceUnclearedVal = true;
        boolean amtCAVal = true;
        boolean remarksVal = true;

        if ((ftDetail.getClosingBalanceDate() == null) || (ftDetail.getClosingBalanceDate().equals("")))
        {
          closeBalDateVal = false;
        }
        if ((ftDetail.getBalanceAsPerStmt() == null) || (ftDetail.getBalanceAsPerStmt().equals("")) || (Double.parseDouble(ftDetail.getBalanceAsPerStmt()) == 0))
        {
          balanceStmtVal = false;
        }
        if ((ftDetail.getUnclearedBalance() == null) || (ftDetail.getUnclearedBalance().equals("")))
        {
          balanceUnclearedVal = false;
        }
        if ((ftDetail.getAmtCANotReflected() == null) || (ftDetail.getAmtCANotReflected().equals("")))
        {
          amtCAVal = false;
        }
        if ((ftDetail.getRemarks() == null) || (ftDetail.getRemarks().equals("")))
        {
          remarksVal = false;
        }

        if ((!closeBalDate) && ((ftDetail.getId() != 0) || ((ftDetail.getId() == 0) && ((balanceStmtVal) || (balanceUnclearedVal) || (amtCAVal) || (remarksVal)))) && (!closeBalDateVal))
        {
          ActionError error = new ActionError("errors.required", "Closing Balance Date");
          errors.add("org.apache.struts.action.GLOBAL_ERROR", error);
          closeBalDate = true;
          Log.log(5, "InvestmentForm", "validate", " Closing Balance Date is null ");
        }
        else if ((closeBalDateVal) && (!closeBalDate))
        {
          String clDate = ftDetail.getClosingBalanceDate();

          if (clDate.trim().length() < 10)
          {
            String[] errorStrs = new String[2];
            errorStrs[0] = "Closing Balance Date ";
            errorStrs[1] = "10";

            ActionError error = new ActionError("errors.minlength", errorStrs);

            errors.add("org.apache.struts.action.GLOBAL_ERROR", error);
            closeBalDate = true;

            Log.log(5, "InvestmentForm", "validate", " length is less than zero");
          }
          else
          {
            SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");

            Date date = dateFormat.parse(clDate, new ParsePosition(0));
            Log.log(5, "InvestmentForm", "validate", " date " + date);

            if (date == null)
            {
              ActionError error = new ActionError("errors.date", "Closing Balance Date");

              errors.add("org.apache.struts.action.GLOBAL_ERROR", error);
              closeBalDate = true;
            }
            else if (date.after(this.statementDate))
            {
              ActionError error = new ActionError("closingBalDtGTStmtDt");
              errors.add("org.apache.struts.action.GLOBAL_ERROR", error);
              closeBalDate = true;
            }
          }
        }

        if ((!balanceStmt) && ((ftDetail.getId() != 0) || ((ftDetail.getId() == 0) && ((closeBalDateVal) || (balanceUnclearedVal) || (amtCAVal) || (remarksVal)))) && (!balanceStmtVal))
        {
          ActionError error = new ActionError("errors.required", "Balance as per Statement ");
          errors.add("org.apache.struts.action.GLOBAL_ERROR", error);
          Log.log(5, "InvestmentForm", "validate", " Balance as per Statement is null ");
          balanceStmt = true;
        }

        if ((!balanceUncleared) && ((ftDetail.getId() != 0) || ((ftDetail.getId() == 0) && ((closeBalDateVal) || (balanceStmtVal) || (amtCAVal) || (remarksVal)))) && (!balanceUnclearedVal))
        {
          ActionError error = new ActionError("errors.required", "Credit with Future Value Date");
          errors.add("org.apache.struts.action.GLOBAL_ERROR", error);
          balanceUncleared = true;
          Log.log(5, "InvestmentForm", "validate", " Credit with Future Value Date is null ");
        }

        if ((!amtCA) && ((ftDetail.getId() != 0) || ((ftDetail.getId() == 0) && ((closeBalDateVal) || (balanceStmtVal) || (balanceUnclearedVal) || (remarksVal)))) && (!amtCAVal))
        {
          ActionError error = new ActionError("errors.required", "Amount transferred to CA not reflected");
          errors.add("org.apache.struts.action.GLOBAL_ERROR", error);
          Log.log(5, "InvestmentForm", "validate", " Amount transferred to CA not reflected is null");

          amtCA = true;
        }

        if ((!remarks) && ((ftDetail.getId() != 0) || ((ftDetail.getId() == 0) && ((closeBalDateVal) || (balanceStmtVal) || (balanceUnclearedVal) || (amtCAVal)))) && (!remarksVal))
        {
          ActionError error = new ActionError("errors.required", "Remarks ");
          errors.add("org.apache.struts.action.GLOBAL_ERROR", error);
          Log.log(5, "InvestmentForm", "validate", " Remarks is null ");
          remarks = true;
        }

        if (((closeBalDate) || (balanceStmt) || (balanceUncleared) || (amtCA) || (remarks)) && ((!closeBalDate) || (!balanceStmt) || (!balanceUncleared) || (!amtCA) || (!remarks) || (ftDetail.getId() == 0)));
      }

      ftIterator = ftSet.iterator();
      int notPresentCount = 0;
      while (ftIterator.hasNext())
      {
        String key = (String)ftIterator.next();
        FundTransferDetail fundTransfer = (FundTransferDetail)this.fundTransfers.get(key);
        if ((fundTransfer.getId() == 0) && ((fundTransfer.getClosingBalanceDate() == null) || (fundTransfer.getClosingBalanceDate().equals(""))) && ((fundTransfer.getBalanceAsPerStmt() == null) || (fundTransfer.getBalanceAsPerStmt().equals("")) || (Double.parseDouble(fundTransfer.getBalanceAsPerStmt()) == 0)) && ((fundTransfer.getUnclearedBalance() == null) || (fundTransfer.getUnclearedBalance().equals("")) || (Double.parseDouble(fundTransfer.getUnclearedBalance()) == 0)) && ((fundTransfer.getAmtCANotReflected() == null) || (fundTransfer.getAmtCANotReflected().equals("")) || (Double.parseDouble(fundTransfer.getAmtCANotReflected()) == 0)))
        {
          notPresentCount++;
        }
      }
      if (notPresentCount == this.fundTransfers.size())
      {
        ActionError actionMessage = new ActionError("atleastOneFundTransfer");
        errors.add("org.apache.struts.action.GLOBAL_ERROR", actionMessage);
      }
    }
    else if (mapping.getPath().equals("/showInflowOutflowReport"))
    {
      Log.log(5, "InvestmentForm", "validate", " Plan Investment ");
      Set imSet = this.invstMaturingDetails.keySet();
      Iterator imIterator = imSet.iterator();
      boolean matuirtyAmt = false;
      boolean otherDesc = false;

      while (imIterator.hasNext())
      {
        String key = (String)imIterator.next();
        Log.log(5, "InvestmentForm", "validate", " key " + key);

        InvestmentMaturityDetails imDetail = (InvestmentMaturityDetails)this.invstMaturingDetails.get(key);

        boolean matAmtVal = true;
        boolean otherDescVal = true;
        boolean invNameVal = true;

        if ((imDetail.getInvName() == null) || (imDetail.getInvName().equals("")))
        {
          invNameVal = false;
        }
        if ((imDetail.getOtherDesc() == null) || (imDetail.getOtherDesc().equals("")))
        {
          otherDescVal = false;
        }

        if ((imDetail.getMaturityAmt() == null) || (imDetail.getMaturityAmt().equals("")) || (Double.parseDouble(imDetail.getMaturityAmt()) == 0))
        {
          matAmtVal = false;
        }

        if ((!otherDesc) && ((imDetail.getPliId() != 0) || ((imDetail.getPliId() == 0) && (matAmtVal))) && (!otherDescVal) && (matAmtVal) && (!invNameVal))
        {
          ActionError error = new ActionError("errors.required", "Description ");
          errors.add("org.apache.struts.action.GLOBAL_ERROR", error);
          otherDesc = true;
          Log.log(5, "InvestmentForm", "validate", " Description is null ");
        }

        if ((!matuirtyAmt) && (!matAmtVal) && ((invNameVal) || (otherDescVal)))
        {
          ActionError error = new ActionError("errors.required", "Maturity Amount ");
          errors.add("org.apache.struts.action.GLOBAL_ERROR", error);
          Log.log(5, "InvestmentForm", "validate", " Maturity Amount is null ");
          matuirtyAmt = true;
        }

      }

    }

    Log.log(4, "InvestmentForm", "validate", "Exited");

    return errors;
  }

  public void resetWhenRequired(ActionMapping arg0, HttpServletRequest arg1)
  {
    this.heads.clear();
    this.subHeads.clear();

    this.headsToRender.clear();
    this.subHeadsToRender.clear();
    this.shortHeads.clear();
    this.shortSubHeads.clear();

    this.annualOrShortTerm = "";

    this.month = "";
    this.year = "";
    this.annualFromDate = "";
    this.annualToDate = "";
    this.startDate = "";
    this.endDate = "";
    this.dateOfFlow = "";
    this.valueDate = new Date();
    this.values.clear();
    this.miscReceipts.clear();
    this.fundTransfers.clear();
  }

  private boolean checkValidDate(String date)
  {
    boolean returnValue = true;
    Log.log(4, "InvestmentForm", "checkValidDate", "Entered");

    int index = date.lastIndexOf("/");

    Log.log(5, "InvestmentForm", "index", "index" + index);

    String year = date.substring(index, date.length());

    Log.log(5, "InvestmentForm", "year", "year" + year);

    if (year.length() < 4)
    {
      returnValue = false;
    }

    Log.log(4, "InvestmentForm", "checkValidDate", "Exited");

    return returnValue;
  }

  public Map getShortHeads()
  {
    return this.shortHeads;
  }

  public Map getShortSubHeads()
  {
    return this.shortSubHeads;
  }

  public void setShortSubHead(String key, Object value)
  {
    this.shortSubHeads.put(key, value);
  }

  public Object getShortSubHead(String key)
  {
    return this.shortSubHeads.get(key);
  }

  public void setShortHead(String key, Object value)
  {
    this.shortHeads.put(key, value);
  }

  public Object getShortHead(String key)
  {
    return this.shortHeads.get(key);
  }

  public Map getMiscReceipts()
  {
    return this.miscReceipts;
  }

  public void setMiscReceipts(Map map)
  {
    this.miscReceipts = map;
  }

  public void setMiscReceiptsDate(Date date)
  {
    this.miscReceiptsDate = date;
  }

  public Date getMiscReceiptsDate()
  {
    return this.miscReceiptsDate;
  }

  public int getCounter()
  {
    return this.counter;
  }

  public void setCounter(int i)
  {
    this.counter = i;
  }

  public Map getFundTransfers()
  {
    return this.fundTransfers;
  }

  public Date getStatementDate()
  {
    return this.statementDate;
  }

  public void setFundTransfers(Map map)
  {
    this.fundTransfers = map;
  }

  public void setStatementDate(Date date)
  {
    this.statementDate = date;
  }

  public String getTest()
  {
    return this.test;
  }

  public void setTest(String string)
  {
    this.test = string;
  }

  public ArrayList getBnkChqDetails()
  {
    return this.bnkChqDetails;
  }

  public void setBnkChqDetails(ArrayList list)
  {
    this.bnkChqDetails = list;
  }

  public String getBankBranchName()
  {
    return this.bankBranchName;
  }

  public void setBankBranchName(String string)
  {
    this.bankBranchName = string;
  }

  public Map getEndNo()
  {
    return this.endNo;
  }

  public Map getStartNo()
  {
    return this.startNo;
  }

  public void setEndNo(Map map)
  {
    this.endNo = map;
  }

  public void setStartNo(Map map)
  {
    this.startNo = map;
  }

  public Map getBankId()
  {
    return this.bankId;
  }

  public void setBankId(Map map)
  {
    this.bankId = map;
  }

  public ArrayList getUnutilizedChqDetails()
  {
    return this.unutilizedChqDetails;
  }

  public void setUnutilizedChqDetails(ArrayList list)
  {
    this.unutilizedChqDetails = list;
  }

  public Map getCancelledChq()
  {
    return this.cancelledChq;
  }

  public void setCancelledChq(Map map)
  {
    this.cancelledChq = map;
  }

  public void resetMaps()
  {
    this.cancelledChq.clear();
    this.bnkChqDetails.clear();
  }

  public Map getTempEndNo()
  {
    return this.tempEndNo;
  }

  public void setTempEndNo(Map map)
  {
    this.tempEndNo = map;
  }

  public Map getTempStartNo()
  {
    return this.tempStartNo;
  }

  public void setTempStartNo(Map map)
  {
    this.tempStartNo = map;
  }

  public Map getInvstMaturingDetails()
  {
    return this.invstMaturingDetails;
  }

  public Date getValueDate()
  {
    return this.valueDate;
  }

  public void setInvstMaturingDetails(Map map)
  {
    this.invstMaturingDetails = map;
  }

  public void setValueDate(Date date)
  {
    this.valueDate = date;
  }

  public ArrayList getPlanInvFTDetails()
  {
    return this.planInvFTDetails;
  }

  public Map getPlanInvMainDetails()
  {
    return this.planInvMainDetails;
  }

  public ArrayList getPlanInvMatDetails()
  {
    return this.planInvMatDetails;
  }

  public ArrayList getPlanInvProvisionDetails()
  {
    return this.planInvProvisionDetails;
  }

  public void setPlanInvFTDetails(ArrayList list)
  {
    this.planInvFTDetails = list;
  }

  public void setPlanInvMainDetails(Map map)
  {
    this.planInvMainDetails = map;
  }

  public void setPlanInvMatDetails(ArrayList list)
  {
    this.planInvMatDetails = list;
  }

  public void setPlanInvProvisionDetails(ArrayList list)
  {
    this.planInvProvisionDetails = list;
  }

  public Map getProvisionRemarks()
  {
    return this.provisionRemarks;
  }

  public void setProvisionRemarks(Map map)
  {
    this.provisionRemarks = map;
  }

  public Map getChequeId()
  {
    return this.chequeId;
  }

  public void setChequeId(Map map)
  {
    this.chequeId = map;
  }

  public Date getDateOfTheDocument2()
  {
    return this.dateOfTheDocument2;
  }

  public Date getDateOfTheDocument3()
  {
    return this.dateOfTheDocument3;
  }

  public void setDateOfTheDocument2(Date date)
  {
    this.dateOfTheDocument2 = date;
  }

  public void setDateOfTheDocument3(Date date)
  {
    this.dateOfTheDocument3 = date;
  }

  public ArrayList getChequeArray()
  {
    return this.chequeArray;
  }

  public void setChequeArray(ArrayList list)
  {
    this.chequeArray = list;
  }

  public String getChqId()
  {
    return this.chqId;
  }

  public void setChqId(String string)
  {
    this.chqId = string;
  }
}