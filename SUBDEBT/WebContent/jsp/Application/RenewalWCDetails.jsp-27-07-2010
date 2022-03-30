<%@ page import="com.cgtsi.util.SessionConstants"%>
<!--start of enhance WC-->	<table width="100%" border="0" cellspacing="1" cellpadding="0">
								<TR>
									<TD colspan="7">
										<TABLE width="100%" border="0" cellpadding="0" cellspacing="0">
											<TR>
												<TD width="31%" class="Heading"><bean:message key="facilityDetails" /></TD>
												<TD><IMG src="images/TriangleSubhead.gif" width="19" height="19"></TD>
											</TR>
											<TR>
												<TD colspan="6" class="Heading"><IMG src="images/Clear.gif" width="5" height="5"></TD>
											</TR>
										</TABLE>
									</TD>
								</TR>
								<tr align="left">
									<td class="ColumnBackground">
										<bean:message key="facilityRehabilitation" />
									</td>
									<td class="TableData" colspan="10">
									<%
									String appWCRFlag=session.getAttribute(SessionConstants.APPLICATION_TYPE_FLAG).toString();
									if(appWCRFlag.equals("1"))
									{
									%>
										<bean:write name="appForm" property="rehabilitation"/>
									<%
									}
									else
									{%>
										<html:radio name="appForm" value="Y" property="rehabilitation" ><bean:message key="yes" /></html:radio>
										<html:radio name="appForm" value="N" property="rehabilitation" ><bean:message key="no" /></html:radio>
									<%}%>
									</td>
								</tr>
								<tr align="left">
									<td class="ColumnBackground">&nbsp;<bean:message key="compositeLoan" />
									</td>
									<td colspan="10" class="TableData">&nbsp;No
									</td>
								</tr>
								<tr align="left">
									<td width="30%" class="ColumnBackground"><div align="left">&nbsp;
										<bean:message key="loanType" /></div>
									</td>
									<td colspan="10" class="TableData"><div align="left">&nbsp;Working Capital Renewal</div>
									</td>
								</tr>
								<tr>
									<td class="ColumnBackground">&nbsp;<bean:message key="scheme" />
									</td>
									<td class="TableData" colspan="10">&nbsp;
									<%
									if (session.getAttribute(SessionConstants.MCGF_FLAG).equals("M"))
									{%>
									MCGF
									<%}
									else
									{%>
									CGFSI<%}%>

									</td>               
								</tr>
								 <TR>
									<TD class="SubHeading" width="843" colspan="6"><bean:message key="workingCapitalRenewal" />
									</TD>						
								</TR>	
								
								<tr align="left"> 
									<td class="ColumnBackground" height="28">&nbsp;
										<bean:message key="interestType" />
									</td>
									<td class="TableData" colspan="10">													
									
										<html:radio name="appForm" value="T" property="wcInterestType" >
										</html:radio>
									
										<bean:message key="fixedInterest" />&nbsp;
									
										<html:radio name="appForm" value="F" property="wcInterestType" ></html:radio>	
									
										<bean:message key="floatingInterest" />	
									
									</td>
								</tr>

										
								<TR align="left">
									<td class="ColumnBackground" >
										<bean:message key="existingFundBased"/>
									</td>
									<td class="TableData">
									<%									
									if(appWCRFlag.equals("2"))
									{
									%>
										<bean:write name="appForm" property="existingFundBasedTotal"/>
									<%
									}
									else
									{%>
										<html:text property="wcFundBasedSanctioned" size="20" alt="fundBasedLimitSanctioned" name="appForm" disabled="true" onkeypress="return numbersOnly(this, event)" onkeyup="isValidNumber(this)"/>
									<%}%>
									</td>
									<td class="ColumnBackground">
										<bean:message key="limitFundBasedInterest"/>
									</td>
									<td class="TableData" colspan="10">
									<%									
									if(appWCRFlag.equals("2"))
									{
									%>
										<bean:write name="appForm" property="limitFundBasedInterest"/>
										<bean:message key="inPa" />
									<%
									}
									else
									{%>
										<html:text property="limitFundBasedInterest" size="20" alt="limitFundBasedInterest" name="appForm" disabled="true" onkeypress="return decimalOnly(this, event)" onkeyup="isValidDecimal(this)"/>
										<%}%>
									</td>
								</TR>
								<TR align="left">
									<td class="ColumnBackground" >
										<bean:message key="existingNonFundBased"/>
									</td>
									<td class="TableData">
									<%									
									if(appWCRFlag.equals("2"))
									{
									%>
										<bean:write name="appForm" property="wcNonFundBasedSanctioned"/>
									<%
									}
									else
									{%>
										<html:text property="wcNonFundBasedSanctioned" size="20" alt="nonFundBasedLimitSantioned" name="appForm" disabled="true" onkeypress="return numbersOnly(this, event)" onkeyup="isValidNumber(this)"/>
									<%}%>
									</td>
									<td class="ColumnBackground">
										<bean:message key="limitNonFundBasedCommission"/>
									</td>
									<td class="TableData" colspan="3">
									<%									
									if(appWCRFlag.equals("2"))
									{
									%>
										<bean:write name="appForm" property="limitNonFundBasedCommission"/>
									<%
									}
									else
									{%>
										<html:text property="limitNonFundBasedCommission" size="20" alt="limitNonFundBasedCommission" name="appForm" disabled="true" onkeypress="return decimalOnly(this, event)" onkeyup="isValidDecimal(this)"/>
										<%}%>
									</td>
								</TR>
								<TR align="left">
									<td class="ColumnBackground" >
										<bean:message key="renewalFundBased"/>
									</td>
									<td class="TableData" id="renewalFundBased">

									</td>
									<td class="ColumnBackground">
										&nbsp;<bean:message key="renewalFBInterest"/>
									</td>
									<td class="TableData" colspan="10">
										<html:text property="renewalFBInterest" size="5" alt="renewalFBInterest" name="appForm" onkeypress="return decimalOnly(this, event,2)" onkeyup="isValidDecimal(this)" maxlength="5"/><bean:message key="inPa" />
									</td>
								</TR>
								<TR align="left">
									<td class="ColumnBackground" >
										<bean:message key="renewalNonFundBased"/>
									</td>
									<td class="TableData" id="renewalNonFundBased">

									</td>
									<td class="ColumnBackground">
										<bean:message key="renewalNFBComission"/>
									</td>
									<td class="TableData" colspan="3">
										<html:text property="renewalNFBComission" size="20" onkeypress="return decimalOnly(this, event)" onkeyup="isValidDecimal(this)"/>
									</td>
								</TR>
								<!--<TR align="left">
									<td class="ColumnBackground" >
										<bean:message key="renewalTotal"/>
									</td>
									<td class="TableData" colspan="10" id="renewalTotal">
										
									</td>
								</TR>-->
								<tr>
									<td class="ColumnBackground" height="28" >&nbsp;
										<font color="#FF0000" size="2">*</font>&nbsp;<bean:message key="typeOfPLR" />
									</td>
									<td class="TableData" height="28" >&nbsp;
										<html:text property="wcTypeOfPLR" size="30" alt="wcTypeOfPLR" name="appForm" maxlength="50" />&nbsp;						
	
									</td>
									<td width="25%" class="ColumnBackground"> <div align="left">&nbsp;
										<font color="#FF0000" size="2">*</font>&nbsp;<bean:message key="plr" /></div>
									</td>
									<td class="TableData" colspan="5">
										<div align="left"> 
											<html:text property="wcPlr" size="5" alt="wcPlr" name="appForm" maxlength="6" onkeypress="return decimalOnly(this, event,3)" onkeyup="isValidDecimal(this)"/>&nbsp;

											<bean:message key="inPa" />
										
										</div>
									</td>

								</tr>

								<TR align="left">
									<td class="ColumnBackground" >
										<font color="#FF0000" size="2">*</font>&nbsp;<bean:message key="renewalDate"/>
									</td>
									<td class="TableData" colspan="10">
										<html:text property="renewalDate" size="20" alt="renewalDate" name="appForm" maxlength="10"/>
										<IMG src="images/CalendarIcon.gif" width="20" onClick="showCalendar('appForm.renewalDate')" align="center">
									</td>
								</TR>
								

							</table>			<!--end of WC-->
						