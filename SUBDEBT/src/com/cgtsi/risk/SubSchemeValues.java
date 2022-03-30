package com.cgtsi.risk;

/*************************************************************
   *
   * Name of the class: SubSchemeValues
   * This class encapsulates the sub scheme values. This 
   * class has setter and getter methods to set and retrieve the attributes.
   * 
   * @author : Nithyalakshmi P
   * @version:  
   * @since: 
   **************************************************************/

import java.util.Date;

public class SubSchemeValues implements java.io.Serializable
{
	private int appFilingTimeLimit;
	private Date appFilingTimeLimitValidFrom;
	private Date appFilingTimeLimitValidTo;
	private double maxGCoverExposure;
	private Date maxGCoverExposureValidFrom;
	private Date maxGCoverExposureValidTo;
	private double minSanctionedAmount;
	private Date minSanctionedAmtValidFrom;
	private Date minSanctionedAmtValidTo;
	private double maxSanctionedAmount;
	private Date maxSanctionedAmtValidFrom;
	private Date maxSanctionedAmtValidTo;
	private double maxBorrowerExposureAmount;
	private Date maxBorrowerExpAmtValidFrom;
	private Date maxBorrowerExpAmtValidTo;
	private double guaranteeCoverExtent;
	private Date guaranteeCoverExtentValidFrom;
	private Date guaranteeCoverExtentValidTo;
	private double serviceFeeCardRate;
	private Date serviceFeeCardRateValidFrom;
	private Date serviceFeeCardRateValidTo;
	private double guaranteeFeeCardRate;
	private Date guaranteeFeeCardRateValidFrom;
	private Date guaranteeFeeCardRateValidTo;
	private String defaultRateApplicable;
	private Date defRateApplicableValidFrom;
	private Date defRateApplicableValidTo;
	private double defaultRate;
	private Date defRateValidFrom;
	private Date defRateValidTo;
	private double moderationFactor;
	private Date moderationFactorValidFrom;
	private Date moderationFactorValidTo;
   
   public SubSchemeValues() 
   {}
   
   /**
    * Access method for the appFilingTimeLimit property.
    * 
    * @return   the current value of the appFilingTimeLimit property
    */
   public int getAppFilingTimeLimit() 
   {
      return appFilingTimeLimit;
   }
   
   /**
    * Sets the value of the appFilingTimeLimit property.
    * 
    * @param aAppFilingTimeLimit the new value of the appFilingTimeLimit property
    */
   public void setAppFilingTimeLimit(int aAppFilingTimeLimit) 
   {
      appFilingTimeLimit = aAppFilingTimeLimit;
   }

   /**
    * Access method for the appFilingTimeLimitValidFrom property.
    * 
    * @return   the current value of the appFilingTimeLimitValidFrom property
    */
   public Date getAppFilingTimeLimitValidFrom() 
   {
      return appFilingTimeLimitValidFrom;
   }
   
   /**
    * Sets the value of the appFilingTimeLimitValidFrom property.
    * 
    * @param aAppFilingTimeLimitValidFrom the new value of the appFilingTimeLimitValidFrom property
    */
   public void setAppFilingTimeLimitValidFrom(Date aAppFilingTimeLimitValidFrom) 
   {
      appFilingTimeLimitValidFrom = aAppFilingTimeLimitValidFrom;
   }   

   /**
    * Access method for the appFilingTimeLimitValidTo property.
    * 
    * @return   the current value of the appFilingTimeLimitValidTo property
    */
   public Date getAppFilingTimeLimitValidTo() 
   {
      return appFilingTimeLimitValidTo;
   }
   
   /**
    * Sets the value of the appFilingTimeLimitValidTo property.
    * 
    * @param aAppFilingTimeLimit the new value of the appFilingTimeLimitValidTo property
    */
   public void setAppFilingTimeLimitValidTo(Date aAppFilingTimeLimitValidTo) 
   {
      appFilingTimeLimitValidTo = aAppFilingTimeLimitValidTo;
   }

   /**
    * Access method for the maxGCoverExposure property.
    * 
    * @return   the current value of the maxGCoverExposure property
    */
   public double getMaxGCoverExposure() 
   {
      return maxGCoverExposure;
   }
   
   /**
    * Sets the value of the maxGCoverExposure property.
    * 
    * @param aMaxGCoverExposure the new value of the maxGCoverExposure property
    */
   public void setMaxGCoverExposure(double aMaxGCoverExposure) 
   {
      maxGCoverExposure = aMaxGCoverExposure;
   }

