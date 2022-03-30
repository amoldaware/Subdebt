//Source file: C:\\CGTSI\\Vss\\com\\cgtsi\\application\\DuplicateApplication.java

package com.cgtsi.application;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;


/**
 * This class is used while displaying probable duplicate applications.
 */
public class DuplicateApplication implements Serializable
{
   private String borrowerId;
   private String oldCgpan;
   private String newAppRefNo;
   private double dupApprovedAmount;
   private double dupCreditAmount;
   private String status;
   private String nature;
   private String duplicateRemarks;
   private Date sanctionedDate;
   private ArrayList DuplicateCondition;
   private String zoneName;
   /* loanType added by sukumar@path on 24-Aug-2009 */
   private String loanType;
  // added prevLoanType and Existing Loan type by sukumar@path on 24-08-2009
 private String prevLoanType = null;
 private String existLoanType = null;
 // activity Type added by sukumar@path on 21-11-2009
 private String activityType = null;
 private String handicraftFlag=null;
 private String dcHandicraftReimb = null;
 
 //Diksha
  /*private String prevSSi = null;
 private String existSSi = null;
 private String handloomFlag=null;*/
 
//Diksha end.
   /**
    * This object is used while displaying probable duplicates in the screen.
    */  

   private  DuplicateCriteria duplicateCriteria;
   
   /**
    * @roseuid 39B875CA0306
    */
   public DuplicateApplication() 
   {
    
   }
   /**
   * 
   * @param handicraftFlag
   */
  public void setHandicraftFlag(String handicraftFlag)
  {
   this.handicraftFlag = handicraftFlag;
  }
  /**
   * 
   * @return handicraftFlag
   */
  public String getHandicraftFlag()
  {
   return this.handicraftFlag;
  }
  /**
   * 
   * @param dcHandicraftReimb
   */
  public void setDcHandicraftReimb(String dcHandicraftReimb)
  {
   this.dcHandicraftReimb = dcHandicraftReimb;
  }
  /**
   * 
   * @return dcHandicraftReimb
   */
  public String getDcHandicraftReimb()
  {
   return this.dcHandicraftReimb;
  }
 /**
   * 
   * @return existLoanType
   */
 public String getActivityType()
 {
  return this.activityType;
 }
 /**
   * 
   * @param activityType
   */
 public void setActivityType(String activityType )
 {
 this.activityType = activityType; 
 }

 
 /**
   * 
   * @return existLoanType
   */
 public String getExistLoanType()
 {
  return this.existLoanType;
 }
 /**
   * 
   * @param existLoanType
   */
 public void setExistLoanType(String existLoanType)
 {
  this.existLoanType = existLoanType;
 }
 /**
   * 
   * @return prevLoanType
   */
 public String getPrevLoanType() 
 {
  return this.prevLoanType;
 }
 /**
   * 
   * @param prevLoanType
   */
 public void setPrevLoanType(String prevLoanType) 
 {
  this.prevLoanType = prevLoanType;
 }
 
   /**
   * 
   * @param type
   */
   public void setLoanType(String type){
     this.loanType = type;
   }
   /**
   * 
   * @return loanType
   */
   public String getLoanType()
   {
    return this.loanType;
   }
   
   public String getZoneName(){
    return zoneName;
   }
   public void setZoneName(String zoneName){
   
   this.zoneName = zoneName;
   
   }
    /**
   * 
   * @return sanctionedDate
   */
    public Date getSanctionedDate() 
   {
      return sanctionedDate;
   }
   
   /**
    * Sets the value of the sanctionedDate property.
    * 
    * @param aSubmittedDate the new value of the submittedDate property
    */
   public void setSanctionedDate(Date aSanctionedDate) 
   {
      sanctionedDate = aSanctionedDate;
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
    * Access method for the oldCgpan property.
    * 
    * @return   the current value of the oldCgpan property
    */
   public String getOldCgpan() 
   {
      return oldCgpan;
   }
   
   /**
    * Sets the value of the oldCgpan property.
    * 
    * @param aOldCgpan the new value of the oldCgpan property
    */
   public void setOldCgpan(String aOldCgpan) 
   {
      oldCgpan = aOldCgpan;
   }
   
   /**
    * Access method for the newAppRefNo property.
    * 
    * @return   the current value of the newAppRefNo property
    */
   public String getNewAppRefNo() 
   {
      return newAppRefNo;
   }
   
   /**
    * Sets the value of the newAppRefNo property.
    * 
    * @param aNewAppRefNo the new value of the newAppRefNo property
    */
   public void setNewAppRefNo(String aNewAppRefNo) 
   {
      newAppRefNo = aNewAppRefNo;
   }
   
   
   /**
    * Access method for the NewAppDtl property.
    * 
    * @return   the current value of the NewAppDtl property
    */
   public DuplicateCriteria getDuplicateCriteria() 
   {
      return duplicateCriteria;
   }
   
   /**
    * Sets the value of the NewAppDtl property.
    * 
    * @param aNewAppDtl the new value of the NewAppDtl property
    */
   public void setDuplicateCriteria(DuplicateCriteria aDuplicateCriteria) 
   {
      duplicateCriteria = aDuplicateCriteria;
   }

   /**
    * Access method for the NewAppDtl property.
    * 
    * @return   the current value of the NewAppDtl property
    */
   public ArrayList getDuplicateCondition() 
   {
      return DuplicateCondition;
   }
   
   /**
    * Sets the value of the NewAppDtl property.
    * 
    * @param aNewAppDtl the new value of the NewAppDtl property
    */
   public void setDuplicateCondition(ArrayList aDuplicateCondition) 
   {
      DuplicateCondition = aDuplicateCondition;
   }
/**
 * @return
 */
public double getDupApprovedAmount() {
	return dupApprovedAmount;
}

/**
 * @param d
 */
public void setDupApprovedAmount(double aDupApprovedAmount) {
	dupApprovedAmount = aDupApprovedAmount;
}

/**
 * @return
 */
public double getDupCreditAmount() {
	return dupCreditAmount;
}

/**
 * @param d
 */
public void setDupCreditAmount(double aDupCreditAmount) {
	dupCreditAmount = aDupCreditAmount;
}

/**
 * @return
 */
public String getStatus() {
	return status;
}

/**
 * @param string
 */
public void setNature(String string1) {
	nature = string1;
}
/**
 * @return
 */
public String getNature() {
	return nature;
}

/**
 * @param string
 */
public void setStatus(String string) {
	status = string;
}
/**
 * @return
 */
public String getDuplicateRemarks() {
	return duplicateRemarks;
}

/**
 * @param string
 */
public void setDuplicateRemarks(String string) {
	duplicateRemarks = string;
}

//Diksha

  /*public void setPrevSSi(String prevSSi)
  {
    this.prevSSi = prevSSi;
  }


  public String getPrevSSi()
  {
    return prevSSi;
  }


  public void setExistSSi(String existSSi)
  {
    this.existSSi = existSSi;
  }


  public String getExistSSi()
  {
    return existSSi;
  }

    public void setHandloomFlag(String handloomFlag) {
        this.handloomFlag = handloomFlag;
    }

    public String getHandloomFlag() {
        return handloomFlag;
    }*/
//Diksha end
}
