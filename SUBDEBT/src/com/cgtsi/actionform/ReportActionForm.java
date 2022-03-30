package com.cgtsi.actionform;

import com.cgtsi.guaranteemaintenance.LegalSuitDetail;
import com.cgtsi.guaranteemaintenance.NPADetails;
import com.cgtsi.guaranteemaintenance.RecoveryProcedure;
import com.cgtsi.reports.QueryBuilderFields;


import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.apache.struts.validator.ValidatorActionForm;

public class ReportActionForm extends ValidatorActionForm
{
	
	private java.util.Date  invoiceFromDate;
	private java.util.Date  invoiceToDate;
  private String MLI_Name_Report;
		  private ArrayList invoiceDetails;
		  private String Invoice_ID;
		  private String appRefNo;
		  private String stateName;
		  private String bankName;
		  private String month;
		  
		  public String getAppRefNo() {
			return appRefNo;
		}

		public void setAppRefNo(String appRefNo) {
			this.appRefNo = appRefNo;
		}

		private Date Invoice_date;
		private Date dtFromDate;
		private Date dtToDate;
		  private String DAN_NUMBER;
		  private float Guarantee_Amount;
		  private String Source_State;
		  private String Supply_State;
		  private float IGST_RATE;
		  private float IGST_AMT;
		  private float CGST_RATE;
		  private float CGST_AMT;
		  private float SGST_RATE;
		  private float SGST_AMT;
		  private float Invoice_Amount;
		  private String Created_By;
		  private String CGPAN;
		  private String App_ref_no;
		  private String Ssi_unit_name;
		  private String Ssi_state_name;
		  private String Gst_no;
		  private float App_approved_amount;
		  private Date App_approved_date_time;
		  private String term_tenure;
		  private Date application_submit_date;
		  private double taxable_amount;
		  private String State;
		  private String zoneBranch;
		  private String address;
		  private String city;
		  private String pinCode;
		  
		  private String APP_REF_NO;
		  private String cgpan1;
		  private double approved_amt;
		  private String danId;
		  private double danAmount;
		  private String unitItpan;
		  private String APP_STATUS;
		  private String APP_REMARKS;
		  private String RP_NUMBER;
		  private String UNIT_NAME;
		  private String app_date;
		  private String APPROPRIATION_DATE;
		  private String DAN_STATUS;
		  private String guarantee_date;
		  private String GUARANTEE_DATE;
		  private String APPROPRIATED_BY;
		  private ArrayList<ReportActionForm> allocatepaymentFinal = new ArrayList<ReportActionForm>();
		  
		  
  public String getZoneBranch() {
			return zoneBranch;
		}

		public void setZoneBranch(String zoneBranch) {
			this.zoneBranch = zoneBranch;
		}

		public String getAddress() {
			return address;
		}

		public void setAddress(String address) {
			this.address = address;
		}

		public String getCity() {
			return city;
		}

		public void setCity(String city) {
			this.city = city;
		}

		public String getPinCode() {
			return pinCode;
		}

		public void setPinCode(String pinCode) {
			this.pinCode = pinCode;
		}

public String getState() {
			return State;
		}

		public void setState(String state) {
			State = state;
		}

private QueryBuilderFields queryBuilderFields = new QueryBuilderFields();
  private ArrayList queryReport = new ArrayList();
  private String memberId;
  private String borrowerId;
  private String cgpan;
  private String borrowerName;
  private String payId;
  private ArrayList borrowerDetailsForPIReport = new ArrayList();

  private ArrayList osPeriodicInfoDetails = new ArrayList();
  private ArrayList disbPeriodicInfoDetails = new ArrayList();
  private ArrayList repayPeriodicInfoDetails = new ArrayList();
  private NPADetails npaDetails = new NPADetails();
  private LegalSuitDetail legalSuitDetail = new LegalSuitDetail();
  private RecoveryProcedure recoveryProcedure = new RecoveryProcedure();
  private ArrayList recoveryProcedures = new ArrayList();
  private ArrayList recoveryDetails = new ArrayList();

  public void setPayId(String payId)
  {
    this.payId = payId;
  }

