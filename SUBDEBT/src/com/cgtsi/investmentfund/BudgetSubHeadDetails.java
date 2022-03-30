package com.cgtsi.investmentfund;

/*************************************************************
   *
   * Name of the class: BudgetSubHeadDetails
   * This class contains the budgetary info for the budget head titles. i.e for the 
   * values stored in the BUDGET_SUB_HEAD master table.
   *
   * @author : Nithyalakshmi P
   * @version: 
   * @since: 
   **************************************************************/

public class BudgetSubHeadDetails 
{
   private String subHeadTitle;
   private double budgetAmount;
   
   public BudgetSubHeadDetails() 
   {    
   }
   
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
    * Access method for the budgetAmount property.
    * 
    * @return   the current value of the budgetAmount property
    */
   public double getBudgetAmount() 
   {
      return budgetAmount;
   }
   
   /**
    * Sets the value of the budgetAmount property.
    * 
    * @param aBudgetAmount the new value of the budgetAmount property
    */
   public void setBudgetAmount(double aBudgetAmount) 
   {
      budgetAmount = aBudgetAmount;
   }
}
