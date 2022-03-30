package com.cgtsi.actionform;

import java.util.ArrayList;

import org.apache.struts.validator.ValidatorActionForm;

public class ApplicationStatusActionForm extends ValidatorActionForm {
	
	private String APP_REF_NO;
	private String appRefNo;
	private String CGPAN;
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
	public String getAPP_REF_NO() {
		return APP_REF_NO;
	}
	public void setAPP_REF_NO(String aPP_REF_NO) {
		APP_REF_NO = aPP_REF_NO;
	}
	public String getCGPAN() {
		return CGPAN;
	}
	public void setCGPAN(String cGPAN) {
		CGPAN = cGPAN;
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
	public ArrayList<ApplicationStatusActionForm> getDisplayData() {
		return displayData;
	}
	public void setDisplayData(ArrayList<ApplicationStatusActionForm> displayData) {
		this.displayData = displayData;
	}
	public String getAppRefNo() {
		return appRefNo;
	}
	public void setAppRefNo(String appRefNo) {
		this.appRefNo = appRefNo;
	}
	private String APPROPRIATED_BY;
	private ArrayList <ApplicationStatusActionForm> displayData;
}
