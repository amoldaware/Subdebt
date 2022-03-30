<%@ page language="java"%>
<%@ page import="java.util.ArrayList"%>
<%@page import ="java.text.SimpleDateFormat"%>
<%@page import ="java.text.DecimalFormat"%>
<%@ taglib uri="/WEB-INF/TLD/struts-html.tld" prefix="html" %>
<%@ taglib uri="/WEB-INF/TLD/struts-bean.tld" prefix="bean" %>
<%@ taglib uri="/WEB-INF/TLD/struts-logic.tld" prefix="logic" %>
<%@ include file="/jsp/SetMenuInfo.jsp" %>
<% session.setAttribute("CurrentPage","chequeDetailsUpdate.do?method=chequeDetailsUpdate");%>
<%SimpleDateFormat dateFormat=new SimpleDateFormat("dd/MM/yyyy");%>
<%DecimalFormat decimalFormat = new DecimalFormat("##########.##");%>

<TABLE width="725" border="0" cellpadding="0" cellspacing="0">
	<html:errors />
	<html:form action="chequeDetailsUpdatePage.do?method=chequeDetailsUpdatePage" method="POST" enctype="multipart/form-data">
		<TR> 
			<TD width="20" align="right" valign="bottom"><IMG src="images/TableLeftTop.gif" width="20" height="31"></TD>
			<TD background="images/TableBackground1.gif"><IMG src="images/InvestmentManagementHeading.gif" width="169" height="25"></TD>
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
												<TD width="28%" class="Heading"><bean:message key="chequeHeader" /></TD>
												<TD><IMG src="images/TriangleSubhead.gif" width="19" height="19"></TD>
											</TR>
											<TR>
												<TD colspan="3" class="Heading"><IMG src="images/Clear.gif" width="5" height="5"></TD>
											</TR>

										</TABLE>
									</TD>

									<TR>
									<TD width="10%" align="left" valign="top" class="ColumnBackground">
										<bean:message key="chequeNumber" />
									</TD>
									<TD width="10%" align="right" valign="top" class="ColumnBackground">
										<bean:message key="chequeAmount" />
									</TD>
									<TD width="10%" align="left" valign="top" class="ColumnBackground">
										<bean:message key="chequeDate" />
									</TD>
									<TD width="10%" align="left" valign="top" class="ColumnBackground">
										<bean:message key="chequeIssuedTo" />
									</TD>
									<TD width="10%" align="left" valign="top" class="ColumnBackground">
										<bean:message key="chequeBankName" />
									</TD>
									</TR>	 

									<% double sum = 0;
									   double totalSum = 0;
									%>
					
											<tr>
											<logic:iterate name="ifForm" id="object" property="chequeArray">
											<%
											com.cgtsi.investmentfund.ChequeDetails cheque = (com.cgtsi.investmentfund.ChequeDetails)object;
											%>

											<% int days=cheque.getExpiryDate();
											if(days < 10)
											{
											%>
											<TR>
											<TD align="center" valign="top" class="HeadingBg">	
											<% String value1 = cheque.getChequeNumber(); 
											   String value2 = cheque.getChequeId(); 
											  String url = "chequeDetailsUpdatePage.do?method=chequeDetailsUpdatePage&number="+value2;%>
											<html:link href="<%=url%>"><%=value1%></html:link>
											</TD>

											<td class="HeadingBg" width="15%"><div align="right"></div>
											<%sum=cheque.getChequeAmount();  
											totalSum = totalSum + sum;%>
											<%=decimalFormat.format(sum)%>
											</td>

											<td class="HeadingBg" width="15%"><div align="left">
											<%   java.util.Date utilDate=cheque.getChequeDate();
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


											<td class="HeadingBg" width="15%"><div align="left"></div><%=cheque.getChequeIssuedTo()%></td>

											<td class="HeadingBg" width="15%"><div align="left"></div><%=cheque.getBankName()%></td>
											
											</TR>
											<%
											}
											else
											{
											%>
											<TR>
											<TD align="center" valign="top" class="ColumnBackground1">	
											<% String value1 = cheque.getChequeNumber(); 
											   String value2 = cheque.getChequeId(); 
											  String url = "chequeDetailsUpdatePage.do?method=chequeDetailsUpdatePage&number="+value2;%>
											<html:link href="<%=url%>"><%=value1%></html:link>
											</TD>

											<td class="tableData" width="15%"><div align="right"></div>
											<%sum=cheque.getChequeAmount();
											totalSum = totalSum + sum;%>
											<%=decimalFormat.format(sum)%>
											</td>

											<td class="tableData" width="15%"><div align="left">
											<%   java.util.Date utilDate=cheque.getChequeDate();
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


											<td class="tableData" width="15%"><div align="left"></div><%=cheque.getChequeIssuedTo()%></td>

											<td class="tableData" width="15%"><div align="left"></div><%=cheque.getBankName()%></td>
											
											</TR>
											<%
											}
											%>
					

											</logic:iterate>

									<TR>
									<TD width="10%" align="left" valign="top" class="ColumnBackground">
										Total
									</TD>
									<TD width="10%" align="right" valign="top" class="ColumnBackground">
										<%=decimalFormat.format(totalSum)%>
									</TD>
									<TD width="10%" align="left" valign="top" class="ColumnBackground">
										
									</TD>
									<TD width="10%" align="left" valign="top" class="ColumnBackground">
										
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
							<A href="javascript:submitForm('chequeDetailsUpdateInput.do')">
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

  