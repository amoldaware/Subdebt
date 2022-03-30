//Source file: C:\\CGTSI\\Vss\\com\\cgtsi\\application\\SubScheme.java

package com.cgtsi.application;


/**
 * scheme class encapsulates the subscheme details.
 */
public class SubScheme 
{
   public String schemeName = null;
   public String schemeId = null;
   
   public int scmId ;
   
   public String zone = null;
   public String stateName = null;
   public String districtName = null;
   public String mliName = null;
   public String industrySector = null;
   public String region = null;
   public String gender = null;   
   public int collateralAllowed = 0;
   public int thirdPartyGuarantee = 0;
   public double minAmount = 0;
   public double maxAmount = 0;
   public double plrInterestDiff = 0;
   public double serviceFeeRate = 0;      
   public double defaultRate = 0;
   public double gcExtent = 0;
   
   /**
    * @roseuid 39B875C80119
    */
   public SubScheme() 
   {
    
   }
   
   /**
    * Access method for the schemeName property.
    * 
    * @return   the current value of the schemeName property
    */
   public String getSchemeName() 
   {
      return schemeName;
   }
   
   /**
    * Sets the value of the schemeName property.
    * 
    * @param aSchemeName the new value of the schemeName property
    */
   public void setSchemeName(String aSchemeName) 
   {
      schemeName = aSchemeName;
   }
   
   /**
    * Access method for the schemeId property.
    * 
    * @return   the current value of the schemeId property
    */
   public String getSchemeId() 
   {
      return schemeId;
   }
   
   /**
    * Sets the value of the schemeId property.
    * 
    * @param aSchemeId the new value of the schemeId property
    */
   public void setSchemeId(String aSchemeId) 
   {
      schemeId = aSchemeId;
   }
   
   /**
   * Access method for the zone property.
   * 
   * @return   the current value of the zone property
   */
   public String getZone() 
   {
		 return zone;
	}
   
   /**
   * Sets the value of the zone property.
   * 
   * @param aSchemeId the new value of the zone property
   */
	  public void setZone(String aZone) 
	  {
		 zone = aZone;
	  }
	  
  /**
  * Access method for the state name property.
  * 
  * @return   the current value of the state name property
  */
		 public String getStateName() 
		 {
			return stateName;
		 }
   
  /**
  * Sets the value of the state name property.
  * 
  * @param aSchemeId the new value of the state name property
  */
		 public void setStateName(String aStateName) 
		 {
			stateName = aStateName;
		 }
   
  /**
  * Access method for the district name property.
  * 
  * @return   the current value of the district name property
  */
		 public String getDistrictName() 
		 {
			return districtName;
		 }
   
  /**
  * Sets the value of the district name property.
  * 
  * @param aSchemeId the new value of the district name property
  */
		 public void setDistrictName(String aDistrictName) 
		 {
			districtName = aDistrictName;
		 }
		 
 
  /**
  * Access method for the mli name property.
  * 
  * @return   the current value of the mli name property
  */
	   public String getMliName() 
	   {
		  return mliName;
	   }
   
	/**
	* Sets the value of the mli name property.
	* 
	* @param aSchemeId the new value of the mli name property
	*/
	   public void setMliName(String aMliName) 
	   {
		  mliName = aMliName;
	   }
		   
	/**
	* Access method for the industry sector property.
	* 
	* @return   the current value of the industry sector property
	*/
	   public String getIndustrySector() 
	   {
		  return industrySector;
	   }
   
	/**
	* Sets the value of the industry sector property.
	* 
	* @param aSchemeId the new value of the industry sector property
	*/
	   public void setIndustrySector(String aIndustrySector) 
	   {
			industrySector = aIndustrySector;
	   }
		 
		 
   /**
    * Access method for the plrInterestDiff property.
    * 
    * @return   the current value of the plrInterestDiff property
    */
   public double getPlrInterestDiff() 
   {
      return plrInterestDiff;
   }
   
   /**
    * Sets the value of the plrInterestDiff property.
    * 
    * @param aPlrInterestDiff the new value of the plrInterestDiff property
    */
   public void setPlrInterestDiff(double aPlrInterestDiff) 
   {
      plrInterestDiff = aPlrInterestDiff;
   }
   
