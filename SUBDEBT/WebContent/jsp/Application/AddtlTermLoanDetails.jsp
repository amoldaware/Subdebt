
<!--start for app details--><TABLE width="100%" border="0" cellspacing="1" cellpadding="0">
								<TR>
									<TD colspan="4"> 
										<TABLE width="100%" border="0" cellpadding="0" cellspacing="0">
											<TR>
												<TD width="31%" class="Heading"><bean:message key="applicationHeader" /></TD>
												<TD><IMG src="images/TriangleSubhead.gif" width="19" height="19"></TD>
											</TR>
											<TR>
												<TD colspan="4" class="Heading"><IMG src="images/Clear.gif" width="5" height="5"></TD>
											</TR>
										</TABLE>
									</TD>
								</TR>
								<TR align="left">
									<TD align="left" valign="top" class="ColumnBackground"><bean:message key="bankRefNo"/></td>								
									<TD align="left" valign="top" class="ColumnBackground"><html:text property="bankRefNo" size="20" alt="Bank Reference No" name="appForm"/>
									</TD>
								 </TR>
								 <TR align="left">
									<TD align="left" valign="top" class="ColumnBackground"><bean:message key="branchName"/></td>								
									<TD align="left" valign="top" class="ColumnBackground"><html:text property="branchName" size="20" alt="branch Name" name="appForm"/>
									</TD>
								 </TR>
								<TR align="left">
									<TD align="left" valign="top" class="ColumnBackground"><bean:message key="branchCode"/></td>								
									<TD align="left" valign="top" class="ColumnBackground"><html:text property="branchCode" size="20" alt="Branch Code" name="appForm"/>
									</TD>
								 </TR>								
							</TABLE>		<!--closing for app details-->
							

	<!--table starting --><TABLE width="100%" border="0" cellspacing="1" cellpadding="1">
								<TR>
									<TD colspan="7">
										<TABLE width="100%" border="0" cellpadding="0" cellspacing="0">
											<TR>
												<TD width="31%" class="Heading"><bean:message key="borrowerHeader" /></TD>
												<TD><IMG src="images/TriangleSubhead.gif" width="19" height="19"></TD>
											</TR>
											<TR>
												<TD colspan="6" class="Heading"><IMG src="images/Clear.gif" width="5" height="5"></TD>
											</TR>
										</TABLE>
									</TD>
								</TR>
								<TR align="left">
									<TD align="left" valign="top" class="ColumnBackground" colspan="3">
										<bean:message key="coveredByCGTSI" />
									</TD>
									<TD align="left" class="TableData" colspan="4"> 
										<html:radio name="appForm" value="Yes" property="setCover" ><bean:message key="yes" /></html:radio>&nbsp;&nbsp;<html:radio name="appForm" value="No" property="setCover"><bean:message key="no"/></html:radio>
									</TD>								
								</TR>
								<TR align="left">
									<TD align="left" valign="top" class="ColumnBackground" colspan="3">
										<bean:message key="bankAssistance" />
									</TD>
									<TD align="left" class="TableData" colspan="4"> 
										<html:radio name="appForm" value="Yes" property="setAssistance" ><bean:message key="yes" /></html:radio>&nbsp;&nbsp;<html:radio name="appForm" value="No" property="setAssistance"><bean:message key="no"/></html:radio>
									</TD>								
								</TR>
								<TR align="left">
									<TD align="left" valign="top" class="ColumnBackground" colspan="3"><bean:message key="outAmt"/></td>								
									<TD align="left" valign="top" class="TableData" colspan="4"><html:text property="outAmt" size="20" alt="Outstanding Amount" name="appForm"/>
									<bean:message key="inRs"/>
									</TD>
								 </TR>
								 <TR align="left">
									<TD align="left" valign="top" class="ColumnBackground" colspan="3"><bean:message key="uniqueAcctNo"/></td>								
									<TD align="left" valign="top" class="TableData" colspan="4"><html:text property="uniqueAcctNo" size="20" alt="uniqueAcctNo" name="appForm"/>	
									</TD>
								 </TR>
								 <TR align="left">
									<TD align="left" valign="top" class="ColumnBackground" colspan="3">
										<bean:message key="npa" />
									</TD>
									<TD align="left" class="TableData" colspan="4"> 
										<html:radio name="appForm" value="Yes" property="setNpa" ><bean:message key="yes" /></html:radio>&nbsp;&nbsp;<html:radio name="appForm" value="No" property="setNpa"><bean:message key="no"/></html:radio>
									</TD>								
								</TR>

								<TR>
									<TD colspan="7">
										<TABLE width="100%" border="0" cellpadding="1" cellspacing="1">
											 <TR>
												<TD class="SubHeading"><bean:message key="unitHeader" /></TD>						
											</TR>
											
											 <TR align="left">
												<TD align="left" valign="top" class="ColumnBackground" width="30%"><html:radio name="appForm" value="Yes" property="setValue" ><bean:message key="none" /></html:radio>
												</TD>
												<TD align="left" valign="top" class="ColumnBackground" width="30%"><html:radio name="appForm" value="Yes" property="setValue" ><bean:message key="cgbid" /></html:radio>
												</TD>
												<TD align="left" valign="top" class="ColumnBackground" width="25%"><html:radio name="appForm" value="Yes" property="setValue" ><bean:message key="cgpan" /></html:radio>
												</TD>
												<TD align="left" valign="top" class="ColumnBackground" width="15%">
												<html:text property="value" size="20" alt="Value" name="appForm"/>
												</TD>												
											</TR>

											 <TR align="left">
												<TD align="left" valign="top" class="ColumnBackground" width="30%"><bean:message key="constitution"/>
												</TD>
												<TD align="left" valign="top" class="TableData" colspan="5">
													<html:radio name="appForm" value="Yes" property="setConst" ><bean:message key="proprietary" /></html:radio>
													<html:radio name="appForm" value="Yes" property="setConst" ><bean:message key="partnership" /></html:radio>
													<html:radio name="appForm" value="Yes" property="setConst" ><bean:message key="public" /></html:radio>
													<html:radio name="appForm" value="Yes" property="setConst" ><bean:message key="private" /></html:radio>
													<html:radio name="appForm" value="Yes" property="setConst" ><bean:message key="others" /></html:radio>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
													<html:text property="constValue" size="20" alt="Value" name="appForm"/>
												</TD>
											 </TR>
											
											 <TR align="left">
												<TD align="left" valign="top" class="ColumnBackground" width="30%"><bean:message key="unitName"/>
												</TD>												
												<TD align="left" valign="top" class="TableData">
													<html:select property="unitName" name="appForm">
														<html:option value="">Select</html:option>
														<html:option value="M/s">M/s</html:option>
														<html:option value="Shri">Shri</html:option>
														<html:option value="Smt">Smt</html:option>
														<html:option value="Ku">Ku</html:option>
													</html:select>	
													<html:text property="unitNameValue" size="20" alt="unitName" name="appForm"/>	
												 </td>
												<TD align="left" valign="top" class="ColumnBackground" width="25%"><bean:message key="ssiRegNo"/>
												</td>
												<TD align="left" valign="top" class="TableData" width="15%"><html:text property="ssiRegNo" size="20" alt="SSI Reg No" name="appForm"/>						</td>
											</tr>
						
											 <TR align="left">
												<TD align="left" valign="top" class="ColumnBackground" width="30%"><bean:message key="commencementDate"/>
												</TD>												
												<TD align="left" valign="top" class="TableData">
													<html:text property="commencementDate" size="20" alt="Commencement Date" name="appForm"/><IMG src="images/CalendarIcon.gif" width="20" onClick="showCalendar('appForm.sfCalDate')" align="center">
												 </td>
												<TD align="left" valign="top" class="ColumnBackground" width="25%"><bean:message key="firmItpan"/>
												</td>
												<TD align="left" valign="top" class="TableData" width="15%"><html:text property="firmItpan" size="20" alt="ITPAN" name="appForm"/>	
												</td>
											</tr>

											 <TR align="left">
												<TD align="left" valign="top" class="ColumnBackground" width="30%"><bean:message key="industryNature"/>
												</TD>												
												<TD align="left" valign="top" class="TableData">
													<html:select property="industryNature" name="appForm">
														<html:option value="">Select</html:option>
														<html:option value="Manufacturing">Manufacturing</html:option>	
														<html:option value="Service">Service</html:option>
													</html:select>									
												 </td>
												<TD align="left" valign="top" class="ColumnBackground" width="25%"><bean:message key="industrySector"/>
												</td>
												<TD align="left" valign="top" class="TableData">
													<html:select property="industrySector" name="appForm">
														<html:option value="">Select</html:option>
														<html:option value="Depends">Depends</html:option>
														<html:option value="Others">Others</html:option>
													 </html:select>									
												 </td>
											</tr>

											 <TR align="left">
												<TD align="left" valign="top" class="ColumnBackground" width="30%"><bean:message key="activitytype"/>
												</TD>												
												<TD align="left" valign="top" class="TableData">
													<html:text property="activitytype" size="20" alt="Activity Type" name="appForm"/>				
												 </td>
												<TD align="left" valign="top" class="ColumnBackground" width="25%"><bean:message key="noOfEmployees"/>
												</td>
												<TD align="left" valign="top" class="TableData" width="15%"><html:text property="noOfEmployees" size="20" alt="No Of employees" name="appForm"/>					</td>
											</tr>

											 <TR align="left">
												<TD align="left" valign="top" class="ColumnBackground" width="30%"><bean:message key="turnover"/>
												</TD>												
												<TD align="left" valign="top" class="TableData">
													<html:text property="turnover" size="20" alt="turnover" name="appForm"/><bean:message key="inRs"/>
												 </td>
												<TD align="left" valign="top" class="ColumnBackground" width="25%"><bean:message key="exports"/>
												</td>
												<TD align="left" valign="top" class="TableData" width="15%">
													<html:text property="exports" size="10" alt="exports" name="appForm"/><bean:message key="inRs"/>
												</td>
											</tr>

											 <TR align="left">											
												<TD align="left" valign="top" class="ColumnBackground" width="30%"><bean:message key="address"/>
												</td>
												<TD align="left" valign="top" class="TableData" width="30%">
													<html:textarea property="address" cols="20" alt="address" name="appForm"/>						
												</td>
												<TD align="left" valign="top" class="ColumnBackground" width="25%"><bean:message key="state"/>
												</TD>												
												<TD align="left" valign="top" class="TableData" width="15%">
													<html:select property="state" name="appForm">
														<html:option value="">Select</html:option>
														<html:option value="TamilNadu">TamilNadu</html:option>
														<html:option value="Kerala">Kerala</html:option>
														<html:option value="U.P.">U.P.</html:option>
														<html:option value="M.P.">M.P.</html:option>
													</html:select>										
												 </td>
											</tr>

												
											 <TR align="left">
												<TD align="left" valign="top" class="ColumnBackground" width="30%"><bean:message key="district"/>
												</TD>												
												<TD align="left" valign="top" class="TableData">
													<html:select property="district" name="appForm">
														<html:option value="">Select</html:option>
														<html:option value="Pune">Pune</html:option>
														<html:option value="Sangli">Sangli</html:option>
														<html:option value="Mumbai">Mumbai</html:option>
														<html:option value="Kolhapur">Kolhapur</html:option>
													</html:select><html:text property="districtOthers" size="5" alt="district" name="appForm"/><bean:message key="others"/>		
												 </td>
												<TD align="left" valign="top" class="ColumnBackground" width="25%"><bean:message key="city"/>
												</td>
												<TD align="left" valign="top" class="TableData" width="15%">
													<html:text property="city" size="20" alt="city" name="appForm"/>		
												</td>
											</tr>

											 <TR align="left">
												<TD align="left" valign="top" class="ColumnBackground" width="30%"><bean:message key="pinCode"/>
												</td>
												<TD align="left" valign="top" class="TableData" width="70%" colspan="5">
													<html:text property="pinCode" size="20" alt="pinCode" name="appForm"/>		
												</td>		
											</TR>
										</TABLE>
									</TD>
								</TR>								
								<TR>
									<TD class="SubHeading"><bean:message key="promoterHeader" />
									</TD>						
								</TR>

								<TR align="left">
									<TD align="left" valign="top" class="ColumnBackground" colspan="6">
										<bean:message key="chiefInfo"/>
									</TD>
								</TR>

							  <TR align="left">
								  <TD align="left" valign="top" class="ColumnBackground" colspan="2" width="15%">
								  </TD>												 
								  <TD align="left" valign="top" class="ColumnBackground" width="10%">
										<bean:message key="title"/>
								   </TD>
								   <TD align="left" valign="top" class="ColumnBackground" width="25%">
										<bean:message key="firstName"/>
								   </TD>
									<TD align="left" valign="top" class="ColumnBackground" width="25%">
										<bean:message key="middleName"/>	
								   </TD>
									<TD align="left" valign="top" class="ColumnBackground" width="25%">
										<bean:message key="lastName"/>
								   </TD>
								</TR>

								<TR align="left">
								  <TD align="left" valign="top" class="ColumnBackground" colspan="2" width="15%"><bean:message key="name"/>
								  </TD>		
								  <TD align="left" valign="top" class="TableData">
									<html:select property="title" name="appForm">
										<html:option value="">Select</html:option>
										<html:option value="Shri">Shri</html:option>
										<html:option value="Smt">Smt</html:option>
										<html:option value="Ku">Ku</html:option>
									</html:select>
								  </TD>
								  <TD align="left" valign="top" class="TableData">
									<html:text property="firstName" size="20" alt="firstName" name="appForm"/>	
								  </TD>
								   <TD align="left" valign="top" class="TableData">
									<html:text property="middleName" size="20" alt="middleName" name="appForm"/>	
								  </TD>
								   <TD align="left" valign="top" class="TableData">
									<html:text property="lastName" size="20" alt="lastName" name="appForm"/>	
								  </TD>
								</TR>

								<TR align="left">
									<TD align="left" valign="top" class="ColumnBackground" colspan="2"><bean:message key="gender"/>
									</TD>
									<TD align="left" valign="top" class="TableData" width="20%">
										<html:radio name="appForm" value="Male" property="setGender" ><bean:message key="male" /></html:radio>&nbsp;&nbsp;&nbsp;
										<html:radio name="appForm" value="female" property="setGender" ><bean:message key="female" /></html:radio>
									</TD>
									<TD align="left" valign="top" class="ColumnBackground">
										<bean:message key="chiefItpan" />
									</TD>
									<TD align="left" valign="top" class="TableData" colspan="2">
										<html:text property="chiefItpan" size="20" alt="chiefItpan" name="appForm"/>
									</TD>													
								</TR>

								<TR align="left">
									<TD align="left" valign="top" class="ColumnBackground" colspan="2">
										<bean:message key="dob" />					
									</TD>
									<TD align="left" valign="top" class="TableData" colspan="5">
										<html:text property="dob" size="20" alt="dob" name="appForm"/>
										<IMG src="images/CalendarIcon.gif" width="20" onClick="showCalendar('appForm.dob')" align="center">
									</TD>
								</TR>

								<TR align="left">
									<TD align="left" valign="top" class="ColumnBackground" colspan="2">
										<bean:message key="legalid" />					
									</TD>
									<TD align="left" valign="top" class="TableData" colspan="5">
										<html:select property="legalid" name="appForm">
											<html:option value="">Select</html:option>
											<html:option value="PassportNumber">PassportNumber</html:option>
											<html:option value="ElectionCardnumber">ElectionCardnumber</html:option>
											<html:option value="Driving License">Driving License</html:option>
											<html:option value="Others">Others</html:option>
										</html:select>
										<html:text property="otherId" size="20" alt="otherId" name="appForm"/><bean:message key="otherId" />	
										<html:text property="value" size="20" alt="value" name="appForm"/><bean:message key="value" />	
									</TD>
								</TR>

								<TR align="left">
									<TD align="left" valign="top" class="ColumnBackground" colspan="7"><bean:message key="otherPromoters" />	
									</TD>
								</TR>
								 <tr> 
									 <td class="ColumnBackground"colspan="7">
										 <table border="0" cellpadding="1" cellspacing="0" width="100%">
											<tr>
												<td class="ColumnBackground" width="17%"><b>
													<span style="font-size: 9pt">1. <bean:message key="promoterName" />	</span></b>
												</td>
												<td width="15%" class="TableData">
													<b><span style="font-size: 9pt"> 
													<html:text property="name1" size="20" alt="name1" name="appForm"/></span></b>
												</td>
												<td width="17%" class="ColumnBackground" >
													<b><span style="font-size: 9pt"> <bean:message key="promoterItpan" /></span></b>
												</td>
												<td width="17%" class="TableData">
													<b><span style="font-size: 9pt"> 
													<html:text property="itpan1" size="20" alt="itpan1" name="appForm"/></span></b>
												</td>
												<td width="17%" class="ColumnBackground" >
													<b><span style="font-size: 9pt"><bean:message key="promoterDob" /></span></b>
												</td>
												<td width="17%" class="TableData">
													<b><span style="font-size: 9pt"> 
													 <html:text property="dob1" size="10" alt="dob1" name="appForm"/>
													<IMG src="images/CalendarIcon.gif" width="20" onClick="showCalendar('appForm.dob1')" align="center"></span></b>
												</td>
											</tr>
											 <tr>
												<td class="ColumnBackground" width="17%"><b>
													<span style="font-size: 9pt">1. <bean:message key="promoterName" />	</span></b>
												</td>
												<td width="15%" class="TableData">
													<b><span style="font-size: 9pt"> 
													<html:text property="name2" size="20" alt="name2" name="appForm"/></span></b>
												</td>
												<td width="17%" class="ColumnBackground" >
													<b><span style="font-size: 9pt"> <bean:message key="promoterItpan" /></span></b>
												</td>
												<td width="17%" class="TableData">
													<b><span style="font-size: 9pt"> 
													<html:text property="itpan2" size="20" alt="itpan2" name="appForm"/></span></b>
												</td>
												<td width="17%" class="ColumnBackground" >
													<b><span style="font-size: 9pt"><bean:message key="promoterDob" /></span></b>
												</td>
												<td width="17%" class="TableData">
													<b><span style="font-size: 9pt"> 
													 <html:text property="dob2" size="10" alt="dob2" name="appForm"/>
													<IMG src="images/CalendarIcon.gif" width="20" onClick="showCalendar('appForm.dob2')" align="center"></span></b>
												</td>
											</tr>

											 <tr>
												<td class="ColumnBackground" width="17%"><b>
													<span style="font-size: 9pt">1. <bean:message key="promoterName" />	</span></b>
												</td>
												<td width="15%" class="TableData">
													<b><span style="font-size: 9pt"> 
													<html:text property="name3" size="20" alt="name3" name="appForm"/></span></b>
												</td>
												<td width="17%" class="ColumnBackground" >
													<b><span style="font-size: 9pt"> <bean:message key="promoterItpan" /></span></b>
												</td>
												<td width="17%" class="TableData">
													<b><span style="font-size: 9pt"> 
													<html:text property="itpan3" size="20" alt="itpan3" name="appForm"/></span></b>
												</td>
												<td width="17%" class="ColumnBackground" >
													<b><span style="font-size: 9pt"><bean:message key="promoterDob" /></span></b>
												</td>
												<td width="17%" class="TableData">
													<b><span style="font-size: 9pt"> 
													 <html:text property="dob3" size="10" alt="dob3" name="appForm"/>
													<IMG src="images/CalendarIcon.gif" width="20" onClick="showCalendar('appForm.dob3')" align="center"></span></b>
												</td>
											</tr>
										 </table>
									</td>
								 </tr>
								<tr align="left"> 
									<td colspan="6">
										<img src="../images/clear.gif" width="5" height="15">
									</td>
								</tr>
							</TABLE>			<!--table closing for borrower details-->

							<table width="100%" border="0" cellspacing="1" cellpadding="0">
								<tr align="left">
									<td class="TableData"><font color="red">
										<bean:message key="addtlTermLoanHint"/></font>
									</td>
								</tr>
							</table>

