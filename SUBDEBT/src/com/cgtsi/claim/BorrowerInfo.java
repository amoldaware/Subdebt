//Source file: F:\\CGTSI\\Design\\DesignModel\\com\\cgtsi\\claim\\BorrowerInfo.java

package com.cgtsi.claim;


public class BorrowerInfo implements java.io.Serializable
{
   private String borrowerName;
   private String address;
   private String district;
   private String state;
   private String telephone;
   private String city;
   private String pinCode;
   private String stdcode;
   private String telephoneNumber;
   public BorrowerInfo()
   {

   }

   /**
    * Access method for the borrowerName property.
    *
    * @return   the current value of the borrowerName property
    */
   public String getBorrowerName()
   {
      return borrowerName;
   }

   /**
    * Sets the value of the borrowerName property.
    *
    * @param aBorrowerName the new value of the borrowerName property
    */
   public void setBorrowerName(String aBorrowerName)
   {
      borrowerName = aBorrowerName;
   }

   /**
    * Access method for the address property.
    *
    * @return   the current value of the address property
    */
   public String getAddress()
   {
      return address;
   }

   /**
    * Sets the value of the address property.
    *
    * @param aAddress the new value of the address property
    */
   public void setAddress(String aAddress)
   {
      address = aAddress;
   }

   /**
    * Access method for the district property.
    *
    * @return   the current value of the district property
    */
   public String getDistrict()
   {
      return district;
   }

   /**
    * Sets the value of the district property.
    *
    * @param aDistrict the new value of the district property
    */
   public void setDistrict(String aDistrict)
   {
      district = aDistrict;
   }

   /**
    * Access method for the state property.
    *
    * @return   the current value of the state property
    */
   public String getState()
   {
      return state;
   }

   /**
    * Sets the value of the state property.
    *
    * @param aState the new value of the state property
    */
   public void setState(String aState)
   {
      state = aState;
   }

   /**
    * Access method for the telephone property.
    *
    * @return   the current value of the telephone property
    */
   public String getTelephone()
   {
      return telephone;
   }

   /**
    * Sets the value of the telephone property.
    *
    * @param aTelephone the new value of the telephone property
    */
   public void setTelephone(String aTelephone)
   {
      telephone = aTelephone;
   }

   public String getCity()
   {
	   return city;
   }

   public void setCity(String acity)
   {
	   city = acity;
   }

   public String getPinCode()
   {
	   return pinCode;
   }

   public void setPinCode(String apinCode)
   {
	   pinCode = apinCode;
   }

   public String getStdcode()
   {
	   return this.stdcode;
   }

   public void setStdcode(String code)
   {
	   this.stdcode = code;
   }

   public String getTelephoneNumber()
   {
	   return this.telephoneNumber;
   }

   public void setTelephoneNumber(String telNumber)
   {
       this.telephoneNumber = telNumber;
   }
}
