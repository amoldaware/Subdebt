// Decompiled Using: FrontEnd Plus v2.03 and the JAD Engine
// Available From: http://www.reflections.ath.cx
// Decompiler options: packimports(3) 

package com.cgtsi.investmentfund;


public class InvesteeDetail
{

    public InvesteeDetail()
    {
    }

    public String getSInvesteeId()
    {
        return sInvesteeId;
    }

    public void setSInvesteeId(String s)
    {
        sInvesteeId = s;
    }

    public String getInvesteeGroup()
    {
        return investeeGroup;
    }

    public void setInvesteeGroup(String s)
    {
        investeeGroup = s;
    }

    public String getInvestee()
    {
        return investee;
    }

    public void setInvestee(String s)
    {
        investee = s;
    }

    public double getInvesteeTangibleAssets()
    {
        return investeeTangibleAssets;
    }

    public void setInvesteeTangibleAssets(double d)
    {
        investeeTangibleAssets = d;
    }

    public double getInvesteeNetWorth()
    {
        return investeeNetWorth;
    }

    public void setInvesteeNetWorth(double d)
    {
        investeeNetWorth = d;
    }

    public String getModInvestee()
    {
        return modInvestee;
    }

    public String getNewInvestee()
    {
        return newInvestee;
    }

    public void setModInvestee(String s)
    {
        modInvestee = s;
    }

    public void setNewInvestee(String s)
    {
        newInvestee = s;
    }

    private String sInvesteeId;
    private String investeeGroup;
    private String investee;
    private String newInvestee;
    private String modInvestee;
    private double investeeTangibleAssets;
    private double investeeNetWorth;
}