   /**
    * Access method for the maxGCoverExposureValidFrom property.
    * 
    * @return   the current value of the maxGCoverExposureValidFrom property
    */
   public Date getMaxGCoverExposureValidFrom() 
   {
      return maxGCoverExposureValidFrom;
   }
   
   /**
    * Sets the value of the maxGCoverExposureValidFrom property.
    * 
    * @param aMaxGCoverExposureValidFrom the new value of the maxGCoverExposureValidFrom property
    */
   public void setMaxGCoverExposureValidFrom(Date aMaxGCoverExposureValidFrom) 
   {
      maxGCoverExposureValidFrom = aMaxGCoverExposureValidFrom;
   }

   /**
    * Access method for the maxGCoverExposureValidTo property.
    * 
    * @return   the current value of the maxGCoverExposureValidTo property
    */
   public Date getMaxGCoverExposureValidTo() 
   {
      return maxGCoverExposureValidTo;
   }
   
   /**
    * Sets the value of the maxGCoverExposureValidTo property.
    * 
    * @param aMaxGCoverExposureValidTo the new value of the maxGCoverExposureValidTo property
    */
   public void setMaxGCoverExposureValidTo(Date aMaxGCoverExposureValidTo) 
   {
      maxGCoverExposureValidTo = aMaxGCoverExposureValidTo;
   }

   /**
    * Access method for the minSanctionedAmount property.
    * 
    * @return   the current value of the minSanctionedAmount property
    */
   public double getMinSanctionedAmount() 
   {
      return minSanctionedAmount;
   }
   
   /**
    * Sets the value of the minSanctionedAmount property.
    * 
    * @param aMinSanctionedAmount the new value of the minSanctionedAmount property
    */
   public void setMinSanctionedAmount(double aMinSanctionedAmount) 
   {
      minSanctionedAmount = aMinSanctionedAmount;
   }

   /**
    * Access method for the minSanctionedAmtValidFrom property.
    * 
    * @return   the current value of the minSanctionedAmtValidFrom property
    */
   public Date getMinSanctionedAmtValidFrom() 
   {
      return minSanctionedAmtValidFrom;
   }
   
   /**
    * Sets the value of the minSanctionedAmtValidFrom property.
    * 
    * @param aMinSanctionedAmtValidFrom the new value of the minSanctionedAmtValidFrom property
    */
   public void setMinSanctionedAmtValidFrom(Date aMinSanctionedAmtValidFrom) 
   {
      minSanctionedAmtValidFrom = aMinSanctionedAmtValidFrom;
   }

   /**
    * Access method for the minSanctionedAmtValidTo property.
    * 
    * @return   the current value of the minSanctionedAmtValidTo property
    */
   public Date getMinSanctionedAmtValidTo() 
   {
      return minSanctionedAmtValidTo;
   }
   
   /**
    * Sets the value of the minSanctionedAmtValidTo property.
    * 
    * @param aMinSanctionedAmtValidTo the new value of the minSanctionedAmtValidTo property
    */
   public void setMinSanctionedAmtValidTo(Date aMinSanctionedAmtValidTo) 
   {
      minSanctionedAmtValidTo = aMinSanctionedAmtValidTo;
   }

   /**
    * Access method for the maxSanctionedAmount property.
    * 
    * @return   the current value of the maxSanctionedAmount property
    */
   public double getMaxSanctionedAmount() 
   {
      return maxSanctionedAmount;
   }
   
   /**
    * Sets the value of the maxSanctionedAmount property.
    * 
    * @param aMaxSanctionedAmount the new value of the maxSanctionedAmount property
    */
   public void setMaxSanctionedAmount(double aMaxSanctionedAmount) 
   {
      maxSanctionedAmount = aMaxSanctionedAmount;
   }

   /**
    * Access method for the maxSanctionedAmtValidFrom property.
    * 
    * @return   the current value of the maxSanctionedAmtValidFrom property
    */
   public Date getMaxSanctionedAmtValidFrom() 
   {
      return maxSanctionedAmtValidFrom;
   }
   
   /**
    * Sets the value of the maxSanctionedAmtValidFrom property.
    * 
    * @param aMaxSanctionedAmtValidFrom the new value of the maxSanctionedAmtValidFrom property
    */
   public void setMaxSanctionedAmtValidFrom(Date aMaxSanctionedAmtValidFrom) 
   {
      maxSanctionedAmtValidFrom = aMaxSanctionedAmtValidFrom;
   }

