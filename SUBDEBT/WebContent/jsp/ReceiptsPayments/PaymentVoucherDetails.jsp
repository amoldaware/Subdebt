
<%@ page language="java"%>
<%@ taglib uri="/WEB-INF/TLD/struts-html.tld" prefix="html" %>
<%@ taglib uri="/WEB-INF/TLD/struts-bean.tld" prefix="bean" %>
<%@ taglib uri="/WEB-INF/TLD/struts-logic.tld" prefix="logic" %>
<%@ include file="/jsp/SetMenuInfo.jsp" %>
<%@ page import="com.cgtsi.util.SessionConstants"%>
<% 
if (((String) session.getAttribute(SessionConstants.VOUCHER_FLAG)).equals("1"))
{
	session.setAttribute("CurrentPage","showPaymentVoucherDetails.do?method=showPaymentVoucherDetails");
}
else if (((String) session.getAttribute(SessionConstants.VOUCHER_FLAG)).equals("2"))
{
	session.setAttribute("CurrentPage","showPaymentVoucherDetails.do?method=getGLName");
}
%>
<% 
	String focusObj="bankGLCode";
	org.apache.struts.action.ActionErrors errors = (org.apache.struts.action.ActionErrors)request.getAttribute(org.apache.struts.Globals.ERROR_KEY);
	if (errors!=null && !errors.isEmpty())
	{
		focusObj="test";
	}
%>
<body onload="javascript:dispTotalAmountPV()">
<html>

