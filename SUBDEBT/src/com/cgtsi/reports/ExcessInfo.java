/*
 * Created on Aug 16, 2004
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
public class ExcessInfo {
	
	
	private String memberId;
	private String voucher;
	private String paymentId;
	private double amount;
	private Date excessDate;
	
	private String danId;
	private String cgpan;
	private Date danDate;
	private Date allocationDate;
	private Date appropriationDate;
	private double gFee;
	
	

	public ExcessInfo() {
	}

	/**
	 * @return
	 */
	public double getAmount() { 
		return amount;
	}

	/**
	 * @return
	 */
	public Date getExcessDate() {
		return excessDate;
	}

	/**
	 * @return
	 */
	public String getMemberId() {
		return memberId;
	}

	/**
	 * @return
	 */
	public String getPaymentId() {
		return paymentId;
	}

	/**
	 * @return
	 */
	public String getVoucher() {
		return voucher;
	}

	/**
	 * @param d
	 */
	public void setAmount(double d) {
		amount = d;
	}

	/**
	 * @param date
	 */
	public void setExcessDate(Date date) {
		excessDate = date;
	}

	/**
	 * @param string
	 */
	public void setMemberId(String string) {
		memberId = string;
	}

	/**
	 * @param string
	 */
	public void setPaymentId(String string) {
		paymentId = string;
	}

	/**
	 * @param string
	 */
	public void setVoucher(String string) {
		voucher = string;
	}

	/**
	 * @return
	 */
	public Date getAppropriationDate() { 
		return appropriationDate;
	}

	/**
	 * @return
	 */
	public String getCgpan() {
		return cgpan;
	}

	/**
	 * @return
	 */
	public Date getDanDate() {
		return danDate;
	}

	/**
	 * @return
	 */
	public String getDanId() {
		return danId;
	}

	/**
	 * @return
	 */
	public double getGFee() {
		return gFee;
	}

	/**
	 * @param date
	 */
	public void setAppropriationDate(Date date) {
		appropriationDate = date;
	}

	/**
	 * @param string
	 */
	public void setCgpan(String string) {
		cgpan = string;
	}

	/**
	 * @param date
	 */
	public void setDanDate(Date date) {
		danDate = date;
	}

	/**
	 * @param string
	 */
	public void setDanId(String string) {
		danId = string;
	}

	/**
	 * @param d
	 */
	public void setGFee(double d) {
		gFee = d;
	}

	/**
	 * @return
	 */
	public Date getAllocationDate() {
		return allocationDate;
	}

	/**
	 * @param date
	 */
	public void setAllocationDate(Date date) {
		allocationDate = date;
	}

}
