package com.cgtsi.actionform;

import java.util.Date;

import org.apache.struts.validator.ValidatorActionForm;

public class MLIDeatils extends ValidatorActionForm {

	private String cgPan;
	private String unitName;
	private String memberID;
	private String applicationStatus;
	private int existingTenure;
	private String reviseExpiryDate;
	private int reviseTenure;
	private String sanctionDate;
	private String modificationRemark;
	
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
	public String getMemberID() {
		return memberID;
	}
	public void setMemberID(String memberID) {
		this.memberID = memberID;
	}
	public String getApplicationStatus() {
		return applicationStatus;
	}
	public void setApplicationStatus(String applicationStatus) {
		this.applicationStatus = applicationStatus;
	}
	public int getExistingTenure() {
		return existingTenure;
	}
	public void setExistingTenure(int existingTenure) {
		this.existingTenure = existingTenure;
	}
	
	public int getReviseTenure() {
		return reviseTenure;
	}
	public void setReviseTenure(int reviseTenure) {
		this.reviseTenure = reviseTenure;
	}

	public String getModificationRemark() {
		return modificationRemark;
	}
	public void setModificationRemark(String modificationRemark) {
		this.modificationRemark = modificationRemark;
	}
	public String getReviseExpiryDate() {
		return reviseExpiryDate;
	}
	public void setReviseExpiryDate(String reviseExpiryDate) {
		this.reviseExpiryDate = reviseExpiryDate;
	}
	public String getSanctionDate() {
		return sanctionDate;
	}
	public void setSanctionDate(String sanctionDate) {
		this.sanctionDate = sanctionDate;
	}
	
	
	
}
