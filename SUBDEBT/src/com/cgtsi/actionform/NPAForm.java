package com.cgtsi.actionform;

import org.apache.struts.action.ActionForm;

public class NPAForm extends ActionForm{

	private String npaupgradationDate;
	private String cgpan;
	
	private String strCgpan;
	private String NPAformType;
	private String newNpaDate;
	private String remark;
	public String getStrCgpan() {
		return strCgpan;
	}
	public void setStrCgpan(String strCgpan) {
		this.strCgpan = strCgpan;
	}
	public String getNPAformType() {
		return NPAformType;
	}
	public void setNPAformType(String nPAformType) {
		NPAformType = nPAformType;
	}
	public String getNpaupgradationDate() {
		return npaupgradationDate;
	}
	public void setNpaupgradationDate(String npaupgradationDate) {
		this.npaupgradationDate = npaupgradationDate;
	}
	public String getNewNpaDate() {
		return newNpaDate;
	}
	public void setNewNpaDate(String newNpaDate) {
		this.newNpaDate = newNpaDate;
	}
	public String getRemark() {
		return remark;
	}
	public void setRemark(String remark) {
		this.remark = remark;
	}	
	public String getCgpan() {
		return cgpan;
	}
	public void setCgpan(String cgpan) {
		this.cgpan = cgpan;
	}
	
	
}
