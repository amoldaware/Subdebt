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
	<html:form action="setBudgetInflowDetails.do?method=setBudgetInflowDetails" method="POST" >
		<TR>
			<TD width="20" align="right" valign="bottom"><IMG src="images/TableLeftTop.gif" width="20" height="31"></TD>
			<TD background="images/TableBackground1.gif"><IMG src="images/InvestmentManagementHeading.gif" width="169" height="25"></TD>
			<TD width="20" align="left" valign="bottom"><IMG src="images/TableRightTop.gif" width="23" height="31"></TD>
		</TR>
		<TR>
			<TD width="20" background="images/TableVerticalLeftBG.gif">&nbsp;</TD>
			<TD>
			<DIV align="right">			
				<A HREF="javascript:submitForm('helpShortTermInflowDetails.do?method=helpShortTermInflowDetails')">
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
												<TD width="31%" class="Heading"><bean:message key="inflowDetailsHeading" /></TD>
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
											&nbsp;&nbsp;<font color="#FF0000" size="2">*</font>&nbsp;<bean:message key="year" />
										</DIV>
									</TD>
									<TD align="left" valign="top" class="TableData" colspan="2">
									<html:text property="year" name="investmentForm" maxlength="4" size="6" onkeypress="return numbersOnly(this, event)" onkeyup="isValidNumber(this)"/>
									</TD>
								</TR>
								<TR align="left" valign="top">
									<TD width="30%" valign="top" class="ColumnBackground">
										<DIV align="left">
											&nbsp;&nbsp;<font color="#FF0000" size="2">*</font>&nbsp;<bean:message key="month" />
										</DIV>
									</TD>
									<TD align="left" valign="top" class="TableData" colspan="2">
										<html:select property="month" name="investmentForm" onchange="javacript:submitForm('getShortTermInflowDetails.do?method=getShortTermInflowDetails')">
											<html:option value="">Select </html:option>
											<html:option value="January">January </html:option>
											<html:option value="February">February </html:option>
											<html:option value="March">March </html:option>
											<html:option value="April">April </html:option>
											<html:option value="May">May </html:option>
											<html:option value="June">June </html:option>
											<html:option value="July">July </html:option>
											<html:option value="August">August </html:option>
											<html:option value="September">September </html:option>
											<html:option value="October">October </html:option>
											<html:option value="November">November </html:option>
											<html:option value="December">December </html:option>

										</html:select>
									</TD>
								</TR>
								<TR>
									<TD class="ColumnBackground" width="30%" height="25">
										<b>&nbsp;<bean:message key="budgetMenu" /></b>
									</TD>
									<TD class="ColumnBackground" height="25">
										<b>&nbsp;<bean:message key="amountBudgeted" /></b>
									</TD>
									<TD class="ColumnBackground" height="25">
										<b>&nbsp;<bean:message key="amountInflow" /></b>
									</TD>
								</TR>

								<%
									int noOfHeads=0;
									int noOfSubHeads=0;
									InvestmentForm ifForm=(InvestmentForm) session.getAttribute("investmentForm");

									HashMap heads=new HashMap(ifForm.getHeadsToRender());

									HashMap subHeads=new HashMap();
									String headTitle="";
									String subHeadTitle="";
									int i = 0;
									Set headsSet=heads.keySet();
									Iterator headsIterator=headsSet.iterator();

									String headKey="";

									String shortHeadKey="";

									while (headsIterator.hasNext())
									{
										headTitle=headsIterator.next().toString();
										//System.out.println(heads.get(headTitle));
										headKey="head("+headTitle+")";
										shortHeadKey="shortHead("+headTitle+")";
										if (heads.get(headTitle) == null)
										{
									%>

										<TR >
											<TD class="ColumnBackground" width="30%" height="25">
												<DIV align="left">
													&nbsp;<%= headTitle%>
												</DIV>
											</TD>
											<TD class="TableData" height="25">&nbsp;<bean:write property="<%=headKey%>" name="investmentForm"/></TD>
											<TD class="TableData" height="25">
												<html:text name="investmentForm" property="<%=shortHeadKey%>" size="15" maxlength="13" onkeypress="return decimalOnly(this, event)" onkeyup="isValidDecimal(this)"/> &nbsp;<bean:message key="inRs" />
											</TD>
										</TR>
									<%
										}
										else
										{
											//out.println("jsp getting sub heads");
											subHeads=(HashMap) heads.get(headTitle);
											Set subHeadsSet=subHeads.keySet();
											Iterator subHeadsIterator=subHeadsSet.iterator();
									%>
										<TR align="left" valign="top">
											<TD width="20%" valign="top" class="ColumnBackground" colspan="3">
												<DIV align="left">
													&nbsp;
													<%=headTitle%>

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
											<%
												String subHeadsName = "subHead("+headTitle+"_"+subHeadTitle+")";
												String shortSubHeadName="shortSubHead("+headTitle+"_"+subHeadTitle+")";
											%>
											<TD class="TableData" height="25"><bean:write property="<%=subHeadsName%>" name="investmentForm"/></TD>
											<TD class="TableData" height="25">
										<html:text name="investmentForm" property="<%=shortSubHeadName%>" size="15" maxlength="13" onkeypress="return decimalOnly(this, event)" onkeyup="isValidDecimal(this)"/> &nbsp;<bean:message key="inRs" />
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
								<A href="javascript:submitForm('setShortTermBudgetInflowDetails.do?method=setShortTermBudgetInflowDetails')">
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