<html:form action="insertPaymentVoucherDetails.do?method=insertPaymentVoucherDetails" method="POST" focus="<%=focusObj%>">
<html:hidden name="rpAllocationForm" property="test"/>
<html:errors />
	<TABLE width="100%" border="0" cellspacing="0" cellpadding="0">
		<TR>
			<TD>
				<TABLE width="100%" border="0" cellspacing="0" cellpadding="0" align="center">
					<TR>
							<TD width="20" align="right" valign="bottom"><IMG src="images/TableLeftTop.gif" width="20" height="31"></TD>
							<TD background="images/TableBackground1.gif"><img src="images/ReceiptsPaymentsHeading.gif" width="142"
      							height="25">
      						</TD>
							<TD width="20" align="left" valign="bottom"><IMG src="images/TableRightTop.gif" width="23" height="31"></TD>
					</TR>
					<TR>
						<TD width="20" background="images/TableVerticalLeftBG.gif">&nbsp;</TD>
						<TD width="780">
							<TABLE width="100%" border="0" cellspacing="1" cellpadding="0">
								<TR>
									<TD>
										<TABLE width="100%" border="0" cellspacing="0" cellpadding="0">
											<TR>
												<TD width="25%" class="PVHeading"><bean:message key="paymentVoucher" /></TD>
												<TD><IMG src="images/PVTriangleSubhead.gif" width="19" height="19"></TD>
											</TR>
											<TR>
												<TD colspan="3" class="PVHeading"><IMG src="images/Clear.gif" width="5" height="5"></TD>
											</TR>
										</TABLE>
									</TD>
								</TR>
								<TR>
									<TD>
										<TABLE width="100%" border="0" cellspacing="1" cellpadding="0">
											<TR>

												<TD colspan="2" align="left" valign="top" class="ColumnBackground">
													&nbsp;&nbsp;<font color="#FF0000" size="2">*</font>&nbsp;<bean:message key="bankGLCode" />
												</TD>
												<TD class="tableData" >
													<%--
													<html:text property="bankGLCode" size="20" alt="Bank GL Code" name="rpAllocationForm"/>
													--%>
													<html:select name="rpAllocationForm" property="bankGLCode" onchange="javascript:submitForm('showPaymentVoucherDetails.do?method=getGLName')">
														<html:option value="">Select </html:option>
														<html:options name="rpAllocationForm" property="glHeads"/>
													</html:select>
												</TD>
												<TD colspan="2" align="left" valign="top" class="ColumnBackground">
												&nbsp;&nbsp;<font color="#FF0000" size="2">*</font>&nbsp;<bean:message key="bankGLName"/>
												</TD>
												<TD class="tableData" colspan="2">
													<html:text property="bankGLName" size="30" alt="Bank GL name" name="rpAllocationForm" disabled="true"/>
												</TD>
												<TD align="left" valign="top" class="ColumnBackground">
													&nbsp;&nbsp;<font color="#FF0000" size="2">*</font>&nbsp;<bean:message key="deptSecCode"/>
												</TD>
												<TD class="tableData" >
													<html:text property="deptCode" size="20" name="rpAllocationForm" maxlength="2"/>
												</TD>
											</TR>
											<TR>
												<TD colspan="2" align="left" valign="top" class="ColumnBackground">
													<bean:message key="voucherAmount"/>
												</TD>
												<TD class="tableData">
													<html:text property="amount" size="20" name="rpAllocationForm" maxlength="16" onkeypress="return decimalOnly(this, event,13)" onkeyup="isValidDecimal(this)" disabled="true"/>
												</TD>
												<TD colspan="2" align="left" valign="top" class="ColumnBackground">&nbsp;
													<bean:message key="voucherAmountInFigures"/>
												</TD>
												<TD class="tableData" colspan="2">
													<html:text property="amountInFigure" size="30" name="rpAllocationForm"/>
												</TD>
												<TD align="left" valign="top" class="ColumnBackground">&nbsp;
													&nbsp;&nbsp;<font color="#FF0000" size="2">*</font>&nbsp;<bean:message key="narration"/>
												</TD>
												<TD class="tableData" >
													<html:textarea property="narration" cols="35" name="rpAllocationForm"/>
												</TD>
											</TR>
											<TR>
												<TD align="left" valign="top" class="ColumnBackground">&nbsp;
													<font color="#FF0000" size="2">*</font>&nbsp;<bean:message key="acCode"/>
												</TD>
												<TD align="left" valign="top" class="ColumnBackground">&nbsp;
													<font color="#FF0000" size="2">*</font>&nbsp;<bean:message key="paidTo"/>
												</TD>
												<TD align="left" valign="top" class="ColumnBackground">&nbsp;
													<font color="#FF0000" size="2">*</font>&nbsp;<bean:message key="amountInRs"/>
												</TD>
												<TD align="left" valign="top" class="ColumnBackground">&nbsp;
													<font color="#FF0000" size="2">*</font>&nbsp;<bean:message key="debitOrCredit"/>
												</TD>
												<TD align="left" valign="top" class="ColumnBackground">&nbsp;
													<bean:message key="advNo"/>
												</TD>
												<TD align="left" valign="top" class="ColumnBackground">&nbsp;
													<bean:message key="advDate"/>
												</TD>
												<TD align="left" valign="top" class="ColumnBackground">&nbsp;
													<font color="#FF0000" size="2">*</font>&nbsp;<bean:message key="instrumentType"/>
												</TD>
												<TD align="left" valign="top" class="ColumnBackground">&nbsp;
													<font color="#FF0000" size="2">*</font>&nbsp;<bean:message key="instrumentDate"/>
												</TD>
												<TD align="left" valign="top" class="ColumnBackground">&nbsp;
													<font color="#FF0000" size="2">*</font>&nbsp;<bean:message key="instrumentNumber"/>
												</TD>
											</TR>
											<bean:define id="lastOne" name="rpAllocationForm" property="voucherDetails" />
											<%
												int counter=0;

												String voucherAcCodeKey=null;
												String voucherPaidToKey=null;
												String voucherAmountRsKey=null;
												String voucherDebitCreditKey=null;
												String voucherAdvNoKey=null;
												String voucherAdvDateKey=null;
												String voucherInstrumentTypeKey=null;

												String voucherInstrumentDateKey=null;
												String voucherInstrumentNoKey=null;

												java.util.Map vouchers =(java.util.Map)lastOne;

												int size=vouchers.size();

												//System.out.println("size "+size);

												com.cgtsi.receiptspayments.Voucher voucher=new com.cgtsi.receiptspayments.Voucher();

												boolean isExecuted=false;
												Boolean isRequired=(Boolean)request.getAttribute("IsRequired");

												String advCal=null;
												String instrumentCal=null;

											%>
											<logic:iterate id="voucherDetail" name="rpAllocationForm" property="voucherDetails">

												<%

													voucherAcCodeKey="voucherDetails(key-"+counter+").acCode";
													voucherPaidToKey="voucherDetails(key-"+counter+").paidTo";
													voucherAmountRsKey="voucherDetails(key-"+counter+").amountInRs";
													voucherDebitCreditKey="voucherDetails(key-"+counter+").debitOrCredit";
													voucherAdvNoKey="voucherDetails(key-"+counter+").advNo";
													voucherAdvDateKey="voucherDetails(key-"+counter+").advDate";
													voucherInstrumentTypeKey="voucherDetails(key-"+counter+").instrumentType";

													voucherInstrumentDateKey="voucherDetails(key-"+counter+").instrumentDate";
													voucherInstrumentNoKey="voucherDetails(key-"+counter+").instrumentNo";
													isExecuted=true;

													advCal="showCalendar('rpAllocationForm."+voucherAdvDateKey+"')";
													instrumentCal="showCalendar('rpAllocationForm."+voucherInstrumentDateKey+"')";

												%>

											<TR>
												<TD class="tableData">
													<html:select name="rpAllocationForm" property="<%=voucherAcCodeKey%>" >
														<html:option value="">Select </html:option>
														<html:options name="rpAllocationForm" property="glHeads"/>
													</html:select>

												</TD>

												<TD class="tableData">
													<html:text property="<%=voucherPaidToKey%>" size="20" name="rpAllocationForm" maxlength="200"/>
												</TD>

												<TD class="tableData">
													<html:text property="<%=voucherAmountRsKey%>" size="15" name="rpAllocationForm" maxlength="16"  onkeypress="return decimalOnly(this, event,13)" onkeyup="isValidDecimal(this)" onblur="javascript:dispTotalAmountPV()"/>
												</TD>

												<TD class="tableData">
												
													<html:select property="<%=voucherDebitCreditKey%>" name="rpAllocationForm">
														<html:option value="D">Debit </html:option>
														<html:option value="C">Credit </html:option>
													</html:select>
												</TD>
												<TD class="tableData">
													<html:text property="<%=voucherAdvNoKey%>" size="10" name="rpAllocationForm" />
												</TD>
												<TD class="tableData">
													<html:text property="<%=voucherAdvDateKey%>" size="10" maxlength="10" name="rpAllocationForm"/>
