package com.cgtsi.action;

import com.cgtsi.actionform.AdministrationActionForm;
import com.cgtsi.actionform.GMActionForm;
import com.cgtsi.admin.User;
import com.cgtsi.claim.ClaimsProcessor;
import com.cgtsi.common.Constants;
import com.cgtsi.common.DatabaseException;
import com.cgtsi.common.Log;
import com.cgtsi.common.MessageException;
import com.cgtsi.common.NoDataException;
import com.cgtsi.registration.MLIInfo;
import com.cgtsi.registration.Registration;
import com.cgtsi.registration.RegistrationDAO;
import com.cgtsi.reports.GeneralReport;
import com.cgtsi.util.DBConnection;

import java.io.PrintStream;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Types;

import java.text.DecimalFormat;
import java.text.SimpleDateFormat;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.StringTokenizer;
import java.util.Vector;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.commons.beanutils.BeanUtils;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.action.DynaActionForm;
import org.apache.struts.validator.DynaValidatorActionForm;

public class NewReportsAction extends BaseAction {
    public ActionForward slabWiseInput(ActionMapping mapping, ActionForm form, 
                                       HttpServletRequest request, 
                                       HttpServletResponse response) throws Exception {
        DynaValidatorActionForm Form = (DynaValidatorActionForm)form;
        HttpSession session = request.getSession();
        ArrayList stateList = new ArrayList();
        ArrayList districtList = new ArrayList();
        ArrayList sectorList = new ArrayList();
        ArrayList rangeList = new ArrayList();
        Log.log(4, "SlabWiseInputREportAction", "slabReport", "Entered");
        Statement stmt = null;
        ResultSet result = null;
        Connection connection = DBConnection.getConnection();
        DynaActionForm dynaForm = (DynaActionForm)form;
        java.util.Date date = new java.util.Date();
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        int month = calendar.get(2);
        int day = calendar.get(5);
        month--;
        day++;
        calendar.set(2, month);
        calendar.set(5, day);
        java.util.Date prevDate = calendar.getTime();
        GeneralReport generalReport = new GeneralReport();
        generalReport.setDateOfTheDocument42(prevDate);
        generalReport.setDateOfTheDocument43(date);
        BeanUtils.copyProperties(dynaForm, generalReport);
        try {
            String query = 
                " SELECT ste_code,ste_name FROM state_master order by ste_name";
            stmt = connection.createStatement();
            result = stmt.executeQuery(query);
            String[] state = null;
            for (; result.next(); stateList.add(state)) {
                state = new String[2];
                state[0] = result.getString(1);
                state[1] = result.getString(2);
            }

            session.setAttribute("stateList", stateList);
            result = null;
            stmt = null;
        } catch (Exception exception) {
            Log.logException(exception);
            throw new DatabaseException(exception.getMessage());
        }
        if (request.getParameter("hiddenvalue") != null) {
            String state = (String)Form.get("slabState");
            String stateCode = "";
            try {
                String query = 
                    " select ste_code FROM state_master where ste_name='" + 
                    state + "'";
                stmt = connection.createStatement();
                for (result = stmt.executeQuery(query); result.next(); ) {
                    stateCode = result.getString(1);
                }
                result = null;
                stmt = null;
            } catch (Exception exception) {
                Log.logException(exception);
                throw new DatabaseException(exception.getMessage());
            }
            try {
                String query = 
                    " select dst_code,dst_name,ste_code  from district_master where ste_code='" + 
                    stateCode + "' order by dst_name";
                stmt = connection.createStatement();
                result = stmt.executeQuery(query);
                String[] district = null;
                for (; result.next(); districtList.add(district)) {
                    district = new String[2];
                    district[0] = (result.getInt(1) + "");
                    district[1] = result.getString(2);
                }

                session.setAttribute("districtList", districtList);
                result = null;
                stmt = null;
            } catch (Exception exception) {
                Log.logException(exception);
                throw new DatabaseException(exception.getMessage());
            }
            request.setAttribute("districtSet", "districtSet");
        }
        try {
            String query = 
                " SELECT isc_code, isc_name FROM INDUSTRY_SECTOR ORDER BY isc_name";
            stmt = connection.createStatement();
            result = stmt.executeQuery(query);
            String[] sector = null;
            for (; result.next(); sectorList.add(sector)) {
                sector = new String[2];
                sector[0] = (result.getInt(1) + "");
                sector[1] = result.getString(2);
            }

            session.setAttribute("sectorList", sectorList);
            result = null;
            stmt = null;
        } catch (Exception exception) {
            Log.logException(exception);
            throw new DatabaseException(exception.getMessage());
        }
        try {
            String query = "SELECT range_id,range_desc FROM range_master";
            Statement stmt2 = connection.createStatement();
            ResultSet result2 = stmt2.executeQuery(query);
            String[] range = null;
            for (; result2.next(); rangeList.add(range)) {
                range = new String[2];
                range[0] = result2.getString(1);
                range[1] = result2.getString(2);
            }

            session.setAttribute("rangeList", rangeList);
            result = null;
            stmt = null;
        } catch (Exception exception) {
            Log.logException(exception);
            throw new DatabaseException(exception.getMessage());
        }
        DBConnection.freeConnection(connection);
        DBConnection.freeConnection(connection);

        return mapping.findForward("success");
    }

    public ActionForward slabWiseOutPut(ActionMapping mapping, ActionForm form, 
                                        HttpServletRequest request, 
                                        HttpServletResponse response) throws Exception {
        DynaActionForm Form = (DynaActionForm)form;
        Log.log(4, "SlabWiseOutPutReportAction", "slabReport", "Entered");
        Statement stmt = null;
        ResultSet result = null;
        Connection connection = DBConnection.getConnection();
        Vector list = new Vector();
        double amount = 0.0D;
        int proposals = 0;
        DecimalFormat decimalFormat = new DecimalFormat("##########0.00");
        java.util.Date resta = (java.util.Date)Form.get("dateOfTheDocument42");
        java.util.Date resend = 
            (java.util.Date)Form.get("dateOfTheDocument43");
        String state = (String)Form.get("slabState");
        String district = (String)Form.get("slabDistrict");
        String sector = (String)Form.get("slabIndustrySector");
        String mliID = ((String)Form.get("mliID")).trim();
        String bankName = null;
        String zoneName = null;
        String branchName = null;
        MLIInfo mliInfo = null;
        RegistrationDAO registrationDAO = new RegistrationDAO();
        if ((mliID != null) && (!mliID.equals(""))) {
            mliInfo = 
                    registrationDAO.getMemberDetails(mliID.substring(0, 4), mliID.substring(4, 
                                                                                            8), 
                                                     mliID.substring(8, 12));
            bankName = mliInfo.getBankName();
            zoneName = mliInfo.getZoneName();
            branchName = mliInfo.getBranchName();
        }
        Form.set("bank", bankName);
        Form.set("zone", zoneName);
        Form.set("branch", branchName);
        String rangeFrom = ((String)Form.get("rangeFrom")).trim();
        String rangeTo = ((String)Form.get("rangeTo")).trim();
        String range = "";
        java.util.Date toDate = null;
        java.util.Date fromDate = null;
        if ((!resend.equals("")) && (resend != null))
            toDate = (java.util.Date)Form.get("dateOfTheDocument43");
        if ((!resta.equals("")) && (resta != null))
            fromDate = (java.util.Date)Form.get("dateOfTheDocument42");
        java.sql.Date startDate = null;
        java.sql.Date endDate = null;
        if ((fromDate != null) && (!fromDate.equals("")))
            startDate = new java.sql.Date(fromDate.getTime());
        if ((toDate != null) && (!toDate.equals("")))
            endDate = new java.sql.Date(toDate.getTime());
        if (state.equals("select"))
            state = null;
        if (district.equals("select"))
            district = null;
        if (sector.equals("select"))
            sector = null;
        if (mliID.equals(""))
            mliID = null;
        if (rangeTo.equals(""))
            rangeTo = null;
        if (rangeFrom.equals(""))
            rangeFrom = null;
        if ((rangeFrom == null) || (rangeTo == null))
            range = null;
        else
            range = rangeFrom + "-" + rangeTo;
        CallableStatement slab = null;
        try {
            slab = 
connection.prepareCall("call Packallslab.PROCSLABREPORT(?,?,?,?,?,?,?,?)");
            slab.setDate(1, startDate);
            slab.setDate(2, endDate);
            slab.setString(3, state);
            slab.setString(4, district);
            slab.setString(5, range);
            slab.setString(6, sector);
            slab.setString(7, mliID);
            slab.registerOutParameter(8, -10);
            slab.execute();
            result = (ResultSet)slab.getObject(8);
            String[] slabList = null;
            for (; result.next(); list.add(slabList)) {
                slabList = new String[3];
                slabList[0] = result.getString(1);
                slabList[1] = (result.getInt(2) + "");
                slabList[2] = (decimalFormat.format(result.getDouble(3)) + "");
                amount += result.getDouble(3);
                proposals += result.getInt(2);
            }

            request.setAttribute("slabList", list);
            request.setAttribute("totalProposals", proposals + "");
            request.setAttribute("totalLoan", decimalFormat.format(amount));
            result = null;
            stmt = null;
        } catch (Exception exception) {
            exception.printStackTrace();
            Log.logException(exception);
            throw new DatabaseException(exception.getMessage());
        }
        DBConnection.freeConnection(connection);

        return mapping.findForward("success");
    }

    public ActionForward listOfPendingCases(ActionMapping mapping, 
                                            ActionForm form, 
                                            HttpServletRequest request, 
                                            HttpServletResponse response) throws Exception {
        Log.log(4, "NewReportsAction", "listOfPendingCases", "Entered");
        HttpSession session = request.getSession();
        DynaActionForm dynaForm = (DynaActionForm)form;
        java.util.Date date = new java.util.Date();
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        int month = calendar.get(2);
        int day = calendar.get(5);
        month--;
        day++;
        calendar.set(2, month);
        calendar.set(5, day);
        java.util.Date prevDate = calendar.getTime();
        GeneralReport generalReport = new GeneralReport();
        generalReport.setDateOfTheDocument40(prevDate);
        generalReport.setDateOfTheDocument41(date);
        BeanUtils.copyProperties(dynaForm, generalReport);
        dynaForm.set("memberId", "");
        dynaForm.set("ssi_name", "");
        Log.log(4, "NewReportsAction", "listOfPendingCases", "Exited");

        return mapping.findForward("success");
    }

    public ActionForward pendingDetails(ActionMapping mapping, ActionForm form, 
                                        HttpServletRequest request, 
                                        HttpServletResponse response) throws Exception {
        DynaActionForm dynaForm = (DynaActionForm)form;
        Log.log(4, "NewReportsAction", "pendingDetails", "Entered");
        HttpSession session = request.getSession();
        Connection connection = DBConnection.getConnection();
        String query = "";
        java.util.Date fromdate = 
            (java.util.Date)dynaForm.get("dateOfTheDocument40");
        java.util.Date todate = 
            (java.util.Date)dynaForm.get("dateOfTheDocument41");
        String memberID = (String)dynaForm.get("memberId");
        String ssi_name = ((String)dynaForm.get("ssi_name")).trim();
        String[] PAStringArray = null;
        ArrayList PAArrayList = new ArrayList();
        String id = "";
        query = 
                " Select A.App_ref_no, A.MEM_BNK_ID||A.MEM_ZNE_ID||A.MEM_BRN_ID, a.APP_LOAN_TYPE,a.APP_STATUS,  to_char(TRUNC(b.WCP_FB_LIMIT_SANCTIONED_DT),'DD/MM/YYYY') WCP_FB_LIMIT_SANCTIONED_DT,to_char(TRUNC(c.TRM_AMOUNT_SANCTIONED_DT),'DD/MM/YYYY') TRM_AMOUNT_SANCTIONED_DT,d.SSI_UNIT_NAME,d.SSI_TYPE_OF_ACTIVITY, ltrim(rtrim(d.SSI_CONSTITUTION)),e.PMR_CHIEF_IT_PAN, a.APP_REMARKS  from Application_detail_temp@CGINTER a, working_capital_detail_temp@CGINTER B,   TERM_LOAN_DETAIL_temp@CGINTER C,ssi_detail_temp@CGINTER d, promoter_detail_temp@CGINTER e  Where A.app_ref_no =  B.app_ref_no AND A.app_ref_no =  C.app_ref_no  AND B.app_ref_no = C.app_ref_no  AND A.APP_STATUS <> 'RE'   AND a.SSI_REFERENCE_NUMBER = d.SSI_REFERENCE_NUMBER(+)  AND d.SSI_REFERENCE_NUMBER = e.SSI_REFERENCE_NUMBER(+)  AND trunc(a.app_submitted_dt) between to_date('" + 
                fromdate + "','dd/mm/yyyy')" + "  AND to_date('" + todate + 
                "','dd/mm/yyyy') AND d.SSI_UNIT_NAME LIKE '%" + ssi_name + 
                "%'";
        if ((memberID.equals(null)) || (memberID.equals(""))) {
            query = 
                    query + "AND MEM_BNK_ID||MEM_ZNE_ID||MEM_BRN_ID=NVL('" + memberID + 
                    "',MEM_BNK_ID||MEM_ZNE_ID||MEM_BRN_ID)";
        } else if (!memberID.equals(null)) {
            String bankId = memberID.substring(0, 4);
            String zoneId = memberID.substring(4, 8);
            String branchId = memberID.substring(8, 12);
            if (!branchId.equals("0000")) {
                id = bankId + zoneId + branchId;
                query = 
                        query + "AND MEM_BNK_ID||MEM_ZNE_ID||MEM_BRN_ID=NVL('" + 
                        id + "',MEM_BNK_ID||MEM_ZNE_ID||MEM_BRN_ID)";
            } else if (!zoneId.equals("0000")) {
                id = bankId + zoneId;
                query = 
                        query + "AND MEM_BNK_ID||MEM_ZNE_ID=NVL('" + id + "',MEM_BNK_ID||MEM_ZNE_ID)";
            } else {
                id = bankId;
                query = query + "AND MEM_BNK_ID=NVL('" + id + "',MEM_BNK_ID)";
            }
        }
        query = query + "ORDER BY a.app_submitted_dt";
        try {
            Statement pendingCaseDetailsStmt = null;
            ResultSet pendingCaseDetailsResult = null;
            pendingCaseDetailsStmt = connection.createStatement();
            for (pendingCaseDetailsResult = 
                 pendingCaseDetailsStmt.executeQuery(query); 
                 pendingCaseDetailsResult.next(); 
                 PAArrayList.add(PAStringArray)) {
                PAStringArray = new String[11];
                PAStringArray[0] = pendingCaseDetailsResult.getString(1);
                PAStringArray[1] = pendingCaseDetailsResult.getString(2);
                PAStringArray[2] = pendingCaseDetailsResult.getString(3);
                PAStringArray[3] = pendingCaseDetailsResult.getString(4);
                PAStringArray[4] = pendingCaseDetailsResult.getString(5);
                PAStringArray[5] = pendingCaseDetailsResult.getString(6);
                PAStringArray[6] = pendingCaseDetailsResult.getString(7);
                PAStringArray[7] = pendingCaseDetailsResult.getString(8);
                PAStringArray[8] = pendingCaseDetailsResult.getString(9);
                PAStringArray[9] = pendingCaseDetailsResult.getString(10);
                PAStringArray[10] = pendingCaseDetailsResult.getString(11);
            }

            pendingCaseDetailsResult.close();
            pendingCaseDetailsResult = null;
            pendingCaseDetailsStmt.close();
            pendingCaseDetailsStmt = null;
        } catch (Exception e) {
            Log.logException(e);
            throw new DatabaseException(e.getMessage());
        }
        DBConnection.freeConnection(connection);
        request.setAttribute("pendingCaseDetailsArray", PAArrayList);
        request.setAttribute("pendingCaseDetailsArray_size", 
                             new Integer(PAArrayList.size()).toString());
        Log.log(4, "NewReportsAction", "pendingDetails", "Exited");

        return mapping.findForward("success");
    }

    public ActionForward newMonthlyReport(ActionMapping mapping, 
                                          ActionForm form, 
                                          HttpServletRequest request, 
                                          HttpServletResponse response) throws Exception {
        Log.log(4, "NewReportsAction", "newMonthlyReport", "Entered");
        DynaActionForm dynaForm = (DynaActionForm)form;
        java.util.Date date = new java.util.Date();
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        int month = calendar.get(2);
        int day = calendar.get(5);
        month--;
        day++;
        calendar.set(2, month);
        calendar.set(5, day);
        java.util.Date prevDate = calendar.getTime();
        GeneralReport generalReport = new GeneralReport();
        generalReport.setDateOfTheDocument38(prevDate);
        generalReport.setDateOfTheDocument39(date);
        BeanUtils.copyProperties(dynaForm, generalReport);
        Log.log(4, "NewReportsAction", "newMonthlyReport", "Exited");

        return mapping.findForward("success");
    }

    public ActionForward newMonthlyProgressReport(ActionMapping mapping, 
                                                  ActionForm form, 
                                                  HttpServletRequest request, 
                                                  HttpServletResponse response) throws Exception {
        DynaActionForm dynaForm = (DynaActionForm)form;
        Log.log(4, "NewReportsAction", "newMonthlyProgressReport", "Entered");
        Connection connection = DBConnection.getConnection();
        java.util.Date date = new java.util.Date();
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        int month = calendar.get(2);
        int day = calendar.get(5);
        int year = calendar.get(5);
        month--;
        day++;
        String query = "";
        java.util.Date fromdate = 
            (java.util.Date)dynaForm.get("dateOfTheDocument38");
        java.util.Date todate = 
            (java.util.Date)dynaForm.get("dateOfTheDocument39");
        String[] MonthlyStringArray = null;
        PreparedStatement monthlyProgressStmt = null;
        ArrayList monthlyProgressReportArray = new ArrayList();
        ResultSet monthlyProgressResult = null;
        try {
            query = 
                    " select count(b.SSI_REFERENCE_NUMBER), sum(SSI_NO_OF_EMPLOYEES) emp, round(sum(SSI_PROJECTED_SALES_TURNOVER)/100000,2) turn, round(sum(SSI_PROJECTED_EXPORTS)/100000,2) expo ,'current' from application_detail a , ssi_detail b where a.SSI_REFERENCE_NUMBER = b.SSI_REFERENCE_NUMBER and a.APP_STATUS not in ('RE') and trunc(a.APP_APPROVED_DATE_TIME) between to_date('" + 
                    fromdate + "','dd/mm/yyyy') and to_date('" + todate + 
                    "','dd/mm/yyyy')" + " union" + 
                    " select count(b.SSI_REFERENCE_NUMBER), sum(SSI_NO_OF_EMPLOYEES) emp," + 
                    " round(sum(SSI_PROJECTED_SALES_TURNOVER)/100000,2) turn," + 
                    " round(sum(SSI_PROJECTED_EXPORTS)/100000,2) expo,'prev' from application_detail a , ssi_detail b" + 
                    " where a.SSI_REFERENCE_NUMBER = b.SSI_REFERENCE_NUMBER and a.APP_STATUS not in ('RE')" + 
                    " and trunc(a.APP_APPROVED_DATE_TIME) between add_months(to_date('" + 
                    fromdate + 
                    "','dd/mm/yyyy'),-12) and add_months(to_date('" + todate + 
                    "','dd/mm/yyyy'),-12) order by 5";
            monthlyProgressStmt = connection.prepareStatement(query);

            for (monthlyProgressResult = monthlyProgressStmt.executeQuery(); 
                 monthlyProgressResult.next(); 
                 monthlyProgressReportArray.add(MonthlyStringArray)) {
                MonthlyStringArray = new String[5];
                MonthlyStringArray[0] = monthlyProgressResult.getString(1);
                MonthlyStringArray[1] = monthlyProgressResult.getString(2);
                MonthlyStringArray[2] = monthlyProgressResult.getString(3);
                MonthlyStringArray[3] = monthlyProgressResult.getString(4);
            }

            monthlyProgressResult.close();
            monthlyProgressResult = null;
            monthlyProgressStmt.close();
            monthlyProgressStmt = null;
        } catch (Exception exception) {
            Log.logException(exception);
            throw new DatabaseException(exception.getMessage());
        }
        DBConnection.freeConnection(connection);
        request.setAttribute("monthlyProgressReportArray", 
                             monthlyProgressReportArray);
        request.setAttribute("monthlyProgressReportArray_size", 
                             new Integer(monthlyProgressReportArray.size()).toString());
        Log.log(4, "NewReportsAction", "newMonthlyProgressReport", "Exited");

        return mapping.findForward("success");
    }

    public ActionForward asfSummeryReport(ActionMapping mapping, 
                                          ActionForm form, 
                                          HttpServletRequest request, 
                                          HttpServletResponse response) throws Exception {
        Log.log(4, "NewReportsAction", "asfSummeryReport", "Entered");
        HttpSession session = request.getSession();
        DynaActionForm dynaForm = (DynaActionForm)form;
        dynaForm.set("memberId", "");
        User user = getUserInformation(request);
        String bankId = user.getBankId();
        String zoneId = user.getZoneId();
        String branchId = user.getBranchId();
        String memberId = bankId.concat(zoneId).concat(branchId);
        dynaForm.set("bankId", bankId);
        if (bankId.equals("0000")) {
            memberId = "";
            dynaForm.set("memberId", memberId);
        } else {
            dynaForm.set("mliID", memberId);
        }
        java.util.Date date = new java.util.Date();
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        int month = calendar.get(2);
        int day = calendar.get(5);
        month--;
        day++;
        calendar.set(2, month);
        calendar.set(5, day);
        java.util.Date prevDate = calendar.getTime();
        GeneralReport generalReport = new GeneralReport();
        generalReport.setDateOfTheDocument36(prevDate);
        generalReport.setDateOfTheDocument37(date);
        BeanUtils.copyProperties(dynaForm, generalReport);
        dynaForm.set("memberId", "");
        Log.log(4, "NewReportsAction", "asfSummeryReport", "Exited");

        return mapping.findForward("success");
    }

    public ActionForward asfSummeryReportDetails(ActionMapping mapping, 
                                                 ActionForm form, 
                                                 HttpServletRequest request, 
                                                 HttpServletResponse response) throws Exception {
        DynaActionForm dynaForm = (DynaActionForm)form;
        Log.log(4, "NewReportsAction", "asfSummeryReportDetails", "Entered");
        Connection connection = DBConnection.getConnection();
        java.util.Date date = new java.util.Date();
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        int month = calendar.get(2);
        int day = calendar.get(5);
        month--;
        day++;
        String query = "";
        java.util.Date fromdate = 
            (java.util.Date)dynaForm.get("dateOfTheDocument36");
        java.util.Date todate = 
            (java.util.Date)dynaForm.get("dateOfTheDocument37");
        String memberID = (String)dynaForm.get("memberId");
        String mliId = (String)dynaForm.get("mliID");
        String bankID = (String)dynaForm.get("bankId");
        if (!bankID.equals("0000"))
            memberID = mliId;
        String[] AsfStringArray = null;
        ArrayList asfSummeryDetailsArray = new ArrayList();
        String id = "";
        query = 
                " SELECT MEM,DUE_C,DUE,PAID_C,PAID, (DUE-PAID) DIFF,(DUE_C-PAID_C) CASES,M.MEM_BANK_NAME,M.MEM_ZONE_NAME  FROM (SELECT MEM,SUM(DUE_C) DUE_C,SUM(DUE) DUE, SUM(PAID_C) PAID_C,SUM(PAID) PAID FROM (SELECT C.DAN_ID,C.MEM_BNK_ID||C.MEM_ZNE_ID||C.MEM_BRN_ID MEM, nvl(DUE_C,0) DUE_C, DUE , nvl(PAID_C,0) PAID_C, nvl(PAID,0) PAID FROM (SELECT DAN_ID , SUM(DCI_AMOUNT_RAISED) DUE , COUNT(*) DUE_C  FROM DAN_CGPAN_INFO WHERE (DAN_ID LIKE 'SF%' OR DAN_ID LIKE 'AF%')  AND (DCI_AMOUNT_RAISED-NVL(DCI_AMOUNT_CANCELLED,0)) >0 GROUP BY DAN_ID ) A, (SELECT DAN_ID, SUM(DCI_AMOUNT_RAISED) PAID, COUNT(*) PAID_C  FROM DAN_CGPAN_INFO WHERE (DAN_ID LIKE 'SF%' OR DAN_ID LIKE 'AF%') AND DCI_APPROPRIATION_FLAG = 'Y' AND (DCI_AMOUNT_RAISED-NVL(DCI_AMOUNT_CANCELLED,0)) >0  GROUP BY DAN_ID) B , DEMAND_ADVICE_INFO C WHERE A.DAN_ID= B.DAN_ID (+) AND A.DAN_ID = C.DAN_ID and c.dan_generated_dt between to_date('" + 
                fromdate + "','dd/mm/yyyy')" + "  AND to_date('" + todate + 
                "','dd/mm/yyyy')";
        if ((memberID.equals(null)) || (memberID.equals(""))) {
            query = 
                    query + "AND C.MEM_BNK_ID||C.MEM_ZNE_ID||C.MEM_BRN_ID=NVL('" + 
                    memberID + "',C.MEM_BNK_ID||C.MEM_ZNE_ID||C.MEM_BRN_ID)";
        } else if (!memberID.equals(null)) {
            String bankId = memberID.substring(0, 4);
            String zoneId = memberID.substring(4, 8);
            String branchId = memberID.substring(8, 12);
            if (!branchId.equals("0000")) {
                id = bankId + zoneId + branchId;
                query = 
                        query + "and C.MEM_BNK_ID||C.MEM_ZNE_ID||C.MEM_BRN_ID=nvl('" + 
                        id + "',C.MEM_BNK_ID||C.MEM_ZNE_ID||C.MEM_BRN_ID)";
            } else if (!zoneId.equals("0000")) {
                id = bankId + zoneId;
                query = 
                        query + "and C.MEM_BNK_ID||C.MEM_ZNE_ID=nvl('" + id + "',C.MEM_BNK_ID||C.MEM_ZNE_ID)";
            } else {
                id = bankId;
                query = 
                        query + "and C.MEM_BNK_ID=nvl('" + id + "',C.MEM_BNK_ID)";
            }
        }
        query = 
                query + "GROUP BY C.DAN_ID,C.MEM_BNK_ID, C.MEM_ZNE_ID, MEM_BRN_ID,DUE_C,DUE,PAID_C,PAID) GROUP BY MEM ORDER BY 1),MEMBER_INFO M where m.mem_bnk_id = substr(mem,1,4) and m.mem_zne_id = substr(mem,5,4) and mem_brn_id = substr(mem,9,4) order by 8,9";
        try {
            Statement asfSummeryDetailsStmt = null;
            ResultSet asfSummeryDetailsResult = null;
            asfSummeryDetailsStmt = connection.createStatement();
            for (asfSummeryDetailsResult = 
                 asfSummeryDetailsStmt.executeQuery(query); 
                 asfSummeryDetailsResult.next(); 
                 asfSummeryDetailsArray.add(AsfStringArray)) {
                AsfStringArray = new String[9];
                AsfStringArray[0] = asfSummeryDetailsResult.getString(1);
                AsfStringArray[1] = asfSummeryDetailsResult.getString(2);
                AsfStringArray[2] = asfSummeryDetailsResult.getString(3);
                AsfStringArray[3] = asfSummeryDetailsResult.getString(4);
                AsfStringArray[4] = asfSummeryDetailsResult.getString(5);
                AsfStringArray[5] = asfSummeryDetailsResult.getString(6);
                AsfStringArray[6] = asfSummeryDetailsResult.getString(7);
                AsfStringArray[7] = asfSummeryDetailsResult.getString(8);
                AsfStringArray[8] = asfSummeryDetailsResult.getString(9);
            }

            asfSummeryDetailsResult.close();
            asfSummeryDetailsResult = null;
            asfSummeryDetailsStmt.close();
            asfSummeryDetailsStmt = null;
        } catch (Exception e) {
            Log.logException(e);
            throw new DatabaseException(e.getMessage());
        }
        DBConnection.freeConnection(connection);
        request.setAttribute("asfSummeryDetailsArray", asfSummeryDetailsArray);
        request.setAttribute("asfSummeryDetailsArray_size", 
                             new Integer(asfSummeryDetailsArray.size()).toString());
        Log.log(4, "NewReportsAction", "asfSummeryReportDetails", "Exited");

        return mapping.findForward("success1");
    }

