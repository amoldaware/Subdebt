// Decompiled Using: FrontEnd Plus v2.03 and the JAD Engine
// Available From: http://www.reflections.ath.cx
// Decompiler options: packimports(3) 

package com.cgtsi.investmentfund;

import java.io.Serializable;

public class TransactionDetail
    implements Serializable
{

    public TransactionDetail()
    {
        transactionNature = "C";
    }

    public String getTransactionDate()
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

    public void setTransactionDate(String s)
    {
        transactionDate = s;
    }

    public void setTransactionFromTo(String s)
    {
        transactionFromTo = s;
    }

    public void setTransactionNature(String s)
    {
        transactionNature = s;
    }

    public String getChequeNumber()
    {
        return chequeNumber;
    }

    public void setChequeNumber(String s)
    {
        chequeNumber = s;
    }

    public String getValueDate()
    {
        return valueDate;
    }

    public void setValueDate(String s)
    {
        valueDate = s;
    }

    public double getDeposits()
    {
        return deposits;
    }

    public String getTransactionId()
    {
        return transactionId;
    }

    public double getWithdrawals()
    {
        return withdrawals;
    }

    public void setDeposits(double d)
    {
        deposits = d;
    }

    public void setTransactionId(String s)
    {
        transactionId = s;
    }

    public void setWithdrawals(double d)
    {
        withdrawals = d;
    }

    public double getTransactionAmount()
    {
        return transactionAmount;
    }

    public void setTransactionAmount(double d)
    {
        transactionAmount = d;
    }

    private String transactionFromTo;
    private String transactionNature;
    private String transactionDate;
    private String valueDate;
    private String chequeNumber;
    private double withdrawals;
    private double deposits;
    private double transactionAmount;
    private String transactionId;
}