   /**
    * Access method for the maxSanctionedAmtValidTo property.
    * 
    * @return   the current value of the maxSanctionedAmtValidTo property
    */
   public Date getMaxSanctionedAmtValidTo() 
   {
      return maxSanctionedAmtValidTo;
   }
   
   /**
    * Sets the value of the maxSanctionedAmtValidTo property.
    * 
    * @param aMaxSanctionedAmtValidTo the new value of the maxSanctionedAmtValidTo property
    */
   public void setMaxSanctionedAmtValidTo(Date aMaxSanctionedAmtValidTo) 
   {
      maxSanctionedAmtValidTo = aMaxSanctionedAmtValidTo;
   }

   /**
    * Access method for the maxBorrowerExposureAmount property.
    * 
    * @return   the current value of the maxBorrowerExposureAmount property
    */
   public double getMaxBorrowerExposureAmount() 
   {
      return maxBorrowerExposureAmount;
   }
   
   /**
    * Sets the value of the maxBorrowerExposureAmount property.
    * 
    * @param aMaxBorrowerExposureAmount the new value of the maxBorrowerExposureAmount property
    */
   public void setMaxBorrowerExposureAmount(double aMaxBorrowerExposureAmount) 
   {
      maxBorrowerExposureAmount = aMaxBorrowerExposureAmount;
   }

   /**
    * Access method for the maxBorrowerExpAmtValidFrom property.
    * 
    * @return   the current value of the maxBorrowerExpAmtValidFrom property
    */
   public Date getMaxBorrowerExpAmtValidFrom() 
   {
      return maxBorrowerExpAmtValidFrom;
   }
   
   /**
    * Sets the value of the maxBorrowerExpAmtValidFrom property.
    * 
    * @param aMaxBorrowerExpAmtValidFrom the new value of the maxBorrowerExpAmtValidFrom property
    */
   public void setMaxBorrowerExpAmtValidFrom(Date aMaxBorrowerExpAmtValidFrom) 
   {
      maxBorrowerExpAmtValidFrom = aMaxBorrowerExpAmtValidFrom;
   }

   /**
    * Access method for the maxBorrowerExpAmtValidTo property.
    * 
    * @return   the current value of the maxBorrowerExpAmtValidTo property
    */
   public Date getMaxBorrowerExpAmtValidTo() 
   {
      return maxBorrowerExpAmtValidTo;
   }
   
   /**
    * Sets the value of the maxBorrowerExpAmtValidTo property.
    * 
    * @param aMaxBorrowerExpAmtValidTo the new value of the maxBorrowerExpAmtValidTo property
    */
   public void setMaxBorrowerExpAmtValidTo(Date aMaxBorrowerExpAmtValidTo) 
   {
      maxBorrowerExpAmtValidTo = aMaxBorrowerExpAmtValidTo;
   }

   /**
    * Access method for the guaranteeCoverExtent property.
    * 
    * @return   the current value of the guaranteeCoverExtent property
    */
   public double getGuaranteeCoverExtent() 
   {
      return guaranteeCoverExtent;
   }
   
   /**
    * Sets the value of the guaranteeCoverExtent property.
    * 
    * @param aGuaranteeCoverExtent the new value of the guaranteeCoverExtent property
    */
   public void setGuaranteeCoverExtent(double aGuaranteeCoverExtent) 
   {
      guaranteeCoverExtent = aGuaranteeCoverExtent;
   }

   /**
    * Access method for the guaranteeCoverExtentValidFrom property.
    * 
    * @return   the current value of the guaranteeCoverExtentValidFrom property
    */
   public Date getGuaranteeCoverExtentValidFrom() 
   {
      return guaranteeCoverExtentValidFrom;
   }
   
   /**
    * Sets the value of the guaranteeCoverExtentValidFrom property.
    * 
    * @param aGCoverExtentValidFrom the new value of the guaranteeCoverExtentValidFrom property
    */
   public void setGuaranteeCoverExtentValidFrom(Date aGCoverExtentValidFrom) 
   {
      guaranteeCoverExtentValidFrom = aGCoverExtentValidFrom;
   }

