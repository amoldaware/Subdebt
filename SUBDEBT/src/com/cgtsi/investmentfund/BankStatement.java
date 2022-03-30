/*
 * Created on Feb 4, 2004
 *
 * To change the template for this generated file go to
 * Window&gt;Preferences&gt;Java&gt;Code Generation&gt;Code and Comments
 */
package com.cgtsi.investmentfund;

import java.util.Date;

/**
 * @author RT14509
 *
 * To change the template for this generated type comment go to
 * Window&gt;Preferences&gt;Java&gt;Code Generation&gt;Code and Comments
 */
public class BankStatement {
	
	
	private String bstId;
	private String bamId;
	private String accountNumber;
	private String transactionFrom;
	private String transactionNature;
	private String remarks;
	private String bank;
	private Date transactionDate;
	private double openingBalance;
	private double closingBalance;
	private double creditPending;
	private double bstAmount;
	

	
	public BankStatement() {
		
	}
	
	
	/**
	 * @return
	 */
	public String getBstId() {
		return bstId;
	}

	/**
	 * @param string
	 */
	public void setBstId(String aBstId) {
		bstId = aBstId;
	}
	
	
	/**
	 * @return
	 */
	public String getBamId() {
		return bamId;
	}

	/**
	 * @param string
	 */
	public void setBamId(String aBamId) {
		bamId = aBamId;
	}
	
	
	/**
	 * @return
	 */
	public String getAccountNumber() {
		return accountNumber;
	}

	/**
	 * @param string
	 */
	public void setAccountNumber(String aAccountNumber) {
		accountNumber = aAccountNumber;
	}
	
	
	
	/**
	 * @return
	 */
	public String getTransactionFrom() {
		return transactionFrom;
	}

	/**
	 * @param string
	 */
	public void setTransactionFrom(String aTransactionFrom) {
		transactionFrom = aTransactionFrom;
	}
	
	/**
	 * @return
	 */
	public String getTransactionNature() {
		return transactionNature;
	}

	/**
	 * @param string
	 */
	public void setTransactionNature(String aTransactionNature) {
		transactionNature = aTransactionNature;
	}
	
	
	/**
	 * @return
	 */
	public String getRemarks() {
		return remarks;
	}

	/**
	 * @param string
	 */
	public void setRemarks(String aRemarks) {
		remarks = aRemarks;
	}
	
	
	/**
	 * @return
	 */
	public String getBank() {
		return bank;
	}

	/**
	 * @param string
	 */
	public void setBank(String aBank) {
		bank = aBank;
	}
 	
	
	
	/**
	 * @return
	 */
	public double getOpeningBalance() {
		return openingBalance;
	}

	/**
	 * @param d
	 */
	public void setOpeningBalance(double dOpeningBalance) {
		openingBalance = dOpeningBalance;
	}
	
	
	/**
	 * @return
	 */
	public double getClosingBalance() {
		return closingBalance;
	}

	/**
	 * @param d
	 */
	public void setClosingBalance(double dClosingBalance) {
		closingBalance = dClosingBalance;
	}
	
	
	/**
	 * @return
	 */
	public double getCreditPending() {
		return creditPending;
	}

	/**
	 * @param d
	 */
	public void setCreditPending(double dCreditPending) {
		creditPending = dCreditPending;
	}
	
	
	/**
	 * @return
	 */
	public double getBstAmount() {
		return bstAmount;
	}

	/**
	 * @param d
	 */
	public void setBstAmount(double dBstAmount) {
		bstAmount = dBstAmount;
	}
	
	
	/**
	 * @return
	 */
	public Date getTransactionDate() {
		return transactionDate;
	}

	/**
	 * @param date
	 */
	public void setTransactionDate(Date date) {
		transactionDate = date;
	}
	
	

	

}
