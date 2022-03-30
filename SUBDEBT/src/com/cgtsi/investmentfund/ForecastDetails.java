package com.cgtsi.investmentfund;

/*************************************************************
   *
   * Name of the class: ForecastDetails
   * This class captures the budget details. There is a flag in ForecastDetails to
   * specify if the ForecastDetail is Inflow or Outflow. There is also a flag to
   * specify if the Forecast Detail is for an Annual forecast or Short Term Budget.
   *
   * The objForecastHeadTitles is an ArrayList of Forecast Heads.
   *
   * @author : Nithyalakshmi P
   * @version:
   * @since:
   **************************************************************/

import java.util.ArrayList;

public class ForecastDetails
{
   private String month;
   private String year;
   private String annualOrShortTerm;
   private String startDate;								//Included 12-09-2003
   private String endDate;									//Included 12-09-2003

   /**
    * This attribute is a collection of ForecastHeadDetail objects. A ForecastHeadDetail
    * object encapsulates the budget title and the budgeted amount or it may
    * encapsulate a budget head title, and budget sub head details.
    */
   private ArrayList forecastHeadDetails;
   private String inflowOrOutflow;
   private String modifiedBy;

   public ForecastDetails(){ }

   /**
    * Access method for the forecastHeadDetails property.
    *
    * @return   the current value of the forecastHeadDetails property
    */

   public ArrayList getForecastHeadDetails(){
      return forecastHeadDetails;
   }

   /**
    * Sets the value of the forecastHeadDetails property.
    *
    * @param aForecastHeadDetails the new value of the forecastHeadDetails property
    */
   public void setForecastHeadDetails(ArrayList aForecastHeadDetails)
   {
      forecastHeadDetails = aForecastHeadDetails;
   }

   /**
    * Access method for the inflowOrOutflow property.
    *
    * @return   the current value of the inflowOrOutflow property
    */
   public String getInflowOrOutflow()
   {
      return inflowOrOutflow;
   }

   /**
    * Sets the value of the inflowOrOutflow property.
    *
    * @param aInflowOrOutflow the new value of the inflowOrOutflow property
    */
   public void setInflowOrOutflow(String aInflowOrOutflow)
   {
      inflowOrOutflow = aInflowOrOutflow;
   }

   /**
    * Access method for the startDate property.
    *
    * @return   the current value of the startDate property
    */
   public String getStartDate()
   {
      return startDate;
   }

   /**
    * Sets the value of the startDate property.
    *
    * @param aStartDate the new value of the startDate property
    */
   public void setStartDate(String aStartDate)
   {
      startDate = aStartDate;
   }

   /**
    * Access method for the endDate property.
    *
    * @return   the current value of the endDate property
    */
   public String getEndDate()
   {
      return endDate;
   }

   /**
    * Sets the value of the endDate property.
    *
    * @param aInflowOrOutflow the new value of the endDate property
    */
   public void setEndDate(String aEndDate)
   {
      endDate = aEndDate;
   }

   public String getModifiedBy()
   {
	   return modifiedBy;
   }

   public void setModifiedBy(String aModifiedBy)
   {
	   modifiedBy = aModifiedBy;
   }
}
