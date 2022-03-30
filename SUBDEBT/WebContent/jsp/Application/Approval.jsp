<%@ page language="java"%>
<%@ taglib uri="/WEB-INF/TLD/struts-html.tld" prefix="html" %>
<%@ taglib uri="/WEB-INF/TLD/struts-bean.tld" prefix="bean" %>
<%@ taglib uri="/WEB-INF/TLD/struts-logic.tld" prefix="logic" %>
<%@ page import="java.util.ArrayList"%>
<%@ page import="java.util.Arrays"%>
<%@ page import="java.util.Map"%>
<%@ page import="java.text.SimpleDateFormat"%>
<%@ page import="java.util.Date"%>
<%@ page import="com.cgtsi.actionform.APForm"%>
<%@ page import="com.cgtsi.application.DuplicateApplication"%>
<%@ page import="com.cgtsi.application.DuplicateCriteria"%>
<%@ page import="com.cgtsi.application.DuplicateCondition"%>
<% session.setAttribute("CurrentPage","approvalApps.do?method=showAppsForApproval");
String name="";
String submittedDate;
String splMessage="";			
SimpleDateFormat dateFormat=new SimpleDateFormat("dd/MM/yyyy");
%>
<%@ include file="/jsp/SetMenuInfo.jsp" %>


<!--<body onLoad="enableClearStatus()"/>-->


<TABLE width="725" border="0" cellpadding="0" cellspacing="0">
	<html:errors />
	<html:form action="approvalApps.do?method=showAppsForApproval" method="POST">
		<TR> 
			<TD width="20" align="right" valign="bottom"><IMG src="images/TableLeftTop.gif" width="20" height="31"></TD>
			<TD background="images/TableBackground1.gif"></TD>
			<TD width="20" align="left" valign="bottom"><IMG src="images/TableRightTop.gif" width="23" height="31"></TD>
		</TR>
		<TR>
			<TD width="20" background="images/TableVerticalLeftBG.gif">&nbsp;</TD>
			<TD>
				<DIV align="right">			
					<A HREF="javascript:submitForm('approvalHelp.do?method=approvalHelp')">
					HELP</A>
				</DIV>

				<TABLE width="100%" border="0" align="left" cellpadding="0" cellspacing="0">
					<TR>
						<TD>
							<table width="661" border="0" cellspacing="1" cellpadding="0">
							 <TR>
								<TD colspan="7">
									<TABLE width="100%" border="0" cellpadding="0" cellspacing="0">
										<TR>
											<TD width="31%" class="Heading"><bean:message key="approvalHeader" /></TD>
											<TD><IMG src="images/TriangleSubhead.gif" width="19" height="19"></TD>
											
											<%
											HttpSession appSession = request.getSession(false);
											APForm apForm = (APForm)appSession.getAttribute("apForm");
											if (apForm.getSpecialMessagesList()!=null && apForm.getSpecialMessagesList().size()!=0)
											{
												ArrayList messagesList=(ArrayList)apForm.getSpecialMessagesList();
												int size=messagesList.size();						
												String msge=(String)messagesList.get(0);
												String smsg = "";

												for (int f=1;f<size;f++)
												{
													smsg = (String)messagesList.get(f);
													splMessage= splMessage + "," + smsg;
												}

												if(splMessage!=null && !(splMessage.equals("")))
												{								
												splMessage = msge + splMessage;
												}else{

													splMessage = msge ;
												}
												
											}



											%>											&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;

											
											<HEAD>


<SCRIPT LANGUAGE="JavaScript">

window.onerror = null;
 var bName = navigator.appName;
 var bVer = parseInt(navigator.appVersion);
 var NS4 = (bName == "Netscape" && bVer >= 4);
 var IE4 = (bName == "Microsoft Internet Explorer"
 && bVer >= 4);
 var NS3 = (bName == "Netscape" && bVer < 4);
 var IE3 = (bName == "Microsoft Internet Explorer"
 && bVer < 4);
 var blink_speed=700;
 var i=0;

if (NS4 || IE4) {
 if (navigator.appName == "Netscape") {
 layerStyleRef="layer.";
 layerRef="document.layers";
 styleSwitch="";
 }else{
 layerStyleRef="layer.style.";
 layerRef="document.all";
 styleSwitch=".style";
 }
}

</script>


