//Source file: C:\\CGTSI\\Vss\\com\\cgtsi\\application\\ProjectOutlayDetails.java
package com.cgtsi.application;
import java.io.Serializable;

/**
 * This class holds the project outlay details
 */

public class ProjectOutlayDetails implements Serializable{
	
	private String isPrimarySecurity; /** added vinod 17-Mar-2015 */ 
	private String collateralSecurityTaken;//collateralSecurityValue
	private String thirdPartyGuaranteeTaken;
	private String guarantorsName1;
	private String guarantorsName2;
	private String guarantorsName3;
	private String guarantorsName4;
	private double guarantorsNetWorth1;
	private double guarantorsNetWorth2;
	private double guarantorsNetWorth3;
	private double guarantorsNetWorth4;
	private String subsidyName;
	
	//primary security details
	private PrimarySecurityDetails primarySecurityDetails=null; 
	
	private double termCreditSanctioned;
	private double wcFundBasedSanctioned;
	private double wcNonFundBasedSanctioned;
	private double tcPromoterContribution;
	private double wcPromoterContribution;
	private double tcSubsidyOrEquity;
	private double wcSubsidyOrEquity;
	private double tcOthers;
	private double wcOthers;
	private double projectCost;
	private double wcAssessed;
	private double projectOutlay;	


	/** added vinod 17-Mar-2015 */ 
	
	/**
	 * Access method for the IsPrimarySecurity property.
	 * 
	 * @return the current value of the IsPrimarySecurity property
	 * */

	public void setIsPrimarySecurity(String isPrimarySecurity) {
		this.isPrimarySecurity = isPrimarySecurity;
	}

	public String getIsPrimarySecurity() {
		return isPrimarySecurity;
	}
	
	/**
	  * Access method for the collateralSecurityTaken property.
	  * 
	  * @return   the current value of the collateralSecurityTaken property
	  */
	 public String getCollateralSecurityTaken() 
	 {
		return collateralSecurityTaken;
	 }
   
	 /**
	  * Sets the value of the collateralSecurityTaken property.
	  * 
	  * @param aCollateralSecurityTaken the new value of the collateralSecurityTaken property
	  */
	 public void setCollateralSecurityTaken(String aCollateralSecurityTaken) 
	 {
		collateralSecurityTaken = aCollateralSecurityTaken;
	 }
	 
	/**
	   * Access method for the thirdPartyGuaranteeTaken property.
	   * 
	   * @return   the current value of the thirdPartyGuaranteeTaken property
	   */
	  public String getThirdPartyGuaranteeTaken() 
	  {
		 return thirdPartyGuaranteeTaken;
	  }
   
	  /**
	   * Sets the value of the thirdPartyGuaranteeTaken property.
	   * 
	   * @param aThirdPartyGuaranteeTaken the new value of the thirdPartyGuaranteeTaken property
	   */
	  public void setThirdPartyGuaranteeTaken(String aThirdPartyGuaranteeTaken) 
	  {
		 thirdPartyGuaranteeTaken = aThirdPartyGuaranteeTaken;
	  }
	  
	/**
   * Access method for the guarantorsNames property.
   * 
   * @return   the current value of the guarantorsNames property
   */
  	public String getGuarantorsName1() 
  	{
	 	return guarantorsName1;
 	}
   
	  /**
	   * Sets the value of the guarantorsNames property.
	   * 
	   * @param aGuarantorNames the new value of the guarantorsNames property
	   */
	  public void setGuarantorsName1(String aGuarantorsName1) 
	  {
		guarantorsName1 = aGuarantorsName1;
	  }
	  
	/**
	   * Access method for the guarantorsWorth property.
	   * 
	   * @return   the current value of the guarantorsWorth property
	   */
	  public double getGuarantorsNetWorth1() 
	  {
		 return guarantorsNetWorth1;
	  }
   
		  /**
	   * Sets the value of the guarantorsWorth property.
	   * 
	   * @param aGuarantorsNetWorth the new value of the guarantorsWorth property
	   */
	  public void setGuarantorsNetWorth1(double aGuarantorsNetWorth1) 
	  {
		guarantorsNetWorth1 = aGuarantorsNetWorth1;
	  }
  
	/**
   * Access method for the subsidyName property.
   * 
   * @return   the current value of the subsidyName property
   */
  	public String getSubsidyName() 
  	{
		 return subsidyName;
  	}
   
