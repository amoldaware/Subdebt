<%@ page language="java"%>
<%@ page import="java.util.ArrayList"%>
<%@ page import="com.cgtsi.guaranteemaintenance.OutstandingDetail"%>
<%@ page import="com.cgtsi.guaranteemaintenance.OutstandingAmount"%>
<%@ page import="com.cgtsi.guaranteemaintenance.PeriodicInfo"%>
<%@ page import="java.text.SimpleDateFormat"%>
<%@ page import="java.util.Date"%>
<%@ include file="/jsp/SetMenuInfo.jsp" %>


<%@ taglib uri="/WEB-INF/TLD/struts-html.tld" prefix="html" %>
<%@ taglib uri="/WEB-INF/TLD/struts-bean.tld" prefix="bean" %>
<%@ taglib uri="/WEB-INF/TLD/struts-logic.tld" prefix="logic" %>


<% session.setAttribute("CurrentPage","showOutstandingDetails.do?method=showOutstandingDetails");
double tcPrAmt=0;
String dtcPrAmt=null;

double tcIntAmt = 0;
String dtcIntAmt = null;

Date tcDate = null;
String dtcDate = null;

double wcFBPrAmt = 0;
String dwcFBPrAmt = null;

double wcFBIntAmt = 0;
String dwcFBIntAmt = null;

Date wcFBDate = null;
String dwcFBDate = null;

double wcNFBPrAmt = 0;
String  dwcNFBPrAmt = null;

double wcNFBIntAmt = 0;
String dwcNFBIntAmt = null;

Date wcNFBDate = null;
String dwcNFBDate = null;

String name;
String cgpanType = null;

String amtCgpan = null;
String amtCgpanType = null;
int amtCgpanLen = 0;
String tcId = null;
String wcId = null;

int outDetailIndex=0;

int outAmtsIndex = 0 ;
int len = 0;
String totalTcId = null;
String totalWcId = null;

String totalNFBWcId = null;

String tcOutAmount=null;
String wcOutAmount=null;
String wcNFBOutAmount=null;
SimpleDateFormat dateFormat=new SimpleDateFormat("dd/MM/yyyy");%>

