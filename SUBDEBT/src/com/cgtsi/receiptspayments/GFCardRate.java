/*
 * Created on Oct 13, 2004
 *
 * To change the template for this generated file go to
 * Window&gt;Preferences&gt;Java&gt;Code Generation&gt;Code and Comments
 */
package com.cgtsi.receiptspayments;

/**
 * @author SS14485
 *
 * To change the template for this generated type comment go to
 * Window&gt;Preferences&gt;Java&gt;Code Generation&gt;Code and Comments
 */
public class GFCardRate {
	
	private double lowRangeAmount;
	private double highRangeAmount;
	private double gfCardRate;
	private int cardRateId;
	

	/**
	 * @return
	 */
	public double getGfCardRate() {
		return gfCardRate;
	}

	/**
	 * @return
	 */
	public double getHighRangeAmount() {
		return highRangeAmount;
	}

	/**
	 * @return
	 */
	public double getLowRangeAmount() {
		return lowRangeAmount;
	}

	/**
	 * @param d
	 */
	public void setGfCardRate(double d) {
		gfCardRate = d;
	}

	/**
	 * @param d
	 */
	public void setHighRangeAmount(double d) {
		highRangeAmount = d;
	}

	/**
	 * @param d
	 */
	public void setLowRangeAmount(double d) {
		lowRangeAmount = d;
	}

	/**
	 * @return
	 */
	public int getCardRateId() {
		return cardRateId;
	}

	/**
	 * @param i
	 */
	public void setCardRateId(int i) {
		cardRateId = i;
	}

}
