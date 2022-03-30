package com.cgtsi.investmentfund;

/*************************************************************
   *
   * Name of the class: ForecastingDetail
   * This class encapsulates the forecasting details. Forecasting  can be done for
   * specific periods.
   *
   * @author : Nithyalakshmi P
   * @version:
   * @since:
   **************************************************************/

public class ForecastingDetail
{
   private java.lang.String sBudgetHead;
   private java.lang.String sBudgetSubHead;
   private String forecastedAmount;
   private String forecastingStartDate;
   private String forecastingEndDate;

   public ForecastingDetail()
   {

   }

   /**
    * Access method for the sBudgetHead property.
    *
    * @return   the current value of the sBudgetHead property
    */
   public java.lang.String getBudgetHead()
   {
      return sBudgetHead;
   }

   /**
    * Sets the value of the sBudgetHead property.
    *
    * @param aSBudgetHeadId the new value of the sBudgetHead property
    */
   public void setBudgetHead(java.lang.String aSBudgetHeadId)
   {
      sBudgetHead = aSBudgetHeadId;
   }

   /**
    * Access method for the sBudgetSubHead property.
    *
    * @return   the current value of the sBudgetSubHead property
    */
   public java.lang.String getBudgetSubHead()
   {
      return sBudgetSubHead;
   }

   /**
    * Sets the value of the sBudgetSubHead property.
    *
    * @param aSBudgetSubHeadId the new value of the sBudgetSubHead property
    */
   public void setBudgetSubHead(java.lang.String aSBudgetSubHeadId)
   {
      sBudgetSubHead = aSBudgetSubHeadId;
   }

   /**
    * Access method for the forecastedAmount property.
    *
    * @return   the current value of the forecastedAmount property
    */
   public String getForecastedAmount()
   {
      return forecastedAmount;
   }

   /**
    * Sets the value of the forecastedAmount property.
    *
    * @param aDForecastedAmount the new value of the forecastedAmount property
    */
   public void setForecastedAmount(String aDForecastedAmount)
   {
      forecastedAmount = aDForecastedAmount;
   }

   /**
    * Access method for the forecastingStartDate property.
    *
    * @return   the current value of the forecastingStartDate property
    */
   public String getForecastingStartDate()
   {
      return forecastingStartDate;
   }

   /**
    * Sets the value of the forecastingStartDate property.
    *
    * @param aDForecastingStartDate the new value of the forecastingStartDate property
    */
   public void setForecastingStartDate(String aDForecastingStartDate)
   {
      forecastingStartDate = aDForecastingStartDate;
   }

   /**
    * Access method for the forecastingEndDate property.
    *
    * @return   the current value of the forecastingEndDate property
    */
   public String getForecastingEndDate()
   {
      return forecastingEndDate;
   }

   /**
    * Sets the value of the forecastingEndDate property.
    *
    * @param aDForecastingEndDate the new value of the forecastingEndDate property
    */
   public void setForecastingEndDate(String aDForecastingEndDate)
   {
      forecastingEndDate = aDForecastingEndDate;
   }
}
