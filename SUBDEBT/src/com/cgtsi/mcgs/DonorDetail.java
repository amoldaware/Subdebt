//Source file: C:\\CHECKOUT\\28OCT2003\\com\\cgtsi\\mcgs\\DonorDetail.java

package com.cgtsi.mcgs;

import java.util.Date;


/**
 * This class has the donor information
 */
public class DonorDetail 
{
   private String name;
   private String address;
   private double corpusContributionAmt;
   private Date corpusContributionDate;
   
   private String memberId;
   
   /**
    * @roseuid 3A1646E80042
    */
   public DonorDetail() 
   {
    
   }
   
   /**
    * Access method for the name property.
    * 
    * @return   the current value of the name property
    */
   public String getName() 
   {
      return name;
   }
   
   /**
    * Sets the value of the name property.
    * 
    * @param aName the new value of the name property
    */
   public void setName(String aName) 
   {
      name = aName;
   }
   
   /**
    * Access method for the address property.
    * 
    * @return   the current value of the address property
    */
   public String getAddress() 
   {
      return address;
   }
   
   /**
    * Sets the value of the address property.
    * 
    * @param aAddress the new value of the address property
    */
   public void setAddress(String aAddress) 
   {
      address = aAddress;
   }
   
   /**
    * Access method for the corpusContributionAmt property.
    * 
    * @return   the current value of the corpusContributionAmt property
    */
   public double getCorpusContributionAmt() 
   {
      return corpusContributionAmt;
   }
   
   /**
    * Sets the value of the corpusContributionAmt property.
    * 
    * @param aCorpusContributionAmt the new value of the corpusContributionAmt property
    */
   public void setCorpusContributionAmt(double aCorpusContributionAmt) 
   {
      corpusContributionAmt = aCorpusContributionAmt;
   }
   
   /**
    * Access method for the corpusContributionDate property.
    * 
    * @return   the current value of the corpusContributionDate property
    */
   public Date getCorpusContributionDate() 
   {
      return corpusContributionDate;
   }
   
   /**
    * Sets the value of the corpusContributionDate property.
    * 
    * @param aCorpusContributionDate the new value of the corpusContributionDate property
    */
   public void setCorpusContributionDate(Date aCorpusContributionDate) 
   {
      corpusContributionDate = aCorpusContributionDate;
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
