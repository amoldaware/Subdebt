//Source file: C:\\CHECKOUT\\28OCT2003\\com\\cgtsi\\mcgs\\ParticipatingBankLimit.java

package com.cgtsi.mcgs;

import java.util.Date;


/**
 * This class has the information related to limit set for a participating bank.
 */
public class ParticipatingBankLimit  implements java.io.Serializable
{
   private String bankName;
   private double amount;
   private Date validFrom;
   private Date validTo;
   private String memberId;
   
   /**
    * @roseuid 3A1646E800B0
    */
   public ParticipatingBankLimit() 
   {
    
   }
   
   /**
    * Access method for the bankName property.
    * 
    * @return   the current value of the bankName property
    */
   public String getBankName() 
   {
      return bankName;
   }
   
   /**
    * Sets the value of the bankName property.
    * 
    * @param aBankName the new value of the bankName property
    */
   public void setBankName(String aBankName) 
   {
      bankName = aBankName;
   }
   
   /**
    * Access method for the amount property.
    * 
    * @return   the current value of the amount property
    */
   public double getAmount() 
   {
      return amount;
   }
   
   /**
    * Sets the value of the amount property.
    * 
    * @param aAmount the new value of the amount property
    */
   public void setAmount(double aAmount) 
   {
      amount = aAmount;
   }
   
   /**
    * Access method for the validFrom property.
    * 
    * @return   the current value of the validFrom property
    */
   public Date getValidFrom() 
   {
      return validFrom;
   }
   
   /**
    * Sets the value of the validFrom property.
    * 
    * @param aValidFrom the new value of the validFrom property
    */
   public void setValidFrom(Date aValidFrom) 
   {
      validFrom = aValidFrom;
   }
   
   /**
    * Access method for the validTo property.
    * 
    * @return   the current value of the validTo property
    */
   public Date getValidTo() 
   {
      return validTo;
   }
   
   /**
    * Sets the value of the validTo property.
    * 
    * @param aValidTo the new value of the validTo property
    */
   public void setValidTo(Date aValidTo) 
   {
      validTo = aValidTo;
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

}
