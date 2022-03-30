<%@ page language="java"%>
<%@ taglib uri="/WEB-INF/TLD/struts-html.tld" prefix="html" %>
<%@ taglib uri="/WEB-INF/TLD/struts-bean.tld" prefix="bean" %>
<%@ taglib uri="/WEB-INF/TLD/struts-logic.tld" prefix="logic" %>
<%@ page import="java.util.ArrayList"%>
<%@ page import="com.cgtsi.actionform.APForm"%>
<% session.setAttribute("CurrentPage","afterTcConversion.do?method=afterTcConversion");%>
<%@ include file="/jsp/SetMenuInfo.jsp" %>

<TABLE width="725" border="0" cellpadding="0" cellspacing="0">
	<html:errors />
	<html:form action="afterWcConversion.do?method=afterWcConversion" method="POST">
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
								<TD colspan="5">
									<TABLE width="100%" border="0" cellpadding="0" cellspacing="0">
										<TR>
											<TD width="31%" class="Heading"><bean:message key="tcConvertedApps" /></TD>
											
										</TR>
									</TABLE>
								</TD>
							</TR>
							</table>
							<%
								APForm apForm = (APForm)session.getAttribute("apForm");
								
								 if (apForm.getRenewConvertedApplications().size()!=0)
								 {
							%>
							<TABLE width="100%" border="0" cellpadding="0" cellspacing="1">
							<tr>
									<td class="SubHeading" colspan="3">
									Renewal Applications</td>
							</tr>

							<tr>
												<td class="HeadingBg"><div align="center">
														Application Reference Number
														</div>
												</td>

												<td class="HeadingBg"><div align="center">
														Link CGPAN
														</div>
												</td>
							</tr>
							<logic:iterate id="object" name="apForm" property="renewConvertedApplications">
								<% com.cgtsi.application.Application wcConverted = (com.cgtsi.application.Application)object;

								String appRefNo = wcConverted.getAppRefNo();
								String cgpan=wcConverted.getCgpan();
								%>
								<tr>
										 <td align="center" class="ColumnBackground" width="29"><div align="center"><%=appRefNo%></div></td>
										 <td align="center" class="ColumnBackground" width="29"><div align="center"><%=cgpan%></div></td>
								</tr>
							</logic:iterate>
							</table>
							<%}%>


							<%
								 if (apForm.getEnhanceConvertedApplications().size()!=0)
								 {
							%>
							<TABLE width="100%" border="0" cellpadding="0" cellspacing="1">
							<tr>
									<td class="SubHeading" colspan="3">
									Enhanced Applications</td>
							</tr>

							<tr>
												<td class="HeadingBg"><div align="center">
														Application Reference Number
														</div>
												</td>

												<td class="HeadingBg"><div align="center">
														Link CGPAN
														</div>
												</td>
							</tr>
							<logic:iterate id="object" name="apForm" property="enhanceConvertedApplications">
								<% com.cgtsi.application.Application wcConverted = (com.cgtsi.application.Application)object;

								String appRefNo = wcConverted.getAppRefNo();
								String cgpan=wcConverted.getCgpan();
								%>
								<tr>
										 <td align="center" class="ColumnBackground" width="29"><div align="center"><%=appRefNo%></div></td>
										 <td align="center" class="ColumnBackground" width="29"><div align="center"><%=cgpan%></div></td>
								</tr>
							</logic:iterate>
							</table>
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
											<A href="subHome.do?method=getSubMenu&menuIcon=<%=session.getAttribute("menuIcon")%>&mainMenu=<%=session.getAttribute("mainMenu")%>">
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

											
										  
										  
											