    public ActionForward asfSummeryMliwiseDetails(ActionMapping mapping, 
                                                  ActionForm form, 
                                                  HttpServletRequest request, 
                                                  HttpServletResponse response) throws Exception {
        DynaActionForm dynaForm = (DynaActionForm)form;
        Log.log(4, "NewReportsAction", "asfSummeryReportDetails", "Entered");
        Connection connection = DBConnection.getConnection();
        java.util.Date date = new java.util.Date();
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        int month = calendar.get(2);
        int day = calendar.get(5);
        month--;
        day++;
        String query = "";
        java.util.Date fromdate = 
            (java.util.Date)dynaForm.get("dateOfTheDocument36");
        java.util.Date todate = 
            (java.util.Date)dynaForm.get("dateOfTheDocument37");
        ////System.out.println("fromdate;" + fromdate + " todate:" + todate);
        String number = request.getParameter("num");
        String[] AsfMLIStringArray = null;
        ArrayList asfSummeryMLIDetailsArray = new ArrayList();
        query = 
                "SELECT C.MEM_BNK_ID||C.MEM_ZNE_ID||C.MEM_BRN_ID ,c.DAN_ID, a.CGPAN,d.SSI_UNIT_NAME, b.DCI_AMOUNT_RAISED,decode(b.DCI_APPROPRIATION_FLAG,'N','Not Appropriated' , 'Yes') , b.DCI_REMARKS , a.APP_STATUS,app_mli_branch_name FROM application_detail a, dan_cgpan_info b ,demand_advice_info c , ssi_detail d where C.MEM_BNK_ID||C.MEM_ZNE_ID||C.MEM_BRN_ID=" + 
                number + 
                " and a.SSI_REFERENCE_NUMBER = d.SSI_REFERENCE_NUMBER" + 
                " and a.CGPAN = b.CGPAN and c.DAN_ID = b.dan_id and c.DAN_TYPE = 'SF'" + 
                " and (DCI_AMOUNT_RAISED-NVL(DCI_AMOUNT_CANCELLED,0))>0 and trunc(c.dan_generated_dt) between to_date('" + 
                fromdate + "','dd/mm/yyyy')" + "AND to_date('" + todate + 
                "','dd/mm/yyyy') order by 1,app_mli_branch_name,2,3, 4";
        try {
            Statement asfSummeryMLIDetailsStmt = null;
            ResultSet asfSummeryMLIDetailsResult = null;
            asfSummeryMLIDetailsStmt = connection.createStatement();
            for (asfSummeryMLIDetailsResult = 
                 asfSummeryMLIDetailsStmt.executeQuery(query); 
                 asfSummeryMLIDetailsResult.next(); 
                 asfSummeryMLIDetailsArray.add(AsfMLIStringArray)) {
                AsfMLIStringArray = new String[9];
                AsfMLIStringArray[0] = asfSummeryMLIDetailsResult.getString(1);
                AsfMLIStringArray[1] = asfSummeryMLIDetailsResult.getString(2);
                AsfMLIStringArray[2] = asfSummeryMLIDetailsResult.getString(3);
                AsfMLIStringArray[3] = asfSummeryMLIDetailsResult.getString(4);
                AsfMLIStringArray[4] = asfSummeryMLIDetailsResult.getString(5);
                AsfMLIStringArray[5] = asfSummeryMLIDetailsResult.getString(6);
                AsfMLIStringArray[6] = asfSummeryMLIDetailsResult.getString(7);
                AsfMLIStringArray[7] = asfSummeryMLIDetailsResult.getString(8);
                AsfMLIStringArray[8] = asfSummeryMLIDetailsResult.getString(9);
            }

            asfSummeryMLIDetailsResult.close();
            asfSummeryMLIDetailsResult = null;
            asfSummeryMLIDetailsStmt.close();
            asfSummeryMLIDetailsStmt = null;
        } catch (Exception e) {
            Log.logException(e);
            throw new DatabaseException(e.getMessage());
        }
        DBConnection.freeConnection(connection);
        request.setAttribute("asfSummeryMLIDetailsArray", 
                             asfSummeryMLIDetailsArray);
        request.setAttribute("asfSummeryMLIDetailsArray_size", 
                             new Integer(asfSummeryMLIDetailsArray.size()).toString());
        Log.log(4, "NewReportsAction", "asfSummeryReportDetails", "Exited");

        return mapping.findForward("success2");
    }

    public ActionForward womenEntrepreneurReports(ActionMapping mapping, 
                                                  ActionForm form, 
                                                  HttpServletRequest request, 
                                                  HttpServletResponse response) throws Exception {
        Log.log(4, "NewReportsAction", "asfSummeryReport", "Entered");
        HttpSession session = request.getSession();
        DynaActionForm dynaForm = (DynaActionForm)form;
        java.util.Date date = new java.util.Date();
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        int month = calendar.get(2);
        int day = calendar.get(5);
        month--;
        day++;
        calendar.set(2, month);
        calendar.set(5, day);
        java.util.Date prevDate = calendar.getTime();
        GeneralReport generalReport = new GeneralReport();
        generalReport.setDateOfTheDocument34(prevDate);
        generalReport.setDateOfTheDocument35(date);
        BeanUtils.copyProperties(dynaForm, generalReport);
        dynaForm.set("memberId", "");
        Statement womenEntrepreneurStmt = null;
        String[] StateStringArray = null;
        ArrayList womenEntrepreneurReportArray = new ArrayList();
        ArrayList womenEntrepreneurReportArray1 = new ArrayList();
        ArrayList womenEntrepreneurReportArray2 = new ArrayList();
        Connection connection = null;
        ResultSet womenEntrepreneurReportResult2 = null;
        try {
            connection = DBConnection.getConnection();
            String query2 = 
                "select STE_CODE,STE_NAME from State_master order by STE_NAME";
            Statement womenEntrepreneurStmt2 = connection.createStatement();

            for (womenEntrepreneurReportResult2 = 
                 womenEntrepreneurStmt2.executeQuery(query2); 
                 womenEntrepreneurReportResult2.next(); 
                 womenEntrepreneurReportArray2.add(StateStringArray)) {
                StateStringArray = new String[3];
                StateStringArray[0] = 
                        womenEntrepreneurReportResult2.getString(1);
                StateStringArray[1] = 
                        womenEntrepreneurReportResult2.getString(2);
            }

            womenEntrepreneurReportResult2.close();
            womenEntrepreneurReportResult2 = null;
            womenEntrepreneurStmt2.close();
            womenEntrepreneurStmt2 = null;
        } catch (Exception e) {
            Log.logException(e);
            throw new DatabaseException(e.getMessage());
        }
        DBConnection.freeConnection(connection);
        request.setAttribute("district_arraylist_data", null);
        request.setAttribute("district_arraylist_data_size", null);
        session.setAttribute("womenEntrepreneurReportArray2", 
                             womenEntrepreneurReportArray2);
        Log.log(4, "NewReportsAction", "womenEntrepreneurReports", "Exited");

        return mapping.findForward("success");
    }

    public ActionForward womenEntrepreneurReportDetails(ActionMapping mapping, 
                                                        ActionForm form, 
                                                        HttpServletRequest request, 
                                                        HttpServletResponse response) throws Exception {
        DynaActionForm dynaForm = (DynaActionForm)form;
        Log.log(4, "NewReportsAction", "asfSummeryReportDetails", "Entered");
        Connection connection = DBConnection.getConnection();
        java.util.Date date = new java.util.Date();
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        int month = calendar.get(2);
        int day = calendar.get(5);
        month--;
        day++;
        String query = "";
        java.util.Date fromdate = 
            (java.util.Date)dynaForm.get("dateOfTheDocument34");
        java.util.Date todate = 
            (java.util.Date)dynaForm.get("dateOfTheDocument35");
        String memberID = (String)dynaForm.get("memberId");
        String District = (String)dynaForm.get("District");
        String state = (String)dynaForm.get("State");
        String[] WomenStringArray = null;
        ArrayList womenEntrepreneurDetailsArray = new ArrayList();
        String id = "";
        if ((memberID.equals(null)) || (memberID.equals(""))) {
            query = 
                    " select round(sum(decode(PMR_chief_gender,'F', nvl(APP_APPROVED_AMOUNT,0),null)/100000),2) Female,decode(PMR_chief_gender,'F', count(*)) F_count  from application_detail A, PROMOTER_DETAIL B, ssi_detail c  WHERE a.SSI_REFERENCE_NUMBER = B.SSI_REFERENCE_NUMBER and  B.SSI_REFERENCE_NUMBER = c.SSI_REFERENCE_NUMBER  and APP_APPROVED_AMOUNT is not null AND trunc(APP_APPROVED_DATE_TIME) between to_date('" + 
                    fromdate + "','dd/mm/yyyy')" + "  AND to_date('" + todate + 
                    "','dd/mm/yyyy')";
            if (!state.equals(""))
                query = 
                        query + "and C.SSI_STATE_NAME =(select STE_NAME from  state_master where  STE_code='" + 
                        state.trim() + "')";
            if (!District.equals(""))
                query = 
                        query + "and C.SSI_DISTRICT_NAME =(SELECT DST_NAME FROM DISTRICT_MASTER WHERE DST_CODE='" + 
                        District.trim() + "')";
            query = query + "group by PMR_chief_gender";
        } else if (!memberID.equals(null)) {
            String bankId = memberID.substring(0, 4);
            String zoneId = memberID.substring(4, 8);
            String branchId = memberID.substring(8, 12);
            if (!branchId.equals("0000")) {
                id = bankId + zoneId + branchId;
                query = 
                        " select round(sum(decode(PMR_chief_gender,'F', nvl(APP_APPROVED_AMOUNT,0),null)/100000),2) Female,decode(PMR_chief_gender,'F', count(*)) F_count  from application_detail A, PROMOTER_DETAIL B, ssi_detail c  WHERE a.SSI_REFERENCE_NUMBER = B.SSI_REFERENCE_NUMBER and  B.SSI_REFERENCE_NUMBER = c.SSI_REFERENCE_NUMBER and a.MEM_BNK_ID||a.MEM_ZNE_ID||a.MEM_BRN_ID=nvl('" + 
                        id + "',a.MEM_BNK_ID||a.MEM_ZNE_ID||a.MEM_BRN_ID)" + 
                        " and APP_APPROVED_AMOUNT is not null AND trunc(APP_APPROVED_DATE_TIME) between to_date('" + 
                        fromdate + "','dd/mm/yyyy')" + "  AND to_date('" + 
                        todate + "','dd/mm/yyyy')";
                if (!state.equals(""))
                    query = 
                            query + "and C.SSI_STATE_NAME =(select STE_NAME from  state_master where  STE_code='" + 
                            state.trim() + "')";
                if (!District.equals(""))
                    query = 
                            query + "and C.SSI_DISTRICT_NAME =(SELECT DST_NAME FROM DISTRICT_MASTER WHERE DST_CODE='" + 
                            District.trim() + "')";
                query = query + "group by PMR_chief_gender";
            } else if (!zoneId.equals("0000")) {
                id = bankId + zoneId;
                query = 
                        " select round(sum(decode(PMR_chief_gender,'F', nvl(APP_APPROVED_AMOUNT,0),null)/100000),2) Female,decode(PMR_chief_gender,'F', count(*)) F_count  from application_detail A, PROMOTER_DETAIL B, ssi_detail c  WHERE a.SSI_REFERENCE_NUMBER = B.SSI_REFERENCE_NUMBER and  B.SSI_REFERENCE_NUMBER = c.SSI_REFERENCE_NUMBER and a.MEM_BNK_ID||a.MEM_ZNE_ID=nvl('" + 
                        id + "',a.MEM_BNK_ID||a.MEM_ZNE_ID)" + 
                        " and APP_APPROVED_AMOUNT is not null AND trunc(APP_APPROVED_DATE_TIME) between to_date('" + 
                        fromdate + "','dd/mm/yyyy')" + "  AND to_date('" + 
                        todate + "','dd/mm/yyyy')";
                if (!state.equals(""))
                    query = 
                            query + "and C.SSI_STATE_NAME =(select STE_NAME from  state_master where  STE_code='" + 
                            state.trim() + "')";
                if (!District.equals(""))
                    query = 
                            query + "and C.SSI_DISTRICT_NAME =(SELECT DST_NAME FROM DISTRICT_MASTER WHERE DST_CODE='" + 
                            District.trim() + "')";
                query = query + "group by PMR_chief_gender";
            } else {
                id = bankId;
                query = 
                        " select round(sum(decode(PMR_chief_gender,'F', nvl(APP_APPROVED_AMOUNT,0),null)/100000),2) Female,decode(PMR_chief_gender,'F', count(*)) F_count  from application_detail A, PROMOTER_DETAIL B, ssi_detail c  WHERE a.SSI_REFERENCE_NUMBER = B.SSI_REFERENCE_NUMBER and  B.SSI_REFERENCE_NUMBER = c.SSI_REFERENCE_NUMBER and a.MEM_BNK_ID=nvl('" + 
                        id + "',a.MEM_BNK_ID)" + 
                        " and APP_APPROVED_AMOUNT is not null AND trunc(APP_APPROVED_DATE_TIME) between to_date('" + 
                        fromdate + "','dd/mm/yyyy')" + "  AND to_date('" + 
                        todate + "','dd/mm/yyyy')";
                if (!state.equals(""))
                    query = 
                            query + "and C.SSI_STATE_NAME =(select STE_NAME from  state_master where  STE_code='" + 
                            state.trim() + "')";
                if (!District.equals(""))
                    query = 
                            query + "and C.SSI_DISTRICT_NAME =(SELECT DST_NAME FROM DISTRICT_MASTER WHERE DST_CODE='" + 
                            District.trim() + "')";
                query = query + "group by PMR_chief_gender";
            }
        }
        try {
            Statement womenEntrepreneurDetailsStmt = null;
            ResultSet womenEntrepreneurDetailsResult = null;
            womenEntrepreneurDetailsStmt = connection.createStatement();
            for (womenEntrepreneurDetailsResult = 
                 womenEntrepreneurDetailsStmt.executeQuery(query); 
                 womenEntrepreneurDetailsResult.next(); 
                 womenEntrepreneurDetailsArray.add(WomenStringArray)) {
                WomenStringArray = new String[2];
                WomenStringArray[0] = 
                        womenEntrepreneurDetailsResult.getString(1);
                WomenStringArray[1] = 
                        womenEntrepreneurDetailsResult.getString(2);
            }

            womenEntrepreneurDetailsResult.close();
            womenEntrepreneurDetailsResult = null;
            womenEntrepreneurDetailsStmt.close();
            womenEntrepreneurDetailsStmt = null;
        } catch (Exception e) {
            Log.logException(e);
            throw new DatabaseException(e.getMessage());
        }
        DBConnection.freeConnection(connection);
        int total1 = womenEntrepreneurDetailsArray.size();
        String total = new Integer(total1).toString();
        if ((total == "1") || (total == "0")) {
            throw new NoDataException("No Data is available for the values entered, Please Enter Any Other Value ");
        }

        request.setAttribute("womenEntrepreneurDetailsArray", 
                             womenEntrepreneurDetailsArray);
        request.setAttribute("womenEntrepreneurDetailsArray_size", 
                             new Integer(womenEntrepreneurDetailsArray.size()).toString());
        Log.log(4, "NewReportsAction", "womenEntrepreneurReportDetails", 
                "Exited");

        return mapping.findForward("success1");
    }

    public ActionForward fetchDistrict(ActionMapping mapping, ActionForm form, 
                                       HttpServletRequest request, 
                                       HttpServletResponse response) throws Exception {
        DynaActionForm dynaForm = (DynaActionForm)form;
        Connection con = null;
        Statement st = null;
        ResultSet rs = null;
        String state = "";
        state = (String)dynaForm.get("State");
        HttpSession session = request.getSession();
        String[] District_Array = null;
        ArrayList district_arraylist_data = new ArrayList();
        String sql_district = 
            "SELECT DST_CODE,DST_NAME FROM DISTRICT_MASTER WHERE ste_code='" + 
            state + "'";
        try {
            con = DBConnection.getConnection(false);
            st = con.createStatement();
            for (rs = st.executeQuery(sql_district); rs.next(); 
                 district_arraylist_data.add(District_Array)) {
                District_Array = new String[2];
                District_Array[0] = rs.getString(1);
                District_Array[1] = rs.getString(2);
            }

        } catch (Exception e) {
           // //System.out.println("Exception during fetch district data");
        }
        if (con != null)
            con.close();
        request.setAttribute("district_arraylist_data", 
                             district_arraylist_data);
        request.setAttribute("district_arraylist_data_size", 
                             new Integer(district_arraylist_data.size()).toString());

        return mapping.findForward("district");
    }

