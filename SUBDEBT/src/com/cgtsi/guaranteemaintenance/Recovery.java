//Source file: D:\\com\\cgtsi\\guaranteemaintenance\\Recovery.java

package com.cgtsi.guaranteemaintenance;

import java.util.Date; 
import java.io.Serializable;

/**
 * This class provides data members and methods to deal with the Recovery details.
 */
public class Recovery implements Serializable
{
   private String recoveryNo;//recovery id from database
   private String cgbid;
   private String cgpan;
  
   
   private Date dateOfRecovery;
   private double amountRecovered;
   private double legalCharges;   
   private String remarks;
   private String isRecoveryByOTS; 
   private String isRecoveryBySaleOfAsset;
   private String detailsOfAssetSold;

   
   /**
    * @roseuid 39B9CCD902BF
    */
   public Recovery() 
   {
    
   }
   
   
   /**
    * Access method for the cgbid property.
    * 
    * @return   the current value of the cgbid property
    */
   public String getCgbid() 
   {
      return cgbid;    
   }
   
   /**
    * Sets the value of the cgbid property.
    * 
    * @param aCgbid the new value of the cgbid property
    */
   public void setCgbid(String aCgbid) 
   {
      cgbid = aCgbid;
   }
   
   /**
    * Access method for the amountRecovered property.
    * 
    * @return   the current value of the amountRecovered property
    */
   public double getAmountRecovered() 
   {
      return amountRecovered;    
   }
   
   /**
    * Sets the value of the amountRecovered property.
    * 
    * @param aAmountRecovered the new value of the amountRecovered property
    */
   public void setAmountRecovered(double aAmountRecovered) 
   {
      amountRecovered = aAmountRecovered;
   }
   
   
   /**
	  * Access method for the legalCharges property.
	  * 
	  * @return   the current value of the legalCharges property
	  */
	 public double getLegalCharges() 
	 {
		return legalCharges;    
	 }
   
	 /**
	  * Sets the value of the legalCharges property.
	  * 
	  * @param aLegalCharges the new value of the legalCharges property
	  */
	 public void setLegalCharges(double aLegalCharges) 
	 {
		legalCharges = aLegalCharges;
	 }
   
   /**
    * Access method for the dateOfRecovery property.
    * 
    * @return   the current value of the dateOfRecovery property
    */
   public Date getDateOfRecovery() 
   {
      return dateOfRecovery;    
   }
   
   /**
    * Sets the value of the dateOfRecovery property.
    * 
    * @param aDateOfRecovery the new value of the dateOfRecovery property
    */
   public void setDateOfRecovery(Date aDateOfRecovery) 
   {
      dateOfRecovery = aDateOfRecovery;
   }
   
  
   /**
    * Access method for the remarks property.
    * 
    * @return   the current value of the remarks property
    */
   public String getRemarks() 
   {
      return remarks;    
   }
   
   /**
    * Sets the value of the remarks property.
    * 
    * @param aRemarks the new value of the remarks property
    */
   public void setRemarks(String aRemarks) 
   {
      remarks = aRemarks;
   }
   
   /**
    * Access method for the ots property.
    * 
    * @return   the current value of the ots property
    */
   public String getIsRecoveryByOTS() 
   {
      return isRecoveryByOTS;    
   }
   
   /**
    * Sets the value of the ots property.
    * 
    * @param aOts the new value of the ots property
    */
   public void setIsRecoveryByOTS(String aIsRecoveryByOTS) 
   {
	isRecoveryByOTS = aIsRecoveryByOTS;
   }
   
   /**
    * Access method for the saleOfAsset property.
    * 
    * @return   the current value of the saleOfAsset property
    */
   public String getIsRecoveryBySaleOfAsset() 
   {
      return isRecoveryBySaleOfAsset;    
   }
   
   /**
    * Sets the value of the saleOfAsset property.
    * 
    * @param aSaleOfAsset the new value of the saleOfAsset property
    */
   public void setIsRecoveryBySaleOfAsset(String aIsRecoveryBySaleOfAsset) 
   {
      isRecoveryBySaleOfAsset = aIsRecoveryBySaleOfAsset;
   }
   
   /**
    * Access method for the detailsOfAssetSold property.
    * 
    * @return   the current value of the detailsOfAssetSold property
    */
   public String getDetailsOfAssetSold() 
   {
      return detailsOfAssetSold;    
   }
   
   /**
    * Sets the value of the detailsOfAssetSold property.
    * 
    * @param aDetailsOfAssetSold the new value of the detailsOfAssetSold property
    */
   public void setDetailsOfAssetSold(String aDetailsOfAssetSold) 
   {
      detailsOfAssetSold = aDetailsOfAssetSold;
   }
   
   /**
    * Access method for the cgpan property.
    * 
    * @return   the current value of the cgpan property
    */
   public String getCgpan() 
   {
      return cgpan;    
   }
   
   /**
    * Sets the value of the cgpan property.
    * 
    * @param aCgpan the new value of the cgpan property
    */
   public void setCgpan(String aCgpan) 
   {
      cgpan = aCgpan;
   }
   
   


/**
 * @return
 */
public String getRecoveryNo() {
	return recoveryNo;
}

/**
 * @param string
 */
public void setRecoveryNo(String string) {
	recoveryNo = string;
}

}
