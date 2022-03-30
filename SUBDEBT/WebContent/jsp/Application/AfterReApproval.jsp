<%@ page language="java"%>
<%@ taglib uri="/WEB-INF/TLD/struts-html.tld" prefix="html" %>
<%@ taglib uri="/WEB-INF/TLD/struts-bean.tld" prefix="bean" %>
<%@ taglib uri="/WEB-INF/TLD/struts-logic.tld" prefix="logic" %>
<%@ page import="java.util.ArrayList"%>
<%@ page import="com.cgtsi.actionform.APForm"%>
<% session.setAttribute("CurrentPage","afterReApprovalApps.do?method=afterReApprovalApps");%>
<%@ include file="/jsp/SetMenuInfo.jsp" %>

<TABLE width="725" border="0" cellpadding="0" cellspacing="0">
	<html:errors />
	<html:form action="approvalApps.do?method=showAppsForApproval" method="POST">
		<TR> 
			<TD width="20" align="right" valign="bottom"><IMG src="images/TableLeftTop.gif" width="20" height="31"></TD>
			<TD background="images/TableBackground1.gif"></TD>
			<TD width="20" align="left" valign="bottom"><IMG src="images/TableRightTop.gif" width="23" height="31"></TD>
		</TR>
		<TR>
			<TD width="20" background="images/TableVerticalLeftBG.gif">&nbsp;</TD>
			<TD>
				<TABLE width="100%" border="0" align="left" cellpadding="0" cellspacing="0">
					<TR>
						<TD>
							<table width="661" border="0" cellspacing="1" cellpadding="0">
							 <TR>
								<TD colspan="4">
									<TABLE width="100%" border="0" cellpadding="0" cellspacing="0">
										<TR>
											<TD width="31%" class="Heading"><bean:message key="reApprovalHeader" /></TD>
											<TD><IMG src="images/TriangleSubhead.gif" width="19" height="19"></TD>
										</TR>
										<TR>
											<TD colspan="4" class="Heading"><IMG src="images/Clear.gif" width="5" height="5"></TD>
										</TR>
									</TABLE>
								</TD>
							</TR>
							</table>
							<%
								APForm apForm = (APForm)session.getAttribute("apForm");
								 if (apForm.getApprovedApplications().size()!=0)
								 {
							%>
							<tr>
									<td class="SubHeading" >
									<bean:message key="approvedApplications"/>
							</tr>
							<tr>
								<td align="center" class="tableData" width="29"><div align="center">
									<bean:message key = "cgpanNumber"/></div>
								  </td>
							</tr>
							<logic:iterate id="object" name="apForm" property="approvedApplications">
								<% com.cgtsi.application.Application approvedApp = (com.cgtsi.application.Application)object;

								String cgpan=approvedApp.getCgpan();
								%>
								<tr>
										 <td align="center" class="ColumnBackground" width="29"><div align="center"><%=cgpan%></div>
								</tr>
							</logic:iterate>
							<%}%>

							<%

								 if (apForm.getHoldApplications().size()!=0)
								 {
							%>
							<tr>
									<td class="SubHeading" >
									<bean:message key="holdApplications"/>
							</tr>
							<tr>
								<td align="center" class="tableData" width="29"><div align="center">
									<bean:message key = "cgpanNumber"/></div>
								  </td>
							</tr>
							<logic:iterate id="object" name="apForm" property="holdApplications">
								<% com.cgtsi.application.Application holdApp = (com.cgtsi.application.Application)object;

								String cgpan=holdApp.getCgpan();
								%>
								<tr>
										 <td align="center" class="ColumnBackground" width="29"><div align="center"><%=cgpan%></div>
								</tr>
							</logic:iterate>
							<%}%>

							<%

								 if (apForm.getRejectedApplications().size()!=0)
								 {
							%>
							<tr>
									<td class="SubHeading" >
									<bean:message key="rejectedApplications"/>
							</tr>
							<tr>
								<td align="center" class="tableData" width="29"><div align="center">
									<bean:message key = "cgpanNumber"/></div>
								  </td>
							</tr>
							<logic:iterate id="object" name="apForm" property="rejectedApplications">
								<% com.cgtsi.application.Application rejectedApp = (com.cgtsi.application.Application)object;

								String cgpan=rejectedApp.getCgpan();
								%>
								<tr>
										 <td align="center" class="ColumnBackground" width="29"><div align="center"><%=cgpan%></div>
								</tr>
							</logic:iterate>
							<%}%>				

														<TR >
								<TD height="20" >
									&nbsp;
								</TD>
							</TR>
							<TR >
								<TD align="center" valign="baseline" >
									<DIV align="center">
										<!--<A href="javascript:document.appForm.reset()">
											<IMG src="images/Reset.gif" alt="Cancel" width="49" height="37" border="0"></A>-->
								<A href="home.do?method=getMainMenu&menuIcon=<%=session.getAttribute("menuIcon")%>&mainMenu=<%=session.getAttribute("mainMenu")%>">
											<IMG src="images/OK.gif" alt="OK" width="49" height="37" border="0"></A>
										<!--<A href="subHome.do?method=getSubMenu&menuIcon=<%=session.getAttribute("menuIcon")%>&mainMenu=<%=session.getAttribute("mainMenu")%>">
										<IMG src="images/Cancel.gif" alt="Cancel" width="49" height="37" border="0"></A>-->
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

											
										  
										  
											




