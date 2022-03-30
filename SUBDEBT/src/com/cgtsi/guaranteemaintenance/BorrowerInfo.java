/*
 * Created on Dec 3, 2003
 *
 * To change the template for this generated file go to
 * Window&gt;Preferences&gt;Java&gt;Code Generation&gt;Code and Comments
 */
package com.cgtsi.guaranteemaintenance;

import java.util.ArrayList;

/**
 * @author GU14477 
 *
 * To change the template for this generated type comment go to
 * Window&gt;Preferences&gt;Java&gt;Code Generation&gt;Code and Comments
 */
public class BorrowerInfo implements java.io.Serializable {
	private String borrowerId;
	private String borrowerName;
	private ArrayList cgpanInfos; 
	
	public BorrowerInfo(){
		
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
	public ArrayList getCgpanInfos() {
		return cgpanInfos;
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
	 * @param list
	 */
	public void setCgpanInfos(ArrayList list) {
		cgpanInfos = list;
	}

}
