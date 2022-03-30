package com.cgtsi.actionform;

import java.util.ArrayList;
import java.util.Date;

import javax.servlet.http.HttpServletRequest;

import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.validator.ValidatorActionForm;

public class RecoveryActionForm extends ValidatorActionForm  {

	private String claimRefNo;
	private String cgpan;
	private String unitName;
	private String firstInstallmentAmount;
	private String previouseRecoveredAmount;
	private String typeOfRecovery;	
	private String recoveredAmout;
	private String legalExpenses;
	private String amoutRemitted;
	private String decision;
	private String hiddenFieldForClaimNotSettled;
	
	//added by dkr
	private Date mliRecoveryDateRecv;
	private double penalBankIntRate=0.0d;
	private double totalAmt2Paid=0.0d;	
	private double totalAmountToDisplay=0.0d;
	private double totalPenalBankIntRateDisplay=0.0d;
	private double totalAmt2PaidDisplay=0.0d;


	public Date getMliRecoveryDateRecv() {
		return mliRecoveryDateRecv;
	}

	public void setMliRecoveryDateRecv(Date mliRecoveryDateRecv) {
		this.mliRecoveryDateRecv = mliRecoveryDateRecv;
	}

	public double getPenalBankIntRate() {
		return penalBankIntRate;
	}

	public void setPenalBankIntRate(double penalBankIntRate) {
		this.penalBankIntRate = penalBankIntRate;
	}

	public double getTotalAmt2Paid() {
		return totalAmt2Paid;
	}

	public void setTotalAmt2Paid(double totalAmt2Paid) {
		this.totalAmt2Paid = totalAmt2Paid;
	}

	public double getTotalAmountToDisplay() {
		return totalAmountToDisplay;
	}

	public void setTotalAmountToDisplay(double totalAmountToDisplay) {
		this.totalAmountToDisplay = totalAmountToDisplay;
	}

	public double getTotalPenalBankIntRateDisplay() {
		return totalPenalBankIntRateDisplay;
	}

	public void setTotalPenalBankIntRateDisplay(double totalPenalBankIntRateDisplay) {
		this.totalPenalBankIntRateDisplay = totalPenalBankIntRateDisplay;
	}

	public double getTotalAmt2PaidDisplay() {
		return totalAmt2PaidDisplay;
	}

	public void setTotalAmt2PaidDisplay(double totalAmt2PaidDisplay) {
		this.totalAmt2PaidDisplay = totalAmt2PaidDisplay;
	}

	public String getHiddenFieldForClaimNotSettled() {
		return hiddenFieldForClaimNotSettled;
	}

	public void setHiddenFieldForClaimNotSettled(
			String hiddenFieldForClaimNotSettled) {
		this.hiddenFieldForClaimNotSettled = hiddenFieldForClaimNotSettled;
	}

	public String getDecision() {
		return decision;
	}

	public void setDecision(String decision) {
		this.decision = decision;
	}

	public String getClaimRefNo() {
		return claimRefNo;
	}

	public void setClaimRefNo(String claimRefNo) {
		this.claimRefNo = claimRefNo;
	}

	public String getCgpan() {
		return cgpan;
	}

	public void setCgpan(String cgpan) {
		this.cgpan = cgpan;
	}

	public String getUnitName() {
		return unitName;
	}

	public void setUnitName(String unitName) {
		this.unitName = unitName;
	}

	public String getFirstInstallmentAmount() {
		return firstInstallmentAmount;
	}

	public void setFirstInstallmentAmount(String firstInstallmentAmount) {
		this.firstInstallmentAmount = firstInstallmentAmount;
	}

	public String getPreviouseRecoveredAmount() {
		return previouseRecoveredAmount;
	}

	public void setPreviouseRecoveredAmount(String previouseRecoveredAmount) {
		this.previouseRecoveredAmount = previouseRecoveredAmount;
	}

	public String getTypeOfRecovery() {
		return typeOfRecovery;
	}

	public void setTypeOfRecovery(String typeOfRecovery) {
		this.typeOfRecovery = typeOfRecovery;
	}

	

	public String getRecoveredAmout() {
		return recoveredAmout;
	}

	public void setRecoveredAmout(String recoveredAmout) {
		this.recoveredAmout = recoveredAmout;
	}

	public String getLegalExpenses() {
		return legalExpenses;
	}

	public void setLegalExpenses(String legalExpenses) {
		this.legalExpenses = legalExpenses;
	}

	public String getAmoutRemitted() {
		return amoutRemitted;
	}

	public void setAmoutRemitted(String amoutRemitted) {
		this.amoutRemitted = amoutRemitted;
	}

	@Override
	
	
	 public ActionErrors validate(ActionMapping mapping, HttpServletRequest request)
	  {
		 System.out.println("validate executed");
	    ActionErrors errors = super.validate(mapping, request);
	    return errors;
	  }

	@Override
	public String toString() {
		return "RecoveryActionForm [claimRefNo=" + claimRefNo + ", cgpan="
				+ cgpan + ", unitName=" + unitName
				+ ", firstInstallmentAmount=" + firstInstallmentAmount
				+ ", previouseRecoveredAmount=" + previouseRecoveredAmount
				+ ", typeOfRecovery=" + typeOfRecovery + ", recoveredAmout="
				+ recoveredAmout + ", legalExpenses=" + legalExpenses
				+ ", amoutRemitted=" + amoutRemitted + ", decision=" + decision
				+ ", hiddenFieldForClaimNotSettled="
				+ hiddenFieldForClaimNotSettled + ", mliRecoveryDateRecv="
				+ mliRecoveryDateRecv + ", penalBankIntRate="
				+ penalBankIntRate + ", totalAmt2Paid=" + totalAmt2Paid
				+ ", totalAmountToDisplay=" + totalAmountToDisplay
				+ ", totalPenalBankIntRateDisplay="
				+ totalPenalBankIntRateDisplay + ", totalAmt2PaidDisplay="
				+ totalAmt2PaidDisplay + "]";
	}
}
