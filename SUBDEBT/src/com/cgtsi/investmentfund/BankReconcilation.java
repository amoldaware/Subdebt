/*
 * Created on Jun 11, 2004
 *
 * To change the template for this generated file go to
 * Window&gt;Preferences&gt;Java&gt;Code Generation&gt;Code and Comments
 */
package com.cgtsi.investmentfund;

import java.util.ArrayList;
import java.util.Date;



/**
 * @author RT14509
 *
 * To change the template for this generated type comment go to
 * Window&gt;Preferences&gt;Java&gt;Code Generation&gt;Code and Comments
 */
public class BankReconcilation {

	/**
	 * 
	 */
	
	private Date reconDate;
	private double cgtsiBalance;
	private double chequeIssuedAmount;
	private double directCredit;
	private double directDebit;
	private double closingBalanceIDBI;
	private int reconId;
	
	private ArrayList chequeDetails;

	/**
	 * @return
	 */
	public double getCgtsiBalance() {
		return cgtsiBalance;
	}

	/**
	 * @return
	 */
	public ArrayList getChequeDetails() {
		return chequeDetails;
	}


	/**
	 * @return
	 */
	public double getDirectCredit() {
		return directCredit;
	}

	/**
	 * @return
	 */
	public double getDirectDebit() {
		return directDebit;
	}

	/**
	 * @return
	 */
	public Date getReconDate() {
		return reconDate;
	}

	/**
	 * @param d
	 */
	public void setCgtsiBalance(double d) {
		cgtsiBalance = d;
	}

	/**
	 * @param list
	 */
	public void setChequeDetails(ArrayList list) {
		chequeDetails = list;
	}


	/**
	 * @param d
	 */
	public void setDirectCredit(double d) {
		directCredit = d;
	}

	/**
	 * @param d
	 */
	public void setDirectDebit(double d) {
		directDebit = d;
	}

	/**
	 * @param date
	 */
	public void setReconDate(Date date) {
		reconDate = date;
	}

	/**
	 * @return
	 */
	public int getReconId() {
		return reconId;
	}

	/**
	 * @param i
	 */
	public void setReconId(int i) {
		reconId = i;
	}

	/**
	 * @return
	 */
	public double getClosingBalanceIDBI() {
		return closingBalanceIDBI;
	}

	/**
	 * @param d
	 */
	public void setClosingBalanceIDBI(double d) {
		closingBalanceIDBI = d;
	}

	/**
	 * @return
	 */
	public double getChequeIssuedAmount() {
		return chequeIssuedAmount;
	}

	/**
	 * @param d
	 */
	public void setChequeIssuedAmount(double d) {
		chequeIssuedAmount = d;
	}

}
