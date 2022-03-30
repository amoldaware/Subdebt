<%@ page language="java"%>
<%@ taglib uri="/WEB-INF/TLD/struts-html.tld" prefix="html" %>
<%@ taglib uri="/WEB-INF/TLD/struts-bean.tld" prefix="bean" %>
<%@ include file="/jsp/SetMenuInfo.jsp" %>
<% session.setAttribute("CurrentPage","showSubSchemeValues.do");%>

<%
String focusField="noOfDays";
org.apache.struts.action.ActionErrors errors = (org.apache.struts.action.ActionErrors)request.getAttribute(org.apache.struts.Globals.ERROR_KEY);
if (errors!=null && !errors.isEmpty())
{
	focusField="test";
}
%>
<body onLoad="enableAppFilingTimeLimit(),enableDefaultRate()">
	<html:form action="setSubSchemeValues.do" method="POST" focus="<%=focusField%>">
	<html:hidden name="rmForm" property="test"/>
	<html:errors />
<TABLE width="100%" border="0" cellspacing="0" cellpadding="0">
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
										<A href="showSubSchemeDetailsHelp.do">HELP</A>
									</TD>
								</TR>
								<TR> 
									<TD colspan="4">
										<TABLE width="100%" border="0" cellspacing="0" cellpadding="0">
											<TR> 
												<TD width="25%" height="19" class="Heading">&nbsp;
													<bean:message key="subSchemeDetailsHeading" />
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
									<TD width="25%" class="ColumnBackground" rowspan="2">
										<DIV align="left"> &nbsp;
											* &nbsp;<bean:message key="appFilingTimeLimit" name="rmForm" />
										</DIV>
									</TD>
									<TD class="TableData">
										<DIV align="left">
											<bean:message key="rule" name="rmForm"/>
						                </DIV>
									</TD>
									<TD class="TableData">
										<DIV align="left">
											<html:radio property="rule" name="rmForm" value="Days" onclick="javascript:enableAppFilingTimeLimit()">
												<bean:message key="days" name="rmForm"/>
											</html:radio>
										</DIV>
									<TD class="TableData">
										<DIV align="left">
											<html:radio property="rule" name="rmForm" value="Periodicity"  onclick="javascript:enableAppFilingTimeLimit()">
												<bean:message key="periodicity" name="rmForm"/>
											</html:radio>
											<br>&nbsp;
											<bean:message key="periodicityHint" name="rmForm"/>
						                </DIV>
									</TD>
								</TR>
								<TR>
									<TD class="TableData"></TD>
									<TD class="TableData">
										<DIV align="left">
											<bean:message key="noOfDays" name="rmForm"/>&nbsp;&nbsp;
											<html:text name="rmForm" property="noOfDays" size="12" maxlength="3" onkeypress="return numbersOnly(this, event)" onkeyup="isValidNumber(this)"/>
										</DIV>
									</TD>
									<TD class="TableData">
										<DIV align="left">
											<bean:message key="select" name="rmForm"/>&nbsp;&nbsp;&nbsp;
											<html:select property="periodicity" name="rmForm">
												<html:option value="">Select </html:option>
												<html:option value="-1">Monthly </html:option>
												<html:option value="-2">Bi-Monthly </html:option>
												<html:option value="-3">Quarterly </html:option>
												<html:option value="-4">Half Yearly </html:option>
												<html:option value="-6">Yearly </html:option>
											</html:select>
											<br><br>&nbsp;
											<bean:message key="periodicityExample" name="rmForm"/>
										</DIV>
									</TD>
								</TR>
								<TR> 
									<TD width="25%" class="ColumnBackground">
										<DIV align="left"> &nbsp;
											* &nbsp;<bean:message key="validFrom" name="rmForm" />
										</DIV>
									</TD>
									<TD class="TableData" width="20%">
										<DIV align="left">
											<html:text property="appFilingTimeLimitValidFrom" size="20"  alt="Valid From" name="rmForm" maxlength="10"/>
											<IMG src="images/CalendarIcon.gif" width="20" onClick="showCalendar('rmForm.appFilingTimeLimitValidFrom')" align="center">
						                </DIV>
									</TD>
									<TD width="20%" class="ColumnBackground">
										<DIV align="left"> &nbsp;
											<bean:message key="validTo" name="rmForm" />
										</DIV>
									</TD>
									<TD class="TableData" width="25%">
										<DIV align="left">
											<html:text property="appFilingTimeLimitValidTo" size="20"  alt="Valid To" name="rmForm" maxlength="10"/>
											<IMG src="images/CalendarIcon.gif" width="20" onClick="showCalendar('rmForm.appFilingTimeLimitValidTo')" align="center">
						                </DIV>
									</TD>
								</TR>
