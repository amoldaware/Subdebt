package com.cgtsi.actionform;

import org.apache.struts.validator.ValidatorActionForm;

public class ClaimPaymentData extends ValidatorActionForm{
	/**
 * 
 */
private static final long serialVersionUID = 1L;

private String message;


private String claimType;
private String memberId;
private String isSearchClicked;
private String role;

private String ccgpan;
private String referanceNumber;
private String claimTypee;
private double netPaidAmountt;
private String details;

private String userId;

private String checkerStatus;






public String getCheckerStatus() {
	return checkerStatus;
}
public void setCheckerStatus(String checkerStatus) {
	this.checkerStatus = checkerStatus;
}
public String getUserId() {
	return userId;
}
public void setUserId(String userId) {
	this.userId = userId;
}
public String getMessage() {
	return message;
}
public void setMessage(String message) {
	this.message = message;
}
public String getClaimType() {
	return claimType;
}
public void setClaimType(String claimType) {
	this.claimType = claimType;
}
public String getMemberId() {
	return memberId;
}
public void setMemberId(String memberId) {
	this.memberId = memberId;
}
public String getIsSearchClicked() {
	return isSearchClicked;
}
public void setIsSearchClicked(String isSearchClicked) {
	this.isSearchClicked = isSearchClicked;
}
public String getRole() {
	return role;
}
public void setRole(String role) {
	this.role = role;
}
public String getCcgpan() {
	return ccgpan;
}
public void setCcgpan(String ccgpan) {
	this.ccgpan = ccgpan;
}
public String getReferanceNumber() {
	return referanceNumber;
}
public void setReferanceNumber(String referanceNumber) {
	this.referanceNumber = referanceNumber;
}
public String getClaimTypee() {
	return claimTypee;
}
public void setClaimTypee(String claimTypee) {
	this.claimTypee = claimTypee;
}

public double getNetPaidAmountt() {
	return netPaidAmountt;
}
public void setNetPaidAmountt(double netPaidAmountt) {
	this.netPaidAmountt = netPaidAmountt;
}
public String getDetails() {
	return details;
}
public void setDetails(String details) {
	this.details = details;
}





}
