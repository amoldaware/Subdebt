// Decompiled Using: FrontEnd Plus v2.03 and the JAD Engine
// Available From: http://www.reflections.ath.cx
// Decompiler options: packimports(3) 
// Source File Name:   UpdateServlet.java

package com.cgtsi.admin;

import com.cgtsi.common.Log;
import java.io.PrintWriter;
import javax.servlet.ServletException;
import javax.servlet.http.*;

// Referenced classes of package com.cgtsi.admin:
//            ScheduledProcessManager, User

public class UpdateServlet extends HttpServlet
{

    public UpdateServlet()
    {
    }

    public void doPost(HttpServletRequest request, HttpServletResponse response)
        throws ServletException
    {
        ScheduledProcessManager scheduledProcess = new ScheduledProcessManager();
        User user = new User();
        user.setUserId("ADMIN");
        try
        {
            Log.log(4, "UpdateServlet", "doPost", "Entered");
            Log.log(4, "UpdateServlet", "doPost", "calling outstandingDetail");
            scheduledProcess.outstandingDetail();
            Log.log(4, "UpdateServlet", "doPost", "calling updateData");
            scheduledProcess.updateData();
            Log.log(4, "UpdateServlet", "doPost", "calling adminMailInfo");
            scheduledProcess.adminMailInfo();
            Log.log(4, "UpdateServlet", "doPost", "calling dbrDetail");
            scheduledProcess.dbrDetail();
            Log.log(4, "UpdateServlet", "doPost", "calling donorDetail");
            scheduledProcess.donorDetail();
            Log.log(4, "UpdateServlet", "doPost", "calling legalDetail");
            scheduledProcess.legalDetail();
            Log.log(4, "UpdateServlet", "doPost", "calling memberInfo");
            scheduledProcess.memberInfo();
            Log.log(4, "UpdateServlet", "doPost", "calling npaDetail");
            scheduledProcess.npaDetail();
            Log.log(4, "UpdateServlet", "doPost", "calling plr");
            scheduledProcess.plr();
            Log.log(4, "UpdateServlet", "doPost", "calling recoveryDetail");
            scheduledProcess.recoveryDetail(user);
            Log.log(4, "UpdateServlet", "doPost", "calling recoveryActionDetail");
            scheduledProcess.recoveryActionDetail();
            Log.log(4, "UpdateServlet", "doPost", "calling repaymentDetail");
            scheduledProcess.repaymentDetail();
            Log.log(4, "UpdateServlet", "doPost", "calling repaymentSchemeDetail");
            scheduledProcess.repaymentSchemeDetail();
            Log.log(4, "UpdateServlet", "doPost", "calling userInfo");
            scheduledProcess.userInfo();
            Log.log(4, "UpdateServlet", "doPost", "calling userActiveDeactiveLog");
            scheduledProcess.userActiveDeactiveLog();
            Log.log(4, "UpdateServlet", "doPost", "calling procRemDemoUser");
            scheduledProcess.procRemDemoUser();
            Log.log(4, "UpdateServlet", "doPost", "calling updateAppExpiry");
            scheduledProcess.updateAppExpiry();
            Log.log(4, "UpdateServlet", "doPost", "calling updateAppExpiry");
            scheduledProcess.generateCGDAN();
            Log.log(4, "UpdateServlet", "doPost", "calling updateAppExpiry");
            scheduledProcess.generateCLDAN();
            Log.log(4, "UpdateServlet", "doPost", "calling updateAppExpiry");
            scheduledProcess.generateSFDAN();
            Log.log(4, "UpdateServlet", "doPost", "calling updateAppExpiry");
            scheduledProcess.generateSHDAN();
            Log.log(4, "UpdateServlet", "doPost", "calling userRoles");
            scheduledProcess.userRoles();
            Log.log(4, "UpdateServlet", "doPost", "calling userPrivileges");
            scheduledProcess.userPrivileges();
            Log.log(4, "UpdateServlet", "doPost", "Exited");
            response.setContentType("html/text");
            PrintWriter out = response.getWriter();
            out.println("Hi--Post");
            out.flush();
            out.close();
        }
        catch(Exception e)
        {
            e.printStackTrace();
        }
    }

    public void doGet(HttpServletRequest request, HttpServletResponse response)
        throws ServletException
    {
        ScheduledProcessManager scheduledProcess = new ScheduledProcessManager();
        try
        {
            Log.log(4, "UpdateServlet", "doGet", "Entered");
            scheduledProcess.updateData();
            Log.log(4, "UpdateServlet", "doGet", "Exited");
            response.setContentType("html/text");
            PrintWriter out = response.getWriter();
            out.println("Hi--Get");
            out.flush();
            out.close();
        }
        catch(Exception e)
        {
            e.printStackTrace();
        }
    }
}
