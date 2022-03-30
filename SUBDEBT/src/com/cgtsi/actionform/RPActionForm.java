// FrontEnd Plus GUI for JAD
// DeCompiled : RPActionForm.class

package com.cgtsi.actionform;

import com.cgtsi.common.Log;
import com.cgtsi.receiptspayments.AllocationDetail;
import com.cgtsi.receiptspayments.Voucher;
import com.cgtsi.util.DateHelper;
import java.io.PrintStream;
import java.text.ParsePosition;
import java.text.SimpleDateFormat;
import java.util.*;
import javax.servlet.ServletRequest;
import javax.servlet.http.HttpServletRequest;
import org.apache.struts.action.*;
import org.apache.struts.validator.ValidatorActionForm;

public class RPActionForm extends ValidatorActionForm
{
	 private String borrowerDate;
	 private String UTRNo;
	 private String UTRDate;
	 private String status;
	    public String getStatus() {
		return status;
	}



	public void setStatus(String status) {
		this.status = status;
	}

		private Date dtFromDate;
	    private Date dtToDate;
	    private Date dtpaymentDate;
	    private Date bankClearingDate;
	    private double guaranteeFee;
	    private double sanctionedAmount;
	    private String danChecked;
	    private String danIdTxt;
	    private String unitNameRP;
	    private String loanAccountNoRP;
	    private String paymentModeRP;
	    private String RPNumber;
	    private String paymentMode;
	    private int listSize;
    public String getPaymentMode() {
			return paymentMode;
		}



		public void setPaymentMode(String paymentMode) {
			this.paymentMode = paymentMode;
		}

	private ArrayList glHeads;
    private int bankAccountNo;
    private String bankName;
    private String zoneName;
    private String branchName;
    private String comments;
    private double allocatedAmt;
    private String test;
    private String payInSlipFormat;
    private String bankGLCode;
    private String bankGLName;
    private String deptCode;
    private double amount;
    private String amountInFigure;
    private String narration;
    private String manager;
    private String asstManager;
    private Map voucherDetails;
    private Map danIds;
    private Map cgpans;
    private Map unitNames;
    private Map facilitiesCovered;
    private Map dueAmounts;
    private Map amountBeingPaid;
    private Map allocatedFlags;
    private Map appropriatedFlags;
    private Map depositedFlags;
    private Map remarks;
    private Map notAllocatedReasons;
    private Map danPanDetails;
    private Map newDanIds;
    private ArrayList danSummaries;
    private ArrayList panDetails;
    private ArrayList selectedDANs;
    private ArrayList danRemarks;
    private ArrayList paymentDetails;
    private ArrayList allocatedPanDetails;
    private double instrumentAmount;
    private Map firstDisbursementDates;
    private String danNo;
    private String cgpan;
    private int danAmt;
    private String applRemarks;
    private String danType;
    private String selectMember;
    private String collectingBankName;
    private String accountNumber;
    private String memberId;
    private String paymentId;
    private String accountName;
    private String ifscCode;
    private String neftCode;
    private String inwardId;
    private Date dateOfRealisation;
    private Date dateOfTheDocument24;
    private double receivedAmount;
    private Map bankIds;
    private Map zoneIds;
    private Map branchIds;
    private Map waivedFlags;
    private Map amountsRaised;
    private Map penalties;
    String bankId;
    String zoneId;
    String branchId;
    String targetURL;
    private String modeOfPayment;
    private String modeOfDelivery;
    private String instrumentNo;
    private String instrumentType;
    private Date instrumentDate;
    private String payableAt;
    private String collectingBank;
    private String collectingBankBranch;
    private String cgtsiAccountHoldingBranch;
    private Date paymentDate;
    private String drawnAtBranch;
    private String officerName;
    private String drawnAtBank;
    private String userId;
    private String selectAll;
    private ArrayList instruments;
    private Date fromDate;
    private Date toDate;
    private Vector mliWiseDanDetails;
    private Vector dateWiseDANDetails;
    private double refundAmount;
    private String dateType;
    private ArrayList paymentList;
    private ArrayList gfCardRateList;
    private Map rateId;
    private Map lowAmount;
    private Map highAmount;
    private Map gfRate;
    private String allocationType;
    private String remarksforAppropriation;
    private String newInstrumentNo;
    private Date newInstrumentDt;
    private Date paymentInitiateDate;
    private TreeMap allocationPaymentDans;
    private TreeMap allocationPaymentYes;
    private TreeMap allocationPaymentFinalSubmit;
    private double tAmount;
    private ArrayList makepaymentList;
    private TreeMap allocationPaymentFinalSubmit1;
    private TreeMap allocationPaymentFinalSubmit2;
    private TreeMap allocationPaymentFinalSubmit3;
    private String allocatedanIds;
    public double gettAmount() {
		return tAmount;
	}



	public String getUTRNo() {
		return UTRNo;
	}



	public void setUTRNo(String uTRNo) {
		UTRNo = uTRNo;
	}



	public String getUTRDate() {
		return UTRDate;
	}



	public void setUTRDate(String uTRDate) {
		UTRDate = uTRDate;
	}



	public void settAmount(double tAmount) {
		this.tAmount = tAmount;
	}

	private ArrayList allocatedanIdsDetails;
    private String paymentIdR;
    private String paymentIds;
    private String vaccno;
    private String vaccnumber;
    private String paymentId1;
    private double ammount1;
    private String payidcreateddate;
    private ArrayList allocatepaymentmodify;
    private ArrayList allocatepaymentFinal;
    private ArrayList makepaymentFinal;
    private ArrayList allocatepayIDdetails;
    private String cgpan1;
    private String danid1;
    private double ammount2;
    private String dandate1;
    private String PaymentIdF;
    private String VitrualAcF;
    private double AmtF;
    private String RPDATEF;
    private String statusF;
    private TreeMap dansList;

    public RPActionForm()
    {
        dansList = new TreeMap();
        glHeads = new ArrayList();
        test = "";
        payInSlipFormat = "";
        voucherDetails = new HashMap();
        danIds = new HashMap();
        cgpans = new HashMap();
        unitNames = new HashMap();
        facilitiesCovered = new HashMap();
        dueAmounts = new HashMap();
        amountBeingPaid = new HashMap();
        allocatedFlags = new HashMap();
        appropriatedFlags = new HashMap();
        depositedFlags = new HashMap();
        remarks = new HashMap();
        notAllocatedReasons = new HashMap();
        danPanDetails = new HashMap();
        newDanIds = new HashMap();
        instrumentAmount = 0.0D;
        firstDisbursementDates = new HashMap();
        bankIds = new HashMap();
        zoneIds = new HashMap();
        branchIds = new HashMap();
        waivedFlags = new HashMap();
        amountsRaised = new HashMap();
        penalties = new HashMap();
        bankId = null;
        zoneId = null;
        branchId = null;
        targetURL = null;
        instruments = new ArrayList();
        rateId = new TreeMap();
        lowAmount = new TreeMap();
        highAmount = new TreeMap();
        gfRate = new TreeMap();
        allocationPaymentYes = new TreeMap();
        allocationPaymentDans = new TreeMap();
        allocationPaymentFinalSubmit = new TreeMap();
        allocationPaymentFinalSubmit1 = new TreeMap();
        allocationPaymentFinalSubmit2 = new TreeMap();
        allocationPaymentFinalSubmit3 = new TreeMap();
    }
    
    

  



	public String getBorrowerDate() {
		return borrowerDate;
	}



	public void setBorrowerDate(String borrowerDate) {
		this.borrowerDate = borrowerDate;
	}



	public Date getDtFromDate() {
		return dtFromDate;
	}



	public void setDtFromDate(Date dtFromDate) {
		this.dtFromDate = dtFromDate;
	}