  public String getPayId()
  {
    return this.payId;
  }
  
  private String itPanVal;
	 private String unitNameVal="";    
	 private String appRefNoVal="";    
	 private String CGPANVal="";    
	 private String loanAccVal="";    
  private List colletralCoulmnName = new ArrayList();
	private List colletralCoulmnValue = new ArrayList();
	private List reportTypeList = new ArrayList();
	private java.util.Date dateOfTheDocument20;
	private java.util.Date dateOfTheDocument21;
	private java.util.Date dateOfTheDocument26;
	private java.util.Date dateOfTheDocument27;
	private String checkValue;
	private String itpan;
	 private List bulkUploadReportName = new ArrayList();
		private List bulkUploadReportValue = new ArrayList();
		private String reportTypeList2;
		private String reportTypeList1;
		
		 private String zoneId;
		  private String bankId;
		  
		  
		  
		  
		  
		  public java.util.Date getInvoiceFromDate() {
			return invoiceFromDate;
		}

		public void setInvoiceFromDate(java.util.Date invoiceFromDate) {
			this.invoiceFromDate = invoiceFromDate;
		}

		public java.util.Date getInvoiceToDate() {
			return invoiceToDate;
		}

		public void setInvoiceToDate(java.util.Date invoiceToDate) {
			this.invoiceToDate = invoiceToDate;
		}

		public String getMLI_Name_Report() {
			return MLI_Name_Report;
		}

		public void setMLI_Name_Report(String mLI_Name_Report) {
			MLI_Name_Report = mLI_Name_Report;
		}

		public ArrayList getInvoiceDetails() {
			return invoiceDetails;
		}

		public void setInvoiceDetails(ArrayList invoiceDetails) {
			this.invoiceDetails = invoiceDetails;
		}

		public String getInvoice_ID() {
			return Invoice_ID;
		}

		public void setInvoice_ID(String invoice_ID) {
			Invoice_ID = invoice_ID;
		}

		public Date getInvoice_date() {
			return Invoice_date;
		}

		public void setInvoice_date(Date invoice_date) {
			Invoice_date = invoice_date;
		}

		public String getDAN_NUMBER() {
			return DAN_NUMBER;
		}

		public void setDAN_NUMBER(String dAN_NUMBER) {
			DAN_NUMBER = dAN_NUMBER;
		}

		public float getGuarantee_Amount() {
			return Guarantee_Amount;
		}

		public void setGuarantee_Amount(float guarantee_Amount) {
			Guarantee_Amount = guarantee_Amount;
		}

		public String getSource_State() {
			return Source_State;
		}

		public void setSource_State(String source_State) {
			Source_State = source_State;
		}

		public String getSupply_State() {
			return Supply_State;
		}

		public void setSupply_State(String supply_State) {
			Supply_State = supply_State;
		}

		public float getIGST_RATE() {
			return IGST_RATE;
		}

		public void setIGST_RATE(float iGST_RATE) {
			IGST_RATE = iGST_RATE;
		}

		public float getIGST_AMT() {
			return IGST_AMT;
		}

		public void setIGST_AMT(float iGST_AMT) {
			IGST_AMT = iGST_AMT;
		}

		public float getCGST_RATE() {
			return CGST_RATE;
		}

		public void setCGST_RATE(float cGST_RATE) {
			CGST_RATE = cGST_RATE;
		}

		public float getCGST_AMT() {
			return CGST_AMT;
		}

		public void setCGST_AMT(float cGST_AMT) {
			CGST_AMT = cGST_AMT;
		}

		public float getSGST_RATE() {
			return SGST_RATE;
		}

		public void setSGST_RATE(float sGST_RATE) {
			SGST_RATE = sGST_RATE;
		}

		public float getSGST_AMT() {
			return SGST_AMT;
		}

		public void setSGST_AMT(float sGST_AMT) {
			SGST_AMT = sGST_AMT;
		}

		public float getInvoice_Amount() {
			return Invoice_Amount;
		}

		public void setInvoice_Amount(float invoice_Amount) {
			Invoice_Amount = invoice_Amount;
		}

