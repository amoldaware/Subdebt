<%@ page language="java"%>
<%@ page import="java.util.ArrayList"%>
<%@ page import="com.cgtsi.guaranteemaintenance.NPADetails"%>
<%@ page import="com.cgtsi.guaranteemaintenance.RecoveryProcedure"%>
<%@ page import="com.cgtsi.guaranteemaintenance.LegalSuitDetail"%>
<%@ page import="java.text.SimpleDateFormat"%>
<%@ page import="java.text.DecimalFormat"%>
<%@ page import="java.util.Date"%>
<%@ include file="/jsp/SetMenuInfo.jsp" %>


<%@ taglib uri="/WEB-INF/TLD/struts-html.tld" prefix="html" %>
<%@ taglib uri="/WEB-INF/TLD/struts-bean.tld" prefix="bean" %>
<%@ taglib uri="/WEB-INF/TLD/struts-logic.tld" prefix="logic" %>


<%
session.setAttribute("CurrentPage","showNpaForApproval.do?method=showNpaForApproval");
%>

<TABLE width="725" border="0" cellpadding="0" cellspacing="0">
	<html:errors />
	<html:form action="showNpaForApproval.do?method=showNpaForApproval" >
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
												<TD width="40%" class="Heading"><bean:message key="NPADetailsHeader"/><bean:write name="gmApprovalForm" property="borrowerId"/></TD>
												<TD><IMG src="images/TriangleSubhead.gif" width="19" height="19"></TD>
											</TR>
											<tr>
											  <td colspan="6" class="Heading" height="7" ><img
											  src="images/Clear.gif" width="5" height="5">
											   <A href="javascript:submitForm('showOutstandingsForApproval.do?method=showOutstandingsForApproval')">Outstanding
											  Details</A> | <A href="javascript:submitForm('showRepaymentsForApproval.do?method=showRepaymentsForApproval')">Repayment Details</A>| <A href="javascript:submitForm('showDisbursementsForApproval.do?method=showDisbursementsForApproval')">Disbursement
											  Details</A>|<A href="javascript:submitForm('showRecoveryForApproval.do?method=showRecoveryForApproval')">Recovery Details</A> | <A href="javascript:submitForm('showRepaySchForApproval.do?method=showRepaySchForApproval')">Repayment Schedule
											  </td>
										</tr>
								  </table>
							  </td>
							</tr>

							<tr>
								<td align="center" valign="middle" class="HeadingBg"
								align="center"><center><bean:message key="field"/></td>
								<td align="center" valign="middle" class="HeadingBg"
								align="center"><center><bean:message key="oldNpaDetails"/></td>
								<td align="center" valign="middle" class="HeadingBg"
								align="center"><center><bean:message key="newNpaDetails"/></td>
							</tr>

							<%
								NPADetails oldNpa = new NPADetails();
								NPADetails newNpa = new NPADetails();
								int i=0;
							%>

							<logic:iterate name="gmApprovalForm" property="npaDetails" id="object">
							<%
							if (i==0)
							{
								oldNpa = (NPADetails) object;
							}
							else if (i==1)
							{
								newNpa = (NPADetails) object;
							}
							i++;
							%>
							</logic:iterate>
							<%
								SimpleDateFormat dateFormat=new SimpleDateFormat("dd/MM/yyyy");
								DecimalFormat df = new DecimalFormat("#############.##");
								df.setDecimalSeparatorAlwaysShown(false);

								String oldNpaId = oldNpa.getNpaId();
								String newNpaId = newNpa.getNpaId();

								String oldBid  = oldNpa.getCgbid();
								String newBid  = newNpa.getCgbid();

								Date oldNpaDate = oldNpa.getNpaDate();
								String strOldNpaDate = "";
								if (oldNpaDate != null)
								{
									strOldNpaDate = dateFormat.format(oldNpaDate);
								}
								Date newNpaDate = newNpa.getNpaDate();
								String strNewNpaDate = "";
								if (newNpaDate != null)
								{
									strNewNpaDate = dateFormat.format(newNpaDate);
								}

								String oldReported = oldNpa.getWhetherNPAReported();
								String newReported = newNpa.getWhetherNPAReported();

								Date oldReportingDate = oldNpa.getReportingDate();
								String strOldReportingDate ="";
								if (oldReportingDate != null)
								{
									strOldReportingDate = dateFormat.format(oldReportingDate);
								}
								Date newReportingDate = newNpa.getReportingDate();
								String strNewReportingDate = "";
								if (newReportingDate != null)
								{
									strNewReportingDate = dateFormat.format(newReportingDate);
								}

								String oldReference = oldNpa.getReference();
								String newReference = newNpa.getReference();

								double oldOsAmt = oldNpa.getOsAmtOnNPA();
								String strOldOsAmt = df.format(oldOsAmt);
								double newOsAmt = newNpa.getOsAmtOnNPA();
								String strNewOsAmt = df.format(newOsAmt);

								String oldReasons = oldNpa.getNpaReason();
								String newReasons = newNpa.getNpaReason();
								
								String oldRemarks="";
								if(oldNpa.getRemarksOnNpa()!=null)
								{
									oldRemarks = oldNpa.getRemarksOnNpa();
								}
								else{

									oldRemarks ="";
								}
								String newRemarks="";
								if(newNpa.getRemarksOnNpa()!=null)
								{
									newRemarks = newNpa.getRemarksOnNpa();
								}
								else{

									newRemarks ="";
								}
								
								String oldRecInitiated ="";
								if(oldNpa.getIsRecoveryInitiated()!=null)
								{
									oldRecInitiated = oldNpa.getIsRecoveryInitiated();
								}
								else{
									oldRecInitiated="";
									}
								
								String newRecInitiated = "";
								if(newNpa.getIsRecoveryInitiated()!=null)
								{
									newRecInitiated = newNpa.getIsRecoveryInitiated();
								}
								else{

									newRecInitiated ="";
								}

								int oldNoOfActions = oldNpa.getNoOfActions();
								int newNoOfActions = newNpa.getNoOfActions();

								Date oldEffConcDate = oldNpa.getEffortsConclusionDate();
								String strOldEffConcDate = "";
								if (oldEffConcDate != null)
								{
									strOldEffConcDate = dateFormat.format(oldEffConcDate);
								}
								Date newEffConcDate = newNpa.getEffortsConclusionDate();
								String strNewEffConcDate = "";
								if (newEffConcDate != null)
								{
									strNewEffConcDate = dateFormat.format(newEffConcDate);
								}
								String oldMliComment="";
								if(oldNpa.getMliCommentOnFinPosition()!=null)
								{
									oldMliComment = oldNpa.getMliCommentOnFinPosition();
								}
								else{
									oldMliComment ="";
								}							
								String newMliComment ="";
								if(newNpa.getMliCommentOnFinPosition()!=null)
								{
									newMliComment = newNpa.getMliCommentOnFinPosition();
								}
								else{

									newMliComment="";
								}								
								String oldDtlsFinAssistance="";
								if(oldNpa.getDetailsOfFinAssistance()!=null)
								{
									oldDtlsFinAssistance = oldNpa.getDetailsOfFinAssistance();
								}
								else{
									oldDtlsFinAssistance="";
								}
								String newDtlsFinAssistance="";
								if(newNpa.getDetailsOfFinAssistance()!=null)
								{
									newDtlsFinAssistance = newNpa.getDetailsOfFinAssistance();
								}
								else{

									newDtlsFinAssistance="";
								}
								String oldCreditSupport="";
								if(oldNpa.getCreditSupport()!=null)
								{
									oldCreditSupport = oldNpa.getCreditSupport();
								}
								else{

									oldCreditSupport="";
								}
								String newCreditSupport = "";
								if(newNpa.getCreditSupport()!=null)
								{
									newCreditSupport=newNpa.getCreditSupport();
								}
								else{

									newCreditSupport="";
								}
								String oldBankFacility="";
								if(oldNpa.getBankFacilityDetail()!=null)
								{
									oldBankFacility = oldNpa.getBankFacilityDetail();
								}
								else{

									oldBankFacility="";
								}
								String newBankFacility ="";
								if(newNpa.getBankFacilityDetail()!=null)
								{
									newBankFacility = newNpa.getBankFacilityDetail();
								}
								else{

									newBankFacility="";
								}

								String oldWillfull="";
								if(oldNpa.getWillfulDefaulter()!=null)
								{
									oldWillfull = oldNpa.getWillfulDefaulter();
								}
								else{

									oldWillfull ="";
								}
								String newWillfull="";
								if(newNpa.getWillfulDefaulter()!=null)
								{
									newWillfull = newNpa.getWillfulDefaulter();
								}
								else{

									newWillfull ="";
								}
								
								String oldWatchList ="";
								if(oldNpa.getPlaceUnderWatchList()!=null)
								{
									oldWatchList = oldNpa.getPlaceUnderWatchList();
								}
								else{

									oldWatchList ="";
								}
								String newWatchList ="";
								if(newNpa.getPlaceUnderWatchList()!=null)
								{
									newWatchList = newNpa.getPlaceUnderWatchList();
								}
								else{

									newWatchList ="";
								}
								

								ArrayList oldRecActions = oldNpa.getRecoveryProcedure();
								ArrayList newRecActions = newNpa.getRecoveryProcedure();

								LegalSuitDetail oldLegalSuit = oldNpa.getLegalSuitDetail();
								LegalSuitDetail newLegalSuit = newNpa.getLegalSuitDetail();

								String oldSuitNo = "";
								String oldCourtName = "";
								String oldForumName = "";
								String oldLocation = "";
								String strOldFilingDate = "";
								String strOldAmtClaimed = "";
								String oldCurrentStatus = "";
								String oldProcConcluded = "";
								if (oldLegalSuit != null)
								{
									if(oldLegalSuit.getLegalSuiteNo()!=null)
									{
										oldSuitNo = oldLegalSuit.getLegalSuiteNo();
									}
									else{

										oldSuitNo ="";
									}
									if(oldLegalSuit.getCourtName()!=null)
									{
										oldCourtName = oldLegalSuit.getCourtName();
									}
									else{
										oldCourtName = "";
									}
									if( oldLegalSuit.getForumName()!=null)
									{
										oldForumName = oldLegalSuit.getForumName();
									}
									else{

										oldForumName = "";
									}
									if(oldLegalSuit.getLocation()!=null)
									{
										oldLocation = oldLegalSuit.getLocation();
									}
									else{

										oldLocation = "";
									}
									
									Date oldFilingDate = oldLegalSuit.getDtOfFilingLegalSuit();
									if (oldFilingDate != null)
									{
										strOldFilingDate = dateFormat.format(oldFilingDate);
									}
									double oldAmtClaimed = oldLegalSuit.getAmountClaimed();
									strOldAmtClaimed = df.format(oldAmtClaimed);
									if(oldLegalSuit.getCurrentStatus()!=null)
									{
										oldCurrentStatus = oldLegalSuit.getCurrentStatus();
									}
									else
									{
										oldCurrentStatus="";
									}
									if(oldLegalSuit.getRecoveryProceedingsConcluded()!=null)
									{
										oldProcConcluded = oldLegalSuit.getRecoveryProceedingsConcluded();
									}
									else{
										oldProcConcluded="";
									}
									
								}

								String newSuitNo = "";
								String newCourtName = "";
								String newForumName = "";
								String newLocation = "";
								String strNewFilingDate = "";
								String strNewAmtClaimed = "";
								String newCurrentStatus = "";
								String newProcConcluded = "";
								if (newLegalSuit != null)
								{
									if(newLegalSuit.getLegalSuiteNo()!=null)
									{
										newSuitNo = newLegalSuit.getLegalSuiteNo();
									}
									else{

										newSuitNo ="";
									}
									
									if(newLegalSuit.getCourtName()!=null)
									{
										newCourtName = newLegalSuit.getCourtName();
									}
									else{

										newCourtName="";
									}
							
									
									if(newLegalSuit.getForumName()!=null)
									{
										newForumName = newLegalSuit.getForumName();
									}
									else{

										newForumName="";
									}
									if(newLegalSuit.getLocation()!=null)
									{
										newLocation = newLegalSuit.getLocation();
									}
									else{
											newLocation="";
									}
									
									Date newFilingDate = newLegalSuit.getDtOfFilingLegalSuit();
									if (newFilingDate != null)
									{
										strNewFilingDate = dateFormat.format(newFilingDate);
									}
									double newAmtClaimed = newLegalSuit.getAmountClaimed();
									strNewAmtClaimed = df.format(newAmtClaimed);
									if(newLegalSuit.getCurrentStatus()!=null)
									{
										newCurrentStatus = newLegalSuit.getCurrentStatus();
									}
									else{
										newCurrentStatus="";
									}
									if(newLegalSuit.getRecoveryProceedingsConcluded()!=null)
									{
										newProcConcluded = newLegalSuit.getRecoveryProceedingsConcluded();
									}
									else{

										newProcConcluded="";
									}
									
								}

							%>
							<%
							if (!(oldNpaId==null || oldNpaId.equals("")))
							{
							%>
							<tr>
								<TD align="center" valign="middle" class="TableData"
								  align="center">
								  <center><bean:message key="npaId"/>
								</TD>
								<TD align="center" valign="middle" class="TableData"
								  align="center">
								  <center><%=oldNpaId%>
								</TD>
								<TD align="center" valign="middle" class="TableData"
								  align="center">
								  <center><%=newNpaId%>
								</TD>
							</tr>
							<tr>
								<TD align="center" valign="middle" class="TableData"
								  align="center">
								  <center><bean:message key="borrowerId"/>
								</TD>
								<TD align="center" valign="middle" class="TableData"
								  align="center">
								  <center><%=oldBid%>
								</TD>
								<TD align="center" valign="middle" class="TableData"
								  align="center">
								  <center><%=oldBid%>
								</TD>
							</tr>
							<tr>
								<TD align="center" valign="middle" class="TableData"
								  align="center">
								  <center><bean:message key="osAmtOnNPA"/>
								</TD>
								<TD align="center" valign="middle" class="TableData"
								  align="center">
								  <center><%=strOldOsAmt%>
								</TD>
								<TD align="center" valign="middle" class="TableData"
								  align="center">
								  <center><%=strOldOsAmt%>
								</TD>
							</tr>
							<tr>
								<TD align="center" valign="middle" class="TableData"
								  align="center">
								  <center><bean:message key="whetherNPAReported"/>
								</TD>
								<TD align="center" valign="middle" class="TableData"
								  align="center">
								  <center><%=oldReported%>
								</TD>
								<TD align="center" valign="middle" class="TableData"
								  align="center">
								  <center><%=newReported%>
								</TD>
							</tr>
							<tr>
								<TD align="center" valign="middle" class="TableData"
								  align="center">
								  <center><bean:message key="reportingDate"/>
								</TD>
								<TD align="center" valign="middle" class="TableData"
								  align="center">
								  <center><%=strOldReportingDate%>
								</TD>
								<TD align="center" valign="middle" class="TableData"
								  align="center">
								  <center><%=strNewReportingDate%>
								</TD>
							</tr>
							<tr>
								<TD align="center" valign="middle" class="TableData"
								  align="center">
								  <center><bean:message key="npaReason"/>
								</TD>
								<TD align="center" valign="middle" class="TableData"
								  align="center">
								  <center><%=oldReasons%>
								</TD>
								<TD align="center" valign="middle" class="TableData"
								  align="center">
								  <center><%=newReasons%>
								</TD>
							</tr>
							<tr>
								<TD align="center" valign="middle" class="TableData"
								  align="center">
								  <center><bean:message key="defaulter"/>
								</TD>
								<TD align="center" valign="middle" class="TableData"
								  align="center">
								  <center><%=oldWillfull%>
								</TD>
								<TD align="center" valign="middle" class="TableData"
								  align="center">
								  <center><%=newWillfull%>
								</TD>
							</tr>
							<tr>
								  <%
								  int row=0;
								  if (oldRecActions != null && oldRecActions.size()>0)
								  {
									  System.out.println("old size not null");
									  int oldSize = oldRecActions.size();
									  int newSize = newRecActions.size();

										  System.out.println("old size size greater than zero");

										  for (int j=0;j<oldRecActions.size();j++)
										  {
											  RecoveryProcedure oldRecProc = (RecoveryProcedure) oldRecActions.get(j);
											  String oldRecId = oldRecProc.getRadId();
											  for (int k=0;k<newRecActions.size();k++)
											  {
												  RecoveryProcedure newRecProc = (RecoveryProcedure) newRecActions.get(k);
												  String newRecId = newRecProc.getRadId();
//												  if (oldRecId.equals(newRecId))
//												  {
													  String oldActionType = oldRecProc.getActionType();
													  String newActionType = newRecProc.getActionType();

													  String oldActionDetails = oldRecProc.getActionDetails();
													  String newActionDetails = newRecProc.getActionDetails();

													  Date oldActionDate = oldRecProc.getActionDate();
													  String strOldActionDate = "";
													  if (oldActionDate != null)
													  {
														strOldActionDate = dateFormat.format(oldActionDate);
													  }
													  Date newActionDate = newRecProc.getActionDate();
													  String strNewActionDate = "";
													  if (newActionDate != null)
													  {
														strNewActionDate = dateFormat.format(newActionDate);
													  }

													  String oldAttachement = oldRecProc.getAttachmentName();
													  String newAttachement = newRecProc.getAttachmentName();
										%>
										<TD align="center" valign="middle" class="TableData"
										  align="center">
											  <%
											  if (row==0)
											  {
											  %>
										  <center><bean:message key="recoveryProcedure"/>
											<%}%>

										</TD>
										<TD align="center" valign="middle" class="TableData"
										  align="center">
										  <center>
										  <table width="100%" border="0" cellspacing="1" cellpadding="0">
												<tr>
													<TD align="center" valign="middle" class="TableData"
													  align="center">
													  <center><bean:message key="actionType"/>
													</TD>
													<TD align="center" valign="middle" class="TableData"
													  align="center">
													  <center><%=oldActionType%>
													</TD>
												</tr>
												<tr>
													<TD align="center" valign="middle" class="TableData"
													  align="center">
													  <center><bean:message key="details"/>
													</TD>
													<TD align="center" valign="middle" class="TableData"
													  align="center">
													  <center><%=oldActionDetails%>
													</TD>
												</tr>
												<tr>
													<TD align="center" valign="middle" class="TableData"
													  align="center">
													  <center><bean:message key="date"/>
													</TD>
													<TD align="center" valign="middle" class="TableData"
													  align="center">
													  <center><%=oldActionDate%>
													</TD>
												</tr>
												<tr>
													<TD align="center" valign="middle" class="TableData"
													  align="center">
													  <center><bean:message key="attachment"/>
													</TD>
													<TD align="center" valign="middle" class="TableData"
													  align="center">
													  <center><%=oldAttachement%>
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
													  <center><bean:message key="actionType"/>
													</TD>
													<TD align="center" valign="middle" class="TableData"
													  align="center">
													  <center><%=newActionType%>
													</TD>
												</tr>
												<tr>
													<TD align="center" valign="middle" class="TableData"
													  align="center">
													  <center><bean:message key="details"/>
													</TD>
													<TD align="center" valign="middle" class="TableData"
													  align="center">
													  <center><%=newActionDetails%>
													</TD>
												</tr>
												<tr>
													<TD align="center" valign="middle" class="TableData"
													  align="center">
													  <center><bean:message key="date"/>
													</TD>
													<TD align="center" valign="middle" class="TableData"
													  align="center">
													  <center><%=newActionDate%>
													</TD>
												</tr>
												<tr>
													<TD align="center" valign="middle" class="TableData"
													  align="center">
													  <center><bean:message key="attachment"/>
													</TD>
													<TD align="center" valign="middle" class="TableData"
													  align="center">
													  <center><%=newAttachement%>
													</TD>
												</tr>
										  </table>
										</TD>
										<%
											  break;
										  
									  }
								  }
								  
								  }
								  else if (newRecActions!=null && newRecActions.size()>0)
								  {
										  System.out.println("new size size greater than zero");
											  for (int k=0;k<newRecActions.size();k++)
											  {
												  RecoveryProcedure newRecProc = (RecoveryProcedure) newRecActions.get(k);
												  String newRecId = newRecProc.getRadId();
//												  if (oldRecId.equals(newRecId))
//												  {
													  String oldActionType = "";
													  String newActionType = newRecProc.getActionType();

													  String oldActionDetails = "";
													  String newActionDetails = newRecProc.getActionDetails();

													  Date oldActionDate = null;
													  String strOldActionDate = "";
													  if (oldActionDate != null)
													  {
														strOldActionDate = dateFormat.format(oldActionDate);
													  }
													  Date newActionDate = newRecProc.getActionDate();
													  String strNewActionDate = "";
													  if (newActionDate != null)
													  {
														strNewActionDate = dateFormat.format(newActionDate);
													  }

													  String oldAttachement = "";
													  String newAttachement = newRecProc.getAttachmentName();
										%>
										<TD align="center" valign="middle" class="TableData"
										  align="center">
											  <%
											  if (row==0)
											  {
											  %>
										  <center><bean:message key="recoveryProcedure"/>
											<%}%>

										</TD>
										<TD align="center" valign="middle" class="TableData"
										  align="center">
										  <center>
										  <table width="100%" border="0" cellspacing="1" cellpadding="0">
												<tr>
													<TD align="center" valign="middle" class="TableData"
													  align="center">
													  <center><bean:message key="actionType"/>
													</TD>
													<TD align="center" valign="middle" class="TableData"
													  align="center">
													  <center><%=oldActionType%>
													</TD>
												</tr>
												<tr>
													<TD align="center" valign="middle" class="TableData"
													  align="center">
													  <center><bean:message key="details"/>
													</TD>
													<TD align="center" valign="middle" class="TableData"
													  align="center">
													  <center><%=oldActionDetails%>
													</TD>
												</tr>
												<tr>
													<TD align="center" valign="middle" class="TableData"
													  align="center">
													  <center><bean:message key="date"/>
													</TD>
													<TD align="center" valign="middle" class="TableData"
													  align="center">
													  <center><%=oldActionDate%>
													</TD>
												</tr>
												<tr>
													<TD align="center" valign="middle" class="TableData"
													  align="center">
													  <center><bean:message key="attachment"/>
													</TD>
													<TD align="center" valign="middle" class="TableData"
													  align="center">
													  <center><%=oldAttachement%>
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
													  <center><bean:message key="actionType"/>
													</TD>
													<TD align="center" valign="middle" class="TableData"
													  align="center">
													  <center><%=newActionType%>
													</TD>
												</tr>
												<tr>
													<TD align="center" valign="middle" class="TableData"
													  align="center">
													  <center><bean:message key="details"/>
													</TD>
													<TD align="center" valign="middle" class="TableData"
													  align="center">
													  <center><%=newActionDetails%>
													</TD>
												</tr>
												<tr>
													<TD align="center" valign="middle" class="TableData"
													  align="center">
													  <center><bean:message key="date"/>
													</TD>
													<TD align="center" valign="middle" class="TableData"
													  align="center">
													  <center><%=newActionDate%>
													</TD>
												</tr>
												<tr>
													<TD align="center" valign="middle" class="TableData"
													  align="center">
													  <center><bean:message key="attachment"/>
													</TD>
													<TD align="center" valign="middle" class="TableData"
													  align="center">
													  <center><%=newAttachement%>
													</TD>
												</tr>
										  </table>
										</TD>
										<%
											  break;
										  
									  }

								  }
								  %>
							</tr>
							<tr>
								<TD align="center" valign="middle" class="TableData"
								  align="center">
								  <center><bean:message key="isRecoveryInitiated"/>
								</TD>
								<TD align="center" valign="middle" class="TableData"
								  align="center">
								  <center><%=oldRecInitiated%>
								</TD>
								<TD align="center" valign="middle" class="TableData"
								  align="center">
								  <center><%=newRecInitiated%>
								</TD>
							</tr>
							<tr>
								<TD align="center" valign="middle" class="TableData"
								  align="center">
								  <center><bean:message key="forum"/>
								</TD>
								<TD align="center" valign="middle" class="TableData"
								  align="center">
								  <center><%=oldCourtName%>
								</TD>
								<TD align="center" valign="middle" class="TableData"
								  align="center">
								  <center><%=newCourtName%>
								</TD>
							</tr>
							<tr>
								<TD align="center" valign="middle" class="TableData"
								  align="center">
								  <center><bean:message key="suit"/>
								</TD>
								<TD align="center" valign="middle" class="TableData"
								  align="center">
								  <center><%=oldSuitNo%>
								</TD>
								<TD align="center" valign="middle" class="TableData"
								  align="center">
								  <center><%=newSuitNo%>
								</TD>
							</tr>
							<tr>
								<TD align="center" valign="middle" class="TableData"
								  align="center">
								  <center><bean:message key="date"/>
								</TD>
								<TD align="center" valign="middle" class="TableData"
								  align="center">
								  <center><%=strOldFilingDate%>
								</TD>
								<TD align="center" valign="middle" class="TableData"
								  align="center">
								  <center><%=strNewFilingDate%>
								</TD>
							</tr>
							<tr>
								<TD align="center" valign="middle" class="TableData"
								  align="center">
								  <center><bean:message key="forumName"/>
								</TD>
								<TD align="center" valign="middle" class="TableData"
								  align="center">
								  <center><%=oldForumName%>
								</TD>
								<TD align="center" valign="middle" class="TableData"
								  align="center">
								  <center><%=newForumName%>
								</TD>
							</tr>
							<tr>
								<TD align="center" valign="middle" class="TableData"
								  align="center">
								  <center><bean:message key="location"/>
								</TD>
								<TD align="center" valign="middle" class="TableData"
								  align="center">
								  <center><%=oldLocation%>
								</TD>
								<TD align="center" valign="middle" class="TableData"
								  align="center">
								  <center><%=newLocation%>
								</TD>
							</tr>
							<tr>
								<TD align="center" valign="middle" class="TableData"
								  align="center">
								  <center><bean:message key="amountClaimed"/>
								</TD>
								<TD align="center" valign="middle" class="TableData"
								  align="center">
								  <center><%=strOldAmtClaimed%>
								</TD>
								<TD align="center" valign="middle" class="TableData"
								  align="center">
								  <center><%=strNewAmtClaimed%>
								</TD>
							</tr>
							<tr>
								<TD align="center" valign="middle" class="TableData"
								  align="center">
								  <center><bean:message key="currentStatus"/>
								</TD>
								<TD align="center" valign="middle" class="TableData"
								  align="center">
								  <center><%=oldCurrentStatus%>
								</TD>
								<TD align="center" valign="middle" class="TableData"
								  align="center">
								  <center><%=newCurrentStatus%>
								</TD>
							</tr>
							<tr>
								<TD align="center" valign="middle" class="TableData"
								  align="center">
								  <center><bean:message key="recoveryProceedingsConcluded"/>
								</TD>
								<TD align="center" valign="middle" class="TableData"
								  align="center">
								  <center><%=oldProcConcluded%>
								</TD>
								<TD align="center" valign="middle" class="TableData"
								  align="center">
								  <center><%=newProcConcluded%>
								</TD>
							</tr>
							<tr>
								<TD align="center" valign="middle" class="TableData"
								  align="center">
								  <center><bean:message key="mliComment"/>
								</TD>
								<TD align="center" valign="middle" class="TableData"
								  align="center">
								  <center><%=oldMliComment%>
								</TD>
								<TD align="center" valign="middle" class="TableData"
								  align="center">
								  <center><%=newMliComment%>
								</TD>
							</tr>
							<tr>
								<TD align="center" valign="middle" class="TableData"
								  align="center">
								  <center><bean:message key="finAssistDetails"/>
								</TD>
								<TD align="center" valign="middle" class="TableData"
								  align="center">
								  <center><%=oldDtlsFinAssistance%>
								</TD>
								<TD align="center" valign="middle" class="TableData"
								  align="center">
								  <center><%=newDtlsFinAssistance%>
								</TD>
							</tr>
							<tr>
								<TD align="center" valign="middle" class="TableData"
								  align="center">
								  <center><bean:message key="creditSupport"/>
								</TD>
								<TD align="center" valign="middle" class="TableData"
								  align="center">
								  <center><%=oldCreditSupport%>
								</TD>
								<TD align="center" valign="middle" class="TableData"
								  align="center">
								  <center><%=newCreditSupport%>
								</TD>
							</tr>
							<tr>
								<TD align="center" valign="middle" class="TableData"
								  align="center">
								  <center><bean:message key="bankFacilityProvided"/>
								</TD>
								<TD align="center" valign="middle" class="TableData"
								  align="center">
								  <center><%=oldBankFacility%>
								</TD>
								<TD align="center" valign="middle" class="TableData"
								  align="center">
								  <center><%=newBankFacility%>
								</TD>
							</tr>
							<tr>
								<TD align="center" valign="middle" class="TableData"
								  align="center">
								  <center><bean:message key="placeUnderWatchList"/>
								</TD>
								<TD align="center" valign="middle" class="TableData"
								  align="center">
								  <center><%=oldWatchList%>
								</TD>
								<TD align="center" valign="middle" class="TableData"
								  align="center">
								  <center><%=newWatchList%>
								</TD>
							</tr>
							<tr>
								<TD align="center" valign="middle" class="TableData"
								  align="center">
								  <center><bean:message key="otherRemarks"/>
								</TD>
								<TD align="center" valign="middle" class="TableData"
								  align="center">
								  <center><%=oldRemarks%>
								</TD>
								<TD align="center" valign="middle" class="TableData"
								  align="center">
								  <center><%=newRemarks%>
								</TD>
							</tr>
							<%
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
