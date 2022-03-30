/*
 * Created on Dec 3, 2003
 *
 * To change the template for this generated file go to
 * Window&gt;Preferences&gt;Java&gt;Code Generation&gt;Code and Comments
 */
package com.cgtsi.guaranteemaintenance;

/**
 * @author GU14477
 *
 * To change the template for this generated type comment go to
 * Window&gt;Preferences&gt;Java&gt;Code Generation&gt;Code and Comments
 */
public class CgpanInfo implements java.io.Serializable{
	private String cgpan;

	private String scheme;
	private String reasonForClosure;
	private String closureChecked ;
	private double sanctionedAmount;
	private String remarks;
	
	public CgpanInfo(){
		
	}


	/**
	 * @return
	 */
	public String getCgpan() {
		return cgpan;
	}

	/**
	 * @return
	 */
	public String getClosureChecked() {
		return closureChecked;
	}

	/**
	 * @return
	 */
	public String getReasonForClosure() {
		return reasonForClosure;
	}

	/**
	 * @return
	 */
	public double getSanctionedAmount() {
		return sanctionedAmount;
	}

	/**
	 * @return
	 */
	public String getScheme() {
		return scheme;
	}

	/**
	 * @param string
	 */
	public void setCgpan(String string) {
		cgpan = string;
	}

	/**
	 * @param b
	 */
	public void setClosureChecked(String b) {
		closureChecked = b;
	}

	/**
	 * @param string
	 */
	public void setReasonForClosure(String string) {
		reasonForClosure = string;
	}

	/**
	 * @param d
	 */
	public void setSanctionedAmount(double d) {
		sanctionedAmount = d;
	}

	/**
	 * @param string
	 */
	public void setScheme(String string) {
		scheme = string;
	}

	/**
	 * @return
	 */
	public String getRemarks() {
		return remarks;
	}

	/**
	 * @param string
	 */
	public void setRemarks(String string) {
		remarks = string;
	}

}
