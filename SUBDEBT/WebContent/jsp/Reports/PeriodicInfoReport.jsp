<%@ page language="java"%>
<%@ taglib uri="/WEB-INF/TLD/struts-html.tld" prefix="html" %>
<%@ taglib uri="/WEB-INF/TLD/struts-bean.tld" prefix="bean" %>
<%@ taglib uri="/WEB-INF/TLD/struts-logic.tld" prefix="logic" %>

<%@ page import="java.util.ArrayList"%>
<%@ page import="com.cgtsi.guaranteemaintenance.PeriodicInfo"%>
<%@ page import="com.cgtsi.guaranteemaintenance.RepaymentAmount"%>
<%@ page import="com.cgtsi.guaranteemaintenance.Repayment"%>
<%@ page import="com.cgtsi.guaranteemaintenance.Disbursement"%>
<%@ page import="com.cgtsi.guaranteemaintenance.DisbursementAmount"%>
<%@ page import="com.cgtsi.guaranteemaintenance.OutstandingDetail"%>
<%@ page import="com.cgtsi.guaranteemaintenance.OutstandingAmount"%>
<%@ page import="com.cgtsi.guaranteemaintenance.NPADetails"%>
<%@ page import="com.cgtsi.guaranteemaintenance.RecoveryProcedure"%>
<%@ page import="com.cgtsi.guaranteemaintenance.LegalSuitDetail"%>
<%@ page import="com.cgtsi.guaranteemaintenance.Recovery"%>
<%@ page import="com.cgtsi.actionform.ReportActionForm"%>
<%@page import ="java.text.DecimalFormat"%>

<%@ page import="java.text.SimpleDateFormat"%>
<%@ page import="java.util.Date"%>

<%@ include file="/jsp/SetMenuInfo.jsp" %>

<%
String bIdForReport = request.getParameter("bidForPIReport");
session.setAttribute("CurrentPage","showPeriodicInfoReport.do?method=showPeriodicInfoReport&bidForPIReport="+bIdForReport);

String totalId = null;
int repaymentIndex =0;
int repayAmtsIndex = 0;
String name = null;
String repayDate=null;
SimpleDateFormat dateFormat=new SimpleDateFormat("dd/MM/yyyy");
String rpAmt=null;
int count = 0;
double amount = 0;
String repayId = null; 


String disbDate = null;
int disbAmtsIndex = 0;
String disbId = null;
String dAmount = null;
int dcount = 0;
String sancAmtId = null;
String disbIdFromDatabase = null;
DecimalFormat decimalFormat = new DecimalFormat("##########.##");

double totalRepayAmount = 0;
double totalDisbAmount = 0;
double totalOutAmount = 0;

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

String cgpanType = null;

String amtCgpan = null;
String amtCgpanType = null;
int amtCgpanLen = 0;

int outDetailIndex=0;

int outAmtsIndex = 0 ;
int len = 0;


%>

<TABLE width="725" border="0" cellpadding="0" cellspacing="0">
	<html:errors />
		<body>
			<html:form action="saveRepaymentDetails.do?method=saveRepaymentDetails" method="POST" enctype="multipart/form-data">
			<html:hidden property="rowCount" name ="rsPeriodicInfoForm" value="0" />
			<TR> 
				<TD width="20" align="right" valign="bottom">
					<IMG src="images/TableLeftTop.gif" width="20" height="31">
				</TD>

				<TD background="images/TableBackground1.gif">
					<IMG src="images/GuaranteeMaintenanceHeading.gif">
				</TD>

				<TD width="20" align="left" valign="bottom">
					<IMG src="images/TableRightTop.gif" width="23" height="31">
				</TD>
			</TR>
			<TR>
				<TD width="20" background="images/TableVerticalLeftBG.gif">
				</TD>
				<TD>
					<TABLE width="100%" border="0" align="left" cellpadding="0" cellspacing="0">
						<TR>
							<TD>
								<TABLE width="100%" border="0" cellspacing="1" cellpadding="1">		
