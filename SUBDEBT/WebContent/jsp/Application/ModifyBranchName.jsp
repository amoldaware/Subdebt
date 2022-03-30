<%@ page language="java"%>
<%@ taglib uri="/WEB-INF/TLD/struts-html.tld" prefix="html" %>
<%@ taglib uri="/WEB-INF/TLD/struts-bean.tld" prefix="bean" %>
<%@ taglib uri="/WEB-INF/TLD/struts-logic.tld" prefix="logic" %>
<%@ page import="org.apache.struts.action.DynaActionForm"%>
<%@ include file="/jsp/SetMenuInfo.jsp" %>
<%String focusField ="";%>

<logic:equal property="bankId" value="0000" name="appForm">
<%focusField = "selectMember";%>
</logic:equal>

<TABLE width="725" border="0" cellpadding="0" cellspacing="0">
	<html:errors />
	<html:form action="modifyAppBranchName.do?method=modifyAppBranchName" method="POST" focus="<%=focusField%>">
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
							  <td colspan="2">
								<TABLE width="100%" border="0" cellpadding="0" cellspacing="0">
									<TR>
										<TD width="31%" class="Heading"><bean:message key="enterApplicationDetails" /></TD>
										<TD width="69%"><IMG src="images/TriangleSubhead.gif" width="19" height="19"></TD>
									</TR>
									<TR>
										<TD colspan="4" class="Heading"><IMG src="images/Clear.gif" width="5" height="5"></TD>
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
										<html:text property="selectMember" size="15" alt="selectMember" name="appForm" maxlength="12"/> 								
									</td>
								</logic:equal>
								<logic:notEqual property="bankId" value="0000" name="appForm">
									<td  class="TableData">
										<bean:write property="selectMember" name="appForm"/>					
									</td>
								</logic:notEqual>
           		 </tr>
							 <tr align="left"> 
							   <td class="ColumnBackground"> 
									<font color="#FF0000" size="2">*</font>	&nbsp;
									<bean:message key="cgpan"/>			
								</td>
								<td  class="TableData">
									 <html:text property="cgpan" size="15" alt="cgpan" name="appForm" maxlength="15"/>        
								</td>
							 </tr>
                             
                
						  </table>									
						</td>
					</tr>

					
					<TR >
						<TD align="center" valign="baseline">
							<DIV align="center">
								<A href="javascript:submitForm('aftermodifyAppBranchName.do?method=aftermodifyAppBranchName')"><IMG src="images/OK.gif" alt="Save" width="49" height="37" border="0"></A>	
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







					