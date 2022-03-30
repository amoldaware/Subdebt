// Decompiled Using: FrontEnd Plus v2.03 and the JAD Engine
// Available From: http://www.reflections.ath.cx
// Decompiler options: packimports(3) 

package com.cgtsi.investmentfund;

import java.io.Serializable;

public class InstrumentFeature
    implements Serializable
{

    public InstrumentFeature()
    {
        instrumentFeatures = "";
        instrumentFeatureDescription = "";
        newInstrumentFeatures = "";
        modInstrumentFeatures = "";
    }

    public String getInstrumentFeatureDescription()
    {
        return instrumentFeatureDescription;
    }

    public String getInstrumentFeatures()
    {
        return instrumentFeatures;
    }

    public void setInstrumentFeatureDescription(String s)
    {
        instrumentFeatureDescription = s;
    }

    public void setInstrumentFeatures(String s)
    {
        instrumentFeatures = s;
    }

    public String getModInstrumentFeatures()
    {
        return modInstrumentFeatures;
    }

    public String getNewInstrumentFeatures()
    {
        return newInstrumentFeatures;
    }

    public void setModInstrumentFeatures(String s)
    {
        modInstrumentFeatures = s;
    }

    public void setNewInstrumentFeatures(String s)
    {
        newInstrumentFeatures = s;
    }

    private String instrumentFeatures;
    private String instrumentFeatureDescription;
    private String newInstrumentFeatures;
    private String modInstrumentFeatures;
}
