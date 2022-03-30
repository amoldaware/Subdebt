<%@ page language="java"%>
<%@ page import="java.util.ArrayList"%>
<%@ page import="com.cgtsi.guaranteemaintenance.Disbursement"%>
<%@ page import="com.cgtsi.guaranteemaintenance.DisbursementAmount"%>
<%@ page import="com.cgtsi.guaranteemaintenance.PeriodicInfo"%>
<%@ page import="com.cgtsi.actionform.GMActionForm"%>
<%@ page import="java.text.SimpleDateFormat"%>
<%@page import ="java.text.DecimalFormat"%>

<%@ page import="java.util.Date"%>


<%@ taglib uri="/WEB-INF/TLD/struts-html.tld" prefix="html" %>
<%@ taglib uri="/WEB-INF/TLD/struts-bean.tld" prefix="bean" %>
<%@ taglib uri="/WEB-INF/TLD/struts-logic.tld" prefix="logic" %>
<%@ include file="/jsp/SetMenuInfo.jsp" %>

<% session.setAttribute("CurrentPage","showDisbursementDetails.do?method=showDisbursementDetails");
String name = null;
String disbDate = null;
SimpleDateFormat dateFormat=new SimpleDateFormat("dd/MM/yyyy");
int disbAmtsIndex = 0;
String disbId = null;
String dAmount = null;
int count = 0;
String sancAmtId = null;
String disbIdFromDatabase = null;
DecimalFormat decimalFormat = new DecimalFormat("##########.##");

%>
	<body onload ="javascript:displayDbrTotal(),validateFinalDisbursementOnLoad()" >
	<html:form action="saveDisbursementDetails.do?method=saveDisbursementDetails" method="POST" enctype="multipart/form-data">
