<%@ page import="com.cgtsi.util.SessionConstants"%>	
<script>
function calltotalAmt()
{
var sanctionedamt=document.getElementById('termCreditSanctioned');
var fundbasedamt=document.getElementById('wcFundBasedSanctioned');
var nonfundbasedamt=document.getElementById('wcNonFundBasedSanctioned');

if (!(isNaN(sanctionedamt.value)) && (sanctionedamt.value != ""))
	{
sanctionedamtValue=parseFloat(sanctionedamt.value);
	}
	else
	{
	sanctionedamtValue=0;
	}

if (!(isNaN(fundbasedamt.value)) && (fundbasedamt.value != ""))
	{
fundbasedamtValue=parseFloat(fundbasedamt.value);
	}
	else
	{
	fundbasedamtValue=0;
	}
if (!(isNaN(nonfundbasedamt.value)) && (nonfundbasedamt.value != ""))
	{
nonfundbasedamtValue=parseFloat(nonfundbasedamt.value);
	}
else
	{
	nonfundbasedamtValue=0;
	}
	//alert("sukant");
var amtVal=sanctionedamtValue+fundbasedamtValue+nonfundbasedamtValue;
//alert("sukant"+amtVal);
		if (amtVal>1000000)
		{
		document.forms[0].agree.disabled=false;
		document.forms[0].agree.checked=false;
		}
		else{
		document.forms[0].agree.disabled=true;
		document.forms[0].agree.checked=false;
		}	
}
</script>
<!--start for app details--><TABLE width="100%" border="0" cellspacing="1" cellpadding="0">
<tr>
			<TD  align="left" colspan="4"><font size="2"><bold>
				Fields marked as </font><font color="#FF0000" size="2">*</font><font size="2"> are mandatory</bold></font>
			</td>
		</tr>
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
									<TD align="left" valign="top" class="ColumnBackground"><font color="#FF0000" size="2">*</font>&nbsp;<bean:message key="bankRefNo"/></td>								
									<TD align="left" valign="top" class="tableData">
										<%	

											String appCommonFlag=(String)session.getAttribute(SessionConstants.APPLICATION_TYPE_FLAG);										
											if((appCommonFlag.equals("1"))||(appCommonFlag.equals("11"))||(appCommonFlag.equals("12"))||(appCommonFlag.equals("13")))
											{										
												
										%>
											<bean:write name="appForm" property="mliRefNo"/>

										<%	}										
											else
											{%>
												<html:text property="mliRefNo" size="25" alt="Bank Reference No" name="appForm" maxlength="10" />
											<%}%>

									</TD>
								 </TR>
								 <TR align="left">
									<TD align="left" valign="top" class="ColumnBackground"><font color="#FF0000" size="2">*</font>&nbsp;<bean:message key="branchName"/></td>								
									<TD align="left" valign="top" class="tableData">
									<%
									if((appCommonFlag.equals("1"))||(appCommonFlag.equals("11"))||(appCommonFlag.equals("12"))||(appCommonFlag.equals("13")))
									{
									%>
									<bean:write name="appForm" property="mliBranchName"/>
									<%	}
										else
										{ %><html:text property="mliBranchName" size="20" alt="branch Name" name="appForm" maxlength="100" />
										<%}%>
									</TD>
								 </TR>
								<TR align="left">
									<TD align="left" valign="top" class="ColumnBackground"><bean:message key="branchCode"/></td>								
									<TD align="left" valign="top" class="tableData">
									<%
										if((appCommonFlag.equals("1"))||(appCommonFlag.equals("11"))||(appCommonFlag.equals("12"))||(appCommonFlag.equals("13")))
										{
									%>
										<bean:write name="appForm" property="mliBranchCode"/>
									<%	}
										else
										{ %>
										<html:text property="mliBranchCode" size="20" alt="Branch Code" name="appForm" maxlength="10" />
										<%}%>
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
										<bean:message key="bankAssistance" />
									</TD>
									<TD align="left" class="TableData" colspan="4"> 
									<%
									if((appCommonFlag.equals("0"))||(appCommonFlag.equals("1"))||(appCommonFlag.equals("2")) ||(appCommonFlag.equals("3"))
									|| (appCommonFlag.equals("4")) || (appCommonFlag.equals("5")) || (appCommonFlag.equals("6")) || (appCommonFlag.equals("11"))||(appCommonFlag.equals("12"))||(appCommonFlag.equals("13"))  || (appCommonFlag.equals("14"))||(appCommonFlag.equals("17"))||(appCommonFlag.equals("18"))
									||(appCommonFlag.equals("19")))
									{
										%>
<!--						<bean:write property="assistedByBank" name="appForm"/>-->
											<html:radio name="appForm" value="Y" property="assistedByBank" disabled="true"></html:radio>
											
											<bean:message key="yes" />&nbsp;&nbsp;

											
												<html:radio name="appForm" value="N" property="assistedByBank" disabled="true"></html:radio>
											
											<bean:message key="no"/>

												
										<%}
											else {
											%>
											<html:radio name="appForm" value="Y" property="assistedByBank" onclick="javascript:enableAssistance()"></html:radio>
											
											<bean:message key="yes" />&nbsp;&nbsp;

											
												<html:radio name="appForm" value="N" property="assistedByBank" onclick="javascript:enableAssistance()"></html:radio>
											
											<bean:message key="no"/>
											
											<%}%>
									</TD>								
								</TR>
								<TR align="left">
									<TD align="left" valign="top" class="ColumnBackground" colspan="3"><bean:message key="osAmt"/></td>								
									<TD align="left" valign="top" class="TableData" colspan="4">
									<%
										if((appCommonFlag.equals("0"))||(appCommonFlag.equals("1"))||(appCommonFlag.equals("2")) ||(appCommonFlag.equals("3"))
											|| (appCommonFlag.equals("4")) || (appCommonFlag.equals("5")) || (appCommonFlag.equals("6")) || (appCommonFlag.equals("11"))||(appCommonFlag.equals("12"))||(appCommonFlag.equals("13")) || (appCommonFlag.equals("14"))||(appCommonFlag.equals("17"))||(appCommonFlag.equals("18"))
									||(appCommonFlag.equals("19")))

											{
										%>
											<bean:write name="appForm" property="osAmt"/>

										<%	}
											else
											{ %><html:text property="osAmt" size="20" alt="Outstanding Amount" name="appForm" maxlength="16" onkeypress="return decimalOnly(this, event,13)" onkeyup="isValidDecimal(this)"/>
											<%}%>
											<bean:message key="inRs"/>
									</TD>
								 </TR>
								 <TR align="left">
									<TD align="left" valign="top" class="ColumnBackground" colspan="3">
										<bean:message key="npa" />
									</TD>
									<TD align="left" class="TableData" colspan="4"> 
									<%
									if((appCommonFlag.equals("0"))||(appCommonFlag.equals("1"))||(appCommonFlag.equals("2")) ||(appCommonFlag.equals("3"))
									|| (appCommonFlag.equals("4")) || (appCommonFlag.equals("5")) || (appCommonFlag.equals("6")) || (appCommonFlag.equals("11"))||(appCommonFlag.equals("12"))||(appCommonFlag.equals("13")) || (appCommonFlag.equals("14"))||(appCommonFlag.equals("17"))||(appCommonFlag.equals("18"))
									||(appCommonFlag.equals("19")))
									{
										%>
						<bean:write property="npa" name="appForm"/>
																							
										<%}
											else {
											%>
									
											<html:radio name="appForm" value="Y" property="npa" ></html:radio>
											
											<bean:message key="yes" />&nbsp;&nbsp;
											<html:radio name="appForm" value="N" property="npa" ></html:radio>
												
											<bean:message key="no"/>
										<%}%>
									</TD>								
								</TR>
								<TR align="left">
									<TD align="left" valign="top" class="ColumnBackground" colspan="3">
										<bean:message key="coveredByCGTSI" />
									</TD>
									<TD align="left" class="TableData" colspan="4">

									 <% if((appCommonFlag.equals("0"))||(appCommonFlag.equals("1"))||(appCommonFlag.equals("2")) ||(appCommonFlag.equals("3"))
											|| (appCommonFlag.equals("4")) || (appCommonFlag.equals("5")) || (appCommonFlag.equals("6")) || (appCommonFlag.equals("11"))||(appCommonFlag.equals("12"))||(appCommonFlag.equals("13")) || (appCommonFlag.equals("14"))||(appCommonFlag.equals("17"))||(appCommonFlag.equals("18"))
									||(appCommonFlag.equals("19")))
									 {
									%>
									<html:radio name="appForm" value="Y" property="previouslyCovered" disabled="true"></html:radio>
											
											<bean:message key="yes" />&nbsp;&nbsp;
											
											<html:radio name="appForm" value="N" property="previouslyCovered"  disabled="true"></html:radio>
											
											<bean:message key="no"/>
									<%}
									else {
									%>
									
											<html:radio name="appForm" value="Y" property="previouslyCovered" onclick="enableNone()"></html:radio>
											
											<bean:message key="yes" />&nbsp;&nbsp;
											
											<html:radio name="appForm" value="N" property="previouslyCovered"  onclick="enableNone()"></html:radio>
											
											<bean:message key="no"/>
										<%}%>
										
									</TD>								
								</TR>

								<TR>
									<TD colspan="9">
										<TABLE width="100%" border="0" cellpadding="1" cellspacing="1">			
											 <TR align="left">
											 <%										
											 if((appCommonFlag.equals("0"))||(appCommonFlag.equals("1"))||(appCommonFlag.equals("2")) ||(appCommonFlag.equals("3"))
											|| (appCommonFlag.equals("4")) || (appCommonFlag.equals("5")) || (appCommonFlag.equals("6")) || (appCommonFlag.equals("11"))||(appCommonFlag.equals("12"))||(appCommonFlag.equals("13")) ||(appCommonFlag.equals("14"))||(appCommonFlag.equals("17"))||(appCommonFlag.equals("18"))
									||(appCommonFlag.equals("19")))
											{	
											%>	
											<script>
												booleanVal=true;
											</script>
											
												<TD align="left" valign="top" class="ColumnBackground" width="25%"><html:radio name="appForm" value="none" property="none" disabled="true"><bean:message key="none" /></html:radio>
												</TD>
												<TD align="left" valign="top" class="ColumnBackground" ><html:radio name="appForm" value="cgbid" property="none" disabled="true">
												<bean:message key="cgbid" /></html:radio>								
												</TD>
												<TD align="left" valign="top" class="ColumnBackground" width="25%"><html:radio name="appForm" value="cgpan" property="none" disabled="true">
												<bean:message key="cgpan" /></html:radio>
																								
												</TD>
											<%}else {%>

											<TD align="left" valign="top" class="ColumnBackground" width="25%"><html:radio name="appForm" value="none" property="none" disabled="false"><bean:message key="none" /></html:radio>
												</TD>
												<TD align="left" valign="top" class="ColumnBackground" ><html:radio name="appForm" value="cgbid" property="none" disabled="false">
												<bean:message key="cgbid" /></html:radio>								
												</TD>
												<TD align="left" valign="top" class="ColumnBackground" width="25%"><html:radio name="appForm" value="cgpan" property="none" disabled="false">
												<bean:message key="cgpan" /></html:radio>
												
											<%}%>
												
												<TD align="left" valign="top" class="ColumnBackground" width="15%" colspan="2">	
												

											 <%	

											 if((appCommonFlag.equals("0"))||(appCommonFlag.equals("1"))||(appCommonFlag.equals("2")) ||(appCommonFlag.equals("3"))
											|| (appCommonFlag.equals("4")) || (appCommonFlag.equals("5")) || (appCommonFlag.equals("6")) || (appCommonFlag.equals("11"))||(appCommonFlag.equals("12"))||(appCommonFlag.equals("13")))
											{													
												
											%>	
											<script>
												booleanVal=true;
											</script>

												<html:text property="unitValue" size="15" alt="Value" name="appForm"  maxlength="13" disabled="true"/>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
											<%}
											else if((appCommonFlag.equals("14"))||(appCommonFlag.equals("17"))||(appCommonFlag.equals("18"))||(appCommonFlag.equals("19"))){
											%>
											<script>
												booleanVal=true;
											</script>

												<html:text property="unitValue" size="15" alt="Value" name="appForm"  maxlength="13" disabled="false"/>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;

											<%}else {%>

											<script>
												booleanVal=false;
											</script>

												<html:text property="unitValue" size="15" alt="Value" name="appForm"  maxlength="13" disabled="false"/>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
											<%}%>
										
											<%
											if((appCommonFlag.equals("7"))||(appCommonFlag.equals("8"))||(appCommonFlag.equals("9")) || (appCommonFlag.equals("10")) || (appCommonFlag.equals("14"))||(appCommonFlag.equals("17"))||(appCommonFlag.equals("18"))||(appCommonFlag.equals("19")))
											{
											%>												
												<a href="javascript:submitForm('afterTcMli.do?method=getBorrowerDetails')"><bean:message key="getBorrowerDetails"/></a>


											<%}%>
												</TD>											
												
											</TR>

											<TR align="left">
												<TD align="left" valign="top" class="ColumnBackground" width="30%">&nbsp;<bean:message key="balanceApprovedAmt"/>
												</TD>
												<TD align="left" valign="top" class="TableData" colspan="8">
												<%	if((appCommonFlag.equals("0"))||(appCommonFlag.equals("1"))||(appCommonFlag.equals("2")) ||(appCommonFlag.equals("3"))
												|| (appCommonFlag.equals("4")) || (appCommonFlag.equals("5")) || (appCommonFlag.equals("6")) || (appCommonFlag.equals("11"))||(appCommonFlag.equals("12"))||(appCommonFlag.equals("13")) || (appCommonFlag.equals("14"))||(appCommonFlag.equals("17"))||(appCommonFlag.equals("18"))||(appCommonFlag.equals("19")))
												{	
												%>
												<bean:write property="balanceApprovedAmt" name="appForm"/>
												<%}%>
												</TD>
											</TR>


											 <TR align="left">
												<TD align="left" valign="top" class="ColumnBackground" width="30%"><font color="#FF0000" size="2">*</font>&nbsp;<bean:message key="constitution"/>
												</TD>
												<TD align="left" valign="top" class="TableData" colspan="8">
												<%	if((appCommonFlag.equals("0"))||(appCommonFlag.equals("1"))||(appCommonFlag.equals("2")) ||(appCommonFlag.equals("3"))
												|| (appCommonFlag.equals("4")) || (appCommonFlag.equals("5")) || (appCommonFlag.equals("6")) || (appCommonFlag.equals("11"))||(appCommonFlag.equals("12"))||(appCommonFlag.equals("13")) || (appCommonFlag.equals("14"))||(appCommonFlag.equals("17"))||(appCommonFlag.equals("18"))||(appCommonFlag.equals("19")))
												{	
												%>
		<html:select property="constitution" name="appForm" disabled="true">
														<html:option value="">Select</html:option>
														<html:option value="proprietary"><bean:message key="proprietary" /></html:option>
														<html:option value="partnership"><bean:message key="partnership" /></html:option>
														<html:option value="private"><bean:message key="private" /></html:option>
														<html:option value="public"><bean:message key="public" /></html:option>
														<html:option value="Others"><bean:message key="others" /></html:option>
													</html:select>
	<!--				<bean:write property="constitution" name="appForm"/>-->

												<%}
												else{
													%>
												
													<html:select property="constitution" name="appForm" onchange="setConstEnabled()">
														<html:option value="">Select</html:option>
														<html:option value="proprietary"><bean:message key="proprietary" /></html:option>
														<html:option value="partnership"><bean:message key="partnership" /></html:option>
														<html:option value="private"><bean:message key="private" /></html:option>
														<html:option value="public"><bean:message key="public" /></html:option>
														<html:option value="Others"><bean:message key="others" /></html:option>
													</html:select>&nbsp;&nbsp;&nbsp;&nbsp;		
												<%}%>
												
												 <% if((appCommonFlag.equals("0"))||(appCommonFlag.equals("1"))||(appCommonFlag.equals("2")) ||(appCommonFlag.equals("3"))
												|| (appCommonFlag.equals("4")) || (appCommonFlag.equals("5")) || (appCommonFlag.equals("6")) || (appCommonFlag.equals("11"))||(appCommonFlag.equals("12"))||(appCommonFlag.equals("13")) || (appCommonFlag.equals("14"))||(appCommonFlag.equals("17"))||(appCommonFlag.equals("18"))||(appCommonFlag.equals("19")))
												 {
												%>
												<bean:write property="constitutionOther" name="appForm"/>
												<%}
												else {
												%>
									
													<html:text property="constitutionOther" size="15" alt="constitution" name="appForm" maxlength="50" disabled="true"/>
												<%}%>
													
												</TD>
											 </TR>
											
											 <TR align="left">
												<TD align="left" valign="top" class="ColumnBackground" width="30%"><font color="#FF0000" size="2">*</font>&nbsp;<bean:message key="unitName"/>
												</TD>												
												<TD align="left" valign="top" class="TableData" width="30%" colspan="4">

												<%	if((appCommonFlag.equals("0"))||(appCommonFlag.equals("1"))||(appCommonFlag.equals("2")) ||(appCommonFlag.equals("3"))
												|| (appCommonFlag.equals("4")) || (appCommonFlag.equals("5")) || (appCommonFlag.equals("6")) || (appCommonFlag.equals("11"))||(appCommonFlag.equals("12"))||(appCommonFlag.equals("13")) || (appCommonFlag.equals("14"))||(appCommonFlag.equals("17"))||(appCommonFlag.equals("18"))||(appCommonFlag.equals("19")))
												{	
												%>
												<bean:write property="ssiType" name="appForm"/>
												<%}
												else{
													%>
												
													<html:select property="ssiType" name="appForm" >
														<html:option value="">Select</html:option>
														<html:option value="M/s">M/s</html:option>
														<html:option value="Shri">Shri</html:option>
														<html:option value="Smt">Smt</html:option>
														<html:option value="Ku">Ku</html:option>
													</html:select>&nbsp;&nbsp;&nbsp;&nbsp;
												<%}%>
													

													<%													if((appCommonFlag.equals("0"))||(appCommonFlag.equals("1"))||(appCommonFlag.equals("2")) ||(appCommonFlag.equals("3"))
													|| (appCommonFlag.equals("4")) || (appCommonFlag.equals("5")) || (appCommonFlag.equals("6")) || (appCommonFlag.equals("11"))||(appCommonFlag.equals("12"))||(appCommonFlag.equals("13")) || (appCommonFlag.equals("14"))||(appCommonFlag.equals("17"))||(appCommonFlag.equals("18"))||(appCommonFlag.equals("19")))

													{
													%>
													<bean:write name="appForm" property="ssiName"/>
													<%}
													else
													{ %>
													<html:text property="ssiName" size="30" alt="unitName" name="appForm" maxlength="100"/>	
													<%}%>
												 </td>
											</tr>
                      <TR>
                 <TD align="left" valign="top" class="ColumnBackground" colspan="3" ><font color="#FF0000" size="2">*</font>&nbsp;Whether the Unit Assisted is a Micro Enterprise and Guarantee cover is within Rs 5 lakh cap:</TD>
                  <TD align="left" valign="top" class="ColumnBackground" colspan="5" ><input type="radio" name="enterprise" value="Y">Yes<input type="radio" name="enterprise" value="N" checked="checked">No</TD>  
                  </TR>
											 <TR align="left">											
												<TD align="left" valign="top" class="ColumnBackground" width="30%"><font color="#FF0000" size="2">*</font>&nbsp;<bean:message key="unitAddress"/>
												</td>
												<TD align="left" valign="top" class="TableData" width="30%">
												<%
													if((appCommonFlag.equals("0"))||(appCommonFlag.equals("1"))||(appCommonFlag.equals("2")) ||(appCommonFlag.equals("3"))
													|| (appCommonFlag.equals("4")) || (appCommonFlag.equals("5")) || (appCommonFlag.equals("6")) || (appCommonFlag.equals("11"))||(appCommonFlag.equals("12"))||(appCommonFlag.equals("13")) || (appCommonFlag.equals("14"))||(appCommonFlag.equals("17"))||(appCommonFlag.equals("18"))||(appCommonFlag.equals("19")))

													{
												%>
													<bean:write name="appForm" property="address"/>

												<%	}
													else
													{ %>
													<html:textarea property="address" cols="30" alt="address" name="appForm" rows="3"/>	
													<%}%>
												</td>
												<TD align="left" valign="top" class="ColumnBackground" width="25%"><font color="#FF0000" size="2">*</font>&nbsp;<bean:message key="state"/>
												</TD>												
												<TD align="left" valign="top" class="TableData" width="15%" colspan="2">
												<%
													if((appCommonFlag.equals("0"))||(appCommonFlag.equals("1"))||(appCommonFlag.equals("2")) ||(appCommonFlag.equals("3"))
													|| (appCommonFlag.equals("4")) || (appCommonFlag.equals("5")) || (appCommonFlag.equals("6")) || (appCommonFlag.equals("11"))||(appCommonFlag.equals("12"))||(appCommonFlag.equals("13")) || (appCommonFlag.equals("14"))||(appCommonFlag.equals("17"))||(appCommonFlag.equals("18"))||(appCommonFlag.equals("19")))

													{
													%>
													<bean:write name="appForm" property="state"/>

													<%	}
													else
													{ %>												
												
													<html:select property="state" name="appForm" onchange="javascript:submitForm('afterRSFWcMli.do?method=getDistricts')">
														<html:option value="">Select</html:option>
														<html:options name="appForm" property="statesList"></html:options>

													</html:select>	
												<%}%>
													
												 </td>
											</tr>

												
											 <TR align="left">
												<TD align="left" valign="top" class="ColumnBackground" width="15%"><font color="#FF0000" size="2">*</font><bean:message key="district"/>
												</TD>												
												<TD align="left" valign="top" class="TableData" width="15%">
												<%
													if((appCommonFlag.equals("0"))||(appCommonFlag.equals("1"))||(appCommonFlag.equals("2")) ||(appCommonFlag.equals("3"))
													|| (appCommonFlag.equals("4")) || (appCommonFlag.equals("5")) || (appCommonFlag.equals("6")) || (appCommonFlag.equals("11"))||(appCommonFlag.equals("12"))||(appCommonFlag.equals("13")) || (appCommonFlag.equals("14"))||(appCommonFlag.equals("17"))||(appCommonFlag.equals("18"))||(appCommonFlag.equals("19")))

													{
													%>
													<bean:write name="appForm" property="district"/>

													<%	}
													else
													{ %>
												
													<html:select property="district" name="appForm">
														<html:option value="">Select</html:option>
														<html:options name="appForm" property="districtList"/>
													</html:select>
												</TD>
												<TD align="left" valign="top" class="TableData">
												 </td>
												 <%}%>
												<TD align="left" valign="top" class="ColumnBackground" width="25%"><font color="#FF0000" size="2">*</font><bean:message key="city"/>
												</td>
												<TD align="left" valign="top" class="TableData" width="15%">
												<%
													if((appCommonFlag.equals("0"))||(appCommonFlag.equals("1"))||(appCommonFlag.equals("2")) ||(appCommonFlag.equals("3"))
													|| (appCommonFlag.equals("4")) || (appCommonFlag.equals("5")) || (appCommonFlag.equals("6")) || (appCommonFlag.equals("11"))||(appCommonFlag.equals("12"))||(appCommonFlag.equals("13")) || (appCommonFlag.equals("14"))||(appCommonFlag.equals("17"))||(appCommonFlag.equals("18"))||(appCommonFlag.equals("19")))

													{
													%>
													<bean:write name="appForm" property="city"/>

													<%	}
													else
													{ %>
													<html:text property="city" size="20" alt="city" name="appForm" maxlength="100"/>	
													<%}%>
												</td>
											</tr>

											 <TR align="left">
												<TD align="left" valign="top" class="ColumnBackground" width="30%"><font color="#FF0000" size="2">*</font>&nbsp;<bean:message key="pinCode"/>
												</td>
												<TD align="left" valign="top" class="TableData" width="70%" colspan="5">
												<%
													if((appCommonFlag.equals("0"))||(appCommonFlag.equals("1"))||(appCommonFlag.equals("2")) ||(appCommonFlag.equals("3"))
													|| (appCommonFlag.equals("4")) || (appCommonFlag.equals("5")) || (appCommonFlag.equals("6")) || (appCommonFlag.equals("11"))||(appCommonFlag.equals("12"))||(appCommonFlag.equals("13")) || (appCommonFlag.equals("14"))||(appCommonFlag.equals("17"))||(appCommonFlag.equals("18"))||(appCommonFlag.equals("19")))

													{
													%>
													<bean:write name="appForm" property="pincode"/>

													<%	}
													else
													{ %>
													<html:text property="pincode" size="20" alt="pinCode" name="appForm" maxlength="6" onkeypress="return numbersOnly(this, event)" onkeyup="isValidNumber(this)"/>
													<%}%>
												</td>		
											</TR>

						
											 <TR align="left">											
												<TD align="left" valign="top" class="ColumnBackground" width="25%">&nbsp;<bean:message key="firmItpan"/>
												</td>
												<TD align="left" valign="top" class="TableData" width="15%" >
												<%
													if((appCommonFlag.equals("0"))||(appCommonFlag.equals("1"))||(appCommonFlag.equals("2")) ||(appCommonFlag.equals("3"))
													|| (appCommonFlag.equals("4")) || (appCommonFlag.equals("5")) || (appCommonFlag.equals("6")) || (appCommonFlag.equals("11"))||(appCommonFlag.equals("12"))||(appCommonFlag.equals("13")) || (appCommonFlag.equals("14"))||(appCommonFlag.equals("17"))||(appCommonFlag.equals("18"))||(appCommonFlag.equals("19")))

													{
												%>
													<bean:write name="appForm" property="ssiITPan"/>

												<%	}
													else
													{ %><html:text property="ssiITPan" size="20" alt="ITPAN" name="appForm" maxlength="10" />
													<%}%>
												</td>
												<TD align="left" valign="top" class="ColumnBackground" width="25%">&nbsp;<bean:message key="ssiRegNo"/>
												</td>
												<TD align="left" valign="top" class="TableData" width="15%" colspan="2">
												<%
													if((appCommonFlag.equals("0"))||(appCommonFlag.equals("1"))||(appCommonFlag.equals("2")) ||(appCommonFlag.equals("3"))
													|| (appCommonFlag.equals("4")) || (appCommonFlag.equals("5")) || (appCommonFlag.equals("6")) || (appCommonFlag.equals("11"))||(appCommonFlag.equals("12"))||(appCommonFlag.equals("13")) || (appCommonFlag.equals("14"))||(appCommonFlag.equals("17"))||(appCommonFlag.equals("18"))||(appCommonFlag.equals("19")))

													{
												%>
													<bean:write name="appForm" property="regNo"/>

												<%	}
													else
													{ %>
														<html:text property="regNo" size="30" alt="SSI Reg No" name="appForm"maxlength="25"/>
													<%}%>
												</td>

											</tr>

											 <TR align="left">
												<TD align="left" valign="top" class="ColumnBackground" width="30%"><bean:message key="industryNature"/>
												</TD>												
												<TD align="left" valign="top" class="TableData">

												<%
													if((appCommonFlag.equals("0"))||(appCommonFlag.equals("1"))||(appCommonFlag.equals("2")) ||(appCommonFlag.equals("3"))
													|| (appCommonFlag.equals("4")) || (appCommonFlag.equals("5")) || (appCommonFlag.equals("6")) || (appCommonFlag.equals("11"))||(appCommonFlag.equals("12"))||(appCommonFlag.equals("13")) || (appCommonFlag.equals("14"))||(appCommonFlag.equals("17"))||(appCommonFlag.equals("18"))||(appCommonFlag.equals("19")))

													{
													%>
													<bean:write name="appForm" property="industryNature"/>

													<%	}
													else
													{ %>											
													<html:select property="industryNature" name="appForm" onchange="javascript:submitForm('afterRSFWcMli.do?method=getIndustrySector')">
														<html:option value="">Select</html:option>
														<html:options name="appForm" property="industryNatureList"></html:options>	
													</html:select>	
												<%}%>
													
												 </td>
												<TD align="left" valign="top" class="ColumnBackground" width="25%"><bean:message key="industrySector"/>
												</td>
												<TD align="left" valign="top" class="TableData" colspan="2">
												<%
													if((appCommonFlag.equals("0"))||(appCommonFlag.equals("1"))||(appCommonFlag.equals("2")) ||(appCommonFlag.equals("3"))
													|| (appCommonFlag.equals("4")) || (appCommonFlag.equals("5")) || (appCommonFlag.equals("6")) || (appCommonFlag.equals("11"))||(appCommonFlag.equals("12"))||(appCommonFlag.equals("13")) || (appCommonFlag.equals("14"))||(appCommonFlag.equals("17"))||(appCommonFlag.equals("18"))||(appCommonFlag.equals("19")))

													{
													%>
													<bean:write name="appForm" property="industrySector"/>

													<%	}
													else
													{ %>										
												
													<html:select property="industrySector" name="appForm" >
														<html:option value="">Select</html:option>
														<html:options name="appForm" property="industrySectorList"></html:options>
														<html:option value="Others">Others</html:option>
													 </html:select>
													<%}%>
												 </td>
											</tr>

											 <TR align="left">
												<TD align="left" valign="top" class="ColumnBackground" width="30%">&nbsp;<font color="#FF0000" size="2">*</font><bean:message key="activitytype"/>
												</TD>												
												<TD align="left" valign="top" class="TableData">
												<%
													if((appCommonFlag.equals("0"))||(appCommonFlag.equals("1"))||(appCommonFlag.equals("2")) ||(appCommonFlag.equals("3"))
													|| (appCommonFlag.equals("4")) || (appCommonFlag.equals("5")) || (appCommonFlag.equals("6")) || (appCommonFlag.equals("11"))||(appCommonFlag.equals("12"))||(appCommonFlag.equals("13")) || (appCommonFlag.equals("14"))||(appCommonFlag.equals("17"))||(appCommonFlag.equals("18"))||(appCommonFlag.equals("19")))

													{
												%>
													<bean:write name="appForm" property="activityType"/>

												<%	}
													else
													{ %>
													<html:text property="activityType" size="20" alt="Activity Type" name="appForm" maxlength="50"/>
													<%}%>
												 </td>
												<TD align="left" valign="top" class="ColumnBackground" width="25%">&nbsp;<bean:message key="noOfEmployees"/>
												</td>
												<TD align="left" valign="top" class="TableData" width="15%" colspan="2">
												<%
													if((appCommonFlag.equals("0"))||(appCommonFlag.equals("1"))||(appCommonFlag.equals("2")) ||(appCommonFlag.equals("3"))
													|| (appCommonFlag.equals("4")) || (appCommonFlag.equals("5")) || (appCommonFlag.equals("6")) || (appCommonFlag.equals("11"))||(appCommonFlag.equals("12"))||(appCommonFlag.equals("13")) || (appCommonFlag.equals("14"))||(appCommonFlag.equals("17"))||(appCommonFlag.equals("18"))||(appCommonFlag.equals("19")))

													{
												%>
													<bean:write name="appForm" property="employeeNos"/>

												<%	}
													else
													{ %><html:text property="employeeNos" size="10" alt="No Of employees" name="appForm" onkeypress="return numbersOnly(this, event)" onkeyup="isValidNumber(this)"/>	
													<%}%>
												</td>
											</tr>

											 <TR align="left">
												<TD align="left" valign="top" class="ColumnBackground" width="30%"><bean:message key="turnover"/>
												</TD>												
												<TD align="left" valign="top" class="TableData">
												<%
													if((appCommonFlag.equals("0"))||(appCommonFlag.equals("1"))||(appCommonFlag.equals("2")) ||(appCommonFlag.equals("3"))
													|| (appCommonFlag.equals("4")) || (appCommonFlag.equals("5")) || (appCommonFlag.equals("6")) || (appCommonFlag.equals("11"))||(appCommonFlag.equals("12"))||(appCommonFlag.equals("13")) || (appCommonFlag.equals("14"))||(appCommonFlag.equals("17"))||(appCommonFlag.equals("18"))||(appCommonFlag.equals("19")))
													{
												%>
													<bean:write name="appForm" property="projectedSalesTurnover"/>

												<%	}
													else
													{ %>
													<html:text property="projectedSalesTurnover" size="20" alt="turnover" name="appForm" maxlength="16" onkeypress="return decimalOnly(this, event,13)" onkeyup="isValidDecimal(this)"/>
													<%}%>
													<bean:message key="inRs"/>
												 </td>
												<TD align="left" valign="top" class="ColumnBackground" width="25%"><bean:message key="exports"/>
												</td>
												<TD align="left" valign="top" class="TableData" width="15%" colspan="2">
												<%
													if((appCommonFlag.equals("0"))||(appCommonFlag.equals("1"))||(appCommonFlag.equals("2")) ||(appCommonFlag.equals("3"))
													|| (appCommonFlag.equals("4")) || (appCommonFlag.equals("5")) || (appCommonFlag.equals("6")) || (appCommonFlag.equals("11"))||(appCommonFlag.equals("12"))||(appCommonFlag.equals("13")) || (appCommonFlag.equals("14"))||(appCommonFlag.equals("17"))||(appCommonFlag.equals("18"))||(appCommonFlag.equals("19")))

													{
												%>
													<bean:write name="appForm" property="projectedExports"/>

												<%	}
													else
													{ %>
													<html:text property="projectedExports" size="10" alt="exports" name="appForm" maxlength="16" onkeypress="return decimalOnly(this, event,13)" onkeyup="isValidDecimal(this)"/>
													<%}%>
													<bean:message key="inRs"/>
												</td>
											</tr>

										</TABLE>
									</TD>
								</TR>		
								<tr>
									<td>
									</td>
								</tr>
								<TR>
									<TD class="SubHeading" colspan="6"><bean:message key="promoterHeader" />
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
										<font color="#FF0000" size="2">*</font>&nbsp;<bean:message key="title"/>
								   </TD>
								   <TD align="left" valign="top" class="ColumnBackground" width="25%">
										<font color="#FF0000" size="2">*</font>&nbsp;<bean:message key="firstName"/>
								   </TD>
									<TD align="left" valign="top" class="ColumnBackground" width="25%">
										<bean:message key="middleName"/>	
								   </TD>
									<TD align="left" valign="top" class="ColumnBackground" width="25%">
										<font color="#FF0000" size="2">*</font>&nbsp;<bean:message key="lastName"/>
								   </TD>
								</TR>

								<TR align="left">
								  <TD align="left" valign="top" class="ColumnBackground" colspan="2" width="15%"><bean:message key="name"/>
								  </TD>		
								  <TD align="left" valign="top" class="TableData">

								  <%if((appCommonFlag.equals("0"))||(appCommonFlag.equals("1"))||(appCommonFlag.equals("2")) ||(appCommonFlag.equals("3"))
									|| (appCommonFlag.equals("4")) || (appCommonFlag.equals("5")) || (appCommonFlag.equals("6")) || (appCommonFlag.equals("11"))||(appCommonFlag.equals("12"))||(appCommonFlag.equals("13")) || (appCommonFlag.equals("14"))||(appCommonFlag.equals("17"))||(appCommonFlag.equals("18"))||(appCommonFlag.equals("19")))

											{										

									%>
									<bean:write property="cpTitle" name="appForm"/>
									<%}
									else {
									%>
								 
									<html:select property="cpTitle" name="appForm" onchange="enableGender()">
										<html:option value="">Select</html:option>
										<html:option value="Mr.">Mr</html:option>
										<html:option value="Smt">Smt</html:option>
										<html:option value="Ku">Ku</html:option>
									</html:select>
									<%}%>
									
								  </TD>
								  <TD align="left" valign="top" class="TableData">

								  <% if((appCommonFlag.equals("0"))||(appCommonFlag.equals("1"))||(appCommonFlag.equals("2")) ||(appCommonFlag.equals("3"))
									|| (appCommonFlag.equals("4")) || (appCommonFlag.equals("5")) || (appCommonFlag.equals("6")) || (appCommonFlag.equals("11"))||(appCommonFlag.equals("12"))||(appCommonFlag.equals("13")) || (appCommonFlag.equals("14"))||(appCommonFlag.equals("17"))||(appCommonFlag.equals("18"))||(appCommonFlag.equals("19")))
											{										

									%>
									<bean:write property="cpFirstName" name="appForm"/>
									<%}
									else {
									%>
								  
									<html:text property="cpFirstName" size="20" alt="firstName" name="appForm" maxlength="20"/>
									<%}%>
									
								  </TD>
								   <TD align="left" valign="top" class="TableData">

								   <%if((appCommonFlag.equals("0"))||(appCommonFlag.equals("1"))||(appCommonFlag.equals("2")) ||(appCommonFlag.equals("3"))
									|| (appCommonFlag.equals("4")) || (appCommonFlag.equals("5")) || (appCommonFlag.equals("6")) || (appCommonFlag.equals("11"))||(appCommonFlag.equals("12"))||(appCommonFlag.equals("13")) || (appCommonFlag.equals("14"))||(appCommonFlag.equals("17"))||(appCommonFlag.equals("18"))||(appCommonFlag.equals("19")))

											{										

									%>
									<bean:write property="cpMiddleName" name="appForm"/>
									<%}
									else {
									%>								  
								   
									<html:text property="cpMiddleName" size="20" alt="middleName" name="appForm" maxlength="20"/>

									<%}%>
									
								  </TD>
								   <TD align="left" valign="top" class="TableData">

								   <%if((appCommonFlag.equals("0"))||(appCommonFlag.equals("1"))||(appCommonFlag.equals("2")) ||(appCommonFlag.equals("3"))
									|| (appCommonFlag.equals("4")) || (appCommonFlag.equals("5")) || (appCommonFlag.equals("6")) || (appCommonFlag.equals("11"))||(appCommonFlag.equals("12"))||(appCommonFlag.equals("13")) || (appCommonFlag.equals("14"))||(appCommonFlag.equals("17"))||(appCommonFlag.equals("18"))||(appCommonFlag.equals("19")))
											{										

									%>
									<bean:write property="cpLastName" name="appForm"/>
									<%}
									else {
									%>	
								  
									<html:text property="cpLastName" size="20" alt="lastName" name="appForm" maxlength="20"/>

									<%}%>									
								  </TD>
								</TR>

								<TR align="left">
									<TD align="left" valign="top" class="ColumnBackground" colspan="2"><font color="#FF0000" size="2">*</font>&nbsp;<bean:message key="gender"/>
									</TD>
									<TD align="left" valign="top" class="TableData" width="20%">

									<% if((appCommonFlag.equals("0"))||(appCommonFlag.equals("1"))||(appCommonFlag.equals("2")) ||(appCommonFlag.equals("3"))
									|| (appCommonFlag.equals("4")) || (appCommonFlag.equals("5")) || (appCommonFlag.equals("6")) || (appCommonFlag.equals("11"))||(appCommonFlag.equals("12"))||(appCommonFlag.equals("13")) || (appCommonFlag.equals("14"))||(appCommonFlag.equals("17"))||(appCommonFlag.equals("18"))||(appCommonFlag.equals("19")))
											{										

									%>
							<bean:write property="cpGender" name="appForm"/>

									<%}
									else {
									%>	
									
										<html:radio name="appForm" value="M" property="cpGender" >
										</html:radio>
									
										<bean:message key="male" />&nbsp;&nbsp;&nbsp;

									
										<html:radio name="appForm" value="F" property="cpGender" ></html:radio>
									
										<bean:message key="female" />
									<%}%>
									
									</TD>
									<TD align="left" valign="top" class="ColumnBackground">
										&nbsp;<bean:message key="chiefItpan" />
									</TD>
									<TD align="left" valign="top" class="TableData" colspan="2" width="20%">

									<% if((appCommonFlag.equals("0"))||(appCommonFlag.equals("1"))||(appCommonFlag.equals("2")) ||(appCommonFlag.equals("3"))
									|| (appCommonFlag.equals("4")) || (appCommonFlag.equals("5")) || (appCommonFlag.equals("6")) || (appCommonFlag.equals("11"))||(appCommonFlag.equals("12"))||(appCommonFlag.equals("13")) || (appCommonFlag.equals("14"))||(appCommonFlag.equals("17"))||(appCommonFlag.equals("18"))||(appCommonFlag.equals("19")))
											{										

									%>
									<bean:write property="cpITPAN" name="appForm"/>
									<%}
									else {
									%>	
									
										<html:text property="cpITPAN" size="15" alt="chiefItpan" name="appForm" maxlength="10"/>

									<%}%>
									
									</TD>													
								</TR>
                <!--added by sukant-->
                <TR>
                <TD align="left" valign="top" class="ColumnBackground" colspan="3" ><font color="#FF0000" size="2">*</font>&nbsp;Whether the Chief Promoter belongs to Minority Community:</TD>
                  <TD align="left" valign="top" class="ColumnBackground" colspan="5" >
