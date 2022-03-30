<%@ page language="java"%>
<%@ taglib uri="/WEB-INF/TLD/struts-html.tld" prefix="html" %>
<%@ taglib uri="/WEB-INF/TLD/struts-bean.tld" prefix="bean" %>
<%@ include file="/jsp/SetMenuInfo.jsp" %>
<%@ page import="com.cgtsi.util.SessionConstants"%>
<%@ page import="com.cgtsi.actionform.GMActionForm"%>
<%@ page import="com.cgtsi.application.BorrowerDetails"%>
<%
String focusField="";

if(request.getAttribute(SessionConstants.GM_FOCUS_FIELD)!=null && 											request.getAttribute(SessionConstants.GM_FOCUS_FIELD).equals("GMDistrict"))
{
	focusField="district";
}
else if(request.getAttribute(SessionConstants.GM_FOCUS_FIELD)!=null && 											request.getAttribute(SessionConstants.GM_FOCUS_FIELD).equals("GMSector"))
{
	focusField="industrySector";
}else
{
	focusField="osAmt";
}

org.apache.struts.action.ActionErrors errors = (org.apache.struts.action.ActionErrors)request.getAttribute(org.apache.struts.Globals.ERROR_KEY);
if (errors!=null && !errors.isEmpty())
{
	focusField="test";
}

%>

     
        
<% //session.setAttribute("CurrentPage","modifyBorrowerDetails.do?method=modifyBorrowerDetails");
session.setAttribute("CurrentPage","saveBorrowerDetails.do?method=updateBorrowerDetails"); %>
<body onLoad="enableGender(),setConstEnabled(),enableDistrictOthers()"/>

	<html:form action="saveBorrowerDetails.do?method=updateBorrowerDetails" focus="<%=focusField%>">
	<html:hidden name="gmForm" property="test"/>
		<html:errors />
		
	 	
	<TABLE width="725" border="0" cellpadding="0" cellspacing="0">
		<TR> 
			<TD width="20" align="right" valign="bottom"><IMG src="images/TableLeftTop.gif" width="20" height="31"></TD>
			<TD background="images/TableBackground1.gif"><IMG src="images/GuaranteeMaintenanceHeading.gif"></TD>
			<TD width="20" align="left" valign="bottom"><IMG src="images/TableRightTop.gif" width="23" height="31"></TD>
		</TR>
		<TR>
			<TD width="20" background="images/TableVerticalLeftBG.gif">&nbsp;</TD>
			<TD>
						<DIV align="right">			
				<A HREF="javascript:submitForm('helpBorrowerDetails.do?method=helpBorrowerDetails')">
			    HELP</A>
			</DIV>

				<TABLE width="100%" border="0" cellspacing="1" cellpadding="1">
					<TR> 
						<TD colspan="7">
							<TABLE width="100%" border="0" cellpadding="0" cellspacing="0">
								<TR>
									<TD width="30%" class="Heading"><bean:message key="gmborrowerHeader" /><bean:write name = "gmForm" property ="borrowerIdForModifyBorrDtl"/> </TD>
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
							<html:radio disabled="true" name="gmForm" value="Y" property="assistedByBank" ><bean:message key="yes" /></html:radio>
							&nbsp;&nbsp;<html:radio disabled="true"  name="gmForm" value="N" property="assistedByBank"><bean:message key="no"/></html:radio>
						</TD>								
					</TR>
					<TR align="left">
						<TD align="left" valign="top" class="ColumnBackground" colspan="3"><bean:message key="osAmt"/></td>								
						<TD align="left" valign="top" class="TableData" colspan="4"><html:text property="osAmt" disabled="true" size="20" alt="Outstanding Amount" name="gmForm" onkeypress="return decimalOnly(this, event,13)" onkeyup="isValidDecimal(this)" maxlength="16"/>
							<bean:message key="inRs"/>
						</TD>
					 </TR>
					 <TR align="left">
						<TD align="left" valign="top" class="ColumnBackground" colspan="3">
							<bean:message key="npa" />
						</TD>
						<TD align="left" class="TableData" colspan="4"> 
							<html:radio name="gmForm" value="Y" property="npa" disabled="true" ><bean:message key="yes" /></html:radio>
							&nbsp;&nbsp;<html:radio name="gmForm" value="N" property="npa" disabled="true"><bean:message key="no"/></html:radio>
						</TD>								
					</TR>

					<TR align="left">
						<TD align="left" valign="top" class="ColumnBackground" colspan="3">
							<bean:message key="coveredByCGTSI" />
						</TD>
						<TD align="left" class="TableData" colspan="4"> 
							<html:radio  disabled="true" name="gmForm" value="Y" property="previouslyCovered" ><bean:message key="yes" /></html:radio>
							&nbsp;&nbsp;<html:radio disabled="true" name="gmForm" value="N" property="previouslyCovered"><bean:message key="no"/></html:radio>
						</TD>								
					</TR>
					<TR align="left">
						<TD align="left" valign="top" class="ColumnBackground" colspan="3">
							<bean:message key="branchName" />
						</TD>
						<TD align="left" valign="top" class="TableData" colspan="4"><html:text property="branchName" size="20" alt="Outstanding Amount" name="gmForm" maxlength="100"/>
							
						</TD>							
					</TR>
