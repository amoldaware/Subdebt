<%@page import="com.cgtsi.registration.MLIInfo"%>
<%@page import="java.util.Iterator"%>
<%@ page import="com.cgtsi.util.SessionConstants"%>
	<!--start of WC-->		
	 <style>
 .w150{width:150px;}
 </style>
<script type="text/javascript">
	function validateInterestRateWC(rateType){
		var plr = document.forms[0].wcPlr.value;
		var interest = document.forms[0].limitFundBasedInterest.value;
		if(rateType === 'interest'){
			if(!(parseFloat(plr) > 0)){
				alert('Please enter PLR rate first.');
				document.forms[0].limitFundBasedInterest.value='0.0';
				return false;
			}
			if(parseFloat(interest) < parseFloat(plr)){				
				alert('Interest rate can not be less than PLR rate.');
				document.forms[0].limitFundBasedInterest.value='0.0';
				return false;
			}
			if(parseFloat(interest) > (parseFloat(plr) + 4)){
				alert('Difference between plr and interest can not be more than 4%.');
				document.forms[0].limitFundBasedInterest.value='0.0';				
				return false;
			}
		}
		if(rateType === 'plr'){
			if(parseFloat(interest) > (parseFloat(plr) + 4)){				
				alert('Difference between plr and interest can not be more than 4%.');
				document.forms[0].limitFundBasedInterest.value='0.0';
				return false;
			}
		}
		
	}
