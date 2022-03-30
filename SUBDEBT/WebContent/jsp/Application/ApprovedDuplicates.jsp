<%@ page language="java"%>
<%@ taglib uri="/WEB-INF/TLD/struts-html.tld" prefix="html" %>
<%@ taglib uri="/WEB-INF/TLD/struts-bean.tld" prefix="bean" %>
<%@ taglib uri="/WEB-INF/TLD/struts-logic.tld" prefix="logic" %>
<%@ page import="com.cgtsi.actionform.APForm"%>
<%@ page import="java.util.ArrayList"%>
<% session.setAttribute("CurrentPage","updateApplicationStatus.do?method=checkDuplicate.do");%>
<%@ include file="/jsp/SetMenuInfo.jsp" %>

<TABLE width="725" border="0" cellpadding="0" cellspacing="0">
	<html:errors />
	<html:form action="afterCheckDuplicate.do" method="POST">
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
											<TD width="31%" class="Heading"><bean:message key="probableDuplicatesHeader" /></TD>
											<TD><IMG src="images/TriangleSubhead.gif" width="19" height="19"></TD>
										</TR>
										<TR>
											<TD colspan="4" class="Heading"><IMG src="images/Clear.gif" width="5" height="5"></TD>
										</TR>
									</TABLE>
								</TD>
							</TR>
							</table>
							<!--<tr>
								<td class="SubHeading" colspan="4">
									<bean:message key="probableDuplicates"/>
							</tr>-->
							<%
								int i=0;
								APForm apForm = (APForm)session.getAttribute("apForm");

								if (apForm.getApprovedApplications().size()!=0)
								{
							%>
								<tr>
									<td class="SubHeading" colspan="4">
									<bean:message key="acceptedApplications"/>
								</tr>
								<tr>
									<td>

										<TABLE width="100%" border="0" cellpadding="1" cellspacing="1">
											<tr> 
											  <td align="center" class="HeadingBg" width="15%"><div align="center"><bean:message key = "srNo"/></div>
											  </td>
											  <td class="HeadingBg" align="center"><div align="center"><bean:message key="applicationRefNo"/></div>
											  </td>
											</tr>
											<logic:iterate id="object" name="apForm" property="approvedApplications">
											<%
												com.cgtsi.application.Application acceptedapplication=(com.cgtsi.application.Application)object;
												String applicationRefNo=acceptedapplication.getAppRefNo();
											%>
											<tr>
												<td class="tableData" align="center">&nbsp;<%=++i%>
												</td>
												<td class="ColumnBackground" align="center">
													<%=applicationRefNo%>
												</td>
											</tr>
											</logic:iterate>
										</table>
									</td>
								</tr>
								<%}%>

								<%
								

								if (apForm.getDuplicateApplication().size()!=0)
								{									
							%>
								<tr>
									<td class="SubHeading" colspan="4">
									<bean:message key="duplicateApplications"/>
								</tr>
								<tr>
									<td>

										<TABLE width="100%" border="0" cellpadding="1" cellspacing="1">
											<tr> 
											  <td align="center" class="HeadingBg" width="15%"><div align="center"><bean:message key = "srNo"/></div>
											  </td>
											  <td class="HeadingBg" align="center"><div align="center"><bean:message key="applicationRefNo"/></div>
											  </td>
											</tr>
											<logic:iterate id="object" name="apForm" property="duplicateApplication">
											<%											
											 com.cgtsi.application.Application dupapplication=(com.cgtsi.application.Application)object;
												
												String applicationRefNo=dupapplication.getAppRefNo();
												
											%>
											<tr>
												<td class="tableData" align="center">&nbsp;<%=++i%>
												</td>
												<td class="ColumnBackground" align="center">
													<%=applicationRefNo%>
												</td>
											</tr>
											</logic:iterate>
										</table>
									</td>
								</tr>
								<%}%>

								<%
								

								if (apForm.getRejectedApplications().size()!=0)
								{
							%>
								<tr>
									<td class="SubHeading" colspan="4">
									<bean:message key="rejectedApplications"/>
								</tr>
								<tr>
									<td>

										<TABLE width="100%" border="0" cellpadding="1" cellspacing="1">
											<tr> 
											  <td align="center" class="HeadingBg" width="15%"><div align="center"><bean:message key = "srNo"/></div>
											  </td>
											  <td class="HeadingBg" align="center"><div align="center"><bean:message key="applicationRefNo"/></div>
											  </td>
											</tr>
											<logic:iterate id="object" name="apForm" property="rejectedApplications">
											<%
												com.cgtsi.application.Application rejapplication=(com.cgtsi.application.Application)object;
												String applicationRefNo=rejapplication.getAppRefNo();
											%>
											<tr>
												<td class="tableData" align="center">&nbsp;<%=++i%>
												</td>
												<td class="ColumnBackground" align="center">
													<%=applicationRefNo%>
												</td>
											</tr>
											</logic:iterate>
										</table>
									</td>
								</tr>
								<%}%>

								<%
								
								if (apForm.getHoldApplications().size()!=0)
								{
							%>
								<tr>
									<td class="SubHeading" colspan="4">
									<bean:message key="holdApplications"/>
								</tr>
								<tr>
									<td>

										<TABLE width="100%" border="0" cellpadding="1" cellspacing="1">
											<tr> 
											  <td align="center" class="HeadingBg" width="15%"><div align="center"><bean:message key = "srNo"/></div>
											  </td>
											  <td class="HeadingBg" align="center"><div align="center"><bean:message key="applicationRefNo"/></div>
											  </td>
											</tr>
											<logic:iterate id="object" name="apForm" property="holdApplications">
											<%
												com.cgtsi.application.Application holdApplication=(com.cgtsi.application.Application)object;
												String applicationRefNo=holdApplication.getAppRefNo();
											%>
											<tr>
												<td class="tableData" align="center">&nbsp;<%=++i%>
												</td>
												<td class="ColumnBackground" align="center">
													<%=applicationRefNo%>
												</td>
											</tr>
											</logic:iterate>
										</table>
									</td>
								</tr>
								<%}%>

								

								<TR >
								<TD height="20" >
									&nbsp;
								</TD>
							</TR>
							<TR >
								<TD align="center" valign="baseline" >
									<DIV align="center">										
											<A href="javascript:submitForm('updateApplicationStatus.do?method=updateApplicationStatus')">
											<IMG src="images/OK.gif" alt="OK" width="49" height="37" border="0"></A>
										<A href="subHome.do?method=getSubMenu&menuIcon=<%=session.getAttribute("menuIcon")%>&mainMenu=<%=session.getAttribute("mainMenu")%>">
										<IMG src="images/Cancel.gif" alt="Cancel" width="49" height="37" border="0"></A>
										<A href="javascript:submitForm('updateApplicationStatus.do?method=updateApplicationStatus')">
											<IMG src="images/Print.gif" alt="OK" width="49" height="37" border="0"></A>
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

								