<!--								<TR> 
									<TD width="35%" class="ColumnBackground">
										<DIV align="left"> &nbsp;
											* &nbsp;<bean:message key="maxGCoverExposure" name="rmForm" />
										</DIV>
									</TD>
									<TD class="TableData" colspan="3">
										<DIV align="left">
											<html:text property="maxGCoverExposure" name="rmForm" size="20" maxlength="5" onkeypress="return numbersOnly(this, event)" onkeyup="isValidNumber(this)"/> <bean:message key="inPer" name="rmForm" />
						                </DIV>
									</TD>
								</TR>
								<TR> 
									<TD width="25%" class="ColumnBackground">
										<DIV align="left"> &nbsp;
											* &nbsp;<bean:message key="validFrom" name="rmForm" />
										</DIV>
									</TD>
									<TD class="TableData" width="20%">
										<DIV align="left">
											<html:text property="maxGCoverExposureValidFrom" size="20"  alt="Valid From" name="rmForm" maxlength="10"/>
											<IMG src="images/CalendarIcon.gif" width="20" onClick="showCalendar('rmForm.maxGCoverExposureValidFrom')" align="center">
						                </DIV>
									</TD>
									<TD width="20%" class="ColumnBackground">
										<DIV align="left"> &nbsp;
											<bean:message key="validTo" name="rmForm" />
										</DIV>
									</TD>
									<TD class="TableData" width="25%">
										<DIV align="left">
											<html:text property="maxGCoverExposureValidTo" size="20"  alt="Valid To" name="rmForm" maxlength="10"/>
											<IMG src="images/CalendarIcon.gif" width="20" onClick="showCalendar('rmForm.maxGCoverExposureValidTo')" align="center">
						                </DIV>
									</TD>
								</TR>
								<TR>
									<TD width="25%" class="ColumnBackground">
										<DIV align="left"> &nbsp;
											* &nbsp;<bean:message key="minSanctionedAmount" name="rmForm" />
										</DIV>
									</TD>
									<TD class="TableData" colspan="3">
										<DIV align="left">
											<html:text property="minSanctionedAmount" name="rmForm" size="20" maxlength="13" onkeypress="return numbersOnly(this, event)" onkeyup="isValidNumber(this)"/> <bean:message key="inRs" name="rmForm" />
						                </DIV>
									</TD>
								</TR>
								<TR> 
									<TD width="25%" class="ColumnBackground">
										<DIV align="left"> &nbsp;
											* &nbsp;<bean:message key="validFrom" name="rmForm" />
										</DIV>
									</TD>
									<TD class="TableData" width="20%">
										<DIV align="left">
											<html:text property="minSanctionedAmtValidFrom" size="20"  alt="Valid From" name="rmForm" maxlength="10"/>
											<IMG src="images/CalendarIcon.gif" width="20" onClick="showCalendar('rmForm.minSanctionedAmtValidFrom')" align="center">
						                </DIV>
									</TD>
									<TD width="20%" class="ColumnBackground">
										<DIV align="left"> &nbsp;
											<bean:message key="validTo" name="rmForm" />
										</DIV>
									</TD>
									<TD class="TableData" width="25%">
										<DIV align="left">
											<html:text property="minSanctionedAmtValidTo" size="20"  alt="Valid To" name="rmForm" maxlength="10"/>
											<IMG src="images/CalendarIcon.gif" width="20" onClick="showCalendar('rmForm.minSanctionedAmtValidTo')" align="center">
						                </DIV>
									</TD>
								</TR>
								<TR> 
									<TD width="25%" class="ColumnBackground">
										<DIV align="left"> &nbsp;
											* &nbsp;<bean:message key="maxSanctionedAmount" name="rmForm" />
										</DIV>
									</TD>
									<TD class="TableData" colspan="3">
										<DIV align="left">
											<html:text property="maxSanctionedAmount" name="rmForm" size="20" maxlength="13" onkeypress="return numbersOnly(this, event)" onkeyup="isValidNumber(this)"/> <bean:message key="inRs" name="rmForm" />
						                </DIV>
									</TD>
								</TR>
								<TR> 
									<TD width="25%" class="ColumnBackground">
										<DIV align="left"> &nbsp;
											* &nbsp;<bean:message key="validFrom" name="rmForm" />
										</DIV>
									</TD>
									<TD class="TableData" width="20%">
										<DIV align="left">
											<html:text property="maxSanctionedAmtValidFrom" size="20"  alt="Valid From" name="rmForm" maxlength="10"/>
											<IMG src="images/CalendarIcon.gif" width="20" onClick="showCalendar('rmForm.maxSanctionedAmtValidFrom')" align="center">
						                </DIV>
									</TD>
									<TD width="20%" class="ColumnBackground">
										<DIV align="left"> &nbsp;
											<bean:message key="validTo" name="rmForm" />
										</DIV>
									</TD>
									<TD class="TableData" width="25%">
										<DIV align="left">
											<html:text property="maxSanctionedAmtValidTo" size="20"  alt="Valid To" name="rmForm" maxlength="10"/>
											<IMG src="images/CalendarIcon.gif" width="20" onClick="showCalendar('rmForm.maxSanctionedAmtValidTo')" align="center">
						                </DIV>
									</TD>
								</TR>-->
								<TR> 
									<TD width="25%" class="ColumnBackground">
										<DIV align="left"> &nbsp;
											* &nbsp;<bean:message key="maxBorrowerExposureAmount" name="rmForm" />
										</DIV>
									</TD>
									<TD class="TableData" colspan="3">
										<DIV align="left">
											<html:text property="maxBorrowerExposureAmount" name="rmForm" size="20" maxlength="16" onkeypress="return decimalOnly(this, event,13)" onkeyup="isValidDecimal(this)"/> <bean:message key="inRs" name="rmForm" />
						                </DIV>
									</TD>
								</TR>
								<TR> 
									<TD width="25%" class="ColumnBackground">
										<DIV align="left"> &nbsp;
											* &nbsp;<bean:message key="validFrom" name="rmForm" />
										</DIV>
									</TD>
									<TD class="TableData" width="20%">
										<DIV align="left">
											<html:text property="maxBorrowerExpAmtValidFrom" size="20"  alt="Valid From" name="rmForm" maxlength="10"/>
											<IMG src="images/CalendarIcon.gif" width="20" onClick="showCalendar('rmForm.maxBorrowerExpAmtValidFrom')" align="center">
						                </DIV>
									</TD>
									<TD width="20%" class="ColumnBackground">
										<DIV align="left"> &nbsp;
											<bean:message key="validTo" name="rmForm" />
										</DIV>
									</TD>
									<TD class="TableData" width="25%">
										<DIV align="left">
											<html:text property="maxBorrowerExpAmtValidTo" size="20"  alt="Valid To" name="rmForm" maxlength="10"/>
											<IMG src="images/CalendarIcon.gif" width="20" onClick="showCalendar('rmForm.maxBorrowerExpAmtValidTo')" align="center">
						                </DIV>
									</TD>
								</TR>
