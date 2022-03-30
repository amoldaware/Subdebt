package com.cgtsi.reports;

public class SanctionedReport
{
	private String mliId;
	private String dan;
	private String flag;
	private int applications;
	private long sanctionedAmount;
	private double guaranteeFee;
	private double guaranteeFeePaid;
	private double outstanding;
	
	

	public SanctionedReport()
	{
		
	}


	/**
	 * @return
	 */
	public String getMliId() {
		return mliId;
	}

	/**
	 * @param string
	 */
	public void setMliId(String aMliId) {
		mliId = aMliId;
	}
	
	

	/**
	 * @return
	 */
	public int getApplications() {
		return applications;
	}

	/**
	 * @param string
	 */
	public void setApplications(int aApplications) {
		applications = aApplications;
	}

	/**
	 * @return
	 */
	
	
	public long getSanctionedAmount() {
		return sanctionedAmount;
	}

	/**
	 * @param d
	 */
	public void setSanctionedAmount(long lSanctionedAmount) {
		sanctionedAmount = lSanctionedAmount;
	}
	
	
	
	/**
	 * @return
	 */
	public double getGuaranteeFee() {
		return guaranteeFee;
	}

	/**
	 * @param d
	 */
	public void setGuaranteeFee(double dGuaranteeFee) {
		guaranteeFee = dGuaranteeFee;
	}



	/**
	 * @return
	 */
	public double getGuaranteeFeePaid() {
		return guaranteeFeePaid;
	}

	/**
	 * @param d
	 */
	public void setGuaranteeFeePaid(double dGuaranteeFeePaid) {
		guaranteeFeePaid = dGuaranteeFeePaid;
	}

	/**
	 * @return
	 */
	public double getOutstanding() {
		return outstanding;
	}

	/**
	 * @param d
	 */
	public void setOutstanding(double dOutstanding) {
		outstanding = dOutstanding;
	}

	/**
	 * @return
	 */
	public String getDan() {
		return dan;
	}

	/**
	 * @param string
	 */
	public void setDan(String string) {
		dan = string;
	}

	/**
	 * @return
	 */
	public String getFlag() {
		return flag;
	}

	/**
	 * @param string
	 */
	public void setFlag(String string) {
		flag = string;
	}

}