   /**
    * Access method for the guaranteeCoverExtentValidTo property.
    * 
    * @return   the current value of the guaranteeCoverExtentValidTo property
    */
   public Date getGuaranteeCoverExtentValidTo() 
   {
      return guaranteeCoverExtentValidTo;
   }
   
   /**
    * Sets the value of the guaranteeCoverExtentValidTo property.
    * 
    * @param aGCoverExtentValidTo the new value of the guaranteeCoverExtentValidTo property
    */
   public void setGuaranteeCoverExtentValidTo(Date aGCoverExtentValidTo) 
   {
      guaranteeCoverExtentValidTo = aGCoverExtentValidTo;
   }

   /**
    * Access method for the serviceFeeCardRate property.
    * 
    * @return   the current value of the serviceFeeCardRate property
    */
   public double getServiceFeeCardRate() 
   {
      return serviceFeeCardRate;
   }
   
   /**
    * Sets the value of the serviceFeeCardRate property.
    * 
    * @param aServiceFeeCardRate the new value of the serviceFeeCardRate property
    */
   public void setServiceFeeCardRate(double aServiceFeeCardRate) 
   {
      serviceFeeCardRate = aServiceFeeCardRate;
   }

   /**
    * Access method for the serviceFeeCardRateValidFrom property.
    * 
    * @return   the current value of the serviceFeeCardRateValidFrom property
    */
   public Date getServiceFeeCardRateValidFrom() 
   {
      return serviceFeeCardRateValidFrom;
   }
   
   /**
    * Sets the value of the serviceFeeCardRateValidFrom property.
    * 
    * @param aServiceFeeCardRateValidFrom the new value of the serviceFeeCardRateValidFrom property
    */
   public void setServiceFeeCardRateValidFrom(Date aServiceFeeCardRateValidFrom) 
   {
      serviceFeeCardRateValidFrom = aServiceFeeCardRateValidFrom;
   }

   /**
    * Access method for the serviceFeeCardRateValidTo property.
    * 
    * @return   the current value of the serviceFeeCardRateValidTo property
    */
   public Date getServiceFeeCardRateValidTo() 
   {
      return serviceFeeCardRateValidTo;
   }
   
   /**
    * Sets the value of the serviceFeeCardRateValidTo property.
    * 
    * @param aServiceFeeCardRateValidTo the new value of the serviceFeeCardRateValidTo property
    */
   public void setServiceFeeCardRateValidTo(Date aServiceFeeCardRateValidTo) 
   {
      serviceFeeCardRateValidTo = aServiceFeeCardRateValidTo;
   }

   /**
    * Access method for the guaranteeFeeCardRate property.
    * 
    * @return   the current value of the guaranteeFeeCardRate property
    */
   public double getGuaranteeFeeCardRate() 
   {
      return guaranteeFeeCardRate;
   }
   
   /**
    * Sets the value of the guaranteeFeeCardRate property.
    * 
    * @param aGuaranteeFeeCardRate the new value of the guaranteeFeeCardRate property
    */
   public void setGuaranteeFeeCardRate(double aGuaranteeFeeCardRate) 
   {
      guaranteeFeeCardRate = aGuaranteeFeeCardRate;
   }

   /**
    * Access method for the guaranteeFeeCardRateValidFrom property.
    * 
    * @return   the current value of the guaranteeFeeCardRateValidFrom property
    */
   public Date getGuaranteeFeeCardRateValidFrom() 
   {
      return guaranteeFeeCardRateValidFrom;
   }
   
   /**
    * Sets the value of the guaranteeFeeCardRateValidFrom property.
    * 
    * @param aGFeeCardRateValidFrom the new value of the guaranteeFeeCardRateValidFrom property
    */
   public void setGuaranteeFeeCardRateValidFrom(Date aGFeeCardRateValidFrom) 
   {
      guaranteeFeeCardRateValidFrom = aGFeeCardRateValidFrom;
   }

   /**
    * Access method for the guaranteeFeeCardRateValidTo property.
    * 
    * @return   the current value of the guaranteeFeeCardRateValidTo property
    */
   public Date getGuaranteeFeeCardRateValidTo() 
   {
      return guaranteeFeeCardRateValidTo;
   }
   
   /**
    * Sets the value of the guaranteeFeeCardRateValidTo property.
    * 
    * @param aguaranteeFeeCardRateValidTo the new value of the guaranteeFeeCardRateValidTo property
    */
   public void setGuaranteeFeeCardRateValidTo(Date aguaranteeFeeCardRateValidTo) 
   {
      guaranteeFeeCardRateValidTo = aguaranteeFeeCardRateValidTo;
   }

