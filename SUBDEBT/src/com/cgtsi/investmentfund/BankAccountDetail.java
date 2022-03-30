/*
 * Created on Jan 18, 2004
 *
 * To change the template for this generated file go to
 * Window&gt;Preferences&gt;Java&gt;Code Generation&gt;Code and Comments
 */
package com.cgtsi.investmentfund;

import java.io.Serializable;

/**
 * @author VT8150
 *
 * To change the template for this generated type comment go to
 * Window&gt;Preferences&gt;Java&gt;Code Generation&gt;Code and Comments
 */
public class BankAccountDetail implements Serializable 
{
	private String bankName;
	private String bankBranchName;	
	private String accountNumber;
	private String accountType;
	
	private String modBankName;
	private String modBankBranchName;
	private String modAccountNumber;
	
	private double minBalance;
	
	/**
	 * @return
	 */
	public String getAccountNumber() {
		return accountNumber;
	}

	/**
	 * @return
	 */
	public String getAccountType() {
		return accountType;
	}

	/**
	 * @return
	 */
	public String getBankBranchName() {
		return bankBranchName;
	}

	/**
	 * @return
	 */
	public String getBankName() {
		return bankName;
	}

	/**
	 * @param string
	 */
	public void setAccountNumber(String string) {
		accountNumber = string;
	}

	/**
	 * @param string
	 */
	public void setAccountType(String string) {
		accountType = string;
	}

	/**
	 * @param string
	 */
	public void setBankBranchName(String string) {
		bankBranchName = string;
	}

	/**
	 * @param string
	 */
	public void setBankName(String string) {
		bankName = string;
	}

	/**
	 * @return
	 */
	public String getModAccountNumber() {
		return modAccountNumber;
	}

	/**
	 * @return
	 */
	public String getModBankBranchName() {
		return modBankBranchName;
	}

	/**
	 * @return
	 */
	public String getModBankName() {
		return modBankName;
	}

	/**
	 * @param string
	 */
	public void setModAccountNumber(String string) {
		modAccountNumber = string;
	}

	/**
	 * @param string
	 */
	public void setModBankBranchName(String string) {
		modBankBranchName = string;
	}

	/**
	 * @param string
	 */
	public void setModBankName(String string) {
		modBankName = string;
	}

	/**
	 * @return
	 */
	public double getMinBalance() {
		return minBalance;
	}

	/**
	 * @param d
	 */
	public void setMinBalance(double d) {
		minBalance = d;
	}

}
