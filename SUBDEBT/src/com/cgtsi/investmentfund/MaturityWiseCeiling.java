// Decompiled Using: FrontEnd Plus v2.03 and the JAD Engine
// Available From: http://www.reflections.ath.cx
// Decompiler options: packimports(3) 

package com.cgtsi.investmentfund;

import java.io.Serializable;
import java.util.Date;

public class MaturityWiseCeiling
    implements Serializable
{

    public MaturityWiseCeiling()
    {
        ceilingEndDate = null;
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

    public String getMaturityType()
    {
        return maturityType;
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

    public void setMaturityType(String s)
    {
        maturityType = s;
    }

    private String maturityType;
    private Date ceilingStartDate;
    private Date ceilingEndDate;
    private double ceilingLimit;
}
