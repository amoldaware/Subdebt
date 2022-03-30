<%@ page language="java"%>
<%@ taglib uri="/WEB-INF/TLD/struts-html.tld" prefix="html" %>
<%@ taglib uri="/WEB-INF/TLD/struts-bean.tld" prefix="bean" %>
<%@ include file="/jsp/SetMenuInfo.jsp" %>
<% session.setAttribute("CurrentPage","showEnterPaymentDetails.do?method=showEnterPaymentDetails");%>
<body onload="enableCheque()">
<TABLE width="100%" border="0" cellspacing="0" cellpadding="0">
	<html:errors />
	<html:form action="saveEnterPaymentDetails.do?method=saveEnterPaymentDetails" method="POST" focus="paymentsTo">

		<TR>
			<TD width="20" align="right" valign="bottom"><IMG src="images/TableLeftTop.gif" width="20" height="31"></TD>
			<TD background="images/TableBackground1.gif"><IMG src="images/InvestmentManagementHeading.gif" width="169" height="25"></TD>
			<TD width="20" align="left" valign="bottom"><IMG src="images/TableRightTop.gif" width="23" height="31"></TD>
		</TR>
		<TR>
			<TD width="20" background="images/TableVerticalLeftBG.gif">&nbsp;</TD>
			<TD>
			<DIV align="right">			
				<A HREF="javascript:submitForm('helpEnterPaymentDetails.do?method=helpEnterPaymentDetails')">
			    HELP</A>
			</DIV>
						
				<TABLE width="100%" border="0" align="left" cellpadding="0" cellspacing="0">
					<TR>
						<TD>
							<TABLE width="100%" border="0" cellspacing="1" cellpadding="1">
								<TR>
									<TD colspan="4">
										<TABLE width="100%" border="0" cellpadding="0" cellspacing="0">
											<TR>
												<TD width="31%" class="Heading">
												<bean:message key="paymentDetailsHeading" /></TD>
												<TD><IMG src="images/TriangleSubhead.gif" width="19" height="19"></TD>
											</TR>
											<TR>
												<TD colspan="3" class="Heading"><IMG src="images/Clear.gif" width="5" height="5"></TD>
											</TR>

										</TABLE>
									</TD>

								</TR>
								<TR align="left" valign="top">
									<TD width="30%" valign="top" class="ColumnBackground">
										<DIV align="left">
											&nbsp;&nbsp;
											<font color="#FF0000" size="2">*</font>
											&nbsp;<bean:message key="paymentsTo" />
										</DIV>
									</TD>
									<TD align="left" valign="top" class="TableData">
										<html:text property="paymentsTo" size="20"  alt="Payments To" name="payDetails" maxlength="50"/>
										
									</TD>
								</TR>
								<TR align="left" valign="top">
									<TD width="30%" valign="top" class="ColumnBackground">
										<DIV align="left">
											&nbsp;&nbsp;
											<font color="#FF0000" size="2">*</font>
											&nbsp;<bean:message key="amount" />
										</DIV>
									</TD>
									<TD align="left" valign="top" class="TableData">
										<html:text property="amount" size="20"  alt="Amount" name="payDetails" maxlength="16" onkeypress="return decimalOnly(this, event,13)" onkeyup="isValidDecimal(this)"/>
										
									</TD>
								</TR>

								<TR align="left" valign="top">
									<TD width="30%" valign="top" class="ColumnBackground">
										<DIV align="left">
											&nbsp;&nbsp;
											<font color="#FF0000" size="2">*</font>
											&nbsp;<bean:message key="paymentDate" />
										</DIV>
									</TD>
									<TD align="left" valign="top" class="TableData">
										<html:text property="paymentDate" size="20"  alt=" Payment Date" name="payDetails" maxlength="10"/>
										<IMG src="images/CalendarIcon.gif" width="20" onClick="showCalendar('payDetails.paymentDate')" align="center">
									</TD>
								</TR>

								<TR align="left" valign="top">
									<TD width=30% valign="top" class="ColumnBackground">
										<DIV align="left">
											&nbsp;&nbsp;
											<font color="#FF0000" size="2">*</font>
											&nbsp;<bean:message key="ifChequed"/>
										</DIV>
									</TD>
									<TD align="left" valign="top" class="TableData">
										<html:radio value="Y" property="ifChequed" onclick="javascript:enableCheque()"/> <bean:message key="yes"/>&nbsp;&nbsp;
										
										<html:radio value="N" property="ifChequed" onclick="javascript:enableCheque()"/><bean:message key="no"/>&nbsp;&nbsp;
									</TD>
								</TR>
								
								
							</TABLE>
						</TD>
					</TR>

						<TR>
							<TD height="20" > &nbsp;
								<TABLE width="100%" border="0" cellspacing="0" cellpadding="0">
									<TR>
										<TD width="35%" class="Heading">&nbsp;<bean:message key="chequeHeader"/></TD>
										<TD colspan="3"><img src="images/TriangleSubhead.gif" width="19" height="19"></TD>
				   				    </TR>

									<TR>
										<TD colspan="4" class="Heading" ><img src="images/Clear.gif" width="5" height="5"></TD>
									</TR>
								</TABLE>
						
								<TABLE width="100%" border="0" cellspacing="1" cellpadding="0">
									<TR align="left" valign="top">
										<TD align="left" valign="top" class="ColumnBackground"> 
										<font color="#FF0000" size="2">*</font>
											&nbsp;<bean:message key="chequeBankName" />
										
										</TD>
										<TD align="left" class="TableData"> 
										<html:select  property="bankName"  name="payDetails">
											<html:option value="">Select</html:option>
											<html:options property="banks" name="payDetails"/>
										</html:select>
										</TD>
									</TR>


									<TR align="left" valign="top">
										<TD align="left" valign="top" class="ColumnBackground"> 
										<font color="#FF0000" size="2">*</font>
											&nbsp;<bean:message key="chequeNumbers" />
										
										</TD>
										<TD align="left" class="TableData"> 
										<html:text property="chequeNumber" size="20"  alt="Source Name"  maxlength = "100" name="payDetails" onkeypress="return numbersOnly(this, event)" onkeyup="isValidNumber(this)"/>
										&nbsp;
										</TD>
									</TR>

									<TR>
										<TD align="left" valign="top" class="ColumnBackground">
										<font color="#FF0000" size="2">*</font>
										&nbsp;<bean:message key="chequeDate" /> 
										</TD>
										<TD  align="left" valign="center" class="ColumnBackground">
										  <DIV align="left">
										  <html:text property="chequeDate" size="20"  alt="Reference" maxlength="10" name="payDetails"/>
										  <IMG src="images/CalendarIcon.gif" width="20" onClick="showCalendar('payDetails.chequeDate')" align="center">
										  <DIV align="left">
										</TD>
									</TR>

									<TR align="left" valign="top">
										<TD align="left" valign="top" class="ColumnBackground"> 
											<font color="#FF0000" size="2">*</font>
											&nbsp;<bean:message key="chequeAmount" />
										
										</TD>
									<TD align="left" class="TableData"> 
										<html:text property="chequeAmount" size="20"  alt="Source Name"  maxlength="16" onkeypress="return decimalOnly(this, event,13)" onkeyup="isValidDecimal(this)" name="payDetails"/>
									</TD>
									</TR>

									<TR>
										<TD align="left" valign="top" class="ColumnBackground"><font color="#FF0000" size="2">*</font>
										&nbsp;<bean:message key="chequeIssuedTo" />
										</TD>
										<TD align="left" valign="top" class="TableData"> 
										<html:text property="chequeIssuedTo" size="20"  alt="Reference"  maxlength = "50" name="payDetails"/>
										</TD>
									</TR>

									<TR>
										<TD align="left" valign="top" class="ColumnBackground">
										&nbsp;<bean:message key="remarks" />
										</TD>
										<TD align="left" valign="top" class="TableData"> 
										<html:textarea property="remarks" cols="20"  alt="Remarks" name="payDetails"/>
										</TD>
									</TR>
								</TABLE>
							</TD>
						</TR>

						<TR>
