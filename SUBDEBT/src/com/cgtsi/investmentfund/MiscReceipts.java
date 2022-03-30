// Decompiled Using: FrontEnd Plus v2.03 and the JAD Engine
// Available From: http://www.reflections.ath.cx
// Decompiler options: packimports(3) 

package com.cgtsi.investmentfund;


public class MiscReceipts
{

    public MiscReceipts()
    {
        isConsideredForInv = "Y";
    }

    public String getAmount()
    {
        return amount;
    }

    public String getDateOfReceipt()
    {
        return dateOfReceipt;
    }

    public String getInstrumentDate()
    {
        return instrumentDate;
    }

    public String getInstrumentNo()
    {
        return instrumentNo;
    }

    public String getIsConsideredForInv()
    {
        return isConsideredForInv;
    }

    public String getSourceOfFund()
    {
        return sourceOfFund;
    }

    public void setAmount(String s)
    {
        amount = s;
    }

    public void setDateOfReceipt(String s)
    {
        dateOfReceipt = s;
    }

    public void setInstrumentDate(String s)
    {
        instrumentDate = s;
    }

    public void setInstrumentNo(String s)
    {
        instrumentNo = s;
    }

    public void setIsConsideredForInv(String s)
    {
        isConsideredForInv = s;
    }

    public void setSourceOfFund(String s)
    {
        sourceOfFund = s;
    }

    public int getId()
    {
        return id;
    }

    public void setId(int i)
    {
        id = i;
    }

    private String sourceOfFund;
    private String instrumentDate;
    private String instrumentNo;
    private String amount;
    private String isConsideredForInv;
    private String dateOfReceipt;
    private int id;
}
