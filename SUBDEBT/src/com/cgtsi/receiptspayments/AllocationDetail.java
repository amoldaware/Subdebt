/*
 * Created on Sep 24, 2003
 *
 * To change the template for this generated file go to
 * Window&gt;Preferences&gt;Java&gt;Code Generation&gt;Code and Comments
 */
package com.cgtsi.receiptspayments;


import java.util.Date ;
/**
 * @author GU14477
 *
 * To change the template for this generated type comment go to
 * Window&gt;Preferences&gt;Java&gt;Code Generation&gt;Code and Comments
 */
public class AllocationDetail 
{
	private String cgpan;
	private String nameOfUnit ;
	private String facilityCovered ;
	private Date firstDisbursementDate ;
	private String notAllocatedReason ;
	private String allocatedFlag;
	private double amountDue ;
	private double penalty ;
	private double guarCoverAmount;
	private String danNo;
	private String paymentId;
	private String appropriatedFlag;
	private String danType;
	private String bankId;
	private String zoneId;
	private String branchId;

	private String newDanId;

	public AllocationDetail()
	{
		
	}
	/**
	 * @return
	 */
	public double getAmountDue() 
	{
		return amountDue;
	}

	/**
	 * @return
	 */
	public String getCgpan() 
	{
		return cgpan;
	}

	/**
	 * @return
	 */
	public String getFacilityCovered() 
	{
		return facilityCovered;
	}

	/**
	 * @return
	 */
	public Date getFirstDisbursementDate() 
	{
			return firstDisbursementDate;
	}

	/**
	 * @return
	 */
	public String getNameOfUnit() 
	{
		return nameOfUnit;
	}

	/**
	 * @return
	 */
	public String getNotAllocatedReason() {
		return notAllocatedReason;
	}

	/**
	 * @param d
	 */
	public void setAmountDue(double d) {
		amountDue = d;
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
	public void setFacilityCovered(String string) {
		facilityCovered = string;
	}

	/**
	 * @param date
	 */
	public void setFirstDisbursementDate(Date date) {
		firstDisbursementDate = date;
	}

	/**
	 * @param string
	 */
	public void setNameOfUnit(String string) {
		nameOfUnit = string;
	}

	/**
	 * @param string
	 */
	public void setNotAllocatedReason(String string) {
		notAllocatedReason = string;
	}


	/**
	 * @return
	 */
	public double getPenalty() {
		return penalty;
	}

	/**
	 * @param d
	 */
	public void setPenalty(double d) {
		penalty = d;
	}

	/**
	 * @return
	 */
	public String getAllocatedFlag() {
		return allocatedFlag;
	}

	/**
	 * @param string
	 */
	public void setAllocatedFlag(String string) {
		allocatedFlag = string;
	}

	/**
	 * @return
	 */
	public double getGuarCoverAmount() {
		return guarCoverAmount;
	}

	/**
	 * @param d
	 */
	public void setGuarCoverAmount(double d) {
		guarCoverAmount = d;
	}

	/**
	 * @return
	 */
	public String getNewDanId() {
		return newDanId;
	}

	/**
	 * @param string
	 */
	public void setNewDanId(String string) {
		newDanId = string;
	}

	/**
	 * @return
	 */
	public String getDanNo() {
		return danNo;
	}

	/**
	 * @param string
	 */
	public void setDanNo(String string) {
		danNo = string;
	}

	/**
	 * @return
	 */
	public String getPaymentId() {
		return paymentId;
	}

	/**
	 * @param string
	 */
	public void setPaymentId(String string) {
		paymentId = string;
	}

	/**
	 * @return
	 */
	public String getAppropriatedFlag() {
		return appropriatedFlag;
	}

	/**
	 * @param string
	 */
	public void setAppropriatedFlag(String string) {
		appropriatedFlag = string;
	}

	/**
	 * @return
	 */
	public String getDanType() {
		return danType;
	}

	/**
	 * @param string
	 */
	public void setDanType(String string) {
		danType = string;
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
