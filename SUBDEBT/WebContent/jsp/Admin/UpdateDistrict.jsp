<%@ page language="java"%>
<%@ taglib uri="/WEB-INF/TLD/struts-html.tld" prefix="html" %>
<%@ taglib uri="/WEB-INF/TLD/struts-bean.tld" prefix="bean" %>
<% session.setAttribute("CurrentPage","showUpdateDistrict.do?method=showUpdateDistrict");%>
<% 
String req = (String)session.getAttribute("modFlag");
String focusField="stateName";
if (req.equals("0"))
{
	session.setAttribute("CurrentPage","showUpdateDistrict.do?method=showUpdateDistrict");
}
else if (req.equals("1"))
{
	session.setAttribute("CurrentPage","showUpdateDistrict.do?method=showDistricts");
	focusField="districtName";
}
else if (req.equals("2"))
{
	session.setAttribute("CurrentPage","showUpdateDistrict.do?method=showDistrictName");
	focusField="districtName";
}
%>
<%@ include file="/jsp/SetMenuInfo.jsp" %>
<TABLE width="725" border="0" cellpadding="0" cellspacing="0">
	<html:errors />
	<html:form action="updateDistrict.do?method=updateDistrict" method="POST" focus="<%=focusField%>">
		<TR> 
			<TD width="20" align="right" valign="bottom"><IMG src="images/TableLeftTop.gif" width="20" height="31"></TD>
			<TD background="images/TableBackground1.gif"></TD>
			<TD width="20" align="left" valign="bottom"><IMG src="images/TableRightTop.gif" width="23" height="31"></TD>
		</TR>
		<TR>
			<TD width="20" background="images/TableVerticalLeftBG.gif">&nbsp;</TD>
			<TD>
			<DIV align="right">			
				<A HREF="javascript:submitForm('helpUpdateDistrict.do')">
			    HELP</A>
			</DIV>
				<TABLE width="100%" border="0" align="left" cellpadding="0" cellspacing="0">
				<TD  align="left" colspan="4"><font size="2"><bold>
				Fields marked as </font><font color="#FF0000" size="2">*</font><font size="2"> are mandatory</bold></font>
				</td>
					<TR>
						<TD>
							<TABLE width="100%" border="0" cellspacing="1" cellpadding="1">
								<TR>
									<TD colspan="4"> 
										<TABLE width="100%" border="0" cellpadding="0" cellspacing="0">
											<TR>
												<TD width="31%" class="Heading"><bean:message key="districtMasterHeader" /></TD>
												<TD><IMG src="images/TriangleSubhead.gif" width="19" height="19"></TD>
											</TR>
											<TR>
												<TD colspan="3" class="Heading"><IMG src="images/Clear.gif" width="5" height="5"></TD>
											</TR>

										</TABLE>
									</TD>
								</TR>

								<TR align="left">
									<TD align="left" valign="top" class="ColumnBackground">
										&nbsp;<font color="#FF0000" size="2">*</font><bean:message key="districtState" />
									</TD>
									<TD align="left" class="TableData"> 
										<html:select property="stateName" name="adminForm" onchange="javascript:submitForm('showUpdateDistrict.do?method=showDistricts')">
											<html:option value="">Select</html:option>
											<html:options property="states" name="adminForm"/>
										</html:select>
									</TD>
								</TR>
								<TR align="left">
									<TD align="left" valign="top" class="ColumnBackground">
										&nbsp;<font color="#FF0000" size="2">*</font><bean:message key="districtName" />
									</TD>
									<TD align="left" class="TableData"> 
										<html:select property="districtName" name="adminForm" onchange="javascript:submitForm('showUpdateDistrict.do?method=showDistrictName')">
											<html:option value="">Select</html:option>
											<html:options property="districts" name="adminForm"/>
										</html:select>
									</TD>
								</TR>
								<TR align="left">
									<TD align="left" valign="top" class="ColumnBackground">
										&nbsp;<font color="#FF0000" size="2">*</font><bean:message key="modDistrictName" />
									</TD>
									<TD align="left" class="TableData"> 
										<html:text property="modDistrictName" size="20" alt="District name" name="adminForm" maxlength="100"/>
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
							<A href="javascript:submitForm('updateDistrict.do?method=updateDistrict')"><IMG src="images/Save.gif" alt="Save" width="49" height="37" border="0"></A>
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





						


