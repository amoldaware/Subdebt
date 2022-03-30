<%@ page language="java"%>
<%@ taglib uri="/WEB-INF/TLD/struts-html.tld" prefix="html" %>
<%@ taglib uri="/WEB-INF/TLD/struts-bean.tld" prefix="bean" %>
<%@ taglib uri="/WEB-INF/TLD/struts-logic.tld" prefix="logic" %>

<%@ page import="com.cgtsi.guaranteemaintenance.NPADetails"%>
<%@ page import="com.cgtsi.guaranteemaintenance.RecoveryProcedure"%>
<%@ page import="com.cgtsi.admin.MenuOptions"%>
<%@ include file="/jsp/SetMenuInfo.jsp" %>
<%@ page import="java.util.Date"%>
<%@ page import="java.util.ArrayList"%>
<%@ page import="java.text.SimpleDateFormat"%>

<% 
String acType = null;

String name = null;
String dtls = null;
String acDate = null;
String fileName = null;
SimpleDateFormat dateFormat=new SimpleDateFormat("dd/MM/yyyy");

String recId = null;

// session.setAttribute("CurrentPage","showNPADetails.do?method=showNPADetails");
String menu = (String)session.getAttribute("mainMenu");
String subMenu = (String)session.getAttribute("subMenuItem");
// System.out.println("**** - Main Menu :" + menu);
// System.out.println("**** - Sub Menu :" + subMenu);
if(menu!=null && subMenu!=null)
{
	if((menu.equals(MenuOptions.getMenu(MenuOptions.GM_PERIODIC_INFO))) &&
	  (subMenu.equals(MenuOptions.getMenu(MenuOptions.GM_PI_NPA_DETAILS))))
	  {
		 session.setAttribute("CurrentPage","showNPADetails.do?method=showNPADetails");
	  }
	if((menu.equals(MenuOptions.getMenu(MenuOptions.CP_CLAIM_FOR))) &&
	  (subMenu.equals(MenuOptions.getMenu(MenuOptions.CP_CLAIM_FOR_FIRST_INSTALLMENT))))
	  {
		 session.setAttribute("CurrentPage","getBorrowerId.do?method=setBankId");
	  }
}
%>
<body onLoad="enableReportingDate(),setForumOthersEnabled(),checkProceedings()">
<table width="725" border="0" cellpadding="0" cellspacing="0">
	<html:errors />
	<html:form action="saveNpaDetails.do?method=saveNpaDetails" method="POST" enctype="multipart/form-data" focus="npaDate">
	<html:hidden property="noOfActions" name ="gmPeriodicInfoForm" value="0" />
