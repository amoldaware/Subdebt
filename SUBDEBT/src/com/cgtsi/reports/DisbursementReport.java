/*
 * Created on Dec 3, 2003
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
public class DisbursementReport {
	
	private String memberid;
	private String cgpan;
	private double termCredit;
	private double disbursedAmount;
	private String dibursementType;
	private Date disbursementDate;
	
	public DisbursementReport(){
		
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
	public Date getDisbursementDate() {
		return disbursementDate;
	}
	/**
	 * @param date
	 */
	public void setDisbursementDate(Date date) {
		disbursementDate = date;
	}
	
	

	/**
	 * @return
	 */
	public String getDibursementType() {
		return dibursementType;
	}
	/**
	 * @param string
	 */
	public void setDibursementType(String string) {
		dibursementType = string;
	}
	
		

	/**
	 * @return
	 */
	public double getDisbursedAmount() {
		return disbursedAmount;
	}
	/**
	 * @param d
	 */
	public void setDisbursedAmount(double d) {
		disbursedAmount = d;
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
	public double getTermCredit() {
		return termCredit;
	}
	/**
	 * @param d
	 */
	public void setTermCredit(double d) {
		termCredit = d;
	}


}
