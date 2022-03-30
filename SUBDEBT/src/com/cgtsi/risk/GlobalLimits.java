//Source file: C:\\CGTSI\\CodeFromRose\\com\\cgtsi\\risk\\GlobalLimits.java

package com.cgtsi.risk;


/*************************************************************
   *
   * Name of the class: GlobalLimits
   * This class encapsulates the various parameters for global limits setting. This 
   * class has setter and getter methods to set and retrieve the attributes.
   * 
   * @author : Nithyalakshmi P
   * @version:  
   * @since: 
   **************************************************************/

import java.util.Date;

public class GlobalLimits  implements java.io.Serializable
{
   private String scheme;
/*   private String state;
   private String industry;
   private String mli;
   private String bank;
   private String branch;
   private String zone;
   private String region;
   private String gender;				//included 09-09-2003
   private String district;				//included 09-09-2003
*/
   private String createdBy;
   private String createdDate;
   private double upperLimit;
   private int isFundsBasedOrNonFundsBasedOrBoth;
   private Date validToDate;
   private Date validFromDate;
   private String subScheme;
   
   public GlobalLimits() 
   {
    
   }
   
   /**
    * Access method for the scheme property.
    * 
    * @return   the current value of the scheme property
    */
   public String getScheme() 
   {
      return scheme;
   }
   
   /**
    * Sets the value of the scheme property.
    * 
    * @param aScheme the new value of the scheme property
    */
   public void setScheme(String aScheme) 
   {
      scheme = aScheme;
   }
   
   /**
    * Access method for the state property.
    * 
    * @return   the current value of the state property
    */
/*   public String getState() 
   {
      return state;
   }*/
   
   /**
    * Sets the value of the state property.
    * 
    * @param aState the new value of the state property
    */
/*   public void setState(String aState) 
   {
      state = aState;
   }*/
   
   /**
    * Access method for the industry property.
    * 
    * @return   the current value of the industry property
    */
/*   public String getIndustry() 
   {
      return industry;
   }*/
   
   /**
    * Sets the value of the industry property.
    * 
    * @param aIndustry the new value of the industry property
    */
/*   public void setIndustry(String aIndustry) 
   {
      industry = aIndustry;
   }*/
   
   /**
    * Access method for the createdBy property.
    * 
    * @return   the current value of the createdBy property
    */
   public String getCreatedBy() 
   {
      return createdBy;
   }
   
   /**
    * Sets the value of the createdBy property.
    * 
    * @param createdBy the new value of the createdBy property
    */
   public void setCreatedBy(String aCreatedBy) 
   {
      createdBy = aCreatedBy;
   }
   
   /**
    * Access method for the createdDate property.
    * 
    * @return   the current value of the createdDate property
    */
   public String getCreatedDate() 
   {
      return createdDate;
   }
   
   /**
    * Sets the value of the createdDate property.
    * 
    * @param aCreatedDate the new value of the createdDate property
    */
   public void setCreatedDate(String aCreatedDate) 
   {
      createdDate = aCreatedDate;
   }
   
   /**
    * Access method for the mli property.
    * 
    * @return   the current value of the mli property
    */
/*   public String getMli() 
   {
      return mli;
   }*/
   
   /**
    * Sets the value of the mli property.
    * 
    * @param aMli the new value of the mli property
    */
/*   public void setMli(String aMli) 
   {
      mli = aMli;
   }*/
   
   /**
    * Access method for the upperLimit property.
    * 
    * @return   the current value of the upperLimit property
    */
   public double getUpperLimit() 
   {
      return upperLimit;
   }
   
   /**
    * Sets the value of the upperLimit property.
    * 
    * @param aUpperLimit the new value of the upperLimit property
    */
   public void setUpperLimit(double aUpperLimit) 
   {
      upperLimit = aUpperLimit;
   }
   
   /**
    * Access method for the isFundsBasedOrNonFundsBasedOrBoth property
    * 
    * @return   the current value of isFundsBasedOrNonFundsBasedOrBoth property
    */
   public int getIsFundsBasedOrNonFundsBasedOrBoth() 
   {
      return isFundsBasedOrNonFundsBasedOrBoth;
   }
   