<%
										String report = (String)session.getAttribute("reportFlag");
										

										if(report!=null && report.equals("Yes"))
										{
									%>

									<TR>
										<TD colspan="6"> 
								<TABLE width="100%" border="0" cellpadding="0" cellspacing="0">
									<TR>
										<TD width="31%" class="Heading">
												<bean:message key="repaymentDetailsHeader"/>
												<bean:write name="rsPeriodicInfoForm" property="borrowerId" /> 

										</TD>
										<TD><IMG src="images/TriangleSubhead.gif" width="19" height="19"></TD>
									</TR>
									<TR>
										<TD colspan="6" class="Heading"><IMG src="images/Clear.gif" width="5" height="5"></TD>
									</TR>
								</TABLE>
							  </td>
							</tr>
									<TR> 
										<TD colspan="6">
											<TABLE width="100%" border="0" cellspacing="1" 				cellpadding="0">
												<TR> 
													<TD align="center" valign="middle" 					class="HeadingBg" height="59" 					align="center">
														<bean:message key="srNo"/>
													</TD>
													<TD class="HeadingBg"  align="center">
														<bean:message key="BorrowerID"/>
													</TD>
						  
													<TD  class="HeadingBg" align="center">
														<bean:message key = "borrowerName"/>
													</TD>
						 
													<TD  class="HeadingBg" align="center">
														<bean:message key="cgpanNumber"/>
													</TD>
						  
													<TD class="HeadingBg"  align="center">
														<bean:message key="scheme"/>
													</TD>
						  
													<TD class="HeadingBg"   align="center">
														<bean:message key =	"repaymentAmountinRs"/>
													</TD>
						  
													<TD class="HeadingBg"  align="center">
														<bean:message key="date"/>
													</TD>
						  
													<TD  class="HeadingBg"  align="center">
														<bean:message key="totalRepaymentAmount"/>
													</TD>
												</TR>
                
				<% int i=0; int repaymentEntryIndex = 0; %>
						<TR align="center"> 
							<logic:iterate id="object1" name="rsPeriodicInfoForm" property = "repayPeriodicInfoDetails">
							<%
								PeriodicInfo periodicInfo = (PeriodicInfo)object1; 
								String cgpan = "";
								String scheme = "";
								String borrowerId = periodicInfo.getBorrowerId();
								String borrowerName = periodicInfo.getBorrowerName();%>
								<%if (borrowerId != null) 
								{
							%>
									<TD class="tableData" align="center">
										<%=++i%>
									</TD>
				  
									<TD class="tableData">&nbsp;
										<%=borrowerId%>
									</TD>
									<TD class="tableData">&nbsp;
										<%=borrowerName%>
									</TD>
					
									<%
										ArrayList repayments = (ArrayList)periodicInfo.getRepaymentDetails(); 
										
										int repaymentsSize = repayments.size();
	
										Repayment repayment = null;
										
										for(repaymentIndex =0; repaymentIndex < repaymentsSize; ++repaymentIndex) 
										{
											repayment = (Repayment)repayments.get(repaymentIndex);
											cgpan = repayment.getCgpan() ;
											scheme = repayment.getScheme() ; 
									%>

									<%
										if(cgpan!=null) 
										{ 
									%>
									<%
										name = "cgpans(key-"+repaymentIndex+")";
									%>
										<html:hidden property = "<%=name%>" name="rsPeriodicInfoForm" value="<%=cgpan%>"/>

									<%	if(repaymentIndex == 0) 
										{ 
									%>
										<TD class="tableData" valign="top" align="center">&nbsp;
											<%=cgpan%>
										</TD>
									  
										<TD class="tableData" valign="top" align="center">
											<%=scheme%>
										</TD>						
							  
									<%  
										} 
										else 
										{ 
									%>
											<TD class="tableData"></TD>
											<TD class="tableData"></TD>
											<TD class="tableData"></TD>
								
											<TD class="tableData" valign="top" align="center">&nbsp;
												<%=cgpan%>
											</TD>
									  
											<TD class="tableData" valign="top" align="center">
												<%=scheme%>
											</TD>						
							  
									<% 
										}   
							  
										ArrayList repayAmounts = (ArrayList)repayment.getRepaymentAmounts();	
										int repayAmtSize = repayAmounts.size();
							  														 
										for(repayAmtsIndex = 0; repayAmtsIndex < repayAmtSize; ++repayAmtsIndex) 
										{								
											RepaymentAmount  repaymentAmount = (RepaymentAmount)repayAmounts.get(repayAmtsIndex)	;
																
											amount = repaymentAmount.getRepaymentAmount();
											rpAmt = (new Double(amount)).toString();

											repayId = repaymentAmount.getRepayId();
											if(repaymentAmount.getRepaymentDate()!=null)			
											{
												repayDate = dateFormat.format(repaymentAmount.getRepaymentDate());
											}
											totalId = "totalAmt"+repaymentIndex ;
											totalRepayAmount = totalRepayAmount+amount;

											if (repayAmtsIndex == 0) 
											{
									%>

												<TD class="tableData" valign="top" align="center">
													<%=amount%>									
												</TD>						
							  
												<TD class="tableData" valign="top"	align="center">
													<%=repayDate%>								
												</TD>
											
												<%												
													if (repayAmtSize == 1) 
													{
												%>								
													<TD class="tableData" valign="top" align="center"> 
													 <%=totalRepayAmount%>
													</TD>
												<%
													}
													else
													{
												%>
													<TD class="tableData" valign="top" align="center"> 
													</TD>
												<%
													}
												%>

										<%
											} 
											else 
											{ 
										%>
												<TR>	
													<TD class="tableData"></TD>
													<TD class="tableData"></TD>
													<TD class="tableData"></TD>
													<TD class="tableData"></TD>
													<TD class="tableData"></TD>
									
													<TD class="tableData" valign="top" align="center">
														<%=amount%>									
													</TD>						
							  
													<TD class="tableData" valign="top" align="center">
														<%=repayDate%>								
													</TD>		
													
													<TD class="tableData">							
													</TD>

										<%			count = repayAmtsIndex;
												} // end of else loop. 
								  
											}	
										%> <!-- end of inner for loop -->					
										<%
											count++;
											totalRepayAmount = 0;
										%>				
									</TR>						
									<% 
										} // end of condt cgpan!=null 
										++repaymentEntryIndex;
										count =0;
									}
									%>		<!-- end of outer for  -->							
							<%
								}
							%> <!-- end of condt bid != null  -->							
						</TR>			 
				</logic:iterate>
					<html:hidden property = "repaymentEntryIndex" name="rsPeriodicInfoForm" value="<%=String.valueOf(repaymentEntryIndex)%>"/>				
						<TR>
							<TD>
							</TD>
						</TR>
					</table>
				</TD>
			</TR>
			<%}%>
