<%@ page language="java"%>
<%@ page import="java.util.ArrayList"%>
<%@ page import="java.text.SimpleDateFormat"%>
<%@ page import="java.util.Date"%>
<%@ page import="com.cgtsi.receiptspayments.PaymentDetails"%>
<%@ page import="com.cgtsi.receiptspayments.DemandAdvice"%>
<%@ page import="java.text.DecimalFormat"%>
<%@ taglib uri="/WEB-INF/TLD/struts-html.tld" prefix="html" %>
<%@ taglib uri="/WEB-INF/TLD/struts-bean.tld" prefix="bean" %>
<%@ taglib uri="/WEB-INF/TLD/struts-logic.tld" prefix="logic" %>
<%@ include file="/jsp/SetMenuInfo.jsp" %>
<%session.setAttribute("CurrentPage","gfappropriatePayments.do?method=getPaymentDetailsForGF");%>

<%SimpleDateFormat dateFormat=new SimpleDateFormat("dd/MM/yyyy");
DecimalFormat df=new DecimalFormat("######################.##");
df.setDecimalSeparatorAlwaysShown(false);
DemandAdvice demandAdvice = null;

String instrumentNo = "";
Date instrumentDate = null;
double instrumentAmount = 0.0;
String modeOfPayment = "";
String collectingBankName = "";
String collectingBankBranch = "";
String drawnAtBank = "";
String drawnAtBranch = "";
String payableAt = "";
double allocatedAmount = 0.0;
double appropriatedAmount = 0.0;

String danNo = "" ;
String danType = "" ;
String prevDanType = "" ;
String cgpan = "" ;
String bankId = "" ;
String zoneId = "" ;
String branchId = "" ;
double danAmount = 0.0 ;
String reasons = "" ;
double fee = 0.0 ;
double penalty = 0.0 ;
double totalAmt = 0.0 ;
String allocatedFlag = "" ;
String appropriatedFlag = "" ;
String name = "" ;
String status="";

%>
<%
String focusField="dateOfRealisation";
org.apache.struts.action.ActionErrors errors = (org.apache.struts.action.ActionErrors)request.getAttribute(org.apache.struts.Globals.ERROR_KEY);
if (errors!=null && !errors.isEmpty())
{
	focusField="test";
}
%>
	<body onload="setTotalAppropriated(this)">
	<html:form name="rpAllocationForm" type="org.apache.struts.validator.DynaValidatorActionForm" action="afterAppropriatePaymentsForGF.do?method=appropriatePaymentsForGF" method="POST" enctype="multipart/form-data" focus="<%=focusField%>">
	<html:hidden name="rpAllocationForm" property="test"/>
	<html:errors />
