// Decompiled Using: FrontEnd Plus v2.03 and the JAD Engine
// Available From: http://www.reflections.ath.cx
// Decompiler options: packimports(3) 

package com.cgtsi.investmentfund;


public class MaturityDetail
{

    public MaturityDetail()
    {
    }

    public String getSMaturityId()
    {
        return sMaturityId;
    }

    public void setSMaturityId(String s)
    {
        sMaturityId = s;
    }

    public String getModMaturityType()
    {
        return modMaturityType;
    }

    public String getNewMaturityType()
    {
        return newMaturityType;
    }

    public void setModMaturityType(String s)
    {
        modMaturityType = s;
    }

    public void setNewMaturityType(String s)
    {
        newMaturityType = s;
    }

    public String getMaturityDescription()
    {
        return maturityDescription;
    }

    public String getMaturityType()
    {
        return maturityType;
    }

    public void setMaturityDescription(String s)
    {
        maturityDescription = s;
    }

    public void setMaturityType(String s)
    {
        maturityType = s;
    }

    private String maturityType;
    private String maturityDescription;
    private String sMaturityId;
    private String newMaturityType;
    private String modMaturityType;
}
