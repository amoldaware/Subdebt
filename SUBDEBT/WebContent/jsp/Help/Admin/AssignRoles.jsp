<%@ page language="java"%>
<%@ taglib uri="/WEB-INF/TLD/struts-html.tld" prefix="html" %>
<%@ taglib uri="/WEB-INF/TLD/struts-bean.tld" prefix="bean" %>
<% session.setAttribute("CurrentPage","helpAssignRoles.do");%>
<%@ include file="/jsp/SetMenuInfo.jsp" %>
<script src="/csrfguard"></script>

<TABLE width="725" border="0" cellpadding="0" cellspacing="0">
<html:errors />

<form>
<table width="680" border="0" cellspacing="0" cellpadding="0" align="center">
<TR> 
			<TD width="20" align="right" valign="bottom"><IMG src="images/TableLeftTop.gif" width="20" height="31"></TD>
			<TD background="images/TableBackground1.gif"></TD>
			<TD width="20" align="left" valign="bottom"><IMG src="images/TableRightTop.gif" width="23" height="31"></TD>
		</TR>
		<TR>
			<TD width="20" background="images/TableVerticalLeftBG.gif">&nbsp;</TD>
			<td width="713"><table width="606" border="0" cellspacing="1" cellpadding="0">
          <tr> 
            <td colspan="2" width="700"><table width="604" border="0" cellspacing="0" cellpadding="0">
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
												<TD width="31%" class="Heading"><bean:message key="<%=header%>" /></TD>
												<TD><IMG src="images/TriangleSubhead.gif" width="19" height="19"></TD>
											</TR>
											<TR>
												<TD colspan="3" class="Heading"><IMG src="images/Clear.gif" width="5" height="5"></TD>
											</TR>

										</TABLE>
									</TD> 

								</TR>   
          </tr>
          <tr>
		  <TD width="20%" align="left" valign="top" class="ColumnBackground">
										<DIV align="left">
											&nbsp;Roles
										</DIV>
									</TD>
           <TD width="27%" align="left" valign="top" class="TableData">&nbsp;
										Choose the roles to be assigned/modified to the user.
		  </TD>
          </tr>
		   <tr>
		  <TD width="20%" align="left" valign="top" class="ColumnBackground">
										<DIV align="left">
											&nbsp;Privileges
										</DIV>
									</TD>
           <TD width="27%" align="left" valign="top" class="TableData">&nbsp;
										When a role is chosen a set of privileges assigned to the Role will<BR>&nbsp; appear checked in the checkboxes.More privileges can be assigned <BR>&nbsp;&nbsp;or removed.
		  </TD>
          </tr>
		  	  
		  
							<TR align="center" valign="baseline" >
						<td colspan="2" width="700">
						<DIV align="center">
						<A  HREF="javascript:history.back()">	
						<IMG src="images/Back.gif" alt="Back" width="49" height="37" border="0">
						</A>								
						</DIV>
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
	</form>
</TABLE>