   /**
    * Sets the value of the isFundsBasedOrNonFundsBasedOrBoth property.
    * 
    * @param aIsFundsBasedOrNonFundsBasedOrBoth the new value of the isFundsBasedOrNonFundsBasedOrBoth property
    */
   public void setIsFundsBasedOrNonFundsBasedOrBoth(int aIsFundsBasedOrNonFundsBasedOrBoth) 
   {
      isFundsBasedOrNonFundsBasedOrBoth = aIsFundsBasedOrNonFundsBasedOrBoth;
   }
   
   /**
    * Access method for the bank property.
    * 
    * @return   the current value of the bank property
    */
/*   public String getBank() 
   {
      return bank;
   }*/
   
   /**
    * Sets the value of the bank property.
    * 
    * @param aBank the new value of the bank property
    */
/*   public void setBank(String aBank) 
   {
      bank = aBank;
   }*/
   
   /**
    * Access method for the branch property.
    * 
    * @return   the current value of the branch property
    */
/*   public String getBranch() 
   {
      return branch;
   }*/
   
   /**
    * Sets the value of the branch property.
    * 
    * @param aBranch the new value of the branch property
    */
/*   public void setBranch(String aBranch) 
   {
      branch = aBranch;
   }*/
   
   /**
    * Access method for the zone property.
    * 
    * @return   the current value of the zone property
    */
/*   public String getZone() 
   {
      return zone;
   }*/
   
   /**
    * Sets the value of the zone property.
    * 
    * @param aZone the new value of the zone property
    */
/*   public void setZone(String aZone) 
   {
      zone = aZone;
   }*/
   
   /**
    * Access method for the validToDate property.
    * 
    * @return   the current value of the validToDate property
    */
   public Date getValidToDate() 
   {
      return validToDate;
   }
   
   /**
    * Sets the value of the validToDate property.
    * 
    * @param aValidToDate the new value of the validToDate property
    */
   public void setValidToDate(Date aValidToDate) 
   {
      validToDate = aValidToDate;
   }
   
   /**
    * Access method for the validFromDate property.
    * 
    * @return   the current value of the validFromDate property
    */
   public Date getValidFromDate() 
   {
      return validFromDate;
   }
   
   /**
    * Sets the value of the validFromDate property.
    * 
    * @param aValidFromDate the new value of the validFromDate property
    */
   public void setValidFromDate(Date aValidFromDate) 
   {
      validFromDate = aValidFromDate;
   }
   
   /**
    * Access method for the region property.
    * 
    * @return   the current value of the region property
    */
/*   public String getRegion() 
   {
      return region;
   }*/
   
   /**
    * Sets the value of the region property.
    * 
    * @param aRegion the new value of the region property
    */
/*   public void setRegion(String aRegion) 
   {
      region = aRegion;
   }*/

   /**
    * Access method for the gender property.
    * 
    * @return   the current value of the gender property
    */
/*   public String getGender() 
   {
      return gender;
   }*/
   
   /**
    * Sets the value of the gender property.
    * 
    * @param aGender the new value of the gender property
    */
/*   public void setGender(String aGender) 
   {
      gender = aGender;
   }*/

   /**
    * Access method for the district property.
    * 
    * @return   the current value of the district property
    */
/*   public String getDistrict() 
   {
      return district;
   }*/
   
   /**
    * Sets the value of the district property.
    * 
    * @param aDistrict the new value of the district property
    */
/*   public void setDistrict(String aDistrict) 
   {
      district = aDistrict;
   }*/

   /**
    * Access method for the subScheme property.
    * 
    * @return   the current value of the subScheme property
    */
   public String getSubScheme() 
   {
      return subScheme;
   }
   
   /**
    * Sets the value of the subScheme property.
    * 
    * @param aDistrict the new value of the subScheme property
    */
   public void setSubScheme(String aSubScheme) 
   {
      subScheme = aSubScheme;
   }
}