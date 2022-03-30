// Decompiled Using: FrontEnd Plus v2.03 and the JAD Engine
// Available From: http://www.reflections.ath.cx
// Decompiler options: packimports(3) 

package com.cgtsi.investmentfund;

import java.util.Date;

public class InflowOutflowDetail
{

    public InflowOutflowDetail()
    {
    }

    public double getBalanceAsPerStmt()
    {
        return balanceAsPerStmt;
    }

    public double getBalanceForUtilisation()
    {
        return balanceForUtilisation;
    }

    public String getBankName()
    {
        return bankName;
    }

    public double getChqIssued()
    {
        return chqIssued;
    }

    public Date getDate()
    {
        return date;
    }

    public double getFdMaturity()
    {
        return fdMaturity;
    }

    public double getFundsRequired()
    {
        return fundsRequired;
    }

    public double getHvcAmount()
    {
        return hvcAmount;
    }

    public double getMinBalance()
    {
        return minBalance;
    }

    public double getNetBalance()
    {
        return netBalance;
    }

    public double getSurplusShortfall()
    {
        return surplusShortfall;
    }

    public void setBalanceAsPerStmt(double d)
    {
        balanceAsPerStmt = d;
    }

    public void setBalanceForUtilisation(double d)
    {
        balanceForUtilisation = d;
    }

    public void setBankName(String s)
    {
        bankName = s;
    }

    public void setChqIssued(double d)
    {
        chqIssued = d;
    }

    public void setDate(Date date1)
    {
        date = date1;
    }

    public void setFdMaturity(double d)
    {
        fdMaturity = d;
    }

    public void setFundsRequired(double d)
    {
        fundsRequired = d;
    }

    public void setHvcAmount(double d)
    {
        hvcAmount = d;
    }

    public void setMinBalance(double d)
    {
        minBalance = d;
    }

    public void setNetBalance(double d)
    {
        netBalance = d;
    }

    public void setSurplusShortfall(double d)
    {
        surplusShortfall = d;
    }

    public String getBankId()
    {
        return bankId;
    }

    public void setBankId(String s)
    {
        bankId = s;
    }

    private Date date;
    private String bankName;
    private String bankId;
    private double balanceAsPerStmt;
    private double balanceForUtilisation;
    private double fdMaturity;
    private double hvcAmount;
    private double netBalance;
    private double chqIssued;
    private double minBalance;
    private double fundsRequired;
    private double surplusShortfall;
}