<!--start for project-->	<table width="100%" cellspacing="1" cellpadding="0">
								<tr> 
									<td colspan="8">
										<table width="100%" border="0" cellspacing="0" cellpadding="0">
											 <tr> 
												<TD width="31%" class="Heading"><bean:message key="projectHeader" /></TD>
												<TD><IMG src="images/TriangleSubhead.gif" width="19" height="19"></TD>                 
												 <td>&nbsp;</td>
												 <td>&nbsp;</td>
											 </tr>											
											 <TR>
												<TD colspan="4" class="Heading"><IMG src="images/Clear.gif" width="5" height="5"></TD>
											</TR>                   
										</table>
									</td>
								</tr>
								<tr align="left"> 
									 <td class="ColumnBackground">
										<bean:message key="collateralSec" />
									 </td>
									 <td align="left" valign="middle" class="TableData"> <div align="left"> 
										<html:radio name="appForm" value="yes" property="setCollateralSec" ><bean:message key="y" /></html:radio>
										<html:radio name="appForm" value="no" property="setCollateralSec" ><bean:message key="n" /></html:radio>
									</td>
									<td class="ColumnBackground"> &nbsp
										<bean:message key="thirdPartyTaken" />
									</td>
									<td align="left" valign="middle" class="TableData"> 
										 <html:radio name="appForm" value="yes" property="setThirdParty" ><bean:message key="y" /></html:radio>
										<html:radio name="appForm" value="no" property="setThirdParty" ><bean:message key="n" /></html:radio>
									</td>
								</tr>
								<tr align="left"> 
									<td colspan="4" align="left" class="ColumnBackground"><bean:message key="guarantors" /></td>
								</tr>

								<tr align="left"> 
									<TD align="left" valign="top" class="ColumnBackground" colspan="4">
										<table border="0" cellpadding="1" cellspacing="0" width="100%">
											<tr>
												<TD align="left" valign="top" class="ColumnBackground" width="17%">
													<b><span style="font-size: 9pt">1. <bean:message key="guarantorsName" /></span></b>
												</td>
												<td width="17%" class="TableData">
													<b><span style="font-size: 9pt"> 
													<html:text property="gName1" size="20" alt="gName1" name="appForm"/></span></b>
												</td>
												<td width="17%" class="ColumnBackground" >
													<b><span style="font-size: 9pt"><bean:message key="guarantorsNetWorth" /></span></b>
												</td>
												<td width="17%" class="TableData">
													<b><span style="font-size: 9pt"> 
													<html:text property="gWorth1" size="20" alt="gName1" name="appForm"/></span></b>
												</td>
											</tr>
										</table>
									</td>
								</tr>
								<tr align="left"> 
									<TD align="left" valign="top" class="ColumnBackground" colspan="4">
										<table border="0" cellpadding="1" cellspacing="0" width="100%">
											<tr>
												<TD align="left" valign="top" class="ColumnBackground" width="17%">
													<b><span style="font-size: 9pt">2. <bean:message key="guarantorsName" /></span></b>
												</td>
												<td width="17%" class="TableData">
													<b><span style="font-size: 9pt"> 
													<html:text property="gName2" size="20" alt="gName2" name="appForm"/></span></b>
												</td>
												<td width="17%" class="ColumnBackground" >
													<b><span style="font-size: 9pt"><bean:message key="guarantorsNetWorth" /></span></b>
												</td>
												<td width="17%" class="TableData">
													<b><span style="font-size: 9pt"> 
													<html:text property="gWorth2" size="20" alt="gName2" name="appForm"/></span></b>
												</td>
											</tr>
										</table>
									</td>
								</tr>
								<tr align="left"> 
									<TD align="left" valign="top" class="ColumnBackground" colspan="4">
										<table border="0" cellpadding="1" cellspacing="0" width="100%">
											<tr>
												<TD align="left" valign="top" class="ColumnBackground" width="17%">
													<b><span style="font-size: 9pt">3. <bean:message key="guarantorsName" /></span></b>
												</td>
												<td width="17%" class="TableData">
													<b><span style="font-size: 9pt"> 
													<html:text property="gName3" size="20" alt="gName3" name="appForm"/></span></b>
												</td>
												<td width="17%" class="ColumnBackground" >
													<b><span style="font-size: 9pt"><bean:message key="guarantorsNetWorth" /></span></b>
												</td>
												<td width="17%" class="TableData">
													<b><span style="font-size: 9pt"> 
													<html:text property="gWorth3" size="20" alt="gName3" name="appForm"/></span></b>
												</td>
											</tr>
										</table>
									</td>
								</tr>
								<tr align="left"> 
									<TD align="left" valign="top" class="ColumnBackground" colspan="4">
										<table border="0" cellpadding="1" cellspacing="0" width="100%">
											<tr>
												<TD align="left" valign="top" class="ColumnBackground" width="17%">
													<b><span style="font-size: 9pt">4. <bean:message key="guarantorsName" /></span></b>
												</td>
												<td width="17%" class="TableData">
													<b><span style="font-size: 9pt"> 
													<html:text property="gName4" size="20" alt="gName4" name="appForm"/></span></b>
												</td>
												<td width="17%" class="ColumnBackground" >
													<b><span style="font-size: 9pt"><bean:message key="guarantorsNetWorth" /></span></b>
												</td>
												<td width="17%" class="TableData">
													<b><span style="font-size: 9pt"> 
													<html:text property="gWorth4" size="20" alt="gName4" name="appForm"/></span></b>
												</td>
											</tr>
										</table>
									</td>
								</tr>
								<tr> 
									<TD align="left" valign="top" class="ColumnBackground">
										<bean:message key="subsidyEquityName" />
									</td>
									<TD align="left" valign="top" class="TableData" colspan="3">
										<html:select property="subsidyEquityName" name="appForm">
											<html:option value="">Select</html:option>
											<html:option value="PMRY">PMRY</html:option>
											<html:option value="SJRY">SJRY</html:option>
											<html:option value="Others">Others</html:option>
										</html:select>
										<html:text property="subsidyEquityValue" size="20" alt="subsidyEquityName" name="appForm"/>
									</td>
								</tr>
								<tr align="left"> 
									<TD class="SubHeading">
										<bean:message key="primarySecurity" />
									</TD>
								</tr>
								<tr align="left">
									<TD align="left" valign="top" class="ColumnBackground" >
										<bean:message key="securityName"/>
									</td>
									<TD align="center" valign="top" colspan="2" class="ColumnBackground" >
										<bean:message key="particulars"/>
									</td>
									<TD align="center" valign="top" class="ColumnBackground" > 
										<bean:message key="valueInRs"/>
									</td>
								</tr>								
								<tr align="left">
									<TD align="left" valign="top" class="TableData" >
										<bean:message key="land"/>
									</td>
									<TD align="left" valign="top" colspan="2" class="TableData" > 
										<html:text property="landPart" size="20" alt="landPart" name="appForm"/>
									</td>
									<TD align="left" valign="top" class="TableData" >
										<html:text property="landValue" size="20" alt="landValue" name="appForm"/>
									</td>
								</tr>
								<tr align="left">
									<TD align="left" valign="top" class="TableData" >
										<bean:message key="bldg"/>
									</td>
									<TD align="left" valign="top" colspan="2" class="TableData" > 
										<html:text property="bldgPart" size="20" alt="bldgPart" name="appForm"/>
									</td>
									<TD align="left" valign="top" class="TableData" >
										<html:text property="bldgValue" size="20" alt="landValue" name="appForm"/>
									</td>
								</tr>
								<tr align="left">
									<TD align="left" valign="top" class="TableData" >
										<bean:message key="machine"/>
									</td>
									<TD align="left" valign="top" colspan="2" class="TableData" > 
										<html:text property="machinePart" size="20" alt="machinePart" name="appForm"/>
									</td>
									<TD align="left" valign="top" class="TableData" >
										<html:text property="machineValue" size="20" alt="machineValue" name="appForm"/>
									</td>
								</tr>
								<tr align="left">
									<TD align="left" valign="top" class="TableData" >
										<bean:message key="assets"/>
									</td>
									<TD align="left" valign="top" colspan="2" class="TableData" > 
										<html:text property="assetsPart" size="20" alt="assetsPart" name="appForm"/>
									</td>
									<TD align="left" valign="top" class="TableData" >
										<html:text property="assetsValue" size="20" alt="assetsValue" name="appForm"/>
									</td>
								</tr>
								<tr align="left">
									<TD align="left" valign="top" class="TableData" >
										<bean:message key="currentAssets"/>
									</td>
									<TD align="left" valign="top" colspan="2" class="TableData" > 
										<html:text property="currentAssetsPart" size="20" alt="currentAssetsPart" name="appForm"/>
									</td>
									<TD align="left" valign="top" class="TableData" >
										<html:text property="currentAssetsValue" size="20" alt="currentAssetsValue" name="appForm"/>
									</td>
								</tr>
								<tr align="left">
									<TD align="left" valign="top" class="TableData" >
										<bean:message key="psOthers"/>
									</td>
									<TD align="left" valign="top" colspan="2" class="TableData" > 
										<html:text property="othersPart" size="20" alt="othersPart" name="appForm"/>
									</td>
									<TD align="left" valign="top" class="TableData" >
										<html:text property="othersValue" size="20" alt="othersValue" name="appForm"/>
									</td>
								</tr>
								<tr align="left">
									<td colspan="4">&nbsp;</td>
								</tr>	
									

								<tr valign="top"> 
									<td colspan="4">
										<table width="100%" border="0" align="right" cellpadding="0" cellspacing="1">
											<tr> 
												<td class="ColumnBackground" width="25%" align="left" valign="top" >
													<bean:message key="tcSanctioned" />
												</td>
												<td class="TableData" width="20%" align="left" valign="top">
													<html:text property="tcSanctioned" size="20" alt="tcSanctioned" name="appForm"/>
													<bean:message key="inRs" />
												</td>
												<td class="ColumnBackground" colspan="2" align="left" valign="top" >
													<table width="100%" border="0" align="right" cellpadding="0" cellspacing="1">
														<tr align="left">
															<td class="ColumnBackground" colspan="2" align="center">
																<bean:message key="wcLimitSanctioned"/>
															</td>
														</tr>							
														<tr align="left">
															<td class="ColumnBackground" align="left" valign="top" >
																<bean:message key="wcFundBased"/>
															</td>
															<td class="TableData" align="left" valign="top" >&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
																<html:text property="wcFundBased" size="20" alt="wcFundBased" name="appForm"/>
																<bean:message key="inRs"/>
															</td>						
														</tr>
														<tr align="left">
															<td class="ColumnBackground" align="left" valign="top" >
																<bean:message key="wcNonFundBased"/>
															</td>
															<td class="TableData" align="left" valign="top" >&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
																<html:text property="wcNonFundBased" size="20" alt="wcNonFundBased" name="appForm"/>
																<bean:message key="inRs"/>
															</td>						
														</tr>										
													</table>
												</td>						 
											</tr>
											<tr align="left"> 
												<td class="ColumnBackground" width="25%">&nbsp;
													<bean:message key="tcPromoterContribution"/>
												</td>
												<td class="TableData" width="20%" align="left" valign="top">
													<html:text property="tcPromoterContribution" size="20" alt="promotersCont" name="appForm"/>
												  <bean:message key="inRs"/>
												</td>
												<td class="ColumnBackground" width="25%">&nbsp;
													<bean:message key="wcPromoterContribution"/>
												</td>
												<td class="TableData" width="20%" align="left" valign="top">
													<html:text property="wcPromoterContribution" size="20" alt="wcPromoterContribution" name="appForm"/>
												  <bean:message key="inRs"/>
												</td>
											</tr>
											<tr align="left"> 
												<td class="ColumnBackground" width="25%">&nbsp;
													<bean:message key="tcSubsidyOrEquity"/>
												</td>
												<td class="TableData" width="20%" align="left" valign="top">
													<html:text property="tcSubsidyOrEquity" size="20" alt="tcSubsidyOrEquity" name="appForm"/>
												  <bean:message key="inRs"/>
												</td>
												<td class="ColumnBackground" width="25%">&nbsp;
													<bean:message key="wcSubsidyOrEquity"/>
												</td>
												<td class="TableData" width="20%" align="left" valign="top">
													<html:text property="wcSubsidyOrEquity" size="20" alt="wcSubsidyOrEquity" name="appForm"/>
												  <bean:message key="inRs"/>
												</td>
											</tr>
											<tr align="left"> 
												<td class="ColumnBackground" width="25%">&nbsp;
													<bean:message key="tcOthers"/>
												</td>
												<td class="TableData" width="20%" align="left" valign="top">
													<html:text property="tcOthers" size="20" alt="tcOthers" name="appForm"/>
												  <bean:message key="inRs"/>
												</td>
												<td class="ColumnBackground" width="25%">&nbsp;
													<bean:message key="wcOthers"/>
												</td>
												<td class="TableData" width="20%" align="left" valign="top">
													<html:text property="wcOthers" size="20" alt="wcOthers" name="appForm"/>
												  <bean:message key="inRs"/>
												</td>
											</tr>
											<TR align="left">
												<td class="ColumnBackground" width="25%">&nbsp; 
													<bean:message key="projectCost"/><br> <font color="#FF0000" size="1">&nbsp;
													<bean:message key="projectCostHint"/>
													</font>						
												</td>
												<td class="TableData" width="20%" align="left" valign="top">
													<html:text property="projectCost" size="20" alt="projectCost" name="appForm"/>
													<bean:message key="inRs"/>
												</td>
												<td class="ColumnBackground" width="25%">&nbsp;
													<bean:message key="wcAssessed"/>
												</td>
												<td class="TableData" width="20%" align="left" valign="top">
													<html:text property="wcAssessed" size="20" alt="wcAssessed" name="appForm"/>
													<bean:message key="inRs"/>
												</td>
											</tr>
											<tr align="left"> 
												<td class="ColumnBackground" colspan="4" style="text-align: center">
													<bean:message key="projectOutlay"/><br> <font color="#FF0000" size="1">&nbsp
													<bean:message key="projectOutlayHint"/></font>		<html:text property="projectOutlay" size="20" alt="projectOutlay" name="appForm"/>
													<bean:message key="inRs"/>
												</td>
											</tr>											
										</table>					
									</TD>
								</TR>
							</table>
							










