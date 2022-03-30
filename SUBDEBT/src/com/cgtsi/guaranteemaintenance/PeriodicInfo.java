/*
 * Created on Nov 13, 2003
 *
 * To change the template for this generated file go to
 * Window&gt;Preferences&gt;Java&gt;Code Generation&gt;Code and Comments
 */
package com.cgtsi.guaranteemaintenance;

import java.util.ArrayList;

/**
 * @author NS30571
 *
 * To change the template for this generated type comment go to
 * Window&gt;Preferences&gt;Java&gt;Code Generation&gt;Code and Comments
 */
public class PeriodicInfo implements java.io.Serializable{
	private String borrowerId ;
	private String borrowerName ;
	private ArrayList outstandingDetails = null;
	private ArrayList disbursementDetails = null;
	private ArrayList repaymentDetails = null;
	private NPADetails npaDetail = null;
	private ArrayList recoveryDetails = null;
	
	private boolean isVerified;
	
	/**
	* this boolean variable has to be set TRUE when exporting Periodic Info file.
	* while uploading, this variable if TRUE indicates 
	*	that the details have been entered with a export file.
	* if FALSE indicates that the details were entered without the export file 
	*	so CGPAN and borrower ids have to be necessarily validated.
	*/
	private boolean export;
	  
	
	public static void main(String[] args) {
	}
	/**
	 * @return
	 */
	public ArrayList getDisbursementDetails() {
		return disbursementDetails;
	}

	/**
	 * @return
	 */
	public NPADetails getNpaDetails() {
		return npaDetail;
	}

	/**
	 * @return
	 */
	public ArrayList getOutstandingDetails() {
		return outstandingDetails;
	}

	/**
	 * @return
	 */
	public ArrayList getRecoveryDetails() {
		return recoveryDetails;
	}

	/**
	 * @return
	 */
	public ArrayList getRepaymentDetails() {
		return repaymentDetails;
	}

	/**
	 * @param list
	 */
	public void setDisbursementDetails(ArrayList list) {
		disbursementDetails = list;
	}

	/**
	 * @param details
	 */
	public void setNpaDetails(NPADetails details) {
		npaDetail = details;
	}

	/**
	 * @param list
	 */
	public void setOutstandingDetails(ArrayList list) {
		outstandingDetails = list;
	}

	/**
	 * @param recovery
	 */
	public void setRecoveryDetails(ArrayList recoveryDetails) {
		this.recoveryDetails = recoveryDetails;
	}

	/**
	 * @param list
	 */
	public void setRepaymentDetails(ArrayList list) {
		repaymentDetails = list;
	}

	/**
	 * @return
	 */
	public String getBorrowerId() {
		return borrowerId;
	}

	/**
	 * @return
	 */
	public String getBorrowerName() {
		return borrowerName;
	}

	/**
	 * @return
	 */
	public NPADetails getNpaDetail() {
		return npaDetail;
	}

	/**
	 * @param string
	 */
	public void setBorrowerId(String string) {
		borrowerId = string;
	}

	/**
	 * @param string
	 */
	public void setBorrowerName(String string) {
		borrowerName = string;
	}

	/**
	 * @param details
	 */
	public void setNpaDetail(NPADetails details) {
		npaDetail = details;
	}

	public void setExport(boolean aExport)
	{
		export = aExport;
	}

	public boolean getExport()
	{
		return export;
	}
	/**
	 * @return
	 */
	public boolean getIsVerified() {
		return isVerified;
	}

	/**
	 * @param b
	 */
	public void setIsVerified(boolean b) {
		isVerified = b;
	}

}
