<%@ page language="java"%>
<%@ taglib uri="/WEB-INF/TLD/struts-html.tld" prefix="html" %>
<%@ taglib uri="/WEB-INF/TLD/struts-bean.tld" prefix="bean" %>
<%@ include file="/jsp/SetMenuInfo.jsp" %>
<%@page import ="java.text.DecimalFormat"%>
<%@ page import="com.cgtsi.actionform.GMActionForm"%>
<%@ page import="com.cgtsi.application.BorrowerDetails"%>
<%@ page import="com.cgtsi.application.SSIDetails"%>
<%@page import ="java.text.SimpleDateFormat"%>

<%SimpleDateFormat dateFormat=new SimpleDateFormat("dd/MM/yyyy");%>
<%
DecimalFormat decimalFormat = new DecimalFormat("##########.##");
%>

<% session.setAttribute("CurrentPage","showBorrowerDetailsLink.do?method=showBorrowerDetailsLink");%>
<TABLE width="725" border="0" cellpadding="0" cellspacing="0">
	<html:errors />
	<html:form action="saveBorrowerDetails.do?method=saveBorrowerDetails">
		<TR> 
			<TD width="20" align="right" valign="bottom"><IMG src="images/TableLeftTop.gif" width="20" height="31"></TD>
			<TD background="images/TableBackground1.gif"><IMG src="images/GuaranteeMaintenanceHeading.gif"></TD>
			<TD width="20" align="left" valign="bottom"><IMG src="images/TableRightTop.gif" width="23" height="31"></TD>
		</TR>
		<TR>
			<TD width="20" background="images/TableVerticalLeftBG.gif">&nbsp;</TD>
			<TD>
				<TABLE width="100%" border="0" cellspacing="1" cellpadding="1">
					<TR> 
						<TD colspan="7">
							<TABLE width="100%" border="0" cellpadding="0" cellspacing="0">
								<TR>
									<TD width="30%" class="Heading"><bean:message key="borrowerHeader" /></TD>
									<TD><IMG src="images/TriangleSubhead.gif" width="19" height="19"></TD>
								</TR>
								<TR>
									<TD colspan="6" class="Heading"><IMG src="images/Clear.gif" width="5" height="5"></TD>
								</TR>
							</TABLE>
						</TD>
					</TR>
					<%
						HttpSession gmSession = request.getSession(false);
						GMActionForm gmActionForm = (GMActionForm)session.getAttribute("gmPeriodicInfoForm");						
						BorrowerDetails borrowerDetails = gmActionForm.getBorrowerDetails();
						SSIDetails ssiDetails = borrowerDetails.getSsiDetails();
						java.util.Date dob = ssiDetails.getCpDOB();
						System.out.println("dob:"+dob);
						String formatedDate = null;
						if(dob != null)
						{
							 formatedDate=dateFormat.format(dob);
							 System.out.println("formatedDate:"+formatedDate);
						}
						else
						{
							 formatedDate = "";
							 System.out.println("formatedDate:"+formatedDate);
						}
					%>
					<TR align="left">
						<TD align="left" valign="top" class="ColumnBackground" colspan="3">
							<bean:message key="bankAssistance" />
						</TD>
						<TD align="left" class="TableData" colspan="4"> 
							<bean:write name="gmPeriodicInfoForm" property="borrowerDetails.assistedByBank"/>
						</TD>								
					</TR>
					<TR align="left">
						<TD align="left" valign="top" class="ColumnBackground" colspan="3"><bean:message key="osAmt"/></td>								
						<TD align="left" valign="top" class="TableData" colspan="4">
						<%=decimalFormat.format(borrowerDetails.getOsAmt())%>
<!--						<bean:write property="borrowerDetails.osAmt" name="gmPeriodicInfoForm"/>-->
						<bean:message key="inRs"/>
						</TD>
					 </TR>
					 <TR align="left">
						<TD align="left" valign="top" class="ColumnBackground" colspan="3">
							<bean:message key="npa" />
						</TD>
						<TD align="left" class="TableData" colspan="4"> 
							<bean:write name="gmPeriodicInfoForm" property="borrowerDetails.npa"/>
						</TD>								
					</TR>

					<TR align="left">
						<TD align="left" valign="top" class="ColumnBackground" colspan="3">
							<bean:message key="coveredByCGTSI" />
						</TD>
						<TD align="left" class="TableData" colspan="4"> 
							<bean:write name="gmPeriodicInfoForm" property="borrowerDetails.previouslyCovered" />
						</TD>								
					</TR>