<TABLE width="725" border="0" cellpadding="0" cellspacing="0">
	<html:errors />
	<body onload = "displayTcOutAmtTotal(),displayWcOutAmtTotal(),displayWcNFBOutAmtTotal()">
	<html:form action="saveOutstandingDetails.do?method=saveOutstandingDetails" >
		<TR> 
			<TD width="20" align="right" valign="bottom"><IMG src="images/TableLeftTop.gif" width="20" height="31"></TD>
			<TD background="images/TableBackground1.gif"><IMG src="images/GuaranteeMaintenanceHeading.gif"></TD>
			<TD width="20" align="left" valign="bottom"><IMG src="images/TableRightTop.gif" width="23" height="31"></TD>
		</TR>
		<TR>
			<TD width="20" background="images/TableVerticalLeftBG.gif">&nbsp;</TD>
			<TD>
			<DIV align="right">			
				<A HREF="javascript:submitForm('helpOutstandingDetails.do?method=helpOutstandingDetails')">
			    HELP</A>
			</DIV>
				<TABLE width="100%" border="0" align="left" cellpadding="0" cellspacing="0">
					<TR>
						<TD>
							<TABLE width="100%" border="0" cellspacing="1" cellpadding="1">
								<TR>
									<TD colspan="6"> 
										<TABLE width="100%" border="0" cellpadding="0" cellspacing="0">
										<tr>
										  <td width="35%" class="Heading"><bean:message key="outstandingDetailHeader"/><bean:write name = "gmPeriodicInfoForm" property = "borrowerId"/></td>
										  <td  align="left" valign="bottom"><img src="images/TriangleSubhead.gif" width="19" height="19"></td>
										  <td align="right"> <div align="right"> </div></td>
										</tr>
										<tr>
											<td colspan="6" class="Heading"><img src="images/Clear.gif" width="5" height="5">
											  <A href="javascript:submitForm('showDisbursementDetailsLink.do?method=showDisbursementDetailsLink')">Disbursement Details</A> |
											  <A href="javascript:submitForm('showRepaymentDetailsLink.do?method=showRepaymentDetailsLink')">Repayment Details</A>| 
											<!--  <A href="javascript:submitForm('showNPADetailsLink.do?method=showNPADetailsLink')">NPA Details</A>| -->
											  <A href="javascript:submitForm('showRecoveryDetailsLink.do?method=showRecoveryDetailsLink')">Recovery Details</A>
											</td>
										</tr>
								  </table>
							  </td>
							</tr>
							<tr>
								  <td colspan="6" valign="top" >
								  <font color="#FF0000" size="2">
								 <!-- <bean:message key="outstandingDetailHint"/> -->
								  </font>
								  </td>
							</tr>

							 <tr>
								  <td colspan="6" valign="top">
									<table width="100%" border="0" cellspacing="1" cellpadding="0">
										<tr>
										  <td align="center" valign="middle" class="HeadingBg" height="7" rowspan="2"
										  align="center"><center><bean:message key="srNo"/></td>
										  
										  <td class="HeadingBg" height="7" rowspan="2" align="center" align="center"><center><bean:message key="BorrowerID"/></td>
  										  
										  <td class="HeadingBg" height="7" rowspan="2" align="center" align="center"><center><bean:message key = "borrowerName"/></td>
										  
										  <td class="HeadingBg" height="7" rowspan="2" align="center" align="center"><center><bean:message key="cgpanNumber"/></td>
										  
										  <td class="HeadingBg" height="7" rowspan="2" align="center" align="center"><center><bean:message key="scheme"/></td>

										  <td class="HeadingBg" colspan="2"><bean:message key = "sanctioned"/>&nbsp;<bean:message key = "inRs"/>
										  </td>

										  
										  <td class="HeadingBg" colspan="3" align="center" align="center"><center><bean:message key = "outstandinginRs"/></td>
										  
										  <td rowspan="2" class="HeadingBg" align="center"><bean:message key="totalOutstandingAmount"/></td>

										  <td class="HeadingBg" colspan="3" align="center" align="center"><center>Non Fund Based Outstanding </td>

										  <td rowspan="2" class="HeadingBg" align="center">Total Non Fund Based Outstanding Amount</td>

										  
								</tr>
								<tr align="center">
								  
								  <td class="HeadingBg">Sanctioned Amount
								  </td>

								  <td class="HeadingBg">Non Fund Based Sanctioned Amount
								  </td>
									  
								  <td class="HeadingBg"><bean:message key="osNonFundBasedPpl"/>
								  <bean:message key="inRs"/>
								  </td>
								  <td class="HeadingBg">Interest (in Rs.)
								  </td>

								  <td  class="HeadingBg" align="center"><bean:message key = "asOnDate"/></td>


								  <td class="HeadingBg">Amount (in Rs.)
								  </td>
								  <td class="HeadingBg">Commission (in Rs.)
								  </td>

								  <td  class="HeadingBg" align="center">Non  Fund Based As On Date</td>

								
								</tr>

								<% int i=0; int j =0, k=0;%>
								
					<tr align="center">	
					<logic:iterate id="object" name="gmPeriodicInfoForm" property="osPeriodicInfoDetails">
					<%
						com.cgtsi.guaranteemaintenance.PeriodicInfo periodicInfo = (com.cgtsi.guaranteemaintenance.PeriodicInfo)object;
						
						String cgpan = "";
						String scheme = "";
						String borrowerId = periodicInfo.getBorrowerId();
						String borrowerName = periodicInfo.getBorrowerName();
						if (borrowerId != null) { 
					%>

						<td class="tableData" valign="top" align="center"><center>
							<%= ++j%>
						</td>
						  
						<td class="tableData" valign="top" align="center">&nbsp;<A
							href="javascript:submitForm('showBorrowerDetailsLink.do?method=showBorrowerDetailsLink&formFlag=periodic&bidLink=<%=borrowerId%>')"><%=borrowerId%></A>
						</td>

						<td class="tableData" valign="top"      									align="center"><%=borrowerName%>
						</td>

								<bean:define type="java.util.Map" id="wcOutMap" name="gmPeriodicInfoForm" property="wcFBPrincipalOutstandingAmount"/>

								<bean:define type="java.util.Map" id="wcNFBOutMap" name="gmPeriodicInfoForm" property="wcNFBPrincipalOutstandingAmount"/>

								<bean:define type="java.util.Map" id="tcOutMap" name="gmPeriodicInfoForm" property="tcPrincipalOutstandingAmount"/>

								<bean:define type="java.util.Map" id="wcIntMap" name="gmPeriodicInfoForm" property="wcFBInterestOutstandingAmount"/>

								<bean:define type="java.util.Map" id="wcNFBIntMap" name="gmPeriodicInfoForm" property="wcNFBInterestOutstandingAmount"/>

								<bean:define type="java.util.Map" id="wcDateMap" name="gmPeriodicInfoForm" property="wcFBOutstandingAsOnDate"/>


								<bean:define type="java.util.Map" id="wcNFBDateMap" name="gmPeriodicInfoForm" property="wcNFBOutstandingAsOnDate"/>

								<bean:define type="java.util.Map" id="tcDateMap" name="gmPeriodicInfoForm" property="tcOutstandingAsOnDate"/>


						  
						<%
						  java.util.ArrayList outDetails = (java.util.ArrayList)periodicInfo.getOutstandingDetails();  
						
						   int outDetailsSize = outDetails.size();
						   com.cgtsi.guaranteemaintenance.OutstandingDetail outDetail = null;
						
							for(outDetailIndex=0; outDetailIndex < outDetailsSize; ++outDetailIndex) {
							outDetail = (com.cgtsi.guaranteemaintenance.OutstandingDetail)outDetails.get(outDetailIndex);
							cgpan = outDetail.getCgpan() ;
							scheme = outDetail.getScheme() ;

							len = cgpan.length();
							cgpanType = cgpan.substring(len-2,len-1);

							totalTcId = "totalTcOutAmt"+outDetailIndex;
							totalWcId = "totalWcOutAmt"+outDetailIndex;
							totalNFBWcId = "totalNFBWcOutAmt"+outDetailIndex;

								if (outDetailIndex == 0)
								{
							%>	
								  <td class="tableData" valign="top" align="center">&nbsp;<A
									href="javascript:submitForm('showCgpanDetailsLink.do?method=showCgpanDetailsLink&cgpanDetail=<%=cgpan%>')"><%=cgpan%></a>
								  </td>
																  
								  <td class="tableData" valign="top" align="center"><%=scheme%>
								  </td>
								
								  <% if(cgpanType.equalsIgnoreCase("w") || 									cgpanType.equalsIgnoreCase("r")) 
									 {
								  %>
  										<%name = "cgpansForWc(key-"+outDetailIndex+")";%>
										<html:hidden property = "<%=name%>" name="gmPeriodicInfoForm" value="<%=cgpan%>"/>

										<%wcOutAmount="wcSanct(key-"+outDetailIndex+")";%>

										<td class="tableData" valign="top"				        align="center" id="<%=wcOutAmount%>"> <%=outDetail.getWcFBSanctionedAmount()%>
										</td>

											<%wcNFBOutAmount="wcNFBSanct(key-"+outDetailIndex+")";%>

										<td class="tableData" valign="top"				        align="center" id="<%=wcNFBOutAmount%>"> <%=outDetail.getWcNFBSanctionedAmount()%>
										</td>

										

								  <%} else if (cgpanType.equalsIgnoreCase("t")) 
									{ %>

										<%name = "cgpansForTc(key-"+outDetailIndex+")";%>
										<html:hidden property = "<%=name%>" name="gmPeriodicInfoForm" value="<%=cgpan%>"/>

										<%tcOutAmount="tcSanct(key-"+outDetailIndex+")";%>

										 <td class="tableData" valign="top"			        align="center" id="<%=tcOutAmount%>"><%=outDetail.getTcSanctionedAmount()%>
										 </td>

										 <td class="tableData" valign="top"			        align="center" >
										 </td>
								 <% } %>

							  <%  } else 
								  {  
							  %>
									<TD class="tableData"></TD>
									<TD class="tableData"></TD>
									<TD class="tableData"></TD>
									
									<TD class="tableData" valign="top" align="center">&nbsp;<A
									href="javascript:submitForm('showCgpanDetailsLink.do?method=showCgpanDetailsLink&cgpanDetail=<%=cgpan%>')"><%=cgpan%></a>
									</TD>
										  
									<TD class="tableData" valign="top" align="center"><%=scheme%>
									</TD>						
								  
									<% if(cgpanType.equalsIgnoreCase("w") || 							cgpanType.equalsIgnoreCase("r")) 
									{
									%>		
  										<%name = "cgpansForWc(key-"+outDetailIndex+")";%>
										<html:hidden property = "<%=name%>" name="gmPeriodicInfoForm" value="<%=cgpan%>"/>

										<%wcOutAmount="wcSanct(key-"+outDetailIndex+")";
										%>

										<td class="tableData" valign="top"				        align="center" id="<%=wcOutAmount%>"><%=outDetail.getWcFBSanctionedAmount()%>
										</td>

										<%wcNFBOutAmount="wcNFBSanct(key-"+outDetailIndex+")";%>

										<td class="tableData" valign="top"				        align="center" id="<%=wcNFBOutAmount%>"> <%=outDetail.getWcNFBSanctionedAmount()%>
										</td>

										

								  <%} else if (cgpanType.equalsIgnoreCase("t")) 
									{ %>
										<%name = "cgpansForTc(key-"+outDetailIndex+")";%>
										<html:hidden property = "<%=name%>" name="gmPeriodicInfoForm" value="<%=cgpan%>"/>

										<%tcOutAmount="tcSanct(key-"+outDetailIndex+")";%>

										<td class="tableData" valign="top"			        align="center" id="<%=tcOutAmount%>"><%=outDetail.getTcSanctionedAmount()%>
										</td>

										<td class="tableData" valign="top"			        align="center">
										</td>
								 <% } %>
							<%	}  %>
										  
							<%ArrayList outstandingAmounts = 												(ArrayList)outDetail.getOutstandingAmounts();
								int outAmtSize = outstandingAmounts.size();
					  
							   for(outAmtsIndex = 0; outAmtsIndex < outAmtSize; ++outAmtsIndex) 
							   {
								   
								OutstandingAmount  outAmount = (OutstandingAmount)outstandingAmounts.get(outAmtsIndex)	;

								amtCgpan = outAmount.getCgpan();
								amtCgpanLen = amtCgpan.length(); 
								amtCgpanType = amtCgpan.substring(amtCgpanLen-2,amtCgpanLen-1);
						
								if( amtCgpanType.equalsIgnoreCase("w") || 										amtCgpanType.equalsIgnoreCase("r")) 
								{ 
									wcFBPrAmt = outAmount.getWcFBPrincipalOutstandingAmount();
									//System.out.println("wcFBPrAmt"+wcFBPrAmt);
									dwcFBPrAmt = (new Double(wcFBPrAmt)).toString();
									
									wcNFBPrAmt = outAmount.getWcNFBPrincipalOutstandingAmount();
									//System.out.println("wcFBPrAmt"+wcFBPrAmt);
									dwcNFBPrAmt = (new Double(wcNFBPrAmt)).toString();

									wcFBIntAmt = outAmount.getWcFBInterestOutstandingAmount();
									dwcFBIntAmt = (new Double(wcFBIntAmt)).toString();

									wcNFBIntAmt = outAmount.getWcNFBInterestOutstandingAmount();
									dwcNFBIntAmt = (new Double(wcNFBIntAmt)).toString();
									
									if(outAmount.getWcFBOutstandingAsOnDate()!=null)
									{
										dwcFBDate=dateFormat.format(outAmount.getWcFBOutstandingAsOnDate());
									}

									if(outAmount.getWcNFBOutstandingAsOnDate()!=null)
									{
										dwcNFBDate=dateFormat.format(outAmount.getWcNFBOutstandingAsOnDate());
									}

									

									wcId = outAmount.getWcoId();

									System.out.println("index :" +outAmtsIndex);
								%>
														
								<%  if (outAmtsIndex == 0) 
									{ 
								%>
										<%
										if(wcId!=null)
										{
											name = "workingCapitalId("+amtCgpan+"-"+outAmtsIndex+"-"+wcId+")";
										%>
											<html:hidden property = "<%=name%>" name="gmPeriodicInfoForm" value="<%=wcId%>"/>
									  <%}
										
										if(request.getParameter("wcFBPrincipalOutstandingAmount("+cgpan+"-"+outAmtsIndex+")")!=null)
										{
											if(request.getParameter("wcFBPrincipalOutstandingAmount("+cgpan+"-"+outAmtsIndex+")").equals(""))
											{
												wcOutMap.put(cgpan+"-"+outAmtsIndex,new Double(0));
											}
											else{
												double wcOutAmt = Double.parseDouble(request.getParameter("wcFBPrincipalOutstandingAmount("+cgpan+"-"+outAmtsIndex+")"));

												wcOutMap.put(cgpan+"-"+outAmtsIndex,new Double(wcOutAmt));
											}
										}
										else{

											wcOutMap.put(cgpan+"-"+outAmtsIndex,dwcFBPrAmt);
										}
										%>
			 
									  <td class="tableData" valign="top" align="center"><% name = "wcFBPrincipalOutstandingAmount("+cgpan+"-"+outAmtsIndex+")";%> <html:text property = "<%=name%>" size="10" name="gmPeriodicInfoForm"  onblur= "javascript:displayWcOutAmtTotal()" onkeypress="return decimalOnly(this, event,13)" onkeyup="isValidDecimal(this)" maxlength="16"/>				
									  </td>

									  <%										  
										if(request.getParameter("wcFBInterestOutstandingAmount("+cgpan+"-"+outAmtsIndex+")")!=null)
										{										  if(request.getParameter("wcFBInterestOutstandingAmount("+cgpan+"-"+outAmtsIndex+")").equals(""))
											{
											  wcIntMap.put(cgpan+"-"+outAmtsIndex,new Double(0));
											}
											else
											{
												double wcIntAmt = Double.parseDouble(request.getParameter("wcFBInterestOutstandingAmount("+cgpan+"-"+outAmtsIndex+")"));

												wcIntMap.put(cgpan+"-"+outAmtsIndex,new Double(wcIntAmt));
											}
										}
										else{

											wcIntMap.put(cgpan+"-"+outAmtsIndex,dwcFBIntAmt);
										}
									  %>
			
									  <td class="tableData" valign="top" align="center">
										<%name = "wcFBInterestOutstandingAmount(" + cgpan + "-"+outAmtsIndex+")";%>
										<html:text property = "<%=name%>" size="10"   name="gmPeriodicInfoForm" onblur= "javascript:displayWcOutAmtTotal()" onkeypress="return decimalOnly(this, event)" onkeyup="isValidDecimal(this)" maxlength="13"/>   
									   </td>

									  <%										  
										if(request.getParameter("wcFBOutstandingAsOnDate("+cgpan+"-"+outAmtsIndex+")")!=null)
										{
										  if(request.getParameter("wcFBOutstandingAsOnDate("+cgpan+"-"+outAmtsIndex+")").equals(""))
											{
											  wcDateMap.put(cgpan+"-"+outAmtsIndex,null);
											}
											else{

												wcDateMap.put(cgpan+"-"+outAmtsIndex,request.getParameter("wcFBOutstandingAsOnDate("+cgpan+"-"+outAmtsIndex+")"));

											}
										}
										else{

											wcDateMap.put(cgpan+"-"+outAmtsIndex,dwcFBDate);
										}
									  %>

						  
									   <td class="tableData" valign="top" align="center">
										  <table cellpadding="0" cellspacing="0">
											  <tr>
												  <td align="left" valign="top" class="TableData">
													<% name = "wcFBOutstandingAsOnDate(" + cgpan + "-"+outAmtsIndex+")";%>
													<html:text property="<%=name%>" size="10" alt=" Date" name="gmPeriodicInfoForm"  maxlength="10"/>
												  </td>	  							   
												</tr>
											</table>	
										</td>
					 
									   <td class = "tableData" align="center" id = "<%=totalWcId%>">
									   </td>   


										<%if(request.getParameter("wcNFBPrincipalOutstandingAmount("+cgpan+"-"+outAmtsIndex+")")!=null)
										{
											if(request.getParameter("wcNFBPrincipalOutstandingAmount("+cgpan+"-"+outAmtsIndex+")").equals(""))
											{
												wcNFBOutMap.put(cgpan+"-"+outAmtsIndex,new Double(0));
											}
											else{

												double wcOutAmt = Double.parseDouble(request.getParameter("wcNFBPrincipalOutstandingAmount("+cgpan+"-"+outAmtsIndex+")"));


												wcNFBOutMap.put(cgpan+"-"+outAmtsIndex,new Double(wcOutAmt));
											}
										}
										else{

											wcNFBOutMap.put(cgpan+"-"+outAmtsIndex,dwcNFBPrAmt);
										}
										%>
			 
									  <td class="tableData" valign="top" align="center"><% name = "wcNFBPrincipalOutstandingAmount("+cgpan+"-"+outAmtsIndex+")";%> <html:text property = "<%=name%>" size="10" name="gmPeriodicInfoForm"  onblur= "javascript:displayWcNFBOutAmtTotal()" onkeypress="return decimalOnly(this, event,13)" onkeyup="isValidDecimal(this)" maxlength="16"/>				
									  </td>

									  <%	
										  
										if(request.getParameter("wcNFBInterestOutstandingAmount("+cgpan+"-"+outAmtsIndex+")")!=null)
										{
											if(request.getParameter("wcNFBInterestOutstandingAmount("+cgpan+"-"+outAmtsIndex+")").equals(""))
											{
												wcNFBIntMap.put(cgpan+"-"+outAmtsIndex,new Double(0));
											}
											else{
												double wcIntAmt = Double.parseDouble(request.getParameter("wcNFBInterestOutstandingAmount("+cgpan+"-"+outAmtsIndex+")"));

												wcNFBIntMap.put(cgpan+"-"+outAmtsIndex,new Double(wcIntAmt));
											}
										}
										else{

											wcNFBIntMap.put(cgpan+"-"+outAmtsIndex,dwcNFBIntAmt);
										}
									  %>
			
									  <td class="tableData" valign="top" align="center">
										<%name = "wcNFBInterestOutstandingAmount(" + cgpan + "-"+outAmtsIndex+")";%>
										<html:text property = "<%=name%>" size="10"   name="gmPeriodicInfoForm" onblur= "javascript:displayWcNFBOutAmtTotal()" onkeypress="return decimalOnly(this, event)" onkeyup="isValidDecimal(this)" maxlength="13"/>   
									   </td>

									  <%										  
										if(request.getParameter("wcNFBOutstandingAsOnDate("+cgpan+"-"+outAmtsIndex+")")!=null)
										{
										  if(request.getParameter("wcNFBOutstandingAsOnDate("+cgpan+"-"+outAmtsIndex+")").equals(""))
											{
											  wcNFBDateMap.put(cgpan+"-"+outAmtsIndex,null);
											}
											else{

												wcNFBDateMap.put(cgpan+"-"+outAmtsIndex,request.getParameter("wcNFBOutstandingAsOnDate("+cgpan+"-"+outAmtsIndex+")"));
											}
											
										}
										else{

											wcNFBDateMap.put(cgpan+"-"+outAmtsIndex,dwcNFBDate);
										}
									  %>

						  
									   <td class="tableData" valign="top" align="center">
										  <table cellpadding="0" cellspacing="0">
											  <tr>
												  <td align="left" valign="top" class="TableData">
													<% name = "wcNFBOutstandingAsOnDate(" + cgpan + "-"+outAmtsIndex+")";%>
													<html:text property="<%=name%>" size="10" alt=" Date" name="gmPeriodicInfoForm"  maxlength="10"/>
												  </td>	  							   
												</tr>
											</table>	
										</td>

									   <td class = "tableData" align="center" id = "<%=totalNFBWcId%>">								   </td>   

					   
									</TR> 

							  <% ++i;
							  }	else 
							  {
							 %>
							<TR>	
								<TD class="tableData"></TD>
								<TD class="tableData"></TD>
								<TD class="tableData"></TD>
								<TD class="tableData"></TD>
								<TD class="tableData"></TD>
								<TD class="tableData"></TD>

								<TD class="tableData"></TD>
						
								<%
								if(wcId!=null)
								{
									name = "workingCapitalId("+amtCgpan+"-"+outAmtsIndex+"-"+wcId+")";
								%>
									<html:hidden property = "<%=name%>" name="gmPeriodicInfoForm" value="<%=wcId%>"/>
								<%}
										if(request.getParameter("wcFBPrincipalOutstandingAmount("+cgpan+"-"+outAmtsIndex+")")!=null)
										{
											if(request.getParameter("wcFBPrincipalOutstandingAmount("+cgpan+"-"+outAmtsIndex+")").equals(""))
											{
												wcOutMap.put(cgpan+"-"+outAmtsIndex,new Double(0));

											}
											else{
											double wcOutAmt = Double.parseDouble(request.getParameter("wcFBPrincipalOutstandingAmount("+cgpan+"-"+outAmtsIndex+")"));

											wcOutMap.put(cgpan+"-"+outAmtsIndex,new Double(wcOutAmt));

											}
										}
										else{

											wcOutMap.put(cgpan+"-"+outAmtsIndex,dwcFBPrAmt);
										}
								
								%>

								<td class="tableData" valign="top" align="center"><% name = "wcFBPrincipalOutstandingAmount("+cgpan+"-"+outAmtsIndex+")";%> <html:text property = "<%=name%>" size="10" name="gmPeriodicInfoForm"  onblur= "javascript:displayWcOutAmtTotal()" onkeypress="return decimalOnly(this, event,13)" onkeyup="isValidDecimal(this)" maxlength="16"/>				
								</td>

								<%
										if(request.getParameter("wcFBInterestOutstandingAmount("+cgpan+"-"+outAmtsIndex+")")!=null)
										{
											if(request.getParameter("wcFBInterestOutstandingAmount("+cgpan+"-"+outAmtsIndex+")").equals(""))
											{
												wcIntMap.put(cgpan+"-"+outAmtsIndex,new Double(0));
											}
											else
											{
												double wcIntAmt = Double.parseDouble(request.getParameter("wcFBInterestOutstandingAmount("+cgpan+"-"+outAmtsIndex+")"));

												wcIntMap.put(cgpan+"-"+outAmtsIndex,new Double(wcIntAmt));

											}
										}
										else{

											wcIntMap.put(cgpan+"-"+outAmtsIndex,dwcFBIntAmt);
										}
									
								%>
						  
								<td class="tableData" valign="top" align="center">
								  <%name = "wcFBInterestOutstandingAmount("+cgpan+"-"+outAmtsIndex+")";%>
								  <html:text property = "<%=name%>" size="10" name="gmPeriodicInfoForm"  onblur= "javascript:displayWcOutAmtTotal()" onkeypress="return decimalOnly(this, event)" onkeyup="isValidDecimal(this)" maxlength="13"/>   
								</td>


									  <%										  
										if(request.getParameter("wcFBOutstandingAsOnDate("+cgpan+"-"+outAmtsIndex+")")!=null)
										{
										  if(request.getParameter("wcFBOutstandingAsOnDate("+cgpan+"-"+outAmtsIndex+")").equals(""))
											{
											  wcDateMap.put(cgpan+"-"+outAmtsIndex,null);
											}
											else
											{
											wcDateMap.put(cgpan+"-"+outAmtsIndex,request.getParameter("wcFBOutstandingAsOnDate("+cgpan+"-"+outAmtsIndex+")"));

											}
									
										}
										else{

											wcDateMap.put(cgpan+"-"+outAmtsIndex,dwcFBDate);
										}
									  %>

								<td class="tableData" valign="top" align="center">
								  <table cellpadding="0" cellspacing="0">
									  <tr>
										  <td align="left" valign="top" class="TableData">
											<% name = "wcFBOutstandingAsOnDate("+cgpan+"-"+outAmtsIndex+")";%>
											<html:text property="<%=name%>" size="10" alt=" Date" name="gmPeriodicInfoForm" value="<%=dwcFBDate%>" maxlength="10"/>
										  </td>	  							   
										</tr>
									</table>	
								 </td>

								 <TD class="tableData" valign="top" align="center" id = "<%=totalWcId%>">						
								 </TD>	

								<%
										if(request.getParameter("wcNFBPrincipalOutstandingAmount("+cgpan+"-"+outAmtsIndex+")")!=null)
										{
											if(request.getParameter("wcNFBPrincipalOutstandingAmount("+cgpan+"-"+outAmtsIndex+")").equals(""))
											{
												wcNFBOutMap.put(cgpan+"-"+outAmtsIndex,new Double(0));
											}
											else
											{
												double wcOutAmt = Double.parseDouble(request.getParameter("wcNFBPrincipalOutstandingAmount("+cgpan+"-"+outAmtsIndex+")"));

												wcNFBOutMap.put(cgpan+"-"+outAmtsIndex,new Double(wcOutAmt));

											}
										}
										else{

											wcNFBOutMap.put(cgpan+"-"+outAmtsIndex,dwcNFBPrAmt);
										}
								
								%>

								<td class="tableData" valign="top" align="center"><% name = "wcNFBPrincipalOutstandingAmount("+cgpan+"-"+outAmtsIndex+")";%> <html:text property = "<%=name%>" size="10" name="gmPeriodicInfoForm"  onblur= "javascript:displayWcNFBOutAmtTotal()" onkeypress="return decimalOnly(this, event,13)" onkeyup="isValidDecimal(this)" maxlength="16"/>				
								</td>

								<%
										if(request.getParameter("wcNFBInterestOutstandingAmount("+cgpan+"-"+outAmtsIndex+")")!=null)
										{
											if(request.getParameter("wcNFBInterestOutstandingAmount("+cgpan+"-"+outAmtsIndex+")").equals(""))
											{
												wcNFBIntMap.put(cgpan+"-"+outAmtsIndex,new Double(0));
											}
											else{
												double wcIntAmt = Double.parseDouble(request.getParameter("wcNFBInterestOutstandingAmount("+cgpan+"-"+outAmtsIndex+")"));

												wcNFBIntMap.put(cgpan+"-"+outAmtsIndex,new Double(wcIntAmt));

											}
										}
										else{

											wcNFBIntMap.put(cgpan+"-"+outAmtsIndex,dwcNFBIntAmt);
										}
									
								%>
						  
								<td class="tableData" valign="top" align="center">
								  <%name = "wcNFBInterestOutstandingAmount("+cgpan+"-"+outAmtsIndex+")";%>
								  <html:text property = "<%=name%>" size="10" name="gmPeriodicInfoForm"  onblur= "javascript:displayWcNFBOutAmtTotal()" onkeypress="return decimalOnly(this, event)" onkeyup="isValidDecimal(this)" maxlength="13"/>   
								</td>
						  

									  <%										  
										if(request.getParameter("wcNFBOutstandingAsOnDate("+cgpan+"-"+outAmtsIndex+")")!=null)
										{
										  if(request.getParameter("wcNFBOutstandingAsOnDate("+cgpan+"-"+outAmtsIndex+")").equals(""))
											{
											  wcNFBDateMap.put(cgpan+"-"+outAmtsIndex,null);
											}
											else{
												wcNFBDateMap.put(cgpan+"-"+outAmtsIndex,request.getParameter("wcNFBOutstandingAsOnDate("+cgpan+"-"+outAmtsIndex+")"));
											}
											
										}
										else{

											wcNFBDateMap.put(cgpan+"-"+outAmtsIndex,dwcNFBDate);
										}
									  %>

								<td class="tableData" valign="top" align="center">
								  <table cellpadding="0" cellspacing="0">
									  <tr>
										  <td align="left" valign="top" class="TableData">
											<% name = "wcNFBOutstandingAsOnDate("+cgpan+"-"+outAmtsIndex+")";%>
											<html:text property="<%=name%>" size="10" alt=" Date" name="gmPeriodicInfoForm" maxlength="10"/>
										  </td>	  							   
										</tr>
									</table>	
								 </td>

								   <td class = "tableData" align="center" id = "<%=totalNFBWcId%>">								   </td>   


							</TR> 
							<% } 
							 }else if(cgpanType.equalsIgnoreCase("t"))
							  {
								tcPrAmt = outAmount.getTcPrincipalOutstandingAmount();
								dtcPrAmt = (new Double(tcPrAmt)).toString();

								dtcDate=dateFormat.format(outAmount.getTcOutstandingAsOnDate());
								tcId = outAmount.getTcoId();
								//System.out.println("TC ID : "+tcId);
							 %> 

								<%	if (outAmtsIndex == 0)
								{ 
								%>

								<%
								if(tcId!=null)
								{
									name = "termCreditId("+amtCgpan+"-"+outAmtsIndex+"-"+tcId+")";
								%>
									<html:hidden property = "<%=name%>" name="gmPeriodicInfoForm" value="<%=tcId%>"/>
								<%}

										if(request.getParameter("tcPrincipalOutstandingAmount("+cgpan+"-"+outAmtsIndex+")")!=null)
										{
											if(request.getParameter("tcPrincipalOutstandingAmount("+cgpan+"-"+outAmtsIndex+")").equals(""))
											{
												tcOutMap.put(cgpan+"-"+outAmtsIndex,new Double(0));
											}
											else
											{
												double tcOutAmt = Double.parseDouble(request.getParameter("tcPrincipalOutstandingAmount("+cgpan+"-"+outAmtsIndex+")"));

												tcOutMap.put(cgpan+"-"+outAmtsIndex,new Double(tcOutAmt));

											}
										}
										else{

											tcOutMap.put(cgpan+"-"+outAmtsIndex,dtcPrAmt);
										}

								%>

								  <td class="tableData" valign="top" align="center">
								  <%name = "tcPrincipalOutstandingAmount("+cgpan+"-"+outAmtsIndex+")";%> 
								  <html:text property = "<%=name%>" size="10" name="gmPeriodicInfoForm"  onblur= "javascript:displayTcOutAmtTotal()" onkeypress="return decimalOnly(this, event,13)" onkeyup="isValidDecimal(this)" maxlength="16"/>
								  </td>

								  <TD class="tableData"></TD>
						  
									  <%										  
										if(request.getParameter("tcOutstandingAsOnDate("+cgpan+"-"+outAmtsIndex+")")!=null )
										{
										  if(request.getParameter("tcOutstandingAsOnDate("+cgpan+"-"+outAmtsIndex+")").equals(""))
											{
											  tcDateMap.put(cgpan+"-"+outAmtsIndex,null);
											}
											else
											{
											tcDateMap.put(cgpan+"-"+outAmtsIndex,request.getParameter("tcOutstandingAsOnDate("+cgpan+"-"+outAmtsIndex+")"));

											}
										}
										else{

											tcDateMap.put(cgpan+"-"+outAmtsIndex,dtcDate);
										}
									  %>

								  <td class="tableData" valign="top" align="center">
									<table cellpadding="0" cellspacing="0">
										<tr>
										 <TD align="left" valign="top" class="TableData">
											<%name = "tcOutstandingAsOnDate("+cgpan+"-"+outAmtsIndex+")";%>
											<html:text property="<%=name%>" size="10" alt=" Date" name="gmPeriodicInfoForm"  maxlength="10"/>
										 </td>
									 </tr>
								   </table>
							      </td>
					  
								  <td class="tableData" valign="top" align="center" id = "<%=totalTcId%>">
								  </td>
									<TD class="tableData"></TD>
									<TD class="tableData"></TD>
									<TD class="tableData"></TD>
									<TD class="tableData"></TD>

								<TR> 
							<%   }  else { %>
								<TR>
									<TD class="tableData"></TD>
									<TD class="tableData"></TD>
									<TD class="tableData"></TD>
									<TD class="tableData"></TD>
									<TD class="tableData"></TD>
									<TD class="tableData"></TD>
									<TD class="tableData"></TD>	

									<%
									if(tcId!=null)
									{
										name = "termCreditId("+amtCgpan+"-"+outAmtsIndex+"-"+tcId+")";
									%>
										<html:hidden property = "<%=name%>" name="gmPeriodicInfoForm" value="<%=tcId%>"/>
								  <%}

										if(request.getParameter("tcPrincipalOutstandingAmount("+cgpan+"-"+outAmtsIndex+")")!=null)
										{
											if(request.getParameter("tcPrincipalOutstandingAmount("+cgpan+"-"+outAmtsIndex+")").equals(""))
											{
												tcOutMap.put(cgpan+"-"+outAmtsIndex,new Double(0));
											}
											else
											{
												double tcOutAmt = Double.parseDouble(request.getParameter("tcPrincipalOutstandingAmount("+cgpan+"-"+outAmtsIndex+")"));

												tcOutMap.put(cgpan+"-"+outAmtsIndex,new Double(tcOutAmt));

											}
										}
										else{

											tcOutMap.put(cgpan+"-"+outAmtsIndex,dtcPrAmt);
										}

									%>

									<td class="tableData" valign="top" align="center">
									  <%name = "tcPrincipalOutstandingAmount("+cgpan+"-"+outAmtsIndex+")";%> 
									  <html:text property = "<%=name%>" size="10" 	name="gmPeriodicInfoForm"  onblur= "javascript:displayTcOutAmtTotal()" onkeypress="return decimalOnly(this, event,13)" onkeyup="isValidDecimal(this)" maxlength="16"/>
									</td>

									<TD class="tableData"></TD>

									  <%										  
										if(request.getParameter("tcOutstandingAsOnDate("+cgpan+"-"+outAmtsIndex+")")!=null)
										{
										  if(request.getParameter("tcOutstandingAsOnDate("+cgpan+"-"+outAmtsIndex+")").equals(""))
											{
											  tcDateMap.put(cgpan+"-"+outAmtsIndex,null);
											}
											else
											{
											tcDateMap.put(cgpan+"-"+outAmtsIndex,request.getParameter("tcOutstandingAsOnDate("+cgpan+"-"+outAmtsIndex+")"));

											}
										}
										else{

											tcDateMap.put(cgpan+"-"+outAmtsIndex,dtcDate);
										}
									  %>
							  
									<td class="tableData" valign="top" align="center">
										<table cellpadding="0" cellspacing="0">
											<tr>
												 <TD align="left" valign="top" class="TableData">
													<%name = "tcOutstandingAsOnDate("+cgpan+"-"+outAmtsIndex+")";%>
													<html:text property="<%=name%>" size="10" alt=" Date" name="gmPeriodicInfoForm"  maxlength="10"/>
												</td>
											</tr>
										  </table>
										</td>
					  
										<td class="tableData" valign="top" align="center" id = "<%=totalWcId%>">
										</td>

										<TD class="tableData"></TD>	
										<TD class="tableData"></TD>	
										<TD class="tableData"></TD>	
										<TD class="tableData"></TD>	

									</tr>  
									<% ++k; 
									}//end of else.

								}// end of condt TC
				
							 }// End of inner For loop
							 %>
							<% if(cgpanType.equalsIgnoreCase("w") || 											cgpanType.equalsIgnoreCase("r")) 
								{
								 if(outAmtSize == 0)
								 {								 									 
							%>	
								<td class="tableData" valign="top" align="center"><% name = "wcFBPrincipalOutstandingAmount("+cgpan+"-"+outAmtSize+")";%> <html:text property = "<%=name%>" size="10" name="gmPeriodicInfoForm"  onblur= "javascript:displayWcOutAmtTotal()"onkeypress="return decimalOnly(this, event,13)" onkeyup="isValidDecimal(this)" maxlength="16"/>		
								</td>
						  
								<td class="tableData" valign="top" align="center">
								  <%name = "wcFBInterestOutstandingAmount("+cgpan+"-"+outAmtSize+")";%>
								  <html:text property = "<%=name%>" size="10" name="gmPeriodicInfoForm"  onblur= "javascript:displayWcOutAmtTotal()" onkeypress="return decimalOnly(this, event)" onkeyup="isValidDecimal(this)" maxlength="13"/>   
								</td>
						  
								<td class="tableData" valign="top" align="center">
								  <table cellpadding="0" cellspacing="0">
									  <tr>
										  <td align="left" valign="top" class="TableData">
											<% name = "wcFBOutstandingAsOnDate("+cgpan+"-"+outAmtSize+")";%>
											<html:text property="<%=name%>" size="10" alt=" Date" name="gmPeriodicInfoForm"  maxlength="10"/>
										  </td>	  							   
										</tr>
									</table>	
								 </td>

								 <TD class="tableData" valign="top" align="center" id = "<%=totalWcId%>">						
								 </TD>	

								<td class="tableData" valign="top" align="center"><% name = "wcNFBPrincipalOutstandingAmount("+cgpan+"-"+outAmtSize+")";%> <html:text property = "<%=name%>" size="10" name="gmPeriodicInfoForm"  onblur= "javascript:displayWcNFBOutAmtTotal()"onkeypress="return decimalOnly(this, event,13)" onkeyup="isValidDecimal(this)" maxlength="16"/>		
								</td>
						  
								<td class="tableData" valign="top" align="center">
								  <%name = "wcNFBInterestOutstandingAmount("+cgpan+"-"+outAmtSize+")";%>
								  <html:text property = "<%=name%>" size="10" name="gmPeriodicInfoForm"  onblur= "javascript:displayWcNFBOutAmtTotal()" onkeypress="return decimalOnly(this, event)" onkeyup="isValidDecimal(this)" maxlength="13"/>   
								</td>
						  
								<td class="tableData" valign="top" align="center">
								  <table cellpadding="0" cellspacing="0">
									  <tr>
										  <td align="left" valign="top" class="TableData">
											<% name = "wcNFBOutstandingAsOnDate("+cgpan+"-"+outAmtSize+")";%>
											<html:text property="<%=name%>" size="10" alt=" Date" name="gmPeriodicInfoForm"  maxlength="10"/>
										  </td>	  							   
										</tr>
									</table>	
								 </td>

								 <TD class="tableData" valign="top" align="center" id = "<%=totalNFBWcId%>">						
								 </TD>	


								 </TR>
							<%  } else {%>
								<TR>
									<TD class="tableData"></TD>
									<TD class="tableData"></TD>
									<TD class="tableData"></TD>
									<TD class="tableData"></TD>
									<TD class="tableData"></TD>
									<TD class="tableData"></TD>

									<TD class="tableData"></TD>

								<td class="tableData" valign="top" align="center"><% name = "wcFBPrincipalOutstandingAmount("+cgpan+"-"+outAmtsIndex +")";%> <html:text property = "<%=name%>" size="10" name="gmPeriodicInfoForm" onblur= "javascript:displayWcOutAmtTotal()" onkeypress="return decimalOnly(this, event,13)" onkeyup="isValidDecimal(this)" maxlength="16"/>				
								</td>
						  
								<td class="tableData" valign="top" align="center">
								  <%name = "wcFBInterestOutstandingAmount("+cgpan+"-"+outAmtsIndex +")";%>
								  <html:text property = "<%=name%>" size="10" name="gmPeriodicInfoForm" onblur= "javascript:displayWcOutAmtTotal()" onkeypress="return decimalOnly(this, event)" onkeyup="isValidDecimal(this)" maxlength="13"/>   
								</td>
						  
								<td class="tableData" valign="top" align="center">
								  <table cellpadding="0" cellspacing="0">
									  <tr>
										  <td align="left" valign="top" class="TableData">
											<% name = "wcFBOutstandingAsOnDate("+cgpan+"-"+ outAmtsIndex +")";%>
											<html:text property="<%=name%>" size="10" alt=" Date" name="gmPeriodicInfoForm" maxlength="10"/>
										  </td>	  							   
										</tr>
									</table>	
								 </td>

								 <TD class="tableData" valign="top" align="center">						
								 </TD>	

								<td class="tableData" valign="top" align="center"><% name = "wcNFBPrincipalOutstandingAmount("+cgpan+"-"+outAmtsIndex +")";%> <html:text property = "<%=name%>" size="10" name="gmPeriodicInfoForm" onblur= "javascript:displayWcNFBOutAmtTotal()" onkeypress="return decimalOnly(this, event,13)" onkeyup="isValidDecimal(this)" maxlength="16"/>				
								</td>
						  
								<td class="tableData" valign="top" align="center">
								  <%name = "wcNFBInterestOutstandingAmount("+cgpan+"-"+outAmtsIndex +")";%>
								  <html:text property = "<%=name%>" size="10" name="gmPeriodicInfoForm" onblur= "javascript:displayWcNFBOutAmtTotal()" onkeypress="return decimalOnly(this, event)" onkeyup="isValidDecimal(this)" maxlength="13"/>   
								</td>
						  
								<td class="tableData" valign="top" align="center">
								  <table cellpadding="0" cellspacing="0">
									  <tr>
										  <td align="left" valign="top" class="TableData">
											<% name = "wcNFBOutstandingAsOnDate("+cgpan+"-"+ outAmtsIndex +")";%>
											<html:text property="<%=name%>" size="10" alt=" Date" name="gmPeriodicInfoForm" maxlength="10"/>
										  </td>	  							   
										</tr>
									</table>	
								 </td>
								<td class="tableData" valign="top" align="center">
								</td>

							</TR> 

							<%}						
							}else if (cgpanType.equalsIgnoreCase("t")) 
							{ 
							  if (outAmtSize == 0)
							  {

								%>


									  <td class="tableData" valign="top" align="center">
									  <%name = "tcPrincipalOutstandingAmount("+cgpan+"-"+ outAmtSize +")";%> 
									  <html:text property = "<%=name%>" size="10" 	name="gmPeriodicInfoForm"  onblur= "javascript:displayTcOutAmtTotal()" onkeypress="return decimalOnly(this, event,13)" onkeyup="isValidDecimal(this)" maxlength="16"/>
									</td>

									<TD class="tableData"></TD>
							  
									<td class="tableData" valign="top" align="center">
										<table cellpadding="0" cellspacing="0">
											<tr>
												 <TD align="left" valign="top" class="TableData">
													<%name = "tcOutstandingAsOnDate("+cgpan+"-"+ outAmtSize +")";%>
													<html:text property="<%=name%>" size="10" alt=" Date" name="gmPeriodicInfoForm" maxlength="10"/>
												</td>
											</tr>
										  </table>
										</td>
					  
										<td class="tableData" valign="top" align="center" id = "<%=totalTcId%>">
										</td>

									<TD class="tableData"></TD>
									<TD class="tableData"></TD>
									<TD class="tableData"></TD>
									<TD class="tableData"></TD>

									</tr>  
								<% } else { %>
								  <TR>
									<TD class="tableData"></TD>
									<TD class="tableData"></TD>
									<TD class="tableData"></TD>
									<TD class="tableData"></TD>
									<TD class="tableData"></TD>
									<TD class="tableData"></TD>
									<TD class="tableData"></TD>

									<%
									/*if(request.getParameter("tcPrincipalOutstandingAmount("+cgpan+"-"+outAmtsIndex+")")!=null)
											{

											}
									*/	%>
									
									<td class="tableData" valign="top" align="center">
									  <%name = "tcPrincipalOutstandingAmount("+cgpan+"-"+outAmtsIndex +")";%> 
									  <html:text property = "<%=name%>" size="10" 	name="gmPeriodicInfoForm" onblur= "javascript:displayTcOutAmtTotal()" onkeypress="return decimalOnly(this, event,13)" onkeyup="isValidDecimal(this)" maxlength="16"/>
									</td>

									<TD class="tableData"></TD>
							  
									<td class="tableData" valign="top" align="center">
										<table cellpadding="0" cellspacing="0">
											<tr>
												 <TD align="left" valign="top" class="TableData">
													<%name = "tcOutstandingAsOnDate("+cgpan+"-"+outAmtsIndex +")";%>
													<html:text property="<%=name%>" size="10" alt=" Date" name="gmPeriodicInfoForm"  maxlength="10"/>
												</td>
											</tr>
										  </table>
										</td>
					  
										<td class="tableData" valign="top" align="center">
										</td>

									<TD class="tableData"></TD>
									<TD class="tableData"></TD>
									<TD class="tableData"></TD>
									<TD class="tableData"></TD>


									</tr>  
							 <% }
							} %>

						<%	}// end of outer for
							%>	
							<%}%> <!-- end of condt bid != null  -->															
							</TR>
							</logic:iterate>
<html:hidden property = "i" name="gmPeriodicInfoForm" value="<%=String.valueOf(i)%>"/>
<html:hidden property = "k" name="gmPeriodicInfoForm" value="<%=String.valueOf(k)%>"/>
<html:hidden property = "outDetailIndex" name="gmPeriodicInfoForm" value="<%=String.valueOf(outDetailIndex)%>"/>
           
								</table>    
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
													
														<A href = "javascript:checkForTcClosure()">
														<IMG src="images/Save.gif" alt="Save" width="49" height="37" border="0"></A>
														<A href="javascript:document.gmPeriodicInfoForm.reset()">
														<IMG src="images/Reset.gif" alt="Cancel" width="49" height="37" border="0"></A>

<%--			<A href="javascript:submitForm('saveOutstandingDetails.do?method=saveOutstandingDetails')">
														<IMG src="images/Save.gif" alt="Save" width="49" height="37" border="0"></A> --%>

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
			</html:form>
			</body>
		</TABLE>
