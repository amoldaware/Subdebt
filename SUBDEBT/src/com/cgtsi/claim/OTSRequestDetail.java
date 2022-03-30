//Source file: F:\\CGTSI\\Design\\DesignModel\\com\\cgtsi\\claim\\OTSRequestDetail.java

package com.cgtsi.claim;


/**
 * This class is for processing OTS. The "remarks" field is initially null. The
 * "decision" field is initially "false".
 */
public class OTSRequestDetail
{
   private java.lang.String mliId;
   private java.lang.String cgbid;
   private String reasonForOTS;
   private String willfulDefaulter;
   private java.util.Vector cgpanDetails;

   public OTSRequestDetail()
   {

   }

   /**
    * Access method for the mliId property.
    *
    * @return   the current value of the mliId property
    */
   public java.lang.String getMliId()
   {
      return mliId;
   }

   /**
    * Sets the value of the mliId property.
    *
    * @param aMliId the new value of the mliId property
    */
   public void setMliId(java.lang.String aMliId)
   {
      mliId = aMliId;
   }

   /**
    * Access method for the cgbid property.
    *
    * @return   the current value of the cgbid property
    */
   public java.lang.String getCgbid()
   {
      return cgbid;
   }

   /**
    * Sets the value of the cgbid property.
    *
    * @param aCgbid the new value of the cgbid property
    */
   public void setCgbid(java.lang.String aCgbid)
   {
      cgbid = aCgbid;
   }

   /**
    * Access method for the reasonForOTS property.
    *
    * @return   the current value of the reasonForOTS property
    */
   public String getReasonForOTS()
   {
      return reasonForOTS;
   }

   /**
    * Sets the value of the reasonForOTS property.
    *
    * @param aReasonForOTS the new value of the reasonForOTS property
    */
   public void setReasonForOTS(String aReasonForOTS)
   {
      reasonForOTS = aReasonForOTS;
   }

   /**
    * Access method for the willfulDefaulter property.
    *
    * @return   the current value of the willfulDefaulter property
    */
   public String getWillfulDefaulter()
   {
      return willfulDefaulter;
   }

   /**
    * Sets the value of the willfulDefaulter property.
    *
    * @param aWillfulDefaulter the new value of the willfulDefaulter property
    */
   public void setWillfulDefaulter(String aWillfulDefaulter)
   {
      willfulDefaulter = aWillfulDefaulter;
   }

   /**
    * Access method for the loanDetails property.
    *
    * @return   the current value of the loanDetails property
    */
   public java.util.Vector getLoanDetails()
   {
      return cgpanDetails;
   }

   /**
    * Sets the value of the loanDetails property.
    *
    * @param aLoanDetails the new value of the loanDetails property
    */
   public void setLoanDetails(java.util.Vector aLoanDetails)
   {
      cgpanDetails = aLoanDetails;
   }
}