</script>									
							<table width="100%" border="0" cellspacing="1" cellpadding="0" colspan="5">
								 <TR>
									<TD class="SubHeading" width="843"><bean:message key="workingCapital" />
									</TD>						
								</TR>						
										
									
 
								<tr align="left"> 
								<td colspan="12" style="width:100%;">
									<table style="width:100%;">
									 <tr>
									 	<!--<td class="ColumnBackground" style="width:25% !important;" height="28">&nbsp;
										<bean:message key="amountSanctioned" />
									</td>
									<td style="width:25% !important;" class="TableData" height="28" id="amountSanctioned">
										<%		
										String loanTypd_d = session.getAttribute("APPLICATION_LOAN_TYPE").toString();
										System.out.println("loanTypd_d :"+loanTypd_d);
						String appTCFlag=session.getAttribute(SessionConstants.APPLICATION_TYPE_FLAG).toString();
											if((appTCFlag.equals("0"))||(appTCFlag.equals("3"))||(appTCFlag.equals("5"))||(appTCFlag.equals("6") || (appCommonFlag.equals("11"))||(appCommonFlag.equals("13")))
											)

											{
											%>
											<html:text name="appForm" property="amountSanctioned"/>
											<%}
											else
											{ %>
											<bean:message key="inRs"/>
											<%}%>
												
									</td>
									-->
									
									
									<td style="width:25%" class="ColumnBackground" height="28">&nbsp;
										<font color="#FF0000" size="2">*</font>&nbsp;<bean:message key="sanctionedDate" />
									</td>
									<td style="width:25%" class="TableData" height="28">
									<%				
										if((appCommonFlag.equals("11"))||(appCommonFlag.equals("13")))									
										{
										%>
										<bean:write name="appForm" property="sanctionedDate"/>
										<%}
										else
										{ 
									         if(loanTypd_d.equals("BO")||loanTypd_d.equals("CC")){
									         %>
									         <html:text property="sanctionedDate" size="20" alt="sanctionedDate" styleId="sanctionedDate" name="appForm" maxlength="10"/>
											  <IMG src="images/CalendarIcon.gif" width="20"  onclick="showCalendar('appForm.sanctionedDate')" align="center">
									       <%  }else{ %>
									           <html:text property="sanctionedDate" size="20" alt="sanctionedDate" styleId="sanctionedDate" name="appForm" maxlength="10"/>
											  <IMG src="images/CalendarIcon.gif" width="20"  onclick="showCalendar('appForm.sanctionedDate')" align="center">									         
									         
									     <%}}%>
											
									</td>
									
									<td class="ColumnBackground" height="28" style="width:25%">&nbsp;
										<font color="#FF0000" size="2">*</font>&nbsp;<bean:message key="sanctionTenure" />&nbsp;
									</td>
									<td class="TableData" height="28" style="width:25%">	
									<%				
										if((appCommonFlag.equals("11"))||(appCommonFlag.equals("13")))
										

										{
										%>
										<bean:write name="appForm" property="sanctionTenure"/>
										<%}
										else
										{ %>
									
										<html:text property="sanctionTenure" size="20" alt="sanctionTenure" name="appForm" maxlength="3" onkeypress="return numbersOnly(this, event)" onkeyup="isValidNumber(this)"/><bean:message key="inMonths" />
									<%}%>
									
									</td>
									 </tr>
									 <tr>
					
									
									
									
									<td class="ColumnBackground" height="28" style="width:25%;">&nbsp;
										<font color="#FF0000" size="2">*</font>&nbsp;<bean:message key="DPDStatus" />&nbsp;
									</td>
									<td class="TableData" height="28" style="width:25%;" >	
									<%				
										if((appCommonFlag.equals("11"))||(appCommonFlag.equals("13")))
										

										{
										%>
										<bean:write name="appForm" property="DPDStatus"/>
										<%}
										else
										{ %>
									
										<html:text property="DPDStatus" size="20" alt="DPDStatus" name="appForm" maxlength="3" onkeypress="return numbersOnly(this, event)" onkeyup="isValidNumber(this)"/>in days
									<%}%>
									
									</td>	
									<td style="width:25% !important;" class="ColumnBackground" height="28">&nbsp;
										<font color="#FF0000" size="2">*</font>&nbsp;<bean:message key="amountSanctionedDate" />
									</td>
									<td style="width:25% !important;" class="TableData" height="28" >
									<%				
										if((appCommonFlag.equals("11"))||(appCommonFlag.equals("13")))									
										{
										%>
										<bean:write name="appForm" property="amountSanctionedDate"/>
										<%}
										else
										{ 
									         if(loanTypd_d.equals("BO")||loanTypd_d.equals("CC")){
									         %>
									         <html:text property="amountSanctionedDate" size="20" alt="amountSanctionedDate" styleId="amountSanctionedDate" name="appForm" maxlength="10"/>
											  <IMG src="images/CalendarIcon.gif" width="20"  onclick="showCalendar('appForm.amountSanctionedDate')" align="center">
									       <%  }else{ %>
									           <html:text property="amountSanctionedDate" size="20" alt="amountSanctionedDate" styleId="amountSanctionedDate" name="appForm" maxlength="10" onblur="return enableExtGreenFld('TC');"/>
											  <IMG src="images/CalendarIcon.gif" width="20"  onclick="showCalendar('appForm.amountSanctionedDate')" align="center">									         
									         
									     <%}}%>
											
									</td>
									 </tr>
									 <tr>
									 	
									 </tr>
									 <tr>
									 	<td class="ColumnBackground" height="28" width="25% !important;">&nbsp;
										<font color="#FF0000" size="2">*</font><bean:message key="pplOS" />
									</td>
									<td colspan="10" class="TableData" height="28" width="25% !important;">
									<%				
										if((appCommonFlag.equals("11"))||(appCommonFlag.equals("13")) || (appCommonFlag.equals("3")) || (appCommonFlag.equals("6")))			

										{
										%>
										<bean:write name="appForm" property="pplOS"/>
										<%}
										else
										{ %>
									
										<html:text property="pplOS" size="25"  alt="pplOS" name="appForm" maxlength="16" onkeypress="return decimalOnly(this, event,13)" onkeyup="isValidDecimal(this)"/>
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
										<IMG src="images/CalendarIcon.gif" width="20" onclick="showCalendar('appForm.pplOsAsOnDate')" align="center">
										<%}%>
										
									</td>
									 </tr>
									 <tr>
									 <td class="ColumnBackground" height="28" width="25% !important;">
									<font color="#FF0000" size="2">*</font>&nbsp;<bean:message key="creditGuaranteed" />
									</td>
									<td width="25% !important;" colspan="10" class="TableData" height="28">
									<%				
										if((appCommonFlag.equals("11"))||(appCommonFlag.equals("13")))
										{
										%>
										<bean:write name="appForm" property="creditGuaranteed"/>											
										<%}
										else
										{
										%>
										    <html:hidden property="exposurelmtAmt" name="appForm"/>				
											<html:text property="creditGuaranteed" styleId="creditGuaranteed" size="20" alt="creditGuaranteed" name="appForm" maxlength="16" onblur="checkGurentyMaxtotalMIcollatSecAmt();"  onkeyup="return enableExtGreenFld('TC');"  onkeypress="return decimalOnly(this, event,13);"/>&nbsp;<bean:message key="inRs" />
									     	<html:hidden property="creditGuaranteed" styleId="creditGuaranteedhid" name="appForm"/>									     	
									     	<div id="FBerrorsMessage" class="errorsMessage"></div>											
									   	<%}%>
										
									</td>
									 </tr>
									 
									 <tr>
									 	<td class="ColumnBackground" height="28" width="25% !important">&nbsp;
										<bean:message key="amtDisbursed" />		
									</td>
									<td class="TableData" height="28" width="25% !important">
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
									<td class="ColumnBackground" height="28" colspan="10">
										<table border="0" cellpadding="0" cellspacing="0"  width="100%">
											<tr align="left">
												<td class="ColumnBackground" height="28" width="269">&nbsp;
													<font color="#FF0000" size="2">*</font><bean:message key="firstDisbursementDate" />	
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
													<IMG src="images/CalendarIcon.gif" width="20" onclick="showCalendar('appForm.firstDisbursementDate')" align="center">
												<%}%>
												</td>
											</tr>
											<tr align="left">
												<td class="ColumnBackground" height="28" width="269" >&nbsp;
												<%
												if(appCommonFlag.equals("11")||(appCommonFlag.equals("13")) || appTCFlag.equals("3") || appTCFlag.equals("6"))
												{
												%>
													<bean:message key="lastDisbursementDate" />	
													<%
												}
												else
												{%>
												<bean:message key="finalDisbursementDate" />
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
													<IMG src="images/CalendarIcon.gif" width="20" onclick="showCalendar('appForm.finalDisbursementDate')" align="center">
												<%}%>
												</td>
											</tr>
										</table>
									</td>
									 </tr>
									 	<!-- DKR 32-->
							  <%
							    String loanTypk = session.getAttribute("APPLICATION_LOAN_TYPE").toString();
								if((loanTypk.equals("TC") && !loanTypk.equals("BO") && !loanTypk.equals("TCE")))		
                                {
						      %> 
								<!-- <tr align="left">
						          <TD align="left" valign="top" class="ColumnBackground"  colspan="2"><font color="#FF0000" size="2">*</font><bean:message
										key="inCrilcCibilRbi" /></TD>
								<TD align="left" valign="top" class="tableData" colspan="2">
								<html:radio	name="appForm" value="Y" property="promDirDefaltFlg" styleId="promDirDefaltFlg_Y" onclick="enableOtherFinancialDtl('TC');" />
										 <bean:message key="yes" />&nbsp;&nbsp;
										  <html:radio name="appForm" value="N" property="promDirDefaltFlg" styleId="promDirDefaltFlg_N" onclick="enableOtherFinancialDtl('TC');" />
									<bean:message key="no" /></TD>	
						       </tr> -->	
					       		<!-- 	========================================== -->
									 <tr>
									 	 
									 </tr>
									 </table>
								</td>	 
									
								 </tr>
								 <% 
								 //String ID=session.getAttribute("expoid").toString();%>
						 <!-- Diksha -->
						 
						 <tr> 
									
								</tr>		
								
									<tr align="left"> 
									 
						<!--<td width="15%" class="ColumnBackground" height="28">
									
									<%
									//if(session.getAttribute("expoid").equals("")==false)
									//{										
									%>
										&nbsp;<bean:message key="fbschemechk" />	
									</td>
									<td width="20%" class="TableData" height="28" colspan="8">
							  
							                                   <html:radio name="appForm" value="Y1" property="exposureFbId" ></html:radio>
											
																<bean:message key="yes" />&nbsp;&nbsp;
																
																<html:radio name="appForm" value="N1" property="exposureFbId" ></html:radio>
																
																<bean:message key="no"/>  
								 </tr>
                                   <%
                                   //} 
                                   %>-->
								<tr> 
									
								</tr>
							
					       		<TR>
								  <TD colspan="4">
									<TABLE width="100%" id="financialOtherDtlLblId" 
										<%
											String gFinancialUIflag="DFALSEUI";
												if(null!=session.getAttribute("gFinancialUIflag")){
													gFinancialUIflag = (String)session.getAttribute("gFinancialUIflag");		
													System.out.println("gFinancialUIflag>>>>>>>>1>>>>>>"+gFinancialUIflag);
									   }						
								    	if(gFinancialUIflag.equalsIgnoreCase("DTRUEUI")){  System.out.println("gFinancialUIflag>>>>>2>>>>DTRUEUI>>>>>"+gFinancialUIflag); %>
								    	
								    	 style="display: block;"
                                        <%}else if(gFinancialUIflag.equalsIgnoreCase("DFALSEUI")){     	System.out.println("gFinancialUIflag>>>>>>3>>>DFALSEUI>>>>>"+gFinancialUIflag);
                                        %>
								    	  style="display:none;" <%}%> >	
								   	 
								<tr align="left">
						          <TD align="left" valign="top" class="ColumnBackground"  colspan="2"><font color="#FF0000" size="2">*</font>
						          <bean:message	key="inCrilcCibilRbi" /></TD>
								<TD align="left" valign="top" class="tableData">
								<html:radio	name="appForm" value="Y" property="promDirDefaltFlg" styleId="promDirDefaltFlg_Y" />
										 <bean:message key="yes" />&nbsp;&nbsp;
										  <html:radio name="appForm" value="N" property="promDirDefaltFlg" styleId="promDirDefaltFlg_N" />
									<bean:message key="no" /></TD>	
						       </tr>     
					             <tr align="left">
										<td class="ColumnBackground" width="25%"><font color="#FF0000" size="2">*</font>
										<bean:message key="credBureName1" /></td>
										<td class="TableData" width="20%" align="left" valign="top">
											<%if (appTCFlag.equals("11") 
													|| appTCFlag.equals("13")) {
																	%> <bean:write property="credBureName1" name="appForm" /> <%
																		} else {
																	%> <html:text property="credBureName1" size="20"
												alt="credBureName1" name="appForm"/>
												 <%	} %> 
										</td>
										<td class="ColumnBackground" width="25%">&nbsp;<font color="#FF0000" size="2">*</font> <bean:message
												key="credBureScorKeyProm" />
										</td>
										<td class="TableData" width="20%" align="left" valign="top">
											<%
											if (appTCFlag.equals("11") 
													|| appTCFlag.equals("13")){
																	%> <bean:write property="credBureKeyPromScor" name="appForm" />
																	 <%
																		} else {
																	%> <html:text property="credBureKeyPromScor" size="20"
												alt="credBureKeyPromScor" name="appForm" maxlength="3" />
												<!-- 	alt="credBureKeyPromScor" name="appForm" maxlength="3" onkeyup="isValidDecimal(this)" />  -->
												 <bean:message key="3to9" /><%
												
																		}
																	%> 
										</td>
									</tr>						       						
									<tr align="left">
										<td class="ColumnBackground" width="25%"><font color="#FF0000" size="2"></font><bean:message
												key="credBureName2" /></td>
										<td class="TableData" width="20%" align="left" valign="top">
											<%
																		if (appTCFlag.equals("11") 
																				|| appTCFlag.equals("13")) {
																	%> <bean:write property="credBureName2"
												name="appForm" /> <%
																		} else {
																	%> <html:text property="credBureName2" size="20"
												alt="credBureName2" name="appForm" /> <%
																		}
																	%> 
										</td>
										<td class="ColumnBackground" width="25%">&nbsp;<font color="#FF0000" size="2"></font> <bean:message
												key="credBureScoreProm2" />
										</td>
										<td class="TableData" width="20%" align="left" valign="top">
											<%
																		if (appTCFlag.equals("11") 
																				|| appTCFlag.equals("13")) {
																	%> <bean:write property="credBurePromScor2"
												name="appForm" /> <%
																		} else {
																	%> <html:text property="credBurePromScor2" size="20"
												alt="credBurePromScor2" name="appForm" maxlength="3"
												onblur="javascript:calProjectOutlay()"
												onkeypress="return decimalOnly(this, event,13)"
												onkeyup="isValidDecimal(this)" /> <%
																		}
																	%> <bean:message key="3to9" />
										</td>
									</tr>								
									<tr align="left">
										<td class="ColumnBackground" width="25%"><font color="#FF0000" size="2"></font><bean:message
												key="credBureName3" /></td>
										<td class="TableData" width="20%" align="left" valign="top">
											<%
																		if (appTCFlag.equals("11") 
																				|| appTCFlag.equals("13")) {
																	%> <bean:write property="credBureName3"
												name="appForm" /> <%
																		} else {
																	%> <html:text property="credBureName3" size="20"
												alt="credBureName3" name="appForm" /> <%
																		}
																	%> 
										</td>
										<td class="ColumnBackground" width="25%">&nbsp;<font color="#FF0000" size="2"></font> <bean:message
												key="credBureScoreProm3" />
										</td>
										<td class="TableData" width="20%" align="left" valign="top">
											<%
											if (appTCFlag.equals("11") 
													|| appTCFlag.equals("13")) {
											%> <bean:write property="credBurePromScor3"
												name="appForm" />
												<%
													} else {
												%> <html:text property="credBurePromScor3" size="20"
												alt="credBurePromScor3" name="appForm" maxlength="3"
												onblur="javascript:calProjectOutlay()"
												onkeypress="return decimalOnly(this, event,13)"
												onkeyup="isValidDecimal(this)" />
												 <%
													}
												 %> 
												 <bean:message key="3to9" />
										</td>
									</tr>							
									
									<tr align="left">
										<td class="ColumnBackground" width="25%"><font color="#FF0000" size="2"></font><bean:message
												key="credBureName4" /></td>
										<td class="TableData" width="20%" align="left" valign="top">
											<%
																		if(appTCFlag.equals("11") 
																				|| appTCFlag.equals("13")) {
																	%> <bean:write property="credBureName4"
												name="appForm" /> <%
																		} else {
																	%> <html:text property="credBureName4" size="20"
												alt="credBureName4" name="appForm" /> <%
																		}
																	%> 
										</td>
										<td class="ColumnBackground" width="25%">&nbsp;<font color="#FF0000" size="2"></font> <bean:message
												key="credBureScoreProm4" />
										</td>
										<td class="TableData" width="20%" align="left" valign="top">
											<%
																		if (appTCFlag.equals("11") 
																				|| appTCFlag.equals("13")) {
																	%> <bean:write property="credBurePromScor4"
												name="appForm" /> <%
																		} else {
																	%> <html:text property="credBurePromScor4" size="20"
												alt="credBurePromScor4" name="appForm" maxlength="3"
												onblur="javascript:calProjectOutlay()"
												onkeypress="return decimalOnly(this, event,13)"
												onkeyup="isValidDecimal(this)" /> <%
																		}
																	%> <bean:message key="3to9" />
										</td>
									</tr>									
									<tr align="left">
										<td class="ColumnBackground" width="25%"><font color="#FF0000" size="2"></font><bean:message
												key="credBureName5" /></td>
										<td class="TableData" width="20%" align="left" valign="top">
											<%
																		if(appTCFlag.equals("11") 
																				|| appTCFlag.equals("13")) {
																	%> <bean:write property="credBureName5"
												name="appForm" /> <%
																		} else {
																	%> <html:text property="credBureName5" size="20"
												alt="credBureName5" name="appForm"  /> <%
																		}
																	%> 
										</td>
										<td class="ColumnBackground" width="25%">&nbsp;<font color="#FF0000" size="2"></font> <bean:message
												key="credBureScoreProm5" />
										</td>
										<td class="TableData" width="20%" align="left" valign="top">
											<%
																		if (appTCFlag.equals("11") 
																				|| appTCFlag.equals("13")) {
																	%> <bean:write property="credBurePromScor5"
												name="appForm" /> <%
																		} else {
																	%> <html:text property="credBurePromScor5" size="20"
												alt="credBurePromScor5" name="appForm" maxlength="3"
												onblur="javascript:calProjectOutlay()"
												onkeypress="return decimalOnly(this, event,13)"
												onkeyup="isValidDecimal(this)" /> <%
																		}
												%> <bean:message key="3to9" />
										</td>
									</tr>
									<tr align="left">
										<td class="ColumnBackground" width="25%"><font color="#FF0000" size="2">*</font><bean:message
												key="cibMSMERankFirm" /></td>
										<td class="TableData" width="20%" align="left" valign="top">
											<%
																		if (appTCFlag.equals("11") 
																				|| appTCFlag.equals("13")) {
																	%> <bean:write property="cibilFirmMsmeRank"
												name="appForm" /> <%
																		} else {
																	%> <html:text property="cibilFirmMsmeRank" size="20"
												alt="credBureName5" name="appForm" maxlength="2"
												onblur="javascript:calProjectOutlay()"
												onkeypress="return decimalOnly(this, event,13)"
												onkeyup="isValidDecimal(this)" /> value between(1-10) <%
																		}
																	%> 
										</td>
										<td class="ColumnBackground" width="25%">&nbsp;<font color="#FF0000" size="2">*</font> <bean:message
												key="expCommercialScore" />
										</td>
										<td class="TableData" width="20%" align="left" valign="top">
											<%
																		if (appTCFlag.equals("11") 
																				|| appTCFlag.equals("13")){
																	%> <bean:write property="expCommerScor"
												name="appForm" /> <%
																		} else {
																	%> <html:text property="expCommerScor" size="20"
												alt="credBurePromScor5" name="appForm" maxlength="3"
												onblur="javascript:calProjectOutlay()"
												onkeypress="return decimalOnly(this, event,13)"
												onkeyup="isValidDecimal(this)" /> <%
																		}
												%> <bean:message key="3to9" />
										</td>
									</tr>
									<tr align="left">
										<td class="ColumnBackground" width="25%"><font color="#FF0000" size="2">*</font><bean:message
												key="promNetworth" /></td>
										<td class="TableData" width="20%" align="left" valign="top">
											<%
																		if(appTCFlag.equals("11") 
																				|| appTCFlag.equals("13")) {
																	%> <bean:write property="promBorrNetWorth"
												name="appForm" /> <%
																		} else {
																	%> <html:text property="promBorrNetWorth" size="20"
												alt="promBorrNetWorth" name="appForm" 
												onblur="javascript:calProjectOutlay()"
												onkeypress="return decimalOnly(this, event,13)"
												onkeyup="isValidDecimal(this)" /> (in Rs. Lacs)
												 <%
													}
												%> 
										</td>
										<td class="ColumnBackground" width="25%">&nbsp;<font color="#FF0000" size="2">*</font> <bean:message
												key="contributPromEntity" />
										</td>
										<td class="TableData" width="20%" align="left" valign="top">
											<%
																		if (appTCFlag.equals("11") 
																				|| appTCFlag.equals("13")) {
																	%> <bean:write property="promContribution"
												name="appForm" /> <%
																		} else {
																	%> <html:text property="promContribution" size="20"
												alt="promContribution" name="appForm" maxlength="3"
												onblur="javascript:calProjectOutlay()"
												onkeypress="return decimalOnly(this, event,13)"
												onkeyup="isValidDecimal(this)" />(Between 0-100%)
											 <%
												}
												%> 
										</td>
									</tr>
								   <tr align="left">
										<td class="ColumnBackground" width="25%"><font color="#FF0000" size="2">*</font><bean:message
												key="promNpainPast" /></td>
										<td class="TableData" width="20%" align="left" valign="top">
											<%
											if (appTCFlag.equals("11") 
													|| appTCFlag.equals("13")){
											%> <bean:write property="promGAssoNPA1YrFlg" name="appForm" /> <%
												} else {
											%>
											 <html:radio name="appForm" value="Y" property="promGAssoNPA1YrFlg"/>
											 <bean:message key="yes" />&nbsp;&nbsp;
											  <html:radio name="appForm" value="N" property="promGAssoNPA1YrFlg" />
										      <bean:message key="no" />
										 <%
											}
										%> 
										</td>
										<td class="ColumnBackground" width="25%">&nbsp;<font color="#FF0000" size="2">*</font> <bean:message
												key="promExpRelBusiness" />
										</td>
										<td class="TableData" width="20%" align="left" valign="top">
											<%
																		if(appTCFlag.equals("11") 
																				|| appTCFlag.equals("13")) {
																	%> <bean:write property="promBussExpYr"
												name="appForm" /> <%
																		} else {
																	%> <html:text property="promBussExpYr" size="20"
												alt="promContribution" name="appForm" maxlength="3"
												onblur="javascript:calProjectOutlay()"
												onkeypress="return decimalOnly(this, event,13)"
												onkeyup="isValidDecimal(this)" />(Between 0-100)
											 <%
												}
												%> 
										</td>
									</tr>									
									<tr align="left">
										<td class="ColumnBackground" width="25%"><font color="#FF0000" size="2">*</font><bean:message
												key="salesRevenue" /></td>
										<td class="TableData" width="20%" align="left" valign="top">
											<%
																		if(appTCFlag.equals("11") 
																				|| appTCFlag.equals("13")) {
																	%> <bean:write property="salesRevenue"
												name="appForm" /> <%
																		} else {
																	%> <html:text property="salesRevenue" size="20"
												alt="salesRevenue" name="appForm" onblur="javascript:calProjectOutlay()"
												onkeypress="return decimalOnly(this, event,13)"
												onkeyup="isValidDecimal(this)" /> (in Rs. Lacs)
												 <%
													}
												%> 
										</td>
										<td class="ColumnBackground" width="25%">&nbsp;<font color="#FF0000" size="2">*</font> <bean:message
												key="taxPBIT" />
										</td>
										<td class="TableData" width="20%" align="left" valign="top">
											<%
																		if(appTCFlag.equals("11") 
																				|| appTCFlag.equals("13")) {
																	%> <bean:write property="taxPBIT"
												name="appForm" /> <%
																		} else {
																   %> <html:text property="taxPBIT" size="20"
												alt="taxPBIT" name="appForm" onblur="javascript:calProjectOutlay()"
												onkeypress="return decimalOnly(this, event,13)"
												onkeyup="isValidDecimal(this)" />(in Rs. Lacs)
											    <%
												}
												%> 
										</td>
									</tr>
									
									<tr align="left">
										<td class="ColumnBackground" width="25%"><font color="#FF0000" size="2">*</font><bean:message
												key="interestPayment" /></td>
										<td class="TableData" width="20%" align="left" valign="top">
											 <%
													if (appTCFlag.equals("11") || appTCFlag.equals("13")){
											 %> <bean:write property="interestPayment"
												name="appForm" /> 
												<%
													} else {
												%> <html:text property="interestPayment" size="20"
												alt="interestPayment" name="appForm" onblur="javascript:calProjectOutlay()"
												onkeypress="return decimalOnly(this, event,13)"
												onkeyup="isValidDecimal(this)" /> (in Rs. Lacs)
												 <%
													}
												%> 
										</td>
										<td class="ColumnBackground" width="25%">&nbsp;<font color="#FF0000" size="2">*</font> <bean:message
												key="taxCurrentProvisionAmt" />
										</td>
										<td class="TableData" width="20%" align="left" valign="top">
											<%
																		if (appTCFlag.equals("11") 
																				|| appTCFlag.equals("13")) {
																	%> <bean:write property="taxCurrentProvisionAmt"
												name="appForm" /> <%
																		} else {
																	%> <html:text property="taxCurrentProvisionAmt" size="20"
												alt="taxCurrentProvisionAmt" name="appForm" onblur="javascript:calProjectOutlay()"
												onkeypress="return decimalOnly(this, event,13)"
												onkeyup="isValidDecimal(this)" />(in Rs. Lacs)
											 <%
												}
												%> 
										</td>
									</tr>
									
									
									<tr align="left">
										<td class="ColumnBackground" width="25%"><font color="#FF0000" size="2">*</font><bean:message
												key="totCurrentAssets" /></td>
										<td class="TableData" width="20%" align="left" valign="top">
											<%
																		if (appTCFlag.equals("11") 
																				|| appTCFlag.equals("13")) {
											%> <bean:write property="totCurrentAssets" name="appForm" /> <%
																		} else {
																	%> <html:text property="totCurrentAssets" size="20"
												alt="totCurrentAssets" name="appForm" onblur="javascript:calProjectOutlay()"
												onkeypress="return decimalOnly(this, event,13)"
												onkeyup="isValidDecimal(this)" /> (in Rs. Lacs)
												 <%
													}
												%> 
										</td>
										<td class="ColumnBackground" width="25%">&nbsp;<font color="#FF0000" size="2">*</font> <bean:message
												key="totCurrentLiability" />
										</td>
										<td class="TableData" width="20%" align="left" valign="top">
											<%
																		if (appTCFlag.equals("11") 
																				|| appTCFlag.equals("13")) {
																	%> <bean:write property="totCurrentLiability"
												name="appForm" /> <%
																		} else {
																	%> <html:text property="totCurrentLiability" size="20"
												alt="totCurrentLiability" name="appForm" 
												onkeypress="return decimalOnly(this, event,13)"
												onkeyup="isValidDecimal(this)" />(in Rs. Lacs)
											 <%
												}
												%> 
										</td>
									</tr>
									
									
									<tr align="left">
										<td class="ColumnBackground" width="25%"><font color="#FF0000" size="2">*</font><bean:message
												key="totTermLiability" /></td>
										<td class="TableData" width="20%" align="left" valign="top">
											<%
																		if(appTCFlag.equals("11") 
																				|| appTCFlag.equals("13")) {
																	%> <bean:write property="totTermLiability"
												name="appForm" /> <%
																		} else {
																	%> <html:text property="totTermLiability" size="20"
												alt="totTermLiability" name="appForm" 
												onkeypress="return decimalOnly(this, event,13)"
												onkeyup="isValidDecimal(this)" /> (in Rs. Lacs)
												 <%
													}
												%> 
										</td>
										<td class="ColumnBackground" width="25%">&nbsp;<font color="#FF0000" size="2">*</font> <bean:message
												key="exuityCapital" />
										</td>
										<td class="TableData" width="20%" align="left" valign="top">
											<%
																		if(appTCFlag.equals("11") 
																				|| appTCFlag.equals("13")) {
																	%> <bean:write property="exuityCapital"
												name="appForm" /> <%
																		} else {
																	%> <html:text property="exuityCapital" size="20"
												alt="exuityCapital" name="appForm" 
												onkeypress="return decimalOnly(this, event,13)"
												onkeyup="isValidDecimal(this)" />(in Rs. Lacs)
											 <%
												}
												%> 
										</td>
									</tr>
									
									<tr align="left">
										<td class="ColumnBackground" width="25%"><font color="#FF0000" size="2">*</font><bean:message
												key="preferenceCapital" /></td>
										<td class="TableData" width="20%" align="left" valign="top">
											<%
																		if (appTCFlag.equals("11") 
																				|| appTCFlag.equals("13")) {
																	%> <bean:write property="preferenceCapital"
												name="appForm" /> <%
																		} else {
																	%> <html:text property="preferenceCapital" size="20"
												alt="preferenceCapital" name="appForm" 
												onkeypress="return decimalOnly(this, event,13)"
												onkeyup="isValidDecimal(this)" /> (in Rs. Lacs)
												 <%
													}
												%> 
										</td>
										<td class="ColumnBackground" width="25%">&nbsp;<font color="#FF0000" size="2">*</font> <bean:message
												key="reservesSurplus" />
										</td>
										<td class="TableData" width="20%" align="left" valign="top">
											<%
																		if (appTCFlag.equals("11") 
																				|| appTCFlag.equals("13")) {
																	%> <bean:write property="reservesSurplus"
												name="appForm" /> <%
																		} else {
																	%> <html:text property="reservesSurplus" size="20"
												alt="exuityCapital" name="appForm" 
												onkeypress="return decimalOnly(this, event,13)"
												onkeyup="isValidDecimal(this)" />(in Rs. Lacs)
											 <%
												}
												%> 
										</td>
									</tr>
									
									<tr align="left">
										<td class="ColumnBackground" width="25%"><font color="#FF0000" size="2">*</font><bean:message
												key="repaymentDueNyrAmt" /></td>
										<td class="TableData" width="20%" align="left" valign="top">
											<%
																		if (appTCFlag.equals("11") 
																				|| appTCFlag.equals("13")) {
																	%> <bean:write property="repaymentDueNyrAmt"
												name="appForm" /> <%
																		} else {
																	%> <html:text property="repaymentDueNyrAmt" size="20"
												alt="repaymentDueNyrAmt" name="appForm" 
												onkeypress="return decimalOnly(this, event,13)"
												onkeyup="isValidDecimal(this)" /> (in Rs. Lacs)
												 <%
													}
												%> 
										</td>
										<td class="ColumnBackground" width="25%">&nbsp;<!--  <bean:message
												key="reservesSurplus" /> -->
										</td>
										<td class="TableData" width="20%" align="left" valign="top">
											<%
																		if (appTCFlag.equals("11") 
																				|| appTCFlag.equals("13")) {
																	%> <!-- <bean:write property="reservesSurplus"
												name="appForm" /> --> <%
																		} else {
																	%> <!-- <html:text property="reservesSurplus" size="20"
												alt="exuityCapital" name="appForm" maxlength="16"
												onblur="javascript:calProjectOutlay()"
												onkeypress="return decimalOnly(this, event,13)"
												onkeyup="isValidDecimal(this)" />(in Rs. Lacs) -->
											 <%
												}
											 %> 
										</td>
									</tr>
					            	</table>
		                          </TD>
								</TR>
								 <!--CR 158 End -->	
								
								  <!--CR 159 add by DKR -->						
								<!-- <tr align="left"  id="existGreenFldUnitType_id" style="display:none;">
						          <TD align="left" valign="top" class="ColumnBackground"  colspan="2"><font color="#FF0000" size="2">*</font>
						          <bean:message key="existGreenFieldUnits" /></TD>
								<TD align="left" valign="top" class="tableData" colspan="2">
								<html:radio	property="existGreenFldUnitType" name="appForm" value="Existing"/>
								 <bean:message key="existUnt" />&nbsp;&nbsp;
								 <html:radio  property="existGreenFldUnitType" name="appForm" value="Greenfield" />
									<bean:message key="GreenUnt" /></TD>	
						       </tr> -->
						      <%
						      //session.setAttribute("dblockUI","UI_2");
						      
						      String dblockUIEnable="";
								if(null!=session.getAttribute("dblockUI")){
									dblockUIEnable = (String) session.getAttribute("dblockUI");	
									System.out.println("dblockUIEnable>>>>>>>>1>>>>>>"+dblockUIEnable);
								}
						      %>						       
						       <TR>						       
								<TD colspan="4">																
								<!--  <table width="100%" id="existGreenUnitUI_1to10L" style="display:none;">	 -->								 
								 <%-- <TABLE width="100%" id="existGreenFldUnitType_id" 
										<%
											String gExgGreenUIflag="RFALSEUI";
												if(null!=session.getAttribute("gExgGreenUIFlag")){
													gExgGreenUIflag = (String)session.getAttribute("gExgGreenUIFlag");		
													System.out.println("gExgGreenUIflag>>>>>>>>1>>>>>>"+gExgGreenUIflag);
									   }						
								    	if(gExgGreenUIflag.equalsIgnoreCase("RTRUEUI")){  System.out.println("gExgGreenUIflag>>>>>2>>>>RTRUEUI>>>>>"+gExgGreenUIflag); %>
								    	
								    	 style="display: block;"
                                        <%}else if(gExgGreenUIflag.equalsIgnoreCase("RFALSEUI")){     	System.out.println("gExgGreenUIflag>>>>>>3>>>RFALSEUI>>>>>"+gExgGreenUIflag);
                                        %>
								    	  style="display:none;" <%}%> >  --%>
								    	  
								   <TABLE width="100%" id="existGreenFldUnitType_id"  	
								    	  <%																
								    	if(dblockUIEnable.equalsIgnoreCase("UI_1") || dblockUIEnable.equalsIgnoreCase("UI_2")||dblockUIEnable.equalsIgnoreCase("UI_3")){
								    		System.out.println(dblockUIEnable+".................................UI_1.");
								    	%>								    	
								    	 style="display: block;"
                                        <%}else{ 
                                        %>
								    	  style="display:none;" <%}%>
								    >						 
								  <tr align="left"  >
						          <TD align="left" valign="top" class="ColumnBackground"  colspan="2"><font color="#FF0000" size="2">*</font>
								           <bean:message key="existGreenFieldUnits" /></TD>
									     	<TD align="left" valign="top" class="tableData" colspan="2">
										  <html:radio	property="existGreenFldUnitType" name="appForm" value="Existing"/>
										   <bean:message key="existUnt" />&nbsp;&nbsp;
										   <html:radio  property="existGreenFldUnitType" name="appForm" value="Greenfield" />
											<bean:message key="GreenUnt" /></TD>	
						       </tr>
						            <tr align="left"  id="existGreenUnitUI_1to10L">
										<td class="ColumnBackground" width="25%"><font color="#FF0000" size="2">*</font>
										<bean:message key="oprtInc" /></td>
										<td class="TableData" width="20%" align="left" valign="top">
											<%
																		if (appTCFlag.equals("11") 
																				|| appTCFlag.equals("13")) {
											%> <bean:write property="opratIncome" name="appForm" />
											 <%
												} else {
											 %> <html:text property="opratIncome" size="20" alt="opratIncome" name="appForm" onkeypress="return decimalOnly(this, event,13)" onkeyup="isValidDecimal(this)" />
											 <%	} %> 
										</td>
										<td class="ColumnBackground" width="25%">&nbsp;<font color="#FF0000" size="2">*</font> 
										<bean:message key="pat" />
										</td>
										<td class="TableData" width="20%" align="left" valign="top">
											<%
																		if (appTCFlag.equals("11") 
																				|| appTCFlag.equals("13")) {
											%> <bean:write property="profAftTax" name="appForm" /> 
											<%
												} else {
											%> <html:text property="profAftTax" size="20" alt="profAftTax" name="appForm" onkeypress="return decimalOnly(this, event,13)" onkeyup="isValidDecimal(this)" />
											 <%
												}
												%> 
										</td>								
										<td class="ColumnBackground" width="25%"><font color="#FF0000" size="2">*</font><bean:message
												key="netwrth" /></td>
										<td class="TableData" width="20%" align="left" valign="top">
											<%
																		if (appTCFlag.equals("11") 
																				|| appTCFlag.equals("13")) {
											%> <bean:write property="networth" name="appForm" />
											 <%
												} else {
											 %> <html:text property="networth" size="20" alt="networth" name="appForm" onkeypress="return decimalOnly(this, event,13)" onkeyup="isValidDecimal(this)" />
											 <%	} %> 
										</td>
									</tr>								
									</table> 
									</TD></TR>
									
								  <TR>						       
								  <TD colspan="4">		
								  <!--  <table width="100%"  id="existGreenUnitUI_10to50L" style="display:none;"> -->
								     <TABLE width="100%" id="existGreenUnitUI_10to50L"  	
								    	 <%																
								    	if(dblockUIEnable.equalsIgnoreCase("UI_2") ){								    		
								    	System.out.println(dblockUIEnable+".................................UI_2.");
								    	%>										    							    	
								    	 style="display: block;"
                                        <%}else{ 
                                        %>
								    	  style="display:none;" <%}%>
								     >								   
						              <tr align="left">
										<td class="ColumnBackground" width="25%"><font color="#FF0000" size="2">*</font><bean:message
												key="dvtEqtRatioUnt" /></td>
										<td class="TableData" width="20%" align="left" valign="top">
											<%
																		if (appTCFlag.equals("11") 
																				|| appTCFlag.equals("13")) {
											%> <bean:write property="debitEqtRatioUnt" name="appForm" />
											 <%
												} else {
											 %> <html:text property="debitEqtRatioUnt" size="20" alt="debitEqtRatioUnt" name="appForm" onkeypress="return decimalOnly(this, event,13)" onkeyup="isValidDecimal(this)" />
											 <%	} %> 
										</td>
										
										<td class="ColumnBackground" width="25%">&nbsp;<font color="#FF0000" size="2">*</font> 
										<bean:message key="dvtSrvCoverageRatioTl" />
										</td>
										<td class="TableData" width="20%" align="left" valign="top">
											<%
																		if (appTCFlag.equals("11") 
																				|| appTCFlag.equals("13")) {
											%> <bean:write property="debitSrvCoverageRatioTl" name="appForm" /> 
											<%
												} else {
											%> <html:text property="debitSrvCoverageRatioTl" size="20" alt="debitSrvCoverageRatioTl" name="appForm" 
												onkeypress="return decimalOnly(this, event,13)"
												onkeyup="isValidDecimal(this)" />
											 <%
												}
												%> 
										</td>								
										<td class="ColumnBackground" width="25%"><bean:message
												key="crtRatioWc" /></td>
										<td class="TableData" width="20%" align="left" valign="top">
											<%
																		if (appTCFlag.equals("11") 
																				|| appTCFlag.equals("13")) {
											%> <bean:write property="currentRatioWc" name="appForm" />
											 <%
												} else {
											 %> <html:text property="currentRatioWc" size="20" alt="currentRatioWc" name="appForm" onkeypress="return decimalOnly(this, event,13)" onkeyup="isValidDecimal(this)" />
											 <%	} %> 
										</td>
									</tr>								
									</table> 
									</td></tr>	
								 <TR>						       
								    <TD colspan="4">																
								  <!--  <table width="100%"  id="existGreenUnitUI_50to100L" style="display:none;"> -->
								  <TABLE width="100%" id="existGreenUnitUI_50to100L"  	
								    	  <%																
								    	if(dblockUIEnable.equalsIgnoreCase("UI_3")){
								    		System.out.println(dblockUIEnable+".................................UI_3.");
								    	%>								    	
								    	 style="display: block;"
                                        <%}else{ 
                                        %>
								    	  style="display:none;" <%}%>
								     >
								  
						              <tr align="left">
										<td class="ColumnBackground" width="25%"><font color="#FF0000" size="2">*</font><bean:message
												key="dvtEqtRatio" /></td>
										<td class="TableData" width="20%" align="left" valign="top">
											<%
																		if (appTCFlag.equals("11") 
																				|| appTCFlag.equals("13")) {
											%> <bean:write property="debitEqtRatio" name="appForm" />
											 <%
												} else {
											 %> <html:text property="debitEqtRatio" size="20" alt="debitEqtRatio" name="appForm" onkeypress="return decimalOnly(this, event,13)" onkeyup="isValidDecimal(this)" />
											 <%	} %> 
										</td>
										<td class="ColumnBackground" width="25%">&nbsp;<font color="#FF0000" size="2">*</font> 
										<bean:message key="dvtSrvCoverageRatio" />
										</td>
										<td class="TableData" width="20%" align="left" valign="top">
											<%
																		if (appTCFlag.equals("11") 
																				|| appTCFlag.equals("13")) {
											%> <bean:write property="debitSrvCoverageRatio" name="appForm" /> 
											<%
												} else {
											%> <html:text property="debitSrvCoverageRatio" size="20" alt="debitSrvCoverageRatio" name="appForm"
											 onkeypress="return decimalOnly(this, event,13)" onkeyup="isValidDecimal(this)" />
											 <%
												}
												%> 
										</td>								
										<td class="ColumnBackground" width="25%"><font color="#FF0000" size="2">*</font><bean:message
												key="crtRatio" /></td>
										<td class="TableData" width="20%" align="left" valign="top">
											<%
																		if (appTCFlag.equals("11") 
																				|| appTCFlag.equals("13")) {
											%> <bean:write property="currentRatios" name="appForm" />
											 <%
												} else {
											 %> <html:text property="currentRatios" size="20" alt="currentRatios" name="appForm" onkeypress="return decimalOnly(this, event,13)" onkeyup="isValidDecimal(this)" />
											 <%	} %> 
										</td>
									</tr>	
									
								    <tr align="left">
										<td class="ColumnBackground" width="25%"><font color="#FF0000" size="2">*</font>
										<bean:message key="crdBureauChiefPromScor" /></td>
										<td class="TableData" width="20%" align="left" valign="top">
											<%
																		if (appTCFlag.equals("11") 
																				|| appTCFlag.equals("13")) {
																	%> <bean:write property="creditBureauChiefPromScor"	name="appForm" /> <%
																		} else {
																	%> <html:text property="creditBureauChiefPromScor" size="20"
												alt="creditBureauChiefPromScor" name="appForm" 
												onkeypress="return decimalOnly(this, event,13)"
												onkeyup="isValidDecimal(this)" /> 
												 <%
													}
												%> 
										</td>
										<td class="ColumnBackground" width="25%"><font color="#FF0000" size="2">*</font><bean:message
												key="totAssets" /> 
										</td>
										<td class="TableData" width="20%" align="left" valign="top">
											<%
																		if (appTCFlag.equals("11") 
																				|| appTCFlag.equals("13")) {
																	%>  <bean:write property="totalAssets" name="appForm" />  <%
																		} else {
																	%>  <html:text property="totalAssets" size="20"	alt="totalAssets" name="appForm" maxlength="16"	onkeypress="return decimalOnly(this, event,13)"
												onkeyup="isValidDecimal(this)" />
											 <%
												}
											 %> 
										</td>
									</tr>							
									</table> 
								  </TD>
								</TR>								
							      <!--CR 159 End -->								
						<%}%>
		                 
							  
							  
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
								<!--<tr align="left"> 
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
										<bean:message key="fixedInterest" />&nbsp;									
										<html:radio name="appForm" value="F" property="interestType" disabled="true"></html:radio>										
										<bean:message key="floatingInterest" />	
										<bean:write name="appForm" property="interestType"/>
										<%}
										else
										{ %>

										<html:radio name="appForm" value="T" property="interestType" >
										</html:radio>									
										<bean:message key="fixedInterest" />&nbsp;									
										<html:radio name="appForm" value="F" property="interestType" ></html:radio>										
										<bean:message key="floatingInterest" />	
									<%}%>									
										&nbsp;<html:text property="interestRate" size="5" alt="interestRate" name="appForm" maxlength="4" onkeypress="return decimalOnly(this, event)" onkeyup="isValidDecimal(this)"/>
										<bean:message key="inPer" />
									</td>
									
									    <td class="ColumnBackground" height="28" >&nbsp;
										<bean:message key="benchMarkPLR" />
									</td>
									<td class="TableData" height="28" width="160">&nbsp;
										<html:text property="benchMarkPLR" size="5" alt="benchMarkPLR" name="appForm" maxlength="3" onkeypress="return numbersOnly(this, event)" onkeyup="isValidNumber(this)"/>&nbsp;
										<bean:message key="inPer" />
									</td>
									<td class="ColumnBackground" height="28" >&nbsp;
										<bean:message key="benchMarkPLR" />
									</td>
									
									<td class="ColumnBackground" height="28" >&nbsp;
									<font color="#FF0000" size="2">*</font>&nbsp;<bean:message key="typeOfPLR" /> 
									Type of Base Rate
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
										
										<html:text disabled="true" property="typeOfPLR" size="20" alt="typeOfPLR" name="appForm" maxlength="50" />&nbsp;						
									<%}%>
									</td>
								</tr>
								-->
								<tr align="left">
									
									<!--<td width="25%" class="ColumnBackground"> <div align="left">&nbsp;
										<font color="#FF0000" size="2">*</font>&nbsp; <bean:message key="plr" /> Base Rate</div>
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
											<html:text property="plr" size="5" alt="plr" name="appForm" maxlength="6"  onkeypress="return decimalOnly(this, event,3)" onkeyup="isValidDecimal(this)"/>&nbsp;
										<%}%>
											<bean:message key="inPa" />
										
										</div>
									</td>
									--><td width="25%" class="ColumnBackground"> <div align="left">&nbsp;
										<font color="#FF0000" size="2">*</font>&nbsp;<bean:message key="interestRate" /></div>
									</td>
									<td class="TableData"  >
										<div align="left"> 
										<%				
										if((appCommonFlag.equals("11"))||(appCommonFlag.equals("13")))
										

										{
										%>
										<bean:write name="appForm" property="interestRate"/>
										<%}
										else
										{ %>
										
											<html:text property="interestRate" size="5" alt="interestRate" name="appForm"  maxlength="5" onkeypress="return decimalOnly(this, event,2)" onkeyup="isValidDecimal(this)"/>&nbsp;
										<%}%>
											<bean:message key="inPa" />
										
										</div>
									</td>
									<td width="29%" class="ColumnBackground" height="18"><span style="font-size: 9pt; font-weight: 700">
													<font color="#FF0000" size="2">*</font><bean:message key="repaymentMoratorium" /></span>
												</td>
												<td width="64%" class="TableData"   height="18"  >
												
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
								<!-- <tr align="left"> 
									<td class="ColumnBackground" colspan="12" height="24" width="843">
										<bean:message key="repaymentSchedule" />
									</td>
								</tr> -->
								<tr align="left"> 
									<td class="ColumnBackground" colspan="12" height="24" width="843">
										<table border="0" cellpadding="0" cellspacing="0"  width="100%" height=" ">
											<tr> 
												
											</tr>
											<%-- <tr>
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
														<IMG src="images/CalendarIcon.gif" width="20" onclick="showCalendar('appForm.firstInstallmentDueDate')" align="center">
														<%}%>
														</span>
													
												</td>
											</tr> --%>
											<%-- <tr>
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
                                                                                                                <html:option value="4">Annually</html:option>
                                                                                                                <html:option value="5">Weekly</html:option>
													</html:select>

													<%}
													else
													{ %>
													<html:select property="periodicity" name="appForm">
														<html:option value="">Select</html:option>
														<html:option value="1">Monthly</html:option>
														<html:option value="2">Quarterly</html:option>
														<html:option value="3">Half-Yearly</html:option>
                                                                                                                <html:option value="4">Annually</html:option>
                                                                                                                <html:option value="5">Weekly</html:option>
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
											</tr> --%>
 
								
								
							</table>			<!--end of WC-->
							</td>
							</tr>
							</tr>
							</table>
							
						