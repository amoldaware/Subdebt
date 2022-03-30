<%@ page language="java"%>
<%@ taglib uri="/WEB-INF/TLD/struts-html.tld" prefix="html" %>
<%@ taglib uri="/WEB-INF/TLD/struts-bean.tld" prefix="bean" %>

<% session.setAttribute("CurrentPage","showModifyRole.do?method=showModifyRole");%>
<%@ include file="/jsp/SetMenuInfo.jsp" %>
<%
String focusflag = "";
if(request.getAttribute("privileges")!=null && request.getAttribute("privileges").equals("1"))
{
	focusflag="roleDescription";
}
else{

	focusflag="roleName";
}
%>
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
			<html:form action="showModifyRole.do" method="POST" focus="<%=focusflag%>" >
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
								<A HREF="javascript:submitForm('helpModifyRole.do?method=helpModifyRole')">
								HELP</A>
							</DIV>
									<TABLE width="606" border="0" cellspacing="1" cellpadding="0">
									<TD  align="left" colspan="4"><font size="2"><bold>
				Fields marked as </font><font color="#FF0000" size="2">*</font><font size="2"> are mandatory</bold></font>
				</td>
										<TR> 
											<TD colspan="2" width="700"><table width="604" border="0" cellspacing="0" cellpadding="0">
											<TD width="45%" class="Heading"><bean:message key="modifyRoleHeader" /></TD>
											<TD><IMG src="images/TriangleSubhead.gif" width="19" height="19"></TD>
										</TR>
										<TR>
											<TD colspan="3" class="Heading"><IMG src="images/Clear.gif" width="5" height="5"></TD>
										</TR>
									</TABLE>
								</TD>
							</TR>
							<TR>
								<TD colspan="4" class="SubHeading">
									<DIV align="left">
										<BR>
										<bean:message key="roleDetails" />
									</DIV>
								</TD>
							</TR>
							<TR>
								<TD width="20%" align="left" valign="top" class="ColumnBackground">
									<DIV align="left">
									&nbsp;<font color="#FF0000" size="2">*</font><bean:message key="roleName" />
									</DIV>
								</TD>
								<TD width="27%" align="left" valign="top" class="TableData">
									<html:select property="roleName" name="adminForm" onchange="javascript:submitForm('showPrivilegesForRole.do?method=getPrivilegesForRole')">
										<html:option value="">Select</html:option>
										<html:options name="<%=com.cgtsi.util.SessionConstants.ROLE_NAMES%>" ></html:options>
									</html:select>
								</TD>
							</TR>
							<TR>
								<TD align="left" valign="top" class="ColumnBackground">
									&nbsp;<font color="#FF0000" size="2">*</font><bean:message key="roleDescription" />
								</TD>
								<TD  align="left" valign="top" class="TableData">
									<html:text property="roleDescription" size="20" maxlength="200" alt="Role Description"  name="adminForm"/>
								</TD>
							</TR>
							<%@ include file="Privileges.jsp" %>												
							<TR align="center" valign="baseline" >
								<TD colspan="2" width="700">
									<DIV align="center">
									<A href="javascript:submitForm('modifyRole.do?method=modifyRole')">
										<IMG src="images/Save.gif" alt="Save" width="49" height="37" border="0"></A>
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
			</html:form>
		</TABLE>
	</BODY>
</HTML>
