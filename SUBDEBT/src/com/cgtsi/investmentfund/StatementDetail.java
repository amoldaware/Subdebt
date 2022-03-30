// Decompiled Using: FrontEnd Plus v2.03 and the JAD Engine
// Available From: http://www.reflections.ath.cx
// Decompiler options: packimports(3) 

package com.cgtsi.investmentfund;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;

public class StatementDetail
    implements Serializable
{

    public StatementDetail()
    {
        transactionDetail = new ArrayList();
    }

    public String getAccountNumber()
    {
        return accountNumber;
    }

    public String getBankBranchName()
    {
        return bankBranchName;
    }

    public String getBankName()
    {
        return bankName;
    }

    public double getClosingBalance()
    {
        return closingBalance;
    }

    public double getCreditPendingForTheDay()
    {
        return creditPendingForTheDay;
    }

    public double getOpeningBalance()
    {
        return openingBalance;
    }

    public String getRemarks()
    {
        return remarks;
    }

    public double getTransactionAmount()
    {
        return transactionAmount;
    }

    public Date getTransactionDate()
    {
        return transactionDate;
    }

    public String getTransactionFromTo()
    {
        return transactionFromTo;
    }

    public String getTransactionNature()
    {
        return transactionNature;
    }

    public void setAccountNumber(String s)
    {
        accountNumber = s;
    }

    public void setBankBranchName(String s)
    {
        bankBranchName = s;
    }

    public void setBankName(String s)
    {
        bankName = s;
    }

    public void setClosingBalance(double d)
    {
        closingBalance = d;
    }

    public void setCreditPendingForTheDay(double d)
    {
        creditPendingForTheDay = d;
    }

    public void setOpeningBalance(double d)
    {
        openingBalance = d;
    }

    public void setRemarks(String s)
    {
        remarks = s;
    }

    public void setTransactionAmount(double d)
    {
        transactionAmount = d;
    }

    public void setTransactionDate(Date date)
    {
        transactionDate = date;
    }

    public void setTransactionFromTo(String s)
    {
        transactionFromTo = s;
    }

    public void setTransactionNature(String s)
    {
        transactionNature = s;
    }

    public ArrayList getTransactionDetail()
    {
        return transactionDetail;
    }

    public void setTransactionDetail(ArrayList arraylist)
    {
        transactionDetail = arraylist;
    }

    public Date getStatementDate()
    {
        return statementDate;
    }

    public void setStatementDate(Date date)
    {
        statementDate = date;
    }

    public String getUserId()
    {
        return userId;
    }

    public void setUserId(String s)
    {
        userId = s;
    }

    private String bankName;
    private String bankBranchName;
    private String accountNumber;
    private double openingBalance;
    private double closingBalance;
    private double creditPendingForTheDay;
    private String transactionFromTo;
    private String transactionNature;
    private Date transactionDate;
    private Date statementDate;
    private double transactionAmount;
    private String remarks;
    private String userId;
    private ArrayList transactionDetail;
}
