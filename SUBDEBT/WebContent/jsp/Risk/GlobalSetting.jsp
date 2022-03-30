<%@ page language="java"%>
<%@ taglib uri="/WEB-INF/TLD/struts-html.tld" prefix="html" %>
<%@ taglib uri="/WEB-INF/TLD/struts-bean.tld" prefix="bean" %>
<%@ include file="/jsp/SetMenuInfo.jsp" %>
<% session.setAttribute("CurrentPage","showGlobalLimits.do?method=showGlobalLimits");%>
<%@page import ="java.text.DecimalFormat"%>
<%DecimalFormat decimalFormat = new DecimalFormat("#################.##");
decimalFormat.setDecimalSeparatorAlwaysShown(false);
%>

<TABLE width="100%" border="0" cellspacing="0" cellpadding="0">
	<html:errors />
	<html:form action="setGlobalLimits.do?method=setGlobalLimits" method="POST" focus="scheme">
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
										<A href="showGlobalSettingHelp.do">HELP</A>
									</TD>
								</TR>
								<TR> 
									<TD colspan="4">
										<TABLE width="100%" border="0" cellspacing="0" cellpadding="0">
											<TR> 
												<TD width="25%" height="19" class="Heading">&nbsp;
													<bean:message key="globalLimitsHeading" />
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
									<TD width="25%" class="ColumnBackground">
										<DIV align="left"> &nbsp;
											* &nbsp;<bean:message key="scheme" name="rmForm" />
										</DIV>
									</TD>
									<TD class="TableData" width="25%">
										<DIV align="left">
											<html:select property="scheme" name="rmForm">
												<html:option value="">Select </html:option>
												<html:options name="rmForm" property="schemeList"/>
											</html:select>
						                </DIV>
									</TD>
									<TD class="ColumnBackground" width="25%" > &nbsp;
										* &nbsp;<bean:message key="subScheme" name="rmForm" />
									</TD>
									<TD class="TableData" width="25%">
										<html:select property="subScheme" name="rmForm" onchange="javascript:submitForm('showGlobalLimitValue.do?method=showGlobalLimitValue')">
											<html:option value="">Select </html:option>
											<html:options name="rmForm" property="subSchemeList"/>
										</html:select>
									</TD>
								</TR>
<!--								<TR> 
									<TD class="ColumnBackground" width="25%">&nbsp;
										* &nbsp;<bean:message key="isFundsBasedOrNonFundsBasedOrBoth" name="rmForm" />
									</TD>
									<TD class="TableData" colspan="3">
										<html:radio name="rmForm" value="0" property="isFundsBasedOrNonFundsBasedOrBoth"> <bean:message key="fundBased" name="rmForm" /> </html:radio>
										<html:radio name="rmForm" value="1" property="isFundsBasedOrNonFundsBasedOrBoth"> 
										<bean:message key="nonFundBased" name="rmForm" /></html:radio>
										<html:radio name="rmForm" value="2" property="isFundsBasedOrNonFundsBasedOrBoth"> <bean:message key="both" name="rmForm" /> </html:radio>
									</TD>
								</TR>
	-->
<bean:define id="limitVal" name="rmForm" property="upperLimit" />
		<%
			Double dlimitVal = (Double)limitVal;
			String slimitVal = decimalFormat.format(dlimitVal.doubleValue());
		%>
								<TR>
									<TD class="ColumnBackground">&nbsp;
										* &nbsp;<bean:message key="upperLimit" name="rmForm" />
									</TD>
									<TD class="TableData" colspan="3">
										<html:text property="upperLimit" size="20" alt="Upper Limit" name="rmForm" maxlength="16" onkeypress="return decimalOnly(this, event,13)" onkeyup="isValidDecimal(this)" value="<%=slimitVal%>"/>&nbsp;<bean:message key="inRs" />
									</TD>
								</TR>
								<TR>
									<TD class="ColumnBackground">&nbsp;
										* &nbsp;<bean:message key="validFromDate" name="rmForm" />
									</TD>
									<TD class="TableData" align="center">
										<html:text property="validFromDate" size="20"  alt="Limit Valid From" name="rmForm" maxlength="10"/>
										<IMG src="images/CalendarIcon.gif" width="20" onClick="showCalendar('rmForm.validFromDate')" align="center">
									</TD>
									<TD class="ColumnBackground">&nbsp;
										<bean:message key="validToDate" name="rmForm" />
									</TD>
									<TD class="TableData" align="center">
										<html:text property="validToDate" size="20"  alt="Limit Valid To" name="rmForm" maxlength="10"/>
										<IMG src="images/CalendarIcon.gif" width="20" onClick="showCalendar('rmForm.validToDate')" align="center">
									</TD>
								</TR>
							</TABLE>
						</TD>
					</TR>
					<TR >
						<TD align="center" valign="baseline" >
							<DIV align="center">
								
								<A href="javascript:submitForm('setGlobalLimits.do?method=setGlobalLimits')">
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