<!-- Disbursement Details report -->
<%
										String disFlag = (String)session.getAttribute("disFlag");

										if(disFlag!=null && disFlag.equals("Yes"))
										{
									%>

			<TR>
				<TD colspan="6">
					<TABLE width="100%" border="0" cellpadding="0" cellspacing="0">
						<TR>
							<TD width="45%" class="Heading">
								<bean:message key="disbursementDetailsHeader"/>
								<bean:write name = "rsPeriodicInfoForm" property = "borrowerId"/>
							</TD>
							<TD>
								<img src="images/TriangleSubhead.gif" width="19" height="19">
							</TD>
										  
						</TR>
						<TR>
							<TD colspan="6" class="Heading">
								<img src="images/Clear.gif" width="5" height="5">
							</TD>
						</TR>
					</table>
				</TD>
			</TR>
			<TR>
				<TD colspan="6">
					<table width="100%" border="0" cellspacing="1" cellpadding="0">
						<TR>
							<TD align="center" valign="middle" class="HeadingBg" align="center">
								<bean:message key = "srNo"/>
							</TD>
							<TD class="HeadingBg" align="center">
								<bean:message key="borrowerId"/>
							</TD>
							<TD class="HeadingBg" align="center">
								<bean:message key="borrowerName"/>
	 					    </TD>
							<TD class="HeadingBg" align="center">
								<bean:message key="cgpanNumber"/>
							</TD>
							<TD class="HeadingBg" align="center">
								<bean:message key = "scheme"/>
						    </TD>
							<TD class="HeadingBg" align = "center">
								<bean:message key = "sanctionedAmount"/>
							</TD>
							<TD class="HeadingBg" align="center">
								<bean:message key="disbursementAmountinRs"/>
							</TD>
							<TD class="HeadingBg" align="center">
								<bean:message key="date"/>
							</TD>
							<TD class="HeadingBg" align="center">
								<bean:message key="finalDisbursement"/>
							</TD>
							<TD class="HeadingBg" align="center">
								<bean:message key="totalDisbursementAmount"/>
							</TD>
						</TR>

						<% int k=0;%>
						<TR align="center">
							<logic:iterate id="object2" name="rsPeriodicInfoForm" property = "disbPeriodicInfoDetails">
								<%
									com.cgtsi.guaranteemaintenance.PeriodicInfo periodicInfo = (com.cgtsi.guaranteemaintenance.PeriodicInfo)object2;
									String cgpan = "";
									String scheme = "";
									String borrowerId = periodicInfo.getBorrowerId();
									String borrowerName = periodicInfo.getBorrowerName();
								%>
								<%
									if (borrowerId != null) 
									{ 
								%>
										<TD class="tableData" align="center">
											<%=++k%>
										</TD>
										<TD class="tableData">&nbsp;
											<%=borrowerId%>
										</TD>
										<TD class="tableData">&nbsp;
											<%=borrowerName%>
										</TD>

										<%
											java.util.ArrayList disbursements = (java.util.ArrayList)periodicInfo.getDisbursementDetails();

											int disbursementsSize = disbursements.size();
											com.cgtsi.guaranteemaintenance.Disbursement disbursement = null;

											for(int disbursementIndex =0; disbursementIndex < disbursementsSize; ++disbursementIndex) {

											disbursement = (com.cgtsi.guaranteemaintenance.Disbursement)disbursements.get(disbursementIndex);
											cgpan = disbursement.getCgpan() ;
											scheme = disbursement.getScheme() ;
											double sanctionedAmount = disbursement.getSanctionedAmount() ;
											disbId = "totalDisbAmt"+disbursementIndex ;
										%>

										<%	
											if(cgpan!=null) 
											{ 
										%>										

										<% 
											sancAmtId = "sanDisb("+disbursementIndex+")" ; 
										%>

										<% 
											if(disbursementIndex == 0) 
											{ 
										%>

											  <TD class="tableData" valign="top" align="center">&nbsp;
												  <%=cgpan%>
											  </TD>

											  <TD class="tableData" valign="top" align="center">
												  <%=scheme%>
											  </TD>
									
											  <TD class="tableData" valign="top" align="center" id="">
												  <%=decimalFormat.format(sanctionedAmount)%>
											  </TD>
										<%  } 
											else 
											{ 
										%>
												<TD class="tableData"></TD>
												<TD class="tableData"></TD>
												<TD class="tableData"></TD>
			
												<TD class="tableData" valign="top" align="center">
													<%=cgpan%>
												</TD>

												<TD class="tableData" valign="top" align="center">
													<%=scheme%>
												</TD>

												<TD class="tableData" valign="top" align="center" 		id="">
													<%=decimalFormat.format(sanctionedAmount)%>
												</TD>
										<% 
											} 
										%>

											<% 
												java.util.ArrayList disbAmts = (java.util.ArrayList)disbursement.getDisbursementAmounts();
												int disbAmtSize = disbAmts.size();
												double disamount = 0;
												String finaldisbursement = null;

												boolean finalFlag=true;

												for(disbAmtsIndex = 0; disbAmtsIndex < disbAmtSize; ++disbAmtsIndex) 
												{

													com.cgtsi.guaranteemaintenance.DisbursementAmount  disbAmount = (com.cgtsi.guaranteemaintenance.DisbursementAmount)disbAmts.get(disbAmtsIndex)	;
													disamount = disbAmount.getDisbursementAmount();
													dAmount = (new Double(disamount)).toString();
													if(disbAmount.getDisbursementDate()!=null)
													{
														disbDate = dateFormat.format(disbAmount.getDisbursementDate());
													}

													finaldisbursement = disbAmount.getFinalDisbursement();

													disbIdFromDatabase = disbAmount.getDisbursementId();
													totalDisbAmount = totalDisbAmount + disamount; 
	
													if (disbAmtsIndex == 0) 
													{ 
												%>

														 <TD class="tableData" valign="top" align="center" >
															 <%=disamount%>						
														 </TD>

														 <TD class="tableData" valign="top" align="center">									 <%=disbDate%>
														 </TD>

														 <TD class="tableData" valign="top" align="center">
															 <%=finaldisbursement%>
													
														 </TD>

														 <TD class="tableData" valign="top" align="center" id="<%=disbId%>">
														 <%=totalDisbAmount%> 
														 </TD>	
													</TR>
	
												<%
													} 
													else 
													{ 
												%>
													<TR>
														<TD class="tableData"></TD>
														<TD class="tableData"></TD>
														<TD class="tableData"></TD>
														<TD class="tableData"></TD>
														<TD class="tableData"></TD>
														<TD class="tableData"></TD>

														<TD class="tableData" valign="top" align="center">
															<%=disamount%>									
														</TD>

														<TD class="tableData" valign="top" align="center"> 
															<%=disbDate%>									
														</TD>

														<TD class="tableData"  valign="top" align="center">
															<%=finaldisbursement%>							
														</TD>

														<TD class="tableData" id="">
															<%=totalDisbAmount%>
														</TD>
	
												   </TR>
												   <% 
													   count = disbAmtsIndex;
													} 
													%>

											<%  
												}	
											%> <!-- end of inner for loop -->
										<% 
											count++ ; 
											totalDisbAmount = 0;
										%>
								<%	
										}
									}		 //outer for  
								}
								%> <!-- end of condt bid != null  -->
							</TR>
						</logic:iterate>
		<html:hidden property = "noOfDisb" name = "rsPeriodicInfoForm" value = "<%=String.valueOf(disbAmtsIndex)%>"/>

					</table>
				</TD>
			</TR>
			<%}%>
