<%@ page language="java"%>
<%@ taglib uri="/WEB-INF/TLD/struts-html.tld" prefix="html" %>
<%@ taglib uri="/WEB-INF/TLD/struts-bean.tld" prefix="bean" %>
<%@ include file="/jsp/SetMenuInfo.jsp" %>
<% 
String req = (String)session.getAttribute("statusFlag");
if (req.equals("0"))
{
	session.setAttribute("CurrentPage","showAllowableRatingsForAgency.do?method=showAllowableRatingsForAgency");
}
else if (req.equals("1"))
{
	session.setAttribute("CurrentPage","showAllowableRatingsForAgency.do?method=showAllowableRatingsForAgencyDetails");
}
%>



	<html:form action="showAllowableRatingsForAgency.do?method=showAllowableRatingsForAgency" method="POST" enctype="multipart/form-data" >
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
												<TD width="31%" class="Heading"><bean:message key="ratingHeader" /></TD>
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
									<html:select property="ratingAgency" name="ifForm" onchange="javascript:submitForm('showAllowableRatingsForAgency.do?method=showAllowableRatingsForAgencyDetails')">
											<html:option value="">Select</html:option>
											<html:options property="agencies" name="ifForm"/>
										</html:select>
									</TD>

									<TD class="ColumnBackground" rowspan="3">
										<font color="#FF0000" size="2">*</font><bean:message key="rating"/>
									</TD>
									<TD class="TableData" rowspan="3">
										<html:select property="allowableRating" name="ifForm" multiple="true" size="5">
<!--											<html:option value="All"></html:option>-->
											<html:options property="allowableRatings" name="ifForm" />
										</html:select>
									&nbsp;</TD>

							</TABLE>
						</TD>
					</TR>
					<TR>
						<TD height="20" >
							&nbsp;
						</TD>
					</TR>
					<TR >
						<TD align="center" valign="baseline" >
							<DIV align="center">
							<A href="javascript:submitForm('updateAllowableRatingsForAgency.do?method=updateAllowableRatingsForAgency')">
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

