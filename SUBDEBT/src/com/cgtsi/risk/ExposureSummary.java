//Source file: F:\\CGTSI\\Design\\DesignModel\\com\\cgtsi\\risk\\ExposureSummary.java

package com.cgtsi.risk;

import java.util.ArrayList;

public class ExposureSummary  implements java.io.Serializable
{
	private String scheme;
	private String[] state;
	private String[] mli;
	private String[] industry;
	private String[] gender;
	private String[] socialCategory;
	private ArrayList tcDetails;
	private ArrayList wcDetails;
   
   /**
    * @roseuid 39E6CD7A0194
    */
   public ExposureSummary() 
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
    * Access method for the tcDetails property.
    * 
    * @return   the current value of the tcDetails property
    */
   public ArrayList getTcDetails() 
   {
      return tcDetails;
   }
   
   /**
    * Sets the value of the tcDetails property.
    * 
    * @param aTcDetails the new value of the tcDetails property
    */
   public void setTcDetails(ArrayList aTcDetails) 
   {
      tcDetails = aTcDetails;
   }

	/**
    * Access method for the wcDetails property.
    * 
    * @return   the current value of the wcDetails property
    */
   public ArrayList getWcDetails() 
   {
      return wcDetails;
   }
   
   /**
    * Sets the value of the wcDetails property.
    * 
    * @param aWcDetails the new value of the wcDetails property
    */
   public void setWcDetails(ArrayList aWcDetails) 
   {
      wcDetails = aWcDetails;
   }
}