<!--								<TR> 
									<TD width="25%" class="ColumnBackground">
										<DIV align="left"> &nbsp;
											* &nbsp;<bean:message key="guaranteeCoverExtent" name="rmForm" />
										</DIV>
									</TD>
									<TD class="TableData" colspan="3">
										<DIV align="left">
											<html:text property="guaranteeCoverExtent" name="rmForm" size="20" maxlength="13" onkeypress="return numbersOnly(this, event)" onkeyup="isValidNumber(this)"/> <bean:message key="inRs" name="rmForm" />
						                </DIV>
									</TD>
								</TR>
								<TR> 
									<TD width="25%" class="ColumnBackground">
										<DIV align="left"> &nbsp;
											* &nbsp;<bean:message key="validFrom" name="rmForm" />
										</DIV>
									</TD>
									<TD class="TableData" width="20%">
										<DIV align="left">
											<html:text property="guaranteeCoverExtentValidFrom" size="20"  alt="Valid From" name="rmForm" maxlength="10"/>
											<IMG src="images/CalendarIcon.gif" width="20" onClick="showCalendar('rmForm.guaranteeCoverExtentValidFrom')" align="center">
						                </DIV>
									</TD>
									<TD width="20%" class="ColumnBackground">
										<DIV align="left"> &nbsp;
											<bean:message key="validTo" name="rmForm" />
										</DIV>
									</TD>
									<TD class="TableData" width="25%">
										<DIV align="left">
											<html:text property="guaranteeCoverExtentValidTo" size="20"  alt="Valid To" name="rmForm" maxlength="10"/>
											<IMG src="images/CalendarIcon.gif" width="20" onClick="showCalendar('rmForm.guaranteeCoverExtentValidTo')" align="center">
						                </DIV>
									</TD>
								</TR>-->
								<TR> 
									<TD width="25%" class="ColumnBackground">
										<DIV align="left"> &nbsp;
											* &nbsp;<bean:message key="serviceFeeCardRate" name="rmForm" />
										</DIV>
									</TD>
									<TD class="TableData" colspan="3">
										<DIV align="left">
											<html:text property="serviceFeeCardRate" name="rmForm" size="20" maxlength="5" onkeypress="return decimalOnly(this, event,2)" onkeyup="isValidDecimal(this)"/> <bean:message key="inPer" name="rmForm" />
						                </DIV>
									</TD>
								</TR>
								<TR> 
									<TD width="25%" class="ColumnBackground">
										<DIV align="left"> &nbsp;
											* &nbsp;<bean:message key="validFrom" name="rmForm" />
										</DIV>
									</TD>
									<TD class="TableData" width="20%">
										<DIV align="left">
											<html:text property="serviceFeeCardRateValidFrom" size="20"  alt="Valid From" name="rmForm" maxlength="10"/>
											<IMG src="images/CalendarIcon.gif" width="20" onClick="showCalendar('rmForm.serviceFeeCardRateValidFrom')" align="center">
						                </DIV>
									</TD>
									<TD width="20%" class="ColumnBackground">
										<DIV align="left"> &nbsp;
											<bean:message key="validTo" name="rmForm" />
										</DIV>
									</TD>
									<TD class="TableData" width="25%">
										<DIV align="left">
											<html:text property="serviceFeeCardRateValidTo" size="20"  alt="Valid To" name="rmForm" maxlength="10"/>
											<IMG src="images/CalendarIcon.gif" width="20" onClick="showCalendar('rmForm.serviceFeeCardRateValidTo')" align="center">
						                </DIV>
									</TD>
								</TR>
