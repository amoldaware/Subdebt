<%@ page language="java"%>
<%@ page import="java.util.ArrayList"%>
<%@page import ="java.text.SimpleDateFormat"%>
<%@page import ="java.text.DecimalFormat"%>

<%@ taglib uri="/WEB-INF/TLD/struts-html.tld" prefix="html" %>
<%@ taglib uri="/WEB-INF/TLD/struts-bean.tld" prefix="bean" %>
<%@ taglib uri="/WEB-INF/TLD/struts-logic.tld" prefix="logic" %>
<%@ include file="/jsp/SetMenuInfo.jsp" %>
<% session.setAttribute("CurrentPage","defaultRateReport.do?method=defaultRateReport");%>
<%DecimalFormat decimalFormat = new DecimalFormat("##########.##");%>
<%DecimalFormat decimalFormat1 = new DecimalFormat("####.####");%>

<TABLE width="725" border="0" cellpadding="0" cellspacing="0">
	<html:errors />
	<html:form action="defaultRateReport.do?method=defaultRateReport" method="POST" enctype="multipart/form-data">
		<TR> 
			<TD width="20" align="right" valign="bottom"><IMG src="images/TableLeftTop.gif" width="20" height="31"></TD>
			<TD background="images/TableBackground1.gif"><IMG src="images/RiskManagementHeading.gif" width="121" height="25"></TD>
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
												<TD width="20%" class="Heading"><bean:message key="defaultRateHeader" /></TD>
												<TD><IMG src="images/TriangleSubhead.gif" width="19" height="19"></TD>
											</TR>
											<TR>
												<TD colspan="3" class="Heading"><IMG src="images/Clear.gif" width="5" height="5"></TD>
											</TR>

										</TABLE>
									</TD>

									<TR>
									<TD width="1%" align="left" valign="top" class="ColumnBackground">
										S.No
									</TD>
									<TD width="10%" align="left" valign="top" class="ColumnBackground">
									<div align="center">
										<bean:message key="cpmliid" />
									</div>
									</TD>
									<TD width="10%" align="left" valign="top" class="ColumnBackground">
										<bean:message key="cpmliname" />
									</TD>
									<TD width="10%" align="left" valign="top" class="ColumnBackground">
										<bean:message key="cumulativeClaim" />
									</TD>
									<TD width="10%" align="left" valign="top" class="ColumnBackground">
										<bean:message key="cumulativeAmount"/>
									</TD>
									<TD width="10%" align="left" valign="top" class="ColumnBackground">
										<bean:message key="defaultRate"/>
									</TD>
									</TR>	

									<%
										double amount = 0;
										double totalAmount = 0;
										double amountTemp = 0;
										double amountDisbursed = 0;
										double amountDisbursedTemp = 0;
										double totalAmountDisbursed = 0;
										int i=0;

									%>

									<tr>
									<logic:iterate name="rmForm"  property="defaultArray" id="object">
									<%
									      com.cgtsi.risk.DefaultRate dReport =  (com.cgtsi.risk.DefaultRate)object;
									%>
							
											<tr>
											<TD  width="1%" align="left" valign="top" class="ColumnBackground1">
											<div align="right">
											<%=++i%>
											</div>
											</TD>
											<TD width="10%" align="left" valign="top" class="ColumnBackground1">
											<div align="center">
											<%String mli=dReport.getMliId();
											String id = mli + "00000000";%>
											<%=id%>
											</div>
											</TD>
											<TD width="10%" align="left" valign="top" class="ColumnBackground1">
											<%=dReport.getMliName()%>

											</TD>
											<TD width="10%" align="left" valign="top" class="ColumnBackground1">						
											<div align="right">
											<%  amount = dReport.getCumulativeClaimSettlement();
											totalAmount = totalAmount + amount;%>
											<%=decimalFormat.format(amount)%>
											</div>
											</TD>
											<TD width="10%" align="left" valign="top" class="ColumnBackground1">	
											<div align="right">
											<%amountDisbursed = dReport.getGuaranteeCoverIssuedAmount();
											totalAmountDisbursed = totalAmountDisbursed + amountDisbursed;%>
											<%=decimalFormat.format(amountDisbursed)%>
											</div>
											</TD>
											<TD width="10%" align="left" valign="top" class="ColumnBackground1">						
											<div align="right">
											<%double defaultRate=dReport.getDefaultRate();%>
											<%=decimalFormat1.format(defaultRate)%>
					
												</div>
											</TD>
											</TR>

											</logic:iterate>

											<tr>
											<TD width="1%" align="left" valign="top" class="ColumnBackground">	
											</TD>
											<TD width="10%" align="left" valign="top" class="ColumnBackground">	
											<div align="center">
											Total
											</div>
											</TD>
											<TD width="10%" align="left" valign="top" class="ColumnBackground">	
											</TD>
											<TD width="10%" align="left" valign="top" class="ColumnBackground">						
											<div align="right">
											<%=decimalFormat.format(totalAmount)%>
											</div>
											</TD>
											<TD width="10%" align="left" valign="top" class="ColumnBackground">		
											<div align="right">
											<%=decimalFormat.format(totalAmountDisbursed)%>				</div>

											</TD>
											<TD width="10%" align="left" valign="top" class="ColumnBackground">						
											
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
									<A href="javascript:submitForm('defaultRateReportInput.do?method=defaultRateReportInput')">
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