  /**
   * Sets the value of the subsidyName property.
   * 
   * @param aSubsidyName the new value of the subsidyName property
   */
  	public void setSubsidyName(String aSubsidyName) 
  	{
	 	subsidyName = aSubsidyName;
  	}
	  
	/**
   * Access method for the primarySecurityDetails property.
   * 
   * @return   the current value of the primarySecurityDetails property
   */
 	 public PrimarySecurityDetails getPrimarySecurityDetails() 
  	{
		 return primarySecurityDetails;
  	}
   
  /**
   * Sets the value of the primarySecurityDetails property.
   * 
   * @param aBorrowerDetails the new value of the primarySecurityDetails property
   */
  public void setPrimarySecurityDetails(PrimarySecurityDetails aPrimarySecurityDetails) 
  {
	primarySecurityDetails = aPrimarySecurityDetails;
  }
  
  
	/**
	* Access method for the termCreditSantioned property.
	* 
	* @return   the current value of the termCreditSantioned property
	*/
   public double getTermCreditSanctioned() 
   {
	  return termCreditSanctioned;
   }
   
	/**
	* Sets the value of the termCreditSantioned property.
	* 
	* @param aTermCreditSantioned the new value of the termCreditSantioned property
	*/
   public void setTermCreditSanctioned(double aTermCreditSanctioned) 
   {
	termCreditSanctioned = aTermCreditSanctioned;
   }
	   
	/**
	* Access method for the wcFundBasedSanctioned property.
	* 
	* @return   the current value of the wcFundBasedSanctioned property
	*/
   public double getWcFundBasedSanctioned() 
   {
	  return wcFundBasedSanctioned;
   }
   
	/**
	* Sets the value of the wcFundBasedSanctioned property.
	* 
	* @param aWcFundBasedSanctioned the new value of the wcFundBasedSanctioned property
	*/
   public void setWcFundBasedSanctioned(double aWcFundBasedSanctioned) 
   {
		wcFundBasedSanctioned = aWcFundBasedSanctioned;
   }
   
   /**
   * Access method for the wcNonFundBasedSanctioned property.
   * 
   * @return   the current value of the wcNonFundBasedSanctioned property
   */
  public double getWcNonFundBasedSanctioned() 
  {
	 return wcNonFundBasedSanctioned;
  }
   
   /**
   * Sets the value of the wcNonFundBasedSanctioned property.
   * 
   * @param aWcNonFundBasedSanctioned the new value of the wcNonFundBasedSanctioned property
   */
  public void setWcNonFundBasedSanctioned(double aWcNonFundBasedSanctioned) 
  {
   	wcNonFundBasedSanctioned = aWcNonFundBasedSanctioned;
  }
	  
	/**
	* Access method for the tcpromoterContribution property.
	* 
	* @return   the current value of the tcpromoterContribution property
	*/
   public double getTcPromoterContribution() 
   {
	  return tcPromoterContribution;
   }
   
   /**
	* Sets the value of the tcpromoterContribution property.
	* 
	* @param aTcPromoterContribution the new value of the tcpromoterContribution property
	*/
   public void setTcPromoterContribution(double aTcPromoterContribution) 
   {
	  tcPromoterContribution = aTcPromoterContribution;
   }

	/**
	* Access method for the wcpromoterContribution property.
	* 
	* @return   the current value of the wcpromoterContribution property
	*/
   public double getWcPromoterContribution() 
   {
	  return wcPromoterContribution;
   }
   
   /**
	* Sets the value of the wcpromoterContribution property.
	* 
	* @param aWcPromoterContribution the new value of the promoterContribution property
	*/
   public void setWcPromoterContribution(double aWcPromoterContribution) 
   {
	  wcPromoterContribution = aWcPromoterContribution;
   }
   
  
   /**
   * Access method for the tcsubsidyOrEquity property.
   * 
   * @return   the current value of the tcsubsidyOrEquity property
   */
  public double getTcSubsidyOrEquity() 
  {
	 return tcSubsidyOrEquity;
  }
   
  /**
   * Sets the value of the tcsubsidyOrEquity property.
   * 
   * @param TcSubsidyOrEquity the new value of the tcsubsidyOrEquity property
   */
  public void setTcSubsidyOrEquity(double aTcSubsidyOrEquity) 
  {
	tcSubsidyOrEquity = aTcSubsidyOrEquity;
  }

  /**
   * Access method for the wcsubsidyOrEquity property.
   * 
   * @return   the current value of the wcsubsidyOrEquity property
   */
  public double getWcSubsidyOrEquity() 
  {
	 return wcSubsidyOrEquity;
  }
   
