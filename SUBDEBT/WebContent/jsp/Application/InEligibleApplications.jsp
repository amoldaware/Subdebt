<%@ page language="java"%>
<%@ taglib uri="/WEB-INF/TLD/struts-html.tld" prefix="html" %>
<%@ taglib uri="/WEB-INF/TLD/struts-bean.tld" prefix="bean" %>
<%@ taglib uri="/WEB-INF/TLD/struts-logic.tld" prefix="logic" %>
<%@ page import="com.cgtsi.application.EligibleApplication"%>
<%@ page import="com.cgtsi.actionform.APForm"%>
<%@ page import="java.text.SimpleDateFormat"%>
<%@ page import="java.util.ArrayList"%>
<%@ page import="java.util.Arrays"%>
<% session.setAttribute("CurrentPage","showAppsForEligibility.do?method=showAppsForEligibility");
String name="";
%>
<%@ include file="/jsp/SetMenuInfo.jsp" %>

<TABLE width="725" border="0" cellpadding="0" cellspacing="0">
	<html:errors />
	<html:form action="showAppsForEligibility.do?method=showAppsForEligibility" method="POST">
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
											<TD width="31%" class="Heading"><bean:message key="eligibleCriteria" /></TD>
											<TD><IMG src="images/TriangleSubhead.gif" width="19" height="19"></TD>
										</TR>
										<TR>
											<TD colspan="4" class="Heading"><IMG src="images/Clear.gif" width="5" height="5"></TD>
										</TR>
									</TABLE>
								</TD>
							</TR>
							</table>
							<tr>
								<td class="SubHeading" colspan="4">
									<bean:message key="ineligibleApps"/>
							</tr>
							<tr>
								<td>
								<TABLE width="100%" border="0" cellpadding="1" cellspacing="1">
								<tr> 
								  <td align="center" class="HeadingBg" width="29"><div align="center"><bean:message key = "srNo"/></div>
								  </td>
								  <td class="HeadingBg" align="center"><div align="center"><bean:message key="date"/></div>
								  </td>
								  <td class="HeadingBg" align="center"><div align="center"><bean:message key="applicationRefNo"/></div>
								  </td>
								  <td class="HeadingBg" align="center"><div align="center"><bean:message key="eligibleCriteria"/></div>
								  </td>
								  <td class="HeadingBg" align="center" width="15%"><div align="center"><bean:message key ="value"/></div>
								  </td>
								  						 						
							  </tr>
							   
								<% int k=0;%>
								<logic:iterate id="object" name="apForm" property="eligibleAppList">
								 <%
									com.cgtsi.application.EligibleApplication eligibleApplication= (com.cgtsi.application.EligibleApplication)object;

									String submissionDate=eligibleApplication.getSubmissiondate();
									String appRefNo = eligibleApplication.getAppRefNo();				
									String passedValues = eligibleApplication.getPassedCondition();	

									String failedValues = eligibleApplication.getFailedCondition();	
									String messages = eligibleApplication.getMessage();	

								%>

								 <tr align="center">
							  
									<td class="tableData" align="center" rowspan="3">&nbsp;<%=(k+1)%>
									</td>
									<td class="tableData" align="center" width="10%" rowspan="3">&nbsp;<%=submissionDate%>
									</td>
									<td class="tableData" align="center" width="10%" rowspan="3">&nbsp;
									<!--<%name="appRefNo(key-"+k+")";%>--><a href="afterCGPANPage.do?method=showCgpanList&appRef=<%=appRefNo%>&flag=1"><%=appRefNo%></a>
									<!--<html:hidden property = "<%=name%>" name="apForm" value="<%=appRefNo%>"/>-->
									</td>

											<td class="TableData" align="center"><%=failedValues%></td>
											<td class="TableData" align="center">False</td>

									<%if(passedValues!=null && !(passedValues.equals("")))
									{
									%>	
										<tr>
										<td class="TableData" align="center"><%=passedValues%></td>
										<td class="TableData" align="center">True</td>
										</tr>
									<%}
									else{%>
									<tr>
									<td class="TableData" align="center">
									</td>
									</tr>
									<%}%>

									<%if(messages!=null && !(messages.equals("")))
									{%>
										<tr>

											<td class="TableData" align="center" colspan="2"><%=messages%></td>	

											</tr>
										<%}
											else{%>
											<tr>
											<td class="TableData" align="center">
											</td>
											</tr>
											<%}%>

											<%++k;%>
										
									</logic:iterate>
										
								</table>
							</td>
						</tr>		 
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


								

							
							
		

