<%@ page language="java"%>
<%@ page import="java.util.ArrayList"%>
<%@page import ="java.text.SimpleDateFormat"%>
<%@page import ="java.text.DecimalFormat"%>

<%@ taglib uri="/WEB-INF/TLD/struts-html.tld" prefix="html" %>
<%@ taglib uri="/WEB-INF/TLD/struts-bean.tld" prefix="bean" %>
<%@ taglib uri="/WEB-INF/TLD/struts-logic.tld" prefix="logic" %>
<%@ include file="/jsp/SetMenuInfo.jsp" %>
<% session.setAttribute("CurrentPage","otsReportDetails.do?method=otsReportDetails");%>
<%SimpleDateFormat dateFormat=new SimpleDateFormat("dd/MM/yyyy");%>
<%DecimalFormat decimalFormat = new DecimalFormat("##########.##");%>

<TABLE width="725" border="0" cellpadding="0" cellspacing="0">
	<html:errors />
	<html:form action="otsReportDetails.do?method=otsReportDetails" method="POST" enctype="multipart/form-data">
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
												<TD width="20%" class="Heading"><bean:message key="otsheader" /></TD>
												<TD><IMG src="images/TriangleSubhead.gif" width="19" height="19"></TD>
											</TR>
											<TR>
												<TD colspan="3" class="Heading"><IMG src="images/Clear.gif" width="5" height="5"></TD>
											</TR>

										</TABLE>
									</TD>

									<TR>
									<TD width="10%" align="left" valign="top" rowspan="3" class="ColumnBackground">
										<bean:message key="cgpan" />
									</TD>
									<TD width="10%" align="left" valign="top" colspan="3" class="ColumnBackground">
									<div align="center">
										<bean:message key="amountSanctioned" />&nbsp; in Rs.
									</div>
									</TD>
									<TD width="10%" align="left" valign="top" rowspan="3" class="ColumnBackground">
										<bean:message key="proposedAmount" />
									</TD>
									<TD width="10%" align="left" valign="top" rowspan="3" class="ColumnBackground">
										<bean:message key="sacrificedAmount"/>
									</TD>
									<TD width="10%" align="left" valign="top" rowspan="3" class="ColumnBackground">
										<bean:message key="pplOS"/>&nbsp; in Rs.
									</TD>
									</TR>	


									<TR>
									<TD width="10%" align="left" valign="top" rowspan="2" class="ColumnBackground">
										<bean:message key="tcSanctioned"/>&nbsp; in Rs.
									</TD>
									<TD width="10%" align="left" valign="top" colspan="2"  class="ColumnBackground">
										<bean:message key="wcLimitSanctioned"/>&nbsp; in Rs.
									</TD>
									</TR>

									<TR>
									<TD width="10%" align="left" valign="top"  class="ColumnBackground">
										<bean:message key="wcFundBased"/>
									</TD>
									<TD width="10%" align="left" valign="top" class="ColumnBackground">
										<bean:message key="wcNonFundBased"/>
									</TD>
									</TR>

									<%
										double amount = 0;
										double totalAmount = 0;
										double amountDisbursed = 0;
										double totalAmountDisbursed = 0;
										double amountOts= 0;
										double totalAmountOts = 0;
										double tcAmount = 0;
										double totalTcAmount = 0;
										double fbAmount = 0;
										double totalFbAmount = 0;
										double nfbAmount = 0;
										double totalNfbAmount = 0;
										String type = null;

									%>

									<tr>
									<logic:iterate name="rsForm"  property="danRaisedReport" id="object">
									<%
									      com.cgtsi.reports.OtsDetails dReport =  (com.cgtsi.reports.OtsDetails)object;
									%>
							
											<tr>
											<TD width="10%" align="left" valign="top" class="ColumnBackground1">
											<%String cgpan=dReport.getCgpan();
											 int length = cgpan.length();
											 type = cgpan.substring(length-2,length);%>
											 <%=cgpan%>
											
											</TD>

											<TD width="10%" align="left" valign="top" class="ColumnBackground1">						
											<div align="right">
											
											<%
											if((type.equals("TC")) || (type.equals("CC")))
											{
												tcAmount = dReport.getTcSanctionedAmount();
												totalTcAmount = totalTcAmount + tcAmount;
											%>
												<%=decimalFormat.format(tcAmount)%>
											<%
											}
											else
											{
											%>
												<%=""%>
											<%
											}
											%>


											</div>
											</TD>

											<TD width="10%" align="left" valign="top" class="ColumnBackground1">						
											<div align="right">

											<%
											if((type.equals("TC")) || (type.equals("CC")))
											{
												%>
												<%=""%>
												<%
											}
											else
											{
											fbAmount = dReport.getWcFbSanctionedAmount();
											totalFbAmount = totalFbAmount + fbAmount;
											%>
												<%=decimalFormat.format(fbAmount)%>
											<%
											}
											%>
											</div>
											</TD>

											<TD width="10%" align="left" valign="top" class="ColumnBackground1">						
											<div align="right">
											<%
											if((type.equals("TC")) || (type.equals("CC")))
											{
												%>
												<%=""%>
												<%
											}
											else
											{
											nfbAmount = dReport.getWcNfbSanctionedAmount(); 
											totalNfbAmount = totalNfbAmount + nfbAmount;				%>
												<%=decimalFormat.format(nfbAmount)%>
											<%
											}
											%>
											</div>
											</TD>

											<TD width="10%" align="left" valign="top" class="ColumnBackground1">						
											<div align="right">
											<%amount = dReport.getProposedAmount();
											totalAmount = totalAmount + amount;%>
											<%=decimalFormat.format(amount)%>
											</div>
											</TD>

											<TD width="10%" align="left" valign="top" class="ColumnBackground1">						
											<div align="right">
											<%amountDisbursed = dReport.getSacrificedAmount();
											totalAmountDisbursed = totalAmountDisbursed + amountDisbursed;%>
											<%=decimalFormat.format(amountDisbursed)%>
											</div>
											</TD>

											<TD width="10%" align="left" valign="top" class="ColumnBackground1">						
											<div align="right">
											<%amountOts = dReport.getOutstandingAmount();
											totalAmountOts = totalAmountOts + amountOts;%>
											<%=decimalFormat.format(amountOts)%>
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
											<%
											if(totalTcAmount == 0)
											{
											%>		
											<%=""%>
											<%
											}
											else
											{
											%>
											<%=decimalFormat.format(totalTcAmount)%>
											<%
											}
											%>
											</div>
											</TD>
											<TD width="10%" align="left" valign="top" class="ColumnBackground">						
											<div align="right">
											<%
											if(totalFbAmount == 0)
											{
											%>		
											<%=""%>
											<%
											}
											else
											{
											%>
											<%=decimalFormat.format(totalFbAmount)%>
											<%
											}
											%>

											
											</div>
											</TD>
											<TD width="10%" align="left" valign="top" class="ColumnBackground">						
											<div align="right">
											<%
											if(totalNfbAmount == 0)
											{
											%>		
											<%=""%>
											<%
											}
											else
											{
											%>
											<%=decimalFormat.format(totalNfbAmount)%>					
											<%
											}
											%>

											</div>
											</TD>
											<TD width="10%" align="left" valign="top" class="ColumnBackground">	
											<div align="right">
											<%=decimalFormat.format(totalAmount)%>
											</div>
											</TD>
											<TD width="10%" align="left" valign="top" class="ColumnBackground">						
											<div align="right">
											<%=decimalFormat.format(totalAmountDisbursed)%>
												</div>
											</TD>
											<TD width="10%" align="left" valign="top" class="ColumnBackground">						
											<div align="right">
											<%=decimalFormat.format(totalAmountOts)%>
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
									<A href="javascript:submitForm('otsReport.do?method=otsReport')">
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