	public Date getDtToDate() {
		return dtToDate;
	}



	public void setDtToDate(Date dtToDate) {
		this.dtToDate = dtToDate;
	}



	public Date getDtpaymentDate() {
		return dtpaymentDate;
	}



	public void setDtpaymentDate(Date dtpaymentDate) {
		this.dtpaymentDate = dtpaymentDate;
	}



	public Date getBankClearingDate() {
		return bankClearingDate;
	}



	public void setBankClearingDate(Date bankClearingDate) {
		this.bankClearingDate = bankClearingDate;
	}



	public double getGuaranteeFee() {
		return guaranteeFee;
	}



	public void setGuaranteeFee(double guaranteeFee) {
		this.guaranteeFee = guaranteeFee;
	}



	public double getSanctionedAmount() {
		return sanctionedAmount;
	}



	public void setSanctionedAmount(double sanctionedAmount) {
		this.sanctionedAmount = sanctionedAmount;
	}



	public String getDanChecked() {
		return danChecked;
	}



	public void setDanChecked(String danChecked) {
		this.danChecked = danChecked;
	}



	public String getDanIdTxt() {
		return danIdTxt;
	}



	public void setDanIdTxt(String danIdTxt) {
		this.danIdTxt = danIdTxt;
	}



	public String getUnitNameRP() {
		return unitNameRP;
	}



	public void setUnitNameRP(String unitNameRP) {
		this.unitNameRP = unitNameRP;
	}



	public String getLoanAccountNoRP() {
		return loanAccountNoRP;
	}



	public void setLoanAccountNoRP(String loanAccountNoRP) {
		this.loanAccountNoRP = loanAccountNoRP;
	}



	public String getPaymentModeRP() {
		return paymentModeRP;
	}



	public void setPaymentModeRP(String paymentModeRP) {
		this.paymentModeRP = paymentModeRP;
	}



	public String getRemarksforAppropriation() {
		return remarksforAppropriation;
	}



	public void setRemarksforAppropriation(String remarksforAppropriation)
    {
        this.remarksforAppropriation = remarksforAppropriation;
    }

    public String getremarksforAppropriation()
    {
        return remarksforAppropriation;
    }

    public void setInwardId(String inwardId)
    {
        this.inwardId = inwardId;
    }

    public String getInwardId()
    {
        return inwardId;
    }

    public void setDanType(String danType)
    {
        this.danType = danType;
    }

    public String getDanType()
    {
        return danType;
    }

    public void setApplRemarks(String applRemarks)
    {
        this.applRemarks = applRemarks;
    }

    public String getApplRemarks()
    {
        return applRemarks;
    }

    public void setDanAmt(int danAmt)
    {
        this.danAmt = danAmt;
    }

    public int getDanAmt()
    {
        return danAmt;
    }

    public void setCgpan(String cgpan)
    {
        this.cgpan = cgpan;
    }

    public String getCgpan()
    {
        return cgpan;
    }

    public void setDanId(String key, Object value)
    {
        danIds.put(key, value);
    }

    public Object getDanId(String key)
    {
        return danIds.get(key);
    }

    public Map getAmountBeingPaid()
    {
        return amountBeingPaid;
    }

    public void setAmountBeingPaid(String key, Object value)
    {
        amountBeingPaid.put(key, value);
    }

    public Object getpaidAmounts(String key)
    {
        return amountBeingPaid.get(key);
    }

    public Object getRemarks(String key)
    {
        return remarks.get(key);
    }

    public Map setRemarks()
    {
        return remarks;
    }

    public String getSelectAll()
    {
        return selectAll;
    }

    public void setSelectAll(String string)
    {
        selectAll = string;
    }

