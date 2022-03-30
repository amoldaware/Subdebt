package com.cgtsi.actionform;

import java.util.ArrayList;
import java.util.Date;

import org.apache.struts.validator.ValidatorActionForm;

public class NPAReportForm extends ValidatorActionForm{
	private static final long serialVersionUID = 1L;
	private String mliName;
	private String cgpan;
	private String promoterName;
	private String guranteeStatus;
	private String promoter_itpan;
	private String mliId;
	private String loanAccountNo;
	private String guaranteeStartDate;
	private String guaranteeEndDate;
	private String npaStatus;
	private String reportType;
	private String message;
	private String loginType;
	private ArrayList mliDetals;
	private ArrayList promoterValues;
	private String isSearchClicked;
	private int listCount;
	
	
	public String getMliName() {
		return mliName;
	}
	public void setMliName(String mliName) {
		this.mliName = mliName;
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
	public String getGuranteeStatus() {
		return guranteeStatus;
	}
	public void setGuranteeStatus(String guranteeStatus) {
		this.guranteeStatus = guranteeStatus;
	}
	public String getPromoter_itpan() {
		return promoter_itpan;
	}
	public void setPromoter_itpan(String promoter_itpan) {
		this.promoter_itpan = promoter_itpan;
	}
	public String getMliId() {
		return mliId;
	}
	public void setMliId(String mliId) {
		this.mliId = mliId;
	}
	public String getLoanAccountNo() {
		return loanAccountNo;
	}
	public void setLoanAccountNo(String loanAccountNo) {
		this.loanAccountNo = loanAccountNo;
	}
	public String getGuaranteeStartDate() {
		return guaranteeStartDate;
	}
	public void setGuaranteeStartDate(String guaranteeStartDate) {
		this.guaranteeStartDate = guaranteeStartDate;
	}
	public String getGuaranteeEndDate() {
		return guaranteeEndDate;
	}
	public void setGuaranteeEndDate(String guaranteeEndDate) {
		this.guaranteeEndDate = guaranteeEndDate;
	}
	public String getNpaStatus() {
		return npaStatus;
	}
	public void setNpaStatus(String npaStatus) {
		this.npaStatus = npaStatus;
	}
	public String getReportType() {
		return reportType;
	}
	public void setReportType(String reportType) {
		this.reportType = reportType;
	}
	public String getMessage() {
		return message;
	}
	public void setMessage(String message) {
		this.message = message;
	}
	public ArrayList getMliDetals() {
		return mliDetals;
	}
	public void setMliDetals(ArrayList mliDetals) {
		this.mliDetals = mliDetals;
	}
	public String getLoginType() {
		return loginType;
	}
	public void setLoginType(String loginType) {
		this.loginType = loginType;
	}
	public ArrayList getPromoterValues() {
		return promoterValues;
	}
	public void setPromoterValues(ArrayList promoterValues) {
		this.promoterValues = promoterValues;
	}
	public String getIsSearchClicked() {
		return isSearchClicked;
	}
	public void setIsSearchClicked(String isSearchClicked) {
		this.isSearchClicked = isSearchClicked;
	}
	public int getListCount() {
		return listCount;
	}
	public void setListCount(int listCount) {
		this.listCount = listCount;
	}
}
