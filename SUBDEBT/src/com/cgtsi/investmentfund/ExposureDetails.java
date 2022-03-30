/*
 * Created on Jan 20, 2004
 *
 * To change the template for this generated file go to
 * Window&gt;Preferences&gt;Java&gt;Code Generation&gt;Code and Comments
 */
package com.cgtsi.investmentfund;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Hashtable;

/**
 * @author VT8150
 *
 * To change the template for this generated type comment go to
 * Window&gt;Preferences&gt;Java&gt;Code Generation&gt;Code and Comments
 */
public class ExposureDetails implements Serializable
{
	private String investeeGroup;

	private String investeeName;
	private double tangibleAssets;
	private double networth;
	private double investeeTangibleAssets;
	private double investeeNetWorth;
	private ArrayList exposureDetails;
	private double totalAmntForCalculateExposure;
	private Hashtable instEligibleAmts;
	private Hashtable matEligibleAmts;
	private Hashtable instInvestedAmts;
	private Hashtable matInvestedAmts;
	private double investeeEligibleAmt;
	private double invGroupEligibleAmt;
	private double invInvestedAmt;
	private double invGrpInvestedAmt;
	private double invCeilingAmt;
	private double ceilingLimit;
	
	private double gapAvailableAmount;
	
	private double liveInvtAmount;
	private double investedAmount;
	private double maturedAmount;
	private double exposureCorpusAmount;
	private double otherReceiptsAmount;
	private double expenditureAmount;
	private double totalSurplusAmount;
	private String maturityName;
	
	private String instCatName;


	/**
	 * @return
	 */
	public String getInvesteeName() {
		return investeeName;
	}

	/**
	 * @return
	 */
	public double getInvesteeNetWorth() {
		return investeeNetWorth;
	}

	/**
	 * @return
	 */
	public double getInvesteeTangibleAssets() {
		return investeeTangibleAssets;
	}

	/**
	 * @return
	 */
	public double getNetworth() {
		return networth;
	}

	/**
	 * @return
	 */
	public double getTangibleAssets() {
		return tangibleAssets;
	}

	/**
	 * @param string
	 */
	public void setInvesteeName(String string) {
		investeeName = string;
	}

	/**
	 * @param d
	 */
	public void setInvesteeNetWorth(double d) {
		investeeNetWorth = d;
	}

	/**
	 * @param d
	 */
	public void setInvesteeTangibleAssets(double d) {
		investeeTangibleAssets = d;
	}

	/**
	 * @param d
	 */
	public void setNetworth(double d) {
		networth = d;
	}

	/**
	 * @param d
	 */
	public void setTangibleAssets(double d) {
		tangibleAssets = d;
	}

	/**
	 * @return
	 */
	public String getInvesteeGroup() {
		return investeeGroup;
	}

	/**
	 * @param string
	 */
	public void setInvesteeGroup(String string) {
		investeeGroup = string;
	}

	/**
	 * @return
	 */
	public ArrayList getExposureDetails() {
		return exposureDetails;
	}

	/**
	 * @param list
	 */
	public void setExposureDetails(ArrayList list) {
		exposureDetails = list;
	}

	public double getTotalAmntForCalculateExposure()
	{
		return this.totalAmntForCalculateExposure;
	}

    public void setTotalAmntForCalculateExposure(double amnt)
    {
        this.totalAmntForCalculateExposure = amnt;
	}
	/**
	 * @return
	 */
	public Hashtable getInstEligibleAmts() {
		return instEligibleAmts;
	}

	/**
	 * @return
	 */
	public Hashtable getMatEligibleAmts() {
		return matEligibleAmts;
	}

	/**
	 * @param hashtable
	 */
	public void setInstEligibleAmts(Hashtable hashtable) {
		instEligibleAmts = hashtable;
	}

	/**
	 * @param hashtable
	 */
	public void setMatEligibleAmts(Hashtable hashtable) {
		matEligibleAmts = hashtable;
	}

	/**
	 * @return
	 */
	public Hashtable getInstInvestedAmts() {
		return instInvestedAmts;
	}

	/**
	 * @return
	 */
	public double getInvesteeEligibleAmt() {
		return investeeEligibleAmt;
	}

