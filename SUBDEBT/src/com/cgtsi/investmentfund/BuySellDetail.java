package com.cgtsi.investmentfund;

/*************************************************************
   *
   * Name of the class: BuySellDetail
   * This class encapsulates the information of buying and selling transactions
   * requested by CGTSI.
   *
   * @author : Nithyalakshmi P
   * @version:
   * @since:
   **************************************************************/

public class BuySellDetail
{
   private String investmentRefNumber;
   private String isBuyOrSellRequest;
   private String noOfUnits;
   private String worthOfUnits;
   private String requestStatus;
   private String instrumentName;
   private String investeeName;
   private String modifiedBy;

   public BuySellDetail(){}

   /**
    * Access method for the investmentRefNumber property.
    *
    * @return   the current value of the investmentRefNumber property
    */
   public String getInvestmentRefNumber()
   {
      return investmentRefNumber;
   }

   /**
    * Sets the value of the investmentRefNumber property.
    *
    * @param investmentRefNumber the new value of the investmentRefNumber property
    */
   public void setInvestmentRefNumber(String investmentRefNumber)
   {
      this.investmentRefNumber = investmentRefNumber;
   }

   /**
    * Access method for the isBuyOrSellRequest property.
    *
    * @return   the current value of the isBuyOrSellRequest property
    */
   public String getIsBuyOrSellRequest()
   {
      return isBuyOrSellRequest;
   }

   /**
    * Sets the value of the isBuyOrSellRequest property.
    *
    * @param aIsBuyOrSellRequest the new value of the isBuyOrSellRequest property
    */
   public void setIsBuyOrSellRequest(String aIsBuyOrSellRequest)
   {
      isBuyOrSellRequest = aIsBuyOrSellRequest;
   }

   /**
    * Access method for the noOfUnits property.
    *
    * @return   the current value of the noOfUnits property
    */
   public String getNoOfUnits()
   {
      return noOfUnits;
   }

   /**
    * Sets the value of the noOfUnits property.
    *
    * @param aNoOfUnits the new value of the noOfUnits property
    */
   public void setNoOfUnits(String aNoOfUnits)
   {
      noOfUnits = aNoOfUnits;
   }

   /**
    * Access method for the worthOfUnits property.
    *
    * @return   the current value of the worthOfUnits property
    */
   public String getWorthOfUnits()
   {
      return worthOfUnits;
   }

   /**
    * Sets the value of the worthOfUnits property.
    *
    * @param aWorthOfUnits the new value of the worthOfUnits property
    */
   public void setWorthOfUnits(String aWorthOfUnits)
   {
      worthOfUnits = aWorthOfUnits;
   }

   /**
    * Access method for the requestStatus property.
    *
    * @return   the current value of the requestStatus property
    */
   public String getRequestStatus()
   {
      return requestStatus;
   }

   /**
    * Sets the value of the requestStatus property.
    *
    * @param aRequestStatus the new value of the requestStatus property
    */
   public void setRequestStatus(String aRequestStatus)
   {
      requestStatus = aRequestStatus;
   }

   /**
    * Access method for the instrumentName property.
    *
    * @return   the current value of the instrumentName property
    */
   public String getInstrumentName()
   {
      return instrumentName;
   }

   /**
    * Sets the value of the instrumentName property.
    *
    * @param aInstrumentName the new value of the instrumentName property
    */
   public void setInstrumentName(String aInstrumentName)
   {
      instrumentName = aInstrumentName;
   }

   public String getInvesteeName()
   {
	   return investeeName;
   }

   public void setInvesteeName(String investee)
   {
	   investeeName = investee;
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
