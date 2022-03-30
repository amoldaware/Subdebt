//Source file: C:\\CGTSI\\Vss\\com\\cgtsi\\application\\SSIDetails.java

package com.cgtsi.application;
import java.io.Serializable;
import java.util.Date;

import com.cgtsi.actionform.APForm;


/**
 * This class holds the details about the ssis.(Small Scale Industries)
 */
public class SSIDetails implements Serializable
{
   private String cgbid;
   private String cgPan;    //Diksha
   private int borrowerRefNo;
   private String constitution;
   private String constitutionOther;
   private String ssiType;
   private String ssiName;
   private String regNo;
   private Date commencementDate;
   private String ssiITPan;
   private String industryNature;
   private String industrySector;
   private String activityType;
   private String mudraType;
   private String mudraITPANChk;
   private int employeeNos;
   private double projectedSalesTurnover;
   private double projectedExports;
   private String address;
   private String state;
   private String district;
   private String city;
   private String pincode;
   private String remarks;

   //****Chief promoters details****
   private String cpTitle;
   private String cpFirstName;
   private String cpMiddleName;
   private String cpLastName;
   private String cpGender;
   private String cpITPAN;
   private Date cpDOB;
   private String cpLegalID;
   private String cpLegalIdValue;
   private String firstName;
   private String firstItpan;
   private Date firstDOB;
   private String secondName;
   private String secondItpan;
   private Date secondDOB;
   private String thirdName;
   private String thirdItpan;
   private Date thirdDOB;
   //added by sukumar@path for fixing the bug in funcInsertApplicationDetail
   private String enterprise;
   private String unitAssisted;
   private String conditionAccepted="Y";
   private String womenOperated;
   /*private boolean newOrExisting;
   private double projectCost;
   private double wcAccessed;
   private double promoterContribution;
   private boolean alreadyAccessed;
   private double optimumTO;
   private double optimumExports;
   private boolean alreadyCovered;*/
    private String religion;
    private String mSE;
    
    
    
    //added  by sukumar@path for capturing whethe chief promoter is physically handicapped
    private String physicallyHandicapped;
   /*For MCGS*/

   private double corpusContributionAmt;

   private Date corpusContributionDate;

   private String displayDefaultersList="";

   private String socialCategory="";
   
   private String cpParTitle;
   public String getCpParTitle() {
	return cpParTitle;
}

   
public void setCpParTitle(String cpParTitle) {
	this.cpParTitle = cpParTitle;
}

public String getCpParFirstName() {
	return cpParFirstName;
}

public void setCpParFirstName(String cpParFirstName) {
	this.cpParFirstName = cpParFirstName;
}

public String getCpParMiddleName() {
	return cpParMiddleName;
}

public void setCpParMiddleName(String cpParMiddleName) {
	this.cpParMiddleName = cpParMiddleName;
}

public String getCpParLastName() {
	return cpParLastName;
}

public void setCpParLastName(String cpParLastName) {
	this.cpParLastName = cpParLastName;
}

private String cpParFirstName;
   private String cpParMiddleName;
   private String cpParLastName;


   /**
    * @roseuid 39B87D940007
    */
   public SSIDetails()
   {

   }
// aDDED BY rITESH pATH ON 29Nov2006 TO SHOW SOME MORE iINFORMATION ON aPPROVAL pAGE.
 
   private String sancDate_new;
   
   public String getSancDate_new() 
   {
      return sancDate_new;
   }
   
   public void setSancDate_new(String aSancDate_new) 
   {
      sancDate_new = aSancDate_new;
   }
   
///End By  Ritesh ////////////////////////////////////
/**
   * 
   * @param enterprise
   */
 public void setEnterprise(String enterprise)
    {
        this.enterprise = enterprise;
    }
/**
   * 
   * @return 
   */
    public String getEnterprise()
    {
        return enterprise;
    }
   /**
   * 
   * @param unitAssisted
   */
      public void setUnitAssisted(String unitAssisted)
    {
        this.unitAssisted = unitAssisted;
    }
/**
   * 
   * @return 
   */
    public String getUnitAssisted()
    {
        return unitAssisted;
    }
    /**
   * 
   * @param conditionAccepted
   */
     public void setConditionAccepted(String conditionAccepted)
    {
        this.conditionAccepted = conditionAccepted;
    }
/**
   * 
   * @return 
   */
    public String getConditionAccepted()
    {
        return conditionAccepted;
    }
    /**
   * 
   * @param womenOperated
   */
     public void setWomenOperated(String womenOperated)
    {
        this.womenOperated = womenOperated;
    }
    /**
   * 
   * @return physicallyHandicapped
   */
    public String getPhysicallyHandicapped()
    {
     return this.physicallyHandicapped;
    }
    /**
   * 
   * @param physicallyHandicapped
   */
    public void setPhysicallyHandicapped(String physicallyHandicapped)
    {
     this.physicallyHandicapped=physicallyHandicapped;
    }
/**
   * 
   * @return 
   */
    public String getWomenOperated()
    {
        return womenOperated;
    }
     public void setReligion(String religion)
    {
        this.religion = religion;
    }

