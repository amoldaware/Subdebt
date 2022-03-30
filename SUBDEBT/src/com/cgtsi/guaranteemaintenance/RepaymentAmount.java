/*
 * Created on Nov 17, 2003
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
public class RepaymentAmount implements java.io.Serializable {
	private String cgpan;
	private double repaymentAmount;
	private Date repaymentDate;
	private String repayId;
	

	/**
	 * @return
	 */
	public String getCgpan() {
		return cgpan;
	}

	/**
	 * @return
	 */
	public double getRepaymentAmount() {
		return repaymentAmount;
	}

	/**
	 * @return
	 */
	public Date getRepaymentDate() {
		return repaymentDate;
	}

	/**
	 * @param string
	 */
	public void setCgpan(String string) {
		cgpan = string;
	}

	/**
	 * @param d
	 */
	public void setRepaymentAmount(double d) {
		repaymentAmount = d;
	}

	/**
	 * @param date
	 */
	public void setRepaymentDate(Date date) {
		repaymentDate = date;
	}

	/**
	 * @return
	 */
	public String getRepayId() {
		return repayId;
	}

	/**
	 * @param string
	 */
	public void setRepayId(String string) {
		repayId = string;
	}

}
