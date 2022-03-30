<%@ page language="java"%>
<%@ taglib uri="/WEB-INF/TLD/struts-html.tld" prefix="html" %>
<%@ taglib uri="/WEB-INF/TLD/struts-bean.tld" prefix="bean" %>
<%@ taglib uri="/WEB-INF/TLD/struts-logic.tld" prefix="logic" %>
<%@ page import="java.util.ArrayList"%>
<%@ page import="java.text.SimpleDateFormat"%>
<%@ page import="java.util.Date"%>
<%@ page import="java.util.Map"%>
<%@ page import="com.cgtsi.actionform.APForm"%>
<%@ page import="com.cgtsi.application.EligibleApplication"%>

<% session.setAttribute("CurrentPage","reApprovalApps.do?method=showAppsForReApproval");
String name;
String submittedDate;
SimpleDateFormat dateFormat=new SimpleDateFormat("dd/MM/yyyy");
%>
<%@ include file="/jsp/SetMenuInfo.jsp" %>


<script>
function openNew (url)
{
//	var url = "afterCGPANPage.do?method=showCgpanList&cgpan="+cgpan+"&flag=0";
	window.open(url);
}
</script>

<TABLE width="725" border="0" cellpadding="0" cellspacing="0">
	<html:errors />
	<html:form action="reApprovalApps.do?method=showAppsForReApproval" method="POST" >
		<TR> 
			<TD width="20" align="right" valign="bottom"><IMG src="images/TableLeftTop.gif" width="20" height="31"></TD>
			<TD background="images/TableBackground1.gif"></TD>
			<TD width="20" align="left" valign="bottom"><IMG src="images/TableRightTop.gif" width="23" height="31"></TD>
		</TR>
		<TR>
			<TD width="20" background="images/TableVerticalLeftBG.gif">&nbsp;</TD>
			<TD>
				<DIV align="right">			
					<A HREF="javascript:submitForm('reapprovalHelp.do?method=reapprovalHelp')">
					HELP</A>
				</DIV>

				<TABLE width="100%" border="0" align="left" cellpadding="0" cellspacing="0">
					<TR>
						<TD>
							<table width="661" border="0" cellspacing="1" cellpadding="0">
							 <TR>
								<TD colspan="4">
									<TABLE width="100%" border="0" cellpadding="0" cellspacing="0">
										<TR>
											<TD width="31%" class="Heading"><bean:message key="reApprovalHeader" /></TD>
											<TD><IMG src="images/TriangleSubhead.gif" width="19" height="19"></TD>
											</TR>
										<TR>
											<TD colspan="4" class="Heading"><IMG src="images/Clear.gif" width="5" height="5"></TD>
										</TR>
									</TABLE>
								</TD>
							</TR>
							</table>
							<tr>
								<td>

								<TABLE width="100%" border="0" cellpadding="1" cellspacing="1">

								<bean:define type="java.util.Map" id="reapprovalStatusMap" name="apForm" property="reapprovalStatus"/>

								<bean:define type="java.util.Map" id="reApprovedAmtMap" name="apForm" property="reApprovedAmt"/>

								<bean:define type="java.util.Map" id="reApprovedRemarksMap" name="apForm" property="reApprovalRemarks"/>

							  <%
							  int k=0;
								
								APForm apForm = (APForm)session.getAttribute("apForm");
								

								if (apForm.getTcClearApplications().size()!=0)
								{
									
							%>
							<tr>
									<td class="SubHeading" colspan="9">
									<bean:message key="pendingAppsReApproval"/>
							</tr>

								<tr> 
								  <td align="center" class="HeadingBg" width="29"><div align="center"><bean:message key = "srNo"/></div>
								  </td>
								  <td class="HeadingBg" align="center"><div align="center"><bean:message key="date"/></div>
								  </td>								  
								  <td class="HeadingBg" align="center"><div align="center"><bean:message 					key="cgpanNumber"/></div>
								  </td>	
								  <td class="HeadingBg" align="center"><div align="center"><bean:message 					key="borrowerRefNo"/></div>
								  </td>		
								   <td class="HeadingBg" align="center"><div align="center"><bean:message key="eligibleCriteria"/></div>
								  </td>
								<td class="HeadingBg" align="center" width="15%"><div align="center"><bean:message key ="value"/></div>
								  </td>
								  <td class="HeadingBg" align="center"><div align="center"><bean:message key="decision"/></div>
								  </td>
								  <td class="HeadingBg" align="center"><div align="center"><bean:message key="approvedAmount"/></div>
								  </td>								  
								  <td class="HeadingBg" align="center"><div align="center"><bean:message key="reapprovedAmount"/></div>
								  </td>								  
								  <td class="HeadingBg" align="center"><div align="center"><bean:message key="reapprovalComments"/></div>
								  </td>								
								  <td class="HeadingBg" align="center"><div align="center">Old Application</div>
								  </td>								
							  </tr>		


							  
							   <logic:iterate id="object" name="apForm" property="tcClearApplications">
							   <%
							  com.cgtsi.application.EligibleApplication eligibleApplication= (com.cgtsi.application.EligibleApplication)object;
							   
							   String appRefNo= eligibleApplication.getAppRefNo();
							   int ssiRefNo=eligibleApplication.getBorrowerRefNo();
							   submittedDate=eligibleApplication.getSubmissiondate();
//							   double approvedAmount=0;
							   double approvedAmount = eligibleApplication.getEligibleApprovedAmount();
							    double reApprovedAmountTemp = eligibleApplication.getEligibleCreditAmount();

								 String reApprovedAmount=(new Double(reApprovedAmountTemp)).toString();

								 double eligibleCreditAmountTemp = eligibleApplication.getEligibleCreditGuaranteed();

								 String eligibleCreditAmount = (new Double(eligibleCreditAmountTemp)).toString();

/*								if(reApprovedAmount==0)
									{
									approvedAmount=tempApprovedAmount;
									}else{

										approvedAmount=reApprovedAmount;
									}
*/							   String cgpanNo=eligibleApplication.getCgpan();
								String passedValues = eligibleApplication.getPassedCondition();	

								String failedValues = eligibleApplication.getFailedCondition();	
								String messages = eligibleApplication.getMessage();	
								String remarks = eligibleApplication.getEligibleRemarks();
							   %>

							   <tr align="center">
							  
								<td class="tableData" align="center" rowspan="3">&nbsp;<%=(k+1)%>
								</td>
								<td class="tableData" align="center" width="10%" rowspan="3">&nbsp;<%=submittedDate%>
								</td>
								<td class="tableData" align="center" width="10%" rowspan="3">&nbsp;
								<%name="cgpanNo(key-"+k+")";%>
								<a href="afterCGPANPage.do?method=showCgpanList&cgpan=<%=cgpanNo%>&flag=0"><%=cgpanNo%></a>
								<html:hidden property="<%=name%>" name="apForm" value="<%=cgpanNo%>"/>
								</td>
								<td class="tableData" align="center" width="10%" rowspan="3"><a href="showborrowerDetails.do?method=showborrowerDetails&ssiRef=<%=ssiRefNo%>"><%=ssiRefNo%></a>							

								</td>	
								<td class="TableData" align="center"><%=failedValues%></td>
											<td class="TableData" align="center">False</td>

								<td  class="TableData" align="center" rowspan="3" >


								<%
									Map ineligibleReapproveMap = apForm.getIneligibleReapproveMap();
								String ineligibleValue = (String)ineligibleReapproveMap.get(eligibleApplication.getCgpan());

								if(ineligibleValue.equals("RH"))
									{
										ineligibleValue = "HO";
									}


								if(request.getParameter("reapprovalStatus(key-"+k+")")==null && ( 									ineligibleValue.equals("RH") || ineligibleValue.equals("HO")))
								{
									reapprovalStatusMap.put("key-"+k,ineligibleValue);
								}

								%>

									<%name="reapprovalStatus(key-"+k+")";%>
									<div align="center">
										<html:select property="<%=name%>" name="apForm">
											<html:option value="">Select</html:option>					
											<html:option value="HO">Hold</html:option>
											<html:option value="RE">Reject</html:option>
											<html:option value="AP">Accept</html:option>				
										</html:select></div>											

								</td>
								<td class="tableData" align="center" width="10%" rowspan="3">&nbsp;<%=approvedAmount%>
								</td>
								<td  class="TableData" align="center" rowspan="3">

								
								<%
									
								if(request.getParameter("reApprovedAmt(key-"+k+")")!=null )
									{								
										
										if(request.getParameter("reApprovedAmt(key-"+k+")").equals(""))
										{
											reApprovedAmtMap.put("key-"+k,new Double(0));
										}
										else{
											double doubleAmt = Double.parseDouble(request.getParameter("reApprovedAmt(key-"+k+")"));

											reApprovedAmtMap.put("key-"+k,new Double(doubleAmt));
										}



									}
								else{

									reApprovedAmtMap.put("key-"+k,reApprovedAmount);

								}

								%>


									<%name="reApprovedAmt(key-"+k+")";%>
									<div align="center">
									<html:text property="<%=name%>" alt="approvedAmt" name="apForm" size="20" onkeypress="return decimalOnly(this, event)" onkeyup="isValidDecimal(this)" maxlength="16" /></div>
								</td>

								<%
								if(request.getParameter("reapprovalStatus(key-"+k+")")==null && ( 									ineligibleValue.equals("RH") || ineligibleValue.equals("HO")))
								{
									reApprovedRemarksMap.put("key-"+k,remarks);
								}
									%>
								<td  class="TableData" align="center" rowspan="3">
									<%name="reApprovalRemarks(key-"+k+")";%>
									<div align="center">
									<html:textarea property="<%=name%>" cols="15" alt="remarks" name="apForm" rows="2" /></div>
								</td>
								<td class="tableData" align="center" width="10%" rowspan="3">&nbsp;
								<a href = "javascript:openNew('afterCGPANPage.do?method=showOldAppDetails&cgpan=<%=cgpanNo%>&detail=application')"><%=cgpanNo%></a>
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

										<%name="creditAmt(key-"+k+")";%>
										<html:hidden property="<%=name%>" name="apForm" value="<%=eligibleCreditAmount%>"/>

							<%++k;%>
							</logic:iterate>
						<%}
								else {
									k=0;
								}
									%>

						 <%									

								if (apForm.getWcClearApplications().size()!=0)
								{
									
							%>
							<tr>
									<td class="SubHeading" colspan="7">
									<bean:message key="pendingEligibleAppsReApproval"/>
							</tr>

								<tr> 
								  <td align="center" class="HeadingBg" width="29"><div align="center"><bean:message key = "srNo"/></div>
								  </td>
								  <td class="HeadingBg" align="center"><div align="center"><bean:message key="date"/></div>
								  </td>								  
								  <td class="HeadingBg" align="center"><div align="center"><bean:message 					key="cgpanNumber"/></div>
								  </td>	
								  <td class="HeadingBg" align="center"><div align="center"><bean:message 					key="borrowerRefNo"/></div>
								  </td>		
								  <td class="HeadingBg" align="center" colspan="2"><div align="center"><bean:message key="decision"/></div>
								  </td>
								  <td class="HeadingBg" align="center"><div align="center"><bean:message key="approvedAmount"/></div>
								  </td>								  
								  <td class="HeadingBg" align="center"><div align="center"><bean:message key="reapprovedAmount"/></div>
								  </td>								  
								  <td class="HeadingBg" align="center"><div align="center"><bean:message key="reapprovalComments"/></div>
								  </td>								
								  <td class="HeadingBg" align="center" colspan="2"><div align="center">Old Application</div>
								  </td>								
							  </tr>		


							   
							   <logic:iterate id="object" name="apForm" property="wcClearApplications">
							   <%
							   com.cgtsi.application.Application wcApplication = (com.cgtsi.application.Application)object;
							   
							   String appRefNo= wcApplication.getAppRefNo();
							   int ssiRefNo=wcApplication.getBorrowerDetails().getSsiDetails().getBorrowerRefNo();
							   submittedDate=dateFormat.format(wcApplication.getSubmittedDate());
//							   double approvedAmount=0;
							   double approvedAmount = wcApplication.getApprovedAmount();
							    double reApprovedAmountTemp = wcApplication.getReapprovedAmount();
								 String reApprovedAmount=(new Double(reApprovedAmountTemp)).toString();
/*								if(reApprovedAmount==0)
									{
									approvedAmount=tempApprovedAmount;
									}else{

										approvedAmount=reApprovedAmount;
									}

*/
								 double creditAmountTemp = wcApplication.getTermLoan().getCreditGuaranteed();
								 String creditAmount = (new Double(creditAmountTemp)).toString();

							   String cgpanNo=wcApplication.getCgpan();
							   String remarks = wcApplication.getReapprovalRemarks();
							   
							   %>

							   <tr align="center">
							  
								<td class="tableData" align="center">&nbsp;<%=(k+1)%>
								</td>
								<td class="tableData" align="center" width="10%">&nbsp;<%=submittedDate%>
								</td>
								<td class="tableData" align="center" width="10%">&nbsp;<%name="cgpanNo(key-"+k+")";%>
								<a href="afterCGPANPage.do?method=showCgpanList&cgpan=<%=cgpanNo%>&flag=0"><%=cgpanNo%></a>
								<html:hidden property="<%=name%>" name="apForm" value="<%=cgpanNo%>"/>
								</td>
								<td class="tableData" align="center" width="10%"><a href="showborrowerDetails.do?method=showborrowerDetails&ssiRef=<%=ssiRefNo%>"><%=ssiRefNo%></a>							

								</td>	

								<%
									Map eligibleReapproveMap = apForm.getEligibleReapproveMap();
								String eligibleValue = (String)eligibleReapproveMap.get(wcApplication.getCgpan());

								if(eligibleValue.equals("RH"))
									{
										eligibleValue = "HO";
									}


								if(request.getParameter("reapprovalStatus(key-"+k+")")==null && ( 									eligibleValue.equals("RH") || eligibleValue.equals("HO")))
								{
									reapprovalStatusMap.put("key-"+k,eligibleValue);
								}

								%>

								<td  class="TableData" align="center" colspan="2">
									<%name="reapprovalStatus(key-"+k+")";%>
									<div align="center">
										<html:select property="<%=name%>" name="apForm">
											<html:option value="">Select</html:option>
											<html:option value="HO">Hold</html:option>
											<html:option value="RE">Reject</html:option>
											<html:option value="AP">Accept</html:option>			
										</html:select></div>
											
								</td>
								<td class="tableData" align="center" width="10%">&nbsp;<%=approvedAmount%>
								</td>

								<%
									

								if(request.getParameter("reApprovedAmt(key-"+k+")")!=null)
									{								
										if(request.getParameter("reApprovedAmt(key-"+k+")").equals(""))
										{
											reApprovedAmtMap.put("key-"+k,new Double(0));
										}
										else{
											double doubleAmt = Double.parseDouble(request.getParameter("reApprovedAmt(key-"+k+")"));

											reApprovedAmtMap.put("key-"+k,new Double(doubleAmt));
										}


									}
								else{

									reApprovedAmtMap.put("key-"+k,reApprovedAmount);

								}

								%>

								<td  class="TableData" align="center" >
									<%name="reApprovedAmt(key-"+k+")";%>
									<div align="center">
									<html:text property="<%=name%>" size="20" alt="reApprovedAmt" name="apForm"  onkeypress="return decimalOnly(this, event)" onkeyup="isValidDecimal(this)" maxlength="16" /></div>
								</td>
								<td  class="TableData" align="center" >
								<%
								if(request.getParameter("reapprovalStatus(key-"+k+")")==null && ( 									eligibleValue.equals("RH") || eligibleValue.equals("HO")))
								{
									reApprovedRemarksMap.put("key-"+k,remarks);
								}
									%>

									<%name="reApprovalRemarks(key-"+k+")";%>
									<div align="center">
									<html:textarea property="<%=name%>" cols="15" alt="remarks" name="apForm" rows="2"/></div>
								</td>
								<td class="tableData" align="center" width="10%" colspan="2">&nbsp;
								<a href = "javascript:openNew('afterCGPANPage.do?method=showOldAppDetails&cgpan=<%=cgpanNo%>&detail=application')"><%=cgpanNo%></a>
								</td>
							</tr>
										<%name="creditAmt(key-"+k+")";%>
										<html:hidden property="<%=name%>" name="apForm" value="<%=creditAmount%>"/>

							<%++k;%>
							</logic:iterate>
						<%}%>

						</table>
							</td>
						</tr>
						<TR >
								<TD height="20" >
									&nbsp;
								</TD>
							</TR>
							<TR >
								<TD align="center" valign="baseline" >
									<DIV align="center">
											<A href="javascript:submitForm('afterReApprovalApps.do?method=afterReApprovalApps')">
											<IMG src="images/Save.gif" alt="Save" width="49" height="37" border="0"></A>
										<A href="javascript:document.apForm.reset()">
											<IMG src="images/Reset.gif" alt="Cancel" width="49" height="37" border="0"></A>

										<A href="home.do?method=getMainMenu&menuIcon=<%=session.getAttribute("menuIcon")%>">
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
			</html:form>
		</TABLE>
