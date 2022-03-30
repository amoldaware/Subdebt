/*
 * Created on May 14, 2004
 *
 * To change the template for this generated file go to
 * Window&gt;Preferences&gt;Java&gt;Code Generation&gt;Code and Comments
 */
package com.cgtsi.reports;

import java.util.Date;

/**
 * @author RT14509
 *
 * To change the template for this generated type comment go to
 * Window&gt;Preferences&gt;Java&gt;Code Generation&gt;Code and Comments
 */

public class Securitization {

	private String slpname;
	private String spvName;
	private Date securitizationDate;
	private Date tenureDate;
	private double amount;
	private double interest;
	private String rating;
	private String ratingAgency;
	private String memberid;
	private String sector;
	private String state;
	private String interestType;
	private String loanType;
	private String cgpan;
	private double maxInterest;
	private double maxLoan;
	private double minInterest;
	private double minLoan;
	private int tenure;
	private int trackRecord;
	
	
	public Securitization() { 
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
	public void setCgpan(String string) {
		cgpan = string;
	}

	/**
	 * @return
	 */
	public String getSlpname() {
		return slpname;
	}

	/**
	 * @param string
	 */
	public void setSlpname(String string) {
		slpname = string;
	}

	/**
	 * @return
	 */
	public String getSpvName() {
		return spvName;
	}

	/**
	 * @param string
	 */
	public void setSpvName(String string) {
		spvName = string;
	}

	/**
	 * @return
	 */
	public String getSector() {
		return sector;
	}

	/**
	 * @param string
	 */
	public void setSector(String string) {
		sector = string;
	}

	/**
	 * @return
	 */
	public String getState() {
		return state;
	}

	/**
	 * @param string
	 */
	public void setState(String string) {
		state = string;
	}

	/**
	 * @return
	 */
	public String getRating() {
		return rating;
	}

	/**
	 * @param string
	 */
	public void setRating(String string) {
		rating = string;
	}

	/**
	 * @return
	 */
	public String getRatingAgency() {
		return ratingAgency;
	}

	/**
	 * @param string
	 */
	public void setRatingAgency(String string) {
		ratingAgency = string;
	}

	/**
	 * @return
	 */
	public double getAmount() {
		return amount;
	}

	/**
	 * @param d
	 */
	public void setAmount(double d) {
		amount = d;
	}

	/**
	 * @return
	 */
	public double getInterest() {
		return interest;
	}

	/**
	 * @param d
	 */
	public void setInterest(double d) {
		interest = d;
	}

	/**
	 * @return
	 */
	public String getMemberid() {
		return memberid;
	}

	/**
	 * @param string
	 */
	public void setMemberid(String string) {
		memberid = string;
	}

	/**
	 * @return
	 */
	public Date getSecuritizationDate() {
		return securitizationDate;
	}

	/**
	 * @param date
	 */
	public void setSecuritizationDate(Date date) {
		securitizationDate = date;
	}

	/**
	 * @return
	 */
	public String getInterestType() {
		return interestType;
	}

	/**
	 * @param string
	 */
	public void setInterestType(String string) {
		interestType = string;
	}

	/**
	 * @return
	 */
	public String getLoanType() {
		return loanType;
	}

	/**
	 * @param string
	 */
	public void setLoanType(String string) {
		loanType = string;
	}

	/**
	 * @return
	 */
	public double getMaxInterest() {
		return maxInterest;
	}

	/**
	 * @param d
	 */
	public void setMaxInterest(double d) {
		maxInterest = d;
	}

	/**
	 * @return
	 */
	public double getMaxLoan() {
		return maxLoan;
	}

	/**
	 * @param d
	 */
	public void setMaxLoan(double d) {
		maxLoan = d;
	}

	/**
	 * @return
	 */
	public double getMinInterest() {
		return minInterest;
	}

	/**
	 * @param d
	 */
	public void setMinInterest(double d) {
		minInterest = d;
	}

	/**
	 * @return
	 */
	public double getMinLoan() {
		return minLoan;
	}

	/**
	 * @param d
	 */
	public void setMinLoan(double d) {
		minLoan = d;
	}

	/**
	 * @return
	 */
	public int getTenure() {
		return tenure;
	}

	/**
	 * @param i
	 */
	public void setTenure(int i) {
		tenure = i;
	}

	/**
	 * @return
	 */
	public Date getTenureDate() {
		return tenureDate;
	}

	/**
	 * @param date
	 */
	public void setTenureDate(Date date) {
		tenureDate = date;
	}

	/**
	 * @return
	 */
	public int getTrackRecord() {
		return trackRecord;
	}

	/**
	 * @param i
	 */
	public void setTrackRecord(int i) {
		trackRecord = i;
	}

}