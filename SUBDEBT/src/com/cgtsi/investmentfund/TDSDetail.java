// Decompiled Using: FrontEnd Plus v2.03 and the JAD Engine
// Available From: http://www.reflections.ath.cx
// Decompiler options: packimports(3) 

package com.cgtsi.investmentfund;

import java.util.Date;

public class TDSDetail
{

    public TDSDetail()
    {
    }

    public String getInvestmentRefNumber()
    {
        return investmentRefNumber;
    }

    public void setInvestmentRefNumber(String s)
    {
        investmentRefNumber = s;
    }

    public double getTDSAmount()
    {
        return tdsAmount;
    }

    public void setTDSAmount(double d)
    {
        tdsAmount = d;
    }

    public Date getReminderDate()
    {
        return reminderDate;
    }

    public void setReminderDate(Date date)
    {
        reminderDate = date;
    }

    public String getTDSCertificateReceivedORNot()
    {
        return isTDSCertificateReceived;
    }

    public void setTDSCertificateReceivedORNot(String s)
    {
        isTDSCertificateReceived = s;
    }

    public Date getTDSDeductedDate()
    {
        return tdsDeductedDate;
    }

    public void setTDSDeductedDate(Date date)
    {
        tdsDeductedDate = date;
    }

    public String getModifiedBy()
    {
        return modifiedBy;
    }

    public void setModifiedBy(String s)
    {
        modifiedBy = s;
    }

    public String getIsTDSCertificateReceived()
    {
        return isTDSCertificateReceived;
    }

    public double getTdsAmount()
    {
        return tdsAmount;
    }

    public Date getTdsDeductedDate()
    {
        return tdsDeductedDate;
    }

    public void setIsTDSCertificateReceived(String s)
    {
        isTDSCertificateReceived = s;
    }

    public void setTdsAmount(double d)
    {
        tdsAmount = d;
    }

    public void setTdsDeductedDate(Date date)
    {
        tdsDeductedDate = date;
    }

    public String getTdsID()
    {
        return tdsID;
    }

    public void setTdsID(String s)
    {
        tdsID = s;
    }

    public String getInstrumentName()
    {
        return instrumentName;
    }

    public String getInvesteeName()
    {
        return investeeName;
    }

    public void setInstrumentName(String s)
    {
        instrumentName = s;
    }

    public void setInvesteeName(String s)
    {
        investeeName = s;
    }

    private String investmentRefNumber;
    private double tdsAmount;
    private Date reminderDate;
    private Date tdsDeductedDate;
    private String isTDSCertificateReceived;
    private String modifiedBy;
    private String tdsID;
    private String investeeName;
    private String instrumentName;
}
