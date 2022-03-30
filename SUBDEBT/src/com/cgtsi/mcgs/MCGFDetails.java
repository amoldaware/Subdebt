/*************************************************************
   *
   * Name of the class: MCGFDetails.java
   * This class holds the properties related to an MCGF.
   * 
   *
   * @author : Veldurai T
   * @version:  
   * @since: Dec 3, 2003
   **************************************************************/
package com.cgtsi.mcgs;

import java.util.Date;

/**
 * @author Veldurai T
 *
 * This class holds the properied related to an MCGF.
 * 
 */
public class MCGFDetails implements java.io.Serializable
{
	private String participatingBank=null;
	
	private String participatingBankBranch=null;
	
	private String mcgfDistrict=null;
	
	private double mcgfApprovedAmt=0;
	
	private Date mcgfApprovedDate=null;
	
	private Date mcgfGuaranteeCoverStartDate=null;
	
	private String mcgfName=null;
	
	private String mcgfId=null;
	
	private String applicationReferenceNumber=null;
	
	/**
	 * @return
	 */
	public double getMcgfApprovedAmt() {
		return mcgfApprovedAmt;
	}

	/**
	 * @return
	 */
	public Date getMcgfApprovedDate() {
		return mcgfApprovedDate;
	}

	/**
	 * @return
	 */
	public String getMcgfDistrict() {
		return mcgfDistrict;
	}

	/**
	 * @return
	 */
	public Date getMcgfGuaranteeCoverStartDate() {
		return mcgfGuaranteeCoverStartDate;
	}

	/**
	 * @return
	 */
	public String getMcgfId() {
		return mcgfId;
	}

	/**
	 * @return
	 */
	public String getMcgfName() {
		return mcgfName;
	}

	/**
	 * @return
	 */
	public String getParticipatingBank() {
		return participatingBank;
	}

	/**
	 * @return
	 */
	public String getParticipatingBankBranch() {
		return participatingBankBranch;
	}

	/**
	 * @param d
	 */
	public void setMcgfApprovedAmt(double d) {
		mcgfApprovedAmt = d;
	}

	/**
	 * @param date
	 */
	public void setMcgfApprovedDate(Date date) {
		mcgfApprovedDate = date;
	}

	/**
	 * @param string
	 */
	public void setMcgfDistrict(String string) {
		mcgfDistrict = string;
	}

	/**
	 * @param date
	 */
	public void setMcgfGuaranteeCoverStartDate(Date date) {
		mcgfGuaranteeCoverStartDate = date;
	}

	/**
	 * @param string
	 */
	public void setMcgfId(String string) {
		mcgfId = string;
	}

	/**
	 * @param string
	 */
	public void setMcgfName(String string) {
		mcgfName = string;
	}

	/**
	 * @param string
	 */
	public void setParticipatingBank(String string) {
		participatingBank = string;
	}

	/**
	 * @param string
	 */
	public void setParticipatingBankBranch(String string) {
		participatingBankBranch = string;
	}

	/**
	 * @return
	 */
	public String getApplicationReferenceNumber() {
		return applicationReferenceNumber;
	}

	/**
	 * @param string
	 */
	public void setApplicationReferenceNumber(String string) {
		applicationReferenceNumber = string;
	}

}
