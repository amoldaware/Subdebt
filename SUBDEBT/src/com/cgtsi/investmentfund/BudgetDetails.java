package com.cgtsi.investmentfund;

/*************************************************************
   *
   * Name of the class: BudgetDetails
   * This class captures the budget details. There is a flag in BudgetDetails to
   * specify if the BudgetDetail is Inflow or Outflow. There is also a flag to
   * specify if the Budget Detail is for an Annual Budget or Short Term Budget.
   *
   * The objBudgetHeadTitles is an ArrayList of Budget Heads.
   *
   * @author : Nithyalakshmi P
   * @version:
   * @since:
   **************************************************************/

import java.util.ArrayList;

public class BudgetDetails
{
   private String month;
   private String year;
   private String annualOrShortTerm;
   private String annualFromDate;								//Included 12-09-2003
   private String annualToDate;									//Included 12-09-2003

   /**
    * This attribute is a collection of BudgetHeadDetail objects. A BudgetHeadDetail
    * object encapsulates the budget title and the budgeted amount or it may
    * encapsulate a budget head title, and budget sub head details.
    */
   private ArrayList budgetHeadDetails;
   private String inflowOrOutflow;
   private String modifiedBy;

   public BudgetDetails()
   {

   }

   /**
    * Access method for the month property.
    *
    * @return   the current value of the month property
    */
   public String getMonth()
   {
      return month;
   }

   /**
    * Sets the value of the month property.
    *
    * @param aMonth the new value of the month property
    */
   public void setMonth(String aMonth)
   {
      month = aMonth;
   }

   /**
    * Access method for the year property.
    *
    * @return   the current value of the year property
    */
   public String getYear()
   {
      return year;
   }

   /**
    * Sets the value of the year property.
    *
    * @param aYear the new value of the year property
    */
   public void setYear(String aYear)
   {
      year = aYear;
   }

   /**
    * Access method for the annualOrShortTerm property.
    *
    * @return   the current value of the annualOrShortTerm property
    */
   public String getAnnualOrShortTerm()
   {
      return annualOrShortTerm;
   }

   /**
    * Sets the value of the annualOrShortTerm property.
    *
    * @param aAnnualOrShortTerm the new value of the annualOrShortTerm property
    */
   public void setAnnualOrShortTerm(String aAnnualOrShortTerm)
   {
      annualOrShortTerm = aAnnualOrShortTerm;
   }

   /**
    * Access method for the budgetHeadDetails property.
    *
    * @return   the current value of the budgetHeadDetails property
    */
   public ArrayList getBudgetHeadDetails()
   {
      return budgetHeadDetails;
   }

   /**
    * Sets the value of the budgetHeadDetails property.
    *
    * @param aBudgetHeadDetails the new value of the budgetHeadDetails property
    */
   public void setBudgetHeadDetails(ArrayList aBudgetHeadDetails)
   {
      budgetHeadDetails = aBudgetHeadDetails;
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
    * Access method for the annualFromDate property.
    *
    * @return   the current value of the annualFromDate property
    */
   public String getAnnualFromDate()
   {
      return annualFromDate;
   }

   /**
    * Sets the value of the annualFromDate property.
    *
    * @param aAnnualFromDate the new value of the annualFromDate property
    */
   public void setAnnualFromDate(String aAnnualFromDate)
   {
      annualFromDate = aAnnualFromDate;
   }

   /**
    * Access method for the annualToDate property.
    *
    * @return   the current value of the annualToDate property
    */
   public String getAnnualToDate()
   {
      return annualToDate;
   }

   /**
    * Sets the value of the annualToDate property.
    *
    * @param aInflowOrOutflow the new value of the annualToDate property
    */
   public void setAnnualToDate(String aAnnualToDate)
   {
      annualToDate = aAnnualToDate;
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
