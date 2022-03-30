<%@ page language="java"%>
<%@ page import="com.cgtsi.util.SessionConstants"%>
<%@ taglib uri="/WEB-INF/TLD/struts-html.tld" prefix="html" %>
<%@ taglib uri="/WEB-INF/TLD/struts-bean.tld" prefix="bean" %>
<% session.setAttribute("CurrentPage","showborrowerDetails.do?method=showborrowerDetails");%>

<%@ include file="/jsp/SetMenuInfo.jsp" %>
<body onLoad="setConstEnabled(),enableNone(),enableDistrictOthers(),enableOtherLegalId(),enableSubsidyName()">

<TABLE width="725" border="0" cellpadding="0" cellspacing="0">
	<html:errors />
	<html:form action="addTermCreditApp.do?method=submitApp" method="POST" >
		<TR> 
			<TD width="20" align="right" valign="bottom"><IMG src="images/TableLeftTop.gif" width="20" height="31"></TD>
			<TD background="images/TableBackground1.gif"><IMG src="images/ApplicationProcessingHeading.gif" width="91" height="31"></TD>
			<TD width="20" align="left" valign="bottom"><IMG src="images/TableRightTop.gif" width="23" height="31"></TD>
		</TR>
		<TR>
			<TD width="20" background="images/TableVerticalLeftBG.gif">&nbsp;</TD>
			<TD>
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
									
											<bean:write property="assistedByBank" name="appForm"/>
												
										
									</TD>								
								</TR>
								<TR align="left">
									<TD align="left" valign="top" class="ColumnBackground" colspan="3"><bean:message key="osAmt"/></td>								
									<TD align="left" valign="top" class="TableData" colspan="4">
									
											<bean:write name="appForm" property="osAmt"/>										
											<bean:message key="inRs"/>
									</TD>
								 </TR>
								 <TR align="left">
									<TD align="left" valign="top" class="ColumnBackground" colspan="3">
										<bean:message key="npa" />
									</TD>
									<TD align="left" class="TableData" colspan="4"> 
											<bean:write property="npa" name="appForm"/>
												
									</TD>								
								</TR>
								<TR align="left">
									<TD align="left" valign="top" class="ColumnBackground" colspan="3">
										<bean:message key="coveredByCGTSI" />
									</TD>
									<TD align="left" class="TableData" colspan="4">

									<bean:write property="previouslyCovered" name="appForm"/>
										
									</TD>								
								</TR>

								<TR>
									<TD colspan="9">
										<TABLE width="100%" border="0" cellpadding="1" cellspacing="1">			
											 <TR align="left">
												<TD align="left" valign="top" class="ColumnBackground" width="25%"><html:radio name="appForm" value="none" property="none" onclick="disableUnitValue(this)" disabled="true"><bean:message key="none" /></html:radio>
												</TD>
												<TD align="left" valign="top" class="ColumnBackground" ><html:radio name="appForm" value="cgbid" property="none" onclick="enableUnitValue(this)" disabled="true">
												<bean:message key="cgbid" /></html:radio>								
												</TD>
												<TD align="left" valign="top" class="ColumnBackground" width="25%"><html:radio name="appForm" value="cgpan" property="none" onclick="enableUnitValue(this)" disabled="true">
												<bean:message key="cgpan" /></html:radio>
																								
												</TD>
												<TD align="left" valign="top" class="ColumnBackground" width="15%" colspan="2">	
												
											
											<bean:write property="unitValue" name="appForm"/>
											
												
												
											</TR>

											 <TR align="left">
												<TD align="left" valign="top" class="ColumnBackground" width="30%"><bean:message key="constitution"/>
												</TD>
												<TD align="left" valign="top" class="TableData" colspan="8">
												
												<bean:write property="constitution" name="appForm"/>
												
												<bean:write property="constitutionOther" name="appForm"/>
												
												</TD>
											 </TR>
											
											 <TR align="left">
												<TD align="left" valign="top" class="ColumnBackground" width="30%"><bean:message key="unitName"/>
												</TD>												
												<TD align="left" valign="top" class="TableData" width="30%">

												
												<bean:write property="ssiType" name="appForm"/>
												
													<bean:write name="appForm" property="ssiName"/>
													
												 </td>
												<TD align="left" valign="top" class="ColumnBackground" width="25%"><bean:message key="ssiRegNo"/>
												</td>
												<TD align="left" valign="top" class="TableData" colspan="2" width="15%">
												
													<bean:write name="appForm" property="regNo"/>

												
												</td>
											</tr>
						
											 <TR align="left">											
												<TD align="left" valign="top" class="ColumnBackground" width="25%"><bean:message key="firmItpan"/>
												</td>
												<TD align="left" valign="top" class="TableData" width="15%" colspan="4">
												
													<bean:write name="appForm" property="ssiITPan"/>

												
												</td>
											</tr>

											 <TR align="left">
												<TD align="left" valign="top" class="ColumnBackground" width="30%"><bean:message key="industryNature"/>
												</TD>												
												<TD align="left" valign="top" class="TableData">

													<bean:write name="appForm" property="industryNature"/>

													
												 </td>
												<TD align="left" valign="top" class="ColumnBackground" width="25%"><bean:message key="industrySector"/>
												</td>
												<TD align="left" valign="top" class="TableData" colspan="2">
												
													<bean:write name="appForm" property="industrySector"/>

													
												 </td>
											</tr>

											 <TR align="left">
												<TD align="left" valign="top" class="ColumnBackground" width="30%"><bean:message key="activitytype"/>
												</TD>												
												<TD align="left" valign="top" class="TableData">
												
													<bean:write name="appForm" property="activityType"/>

												
												 </td>
												<TD align="left" valign="top" class="ColumnBackground" width="25%"><bean:message key="noOfEmployees"/>
												</td>
												<TD align="left" valign="top" class="TableData" width="15%" colspan="2">
												
													<bean:write name="appForm" property="employeeNos"/>

												
												</td>
											</tr>

											 <TR align="left">
												<TD align="left" valign="top" class="ColumnBackground" width="30%"><bean:message key="turnover"/>
												</TD>												
												<TD align="left" valign="top" class="TableData">
												
													<bean:write name="appForm" property="projectedSalesTurnover"/>

												
													<bean:message key="inRs"/>
												 </td>
												<TD align="left" valign="top" class="ColumnBackground" width="25%"><bean:message key="exports"/>
												</td>
												<TD align="left" valign="top" class="TableData" width="15%" colspan="2">
												
													<bean:write name="appForm" property="projectedExports"/>

												
													<bean:message key="inRs"/>
												</td>
											</tr>

											 <TR align="left">											
												<TD align="left" valign="top" class="ColumnBackground" width="30%"><bean:message key="address"/>
												</td>
												<TD align="left" valign="top" class="TableData" width="30%">
												
													<bean:write name="appForm" property="address"/>

												</td>
												<TD align="left" valign="top" class="ColumnBackground" width="25%"><bean:message key="state"/>
												</TD>												
												<TD align="left" valign="top" class="TableData" width="15%" colspan="2">
												
													<bean:write name="appForm" property="state"/>											
												 </td>
											</tr>

												
											 <TR align="left">
												<TD align="left" valign="top" class="ColumnBackground" width="15%"><bean:message key="district"/>
												</TD>												
												<TD align="left" valign="top" class="TableData" width="15%">
												
													<bean:write name="appForm" property="district"/>													
												</TD>
												<TD align="left" valign="top" class="TableData">

												
												<bean:write property="districtOthers" name="appForm"/>
												
												 </td>
												
												<TD align="left" valign="top" class="ColumnBackground" width="25%"><bean:message key="city"/>
												</td>
												<TD align="left" valign="top" class="TableData" width="15%">
												
													<bean:write name="appForm" property="city"/>													
												</td>
											</tr>

											 <TR align="left">
												<TD align="left" valign="top" class="ColumnBackground" width="30%"><bean:message key="pinCode"/>
												</td>
												<TD align="left" valign="top" class="TableData" width="70%" colspan="5">
												
													<bean:write name="appForm" property="pincode"/>
													
												</td>		
											</TR>
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

								  	<bean:write property="cpTitle" name="appForm"/>
																	
								  </TD>
								  <TD align="left" valign="top" class="TableData">

									<bean:write property="cpFirstName" name="appForm"/>
									
								  </TD>
								   <TD align="left" valign="top" class="TableData">
	
										<bean:write property="cpMiddleName" name="appForm"/>
									
								  </TD>
								   <TD align="left" valign="top" class="TableData">

								   	<bean:write property="cpLastName" name="appForm"/>
																	  </TD>
								</TR>

								<TR align="left">
									<TD align="left" valign="top" class="ColumnBackground" colspan="2"><bean:message key="gender"/>
									</TD>
									<TD align="left" valign="top" class="TableData" width="20%">

									<bean:write property="cpGender" name="appForm"/>
									
									</TD>
									<TD align="left" valign="top" class="ColumnBackground">
										<bean:message key="chiefItpan" />
									</TD>
									<TD align="left" valign="top" class="TableData" colspan="2" width="20%">

									
									<bean:write property="cpITPAN" name="appForm"/>
									
									</TD>													
								</TR>

								<TR align="left">
									<TD align="left" valign="top" class="ColumnBackground" colspan="2">
										<bean:message key="dob" />					
									</TD>
									<TD align="left" valign="top" class="TableData" >

										<bean:write property="cpDOB" name="appForm"/>
									

									<TD align="left" valign="top" class="ColumnBackground">
									<bean:message key="socialCategory"/>
									</TD>
									<TD align="left" valign="top" class="TableData" width="20%" colspan="3">

									<bean:write property="socialCategory" name="appForm"/>
										
									</TD>

										
									</TD>
								</TR>

								<TR align="left">
									<TD align="left" valign="top" class="ColumnBackground" colspan="2">
										<bean:message key="legalid" />					
									</TD>
									<TD align="left" valign="top" class="TableData" colspan="5">

									
									<bean:write property="cpLegalID" name="appForm"/>
									

									<bean:write property="otherCpLegalID" name="appForm"/>

										&nbsp;&nbsp;&nbsp;<strong><bean:message key="pleaseSpecify"/></strong>

									<bean:write property="cpLegalIdValue" name="appForm"/>
									
									
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

													<bean:write property="firstName" name="appForm"/>
													</span></b>
													
												</td>
												<td width="17%" class="ColumnBackground" >
													<b><span style="font-size: 9pt"> <bean:message key="promoterItpan" /></span></b>
												</td>
												<td width="17%" class="TableData">												
													<b><span style="font-size: 9pt"> 

													<bean:write property="firstItpan" name="appForm"/>
													</span></b>
													
												</td>
												<td width="17%" class="ColumnBackground" >
													<b><span style="font-size: 9pt"><bean:message key="promoterDob" /></span></b>
												</td>
												<td width="17%" class="TableData">
												
													<b><span style="font-size: 9pt"> 
													
													<bean:write property="firstDOB" name="appForm"/>
													</span></b>
													
												</td>
											</tr>
											 <tr>
												<td class="ColumnBackground" width="17%"><b>
													<span style="font-size: 9pt">2. <bean:message key="promoterName" />	</span></b>
												</td>
												<td width="15%" class="TableData">
												
													<b><span style="font-size: 9pt"> 

													<bean:write property="secondName" name="appForm"/>

													</span></b>
													
												</td>
												<td width="17%" class="ColumnBackground" >
													<b><span style="font-size: 9pt"> <bean:message key="promoterItpan" /></span></b>
												</td>
												<td width="17%" class="TableData">
												
													<b><span style="font-size: 9pt"> 

													<bean:write property="secondItpan" name="appForm"/>
													
													</span></b>
													
												</td>
												<td width="17%" class="ColumnBackground" >
													<b><span style="font-size: 9pt"><bean:message key="promoterDob" /></span></b>
												</td>
												<td width="17%" class="TableData">
												
													<b><span style="font-size: 9pt"> 

													<bean:write property="secondDOB" name="appForm"/>

													</span></b>
													
												</td>
											</tr>

											 <tr>
												<td class="ColumnBackground" width="17%"><b>
													<span style="font-size: 9pt">3. <bean:message key="promoterName" />	</span></b>
												</td>
												<td width="15%" class="TableData">
												
													<b><span style="font-size: 9pt"> 

													<bean:write property="thirdName" name="appForm"/>
													</span></b>
													
												</td>
												<td width="17%" class="ColumnBackground" >
													<b><span style="font-size: 9pt"> <bean:message key="promoterItpan" /></span></b>
												</td>
												<td width="17%" class="TableData">
												
													<b><span style="font-size: 9pt"> 
													<bean:write property="thirdItpan" name="appForm"/>
													</span></b>
													
												</td>
												<td width="17%" class="ColumnBackground" >
													<b><span style="font-size: 9pt"><bean:message key="promoterDob" /></span></b>
												</td>
												<td width="17%" class="TableData">
												
													<b><span style="font-size: 9pt"> 

													<bean:write property="thirdDOB" name="appForm"/>

													</span></b>
													
												</td>
											</tr>
										 </table>
									</td>
								 </tr>

							</table>
							<TABLE width="100%" border="0" align="left" cellpadding="0" cellspacing="0">
							<TR>
								<TD align="center" valign="baseline" colspan="5">
									<DIV align="center">								
										<A href="javascript:history.back()">
											<IMG src="images/Back.gif" alt="Back" width="49" height="37" border="0"></A>
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
		</body>






					