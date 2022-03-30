/*
 * Created on Feb 6, 2004
 *
 * To change the template for this generated file go to
 * Window&gt;Preferences&gt;Java&gt;Code Generation&gt;Code and Comments
 */
package com.cgtsi.reports;

/**
 * @author HG7250
 *
 * To change the template for this generated type comment go to
 * Window&gt;Preferences&gt;Java&gt;Code Generation&gt;Code and Comments
 */
public class QueryReport {

	private String cgpan = "";
	private String applnRefNo = "";
	private String applSubmittedDate = "";
	private double termCreditSanctioned = 0;
	private double workingCapitalSanctioned = 0;
	private String bankApplnRefNumber = "";
	private String chiefPromoterName = "";
	private String ITPanNo = "";
	private String ssiName = "";
	private String ssiAddress = "";
	private String city = "";
	private String pin = "";
	private String district = "";
	private String state = "";
	private String unitType = "";
	private float termLoanIntRate = 0;
	private int termLoanTenure = 0;
	private double projectOutlay = 0;
	private String approvedDate = "";
	private double approvedAmount = 0;
	private double guaranteeFee = 0;
	private float workingCapitalPLR = 0;
	private float termLoanPLR = 0;
	private String guaranteeFeeDate = "";
 
	public QueryReport() {
		super();
	}

	/**
	 * @return
	 */
	public double getApprovedAmount() {
		return approvedAmount;
	}

	/**
	 * @return
	 */
	public String getBankApplnRefNumber() {
		return bankApplnRefNumber;
	}

	/**
	 * @return
	 */
	public String getChiefPromoterName() {
		return chiefPromoterName;
	}

	/**
	 * @return
	 */
	public String getCity() {
		return city;
	}

	/**
	 * @return
	 */
	public String getDistrict() {
		return district;
	}

	/**
	 * @return
	 */
	public double getGuaranteeFee() {
		return guaranteeFee;
	}

	/**
	 * @return
	 */
	public String getITPanNo() {
		return ITPanNo;
	}

	/**
	 * @return
	 */
	public String getPin() {
		return pin;
	}

	/**
	 * @return
	 */
	public double getProjectOutlay() {
		return projectOutlay;
	}

	/**
	 * @return
	 */
	public String getSsiAddress() {
		return ssiAddress;
	}

	/**
	 * @return
	 */
	public String getSsiName() {
		return ssiName;
	}

	/**
	 * @return
	 */
	public String getState() {
		return state;
	}

	/**
	 * @return
	 */
	public double getTermCreditSanctioned() {
		return termCreditSanctioned;
	}

	/**
	 * @return
	 */
	public float getTermLoanIntRate() {
		return termLoanIntRate;
	}

	/**
	 * @return
	 */
	public int getTermLoanTenure() {
		return termLoanTenure;
	}

	/**
	 * @return
	 */
	public String getUnitType() {
		return unitType;
	}

	/**
	 * @return
	 */
	public double getWorkingCapitalSanctioned() {
		return workingCapitalSanctioned;
	}

	/**
	 * @param d
	 */
	public void setApprovedAmount(double d) {
		approvedAmount = d;
	}

	/**
	 * @param string
	 */
	public void setBankApplnRefNumber(String string) {
		bankApplnRefNumber = string;
	}

	/**
	 * @param string
	 */
	public void setChiefPromoterName(String string) {
		chiefPromoterName = string;
	}

	/**
	 * @param string
	 */
	public void setCity(String string) {
		city = string;
	}

	/**
	 * @param string
	 */
	public void setDistrict(String string) {
		district = string;
	}

	/**
	 * @param d
	 */
	public void setGuaranteeFee(double d) {
		guaranteeFee = d;
	}

	/**
	 * @param string
	 */
	public void setITPanNo(String string) {
		ITPanNo = string;
	}

	/**
	 * @param string
	 */
	public void setPin(String string) {
		pin = string;
	}

	/**
	 * @param d
	 */
	public void setProjectOutlay(double d) {
		projectOutlay = d;
	}

	/**
	 * @param string
	 */
	public void setSsiAddress(String string) {
		ssiAddress = string;
	}

	/**
	 * @param string
	 */
	public void setSsiName(String string) {
		ssiName = string;
	}

	/**
	 * @param string
	 */
	public void setState(String string) {
		state = string;
	}

	/**
	 * @param d
	 */
	public void setTermCreditSanctioned(double d) {
		termCreditSanctioned = d;
	}

	/**
	 * @param f
	 */
	public void setTermLoanIntRate(float f) {
		termLoanIntRate = f;
	}

	/**
	 * @param i
	 */
	public void setTermLoanTenure(int i) {
		termLoanTenure = i;
	}

	/**
	 * @param string
	 */
	public void setUnitType(String string) {
		unitType = string;
	}

	/**
	 * @param d
	 */
	public void setWorkingCapitalSanctioned(double d) {
		workingCapitalSanctioned = d;
	}

	/**
	 * @return
	 */
	public float getTermLoanPLR() {
		return termLoanPLR;
	}

	/**
	 * @return
	 */
	public float getWorkingCapitalPLR() {
		return workingCapitalPLR;
	}

	/**
	 * @param f
	 */
	public void setTermLoanPLR(float f) {
		termLoanPLR = f;
	}

	/**
	 * @param f
	 */
	public void setWorkingCapitalPLR(float f) {
		workingCapitalPLR = f;
	}

	/**
	 * @return
	 */
	public String getApplSubmittedDate() {
		return applSubmittedDate;
	}

	/**
	 * @return
	 */
	public String getApprovedDate() {
		return approvedDate;
	}

	/**
	 * @return
	 */
	public String getGuaranteeFeeDate() {
		return guaranteeFeeDate;
	}

	/**
	 * @param string
	 */
	public void setApplSubmittedDate(String string) {
		applSubmittedDate = string;
	}

	/**
	 * @param string
	 */
	public void setApprovedDate(String string) {
		approvedDate = string;
	}

	/**
	 * @param string
	 */
	public void setGuaranteeFeeDate(String string) {
		guaranteeFeeDate = string;
	}

	/**
	 * @return
	 */
	public String getApplnRefNo() {
		return applnRefNo;
	}

	/**
	 * @return
	 */
	public String getCgpan() {
		return cgpan;
	}

	/**
	 * @param string
	 */
	public void setApplnRefNo(String string) {
		applnRefNo = string;
	}

	/**
	 * @param string
	 */
	public void setCgpan(String string) {
		cgpan = string;
	}

}