    public ActionErrors validate(ActionMapping mapping, HttpServletRequest request)
    {
        ActionErrors errors = super.validate(mapping, request);
        if(errors.isEmpty())
        {
            Log.log(5, "RPActionForm", "validate", (new StringBuilder("size of cgpans selected")).append(cgpans.size()).toString());
            Log.log(5, "RPActionForm", "validate", (new StringBuilder("param method value is ")).append(request.getParameter("method")).toString());
            if(mapping.getPath().equals("/submitPANPayments") && request.getParameter("method").equals("submitPANPayments"))
            {
                String danId = getDanNo();
                Log.log(5, "RPActionForm", "validate", (new StringBuilder(" danId ")).append(danId).toString());
                if(danId.indexOf(".") > 0)
                    danId = danId.replace('.', '_');
                Log.log(5, "RPActionForm", "validate", (new StringBuilder(" danId ")).append(danId).toString());
                Set cgpansSet = getCgpans().keySet();
                ArrayList panAllocationDetails = (ArrayList)getDanPanDetail(danNo);
                int allocationSize = panAllocationDetails.size();
                Iterator cgpansIterator = cgpansSet.iterator();
                Set notAllocatedReasonsSet = getNotAllocatedReasons().keySet();
                Iterator notAllocatedReasonsIterator = notAllocatedReasonsSet.iterator();
                boolean isAvl = false;
                boolean isDate = true;
                boolean isReasonsGiven = false;
                boolean disDateAfterCurrDate = true;
                boolean validDisDate = true;
                boolean disDateMinLength = true;
                Log.log(5, "RPActionForm", "validate", (new StringBuilder(" cgpan size ")).append(cgpans.size()).toString());
                do
                {
                    if(!cgpansIterator.hasNext())
                        break;
                    String cgpanKey = (String)cgpansIterator.next();
                    Log.log(5, "RPActionForm", "validate", (new StringBuilder(" cgpanKey ")).append(cgpanKey).toString());
                    String cgpanValue = (String)cgpans.get(cgpanKey);
                    Log.log(5, "RPActionForm", "validate", (new StringBuilder(" cgpanKey ")).append(cgpanValue.startsWith(danId)).toString());
                    String cgpanPart = cgpanValue.substring(cgpanValue.indexOf("-") + 1, cgpanValue.length());
                    String tempIdKey = (new StringBuilder(String.valueOf(danId))).append("-").append(cgpanPart).toString();
                    Log.log(5, "RPActionForm", "validate", (new StringBuilder(" cgpan part ")).append(cgpanPart).toString());
                    Log.log(5, "RPActionForm", "validate", (new StringBuilder(" frm request ")).append(request.getParameter((new StringBuilder("allocatedFlags(")).append(tempIdKey).append(")").toString())).toString());
                    if(cgpanValue.startsWith(danId.replace('_', '.')) && request.getParameter((new StringBuilder("allocatedFlags(")).append(tempIdKey).append(")").toString()) != null)
                    {
                        Log.log(5, "RPActionForm", "validate", (new StringBuilder(" allocated ")).append(cgpanValue).toString());
                        int j = 0;
                        do
                        {
                            if(j >= allocationSize)
                                break;
                            AllocationDetail allocationDetail = (AllocationDetail)panAllocationDetails.get(j);
                            if(allocationDetail.getCgpan().equals(cgpanPart))
                            {
                                allocationDetail.setAllocatedFlag("Y");
                                panAllocationDetails.set(j, allocationDetail);
                                break;
                            }
                            j++;
                        } while(true);
                        isAvl = true;
                        if(cgpanValue.substring(cgpanValue.length() - 2, cgpanValue.length()).equals("TC"))
                        {
                            Log.log(5, "RPActionForm", "validate", (new StringBuilder(" checking dis date ")).append(cgpanKey).toString());
                            Log.log(5, "RPActionForm", "validate", (new StringBuilder(" dis date ")).append((String)firstDisbursementDates.get(cgpanKey)).toString());
                            if((String)firstDisbursementDates.get(cgpanKey) == null || ((String)firstDisbursementDates.get(cgpanKey)).equals(""))
                            {
                                Log.log(5, "RPActionForm", "validate", (new StringBuilder(" dis date ")).append((String)firstDisbursementDates.get(cgpanKey)).toString());
                                isDate = false;
                            } else
                            {
                                Date currentDate = new Date();
                                SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
                                String disDate = (String)firstDisbursementDates.get(cgpanKey);
                                try
                                {
                                    String stringDate = dateFormat.format(currentDate);
                                    if(!disDate.equals(""))
                                    {
                                        if(disDate.trim().length() < 10)
                                        {
                                            disDateMinLength = false;
                                            Log.log(5, "RPActionForm", "validate", " length is less than zero");
                                        } else
                                        {
                                            dateFormat = new SimpleDateFormat("dd/MM/yyyy");
                                            Date date = dateFormat.parse(disDate, new ParsePosition(0));
                                            Log.log(5, "RPActionForm", "validate", (new StringBuilder(" date ")).append(disDate).toString());
                                            if(date == null)
                                                validDisDate = false;
                                        }
                                        if(disDateMinLength && validDisDate && DateHelper.compareDates(disDate, stringDate) != 0 && DateHelper.compareDates(disDate, stringDate) != 1)
                                            disDateAfterCurrDate = false;
                                    }
                                }
                                catch(Exception exp)
                                {
                                    validDisDate = false;
                                }
                            }
                        }
                    } else
                    {
                        Log.log(5, "RPActionForm", "validate", (new StringBuilder("not allocated ")).append(cgpanValue).toString());
                        Log.log(5, "RPActionForm", "validate", (new StringBuilder("not allocated reason ")).append(notAllocatedReasons.get(cgpanKey)).toString());
                        if(notAllocatedReasons.get(cgpanKey) != null && !((String)notAllocatedReasons.get(cgpanKey)).equals(""))
                            isReasonsGiven = true;
                        int j = 0;
                        do
                        {
                            if(j >= allocationSize)
                                break;
                            AllocationDetail allocationDetail = (AllocationDetail)panAllocationDetails.get(j);
                            if(allocationDetail.getCgpan().equals(cgpanPart))
                            {
                                Log.log(5, "RPActionForm", "validate", (new StringBuilder("setting allocated N ")).append(cgpanKey).toString());
                                allocationDetail.setAllocatedFlag("N");
                                panAllocationDetails.set(j, allocationDetail);
                                break;
                            }
                            j++;
                        } while(true);
                    }
                    if(request.getParameter((new StringBuilder("allocatedFlags(")).append(tempIdKey).append(")").toString()) == null)
                    {
                        Log.log(5, "RPActionForm", "validate", (new StringBuilder("not allocated ")).append(tempIdKey).toString());
                        allocatedFlags.put(tempIdKey, "N");
                    }
                } while(true);
                if(!isAvl)
                {
                    Log.log(5, "RPActionForm", "validate", " No CGPANS selected ");
                    ActionError error = new ActionError("oneCGPANSelected");
                    errors.add("org.apache.struts.action.GLOBAL_ERROR", error);
                }
                if(!isDate)
                {
                    Log.log(5, "RPActionForm", "validate", " No Dates entered");
                    ActionError error = new ActionError("disbursementDateRequired");
                    errors.add("org.apache.struts.action.GLOBAL_ERROR", error);
                }
                if(!disDateMinLength)
                {
                    String errorStrs[] = new String[2];
                    errorStrs[0] = "Date of First Disbursement";
                    errorStrs[1] = "10";
                    ActionError error = new ActionError("errors.minlength", errorStrs);
                    errors.add("org.apache.struts.action.GLOBAL_ERROR", error);
                }
                if(!disDateAfterCurrDate)
                {
                    ActionError actionError = new ActionError("futureDate", "Date of First Disbursement");
                    errors.add("org.apache.struts.action.GLOBAL_ERROR", actionError);
                }
                if(!validDisDate)
                {
                    ActionError actionError = new ActionError("errors.date", "Date of First Disbursement");
                    errors.add("org.apache.struts.action.GLOBAL_ERROR", actionError);
                }
                int j = 0;
                do
                {
                    if(j >= panAllocationDetails.size())
                        break;
                    AllocationDetail allocationDetail = (AllocationDetail)panAllocationDetails.get(j);
                    Log.log(5, "RPActionForm", "validate", (new StringBuilder(" cgpan from danpandetails ")).append(allocationDetail.getCgpan()).toString());
                    if(allocationDetail.getAllocatedFlag().equals("N") && allocationDetail.getAmountDue() > 0.0D)
                    {
                        Log.log(5, "RPActionForm", "validate", " not allocated ");
                        danId = danNo;
                        danId = danId.replace('.', '_');
                        String reasons = (String)notAllocatedReasons.get((new StringBuilder(String.valueOf(danId))).append("-").append(allocationDetail.getCgpan()).toString());
                        Log.log(5, "RPActionForm", "validate", (new StringBuilder(" reason for not allocated ")).append(reasons).toString());
                        if((reasons == null || reasons.equals("")) && ((String)newDanIds.get((new StringBuilder(String.valueOf(danId))).append("-").append(allocationDetail.getCgpan()).toString())).equals(""))
                        {
                            ActionError error = new ActionError("notAllocatedReasonRequired");
                            errors.add("org.apache.struts.action.GLOBAL_ERROR", error);
                            break;
                        }
                    }
                    j++;
                } while(true);
            } else
            if(mapping.getPath().equals("/allocatePayments") && request.getParameter("method").equals("allocatePayments"))
            {
                Log.log(5, "RPActionForm", "validate", " allocations ");
                if(modeOfPayment == null || modeOfPayment.equals(""))
                {
                    ActionError error = new ActionError("errors.required", "Mode Of Payment");
                    errors.add("org.apache.struts.action.GLOBAL_ERROR", error);
                }
                Log.log(5, "RPActionForm", "validate", (new StringBuilder(" paymentDate ")).append(paymentDate).toString());
                if(paymentDate == null || paymentDate.toString().equals(""))
                {
                    ActionError error = new ActionError("errors.required", "Payment Date");
                    errors.add("org.apache.struts.action.GLOBAL_ERROR", error);
                } else
                {
                    try
                    {
                        SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
                        String payDate = paymentDate.toString();
                        Log.log(5, "RPActionForm", "validate", (new StringBuilder(" payDate ")).append(payDate.length()).toString());
                        if(payDate.length() < 10)
                        {
                            ActionError actionError = new ActionError("errors.minlength", "Payment Date", "10");
                            errors.add("org.apache.struts.action.GLOBAL_ERROR", actionError);
                        } else
                        {
                            Date date = dateFormat.parse(payDate, new ParsePosition(0));
                            Log.log(5, "RPActionForm", "validate", (new StringBuilder(" date ")).append(date).toString());
                            if(date == null)
                            {
                                ActionError actionError = new ActionError("errors.date", "Payment Date");
                                errors.add("org.apache.struts.action.GLOBAL_ERROR", actionError);
                            } else
                            {
                                Log.log(5, "RPActionForm", "validate", " date not null");
                                Date stringDate = new Date();
                                try
                                {
                                    if(date.compareTo(stringDate) == 1)
                                    {
                                        ActionError actionError = new ActionError("currentDatepaymentDate");
                                        errors.add("org.apache.struts.action.GLOBAL_ERROR", actionError);
                                    }
                                }
                                catch(NumberFormatException numberFormatException)
                                {
                                    Log.log(5, "RPActionForm", "validate", (new StringBuilder(" numberFormatException :")).append(numberFormatException.getMessage()).toString());
                                    ActionError actionError = new ActionError("errors.date", "Payment Date");
                                    errors.add("org.apache.struts.action.GLOBAL_ERROR", actionError);
                                }
                            }
                        }
                    }
                    catch(Exception e)
                    {
                        Log.log(5, "RPActionForm", "validate", " error message");
                        ActionError actionError = new ActionError("errors.date", "Payment Date");
                        errors.add("org.apache.struts.action.GLOBAL_ERROR", actionError);
                    }
                }
                if(instrumentNo == null || instrumentNo.equals(""))
                {
                    ActionError error = new ActionError("errors.required", "Instrument Number");
                    errors.add("org.apache.struts.action.GLOBAL_ERROR", error);
                }
                if(this.instrumentDate == null || this.instrumentDate.toString().equals(""))
                {
                    ActionError error = new ActionError("errors.required", "Instrument Date");
                    errors.add("org.apache.struts.action.GLOBAL_ERROR", error);
                } else
                {
                    try
                    {
                        SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
                        String instDate = this.instrumentDate.toString();
                        if(instDate.length() < 10)
                        {
                            ActionError actionError = new ActionError("errors.minlength", "Instrument Date", "10");
                            errors.add("org.apache.struts.action.GLOBAL_ERROR", actionError);
                        } else
                        {
                            Date date = dateFormat.parse(instDate, new ParsePosition(0));
                            if(date == null)
                            {
                                ActionError actionError = new ActionError("errors.date", "Instrument Date");
                                errors.add("org.apache.struts.action.GLOBAL_ERROR", actionError);
                            }
                        }
                    }
                    catch(Exception e)
                    {
                        ActionError actionError = new ActionError("errors.date", "Instrument Date");
                        errors.add("org.apache.struts.action.GLOBAL_ERROR", actionError);
                    }
                }
                if(payableAt == null || payableAt.equals(""))
                {
                    ActionError error = new ActionError("errors.required", "Payable At");
                    errors.add("org.apache.struts.action.GLOBAL_ERROR", error);
                }
                if(drawnAtBank == null || drawnAtBank.equals(""))
                {
                    ActionError error = new ActionError("errors.required", "Drawn At Bank");
                    errors.add("org.apache.struts.action.GLOBAL_ERROR", error);
                }
                if(drawnAtBranch == null || drawnAtBranch.equals(""))
                {
                    ActionError error = new ActionError("errors.required", "Drawn At Branch");
                    errors.add("org.apache.struts.action.GLOBAL_ERROR", error);
                }
            } else
            if(mapping.getPath().equals("/addMoreReceiptVoucherDetails") || mapping.getPath().equals("/insertReceiptVoucherDetails") || mapping.getPath().equals("/addMorePaymentVoucherDetails") || mapping.getPath().equals("/insertPaymentVoucherDetails") || mapping.getPath().equals("/insertJournalVoucherDetails") || mapping.getPath().equals("/addMoreJournalVoucherDetails"))
            {
                Log.log(5, "RPActionForm", "validate", " Voucher");
                Set voucherSet = voucherDetails.keySet();
                Iterator voucherIterator = voucherSet.iterator();
                boolean acCode = false;
                boolean paidTo = false;
                boolean amountRs = false;
                boolean debitCredit = false;
                boolean instrumentTypeBoolean = false;
                boolean instrumentDateBoolean = false;
                boolean instrumentNumBoolean = false;
                boolean advDateBoolean = false;
                do
                {
                    if(!voucherIterator.hasNext())
                        break;
                    String key = (String)voucherIterator.next();
                    Log.log(5, "RPActionForm", "validate", (new StringBuilder(" key ")).append(key).toString());
                    Voucher voucher = (Voucher)voucherDetails.get(key);
                    if(!acCode && (voucher.getAcCode() == null || voucher.getAcCode().equals("")))
                    {
                        ActionError error = new ActionError("errors.required", "Account Code");
                        errors.add("org.apache.struts.action.GLOBAL_ERROR", error);
                        acCode = true;
                        Log.log(5, "RPActionForm", "validate", " Ac is null ");
                    }
                    if(!paidTo && (voucher.getPaidTo() == null || voucher.getPaidTo().equals("")))
                    {
                        ActionError error = null;
                        if(mapping.getPath().equals("/addMorePaymentVoucherDetails") || mapping.getPath().equals("/insertPaymentVoucherDetails"))
                            error = new ActionError("errors.required", "Paid To ");
                        else
                            error = new ActionError("errors.required", "Received From");
                        errors.add("org.apache.struts.action.GLOBAL_ERROR", error);
                        Log.log(5, "RPActionForm", "validate", " Paid to is null ");
                        paidTo = true;
                    }
                    if(!amountRs && (voucher.getAmountInRs() == null || voucher.getAmountInRs().equals("")))
                    {
                        ActionError error = new ActionError("errors.required", "Amount in Rs");
                        errors.add("org.apache.struts.action.GLOBAL_ERROR", error);
                        Log.log(5, "RPActionForm", "validate", " Amount in Rs ");
                        amountRs = true;
                    }
                    if(!debitCredit && (voucher.getDebitOrCredit() == null || voucher.getDebitOrCredit().equals("")))
                    {
                        ActionError error = new ActionError("errors.required", "Debit or Credit ");
                        errors.add("org.apache.struts.action.GLOBAL_ERROR", error);
                        Log.log(5, "RPActionForm", "validate", " Debit or Credit ");
                        debitCredit = true;
                    }
                    if(!instrumentTypeBoolean && (voucher.getInstrumentType() == null || voucher.getInstrumentType().equals("")))
                    {
                        ActionError error = new ActionError("errors.required", "Instrument Type ");
                        errors.add("org.apache.struts.action.GLOBAL_ERROR", error);
                        Log.log(5, "RPActionForm", "validate", " Instrument type");
                        instrumentTypeBoolean = true;
                    }
                    if(!instrumentDateBoolean)
                        if(voucher.getInstrumentDate() == null || voucher.getInstrumentDate().equals(""))
                        {
                            ActionError error = new ActionError("errors.required", "Instrument Date ");
                            errors.add("org.apache.struts.action.GLOBAL_ERROR", error);
                            Log.log(5, "RPActionForm", "validate", " Instrument date is null ");
                            instrumentDateBoolean = true;
                        } else
                        {
                            String instrumentDate = voucher.getInstrumentDate();
                            if(instrumentDate.trim().length() < 10)
                            {
                                String errorStrs[] = new String[2];
                                errorStrs[0] = "Instrument Date ";
                                errorStrs[1] = "10";
                                ActionError error = new ActionError("errors.minlength", errorStrs);
                                errors.add("org.apache.struts.action.GLOBAL_ERROR", error);
                                instrumentDateBoolean = true;
                                Log.log(5, "RPActionForm", "validate", " length is less than zero");
                            } else
                            {
                                SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
                                Date date = dateFormat.parse(instrumentDate, new ParsePosition(0));
                                Log.log(5, "RPActionForm", "validate", (new StringBuilder(" date ")).append(date).toString());
                                if(date == null)
                                {
                                    ActionError error = new ActionError("errors.date", "Instrument Date");
                                    errors.add("org.apache.struts.action.GLOBAL_ERROR", error);
                                    instrumentDateBoolean = true;
                                }
                            }
                        }
                    if(!instrumentNumBoolean && (voucher.getInstrumentNo() == null || voucher.getInstrumentNo().equals("")))
                    {
                        ActionError error = new ActionError("errors.required", "Instrument Number ");
                        errors.add("org.apache.struts.action.GLOBAL_ERROR", error);
                        Log.log(5, "RPActionForm", "validate", " Instrument Num is null ");
                        instrumentNumBoolean = true;
                    }
                    Log.log(5, "RPActionForm", "validate", (new StringBuilder("advDateBoolean ")).append(advDateBoolean).append(",").append(voucher.getAdvDate()).toString());
                    if(!advDateBoolean && voucher.getAdvDate() != null && !voucher.getAdvDate().equals(""))
                    {
                        String advDate = voucher.getAdvDate();
                        if(advDate.trim().length() < 10)
                        {
                            String errorStrs[] = new String[2];
                            errorStrs[0] = "Adv. Date ";
                            errorStrs[1] = "10";
                            ActionError error = new ActionError("errors.minlength", errorStrs);
                            errors.add("org.apache.struts.action.GLOBAL_ERROR", error);
                            Log.log(5, "RPActionForm", "validate", " length is less than zero");
                        } else
                        {
                            SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
                            Date date = dateFormat.parse(advDate, new ParsePosition(0));
                            Log.log(5, "RPActionForm", "validate", (new StringBuilder(" date ")).append(date).toString());
                            if(date == null)
                            {
                                ActionError error = new ActionError("errors.date", "Adv Date");
                                errors.add("org.apache.struts.action.GLOBAL_ERROR", error);
                                instrumentDateBoolean = true;
                            }
                        }
                        advDateBoolean = true;
                    }
                    Log.log(5, "RPActionForm", "validate", (new StringBuilder("instrumentDateBoolean,instrumentTypeBoolean,instrumentNumBoolean,acCode,paidTo,amountRs,debitCredit ")).append(instrumentDateBoolean).append(",").append(instrumentTypeBoolean).append(",").append(instrumentNumBoolean).append(",").append(acCode).append(",").append(paidTo).append(",").append(amountRs).append(",").append(debitCredit).toString());
                } while(!instrumentDateBoolean || !instrumentTypeBoolean || !instrumentNumBoolean || !acCode || !paidTo || !amountRs || !debitCredit);
            }
        }
        if(mapping.getPath().equals("/afterAppropriatePayments") && request.getParameter("method").equals("appropriatePayments"))
        {
            Map appMap = getAppropriatedFlags();
            Set appSet = appMap.keySet();
            Iterator appIterator = appSet.iterator();
            System.out.println((new StringBuilder(" appMap ")).append(appMap).toString());
            boolean atleastOneAppropriated = false;
            while(appIterator.hasNext()) 
            {
                Object key = appIterator.next();
                String value = (String)appMap.get(key);
                Log.log(5, "RPActionForm", "validate", (new StringBuilder(String.valueOf(String.valueOf(key)))).append(",").append(value).toString());
                Log.log(5, "RPActionForm", "validate", request.getParameter((new StringBuilder("appropriatedFlags(")).append((String)key).append(")").toString()));
                if(request.getParameter((new StringBuilder("appropriatedFlags(")).append((String)key).append(")").toString()) != null)
                {
                    Log.log(5, "RPActionForm", "validate", "request has values ");
                    atleastOneAppropriated = true;
                } else
                {
                    appMap.put(key, "N");
                }
            }
            if(!atleastOneAppropriated)
            {
                ActionError error = new ActionError("appropriateOne");
                errors.add("org.apache.struts.action.GLOBAL_ERROR", error);
            }
            Date realisationDate = getDateOfRealisation();
            Date instDate = getInstrumentDate();
            if(realisationDate != null && !realisationDate.toString().equals("") && realisationDate.compareTo(instDate) < 0)
            {
                ActionError error = new ActionError("dtRealisationGTEqualInstDate");
                errors.add("org.apache.struts.action.GLOBAL_ERROR", error);
            }
        }
        if(mapping.getPath().equals("/submitReallocationPayments") && request.getParameter("method").equals("submitReallocationPayments"))
        {
            Map appMap = getCgpans();
            Set appSet = appMap.keySet();
            Iterator appIterator = appSet.iterator();
            boolean atleastOneReallocated = false;
            boolean isDate = true;
            boolean disDateMinLength = true;
            boolean validDisDate = true;
            boolean disDateAfterCurrDate = true;
            do
            {
                if(!appIterator.hasNext())
                    break;
                Object key = appIterator.next();
                String value = (String)appMap.get(key);
                Log.log(5, "RPActionForm", "validate", (new StringBuilder(String.valueOf(String.valueOf(key)))).append(",").append(value).toString());
                String cgpanValue = ((String)key).substring(((String)key).indexOf('-') + 1, ((String)key).length());
                Log.log(5, "RPActionForm", "validate", request.getParameter((new StringBuilder("cgpan(")).append((String)key).append(")").toString()));
                if(request.getParameter((new StringBuilder("cgpan(")).append((String)key).append(")").toString()) != null)
                {
                    Log.log(5, "RPActionForm", "validate", "request has values ");
                    atleastOneReallocated = true;
                    if(cgpanValue.substring(cgpanValue.length() - 2, cgpanValue.length()).equals("TC"))
                        if((String)firstDisbursementDates.get(key) == null || ((String)firstDisbursementDates.get(key)).equals(""))
                        {
                            Log.log(5, "RPActionForm", "validate", (new StringBuilder(" dis date ")).append((String)firstDisbursementDates.get(key)).toString());
                            isDate = false;
                        } else
                        {
                            Date currentDate = new Date();
                            SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
                            String disDate = (String)firstDisbursementDates.get(key);
                            try
                            {
                                String stringDate = dateFormat.format(currentDate);
                                if(!disDate.equals(""))
                                {
                                    if(disDate.trim().length() < 10)
                                    {
                                        disDateMinLength = false;
                                        Log.log(5, "RPActionForm", "validate", " length is less than zero");
                                    } else
                                    {
                                        dateFormat = new SimpleDateFormat("dd/MM/yyyy");
                                        Date date = dateFormat.parse(disDate, new ParsePosition(0));
                                        Log.log(5, "RPActionForm", "validate", (new StringBuilder(" date ")).append(disDate).toString());
                                        if(date == null)
                                            validDisDate = false;
                                    }
                                    if(disDateMinLength && validDisDate && DateHelper.compareDates(disDate, stringDate) != 0 && DateHelper.compareDates(disDate, stringDate) != 1)
                                        disDateAfterCurrDate = false;
                                }
                            }
                            catch(Exception exp)
                            {
                                validDisDate = false;
                            }
                        }
                } else
                {
                    appMap.put(key, "N");
                }
            } while(true);
            if(!atleastOneReallocated)
            {
                ActionError error = new ActionError("reallocateOne");
                errors.add("org.apache.struts.action.GLOBAL_ERROR", error);
            }
            if(!isDate)
            {
                Log.log(5, "RPActionForm", "validate", " No Dates entered");
                ActionError error = new ActionError("disbursementDateRequired");
                errors.add("org.apache.struts.action.GLOBAL_ERROR", error);
            }
            Log.log(5, "RPActionForm", "validate", (new StringBuilder(" disDateMinLength :")).append(disDateMinLength).toString());
            if(!disDateMinLength)
            {
                ActionError error = new ActionError("errors.minlength", "Date of First Disbursement", "10");
                errors.add("org.apache.struts.action.GLOBAL_ERROR", error);
            }
            if(!disDateAfterCurrDate)
            {
                ActionError actionError = new ActionError("futureDate", "Date of First Disbursement");
                errors.add("org.apache.struts.action.GLOBAL_ERROR", actionError);
            }
            if(!validDisDate)
            {
                ActionError actionError = new ActionError("errors.date", "Date of First Disbursement");
                errors.add("org.apache.struts.action.GLOBAL_ERROR", actionError);
            }
        }
        return errors;
    }

