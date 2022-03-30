/*
 * Created on Dec 5, 2003
 *
 * To change the template for this generated file go to
 * Window&gt;Preferences&gt;Java&gt;Code Generation&gt;Code and Comments
 */
package com.cgtsi.reports;

import java.sql.Date;

/**
 * @author RT14509
 *
 * To change the template for this generated type comment go to
 * Window&gt;Preferences&gt;Java&gt;Code Generation&gt;Code and Comments
 */
public class GFee {

	private String dan;
	private String flag;
	private int applications;
	private double guaranteeFee;
	private double guaranteeFeePaid;
	private double outstanding;
	private Date danDate;
	private String memberId;   
	private String zone;
	private String branch;
	
	public GFee() {
		
	}



	/**
	 * @return
	 */
	public String getDan() {
		return dan;
	}

	/**
	 * @param string
	 */
	public void setDan(String aDan) {
		dan = aDan;
	}
	
	

	/**
	 * @return
	 */
	public int getApplications() {
		return applications;
	}

	/**
	 * @param string
	 */
	public void setApplications(int aApplications) {
		applications = aApplications;
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
	public double getGuaranteeFeePaid() {
		return guaranteeFeePaid;
	}

	/**
	 * @param d
	 */
	public void setGuaranteeFeePaid(double dGuaranteeFeePaid) {
		guaranteeFeePaid = dGuaranteeFeePaid;
	}

	/**
	 * @return
	 */
	public double getOutstanding() {
		return outstanding;
	}

	/**
	 * @param d
	 */
	public void setOutstanding(double dOutstanding) {
		outstanding = dOutstanding;
	}

	/**
	 * @return
	 */
	public Date getDanDate() {
		return danDate;
	}

	/**
	 * @param date
	 */
	public void setDanDate(Date date) {
		danDate = date;
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
	public String getMemberId() {
		return memberId;
	}

	/**
	 * @param string
	 */
	public void setMemberId(String string) {
		memberId = string;
	}

	/**
	 * @return
	 */
	public String getBranch() {
		return branch;
	}

	/**
	 * @return
	 */
	public String getZone() {
		return zone;
	}

	/**
	 * @param string
	 */
	public void setBranch(String string) {
		branch = string;
	}

	/**
	 * @param string
	 */
	public void setZone(String string) {
		zone = string;
	}

}


