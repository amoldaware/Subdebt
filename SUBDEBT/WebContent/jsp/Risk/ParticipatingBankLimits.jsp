<%@ page language="java"%>
<%@ taglib uri="/WEB-INF/TLD/struts-html.tld" prefix="html" %>
<%@ taglib uri="/WEB-INF/TLD/struts-bean.tld" prefix="bean" %>
<%@ include file="/jsp/SetMenuInfo.jsp" %>
<%@ page import="com.cgtsi.util.SessionConstants"%>
<%@page import ="java.text.DecimalFormat"%>
<%DecimalFormat decimalFormat = new DecimalFormat("#################.##");
decimalFormat.setDecimalSeparatorAlwaysShown(false);
%>
<% 
String focusObj="";
if (((String) session.getAttribute(SessionConstants.PARTICIPATING_BANK_FLAG)).equals("0"))
{
	session.setAttribute("CurrentPage", "showParticipatingBankLimits.do?method=showParticipatingBankLimits");
	focusObj="memberId";
}
else if (((String) session.getAttribute(SessionConstants.PARTICIPATING_BANK_FLAG)).equals("1"))
{
	session.setAttribute("CurrentPage","showParticipatingBankLimits.do?method=getParticipatingBanks");
	focusObj="bankName";
}
else if (((String) session.getAttribute(SessionConstants.PARTICIPATING_BANK_FLAG)).equals("2"))
{
	session.setAttribute("CurrentPage","showParticipatingBankLimits.do?method=getParticipatingBanksLimits");
	focusObj="amount";
}
%>

<TABLE width="100%" border="0" cellspacing="0" cellpadding="0">
	<html:errors />
	<html:form action="setParticipatingBankLimits.do?method=setParticipatingBankLimits" method="POST" focus="<%=focusObj%>">
	<TR> 
			<TD width="20" align="right" valign="bottom"><IMG src="images/TableLeftTop.gif" width="20" height="31"></TD>
			<TD background="images/TableBackground1.gif"><IMG src="images/RiskManagementHeading.gif" width="121" height="25"></TD>
			<TD width="20" align="left" valign="bottom"><IMG src="images/TableRightTop.gif" width="23" height="31"></TD>
		</TR>
		<TR> 
			<TD width="20" background="images/TableVerticalLeftBG.gif">&nbsp;</TD>
			<TD>
				<TABLE width="100%" align="left" border="0" cellspacing="0" cellpadding="0">
					<TR>
						<TD>
							<TABLE width="100%" border="0" cellspacing="1" cellpadding="0">
								<TR>
									<TD align="right" colspan="4">
										<A href="showPartBankLimitsHelp.do">HELP</A>
									</TD>
								</TR>
								<TR> 
									<TD colspan="4">
										<TABLE width="100%" border="0" cellspacing="0" cellpadding="0">
											<TR> 
												<TD width="25%" height="19" class="Heading">&nbsp;
													<bean:message key="participatingBankLimitsHeading" />
												</TD>
												<TD><IMG src="images/TriangleSubhead.gif" width="19" height="19"></TD>
											</TR>
											<TR> 
												<TD colspan="3" class="Heading"><IMG src="images/Clear.gif" width="5" height="5"></TD>
											</TR>
										</TABLE>
									</TD>
								</TR>
								<TR> 
									<TD class="ColumnBackground" width="25%">&nbsp;
										* &nbsp;<bean:message key="memberId" name="rmForm" />
									</TD>
									<TD class="TableData" width="25%">
										<html:select property="memberId" name="rmForm" onchange="javascript:submitForm('showParticipatingBankLimits.do?method=getParticipatingBanks')">
											<html:option value="">Select </html:option>
											<html:options name="rmForm" property="mliList"/>
										</html:select>
									</TD>
									<TD class="ColumnBackground" width="25%">&nbsp;
										* &nbsp;<bean:message key="bankName" name="rmForm" />
									</TD>
									<TD class="TableData" width="25%">
										<html:select property="bankName" name="rmForm" onchange="javascript:submitForm('showParticipatingBankLimits.do?method=getParticipatingBanksLimits')">
											<html:option value="">Select </html:option>
											<html:options name="rmForm" property="participatingBanksList"/>
										</html:select>
									</TD>
								</TR>
								<bean:define id="parLimitVal" name="rmForm" property="amount" />
		<%
			Double dparLimitVal = (Double)parLimitVal;
			String sparLimitVal = decimalFormat.format(dparLimitVal.doubleValue());
		%>
								<TR> 
									<TD class="ColumnBackground" width="25%">&nbsp;
									* &nbsp;	<bean:message key="amount" name="rmForm" />
									</TD>
									<TD class="TableData" width="75%" colspan="3">
										<html:text name="rmForm" property="amount" size="20" onkeypress="return decimalOnly(this, event,13)" onkeyup="isValidDecimal(this)" maxlength="16" value="<%=sparLimitVal%>"/>&nbsp;<bean:message key="inRs" />
									</TD>
								</TR>
								<TR> 
									<TD class="ColumnBackground" width="25%">&nbsp;
										* &nbsp;<bean:message key="validFrom" name="rmForm" />
									</TD>
									<TD class="TableData" width="25%">
										<html:text name="rmForm" property="validFrom" size="20" maxlength="10"/>
										<IMG src="images/CalendarIcon.gif" width="20" onClick="showCalendar('rmForm.validFrom')" align="center">
									</TD>
									<TD class="ColumnBackground" width="25%">&nbsp;
										<bean:message key="validTo" name="rmForm" />
									</TD>
									<TD class="TableData" width="25%">
										<html:text name="rmForm" property="validTo" size="20" maxlength="10"/>
										<IMG src="images/CalendarIcon.gif" width="20" onClick="showCalendar('rmForm.validTo')" align="center">
									</TD>
								</TR>
							</TABLE>
						</TD>
					</TR>
					<TR >
						<TD align="center" valign="baseline" >
							<DIV align="center">
								<A href="javascript:submitForm('setParticipatingBankLimits.do?method=setParticipatingBankLimits')">
									<IMG src="images/Save.gif" alt="Save" width="49" height="37" border="0"></A>
									<A href="javascript:document.rmForm.reset()">
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