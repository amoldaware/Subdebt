// Decompiled Using: FrontEnd Plus v2.03 and the JAD Engine
// Available From: http://www.reflections.ath.cx
// Decompiler options: packimports(3) 
// Source File Name:   ScheduledProcessManager.java

package com.cgtsi.admin;

import com.cgtsi.application.NoApplicationFoundException;
import com.cgtsi.common.DatabaseException;
import com.cgtsi.common.NoUserFoundException;
import com.cgtsi.receiptspayments.RpProcessor;

// Referenced classes of package com.cgtsi.admin:
//            User, ScheduledProcessDAO, Administrator

public class ScheduledProcessManager
{

    public ScheduledProcessManager()
    {
    }

    public void updateData()
        throws DatabaseException
    {
        try
        {
            RpProcessor rpProcessor = new RpProcessor();
            User user = new User();
            user.setUserId("ADMIN");
            rpProcessor.calculatePenaltyForOverdueDANs(user);
        }
        catch(Exception exception)
        {
            throw new DatabaseException(exception.getMessage());
        }
    }

    public void adminMailInfo()
        throws DatabaseException
    {
        ScheduledProcessDAO scheduledProcessDAO = new ScheduledProcessDAO();
        scheduledProcessDAO.adminMailInfo();
    }

    public void tcOutstanding()
        throws DatabaseException
    {
        ScheduledProcessDAO scheduledProcessDAO = new ScheduledProcessDAO();
        scheduledProcessDAO.tcOutstanding();
    }

    public void wcOutstanding()
        throws DatabaseException
    {
        ScheduledProcessDAO scheduledProcessDAO = new ScheduledProcessDAO();
        scheduledProcessDAO.wcOutstanding();
    }

    public void dbrDetail()
        throws DatabaseException
    {
        ScheduledProcessDAO scheduledProcessDAO = new ScheduledProcessDAO();
        scheduledProcessDAO.dbrDetail();
    }

    public void donorDetail()
        throws DatabaseException
    {
        ScheduledProcessDAO scheduledProcessDAO = new ScheduledProcessDAO();
        scheduledProcessDAO.donorDetail();
    }

    public void legalDetail()
        throws DatabaseException
    {
        ScheduledProcessDAO scheduledProcessDAO = new ScheduledProcessDAO();
        scheduledProcessDAO.legalDetail();
    }

    public void memberInfo()
        throws DatabaseException
    {
        ScheduledProcessDAO scheduledProcessDAO = new ScheduledProcessDAO();
        scheduledProcessDAO.memberInfo();
    }

    public void npaDetail()
        throws DatabaseException
    {
        ScheduledProcessDAO scheduledProcessDAO = new ScheduledProcessDAO();
        scheduledProcessDAO.npaDetail();
    }

    public void plr()
        throws DatabaseException
    {
        ScheduledProcessDAO scheduledProcessDAO = new ScheduledProcessDAO();
        scheduledProcessDAO.plr();
    }

    public void recoveryDetail(User user)
        throws DatabaseException
    {
        ScheduledProcessDAO scheduledProcessDAO = new ScheduledProcessDAO();
        scheduledProcessDAO.recoveryDetail(user);
    }

    public void recoveryActionDetail()
        throws DatabaseException
    {
        ScheduledProcessDAO scheduledProcessDAO = new ScheduledProcessDAO();
        scheduledProcessDAO.recoveryActionDetail();
    }

    public void repaymentDetail()
        throws DatabaseException
    {
        ScheduledProcessDAO scheduledProcessDAO = new ScheduledProcessDAO();
        scheduledProcessDAO.repaymentDetail();
    }

    public void repaymentSchemeDetail()
        throws DatabaseException
    {
        ScheduledProcessDAO scheduledProcessDAO = new ScheduledProcessDAO();
        scheduledProcessDAO.repaymentSchemeDetail();
    }

    public void userInfo()
        throws DatabaseException
    {
        ScheduledProcessDAO scheduledProcessDAO = new ScheduledProcessDAO();
        scheduledProcessDAO.userInfo();
    }

    public void userActiveDeactiveLog()
        throws DatabaseException
    {
        ScheduledProcessDAO scheduledProcessDAO = new ScheduledProcessDAO();
        scheduledProcessDAO.userActiveDeactiveLog();
    }

    public void procRemDemoUser()
        throws DatabaseException
    {
        ScheduledProcessDAO scheduledProcessDAO = new ScheduledProcessDAO();
        scheduledProcessDAO.procRemDemoUser();
    }

    public void updateAppExpiry()
        throws DatabaseException
    {
        ScheduledProcessDAO scheduledProcessDAO = new ScheduledProcessDAO();
        scheduledProcessDAO.updateAppExpiry();
    }

    public void outstandingDetail()
        throws DatabaseException
    {
        ScheduledProcessDAO scheduledProcessDAO = new ScheduledProcessDAO();
        scheduledProcessDAO.outstandingDetail();
    }

    public void generateCGDAN()
        throws NoApplicationFoundException, NoUserFoundException, DatabaseException
    {
        RpProcessor rpProcessor = new RpProcessor();
        Administrator administrator = new Administrator();
        User user = new User();
        String userId = "ADMIN";
        user = administrator.getUserInfo(userId);
        String memberId = null;
        rpProcessor.generateCGDAN(user, "All");
    }

    public void generateCLDAN()
        throws NoUserFoundException, DatabaseException
    {
        RpProcessor rpProcessor = new RpProcessor();
        Administrator administrator = new Administrator();
        User user = new User();
        String userId = "ADMIN";
        user = administrator.getUserInfo(userId);
        String bankId = null;
        String zoneId = null;
        String branchId = null;
        rpProcessor.generateCLDAN(user, bankId, zoneId, branchId);
    }

    public void generateSFDAN()
        throws NoUserFoundException, DatabaseException
    {
        RpProcessor rpProcessor = new RpProcessor();
        Administrator administrator = new Administrator();
        User user = new User();
        String userId = "ADMIN";
        user = administrator.getUserInfo(userId);
        String bankId = null;
        String zoneId = null;
        String branchId = null;
        rpProcessor.generateSFDAN(user, bankId, zoneId, branchId);
    }

    public void generateSHDAN()
        throws NoUserFoundException, DatabaseException
    {
        RpProcessor rpProcessor = new RpProcessor();
        Administrator administrator = new Administrator();
        User user = new User();
        String userId = "ADMIN";
        user = administrator.getUserInfo(userId);
        String memberId = null;
        rpProcessor.generateSHDAN(user, memberId);
    }

    public void userRoles()
        throws DatabaseException
    {
        ScheduledProcessDAO scheduledProcessDAO = new ScheduledProcessDAO();
        scheduledProcessDAO.userRoles();
    }

    public void userPrivileges()
        throws DatabaseException
    {
        ScheduledProcessDAO scheduledProcessDAO = new ScheduledProcessDAO();
        scheduledProcessDAO.userPrivileges();
    }
}
