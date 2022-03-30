<%@ page language="java"%>
<%@ taglib uri="/WEB-INF/TLD/struts-html.tld" prefix="html" %>
<%@ taglib uri="/WEB-INF/TLD/struts-bean.tld" prefix="bean" %>
<%@ taglib uri="/WEB-INF/TLD/struts-logic.tld" prefix="logic" %>
<%@ page import="org.apache.struts.action.DynaActionForm"%>
<% session.setAttribute("CurrentPage","reapplyRejectedApp.do?method=getReapplyRejectedApps");%>
<%@ include file="/jsp/SetMenuInfo.jsp" %>
<%String focusField ="";%>

<logic:equal property="bankId" value="0000" name="appForm">
<%focusField = "selectMember";%>
</logic:equal>

<logic:notEqual property="bankId" value="0000" name="appForm">
<%focusField = "cgbid";%>
</logic:notEqual>

<TABLE width="725" border="0" cellpadding="0" cellspacing="0">
	<html:errors />
	<html:form action="afterSubmitRejectedApp.do?method=showCgpanListNew" method="POST" focus="<%=focusField%>">
		<TR> 
			<TD width="20" align="right" valign="bottom"><IMG src="images/TableLeftTop.gif" width="20" height="31"></TD>
			<TD background="images/TableBackground1.gif"></TD>
			<TD width="20" align="left" valign="bottom"><IMG src="images/TableRightTop.gif" width="23" height="31"></TD>
		</TR>
		<TR>
			<TD width="20" background="images/TableVerticalLeftBG.gif">&nbsp;</TD>
			<TD>
				<DIV align="right">			
					<A HREF="javascript:submitForm('modifyHelp.do?method=modifyHelp')">
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
										<TD width="31%" class="Heading"><bean:message key="enterApplicationDetails" /></TD>
										<TD><IMG src="images/TriangleSubhead.gif" width="19" height="19"></TD>
									</TR>
									<TR>
										<TD colspan="6" class="Heading"><IMG src="images/Clear.gif" width="5" height="5"></TD>
									</TR>
								</TABLE>
							  </td>
							</tr>
							<tr align="left"> 
								<td class="ColumnBackground"> 
									&nbsp;
									
									<font color="#FF0000" size="2">*</font>	&nbsp;<bean:message key="mliID"/>		
								</td>
								<logic:equal property="bankId" value="0000" name="appForm">
									<td  class="TableData">
										<html:text property="selectMember" size="20" alt="cgbid" name="appForm" maxlength="12"/> 								
									</td>
								</logic:equal>
								<logic:notEqual property="bankId" value="0000" name="appForm">
									<td  class="TableData">
										<bean:write property="selectMember" name="appForm"/>					
									</td>
								</logic:notEqual>

								<td width="25%" class="ColumnBackground"> &nbsp;
									<bean:message key="cgbid"/>			
								</td>
								<td width="25%" class="TableData"> 
									<html:text property="cgbid" size="20" alt="cgbid" name="appForm" maxlength="9"/>           
								</td>
							 </tr>
							 <tr align="left"> 
                                                   <td width="25%" class="ColumnBackground"> 
									&nbsp; 
									<bean:message key="applicationRefNo"/>			
								</td>
								<td width="25%" class="TableData" colspan="4">
									 <html:text property="appRefNo" size="20" alt="cgbid" name="appForm" maxlength="12"/>
								</td>
							</tr>
						
							<!--  <tr align="left"> 
							   <td width="25%" class="ColumnBackground"> 
									&nbsp; 
									<bean:message key="borrowerName"/>			
								</td>
								<td width="25%" class="TableData" colspan="4">
									 <html:text property="ssiName" size="15" alt="ssiName" name="appForm" maxlength="100"/>
								</td>
							</tr> -->
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
								<A href="javascript:submitForm('afterSubmitRejectedApp.do?method=showCgpanListNew&flag=2')"><IMG src="images/OK.gif" alt="Save" width="49" height="37" border="0"></A>	
								<A href="javascript:document.appForm.reset()">
									<IMG src="images/Reset.gif" alt="Reset" width="49" height="37" border="0"></A>
								<A href="home.do?method=getMainMenu&menuIcon=<%=session.getAttribute("menuIcon")%>&mainMenu=<%=session.getAttribute("mainMenu")%>">
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







					