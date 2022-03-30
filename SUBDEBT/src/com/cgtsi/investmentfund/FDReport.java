/*
 * Created on Feb 7, 2004
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
public class FDReport {
	
	
	private String investee;
	private String investmentNumber;
	private String instrumentType;
	private String maturityType;
	private String rating;
	private String tenureType;
	private double principalAmount;
	private double maturityAmount;
	private Date depositDate;
	private Date maturityDate;
	private int frequency;
	private int interest;
	
	
	
	
	public FDReport() {
		
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
	public int getFrequency() {
		return frequency;
	}

	/**
	 * @return
	 */
	public String getInstrumentType() {
		return instrumentType;
	}

	/**
	 * @return
	 */
	public int getInterest() {
		return interest;
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
	public double getMaturityAmount() {
		return maturityAmount;
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
	public String getMaturityType() {
		return maturityType;
	}

	/**
	 * @return
	 */
	public double getPrincipalAmount() {
		return principalAmount;
	}

	/**
	 * @return
	 */
	public String getRating() {
		return rating;
	}

	/**
	 * @return
	 */
	public String getTenureType() {
		return tenureType;
	}

	/**
	 * @param date
	 */
	public void setDepositDate(Date date) {
		depositDate = date;
	}

	/**
	 * @param i
	 */
	public void setFrequency(int i) {
		frequency = i;
	}

	/**
	 * @param string
	 */
	public void setInstrumentType(String string) {
		instrumentType = string;
	}

	/**
	 * @param i
	 */
	public void setInterest(int i) {
		interest = i;
	}

	/**
	 * @param string
	 */
	public void setInvestee(String string) {
		investee = string;
	}

	/**
	 * @param d
	 */
	public void setMaturityAmount(double d) {
		maturityAmount = d;
	}

	/**
	 * @param date
	 */
	public void setMaturityDate(Date date) {
		maturityDate = date;
	}

	/**
	 * @param string
	 */
	public void setMaturityType(String string) {
		maturityType = string;
	}

	/**
	 * @param d
	 */
	public void setPrincipalAmount(double d) {
		principalAmount = d;
	}

	/**
	 * @param string
	 */
	public void setRating(String string) {
		rating = string;
	}

	/**
	 * @param string
	 */
	public void setTenureType(String string) {
		tenureType = string;
	}

	/**
	 * @return
	 */
	public String getInvestmentNumber() {
		return investmentNumber;
	}

	/**
	 * @param string
	 */
	public void setInvestmentNumber(String string) {
		investmentNumber = string;
	}

}
