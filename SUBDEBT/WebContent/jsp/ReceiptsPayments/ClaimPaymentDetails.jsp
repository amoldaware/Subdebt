<%@ page language="java"%>
<%@ taglib uri="/WEB-INF/TLD/struts-html.tld" prefix="html" %>
<%@ taglib uri="/WEB-INF/TLD/struts-bean.tld" prefix="bean" %>
<%@ include file="/jsp/SetMenuInfo.jsp" %>
<% session.setAttribute("CurrentPage","showPaymentDetails.do?method=getClaimPaymentDetails");%>
<html>
	<TABLE width="100%" border="0" cellspacing="0" cellpadding="0">
	<html:errors />
	<html:form action="insertPaymentDetails.do?method=insertPaymentDetails" method="POST" focus="modeOfPayment">
		<TR>
			<TD>
				<TABLE width="680" border="0" cellspacing="0" cellpadding="0" align="center">
					<TR>
							<TD width="20" align="right" valign="bottom"><IMG src="images/TableLeftTop.gif" width="20" height="31"></TD>
							<TD background="images/TableBackground1.gif"><img src="images/ReceiptsPaymentsHeading.gif" width="142"
      							height="25">
      						</TD>
							<TD width="20" align="left" valign="bottom"><IMG src="images/TableRightTop.gif" width="23" height="31"></TD>
					</TR>
					<TR>
						<TD width="20" background="images/TableVerticalLeftBG.gif">&nbsp;</TD>
						<TD width="680">
							<TABLE width="100%" border="0" cellspacing="1" cellpadding="0">
								<TR>
									<TD>
										<TABLE width="100%" border="0" cellspacing="0" cellpadding="0">
											<TR>
												<TD width="25%" class="Heading"><bean:message key="paymentDetails" /></TD>
												<TD><img src="images/TriangleSubhead.gif" width="19" height="19"></TD>
											</TR>
											<TR>
												<TD colspan="2" class="Heading"><img src="images/clear.gif" width="5" height="5"></TD>
											</TR>
										</TABLE>
									</TD>
								</TR>
								<TR>
									<TD>
										<TABLE width="100%" border="0" cellspacing="1" cellpadding="0">
											<TR>
												<TD width="40%" align="left" valign="top" class="ColumnBackground">
												&nbsp;&nbsp;<font color="#FF0000" size="2">*</font>&nbsp;<bean:message key="modeOfPayment" /></TD>
												<TD class="tableData">
												  <html:select property="modeOfPayment" name="rpAllocationForm">
														<html:option value="">Select </html:option>
														<html:options name="rpAllocationForm" property="instruments" />
												  </html:select>
												</TD>
											</TR>
											<TR>
												<TD align="left" valign="top" class="ColumnBackground"><bean:message key="collectingBankName"/></TD>
												<TD class="tableData">
													<html:text property="collectingBankName" size="20" alt="Bank name" name="rpAllocationForm"/>
												</TD>
											</TR>
											<TR>
												<TD align="left" valign="top" class="ColumnBackground">&nbsp;&nbsp;<font color="#FF0000" size="2">*</font>&nbsp;<bean:message key="collectingBankBranch"/></TD>
												<TD class="tableData"><html:text property="collectingBankBranch" size="20" alt="Instrument Number" name="rpAllocationForm"/></TD>
											</TR>
											<TR>
												<TD align="left" valign="top" class="ColumnBackground">&nbsp;&nbsp;<font color="#FF0000" size="2">*</font>&nbsp;<bean:message key="CGTSIAccountHoldingBranch"/></TD>
												<TD class="tableData">
												<html:text property="accountNumber" size="20" alt="Bank name" name="rpAllocationForm"/>
												</TD>
											</TR>
											<TR>
												<TD align="left" valign="top" class="ColumnBackground">&nbsp;&nbsp;<font color="#FF0000" size="2">*</font>&nbsp;<bean:message key="paymentDate"/></TD>
												<TD class="tableData"><html:text property="paymentDate" size="20" alt="Payment Date" name="rpAllocationForm"/>
													<img src="images/CalendarIcon.gif" width="20" align="center"
													onClick="showCalendar('rpAllocationForm.paymentDate')">
												</TD>
											</TR>
											<TR>
												<TD colspan="2"><img src="images/clear.gif" width="5" height="15"></TD>
											</TR>
											<TR>
												<TD colspan="2">
													<TABLE width="100%" border="0" cellspacing="0" cellpadding="0">
														<TR>
															<TD width="35%" class="Heading"><bean:message key="instrumentDetails"/></TD>
															<TD><img src="images/TriangleSubhead.gif" width="19" height="19"></TD>
														</TR>
														<TR>
															<TD colspan="2" class="Heading"><img src="images/clear.gif" width="5" height="5"></TD>
														</TR>
													</TABLE>
												</TD>
											</TR>
											<TR>
											<TD align="left" valign="top" class="ColumnBackground">&nbsp;&nbsp;<font color="#FF0000" size="2">*</font>&nbsp;<bean:message key="instrumentNumber"/></TD>
											<TD class="tableData"><html:text property="instrumentNo" size="20" alt="Instrument Number" name="rpAllocationForm" /></TD>
											</TR>
											<TR>
											<TD align="left" valign="top" class="ColumnBackground">&nbsp;<span id="instdate" name="instdate">&nbsp;&nbsp;<font color="#FF0000" size="2">*</font>&nbsp;<bean:message key="instrumentDate"/></span></TD>
											<TD class="tableData"><TABLE cellpadding="0" cellspacing="0">
											<TR>
											<TD><html:text property="instrumentDate" size="20" alt="Instrument Date" name="rpAllocationForm"/></TD>
											<TD><img src="images/CalendarIcon.gif" width="20" align="center"
											onClick="showCalendar('rpAllocationForm.instrumentDate')"></TD>
											</TR>
										</TABLE>
									</TD>
								</TR>
								<TR>
									<TD align="left" valign="top" class="ColumnBackground"><bean:message key="instrumentAmount"/></TD>
									<TD class="tableData">
									<html:text property="instrumentAmount" size="20" alt="Instrument amount" name="rpAllocationForm" maxlength="13"  onkeypress="return decimalOnly(this, event)" onkeyup="isValidDecimal(this)"/>
									</TD>
								</TR>
								<TR>
									<TD align="left" valign="top" class="ColumnBackground">&nbsp;&nbsp;<font color="#FF0000" size="2">*</font>&nbsp;<bean:message key="drawnAtBank"/></TD>
									<TD class="tableData"><html:text property="drawnAtBank" size="20" alt="Drawn at Bank" name="rpAllocationForm"/></TD>
								</TR>
								<TR>
									<TD align="left" valign="top" class="ColumnBackground">&nbsp;&nbsp;<font color="#FF0000" size="2">*</font>&nbsp;<bean:message key="drawnAtBranch"/></TD>
									<TD class="tableData"><html:text property="drawnAtBranch" size="20" alt="Drawn at Branch" name="rpAllocationForm"/></TD>
								</TR>
								<TR>
									<TD align="left" valign="top" class="ColumnBackground">&nbsp;&nbsp;<font color="#FF0000" size="2">*</font>&nbsp;<bean:message key="payableAt"/></TD>
									<TD class="tableData"><html:text property="payableAt" size="20" alt="Payable at" name="rpAllocationForm"/></TD>
								</TR>
							</TABLE>
						</TD>
					</TR>
					<TR>
						<TD><img src="clear.gif" width="5" height="15"></TD>
					</TR>
					<TR align="center" valign="baseline">
						<TD>
						<a href="javascript:submitForm('insertPaymentDetails.do?method=insertPaymentDetails');"><img src="images/Save.gif" alt="Save"
						width="49" height="37" border="0"></a>
						<a href="javascript:document.rpAllocationForm.reset()">
						<img src="images/Reset.gif" alt="Reset" width="49" height="37"
						border="0"></a>
						</TD>
					</TR>
				</TABLE>
			</TD>
			<TD background="images/TableVerticalRightBG.gif"></TD>
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
</html:form>
</html>