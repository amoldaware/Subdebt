/*
 * Created on Jan 24, 2004
 *
 * To change the template for this generated file go to
 * Window&gt;Preferences&gt;Java&gt;Code Generation&gt;Code and Comments
 */
package com.cgtsi.investmentfund;

import java.io.Serializable;
import java.util.Date;

/**
 * @author VT8150
 *
 * To change the template for this generated type comment go to
 * Window&gt;Preferences&gt;Java&gt;Code Generation&gt;Code and Comments
 */
public class GuaranteeIssuedDetail implements Serializable 
{
	
	private Date guaranteeIssuedDate;
	
	private double guaranteeIssuedAmount;
	
	

	/**
	 * @return
	 */
	public double getGuaranteeIssuedAmount() {
		return guaranteeIssuedAmount;
	}

	/**
	 * @return
	 */
	public Date getGuaranteeIssuedDate() {
		return guaranteeIssuedDate;
	}

	/**
	 * @param d
	 */
	public void setGuaranteeIssuedAmount(double d) {
		guaranteeIssuedAmount = d;
	}

	/**
	 * @param date
	 */
	public void setGuaranteeIssuedDate(Date date) {
		guaranteeIssuedDate = date;
	}

}
