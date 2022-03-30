<%@ page language="java"%>
<%@ page import="com.cgtsi.actionform.AdministrationActionForm"%>
<%@ taglib uri="/WEB-INF/TLD/struts-html.tld" prefix="html" %>
<%@ taglib uri="/WEB-INF/TLD/struts-bean.tld" prefix="bean" %>
<%@ taglib uri="/WEB-INF/TLD/struts-logic.tld" prefix="logic" %>
<%@ include file="/jsp/SetMenuInfo.jsp" %>

<%
	session.setAttribute("CurrentPage", "processQueryStatus.do?method=processQueryStatus");
%>


<script>




			
		</script>
	</head>
	<body>
		<TABLE width="100%" border="0" cellpadding="0" cellspacing="0">
			<html:errors />
			<html:form  action="showQueryStatusReport.do?method=showQueryStatusReport" method="POST" enctype="multipart/form-data">
				<TR> 
					<TD width="20" align="right" valign="bottom"><IMG src="images/TableLeftTop.gif" width="20" height="31"></TD>
					<TD background="images/TableBackground1.gif"><IMG src="images/SystemAdministrationHeading.gif"></TD>
					<TD width="20" align="left" valign="bottom"><IMG src="images/TableRightTop.gif" width="23" height="31"></TD>
				</TR>
				<TR>
					<TD width="20" background="images/TableVerticalLeftBG.gif">&nbsp;</TD>			
					<TD>
						<TABLE width="100%" border="0" align="left" cellpadding="1" cellspacing="1">
							<TR>
								<TD colspan="12"> 
									<TABLE width="100%" border="0" cellpadding="0" cellspacing="0">
										<TR>
											<TD width="35%" class="Heading">Query Status Report</TD>
											<TD><IMG src="images/TriangleSubhead.gif" width="19" height="19"></TD>
										</TR>
										<TR><TD colspan="12" class="Heading"><IMG src="images/Clear.gif" width="5" height="5"></TD></TR>
									</TABLE>
								</TD>
							</TR>
							<tr><td colspan="8"><font color="Green" size="2">  </font></td></tr>
							<TR>
								<TD align="left" valign="top" class="ColumnBackground" width="50">
									<bean:message key="sNo" />
								</TD>
								<TD align="left" valign="top" class="ColumnBackground" width="50">
									QUERY/COMPLAINT ID
								</TD>
								<TD align="left" valign="top" class="ColumnBackground" width="98">
									BANK ID
								</TD>
								<TD align="left" valign="top" class="ColumnBackground" width="98">
									ZONE ID
								</TD>						
								<TD align="left" valign="top" class="ColumnBackground" width="98">
									BRANCH ID
								</TD>
								<TD align="left" valign="top" class="ColumnBackground" width="98">
									PHONE NO
								</TD>
								<TD align="left" valign="top" class="ColumnBackground" width="98">
									EMAIL ID
								</TD>
								<TD align="left" valign="top" class="ColumnBackground" width="98">
									QUERY DESCRIPTION
								</TD>						
								<TD align="left" valign="top" class="ColumnBackground" width="114">
									CONTACT PERSON
								</TD>
								
								<TD align="left" valign="top" class="ColumnBackground" width="114">
									QUERY DATE
								</TD>
								<TD align="left" valign="top" class="ColumnBackground" width="114">
									QUERY  STATUS
								</TD>
                                         <TD align="left" valign="top" class="ColumnBackground" width="114">
									QUERY  REPLY/STATUS
								</TD>
								
							</TR>
													
							<logic:iterate id="object" name="adminActionForm" property="mliQueryList" indexId="index">
								<%
									AdministrationActionForm QueryFrom = (AdministrationActionForm)object;
									String queryRespon = "";
								
								%>
								<TR>
									<TD align="left" valign="top" class="ColumnBackground" width="50"><%=Integer.parseInt(index+"")+1%></TD>
									<TD align="left" valign="top" class="ColumnBackground" width="98"><%=QueryFrom.getQueryId()%></TD>
									<TD align="left" valign="top" class="ColumnBackground" width="98"><%=QueryFrom.getBankId()%></TD>
									<TD align="left" valign="top" class="ColumnBackground" width="98"><%=QueryFrom.getZoneId()%></TD>
									<TD align="left" valign="top" class="ColumnBackground" width="98"><%=QueryFrom.getBranchId()%></TD>
									<TD align="left" valign="top" class="ColumnBackground" width="98"><%=QueryFrom.getContPerson()%></TD>
									<TD align="left" valign="top" class="ColumnBackground" width="98"><%=QueryFrom.getPhoneNo()%></TD>
									<TD align="left" valign="top" class="ColumnBackground" width="98"><%=QueryFrom.getEmailId()%></TD>
									<TD align="left" valign="top" class="ColumnBackground" width="114"><%=QueryFrom.getDepartments()%></TD>									
									<TD align="left" valign="top" class="ColumnBackground" width="114"><%=QueryFrom.getQueryDescription()%></TD>
									<TD align="left" valign="top" class="ColumnBackground" width="114"><%=QueryFrom.getQueryStatus()%></TD>
<TD align="left" valign="top" class="ColumnBackground" width="114"><%=QueryFrom.getQueryResponse()%></TD>
								
								
								
								</TR>
							</logic:iterate>																
							<TR>
								
							</TR>
						</TABLE>
					</TD>
					<TD width="20" background="images/TableVerticalRightBG.gif">&nbsp;</TD>
					
					
				</TR>
				<TR>
					<TD width="20" align="right" valign="top">
						<IMG src="images/TableLeftBottom1.gif" width="20" height="15">
					</TD>
					<TD background="images/TableBackground2.gif">&nbsp;</TD>
					<TD width="20" align="left" valign="top">
						<IMG src="images/TableRightBottom1.gif" width="23" height="15"></td>
						
					
				</TR>
				
				<TR><TD  align="middle" colspan="2" class=""><A href="javascript:submitForm('showQueryStatusReportInput.do?method=showQueryStatusReportInput')"><IMG src="images/OK.gif" width="49" height="37" border="0"></A></TD></TR>
				</TR>
				
				
				
			</html:form>
		</TABLE>
	</body>
</html>