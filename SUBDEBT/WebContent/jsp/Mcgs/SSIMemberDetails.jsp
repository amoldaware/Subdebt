
<%@ page language="java"%>
<%@ include file="/jsp/SetMenuInfo.jsp" %>
<%@ page import="com.cgtsi.util.SessionConstants"%>
<%@ taglib uri="/WEB-INF/TLD/struts-html.tld" prefix="html" %>
<%@ taglib uri="/WEB-INF/TLD/struts-bean.tld" prefix="bean" %>
<% session.setAttribute("CurrentPage","showSSIMemberDetails.do?method=showSSIMemberDetails");%>
<%String focusField="";

if(request.getAttribute(SessionConstants.MCGS_ACTION_FLAG)!=null && request.getAttribute(SessionConstants.MCGS_ACTION_FLAG).equals("1"))
{
	focusField="district";
}
else if(request.getAttribute(SessionConstants.MCGS_ACTION_FLAG)!=null && request.getAttribute(SessionConstants.MCGS_ACTION_FLAG).equals("2"))
{
	focusField="industrySector";
}
else{

	focusField="mcgf";
}
%>
<HTML>
	<BODY onLoad="setConstEnabled(),enableDistrictOthers(),enableOtherLegalId(),enableAssistance(),enableGender()">	
