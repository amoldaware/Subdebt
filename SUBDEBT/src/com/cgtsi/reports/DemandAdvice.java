package com.cgtsi.reports;

import java.sql.Date;

public class DemandAdvice
{
	private String memberId;
	private String address;
	private String scheme;
 	private String dan;
	private Date danDate;
	private String mli;
	private String cgpan;
	private String bankReference;
	private String office;
	private String branch;
	private String ssiName;
	private double approvedAmount;
	private double guaranteeFee;
	
	
	
	public DemandAdvice()
	{
	}
	/**
	 * @return
	 */
	public String getMemberId() {
		return memberId;
	}

	/**
	 * @param string
	 */
	public void setMemberId(String aMemberId) {
		memberId = aMemberId;
	}

	/**
	 * @return
	 */
	public String getAddress() {
		return address;
	}

	/**
	 * @param string
	 */
	public void setAddress(String aAddress) {
		address = aAddress;
	}

	/**
	 * @return
	 */
	public String getScheme() {
		return scheme;
	}

	/**
	 * @param string
	 */
	public void setScheme(String aScheme) {
		scheme = aScheme;
	}

	/**
	 * @return
	 */
	public String getDan() {
		return dan;
	}

	/**
	 * @param string
	 */
	public void setDan(String aDan) {
		dan = aDan;
	}

	/**
	 * @return
	 */
	public Date getDanDate() {
		return danDate;
	}

	/**
	 * @param date
	 */
	public void setDanDate(Date aDanDate) {
		danDate = aDanDate;
	}

	/**
	 * @return
	 */
	public String getMli() {
		return mli;
	}

	/**
	 * @param string
	 */
	public void setMli(String aMli) {
		mli = aMli;
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
	public void setCgpan(String aCgpan) {
		cgpan = aCgpan;
	}

	/**
	 * @return
	 */
	public String getBankReference() {
		return bankReference;
	}

	/**
	 * @param string
	 */
	public void setBankReference(String aBankReference) {
		bankReference = aBankReference;
	}

	/**
	 * @return
	 */
	public String getOffice() {
		return office;
	}

	/**
	 * @param string
	 */
	public void setOffice(String aOffice) {
		office = aOffice;
	}

	/**
	 * @return
	 */
	public String getBranch() {
		return branch;
	}

	/**
	 * @param string
	 */
	public void setBranch(String aBranch) {
		branch = aBranch;
	}

	/**
	 * @return
	 */
	public String getSsiName() {
		return ssiName;
	}

	/**
	 * @param string
	 */
	public void setSsiName(String aSsiName) {
		ssiName = aSsiName;
	}

	/**
	 * @return
	 */
	public double getApprovedAmount() {
		return approvedAmount;
	}

	/**
	 * @param d
	 */
	public void setApprovedAmount(double dApprovedAmount) {
		approvedAmount = dApprovedAmount;
	}

	/**
	 * @return
	 */
	public double getGuaranteeFee() {
		return guaranteeFee;
	}

	/**
	 * @param d
	 */
	public void setGuaranteeFee(double dGuaranteeFee) {
		guaranteeFee = dGuaranteeFee;
	}

}