   /* public ActionForward claimSettledReport(ActionMapping mapping, 
                                            ActionForm form, 
                                            HttpServletRequest request, 
                                            HttpServletResponse response) throws Exception {
        String condition = request.getParameter("condition");
        DynaActionForm dynaForm = (DynaActionForm)form;
        Log.log(4, "NewReportsAction", "Claim settled", "Entered");
        HttpSession session = request.getSession();
        Connection connection = DBConnection.getConnection();
        String query = "";
        java.util.Date fromdate = 
            (java.util.Date)dynaForm.get("dateOfTheDocument26");
        java.util.Date todate = 
            (java.util.Date)dynaForm.get("dateOfTheDocument27");
        String memberID = (String)dynaForm.get("memberId");
        String ssi_name = ((String)dynaForm.get("ssi_name")).trim();
        String clm_date_for_report = null;
        if ("approvedDateWiseReport".equals(condition)) {
            clm_date_for_report = "clm_approved_dt";
        } else if ("paymentDateWiseReport".equals(condition)) {
            clm_date_for_report = "clm_payment_dt";
        } else {
            return mapping.findForward("nocondition");
        }
        String[] PAStringArray = null;
        ArrayList PAArrayList = new ArrayList();
        String id = "";
        ClaimsProcessor claimsProcessor = new ClaimsProcessor();
        Vector memberids = new Vector();
        memberids = claimsProcessor.getAllMemberIds();
        request.setAttribute("date1", fromdate);
        request.setAttribute("date2", todate);
        String stDate = String.valueOf(fromdate);
        String todateNew = String.valueOf(todate);
        if ((stDate.equals(null)) || (stDate.equals("")))
            throw new NoDataException("From Date shold not be empty, Please Enter from Date ");
        if ((todateNew.equals(null)) || (todateNew.equals("")))
            throw new NoDataException("To Date shold not be empty, Please Enter To Date ");
        if ((memberID.length() == 12) && (!memberids.contains(memberID))) {
            Log.log(2, "CPDAO", "getAllMemberIds()", 
                    "No Member Ids in the database!");
            throw new DatabaseException("No Member Ids in the database");
        }
        if ((fromdate == null) && (memberID.equals(""))) {
            //query = " SELECT mem_bank_name,mem_zone_name,c.mem_bnk_id||c.mem_zne_id||c.mem_brn_id memberid, \nA.cgpan,ssi_unit_name, \nDECODE(NVL(app_reapprove_amount,0),0,app_approved_amount,app_reapprove_amount) appamt, \nctd_npa_outstanding_amt_revise nps_os,ctd_npa_recovered_revise reco, \nLEAST(DECODE(NVL(app_reapprove_amount,0),0,app_approved_amount,app_reapprove_amount),ctd_npa_outstanding_amt_revise) - ctd_npa_recovered_revise netos, \ncaa_applied_amount,ctd_tc_clm_elig_amt,ctd_tc_first_inst_pay_amt,ctd_tc_asf_deductable, \nctd_tc_first_inst_pay_amt - ctd_tc_asf_deductable netpaid,NVL(CLM_PAYMENT_UTR_NO,''),NVL(CLM_PAYMENT_AC_NO,'-'),NVL(CLM_PYMT_OUTWARD_NO,''),CLM_PYMT_OUTWARD_DT,clm_approved_dt,clm_payment_dt \nFROM APPLICATION_DETAIL A,SSI_DETAIL s,CLAIM_DETAIL c,CLAIM_TC_DETAIL ctd,MEMBER_INFO m,CLAIM_APPLICATION_AMOUNT caa \nWHERE A.ssi_reference_number = s.ssi_Reference_number \nAND s.bid = c.bid \nAND C.CLM_REF_NO = ctd.clm_ref_no \nAND A.mem_bnk_id||A.mem_zne_id||A.mem_brn_id = m.mem_bnk_id||m.mem_zne_id||m.mem_brn_id \nAND A.cgpan = caa.cgpan \nAND app_loan_type IN ('TC') \nAND clm_status IN ('AP') \nAND TRUNC(" + clm_date_for_report + ") <= to_date('" + todate + "','DD/MM/YYYY')     \n" + "UNION ALL \n" + "SELECT mem_bank_name,mem_zone_name,c.mem_bnk_id||c.mem_zne_id||c.mem_brn_id memberid, \n" + "A.cgpan,ssi_unit_name, \n" + "DECODE(NVL(app_reapprove_amount,0),0,app_approved_amount,app_reapprove_amount) appamt, \n" + "cwd_npa_outstanding_amt_revise nps_os,cwd_npa_recovered_revise reco, \n" + "LEAST(DECODE(NVL(app_reapprove_amount,0),0,app_approved_amount,app_reapprove_amount),cwd_npa_outstanding_amt_revise) - cwd_npa_recovered_revise netos, \n" + "caa_applied_amount,cwd_wc_clm_elig_amt,cwd_wc_first_inst_pay_amt,cwd_wc_asf_deductable, \n" + "cwd_wc_first_inst_pay_amt - cwd_wc_asf_deductable netpaid,NVL(CLM_PAYMENT_UTR_NO,''),NVL(CLM_PAYMENT_AC_NO,'-'),NVL(CLM_PYMT_OUTWARD_NO,'-'),CLM_PYMT_OUTWARD_DT,clm_approved_dt,clm_payment_dt \n" + "FROM APPLICATION_DETAIL A,SSI_DETAIL s,CLAIM_DETAIL c,CLAIM_WC_DETAIL cwd,MEMBER_INFO m,CLAIM_APPLICATION_AMOUNT caa \n" + "WHERE A.ssi_reference_number = s.ssi_Reference_number \n" + "AND s.bid = c.bid \n" + "AND C.CLM_REF_NO = cwd.clm_ref_no \n" + "AND A.mem_bnk_id||A.mem_zne_id||A.mem_brn_id = m.mem_bnk_id||m.mem_zne_id||m.mem_brn_id \n" + "AND A.cgpan = caa.cgpan \n" + "AND app_loan_type IN ('WC') \n" + "AND clm_status IN ('AP') \n" + "AND TRUNC(" + clm_date_for_report + ") <= to_date('" + todate + "','DD/MM/YYYY')    \n" + "UNION ALL \n" + "SELECT mem_bank_name,mem_zone_name,c.mem_bnk_id||c.mem_zne_id||c.mem_brn_id memberid, \n" + "A.cgpan,ssi_unit_name, \n" + "DECODE(NVL(app_reapprove_amount,0),0,app_approved_amount,app_reapprove_amount) appamt, \n" + "cwd_npa_outstanding_amt_revise + ctd_npa_outstanding_amt_revise nps_os,cwd_npa_recovered_revise + ctd_npa_recovered_revise reco, \n" + "LEAST(DECODE(NVL(app_reapprove_amount,0),0,app_approved_amount,app_reapprove_amount),cwd_npa_outstanding_amt_revise + ctd_npa_outstanding_amt_revise) - cwd_npa_recovered_revise + ctd_npa_recovered_revise netos, \n" + "caa_applied_amount,cwd_wc_clm_elig_amt + ctd_tc_clm_elig_amt,cwd_wc_first_inst_pay_amt + ctd_tc_first_inst_pay_amt,cwd_wc_asf_deductable + cwd_wc_asf_deductable, \n" + "cwd_wc_first_inst_pay_amt - cwd_wc_asf_deductable + cwd_wc_first_inst_pay_amt - cwd_wc_asf_deductable netpaid,NVL(CLM_PAYMENT_UTR_NO,''),NVL(CLM_PAYMENT_AC_NO,'-'),NVL(CLM_PYMT_OUTWARD_NO,'-'),CLM_PYMT_OUTWARD_DT,clm_approved_dt,clm_payment_dt \n" + "FROM APPLICATION_DETAIL A,SSI_DETAIL s,CLAIM_DETAIL c,CLAIM_WC_DETAIL cwd,MEMBER_INFO m,CLAIM_APPLICATION_AMOUNT caa,CLAIM_TC_DETAIL ctd \n" + "WHERE A.ssi_reference_number = s.ssi_Reference_number \n" + "AND s.bid = c.bid \n" + "AND C.CLM_REF_NO = cwd.clm_ref_no \n" + "AND C.CLM_REF_NO = ctd.clm_ref_no \n" + "AND A.mem_bnk_id||A.mem_zne_id||A.mem_brn_id = m.mem_bnk_id||m.mem_zne_id||m.mem_brn_id \n" + "AND A.cgpan = caa.cgpan \n" + "AND app_loan_type IN ('CC') \n" + "AND clm_status IN ('AP') \n" + "AND TRUNC(" + clm_date_for_report + ") <= to_date('" + todate + "','DD/MM/YYYY')   \n" + "ORDER BY 1,2,5 \n" + "\n";
            query = 
                    (new StringBuilder()).append(" SELECT mem_bank_name,mem_zone_name,c.mem_bnk_id||c.mem_zne_id||c.mem_brn_id memberid, \nA.cgpan,ssi_unit_name, \nDECODE(NVL(app_reapprove_amount,0),0,app_approved_amount,app_reapprove_amount) appamt, \nctd_npa_outstanding_amt_revise nps_os,ctd_npa_recovered_revise reco, \nLEAST(DECODE(NVL(app_reapprove_amount,0),0,app_approved_amount,app_reapprove_amount),ctd_npa_outstanding_amt_revise) - ctd_npa_recovered_revise netos, \ncaa_applied_amount,ctd_tc_clm_elig_amt,ctd_tc_first_inst_pay_amt,ctd_tc_asf_deductable, \nctd_tc_first_inst_pay_amt - ctd_tc_asf_deductable netpaid,NVL(CLM_PAYMENT_UTR_NO,''),NVL(CLM_PAYMENT_AC_NO,'-'),NVL(CLM_PYMT_OUTWARD_NO,''),CLM_PYMT_OUTWARD_DT,clm_approved_dt,clm_payment_dt,decode(ctd_sf_refund_flag,'Y',ctd_tc_sf_refundable,0),ctd_sf_refund_flag \nFROM APPLICATION_DETAIL A,SSI_DETAIL s,CLAIM_DETAIL c,CLAIM_TC_DETAIL ctd,MEMBER_INFO m,CLAIM_APPLICATION_AMOUNT caa \nWHERE A.ssi_reference_number = s.ssi_Reference_number \nAND s.bid = c.bid \nAND C.CLM_REF_NO = ctd.clm_ref_no \nAND A.mem_bnk_id||A.mem_zne_id||A.mem_brn_id = m.mem_bnk_id||m.mem_zne_id||m.mem_brn_id \nAND A.cgpan = caa.cgpan \nAND  A.cgpan=ctd.cgpan \nAND     app_loan_type IN ('TC') \nAND clm_status IN ('AP') \nAND TRUNC(").append(clm_date_for_report).append(") <= to_date('").append(todate).append("','DD/MM/YYYY')     \n").append("UNION ALL \n").append("SELECT mem_bank_name,mem_zone_name,c.mem_bnk_id||c.mem_zne_id||c.mem_brn_id memberid, \n").append("A.cgpan,ssi_unit_name, \n").append("DECODE(NVL(app_reapprove_amount,0),0,app_approved_amount,app_reapprove_amount) appamt, \n").append("cwd_npa_outstanding_amt_revise nps_os,cwd_npa_recovered_revise reco, \n").append("LEAST(DECODE(NVL(app_reapprove_amount,0),0,app_approved_amount,app_reapprove_amount),cwd_npa_outstanding_amt_revise) - cwd_npa_recovered_revise netos, \n").append("caa_applied_amount,cwd_wc_clm_elig_amt,cwd_wc_first_inst_pay_amt,cwd_wc_asf_deductable, \n").append("cwd_wc_first_inst_pay_amt - cwd_wc_asf_deductable netpaid,NVL(CLM_PAYMENT_UTR_NO,''),NVL(CLM_PAYMENT_AC_NO,'-'),NVL(CLM_PYMT_OUTWARD_NO,'-'),CLM_PYMT_OUTWARD_DT,clm_approved_dt,clm_payment_dt,decode(cwd_sf_refund_flag,'Y',cwd_wc_sf_refundable,0),cwd_sf_refund_flag \n").append("FROM APPLICATION_DETAIL A,SSI_DETAIL s,CLAIM_DETAIL c,CLAIM_WC_DETAIL cwd,MEMBER_INFO m,CLAIM_APPLICATION_AMOUNT caa \n").append("WHERE A.ssi_reference_number = s.ssi_Reference_number \n").append("AND s.bid = c.bid \n").append("AND C.CLM_REF_NO = cwd.clm_ref_no \n").append("AND A.mem_bnk_id||A.mem_zne_id||A.mem_brn_id = m.mem_bnk_id||m.mem_zne_id||m.mem_brn_id \n").append("AND A.cgpan = caa.cgpan \n").append("AND A.cgpan = cwd.cgpan \n").append("AND app_loan_type IN ('WC') \n").append("AND clm_status IN ('AP') \n").append("AND TRUNC(").append(clm_date_for_report).append(") <= to_date('").append(todate).append("','DD/MM/YYYY')    \n").append("UNION ALL \n").append("SELECT mem_bank_name,mem_zone_name,c.mem_bnk_id||c.mem_zne_id||c.mem_brn_id memberid, \n").append("A.cgpan,ssi_unit_name, \n").append("DECODE(NVL(app_reapprove_amount,0),0,app_approved_amount,app_reapprove_amount) appamt, \n").append("cwd_npa_outstanding_amt_revise + ctd_npa_outstanding_amt_revise nps_os,cwd_npa_recovered_revise + ctd_npa_recovered_revise reco, \n").append("LEAST(DECODE(NVL(app_reapprove_amount,0),0,app_approved_amount,app_reapprove_amount),cwd_npa_outstanding_amt_revise + ctd_npa_outstanding_amt_revise) - cwd_npa_recovered_revise + ctd_npa_recovered_revise netos, \n").append("caa_applied_amount,cwd_wc_clm_elig_amt + ctd_tc_clm_elig_amt,cwd_wc_first_inst_pay_amt + ctd_tc_first_inst_pay_amt,cwd_wc_asf_deductable + cwd_wc_asf_deductable, \n").append("cwd_wc_first_inst_pay_amt - cwd_wc_asf_deductable + cwd_wc_first_inst_pay_amt - cwd_wc_asf_deductable netpaid,NVL(CLM_PAYMENT_UTR_NO,''),NVL(CLM_PAYMENT_AC_NO,'-'),NVL(CLM_PYMT_OUTWARD_NO,'-'),CLM_PYMT_OUTWARD_DT,clm_approved_dt,clm_payment_dt,decode(ctd_sf_refund_flag,'Y',ctd_tc_sf_refundable + cwd_wc_sf_refundable,0),ctd_sf_refund_flag \n").append("FROM APPLICATION_DETAIL A,SSI_DETAIL s,CLAIM_DETAIL c,CLAIM_WC_DETAIL cwd,MEMBER_INFO m,CLAIM_APPLICATION_AMOUNT caa,CLAIM_TC_DETAIL ctd \n").append("WHERE A.ssi_reference_number = s.ssi_Reference_number \n").append("AND s.bid = c.bid \n").append("AND C.CLM_REF_NO = cwd.clm_ref_no \n").append("AND C.CLM_REF_NO = ctd.clm_ref_no \n").append("AND A.mem_bnk_id||A.mem_zne_id||A.mem_brn_id = m.mem_bnk_id||m.mem_zne_id||m.mem_brn_id \n").append("AND A.cgpan = caa.cgpan \n").append("AND A.cgpan = ctd.cgpan \n").append("AND A.cgpan = cwd.cgpan \n").append("AND app_loan_type IN ('CC') \n").append("AND clm_status IN ('AP') \n").append("AND TRUNC(").append(clm_date_for_report).append(") <= to_date('").append(todate).append("','DD/MM/YYYY')   \n").append("ORDER BY 1,2,5 \n").append("\n").toString();
            //System.out.println("query1"+query);
        } else if ((memberID.equals(null)) || (memberID.equals(""))) {
            //query = " SELECT mem_bank_name,mem_zone_name,c.mem_bnk_id||c.mem_zne_id||c.mem_brn_id memberid, \nA.cgpan,ssi_unit_name, \nDECODE(NVL(app_reapprove_amount,0),0,app_approved_amount,app_reapprove_amount) appamt, \nctd_npa_outstanding_amt_revise nps_os,ctd_npa_recovered_revise reco, \nLEAST(DECODE(NVL(app_reapprove_amount,0),0,app_approved_amount,app_reapprove_amount),ctd_npa_outstanding_amt_revise) - ctd_npa_recovered_revise netos, \ncaa_applied_amount,ctd_tc_clm_elig_amt,ctd_tc_first_inst_pay_amt,ctd_tc_asf_deductable, \nctd_tc_first_inst_pay_amt - ctd_tc_asf_deductable netpaid,NVL(CLM_PAYMENT_UTR_NO,'-'),NVL(CLM_PAYMENT_AC_NO,'-'),NVL(CLM_PYMT_OUTWARD_NO,'-'),CLM_PYMT_OUTWARD_DT,clm_approved_dt,clm_payment_dt \nFROM APPLICATION_DETAIL A,SSI_DETAIL s,CLAIM_DETAIL c,CLAIM_TC_DETAIL ctd,MEMBER_INFO m,CLAIM_APPLICATION_AMOUNT caa \nWHERE A.ssi_reference_number = s.ssi_Reference_number \nAND s.bid = c.bid \nAND C.CLM_REF_NO = ctd.clm_ref_no \nAND A.mem_bnk_id||A.mem_zne_id||A.mem_brn_id = m.mem_bnk_id||m.mem_zne_id||m.mem_brn_id \nAND A.cgpan = caa.cgpan \nAND app_loan_type IN ('TC') \nAND clm_status IN ('AP') \nAND TRUNC(" + clm_date_for_report + ") >=  to_date('" + fromdate + "','DD/MM/YYYY')    \n" + "AND TRUNC(" + clm_date_for_report + ") <= to_date('" + todate + "','DD/MM/YYYY')     \n" + "UNION ALL \n" + "SELECT mem_bank_name,mem_zone_name,c.mem_bnk_id||c.mem_zne_id||c.mem_brn_id memberid, \n" + "A.cgpan,ssi_unit_name, \n" + "DECODE(NVL(app_reapprove_amount,0),0,app_approved_amount,app_reapprove_amount) appamt, \n" + "cwd_npa_outstanding_amt_revise nps_os,cwd_npa_recovered_revise reco, \n" + "LEAST(DECODE(NVL(app_reapprove_amount,0),0,app_approved_amount,app_reapprove_amount),cwd_npa_outstanding_amt_revise) - cwd_npa_recovered_revise netos, \n" + "caa_applied_amount,cwd_wc_clm_elig_amt,cwd_wc_first_inst_pay_amt,cwd_wc_asf_deductable, \n" + "cwd_wc_first_inst_pay_amt - cwd_wc_asf_deductable netpaid,NVL(CLM_PAYMENT_UTR_NO,'-'),NVL(CLM_PAYMENT_AC_NO,'-'),NVL(CLM_PYMT_OUTWARD_NO,'-'),CLM_PYMT_OUTWARD_DT,clm_approved_dt,clm_payment_dt \n" + "FROM APPLICATION_DETAIL A,SSI_DETAIL s,CLAIM_DETAIL c,CLAIM_WC_DETAIL cwd,MEMBER_INFO m,CLAIM_APPLICATION_AMOUNT caa \n" + "WHERE A.ssi_reference_number = s.ssi_Reference_number \n" + "AND s.bid = c.bid \n" + "AND C.CLM_REF_NO = cwd.clm_ref_no \n" + "AND A.mem_bnk_id||A.mem_zne_id||A.mem_brn_id = m.mem_bnk_id||m.mem_zne_id||m.mem_brn_id \n" + "AND A.cgpan = caa.cgpan \n" + "AND app_loan_type IN ('WC') \n" + "AND clm_status IN ('AP') \n" + "AND TRUNC(" + clm_date_for_report + ") >= to_date('" + fromdate + "','DD/MM/YYYY')    \n" + "AND TRUNC(" + clm_date_for_report + ") <= to_date('" + todate + "','DD/MM/YYYY')    \n" + "UNION ALL \n" + "SELECT mem_bank_name,mem_zone_name,c.mem_bnk_id||c.mem_zne_id||c.mem_brn_id memberid, \n" + "A.cgpan,ssi_unit_name, \n" + "DECODE(NVL(app_reapprove_amount,0),0,app_approved_amount,app_reapprove_amount) appamt, \n" + "cwd_npa_outstanding_amt_revise + ctd_npa_outstanding_amt_revise nps_os,cwd_npa_recovered_revise + ctd_npa_recovered_revise reco, \n" + "LEAST(DECODE(NVL(app_reapprove_amount,0),0,app_approved_amount,app_reapprove_amount),cwd_npa_outstanding_amt_revise + ctd_npa_outstanding_amt_revise) - cwd_npa_recovered_revise + ctd_npa_recovered_revise netos, \n" + "caa_applied_amount,cwd_wc_clm_elig_amt + ctd_tc_clm_elig_amt,cwd_wc_first_inst_pay_amt + ctd_tc_first_inst_pay_amt,cwd_wc_asf_deductable + cwd_wc_asf_deductable, \n" + "cwd_wc_first_inst_pay_amt - cwd_wc_asf_deductable + cwd_wc_first_inst_pay_amt - cwd_wc_asf_deductable netpaid,NVL(CLM_PAYMENT_UTR_NO,'-'),NVL(CLM_PAYMENT_AC_NO,'-'),NVL(CLM_PYMT_OUTWARD_NO,'-'),CLM_PYMT_OUTWARD_DT,clm_approved_dt,clm_payment_dt \n" + "FROM APPLICATION_DETAIL A,SSI_DETAIL s,CLAIM_DETAIL c,CLAIM_WC_DETAIL cwd,MEMBER_INFO m,CLAIM_APPLICATION_AMOUNT caa,CLAIM_TC_DETAIL ctd \n" + "WHERE A.ssi_reference_number = s.ssi_Reference_number \n" + "AND s.bid = c.bid \n" + "AND C.CLM_REF_NO = cwd.clm_ref_no \n" + "AND C.CLM_REF_NO = ctd.clm_ref_no \n" + "AND A.mem_bnk_id||A.mem_zne_id||A.mem_brn_id = m.mem_bnk_id||m.mem_zne_id||m.mem_brn_id \n" + "AND A.cgpan = caa.cgpan \n" + "AND app_loan_type IN ('CC') \n" + "AND clm_status IN ('AP') \n" + "AND TRUNC(" + clm_date_for_report + ") >= to_date('" + fromdate + "','DD/MM/YYYY')    \n" + "AND TRUNC(" + clm_date_for_report + ") <= to_date('" + todate + "','DD/MM/YYYY')   \n" + "ORDER BY 1,2,5 \n" + "\n";
            query = 
                    (new StringBuilder()).append(" SELECT mem_bank_name,mem_zone_name,c.mem_bnk_id||c.mem_zne_id||c.mem_brn_id memberid, \nA.cgpan,ssi_unit_name, \nDECODE(NVL(app_reapprove_amount,0),0,app_approved_amount,app_reapprove_amount) appamt, \nctd_npa_outstanding_amt_revise nps_os,ctd_npa_recovered_revise reco, \nLEAST(DECODE(NVL(app_reapprove_amount,0),0,app_approved_amount,app_reapprove_amount),ctd_npa_outstanding_amt_revise) - ctd_npa_recovered_revise netos, \ncaa_applied_amount,ctd_tc_clm_elig_amt,ctd_tc_first_inst_pay_amt,ctd_tc_asf_deductable, \nctd_tc_first_inst_pay_amt - ctd_tc_asf_deductable netpaid,NVL(CLM_PAYMENT_UTR_NO,'-'),NVL(CLM_PAYMENT_AC_NO,'-'),NVL(CLM_PYMT_OUTWARD_NO,'-'),CLM_PYMT_OUTWARD_DT,clm_approved_dt,clm_payment_dt,decode(ctd_sf_refund_flag,'Y',ctd_tc_sf_refundable,0),ctd_sf_refund_flag \nFROM APPLICATION_DETAIL A,SSI_DETAIL s,CLAIM_DETAIL c,CLAIM_TC_DETAIL ctd,MEMBER_INFO m,CLAIM_APPLICATION_AMOUNT caa \nWHERE A.ssi_reference_number = s.ssi_Reference_number \nAND s.bid = c.bid \nAND C.CLM_REF_NO = ctd.clm_ref_no \nAND A.mem_bnk_id||A.mem_zne_id||A.mem_brn_id = m.mem_bnk_id||m.mem_zne_id||m.mem_brn_id \nAND A.cgpan = caa.cgpan  \nAND   A.cgpan=ctd.cgpan  \nAND  app_loan_type IN ('TC') \nAND clm_status IN ('AP') \nAND TRUNC(").append(clm_date_for_report).append(") >=  to_date('").append(fromdate).append("','DD/MM/YYYY')    \n").append("AND TRUNC(").append(clm_date_for_report).append(") <= to_date('").append(todate).append("','DD/MM/YYYY')     \n").append("UNION ALL \n").append("SELECT mem_bank_name,mem_zone_name,c.mem_bnk_id||c.mem_zne_id||c.mem_brn_id memberid, \n").append("A.cgpan,ssi_unit_name, \n").append("DECODE(NVL(app_reapprove_amount,0),0,app_approved_amount,app_reapprove_amount) appamt, \n").append("cwd_npa_outstanding_amt_revise nps_os,cwd_npa_recovered_revise reco, \n").append("LEAST(DECODE(NVL(app_reapprove_amount,0),0,app_approved_amount,app_reapprove_amount),cwd_npa_outstanding_amt_revise) - cwd_npa_recovered_revise netos, \n").append("caa_applied_amount,cwd_wc_clm_elig_amt,cwd_wc_first_inst_pay_amt,cwd_wc_asf_deductable, \n").append("cwd_wc_first_inst_pay_amt - cwd_wc_asf_deductable netpaid,NVL(CLM_PAYMENT_UTR_NO,'-'),NVL(CLM_PAYMENT_AC_NO,'-'),NVL(CLM_PYMT_OUTWARD_NO,'-'),CLM_PYMT_OUTWARD_DT,clm_approved_dt,clm_payment_dt,decode(cwd_sf_refund_flag,'Y',cwd_wc_sf_refundable,0),cwd_sf_refund_flag \n").append("FROM APPLICATION_DETAIL A,SSI_DETAIL s,CLAIM_DETAIL c,CLAIM_WC_DETAIL cwd,MEMBER_INFO m,CLAIM_APPLICATION_AMOUNT caa \n").append("WHERE A.ssi_reference_number = s.ssi_Reference_number \n").append("AND s.bid = c.bid \n").append("AND C.CLM_REF_NO = cwd.clm_ref_no \n").append("AND A.mem_bnk_id||A.mem_zne_id||A.mem_brn_id = m.mem_bnk_id||m.mem_zne_id||m.mem_brn_id \n").append("AND A.cgpan = caa.cgpan \n").append("AND A.cgpan = cwd.cgpan \n").append("AND app_loan_type IN ('WC') \n").append("AND clm_status IN ('AP') \n").append("AND TRUNC(").append(clm_date_for_report).append(") >= to_date('").append(fromdate).append("','DD/MM/YYYY')    \n").append("AND TRUNC(").append(clm_date_for_report).append(") <= to_date('").append(todate).append("','DD/MM/YYYY')    \n").append("UNION ALL \n").append("SELECT mem_bank_name,mem_zone_name,c.mem_bnk_id||c.mem_zne_id||c.mem_brn_id memberid, \n").append("A.cgpan,ssi_unit_name, \n").append("DECODE(NVL(app_reapprove_amount,0),0,app_approved_amount,app_reapprove_amount) appamt, \n").append("cwd_npa_outstanding_amt_revise + ctd_npa_outstanding_amt_revise nps_os,cwd_npa_recovered_revise + ctd_npa_recovered_revise reco, \n").append("LEAST(DECODE(NVL(app_reapprove_amount,0),0,app_approved_amount,app_reapprove_amount),cwd_npa_outstanding_amt_revise + ctd_npa_outstanding_amt_revise) - cwd_npa_recovered_revise + ctd_npa_recovered_revise netos, \n").append("caa_applied_amount,cwd_wc_clm_elig_amt + ctd_tc_clm_elig_amt,cwd_wc_first_inst_pay_amt + ctd_tc_first_inst_pay_amt,cwd_wc_asf_deductable + cwd_wc_asf_deductable, \n").append("cwd_wc_first_inst_pay_amt - cwd_wc_asf_deductable + cwd_wc_first_inst_pay_amt - cwd_wc_asf_deductable netpaid,NVL(CLM_PAYMENT_UTR_NO,'-'),NVL(CLM_PAYMENT_AC_NO,'-'),NVL(CLM_PYMT_OUTWARD_NO,'-'),CLM_PYMT_OUTWARD_DT,clm_approved_dt,clm_payment_dt,decode(ctd_sf_refund_flag,'Y',ctd_tc_sf_refundable + cwd_wc_sf_refundable,0),ctd_sf_refund_flag \n").append("FROM APPLICATION_DETAIL A,SSI_DETAIL s,CLAIM_DETAIL c,CLAIM_WC_DETAIL cwd,MEMBER_INFO m,CLAIM_APPLICATION_AMOUNT caa,CLAIM_TC_DETAIL ctd \n").append("WHERE A.ssi_reference_number = s.ssi_Reference_number \n").append("AND s.bid = c.bid \n").append("AND C.CLM_REF_NO = cwd.clm_ref_no \n").append("AND C.CLM_REF_NO = ctd.clm_ref_no \n").append("AND A.mem_bnk_id||A.mem_zne_id||A.mem_brn_id = m.mem_bnk_id||m.mem_zne_id||m.mem_brn_id \n").append("AND A.cgpan = caa.cgpan \n").append("AND A.cgpan = ctd.cgpan \n").append("AND A.cgpan = cwd.cgpan \n").append("AND app_loan_type IN ('CC') \n").append("AND clm_status IN ('AP') \n").append("AND TRUNC(").append(clm_date_for_report).append(") >= to_date('").append(fromdate).append("','DD/MM/YYYY')    \n").append("AND TRUNC(").append(clm_date_for_report).append(") <= to_date('").append(todate).append("','DD/MM/YYYY')   \n").append("ORDER BY 1,2,5 \n").append("\n").toString();
            //System.out.println("query2"+query);
        } else {
            //query = " SELECT mem_bank_name,mem_zone_name,c.mem_bnk_id||c.mem_zne_id||c.mem_brn_id memberid, \nA.cgpan,ssi_unit_name, \nDECODE(NVL(app_reapprove_amount,0),0,app_approved_amount,app_reapprove_amount) appamt, \nctd_npa_outstanding_amt_revise nps_os,ctd_npa_recovered_revise reco, \nLEAST(DECODE(NVL(app_reapprove_amount,0),0,app_approved_amount,app_reapprove_amount),ctd_npa_outstanding_amt_revise) - ctd_npa_recovered_revise netos, \ncaa_applied_amount,ctd_tc_clm_elig_amt,ctd_tc_first_inst_pay_amt,ctd_tc_asf_deductable, \nctd_tc_first_inst_pay_amt - ctd_tc_asf_deductable netpaid,NVL(CLM_PAYMENT_UTR_NO,'-'),NVL(CLM_PAYMENT_AC_NO,'-'),NVL(CLM_PYMT_OUTWARD_NO,'-'),CLM_PYMT_OUTWARD_DT,clm_approved_dt,clm_payment_dt \nFROM APPLICATION_DETAIL A,SSI_DETAIL s,CLAIM_DETAIL c,CLAIM_TC_DETAIL ctd,MEMBER_INFO m,CLAIM_APPLICATION_AMOUNT caa \nWHERE A.ssi_reference_number = s.ssi_Reference_number \nAND s.bid = c.bid \nAND C.CLM_REF_NO = ctd.clm_ref_no \nAND A.mem_bnk_id||A.mem_zne_id||A.mem_brn_id = m.mem_bnk_id||m.mem_zne_id||m.mem_brn_id \nAND A.cgpan = caa.cgpan \nAND app_loan_type IN ('TC') \nAND clm_status IN ('AP') \nAND TRUNC(" + clm_date_for_report + ") >=  to_date('" + fromdate + "','DD/MM/YYYY')    \n" + "AND TRUNC(" + clm_date_for_report + ") <= to_date('" + todate + "','DD/MM/YYYY')   and c.mem_bnk_id||c.mem_zne_id||c.mem_brn_id='" + memberID + "'  \n" + "UNION ALL \n" + "SELECT mem_bank_name,mem_zone_name,c.mem_bnk_id||c.mem_zne_id||c.mem_brn_id memberid, \n" + "A.cgpan,ssi_unit_name, \n" + "DECODE(NVL(app_reapprove_amount,0),0,app_approved_amount,app_reapprove_amount) appamt, \n" + "cwd_npa_outstanding_amt_revise nps_os,cwd_npa_recovered_revise reco, \n" + "LEAST(DECODE(NVL(app_reapprove_amount,0),0,app_approved_amount,app_reapprove_amount),cwd_npa_outstanding_amt_revise) - cwd_npa_recovered_revise netos, \n" + "caa_applied_amount,cwd_wc_clm_elig_amt,cwd_wc_first_inst_pay_amt,cwd_wc_asf_deductable, \n" + "cwd_wc_first_inst_pay_amt - cwd_wc_asf_deductable netpaid,NVL(CLM_PAYMENT_UTR_NO,'-'),NVL(CLM_PAYMENT_AC_NO,'-'),NVL(CLM_PYMT_OUTWARD_NO,'-'),CLM_PYMT_OUTWARD_DT,clm_approved_dt,clm_payment_dt \n" + "FROM APPLICATION_DETAIL A,SSI_DETAIL s,CLAIM_DETAIL c,CLAIM_WC_DETAIL cwd,MEMBER_INFO m,CLAIM_APPLICATION_AMOUNT caa \n" + "WHERE A.ssi_reference_number = s.ssi_Reference_number \n" + "AND s.bid = c.bid \n" + "AND C.CLM_REF_NO = cwd.clm_ref_no \n" + "AND A.mem_bnk_id||A.mem_zne_id||A.mem_brn_id = m.mem_bnk_id||m.mem_zne_id||m.mem_brn_id \n" + "AND A.cgpan = caa.cgpan \n" + "AND app_loan_type IN ('WC') \n" + "AND clm_status IN ('AP') \n" + "AND TRUNC(" + clm_date_for_report + ") >= to_date('" + fromdate + "','DD/MM/YYYY')    \n" + "AND TRUNC(" + clm_date_for_report + ") <= to_date('" + todate + "','DD/MM/YYYY')  and c.mem_bnk_id||c.mem_zne_id||c.mem_brn_id='" + memberID + "'  \n" + "UNION ALL \n" + "SELECT mem_bank_name,mem_zone_name,c.mem_bnk_id||c.mem_zne_id||c.mem_brn_id memberid, \n" + "A.cgpan,ssi_unit_name, \n" + "DECODE(NVL(app_reapprove_amount,0),0,app_approved_amount,app_reapprove_amount) appamt, \n" + "cwd_npa_outstanding_amt_revise + ctd_npa_outstanding_amt_revise nps_os,cwd_npa_recovered_revise + ctd_npa_recovered_revise reco, \n" + "LEAST(DECODE(NVL(app_reapprove_amount,0),0,app_approved_amount,app_reapprove_amount),cwd_npa_outstanding_amt_revise + ctd_npa_outstanding_amt_revise) - cwd_npa_recovered_revise + ctd_npa_recovered_revise netos, \n" + "caa_applied_amount,cwd_wc_clm_elig_amt + ctd_tc_clm_elig_amt,cwd_wc_first_inst_pay_amt + ctd_tc_first_inst_pay_amt,cwd_wc_asf_deductable + cwd_wc_asf_deductable, \n" + "cwd_wc_first_inst_pay_amt - cwd_wc_asf_deductable + cwd_wc_first_inst_pay_amt - cwd_wc_asf_deductable netpaid,NVL(CLM_PAYMENT_UTR_NO,'-'),NVL(CLM_PAYMENT_AC_NO,'-'),NVL(CLM_PYMT_OUTWARD_NO,'-'),CLM_PYMT_OUTWARD_DT,clm_approved_dt,clm_payment_dt \n" + "FROM APPLICATION_DETAIL A,SSI_DETAIL s,CLAIM_DETAIL c,CLAIM_WC_DETAIL cwd,MEMBER_INFO m,CLAIM_APPLICATION_AMOUNT caa,CLAIM_TC_DETAIL ctd \n" + "WHERE A.ssi_reference_number = s.ssi_Reference_number \n" + "AND s.bid = c.bid \n" + "AND C.CLM_REF_NO = cwd.clm_ref_no \n" + "AND C.CLM_REF_NO = ctd.clm_ref_no \n" + "AND A.mem_bnk_id||A.mem_zne_id||A.mem_brn_id = m.mem_bnk_id||m.mem_zne_id||m.mem_brn_id \n" + "AND A.cgpan = caa.cgpan \n" + "AND app_loan_type IN ('CC') \n" + "AND clm_status IN ('AP') \n" + "AND TRUNC(" + clm_date_for_report + ") >= to_date('" + fromdate + "','DD/MM/YYYY')    \n" + "AND TRUNC(" + clm_date_for_report + ") <= to_date('" + todate + "','DD/MM/YYYY')  and c.mem_bnk_id||c.mem_zne_id||c.mem_brn_id='" + memberID + "'  \n" + "ORDER BY 1,2,5 \n" + "\n";
            query = 
                    (new StringBuilder()).append(" SELECT mem_bank_name,mem_zone_name,c.mem_bnk_id||c.mem_zne_id||c.mem_brn_id memberid, \nA.cgpan,ssi_unit_name, \nDECODE(NVL(app_reapprove_amount,0),0,app_approved_amount,app_reapprove_amount) appamt, \nctd_npa_outstanding_amt_revise nps_os,ctd_npa_recovered_revise reco, \nLEAST(DECODE(NVL(app_reapprove_amount,0),0,app_approved_amount,app_reapprove_amount),ctd_npa_outstanding_amt_revise) - ctd_npa_recovered_revise netos, \ncaa_applied_amount,ctd_tc_clm_elig_amt,ctd_tc_first_inst_pay_amt,ctd_tc_asf_deductable, \nctd_tc_first_inst_pay_amt - ctd_tc_asf_deductable netpaid,NVL(CLM_PAYMENT_UTR_NO,'-'),NVL(CLM_PAYMENT_AC_NO,'-'),NVL(CLM_PYMT_OUTWARD_NO,'-'),CLM_PYMT_OUTWARD_DT,clm_approved_dt,clm_payment_dt,decode(ctd_sf_refund_flag,'Y',ctd_tc_sf_refundable,0),ctd_sf_refund_flag \nFROM APPLICATION_DETAIL A,SSI_DETAIL s,CLAIM_DETAIL c,CLAIM_TC_DETAIL ctd,MEMBER_INFO m,CLAIM_APPLICATION_AMOUNT caa \nWHERE A.ssi_reference_number = s.ssi_Reference_number \nAND s.bid = c.bid \nAND C.CLM_REF_NO = ctd.clm_ref_no \nAND A.mem_bnk_id||A.mem_zne_id||A.mem_brn_id = m.mem_bnk_id||m.mem_zne_id||m.mem_brn_id \nAND A.cgpan = caa.cgpan    \nAND  A.cgpan=ctd.cgpan \nAND app_loan_type IN ('TC') \nAND clm_status IN ('AP') \nAND TRUNC(").append(clm_date_for_report).append(") >=  to_date('").append(fromdate).append("','DD/MM/YYYY')    \n").append("AND TRUNC(").append(clm_date_for_report).append(") <= to_date('").append(todate).append("','DD/MM/YYYY')   and c.mem_bnk_id||c.mem_zne_id||c.mem_brn_id='").append(memberID).append("'  \n").append("UNION ALL \n").append("SELECT mem_bank_name,mem_zone_name,c.mem_bnk_id||c.mem_zne_id||c.mem_brn_id memberid, \n").append("A.cgpan,ssi_unit_name, \n").append("DECODE(NVL(app_reapprove_amount,0),0,app_approved_amount,app_reapprove_amount) appamt, \n").append("cwd_npa_outstanding_amt_revise nps_os,cwd_npa_recovered_revise reco, \n").append("LEAST(DECODE(NVL(app_reapprove_amount,0),0,app_approved_amount,app_reapprove_amount),cwd_npa_outstanding_amt_revise) - cwd_npa_recovered_revise netos, \n").append("caa_applied_amount,cwd_wc_clm_elig_amt,cwd_wc_first_inst_pay_amt,cwd_wc_asf_deductable, \n").append("cwd_wc_first_inst_pay_amt - cwd_wc_asf_deductable netpaid,NVL(CLM_PAYMENT_UTR_NO,'-'),NVL(CLM_PAYMENT_AC_NO,'-'),NVL(CLM_PYMT_OUTWARD_NO,'-'),CLM_PYMT_OUTWARD_DT,clm_approved_dt,clm_payment_dt,decode(cwd_sf_refund_flag,'Y',cwd_wc_sf_refundable,0),cwd_sf_refund_flag \n").append("FROM APPLICATION_DETAIL A,SSI_DETAIL s,CLAIM_DETAIL c,CLAIM_WC_DETAIL cwd,MEMBER_INFO m,CLAIM_APPLICATION_AMOUNT caa \n").append("WHERE A.ssi_reference_number = s.ssi_Reference_number \n").append("AND s.bid = c.bid \n").append("AND C.CLM_REF_NO = cwd.clm_ref_no \n").append("AND A.mem_bnk_id||A.mem_zne_id||A.mem_brn_id = m.mem_bnk_id||m.mem_zne_id||m.mem_brn_id \n").append("AND A.cgpan = caa.cgpan \n").append("AND A.cgpan = cwd.cgpan \n").append("AND app_loan_type IN ('WC') \n").append("AND clm_status IN ('AP') \n").append("AND TRUNC(").append(clm_date_for_report).append(") >= to_date('").append(fromdate).append("','DD/MM/YYYY')    \n").append("AND TRUNC(").append(clm_date_for_report).append(") <= to_date('").append(todate).append("','DD/MM/YYYY')  and c.mem_bnk_id||c.mem_zne_id||c.mem_brn_id='").append(memberID).append("'  \n").append("UNION ALL \n").append("SELECT mem_bank_name,mem_zone_name,c.mem_bnk_id||c.mem_zne_id||c.mem_brn_id memberid, \n").append("A.cgpan,ssi_unit_name, \n").append("DECODE(NVL(app_reapprove_amount,0),0,app_approved_amount,app_reapprove_amount) appamt, \n").append("cwd_npa_outstanding_amt_revise + ctd_npa_outstanding_amt_revise nps_os,cwd_npa_recovered_revise + ctd_npa_recovered_revise reco, \n").append("LEAST(DECODE(NVL(app_reapprove_amount,0),0,app_approved_amount,app_reapprove_amount),cwd_npa_outstanding_amt_revise + ctd_npa_outstanding_amt_revise) - cwd_npa_recovered_revise + ctd_npa_recovered_revise netos, \n").append("caa_applied_amount,cwd_wc_clm_elig_amt + ctd_tc_clm_elig_amt,cwd_wc_first_inst_pay_amt + ctd_tc_first_inst_pay_amt,cwd_wc_asf_deductable + cwd_wc_asf_deductable, \n").append("cwd_wc_first_inst_pay_amt - cwd_wc_asf_deductable + cwd_wc_first_inst_pay_amt - cwd_wc_asf_deductable netpaid,NVL(CLM_PAYMENT_UTR_NO,'-'),NVL(CLM_PAYMENT_AC_NO,'-'),NVL(CLM_PYMT_OUTWARD_NO,'-'),CLM_PYMT_OUTWARD_DT,clm_approved_dt,clm_payment_dt,decode(ctd_sf_refund_flag,'Y',ctd_tc_sf_refundable + cwd_wc_sf_refundable,0),ctd_sf_refund_flag \n").append("FROM APPLICATION_DETAIL A,SSI_DETAIL s,CLAIM_DETAIL c,CLAIM_WC_DETAIL cwd,MEMBER_INFO m,CLAIM_APPLICATION_AMOUNT caa,CLAIM_TC_DETAIL ctd \n").append("WHERE A.ssi_reference_number = s.ssi_Reference_number \n").append("AND s.bid = c.bid \n").append("AND C.CLM_REF_NO = cwd.clm_ref_no \n").append("AND C.CLM_REF_NO = ctd.clm_ref_no \n").append("AND A.mem_bnk_id||A.mem_zne_id||A.mem_brn_id = m.mem_bnk_id||m.mem_zne_id||m.mem_brn_id \n").append("AND A.cgpan = caa.cgpan \n").append("AND A.cgpan = ctd.cgpan \n").append("AND A.cgpan = cwd.cgpan \n").append("AND app_loan_type IN ('CC') \n").append("AND clm_status IN ('AP') \n").append("AND TRUNC(").append(clm_date_for_report).append(") >= to_date('").append(fromdate).append("','DD/MM/YYYY')    \n").append("AND TRUNC(").append(clm_date_for_report).append(") <= to_date('").append(todate).append("','DD/MM/YYYY')  and c.mem_bnk_id||c.mem_zne_id||c.mem_brn_id='").append(memberID).append("'  \n").append("ORDER BY 1,2,5 \n").append("\n").toString();
            //System.out.println("query3"+query);
        }
        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
        java.util.Date clm_approved_date = null;
        java.util.Date clm_payment_date = null;
        String numberformat = "-?\\d+(\\.\\d+)?";
        String firstInstAmt = "0.0";
        String asfDeductedAmt = "0.0";
        String netPaidAmt = "0.0";
        java.util.Date clm_outward_date = null;
        String refundableAmt = "0.0";
        try {
            Statement pendingCaseDetailsStmt = null;
            ResultSet pendingCaseDetailsResult = null;
            pendingCaseDetailsStmt = connection.createStatement();
            for (pendingCaseDetailsResult = 
                 pendingCaseDetailsStmt.executeQuery(query); 
                 pendingCaseDetailsResult.next(); 
                 PAArrayList.add(PAStringArray)) {
                PAStringArray = new String[22];
                PAStringArray[0] = pendingCaseDetailsResult.getString(1);
                PAStringArray[1] = pendingCaseDetailsResult.getString(2);
                PAStringArray[2] = pendingCaseDetailsResult.getString(3);
                PAStringArray[3] = pendingCaseDetailsResult.getString(4);
                PAStringArray[4] = pendingCaseDetailsResult.getString(5);
                PAStringArray[5] = pendingCaseDetailsResult.getString(6);
                PAStringArray[6] = pendingCaseDetailsResult.getString(7);
                PAStringArray[7] = pendingCaseDetailsResult.getString(8);
                PAStringArray[8] = pendingCaseDetailsResult.getString(9);
                PAStringArray[9] = pendingCaseDetailsResult.getString(10);
                PAStringArray[10] = pendingCaseDetailsResult.getString(11);

                firstInstAmt = 
                        String.valueOf(pendingCaseDetailsResult.getString(12));
                asfDeductedAmt = 
                        String.valueOf(pendingCaseDetailsResult.getString(13));
                netPaidAmt = 
                        String.valueOf(pendingCaseDetailsResult.getString(14));

                PAStringArray[14] = pendingCaseDetailsResult.getString(15);
                PAStringArray[15] = pendingCaseDetailsResult.getString(16);
                PAStringArray[16] = pendingCaseDetailsResult.getString(17);
                clm_outward_date = pendingCaseDetailsResult.getDate(18);

                clm_approved_date = pendingCaseDetailsResult.getDate(19);
                clm_payment_date = pendingCaseDetailsResult.getDate(20);

                if (firstInstAmt.matches(numberformat))
                    PAStringArray[11] = firstInstAmt;
                else
                    PAStringArray[11] = "0.0";
                if (asfDeductedAmt.matches(numberformat))
                    PAStringArray[12] = asfDeductedAmt;
                else
                    PAStringArray[12] = "0.0";
                if (netPaidAmt.matches(numberformat))
                    PAStringArray[13] = netPaidAmt;
                else {
                    PAStringArray[13] = "0.0";
                }
                if (clm_outward_date != null)
                    PAStringArray[17] = sdf.format(clm_outward_date);
                else {
                    PAStringArray[17] = "";
                }
                if (clm_approved_date != null)
                    PAStringArray[18] = sdf.format(clm_approved_date);
                else
                    PAStringArray[18] = "";
                if (clm_payment_date != null)
                    PAStringArray[19] = sdf.format(clm_payment_date);
                else {
                    PAStringArray[19] = "";
                }

                refundableAmt = 
                        String.valueOf(pendingCaseDetailsResult.getString(21));


                if (refundableAmt.matches(numberformat)) {
                    PAStringArray[20] = refundableAmt;
                } else {
                    PAStringArray[20] = "0.0";
                }

                PAStringArray[21] = pendingCaseDetailsResult.getString(22);
            }

            pendingCaseDetailsResult.close();
            pendingCaseDetailsResult = null;
            pendingCaseDetailsStmt.close();
            pendingCaseDetailsStmt = null;
        } catch (Exception e) {
            Log.logException(e);
            throw new DatabaseException(e.getMessage());
        } finally {
            DBConnection.freeConnection(connection);
        }
        request.setAttribute("reportCondition", condition);
        request.setAttribute("pendingCaseDetailsArray", PAArrayList);
        request.setAttribute("pendingCaseDetailsArray_size", 
                             new Integer(PAArrayList.size()).toString());
        Log.log(4, "NewReportsAction", "Claim settled", "Exited");

        return mapping.findForward("success");
    }
*/
  //KULDEEP
	public ActionForward claimSettledReport(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		String condition = request.getParameter("condition");
		DynaActionForm dynaForm = (DynaActionForm) form;
		Log.log(4, "NewReportsAction", "Claim settled", "Entered");
		HttpSession session = request.getSession();
		Connection connection = DBConnection.getConnection();
		String query = "";
		Date fromdate = (Date) dynaForm.get("dateOfTheDocument26");
		Date todate = (Date) dynaForm.get("dateOfTheDocument27");
		String memberID = (String) dynaForm.get("memberId");
		String ssi_name = ((String) dynaForm.get("ssi_name")).trim();
		String clm_date_for_report = null;
		if ("approvedDateWiseReport".equals(condition))
			clm_date_for_report = "clm_approved_dt";
		else if ("paymentDateWiseReport".equals(condition))
			clm_date_for_report = "clm_payment_dt";
		else
			return mapping.findForward("nocondition");
		
	
		String PAStringArray[] = null;
		ArrayList PAArrayList = new ArrayList();
		String id = "";
		ClaimsProcessor claimsProcessor = new ClaimsProcessor();
		Vector memberids = new Vector();
		memberids = claimsProcessor.getAllMemberIds();
		request.setAttribute("date1", fromdate);
		request.setAttribute("date2", todate);
		
		String stDate = String.valueOf(fromdate);
		String todateNew = String.valueOf(todate);
		
		if (stDate.equals(null) || stDate.equals(""))
			throw new NoDataException(
					"From Date should not be empty, Please Enter from Date ");
		if (todateNew.equals(null) || todateNew.equals(""))
			throw new NoDataException(
					"To Date should not be empty, Please Enter To Date ");
		if (memberID.length() == 12 && !memberids.contains(memberID)) {
			Log.log(2, "CPDAO", "getAllMemberIds()",
					"No Member Ids in the database!");
			throw new DatabaseException("No Member Ids in the database");
		}
		if (fromdate == null && memberID.equals("")) {
			/*query = (new StringBuilder())
					.append(" SELECT mem_bank_name,mem_zone_name,c.mem_bnk_id||c.mem_zne_id||c.mem_brn_id memberid, \nA.cgpan,ssi_unit_name, \nDECODE(NVL(app_reapprove_amount,0),0,app_approved_amount,app_reapprove_amount) appamt, \nctd_npa_outstanding_amt_revise nps_os,ctd_npa_recovered_revise reco, \nLEAST(DECODE(NVL(app_reapprove_amount,0),0,app_approved_amount,app_reapprove_amount),ctd_npa_outstanding_amt_revise) - ctd_npa_recovered_revise netos, \ncaa_applied_amount,ctd_tc_clm_elig_amt,ctd_tc_first_inst_pay_amt,ctd_tc_asf_deductable, \nctd_tc_first_inst_pay_amt - ctd_tc_asf_deductable netpaid,NVL(CLM_PAYMENT_UTR_NO,''),NVL(CLM_PAYMENT_AC_NO,'-'),NVL(CLM_PYMT_OUTWARD_NO,''),CLM_PYMT_OUTWARD_DT,clm_approved_dt,clm_payment_dt,decode(ctd_sf_refund_flag,'Y',ctd_tc_sf_refundable,0),ctd_sf_refund_flag \nFROM APPLICATION_DETAIL A,SSI_DETAIL s,CLAIM_DETAIL c,CLAIM_TC_DETAIL ctd,MEMBER_INFO m,CLAIM_APPLICATION_AMOUNT caa \nWHERE A.ssi_reference_number = s.ssi_Reference_number \nAND s.bid = c.bid \nAND C.CLM_REF_NO = ctd.clm_ref_no \nAND A.mem_bnk_id||A.mem_zne_id||A.mem_brn_id = m.mem_bnk_id||m.mem_zne_id||m.mem_brn_id \nAND A.cgpan = caa.cgpan \nAND  A.cgpan=ctd.cgpan \nAND     app_loan_type IN ('TC') \nAND clm_status IN ('AP') \nAND TRUNC(")
					.append(clm_date_for_report)
					.append(") <= to_date('")
					.append(todate)
					.append("','DD/MM/YYYY')     \n")
					.append("UNION ALL \n")
					.append("SELECT mem_bank_name,mem_zone_name,c.mem_bnk_id||c.mem_zne_id||c.mem_brn_id memberid, \n")
					.append("A.cgpan,ssi_unit_name, \n")
					.append("DECODE(NVL(app_reapprove_amount,0),0,app_approved_amount,app_reapprove_amount) appamt, \n")
					.append("cwd_npa_outstanding_amt_revise nps_os,cwd_npa_recovered_revise reco, \n")
					.append("LEAST(DECODE(NVL(app_reapprove_amount,0),0,app_approved_amount,app_reapprove_amount),cwd_npa_outstanding_amt_revise) - cwd_npa_recovered_revise netos, \n")
					.append("caa_applied_amount,cwd_wc_clm_elig_amt,cwd_wc_first_inst_pay_amt,cwd_wc_asf_deductable, \n")
					.append("cwd_wc_first_inst_pay_amt - cwd_wc_asf_deductable netpaid,NVL(CLM_PAYMENT_UTR_NO,''),NVL(CLM_PAYMENT_AC_NO,'-'),NVL(CLM_PYMT_OUTWARD_NO,'-'),CLM_PYMT_OUTWARD_DT,clm_approved_dt,clm_payment_dt,decode(cwd_sf_refund_flag,'Y',cwd_wc_sf_refundable,0),cwd_sf_refund_flag \n")
					.append("FROM APPLICATION_DETAIL A,SSI_DETAIL s,CLAIM_DETAIL c,CLAIM_WC_DETAIL cwd,MEMBER_INFO m,CLAIM_APPLICATION_AMOUNT caa \n")
					.append("WHERE A.ssi_reference_number = s.ssi_Reference_number \n")
					.append("AND s.bid = c.bid \n")
					.append("AND C.CLM_REF_NO = cwd.clm_ref_no \n")
					.append("AND A.mem_bnk_id||A.mem_zne_id||A.mem_brn_id = m.mem_bnk_id||m.mem_zne_id||m.mem_brn_id \n")
					.append("AND A.cgpan = caa.cgpan \n")
					.append("AND A.cgpan = cwd.cgpan \n")
					.append("AND app_loan_type IN ('WC') \n")
					.append("AND clm_status IN ('AP') \n")
					.append("AND TRUNC(")
					.append(clm_date_for_report)
					.append(") <= to_date('")
					.append(todate)
					.append("','DD/MM/YYYY')    \n")
					.append("UNION ALL \n")
					.append("SELECT mem_bank_name,mem_zone_name,c.mem_bnk_id||c.mem_zne_id||c.mem_brn_id memberid, \n")
					.append("A.cgpan,ssi_unit_name, \n")
					.append("DECODE(NVL(app_reapprove_amount,0),0,app_approved_amount,app_reapprove_amount) appamt, \n")
					.append("cwd_npa_outstanding_amt_revise + ctd_npa_outstanding_amt_revise nps_os,cwd_npa_recovered_revise + ctd_npa_recovered_revise reco, \n")
					.append("LEAST(DECODE(NVL(app_reapprove_amount,0),0,app_approved_amount,app_reapprove_amount),cwd_npa_outstanding_amt_revise + ctd_npa_outstanding_amt_revise) - cwd_npa_recovered_revise + ctd_npa_recovered_revise netos, \n")
					.append("caa_applied_amount,cwd_wc_clm_elig_amt + ctd_tc_clm_elig_amt,cwd_wc_first_inst_pay_amt + ctd_tc_first_inst_pay_amt,cwd_wc_asf_deductable + cwd_wc_asf_deductable, \n")
					.append("cwd_wc_first_inst_pay_amt - cwd_wc_asf_deductable + cwd_wc_first_inst_pay_amt - cwd_wc_asf_deductable netpaid,NVL(CLM_PAYMENT_UTR_NO,''),NVL(CLM_PAYMENT_AC_NO,'-'),NVL(CLM_PYMT_OUTWARD_NO,'-'),CLM_PYMT_OUTWARD_DT,clm_approved_dt,clm_payment_dt,decode(ctd_sf_refund_flag,'Y',ctd_tc_sf_refundable + cwd_wc_sf_refundable,0),ctd_sf_refund_flag \n")
					.append("FROM APPLICATION_DETAIL A,SSI_DETAIL s,CLAIM_DETAIL c,CLAIM_WC_DETAIL cwd,MEMBER_INFO m,CLAIM_APPLICATION_AMOUNT caa,CLAIM_TC_DETAIL ctd \n")
					.append("WHERE A.ssi_reference_number = s.ssi_Reference_number \n")
					.append("AND s.bid = c.bid \n")
					.append("AND C.CLM_REF_NO = cwd.clm_ref_no \n")
					.append("AND C.CLM_REF_NO = ctd.clm_ref_no \n")
					.append("AND A.mem_bnk_id||A.mem_zne_id||A.mem_brn_id = m.mem_bnk_id||m.mem_zne_id||m.mem_brn_id \n")
					.append("AND A.cgpan = caa.cgpan \n")
					.append("AND A.cgpan = ctd.cgpan \n")
					.append("AND A.cgpan = cwd.cgpan \n")
					.append("AND app_loan_type IN ('CC') \n")
					.append("AND clm_status IN ('AP') \n").append("AND TRUNC(")
					.append(clm_date_for_report).append(") <= to_date('")
					.append(todate).append("','DD/MM/YYYY')   \n")
					.append("ORDER BY 1,2,5 \n").append("\n").toString();*/
			
			
			 query =  "SELECT mem_bank_name, "
				+ "       mem_zone_name, "
				+ "       c.mem_bnk_id || c.mem_zne_id || c.mem_brn_id memberid, "
				+ "       A.cgpan, "
				+ "       ssi_unit_name, "
				+ "       DECODE (NVL (app_reapprove_amount, 0), "
				+ "               0, app_approved_amount, "
				+ "               app_reapprove_amount) "
				+ "          appamt, "
				+ "       ctd_npa_outstanding_amt_revise nps_os, "
				+ "       ctd_npa_recovered_revise reco, "
				+ "         LEAST ( "
				+ "            DECODE (NVL (app_reapprove_amount, 0), "
				+ "                    0, app_approved_amount, "
				+ "                    app_reapprove_amount), "
				+ "            ctd_npa_outstanding_amt_revise) "
				+ "       - ctd_npa_recovered_revise "
				+ "          netos, "
				+ "       caa_applied_amount, "
				+ "       ctd_tc_clm_elig_amt, "
				+ "       ctd_tc_first_inst_pay_amt, "
				+ "       ctd_tc_asf_deductable, "
				+ "       DECODE ( "
				+ "          NVL (ctd_sf_refund_flag, 'N'), "
				+ "          'N', (  ctd_tc_first_inst_pay_amt "
				+ "                - NVL (ctd_tc_asf_deductable, 0) "
				+ "                - NVL (CTD_SWBH_CESS_TAX_DED, 0) "
				+ "                - NVL (CTD_SERV_TAX_DED, 0) "
				+ "                - NVL (CTD_KK_CESS_TAX_DED, 0)), "
				+ "          (  ctd_tc_first_inst_pay_amt "
				+ "           + NVL (ctd_tc_sf_refundable, 0) "
				+ "           + NVL (CTD_SWBH_CESS_TAX_DED, 0) "
				+ "           + NVL (CTD_SERV_TAX_DED, 0) "
				+ "           + NVL (CTD_KK_CESS_TAX_DED, 0))) "
				+ "          netpaid, "
				+ "       NVL (CLM_PAYMENT_UTR_NO, '-'), "
				+ "       NVL (CLM_PAYMENT_AC_NO, '-'), "
				+ "       NVL (CLM_PYMT_OUTWARD_NO, '-'), "
				+ "       CLM_PYMT_OUTWARD_DT, "
				+ "       clm_approved_dt, "
				+ "       clm_payment_dt, "
				+ "       DECODE (ctd_sf_refund_flag, 'Y', ctd_tc_sf_refundable, 0), "
				+ "       ctd_sf_refund_flag, "
				+ "       DECODE (ctd_sf_refund_flag, 'Y',NVL (CTD_SWBH_CESS_TAX_DED, 0), 0) SWBH_CESS_REFUND, "
				+ "       DECODE (ctd_sf_refund_flag, 'Y',NVL (CTD_SERV_TAX_DED, 0), 0) SERV_TAX_REFUND, "
				+ "       DECODE (ctd_sf_refund_flag, 'Y',NVL (CTD_KK_CESS_TAX_DED, 0), 0) KK_CESS_REFUND, "
				+ "       DECODE (ctd_sf_refund_flag, 'N',NVL (CTD_SWBH_CESS_TAX_DED, 0), 0) SWBH_CESS_DED, "
				+ "       DECODE (ctd_sf_refund_flag, 'N',NVL (CTD_SERV_TAX_DED, 0), 0) SERV_TAX_DED, "
				+ "       DECODE (ctd_sf_refund_flag, 'N',NVL (CTD_KK_CESS_TAX_DED, 0), 0) KK_CESS_DED "
				+ "  FROM APPLICATION_DETAIL A, "
				+ "       SSI_DETAIL s, "
				+ "       CLAIM_DETAIL c, "
				+ "       CLAIM_TC_DETAIL ctd, "
				+ "       MEMBER_INFO m, "
				+ "       CLAIM_APPLICATION_AMOUNT caa "
				+ " WHERE     A.ssi_reference_number = s.ssi_Reference_number "
				+ "       AND s.bid = c.bid "
				+ "       AND C.CLM_REF_NO = ctd.clm_ref_no "
				+ "       AND A.mem_bnk_id || A.mem_zne_id || A.mem_brn_id = "
				+ "              m.mem_bnk_id || m.mem_zne_id || m.mem_brn_id "
				+ "       AND A.cgpan = caa.cgpan "
				+ "       AND A.cgpan = ctd.cgpan "
				+ "       AND app_loan_type IN ('TC') "
				+ "       AND clm_status IN ('AP') "
				+ "   and trunc("+clm_date_for_report+")  <=  to_date('"+todate+"','DD/MM/YYYY')"
				+ "UNION "
				+ "SELECT mem_bank_name, "
				+ "       mem_zone_name, "
				+ "       c.mem_bnk_id || c.mem_zne_id || c.mem_brn_id memberid, "
				+ "       A.cgpan, "
				+ "       ssi_unit_name, "
				+ "       DECODE (NVL (app_reapprove_amount, 0), "
				+ "               0, app_approved_amount, "
				+ "               app_reapprove_amount) "
				+ "          appamt, "
				+ "       cwd_npa_outstanding_amt_revise nps_os, "
				+ "       cwd_npa_recovered_revise reco, "
				+ "         LEAST ( "
				+ "            DECODE (NVL (app_reapprove_amount, 0), "
				+ "                    0, app_approved_amount, "
				+ "                    app_reapprove_amount), "
				+ "            cwd_npa_outstanding_amt_revise) "
				+ "       - cwd_npa_recovered_revise "
				+ "          netos, "
				+ "       caa_applied_amount, "
				+ "       cwd_wc_clm_elig_amt, "
				+ "       cwd_wc_first_inst_pay_amt, "
				+ "       cwd_wc_asf_deductable, "
				+ "       DECODE ( "
				+ "          NVL (cwd_sf_refund_flag, 'N'), "
				+ "          'N', (  cwd_wc_first_inst_pay_amt "
				+ "                - NVL (cwd_wc_asf_deductable, 0) "
				+ "                - NVL (CWD_SWBH_CESS_TAX_DED, 0) "
				+ "                - NVL (CWD_SERV_TAX_DED, 0) "
				+ "                - NVL (CWD_KK_CESS_TAX_DED, 0)), "
				+ "          (  cwd_wc_first_inst_pay_amt "
				+ "           + NVL (cwd_wc_sf_refundable, 0) "
				+ "           + NVL (CWD_SWBH_CESS_TAX_DED, 0) "
				+ "           + NVL (CWD_SERV_TAX_DED, 0) "
				+ "           + NVL (CWD_KK_CESS_TAX_DED, 0))) "
				+ "          netpaid, "
				+ "       NVL (CLM_PAYMENT_UTR_NO, '-'), "
				+ "       NVL (CLM_PAYMENT_AC_NO, '-'), "
				+ "       NVL (CLM_PYMT_OUTWARD_NO, '-'), "
				+ "       CLM_PYMT_OUTWARD_DT, "
				+ "       clm_approved_dt, "
				+ "       clm_payment_dt, "
				+ "       DECODE (cwd_sf_refund_flag, 'Y', cwd_wc_sf_refundable, 0), "
				+ "       cwd_sf_refund_flag, "
				+ "       DECODE (cwd_sf_refund_flag, 'Y',NVL (CWD_SWBH_CESS_TAX_DED, 0), 0) SWBH_CESS_REFUND, "
				+ "       DECODE (cwd_sf_refund_flag, 'Y',NVL (CWD_SERV_TAX_DED, 0), 0) SERV_TAX_REFUND, "
				+ "       DECODE (cwd_sf_refund_flag, 'Y',NVL (CWD_KK_CESS_TAX_DED, 0), 0) KK_CESS_REFUND, "
				+ "        DECODE (cwd_sf_refund_flag, 'N',NVL (CWD_SWBH_CESS_TAX_DED, 0), 0) SWBH_CESS_DED, "
				+ "       DECODE (cwd_sf_refund_flag, 'N',NVL (CWD_SERV_TAX_DED, 0), 0) SERV_TAX_DED, "
				+ "       DECODE (cwd_sf_refund_flag, 'N',NVL (CWD_KK_CESS_TAX_DED, 0), 0) KK_CESS_DED "
				+ "  FROM APPLICATION_DETAIL A, "
				+ "       SSI_DETAIL s, "
				+ "       CLAIM_DETAIL c, "
				+ "       CLAIM_WC_DETAIL cwd, "
				+ "       MEMBER_INFO m, "
				+ "       CLAIM_APPLICATION_AMOUNT caa "
				+ " WHERE     A.ssi_reference_number = s.ssi_Reference_number "
				+ "       AND s.bid = c.bid "
				+ "       AND C.CLM_REF_NO = cwd.clm_ref_no "
				+ "       AND A.mem_bnk_id || A.mem_zne_id || A.mem_brn_id = "
				+ "              m.mem_bnk_id || m.mem_zne_id || m.mem_brn_id "
				+ "       AND A.cgpan = caa.cgpan "
				+ "       AND A.cgpan = cwd.cgpan "
				+ "       AND app_loan_type IN ('WC') "
				+ "       AND clm_status IN ('AP') "
				+ "   and trunc("+clm_date_for_report+")  <=  to_date('"+todate+"','DD/MM/YYYY')"
				+ "UNION "
				+ "SELECT mem_bank_name, "
				+ "       mem_zone_name, "
				+ "       c.mem_bnk_id || c.mem_zne_id || c.mem_brn_id memberid, "
				+ "       A.cgpan, "
				+ "       ssi_unit_name, "
				+ "       DECODE (NVL (app_reapprove_amount, 0), "
				+ "               0, app_approved_amount, "
				+ "               app_reapprove_amount) "
				+ "          appamt, "
				+ "       cwd_npa_outstanding_amt_revise + ctd_npa_outstanding_amt_revise nps_os, "
				+ "       cwd_npa_recovered_revise + ctd_npa_recovered_revise reco, "
				+ "         LEAST ( "
				+ "            DECODE (NVL (app_reapprove_amount, 0), "
				+ "                    0, app_approved_amount, "
				+ "                    app_reapprove_amount), "
				+ "            cwd_npa_outstanding_amt_revise + ctd_npa_outstanding_amt_revise) "
				+ "       - cwd_npa_recovered_revise "
				+ "       + ctd_npa_recovered_revise "
				+ "          netos, "
				+ "       caa_applied_amount, "
				+ "       cwd_wc_clm_elig_amt + ctd_tc_clm_elig_amt, "
				+ "       cwd_wc_first_inst_pay_amt + ctd_tc_first_inst_pay_amt, "
				+ "       cwd_wc_asf_deductable + cwd_wc_asf_deductable, "
				+ "       DECODE ( "
				+ "          NVL (ctd_sf_refund_flag, 'N'), "
				+ "          'N', (  ctd_tc_first_inst_pay_amt "
				+ "                - ctd_tc_asf_deductable "
				+ "                - NVL (CTD_SWBH_CESS_TAX_DED, 0) "
				+ "                - NVL (CTD_SERV_TAX_DED, 0) "
				+ "                - NVL (CTD_KK_CESS_TAX_DED, 0)), "
				+ "          (  ctd_tc_first_inst_pay_amt "
				+ "           + ctd_tc_asf_deductable "
				+ "           + NVL (CTD_SWBH_CESS_TAX_DED, 0) "
				+ "           + NVL (CTD_SERV_TAX_DED, 0) "
				+ "           + NVL (CTD_KK_CESS_TAX_DED, 0))) "
				+ "          netpaid, "
				+ "       NVL (CLM_PAYMENT_UTR_NO, '-'), "
				+ "       NVL (CLM_PAYMENT_AC_NO, '-'), "
				+ "       NVL (CLM_PYMT_OUTWARD_NO, '-'), "
				+ "       CLM_PYMT_OUTWARD_DT, "
				+ "       clm_approved_dt, "
				+ "       clm_payment_dt, "
				+ "       DECODE (ctd_sf_refund_flag, "
				+ "               'Y', ctd_tc_sf_refundable + cwd_wc_sf_refundable, "
				+ "               0), "
				+ "       ctd_sf_refund_flag, "
				+ "       DECODE (ctd_sf_refund_flag, 'Y',NVL (CTD_SWBH_CESS_TAX_DED, 0), 0) SWBH_CESS_REFUND, "
				+ "       DECODE (ctd_sf_refund_flag, 'Y',NVL (CTD_SERV_TAX_DED, 0), 0) SERV_TAX_REFUND, "
				+ "       DECODE (ctd_sf_refund_flag, 'Y',NVL (CTD_KK_CESS_TAX_DED, 0), 0) KK_CESS_REFUND, "
				+ "       DECODE (ctd_sf_refund_flag, 'N',NVL (CTD_SWBH_CESS_TAX_DED, 0), 0) SWBH_CESS_DED, "
				+ "       DECODE (ctd_sf_refund_flag, 'N',NVL (CTD_SERV_TAX_DED, 0), 0) SERV_TAX_DED, "
				+ "       DECODE (ctd_sf_refund_flag, 'N',NVL (CTD_KK_CESS_TAX_DED, 0), 0) KK_CESS_DED "
				+ "  FROM APPLICATION_DETAIL A, "
				+ "       SSI_DETAIL s, "
				+ "       CLAIM_DETAIL c, "
				+ "       CLAIM_WC_DETAIL cwd, "
				+ "       MEMBER_INFO m, "
				+ "       CLAIM_APPLICATION_AMOUNT caa, "
				+ "       CLAIM_TC_DETAIL ctd "
				+ " WHERE     A.ssi_reference_number = s.ssi_Reference_number "
				+ "       AND s.bid = c.bid "
				+ "       AND C.CLM_REF_NO = cwd.clm_ref_no "
				+ "       AND C.CLM_REF_NO = ctd.clm_ref_no "
				+ "       AND A.mem_bnk_id || A.mem_zne_id || A.mem_brn_id = "
				+ "              m.mem_bnk_id || m.mem_zne_id || m.mem_brn_id "
				+ "       AND A.cgpan = caa.cgpan "
				+ "       AND A.cgpan = ctd.cgpan "
				+ "       AND A.cgpan = cwd.cgpan "
				+ "       AND app_loan_type IN ('CC') "
				+ "       AND clm_status IN ('AP') "
				+ "   and trunc("+clm_date_for_report+")  <=  to_date('"+todate+"','DD/MM/YYYY')"
				+ "ORDER BY 1, 2, 5";
			
			
			
			// //System.out.println("query1"+query);
				 /*(new StringBuilder())
             .append(" SELECT distinct mem_bank_name,mem_zone_name,c.mem_bnk_id||c.mem_zne_id||c.mem_brn_id memberid, \nA.cgpan,ssi_unit_name, \nDECODE(NVL(app_reapprove_amount,0),0,app_approved_amount,app_reapprove_amount) appamt, \nctd_npa_outstanding_amt_revise nps_os,ctd_npa_recovered_revise reco, \nLEAST(DECODE(NVL(app_reapprove_amount,0),0,app_approved_amount,app_reapprove_amount),ctd_npa_outstanding_amt_revise) - ctd_npa_recovered_revise netos, \ncaa_applied_amount,ctd_tc_clm_elig_amt,ctd_tc_first_inst_pay_amt,ctd_tc_asf_deductable, \nctd_tc_first_inst_pay_amt - ctd_tc_asf_deductable - nvl(CTD_SWBH_CESS_TAX_DED,0) - nvl(CTD_SERV_TAX_DED,0)-NVL(CTD_KK_CESS_TAX_DED,0) netpaid,NVL(CLM_PAYMENT_UTR_NO,''),NVL(CLM_PAYMENT_AC_NO,'-'),NVL(CLM_PYMT_OUTWARD_NO,''),CLM_PYMT_OUTWARD_DT,clm_approved_dt,clm_payment_dt,decode(ctd_sf_refund_flag,'Y',ctd_tc_sf_refundable,0),ctd_sf_refund_flag,nvl(CTD_SWBH_CESS_TAX_DED,0),nvl(CTD_SERV_TAX_DED,0),NVL(CTD_KK_CESS_TAX_DED,0) \nFROM APPLICATION_DETAIL A,SSI_DETAIL s,CLAIM_DETAIL c,CLAIM_TC_DETAIL ctd,MEMBER_INFO m,CLAIM_APPLICATION_AMOUNT caa \nWHERE A.ssi_reference_number = s.ssi_Reference_number \nAND s.bid = c.bid \nAND C.CLM_REF_NO = ctd.clm_ref_no \nAND A.mem_bnk_id||A.mem_zne_id||A.mem_brn_id = m.mem_bnk_id||m.mem_zne_id||m.mem_brn_id \nAND A.cgpan = caa.cgpan \nAND  A.cgpan=ctd.cgpan \nAND     app_loan_type IN ('TC') \nAND clm_status IN ('AP') \nAND TRUNC(")
             .append(clm_date_for_report)
             .append(") <= to_date('")
             .append(todate)
             .append("','DD/MM/YYYY')     \n")
             .append("UNION ALL \n")
             .append("SELECT distinct mem_bank_name,mem_zone_name,c.mem_bnk_id||c.mem_zne_id||c.mem_brn_id memberid, \n")
             .append("A.cgpan,ssi_unit_name, \n")
             .append("DECODE(NVL(app_reapprove_amount,0),0,app_approved_amount,app_reapprove_amount) appamt, \n")
             .append("cwd_npa_outstanding_amt_revise nps_os,cwd_npa_recovered_revise reco, \n")
             .append("LEAST(DECODE(NVL(app_reapprove_amount,0),0,app_approved_amount,app_reapprove_amount),cwd_npa_outstanding_amt_revise) - cwd_npa_recovered_revise netos, \n")
             .append("caa_applied_amount,cwd_wc_clm_elig_amt,cwd_wc_first_inst_pay_amt,cwd_wc_asf_deductable \n")
             .append("cwd_wc_first_inst_pay_amt - cwd_wc_asf_deductable - nvl(CWD_SWBH_CESS_TAX_DED,0) - nvl(CWD_SERV_TAX_DED,0) -NVL(CWD_KK_CESS_TAX_DED,0) netpaid,NVL(CLM_PAYMENT_UTR_NO,''),NVL(CLM_PAYMENT_AC_NO,'-'),NVL(CLM_PYMT_OUTWARD_NO,'-'),CLM_PYMT_OUTWARD_DT,clm_approved_dt,clm_payment_dt,decode(cwd_sf_refund_flag,'Y',cwd_wc_sf_refundable,0),cwd_sf_refund_flag,nvl(CWD_SWBH_CESS_TAX_DED,0),nvl(CWD_SERV_TAX_DED,0),NVL(CWD_KK_CESS_TAX_DED,0) \n")
             .append("FROM APPLICATION_DETAIL A,SSI_DETAIL s,CLAIM_DETAIL c,CLAIM_WC_DETAIL cwd,MEMBER_INFO m,CLAIM_APPLICATION_AMOUNT caa \n")
             .append("WHERE A.ssi_reference_number = s.ssi_Reference_number \n")
             .append("AND s.bid = c.bid \n")
             .append("AND C.CLM_REF_NO = cwd.clm_ref_no \n")
             .append("AND A.mem_bnk_id||A.mem_zne_id||A.mem_brn_id = m.mem_bnk_id||m.mem_zne_id||m.mem_brn_id \n")
             .append("AND A.cgpan = caa.cgpan \n")
             .append("AND A.cgpan = cwd.cgpan \n")
             .append("AND app_loan_type IN ('WC') \n")
             .append("AND clm_status IN ('AP') \n")
             .append("AND TRUNC(")
             .append(clm_date_for_report)
             .append(") <= to_date('")
             .append(todate)
             .append("','DD/MM/YYYY')    \n")
             .append("UNION ALL \n")
             .append("SELECT distinct mem_bank_name,mem_zone_name,c.mem_bnk_id||c.mem_zne_id||c.mem_brn_id memberid, \n")
             .append("A.cgpan,ssi_unit_name, \n")
             .append("DECODE(NVL(app_reapprove_amount,0),0,app_approved_amount,app_reapprove_amount) appamt, \n")
             .append("cwd_npa_outstanding_amt_revise + ctd_npa_outstanding_amt_revise nps_os,cwd_npa_recovered_revise + ctd_npa_recovered_revise reco, \n")
             .append("LEAST(DECODE(NVL(app_reapprove_amount,0),0,app_approved_amount,app_reapprove_amount),cwd_npa_outstanding_amt_revise + ctd_npa_outstanding_amt_revise) - cwd_npa_recovered_revise + ctd_npa_recovered_revise netos, \n")
             .append("caa_applied_amount,cwd_wc_clm_elig_amt + ctd_tc_clm_elig_amt,cwd_wc_first_inst_pay_amt + ctd_tc_first_inst_pay_amt,cwd_wc_asf_deductable + cwd_wc_asf_deductable, \n")
             .append("cwd_wc_first_inst_pay_amt - cwd_wc_asf_deductable - nvl(CWD_SWBH_CESS_TAX_DED,0) - nvl(CWD_SERV_TAX_DED,0)-NVL(CWD_KK_CESS_TAX_DED,0) netpaid,NVL(CLM_PAYMENT_UTR_NO,''),NVL(CLM_PAYMENT_AC_NO,'-'),NVL(CLM_PYMT_OUTWARD_NO,'-'),CLM_PYMT_OUTWARD_DT,clm_approved_dt,clm_payment_dt,decode(ctd_sf_refund_flag,'Y',ctd_tc_sf_refundable + cwd_wc_sf_refundable,0),ctd_sf_refund_flag,nvl(CWD_SWBH_CESS_TAX_DED,0),nvl(CWD_SERV_TAX_DED,0),NVL(CWD_KK_CESS_TAX_DED,0)  \n")
             .append("FROM APPLICATION_DETAIL A,SSI_DETAIL s,CLAIM_DETAIL c,CLAIM_WC_DETAIL cwd,MEMBER_INFO m,CLAIM_APPLICATION_AMOUNT caa,CLAIM_TC_DETAIL ctd \n")
             .append("WHERE A.ssi_reference_number = s.ssi_Reference_number \n")
             .append("AND s.bid = c.bid \n")
             .append("AND C.CLM_REF_NO = cwd.clm_ref_no \n")
             .append("AND C.CLM_REF_NO = ctd.clm_ref_no \n")
             .append("AND A.mem_bnk_id||A.mem_zne_id||A.mem_brn_id = m.mem_bnk_id||m.mem_zne_id||m.mem_brn_id \n")
             .append("AND A.cgpan = caa.cgpan \n")
             .append("AND A.cgpan = ctd.cgpan \n")
             .append("AND A.cgpan = cwd.cgpan \n")
             .append("AND app_loan_type IN ('CC') \n")
             .append("AND clm_status IN ('AP') \n").append("AND TRUNC(")
             .append(clm_date_for_report).append(") <= to_date('")
             .append(todate).append("','DD/MM/YYYY')   \n")
             .append("ORDER BY 1,2,5 \n").append("\n").toString();*/
		 ////System.out.println("query1"+query);
		} else if (memberID.equals(null) || memberID.equals("")) {
			/*query = (new StringBuilder())
					.append(" SELECT mem_bank_name,mem_zone_name,c.mem_bnk_id||c.mem_zne_id||c.mem_brn_id memberid, \nA.cgpan,ssi_unit_name, \nDECODE(NVL(app_reapprove_amount,0),0,app_approved_amount,app_reapprove_amount) appamt, \nctd_npa_outstanding_amt_revise nps_os,ctd_npa_recovered_revise reco, \nLEAST(DECODE(NVL(app_reapprove_amount,0),0,app_approved_amount,app_reapprove_amount),ctd_npa_outstanding_amt_revise) - ctd_npa_recovered_revise netos, \ncaa_applied_amount,ctd_tc_clm_elig_amt,ctd_tc_first_inst_pay_amt,ctd_tc_asf_deductable, \nctd_tc_first_inst_pay_amt - ctd_tc_asf_deductable netpaid,NVL(CLM_PAYMENT_UTR_NO,'-'),NVL(CLM_PAYMENT_AC_NO,'-'),NVL(CLM_PYMT_OUTWARD_NO,'-'),CLM_PYMT_OUTWARD_DT,clm_approved_dt,clm_payment_dt,decode(ctd_sf_refund_flag,'Y',ctd_tc_sf_refundable,0),ctd_sf_refund_flag \nFROM APPLICATION_DETAIL A,SSI_DETAIL s,CLAIM_DETAIL c,CLAIM_TC_DETAIL ctd,MEMBER_INFO m,CLAIM_APPLICATION_AMOUNT caa \nWHERE A.ssi_reference_number = s.ssi_Reference_number \nAND s.bid = c.bid \nAND C.CLM_REF_NO = ctd.clm_ref_no \nAND A.mem_bnk_id||A.mem_zne_id||A.mem_brn_id = m.mem_bnk_id||m.mem_zne_id||m.mem_brn_id \nAND A.cgpan = caa.cgpan  \nAND   A.cgpan=ctd.cgpan  \nAND  app_loan_type IN ('TC') \nAND clm_status IN ('AP') \nAND TRUNC(")
					.append(clm_date_for_report)
					.append(") >=  to_date('")
					.append(fromdate)
					.append("','DD/MM/YYYY')    \n")
					.append("AND TRUNC(")
					.append(clm_date_for_report)
					.append(") <= to_date('")
					.append(todate)
					.append("','DD/MM/YYYY')     \n")
					.append("UNION ALL \n")
					.append("SELECT mem_bank_name,mem_zone_name,c.mem_bnk_id||c.mem_zne_id||c.mem_brn_id memberid, \n")
					.append("A.cgpan,ssi_unit_name, \n")
					.append("DECODE(NVL(app_reapprove_amount,0),0,app_approved_amount,app_reapprove_amount) appamt, \n")
					.append("cwd_npa_outstanding_amt_revise nps_os,cwd_npa_recovered_revise reco, \n")
					.append("LEAST(DECODE(NVL(app_reapprove_amount,0),0,app_approved_amount,app_reapprove_amount),cwd_npa_outstanding_amt_revise) - cwd_npa_recovered_revise netos, \n")
					.append("caa_applied_amount,cwd_wc_clm_elig_amt,cwd_wc_first_inst_pay_amt,cwd_wc_asf_deductable, \n")
					.append("cwd_wc_first_inst_pay_amt - cwd_wc_asf_deductable netpaid,NVL(CLM_PAYMENT_UTR_NO,'-'),NVL(CLM_PAYMENT_AC_NO,'-'),NVL(CLM_PYMT_OUTWARD_NO,'-'),CLM_PYMT_OUTWARD_DT,clm_approved_dt,clm_payment_dt,decode(cwd_sf_refund_flag,'Y',cwd_wc_sf_refundable,0),cwd_sf_refund_flag \n")
					.append("FROM APPLICATION_DETAIL A,SSI_DETAIL s,CLAIM_DETAIL c,CLAIM_WC_DETAIL cwd,MEMBER_INFO m,CLAIM_APPLICATION_AMOUNT caa \n")
					.append("WHERE A.ssi_reference_number = s.ssi_Reference_number \n")
					.append("AND s.bid = c.bid \n")
					.append("AND C.CLM_REF_NO = cwd.clm_ref_no \n")
					.append("AND A.mem_bnk_id||A.mem_zne_id||A.mem_brn_id = m.mem_bnk_id||m.mem_zne_id||m.mem_brn_id \n")
					.append("AND A.cgpan = caa.cgpan \n")
					.append("AND A.cgpan = cwd.cgpan \n")
					.append("AND app_loan_type IN ('WC') \n")
					.append("AND clm_status IN ('AP') \n")
					.append("AND TRUNC(")
					.append(clm_date_for_report)
					.append(") >= to_date('")
					.append(fromdate)
					.append("','DD/MM/YYYY')    \n")
					.append("AND TRUNC(")
					.append(clm_date_for_report)
					.append(") <= to_date('")
					.append(todate)
					.append("','DD/MM/YYYY')    \n")
					.append("UNION ALL \n")
					.append("SELECT mem_bank_name,mem_zone_name,c.mem_bnk_id||c.mem_zne_id||c.mem_brn_id memberid, \n")
					.append("A.cgpan,ssi_unit_name, \n")
					.append("DECODE(NVL(app_reapprove_amount,0),0,app_approved_amount,app_reapprove_amount) appamt, \n")
					.append("cwd_npa_outstanding_amt_revise + ctd_npa_outstanding_amt_revise nps_os,cwd_npa_recovered_revise + ctd_npa_recovered_revise reco, \n")
					.append("LEAST(DECODE(NVL(app_reapprove_amount,0),0,app_approved_amount,app_reapprove_amount),cwd_npa_outstanding_amt_revise + ctd_npa_outstanding_amt_revise) - cwd_npa_recovered_revise + ctd_npa_recovered_revise netos, \n")
					.append("caa_applied_amount,cwd_wc_clm_elig_amt + ctd_tc_clm_elig_amt,cwd_wc_first_inst_pay_amt + ctd_tc_first_inst_pay_amt,cwd_wc_asf_deductable + cwd_wc_asf_deductable, \n")
					.append("cwd_wc_first_inst_pay_amt - cwd_wc_asf_deductable + cwd_wc_first_inst_pay_amt - cwd_wc_asf_deductable netpaid,NVL(CLM_PAYMENT_UTR_NO,'-'),NVL(CLM_PAYMENT_AC_NO,'-'),NVL(CLM_PYMT_OUTWARD_NO,'-'),CLM_PYMT_OUTWARD_DT,clm_approved_dt,clm_payment_dt,decode(ctd_sf_refund_flag,'Y',ctd_tc_sf_refundable + cwd_wc_sf_refundable,0),ctd_sf_refund_flag \n")
					.append("FROM APPLICATION_DETAIL A,SSI_DETAIL s,CLAIM_DETAIL c,CLAIM_WC_DETAIL cwd,MEMBER_INFO m,CLAIM_APPLICATION_AMOUNT caa,CLAIM_TC_DETAIL ctd \n")
					.append("WHERE A.ssi_reference_number = s.ssi_Reference_number \n")
					.append("AND s.bid = c.bid \n")
					.append("AND C.CLM_REF_NO = cwd.clm_ref_no \n")
					.append("AND C.CLM_REF_NO = ctd.clm_ref_no \n")
					.append("AND A.mem_bnk_id||A.mem_zne_id||A.mem_brn_id = m.mem_bnk_id||m.mem_zne_id||m.mem_brn_id \n")
					.append("AND A.cgpan = caa.cgpan \n")
					.append("AND A.cgpan = ctd.cgpan \n")
					.append("AND A.cgpan = cwd.cgpan \n")
					.append("AND app_loan_type IN ('CC') \n")
					.append("AND clm_status IN ('AP') \n").append("AND TRUNC(")
					.append(clm_date_for_report).append(") >= to_date('")
					.append(fromdate).append("','DD/MM/YYYY')    \n")
					.append("AND TRUNC(").append(clm_date_for_report)
					.append(") <= to_date('").append(todate)
					.append("','DD/MM/YYYY')   \n").append("ORDER BY 1,2,5 \n")
					.append("\n").toString();*/
			
// New modified for swach bharat start from here
		
			
			 query =  "SELECT mem_bank_name, "
				+ "       mem_zone_name, "
				+ "       c.mem_bnk_id || c.mem_zne_id || c.mem_brn_id memberid, "
				+ "       A.cgpan, "
				+ "       ssi_unit_name, "
				+ "       DECODE (NVL (app_reapprove_amount, 0), "
				+ "               0, app_approved_amount, "
				+ "               app_reapprove_amount) "
				+ "          appamt, "
				+ "       ctd_npa_outstanding_amt_revise nps_os, "
				+ "       ctd_npa_recovered_revise reco, "
				+ "         LEAST ( "
				+ "            DECODE (NVL (app_reapprove_amount, 0), "
				+ "                    0, app_approved_amount, "
				+ "                    app_reapprove_amount), "
				+ "            ctd_npa_outstanding_amt_revise) "
				+ "       - ctd_npa_recovered_revise "
				+ "          netos, "
				+ "       caa_applied_amount, "
				+ "       ctd_tc_clm_elig_amt, "
				+ "       ctd_tc_first_inst_pay_amt, "
				+ "       ctd_tc_asf_deductable, "
				+ "       DECODE ( "
				+ "          NVL (ctd_sf_refund_flag, 'N'), "
				+ "          'N', (  ctd_tc_first_inst_pay_amt "
				+ "                - NVL (ctd_tc_asf_deductable, 0) "
				+ "                - NVL (CTD_SWBH_CESS_TAX_DED, 0) "
				+ "                - NVL (CTD_SERV_TAX_DED, 0) "
				+ "                - NVL (CTD_KK_CESS_TAX_DED, 0)), "
				+ "          (  ctd_tc_first_inst_pay_amt "
				+ "           + NVL (ctd_tc_sf_refundable, 0) "
				+ "           + NVL (CTD_SWBH_CESS_TAX_DED, 0) "
				+ "           + NVL (CTD_SERV_TAX_DED, 0) "
				+ "           + NVL (CTD_KK_CESS_TAX_DED, 0))) "
				+ "          netpaid, "
				+ "       NVL (CLM_PAYMENT_UTR_NO, '-'), "
				+ "       NVL (CLM_PAYMENT_AC_NO, '-'), "
				+ "       NVL (CLM_PYMT_OUTWARD_NO, '-'), "
				+ "       CLM_PYMT_OUTWARD_DT, "
				+ "       clm_approved_dt, "
				+ "       clm_payment_dt, "
				+ "       DECODE (ctd_sf_refund_flag, 'Y', ctd_tc_sf_refundable, 0), "
				+ "       ctd_sf_refund_flag, "
				+ "       DECODE (ctd_sf_refund_flag, 'Y',NVL (CTD_SWBH_CESS_TAX_DED, 0), 0) SWBH_CESS_REFUND, "
				+ "       DECODE (ctd_sf_refund_flag, 'Y',NVL (CTD_SERV_TAX_DED, 0), 0) SERV_TAX_REFUND, "
				+ "       DECODE (ctd_sf_refund_flag, 'Y',NVL (CTD_KK_CESS_TAX_DED, 0), 0) KK_CESS_REFUND, "
				+ "       DECODE (ctd_sf_refund_flag, 'N',NVL (CTD_SWBH_CESS_TAX_DED, 0), 0) SWBH_CESS_DED, "
				+ "       DECODE (ctd_sf_refund_flag, 'N',NVL (CTD_SERV_TAX_DED, 0), 0) SERV_TAX_DED, "
				+ "       DECODE (ctd_sf_refund_flag, 'N',NVL (CTD_KK_CESS_TAX_DED, 0), 0) KK_CESS_DED "
				+ "  FROM APPLICATION_DETAIL A, "
				+ "       SSI_DETAIL s, "
				+ "       CLAIM_DETAIL c, "
				+ "       CLAIM_TC_DETAIL ctd, "
				+ "       MEMBER_INFO m, "
				+ "       CLAIM_APPLICATION_AMOUNT caa "
				+ " WHERE     A.ssi_reference_number = s.ssi_Reference_number "
				+ "       AND s.bid = c.bid "
				+ "       AND C.CLM_REF_NO = ctd.clm_ref_no "
				+ "       AND A.mem_bnk_id || A.mem_zne_id || A.mem_brn_id = "
				+ "              m.mem_bnk_id || m.mem_zne_id || m.mem_brn_id "
				+ "       AND A.cgpan = caa.cgpan "
				+ "       AND A.cgpan = ctd.cgpan "
				+ "       AND app_loan_type IN ('TC') "
				+ "       AND clm_status IN ('AP') "
				+ "   and trunc("+clm_date_for_report+")  >=  to_date('"+fromdate+"','DD/MM/YYYY')"
				+ "   and trunc("+clm_date_for_report+")  <=  to_date('"+todate+"','DD/MM/YYYY')"				
				+ "UNION "
				+ "SELECT mem_bank_name, "
				+ "       mem_zone_name, "
				+ "       c.mem_bnk_id || c.mem_zne_id || c.mem_brn_id memberid, "
				+ "       A.cgpan, "
				+ "       ssi_unit_name, "
				+ "       DECODE (NVL (app_reapprove_amount, 0), "
				+ "               0, app_approved_amount, "
				+ "               app_reapprove_amount) "
				+ "          appamt, "
				+ "       cwd_npa_outstanding_amt_revise nps_os, "
				+ "       cwd_npa_recovered_revise reco, "
				+ "         LEAST ( "
				+ "            DECODE (NVL (app_reapprove_amount, 0), "
				+ "                    0, app_approved_amount, "
				+ "                    app_reapprove_amount), "
				+ "            cwd_npa_outstanding_amt_revise) "
				+ "       - cwd_npa_recovered_revise "
				+ "          netos, "
				+ "       caa_applied_amount, "
				+ "       cwd_wc_clm_elig_amt, "
				+ "       cwd_wc_first_inst_pay_amt, "
				+ "       cwd_wc_asf_deductable, "
				+ "       DECODE ( "
				+ "          NVL (cwd_sf_refund_flag, 'N'), "
				+ "          'N', (  cwd_wc_first_inst_pay_amt "
				+ "                - NVL (cwd_wc_asf_deductable, 0) "
				+ "                - NVL (CWD_SWBH_CESS_TAX_DED, 0) "
				+ "                - NVL (CWD_SERV_TAX_DED, 0) "
				+ "                - NVL (CWD_KK_CESS_TAX_DED, 0)), "
				+ "          (  cwd_wc_first_inst_pay_amt "
				+ "           + NVL (cwd_wc_sf_refundable, 0) "
				+ "           + NVL (CWD_SWBH_CESS_TAX_DED, 0) "
				+ "           + NVL (CWD_SERV_TAX_DED, 0) "
				+ "           + NVL (CWD_KK_CESS_TAX_DED, 0))) "
				+ "          netpaid, "
				+ "       NVL (CLM_PAYMENT_UTR_NO, '-'), "
				+ "       NVL (CLM_PAYMENT_AC_NO, '-'), "
				+ "       NVL (CLM_PYMT_OUTWARD_NO, '-'), "
				+ "       CLM_PYMT_OUTWARD_DT, "
				+ "       clm_approved_dt, "
				+ "       clm_payment_dt, "
				+ "       DECODE (cwd_sf_refund_flag, 'Y', cwd_wc_sf_refundable, 0), "
				+ "       cwd_sf_refund_flag, "
				+ "       DECODE (cwd_sf_refund_flag, 'Y',NVL (CWD_SWBH_CESS_TAX_DED, 0), 0) SWBH_CESS_REFUND, "
				+ "       DECODE (cwd_sf_refund_flag, 'Y',NVL (CWD_SERV_TAX_DED, 0), 0) SERV_TAX_REFUND, "
				+ "       DECODE (cwd_sf_refund_flag, 'Y',NVL (CWD_KK_CESS_TAX_DED, 0), 0) KK_CESS_REFUND, "
				+ "        DECODE (cwd_sf_refund_flag, 'N',NVL (CWD_SWBH_CESS_TAX_DED, 0), 0) SWBH_CESS_DED, "
				+ "       DECODE (cwd_sf_refund_flag, 'N',NVL (CWD_SERV_TAX_DED, 0), 0) SERV_TAX_DED, "
				+ "       DECODE (cwd_sf_refund_flag, 'N',NVL (CWD_KK_CESS_TAX_DED, 0), 0) KK_CESS_DED "
				+ "  FROM APPLICATION_DETAIL A, "
				+ "       SSI_DETAIL s, "
				+ "       CLAIM_DETAIL c, "
				+ "       CLAIM_WC_DETAIL cwd, "
				+ "       MEMBER_INFO m, "
				+ "       CLAIM_APPLICATION_AMOUNT caa "
				+ " WHERE     A.ssi_reference_number = s.ssi_Reference_number "
				+ "       AND s.bid = c.bid "
				+ "       AND C.CLM_REF_NO = cwd.clm_ref_no "
				+ "       AND A.mem_bnk_id || A.mem_zne_id || A.mem_brn_id = "
				+ "              m.mem_bnk_id || m.mem_zne_id || m.mem_brn_id "
				+ "       AND A.cgpan = caa.cgpan "
				+ "       AND A.cgpan = cwd.cgpan "
				+ "       AND app_loan_type IN ('WC') "
				+ "       AND clm_status IN ('AP') "
				+ "   and trunc("+clm_date_for_report+")  >=  to_date('"+fromdate+"','DD/MM/YYYY')"
				+ "   and trunc("+clm_date_for_report+")  <=  to_date('"+todate+"','DD/MM/YYYY')"		
				+ "UNION "
				+ "SELECT mem_bank_name, "
				+ "       mem_zone_name, "
				+ "       c.mem_bnk_id || c.mem_zne_id || c.mem_brn_id memberid, "
				+ "       A.cgpan, "
				+ "       ssi_unit_name, "
				+ "       DECODE (NVL (app_reapprove_amount, 0), "
				+ "               0, app_approved_amount, "
				+ "               app_reapprove_amount) "
				+ "          appamt, "
				+ "       cwd_npa_outstanding_amt_revise + ctd_npa_outstanding_amt_revise nps_os, "
				+ "       cwd_npa_recovered_revise + ctd_npa_recovered_revise reco, "
				+ "         LEAST ( "
				+ "            DECODE (NVL (app_reapprove_amount, 0), "
				+ "                    0, app_approved_amount, "
				+ "                    app_reapprove_amount), "
				+ "            cwd_npa_outstanding_amt_revise + ctd_npa_outstanding_amt_revise) "
				+ "       - cwd_npa_recovered_revise "
				+ "       + ctd_npa_recovered_revise "
				+ "          netos, "
				+ "       caa_applied_amount, "
				+ "       cwd_wc_clm_elig_amt + ctd_tc_clm_elig_amt, "
				+ "       cwd_wc_first_inst_pay_amt + ctd_tc_first_inst_pay_amt, "
				+ "       cwd_wc_asf_deductable + cwd_wc_asf_deductable, "
				+ "       DECODE ( "
				+ "          NVL (ctd_sf_refund_flag, 'N'), "
				+ "          'N', (  ctd_tc_first_inst_pay_amt "
				+ "                - ctd_tc_asf_deductable "
				+ "                - NVL (CTD_SWBH_CESS_TAX_DED, 0) "
				+ "                - NVL (CTD_SERV_TAX_DED, 0) "
				+ "                - NVL (CTD_KK_CESS_TAX_DED, 0)), "
				+ "          (  ctd_tc_first_inst_pay_amt "
				+ "           + ctd_tc_asf_deductable "
				+ "           + NVL (CTD_SWBH_CESS_TAX_DED, 0) "
				+ "           + NVL (CTD_SERV_TAX_DED, 0) "
				+ "           + NVL (CTD_KK_CESS_TAX_DED, 0))) "
				+ "          netpaid, "
				+ "       NVL (CLM_PAYMENT_UTR_NO, '-'), "
				+ "       NVL (CLM_PAYMENT_AC_NO, '-'), "
				+ "       NVL (CLM_PYMT_OUTWARD_NO, '-'), "
				+ "       CLM_PYMT_OUTWARD_DT, "
				+ "       clm_approved_dt, "
				+ "       clm_payment_dt, "
				+ "       DECODE (ctd_sf_refund_flag, "
				+ "               'Y', ctd_tc_sf_refundable + cwd_wc_sf_refundable, "
				+ "               0), "
				+ "       ctd_sf_refund_flag, "
				+ "       DECODE (ctd_sf_refund_flag, 'Y',NVL (CTD_SWBH_CESS_TAX_DED, 0), 0) SWBH_CESS_REFUND, "
				+ "       DECODE (ctd_sf_refund_flag, 'Y',NVL (CTD_SERV_TAX_DED, 0), 0) SERV_TAX_REFUND, "
				+ "       DECODE (ctd_sf_refund_flag, 'Y',NVL (CTD_KK_CESS_TAX_DED, 0), 0) KK_CESS_REFUND, "
				+ "       DECODE (ctd_sf_refund_flag, 'N',NVL (CTD_SWBH_CESS_TAX_DED, 0), 0) SWBH_CESS_DED, "
				+ "       DECODE (ctd_sf_refund_flag, 'N',NVL (CTD_SERV_TAX_DED, 0), 0) SERV_TAX_DED, "
				+ "       DECODE (ctd_sf_refund_flag, 'N',NVL (CTD_KK_CESS_TAX_DED, 0), 0) KK_CESS_DED "
				+ "  FROM APPLICATION_DETAIL A, "
				+ "       SSI_DETAIL s, "
				+ "       CLAIM_DETAIL c, "
				+ "       CLAIM_WC_DETAIL cwd, "
				+ "       MEMBER_INFO m, "
				+ "       CLAIM_APPLICATION_AMOUNT caa, "
				+ "       CLAIM_TC_DETAIL ctd "
				+ " WHERE     A.ssi_reference_number = s.ssi_Reference_number "
				+ "       AND s.bid = c.bid "
				+ "       AND C.CLM_REF_NO = cwd.clm_ref_no "
				+ "       AND C.CLM_REF_NO = ctd.clm_ref_no "
				+ "       AND A.mem_bnk_id || A.mem_zne_id || A.mem_brn_id = "
				+ "              m.mem_bnk_id || m.mem_zne_id || m.mem_brn_id "
				+ "       AND A.cgpan = caa.cgpan "
				+ "       AND A.cgpan = ctd.cgpan "
				+ "       AND A.cgpan = cwd.cgpan "
				+ "       AND app_loan_type IN ('CC') "
				+ "       AND clm_status IN ('AP') "
				+ "   and trunc("+clm_date_for_report+")  >=  to_date('"+fromdate+"','DD/MM/YYYY')"
				+ "   and trunc("+clm_date_for_report+")  <=  to_date('"+todate+"','DD/MM/YYYY')"		
				+ "ORDER BY 1, 2, 5";
				////System.out.println("query2"+query);
		 /*koteswar   query ="SELECT mem_bank_name, "
				 + "       mem_zone_name, "
				 + "       c.mem_bnk_id || c.mem_zne_id || c.mem_brn_id memberid, "
				 + "       A.cgpan, "
				 + "       ssi_unit_name, "
				 + "       DECODE (NVL (app_reapprove_amount, 0), "
				 + "               0, app_approved_amount, "
				 + "               app_reapprove_amount) "
				 + "          appamt, "
				 + "       ctd_npa_outstanding_amt_revise nps_os, "
				 + "       ctd_npa_recovered_revise reco, "
				 + "         LEAST ( "
				 + "            DECODE (NVL (app_reapprove_amount, 0), "
				 + "                    0, app_approved_amount, "
				 + "                    app_reapprove_amount), "
				 + "            ctd_npa_outstanding_amt_revise) "
				 + "       - ctd_npa_recovered_revise "
				 + "          netos, "
				 + "       caa_applied_amount, "
				 + "       ctd_tc_clm_elig_amt, "
				 + "       ctd_tc_first_inst_pay_amt, "
				 + "       ctd_tc_asf_deductable, "
				 + "       DECODE ( "
				 + "          NVL (ctd_sf_refund_flag, 'N'), "
				 + "          'N', (  ctd_tc_first_inst_pay_amt "
				 + "                - NVL (ctd_tc_asf_deductable, 0) "
				 + "                - NVL (CTD_SWBH_CESS_TAX_DED, 0) "
				 + "                - NVL (CTD_SERV_TAX_DED, 0) "
				 + "                - NVL (CTD_KK_CESS_TAX_DED, 0)), "
				 + "          (  ctd_tc_first_inst_pay_amt "
				 + "           + NVL (ctd_tc_sf_refundable, 0) "
				 + "           + NVL (CTD_SWBH_CESS_TAX_DED, 0) "
				 + "           + NVL (CTD_SERV_TAX_DED, 0) "
				 + "           + NVL (CTD_KK_CESS_TAX_DED, 0))) "
				 + "          netpaid, "
				 + "       NVL (CLM_PAYMENT_UTR_NO, '-'), "
				 + "       NVL (CLM_PAYMENT_AC_NO, '-'), "
				 + "       NVL (CLM_PYMT_OUTWARD_NO, '-'), "
				 + "       CLM_PYMT_OUTWARD_DT, "
				 + "       clm_approved_dt, "
				 + "       clm_payment_dt, "
				 + "       DECODE (ctd_sf_refund_flag, 'Y', ctd_tc_sf_refundable, 0), "
				 + "       ctd_sf_refund_flag, "
				 + "       NVL (CTD_SWBH_CESS_TAX_DED, 0), "
				 + "       NVL (CTD_SERV_TAX_DED, 0), "
				 + "       NVL (CTD_KK_CESS_TAX_DED, 0) "
				 + "  FROM APPLICATION_DETAIL A, "
				 + "       SSI_DETAIL s, "
				 + "       CLAIM_DETAIL c, "
				 + "       CLAIM_TC_DETAIL ctd, "
				 + "       MEMBER_INFO m, "
				 + "       CLAIM_APPLICATION_AMOUNT caa "
				 + " WHERE     A.ssi_reference_number = s.ssi_Reference_number "
				 + "       AND s.bid = c.bid "
				 + "       AND C.CLM_REF_NO = ctd.clm_ref_no "
				 + "       AND A.mem_bnk_id || A.mem_zne_id || A.mem_brn_id = "
				 + "              m.mem_bnk_id || m.mem_zne_id || m.mem_brn_id "
				 + "       AND A.cgpan = caa.cgpan "
				 + "       AND A.cgpan = ctd.cgpan "
				 + "       AND app_loan_type IN ('TC') "
				 + "       AND clm_status IN ('AP') "
				 + "       AND TRUNC (clm_approved_dt) >= TO_DATE ('"+fromdate+"', 'DD/MM/YYYY') "
				 + "       AND TRUNC (clm_approved_dt) <= TO_DATE ('"+todate +"', 'DD/MM/YYYY') "
				 
				 + "UNION "
				 + "SELECT mem_bank_name, "
				 + "       mem_zone_name, "
				 + "       c.mem_bnk_id || c.mem_zne_id || c.mem_brn_id memberid, "
				 + "       A.cgpan, "
				 + "       ssi_unit_name, "
				 + "       DECODE (NVL (app_reapprove_amount, 0), "
				 + "               0, app_approved_amount, "
				 + "               app_reapprove_amount) "
				 + "          appamt, "
				 + "       cwd_npa_outstanding_amt_revise nps_os, "
				 + "       cwd_npa_recovered_revise reco, "
				 + "         LEAST ( "
				 + "            DECODE (NVL (app_reapprove_amount, 0), "
				 + "                    0, app_approved_amount, "
				 + "                    app_reapprove_amount), "
				 + "            cwd_npa_outstanding_amt_revise) "
				 + "       - cwd_npa_recovered_revise "
				 + "          netos, "
				 + "       caa_applied_amount, "
				 + "       cwd_wc_clm_elig_amt, "
				 + "       cwd_wc_first_inst_pay_amt, "
				 + "       cwd_wc_asf_deductable, "
				 + "       DECODE ( "
				 + "          NVL (cwd_sf_refund_flag, 'N'), "
				 + "          'N', (  cwd_wc_first_inst_pay_amt "
				 + "                - NVL (cwd_wc_asf_deductable, 0) "
				 + "                - NVL (CWD_SWBH_CESS_TAX_DED, 0) "
				 + "                - NVL (CWD_SERV_TAX_DED, 0) "
				 + "                - NVL (CWD_KK_CESS_TAX_DED, 0)), "
				 + "          (  cwd_wc_first_inst_pay_amt "
				 + "           + NVL (cwd_wc_sf_refundable, 0) "
				 + "           + NVL (CWD_SWBH_CESS_TAX_DED, 0) "
				 + "           + NVL (CWD_SERV_TAX_DED, 0) "
				 + "           + NVL (CWD_KK_CESS_TAX_DED, 0))) "
				 + "          netpaid, "
				 + "       NVL (CLM_PAYMENT_UTR_NO, '-'), "
				 + "       NVL (CLM_PAYMENT_AC_NO, '-'), "
				 + "       NVL (CLM_PYMT_OUTWARD_NO, '-'), "
				 + "       CLM_PYMT_OUTWARD_DT, "
				 + "       clm_approved_dt, "
				 + "       clm_payment_dt, "
				 + "       DECODE (cwd_sf_refund_flag, 'Y', cwd_wc_sf_refundable, 0), "
				 + "       cwd_sf_refund_flag, "
				 + "       NVL (CWD_SWBH_CESS_TAX_DED, 0), "
				 + "       NVL (CWD_SERV_TAX_DED, 0), "
				 + "       NVL (CWD_KK_CESS_TAX_DED, 0) "
				 + "  FROM APPLICATION_DETAIL A, "
				 + "       SSI_DETAIL s, "
				 + "       CLAIM_DETAIL c, "
				 + "       CLAIM_WC_DETAIL cwd, "
				 + "       MEMBER_INFO m, "
				 + "       CLAIM_APPLICATION_AMOUNT caa "
				 + " WHERE     A.ssi_reference_number = s.ssi_Reference_number "
				 + "       AND s.bid = c.bid "
				 + "       AND C.CLM_REF_NO = cwd.clm_ref_no "
				 + "       AND A.mem_bnk_id || A.mem_zne_id || A.mem_brn_id = "
				 + "              m.mem_bnk_id || m.mem_zne_id || m.mem_brn_id "
				 + "       AND A.cgpan = caa.cgpan "
				 + "       AND A.cgpan = cwd.cgpan "
				 + "       AND app_loan_type IN ('WC') "
				 + "       AND clm_status IN ('AP') "
				 + "       AND TRUNC (clm_approved_dt) >= TO_DATE ('"+fromdate+"', 'DD/MM/YYYY') "
				 + "       AND TRUNC (clm_approved_dt) <= TO_DATE ('"+todate+"', 'DD/MM/YYYY') "
				 
				 + "UNION "
				 + "SELECT mem_bank_name, "
				 + "       mem_zone_name, "
				 + "       c.mem_bnk_id || c.mem_zne_id || c.mem_brn_id memberid, "
				 + "       A.cgpan, "
				 + "       ssi_unit_name, "
				 + "       DECODE (NVL (app_reapprove_amount, 0), "
				 + "               0, app_approved_amount, "
				 + "               app_reapprove_amount) "
				 + "          appamt, "
				 + "       cwd_npa_outstanding_amt_revise + ctd_npa_outstanding_amt_revise nps_os, "
				 + "       cwd_npa_recovered_revise + ctd_npa_recovered_revise reco, "
				 + "         LEAST ( "
				 + "            DECODE (NVL (app_reapprove_amount, 0), "
				 + "                    0, app_approved_amount, "
				 + "                    app_reapprove_amount), "
				 + "            cwd_npa_outstanding_amt_revise + ctd_npa_outstanding_amt_revise) "
				 + "       - cwd_npa_recovered_revise "
				 + "       + ctd_npa_recovered_revise "
				 + "          netos, "
				 + "       caa_applied_amount, "
				 + "       cwd_wc_clm_elig_amt + ctd_tc_clm_elig_amt, "
				 + "       cwd_wc_first_inst_pay_amt + ctd_tc_first_inst_pay_amt, "
				 + "       cwd_wc_asf_deductable + cwd_wc_asf_deductable, "
				 + "       DECODE ( "
				 + "          NVL (ctd_sf_refund_flag, 'N'), "
				 + "          'N', (  cwd_wc_first_inst_pay_amt "
				 + "                + ctd_tc_first_inst_pay_amt "
				 + "                - cwd_wc_asf_deductable "
				 + "                - ctd_tc_asf_deductable "
				 + "                - NVL (CWD_SWBH_CESS_TAX_DED, 0) "
				 + "                - NVL (CWD_SERV_TAX_DED, 0) "
				 + "                - NVL (CWD_KK_CESS_TAX_DED, 0) "
				 + "                - NVL (CTD_SWBH_CESS_TAX_DED, 0) "
				 + "                - NVL (CTD_SERV_TAX_DED, 0) "
				 + "                - NVL (CTD_KK_CESS_TAX_DED, 0)), "
				 + "          (  cwd_wc_first_inst_pay_amt "
				 + "           + ctd_tc_first_inst_pay_amt "
				 + "           + cwd_wc_asf_deductable "
				 + "           + ctd_tc_asf_deductable "
				 + "           + NVL (CWD_SWBH_CESS_TAX_DED, 0) "
				 + "           + NVL (CWD_SERV_TAX_DED, 0) "
				 + "           + NVL (CWD_KK_CESS_TAX_DED, 0) "
				 + "           + NVL (CTD_SWBH_CESS_TAX_DED, 0) "
				 + "           + NVL (CTD_SERV_TAX_DED, 0) "
				 + "           + NVL (CTD_KK_CESS_TAX_DED, 0))) "
				 + "          netpaid, "
				 + "       NVL (CLM_PAYMENT_UTR_NO, '-'), "
				 + "       NVL (CLM_PAYMENT_AC_NO, '-'), "
				 + "       NVL (CLM_PYMT_OUTWARD_NO, '-'), "
				 + "       CLM_PYMT_OUTWARD_DT, "
				 + "       clm_approved_dt, "
				 + "       clm_payment_dt, "
				 + "       DECODE (ctd_sf_refund_flag, "
				 + "               'Y', ctd_tc_sf_refundable + cwd_wc_sf_refundable, "
				 + "               0), "
				 + "       ctd_sf_refund_flag, "
				 + "       NVL (CTD_SWBH_CESS_TAX_DED, 0), "
				 + "       NVL (CTD_SERV_TAX_DED, 0), "
				 + "       NVL (CTD_KK_CESS_TAX_DED, 0) "
				 + "  FROM APPLICATION_DETAIL A, "
				 + "       SSI_DETAIL s, "
				 + "       CLAIM_DETAIL c, "
				 + "       CLAIM_WC_DETAIL cwd, "
				 + "       MEMBER_INFO m, "
				 + "       CLAIM_APPLICATION_AMOUNT caa, "
				 + "       CLAIM_TC_DETAIL ctd "
				 + " WHERE     A.ssi_reference_number = s.ssi_Reference_number "
				 + "       AND s.bid = c.bid "
				 + "       AND C.CLM_REF_NO = cwd.clm_ref_no "
				 + "       AND C.CLM_REF_NO = ctd.clm_ref_no "
				 + "       AND A.mem_bnk_id || A.mem_zne_id || A.mem_brn_id = "
				 + "              m.mem_bnk_id || m.mem_zne_id || m.mem_brn_id "
				 + "       AND A.cgpan = caa.cgpan "
				 + "       AND A.cgpan = ctd.cgpan "
				 + "       AND A.cgpan = cwd.cgpan "
				 + "       AND app_loan_type IN ('CC') "
				 + "       AND clm_status IN ('AP') "
				 + "       AND TRUNC (clm_approved_dt) >= TO_DATE ('"+fromdate+"', 'DD/MM/YYYY') "
				 + "       AND TRUNC (clm_approved_dt) <= TO_DATE ('"+todate+"', 'DD/MM/YYYY') "
				 + "ORDER BY 1, 2, 5";*/
		    
		   // //System.out.println("query2"+query);
/* (new StringBuilder())
            .append(" SELECT distinct mem_bank_name,mem_zone_name,c.mem_bnk_id||c.mem_zne_id||c.mem_brn_id memberid, \nA.cgpan,ssi_unit_name, \nDECODE(NVL(app_reapprove_amount,0),0,app_approved_amount,app_reapprove_amount) appamt, \nctd_npa_outstanding_amt_revise nps_os,ctd_npa_recovered_revise reco, \nLEAST(DECODE(NVL(app_reapprove_amount,0),0,app_approved_amount,app_reapprove_amount),ctd_npa_outstanding_amt_revise) - ctd_npa_recovered_revise netos, \ncaa_applied_amount,ctd_tc_clm_elig_amt,ctd_tc_first_inst_pay_amt,ctd_tc_asf_deductable, \nctd_tc_first_inst_pay_amt - ctd_tc_asf_deductable - nvl(CTD_SWBH_CESS_TAX_DED,0) - nvl(CTD_SERV_TAX_DED,0)-NVL(CTD_KK_CESS_TAX_DED,0) netpaid,NVL(CLM_PAYMENT_UTR_NO,'-'),NVL(CLM_PAYMENT_AC_NO,'-'),NVL(CLM_PYMT_OUTWARD_NO,'-'),CLM_PYMT_OUTWARD_DT,clm_approved_dt,clm_payment_dt,decode(ctd_sf_refund_flag,'Y',ctd_tc_sf_refundable,0),ctd_sf_refund_flag,nvl(CTD_SWBH_CESS_TAX_DED,0),nvl(CTD_SERV_TAX_DED,0),NVL(CTD_KK_CESS_TAX_DED,0) \nFROM APPLICATION_DETAIL A,SSI_DETAIL s,CLAIM_DETAIL c,CLAIM_TC_DETAIL ctd,MEMBER_INFO m,CLAIM_APPLICATION_AMOUNT caa \nWHERE A.ssi_reference_number = s.ssi_Reference_number \nAND s.bid = c.bid \nAND C.CLM_REF_NO = ctd.clm_ref_no \nAND A.mem_bnk_id||A.mem_zne_id||A.mem_brn_id = m.mem_bnk_id||m.mem_zne_id||m.mem_brn_id \nAND A.cgpan = caa.cgpan  \nAND   A.cgpan=ctd.cgpan  \nAND  app_loan_type IN ('TC') \nAND clm_status IN ('AP') \nAND TRUNC(")
            .append(clm_date_for_report)
            .append(") >=  to_date('")
            .append(fromdate)
            .append("','DD/MM/YYYY')    \n")
            .append("AND TRUNC(")
            .append(clm_date_for_report)
            .append(") <= to_date('")
            .append(todate)
            .append("','DD/MM/YYYY')     \n")
            .append("UNION ALL \n")
            .append("SELECT distinct mem_bank_name,mem_zone_name,c.mem_bnk_id||c.mem_zne_id||c.mem_brn_id memberid, \n")
            .append("A.cgpan,ssi_unit_name, \n")
            .append("DECODE(NVL(app_reapprove_amount,0),0,app_approved_amount,app_reapprove_amount) appamt, \n")
            .append("cwd_npa_outstanding_amt_revise nps_os,cwd_npa_recovered_revise reco, \n")
            .append("LEAST(DECODE(NVL(app_reapprove_amount,0),0,app_approved_amount,app_reapprove_amount),cwd_npa_outstanding_amt_revise) - cwd_npa_recovered_revise netos, \n")
            .append("caa_applied_amount,cwd_wc_clm_elig_amt,cwd_wc_first_inst_pay_amt,cwd_wc_asf_deductable, \n")
            .append("cwd_wc_first_inst_pay_amt - cwd_wc_asf_deductable - nvl(CWD_SWBH_CESS_TAX_DED,0) - nvl(CWD_SERV_TAX_DED,0)-NVL(CWD_KK_CESS_TAX_DED,0) netpaid,NVL(CLM_PAYMENT_UTR_NO,'-'),NVL(CLM_PAYMENT_AC_NO,'-'),NVL(CLM_PYMT_OUTWARD_NO,'-'),CLM_PYMT_OUTWARD_DT,clm_approved_dt,clm_payment_dt,decode(cwd_sf_refund_flag,'Y',cwd_wc_sf_refundable,0),cwd_sf_refund_flag,nvl(CWD_SWBH_CESS_TAX_DED,0),nvl(CWD_SERV_TAX_DED,0),NVL(CWD_KK_CESS_TAX_DED,0) \n")
            .append("FROM APPLICATION_DETAIL A,SSI_DETAIL s,CLAIM_DETAIL c,CLAIM_WC_DETAIL cwd,MEMBER_INFO m,CLAIM_APPLICATION_AMOUNT caa \n")
            .append("WHERE A.ssi_reference_number = s.ssi_Reference_number \n")
            .append("AND s.bid = c.bid \n")
            .append("AND C.CLM_REF_NO = cwd.clm_ref_no \n")
            .append("AND A.mem_bnk_id||A.mem_zne_id||A.mem_brn_id = m.mem_bnk_id||m.mem_zne_id||m.mem_brn_id \n")
            .append("AND A.cgpan = caa.cgpan \n")
            .append("AND A.cgpan = cwd.cgpan \n")
            .append("AND app_loan_type IN ('WC') \n")
            .append("AND clm_status IN ('AP') \n")
            .append("AND TRUNC(")
            .append(clm_date_for_report)
            .append(") >= to_date('")
            .append(fromdate)
            .append("','DD/MM/YYYY')    \n")
            .append("AND TRUNC(")
            .append(clm_date_for_report)
            .append(") <= to_date('")
            .append(todate)
            .append("','DD/MM/YYYY')    \n")
            .append("UNION ALL \n")
            .append("SELECT distinct mem_bank_name,mem_zone_name,c.mem_bnk_id||c.mem_zne_id||c.mem_brn_id memberid, \n")
            .append("A.cgpan,ssi_unit_name, \n")
            .append("DECODE(NVL(app_reapprove_amount,0),0,app_approved_amount,app_reapprove_amount) appamt, \n")
            .append("cwd_npa_outstanding_amt_revise + ctd_npa_outstanding_amt_revise nps_os,cwd_npa_recovered_revise + ctd_npa_recovered_revise reco, \n")
            .append("LEAST(DECODE(NVL(app_reapprove_amount,0),0,app_approved_amount,app_reapprove_amount),cwd_npa_outstanding_amt_revise + ctd_npa_outstanding_amt_revise) - cwd_npa_recovered_revise + ctd_npa_recovered_revise netos, \n")
            .append("caa_applied_amount,cwd_wc_clm_elig_amt + ctd_tc_clm_elig_amt,cwd_wc_first_inst_pay_amt + ctd_tc_first_inst_pay_amt,cwd_wc_asf_deductable + cwd_wc_asf_deductable, \n")
            .append("cwd_wc_first_inst_pay_amt - cwd_wc_asf_deductable - nvl(CWD_SWBH_CESS_TAX_DED,0) - nvl(CWD_SERV_TAX_DED,0)-NVL(CWD_KK_CESS_TAX_DED,0) netpaid,NVL(CLM_PAYMENT_UTR_NO,'-'),NVL(CLM_PAYMENT_AC_NO,'-'),NVL(CLM_PYMT_OUTWARD_NO,'-'),CLM_PYMT_OUTWARD_DT,clm_approved_dt,clm_payment_dt,decode(ctd_sf_refund_flag,'Y',ctd_tc_sf_refundable + cwd_wc_sf_refundable,0),ctd_sf_refund_flag,nvl(CTD_SWBH_CESS_TAX_DED,0),nvl(CTD_SERV_TAX_DED,0),NVL(CTD_KK_CESS_TAX_DED,0) \n")
            .append("FROM APPLICATION_DETAIL A,SSI_DETAIL s,CLAIM_DETAIL c,CLAIM_WC_DETAIL cwd,MEMBER_INFO m,CLAIM_APPLICATION_AMOUNT caa,CLAIM_TC_DETAIL ctd \n")
            .append("WHERE A.ssi_reference_number = s.ssi_Reference_number \n")
            .append("AND s.bid = c.bid \n")
            .append("AND C.CLM_REF_NO = cwd.clm_ref_no \n")
            .append("AND C.CLM_REF_NO = ctd.clm_ref_no \n")
            .append("AND A.mem_bnk_id||A.mem_zne_id||A.mem_brn_id = m.mem_bnk_id||m.mem_zne_id||m.mem_brn_id \n")
            .append("AND A.cgpan = caa.cgpan \n")
            .append("AND A.cgpan = ctd.cgpan \n")
            .append("AND A.cgpan = cwd.cgpan \n")
            .append("AND app_loan_type IN ('CC') \n")
            .append("AND clm_status IN ('AP') \n").append("AND TRUNC(")
            .append(clm_date_for_report).append(") >= to_date('")
            .append(fromdate).append("','DD/MM/YYYY')    \n")
            .append("AND TRUNC(").append(clm_date_for_report)
            .append(") <= to_date('").append(todate)
            .append("','DD/MM/YYYY')   \n").append("ORDER BY 1,2,5 \n")
            .append("\n").toString();*/
			// New modified for swach bharat end here
		 //   //System.out.println("query2"+query);
		} else {
			/*query = (new StringBuilder())
					.append(" SELECT mem_bank_name,mem_zone_name,c.mem_bnk_id||c.mem_zne_id||c.mem_brn_id memberid, \nA.cgpan,ssi_unit_name, \nDECODE(NVL(app_reapprove_amount,0),0,app_approved_amount,app_reapprove_amount) appamt, \nctd_npa_outstanding_amt_revise nps_os,ctd_npa_recovered_revise reco, \nLEAST(DECODE(NVL(app_reapprove_amount,0),0,app_approved_amount,app_reapprove_amount),ctd_npa_outstanding_amt_revise) - ctd_npa_recovered_revise netos, \ncaa_applied_amount,ctd_tc_clm_elig_amt,ctd_tc_first_inst_pay_amt,ctd_tc_asf_deductable, \nctd_tc_first_inst_pay_amt - ctd_tc_asf_deductable netpaid,NVL(CLM_PAYMENT_UTR_NO,'-'),NVL(CLM_PAYMENT_AC_NO,'-'),NVL(CLM_PYMT_OUTWARD_NO,'-'),CLM_PYMT_OUTWARD_DT,clm_approved_dt,clm_payment_dt,decode(ctd_sf_refund_flag,'Y',ctd_tc_sf_refundable,0),ctd_sf_refund_flag \nFROM APPLICATION_DETAIL A,SSI_DETAIL s,CLAIM_DETAIL c,CLAIM_TC_DETAIL ctd,MEMBER_INFO m,CLAIM_APPLICATION_AMOUNT caa \nWHERE A.ssi_reference_number = s.ssi_Reference_number \nAND s.bid = c.bid \nAND C.CLM_REF_NO = ctd.clm_ref_no \nAND A.mem_bnk_id||A.mem_zne_id||A.mem_brn_id = m.mem_bnk_id||m.mem_zne_id||m.mem_brn_id \nAND A.cgpan = caa.cgpan    \nAND  A.cgpan=ctd.cgpan \nAND app_loan_type IN ('TC') \nAND clm_status IN ('AP') \nAND TRUNC(")
					.append(clm_date_for_report)
					.append(") >=  to_date('")
					.append(fromdate)
					.append("','DD/MM/YYYY')    \n")
					.append("AND TRUNC(")
					.append(clm_date_for_report)
					.append(") <= to_date('")
					.append(todate)
					.append("','DD/MM/YYYY')   and c.mem_bnk_id||c.mem_zne_id||c.mem_brn_id='")
					.append(memberID)
					.append("'  \n")
					.append("UNION ALL \n")
					.append("SELECT mem_bank_name,mem_zone_name,c.mem_bnk_id||c.mem_zne_id||c.mem_brn_id memberid, \n")
					.append("A.cgpan,ssi_unit_name, \n")
					.append("DECODE(NVL(app_reapprove_amount,0),0,app_approved_amount,app_reapprove_amount) appamt, \n")
					.append("cwd_npa_outstanding_amt_revise nps_os,cwd_npa_recovered_revise reco, \n")
					.append("LEAST(DECODE(NVL(app_reapprove_amount,0),0,app_approved_amount,app_reapprove_amount),cwd_npa_outstanding_amt_revise) - cwd_npa_recovered_revise netos, \n")
					.append("caa_applied_amount,cwd_wc_clm_elig_amt,cwd_wc_first_inst_pay_amt,cwd_wc_asf_deductable, \n")
					.append("cwd_wc_first_inst_pay_amt - cwd_wc_asf_deductable netpaid,NVL(CLM_PAYMENT_UTR_NO,'-'),NVL(CLM_PAYMENT_AC_NO,'-'),NVL(CLM_PYMT_OUTWARD_NO,'-'),CLM_PYMT_OUTWARD_DT,clm_approved_dt,clm_payment_dt,decode(cwd_sf_refund_flag,'Y',cwd_wc_sf_refundable,0),cwd_sf_refund_flag \n")
					.append("FROM APPLICATION_DETAIL A,SSI_DETAIL s,CLAIM_DETAIL c,CLAIM_WC_DETAIL cwd,MEMBER_INFO m,CLAIM_APPLICATION_AMOUNT caa \n")
					.append("WHERE A.ssi_reference_number = s.ssi_Reference_number \n")
					.append("AND s.bid = c.bid \n")
					.append("AND C.CLM_REF_NO = cwd.clm_ref_no \n")
					.append("AND A.mem_bnk_id||A.mem_zne_id||A.mem_brn_id = m.mem_bnk_id||m.mem_zne_id||m.mem_brn_id \n")
					.append("AND A.cgpan = caa.cgpan \n")
					.append("AND A.cgpan = cwd.cgpan \n")
					.append("AND app_loan_type IN ('WC') \n")
					.append("AND clm_status IN ('AP') \n")
					.append("AND TRUNC(")
					.append(clm_date_for_report)
					.append(") >= to_date('")
					.append(fromdate)
					.append("','DD/MM/YYYY')    \n")
					.append("AND TRUNC(")
					.append(clm_date_for_report)
					.append(") <= to_date('")
					.append(todate)
					.append("','DD/MM/YYYY')  and c.mem_bnk_id||c.mem_zne_id||c.mem_brn_id='")
					.append(memberID)
					.append("'  \n")
					.append("UNION ALL \n")
					.append("SELECT mem_bank_name,mem_zone_name,c.mem_bnk_id||c.mem_zne_id||c.mem_brn_id memberid, \n")
					.append("A.cgpan,ssi_unit_name, \n")
					.append("DECODE(NVL(app_reapprove_amount,0),0,app_approved_amount,app_reapprove_amount) appamt, \n")
					.append("cwd_npa_outstanding_amt_revise + ctd_npa_outstanding_amt_revise nps_os,cwd_npa_recovered_revise + ctd_npa_recovered_revise reco, \n")
					.append("LEAST(DECODE(NVL(app_reapprove_amount,0),0,app_approved_amount,app_reapprove_amount),cwd_npa_outstanding_amt_revise + ctd_npa_outstanding_amt_revise) - cwd_npa_recovered_revise + ctd_npa_recovered_revise netos, \n")
					.append("caa_applied_amount,cwd_wc_clm_elig_amt + ctd_tc_clm_elig_amt,cwd_wc_first_inst_pay_amt + ctd_tc_first_inst_pay_amt,cwd_wc_asf_deductable + cwd_wc_asf_deductable, \n")
					.append("cwd_wc_first_inst_pay_amt - cwd_wc_asf_deductable + cwd_wc_first_inst_pay_amt - cwd_wc_asf_deductable netpaid,NVL(CLM_PAYMENT_UTR_NO,'-'),NVL(CLM_PAYMENT_AC_NO,'-'),NVL(CLM_PYMT_OUTWARD_NO,'-'),CLM_PYMT_OUTWARD_DT,clm_approved_dt,clm_payment_dt,decode(ctd_sf_refund_flag,'Y',ctd_tc_sf_refundable + cwd_wc_sf_refundable,0),ctd_sf_refund_flag \n")
					.append("FROM APPLICATION_DETAIL A,SSI_DETAIL s,CLAIM_DETAIL c,CLAIM_WC_DETAIL cwd,MEMBER_INFO m,CLAIM_APPLICATION_AMOUNT caa,CLAIM_TC_DETAIL ctd \n")
					.append("WHERE A.ssi_reference_number = s.ssi_Reference_number \n")
					.append("AND s.bid = c.bid \n")
					.append("AND C.CLM_REF_NO = cwd.clm_ref_no \n")
					.append("AND C.CLM_REF_NO = ctd.clm_ref_no \n")
					.append("AND A.mem_bnk_id||A.mem_zne_id||A.mem_brn_id = m.mem_bnk_id||m.mem_zne_id||m.mem_brn_id \n")
					.append("AND A.cgpan = caa.cgpan \n")
					.append("AND A.cgpan = ctd.cgpan \n")
					.append("AND A.cgpan = cwd.cgpan \n")
					.append("AND app_loan_type IN ('CC') \n")
					.append("AND clm_status IN ('AP') \n")
					.append("AND TRUNC(")
					.append(clm_date_for_report)
					.append(") >= to_date('")
					.append(fromdate)
					.append("','DD/MM/YYYY')    \n")
					.append("AND TRUNC(")
					.append(clm_date_for_report)
					.append(") <= to_date('")
					.append(todate)
					.append("','DD/MM/YYYY')  and c.mem_bnk_id||c.mem_zne_id||c.mem_brn_id='")
					.append(memberID).append("'  \n")
					.append("ORDER BY 1,2,5 \n").append("\n").toString();*/
			
			 query = "SELECT mem_bank_name, "
				+ "       mem_zone_name, "
				+ "       c.mem_bnk_id || c.mem_zne_id || c.mem_brn_id memberid, "
				+ "       A.cgpan, "
				+ "       ssi_unit_name, "
				+ "       DECODE (NVL (app_reapprove_amount, 0), "
				+ "               0, app_approved_amount, "
				+ "               app_reapprove_amount) "
				+ "          appamt, "
				+ "       ctd_npa_outstanding_amt_revise nps_os, "
				+ "       ctd_npa_recovered_revise reco, "
				+ "         LEAST ( "
				+ "            DECODE (NVL (app_reapprove_amount, 0), "
				+ "                    0, app_approved_amount, "
				+ "                    app_reapprove_amount), "
				+ "            ctd_npa_outstanding_amt_revise) "
				+ "       - ctd_npa_recovered_revise "
				+ "          netos, "
				+ "       caa_applied_amount, "
				+ "       ctd_tc_clm_elig_amt, "
				+ "       ctd_tc_first_inst_pay_amt, "
				+ "       ctd_tc_asf_deductable, "
				+ "       DECODE ( "
				+ "          NVL (ctd_sf_refund_flag, 'N'), "
				+ "          'N', (  ctd_tc_first_inst_pay_amt "
				+ "                - NVL (ctd_tc_asf_deductable, 0) "
				+ "                - NVL (CTD_SWBH_CESS_TAX_DED, 0) "
				+ "                - NVL (CTD_SERV_TAX_DED, 0) "
				+ "                - NVL (CTD_KK_CESS_TAX_DED, 0)), "
				+ "          (  ctd_tc_first_inst_pay_amt "
				+ "           + NVL (ctd_tc_sf_refundable, 0) "
				+ "           + NVL (CTD_SWBH_CESS_TAX_DED, 0) "
				+ "           + NVL (CTD_SERV_TAX_DED, 0) "
				+ "           + NVL (CTD_KK_CESS_TAX_DED, 0))) "
				+ "          netpaid, "
				+ "       NVL (CLM_PAYMENT_UTR_NO, '-'), "
				+ "       NVL (CLM_PAYMENT_AC_NO, '-'), "
				+ "       NVL (CLM_PYMT_OUTWARD_NO, '-'), "
				+ "       CLM_PYMT_OUTWARD_DT, "
				+ "       clm_approved_dt, "
				+ "       clm_payment_dt, "
				+ "       DECODE (ctd_sf_refund_flag, 'Y', ctd_tc_sf_refundable, 0), "
				+ "       ctd_sf_refund_flag, "
				+ "       DECODE (ctd_sf_refund_flag, 'Y',NVL (CTD_SWBH_CESS_TAX_DED, 0), 0) SWBH_CESS_REFUND, "
				+ "       DECODE (ctd_sf_refund_flag, 'Y',NVL (CTD_SERV_TAX_DED, 0), 0) SERV_TAX_REFUND, "
				+ "       DECODE (ctd_sf_refund_flag, 'Y',NVL (CTD_KK_CESS_TAX_DED, 0), 0) KK_CESS_REFUND, "
				+ "       DECODE (ctd_sf_refund_flag, 'N',NVL (CTD_SWBH_CESS_TAX_DED, 0), 0) SWBH_CESS_DED, "
				+ "       DECODE (ctd_sf_refund_flag, 'N',NVL (CTD_SERV_TAX_DED, 0), 0) SERV_TAX_DED, "
				+ "       DECODE (ctd_sf_refund_flag, 'N',NVL (CTD_KK_CESS_TAX_DED, 0), 0) KK_CESS_DED "
				+ "  FROM APPLICATION_DETAIL A, "
				+ "       SSI_DETAIL s, "
				+ "       CLAIM_DETAIL c, "
				+ "       CLAIM_TC_DETAIL ctd, "
				+ "       MEMBER_INFO m, "
				+ "       CLAIM_APPLICATION_AMOUNT caa "
				+ " WHERE     A.ssi_reference_number = s.ssi_Reference_number "
				+ "       AND s.bid = c.bid "
				+ "       AND C.CLM_REF_NO = ctd.clm_ref_no "
				+ "       AND A.mem_bnk_id || A.mem_zne_id || A.mem_brn_id = "
				+ "              m.mem_bnk_id || m.mem_zne_id || m.mem_brn_id "
				+ "       AND A.cgpan = caa.cgpan "
				+ "       AND A.cgpan = ctd.cgpan "
				+ "       AND app_loan_type IN ('TC') "
				+ "       AND clm_status IN ('AP') "
				+ "   and trunc("+clm_date_for_report+")  >=  to_date('"+fromdate+"','DD/MM/YYYY')"
				+ "   and trunc("+clm_date_for_report+")  <=  to_date('"+todate+"','DD/MM/YYYY')"
				+ "   and  c.mem_bnk_id||c.mem_zne_id||c.mem_brn_id='"+memberID+"'	"
				+ "UNION "
				+ "SELECT mem_bank_name, "
				+ "       mem_zone_name, "
				+ "       c.mem_bnk_id || c.mem_zne_id || c.mem_brn_id memberid, "
				+ "       A.cgpan, "
				+ "       ssi_unit_name, "
				+ "       DECODE (NVL (app_reapprove_amount, 0), "
				+ "               0, app_approved_amount, "
				+ "               app_reapprove_amount) "
				+ "          appamt, "
				+ "       cwd_npa_outstanding_amt_revise nps_os, "
				+ "       cwd_npa_recovered_revise reco, "
				+ "         LEAST ( "
				+ "            DECODE (NVL (app_reapprove_amount, 0), "
				+ "                    0, app_approved_amount, "
				+ "                    app_reapprove_amount), "
				+ "            cwd_npa_outstanding_amt_revise) "
				+ "       - cwd_npa_recovered_revise "
				+ "          netos, "
				+ "       caa_applied_amount, "
				+ "       cwd_wc_clm_elig_amt, "
				+ "       cwd_wc_first_inst_pay_amt, "
				+ "       cwd_wc_asf_deductable, "
				+ "       DECODE ( "
				+ "          NVL (cwd_sf_refund_flag, 'N'), "
				+ "          'N', (  cwd_wc_first_inst_pay_amt "
				+ "                - NVL (cwd_wc_asf_deductable, 0) "
				+ "                - NVL (CWD_SWBH_CESS_TAX_DED, 0) "
				+ "                - NVL (CWD_SERV_TAX_DED, 0) "
				+ "                - NVL (CWD_KK_CESS_TAX_DED, 0)), "
				+ "          (  cwd_wc_first_inst_pay_amt "
				+ "           + NVL (cwd_wc_sf_refundable, 0) "
				+ "           + NVL (CWD_SWBH_CESS_TAX_DED, 0) "
				+ "           + NVL (CWD_SERV_TAX_DED, 0) "
				+ "           + NVL (CWD_KK_CESS_TAX_DED, 0))) "
				+ "          netpaid, "
				+ "       NVL (CLM_PAYMENT_UTR_NO, '-'), "
				+ "       NVL (CLM_PAYMENT_AC_NO, '-'), "
				+ "       NVL (CLM_PYMT_OUTWARD_NO, '-'), "
				+ "       CLM_PYMT_OUTWARD_DT, "
				+ "       clm_approved_dt, "
				+ "       clm_payment_dt, "
				+ "       DECODE (cwd_sf_refund_flag, 'Y', cwd_wc_sf_refundable, 0), "
				+ "       cwd_sf_refund_flag, "
				+ "       DECODE (cwd_sf_refund_flag, 'Y',NVL (CWD_SWBH_CESS_TAX_DED, 0), 0) SWBH_CESS_REFUND, "
				+ "       DECODE (cwd_sf_refund_flag, 'Y',NVL (CWD_SERV_TAX_DED, 0), 0) SERV_TAX_REFUND, "
				+ "       DECODE (cwd_sf_refund_flag, 'Y',NVL (CWD_KK_CESS_TAX_DED, 0), 0) KK_CESS_REFUND, "
				+ "        DECODE (cwd_sf_refund_flag, 'N',NVL (CWD_SWBH_CESS_TAX_DED, 0), 0) SWBH_CESS_DED, "
				+ "       DECODE (cwd_sf_refund_flag, 'N',NVL (CWD_SERV_TAX_DED, 0), 0) SERV_TAX_DED, "
				+ "       DECODE (cwd_sf_refund_flag, 'N',NVL (CWD_KK_CESS_TAX_DED, 0), 0) KK_CESS_DED "
				+ "  FROM APPLICATION_DETAIL A, "
				+ "       SSI_DETAIL s, "
				+ "       CLAIM_DETAIL c, "
				+ "       CLAIM_WC_DETAIL cwd, "
				+ "       MEMBER_INFO m, "
				+ "       CLAIM_APPLICATION_AMOUNT caa "
				+ " WHERE     A.ssi_reference_number = s.ssi_Reference_number "
				+ "       AND s.bid = c.bid "
				+ "       AND C.CLM_REF_NO = cwd.clm_ref_no "
				+ "       AND A.mem_bnk_id || A.mem_zne_id || A.mem_brn_id = "
				+ "              m.mem_bnk_id || m.mem_zne_id || m.mem_brn_id "
				+ "       AND A.cgpan = caa.cgpan "
				+ "       AND A.cgpan = cwd.cgpan "
				+ "       AND app_loan_type IN ('WC') "
				+ "       AND clm_status IN ('AP') "
				+ "   and trunc("+clm_date_for_report+")  >=  to_date('"+fromdate+"','DD/MM/YYYY')"
				+ "   and trunc("+clm_date_for_report+")  <=  to_date('"+todate+"','DD/MM/YYYY')"
				+ "   and  c.mem_bnk_id||c.mem_zne_id||c.mem_brn_id='"+memberID+"'	"
				+ "UNION "
				+ "SELECT mem_bank_name, "
				+ "       mem_zone_name, "
				+ "       c.mem_bnk_id || c.mem_zne_id || c.mem_brn_id memberid, "
				+ "       A.cgpan, "
				+ "       ssi_unit_name, "
				+ "       DECODE (NVL (app_reapprove_amount, 0), "
				+ "               0, app_approved_amount, "
				+ "               app_reapprove_amount) "
				+ "          appamt, "
				+ "       cwd_npa_outstanding_amt_revise + ctd_npa_outstanding_amt_revise nps_os, "
				+ "       cwd_npa_recovered_revise + ctd_npa_recovered_revise reco, "
				+ "         LEAST ( "
				+ "            DECODE (NVL (app_reapprove_amount, 0), "
				+ "                    0, app_approved_amount, "
				+ "                    app_reapprove_amount), "
				+ "            cwd_npa_outstanding_amt_revise + ctd_npa_outstanding_amt_revise) "
				+ "       - cwd_npa_recovered_revise "
				+ "       + ctd_npa_recovered_revise "
				+ "          netos, "
				+ "       caa_applied_amount, "
				+ "       cwd_wc_clm_elig_amt + ctd_tc_clm_elig_amt, "
				+ "       cwd_wc_first_inst_pay_amt + ctd_tc_first_inst_pay_amt, "
				+ "       cwd_wc_asf_deductable + cwd_wc_asf_deductable, "
				+ "       DECODE ( "
				+ "          NVL (ctd_sf_refund_flag, 'N'), "
				+ "          'N', (  ctd_tc_first_inst_pay_amt "
				+ "                - ctd_tc_asf_deductable "
				+ "                - NVL (CTD_SWBH_CESS_TAX_DED, 0) "
				+ "                - NVL (CTD_SERV_TAX_DED, 0) "
				+ "                - NVL (CTD_KK_CESS_TAX_DED, 0)), "
				+ "          (  ctd_tc_first_inst_pay_amt "
				+ "           + ctd_tc_asf_deductable "
				+ "           + NVL (CTD_SWBH_CESS_TAX_DED, 0) "
				+ "           + NVL (CTD_SERV_TAX_DED, 0) "
				+ "           + NVL (CTD_KK_CESS_TAX_DED, 0))) "
				+ "          netpaid, "
				+ "       NVL (CLM_PAYMENT_UTR_NO, '-'), "
				+ "       NVL (CLM_PAYMENT_AC_NO, '-'), "
				+ "       NVL (CLM_PYMT_OUTWARD_NO, '-'), "
				+ "       CLM_PYMT_OUTWARD_DT, "
				+ "       clm_approved_dt, "
				+ "       clm_payment_dt, "
				+ "       DECODE (ctd_sf_refund_flag, "
				+ "               'Y', ctd_tc_sf_refundable + cwd_wc_sf_refundable, "
				+ "               0), "
				+ "       ctd_sf_refund_flag, "
				+ "       DECODE (ctd_sf_refund_flag, 'Y',NVL (CTD_SWBH_CESS_TAX_DED, 0), 0) SWBH_CESS_REFUND, "
				+ "       DECODE (ctd_sf_refund_flag, 'Y',NVL (CTD_SERV_TAX_DED, 0), 0) SERV_TAX_REFUND, "
				+ "       DECODE (ctd_sf_refund_flag, 'Y',NVL (CTD_KK_CESS_TAX_DED, 0), 0) KK_CESS_REFUND, "
				+ "       DECODE (ctd_sf_refund_flag, 'N',NVL (CTD_SWBH_CESS_TAX_DED, 0), 0) SWBH_CESS_DED, "
				+ "       DECODE (ctd_sf_refund_flag, 'N',NVL (CTD_SERV_TAX_DED, 0), 0) SERV_TAX_DED, "
				+ "       DECODE (ctd_sf_refund_flag, 'N',NVL (CTD_KK_CESS_TAX_DED, 0), 0) KK_CESS_DED "
				+ "  FROM APPLICATION_DETAIL A, "
				+ "       SSI_DETAIL s, "
				+ "       CLAIM_DETAIL c, "
				+ "       CLAIM_WC_DETAIL cwd, "
				+ "       MEMBER_INFO m, "
				+ "       CLAIM_APPLICATION_AMOUNT caa, "
				+ "       CLAIM_TC_DETAIL ctd "
				+ " WHERE     A.ssi_reference_number = s.ssi_Reference_number "
				+ "       AND s.bid = c.bid "
				+ "       AND C.CLM_REF_NO = cwd.clm_ref_no "
				+ "       AND C.CLM_REF_NO = ctd.clm_ref_no "
				+ "       AND A.mem_bnk_id || A.mem_zne_id || A.mem_brn_id = "
				+ "              m.mem_bnk_id || m.mem_zne_id || m.mem_brn_id "
				+ "       AND A.cgpan = caa.cgpan "
				+ "       AND A.cgpan = ctd.cgpan "
				+ "       AND A.cgpan = cwd.cgpan "
				+ "       AND app_loan_type IN ('CC') "
				+ "       AND clm_status IN ('AP') "
				+ "   and trunc("+clm_date_for_report+")  >=  to_date('"+fromdate+"','DD/MM/YYYY')"
				+ "   and trunc("+clm_date_for_report+")  <=  to_date('"+todate+"','DD/MM/YYYY')"
				+ "   and  c.mem_bnk_id||c.mem_zne_id||c.mem_brn_id='"+memberID+"'	"
				+ "ORDER BY 1, 2, 5";
			////System.out.println("query3"+query);
			
		}
		SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
		Date clm_approved_date = null;
		Date clm_payment_date = null;
		String numberformat = "-?\\d+(\\.\\d+)?";
		String firstInstAmt = "0.0";
		String asfDeductedAmt = "0.0";
		String netPaidAmt = "0.0";
		Date clm_outward_date = null;
		String refundableAmt = "0.0";
		
		String swbhTaxDed = "0.0";
		String serTaxDed = "0.0";
		
		String serKrishiKalyan = "0.0";
		
		
		String serDedKrishiKalyan = "0.0";
		
		String swDedbhTaxDed = "0.0";
		String serDedTaxDed = "0.0";
		

		try {
			Statement pendingCaseDetailsStmt = null;
			ResultSet pendingCaseDetailsResult = null;
			pendingCaseDetailsStmt = connection.createStatement();
			for (pendingCaseDetailsResult = pendingCaseDetailsStmt
					.executeQuery(query); pendingCaseDetailsResult.next(); PAArrayList
					.add(PAStringArray)) {
				//PAStringArray = new String[24];
				PAStringArray = new String[28];
				PAStringArray[0] = pendingCaseDetailsResult.getString(1);
				PAStringArray[1] = pendingCaseDetailsResult.getString(2);
				PAStringArray[2] = pendingCaseDetailsResult.getString(3);
				PAStringArray[3] = pendingCaseDetailsResult.getString(4);
				PAStringArray[4] = pendingCaseDetailsResult.getString(5);
				PAStringArray[5] = pendingCaseDetailsResult.getString(6);
				PAStringArray[6] = pendingCaseDetailsResult.getString(7);
				PAStringArray[7] = pendingCaseDetailsResult.getString(8);
				PAStringArray[8] = pendingCaseDetailsResult.getString(9);
				PAStringArray[9] = pendingCaseDetailsResult.getString(10);
				PAStringArray[10] = pendingCaseDetailsResult.getString(11);

				firstInstAmt = String.valueOf(pendingCaseDetailsResult
						.getString(12));
				asfDeductedAmt = String.valueOf(pendingCaseDetailsResult
						.getString(13));
				netPaidAmt = String.valueOf(pendingCaseDetailsResult
						.getString(14));

				PAStringArray[14] = pendingCaseDetailsResult.getString(15);
				PAStringArray[15] = pendingCaseDetailsResult.getString(16);
				PAStringArray[16] = pendingCaseDetailsResult.getString(17);
				clm_outward_date = pendingCaseDetailsResult.getDate(18);
				clm_approved_date = pendingCaseDetailsResult.getDate(19);
				clm_payment_date = pendingCaseDetailsResult.getDate(20);

				if (firstInstAmt.matches(numberformat))
					PAStringArray[11] = firstInstAmt;
				else
					PAStringArray[11] = "0.0";
				if (asfDeductedAmt.matches(numberformat))
					PAStringArray[12] = asfDeductedAmt;
				else
					PAStringArray[12] = "0.0";
				if (netPaidAmt.matches(numberformat))
					PAStringArray[13] = netPaidAmt;
				else
					PAStringArray[13] = "0.0";

				if (clm_outward_date != null)
					PAStringArray[17] = sdf.format(clm_outward_date);
				else
					PAStringArray[17] = "";

				if (clm_approved_date != null)
					PAStringArray[18] = sdf.format(clm_approved_date);
				else
					PAStringArray[18] = "";
				if (clm_payment_date != null)
					PAStringArray[19] = sdf.format(clm_payment_date);
				else
					PAStringArray[19] = "";

				refundableAmt = String.valueOf(pendingCaseDetailsResult
						.getString(21));

				if (refundableAmt.matches(numberformat))
					PAStringArray[20] = refundableAmt;
				else
					PAStringArray[20] = "0.0";

				PAStringArray[21] = pendingCaseDetailsResult.getString(22);
				
				swbhTaxDed = String.valueOf(pendingCaseDetailsResult
						.getString(23));
				PAStringArray[22]=swbhTaxDed;
				
				//if (swbhTaxDed.matches(numberformat))  
					
				//else
					//PAStringArray[22] = "0.0";
				
				serTaxDed = String.valueOf(pendingCaseDetailsResult
						.getString(24));
				PAStringArray[23]=serTaxDed;
				//if (serTaxDed.matches(numberformat))
				
			//else
				//PAStringArray[23] = "0.0";
				 
				//PAStringArray[23]= String.valueOf(pendingCaseDetailsResult
						//.getString(24));
				
				serKrishiKalyan= String.valueOf(pendingCaseDetailsResult
						.getString(25));
				PAStringArray[24]=serKrishiKalyan;
				
				
				serDedTaxDed= String.valueOf(pendingCaseDetailsResult
						.getString(26));
				PAStringArray[25]=serDedTaxDed;
				
				swDedbhTaxDed= String.valueOf(pendingCaseDetailsResult
						.getString(27));
				PAStringArray[26]=swDedbhTaxDed;

				serDedKrishiKalyan= String.valueOf(pendingCaseDetailsResult
						.getString(28));
				PAStringArray[27]=serDedKrishiKalyan;
			
			//	//System.out.println("serKrishiKalyan"+serKrishiKalyan);	 
			}
			pendingCaseDetailsResult.close();
			pendingCaseDetailsResult = null;
			pendingCaseDetailsStmt.close();
			pendingCaseDetailsStmt = null;
		} catch (Exception e) {
			Log.logException(e);
			////System.out.println("e.getMessage()"+e.getMessage());
			throw new DatabaseException(e.getMessage());
		} finally {
			DBConnection.freeConnection(connection);
		}
////System.out.println("arrayList"+PAArrayList.size());
		if (PAArrayList.size() == 0) {
			throw new NoDataException(
					"There are no Claim Ref Numbers that match the query.");
		}
		request.setAttribute("reportCondition", condition);
		request.setAttribute("pendingCaseDetailsArray", PAArrayList);
		request.setAttribute("pendingCaseDetailsArray_size", (new Integer(
				PAArrayList.size())).toString());
		Log.log(4, "NewReportsAction", "Claim settled", "Exited");
		return mapping.findForward("success");
	}

