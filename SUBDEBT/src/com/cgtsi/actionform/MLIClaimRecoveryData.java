package com.cgtsi.actionform;

public class MLIClaimRecoveryData 
{
	private static final long serialVersionUID = 1L;
	private int srNo;
	private String pmr_ref_No;
	private String cgpan;
	private String memberId;
	private String claim_ref_number;
	private String promoterName;
	private String unitName;
	private double previous_Recovery;
	private double eligibl_Amt_First_Inst;
	private String type_of_Recovery;
	private double additional_Recovery;
	private double legal_Expense_Advocate_Fee;
	private double legal_Expense_Court_Fee;
	private String description_of_Other_Recovery;
	private double other_Recovery_Expenses;
	private double total_Legal_Expenses;
	private double recoveryAmounttoberemittedtoCGTMSE;
	private String dateOfrecovery;
	private double totalAmount;
	private int rec_id;
	private String utr_number;
	private String utr_date;
	private String status;
	
	public String getUtr_number() {
		return utr_number;
	}
	public void setUtr_number(String utr_number) {
		this.utr_number = utr_number;
	}
	public String getUtr_date() {
		return utr_date;
	}
	public void setUtr_date(String utr_date) {
		this.utr_date = utr_date;
	}
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	public int getRec_id() {
		return rec_id;
	}
	public void setRec_id(int rec_id) {
		this.rec_id = rec_id;
	}
	public int getSrNo() {
		return srNo;
	}
	public void setSrNo(int srNo) {
		this.srNo = srNo;
	}
	public String getPmr_ref_No() {
		return pmr_ref_No;
	}
	public void setPmr_ref_No(String pmr_ref_No) {
		this.pmr_ref_No = pmr_ref_No;
	}
	public String getCgpan() {
		return cgpan;
	}
	public void setCgpan(String cgpan) {
		this.cgpan = cgpan;
	}
	public String getMemberId() {
		return memberId;
	}
	public void setMemberId(String memberId) {
		this.memberId = memberId;
	}
	public String getClaim_ref_number() {
		return claim_ref_number;
	}
	public void setClaim_ref_number(String claim_ref_number) {
		this.claim_ref_number = claim_ref_number;
	}
	public String getPromoterName() {
		return promoterName;
	}
	public void setPromoterName(String promoterName) {
		this.promoterName = promoterName;
	}
	public String getUnitName() {
		return unitName;
	}
	public void setUnitName(String unitName) {
		this.unitName = unitName;
	}
	public double getPrevious_Recovery() {
		return previous_Recovery;
	}
	public void setPrevious_Recovery(double previous_Recovery) {
		this.previous_Recovery = previous_Recovery;
	}
	public double getEligibl_Amt_First_Inst() {
		return eligibl_Amt_First_Inst;
	}
	public void setEligibl_Amt_First_Inst(double eligibl_Amt_First_Inst) {
		this.eligibl_Amt_First_Inst = eligibl_Amt_First_Inst;
	}
	public String getType_of_Recovery() {
		return type_of_Recovery;
	}
	public void setType_of_Recovery(String type_of_Recovery) {
		this.type_of_Recovery = type_of_Recovery;
	}
	public double getAdditional_Recovery() {
		return additional_Recovery;
	}
	public void setAdditional_Recovery(double additional_Recovery) {
		this.additional_Recovery = additional_Recovery;
	}
	public double getLegal_Expense_Advocate_Fee() {
		return legal_Expense_Advocate_Fee;
	}
	public void setLegal_Expense_Advocate_Fee(double legal_Expense_Advocate_Fee) {
		this.legal_Expense_Advocate_Fee = legal_Expense_Advocate_Fee;
	}
	public double getLegal_Expense_Court_Fee() {
		return legal_Expense_Court_Fee;
	}
	public void setLegal_Expense_Court_Fee(double legal_Expense_Court_Fee) {
		this.legal_Expense_Court_Fee = legal_Expense_Court_Fee;
	}
	public String getDescription_of_Other_Recovery() {
		return description_of_Other_Recovery;
	}
	public void setDescription_of_Other_Recovery(String description_of_Other_Recovery) {
		this.description_of_Other_Recovery = description_of_Other_Recovery;
	}
	public double getOther_Recovery_Expenses() {
		return other_Recovery_Expenses;
	}
	public void setOther_Recovery_Expenses(double other_Recovery_Expenses) {
		this.other_Recovery_Expenses = other_Recovery_Expenses;
	}
	public double getTotal_Legal_Expenses() {
		return total_Legal_Expenses;
	}
	public void setTotal_Legal_Expenses(double total_Legal_Expenses) {
		this.total_Legal_Expenses = total_Legal_Expenses;
	}
	public double getRecoveryAmounttoberemittedtoCGTMSE() {
		return recoveryAmounttoberemittedtoCGTMSE;
	}
	public void setRecoveryAmounttoberemittedtoCGTMSE(double recoveryAmounttoberemittedtoCGTMSE) {
		this.recoveryAmounttoberemittedtoCGTMSE = recoveryAmounttoberemittedtoCGTMSE;
	}
	public String getDateOfrecovery() {
		return dateOfrecovery;
	}
	public void setDateOfrecovery(String dateOfrecovery) {
		this.dateOfrecovery = dateOfrecovery;
	}
	public double getTotalAmount() {
		return totalAmount;
	}
	public void setTotalAmount(double totalAmount) {
		this.totalAmount = totalAmount;
	}
}
