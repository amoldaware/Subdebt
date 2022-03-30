package com.cgtsi.investmentfund;

/*************************************************************
   *
   * Name of the class: ActualInflowOutflowDetails
   * This class encapsulates the actual Inflow and Outflow details. There is a flag
   * in this class to indicate if the detail is an Inflow or an Outflow. There is
   * also a flag to indicate if the detail is annual or short term.
   *
   * @author : Nithyalakshmi P
   * @version:
   * @since:
   **************************************************************/

import java.util.ArrayList;

public class ActualInflowOutflowDetails
{
   private String sMonth;
   private String sYear;
   private String sIsAnnualOrShortTerm;
   private java.util.Vector actualIOHeads;
   private String isInflowOrOutflow;
   private String dateOfFlow;
   private String modifiedBy;

   private ArrayList actualIOHeadDetails;

   public ActualInflowOutflowDetails(){}

   /**
    * Access method for the sMonth property.
    *
    * @return   the current value of the sMonth property
    */
   public String getMonth()
   {
      return sMonth;
   }

   /**
    * Sets the value of the sMonth property.
    *
    * @param aSMonth the new value of the sMonth property
    */
   public void setMonth(String aSMonth)
   {
      sMonth = aSMonth;
   }

   /**
    * Access method for the sYear property.
    *
    * @return   the current value of the sYear property
    */
   public String getYear()
   {
      return sYear;
   }

   /**
    * Sets the value of the sYear property.
    *
    * @param aSYear the new value of the sYear property
    */
   public void setYear(String aSYear)
   {
      sYear = aSYear;
   }

   /**
    * Access method for the sIsAnnualOrShortTerm property.
    *
    * @return   the current value of the sIsAnnualOrShortTerm property
    */
   public String getIsAnnualOrShortTerm()
   {
      return sIsAnnualOrShortTerm;
   }

   /**
    * Sets the value of the sIsAnnualOrShortTerm property.
    *
    * @param aSIsAnnualOrShortTerm the new value of the sIsAnnualOrShortTerm property
    */
   public void setIsAnnualOrShortTerm(String aSIsAnnualOrShortTerm)
   {
      sIsAnnualOrShortTerm = aSIsAnnualOrShortTerm;
   }

   /**
    * Access method for the actualIOHeads property.
    *
    * @return   the current value of the actualIOHeads property
    */
   public java.util.Vector getActualIOHeads()
   {
      return actualIOHeads;
   }


	 /**
    * Access method for the budgetHeadDetails property.
    *
    * @return   the current value of the budgetHeadDetails property
    */
   public ArrayList getActualIOHeadDetails()
   {
      return actualIOHeadDetails;
   }

   /**
    * Sets the value of the budgetHeadDetails property.
    *
    * @param aBudgetHeadDetails the new value of the budgetHeadDetails property
    */
   public void setActualIOHeadDetails(ArrayList aActualIOHeadDetails)
   {
      actualIOHeadDetails = aActualIOHeadDetails;
   }

   /**
    * Sets the value of the actualIOHeads property.
    *
    * @param actualIOHeads the new value of the actualIOHeads property
    */
   public void setActualIOHeads(java.util.Vector actualIOHeads)
   {
      this.actualIOHeads = actualIOHeads;
   }

   /**
    * Access method for the isInflowOrOutflow property.
    *
    * @return   the current value of the isInflowOrOutflow property
    */
   public String getIsInflowOrOutflow()
   {
      return isInflowOrOutflow;
   }

   /**
    * Sets the value of the isInflowOrOutflow property.
    *
    * @param aSIsInflowOrOutflow the new value of the isInflowOrOutflow property
    */
   public void setIsInflowOrOutflow(String aSIsInflowOrOutflow)
   {
      isInflowOrOutflow = aSIsInflowOrOutflow;
   }

   public String getDateOfFlow()
   {
	   return dateOfFlow;
   }

   public void setDateOfFlow(String dateOfFlow)
   {
	   this.dateOfFlow = dateOfFlow;
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
