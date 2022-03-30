package com.cgtsi.actionform;

import java.util.ArrayList;
import java.util.Date;

import org.apache.struts.validator.ValidatorActionForm;

import com.cgtsi.registration.MLIInfo;

public class AppropriationActionForm extends ValidatorActionForm {

		String bankId;
		String zoneId;
	    String branchId;
	    private String mliName;
	    private String mliNames;
	    private String paymentId;	    
	    private String paymentDate;
	    public ArrayList getPopupData() {
			return popupData;
		}


		public void setPopupData(ArrayList popupData) {
			this.popupData = popupData;
		}


		public String getCgpan() {
			return cgpan;
		}


		public void setCgpan(String cgpan) {
			this.cgpan = cgpan;
		}


		public String getDanid() {
			return danid;
		}


		public void setDanid(String danid) {
			this.danid = danid;
		}


		public double getGuaranteeFee() {
			return guaranteeFee;
		}


		public void setGuaranteeFee(double guaranteeFee) {
			this.guaranteeFee = guaranteeFee;
		}


		public String getUnitNameRP() {
			return unitNameRP;
		}


		public void setUnitNameRP(String unitNameRP) {
			this.unitNameRP = unitNameRP;
		}


		private String paymentMode;
	    private String utrNo;
	    private double paymentAmount;
	    private ArrayList popupData;
	    private String cgpan;
	    private String danid;
	    private double guaranteeFee;
	    private String unitNameRP;
	    
	    
	    public double getPaymentAmount() {
			return paymentAmount;
		}


		public void setPaymentAmount(double paymentAmount) {
			this.paymentAmount = paymentAmount;
		}


		private String virtualAcNumber;
	    private String status;
	    private Date paymentRcvdDate;
	    private Date dtFromDate;
	    private Date dtToDate;
	    private ArrayList < MLIInfo> allMliNames;
	    private String returnLov;
	    
	    public AppropriationActionForm()
	    {
	    	 bankId=null;
		     zoneId=null;
		     branchId=null;
	    }
	    
	    
	    public String getBankId() {
				return bankId;
			}

			public void setBankId(String bankId) {
				this.bankId = bankId;
			}

			public String getZoneId() {
				return zoneId;
			}

			public void setZoneId(String zoneId) {
				this.zoneId = zoneId;
			}

			public String getBranchId() {
				return branchId;
			}

			public void setBranchId(String branchId) {
				this.branchId = branchId;
			}


			public String getMliName() {
				return mliName;
			}


			public void setMliName(String mliName) {
				this.mliName = mliName;
			}


			public String getPaymentId() {
				return paymentId;
			}


			public void setPaymentId(String paymentId) {
				this.paymentId = paymentId;
			}


			


			public String getPaymentMode() {
				return paymentMode;
			}


			public void setPaymentMode(String paymentMode) {
				this.paymentMode = paymentMode;
			}


		/*	public double getPaymentAmount() {
				return paymentAmount;
			}


			public void setPaymentAmount(double paymentAmount) {
				this.paymentAmount = paymentAmount;
			}
*/

			public String getVirtualAcNumber() {
				return virtualAcNumber;
			}


			public void setVirtualAcNumber(String virtualAcNumber) {
				this.virtualAcNumber = virtualAcNumber;
			}


			public String getMliNames() {
				return mliNames;
			}


			public void setMliNames(String mliNames) {
				this.mliNames = mliNames;
			}


			public String getStatus() {
				return status;
			}


			public void setStatus(String status) {
				this.status = status;
			}


			public Date getPaymentRcvdDate() {
				return paymentRcvdDate;
			}

			public void setPaymentRcvdDate(Date paymentRcvdDate) {
				this.paymentRcvdDate = paymentRcvdDate;
			}


			public Date getDtFromDate() {
				return dtFromDate;
			}


			public void setDtFromDate(Date dtFromDate) {
				this.dtFromDate = dtFromDate;
			}


			public Date getDtToDate() {
				return dtToDate;
			}


			public void setDtToDate(Date dtToDate) {
				this.dtToDate = dtToDate;
			}




			public String getReturnLov() {
				return returnLov;
			}


			public void setReturnLov(String returnLov) {
				this.returnLov = returnLov;
			}


			public ArrayList < MLIInfo> getAllMliNames() {
				return allMliNames;
			}


			public void setAllMliNames(ArrayList < MLIInfo> allMliNames) {
				this.allMliNames = allMliNames;
			}


			public String getUtrNo() {
				return utrNo;
			}


			public void setUtrNo(String utrNo) {
				this.utrNo = utrNo;
			}


			public String getPaymentDate() {
				return paymentDate;
			}


			public void setPaymentDate(String paymentDate) {
				this.paymentDate = paymentDate;
			}


		
}
