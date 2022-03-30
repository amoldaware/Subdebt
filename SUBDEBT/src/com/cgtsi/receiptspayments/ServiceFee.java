// Decompiled Using: FrontEnd Plus v2.03 and the JAD Engine
// Available From: http://www.reflections.ath.cx
// Decompiler options: packimports(3) 
// Source File Name:   ServiceFee.java

package com.cgtsi.receiptspayments;

import java.util.Date;

public class ServiceFee
{

    public ServiceFee()
    {
    }

    public ServiceFee(String cgpan, double serviceAmount)
    {
        this.serviceAmount = serviceAmount;
        this.cgpan = cgpan;
    }

    public String getCgpan()
    {
        return cgpan;
    }

    public void setCgpan(String aCgpan)
    {
        cgpan = aCgpan;
    }

    public double getServiceAmount()
    {
        return serviceAmount;
    }

    public void setServiceAmount(double aServiceAmount)
    {
        serviceAmount = aServiceAmount;
    }

    public Date getFromDate()
    {
        return fromDate;
    }

    public void setFromDate(Date aFromDate)
    {
        fromDate = aFromDate;
    }

    public Date getToDate()
    {
        return toDate;
    }

    public void setToDate(Date aToDate)
    {
        toDate = aToDate;
    }

    public String getBankId()
    {
        return bankId;
    }

    public void setBankId(String aBankId)
    {
        bankId = aBankId;
    }

    public String getZoneId()
    {
        return zoneId;
    }

    public void setZoneId(String aZoneId)
    {
        zoneId = aZoneId;
    }

    public String getBranchId()
    {
        return branchId;
    }

    public void setBranchId(String aBranchId)
    {
        branchId = aBranchId;
    }

    public double getServiceFeeId()
    {
        return serviceFeeId;
    }

    public String getBorrowerId()
    {
        return borrowerId;
    }

    public void setBorrowerId(String string)
    {
        borrowerId = string;
    }

    public void setServiceFeeId(double d)
    {
        serviceFeeId = d;
    }

    private String cgpan;
    private double serviceAmount;
    private Date fromDate;
    private Date toDate;
    private String bankId;
    private String zoneId;
    private String branchId;
    private double serviceFeeId;
    private String borrowerId;
}
