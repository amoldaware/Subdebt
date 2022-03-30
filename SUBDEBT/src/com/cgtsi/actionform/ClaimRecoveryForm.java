package com.cgtsi.actionform;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import org.apache.struts.validator.ValidatorActionForm;

public class ClaimRecoveryForm extends ValidatorActionForm {

	private int count;
	private String claimReferanceNo;
	private ArrayList<String> cgpanList;
	private ArrayList<String> unitNameList;
	private ArrayList<Long> firstInstallmentAmountList;
	private ArrayList<Long> previouseRecoveredAmountList;
	private ArrayList<String> typeOfRecoveryList;
	private String typeOfRecoveryName;
	private ArrayList<Long> RecoveredAmoutList;
	private ArrayList<Long> legalExpensesList;
	private ArrayList<Long> amoutRemittedList;
	private final Map values = new HashMap();
	private String[] unitNameListArray;
	private RecoveryActionForm objRecoveryActionForm[];
	
	
	
	public RecoveryActionForm[] getObjRecoveryActionForm() {
		return objRecoveryActionForm;
	}
	public void setObjRecoveryActionForm(RecoveryActionForm[] objRecoveryActionForm) {
		this.objRecoveryActionForm = objRecoveryActionForm;
	}
	public String[] getUnitNameListArray() {
		return unitNameListArray;
	}
	public void setUnitNameListArray(String[] unitNameListArray) {
		this.unitNameListArray = unitNameListArray;
	}
	public int getCount() {
		return count;
	}
	public void setCount(int count) {
		this.count = count;
	}
	public String getClaimReferanceNo() {
		return claimReferanceNo;
	}
	public void setClaimReferanceNo(String claimReferanceNo) {
		this.claimReferanceNo = claimReferanceNo;
	}
	public ArrayList<String> getCgpanList() {
		return cgpanList;
	}
	public void setCgpanList(ArrayList<String> cgpanList) {
		this.cgpanList = cgpanList;
	}
	public ArrayList<String> getUnitNameList() {
		return unitNameList;
	}
	public void setUnitNameList(ArrayList<String> unitNameList) {
		this.unitNameList = unitNameList;
	}
	public ArrayList<Long> getFirstInstallmentAmountList() {
		return firstInstallmentAmountList;
	}
	public void setFirstInstallmentAmountList(
			ArrayList<Long> firstInstallmentAmountList) {
		this.firstInstallmentAmountList = firstInstallmentAmountList;
	}
	public ArrayList<Long> getPreviouseRecoveredAmountList() {
		return previouseRecoveredAmountList;
	}
	public void setPreviouseRecoveredAmountList(
			ArrayList<Long> previouseRecoveredAmountList) {
		this.previouseRecoveredAmountList = previouseRecoveredAmountList;
	}
	public ArrayList<String> getTypeOfRecoveryList() {
		return typeOfRecoveryList;
	}
	public void setTypeOfRecoveryList(ArrayList<String> typeOfRecoveryList) {
		this.typeOfRecoveryList = typeOfRecoveryList;
	}
	public String getTypeOfRecoveryName() {
		return typeOfRecoveryName;
	}
	public void setTypeOfRecoveryName(String typeOfRecoveryName) {
		this.typeOfRecoveryName = typeOfRecoveryName;
	}
	public ArrayList<Long> getRecoveredAmoutList() {
		return RecoveredAmoutList;
	}
	public void setRecoveredAmoutList(ArrayList<Long> recoveredAmoutList) {
		RecoveredAmoutList = recoveredAmoutList;
	}
	public ArrayList<Long> getLegalExpensesList() {
		return legalExpensesList;
	}
	public void setLegalExpensesList(ArrayList<Long> legalExpensesList) {
		this.legalExpensesList = legalExpensesList;
	}
	public ArrayList<Long> getAmoutRemittedList() {
		return amoutRemittedList;
	}
	public void setAmoutRemittedList(ArrayList<Long> amoutRemittedList) {
		this.amoutRemittedList = amoutRemittedList;
	}
	public Map getValues()
	{
	        return values;
	}
	     
	    public void setValue(String key, Object value){
	        values.put(key, value);
	    }
	     
	    public Object getValue(String key){
	        return values.get(key);
	    }
	
}
