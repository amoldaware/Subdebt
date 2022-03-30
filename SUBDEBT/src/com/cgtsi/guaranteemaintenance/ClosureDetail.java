/*
 * Created on Dec 2, 2003
 *
 * To change the template for this generated file go to
 * Window&gt;Preferences&gt;Java&gt;Code Generation&gt;Code and Comments
 */
package com.cgtsi.guaranteemaintenance;


import java.util.HashMap;


/**
 * @author GU14477
 *
 * To change the template for this generated type comment go to
 * Window&gt;Preferences&gt;Java&gt;Code Generation&gt;Code and Comments
 */
public class ClosureDetail implements java.io.Serializable {
	
	private String memberId;
	private HashMap borrowerInfos; 
	
	public ClosureDetail(){
		
	}

	public String getMemberId() {
		return memberId;
	}

	
	/**
	 * @param string
	 */
	public void setMemberId(String string) {
		memberId = string;
	}

	/**
	 * @return
	 */
	public HashMap getBorrowerInfos() {
		return borrowerInfos;
	}

	/**
	 * @param hashtable
	 */
	public void setBorrowerInfos(HashMap hashMap) {
		borrowerInfos = hashMap;
	}

}
