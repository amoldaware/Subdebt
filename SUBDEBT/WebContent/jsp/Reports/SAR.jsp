<%@ page language="java"%>
<%@ page import="java.util.ArrayList"%>

<%@ taglib uri="/WEB-INF/TLD/struts-html.tld" prefix="html" %>
<%@ taglib uri="/WEB-INF/TLD/struts-bean.tld" prefix="bean" %>
<%@ taglib uri="/WEB-INF/TLD/struts-logic.tld" prefix="logic" %>
<%@ include file="/jsp/SetMenuInfo.jsp" %>
<%@page import ="java.text.DecimalFormat"%>
<%DecimalFormat decimalFormat = new DecimalFormat("##########.##");%>
<% session.setAttribute("CurrentPage","sanctionedApplicationReport.do?method = sanctionedApplicationReport");%>

<TABLE width="725" border="0" cellpadding="0" cellspacing="0">
	<html:errors />
	<html:form action="sanctionedApplicationReportInput.do?method=sanctionedApplicationReportInput" method="POST" enctype="multipart/form-data">
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
												<TD width="29%" class="Heading"><bean:message key="sanctionedApplicationReportHeader" /></TD>
												<TD><IMG src="images/TriangleSubhead.gif" width="19" height="19"></TD>
											</TR>
											<TR>
												<TD colspan="3" class="Heading"><IMG src="images/Clear.gif" width="5" height="5"></TD>
											</TR>

										</TABLE>
									</TD>

									<TR>
									<TD width="10%" align="left" valign="top" class="ColumnBackground">
										<bean:message key="memberId" />
									</TD>
									<TD width="10%" align="left" valign="top" class="ColumnBackground">
										<bean:message key="numberOfAppns" />
									</TD>
									<TD width="10%" align="left" valign="top" class="ColumnBackground">
										<bean:message key="sanctionAmount" />
									</TD>
									<TD width="10%" align="left" valign="top" class="ColumnBackground">
										<bean:message key="guaranteeFeeRs"/>
									</TD>
									<TD width="10%" align="left" valign="top" class="ColumnBackground">
										<bean:message key="guaranteeFeePaid"/>
									</TD>
									<TD width="10%" align="left" valign="top" class="ColumnBackground">
										<bean:message key="outstanding"/>
									</TD>
									</TR>	

									<%	int sum = 0;
										int totalSum = 0;
										double gFee = 0;
										double totalgFee = 0;
										double gFeePaid = 0;
										double totalgFeePaid = 0;
										double outstanding = 0;
										double totalOutstanding = 0;
										double amount = 0;
										double totalAmount = 0;
									%>

									<tr>
									<logic:iterate id="object" name="rsForm" property="sar">
									<%
									     com.cgtsi.reports.SanctionedReport saReport =  (com.cgtsi.reports.SanctionedReport)object;
									%>
									

											<tr>
											<TD width="10%" align="left" valign="top" class="ColumnBackground1">						
											<%=saReport.getMliId()%>				
											</TD>
											<TD width="10%" align="left" valign="top" class="ColumnBackground1">	
											<div align="right">
											  <%sum=saReport.getApplications();
											  totalSum = totalSum + sum;%>		
											  <%=sum%>
											  </div>
											</TD>
											<TD width="10%" align="left" valign="top" class="ColumnBackground1">		
											<div align="right">
											<% amount=saReport.getSanctionedAmount();	
											totalAmount = totalAmount + amount;%>		
											<%=decimalFormat.format(amount)%>
											</div>
											</TD>
											<TD width="10%" align="left" valign="top" class="ColumnBackground1">		
											<div align="right">
											<% gFee=saReport.getGuaranteeFee();
											  totalgFee = totalgFee + gFee;%>		
											<%=decimalFormat.format(gFee)%>
											</div>
											</TD>
											<TD width="10%" align="left" valign="top" class="ColumnBackground1">		
											<div align="right">
											<% gFeePaid=saReport.getGuaranteeFeePaid();
											  totalgFeePaid = totalgFeePaid + gFeePaid;%>		
											<%=decimalFormat.format(gFeePaid)%>
											</div>
											</TD>
											<TD width="10%" align="left" valign="top" class="ColumnBackground1">	
											<div align="right">
											  <% outstanding=saReport.getOutstanding();	
											  totalOutstanding = totalOutstanding + outstanding;%>		
											  <%=decimalFormat.format(outstanding)%>
											  </div>
											</TD>
											</TR>
									</logic:iterate>


											<tr>
											<TD width="10%" align="left" valign="top" class="ColumnBackground">						
											Total
											</TD>
											<TD width="10%" align="left" valign="top" class="ColumnBackground">	
											<div align="right">
											  <%=totalSum%>
											  </div>
											</TD>
											<TD width="10%" align="left" valign="top" class="ColumnBackground">		
											<div align="right">
											<%=decimalFormat.format(totalAmount)%>
											</div>
											</TD>
											<TD width="10%" align="left" valign="top" class="ColumnBackground">		
											<div align="right">
											<%=decimalFormat.format(totalgFee)%>
											</div>
											</TD>
											<TD width="10%" align="left" valign="top" class="ColumnBackground">		
											<div align="right">
											<%=decimalFormat.format(totalgFeePaid)%>
											</div>
											</TD>
											<TD width="10%" align="left" valign="top" class="ColumnBackground">	
											<div align="right">
											  <%=decimalFormat.format(totalOutstanding)%>
											  </div>
											</TD>
											</TR>

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
								<A href="javascript:submitForm('sanctionedApplicationReportInput.do?method=sanctionedApplicationReportInput')">
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

