<%@ page language="java"%>
<%@ taglib uri="/WEB-INF/TLD/struts-html.tld" prefix="html" %>
<%@ taglib uri="/WEB-INF/TLD/struts-bean.tld" prefix="bean" %>
<%@ include file="/jsp/SetMenuInfo.jsp" %>
<% 
String req = (String)session.getAttribute("statusFlag");
if (req.equals("0"))
{
	session.setAttribute("CurrentPage","showRatingAgency.do?method=showRatingAgency");
}
else if (req.equals("1"))
{
	session.setAttribute("CurrentPage","showRatingAgency.do?method=showRatingAgencyDetails");
}
%>


<body onload="enableAgency(this)">
	<html:form action="showRatingAgency.do?method=showRatingAgency" method="POST" enctype="multipart/form-data" >
	<html:hidden name="ifForm" property="test"/>
	<html:errors />

<TABLE width="725" border="0" cellpadding="0" cellspacing="0">
		<TR>
			<TD width="20" align="right" valign="bottom"><IMG src="images/TableLeftTop.gif" width="20" height="31"></TD>
			<TD background="images/TableBackground1.gif"><IMG src="images/InvestmentManagementHeading.gif" width="161" height="25"></TD>
			<TD width="20" align="left" valign="bottom"><IMG src="images/TableRightTop.gif" width="23" height="31"></TD>
		</TR>
		<TR>
			<TD width="20" background="images/TableVerticalLeftBG.gif">&nbsp;</TD>
			<TD>
			<DIV align="right">			
			<A HREF="inwardHelp.do?method=inwardHelp">
			HELP</A>
				<TABLE width="100%" border="0" align="left" cellpadding="0" cellspacing="0">
				<tr>
				<TD  align="left" colspan="4"><font size="2"><bold>
				Fields marked as </font><font color="#FF0000" size="2">*</font><font size="2"> are mandatory</bold></font>
				</td>
				</tr>
					<TR>
						<TD>
							<TABLE width="100%" border="0" cellspacing="1" cellpadding="1">
								<TR>
									<TD colspan="4"> 
										<TABLE width="100%" border="0" cellpadding="0" cellspacing="0">
											<TR>
												<TD width="31%" class="Heading"><bean:message key="ratingAgencyHeader" /></TD>
												<TD><IMG src="images/TriangleSubhead.gif" width="19" height="19"></TD>
											</TR>
											<TR>
												<TD colspan="3" class="Heading"><IMG src="images/Clear.gif" width="5" height="5"></TD>
											</TR>

										</TABLE>
									</TD>
								</TR>


								<TR>
									<TD width="20%" align="left" valign="top" class="ColumnBackground"><font color="#FF0000" size="2">*</font>
									&nbsp;<bean:message key="agencyNames" />
									</TD>
									<TD width="27%" align="left" valign="top" class="TableData">
									<html:select property="agency" name="ifForm" onchange="enableAgency(this);javascript:submitForm('showRatingAgency.do?method=showRatingAgencyDetails')">
											<html:option value="">Select</html:option>
											<html:options property="agencies" name="ifForm"/>
										</html:select>
									</TD>


									  <bean:define id="nVal" name="ifForm" property="newAgency"/>
									  <%
									  String reqVal1 = (String)nVal;
									  if (request.getParameter("newAgency")!=null)
									  {
										reqVal1 = (String)request.getParameter("newAgency");
									  }
									  %>
									<TD align="left" class="ColumnBackground">&nbsp;&nbsp;<bean:message key="newAgency" />&nbsp;&nbsp;
										<html:text property="newAgency" size="20" alt="Enter" name="ifForm"  maxlength="20" value="<%=reqVal1%>"/>
									</TD>
								</TR>


								  <bean:define id="mVal" name="ifForm" property="modAgencyName"/>
								  <%
								  String reqVal = (String)mVal;
								  if (request.getParameter("modAgencyName")!=null)
								  {
									reqVal = (String)request.getParameter("modAgencyName");
								  }
								  %>
								<TR>
									  <TD align="left" valign="top" class="ColumnBackground"><font color="#FF0000" size="2">*</font>
										  &nbsp;<bean:message key="modifyAgencyName" />
									  </TD>
									  <TD colspan="3" align="left" valign="top" class="TableData"class="ColumnBackground"> 
										<html:text property="modAgencyName" size="20" maxlength = "30" alt="Language" name="ifForm" value="<%=reqVal%>"/>
									  </TD>
								</TR>

								<TR>
									<TD align="left" valign="top" class="ColumnBackground"><font color="#FF0000" size="2">*</font>
										&nbsp;<bean:message key="modifyAgencyDesc" />
									</TD>
									<TD colspan="3" align="left" valign="top" class="TableData">
										<html:text property="modAgencyDesc" size="70"  alt="Subject"  maxlength = "50" name="ifForm"/>
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
							<A href="javascript:submitForm('updateRatingAgency.do?method=updateRatingAgency')">
									<IMG src="images/Save.gif" alt="Save" width="49" height="37" border="0"></A>
								<A href="javascript:document.ifForm.reset()">
									<IMG src="images/Reset.gif" alt="Cancel" width="49" height="37" border="0"></A>
								
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