    private ActionError generateError(Map map, String message)
    {
        ActionError error = null;
        for(Iterator iterator = map.keySet().iterator(); iterator.hasNext();)
        {
            String data = (String)map.get(iterator.next());
            if(data == null || data.trim().equals(""))
                return new ActionError("errors.all.required", message);
        }

        return error;
    }

    public void reset(ActionMapping arg0, ServletRequest arg1)
    {
        super.reset(arg0, arg1);
    }

    public void reset(ActionMapping arg0, HttpServletRequest arg1)
    {
        super.reset(arg0, arg1);
    }

    public Map getDanIds()
    {
        return danIds;
    }

    public ArrayList getDanSummaries()
    {
        return danSummaries;
    }

    public Map getRemarks()
    {
        return remarks;
    }

    public void setAmountBeingPaid(Map map)
    {
        amountBeingPaid = map;
    }

    public void setDanIds(Map map)
    {
        danIds = map;
    }

    public void setDanSummaries(ArrayList list)
    {
        danSummaries = list;
    }

    public void setRemarks(Map map)
    {
        remarks = map;
    }

    public Map getAllocatedFlags()
    {
        return allocatedFlags;
    }

    public void setAllocatedFlag(String key, Object value)
    {
        allocatedFlags.put(key, value);
    }

