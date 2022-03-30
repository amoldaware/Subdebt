// Decompiled Using: FrontEnd Plus v2.03 and the JAD Engine
// Available From: http://www.reflections.ath.cx
// Decompiler options: packimports(3) 

package com.cgtsi.investmentfund;

import java.util.Date;

public class PaymentDetails
{

    public PaymentDetails()
    {
    }

    public String getUserId()
    {
        return userId;
    }

    public void setUserId(String s)
    {
        userId = s;
    }

    public String getPayId()
    {
        return payId;
    }

    public void setPayId(String s)
    {
        payId = s;
    }

    public String getPaymentsTo()
    {
        return paymentsTo;
    }

    public void setPaymentsTo(String s)
    {
        paymentsTo = s;
    }

    public double getAmount()
    {
        return amount;
    }

    public void setAmount(double d)
    {
        amount = d;
    }

    public String getRemarks()
    {
        return remarks;
    }

    public void setRemarks(String s)
    {
        remarks = s;
    }

    public Date getToDate()
    {
        return toDate;
    }

    public void setToDate(Date date)
    {
        toDate = date;
    }

    public Date getFromDate()
    {
        return fromDate;
    }

    public void setFromDate(Date date)
    {
        fromDate = date;
    }

    public Date getPaymentDate()
    {
        return paymentDate;
    }

    public void setPaymentDate(Date date)
    {
        paymentDate = date;
    }

    private String payId;
    private String paymentsTo;
    private double amount;
    private String remarks;
    private Date toDate;
    private Date fromDate;
    private Date paymentDate;
    private String userId;
}
