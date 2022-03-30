package com.cgtsi.investmentfund;

import java.util.Date;

/*************************************************************
   *
   * Name of the class: CommercialPaperDetail
   * This class encapsulates the various details of investment in  Commercial Paper.
   *
   * @author : Nithyalakshmi P
   * @version:
   * @since:
   **************************************************************/

public class CommercialPaperDetail
{
	private String investeeName;
	private String instrumentName;
	private String investmentName;

	private String commercialPaperNumber;
	private String nameOfCompany;
	private double faceValue;
	private int noOfCommercialPapers;
	private double costOfPurchase;
	private double couponRate;
	private int tenure;
	private Date dateOfInvestment;

	private String tenureType;
	private String maturityName;

	private Date maturityDate;
	private double maturityAmount;
	private String callOrPutOption;

	private int callOrPutDuration;
	
	private String[] instrumentFeature;

	private String investmentReferenceNumber;

	private String modifiedBy;
	
	private String rating;
	private String agency;
	
	private String instrumentCategory;
	private double ytmValue;


	public CommercialPaperDetail()
	{

	}

	/**
	* Access method for the investeeName property.
	* 
	* @return   the current value of the investeeName property
	*/
	public String getInvesteeName() 
	{
	  return investeeName;    
	}

	/**
	* Sets the value of the investeeName property.
	* 
	* @param aInvesteeName the new value of the investeeName property
	*/
	public void setInvesteeName(String aInvesteeName) 
	{
	  investeeName = aInvesteeName;
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
	* @param aInstrumentName the new value of the investeeName property
	*/
	public void setInstrumentName(String aInstrumentName) 
	{
	  instrumentName = aInstrumentName;
	}

	/**
	* Access method for the investmentName property.
	* 
	* @return   the current value of the investmentName property
	*/
	public String getInvestmentName() 
	{
	  return investmentName;    
	}

	/**
	* Sets the value of the investmentName property.
	* 
	* @param aInvestmentName the new value of the investmentName property
	*/
	public void setInvestmentName(String aInvestmentName) 
	{
	  investmentName = aInvestmentName;
	}

	/**
	* Access method for the commercialPaperNumber property.
	*
	* @return   the current value of the commercialPaperNumber property
	*/
	public String getCommercialPaperNumber()
	{
	  return commercialPaperNumber;
	}

	/**
	* Sets the value of the commercialPaperNumber property.
	*
	* @param commercialPaperNumber the new value of the commercialPaperNumber property
	*/
	public void setCommercialPaperNumber(String aCommercialPaperNumber)
	{
	  commercialPaperNumber = aCommercialPaperNumber;
	}

	/**
	* Access method for the nameOfCompany property.
	*
	* @return   the current value of the nameOfCompany property
	*/
	public String getNameOfCompany()
	{
	  return nameOfCompany;
	}

	/**
	* Sets the value of the nameOfCompany property.
	*
	* @param nameOfCompany the new value of the nameOfCompany property
	*/
	public void setNameOfCompany(String aNameOfCompany)
	{
	  nameOfCompany = aNameOfCompany;
	}

	/**
	* Access method for the faceValue property.
	*
	* @return   the current value of the faceValue property
	*/
	public double getFaceValue()
	{
	  return faceValue;
	}

	/**
	* Sets the value of the faceValue property.
	*
	* @param faceValue the new value of the faceValue property
	*/
	public void setFaceValue(double aFaceValue)
	{
	  faceValue = aFaceValue;
	}

	/**
	* Access method for the noOfCommercialPapers property.
	*
	* @return   the current value of the noOfCommercialPapers property
	*/
	public int getNoOfCommercialPapers()
	{
	  return noOfCommercialPapers;
	}

	/**
	* Sets the value of the noOfCommercialPapers property.
	*
	* @param noOfCommercialPapers the new value of the noOfCommercialPapers property
	*/
	public void setNoOfCommercialPapers(int aNoOfCommercialPapers)
	{
	  noOfCommercialPapers = aNoOfCommercialPapers;
	}

	/**
	* Access method for the costOfPurchase property.
	*
	* @return   the current value of the costOfPurchase property
	*/
	public double getCostOfPurchase()
	{
	  return costOfPurchase;
	}

	/**
	* Sets the value of the costOfPurchase property.
	*
	* @param costOfPurchase the new value of the costOfPurchase property
	*/
	public void setCostOfPurchase(double aCostOfPurchase)
	{
	  costOfPurchase = aCostOfPurchase;
	}

	/**
	* Access method for the couponRate property.
	*
	* @return   the current value of the couponRate property
	*/
	public double getCouponRate()
	{
	  return couponRate;
	}

	/**
	* Sets the value of the couponRate property.
	*
	* @param couponRate the new value of the couponRate property
	*/
	public void setCouponRate(double aCouponRate)
	{
	  couponRate = aCouponRate;
	}

	/**
	* Access method for the tenure property.
	*
	* @return   the current value of the tenure property
	*/
	public int getTenure()
	{
	  return tenure;
	}

	/**
	* Sets the value of the tenure property.
	*
	* @param tenure the new value of the tenure property
	*/
	public void setTenure(int aTenure)
	{
	  tenure = aTenure;
	}

	/**
	* Access method for the dateOfInvestment property.
	*
	* @return   the current value of the dateOfInvestment property
	*/
	public Date getDateOfInvestment()
	{
	  return dateOfInvestment;
	}

	/**
	* Sets the value of the dateOfInvestment property.
	*
	* @param dateOfInvestment the new value of the dateOfInvestment property
	*/
	public void setDateOfInvestment(Date aDateOfInvestment)
	{
	  dateOfInvestment = aDateOfInvestment;
	}

	/**
	* Access method for the tenureType property.
	* 
	* @return   the current value of the tenureType property
	*/
	public String getTenureType() 
	{
	  return tenureType;    
	}

	/**
	* Sets the value of the tenureType property.
	* 
	* @param aTenureType the new value of the tenureType property
	*/
	public void setTenureType(String aTenureType) 
	{
	  tenureType = aTenureType;
	}

	/**
	* Access method for the maturityName property.
	* 
	* @return   the current value of the maturityName property
	*/
	public String getMaturityName() 
	{
	  return maturityName;    
	}

	/**
	* Sets the value of the maturityName property.
	* 
	* @param maturityName the new value of the maturityName property
	*/
	public void setMaturityName(String aMaturityName) 
	{
	  maturityName = aMaturityName;
	}

	/**
	* Access method for the maturityDate property.
	*
	* @return   the current value of the maturityDate property
	*/
	public Date getMaturityDate()
	{
	  return maturityDate;
	}

	/**
	* Sets the value of the maturityDate property.
	*
	* @param maturityDate the new value of the maturityDate property
	*/
	public void setMaturityDate(Date aMaturityDate)
	{
	  maturityDate = aMaturityDate;
	}

	/**
	* Access method for the maturityAmount property.
	*
	* @return   the current value of the maturityAmount property
	*/
	public double getMaturityAmount()
	{
	  return maturityAmount;
	}

	/**
	* Sets the value of the maturityAmount property.
	*
	* @param maturityAmount the new value of the maturityAmount property
	*/
	public void setMaturityAmount(double aMaturityAmount)
	{
	  maturityAmount = aMaturityAmount;
	}

	/**
	* Access method for the callOrPutOption property.
	*
	* @return   the current value of the callOrPutOption property
	*/
	public String getCallOrPutOption()
	{
	  return callOrPutOption;
	}

	/**
	* Sets the value of the callOrPutOption property.
	*
	* @param callOrPutOption the new value of the callOrPutOption property
	*/
	public void setCallOrPutOption(String aCallOrPutOption)
	{
	  callOrPutOption = aCallOrPutOption;
	}

	/**
	* Access method for the callOrPutDuration property.
	*
	* @return   the current value of the callOrPutDuration property
	*/
	public int getCallOrPutDuration()
	{
	  return callOrPutDuration;
	}

	/**
	* Sets the value of the callOrPutDuration property.
	*
	* @param aCallOrPutDuration the new value of the callOrPutDuration property
	*/
	public void setCallOrPutDuration(int aCallOrPutDuration)
	{
	  callOrPutDuration = aCallOrPutDuration;
	}

	/**
	* Access method for the instrumentFeature property.
	*
	* @return   the current value of the instrumentFeature property
	*/
	public String[] getInstrumentFeature()
	{
	  return instrumentFeature;
	}

	/**
	* Sets the value of the instrumentFeature property.
	*
	* @param instrumentFeature the new value of the instrumentFeature property
	*/
	public void setInstrumentFeature(String[] aInstrumentFeature)
	{
	  instrumentFeature = aInstrumentFeature;
	}

	
   	public String getModifiedBy()
	{
	   return modifiedBy;
	}

   public void setModifiedBy(String aModifiedBy)
   {
	   modifiedBy = aModifiedBy;
   }
   /**
	* @return
	*/
   public String getInvestmentReferenceNumber() {
	   return investmentReferenceNumber;
   }

   /**
	* @param string
	*/
   public void setInvestmentReferenceNumber(String string) {
	   investmentReferenceNumber = string;
   }
   
	/**
	 * @return
	 */
	public String getRating() {
		return rating;
	}

	/**
	 * @param string
	 */
	public void setRating(String string) {
		rating = string;
	}

	/**
	 * @return
	 */
	public String getInstrumentCategory() {
		return instrumentCategory;
	}

	/**
	 * @param string
	 */
	public void setInstrumentCategory(String string) {
		instrumentCategory = string;
	}
	/**
	 * @return
	 */
	public double getYtmValue() {
		return ytmValue;
	}

	/**
	 * @param d
	 */
	public void setYtmValue(double d) {
		ytmValue = d;
	}
	/**
	 * @return
	 */
	public String getAgency() {
		return agency;
	}

	/**
	 * @param string
	 */
	public void setAgency(String string) {
		agency = string;
	}

}
