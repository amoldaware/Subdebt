<%@ page language="java"%>
<%@ taglib uri="/WEB-INF/TLD/struts-html.tld" prefix="html" %>
<%@ taglib uri="/WEB-INF/TLD/struts-bean.tld" prefix="bean" %>
<%@ taglib uri="/WEB-INF/TLD/struts-logic.tld" prefix="logic" %>
<%@ include file="/jsp/SetMenuInfo.jsp" %>
<% 
	session.setAttribute("CurrentPage","showFundTransfer.do?method=showFundTransfer");
	String focusObj="counter";
	org.apache.struts.action.ActionErrors errors = (org.apache.struts.action.ActionErrors)request.getAttribute(org.apache.struts.Globals.ERROR_KEY);
	if (errors!=null && !errors.isEmpty())
	{
		focusObj="test";
	}
%>

<body onload="displayBalanceFundTransfer()">
<html:form action="updateFundTransfer.do?method=updateFundTransfer" method="POST" focus="<%=focusObj%>">
<html:hidden name="investmentForm" property="test"/>
<html:errors />
	<TABLE width="100%" border="0" cellspacing="0" cellpadding="0">
		<TR>
			<TD>
				<TABLE width="100%" border="0" cellspacing="0" cellpadding="0" align="center">
					<TR>
							<TD width="20" align="right" valign="bottom"><IMG src="images/TableLeftTop.gif" width="20" height="31"></TD>
							<TD background="images/TableBackground1.gif"><img src="images/InvestmentManagementHeading.gif" width="169"
      							height="25">
      						</TD>
							<TD width="20" align="left" valign="bottom"><IMG src="images/TableRightTop.gif" width="23" height="31"></TD>
					</TR>
					<TR>
						<TD width="20" background="images/TableVerticalLeftBG.gif">&nbsp;</TD>
						<TD width="780">
							<TABLE width="100%" border="0" cellspacing="1" cellpadding="0">
							<tr>
							  <TD>			
									<DIV align="right">			
										<A HREF="javascript:submitForm('helpFundTransfer.do')">
										HELP</A>
									</DIV>
								</td>
							  </tr>

								<TR>
									<TD>
										<TABLE width="100%" border="0" cellspacing="0" cellpadding="0">
											<TR>
												<TD width="40%" class="Heading"><bean:message key="fundTransferHeading" /> &nbsp; for <bean:write name="investmentForm" property="statementDate"/></TD>
												<TD><IMG src="images/TriangleSubhead.gif" width="19" height="19"></TD>
											</TR>
											<TR>
												<TD colspan="3" class="Heading"><IMG src="images/Clear.gif" width="5" height="5"></TD>
											</TR>
										</TABLE>
									</TD>
								</TR>
								<TR>
									<TD>
										<TABLE width="100%" border="0" cellspacing="1" cellpadding="0">
											<tr>
											<td class="SubHeading" colspan="12">
											<bean:message key="fundTransferDetails"/></td>
											</tr>
											<TR>

												<TD align="center" valign="top" class="ColumnBackground">
												<div align="center">
													&nbsp;&nbsp;<bean:message key="SlNo" /> &nbsp;(1)
													</div>
												</TD>
												<TD align="left" valign="top" class="ColumnBackground">
												<div align="center">
													&nbsp;&nbsp;<bean:message key="BankName" /> &nbsp;(2)
													</div>
												</TD>
												<TD align="left" valign="top" class="ColumnBackground">
												<div align="center">
													&nbsp;&nbsp;<bean:message key="acNum" /> &nbsp;(3)
													</div>
												</TD>
												<TD align="left" valign="top" class="ColumnBackground">
												<div align="center">
													&nbsp;&nbsp;<bean:message key="closingBalanceDate" /> &nbsp;(4)
													</div>
												</TD>
												<TD align="left" valign="top" class="ColumnBackground">
												<div align="center">
													&nbsp;&nbsp;<bean:message key="balanceAsPerStmt" /><br>&nbsp;(5)<br>
													&nbsp;<bean:message key="inRs" />
													</div>
												</TD>
												<TD align="left" valign="top" class="ColumnBackground">
												<div align="center">
													&nbsp;&nbsp;<bean:message key="CreditFutureDate" /><br>&nbsp;(6)<br>
													&nbsp;<bean:message key="inRs" />
													</div>
												</TD>
												<TD align="left" valign="top" class="ColumnBackground">
												<div align="center">
													&nbsp;&nbsp;<bean:message key="balanceAvail" /><br>&nbsp;(7)=(5)-(6)
													&nbsp;<bean:message key="inRs" />
													</div>
												</TD>
												<TD align="left" valign="top" class="ColumnBackground">
												<div align="center">
													&nbsp;&nbsp;<bean:message key="minbalance" /><br>&nbsp;(8)<br>
													&nbsp;<bean:message key="inRs" />
													</div>
												</TD>
												<TD align="left" valign="top" class="ColumnBackground">
												<div align="center">
													&nbsp;&nbsp;<bean:message key="AmtTransferred" /><br>&nbsp;(9)<br>
													&nbsp;<bean:message key="inRs" />
													</div>
												</TD>
												<TD align="left" valign="top" class="ColumnBackground">
												<div align="center">
													&nbsp;&nbsp;<bean:message key="BalanceAvailableForTransfer" /><br>&nbsp;(10)=(7)-(8)-(9)<br>
													&nbsp;<bean:message key="inRs" />
													</div>
												</TD>
												<TD align="left" valign="top" class="ColumnBackground">
												<div align="center">
													&nbsp;&nbsp;<bean:message key="exposureInvestment" /><br>&nbsp;(11)
												</TD>
												<TD align="left" valign="top" class="ColumnBackground">
												<div align="center">
													&nbsp;&nbsp;<bean:message key="remarks" /><br>&nbsp;(12)
												</div>
												</TD>
											</TR>
											<% 
												int counter=0; 

												String ftIdKey=null;
												String ftBankNameKey=null;
												String ftAccNumKey=null;
												String ftStmtBalanceKey=null;
												String ftUnclearedBalKey=null;
												String ftBalanceAvailKey=null;
												String ftMinBalanceKey=null;
												String ftAmtCAKey=null;
												String ftBalanceForIDBIKey=null;
												String ftAvailForInvst=null;
												String ftRemarksKey=null;
												String ftClosingBalDateKey=null;
											%>

											<logic:iterate id="ft" name="investmentForm" property="fundTransfers">

												<%
													ftIdKey="fundTransfers(key-"+counter+").id";
													ftBankNameKey="fundTransfers(key-"+counter+").bankName";
													ftAccNumKey="fundTransfers(key-"+counter+").accNumber";
													ftClosingBalDateKey="fundTransfers(key-"+counter+").closingBalanceDate";
													ftStmtBalanceKey="fundTransfers(key-"+counter+").balanceAsPerStmt";
													ftUnclearedBalKey="fundTransfers(key-"+counter+").unclearedBalance";
													ftBalanceAvailKey="fundTransfers(key-"+counter+").balanceUtil";
													ftMinBalanceKey="fundTransfers(key-"+counter+").minBalance";
													ftAmtCAKey="fundTransfers(key-"+counter+").amtCANotReflected";
													ftBalanceForIDBIKey="fundTransfers(key-"+counter+").amtForIDBI";
													ftAvailForInvst="fundTransfers(key-"+counter+").availForInvst";
													ftRemarksKey="fundTransfers(key-"+counter+").remarks";
												%>
											<TR>
												<TD class="tableData">
												<%=counter+1%><html:hidden name="investmentForm" property="<%=ftIdKey%>"/>
												</TD>
												<TD align="left" valign="top" class="tableData">
													<bean:write name="investmentForm" property="<%=ftBankNameKey%>"/>
													<html:hidden name="investmentForm" property="<%=ftBankNameKey%>"/>
												</TD>
												<TD align="left" valign="top" class="tableData">
													<bean:write name="investmentForm" property="<%=ftAccNumKey%>"/>
													<html:hidden name="investmentForm" property="<%=ftAccNumKey%>"/>
												</TD>
												<TD align="left" valign="top" class="tableData">
													<html:text property="<%=ftClosingBalDateKey%>" size="15" name="investmentForm" maxlength="10" onkeypress="return dateOnly(this, event)" onkeyup="isValidDate(this)"/>
												</TD>
												<TD align="left" valign="top" class="tableData">
													<html:text property="<%=ftStmtBalanceKey%>" size="15" name="investmentForm" maxlength="16"  onkeypress="return decimalOnly(this, event,13)" onkeyup="isValidDecimal(this)" onblur="displayBalanceFundTransfer()"/>
												</TD>
												<TD align="left" valign="top" class="tableData">
													<html:text property="<%=ftUnclearedBalKey%>" size="15" name="investmentForm" maxlength="16"  onkeypress="return decimalOnly(this, event,13)" onkeyup="isValidDecimal(this)" onblur="displayBalanceFundTransfer()"/>
												</TD>
												<TD align="left" valign="top" class="tableData" id="<%=ftBalanceAvailKey%>">
													<bean:write name="investmentForm" property="<%=ftBalanceAvailKey%>"/>
													<html:hidden name="investmentForm" property="<%=ftBalanceAvailKey%>"/>
												</TD>
												<TD align="left" valign="top" class="tableData">
													<bean:write name="investmentForm" property="<%=ftMinBalanceKey%>"/>
													<html:hidden name="investmentForm" property="<%=ftMinBalanceKey%>"/>
												</TD>
												<TD align="left" valign="top" class="tableData">
													<html:text property="<%=ftAmtCAKey%>" size="15" name="investmentForm" maxlength="16"  onkeypress="return decimalOnly(this, event,13)" onkeyup="isValidDecimal(this)" onblur="displayBalanceFundTransfer()"/>
												</TD>
												<TD align="left" valign="top" class="tableData" id="<%=ftBalanceForIDBIKey%>">
													<bean:write name="investmentForm" property="<%=ftBalanceForIDBIKey%>"/>
													<html:hidden name="investmentForm" property="<%=ftBalanceForIDBIKey%>"/>
												</TD>
												<TD class="tableData">
													<html:radio name="investmentForm" value="Y" property="<%=ftAvailForInvst%>"  onclick="displayBalanceFundTransfer()"> Yes </html:radio>
													<html:radio name="investmentForm" value="N" property="<%=ftAvailForInvst%>" onclick="displayBalanceFundTransfer()"> No </html:radio>
												</TD>
												<TD align="left" valign="top" class="tableData">
													<html:textarea property="<%=ftRemarksKey%>" cols="25" rows="3" name="investmentForm" onblur="displayBalanceFundTransfer()"/>
												</TD>
											</TR>


											<%
											if (counter==1)
											{
											%>
											<html:hidden name="investmentForm" property="counter"/>
											<%
											}
											counter++;
											%>
											</logic:iterate>

											<TR>
												<TD class="ColumnBackground" colspan="9">
													<bean:message key="total" />
												</TD>
												<TD class="tableData" colspan="12" id="totalFundTransfer">

												</TD>
											</TR>
										</TABLE>
									</TD>
								</TR>
								<TR>
									<TD><img src="clear.gif" width="5" height="15">
									</TD>
								</TR>
								<TR align="center" valign="baseline">
									<TD>
									<a href="javascript:submitForm('updateFundTransfer.do?method=updateFundTransfer');"><img src="images/Save.gif" alt="Save"
										width="49" height="37" border="0"></a>
									<a href="javascript:document.investmentForm.reset()">
										<img src="images/Reset.gif" alt="Reset" width="49" height="37"
										border="0"></a>
								<a href="home.do?method=getMainMenu&menuIcon=<%=session.getAttribute("menuIcon")%>">
								<IMG src="images/Cancel.gif" alt="Cancel" width="49" height="37" border="0"></A>

									</TD>
								</TR>
							</TABLE>
						</TD>
						<TD background="images/TableVerticalRightBG.gif">
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
				</TABLE>
			</TD>
		</TR>
	</TABLE>
</html:form>
</body>