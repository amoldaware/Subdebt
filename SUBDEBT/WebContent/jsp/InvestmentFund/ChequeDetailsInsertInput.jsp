<%@ page language="java"%>
<%@ page import="java.util.ArrayList"%>

<%@ taglib uri="/WEB-INF/TLD/struts-html.tld" prefix="html" %>
<%@ taglib uri="/WEB-INF/TLD/struts-bean.tld" prefix="bean" %>
<%@ taglib uri="/WEB-INF/TLD/struts-logic.tld" prefix="logic" %>
<%@ include file="/jsp/SetMenuInfo.jsp" %>
<% session.setAttribute("CurrentPage","chequeDetailsInsertInput.do?method=chequeDetailsInsertInput");%>

<TABLE width="725" border="0" cellpadding="0" cellspacing="0">
	<html:errors />
	<html:form action="chequeDetailsInsertSuccess.do?method=chequeDetailsInsertSuccess" method="POST" enctype="multipart/form-data">
		<TR> 
			<TD width="20" align="right" valign="bottom"><IMG src="images/TableLeftTop.gif" width="20" height="31"></TD>
			<TD background="images/TableBackground1.gif"><IMG src="images/InvestmentManagementHeading.gif" width="169" height="25"></TD>
			<TD width="20" align="left" valign="bottom"><IMG src="images/TableRightTop.gif" width="23" height="31"></TD>
		</TR>
		<TR>
			<TD width="20" background="images/TableVerticalLeftBG.gif">&nbsp;</TD>
			<TD>
			<DIV align="right">			
			<A HREF="chequeDetailsInsertHelp.do?method=chequeDetailsInsertHelp">
			HELP</A>
				<TABLE width="100%" border="0" align="left" cellpadding="0" cellspacing="0">
				<tr>
				<TD  align="left" colspan="4"><font size="2"><bold>
				Fields marked as </font><font color="#FF0000" size="2">*</font><font size="2"> are mandatory</bold></font>
				</td>
				</tr>
					<TR>
						<TD>
							<TABLE width="100%" border="0" cellspacing="1" cellpadding="1">
								<TR>
									<TD colspan="10"> 
										<TABLE width="100%" border="0" cellpadding="0" cellspacing="0">
											<TR>
												<TD width="27%" class="Heading"><bean:message key="chequeHeader" /></TD>
												<TD><IMG src="images/TriangleSubhead.gif" width="19" height="19"></TD>
											</TR>
											<TR>
												<TD colspan="3" class="Heading"><IMG src="images/Clear.gif" width="5" height="5"></TD>
											</TR>

										</TABLE>
									</TD>


								<TR align="left" valign="top">
									<TD align="left" valign="top" class="ColumnBackground"> 
										<font color="#FF0000" size="2">*</font>
											&nbsp;<bean:message key="chequeBankName" />
										
									</TD>
									<TD align="left" class="TableData"> 
									<html:select  property="bankName"  name="ifForm">
											<html:option value="">Select</html:option>
											<html:options property="banks" name="ifForm"/>
										</html:select>
									</TD>
								</TR>


								<TR align="left" valign="top">
									<TD align="left" valign="top" class="ColumnBackground"> 
										<font color="#FF0000" size="2">*</font>
											&nbsp;<bean:message key="chequeNumbers" />
										
									</TD>
									<TD align="left" class="TableData"> 
										<html:text property="chequeNumber" size="20"  alt="Source Name"  maxlength = "100" onkeypress="return decimalOnly(this, event)" onkeyup="isValidDecimal(this)"name="ifForm"/>
										&nbsp;
									</TD>
								</TR>

								<TR>
									<TD align="left" valign="top" class="ColumnBackground">
									<font color="#FF0000" size="2">*</font>
									 &nbsp;<bean:message key="chequeDate" /> 
									</TD>
									   <TD  align="left" valign="center" class="ColumnBackground">
										  <DIV align="left">
										  <html:text property="chequeDate" size="20"  alt="Reference" maxlength="10" name="ifForm"/>
										  <IMG src="images/CalendarIcon.gif" width="20" onClick="showCalendar('ifForm.chequeDate')" align="center">
										  <DIV align="left">
									  </TD>
								</TR>

								<TR align="left" valign="top">
									<TD align="left" valign="top" class="ColumnBackground"> 
										<font color="#FF0000" size="2">*</font>
											&nbsp;<bean:message key="chequeAmount" />
										
									</TD>
									<TD align="left" class="TableData"> 
										<html:text property="chequeAmount" size="20"  alt="Source Name"  maxlength="16" onkeypress="return decimalOnly(this, event,13)" onkeyup="isValidDecimal(this)"name="ifForm"/>
									</TD>
								</TR>

								<TR>
									<TD align="left" valign="top" class="ColumnBackground"><font color="#FF0000" size="2">*</font>
										&nbsp;<bean:message key="chequeIssuedTo" />
									</TD>
									<TD align="left" valign="top" class="TableData"> 
										<html:text property="chequeIssuedTo" size="20"  alt="Reference"  maxlength = "50" name="ifForm"/>
									</TD>
								</TR>

								<TR>
									<TD align="left" valign="top" class="ColumnBackground">
										&nbsp;<bean:message key="remarks" />
									</TD>
									<TD align="left" valign="top" class="TableData"> 
										<html:textarea property="chequeRemarks" cols="70" rows="3" name="ifForm"/>
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
							        <A href="javascript:submitForm('chequeDetailsInsertSuccess.do?method=chequeDetailsInsertSuccess')">
									<IMG src="images/Save.gif" alt="Save" width="49" height="37" border="0"></A>
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

