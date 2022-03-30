<%@ page language="java"%>
<%@ taglib uri="/WEB-INF/TLD/struts-html.tld" prefix="html" %>
<%@ taglib uri="/WEB-INF/TLD/struts-bean.tld" prefix="bean" %>
<%@ taglib uri="/WEB-INF/TLD/struts-logic.tld" prefix="logic" %>
<% session.setAttribute("CurrentPage","showPaymentFilter.do?method=showPaymentFilter");%>
<%@ include file="/jsp/SetMenuInfo.jsp" %>
<%@ page import="com.cgtsi.actionform.RPActionForm"%>

<TABLE width="725" border="0" cellpadding="0" cellspacing="0">
	<html:errors />
	<html:form action="getPaymentList.do?method=getPaymentList" method="POST">

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
											<TD width="31%" class="Heading"><bean:message key="paymentDetails" /></TD>
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
									<TD align="left" valign="top" class="ColumnBackground">
									<DIV align="left">
										  &nbsp;From Date 
										  </DIV>
									</TD>
									   <TD  align="left" valign="center" class="TableData">
										  <DIV align="left">
										  <html:text property="fromDate" size="20" alt="From Date" name="rpAllocationForm" maxlength="10"/>
										  <IMG src="images/CalendarIcon.gif" width="20" onClick="showCalendar('rpAllocationForm.fromDate')" align="center">
										  <DIV align="left">
									  </TD>
									
									  <TD align="left" valign="top" class="ColumnBackground">
									<DIV align="left">
										  &nbsp;<font color="#FF0000" size="2">*</font>&nbsp; To Date 
										  </DIV>
									</TD>

									    <TD  align="left" valign="center" class="TableData">
										  <DIV align="left">
										  <html:text property="toDate" size="20" alt="To Date" name="rpAllocationForm" maxlength="10"/>
										  <IMG src="images/CalendarIcon.gif" width="20" onClick="showCalendar('rpAllocationForm.toDate')" align="center">
										  <DIV align="left">
									  </TD>
									  </TR>
								<tr align="left">
									<td class="ColumnBackground" colspan="2">
										<div align="left">
										  &nbsp;<font color="#FF0000" size="2">*</font>&nbsp;<bean:message key="mliID" />
										</div>
									 </td>

									<logic:equal property="bankId" value="0000" name="rpAllocationForm">
									 <td class="TableData" colspan="2">
										<div align="left">
										<html:text property="selectMember" name="rpAllocationForm" maxlength="12" onkeypress="return numbersOnly(this, event)" onkeyup="isValidNumber(this)"/>
										</div>
									</td>
									</logic:equal>
									<logic:notEqual property="bankId" value="0000" name="rpAllocationForm">
									 <td class="TableData" colspan="2">
										<div align="left">
										<bean:write property="selectMember" name="rpAllocationForm"/>
										</div>
									</td>
									</logic:notEqual>
								</tr>
								<tr align="left">
									<td class="ColumnBackground" colspan="2">
										<div align="left">
										  &nbsp;<font color="#FF0000" size="2">*</font>&nbsp;<bean:message key="dateType" />
										</div>
									 </td>
									 <td class="TableData" colspan="2">
										<div align="left">
										  <html:radio property="dateType" value="N"/> <bean:message key="instrumentDate"/><html:radio property="dateType" value="Y" name="rpAllocationForm"/> 
										  <bean:message key="paymentDate"/>
										</div>
									 </td>
								</tr>
								<tr align="left">
									<td class="ColumnBackground" colspan="2">
										<div align="left">
										  &nbsp;<font color="#FF0000" size="2">*</font>&nbsp;<bean:message key="payInSlipFormat" />
										</div>
									 </td>
									 <td class="TableData" colspan="2">
										<div align="left">
										<html:select property="payInSlipFormat" name="rpAllocationForm">
											<html:option value="IDBI">IDBI Bank </html:option>
											<html:option value="PNB">PNB </html:option>
											<html:option value="HDFC">HDFC Bank </html:option>
										</html:select>
										</div>
									 </td>
								</tr>
							 </table>
						</td>
					</tr>


					<TR >
						<TD align="center" valign="baseline" >
							<DIV align="center">
								<A href="javascript:submitForm('getPaymentList.do?method=getPaymentList')"><IMG src="images/OK.gif" alt="OK" width="49" height="37" border="0"></A>
								<a href="javascript:document.rpAllocationForm.reset()">
								<img src="images/Reset.gif" alt="Reset" width="49" height="37"       border="0"></a>
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