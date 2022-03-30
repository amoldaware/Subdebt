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
	<html:form  name = "reportForm" type="com.cgtsi.actionform.ReportActionForm" action="detailSummaryReport.do?method=getDetailSummaryReport" method="POST" >
	<tr>
					<TD align="left" valign="top" class="ColumnBackground"><font color="#FF0000" size="2"></font>&nbsp;State Name:
					<html:text property="stateName" styleId="statename"
					size="20" alt="stateName" maxlength="15" name="reportForm" />
			</td>
			<TD align="left" valign="top" class="ColumnBackground"><font color="#FF0000" size="2"></font>&nbsp;Bank Name:
					<html:text property="bankName" styleId="bankname"
					size="20" alt="bankName" maxlength="50" name="reportForm" />
			</td>
			<TD align="left" valign="top" class="ColumnBackground"><font color="#FF0000" size="2"></font>&nbsp;Month:
					<html:text property="month" styleId="month"
					size="20" alt="bankName" maxlength="15" name="reportForm" />
			</td>
	
	</tr>
	<TR >
						<TD align="center" valign="baseline" >
							<DIV align="center">
							<A href="javascript:submitForm('detailSummaryReport.do?method=getDetailSummaryReport&val=download')">
									Export To Excel</A>
								<A href="javascript:document.reportForm.reset()">
									<IMG src="images/Reset.gif" alt="Reset" width="49" height="37" border="0"></A>
									<a href="subHome.do?method=getSubMenu&menuIcon=<%=session.getAttribute("menuIcon")%>&mainMenu=<%=session.getAttribute("mainMenu")%>">
									<IMG src="images/Cancel.gif" alt="Cancel" width="49" height="37" border="0"></A>
								
							</DIV>
						</TD>
					</TR>
	
</html:form>
</TABLE>
</html>