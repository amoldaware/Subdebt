//Source file: C:\\CGTSI\\Vss\\com\\cgtsi\\application\\RepaymentDetail.java

package com.cgtsi.application;
import java.util.Date;
import java.io.Serializable;

/**
 * This class has details about repayment.
 */
public class RepaymentDetail implements Serializable
{
   public String repaymentId = null;
   public String cgpan = null;
   public double repaymentPpl = 0;
   public double repaymentDue = 0;
   public double repaymentInterest = 0;
   public Date repaymentDate = null;
   public double osPpl = 0;
   public double osInterest = 0;
   public String remarks = null;
   
   /**
    * @roseuid 39B875C90142
    */
   public RepaymentDetail() 
   {
    
   }
   
   /**
    * Access method for the repaymentId property.
    * 
    * @return   the current value of the repaymentId property
    */
   public String getRepaymentId() 
   {
      return repaymentId;
   }
   
   /**
    * Sets the value of the repaymentId property.
    * 
    * @param aRepaymentId the new value of the repaymentId property
    */
   public void setRepaymentId(String aRepaymentId) 
   {
      repaymentId = aRepaymentId;
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
    * Access method for the repaymentPpl property.
    * 
    * @return   the current value of the repaymentPpl property
    */
   public double getRepaymentPpl() 
   {
      return repaymentPpl;
   }
   
   /**
    * Sets the value of the repaymentPpl property.
    * 
    * @param aRepaymentPpl the new value of the repaymentPpl property
    */
   public void setRepaymentPpl(double aRepaymentPpl) 
   {
      repaymentPpl = aRepaymentPpl;
   }
   
   /**
    * Access method for the repaymentDue property.
    * 
    * @return   the current value of the repaymentDue property
    */
   public double getRepaymentDue() 
   {
      return repaymentDue;
   }
   
   /**
    * Sets the value of the repaymentDue property.
    * 
    * @param aRepaymentDue the new value of the repaymentDue property
    */
   public void setRepaymentDue(double aRepaymentDue) 
   {
      repaymentDue = aRepaymentDue;
   }
   
   /**
    * Access method for the repaymentInterest property.
    * 
    * @return   the current value of the repaymentInterest property
    */
   public double getRepaymentInterest() 
   {
      return repaymentInterest;
   }
   
   /**
    * Sets the value of the repaymentInterest property.
    * 
    * @param aRepaymentInterest the new value of the repaymentInterest property
    */
   public void setRepaymentInterest(double aRepaymentInterest) 
   {
      repaymentInterest = aRepaymentInterest;
   }
   
   /**
    * Access method for the repaymentDate property.
    * 
    * @return   the current value of the repaymentDate property
    */
   public Date getRepaymentDate() 
   {
      return repaymentDate;
   }
   
   /**
    * Sets the value of the repaymentDate property.
    * 
    * @param aRepaymentDate the new value of the repaymentDate property
    */
   public void setRepaymentDate(Date aRepaymentDate) 
   {
      repaymentDate = aRepaymentDate;
   }
   
   /**
    * Access method for the osPpl property.
    * 
    * @return   the current value of the osPpl property
    */
   public double getOsPpl() 
   {
      return osPpl;
   }
   
   /**
    * Sets the value of the osPpl property.
    * 
    * @param aOsPpl the new value of the osPpl property
    */
   public void setOsPpl(double aOsPpl) 
   {
      osPpl = aOsPpl;
   }
   
   /**
    * Access method for the osInterest property.
    * 
    * @return   the current value of the osInterest property
    */
   public double getOsInterest() 
   {
      return osInterest;
   }
   
   /**
    * Sets the value of the osInterest property.
    * 
    * @param aOsInterest the new value of the osInterest property
    */
   public void setOsInterest(double aOsInterest) 
   {
      osInterest = aOsInterest;
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
