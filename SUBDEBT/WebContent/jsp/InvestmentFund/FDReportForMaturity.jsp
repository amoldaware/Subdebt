<%@ page language="java"%>
<%@ page import="java.util.ArrayList"%>
<%@ page import="java.text.DecimalFormat"%>
<%@ taglib uri="/WEB-INF/TLD/struts-html.tld" prefix="html" %>
<%@ taglib uri="/WEB-INF/TLD/struts-bean.tld" prefix="bean" %>
<%@ taglib uri="/WEB-INF/TLD/struts-logic.tld" prefix="logic" %>
<%@ include file="/jsp/SetMenuInfo.jsp" %>
<% session.setAttribute("CurrentPage","fdiReport.do?method=fdiReport");%>


<TABLE width="725" border="0" cellpadding="0" cellspacing="0">
	<html:errors />
	<html:form action="fdiReportInput.do?method=fdiReportInput" method="POST" enctype="multipart/form-data">
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
									<TD colspan="12"> 
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
									<TD width="10%" align="left" valign="top" class="ColumnBackground">
										<bean:message key="investee" />
									</TD>
									<TD width="10%" align="left" valign="top" class="ColumnBackground">
										<bean:message key="mType"/>
									</TD>
									<TD width="10%" align="left" valign="top" class="ColumnBackground">
										<bean:message key="principal"/>
									</TD>
									<TD width="10%" align="left" valign="top" class="ColumnBackground">
										<bean:message key="maturityAmountRs"/>
									</TD>
									</TR>	
									<%
									DecimalFormat df= new DecimalFormat("######################.##");
									df.setDecimalSeparatorAlwaysShown(false);

									double totalSum = 0;
									double totalAmount = 0;

									%>
									<tr>
									<logic:iterate name="ifForm"  property="fdReport"  id="object">
									<%
									      com.cgtsi.investmentfund.FDReport pReport = (com.cgtsi.investmentfund.FDReport)object;
										  String prnAmt = df.format(pReport.getPrincipalAmount());
										  String matAmt = df.format(pReport.getMaturityAmount());
									%>
									
					
											<TR>
											<TD width="10%" align="left" valign="top" class="ColumnBackground1">		
											<% String mli = pReport.getInvestee(); 
												   String url = null;	
											if(!(mli == null  || mli.equals("null") || mli.equals("")))
											{
											   url = "fdDetailsForMaturity.do?method=fdDetailsForMaturity&investee="+mli;
											 %>
											<html:link href="<%=url%>"><%=mli%></html:link>
											<%
											}
											%>
											</TD>
											<TD width="10%" align="left" valign="top" class="ColumnBackground1">						
											<%=pReport.getMaturityType()%>
											</TD>
											<TD width="10%" align="left" valign="top" class="ColumnBackground1">	
											<div align="right">
											<%=prnAmt%>   
											</div>
											<%totalSum = totalSum + pReport.getPrincipalAmount();%>
											</TD>
											<TD width="10%" align="left" valign="top" class="ColumnBackground1">						
											<div align="right">
											<%=matAmt%>
											</div>
											<%totalAmount = totalAmount + pReport.getMaturityAmount();%>
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
											<%=df.format(totalSum)%>
											</div>
											</TD>
											<TD width="10%" align="left" valign="top" class="ColumnBackground">	
											<div align="right">
											  <%=df.format(totalAmount)%>
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
							<A href="javascript:submitForm('fdiReportInput.do?method=fdiReportInput')">
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