<TABLE width="725" border="0" cellpadding="0" cellspacing="0">
	
		<TR>
			<TD width="20" align="right" valign="bottom"><IMG src="images/TableLeftTop.gif" width="20" height="31"></TD>
			<TD background="images/TableBackground1.gif"><IMG src="images/ReceiptsPaymentsHeading.gif" height="25"></TD>
			<TD width="20" align="left" valign="bottom"><IMG src="images/TableRightTop.gif" height="31"></TD>
		</TR>
		<TR>
			<TD width="20" background="images/TableVerticalLeftBG.gif">&nbsp;</TD>
			<TD>
				<TABLE width="100%" border="0" align="left" cellpadding="0" cellspacing="0">
					<TR>
						<TD>
							<TABLE width="100%" border="0" cellspacing="1" cellpadding="1">
								<TR>
									<TD colspan="12">
										<TABLE width="100%" border="0" cellpadding="0" cellspacing="0">
											<TR>
												<TD width="40%" class="Heading"><bean:message key="appropriateHeader" />
												</TD>
												<TD><IMG src="images/TriangleSubhead.gif" width="19" height="19"></TD>
											</TR>
											<TR>
												<TD colspan="2" class="Heading"><IMG src="images/Clear.gif" width="5" height="5"></TD>
											</TR>
											<TR>
												<TD class="subHeading" height="21">Payment Details of
												<bean:write name="rpAllocationForm" property="paymentId"/>
												<html:hidden property="paymentId" name="rpAllocationForm" />
												</TD>
											</TR>
										</TABLE>
									</TD>
									</TR>
									<%int index=0;
									boolean heading = true ;
									%>
									<logic:iterate id="object" name="rpAllocationForm" property="paymentDetails">
									<%if (index==0) {
										PaymentDetails paymentDetails =(PaymentDetails)object;

										instrumentNo = paymentDetails.getInstrumentNo() ;
										instrumentDate = paymentDetails.getInstrumentDate() ;
										instrumentAmount = paymentDetails.getInstrumentAmount() ;
										modeOfPayment = paymentDetails.getModeOfPayment() ;
										collectingBankName = paymentDetails.getCollectingBank() ;
										collectingBankBranch = paymentDetails.getCollectingBankBranch() ;
										drawnAtBank = paymentDetails.getDrawnAtBank() ;
										drawnAtBranch = paymentDetails.getDrawnAtBranch() ;
										payableAt = paymentDetails.getPayableAt() ;
										allocatedAmount = paymentDetails.getAllocatedAmount() ;
										appropriatedAmount = paymentDetails.getAllocatedAmount() ;
										%>
										<TR>
										<TD align="left" valign="top" colspan="12">
											<TABLE width="100%" border="0" cellpadding="0" cellspacing="1">
												<TR>
													<TD align="left" valign="top" class="ColumnBackground"> <bean:message key="instrumentNumber"/>   </TD>
										            <TD class="tableData"><%=instrumentNo%></TD>
													<TD align="left" valign="top" class="ColumnBackground"><bean:message key="instrumentDate"/></TD>
										            <TD class="tableData">
														<%if(instrumentDate!=null)
														{
													%>
											<%=dateFormat.format(instrumentDate)%>
											<%}%>
													</TD>
												</TR>

												<TR>
													<TD align="left" valign="top" class="ColumnBackground"><bean:message key="instrumentAmount"/></TD>
										            <TD class="tableData"><%=instrumentAmount%></TD>
													<TD align="left" valign="top" class="ColumnBackground"><bean:message key="modeOfPayment"/></TD>
										            <TD class="tableData"><%=modeOfPayment%></TD>
												</TR>

												<TR>
													<TD align="left" valign="top" class="ColumnBackground"><bean:message key="collectingBankName"/></TD>
										            <TD class="tableData">
													<%if(collectingBankName!=null)
														{%><%=collectingBankName%><%}%></TD>
													<TD align="left" valign="top" class="ColumnBackground"><bean:message key="collectingBankBranch"/></TD>
										            <TD class="tableData">
													<%if(collectingBankBranch!=null)
													{%>
													<%=collectingBankBranch%>
													<%}%></TD>
												</TR>

												<TR>
													<TD align="left" valign="top" class="ColumnBackground"><bean:message key="drawnAtBank"/></TD>
										            <TD class="tableData"><%=drawnAtBank%></TD>
													<TD align="left" valign="top" class="ColumnBackground"><bean:message key="drawnAtBranch"/></TD>
										            <TD class="tableData"><%=drawnAtBranch%></TD>
												</TR>

												<TR>
													<TD align="left" valign="top" class="ColumnBackground"><bean:message key="payableAt"/></TD>
										            <TD class="tableData"><%=payableAt%></TD>
													<TD align="left" valign="top" class="ColumnBackground"><bean:message key="allocatedAmount"/></TD>
										            <TD class="tableData" id="allocatedAmount">
														<%=df.format(allocatedAmount)%>
													</TD>
												</TR>

												<TR>
													<TD align="left" valign="top" class="ColumnBackground"><bean:message key="appropriatedAmount"/></TD>
										            <TD id="appropriatedAmount" class="tableData"><%=df.format(appropriatedAmount)%></TD>
													<TD align="left" valign="top" class="ColumnBackground"><bean:message key="shortExcess"/></TD>
										            <TD id="shortOrExcessAmount" class="tableData">0</TD>
												</TR>

												<TR>
													<TD align="left" valign="top" class="ColumnBackground">&nbsp;&nbsp;<font color="#FF0000" size="2">*</font>&nbsp;<bean:message key="dateOfRealisation"/></TD>
										            <TD class="tableData">
														<html:text property="dateOfRealisation" size="20" name="rpAllocationForm" maxlength="10"/>
														<IMG src="images/CalendarIcon.gif" width="20" onClick="showCalendar('rpAllocationForm.dateOfRealisation')" align="center">
													</TD>
													<TD align="left" valign="top" class="ColumnBackground">&nbsp;&nbsp;<font color="#FF0000" size="2">*</font>&nbsp;<bean:message key="receivedAmount"/></TD>
										            <TD class="tableData">
														<html:text property="receivedAmount" size="20" name="rpAllocationForm"/>
													</TD>
												</TR>
											</TABLE>
										</TD>
									</TR>
									<%} else {
										demandAdvice =(DemandAdvice)object;
										danNo = demandAdvice.getDanNo() ;
										danType = demandAdvice.getDanType() ;
										bankId = demandAdvice.getBankId() ;
										zoneId = demandAdvice.getZoneId() ;
										branchId = demandAdvice.getBranchId() ;
										cgpan = demandAdvice.getCgpan() ;
										danAmount = demandAdvice.getAmountRaised() ;
										reasons = demandAdvice.getReason() ;
										fee = demandAdvice.getAmountRaised() ;
										penalty = demandAdvice.getPenalty() ;
										allocatedFlag = demandAdvice.getAllocated() ;
                    status = demandAdvice.getStatus();
										totalAmt = fee + penalty ;

										if(!(prevDanType.equals(danType))) {%>
												<TR>
													<TD class="subHeading" height="21" colspan="8">
														<bean:message key="<%=danType%>" />
													</TD>
												</TR>
												<TR>
													<TD align="left" valign="top" class="ColumnBackground" rowspan="2">
														<bean:message key="sNo" />
													</TD>
													<TD align="left" valign="top" class="ColumnBackground" rowspan="2">
														<bean:message key="<%=(danType+\"DAN\")%>"/>
													</TD>
													<TD align="left" valign="top" class="ColumnBackground" rowspan="2">
														<bean:message key="cgpan" />
													</TD>
													<TD align="left" valign="top" class="ColumnBackground" colspan="3">
														<bean:message key="amount" />
													</TD>
													<TD align="left" valign="top" class="ColumnBackground" rowspan="2">
														<bean:message key="totalAmountDANWise" />
													</TD>
													<TD align="left" valign="top" class="ColumnBackground" rowspan="2">
														<bean:message key="reasons" />
													</TD>
                          <TD align="left" valign="top" class="ColumnBackground" rowspan="2">
														<bean:message key="status" />
													</TD>
													<TD align="left" valign="top" class="ColumnBackground" rowspan="2">
														<bean:message key="appropriate" />
													</TD>                          
													<TD align="left" valign="top" class="ColumnBackground" rowspan="2">
														<bean:message key="remarks" />
													</TD>
                          
                          
											</TR>
											<TR>
												<TD align="left" valign="top" class="ColumnBackground">
													<bean:message key="guaranteeFee" />
												</TD>
												<TD align="left" valign="top" class="ColumnBackground">
													<bean:message key="penalty" />
												</TD>
												<TD align="left" valign="top" class="ColumnBackground">
													<bean:message key="total" />
												</TD>
											</TR>
											<%prevDanType = danType ;
										}%>
											<TR>
												<TD align="left" valign="top" class="tableData">
													<%=index%>
												</TD>
												<TD align="left" valign="top" class="tableData">
													<%=danNo%>
													<%name="danIds(key-"+(index-1)+")";%>
													<html:hidden property="<%=name%>" name="rpAllocationForm" value="<%=danNo%>" />
												</TD>
												<TD align="left" valign="top" class="tableData">
													<%=cgpan%>
													<%name="cgpans(key-"+(index-1)+")";%>
													<html:hidden property="<%=name%>" name="rpAllocationForm" value="<%=cgpan%>" />
												</TD>
												<TD align="left" valign="top" class="tableData">
													<%=fee%>
													<%name="amountsRaised(key-"+(index-1)+")";%>
													<html:hidden property="<%=name%>" name="rpAllocationForm" value='<%=""+fee%>' />

												</TD>
												<TD align="left" valign="top" class="tableData">
													<%=penalty%>
													<%name="penalties(key-"+(index-1)+")";%>
													<html:hidden property="<%=name%>" name="rpAllocationForm" value='<%=""+penalty%>' />

												</TD>
												<TD align="left" valign="top" class="tableData">
													<%if (totalAmt==0)
													{
													%>
													-
													<%}else{%>
													<%=totalAmt%>
													<%}%>
												</TD>
												<TD align="left" valign="top" class="tableData">
													<%=0.00%>
												</TD>
                       
												<TD align="left" valign="top" class="tableData">
													<%if(reasons!=null && !reasons.equalsIgnoreCase("null"))
															{%>
													<%=reasons%>
													<%}%>
												</TD>
                         <TD align="left" valign="top" class="tableData">
													<%=status%>
												</TD>
                        
                        
<%--												<TD align="left" valign="top" class="tableData">
													<%name="appropriatedFlags(key-"+(index-1)+")";

													if(allocatedFlag.equals("Y")) {%>
														<input type="checkbox" name="appropriateFlag<%=(index-1)%>" value="Y" checked onclick="setTotalAppropriated()">
													<%}else{%>
														<input type="checkbox" name="appropriateFlag<%=(index-1)%>" value="N" onclick="setTotalAppropriated()">
													<%}%>
													<html:hidden property="<%=name%>" name="rpAllocationForm"/>

													<%name="allocatedFlags(key-"+(index-1)+")";%>
													<html:hidden property="<%=name%>" name="rpAllocationForm" value="<%=allocatedFlag%>" />
												</TD>--%>
												<TD align="left" valign="top" class="tableData">
													<%name="appropriatedFlags(key-"+(index-1)+")";%>

													<%
														if (fee==0)
														{
													%>
													<html:checkbox property="<%=name%>" value="" name="rpAllocationForm" disabled="true"/>
													<%
														}else{%>
														<html:checkbox property="<%=name%>" value="Y" name="rpAllocationForm" onclick="setTotalAppropriated()"/>
													<%}%>

													<%name="allocatedFlags(key-"+(index-1)+")";%>
													<html:hidden property="<%=name%>" name="rpAllocationForm" value="<%=allocatedFlag%>" />
												</TD>
												<TD align="left" valign="top" class="tableData">
													<%name="remarks(key-"+(index-1)+")";%>
													<html:textarea property="<%=name%>" rows="4" cols="40" name="rpAllocationForm" value=""/>
												</TD>
											</TR>
									<%}
									++index;%>
									</logic:iterate>
