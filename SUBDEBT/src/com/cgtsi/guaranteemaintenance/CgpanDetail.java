/*
 * Created on Jan 21, 2004
 *
 * To change the template for this generated file go to
 * Window&gt;Preferences&gt;Java&gt;Code Generation&gt;Code and Comments
 */
package com.cgtsi.guaranteemaintenance;

import java.util.Date;

/**
 * @author GU14477
 *
 * To change the template for this generated type comment go to
 * Window&gt;Preferences&gt;Java&gt;Code Generation&gt;Code and Comments
 */
public class CgpanDetail {
	
	private String borrowerId;
	private String borrowerName;
	private String chiefPromoterName;
	private String city;
	private double wcAmountSanctioned;
	private double tcAmountSanctioned;
	private double amountApproved;
	private Date  guaranteeIssueDate;
	
	
	
	public CgpanDetail(){
		
	}
	

	/**
	 * @return
	 */
	public double getAmountApproved() {
		return amountApproved;
	}

	/**
	 * @return
	 */
	public String getBorrowerId() {
		return borrowerId;
	}

	/**
	 * @return
	 */
	public String getBorrowerName() {
		return borrowerName;
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
	public Date getGuaranteeIssueDate() {
		return guaranteeIssueDate;
	}

	/**
	 * @return
	 */
	public double getTcAmountSanctioned() {
		return tcAmountSanctioned;
	}

	/**
	 * @return
	 */
	public double getWcAmountSanctioned() {
		return wcAmountSanctioned;
	}

	/**
	 * @param d
	 */
	public void setAmountApproved(double d) {
		amountApproved = d;
	}

	/**
	 * @param string
	 */
	public void setBorrowerId(String string) {
		borrowerId = string;
	}

	/**
	 * @param string
	 */
	public void setBorrowerName(String string) {
		borrowerName = string;
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
	 * @param date
	 */
	public void setGuaranteeIssueDate(Date date) {
		guaranteeIssueDate = date;
	}

	/**
	 * @param d
	 */
	public void setTcAmountSanctioned(double d) {
		tcAmountSanctioned = d;
	}

	/**
	 * @param d
	 */
	public void setWcAmountSanctioned(double d) {
		wcAmountSanctioned = d;
	}

}
