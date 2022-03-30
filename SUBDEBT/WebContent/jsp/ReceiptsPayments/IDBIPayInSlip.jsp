<%@ page language="java"%>
<%@ taglib uri="/WEB-INF/TLD/struts-html.tld" prefix="html" %>
<%@ taglib uri="/WEB-INF/TLD/struts-bean.tld" prefix="bean" %>
<% session.setAttribute("CurrentPage","getPaymentDetails.do?method=getPaymentDetailsForPayInSlip");%>
<%@ include file="/jsp/SetMenuInfo.jsp" %>
<%@ page import="java.text.DecimalFormat"%>
<%
DecimalFormat df=new DecimalFormat("######################");
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
									<TD colspan="7"><IMG src="images/idbilogo.gif" width="661"></TD>
								</tr>
								<tr>
								<td colspan="7"><table width="661" border="1" cellspacing="1" cellpadding="1">
								<tr align="left">
									<td class="ColumnBackground">
										<div align="left">
										  &nbsp;<bean:message key="branch" />
										</div>
									 </td>
									 <td class="TableData">
										<div align="left">
										  &nbsp;
										  <bean:write property="collectingBankBranch" name="rpAllocationForm"/>
										</div>
									 </td>
									<td class="ColumnBackground">
										<div align="left">
										  &nbsp;<bean:message key="panNo" />
										</div>
									 </td>
									 <td class="TableData">
										<div align="left">
										  ___________________
										</div>
									 </td>
									<td class="ColumnBackground">
										<div align="left">
										  &nbsp;<bean:message key="date" />
										</div>
									 </td>
									 <td class="TableData">
										<div align="left">
										  <bean:write property="paymentDate" name="rpAllocationForm"/>
										</div>
									 </td>
								</tr>
								</table></td></tr>
								<tr>
									<td colspan="7">
									<table width="661" border="1" cellspacing="1" cellpadding="1">
									<tr>
										<td class="ColumnBackground" colspan="2">
											<div align="left">
											  &nbsp;<bean:message key="acName" />
											</div>
										 </td>
										<td class="ColumnBackground" colspan="2">CGTSI

										 </td>
										<td class="ColumnBackground">
											<div align="left">
											  &nbsp;<bean:message key="acNum" />
											</div>
										 </td>
										<td class="ColumnBackground" colspan="2">
										<bean:write property="accountNumber" name="rpAllocationForm"/>
										 </td>
									</tr>
									<tr>
										<td class="ColumnBackground" width="10">

										 </td>
										<td class="ColumnBackground">
											<div align="left">
											  &nbsp;<bean:message key="draweeBank" />
											</div>
										 </td>
										<td class="ColumnBackground">
											<div align="left">
											  &nbsp;<bean:message key="branchCaps" />
											</div>
										 </td>
										<td class="ColumnBackground">
											<div align="left">
											  &nbsp;<bean:message key="chqDraft" />
											</div>
										 </td>
										<td class="ColumnBackground">
											<div align="left">
											  &nbsp;<bean:message key="cashDetails" />
											</div>
										 </td>
										<td class="ColumnBackground">
											<div align="left">
											  &nbsp;<bean:message key="rupeesCaps" />
											</div>
										 </td>
										<td class="ColumnBackground">
											<div align="left">
											  &nbsp;<bean:message key="paise" />
											</div>
										 </td>
									</tr>
									<tr>
										<td class="TableData"> 1

										 </td>
										<td class="TableData">
											<div align="left">
											  &nbsp;<bean:write property="drawnAtBank" name="rpAllocationForm"/>
											</div>
										 </td>
										<td class="TableData">
											<div align="left">
											  &nbsp;<bean:write property="drawnAtBranch" name="rpAllocationForm"/>
											</div>
										 </td>
										<td class="TableData">
											<div align="left">
											  &nbsp;<bean:write property="instrumentNo" name="rpAllocationForm"/>
											</div>
										 </td>
										<td class="TableData">
											<div align="left">
											  &nbsp;Rs. 1000 &nbsp; x&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;=
											</div>
										 </td>
										<bean:define id="amtObj" name="rpAllocationForm" property="instrumentAmount"/>
										<%
											double amtVal = ((Double)amtObj).doubleValue();
											String amtStr = df.format(amtVal);
										%>
										<td class="TableData">
											<div align="right">
											  <%=amtStr%>
											</div>
										 </td>
										<td class="TableData">
											<div align="right">
											  00
											</div>
										 </td>
									</tr>
									<tr>
										<td class="TableData"> 2

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
											  &nbsp;Rs. 500 &nbsp;&nbsp;&nbsp; x&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;=
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
									</tr>
									<tr>
										<td class="TableData"> 3

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
											  &nbsp;Rs. 100 &nbsp;&nbsp;&nbsp; x&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;=
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
									</tr>
									<tr>
										<td class="TableData"> 4

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
											  &nbsp;Rs. 50 &nbsp;&nbsp;&nbsp;&nbsp;&nbsp; x&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;=
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
									</tr>
									<tr>
										<td class="TableData"> 5

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
											  &nbsp;Rs. 20 &nbsp;&nbsp;&nbsp;&nbsp;&nbsp; x&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;=
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
									</tr>
									<tr>
										<td class="TableData"> 6

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
											  &nbsp;Rs. 10 &nbsp;&nbsp;&nbsp;&nbsp;&nbsp; x&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;=
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
									</tr>
									<tr>
										<td class="TableData"> 7

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
											  &nbsp;Rs. 5 &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp; x&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;=
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
									</tr>
									<tr>
										<td class="TableData"> 8

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
											  &nbsp;Rs. 2 &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp; x&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;=
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
									</tr>
									<tr>
										<td class="TableData"> 9

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
											  &nbsp;Coins &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp; x&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;=
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
									</tr>
									<tr>
										<td class="TableData"> 

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
											<div align="center">
											  <bean:message key="totalCaps" />
											</div>
										 </td>
										<td class="TableData">
											<div align="right">
											  <%=amtStr%>
											</div>
										 </td>
										<td class="TableData">
											<div align="right">
											  00
											</div>
										 </td>
									</tr>
									</table>
									</td>
								</tr>
								<tr>
								<td colspan="7"><table width="661" border="1" cellspacing="1" cellpadding="1">
									<tr >
										<td class="TableData" rowspan="2" colspan="5">
										<bean:message key="rsInWords"/>______________________________________________________ <br><br>___________________________________________________________________________
										</td>
										<td class="TableData" colspan="2">
										<bean:message key="signature"/>______________________
										</td>
									</tr>
									<tr>

										<td class="TableData" colspan="2">
										<bean:message key="bankOffSign"/>_______________________
										</td>
									</tr>
								</table></td></tr>
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