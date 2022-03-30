//Source file: F:\\CGTSI\\Design\\DesignModel\\com\\cgtsi\\claim\\WorkingCapitalDetail.java

package com.cgtsi.claim;


public class WorkingCapitalDetail  implements java.io.Serializable
{
   private String cgpan;
   private double outstandingAsOnDateOfNPA;
   private double outstandingStatedInCivilSuit;
   private double outstandingAsOnDateOfLodgement;
   private double osAsOnDateOfLodgementOfClmForSecInstllmnt;

   public WorkingCapitalDetail()
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
    * Access method for the outstandingAsOnDateOfNPA property.
    *
    * @return   the current value of the outstandingAsOnDateOfNPA property
    */
   public double getOutstandingAsOnDateOfNPA()
   {
      return outstandingAsOnDateOfNPA;
   }

   /**
    * Sets the value of the outstandingAsOnDateOfNPA property.
    *
    * @param aOutstandingAsOnDateOfNPA the new value of the outstandingAsOnDateOfNPA property
    */
   public void setOutstandingAsOnDateOfNPA(double aOutstandingAsOnDateOfNPA)
   {
      outstandingAsOnDateOfNPA = aOutstandingAsOnDateOfNPA;
   }

   /**
    * Access method for the outstandingStatedInCivilSuit property.
    *
    * @return   the current value of the outstandingStatedInCivilSuit property
    */
   public double getOutstandingStatedInCivilSuit()
   {
      return outstandingStatedInCivilSuit;
   }

   /**
    * Sets the value of the outstandingStatedInCivilSuit property.
    *
    * @param aOutstandingStatedInCivilSuit the new value of the outstandingStatedInCivilSuit property
    */
   public void setOutstandingStatedInCivilSuit(double aOutstandingStatedInCivilSuit)
   {
      outstandingStatedInCivilSuit = aOutstandingStatedInCivilSuit;
   }

   /**
    * Access method for the outstandingAsOnDateOfLodgement property.
    *
    * @return   the current value of the outstandingAsOnDateOfLodgement property
    */
   public double getOutstandingAsOnDateOfLodgement()
   {
      return outstandingAsOnDateOfLodgement;
   }

   /**
    * Sets the value of the outstandingAsOnDateOfLodgement property.
    *
    * @param aOutstandingAsOnDateOfLodgement the new value of the outstandingAsOnDateOfLodgement property
    */
   public void setOutstandingAsOnDateOfLodgement(double aOutstandingAsOnDateOfLodgement)
   {
      outstandingAsOnDateOfLodgement = aOutstandingAsOnDateOfLodgement;
   }

   public double getOsAsOnDateOfLodgementOfClmForSecInstllmnt()
   {
	   return this.osAsOnDateOfLodgementOfClmForSecInstllmnt;
   }

   public void setOsAsOnDateOfLodgementOfClmForSecInstllmnt(double amnt)
   {
	   this.osAsOnDateOfLodgementOfClmForSecInstllmnt = amnt;
   }
   
    private String wcClaimFlag;

     public void setWcClaimFlag(String wcClaimFlag) {
         this.wcClaimFlag = wcClaimFlag;
     }

     public String getWcClaimFlag() {
         return wcClaimFlag;
     }
}
