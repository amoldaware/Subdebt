<%@ page language="java"%>
<%@ taglib uri="/WEB-INF/TLD/struts-html.tld" prefix="html" %>
<%@ taglib uri="/WEB-INF/TLD/struts-bean.tld" prefix="bean" %>
<% session.setAttribute("CurrentPage","getPaymentDetails.do?method=getPaymentDetailsForPayInSlip");%>
<%@ include file="/jsp/SetMenuInfo.jsp" %>
<%@ page import="java.text.DecimalFormat"%>
<%
DecimalFormat df=new DecimalFormat("######################.##");
df.setDecimalSeparatorAlwaysShown(false);
%>

<TABLE width="725" border="0" cellpadding="0" cellspacing="0">
	<html:errors />
	<html:form action="getPaymentDetails.do?method=getPaymentDetailsForPayInSlip" method="POST">

		<TR>
			<TD width="20" align="right" valign="bottom"><IMG src="images/TableLeftTop.gif" width="20" height="31"></TD>
			<TD background="images/TableBackground1.gif"></TD>
			<TD width="20" align="left" valign="bottom"><IMG src="images/TableRightTop.gif" width="23" height="31"></TD>
		</TR>
		<TR>
			<TD width="20" background="images/TableVerticalLeftBG.gif">&nbsp;</TD>
			<TD>
				<TABLE width="100%" border="0" align="left" cellpadding="0" cellspacing="0">
					<TR>
						<TD>
							<table width="661" border="0" cellspacing="1" cellpadding="0">
							 <TR>
								<TD colspan="7">
									<TABLE width="100%" border="0" cellpadding="0" cellspacing="0">
										<TR>
											<TD width="31%" class="Heading"><bean:message key="payInSlipDetail" /></TD>
											<TD><IMG src="images/TriangleSubhead.gif" width="19" height="19"></TD>
										</TR>
										<TR>
											<TD colspan="6" class="Heading"><IMG src="images/Clear.gif" width="5" height="5"></TD>
										</TR>
									</TABLE>
								</TD>
							</TR>
							</table>

							<table width="661" border="0" cellspacing="1" cellpadding="1">
								<tr align="left">
									<TD rowspan="2" class="TableData"><IMG src="images/hdfclogo1.gif"><IMG src="images/hdfclogo.gif"></TD>
									<TD class="TableData" rowspan="2">Indicate Bank A/c for cheque/cash deposi & for credit card payment indicate credit card No.
									<br>Please quote your Account No./Credit Card No on the reverse of the cheque<br>
									Please use seperate slips for cash/local cheques/tranfer cheque.
									</TD>
									<TD class="TableData"><bean:message key="cashDeposit" /></TD>
								</tr>
								<tr align="left">
									<TD class="ColumnBackground"><bean:message key="date"/><br>______________
									</TD>
								</tr>
								<tr>
								<td colspan="7"><table width="661" border="1" cellspacing="1" cellpadding="1">
								<tr align="left">
									<td class="ColumnBackground" colspan="2">
										<div align="left">
										  &nbsp;<bean:message key="accName" />
										</div>
									 </td>
									 <td class="ColumnBackground" colspan="3">
										<div align="left">
										  &nbsp;
										  <bean:message key="acNum" />
										</div>
									 </td>
									<td class="ColumnBackground" colspan="2">
										<div align="left">
										  &nbsp;<bean:message key="homeBranch" />
										</div>
									 </td>
								</tr>
								<tr align="left">
									<td class="TableData" colspan="2">
										<div align="left">
										  &nbsp;CGTSI
										</div>
									 </td>
									 <td class="TableData" colspan="3">
										<div align="left">
										  &nbsp;
										  <bean:write property="accountNumber" name="rpAllocationForm"/>
										</div>
									 </td>
									<td class="TableData" colspan="2">
										<div align="left">
										  &nbsp;
										</div>
									 </td>
								</tr>
								<tr align="left">
									<td class="ColumnBackground" colspan="2" rowspan="2">
										<div align="left">
										  &nbsp;<bean:message key="towards" /> &nbsp;&nbsp; <bean:message key="chqCash" /> <br>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp; &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
										  &nbsp;<bean:message key="creditPay" />
										</div>
									 </td>
									 <td class="ColumnBackground" colspan="3">
										<div align="left">
										  &nbsp;
										  <bean:message key="creditCardNo" />
										</div>
									 </td>
									<td class="ColumnBackground" colspan="2">
										<div align="left">
										  &nbsp;<bean:message key="phNo" />
										</div>
									 </td>
								</tr>
								<tr align="left">
									 <td class="TableData" colspan="3">
										<div align="left">
										  &nbsp;
										</div>
									 </td>
									<td class="TableData" colspan="2">
										<div align="left">
										  &nbsp;
										</div>
									 </td>
								</tr>
								<tr align="left">
									 <td class="ColumnBackground" colspan="7">
										<div align="left">
										  &nbsp;<bean:message key="amtWords" /> _________________________________________________________<br><br>
										  _________________________________________________________________________________
										</div>
									 </td>
								</tr>
								</table></td></tr>
								<tr>
									<td colspan="7">
									<table width="661" border="1" cellspacing="1" cellpadding="1">
									<tr>
										<td class="ColumnBackground" colspan="5">
											<div align="left">
											  &nbsp;<bean:message key="chqDetails" />
											</div>
										 </td>
										<td class="ColumnBackground" colspan="2">
											<div align="left">
											  &nbsp;<bean:message key="cash" />
											</div>
										 </td>
									</tr>
									<tr>
										<td class="ColumnBackground" colspan="2">
										<div align="center">
											  &nbsp;<bean:message key="bankBranch" />
											</div>
										 </td>
										<td class="ColumnBackground" width="80">
											<div align="center">
											  &nbsp;<bean:message key="city" />
											</div>
										 </td>
										<td class="ColumnBackground">
											<div align="center">
											  &nbsp;<bean:message key="drawer" />
											</div>
										 </td>
										<td class="ColumnBackground">
											<div align="center">
											  &nbsp;<bean:message key="chqNo" />
											</div>
										 </td>
										<td class="ColumnBackground">
											<div align="center">
											  &nbsp;<bean:message key="denomination" />
											</div>
										 </td>
										<td class="ColumnBackground">
											<div align="center">
											  &nbsp;<bean:message key="Amount" />
											</div>
										 </td>
									</tr>
										<bean:define id="amtObj" name="rpAllocationForm" property="instrumentAmount"/>
										<%
											double amtVal = ((Double)amtObj).doubleValue();
											String amtStr = df.format(amtVal);
										%>
									<tr>
										<td class="TableData" colspan="2">
										<div align="left">
											  &nbsp;<bean:write name="rpAllocationForm" property="collectingBank"/> &nbsp;
											  <bean:write property="collectingBankBranch" name="rpAllocationForm"/>
											</div>
										 </td>
										<td class="TableData">
											<div align="left">
											  &nbsp;
											</div>
										 </td>
										<td class="TableData">
											<div align="left">
											  &nbsp;
											</div>
										 </td>
										<td class="TableData">
											<div align="left">
											  &nbsp;<bean:write property="instrumentNo" name="rpAllocationForm"/>
											</div>
										 </td>
										<td class="TableData">
											<div align="left">
											  &nbsp;1000 x
											</div>
										 </td>
										<td class="TableData">
											<div align="right">
											  <%=amtStr%>
											</div>
										 </td>
									</tr>
									<tr>
										<td class="TableData" colspan="2">
										<div align="left">
											  &nbsp;
											</div>
										 </td>
										<td class="TableData">
											<div align="left">
											  &nbsp;
											</div>
										 </td>
										<td class="TableData">
											<div align="left">
											  &nbsp;
											</div>
										 </td>
										<td class="TableData">
											<div align="left">
											  &nbsp;
											</div>
										 </td>
										<td class="TableData">
											<div align="left">
											  &nbsp;&nbsp;&nbsp;500 x
											</div>
										 </td>
										<td class="TableData">
											<div align="left">
											  &nbsp;
											</div>
										 </td>
									</tr>
									<tr>
										<td class="TableData" colspan="2">
										<div align="left">
											  &nbsp;
											</div>
										 </td>
										<td class="TableData">
											<div align="left">
											  &nbsp;
											</div>
										 </td>
										<td class="TableData">
											<div align="left">
											  &nbsp;
											</div>
										 </td>
										<td class="TableData">
											<div align="left">
											  &nbsp;
											</div>
										 </td>
										<td class="TableData">
											<div align="left">
											  &nbsp;&nbsp;&nbsp;100 x
											</div>
										 </td>
										<td class="TableData">
											<div align="left">
											  &nbsp;
											</div>
										 </td>
									</tr>
									<tr>
										<td class="TableData" colspan="2">
										<div align="left">
											  &nbsp;
											</div>
										 </td>
										<td class="TableData">
											<div align="left">
											  &nbsp;
											</div>
										 </td>
										<td class="TableData">
											<div align="left">
											  &nbsp;
											</div>
										 </td>
										<td class="TableData">
											<div align="left">
											  &nbsp;
											</div>
										 </td>
										<td class="TableData">
											<div align="left">
											  &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;50 x
											</div>
										 </td>
										<td class="TableData">
											<div align="left">
											  &nbsp;
											</div>
										 </td>
									</tr>
									<tr>
										<td class="TableData" colspan="2">
										<div align="left">
											  &nbsp;
											</div>
										 </td>
										<td class="TableData">
											<div align="left">
											  &nbsp;
											</div>
										 </td>
										<td class="TableData">
											<div align="left">
											  &nbsp;
											</div>
										 </td>
										<td class="TableData">
											<div align="left">
											  &nbsp;
											</div>
										 </td>
										<td class="TableData">
											<div align="left">
											  &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;20 x
											</div>
										 </td>
										<td class="TableData">
											<div align="left">
											  &nbsp;
											</div>
										 </td>
									</tr>
									<tr>
										<td class="TableData" colspan="2">
										<div align="left">
											  &nbsp;
											</div>
										 </td>
										<td class="TableData">
											<div align="left">
											  &nbsp;
											</div>
										 </td>
										<td class="TableData">
											<div align="left">
											  &nbsp;
											</div>
										 </td>
										<td class="TableData">
											<div align="left">
											  &nbsp;
											</div>
										 </td>
										<td class="TableData">
											<div align="left">
											  &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;10 x
											</div>
										 </td>
										<td class="TableData">
											<div align="left">
											  &nbsp;
											</div>
										 </td>
									</tr>
									<tr>
										<td class="TableData" colspan="2">
										<div align="left">
											  &nbsp;
											</div>
										 </td>
										<td class="TableData">
											<div align="left">
											  &nbsp;
											</div>
										 </td>
										<td class="TableData">
											<div align="left">
											  &nbsp;
											</div>
										 </td>
										<td class="TableData">
											<div align="left">
											  &nbsp;
											</div>
										 </td>
										<td class="TableData">
											<div align="left">
											  &nbsp;Others
											</div>
										 </td>
										<td class="TableData">
											<div align="left">
											  &nbsp;
											</div>
										 </td>
									</tr>
									<tr>
										<td class="TableData" colspan="6">
											<div align="right">
											  &nbsp;<bean:message key="totalCaps" />
											</div>
										 </td>
										<td class="TableData">
											<div align="right">
											  <%=amtStr%>
											</div>
										 </td>
									</tr>
									</table></td></tr>
									<tr>
									<td colspan="7"><table width="661" border="0" cellspacing="1" cellpadding="1">
									<tr>
										<td class="TableData" colspan="3">
											<div align="center">
											  <br>............................................<br><bean:message key="depSign"/>
											</div>
										 </td>
										<td class="TableData" colspan="4">
											<div align="center">
											  <br>............................................<br><bean:message key="tellerSign"/>
											</div>
										 </td>

									</tr>
									</table></td>
									</tr>
							 </table>
						</td>
					</tr>


					<TR >
						<TD align="center" valign="baseline" >
							<DIV align="center">
								<A href="home.do?method=getMainMenu&menuIcon=<%=session.getAttribute("menuIcon")%>"><IMG src="images/Cancel.gif" alt="OK" width="49" height="37" border="0"></A>
								<A href="javascript:printpage()">
									<IMG src="images/Print.gif" alt="Print" width="49" height="37" border="0"></A>
								<A href="javascript:history.back()">
								<IMG src="images/Back.gif" alt="Cancel" width="49" height="37" border="0"></A>
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