<%--	<html:hidden property="recActionType" name ="gmPeriodicInfoForm"/> --%>

		<TR> 
			<TD width="20" align="right" valign="bottom"><IMG src="images/TableLeftTop.gif" width="20" height="31"></TD>
			<TD background="images/TableBackground1.gif"><IMG src="images/GuaranteeMaintenanceHeading.gif"></TD>
			<TD width="20" align="left" valign="bottom"><IMG src="images/TableRightTop.gif" width="23" height="31"></TD>
		</TR>
		<TR>
			<TD width="20" background="images/TableVerticalLeftBG.gif">&nbsp;</TD>
			<TD>
			<DIV align="right">			
				<A HREF="javascript:submitForm('helpNpaDetails.do?method=helpNpaDetails')">
			    HELP</A>
			</DIV>
				<table width="100%" border="0" align="left" cellpadding="0" cellspacing="0">
					<TR>
						<TD >
							<table width="100%" border="0" cellspacing="1" cellpadding="1">
								<TR>
									<TD colspan="4"> 
										<table width="100%" border="0" cellpadding="0" cellspacing="0">
											<TR>
												<td width="35%" class="Heading">&nbsp; 			<bean:message key="NPADetailsHeader"/>&nbsp; for &nbsp;<bean:write name = "gmPeriodicInfoForm" property = "borrowerId"/>
												</TD>

												 <td  align="left" valign="bottom"><img src="images/TriangleSubhead.gif" width="19" height="19"></td>
										  <td align="right"> <div align="right"> </div></td>
											</TR>

											<TR>
												<TD colspan="6" class="Heading">
												<%
												String srcMenu = (String)session.getAttribute("mainMenu");
												String srcSubMenu = (String)session.getAttribute("subMenuItem");
												if((srcMenu != null) && (srcSubMenu != null) || srcMenu != null)
												{
												   if(srcMenu.equals(MenuOptions.getMenu(MenuOptions.GM_PERIODIC_INFO)))												   
												   {
												%>
												<img src="images/Clear.gif" height="5">
											  <A href="javascript:submitForm('showOutstandingDetailsLink.do?method=showOutstandingDetailsLink')">Outstanding Details</A>|
											   <A href="javascript:submitForm('showDisbursementDetailsLink.do?method=showDisbursementDetailsLink')">Disbursement
											  Details</A> | <A href="javascript:submitForm('showRepaymentDetailsLink.do?method=showRepaymentDetailsLink')">Repayment Details</A>| <A href="javascript:submitForm('showRecoveryDetailsLink.do?method=showRecoveryDetailsLink')">Recovery Details</A>
												</TD>
												<%
												   }
												}
												%>
											</TR>

											<TR>
												<TD colspan="6" class="SubHeading">&nbsp;<bean:message key = "BorrowerID"/>
												<bean:write name="gmPeriodicInfoForm"property="borrowerId"/><font	  color="#FFFFFF"></font>&nbsp;&nbsp;&nbsp;&nbsp;<font color="#FFCCCC"><strong>&nbsp;</strong></font>
												</TD>
											</TR>
										</table>
									</TD>
								</TR>


								<TR>
									<TD class="ColumnBackground">&nbsp;&nbsp;<font color="#FF0000" size="2">*</font><bean:message key="npaDate"/>
									</TD>
									<TD class="tableData">
										<table cellpadding="0" cellspacing="0">
											<TR>
												<TD><html:text property="npaDate" size="8" maxlength="10" alt ="NPA turned Date" name="gmPeriodicInfoForm" />
												</TD>
												<TD><IMG src="images/CalendarIcon.gif" width="20" onClick="showCalendar('gmPeriodicInfoForm.npaDate')" align="center">
												</TD>
											</TR>
										 </table>
								     </TD>
							    </TR>
								<TR>
									<TD class="ColumnBackground">&nbsp;<font color="#FF0000" size="2">*</font><bean:message key="osAmtOnNPA"/>
									</TD>
									<TD class="tableData"><html:text property="osAmtOnNPA"  maxlength="16" name="gmPeriodicInfoForm"  onkeypress="return decimalOnly(this, event,13)" onkeyup="isValidDecimal(this)" maxlength="16"/><bean:message key="inRs"/>
									</TD>
								</TR>
								 <TR>
									 <TD class="ColumnBackground" align="left">&nbsp;&nbsp;<font color="#FF0000" size="2">*</font><bean:message key="whetherNPAReported"/>
									 </TD>
									 <TD class="tableData"><html:radio name="gmPeriodicInfoForm" 			value="Y"
										  property="whetherNPAReported" onclick="enableReportingDate()"> <bean:message key="yes"/> </html:radio>&nbsp; <html:radio name="gmPeriodicInfoForm" value="N" property="whetherNPAReported" onclick="enableReportingDate()"><bean:message key="no"/></html:radio>
									  </TD>
								</TR>
								<TR>
									<TD class="ColumnBackground">&nbsp;<bean:message key="reportingDate"/>
									</TD>
									<TD class="tableData">
										<table cellpadding="0" cellspacing="0">
											<TR>
												<TD><html:text property ="reportingDate"size="8" maxlength="10" name="gmPeriodicInfoForm"/>
												</TD>
												<TD><IMG src="images/CalendarIcon.gif" width="20" onClick="showCalendar('gmPeriodicInfoForm.reportingDate')" align="center">
												</TD>
											</TR>
										 </table>
									</TD>
								 </TR>
								 <TR>
									<TD class="ColumnBackground">&nbsp;&nbsp;<font color="#FF0000" size="2">*</font><bean:message key="npaReason"/>
									</TD>
									<TD class="tableData"><html:textarea property="npaReason" cols="40" name="gmPeriodicInfoForm"/>
									</TD>
								</TR>
								<TR>
									<TD class="ColumnBackground">&nbsp;&nbsp;<font color="#FF0000" size="2">*</font><bean:message key="defaulter"/>
									</TD>
									<TD class="tableData"><html:select 										property="willfulDefaulter" name= "gmPeriodicInfoForm">   
										<html:option value="">Select</html:option>
										<html:option value="Y">Yes</html:option>
										<html:option value="N">No</html:option>
										</html:select>
									</TD>
								 </TR>
							     <TR>
									<TD class="ColumnBackground">&nbsp;&nbsp;<bean:message 					key="enumerateEfforts"/>
									</TD>
									<TD class="tableData"><html:textarea 									property="effortsTaken" cols="40" name="gmPeriodicInfoForm"/>
									</TD>
								</TR>
										<%String recValue = (String)session.getAttribute("recInitiated");
										if(recValue!=null && recValue.equals("Y")){%>
									<TD colspan="1" 									  		class="ColumnBackground">&nbsp;<font color="#FF0000" size="2">*</font><bean:message key="isRecoveryInitiated"/>
									</TD>
									<TD colspan="3" class="tableData" >
									  <html:radio value="Y" name="gmPeriodicInfoForm" property="isRecoveryInitiated" 	onclick="checkProceedings()"/><bean:message key="yes" />
									  <html:radio value="N" name="gmPeriodicInfoForm" property="isRecoveryInitiated" onclick="checkProceedings()" disabled="true"/><bean:message key="no"/>
									</TD>
									<%}
									else
									{%>
									<TD colspan="1" 									  		class="ColumnBackground">&nbsp;<font color="#FF0000" size="2">*</font><bean:message key="isRecoveryInitiated"/>
									</TD>
									<TD colspan="3" class="tableData" >
									  <html:radio value="Y" name="gmPeriodicInfoForm" property="isRecoveryInitiated" 	onclick="checkProceedings()"/><bean:message key="yes" />
									  <html:radio value="N" name="gmPeriodicInfoForm" property="isRecoveryInitiated" onclick="checkProceedings()"/><bean:message key="no"/>
									</TD>
									<%}%>
								</TR>

								<TR>
									<TD colspan="6" class="SubHeading">&nbsp;<bean:message 					key="recoveryProcedure"/>
									</TD>
								</TR>
								<TR>
									<TD align=middle colspan="6">
										<table width="100%" border="0" cellspacing="1" cellpadding="0"  class="TableData" >

											<TR>
												<TD class="ColumnBackground" 								align="left">&nbsp;<bean:message key="actionType"/>
												</TD>
												<TD class="ColumnBackground" 								align="left">&nbsp;<bean:message key="details"/>
												</TD>
												<TD class="ColumnBackground" 								align="left">&nbsp;<bean:message key="date"/>
												</TD>
												<TD class="ColumnBackground" >&nbsp;<bean:message 			key="attachment"/>
												</TD>
											</TR>
