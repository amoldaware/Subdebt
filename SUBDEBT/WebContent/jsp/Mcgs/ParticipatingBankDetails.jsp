<%@ page language="java"%>
<%@ include file="/jsp/SetMenuInfo.jsp" %>
<%@ page import="com.cgtsi.util.SessionConstants"%>
<%@ taglib uri="/WEB-INF/TLD/struts-html.tld" prefix="html" %>
<%@ taglib uri="/WEB-INF/TLD/struts-bean.tld" prefix="bean" %>
<% session.setAttribute("CurrentPage","showParticipatingBank.do?method=showParticipatingBank");%>
<%String focusField="";

if(request.getAttribute(SessionConstants.MCGS_ACTION_FLAG)!=null && request.getAttribute(SessionConstants.MCGS_ACTION_FLAG).equals("1"))
{
	focusField="district";
}
else{

	focusField="mcgf";
}
%>

<HTML>
	<BODY>		
		<html:errors />
		<html:form action="addParticipatingBank.do" method="POST" focus="<%=focusField%>">
			<TABLE width="725" border="0" cellpadding="0" cellspacing="0">
				<TR> 
					<TD width="20" align="right" valign="bottom"><IMG src="images/TableLeftTop.gif" width="20" height="31"></TD>
					<TD background="images/TableBackground1.gif"></TD>
					<TD width="20" align="left" valign="bottom"><IMG src="images/TableRightTop.gif" width="23" height="31"></TD>
				</TR>
				<TR>
					<TD width="20" background="images/TableVerticalLeftBG.gif">&nbsp;</TD>
					<TD>
					<DIV align="right">			
						<A HREF="javascript:submitForm('participatingBankHelp.do?method=participatingBankHelp')">
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
														<TD width="31%" class="Heading"><bean:message key="addParticipatingBank" /></TD>
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
											<TD align="left" valign="top" class="ColumnBackground">
												&nbsp;<font color="#FF0000" size="2">*</font>&nbsp;<bean:message key="bankName" />
											</TD>
											<TD align="left" class="TableData"> 
												<html:text property="bankName" size="20" alt="Bank name" name="mcgsForm" maxlength="100"/>
											</TD>
										</TR>

										<TR align="left">
											<TD align="left" valign="top" class="ColumnBackground">
												&nbsp;<font color="#FF0000" size="2">*</font>&nbsp;<bean:message key="branchName" />
											</TD>
											<TD align="left" class="TableData"> 
												<html:text property="branchName" size="20" alt="Branch name" name="mcgsForm" maxlength="100"/>
											</TD>
										</TR>

										<TR align="left">
											<TD align="left" valign="top" class="ColumnBackground">
												&nbsp;<font color="#FF0000" size="2">*</font>&nbsp;<bean:message key="address" />
											</TD>
											<TD align="left" class="TableData"> 
												<html:textarea property="address" cols="20" alt="Address" name="mcgsForm"/>
											</TD>
										</TR>

										<TR align="left">
											<TD align="left" valign="top" class="ColumnBackground">
												&nbsp;<font color="#FF0000" size="2">*</font>&nbsp;<bean:message key="city" />
											</TD>
											<TD align="left" class="TableData"> 
												<html:text property="city" size="20" alt="City" name="mcgsForm" maxlength="50"/>
											</TD>
										</TR>

										<TR align="left">
											<TD align="left" valign="top" class="ColumnBackground">
												&nbsp;<font color="#FF0000" size="2">*</font>&nbsp;<bean:message key="state" />
											</TD>
											<TD align="left" class="TableData"> 
												<html:select property="state" name="mcgsForm" onchange="javascript:submitForm('showParticipatingBank.do?method=getDistricts')">
													<html:option value="">Select</html:option>
													<html:options property="states" name="mcgsForm"/>				
												</html:select>
											</TD>
										</TR>				

										<TR align="left">
											<TD align="left" valign="top" class="ColumnBackground">
												&nbsp;<font color="#FF0000" size="2">*</font>&nbsp;<bean:message key="district" />
											</TD>
											<TD align="left" class="TableData"> 
												<html:select property="district" name="mcgsForm">
													<html:option value="">Select</html:option>
													<html:options property="districts" name="mcgsForm"/>				
												</html:select>
											</TD>
										</TR>
										
										<TR align="left">
											<TD align="left" valign="top" class="ColumnBackground">
												&nbsp;<font color="#FF0000" size="2">*</font>&nbsp;<bean:message key="phoneNo" />
											</TD>
											<TD align="left" class="TableData"> 
												<html:text property="stdCode" size="10" alt="Phone No" name="mcgsForm" maxlength="10" onkeypress="return numbersOnly(this, event)" onkeyup="isValidNumber(this)"/>
												&nbsp;&nbsp-&nbsp&nbsp;
												<html:text property="phoneNo" size="10" alt="Phone No" name="mcgsForm" maxlength="20" onkeypress="return numbersOnly(this, event)" onkeyup="isValidNumber(this)"/>
											</TD>
										</TR>								

										<TR align="left">
											<TD align="left" valign="top" class="ColumnBackground">
												&nbsp;<font color="#FF0000" size="2">*</font>&nbsp;<bean:message key="contactPerson" />
											</TD>
											<TD align="left" class="TableData"> 
												<html:text property="contactPerson" size="20" alt="Contact Person" name="mcgsForm" maxlength="50"/>
											</TD>
										</TR>

										<TR align="left">
											<TD align="left" valign="top" class="ColumnBackground">
												&nbsp;<bean:message key="emailId" />
											</TD>
											<TD align="left" class="TableData"> 
												<html:text property="emailId" size="20" alt="Email Address" name="mcgsForm" maxlength="40"/>
											</TD>
										</TR>							
										<TR align="left">
											<TD align="left" valign="top" class="ColumnBackground">
												&nbsp;<font color="#FF0000" size="2">*</font>&nbsp;<bean:message key="hoAddress" />
											</TD>
											<TD align="left" class="TableData"> 
												<html:textarea property="hoAddress" cols="20" alt="HO Address" name="mcgsForm"/>
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
										<A href="javascript:submitForm('addParticipatingBank.do?method=addParticipatingBank')"><IMG src="images/Save.gif" alt="Save" width="49" height="37" border="0"></A>								
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
			</TABLE>
		</html:form>
		
	</BODY>
</HTML>		





						
