<%@ page language="java"%>
<%@ page import="java.util.ArrayList"%>
<%@ page import="java.text.SimpleDateFormat"%>
<%SimpleDateFormat dateFormat=new SimpleDateFormat("dd/MM/yyyy");%>
<%@ page import="com.cgtsi.receiptspayments.PaymentList"%>
<%@ page import="com.cgtsi.actionform.RPActionForm"%>
<%@ taglib uri="/WEB-INF/TLD/struts-html.tld" prefix="html" %>
<%@ taglib uri="/WEB-INF/TLD/struts-bean.tld" prefix="bean" %>
<%@ taglib uri="/WEB-INF/TLD/struts-logic.tld" prefix="logic" %>
<%@ include file="/jsp/SetMenuInfo.jsp" %>
<% session.setAttribute("CurrentPage","gfappropriatePayments.do?method=getPaymentsMadeForGF");%>
<TABLE width="100%" border="0" cellpadding="0" cellspacing="0">
	<html:errors />
	<html:form action="gfappropriatePayments.do" method="POST" enctype="multipart/form-data">
		<html:hidden property="paymentId" alt="Payment Id" name="rpAllocationForm" />
		<html:hidden property="memberId" alt="Member Id" name="rpAllocationForm" />
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
									<TD colspan="7"> 
										<TABLE width="100%" border="0" cellpadding="0" cellspacing="0">
											<TR>
												<TD width="20%" class="Heading"><bean:message key="paymentDetails"/></TD>
												<TD><IMG src="images/TriangleSubhead.gif" width="19" height="19"></TD>
												<TD>&nbsp;</TD>
											</TR>
											<TR>
												<TD colspan="7" class="Heading"><IMG src="images/Clear.gif" width="5" height="5"></TD>
											</TR>

										</TABLE>
									</TD>
									</TR>
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
                  <TD align="left" valign="top" class="ColumnBackground">
										<bean:message key="allocatedAmount" />
									</TD>                                                      
                  <TD align="left" valign="top" class="ColumnBackground">
										<!-- <bean:message key="instrumentNo" /> --> Instrument Number
									</TD>
                   <TD align="left" valign="top" class="ColumnBackground">
										<bean:message key="instrumentAmount" />
									</TD>
									<TD align="left" valign="top" class="ColumnBackground">
										<!-- <bean:message key="instrumentDt" /> --> Instrument Date
									</TD>
									</TR>	
									<%RPActionForm rpActionForm = (RPActionForm)session.getAttribute("rpAllocationForm") ;

									ArrayList paymentSummary = rpActionForm.getPaymentDetails() ;
									if(paymentSummary==null) {%>
										<TR>
											<TD colspan="5" align="center" valign="top" class="ColumnBackground" >
													<bean:message key="noPaymentsReceived" />
											</TD>
										</TR>
									<%} else {%>
										<logic:iterate id="object" name="rpAllocationForm" property="paymentDetails">
										<%
										com.cgtsi.receiptspayments.PaymentList paymentList =(com.cgtsi.receiptspayments.PaymentList)object;%>
											<TR>
												<TD align="left" valign="top" class="tableData">
													<%=paymentList.getMemberID()%>
													
												</TD>
												<TD align="left" valign="top" class="tableData">
													<%=paymentList.getMemberName()%>
												</TD>
												<TD align="left" valign="top" class="tableData">
													<a href="javascript:setHiddenFieldValue('paymentId', '<%=paymentList.getPaymentId()%>', 'gfappropriatePayments.do?method=getPaymentDetailsForGF');">
														<%=paymentList.getPaymentId()%>
													</a>
												</TD>
                        <TD align="left" valign="top" class="tableData">
													<div align="right"><%=paymentList.getAllocatedAmt()%></div>
												</TD>                                                                        
                        <TD align="left" valign="top" class="tableData">
													<div align="right"><%=paymentList.getInstrumentNo()%></div>
												</TD>
                        <TD align="right" valign="top" class="tableData">
													<div align="right"><%=paymentList.getPayAmount()%></div>
												</TD>
												<TD align="left" valign="top" class="tableData">
														<%
                            String date1=null;
                            java.util.Date instrumetDt = paymentList.getInstrumentDt();
                            if(instrumetDt != null)
                            {
                            date1 =dateFormat.format(instrumetDt);
                            }
                            else
                            {
                              date1="";
                            }                            
                            %>
                            <div align="center"><%=date1%></div>
												</TD>
											</TR>
									</logic:iterate>
								<%}%>
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