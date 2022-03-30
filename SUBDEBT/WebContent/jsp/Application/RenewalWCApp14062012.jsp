<%@ page language="java"%>
<%@ page import="com.cgtsi.util.SessionConstants"%>
<%@ taglib uri="/WEB-INF/TLD/struts-html.tld" prefix="html" %>
<%@ taglib uri="/WEB-INF/TLD/struts-bean.tld" prefix="bean" %>
<% if(session.getAttribute(SessionConstants.APPLICATION_TYPE_FLAG)!=null)
{
if(session.getAttribute(SessionConstants.APPLICATION_TYPE_FLAG).equals("2"))
{
session.setAttribute("CurrentPage","afterEnterApp.do?method=showCgpanList&flag=1");
}
};%>

<%@ include file="/jsp/SetMenuInfo.jsp" %>

<body onLoad="setConstEnabled(),enableNone(),enableDistrictOthers(),enableOtherLegalId(),enableSubsidyName()">

<TABLE width="725" border="0" cellpadding="0" cellspacing="0">
	<html:errors />
	<html:form action="afterWcRenewalApp.do" method="POST" focus="mliRefNo">
		<TR> 
			<TD width="20" align="right" valign="bottom"><IMG src="images/TableLeftTop.gif" width="20" height="31"></TD>
			<TD background="images/TableBackground1.gif"><IMG src="images/ApplicationProcessingHeading.gif" width="91" height="31"></TD>
			<TD width="20" align="left" valign="bottom"><IMG src="images/TableRightTop.gif" width="23" height="31"></TD>
		</TR>
		<TR>
			<TD width="20" background="images/TableVerticalLeftBG.gif">&nbsp;</TD>
			<TD>
				<DIV align="right">			
					<A HREF="javascript:submitForm('renewalHelp.do?method=renewalHelp')">
					HELP</A>
				</DIV>

					<%@include file="CommonAppDetails.jsp"%>
					<%@include file="RenewalWCDetails.jsp"%>
					<%@include file="SecuritizationDetails.jsp"%>
					<%
							if (session.getAttribute(SessionConstants.MCGF_FLAG).equals("M"))
							{%>
								<%@include file="MCGFDetails.jsp"%>
							<%}
							%>
				<TABLE width="100%" border="0" align="left" cellpadding="0" cellspacing="1">
					<tr align="left">
						<td class="ColumnBackground" height="28" width="252">&nbsp;
							<bean:message key="existingRemarks"/>
						</td>
						<td class="TableData" height="28" colspan="4" width="578">

							<%
							String appFlag=(String)session.getAttribute(SessionConstants.APPLICATION_TYPE_FLAG);
							if(appFlag.equals("2"))
							{
							%>
							<bean:write name="appForm" property="existingRemarks"/>
							<%}
							else
							{ %>
							<html:textarea property="remarks" cols="35" alt="address" name="appForm"/>	
							<%}%>
						</td>
					</tr>
					<tr align="left">
						<td class="ColumnBackground" height="28">&nbsp;
							<bean:message key="remarks"/>
						</td>
						<td class="TableData" height="28" colspan="4">
							<html:textarea property="remarks" cols="35" alt="address" name="appForm"/>	
						</td>
					</tr>

					
					<TR >
						<TD align="center" valign="baseline" colspan="6">
							<DIV align="center">
								<A href="javascript:submitForm('afterWcRenewalApp.do?method=afterWcRenewalApp')"><IMG src="images/Save.gif" alt="Save" width="49" height="37" border="0"></A>		
								<A href="javascript:document.appForm.reset()">
									<IMG src="images/Reset.gif" alt="Reset" width="49" height="37" border="0"></A>
								<A href="subHome.do?method=getSubMenu&menuIcon=<%=session.getAttribute("menuIcon")%>&mainMenu=<%=session.getAttribute("mainMenu")%>">
								<IMG src="images/Cancel.gif" alt="Cancel" width="49" height="37" border="0"></A>
							</DIV>
						</TD>
					</TR>					
				</TABLE>
			</TD>
			<TD width="20" background="images/TableVerticalRightBG.gif">
				&nbsp;
			</TD>
		</TR>
		<TR>
			<TD width="20" align="right" valign="top">
				<IMG src="images/TableLeftBottom1.gif" width="20" height="15">
			</TD>
			<TD background="images/TableBackground2.gif">
				&nbsp;
			</TD>
			<TD width="20" align="left" valign="top">
				<IMG src="images/TableRightBottom1.gif" width="23" height="15">
			</TD>
		</TR>
	</html:form>
</TABLE>







					