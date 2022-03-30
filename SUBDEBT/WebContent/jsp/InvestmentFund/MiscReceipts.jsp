
<%@ page language="java"%>
<%@ taglib uri="/WEB-INF/TLD/struts-html.tld" prefix="html" %>
<%@ taglib uri="/WEB-INF/TLD/struts-bean.tld" prefix="bean" %>
<%@ taglib uri="/WEB-INF/TLD/struts-logic.tld" prefix="logic" %>
<%@ include file="/jsp/SetMenuInfo.jsp" %>
<%@ page import="com.cgtsi.util.SessionConstants"%>
<%@ page import="com.cgtsi.investmentfund.MiscReceipts"%>
<% 
	session.setAttribute("CurrentPage","showMiscReceipts.do?method=showMiscReceipts");
	String focusObj="counter";
	org.apache.struts.action.ActionErrors errors = (org.apache.struts.action.ActionErrors)request.getAttribute(org.apache.struts.Globals.ERROR_KEY);
	if (errors!=null && !errors.isEmpty())
	{
		focusObj="test";
	}
%>
<body onload="displayMiscReceiptsTotal()">
<html>
<html:form action="insertMiscReceipts.do?method=insertMiscReceipts" method="POST" focus="<%=focusObj%>">
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
										<A HREF="javascript:submitForm('helpMiscReceipts.do')">
										HELP</A>
									</DIV>
								</td>
							  </tr>

								<TR>
									<TD>
										<TABLE width="100%" border="0" cellspacing="0" cellpadding="0">
											<TR>
												<TD width="40%" class="Heading"><bean:message key="MiscReceiptsHeading" /> &nbsp; for <bean:write name="investmentForm" property="miscReceiptsDate"/></TD>
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
											<TR>

												<TD align="left" valign="top" class="ColumnBackground">
													&nbsp;&nbsp;<bean:message key="SlNo" />
												</TD>
												<TD align="left" valign="top" class="ColumnBackground">
													&nbsp;&nbsp;<bean:message key="SourceOfFund" />
												</TD>
												<TD align="left" valign="top" class="ColumnBackground">
													&nbsp;&nbsp;<bean:message key="DateOfInst" />
												</TD>
												<TD align="left" valign="top" class="ColumnBackground">
													&nbsp;&nbsp;<bean:message key="InstNumber" />
												</TD>
												<TD align="left" valign="top" class="ColumnBackground">
													&nbsp;&nbsp;<bean:message key="Amount" />
												</TD>
												<TD align="left" valign="top" class="ColumnBackground">
													&nbsp;&nbsp;<bean:message key="ConsiderInv" />
												</TD>
												<TD align="left" valign="top" class="ColumnBackground">
													&nbsp;&nbsp;<bean:message key="DateOfReceipt" />
												</TD>
											</TR>
											<html:hidden property = "counter" name="investmentForm"/>
											<bean:define id="lastOne" name="investmentForm" property="miscReceipts" />
											<%
												int counter=0;

												String receiptSourceOfFundKey=null;
												String receiptInstDateKey=null;
												String receiptInstNoKey=null;
												String receiptAmountKey=null;
												String receiptConsiderForInvKey=null;
												String receiptDateOfReceiptKey=null;
												String receiptIdKey=null;

												java.util.Map receipts =(java.util.Map)lastOne;

												int size=receipts.size();

												//System.out.println("size "+size);

												com.cgtsi.investmentfund.MiscReceipts receipt = new com.cgtsi.investmentfund.MiscReceipts();

												boolean isExecuted=false;
												Boolean isRequired=(Boolean)request.getAttribute("IsRequired");

											%>

											<logic:iterate id="mreceipt" name="investmentForm" property="miscReceipts">

												<%
													receiptSourceOfFundKey="miscReceipts(key-"+counter+").sourceOfFund";
													receiptInstDateKey="miscReceipts(key-"+counter+").instrumentDate";
													receiptInstNoKey="miscReceipts(key-"+counter+").instrumentNo";
													receiptAmountKey="miscReceipts(key-"+counter+").amount";
													receiptConsiderForInvKey="miscReceipts(key-"+counter+").isConsideredForInv";
													receiptDateOfReceiptKey="miscReceipts(key-"+counter+").dateOfReceipt";
													receiptIdKey="miscReceipts(key-"+counter+").id";
													isExecuted=true;
												%>
											<TR>
												<TD class="tableData">
												<%=counter+1%><html:hidden name="investmentForm" property="<%=receiptIdKey%>"/>
												</TD>
												<TD align="left" valign="top" class="tableData">
													<html:text property="<%=receiptSourceOfFundKey%>" size="20" name="investmentForm" maxlength="20" onblur="displayMiscReceiptsTotal()"/>
												</TD>
												<TD align="left" valign="top" class="tableData">
													<html:text property="<%=receiptInstDateKey%>" size="15" name="investmentForm" maxlength="10" onblur="displayMiscReceiptsTotal()" onkeypress="return dateOnly(this, event)" onkeyup="isValidDate(this)"/>
												</TD>
												<TD align="left" valign="top" class="tableData">
													<html:text property="<%=receiptInstNoKey%>" size="15" name="investmentForm" maxlength="10" onkeypress="return numbersOnly(this, event,10)" onkeyup="isValidNumber(this)" onblur="displayMiscReceiptsTotal()"/>
												</TD>
												<TD align="left" valign="top" class="tableData">
													<html:text property="<%=receiptAmountKey%>" size="15" name="investmentForm" maxlength="16"  onkeypress="return decimalOnly(this, event,13)" onkeyup="isValidDecimal(this)" onblur="displayMiscReceiptsTotal()"/>
												</TD>
												<TD class="tableData">
													<html:radio name="investmentForm" value="Y" property="<%=receiptConsiderForInvKey%>" onclick="displayMiscReceiptsTotal()"> Yes </html:radio>
													<html:radio name="investmentForm" value="N" property="<%=receiptConsiderForInvKey%>" onclick="displayMiscReceiptsTotal()"> No </html:radio>
												</TD>
												<TD align="left" valign="top" class="tableData">
													<html:text property="<%=receiptDateOfReceiptKey%>" size="10" maxlength="10" name="investmentForm" onblur="displayMiscReceiptsTotal()" onkeypress="return dateOnly(this, event)" onkeyup="isValidDate(this)"/>
													<%if(((size-1)==counter) && isRequired==null){%>
													<a href="javascript:submitForm('addMoreMiscReceipts.do?method=addMoreMiscReceipts')"> AddMore</a>
													<%}%>
												</TD>
											</TR>


											<%

											//System.out.println("counter "+counter);
											counter++;
											%>
											</logic:iterate>

											<%
											//System.out.println("isExecuted "+isExecuted+","+isRequired);

											if(!isExecuted || (isExecuted && isRequired!=null))
											{
												receipts.put("key-"+counter,receipt);
												receiptSourceOfFundKey="miscReceipts(key-"+counter+").sourceOfFund";
												receiptInstDateKey="miscReceipts(key-"+counter+").instrumentDate";
												receiptInstNoKey="miscReceipts(key-"+counter+").instrumentNo";
												receiptAmountKey="miscReceipts(key-"+counter+").amount";
												receiptConsiderForInvKey="miscReceipts(key-"+counter+").isConsideredForInv";
												receiptDateOfReceiptKey="miscReceipts(key-"+counter+").dateOfReceipt";
												receiptIdKey="miscReceipts(key-"+counter+").id";
											%>
											<TR>
												<TD class="tableData">
												<%=counter+1%><html:hidden name="investmentForm" property="<%=receiptIdKey%>"/>
												</TD>
												<TD class="tableData">
													<html:text property="<%=receiptSourceOfFundKey%>" size="20" name="investmentForm" maxlength="20" onblur="displayMiscReceiptsTotal()"/>
												</TD>

												<TD class="tableData">
													<html:text property="<%=receiptInstDateKey%>" size="15" name="investmentForm" maxlength="10" onblur="displayMiscReceiptsTotal()"/>
												</TD>

												<TD class="tableData">
													<html:text property="<%=receiptInstNoKey%>" size="15" name="investmentForm" maxlength="10" onkeypress="return numbersOnly(this, event,10)" onkeyup="isValidNumber(this)" onblur="displayMiscReceiptsTotal()"/>
												</TD>

												<TD class="tableData">
													<html:text property="<%=receiptAmountKey%>" size="15" name="investmentForm" maxlength="16"  onkeypress="return decimalOnly(this, event,13)" onkeyup="isValidDecimal(this)" onblur="displayMiscReceiptsTotal()"/>
												</TD>
												<TD class="tableData">
													<html:radio name="investmentForm" value="Y" property="<%=receiptConsiderForInvKey%>" onclick="displayMiscReceiptsTotal()"> Yes </html:radio>
													<html:radio name="investmentForm" value="N" property="<%=receiptConsiderForInvKey%>" onclick="displayMiscReceiptsTotal()"> No </html:radio>
												</TD>
												<TD class="tableData">
													<html:text property="<%=receiptDateOfReceiptKey%>" size="10" maxlength="10" name="investmentForm" onblur="displayMiscReceiptsTotal()"/>
													<a href="javascript:submitForm('addMoreMiscReceipts.do?method=addMoreMiscReceipts')"> AddMore</a>
												</TD>
											</TR>
											<%}%>

											<TR>
												<TD class="ColumnBackground" colspan="4">
													<bean:message key="total" />
												</TD>
												<TD class="tableData" colspan="3" id="totalMiscAmount">

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
									<a href="javascript:submitForm('insertMiscReceipts.do?method=insertMiscReceipts');"><img src="images/Save.gif" alt="Save"
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
</html>
</body>