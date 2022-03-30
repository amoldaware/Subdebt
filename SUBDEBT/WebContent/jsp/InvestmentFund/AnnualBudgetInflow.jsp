<%@ page language="java"%>
<%@ page import="java.util.HashMap"%>
<%@ page import="java.util.Set"%>
<%@ page import="java.util.Iterator"%>
<%@ page import="com.cgtsi.actionform.InvestmentForm"%>
<%@ taglib uri="/WEB-INF/TLD/struts-html.tld" prefix="html" %>
<%@ taglib uri="/WEB-INF/TLD/struts-bean.tld" prefix="bean" %>
<%@ include file="/jsp/SetMenuInfo.jsp" %>
<% session.setAttribute("CurrentPage","showBudgetInflowDetails.do?method=showBudgetInflowDetails");%>

<TABLE width="100%" border="0" cellspacing="0" cellpadding="0">
	<html:errors />
	<html:form action="setBudgetInflowDetails.do?method=setBudgetInflowDetails" method="POST" focus="annualFromDate">

		<TR>
			<TD width="20" align="right" valign="bottom"><IMG src="images/TableLeftTop.gif" width="20" height="31"></TD>
			<TD background="images/TableBackground1.gif"><IMG src="images/InvestmentManagementHeading.gif" width="169" height="25"></TD>
			<TD width="20" align="left" valign="bottom"><IMG src="images/TableRightTop.gif" width="23" height="31"></TD>
		</TR>
		<TR>
			<TD width="20" background="images/TableVerticalLeftBG.gif">&nbsp;</TD>
			<TD>
			<DIV align="right">			
				<A HREF="javascript:submitForm('helpAnnualInflowDetails.do?method=helpAnnualInflowDetails')">
			    HELP</A>
			</DIV>
						
				<TABLE width="100%" border="0" align="left" cellpadding="0" cellspacing="0">
					<TR>
						<TD>
							<TABLE width="100%" border="0" cellspacing="1" cellpadding="1">
								<TR>
									<TD colspan="4">
										<TABLE width="100%" border="0" cellpadding="0" cellspacing="0">
											<TR>
												<TD width="31%" class="Heading">
												<bean:message key="inflowDetailsHeading" /></TD>
												<TD><IMG src="images/TriangleSubhead.gif" width="19" height="19"></TD>
											</TR>
											<TR>
												<TD colspan="3" class="Heading"><IMG src="images/Clear.gif" width="5" height="5"></TD>
											</TR>

										</TABLE>
									</TD>

								</TR>
								<TR align="left" valign="top">
									<TD width="30%" valign="top" class="ColumnBackground">
										<DIV align="left">
											&nbsp;&nbsp;<font color="#FF0000" size="2">*</font>&nbsp;<bean:message key="annualFromDate" />
										</DIV>
									</TD>
									<TD align="left" valign="top" class="TableData">
										<html:text property="annualFromDate" size="20"  alt="Annual From Date" name="investmentForm" maxlength="10"/>
										<IMG src="images/CalendarIcon.gif" width="20" onClick="showCalendar('investmentForm.annualFromDate')" align="center">
									</TD>
								</TR>
								<TR align="left" valign="top">
									<TD width="30%" valign="top" class="ColumnBackground">
										<DIV align="left">
											&nbsp;&nbsp;<font color="#FF0000" size="2">*</font>&nbsp;<bean:message key="annualToDate" />
										</DIV>
									</TD>
									<TD align="left" valign="top" class="TableData">
										<html:text property="annualToDate" size="20"  alt="Annual To Date" name="investmentForm" maxlength="10"/>
										<IMG src="images/CalendarIcon.gif" width="20" onClick="showCalendar('investmentForm.annualToDate')" align="center">
										&nbsp<a href="javascript:submitForm('getAnnualBudgetInflowDetails.do?method=getAnnualBudgetInflowDetails')"><bean:message key="getBudgetInflowDetails"/> </a>
									</TD>
								</TR>

								<%
									int noOfHeads=0;
									int noOfSubHeads=0;
									InvestmentForm ifForm=(InvestmentForm) session.getAttribute("investmentForm");
									//System.out.println("annual/shortTerm "+ifForm.getAnnualOrShortTerm());
								%>

								<%
									HashMap heads=new HashMap(ifForm.getHeadsToRender());
									HashMap subHeads=new HashMap();
									String headTitle="";
									String subHeadTitle="";

									Set headsSet=heads.keySet();
									Iterator headsIterator=headsSet.iterator();
								%>

								<%
									int i = 0;
									while (headsIterator.hasNext())
									{

								%>

								<%
										headTitle=headsIterator.next().toString();
										//System.out.println("Head title "+headTitle);
										if (heads.get(headTitle) == null)
										{
									%>

											<%
											//System.out.println("jsp: No sub heads");
											String headKey="head("+headTitle+")";
											//System.out.println("jsp head key: "+headKey+","+ifForm.getHead(headKey));

											%>

										<TR align="left" valign="top">
											<TD width="20%" valign="top" class="ColumnBackground">
												<DIV align="left">
													&nbsp;<%= headTitle%>
												</DIV>
											</TD>

											<TD align="left" valign="top" class="TableData">
												<html:text name="investmentForm" property="<%=headKey%>" size="20" onkeypress="return decimalOnly(this, event, 13)" onkeyup="isValidDecimal(this)" maxlength="16"/> &nbsp;<bean:message key="inRs" />
											</TD>
										</TR>
									<%
										}
										else
										{
											//System.out.println("jsp: has sub heads ");
											subHeads=(HashMap) heads.get(headTitle);
											Set subHeadsSet=subHeads.keySet();
											Iterator subHeadsIterator=subHeadsSet.iterator();
									%>
										<TR align="left" valign="top">
											<TD width="20%" valign="top" class="ColumnBackground" colspan="2">
												<DIV align="left">
													&nbsp;<%=headTitle%>
												</DIV>
											</TD>
										</TR>
									<%

											while (subHeadsIterator.hasNext())
											{	i++;
												subHeadTitle=subHeadsIterator.next().toString();
												//out.println("jsp "+subHeadTitle);
									%>

										<TR align="left" valign="top">
											<TD width="20%" valign="top" class="ColumnBackground">
												<DIV align="left">&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
													&nbsp;<%= subHeadTitle%>
												</DIV>
											</TD>
											<% String subHeadsName = "subHead("+headTitle+"_"+subHeadTitle+")"; %>

											<TD align="left" valign="top" class="TableData">
										<html:text name="investmentForm" property="<%=subHeadsName%>" size="20" onkeypress="return decimalOnly(this, event, 13)" onkeyup="isValidDecimal(this)" maxlength="16"/> &nbsp;<bean:message key="inRs" />
											</TD>
										</TR>
									<%
											}
										}
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
								
								<A href="javascript:submitForm('setBudgetInflowDetails.do?method=setBudgetInflowDetails')">
									<IMG src="images/Save.gif" alt="Save" width="49" height="37" border="0"></A>
								<A href="javascript:document.investmentForm.reset()">
									<IMG src="images/Reset.gif" alt="Cancel" width="49" height="37" border="0"></A>
								<A href="subHome.do?method=getSubMenu&menuIcon=<%=session.getAttribute("menuIcon")%>&mainMenu=<%=session.getAttribute("mainMenu")%>">
									<IMG src="images/Cancel.gif" alt="Cancel" width="49" height="37" border="0"></A>
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
</TABLE>