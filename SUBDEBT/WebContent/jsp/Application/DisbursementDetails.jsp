<%@ page language="java"%>
<%@ page import="java.util.ArrayList"%>
<%@ page import="com.cgtsi.guaranteemaintenance.Disbursement"%>
<%@ page import="com.cgtsi.guaranteemaintenance.DisbursementAmount"%>
<%@ page import="com.cgtsi.guaranteemaintenance.PeriodicInfo"%>
<%@ page import="java.text.SimpleDateFormat"%>
<%@ page import="java.util.Date"%>


<%@ taglib uri="/WEB-INF/TLD/struts-html.tld" prefix="html" %>
<%@ taglib uri="/WEB-INF/TLD/struts-bean.tld" prefix="bean" %>
<%@ taglib uri="/WEB-INF/TLD/struts-logic.tld" prefix="logic" %>
<%@ include file="/jsp/SetMenuInfo.jsp" %>

<% session.setAttribute("CurrentPage","showDisbursementDetails.do");
String name;
String disbDate;
SimpleDateFormat dateFormat=new SimpleDateFormat("dd/MM/yyyy");
%>

<TABLE width="725" border="0" cellpadding="0" cellspacing="0">
	<html:errors />
	<html:form action="saveDisbursementDetails.do?method=saveDisbursementDetails" method="POST" enctype="multipart/form-data">
		<TR> 
			<TD width="20" align="right" valign="bottom"><IMG src="images/TableLeftTop.gif" width="20" height="31"></TD>
			<TD background="images/TableBackground1.gif"><IMG src="images/GuaranteeMaintenanceHeading.gif"></TD>
			<TD width="20" align="left" valign="bottom"><IMG src="images/TableRightTop.gif" width="23" height="31"></TD>
		</TR>
		<TR>
			<TD width="20" background="images/TableVerticalLeftBG.gif">&nbsp;</TD>
			<TD>
				<TABLE width="100%" border="0" align="left" cellpadding="0" cellspacing="0">
					<TR>
						<TD>
							<TABLE width="100%" border="0" cellspacing="1" cellpadding="1">
								<TR>
									<TD colspan="5"> 
										<TABLE width="100%" border="0" cellpadding="0" cellspacing="0">
										<tr> 
										  <td width="35%" class="Heading"><bean:message key="disbursementDetailsHeader"/><bean:write name = "gmForm" property = "BorrowerID"/></td>
										  <td colspan="2" align="left" valign="bottom"><img src="images/TriangleSubhead.gif" width="19" height="19"></td>
										  <td align="right"> <div align="right"> </div></td>
										</tr>
										<tr> 
										  <td colspan="4" class="Heading"><img src="images/Clear.gif" width="5" height="5"><A href="javascript:submitForm('showOutstandingDetailsLink.do?method=showOutstandingDetailsLink')">Outstanding Details</A> | <A href="javascript:submitForm('showRepaymentDetailsLink.do?method=showRepaymentDetailsLink')">Repayment Details</A>| <A href="javascript:submitForm('showNPADetailsLink.do?method=showNPADetailsLink')">NPA Details</A>|<A href="javascript:submitForm('showRecoveryDetailsLink.do?method=showRecoveryDetailsLink')">Recovery Details</A> </td>
										</tr>
									 </table>
								 </td>
							  </tr>
					          <tr> 
					            <td colspan="4">
									<table width="100%" border="0" cellspacing="1" cellpadding="0">
										<tr> 
										  <td align="center" valign="middle" class="Heading" 
											 align="center"><bean:message key = "SrNo"/>
										  </td>
										  <td class="Heading" align="center"><bean:message 					key="borrowerId"/>
										  </td>
										  <td class="Heading" align="center"><bean:message 					key="BorrowerName"/>
										  </td>
										  <td class="Heading" align="center"><bean:message 					key="cgpanNumber"/>
										  </td>
										  <td class="Heading" align="center"><bean:message key = 			"scheme"/>
										  </td>
										  <td class="Heading" align = "center"><bean:message key = 			"sanctionedAmount"/>
										  </td>
										  <td class="Heading" width="20%" align="center"><bean:message 		key="disbursementAmountinRs"/>
										  </td>
										  <td class="Heading" align="center"><bean:message key="date"/>
										  </td>
										  <td class="Heading" width="10%" align="center"><bean:message 		key="finalDisbursement"/>
										  </td>
										  <td class="Heading" align="center"><bean:message 					key="totalDisbursementAmount"/>
										  </td>
									  </tr>

								<% int i=0; int disbursementEntryIndex=0;%>
								<tr align="center"> 
									<logic:iterate id="object" name="gmDisbDtlForm" property = "periodicInfoDetails">
										<%
											com.cgtsi.guaranteemaintenance.PeriodicInfo periodicInfo = (com.cgtsi.guaranteemaintenance.PeriodicInfo)object; 
											String cgpan = "";
											String scheme = "";
											String borrowerId = periodicInfo.getBorrowerId();
											String borrowerName = periodicInfo.getBorrowerName();%>

										<td class="tableData" align="center"><%=++i%>
										</td>
								  
										<td class="tableData">&nbsp;<a href =    "BorrowerDetailsDisbursement.html"><%=borrowerId%></a>
										</td>
										<td class="tableData">&nbsp;<a href="PanDetailsDisbursement.html"><%=borrowerName%></a>
										</td>
									
										<%
										java.util.ArrayList disbursements = (java.util.ArrayList)periodicInfo.getDisbursementDetails();  
														
										int disbursementsSize = disbursements.size();
										com.cgtsi.guaranteemaintenance.Disbursement disbursement = null;
									
										System.out.println("disbursement obj:"+disbursementsSize);				
									
										for(int disbursementIndex =0; disbursementIndex < disbursementsSize; ++disbursementIndex) {
											
											disbursement = (com.cgtsi.guaranteemaintenance.Disbursement)disbursements.get(disbursementIndex);
											cgpan = disbursement.getCgpan() ;
											scheme = disbursement.getScheme() ;
											double sanctionedAmount = disbursement.getSanctionedAmount() ;
											if(disbursementIndex == 0) { %>
											
											  <td class="tableData" valign="top" align="center">&nbsp;<a
											  href="PanDetailsOutstanding.html"><%=cgpan%></a>
											  </td>
													  
											  <td class="tableData" valign="top" align="center"><%=scheme%>
											  </td>						
											  
											  <td class="tableData" valign="top" align="center"><%=sanctionedAmount%>
											  </td>						
											  <%  } else { %>
	    										<td class="tableData"></td>
												<td class="tableData"></td>
												<td class="tableData"></td>
												
											  <td class="tableData" valign="top" align="center">&nbsp;<a
											  href="PanDetailsOutstanding.html"><%=cgpan%></a>
											  </td>
													  
											  <td class="tableData" valign="top" align="center"><%=scheme%>
											  </td>						
											  
											  <td class="tableData" valign="top" align="center"><%=sanctionedAmount%>
											  </td>						
											  <% } %>  
											<% java.util.ArrayList disbAmts = (java.util.ArrayList)disbursement.getDisbursementAmounts();	
											  int disbAmtSize = disbAmts.size();
											  double amount = 0;
											  //java.util.Date disbDate = null;
											  String finaldisbursement = null;
											 
											  System.out.println("size:"+disbAmtSize);			

											  for(int disbAmtsIndex = 0, flag = 0; disbAmtsIndex < disbAmtSize; ++disbAmtsIndex) {
												
												com.cgtsi.guaranteemaintenance.DisbursementAmount  disbAmount = (com.cgtsi.guaranteemaintenance.DisbursementAmount)disbAmts.get(disbAmtsIndex)	;
												cgpan = disbAmount.getCgpan();
												amount = disbAmount.getDisbursementAmount();
												//disbDate = disbAmount.getDisbursementDate();
												disbDate = dateFormat.format(disbAmount.getDisbursementDate());

												finaldisbursement = disbAmount.getFinalDisbursement();
												 
												 if (disbAmtsIndex == 0) { %>

												 <td class="tableData" valign="top" align="center"><%=amount%>
												 </td>						
											  
												 <td class="tableData" valign="top" align="center"><%=disbDate%>
												 </td>						
												
												 <td class="tableData" valign="top" align="center">
												 </td>	

												 <td class="tableData" valign="top" align="center"> 
														 <input name="tout23" type="text" id="tout22" size="10" maxlength="10">
												 </td>	
											
												 <%} else { %>
													<TR>	
														<td class="tableData"></td>
														<td class="tableData"></td>
														<td class="tableData"></td>
														<td class="tableData"></td>
														<td class="tableData"></td>
														<td class="tableData"></td>
														
														<td class="tableData" valign="top" align="center"><%=amount%>
														</td>						
											  
														<td class="tableData" valign="top" align="center"><%=disbDate%>
														</td>		
														<td class="tableData"  valign="top" align="center"></td>
														<td class="tableData"></td>
									
											       </TR>
											    <% } %>
												
											<%  }	%> <!-- end of inner for loop -->					<TR>
													<td class="tableData"></td>
													<td class="tableData"></td>
													<td class="tableData"></td>
													<td class="tableData"></td>
													<td class="tableData"></td>
													<td class="tableData"></td>
													
													<%name = "cgpans(key-"+disbursementEntryIndex+")";%>
													<html:hidden property = "<%=name%>" name="gmDisbDtlForm" value="<%=cgpan%>"/>
													
													<td class="tableData" valign="top" align="center"><%name = "disbursementAmount(key-"+disbursementEntryIndex+")";%> <html:text property = "<%=name%>" size="10" name="gmDisbDtlForm" value=""/>
													</td>	
													
											  		<td class="tableData" valign="top" align="center">
													<%name = "disbursementDate(key-"+disbursementEntryIndex+")";
													%> 
													<html:text property="<%=name%>" size="10" name="gmDisbDtlForm" value=""/>
													<IMG src="images/CalendarIcon.gif" width = "20" onClick="showCalendar('gmDisbDtlForm.<%=name%>')" align="center">
													</td>
													
													<td align="right" valign="top"class="tableData">
									<%name="finalDisbursement(key-"+disbursementEntryIndex+")";%>
														 <html:checkbox property="<%=name%>"  alt="final" name="gmDisbDtlForm" value="Y"/>
													</td>
													<td class="tableData"></td><%++disbursementEntryIndex;%>
									       </TR>
								     						
										<%	}%>		<!-- outer for  -->							
								</TR>			 
						</logic:iterate>

<html:hidden property = "disbursementEntryIndex" name="gmDisbDtlForm" value="<%=String.valueOf(disbursementEntryIndex)%>"/>
						
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
											<A href="javascript:document.gmForm.reset()">
												<IMG src="images/Reset.gif" alt="Cancel" width="49" height="37" border="0"></A>
												<A href="javascript:submitForm('saveDisbursementDetails.do?method=saveDisbursementDetails')">
												<IMG src="images/OK.gif" alt="OK" width="49" height="37" border="0"></A>
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
			</TABLE>