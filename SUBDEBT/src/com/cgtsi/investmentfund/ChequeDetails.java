/*
 * Created on Jun 11, 2004
 *
 * To change the template for this generated file go to
 * Window&gt;Preferences&gt;Java&gt;Code Generation&gt;Code and Comments
 */
package com.cgtsi.investmentfund;



/**
 * @author RT14509
 *
 * To change the template for this generated type comment go to
 * Window&gt;Preferences&gt;Java&gt;Code Generation&gt;Code and Comments
 */
public class ChequeDetails {

	/**
	 * 
	 */
	
	private String chequeNumber;
	private double chequeAmount;
	private java.util.Date chequeDate;
	private int expiryDate;
	private String chequeIssuedTo;
	private String bankName;
	private String branchName;
	private String toBranchName;
	private String toBankName;
	private String userId;
	private String chequeId;
	private java.sql.Date addedDate;
	private String status;
	private String chequeRemarks; 
	private String payId;
	private java.util.Date presentedDate;
	
	private String chqId;
	
	private int chqNumber;


	/**
	 * @return
	 */
	
	public String getPayId()
	{
		return payId;
	}

	public void setPayId(String string)
	{
		payId = string;
	}
	/**
	 * @return
	 */
	
	public String getBankName() {
		return bankName;
	}

	/**
	 * @return
	 */
	public double getChequeAmount() {
		return chequeAmount;
	}

	/**
	 * @return
	 */
	public java.util.Date getChequeDate() {
		return chequeDate;
	}

	/**
	 * @return
	 */
	public String getChequeIssuedTo() {
		return chequeIssuedTo;
	}

	/**
	 * @return
	 */
	public String getChequeNumber() {
		return chequeNumber;
	}

	/**
	 * @param string
	 */
	public void setBankName(String string) {
		bankName = string;
	}

	/**
	 * @param d
	 */
	public void setChequeAmount(double d) {
		chequeAmount = d;
	}

	/**
	 * @param date
	 */
	public void setChequeDate(java.util.Date date) {
		chequeDate = date;
	}

	/**
	 * @param string
	 */
	public void setChequeIssuedTo(String string) {
		chequeIssuedTo = string;
	}

	/**
	 * @param string
	 */
	public void setChequeNumber(String string) {
		chequeNumber = string;
	}



	/**
	 * @return
	 */
	public String getChequeId() {
		return chequeId;
	}

	/**
	 * @return
	 */
	public String getUserId() {
		return userId;
	}

	/**
	 * @param string
	 */
	public void setChequeId(String string) {
		chequeId = string;
	}

	/**
	 * @param string
	 */
	public void setUserId(String string) {
		userId = string;
	}

	/**
	 * @return
	 */
	public String getStatus() {
		return status;
	}

	/**
	 * @param string
	 */
	public void setStatus(String string) {
		status = string;
	}

	/**
	 * @return
	 */
	public java.sql.Date getAddedDate() { 
		return addedDate;
	}

	/**
	 * @param date
	 */
	public void setAddedDate(java.sql.Date date) {
		addedDate = date;
	}



	/**
	 * @return
	 */
	public String getChequeRemarks() {
		return chequeRemarks;
	}

	/**
	 * @param string
	 */
	public void setChequeRemarks(String string) {
		chequeRemarks = string;
	}

	/**
	 * @return
	 */
	public String getToBankName() {
		return toBankName;
	}

	/**
	 * @param string
	 */
	public void setToBankName(String string) {
		toBankName = string;
	}




	/**
	 * @return
	 */
	public int getExpiryDate() {
		return expiryDate;
	}

	/**
	 * @param i
	 */
	public void setExpiryDate(int i) {
		expiryDate = i;
	}

	/**
	 * @return
	 */
	public String getBranchName() {
		return branchName;
	}

	/**
	 * @param string
	 */
	public void setBranchName(String string) {
		branchName = string;
	}

	/**
	 * @return
	 */
	public String getToBranchName() {
		return toBranchName;
	}

	/**
	 * @param string
	 */
	public void setToBranchName(String string) {
		toBranchName = string;
	}

	/**
	 * @return
	 */
	public java.util.Date getPresentedDate() {
		return presentedDate;
	}

	/**
	 * @param date
	 */
	public void setPresentedDate(java.util.Date date) {
		presentedDate = date;
	}

	/**
	 * @return
	 */
	public int getChqNumber() {
		return chqNumber;
	}

	/**
	 * @param i
	 */
	public void setChqNumber(int i) {
		chqNumber = i;
	}

	/**
	 * @return
	 */
	public String getChqId() {
		return chqId;
	}

	/**
	 * @param string
	 */
	public void setChqId(String string) {
		chqId = string;
	}

}
