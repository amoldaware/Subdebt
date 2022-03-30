<%@ page language="java"%>
<%@ taglib uri="/WEB-INF/TLD/struts-html.tld" prefix="html" %>
<%@ taglib uri="/WEB-INF/TLD/struts-bean.tld" prefix="bean" %>
<%@page import ="java.text.SimpleDateFormat"%>
<%SimpleDateFormat dateFormat=new SimpleDateFormat("dd/MM/yyyy");%> 
<%@ include file="/jsp/SetMenuInfo.jsp" %>
<% session.setAttribute("CurrentPage","showModifyPaymentDetails.do?method=getPaymentDetails");%>

<TABLE width="100%" border="0" cellspacing="0" cellpadding="0">
	<html:errors />
	<html:form action="saveModifyPaymentDetails.do?method=saveModifyPaymentDetails" method="POST" focus="paymentsTo">

		<TR>
			<TD width="20" align="right" valign="bottom"><IMG src="images/TableLeftTop.gif" width="20" height="31"></TD>
			<TD background="images/TableBackground1.gif"><IMG src="images/InvestmentManagementHeading.gif" width="169" height="25"></TD>
			<TD width="20" align="left" valign="bottom"><IMG src="images/TableRightTop.gif" width="23" height="31"></TD>
		</TR>
		<TR>
			<TD width="20" background="images/TableVerticalLeftBG.gif">&nbsp;</TD>
			<TD>
			<DIV align="right">			
				<A HREF="javascript:submitForm('helpModifyPaymentDetails.do?method=helpModifyPaymentDetails')">
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
												<bean:message key="updpaymentDetailsHeading" /></TD>
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
										<html:text property="paymentsTo" size="20"  alt="Payments To" name="payDetails"/>
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
										<html:text property="amount" size="20"  alt="Amount" name="payDetails" onkeypress="return decimalOnly(this, event, 13)" onkeyup="isValidDecimal(this)" maxlength="16"/>
										
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
								<html:text property="paymentDate" size="20"  alt=" Payment 				Date" name="payDetails"/>   
<!--									<bean:define id="payIdVal" name="payDetails"  														property="paymentDate"/>
									<%
									java.util.Date newDate = (java.util.Date)payIdVal;
									String date = dateFormat.format(newDate);%>
									<%=date%>  
									<IMG src="images/CalendarIcon.gif" width="20" 						onClick="showCalendar('payDetails.paymentDate')" align="center"> -->
									</TD>
								</TR>

								<TR align="left" valign="top">
									<TD width="30%" valign="top" class="ColumnBackground">
										<DIV align="left">
											&nbsp;&nbsp;
											<!--<font color="#FF0000" size="2">*</font>&nbsp;-->
											<bean:message key="remarks" />
										</DIV>
									</TD>
									<TD align="left" valign="top" class="TableData">
										<html:textarea property="remarks" cols="20"  alt="Remarks" name="payDetails"/>
									</TD>
								</TR>

							<html:hidden property="payId" name="payDetails"/>
								
							</TABLE>
						</TD>
					</TR>

					<TR>
					<TD>
					<TABLE>
					<TR>
					<TD>
					<bean:define id="payIdVal" name="payDetails" property="payId"/>
					<%String text = "Modify Cheque Details";
					String strPay = (String)payIdVal;
					 String url = "chequeDetailsForPayment.do?method=chequeDetailsForPayment&number="+strPay;%>
					 <html:link href="<%=url%>"><%=text%></html:link>
					</TD>
					</TR>
					</TABLE>
					</TD>
					</TR>


					<TR >
						<TD height="20" >
							&nbsp;
						</TD>
					</TR>
					<TR >
						<TD align="center" valign="baseline" >
							<DIV align="center">
								
								<A href="javascript:submitForm('saveModifyPaymentDetails.do?method=saveModifyPaymentDetails')">
									<IMG src="images/Save.gif" alt="Save" width="49" height="37" border="0"></A>
								<A href="javascript:document.payDetails.reset()">
									<IMG src="images/Reset.gif" alt="Cancel" width="49" height="37" border="0"></A>
								<A href="javascript:history.back()">
									<IMG src="images/Back.gif" alt="Cancel" width="49" height="37" border="0"></A>
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