<%--					 <TR align="left">
						<TD align="left" valign="top" class="ColumnBackground" colspan="3"><bean:message key="acNo"/></td>								
						<TD align="left" valign="top" class="TableData" colspan="4"><html:text property="acNo" size="20" alt="uniqueAcctNo" name="gmPeriodicInfoForm"/>	
						</TD>
					 </TR>
--%>

					<TR>
						<TD colspan="7">
							<TABLE width="100%" border="0" cellpadding="1" cellspacing="1">
								 <TR>
									<TD class="SubHeading"><bean:message key="unitHeader" /></TD>	</TR>
										
								 <TR align="left">
									<TD align="left" valign="top" class="ColumnBackground" width="30%"><bean:message key="constitution"/>
									</TD>
									<TD align="left" valign="top" class="TableData" colspan="5">
										<bean:write property="borrowerDetails.ssiDetails.constitution" name="gmPeriodicInfoForm"/>
												
									
									<bean:write property="borrowerDetails.ssiDetails.constitutionOther" name="gmPeriodicInfoForm" />
									</TD>
								 </TR>
											
								 <TR align="left">
									<TD align="left" valign="top" class="ColumnBackground" width="30%"><bean:message key="unitName"/>
									</TD>												
									<TD align="left" valign="top" class="TableData">
										<bean:write property="borrowerDetails.ssiDetails.ssiType" name="gmPeriodicInfoForm"/>
											
										<bean:write property="borrowerDetails.ssiDetails.ssiName"  name="gmPeriodicInfoForm"/>	
									 </td>
									<TD align="left" valign="top" class="ColumnBackground" width="25%"><bean:message key="ssiRegNo"/>
									</td>
									<TD align="left" valign="top" class="TableData" width="15%"><bean:write property="borrowerDetails.ssiDetails.regNo"  name="gmPeriodicInfoForm"/>				
									</td>
								</tr>
								 <TR align="left">
<%--								<TD align="left" valign="top" class="ColumnBackground" 													width="30%"><bean:message key="commencementDate"/>
									</TD>
									
									<TD align="left" valign="top" class="TableData">
									<html:text property="commencementDate" size="20" alt="Commencement Date" name="gmPeriodicInfoForm"/><IMG src="images/CalendarIcon.gif" width="20" onClick="showCalendar('gmPeriodicInfoForm.commencementDate')" align="center">
									 </td>
