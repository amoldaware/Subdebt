package com.cgtsi.actionform;

import java.io.Serializable;
import java.util.Date;

public class NPAUpgradationActionForm implements Serializable {

	private String[] unitName;
	public String[] getUnitName() {
		return unitName;
	}
	public void setUnitName(String[] unitName) {
		this.unitName = unitName;
	}
	public String[] getBankName() {
		return bankName;
	}
	public void setBankName(String[] bankName) {
		this.bankName = bankName;
	}
	public String[] getCgpan() {
		return cgpan;
	}
	public void setCgpan(String[] cgpan) {
		this.cgpan = cgpan;
	}
	public String[] getMliid() {
		return mliid;
	}
	public void setMliid(String[] mliid) {
		this.mliid = mliid;
	}
	public String[] getApplicationStatus() {
		return applicationStatus;
	}
	public void setApplicationStatus(String[] applicationStatus) {
		this.applicationStatus = applicationStatus;
	}
	public String[] getLastNpaReportedDate() {
		return lastNpaReportedDate;
	}
	public void setLastNpaReportedDate(String[] lastNpaReportedDate) {
		this.lastNpaReportedDate = lastNpaReportedDate;
	}
	public String[] getReasonForTurningNpa() {
		return reasonForTurningNpa;
	}
	public void setReasonForTurningNpa(String[] reasonForTurningNpa) {
		this.reasonForTurningNpa = reasonForTurningNpa;
	}
	public Date getNpaUpgradationDate() {
		return npaUpgradationDate;
	}
	public void setNpaUpgradationDate(Date npaUpgradationDate) {
		this.npaUpgradationDate = npaUpgradationDate;
	}
	public String getRemark() {
		return remark;
	}
	public void setRemark(String remark) {
		this.remark = remark;
	}
	private String[] bankName;
	private String[] cgpan;
	private String[] mliid;
	private String[] applicationStatus;
	private String[] lastNpaReportedDate;
	private String[] reasonForTurningNpa;
	private Date npaUpgradationDate;
	private String remark;
	private String strCgpan;
	private String NPAformType;
	public String getNPAformType() {
		return NPAformType;
	}
	public void setNPAformType(String nPAformType) {
		NPAformType = nPAformType;
	}
	public String getStrCgpan() {
		return strCgpan;
	}
	public void setStrCgpan(String strCgpan) {
		this.strCgpan = strCgpan;
	}
	
}