<%--											<TR>
											<TD align=middle colspan="6">
											<TABLE  width="100%" border="0" cellspacing="1" cellpadding="0" id="addRecovery">			
											--%>
	
											<bean:define id="obj" name="gmPeriodicInfoForm" property="recoveryProcedures" />

											<%
												ArrayList procs = (ArrayList)obj; 
											%>
											<bean:define id="previous" name="gmPeriodicInfoForm" property="recProcedures" />
											<%
											java.util.Map recProcs =(java.util.Map)previous;
											int size = recProcs.size();
												int i = 0;
												String actypeKey = null;
												String acdtlKey = null;
												String acdateKey = null;
												String fileKey = null;
												boolean isExecuted=false;

											com.cgtsi.guaranteemaintenance.RecoveryProcedureTemp procedureTemp =new com.cgtsi.guaranteemaintenance.RecoveryProcedureTemp();
												
											Boolean isRequired=(Boolean)request.getAttribute("IsRecProcRequired");

											%>
											<logic:iterate id="object" name="gmPeriodicInfoForm" property = "recProcedures">
											<%

												actypeKey = "recProcedures(key-"+i+").actionType";
												
												acdtlKey = "recProcedures(key-"+i+").actionDetails";
												
												acdateKey = "recProcedures(key-"+i+").actionDate";

												fileKey = "recProcedures(key-"+i+").attachmentName";

												isExecuted=true;

											%>

											<TR>
											<%
											if(procs.size()>i){
												RecoveryProcedure recProc = (RecoveryProcedure)procs.get(i);
												if(recProc!=null)
												{
													recId = recProc.getRadId();
												}
											}
											
													name="radId(key-"+i+")";						
