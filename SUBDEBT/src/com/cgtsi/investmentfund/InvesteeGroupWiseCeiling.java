// Decompiled Using: FrontEnd Plus v2.03 and the JAD Engine
// Available From: http://www.reflections.ath.cx
// Decompiler options: packimports(3) 

package com.cgtsi.investmentfund;

import java.io.Serializable;
import java.util.Date;

public class InvesteeGroupWiseCeiling
    implements Serializable
{

    public InvesteeGroupWiseCeiling()
    {
    }

    public double getCeilingAmount()
    {
        return ceilingAmount;
    }

    public Date getCeilingEndDate()
    {
        return ceilingEndDate;
    }

    public double getCeilingLimit()
    {
        return ceilingLimit;
    }

    public Date getCeilingStartDate()
    {
        return ceilingStartDate;
    }

    public String getInvesteeGroup()
    {
        return investeeGroup;
    }

    public void setCeilingAmount(double d)
    {
        ceilingAmount = d;
    }

    public void setCeilingEndDate(Date date)
    {
        ceilingEndDate = date;
    }

    public void setCeilingLimit(double d)
    {
        ceilingLimit = d;
    }

    public void setCeilingStartDate(Date date)
    {
        ceilingStartDate = date;
    }

    public void setInvesteeGroup(String s)
    {
        investeeGroup = s;
    }

    private String investeeGroup;
    private Date ceilingStartDate;
    private Date ceilingEndDate;
    private double ceilingLimit;
    private double ceilingAmount;
}
