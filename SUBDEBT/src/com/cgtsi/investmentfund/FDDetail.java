package com.cgtsi.investmentfund;

/*************************************************************
   *
   * Name of the class: FDDetail
   * This class encapsulates the various details of investment in  Fixed Deposits.
   *
   * @author : Nithyalakshmi P
   * @version: 
   * @since: 
   **************************************************************/

import java.util.Date;

public class FDDetail
{
   private String investeeName;
   private String instrumentName;
   private String investmentName;
   private double principalAmount;
   private int compoundingFrequency;
   private double interestRate;
   private int tenure;
   private String tenureType;
   private String receiptNumber;
   private Date dateOfDeposit;
   private String maturityName;
   private double maturityAmount;
   private Date maturityDate;
   private String modifiedBy;
   private String instrumentCategory;
   
   private String investmentReferenceNumber;

   private String rating;
   private String agency;
   private double ytmValue;

   public FDDetail() 
   {
    
   }

   /**
    * Access method for the investeeName property.
    * 
    * @return   the current value of the investeeName property
    */
   public String getInvesteeName() 
   {
      return investeeName;    
   }
   
   /**
    * Sets the value of the investeeName property.
    * 
    * @param aInvesteeName the new value of the investeeName property
    */
   public void setInvesteeName(String aInvesteeName) 
   {
      investeeName = aInvesteeName;
   }

   /**
    * Access method for the instrumentName property.
    * 
    * @return   the current value of the instrumentName property
    */
   public String getInstrumentName() 
   {
      return instrumentName;    
   }
   
   /**
    * Sets the value of the instrumentName property.
    * 
    * @param aInstrumentName the new value of the investeeName property
    */
   public void setInstrumentName(String aInstrumentName) 
   {
      instrumentName = aInstrumentName;
   }

   /**
    * Access method for the investmentName property.
    * 
    * @return   the current value of the investmentName property
    */
   public String getInvestmentName() 
   {
      return investmentName;    
   }
   
   /**
    * Sets the value of the investmentName property.
    * 
    * @param aInvestmentName the new value of the investmentName property
    */
   public void setInvestmentName(String aInvestmentName) 
   {
      investmentName = aInvestmentName;
   }

   /**
    * Access method for the principalAmount property.
    * 
    * @return   the current value of the principalAmount property
    */
   public double getPrincipalAmount() 
   {
      return principalAmount;    
   }
   
   /**
    * Sets the value of the principalAmount property.
    * 
    * @param aprincipalAmount the new value of the principalAmount property
    */
   public void setPrincipalAmount(double aPrincipalAmount) 
   {
      principalAmount = aPrincipalAmount;
   }
   
   /**
    * Access method for the compoundingFrequency property.
    * 
    * @return   the current value of the compoundingFrequency property
    */
   public int getCompoundingFrequency() 
   {
      return compoundingFrequency;    
   }
   
   /**
    * Sets the value of the compoundingFrequency property.
    * 
    * @param acompoundingFrequency the new value of the compoundingFrequency property
    */
   public void setCompoundingFrequency(int aCompoundingFrequency) 
   {
      compoundingFrequency = aCompoundingFrequency;
   }
   
   /**
    * Access method for the interestRate property.
    * 
    * @return   the current value of the interestRate property
    */
   public double getInterestRate() 
   {
      return interestRate;    
   }
   
   /**
    * Sets the value of the interestRate property.
    * 
    * @param aInterestRate the new value of the interestRate property
    */
   public void setInterestRate(double aInterestRate) 
   {
      interestRate = aInterestRate;
   }
   
   /**
    * Access method for the tenure property.
    * 
    * @return   the current value of the tenure property
    */
   public int getTenure() 
   {
      return tenure;    
   }
   
   /**
    * Sets the value of the tenure property.
    * 
    * @param aTenure the new value of the tenure property
    */
   public void setTenure(int aTenure) 
   {
      tenure = aTenure;
   }

