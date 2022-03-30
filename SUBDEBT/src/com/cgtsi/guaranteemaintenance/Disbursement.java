//Source file: D:\\com\\cgtsi\\guaranteemaintenance\\Disbursement.java

package com.cgtsi.guaranteemaintenance;
 
import java.util.ArrayList;


/**
 * This class handles the data about Disbursement. It provides methods to 
 * manipulate the data also.
 */
public class Disbursement implements java.io.Serializable
{
               
	private String cgpan;
	private String scheme;
	private double sanctionedAmount;
	
	private ArrayList disbursementAmounts = null;	

   	
// private String borrowerId;
//   private String borrowerName;

 
  //  constructor defined.
   	public Disbursement() {
  	
   }
  
  
   /**
    * @roseuid 39B9CCD901A5
    */
   public Disbursement(String borrowerId, String cgpan, String borrowerName, String scheme) 
   {
	//	this.borrowerId = borrowerId ;
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
    * Access method for the borrowerId property.
    * 
    * @return   the current value of the borrowerId property
    */
 /*  public String getBorrowerId() 
   {
      return borrowerId;    
   }*/
   
   /**
    * Sets the value of the borrowerId property.
    * 
    * @param aBorrowerId the new value of the borrowerId property
    */
 /*  public void setBorrowerId(String aBorrowerId) 
   {
      borrowerId = aBorrowerId;
  }*/
   
   /**
    * Access method for the borrowerName property.
    * 
    * @return   the current value of the borrowerName property
    */
  /* public String getBorrowerName() 
   {
      return borrowerName;    
   }*/
   
   /**
    * Sets the value of the borrowerName property.
    * 
    * @param aBorrowerName the new value of the borrowerName property
    */
  /* public void setBorrowerName(String aBorrowerName) 
   {
      borrowerName = aBorrowerName;
   }*/
   
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
 * @return ***ADDED By gowri****
 */
public double getSanctionedAmount() {
	return sanctionedAmount;
}

/**
 * @param d
 */
public void setSanctionedAmount(double aSanctionedAmount) {
	sanctionedAmount = aSanctionedAmount;
}

	/**
	 * @return
	 */
	public ArrayList getDisbursementAmounts() {
		return disbursementAmounts;
	}

	/**
	 * @param list
	 */
	public void setDisbursementAmounts(ArrayList list) {
		disbursementAmounts = list;
	}

}
