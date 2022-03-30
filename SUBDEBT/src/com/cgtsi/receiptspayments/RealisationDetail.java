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
public class RealisationDetail {
	
	private String paymentId ;
	private double realisationAmount ;
	private Date realisationDate ;
	
	
	

	public RealisationDetail()
	{
		
	}
	/**
	 * @return
	 */
	public String getPaymentId() {
		return paymentId;
	}

	/**
	 * @return
	 */
	public double getRealisationAmount() {
		return realisationAmount;
	}

	/**
	 * @return
	 */
	public Date getRealisationDate() {
		return realisationDate;
	}

	/**
	 * @param string
	 */
	public void setPaymentId(String aPaymentId) {
		paymentId = aPaymentId;
	}

	/**
	 * @param d
	 */
	public void setRealisationAmount(double aRealisationAmount) {
		realisationAmount = aRealisationAmount;
	}

	/**
	 * @param date
	 */
	public void setRealisationDate(Date aRealisationDate) {
		realisationDate = aRealisationDate;
	}

}