<!-- Outstanding Details Report -->
<%
										String outFlag = (String)session.getAttribute("outFlag");

										if(outFlag!=null && outFlag.equals("Yes"))
										{
									%>
			<TR>
				<TD colspan="6"> 
					<TABLE width="100%" border="0" cellpadding="0" cellspacing="0">
						<TR>
							<TD width="40%" class="Heading">
								<bean:message key="outstandingDetailHeader"/>
								<bean:write name="rsPeriodicInfoForm" property="borrowerId"/>
							</TD>
							<TD>
								<IMG src="images/TriangleSubhead.gif"  height="19">
							</TD>
							<TD align="right" height="21"><div align="right"  height = "19"></div>
							</TD>
						</TR>
						<TR>
							<TD colspan="6" class="Heading" height="7" >
								<img src="images/Clear.gif" width="5" height="5">
							</TD>
						</TR>
					</table>
				</TD>
			</TR>

			<TR>
				<TD colspan="6" valign="top">
					<table width="100%" border="0" cellspacing="1" cellpadding="0">
						<TR>
							<TD align="center" valign="middle" class="HeadingBg" height="7" rowspan="2" align="center"> <center>
							  <bean:message key="srNo"/>
							</TD>									  
							<TD class="HeadingBg" height="7" rowspan="2" align="center" 				align="center"><center>
								<bean:message key="BorrowerID"/>
							</TD>
  						  
							<TD class="HeadingBg" height="7" rowspan="2" align="center" 				align="center"><center>
								<bean:message key = "borrowerName"/>
							</TD>
										  
							<TD class="HeadingBg" height="7" rowspan="2" align="center" 				align="center"><center>
								<bean:message key="cgpanNumber"/>
							</TD>
										  
							<TD class="HeadingBg" height="7" rowspan="2" align="center" 				align="center"><center>
								<bean:message key="scheme"/>
							</TD>

							<TD class="HeadingBg" colspan="2">
								<bean:message key = "sanctioned"/>
							</TD>

										  
							<TD class="HeadingBg" colspan="3" align="center" align="center"><center>
								<bean:message key = "outstandinginRs"/>
							</TD>
										  
										  
							<TD rowspan="2" class="HeadingBg" align="center">
								<bean:message key="totalOutstandingAmount"/>&nbsp;<bean:message key="inRs"/>
							</TD>

							  <td class="HeadingBg" colspan="3" align="center" align="center"><center>Non Fund Based Outstanding </td>

							  <td rowspan="2" class="HeadingBg" align="center">Total Non Fund Based Outstanding Amount</td>

						</TR>
						<TR align="center">							 
									  
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

						</TR>

						<% int j =0;%>
								
						<TR align="center">	
							<logic:iterate id="object3" name="rsPeriodicInfoForm" property="osPeriodicInfoDetails">
						<%
							com.cgtsi.guaranteemaintenance.PeriodicInfo periodicInfo = (com.cgtsi.guaranteemaintenance.PeriodicInfo)object3;
						
							String oscgpan = "";
							String scheme = "";
							String borrowerId = periodicInfo.getBorrowerId();
							String borrowerName = periodicInfo.getBorrowerName();
							if (borrowerId != null) 
							{ 
						%>

								<TD class="tableData" valign="top" align="center"><center>
									<%= ++j%>
								</TD>
						  
								<TD class="tableData" valign="top" align="center">&nbsp;
									<%=borrowerId%>
								</TD>

								<TD class="tableData" valign="top" align="center">
									<%=borrowerName%>
								</TD>
						  
							<%
							  java.util.ArrayList outDetails = (java.util.ArrayList)periodicInfo.getOutstandingDetails();  
						
							   int outDetailsSize = outDetails.size();
							   com.cgtsi.guaranteemaintenance.OutstandingDetail outDetail = null;
						
								for(outDetailIndex=0; outDetailIndex < outDetailsSize; ++outDetailIndex) 
								{
									outDetail = (com.cgtsi.guaranteemaintenance.OutstandingDetail)outDetails.get(outDetailIndex);
									oscgpan = outDetail.getCgpan() ;
									scheme = outDetail.getScheme() ;

									len = oscgpan.length();
									cgpanType = oscgpan.substring(len-2,len-1);

									if (outDetailIndex == 0)
									{	
								%>	
										<TD class="tableData" valign="top" align="center">&nbsp;
											<%=oscgpan%>
										</TD>
																  
										<TD class="tableData" valign="top" align="center">
											<%=scheme%>
										</TD>
								
										<% 
											if(cgpanType.equalsIgnoreCase("w") || 									cgpanType.equalsIgnoreCase("r")) 
											{
									  %>
												<TD class="tableData" valign="top" align="center">
													<%=outDetail.getWcFBSanctionedAmount()%>
												</TD>

												<TD class="tableData" valign="top" align="center">
													<%=outDetail.getWcNFBSanctionedAmount()%>
												</TD>

										  <%} 
											else if (cgpanType.equalsIgnoreCase("t")) 
											{ 
										  %>
												<TD class="tableData" valign="top" align="center">
													<%=outDetail.getTcSanctionedAmount()%>
												</TD>
												<TD class="tableData" valign="top" align="center">
												</TD>

										 <% 
											} 
										 %>

									<% 
										} 
										else 
										{  
									%>
											<TD class="tableData"></TD>
											<TD class="tableData"></TD>
											<TD class="tableData"></TD>
									
											<TD class="tableData" valign="top" align="center">&nbsp;
												<%=oscgpan%>
											</TD>
										  
											<TD class="tableData" valign="top" align="center">
												<%=scheme%>
											</TD>						
								  
											<% 
												if(cgpanType.equalsIgnoreCase("w") || 							cgpanType.equalsIgnoreCase("r")) 
												{
											%>		
													<TD class="tableData" valign="top" align="center">
														<%=outDetail.getWcFBSanctionedAmount()%>
													</TD>

												<TD class="tableData" valign="top" align="center">
													<%=outDetail.getWcNFBSanctionedAmount()%>
												</TD>

											<%
												} 
												else if (cgpanType.equalsIgnoreCase("t")) 
												{ 
											%>
													<TD class="tableData" valign="top" align="center">
														<%=outDetail.getTcSanctionedAmount()%>
													</TD>

													<TD class="tableData" valign="top" align="center">
													</td>
										<%  
												} 
											}  
										%>
										  
										<%
											ArrayList outstandingAmounts = 					(ArrayList)outDetail.getOutstandingAmounts();
											int outAmtSize = outstandingAmounts.size();
					  
											for(outAmtsIndex = 0; outAmtsIndex < outAmtSize; ++outAmtsIndex) 
											{
								   
												OutstandingAmount  outAmount = (OutstandingAmount)outstandingAmounts.get(outAmtsIndex)	;

												amtCgpan = outAmount.getCgpan();
												amtCgpanLen = amtCgpan.length(); 
												amtCgpanType = amtCgpan.substring(amtCgpanLen-2,amtCgpanLen-1);
								
												if( amtCgpanType.equalsIgnoreCase("w") ||					amtCgpanType.equalsIgnoreCase("r")) 
												{ 
													wcFBPrAmt = outAmount.getWcFBPrincipalOutstandingAmount();

													dwcFBPrAmt = (new Double(wcFBPrAmt)).toString();

													wcFBIntAmt = outAmount.getWcFBInterestOutstandingAmount();
													dwcFBIntAmt = (new Double(wcFBIntAmt)).toString();
													wcFBDate = outAmount.getWcFBOutstandingAsOnDate();
													if(wcFBDate!=null)
													{
														dwcFBDate=dateFormat.format(wcFBDate);
													}


													//non fund based
													wcNFBPrAmt = outAmount.getWcNFBPrincipalOutstandingAmount();

													dwcNFBPrAmt = (new Double(wcNFBPrAmt)).toString();

													wcNFBIntAmt = outAmount.getWcNFBInterestOutstandingAmount();
													dwcNFBIntAmt = (new Double(wcNFBIntAmt)).toString();
													wcNFBDate = outAmount.getWcNFBOutstandingAsOnDate();
													if(wcNFBDate!=null)
													{
														dwcNFBDate=dateFormat.format(wcNFBDate);
													}
													else{

														dwcNFBDate="";
													}


													
											%>
														
											<%  
													if (outAmtsIndex == 0) 
													{
											%>
			 
														<TD class="tableData"valign="top" 				align="center">
															<%=wcFBPrAmt%>							
														</TD> 
			
														<TD class="tableData" valign="top" 				align="center">
															<%=wcFBIntAmt%>							
														</TD>
						  
														<TD class="tableData" valign="top" 				align="center">
															<%=dwcFBDate%>							
														</TD>
					 
														<TD class = "tableData" align="center" id = "">
														</TD>   					   
														
														<TD class="tableData"valign="top" 				align="center">
															<%=wcNFBPrAmt%>							
														</TD> 
			
														<TD class="tableData" valign="top" 				align="center">
															<%=wcNFBIntAmt%>							
														</TD>
						  
														<TD class="tableData" valign="top" 				align="center">
															<%=dwcNFBDate%>							
														</TD>
					 
														<TD class = "tableData" align="center" id = "">
														</TD>   					   

													</TR> 

											<% 
												}
												else
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

														<TD class="tableData" valign="top" 				align="center">
															<%=wcFBPrAmt%>							
														</TD>
							  
														<TD class="tableData" valign="top" 				align="center">
															<%=wcFBIntAmt%>						 
														</TD>
						  
														<TD class="tableData" valign="top" 				align="center">
															<%=dwcFBDate%>
														</TD>

														<TD class="tableData" valign="top" 				align="center" id = "">					
														</TD>	

														<TD class="tableData"valign="top" 				align="center">
															<%=wcNFBPrAmt%>							
														</TD> 
			
														<TD class="tableData" valign="top" 				align="center">
															<%=wcNFBIntAmt%>							
														</TD>
						  
														<TD class="tableData" valign="top" 				align="center">
															<%=dwcNFBDate%>							
														</TD>
					 
														<TD class = "tableData" align="center" id = "">
														</TD>   					   

															
													</TR> 
											<% 
												} 
											}
											else if(cgpanType.equalsIgnoreCase("t"))
											{
												tcPrAmt = outAmount.getTcPrincipalOutstandingAmount();
												dtcPrAmt = (new Double(tcPrAmt)).toString();
												tcDate = outAmount.getTcOutstandingAsOnDate();

												if(tcDate!=null)
												{	
													dtcDate=dateFormat.format(tcDate);
												}
								
										 %> 
	
										 <%	
												if (outAmtsIndex == 0)
												{ 
										%>

													<TD class="tableData" valign="top" align="center">
														<%=tcPrAmt%>								  
													</TD>

													<TD class="tableData"></TD>
						  
													<TD class="tableData" valign="top" align="center">
														<%=dtcDate%>								
													</TD>
					  
													<TD class="tableData" valign="top" align="center" 	id = "">
													</TD>

														<TD class="tableData"></TD>
														<TD class="tableData"></TD>
														<TD class="tableData"></TD>
														<TD class="tableData"></TD>

												<TR> 
											<%  }
											    else 
												{ 
											%>
											<TR>
												<TD class="tableData"></TD>
												<TD class="tableData"></TD>
												<TD class="tableData"></TD>
												<TD class="tableData"></TD>
												<TD class="tableData"></TD>
												<TD class="tableData"></TD>						

												<TD class="tableData" valign="top" align="center">
													<%=tcPrAmt%>								 
												</TD>

												<TD class="tableData"></TD>
							  
												<TD class="tableData" valign="top" align="center">
													<%=dtcDate%>
												</TD>
					  
												<TD class="tableData" valign="top" align="center">
												</TD>

														<TD class="tableData"></TD>
														<TD class="tableData"></TD>
														<TD class="tableData"></TD>
														<TD class="tableData"></TD>


											</TR>  
						<% 
										}//end of else.
									}// end of condt TC				
							 }// End of inner For loop
						 %>

						 </TR>

					<%	
						}// end of outer for						
					}
					%> <!-- end of condt bid != null  -->															
				</TR>
			</logic:iterate>
		</TABLE>
	</TD>
