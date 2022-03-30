/*
 * Created on Nov 26, 2004
 *
 * To change the template for this generated file go to
 * Window&gt;Preferences&gt;Java&gt;Code Generation&gt;Code and Comments
 */
package com.cgtsi.investmentfund;

import java.util.Date;



/**
 * @author ss14485
 *
 * To change the template for this generated type comment go to
 * Window&gt;Preferences&gt;Java&gt;Code Generation&gt;Code and Comments
 */
public class ChequeLeavesDetails {
	
	private String chqBankName;
	private String chqBranchName;
	private int chqId;
	private int chqBankId;
	private int chqStartNo;
	private int chqEndingNo;
	private int noOfChqLeaves;
	private Date chqIssueDate;
	
	private String bnkAccountNumber;
	
	private String bnkName;
	

	/**
	 * @return
	 */
	public int getChqBankId() {
		return chqBankId;
	}

	/**
	 * @return
	 */
	public int getChqEndingNo() {
		return chqEndingNo;
	}

	/**
	 * @return
	 */
	public int getChqId() {
		return chqId;
	}

	/**
	 * @return
	 */
	public Date getChqIssueDate() {
		return chqIssueDate;
	}

	/**
	 * @return
	 */
	public int getChqStartNo() {
		return chqStartNo;
	}

	/**
	 * @return
	 */
	public int getNoOfChqLeaves() {
		return noOfChqLeaves;
	}

	/**
	 * @param i
	 */
	public void setChqBankId(int i) {
		chqBankId = i;
	}

	/**
	 * @param i
	 */
	public void setChqEndingNo(int i) {
		chqEndingNo = i;
	}

	/**
	 * @param i
	 */
	public void setChqId(int i) {
		chqId = i;
	}

	/**
	 * @param date
	 */
	public void setChqIssueDate(Date date) {
		chqIssueDate = date;
	}

	/**
	 * @param i
	 */
	public void setChqStartNo(int i) {
		chqStartNo = i;
	}

	/**
	 * @param i
	 */
	public void setNoOfChqLeaves(int i) {
		noOfChqLeaves = i;
	}


	/**
	 * @return
	 */
	public String getChqBankName() {
		return chqBankName;
	}

	/**
	 * @return
	 */
	public String getChqBranchName() {
		return chqBranchName;
	}

	/**
	 * @param string
	 */
	public void setChqBankName(String string) {
		chqBankName = string;
	}

	/**
	 * @param string
	 */
	public void setChqBranchName(String string) {
		chqBranchName = string;
	}

	/**
	 * @return
	 */
	public String getBnkName() {
		return bnkName;
	}

	/**
	 * @param string
	 */
	public void setBnkName(String string) {
		bnkName = string;
	}

	/**
	 * @return
	 */
	public String getBnkAccountNumber() {
		return bnkAccountNumber;
	}

	/**
	 * @param string
	 */
	public void setBnkAccountNumber(String string) {
		bnkAccountNumber = string;
	}

}
