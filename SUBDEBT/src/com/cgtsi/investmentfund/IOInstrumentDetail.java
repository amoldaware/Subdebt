// Decompiled Using: FrontEnd Plus v2.03 and the JAD Engine
// Available From: http://www.reflections.ath.cx
// Decompiler options: packimports(3) 

package com.cgtsi.investmentfund;

import java.util.Date;

public class IOInstrumentDetail
{

    public IOInstrumentDetail()
    {
    }

    public String getIsChequePaidOrReceived()
    {
        return chequePaidOrReceived;
    }

    public void setIsChequePaidOrReceived(String s)
    {
        chequePaidOrReceived = s;
    }

    public String getChequeNumber()
    {
        return chequeNumber;
    }

    public void setChequeNumber(String s)
    {
        chequeNumber = s;
    }

    public double getChequeAmount()
    {
        return chequeAmount;
    }

    public void setChequeAmount(double d)
    {
        chequeAmount = d;
    }

    public Date getChequeDated()
    {
        return chequeDated;
    }

    public void setChequeDated(Date date)
    {
        chequeDated = date;
    }

    public String getInvestmentId()
    {
        return investmentId;
    }

    public void setInvestmentId(String s)
    {
        investmentId = s;
    }

    private String chequePaidOrReceived;
    private String chequeNumber;
    private double chequeAmount;
    private Date chequeDated;
    private String investmentId;
}