   /**
    * Access method for the tenureType property.
    * 
    * @return   the current value of the tenureType property
    */
   public String getTenureType() 
   {
      return tenureType;    
   }
   
   /**
    * Sets the value of the tenureType property.
    * 
    * @param aTenureType the new value of the tenureType property
    */
   public void setTenureType(String aTenureType) 
   {
      tenureType = aTenureType;
   }
   
   /**
    * Access method for the receiptNumber property.
    * 
    * @return   the current value of the receiptNumber property
    */
   public String getReceiptNumber() 
   {
      return receiptNumber;    
   }
   
   /**
    * Sets the value of the receiptNumber property.
    * 
    * @param aReceiptNumber the new value of the receiptNumber property
    */
   public void setReceiptNumber(String aReceiptNumber) 
   {
      receiptNumber = aReceiptNumber;
   }
   
   /**
    * Access method for the dateOfDeposit property.
    * 
    * @return   the current value of the dateOfDeposit property
    */
   public Date getDateOfDeposit() 
   {
      return dateOfDeposit;    
   }
   
   /**
    * Sets the value of the dateOfDeposit property.
    * 
    * @param aDateOfDeposit the new value of the dateOfDeposit property
    */
   public void setDateOfDeposit(Date aDateOfDeposit) 
   {
      dateOfDeposit = aDateOfDeposit;
   }

   /**
    * Access method for the maturityName property.
    * 
    * @return   the current value of the maturityName property
    */
   public String getMaturityName() 
   {
      return maturityName;    
   }
   
   /**
    * Sets the value of the maturityName property.
    * 
    * @param maturityName the new value of the maturityName property
    */
   public void setMaturityName(String aMaturityName) 
   {
      maturityName = aMaturityName;
   }
   
   /**
    * Access method for the maturityAmount property.
    * 
    * @return   the current value of the maturityAmount property
    */
   public double getMaturityAmount() 
   {
      return maturityAmount;    
   }
   
   /**
    * Sets the value of the maturityAmount property.
    * 
    * @param aMaturityAmount the new value of the maturityAmount property
    */
   public void setmaturityAmount(int aMaturityAmount) 
   {
      maturityAmount = aMaturityAmount;
   }

   /**
    * Access method for the maturityDate property.
    * 
    * @return   the current value of the maturityDate property
    */
   public Date getMaturityDate() 
   {
      return maturityDate;    
   }
   
   /**
    * Sets the value of the maturityDate property.
    * 
    * @param aMaturityDate the new value of the maturityDate property
    */
   public void setMaturityDate(Date aMaturityDate) 
   {
      maturityDate = aMaturityDate;
   }

   public String getModifiedBy()
   {
	   return modifiedBy;
   }

   public void setModifiedBy(String aModifiedBy)
   {
	   modifiedBy = aModifiedBy;
   }
/**
 * @return
 */
public String getInvestmentReferenceNumber() {
	return investmentReferenceNumber;
}

/**
 * @param string
 */
public void setInvestmentReferenceNumber(String string) {
	investmentReferenceNumber = string;
}

/**
 * @param d
 */
public void setMaturityAmount(double d) {
	maturityAmount = d;
}

/**
 * @return
 */
public String getRating() {
	return rating;
}

/**
 * @param string
 */
public void setRating(String string) {
	rating = string;
}


/**
 * @return
 */
public String getInstrumentCategory() {
	return instrumentCategory;
}

/**
 * @param string
 */
public void setInstrumentCategory(String string) {
	instrumentCategory = string;
}

/**
 * @return
 */
public String getAgency() {
	return agency;
}

/**
 * @param string
 */
public void setAgency(String string) {
	agency = string;
}

/**
 * @return
 */
public double getYtmValue() {
	return ytmValue;
}

/**
 * @param d
 */
public void setYtmValue(double d) {
	ytmValue = d;
}

}
