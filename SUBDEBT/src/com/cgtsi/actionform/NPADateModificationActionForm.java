package com.cgtsi.actionform;

import java.util.Date;

import org.apache.struts.validator.ValidatorActionForm;

public class NPADateModificationActionForm  extends ValidatorActionForm {

	
	private String mliID;
	private String npaID;
	
	private String lstNpaDate;
	public String getLstNpaDate() {
		return lstNpaDate;
	}
	public void setLstNpaDate(String lstNpaDate) {
		this.lstNpaDate = lstNpaDate;
	}
	public String getNpaID() {
		return npaID;
	}
	public void setNpaID(String npaID) {
		this.npaID = npaID;
	}
	private String cgPan;
	private String unitName;
	private String bankName;
	private String applicationStatus;
	public String getApplicationStatus() {
		return applicationStatus;
	}
	public void setApplicationStatus(String applicationStatus) {
		this.applicationStatus = applicationStatus;
	}
	private Date npaDate;
	
	public Date getNpaDate() {
		return npaDate;
	}
	public void setNpaDate(Date npaDate) {
		this.npaDate = npaDate;
	}
	private Date npaUpgradationDate;
	private Date newNpaDate;
	private String reasonForAccNpa;
	private String remarks;
	public String getMliID() {
		return mliID;
	}
	public void setMliID(String mliID) {
		this.mliID = mliID;
	}
	public String getCgPan() {
		return cgPan;
	}
	public void setCgPan(String cgPan) {
		this.cgPan = cgPan;
	}
	public String getUnitName() {
		return unitName;
	}
	public void setUnitName(String unitName) {
		this.unitName = unitName;
	}
	public String getBankName() {
		return bankName;
	}
	public void setBankName(String bankName) {
		this.bankName = bankName;
	}
	
	
	public Date getNpaUpgradationDate() {
		return npaUpgradationDate;
	}
	public void setNpaUpgradationDate(Date npaUpgradationDate) {
		this.npaUpgradationDate = npaUpgradationDate;
	}
	public Date getNewNpaDate() {
		return newNpaDate;
	}
	public void setNewNpaDate(Date newNpaDate) {
		this.newNpaDate = newNpaDate;
	}
	public String getReasonForAccNpa() {
		return reasonForAccNpa;
	}
	public void setReasonForAccNpa(String reasonForAccNpa) {
		this.reasonForAccNpa = reasonForAccNpa;
	}
	public String getRemarks() {
		return remarks;
	}
	public void setRemarks(String remarks) {
		this.remarks = remarks;
	}
}