    public Object getAllocatedFlag(String key)
    {
        return allocatedFlags.get(key);
    }

    public void setAllocatedFlags(Map map)
    {
        allocatedFlags = map;
    }

    public double getInstrumentAmount()
    {
        return instrumentAmount;
    }

    public void setInstrumentAmount(double d)
    {
        instrumentAmount = d;
    }

    public Map getCgpans()
    {
        return cgpans;
    }

    public Object getCgpan(String key)
    {
        return cgpans.get(key);
    }

    public void setCgpan(String key, Object value)
    {
        cgpans.put(key, value);
    }

    public String getDanNo()
    {
        return danNo;
    }

    public void setCgpans(Map map)
    {
        cgpans = map;
    }

    public void setDanNo(String string)
    {
        danNo = string;
    }

    public Map getFirstDisbursementDates()
    {
        return firstDisbursementDates;
    }

    public void setFirstDisbursementDates(Map map)
    {
        firstDisbursementDates = map;
    }

    public Date getDateOfRealisation()
    {
        return dateOfRealisation;
    }

    public Date getDateOfTheDocument24()
    {
        return dateOfTheDocument24;
    }

    public void setDateOfTheDocument24(Date dateOfTheDocument24)
    {
        this.dateOfTheDocument24 = dateOfTheDocument24;
    }