</TR>
<%}%>

<!-- NPA Details Report -->

	<%
		String isNpaAvailable = (String)session.getAttribute("isNPAAvailable");
		if(isNpaAvailable!=null && isNpaAvailable.equals("Yes"))
		{
	%>

			<TR>
				<TD colspan="6"> 
					<table width="100%" border="0" cellpadding="0" cellspacing="0">
						<TR>
							<TD width="15%"height="20" class="Heading">&nbsp;
								<bean:message key="NPADetailsHeader"/>
							</TD>

							<TD align="left" valign="bottom">
								<img src="images/TriangleSubhead.gif" height="19">
							</TD>
						</TR>

						<TR>
							<TD colspan="6" class="Heading">
								<img src="images/Clear.gif" height="5">
							</TD>
						</TR>
						<TR>
							<TD colspan="6" class="SubHeading">&nbsp;
								<bean:message key = "BorrowerID"/>
								<bean:write name="rsPeriodicInfoForm"property="borrowerId"/><font	  color="#FFFFFF"></font>&nbsp;&nbsp;&nbsp;&nbsp;<font color="#FFCCCC"><strong>&nbsp;</strong></font>
							</TD>
						</TR>
					</table>
				</TD>
			</TR>

			<bean:define id="obj" name="rsPeriodicInfoForm" property="npaDetails" />
			<%
				NPADetails npadetails = (NPADetails)obj; 
			%>	

			<TR>
				<TD class="ColumnBackground">&nbsp;&nbsp;
					<bean:message key="npaDate"/>
				</TD>
				<TD class="tableData" colspan="4">
				<%
						if(npadetails.getNpaDate()!=null)
						{
				%>
					<%=dateFormat.format(npadetails.getNpaDate())%>
				<%}%>
				</TD>
			</TR>
			<TR>
				<TD class="ColumnBackground">&nbsp;
					<bean:message key="osAmtOnNPA"/>
				</TD>
				<TD class="tableData" colspan="4">
					<%=npadetails.getOsAmtOnNPA()%>
					<bean:message key="inRs"/>
				</TD>
			</TR>
			<TR>
				<TD class="ColumnBackground" align="left">&nbsp;&nbsp;
					<bean:message key="whetherNPAReported"/>
				</TD>
				<TD class="tableData" colspan="4">
					<%=npadetails.getWhetherNPAReported()%>
				</TD>
			</TR>
			<TR>
				<TD class="ColumnBackground">&nbsp;&nbsp;
					<bean:message key="reportingDate"/>
				</TD>
				<TD class="tableData" colspan="4">
				<%
				if(npadetails.getReportingDate()!=null)
				{
				%>
					<%=dateFormat.format(npadetails.getReportingDate())%>
				<%}%>
				</TD>
			</TR>
			<TR>
				<TD class="ColumnBackground">&nbsp;&nbsp;
					<bean:message key="npaReason"/>
				</TD>
				<TD class="tableData" colspan="4">
					<%=npadetails.getNpaReason()%>
				</TD>
			</TR>
			<TR>
				<TD class="ColumnBackground">&nbsp;&nbsp;
					<bean:message key="defaulter"/>
				</TD>
				<TD class="tableData" colspan="4">
					<%=npadetails.getWillfulDefaulter()%>
				</TD>
			</TR>
			<TR>
				<TD class="ColumnBackground">&nbsp;&nbsp;
					<bean:message key="enumerateEfforts"/>
				</TD>
				<TD class="tableData" colspan="4">
				<%
						if(npadetails.getEffortsTaken()!=null)
						{
				%>
					<%=npadetails.getEffortsTaken()%>
					<%}%>
				</TD>
			</TR>
			<TR>
				<TD colspan="6" class="SubHeading">&nbsp;
					<bean:message key="recoveryProcedure"/>
				</TD>
			</TR>
			<TR>
				<TD align=middle colspan="6">
					<table width="100%" border="0" cellspacing="1" cellpadding="0"  class="TableData">
						<TR>
							<TD class="ColumnBackground" align="left">&nbsp;
								<bean:message key="actionType"/>
							</TD>
							<TD class="ColumnBackground" align="left">&nbsp;
								<bean:message key="details"/>
							</TD>
							<TD class="ColumnBackground" align="left">&nbsp;
								<bean:message key="date"/>
							</TD>
							<TD class="ColumnBackground">&nbsp;
								<bean:message key="attachment"/>
							</TD>
						</TR>
	
						<%
							int recsize =0; 
							ArrayList recoprocedures = (ArrayList)npadetails.getRecoveryProcedure();
							if(recoprocedures!=null)
							{
								if(!recoprocedures.isEmpty())
								{
									recsize=recoprocedures.size();
									for(int reccount=0;reccount<recsize;++reccount)
									{										
										RecoveryProcedure recProc=(RecoveryProcedure)recoprocedures.get(reccount);
						%>
										<TR>
											<TD class="tableData" align="center">
												<%=recProc.getActionType()%>
											</TD>

											<TD class="tableData" valign="top" align="center">
												<%=recProc.getActionDetails()%>  
											</TD>	
											
											<TD class="tableData" valign="top"	align="center">
											<%
												if(recProc.getActionDate()!=null)
												{
											%>
												<%=dateFormat.format(recProc.getActionDate())%>
											<%}%>
											</TD>
 
											<TD class="tableData" valign="top" align="center">
											<%
												if(recProc.getAttachmentName()!=null)
												{
											%>
												<%=recProc.getAttachmentName()%>
											<%}%>
											</TD>
											
										</TR>						
						<% 
									}
								}
							}	
						%>
							</table>
						</TD>	
					</TR>	
					<TR>
						<TD colspan="1" class="ColumnBackground">&nbsp;
							<bean:message key="isRecoveryInitiated"/>
						</TD>
						<TD  class="tableData" colspan="4">
							<%=npadetails.getIsRecoveryInitiated()%>
						</TD>
					</TR>
					<%
						LegalSuitDetail legalDetail = (LegalSuitDetail)npadetails.getLegalSuitDetail();

					%>
					<TR>
						<TD colspan="6" class="SubHeading">&nbsp; 
							<bean:message key = "legalDetails"/>
						</TD>
					</TR>
					<TR>
						<TD colspan="6" class="SubHeading">
							<table width="100%" border="0" cellspacing="1" cellpadding="0">
								<TR>
									<TD class="ColumnBackground">&nbsp; 
										<bean:message key = "forum"/>
									</TD>
								<%
									if(legalDetail!=null)
									{
								%>
										<TD class="tableData" colspan="12"> 
											<%=legalDetail.getCourtName()%>
										</TD>
								<%	}
									else
									{
								%>
										<TD class="tableData" colspan="3"> 
										</TD>
								<%	
									}
								%>
									</TR>
									<TR>
										<TD class="ColumnBackground">&nbsp;
											<bean:message key = "suit"/>
										</TD>

									<%
										if(legalDetail!=null)
										{
									%>
											<TD class="tableData" colspan="6"> 
												<%=legalDetail.getLegalSuiteNo()%>
											</TD>
									<%	}
										else
										{
									%>
											<TD class="tableData" colspan="3"> 
											</TD>
									<%	
										}
									%>

										<TD class="ColumnBackground">&nbsp;
											<bean:message key="date"/>
										</TD>
									<%
										if(legalDetail!=null)
										{
									%>
											<TD class="tableData" colspan="4"> 	
											<%
											if(legalDetail.getDtOfFilingLegalSuit()!=null)
											{
											%>
											<%=dateFormat.format(legalDetail.getDtOfFilingLegalSuit())%>
											<%}%>
											</TD>
									<%	}
										else
										{
									%>
											<TD class="tableData" colspan="3"> 
											</TD>
									<%	
										}
									%>
									</TR>
									<TR>
										<TD class="ColumnBackground">&nbsp;
											<bean:message key = "forumName"/>
										</TD>
									<%
										if(legalDetail!=null)
										{
									%>
											<TD class="tableData" colspan="6"> 							<%=legalDetail.getForumName()%>
											</TD>
									<%	}
										else
										{
									%>
											<TD class="tableData" colspan="3"> 
											</TD>
									<%	
										}
									%>
										<TD class="ColumnBackground">&nbsp;
											<bean:message key="location"/>
										</TD>
									<%	if(legalDetail!=null)
										{
									%>
											<TD class="tableData" colspan="4"> 							<%=legalDetail.getLocation()%>
											</TD>
									<%	}
										else
										{
									%>
											<TD class="tableData" colspan="3"> 
											</TD>
									<%
										}
									%>
										</TR>
										<TR>
											<TD class="ColumnBackground">&nbsp;
												<bean:message 	key="amountClaimed"/>
											</TD>													
										<%  
											if(legalDetail!=null)
											{
										%>
												<TD class="tableData" colspan="12"> 						<%=legalDetail.getAmountClaimed()%>
												</TD>
										<%	}
											else
											{
										%>
												<TD class="tableData" colspan="3"> 
												</TD>
										<%	
											}
										%>											
										</TR>
										<TR>
											<TD class="ColumnBackground">&nbsp;
												<bean:message key="currentStatus"/>
											</TD>
										<%
											if(legalDetail!=null)
											{
										%>
												<TD class="tableData" colspan="12"> 	
												<%
												if(legalDetail.getCurrentStatus()!=null)
												{
												%>
												<%=legalDetail.getCurrentStatus()%>
												<%}%>
												</TD>
										<%	}
											else
											{
										%>
												<TD class="tableData" colspan="3"> 
												</TD>
										<%	
											}
										%>											
											</TR>
											<TR>
												<TD class="ColumnBackground">&nbsp;
													<bean:message key="recoveryProceedingsConcluded"/>
												</TD>
											<%	if(legalDetail!=null)
												{
											%>
													<TD class="tableData" colspan="12">				
													<%=
													legalDetail.getRecoveryProceedingsConcluded()
													%>
													</TD>
											<%
												}
												else
												{
											%>
													<TD class="tableData" colspan="3"> 
													</TD>	
											<%	
												}
											%>											

											</TR>
										</table>
									</TD>
								</TR>
				                <TR align="center">
								  <TD colspan="4" class="ColumnBackground" align="left">&nbsp;
									  <bean:message key="dateOfConclusion"/>                    
								</TD>
								<TD class="tableData">
								<%
										  if(npadetails.getEffortsConclusionDate()!=null)
											{
								%>
									<%=dateFormat.format(npadetails.getEffortsConclusionDate())%>	
									<%}%>
								</TD>
			                </TR>
						  </table>
		              </TD>
				    </TR>
		        <TR align="center">
			      <TD colspan="2" class="SubHeading" height="382">
					<table width="100%" border="0" cellspacing="1" cellpadding="0">
			            <TR align="center">
					      <TD class="SubHeading" align="left">
							 <bean:message key="others"/>
						  </TD>
						</TR>
						<TR>
							<TD class="ColumnBackground" colspan="3">&nbsp;
								<bean:message key="mliComment"/>
							</TD>
							<TD class="tableData" colspan="3">
							<%if(npadetails.getMliCommentOnFinPosition()!=null)
							{%>
								<%=npadetails.getMliCommentOnFinPosition()%>
							<%}%>
							</TD>
						</TR>
					<TR>
						<TD class="ColumnBackground" colspan="3">&nbsp;
							<bean:message key = "finAssistDetails"/>
						</TD>
						<TD class="tableData" colspan="3">
						<%
								if(npadetails.getDetailsOfFinAssistance()!=null)
								{
						%>
							<%=npadetails.getDetailsOfFinAssistance()%>
						<%}%>
						</TD>
					</TR>
					<TR>
						<TD class="ColumnBackground" colspan="3">&nbsp;
							<bean:message key="creditSupport"/>
						</TD>
						<TD class="tableData" colspan="3">
							<%=npadetails.getCreditSupport()%>						
						</TD>
					</TR>
					<TR>
						<TD class="ColumnBackground" colspan="3">&nbsp;
							<bean:message key="bankFacilityProvided"/>
						</TD>
						<TD class="tableData" colspan="3">
						<%if(npadetails.getBankFacilityDetail()!=null)
							{%>
							<%=npadetails.getBankFacilityDetail()%>
						<%}%>
						</TD>
					</TR>
					<TR>
						<TD class="ColumnBackground" colspan="3">&nbsp;
							<bean:message key="placeUnderWatchList"/>
						</TD>
						<TD class="tableData" colspan="3">
							<%=npadetails.getPlaceUnderWatchList()%>						
					  </TD>
                </TR>
                <TR>
                  <TD class="ColumnBackground" colspan="3">&nbsp;
					<bean:message key="otherRemarks"/>
				  </TD>
                  <TD class="tableData" colspan="3">
				  <%
						if(npadetails.getRemarksOnNpa()!=null)
						{
					%>
				     <%=npadetails.getRemarksOnNpa()%>	
					 <%}%>
				  </TD>
                </TR>


	<%
		}
	%>



