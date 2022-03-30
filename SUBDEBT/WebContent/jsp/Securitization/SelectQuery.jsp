<%@ page language="java"%>
<%@ include file="/jsp/SetMenuInfo.jsp" %>
<%@ taglib uri="/WEB-INF/TLD/sTRuts-html.tld" prefix="html" %>
<%@ taglib uri="/WEB-INF/TLD/sTRuts-bean.tld" prefix="bean" %>
<% session.setAttribute("CurrentPage","showSelectQuery.do?method=showSelectQuery");%>
<HTML>
	<BODY>
		<html:errors />
		<TABLE width="725" border="0" cellpadding="0" cellspacing="0">
			<TR>
				<TD>
					<TABLE width="725" border="0" cellpadding="0" cellspacing="0">
						<TR>
							<TD>
								<html:form action="getHomogenousLoans.do?method=getHomogenousLoans" method="POST">
								<TR>
									<TD width="20" align="right" valign="bottom"><IMG src="images/TableLeftTop.gif" width="20" height="31"></TD>
									<TD background="images/TableBackground1.gif"></TD>
									<TD width="20" align="left" valign="bottom"><IMG src="images/TableRightTop.gif" width="23" height="31"></TD>
								</TR>
								<TR>
									<TD width="20" background="images/TableVerticalLeftBG.gif">&nbsp;</TD>
									<TD>
									<DIV align="right">
										<A HREF="javascript:submitForm('loanPoolHelp.do?method=loanPoolHelp')">
										HELP</A>
									</DIV>
										<TABLE border="0" cellpadding="1" cellspacing="1" width="100%" >
											<TR>
												<TD colspan="4">
													<TABLE width="100%" border="0" cellspacing="0" cellpadding="0">
														<TR>
															<TD colspan="4">
																<TABLE width="100%" border="0" cellpadding="0" cellspacing="0">
																	<TR>
																		<TD width="31%" class="Heading"><bean:message key="selectQuery" /></TD>
																		<TD><IMG src="images/TriangleSubhead.gif" width="19" height="19"></TD>
																	</TR>
																	<TR>
																		<TD colspan="4" class="Heading"><IMG src="images/Clear.gif" width="5" height="5"></TD>
																	</TR>
																</TABLE>
															</TD>
														</TR>
													</TABLE>
												</TD>
											</TR>
											<TR>
												<TD class="ColumnBackground" rowspan="3">
													<font color="#FF0000" size="2">*</font><bean:message key="mli"/>
												</TD>
												<TD class="TableData" rowspan="3">
													<html:select property="mlis" name="securitizationForm" multiple="true" size="5">
														<html:option value="All">All</html:option>
														<html:options property="allMembers" name="securitizationForm" />
													</html:select>
												&nbsp;</TD>
												<TD class="ColumnBackground">
													<font color="#FF0000" size="2">*</font><bean:message key="loanTenure" />
												</TD>
												<TD class="TableData">
													<html:text property="tenure" name="securitizationForm" size="10" maxlength="5" onkeypress="return numbersOnly(this, event)" onkeyup="isValidNumber(this)"/>
													<bean:message key="inMonths"/>
												</TD>
											</TR>
											<TR>
												<TD class="ColumnBackground">
													<font color="#FF0000" size="2">*</font><bean:message key="secEffFrom" />
												</TD>
												<TD class="TableData">
													<html:text property="effectiveDate" name="securitizationForm" size="10" maxlength="10" />
													<IMG src="images/CalendarIcon.gif" width="20" onClick="showCalendar('securitizationForm.effectiveDate')" align="center">
												</TD>
											</TR>
											<TR>
												<TD  class="ColumnBackground">
													<font color="#FF0000" size="2">*</font><bean:message key="scloanType" />
												</TD>

												<TD  class="TableData">
													<html:radio property="loanType" name="securitizationForm" value="TC"/>
													<bean:message key="sctc"/>
													<html:radio property="loanType" name="securitizationForm" value="WC"/>
													<bean:message key="scwc"/>
													<html:radio property="loanType" name="securitizationForm" value="BO"/>
													<bean:message key="both"/>
												</TD>
											</TR>
											<TR>
												<TD  class="ColumnBackground">
													<font color="#FF0000" size="2">*</font><bean:message key="state" />
												</TD>
												<TD class="TableData">
													<html:select property="states" name="securitizationForm" multiple="true" size="5">
														<html:option value="All">All</html:option>
														<html:options property="allStates" name="securitizationForm" />
													</html:select>
												</TD>
												<TD class="ColumnBackground">
													<font color="#FF0000" size="2">*</font><bean:message key="industrySector"/>
												</TD>
												<TD  class="TableData">
													<html:select property="sectors" name="securitizationForm" multiple="true" size="5">
														<html:option value="All">All</html:option>
														<html:options property="allSectors" name="securitizationForm" />
													</html:select>
												</TD>
											</TR>
											<TR>
												<TD  class="ColumnBackground">
													<font color="#FF0000" size="2">*</font><bean:message key="interestRate" />&nbsp<bean:message key="between"/>
												</TD>
												<TD  class="TableData">

													<html:text property="interestRate" name="securitizationForm" size="10" maxlength="5" onkeypress="return decimalOnly(this, event,2)" onkeyup="isValidDecimal(this)"/>
													&nbsp<bean:message key="and"/>&nbsp
													<html:text property="nextInterestRate" name="securitizationForm" size="10" maxlength="5" onkeypress="return decimalOnly(this, event,2)" onkeyup="isValidDecimal(this)"/>
													<bean:message key="in"/>
												</TD>
												<TD  class="ColumnBackground">
													<font color="#FF0000" size="2">*</font><bean:message key="interestRateType"/>
												</TD>
												<TD  class="TableData">
													<html:radio property="typeOfInterest" name="securitizationForm" value="F" />
													<bean:message key="fixedInterest" />
													<html:radio property="typeOfInterest" name="securitizationForm" value="T" />
													<bean:message key="floatingInterest" />
												</TD>
											</TR>
											<TR>
												<TD class="ColumnBackground">
													<font color="#FF0000" size="2">*</font><bean:message key="loanSize"/>&nbsp<bean:message key="between"/>
												</TD>
												<TD class="TableData">

													<html:text property="loanSize" name="securitizationForm" size="15" maxlength="16" onkeypress="return decimalOnly(this, event,13)" onkeyup="isValidDecimal(this)"/>
													&nbsp<bean:message key="and"/>&nbsp
													<html:text property="nextLoanSize" name="securitizationForm" size="15" maxlength="16" onkeypress="return decimalOnly(this, event,13)" onkeyup="isValidDecimal(this)"/>
													<bean:message key="inRs"/>
												</TD>
												<TD class="ColumnBackground" >
													<font color="#FF0000" size="2">*</font><bean:message key="trackRecord" />
												</TD>
												<TD class="TableData" >
													<html:text property="trackRecord" maxlength="5" name="securitizationForm" size="10" onkeypress="return numbersOnly(this, event)" onkeyup="isValidNumber(this)"/>
													<bean:message key="inMonths"/>
												</TD>
											</TR>
											<TR align="center" valign="baseline" >
												<TD colspan="4" width="700"><DIV align="center">
													<A href="javascript:submitForm('getHomogenousLoans.do?method=getHomogenousLoans')">
														<IMG src="images/OK.gif" alt="Save" width="49" height="37" border="0"></A>
													<A href="javascript:document.securitizationForm.reset()">
														<IMG src="images/Reset.gif" alt="Cancel" width="49" height="37" border="0"></A>
													<A href="home.do?method=getMainMenu&menuIcon=<%=session.getAttribute("menuIcon")%>&mainMenu=<%=session.getAttribute("mainMenu")%>">
													<IMG src="images/Cancel.gif" alt="Cancel" width="49" height="37" border="0"></A>														
												</TD>

											</DIV>
										</TR>

										</TABLE>
									</TD>
									<TD width="20" background="images/TableVerticalRightBG.gif">
									&nbsp;
									</TD>
								</TR>
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
				</TD>
			</TR>
		</TABLE>
	</BODY>
</HTML>
