// Decompiled Using: FrontEnd Plus v2.03 and the JAD Engine
// Available From: http://www.reflections.ath.cx
// Decompiler options: packimports(3) 

package com.cgtsi.investmentfund;

import java.io.Serializable;

public class InstrumentDetail
    implements Serializable
{

    public InstrumentDetail()
    {
        instrumentName = "";
        instrumentDescription = "";
        instrumentType = "";
        newInstrumentName = "";
        modInstrumentName = "";
    }

    public String getInstrumentDescription()
    {
        return instrumentDescription;
    }

    public String getInstrumentName()
    {
        return instrumentName;
    }

    public String getInstrumentType()
    {
        return instrumentType;
    }

    public void setInstrumentDescription(String s)
    {
        instrumentDescription = s;
    }

    public void setInstrumentName(String s)
    {
        instrumentName = s;
    }

    public void setInstrumentType(String s)
    {
        instrumentType = s;
    }

    public String getModInstrumentName()
    {
        return modInstrumentName;
    }

    public String getNewInstrumentName()
    {
        return newInstrumentName;
    }

    public void setModInstrumentName(String s)
    {
        modInstrumentName = s;
    }

    public void setNewInstrumentName(String s)
    {
        newInstrumentName = s;
    }

    private String instrumentName;
    private String instrumentDescription;
    private String instrumentType;
    private String newInstrumentName;
    private String modInstrumentName;
}
