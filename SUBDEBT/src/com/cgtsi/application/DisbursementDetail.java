//Source file: C:\\CGTSI\\Vss\\com\\cgtsi\\application\\DisbursementDetail.java

package com.cgtsi.application;
import java.util.Date;
import java.io.Serializable;

/**
 * This class holds the disbursment details.
 */
public class DisbursementDetail implements Serializable
{
   public int disbursementNo = 0;
   public String cgpan = null;
   public double disbursementAmt = 0;
   public Date disbursementDate = null;
   
   /**
    * false-If the disbursement details are not for the last disbursement
    * true-The disbursement details entered are the last
    */
   public boolean isLastDisbursement = false;
   
   /**
    * @roseuid 39B875CA00A4
    */
   public DisbursementDetail() 
   {
    
   }
   
   /**
    * Access method for the disbursementNo property.
    * 
    * @return   the current value of the disbursementNo property
    */
   public int getDisbursementNo() 
   {
      return disbursementNo;
   }
   
   /**
    * Sets the value of the disbursementNo property.
    * 
    * @param aDisbursementNo the new value of the disbursementNo property
    */
   public void setDisbursementNo(int aDisbursementNo) 
   {
      disbursementNo = aDisbursementNo;
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
    * Access method for the disbursementAmt property.
    * 
    * @return   the current value of the disbursementAmt property
    */
   public double getDisbursementAmt() 
   {
      return disbursementAmt;
   }
   
   /**
    * Sets the value of the disbursementAmt property.
    * 
    * @param aDisbursementAmt the new value of the disbursementAmt property
    */
   public void setDisbursementAmt(double aDisbursementAmt) 
   {
      disbursementAmt = aDisbursementAmt;
   }
   
   /**
    * Access method for the disbursementDate property.
    * 
    * @return   the current value of the disbursementDate property
    */
   public Date getDisbursementDate() 
   {
      return disbursementDate;
   }
   
   /**
    * Sets the value of the disbursementDate property.
    * 
    * @param aDisbursementDate the new value of the disbursementDate property
    */
   public void setDisbursementDate(Date aDisbursementDate) 
   {
      disbursementDate = aDisbursementDate;
   }
   
   /**
    * Access method for the isLastDisbursement property.
    * 
    * @return   the current value of the isLastDisbursement property
    */
   public boolean getIsLastDisbursement() 
   {
      return isLastDisbursement;
   }
   
   /**
    * Sets the value of the isLastDisbursement property.
    * 
    * @param aIsLastDisbursement the new value of the isLastDisbursement property
    */
   public void setIsLastDisbursement(boolean aIsLastDisbursement) 
   {
      isLastDisbursement = aIsLastDisbursement;
   }
}
