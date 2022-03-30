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
public class CeilingDetails implements Serializable 
{
	private int flag;
	private String ceilingName;
	private double ceilingLimit;
	private double ceilingNetWorth;
	private double celingTangibleAssets;
	private double ceilingInPercentage;
	

	/**
	 * @return
	 */
	public double getCeilingInPercentage() {
		return ceilingInPercentage;
	}

	/**
	 * @return
	 */
	public double getCeilingLimit() {
		return ceilingLimit;
	}

	/**
	 * @return
	 */
	public String getCeilingName() {
		return ceilingName;
	}

	/**
	 * @return
	 */
	public double getCeilingNetWorth() {
		return ceilingNetWorth;
	}

	/**
	 * @return
	 */
	public double getCelingTangibleAssets() {
		return celingTangibleAssets;
	}

	/**
	 * @return
	 */
	public int getFlag() {
		return flag;
	}

	/**
	 * @param d
	 */
	public void setCeilingInPercentage(double d) {
		ceilingInPercentage = d;
	}

	/**
	 * @param d
	 */
	public void setCeilingLimit(double d) {
		ceilingLimit = d;
	}

	/**
	 * @param string
	 */
	public void setCeilingName(String string) {
		ceilingName = string;
	}

	/**
	 * @param d
	 */
	public void setCeilingNetWorth(double d) {
		ceilingNetWorth = d;
	}

	/**
	 * @param d
	 */
	public void setCelingTangibleAssets(double d) {
		celingTangibleAssets = d;
	}

	/**
	 * @param i
	 */
	public void setFlag(int i) {
		flag = i;
	}

}