<!--													<img src="images/CalendarIcon.gif" width="20" align="center" onClick="<%=advCal%>">-->
												</TD>
												<TD class="tableData">
													<html:select property="<%=voucherInstrumentTypeKey%>" name="rpAllocationForm">
													<html:option value="">Select</html:option>
														<html:options name="rpAllocationForm" property="instruments"/>
													</html:select>
												</TD>
												<TD class="tableData">
													<html:text property="<%=voucherInstrumentDateKey%>" size="12" name="rpAllocationForm" maxlength="10"/>
<!--													<img src="images/CalendarIcon.gif" width="20" align="center" onClick="<%=instrumentCal%>">-->
												</TD>

												<TD class="tableData">
													<html:text property="<%=voucherInstrumentNoKey%>" size="20" maxlength="10" name="rpAllocationForm" /><%if(((size-1)==counter) && isRequired==null){%><a href="javascript:submitForm('addMorePaymentVoucherDetails.do?method=addMorePaymentVoucherDetails')">AddMore</a><%}%>
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

												vouchers.put("key-"+counter,voucher);
												voucherAcCodeKey="voucherDetails(key-"+counter+").acCode";
												voucherPaidToKey="voucherDetails(key-"+counter+").paidTo";
												voucherAmountRsKey="voucherDetails(key-"+counter+").amountInRs";
												voucherDebitCreditKey="voucherDetails(key-"+counter+").debitOrCredit";
												voucherAdvNoKey="voucherDetails(key-"+counter+").advNo";
												voucherAdvDateKey="voucherDetails(key-"+counter+").advDate";
												voucherInstrumentTypeKey="voucherDetails(key-"+counter+").instrumentType";

												voucherInstrumentDateKey="voucherDetails(key-"+counter+").instrumentDate";
												voucherInstrumentNoKey="voucherDetails(key-"+counter+").instrumentNo";
											%>
											<TR>
												<TD class="tableData">
													<html:select name="rpAllocationForm" property="<%=voucherAcCodeKey%>" >
														<html:option value="">Select </html:option>
														<html:options name="rpAllocationForm" property="glHeads"/>
													</html:select>

												</TD>

												<TD class="tableData">
													<html:text property="<%=voucherPaidToKey%>" size="20" name="rpAllocationForm" maxlength="200"/>
												</TD>

												<TD class="tableData">
													<html:text property="<%=voucherAmountRsKey%>" size="15" name="rpAllocationForm" maxlength="16"  onkeypress="return decimalOnly(this, event,13)" onkeyup="isValidDecimal(this)" onblur="javascript:dispTotalAmountPV()"/>
												</TD>

												<TD class="tableData">
												
													<html:select property="<%=voucherDebitCreditKey%>" name="rpAllocationForm">
														<html:option value="D">Debit </html:option>
														<html:option value="C">Credit </html:option>
													</html:select>
												</TD>
												<TD class="tableData">
													<html:text property="<%=voucherAdvNoKey%>" size="10" maxlength="6" name="rpAllocationForm"/>
												</TD>
												<TD class="tableData">
													<html:text property="<%=voucherAdvDateKey%>" size="10" name="rpAllocationForm" maxlength="10"/>
