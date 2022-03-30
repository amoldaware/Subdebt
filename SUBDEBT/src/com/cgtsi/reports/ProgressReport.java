 
package com.cgtsi.reports; 

import java.util.Date;

public class ProgressReport
{
	private int proposalsRecievedDuring;  
	private int totalProposalsRecieved;
	private int proposalsApprovedDuring; 
	private int totalProposalsApproved;
	private double sanctionedAmountDuring; 
	private double totalSanctionedAmount;
	private int proposalsCancelled;
	private int proposalsPending;
	private int numberOfProposals;
	private double guaranteeIssued;
	private String bankName;
	private String memberId;
	private String appRefNo;
  private String applicationRefNo;
	private String cgPan; //added by sukumar
  private Date submittedDate;
   private Date approvedDate;
  private String ssiName;
  //added branchName,expirydate and status by sukumar@path 
	private String branchName;
  private Date expiryDate;
  private String status;
  //added by sukumar@path for capturing the guarantee start date time
  private Date guarStartDate;
  private String LoanAcntNumber;
	
	public String getLoanAcntNumber() {
	return LoanAcntNumber;
}

public void setLoanAcntNumber(String loanAcntNumber) {
	LoanAcntNumber = loanAcntNumber;
}

	public ProgressReport()
	{
	}
  
  public Date getGuarStartDate() {
		return guarStartDate;
	}

	/**
	 * @param date
	 */
	public void setGuarStartDate(Date date) {
		guarStartDate = date;
	}
	//setter and getter method for ssiName, cgPan and submittedDate  added by sukumar
  public String getStatus(){
    return status;
  }
  public void setStatus(String st){
  status=st;
  }
  	/**
	 * @return
	 */
	public Date getExpiryDate() {
		return expiryDate;
	}

	/**
	 * @param date
	 */
	public void setExpiryDate(Date date) {
		expiryDate = date;
	}
  	/**
	 * @return
	 */
	public Date getApprovedDate() {
		return approvedDate;
	}

	/**
	 * @param date
	 */
	public void setApprovedDate(Date date) {
		approvedDate = date;
	}
  /**
	 * @return
	 */
	public String getSsiName() {
		return ssiName;
	}

	/**
	 * @param string
	 */
	public void setSsiName(String string) {
		ssiName = string;
	}
  
  
  
   /**
	 * @return
	 */
	public String getBranchName() {
		return branchName;
	}

	/**
	 * @param string
	 */
	public void setBranchName(String string) {
		branchName = string;
	}
  	/**
	 * @return
	 */
	public Date getSubmittedDate() {
		return submittedDate;
	}

	/**
	 * @param date
	 */
	public void setSubmittedDate(Date date) {
		submittedDate = date;
	}
	/**
	 * @return
	 */
	public String getCgPan() {
		return cgPan;
	}

	/**
	 * @param string
	 */
	public void setCgPan(String string) {
		cgPan = string;
	}
	
	/**
	 * @return
	 */
	public int getProposalsRecievedDuring() {
		return proposalsRecievedDuring;
	}

	/**
	 * @param string
	 */
	public void setProposalsRecievedDuring(int aProposalsRecievedDuring) {
		proposalsRecievedDuring = aProposalsRecievedDuring;
	}

	/**
	 * @return
	 */
	public int getTotalProposalsRecieved() {
		return totalProposalsRecieved;
	}

	/**
	 * @param string
	 */
	public void setTotalProposalsRecieved(int aTotalProposalsRecieved) {
		totalProposalsRecieved = aTotalProposalsRecieved;
	}

	/**
	 * @return
	 */
	public int getProposalsApprovedDuring() {
		return proposalsApprovedDuring;
	}

	/**
	 * @param string
	 */
	public void setProposalsApprovedDuring(int aProposalsApprovedDuring) {
		proposalsApprovedDuring = aProposalsApprovedDuring;
	}

	/**
	 * @return
	 */
	public int getTotalProposalsApproved() {
		return totalProposalsApproved;
	}

	/**
	 * @param string
	 */
	public void setTotalProposalsApproved(int aTotalProposalsApproved) {
		totalProposalsApproved = aTotalProposalsApproved;
	}

	/**
	 * @return
	 */
	public double getSanctionedAmountDuring() {
		return sanctionedAmountDuring;
	}

	/**
	 * @param string
	 */
	public void setSanctionedAmountDuring(double aSanctionedAmountDuring) {
		sanctionedAmountDuring = aSanctionedAmountDuring;
	}

	/**
	 * @return
	 */
	public double getTotalSanctionedAmount() {
		return totalSanctionedAmount;
	}

	/**
	 * @param string
	 */
	public void setTotalSanctionedAmount(double aTotalSanctionedAmount) {
		totalSanctionedAmount = aTotalSanctionedAmount;
	}

	/**
	 * @return
	 */
	public int getProposalsCancelled() {
		return proposalsCancelled;
	}

	/**
	 * @param string
	 */
	public void setProposalsCancelled(int aProposalsCancelled) {
		proposalsCancelled = aProposalsCancelled;
	}

	/**
	 * @return
	 */
	public int getProposalsPending() {
		return proposalsPending;
	}

	/**
	 * @param string
	 */
	public void setProposalsPending(int aProposalsPending) {
		proposalsPending = aProposalsPending;
	}

	/**
	 * @return
	 */
	public int getNumberOfProposals() {
		return numberOfProposals;
	}

	/**
	 * @param int 
	 */
	public void setNumberOfProposals(int aNumberOfProposals) {
		numberOfProposals = aNumberOfProposals;
	}

	/**
	 * @return
	 */
	public double getGuaranteeIssued() {
		return guaranteeIssued;
	}

	/**
	 * @param string
	 */
	public void setGuaranteeIssued(double aGuaranteeIssued) {
		guaranteeIssued = aGuaranteeIssued;
	}

	/**
	 * @return
	 */
	public String getBankName() {
		return bankName;
	}

	/**
	 * @param string
	 */
	public void setBankName(String aBankName) {
		bankName = aBankName;
	}

	

	/**
	 * @return
	 */
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
   * 
   * @return 
   */
public String getApplicationRefNo()
{
  return applicationRefNo;
}
/**
   * 
   * @param applicationRefNo1
   */
public void setApplicationRefNo(String applicationRefNo1){
  applicationRefNo = applicationRefNo1;
}
	/**
	 * @return
	 */
	public String getAppRefNo() {
		return appRefNo;
	}

	/**
	 * @param string
	 */
	public void setAppRefNo(String string) {
		appRefNo = string;
	}

}