<html:errors />
<html:form action="addSSIMemberDetails.do" method="POST" focus="<%=focusField%>">
	<TABLE width="725" border="0" cellpadding="0" cellspacing="0">
			<TR>
				<TD width="20" align="right" valign="bottom"><IMG src="images/TableLeftTop.gif" width="20" height="31"></TD>
				<TD background="images/TableBackground1.gif"></TD>
				<TD width="20" align="left" valign="bottom"><IMG src="images/TableRightTop.gif" width="23" height="31"></TD>
			</TR>
			<TR>
				<TD width="20" background="images/TableVerticalLeftBG.gif">&nbsp;</TD>
				<TD>
				<DIV align="right">			
						<A HREF="javascript:submitForm('ssiMembersHelp.do?method=ssiMembersHelp')">
						HELP</A>
					</DIV>
					<TABLE width="100%" cellspacing="0" cellpadding="0">
						<TR>
							<TD>
								<TABLE width="100%" border="0" cellspacing="1" cellpadding="0">
									<TR>
										<TD colspan="4">
											<TABLE width="100%" border="0" cellpadding="0" cellspacing="0">
												<TR>
													<TD width="31%" class="Heading"><bean:message key="ssiMemberHeader" /></TD>
													<TD><IMG src="images/TriangleSubhead.gif" width="19" height="19"></TD>
												</TR>
												<TR>
													<TD colspan="4" class="Heading"><IMG src="images/Clear.gif" width="5" height="5"></TD>
												</TR>
											</TABLE>
										</TD>
									</TR>
								</TABLE>
							</TD>
						</TR>
						<TR>
							<TD>
								<TABLE width="100%" border="0" cellspacing="1" cellpadding="1">
									<TR align="left">
										<TD class="ColumnBackground" colspan="3">
											&nbsp;<font color="#FF0000" size="2">*</font>&nbsp;<bean:message key="mcgfName" />
										</TD>
										<TD class="TableData" colspan="4">
											<html:select property="mcgf" name="mcgsForm" >
												<html:option value="">Select</html:option>
												<html:options name="mcgsForm" property="mcgfs"/>
											</html:select>
										</TD>
									</TR>
									<%--
									<TR align="left">
										<TD align="left" valign="top" class="ColumnBackground" colspan="3">
											&nbsp;<bean:message key="coveredByCGTSI" />
										</TD>
										<TD align="left" class="TableData" colspan="4">
												<html:radio name="mcgsForm" value="Y" property="previouslyCovered"  ></html:radio>
												<bean:message key="yes" />&nbsp;&nbsp;
												<html:radio name="mcgsForm" value="N" property="previouslyCovered"  ></html:radio>
												<bean:message key="no"/>
										</TD>
									</TR>
									--%>

									<TR align="left">
										<TD align="left" valign="top" class="ColumnBackground" colspan="3">
											&nbsp;<bean:message key="bankAssistance" />
										</TD>
										<TD align="left" class="TableData" colspan="4">
												<html:radio name="mcgsForm" value="Y" property="assistedByBank"  onclick="javascript:enableAssistance()"></html:radio>
												<bean:message key="yes" />&nbsp;&nbsp;
												<html:radio name="mcgsForm" value="N" property="assistedByBank" onclick="javascript:enableAssistance()"></html:radio>
												<bean:message key="no"/>
										</TD>
									</TR>
									<TR align="left">
										<TD align="left" valign="top" class="ColumnBackground" colspan="3">
											<bean:message key="osAmt"/>
										</TD>
										<TD align="left" valign="top" class="TableData" colspan="4">
												<html:text property="osAmt" size="20" alt="Outstanding Amount" name="mcgsForm" maxlength="15" onkeypress="return numbersOnly(this, event)" onkeyup="isValidNumber(this)"/>
												<bean:message key="inRs"/>
										</TD>
									 </TR>
									 <%--
									 <TR align="left">
										<TD align="left" valign="top" class="ColumnBackground" colspan="3">
										&nbsp;<bean:message key="acNo"/></TD>
										<TD align="left" valign="top" class="TableData" colspan="4">
											<html:text property="acNo" size="25" alt="uniqueAcctNo" name="mcgsForm" maxlength="20" />
										</TD>
									 </TR>


									 <TR align="left">
										<TD align="left" valign="top" class="ColumnBackground" colspan="3">
											&nbsp;<bean:message key="npa" />
										</TD>
										<TD align="left" class="TableData" colspan="4">
												<html:radio name="mcgsForm" value="Y" property="npa"  ></html:radio>
												<bean:message key="yes" />&nbsp;&nbsp;
												<html:radio name="mcgsForm" value="N" property="npa" /></html:radio>
												<bean:message key="no"/>
										</TD>
									</TR>
									--%>


									<!--
									<TR>
										<TD colspan="7">
											<TABLE width="100%" border="0" cellpadding="1" cellspacing="1">
									!-->
												<TR>
													<TD colspan="9">
														<TABLE width="100%" border="0" cellpadding="1" cellspacing="1">
															<!--
															<TR>
																<TD class="SubHeading"><bean:message key="unitHeader" /></TD>
															</TR>
															!-->
															<%--
															 <TR>
																<TD align="left" valign="top" class="ColumnBackground" width="30%">
																	<html:radio name="mcgsForm" value="none" property="idType" onclick="disableIdOther(this)">
																		<bean:message key="none" />
																	</html:radio>
																</TD>
																<TD align="left" valign="top" class="ColumnBackground" width="30%">
																	<html:radio name="mcgsForm" value="cgbid" property="idType" onclick="disableIdOther(this)" >
																		<bean:message key="cgbid" />
																	</html:radio>
																</TD>

																<TD align="left" valign="top" class="ColumnBackground" width="25%">
																	<html:radio name="mcgsForm" value="cgpan" property="idType" onclick="disableIdOther(this)">
																		<bean:message key="cgpan" />
																	</html:radio>
																</TD>

																<TD align="left" valign="top" class="ColumnBackground" colspan="3">
																	<html:text property="idTypeOther" size="20" alt="Value" name="mcgsForm"  maxlength="13" disabled="true"/>
																</TD>
															</TR>
															--%>
															 <TR align="left">
																<TD align="left" valign="top" class="ColumnBackground" width="30%"><font color="#FF0000" size="2">*</font><bean:message key="constitution"/>
																</TD>
																<TD align="left" valign="top" class="TableData" >
																	<html:select property="constitution" name="mcgsForm" onchange="enableConstituitionOther(this)">
																		<html:option value="">Select</html:option>
																		<html:option value="proprietary"><bean:message key="proprietary" /></html:option>
																		<html:option value="partnership"><bean:message key="partnership" /></html:option>
																		<html:option value="private"><bean:message key="private" /></html:option>
																		<html:option value="public"><bean:message key="public" /></html:option>
																		<html:option value="Others"><bean:message key="others" /></html:option>
																	</html:select>&nbsp;&nbsp;&nbsp;&nbsp;


																	<%--
																	<html:radio name="mcgsForm" value="proprietary" property="constitution"   onclick="setConstDisabled(this)"></html:radio>

																	<bean:message key="proprietary" />


																	<html:radio name="mcgsForm" value="partnership" property="constitution"  onclick="setConstDisabled(this)"></html:radio>

																	<bean:message key="partnership" />


																		<html:radio name="mcgsForm" value="public" property="constitution"   onclick="setConstDisabled(this)"></html:radio>
																	<bean:message key="public" />


																	<html:radio name="mcgsForm" value="private" property="constitution"   onclick="setConstDisabled(this)"></html:radio>
																	<bean:message key="private" />


																	<html:radio name="mcgsForm" value="" property="constitution"  onclick="setConstEnabled(this)"></html:radio>

																	<bean:message key="others" />&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
																	--%>

																	<html:text property="constitutionOther" size="15" alt="constitution" name="mcgsForm"  maxlength="50" disabled="true"/>

																</TD>
																<TD align="left" valign="top" class="ColumnBackground" width="60%"><font color="#FF0000" size="2">*</font><bean:message key="unitName"/>
																</TD>
																<TD align="left" valign="top" class="TableData" colspan="2">

																	<html:select property="ssiType" name="mcgsForm" >
																		<html:option value="">Select</html:option>
																		<html:option value="M/s">M/s</html:option>
																		<html:option value="Shri">Shri</html:option>
																		<html:option value="Smt">Smt</html:option>
																		<html:option value="Ku">Ku</html:option>
																	</html:select>&nbsp;&nbsp;&nbsp;&nbsp;
																	<html:text property="ssiName" size="20" alt="unitName" name="mcgsForm"  maxlength="100"/>
																 </td>
															 </TR>
															 <TR align="left">
																<TD align="left" valign="top" class="ColumnBackground" width="30%"><font color="#FF0000" size="2">*</font>&nbsp;<bean:message key="unitAddress"/>
																</td>
																<TD align="left" valign="top" class="TableData" width="30%">
																	<html:textarea property="address" cols="30" alt="address" name="mcgsForm" rows="3"/>
																</td>
																<TD align="left" valign="top" class="ColumnBackground" width="25%"><font color="#FF0000" size="2">*</font>&nbsp;<bean:message key="state"/>
																</TD>
																<TD align="left" valign="top" class="TableData" width="15%" colspan="2">

																	<html:select property="state" name="mcgsForm" onchange="submitForm('showSSIMemberDetails.do?method=getDistricts')">
																		<html:option value="">Select</html:option>
																		<html:options name="mcgsForm" property="states"></html:options>

																	</html:select>

																 </td>
															</tr>


															 <TR align="left">
																<TD align="left" valign="top" class="ColumnBackground" width="15%"><font color="#FF0000" size="2">*</font>&nbsp;<bean:message key="district"/>
																</TD>
																<TD align="left" valign="top" class="TableData" width="15%">

																	<html:select property="district" name="mcgsForm" onchange="javascript:enableDistrictOthers()">
																		<html:option value="">Select</html:option>
																		<html:options name="mcgsForm" property="districts"/>
																		<html:option value="Others">Others</html:option>
																	</html:select>
																</TD>
																<TD align="left" valign="top" class="TableData">
																	<html:text property="districtOthers" size="10" alt="district" name="mcgsForm"  maxlength="100" disabled="true"/>
																	<bean:message key="others"/>

																 </td>
																<TD align="left" valign="top" class="ColumnBackground" width="25%"><font color="#FF0000" size="2">*</font><bean:message key="city"/>
																</td>
																<TD align="left" valign="top" class="TableData" width="15%">
																	<html:text property="city" size="20" alt="city" name="mcgsForm" maxlength="100"/>
																</td>
															</tr>

															 <TR align="left">
																<TD align="left" valign="top" class="ColumnBackground" width="30%"><font color="#FF0000" size="2">*</font>&nbsp;<bean:message key="pinCode"/>
																</td>
																<TD align="left" valign="top" class="TableData" colspan="1">
																	<html:text property="pincode" size="20" alt="pinCode" name="mcgsForm" maxlength="6" onkeypress="return numbersOnly(this, event)" onkeyup="isValidNumber(this)"/>
																</td>

																<TD align="left" valign="top" class="ColumnBackground" width="30%"><font color="#FF0000" size="2">*</font>&nbsp;<bean:message key="displayDefaulters"/>
																</td>
																<TD align="left" valign="top" class="TableData" colspan="2">
																	<html:radio name="mcgsForm" value="Y" property="displayDefaultersList"   ></html:radio>
																	<bean:message key="yes" />
																	<html:radio name="mcgsForm" value="N" property="displayDefaultersList"  ></html:radio>
																	<bean:message key="no" />

																</td>
															</TR>

															 <TR align="left">

																<TD align="left" valign="top" class="ColumnBackground" width="25%"><bean:message key="ssiRegNo"/>
																</td>
																<TD align="left" valign="top" class="TableData"  >
																	<html:text property="regNo" size="30" alt="SSI Reg No" name="mcgsForm"maxlength="25" />
																</td>

																<TD align="left" valign="top" class="ColumnBackground" width="25%"><bean:message key="firmItpan"/>
																</td>
																<TD align="left" valign="top" class="TableData" width="15%" colspan="2">
																	<html:text property="ssiITPan" size="20" alt="ITPAN" name="mcgsForm" maxlength="10" />
																</td>
															</tr>

															 <TR align="left">
																<TD align="left" valign="top" class="ColumnBackground" width="30%"><bean:message key="industryNature"/>
																</TD>
																<TD align="left" valign="top" class="TableData">
																	<html:select property="industryNature" name="mcgsForm" onchange="submitForm('showSSIMemberDetails.do?method=getIndustrySectors')">
																		<html:option value="">Select</html:option>
																		<html:options property="industryNatures" name="mcgsForm" />

																	</html:select>
																 </td>
																<TD align="left" valign="top" class="ColumnBackground" width="25%"><bean:message key="industrySector"/>
																</td>
																<TD align="left" valign="top" class="TableData" colspan="2">

																	<html:select property="industrySector" name="mcgsForm" >
																		<html:option value="">Select</html:option>
																		<html:options property="industrySectors" name="mcgsForm" />

																	 </html:select>

																 </td>
															</tr>

															 <TR align="left">
																<TD align="left" valign="top" class="ColumnBackground" width="30%"><font color="#FF0000" size="2">*</font>&nbsp;<bean:message key="activitytype"/>
																</TD>
																<TD align="left" valign="top" class="TableData">
																	<html:text property="activityType" size="20" alt="Activity Type" name="mcgsForm" maxlength="50"/>
																 </td>
																<TD align="left" valign="top" class="ColumnBackground" width="25%"><bean:message key="noOfEmployees"/>
																</td>
																<TD align="left" valign="top" class="TableData" width="15%" colspan="2">
																	<html:text property="employeeNos" size="10" alt="No Of employees" name="mcgsForm" onkeypress="return numbersOnly(this, event)" onkeyup="isValidNumber(this)"/>
																</td>
															</tr>

															 <TR align="left">
																<TD align="left" valign="top" class="ColumnBackground" width="30%"><bean:message key="turnover"/>
																</TD>
																<TD align="left" valign="top" class="TableData">
																	<html:text property="projectedSalesTurnover" size="20" alt="turnover" name="mcgsForm" maxlength="13" onkeypress="return numbersOnly(this, event)" onkeyup="isValidNumber(this)"/>
																	<bean:message key="inRs"/>
																 </td>
																<TD align="left" valign="top" class="ColumnBackground" width="25%"><bean:message key="exports" />
																</td>
																<TD align="left" valign="top" class="TableData" width="15%" colspan="2">
																	<html:text property="projectedExports" size="10" alt="exports" name="mcgsForm" maxlength="13" onkeypress="return numbersOnly(this, event)" onkeyup="isValidNumber(this)"/>
																	<bean:message key="inRs"/>
																</td>
															</tr>


														<!--
														</TABLE>
													</TD>
												</TR> !-->
											</TABLE>
										</TD>
									</TR>
									<TR>
										<TD class="SubHeading" colspan="4"><bean:message key="promoterHeader" />
										</TD>
									</TR>

									<TR>
										<TD align="left" valign="top" class="ColumnBackground" colspan="6">
											&nbsp;<bean:message key="chiefInfo"/>
										</TD>
									</TR>

								  <TR>
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
									  <TD align="left" valign="top" class="ColumnBackground" colspan="2" >&nbsp;<bean:message key="name"/>
									  </TD>
									  <TD align="left" valign="top" class="TableData" >
										<html:select property="cpTitle" name="mcgsForm" onchange="enableGender()">
											<html:option value="">Select</html:option>
											<html:option value="Mr.">Mr</html:option>
											<html:option value="Smt">Smt</html:option>
											<html:option value="Ku">Ku</html:option>
										</html:select>
									  </TD>
									  <TD align="left" valign="top" class="TableData">
										<html:text property="cpFirstName" size="20" alt="firstName" name="mcgsForm" maxlength="20"/>
									  </TD>
									   <TD align="left" valign="top" class="TableData">
										<html:text property="cpMiddleName" size="20" alt="middleName" name="mcgsForm" maxlength="20"/>
									  </TD>
									   <TD align="left" valign="top" class="TableData">
										<html:text property="cpLastName" size="20" alt="lastName" name="mcgsForm" maxlength="20"/>
									  </TD>
									</TR>

									<TR align="left">
										<TD align="left" valign="top" class="ColumnBackground" colspan="2">&nbsp;<font color="#FF0000" size="2">*</font>&nbsp;<bean:message key="gender"/>
										</TD>
										<TD align="left" valign="top" class="TableData" width="20%">
											<html:radio name="mcgsForm" value="M" property="cpGender" >
											</html:radio>
											<bean:message key="male" />&nbsp;&nbsp;&nbsp;

											<html:radio name="mcgsForm" value="F" property="cpGender" ></html:radio>
											<bean:message key="female" />
										</TD>
										<TD align="left" valign="top" class="ColumnBackground">
											<bean:message key="chiefItpan" />
										</TD>
										<TD align="left" valign="top" class="TableData" colspan="2">
											<html:text property="cpITPAN" size="20" alt="chiefItpan" name="mcgsForm" maxlength="10"/>
										</TD>
									</TR>

									<TR>
										<TD align="left" valign="top" class="ColumnBackground" colspan="2">
											&nbsp;<bean:message key="dob" />
										</TD>
										<TD align="left" valign="top" class="TableData" width="25%">
											<html:text property="cpDOB" size="20" alt="dob" name="mcgsForm" maxlength="10"/>
											<IMG src="images/CalendarIcon.gif" width="20" onClick="showCalendar('mcgsForm.cpDOB')" align="center">
										</TD>
										<TD align="left" valign="top" class="ColumnBackground" ><bean:message key="socialCategory"/>
										</td>
										<TD align="left" valign="top" class="TableData" colspan="2">
											<html:select property="socialCategory" name="mcgsForm">
											<html:option value="">Select</html:option>
											<html:options property="socialCategories" name="mcgsForm"/>
											</html:select>
										</td>
									</TR>

									<TR align="left">
										<TD align="left" valign="top" class="ColumnBackground" colspan="2">
											&nbsp;<bean:message key="legalid" />
										</TD>
										<TD align="left" valign="top" class="TableData" colspan="5">
											<html:select property="cpLegalID" name="mcgsForm" onchange="enableOtherLegalId(this)">
												<html:option value="">Select</html:option>
											<html:option value="VoterIdentityCard">Voter Identity Card</html:option>
											<html:option value="PASSPORT">Passport Number</html:option>
											<html:option value="Driving License">Driving License</html:option>
											<html:option value="RationCardnumber">Ration Card Number</html:option>
												<html:option value="Others">Others</html:option>
											</html:select>
											<html:text property="otherCpLegalID" size="20" alt="otherId" name="mcgsForm"  maxlength="50" disabled="true"/><bean:message key="otherId"/>&nbsp&nbsp<bean:message key="pleaseSpecify"/>
											<html:text property="cpLegalIdValue" size="20" alt="value" name="mcgsForm" maxlength="20"/>&nbsp<bean:message key="id" />
										</TD>
									</TR>

									<TR align="left">
										<TD align="left" valign="top" class="ColumnBackground" colspan="7">&nbsp;<bean:message key="otherPromoters" />
										</TD>
									</TR>
									 <TR  align="left" >

										 <TD colspan="7">
											 <TABLE border="0" cellpadding="0" cellspacing="1" width="100%">

												<TR>

													<TD class="ColumnBackground" colspan="1">&nbsp;
														1. <bean:message key="promoterName" />
													</TD>
													<TD colspan="1" class="TableData">&nbsp;
														<html:text property="firstName" size="20" alt="firstName" name="mcgsForm" maxlength="50"/>
													</TD>
													<TD colspan="1" class="ColumnBackground" >&nbsp;
														<bean:message key="promoterItpan" />
													</TD>
													<TD colspan="1" class="TableData">&nbsp;
														<html:text property="firstItpan" size="20" alt="firstItpan" name="mcgsForm" maxlength="10"/>
													</TD>
													<TD colspan="1" class="ColumnBackground" >&nbsp;
														<bean:message key="promoterDob" />
													</TD>
													<TD colspan="1" class="TableData">&nbsp;
														 <html:text property="firstDOB" size="10" alt="firstDOB" name="mcgsForm" maxlength="10"/>
														<IMG src="images/CalendarIcon.gif" width="20" onClick="showCalendar('mcgsForm.firstDOB')" align="center">
													</TD>
												</TR>
												 <TR>
													<TD class="ColumnBackground" colspan="1">&nbsp;
														2. <bean:message key="promoterName" />
													</TD>
													<TD colspan="1" class="TableData">&nbsp;
														<html:text property="secondName" size="20" alt="secondName" name="mcgsForm" maxlength="50"/>
													</TD>
													<TD colspan="1" class="ColumnBackground" >&nbsp;
														<bean:message key="promoterItpan" />
													</TD>
													<TD colspan="1" class="TableData">&nbsp;
														<html:text property="secondItpan" size="20" alt="secondItpan" name="mcgsForm" maxlength="10"/>
													</TD>
													<TD colspan="1" class="ColumnBackground" >&nbsp;
														<bean:message key="promoterDob" />
													</TD>
													<TD colspan="1" class="TableData">&nbsp;
														 <html:text property="secondDOB" size="10" alt="secondDOB" name="mcgsForm" maxlength="10"/>
														<IMG src="images/CalendarIcon.gif" width="20" onClick="showCalendar('mcgsForm.secondDOB')" align="center">
													</TD>
												</TR>

												 <TR>
													<TD class="ColumnBackground" colspan="1">&nbsp;
														3. <bean:message key="promoterName" />
													</TD>
													<TD colspan="1" class="TableData">&nbsp;
														<html:text property="thirdName" size="20" alt="thirdName" name="mcgsForm" maxlength="50"/>
													</TD>
													<TD colspan="1" class="ColumnBackground" >&nbsp;
														<bean:message key="promoterItpan" />
													</TD>
													<TD colspan="1" class="TableData">&nbsp;
														<html:text property="thirdItpan" size="20" alt="thirdItpan" name="mcgsForm" maxlength="10"/>
													</TD>
													<TD colspan="1" class="ColumnBackground" >&nbsp;
														<bean:message key="promoterDob" />
													</TD>
													<TD colspan="1" class="TableData">&nbsp;
														 <html:text property="thirdDOB" size="10" alt="thirdDOB" name="mcgsForm" maxlength="10"/>
														<IMG src="images/CalendarIcon.gif" width="20" onClick="showCalendar('mcgsForm.thirdDOB')" align="center">
													</TD>
												</TR>

											 </TABLE>
										</TD>
									 </TR>

									<TR>
										<TD align="left" valign="top" class="ColumnBackground" colspan="7">&nbsp;<bean:message key="corpusContribution" />
										</TD>

									<TR>
										<TD class="ColumnBackground" colspan="3">
											<font color="#FF0000" size="2">*</font>&nbsp;<bean:message key="corpusContributionAmt" />
										</TD>
										<TD  class="ColumnBackground" colspan="1">
											&nbsp;&nbsp;
											 <html:text property="corpusContributionAmt" size="20"  name="mcgsForm" maxlength="13" onkeypress="return numbersOnly(this, event)" onkeyup="isValidNumber(this)"/>
										</TD>
										<TD class="ColumnBackground" colspan="1"><font color="#FF0000" size="2">*</font>&nbsp;
											<bean:message key="corpusContributionDate" />
										</TD>
										<TD class="ColumnBackground" colspan="1">
											&nbsp;&nbsp;
											 <html:text property="corpusContributionDate" size="10"  name="mcgsForm" maxlength="10"/>
											<IMG src="images/CalendarIcon.gif" width="20" onClick="showCalendar('mcgsForm.corpusContributionDate')" align="center">
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
							<TD align="center" valign="baseline" >
								<DIV align="center">
									<A href="javascript:submitForm('addSSIMemberDetails.do?method=addSSIMemberDetails')"><IMG src="images/Save.gif" alt="Save" width="49" height="37" border="0"></A>
									<A href="javascript:document.mcgsForm.reset()">
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
		
	</BODY>
</HTML>