	/*
	 * public ActionForward clmSettldReport(ActionMapping mapping, ActionForm form,
	 * HttpServletRequest request, HttpServletResponse response) throws Exception {
	 * Log.log(4, "ReportsAction", "clmSettldReport", "Entered"); DynaActionForm
	 * dynaForm = (DynaActionForm)form; User user = getUserInformation(request);
	 * HttpSession session = request.getSession(false); String bankId =
	 * user.getBankId(); String zoneId = user.getZoneId(); String branchId =
	 * user.getBranchId(); String memberId = bankId.concat(zoneId).concat(branchId);
	 * java.util.Date date = new java.util.Date(); Calendar calendar =
	 * Calendar.getInstance(); calendar.setTime(date); int month = calendar.get(2);
	 * int day = calendar.get(5); month--; day++; calendar.set(2, month);
	 * calendar.set(5, day); java.util.Date prevDate = calendar.getTime();
	 * GeneralReport generalReport = new GeneralReport();
	 * generalReport.setDateOfTheDocument26(prevDate);
	 * generalReport.setDateOfTheDocument27(date);
	 * BeanUtils.copyProperties(dynaForm, generalReport); dynaForm.set("bankId",
	 * bankId); dynaForm.set("zone_Id", zoneId); if (bankId.equals("0000")||
	 * zoneId.equals("0000")) memberId = ""; dynaForm.set("memberId", memberId);
	 * 
	 * session.setAttribute("memberId", memberId); Log.log(4, "ReportsAction",
	 * "clmSettldReport", "Exited");
	 * 
	 * return mapping.findForward("success"); }
	 */
	public ActionForward clmSettldReport(ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		Log.log(4, "ReportsAction", "clmSettldReport", "Entered");
		DynaActionForm dynaForm = (DynaActionForm) form;
		dynaForm.initialize(mapping);

		User loggedUser = getUserInformation(request);
		String userBankId = loggedUser.getBankId();
		String userZoneId = loggedUser.getZoneId();
		String userBranchId = loggedUser.getBranchId();
		String userMemberId = userBankId + userZoneId + userBranchId;

        Registration registration = new Registration();
        ArrayList memberIds = new ArrayList();
        ArrayList zones = null;
        ArrayList branches = null;
        ArrayList allMembers = null;
        MLIInfo mliInfo = null;

        memberIds.add(userMemberId);
        User user = getUserInformation(request);
        HttpSession session = request.getSession(false);
        String bankId = user.getBankId();
        String zoneId = user.getZoneId();
        String branchId = user.getBranchId();
        String memberId = bankId.concat(zoneId).concat(branchId);
        java.util.Date date = new java.util.Date();
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        int month = calendar.get(2);
        int day = calendar.get(5);
        month--;
        day++;
        calendar.set(2, month);
        calendar.set(5, day);
        java.util.Date prevDate = calendar.getTime();
        GeneralReport generalReport = new GeneralReport();
        generalReport.setDateOfTheDocument26(prevDate);
        generalReport.setDateOfTheDocument27(date);
        BeanUtils.copyProperties(dynaForm, generalReport);
        dynaForm.set("bankId", bankId);
        dynaForm.set("zone_Id", zoneId);
       /* if (bankId.equals("0000")|| zoneId.equals("0000"))
         memberId = "";
       dynaForm.set("memberId", memberId);
       
        session.setAttribute("memberId", memberId);*/
        
        
        
        
        
        
        //memberIds.add(userMemberId);

		if ((userBankId.equals("0000")) && (userZoneId.equals("0000")) && (userBranchId.equals("0000"))) {
			allMembers = registration.getAllMembers();
			int memberSize = allMembers.size();
			for (int i = 0; i < memberSize; i++) {
				mliInfo = (MLIInfo) allMembers.get(i);
				String bankId1 = mliInfo.getBankId();
				String zoneId1 = mliInfo.getZoneId();
				String branchId1 = mliInfo.getBranchId();
				String memberId1 = bankId1 + zoneId1 + branchId1;
				memberIds.add(memberId);
			}

		} else if ((!userBankId.equals("0000")) && (userZoneId.equals("0000")) && (userBranchId.equals("0000"))) {
			zones = registration.getZones(userBankId);
			int zoneSize = zones.size();
			for (int i = 0; i < zoneSize; i++) {
				mliInfo = (MLIInfo) zones.get(i);
				String zoneId2 = mliInfo.getZoneId();
				String branchId2 = mliInfo.getBranchId();
				String memberId2 = userBankId + zoneId2 + branchId2;
				memberIds.add(memberId2);
			}

			branches = registration.getAllBranches(userBankId);
			int branchSize = branches.size();
			for (int i = 0; i < branchSize; i++) {
				mliInfo = (MLIInfo) branches.get(i);
				String branchId2 = mliInfo.getBranchId();
				String zoneId2 = mliInfo.getZoneId();
				String memberId2 = userBankId + zoneId + branchId;
				memberIds.add(memberId);
			}
		} else if ((!userBankId.equals("0000")) && (!userZoneId.equals("0000")) && (userBranchId.equals("0000"))) {
			branches = registration.getAllBranches(userBankId);
			int branchSize = branches.size();
			for (int i = 0; i < branchSize; i++) {
				mliInfo = (MLIInfo) branches.get(i);
				String branchId2 = mliInfo.getBranchId();
				String zoneId2 = mliInfo.getZoneId();

            if (zoneId.equals(userZoneId)) {
              String members = userBankId + zoneId + branchId;
              memberIds.add(members);
            }
          }

        }

        dynaForm.set("memberIds", memberIds);
        Log.log(4, "ReportsAction", "clmSettldReport", "Exited");

        return mapping.findForward("success");
    }

