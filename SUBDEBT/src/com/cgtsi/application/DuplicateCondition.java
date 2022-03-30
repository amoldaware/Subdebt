/*************************************************************
   *
   * Name of the class: DuplicateCondition.java
   * This class holds the duplicate condition name and its existing
   * and new values.
   * 
   *
   * @author : Veldurai T
   * @version:  
   * @since: Dec 8, 2003
   **************************************************************/
package com.cgtsi.application;

/**
 * @author Veldurai T
 *
 * This class holds the duplicate condition name 
 * and its old and new values.
 */
public class DuplicateCondition
{
	private String conditionName=null;
	
	private String existingValue=null;
	
	private String newValue=null;
	
 // added prevLoanType and Existing Loan type by sukumar@path on 24-08-2009
 private String prevLoanType = null;
 private String existLoanType = null;
 
 //Diksha
 /*private String prevSSi = null;
 private String existSSi = null;*/
 //Diksha end
 
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
 public void setexistLoanType(String existLoanType)
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
	 * @return
	 */
	public String getConditionName() {
		return conditionName;
	}

	/**
	 * @return
	 */
	public String getExistingValue() {
		return existingValue;
	}

	/**
	 * @return
	 */
	public String getNewValue() {
		return newValue;
	}

	/**
	 * @param string
	 */
	public void setConditionName(String string) {
		conditionName = string;
	}

	/**
	 * @param string
	 */
	public void setExistingValue(String string) {
		existingValue = string;
	}

	/**
	 * @param string
	 */
	public void setNewValue(String string) {
		newValue = string;
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
  }*/
//Diksha end
}
