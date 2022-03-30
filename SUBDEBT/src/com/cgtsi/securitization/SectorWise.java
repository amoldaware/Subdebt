/*************************************************************
   *
   * Name of the class: SectorWise.java
   * This class holds the properties for a sector related loan pool.
   * 
   *
   * @author : Veldurai T
   * @version:  
   * @since: Dec 4, 2003
   **************************************************************/
package com.cgtsi.securitization;

import java.io.Serializable;

/**
 * @author Veldurai T
 *
 * This class holds the properties for a sector related loan pool.
 * 
 */
public class SectorWise implements Serializable
{

	private String state;
	private int noOfLoans;
	private double totalSanctionedAmt;
	private double totalGC;	
	/**
	 * @return
	 */
	public int getNoOfLoans() {
		return noOfLoans;
	}

	/**
	 * @return
	 */
	public String getState() {
		return state;
	}

	/**
	 * @return
	 */
	public double getTotalGC() {
		return totalGC;
	}

	/**
	 * @return
	 */
	public double getTotalSanctionedAmt() {
		return totalSanctionedAmt;
	}

	/**
	 * @param i
	 */
	public void setNoOfLoans(int i) {
		noOfLoans = i;
	}

	/**
	 * @param string
	 */
	public void setState(String string) {
		state = string;
	}

	/**
	 * @param d
	 */
	public void setTotalGC(double d) {
		totalGC = d;
	}

	/**
	 * @param d
	 */
	public void setTotalSanctionedAmt(double d) {
		totalSanctionedAmt = d;
	}

}
