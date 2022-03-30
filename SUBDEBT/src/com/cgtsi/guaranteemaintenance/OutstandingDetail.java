//Source file: D:\\com\\cgtsi\\guaranteemaintenance\\OutstandingDetail.java

package com.cgtsi.guaranteemaintenance;

import java.util.ArrayList;

public class OutstandingDetail implements java.io.Serializable
{
   private String cgpan;
   private String scheme;

   private double wcFBSanctionedAmount; 
   private double wcNFBSanctionedAmount;
   private double tcSanctionedAmount;
   
   private ArrayList outstandingAmounts = null;
   
   
   public OutstandingDetail() {
   	
   }
   
   /**
    * @roseuid 39B9CCDA009C
    */
   public OutstandingDetail(String cgpan, String scheme) 
   {
   		this.cgpan = cgpan ;
   		
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
	public double getTcSanctionedAmount() {
		return tcSanctionedAmount;
	}
	
	/**
	 * @return
	 */
	public double getWcFBSanctionedAmount() {
		return wcFBSanctionedAmount;
	}
	
	/**
	 * @return
	 */
	public double getWcNFBSanctionedAmount() {
		return wcNFBSanctionedAmount;
	}
	
	/**
	 * @param d
	 */
	public void setTcSanctionedAmount(double aTcSanctionedAmount) {
		tcSanctionedAmount = aTcSanctionedAmount;
	}
	
	/**
	 * @param d
	 */
	public void setWcFBSanctionedAmount(double aWcFBSanctionedAmount) {
		wcFBSanctionedAmount = aWcFBSanctionedAmount;
	}
	
	/**
	 * @param d
	 */
	public void setWcNFBSanctionedAmount(double aWcNFBSanctionedAmount) {
		wcNFBSanctionedAmount = aWcNFBSanctionedAmount;
	}

/**
 * @return  
 */
public ArrayList getOutstandingAmounts() {
	return outstandingAmounts;
}

/**
 * @param list
 */
public void setOutstandingAmounts(ArrayList list) {
	outstandingAmounts = list;
}

}