<input type="radio" name="religion" value="Y">Yes<input type="radio" name="religion" value="N" checked="checked">No</TD>
                  
                   
                  </TR>

								<TR align="left">
									<TD align="left" valign="top" class="ColumnBackground" colspan="2">
										<font color="#FF0000" size="2">*</font>&nbsp;<bean:message key="dob" />					
									</TD>
									<TD align="left" valign="top" class="TableData" >

									<% if((appCommonFlag.equals("0"))||(appCommonFlag.equals("1"))||(appCommonFlag.equals("2")) ||(appCommonFlag.equals("3"))
									|| (appCommonFlag.equals("4")) || (appCommonFlag.equals("5")) || (appCommonFlag.equals("6")) || (appCommonFlag.equals("11"))||(appCommonFlag.equals("12"))||(appCommonFlag.equals("13")) || (appCommonFlag.equals("14"))||(appCommonFlag.equals("17"))||(appCommonFlag.equals("18"))||(appCommonFlag.equals("19")))
											{										

									%>
									<bean:write property="cpDOB" name="appForm"/>
									<%}
									else {
									%>	
									
										<html:text property="cpDOB" size="15" alt="dob" name="appForm" maxlength="10"/>
										<IMG src="images/CalendarIcon.gif" width="20" onClick="showCalendar('appForm.cpDOB')" align="center">
									<%}%>
									

									<TD align="left" valign="top" class="ColumnBackground">
									<bean:message key="socialCategory"/>
									</TD>
									<TD align="left" valign="top" class="TableData" width="20%" colspan="3">

									<% if((appCommonFlag.equals("0"))||(appCommonFlag.equals("1"))||(appCommonFlag.equals("2")) ||(appCommonFlag.equals("3"))
									|| (appCommonFlag.equals("4")) || (appCommonFlag.equals("5")) || (appCommonFlag.equals("6")) || (appCommonFlag.equals("11"))||(appCommonFlag.equals("12"))||(appCommonFlag.equals("13")) || (appCommonFlag.equals("14"))||(appCommonFlag.equals("17"))||(appCommonFlag.equals("18"))||(appCommonFlag.equals("19")))
											{										

									%>
									<bean:write property="socialCategory" name="appForm"/>
									<%}
									else {
									%>	
									
										<html:select property="socialCategory" name="appForm">
											<html:option value="">Select</html:option>
											<html:options name="appForm" property="socialCategoryList"></html:options>
											
										</html:select>

									<%}%>									
										
									</TD>

										
									</TD>
								</TR>

								<TR align="left">
									<TD align="left" valign="top" class="ColumnBackground" colspan="2">
										&nbsp;<bean:message key="legalid" />					
									</TD>
									<TD align="left" valign="top" class="TableData" colspan="5">

									<% if((appCommonFlag.equals("0"))||(appCommonFlag.equals("1"))||(appCommonFlag.equals("2")) ||(appCommonFlag.equals("3"))
									|| (appCommonFlag.equals("4")) || (appCommonFlag.equals("5")) || (appCommonFlag.equals("6")) || (appCommonFlag.equals("11"))||(appCommonFlag.equals("12"))||(appCommonFlag.equals("13")) || (appCommonFlag.equals("14"))||(appCommonFlag.equals("17"))||(appCommonFlag.equals("18"))||(appCommonFlag.equals("19")))
											{										

									%>
									<bean:write property="cpLegalID" name="appForm"/>
									<%}
									else {
									%>	
																	
										<html:select property="cpLegalID" name="appForm" onchange="enableOtherLegalId()">
											<html:option value="">Select</html:option>
											<html:option value="VoterIdentityCard">Voter Identity Card</html:option>
											<html:option value="PASSPORT">Passport Number</html:option>
											<html:option value="Driving License">Driving License</html:option>
											<html:option value="RationCardnumber">Ration Card Number</html:option>
											<html:option value="Others">Others</html:option>
										</html:select>			
									<%}%>

									<% if((appCommonFlag.equals("0"))||(appCommonFlag.equals("1"))||(appCommonFlag.equals("2")) ||(appCommonFlag.equals("3"))
									|| (appCommonFlag.equals("4")) || (appCommonFlag.equals("5")) || (appCommonFlag.equals("6")) || (appCommonFlag.equals("11"))||(appCommonFlag.equals("12"))||(appCommonFlag.equals("13")) || (appCommonFlag.equals("14"))||(appCommonFlag.equals("17"))||(appCommonFlag.equals("18"))||(appCommonFlag.equals("19")))
											{										

									%>
									<bean:write property="otherCpLegalID" name="appForm"/>
									<%}
									else {
									%>																	
										<html:text property="otherCpLegalID" size="20" alt="otherId" name="appForm"  maxlength="50" disabled="true"/>&nbsp;&nbsp;<bean:message key="otherId"/>	
									<%}%>

										&nbsp;&nbsp;&nbsp;&nbsp;<strong><bean:message key="pleaseSpecify"/></strong>

									<% if((appCommonFlag.equals("0"))||(appCommonFlag.equals("1"))||(appCommonFlag.equals("2")) ||(appCommonFlag.equals("3"))
									|| (appCommonFlag.equals("4")) || (appCommonFlag.equals("5")) || (appCommonFlag.equals("6")) || (appCommonFlag.equals("11"))||(appCommonFlag.equals("12"))||(appCommonFlag.equals("13")) || (appCommonFlag.equals("14"))||(appCommonFlag.equals("17"))||(appCommonFlag.equals("18"))||(appCommonFlag.equals("19")))
											{										

									%>
									<bean:write property="cpLegalIdValue" name="appForm"/>
									<%}
									else {
									%>							
									
										<html:text property="cpLegalIdValue" size="20" alt="value" name="appForm" maxlength="20"/>&nbsp;&nbsp;<bean:message key="id" />
									<%}%>
									
									
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
												
													<b><span style="font-size: 9pt" >	

													<% if((appCommonFlag.equals("0"))||(appCommonFlag.equals("1"))||(appCommonFlag.equals("2")) ||(appCommonFlag.equals("3"))
													|| (appCommonFlag.equals("4")) || (appCommonFlag.equals("5")) || (appCommonFlag.equals("6")) || (appCommonFlag.equals("11"))||(appCommonFlag.equals("12"))||(appCommonFlag.equals("13")) || (appCommonFlag.equals("14"))||(appCommonFlag.equals("17"))||(appCommonFlag.equals("18"))||(appCommonFlag.equals("19")))
															{										

													%>
													<bean:write property="firstName" name="appForm"/>
													<%}
													else {
													%>							
									
													<html:text property="firstName" size="20" alt="firstName" name="appForm" maxlength="50"/>
													<%}%>
													</span></b>
													
												</td>
												<td width="17%" class="ColumnBackground" >
													<b><span style="font-size: 9pt"> <bean:message key="promoterItpan" /></span></b>
												</td>
												<td width="17%" class="TableData">												
													<b><span style="font-size: 9pt"> 

													<% if((appCommonFlag.equals("0"))||(appCommonFlag.equals("1"))||(appCommonFlag.equals("2")) ||(appCommonFlag.equals("3"))
													|| (appCommonFlag.equals("4")) || (appCommonFlag.equals("5")) || (appCommonFlag.equals("6")) || (appCommonFlag.equals("11"))||(appCommonFlag.equals("12"))||(appCommonFlag.equals("13")) || (appCommonFlag.equals("14"))||(appCommonFlag.equals("17"))||(appCommonFlag.equals("18"))||(appCommonFlag.equals("19")))
													{										

													%>
													<bean:write property="firstItpan" name="appForm"/>
													<%}
													else {
													%>		
													<html:text property="firstItpan" size="20" alt="firstItpan" name="appForm" maxlength="10"/>
													<%}%>
													</span></b>
													
												</td>
												<td width="17%" class="ColumnBackground" >
													<b><span style="font-size: 9pt"><bean:message key="promoterDob" /></span></b>
												</td>
												<td width="17%" class="TableData">
												
													<b><span style="font-size: 9pt"> 
													
													<% if((appCommonFlag.equals("0"))||(appCommonFlag.equals("1"))||(appCommonFlag.equals("2")) ||(appCommonFlag.equals("3"))
													|| (appCommonFlag.equals("4")) || (appCommonFlag.equals("5")) || (appCommonFlag.equals("6")) || (appCommonFlag.equals("11"))||(appCommonFlag.equals("12"))||(appCommonFlag.equals("13")) || (appCommonFlag.equals("14"))||(appCommonFlag.equals("17"))||(appCommonFlag.equals("18"))||(appCommonFlag.equals("19")))
													{										

													%>
													<bean:write property="firstDOB" name="appForm"/>
													<%}
													else {
													%>												
													 <html:text property="firstDOB" size="10" alt="firstDOB" name="appForm" maxlength="10"/>
													<IMG src="images/CalendarIcon.gif" width="20" onClick="showCalendar('appForm.firstDOB')" align="center">
													<%}%>
													</span></b>
													
												</td>
											</tr>
											 <tr>
												<td class="ColumnBackground" width="17%"><b>
													<span style="font-size: 9pt">2. <bean:message key="promoterName" />	</span></b>
												</td>
												<td width="15%" class="TableData">
												
													<b><span style="font-size: 9pt"> 

													<% if((appCommonFlag.equals("0"))||(appCommonFlag.equals("1"))||(appCommonFlag.equals("2")) ||(appCommonFlag.equals("3"))
													|| (appCommonFlag.equals("4")) || (appCommonFlag.equals("5")) || (appCommonFlag.equals("6")) || (appCommonFlag.equals("11"))||(appCommonFlag.equals("12"))||(appCommonFlag.equals("13")) || (appCommonFlag.equals("14"))||(appCommonFlag.equals("17"))||(appCommonFlag.equals("18"))||(appCommonFlag.equals("19")))
													{										

													%>
													<bean:write property="secondName" name="appForm"/>
													<%}
													else {
													%>		
													<html:text property="secondName" size="20" alt="secondName" name="appForm" maxlength="50"/>
													<%}%>
													</span></b>
													
												</td>
												<td width="17%" class="ColumnBackground" >
													<b><span style="font-size: 9pt"> <bean:message key="promoterItpan" /></span></b>
												</td>
												<td width="17%" class="TableData">
												
													<b><span style="font-size: 9pt"> 

													<% if((appCommonFlag.equals("0"))||(appCommonFlag.equals("1"))||(appCommonFlag.equals("2")) ||(appCommonFlag.equals("3"))
													|| (appCommonFlag.equals("4")) || (appCommonFlag.equals("5")) || (appCommonFlag.equals("6")) || (appCommonFlag.equals("11"))||(appCommonFlag.equals("12"))||(appCommonFlag.equals("13")) || (appCommonFlag.equals("14"))||(appCommonFlag.equals("17"))||(appCommonFlag.equals("18"))||(appCommonFlag.equals("19")))
													{										

													%>
													<bean:write property="secondItpan" name="appForm"/>
													<%}
													else {
													%>		
													<html:text property="secondItpan" size="20" alt="secondItpan" name="appForm" maxlength="10"/>
													<%}%>
													</span></b>
													
												</td>
												<td width="17%" class="ColumnBackground" >
													<b><span style="font-size: 9pt"><bean:message key="promoterDob" /></span></b>
												</td>
												<td width="17%" class="TableData">
												
													<b><span style="font-size: 9pt"> 

													<% if((appCommonFlag.equals("0"))||(appCommonFlag.equals("1"))||(appCommonFlag.equals("2")) ||(appCommonFlag.equals("3"))
													|| (appCommonFlag.equals("4")) || (appCommonFlag.equals("5")) || (appCommonFlag.equals("6")) || (appCommonFlag.equals("11"))||(appCommonFlag.equals("12"))||(appCommonFlag.equals("13")) || (appCommonFlag.equals("14"))||(appCommonFlag.equals("17"))||(appCommonFlag.equals("18"))||(appCommonFlag.equals("19")))
													{										

													%>
													<bean:write property="secondDOB" name="appForm"/>
													<%}
													else {
													%>	
													 <html:text property="secondDOB" size="10" alt="secondDOB" name="appForm" maxlength="10"/>
													<IMG src="images/CalendarIcon.gif" width="20" onClick="showCalendar('appForm.secondDOB')" align="center">
													<%}%>
													</span></b>
													
												</td>
											</tr>

											 <tr>
												<td class="ColumnBackground" width="17%"><b>
													<span style="font-size: 9pt">3. <bean:message key="promoterName" />	</span></b>
												</td>
												<td width="15%" class="TableData">
												
													<b><span style="font-size: 9pt"> 

													<% if((appCommonFlag.equals("0"))||(appCommonFlag.equals("1"))||(appCommonFlag.equals("2")) ||(appCommonFlag.equals("3"))
													|| (appCommonFlag.equals("4")) || (appCommonFlag.equals("5")) || (appCommonFlag.equals("6")) || (appCommonFlag.equals("11"))||(appCommonFlag.equals("12"))||(appCommonFlag.equals("13")) || (appCommonFlag.equals("14"))||(appCommonFlag.equals("17"))||(appCommonFlag.equals("18"))||(appCommonFlag.equals("19")))
												{										

													%>
													<bean:write property="thirdName" name="appForm"/>
													<%}
													else {
													%>	
													<html:text property="thirdName" size="20" alt="thirdName" name="appForm" maxlength="50"/>
													<%}%>
													</span></b>
													
												</td>
												<td width="17%" class="ColumnBackground" >
													<b><span style="font-size: 9pt"> <bean:message key="promoterItpan" /></span></b>
												</td>
												<td width="17%" class="TableData">
												
													<b><span style="font-size: 9pt"> 
													<% if((appCommonFlag.equals("0"))||(appCommonFlag.equals("1"))||(appCommonFlag.equals("2")) ||(appCommonFlag.equals("3"))
													|| (appCommonFlag.equals("4")) || (appCommonFlag.equals("5")) || (appCommonFlag.equals("6")) || (appCommonFlag.equals("11"))||(appCommonFlag.equals("12"))||(appCommonFlag.equals("13")) || (appCommonFlag.equals("14"))||(appCommonFlag.equals("17"))||(appCommonFlag.equals("18"))||(appCommonFlag.equals("19")))
													{										

													%>
													<bean:write property="thirdItpan" name="appForm"/>
													<%}
													else {
													%>	
													<html:text property="thirdItpan" size="20" alt="thirdItpan" name="appForm" maxlength="10"/>
													<%}%>
													</span></b>
													
												</td>
												<td width="17%" class="ColumnBackground" >
													<b><span style="font-size: 9pt"><bean:message key="promoterDob" /></span></b>
												</td>
												<td width="17%" class="TableData">
												
													<b><span style="font-size: 9pt"> 

													<% if((appCommonFlag.equals("0"))||(appCommonFlag.equals("1"))||(appCommonFlag.equals("2")) ||(appCommonFlag.equals("3"))
													|| (appCommonFlag.equals("4")) || (appCommonFlag.equals("5")) || (appCommonFlag.equals("6")) || (appCommonFlag.equals("11"))||(appCommonFlag.equals("12"))||(appCommonFlag.equals("13")) || (appCommonFlag.equals("14"))||(appCommonFlag.equals("17"))||(appCommonFlag.equals("18"))||(appCommonFlag.equals("19")))
													{										

													%>
													<bean:write property="thirdDOB" name="appForm"/>
													<%}
													else {
													%>	
													 <html:text property="thirdDOB" size="10" alt="thirdDOB" name="appForm" maxlength="10"/>
													<IMG src="images/CalendarIcon.gif" width="20" onClick="showCalendar('appForm.thirdDOB')" align="center">
													<%}%>
													</span></b>
													
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
								<TR>
               <TD align="left" valign="top" class="ColumnBackground" colspan="2" ><font color="#FF0000" size="2">*</font>&nbsp;Whether Unit assisted is a new unit:</TD>
                  <TD align="left" valign="top" class="ColumnBackground" colspan="5" >
