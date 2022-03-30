<%@ page language="java"%>
<%@ page import="java.util.ArrayList"%>
<%@ page import="com.cgtsi.guaranteemaintenance.OutstandingDetail"%>
<%@ page import="com.cgtsi.guaranteemaintenance.OutstandingAmount"%>
<%@ page import="com.cgtsi.guaranteemaintenance.PeriodicInfo"%>
<%@ page import="java.text.SimpleDateFormat"%>
<%@ page import="java.text.DecimalFormat"%>
<%@ page import="java.util.Date"%>
<%@ include file="/jsp/SetMenuInfo.jsp" %>


<%@ taglib uri="/WEB-INF/TLD/struts-html.tld" prefix="html" %>
<%@ taglib uri="/WEB-INF/TLD/struts-bean.tld" prefix="bean" %>
<%@ taglib uri="/WEB-INF/TLD/struts-logic.tld" prefix="logic" %>


<%
session.setAttribute("CurrentPage","showOutstandingsForApproval.do?method=showOutstandingsForApproval");
%>

<TABLE width="725" border="0" cellpadding="0" cellspacing="0">
	<html:errors />
	<html:form action="showOutstandingsForApproval.do?method=showOutstandingsForApproval" >
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
												<TD width="40%" class="Heading"><bean:message key="outstandingDetailHeader"/><bean:write name="gmApprovalForm" property="borrowerId"/></TD>
												<TD><IMG src="images/TriangleSubhead.gif" width="19" height="19"></TD>
											</TR>
											<tr>
											  <td colspan="6" class="Heading" height="7" ><img
											  src="images/Clear.gif" width="5" height="5">
											   <A href="javascript:submitForm('showDisbursementsForApproval.do?method=showDisbursementsForApproval')">Disbursement
											  Details</A> | <A href="javascript:submitForm('showRepaymentsForApproval.do?method=showRepaymentsForApproval')">Repayment Details</A> |  <A href="javascript:submitForm('showNpaForApproval.do?method=showNpaForApproval')">NPA Details</A> | <A href="javascript:submitForm('showRecoveryForApproval.do?method=showRecoveryForApproval')">Recovery Details</A> | <A href="javascript:submitForm('showRepaySchForApproval.do?method=showRepaySchForApproval')">Repayment Schedule Details</A>
											  </td>
										</tr>
								  </table>
							  </td>
							</tr>

							<%
								ArrayList oldArr = new ArrayList();
								ArrayList newArr = new ArrayList();
								int i=0;
							%>

							<logic:iterate name="gmApprovalForm" property="outstandingDetails" id="object">
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

							 <tr>
								  <td colspan="6" valign="top">
									<table width="100%" border="0" cellspacing="1" cellpadding="0">
										<tr>
										  <td align="center" valign="middle" class="HeadingBg"
										  align="center"><center><bean:message key="srNo"/></td>
										  
										  <td class="HeadingBg" align="center" align="center"><center><bean:message key="cgpanNumber"/></td>
										  
										  <td class="HeadingBg" align="center" align="center"><center><bean:message key="scheme"/></td>
										  
										  <td class="HeadingBg" align="center" align="center"><center><bean:message key = "oldOsDetails"/></td>
										  
										  <td class="HeadingBg" align="center"><bean:message key = "newOsDetails"/></td>
										</tr>
								<%
								ArrayList totalArr = new ArrayList();
								int row=0;
								int rowNo=0;

								String oldCgpan;
								String newCgpan;

								String type;

								String oldTcoId;
								String newTcoId;

								String oldWcoId;
								String newWcoId;

								double oldAmount;
								String strOldAmount;
								double newAmount;
								String strNewAmount;

								double oldPrAmount;
								String strOldPrAmount="";
								double newPrAmount;
								String strNewPrAmount="";

								double oldNFBPrAmount;
								String strOldNFBPrAmount="";
								double newNFBPrAmount;
								String strNewNFBPrAmount="";

								double oldIntAmount;
								String strOldIntAmount="";
								double newIntAmount;
								String strNewIntAmount="";

								double oldNFBIntAmount;
								String strOldNFBIntAmount="";
								double newNFBIntAmount;
								String strNewNFBIntAmount="";

								Date oldDate;
								String strOldDate="";
								Date newDate;
								String strNewDate="";

								Date oldNFBDate;
								String strNFBOldDate="";
								Date newNFBDate;
								String strNFBNewDate="";

								SimpleDateFormat dateFormat=new SimpleDateFormat("dd/MM/yyyy");
								DecimalFormat df = new DecimalFormat("#############.##");
								df.setDecimalSeparatorAlwaysShown(false);

									int oldSize = oldArr.size();
									int newSize = newArr.size();
									for(int x=0;x<oldSize;x++)
									{
										OutstandingDetail oldDetail = (OutstandingDetail) oldArr.get(x);
										OutstandingDetail newDetail = (OutstandingDetail) newArr.get(x);

										oldCgpan = oldDetail.getCgpan();
										newCgpan = newDetail.getCgpan();
										type = oldCgpan.substring(oldCgpan.length()-2, oldCgpan.length()-1);

										ArrayList oldAmts = oldDetail.getOutstandingAmounts();
										ArrayList newAmts = newDetail.getOutstandingAmounts();
										int oldAmtSize=oldAmts.size();
										int newAmtSize=newAmts.size();
										row=0;
										for(int oldIndex=0;oldIndex<oldAmts.size();oldIndex++)
										{
											OutstandingAmount oldOsAmt = (OutstandingAmount) oldAmts.get(oldIndex);
											for(int newIndex=0;newIndex<newAmts.size();newIndex++)
											{
												OutstandingAmount newOsAmt = (OutstandingAmount) newAmts.get(newIndex);
												if (type.equals("T"))
												{
													if (newOsAmt.getTcoId() != null && (newOsAmt.getTcoId().equals(oldOsAmt.getTcoId())))
													{
														oldTcoId = oldOsAmt.getTcoId();
														newTcoId = newOsAmt.getTcoId();

														oldAmount = oldOsAmt.getTcPrincipalOutstandingAmount();
														strOldAmount = df.format(oldAmount);
														newAmount = newOsAmt.getTcPrincipalOutstandingAmount();
														strNewAmount = df.format(newAmount);

														oldDate = oldOsAmt.getTcOutstandingAsOnDate();
														if(oldDate!=null)
														{
															strOldDate = dateFormat.format(oldDate);
														}
														newDate = newOsAmt.getTcOutstandingAsOnDate();
														if(newDate!=null)
														{
														strNewDate = dateFormat.format(newDate);
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
																	<bean:message key="tcoId"/>
																</td>
																<td align="center" class="TableData"  align="center"><center>
																	<%=oldTcoId%>
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
																	<bean:message key="tcoId"/>
																</td>
																<td align="center" class="TableData"  align="center"><center>
																	<%=newTcoId%>
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
												else if (type.equals("W") || type.equals("R"))
												{
													if (newOsAmt.getWcoId() != null && (newOsAmt.getWcoId().equals(oldOsAmt.getWcoId())))
													{
														oldWcoId = oldOsAmt.getWcoId();
														newWcoId = newOsAmt.getWcoId();

														oldPrAmount = oldOsAmt.getWcFBPrincipalOutstandingAmount();
														if(oldPrAmount!=0)
														{
														strOldPrAmount = df.format(oldPrAmount);
														}
														newPrAmount = newOsAmt.getWcFBPrincipalOutstandingAmount();
														if(newPrAmount!=0)
														{
														strNewPrAmount = df.format(newPrAmount);
														}

														oldIntAmount = oldOsAmt.getWcFBInterestOutstandingAmount();
														if(oldIntAmount!=0)
														{
														strOldIntAmount = df.format(oldIntAmount);
														}
														newIntAmount = newOsAmt.getWcFBInterestOutstandingAmount();
														if(newIntAmount!=0)
														{
														strNewIntAmount = df.format(newIntAmount);
														}

														oldDate = oldOsAmt.getWcFBOutstandingAsOnDate();
														if(oldDate!=null)
															{
														strOldDate = dateFormat.format(oldDate);
															}
														newDate = newOsAmt.getWcFBOutstandingAsOnDate();
														if(newDate!=null)
														{
														strNewDate = dateFormat.format(newDate);
														}

														//non-fund based fields
														oldNFBPrAmount = oldOsAmt.getWcNFBPrincipalOutstandingAmount();
														if(oldNFBPrAmount!=0)
														{
														strOldNFBPrAmount = df.format(oldNFBPrAmount);
														}
														newNFBPrAmount = newOsAmt.getWcNFBPrincipalOutstandingAmount();
														if(newNFBPrAmount!=0)
														{
														strNewNFBPrAmount = df.format(newNFBPrAmount);
														}

														oldNFBIntAmount = oldOsAmt.getWcNFBInterestOutstandingAmount();
														if(oldNFBIntAmount!=0)
														{
														strOldNFBIntAmount = df.format(oldNFBIntAmount);
														}
														newNFBIntAmount = newOsAmt.getWcNFBInterestOutstandingAmount();
														if(newNFBIntAmount!=0)
														{
														strNewNFBIntAmount = df.format(newNFBIntAmount);
														}

														oldNFBDate = oldOsAmt.getWcNFBOutstandingAsOnDate();
														if(oldNFBDate!=null)
															{
														strNFBOldDate = dateFormat.format(oldNFBDate);
															}
														newNFBDate = newOsAmt.getWcNFBOutstandingAsOnDate();
														if(newNFBDate!=null)
														{
														strNFBNewDate = dateFormat.format(newNFBDate);
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
																	<bean:message key="wcoId"/>
																</td>
																<td align="center" class="TableData"  align="center"><center>
																	<%=oldWcoId%>
																</td>
															</tr>
															<tr>
																<td align="center" class="TableData"  align="center"><center>
																	<bean:message key="principalamount"/>
																</td>
																<td align="center" class="TableData"  align="center"><center>
																	<%=strOldPrAmount%>
																</td>
															</tr>
															<tr>
																<td align="center" class="TableData"  align="center"><center>
																	<bean:message key="interestamount"/>
																</td>
																<td align="center" class="TableData"  align="center"><center>
																	<%=strOldIntAmount%>
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

															<tr>
																<td align="center" class="TableData"  align="center"><center>
																	Non Fund Based Principal
																</td>
																<td align="center" class="TableData"  align="center"><center>
																	<%=strOldNFBPrAmount%>
																</td>
															</tr>
															<tr>
																<td align="center" class="TableData"  align="center"><center>
																	Non Fund Based Commission
																</td>
																<td align="center" class="TableData"  align="center"><center>
																	<%=strOldNFBIntAmount%>
																</td>
															</tr>

															<tr>
																<td align="center" class="TableData"  align="center"><center>
																	Non Fund Based Date
																</td>
																<td align="center" class="TableData"  align="center"><center>
																	<%=strNFBOldDate%>
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
																	<bean:message key="wcoId"/>
																</td>
																<td align="center" class="TableData"  align="center"><center>
																	<%=newWcoId%>
																</td>
															</tr>
															<tr>
																<td align="center" class="TableData"  align="center"><center>
																	<bean:message key="principalamount"/>
																</td>
																<td align="center" class="TableData"  align="center"><center>
																	<%=strNewPrAmount%>
																</td>
															</tr>
															<tr>
																<td align="center" class="TableData"  align="center"><center>
																	<bean:message key="interestamount"/>
																</td>
																<td align="center" class="TableData"  align="center"><center>
																	<%=strNewIntAmount%>
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

															<tr>
																<td align="center" class="TableData"  align="center"><center>
																	Non Fund Based Principal
																</td>
																<td align="center" class="TableData"  align="center"><center>
																	<%=strNewNFBPrAmount%>
																</td>
															</tr>
															<tr>
																<td align="center" class="TableData"  align="center"><center>
																	Non Fund Based Commission
																</td>
																<td align="center" class="TableData"  align="center"><center>
																	<%=strNewNFBIntAmount%>
																</td>
															</tr>
															<tr>
																<td align="center" class="TableData"  align="center"><center>
																	Non Fund Based Date
																</td>
																<td align="center" class="TableData"  align="center"><center>
																	<%=strNFBNewDate%>
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
												newOsAmt=null;
											}
											oldOsAmt=null;	
										}
										oldDetail=null;
										newDetail=null;
										oldAmts=null;
										newAmts=null;
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