<%--					 <TR align="left">
						<TD align="left" valign="top" class="ColumnBackground" colspan="3"><bean:message key="acNo"/></td>								
						<TD align="left" valign="top" class="TableData" colspan="4"><html:text property="acNo" size="20" alt="uniqueAcctNo" name="gmForm"/>	
						</TD>
					 </TR>
--%>

					<TR>
						<TD colspan="7">
							<TABLE width="100%" border="0" cellpadding="1" cellspacing="1">
								 <TR>
									<TD class="SubHeading"><bean:message key="unitHeader" /></TD>	</TR>
										
								 <TR align="left">
									<TD align="left" valign="top" class="ColumnBackground" width="30%"><font color="#FF0000" size="2">*</font><bean:message key="constitution"/>
									</TD>
									<TD align="left" valign="top" class="TableData" colspan="5">
										<html:select property="constitution" disabled="false" name="gmForm" onchange="setConstEnabled()">
											<html:option value="">Select</html:option>
											<html:option value="proprietary"><bean:message key="proprietary" /></html:option>
											<html:option value="partnership"><bean:message key="partnership" /></html:option>
											<html:option value="partnershipLimited"><bean:message key="limitedPartnership" /></html:option>
											<html:option value="private"><bean:message key="private" /></html:option>
											<html:option value="public"><bean:message key="public" /></html:option>
											<html:option value="HUF"><bean:message key="huf" /></html:option>
											<html:option value="Trust"><bean:message key="trust" /></html:option>
											<html:option value="CoopSociety"><bean:message key="coSociety" /></html:option>
											<html:option value="Others"><bean:message key="others" /></html:option>
										</html:select>&nbsp;&nbsp;&nbsp;&nbsp;		
									
									<html:text property="constitutionOther"  disabled="true" size="15" alt="constitution" name="gmForm" maxlength="50"/>
									</TD>
								 </TR>
											
								 <TR align="left">
									<TD align="left" valign="top" class="ColumnBackground" width="30%"><font color="#FF0000" size="2">*</font><bean:message key="unitName"/>
									</TD>												
									<TD align="left" valign="top" class="TableData">
										<html:select property="ssiType" name="gmForm" disabled="false">
										<html:option value="">Select</html:option>
										<html:option value="M/s">M/s</html:option>
										<html:option value="Shri">Shri</html:option>
										<html:option value="Smt">Smt</html:option>
										<html:option value="Ku">Ku</html:option>
										</html:select>	
										<html:text property="ssiName" size="20" alt="unitName" name="gmForm" maxlength="100" disabled="false"/>	
									 </td>
									<TD align="left" valign="top" class="ColumnBackground" width="25%"><bean:message key="ssiRegNo"/>
									</td>
									<TD align="left" valign="top" class="TableData" width="15%"><html:text property="regNo" size="20" alt="SSI Reg No" name="gmForm" maxlength="25" disabled="true"/>				
									</td>
								</tr>
								 <TR align="left">