		public String getCreated_By() {
			return Created_By;
		}

		public void setCreated_By(String created_By) {
			Created_By = created_By;
		}

		public String getAppRefNoVal() {
			return appRefNoVal;
		}

		public void setAppRefNoVal(String appRefNoVal) {
			this.appRefNoVal = appRefNoVal;
		}

		public String getCGPANVal() {
			return CGPANVal;
		}

		public void setCGPANVal(String cGPANVal) {
			CGPANVal = cGPANVal;
		}

		public String getLoanAccVal() {
			return loanAccVal;
		}

		public void setLoanAccVal(String loanAccVal) {
			this.loanAccVal = loanAccVal;
		}

		public String getItPanVal() {
			return itPanVal;
		}

		public void setItPanVal(String itPanVal) {
			this.itPanVal = itPanVal;
		}

		public String getUnitNameVal() {
			return unitNameVal;
		}

		public void setUnitNameVal(String unitNameVal) {
			this.unitNameVal = unitNameVal;
		}

		public String getBankId() {
			return bankId;
		}
		public void setBankId(String bankId) {
			this.bankId = bankId;
		}
		public String getZoneId() {
			return zoneId;
		}
		public void setZoneId(String zoneId) {
			this.zoneId = zoneId;
		}
	
	public List getBulkUploadReportName() {
			return bulkUploadReportName;
		}

		public void setBulkUploadReportName(List bulkUploadReportName) {
			this.bulkUploadReportName = bulkUploadReportName;
		}

		public List getBulkUploadReportValue() {
			return bulkUploadReportValue;
		}

		public void setBulkUploadReportValue(List bulkUploadReportValue) {
			this.bulkUploadReportValue = bulkUploadReportValue;
		}

	public String getReportTypeList2() {
			return reportTypeList2;
		}

		public void setReportTypeList2(String reportTypeList2) {
			this.reportTypeList2 = reportTypeList2;
		}

		public String getReportTypeList1() {
			return reportTypeList1;
		}

		public void setReportTypeList1(String reportTypeList1) {
			this.reportTypeList1 = reportTypeList1;
		}

	public String getItpan() {
		return itpan;
	}
	public void setItpan(String itpan) {
		this.itpan = itpan;
	}
	public String getCheckValue() {
		return checkValue;
	}
	public void setCheckValue(String checkValue) {
		this.checkValue = checkValue;
	}
	
	public java.util.Date getDateOfTheDocument20() {
		return dateOfTheDocument20;
	}
	public void setDateOfTheDocument20(java.util.Date dateOfTheDocument20) {
		this.dateOfTheDocument20 = dateOfTheDocument20;
	}
	public java.util.Date getDateOfTheDocument21() {
		return dateOfTheDocument21;
	}
	public void setDateOfTheDocument21(java.util.Date dateOfTheDocument21) {
		this.dateOfTheDocument21 = dateOfTheDocument21;
	}

	
  public List getColletralCoulmnName() {
		return colletralCoulmnName;
	}
	public void setColletralCoulmnName(List colletralCoulmnName) {
		this.colletralCoulmnName = colletralCoulmnName;
	}
	public List getColletralCoulmnValue() {
		return colletralCoulmnValue;
	}
	public void setColletralCoulmnValue(List colletralCoulmnValue) {
		this.colletralCoulmnValue = colletralCoulmnValue;
	}
	public List getReportTypeList() {
		return reportTypeList;
	}
	public void setReportTypeList(List reportTypeList) {
		this.reportTypeList = reportTypeList;
	}

  public QueryBuilderFields getQueryBuilderFields()
  {
    return this.queryBuilderFields;
  }

  public void setQueryBuilderFields(QueryBuilderFields fields)
  {
    this.queryBuilderFields = fields;
  }

  public ArrayList getQueryReport()
  {
    return this.queryReport;
  }

  public void setQueryReport(ArrayList report)
  {
    this.queryReport = report;
  }

  public String getBorrowerId()
  {
    return this.borrowerId;
  }

  public String getBorrowerName()
  {
    return this.borrowerName;
  }