</HEAD>
<BODY>
<div id="prem_hint" style="position:relative; left:0; visibility:hidden" class="prem_hint">
<font color="#FF0000"><b><%=splMessage%></b></font>
</div>
<script language="javascript">
if(navigator.appName == "Microsoft Internet Explorer")
{
		Blink('prem_hint');	
}
else
{
//	document.writeln('<blink> <font color="#FF0000"><b><%=splMessage%></b></font> </blink>');

}
</script>
									
										</TR>
										<TR>
											<TD colspan="7" class="Heading"><IMG src="images/Clear.gif" width="8" height="5"></TD>
										</TR>
									</TABLE>
								</TD>
							</TR>
							</table>

							<%
								int count=apForm.getIntApplicationCount();
							%>
							<tr>
								<td class="SubHeading" colspan="7">
									<bean:message key="appsCount"/>
									<%=count%>
								</td>
							<tr>

							<!--Eligible Non Duplicate Applications (Clear Applications)-->
							 <%
								int k=0;
							  
								if (apForm.getEligibleNonDupApps().size()!=0)
								{
									
							%>
								<tr>
										<td class="SubHeading" colspan="7">
										<bean:message key="clearApplications"/>
								</tr>
								<tr>
									<td colspan="7">

									<TABLE width="100%" border="0" cellpadding="1" cellspacing="1">
									<tr> 
									  <td align="center" class="HeadingBg" width="29"><div align="center"><bean:message key = "srNo"/></div>
									  </td>
									  <td class="HeadingBg" align="center"><div align="center"><bean:message key="date"/></div>
									  </td>								  
									  <td class="HeadingBg" align="center"><div align="center"><bean:message 					key="applicationRefNo"/></div>
									  </td>	
									  <td class="HeadingBg" align="center"><div align="center"><bean:message 					key="borrowerRefNo"/></div>
									  </td>		
									  <td class="HeadingBg" align="center"><div align="center"><bean:message key="decision"/></div>
									  </td>
									  <td class="HeadingBg" align="center"><div align="center"><bean:message key="approvedAmount"/></div>
									  </td>								  
									  <td class="HeadingBg" align="center"><div align="center"><bean:message key="comments"/></div>
									  </td>								
								  </tr>				  
							 

							 <logic:iterate id="object" name="apForm" property="eligibleNonDupApps">
							   <%
							   com.cgtsi.application.Application eligibleNonDupApplication = (com.cgtsi.application.Application)object;
							   
							   String appRefNo= eligibleNonDupApplication.getAppRefNo();
							   int ssiRefNo=eligibleNonDupApplication.getBorrowerDetails().getSsiDetails().getBorrowerRefNo();
							   submittedDate=dateFormat.format(eligibleNonDupApplication.getSubmittedDate());
							   double amount=eligibleNonDupApplication.getApprovedAmount();
							   String clearApprovedAmount=(new Double(amount)).toString();

							   String appLoanType=eligibleNonDupApplication.getLoanType();
							   double creditamount=0;
							   if(appLoanType.equals("TC"))
									{									    creditamount=eligibleNonDupApplication.getTermLoan().getCreditGuaranteed();
									   								
									}
									else if(appLoanType.equals("WC"))
									{	
										creditamount=eligibleNonDupApplication.getWc().getCreditFundBased() + 
														eligibleNonDupApplication.getWc().getCreditNonFundBased();
									}
									else if (appLoanType.equals("CC"))
									{													double tcCreditamount=eligibleNonDupApplication.getTermLoan().getCreditGuaranteed();

									double wcCreditamount=eligibleNonDupApplication.getWc().getCreditFundBased() + 
														eligibleNonDupApplication.getWc().getCreditNonFundBased();

									creditamount=tcCreditamount + wcCreditamount;
									
									}
									String clearCreditAmount=(new Double(creditamount)).toString();	

							   %>

							   <tr align="center">
							  
								<td class="tableData" align="center">&nbsp;<%=(k+1)%>
								</td>
								<td class="tableData" align="center" width="10%">&nbsp;<%=submittedDate%>
								</td>
								<td class="tableData" align="center" width="10%">&nbsp;<%name="clearAppRefNo(key-"+k+")";%><a href="afterCGPANPage.do?method=showCgpanList&appRef=<%=appRefNo%>&flag=1"><%=appRefNo%></a>
								<html:hidden property = "<%=name%>" name="apForm" value="<%=appRefNo%>"/>
								</td>
								<td class="tableData" align="center" width="10%"><a href="showborrowerDetails.do?method=showborrowerDetails&ssiRef=<%=ssiRefNo%>"><%=ssiRefNo%></a>							

								</td>	
								<%
									int count1=k;
									%>

								<td  class="TableData" align="center" >
								<bean:define type="java.util.Map" id="clearStatusMap" name="apForm" property="clearStatus"/>

								<%
									Map clearStatusTempMap = apForm.getClearTempMap();
								String clearStatusValue = (String)clearStatusTempMap.get(appRefNo);

								if(request.getParameter("clearStatus(key-"+k+")")==null && ( clearStatusValue.equals("PE") || clearStatusValue.equals("HO") ))
								{
									clearStatusMap.put("key-"+k,clearStatusValue);
								}								

							if(request.getParameter("clearStatus(key-"+k+")")==null && clearStatusValue.equals("PE"))
									{
								%>

									<%name="clearStatus(key-"+k+")";%>
									<div align="center">
										<html:select property="<%=name%>" name="apForm" >
											<html:option value="">Select</html:option>											
											<html:option value="RE">Reject</html:option>
											<html:option value="PE">Pending</html:option>
											<html:option value="AP">Accept</html:option>				
										</html:select></div>
								<%
								}
									else
									{%>
									<%name="clearStatus(key-"+k+")";%>
									<div align="center">
										<html:select property="<%=name%>" name="apForm" >
											<html:option value="">Select</html:option>	
											<html:option value="HO">Hold</html:option>
											<html:option value="RE">Reject</html:option>
											<html:option value="PE">Pending</html:option>
											<html:option value="AP">Accept</html:option>				
										</html:select></div>
									<%}%>

											
								</td>
								
								<td  class="TableData" align="center" >
								<bean:define type="java.util.Map" id="clearAmtMap" name="apForm" property="clearApprovedAmt"/>
								<%if(request.getParameter("clearApprovedAmt(key-"+k+")")!=null)
									{								
										if(request.getParameter("clearApprovedAmt(key-"+k+")").equals(""))
										{
											clearAmtMap.put("key-"+k,new Double(0));
										}
										else
										{
											double doubleAmt = Double.parseDouble(request.getParameter("clearApprovedAmt(key-"+k+")"));

											clearAmtMap.put("key-"+k,new Double(doubleAmt));
										}

									}
								else{

									clearAmtMap.put("key-"+k,clearApprovedAmount);
								}

								%>

									<%name="clearApprovedAmt(key-"+k+")";%>
									<div align="center">
									<html:text property="<%=name%>" alt="approvedAmt" name="apForm" size="15" onkeypress="return decimalOnly(this, event,13)" onkeyup="isValidDecimal(this)" maxlength="16"/></div>
								</td>

							<bean:define type="java.util.Map" id="clearRemarksMap" name="apForm" property="clearRemarks"/>
								<td  class="TableData" align="center" >
								<%
									Map clearRemarksTempMap = apForm.getClearRemMap();
								String clearRemValue = (String)clearRemarksTempMap.get(appRefNo);
								
							if(request.getParameter("clearStatus(key-"+k+")")==null && ( clearStatusValue.equals("PE") || 			clearStatusValue.equals("HO") ))
								{
								clearRemarksMap.put("key-"+k,clearRemValue);
								}
								%>

									<%name="clearRemarks(key-"+k+")";%>
									<div align="center">
									<html:textarea property="<%=name%>" cols="15" alt="remarks" name="apForm" rows="2"/></div>
								</td>
							</tr>
										<%name="clearCreditAmt(key-"+k+")";%>
										<html:hidden property="<%=name%>" name="appForm" value="<%=clearCreditAmount%>"/>

							<%++k;%>
							</logic:iterate>

							 </table>
							</td>
						</tr>
						<%}%>


						<!--Eligible Duplicate Applications (Probable Duplicates Applications)-->
						  <%
								int i=0;
								if (apForm.getEligibleDupApps().size()!=0)
								{
									
							%>
							<tr>
									<td class="SubHeading" colspan="4">
									<bean:message key="probableDuplicateApps"/>
							</tr>

							<tr>
								<td>

								<TABLE width="100%" border="0" cellpadding="1" cellspacing="1">
								<tr> 
								  <td align="center" class="HeadingBg" width="29"><div align="center"><bean:message key = "srNo"/></div>
								  </td>
								  <td class="HeadingBg" align="center"><div align="center"><bean:message key="borrowerId"/></div>
								  </td>
								  <td class="HeadingBg" align="center"><div align="center"><bean:message 					key="oldCgpanNumber"/></div>
								  </td>
								  <td class="HeadingBg" align="center"><div align="center"><bean:message 					key="newAppRefNo"/></div>
								  </td>
								  <td class="HeadingBg" align="center" width="15%"><div align="center"><bean:message key ="details"/></div>
								  </td>
								  <td class="HeadingBg" align = "center"><div align="center"><bean:message key ="existing"/></div>
								  </td>
								  <td class="HeadingBg"  align="center"><div align="center"><bean:message key="newDetails"/></div>
								  </td>					
								  <td class="HeadingBg" align="center"><div align="center"><bean:message key="decision"/></div>
								  </td>
								  <td class="HeadingBg" align="center"><div align="center"><bean:message key="approvedAmount"/></div>
								  </td>								  
								  <td class="HeadingBg" align="center"><div align="center"><bean:message key="comments"/></div>
								  </td>	
							  </tr>							 

							   <logic:iterate id="object" name="apForm" property="eligibleDupApps">
							   <%
									com.cgtsi.application.DuplicateApplication eligibleDupApplication= (com.cgtsi.application.DuplicateApplication)object;
									String borrowerId = eligibleDupApplication.getBorrowerId();
									String oldCgpan = eligibleDupApplication.getOldCgpan();				
									String newAppRefNo = eligibleDupApplication.getNewAppRefNo();		
									ArrayList duplicateConditionList=eligibleDupApplication.getDuplicateCondition();
									double amount1=eligibleDupApplication.getDupApprovedAmount();
								   String dupApprovedAmount=(new Double(amount1)).toString();

									double creditAmount1=eligibleDupApplication.getDupCreditAmount();
								   String dupCreditAmount=(new Double(creditAmount1)).toString();


									int c=duplicateConditionList.size();
									
									String stringVal=new String();
									String jVal=stringVal.valueOf(c);

								%>

		<bean:define type="java.util.Map" id="duplicateStatusMap" name="apForm" property="duplicateStatus"/>
									<%
										Map dupStatusTempMap = apForm.getDupTempMap();
									String dupStatusValue = (String)dupStatusTempMap.get(newAppRefNo);%>

								
								 <tr align="center">
							  
								<td class="tableData" align="center" rowspan="<%=jVal%>">&nbsp;<%=(i+1)%>
								</td>
								<td class="tableData" align="center" rowspan="<%=jVal%>" width="10%">&nbsp;
								<%if (borrowerId!=null)
									{%>
									<%=borrowerId%>
								<%}%>
								</td>
								<td class="tableData" align="center" rowspan="<%=jVal%>" width="10%">&nbsp;

								<%
								if(oldCgpan.substring(0,2).equals("CG") && dupStatusValue.equals("EN"))	
									{%>
								<a href="afterCGPANPage.do?method=showOldAppDetails&cgpan=<%=oldCgpan%>"><%=oldCgpan%></a>
									<%}
								else if (oldCgpan.substring(0,2).equals("CG"))
								{%>
								<a href="afterCGPANPage.do?method=showCgpanList&cgpan=<%=oldCgpan%>&flag=0"><%=oldCgpan%></a>
								<%}
								else
								{%>
								<a href="afterCGPANPage.do?method=showCgpanList&appRef=<%=oldCgpan%>&flag=1"><%=oldCgpan%></a>
								<%}%>
								<html:hidden property="<%=name%>" name="apForm" value="<%=oldCgpan%>"/>
								</td>
								<td class="tableData" align="center" rowspan="<%=jVal%>" width="10%">
									<%name="duplicateAppRefNo(key-"+i+")";%>&nbsp;<a href="afterCGPANPage.do?method=showCgpanList&appRef=<%=newAppRefNo%>&flag=1"><%=newAppRefNo%></a>
									<html:hidden property = "<%=name%>" name="apForm" value="<%=newAppRefNo%>"/>

								</td>									
								<%
									java.util.ArrayList duplicateConditions=(java.util.ArrayList)eligibleDupApplication.getDuplicateCondition();
									int duplicateConditionSize=duplicateConditions.size();

									com.cgtsi.application.DuplicateCondition duplicateCondition=null;

									for (int j=0; j<duplicateConditionSize;j++)
									{
										duplicateCondition=(com.cgtsi.application.DuplicateCondition)duplicateConditions.get(j);
										String conditionName=duplicateCondition.getConditionName();
										String oldValue=duplicateCondition.getExistingValue();
										String newValue=duplicateCondition.getNewValue();
										if(j==0)
										{										
								%>
										<td class="ColumnBackground" align="center"><%=conditionName%></td>
										<td  class="TableData" align="center"><div align="center"><%=oldValue%></div></td>
										<td  class="TableData" align="center"><div align="center"><%=newValue%></div></td>
										<td  class="TableData" align="center" rowspan="<%=jVal%>">


								<%if(request.getParameter("duplicateStatus(key-"+i+")")==null && ( dupStatusValue.equals("PE") || dupStatusValue.equals("HO") ))
								{
									duplicateStatusMap.put("key-"+i,dupStatusValue);
								}

								if(request.getParameter("duplicateStatus(key-"+i+")")==null && dupStatusValue.equals("PE"))
								{
									%>
									<%name="duplicateStatus(key-"+i+")";%>
									<div align="center">
										<html:select property="<%=name%>" name="apForm" >
											<html:option value="">Select</html:option>												
											<html:option value="RE">Reject</html:option>
											<html:option value="PE">Pending</html:option>
											<html:option value="AP">Accept</html:option>
<!--											<html:option value="ATL">Additional TL</html:option>
											<html:option value="WCR">WC Renewal</html:option>
											<html:option value="WEN">WC Enhancement</html:option>		-->
										</html:select></div>
								<%}
								else
								{%>
									<%name="duplicateStatus(key-"+i+")";%>
									<div align="center">
										<html:select property="<%=name%>" name="apForm" >
											<html:option value="">Select</html:option>				
											<html:option value="HO">Hold</html:option>
											<html:option value="RE">Reject</html:option>
											<html:option value="PE">Pending</html:option>
											<html:option value="AP">Accept</html:option>
<!--											<html:option value="ATL">Additional TL</html:option>
											<html:option value="WCR">WC Renewal</html:option>
											<html:option value="WEN">WC Enhancement</html:option>		-->
										</html:select></div>
								<%}%>

											
									</td>
									<td  class="TableData" align="center" rowspan="<%=jVal%>">
					<bean:define type="java.util.Map" id="duplicateAmtMap" name="apForm" property="duplicateApprovedAmt"/>
								<%if(request.getParameter("duplicateApprovedAmt(key-"+i+")")!=null)
									{								
										if(request.getParameter("duplicateApprovedAmt(key-"+i+")").equals(""))
										{
											duplicateAmtMap.put("key-"+i,new Double(0));
										}
										else
										{
											double doubleAmt = Double.parseDouble(request.getParameter("duplicateApprovedAmt(key-"+i+")"));

											duplicateAmtMap.put("key-"+i,new Double(doubleAmt));
										}

									}
								else{

									duplicateAmtMap.put("key-"+i,dupApprovedAmount);
								}

								%>


										<%name="duplicateApprovedAmt(key-"+i+")";%>
										<div align="center">
										<html:text property="<%=name%>" alt="approvedAmt" name="apForm" size="15"  onkeypress="return decimalOnly(this, event,13)" onkeyup="isValidDecimal(this)" maxlength="16" /></div>
									</td>

									<td  class="TableData" align="center" rowspan="<%=jVal%>">
							<bean:define type="java.util.Map" id="dupRemarksMap" name="apForm" property="duplicateRemarks"/>

							<%
									Map dupRemarksTempMap = apForm.getDupRemMap();
								String dupRemValue = (String)dupRemarksTempMap.get(newAppRefNo);

								if(request.getParameter("duplicateStatus(key-"+i+")")==null && ( dupStatusValue.equals("PE") || dupStatusValue.equals("HO") ))
								{
									dupRemarksMap.put("key-"+i,dupRemValue);
								}

								
							%>
										<%name="duplicateRemarks(key-"+i+")";%>
										<div align="center">
										<html:textarea property="<%=name%>" cols="15" alt="remarks" name="apForm" rows="2"/></div>
									</td>

										
										
										<%}	else {											
								%>
										<tr>						

											<td class="ColumnBackground" align="center" ><div align="center"><%=conditionName%></div></td>
											<td  class="tableData" align="center"><div align="center"><%=oldValue%></div></td>
											<td  class="tableData" align="center"><div align="center"><%=newValue%></div></td>										
										<%}%>
										</tr>
										<%name="duplicateCreditAmt(key-"+i+")";%>
										<html:hidden property="<%=name%>" name="appForm" value="<%=dupCreditAmount%>"/>

										<%}++i;
										%>
								</logic:iterate>						

							 </table>
							</td>
						</tr>
						<%}%>


						<!--InEligible Non Duplicate Applications (Probable InEligible Applications)-->
						 <%					
								int a=0;
							  
								if (apForm.getIneligibleNonDupApps().size()!=0)
								{
									
									
							%>
							<tr>
									<td class="SubHeading" colspan="4">
									<bean:message key="probableIneligibleApps"/>
							</tr>
							<tr>
								<td>

								<TABLE width="100%" border="0" cellpadding="1" cellspacing="1">
								<tr>
								  <td align="center" class="HeadingBg" width="29"><div align="center"><bean:message key = "srNo"/></div>
								  </td>
								  <td class="HeadingBg" align="center"><div align="center"><bean:message key="date"/></div>
								  </td>
								  <td class="HeadingBg" align="center"><div align="center"><bean:message key="applicationRefNo"/></div>
								  </td>
								  <td class="HeadingBg" align="center"><div align="center"><bean:message key="eligibleCriteria"/></div>
								  </td>
								  <td class="HeadingBg" align="center" width="15%"><div align="center"><bean:message key ="value"/></div>
								  </td>
								  <td class="HeadingBg" align="center"><div align="center"><bean:message key="decision"/></div>
								  </td>
								  <td class="HeadingBg" align="center"><div align="center"><bean:message key="approvedAmount"/></div>
								  </td>								  
								  <td class="HeadingBg" align="center"><div align="center"><bean:message key="comments"/></div>
								  </td>	
								  						 						
							  </tr>						   

								<logic:iterate id="object" name="apForm" property="ineligibleNonDupApps">
								 <%
									com.cgtsi.application.EligibleApplication eligibleApplication= (com.cgtsi.application.EligibleApplication)object;

									String submissionDate=eligibleApplication.getSubmissiondate();
									String appRefNo = eligibleApplication.getAppRefNo();				
									String passedValues = eligibleApplication.getPassedCondition();	
									String failedValues = eligibleApplication.getFailedCondition();	
									String messages = eligibleApplication.getMessage();			
									double amount2=eligibleApplication.getEligibleApprovedAmount();
								   String eligibleApprovedAmount=(new Double(amount2)).toString();

									double creditAmount2=eligibleApplication.getEligibleCreditAmount();
								   String eligibleCreditAmount=(new Double(creditAmount2)).toString();

								
								%>

								 <tr align="center">
							  
									<td class="tableData" align="center" rowspan="3">&nbsp;<%=(a+1)%>
									</td>
									<td class="tableData" align="center" width="10%" rowspan="3">&nbsp;<%=submissionDate%>
									</td>
									<td class="tableData" align="center" width="10%" rowspan="3">&nbsp;<%name="ineligibleAppRefNo(key-"+a+")";%><a href="afterCGPANPage.do?method=showCgpanList&appRef=<%=appRefNo%>&flag=1"><%=appRefNo%></a>
									<html:hidden property = "<%=name%>" name="apForm" value="<%=appRefNo%>"/>
									</td>
									
										<td class="TableData" align="center"><%=failedValues%></td>
											<td class="TableData" align="center">False</td>
											<td  class="TableData" align="center" rowspan="3">
								<bean:define type="java.util.Map" id="ineligibleStatusMap" name="apForm" property="ineligibleStatus"/>
										<%
											Map ineligibleStatusTempMap = apForm.getIneligibleTempMap();
										String ineligibleStatusValue = (String)ineligibleStatusTempMap.get(appRefNo);


								if(request.getParameter("ineligibleStatus(key-"+a+")")==null && ( ineligibleStatusValue.equals("PE") || ineligibleStatusValue.equals("HO") ))
								{
									ineligibleStatusMap.put("key-"+a,ineligibleStatusValue);
								}
								
								if(request.getParameter("ineligibleStatus(key-"+a+")")==null &&ineligibleStatusValue.equals("PE"))
								{
									
								%>

										<%name="ineligibleStatus(key-"+a+")";%>
										<div align="center">
											<html:select property="<%=name%>" name="apForm" >
												<html:option value="">Select</html:option>											
												<html:option value="RE">Reject</html:option>
												<html:option value="PE">Pending</html:option>
												<html:option value="AP">Accept</html:option>
<!--												<html:option value="ATL">Additional TL</html:option>
												<html:option value="WCR">WC Renewal</html:option>
												<html:option value="WEN">WC Enhancement</html:option>	-->
											</html:select></div>
									<%}
									else
									{
										
										%>
										<%name="ineligibleStatus(key-"+a+")";%>
										<div align="center">
											<html:select property="<%=name%>" name="apForm" >
												<html:option value="">Select</html:option>
												<html:option value="HO">Hold</html:option>
												<html:option value="RE">Reject</html:option>
												<html:option value="PE">Pending</html:option>
												<html:option value="AP">Accept</html:option>
<!--												<html:option value="ATL">Additional TL</html:option>
												<html:option value="WCR">WC Renewal</html:option>
												<html:option value="WEN">WC Enhancement</html:option>	-->
											</html:select></div>
									<%}%>
												
									</td>
									<td  class="TableData" align="center" rowspan="3">
								<bean:define type="java.util.Map" id="ineligibleApprovedAmtMap" name="apForm" property="ineligibleApprovedAmt"/>
								<%if(request.getParameter("ineligibleApprovedAmt(key-"+a+")")!=null)
									{						
										
										if(request.getParameter("ineligibleApprovedAmt(key-"+a+")").equals(""))
										{
											ineligibleApprovedAmtMap.put("key-"+a,new Double(0));
										}
										else
										{
											double doubleAmt = Double.parseDouble(request.getParameter("ineligibleApprovedAmt(key-"+a+")"));

											ineligibleApprovedAmtMap.put("key-"+a,new Double(doubleAmt));

										}

									}
								else{

									

									ineligibleApprovedAmtMap.put("key-"+a,eligibleApprovedAmount);
								}

								%>

										<%name="ineligibleApprovedAmt(key-"+a+")";%>
										<div align="center">
										<html:text property="<%=name%>" alt="approvedAmt" name="apForm" size="15" onkeypress="return decimalOnly(this, event,13)" onkeyup="isValidDecimal(this)" maxlength="16"/></div>
									</td>

							<bean:define type="java.util.Map" id="ineligibleRemarksMap" name="apForm" property="ineligibleRemarks"/>
								<td  class="TableData" align="center" rowspan="3" >
								<%
									Map ineligibleRemarksTempMap = apForm.getIneligibleRemMap();
								String ineligibleRemValue = (String)ineligibleRemarksTempMap.get(appRefNo);
								
								if(request.getParameter("ineligibleStatus(key-"+a+")")==null && ( ineligibleStatusValue.equals("PE") || ineligibleStatusValue.equals("HO") ))
								{
									
									ineligibleRemarksMap.put("key-"+a,ineligibleRemValue);
								}
								
								%>

										<%name="ineligibleRemarks(key-"+a+")";%>
										<div align="center">
										<html:textarea property="<%=name%>" cols="15" alt="remarks" name="apForm" rows="2"/></div>
									</td>



									
									</tr>
									
									<%if(passedValues!=null && !(passedValues.equals("")))
									{
									%>	
										<tr>
										<td class="TableData" align="center"><%=passedValues%></td>
										<td class="TableData" align="center">True</td>
										</tr>
									<%}
									else{%>
									<tr>
									<td class="TableData" align="center">
									</td>
									</tr>
									<%}%>

									<%if(messages!=null && !(messages.equals("")))
									{%>
										<tr>

											<td class="TableData" align="center" colspan="2"><%=messages%></td>	

											</tr>
										<%}
											else{%>
											<tr>
											<td class="TableData" align="center">
											</td>
											</tr>
											<%}%>
										<%name="ineligibleCreditAmt(key-"+a+")";%>
										<html:hidden property="<%=name%>" name="appForm" value="<%=eligibleCreditAmount%>"/>


										<%++a;%>									

									</logic:iterate>									

								</table>
							</td>
						</tr>		
						<% }%>


						<!--Ineligible Duplicate Applications-->
						<%							
							int m=0;
							  
							ArrayList ineligibleDupApplications=(ArrayList)apForm.getIneligibleDupApps();
							if(ineligibleDupApplications.size()>0)
							{
								ArrayList ineligibleApps=(ArrayList)ineligibleDupApplications.get(0);
								ArrayList dupApps=(ArrayList)ineligibleDupApplications.get(1);
								if (ineligibleApps.size()!=0 && dupApps.size()!=0)
								{							
										
								%>
								<tr>
										<td class="SubHeading" colspan="4">
										<bean:message key="probableDuplicateInEligibleApps"/>
								</tr>

								<tr>
									<td>

									<TABLE width="100%" border="0" cellpadding="1" cellspacing="1">
									<tr> 
									  <td align="center" class="HeadingBg" width="29"><div align="center"><bean:message key = "srNo"/></div>
									  </td>
									  <td class="HeadingBg" align="center"><div align="center"><bean:message key="borrowerId"/></div>
									  </td>
									  <td class="HeadingBg" align="center"><div align="center"><bean:message 					key="oldCgpanNumber"/></div>
									  </td>
									  <td class="HeadingBg" align="center"><div align="center"><bean:message 					key="newAppRefNo"/></div>
									  </td>
									  <td class="HeadingBg" align="center" width="15%"><div align="center"><bean:message key ="details"/></div>
									  </td>
									  <td class="HeadingBg" align = "center"><div align="center"><bean:message key ="existing"/></div>
									  </td>
									  <td class="HeadingBg"  align="center"><div align="center"><bean:message key="newDetails"/></div>
									  </td>						
									  <td class="HeadingBg" align="center"><div align="center"><bean:message key="decision"/></div>
									  </td>
									  <td class="HeadingBg" align="center"><div align="center"><bean:message key="approvedAmount"/></div>
									  </td>								  
									  <td class="HeadingBg" align="center"><div align="center"><bean:message key="comments"/></div>
									  </td>	
								  </tr>			
								  <%
										ArrayList ineligibleDuplicateApps=(ArrayList)apForm.getIneligibleDupApps();
										ArrayList duplicateApplications = (ArrayList)ineligibleDuplicateApps.get(0);
										ArrayList ineligibleApplication = (ArrayList)ineligibleDuplicateApps.get(1);

										int duplicateApplicationsSize=duplicateApplications.size();
										int ineligibleApplicationSize=ineligibleApplication.size();

										int x=0;
										int y=0;

										for(x=0,y=0;x<duplicateApplicationsSize && y<ineligibleApplicationSize; x++,y++)
										{
											com.cgtsi.application.DuplicateApplication eligibleDupApplication= (com.cgtsi.application.DuplicateApplication)duplicateApplications.get(x);
											String borrowerId = eligibleDupApplication.getBorrowerId();
											String oldCgpan = eligibleDupApplication.getOldCgpan();				
											String newAppRefNo = eligibleDupApplication.getNewAppRefNo();		
											ArrayList duplicateConditionList=eligibleDupApplication.getDuplicateCondition();
											int c=duplicateConditionList.size();

											double amount3=eligibleDupApplication.getDupApprovedAmount();
										   String ineligibleDupApprovedAmount=(new Double(amount3)).toString();

											double creditAmount3=eligibleDupApplication.getDupCreditAmount();
										   String ineligibleDupCreditAmount=(new Double(creditAmount3)).toString();




											com.cgtsi.application.EligibleApplication eligibleApplication= (com.cgtsi.application.EligibleApplication)ineligibleApplication.get(y);

											String submissionDate=eligibleApplication.getSubmissiondate();
										String appRefNo = eligibleApplication.getAppRefNo();				
										String passedValues = eligibleApplication.getPassedCondition();	
										String failedValues = eligibleApplication.getFailedCondition();	
										String messages = eligibleApplication.getMessage();	

											int rowsize=c + 4;
											String stringVal=new String();
											String jVal=stringVal.valueOf(rowsize);
											%>

								<bean:define type="java.util.Map" id="ineligibleDupStatusMap" name="apForm" property="ineligibleDupStatus"/>
													<%
														Map statusTempMap = apForm.getTempMap();
													String statusValue = (String)statusTempMap.get(newAppRefNo);%>


											 <tr align="center">
								  
											<td class="tableData" align="center" rowspan="<%=jVal%>">&nbsp;<%=(m+1)%>
											</td>
											<td class="tableData" align="center" rowspan="<%=jVal%>" width="10%">&nbsp;
											<%if (borrowerId!=null)
												{%>
												<%=borrowerId%>
											<%}%>

											</td>
											<td class="tableData" align="center" rowspan="<%=jVal%>" width="10%">&nbsp;

										<%
										if(oldCgpan.substring(0,2).equals("CG") && statusValue.equals("EN"))	
											{%>
										<a href="afterCGPANPage.do?method=showOldAppDetails&cgpan=<%=oldCgpan%>"><%=oldCgpan%></a>
											<%}
											else if (oldCgpan.substring(0,2).equals("CG"))
											{%>
											<a href="afterCGPANPage.do?method=showCgpanList&cgpan=<%=oldCgpan%>&flag=0"><%=oldCgpan%></a>
											<%}
											else
											{%>
											<a href="afterCGPANPage.do?method=showCgpanList&appRef=<%=oldCgpan%>&flag=1"><%=oldCgpan%></a>
											<%}%>
											<html:hidden property="<%=name%>" name="apForm" value="<%=oldCgpan%>"/>
											</td>
											<td class="tableData" align="center" rowspan="<%=jVal%>" width="10%">
												<%name="ineligibleDupAppRefNo(key-"+m+")";%>&nbsp;<a href="afterCGPANPage.do?method=showCgpanList&appRef=<%=newAppRefNo%>&flag=1"><%=newAppRefNo%></a>
												<html:hidden property = "<%=name%>" name="apForm" value="<%=newAppRefNo%>"/>

											</td>											
											<%
												java.util.ArrayList duplicateConditions=(java.util.ArrayList)eligibleDupApplication.getDuplicateCondition();
												int duplicateConditionSize=duplicateConditions.size();

												com.cgtsi.application.DuplicateCondition duplicateCondition=null;

												for (int n=0; n<duplicateConditionSize;n++)
												{
													duplicateCondition=(com.cgtsi.application.DuplicateCondition)duplicateConditions.get(n);
													String conditionName=duplicateCondition.getConditionName();
													String oldValue=duplicateCondition.getExistingValue();
													String newValue=duplicateCondition.getNewValue();
													if(n==0)
													{										
													%>
													<td class="ColumnBackground" align="center"><%=conditionName%></td>
													<td  class="TableData" align="center"><div align="center"><%=oldValue%></div></td>
													<td  class="TableData" align="center"><div align="center"><%=newValue%></div></td>	
													<td  class="TableData" align="center" rowspan="<%=jVal%>">
<%
												if(request.getParameter("ineligibleDupStatus(key-"+m+")")==null && ( statusValue.equals("PE") || statusValue.equals("HO") ))
												{
													ineligibleDupStatusMap.put("key-"+m,statusValue);
												}
												if(request.getParameter("ineligibleDupStatus(key-"+m+")")==null && statusValue.equals("PE"))
												{
										%>

													
													<%name="ineligibleDupStatus(key-"+m+")";%>
														<html:select property="<%=name%>" name="apForm" >
															<html:option value="">Select</html:option>
															<html:option value="RE">Reject</html:option>
															<html:option value="PE">Pending</html:option>
															<html:option value="AP">Accept</html:option>
	<!--														<html:option value="ATL">Additional TL</html:option>
															<html:option value="WCR">WC Renewal</html:option>
															<html:option value="WEN">WC Enhancement</html:option>		-->
														</html:select>	
												<%}
												else
												{%>
													<%name="ineligibleDupStatus(key-"+m+")";%>
														<html:select property="<%=name%>" name="apForm" >
															<html:option value="">Select</html:option>
															<html:option value="HO">Hold</html:option>
															<html:option value="RE">Reject</html:option>
															<html:option value="PE">Pending</html:option>
															<html:option value="AP">Accept</html:option>
	<!--														<html:option value="ATL">Additional TL</html:option>
															<html:option value="WCR">WC Renewal</html:option>
															<html:option value="WEN">WC Enhancement</html:option>		-->
														</html:select>	
												<%}%>

													</td>
													<td  class="TableData" align="center"rowspan="<%=jVal%>" >
										<bean:define type="java.util.Map" id="ineligibleDupApprovedAmtMap" name="apForm" property="ineligibleDupApprovedAmt"/>
											<%if(request.getParameter("ineligibleDupApprovedAmt(key-"+m+")")!=null)
												{								
													if(request.getParameter("ineligibleDupApprovedAmt(key-"+m+")").equals(""))
													{
														ineligibleDupApprovedAmtMap.put("key-"+m,new Double(0));
													}
													else
													{
														double doubleAmt = Double.parseDouble(request.getParameter("ineligibleDupApprovedAmt(key-"+m+")"));

														ineligibleDupApprovedAmtMap.put("key-"+m,new Double(doubleAmt));

													}

												}
											else{

												ineligibleDupApprovedAmtMap.put("key-"+m,ineligibleDupApprovedAmount);
											}

											%>

														<%name="ineligibleDupApprovedAmt(key-"+m+")";%>
														<div align="center">
														<html:text property="<%=name%>" alt="approvedAmt" name="apForm" size="10" onkeypress="return decimalOnly(this, event,13)" onkeyup="isValidDecimal(this)" maxlength="16"/></div>
													</td>
													<td  class="TableData" align="center" rowspan="<%=jVal%>">
												<bean:define type="java.util.Map" id="ineligibleDupRemarksMap" name="apForm" property="ineligibleDupRemarks"/>

													<%
														Map ineligibleDupRemarksTempMap = apForm.getTempRemMap();
													String ineligibleDupRemValue = (String)ineligibleDupRemarksTempMap.get(newAppRefNo);
													
												if(request.getParameter("ineligibleDupStatus(key-"+m+")")==null && ( statusValue.equals("PE") || statusValue.equals("HO") ))
												{
													ineligibleDupRemarksMap.put("key-"+m,ineligibleDupRemValue);
													}
													%>

														<%name="ineligibleDupRemarks(key-"+m+")";%>
														<html:textarea property="<%=name%>" cols="10" alt="address" name="apForm" rows="2"/>
													</td>												
														
												<%}
												else {											
													%>
											<tr>						

												<td class="ColumnBackground" align="center"><div align="center"><%=conditionName%></div></td>
												<td  class="tableData" align="center"><div align="center"><%=oldValue%></div></td>
												<td  class="tableData" align="center"><div align="center"><%=newValue%></div></td>										
											<%}%>
											</tr>
											<%}%>

											<tr>
												<td class="ColumnBackground" align="center">
													<bean:message key="eligibleCriteria"/>
												</td>
												<td class="ColumnBackground" align="center">
													<bean:message key ="value"/>
												</td>
												<td class="ColumnBackground" align="center">
												</td>
											</tr>

											<tr>
												<td class="TableData" align="center"><%=failedValues%></td>
												<td class="TableData" align="center">False</td>				
												<td class="TableData" align="center"></td>
											</tr>


										<%if(passedValues!=null && !(passedValues.equals("")))
										{
										%>	
											<tr>
											<td class="TableData" align="center"><%=passedValues%></td>
											<td class="TableData" align="center">True</td>
										<td class="TableData" align="center">
										</td>

											</tr>
										<%}
										else{%>
										<tr>
										<td class="TableData" align="center">
										</td>
										</tr>
										<%}%>


										<%if(messages!=null && !(messages.equals("")))
										{%>
											<tr>

												<td class="TableData" align="center" colspan="2"><%=messages%></td>	
											<td class="TableData" align="center">
											</td>


												</tr>
											<%}
												else{%>
												<tr>
												<td class="TableData" align="center">
												</td>
												</tr>
												<%}%>

											<%name="ineligibleDupCreditAmt(key-"+m+")";%>
											<html:hidden property="<%=name%>" name="appForm" value="<%=ineligibleDupCreditAmount%>"/>

												<%	++m;}%>

												 </table>
												</td>
											</tr>

												<%}
										}%>

											<TR >
								<TD height="20" >
									&nbsp;
								</TD>
							</TR>
							<TR >
								<TD align="center" valign="baseline" >
									<DIV align="center">
										<!--<A href="javascript:document.appForm.reset()">
											<IMG src="images/Reset.gif" alt="Cancel" width="49" height="37" border="0"></A>-->
											<A href="javascript:submitForm('afterApprovalApps.do?method=afterApprovalApps')">
											<IMG src="images/Save.gif" alt="Save" width="49" height="37" border="0"></A>
											<A href="javascript:document.apForm.reset()">
											<IMG src="images/Reset.gif" alt="Reset" width="49" height="37" border="0"></A>
											<A href="subHome.do?method=getSubMenu&menuIcon=<%=session.getAttribute("menuIcon")%>&mainMenu=<%=session.getAttribute("mainMenu")%>">
											<IMG src="images/Cancel.gif" alt="Cancel" width="49" height="37" border="0"></A>
										<!--<A href="subHome.do?method=getSubMenu&menuIcon=<%=session.getAttribute("menuIcon")%>&mainMenu=<%=session.getAttribute("mainMenu")%>">
										<IMG src="images/Cancel.gif" alt="Cancel" width="49" height="37" border="0"></A>-->
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

											
										  
										  