    public String getReligion()
    {
        return religion;
    }
     public void setMSE(String mSE)
    {
        this.mSE = mSE;
    }

    public String getMudraType() {
		return mudraType;
	}


	public void setMudraType(String mudraType) {
		this.mudraType = mudraType;
	}


	public String getMudraITPANChk() {
		return mudraITPANChk;
	}


	public void setMudraITPANChk(String mudraITPANChk) {
		this.mudraITPANChk = mudraITPANChk;
	}


	public String getMSE()
    {
        return mSE;
    }
    //End by here---sukumar --------
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
	  * Access method for the borrower reference No property.
	  *
	  * @return   the current value of the borrower reference No property
	  */
	 public int getBorrowerRefNo()
	 {
		return borrowerRefNo;
	 }

	 /**
	  * Sets the value of the borrower reference No property.
	  *
	  * @param aCgbid the new value of the borrower reference No property
	  */
	 public void setBorrowerRefNo(int aBorrowerRefNo)
	 {
		borrowerRefNo = aBorrowerRefNo;
	 }

   /**
    * Access method for the constitution property.
    *
    * @return   the current value of the constitution property
    */
   public String getConstitution()
   {
      return constitution;
   }

   /**
    * Sets the value of the constitution property.
    *
    * @param aConstitution the new value of the constitution property
    */
   public void setConstitution(String aConstitution)
   {
      constitution = aConstitution;
   }

   /**
    * Access method for the constitutionOther property.
    *
    * @return   the current value of the constitutionOther property
    */
   public String getConstitutionOther()
   {
      return constitutionOther;
   }

   /**
    * Sets the value of the constitutionOther property.
    *
    * @param aConstitutionOther the new value of the constitutionOther property
    */
   public void setConstitutionOther(String aConstitutionOther)
   {
      constitutionOther = aConstitutionOther;
   }

   /**
    * Access method for the ssiName property.
    *
    * @return   the current value of the ssiName property
    */
   public String getSsiName()
   {
      return ssiName;
   }

   /**
    * Sets the value of the ssiName property.
    *
    * @param aSsiName the new value of the ssiName property
    */
   public void setSsiName(String aSsiName)
   {
      ssiName = aSsiName;
   }

   /**
    * Access method for the regNo property.
    *
    * @return   the current value of the regNo property
    */
   public String getRegNo()
   {
      return regNo;
   }

   /**
    * Sets the value of the regNo property.
    *
    * @param aRegNo the new value of the regNo property
    */
   public void setRegNo(String aRegNo)
   {
      regNo = aRegNo;
   }

   /**
    * Access method for the commencementDate property.
    *
    * @return   the current value of the commencementDate property
    */
   public Date getCommencementDate()
   {
      return commencementDate;
   }

   /**
    * Sets the value of the commencementDate property.
    *
    * @param aCommencementDate the new value of the commencementDate property
    */
   public void setCommencementDate(Date aCommencementDate)
   {
      commencementDate = aCommencementDate;
   }

   /**
    * Access method for the ssiITPan property.
    *
    * @return   the current value of the ssiITPan property
    */
   public String getSsiITPan()
   {
      return ssiITPan;
   }

   /**
    * Sets the value of the ssiITPan property.
    *
    * @param aSsiITPan the new value of the ssiITPan property
    */
   public void setSsiITPan(String aSsiITPan)
   {
      ssiITPan = aSsiITPan;
   }

   /**
    * Access method for the industryNature property.
    *
    * @return   the current value of the industryNature property
    */
   public String getIndustryNature()
   {
      return industryNature;
   }

