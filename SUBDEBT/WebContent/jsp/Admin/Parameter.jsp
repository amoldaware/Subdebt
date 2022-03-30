<%@ page language="java"%>
<%@ taglib uri="/WEB-INF/TLD/struts-html.tld" prefix="html" %>
<%@ taglib uri="/WEB-INF/TLD/struts-bean.tld" prefix="bean" %>
<% session.setAttribute("CurrentPage","showParameter.do?method=showParameter");%>
<%@ include file="/jsp/SetMenuInfo.jsp" %>
<HTML>
	<BODY>
		<TABLE width="725" border="0" cellpadding="0" cellspacing="0">
		<body onload="enableAppFilingTimeLimitInParameterPage()">
			<html:errors />
			<html:form action="addParameter.do" method="POST" focus="activeUserLimit">
				<TR>
					<TD width="20" align="right" valign="bottom"><IMG src="images/TableLeftTop.gif" width="20" height="31"></TD>
					<TD background="images/TableBackground1.gif"></TD>
					<TD width="20" align="left" valign="bottom"><IMG src="images/TableRightTop.gif" width="23" height="31"></TD>
				</TR>
				<TR>
					<TD width="20" background="images/TableVerticalLeftBG.gif">&nbsp;</TD>
					<TD>
					<DIV align="right">
				<A HREF="javascript:submitForm('helpParameter.do?method=helpParameter')">
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
														<TD width="31%" class="Heading"><bean:message key="parameterMasterHeader" /></TD>
														<TD><IMG src="images/TriangleSubhead.gif" width="19" height="19"></TD>
													</TR>
													<TR>
														<TD colspan="4" class="Heading"><IMG src="images/Clear.gif" width="5" height="5"></TD>
													</TR>
												</TABLE>
											</TD>
										</TR>
										<TR align="left">
											<TD align="left" valign="top" class="ColumnBackground">
												&nbsp;<font color="#FF0000" size="2">*</font><bean:message key="activeUserLimit" />
											</TD>
											<TD align="left" class="TableData">
												<html:text property="activeUserLimit" maxlength="3" size="20" alt="Active User Limit" name="adminForm"  onkeypress="return numbersOnly(this, event)" onkeyup="isValidNumber(this)"/>
											</TD>
										</TR>
										<TR align="left">
											<TD align="left" valign="top" class="ColumnBackground">
												&nbsp;<font color="#FF0000" size="2">*</font><bean:message key="passwordExpiry" />
											</TD>
											<TD align="left" class="TableData">
												<html:text property="passwordExpiryDays" maxlength="3" size="20" alt="Password Expiry" name="adminForm"  onkeypress="return numbersOnly(this, event)" onkeyup="isValidNumber(this)"/><bean:message key="inDays" />
											</TD>
										</TR>
										<TR align="left">
											<TD align="left" valign="top" class="ColumnBackground">
												&nbsp;<font color="#FF0000" size="2">*</font><bean:message key="passwordDisplayPeriod" />
											</TD>
											<TD align="left" class="TableData">
												<html:text property="passwordDisplayPeriod" maxlength="2" size="20" alt="Password Display Period" name="adminForm"  onkeypress="return numbersOnly(this, event)" onkeyup="isValidNumber(this)"/><bean:message key="inSeconds" />
											</TD>
										</TR>
										<TR align="left">
											<TD align="left" valign="top" class="ColumnBackground">
												&nbsp;<font color="#FF0000" size="2">*</font><bean:message key="workingCapitalTenure" />
											</TD>
											<TD align="left" class="TableData">
												<html:text property="wcTenorInYrs" maxlength="2" size="20" alt="Working Capital Tenure" name="adminForm"  onkeypress="return numbersOnly(this, event)" onkeyup="isValidNumber(this)"/><bean:message key="inYears" />
											</TD>
										</TR>
										<TR align="left">
											<TD align="left" valign="top" class="ColumnBackground">
												&nbsp;<font color="#FF0000" size="2">*</font><bean:message key="maxInterestRate" />&nbsp;&nbsp;<bean:message key="adminperpa" />
											</TD>
											<TD align="left" class="TableData">
												<html:text property="maxIntRateApplied" maxlength="5" size="20" alt="Max Interest Rate" name="adminForm"  onkeypress="return decimalOnly(this, event,2)" onkeyup="isValidDecimal(this)"/><bean:message key="PLR1" />
											</TD>
										</TR>


										<TR align="left">
											<TD align="left" valign="top" class="ColumnBackground">
											&nbsp;<font color="#FF0000" size="2">*</font><bean:message key="minAmtMandatoryPAN" />
											</TD>
											<TD align="left" class="TableData">
											<html:text property="minAmtForMandatoryITPAN" maxlength="16" size="20" alt="min Amt Mandatory PAN" name="adminForm"  onkeypress="return decimalOnly(this, event,13)" onkeyup="isValidDecimal(this)"/><bean:message key="inRs" />
											</TD>
										</TR>

										<TR align="left">
											<TD align="left" valign="top" class="ColumnBackground">
											&nbsp;<font color="#FF0000" size="2">*</font><bean:message key="serviceFeeWithoutPenalty" />
											</TD>
											<TD align="left" class="TableData">
											<html:text property="serviceFeeWithoutPenalty" maxlength="3" size="20" alt="Service Fee Without Penalty" name="adminForm" onkeypress="return numbersOnly(this, event)" onkeyup="isValidNumber(this)"/><bean:message key="inDays" />
											</TD>
										</TR>

										<TR align="left">
											<TD align="left" valign="top" class="ColumnBackground">
											&nbsp;<font color="#FF0000" size="2">*</font><bean:message key="serviceFeePenaltyRate" />
											</TD>
											<TD align="left" class="TableData">
											<html:text property="serviceFeePenaltyRate" maxlength="5" size="20" alt="service Fee Penalty Rate" name="adminForm" onkeypress="return decimalOnly(this, event,2)" onkeyup="isValidDecimal(this)"/><bean:message key="inPa" />
											</TD>
										</TR>

										<TR align="left">
											<TD align="left" valign="top" class="ColumnBackground">
											&nbsp;<font color="#FF0000" size="2">*</font><bean:message key="cgpanPerCgdan" />
											</TD>
											<TD align="left" class="TableData">
											<html:text property="noOfCgpansPerDAN" maxlength="3" size="20" alt="No of cgpans Per Cgdan" name="adminForm"  onkeypress="return numbersOnly(this, event)" onkeyup="isValidNumber(this)"/>
											</TD>
										</TR>

										<TR align="left">
											<TD align="left" valign="top" class="ColumnBackground">
											&nbsp;<font color="#FF0000" size="2">*</font><bean:message key="gfWithoutPenalty" />
											</TD>
											<TD align="left" class="TableData">
											<html:text property="guaranteeFeeWithoutPenalty" maxlength="3" size="20" alt="gf Without Penalty" name="adminForm"  onkeypress="return numbersOnly(this, event)" onkeyup="isValidNumber(this)"/><bean:message key="inDays" />
											</TD>
										</TR>

										<TR align="left">
											<TD align="left" valign="top" class="ColumnBackground">
											&nbsp;<font color="#FF0000" size="2">*</font><bean:message key="shortPaymentLimit" />
											</TD>
											<TD align="left" class="TableData">
											<html:text property="shortLimit" maxlength="16" size="20" alt="short Payment Limit" name="adminForm" onkeypress="return decimalOnly(this, event,13)" onkeyup="isValidDecimal(this)"/><bean:message key="inRs" />
											</TD>
										</TR>

										<TR align="left">
											<TD align="left" valign="top" class="ColumnBackground">
											&nbsp;<font color="#FF0000" size="2">*</font><bean:message key="excessPaymentLimit" />
											</TD>
											<TD align="left" class="TableData">
											<html:text property="excessLimit" maxlength="16" size="20" alt="Excess Payment Limit" name="adminForm" onkeypress="return decimalOnly(this, event,13)" onkeyup="isValidDecimal(this)"/><bean:message key="inRs" />
											</TD>
										</TR>
										<TR align="left">
											<TD align="left" valign="top" class="ColumnBackground">
											&nbsp;<font color="#FF0000" size="2">*</font><bean:message key="waiveLimit" />
											</TD>
											<TD align="left" class="TableData">
											<html:text property="waiveLimit" maxlength="16" size="20" alt="waive Limit" name="adminForm" onkeypress="return decimalOnly(this, event,13)" onkeyup="isValidDecimal(this)"/><bean:message key="inRs" />
											</TD>
										</TR>
										<TR align="left">
											<TD align="left" valign="top" class="ColumnBackground">
											&nbsp;<font color="#FF0000" size="2">*</font><bean:message key="gfFirstAlertDate" />
											</TD>
											<TD align="left" class="TableData">
											<html:text property="guaranteeFeeFirstAlert" maxlength="3" size="20" alt="GF First Alert Date" name="adminForm"  onkeypress="return numbersOnly(this, event)" onkeyup="isValidNumber(this)"/><bean:message key="inDaysAfterCGFDAN" />
											</TD>
										</TR>

										<TR align="left">
											<TD align="left" valign="top" class="ColumnBackground">
											&nbsp;<font color="#FF0000" size="2">*</font><bean:message key="gfAlertFrequency" />
											</TD>
											<TD align="left" class="TableData">
											<html:text property="guaranteeFeeAlertFrequency" maxlength="3" size="20" alt="GF Alert Frequency" name="adminForm" onkeypress="return numbersOnly(this, event)" onkeyup="isValidNumber(this)"/><bean:message key="inDays" />
											</TD>
										</TR>

										<TR align="left">
											<TD align="left" valign="top" class="ColumnBackground">
											&nbsp;<font color="#FF0000" size="2">*</font><bean:message key="sfCalDate" />
											</TD>
											<TD align="left" class="ColumnBackground" valign="top">
											<!--<html:text property="sfCalDay" size="20" alt="SF Cal Date" name="adminForm" value="31st March"/><IMG src="images/CalendarIcon.gif" width="20" onClick="showCalendar('adminForm.sfCalDate')" align="center">
											!-->
											&nbsp;<bean:message key="day"/>:&nbsp;
											<html:select property="serviceFeeCalculationDay" name="adminForm">
											<html:option value="1"/>
											<html:option value="2"/>
											<html:option value="3"/>
											<html:option value="4"/>
											<html:option value="5"/>
											<html:option value="6"/>
											<html:option value="7"/>
											<html:option value="8"/>
											<html:option value="9"/>
											<html:option value="10"/>
											<html:option value="11"/>
											<html:option value="12"/>
											<html:option value="13"/>
											<html:option value="14"/>
											<html:option value="15"/>
											<html:option value="16"/>
											<html:option value="17"/>
											<html:option value="18"/>
											<html:option value="19"/>
											<html:option value="20"/>
											<html:option value="21"/>
											<html:option value="22"/>
											<html:option value="23"/>
											<html:option value="24"/>
											<html:option value="25"/>
											<html:option value="26"/>
											<html:option value="27"/>
											<html:option value="28"/>
											<html:option value="29"/>
											<html:option value="30"/>
											<html:option value="31"/>
											</html:select>

											&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
											<bean:message key="month"/>:
											<html:select property="serviceFeeCalculationMonth" name="adminForm">
											<html:option value="January"/>
											<html:option value="February"/>
											<html:option value="March"/>
											<html:option value="April"/>
											<html:option value="May"/>
											<html:option value="June"/>
											<html:option value="July"/>
											<html:option value="August"/>
											<html:option value="September"/>
											<html:option value="October"/>
											<html:option value="November"/>
											<html:option value="December"/>
											</html:select>

											</TD>

										</TR>


										<TR align="left">
											<TD align="left" valign="top" class="ColumnBackground">
											&nbsp;<font color="#FF0000" size="2">*</font><bean:message key="sfCalFreqency" />
											</TD>
											<TD align="left" class="TableData">
											<html:text property="serviceFeeAlertFreq" maxlength="3" size="20" alt="SF Cal Frequency" name="adminForm" onkeypress="return numbersOnly(this, event)" onkeyup="isValidNumber(this)"/><bean:message key="inDays" />
											</TD>
										</TR>


										<TR align="left">
											<TD align="left" valign="top" class="ColumnBackground">
											&nbsp;<font color="#FF0000" size="2">*</font><bean:message key="periodicInfoFrequency" />
											</TD>
											<TD align="left" class="TableData">
												<html:select property="periodicInfoFrequency" name="adminForm">
																<html:option value="">Select</html:option>
																<html:option value="Monthly">Monthly</html:option>
																<html:option value="BiMonthly">BiMonthly</html:option>
																<html:option value="Quaterly">Quarterly</html:option>
																<html:option value="HalfYearly">HalfYearly</html:option>
												</html:select>
											</TD>
										</TR>

										<TR align="left">
											<TD align="left" valign="top" class="ColumnBackground">
											&nbsp;<font color="#FF0000" size="2">*</font><bean:message key="periodicInfoAlertFreq" />
											</TD>
											<TD align="left" class="TableData">
											<html:text property="periodicInfoAlertFreq" maxlength="3" size="20" alt="periodic Info Alert Freq" name="adminForm" onkeypress="return numbersOnly(this, event)" onkeyup="isValidNumber(this)"/><bean:message key="inDays" />
											</TD>
										</TR>

										<TR align="left">
											<TD align="left" valign="top" class="ColumnBackground">
											&nbsp;<font color="#FF0000" size="2">*</font><bean:message key="bankRate" />
											</TD>
											<TD align="left" class="TableData">
											<html:text property="bankRate" maxlength="5" size="20" alt="bank Rate" name="adminForm" onkeypress="return decimalOnly(this, event,2)" onkeyup="isValidDecimal(this)"/><bean:message key="inPa" />
											</TD>
										</TR>
										<TR align="left">
											<TD align="left" valign="top" class="ColumnBackground">
											&nbsp;<font color="#FF0000" size="2">*</font><bean:message key="gfPenaltyRate" />
											</TD>
											<TD align="left" class="TableData">
											<html:text property="guaranteeFeePenaltyRate" maxlength="5" size="20" alt="gf Penalty Rate" name="adminForm" onkeypress="return decimalOnly(this, event,2)" onkeyup="isValidDecimal(this)"/><bean:message key="inPa" />
											</TD>
										</TR>
										
										<TR align="left">
											<TD align="left" valign="top" class="ColumnBackground">
											&nbsp;<font color="#FF0000" size="2">*</font><bean:message key="firstInstallClaim" />
											</TD>
											<TD align="left" class="TableData">
											<html:text property="firstInstallClaim" maxlength="2" size="20" alt="first Install Claim" name="adminForm"  onkeypress="return numbersOnly(this, event)" onkeyup="isValidNumber(this)"/><bean:message key="in" />
											</TD>
										</TR>

										<TR align="left">
											<TD align="left" valign="top" class="ColumnBackground">
											&nbsp;<font color="#FF0000" size="2">*</font><bean:message key="lockInPeriod" />
											</TD>
											<TD align="left" class="TableData">
											<html:text property="lockInPeriod" maxlength="2" size="20" alt="lock In Period" name="adminForm"  onkeypress="return numbersOnly(this, event)" onkeyup="isValidNumber(this)"/><bean:message key="inMonths" />
											</TD>
										</TR>										
										
										<TR align="left">
											<TD align="left" valign="top" class="ColumnBackground">
											&nbsp;<font color="#FF0000" size="2">*</font><bean:message key="periodClaimWithoutPenalty" />
											</TD>
											<TD align="left" class="TableData">
											<html:text property="claimSettlementWithoutPenalty" maxlength="3" size="20" alt="period Claim Without Penalty" name="adminForm"  onkeypress="return numbersOnly(this, event)" onkeyup="isValidNumber(this)"/><bean:message key="inDays" />
											</TD>
										</TR>										

										<TR align="left">
											<TD align="left" valign="top" class="ColumnBackground">
											&nbsp;<font color="#FF0000" size="2">*</font><bean:message key="claimPenaltyRate" />
											</TD>
											<TD align="left" class="TableData">
											<html:text property="claimPenaltyRate" maxlength="5" size="20" alt="claim Penalty Rate" name="adminForm" onkeypress="return decimalOnly(this, event,2)" onkeyup="isValidDecimal(this)"/><bean:message key="adminperpa" />
											</TD>
										</TR>

										<TR align="left">
											<TD align="left" valign="top" class="ColumnBackground">
											&nbsp;<font color="#FF0000" size="2">*</font><bean:message key="penaltyCalculation" />
											</TD>
											<TD align="left" class="TableData">
											<html:radio name="adminForm" value="daily" property="penaltyCalculationType" ><bean:message key="daily" /></html:radio>&nbsp;&nbsp;<html:radio name="adminForm" value="monthly" property="penaltyCalculationType"><bean:message key="monthly"/></html:radio>
											</TD>
										</TR>

										<TR align="left">
											<TD align="left" valign="top" class="ColumnBackground">
											&nbsp;<font color="#FF0000" size="2">*</font><bean:message key="mcgfServiceFee" />
											</TD>
											<TD align="left" class="TableData">
											<html:radio name="adminForm" value="NO" property="mcgfServiceFee" ><bean:message key="no" /></html:radio>&nbsp;&nbsp;<html:radio name="adminForm" value="YES" property="mcgfServiceFee"><bean:message key="yes"/></html:radio>
											</TD>
										</TR>

										<TR align="left">
											<TD align="left" valign="top" class="ColumnBackground">
											&nbsp;<font color="#FF0000" size="2">*</font><bean:message key="mcgfGuaranteeFeePercentage" />
											</TD>
											<TD align="left" class="TableData">
											<html:text property="mcgfGuaranteeFeePercentage" size="20" maxlength="5" name="adminForm"  onkeypress="return decimalOnly(this, event,2)" onkeyup="isValidDecimal(this)"/><bean:message key="percentage" />
											</TD>
	                                                                        </TR>
										<TR align="left">
											<TD align="left" valign="top" class="ColumnBackground">
											&nbsp;<font color="#FF0000" size="2">*</font><bean:message key="cgtsiLiability" />
											</TD>
											<TD align="left" class="TableData">
											<html:text property="cgtsiLiability" maxlength="2" size="20" name="adminForm"  onkeypress="return numbersOnly(this, event)" onkeyup="isValidNumber(this)"/><bean:message key="percentage" />
											</TD>
										</TR>										
										
										<TR align="left">
											<TD align="left" valign="top" class="ColumnBackground">
											&nbsp;<font color="#FF0000" size="2">*</font><bean:message key="highValClearanceAmnt" />
											</TD>
											<TD align="left" class="TableData">
											<html:text property="highValClearanceAmnt" maxlength="16" name="adminForm"  onkeypress="return decimalOnly(this, event,13)" onkeyup="isValidDecimal(this)"/>
											</TD>
										</TR>
										

										<TR align="left">
											<TD align="left" valign="top" class="ColumnBackground">
											&nbsp;<font color="#FF0000" size="2">*</font><bean:message key="tenureExpiryPeriod" />
											</TD>
											<TD align="left" class="TableData">
											<html:text property="periodTenureExpiryLodgementClaims" maxlength="3" name="adminForm"  onkeypress="return numbersOnly(this, event)" onkeyup="isValidNumber(this)"/><bean:message key="inMonths" />
											</TD>
										</TR>
	
										<TR>
										<TD colspan="4">
										<TABLE width="700" border="0" cellpadding="0" cellspacing="1">										


										<TR> 
											<TD width="35%" class="ColumnBackground" rowspan="2">
												<DIV align="left"> &nbsp;
													* &nbsp;<bean:message key="appFilingTimeLimit"/>
												</DIV>
											</TD>
											<TD width="20%" class="TableData">
												<DIV align="left">
													&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<bean:message key="rule"/>
										</DIV>
											</TD>
											<TD class="TableData">
												<DIV align="left">
													&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<html:radio property="rule" name="adminForm" value="Days" onclick="javascript:enableAppFilingTimeLimitInParameterPage()">
														<br>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<bean:message key="days"/>
													</html:radio>
												</DIV>
											<TD class="TableData">
												<DIV align="left">
													<html:radio property="rule" name="adminForm" value="Periodicity"  onclick="javascript:enableAppFilingTimeLimitInParameterPage()">
														<bean:message key="periodicity"/>
													</html:radio>
													<br>&nbsp;
													<bean:message key="periodicityHint"/>
										</DIV>
											</TD>
										</TR>

										<TR>
											<TD class="TableData"></TD>
											<TD class="TableData">
												<DIV align="left">
													&nbsp;&nbsp;&nbsp;&nbsp;<bean:message key="noOfDays"/>&nbsp;&nbsp;<br>
													<html:text name="adminForm" property="noOfDays" size="12" maxlength="3" onkeypress="return numbersOnly(this, event)" onkeyup="isValidNumber(this)"/>
												</DIV>
											</TD>
											<TD class="TableData">
												<DIV align="left">
													<bean:message key="select" name="adminForm"/>&nbsp;&nbsp;&nbsp;
													<html:select property="periodicity" name="adminForm">
														<html:option value="">Select </html:option>
														<html:option value="-1">Monthly </html:option>
														<html:option value="-2">Bi-Monthly </html:option>
														<html:option value="-3">Quarterly </html:option>
														<html:option value="-4">Half Yearly </html:option>
														<html:option value="-6">Yearly </html:option>
													</html:select>
													<br><br>&nbsp;
													<bean:message key="periodicityExample"/>
												</DIV>
											</TD>
										</TR>                                                                                 


										</TABLE>
										</TD>
										</TR>


										<TR align="left">
											<TD align="left" valign="top" class="ColumnBackground">
											&nbsp;<font color="#FF0000" size="2">*</font><bean:message key="minSanctionedAmount" />
											</TD>
											<TD align="left" class="TableData">
											<html:text property="minimumSanctionedAmount" maxlength="16" size="20" name="adminForm"  onkeypress="return decimalOnly(this, event,13)" onkeyup="isValidDecimal(this)"/>
											</TD>
										</TR>
										
										<TR align="left">
											<TD align="left" valign="top" class="ColumnBackground">
											&nbsp;<font color="#FF0000" size="2">*</font><bean:message key="maxApprovalAmt" />
											</TD>
											<TD align="left" class="TableData">
											<html:text property="maxApprovedAmt" maxlength="16" size="20" alt="maxApproval Amt" name="adminForm"  onkeypress="return decimalOnly(this, event,13)" onkeyup="isValidDecimal(this)"/><bean:message key="inRs" />
											</TD>
										</TR>
										
										<TR align="left">
											<TD align="left" valign="top" class="ColumnBackground">
											&nbsp;<font color="#FF0000" size="2">*</font><bean:message key="serviceFee" />
											</TD>
											<TD align="left" class="TableData">
											<html:text property="serviceFeeRate" maxlength="5" size="20" alt="Service Fee" name="adminForm"  onkeypress="return decimalOnly(this, event,2)" onkeyup="isValidDecimal(this)"/>&nbsp;<bean:message key="percentage" />
											</TD>
										</TR>
										<!--
										<TR align="left">
											<TD align="left" valign="top" class="ColumnBackground">
											&nbsp;<font color="#FF0000" size="2">*</font><bean:message key="guaranteeFee" />
											</TD>
											<TD align="left" class="TableData">
											<html:text property="guaranteeFee" maxlength="5" size="20" alt="Service Fee" name="adminForm"  onkeypress="return decimalOnly(this, event,2)" onkeyup="isValidDecimal(this)"/>&nbsp;<bean:message key="percentage" />
											</TD>
										</TR>
										-->
										<TR align="left">
											<TD align="left" valign="top" class="ColumnBackground">
											&nbsp;<font color="#FF0000" size="2">*</font><bean:message key="thirdPartyGuarantee" />
											</TD>
											<TD align="left" class="TableData">
											<html:radio name="adminForm" value="N" property="thirdPartyGuarantee" ><bean:message key="no" /></html:radio>&nbsp;&nbsp;<html:radio name="adminForm" value="Y" property="thirdPartyGuarantee"><bean:message key="yes"/></html:radio>
											</TD>
										</TR>
										<TR align="left">
											<TD align="left" valign="top" class="ColumnBackground">
											&nbsp;<font color="#FF0000" size="2">*</font><bean:message key="collateralTaken" />
											</TD>
											<TD align="left" class="TableData">
											<html:radio name="adminForm" value="N" property="collateralTaken" ><bean:message key="no" /></html:radio>&nbsp;&nbsp;<html:radio name="adminForm" value="Y" property="collateralTaken"><bean:message key="yes"/></html:radio>
											</TD>
										</TR>										
										
										<TR align="left">
											<TD align="left" valign="top" class="ColumnBackground">
											&nbsp;<font color="#FF0000" size="2">*</font><bean:message key="isDefaultRateApplicable" />
											</TD>
											<TD align="left" class="TableData">
											<html:radio name="adminForm" value="N" property="isDefaultRateApplicable" onclick="disableDefRate(this)"><bean:message key="no" /></html:radio>&nbsp;&nbsp;
											<html:radio name="adminForm" value="Y" property="isDefaultRateApplicable" onclick="enableDefRate(this)"><bean:message key="yes"/></html:radio>
											</TD>
										</TR>
										<TR align="left">
											<TD align="left" valign="top" class="ColumnBackground">
											&nbsp;<font color="#FF0000" size="2">*</font><bean:message key="defRate" />
											</TD>
											<TD align="left" class="TableData">
											<html:text property="defaultRate" size="20" maxlength="5" name="adminForm"  onkeypress="return decimalOnly(this, event,2)" onkeyup="isValidDecimal(this)"/>&nbsp;<bean:message key="percentage" />
											</TD>
										</TR>
										<TR align="left">
											<TD align="left" valign="top" class="ColumnBackground">
											&nbsp;<font color="#FF0000" size="2">*</font><bean:message key="moderationFactorAdmin" />
											</TD>
											<TD align="left" class="TableData">
											<html:text property="moderationFactor" maxlength="5" size="20" name="adminForm"  onkeypress="return decimalOnly(this, event,2)" onkeyup="isValidDecimal(this)"/>
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
								<TD align="center" valign="baseline" colspan="2">
									<DIV align="center">

									<A href="javascript:submitForm('addParameter.do?method=addParameter')"><IMG src="images/Save.gif" alt="Save" width="49" height="37" border="0"></A>
										<A href="javascript:document.adminForm.reset()">
											<IMG src="images/Reset.gif" alt="Cancel" width="49" height="37" border="0"></A>
											
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