<%--								<html:hidden property="bankId" name="rpAllocationForm" value="<%=bankId%>"/>
									<html:hidden property="zoneId" name="rpAllocationForm" value="<%=zoneId%>"/>

									<html:hidden property="branchId" name="rpAllocationForm" value="<%=branchId%>"/>
--%>
				<TR>
					<TD align="center" valign="baseline" colspan="10">
					<DIV align="center">
					<A href="javascript:setChkHiddenValue('<%=(index-1)%>','appropriatedFlags', 'appropriatedFlags','afterAppropriatePaymentsForGF.do?method=appropriatePaymentsForGF','Y', 'N');">
							<IMG src="images/Save.gif" alt="Save" width="49" height="37" border="0"></A>
						<A href="javascript:document.rpAllocationForm.reset()">
							<IMG src="images/Reset.gif" alt="Cancel" width="49" height="37" border="0"></A>						
								<A href="home.do?method=getMainMenu&menuIcon=<%=session.getAttribute("menuIcon")%>">
								<IMG src="images/Cancel.gif" alt="Cancel" width="49" height="37" border="0"></A>
					</DIV>
					</TD>
				</TR>
<%--									<TR>
										<TD align="center" valign="baseline" colspan="10">
											<DIV align="center">
						<A href="javascript:setChkHiddenValue('<%=(index-1)%>','appropriatedFlags', 'appropriatedFlags','afterAppropriatePaymentsForGF.do?method=appropriatePaymentsForGF','Y', 'N');">
							<IMG src="images/Save.gif" alt="Save" width="49" height="37" border="0">
						</A>
						<A href="javascript:document.rpAllocationForm.reset()">
							<IMG src="images/Reset.gif" alt="Cancel" width="49" height="37" border="0">
						</A>
						<A href="subHome.do?method=getSubMenu&menuIcon=<%=session.getAttribute("menuIcon")%>&mainMenu=<%=session.getAttribute("mainMenu")%>">
						<IMG src="images/Cancel.gif" alt="Cancel" width="49" height="37" border="0"></A>
					</DIV>
					</TD>
				</TR>--%>
				</TABLE>
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
</TABLE>
	</html:form>
	</body>