package com.cgtsi.investmentfund;

public class ActualIOSubHeadDetail
{
   private String subHeadTitle;
   private double budgetAmount;

   public ActualIOSubHeadDetail()
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
    * @param budgetSubHead the new value of the subHeadTitle property
    */
   public void setSubHeadTitle(String budgetSubHead)
   {
      subHeadTitle = budgetSubHead;
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
    * @param budgetAmount the new value of the budgetAmount property
    */
   public void setBudgetAmount(double budgetAmount)
   {
      this.budgetAmount = budgetAmount;
   }
}
