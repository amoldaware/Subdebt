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
public class BudgetSubHead implements Serializable
{
	private String budgetSubHeadType="";
	private String budgetHead="";
	private String budgetSubHeadTitle="";
	private String newBudgetSubHeadTitle="";
	private String modBudgetSubHeadTitle="";
	
	private String budgetHeadType=null;

	/**
	 * @return
	 */
	public String getBudgeHead() {
		return budgetHead;
	}

	/**
	 * @return
	 */
	public String getBudgetSubHeadTitle() {
		return budgetSubHeadTitle;
	}

	/**
	 * @return
	 */
	public String getBudgetSubHeadType() {
		return budgetSubHeadType;
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
	public void setBudgetSubHeadTitle(String string) {
		budgetSubHeadTitle = string;
	}

	/**
	 * @param string
	 */
	public void setBudgetSubHeadType(String string) {
		budgetSubHeadType = string;
	}

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
	public void setBudgetHeadType(String string) {
		budgetHeadType = string;
	}

	/**
	 * @return
	 */
	public String getModBudgetSubHeadTitle() {
		return modBudgetSubHeadTitle;
	}

	/**
	 * @return
	 */
	public String getNewBudgetSubHeadTitle() {
		return newBudgetSubHeadTitle;
	}

	/**
	 * @param string
	 */
	public void setModBudgetSubHeadTitle(String string) {
		modBudgetSubHeadTitle = string;
	}

	/**
	 * @param string
	 */
	public void setNewBudgetSubHeadTitle(String string) {
		newBudgetSubHeadTitle = string;
	}

}
