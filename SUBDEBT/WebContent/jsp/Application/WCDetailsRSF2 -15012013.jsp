<%@ page import="com.cgtsi.util.SessionConstants"%>
	<!--start of WC-->				
							<table width="100%" border="0" cellspacing="1" cellpadding="0" colspan="5">
								 <TR>	<TD class="SubHeading" width="843"><bean:message key="workingCapital" /></TD></TR>	<tr align="left"> 
									<td class="ColumnBackground" height="28">&nbsp;<bean:message key="interestType" /></td><td class="TableData" colspan="2">			<%
									String appWCFlag=session.getAttribute(SessionConstants.APPLICATION_TYPE_FLAG).toString();if((appWCFlag.equals("13"))||(appWCFlag.equals("12")))		{
										%><html:radio name="appForm" value="T" property="wcInterestType" disabled="true">	</html:radio><bean:message key="fixedInterest" />&nbsp;
										<html:radio name="appForm" value="F" property="wcInterestType" disabled="true" ></html:radio><bean:message key="floatingInterest" />	
										<%}	else{ %><html:radio name="appForm" value="T" property="wcInterestType">	</html:radio><bean:message key="fixedInterest" />&nbsp;<html:radio name="appForm" value="F" property="wcInterestType" ></html:radio>	
									<bean:message key="floatingInterest" />	<%}%>&nbsp;<!--<html:text property="interestRate" size="5" alt="interestRate" name="appForm" maxlength="4" onkeypress="return decimalOnly(this, event)" onkeyup="isValidDecimal(this)"/>
										<bean:message key="inPer" />--></td><!--<td class="ColumnBackground" height="28" >&nbsp;<bean:message key="benchMarkPLR" /></td>
									<td class="TableData" height="28" width="160">&nbsp;<html:text property="benchMarkPLR" size="5" alt="benchMarkPLR" name="appForm" maxlength="3" onkeypress="return numbersOnly(this, event)" onkeyup="isValidNumber(this)"/>&nbsp;
										<bean:message key="inPer" /></td><td class="ColumnBackground" height="28" >&nbsp;<bean:message key="benchMarkPLR" />
									</td>--><td class="ColumnBackground" height="28" >&nbsp;<font color="#FF0000" size="2">*</font>&nbsp;<bean:message key="typeOfPLR" />
									</td><td class="TableData" height="28" colspan="8">&nbsp;<%if((appWCFlag.equals("13"))||(appWCFlag.equals("12")))		{
										%><bean:write name="appForm" property="wcTypeOfPLR"/><%}else{ %>	<html:text property="wcTypeOfPLR" size="30" alt="wcTypeOfPLR" name="appForm" maxlength="50" />&nbsp;						
									<%}%></td></tr><tr align="left"><td width="25%" class="ColumnBackground"> <div align="left">&nbsp;
										<font color="#FF0000" size="2">*</font>&nbsp;<bean:message key="plr" /></div></td><td class="TableData" colspan="11">
										<div align="left"> <%if((appWCFlag.equals("13"))||(appWCFlag.equals("12")))		{
										%><bean:write name="appForm" property="wcPlr"/><%}else{ %><html:text property="wcPlr" size="5" alt="wcPlr" name="appForm" maxlength="6" onkeypress="return decimalOnly(this, event,3)" onkeyup="isValidDecimal(this)"/>&nbsp;
										<%}%><bean:message key="inPa" /></div></td></tr><tr align="left"><td class="ColumnBackground" rowspan="1" width="25%"><bean:message key="limitSanctioned" />&nbsp;&nbsp;&nbsp;</td>
										<td class="TableData" width="15%">&nbsp;<bean:message key="fundBasedLimitSanctioned" />&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;</TD>
									<TD class="TableData" id="fundBasedLimitSanctioned" width="5%"><bean:write name="appForm" property="wcFundBasedSanctioned"/>		&nbsp;&nbsp;	</TD>
										<!--<TD class="TableData" ><bean:message key="limitFundBasedInterest"/>		&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
									<html:text property="limitFundBasedInterest" size="7" alt="limitFundBasedInterest" name="appForm" maxlength="5" onkeypress="return decimalOnly(this, event)" onkeyup="isValidDecimal(this)"/>	&nbsp;&nbsp;&nbsp;&nbsp;
								</TD>-->	<td  class="TableData"> <div align="left"><bean:message key="limitFundBasedInterest" /></div><%if((appWCFlag.equals("13"))||(appWCFlag.equals("12")))		{	%>
									<bean:write name="appForm" property="limitFundBasedInterest"/><%}	else{ %><html:text property="limitFundBasedInterest" size="5" alt="limitFundBasedInterest" name="appForm" maxlength="5" onkeypress="return decimalOnly(this, event,2)" onkeyup="isValidDecimal(this)"/>&nbsp;
										<%}%><bean:message key="inPa" /></td>
										
									<TD class="TableData" colspan="8">
										<bean:message key="limitFundbasedSanctionedDate" />	&nbsp;
										<%
										if((appWCFlag.equals("13"))||(appWCFlag.equals("12")))		
										{
										%>
										<bean:write name="appForm" property="limitFundBasedSanctionedDate"/>
										<%}
										else
										{ %>
										
										<html:text property="limitFundBasedSanctionedDate" size="7" alt="limitFundbasedSanctionedDate" name="appForm" maxlength="10"/>
										<IMG src="images/CalendarIcon.gif" width="20" onClick="showCalendar('appForm.limitFundBasedSanctionedDate')" align="center">
										<%}%>
										
									</td>
								</tr>
								<tr>
								<td class="TableData" width="15%">
								</td>
								<td class="TableData" width="15%">
										<bean:message key="nonFundBasedLimitSantioned" />	
									</TD>
								<TD class="TableData" id="nonFundBasedLimitSantioned" width="10%">
									<bean:write name="appForm" property="wcNonFundBasedSanctioned"/>&nbsp;
										
									</TD>
									<TD class="TableData">		
										
										<bean:message key="limitNonFundBasedCommission" />		&nbsp;&nbsp
										<%
										if((appWCFlag.equals("13"))||(appWCFlag.equals("12")))		
										{
										%>
										<bean:write name="appForm" property="limitNonFundBasedCommission"/>
										<%}
										else
										{ %>
										
											<html:text property="limitNonFundBasedCommission" size="5" alt="limitFundBasedInterest" name="appForm" maxlength="5" onkeypress="return decimalOnly(this, event,2)" onkeyup="isValidDecimal(this)"/>&nbsp;
										<%}%>
										
									</TD>
									<TD class="TableData" colspan="8">
										<bean:message key="limitNonFundBasedSanctionedDate" />	&nbsp;
										
										<%
										if((appWCFlag.equals("13"))||(appWCFlag.equals("12")))		
										{
										%>
										<bean:write name="appForm" property="limitNonFundBasedSanctionedDate"/>
										<%}
										else
										{ %>
										
										<html:text property="limitNonFundBasedSanctionedDate" size="7" alt="limitFundbasedSanctionedDate" name="appForm" maxlength="10"/>
										<IMG src="images/CalendarIcon.gif" width="20" onClick="showCalendar('appForm.limitNonFundBasedSanctionedDate')" align="center">
										<%}%>
										
									</td>
								</tr>
								<tr align="left">
									<td class="ColumnBackground" rowspan="1">
										<bean:message key="creditguarateed" />
										&nbsp;&nbsp;&nbsp;
									</td>
									<td class="TableData" colspan="11">
										<bean:message key="creditFundBased" />						&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
										<%
										if((appWCFlag.equals("13"))||(appWCFlag.equals("12")))		
										{
										%>
										<bean:write name="appForm" property="creditFundBased"/>
										<%}
										else
										{ %>
										
										<html:text property="creditFundBased" size="10" alt="creditFundBased" name="appForm" maxlength="16" onkeypress="return decimalOnly(this, event,13)" onkeyup="isValidDecimal(this)"/>&nbsp;
										<%}%>
										<bean:message key="inRs" />	
										
									</td>
								</tr>
								<tr align="left">
									<td class="TableData">
									</td>
									<td class="TableData" colspan="11">
										<bean:message key="creditNonFundBased" />
										&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
										<%
										if((appWCFlag.equals("13"))||(appWCFlag.equals("12")))		
										{
										%>
										<bean:write name="appForm" property="creditNonFundBased"/>
										<%}
										else
										{ %>
										
										<html:text property="creditNonFundBased" size="10" alt="creditFundBased" name="appForm" maxlength="16" onkeypress="return decimalOnly(this, event,13)" onkeyup="isValidDecimal(this)"/>&nbsp;
										<%}%>
										<bean:message key="inRs" />	
										
									</td>
								</tr>
								 <!-- added by sukumar@path for capturing WC details -->
								
								<tr align="left"><td class="ColumnBackground" width="25%" colspan="12" height="25">&nbsp;<bean:message key="osFundBased" /></td>
								</tr>
								<tr align="left"><td class="TableData" colspan="12"><div align="center">&nbsp;<bean:message key="osFundBasedPpl" />&nbsp;
										<%if((appWCFlag.equals("13"))||(appWCFlag.equals("12")) || (appWCFlag.equals("4")) || (appWCFlag.equals("6"))){%>
										<bean:write name="appForm" property="osFundBasedPpl"/><%}else{ %>
										<html:text property="osFundBasedPpl" size="5" alt="osFundBasedPpl" name="appForm" maxlength="16" onkeypress="return decimalOnly(this, event,13)" onkeyup="isValidDecimal(this)"/><%}%>
										<bean:message key="inRs" />	&nbsp;&nbsp;&nbsp;&nbsp;<!--<bean:message key="osFundBasedInterestAmt" />&nbsp;
										<html:text property="osFundBasedInterestAmt" size="5" alt="osFundBasedInterestAmt" name="appForm" maxlength="13" onkeypress="return numbersOnly(this, event)" onkeyup="isValidNumber(this)"/>
										<bean:message key="inRs" />-->&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<bean:message key="osFundBasedAsOnDate" />&nbsp;&nbsp;&nbsp;
										<%if((appWCFlag.equals("13"))||(appWCFlag.equals("12")) || (appWCFlag.equals("4")) || (appWCFlag.equals("6")))		
										{%><html:text property="osFundBasedAsOnDate" size="10" alt="osFundBasedAsOnDate" name="appForm" maxlength="10"/>
										<IMG src="images/CalendarIcon.gif" width="20" onClick="showCalendar('appForm.osFundBasedAsOnDate')" align="center">
										<%}else{ %><html:text property="osFundBasedAsOnDate" size="10" alt="osFundBasedAsOnDate" name="appForm" maxlength="10"/>
										<IMG src="images/CalendarIcon.gif" width="20" onClick="showCalendar('appForm.osFundBasedAsOnDate')" align="center">
										<%}%></div></td></tr><tr align="left"><td class="ColumnBackground" width="25%" colspan="12" height="25">&nbsp;
										<bean:message key="osNonFundBased" /></td></tr><tr align="left"><td class="TableData" colspan="12"><div align="center">
										<bean:message key="osNonFundBasedPpl" />&nbsp;<%if((appWCFlag.equals("13"))||(appWCFlag.equals("12")) || (appWCFlag.equals("4")) || (appWCFlag.equals("6")))		
										{%><bean:write name="appForm" property="osNonFundBasedPpl"/><%}else{ %>
										<html:text property="osNonFundBasedPpl" size="5" alt="osFundBasedPpl" name="appForm" maxlength="16" onkeypress="return decimalOnly(this, event,13)" onkeyup="isValidDecimal(this)"/>
										<%}%><bean:message key="inRs" />	&nbsp;&nbsp;&nbsp;&nbsp;<!--<bean:message key="osNonFundBasedCommissionAmt" />&nbsp;
										<html:text property="osNonFundBasedCommissionAmt" size="5" alt="osNonFundBasedCommissionAmt" name="appForm" maxlength="13" onkeypress="return numbersOnly(this, event)" onkeyup="isValidNumber(this)"/>-->
										<bean:message key="inRs" />&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<bean:message key="osNonFundBasedAsOnDate" />&nbsp;&nbsp;&nbsp;
										<%if((appWCFlag.equals("13"))||(appWCFlag.equals("12")) || (appWCFlag.equals("4")) || (appWCFlag.equals("6")))		
										{%><bean:write name="appForm" property="osNonFundBasedAsOnDate"/>
										<%}else{ %>	<html:text property="osNonFundBasedAsOnDate" size="10" alt="osFundBasedAsOnDate" name="appForm" maxlength="10"/>
										<IMG src="images/CalendarIcon.gif" width="20" onClick="showCalendar('appForm.osNonFundBasedAsOnDate')" align="center">
										<%}%></div></td></tr>
							</table>			<!--end of WC-->
						