<TABLE width="725" border="0" cellpadding="0" cellspacing="0">
	<html:errors />

		<TR>
			<TD width="20" align="right" valign="bottom"><IMG src="images/TableLeftTop.gif" width="20" height="31">
			</TD>
			<TD background="images/TableBackground1.gif"><IMG src="images/GuaranteeMaintenanceHeading.gif">
			</TD>
			<TD width="20" align="left" valign="bottom"><IMG src="images/TableRightTop.gif" width="23" height="31">
			</TD>
		</TR>
		<TR>
			<TD width="20" background="images/TableVerticalLeftBG.gif">&nbsp;
			</TD>			
			<TD>
			<DIV align="right">			
				<A HREF="javascript:submitForm('helpDisbursementDetails.do?method=helpDisbursementDetails')">
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
										  <td width="35%" class="Heading"><bean:message key="disbursementDetailsHeader"/><bean:write name = "gmPeriodicInfoForm" property = "borrowerId"/></td>
										  <td  align="left" valign="bottom"><img src="images/TriangleSubhead.gif" width="19" height="19"></td>
										  <td align="right"> <div align="right"> </div></td>
										</tr>
										<tr>
										  <td colspan="6" class="Heading"><img src="images/Clear.gif" width="5" height="5">
											  <A href="javascript:submitForm('showOutstandingDetailsLink.do?method=showOutstandingDetailsLink')">Outstanding Details</A> | 
											  <A href="javascript:submitForm('showRepaymentDetailsLink.do?method=showRepaymentDetailsLink')">Repayment Details</A>| 
											<!--  <A href="javascript:submitForm('showNPADetailsLink.do?method=showNPADetailsLink')">NPA Details</A>| -->
											  <A href="javascript:submitForm('showRecoveryDetailsLink.do?method=showRecoveryDetailsLink')">Recovery Details</A> 
										  </td>
										</tr>
									 </table>
								 </td>
							  </tr>
                                                          
                                                          <TR>
                                                            <TD colspan="6" style="color:red;font:14px;">
                                                                Note: Please mark check box, if you are entering final disbursement details.
                                                            </TD>
                                                          </TR>

                                                          
					          <tr>
					            <td colspan="6">
									<table width="100%" border="0" cellspacing="1" cellpadding="0">
										<tr>
										  <td align="center" valign="middle" class="HeadingBg"
											 align="center"><bean:message key = "srNo"/>
										  </td>
										  <td class="HeadingBg" align="center"><bean:message 					key="borrowerId"/>
										  </td>
										  <td class="HeadingBg" align="center"><bean:message 					key="borrowerName"/>
										  </td>
										  <td class="HeadingBg" align="center"><bean:message 					key="cgpanNumber"/>
										  </td>
										  <td class="HeadingBg" align="center"><bean:message key = 			"scheme"/>
										  </td>
										  <td class="HeadingBg" align = "center"><bean:message key = 			"sanctionedAmount"/>
										  </td>
										  <td class="HeadingBg" align="center"><font color="#FF0000" size="2">*</font><bean:message 		key="disbursementAmountinRs"/>
										  </td>
										  <td class="HeadingBg" align="center"><font color="#FF0000" size="2">*</font><bean:message key="date"/>
										  </td>
										  <td class="HeadingBg" align="center"><font color="#FF0000" size="2">*</font><bean:message key="finalDisbursement"/>
										  </td>
										  <td class="HeadingBg" align="center"><bean:message 					key="totalDisbursementAmount"/>
										  </td>
									  </tr>

								<% int i=0; int disbursementEntryIndex=0;%>
						<tr align="center">
                                                        <bean:define id="disbursementMap" name="gmPeriodicInfoForm" property = "finalDisbursement"/>
							<logic:iterate id="object" name="gmPeriodicInfoForm" property = "disbPeriodicInfoDetails">
								<%
									com.cgtsi.guaranteemaintenance.PeriodicInfo periodicInfo = (com.cgtsi.guaranteemaintenance.PeriodicInfo)object;
									String cgpan = "";
									String scheme = "";
									String borrowerId = periodicInfo.getBorrowerId();
									String borrowerName = periodicInfo.getBorrowerName();%>
								<%if (borrowerId != null) { %>
								<td class="tableData" align="center"><%=++i%>
								</td>

								<td class="tableData">&nbsp;<A
							href="javascript:submitForm('showBorrowerDetailsLink.do?method=showBorrowerDetailsLink&formFlag=periodic&bidLink=<%=borrowerId%>')"><%=borrowerId%></A>
								</td>
								<td class="tableData">&nbsp;<%=borrowerName%>
								</td>

								<%
								java.util.ArrayList disbursements = (java.util.ArrayList)periodicInfo.getDisbursementDetails();

								int disbursementsSize = disbursements.size();
								com.cgtsi.guaranteemaintenance.Disbursement disbursement = null;

								for(int disbursementIndex =0; disbursementIndex < disbursementsSize; ++disbursementIndex) {

									disbursement = (com.cgtsi.guaranteemaintenance.Disbursement)disbursements.get(disbursementIndex);
									cgpan = disbursement.getCgpan() ;
									scheme = disbursement.getScheme() ;
									double sanctionedAmount = disbursement.getSanctionedAmount() ;
									disbId = "totalDisbAmt"+disbursementIndex ;%>

									<%	if(cgpan!=null) { %>

									<%name = "cgpans(key-"+disbursementIndex+")";%>
									<html:hidden property = "<%=name%>" name="gmPeriodicInfoForm" value="<%=cgpan%>"/>

									<% sancAmtId = "sanDisb("+disbursementIndex+")" ; %>

									<% if(disbursementIndex == 0) { %>

									  <td class="tableData" valign="top" align="center">&nbsp;<A
									href="javascript:submitForm('showCgpanDetailsLink.do?method=showCgpanDetailsLink&cgpanDetail=<%=cgpan%>')"><%=cgpan%></A>
									  </td>

									  <td class="tableData" valign="top" align="center"><%=scheme%>
									  </td>
									
									  <td class="tableData" valign="top" align="center" id="<%=sancAmtId%>"><%=decimalFormat.format(sanctionedAmount)%>
									  </td>
									  <%  } else { %>
										<td class="tableData"></td>
										<td class="tableData"></td>
										<td class="tableData"></td>

									  <td class="tableData" valign="top" align="center">&nbsp;<a
									  href="javascript:submitForm('showCgpanDetailsLink.do?method=showCgpanDetailsLink&cgpanDetail=<%=cgpan%>')"><%=cgpan%></a>
									  </td>

									  <td class="tableData" valign="top" align="center"><%=scheme%>
									  </td>

									  <td class="tableData" valign="top" align="center" id="<%=sancAmtId%>"><%=decimalFormat.format(sanctionedAmount)%>
									  </td>
									  <% } %>

							<bean:define type="java.util.Map" id="disAmtMap" name="gmPeriodicInfoForm" property="disbursementAmount"/>

							<bean:define type="java.util.Map" id="disDateMap" name="gmPeriodicInfoForm" property="disbursementDate"/>


									<% java.util.ArrayList disbAmts = (java.util.ArrayList)disbursement.getDisbursementAmounts();
									  int disbAmtSize = disbAmts.size();
									  double amount = 0;
									  String finaldisbursement = null;

									  boolean finalFlag=true;

									  for(disbAmtsIndex = 0; disbAmtsIndex < disbAmtSize; ++disbAmtsIndex) {

										com.cgtsi.guaranteemaintenance.DisbursementAmount  disbAmount = (com.cgtsi.guaranteemaintenance.DisbursementAmount)disbAmts.get(disbAmtsIndex)	;
										amount = disbAmount.getDisbursementAmount();
										dAmount = (new Double(amount)).toString();
										disbDate = dateFormat.format(disbAmount.getDisbursementDate());

										finaldisbursement = disbAmount.getFinalDisbursement();

										//System.out.println(finaldisbursement);

										disbIdFromDatabase = disbAmount.getDisbursementId();

									if (disbAmtsIndex == 0) { %>

									 <%
										if(disbIdFromDatabase!=null)
										{
											name = "disbursementId("+cgpan+"-"+disbAmtsIndex+"-"+disbIdFromDatabase+")";
									 %>
										<html:hidden property = "<%=name%>" name="gmPeriodicInfoForm" value="<%=disbIdFromDatabase%>"/>
									<%
										}


										if(request.getParameter("disbursementAmount("+cgpan+"-"+"0"+")")!=null)
										{
											if(request.getParameter("disbursementAmount("+cgpan+"-"+"0"+")").equals(""))
											{
												disAmtMap.put(cgpan+"-"+disbAmtsIndex,new Double(0));
											}
											else
											{
												double disAmt = Double.parseDouble(request.getParameter("disbursementAmount("+cgpan+"-"+"0"+")"));

												disAmtMap.put(cgpan+"-"+disbAmtsIndex,new Double(disAmt));

											}
										}
										else{

											disAmtMap.put(cgpan+"-"+disbAmtsIndex,dAmount);
										}

									%>

										 <td class="tableData" valign="top" align="center" ><% name = "disbursementAmount("+cgpan+"-"+"0"+")";%> <html:text property = "<%=name%>" size="10" name="gmPeriodicInfoForm"  onblur= "javascript:displayDbrTotal()" onkeypress="return decimalOnly(this, event,13)" onkeyup="isValidDecimal(this)" maxlength="16"/>
										 </td>

										 <%
										if(request.getParameter("disbursementDate("+cgpan+"-"+disbAmtsIndex+")")!=null)
										{
											 if(request.getParameter("disbursementDate("+cgpan+"-"+disbAmtsIndex+")").equals(""))
											{
												 disDateMap.put(cgpan+"-"+disbAmtsIndex,null);
											}
											else
											{
											disDateMap.put(cgpan+"-"+disbAmtsIndex,request.getParameter("disbursementDate("+cgpan+"-"+disbAmtsIndex+")"));

											}
										}
										else{

											disDateMap.put(cgpan+"-"+disbAmtsIndex,disbDate);
										}
											 
										 %>

										 <td class="tableData" valign="top" align="center">
										 <% name = "disbursementDate("+cgpan+"-"+disbAmtsIndex+")";%>
										 <html:text property = "<%=name%>" size="10" name="gmPeriodicInfoForm"  maxlength="10"/>
										 </td>

										 <td class="tableData" valign="top" align="center">
										 <%

										 	name="finalDisbursement("+cgpan+"-"+disbAmtsIndex+")";

											java.util.Map map=(java.util.Map)disbursementMap;

										if(request.getParameter("finalDisbursement("+cgpan+"-"+disbAmtsIndex+")")!=null)
										{
											map.put(cgpan+"-"+disbAmtsIndex,(String)request.getParameter("finalDisbursement("+cgpan+"-"+disbAmtsIndex+")"));
										}
										else if(finaldisbursement!=null && finaldisbursement.equals("Y"))
										 	{

										 		map.put(cgpan+"-"+disbAmtsIndex,"Y");

										 		//System.out.println("inside");

										 	}
											else
											 {
											map.put(cgpan+"-"+disbAmtsIndex,"N");
											 }
										 	boolean disableFlag=false;
										 	if(disbAmtSize >= 2)
										 	{
										 		disableFlag=true;
										 	}
										 %>
										 <html:checkbox property="<%=name%>"  alt="final" name="gmPeriodicInfoForm" value="Y" disabled="<%=disableFlag%>" onclick = "javascript:validateFinalDisbursement(this)"/>
										 </td>

										 <td class="tableData" valign="top" align="center" id="<%=disbId%>">

										 </td>

										 <%} else { %>
											<TR>
												<td class="tableData"></td>
												<td class="tableData"></td>
												<td class="tableData"></td>
												<td class="tableData"></td>
												<td class="tableData"></td>
												<td class="tableData"></td>
									 <%
										if(disbIdFromDatabase!=null)
										{
											name = "disbursementId("+cgpan+"-"+disbAmtsIndex+"-"+disbIdFromDatabase+")";
									 %>
										<html:hidden property = "<%=name%>" name="gmPeriodicInfoForm" value="<%=disbIdFromDatabase%>"/>
									<%
										}

										if(request.getParameter("disbursementAmount("+cgpan+"-"+disbAmtsIndex+")")!=null)
										{
											if(request.getParameter("disbursementAmount("+cgpan+"-"+disbAmtsIndex+")").equals(""))
											{
												disAmtMap.put(cgpan+"-"+disbAmtsIndex,new Double(0));
											}
											else
											{
												double disAmt = Double.parseDouble(request.getParameter("disbursementAmount("+cgpan+"-"+disbAmtsIndex+")"));

												disAmtMap.put(cgpan+"-"+disbAmtsIndex,new Double(disAmt));

											}
										}
										else{

											disAmtMap.put(cgpan+"-"+disbAmtsIndex,dAmount);
										}


									%>

												<td class="tableData" valign="top" align="center"><% name = "disbursementAmount("+cgpan+"-"+disbAmtsIndex+")";%> <html:text property = "<%=name%>" size="10" name="gmPeriodicInfoForm" onblur= "javascript:displayDbrTotal()" onkeypress="return decimalOnly(this, event,13)" onkeyup="isValidDecimal(this)" maxlength="6"/>
												</td>

										 <%
										if(request.getParameter("disbursementDate("+cgpan+"-"+disbAmtsIndex+")")!=null)
										{
											 if(request.getParameter("disbursementDate("+cgpan+"-"+disbAmtsIndex+")").equals(""))
											{
												 disDateMap.put(cgpan+"-"+disbAmtsIndex,null);
											}
											else
											{
												disDateMap.put(cgpan+"-"+disbAmtsIndex,request.getParameter("disbursementDate("+cgpan+"-"+disbAmtsIndex+")"));

											}
										}
										else{

											disDateMap.put(cgpan+"-"+disbAmtsIndex,disbDate);
										}
											 
										 %>


												<td class="tableData" valign="top" align="center"><% name = "disbursementDate("+cgpan+"-"+disbAmtsIndex+")";%> <html:text property = "<%=name%>" size="10" name="gmPeriodicInfoForm" maxlength="10"/>
												</td>

												<td class="tableData"  valign="top" align="center">
											<%
											name="finalDisbursement("+cgpan+"-"+disbAmtsIndex+")";

												java.util.Map map=(java.util.Map)disbursementMap;


												
										if(request.getParameter("finalDisbursement("+cgpan+"-"+disbAmtsIndex+")")!=null)
										{
											map.put(cgpan+"-"+disbAmtsIndex,(String)request.getParameter("finalDisbursement("+cgpan+"-"+disbAmtsIndex+")"));
										}
										else if(finaldisbursement!=null && finaldisbursement.equals("Y"))
										 	{
												

												map.put(cgpan+"-"+disbAmtsIndex,"Y");
													//System.out.println("inside");
											}
											else
											 {
											map.put(cgpan+"-"+disbAmtsIndex,"N");
											 }
											//System.out.println("disbAmtsIndex "+disbAmtsIndex+", disbAmtSize "+disbAmtSize);
											if(disbAmtSize-1 == disbAmtsIndex)
											{
												finalFlag=false;
											}
											%>
												<html:checkbox property="<%=name%>" disabled="<%=finalFlag%>" alt="final" name="gmPeriodicInfoForm" value="Y" onclick = "javascript:validateFinalDisbursement(this)"/>
												</td>

												<td class="tableData" id="<%=disbId%>"></td>

										   </TR>
										<% count = disbAmtsIndex;
										  } %>

									<%  }	%> <!-- end of inner for loop -->
									<% count++ ; %>

									<% if (disbAmtSize == 0)
									   {
									%>
										<td class="tableData" valign="top" align="center"><% name = "disbursementAmount("+cgpan+"-"+disbAmtSize+")";%> <html:text property = "<%=name%>" size="10" name="gmPeriodicInfoForm"  onblur= "javascript:displayDbrTotal()" onkeypress="return decimalOnly(this, event,13)" onkeyup="isValidDecimal(this)" maxlength="16"/>
										</td>

										<td class="tableData" valign="top" align="center"><% name = "disbursementDate("+cgpan+"-"+disbAmtSize+")";%> <html:text property = "<%=name%>" size="10" name="gmPeriodicInfoForm"  maxlength="10"/>
										</td>

										<td class="tableData"  valign="top" align="center"><%name="finalDisbursement("+cgpan+"-"+disbAmtSize+")";%>
										<html:checkbox property="<%=name%>"  alt="final" name="gmPeriodicInfoForm" value="Y" onclick = "javascript:validateFinalDisbursement(this)"/>
										</td>

										<td class="tableData" id="<%=disbId%>"></td>

								   </TR>
								<%  } %>

									<TR>
										<td class="tableData"></td>
										<td class="tableData"></td>
										<td class="tableData"></td>
										<td class="tableData"></td>
										<td class="tableData"></td>
										<td class="tableData"></td>

										<td class="tableData" valign="top" align="center" ><%name = "disbursementAmount("+cgpan+"-"+count+")";%> <html:text property="<%=name%>" size="10" name="gmPeriodicInfoForm"  onblur= "javascript:displayDbrTotal()" onkeypress="return decimalOnly(this, event,13)" onkeyup="isValidDecimal(this)" maxlength="16"/>
										</td>

										<td class="tableData" valign="top" align="center">
										<% name = "disbursementDate("+cgpan+"-"+count+")";%> <html:text property = "<%=name%>" size="10" name="gmPeriodicInfoForm"  maxlength="10"/>
										</td>

										<td align="right" valign="top"class="tableData">
											<%name="finalDisbursement("+cgpan+"-"+count+")";%>
											 <html:checkbox property="<%=name%>"  alt="final" name="gmPeriodicInfoForm" value="Y" onclick = "javascript:validateFinalDisbursement(this)"/>
											</td>
											<td class="tableData"><A href="javascript:submitForm('addMoreDisbursement.do?method=addMoreDisbursement&cgpanFlag=<%=cgpan%>')">Add More</A></td>
										<% } // end of condt cgpan!=null
											++disbursementEntryIndex;
											count =0;   %>
								   </TR>

								<%	}%>		<!-- outer for  -->
								<%}%> <!-- end of condt bid != null  -->
						</TR>
				</logic:iterate>

<html:hidden property = "disbursementEntryIndex" name="gmPeriodicInfoForm" value="<%=String.valueOf(disbursementEntryIndex)%>"/>
<html:hidden property = "noOfDisb" name = "gmPeriodicInfoForm" value = "<%=String.valueOf(disbAmtsIndex)%>"/>
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
											
												<A href="javascript:submitForm('saveDisbursementDetails.do?method=saveDisbursementDetails')">
												<IMG src="images/Save.gif" alt="OK" width="49" height="37" border="0"></A>
												<A href="javascript:document.gmPeriodicInfoForm.reset()">
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
				</body>