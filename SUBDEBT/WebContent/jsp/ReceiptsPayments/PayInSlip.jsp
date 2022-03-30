<%@ page language="java"%>
<%@ taglib uri="/WEB-INF/TLD/struts-html.tld" prefix="html" %>
<%@ taglib uri="/WEB-INF/TLD/struts-bean.tld" prefix="bean" %>
<% session.setAttribute("CurrentPage","getPaymentDetails.do?method=getPaymentDetailsForPayInSlip");%>
<%@ include file="/jsp/SetMenuInfo.jsp" %>

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
									<td class="ColumnBackground" width="207">
										<div align="left">
										  &nbsp;<bean:message key="paymentId" />
										</div>
									 </td>
									 <td class="TableData" width="343">
										<div align="left">
										  &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
										  <bean:write property="paymentId" name="rpAllocationForm"/>
										</div>
									 </td>
								</tr>
<!--								<tr align="left">
									<td class="ColumnBackground" width="207">
										<div align="left">
										  &nbsp;<bean:message key="modeOfDel" />
										</div>
									 </td>
									 <td class="TableData" width="343">
										<div align="left">
										  &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
										  <bean:write property="modeOfDelivery" name="rpAllocationForm"/>
										</div>
									 </td>
								</tr>-->
								<tr align="left">
									<td class="ColumnBackground" width="207">
										<div align="left">
										  &nbsp;<bean:message key="modeOfPayment" />
										</div>
									 </td>
									 <td class="TableData" width="343">
										<div align="left">
										  &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
										  <bean:write property="modeOfPayment" name="rpAllocationForm"/>
										</div>
									 </td>
								</tr>

								<tr align="left">
									<td class="ColumnBackground" width="207">
										<div align="left">
										  &nbsp;<bean:message key="instrumentNumber" />
										</div>
									 </td>
									 <td class="TableData" width="343">
										<div align="left">
										  &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
										  <bean:write property="instrumentNo" name="rpAllocationForm"/>
										</div>
									 </td>
								</tr>
<!--								<tr align="left">
									<td class="ColumnBackground" width="207">
										<div align="left">
										  &nbsp;<bean:message key="instrumentType" />
										</div>
									 </td>
									 <td class="TableData" width="343">
										<div align="left">
										  &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
										  <bean:write property="instrumentType" name="rpAllocationForm"/>
										</div>
									 </td>
								</tr>-->

								<tr align="left">
									<td class="ColumnBackground" width="207">
										<div align="left">
										  &nbsp;<bean:message key="instrumentDate" />
										</div>
									 </td>
									 <td class="TableData" width="343">
										<div align="left">
										  &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
										  <bean:write property="instrumentDate" name="rpAllocationForm"/>
										</div>
									 </td>
								</tr>
								<tr align="left">
									<td class="ColumnBackground" width="207">
										<div align="left">
										  &nbsp;<bean:message key="instrumentAmount" />
										</div>
									 </td>
									 <td class="TableData" width="343">
										<div align="left">
										  &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
										  <bean:write property="instrumentAmount" name="rpAllocationForm"/>&nbsp;&nbsp;&nbsp;<bean:message key="inRs" />
										</div>
									 </td>
								</tr>
								<tr align="left">
									<td class="ColumnBackground" width="207">
										<div align="left">
										  &nbsp;<bean:message key="payableAt" />
										</div>
									 </td>
									 <td class="TableData" width="343">
										<div align="left">
										  &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
										  <bean:write property="payableAt" name="rpAllocationForm"/>
										</div>
									 </td>
								</tr>
								<tr align="left">
									<td class="ColumnBackground" width="207">
										<div align="left">
										  &nbsp;<bean:message key="drawnAtBranch" />
										</div>
									 </td>
									 <td class="TableData" width="343">
										<div align="left">
										  &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
										  <bean:write property="drawnAtBranch" name="rpAllocationForm"/>
										</div>
									 </td>
								</tr>

								<tr align="left">
									<td class="ColumnBackground" width="207">
										<div align="left">
										  &nbsp;<bean:message key="drawnAtBank" />
										</div>
									 </td>
									 <td class="TableData" width="343">
										<div align="left">
										  &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
										  <bean:write property="drawnAtBank" name="rpAllocationForm"/>
										</div>
									 </td>
								</tr>
								<tr align="left">
									<td class="ColumnBackground" width="207">
										<div align="left">
										  &nbsp;<bean:message key="collectingBank" />
										</div>
									 </td>
									 <td class="TableData" width="343">
										<div align="left">
										  &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
										  <bean:write property="collectingBank" name="rpAllocationForm"/>
										</div>
									 </td>
								</tr>
								<tr align="left">
									<td class="ColumnBackground" width="207">
										<div align="left">
										  &nbsp;<bean:message key="collectingBankBranch" />
										</div>
									 </td>
									 <td class="TableData" width="343">
										<div align="left">
										  &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
										  <bean:write property="collectingBankBranch" name="rpAllocationForm"/>
										</div>
									 </td>
								</tr>

								<tr align="left">
									<td class="ColumnBackground" width="207">
										<div align="left">
										  &nbsp;<bean:message key="paymentDate" />
										</div>
									 </td>
									 <td class="TableData" width="343">
										<div align="left">
										  &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
										  <bean:write property="paymentDate" name="rpAllocationForm"/>
										</div>
									 </td>
								</tr>

							 </table>
						</td>
					</tr>


					<TR >
						<TD align="center" valign="baseline" >
							<DIV align="center">
								<A href="home.do?method=getMainMenu&menuIcon=<%=session.getAttribute("menuIcon")%>"><IMG src="images/OK.gif" alt="OK" width="49" height="37" border="0"></A>
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