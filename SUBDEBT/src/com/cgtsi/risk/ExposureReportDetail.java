//Source file: F:\\CGTSI\\Design\\DesignModel\\com\\cgtsi\\risk\\ExposureReportDetail.java

package com.cgtsi.risk;

import java.util.ArrayList;

public class ExposureReportDetail 
{
   private ParameterCombination inputParameters;
   private ArrayList tcExposureDetails;
   private ArrayList wcExposureDetails;
   
   /**
    * @roseuid 39E6CD7A022B
    */
   public ExposureReportDetail() 
   {
    
   }
   
   /**
    * Access method for the inputParameters property.
    * 
    * @return   the current value of the inputParameters property
    */
   public ParameterCombination getInputParameters() 
   {
      return inputParameters;
   }
   
   /**
    * Sets the value of the inputParameters property.
    * 
    * @param aInputParameters the new value of the inputParameters property
    */
   public void setInputParameters(ParameterCombination aInputParameters) 
   {
      inputParameters = aInputParameters;
   }

   /**
    * Access method for the tcExposureDetails property.
    * 
    * @return   the current value of the tcExposureDetails property
    */
   public ArrayList getTcExposureDetails() 
   {
      return tcExposureDetails;
   }
   
   /**
    * Sets the value of the tcExposureDetails property.
    * 
    * @param aTcExposureDetails the new value of the tcExposureDetails property
    */
   public void setTcExposureDetails(ArrayList aTcExposureDetails) 
   {
      tcExposureDetails = aTcExposureDetails;
   }

   /**
    * Access method for the wcExposureDetails property.
    * 
    * @return   the current value of the wcExposureDetails property
    */
   public ArrayList getWcExposureDetails() 
   {
      return wcExposureDetails;
   }
   
   /**
    * Sets the value of the wcExposureDetails property.
    * 
    * @param aWcExposureDetails the new value of the wcExposureDetails property
    */
   public void setWcExposureDetails(ArrayList aWcExposureDetails) 
   {
      wcExposureDetails = aWcExposureDetails;
   }
}
