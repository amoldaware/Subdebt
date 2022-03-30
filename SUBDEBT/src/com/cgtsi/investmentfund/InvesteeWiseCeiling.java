// Decompiled Using: FrontEnd Plus v2.03 and the JAD Engine
// Available From: http://www.reflections.ath.cx
// Decompiler options: packimports(3) 

package com.cgtsi.investmentfund;

import java.io.Serializable;
import java.util.Date;

public class InvesteeWiseCeiling
    implements Serializable
{

    public InvesteeWiseCeiling()
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

    public Date getCeilingStartDate()
    {
        return ceilingStartDate;
    }

    public String getInvesteeGroup()
    {
        return investeeGroup;
    }

    public String getInvesteeName()
    {
        return investeeName;
    }

    public double getNetworth()
    {
        return networth;
    }

    public double getTangibleAssets()
    {
        return tangibleAssets;
    }

    public void setCeilingAmount(double d)
    {
        ceilingAmount = d;
    }

    public void setCeilingEndDate(Date date)
    {
        ceilingEndDate = date;
    }

    public void setCeilingStartDate(Date date)
    {
        ceilingStartDate = date;
    }

    public void setInvesteeGroup(String s)
    {
        investeeGroup = s;
    }

    public void setInvesteeName(String s)
    {
        investeeName = s;
    }

    public void setNetworth(double d)
    {
        networth = d;
    }

    public void setTangibleAssets(double d)
    {
        tangibleAssets = d;
    }

    public double getCeilingLimit()
    {
        return ceilingLimit;
    }

    public void setCeilingLimit(double d)
    {
        ceilingLimit = d;
    }

    private String investeeGroup;
    private String investeeName;
    private Date ceilingStartDate;
    private Date ceilingEndDate;
    private double networth;
    private double tangibleAssets;
    private double ceilingAmount;
    private double ceilingLimit;
}
