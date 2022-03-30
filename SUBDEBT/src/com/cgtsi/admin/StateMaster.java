// Decompiled Using: FrontEnd Plus v2.03 and the JAD Engine
// Available From: http://www.reflections.ath.cx
// Decompiler options: packimports(3) 
// Source File Name:   StateMaster.java

package com.cgtsi.admin;

import com.cgtsi.common.DatabaseException;

// Referenced classes of package com.cgtsi.admin:
//            Master, AdminDAO

public class StateMaster extends Master
{

    public StateMaster()
    {
    }

    public String getStateName()
    {
        return stateName;
    }

    public void setStateName(String aStateName)
    {
        stateName = aStateName;
    }

    public void updateMaster()
        throws DatabaseException
    {
        AdminDAO adminDAO = new AdminDAO();
        adminDAO.updateStateMaster(this, getCreatedBy());
    }

    public String getRegionName()
    {
        return regionName;
    }

    public void setRegionName(String string)
    {
        regionName = string;
    }

    public String getStateCode()
    {
        return stateCode;
    }

    public void setStateCode(String string)
    {
        stateCode = string;
    }

    public String getCreatedBy()
    {
        return createdBy;
    }

    public void setCreatedBy(String string)
    {
        createdBy = string;
    }

    private String stateName;
    private String regionName;
    private String stateCode;
    private String createdBy;
}
