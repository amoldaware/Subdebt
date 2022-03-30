//Source file: D:\\com\\cgtsi\\guaranteemaintenance\\Repayment.java

package com.cgtsi.guaranteemaintenance;


import java.util.ArrayList;


/** 
 * This class contains data members and methods that  manipulate details regarding 
 * Repayments from the borrowers.
 */
public class Repayment implements java.io.Serializable
{
   private String cgpan;
   private String scheme;
   private double noOfRepayments;
   private ArrayList repaymentAmounts = null ;
   
   
   
   /*
    * Constructor
    */
   public Repayment() {
   	
   }
   
   /**
    * @roseuid 39B9CCD901E4
    */
   public Repayment(String borrowerId, String cgpan, String  borrowerName, String  scheme) 
   {
		//this.borrowerId = borrowerId ;
		this.cgpan = cgpan ;
	//	this.borrowerName = borrowerName ;
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
/**
 * @return
 */
public ArrayList getRepaymentAmounts() {
	return repaymentAmounts;
}

/**
 * @param list
 */
public void setRepaymentAmounts(ArrayList list) {
	repaymentAmounts = list;
}

/**
 * @return
 */
public double getNoOfRepayments() {
	return noOfRepayments;
}

/**
 * @param d
 */
public void setNoOfRepayments(double d) {
	noOfRepayments = d;
}

}