    public String getPaymentId()
    {
        return paymentId;
    }

    public double getReceivedAmount()
    {
        return receivedAmount;
    }

    public void setDateOfRealisation(Date date)
    {
        dateOfRealisation = date;
    }

    public void setPaymentId(String string)
    {
        paymentId = string;
    }

    public void setReceivedAmount(double d)
    {
        receivedAmount = d;
    }

    public Map getBankIds()
    {
        return bankIds;
    }

    public Map getBranchIds()
    {
        return branchIds;
    }

    public Map getZoneIds()
    {
        return zoneIds;
    }

    public void setBankIds(Map map)
    {
        bankIds = map;
    }

    public void setBranchIds(Map map)
    {
        branchIds = map;
    }

    public void setZoneIds(Map map)
    {
        zoneIds = map;
    }

    public Map getAmountsRaised()
    {
        return amountsRaised;
    }

    public Map getPenalties()
    {
        return penalties;
    }

    public void setAmountsRaised(Map map)
    {
        amountsRaised = map;
    }

    public void setPenalties(Map map)
    {
        penalties = map;
    }

    public String getSelectMember()
    {
        return selectMember;
    }

    public void setSelectMember(String string)
    {
        selectMember = string;
    }

    public String getBankId()
    {
        return bankId;
    }

    public String getBranchId()
    {
        return branchId;
    }

    public String getZoneId()
    {
        return zoneId;
    }

    public void setBankId(String string)
    {
        bankId = string;
    }

    public void setBranchId(String string)
    {
        branchId = string;
    }

    public void setZoneId(String string)
    {
        zoneId = string;
    }

    public String getTargetURL()
    {
        return targetURL;
    }

    public void setTargetURL(String string)
    {
        targetURL = string;
    }

    public Map getDueAmounts()
    {
        return dueAmounts;
    }

    public Map getFacilitiesCovered()
    {
        return facilitiesCovered;
    }

    public Map getUnitNames()
    {
        return unitNames;
    }

    public void setDueAmounts(Map map)
    {
        dueAmounts = map;
    }

    public void setFacilitiesCovered(Map map)
    {
        facilitiesCovered = map;
    }

    public void setUnitNames(Map map)
    {
        unitNames = map;
    }

    public ArrayList getPanDetails()
    {
        return panDetails;
    }

    public void setPanDetails(ArrayList list)
    {
        panDetails = list;
    }

    public Map getNotAllocatedReasons()
    {
        return notAllocatedReasons;
    }

    public void setNotAllocatedReasons(Map map)
    {
        notAllocatedReasons = map;
    }

    public Object getNotAllocatedReason(String key)
    {
        return notAllocatedReasons.get(key);
    }

    public void setNotAllocatedReason(String key, Object value)
    {
        notAllocatedReasons.put(key, value);
    }

    public ArrayList getSelectedDANs()
    {
        return selectedDANs;
    }

    public void setSelectedDANs(ArrayList list)
    {
        selectedDANs = list;
    }

    public String getAccountNumber()
    {
        return accountNumber;
    }

    public String getCollectingBankName()
    {
        return collectingBankName;
    }

    public void setAccountNumber(String string)
    {
        accountNumber = string;
    }

    public void setCollectingBankName(String string)
    {
        collectingBankName = string;
    }

    public String getCgtsiAccountHoldingBranch()
    {
        return cgtsiAccountHoldingBranch;
    }

    public String getCollectingBank()
    {
        return collectingBank;
    }

    public String getCollectingBankBranch()
    {
        return collectingBankBranch;
    }

    public String getDrawnAtBank()
    {
        return drawnAtBank;
    }

    public String getDrawnAtBranch()
    {
        return drawnAtBranch;
    }

    public Date getInstrumentDate()
    {
        return instrumentDate;
    }

    public String getInstrumentNo()
    {
        return instrumentNo;
    }

    public String getInstrumentType()
    {
        return instrumentType;
    }

    public String getModeOfDelivery()
    {
        return modeOfDelivery;
    }

    public String getModeOfPayment()
    {
        return modeOfPayment;
    }

    public String getOfficerName()
    {
        return officerName;
    }

    public String getPayableAt()
    {
        return payableAt;
    }

    public Date getPaymentDate()
    {
        return paymentDate;
    }

    public String getUserId()
    {
        return userId;
    }

    public void setCgtsiAccountHoldingBranch(String string)
    {
        cgtsiAccountHoldingBranch = string;
    }

    public void setCollectingBank(String string)
    {
        collectingBank = string;
    }

    public void setCollectingBankBranch(String string)
    {
        collectingBankBranch = string;
    }

    public void setDrawnAtBank(String string)
    {
        drawnAtBank = string;
    }

    public void setDrawnAtBranch(String string)
    {
        drawnAtBranch = string;
    }

    public void setInstrumentDate(Date date)
    {
        instrumentDate = date;
    }

    public void setInstrumentNo(String string)
    {
        instrumentNo = string;
    }

    public void setInstrumentType(String string)
    {
        instrumentType = string;
    }

    public void setModeOfDelivery(String string)
    {
        modeOfDelivery = string;
    }

    public void setModeOfPayment(String string)
    {
        modeOfPayment = string;
    }

    public void setOfficerName(String string)
    {
        officerName = string;
    }

    public void setPayableAt(String string)
    {
        payableAt = string;
    }

