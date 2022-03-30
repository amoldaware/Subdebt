// Decompiled Using: FrontEnd Plus v2.03 and the JAD Engine
// Available From: http://www.reflections.ath.cx
// Decompiler options: packimports(3) 

package com.cgtsi.investmentfund;

import java.util.Date;

public class InvestmentMaturityDetails
{

    public InvestmentMaturityDetails()
    {
        invFlag = "Y";
    }

    public String getInvFlag()
    {
        return invFlag;
    }

    public String getInvName()
    {
        return invName;
    }

    public String getMaturityAmt()
    {
        return maturityAmt;
    }

    public String getOtherDesc()
    {
        return otherDesc;
    }

    public Date getPliDate()
    {
        return pliDate;
    }

    public int getPliId()
    {
        return pliId;
    }

    public Date getPurchaseDate()
    {
        return purchaseDate;
    }

    public void setInvFlag(String s)
    {
        invFlag = s;
    }

    public void setInvName(String s)
    {
        invName = s;
    }

    public void setMaturityAmt(String s)
    {
        maturityAmt = s;
    }

    public void setOtherDesc(String s)
    {
        otherDesc = s;
    }

    public void setPliDate(Date date)
    {
        pliDate = date;
    }

    public void setPliId(int i)
    {
        pliId = i;
    }

    public void setPurchaseDate(Date date)
    {
        purchaseDate = date;
    }

    public String getBuySellId()
    {
        return buySellId;
    }

    public void setBuySellId(String s)
    {
        buySellId = s;
    }

    private int pliId;
    private String invName;
    private String maturityAmt;
    private String invFlag;
    private Date pliDate;
    private String otherDesc;
    private Date purchaseDate;
    private String buySellId;
}
