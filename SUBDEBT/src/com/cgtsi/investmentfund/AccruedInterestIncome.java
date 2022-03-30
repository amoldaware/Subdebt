/*
 * Created on Nov 23, 2004
 *
 * To change the template for this generated file go to
 * Window&gt;Preferences&gt;Java&gt;Code Generation&gt;Code and Comments
 */
package com.cgtsi.investmentfund;

import java.util.Date;

/**
 * @author rt14509
 *
 * To change the template for this generated type comment go to 
 * Window&gt;Preferences&gt;Java&gt;Code Generation&gt;Code and Comments
 */
public class AccruedInterestIncome {

	/**
	 * 
	 */
	public AccruedInterestIncome() {
	}
	
	
	private int days;
	private double interest;
	private double annualInterest;
	private double roi;
	private String investee;
	private Date depositDate;
	private Date maturityDate;
	private Date fromDate;
	private Date toDate;
	private double depositAmount;
	private double interestEarned;
	private double amountFirst;
	private double amountSecond;

	/**
	 * @return
	 */
	public double getAmountFirst() {
		return amountFirst;
	}

	/**
	 * @return
	 */
	public double getAmountSecond() {
		return amountSecond;
	}

	/**
	 * @return
	 */
	public double getAnnualInterest() {
		return annualInterest;
	}

	/**
	 * @return
	 */
	public int getDays() {
		return days;
	}

	/**
	 * @return
	 */
	public double getDepositAmount() {
		return depositAmount;
	}

	/**
	 * @return
	 */
	public Date getDepositDate() {
		return depositDate;
	}

	/**
	 * @return
	 */
	public Date getFromDate() {
		return fromDate;
	}

	/**
	 * @return
	 */
	public double getInterest() {
		return interest;
	}

	/**
	 * @return
	 */
	public double getInterestEarned() {
		return interestEarned;
	}

	/**
	 * @return
	 */
	public String getInvestee() {
		return investee;
	}

	/**
	 * @return
	 */
	public Date getMaturityDate() {
		return maturityDate;
	}

	/**
	 * @return
	 */
	public double getRoi() {
		return roi;
	}

	/**
	 * @return
	 */
	public Date getToDate() {
		return toDate;
	}

	/**
	 * @param d
	 */
	public void setAmountFirst(double d) {
		amountFirst = d;
	}

	/**
	 * @param d
	 */
	public void setAmountSecond(double d) {
		amountSecond = d;
	}

	/**
	 * @param d
	 */
	public void setAnnualInterest(double d) {
		annualInterest = d;
	}

	/**
	 * @param i
	 */
	public void setDays(int i) {
		days = i;
	}

	/**
	 * @param d
	 */
	public void setDepositAmount(double d) {
		depositAmount = d;
	}

	/**
	 * @param date
	 */
	public void setDepositDate(Date date) {
		depositDate = date;
	}

	/**
	 * @param date
	 */
	public void setFromDate(Date date) {
		fromDate = date;
	}

	/**
	 * @param d
	 */
	public void setInterest(double d) {
		interest = d;
	}

	/**
	 * @param d
	 */
	public void setInterestEarned(double d) {
		interestEarned = d;
	}

	/**
	 * @param string
	 */
	public void setInvestee(String string) {
		investee = string;
	}

	/**
	 * @param date
	 */
	public void setMaturityDate(Date date) {
		maturityDate = date;
	}

	/**
	 * @param d
	 */
	public void setRoi(double d) {
		roi = d;
	}

	/**
	 * @param date
	 */
	public void setToDate(Date date) {
		toDate = date;
	}

}

