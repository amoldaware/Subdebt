<%@ page language="java"%>
<%@ page import="java.util.ArrayList"%>
<%@ page import="com.cgtsi.guaranteemaintenance.Repayment"%>
<%@ page import="com.cgtsi.guaranteemaintenance.RepaymentAmount"%>
<%@ page import="com.cgtsi.guaranteemaintenance.PeriodicInfo"%>
<%@ page import="java.text.SimpleDateFormat"%>
<%@ page import="java.text.DecimalFormat"%>
<%@ page import="java.util.Date"%>
<%@ include file="/jsp/SetMenuInfo.jsp" %>


<%@ taglib uri="/WEB-INF/TLD/struts-html.tld" prefix="html" %>
<%@ taglib uri="/WEB-INF/TLD/struts-bean.tld" prefix="bean" %>
<%@ taglib uri="/WEB-INF/TLD/struts-logic.tld" prefix="logic" %>


<%
session.setAttribute("CurrentPage","showRepaymentsForApproval.do?method=showRepaymentsForApproval");
%>

<TABLE width="725" border="0" cellpadding="0" cellspacing="0">
	<html:errors />
	<html:form action="showRepaymentsForApproval.do?method=showRepaymentsForApproval" >
		<TR> 
			<TD width="20" align="right" valign="bottom"><IMG src="images/TableLeftTop.gif" width="20" height="31"></TD>
			<TD background="images/TableBackground1.gif"><IMG src="images/GuaranteeMaintenanceHeading.gif"></TD>
			<TD width="20" align="left" valign="bottom"><IMG src="images/TableRightTop.gif" width="23" height="31"></TD>
		</TR>
		<TR>
			<TD width="20" background="images/TableVerticalLeftBG.gif">&nbsp;</TD>
			<TD>
				<TABLE width="100%" border="0" align="left" cellpadding="0" cellspacing="0">
					<TR>
						<TD>
							<TABLE width="100%" border="0" cellspacing="1" cellpadding="1">
								<TR>
									<TD colspan="6"> 
										<TABLE width="100%" border="0" cellpadding="0" cellspacing="0">
											<TR>
												<TD width="40%" class="Heading"><bean:message key="repaymentDetailsHeader"/><bean:write name="gmApprovalForm" property="borrowerId"/></TD>
												<TD><IMG src="images/TriangleSubhead.gif" width="19" height="19"></TD>
											</TR>
											<tr>
											  <td colspan="6" class="Heading" height="7" ><img
											  src="images/Clear.gif" width="5" height="5">
											   <A href="javascript:submitForm('showOutstandingsForApproval.do?method=showOutstandingsForApproval')">Outstanding
											  Details</A> | <A href="javascript:submitForm('showDisbursementsForApproval.do?method=showDisbursementsForApproval')">Disbursement Details</A>| <A href="javascript:submitForm('showNpaForApproval.do?method=showNpaForApproval')">NPA Details</A>|<A href="javascript:submitForm('showRecoveryForApproval.do?method=showRecoveryForApproval')">Recovery Details</A> | <A href="javascript:submitForm('showRepaySchForApproval.do?method=showRepaySchForApproval')">Repayment Schedule
											  </td>
										</tr>
								  </table>
							  </td>
							</tr>


							 <tr>
								  <td colspan="6" valign="top">
									<table width="100%" border="0" cellspacing="1" cellpadding="0">
										<tr>
										  <td align="center" valign="middle" class="HeadingBg"
										  align="center"><center><bean:message key="srNo"/></td>
										  
										  <td class="HeadingBg" align="center" align="center"><center><bean:message key="cgpanNumber"/></td>
										  
										  <td class="HeadingBg" align="center" align="center"><center><bean:message key="scheme"/></td>
										  
										  <td class="HeadingBg" align="center" align="center"><center><bean:message key = "oldRepayDetails"/></td>
										  
										  <td class="HeadingBg" align="center"><bean:message key = "newRepayDetails"/></td>
								</tr>
								<%
								ArrayList totalArr = new ArrayList();
								ArrayList oldArr = new ArrayList();
								ArrayList newArr = new ArrayList();
								int i=0;
								int row=0;
								int rowNo=0;

								String oldCgpan;
								String newCgpan;

								String type;

								String oldRepayId;
								String newRepayId;

								double oldAmount;
								String strOldAmount;
								double newAmount;
								String strNewAmount;

								Date oldDate;
								String strOldDate="";
								Date newDate;
								String strNewDate="";

								SimpleDateFormat dateFormat=new SimpleDateFormat("dd/MM/yyyy");
								DecimalFormat df = new DecimalFormat("#############.##");
								df.setDecimalSeparatorAlwaysShown(false);
								%>
								<logic:iterate name="gmApprovalForm" property="repaymentDetails" id="object">
								<%
								if (i==0)
								{
									oldArr = (ArrayList) object;
								}
								else if (i==1)
								{
									newArr = (ArrayList) object;
								}
								i++;
								%>
								</logic:iterate>
								<%
									int oldSize = oldArr.size();
									int newSize = newArr.size();
									for(int x=0;x<oldSize;x++)
									{
										Repayment oldDetail = (Repayment) oldArr.get(x);
										Repayment newDetail = (Repayment) newArr.get(x);

										oldCgpan = oldDetail.getCgpan();
										newCgpan = newDetail.getCgpan();

										ArrayList oldAmts = oldDetail.getRepaymentAmounts();
										ArrayList newAmts = newDetail.getRepaymentAmounts();
										int oldAmtSize=oldAmts.size();
										int newAmtSize=newAmts.size();
										row=0;
										for(int oldIndex=0;oldIndex<oldAmts.size();oldIndex++)
										{
											RepaymentAmount oldRepayAmt = (RepaymentAmount) oldAmts.get(oldIndex);
											for(int newIndex=0;newIndex<newAmts.size();newIndex++)
											{
												RepaymentAmount newRepayAmt = (RepaymentAmount) newAmts.get(newIndex);
												if (oldRepayAmt.getRepayId().equals( newRepayAmt.getRepayId()))
												{
													oldRepayId=oldRepayAmt.getRepayId();
													newRepayId=newRepayAmt.getRepayId();

													oldAmount=oldRepayAmt.getRepaymentAmount();
													strOldAmount=df.format(oldAmount);
													newAmount=newRepayAmt.getRepaymentAmount();
													strNewAmount=df.format(newAmount);

													oldDate=oldRepayAmt.getRepaymentDate();
													if(oldDate!=null)
													{
														strOldDate=dateFormat.format(oldDate);
													}

													newDate=newRepayAmt.getRepaymentDate();
													if(newDate!=null)
													{
													strNewDate=dateFormat.format(newDate);
													}
												%>
												<TR>
													<%
													if (row==0)
													{
													%>
													<TD align="center" valign="middle" class="TableData"
													  align="center">
													  <center><%=(++rowNo)%>
													</TD>
													<TD align="center" valign="middle" class="TableData"
													  align="center">
													  <center><%=oldCgpan%>
													</TD>
													<TD align="center" valign="middle" class="TableData"
													  align="center">
													  <center>
													</TD>
													<%
													}
													else
													{
													%>
													<TD align="center" valign="middle" class="TableData"
													  align="center">
													  <center>
													</TD>
													<TD align="center" valign="middle" class="TableData"
													  align="center">
													  <center>
													</TD>
													<TD align="center" valign="middle" class="TableData"
													  align="center">
													  <center>
													</TD>
													<%
													}
													%>
													<TD align="center" valign="middle" class="TableData"
													  align="center">
													  <center>
														<table width="100%" border="0" cellspacing="1" cellpadding="0">
															<tr>
																<td align="center" class="TableData"  align="center"><center>
																	<bean:message key="repayId"/>
																</td>
																<td align="center" class="TableData"  align="center"><center>
																	<%=oldRepayId%>
																</td>
															</tr>
															<tr>
																<td align="center" class="TableData"  align="center"><center>
																	<bean:message key="amount"/>
																</td>
																<td align="center" class="TableData"  align="center"><center>
																	<%=strOldAmount%>
																</td>
															</tr>
															<tr>
																<td align="center" class="TableData"  align="center"><center>
																	<bean:message key="date"/>
																</td>
																<td align="center" class="TableData"  align="center"><center>
																	<%=strOldDate%>
																</td>
															</tr>
														</table>
													</TD>
													<TD align="center" valign="middle" class="TableData"
													  align="center">
													  <center>
														<table width="100%" border="0" cellspacing="1" cellpadding="0">
															<tr>
																<td align="center" class="TableData"  align="center"><center>
																	<bean:message key="repayId"/>
																</td>
																<td align="center" class="TableData"  align="center"><center>
																	<%=newRepayId%>
																</td>
															</tr>
															<tr>
																<td align="center" class="TableData"  align="center"><center>
																	<bean:message key="amount"/>
																</td>
																<td align="center" class="TableData"  align="center"><center>
																	<%=strNewAmount%>
																</td>
															</tr>
															<tr>
																<td align="center" class="TableData"  align="center"><center>
																	<bean:message key="date"/>
																</td>
																<td align="center" class="TableData"  align="center"><center>
																	<%=strNewDate%>
																</td>
															</tr>
														</table>
													</TD>
												</TR>
												<%

													row++;
													break;
												}
											}
											
										}
									}
								%>
           
								</table>    
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
													<A href="javascript:window.history.back()">
													<IMG src="images/Back.gif" alt="Cancel" width="49" height="37" border="0"></A>
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
			</body>
		</TABLE>
