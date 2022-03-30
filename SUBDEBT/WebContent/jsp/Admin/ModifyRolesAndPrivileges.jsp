<%@ page language="java"%>
<%@ taglib uri="/WEB-INF/TLD/struts-html.tld" prefix="html" %>
<%@ taglib uri="/WEB-INF/TLD/struts-bean.tld" prefix="bean" %>
<%@ taglib uri="/WEB-INF/TLD/struts-logic.tld" prefix="logic" %>
<% session.setAttribute("CurrentPage","showModifyRoles.do?method=showModifyRoles");%>
<%@ include file="/jsp/SetMenuInfo.jsp" %>
<HTML>
	<BODY>
		<TABLE width="725" border="0" cellpadding="0" cellspacing="0">
			<html:errors />
			<html:form action="showModifyRoles.do" method="POST" >
				<TR>
					<TD>
						<TABLE width="680" border="0" cellspacing="0" cellpadding="0" align="center">
							<TR> 
								<TD width="20" align="right" valign="bottom"><IMG src="images/TableLeftTop.gif" width="20" height="31"></TD>
								<TD background="images/TableBackground1.gif"></TD>
								<TD width="20" align="left" valign="bottom"><IMG src="images/TableRightTop.gif" width="23" height="31"></TD>
							</TR>
							<TR>
								<TD width="20" background="images/TableVerticalLeftBG.gif">&nbsp;</TD>
								<TD width="713">
									<TABLE width="606" border="0" cellspacing="1" cellpadding="0">
										<TR> 
											<TD colspan="2" width="700"><table width="604" border="0" cellspacing="0" cellpadding="0">
											<TD width="45%" class="Heading"><bean:message key="modifyRoleHeader" />-<bean:write property="userId" name="adminActionForm"/></TD>
											<TD><IMG src="images/TriangleSubhead.gif" width="19" height="19"></TD>
										</TR>
										<TR>
											<TD colspan="3" class="Heading"><IMG src="images/Clear.gif" width="5" height="5"></TD>
										</TR>
									</TABLE>
								</TD>
							</TR>
							<TR>
								<TD class="SubHeading">
									<DIV align="left">
										<BR>
										<font color="#FF0000" size="2">*</font><bean:message key="roleDetails" />
									</DIV>
								</TD>
							</TR>
							<% int counter=0;%>
							<logic:iterate property="roleNames" name="adminActionForm" id="object">
								<TR>
									<TD width="20%" align="left" valign="top" class="ColumnBackground">
										<DIV align="left">
										&nbsp;
										<% 
											out.println(object); 
											String roleName=(String)object;
											String key="roles("+roleName+")";
										%>
										</DIV>
									</TD>
									<TD width="27%" align="left" valign="top" class="TableData">
										<!--<input type="checkbox" name="<%=object%>" value="<%=object%>"> !-->
										<html:checkbox property="<%=key%>" name="adminActionForm" value="<%=roleName%>" onclick="javascript:submitForm('getPrivilegesForRole.do?method=showPrivilegesForRole')"/>
									</TD>
								</TR>
								<% counter++; %>
							</logic:iterate>
							<% 
								request.setAttribute("counter",new Integer(counter));
							%>
							
							<%@ include file="AssignPrivileges.jsp" %>
							
							<TR align="center" valign="baseline" >
								<TD colspan="2" width="700">
									<DIV align="center">
									<A href="javascript:submitForm('assignRolesAndPrivileges.do?method=assignRolesAndPrivileges')">
										<IMG src="images/Save.gif" alt="Save" width="49" height="37" border="0"></A>
										<A href="javascript:document.adminActionForm.reset()">
										<IMG src="images/Reset.gif" alt="Cancel" width="49" height="37" border="0"></A>
										
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
	</BODY>
</HTML>
