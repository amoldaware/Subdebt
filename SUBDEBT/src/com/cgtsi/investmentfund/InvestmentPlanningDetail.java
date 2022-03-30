// Decompiled Using: FrontEnd Plus v2.03 and the JAD Engine
// Available From: http://www.reflections.ath.cx
// Decompiler options: packimports(3) 

package com.cgtsi.investmentfund;

import java.util.Date;

public class InvestmentPlanningDetail
{

    public InvestmentPlanningDetail()
    {
    }

    public double getDOpeningBalance()
    {
        return dOpeningBalance;
    }

    public void setDOpeningBalance(double d)
    {
        dOpeningBalance = d;
    }

    public double getDCreditPendingForDay()
    {
        return dCreditPendingForDay;
    }

    public void setDCreditPendingForDay(double d)
    {
        dCreditPendingForDay = d;
    }

    public String getSNatureOfTransaction()
    {
        return sNatureOfTransaction;
    }

    public void setSNatureOfTransaction(String s)
    {
        sNatureOfTransaction = s;
    }

    public Date getDTransactionDate()
    {
        return dTransactionDate;
    }

    public void setDTransactionDate(Date date)
    {
        dTransactionDate = date;
    }

    public String getSRemarksForTheTransaction()
    {
        return sRemarksForTheTransaction;
    }

    public void setSRemarksForTheTransaction(String s)
    {
        sRemarksForTheTransaction = s;
    }

    public double getDExpensesForTheMonth()
    {
        return dExpensesForTheMonth;
    }

    public void setDExpensesForTheMonth(double d)
    {
        dExpensesForTheMonth = d;
    }

    public String getSSourceOfTransaction()
    {
        return sSourceOfTransaction;
    }

    public void setSSourceOfTransaction(String s)
    {
        sSourceOfTransaction = s;
    }

    public String getSDestinationOfTransaction()
    {
        return sDestinationOfTransaction;
    }

    public void setSDestinationOfTransaction(String s)
    {
        sDestinationOfTransaction = s;
    }

    public double getDClosingBalance()
    {
        return dClosingBalance;
    }

    public void setDClosingBalance(double d)
    {
        dClosingBalance = d;
    }

    public String getSBankAccountNumber()
    {
        return sBankAccountNumber;
    }

    public void setSBankAccountNumber(String s)
    {
        sBankAccountNumber = s;
    }

    public String getIsBuyOrSellRequest()
    {
        return isBuyOrSellRequest;
    }

    public void setIsBuyOrSellRequest(String s)
    {
        isBuyOrSellRequest = s;
    }

    public String getNoOfUnits()
    {
        return noOfUnits;
    }

    public void setNoOfUnits(String s)
    {
        noOfUnits = s;
    }

    public String getInstrumentName()
    {
        return instrumentName;
    }

    public void setInstrumentName(String s)
    {
        instrumentName = s;
    }

    public String getInvesteeName()
    {
        return investeeName;
    }

    public void setInvesteeName(String s)
    {
        investeeName = s;
    }

    public String getModifiedBy()
    {
        return modifiedBy;
    }

    public void setModifiedBy(String s)
    {
        modifiedBy = s;
    }

    private double dOpeningBalance;
    private double dCreditPendingForDay;
    private String sNatureOfTransaction;
    private Date dTransactionDate;
    private String sRemarksForTheTransaction;
    private double dExpensesForTheMonth;
    private String sSourceOfTransaction;
    private String sDestinationOfTransaction;
    private double dClosingBalance;
    private String sBankAccountNumber;
    private String isBuyOrSellRequest;
    private String noOfUnits;
    private String instrumentName;
    private String investeeName;
    private String modifiedBy;
}
