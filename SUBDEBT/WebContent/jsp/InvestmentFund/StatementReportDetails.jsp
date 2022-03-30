<%@ page language="java"%>
<%@ page import="java.util.ArrayList"%>
<%@page import ="java.text.SimpleDateFormat"%>
<%@ page import="java.text.DecimalFormat"%>
<%@ taglib uri="/WEB-INF/TLD/struts-html.tld" prefix="html" %>
<%@ taglib uri="/WEB-INF/TLD/struts-bean.tld" prefix="bean" %>
<%@ taglib uri="/WEB-INF/TLD/struts-logic.tld" prefix="logic" %>
<%@ include file="/jsp/SetMenuInfo.jsp" %>
<% session.setAttribute("CurrentPage","statementReportDetails.do?method=statementReportDetails");%>
<%SimpleDateFormat dateFormat=new SimpleDateFormat("dd/MM/yyyy");%>

<TABLE width="725" border="0" cellpadding="0" cellspacing="0">
	<html:errors />
	<html:form action="statementReportInput.do?method=statementReportInput" method="POST" enctype="multipart/form-data">
		<TR> 
			<TD width="20" align="right" valign="bottom"><IMG src="images/TableLeftTop.gif" width="20" height="31"></TD>
			<TD background="images/TableBackground1.gif"><IMG src="images/InvestmentManagementHeading.gif" width="121" height="25"></TD>
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
									<TD colspan="12"> 
										<TABLE width="100%" border="0" cellpadding="0" cellspacing="0">
											<TR>
												<TD width="35%" class="Heading"><bean:message key="statementReportDetailsHeader" /></TD>
												<TD><IMG src="images/TriangleSubhead.gif" width="19" height="19"></TD>
											</TR>
											<TR>
												<TD colspan="3" class="Heading"><IMG src="images/Clear.gif" width="5" height="5"></TD>
											</TR>

										</TABLE>
									</TD>

									<TR>
									<TD width="10%" align="left" valign="top" class="ColumnBackground">
										<bean:message key="accountNumber" />
									</TD>
									<TD width="10%" align="left" valign="top" class="ColumnBackground">
										<bean:message key="openingBalanceRs"/>
									</TD>
									<TD width="10%" align="left" valign="top" class="ColumnBackground">
										<bean:message key="closingBalanceRs"/>
									</TD>
									<TD width="10%" align="left" valign="top" class="ColumnBackground">
										<bean:message key="creditPending"/>
									</TD>
									<TD width="10%" align="left" valign="top" class="ColumnBackground">
										<bean:message key="transactionFrom"/>
									</TD>
									<TD width="10%" align="left" valign="top" class="ColumnBackground">
										<bean:message key="transactionNature"/>
									</TD>
									<TD width="10%" align="left" valign="top" class="ColumnBackground">
										<bean:message key="transactionDate"/>
									</TD>
									<TD width="10%" align="left" valign="top" class="ColumnBackground">
										<bean:message key="amountRs"/> 
									</TD>
									<TD width="10%" align="left" valign="top" class="ColumnBackground">
										<bean:message key="remarks"/>
									</TD>
									</TR>	
									<%
									DecimalFormat df= new DecimalFormat("######################.##");
									df.setDecimalSeparatorAlwaysShown(false);
									%>

									<tr>
									<%int i=0;%>
									<logic:iterate name="ifForm"  property="statementDetails"  id="object">
									<%
									      com.cgtsi.investmentfund.BankStatement pReport =  (com.cgtsi.investmentfund.BankStatement)object;

										  String openBal = df.format(pReport.getOpeningBalance());
										  String closeBal = df.format(pReport.getClosingBalance());
										  String creditPending = df.format(pReport.getCreditPending());
										  String amt = df.format(pReport.getBstAmount());
									%>
									
									<%if(i==0)
									{%>
											<TR>
											<TD width="10%" align="left" valign="top" class="ColumnBackground1">						
											<%=pReport.getAccountNumber()%>
											</TD>
											<TD width="10%" align="left" valign="top" class="ColumnBackground1">						
											<%=openBal%>
											</TD>
											<TD width="10%" align="left" valign="top" class="ColumnBackground1">						
											<%=closeBal%>
											</TD>
											<TD width="10%" align="left" valign="top" class="ColumnBackground1">						
											<%=creditPending%>
											</TD>
											<TD width="10%" align="left" valign="top" class="ColumnBackground1">						
											<%=pReport.getTransactionFrom()%>   
											</TD>
											<TD width="10%" align="left" valign="top" class="ColumnBackground1">						
											<%=pReport.getTransactionNature()%>
											</TD>
											<TD width="10%" align="left" valign="top" class="ColumnBackground1">	
											<%   java.util.Date utilDate=pReport.getTransactionDate();
													String formatedDate=dateFormat.format(utilDate);
											%>
											<%=formatedDate%>
											</TD>
											<TD width="10%" align="left" valign="top" class="ColumnBackground1">						
											<%=amt%>
											</TD>
											<TD width="10%" align="left" valign="top" class="ColumnBackground1">						
											<%String remark=pReport.getRemarks();
											if((remark == null) || (remark.equalsIgnoreCase("null")))
											{
											%>
											<%=""%>
											<%
											}
											else
											{
											%>
											<%=remark%>
											<%
											}
											%>
											</TD>
											</TR>
										<%}
											else {%>

											<TR>
											<TD width="10%" align="left" valign="top" class="ColumnBackground1">						

											</TD>
											<TD width="10%" align="left" valign="top" class="ColumnBackground1">						

											</TD>
											<TD width="10%" align="left" valign="top" class="ColumnBackground1">						

											</TD>
											<TD width="10%" align="left" valign="top" class="ColumnBackground1">						

											</TD>
											<TD width="10%" align="left" valign="top" class="ColumnBackground1">						
											<%=pReport.getTransactionFrom()%>   
											</TD>
											<TD width="10%" align="left" valign="top" class="ColumnBackground1">						
											<%=pReport.getTransactionNature()%>
											</TD>
											<TD width="10%" align="left" valign="top" class="ColumnBackground1">	
											<%   java.util.Date utilDate=pReport.getTransactionDate();
													String formatedDate=dateFormat.format(utilDate);
											%>
											<%=formatedDate%>
											</TD>
											<TD width="10%" align="left" valign="top" class="ColumnBackground1">						
											<%=amt%>
											</TD>
											<TD width="10%" align="left" valign="top" class="ColumnBackground1">						
											<%String remark=pReport.getRemarks();
											if((remark == null) || (remark.equalsIgnoreCase("null")))
											{
											%>
											<%=""%>
											<%
											}
											else
											{
											%>
											<%=remark%>
											<%
											}
											%>
											</TD>
											</TR>


											<%}%>
											<%i++;%>
											</logic:iterate>
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
									<IMG src="images/Back.gif" alt="Back" width="49" height="37" border="0"></A>
								
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

