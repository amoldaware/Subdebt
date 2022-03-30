/*
 * Created on Dec 3, 2003
 *
 * To change the template for this generated file go to
 * Window&gt;Preferences&gt;Java&gt;Code Generation&gt;Code and Comments
 */
package com.cgtsi.reports;

import java.util.Date;

/**
 * @author RT14509
 *
 * To change the template for this generated type comment go to
 * Window&gt;Preferences&gt;Java&gt;Code Generation&gt;Code and Comments
 */
public class DanReport {

	private String cgpan;
	private String dan;
	private String scheme;
	private String ssi;
	private int count;				
	private String applicationNumber;
	private Date applicationDate;
	private Date danDate;
	private double totalAmount;
	private double guaranteeFee;
	private double guaranteeFeePaid;
	private String memberId;
	private String bank;
	private String zone;
	private String branch;
  private String branchName;
  //bhu 28072015
  private double paidAmount;
  
  
  //Diksha 
  private double krishiKalCess;
  
	private String bankAppRefNo;
	private double swatchBharatTax;
	
  public String getBankAppRefNo() {
		return bankAppRefNo;
	}

	public void setBankAppRefNo(String bankAppRefNo) {
		this.bankAppRefNo = bankAppRefNo;
	}

public double getSwatchBharatTax() {
		return swatchBharatTax;
	}

	public void setSwatchBharatTax(double swatchBharatTax) {
		this.swatchBharatTax = swatchBharatTax;
	}
//Diksha 01/06/2017
	 public double getKrishiKalCess()
	    {
	        return krishiKalCess;
	    }

	    public void setKrishiKalCess(double krishiKalCess)
	    {
	        this.krishiKalCess = krishiKalCess;
	    }
//end
public double getPaidAmount() {
	return paidAmount;
}

public void setPaidAmount(double paidAmount) {
	this.paidAmount = paidAmount;
}

public double getUnPaidAmount() {
	return unPaidAmount;
}

public void setUnPaidAmount(double unPaidAmount) {
	this.unPaidAmount = unPaidAmount;
}

public double getPendingAmount() {
	return pendingAmount;
}

public void setPendingAmount(double pendingAmount) {
	this.pendingAmount = pendingAmount;
}


private double unPaidAmount;
  private double pendingAmount;
	
  //following Three attribute are added by sudeep.dhiman@pathinfotech.com for dispalying the 
  // corresponding column in dan report 
  private String status;
  private Date closeDate;
  private double appAmount;
  //added by sukumar for getting Reapproved amount
  private double reAppAmount;
  private String appropriationBy;
  
  // added by sukumar on 08012008 for getting guarantee start date
  private Date guaranteeStartDate;
 

	public DanReport() {
	}
  
  public void setAppropriationBy(String appropriationBy){
  
  this.appropriationBy=appropriationBy;
  }
  public String getAppropriationBy(){
   return this.appropriationBy;
  }
	//setter and getter for guaranteeStartDate added by sukumar
  
  /**
	 * @param date
	 */
	public void setGuaranteeStartDate(Date date) {
		guaranteeStartDate = date;
	}


	/**
	 * @return
	 */
	public Date getGuaranteeStartDate() {
		return guaranteeStartDate;
	}
  //setter getter for status, closeDate and appAmount added by sudeep.dhiman@pathinfotech.com
  /////////////////////////////////////////////
  /**
  * @param String
  */
  public void setStatus(String status)
  {
    this.status = status;
  }
  /**
  * @return String
  */
  public String getStatus()
  {
    return this.status;
  }
  
  /**
  * @param Date
  */
  public void setCloseDate(Date closeDate)
  {
    this.closeDate = closeDate;
  }
  /**
  * @return Date
  */
  public Date getCloseDate()
  {
    return this.closeDate;
  }
  /**
  * @param double
  */
  public void setAppAmount(double appAmount)
  {
    this.appAmount = appAmount;
  }
  /**
  * @return double
  */
  public double getAppAmount()
  {
    return this.appAmount;
  }
  /**
  * @param double
  */
  public void setReAppAmount(double reAppAmount)
  {
    this.reAppAmount = reAppAmount;
  }
  /**
  * @return double
  */
  public double getReAppAmount()
  {
    return this.reAppAmount;
  }
//////////////////////////////////////////

