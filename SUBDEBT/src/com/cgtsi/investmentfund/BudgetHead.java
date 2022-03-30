/*
 * Created on Jan 11, 2004
 *
 * To change the template for this generated file go to
 * Window&gt;Preferences&gt;Java&gt;Code Generation&gt;Code and Comments
 */
package com.cgtsi.investmentfund;

import java.io.Serializable;

/**
 * @author VT8150
 *
 * To change the template for this generated type comment go to
 * Window&gt;Preferences&gt;Java&gt;Code Generation&gt;Code and Comments
 */
public class BudgetHead implements Serializable
{
	
	private String budgetHead="";
	private String budgetHeadType="";
	private String newBudgetHead="";
	private String modBudgetHead="";

	/**
	 * @return
	 */
	public String getBudgetHead() {
		return budgetHead;
	}

	/**
	 * @return
	 */
	public String getBudgetHeadType() {
		return budgetHeadType;
	}

	/**
	 * @param string
	 */
	public void setBudgetHead(String string) {
		budgetHead = string;
	}

	/**
	 * @param string
	 */
	public void setBudgetHeadType(String string) {
		budgetHeadType = string;
	}

	/**
	 * @return
	 */
	public String getModBudgetHead() {
		return modBudgetHead;
	}

	/**
	 * @return
	 */
	public String getNewBudgetHead() {
		return newBudgetHead;
	}

	/**
	 * @param string
	 */
	public void setModBudgetHead(String string) {
		modBudgetHead = string;
	}

	/**
	 * @param string
	 */
	public void setNewBudgetHead(String string) {
		newBudgetHead = string;
	}

}
