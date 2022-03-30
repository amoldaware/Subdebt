//Source file: D:\\com\\cgtsi\\inwardoutward\\IOHelper.java

package com.cgtsi.inwardoutward;   


/**
 * This class is a utility class. This has methods to generate and reset IDs.
 */
public class IOHelper 
{
   
   public IOHelper() 
   {
    
   }
   
   /**
    * This method is called to generate an Inward ID. The format for an Inward ID is 
    * : IW/current financial year/nnnnn, where nnnnn is a consecutive 5-digit serial 
    * number.
    * @return java.lang.String 
    */
   public java.lang.String generateInwardID() 
   {
    return null;
   }
   
   /**
    * This method is called to generate an Outward ID. The format for an Outward ID 
    * is : OW/current financial year/nnnnn, where nnnnn is a consecutive 5-digit 
    * serial number
    * @return String
    */
   public String generateOutwardID() 
   {
    return null;
   }
   
   /**
    * The last 5 digits of an Inward Id is initialized on the 1st April every year. 
    * This method is called to reset the Inward Id every year.
    * @return Boolean
    */
   public Boolean resetInwardId() 
   {
    return null;
   }
   
   /**
    * The last 5 digits of an Outward ID is initialized on the 1st April every year. 
    * This method is called to reset the Outward ID every year.
    * @return Boolean
    */
   public Boolean resetOutwardId() 
   {
    return null;
   }
}
