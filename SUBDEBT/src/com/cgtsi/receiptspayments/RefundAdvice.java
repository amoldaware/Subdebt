/*
 * Created on Feb 15, 2004
 *
 * To change the template for this generated file go to
 * Window&gt;Preferences&gt;Java&gt;Code Generation&gt;Code and Comments
 */
package com.cgtsi.receiptspayments;

import java.io.Serializable;

/**
 * @author VT8150
 *
 * To change the template for this generated type comment go to
 * Window&gt;Preferences&gt;Java&gt;Code Generation&gt;Code and Comments
 */
public class RefundAdvice implements Serializable 
{
	private String bankId;
	private String zoneId;
	private String branchId;
	
	private String danId;
	private String cgpan;
	private double amount;
	private String refundAdviceNumber;
	/**
	 * @return
	 */
	public double getAmount() {
		return amount;
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
	public String getDanId() {
		return danId;
	}

	/**
	 * @return
	 */
	public String getRefundAdviceNumber() {
		return refundAdviceNumber;
	}

	/**
	 * @param d
	 */
	public void setAmount(double d) {
		amount = d;
	}

	/**
	 * @param string
	 */
	public void setCgpan(String string) {
		cgpan = string;
	}

	/**
	 * @param string
	 */
	public void setDanId(String string) {
		danId = string;
	}

	/**
	 * @param string
	 */
	public void setRefundAdviceNumber(String string) {
		refundAdviceNumber = string;
	}

	/**
	 * @return
	 */
	public String getBankId() {
		return bankId;
	}

	/**
	 * @return
	 */
	public String getBranchId() {
		return branchId;
	}

	/**
	 * @return
	 */
	public String getZoneId() {
		return zoneId;
	}

	/**
	 * @param string
	 */
	public void setBankId(String string) {
		bankId = string;
	}

	/**
	 * @param string
	 */
	public void setBranchId(String string) {
		branchId = string;
	}

	/**
	 * @param string
	 */
	public void setZoneId(String string) {
		zoneId = string;
	}

}
