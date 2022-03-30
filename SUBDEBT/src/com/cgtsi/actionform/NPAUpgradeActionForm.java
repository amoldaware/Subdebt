package com.cgtsi.actionform;

import java.util.ArrayList;

import org.apache.struts.validator.ValidatorActionForm;

import com.cgtsi.action.BaseAction;

public class NPAUpgradeActionForm extends ValidatorActionForm
{
	private static final long serialVersionUID = 1L;
	private String cgpan;
	private String promoterName;
	private String message;
	private ArrayList promoterValues;
	private String isSearchClicked;
	private String npa_date;
	private String roleName;
	private String npaUpgradation_date;
	private String reasonforUpgradation;
	private ArrayList npaPopulateData;
	
	public ArrayList getNpaPopulateData() {
		return npaPopulateData;
	}
	public void setNpaPopulateData(ArrayList npaPopulateData) {
		this.npaPopulateData = npaPopulateData;
	}
	private int npaId;
	
	public int getNpaId() {
		return npaId;
	}
	public void setNpaId(int npaId) {
		this.npaId = npaId;
	}
	public String getNpa_date() {
		return npa_date;
	}
	public void setNpa_date(String npa_date) {
		this.npa_date = npa_date;
	}
	public String getRoleName() {
		return roleName;
	}
	public void setRoleName(String roleName) {
		this.roleName = roleName;
	}
	public String getNpaUpgradation_date() {
		return npaUpgradation_date;
	}
	public void setNpaUpgradation_date(String npaUpgradation_date) {
		this.npaUpgradation_date = npaUpgradation_date;
	}
	public String getReasonforUpgradation() {
		return reasonforUpgradation;
	}
	public void setReasonforUpgradation(String reasonforUpgradation) {
		this.reasonforUpgradation = reasonforUpgradation;
	}
	public String getIsSearchClicked() {
		return isSearchClicked;
	}
	public void setIsSearchClicked(String isSearchClicked) {
		this.isSearchClicked = isSearchClicked;
	}
	public ArrayList getPromoterValues() {
		return promoterValues;
	}
	public void setPromoterValues(ArrayList promoterValues) {
		this.promoterValues = promoterValues;
	}
	public String getCgpan() {
		return cgpan;
	}
	public void setCgpan(String cgpan) {
		this.cgpan = cgpan;
	}
	public String getPromoterName() {
		return promoterName;
	}
	public void setPromoterName(String promoterName) {
		this.promoterName = promoterName;
	}
	public String getMessage() {
		return message;
	}
	public void setMessage(String message) {
		this.message = message;
	}
}
