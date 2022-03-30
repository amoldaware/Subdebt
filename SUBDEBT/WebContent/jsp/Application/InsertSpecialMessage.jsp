<%@ page language="java"%>
<%@ page import="com.cgtsi.util.SessionConstants"%>
<%@ taglib uri="/WEB-INF/TLD/struts-html.tld" prefix="html" %>
<%@ taglib uri="/WEB-INF/TLD/struts-bean.tld" prefix="bean" %>
<% 
if(session.getAttribute(SessionConstants.SPL_MESSAGE_FLAG).equals("0"))
{
	session.setAttribute("CurrentPage","splMessage.do?method=updateSpecialMessage");
}
else{

	session.setAttribute("CurrentPage","splMessage.do?method=newSplMessage");}
%>
<%@ include file="/jsp/SetMenuInfo.jsp" %>

<TABLE width="725" border="0" cellpadding="0" cellspacing="0">
	<html:errors />
	<html:form action="afterInsertSplMessage.do?method=insertSplMessage" method="POST" focus="msgTitle">
		<TR> 
			<TD width="20" align="right" valign="bottom"><IMG src="images/TableLeftTop.gif" width="20" height="31"></TD>
			<TD background="images/TableBackground1.gif"></TD>
			<TD width="20" align="left" valign="bottom"><IMG src="images/TableRightTop.gif" width="23" height="31"></TD>
		</TR>
		<TR>
			<TD width="20" background="images/TableVerticalLeftBG.gif">&nbsp;</TD>
			<TD>
				<DIV align="right">			
					<A HREF="javascript:submitForm('splMessageHelp.do?method=splMessageHelp')">
					HELP</A>
				</DIV>

				<TABLE width="100%" border="0" align="left" cellpadding="0" cellspacing="0">
					<TR>
						<TD>
							<table width="100%" border="0" cellspacing="1" cellpadding="0">
							<tr> 
							  <td colspan="4">
								<TABLE width="100%" border="0" cellpadding="0" cellspacing="0">
									<TR>
										<TD width="31%" class="Heading"><bean:message key="specialMessageHeader" /></TD>
										<TD><IMG src="images/TriangleSubhead.gif" width="19" height="19"></TD>
									</TR>
									<TR>
										<TD colspan="6" class="Heading"><IMG src="images/Clear.gif" width="5" height="5"></TD>
									</TR>
								</TABLE>
							  </td>
							</tr>
						
					 <tr>
						<td class="ColumnBackground" width="25%">&nbsp;&nbsp;
							<font color="#FF0000" size="2">*</font>&nbsp;<bean:message key="messageTitle"/>&nbsp;
						</td>						
						<td class="TableData" width="353" colspan="3">&nbsp;&nbsp;	
							<%
							String splMessageFlag=(String)session.getAttribute(SessionConstants.SPL_MESSAGE_FLAG);
							if(splMessageFlag.equals("0"))
							{

							%>
								<html:select property="msgTitle" name="appForm" onchange="javascript:submitForm('splMessage.do?method=getSplMessage')">
									<html:option value="">Select</html:option>
									<html:options name="appForm" property="msgTitlesList"></html:options>
								</html:select>
							<%
							}
							else
							{
							%>
							<html:text property="msgTitle" name="appForm" size="20" maxlength="50"/>
							<%}%>
						</td>
					  </tr> 
		        
					  <tr>
						<td class="ColumnBackground" width="25%">&nbsp;&nbsp;
							<font color="#FF0000" size="2">*</font>&nbsp;<bean:message key="specialMessage"/>&nbsp;
						</td>
						<td class="TableData" colspan="7" width="397">							
							<html:textarea property="messageDesc" cols="35" alt="message Desciption" name="appForm" rows="5"/>							
						</td>
					  </tr> 
					  
					  <tr>
						<td  class="ColumnBackground">&nbsp;&nbsp;
							<bean:message key="validity"/>
						</td>
						<td  class="TableData">
							<font color="#FF0000" size="2">*</font>&nbsp;<bean:message key="validityFrom"/>&nbsp;
							<html:text property="validityFromDate" size="20" alt="dob" name="appForm" maxlength="10"/>
							<IMG src="images/CalendarIcon.gif" width="20" onClick="showCalendar('appForm.validityFromDate')" align="center">
						</td>
						<td class="TableData"><bean:message key="validityTo"/>&nbsp;
							<html:text property="validityToDate" size="20" alt="dob" name="appForm" maxlength="10"/>
							<IMG src="images/CalendarIcon.gif" width="20" onClick="showCalendar('appForm.validityToDate')" align="center">
						</td>
					</tr>          
					 <tr align="left"> 
								<td colspan="4">
									<img src="images/clear.gif" width="5" height="15">
								</td>
							 </tr>
						  </table>									
						</td>
					</tr>

					
					<TR >
						<TD align="center" valign="baseline" colspan="6">
							<DIV align="center">
								<A href="javascript:submitForm('afterInsertSplMessage.do?method=insertSplMessage')"><IMG src="images/Save.gif" alt="Save" width="49" height="37" border="0"></A>		
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







					