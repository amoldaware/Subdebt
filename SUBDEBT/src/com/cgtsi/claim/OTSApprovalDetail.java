//Source file: F:\\CGTSI\\Design\\DesignModel\\com\\cgtsi\\claim\\OTSApprovalDetail.java

package com.cgtsi.claim;


public class OTSApprovalDetail
{
   private String mliId;
   private String cgbid;
   private java.util.Date otsRequestDate;
   private String decision;
   private String remarks;

   public OTSApprovalDetail()
   {

   }

   /**
    * Access method for the mliId property.
    *
    * @return   the current value of the mliId property
    */
   public String getMliId()
   {
      return mliId;
   }

   /**
    * Sets the value of the mliId property.
    *
    * @param aMliId the new value of the mliId property
    */
   public void setMliId(String aMliId)
   {
      mliId = aMliId;
   }

   /**
    * Access method for the cgbid property.
    *
    * @return   the current value of the cgbid property
    */
   public String getCgbid()
   {
      return cgbid;
   }

   /**
    * Sets the value of the cgbid property.
    *
    * @param aCgbid the new value of the cgbid property
    */
   public void setCgbid(String aCgbid)
   {
      cgbid = aCgbid;
   }

   /**
    * Access method for the otsRequestDate property.
    *
    * @return   the current value of the otsRequestDate property
    */
   public java.util.Date getOtsRequestDate()
   {
      return otsRequestDate;
   }

   /**
    * Sets the value of the otsRequestDate property.
    *
    * @param aOtsRequestDate the new value of the otsRequestDate property
    */
   public void setOtsRequestDate(java.util.Date aOtsRequestDate)
   {
      otsRequestDate = aOtsRequestDate;
   }

   /**
    * Access method for the decision property.
    *
    * @return   the current value of the decision property
    */
   public String getDecision()
   {
      return decision;
   }

   /**
    * Sets the value of the decision property.
    *
    * @param aDecision the new value of the decision property
    */
   public void setDecision(String aDecision)
   {
      decision = aDecision;
   }

   /**
    * Access method for the remarks property.
    *
    * @return   the current value of the remarks property
    */
   public String getRemarks()
   {
      return remarks;
   }

   /**
    * Sets the value of the remarks property.
    *
    * @param aRemarks the new value of the remarks property
    */
   public void setRemarks(String aRemarks)
   {
      remarks = aRemarks;
   }
}