    public void setPaymentDate(Date date)
    {
        paymentDate = date;
    }

    public void setUserId(String string)
    {
        userId = string;
    }

    public ArrayList getDanRemarks()
    {
        return danRemarks;
    }

    public void setDanRemarks(ArrayList list)
    {
        danRemarks = list;
    }

    public ArrayList getPaymentDetails()
    {
        return paymentDetails;
    }

    public void setPaymentDetails(ArrayList list)
    {
        paymentDetails = list;
    }

    public String getMemberId()
    {
        return memberId;
    }

    public void setMemberId(String string)
    {
        memberId = string;
    }

    public Map getAppropriatedFlags()
    {
        return appropriatedFlags;
    }

    public Map getDepositedFlags()
    {
        return depositedFlags;
    }

    public void setDepositedFlags(Map depositedFlags)
    {
        this.depositedFlags = depositedFlags;
    }

    public void setDepositedFlags(String key, Object value)
    {
        depositedFlags.put(key, value);
    }

    public void setAppropriatedFlags(Map map)
    {
        appropriatedFlags = map;
    }

    public Map getDanPanDetails()
    {
        return danPanDetails;
    }

    public void setDanPanDetails(Map map)
    {
        danPanDetails = map;
    }

    public Object getDanPanDetail(String key)
    {
        return danPanDetails.get(key);
    }

    public void setDanPanDetail(String key, Object value)
    {
        danPanDetails.put(key, value);
    }

    public ArrayList getInstruments()
    {
        return instruments;
    }

    public void setInstruments(ArrayList list)
    {
        instruments = list;
    }

    public void resetWhenRequired()
    {
        modeOfPayment = "";
        modeOfDelivery = "";
        instrumentNo = "";
        instrumentType = "";
        instrumentDate = null;
        payableAt = "";
        collectingBank = "";
        collectingBankBranch = "";
        cgtsiAccountHoldingBranch = "";
        paymentDate = null;
        drawnAtBranch = "";
        officerName = "";
        drawnAtBank = "";
        voucherDetails.clear();
        bankGLCode = "";
        bankGLName = "";
        deptCode = "";
        amount = 0.0D;
        amountInFigure = "";
        narration = "";
        manager = "";
        asstManager = "";
        fromDate = null;
        toDate = null;
        dateType = "N";
    }

    public Map getVoucherDetails()
    {
        return voucherDetails;
    }

    public void setVoucherDetails(Map map)
    {
        voucherDetails = map;
    }

    public void setVoucherDetail(String key, Object value)
    {
        voucherDetails.put(key, value);
    }

    public Object getVoucherDetail(String key)
    {
        return voucherDetails.get(key);
    }

    public double getAmount()
    {
        return amount;
    }

    public String getAmountInFigure()
    {
        return amountInFigure;
    }

    public String getBankGLCode()
    {
        return bankGLCode;
    }

    public String getBankGLName()
    {
        return bankGLName;
    }

    public String getDeptCode()
    {
        return deptCode;
    }

    public String getNarration()
    {
        return narration;
    }

    public void setAmount(double d)
    {
        amount = d;
    }

    public void setAmountInFigure(String string)
    {
        amountInFigure = string;
    }

    public void setBankGLCode(String string)
    {
        bankGLCode = string;
    }

    public void setBankGLName(String string)
    {
        bankGLName = string;
    }

    public void setDeptCode(String string)
    {
        deptCode = string;
    }

    public void setNarration(String string)
    {
        narration = string;
    }

    public String getAsstManager()
    {
        return asstManager;
    }

    public String getManager()
    {
        return manager;
    }

    public void setAsstManager(String string)
    {
        asstManager = string;
    }

    public void setManager(String string)
    {
        manager = string;
    }

    public Date getFromDate()
    {
        return fromDate;
    }

    public void setFromDate(Date aDate)
    {
        fromDate = aDate;
    }

    public Date getToDate()
    {
        return toDate;
    }

    public void setToDate(Date aDate)
    {
        toDate = aDate;
    }

    public Vector getMliWiseDanDetails()
    {
        return mliWiseDanDetails;
    }

    public void setMliWiseDanDetails(Vector dtls)
    {
        mliWiseDanDetails = dtls;
    }

    public Vector getDateWiseDANDetails()
    {
        return dateWiseDANDetails;
    }

    public void setDateWiseDANDetails(Vector dtls)
    {
        dateWiseDANDetails = dtls;
    }

    public ArrayList getGlHeads()
    {
        return glHeads;
    }

    public void setGlHeads(ArrayList list)
    {
        glHeads = list;
    }

    public Map getWaivedFlags()
    {
        return waivedFlags;
    }

    public void setWaivedFlags(Map map)
    {
        waivedFlags = map;
    }

    public double getRefundAmount()
    {
        return refundAmount;
    }

    public void setRefundAmount(double d)
    {
        refundAmount = d;
    }

    public ArrayList getAllocatedPanDetails()
    {
        return allocatedPanDetails;
    }

    public void setAllocatedPanDetails(ArrayList list)
    {
        allocatedPanDetails = list;
    }

    public String getDateType()
    {
        return dateType;
    }

    public void setDateType(String string)
    {
        dateType = string;
    }

    public ArrayList getPaymentList()
    {
        return paymentList;
    }

    public void setPaymentList(ArrayList list)
    {
        paymentList = list;
    }

    public void setAppropriatedFlag(String key, Object value)
    {
        appropriatedFlags.put(key, value);
    }

    public String getTest()
    {
        return test;
    }

    public void setTest(String string)
    {
        test = string;
    }

    public String getPayInSlipFormat()
    {
        return payInSlipFormat;
    }

    public void setPayInSlipFormat(String string)
    {
        payInSlipFormat = string;
    }

    public ArrayList getGfCardRateList()
    {
        return gfCardRateList;
    }

    public void setGfCardRateList(ArrayList list)
    {
        gfCardRateList = list;
    }

    public Map getGfRate()
    {
        return gfRate;
    }

    public Map getHighAmount()
    {
        return highAmount;
    }

    public Map getLowAmount()
    {
        return lowAmount;
    }

    public Map getRateId()
    {
        return rateId;
    }

    public void setGfRate(Map map)
    {
        gfRate = map;
    }

    public void setHighAmount(Map map)
    {
        highAmount = map;
    }

    public void setLowAmount(Map map)
    {
        lowAmount = map;
    }

    public void setRateId(Map map)
    {
        rateId = map;
    }

    public Map getNewDanIds()
    {
        return newDanIds;
    }

    public void setNewDanIds(Map map)
    {
        newDanIds = map;
    }

    public String getAllocationType()
    {
        return allocationType;
    }

    public void setAllocationType(String allocationType)
    {
        this.allocationType = allocationType;
    }

    public void setNewInstrumentNo(String newInstrumentNo)
    {
        this.newInstrumentNo = newInstrumentNo;
    }

    public String getNewInstrumentNo()
    {
        return newInstrumentNo;
    }

    public void setNewInstrumentDt(Date newInstrumentDt)
    {
        this.newInstrumentDt = newInstrumentDt;
    }

    public Date getNewInstrumentDt()
    {
        return newInstrumentDt;
    }

    public void setAccountName(String accountName)
    {
        this.accountName = accountName;
    }

    public String getAccountName()
    {
        return accountName;
    }

    public void setIfscCode(String ifscCode)
    {
        this.ifscCode = ifscCode;
    }

    public String getIfscCode()
    {
        return ifscCode;
    }

    public void setBankAccountNo(int bankAccountNo)
    {
        this.bankAccountNo = bankAccountNo;
    }

    public int getBankAccountNo()
    {
        return bankAccountNo;
    }

    public void setBankName(String bankName)
    {
        this.bankName = bankName;
    }

