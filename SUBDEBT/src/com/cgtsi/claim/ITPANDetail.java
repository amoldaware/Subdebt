//Source file: C:\\CGTSI\\Vss\\com\\cgtsi\\claim\\ITPANDetail.java

package com.cgtsi.claim;


/**
 * This is a Value Object holds data related to ITPAN details
 */
public class ITPANDetail 
{
   private java.lang.String ssiName;
   private java.lang.String itpan;
   
   /**
    * @roseuid 39B875CB019F
    */
   public ITPANDetail() 
   {
    
   }
   
   /**
    * Access method for the ssiName property.
    * 
    * @return   the current value of the ssiName property
    */
   public java.lang.String getSsiName() 
   {
      return ssiName;
   }
   
   /**
    * Sets the value of the ssiName property.
    * 
    * @param aSsiName the new value of the ssiName property
    */
   public void setSsiName(java.lang.String aSsiName) 
   {
      ssiName = aSsiName;
   }
   
   /**
    * Access method for the itpan property.
    * 
    * @return   the current value of the itpan property
    */
   public java.lang.String getItpan() 
   {
      return itpan;
   }
   
   /**
    * Sets the value of the itpan property.
    * 
    * @param aItpan the new value of the itpan property
    */
   public void setItpan(java.lang.String aItpan) 
   {
      itpan = aItpan;
   }
}
