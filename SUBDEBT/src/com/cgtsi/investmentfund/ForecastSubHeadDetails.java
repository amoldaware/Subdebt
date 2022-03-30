package com.cgtsi.investmentfund;

/*************************************************************
   *
   * Name of the class: ForecastSubHeadDetails
   * This class contains the budgetary info for the budget head titles. i.e for the 
   * values stored in the BUDGET_SUB_HEAD master table.
   *
   * @author : Nithyalakshmi P
   * @version: 
   * @since: 
   **************************************************************/

public class ForecastSubHeadDetails 
{
   private String subHeadTitle;
   private double forecastAmount;
   
   public ForecastSubHeadDetails(){}
   
   /**
    * Access method for the subHeadTitle property.
    * 
    * @return   the current value of the subHeadTitle property
    */
   public String getSubHeadTitle() 
   {
      return subHeadTitle;
   }
   
   /**
    * Sets the value of the subHeadTitle property.
    * 
    * @param aSubHeadTitle the new value of the subHeadTitle property
    */
   public void setSubHeadTitle(String aSubHeadTitle) 
   {
      subHeadTitle = aSubHeadTitle;
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
    * @param aBudgetAmount the new value of the forecastAmount property
    */
   public void setForecastAmount(double aForecastAmount) 
   {
      forecastAmount = aForecastAmount;
   }
}