    /**
     * 
     * @param mapping
     * @param form
     * @param request
     * @param response
     * @return 
     * @throws java.lang.Exception
     */
    public ActionForward claimDeclarationInput(ActionMapping mapping, 
                                               ActionForm form, 
                                               HttpServletRequest request, 
                                               HttpServletResponse response) throws Exception {

        Log.log(Log.INFO, "NewReportsAction", "claimDeclarationInput", 
                "Entered");

        DynaActionForm dynaForm = (DynaActionForm)form;

        User user = getUserInformation(request);
        String bankId = user.getBankId();
        String zoneId = user.getZoneId();
        String branchId = user.getBranchId();
        String memberId = bankId.concat(zoneId).concat(branchId);

        Date date = new Date();
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        int month = calendar.get(Calendar.MONTH);
        int day = calendar.get(Calendar.DATE);
        month = month - 1;
        day = day + 1;
        calendar.set(Calendar.MONTH, month);
        calendar.set(Calendar.DATE, day);
        Date prevDate = calendar.getTime();

        GeneralReport generalReport = new GeneralReport();
        generalReport.setDateOfTheDocument26(prevDate);
        generalReport.setDateOfTheDocument27(date);
        BeanUtils.copyProperties(dynaForm, generalReport);
        dynaForm.set("bankId", bankId);
        if (bankId.equals(Constants.CGTSI_USER_BANK_ID)) {
            memberId = "";
        }
        dynaForm.set("memberId", memberId);
        Log.log(Log.INFO, "NewReportsAction", "claimDeclarationInput", 
                "Exited");
        return mapping.findForward("success");
    }