  public String getCgpan()
  {
    return this.cgpan;
  }

  public void setBorrowerId(String string)
  {
    this.borrowerId = string;
  }

  public void setBorrowerName(String string)
  {
    this.borrowerName = string;
  }

  public void setCgpan(String string)
  {
    this.cgpan = string;
  }

  public String getMemberId()
  {
    return this.memberId;
  }

  public void setMemberId(String string)
  {
    this.memberId = string;
  }

  public ArrayList getDisbPeriodicInfoDetails()
  {
    return this.disbPeriodicInfoDetails;
  }

  public ArrayList getOsPeriodicInfoDetails()
  {
    return this.osPeriodicInfoDetails;
  }

  public ArrayList getRepayPeriodicInfoDetails()
  {
    return this.repayPeriodicInfoDetails;
  }

  public void setDisbPeriodicInfoDetails(ArrayList list)
  {
    this.disbPeriodicInfoDetails = list;
  }

  public void setOsPeriodicInfoDetails(ArrayList list)
  {
    this.osPeriodicInfoDetails = list;
  }

  public void setRepayPeriodicInfoDetails(ArrayList list)
  {
    this.repayPeriodicInfoDetails = list;
  }

  public ArrayList getBorrowerDetailsForPIReport()
  {
    return this.borrowerDetailsForPIReport;
  }

  public void setBorrowerDetailsForPIReport(ArrayList list)
  {
    this.borrowerDetailsForPIReport = list;
  }

  public NPADetails getNpaDetails()
  {
    return this.npaDetails;
  }

  public ArrayList getRecoveryDetails()
  {
    return this.recoveryDetails;
  }

  public void setNpaDetails(NPADetails details)
  {
    this.npaDetails = details;
  }

  public void setRecoveryDetails(ArrayList list)
  {
    this.recoveryDetails = list;
  }

  public LegalSuitDetail getLegalSuitDetail()
  {
    return this.legalSuitDetail;
  }

  public RecoveryProcedure getRecoveryProcedure()
  {
    return this.recoveryProcedure;
  }

  public ArrayList getRecoveryProcedures()
  {
    return this.recoveryProcedures;
  }

  public void setLegalSuitDetail(LegalSuitDetail detail)
  {
    this.legalSuitDetail = detail;
  }

  public void setRecoveryProcedure(RecoveryProcedure procedure)
  {
    this.recoveryProcedure = procedure;
  }