   /**
    * Sets the value of the industryNature property.
    *
    * @param aIndustryNature the new value of the industryNature property
    */
   public void setIndustryNature(String aIndustryNature)
   {
      industryNature = aIndustryNature;
   }

   /**
    * Access method for the industrySector property.
    *
    * @return   the current value of the industrySector property
    */
   public String getIndustrySector()
   {
      return industrySector;
   }

   /**
    * Sets the value of the industrySector property.
    *
    * @param aIndustrySector the new value of the industrySector property
    */
   public void setIndustrySector(String aIndustrySector)
   {
      industrySector = aIndustrySector;
   }

   /**
    * Access method for the activityType property.
    *
    * @return   the current value of the activityType property
    */
   public String getActivityType()
   {
      return activityType;
   }

   /**
    * Sets the value of the activityType property.
    *
    * @param aActivityType the new value of the activityType property
    */
   public void setActivityType(String aActivityType)
   {
      activityType = aActivityType;
   }

   /**
    * Access method for the employeeNos property.
    *
    * @return   the current value of the employeeNos property
    */
   public int getEmployeeNos()
   {
      return employeeNos;
   }

   /**
    * Sets the value of the employeeNos property.
    *
    * @param aEmployeeNos the new value of the employeeNos property
    */
   public void setEmployeeNos(int aEmployeeNos)
   {
      employeeNos = aEmployeeNos;
   }

   public String getAddress()
	  {
		 return address;
	  }

