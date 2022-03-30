//Source file: F:\\CGTSI\\Design\\DesignModel\\com\\cgtsi\\claim\\RecoveryDetails.java

package com.cgtsi.claim;


public class RecoveryDetails  implements java.io.Serializable
{
   private String cgpan;
   private String modeOfRecovery;
   private double tcPrincipal;
   private double tcInterestAndOtherCharges;
   private double wcAmount;
   private double wcOtherCharges;
   
   public RecoveryDetails() 
   {
    
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
   
   /**
    * Access method for the modeOfRecovery property.
    * 
    * @return   the current value of the modeOfRecovery property
    */
   public String getModeOfRecovery() 
   {
      return modeOfRecovery;
   }
   
   /**
    * Sets the value of the modeOfRecovery property.
    * 
    * @param aModeOfRecovery the new value of the modeOfRecovery property
    */
   public void setModeOfRecovery(String aModeOfRecovery) 
   {
      modeOfRecovery = aModeOfRecovery;
   }
   
   /**
    * Access method for the tcPrincipal property.
    * 
    * @return   the current value of the tcPrincipal property
    */
   public double getTcPrincipal() 
   {
      return tcPrincipal;
   }
   
   /**
    * Sets the value of the tcPrincipal property.
    * 
    * @param aTcPrincipal the new value of the tcPrincipal property
    */
   public void setTcPrincipal(double aTcPrincipal) 
   {
      tcPrincipal = aTcPrincipal;
   }
   
   /**
    * Access method for the tcInterestAndOtherCharges property.
    * 
    * @return   the current value of the tcInterestAndOtherCharges property
    */
   public double getTcInterestAndOtherCharges() 
   {
      return tcInterestAndOtherCharges;
   }
   
   /**
    * Sets the value of the tcInterestAndOtherCharges property.
    * 
    * @param aTcInterestAndOtherCharges the new value of the tcInterestAndOtherCharges property
    */
   public void setTcInterestAndOtherCharges(double aTcInterestAndOtherCharges) 
   {
      tcInterestAndOtherCharges = aTcInterestAndOtherCharges;
   }
   
   /**
    * Access method for the wcAmount property.
    * 
    * @return   the current value of the wcAmount property
    */
   public double getWcAmount() 
   {
      return wcAmount;
   }
   
   /**
    * Sets the value of the wcAmount property.
    * 
    * @param aWcAmount the new value of the wcAmount property
    */
   public void setWcAmount(double aWcAmount) 
   {
      wcAmount = aWcAmount;
   }
   
   /**
    * Access method for the wcOtherCharges property.
    * 
    * @return   the current value of the wcOtherCharges property
    */
   public double getWcOtherCharges() 
   {
      return wcOtherCharges;
   }
   
   /**
    * Sets the value of the wcOtherCharges property.
    * 
    * @param aWcOtherCharges the new value of the wcOtherCharges property
    */
   public void setWcOtherCharges(double aWcOtherCharges) 
   {
      wcOtherCharges = aWcOtherCharges;
   }
}
