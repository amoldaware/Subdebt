<%@ page language="java"%>
<%@ taglib uri="/WEB-INF/TLD/struts-html.tld" prefix="html" %>
<%@ taglib uri="/WEB-INF/TLD/struts-bean.tld" prefix="bean" %>
<%@ taglib uri="/WEB-INF/TLD/struts-logic.tld" prefix="logic" %>
<%@ include file="/jsp/SetMenuInfo.jsp" %>
<%@ page import="com.cgtsi.investmentfund.InvestmentMaturityDetails"%>
<%@ page import="java.util.Map"%>
<%@ page import="java.text.SimpleDateFormat"%>
<% 
	session.setAttribute("CurrentPage","showInvstMaturingDetails.do?method=showInvstMaturingDetails");
	String focusObj="counter";
	org.apache.struts.action.ActionErrors errors = (org.apache.struts.action.ActionErrors)request.getAttribute(org.apache.struts.Globals.ERROR_KEY);
	if (errors!=null && !errors.isEmpty())
	{
		focusObj="test";
	}
%>

<body onload="displayMaturityAmtsTotal()">
<html:form action="showInflowOutflowReport.do?method=showInflowOutflowReport" method="POST" focus="<%=focusObj%>">
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
										<A HREF="javascript:submitForm('helpInvstMaturity.do')">
										HELP</A>
									</DIV>
								</td>
							  </tr>

								<TR>
									<TD>
										<TABLE width="100%" border="0" cellspacing="0" cellpadding="0">
											<TR>
												<TD width="40%" class="Heading"><bean:message key="InflowOutflowDetails" /></TD>
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
											<bean:message key="invstMaturingDetails"/>&nbsp;<bean:write name="investmentForm" property="valueDate"/></td>
											</tr>
											<TR>

												<TD align="center" valign="top" class="ColumnBackground">
												<div align="center">
													&nbsp;&nbsp;<bean:message key="SlNo" /> <br>&nbsp;(1)
													</div>
												</TD>
												<TD align="left" valign="top" class="ColumnBackground">
												<div align="center">
													&nbsp;&nbsp;<bean:message key="expInvesteeName" /> <br>&nbsp;(2)
													</div>
												</TD>
												<TD align="left" valign="top" class="ColumnBackground">
												<div align="center">
													&nbsp;&nbsp;<bean:message key="dateOfDeposit" /> <br>&nbsp;(3)
													</div>
												</TD>
												<TD align="left" valign="top" class="ColumnBackground">
												<div align="center">
													&nbsp;&nbsp;<bean:message key="maturityAmount" /> &nbsp;<bean:message key="inRs" /><br>&nbsp;(4)
													</div>
												</TD>
												<TD align="left" valign="top" class="ColumnBackground">
												<div align="center">
													&nbsp;&nbsp;<bean:message key="exposureInvestment" /><br>&nbsp;(5)<br>
													</div>
												</TD>
											</TR>
											<% 
												int counter=0; 

												String pliIdKey=null;
												String pliInvNameKey=null;
												String pliDateOfDepositkey=null;
												String pliMatAmtKey=null;
												String pliInvFlagKey=null;
												String pliOtherDescKey=null;
												String pliBuySellIdKey=null;
												boolean other=false;
												boolean otherAvail = false;
												SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
											%>

											<logic:iterate id="ft" name="investmentForm" property="invstMaturingDetails">

												<%
													java.util.Map.Entry ftEntry = (java.util.Map.Entry) ft;
													InvestmentMaturityDetails imDetails = (InvestmentMaturityDetails)ftEntry.getValue();
													pliIdKey="invstMaturingDetails(key-"+counter+").pliId";
													pliInvNameKey="invstMaturingDetails(key-"+counter+").invName";
													pliDateOfDepositkey="invstMaturingDetails(key-"+counter+").purchaseDate";
													pliMatAmtKey="invstMaturingDetails(key-"+counter+").maturityAmt";
													pliInvFlagKey="invstMaturingDetails(key-"+counter+").invFlag";
													pliOtherDescKey="invstMaturingDetails(key-"+counter+").otherDesc";
													pliBuySellIdKey="invstMaturingDetails(key-"+counter+").buySellId";
													String dateStr ="";
													if (imDetails.getInvName()==null || imDetails.getInvName().equals(""))
													{
														other=true;
														otherAvail=true;
													}
													else
													{
														other=false;
														dateStr = dateFormat.format(imDetails.getPurchaseDate());
													}
												%>
											<TR>
												<TD class="tableData"><div align="center">
												<%=counter+1%><html:hidden name="investmentForm" property="<%=pliIdKey%>"/>
												<html:hidden name="investmentForm" property="<%=pliBuySellIdKey%>"/><div>
												</TD>
												<TD align="left" valign="top" class="tableData"><div align="center">
												<%
												if (!other)
												{
												%>
													<bean:write name="investmentForm" property="<%=pliInvNameKey%>"/>
													<html:hidden name="investmentForm" property="<%=pliInvNameKey%>"/>
												<%
												}else{
												%>
													<html:text property="<%=pliOtherDescKey%>" size="15" name="investmentForm" maxlength="20"/>
												<%}%><div>
												</TD>
												<TD align="left" valign="top" class="tableData">
												<%
												if (!other)
												{
												%><div align="center">
													<bean:write name="investmentForm" property="<%=pliDateOfDepositkey%>"/>
													<html:hidden name="investmentForm" property="<%=pliDateOfDepositkey%>"/><div>
												<%}%>
												</TD>
												<TD align="left" valign="top" class="tableData"><div align="center">
													<html:text property="<%=pliMatAmtKey%>" size="15" name="investmentForm" maxlength="16"  onkeypress="return decimalOnly(this, event,13)" onkeyup="isValidDecimal(this)" onblur="displayMaturityAmtsTotal()"/></div>
												</TD>
												<TD class="tableData"><div align="center">
													<html:radio name="investmentForm" value="Y" property="<%=pliInvFlagKey%>"  onclick="displayMaturityAmtsTotal()"> Yes </html:radio>
													<html:radio name="investmentForm" value="N" property="<%=pliInvFlagKey%>" onclick="displayMaturityAmtsTotal()"> No </html:radio></div>
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

										<TR>
												<TD class="ColumnBackground" colspan="3">
													<bean:message key="total" />
												</TD>
												<TD class="tableData" colspan="2" id="totalMatAmt">

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
									<a href="javascript:submitForm('showInflowOutflowReport.do?method=showInflowOutflowReport');"><img src="images/Save.gif" alt="Save"
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