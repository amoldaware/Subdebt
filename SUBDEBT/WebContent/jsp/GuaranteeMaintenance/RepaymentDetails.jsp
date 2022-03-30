<%@ page language="java"%>
<%@ taglib uri="/WEB-INF/TLD/struts-html.tld" prefix="html" %>
<%@ taglib uri="/WEB-INF/TLD/struts-bean.tld" prefix="bean" %>
<%@ taglib uri="/WEB-INF/TLD/struts-logic.tld" prefix="logic" %>

<%@ page import="java.util.ArrayList"%>
<%@ page import="com.cgtsi.guaranteemaintenance.PeriodicInfo"%>
<%@ page import="com.cgtsi.guaranteemaintenance.RepaymentAmount"%>
<%@ page import="com.cgtsi.guaranteemaintenance.Repayment"%>
<%@ page import="java.text.SimpleDateFormat"%>
<%@ page import="java.util.Date"%>

<%@ include file="/jsp/SetMenuInfo.jsp" %>



<% session.setAttribute("CurrentPage","showRepaymentDetails.do?method=showRepaymentDetails");
String tableId1=null;
String tableId2=null;
String totalId = null;
int repaymentIndex =0;
int repayAmtsIndex = 0;
String name = null;
String repayDate=null;
SimpleDateFormat dateFormat=new SimpleDateFormat("dd/MM/yyyy");
String rpAmt=null;
int count = 0;
int flag = 0;
int newRowCount = 0;

if(request.getAttribute("idFlag")!=null)
{
	flag=Integer.parseInt((String)request.getAttribute("idFlag"));
}

double amount = 0;
String repayId = null;%>


