<%@ page language="java"%>
<%@ taglib uri="/WEB-INF/TLD/struts-html.tld" prefix="html" %>
<%@ taglib uri="/WEB-INF/TLD/struts-bean.tld" prefix="bean" %>
<%@ taglib uri="/WEB-INF/TLD/struts-logic.tld" prefix="logic" %>
<%@ include file="/jsp/SetMenuInfo.jsp" %>
<%@page import ="java.text.DecimalFormat"%>
<%DecimalFormat decimalFormat = new DecimalFormat("#########0.00");%>
<% session.setAttribute("CurrentPage","statesWiseReportNew.do?method=statesWiseReportNew");%>
<%@page import = "org.apache.struts.action.DynaActionForm"%>
<% DynaActionForm dynaForm = (DynaActionForm)session.getAttribute("rsForm");
   String value = (String)dynaForm.get("checkValue");%>


<TABLE width="780" border="0" cellpadding="0" cellspacing="0">
	<html:errors />
	<html:form action="statesWiseReportNew.do?method=statesWiseReportNew" method="POST" enctype="multipart/form-data">
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
									<TD colspan="8"> 
										<TABLE width="100%" border="0" cellpadding="0" cellspacing="0">
											<TR>
												<TD width="31%" class="Heading"><bean:message key="stateHeader" /></TD>
												<TD><IMG src="images/TriangleSubhead.gif" width="19" height="19"></TD>
											</TR>
											<TR>
												<TD colspan="3" class="Heading"><IMG src="images/Clear.gif" width="5" height="5"></TD>
											</TR>
											</TABLE>
									</TD>
								</TR>


								<TR align="left" valign="top">
                  <td width="2" align="left" valign="top" class="ColumnBackground"><bean:message key="sNo"/></td>
                  <TD width="25%" align="left" valign="top" class="ColumnBackground">
										&nbsp;<bean:message key="stateName"/>
									</TD>
									<TD width="5%" align="left" class="ColumnBackground"> 
								Current Year	<bean:message key="proposals"/>
									</TD>
  								<% if (value.equals("yes")){%>	
										<TD width="5%" align="left" class="ColumnBackground"> 
										<bean:message key="amountApprovedLakhs"/>
										</TD>
                    
									<%}	else{%>
										<TD width="5" align="left" class="ColumnBackground"> 
										<bean:message key="amountIssuedLakhs"/>
										</TD>
									<%}
                    int count       = 0;
										int totalCount  = 0;
										double sum      = 0.0;
										double totalSum = 0.0;
									%>
                  <TD align="left" class="ColumnBackground"> 
									Corresponding Previous Period Approvals
									</TD>
                  <% if (value.equals("yes")){%>	
										<TD align="left" class="ColumnBackground"> 
										<bean:message key="amountApprovedLakhs"/>
										</TD>
                    
									<%}	else{%>
										<TD align="left" class="ColumnBackground"> 
									Previous Year	<bean:message key="amountIssuedLakhs"/>
										</TD>
									<%}
                    int prevcount       = 0;
										int prevtotalCount  = 0;
										double prevsum      = 0.0;
										double prevtotalSum = 0.0;
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
									<%}
                    int cumcount       = 0;
										int cumtotalCount  = 0;
										double cumsum      = 0.0;
										double cumtotalSum = 0.0;
									%>
									</TR>
								

									<logic:iterate id="object" name="rsForm" property="statesWiseReport" indexId="id">
									<%
									     com.cgtsi.reports.GeneralReport dReport =  (com.cgtsi.reports.GeneralReport)object;
									%>
			
									<TR align="left" valign="top">
                 <td width="2" align="left" valign="top" class="ColumnBackground1"><%=Integer.parseInt(id+"")+1%></td>
              
									<TD align="left" valign="top" class="ColumnBackground1">
									<%String state =  dReport.getType();  
									String url = "districtApplicationDetails1.do?method=districtApplicationDetails1&District="+state;%>
										<% int i = state.indexOf("&");
											//System.out.println("i:"+i);
											%>
											<%
											if(i == -1)
											{
											%>
											<html:link href = "<%=url%>"><%=state%></html:link>
											<%
											}
											else
											{
											String newState = state.replace('&','$');
											//System.out.println("newState:"+newState);
											String newUrl = "districtApplicationDetails1.do?method=districtApplicationDetails1&District="+newState;
											%>
											<html:link href="<%=newUrl%>"><%= state%></html:link>
											<%
											}
											%>

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
									<% totalSum = totalSum+sum;%>
									<%=decimalFormat.format(sum)%>
									</div>
									</TD>
                  
                  <TD align="left" class="ColumnBackground1"> 
									<div align="right">
									<%prevcount=dReport.getPrevProposals();%>
									<%prevtotalCount = prevcount + prevtotalCount;%>
									<%=dReport.getPrevProposals()%>
									</div>
									</TD>
									<TD align="left" class="ColumnBackground1"> 
									<div align="right">
									<%double prevamount=dReport.getPrevAmount();
									prevsum = prevamount/100000;%>
									<% prevtotalSum = prevtotalSum+prevsum;%>
									<%=decimalFormat.format(prevsum)%>
									</div>
									</TD>
                  <TD align="left" class="ColumnBackground1"> 
									<div align="right">
									<%cumcount=dReport.getCumProposals();%>
									<%cumtotalCount = cumcount + cumtotalCount;%>
									<%=dReport.getCumProposals()%>
									</div>
									</TD>
									<TD align="left" class="ColumnBackground1"> 
									<div align="right">
									<%double cumamount=dReport.getCumAmount();
									cumsum = cumamount/100000;%>
									<% cumtotalSum = cumtotalSum+cumsum;%>
									<%=decimalFormat.format(cumsum)%>
									</div>
									</TD>
									</TR> 
								</logic:iterate>

								<tr>
									<TD align="center" colspan="2" valign="top" class="ColumnBackground">
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
									<%=prevtotalCount%>
									</div>
									</TD>
									<TD align="left" class="ColumnBackground"> 
									<div align="right">
									<%=decimalFormat.format(prevtotalSum)%>
									</div>
									</TD>
                  <TD align="left" class="ColumnBackground"> 
									<div align="right">
									<%=cumtotalCount%>
									</div>
									</TD>
									<TD align="left" class="ColumnBackground"> 
									<div align="right">
									<%=decimalFormat.format(cumtotalSum)%>
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
								
								<A href="javascript:history.back()">
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

