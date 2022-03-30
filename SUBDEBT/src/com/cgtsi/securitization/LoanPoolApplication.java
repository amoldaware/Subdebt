//Source file: C:\\CHECKOUT\\28OCT2003\\com\\cgtsi\\securitization\\LoanPoolApplication.java

package com.cgtsi.securitization;


/**
 * This class holds the details related to the applications which belong to the 
 * loan pool.
 */
public class LoanPoolApplication 
{
   private String bankId;
   private String zoneId;
   private String branchId;
   private String cgpan;
   
   /**
    * @roseuid 3A1647020216
    */
   public LoanPoolApplication() 
   {
    
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