<!--													<img src="images/CalendarIcon.gif" width="20" align="center" onClick="<%=advCal%>">-->
												</TD>
												<TD class="tableData">
													<html:select property="<%=voucherInstrumentTypeKey%>" name="rpAllocationForm">
													<html:option value="">Select</html:option>
														<html:options name="rpAllocationForm" property="instruments"/>
													</html:select>
												</TD>
												<TD class="tableData">
													<html:text property="<%=voucherInstrumentDateKey%>" size="12" name="rpAllocationForm" maxlength="10"/>
<!--													<img src="images/CalendarIcon.gif" width="20" align="center" onClick="<%=instrumentCal%>">-->
												</TD>

												<TD class="tableData">
													<html:text property="<%=voucherInstrumentNoKey%>" size="20" maxlength="10" name="rpAllocationForm"/><a href="javascript:submitForm('addMorePaymentVoucherDetails.do?method=addMorePaymentVoucherDetails')">AddMore</a>
												</TD>
											</TR>
											<%}%>
											<TR>
												<TD colspan="2" align="left" valign="top" class="ColumnBackground">
													<bean:message key="asstManager"/>
												</TD>
												<TD class="tableData">
													<html:text property="asstManager" size="20" name="rpAllocationForm" />
												</TD>
												<TD colspan="2" align="left" valign="top" class="ColumnBackground">&nbsp;
													<bean:message key="manager"/>
												</TD>
												<TD class="tableData" colspan="4">
													<html:text property="manager" size="30" name="rpAllocationForm"/>
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
									<a href="javascript:submitForm('insertPaymentVoucherDetails.do?method=insertPaymentVoucherDetails');"><img src="images/Save.gif" alt="Save"
										width="49" height="37" border="0"></a>
									<a href="javascript:document.rpAllocationForm.reset()">
										<img src="images/Reset.gif" alt="Reset" width="49" height="37"
										border="0"></a>
								<A href="subHome.do?method=getSubMenu&menuIcon=<%=session.getAttribute("menuIcon")%>&mainMenu=<%=session.getAttribute("mainMenu")%>">
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