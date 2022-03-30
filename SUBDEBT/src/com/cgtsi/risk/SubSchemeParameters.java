package com.cgtsi.risk;

import java.util.Date;

/*************************************************************
   *
   * Name of the class: SubSchemeParameters
   * This class encapsulates the various sub scheme parameters. This 
   * class has setter and getter methods to set and retrieve the attributes.
   * 
   * @author : Nithyalakshmi P
   * @version:  
   * @since: 
   **************************************************************/

public class SubSchemeParameters implements java.io.Serializable
{
   private String[] state;
   private String[] industry;
   private String[] mli;
   private String[] gender;
   private String[] socialCategory;
   private Date validFromDate;
   private Date validToDate;
   private String subScheme;
   private String subSchemeId;
   
   public SubSchemeParameters() 
   {}
   
   /**
    * Access method for the state property.
    * 
    * @return   the current value of the state property
    */
   public String[] getState() 
   {
      return state;
   }
   
   /**
    * Sets the value of the state property.
    * 
    * @param aState the new value of the state property
    */
   public void setState(String[] aState) 
   {
      state = aState;
   }
   
   /**
    * Access method for the industry property.
    * 
    * @return   the current value of the industry property
    */
   public String[] getIndustry() 
   {
      return industry;
   }
   
   /**
    * Sets the value of the industry property.
    * 
    * @param aIndustry the new value of the industry property
    */
   public void setIndustry(String[] aIndustry) 
   {
      industry = aIndustry;
   }
   
   /**
    * Access method for the mli property.
    * 
    * @return   the current value of the mli property
    */
   public String[] getMli() 
   {
      return mli;
   }
   
   /**
    * Sets the value of the mli property.
    * 
    * @param aMli the new value of the mli property
    */
   public void setMli(String[] aMli) 
   {
      mli = aMli;
   }
   
   /**
    * Access method for the gender property.
    * 
    * @return   the current value of the gender property
    */
   public String[] getGender() 
   {
      return gender;
   }
   
   /**
    * Sets the value of the gender property.
    * 
    * @param aGender the new value of the gender property
    */
   public void setGender(String[] aGender) 
   {
      gender = aGender;
   }

   /**
    * Access method for the socialCategory property.
    * 
    * @return   the current value of the socialCategory property
    */
   public String[] getSocialCategory() 
   {
      return socialCategory;
   }
   
   /**
    * Sets the value of the socialCategory property.
    * 
    * @param aSocialCategory the new value of the socialCategory property
    */
   public void setSocialCategory(String[] aSocialCategory) 
   {
      socialCategory = aSocialCategory;
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
    * @param aSubScheme the new value of the subScheme property
    */
   public void setSubScheme(String aSubScheme) 
   {
      subScheme = aSubScheme;
   }
/**
 * @return
 */
public String getSubSchemeId() {
	return subSchemeId;
}

/**
 * @param aSubSchemeId
 */
public void setSubSchemeId(String aSubSchemeId) {
	subSchemeId = aSubSchemeId;
}

}