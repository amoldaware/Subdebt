<%@ page language="java"%>
<%@ page import="java.util.ArrayList"%>
<%@ page import="com.cgtsi.guaranteemaintenance.Recovery"%>
<%@ page import="java.text.SimpleDateFormat"%>
<%@ page import="java.text.DecimalFormat"%>
<%@ page import="java.util.Date"%>
<%@ include file="/jsp/SetMenuInfo.jsp" %>


<%@ taglib uri="/WEB-INF/TLD/struts-html.tld" prefix="html" %>
<%@ taglib uri="/WEB-INF/TLD/struts-bean.tld" prefix="bean" %>
<%@ taglib uri="/WEB-INF/TLD/struts-logic.tld" prefix="logic" %>


<%
session.setAttribute("CurrentPage","showRecoveryForApproval.do?method=showRecoveryForApproval");
%>

<TABLE width="725" border="0" cellpadding="0" cellspacing="0">
	<html:errors />
	<html:form action="showRecoveryForApproval.do?method=showRecoveryForApproval" >
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
												<TD width="40%" class="Heading"><bean:message key="recoveryHeader"/><bean:write name="gmApprovalForm" property="borrowerId"/></TD>
												<TD><IMG src="images/TriangleSubhead.gif" width="19" height="19"></TD>
											</TR>
											<tr>
											  <td colspan="6" class="Heading" height="7" ><img
											  src="images/Clear.gif" width="5" height="5">
											   <A href="javascript:submitForm('showOutstandingsForApproval.do?method=showOutstandingsForApproval')">Outstanding
											  Details</A> | <A href="javascript:submitForm('showRepaymentsForApproval.do?method=showRepaymentsForApproval')">Repayment Details</A>| <A href="javascript:submitForm('showDisbursementsForApproval.do?method=showDisbursementsForApproval')">Disbursement
											  Details</A>|<A href="javascript:submitForm('showNpaForApproval.do?method=showNpaForApproval')">NPA Details</A> | <A href="javascript:submitForm('showRepaySchForApproval.do?method=showRepaySchForApproval')">Repayment Schedule
											  </td>
										</tr>
								  </table>
							  </td>
							</tr>

							<tr>
								<td align="center" valign="middle" class="HeadingBg"
								align="center"><center><bean:message key="oldRecoveryDetails"/></td>
								<td align="center" valign="middle" class="HeadingBg"
								align="center"><center><bean:message key="newRecoveryDetails"/></td>
							</tr>

							<%
								ArrayList oldRecDtls = new ArrayList();
								ArrayList newRecDtls = new ArrayList();
							
								int i=0;
							%>

							<logic:iterate name="gmApprovalForm" property="recoveryDetails" id="object">
							<%
							if (i==0)
							{
								oldRecDtls = (ArrayList) object;
							}
							else if (i==1)
							{
								newRecDtls = (ArrayList) object;
							}
							i++;
							%>
							</logic:iterate>
							<%
								SimpleDateFormat dateFormat=new SimpleDateFormat("dd/MM/yyyy");
								DecimalFormat df = new DecimalFormat("#############.##");
								df.setDecimalSeparatorAlwaysShown(false);

								String oldRecId;
								String newRecId;

								double oldRecAmt;
								String strOldRecAmt;
								double newRecAmt;
								String strNewRecAmt;

								Date oldRecDt;
								String strOldRecDt="";
								Date newRecDt;
								String strNewRecDt="";

								double oldLegalChrgs;
								String strOldLegalChrgs;
								double newLegalChrgs;
								String strNewLegalChrgs;

								String oldIsOts;
								String newIsOts;

								String oldSaleOfAssets;
								String newSaleOfAssets;

								String oldDtlsOfSale;
								String newDtlsOfSale;

								String oldRemarks;
								String newRemarks;

								int row=0;
								for (int j=0;j<oldRecDtls.size();j++)
								{
									Recovery oldRecovery = (Recovery) oldRecDtls.get(j);
									oldRecId = oldRecovery.getRecoveryNo();
									for (int k=0;k<newRecDtls.size();k++)
									{
										Recovery newRecovery = (Recovery) newRecDtls.get(k);
										newRecId = newRecovery.getRecoveryNo();
										if (oldRecId.equals(newRecId))
										{
											oldRecAmt=oldRecovery.getAmountRecovered();
											strOldRecAmt=df.format(oldRecAmt);
											newRecAmt=newRecovery.getAmountRecovered();
											strNewRecAmt=df.format(newRecAmt);

											oldRecDt=oldRecovery.getDateOfRecovery();
											if(oldRecDt!=null)
											{
											strOldRecDt=dateFormat.format(oldRecDt);
											}
											newRecDt=newRecovery.getDateOfRecovery();
											if(newRecDt!=null)
											{
											strNewRecDt=dateFormat.format(newRecDt);
											}

											oldLegalChrgs=oldRecovery.getLegalCharges();
											strOldLegalChrgs=df.format(oldLegalChrgs);
											newLegalChrgs=newRecovery.getLegalCharges();
											strNewLegalChrgs=df.format(newLegalChrgs);

											oldIsOts=oldRecovery.getIsRecoveryByOTS();
											newIsOts=newRecovery.getIsRecoveryByOTS();

											oldSaleOfAssets=oldRecovery.getIsRecoveryBySaleOfAsset();
											newSaleOfAssets=newRecovery.getIsRecoveryBySaleOfAsset();

											oldDtlsOfSale=oldRecovery.getDetailsOfAssetSold();
											newDtlsOfSale=newRecovery.getDetailsOfAssetSold();

											oldRemarks=oldRecovery.getRemarks();
											newRemarks=newRecovery.getRemarks();
									%>
									<tr>
										<TD align="center" valign="middle" class="TableData"
										  align="center">
										  <center>
										  <table width="100%" border="0" cellspacing="1" cellpadding="0">
												<tr>
													<TD align="center" valign="middle" class="TableData"
													  align="center">
													  <center><bean:message key="recoveryNo"/>
													</TD>
													<TD align="center" valign="middle" class="TableData"
													  align="center">
													  <center><%=oldRecId%>
													</TD>
												</tr>
												<tr>
													<TD align="center" valign="middle" class="TableData"
													  align="center">
													  <center><bean:message key="recoveryDate"/>
													</TD>
													<TD align="center" valign="middle" class="TableData"
													  align="center">
													  <center><%=strOldRecDt%>
													</TD>
												</tr>
												<tr>
													<TD align="center" valign="middle" class="TableData"
													  align="center">
													  <center><bean:message key="amountRecovered"/>
													</TD>
													<TD align="center" valign="middle" class="TableData"
													  align="center">
													  <center><%=strOldRecAmt%>
													</TD>
												</tr>
												<tr>
													<TD align="center" valign="middle" class="TableData"
													  align="center">
													  <center><bean:message key="legalCharges"/>
													</TD>
													<TD align="center" valign="middle" class="TableData"
													  align="center">
													  <center><%=strOldLegalChrgs%>
													</TD>
												</tr>
												<tr>
													<TD align="center" valign="middle" class="TableData"
													  align="center">
													  <center><bean:message key="remarks"/>
													</TD>
													<TD align="center" valign="middle" class="TableData"
													  align="center">
													  <center><%=oldRemarks%>
													</TD>
												</tr>
												<tr>
													<TD align="center" valign="middle" class="TableData"
													  align="center">
													  <center><bean:message key="isRecoveryByOts"/>
													</TD>
													<TD align="center" valign="middle" class="TableData"
													  align="center">
													  <center><%=oldIsOts%>
													</TD>
												</tr>
												<tr>
													<TD align="center" valign="middle" class="TableData"
													  align="center">
													  <center><bean:message key="isRecoveryByAssets"/>
													</TD>
													<TD align="center" valign="middle" class="TableData"
													  align="center">
													  <center><%=oldSaleOfAssets%>
													</TD>
												</tr>
												<tr>
													<TD align="center" valign="middle" class="TableData"
													  align="center">
													  <center><bean:message key="detailsOfAsset"/>
													</TD>
													<TD align="center" valign="middle" class="TableData"
													  align="center">
													  <center><%=oldDtlsOfSale%>
													</TD>
												</tr>
										  </table>
										</TD>
										<TD align="center" valign="middle" class="TableData"
										  align="center">
										  <center>
										  <table width="100%" border="0" cellspacing="1" cellpadding="0">
												<tr>
													<TD align="center" valign="middle" class="TableData"
													  align="center">
													  <center><bean:message key="recoveryNo"/>
													</TD>
													<TD align="center" valign="middle" class="TableData"
													  align="center">
													  <center><%=newRecId%>
													</TD>
												</tr>
												<tr>
													<TD align="center" valign="middle" class="TableData"
													  align="center">
													  <center><bean:message key="recoveryDate"/>
													</TD>
													<TD align="center" valign="middle" class="TableData"
													  align="center">
													  <center><%=strNewRecDt%>
													</TD>
												</tr>
												<tr>
													<TD align="center" valign="middle" class="TableData"
													  align="center">
													  <center><bean:message key="amountRecovered"/>
													</TD>
													<TD align="center" valign="middle" class="TableData"
													  align="center">
													  <center><%=strNewRecAmt%>
													</TD>
												</tr>
												<tr>
													<TD align="center" valign="middle" class="TableData"
													  align="center">
													  <center><bean:message key="legalCharges"/>
													</TD>
													<TD align="center" valign="middle" class="TableData"
													  align="center">
													  <center><%=strNewLegalChrgs%>
													</TD>
												</tr>
												<tr>
													<TD align="center" valign="middle" class="TableData"
													  align="center">
													  <center><bean:message key="remarks"/>
													</TD>
													<TD align="center" valign="middle" class="TableData"
													  align="center">
													  <center><%=newRemarks%>
													</TD>
												</tr>
												<tr>
													<TD align="center" valign="middle" class="TableData"
													  align="center">
													  <center><bean:message key="isRecoveryByOts"/>
													</TD>
													<TD align="center" valign="middle" class="TableData"
													  align="center">
													  <center><%=newIsOts%>
													</TD>
												</tr>
												<tr>
													<TD align="center" valign="middle" class="TableData"
													  align="center">
													  <center><bean:message key="isRecoveryByAssets"/>
													</TD>
													<TD align="center" valign="middle" class="TableData"
													  align="center">
													  <center><%=newSaleOfAssets%>
													</TD>
												</tr>
												<tr>
													<TD align="center" valign="middle" class="TableData"
													  align="center">
													  <center><bean:message key="detailsOfAsset"/>
													</TD>
													<TD align="center" valign="middle" class="TableData"
													  align="center">
													  <center><%=newDtlsOfSale%>
													</TD>
												</tr>
										  </table>
										</TD>
									</tr>
									<%

											break;
										}
										newRecovery=null;
									}
									oldRecovery=null;
								}
							%>

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
