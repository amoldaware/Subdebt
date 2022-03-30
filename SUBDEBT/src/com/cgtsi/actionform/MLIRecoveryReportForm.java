package com.cgtsi.actionform;

import java.util.ArrayList;

import org.apache.struts.validator.ValidatorActionForm;

public class MLIRecoveryReportForm extends ValidatorActionForm
{
	private static final long serialVersionUID = 1L;
	private String recMLIName;
	private String recCGPAN;
	private String recPromoterName;
	private String recClaimStatus;
	private String recMLIId;
	private String recClaimRefNo;
	private String recFromDate;
	private String recToDate;
	private String recPromoterITPAN;
	private String isSearchClicked;
	private String recLoginTypel;
	private ArrayList mliRecList;
	private ArrayList promoterValues;
	private String message;
	private int recListCount;
	
	public String getIsSearchClicked() {
		return isSearchClicked;
	}
	public void setIsSearchClicked(String isSearchClicked) {
		this.isSearchClicked = isSearchClicked;
	}
	public String getRecMLIName() {
		return recMLIName;
	}
	public void setRecMLIName(String recMLIName) {
		this.recMLIName = recMLIName;
	}
	public String getRecCGPAN() {
		return recCGPAN;
	}
	public void setRecCGPAN(String recCGPAN) {
		this.recCGPAN = recCGPAN;
	}
	public String getRecPromoterName() {
		return recPromoterName;
	}
	public void setRecPromoterName(String recPromoterName) {
		this.recPromoterName = recPromoterName;
	}
	public String getRecClaimStatus() {
		return recClaimStatus;
	}
	public void setRecClaimStatus(String recClaimStatus) {
		this.recClaimStatus = recClaimStatus;
	}
	public String getRecMLIId() {
		return recMLIId;
	}
	public void setRecMLIId(String recMLIId) {
		this.recMLIId = recMLIId;
	}
	public String getRecClaimRefNo() {
		return recClaimRefNo;
	}
	public void setRecClaimRefNo(String recClaimRefNo) {
		this.recClaimRefNo = recClaimRefNo;
	}
	public String getRecFromDate() {
		return recFromDate;
	}
	public void setRecFromDate(String recFromDate) {
		this.recFromDate = recFromDate;
	}
	public String getRecToDate() {
		return recToDate;
	}
	public void setRecToDate(String recToDate) {
		this.recToDate = recToDate;
	}
	public String getRecPromoterITPAN() {
		return recPromoterITPAN;
	}
	public void setRecPromoterITPAN(String recPromoterITPAN) {
		this.recPromoterITPAN = recPromoterITPAN;
	}
	public String getRecLoginTypel() {
		return recLoginTypel;
	}
	public void setRecLoginTypel(String recLoginTypel) {
		this.recLoginTypel = recLoginTypel;
	}
	public ArrayList getMliRecList() {
		return mliRecList;
	}
	public void setMliRecList(ArrayList mliRecList) {
		this.mliRecList = mliRecList;
	}
	public ArrayList getPromoterValues() {
		return promoterValues;
	}
	public void setPromoterValues(ArrayList promoterValues) {
		this.promoterValues = promoterValues;
	}
	public String getMessage() {
		return message;
	}
	public void setMessage(String message) {
		this.message = message;
	}
	public int getRecListCount() {
		return recListCount;
	}
	public void setRecListCount(int recListCount) {
		this.recListCount = recListCount;
	}
	
	//Claim 
	
	private String claimMLIName;
	private String claimCGPAN;
	private String claimPromoterName;
	private String claimClaimStatus;
	private String claimMLIId;
	private String claimClaimRefNo;
	private String claimFromDate;
	private String claimToDate;
	private String claimPromoterITPAN;
	private String claimisSearchClicked;
	private String claimLoginType;
	private ArrayList claimReportList;
	private int claimListCount;

	public String getClaimMLIName() {
		return claimMLIName;
	}
	public String getClaimCGPAN() {
		return claimCGPAN;
	}
	public String getClaimPromoterName() {
		return claimPromoterName;
	}
	public String getClaimClaimStatus() {
		return claimClaimStatus;
	}
	public String getClaimMLIId() {
		return claimMLIId;
	}
	public String getClaimClaimRefNo() {
		return claimClaimRefNo;
	}
	public String getClaimFromDate() {
		return claimFromDate;
	}
	public String getClaimToDate() {
		return claimToDate;
	}
	public String getClaimPromoterITPAN() {
		return claimPromoterITPAN;
	}
	public String getClaimisSearchClicked() {
		return claimisSearchClicked;
	}
	public String getClaimLoginType() {
		return claimLoginType;
	}
	public ArrayList getClaimReportList() {
		return claimReportList;
	}
	public int getClaimListCount() {
		return claimListCount;
	}
	public void setClaimMLIName(String claimMLIName) {
		this.claimMLIName = claimMLIName;
	}
	public void setClaimCGPAN(String claimCGPAN) {
		this.claimCGPAN = claimCGPAN;
	}
	public void setClaimPromoterName(String claimPromoterName) {
		this.claimPromoterName = claimPromoterName;
	}
	public void setClaimClaimStatus(String claimClaimStatus) {
		this.claimClaimStatus = claimClaimStatus;
	}
	public void setClaimMLIId(String claimMLIId) {
		this.claimMLIId = claimMLIId;
	}
	public void setClaimClaimRefNo(String claimClaimRefNo) {
		this.claimClaimRefNo = claimClaimRefNo;
	}
	public void setClaimFromDate(String claimFromDate) {
		this.claimFromDate = claimFromDate;
	}
	public void setClaimToDate(String claimToDate) {
		this.claimToDate = claimToDate;
	}
	public void setClaimPromoterITPAN(String claimPromoterITPAN) {
		this.claimPromoterITPAN = claimPromoterITPAN;
	}
	public void setClaimisSearchClicked(String claimisSearchClicked) {
		this.claimisSearchClicked = claimisSearchClicked;
	}
	public void setClaimLoginType(String claimLoginType) {
		this.claimLoginType = claimLoginType;
	}
	public void setClaimReportList(ArrayList claimReportList) {
		this.claimReportList = claimReportList;
	}
	public void setClaimListCount(int claimListCount) {
		this.claimListCount = claimListCount;
	}
	
	
}
