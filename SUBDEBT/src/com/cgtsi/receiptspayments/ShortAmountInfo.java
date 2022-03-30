// Decompiled Using: FrontEnd Plus v2.03 and the JAD Engine
// Available From: http://www.reflections.ath.cx
// Decompiler options: packimports(3) 
// Source File Name:   ShortAmountInfo.java

package com.cgtsi.receiptspayments;


public class ShortAmountInfo
{

    public ShortAmountInfo()
    {
        danNo = "";
        cgpan = "";
        bankId = "";
        zoneId = "";
        branchId = "";
        borrowerId = "";
    }

    public String getCgpan()
    {
        return cgpan;
    }

    public String getDanNo()
    {
        return danNo;
    }

    public double getShortAmount()
    {
        return shortAmount;
    }

    public double getShortId()
    {
        return shortId;
    }

    public void setCgpan(String string)
    {
        cgpan = string;
    }

    public void setDanNo(String string)
    {
        danNo = string;
    }

    public void setShortAmount(double d)
    {
        shortAmount = d;
    }

    public void setShortId(double d)
    {
        shortId = d;
    }

    public String getBankId()
    {
        return bankId;
    }

    public String getBorrowerId()
    {
        return borrowerId;
    }

    public String getBranchId()
    {
        return branchId;
    }

    public String getZoneId()
    {
        return zoneId;
    }

    public void setBankId(String string)
    {
        bankId = string;
    }

    public void setBorrowerId(String string)
    {
        borrowerId = string;
    }

    public void setBranchId(String string)
    {
        branchId = string;
    }

    public void setZoneId(String string)
    {
        zoneId = string;
    }

    private double shortId;
    private String danNo;
    private String cgpan;
    private double shortAmount;
    private String bankId;
    private String zoneId;
    private String branchId;
    private String borrowerId;
}
