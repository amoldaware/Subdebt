// Decompiled Using: FrontEnd Plus v2.03 and the JAD Engine
// Available From: http://www.reflections.ath.cx
// Decompiler options: packimports(3) 
// Source File Name:   SchemeMaster.java

package com.cgtsi.admin;


// Referenced classes of package com.cgtsi.admin:
//            Master

public class SchemeMaster extends Master
{

    public SchemeMaster()
    {
    }

    public String getSchemeName()
    {
        return schemeName;
    }

    public void setSchemeName(String aSchemeName)
    {
        schemeName = aSchemeName;
    }

    public Double getPlrInterestDiff()
    {
        return plrInterestDiff;
    }

    public void setPlrInterestDiff(Double aPlrInterestDiff)
    {
        plrInterestDiff = aPlrInterestDiff;
    }

    public Double getGuaranteeFeeRate()
    {
        return guaranteeFeeRate;
    }

    public void setGuaranteeFeeRate(Double aGuaranteeFeeRate)
    {
        guaranteeFeeRate = aGuaranteeFeeRate;
    }

    public Double getServiceFeeRate()
    {
        return serviceFeeRate;
    }

    public void setServiceFeeRate(Double aServiceFeeRate)
    {
        serviceFeeRate = aServiceFeeRate;
    }

    public Boolean getCollateralAllowed()
    {
        return collateralAllowed;
    }

    public void setCollateralAllowed(Boolean aCollateralAllowed)
    {
        collateralAllowed = aCollateralAllowed;
    }

    public Boolean getThirdPartyGuarantee()
    {
        return thirdPartyGuarantee;
    }

    public void setThirdPartyGuarantee(Boolean aThirdPartyGuarantee)
    {
        thirdPartyGuarantee = aThirdPartyGuarantee;
    }

    public Double getMinAmt()
    {
        return minAmt;
    }

    public void setMinAmt(Double aMinAmt)
    {
        minAmt = aMinAmt;
    }

    public Double getMaxAmt()
    {
        return maxAmt;
    }

    public void setMaxAmt(Double aMaxAmt)
    {
        maxAmt = aMaxAmt;
    }

    public Integer getGender()
    {
        return gender;
    }

    public void setGender(Integer aGender)
    {
        gender = aGender;
    }

    public Integer getRegion()
    {
        return region;
    }

    public void setRegion(Integer aRegion)
    {
        region = aRegion;
    }

    public Double getDefaultRate()
    {
        return defaultRate;
    }

    public void setDefaultRate(Double aDefaultRate)
    {
        defaultRate = aDefaultRate;
    }

    public Double getGuaranteeCoverExt()
    {
        return guaranteeCoverExt;
    }

    public void setGuaranteeCoverExt(Double aGuaranteeCoverExt)
    {
        guaranteeCoverExt = aGuaranteeCoverExt;
    }

    public Integer getLockInPeriod()
    {
        return lockInPeriod;
    }

    public void setLockInPeriod(Integer aLockInPeriod)
    {
        lockInPeriod = aLockInPeriod;
    }

    public Double getPenaltyRate()
    {
        return penaltyRate;
    }

    public void setPenaltyRate(Double aPenaltyRate)
    {
        penaltyRate = aPenaltyRate;
    }

    public Integer getScheme_id()
    {
        return scheme_id;
    }

    public void setScheme_id(Integer aScheme_id)
    {
        scheme_id = aScheme_id;
    }

    public double getServiceFeePenaltyRate(String cgpan)
    {
        return 0.0D;
    }

    private String schemeName;
    private Double plrInterestDiff;
    private Double guaranteeFeeRate;
    private Double serviceFeeRate;
    private Boolean collateralAllowed;
    private Boolean thirdPartyGuarantee;
    private Double minAmt;
    private Double maxAmt;
    private Integer gender;
    private Integer region;
    private Double defaultRate;
    private Double guaranteeCoverExt;
    private Integer lockInPeriod;
    private Double penaltyRate;
    private Integer scheme_id;
}