	/**
	 * @return
	 */
	public double getInvGroupEligibleAmt() {
		return invGroupEligibleAmt;
	}

	/**
	 * @return
	 */
	public Hashtable getMatInvestedAmts() {
		return matInvestedAmts;
	}

	/**
	 * @param hashtable
	 */
	public void setInstInvestedAmts(Hashtable hashtable) {
		instInvestedAmts = hashtable;
	}

	/**
	 * @param d
	 */
	public void setInvesteeEligibleAmt(double d) {
		investeeEligibleAmt = d;
	}

	/**
	 * @param d
	 */
	public void setInvGroupEligibleAmt(double d) {
		invGroupEligibleAmt = d;
	}

	/**
	 * @param hashtable
	 */
	public void setMatInvestedAmts(Hashtable hashtable) {
		matInvestedAmts = hashtable;
	}

	/**
	 * @return
	 */
	public double getInvGrpInvestedAmt() {
		return invGrpInvestedAmt;
	}

	/**
	 * @return
	 */
	public double getInvInvestedAmt() {
		return invInvestedAmt;
	}

	/**
	 * @param d
	 */
	public void setInvGrpInvestedAmt(double d) {
		invGrpInvestedAmt = d;
	}

	/**
	 * @param d
	 */
	public void setInvInvestedAmt(double d) {
		invInvestedAmt = d;
	}

	/**
	 * @return
	 */
	public double getInvCeilingAmt() {
		return invCeilingAmt;
	}

	/**
	 * @param d
	 */
	public void setInvCeilingAmt(double d) {
		invCeilingAmt = d;
	}

	/**
	 * @return
	 */
	public double getInvestedAmount() {
		return investedAmount;
	}

	/**
	 * @return
	 */
	public double getLiveInvtAmount() {
		return liveInvtAmount;
	}

	/**
	 * @return
	 */
	public double getMaturedAmount() {
		return maturedAmount;
	}

	/**
	 * @param d
	 */
	public void setInvestedAmount(double d) {
		investedAmount = d;
	}

	/**
	 * @param d
	 */
	public void setLiveInvtAmount(double d) {
		liveInvtAmount = d;
	}

	/**
	 * @param d
	 */
	public void setMaturedAmount(double d) {
		maturedAmount = d;
	}

	/**
	 * @return
	 */
	public double getExpenditureAmount() {
		return expenditureAmount;
	}

	/**
	 * @return
	 */
	public double getOtherReceiptsAmount() {
		return otherReceiptsAmount;
	}

	/**
	 * @return
	 */
	public double getTotalSurplusAmount() {
		return totalSurplusAmount;
	}


	/**
	 * @param d
	 */
	public void setExpenditureAmount(double d) {
		expenditureAmount = d;
	}

	/**
	 * @param d
	 */
	public void setOtherReceiptsAmount(double d) {
		otherReceiptsAmount = d;
	}

	/**
	 * @param d
	 */
	public void setTotalSurplusAmount(double d) {
		totalSurplusAmount = d;
	}

	/**
	 * @return
	 */
	public double getExposureCorpusAmount() {
		return exposureCorpusAmount;
	}

	/**
	 * @param d
	 */
	public void setExposureCorpusAmount(double d) {
		exposureCorpusAmount = d;
	}

	/**
	 * @return
	 */
	public double getCeilingLimit() {
		return ceilingLimit;
	}

	/**
	 * @param d
	 */
	public void setCeilingLimit(double d) {
		ceilingLimit = d;
	}

	/**
	 * @return
	 */
	public double getGapAvailableAmount() {
		return gapAvailableAmount;
	}

	/**
	 * @param d
	 */
	public void setGapAvailableAmount(double d) {
		gapAvailableAmount = d;
	}

	/**
	 * @return
	 */
	public String getMaturityName() {
		return maturityName;
	}

	/**
	 * @param string
	 */
	public void setMaturityName(String string) {
		maturityName = string;
	}

	/**
	 * @return
	 */
	public String getInstCatName() {
		return instCatName;
	}

	/**
	 * @param string
	 */
	public void setInstCatName(String string) {
		instCatName = string;
	}

}
