/*
 * Created on Jan 21, 2004
 *
 * To change the template for this generated file go to
 * Window&gt;Preferences&gt;Java&gt;Code Generation&gt;Code and Comments
 */
package com.cgtsi.investmentfund;

import java.io.Serializable;

/**
 * @author VT8150
 *
 * To change the template for this generated type comment go to
 * Window&gt;Preferences&gt;Java&gt;Code Generation&gt;Code and Comments
 */
public class ExposureDetail implements Serializable 
{
	private String maturityName;
	private String instrumentName;
	private double amountInvested;
	private double ceilingAmount;
	
	/**
	 * @return
	 */
	public double getAmountInvested() {
		return amountInvested;
	}

	/**
	 * @return
	 */
	public double getCeilingAmount() {
		return ceilingAmount;
	}

	/**
	 * @return
	 */
	public String getInstrumentName() {
		return instrumentName;
	}

	/**
	 * @return
	 */
	public String getMaturityName() {
		return maturityName;
	}

	/**
	 * @param d
	 */
	public void setAmountInvested(double d) {
		amountInvested = d;
	}

	/**
	 * @param d
	 */
	public void setCeilingAmount(double d) {
		ceilingAmount = d;
	}

	/**
	 * @param string
	 */
	public void setInstrumentName(String string) {
		instrumentName = string;
	}

	/**
	 * @param string
	 */
	public void setMaturityName(String string) {
		maturityName = string;
	}

}