  /**
   * Sets the value of the address property.
   *
   * @param aAddress the new value of the address property
   */
  public void setAddress(String aAddress)
  {
	 address = aAddress;
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
   * Access method for the district property.
   *
   * @return   the current value of the district property
   */
  public String getDistrict()
  {
	 return district;
  }

	  /**
   * Sets the value of the district property.
   *
   * @param aDistrict the new value of the district property
   */
  public void setDistrict(String aDistrict)
  {
	 district = aDistrict;
  }

	  /**
   * Access method for the city property.
   *
   * @return   the current value of the city property
   */
  public String getCity()
  {
	 return city;
  }

	  /**
   * Sets the value of the city property.
   *
   * @param aCity the new value of the city property
   */
  public void setCity(String aCity)
  {
	 city = aCity;
  }

	  /**
   * Access method for the pincode property.
   *
   * @return   the current value of the pincode property
   */
  public String getPincode()
  {
	 return pincode;
  }

	  /**
   * Sets the value of the pincode property.
   *
   * @param aPincode the new value of the pincode property
   */
  public void setPincode(String aPincode)
  {
	 pincode = aPincode;
  }

	  /**
   * Access method for the cpFirstName property.
   *
   * @return   the current value of the cpFirstName property
   */
  public String getCpFirstName()
  {
	 return cpFirstName;
  }

	  /**
   * Sets the value of the cpFirstName property.
   *
   * @param aCpFirstName the new value of the cpFirstName property
   */
  public void setCpFirstName(String aCpFirstName)
  {
	 cpFirstName = aCpFirstName;
  }

	  /**
   * Access method for the cpMiddleName property.
   *
   * @return   the current value of the cpMiddleName property
   */
  public String getCpMiddleName()
  {
	 return cpMiddleName;
  }

	  /**
   * Sets the value of the cpMiddleName property.
   *
   * @param aCpMiddleName the new value of the cpMiddleName property
   */
  public void setCpMiddleName(String aCpMiddleName)
  {
	 cpMiddleName = aCpMiddleName;
  }

	  /**
   * Access method for the cpLastName property.
   *
   * @return   the current value of the cpLastName property
   */
  public String getCpLastName()
  {
	 return cpLastName;
  }

	  /**
   * Sets the value of the cpLastName property.
   *
   * @param aCpLastName the new value of the cpLastName property
   */
  public void setCpLastName(String aCpLastName)
  {
	 cpLastName = aCpLastName;
  }

	  /**
   * Access method for the cpGender property.
   *
   * @return   the current value of the cpGender property
   */
  public String getCpGender()
  {
	 return cpGender;
  }

	  /**
   * Sets the value of the cpGender property.
   *
   * @param aCpGender the new value of the cpGender property
   */
  public void setCpGender(String aCpGender)
  {
	 cpGender = aCpGender;
  }
//Diksha 30/05/2017
	  /**
   * Access method for the cpPan property.
   *
   * @return   the current value of the cpPan property
   **/
  public String getCgPan()
  {
	 return cgPan;
  }

	  /** 
   * Sets the value of the cpPan property.
   *
   * @param aCpPan the new value of the cpPan property
   */
  public void setCgPan(String aCgPan)
  {
	 cgPan = aCgPan;
  }

	  /**
   * Access method for the cpDOB property.
   *
   * @return   the current value of the cpDOB property
   */
	  public Date getCpDOB()
	  {
		 return cpDOB;
	  }

	  /**
   * Sets the value of the cpDOB property.
   *
   * @param aCpDOB the new value of the cpDOB property
   */
  public void setCpDOB(Date aCpDOB)
  {
	 cpDOB = aCpDOB;
  }



  /**
 * Access method for the projectedSalesTurnover property.
 *
 * @return   the current value of the projectedSalesTurnover property
 */
	public double getProjectedSalesTurnover()
	{
	   return projectedSalesTurnover;
	}

	/**
 * Sets the value of the projectedSalesTurnover property.
 *
 * @param aProjectedSalesTurnover the new value of the projectedSalesTurnover property
 */
	public void setProjectedSalesTurnover(double aProjectedSalesTurnover)
	{
	   projectedSalesTurnover = aProjectedSalesTurnover;
	}

	/**
 * Access method for the projectedExports property.
 *
 * @return   the current value of the projectedExports property
 */
	public double getProjectedExports()
	{
	   return projectedExports;
	}

	/**
 * Sets the value of the projectedExports property.
 *
 * @param aProjectedExports the new value of the projectedExports property
 */
	public void setProjectedExports(double aProjectedExports)
	{
	   projectedExports = aProjectedExports;
	}

	/**
 * Access method for the cpLegalID property.
 *
 * @return   the current value of the cpLegalID property
 */
	public String getCpLegalID()
	{
	   return cpLegalID;
	}

	/**
 * Sets the value of the cpLegalID property.
 *
 * @param aCpLegalID the new value of the cpLegalID property
 */
	public void setCpLegalID(String aCpLegalID)
	{
	   cpLegalID = aCpLegalID;
	}

	/**
 * Access method for the cpLegalIdValue property.
 *
 * @return   the current value of the cpLegalIdValue property
 */
	public String getCpLegalIdValue()
	{
	   return cpLegalIdValue;
	}

	/**
 * Sets the value of the cpLegalIdValue property.
 *
 * @param aCpLegalIdValue the new value of the cpLegalIdValue property
 */
public void setCpLegalIdValue(String aCpLegalIdValue)
{
   cpLegalIdValue = aCpLegalIdValue;
}
   /**
    * Access method for the optimumTO property.
    *
    * @return   the current value of the optimumTO property
    *
   public double getOptimumTO()
   {
      return optimumTO;
   }

   /**
    * Sets the value of the optimumTO property.
    *
    * @param aOptimumTO the new value of the optimumTO property
    *
   public void setOptimumTO(double aOptimumTO)
   {
      optimumTO = aOptimumTO;
   }

   /**
    * Access method for the optimumExports property.
    *
    * @return   the current value of the optimumExports property
    *
   public double getOptimumExports()
   {
      return optimumExports;
   }

   /**
    * Sets the value of the optimumExports property.
    *
    * @param aOptimumExports the new value of the optimumExports property
    *
   public void setOptimumExports(double aOptimumExports)
   {
      optimumExports = aOptimumExports;
   }

   /**
    * Access method for the address property.
    *
    * @return   the current value of the address property
    */

   /**
    * Access method for the newOrExisting property.
    *
    * @return   the current value of the newOrExisting property
    *
   public boolean getNewOrExisting()
   {
      return newOrExisting;
   }

   /**
    * Sets the value of the newOrExisting property.
    *
    * @param aNewOrExisting the new value of the newOrExisting property
    *
   public void setNewOrExisting(boolean aNewOrExisting)
   {
      newOrExisting = aNewOrExisting;
   }








   /**
    * Access method for the alreadyAccessed property.
    *
    * @return   the current value of the alreadyAccessed property
    *
   public boolean getAlreadyAccessed()
   {
      return alreadyAccessed;
   }

   /**
    * Sets the value of the alreadyAccessed property.
    *
    * @param aAlreadyAccessed the new value of the alreadyAccessed property
    *
   public void setAlreadyAccessed(boolean aAlreadyAccessed)
   {
      alreadyAccessed = aAlreadyAccessed;
   }

   /**
    * Access method for the alreadyCovered property.
    *
    * @return   the current value of the alreadyCovered property
    *
   public boolean getAlreadyCovered()
   {
      return alreadyCovered;
   }

   /**
    * Sets the value of the alreadyCovered property.
    *
    * @param aAlreadyCovered the new value of the alreadyCovered property
    *
   public void setAlreadyCovered(boolean aAlreadyCovered)
   {
      alreadyCovered = aAlreadyCovered;
   }*/
/**
 * @return
 */
public String getSsiType() {
	return ssiType;
}

/**
 * @param string
 */
public void setSsiType(String aSsiType) {
	ssiType = aSsiType;
}

/**
 * @return
 */
public String getCpTitle() {
	return cpTitle;
}

/**
 * @param string
 */
public void setCpTitle(String aCpTitle) {
	cpTitle = aCpTitle;
}


/**
 * @return
 */
public Date getFirstDOB() {
	return firstDOB;
}

/**
 * @return
 */
public String getFirstItpan() {
	return firstItpan;
}

/**
 * @return
 */
public String getFirstName() {
	return firstName;
}

/**
 * @return
 */
public Date getSecondDOB() {
	return secondDOB;
}

/**
 * @return
 */
public String getSecondItpan() {
	return secondItpan;
}

/**
 * @return
 */
public String getSecondName() {
	return secondName;
}

/**
 * @return
 */
public Date getThirdDOB() {
	return thirdDOB;
}

/**
 * @return
 */
public String getThirdItpan() {
	return thirdItpan;
}

/**
 * @return
 */
public String getThirdName() {
	return thirdName;
}

/**
 * @param string
 */
public void setFirstDOB(Date aFirstDOB) {
	firstDOB = aFirstDOB;
}

/**
 * @param string
 */
public void setFirstItpan(String aFirstItpan) {
	firstItpan = aFirstItpan;
}

/**
 * @param string
 */
public void setFirstName(String aFirstName) {
	firstName = aFirstName;
}

/**
 * @param string
 */
public void setSecondDOB(Date aSecondDOB) {
	secondDOB = aSecondDOB;
}

/**
 * @param string
 */
public void setSecondItpan(String aSecondItpan) {
	secondItpan = aSecondItpan;
}

/**
 * @param string
 */
public void setSecondName(String aSecondName) {
	secondName = aSecondName;
}

/**
 * @param string
 */
public void setThirdDOB(Date aThirdDOB) {
	thirdDOB = aThirdDOB;
}

/**
 * @param string
 */
public void setThirdItpan(String aThirdItpan) {
	thirdItpan = aThirdItpan;
}

/**
 * @param string
 */
public void setThirdName(String aThirdName) {
	thirdName = aThirdName;
}

public String getCpITPAN(){
	return cpITPAN;
}
public void setCpITPAN(String aCpITPAN){
	cpITPAN=aCpITPAN;
}

public String getSocialCategory()
	{
	return socialCategory;
	}

public void setSocialCategory(String aSocialCategory)
	{
		socialCategory=aSocialCategory;

	}

/**
 * @return
 */
public double getCorpusContributionAmt() {
	return corpusContributionAmt;
}

/**
 * @return
 */
public Date getCorpusContributionDate() {
	return corpusContributionDate;
}

/**
 * @param d
 */
public void setCorpusContributionAmt(double d) {
	corpusContributionAmt = d;
}

/**
 * @param date
 */
public void setCorpusContributionDate(Date date) {
	corpusContributionDate = date;
}

/**
 * @return
 */
public String getDisplayDefaultersList() {
	return displayDefaultersList;
}

/**
 * @param string
 */
public void setDisplayDefaultersList(String string) {
	displayDefaultersList = string;
}


  public void setRemarks(String remarks)
  {
    this.remarks = remarks;
  }


  public String getRemarks()
  {
    return remarks;
  }
//Diksha 30/05/2017
 /* public APForm apForm = null;
  
  public APForm getApForm() {
		return apForm;
	}

	public void setApForm(APForm apForm) {
		this.apForm = apForm;
	}*/
}
