<%@ page language="java"%>
<%@ include file="/jsp/SetMenuInfo.jsp" %>
<%@ taglib uri="/WEB-INF/TLD/sTRuts-html.tld" prefix="html" %>
<%@ taglib uri="/WEB-INF/TLD/sTRuts-bean.tld" prefix="bean" %>
<% session.setAttribute("CurrentPage","showInvestor.do?method=showInvestor");%>
<HTML>
	<BODY>
		<TABLE width="725" border="0" cellpadding="0" cellspacing="0">
			<html:errors />
			<html:form action="createLoanPool.do?method=createLoanPool" method="POST">
				<TR> 
					<TD width="20" align="right" valign="bottom"><IMG src="images/TableLeftTop.gif" width="20" height="31"></TD>
					<TD background="images/TableBackground1.gif">
					<TD width="20" align="left" valign="bottom"><IMG src="images/TableRightTop.gif" width="23" height="31"></TD>
				</TR>
				<TR>
					<TD width="20" background="images/TableVerticalLeftBG.gif">&nbsp;</TD>
					<TD>
					<DIV align="right">			
						<A HREF="javascript:submitForm('addInvestorHelp.do?method=addInvestorHelp')">
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
														<TD width="31%" class="Heading"><bean:message key="addInvestor"/></TD>
														<TD><IMG src="images/TriangleSubhead.gif" width="19" height="19"></TD>
													</TR>
													<TR>
														<TD colspan="3" class="Heading"><IMG src="images/Clear.gif" width="5" height="5"></TD>
													</TR>

												</TABLE>
											</TD>
										</TR>
										<TR>
											<TD class="ColumnBackground" >
											<font color="#FF0000" size="2">*</font><bean:message key="loanPoolName"/>
											</TD>
											<TD class="TableData" >
												<html:select property="loanPoolName" name="securitizationForm" size="1">
													<html:option value="">Select</html:option>
													<html:options property="poolNames" name="securitizationForm" />
												</html:select>											
											</TD>
										</TR>
										<TR>
											<TD class="ColumnBackground" >
												<font color="#FF0000" size="2">*</font><bean:message key="name"/>
											</TD>
											<TD class="TableData" >
												<html:text property="investorName" name="securitizationForm" maxlength="100" />
											</TD>
										</TR>
										<TR>
											<TD class="ColumnBackground" >
												<font color="#FF0000" size="2">*</font><bean:message key="amount"/>
											</TD>
											<TD class="TableData" >
												<html:text property="investedAmount" name="securitizationForm" maxlength="16" onkeypress="return decimalOnly(this, event,13)" onkeyup="isValidDecimal(this)" />
											</TD>										
										</TR>
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
											<A href="javascript:submitForm('addInvestor.do?method=addInvestor')">
											<IMG src="images/Save.gif" alt="Save the investor details" width="49" height="37" border="0"></A>

										<A href="javascript:document.securitizationForm.reset()">
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
	</BODY>
</HTML>		