  public void setRecoveryProcedures(ArrayList list)
  {
    this.recoveryProcedures = list;
  }

public java.util.Date getDateOfTheDocument26() {
	return dateOfTheDocument26;
}

public void setDateOfTheDocument26(java.util.Date dateOfTheDocument26) {
	this.dateOfTheDocument26 = dateOfTheDocument26;
}

public java.util.Date getDateOfTheDocument27() {
	return dateOfTheDocument27;
}

public void setDateOfTheDocument27(java.util.Date dateOfTheDocument27) {
	this.dateOfTheDocument27 = dateOfTheDocument27;
}

public String getApp_ref_no() {
	return App_ref_no;
}

public void setApp_ref_no(String app_ref_no) {
	App_ref_no = app_ref_no;
}

public String getSsi_unit_name() {
	return Ssi_unit_name;
}

public void setSsi_unit_name(String ssi_unit_name) {
	Ssi_unit_name = ssi_unit_name;
}

public String getSsi_state_name() {
	return Ssi_state_name;
}

public void setSsi_state_name(String ssi_state_name) {
	Ssi_state_name = ssi_state_name;
}

public String getGst_no() {
	return Gst_no;
}

public void setGst_no(String gst_no) {
	Gst_no = gst_no;
}



public Date getApp_approved_date_time() {
	return App_approved_date_time;
}

public void setApp_approved_date_time(Date app_approved_date_time) {
	App_approved_date_time = app_approved_date_time;
}

public String getTerm_tenure() {
	return term_tenure;
}

public void setTerm_tenure(String term_tenure) {
	this.term_tenure = term_tenure;
}


public String getCGPAN() {
	return CGPAN;
}

public void setCGPAN(String cGPAN) {
	CGPAN = cGPAN;
}

public float getApp_approved_amount() {
	return App_approved_amount;
}

public void setApp_approved_amount(float app_approved_amount) {
	App_approved_amount = app_approved_amount;
}

public double getTaxable_amount() {
	return taxable_amount;
}

public void setTaxable_amount(double taxable_amount) {
	this.taxable_amount = taxable_amount;
}

public Date getApplication_submit_date() {
	return application_submit_date;
}

public void setApplication_submit_date(Date application_submit_date) {
	this.application_submit_date = application_submit_date;
}

public String getAPP_REF_NO() {
	return APP_REF_NO;
}

public void setAPP_REF_NO(String aPP_REF_NO) {
	APP_REF_NO = aPP_REF_NO;
}

public String getCgpan1() {
	return cgpan1;
}

public void setCgpan1(String cgpan1) {
	this.cgpan1 = cgpan1;
}

public double getApproved_amt() {
	return approved_amt;
}

public void setApproved_amt(double approved_amt) {
	this.approved_amt = approved_amt;
}

public String getDanId() {
	return danId;
}

public void setDanId(String danId) {
	this.danId = danId;
}

public double getDanAmount() {
	return danAmount;
}

public void setDanAmount(double danAmount) {
	this.danAmount = danAmount;
}

public String getUnitItpan() {
	return unitItpan;
}

public void setUnitItpan(String unitItpan) {
	this.unitItpan = unitItpan;
}

public String getAPP_STATUS() {
	return APP_STATUS;
}

public void setAPP_STATUS(String aPP_STATUS) {
	APP_STATUS = aPP_STATUS;
}

public String getAPP_REMARKS() {
	return APP_REMARKS;
}

public void setAPP_REMARKS(String aPP_REMARKS) {
	APP_REMARKS = aPP_REMARKS;
}

public String getRP_NUMBER() {
	return RP_NUMBER;
}

public void setRP_NUMBER(String rP_NUMBER) {
	RP_NUMBER = rP_NUMBER;
}

public String getUNIT_NAME() {
	return UNIT_NAME;
}

public void setUNIT_NAME(String uNIT_NAME) {
	UNIT_NAME = uNIT_NAME;
}

public String getApp_date() {
	return app_date;
}

public void setApp_date(String app_date) {
	this.app_date = app_date;
}

public String getAPPROPRIATION_DATE() {
	return APPROPRIATION_DATE;
}

public void setAPPROPRIATION_DATE(String aPPROPRIATION_DATE) {
	APPROPRIATION_DATE = aPPROPRIATION_DATE;
}

public String getDAN_STATUS() {
	return DAN_STATUS;
}

public void setDAN_STATUS(String dAN_STATUS) {
	DAN_STATUS = dAN_STATUS;
}

public String getGuarantee_date() {
	return guarantee_date;
}

public void setGuarantee_date(String guarantee_date) {
	this.guarantee_date = guarantee_date;
}

public String getGUARANTEE_DATE() {
	return GUARANTEE_DATE;
}

public void setGUARANTEE_DATE(String gUARANTEE_DATE) {
	GUARANTEE_DATE = gUARANTEE_DATE;
}

public String getAPPROPRIATED_BY() {
	return APPROPRIATED_BY;
}

public void setAPPROPRIATED_BY(String aPPROPRIATED_BY) {
	APPROPRIATED_BY = aPPROPRIATED_BY;
}

public ArrayList<ReportActionForm> getAllocatepaymentFinal() {
	return allocatepaymentFinal;
}

public void setAllocatepaymentFinal(ArrayList<ReportActionForm> allocatepaymentFinal) {
	this.allocatepaymentFinal = allocatepaymentFinal;
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

public String getStateName() {
	return stateName;
}

public void setStateName(String stateName) {
	this.stateName = stateName;
}

public String getBankName() {
	return bankName;
}

public void setBankName(String bankName) {
	this.bankName = bankName;
}

public String getMonth() {
	return month;
}

public void setMonth(String month) {
	this.month = month;
}

}