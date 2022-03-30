//Source file: D:\\com\\cgtsi\\guaranteemaintenance\\RepaymentSchedule.java

package com.cgtsi.guaranteemaintenance;

import java.util.Date;


public class RepaymentSchedule 
{
   private String cgpan; 
   private int moratorium;
   private Date firstInstallmentDueDate;
   private String periodicity;
   private int noOfInstallment;
   private String borrowerId;
   private String borrowerName;
   private String scheme;
   
    
   /*
    * Constructor 
    */
   public RepaymentSchedule() {
   	 
   }
   
   /**
    * @roseuid 39B9CCDA004E
    */
   public RepaymentSchedule(String borrowerId, String cgpan, String  borrowerName, String  scheme) 
   {
		this.borrowerId = borrowerId ;
		this.cgpan = cgpan ;
		this.borrowerName = borrowerName ;
		this.scheme = scheme ;
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
    * Access method for the moratorium property.
    * 
    * @return   the current value of the moratorium property
    */
   public int getMoratorium() 
   {
      return moratorium;    
   }
   
   /**
    * Sets the value of the moratorium property.
    * 
    * @param aMoratorium the new value of the moratorium property
    */
   public void setMoratorium(int aMoratorium) 
   {
      moratorium = aMoratorium;
   }
   
   /**
    * Access method for the firstInstallmentDueDate property.
    * 
    * @return   the current value of the firstInstallmentDueDate property
    */
   public Date getFirstInstallmentDueDate() 
   {
      return firstInstallmentDueDate;    
   }
   
   /**
    * Sets the value of the firstInstallmentDueDate property.
    * 
    * @param aFirstInstallmentDueDate the new value of the firstInstallmentDueDate property
    */
   public void setFirstInstallmentDueDate(Date aFirstInstallmentDueDate) 
   {
      firstInstallmentDueDate = aFirstInstallmentDueDate;
   }
   
   /**
    * Access method for the periodicity property.
    * 
    * @return   the current value of the periodicity property
    */
   public String getPeriodicity() 
   {
      return periodicity;    
   }
   
   /**
    * Sets the value of the periodicity property.
    * 
    * @param aPeriodicity the new value of the periodicity property
    */
   public void setPeriodicity(String aPeriodicity) 
   {
      periodicity = aPeriodicity;
   }
   
   /**
    * Access method for the noOfInstallment property.
    * 
    * @return   the current value of the noOfInstallment property
    */
   public int getNoOfInstallment() 
   {
      return noOfInstallment;    
   }
   
   /**
    * Sets the value of the noOfInstallment property.
    * 
    * @param aNoOfInstallment the new value of the noOfInstallment property
    */
   public void setNoOfInstallment(int aNoOfInstallment) 
   {
      noOfInstallment = aNoOfInstallment;
   }
   
   /**
    * Access method for the borrowerId property.
    * 
    * @return   the current value of the borrowerId property
    */
   public String getBorrowerId() 
   {
      return borrowerId;    
   }
   
   /**
    * Sets the value of the borrowerId property.
    * 
    * @param aBorrowerId the new value of the borrowerId property
    */
   public void setBorrowerId(String aBorrowerId) 
   {
      borrowerId = aBorrowerId;
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
    * Access method for the scheme property.
    * 
    * @return   the current value of the scheme property
    */
   public String getScheme() 
   {
      return scheme;    
   }
   
   /**
    * Sets the value of the scheme property.
    * 
    * @param aScheme the new value of the scheme property
    */
   public void setScheme(String aScheme) 
   {
      scheme = aScheme;
   }
}
