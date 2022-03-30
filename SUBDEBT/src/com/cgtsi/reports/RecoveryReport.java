/*
 * Class Name  : RecoveryReport.java
 * Created By  : Deepak Kr. Ranjan
 * Descp       : Recovery Report
 * Created Date:13-June-2018
 * 
 */

package com.cgtsi.reports;
                               
public class RecoveryReport {		
	private	String mliId="";						
	private	String zoneName="";			
	private	String unitName="";				
	private	String cgpanNumber="";					
	private	double guaranteedAmt=0.0d;					
	private	double firstInstalAmt=0.0d;						
	private	java.util.Date dateRecovRecvMli;						
	private	double penalIntrestLiv=0.0d;						
	private	double recoRecvAmt=0.0d;					
	private	String recoveryType="";					
	private	String ddUtrNo="";						
	private	java.util.Date ddDate;	
	
	public String getMliId() {
		return mliId;
	}
	public void setMliId(String mliId) {
		this.mliId = mliId;
	}
	public String getZoneName() {
		return zoneName;
	}
	public void setZoneName(String zoneName) {
		this.zoneName = zoneName;
	}
	public String getUnitName() {
		return unitName;
	}
	public void setUnitName(String unitName) {
		this.unitName = unitName;
	}
	public String getCgpanNumber() {
		return cgpanNumber;
	}
	public void setCgpanNumber(String cgpanNumber) {
		this.cgpanNumber = cgpanNumber;
	}
	public double getGuaranteedAmt() {
		return guaranteedAmt;
	}
	public void setGuaranteedAmt(double guaranteedAmt) {
		this.guaranteedAmt = guaranteedAmt;
	}
	public double getFirstInstalAmt() {
		return firstInstalAmt;
	}
	public void setFirstInstalAmt(double firstInstalAmt) {
		this.firstInstalAmt = firstInstalAmt;
	}
	public java.util.Date getDateRecovRecvMli() {
		return dateRecovRecvMli;
	}
	public void setDateRecovRecvMli(java.util.Date dateRecovRecvMli) {
		this.dateRecovRecvMli = dateRecovRecvMli;
	}
	public double getPenalIntrestLiv() {
		return penalIntrestLiv;
	}
	public void setPenalIntrestLiv(double penalIntrestLiv) {
		this.penalIntrestLiv = penalIntrestLiv;
	}
	public double getRecoRecvAmt() {
		return recoRecvAmt;
	}
	public void setRecoRecvAmt(double recoRecvAmt) {
		this.recoRecvAmt = recoRecvAmt;
	}
	public String getRecoveryType() {
		return recoveryType;
	}
	public void setRecoveryType(String recoveryType) {
		this.recoveryType = recoveryType;
	}
	public String getDdUtrNo() {
		return ddUtrNo;
	}
	public void setDdUtrNo(String ddUtrNo) {
		this.ddUtrNo = ddUtrNo;
	}
	public java.util.Date getDdDate() {
		return ddDate;
	}
	public void setDdDate(java.util.Date ddDate) {
		this.ddDate = ddDate;
	}
	@Override
	public String toString() {
		return "RecoveryReport [mliId=" + mliId + ", zoneName=" + zoneName + ", unitName=" + unitName
				+ ", cgpanNumber=" + cgpanNumber + ", guaranteedAmt=" + guaranteedAmt + ", firstInstalAmt="
				+ firstInstalAmt + ", dateRecovRecvMli=" + dateRecovRecvMli + ", penalIntrestLiv=" + penalIntrestLiv
				+ ", recoRecvAmt=" + recoRecvAmt + ", recoveryType=" + recoveryType + ", ddUtrNo=" + ddUtrNo
				+ ", ddDate=" + ddDate + "]";
	}	
	
	
}