<TABLE width="725" border="0" cellpadding="0" cellspacing="0">
	<html:errors />
	<body onload = "javascript:displayRpmtTotal()">
	<html:form action="saveRepaymentDetails.do?method=saveRepaymentDetails" method="POST" enctype="multipart/form-data">
	<html:hidden property="rowCount" name ="gmPeriodicInfoForm" value="0" />
	<TR> 
		<TD width="20" align="right" valign="bottom"><IMG src="images/TableLeftTop.gif" width="20" 			height="31">
		</TD>
		<TD background="images/TableBackground1.gif"><IMG src="images/GuaranteeMaintenanceHeading.gif">
		</TD>
		<TD width="20" align="left" valign="bottom"><IMG src="images/TableRightTop.gif" width="23" height="31">
		</TD>
	</TR>
	<TR>
		<TD width="20" background="images/TableVerticalLeftBG.gif">
		</TD>
		<TD>
		<DIV align="right">			
				<A HREF="javascript:submitForm('helpRepaymentDetails.do?method=helpRepaymentDetails')">
			    HELP</A>
			</DIV>
			<TABLE width="100%" border="0" align="left" cellpadding="0" cellspacing="0">
				<TR>
					<TD>
						<TABLE width="100%" border="0" cellspacing="1" cellpadding="1" id="addRow">
							<TR>
								 <TD colspan="6"> 
										<TABLE width="100%" border="0" cellpadding="0" cellspacing="0">
										<tr>
										  <td width="35%" class="Heading"><bean:message key="repaymentDetailsHeader"/><bean:write name = "gmPeriodicInfoForm" property = "borrowerId"/></td>
										  <td  align="left" valign="bottom"><img src="images/TriangleSubhead.gif" width="19" height="19"></td>
										  <td align="right"> <div align="right"> </div></td>
										</tr>
										<tr>
										  <td colspan="6" class="Heading"><img src="images/Clear.gif" width="5" height="5">	
										   <A href="javascript:submitForm('showOutstandingDetailsLink.do?method=showOutstandingDetailsLink')">Outstanding Details</A>|	   
										   <A href="javascript:submitForm('showDisbursementDetailsLink.do?method=showDisbursementDetailsLink')">Disbursement Details</A> |
											  
										
										<!--	<A href="javascript:submitForm('showNPADetailsLink.do?method=showNPADetailsLink')">NPA Details</A>| -->
											<A href="javascript:submitForm('showRecoveryDetailsLink.do?method=showRecoveryDetailsLink')">Recovery Details</A>
											  </td>
										</tr>
									</TABLE>
								</TD>
							</TR>
							<TR> 
								<TD colspan="6">
									<TABLE width="100%" border="0" cellspacing="1" cellpadding="0">
										<TR> 
											<TD align="center" valign="middle" class="HeadingBg" 			height="59" align="center"><bean:message key="srNo"/>
											</TD>
											<TD class="HeadingBg" height="59" 								align="center"><bean:message key="BorrowerID"/>
											</TD>
						  
											<TD  class="HeadingBg" height="59" 								align="center"><bean:message key = "borrowerName"/>
											</TD>
						 
											<TD  class="HeadingBg" height="59" 								align="center"><bean:message key="cgpanNumber"/>
											</TD>
						  
											<TD class="HeadingBg" height="59" 								align="center"><bean:message key="scheme"/>
											</TD>
						  
											<TD class="HeadingBg"  height="59" 								align="center"><font color="#FF0000" size="2">*</font><bean:message key = 							"repaymentAmountinRs"/>
											</TD>
						  
											<TD class="HeadingBg" height="59" 								align="center"><font color="#FF0000" size="2">*</font><bean:message key="date"/>
											</TD>
						  
											<TD  class="HeadingBg" height="59" 								align="center"><bean:message key="totalRepaymentAmount"/>
											</TD>
										</TR>
                
				<% int i=0; int repaymentEntryIndex = 0; %>
				<TR align="center"> 
				<logic:iterate id="object" name="gmPeriodicInfoForm" property = "repayPeriodicInfoDetails">

					<bean:define type="java.util.Map" id="repAmtMap" name="gmPeriodicInfoForm" property="repaymentAmount"/>

					<bean:define type="java.util.Map" id="repDateMap" name="gmPeriodicInfoForm" property="repaymentDate"/>

				<%
					PeriodicInfo periodicInfo = (PeriodicInfo)object; 
					String cgpan = "";
					String scheme = "";
					String borrowerId = periodicInfo.getBorrowerId();
					String borrowerName = periodicInfo.getBorrowerName();%>
					<%if (borrowerId != null) { %>

						<TD class="tableData" align="center"><%=++i%>
						</TD>
				  
						<TD class="tableData">&nbsp;<A
							href="javascript:submitForm('showBorrowerDetailsLink.do?method=showBorrowerDetailsLink&formFlag=periodic&bidLink=<%=borrowerId%>')"><%=borrowerId%></A>
						</TD>
						<TD class="tableData">&nbsp;<%=borrowerName%>
						</TD>
					
						<%
						ArrayList repayments = (ArrayList)periodicInfo.getRepaymentDetails(); 
										
						int repaymentsSize = repayments.size();

						Repayment repayment = null;
										
						for(repaymentIndex =0; repaymentIndex < repaymentsSize; ++repaymentIndex) {
							
							repayment = (Repayment)repayments.get(repaymentIndex);
							cgpan = repayment.getCgpan() ;
							scheme = repayment.getScheme() ; %>

						 <%	if(cgpan!=null) { %>
							<%name = "cgpans(key-"+repaymentIndex+")";%>
							<html:hidden property = "<%=name%>" name="gmPeriodicInfoForm" value="<%=cgpan%>"/>

						<%	 if(repaymentIndex == 0) { %>
							

							<TD class="tableData" valign="top" align="center">&nbsp;<A
								href="javascript:submitForm('showCgpanDetailsLink.do?method=showCgpanDetailsLink&cgpanDetail=<%=cgpan%>')"><%=cgpan%></a>
							</TD>
									  
							  <TD class="tableData" valign="top" align="center"><%=scheme%>
							  </TD>						
							  
							  <%  } else { %>
								<TD class="tableData"></TD>
								<TD class="tableData"></TD>
								<TD class="tableData"></TD>
								
							  <TD class="tableData" valign="top" align="center">&nbsp;<A
								href="javascript:submitForm('showCgpanDetailsLink.do?method=showCgpanDetailsLink&cgpanDetail=<%=cgpan%>')"><%=cgpan%></a>
							  </TD>
									  
							  <TD class="tableData" valign="top" align="center"><%=scheme%>
							  </TD>						
							  
							  <% }   
							 ArrayList repayAmounts = (ArrayList)repayment.getRepaymentAmounts();	
							  int repayAmtSize = repayAmounts.size();
							  														 
							  for(repayAmtsIndex = 0; repayAmtsIndex < repayAmtSize; ++repayAmtsIndex) {
								
								RepaymentAmount  repaymentAmount = (RepaymentAmount)repayAmounts.get(repayAmtsIndex)	;
																
								amount = repaymentAmount.getRepaymentAmount();
								rpAmt = (new Double(amount)).toString();

								repayId = repaymentAmount.getRepayId();
																
								repayDate = dateFormat.format(repaymentAmount.getRepaymentDate());
								totalId = "totalAmt"+repaymentIndex ;
								if (repayAmtsIndex == 0) { %>

								<%
									if(repayId!=null)
									{
									name = "repaymentId("+cgpan+"-"+repayAmtsIndex+"-"+repayId+")";
									//System.out.println("Entered not null for cgpan"+cgpan+","+repayId);
								%>
								<html:hidden property = "<%=name%>" name="gmPeriodicInfoForm" value="<%=repayId%>"/>
									<%
									}

										if(request.getParameter("repaymentAmount("+cgpan+"-"+repayAmtsIndex+")")!=null && !request.getParameter("repaymentAmount("+cgpan+"-"+repayAmtsIndex+")").equals(""))
										{
											double repAmt = Double.parseDouble(request.getParameter("repaymentAmount("+cgpan+"-"+repayAmtsIndex+")"));

											repAmtMap.put(cgpan+"-"+repayAmtsIndex,new Double(repAmt));
										}
										else{

											repAmtMap.put(cgpan+"-"+repayAmtsIndex,rpAmt);
										}

									%>

								 <TD class="tableData" valign="top" align="center"><% name = "repaymentAmount("+cgpan+"-"+repayAmtsIndex+")";%> <html:text property = "<%=name%>" size="10" name="gmPeriodicInfoForm"  onblur= "javascript:displayRpmtTotal()" onkeypress="return decimalOnly(this, event,13)" onkeyup="isValidDecimal(this)" maxlength="16"/>
								 </TD>						
							  
								 <TD class="tableData" valign="top" 								align="center"><% name = "repaymentDate("+cgpan+"-"+repayAmtsIndex+")";								 
								 %> <html:text property = "<%=name%>" size="10" name="gmPeriodicInfoForm" value="<%=repayDate%>" maxlength="10"/>
								 </TD>						
								
								 <TD class="tableData" valign="top" align="center" id = "<%=totalId%>"> 
										
								 </TD>	
							
								 <%} else { %>
									<TR>	
										<TD class="tableData"></TD>
										<TD class="tableData"></TD>
										<TD class="tableData"></TD>
										<TD class="tableData"></TD>
										<TD class="tableData"></TD>
										<%
										if(repayId!=null)
										{
											name = "repaymentId("+cgpan+"-"+repayAmtsIndex+"-"+repayId+")";
											%>
										<html:hidden property = "<%=name%>" name="gmPeriodicInfoForm" value="<%=repayId%>"/>
											<%}


											if(request.getParameter("repaymentAmount("+cgpan+"-"+repayAmtsIndex+")")!=null && !request.getParameter("repaymentAmount("+cgpan+"-"+repayAmtsIndex+")").equals(""))
										{
											double repAmt = Double.parseDouble(request.getParameter("repaymentAmount("+cgpan+"-"+repayAmtsIndex+")"));

											repAmtMap.put(cgpan+"-"+repayAmtsIndex,new Double(repAmt));
										}
										else{

											repAmtMap.put(cgpan+"-"+repayAmtsIndex,rpAmt);
										}
											
											%>

										
										<TD class="tableData" valign="top" align="center"><% name = "repaymentAmount("+cgpan+"-"+repayAmtsIndex+")";%> <html:text property = "<%=name%>" size="10" name="gmPeriodicInfoForm" onblur= "javascript:displayRpmtTotal()" onkeypress="return decimalOnly(this, event,13)" onkeyup="isValidDecimal(this)" maxlength="16"/>
										</TD>						
							  
										<TD class="tableData" valign="top" align="center"><% name = "repaymentDate("+cgpan+"-"+repayAmtsIndex+")";%> <html:text property = "<%=name%>" size="10" name="gmPeriodicInfoForm" value="<%=repayDate%>" maxlength="10"/>
										</TD>		
										<%totalId = "totalAmt"+repaymentIndex ;%>
										<TD class="tableData" id = "<%=totalId%>"></TD>
					
										<!--   </TR>-->
										<% count = repayAmtsIndex;
								} // end of else loop. 
							  
							  }	%> <!-- end of inner for loop -->					
							<%count++;%>
							
							<% if (repayAmtSize == 0)
							   {
							%>
								<TD class="tableData" valign="top" align="center"><% name = "repaymentAmount("+cgpan+"-"+repayAmtSize+")";%> <html:text property = "<%=name%>" size="10" name="gmPeriodicInfoForm" onblur= "javascript:displayRpmtTotal()" onkeypress="return decimalOnly(this, event,13)" onkeyup="isValidDecimal(this)" maxlength="16"/>
								</TD>						
							  
								<TD class="tableData" valign="top" align="center"><% name = "repaymentDate("+cgpan+"-"+repayAmtSize+")";%> <html:text property = "<%=name%>" size="10" name="gmPeriodicInfoForm" maxlength="10"/>
								</TD>		
								
								<%totalId = "totalAmt"+repaymentIndex ;%>
								<TD class="tableData" id = "<%=totalId%>"></TD>
						
								</TR>
				
							<%  } %>
							<TR>
								<TD class="tableData"></TD>
								<TD class="tableData"></TD>
								<TD class="tableData"></TD>
								<TD class="tableData"></TD>
								<TD class="tableData"></TD>
								<% newRowCount = count;%>
								<%--
								<%name = "repaymentId("+cgpan+ "-"+ count+")";%>
								<html:hidden property = "<%=name%>" name="gmPeriodicInfoForm" value="<%=cgpan%>"/>
									--%>

								<TD class="tableData" valign="top" align="center"> <% name = "repaymentAmount("+cgpan+"-"+count+")";%> <html:text property = "<%=name%>" size="10" name="gmPeriodicInfoForm" onblur= "javascript:displayRpmtTotal()" onkeypress="return decimalOnly(this, event,13)" onkeyup="isValidDecimal(this)" maxlength="16"/>
								</TD>	
									
								<TD class="tableData" valign="top" align="center"> <% name = "repaymentDate("+cgpan+"-"+count+")";%> <html:text property = "<%=name%>" size="10" name="gmPeriodicInfoForm" maxlength="10"/>
								</TD>
									
								<!--  <TD class="tableData" height="27"><input type="button" name="btnAddRow" value="AddRow " onclick="javascript:AddActivities('<%=count%>');"></TD>-->
								<TD class="tableData" height="27"><A href="javascript:submitForm('addMoreRepayment.do?method=addMoreRepayment&cgpanFlag=<%=cgpan%>')">Add More</A>									<% ++newRowCount ;%>							
						   </TR>
						    	<% tableId1 = "add1col("+repaymentEntryIndex+")" ;
							   tableId2 = "add2col("+repaymentEntryIndex+")" ;%>
						   <tr>
							  <td class="tableData" height="27"></td>
							  <td class="tableData" height="27"></td>
							  <td class="tableData" height="27"></td>
							  <td class="tableData" height="27"></td>
							  <td class="tableData" height="27"></td>
							  <td class = "tableData">
								<table id="<%=tableId1%>" >
									<tr>
										
									</tr> 
								</table>			
							  </td>
							  <td class="tableData">
								<table id="<%=tableId2%>" >
									<tr>
										
									</tr>  
								</table>			
							  </td>
							  <td class="tableData" height="27"></td>
						   </tr>         
											
						
						<% } // end of condt cgpan!=null 
							++repaymentEntryIndex;
							count =0;
						}%>		<!-- end of outer for  -->							
						<%}%> <!-- end of condt bid != null  -->							
						</TR>			 
				</logic:iterate>
<html:hidden property = "repaymentEntryIndex" name="gmPeriodicInfoForm" value="<%=String.valueOf(repaymentEntryIndex)%>"/>				
<html:hidden property = "rpIndex" name="gmPeriodicInfoForm" value="<%=String.valueOf(repaymentIndex)%>"/>				

							</TABLE>
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
								
									<A href="javascript:submitForm('saveRepaymentDetails.do?method=saveRepaymentDetails')">
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
	</html:form>
	</body>
</TABLE>