    public String getBankName()
    {
        return bankName;
    }

    public void setZoneName(String zoneName)
    {
        this.zoneName = zoneName;
    }

    public String getZoneName()
    {
        return zoneName;
    }

    public void setBranchName(String branchName)
    {
        this.branchName = branchName;
    }

    public String getBranchName()
    {
        return branchName;
    }

    public void setComments(String comments)
    {
        this.comments = comments;
    }

    public String getComments()
    {
        return comments;
    }

    public void setAllocatedAmt(double allocatedAmt)
    {
        this.allocatedAmt = allocatedAmt;
    }

    public double getAllocatedAmt()
    {
        return allocatedAmt;
    }

    public void setNeftCode(String neftCode)
    {
        this.neftCode = neftCode;
    }

    public String getNeftCode()
    {
        return neftCode;
    }

    public Date getPaymentInitiateDate()
    {
        return paymentInitiateDate;
    }

    public void setPaymentInitiateDate(Date paymentInitiateDate)
    {
        this.paymentInitiateDate = paymentInitiateDate;
    }

    public TreeMap getAllocationPaymentDans()
    {
        return allocationPaymentDans;
    }

    public void setAllocationPaymentDans(TreeMap allocationPaymentDans)
    {
        this.allocationPaymentDans = allocationPaymentDans;
    }

    public TreeMap getAllocationPaymentFinalSubmit1()
    {
        return allocationPaymentFinalSubmit1;
    }

    public void setAllocationPaymentFinalSubmit1(TreeMap allocationPaymentFinalSubmit1)
    {
        this.allocationPaymentFinalSubmit1 = allocationPaymentFinalSubmit1;
    }

    public TreeMap getAllocationPaymentFinalSubmit2()
    {
        return allocationPaymentFinalSubmit2;
    }

    public void setAllocationPaymentFinalSubmit2(TreeMap allocationPaymentFinalSubmit2)
    {
        this.allocationPaymentFinalSubmit2 = allocationPaymentFinalSubmit2;
    }

    public TreeMap getAllocationPaymentFinalSubmit3()
    {
        return allocationPaymentFinalSubmit3;
    }

    public void setAllocationPaymentFinalSubmit3(TreeMap allocationPaymentFinalSubmit3)
    {
        this.allocationPaymentFinalSubmit3 = allocationPaymentFinalSubmit3;
    }

    public ArrayList getMakepaymentList()
    {
        return makepaymentList;
    }

    public void setMakepaymentList(ArrayList makepaymentList)
    {
        this.makepaymentList = makepaymentList;
    }

    public TreeMap getAllocationPaymentFinalSubmit()
    {
        return allocationPaymentFinalSubmit;
    }

    public void setAllocationPaymentFinalSubmit(TreeMap allocationPaymentFinalSubmit)
    {
        this.allocationPaymentFinalSubmit = allocationPaymentFinalSubmit;
    }

    public TreeMap getAllocationPaymentYes()
    {
        return allocationPaymentYes;
    }

    public void setAllocationPaymentYes(TreeMap allocationPaymentYes)
    {
        this.allocationPaymentYes = allocationPaymentYes;
    }

    public String getVaccno()
    {
        return vaccno;
    }

    public void setVaccno(String vaccno)
    {
        this.vaccno = vaccno;
    }

    public String getVaccnumber()
    {
        return vaccnumber;
    }

    public void setVaccnumber(String vaccnumber)
    {
        this.vaccnumber = vaccnumber;
    }

    public String getPaymentId1()
    {
        return paymentId1;
    }

    public void setPaymentId1(String paymentId1)
    {
        this.paymentId1 = paymentId1;
    }

    public String getAllocatedanIds()
    {
        return allocatedanIds;
    }

    public void setAllocatedanIds(String allocatedanIds)
    {
        this.allocatedanIds = allocatedanIds;
    }

    public ArrayList getAllocatedanIdsDetails()
    {
        return allocatedanIdsDetails;
    }

    public void setAllocatedanIdsDetails(ArrayList allocatedanIdsDetails)
    {
        this.allocatedanIdsDetails = allocatedanIdsDetails;
    }

    public String getPaymentIdR()
    {
        return paymentIdR;
    }

    public void setPaymentIdR(String paymentIdR)
    {
        this.paymentIdR = paymentIdR;
    }

    public String getPaymentIds()
    {
        return paymentIds;
    }

    public void setPaymentIds(String paymentIds)
    {
        this.paymentIds = paymentIds;
    }

    public ArrayList getMakepaymentFinal()
    {
        return makepaymentFinal;
    }

    public void setMakepaymentFinal(ArrayList makepaymentFinal)
    {
        this.makepaymentFinal = makepaymentFinal;
    }

    public ArrayList getAllocatepaymentFinal()
    {
        return allocatepaymentFinal;
    }

    public void setAllocatepaymentFinal(ArrayList allocatepaymentFinal)
    {
        this.allocatepaymentFinal = allocatepaymentFinal;
    }

    public String getPaymentIdF()
    {
        return PaymentIdF;
    }

    public void setPaymentIdF(String paymentIdF)
    {
        PaymentIdF = paymentIdF;
    }

    public String getVitrualAcF()
    {
        return VitrualAcF;
    }

    public void setVitrualAcF(String vitrualAcF)
    {
        VitrualAcF = vitrualAcF;
    }

    public double getAmtF()
    {
        return AmtF;
    }

    public void setAmtF(double amtF)
    {
        AmtF = amtF;
    }

    public String getRPDATEF()
    {
        return RPDATEF;
    }

    public void setRPDATEF(String rPDATEF)
    {
        RPDATEF = rPDATEF;
    }

    public String getStatusF()
    {
        return statusF;
    }

    public void setStatusF(String statusF)
    {
        this.statusF = statusF;
    }

    public double getAmmount1()
    {
        return ammount1;
    }

    public void setAmmount1(double d)
    {
        ammount1 = d;
    }

    public String getPayidcreateddate()
    {
        return payidcreateddate;
    }

    public void setPayidcreateddate(String payidcreateddate)
    {
        this.payidcreateddate = payidcreateddate;
    }

    public ArrayList getAllocatepayIDdetails()
    {
        return allocatepayIDdetails;
    }

    public void setAllocatepayIDdetails(ArrayList allocatepayIDdetails)
    {
        this.allocatepayIDdetails = allocatepayIDdetails;
    }

    public ArrayList getAllocatepaymentmodify()
    {
        return allocatepaymentmodify;
    }

    public void setAllocatepaymentmodify(ArrayList allocatepaymentmodify)
    {
        this.allocatepaymentmodify = allocatepaymentmodify;
    }

    public String getCgpan1()
    {
        return cgpan1;
    }

    public void setCgpan1(String cgpan1)
    {
        this.cgpan1 = cgpan1;
    }

    public String getDanid1()
    {
        return danid1;
    }

    public void setDanid1(String danid1)
    {
        this.danid1 = danid1;
    }

    public String getDandate1()
    {
        return dandate1;
    }

    public void setDandate1(String dandate1)
    {
        this.dandate1 = dandate1;
    }

    public double getAmmount2()
    {
        return ammount2;
    }

    public void setAmmount2(double ammount2)
    {
        this.ammount2 = ammount2;
    }

    public TreeMap getDansList()
    {
        return dansList;
    }

    public void setDansList(TreeMap dansList)
    {
        this.dansList = dansList;
    }



	public String getRPNumber() {
		return RPNumber;
	}



	public void setRPNumber(String rPNumber) {
		RPNumber = rPNumber;
	}



	public int getListSize() {
		return listSize;
	}



	public void setListSize(int listSize) {
		this.listSize = listSize;
	}
}