//Source file: D:\\NTS\\CGTSI\\Source\\com\\cgtsi\\receiptspayments\\GuaranteeFee.java

package com.cgtsi.receiptspayments;


/**
 * This class contains data members and methods that deal with the details 
 * regarding Guarantee Fee.
 */
public class GuaranteeFee 
{  
   private double guaranteeAmount;
   private String bankId;
   private String zoneId;
   private String branchId;
   private String borrowerId;
   private String cgpan;
   
   /**
    * @roseuid 39BBA1F70232
    */
   public GuaranteeFee() 
   {
    
   }
   
   /**
    * @param amount
    * @param cgpan
    * @roseuid 39B9CCDA01A5
    */
   public GuaranteeFee(double amount, String cgpan) 
   {
    	//this.guaranteeAmount = guaranteeAmount;
    	this.cgpan = cgpan;
    	//this.guaranteefeeGeneratedDate = new Date();    
   }
   
   /**
    * This is a constructor that will be initialised with amount and CGPAN as 
    * parameters.
    * @param amount
    * @param cgpan
    * @roseuid 39B898A9037A
    */
   public GuaranteeFee(Double amount, String cgpan) 
   {
    
   }
   
   /**
    * Access method for the guaranteeAmount property.
    * 
    * @return   the current value of the guaranteeAmount property
    */
   public double getGuaranteeAmount() 
   {
      return guaranteeAmount;    
   }
   
   /**
    * Sets the value of the guaranteeAmount property.
    * 
    * @param aGuaranteeAmount the new value of the guaranteeAmount property
    */
   public void setGuaranteeAmount(double aGuaranteeAmount) 
   {
      guaranteeAmount = aGuaranteeAmount;
   }
   
   /**
    * Access method for the bankId property.
    * 
    * @return   the current value of the bankId property
    */
   public String getBankId() 
   {
      return bankId;
   }
   
   /**
    * Sets the value of the bankId property.
    * 
    * @param aBankId the new value of the bankId property
    */
   public void setBankId(String aBankId) 
   {
      bankId = aBankId;
   }
   
   /**
    * Access method for the zoneId property.
    * 
    * @return   the current value of the zoneId property
    */
   public String getZoneId() 
   {
      return zoneId;
   }
   
   /**
    * Sets the value of the zoneId property.
    * 
    * @param aZoneId the new value of the zoneId property
    */
   public void setZoneId(String aZoneId) 
   {
      zoneId = aZoneId;
   }
   
   /**
    * Access method for the branchId property.
    * 
    * @return   the current value of the branchId property
    */
   public String getBranchId() 
   {
      return branchId;
   }
   
   /**
    * Sets the value of the branchId property.
    * 
    * @param aBranchId the new value of the branchId property
    */
   public void setBranchId(String aBranchId) 
   {
      branchId = aBranchId;
   }
   
   /**
    * Access method for the borrowerId property.
    * 
    * @return   the current value of the borrowerId property
    */
   public String getBorrowerId() 
   {
      return borrowerId;    
   }
   
   /**
    * Sets the value of the borrowerId property.
    * 
    * @param aBorrowerId the new value of the borrowerId property
    */
   public void setBorrowerId(String aBorrowerId) 
   {
      borrowerId = aBorrowerId;
   }
   
   /**
    * Access method for the cgpan property.
    * 
    * @return   the current value of the cgpan property
    */
   public String getCgpan() 
   {
      return cgpan;    
   }
   
   /**
    * Sets the value of the cgpan property.
    * 
    * @param aCgpan the new value of the cgpan property
    */
   public void setCgpan(String aCgpan) 
   {
      cgpan = aCgpan;
   }
}
/* 
GuaranteeFee.setGuaranteefeeGeneratedDate(java.util.Date){
      guaranteefeeGeneratedDate = aGuaranteefeeGeneratedDate;
   }
 */
/* 
GuaranteeFee.getGuaranteefeeGeneratedDate(){
      return guaranteefeeGeneratedDate;
   }
 */
/* 
GuaranteeFee.getMliId(){
      return mliId;
   }
 */
/* 
GuaranteeFee.setMliId(String){
      mliId = aMliId;
   }
 */
