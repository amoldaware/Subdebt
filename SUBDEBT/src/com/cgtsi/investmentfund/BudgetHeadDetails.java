//Source file: F:\\CGTSI\\Design\\DesignModel\\com\\cgtsi\\investmentfund\\BudgetHeadDetails.java

package com.cgtsi.investmentfund;

/*************************************************************
   *
   * Name of the class: BudgetHeadDetails
   * This class contains the budgetary info for the budget head titles. i.e for the
   * values stored in the BUDGET_HEAD master table.
   *
   * @author : Nithyalakshmi P
   * @version:
   * @since:
   **************************************************************/

import java.util.ArrayList;

public class BudgetHeadDetails
{
   private String budgetHead;
   private double budgetAmount;
   private ArrayList budgetSubHeadDetails;

   public BudgetHeadDetails()
   {}

   /**
    * Access method for the budgetHead property.
    *
    * @return   the current value of the budgetHead property
    */
   public String getBudgetHead()
   {
      return budgetHead;
   }

   /**
    * Sets the value of the budgetHead property.
    *
    * @param aBudgetHead the new value of the budgetHead property
    */
   public void setBudgetHead(String budgetHead)
   {
      this.budgetHead = budgetHead;
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
   public void setBudgetAmount(double budgetAmount)
   {
      this.budgetAmount = budgetAmount;
   }

   /**
    * Access method for the budgetSubHeadDetails property.
    *
    * @return   the current value of the budgetSubHeadDetails property
    */
   public ArrayList getBudgetSubHeadDetails()
   {
      return budgetSubHeadDetails;
   }

   /**
    * Sets the value of the budgetSubHeadDetails property.
    *
    * @param aBudgetSubHeadDetails the new value of the budgetSubHeadDetails property
    */
   public void setBudgetSubHeadDetails(ArrayList aBudgetSubHeadDetails)
   {
      budgetSubHeadDetails = aBudgetSubHeadDetails;
   }
}
