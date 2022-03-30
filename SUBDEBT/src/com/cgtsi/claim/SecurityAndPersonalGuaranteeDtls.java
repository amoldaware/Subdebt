//Source file: F:\\CGTSI\\Design\\DesignModel\\com\\cgtsi\\claim\\SecurityAndPersonalGuaranteeDtls.java

package com.cgtsi.claim;


public class SecurityAndPersonalGuaranteeDtls implements java.io.Serializable
{
   private DtlsAsOnDateOfSanction detailsAsOnDateOfSanction;
   private DtlsAsOnDateOfNPA detailsAsOnDateOfNPA;
   private DtlsAsOnLogdementOfClaim detailsAsOnDateOfLodgementOfClaim;
   private DtlsAsOnLogdementOfSecondClaim detailsAsOnDateOfLodgementOfSecondClaim;

   public SecurityAndPersonalGuaranteeDtls()
   {

   }

   /**
    * Access method for the detailsAsOnDateOfSanction property.
    *
    * @return   the current value of the detailsAsOnDateOfSanction property
    */
   public DtlsAsOnDateOfSanction getDetailsAsOnDateOfSanction()
   {
      return detailsAsOnDateOfSanction;
   }

   /**
    * Sets the value of the detailsAsOnDateOfSanction property.
    *
    * @param aDetailsAsOnDateOfSanction the new value of the detailsAsOnDateOfSanction property
    */
   public void setDetailsAsOnDateOfSanction(DtlsAsOnDateOfSanction aDetailsAsOnDateOfSanction)
   {
      detailsAsOnDateOfSanction = aDetailsAsOnDateOfSanction;
   }

   /**
    * Access method for the detailsAsOnDateOfNPA property.
    *
    * @return   the current value of the detailsAsOnDateOfNPA property
    */
   public DtlsAsOnDateOfNPA getDetailsAsOnDateOfNPA()
   {
      return detailsAsOnDateOfNPA;
   }

   /**
    * Sets the value of the detailsAsOnDateOfNPA property.
    *
    * @param aDetailsAsOnDateOfNPA the new value of the detailsAsOnDateOfNPA property
    */
   public void setDetailsAsOnDateOfNPA(DtlsAsOnDateOfNPA aDetailsAsOnDateOfNPA)
   {
      detailsAsOnDateOfNPA = aDetailsAsOnDateOfNPA;
   }

   /**
    * Access method for the detailsAsOnDateOfLodgementOfClaim property.
    *
    * @return   the current value of the detailsAsOnDateOfLodgementOfClaim property
    */
   public DtlsAsOnLogdementOfClaim getDetailsAsOnDateOfLodgementOfClaim()
   {
      return detailsAsOnDateOfLodgementOfClaim;
   }

   /**
    * Sets the value of the detailsAsOnDateOfLodgementOfClaim property.
    *
    * @param aDetailsAsOnDateOfLodgementOfClaim the new value of the detailsAsOnDateOfLodgementOfClaim property
    */
   public void setDetailsAsOnDateOfLodgementOfClaim(DtlsAsOnLogdementOfClaim aDetailsAsOnDateOfLodgementOfClaim)
   {
      detailsAsOnDateOfLodgementOfClaim = aDetailsAsOnDateOfLodgementOfClaim;
   }

   /**
    * Access method for the detailsAsOnDateOfLodgementOfSecondClaim property.
    *
    * @return   the current value of the detailsAsOnDateOfLodgementOfSecondClaim property
    */
   public DtlsAsOnLogdementOfSecondClaim getDetailsAsOnDateOfLodgementOfSecondClaim()
   {
      return detailsAsOnDateOfLodgementOfSecondClaim;
   }

   /**
    * Sets the value of the detailsAsOnDateOfLodgementOfSecondClaim property.
    *
    * @param aDetailsAsOnDateOfLodgementOfSecondClaim the new value of the detailsAsOnDateOfLodgementOfSecondClaim property
    */
   public void setDetailsAsOnDateOfLodgementOfSecondClaim(DtlsAsOnLogdementOfSecondClaim aDetailsAsOnDateOfLodgementOfSecondClaim)
   {
      detailsAsOnDateOfLodgementOfSecondClaim = aDetailsAsOnDateOfLodgementOfSecondClaim;
   }
}
