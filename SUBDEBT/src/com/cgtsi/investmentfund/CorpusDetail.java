/*
 * Created on Jun 17, 2004
 *
 * To change the template for this generated file go to
 * Window&gt;Preferences&gt;Java&gt;Code Generation&gt;Code and Comments
 */
package com.cgtsi.investmentfund;

import java.util.Date;

/**
 * @author NP13031
 *
 * To change the template for this generated type comment go to
 * Window&gt;Preferences&gt;Java&gt;Code Generation&gt;Code and Comments
 */
public class CorpusDetail {
	
	private String corpusId="";
	private String corpusContributor="";
	private double corpusAmount;
	private Date corpusDate;

	/**
	 * @return
	 */
	public double getCorpusAmount() {
		return corpusAmount;
	}

	/**
	 * @return
	 */
	public String getCorpusContributor() {
		return corpusContributor;
	}

	/**
	 * @return
	 */
	public Date getCorpusDate() {
		return corpusDate;
	}

	/**
	 * @return
	 */
	public String getCorpusId() {
		return corpusId;
	}

	/**
	 * @param d
	 */
	public void setCorpusAmount(double d) {
		corpusAmount = d;
	}

	/**
	 * @param string
	 */
	public void setCorpusContributor(String string) {
		corpusContributor = string;
	}

	/**
	 * @param date
	 */
	public void setCorpusDate(Date date) {
		corpusDate = date;
	}

	/**
	 * @param string
	 */
	public void setCorpusId(String string) {
		corpusId = string;
	}

}