<!--								<TR> 
									<TD width="25%" class="ColumnBackground">
										<DIV align="left"> &nbsp;
											* &nbsp;<bean:message key="guaranteeFeeCardRate" name="rmForm" />
										</DIV>
									</TD>
									<TD class="TableData" colspan="3">
										<DIV align="left">
											<html:text property="guaranteeFeeCardRate" name="rmForm" size="20" maxlength="5" onkeypress="return decimalOnly(this, event,2)" onkeyup="isValidDecimal(this)"/> <bean:message key="inPer" name="rmForm" />
						                </DIV>
									</TD>
								</TR>
								<TR> 
									<TD width="25%" class="ColumnBackground">
										<DIV align="left"> &nbsp;
											* &nbsp;<bean:message key="validFrom" name="rmForm" />
										</DIV>
									</TD>
									<TD class="TableData" width="20%">
										<DIV align="left">
											<html:text property="guaranteeFeeCardRateValidFrom" size="20"  alt="Valid From" name="rmForm" maxlength="10"/>
											<IMG src="images/CalendarIcon.gif" width="20" onClick="showCalendar('rmForm.guaranteeFeeCardRateValidFrom')" align="center">
						                </DIV>
									</TD>
									<TD width="20%" class="ColumnBackground">
										<DIV align="left"> &nbsp;
											<bean:message key="validTo" name="rmForm" />
										</DIV>
									</TD>
									<TD class="TableData" width="25%">
										<DIV align="left">
											<html:text property="guaranteeFeeCardRateValidTo" size="20"  alt="Valid To" name="rmForm" maxlength="10"/>
											<IMG src="images/CalendarIcon.gif" width="20" onClick="showCalendar('rmForm.guaranteeFeeCardRateValidTo')" align="center">
						                </DIV>
									</TD>
								</TR>-->
								<TR> 
									<TD width="25%" class="ColumnBackground">
										<DIV align="left"> &nbsp;
											* &nbsp;<bean:message key="defaultRateApplicable" name="rmForm" />
										</DIV>
									</TD>
									<TD class="TableData" colspan="3">
										<DIV align="left">
											<html:radio property="defaultRateApplicable" name="rmForm" value="Y" onclick="javascript:enableDefaultRate()"><bean:message key="yes" name="rmForm" /></html:radio>
											<html:radio property="defaultRateApplicable" name="rmForm" value="N" onclick="javascript:enableDefaultRate()"><bean:message key="no" name="rmForm" /></html:radio>
										</DIV>
									</TD>
								</TR>
								<TR> 
									<TD width="25%" class="ColumnBackground">
										<DIV align="left"> &nbsp;
											* &nbsp;<bean:message key="validFrom" name="rmForm" />
										</DIV>
									</TD>
									<TD class="TableData" width="20%">
										<DIV align="left">
											<html:text property="defRateApplicableValidFrom" size="20"  alt="Valid From" name="rmForm" maxlength="10"/>
											<IMG src="images/CalendarIcon.gif" width="20" onClick="showCalendar('rmForm.defRateApplicableValidFrom')" align="center">
						                </DIV>
									</TD>
									<TD width="20%" class="ColumnBackground">
										<DIV align="left"> &nbsp;
											<bean:message key="validTo" name="rmForm" />
										</DIV>
									</TD>
									<TD class="TableData" width="25%">
										<DIV align="left">
											<html:text property="defRateApplicableValidTo" size="20"  alt="Valid To" name="rmForm" maxlength="10"/>
											<IMG src="images/CalendarIcon.gif" width="20" onClick="showCalendar('rmForm.defRateApplicableValidTo')" align="center">
						                </DIV>
									</TD>
								</TR>
								<TR> 
									<TD width="25%" class="ColumnBackground">
										<DIV align="left"> &nbsp;
											<bean:message key="defaultRate" name="rmForm" />
										</DIV>
									</TD>
									<TD class="TableData" colspan="3">
										<DIV align="left">
											<html:text property="defaultRate" name="rmForm" size="20" maxlength="5" onkeypress="return decimalOnly(this, event,2)" onkeyup="isValidDecimal(this)"/>
											<bean:message key="inPer"  />
						                </DIV>
									</TD>
								</TR>
								<TR> 
									<TD width="25%" class="ColumnBackground">
										<DIV align="left"> &nbsp;
											<bean:message key="validFrom" name="rmForm" />
										</DIV>
									</TD>
									<TD class="TableData" width="20%">
										<DIV align="left">
											<html:text property="defRateValidFrom" size="20"  alt="Valid From" name="rmForm" maxlength="10"/>
											<IMG src="images/CalendarIcon.gif" width="20" onClick="showCalendar('rmForm.defRateValidFrom')" align="center">
						                </DIV>
									</TD>
									<TD width="20%" class="ColumnBackground">
										<DIV align="left"> &nbsp;
											<bean:message key="validTo" name="rmForm" />
										</DIV>
									</TD>
									<TD class="TableData" width="25%">
										<DIV align="left">
											<html:text property="defRateValidTo" size="20"  alt="Valid To" name="rmForm" maxlength="10"/>
											<IMG src="images/CalendarIcon.gif" width="20" onClick="showCalendar('rmForm.defRateValidTo')" align="center">
						                </DIV>
									</TD>
								</TR>
								<TR> 
									<TD width="25%" class="ColumnBackground">
										<DIV align="left"> &nbsp;
											* &nbsp;<bean:message key="moderationFactor" name="rmForm" />
										</DIV>
									</TD>
									<TD class="TableData" colspan="3">
										<DIV align="left">
											<html:text property="moderationFactor" name="rmForm" size="20" maxlength="5" onkeypress="return decimalOnly(this, event,2)" onkeyup="isValidDecimal(this)"/>
						                </DIV>
									</TD>
								</TR>
								<TR> 
									<TD width="25%" class="ColumnBackground">
										<DIV align="left"> &nbsp;
											* &nbsp;<bean:message key="validFrom" name="rmForm" />
										</DIV>
									</TD>
									<TD class="TableData" width="20%">
										<DIV align="left">
											<html:text property="moderationFactorValidFrom" size="20"  alt="Valid From" name="rmForm" maxlength="10"/>
											<IMG src="images/CalendarIcon.gif" width="20" onClick="showCalendar('rmForm.moderationFactorValidFrom')" align="center">
						                </DIV>
									</TD>
									<TD width="20%" class="ColumnBackground">
										<DIV align="left"> &nbsp;
											<bean:message key="validTo" name="rmForm" />
										</DIV>
									</TD>
									<TD class="TableData" width="25%">
										<DIV align="left">
											<html:text property="moderationFactorValidTo" size="20"  alt="Valid To" name="rmForm" maxlength="10"/>
											<IMG src="images/CalendarIcon.gif" width="20" onClick="showCalendar('rmForm.moderationFactorValidTo')" align="center">
						                </DIV>
									</TD>
								</TR>
							</TABLE>
						</TD>
					</TR>
					<TR >
						<TD align="center" valign="baseline" >
							<DIV align="center">
								<A href="javascript:submitForm('setSubSchemeValues.do?method=setSubScheme')">
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
		</TABLE>
	</html:form>
</body>