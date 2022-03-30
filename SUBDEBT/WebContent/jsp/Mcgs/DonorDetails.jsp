<%@ page language="java"%>
<%@ include file="/jsp/SetMenuInfo.jsp" %>
<%@ taglib uri="/WEB-INF/TLD/struts-html.tld" prefix="html" %>
<%@ taglib uri="/WEB-INF/TLD/struts-bean.tld" prefix="bean" %>
<% session.setAttribute("CurrentPage","showAddDonorDetails.do");%>
<HTML>
	<BODY>
		<TABLE width="725" border="0" cellpadding="0" cellspacing="0">
			<html:errors />
			<html:form action="addDonorDetails.do?method=addDonorDetails" method="POST" focus="mcgf">
				<TR> 
					<TD width="20" align="right" valign="bottom"><IMG src="images/TableLeftTop.gif" width="20" height="31"></TD>
					<TD background="images/TableBackground1.gif"></TD>
					<TD width="20" align="left" valign="bottom"><IMG src="images/TableRightTop.gif" width="23" height="31"></TD>
				</TR>
				<TR>
					<TD width="20" background="images/TableVerticalLeftBG.gif">&nbsp;</TD>
					<TD>
					<DIV align="right">			
						<A HREF="javascript:submitForm('donorDetailsHelp.do?method=donorDetailsHelp')">
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
														<TD width="31%" class="Heading"><bean:message key="addDonorDetails" /></TD>
														<TD><IMG src="images/TriangleSubhead.gif" width="19" height="19"></TD>
													</TR>
													<TR>
														<TD colspan="3" class="Heading"><IMG src="images/Clear.gif" width="5" height="5"></TD>
													</TR>

												</TABLE>
											</TD>
										</TR>
										<TR align="left">
											<TD class="ColumnBackground" width="40%">
												&nbsp;<font color="#FF0000" size="2">*</font>&nbsp;<bean:message key="mcgfName" />
											</TD>
											<TD class="TableData" width="60%"> 
												<html:select property="mcgf" name="mcgsForm" >
													<html:option value="">Select</html:option>
													<html:options name="mcgsForm" property="mcgfs"/>
												</html:select>
											</TD>
										</TR>
										<TR align="left">
											<TD align="left" valign="top" class="ColumnBackground" width="40%">
												&nbsp;<font color="#FF0000" size="2">*</font>&nbsp;<bean:message key="donorName" />
											</TD>
											<TD align="left" class="TableData" width="60%"> 
												<html:text property="name" size="20" alt="Donor name" name="mcgsForm" maxlength="100"/>
											</TD>
										</TR>
										<TR align="left">
											<TD align="left" valign="top" class="ColumnBackground">
												&nbsp;<font color="#FF0000" size="2">*</font>&nbsp;<bean:message key="address" />
											</TD>
											<TD align="left" class="TableData"> 
												<html:textarea property="address" cols="20" alt="Address" name="mcgsForm" />
											</TD>
										</TR>

										<TR align="left">
											<TD align="left" valign="top" class="ColumnBackground">
												&nbsp;<font color="#FF0000" size="2">*</font>&nbsp;<bean:message key="corpusContributionAmt" />
											</TD>
											<TD align="left" class="TableData"> 
												<html:text property="corpusContributionAmt" size="20"  maxlength="13" name="mcgsForm" onkeypress="return decimalOnly(this, event)" onkeyup="isValidDecimal(this)"/>
											</TD>
										</TR>
										<TR align="left">
											<TD align="left" valign="top" class="ColumnBackground">
												&nbsp;<font color="#FF0000" size="2">*</font>&nbsp;<bean:message key="corpusContributionDate" />
											</TD>
											<TD align="left" class="TableData"> 
												<html:text property="corpusContributionDate" size="20"  name="mcgsForm" maxlength="10"/>
												<IMG src="images/CalendarIcon.gif" width="20" onClick="showCalendar('mcgsForm.corpusContributionDate')" align="center">
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
										<A href="javascript:submitForm('addDonorDetails.do?method=addDonorDetails')"><IMG src="images/Save.gif" alt="Save" width="49" height="37" border="0"></A>
										<A href="javascript:document.mcgsForm.reset()">
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





						
