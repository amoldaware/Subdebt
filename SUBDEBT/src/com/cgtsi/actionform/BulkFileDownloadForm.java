package com.cgtsi.actionform;

import java.util.ArrayList;

import org.apache.struts.validator.ValidatorActionForm;

public class BulkFileDownloadForm extends ValidatorActionForm{

	private String bu_id;
	private String member_id;
	private String upload_id;
	private String process_name;
	private String uploaded_by;
	private String uploaded_date;
	private String status;
	private String download;
	private String MEM_BANK_NAME;
	private ArrayList bulkFileList;
	
	private Integer FAILURE_COUNT;
	private Integer SUCCESS_COUNT;
	private Integer TOTAL_RECORED_IN_FILE;
	
	
	
	
	
	public Integer getFAILURE_COUNT() {
		return FAILURE_COUNT;
	}
	public void setFAILURE_COUNT(Integer fAILURE_COUNT) {
		FAILURE_COUNT = fAILURE_COUNT;
	}
	public Integer getSUCCESS_COUNT() {
		return SUCCESS_COUNT;
	}
	public void setSUCCESS_COUNT(Integer sUCCESS_COUNT) {
		SUCCESS_COUNT = sUCCESS_COUNT;
	}
	public Integer getTOTAL_RECORED_IN_FILE() {
		return TOTAL_RECORED_IN_FILE;
	}
	public void setTOTAL_RECORED_IN_FILE(Integer tOTAL_RECORED_IN_FILE) {
		TOTAL_RECORED_IN_FILE = tOTAL_RECORED_IN_FILE;
	}
	public String getMEM_BANK_NAME() {
		return MEM_BANK_NAME;
	}
	public void setMEM_BANK_NAME(String mEM_BANK_NAME) {
		MEM_BANK_NAME = mEM_BANK_NAME;
	}
	public String getBu_id() {
		return bu_id;
	}
	public void setBu_id(String bu_id) {
		this.bu_id = bu_id;
	}
	public String getMember_id() {
		return member_id;
	}
	public void setMember_id(String member_id) {
		this.member_id = member_id;
	}
	public String getUpload_id() {
		return upload_id;
	}
	public void setUpload_id(String upload_id) {
		this.upload_id = upload_id;
	}
	public String getProcess_name() {
		return process_name;
	}
	public void setProcess_name(String process_name) {
		this.process_name = process_name;
	}
	public String getUploaded_by() {
		return uploaded_by;
	}
	public void setUploaded_by(String uploaded_by) {
		this.uploaded_by = uploaded_by;
	}
	public String getUploaded_date() {
		return uploaded_date;
	}
	public void setUploaded_date(String uploaded_date) {
		this.uploaded_date = uploaded_date;
	}
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	public String getDownload() {
		return download;
	}
	public void setDownload(String download) {
		this.download = download;
	}
	public ArrayList getBulkFileList() {
		return bulkFileList;
	}
	public void setBulkFileList(ArrayList bulkFileList) {
		this.bulkFileList = bulkFileList;
	}

	
	
	
}
