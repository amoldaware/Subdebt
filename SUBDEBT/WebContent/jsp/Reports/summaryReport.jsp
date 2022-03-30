<%@ page language="java"%>
<%@ page import="java.util.ArrayList"%>
<%@ page import="java.text.SimpleDateFormat"%>
<%@ page import="java.util.Date"%>
<%@ page import="com.cgtsi.actionform.ReportActionForm"%>
<%@ page import="java.text.DecimalFormat"%>
<%@ page import="com.cgtsi.claim.ClaimConstants"%>
<%@ taglib uri="/WEB-INF/TLD/struts-html.tld" prefix="html" %>
<%@ taglib uri="/WEB-INF/TLD/struts-bean.tld" prefix="bean" %>
<%@ taglib uri="/WEB-INF/TLD/struts-logic.tld" prefix="logic" %>
<%@ include file="/jsp/SetMenuInfo.jsp" %>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Insert title here</title>
</head>
<TABLE width="100%" border="0" cellpadding="0" cellspacing="0">
	<html:errors />
	<html:form  name = "reportForm" type="com.cgtsi.actionform.ReportActionForm" action="displaySummaryReport.do?method=getSummaryReport" method="POST" >
	<tr>
	<TD align="left" valign="top" class="ColumnBackground"><font color="#FF0000" size="2">*</font>&nbsp;Start Date:</TD>
	<TD align="left" valign="top" class="tableData">
						<div align="left">
							<html:text property="dtFromDate" size="20" maxlength="10" alt="Reference" name="reportForm"/>
							<IMG src="images/CalendarIcon.gif" width="20" onClick="showCalendar('reportForm.dtFromDate')" align="center">
						</div>
					</TD>
	<TD align="left" valign="top" class="ColumnBackground"><font color="#FF0000" size="2">*</font>&nbsp;End Date:</TD>
	<TD align="left" valign="top" class="tableData">
						<div align="left">
							<html:text property="dtToDate" size="20" maxlength="10" alt="Reference" name="reportForm"/>
							<IMG src="images/CalendarIcon.gif" width="20" onClick="showCalendar('reportForm.dtToDate')" align="center">
						</div>
					</TD>
					<TD align="left" valign="top" class="tableData">
					<div align="left">
					<select name="type" id="type">
  							<option value="MliWise">Mli Wise</option>
 							<option value="StateWise">State Wise</option>
  							<option value="StateMonthWise">State and Month Wise</option>
</select>
								</div>
								</TD>
					<TD align="right" valign="top" class="tableData">
						<div class="form-group mb-100" style="text-align:center;">
						<A href="javascript:submitForm('displaySummaryReport.do?method=getSummaryReport&val=download');"
						style="padding:6px 13px; background:#1c4593; color:white;border-radius:4px;font-size:13px;">
								Download
						</A>
						</div>
					</TD>
	</tr>
</html:form>
</TABLE>
</html>