package com.cgtsi.reports;

import java.util.Date;

public class GuaranteeCover
{
	private String cgpan;
	private String ssiName;
	private String flag;
	private double termCredit;
	private double workingCapital;
	private double total;
	private Date tcDate;
	private Date wcDate;
	private Date date;
	private Date totalTenure;
	private int tenure;
	private double guaranteeFee;
	private Date approvalDate;
	private Date issueDate;
	private Date tcEndDate;
	private Date wcEndDate;
	private int timeInterval;
	/*
	 * Added the following three attributes on 11-sep-2004 to fix clientbugs.
	 */
	private String memberId;
	private	String memberShortName;
	private String memberZoneName;
	private String memberBranchName;
	
	
	public GuaranteeCover()
	{
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
	public void setCgpan(String aCgpan) {
		cgpan = aCgpan;
	}

	/**
	 * @return
	 */
	public String getSsiName() {
		return ssiName;
	}

	/**
	 * @param string
	 */
	public void setSsiName(String aSsiName) {
		ssiName = aSsiName;
	}

	/**
	 * @return
	 */
	public double getTermCredit() {
		return termCredit;
	}

	/**
	 * @param d
	 */
	public void setTermCredit(double dTermCredit) {
		termCredit = dTermCredit;
	}

	/**
	 * @return
	 */
	public double getWorkingCapital() {
		return workingCapital;
	}

	/**
	 * @param d
	 */
	public void setWorkingCapital(double dWorkingCapital) {
		workingCapital = dWorkingCapital;
	}

	/**
	 * @return
	 */
	public double getTotal() {
		return total;
	}

	/**
	 * @param d
	 */
	public void setTotal(double dTotal) {
		total = dTotal;
	}

	/**
	 * @return
	 */
	public int getTenure() {
		return tenure;
	}

	/**
	 * @param string
	 */
	public void setTenure(int aTenure) {
		tenure = aTenure;
	}

	/**
	 * @return
	 */
	public double getGuaranteeFee() {
		return guaranteeFee;
	}

	/**
	 * @param d
	 */
	public void setGuaranteeFee(double dGuaranteeFee) {
		guaranteeFee = dGuaranteeFee;
	}

	/**
	 * @return
	 */
	public Date getApprovalDate() {
		return approvalDate;
	}

	/**
	 * @param date
	 */
	public void setApprovalDate(Date aApprovalDate) {
		approvalDate = aApprovalDate;
	}


	public Date getIssueDate() {
		return issueDate;
	}

	/**
	 * @param date
	 */
	public void setIssueDate(Date aIssueDate) {
		issueDate = aIssueDate;
	}

	/**
	 * @return
	 */
	public int getTimeInterval() {
		return timeInterval;
	}

	/**
	 * @param string
	 */
	public void setTimeInterval(int aTimeInterval) {
		timeInterval = aTimeInterval;
	}

	/**
	 * @return
	 */
	public String getFlag() {
		return flag;
	}

	/**
	 * @param string
	 */
	public void setFlag(String string) {
		flag = string;
	}

	/**
	 * @return
	 */
	public Date getTotalTenure() {
		return totalTenure;
	}

	/**
	 * @param date
	 */
	public void setTotalTenure(Date date) {
		totalTenure = date;
	}
	
	
	/**
	 * @return
	 */
	public Date getTcDate() {
		return tcDate;
	}

	/**
	 * @param date
	 */
	public void setTcDate(Date aTcDate) {
		tcDate = aTcDate;
	}
	
	/**
	 * @return
	 */
	public Date getWcDate() {
		return wcDate;
	}

	/**
	 * @param date
	 */
	public void setWcDate(Date aWcDate) {
		wcDate = aWcDate;
	}
	
	/**
	 * @return
	 */
	public Date getTcEndDate() {
		return tcEndDate;
	}

	/**
	 * @param date
	 */
	public void setTcEndDate(Date aTcEndDate) {
		tcEndDate = aTcEndDate;
	}
	
	/**
	 * @return
	 */
	public Date getWcEndDate() {
		return wcEndDate;
	}

	/**
	 * @param date
	 */
	public void setWcEndDate(Date aWcEndDate) {
		wcEndDate = aWcEndDate;
	}
	
	
	/**
	 * @return
	 */
	public Date getDate() {
		return date;
	}

	/**
	 * @param date
	 */
	public void setDate(Date aDate) {
		date = aDate;
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
	public String getMemberShortName() {
		return memberShortName;
	}

	/**
	 * @return
	 */
	public String getMemberZoneName() {
		return memberZoneName;
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
	public void setMemberShortName(String string) {
		memberShortName = string;
	}

	/**
	 * @param string
	 */
	public void setMemberZoneName(String string) {
		memberZoneName = string;
	}

	/**
	 * @return
	 */
	public String getMemberBranchName() {
		return memberBranchName;
	}

	/**
	 * @param string
	 */
	public void setMemberBranchName(String string) {
		memberBranchName = string;
	}

}