<%--								<TD align="left" valign="top" class="ColumnBackground" 													width="30%"><bean:message key="commencementDate"/>
									</TD>
									
									<TD align="left" valign="top" class="TableData">
									<html:text property="commencementDate" size="20" alt="Commencement Date" name="gmForm"/><IMG src="images/CalendarIcon.gif" width="20" onClick="showCalendar('gmForm.commencementDate')" align="center">
									 </td>
--%>
									<TD align="left" valign="top" class="ColumnBackground" width="25%"><bean:message key="firmItpan"/>
									</td>
									<TD align="left" valign="top" class="TableData" width="15%" colspan="4"><html:text property="ssiITPan" size="20" alt="ITPAN" name="gmForm" maxlength="10"/>	
									</td>
								</tr>
								 <TR align="left">
									<TD align="left" valign="top" class="ColumnBackground" width="30%"><font color="#FF0000" size="2">*</font><bean:message key="industryNature"/>
									</TD>												
									<TD align="left" valign="top" class="TableData">
										<html:select property="industryNature" disabled="true" onchange="javascript:submitForm('getIndustryValues.do?method=getIndustrySectors')" name="gmForm">
										<html:option value="">Select</html:option>
										<html:options property ="industryNatureList" name ="gmForm"/>
										</html:select>									
									</td>
									<TD align="left" valign="top" class="ColumnBackground" width="25%"><font color="#FF0000" size="2">*</font><bean:message key="industrySector"/>
									</td>
									<TD align="left" valign="top" class="TableData">
										<html:select property="industrySector" name="gmForm" disabled="true" >
										<html:option value="">Select</html:option>
										<html:options property="industrySectors"/>
										<html:option value="Others">Others</html:option>
										</html:select>									
									</td>
								</tr>

								<TR align="left">
									<TD align="left" valign="top" class="ColumnBackground" width="30%"><font color="#FF0000" size="2">*</font><bean:message key="activitytype"/>
									</TD>												
									<TD align="left" valign="top" class="TableData">
									<html:text property="activityType" size="20" alt="Activity Type" name="gmForm" maxlength="50" disabled="true"/>				
									</td>
									<TD align="left" valign="top" class="ColumnBackground" width="25%"><bean:message key="noOfEmployees"/>
									</td>
									<TD align="left" valign="top" class="TableData" width="15%"><html:text property="employeeNos" onkeypress="return numbersOnly(this, event)" onkeyup="isValidNumber(this)" size="20" alt="No Of employees" name="gmForm" disabled="true" />	
									</td>
								</tr>
     							 <TR align="left">
									<TD align="left" valign="top" class="ColumnBackground" width="30%"><bean:message key="turnover"/>
									</TD>												
									<TD align="left" valign="top" class="TableData">
										<html:text property="projectedSalesTurnover" onkeypress="return decimalOnly(this, event,13)" onkeyup="isValidDecimal(this)" size="20" alt="turnover" name="gmForm" maxlength="16" disabled="true"  /><bean:message key="inRs"/>
									</td>
									<TD align="left" valign="top" class="ColumnBackground" width="25%"><bean:message key="exports"/>
									</td>
									<TD align="left" valign="top" class="TableData" width="15%">
										<html:text property="projectedExports" onkeypress="return decimalOnly(this, event,13)" onkeyup="isValidDecimal(this)" size="10" alt="exports" name="gmForm" maxlength="16" disabled="true" /><bean:message key="inRs"/>
										</td>
									</tr>

									<TR align="left">											
										<TD align="left" valign="top" class="ColumnBackground" width="30%"><font color="#FF0000" size="2">*</font><bean:message key="unitAddress"/>
										</td>
										<TD align="left" valign="top" class="TableData" width="30%">
											<html:textarea property="address" cols="20" alt="address" name="gmForm" disabled="false"/>						
										</td>
										<TD align="left" valign="top" class="ColumnBackground" width="25%"><font color="#FF0000" size="2">*</font><bean:message key="state"/>
										</TD>												
										<TD align="left" valign="top" class="TableData" width="15%">
											<html:select property="state" disabled="false" name="gmForm" onchange="javascript:submitForm('getDistrictValues.do?method=getDistricts')">
											<html:option value="">Select</html:option>
											<html:options property = "states" name="gmForm"/>
											</html:select>										
										</td>
									</tr>
									 <TR align="left">
										<TD align="left" valign="top" class="ColumnBackground" width="15%"><font color="#FF0000" size="2">*</font><bean:message key="district"/>
										</TD>												
										<TD align="left" valign="top" class="TableData" >
										<html:select property="district" disabled="false" name="gmForm" onchange="javascript:enableDistrictOthers()">
										<html:option value="">Select</html:option>
										<html:options property = "districts" name = "gmForm"/>
										<html:option value="Others">Others</html:option>
										</html:select>
										<html:text property="districtOthers" size="10" alt="district" name="gmForm" disabled="true" maxlength="100"/><bean:message key="others"/>		
										</td>
										<TD align="left" valign="top" class="ColumnBackground" width="25%"><font color="#FF0000" size="2">*</font><bean:message key="city"/>
										</td>
										<TD align="left" valign="top" class="TableData" width="15%">
										<html:text property="city" size="20" alt="city" name="gmForm" maxlength="100"/>	</td>
									</tr>

									<TR align="left">
										<TD align="left" valign="top" class="ColumnBackground" width="30%"><font color="#FF0000" size="2">*</font><bean:message key="pinCode"/>
										</td>
										<TD align="left" valign="top" class="TableData" width="70%" colspan="5">
										<html:text property="pincode" size="20" alt="pinCode" name="gmForm" maxlength = "6"/>		
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
										<font color="#FF0000" size="2">*</font><bean:message key="title"/>
							</TD>
							<TD align="left" valign="top" class="ColumnBackground" width="25%">
										<font color="#FF0000" size="2">*</font><bean:message key="firstName"/>
							</TD>
							<TD align="left" valign="top" class="ColumnBackground" width="25%">
										<bean:message key="middleName"/>	
							</TD>
							<TD align="left" valign="top" class="ColumnBackground" width="25%">
										<font color="#FF0000" size="2">*</font><bean:message key="lastName"/>
							</TD>
						</TR>

						<TR align="left">
							<TD align="left" valign="top" class="ColumnBackground" colspan="2" width="15%"><bean:message key="name"/>
							</TD>		
							<TD align="left" valign="top" class="TableData">
								<html:select property="cpTitle" name="gmForm" onchange="enableGender()" disabled="false">
										<html:option value="">Select</html:option>
										<html:option value="Mr.">Mr</html:option>
										<html:option value="Smt">Smt</html:option>
										<html:option value="Ku">Ku</html:option>
								</html:select>
							</TD>
							<TD align="left" valign="top" class="TableData">
								<html:text property="cpFirstName" size="20" alt="firstName" name="gmForm" maxlength="20" disabled="false"/>	
							</TD>
							<TD align="left" valign="top" class="TableData">
								<html:text property="cpMiddleName" size="20" alt="middleName" name="gmForm" maxlength="20" disabled="false"/>	
							</TD>
							<TD align="left" valign="top" class="TableData">
								<html:text property="cpLastName" size="20" alt="lastName" name="gmForm" maxlength="20" disabled="false"/>	
							</TD>
						</TR>

						<TR align="left">
							<TD align="left" valign="top" class="ColumnBackground" colspan="2"><font color="#FF0000" size="2">*</font><bean:message key="gender"/>
							</TD>
							<TD align="left" valign="top" class="TableData" width="20%">
								<html:radio name="gmForm" value="M" property="cpGender" ><bean:message key="male" /></html:radio>&nbsp;&nbsp;&nbsp;
								<html:radio name="gmForm" value="F" property="cpGender" ><bean:message key="female" /></html:radio>
							</TD>
							<TD align="left" valign="top" class="ColumnBackground">
								<bean:message key="chiefItpan" />
							</TD>
							<TD align="left" valign="top" class="TableData" colspan="2">
						
								<html:text property="cpITPAN" size="20" alt="chiefItpan" name="gmForm" maxlength="10" disabled="false"/>
								
							</TD>													
						</TR>

						<TR align="left">
							<TD align="left" valign="top" class="ColumnBackground" colspan="2">
										<bean:message key="dob" />					
							</TD>
							<TD align="left" valign="top" class="TableData">
								<html:text property="cpDOB" size="20" alt="dob" name="gmForm" maxlength="10"/>
								<IMG src="images/CalendarIcon.gif" width="20" onClick="showCalendar('gmForm.cpDOB')" align="center">
							</TD>
							<TD align="left" valign="top" class="ColumnBackground">
								<bean:message key="socialCategory"/>
							</TD>
							<TD align="left" valign="top" class="TableData" width="20%" colspan="3">
								<html:select property="socialCategory" name="gmForm">
								<html:option value="">Select</html:option>
								<html:options name="gmForm" 												property="socialCategoryList"></html:options>
								</html:select>

						</TR>

						<TR align="left">
							<TD align="left" valign="top" class="ColumnBackground" colspan="2">
							<bean:message key="legalid" />					
							</TD>
									<TD align="left" valign="top" class="TableData" colspan="5">
										<html:select property="cpLegalID" name="gmForm" onchange="enableOtherLegalId()" disabled="true" >
											<html:option value="">Select</html:option>
											<html:option value="VoterIdentityCard">Voter Identity Card</html:option>
											<html:option value="PASSPORT">Passport Number</html:option>
											<html:option value="Driving License">Driving License</html:option>
											<html:option value="RationCardnumber">Ration Card Number</html:option>
											<html:option value="Others">Others</html:option>
										</html:select>
										<html:text property="otherCpLegalID" size="20" alt="otherId" name="gmForm"  maxlength="20" disabled="true" /><bean:message key="otherId"  />	
										<html:text property="cpLegalIdValue" size="50" alt="value" name="gmForm"  maxlength="20" disabled="true" /><bean:message key="value"/>	
									</TD>
								</TR>
								
								
						<TR align="left">
							<TD align="left" valign="top" class="ColumnBackground" colspan="2">
										<bean:message key="udyogAdharNo" />					
							</TD>
							<TD align="left" valign="top" class="TableData">
								<html:text property="udyogAdharNo" size="20" alt="dob" name="gmForm" maxlength="12"/>
								
							</TD>
							<TD align="left" valign="top" class="ColumnBackground">
								<bean:message key="loanAcNo"/>
							</TD>
							<TD align="left" valign="top" class="TableData" width="20%" colspan="3">
							<html:text property="bankAcNo" size="20" alt="dob" name="gmForm" maxlength="16"/>
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
													<html:text property="firstName" size="20" alt="firstName" name="gmForm" maxlength="50"  />
													</span></b>
												</td>
												<td width="17%" class="ColumnBackground" >
													<b><span style="font-size: 9pt"> <bean:message key="promoterItpan" /></span></b>
												</td>
												<td width="17%" class="TableData">
													<b><span style="font-size: 9pt"> 
													<html:text property="firstItpan" size="20" alt="firstItpan" name="gmForm" maxlength="10"/></span></b>
												</td>
												<td width="17%" class="ColumnBackground" >
													<b><span style="font-size: 9pt"><bean:message key="promoterDob" /></span></b>
												</td>
												<td width="17%" class="TableData">
													<b><span style="font-size: 9pt"> 
													 <html:text property="firstDOB" size="10" alt="firstDOB" name="gmForm" maxlength="10"/>
													<IMG src="images/CalendarIcon.gif" width="20" onClick="showCalendar('gmForm.firstDOB')" align="center"></span></b>
												</td>
											</tr>
											 <tr>
												<td class="ColumnBackground" width="17%"><b>
													<span style="font-size: 9pt">2. <bean:message key="promoterName" />	</span></b>
												</td>
												<td width="15%" class="TableData">
													<b><span style="font-size: 9pt"> 
													<html:text property="secondName" size="20" alt="secondName" name="gmForm" maxlength="50"/></span></b>
												</td>
												<td width="17%" class="ColumnBackground" >
													<b><span style="font-size: 9pt"> <bean:message key="promoterItpan" /></span></b>
												</td>
												<td width="17%" class="TableData">
													<b><span style="font-size: 9pt"> 
													<html:text property="secondItpan" size="20" alt="secondItpan" name="gmForm" maxlength="10"/></span></b>
												</td>
												<td width="17%" class="ColumnBackground" >
													<b><span style="font-size: 9pt"><bean:message key="promoterDob" /></span></b>
												</td>
												<td width="17%" class="TableData">
													<b><span style="font-size: 9pt"> 
													 <html:text property="secondDOB" size="10" alt="secondDOB" name="gmForm" maxlength="10"/>
													<IMG src="images/CalendarIcon.gif" width="20" onClick="showCalendar('gmForm.secondDOB')" align="center"></span></b>
												</td>
											</tr>

											 <tr>
												<td class="ColumnBackground" width="17%"><b>
													<span style="font-size: 9pt">3. <bean:message key="promoterName" />	</span></b>
												</td>
												<td width="15%" class="TableData">
													<b><span style="font-size: 9pt"> 
													<html:text property="thirdName" size="20" alt="thirdName" name="gmForm" maxlength="50"/></span></b>
												</td>
												<td width="17%" class="ColumnBackground" >
													<b><span style="font-size: 9pt"> <bean:message key="promoterItpan" /></span></b>
												</td>
												<td width="17%" class="TableData">
													<b><span style="font-size: 9pt"> 
													<html:text property="thirdItpan" size="20" alt="thirdItpan" name="gmForm" maxlength="10"/></span></b>
												</td>
												<td width="17%" class="ColumnBackground" >
													<b><span style="font-size: 9pt"><bean:message key="promoterDob" /></span></b>
												</td>
												<td width="17%" class="TableData">
													<b><span style="font-size: 9pt"> 
													 <html:text property="thirdDOB" size="10" alt="thirdDOB" name="gmForm" maxlength="10"/>
													<IMG src="images/CalendarIcon.gif" width="20" onClick="showCalendar('gmForm.thirdDOB')" align="center"></span></b>
												</td>
											</tr>
										 </table>
									</td>
								 </tr>
								<tr align="left"> 
									<td colspan="6">
										<img src="images/Clear.gif" width="5" height="15">
									</td>
								</tr>
								
								  <tr align="left">
						<td class="ColumnBackground" height="28" colspan="2">&nbsp;
							Remarks
						</td>
						<td class="TableData" height="28" colspan="4">
						
							
					<html:textarea property="remarks" cols="75" rows="4" alt="address" name="gmForm"/>
							
						</td>
					</tr>
					
					<tr align="left">
						<td class="ColumnBackground" height="28" colspan="3">&nbsp;
						&nbsp;&nbsp;<html:checkbox property="termsAndConditionCheck"  name="gmForm" >&nbsp;&nbsp;Account is Standard and Regular</html:checkbox>
						</td>
						<td class="TableData" height="28" colspan="4">
						</td>
					</tr>
							</TABLE>			<!--table closing for borrower details-->

				
							


				<TABLE width="100%" border="0" align="left" cellpadding="0" cellspacing="0">			
					<TR>
						<TD align="center" valign="baseline" >
							<DIV align="center">
								
								<A href="javascript:submitForm('saveBorrowerDetails.do?method=updateBorrowerDetails')"><IMG src="images/Save.gif" alt="Save" width="49" height="37" border="0"></A>
								<A href="javascript:document.gmForm.reset()">
									<IMG src="images/Reset.gif" alt="Cancel" width="49" height="37" border="0"></A>
									<A href="home.do?method=getMainMenu&menuIcon=<%=session.getAttribute("menuIcon")%>&mainMenu=<%=session.getAttribute("mainMenu")%>">
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










