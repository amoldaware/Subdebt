/*
 * Created on Nov 14, 2003
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
public class DisbursementAmount implements java.io.Serializable {
	private String cgpan;
	private double disbursementAmount;
	private Date disbursementDate;
	private String finalDisbursement;
	private String disbursementId;
	
	
	
	public DisbursementAmount(){
		
	}
	
	
	/**
	 * Access method for the disbursementAmount property.
	 * 
	 * @return   the current value of the disbursementAmount property
	 */
	public double getDisbursementAmount() 
	{
	   return disbursementAmount;    
	}
   
	/**
	 * Sets the value of the disbursementAmount property.
	 * 
	 * @param aDisbursementAmount the new value of the disbursementAmount property
	 */
	public void setDisbursementAmount(double aDisbursementAmount) 
	{
	   disbursementAmount = aDisbursementAmount;
	}
   
	/**
	 * Access method for the disbursementDate property.
	 * 
	 * @return   the current value of the disbursementDate property
	 */
	public Date getDisbursementDate() 
	{
	   return disbursementDate;    
	}
   
	/**
	 * Sets the value of the disbursementDate property.
	 * 
	 * @param aDisbursementDate the new value of the disbursementDate property
	 */
	public void setDisbursementDate(Date aDisbursementDate) 
	{
	   disbursementDate = aDisbursementDate;
	}
   
	/**
	 * Access method for the finalDisbursement property.
	 * 
	 * @return   the current value of the finalDisbursement property
	 */
	public String getFinalDisbursement() 
	{
	   return finalDisbursement;    
	}
   
	/**
	 * Sets the value of the finalDisbursement property.
	 * 
	 * @param aFinalDisbursement the new value of the finalDisbursement property
	 */
	public void setFinalDisbursement(String aFinalDisbursement) 
	{
	   finalDisbursement = aFinalDisbursement;
	}

	/**
	 * @return
	 */
	public String getCgpan() {
		return cgpan;
	}

	/**
	 * @param string
	 */
	public void setCgpan(String string) {
		cgpan = string;
	}


	/**
	 * @return
	 */
	public String getDisbursementId() {
		return disbursementId;
	}

	/**
	 * @param string
	 */
	public void setDisbursementId(String string) {
		disbursementId = string;
	}

}
