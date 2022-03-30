<%@ page import="com.cgtsi.util.SessionConstants"%>
<!--start of enhance WC-->
	<table width="100%" border="0" cellspacing="1" cellpadding="0">
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
									<td class="TableData" colspan="6">
									<%
									String appWCEFlag=(String)session.getAttribute(SessionConstants.APPLICATION_TYPE_FLAG);
									if(appWCEFlag.equals("1"))
									{
									%>
										<bean:write name="appForm" property="rehabilitation"/>
									<%
									}
									else
									{%>
										<html:radio name="appForm" value="Yes" property="rehabilitation" ><bean:message key="yes" /></html:radio>
										<html:radio name="appForm" value="No" property="rehabilitation" ><bean:message key="no" /></html:radio>
									<%}%>
									</td>
								</tr>
								<tr align="left">
									<td class="ColumnBackground">&nbsp;<bean:message key="compositeLoan" />
									</td>
									<td colspan="6" class="TableData">&nbsp;No
									</td>
								</tr>
								<tr align="left">
									<td width="30%" class="ColumnBackground"><div align="left">&nbsp;
										<bean:message key="loanType" /></div>
									</td>
									<td colspan="6" class="TableData"><div align="left">&nbsp;Working Capital Enhancement</div>
									</td>
								</tr>
								<tr>
									<td class="ColumnBackground">&nbsp;<bean:message key="scheme" />
									</td>
									<td class="TableData" colspan="6">&nbsp;
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
									<TD class="SubHeading" width="843" colspan="6"><bean:message key="workingCapitalEnhancement" />
									</TD>						
								</TR>			
								<tr align="left"> 
									<td class="ColumnBackground" height="28">&nbsp;
										<bean:message key="interestType" />
									</td>
									<td class="TableData" colspan="6">
									
										<html:radio name="appForm" value="T" property="wcInterestType" >
										</html:radio>
									
										<bean:message key="fixedInterest" />&nbsp;
									
										<html:radio name="appForm" value="F" property="wcInterestType" ></html:radio>	
									
										<bean:message key="floatingInterest" />	
									
										&nbsp;
									</td>
								</tr>
										
								<TR align="left">
									<td class="ColumnBackground" >
										<bean:message key="existingFundBased"/>
									</td>
									<td class="TableData" id="wcFundBased">
									<%									
									if(appWCEFlag.equals("1"))
									{
									%>
										<bean:write name="appForm" property="existingFundBasedTotal"/>
									<%
									}
									else
									{%><html:hidden property="exposurelmtAmt" name="appForm"/>		
										<html:text property="wcFundBasedSanctioned" size="20" alt="fundBasedLimitSanctioned" name="appForm" disabled="true" onkeypress="return numbersOnly(this, event,13)" onkeyup="isValidNumber(this)"/>
										<%}%>
									</td>
									<div id="FBerrorsMessage" class="errorsMessage"/>
									<td class="ColumnBackground">
										<bean:message key="limitFundBasedInterest"/>
									</td>
									<td class="TableData" colspan="3">
									<%									
									if(appWCEFlag.equals("1"))
									{
									%>
										<bean:write name="appForm" property="limitFundBasedInterest"/>&nbsp&nbsp<bean:message key="inPa" />
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
									<td class="TableData" id="wcNonFundBased">
									<%									
									if(appWCEFlag.equals("1"))
									{
									%>
										<bean:write name="appForm" property="existingNonFundBasedTotal"/>
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
									if(appWCEFlag.equals("1"))
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
										<bean:message key="enhancedFundBased"/>
									</td>
									<td class="TableData" id="enhancedFundBased">
									<%
										if(appWCEFlag.equals("1"))

										{
										%>
										<bean:write name="appForm" property="enhancedFundBased"/>
										<%}%>
								</td>
				
									
									<td class="ColumnBackground">
										&nbsp;<bean:message key="enhancedFBInterest"/>
									</td>
									<td class="TableData" colspan="3">
										<html:text property="enhancedFBInterest" size="20" alt="enhancedFBInterest" name="appForm" onkeypress="return decimalOnly(this, event,2)" onkeyup="isValidDecimal(this)" maxlength="5"/>
									</td>
								</TR>
								<TR align="left">
									<td class="ColumnBackground" >
										<bean:message key="enhancedNonFundBased"/>
									</td>
									<td class="TableData" id="enhancedNonFundBased">
									<%
										if(appWCEFlag.equals("1"))

										{
										%>
										<bean:write name="appForm" property="enhancedNonFundBased"/>
										<%}%>

									</td>
									<td class="ColumnBackground">
										<bean:message key="enhancedNFBComission"/>
									</td>
									<td class="TableData" colspan="3">
										<html:text property="enhancedNFBComission" size="20" alt="enhancedNFBComission" name="appForm" onkeypress="return decimalOnly(this, event)" onkeyup="isValidDecimal(this)"/>
									</td>
								</TR>
<!--								<TR align="left">
									<td class="ColumnBackground" >
										<bean:message key="enhancedTotal"/>
									</td>
									<td class="TableData" colspan="7" id="enhancedTotal">
										
									</td>
								</TR>-->
								<TR align="left">
									<td class="ColumnBackground" >
										<font color="#FF0000" size="2">*</font>&nbsp;<bean:message key="enhancementDate"/>
									</td>
									<td class="TableData" colspan="7">
										<html:text property="enhancementDate" size="20" alt="enhancementDate" name="appForm" maxlength="10"/>
										<IMG src="images/CalendarIcon.gif" width="20" onClick="showCalendar('appForm.enhancementDate')" align="center">
									</td>
								</TR>
								

							</table>			<!--end of WC-->
						