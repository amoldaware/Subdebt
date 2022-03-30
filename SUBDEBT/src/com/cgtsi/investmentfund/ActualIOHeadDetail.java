//Source file: F:\\CGTSI\\Design\\DesignModel\\com\\cgtsi\\investmentfund\\ActualInflowOrOutflowHeadDetails.java

package com.cgtsi.investmentfund;

import java.util.ArrayList;

public class ActualIOHeadDetail
{
   private java.lang.String budgetHead;
   private double budgetAmount;
   private ArrayList actualIOSubHeadDetails;
   private int id;
   private String remarks;		

   public ActualIOHeadDetail()
   {

   }

   /**
    * Access method for the budgetHead property.
    *
    * @return   the current value of the budgetHead property
    */
   public java.lang.String getBudgetHead()
   {
      return budgetHead;
   }

   /**
    * Sets the value of the budgetHead property.
    *
    * @param budgetHead the new value of the budgetHead property
    */
   public void setBudgetHead(java.lang.String budgetHead)
   {
      this.budgetHead = budgetHead;
   }

   /**
    * Access method for the budgeAmount property.
    *
    * @return   the current value of the budgeAmount property
    */
   public double getBudgetAmount()
   {
      return budgetAmount;
   }

   /**
    * Sets the value of the budgeAmount property.
    *
    * @param budgeAmount the new value of the budgeAmount property
    */
   public void setBudgetAmount(double aBudgetAmount)
   {
      budgetAmount = aBudgetAmount;
   }

	/**
    * Access method for the budgetSubHeadDetails property.
    *
    * @return   the current value of the budgetSubHeadDetails property
    */
   public ArrayList getActualIOSubHeadDetails()
   {
      return actualIOSubHeadDetails;
   }

   /**
    * Sets the value of the budgetSubHeadDetails property.
    *
    * @param aBudgetSubHeadDetails the new value of the budgetSubHeadDetails property
    */
   public void setActualIOSubHeadDetails(ArrayList aActualIOSubHeadDetails)
   {
      actualIOSubHeadDetails = aActualIOSubHeadDetails;
   }

/**
 * @return
 */
public int getId() {
	return id;
}

/**
 * @param i
 */
public void setId(int i) {
	id = i;
}

/**
 * @return
 */
public String getRemarks() {
	return remarks;
}

/**
 * @param string
 */
public void setRemarks(String string) {
	remarks = string;
}

}
