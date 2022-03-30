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
								<TD colspan="12">
									<TABLE width="100%" border="0" cellpadding="0" cellspacing="0">
										<TR>
											<TD width="31%" class="Heading"><bean:message key="payInSlipDetail" /></TD>
											<TD><IMG src="images/TriangleSubhead.gif" width="19" height="19"></TD>
										</TR>
										<TR>
											<TD colspan="12" class="Heading"><IMG src="images/Clear.gif" width="5" height="5"></TD>
										</TR>
									</TABLE>
								</TD>
							</TR>
							</table>

							<bean:define id="amtObj" name="rpAllocationForm" property="instrumentAmount"/>
							<%
								double amtVal = ((Double)amtObj).doubleValue();
								String amtStr = df.format(amtVal);
							%>

							<table border="1" cellspacing="1" cellpadding="1">
								<tr>
								<td ><table width="100" border="1" cellspacing="1" cellpadding="1">
									<tr>
										<td class="ColumnBackground">
										<div align="right">
										  <IMG src="images/pnblogo.jpg" width="200"/><br>
										</div>
										 </td>
									</tr>
								<tr>
									<td class="TableData">
										<div align="right">
										  &nbsp; __________19&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
										</div>
									 </td>
								</tr>
									<tr>
										 <td class="ColumnBackground">
											<div align="left">
											  <br>&nbsp; <bean:message key="sfAcNo"/>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
											  <bean:write property="accountNumber" name="rpAllocationForm"/><br>
											</div>
										 </td>
									</tr>
									<tr>
										<td class="ColumnBackground">
											<div align="left">
											  <br>&nbsp;<bean:message key="paidInCredit" />&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;CGTSI<br>
											</div>
										 </td>
									</TR>
									<tr>
										 <td class="ColumnBackground">
											<div align="left">
											  <bean:message key="amtInWords" />&nbsp;________________________________<br><br>
											  ________________________________________
											</div>
										 </td>
									</tr>
									<tr>
										<td class="TableData"><table width="100%" border="1" cellspacing="1" cellpadding="1">
										<tr>
											<td class="TableData" rowspan="3">
											<bean:message key="asPerDetailsOverleaf"/>
											</td>
										</tr>
										<tr>
											<td class="ColumnBackground" width="70">
											<bean:message key="rs"/>
											</td>
											<td class="ColumnBackground" width="10">
											<bean:message key="paise"/>
											</td>
										</tr>
										<tr>
											<td class="ColumnBackground">
											<div align="right"><br><%=amtStr%></div></td>
											<td class="ColumnBackground"><div align="right"><br> 00</div></td>
										</tr>
										</table></td>
									</tr>
									<tr>
										 <td class="ColumnBackground">
											<div align="left">
											  <br>&nbsp;<bean:message key="receivingOfficial" />&nbsp;_____________________________
											</div>
										 </td>
									</tr>
								</table></td>
								<td><table width="630" border="1" cellspacing="1" cellpadding="1">
								<tr>
									<td class="ColumnBackground">
										<div align="right">
										  &nbsp; <bean:message key="sfAcNo"/>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
										  <bean:write property="accountNumber" name="rpAllocationForm"/>
										</div>
									 </td>
								</tr>
								<tr align="left">
									<td class="ColumnBackground">
										<div align="right">
										  <IMG src="images/pnblogo.jpg" width="200"/>
										</div>
									 </td>
								</tr>
								<tr>
									<td class="ColumnBackground">
										<div align="right">
										  &nbsp; __________19&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
										</div>
									 </td>
								</tr>
								<tr>
									<td class="ColumnBackground">
										<div align="left">
										  &nbsp;<bean:message key="paidInCredit" />&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;CGTSI
										</div>
									 </td>
								</tr>
								<tr>
									<td class="ColumnBackground">
										<div align="left">
										  &nbsp;<bean:message key="address"/> &nbsp; _________________________________________________________________________
										</div>
									 </td>
								</tr>
								<tr>
									 <td class="ColumnBackground">
										<div align="left">
										  <bean:message key="amtInWords"/> ______________________________________________________<br><br>
										  ______________________________________________________________________________&nbsp;
										  <bean:message key="onRealisation" />
										</div>
									 </td>
								</tr>
									<tr>
										<td class="TableData"><table width="100%" border="1" cellspacing="1" cellpadding="1">
										<tr>
											<td class="TableData" rowspan="3">
											<bean:message key="asPerDetailsOverleaf"/>
											</td>
										</tr>
										<tr>
											<td class="ColumnBackground" width="70">
											<bean:message key="rs"/>
											</td>
											<td class="ColumnBackground" width="10">
											<bean:message key="paise"/>
											</td>
										</tr>
										<tr>
											<td class="ColumnBackground">
											<div align="right"><br><%=amtStr%></div></td>
											<td class="ColumnBackground"><div align="right"><br> 00</div></td>
										</tr>
										</table></td>
									</tr>
								<tr>
									 <td class="ColumnBackground">
										<div align="right"><br><br>
										  <bean:message key="by" />_____________________________<br>
										  &nbsp;<bean:message key="receivingOfficial" />&nbsp;______________________
										  &nbsp;<bean:message key="passingOfficial" />&nbsp;_______________________
										  &nbsp;<bean:message key="sign" />
										</div>
									 </td>
								</tr>
								</table></td>
								</tr>

							 </table>

							 <br><br>

							<table border="1" cellspacing="1" cellpadding="1">
								<tr>
								<td ><table width="270" border="1" cellspacing="1" cellpadding="1">
								<tr>
									<td class="ColumnBackground">
										<div align="center">
										  <bean:message key="draweeBankBranch"/>
										</div>
									 </td>
									<td class="ColumnBackground">
										<div align="center">
										  <bean:message key="chqNo"/>
										</div>
									 </td>
									<td class="ColumnBackground" width="70">
										<div align="center">
										  <bean:message key="rs"/>
										</div>
									 </td>
									<td class="ColumnBackground">
										<div align="center">
										  <bean:message key="paise"/>
										</div>
									 </td>
								</tr>
								<tr>
									<td class="TableData">
										<div align="center">
										  <bean:write property="drawnAtBank" name="rpAllocationForm"/>&nbsp;
										  <bean:write property="drawnAtBranch" name="rpAllocationForm"/>
										</div>
									 </td>
									<td class="TableData">
										<div align="center">
										  <bean:write property="instrumentNo" name="rpAllocationForm"/>
										</div>
									 </td>
									<td class="TableData" width="70">
										<div align="center">
										<div align="right">&nbsp;
										<%=amtStr%>
										</div>
									 </td>
									<td class="TableData">
										<div align="right">00
										</div>
									 </td>
								</tr>
								<tr>
									<td class="TableData">
										<div align="center">&nbsp;
										</div>
									 </td>
									<td class="TableData">
										<div align="center">&nbsp;
										</div>
									 </td>
									<td class="TableData" width="70">
										<div align="center">&nbsp;
										</div>
									 </td>
									<td class="TableData">
										<div align="center">&nbsp;
										</div>
									 </td>
								</tr>
								<tr>
									<td class="TableData">
										<div align="center">&nbsp;
										</div>
									 </td>
									<td class="TableData">
										<div align="center">&nbsp;
										</div>
									 </td>
									<td class="TableData" width="70">
										<div align="center">&nbsp;
										</div>
									 </td>
									<td class="TableData">
										<div align="center">&nbsp;
										</div>
									 </td>
								</tr>
								<tr>
									<td class="TableData">
										<div align="center">&nbsp;
										</div>
									 </td>
									<td class="TableData">
										<div align="center">&nbsp;
										</div>
									 </td>
									<td class="TableData" width="70">
										<div align="center">&nbsp;
										</div>
									 </td>
									<td class="TableData">
										<div align="center">&nbsp;
										</div>
									 </td>
								</tr>
								<tr>
									<td class="TableData">
										<div align="center">&nbsp;
										</div>
									 </td>
									<td class="TableData">
										<div align="center">&nbsp;
										</div>
									 </td>
									<td class="TableData" width="70">
										<div align="center">&nbsp;
										</div>
									 </td>
									<td class="TableData">
										<div align="center">&nbsp;
										</div>
									 </td>
								</tr>
								<tr>
									<td class="TableData">
										<div align="center">&nbsp;
										</div>
									 </td>
									<td class="TableData">
										<div align="center">&nbsp;
										</div>
									 </td>
									<td class="TableData" width="70">
										<div align="center">&nbsp;
										</div>
									 </td>
									<td class="TableData">
										<div align="center">&nbsp;
										</div>
									 </td>
								</tr>
								<tr>
									<td class="TableData">
										<div align="center">&nbsp;
										</div>
									 </td>
									<td class="TableData">
										<div align="center">&nbsp;
										</div>
									 </td>
									<td class="TableData" width="70">
										<div align="center">&nbsp;
										</div>
									 </td>
									<td class="TableData">
										<div align="center">&nbsp;
										</div>
									 </td>
								</tr>
								<tr>
									<td class="TableData">
										<div align="center">&nbsp;
										</div>
									 </td>
									<td class="TableData">
										<div align="center">&nbsp;
										</div>
									 </td>
									<td class="TableData" width="70">
										<div align="center">&nbsp;
										</div>
									 </td>
									<td class="TableData">
										<div align="center">&nbsp;
										</div>
									 </td>
								</tr>
								</table></td>
								<td><table width="630" border="1" cellspacing="1" cellpadding="1">
								<tr>
									<td class="ColumnBackground">
										<div align="center">
										  <br><bean:message key="draweeBankBranch"/>
										</div>
									 </td>
									<td class="ColumnBackground">
										<div align="center">
										  <br><bean:message key="chqNo"/>
										</div>
									 </td>
									<td class="ColumnBackground" width="70">
										<div align="center">
										  <br><bean:message key="rs"/>
										</div>
									 </td>
									<td class="ColumnBackground" width="30">
										<div align="center">
										  <br><bean:message key="paise"/>
										</div>
									 </td>
								</tr>
								<tr>
									<td class="TableData">
										<div align="center">
										  <bean:write property="drawnAtBank" name="rpAllocationForm"/>&nbsp;
										  <bean:write property="drawnAtBranch" name="rpAllocationForm"/>
										</div>
									 </td>
									<td class="TableData">
										<div align="center">
										  <bean:write property="instrumentNo" name="rpAllocationForm"/>
										</div>
									 </td>
									<td class="TableData" width="70">
										<div align="right">&nbsp;
										<%=amtStr%>
										</div>
									 </td>
									<td class="TableData" width="30">
										<div align="right">00
										</div>
									 </td>
								</tr>
								<tr>
									<td class="TableData">
										<div align="center">&nbsp;
										</div>
									 </td>
									<td class="TableData">
										<div align="center">&nbsp;
										</div>
									 </td>
									<td class="TableData" width="70">
										<div align="center">&nbsp;
										</div>
									 </td>
									<td class="TableData" width="30">
										<div align="center">&nbsp;
										</div>
									 </td>
								</tr>
								<tr>
									<td class="TableData">
										<div align="center">&nbsp;
										</div>
									 </td>
									<td class="TableData">
										<div align="center">&nbsp;
										</div>
									 </td>
									<td class="TableData" width="70">
										<div align="center">&nbsp;
										</div>
									 </td>
									<td class="TableData" width="30">
										<div align="center">&nbsp;
										</div>
									 </td>
								</tr>
								<tr>
									<td class="TableData">
										<div align="center">&nbsp;
										</div>
									 </td>
									<td class="TableData">
										<div align="center">&nbsp;
										</div>
									 </td>
									<td class="TableData" width="70">
										<div align="center">&nbsp;
										</div>
									 </td>
									<td class="TableData" width="30">
										<div align="center">&nbsp;
										</div>
									 </td>
								</tr>
								<tr>
									<td class="TableData">
										<div align="center">&nbsp;
										</div>
									 </td>
									<td class="TableData">
										<div align="center">&nbsp;
										</div>
									 </td>
									<td class="TableData" width="70">
										<div align="center">&nbsp;
										</div>
									 </td>
									<td class="TableData" width="30">
										<div align="center">&nbsp;
										</div>
									 </td>
								</tr>
								<tr>
									<td class="TableData">
										<div align="center">&nbsp;
										</div>
									 </td>
									<td class="TableData">
										<div align="center">&nbsp;
										</div>
									 </td>
									<td class="TableData" width="70">
										<div align="center">&nbsp;
										</div>
									 </td>
									<td class="TableData" width="30">
										<div align="center">&nbsp;
										</div>
									 </td>
								</tr>
								<tr>
									<td class="TableData">
										<div align="center">&nbsp;
										</div>
									 </td>
									<td class="TableData">
										<div align="center">&nbsp;
										</div>
									 </td>
									<td class="TableData" width="70">
										<div align="center">&nbsp;
										</div>
									 </td>
									<td class="TableData" width="30">
										<div align="center">&nbsp;
										</div>
									 </td>
								</tr>
								<tr>
									<td class="TableData">
										<div align="center">&nbsp;
										</div>
									 </td>
									<td class="TableData">
										<div align="center">&nbsp;
										</div>
									 </td>
									<td class="TableData" width="70">
										<div align="center">&nbsp;
										</div>
									 </td>
									<td class="TableData" width="30">
										<div align="center">&nbsp;
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