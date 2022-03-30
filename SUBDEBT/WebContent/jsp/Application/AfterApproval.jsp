<%@ page language="java"%>
<%@ taglib uri="/WEB-INF/TLD/struts-html.tld" prefix="html" %>
<%@ taglib uri="/WEB-INF/TLD/struts-bean.tld" prefix="bean" %>
<%@ taglib uri="/WEB-INF/TLD/struts-logic.tld" prefix="logic" %>
<%@ page import="java.util.ArrayList"%>
<%@ page import="com.cgtsi.actionform.APForm"%>
<% session.setAttribute("CurrentPage","afterApprovalApps.do?method=afterApprovalApps");%>
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
											<TD width="31%" class="Heading"><bean:message key="approvalHeader" /></TD>
											<TD><IMG src="images/TriangleSubhead.gif" width="19" height="19"></TD>
										</TR>
										<TR>
											<TD colspan="4" class="Heading"><IMG src="images/Clear.gif" width="5" height="5"></TD>
										</TR>
									</TABLE>
								</TD>
							</TR>
							</table>

				<!--Approved Applications-->
							<%
							APForm apForm = (APForm)session.getAttribute("apForm");

								if (apForm.getClearApprovedApplications().size()!=0 || apForm.getDupApprovedApplications().size()!=0 || apForm.getIneligibleApprovedApplications().size()!=0 || apForm.getIneligibleDupApprovedApplications().size()!=0)
								{
							%>
							<tr>
									<td class="SubHeading" colspan="3">
									<bean:message key="approvedApplications"/></td>
							</tr>
<!--							<tr>
								<td align="center" class="HeadingBg" width="30" colspan="3">
							<table width="100%" border="0" cellspacing="1" cellpadding="0">
							<tr>
-->
							<!--Clear Approved Apps-->
							<%
								if (apForm.getClearApprovedApplications().size()!=0)
								{
							%>
								<tr>
								 <!--<td align="center" class="HeadingBg" width="30"><div align="center">
									<table width="100%" border="0" cellspacing="1" cellpadding="0">
										<tr>-->
											<td align="center" class="HeadingBg" colspan="2">
												<bean:message key = "clearApplications"/></div>
											</td>
										 </tr>

									<tr>
									   <td align="left" class="tableData"><div align="left">
											<bean:message key = "applicationRefNo"/></div>
									  </td>

									   <td align="left" class="tableData"><div align="left">
											<bean:message key = "cgpanNumber"/></div>
									  </td>
									</tr>


									<logic:iterate id="object" name="apForm" property="clearApprovedApplications">
								<%
							   com.cgtsi.application.Application clearApprovedApp = (com.cgtsi.application.Application)object;

								String appRefNo=clearApprovedApp.getAppRefNo();
								String cgpan=clearApprovedApp.getCgpan();
								%>	
								<tr>
								<%
								if(cgpan==null || cgpan.equals(""))
								{
								cgpan="";
								}

								%>
									 <td align="center" class="ColumnBackground"><div align="center"><%=appRefNo%></div>
									 <td align="center" class="ColumnBackground"><div align="center"><%=cgpan%></div>

								</tr>
								</logic:iterate>
<!--							</table>
							</td>
							</tr>
							
	-->							<%}%>

								<!--Duplicate Approved Apps-->

								<%
								if (apForm.getDupApprovedApplications().size()!=0)
								{
							%>
							<tr>
								<!-- <td align="center" class="HeadingBg" width="30"><div align="center">
									<table width="100%" border="0" cellspacing="1" cellpadding="0">
										<tr>-->
											<td align="center" class="HeadingBg" colspan="2">
												<bean:message key = "probableDuplicateApps"/></div>
											</td>
								 </tr>

									<tr>
									   <td align="center" class="tableData" ><div align="center">
											<bean:message key = "applicationRefNo"/></div>
									  </td>
									   <td align="center" class="tableData"><div align="center">
											<bean:message key = "cgpanNumber"/></div>
									  </td>
									</tr>
								<logic:iterate id="object" name="apForm" property="dupApprovedApplications">
								<%
							   com.cgtsi.application.Application dupApprovedApp = (com.cgtsi.application.Application)object;

								String appRefNo=dupApprovedApp.getAppRefNo();
								String cgpan=dupApprovedApp.getCgpan();

								%>	
								<tr>
								<%
									if(cgpan==null || cgpan.equals(""))
									{
										cgpan="";										
									}
								%>

									 <td align="center" class="ColumnBackground"><div align="center"><%=appRefNo%></div>
									 <td align="center" class="ColumnBackground"><div align="center"><%=cgpan%></div>

								</tr>
								</logic:iterate>
<!--								</table>
							</td>
							</tr>
	-->							<%}%>

								<!--Ineligible Applications-->

								<%
								if (apForm.getIneligibleApprovedApplications().size()!=0)
								{
								%>
								<tr>
<!--							  <td align="center" class="HeadingBg" width="30"><div align="center">
									<table width="100%" border="0" cellspacing="1" cellpadding="0">
										<tr>-->
											<td align="center" class="HeadingBg" colspan="2">
												<bean:message key = "probableIneligibleApps"/></div>
											</td>
								 </tr>

									<tr>
									   <td align="center" class="tableData"><div align="center">
											<bean:message key = "applicationRefNo"/></div>
									  </td>
									   <td align="center" class="tableData" ><div align="center">
											<bean:message key = "cgpanNumber"/></div>
									  </td>
									</tr>
								<logic:iterate id="object" name="apForm" property="ineligibleApprovedApplications">
								<%
							   com.cgtsi.application.Application ineligibleApprovedApp = (com.cgtsi.application.Application)object;

								String appRefNo=ineligibleApprovedApp.getAppRefNo();
								String cgpan=ineligibleApprovedApp.getCgpan();

								%>	
								<tr>
								<%
								if(cgpan==null || cgpan.equals(""))
								{
								cgpan="";
								}

								%>

									 <td align="center" class="ColumnBackground" ><div align="center"><%=appRefNo%></div>
									 <td align="center" class="ColumnBackground" ><div align="center"><%=cgpan%></div>

								</tr>
								</logic:iterate>
<!--								</table>
							</td>
							</tr>
	-->							<%}%>

								<!--Ineligible Duplicate Approved Apps-->

								<%
								if (apForm.getIneligibleDupApprovedApplications().size()!=0)
								{
								%>
								<tr>
<!--							<td align="center" class="HeadingBg" width="30"><div align="center">
									<table width="100%" border="0" cellspacing="1" cellpadding="0">
										<tr>-->
											<td align="center" class="HeadingBg" colspan="2">									
												<bean:message key = "probableDuplicateInEligibleApps"/></div>
											</td>
										 </tr>

									<tr>
									   <td align="center" class="tableData"><div align="center">
											<bean:message key = "applicationRefNo"/></div>
									  </td>
									   <td align="center" class="tableData" ><div align="center">
											<bean:message key = "cgpanNumber"/></div>
									  </td>
									</tr>
								<logic:iterate id="object" name="apForm" property="ineligibleDupApprovedApplications">
								<%
							   com.cgtsi.application.Application ineligibleDupApprovedApp = (com.cgtsi.application.Application)object;

								String appRefNo=ineligibleDupApprovedApp.getAppRefNo();
								String cgpan=ineligibleDupApprovedApp.getCgpan();

								%>	
								<tr>
								<%
								if(cgpan==null || cgpan.equals(""))
								{
								cgpan="";
								}

								%>

									 <td align="center" class="ColumnBackground" ><div align="center"><%=appRefNo%></div>
									 <td align="center" class="ColumnBackground" ><div align="center"><%=cgpan%></div>

								</tr>
								</logic:iterate>
<!--								</table>
							</td>
							</tr>
	-->							<%}%>
								 

							</tr>
							
<!--							</table>
							</td>
	-->						</tr>
							<%}%>

				<!--Hold Applications-->
						<%

								if (apForm.getClearHoldApplications().size()!=0 || apForm.getDupHoldApplications().size()!=0 || apForm.getIneligibleHoldApplications().size()!=0 || apForm.getIneligibleDupHoldApplications().size()!=0)
								{
							%>
							<tr>
									<td class="SubHeading" colspan="4">
									<bean:message key="holdApplications"/>
							</tr>
							<tr>
						<!--		<td align="center" class="HeadingBg" width="30" colspan="3">
									<table width="100%" border="0" cellspacing="1" cellpadding="0">
										<tr>-->


							<!--Clear Hold Apps-->
							<%
								if (apForm.getClearHoldApplications().size()!=0)
								{
							%>
							<tr>
	<!--					 <td align="center" class="HeadingBg" width="50%"><div align="center">
							<table width="100%" border="0" cellspacing="1" cellpadding="0" colspan="2">
										<tr>-->
											<td align="center" class="HeadingBg" colspan="2">
												<bean:message key = "clearApplications"/></div>
											</td>
										 </tr>
								<logic:iterate id="object" name="apForm" property="clearHoldApplications">
								<%
							   com.cgtsi.application.Application clearHoldApp = (com.cgtsi.application.Application)object;

								String appRefNo=clearHoldApp.getAppRefNo();							

								%>	
								<tr>
									 <td align="center" class="ColumnBackground" colspan="2"><div align="center"><%=appRefNo%></div>
								</tr>
								</logic:iterate>
<!--								</table>
								</td>
								
	-->							<%}%>

								<!--Duplicate Hold Apps-->

								<%
								if (apForm.getDupHoldApplications().size()!=0)
								{
							%>
							<tr>
<!--				 <td align="center" class="HeadingBg" width="30%" colspan="2"><div align="center">
									<table width="100%" border="0" cellspacing="1" cellpadding="0">
										<tr>-->
											<td align="center" class="HeadingBg" colspan="2">
												<bean:message key = "probableDuplicateApps"/></div>
											</td>
										 </tr>
								<logic:iterate id="object" name="apForm" property="dupHoldApplications">
								<%
							   com.cgtsi.application.Application dupHoldApp = (com.cgtsi.application.Application)object;

								String appRefNo=dupHoldApp.getAppRefNo();						

								%>	
								<tr>
									 <td align="center" class="ColumnBackground" colspan="2"><div align="center"><%=appRefNo%></div>

								</tr>
								</logic:iterate>
<!--								</table>
								</td>
								
	-->							<%}%>

								<!--Ineligible Applications-->

								<%
								if (apForm.getIneligibleHoldApplications().size()!=0)
								{
								%>
								<tr>
<!--							 <td align="center" class="HeadingBg" width="30" ><div align="center">
									<table width="100%" border="0" cellspacing="1" cellpadding="0">
										<tr>-->
											<td align="center" class="HeadingBg" colspan="2">
												<bean:message key = "probableIneligibleApps"/></div>
											</td>
										 </tr>
								<logic:iterate id="object" name="apForm" property="ineligibleHoldApplications">
								<%
							   com.cgtsi.application.Application ineligibleHoldApp = (com.cgtsi.application.Application)object;

								String appRefNo=ineligibleHoldApp.getAppRefNo();
								
								%>	
								<tr>
									 <td align="center" class="ColumnBackground" colspan="2"><div align="center"><%=appRefNo%></div>
									

								</tr>
								</logic:iterate>
<!--								</table>
								</td>
								
	-->							<%}%>

								<!--Ineligible Duplicate Apps-->

								<%
								if (apForm.getIneligibleDupHoldApplications().size()!=0)
								{
								%>
								<tr>
<!--							 <td align="center" class="HeadingBg" width="30%"><div align="center">
									<table width="100%" border="0" cellspacing="1" cellpadding="0">
										<tr>-->
											<td align="center" class="HeadingBg" colspan="2">
												<bean:message key = "probableIneligibleApps"/></div>
											</td>
										 </tr>
								<logic:iterate id="object" name="apForm" property="ineligibleDupHoldApplications">
								<%
							   com.cgtsi.application.Application ineligibleDupHoldApp = (com.cgtsi.application.Application)object;

								String appRefNo=ineligibleDupHoldApp.getAppRefNo();
								
								%>	
								<tr>
									 <td align="center" class="ColumnBackground" colspan="2"><div align="center"><%=appRefNo%></div>							 

								</tr>
								</logic:iterate>
<!--								</table>
								</td>
	-->							
								<%}%>				 

<!--							</tr>
							</table>
							</td>
	-->						</tr>
							<%}%>

				<!--Rejected Applications-->
						<%

								if (apForm.getClearRejectedApplications().size()!=0 || apForm.getDupRejectedApplications().size()!=0 || apForm.getIneligibleRejectedApplications().size()!=0 || apForm.getIneligibleDupRejectedApplications().size()!=0)
								{
							%>
							<tr>
									<td class="SubHeading" colspan="4">
									<bean:message key="rejectedApplications"/>
							</tr>
							<tr>
		<!--						<td align="center" class="HeadingBg" width="30" colspan="3">
							<table width="100%" border="0" cellspacing="1" cellpadding="0">
							<tr>
-->
							<!--Clear Rejected Apps-->
							<%
								if (apForm.getClearRejectedApplications().size()!=0)
								{
							%>
							<tr>
<!--							 <td align="center" class="HeadingBg" width="30"><div align="center">
									<table width="100%" border="0" cellspacing="1" cellpadding="0">
										<tr>-->
											<td align="center" class="HeadingBg" colspan="2">
												<bean:message key = "clearApplications"/></div>
											</td>
										 </tr>
								 <logic:iterate id="object" name="apForm" property="clearRejectedApplications">
								<%
							   com.cgtsi.application.Application clearRejectedApp = (com.cgtsi.application.Application)object;

								String appRefNo=clearRejectedApp.getAppRefNo();							

								%>	
								<tr>
									 <td align="center" class="ColumnBackground" colspan="2"><div align="center"><%=appRefNo%></div>
								</tr>
								</logic:iterate>
<!--								</table>
								</td>
								</tr>
	-->							<%}%>

								<!--Duplicate Rejceted Apps-->

								<%
								if (apForm.getDupRejectedApplications().size()!=0)
								{
							%>
							<tr>
<!--						 <td align="center" class="HeadingBg" width="30"><div align="center">
									<table width="100%" border="0" cellspacing="1" cellpadding="0">
										<tr>-->
											<td align="center" class="HeadingBg" colspan="2">
												<bean:message key = "probableDuplicateApps"/></div>
											</td>
										 </tr>
										 <logic:iterate id="object" name="apForm" property="dupRejectedApplications">
								<%
							   com.cgtsi.application.Application dupRejectedApp = (com.cgtsi.application.Application)object;

								String appRefNo=dupRejectedApp.getAppRefNo();						

								%>	
								<tr>
									 <td align="center" class="ColumnBackground" colspan="2"><div align="center"><%=appRefNo%></div>

								</tr>
								</logic:iterate>
<!--								</table>
								</td>
								</tr>
	-->							<%}%>

								<!--Ineligible Rejected Applications-->

								<%
								if (apForm.getIneligibleRejectedApplications().size()!=0)
								{
								%>
								<tr>
<!--							 <td align="center" class="HeadingBg" width="30"><div align="center">
									<table width="100%" border="0" cellspacing="1" cellpadding="0">
										<tr>-->
											<td align="center" class="HeadingBg" colspan="2">
												<bean:message key = "probableIneligibleApps"/></div>
											</td>
										 </tr>
								<logic:iterate id="object" name="apForm" property="ineligibleRejectedApplications">
								<%
							   com.cgtsi.application.Application ineligibleRejectedApp = (com.cgtsi.application.Application)object;

								String appRefNo=ineligibleRejectedApp.getAppRefNo();
								
								%>	
								<tr>
									 <td align="center" class="ColumnBackground" colspan="2"><div align="center"><%=appRefNo%></div>
									

								</tr>
								</logic:iterate>
<!--								</table>
								</td>
								</tr>
	-->							<%}%>

								<!--Ineligible Duplicate Rejected Apps-->

								<%
								if (apForm.getIneligibleDupRejectedApplications().size()!=0)
								{
								%>
								<tr>
<!--							 <td align="center" class="HeadingBg" width="30"><div align="center">
									<table width="100%" border="0" cellspacing="1" cellpadding="0">
										<tr>-->
											<td align="center" class="HeadingBg" colspan="2">
												<bean:message key = "probableDuplicateInEligibleApps"/></div>
											</td>
										 </tr>
								<logic:iterate id="object" name="apForm" property="ineligibleDupRejectedApplications">
								<%
							   com.cgtsi.application.Application ineligibleDupRejectedApp = (com.cgtsi.application.Application)object;

								String appRefNo=ineligibleDupRejectedApp.getAppRefNo();
								
								%>	
								<tr>
									 <td align="center" class="ColumnBackground" colspan="2"><div align="center"><%=appRefNo%></div>							 

								</tr>
								</logic:iterate>
<!--								</table>
								</td>
								</tr>
	-->							<%}%>
								 
<!--							</tr>
							</table>
							</td>
	-->						</tr>
							<%}%>

				<!--Pending Applications-->
						<%

								if (apForm.getClearPendingApplications().size()!=0 || apForm.getDupPendingApplications().size()!=0 || apForm.getIneligiblePendingApplications().size()!=0 || apForm.getIneligibleDupPendingApplications().size()!=0)
								{
							%>
							<tr>
									<td class="SubHeading" colspan="4">
									<bean:message key="pendingApplications"/>
							</tr>
							<tr>
		<!--						<td align="center" class="HeadingBg" width="30" colspan="3">
									<table width="100%" border="0" cellspacing="1" cellpadding="0">
										<tr>

-->
							<!--Clear Pending Apps-->
							<%
								if (apForm.getClearPendingApplications().size()!=0)
								{
							%>
							<tr>
<!--							 <td align="center" class="HeadingBg" width="50%"><div align="center">
									<table width="100%" border="0" cellspacing="1" cellpadding="0" colspan="2">
										<tr>-->
											<td align="center" class="HeadingBg" colspan="2">
												<bean:message key = "clearApplications"/></div>
											</td>
										 </tr>
								<logic:iterate id="object" name="apForm" property="clearPendingApplications">
								<%
							   com.cgtsi.application.Application clearPendingApp = (com.cgtsi.application.Application)object;

								String appRefNo=clearPendingApp .getAppRefNo();							

								%>	
								<tr>
									 <td align="center" class="ColumnBackground" colspan="2"><div align="center"><%=appRefNo%></div>
								</tr>
								</logic:iterate>
<!--								</table>
								</td>
								
	-->							<%}%>

								<!--Duplicate Pending Apps-->

								<%
								if (apForm.getDupPendingApplications().size()!=0)
								{
							%>
							<tr>
<!--				 <td align="center" class="HeadingBg" width="30%" colspan="2"><div align="center">
									<table width="100%" border="0" cellspacing="1" cellpadding="0">
										<tr>-->
											<td align="center" class="HeadingBg" colspan="2">
												<bean:message key = "probableDuplicateApps"/></div>
											</td>
										 </tr>
								<logic:iterate id="object" name="apForm" property="dupPendingApplications">
								<%
							   com.cgtsi.application.Application dupPendingApp = (com.cgtsi.application.Application)object;

								String appRefNo=dupPendingApp.getAppRefNo();						

								%>	
								<tr>
									 <td align="center" class="ColumnBackground" colspan="2"><div align="center"><%=appRefNo%></div>

								</tr>
								</logic:iterate>
<!--								</table>
								</td>
	-->							
								<%}%>

								<!--Ineligible Applications-->

								<%
								if (apForm.getIneligiblePendingApplications().size()!=0)
								{
								%>
								<tr>
<!--						 <td align="center" class="HeadingBg" width="30" ><div align="center">
									<table width="100%" border="0" cellspacing="1" cellpadding="0">
										<tr>-->
											<td align="center" class="HeadingBg" colspan="2">
												<bean:message key = "probableIneligibleApps"/></div>
											</td>
										 </tr>
								<logic:iterate id="object" name="apForm" property="ineligiblePendingApplications">
								<%
							   com.cgtsi.application.Application ineligiblePendingApp = (com.cgtsi.application.Application)object;

								String appRefNo=ineligiblePendingApp.getAppRefNo();
								
								%>	
								<tr>
									 <td align="center" class="ColumnBackground" colspan="2"><div align="center"><%=appRefNo%></div>
									

								</tr>
								</logic:iterate>
<!--								</table>
								</td>
	-->							
								<%}%>

								<!--Ineligible Duplicate Apps-->

								<%
								if (apForm.getIneligibleDupPendingApplications().size()!=0)
								{
								%>
								<tr>
<!--		<td align="center" class="HeadingBg" width="30%"><div align="center">
									<table width="100%" border="0" cellspacing="1" cellpadding="0">
										<tr>-->
											<td align="center" class="HeadingBg"  colspan="2">
												<bean:message key = "probableIneligibleApps"/></div>
											</td>
										 </tr>
								<logic:iterate id="object" name="apForm" property="ineligibleDupPendingApplications">
								<%
							   com.cgtsi.application.Application ineligibleDupPendingApp = (com.cgtsi.application.Application)object;

								String appRefNo=ineligibleDupPendingApp.getAppRefNo();
								
								%>	
								<tr>
									 <td align="center" class="ColumnBackground" colspan="2"><div align="center"><%=appRefNo%></div>							 

								</tr>
								</logic:iterate>
<!--								</table>
								</td>
	-->							
								<%}%>				 

<!--							</tr>
							</table>
							</td>
	-->						</tr>
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

											
										  
										  
											
