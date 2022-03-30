<%@ page language="java"%>
<%@ page import="java.util.ArrayList"%>
<%@page import ="java.text.SimpleDateFormat"%>
<%@page import ="java.text.DecimalFormat"%>

<%@ taglib uri="/WEB-INF/TLD/struts-html.tld" prefix="html" %>
<%@ taglib uri="/WEB-INF/TLD/struts-bean.tld" prefix="bean" %>
<%@ taglib uri="/WEB-INF/TLD/struts-logic.tld" prefix="logic" %>
<%@ include file="/jsp/SetMenuInfo.jsp" %>
<% session.setAttribute("CurrentPage","disbursementReport.do?method=disbursementReport");%>
<%SimpleDateFormat dateFormat=new SimpleDateFormat("dd/MM/yyyy");%>
<%DecimalFormat decimalFormat = new DecimalFormat("##########.##");%>

<TABLE width="725" border="0" cellpadding="0" cellspacing="0">
	<html:errors />
	<html:form action="disbursementReportInput.do?method=disbursementReportInput" method="POST" enctype="multipart/form-data">
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
												<TD width="20%" class="Heading"><bean:message key="disbursementReportHeader" /></TD>
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
										<bean:message key="cgpanNumber" />
									</TD>
									<TD width="10%" align="left" valign="top" class="ColumnBackground">
										<bean:message key="termCreditSactioned" />
									</TD>
									<TD width="10%" align="left" valign="top" class="ColumnBackground">
										<bean:message key="dateOfDisbursement"/>
									</TD>
									<TD width="10%" align="left" valign="top" class="ColumnBackground">
										<bean:message key="amountDisbursed"/>
									</TD>
									</TR>	

									<%
										double amount = 0;
										double totalAmount = 0;
										double amountDisbursed = 0;
										double amountDisbursedTemp = 0;
										double totalAmountDisbursed = 0;
										String cgpanTemp = "";
										double amountTemp = 0;
									%>

									<tr>
									<logic:iterate name="rsForm"  property="disbursement" id="object">
									<%
									      com.cgtsi.reports.DisbursementReport dReport =  (com.cgtsi.reports.DisbursementReport)object;
									%>
							
											<tr>
											<TD width="10%" align="left" valign="top" class="ColumnBackground1">	
											<%=dReport.getMemberid()%>
											</TD>
											<TD width="10%" align="left" valign="top" class="ColumnBackground1">
											<%String cgpan=dReport.getCgpan();
											//System.out.println("cgpan:"+cgpan);
											if((cgpan != null) && (!cgpan.equals("")))
											{
												//System.out.println("cgpan1:"+cgpan);
												if(!cgpan.equals(cgpanTemp))
												{        
												cgpanTemp=cgpan;
												%>
												<%=cgpan%>											
												<%
												}
												else
												{
												%>
												<%=""%>											
												<%
												}
											}
											%>

											</TD>
											<TD width="10%" align="left" valign="top" class="ColumnBackground1">						
											<div align="right">
											<%  amount = dReport.getTermCredit();
											totalAmount = totalAmount + amount;%>
											<%=decimalFormat.format(amount)%>
											</div>
											</TD>
											<TD width="10%" align="left" valign="top" class="ColumnBackground1">						
											<%   java.util.Date utilDate=dReport.getDisbursementDate();
													String formatedDate = null;
													if(utilDate != null)
													{
														 formatedDate=dateFormat.format(utilDate);
													}
													else
													{
														 formatedDate = "";
													}
											%>
											<%=formatedDate%>
											</TD>
											<TD width="10%" align="left" valign="top" class="ColumnBackground1">						
											<div align="right">
											<%amountDisbursed = dReport.getDisbursedAmount();
											totalAmountDisbursed = totalAmountDisbursed + amountDisbursed;%>
											<%=decimalFormat.format(amountDisbursed)%>
											
												</div>
											</TD>
											</TR>

											</logic:iterate>

											<tr>
											<TD width="10%" align="left" valign="top" class="ColumnBackground">	
											Total
											</TD>
											<TD width="10%" align="left" valign="top" class="ColumnBackground">	
											</TD>
											<TD width="10%" align="left" valign="top" class="ColumnBackground">						
											<div align="right">
											<%=decimalFormat.format(totalAmount)%>
											</div>
											</TD>
											<TD width="10%" align="left" valign="top" class="ColumnBackground">						

											</TD>
											<TD width="10%" align="left" valign="top" class="ColumnBackground">						
											<div align="right">
											<%=decimalFormat.format(totalAmountDisbursed)%>
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
									<A href="javascript:submitForm('disbursementReportInput.do?method=disbursementReportInput')">
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

