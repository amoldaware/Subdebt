<!-- modified by sudeep.dhiman@pathinfotech.com 24-10-2006-->

<%@ page language="java"  import="org.apache.struts.action.DynaActionForm" 
import = "org.apache.struts.action.ActionForm"%>
<%@ taglib uri="/WEB-INF/TLD/struts-html.tld" prefix="html" %>
<%@ taglib uri="/WEB-INF/TLD/struts-bean.tld" prefix="bean" %>
<%@ taglib uri="/WEB-INF/TLD/struts-logic.tld" prefix="logic" %>
<%@ include file="/jsp/SetMenuInfo.jsp" %>
<%@page import ="java.text.DecimalFormat"%>
<%DecimalFormat decimalFormat = new DecimalFormat("##########0.00");%>
<% session.setAttribute("CurrentPage","stateWiseReportDetailsAll.do?method=stateWiseReportDetailsAll");%>
<%@page import = "org.apache.struts.action.DynaActionForm"%>
<% DynaActionForm dynaForm = (DynaActionForm)session.getAttribute("rsForm");
   String value = (String)dynaForm.get("checkValue");%>

<TABLE width="725" border="0" cellpadding="0" cellspacing="0">
	<html:errors />
	<html:form action="stateWiseReportDetailsAll.do?method=stateWiseReportDetailsAll" method="POST" enctype="multipart/form-data">
		<TR> 
			<TD width="20" align="right" valign="bottom"><IMG src="images/TableLeftTop.gif" width="20" height="31"></TD>
			<TD background="images/TableBackground1.gif"><IMG src="images/ReportsHeading.gif" width="121" height="25"></TD>
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
									<TD colspan="6"> 
										<TABLE width="100%" border="0" cellpadding="0" cellspacing="0">
                      <tr>
                          <td colspan="6" class="Heading1" align="center"><u><!-- <bean:message key="reportHeader"/> --> Credit Guarantee Fund Trust for Micro And Small Enterprises </u></td>
                      </tr>
                      <tr> <td colspan="6">&nbsp;</td></tr>
											<TR>
												<TD width="18%" class="Heading"><bean:message key="stateHeader" /></TD>
												<td class="Heading" width="52%"><bean:write name="radioValue"/>&nbsp;<bean:message key="from"/> <bean:write  name="rsForm" property="dateOfTheDocument10"/>&nbsp;<bean:message key="to"/> <bean:write  name="rsForm" property="dateOfTheDocument11"/></td>
                        <TD><IMG src="images/TriangleSubhead.gif" width="19" height="19"></TD>
											</TR>
											<TR>
												<TD colspan="3" class="Heading"><IMG src="images/Clear.gif" width="5" height="5"></TD>
											</TR>
											</TABLE>
									</TD>
								</TR>


								<TR align="left" valign="top">
									<td align="left" valign="top" class="ColumnBackground"><bean:message key="sNo"/></td>
                  <TD align="left" valign="top" class="ColumnBackground">
										&nbsp;<bean:message key="stateName"/>
									</TD>
									<TD align="left" class="ColumnBackground"> 
									<bean:message key="proposals"/>
									</TD>
									<% if (value.equals("yes")){%>	
										<TD align="left" class="ColumnBackground"> 
										<bean:message key="amountApprovedLakhs"/>
										</TD>
									<%}else{%>
										<TD align="left" class="ColumnBackground"> 
										<bean:message key="amountIssuedLakhs"/>
										</TD>
									<%}//end of else
                    int count       = 0;
										int totalCount  = 0;
										double sum      = 0.00;
										double totalSum = 0.00;
                    int count1       = 0;
										int totalCount1  = 0;
										double sum1      = 0.00;
										double totalSum1 = 0.00;
									%>
                  <TD align="left" class="ColumnBackground"> 
									Cumulative <bean:message key="proposals"/>
									</TD>
                  <% if (value.equals("yes")){%>	
										<TD align="left" class="ColumnBackground"> 
									Cumulative	<bean:message key="amountApprovedLakhs"/>
										</TD>
                    
									<%}	else{%>
										<TD align="left" class="ColumnBackground"> 
								Cumulative <bean:message key="amountIssuedLakhs"/>
										</TD>
									<%}	%>
									</TR>
									<tr>
									<logic:iterate id="object" name="rsForm" property="mliWiseReport" indexId="id">

									<% com.cgtsi.reports.GeneralReport dReport =  (com.cgtsi.reports.GeneralReport)object;%>

									<TR align="left" valign="top">
									<td align="left" valign="top" class="ColumnBackground1"><%=Integer.parseInt(id+"")+1%></td>
                  <TD align="left" valign="top" class="ColumnBackground1">

									<%	String state  = dReport.getType();
											String url = "stateApplicationDetails1.do?method=stateApplicationDetails1&State="+state;%>
											<% int i = state.indexOf("&");
											if(i == -1){
                      %>
											<html:link href="<%=url%>"><%= state%></html:link>
											<%}else{
											String newState = state.replace('&','$');
											String newUrl = "stateApplicationDetails1.do?method=stateApplicationDetails1&State="+newState;
											%>
											<html:link href="<%=newUrl%>"><%= state%></html:link>
											<%}//end of else%>
										  |
											<%	String state1  = dReport.getType();
											String url1 = "districtApplicationDetails.do?method=districtApplicationDetails&District="+state1;%>
											<% int j = state.indexOf("&");
											if(j == -1){%>
											<html:link href="<%=url1%>">District Wise</html:link>
											<%}	else{
											String newState1 = state.replace('&','$');
											String newUrl1 = "districtApplicationDetails.do?method=districtApplicationDetails&District="+newState1;%>
											<html:link href="<%=newUrl1%>">District Wise</html:link>
											<%}	%>

									</TD>
									<TD align="left" class="ColumnBackground1"> 
									<div align="right">
									<%count=dReport.getProposals();%>
									<%totalCount = count + totalCount;%>
									<%=dReport.getProposals()%>
									</div>
									</TD>
									<TD align="left" class="ColumnBackground1"> 
									<div align="right">
									<%double amount=dReport.getAmount();
									sum = amount/100000;%>
									<% totalSum = totalSum+Double.parseDouble(decimalFormat.format(sum));%>
									<%=decimalFormat.format(sum)%>
									</div>
									</TD>
                  <TD align="left" class="ColumnBackground1"> 
									<div align="right">
									<%count1=dReport.getCumProposals();%>
									<%totalCount1 = count1 + totalCount1;%>
									<%=dReport.getCumProposals()%>
									</div>
									</TD>
               
									<TD align="left" class="ColumnBackground1"> 
									<div align="right">
									<%double amount1=dReport.getCumAmount();
									sum1 = amount1/100000;%>
									<% totalSum1 = totalSum1+Double.parseDouble(decimalFormat.format(sum1));%>
									<%=decimalFormat.format(sum1)%>
									</div>
									</TD>
									</TR> 
								</logic:iterate>

								<tr>
									<TD align="left" valign="top" colspan="2" class="ColumnBackground">
									Total
									</TD>
									<TD align="left" class="ColumnBackground"> 
									<div align="right">
									<%=totalCount%>
									</div>
									</TD>
									<TD align="left" class="ColumnBackground"> 
									<div align="right">
									<%=decimalFormat.format(totalSum)%>
									</div>
									</TD>
                  <TD align="left" class="ColumnBackground"> 
									<div align="right">
									<%=totalCount1%>
									</div>
									</TD>
                  <TD align="left" class="ColumnBackground"> 
									<div align="right">
									<%=decimalFormat.format(totalSum1)%>
									</div>
									</TD>
								</tr>

					</TABLE>
						</TD>
					</TR>
					<TR >
						<TD height="20" >
							&nbsp;
						</TD>
					</TR>
					<TR>
						<TD align="center" valign="baseline">
							<DIV align="center">
								<A href="javascript:submitForm('stateWiseReportAll.do?method=stateWiseReportAll')">
									<IMG src="images/Back.gif" alt="Back" width="49" height="37" border="0"></A>
								
									<A href="javascript:printpage()">
									<IMG src="images/Print.gif" alt="Print" width="49" height="37" border="0"></A>								
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

