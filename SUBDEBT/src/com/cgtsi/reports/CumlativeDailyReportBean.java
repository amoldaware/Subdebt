package com.cgtsi.reports;

import java.io.Serializable;

public class CumlativeDailyReportBean implements Serializable{
	   private String hoReportBankName="";
       private double hoReportEligBorrower=0.0d;
       private double hoReportTotalOs=0.0d;
       private double hoReport20EligOs=0.0d;
       private double hoReportNoBorrowerOffer=0.0d;
       private double hoReportNoBorrowerOpt=0.0d;
       private double hoReportSanctionNo=0.0d;
       private double hoReportSanctionAmt=0.0d;
       private double hoReportDisburseNo=0.0d;
       private double hoReportDisburseAmt=0.0d;
       
       
	public String getHoReportBankName() {
		return hoReportBankName;
	}
	public void setHoReportBankName(String hoReportBankName) {
		this.hoReportBankName = hoReportBankName;
	}
	public double getHoReportEligBorrower() {
		return hoReportEligBorrower;
	}
	public void setHoReportEligBorrower(double hoReportEligBorrower) {
		this.hoReportEligBorrower = hoReportEligBorrower;
	}
	public double getHoReportTotalOs() {
		return hoReportTotalOs;
	}
	public void setHoReportTotalOs(double hoReportTotalOs) {
		this.hoReportTotalOs = hoReportTotalOs;
	}
	public double getHoReport20EligOs() {
		return hoReport20EligOs;
	}
	public void setHoReport20EligOs(double hoReport20EligOs) {
		this.hoReport20EligOs = hoReport20EligOs;
	}
	public double getHoReportNoBorrowerOffer() {
		return hoReportNoBorrowerOffer;
	}
	public void setHoReportNoBorrowerOffer(double hoReportNoBorrowerOffer) {
		this.hoReportNoBorrowerOffer = hoReportNoBorrowerOffer;
	}
	public double getHoReportNoBorrowerOpt() {
		return hoReportNoBorrowerOpt;
	}
	public void setHoReportNoBorrowerOpt(double hoReportNoBorrowerOpt) {
		this.hoReportNoBorrowerOpt = hoReportNoBorrowerOpt;
	}
	public double getHoReportSanctionNo() {
		return hoReportSanctionNo;
	}
	public void setHoReportSanctionNo(double hoReportSanctionNo) {
		this.hoReportSanctionNo = hoReportSanctionNo;
	}
	public double getHoReportSanctionAmt() {
		return hoReportSanctionAmt;
	}
	public void setHoReportSanctionAmt(double hoReportSanctionAmt) {
		this.hoReportSanctionAmt = hoReportSanctionAmt;
	}
	public double getHoReportDisburseNo() {
		return hoReportDisburseNo;
	}
	public void setHoReportDisburseNo(double hoReportDisburseNo) {
		this.hoReportDisburseNo = hoReportDisburseNo;
	}
	public double getHoReportDisburseAmt() {
		return hoReportDisburseAmt;
	}
	public void setHoReportDisburseAmt(double hoReportDisburseAmt) {
		this.hoReportDisburseAmt = hoReportDisburseAmt;
	}
	
	@Override
	public String toString() {
		return "CumlativeDailyReportBean [hoReportBankName=" + hoReportBankName
				+ ", hoReportEligBorrower=" + hoReportEligBorrower
				+ ", hoReportTotalOs=" + hoReportTotalOs
				+ ", hoReport20EligOs=" + hoReport20EligOs
				+ ", hoReportNoBorrowerOffer=" + hoReportNoBorrowerOffer
				+ ", hoReportNoBorrowerOpt=" + hoReportNoBorrowerOpt
				+ ", hoReportSanctionNo=" + hoReportSanctionNo
				+ ", hoReportSanctionAmt=" + hoReportSanctionAmt
				+ ", hoReportDisburseNo=" + hoReportDisburseNo
				+ ", hoReportDisburseAmt=" + hoReportDisburseAmt + "]";
	}
       
}
