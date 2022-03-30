<%@ page language="java"%>
<%@ taglib uri="/WEB-INF/TLD/struts-html.tld" prefix="html" %>
<%@ taglib uri="/WEB-INF/TLD/struts-bean.tld" prefix="bean" %>
<% 
String req = (String)session.getAttribute("modFlag");
String focusField="stateCode";
if (req.equals("0"))
{
	session.setAttribute("CurrentPage","showUpdateState.do?method=showUpdateState");
}
else if (req.equals("1"))
{
	session.setAttribute("CurrentPage","showUpdateState.do?method=showStateName");
//	focusField="stateName";
}
%>
<%@ include file="/jsp/SetMenuInfo.jsp" %>

	<html:form action="updateState.do?method=updateState" method="POST" focus="<%=focusField%>">
	<html:errors />
<TABLE width="725" border="0" cellpadding="0" cellspacing="0">
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
						<DIV align="right">			
				<A HREF="javascript:submitForm('helpUpdateState.do')">
			    HELP</A>
			</DIV>
							<TABLE width="100%" border="0" cellspacing="1" cellpadding="1">
							<TD  align="left" colspan="4"><font size="2"><bold>
				Fields marked as </font><font color="#FF0000" size="2">*</font><font size="2"> are mandatory</bold></font>
				</td>
								<TR>
									<TD colspan="4"> 
										<TABLE width="100%" border="0" cellpadding="0" cellspacing="0">
											<TR>
												<TD width="31%" class="Heading"><bean:message key="stateMasterHeader" /></TD>
												<TD><IMG src="images/TriangleSubhead.gif" width="19" height="19"></TD>
											</TR>
											<TR>
												<TD colspan="3" class="Heading"><IMG src="images/Clear.gif" width="5" height="5"></TD>
											</TR>

										</TABLE>
									</TD>
								</TR>

								<!-- <TR align="left">
									<TD align="left" valign="top" class="ColumnBackground">
										&nbsp;<bean:message key="stateRegion" />
									</TD>
									<TD align="left" class="TableData"> 
										<html:select property="regionName" name="adminForm">
											<html:option value="">Select</html:option>
											<html:options property="regions" name="adminForm"/>
														
										</html:select>
									</TD>
								</TR> -->
								<TR align="left">
									<TD align="left" valign="top" class="ColumnBackground">
										&nbsp;<font color="#FF0000" size="2">*</font><bean:message key="stateCode" />
									</TD>
									<TD align="left" class="TableData"> 
										<html:select property="stateCode" name="adminForm" onchange="javascript:submitForm('showUpdateState.do?method=showStateName')">
											<html:option value="">Select</html:option>
											<html:options property="states" name="adminForm"/>
										</html:select>
									</TD>
								</TR>
								<TR align="left">
									<TD align="left" valign="top" class="ColumnBackground">
										&nbsp;<font color="#FF0000" size="2">*</font><bean:message key="modStateName" />
									</TD>
									<TD align="left" class="TableData"> 
										<html:text property="stateName" size="20" alt="State name" name="adminForm" maxlength="50"/>
									</TD>
								</TR>	
							</TABLE>
						</TD>
					</TR>
					<TR >
						<TD height="20" >
							&nbsp;
						</TD>
					</TR>
					<TR >
						<TD align="center" valign="baseline" >
							<DIV align="center">
							<A href="javascript:submitForm('updateState.do?method=updateState')"><IMG src="images/Save.gif" alt="Save" width="49" height="37" border="0"></A>
								<A href="javascript:document.adminForm.reset()">
									<IMG src="images/Reset.gif" alt="Reset" width="49" height="37" border="0"></A>
							<A href="subHome.do?method=getSubMenu&menuIcon=<%=session.getAttribute("menuIcon")%>&mainMenu=<%=session.getAttribute("mainMenu")%>"><IMG src="images/Cancel.gif" alt="Cancel" width="49" height="37" border="0"></A>									
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
</TABLE>
	</html:form>





						