<!-- Recovery Details Report -->

<%
										String recFlag = (String)session.getAttribute("recFlag");
										

										if(recFlag!=null && recFlag.equals("Yes"))
										{
									%>
	<TR>
		<TD colspan="4"> 
			<TABLE width="100%" border="0" cellpadding="0" cellspacing="0">
				<TR>
					<TD width="31%" class="Heading">
						<bean:message key="recoveryHeader"/>
						<bean:write name="rsPeriodicInfoForm" property="borrowerId"/>
					</TD>
					<TD>
						<IMG src="images/TriangleSubhead.gif" width="19" height="19">
					</TD>
				</TR>
				<TR>
					<TD colspan="3" class="Heading">
						<IMG src="images/Clear.gif" width="5" height="5">
					</TD>
				</TR>
			</TABLE>
		</TD>
	</TR>
					
							<logic:iterate id="object4" name="rsPeriodicInfoForm" property = "recoveryDetails">
							<%	
								Recovery recDetail=(Recovery)object4;
							%>

								<TR>
									<TD class="ColumnBackground" width="146" height="25">&nbsp;
										<bean:message key="recoveryDate"/>
									</TD>
									<TD class="TableData" width="90">
										<%=recDetail.getDateOfRecovery()%>
									</TD>
									<TD class="ColumnBackground" width="227" height="25">
										<bean:message key="amountRecovered" />
									</TD>
									<TD class="TableData" width="210" height="25">&nbsp;
										<%=recDetail.getAmountRecovered()%>
									</TD>
								</TR>
								<TR>
									<TD width="240" class="ColumnBackground" height="28" colspan="2">
										<bean:message key="legalCharges"/>
									</TD>
									<TD width="441" class="TableData" height="28" colspan="2">
										<%=recDetail.getLegalCharges()%>
									</TD>
								</TR>
								<TR>
									<TD class="ColumnBackground" width="240" colspan="2" height="43">
										<bean:message key="remarks"/>
									</TD>
									<TD class="TableData" width="441" colspan="2" height="43">&nbsp;
										<%=recDetail.getRemarks()%>
									</TD>
								</TR>
								<TR>
									<TD colspan="2" align="left" class="ColumnBackground" width="240" height="21">&nbsp;
										<bean:message key="isRecoveryByOts"/>
									</TD>
									<TD colspan="2" align="left" class="TableData" width="441" height="21">&nbsp;
										<%=recDetail.getIsRecoveryByOTS()%>
									</TD>
								</TR>
								<TR>
									<TD class="ColumnBackground" width="240" colspan="2" height="21">&nbsp;
										<bean:message key="isRecoveryByAssets"/>
									</TD>
									<TD class="TableData" width="441" colspan="2" height="21">&nbsp;&nbsp;
										<%=recDetail.getIsRecoveryBySaleOfAsset()%>
									</TD>
								</TR>
								<TR>
									<TD class="ColumnBackground" width="240" colspan="2" height="43">&nbsp;
										<bean:message key="detailsOfAsset"/>
									</TD>
									<TD class="TableData" width="441" colspan="2" height="43">&nbsp;
										<%=recDetail.getDetailsOfAssetSold()%>	
									</TD>
								</TR>  
									
								<TR>
									<TD class="SubHeading">Additional Recovery 
									</TD>						
								</TR>

							</logic:iterate>
<%}%>
							
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
									<A href="javascript:history.back()">
									<IMG src="images/Back.gif" alt="OK" width="49" height="37" border="0"></A>
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