/*
 * Created on Oct 11, 2004
 *
 * To change the template for this generated file go to
 * Window&gt;Preferences&gt;Java&gt;Code Generation&gt;Code and Comments
 */
package com.cgtsi.risk;

import java.util.Date;

/**
 * @author RT14509
 *
 * To change the template for this generated type comment go to
 * Window&gt;Preferences&gt;Java&gt;Code Generation&gt;Code and Comments 
 */
public class DefaultRate {

	
	private String mliId;
	private String mliName;
	private Date settlementDate;
	private double cumulativeClaimSettlement;
	private double guaranteeCoverIssuedAmount;
	private double defaultRate;
	
	
	
	
	public DefaultRate() {

	}

	/**
	 * @return
	 */
	public double getCumulativeClaimSettlement() {
		return cumulativeClaimSettlement;
	}



	/**
	 * @return
	 */
	public double getGuaranteeCoverIssuedAmount() {
		return guaranteeCoverIssuedAmount;
	}

	/**
	 * @return
	 */
	public String getMliId() {
		return mliId;
	}

	/**
	 * @return
	 */
	public String getMliName() {
		return mliName;
	}

	/**
	 * @return
	 */
	public Date getSettlementDate() {
		return settlementDate;
	}

	/**
	 * @param d
	 */
	public void setCumulativeClaimSettlement(double d) {
		cumulativeClaimSettlement = d;
	}



	/**
	 * @param d
	 */
	public void setGuaranteeCoverIssuedAmount(double d) {
		guaranteeCoverIssuedAmount = d;
	}

	/**
	 * @param string
	 */
	public void setMliId(String string) {
		mliId = string;
	}

	/**
	 * @param string
	 */
	public void setMliName(String string) {
		mliName = string;
	}

	/**
	 * @param date
	 */
	public void setSettlementDate(Date date) {
		settlementDate = date;
	}

	/**
	 * @return
	 */
	public double getDefaultRate() {
		return defaultRate;
	}

	/**
	 * @param d
	 */
	public void setDefaultRate(double d) {
		defaultRate = d;
	}

}