   /**
    * Access method for the serviceFeeRate property.
    * 
    * @return   the current value of the serviceFeeRate property
    */
   public double getServiceFeeRate() 
   {
      return serviceFeeRate;
   }
   
   /**
    * Sets the value of the serviceFeeRate property.
    * 
    * @param aServiceFeeRate the new value of the serviceFeeRate property
    */
   public void setServiceFeeRate(double aServiceFeeRate) 
   {
      serviceFeeRate = aServiceFeeRate;
   }
   
   /**
    * Access method for the collateralAllowed property.
    * 
    * @return   the current value of the collateralAllowed property
    */
   public int getCollateralAllowed() 
   {
      return collateralAllowed;
   }
   
   /**
    * Sets the value of the collateralAllowed property.
    * 
    * @param aCollateralAllowed the new value of the collateralAllowed property
    */
   public void setCollateralAllowed(int aCollateralAllowed) 
   {
      collateralAllowed = aCollateralAllowed;
   }
   
   /**
    * Access method for the thirdPartyGuarantee property.
    * 
    * @return   the current value of the thirdPartyGuarantee property
    */
   public int getThirdPartyGuarantee() 
   {
      return thirdPartyGuarantee;
   }
   
   /**
    * Sets the value of the thirdPartyGuarantee property.
    * 
    * @param aThirdPartyGuarantee the new value of the thirdPartyGuarantee property
    */
   public void setThirdPartyGuarantee(int aThirdPartyGuarantee) 
   {
      thirdPartyGuarantee = aThirdPartyGuarantee;
   }
   
   /**
    * Access method for the minAmount property.
    * 
    * @return   the current value of the minAmount property
    */
   public double getMinAmount() 
   {
      return minAmount;
   }
   
   /**
    * Sets the value of the minAmount property.
    * 
    * @param aMinAmount the new value of the minAmount property
    */
   public void setMinAmount(double aMinAmount) 
   {
      minAmount = aMinAmount;
   }
   
   /**
    * Access method for the maxAmount property.
    * 
    * @return   the current value of the maxAmount property
    */
   public double getMaxAmount() 
   {
      return maxAmount;
   }
   
   /**
    * Sets the value of the maxAmount property.
    * 
    * @param aMaxAmount the new value of the maxAmount property
    */
   public void setMaxAmount(double aMaxAmount) 
   {
      maxAmount = aMaxAmount;
   }
   
   /**
    * Access method for the gender property.
    * 
    * @return   the current value of the gender property
    */
   public String getGender() 
   {
      return gender;
   }
   
   /**
    * Sets the value of the gender property.
    * 
    * @param aGender the new value of the gender property
    */
   public void setGender(String aGender) 
   {
      gender = aGender;
   }
   
   /**
    * Access method for the region property.
    * 
    * @return   the current value of the region property
    */
   public String getRegion() 
   {
      return region;
   }
   
   /**
    * Sets the value of the region property.
    * 
    * @param aRegion the new value of the region property
    */
   public void setRegion(String aRegion) 
   {
      region = aRegion;
   }
   
   /**
    * Access method for the defaultRate property.
    * 
    * @return   the current value of the defaultRate property
    */
   public double getDefaultRate() 
   {
      return defaultRate;
   }
   
   /**
    * Sets the value of the defaultRate property.
    * 
    * @param aDefaultRate the new value of the defaultRate property
    */
   public void setDefaultRate(double aDefaultRate) 
   {
      defaultRate = aDefaultRate;
   }
   
   /**
    * Access method for the gcExtent property.
    * 
    * @return   the current value of the gcExtent property
    */
   public double getGcExtent() 
   {
      return gcExtent;
   }
   
   /**
    * Sets the value of the gcExtent property.
    * 
    * @param aGcExtent the new value of the gcExtent property
    */
   public void setGcExtent(double aGcExtent) 
   {
      gcExtent = aGcExtent;
   }


 public void setScmId(int scmId)
  {
    this.scmId = scmId;
  }


  public int getScmId()
  {
    return scmId;
  }
}
