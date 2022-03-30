<%@ page language="java"%>
<%@ page import="java.util.ArrayList"%>
<%@ page import="com.cgtsi.guaranteemaintenance.RepaymentSchedule"%>
<%@ page import="java.text.SimpleDateFormat"%>
<%@ page import="java.text.DecimalFormat"%>
<%@ page import="java.util.Date"%>
<%@ include file="/jsp/SetMenuInfo.jsp" %>


<%@ taglib uri="/WEB-INF/TLD/struts-html.tld" prefix="html" %>
<%@ taglib uri="/WEB-INF/TLD/struts-bean.tld" prefix="bean" %>
<%@ taglib uri="/WEB-INF/TLD/struts-logic.tld" prefix="logic" %>


<%
session.setAttribute("CurrentPage","showRepaySchForApproval.do?method=showRepaySchForApproval");
%>

<TABLE width="725" border="0" cellpadding="0" cellspacing="0">
	<html:errors />
	<html:form action="showRepaySchForApproval.do?method=showRepaySchForApproval" >
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
									<TD colspan="4"> 
										<TABLE width="100%" border="0" cellpadding="0" cellspacing="0">
											<TR>
												<TD width="40%" class="Heading"><bean:message key="repayScheduleHeader"/><bean:write name="gmApprovalForm" property="borrowerId"/></TD>
												<TD><IMG src="images/TriangleSubhead.gif" width="19" height="19"></TD>
											</TR>
											<tr>
											  <td colspan="4" class="Heading" height="7" ><img
											  src="images/Clear.gif" width="5" height="5">
											   <A href="javascript:submitForm('showOutstandingsForApproval.do?method=showOutstandingsForApproval')">Outstanding
											  Details</A> | <A href="javascript:submitForm('showRepaymentsForApproval.do?method=showRepaymentsForApproval')">Repayment Details</A>| <A href="javascript:submitForm('showDisbursementsForApproval.do?method=showDisbursementsForApproval')">Disbursement
											  Details</A>|<A href="javascript:submitForm('showNpaForApproval.do?method=showNpaForApproval')">NPA Details</A> | <A href="javascript:submitForm('showRecoveryForApproval.do?method=showRecoveryForApproval')">Recovery Details</A>
											  </td>
										</tr>
								  </table>
							  </td>
							</tr>

							 <tr>
								  <td colspan="4" valign="top">
									<table width="100%" border="0" cellspacing="1" cellpadding="0">
										<tr>
										  <td align="center" valign="middle" class="HeadingBg"
										  align="center"><center><bean:message key="srNo"/></td>
										  
										  <td class="HeadingBg" align="center" align="center"><center><bean:message key="cgpanNumber"/></td>
										  
										  <td class="HeadingBg" align="center" align="center"><center><bean:message key = "oldRepaySchDetails"/></td>
										  
										  <td class="HeadingBg" align="center"><bean:message key = "newRepaySchDetails"/></td>
										</tr>

							<%
								ArrayList oldRepaySchDtls = new ArrayList();
								ArrayList newRepaySchDtls = new ArrayList();
							
								int i=0;
							%>

							<logic:iterate name="gmApprovalForm" property="repayScheduleDetails" id="object">
							<%
							if (i==0)
							{
								oldRepaySchDtls = (ArrayList) object;
							}
							else if (i==1)
							{
								newRepaySchDtls = (ArrayList) object;
							}
							i++;
							%>
							</logic:iterate>
							<%
								SimpleDateFormat dateFormat=new SimpleDateFormat("dd/MM/yyyy");
								DecimalFormat df = new DecimalFormat("#############.##");
								df.setDecimalSeparatorAlwaysShown(false);

								String oldCgpan;
								String newCgpan;

								int oldMoratorium;
								String strOldMoratorium;
								int newMoratorium;
								String strNewMoratorium;

								Date oldFirstInstDueDt;
								String strOldFirstInstDueDt="";
								Date newFirstInstDueDt;
								String strNewFirstInstDueDt="";

								String strOldPeriodicity="";
								String strNewPeriodicity="";
								String oldPeriodicity;
								String newPeriodicity;

								int oldNoOfInst;
								String strOldNoOfInst;
								int newNoOfInst;
								String strNewNoOfInst;

								int row=0;
								for (int j=0;j<oldRepaySchDtls.size();j++)
								{
									RepaymentSchedule oldRepaySch = (RepaymentSchedule) oldRepaySchDtls.get(j);
									oldCgpan = oldRepaySch.getCgpan();
									for (int k=0;k<newRepaySchDtls.size();k++)
									{
										RepaymentSchedule newRepaySch = (RepaymentSchedule) newRepaySchDtls.get(k);
										newCgpan = newRepaySch.getCgpan();
										if (oldCgpan.equals(newCgpan))
										{
											oldMoratorium=oldRepaySch.getMoratorium();
											newMoratorium=newRepaySch.getMoratorium();


											oldFirstInstDueDt=oldRepaySch.getFirstInstallmentDueDate();
											if(oldFirstInstDueDt!=null)
											{
												strOldFirstInstDueDt=dateFormat.format(oldFirstInstDueDt);
											}
											else{

												strOldFirstInstDueDt="";
											}
											newFirstInstDueDt=newRepaySch.getFirstInstallmentDueDate();
											if(strNewFirstInstDueDt!=null)
											{
												strNewFirstInstDueDt=dateFormat.format(newFirstInstDueDt);
											}
											else{
												strNewFirstInstDueDt="";
											}

											oldPeriodicity=oldRepaySch.getPeriodicity();
											if (oldPeriodicity!=null)
											{
												if (oldPeriodicity.equals("1"))
												{
													strOldPeriodicity="Monthly";
												}
												else if (oldPeriodicity.equals("2"))
												{
													strOldPeriodicity="Quarterly";
												}
												else if (oldPeriodicity.equals("3"))
												{
													strOldPeriodicity="Half-Yearly";
												}
											}
											newPeriodicity=newRepaySch.getPeriodicity();
											if (newPeriodicity!=null)
											{
												if (newPeriodicity.equals("1"))
												{
													strNewPeriodicity="Monthly";
												}
												else if (newPeriodicity.equals("2"))
												{
													strNewPeriodicity="Quarterly";
												}
												else if (newPeriodicity.equals("3"))
												{
													strNewPeriodicity="Half-Yearly";
												}
											}

											oldNoOfInst=oldRepaySch.getNoOfInstallment();
											newNoOfInst=newRepaySch.getNoOfInstallment();
									%>
										<tr>
										  <td align="center" valign="middle" class="TableData"
										  align="center"><center><%=(j+1)%></td>
										  
										  <td class="TableData" align="center" align="center"><center><%=oldCgpan%></td>
										  
										<TD align="center" valign="middle" class="TableData"
										  align="center">
										  <center>
											<table width="100%" border="0" cellspacing="1" cellpadding="0">
												<tr>
													<td align="center" class="TableData"  align="center"><center>
														<bean:message key="moratorium"/>
													</td>
													<td align="center" class="TableData"  align="center"><center>
														<%=oldMoratorium%>
													</td>
												</tr>
												<tr>
													<td align="center" class="TableData"  align="center"><center>
														<bean:message key="firstInstallment"/>
													</td>
													<td align="center" class="TableData"  align="center"><center>
														<%=strOldFirstInstDueDt%>
													</td>
												</tr>
												<tr>
													<td align="center" class="TableData"  align="center"><center>
														<bean:message key="periodicity"/>
													</td>
													<td align="center" class="TableData"  align="center"><center>
														<%=strOldPeriodicity%>
													</td>
												</tr>
												<tr>
													<td align="center" class="TableData"  align="center"><center>
														<bean:message key="noOfInstallment"/>
													</td>
													<td align="center" class="TableData"  align="center"><center>
														<%=oldNoOfInst%>
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
														<bean:message key="moratorium"/>
													</td>
													<td align="center" class="TableData"  align="center"><center>
														<%=newMoratorium%>
													</td>
												</tr>
												<tr>
													<td align="center" class="TableData"  align="center"><center>
														<bean:message key="firstInstallment"/>
													</td>
													<td align="center" class="TableData"  align="center"><center>
														<%=strNewFirstInstDueDt%>
													</td>
												</tr>
												<tr>
													<td align="center" class="TableData"  align="center"><center>
														<bean:message key="periodicity"/>
													</td>
													<td align="center" class="TableData"  align="center"><center>
														<%=strNewPeriodicity%>
													</td>
												</tr>
												<tr>
													<td align="center" class="TableData"  align="center"><center>
														<bean:message key="noOfInstallment"/>
													</td>
													<td align="center" class="TableData"  align="center"><center>
														<%=newNoOfInst%>
													</td>
												</tr>
											</table>
										</TD>
									</tr>
									<%

											break;
										}
										newRepaySch=null;
									}
									oldRepaySch=null;
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