--%>
									<TD align="left" valign="top" class="ColumnBackground" width="25%"><bean:message key="firmItpan"/>
									</td>
									<TD align="left" valign="top" class="TableData" width="15%" colspan="4"><bean:write property="borrowerDetails.ssiDetails.ssiITPan"  name="gmPeriodicInfoForm" />	
									</td>
								</tr>
								 <TR align="left">
									<TD align="left" valign="top" class="ColumnBackground" width="30%"><bean:message key="industryNature"/>
									</TD>												
									<TD align="left" valign="top" class="TableData">
										<bean:write property="borrowerDetails.ssiDetails.industryNature"  name = "gmPeriodicInfoForm"/>						
									</td>
									<TD align="left" valign="top" class="ColumnBackground" width="25%"><bean:message key="industrySector"/>
									</td>
									<TD align="left" valign="top" class="TableData">
										<bean:write property="borrowerDetails.ssiDetails.industrySector" name="gmPeriodicInfoForm"/>
																		
									</td>
								</tr>

								<TR align="left">
									<TD align="left" valign="top" class="ColumnBackground" width="30%"><bean:message key="activitytype"/>
									</TD>												
									<TD align="left" valign="top" class="TableData">
									<bean:write property="borrowerDetails.ssiDetails.activityType"  name="gmPeriodicInfoForm"/>				
									</td>
									<TD align="left" valign="top" class="ColumnBackground" width="25%"><bean:message key="noOfEmployees"/>
									</td>
									<TD align="left" valign="top" class="TableData" width="15%"><bean:write property="borrowerDetails.ssiDetails.employeeNos"  name="gmPeriodicInfoForm"/>	
									</td>
								</tr>
     							 <TR align="left">
									<TD align="left" valign="top" class="ColumnBackground" width="30%"><bean:message key="turnover"/>
									</TD>												
									<TD align="left" valign="top" class="TableData">
										<bean:write property="borrowerDetails.ssiDetails.projectedSalesTurnover"  name="gmPeriodicInfoForm"/><bean:message key="inRs"/>
									</td>
									<TD align="left" valign="top" class="ColumnBackground" width="25%"><bean:message key="exports"/>
									</td>
									<TD align="left" valign="top" class="TableData" width="15%">
										<bean:write property="borrowerDetails.ssiDetails.projectedExports"  name="gmPeriodicInfoForm"/>&nbsp;&nbsp;<bean:message key="inRs"/>
										</td>
									</tr>

									<TR align="left">											
										<TD align="left" valign="top" class="ColumnBackground" width="30%"><bean:message key="address"/>
										</td>
										<TD align="left" valign="top" class="TableData" width="30%">
											<bean:write property="borrowerDetails.ssiDetails.address" name="gmPeriodicInfoForm"/>						
										</td>
										<TD align="left" valign="top" class="ColumnBackground" width="25%"><bean:message key="state"/>
										</TD>												
										<TD align="left" valign="top" class="TableData" width="15%">
											<bean:write property="borrowerDetails.ssiDetails.state" name="gmPeriodicInfoForm" />									
										</td>
									</tr>
									 <TR align="left">
										<TD align="left" valign="top" class="ColumnBackground" width="15%"><bean:message key="district"/>
										</TD>												
										<TD align="left" valign="top" class="TableData" >
										<bean:write property="borrowerDetails.ssiDetails.district" name="gmPeriodicInfoForm"/>
										