/*											if(recId!=null && !recId.equals(""))
											{
												System.out.println("rad id 1 :" + recId + "i :" + i);
												name="radId(key-"+i+")";
											%>
											<html:hidden property = "<%=name%>" name="gmPeriodicInfoForm" value="<%=recId%>"/>
											<%
											//}
											%>

											<TD class="tableData" align="center">
										  
											  <html:select property="<%=actypeKey%>" name = "gmPeriodicInfoForm" >
											  <html:option value="">Select</html:option>
											  <html:options property="recTypes" name="gmPeriodicInfoForm"/>	
											  </html:select>
											</TD>

											<TD class="tableData" valign="top" align="center">
											  <html:textarea property = "<%=acdtlKey%>"  name="gmPeriodicInfoForm"/>
											</TD>	
											
											<TD class="tableData" valign="top"	align="center">
												 <html:text property = "<%=acdateKey%>" maxlength="10" name="gmPeriodicInfoForm" />

											</TD>
 
											<TD class="tableData" valign="top" align="center">
											<html:file property="<%=fileKey%>" maxlength = "200" name="gmPeriodicInfoForm"/>
											<%if(((size-1)==i) && isRequired==null)
											{%>
											<a href="javascript:submitForm('addMoreRecoProcs.do?method=addMoreRecoProcs&addmoreflag=set')"> AddMore</a>
											<%}%>
											</TD>
											<% ++i; %>
											
											</TR>						

											</logic:iterate>
											<%if(!isExecuted || (isExecuted && isRequired!=null))
											{
												recProcs.put("key-"+i,procedureTemp);

												actypeKey = "recProcedures(key-"+i+").actionType";
												
												acdtlKey = "recProcedures(key-"+i+").actionDetails";
												
												acdateKey = "recProcedures(key-"+i+").actionDate";

												fileKey = "recProcedures(key-"+i+").attachmentName";
											
												%>

											<TD class="tableData" align="center">
										  
											  <html:select property="<%=actypeKey%>" name = "gmPeriodicInfoForm" >
											  <html:option value="">Select</html:option>
											  <html:options property="recTypes" name="gmPeriodicInfoForm"/>	
											  </html:select>
											</TD>

											<TD class="tableData" valign="top" align="center">
											  <html:textarea property = "<%=acdtlKey%>"  name="gmPeriodicInfoForm"/>
											</TD>	
											
											<TD class="tableData" valign="top"	align="center">
												 <html:text property = "<%=acdateKey%>" size="10" name="gmPeriodicInfoForm" />

											</TD>
 
											<TD class="tableData" valign="top" align="center">
											<html:file property="<%=fileKey%>" maxlength = "200" name="gmPeriodicInfoForm"/><a href="javascript:submitForm('addMoreRecoProcs.do?method=addMoreRecoProcs')"> AddMore</a>
											</TD>
										<% 
												++i;}%>

										<%--</TABLE> 
										</TD>--%>
										</tr>
											
										</table>
									</td>	
								</tr>	

<html:hidden property = "npaIndex" name="gmPeriodicInfoForm" value="<%=String.valueOf(i)%>"/>
								<TR>
					               <TR>
										<TD colspan="6" class="SubHeading">&nbsp; <bean:message key = "legalDetails"/>
										</TD>
								   </TR>
					               <TR>
						                  <TD colspan="6" class="SubHeading">
												<table width="100%" border="0" cellspacing="1" cellpadding="0">
													<TR>
														<TD class="ColumnBackground">&nbsp; <font color="#FF0000" size="2">*</font><bean:message key = "forum"/>
														</TD>
														<TD class="tableData" colspan="3"> 
														<html:select name="gmPeriodicInfoForm" property = "courtName" onchange="setForumOthersEnabled()">
														<html:option value="">Select</html:option>
														<html:option value="Civil Court">Civil Court</html:option>
														<html:option value = "DRT">DRT</html:option>
														<html:option value="LokAdalat">Lok Adalat</html:option>
														<html:option value = "Revenue Recovery Autority">Revenue Recovery Authority</html:option>
														<html:option value = "Securitisation Act ">Securitisation Act, 2002</html:option>
														<html:option value = "others">Others</html:option>
														</html:select>
														<html:text name="gmPeriodicInfoForm" property = "initiatedName"size="20" maxlength="100"/>
													
														</TD>
													</TR>
												<TR>
							                        <TD class="ColumnBackground">&nbsp;<font color="#FF0000" size="2">*</font><bean:message key = "suit"/>
													</TD>

													<%--<bean:define id="npaid" name="gmPeriodicInfoForm" property="npaId" /> --%>

													<%String npid = (String)session.getAttribute("npaAvailable");
													if (npid==null){%>
													<TD class="tableData" ><html:text name="gmPeriodicInfoForm"property = "legalSuitNo"  size="20" maxlength="50"/>
													</TD>
													<%}else {%>
 													<TD class="tableData" ><html:text name="gmPeriodicInfoForm"property = "legalSuitNo"  size="20" maxlength="50"/>
													</TD>
													<%}%>

													<TD class="ColumnBackground">&nbsp;<font color="#FF0000" size="2">*</font><bean:message key="filingdate"/>
													</TD>
													<TD class="tableData"  ><html:text property = "dtOfFilingLegalSuit" size="10" maxlength="10"/><img src="images/CalendarIcon.gif" width="20" onClick="showCalendar('gmPeriodicInfoForm.dtOfFilingLegalSuit')" align="center">
													</TD>
												</TR>
												<TR>
							                        <TD class="ColumnBackground">&nbsp;<font color="#FF0000" size="2">*</font><bean:message key = "forumName"/>
													</TD>
													<TD class="tableData">
														<html:text name="gmPeriodicInfoForm" property = "forumName" size="20" maxlength="50"/>
													</TD>
							                        <TD class="ColumnBackground">&nbsp;<font color="#FF0000" size="2">*</font><bean:message key="location"/>
													</TD>
													<TD class="tableData">
														<html:text property="location" name = "gmPeriodicInfoForm" size="20" maxlength="50"/>
													</TD>
												</TR>
												<TR>
							                        <TD class="ColumnBackground">&nbsp;<bean:message key="amountClaimed"/>
													</TD>
													<TD class="tableData"  colspan="3">
														<html:text property="amountClaimed" name = "gmPeriodicInfoForm" maxlength="16" onkeypress="return decimalOnly(this, event,13)" onkeyup="isValidDecimal(this)" maxlength="16"/><bean:message key="inRs" />
													</TD>
												</TR>
												<TR>
													<TD class="ColumnBackground">&nbsp;<bean:message key="currentStatus"/>
													</TD>
													<TD class="tableData" colspan="3">
													<html:textarea property = "currentStatus" name = "gmPeriodicInfoForm" cols="30"/>
													</TD>
												</TR>
												<TR>
							                        <TD class="ColumnBackground">&nbsp;<bean:message key="recoveryProceedingsConcluded"/>
													</TD>
													<TD class="tableData"  colspan="3">
													<html:radio value="Y" name = "gmPeriodicInfoForm" property="recoveryProceedingsConcluded"><bean:message key = "yes"/></html:radio>
													<html:radio value="N" name = "gmPeriodicInfoForm" property="recoveryProceedingsConcluded"><bean:message key = "no"/></html:radio>
													</TD>
												</TR>
											</table>
										</TD>
									</TR>
				                <tr align="center">
								  <TD colspan="4" class="ColumnBackground"        							align="left">&nbsp;<bean:message key="dateOfConclusion"/>
				                   <html:text property ="effortsConclusionDate" size="8" maxlength="10" name="gmPeriodicInfoForm"/><img
								   src="images/CalendarIcon.gif" onClick="showCalendar('gmPeriodicInfoForm.effortsConclusionDate')" width="20" align="center">
								</TD>
			                </TR>
						  </table>
		              </TD>
				    </TR>
		        <tr align="center">
			      <TD colspan="2" class="SubHeading">
					<table width="100%" border="0" cellspacing="1" cellpadding="0">
			            <tr align="center">
					      <TD class="SubHeading"
							 align="left"><bean:message key="others"/>
						  </TD>
						</TR>
						<TR>
							<TD class="ColumnBackground" >&nbsp;<bean:message key="mliComment"/>
							</TD>
							<TD class="tableData" ><html:textarea property ="mliCommentOnFinPosition" 	cols="30" name="gmPeriodicInfoForm"/>
							</TD>
						</TR>
					<TR>
						<TD class="ColumnBackground" >&nbsp;<bean:message key = "finAssistDetails"/>
						</TD>
						<TD class="tableData" ><html:textarea property = "detailsOfFinAssistance" cols="30"name="gmPeriodicInfoForm"/>
						</TD>
					</TR>
					<TR>
						<TD class="ColumnBackground" >&nbsp;<bean:message key="creditSupport"/>
						</TD>
						<TD class="tableData" >
						<html:radio value="Y" property="creditSupport" name="gmPeriodicInfoForm" >
						<bean:message key="yes"/>
						</html:radio>
						<html:radio value="N" property="creditSupport" name="gmPeriodicInfoForm">
						<bean:message key="no"/>
						</html:radio>
						</TD>
					</TR>
					<TR>
						<TD class="ColumnBackground" >&nbsp;<bean:message key="bankFacilityProvided"/>
						</TD>
						<TD class="tableData" ><html:textarea property="bankFacilityDetail" name="gmPeriodicInfoForm" cols="30"/>
						</TD>
					</TR>
					<TR>
						<TD class="ColumnBackground" >&nbsp;<font color="#FF0000" size="2">*</font><bean:message key="placeUnderWatchList"/>
						</TD>
						<TD class="tableData" >
						<html:radio name="gmPeriodicInfoForm" value="Y" property="placeUnderWatchList">
						<bean:message key="yes"/>
						</html:radio>
						<html:radio name="gmPeriodicInfoForm" value="N" property="placeUnderWatchList">
						<bean:message key="no"/>
						  </html:radio>
					  </TD>
                </TR>
                <TR>
                  <TD class="ColumnBackground" >&nbsp;<bean:message key="otherRemarks"/></TD>
                  <TD class="tableData" ><html:textarea name="gmPeriodicInfoForm" property="remarksOnNpa" cols="30"/></TD>
                </TR>


             </table>
				</TD>
					</TR>
					<tr>
						<TD align="center" valign="baseline" >
							<DIV align="center">
								
									<A href="javascript:submitForm('saveNpaDetails.do?method=saveNpaDetails')">
									<IMG src="images/Save.gif" alt="OK" width="49" height="37" border="0"></A>
									<A href="javascript:document.gmPeriodicInfoForm.reset()">
									<IMG src="images/Reset.gif" alt="Cancel" width="49" height="37" border="0"></A>
								<A href="subHome.do?method=getSubMenu&menuIcon=<%=session.getAttribute("menuIcon")%>&mainMenu=<%=session.getAttribute("mainMenu")%>">
								<IMG src="images/Cancel.gif" alt="Cancel" width="49" height="37" border="0"></A>
							</DIV>
						</TD>
					</TR>
				</table>
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
</table>
</body>

