<%@ page language="java"%>
<%@ page import="java.util.ArrayList"%>

<%@ taglib uri="/WEB-INF/TLD/struts-html.tld" prefix="html" %>
<%@ taglib uri="/WEB-INF/TLD/struts-bean.tld" prefix="bean" %>
<%@ taglib uri="/WEB-INF/TLD/struts-logic.tld" prefix="logic" %>
<%@ include file="/jsp/SetMenuInfo.jsp" %>
<% session.setAttribute("CurrentPage","showDtlsForChqLeavesInsert.do?method=showDtlsForChqLeavesInsert");%>

<TABLE width="725" border="0" cellpadding="0" cellspacing="0">
	<html:errors />
	<html:form action="insertChqDetails.do?method=insertChqDetails" method="POST" enctype="multipart/form-data" focus="bnkName">
		<TR> 
			<TD width="20" align="right" valign="bottom"><IMG src="images/TableLeftTop.gif" width="20" height="31"></TD>
			<TD background="images/TableBackground1.gif"><IMG src="images/InvestmentManagementHeading.gif" width="180" height="25"></TD>
			<TD width="20" align="left" valign="bottom"><IMG src="images/TableRightTop.gif" width="23" height="31"></TD>
		</TR>
		<TR>
			<TD width="20" background="images/TableVerticalLeftBG.gif">&nbsp;</TD>
			<TD>
				<DIV align="right">		
				<A HREF="chqInsertHelp.do?method=chqInsertHelp">
				HELP</A>

				<TABLE width="100%" border="0" align="left" cellpadding="0" cellspacing="0">
					<TR>
						<TD>
							<TABLE width="100%" border="0" cellspacing="1" cellpadding="1">
								<TR>
									<TD colspan="12"> 
										<TABLE width="100%" border="0" cellpadding="0" cellspacing="0">
											<TR>
												<TD width="24%" class="Heading"><bean:message key="ChequeInsertDetails" /></TD>
												<TD><IMG src="images/TriangleSubhead.gif" width="19" height="19"></TD>
											</TR>
											<TR>
												<TD colspan="3" class="Heading"><IMG src="images/Clear.gif" width="5" height="5"></TD>
											</TR>

										</TABLE>
									</TD>
								</TR>

								<TR>
									<TD align="left" valign="top" class="ColumnBackground">
										<font color="#FF0000" size="2">*</font>&nbsp;<bean:message key="chqBankName"/>
									</TD>
									<TD align="left" valign="top" class="TableData">
											<html:select property="bnkName" name="ifForm" >
												<html:option value="">Select</html:option>
												<html:options name="ifForm" property="bankAcctDetails"></html:options>
											</html:select>	
									</TD>

								</TR>

								<TR>
									<TD align="left" valign="top" class="ColumnBackground">
										<font color="#FF0000" size="2">*</font>&nbsp;<bean:message key="chqStartingNo"/>
									</TD>
									<TD align="left" valign="top" class="TableData">
											<html:text property="chqStartNo" size="30" alt="chqStartNo" name="ifForm" onkeypress="return numbersOnly(this, event)" onkeyup="isValidNumber(this)" maxlength="9"/>	
									</TD>

								</TR>
								
								<TR>
									<TD align="left" valign="top" class="ColumnBackground">
										<font color="#FF0000" size="2">*</font>&nbsp;<bean:message key="chqEndingNo"/>
									</TD>
									<TD align="left" valign="top" class="TableData">
											<html:text property="chqEndingNo" size="30" alt="chqEndingNo" name="ifForm" onkeypress="return numbersOnly(this, event)" onkeyup="isValidNumber(this)" maxlength="9"/>	
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
								<A href="javascript:submitForm('insertChqDetails.do?method=insertChqDetails')">
									<IMG src="images/Save.gif" alt="Back" width="49" height="37" border="0"></A>
								<A href="javascript:document.ifForm.reset()">
									<IMG src="images/Reset.gif" alt="Reset" width="49" height="37" border="0"></A>
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

