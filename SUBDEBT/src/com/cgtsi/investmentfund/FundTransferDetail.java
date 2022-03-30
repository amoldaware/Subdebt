package com.cgtsi.investmentfund;

/*************************************************************
   *
   * Name of the class: FundTransferDetail
   * CGTSI can initiate funds transfer from one account to another account. This 
   * class encapsulates the information of funds transfer made by CGTSI.
   *
   * @author : Nithyalakshmi P
   * @version: 
   * @since: 
   **************************************************************/

public class FundTransferDetail 
{
   private String bankName;
   private String accNumber;
   private String closingBalanceDate;
   private String balanceAsPerStmt;
   private String unclearedBalance;
   private String balanceUtil;
   private String minBalance;
   private String amtCANotReflected;
   private String amtForIDBI;
   private String availForInvst="Y";
   private String remarks;
   private int bankId;
   private int id;
   
   public FundTransferDetail() 
   {
    
   }
   
/**
 * @return
 */
public String getAccNumber() {
	return accNumber;
}

/**
 * @return
 */
public String getAmtCANotReflected() {
	return amtCANotReflected;
}

/**
 * @return
 */
public String getAmtForIDBI() {
	return amtForIDBI;
}

/**
 * @return
 */
public String getAvailForInvst() {
	return availForInvst;
}

/**
 * @return
 */
public String getBalanceAsPerStmt() {
	return balanceAsPerStmt;
}

/**
 * @return
 */
public String getBalanceUtil() {
	return balanceUtil;
}

/**
 * @return
 */
public String getBankName() {
	return bankName;
}

/**
 * @return
 */
public String getClosingBalanceDate() {
	return closingBalanceDate;
}

/**
 * @return
 */
public String getMinBalance() {
	return minBalance;
}

/**
 * @return
 */
public String getRemarks() {
	return remarks;
}

/**
 * @return
 */
public String getUnclearedBalance() {
	return unclearedBalance;
}

/**
 * @param string
 */
public void setAccNumber(String string) {
	accNumber = string;
}

/**
 * @param string
 */
public void setAmtCANotReflected(String string) {
	amtCANotReflected = string;
}

/**
 * @param string
 */
public void setAmtForIDBI(String string) {
	amtForIDBI = string;
}

/**
 * @param string
 */
public void setAvailForInvst(String string) {
	availForInvst = string;
}

/**
 * @param string
 */
public void setBalanceAsPerStmt(String string) {
	balanceAsPerStmt = string;
}

/**
 * @param string
 */
public void setBalanceUtil(String string) {
	balanceUtil = string;
}

/**
 * @param string
 */
public void setBankName(String string) {
	bankName = string;
}

/**
 * @param string
 */
public void setClosingBalanceDate(String string) {
	closingBalanceDate = string;
}

/**
 * @param string
 */
public void setMinBalance(String string) {
	minBalance = string;
}

/**
 * @param string
 */
public void setRemarks(String string) {
	remarks = string;
}

/**
 * @param string
 */
public void setUnclearedBalance(String string) {
	unclearedBalance = string;
}

/**
 * @return
 */
public int getBankId() {
	return bankId;
}

/**
 * @param string
 */
public void setBankId(int i) {
	bankId = i;
}

/**
 * @return
 */
public int getId() {
	return id;
}

/**
 * @param string
 */
public void setId(int i) {
	id = i;
}

}
