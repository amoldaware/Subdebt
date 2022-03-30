package com.cgtsi.actionform;

import java.util.ArrayList;

import org.apache.struts.validator.ValidatorActionForm;

public class MLICheckerReportForm extends ValidatorActionForm
{
	private static final long serialVersionUID = 1L;
	private String checkerMLIName;
	private String checkerMLIId;
	private String approvalStatus;
	private String mliCheckerisSearchClicked;
	private int mliCheckerlistCount;
	private ArrayList MliDetails;
	private String message;
	
	
	public String getMessage() {
		return message;
	}
	public void setMessage(String message) {
		this.message = message;
	}
	public String getCheckerMLIName() {
		return checkerMLIName;
	}
	public void setCheckerMLIName(String checkerMLIName) {
		this.checkerMLIName = checkerMLIName;
	}
	public String getCheckerMLIId() {
		return checkerMLIId;
	}
	public void setCheckerMLIId(String checkerMLIId) {
		this.checkerMLIId = checkerMLIId;
	}
	public String getApprovalStatus() {
		return approvalStatus;
	}
	public void setApprovalStatus(String approvalStatus) {
		this.approvalStatus = approvalStatus;
	}
	
	public String getMliCheckerisSearchClicked() {
		return mliCheckerisSearchClicked;
	}
	public int getMliCheckerlistCount() {
		return mliCheckerlistCount;
	}
	public void setMliCheckerisSearchClicked(String mliCheckerisSearchClicked) {
		this.mliCheckerisSearchClicked = mliCheckerisSearchClicked;
	}
	public void setMliCheckerlistCount(int mliCheckerlistCount) {
		this.mliCheckerlistCount = mliCheckerlistCount;
	}
	public ArrayList getMliDetails() {
		return MliDetails;
	}
	public void setMliDetails(ArrayList mliDetails) {
		MliDetails = mliDetails;
	}
	
	// Bank Mandate Details
	private String bankMandateMLIName;
	private String bankMandateMLIId;
	private ArrayList bankMandateDtls;
	private String bankMandateisSearchClicked;
	private int bankMandatelistCount;
	private String bankMandateLoginType;
	
	

	public String getBankMandateLoginType() {
		return bankMandateLoginType;
	}
	public void setBankMandateLoginType(String bankMandateLoginType) {
		this.bankMandateLoginType = bankMandateLoginType;
	}
	public String getBankMandateMLIName() {
		return bankMandateMLIName;
	}
	public void setBankMandateMLIName(String bankMandateMLIName) {
		this.bankMandateMLIName = bankMandateMLIName;
	}
	public String getBankMandateMLIId() {
		return bankMandateMLIId;
	}
	public void setBankMandateMLIId(String bankMandateMLIId) {
		this.bankMandateMLIId = bankMandateMLIId;
	}
	public ArrayList getBankMandateDtls() {
		return bankMandateDtls;
	}
	public void setBankMandateDtls(ArrayList bankMandateDtls) {
		this.bankMandateDtls = bankMandateDtls;
	}
	public String getBankMandateisSearchClicked() {
		return bankMandateisSearchClicked;
	}
	public int getBankMandatelistCount() {
		return bankMandatelistCount;
	}
	public void setBankMandateisSearchClicked(String bankMandateisSearchClicked) {
		this.bankMandateisSearchClicked = bankMandateisSearchClicked;
	}
	public void setBankMandatelistCount(int bankMandatelistCount) {
		this.bankMandatelistCount = bankMandatelistCount;
	}
	
}