<%--										<bean:write property="borrowerDetails.ssiDetails.districtOthers"  name="gmPeriodicInfoForm" /><bean:message key="others"/>		--%>
										</td>
										<TD align="left" valign="top" class="ColumnBackground" width="25%"><bean:message key="city"/>
										</td>
										<TD align="left" valign="top" class="TableData" width="15%">
										<bean:write property="borrowerDetails.ssiDetails.city"   name="gmPeriodicInfoForm"/>	</td>
									</tr>

									<TR align="left">
										<TD align="left" valign="top" class="ColumnBackground" width="30%"><bean:message key="pinCode"/>
										</td>
										<TD align="left" valign="top" class="TableData" width="70%" colspan="5">
										<bean:write property="borrowerDetails.ssiDetails.pincode"  name="gmPeriodicInfoForm"/>		
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
								<bean:write property="borrowerDetails.ssiDetails.cpTitle" name="gmPeriodicInfoForm"/>
							</TD>
							<TD align="left" valign="top" class="TableData">
								<bean:write property="borrowerDetails.ssiDetails.cpFirstName"  name="gmPeriodicInfoForm"/>	
							</TD>
							<TD align="left" valign="top" class="TableData">
								<bean:write property="borrowerDetails.ssiDetails.cpMiddleName"  name="gmPeriodicInfoForm"/>	
							</TD>
							<TD align="left" valign="top" class="TableData">
								<bean:write property="borrowerDetails.ssiDetails.cpLastName"   name="gmPeriodicInfoForm"/>	
							</TD>
						</TR>

						<TR align="left">
							<TD align="left" valign="top" class="ColumnBackground" colspan="2"><bean:message key="gender"/>
							</TD>
							<TD align="left" valign="top" class="TableData" width="20%">
								<bean:write name="gmPeriodicInfoForm"  property="borrowerDetails.ssiDetails.cpGender" />
								<!--<bean:message key="male" />-->
							</TD>
							<TD align="left" valign="top" class="ColumnBackground">
								<bean:message key="chiefItpan" />
							</TD>
							<TD align="left" valign="top" class="TableData" colspan="2">
								<bean:write property="borrowerDetails.ssiDetails.cpITPAN"  name="gmPeriodicInfoForm"/>
							</TD>													
						</TR>

						<TR align="left">
							<TD align="left" valign="top" class="ColumnBackground" colspan="2">
										<bean:message key="dob" />					
							</TD>
							<TD align="left" valign="top" class="TableData">
								<%=formatedDate%>
							</TD>
							<TD align="left" valign="top" class="ColumnBackground">
								<bean:message key="socialCategory"/>
							</TD>
							<TD align="left" valign="top" class="TableData" width="20%" colspan="3">
								<bean:write property="borrowerDetails.ssiDetails.socialCategory" name="gmPeriodicInfoForm"/>
						</TR>

						<TR align="left">
							<TD align="left" valign="top" class="ColumnBackground" colspan="2">
							<bean:message key="legalid" />					
							</TD>
									<TD align="left" valign="top" class="TableData" colspan="5">
										<bean:write property="borrowerDetails.ssiDetails.cpLegalID" name="gmPeriodicInfoForm" />
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
													<bean:write property="borrowerDetails.ssiDetails.firstName" name="gmPeriodicInfoForm"/></span></b>
												</td>
												<td width="17%" class="ColumnBackground" >
													<b><span style="font-size: 9pt"> <bean:message key="promoterItpan" /></span></b>
												</td>
												<td width="17%" class="TableData">
													<span style="font-size: 9pt"> 
													<bean:write property="borrowerDetails.ssiDetails.firstItpan" name="gmPeriodicInfoForm"/></span>
												</td>
												<td width="17%" class="ColumnBackground" >
													<b><span style="font-size: 9pt"><bean:message key="promoterDob" /></span></b>
												</td>
												<td width="17%" class="TableData">
													<span style="font-size: 9pt"> 
													 <bean:write property="borrowerDetails.ssiDetails.firstDOB" name="gmPeriodicInfoForm"/>
													</span>
												</td>
											</tr>
											 <tr>
												<td class="ColumnBackground" width="17%"><b>
													<span style="font-size: 9pt">2. <bean:message key="promoterName" />	</span></b>
												</td>
												<td width="15%" class="TableData">
													<span style="font-size: 9pt"> 
													<bean:write property="borrowerDetails.ssiDetails.secondName" name="gmPeriodicInfoForm"/></span>
												</td>
												<td width="17%" class="ColumnBackground" >
													<b><span style="font-size: 9pt"> <bean:message key="promoterItpan" /></span></b>
												</td>
												<td width="17%" class="TableData">
													<span style="font-size: 9pt"> 
													<bean:write property="borrowerDetails.ssiDetails.secondItpan" name="gmPeriodicInfoForm"/></span>
												</td>
												<td width="17%" class="ColumnBackground" >
													<b><span style="font-size: 9pt"><bean:message key="promoterDob" /></span></b>
												</td>
												<td width="17%" class="TableData">
													<span style="font-size: 9pt"> 
													<bean:write property="borrowerDetails.ssiDetails.secondDOB" name="gmPeriodicInfoForm"/>
													</span>
												</td>
											</tr>

											 <tr>
												<td class="ColumnBackground" width="17%"><b>
													<span style="font-size: 9pt">3. <bean:message key="promoterName" />	</span></b>
												</td>
												<td width="15%" class="TableData">
													<span style="font-size: 9pt"> 
													<bean:write property="borrowerDetails.ssiDetails.thirdName" name="gmPeriodicInfoForm"/></span>
												</td>
												<td width="17%" class="ColumnBackground" >
													<b><span style="font-size: 9pt"> <bean:message key="promoterItpan" /></span></b>
												</td>
												<td width="17%" class="TableData">
													<span style="font-size: 9pt"> 
													<bean:write property="borrowerDetails.ssiDetails.thirdItpan" name="gmPeriodicInfoForm"/></span>
												</td>
												<td width="17%" class="ColumnBackground" >
													<b><span style="font-size: 9pt"><bean:message key="promoterDob" /></span></b>
												</td>
												<td width="17%" class="TableData">
													<span style="font-size: 9pt"> 
													<bean:write property="borrowerDetails.ssiDetails.thirdDOB" name="gmPeriodicInfoForm"/>
													</span>
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
							</TABLE>			<!--table closing for borrower details-->

				
							


				<TABLE width="100%" border="0" align="left" cellpadding="0" cellspacing="0">			
					<TR>
						<TD align="center" valign="baseline" >
							<DIV align="center">
								
								<A href="javascript:history.back()"><IMG src="images/Back.gif" alt="Back" width="49" height="37" border="0"></A>
								
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










