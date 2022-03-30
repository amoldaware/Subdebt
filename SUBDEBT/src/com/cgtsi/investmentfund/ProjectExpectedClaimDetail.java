// Decompiled Using: FrontEnd Plus v2.03 and the JAD Engine
// Available From: http://www.reflections.ath.cx
// Decompiler options: packimports(3) 

package com.cgtsi.investmentfund;

import java.util.Date;

public class ProjectExpectedClaimDetail
{

    public ProjectExpectedClaimDetail()
    {
        startDate = null;
        endDate = null;
        cgpan = "";
        outstandingAmount = 0.0D;
        projectedClaimAmount = 0.0D;
    }

    public String getCgpan()
    {
        return cgpan;
    }

    public Date getEndDate()
    {
        return endDate;
    }

    public double getOutstandingAmount()
    {
        return outstandingAmount;
    }

    public double getProjectedClaimAmount()
    {
        return projectedClaimAmount;
    }

    public Date getStartDate()
    {
        return startDate;
    }

    public void setCgpan(String s)
    {
        cgpan = s;
    }

    public void setEndDate(Date date)
    {
        endDate = date;
    }

    public void setOutstandingAmount(double d)
    {
        outstandingAmount = d;
    }

    public void setProjectedClaimAmount(double d)
    {
        projectedClaimAmount = d;
    }

    public void setStartDate(Date date)
    {
        startDate = date;
    }

    private Date startDate;
    private Date endDate;
    private String cgpan;
    private double outstandingAmount;
    private double projectedClaimAmount;
}
