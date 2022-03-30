<%@ page language="java"%>
<%@ taglib uri="/WEB-INF/TLD/struts-html.tld" prefix="html" %>
<%@ taglib uri="/WEB-INF/TLD/struts-bean.tld" prefix="bean" %>
<%@ include file="/jsp/SetMenuInfo.jsp" %>
<%@page import ="java.text.DecimalFormat"%>
<%DecimalFormat decimalFormat = new DecimalFormat("#################.##");
decimalFormat.setDecimalSeparatorAlwaysShown(false);
%>
<% session.setAttribute("CurrentPage","showUserLimits.do?method=showUserLimits");%>
<TABLE width="100%" border="0" cellspacing="0" cellpadding="0">
	<html:errors />
	<html:form action="setUserLimits.do?method=setUserLimits" method="POST" focus="designation">
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
										<A href="showUserLimitsHelp.do">HELP</A>
									</TD>
								</TR>
								<TR> 
									<TD colspan="4">
										<TABLE width="100%" border="0" cellspacing="0" cellpadding="0">
											<TR> 
												<TD width="25%" height="19" class="Heading">&nbsp;
													<bean:message key="userLimitsHeading" />
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
									<TD class="ColumnBackground" width="24%">&nbsp;
										* &nbsp;<bean:message key="designationId" name="rmForm" />
									</TD>
									<TD class="TableData"colspan="3">
										<html:select property="designation" name="rmForm" onchange="javascript:submitForm('showUserLimitValue.do?method=showUserLimitValue')">
											<html:option value="">Select </html:option>
											<html:options name="rmForm" property="designationsList"/>
										</html:select>
									</TD>
								</TR>
									<bean:define id="upperApplicationApprovalLimitVal" name="rmForm" property="upperApplicationApprovalLimit" />
		<%
			Double dupperApplicationApprovalLimitVal = (Double)upperApplicationApprovalLimitVal;
			String supperApplicationApprovalLimitVal = decimalFormat.format(dupperApplicationApprovalLimitVal.doubleValue());
		%>
								<TR>
									<TD colspan="4">
										<TABLE width="100%" border="0" cellspacing="1" cellpadding="0">
																		
											<TR> 
												<TD width="24%" class="ColumnBackground">
													<DIV align="left">
														&nbsp;<bean:message key="upperApplicationApprovalLimit" name="rmForm" />
													</DIV>
												</TD>
												<TD class="TableData" colspan="3">
													<DIV align="left">
														<html:text property="upperApplicationApprovalLimit" size="20" alt="Application Approval Amount" name="rmForm" maxlength="16" onkeypress="return decimalOnly(this, event,13)" onkeyup="isValidDecimal(this)"
													 value="<%=supperApplicationApprovalLimitVal%>"	/>&nbsp;<bean:message key="inRs" />
													</DIV>
												</TD>
											</TR>
											<TR>
												<TD class="ColumnBackground" width="24%">&nbsp;
													<bean:message key="applicationLimitValidFrom" name="rmForm" />
												</TD>
												<TD class="TableData" align="center" width="25%">
													<html:text property="applicationLimitValidFrom" size="20"  alt="Limit Valid From" name="rmForm" maxlength="10"/>
													<IMG src="images/CalendarIcon.gif" width="20" onClick="showCalendar('rmForm.applicationLimitValidFrom')" align="center">
												</TD>
												<TD class="ColumnBackground" width="25%">&nbsp;
													<bean:message key="applicationLimitValidTo" name="rmForm" />
												</TD>
												<TD class="TableData" align="center" width="25%">
													<html:text property="applicationLimitValidTo" size="20"  alt="Limit Valid From" name="rmForm" maxlength="10"/>
													<IMG src="images/CalendarIcon.gif" width="20" onClick="showCalendar('rmForm.applicationLimitValidTo')" align="center">
												</TD>
											</TR>
											<bean:define id="upperClaimLimit" name="rmForm" property="upperClaimsApprovalLimit" />
		<%
			Double dupperClaimsLimitVal = (Double)upperClaimLimit;
			String supperClaimsLimitVal = decimalFormat.format(dupperClaimsLimitVal.doubleValue());
		%>
											<TR> 
												<TD class="ColumnBackground" width="24%">&nbsp;
													<bean:message key="upperClaimsApprovalLimit" name="rmForm" />
												</TD>
												<TD class="TableData" colspan="3">
													<html:text property="upperClaimsApprovalLimit" size="20" alt="Claim Approval Amount" name="rmForm" maxlength="16" onkeypress="return decimalOnly(this, event,13)" onkeyup="isValidDecimal(this)"
													value="<%=supperClaimsLimitVal%>"/>&nbsp;<bean:message key="inRs" />
												</TD>
											</TR>
											<TR>
												<TD class="ColumnBackground" width="24%">&nbsp;
													<bean:message key="claimsLimitValidFrom" name="rmForm" />
												</TD>
												<TD class="TableData" align="center" width="25%">
													<html:text property="claimsLimitValidFrom" size="20"  alt="Limit Valid From" name="rmForm" maxlength="10"/>
													<IMG src="images/CalendarIcon.gif" width="20" onClick="showCalendar('rmForm.claimsLimitValidFrom')" align="center">
												</TD>
												<TD class="ColumnBackground" width="25%">&nbsp;
													<bean:message key="claimsLimitValidTo" name="rmForm" />
												</TD>
												<TD class="TableData" align="center" width="25%">
													<html:text property="claimsLimitValidTo" size="20"  alt="Limit Valid From" name="rmForm" maxlength="10"/>
													<IMG src="images/CalendarIcon.gif" width="20" onClick="showCalendar('rmForm.claimsLimitValidTo')" align="center">
												</TD>
											</TR>
										</TABLE>
									</TD>
								</TR>
							</TABLE>
						</TD>
					</TR>
					<TR >
						<TD align="center" valign="baseline" >
							<DIV align="center">
								<A href="javascript:submitForm('setUserLimits.do?method=setUserLimits')">
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