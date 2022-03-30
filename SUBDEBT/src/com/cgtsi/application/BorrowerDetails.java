//Source file: C:\\CGTSI\\Vss\\com\\cgtsi\\application\\BorrowerDetails.java

package com.cgtsi.application;
import java.io.Serializable;

import com.cgtsi.actionform.APForm;


/**
 * Has details about the borrower.
 */
public class BorrowerDetails implements Serializable
{
   private String acNo;
   
   /**
    * FALSE-Not Covered by CGTSI.First time entry
    * TRUE-Covered By CGTSI earlier
    */
   private String previouslyCovered;
   private double osAmt;
   
   /**
    * To hold whether the borrower is an NPA or not
    */
   private String npa;
   private String assistedByBank;
   
 //  private String remarks="";
   
   /**
    * has SSI details
    */
   public SSIDetails ssiDetails = null;
   
   
   public APForm apform = null;
  
   
   



/**
    * @roseuid 39B875C900C0
    */
   public BorrowerDetails() 
   {
    
   }
   
   

/**
    * Access method for the acNo property.
    * 
    * @return   the current value of the acNo property
    */
   public String getAcNo() 
   {
      return acNo;
   }
   
   /**
    * Sets the value of the acNo property.
    * 
    * @param aAcNo the new value of the acNo property
    */
   public void setAcNo(String aAcNo) 
   {
      acNo = aAcNo;
   }
   
   /**
    * Access method for the previouslyCovered property.
    * 
    * @return   the current value of the previouslyCovered property
    */
   public String getPreviouslyCovered() 
   {
      return previouslyCovered;
   }
   
   /**
    * Sets the value of the previouslyCovered property.
    * 
    * @param aPreviouslyCovered the new value of the previouslyCovered property
    */
   public void setPreviouslyCovered(String aPreviouslyCovered) 
   {
      previouslyCovered = aPreviouslyCovered;
   }
   
   /**
    * Access method for the osAmt property.
    * 
    * @return   the current value of the osAmt property
    */
   public double getOsAmt() 
   {
      return osAmt;
   }
   
   /**
    * Sets the value of the osAmt property.
    * 
    * @param aOsAmt the new value of the osAmt property
    */
   public void setOsAmt(double aOsAmt) 
   {
      osAmt = aOsAmt;
   }
   
   /**
    * Access method for the npa property.
    * 
    * @return   the current value of the npa property
    */
   public String getNpa() 
   {
      return npa;
   }
   
   /**
    * Sets the value of the npa property.
    * 
    * @param aNpa the new value of the npa property
    */
   public void setNpa(String aNpa) 
   {
      npa = aNpa;
   }
   
   /**
    * Access method for the assistedByBank property.
    * 
    * @return   the current value of the assistedByBank property
    */
   public String getAssistedByBank() 
   {
      return assistedByBank;
   }
   
   /**
    * Sets the value of the assistedByBank property.
    * 
    * @param aAssistedByBank the new value of the assistedByBank property
    */
   public void setAssistedByBank(String aAssistedByBank) 
   {
      assistedByBank = aAssistedByBank;
   }
   
   /**
  	* Access method for the ssiDetails property.
  	* 
  	* @return   the current value of the ssiDetails property
  	*/
 	public SSIDetails getSsiDetails() 
 	{
	return ssiDetails;
 	}
   
	/**
  	* Sets the value of the ssiDetails property.
  	* 
  	* @param aSsiDetails the new value of the ssiDetails property
  	*/
 	public void setSsiDetails(SSIDetails aSsiDetails) 
 	{
	ssiDetails = aSsiDetails;
 	}
 	public APForm getApform() {
 		return apform;
 	}



 	public void setApform(APForm apform) {
 		this.apform = apform;
 	}
//Diksha 29/05/2017
 /* public void setRemarks(String remarks)
 
  {
    this.remarks = remarks;
  }


  public String getRemarks()
  {
    return remarks;
  }?*/
}
