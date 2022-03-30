/*
 * Created on Feb 8, 2004
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
public class OutstandingAmount implements java.io.Serializable
{
	
	private String cgpan;
	private String wcoId;
	private String tcoId;
	
	private double tcPrincipalOutstandingAmount;
	private double tcInterestOutstandingAmount;   
	private Date tcOutstandingAsOnDate;
   
	private double wcFBPrincipalOutstandingAmount;
	private double wcFBInterestOutstandingAmount; 
	private Date wcFBOutstandingAsOnDate;
   
	private double wcNFBInterestOutstandingAmount;
	private double wcNFBPrincipalOutstandingAmount;
	private Date wcNFBOutstandingAsOnDate;


	public OutstandingAmount(){
		
	}


	/**
	 * @return
	 */
	public double getTcInterestOutstandingAmount() {
		return tcInterestOutstandingAmount;
	}

	/**
	 * @return
	 */
	public String getTcoId() {
		return tcoId;
	}

	/**
	 * @return
	 */
	public Date getTcOutstandingAsOnDate() {
		return tcOutstandingAsOnDate;
	}

	/**
	 * @return
	 */
	public double getTcPrincipalOutstandingAmount() {
		return tcPrincipalOutstandingAmount;
	}

	/**
	 * @return
	 */
	public double getWcFBInterestOutstandingAmount() {
		return wcFBInterestOutstandingAmount;
	}

	/**
	 * @return
	 */
	public Date getWcFBOutstandingAsOnDate() {
		return wcFBOutstandingAsOnDate;
	}

	/**
	 * @return
	 */
	public double getWcFBPrincipalOutstandingAmount() {
		return wcFBPrincipalOutstandingAmount;
	}

	/**
	 * @return
	 */
	public double getWcNFBInterestOutstandingAmount() {
		return wcNFBInterestOutstandingAmount;
	}

	/**
	 * @return
	 */
	public Date getWcNFBOutstandingAsOnDate() {
		return wcNFBOutstandingAsOnDate;
	}

	/**
	 * @return
	 */
	public double getWcNFBPrincipalOutstandingAmount() {
		return wcNFBPrincipalOutstandingAmount;
	}

	/**
	 * @return
	 */
	public String getWcoId() {
		return wcoId;
	}

	/**
	 * @param d
	 */
	public void setTcInterestOutstandingAmount(double d) {
		tcInterestOutstandingAmount = d;
	}

	/**
	 * @param string
	 */
	public void setTcoId(String string) {
		tcoId = string;
	}

	/**
	 * @param date
	 */
	public void setTcOutstandingAsOnDate(Date date) {
		tcOutstandingAsOnDate = date;
	}

	/**
	 * @param d
	 */
	public void setTcPrincipalOutstandingAmount(double d) {
		tcPrincipalOutstandingAmount = d;
	}

	/**
	 * @param d
	 */
	public void setWcFBInterestOutstandingAmount(double d) {
		wcFBInterestOutstandingAmount = d;
	}

	/**
	 * @param date
	 */
	public void setWcFBOutstandingAsOnDate(Date date) {
		wcFBOutstandingAsOnDate = date;
	}

	/**
	 * @param d
	 */
	public void setWcFBPrincipalOutstandingAmount(double d) {
		wcFBPrincipalOutstandingAmount = d;
	}

	/**
	 * @param d
	 */
	public void setWcNFBInterestOutstandingAmount(double d) {
		wcNFBInterestOutstandingAmount = d;
	}

	/**
	 * @param date
	 */
	public void setWcNFBOutstandingAsOnDate(Date date) {
		wcNFBOutstandingAsOnDate = date;
	}

	/**
	 * @param d
	 */
	public void setWcNFBPrincipalOutstandingAmount(double d) {
		wcNFBPrincipalOutstandingAmount = d;
	}

	/**
	 * @param string
	 */
	public void setWcoId(String string) {
		wcoId = string;
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

}
