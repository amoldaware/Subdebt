//Source file: F:\\CGTSI\\Design\\DesignModel\\com\\cgtsi\\risk\\ParameterCombination.java

package com.cgtsi.risk;

import java.util.Date;

public class ParameterCombination 
{
   private String scheme;
   private String[] state;
   private String[] mli;
   private String[] industry;
   private String[] gender;
   private String[] socialCategory;

   private Date guaIssuedAsOn;
   private double sancAmtRangeFrom;
   private double sancAmtRangeTo;
   private String facilityType;
   
   /**
    * @roseuid 39E6CD7902F1
    */
   public ParameterCombination() 
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
    * Access method for the industrySector property.
    * 
    * @return   the current value of the industrySector property
    */
   public String[] getIndustry() 
   {
      return industry;
   }
   
   /**
    * Sets the value of the industrySector property.
    * 
    * @param aIndustrySector the new value of the industrySector property
    */
   public void setIndustry(String[] aIndustry) 
   {
      industry = aIndustry;
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
    * Access method for the guaIssuedAsOn property.
    * 
    * @return   the current value of the guaIssuedAsOn property
    */
   public Date getGuaIssuedAsOn() 
   {
      return guaIssuedAsOn;
   }
   
   /**
    * Sets the value of the guaIssuedAsOn property.
    * 
    * @param aGuaIssuedAsOn the new value of the guaIssuedAsOn property
    */
   public void setGuaIssuedAsOn(Date aGuaIssuedAsOn) 
   {
      guaIssuedAsOn = aGuaIssuedAsOn;
   }

   /**
    * Access method for the sancAmtRangeFrom property.
    * 
    * @return   the current value of the sancAmtRangeFrom property
    */
   public double getSancAmtRangeFrom() 
   {
      return sancAmtRangeFrom;
   }
   
   /**
    * Sets the value of the sancAmtRangeFrom property.
    * 
    * @param aSancAmtRangeFrom the new value of the sancAmtRangeFrom property
    */
   public void setSancAmtRangeFrom(double aSancAmtRangeFrom) 
   {
      sancAmtRangeFrom = aSancAmtRangeFrom;
   }

   /**
    * Access method for the sancAmtRangeTo property.
    * 
    * @return   the current value of the sancAmtRangeTo property
    */
   public double getSancAmtRangeTo() 
   {
      return sancAmtRangeTo;
   }
   
   /**
    * Sets the value of the sancAmtRangeTo property.
    * 
    * @param aSancAmtRangeTo the new value of the sancAmtRangeTo property
    */
   public void setSancAmtRangeTo(double aSancAmtRangeTo) 
   {
      sancAmtRangeTo = aSancAmtRangeTo;
   }

   /**
    * Access method for the facilityType property.
    * 
    * @return   the current value of the facilityType property
    */
   public String getFacilityType() 
   {
      return facilityType;
   }
   
   /**
    * Sets the value of the facilityType property.
    * 
    * @param aScheme the new value of the facilityType property
    */
   public void setFacilityType(String aFacilityType) 
   {
      facilityType = aFacilityType;
   }
}
