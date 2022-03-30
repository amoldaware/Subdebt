<%@ page import="com.cgtsi.util.*"%>

<!--start of term-->		<table width="100%" border="0" cellspacing="1" cellpadding="0">
								<TR>
									<TD colspan="10">
										<TABLE width="100%" border="0" cellpadding="0" cellspacing="0">
											<TR>
												<TD width="31%" class="Heading"><bean:message key="facilityDetails" /></TD>
												<TD><IMG src="images/TriangleSubhead.gif" width="19" height="19"></TD>
											</TR>
											<TR>
												<TD colspan="9" class="Heading"><IMG src="images/Clear.gif" width="5" height="5"></TD>
											</TR>
										</TABLE>
									</TD>
								</TR>
								<%
								String appTCFlag=session.getAttribute(SessionConstants.APPLICATION_TYPE_FLAG).toString();
              	String appCommonFlag=session.getAttribute(SessionConstants.APPLICATION_TYPE_FLAG).toString();


								if (appTCFlag.equals("0") || appTCFlag.equals("1") || appTCFlag.equals("2"))
								{

								%>
								<tr align="left">
									<td class="ColumnBackground">
										<bean:message key="facilityRehabilitation"/>
									</td>
									<td class="TableData" colspan="9">
									
											<html:radio name="appForm" value="Y" property="rehabilitation" ></html:radio>
											
											<bean:message key="yes"/>

											
											<html:radio name="appForm" value="N" property="rehabilitation" ></html:radio>
											
											<bean:message key="no"/>
									</td>
								</tr>
								<%}%>
								<tr>
									<td class="ColumnBackground">&nbsp;<bean:message key="scheme"/>
									</td>
									<td class="TableData" colspan="10">&nbsp;
									<%
									if (session.getAttribute(SessionConstants.MCGF_FLAG).equals("M"))
									{%>
									<bean:write property="scheme" name="appForm"/>
									<%}
									else
									{%>
									<bean:write property="scheme" name="appForm"/>
									<%}%>
									</td>               
								</tr>
								<tr>
									<td class="ColumnBackground" id="loanType">&nbsp;<bean:message key="loanType" />
									</td>
									<td class="TableData" colspan="10">&nbsp;<bean:write property="loanType" name="appForm"/>
									<html:hidden property="loanType" name="appForm"/>
									</td> 
								</tr>
								<tr>
									<td class="ColumnBackground">&nbsp;<bean:message key="compositeLoan"/>
									</td>
									<td class="TableData" colspan="10">&nbsp;<bean:write property="compositeLoan" name="appForm"/>
									</td> 
								</tr>
								
								<tr align="left"> 
									<td colspan="10" class="SubHeading" height="28" width="843"><br> &nbsp;
										<bean:message key="termCreditDetails"/>
									</td>
								</tr>
								
								<tr align="left"> 
									<td class="ColumnBackground" height="28">&nbsp;
										<bean:message key="amountSanctioned"/>
									</td>
									<td width="25%" class="TableData" height="28" id="amountSanctioned">
										<%				
											if((appTCFlag.equals("0"))||(appTCFlag.equals("3"))||(appTCFlag.equals("5"))||(appTCFlag.equals("6") || (appCommonFlag.equals("11"))||(appCommonFlag.equals("13")))
											)

											{
											%>
											<bean:write name="appForm" property="amountSanctioned"/>
											<%}
											else
											{ %>
											<bean:message key="inRs"/>
											<%}%>
												
									</td>
									<td width="15%" class="ColumnBackground" height="28">&nbsp;
										<font color="#FF0000" size="2">*</font>&nbsp;<bean:message key="amountSanctionedDate" />
									</td>
									<td width="20%" class="TableData" height="28" colspan="8">
									<%				
										if((appCommonFlag.equals("11"))||(appCommonFlag.equals("13")))
										
										
										{
										%>
										<bean:write name="appForm" property="amountSanctionedDate"/>
										<%}
										else
										{ %>
									
											<html:text property="amountSanctionedDate" size="20" alt="amountSanctionedDate" name="appForm" maxlength="10"/>
											<IMG src="images/CalendarIcon.gif" width="20" onClick="showCalendar('appForm.amountSanctionedDate')" align="center">
										<%}%>
											
									</td>
								 </tr>
								 <tr align="left">
									<td width="25%" class="ColumnBackground">&nbsp;
										<font color="#FF0000" size="2">*</font>&nbsp;<bean:message key="creditGuaranteed" />						
									</td>
									<td colspan="10" class="TableData">
									<%				
										if((appCommonFlag.equals("11"))||(appCommonFlag.equals("13")))
										

										{
										%>
										<bean:write name="appForm" property="creditGuaranteed"/>
										<%}
										else
										{ %>
									
											<html:text property="creditGuaranteed" size="20" alt="creditGuaranteed" name="appForm" maxlength="16" onkeypress="return decimalOnly(this, event,13)" onkeyup="isValidDecimal(this)"/>&nbsp;<bean:message key="inRs" />
										<%}%>
										
									</td>
								</tr>
								<tr> 
									<td class="ColumnBackground" height="28" width="25%">&nbsp;
										<bean:message key="amtDisbursed" />		
									</td>
									<td class="TableData" height="28" width="25%">
									<%
									
										if(appCommonFlag.equals("11")||(appCommonFlag.equals("13")) || appTCFlag.equals("3") || appTCFlag.equals("6"))
										{
										
										%>
										<bean:write name="appForm" property="amtDisbursed"/>
										<%}
										else
										{ %>
										<html:text property="amtDisbursed" size="20" alt="amtDisbursed" name="appForm" maxlength="16" onkeypress="return decimalOnly(this, event,13)" onkeyup="isValidDecimal(this)"/>
										<%}%>
										<bean:message key="inRs" />	
									</td>
									<td class="ColumnBackground" height="28" colspan="9">
										<table border="0" cellpadding="0" cellspacing="0"  width="100%">
											<tr align="left">
												<td class="ColumnBackground" height="28" width="269">&nbsp;
													<bean:message key="firstDisbursementDate"/>	
												</td>
												<td class="TableData" height="28" width="160" colspan="5">
												<%
												if(appCommonFlag.equals("11")||(appCommonFlag.equals("13")) || appTCFlag.equals("3") || appTCFlag.equals("6"))
												{
												%>
												<bean:write name="appForm" property="firstDisbursementDate"/>
												<%}
												else
												{ %>
													<html:text property="firstDisbursementDate" size="10" alt="firstDisbursementDate" name="appForm" maxlength="10"/>
													<IMG src="images/CalendarIcon.gif" width="20" onClick="showCalendar('appForm.firstDisbursementDate')" align="center">
												<%}%>
												</td>
											</tr>
											<tr align="left">
												<td class="ColumnBackground" height="28" width="269" >&nbsp;
												<%
												if(appCommonFlag.equals("11")||(appCommonFlag.equals("13")) || appTCFlag.equals("3") || appTCFlag.equals("6"))
												{
												%>
													<bean:message key="lastDisbursementDate"/>	
													<%
												}
												else
												{%>
												<bean:message key="finalDisbursementDate"/>
												<%}%>
												</td>
												<td class="TableData" height="28" width="160">
												<%
												if(appCommonFlag.equals("11")||(appCommonFlag.equals("13")) || appTCFlag.equals("3") || appTCFlag.equals("6"))
												{
												%>
												<bean:write name="appForm" property="finalDisbursementDate"/>
												<%}
												else
												{ %>
													<html:text property="finalDisbursementDate" size="10" alt="finalDisbursementDate" name="appForm" maxlength="10"/>
													<IMG src="images/CalendarIcon.gif" width="20" onClick="showCalendar('appForm.finalDisbursementDate')" align="center">
												<%}%>
												</td>
											</tr>
										</table>
									</td>
								</tr>
								<tr align="left"> 
									<td class="ColumnBackground" height="28" >&nbsp;
										<font color="#FF0000" size="2">*</font>&nbsp;<bean:message key="tenure" />&nbsp;
									</td>
									<td class="TableData" height="28" colspan="10">	
									<%				
										if((appCommonFlag.equals("11"))||(appCommonFlag.equals("13")))
										

										{
										%>
										<bean:write name="appForm" property="tenure"/>
										<%}
										else
										{ %>
									
										<html:text property="tenure" size="20" alt="tenure" name="appForm" maxlength="3" onkeypress="return numbersOnly(this, event)" onkeyup="isValidNumber(this)"/><bean:message key="inMonths" />
									<%}%>
									
									</td>
								</tr>
								<tr align="left"> 
									<td class="ColumnBackground" height="28">&nbsp;
										<bean:message key="interestType" />
									</td>
									<td class="TableData" >
									<%				
										if((appCommonFlag.equals("11"))||(appCommonFlag.equals("13")))
										

										{
										%>
										<html:radio name="appForm" value="T" property="interestType" disabled="true">
										</html:radio>
									
										<bean:message key="fixedInterest"/>&nbsp;
									
										<html:radio name="appForm" value="F" property="interestType" disabled="true"></html:radio>	
									
										<bean:message key="floatingInterest"/>	

<!--										<bean:write name="appForm" property="interestType"/>-->
										<%}
										else
										{ %>

										<html:radio name="appForm" value="T" property="interestType" >
										</html:radio>
									
										<bean:message key="fixedInterest"/>&nbsp;
									
										<html:radio name="appForm" value="F" property="interestType" ></html:radio>	
									
										<bean:message key="floatingInterest"/>	
									<%}%>
									
										&nbsp;<!--<html:text property="interestRate" size="5" alt="interestRate" name="appForm" maxlength="4" onkeypress="return decimalOnly(this, event)" onkeyup="isValidDecimal(this)"/>
										<bean:message key="inPer" />-->
									</td>
									<!--<td class="ColumnBackground" height="28" >&nbsp;
										<bean:message key="benchMarkPLR" />
									</td>
									<td class="TableData" height="28" width="160">&nbsp;
										
										<html:text property="benchMarkPLR" size="5" alt="benchMarkPLR" name="appForm" maxlength="3" onkeypress="return numbersOnly(this, event)" onkeyup="isValidNumber(this)"/>&nbsp;
										
										<bean:message key="inPer" />
									</td>
									<td class="ColumnBackground" height="28" >&nbsp;
										<bean:message key="benchMarkPLR" />
									</td>-->
									<td class="ColumnBackground" height="28" >&nbsp;
									<font color="#FF0000" size="2">*</font>&nbsp;<bean:message key="typeOfPLR" />666
									</td>
									<td class="TableData" height="28" colspan="8">&nbsp;
									<%				
										if((appCommonFlag.equals("11"))||(appCommonFlag.equals("13")))
										

										{
										%>
										<bean:write name="appForm" property="typeOfPLR"/>
										<%}
										else
										{ %>
										
										<html:text property="typeOfPLR" size="20" alt="typeOfPLR" name="appForm" maxlength="50" />&nbsp;						
									<%}%>
									</td>
								</tr>
								<tr align="left">
									
									<td width="25%" class="ColumnBackground"> <div align="left">&nbsp;
										<font color="#FF0000" size="2">*</font>&nbsp;<bean:message key="plr" /></div>
									</td>
									<td class="TableData" >
										<div align="left"> 
										<%				
										if((appCommonFlag.equals("11"))||(appCommonFlag.equals("13")))
										

										{
										%>
										<bean:write name="appForm" property="plr"/>
										<%}
										else
										{ %>
											<html:text property="plr" size="5" alt="plr" name="appForm" maxlength="6" onkeypress="return decimalOnly(this, event,3)" onkeyup="isValidDecimal(this)"/>&nbsp;
										<%}%>
											<bean:message key="inPa" />
										
										</div>
									</td>
									<td width="25%" class="ColumnBackground"> <div align="left">&nbsp;
										<font color="#FF0000" size="2">*</font>&nbsp;<bean:message key="interestRate" /></div>
									</td>
									<td class="TableData" colspan="8">
										<div align="left"> 
										<%				
										if((appCommonFlag.equals("11"))||(appCommonFlag.equals("13")))
										

										{
										%>
										<bean:write name="appForm" property="interestRate"/>
										<%}
										else
										{ %>
										
											<html:text property="interestRate" size="5" alt="interestRate" name="appForm" maxlength="5" onkeypress="return decimalOnly(this, event,2)" onkeyup="isValidDecimal(this)"/>&nbsp;
										<%}%>
											<bean:message key="inPa" />
										
										</div>
									</td>

								</tr>
								<tr align="left"> 
									<td class="ColumnBackground" colspan="12" height="24" width="843">
										<bean:message key="repaymentSchedule" />
									</td>
								</tr>
								<tr align="left"> 
									<td class="ColumnBackground" colspan="12" height="24" width="843">
										<table border="0" cellpadding="0" cellspacing="0"  width="100%" height="71">
											<tr>
												<td width="7%" height="18">
												</td>
												<td width="29%" height="18"><span style="font-size: 9pt; font-weight: 700">
													<bean:message key="repaymentMoratorium" /></span>
												</td>
												<td width="64%" height="18" colspan="3">
												
													<span style="font-size: 9pt; font-weight: 700">
													<%				
													if((appCommonFlag.equals("11"))||(appCommonFlag.equals("13")) || (appCommonFlag.equals("3")) || (appCommonFlag.equals("6")))										

													{
													%>
													<bean:write name="appForm" property="repaymentMoratorium"/>
													<%}
													else
													{ %>
													<html:text property="repaymentMoratorium" size="5" alt="repaymentMoratorium" name="appForm" maxlength="3" onkeypress="return numbersOnly(this, event)" onkeyup="isValidNumber(this)"/>
													<%}%></span>
													<span style="font-size: 9pt"> 
												
													<bean:message key="inMonths" />
													</span>
												</td>
											</tr>
											<tr>
												<td width="7%" height="17">
												</td>
												<td width="29%" height="17">
													<span style="font-size: 9pt; font-weight: 700">
														&nbsp;<bean:message key="firstInstallmentDueDate" />
													</span>
												</td>
												<td width="64%" height="17">
												
													<span style="font-size: 9pt; font-weight: 700">
													<%				
													if((appCommonFlag.equals("11"))||(appCommonFlag.equals("13")) || (appCommonFlag.equals("3")) || (appCommonFlag.equals("6")))																			
													{
													%>
													<bean:write name="appForm" property="firstInstallmentDueDate"/>
													<%}
													else
													{ %>
														<html:text property="firstInstallmentDueDate" size="20" alt="firstInstallmentDueDate" name="appForm" maxlength="10"/>
														<IMG src="images/CalendarIcon.gif" width="20" onClick="showCalendar('appForm.firstInstallmentDueDate')" align="center">
														<%}%>
														</span>
													
												</td>
											</tr>
											<tr>
												<td width="7%" height="18"></td>
												<td width="29%" height="18">
													<span style="font-size: 9pt; font-weight: 700">
														<bean:message key="periodicity" />
													</span>
												</td>
												<td width="64%" height="18">
												
													<span style="font-size: 9pt; font-weight: 700">
													<%				
													if((appCommonFlag.equals("11"))||(appCommonFlag.equals("13")) || (appCommonFlag.equals("3")) || (appCommonFlag.equals("6")))						
													{
													%>
													<html:select property="periodicity" name="appForm" disabled="true">
														<html:option value="">Select</html:option>
														<html:option value="1">Monthly</html:option>
														<html:option value="2">Quarterly</html:option>
														<html:option value="3">Half-Yearly</html:option>
													</html:select>

													<%}
													else
													{ %>
													<html:select property="periodicity" name="appForm">
														<html:option value="">Select</html:option>
														<html:option value="1">Monthly</html:option>
														<html:option value="2">Quarterly</html:option>
														<html:option value="3">Half-Yearly</html:option>
													</html:select>
													<%}%>
													</span>
												
												 </td>
											</tr>
											<tr>
												<td width="7%" height="18">
												</td>
												<td width="29%" height="18">
													<span style="font-size: 9pt; font-weight: 700"><font color="#FF0000" size="2">*</font>&nbsp;
													<bean:message key="noOfInstallments" />
													</span>
												</td>
												<td width="64%" height="18">
											
													<span style="font-size: 9pt; font-weight: 700">
													<%				
													if((appCommonFlag.equals("11"))||(appCommonFlag.equals("13")) || (appCommonFlag.equals("3")) || (appCommonFlag.equals("6")))						
													{
													%>
													<bean:write name="appForm" property="noOfInstallments"/>
													<%}
													else
													{ %>
													<html:text property="noOfInstallments" size="5" alt="noOfInstallments" name="appForm" maxlength="3" onkeypress="return numbersOnly(this, event)" onkeyup="isValidNumber(this)"/>
													<%}%>
													</span>
												
												</td>
											</tr>
										 </table>
									</td>
								</tr>
								<tr> 
									<td class="ColumnBackground" height="28" width="252">&nbsp;
										<bean:message key="pplOS" />
									</td>
									<td colspan="10" class="TableData" height="28" width="590">
									<%				
										if((appCommonFlag.equals("11"))||(appCommonFlag.equals("13")) || (appCommonFlag.equals("3")) || (appCommonFlag.equals("6")))			

										{
										%>
										<bean:write name="appForm" property="pplOS"/>
										<%}
										else
										{ %>
									
										<html:text property="pplOS" size="5" alt="pplOS" name="appForm" maxlength="16" onkeypress="return decimalOnly(this, event,13)" onkeyup="isValidDecimal(this)"/>
									<%}%>
										
										<bean:message key="inRs" />&nbsp;
										<bean:message key="pplOsAsOnDate" />

										<%				
										if((appCommonFlag.equals("11"))||(appCommonFlag.equals("13")) || (appCommonFlag.equals("3")) || (appCommonFlag.equals("6")))

										{
										%>
										<bean:write name="appForm" property="pplOsAsOnDate"/>
										<%}
										else
										{ %>
										
										<html:text property="pplOsAsOnDate" size="20" alt="pplOsAsOnDate" name="appForm" maxlength="10"/>
										<IMG src="images/CalendarIcon.gif" width="20" onClick="showCalendar('appForm.pplOsAsOnDate')" align="center">
										<%}%>
										
									</td>
								</tr>		



									           
							</table>			<!--end of term -->
						