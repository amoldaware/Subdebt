// Decompiled Using: FrontEnd Plus v2.03 and the JAD Engine
// Available From: http://www.reflections.ath.cx
// Decompiler options: packimports(3) 

package com.cgtsi.investmentfund;


public class RatingDetails
{

    public RatingDetails()
    {
    }

    public String getRating()
    {
        return Rating;
    }

    public String getRatingAgency()
    {
        return RatingAgency;
    }

    public String getRatingDescription()
    {
        return RatingDescription;
    }

    public void setRating(String s)
    {
        Rating = s;
    }

    public void setRatingAgency(String s)
    {
        RatingAgency = s;
    }

    public void setRatingDescription(String s)
    {
        RatingDescription = s;
    }

    public String getModAgencyDesc()
    {
        return modAgencyDesc;
    }

    public String getModAgencyName()
    {
        return modAgencyName;
    }

    public String getNewAgency()
    {
        return newAgency;
    }

    public void setModAgencyDesc(String s)
    {
        modAgencyDesc = s;
    }

    public void setModAgencyName(String s)
    {
        modAgencyName = s;
    }

    public void setNewAgency(String s)
    {
        newAgency = s;
    }

    public String getUser()
    {
        return user;
    }

    public void setUser(String s)
    {
        user = s;
    }

    public String getAgencyId()
    {
        return agencyId;
    }

    public void setAgencyId(String s)
    {
        agencyId = s;
    }

    public String getAgency()
    {
        return agency;
    }

    public void setAgency(String s)
    {
        agency = s;
    }

    public String getStatus()
    {
        return status;
    }

    public void setStatus(String s)
    {
        status = s;
    }

    private String Rating;
    private String RatingAgency;
    private String RatingDescription;
    private String newAgency;
    private String modAgencyName;
    private String modAgencyDesc;
    private String user;
    private String agencyId;
    private String agency;
    private String status;
}
