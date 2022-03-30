// Decompiled Using: FrontEnd Plus v2.03 and the JAD Engine
// Available From: http://www.reflections.ath.cx
// Decompiler options: packimports(3) 

package com.cgtsi.investmentfund;

import java.io.Serializable;

public class ROIInfo
    implements Serializable
{

    public ROIInfo()
    {
        noOfYears = 0;
        noOfUnits = 0;
        navDifference = 0.0D;
    }

    public String getInstrumentName()
    {
        return instrumentName;
    }

    public double getRateOfInterest()
    {
        return rateOfInterest;
    }

    public void setInstrumentName(String s)
    {
        instrumentName = s;
    }

    public void setRateOfInterest(double d)
    {
        rateOfInterest = d;
    }

    public double getPrincipalAmount()
    {
        return principalAmount;
    }

    public void setPrincipalAmount(double d)
    {
        principalAmount = d;
    }

    public int getNoOfDays()
    {
        return noOfDays;
    }

    public void setNoOfDays(int i)
    {
        noOfDays = i;
    }

    public double getNavDifference()
    {
        return navDifference;
    }

    public int getNoOfUnits()
    {
        return noOfUnits;
    }

    public int getNoOfYears()
    {
        return noOfYears;
    }

    public void setNavDifference(double d)
    {
        navDifference = d;
    }

    public void setNoOfUnits(int i)
    {
        noOfUnits = i;
    }

    public void setNoOfYears(int i)
    {
        noOfYears = i;
    }

    private String instrumentName;
    private double rateOfInterest;
    private double principalAmount;
    private int noOfDays;
    private int noOfYears;
    private int noOfUnits;
    private double navDifference;
}