   /**
    * Access method for the defaultRateApplicable property.
    * 
    * @return   the current value of the defaultRateApplicable property
    */
   public String getDefaultRateApplicable() 
   {
      return defaultRateApplicable;
   }
   
   /**
    * Sets the value of the defaultRateApplicable property.
    * 
    * @param aDefaultRateApplicable the new value of the defaultRateApplicable property
    */
   public void setDefaultRateApplicable(String aDefaultRateApplicable) 
   {
      defaultRateApplicable = aDefaultRateApplicable;
   }

   /**
    * Access method for the defRateApplicableValidFrom property.
    * 
    * @return   the current value of the defRateApplicableValidFrom property
    */
   public Date getDefRateApplicableValidFrom() 
   {
      return defRateApplicableValidFrom;
   }
   
   /**
    * Sets the value of the defRateApplicableValidFrom property.
    * 
    * @param aDefRateApplicableValidFrom the new value of the defRateApplicableValidFrom property
    */
   public void setDefRateApplicableValidFrom(Date aDefRateApplicableValidFrom) 
   {
      defRateApplicableValidFrom = aDefRateApplicableValidFrom;
   }

   /**
    * Access method for the defRateApplicableValidTo property.
    * 
    * @return   the current value of the defRateApplicableValidTo property
    */
   public Date getDefRateApplicableValidTo() 
   {
      return defRateApplicableValidTo;
   }
   
   /**
    * Sets the value of the defRateApplicableValidTo property.
    * 
    * @param aDefRateApplicableValidTo the new value of the defRateApplicableValidTo property
    */
   public void setDefRateApplicableValidTo(Date aDefRateApplicableValidTo) 
   {
      defRateApplicableValidTo = aDefRateApplicableValidTo;
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
    * Access method for the defRateValidFrom property.
    * 
    * @return   the current value of the defRateValidFrom property
    */
   public Date getDefRateValidFrom() 
   {
      return defRateValidFrom;
   }
   
   /**
    * Sets the value of the defRateValidFrom property.
    * 
    * @param aDefRateValidFrom the new value of the defRateValidFrom property
    */
   public void setDefRateValidFrom(Date aDefRateValidFrom) 
   {
      defRateValidFrom = aDefRateValidFrom;
   }

   /**
    * Access method for the defRateValidTo property.
    * 
    * @return   the current value of the defRateValidTo property
    */
   public Date getDefRateValidTo() 
   {
      return defRateValidTo;
   }
   
   /**
    * Sets the value of the defRateValidTo property.
    * 
    * @param aDefRateValidTo the new value of the defRateValidTo property
    */
   public void setDefRateValidTo(Date aDefRateValidTo) 
   {
      defRateValidTo = aDefRateValidTo;
   }

   /**
    * Access method for the moderationFactor property.
    * 
    * @return   the current value of the moderationFactor property
    */
   public double getModerationFactor() 
   {
      return moderationFactor;
   }
   
   /**
    * Sets the value of the moderationFactor property.
    * 
    * @param aModerationFactor the new value of the moderationFactor property
    */
   public void setModerationFactor(double aModerationFactor) 
   {
      moderationFactor = aModerationFactor;
   }

   /**
    * Access method for the moderationFactorValidFrom property.
    * 
    * @return   the current value of the moderationFactorValidFrom property
    */
   public Date getModerationFactorValidFrom() 
   {
      return moderationFactorValidFrom;
   }
   
   /**
    * Sets the value of the moderationFactorValidFrom property.
    * 
    * @param aModerationFactorValidFrom the new value of the moderationFactorValidFrom property
    */
   public void setModerationFactorValidFrom(Date aModerationFactorValidFrom) 
   {
      moderationFactorValidFrom = aModerationFactorValidFrom;
   }

   /**
    * Access method for the moderationFactorValidTo property.
    * 
    * @return   the current value of the moderationFactorValidTo property
    */
   public Date getModerationFactorValidTo() 
   {
      return moderationFactorValidTo;
   }
   
   /**
    * Sets the value of the moderationFactorValidTo property.
    * 
    * @param aModerationFactorValidTo the new value of the moderationFactorValidTo property
    */
   public void setModerationFactorValidTo(Date aModerationFactorValidTo) 
   {
      moderationFactorValidTo = aModerationFactorValidTo;
   }

}