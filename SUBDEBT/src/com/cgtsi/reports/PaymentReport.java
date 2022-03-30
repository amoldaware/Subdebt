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
public class PaymentReport {

	private String memberId;
	private String instrumentNumber;
	private Date recievedDate;
	private Date instrumentDate;
	private double amountPaid;
  
	private String instrumentType;
	private String paymentMode;
	private String payeeBank;
  //added by sukumar@path 25-04-2008
  private String payeeBranch;
	private String payableAt;
	private Date realisationDate;
  //added payId,cgpan and danId by sukumar@path
  private String payId;
  private String danId;
  private String cgpan;
  private double payAmount;
  
  //Diksha 01/06/2017
  private double swatchBharatTax;
  private double krishiKalCess;
  
  //ADDED REALISATIONDATE AND REALISATION AMOUNT BY SUKUMAR@PATH 11-06-2008
  private Date realisedDate;
  private double realisedAmount;
  private String name;
  
  public double getKrishiKalCess()
  {
      return krishiKalCess;
  }

  public void setKrishiKalCess(double krishiKalCess)
  {
      this.krishiKalCess = krishiKalCess;
  }

  public double getSwatchBharatTax()
  {
      return swatchBharatTax;
  }

  public void setSwatchBharatTax(double swatchBharatTax)
  {
      this.swatchBharatTax = swatchBharatTax;
  }

	public PaymentReport() {
	}
	
	public String getName(){
   return this.name;
  }
public void setName(String name){
 this.name=name;
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
	public String getPayId() {
		return payId;
	}

	/**
	 * @param string
	 */
	public void setPayId(String string) {
		payId = string;
	}
  /**
	 * @return String
	 */
	public String getDanId() {
		return danId;
	}

	/**
	 * @param string
	 */
	public void setDanId(String string) {
		danId = string;
	}
  /**
	 * @return String
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
	public String getInstrumentNumber() {
		return instrumentNumber;
	}

	/**
	 * @param i
	 */
	public void setInstrumentNumber(String aInstrumentNumber) {
		instrumentNumber = aInstrumentNumber;
	}

	/**
	 * @return
	 */
	public Date getRecievedDate() {
		return recievedDate;
	}

	/**
	 * @param date
	 */
	public void setRecievedDate(Date date) {
		recievedDate = date;
	}

	/**
	 * @return
	 */
	public Date getInstrumentDate() {
		return instrumentDate;
	}

	/**
	 * @param date
	 */
	public void setInstrumentDate(Date date) {
		instrumentDate = date;
	}




/**
	 * @return
	 */
	public Date getRealisedDate() {
		return realisedDate;
	}

	/**
	 * @param date
	 */
	public void setRealisedDate(Date date) {
		realisedDate = date;
	}
	/**
	 * @return
	 */
	public double getAmountPaid() {
		return amountPaid;
	}

	/**
	 * @param d
	 */
	public void setAmountPaid(double d) {
		amountPaid = d;
	}
  
  
  
  /**
	 * @return
	 */
	public double getRealisedAmount() {
		return realisedAmount;
	}

	/**
	 * @param d
	 */
	public void setRealisedAmount(double d) {
		realisedAmount = d;
	}
  /**
	 * @return
	 */
	public double getPayAmount() {
		return payAmount;
	}

	/**
	 * @param d
	 */
	public void setPayAmount(double d) {
		payAmount = d;
	}

	/**
	 * @return
	 */
	public String getInstrumentType() {
		return instrumentType;
	}

	/**
	 * @param string
	 */
	public void setInstrumentType(String string) {
		instrumentType = string;
	}

	/**
	 * @return
	 */
	public String getPaymentMode() {
		return paymentMode;
	}

	/**
	 * @param string
	 */
	public void setPaymentMode(String string) {
		paymentMode = string;
	}

	/**
	 * @return
	 */
	public String getPayeeBank() {
		return payeeBank;
	}

	/**
	 * @param string
	 */
	public void setPayeeBank(String string) {
		payeeBank = string;
	}
  /* added by sukumr @path 25-04-2008 */
  /**
	 * @return
	 */
	public String getPayeeBranch() {
		return payeeBranch;
	}

	/**
	 * @param string
	 */
	public void setPayeeBranch(String string) {
		payeeBranch = string;
	}

	/**
	 * @return
	 */
	public String getPayableAt() {
		return payableAt;
	}

	/**
	 * @param string
	 */
	public void setPayableAt(String string) {
		payableAt = string;
	}

	/**
	 * @return
	 */
	public Date getRealisationDate() {
		return realisationDate;
	}

	/**
	 * @param date
	 */
	public void setRealisationDate(Date date) {
		realisationDate = date;
	}
        
    private double baseAmount;
        private double SerTaxAmount;
        private double eduCessbaseAmount;
        private double highereduCessbaseAmount;
        
    public void setBaseAmount(double baseAmount)
        {
            this.baseAmount = baseAmount;
        }

        public double getBaseAmount()
        {
            return baseAmount;
        }

        public void setSerTaxAmount(double serTaxAmount)
        {
            SerTaxAmount = serTaxAmount;
        }

        public double getSerTaxAmount()
        {
            return SerTaxAmount;
        }

        public void setEduCessbaseAmount(double eduCessbaseAmount)
        {
            this.eduCessbaseAmount = eduCessbaseAmount;
        }

        public double getEduCessbaseAmount()
        {
            return eduCessbaseAmount;
        }

        public void setHighereduCessbaseAmount(double highereduCessbaseAmount)
        {
            this.highereduCessbaseAmount = highereduCessbaseAmount;
        }

        public double getHighereduCessbaseAmount()
        {
            return highereduCessbaseAmount;
        }
        

}
