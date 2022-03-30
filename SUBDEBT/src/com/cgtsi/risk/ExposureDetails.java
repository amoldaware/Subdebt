//Source file: F:\\CGTSI\\Design\\DesignModel\\com\\cgtsi\\risk\\ExposureSummary.java

package com.cgtsi.risk;


public class ExposureDetails
{

// following fields are used for calculate exposure report

	private String memberId;		// also used for generate exposure report
	private double appAmount;
	private int appCount;
	private double issuedAmount;
	private int issuedCount;

// following fields are used for generate exposure report

	private String state;
	private String bid;
	private String cgpan;
	private double sancAmt;
	private double osAmt;
	private int noOfClaims;
	private double totalClaim;
   
   /**
    * @roseuid 39E6CD7A0194
    */
   public ExposureDetails() 
   {
   }

	/**
    * Access method for the memberId property.
    * 
    * @return   the current value of the memberId property
    */
   public String getMemberId() 
   {
      return memberId;
   }
   
   /**
    * Sets the value of the memberId property.
    * 
    * @param aMemberId the new value of the memberId property
    */
   public void setMemberId(String aMemberId) 
   {
      memberId = aMemberId;
   }

	/**
    * Access method for the appAmount property.
    * 
    * @return   the current value of the appAmount property
    */
   public double getAppAmount() 
   {
      return appAmount;
   }
   
   /**
    * Sets the value of the appAmount property.
    * 
    * @param aAmount the new value of the appAmount property
    */
   public void setAppAmount(double aAppAmount) 
   {
      appAmount = aAppAmount;
   }

	/**
    * Access method for the appCount property.
    * 
    * @return   the current value of the appCount property
    */
   public int getAppCount() 
   {
      return appCount;
   }
   
   /**
    * Sets the value of the appCount property.
    * 
    * @param aCount the new value of the appCount property
    */
   public void setAppCount(int aAppCount) 
   {
      appCount = aAppCount;
   }

	/**
    * Access method for the issuedAmount property.
    * 
    * @return   the current value of the issuedAmount property
    */
   public double getIssuedAmount() 
   {
      return issuedAmount;
   }
   
   /**
    * Sets the value of the issuedAmount property.
    * 
    * @param aAmount the new value of the issuedAmount property
    */
   public void setIssuedAmount(double aIssuedAmount) 
   {
      issuedAmount = aIssuedAmount;
   }

	/**
    * Access method for the issuedCount property.
    * 
    * @return   the current value of the issuedCount property
    */
   public int getIssuedCount() 
   {
      return issuedCount;
   }
   
   /**
    * Sets the value of the issuedCount property.
    * 
    * @param aCount the new value of the issuedCount property
    */
   public void setIssuedCount(int aIssuedCount) 
   {
      issuedCount = aIssuedCount;
   }

	/**
    * Access method for the state property.
    * 
    * @return   the current value of the state property
    */
   public String getState() 
   {
      return state;
   }
   
   /**
    * Sets the value of the state property.
    * 
    * @param aState the new value of the state property
    */
   public void setState(String aState) 
   {
      state = aState;
   }

	/**
    * Access method for the bid property.
    * 
    * @return   the current value of the bid property
    */
   public String getBid() 
   {
      return bid;
   }
   
   /**
    * Sets the value of the bid property.
    * 
    * @param aState the new value of the bid property
    */
   public void setBid(String aBid) 
   {
      bid = aBid;
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
    * @param aState the new value of the cgpan property
    */
   public void setCgpan(String aCgpan) 
   {
      cgpan = aCgpan;
   }

	/**
    * Access method for the sancAmt property.
    * 
    * @return   the current value of the sancAmt property
    */
   public double getSancAmt() 
   {
      return sancAmt;
   }
   
   /**
    * Sets the value of the sancAmt property.
    * 
    * @param aSancAmt the new value of the sancAmt property
    */
   public void setSancAmt(double aSancAmt) 
   {
      sancAmt = aSancAmt;
   }

	/**
    * Access method for the osAmt property.
    * 
    * @return   the current value of the osAmt property
    */
   public double getOsAmt() 
   {
      return osAmt;
   }
   
   /**
    * Sets the value of the osAmt property.
    * 
    * @param aOsAmt the new value of the osAmt property
    */
   public void setOsAmt(double aOsAmt) 
   {
      osAmt = aOsAmt;
   }

	/**
    * Access method for the noOfClaims property.
    * 
    * @return   the current value of the noOfClaims property
    */
   public int getNoOfClaims() 
   {
      return noOfClaims;
   }
   
   /**
    * Sets the value of the noOfClaims property.
    * 
    * @param aNoOfClaims the new value of the noOfClaims property
    */
   public void setNoOfClaims(int aNoOfClaims) 
   {
      noOfClaims = aNoOfClaims;
   }

	/**
    * Access method for the totalClaim property.
    * 
    * @return   the current value of the totalClaim property
    */
   public double getTotalClaim() 
   {
      return totalClaim;
   }
   
   /**
    * Sets the value of the totalClaim property.
    * 
    * @param aTotalClaim the new value of the totalClaim property
    */
   public void setTotalClaim(double aTotalClaim) 
   {
      totalClaim = aTotalClaim;
   }
}