   /**
	 * @return
	 */
	public String getCgpan() {
		return cgpan;
	}

	/**
	 * @param string
	 */
	public void setCgpan(String string) {
		cgpan = string;
	}
	
	

	/**
	 * @return
	 */
	public String getScheme() {
		return scheme;
	}

	/**
	 * @param string
	 */
	public void setScheme(String string) {
		scheme = string;
	}

	/**
	 * @return
	 */
	public String getSsi() {
		return ssi;
	}

	/**
	 * @param string
	 */
	public void setSsi(String string) {
		ssi = string;
	}

	/**
	 * @return
	 */
	public String getApplicationNumber() {
		return applicationNumber;
	}

	/**
	 * @param i
	 */
	public void setApplicationNumber(String iApplicationNumber) {
		applicationNumber = iApplicationNumber;
	}

	/**
	 * @return
	 */
	public Date getApplicationDate() {
		return applicationDate;
	}

	/**
	 * @param date
	 */
	public void setApplicationDate(Date date) {
		applicationDate = date;
	}

	/**
	 * @return
	 */
	public double getTotalAmount() {
		return totalAmount;
	}

	/**
	 * @param d
	 */
	public void setTotalAmount(double d) {
		totalAmount = d;
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
	public void setGuaranteeFee(double d) {
		guaranteeFee = d;
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
	 * @return
	 */
	public String getDan() {
		return dan;
	}

	/**
	 * @return
	 */
	public Date getDanDate() {
		return danDate;
	}

	/**
	 * @param string
	 */
	public void setDan(String string) {
		dan = string;
	}

	/**
	 * @param date
	 */
	public void setDanDate(Date date) {
		danDate = date;
	}

	/**
	 * @return
	 */
	public int getCount() {   
		return count;
	}

	/**
	 * @param i
	 */
	public void setCount(int i) {
		count = i;
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
	public void setGuaranteeFeePaid(double d) {
		guaranteeFeePaid = d;
	}

	/**
	 * @return
	 */
	public String getBank() {
		return bank;
	}

	/**
	 * @return
	 */
	public String getBranch() {
		return branch;
	}
/**
	 * @return
	 */
	public String getBranchName() {
		return branchName;
	}
  /**
   * 
   * @param name
   */
  public void setBranchName(String name){
   branchName = name;
  }
	/**
	 * @return
	 */
	public String getZone() {
		return zone;
	}

	/**
	 * @param string
	 */
	public void setBank(String string) {
		bank = string;
	}

	/**
	 * @param string
	 */
	public void setBranch(String string) {
		branch = string;
	}

	/**
	 * @param string
	 */
	public void setZone(String string) {
		zone = string;
	}


//added @path on 07-09-2013
        private double inclSTaxAmnt;
        private double inclECESSAmnt;
        private double inclHECESSAmnt;
        private double baseAmnt;

    public void setInclSTaxAmnt(double inclSTaxAmnt) {
        this.inclSTaxAmnt = inclSTaxAmnt;
    }

    public double getInclSTaxAmnt() {
        return inclSTaxAmnt;
    }

    public void setInclECESSAmnt(double inclECESSAmnt) {
        this.inclECESSAmnt = inclECESSAmnt;
    }

    public double getInclECESSAmnt() {
        return inclECESSAmnt;
    }

    public void setInclHECESSAmnt(double inclHECESSAmnt) {
        this.inclHECESSAmnt = inclHECESSAmnt;
    }

    public double getInclHECESSAmnt() {
        return inclHECESSAmnt;
    }

    public void setBaseAmnt(double baseAmnt) {
        this.baseAmnt = baseAmnt;
    }

    public double getBaseAmnt() {
        return baseAmnt;
    }
}
