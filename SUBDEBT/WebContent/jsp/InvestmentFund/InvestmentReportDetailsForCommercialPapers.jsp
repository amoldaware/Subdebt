<%@ page language="java"%>
<%@ page import="java.util.ArrayList"%>
<%@page import ="java.text.SimpleDateFormat"%>
<%@page import = "org.apache.struts.action.DynaActionForm"%>
<%@ taglib uri="/WEB-INF/TLD/struts-html.tld" prefix="html" %>
<%@ taglib uri="/WEB-INF/TLD/struts-bean.tld" prefix="bean" %>
<%@ taglib uri="/WEB-INF/TLD/struts-logic.tld" prefix="logic" %>
<%@ include file="/jsp/SetMenuInfo.jsp" %>
<% session.setAttribute("CurrentPage","investmentReportDetailsFinal.do?method=investmentReportDetailsFinal");%>
<%SimpleDateFormat dateFormat=new SimpleDateFormat("dd/MM/yyyy");%>
<%@page import ="java.text.DecimalFormat"%>
<%DecimalFormat decimalFormat = new DecimalFormat("##########.##");%>


<TABLE width="725" border="0" cellpadding="0" cellspacing="0">
	<html:errors />
	<html:form action="investmentReportDetailsFinal.do?method=investmentReportDetailsFinal" method="POST" enctype="multipart/form-data">
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
									<TD colspan="16"> 
										<TABLE width="100%" border="0" cellpadding="0" cellspacing="0">
											<TR>
												<TD width="40%" class="Heading"><bean:message key="investmentDetailsHeader" />&nbsp
												</TD>
												<TD><IMG src="images/TriangleSubhead.gif" width="19" height="19"></TD>
											</TR>
											<TR>
												<TD colspan="3" class="Heading"><IMG src="images/Clear.gif" width="5" height="5"></TD>
											</TR>

										</TABLE>
									</TD>

									<TR>
									<TD width="10%" align="left" valign="top" class="ColumnBackground">
										<bean:message key="investee"/>
									</TD>
									<TD width="10%" align="left" valign="top" class="ColumnBackground">
										<bean:message key="maturityType"/>
									</TD>
									<TD width="10%" align="left" valign="top" class="ColumnBackground">
										<bean:message key="ratingType"/>
									</TD>
									<TD width="10%" align="left" valign="top" class="ColumnBackground">
										<bean:message key="companyName"/>
									</TD>
									<TD width="10%" align="left" valign="top" class="ColumnBackground">
										<bean:message key="receiptNumber"/>
									</TD>

									<TD width="10%" align="left" valign="top" class="ColumnBackground">
										<bean:message key="faceValueRs"/>
									</TD>
									<TD width="10%" align="left" valign="top" class="ColumnBackground">
										<bean:message key="numberOfUnits"/>
									</TD>
									<TD width="10%" align="left" valign="top" class="ColumnBackground">
										<bean:message key="investedDate"/>
									</TD>
									<TD width="10%" align="left" valign="top" class="ColumnBackground">
										<bean:message key="investedAmount"/>
									</TD>
									<TD width="10%" align="left" valign="top" class="ColumnBackground">
										<bean:message key="tenure"/>
									</TD>
									<TD width="10%" align="left" valign="top" class="ColumnBackground">
										<bean:message key="tenureType"/>
									</TD>
									<TD width="10%" align="left" valign="top" class="ColumnBackground">
										<bean:message key="maturityDate"/>
									</TD>
									<TD width="10%" align="left" valign="top" class="ColumnBackground">
										<bean:message key="maturityAmountRs"/>
									</TD>
									<TD width="10%" align="left" valign="top" class="ColumnBackground">
										<bean:message key="interestpa"/>
									</TD>
									<TD width="10%" align="left" valign="top" class="ColumnBackground">
										<bean:message key="callPutOption"/>
									</TD>
									<TD width="10%" align="left" valign="top" class="ColumnBackground">
										<bean:message key="callPutDuration"/>
									</TD>
									<%	double sum = 0;
										double totalSum = 0;
										double gFee = 0;
										double totalgFee = 0;
										double amount = 0;
										double totalAmount = 0;
									%>
									</TR>	
					
													
											<tr>
											<logic:iterate name="ifForm" id="object" property="investmentArray"> 
											<%
											com.cgtsi.investmentfund.InvestmentDetails investment =  (com.cgtsi.investmentfund.InvestmentDetails)object;
											%>
													
											<TR>
												<TD width="10%" align="left" valign="top" class="ColumnBackground1">	
												<div align="left">
												<%=investment.getInvestee()%>
												  </div>
												</TD>
												<TD width="10%" align="left" valign="top" class="ColumnBackground1">	
												<div align="left">
												<%=investment.getMaturityType()%>
												  </div>
												</TD>
												<TD width="10%" align="left" valign="top" class="ColumnBackground1">	
												<div align="left">
												<%=investment.getRatingType()%>
												  </div>
												</TD>
												<TD width="10%" align="left" valign="top" class="ColumnBackground1">	
												<div align="left">
												<%=investment.getInvestmentName()%>	
												 </div>
												</TD>
												<TD width="10%" align="left" valign="top" class="ColumnBackground1">	
												<div align="left">
												<%=investment.getReceiptNumber()%>
												  </div>
												</TD>
												<TD width="10%" align="left" valign="top" class="ColumnBackground1">	
												<div align="left">
												<%=investment.getFaceValue()%>
												  </div>
												</TD>
												<TD width="10%" align="left" valign="top" class="ColumnBackground1">	
												<div align="right">
												<% sum=investment.getNumberOfUnits();
												  totalSum = sum + totalSum;%>						<%=decimalFormat.format(sum)%>	

												 </div>
												</TD>
												<TD width="10%" align="left" valign="top" class="ColumnBackground1">	
												<%   java.util.Date utilDate3=investment.getInvestmentDate();
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
												<div align="right">
												<% gFee=investment.getInvestmentAmount();
												  totalgFee = gFee + totalgFee;%>						<%=decimalFormat.format(gFee)%>	
												 </div>
												</TD>
												<TD width="10%" align="left" valign="top" class="ColumnBackground1">	
												<div align="left">
												<%=investment.getTenure()%>
												  </div>
												</TD>
												<TD width="10%" align="left" valign="top" class="ColumnBackground1">	
												<div align="left">
												<%String tenure=investment.getTenureType();
												  if(tenure.equals("D"))	
												  {
												  %>
												  <%="Days"%>
												  <%
												  }
												  else if(tenure.equals("M"))
												  {
												  %>
												  <%="Months"%>
												  <%
												  }
												  else if(tenure.equals("Y"))
												  {
												  %>
												  <%="Years"%>
												  <%
												  }
												  %>
												 </div>
												</TD>
												<TD width="10%" align="left" valign="top" class="ColumnBackground1">		
												
												<%   java.util.Date utilDate=investment.getMaturityDate();
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
												<% amount=investment.getMaturityAmount();
												  totalAmount = amount + totalAmount;%>						<%=decimalFormat.format(amount)%>	
												  </div>
												</TD>
												<TD width="10%" align="left" valign="top" class="ColumnBackground1">	
												<div align="right">
												<%=investment.getInterestRate()%>	
												 </div>
												</TD>
												<TD width="10%" align="left" valign="top" class="ColumnBackground1">	
												<div align="left">
												<%String call=investment.getCallPutOption();
												  if(call.equals("C"))	
												  {
												  %>
												  <%="Call"%>
												  <%
												  }
												  else 
												  {
												  %>
												  <%="Put"%>
												  <%
												  }
												  %>
												  </div>
												</TD>
												<TD width="10%" align="left" valign="top" class="ColumnBackground1">	
												<div align="left">
												<%=investment.getCallPutDuration()%>	
												 </div>
												</TD>

											</TR>
											</logic:iterate>

									<tr>
									<TD align="left" valign="top" class="ColumnBackground">
									Total
									</TD>
									<TD align="left" valign="top" class="ColumnBackground1">
									</TD>
									<TD align="left" valign="top" class="ColumnBackground">
									</TD>
									<TD align="left" class="ColumnBackground"> 
									</TD>
									<TD align="left" class="ColumnBackground"> 
									</TD>
									<TD align="left" class="ColumnBackground"> 
									</TD>
									<TD align="left" valign="top" class="ColumnBackground">
									<div align="right">
									<%=decimalFormat.format(totalSum)%>
									</div>
									</TD>
									<TD align="left" class="ColumnBackground"> 
									</TD>
									<TD align="left" valign="top" class="ColumnBackground">
									<div align="right">
									<%=decimalFormat.format(totalgFee)%>
									</div>
									</TD>
									<TD align="left" class="ColumnBackground"> 
									</TD>
									<TD align="left" valign="top" class="ColumnBackground1">
									</TD>
									<TD align="left" valign="top" class="ColumnBackground">
									</TD>
									<TD align="left" class="ColumnBackground"> 
									<div align="right">
									<%=decimalFormat.format(totalAmount)%>
									</div>
									</TD>
									<TD align="left" class="ColumnBackground"> 
									</TD>
									<TD align="left" class="ColumnBackground"> 
									</TD>
									<TD align="left" class="ColumnBackground"> 
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
					<TR >
						<TD align="center" valign="baseline" >
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

