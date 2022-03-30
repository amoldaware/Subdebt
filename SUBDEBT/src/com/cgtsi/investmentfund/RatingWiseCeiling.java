// Decompiled Using: FrontEnd Plus v2.03 and the JAD Engine
// Available From: http://www.reflections.ath.cx
// Decompiler options: packimports(3) 

package com.cgtsi.investmentfund;

import java.io.Serializable;
import java.util.Date;

public class RatingWiseCeiling
    implements Serializable
{

    public RatingWiseCeiling()
    {
    }

    public Date getCeilingEndDate()
    {
        return ceilingEndDate;
    }

    public Date getCeilingStartDate()
    {
        return ceilingStartDate;
    }

    public String getInstrumentName()
    {
        return instrumentName;
    }

    public String getInvesteeGroup()
    {
        return investeeGroup;
    }

    public String getInvesteeName()
    {
        return investeeName;
    }

    public String getRating()
    {
        return rating;
    }

    public String getRatingAgency()
    {
        return ratingAgency;
    }

    public void setCeilingEndDate(Date date)
    {
        ceilingEndDate = date;
    }

    public void setCeilingStartDate(Date date)
    {
        ceilingStartDate = date;
    }

    public void setInstrumentName(String s)
    {
        instrumentName = s;
    }

    public void setInvesteeGroup(String s)
    {
        investeeGroup = s;
    }

    public void setInvesteeName(String s)
    {
        investeeName = s;
    }

    public void setRating(String s)
    {
        rating = s;
    }

    public void setRatingAgency(String s)
    {
        ratingAgency = s;
    }

    private String investeeGroup;
    private String investeeName;
    private String instrumentName;
    private String rating;
    private String ratingAgency;
    private Date ceilingStartDate;
    private Date ceilingEndDate;
}
