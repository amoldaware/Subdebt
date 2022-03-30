//Source file: F:\\CGTSI\\Design\\DesignModel\\com\\cgtsi\\claim\\MemberInfo.java

package com.cgtsi.claim;


public class MemberInfo implements java.io.Serializable
{
   private String memberId;
   private String memberBankName;
   private String memberBranchName;
   private String city;
   private String district;
   private String state;
   private String phoneCode;
   private String telephone;
   private String email;

   public MemberInfo()
   {

   }

   /**
    * Access method for the memberId property.
    *
    * @return   the current value of the memberId property
    */
   public String getMemberId()
   {
      return memberId;
   }

   /**
    * Sets the value of the memberId property.
    *
    * @param aMemberId the new value of the memberId property
    */
   public void setMemberId(String aMemberId)
   {
      memberId = aMemberId;
   }

   /**
    * Access method for the memberBankName property.
    *
    * @return   the current value of the memberBankName property
    */
   public String getMemberBankName()
   {
      return memberBankName;
   }

   /**
    * Sets the value of the memberBankName property.
    *
    * @param aMemberBankName the new value of the memberBankName property
    */
   public void setMemberBankName(String aMemberBankName)
   {
      memberBankName = aMemberBankName;
   }

   public String getMemberBranchName()
   {
	  return memberBranchName;
   }

   public void setMemberBranchName(String aMemberBranchName)
   {
	  memberBranchName = aMemberBranchName;
   }

   /**
    * Access method for the city property.
    *
    * @return   the current value of the city property
    */
   public String getCity()
   {
      return city;
   }

   /**
    * Sets the value of the city property.
    *
    * @param aCity the new value of the city property
    */
   public void setCity(String aCity)
   {
      city = aCity;
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

   public String getPhoneCode()
   {
       return phoneCode;
   }


   public void setPhoneCode(String aPhoneCode)
   {
        phoneCode = aPhoneCode;
   }

   /**
    * Access method for the email property.
    *
    * @return   the current value of the email property
    */
   public String getEmail()
   {
      return email;
   }

   /**
    * Sets the value of the email property.
    *
    * @param aEmail the new value of the email property
    */
   public void setEmail(String aEmail)
   {
      email = aEmail;
   }
   
   private String dealingOfficerName;

    public void setDealingOfficerName(String dealingOfficerName) {
        this.dealingOfficerName = dealingOfficerName;
    }

    public String getDealingOfficerName() {
        return dealingOfficerName;
    }
    
    private String memberAddress;

     public void setMemberAddress(String memberAddress) {
         this.memberAddress = memberAddress;
     }

     public String getMemberAddress() {
         return memberAddress;
     }
}