<!--							<TD height="20" > &nbsp;
								<TABLE width="100%" border="0" cellspacing="0" cellpadding="0">
									<TR align="left" valign="top">
									<TD width="30%" valign="top" class="ColumnBackground">
										<DIV align="left">
											&nbsp;&nbsp;
											
											<bean:message key="remarks" />
										</DIV>
									</TD>
									<TD align="left" valign="top" class="TableData">
										<html:textarea property="remarks" cols="20"  alt="Remarks" name="payDetails"/>
									</TD>
								</TR>

							</TABLE>
-->						</TD>
					</TR>



								
					
					<TR >
						<TD height="20" >
							&nbsp;
						</TD>
					</TR>
					<TR >
						<TD align="center" valign="baseline" >
							<DIV align="center">
								
								<A href="javascript:submitForm('saveEnterPaymentDetails.do?method=saveEnterPaymentDetails')">
									<IMG src="images/Save.gif" alt="Save" width="49" height="37" border="0"></A>
								<A href="javascript:document.payDetails.reset()">
									<IMG src="images/Reset.gif" alt="Cancel" width="49" height="37" border="0"></A>
								<A href="subHome.do?method=getSubMenu&menuIcon=<%=session.getAttribute("menuIcon")%>&mainMenu=<%=session.getAttribute("mainMenu")%>">
									<IMG src="images/Cancel.gif" alt="Cancel" width="49" height="37" border="0"></A>
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
</body>