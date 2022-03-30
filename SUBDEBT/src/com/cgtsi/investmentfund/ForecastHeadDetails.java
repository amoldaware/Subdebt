//Source file: F:\\CGTSI\\Design\\DesignModel\\com\\cgtsi\\investmentfund\\ForecastHeadDetails.java

package com.cgtsi.investmentfund;

/*************************************************************
   *
   * Name of the class: ForecastHeadDetails
   * This class contains the budgetary info for the budget head titles. i.e for the
   * values stored in the BUDGET_HEAD master table.
   *
   * @author : Nithyalakshmi P
   * @version:
   * @since:
   **************************************************************/

import java.util.ArrayList;

public class ForecastHeadDetails
{
   private String forecastHead;
   private double forecastAmount;
   private ArrayList forecastSubHeadDetails;

   public ForecastHeadDetails(){}

   /**
    * Access method for the forecastHead property.
    *
    * @return   the current value of the forecastHead property
    */
   public String getForecastHead()
   {
      return forecastHead;
   }

   /**
    * Sets the value of the forecastHead property.
    *
    * @param aForecastHead the new value of the forecastHead property
    */
   public void setForecastHead(String forecastHead)
   {
      this.forecastHead = forecastHead;
   }

   /**
    * Access method for the forecastAmount property.
    *
    * @return   the current value of the forecastAmount property
    */
   public double getForecastAmount()
   {
      return forecastAmount;
   }

   /**
    * Sets the value of the forecastAmount property.
    *
    * @param aForecastAmount the new value of the forecastAmount property
    */
   public void setForecastAmount(double forecastAmount)
   {
      this.forecastAmount = forecastAmount;
   }

   /**
    * Access method for the forecastSubHeadDetails property.
    *
    * @return   the current value of the forecastSubHeadDetails property
    */
   public ArrayList getForecastSubHeadDetails()
   {
      return forecastSubHeadDetails;
   }

   /**
    * Sets the value of the forecastSubHeadDetails property.
    *
    * @param aForecastSubHeadDetails the new value of the forecastSubHeadDetails property
    */
   public void setForecastSubHeadDetails(ArrayList aForecastSubHeadDetails)
   {
      forecastSubHeadDetails = aForecastSubHeadDetails;
   }
}