  /**
   * Sets the value of the wcsubsidyOrEquity property.
   * 
   * @param aWcSubsidyOrEquity the new value of the wcsubsidyOrEquity property
   */
  public void setWcSubsidyOrEquity(double aWcSubsidyOrEquity) 
  {
	wcSubsidyOrEquity = aWcSubsidyOrEquity;
  }
  
  /**
	* Access method for the tcothers property.
	* 
	* @return   the current value of the tcothers property
	*/
   public double getTcOthers() 
   {
	  return tcOthers;
   }
   
   /**
	* Sets the value of the tcothers property.
	* 
	* @param aTcOthers the new value of the tcothers property
	*/
   public void setTcOthers(double aTcOthers) 
   {
		tcOthers = aTcOthers;
   } 
	

 /**
	* Access method for the wcothers property.
	* 
	* @return   the current value of the wcothers property
	*/
   public double getWcOthers() 
   {
	  return wcOthers;
   }
   
   /**
	* Sets the value of the wcothers property.
	* 
	* @param aOthers the new value of the others property
	*/
   public void setWcOthers(double aWcOthers) 
   {
		wcOthers = aWcOthers;
   } 
	/**
  * Access method for the projectCost property.
  * 
  * @return   the current value of the projectCost property
  */
	 public double getProjectCost() 
	 {
		return projectCost;
	 }
   
 /**
  * Sets the value of the projectCost property.
  * 
  * @param aProjectCost the new value of the projectCost property
  */
	 public void setProjectCost(double aProjectCost) 
	 {
		projectCost = aProjectCost;
	 }
	 
  
	/**
	 * Access method for the wcAssessed property.
	 * 
	 * @return   the current value of the wcAssessed property
	 */
	public double getWcAssessed() 
	{
	   return wcAssessed;
	}
   
	/**
	 * Sets the value of the wcAssesed property.
	 * 
	 * @param aWcAccessed the new value of the wcAssessed property
	 */
	public void setWcAssessed(double aWcAssessed) 
	{
	   wcAssessed = aWcAssessed;
	}
	
	/**
	* Access method for the projectOutlay property.
	* 
	* @return   the current value of the projectOutlay property
	*/
   public double getProjectOutlay() 
   {
	  return projectOutlay;
   }
   
	/**
	* Sets the value of the projectOutlay property.
	* 
	* @param aProjectOutlay the new value of the projectOutlay property
	*/
   public void setProjectOutlay(double aProjectOutlay) 
   {
	  projectOutlay = aProjectOutlay;
   }
	/**
	 * @return
	 */
	public String getGuarantorsName2() {
		return guarantorsName2;
	}

	/**
	 * @return
	 */
	public String getGuarantorsName3() {
		return guarantorsName3;
	}

	/**
	 * @return
	 */
	public double getGuarantorsNetWorth2() {
		return guarantorsNetWorth2;
	}

	/**
	 * @return
	 */
	public double getGuarantorsNetWorth3() {
		return guarantorsNetWorth3;
	}

	/**
	 * @param string
	 */
	public void setGuarantorsName2(String aGuarantorsName2) {
		guarantorsName2 = aGuarantorsName2;
	}

	/**
	 * @param string
	 */
	public void setGuarantorsName3(String aGuarantorsName3) {
		guarantorsName3 = aGuarantorsName3;
	}

	/**
	 * @param d
	 */
	public void setGuarantorsNetWorth2(double aGuarantorsNetWorth2) {
		guarantorsNetWorth2 = aGuarantorsNetWorth2;
	}

	/**
	 * @param d
	 */
	public void setGuarantorsNetWorth3(double aGuarantorsNetWorth3) {
		guarantorsNetWorth3 = aGuarantorsNetWorth3;
	}

	/**
	 * @return
	 */
	public String getGuarantorsName4() {
		return guarantorsName4;
	}

	/**
	 * @return
	 */
	public double getGuarantorsNetWorth4() {
		return guarantorsNetWorth4;
	}

	/**
	 * @param string
	 */
	public void setGuarantorsName4(String aGuarantorsName4) {
		guarantorsName4 = aGuarantorsName4;
	}

	/**
	 * @param d
	 */
	public void setGuarantorsNetWorth4(double aGuarantorsNetWorth4) {
		guarantorsNetWorth4 = aGuarantorsNetWorth4;
	}

}
