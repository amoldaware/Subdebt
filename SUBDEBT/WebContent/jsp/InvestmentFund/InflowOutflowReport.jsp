<%@ page language="java"%>
<%@ taglib uri="/WEB-INF/TLD/struts-html.tld" prefix="html" %>
<%@ taglib uri="/WEB-INF/TLD/struts-bean.tld" prefix="bean" %>
<%@ taglib uri="/WEB-INF/TLD/struts-logic.tld" prefix="logic" %>
<%@ include file="/jsp/SetMenuInfo.jsp" %>
<%@ page import="java.text.DecimalFormat"%>
<%@ page import="com.cgtsi.investmentfund.InflowOutflowReport"%>
<%@ page import="com.cgtsi.investmentfund.InvestmentMaturityDetails"%>
<%@ page import="com.cgtsi.investmentfund.FundTransferDetail"%>
<%@ page import="com.cgtsi.investmentfund.ActualIOHeadDetail"%>
<% 
	session.setAttribute("CurrentPage","showInflowOutflowReport.do?method=showInflowOutflowReport");
	String focusObj="counter";
%>
<%
DecimalFormat df= new DecimalFormat("######################.##");
df.setDecimalSeparatorAlwaysShown(false);
int counter=0;
%>
<body>
<html:form action="saveInflowOutflowReportInput.do?method=saveInflowOutflowReportInput"  method="POST" >
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
								<TR>
									<TD>
										<TABLE width="100%" border="0" cellspacing="0" cellpadding="0">
											<TR>
												<TD width="40%" class="Heading"><bean:message key="InflowOutflowDetails" /> &nbsp; for <bean:write name="investmentForm" property="valueDate"/></TD>
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
												<TD align="center" valign="top" class="ColumnBackground" rowspan="2">
												<div align="center">
													&nbsp;&nbsp;<bean:message key="BankName" /><br> &nbsp;(1)
													</div>
												</TD>
												<TD align="left" valign="top" class="ColumnBackground" rowspan="2">
												<div align="center">
													&nbsp;&nbsp;<bean:message key="balanceAsPerStmt" /> <br>&nbsp;(2)
													</div>
												</TD>
												<TD align="left" valign="top" class="ColumnBackground" rowspan="2">
												<div align="center">
													&nbsp;&nbsp;<bean:message key="balanceAvail" /> <br>&nbsp;(3)
													</div>
												</TD>
												<TD align="left" valign="top" class="ColumnBackground" colspan="3">
												<div align="center">
													&nbsp;&nbsp;<bean:message key="inflowBy" /> <br>&nbsp;(4)
													</div>
												</TD>
												<TD align="left" valign="top" class="ColumnBackground" rowspan="2">
												<div align="center">
													&nbsp;&nbsp;<bean:message key="AmtTransferredFD" /><br>&nbsp;(5)
													</div>
												</TD>
												<TD align="left" valign="top" class="ColumnBackground" rowspan="2">
												<div align="center">
													&nbsp;&nbsp;<bean:message key="netBalance" /><br>&nbsp;(6)=(3)+(4a)+(4b)+(4c)-(5)
													</div>
												</TD>
												<TD align="left" valign="top" class="ColumnBackground" rowspan="2">
												<div align="center">
													&nbsp;&nbsp;<bean:message key="chqIssuedAmount" /><br>&nbsp;(7)
													&nbsp;<bean:message key="inRs" />
													</div>
												</TD>
												<TD align="left" valign="top" class="ColumnBackground" rowspan="2">
												<div align="center">
													&nbsp;&nbsp;<bean:message key="minBalanceCA" /><br>&nbsp;(8)
													</div>
												</TD>
												<TD align="left" valign="top" class="ColumnBackground" rowspan="2">
												<div align="center">
													&nbsp;&nbsp;<bean:message key="fundsProvided" /><br>&nbsp;(9)
													</div>
												</TD>
												<TD align="left" valign="top" class="ColumnBackground" rowspan="2">
												<div align="center">
													&nbsp;&nbsp;<bean:message key="surplusShortfall" /><br>&nbsp;(10)
													</div>
												</TD>
												<TD align="left" valign="top" class="ColumnBackground" rowspan="2">
												<div align="center">
													&nbsp;&nbsp;<bean:message key="remarks" /><br>&nbsp;(11)
												</div>
												</TD>
											</TR>
											<TR>
												<TD align="left" valign="top" class="ColumnBackground">
												<div align="center">
													&nbsp;&nbsp;<bean:message key="maturityInv" /> <br>&nbsp;(4a)
													</div>
												</TD>
												<TD align="left" valign="top" class="ColumnBackground">
												<div align="center">
													&nbsp;&nbsp;<bean:message key="fundTansfer" /><br>&nbsp;(4b)
													</div>
												</TD>
												<TD align="left" valign="top" class="ColumnBackground">
												<div align="center">
													&nbsp;&nbsp;<bean:message key="MiscReceiptsHeading" /><br>&nbsp;(4c)
													</div>
												</TD>
											</TR>
											<% 
												String ioIdKey=null;
												String ioBankNameKey=null;
												String ioStmtBalanceKey=null;
												String ioMatInvKey=null;
												String ioAvailBalanceKey=null;
												String ioMinBalanceKey=null;
												String ioAmtCAKey=null;
												String ioFundTransferKey=null;
												String ioMiscReceiptsKey=null;
												String ioRemarksKey=null;
												String ioNetBalanceKey=null;
												String ioSurplusShortKey=null;
												String ioChqIssuedKey=null;
												String ioProvisionKey=null;
											%>
											<logic:iterate id="planInv" name="investmentForm" property="planInvMainDetails">

												<%
													ioIdKey="planInvMainDetails(key-"+counter+").id";
													ioBankNameKey="planInvMainDetails(key-"+counter+").bankName";
													ioStmtBalanceKey="planInvMainDetails(key-"+counter+").stmtBalance";
													ioMatInvKey="planInvMainDetails(key-"+counter+").maturityInflow";
													ioAvailBalanceKey="planInvMainDetails(key-"+counter+").availBalance";
													ioMinBalanceKey="planInvMainDetails(key-"+counter+").minBalance";
													ioMinBalanceKey="planInvMainDetails(key-"+counter+").id";
													ioAmtCAKey="planInvMainDetails(key-"+counter+").caNotReflectedAmt";
													ioFundTransferKey="planInvMainDetails(key-"+counter+").fundTransferInflow";
													ioMiscReceiptsKey="planInvMainDetails(key-"+counter+").miscReceiptsInflow";
													ioRemarksKey="planInvMainDetails(key-"+counter+").remarks";
													ioNetBalanceKey="planInvMainDetails(key-"+counter+").netBalance";
													ioSurplusShortKey="planInvMainDetails(key-"+counter+").surplusShort";
													ioChqIssuedKey="planInvMainDetails(key-"+counter+").chqissuedAmt";
													ioProvisionKey="planInvMainDetails(key-"+counter+").provisionFundsAmt";
												%>
											<TR>
												<TD class="tableData">
													<bean:write name="investmentForm" property="<%=ioBankNameKey%>"/>
													<html:hidden name="investmentForm" property="<%=ioBankNameKey%>"/>
													<html:hidden name="investmentForm" property="<%=ioIdKey%>"/>
												</TD>
												<TD align="left" valign="top" class="tableData">
													<bean:write name="investmentForm" property="<%=ioStmtBalanceKey%>"/>
												</TD>
												<TD align="left" valign="top" class="tableData">
													<bean:write name="investmentForm" property="<%=ioAvailBalanceKey%>"/>
												</TD>
												<TD align="left" valign="top" class="tableData">
													<bean:write name="investmentForm" property="<%=ioMatInvKey%>"/>
												</TD>
												<TD align="left" valign="top" class="tableData">
													<bean:write name="investmentForm" property="<%=ioFundTransferKey%>"/>
												</TD>
												<TD align="left" valign="top" class="tableData">
													<bean:write name="investmentForm" property="<%=ioMiscReceiptsKey%>"/>
												</TD>
												<TD align="left" valign="top" class="tableData">
													<bean:write name="investmentForm" property="<%=ioAmtCAKey%>"/>
												</TD>
												<TD align="left" valign="top" class="tableData">
													<bean:write name="investmentForm" property="<%=ioNetBalanceKey%>"/>
												</TD>
												<TD align="left" valign="top" class="tableData">
													<bean:write name="investmentForm" property="<%=ioChqIssuedKey%>"/>
												</TD>
												<TD align="left" valign="top" class="tableData">
													<bean:write name="investmentForm" property="<%=ioMinBalanceKey%>"/>
												</TD>
												<TD class="tableData">
													<bean:write name="investmentForm" property="<%=ioProvisionKey%>"/>
												</TD>
												<TD align="left" valign="top" class="tableData">
													<bean:write name="investmentForm" property="<%=ioSurplusShortKey%>"/>
												</TD>
												<TD align="left" valign="top" class="tableData">
													<html:textarea property="<%=ioRemarksKey%>" cols="25" rows="3" name="investmentForm"/>
												</TD>
											</TR>


											<%
											if (counter==0)
											{
											%>
											<html:hidden name="investmentForm" property="counter"/>
											<%
											}
											counter++;
											%>
											</logic:iterate>
										</TABLE>
									</TD>
								</TR>
								<TR>
									<td class="SubHeading">
									<bean:message key="inflowBy" />&nbsp;<bean:message key="maturityInv"/></td>
								</TR>
								<TR>
									<TD>
										<TABLE width="100%" border="0" cellspacing="1" cellpadding="0">
											<TR>
												<TD align="left" valign="top" class="ColumnBackground">
												<div align="center">
													&nbsp;&nbsp;<bean:message key="investmentNumber" />
													</div>
												</TD>
												<TD align="left" valign="top" class="ColumnBackground">
												<div align="center">
													&nbsp;&nbsp;<bean:message key="amount" />
													</div>
												</TD>
											</TR>
											<%double total=0;%>
											<logic:iterate id="planInvMat" name="investmentForm" property="planInvMatDetails">

												<%
													InvestmentMaturityDetails invMatDetail=(InvestmentMaturityDetails)planInvMat;
													if (!invMatDetail.getMaturityAmt().trim().equals(""))
													{
														total += Double.parseDouble(invMatDetail.getMaturityAmt());
													}
													String refNo = invMatDetail.getBuySellId();
													if (refNo.equals(""))
													{
														refNo=invMatDetail.getOtherDesc();
													}
												%>
												<TR>
													<TD class="tableData">
														<%=refNo%>
													</TD>
													<TD align="left" valign="top" class="tableData">
													<div align="right">
														<%=invMatDetail.getMaturityAmt()%>
													</div>
													</TD>
												</TR>
											</logic:iterate>
											<TR>
												<TD align="left" valign="top" class="ColumnBackground">
												<div align="center">
													&nbsp;&nbsp;<bean:message key="total" />
													</div>
												</TD>
												<TD align="left" valign="top" class="ColumnBackground">
												<div align="right">
													&nbsp;&nbsp;<%=df.format(total)%>
													</div>
												</TD>
											</TR>
										</TABLE>
									</TD>
								</TR>
								<TR>
									<td class="SubHeading">
									<bean:message key="fundTransferDetails"/></td>
								</TR>
								<TR>
									<TD>
										<TABLE width="100%" border="0" cellspacing="1" cellpadding="0">
											<TR>
												<TD align="left" valign="top" class="ColumnBackground">
												<div align="center">
													&nbsp;&nbsp;<bean:message key="fromBank" />
													</div>
												</TD>
												<TD align="left" valign="top" class="ColumnBackground">
												<div align="center">
													&nbsp;&nbsp;<bean:message key="amount" />
													</div>
												</TD>
											</TR>
											<% total =0 ;%>
											<logic:iterate id="planInvFT" name="investmentForm" property="planInvFTDetails">

												<%
													FundTransferDetail ftDetail=(FundTransferDetail)planInvFT;
													total += Double.parseDouble(ftDetail.getAmtForIDBI());
												%>
												<TR>
													<TD class="tableData">
														<%=ftDetail.getBankName()%>
													</TD>
													<TD align="left" valign="top" class="tableData">
													<div align="right">
														<%=ftDetail.getAmtForIDBI()%>
													</div>
													</TD>
												</TR>
											</logic:iterate>
											<TR>
												<TD align="left" valign="top" class="ColumnBackground">
												<div align="center">
													&nbsp;&nbsp;<bean:message key="total" />
													</div>
												</TD>
												<TD align="left" valign="top" class="ColumnBackground">
												<div align="right">
													&nbsp;&nbsp;<%=df.format(total)%>
													</div>
												</TD>
											</TR>
										</TABLE>
									</TD>
								</TR>
								<TR>
									<td class="SubHeading">
									<bean:message key="provisionForPayment"/></td>
								</TR>
								<TR>
									<TD>
										<TABLE width="100%" border="0" cellspacing="1" cellpadding="0">
											<TR>
												<TD align="left" valign="top" class="ColumnBackground">
												<div align="center">
													&nbsp;&nbsp;<bean:message key="SlNo" />
													</div>
												</TD>
												<TD align="left" valign="top" class="ColumnBackground">
												<div align="center">
													&nbsp;&nbsp;<bean:message key="provisionFor" />
													</div>
												</TD>
												<TD align="left" valign="top" class="ColumnBackground">
												<div align="center">
													&nbsp;&nbsp;<bean:message key="amount" />
													</div>
												</TD>
												<TD align="left" valign="top" class="ColumnBackground">
												<div align="center">
													&nbsp;&nbsp;<bean:message key="remarks" />
													</div>
												</TD>
											</TR>
											<%
											counter=0;
											total = 0; %>
											<logic:iterate id="planInvProvision" name="investmentForm" property="planInvProvisionDetails">

												<%
													ActualIOHeadDetail ioDetail=(ActualIOHeadDetail)planInvProvision;
													String name = "provisionRemarks(key-"+ioDetail.getId()+")";
													String head = ioDetail.getBudgetHead();
													String strAmount = df.format(ioDetail.getBudgetAmount());
													total += ioDetail.getBudgetAmount();
													String remarks = "";
													if (ioDetail.getRemarks()!=null)
													{
														remarks=ioDetail.getRemarks();
													}
												%>
												<TR>
													<TD class="tableData">
														<%=counter+1%>
													</TD>
													<TD class="tableData">
														<%=head%>
													</TD>
													<TD align="left" valign="top" class="tableData">
													<div align="right">
														<%=strAmount%>
													</div>
													</TD>
													<TD align="left" valign="top" class="tableData">
														<html:textarea property="<%=name%>" value="<%=remarks%>" cols="25" rows="3" name="investmentForm"/>
													</TD>
												</TR>
											</logic:iterate>
											<TR>
												<TD align="left" valign="top" class="ColumnBackground" colspan="2">
												<div align="center">
													&nbsp;&nbsp;<bean:message key="total" />
													</div>
												</TD>
												<TD align="left" valign="top" class="ColumnBackground">
												<div align="right">
													&nbsp;&nbsp;<%=df.format(total)%>
													</div>
												</TD>
												<TD align="left" valign="top" class="ColumnBackground">

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
									<a href="javascript:submitForm('saveInflowOutflowReportInput.do?method=saveInflowOutflowReportInput');"><img src="images/Save.gif" alt="Save"
										width="49" height="37" border="0"></a>
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
</body>