<%@ page language="java"%>
<%@ page import="java.util.ArrayList"%>
<%@ page import="com.cgtsi.receiptspayments.DANSummary"%>
<%@ taglib uri="/WEB-INF/TLD/struts-html.tld" prefix="html" %>
<%@ taglib uri="/WEB-INF/TLD/struts-bean.tld" prefix="bean" %>
<%@ taglib uri="/WEB-INF/TLD/struts-logic.tld" prefix="logic" %>
<%@ include file="/jsp/SetMenuInfo.jsp" %>
<%session.setAttribute("CurrentPage","displayPaymentsForReallocation.do?method=getPaymentsForReallocation");%>
<html>
<TABLE width="100%" border="0" cellpadding="0" cellspacing="0">
	<html:errors />
	<html:form action="displayPaymentsForReallocation.do?method=getPaymentsForReallocation" method="POST">
		<TR>
			<TD width="20" align="right" valign="bottom"><IMG src="images/TableLeftTop.gif" width="20" height="31"></TD>
			<TD background="images/TableBackground1.gif"><IMG src="images/ReceiptsPaymentsHeading.gif" width="121" height="25"></TD>
			<TD width="20" align="left" valign="bottom"><IMG src="images/TableRightTop.gif" width="23" height="31"></TD>
		</TR>
		<TR>
			<TD width="20" background="images/TableVerticalLeftBG.gif">&nbsp;</TD>
			<TD>
							<TABLE width="100%" border="0" cellspacing="1" cellpadding="1">
								<TR>
									<TD colspan="3">
										<TABLE width="100%" border="0" cellpadding="0" cellspacing="0">
											<TR>
												<TD width="20%" class="Heading"><bean:message key="paymentDetails"/></TD>
												<TD><IMG src="images/TriangleSubhead.gif" width="19" height="19"></TD>
												<TD>&nbsp;</TD>
											</TR>
											<TR>
												<TD colspan="3" class="Heading"><IMG src="images/Clear.gif" width="5" height="5"></TD>
											</TR>

										</TABLE>
									</TD>

									<TR>
									<TD align="left" valign="top" class="ColumnBackground">
										<bean:message key="memberId" />
									</TD>
									<TD align="left" valign="top" class="ColumnBackground">
										<bean:message key="memberName" />
									</TD>
									<TD align="left" valign="top" class="ColumnBackground">
										<bean:message key="paymentId" />
									</TD>
									</TR>
									<%int i = 0;%>
									<logic:iterate id="object" name="rpAllocationForm" property="paymentDetails">
									<%
									com.cgtsi.receiptspayments.PaymentList paymentList =(com.cgtsi.receiptspayments.PaymentList)object;%>
										<TR>
											<TD align="left" valign="top" class="tableData">
										<a href="displayCgpan.do?method=getAllocatedPaymentDetails&memberId=<%=paymentList.getMemberID()%>&payId=<%=paymentList.getPaymentId()%>">
											<%=paymentList.getMemberID()%>
										</a>
									</TD>
									<TD align="left" valign="top" class="tableData">
										<%=paymentList.getMemberName()%>
									</TD>
									<TD align="left" valign="top" class="tableData">
										<%=paymentList.getPaymentId()%>
									</TD>
									</TR>
									</logic:iterate>
					</TR>
<!--					<TR>
						<TD align="center" valign="baseline" colspan="3">
										<DIV align="center">
											<A href="javascript:submitForm('results.do?method=submitDANPayments')">
												<IMG src="images/OK.gif" alt="Save" width="49" height="37" border="0"></A>
										</DIV>
						</TD>
					</TR>-->

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
</html>
</TABLE>

