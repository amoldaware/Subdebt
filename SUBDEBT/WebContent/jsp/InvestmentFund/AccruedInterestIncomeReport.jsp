<%@ page language="java"%>
<%@ page import="java.util.ArrayList"%>
<%@page import ="java.text.SimpleDateFormat"%>
<%@ page import="java.text.DecimalFormat"%>
<%@ taglib uri="/WEB-INF/TLD/struts-html.tld" prefix="html" %>
<%@ taglib uri="/WEB-INF/TLD/struts-bean.tld" prefix="bean" %>
<%@ taglib uri="/WEB-INF/TLD/struts-logic.tld" prefix="logic" %>
<%@ include file="/jsp/SetMenuInfo.jsp" %>
<%DecimalFormat decimalFormat = new DecimalFormat("##########.##");%>
<% session.setAttribute("CurrentPage","accruedInterestIncomeReport.do?method=accruedInterestIncomeReport");%>
<%SimpleDateFormat dateFormat=new SimpleDateFormat("dd/MM/yyyy");%>

<TABLE width="725" border="0" cellpadding="0" cellspacing="0">
	<html:errors />
	<html:form action="accruedInterestIncomeReport.do?method=accruedInterestIncomeReport" method="POST" enctype="multipart/form-data">
		<TR> 
			<TD width="20" align="right" valign="bottom"><IMG src="images/TableLeftTop.gif" width="20" height="31"></TD>
			<TD background="images/TableBackground1.gif"><IMG src="images/InvestmentManagementHeading.gif" width="180" height="25"></TD>
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
									<TD colspan="13"> 
										<TABLE width="100%" border="0" cellpadding="0" cellspacing="0">
											<TR>
												<TD width="25%" class="Heading"><bean:message key="investmentDetailsHeader" /></TD>
												<TD><IMG src="images/TriangleSubhead.gif" width="19" height="19"></TD>
											</TR>
											<TR>
												<TD colspan="3" class="Heading"><IMG src="images/Clear.gif" width="5" height="5"></TD>
											</TR>

										</TABLE>
									</TD>

									<TR>
									<TD width="10%" align="left" valign="top" rowspan="2"  class="ColumnBackground">
										<bean:message key="bank" />
									</TD>
									<TD width="10%" align="left" valign="top" rowspan="2"  class="ColumnBackground">
										<bean:message key="depositDate"/>
									</TD>
									<TD width="10%" align="left" valign="top" rowspan="2" class="ColumnBackground">
										<bean:message key="amountRs"/>
									</TD>
									<TD width="10%" align="left" valign="top" rowspan="2" class="ColumnBackground">
										<bean:message key="maturityDate"/>
									</TD>
									<TD width="10%" align="left" valign="top"  colspan="2" class="ColumnBackground">
										<bean:message key="interestPeriod"/>
									</TD>
									<TD width="10%" align="left" valign="top" rowspan="2" class="ColumnBackground">
										<bean:message key="interestRate"/>
									</TD>
									<TD width="10%" align="left" valign="top" rowspan="2" class="ColumnBackground">
										<bean:message key="noOfDays"/>
									</TD>
									<TD width="10%" align="left" valign="top" rowspan="2" class="ColumnBackground">
										<bean:message key="annualInterestRate"/>
									</TD>
									<TD width="10%" align="left" valign="top" rowspan="2" class="ColumnBackground">
										<bean:message key="interestEarned"/>
									</TD>
									<TD width="10%" align="left" valign="top" rowspan="2" class="ColumnBackground">
										<bean:message key="amountRs"/>
									</TD>
									<TD width="10%" align="left" valign="top" rowspan="2" class="ColumnBackground">
										<bean:message key="amountRs"/>
									</TD>
									<TD width="10%" align="left" valign="top" rowspan="2" class="ColumnBackground">
										<bean:message key="averageRoi"/>
									</TD>
									</TR>	

									<TR>
									<TD width="10%" align="left" valign="top" class="ColumnBackground">
										<bean:message key="from"/>
									</TD>
									<TD width="10%" align="left" valign="top" class="ColumnBackground">
										<bean:message key="to"/>
									</TD>
									</TR>

									<%

										double interestEarned = 0;
										double totalInterestEarned = 0;
										double amount1 = 0;
										double totalAmount1 = 0;
										double amount2 = 0;
										double totalAmount2 = 0;
										double depositAmount = 0;
										double totalDepositAmount = 0;
										double roi = 0;
										double totalRoi = 0;
										double averageRoi = 0;
										int i = 0;
									
									%>
									<tr>
									<logic:iterate name="ifForm"  property="mliDetails"  id="object">
									<%
									      com.cgtsi.investmentfund.AccruedInterestIncome dReport = (com.cgtsi.investmentfund.AccruedInterestIncome)object;
										  ++i;

									%>
									
					
											<TR>
											<TD width="10%" align="left" valign="top" class="ColumnBackground1">						
											<%=dReport.getInvestee()%>
											</TD>
											<TD width="10%" align="left" valign="top" class="ColumnBackground1">						
											<%   java.util.Date utilDate1=dReport.getDepositDate();
													String formatedDate1 = null;
													if(utilDate1 != null)
													{
														 formatedDate1=dateFormat.format(utilDate1);
													}
													else
													{
														 formatedDate1 = "";
													}
											%>
											<%=formatedDate1%>
											</TD>
											<TD width="10%" align="left" valign="top" class="ColumnBackground1">						
											<%  depositAmount = dReport.getDepositAmount();
											totalDepositAmount = totalDepositAmount + depositAmount;%>
											<div align="right">
											<%=decimalFormat.format(depositAmount)%>
											</div>
											</TD>
											<TD width="10%" align="left" valign="top" class="ColumnBackground1">						
											<%   java.util.Date utilDate2=dReport.getMaturityDate();
													String formatedDate2 = null;
													if(utilDate2 != null)
													{
														 formatedDate2=dateFormat.format(utilDate2);
													}
													else
													{
														 formatedDate2 = "";
													}
											%>
											<%=formatedDate2%>
											</TD>
											<TD width="10%" align="left" valign="top" class="ColumnBackground1">						
											<%   java.util.Date utilDate3=dReport.getFromDate();
													String formatedDate3 = null;
													if(utilDate3 != null)
													{
														 formatedDate3=dateFormat.format(utilDate3);
													}
													else
													{
														 formatedDate3 = "";
													}
											%>
											<%=formatedDate3%>
											</TD>
											<TD width="10%" align="left" valign="top" class="ColumnBackground1">						
											<%   java.util.Date utilDate4=dReport.getToDate();
													String formatedDate4 = null;
													if(utilDate4 != null)
													{
														 formatedDate4=dateFormat.format(utilDate4);
													}
													else
													{
														 formatedDate4 = "";
													}
											%>
											<%=formatedDate4%>
											</TD>
											<TD width="10%" align="left" valign="top" class="ColumnBackground1">	
											<div align="right">
											<%=dReport.getInterest()%>
											</div>
											</TD>
											<TD width="10%" align="left" valign="top" class="ColumnBackground1">	
											<div align="right">
											<%=dReport.getDays()%>
											</div>
											</TD>
											<TD width="10%" align="left" valign="top" class="ColumnBackground1">						
											<%double annualizedInterest=dReport.getAnnualInterest();%>
											<div align="right">
											<%=decimalFormat.format(annualizedInterest)%>
											</div>
											</TD>
											<TD width="10%" align="left" valign="top" class="ColumnBackground1">	
											<%  interestEarned = dReport.getInterestEarned();
											totalInterestEarned = totalInterestEarned+interestEarned;%>
											<div align="right">
											<%=decimalFormat.format(interestEarned)%>
											</div>
											</TD>
											<TD width="10%" align="left" valign="top" class="ColumnBackground1">						
											<%  amount1 = dReport.getAmountFirst();
											totalAmount1 = totalAmount1 + amount1;%>
											<div align="right">
											<%=decimalFormat.format(amount1)%>
											</div>
											</TD>
											<TD width="10%" align="left" valign="top" class="ColumnBackground1">						
											<%  amount2 = dReport.getAmountSecond();
											totalAmount2 = totalAmount2 + amount2;%>
											<div align="right">
											<%=decimalFormat.format(amount2)%>
											</div>
											</TD>
											<TD width="10%" align="left" valign="top" class="ColumnBackground1">
											<%  roi = dReport.getRoi();
											totalRoi = totalRoi + roi;%>
											<div align="right">
											<%=decimalFormat.format(roi)%>
											</div>
											</TD>
											</TR>
											</logic:iterate>

											<TR>
											<TD width="10%" align="left" valign="top" class="ColumnBackground">	
											Total
											</TD>
											<TD width="10%" align="left" valign="top" class="ColumnBackground">	
											</TD>
											<TD width="10%" align="left" valign="top" class="ColumnBackground">						
											<div align="right">
											<%=decimalFormat.format(totalDepositAmount)%>
											</div>
											</TD>
											<TD width="10%" align="left" valign="top" class="ColumnBackground">						
											</TD>
											<TD width="10%" align="left" valign="top" class="ColumnBackground">	
											</TD>
											<TD width="10%" align="left" valign="top" class="ColumnBackground">	
											</TD>
											<TD width="10%" align="left" valign="top" class="ColumnBackground">	
											</TD>
											<TD width="10%" align="left" valign="top" class="ColumnBackground">	
											</TD>
											<TD width="10%" align="left" valign="top" class="ColumnBackground">	
											</TD>
											<TD width="10%" align="left" valign="top" class="ColumnBackground">						
											<div align="right">
											<%=decimalFormat.format(totalInterestEarned)%>
											</div>
											</TD>
											<TD width="10%" align="left" valign="top" class="ColumnBackground">						
											<div align="right">
											<%=decimalFormat.format(totalAmount1)%>
											</div>
											</TD>
											<TD width="10%" align="left" valign="top" class="ColumnBackground">						
											<div align="right">
											<%=decimalFormat.format(totalAmount2)%>
											</div>
											</TD>
											<TD width="10%" align="left" valign="top" class="ColumnBackground">						
											<div align="right">
											<%averageRoi=totalRoi/i;%>
											<%=decimalFormat.format(averageRoi)%>
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