    /**
     *
     * @param mapping
     * @param form
     * @param request
     * @param response
     * @return
     * @throws java.lang.Exception
     */
    public ActionForward claimDeclarationReport(ActionMapping mapping, 
                                                ActionForm form, 
                                                HttpServletRequest request, 
                                                HttpServletResponse response) throws Exception {

        Log.log(Log.INFO, "ReportsAction", "npaReport", "Entered");
        ArrayList npaDetails = new ArrayList();
        DynaActionForm dynaForm = (DynaActionForm)form;
        java.sql.Date startDate = null;
        java.sql.Date endDate = null;
        java.util.Date sDate = 
            (java.util.Date)dynaForm.get("dateOfTheDocument26");
        String stDate = String.valueOf(sDate);


        java.util.Date eDate = 
            (java.util.Date)dynaForm.get("dateOfTheDocument27");
        endDate = new java.sql.Date(eDate.getTime());

        // //System.out.println("date is"+endDate);

        java.util.Date todaydate = new java.util.Date();

        //  //System.out.println("todaydate is"+endDate);

        //  //System.out.println("compare"+(todaydate.compareTo(endDate)));

        ArrayList claimDeclArrayList = new ArrayList();

        int claimCount;

        String clm1 = null;


        int month = 7;
        int year = 1970;
        int hours = 0;
        int day = 0;
        int min = 0;
        String strDay = "";
        String strMonth = "";
        String rDate = "";
        Calendar cal = Calendar.getInstance();
        ////System.out.println("into else"+dateP.get(dateP.DATE)+"year "+dateP.get(dateP.YEAR)+"month"+dateP.get(dateP.HOUR)+"amorp"+dateP.get(dateP.AM_PM));
        try {

            day = cal.get(Calendar.DATE);
            month = cal.get(Calendar.MONTH);
            year = cal.get(Calendar.YEAR);
            //hours = cal.get(cal. 22-02-2000
            month++;

            if (month == 0)
                month = 12;
            strDay = day + "";
            strMonth = month + "";
            if (strDay.length() == 1) {
                strDay = "0" + strDay;
            }
            if (strMonth.length() == 1) {
                strMonth = "0" + strMonth;
            }

            rDate = strDay + "/" + strMonth + "/" + year;

        } catch (Exception ert) {
            return null;
        }

        //  //System.out.println("strDay"+strDay+"strMonth"+strMonth+"year"+year);


        String edateStr = endDate.toString();

        //  //System.out.println("edateStr"+edateStr);

        String todaydateStr = todaydate.toString().trim();

        // //System.out.println("todaydateStr"+todaydateStr);


        StringTokenizer st = new StringTokenizer(edateStr, dbDateSeparator);
        if (st.countTokens() != 3) {

        }

        String yr1 = st.nextToken();
        String mth1 = st.nextToken();
        String day1 = st.nextToken();

        //   //System.out.println("mth1"+mth1+"day1"+day1+"yr1"+yr1);


        if ((strDay.equals(day1)) && (strMonth.equals(mth1)) && 
            (strDay.equals(day1)))


        {

            throw new NoDataException("claim date should not be equal to  today's date");

        }


        if ((todaydate.compareTo(endDate)) < 0)

        {

            throw new NoDataException("claim date should not be greater than  today's date");

        }

        // if ((todaydate.compareTo(endDate))==1)

        // {

        //  throw new NoDataException("claim date should not be equal to today's date");

        // }

        String id = (String)dynaForm.get("memberId");


        Connection connection = DBConnection.getConnection();
        CallableStatement claimDeclaration = null;


        try {
            //claimDeclaration=connection.prepareCall(
            //   "{?=call PackGetClmDeclDetails.funcGetClmDeclDetails(?,?,?,?,?)}");


            claimDeclaration = 
                    connection.prepareCall("{?=call PackGetClmDeclDetails.funcGetClmDeclDetails(?,?,?,?,?)}");


            claimDeclaration.registerOutParameter(1, Types.INTEGER);
            claimDeclaration.setString(2, id);
            claimDeclaration.setDate(3, endDate);


            claimDeclaration.registerOutParameter(4, Types.INTEGER);
            claimDeclaration.registerOutParameter(5, Constants.CURSOR);

            claimDeclaration.registerOutParameter(6, Types.VARCHAR);

            claimDeclaration.executeQuery();

            int Status = claimDeclaration.getInt(1);


            if (Status == Constants.FUNCTION_FAILURE) {

                String error = claimDeclaration.getString(6);

                claimDeclaration.close();
                claimDeclaration = null;


                throw new DatabaseException(error);
            } else {

                claimCount = claimDeclaration.getInt(4);


                String claimDeclarationArray[] = null;

                ResultSet claimDeclarationResults = 
                    (ResultSet)claimDeclaration.getObject(5);


                int i = 0;
                while (claimDeclarationResults.next()) {

                   // //System.out.println(claimDeclarationResults.getString(1));

                    claimDeclarationArray = new String[5];
                    if (clm1 != null) {
                        // if(clm1==claimDeclarationResults.getString(1)) {

                        // if(clm1==claimDeclarationResults.getString(1)) {
                        String clmdup = claimDeclarationResults.getString(1);

                        if (((clmdup).equals(clm1))) {

                            String clm2;
                            claimDeclarationArray[0] = new String("--");

                            clm2 = (claimDeclarationResults.getString(1));
                            ////System.out.println("clm2"+clm2);
                            clm1 = clm2;

                            ////System.out.println("claim ref num is"+clm1);
                        }

                        else {
                            String clm4;
                            ++i;
                           // //System.out.println(i);

                            claimDeclarationArray[0] = Integer.toString(i);
                            //ritesh++;
                            clm4 = (claimDeclarationResults.getString(1));
                            ////System.out.println("clm3"+clm4);
                            clm1 = clm4;

                            ////System.out.println("claim is "+clm1);

                        }
                    }

                    else {
                        String clm3;
                        ++i;
                      //  //System.out.println(i);

                        claimDeclarationArray[0] = Integer.toString(i);
                        //ritesh++;
                        clm3 = (claimDeclarationResults.getString(1));
                        ////System.out.println("clm3"+clm3);
                        clm1 = clm3;

                        ////System.out.println("claim is "+clm1);
                    }


                    ////System.out.println("clm is----"+clm1);


                    //  claimDeclarationArray[0]=(claimDeclarationResults.getString(1));

                    claimDeclarationArray[1] = 
                            (claimDeclarationResults.getString(1));
                    claimDeclarationArray[2] = 
                            (claimDeclarationResults.getString(2));
                    claimDeclarationArray[3] = 
                            (claimDeclarationResults.getString(3));
                    claimDeclarationArray[4] = 
                            (claimDeclarationResults.getString(4));


                    claimDeclArrayList.add(claimDeclarationArray);

                }


                claimDeclaration.close();
                claimDeclaration = null;


            }
        } catch (SQLException sqlException) {
            try {
                connection.rollback();
            } catch (SQLException ignore) {
                Log.log(Log.INFO, "ApplicationDAO", "checkDuplicatePath", 
                        "Exception :" + ignore.getMessage());
            }
            throw new DatabaseException(sqlException.getMessage());
        } finally {
            DBConnection.freeConnection(connection);
        }
        request.setAttribute("memId", id);
        request.setAttribute("endDate", endDate);
        request.setAttribute("claimCount", claimCount);
        request.setAttribute("claimDeclArrayList", claimDeclArrayList);
        request.setAttribute("claimDeclArrayListsize", 
                             new Integer(claimDeclArrayList.size()).toString());

        return mapping.findForward("success");
    }
    public ActionForward showNpaUpgradationList(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response)
    throws Exception
{
    Log.log(4, "GMAction", "displayRequestedForNPAApproval", "Entered");
    GMActionForm gmActionForm1 = null;
    ArrayList npaUpdationList = new ArrayList();
    GeneralReport generalReport = null;
    Connection connection = null;
    Statement stmt = null;
    ResultSet rs = null;
    ResultSet rs1 = null;
    User user = getUserInformation(request);
    String bankId = user.getBankId();
    String zoneId = user.getZoneId();
    String branchId = user.getBranchId();
    String memberId = (new StringBuilder(String.valueOf(bankId))).append(zoneId).append(branchId).toString();
    String forward = "";
    DynaActionForm dynaForm = (DynaActionForm)form;
    connection = DBConnection.getConnection(false);
    String modificationQuery = (new StringBuilder("select  mem_bank_name,mem_zone_name, a.mem_bnk_id||a.mem_zne_id||a.mem_brn_id,cgpan,ssi_unit_name,a.NPA_EFFECTIVE_DT,NPA_UPGRADE_DT,USR_ID,USR_DSG_NAME,USR_EMP_ID from  npa_upgradation_detail a,   member_info b,mli_checker_info c,ssi_detail s,npa_detail_temp_canc d  where s.bid=d.bid and a.npa_id=d.npa_id and   NUD_MLI_LWR_LEV_AP_BY=USR_ID  and a.mem_bnk_id||a.mem_zne_id||a.mem_brn_id=b.mem_bnk_id||b.mem_zne_id||b.mem_brn_id   and NUD_UPGRADE_CHANG_FLAG='LA'  and  a.mem_bnk_id || a.mem_zne_id || a.mem_brn_id ='")).append(memberId).append("'").toString();
    stmt = connection.createStatement();
    for(rs = stmt.executeQuery(modificationQuery); rs.next(); npaUpdationList.add(generalReport))
    {
        generalReport = new GeneralReport();
        generalReport.setBankName(rs.getString(1));
        generalReport.setMemberId(rs.getString(2));
        generalReport.setZoneName(rs.getString(3));
        generalReport.setCgpan(rs.getString(4));
        generalReport.setUnitName(rs.getString(5));
        generalReport.setNpaEffDt(rs.getDate(6));
        generalReport.setNpaUpgraDt(rs.getDate(7));
        generalReport.setUserId(rs.getString(8));
        generalReport.setDesignation(rs.getString(9));
        generalReport.setEmployeeId(rs.getString(10));
    }

    rs.close();
    rs = null;
    stmt.close();
    stmt = null;
    if(npaUpdationList == null || npaUpdationList.size() == 0)
    {
        Log.log(4, "GMAction", "showTenureApproval", "Emty NPA Updation Approval list");
        request.setAttribute("message", "No NPA Upgradation details available");
        throw new MessageException("No NPA Upgradation details available");
    } else
    {
        dynaForm.set("danRaised", npaUpdationList);
        forward = "listofaccounts";
        return mapping.findForward(forward);
    }
}
    
    
    
    
	public ActionForward showQueryStatusReport(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		  Log.log(5, "AdministrationAction", "displayNpaRegistrationFormList", "Entered");
		
		  AdministrationActionForm adminForm = (AdministrationActionForm)form;
		  
		  
		  
		  
		  java.sql.Date startDate = null;
	        java.sql.Date endDate = null;

	        java.util.Date sDate = 
	            (java.util.Date)adminForm.getDateOfTheDocument();
	        String stDate = String.valueOf(sDate);

	        if ((stDate == null) || (stDate.equals("")))
	            startDate = null;
	        else if (stDate != null) {
	            startDate = new java.sql.Date(sDate.getTime());
	        }
	        String status = (String)adminForm.getCheck1();

	      //  if (guarantee.equals("yes"))
	         //   request.setAttribute("radioValue", "CGPAN");
	      //  else if (guarantee.equals("no")) {
	         //   request.setAttribute("radioValue", "BORROWER/Unit Name");
	      //  }
	        java.util.Date eDate = 
	        	 (java.util.Date)adminForm.getDateOfTheDocument1();
	        endDate = new java.sql.Date(eDate.getTime());
		  
		  
		  
		  ArrayList queryDetailsList = new ArrayList();
		  AdministrationActionForm queryDetails = null;
		  Connection connection = DBConnection.getConnection(false);
		  ResultSet rs = null;
		  Statement stmt = null;
		  User user = getUserInformation(request);
	        String bankId = user.getBankId();
	        String zoneId = user.getZoneId();
	        String branchId = user.getBranchId();
	        String memberId = bankId.concat(zoneId).concat(branchId);
		  String queryInfo = "SELECT QRY_ID,MEM_BNK_ID , MEM_ZNE_ID, MEM_BRN_ID, MEM_PHONE_NUMBER, MEM_EMAIL, QUERY_DESC, QUERY_RAISED_BY, to_char(QUERY_RAISED_DT),QUERY_STATUS,DECODE(QUERY_RESOLVR_RESPONS,'','your query is under process',QUERY_RESOLVR_RESPONS) " +
			"  FROM QUERY_MASTER where QUERY_STATUS is  not null  and MEM_BNK_ID||MEM_ZNE_ID||MEM_BRN_ID='"+ memberId + "'  and  QUERY_STATUS='"+ status + "' " +
					"  and  trunc(QUERY_RAISED_DT) between to_date('" + sDate + 
                "','dd/mm/yyyy')  and  to_date('" + eDate + 
                "','dd/mm/yyyy')  ";
			  
		  
		//  String queryInfo = "SELECT QRY_ID,MEM_BNK_ID , MEM_ZNE_ID, MEM_BRN_ID, MEM_PHONE_NUMBER, MEM_EMAIL, QUERY_DESC, QUERY_RAISED_BY, TRUNC(QUERY_RAISED_DT),QUERY_STATUS " +
			//"  FROM QUERY_MASTER where mem_bnk_id= '"+ bankId + "'  and mem_zne_id='"+ zoneId + "' and mem_brn_id= '"+ branchId + "' ";
		
		  
			  stmt = connection.createStatement();			
			 // //System.out.println("ÄA npaRegistQuery : "+queryInfo);
			  rs = stmt.executeQuery(queryInfo);
			  
			  while(rs.next())
			  {
				  queryDetails = new AdministrationActionForm();
				  
				  queryDetails.setQueryId(rs.getString(1));
				  
				  queryDetails.setBankId(rs.getString(2));
				  ////System.out.println("AA MLI Id : "+rs.getString(1));
				  
				  queryDetails.setZoneId(rs.getString(3));
				  ////System.out.println("AA Zone Name : "+rs.getString(2));
				  
				  queryDetails.setBranchId(rs.getString(4));
				  ////System.out.println("AA Fname : "+rs.getString(3));
				  
				  queryDetails.setContPerson(rs.getString(5));
				  ////System.out.println("AA Mname : "+rs.getString(4));
				  
				  queryDetails.setPhoneNo(rs.getString(6));
				  ////System.out.println("AA Lname : "+rs.getString(5));
				  
				  queryDetails.setEmailId(rs.getString(7));
				  ////System.out.println("AA EID : "+rs.getString(6));
				  
				  queryDetails.setDepartments(rs.getString(8));
				  ////System.out.println("AA Designation : "+rs.getString(7));
				  
				  queryDetails.setQueryDescription(rs.getString(9));
				  
				  queryDetails.setQueryStatus(rs.getString(10));
				  
				  queryDetails.setQueryResponse(rs.getString(11));
				  ////System.out.println("AA Phone No : "+rs.getString(8));
				  
				  
				  ////System.out.println("AA checker ID : "+rs.getString(10));
				  
				  queryDetailsList.add(queryDetails);
			  }
				  
					String forward = "";
					if (queryDetailsList == null || queryDetailsList.size() == 0) {
						request.setAttribute("message",
								"No Applications Available For Approval");
						forward = "success";
					} else {
						adminForm.setMliQueryList(queryDetailsList);
						forward = "queryList";
					}
					rs.close();
					rs = null;
					stmt.close();
					stmt = null;
					connection.close();
		 
		  return mapping.findForward(forward);
	  }
	
	
	
	  public ActionForward showQueryStatusReportInput(ActionMapping mapping, ActionForm form, 
              HttpServletRequest request, 
              HttpServletResponse response) throws Exception {
Log.log(4, "ReportsAction", "guaranteeCover", "Entered");

//DynaActionForm dynaForm = (DynaActionForm)form;

AdministrationActionForm adminForm = (AdministrationActionForm)form;
java.util.Date date = new java.util.Date();
Calendar calendar = Calendar.getInstance();
calendar.setTime(date);
int month = calendar.get(2);
int day = calendar.get(5);
month -= 1;
day += 1;
calendar.set(2, month);
calendar.set(5, day);
java.util.Date prevDate = calendar.getTime();

GeneralReport generalReport = new GeneralReport();
generalReport.setDateOfTheDocument(prevDate);
generalReport.setDateOfTheDocument1(date);
BeanUtils.copyProperties(adminForm, generalReport);
Log.log(4, "ReportsAction", "guaranteeCover", "Exited");

return mapping.findForward("success");
}


    
    private static final String dbDateSeparator = "-";
    private static final String stringDateSeparator = "/";

}

    

 
   

    
    
   // private static final String dbDateSeparator = "-";
    //private static final String stringDateSeparator = "/";

//}
