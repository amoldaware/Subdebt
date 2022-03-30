<%@ page language="java"%>
<%@ taglib uri="/WEB-INF/TLD/struts-html.tld" prefix="html" %>
<%@ taglib uri="/WEB-INF/TLD/struts-bean.tld" prefix="bean" %>
<%@ taglib uri="/WEB-INF/TLD/struts-logic.tld" prefix="logic" %>


<% 

//String userID=(String)session.getAttribute("UserID");
session.setAttribute("CurrentPage","showAssignRoles.do?method=showAssignRoles");

%>
<%@ include file="/jsp/SetMenuInfo.jsp" %>
<HTML>
	<head>
	<script type="text/javascript"> 
        window.history.forward(); 
        function noBack() { 
            window.history.forward(); 
        } 
    </script> 
	
	<script src="/csrfguard"></script>
	
	</head>
	<BODY>
		<TABLE width="725" border="0" cellpadding="0" cellspacing="0">
			<html:errors />
			<html:form action="showAssignRoles.do" method="POST" >
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
								<DIV align="right">			
				<A HREF="javascript:submitForm('helpAssignRoles.do?method=helpAssignRoles')">
			    HELP</A>
			</DIV>
									<TABLE width="606" border="0" cellspacing="1" cellpadding="0">
									<TD  align="left" colspan="4"><font size="2"><bold>
				Fields marked as </font><font color="#FF0000" size="2">*</font><font size="2"> are mandatory</bold></font>
				</td>
										<TR> 
											<TD colspan="2" width="700"><table width="604" border="0" cellspacing="0" cellpadding="0">
											<%
												String header="AssignRolesHeader";
												String actionValue="assignRolesAndPrivileges.do?method=assignRolesAndPrivileges";
												String onChangeValue="javascript:submitForm('getPrivilegesForRole.do?method=showPrivilegesForRole')";
												
												
												if(session.getAttribute("subMenuItem")!=null && session.getAttribute("subMenuItem").equals("Modify Roles/Privileges"))
												{
													header="modifyRoleHeader";
													actionValue="modifyRolesAndPrivileges.do?method=modifyRolesAndPrivileges";
													onChangeValue="javascript:submitForm('getPrivilegesForRole.do?method=showPrivilegesForRole&from=modified')";
												}
											%>
											<TD width="300" class="Heading"><bean:message key="<%=header%>" />-<bean:write property="userId" name="adminActionForm"/></TD>
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
										
										<bean:message key="roleDetails" />
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
										<html:checkbox property="<%=key%>" name="adminActionForm" value="<%=roleName%>" onclick="<%=onChangeValue%>"/>
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
									<A href="javascript:submitForm('<%=actionValue%>')">
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
