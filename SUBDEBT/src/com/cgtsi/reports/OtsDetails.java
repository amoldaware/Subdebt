/*
 * Created on Oct 8, 2004
 *
 * To change the template for this generated file go to
 * Window&gt;Preferences&gt;Java&gt;Code Generation&gt;Code and Comments
 */
package com.cgtsi.reports;

/**
 * @author RT14509
 *
 * To change the template for this generated type comment go to
 * Window&gt;Preferences&gt;Java&gt;Code Generation&gt;Code and Comments
 */
public class OtsDetails {

	/**
	 * 
	 */
	
	
	private String bid;
	private String borrowerName;
	private String otsReason;
	private String otsRemarks;
	private String otsDecision;
	private String cgpan;
	private double proposedAmount;
	private double sacrificedAmount;
	private double outstandingAmount;
	private double tcSanctionedAmount;
	private double wcFbSanctionedAmount;
	private double wcNfbSanctionedAmount;
	
	
	
	
	
	public OtsDetails() {

	}

	/**
	 * @return
	 */
	public String getBid() {
		return bid;
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
	public String getCgpan() {
		return cgpan;
	}

	/**
	 * @return
	 */
	public String getOtsDecision() {
		return otsDecision;
	}

	/**
	 * @return
	 */
	public String getOtsReason() {
		return otsReason;
	}

	/**
	 * @return
	 */
	public String getOtsRemarks() {
		return otsRemarks;
	}

	/**
	 * @return
	 */
	public double getOutstandingAmount() {
		return outstandingAmount;
	}

	/**
	 * @return
	 */
	public double getProposedAmount() {
		return proposedAmount;
	}

	/**
	 * @return
	 */
	public double getSacrificedAmount() {
		return sacrificedAmount;
	}

	/**
	 * @param string
	 */
	public void setBid(String string) {
		bid = string;
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
	public void setCgpan(String string) {
		cgpan = string;
	}

	/**
	 * @param string
	 */
	public void setOtsDecision(String string) {
		otsDecision = string;
	}

	/**
	 * @param string
	 */
	public void setOtsReason(String string) {
		otsReason = string;
	}

	/**
	 * @param string
	 */
	public void setOtsRemarks(String string) {
		otsRemarks = string;
	}

	/**
	 * @param d
	 */
	public void setOutstandingAmount(double d) {
		outstandingAmount = d;
	}

	/**
	 * @param d
	 */
	public void setProposedAmount(double d) {
		proposedAmount = d;
	}

	/**
	 * @param d
	 */
	public void setSacrificedAmount(double d) {
		sacrificedAmount = d;
	}

	/**
	 * @return
	 */
	public double getTcSanctionedAmount() {
		return tcSanctionedAmount;
	}

	/**
	 * @return
	 */
	public double getWcFbSanctionedAmount() {
		return wcFbSanctionedAmount;
	}

	/**
	 * @return
	 */
	public double getWcNfbSanctionedAmount() {
		return wcNfbSanctionedAmount;
	}

	/**
	 * @param d
	 */
	public void setTcSanctionedAmount(double d) {
		tcSanctionedAmount = d;
	}

	/**
	 * @param d
	 */
	public void setWcFbSanctionedAmount(double d) {
		wcFbSanctionedAmount = d;
	}

	/**
	 * @param d
	 */
	public void setWcNfbSanctionedAmount(double d) {
		wcNfbSanctionedAmount = d;
	}

}