<input type="radio" name="unitAssisted" value="Y">Yes<input type="radio" name="unitAssisted" value="N" checked="checked">No</TD>	</TR>

			<TR>
						<TD align="left" valign="top" class="ColumnBackground" colspan="2" ><font color="#FF0000" size="2">*</font>&nbsp;Whether the Unit Assisted is Women Operated and/or Women Owned:</TD>
						<TD align="left" valign="top" class="ColumnBackground" colspan="5" >
<input type="radio" name="womenOperated" value="Y">Yes<input type="radio" name="womenOperated" value="N" checked="checked">No</TD>
                  
                  </TR>	
				<TR>
						<TD align="left" valign="top" class="ColumnBackground" colspan="2">Whether the Unit Assisted is an MSE as per the MSMED Act 2006 definition of MSE:</TD>
						<TD align="left" valign="top" class="ColumnBackground" colspan="7"><html:radio name="appForm" value="Y" property="mSE" >Y
												</html:radio><html:radio name="appForm" value="N" property="mSE">N</html:radio>
																								
												</TD>
                  
                  </TR>		


                <tr align="left"> 
									 <td class="ColumnBackground">
										<font color="#FF0000" size="2">*</font>&nbsp;<bean:message key="collateralSec" />
									 </td>
									 <td align="left" valign="middle" class="TableData"> <div align="left">
									 <% if(appCommonFlag.equals("11") || appCommonFlag.equals("12") || appCommonFlag.equals("13"))
										 {
										%>
<!--				<bean:write property="collateralSecurityTaken" name="appForm"/>-->
										<html:radio name="appForm" value="Y" property="collateralSecurityTaken" disabled="true">
										</html:radio>
										
										<bean:message key="y" />

										
										<html:radio name="appForm" value="N" property="collateralSecurityTaken" disabled="true"></html:radio>
										
										<bean:message key="n" />

										<%}
										else {
										%>	
									
										<html:radio name="appForm" value="Y" property="collateralSecurityTaken" >
										</html:radio>
										
										<bean:message key="y" />

										
										<html:radio name="appForm" value="N" property="collateralSecurityTaken" ></html:radio>
										
										<bean:message key="n" />
									<%}%>
										
									</td>
									<td class="ColumnBackground"> &nbsp
										<font color="#FF0000" size="1">#</font>&nbsp;<bean:message key="thirdPartyTaken" />
									</td>
									<td align="left" valign="middle" class="TableData">	

									 <% if(appCommonFlag.equals("11") || appCommonFlag.equals("12") || appCommonFlag.equals("13"))
										 {
										%>
<!--					<bean:write property="thirdPartyGuaranteeTaken" name="appForm"/>-->
										 <html:radio name="appForm" value="Y" property="thirdPartyGuaranteeTaken" disabled="true">
										 <bean:message key="y" /></html:radio>						 
										
										<html:radio name="appForm" value="N" property="thirdPartyGuaranteeTaken" disabled="true">
										
										<bean:message key="n" /></html:radio>

										<%}
										else {
										%>	
										
										 <html:radio name="appForm" value="Y" property="thirdPartyGuaranteeTaken" >
										 <bean:message key="y" /></html:radio>						 
										
										<html:radio name="appForm" value="N" property="thirdPartyGuaranteeTaken" >
										
										<bean:message key="n" /></html:radio>
									<%}%>
									
									</td>
								</tr>
								<tr align="left">
									<td class="ColumnBackground" colspan="4">									
										<font color="#FF0000" size="2">*&nbsp;</font> <font color="#FF0000" size="1"><bean:message key="collateralSecHint"/></font><br>
										<font color="#FF0000" size="1">#&nbsp;</font><font color="#FF0000" size="1"><bean:message key="thirdPartyTakenHint"/></font>
									</td>
								</tr>
                <TR align="left">
									<TD align="left" valign="top" class="ColumnBackground"><font color="#FF0000" size="2">*</font>&nbsp;Internal Rating</td>								
									<TD align="left" valign="top" class="tableData">
									 <% if(appCommonFlag.equals("11") || appCommonFlag.equals("12") || appCommonFlag.equals("13"))
										 {
										%>
									Internal Rating
									<%	}
										else
										{ %><html:text property="internalRating" size="20" alt="internal Rating" name="appForm" maxlength="5"/>
										<%}%>
									</TD>
                  <TD align="left" valign="top" class="ColumnBackground"><font color="#FF0000" size="2">*</font>&nbsp;External Rating</td>								
									<TD align="left" valign="top" class="tableData">
									 <% if(appCommonFlag.equals("11") || appCommonFlag.equals("12") || appCommonFlag.equals("13"))
										 {
										%>
									External Rating
									<%	}
										else
										{ %><html:text property="externalRating" size="20" alt="external Rating" name="appForm" maxlength="5"/>
										<%}%>
									</TD>
								 </TR>
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

													 <% if(appCommonFlag.equals("11") || appCommonFlag.equals("12") || appCommonFlag.equals("13"))
													 {
													%>
													<bean:write property="guarantorsName1" name="appForm"/>
													<%}
													else {
													%>	
													
													<html:text property="guarantorsName1" size="20" alt="guarantorsName1" name="appForm" maxlength="100"/>
													<%}%>
													
													</span></b>
													
												</td>
												<td width="17%" class="ColumnBackground" >
													<b><span style="font-size: 9pt"><bean:message key="guarantorsNetWorth" /></span></b>
												</td>
												<td width="17%" class="TableData">
												 
													<b><span style="font-size: 9pt"> 

													 <% if(appCommonFlag.equals("11") || appCommonFlag.equals("12") || appCommonFlag.equals("13"))
													 {
													%>
													<bean:write property="guarantorsNetWorth1" name="appForm"/>&nbsp;<bean:message key="inRs" />
													<%}
													else {
													%>															
													<html:text property="guarantorsNetWorth1" size="20" alt="guarantorsNetWorth1" name="appForm" maxlength="16" onkeypress="return decimalOnly(this, event,13)" onkeyup="isValidDecimal(this)"/>&nbsp;<bean:message key="inRs" />
													<%}%>
													</span></b>
												
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

													 <% if(appCommonFlag.equals("11") || appCommonFlag.equals("12") || appCommonFlag.equals("13"))
													 {
													%>
													<bean:write property="guarantorsName2" name="appForm"/>
													<%}
													else {
													%>	
														
													<html:text property="guarantorsName2" size="20" alt="guarantorsName2" name="appForm" maxlength="100"/>
													<%}%>
													</span></b>
													
												</td>
												<td width="17%" class="ColumnBackground" >
													<b><span style="font-size: 9pt"><bean:message key="guarantorsNetWorth" /></span></b>
												</td>
												<td width="17%" class="TableData">
												
													<b><span style="font-size: 9pt"> 

													<% if(appCommonFlag.equals("11") || appCommonFlag.equals("12") || appCommonFlag.equals("13"))
													 {
													%>
													<bean:write property="guarantorsNetWorth2" name="appForm"/>&nbsp;<bean:message key="inRs" />
													<%}
													else {
													%>	
													
													<html:text property="guarantorsNetWorth2" size="20" alt="guarantorsNetWorth2" name="appForm" maxlength="16" onkeypress="return decimalOnly(this, event,13)" onkeyup="isValidDecimal(this)"/>&nbsp;<bean:message key="inRs" />
													<%}%>
													</span></b>
													
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

													<% if(appCommonFlag.equals("11") || appCommonFlag.equals("12") || appCommonFlag.equals("13"))
													 {
													%>
													<bean:write property="guarantorsName3" name="appForm"/>
													<%}
													else {
													%>												
													<html:text property="guarantorsName3" size="20" alt="guarantorsName3" name="appForm" maxlength="100" />
													<%}%>
													</span></b>
													
												</td>
												<td width="17%" class="ColumnBackground" >
													<b><span style="font-size: 9pt"><bean:message key="guarantorsNetWorth" /></span></b>
												</td>
												<td width="17%" class="TableData">
												
													<b><span style="font-size: 9pt">

													<% if(appCommonFlag.equals("11") || appCommonFlag.equals("12") || appCommonFlag.equals("13"))
													 {
													%>
													<bean:write property="guarantorsNetWorth3" name="appForm"/>&nbsp;<bean:message key="inRs" />
													<%}
													else {
													%>													
													<html:text property="guarantorsNetWorth3" size="20" alt="guarantorsNetWorth4" name="appForm" maxlength="16" onkeypress="return decimalOnly(this, event,13)" onkeyup="isValidDecimal(this)"/>&nbsp;<bean:message key="inRs" />
													<%}%>
													</span></b>
													
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

													<% if(appCommonFlag.equals("11") || appCommonFlag.equals("12") || appCommonFlag.equals("13"))
													 {
													%>
													<bean:write property="guarantorsName4" name="appForm"/>
													<%}
													else {
													%>												
													<html:text property="guarantorsName4" size="20" alt="guarantorsName4" name="appForm" maxlength="100"/>
													<%}%>
													</span></b>
													
												</td>
												<td width="17%" class="ColumnBackground" >
													<b><span style="font-size: 9pt"><bean:message key="guarantorsNetWorth" /></span></b>
												</td>
												<td width="17%" class="TableData">
												
													<b><span style="font-size: 9pt"> 

													<% if(appCommonFlag.equals("11") || appCommonFlag.equals("12") || appCommonFlag.equals("13"))
													 {
													%>
													<bean:write property="guarantorsNetWorth4" name="appForm"/>&nbsp;<bean:message key="inRs" />
													<%}
													else {
													%>															
													<html:text property="guarantorsNetWorth4" size="20" alt="guarantorsNetWorth4" name="appForm" maxlength="16" onkeypress="return decimalOnly(this, event,13)" onkeyup="isValidNumber(this)"/>&nbsp;<bean:message key="inRs" />
													<%}%>
													</span></b>
													
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

									<% if(appCommonFlag.equals("11") || appCommonFlag.equals("12") || appCommonFlag.equals("13"))
									 {
									%>
									<bean:write property="subsidyName" name="appForm"/>
									<%}
									else {
									%>	
									
										<html:select property="subsidyName" name="appForm" onchange="enableSubsidyName()">
											<html:option value="">Select</html:option>
											<html:option value="PMRY">PMRY</html:option>
											<html:option value="SJRY">SJRY</html:option>
											<html:option value="Others">Others</html:option>
										</html:select>
									<%}%>

									<% if(appCommonFlag.equals("11") || appCommonFlag.equals("12") || appCommonFlag.equals("13"))
									 {
									%>
									<bean:write property="otherSubsidyEquityName" name="appForm"/>
									<%}
									else {
									%>										
										<html:text property="otherSubsidyEquityName" size="20" alt="subsidyName" name="appForm" maxlength="50"/>
									<%}%>
									
										
									</td>
								</tr>
								<tr align="left"> 
									<TD class="SubHeading">
										<bean:message key="primarySecurity" />
									</TD>
								</tr>
								<tr align="left">
									<TD align="center" valign="top" class="ColumnBackground" >
										<div align="center">
										<bean:message key="securityName"/></div>
									</td>
									<TD align="center" valign="top" colspan="2" class="ColumnBackground" ><div align="center">
										<bean:message key="particulars"/></div>
									</td>
									<TD align="center" valign="top" class="ColumnBackground" > 
										<div align="center">
										<bean:message key="valueInRs"/><div>
									</td>
								</tr>	
								<tr align="left">
									<TD align="center" valign="top" class="TableData" >
										<div align="center">
										<bean:message key="land"/></div>
									</td>
									<TD align="center" valign="top" class="TableData" colspan="2"> 
										<div align="center">

										<% if(appCommonFlag.equals("11") || appCommonFlag.equals("12") || appCommonFlag.equals("13"))
										 {
										%>
										<bean:write property="landParticulars" name="appForm"/>
										<%}
										else {
										%>		
										
										<html:text property="landParticulars" size="20" alt="landPart" name="appForm" maxlength="4000"/>
										<%}%>
										</div>
										
									</td>
									<TD align="center" valign="top" class="TableData">
										<div align="center">

										<% if(appCommonFlag.equals("11") || appCommonFlag.equals("12") || appCommonFlag.equals("13"))
										 {
										%>
										<bean:write property="landValue" name="appForm"/>
										<%}
										else {
										%>	
										
										<html:text property="landValue" size="20" alt="landValue" name="appForm" maxlength="16" onkeypress="return decimalOnly(this, event,13)" onkeyup="isValidDecimal(this)" onblur="javascript:calculatePsTotal()"/>
										<%}%>
										</div>
										
									</td>
								</tr>
								<tr align="left">
									<TD align="center" valign="top" class="TableData" >
										<div align="center">
										<bean:message key="bldg"/></div>
									</td>
									<TD align="center" valign="top" class="TableData" colspan="2"> 
										<div align="center">

										<% if(appCommonFlag.equals("11") || appCommonFlag.equals("12") || appCommonFlag.equals("13"))
										 {
										%>
										<bean:write property="bldgParticulars" name="appForm"/>
										<%}
										else {
										%>											
										<html:text property="bldgParticulars" size="20" alt="bldgPart" name="appForm" maxlength="4000"/>
										<%}%>
										</div>
										
									</td>
									<TD align="center" valign="top" class="TableData" >
										<div align="center">

										<% if(appCommonFlag.equals("11") || appCommonFlag.equals("12") || appCommonFlag.equals("13"))
										 {
										%>
										<bean:write property="bldgValue" name="appForm"/>
										<%}
										else {
										%>	
										
										<html:text property="bldgValue" size="20" alt="bldgValue" name="appForm" maxlength="16" onkeypress="return decimalOnly(this, event,13)" onkeyup="isValidDecimal(this)" onblur="javascript:calculatePsTotal()"/>
										<%}%>
										</div>
										
									</td>
								</tr>
								<tr align="left">
									<TD align="center" valign="top" class="TableData" >
										<div align="center">
										<bean:message key="machine"/></div>
									</td>
									<TD align="center" valign="top" class="TableData" colspan="2">
										<div align="center">

										<% if(appCommonFlag.equals("11") || appCommonFlag.equals("12") || appCommonFlag.equals("13"))
										 {
										%>
										<bean:write property="machineParticulars" name="appForm"/>
										<%}
										else {
										%>	
										
										<html:text property="machineParticulars" size="20" alt="machinePart" name="appForm" maxlength="400"/>
										<%}%>
										</div>
										
									</td>
									<TD align="center" valign="top" class="TableData">
										<div align="center">

										<% if(appCommonFlag.equals("11") || appCommonFlag.equals("12") || appCommonFlag.equals("13"))
										 {
										%>
										<bean:write property="machineValue" name="appForm"/>
										<%}
										else {
										%>	
										
										<html:text property="machineValue" size="20" alt="machineValue" name="appForm" maxlength="16" onkeypress="return decimalOnly(this, event,13)" onkeyup="isValidDecimal(this)" onblur="javascript:calculatePsTotal()"/>
										<%}%>
										</div>
										
									</td>
								</tr>
								<tr align="left">
									<TD align="center" valign="top" class="TableData" >
										<div align="center">
										<bean:message key="assets"/></div>
									</td>
									<TD align="center" valign="top" class="TableData" colspan="2"> 
										<div align="center">

										<% if(appCommonFlag.equals("11") || appCommonFlag.equals("12") || appCommonFlag.equals("13"))
										 {
										%>
										<bean:write property="assetsParticulars" name="appForm"/>
										<%}
										else {
										%>	
										
										<html:text property="assetsParticulars" size="20" alt="assetsPart" name="appForm" maxlength="400"/>
										<%}%>
										</div>

									</td>
									<TD align="center" valign="top" class="TableData" >
										<div align="center">

										<% if(appCommonFlag.equals("11") || appCommonFlag.equals("12") || appCommonFlag.equals("13"))
										 {
										%>
										<bean:write property="assetsValue" name="appForm"/>
										<%}
										else {
										%>	
										
										<html:text property="assetsValue" size="20" alt="assetsValue" name="appForm" maxlength="16" onkeypress="return decimalOnly(this, event,13)" onkeyup="isValidDecimal(this)" onblur="javascript:calculatePsTotal()"/>
										<%}%>
										</div>
										
									</td>
								</tr>
								<tr align="left">
									<TD align="center" valign="top" class="TableData" >
										<div align="center">
										<bean:message key="currentAssets"/></div>
									</td>
									<TD align="center" valign="top" class="TableData" colspan="2">
										<div align="center">

										<% if(appCommonFlag.equals("11") || appCommonFlag.equals("12") || appCommonFlag.equals("13"))
										 {
										%>
										<bean:write property="currentAssetsParticulars" name="appForm"/>
										<%}
										else {
										%>	
										
										<html:text property="currentAssetsParticulars" size="20" alt="currentAssetsPart" name="appForm" maxlength="400"/>
										<%}%>
										</div>
										
									</td>
									<TD align="center" valign="top" class="TableData" >
										<div align="center">

										<% if(appCommonFlag.equals("11") || appCommonFlag.equals("12") || appCommonFlag.equals("13"))
										 {
										%>
										<bean:write property="currentAssetsValue" name="appForm"/>
										<%}
										else {
										%>										
										
										<html:text property="currentAssetsValue" size="20" alt="currentAssetsValue" name="appForm" maxlength="16" onkeypress="return decimalOnly(this, event,13)" onkeyup="isValidDecimal(this)" onblur="javascript:calculatePsTotal()"/>
										<%}%>
										</div>
										
									</td>
								</tr>
								<tr align="left">
									<TD align="center" valign="top" class="TableData" >
									<div align="center">
										<bean:message key="psOthers"/></div>
									</td>
									<TD align="center" valign="top" class="TableData" colspan="2"> 
										<div align="center">

										<% if(appCommonFlag.equals("11") || appCommonFlag.equals("12") || appCommonFlag.equals("13"))
										 {
										%>
										<bean:write property="othersParticulars" name="appForm"/>
										<%}
										else {
										%>	
										
										<html:text property="othersParticulars" size="20" alt="othersPart" name="appForm" maxlength="400"/>
										<%}%>
										</div>
										
									</td>
									<TD align="center" valign="top" class="TableData" >
										<div align="center">

										<% if(appCommonFlag.equals("11") || appCommonFlag.equals("12") || appCommonFlag.equals("13"))
										 {
										%>
										<bean:write property="othersValue" name="appForm"/>
										<%}
										else {
										%>	
										
										<html:text property="othersValue" size="20" alt="othersValue" name="appForm" maxlength="16" onkeypress="return decimalOnly(this, event,13)" onkeyup="isValidDecimal(this)" onblur="javascript:calculatePsTotal()"/>
										<%}%>
										</div>
										
									</td>
								</tr>	
								<tr align="left">
									<TD align="right" valign="center" class="TableData" colspan="3"><div align="center">
										<bean:message key="psTotalWorth"/></div>
									</td>									
									<TD align="center" valign="top" class="TableData" id="psTotalWorth">
									<%													if((appCommonFlag.equals("0"))||(appCommonFlag.equals("1"))||(appCommonFlag.equals("2"))||(appCommonFlag.equals("3"))||(appCommonFlag.equals("4"))||(appCommonFlag.equals("5"))||(appCommonFlag.equals("6")) || (appCommonFlag.equals("11"))||(appCommonFlag.equals("12"))||(appCommonFlag.equals("13")))

									{
									%>
									<bean:write name="appForm" property="psTotal"/>
									<%}
									else
									{ %>

									<bean:message key="inRs"/>
									<%}%>					
									</td>
									
								</tr>
								
								<tr align="left"> 
									<td colspan="8" class="SubHeading" height="28" width="843"><br> &nbsp;
										<bean:message key="meansOfFinance" />
									</td>
								</tr>
								<tr valign="top"> 
									<td colspan="4">
										<table width="100%" border="0" align="right" cellpadding="0" cellspacing="1">
											<tr> 
												<td class="ColumnBackground" width="25%" align="left" valign="top" >
												<% if(appCommonFlag.equals("7")|| appCommonFlag.equals("8")|| appCommonFlag.equals("10")|| appCommonFlag.equals("0")|| appCommonFlag.equals("3")|| appCommonFlag.equals("5")|| appCommonFlag.equals("11")|| appCommonFlag.equals("13")||appCommonFlag.equals("14")||appCommonFlag.equals("19"))
												{
												%>
												<font color="#FF0000" size="2">*</font>
													<bean:message key="tcSanctioned" />
												<%
												}
												else
												{
												%>
												<bean:message key="tcSanctioned" />
												<%}%>
												</td>
												<td class="TableData" width="20%" align="left" valign="top" id="tcSanctioned">

												<% if(appCommonFlag.equals("11") || appCommonFlag.equals("12") || appCommonFlag.equals("13"))
												 {
												%>
												<bean:write property="termCreditSanctioned" name="appForm"/>
												<%}
												else {
												%>									
												
													<html:text property="termCreditSanctioned" size="20" alt="tcSanctioned" name="appForm" maxlength="16" onchange="calltotalAmt()" onblur="javascript:calProjectOutlay()" onkeypress="return decimalOnly(this, event,13)" onkeyup="isValidDecimal(this)"/>
												<%}%>
												
													
													<bean:message key="inRs" />
												</td>
												<td class="ColumnBackground" colspan="2" align="left" valign="top" >
													<table width="100%" border="0" align="right" cellpadding="0" cellspacing="1">
														<tr align="left">
															<td class="ColumnBackground" colspan="2" align="center">
															<% if(appCommonFlag.equals("9")|| appCommonFlag.equals("8")|| appCommonFlag.equals("10")|| appCommonFlag.equals("2")|| appCommonFlag.equals("4")|| appCommonFlag.equals("6")|| appCommonFlag.equals("12")|| appCommonFlag.equals("13")||appCommonFlag.equals("18")||appCommonFlag.equals("19"))
															{
															%>
															<font color="#FF0000" size="2">*</font>
																<bean:message key="wcLimitSanctioned"/>
															<%}
															else if(appCommonFlag.equals("1"))
															{%>
															<font color="#FF0000" size="2">*</font>
																<bean:message key="wcEnhanceLimitSanctioned"/>
															<%}
															else
															{%>
															<bean:message key="wcLimitSanctioned"/>
															<%}%>
															</td>
														</tr>							
														<tr align="left">
															<td class="ColumnBackground" align="left" valign="top" >
																<bean:message key="wcFundBased"/>
															</td>
															<td class="TableData" align="left" valign="top" id="wcFBsanctioned">

															<% if(appCommonFlag.equals("11") || appCommonFlag.equals("12") || appCommonFlag.equals("13"))
															 {
															%>
															<bean:write property="wcFundBasedSanctioned" name="appForm"/>
															<%}
															else {
															%>		
															
																<html:text property="wcFundBasedSanctioned" size="20" alt="wcFundBased" name="appForm" maxlength="16" onchange="calltotalAmt()" onblur="javascript:calProjectOutlay()" onkeypress="return decimalOnly(this, event,13)" onkeyup="isValidDecimal(this)"/>
															<%}%>
															
														
																<bean:message key="inRs"/>
															</td>						
														</tr>
														<tr align="left">
															<td class="ColumnBackground" align="left" valign="top" >
																<bean:message key="wcNonFundBased"/>
															</td>
															<td class="TableData" align="left" valign="top"  id="wcNFBsanctioned">

															<% if(appCommonFlag.equals("11") || appCommonFlag.equals("12") || appCommonFlag.equals("13"))
															 {
															%>
															<bean:write property="wcNonFundBasedSanctioned" name="appForm"/>
															<%}
															else {
															%>		
															
																<html:text property="wcNonFundBasedSanctioned" size="20" alt="wcFundBased" name="appForm" maxlength="16" onchange="calltotalAmt()" onblur="javascript:calProjectOutlay()" onkeypress="return decimalOnly(this, event,13)" onkeyup="isValidDecimal(this)"/>
															<%}%>

															
																<bean:message key="inRs"/>
															</td>						
														</tr>	
														<tr align="left">
															<td class="ColumnBackground" align="left" valign="top" width="50%">
																<bean:message key="marginMoneyAsTL"/>
															</td>
															<td class="TableData" align="left" valign="top" >
															<% if(appCommonFlag.equals("11") || appCommonFlag.equals("12") || appCommonFlag.equals("13"))
															 {
															%>
<!--						<bean:write property="marginMoneyAsTL" name="appForm"/>-->
																<html:radio name="appForm" value="Y" property="marginMoneyAsTL" disabled="true"></html:radio>
											
																<bean:message key="yes" />&nbsp;&nbsp;
																
																<html:radio name="appForm" value="N" property="marginMoneyAsTL" disabled="true"></html:radio>
																
																<bean:message key="no"/>

															<%}
															else {
															%>		
															
																<html:radio name="appForm" value="Y" property="marginMoneyAsTL" onclick="javascript:calProjectOutlay()" ></html:radio>
											
																<bean:message key="yes" />&nbsp;&nbsp;
																
																<html:radio name="appForm" value="N" property="marginMoneyAsTL" onclick="javascript:calProjectOutlay()" ></html:radio>
																
																<bean:message key="no"/>
															<%}%>
															
															</td>
														</tr>
														
													</table>
												</td>						 
											</tr>
											<tr align="left"> 
												<td class="ColumnBackground" width="25%">
													<bean:message key="tcPromoterContribution"/>
												</td>
												<td class="TableData" width="20%" align="left" valign="top" id="tcCont">

												<% if(appCommonFlag.equals("11") || appCommonFlag.equals("12") || appCommonFlag.equals("13"))
												 {
												%>
												<bean:write property="tcPromoterContribution" name="appForm"/>
												<%}
												else {
												%>	
												
													<html:text property="tcPromoterContribution" size="20" alt="promotersCont" name="appForm" maxlength="16" onblur="javascript:calProjectOutlay()" onkeypress="return decimalOnly(this, event,13)" onkeyup="isValidDecimal(this)"/>
												<%}%>
												
												
												  <bean:message key="inRs"/>
												</td>
												<td class="ColumnBackground" width="25%">&nbsp;
													<bean:message key="wcPromoterContribution"/>
												</td>
												<td class="TableData" width="20%" align="left" valign="top" id="wcCont">

												<% if(appCommonFlag.equals("11") || appCommonFlag.equals("12") || appCommonFlag.equals("13"))
												 {
												%>
												<bean:write property="wcPromoterContribution" name="appForm"/>
												<%}
												else {
												%>										
												
													<html:text property="wcPromoterContribution" size="20" alt="wcPromoterContribution" name="appForm" maxlength="16" onblur="javascript:calProjectOutlay()" onkeypress="return decimalOnly(this, event,13)" onkeyup="isValidDecimal(this)"/>
												<%}%>
												
													
												  <bean:message key="inRs"/>
												</td>
											</tr>
											<tr align="left"> 
												<td class="ColumnBackground" width="25%">&nbsp;
													<bean:message key="tcSubsidyOrEquity"/>
												</td>
												<td class="TableData" width="20%" align="left" valign="top" id="tcSubsidy">

												<% if(appCommonFlag.equals("11") || appCommonFlag.equals("12") || appCommonFlag.equals("13"))
												 {
												%>
												<bean:write property="tcSubsidyOrEquity" name="appForm"/>
												<%}
												else {
												%>											
												
													<html:text property="tcSubsidyOrEquity" size="20" alt="tcSubsidyOrEquity" name="appForm" maxlength="16" onblur="javascript:calProjectOutlay()" onkeypress="return decimalOnly(this, event,13)" onkeyup="isValidDecimal(this)"/>
												<%}%>		
											
												  <bean:message key="inRs"/>
												</td>
												<td class="ColumnBackground" width="25%">&nbsp;
													<bean:message key="wcSubsidyOrEquity"/>
												</td>
												<td class="TableData" width="20%" align="left" valign="top" id="wcSubsidy">
												<% if(appCommonFlag.equals("11") || appCommonFlag.equals("12") || appCommonFlag.equals("13"))
												 {
												%>
												<bean:write property="wcSubsidyOrEquity" name="appForm"/>
												<%}
												else {
												%>												
												
													<html:text property="wcSubsidyOrEquity" size="20" alt="wcSubsidyOrEquity" name="appForm" maxlength="16" onblur="javascript:calProjectOutlay()" onkeypress="return decimalOnly(this, event,13)" onkeyup="isValidDecimal(this)"/>
												<%}%>												
													
												  <bean:message key="inRs"/>
												</td>
											</tr>
											<tr align="left"> 
												<td class="ColumnBackground" width="25%">&nbsp;
													<bean:message key="tcOthers"/>
												</td>
												<td class="TableData" width="20%" align="left" valign="top" id="tcOther">

												<% if(appCommonFlag.equals("11") || appCommonFlag.equals("12") || appCommonFlag.equals("13"))
												 {
												%>
												<bean:write property="tcOthers" name="appForm"/>
												<%}
												else {
												%>	
												
													<html:text property="tcOthers" size="20" alt="tcOthers" name="appForm" maxlength="16" onblur="javascript:calProjectOutlay()" onkeypress="return decimalOnly(this, event,13)" onkeyup="isValidDecimal(this)"/>
												<%}%>												
													
												  <bean:message key="inRs"/>
												</td>
												<td class="ColumnBackground" width="25%">&nbsp;
													<bean:message key="wcOthers"/>
												</td>
												<td class="TableData" width="20%" align="left" valign="top" id="wcOther">

												<% if(appCommonFlag.equals("11") || appCommonFlag.equals("12") || appCommonFlag.equals("13"))
												 {
												%>
												<bean:write property="wcOthers" name="appForm"/>
												<%}
												else {
												%>												
												
													<html:text property="wcOthers" size="20" alt="wcOthers" name="appForm" maxlength="16" onblur="javascript:calProjectOutlay()" onkeypress="return decimalOnly(this, event,13)" onkeyup="isValidDecimal(this)"/>
												<%}%>							
													
												  <bean:message key="inRs"/>
												</td>
											</tr>												
											<TR align="left">
												<td class="ColumnBackground" width="25%">&nbsp; 
													<bean:message key="projectCost"/>
												</td>
												<td class="TableData" width="20%" align="left" valign="top" id="projectCost">
												<%	
												if((appCommonFlag.equals("0"))||(appCommonFlag.equals("1"))||(appCommonFlag.equals("2"))||(appCommonFlag.equals("3"))||(appCommonFlag.equals("4"))||(appCommonFlag.equals("5"))|| appCommonFlag.equals("6") || appCommonFlag.equals("11") || appCommonFlag.equals("12") || appCommonFlag.equals("13"))

													{										
														
													%>
													
													<bean:write name="appForm" property="projectCost"/>												
													<%}
													else
													{ 
														%>	
													<bean:write name="appForm" property="projectCost"/>

													<bean:message key="inRs"/>
													<%}%>
												</td>
												<td class="ColumnBackground" width="25%">&nbsp;
													<bean:message key="wcAssessed"/>
												</td>
												<td class="TableData" width="20%" align="left" valign="top" id="wcAssessed">
												<%
													if((appCommonFlag.equals("0"))||(appCommonFlag.equals("1"))||(appCommonFlag.equals("2"))||(appCommonFlag.equals("3"))||(appCommonFlag.equals("4"))||(appCommonFlag.equals("5"))|| appCommonFlag.equals("6") || appCommonFlag.equals("11") || appCommonFlag.equals("12") || appCommonFlag.equals("13"))

													{
													%>
													<bean:write name="appForm" property="wcAssessed"/>
													<%}
													else
													{ %>
												
													<bean:message key="inRs"/>
													<%}%>
												</td>
											</tr>
											<tr align="left"> 
												<td class="ColumnBackground" colspan="2" style="text-align: center" >
													<bean:message key="projectOutlay"/>
												</td>
												<td class="ColumnBackground" colspan="1" id="projectOutlay">	
												<%													if((appCommonFlag.equals("0"))||(appCommonFlag.equals("1"))||(appCommonFlag.equals("2"))||(appCommonFlag.equals("3"))||(appCommonFlag.equals("4"))||(appCommonFlag.equals("5"))||(appCommonFlag.equals("6")) || appCommonFlag.equals("11") || appCommonFlag.equals("12") || appCommonFlag.equals("13"))

													{
													%>
													<bean:write name="appForm" property="projectOutlay"/>
													<%}%>
												</td>
												<td class="ColumnBackground" colspan="1">
												<bean:message key="inRs"/>
												<html:hidden property="projectOutlay" name="appForm"/>
												</td>
											</tr>
											<html:hidden property="status" name="appForm"/>											
				
															
										</table>					
									</TD>
								</TR>

									


							</table>
							










