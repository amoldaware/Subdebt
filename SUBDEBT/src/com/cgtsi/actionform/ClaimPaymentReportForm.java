package com.cgtsi.actionform;

import java.util.ArrayList;

import org.apache.struts.validator.ValidatorActionForm;

public class ClaimPaymentReportForm extends ValidatorActionForm
{
	private static final long serialVersionUID = 1L;
	private String claimPaymentMLIName;
	private String claimPaymentCGPAN;
	private String claimPaymentPromoterName;
	private String claimPaymentClaimStatus;
	private String claimPaymentMLIId;
	private String claimPaymentClaimRefNo;
	private String claimPaymentFromDate;
	private String claimPaymentToDate;
	private String claimPaymentPromoterITPAN;
	private String claimPaymentisSearchClicked;
	private String claimPaymentLoginType;
	private String claimPaymentClaimType;
	private ArrayList claimPaymentReportList;
	private ArrayList claimPaymentPromoterValues;
	
	private String message;
	private int claimPaymentListCount;
	public String getClaimPaymentMLIName() {
		return claimPaymentMLIName;
	}
	public String getClaimPaymentCGPAN() {
		return claimPaymentCGPAN;
	}
	public String getClaimPaymentPromoterName() {
		return claimPaymentPromoterName;
	}
	public String getClaimPaymentClaimStatus() {
		return claimPaymentClaimStatus;
	}
	public String getClaimPaymentMLIId() {
		return claimPaymentMLIId;
	}
	public String getClaimPaymentClaimRefNo() {
		return claimPaymentClaimRefNo;
	}
	public String getClaimPaymentFromDate() {
		return claimPaymentFromDate;
	}
	public String getClaimPaymentToDate() {
		return claimPaymentToDate;
	}
	public String getClaimPaymentPromoterITPAN() {
		return claimPaymentPromoterITPAN;
	}
	public String getClaimPaymentisSearchClicked() {
		return claimPaymentisSearchClicked;
	}
	public String getClaimPaymentLoginType() {
		return claimPaymentLoginType;
	}
	public ArrayList getClaimPaymentReportList() {
		return claimPaymentReportList;
	}
	public int getClaimPaymentListCount() {
		return claimPaymentListCount;
	}
	public void setClaimPaymentMLIName(String claimPaymentMLIName) {
		this.claimPaymentMLIName = claimPaymentMLIName;
	}
	public void setClaimPaymentCGPAN(String claimPaymentCGPAN) {
		this.claimPaymentCGPAN = claimPaymentCGPAN;
	}
	public void setClaimPaymentPromoterName(String claimPaymentPromoterName) {
		this.claimPaymentPromoterName = claimPaymentPromoterName;
	}
	public void setClaimPaymentClaimStatus(String claimPaymentClaimStatus) {
		this.claimPaymentClaimStatus = claimPaymentClaimStatus;
	}
	public void setClaimPaymentMLIId(String claimPaymentMLIId) {
		this.claimPaymentMLIId = claimPaymentMLIId;
	}
	public void setClaimPaymentClaimRefNo(String claimPaymentClaimRefNo) {
		this.claimPaymentClaimRefNo = claimPaymentClaimRefNo;
	}
	public void setClaimPaymentFromDate(String claimPaymentFromDate) {
		this.claimPaymentFromDate = claimPaymentFromDate;
	}
	public void setClaimPaymentToDate(String claimPaymentToDate) {
		this.claimPaymentToDate = claimPaymentToDate;
	}
	public void setClaimPaymentPromoterITPAN(String claimPaymentPromoterITPAN) {
		this.claimPaymentPromoterITPAN = claimPaymentPromoterITPAN;
	}
	public void setClaimPaymentisSearchClicked(String claimPaymentisSearchClicked) {
		this.claimPaymentisSearchClicked = claimPaymentisSearchClicked;
	}
	public void setClaimPaymentLoginType(String claimPaymentLoginType) {
		this.claimPaymentLoginType = claimPaymentLoginType;
	}
	public void setClaimPaymentReportList(ArrayList claimPaymentReportList) {
		this.claimPaymentReportList = claimPaymentReportList;
	}
	public void setClaimPaymentListCount(int claimPaymentListCount) {
		this.claimPaymentListCount = claimPaymentListCount;
	}
	public String getMessage() {
		return message;
	}
	public void setMessage(String message) {
		this.message = message;
	}
	public ArrayList getClaimPaymentPromoterValues() {
		return claimPaymentPromoterValues;
	}
	public void setClaimPaymentPromoterValues(ArrayList claimPaymentPromoterValues) {
		this.claimPaymentPromoterValues = claimPaymentPromoterValues;
	}
	public String getClaimPaymentClaimType() {
		return claimPaymentClaimType;
	}
	public void setClaimPaymentClaimType(String claimPaymentClaimType) {
		this.claimPaymentClaimType = claimPaymentClaimType;
	}
	
}
