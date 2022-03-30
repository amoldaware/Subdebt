// Decompiled Using: FrontEnd Plus v2.03 and the JAD Engine
// Available From: http://www.reflections.ath.cx
// Decompiler options: packimports(3) 

package com.cgtsi.investmentfund;

import java.util.Date;

public class InvestmentFulfillmentDetail
{

    public InvestmentFulfillmentDetail()
    {
    }

    public String getInvesteeName()
    {
        return investeeName;
    }

    public void setInvesteeName(String s)
    {
        investeeName = s;
    }

    public String getInstrumentName()
    {
        return instrumentName;
    }

    public void setInstrumentName(String s)
    {
        instrumentName = s;
    }

    public String getInflowOutFlowFlag()
    {
        return inflowOutFlowFlag;
    }

    public void setInflowOutFlowFlag(String s)
    {
        inflowOutFlowFlag = s;
    }

    public String getInstrumentType()
    {
        return instrumentType;
    }

    public void setInstrumentType(String s)
    {
        instrumentType = s;
    }

    public String getInstrumentNumber()
    {
        return instrumentNumber;
    }

    public void setInstrumentNumber(String s)
    {
        instrumentNumber = s;
    }

    public Date getInstrumentDate()
    {
        return instrumentDate;
    }

    public double getInstrumentAmount()
    {
        return instrumentAmount;
    }

    public void setInstrumentAmount(double d)
    {
        instrumentAmount = d;
    }

    public String getDrawnBank()
    {
        return drawnBank;
    }

    public void setDrawnBank(String s)
    {
        drawnBank = s;
    }

    public String getDrawnBranch()
    {
        return drawnBranch;
    }

    public void setDrawnBranch(String s)
    {
        drawnBranch = s;
    }

    public String getPayableAt()
    {
        return payableAt;
    }

    public void setPayableAt(String s)
    {
        payableAt = s;
    }

    public String getModifiedBy()
    {
        return modifiedBy;
    }

    public void setModifiedBy(String s)
    {
        modifiedBy = s;
    }

    public String getInstrumentSchemeType()
    {
        return instrumentSchemeType;
    }

    public String getInvestmentRefNumber()
    {
        return investmentRefNumber;
    }

    public String getReceiptNumber()
    {
        return receiptNumber;
    }

    public void setInstrumentDate(Date date)
    {
        instrumentDate = date;
    }

    public void setInstrumentSchemeType(String s)
    {
        instrumentSchemeType = s;
    }

    public void setInvestmentRefNumber(String s)
    {
        investmentRefNumber = s;
    }

    public void setReceiptNumber(String s)
    {
        receiptNumber = s;
    }

    public int getId()
    {
        return id;
    }

    public void setId(int i)
    {
        id = i;
    }

    private String investeeName;
    private String instrumentName;
    private String instrumentSchemeType;
    private String investmentRefNumber;
    private String receiptNumber;
    private String inflowOutFlowFlag;
    private String instrumentType;
    private String instrumentNumber;
    private Date instrumentDate;
    private double instrumentAmount;
    private String drawnBank;
    private String drawnBranch;
    private String payableAt;
    private String modifiedBy;
    private int id;
}
