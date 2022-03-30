package com.cgtsi.actionform;

import java.util.ArrayList;
import java.util.Date;
import java.util.Map;
import org.apache.struts.validator.ValidatorActionForm;

public class DisbursementActionForm extends ValidatorActionForm {

	private static final long serialVersionUID = 1L;
	private String promoterName;
	private Map<String, String> promoterNameList;
	private String cgpan;
	private ArrayList list;
	private ArrayList promoterValues;
	private ArrayList promoterDetails;
	private double guaranteedAmt;
	private String roleName;
	private String promoterNameValue;
	private Object disrseData;
	private String isSearchClicked;
	private String disbursementData;
	private String message;
	private ArrayList disurseData;
	private Date cpDOB;
	private String startGuaranteeDate;
	private String sanctionDate;
	private String check;
	
	
	
	public String getCheck() {
		return check;
	}

	public void setCheck(String check) {
		this.check = check;
	}

	public String getStartGuaranteeDate() {
		return startGuaranteeDate;
	}

	public void setStartGuaranteeDate(String startGuaranteeDate) {
		this.startGuaranteeDate = startGuaranteeDate;
	}

	public String getSanctionDate() {
		return sanctionDate;
	}

	public void setSanctionDate(String sanctionDate) {
		this.sanctionDate = sanctionDate;
	}

	public Date getCpDOB() {
		return cpDOB;
	}

	public void setCpDOB(Date cpDOB) {
		this.cpDOB = cpDOB;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public ArrayList getDisurseData() {
		return disurseData;
	}

	public void setDisurseData(ArrayList disurseData) {
		this.disurseData = disurseData;
	}

	public String getDisbursementData() {
		return disbursementData;
	}

	public void setDisbursementData(String disbursementData) {
		this.disbursementData = disbursementData;
	}
	public ArrayList getPromoterValues() {
		return promoterValues;
	}

	public void setPromoterValues(ArrayList promoterValues) {
		this.promoterValues = promoterValues;
	}

	public String getPromoterName() {
		return promoterName;
	}

	public void setPromoterName(String promoterName) {
		this.promoterName = promoterName;
	}

	public String getCgpan() {
		return cgpan;
	}

	public void setCgpan(String cgpan) {
		this.cgpan = cgpan;
	}

	public Map<String, String> getPromoterNameList() {
		return promoterNameList;
	}

	public void setPromoterNameList(Map<String, String> promoterNameList) {
		this.promoterNameList = promoterNameList;
	}

	public ArrayList getList() {
		return list;
	}

	public void setList(ArrayList list) {
		this.list = list;
	}

	public ArrayList getPromoterDetails() {
		return promoterDetails;
	}

	public void setPromoterDetails(ArrayList promoterDetails) {
		this.promoterDetails = promoterDetails;
	}

	public double getGuaranteedAmt() {
		return guaranteedAmt;
	}

	public void setGuaranteedAmt(double guaranteedAmt) {
		this.guaranteedAmt = guaranteedAmt;
	}

	public String getRoleName() {
		return roleName;
	}

	public void setRoleName(String roleName) {
		this.roleName = roleName;
	}

	public String getPromoterNameValue() {
		return promoterNameValue;
	}

	public void setPromoterNameValue(String promoterNameValue) {
		this.promoterNameValue = promoterNameValue;
	}

	public Object getDisrseData() {
		return disrseData;
	}

	public void setDisrseData(Object disrseData) {
		this.disrseData = disrseData;
	}

	public String getIsSearchClicked() {
		return isSearchClicked;
	}

	public void setIsSearchClicked(String isSearchClicked) {
		this.isSearchClicked = isSearchClicked;
	}
}
