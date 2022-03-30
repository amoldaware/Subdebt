
<%@ page language="java"%>
<%@ taglib uri="/WEB-INF/TLD/struts-html.tld" prefix="html" %>
<%@ taglib uri="/WEB-INF/TLD/struts-bean.tld" prefix="bean" %>
<% session.setAttribute("CurrentPage","showPLR.do?method=showPLR");%>
<%@ include file="/jsp/SetMenuInfo.jsp" %>
<HTML>
<body onLoad="choosePLR(this)">
<TABLE width="725" border="0" cellpadding="0" cellspacing="0">
	<html:errors />
	<html:form action="addPLR.do" method="POST" focus="shortNameMemId">
		<TR> 
			<TD width="20" align="right" valign="bottom"><IMG src="images/TableLeftTop.gif" width="20" height="31"></TD>
			<TD background="images/TableBackground1.gif"></TD>
			<TD width="20" align="left" valign="bottom"><IMG src="images/TableRightTop.gif" width="23" height="31"></TD>
		</TR>
		<TR>
			<TD width="20" background="images/TableVerticalLeftBG.gif">&nbsp;</TD>
			<TD>
				<TABLE width="100%" border="0" align="left" cellpadding="0" cellspacing="0">
					<TR>
						<TD>
						<DIV align="right">			
				<A HREF="javascript:submitForm('helpPLR.do?method=helpPLR')">
			    HELP</A>
			</DIV>
							<TABLE width="100%" border="0" cellspacing="1" cellpadding="1">
							<TD  align="left" colspan="4"><font size="2"><bold>
				Fields marked as </font><font color="#FF0000" size="2">*</font><font size="2"> are mandatory</bold></font>
				</td>
								<TR>
									<TD colspan="4"> 
										<TABLE width="100%" border="0" cellpadding="0" cellspacing="0">
											<TR>
												<TD width="31%" class="Heading"><bean:message key="plrMasterHeader" /></TD>
												<TD><IMG src="images/TriangleSubhead.gif" width="19" height="19"></TD>
											</TR>
											<TR>
												<TD colspan="4" class="Heading"><IMG src="images/Clear.gif" width="5" height="5"></TD>
											</TR>

										</TABLE>
									</TD>
								</TR>
								<TR align="left">
									<TD align="left" valign="top" class="ColumnBackground">
										&nbsp;<font color="#FF0000" size="2">*</font><bean:message key="plrBankName" />						</TD>							
										<TD align="left" class="TableData">  
										<html:select property="shortNameMemId"  name="adminForm">
											<html:option value="">Select</html:option>
											<html:options property="plrBanks" name="adminForm"/>		
										</html:select>										
										
									</TD>
																							
								</TR>

								

								<TR align="left">
									<TD align="left" valign="top" class="ColumnBackground">
									&nbsp;<font color="#FF0000" size="2">*</font><bean:message key="startDate" />
									</TD>
									<TD align="left" class="TableData"> 
									<html:text property="startDate" size="20" maxlength="10" alt="start Date" name="adminForm"/>&nbsp&nbsp<IMG src="images/CalendarIcon.gif" width="20" onClick="showCalendar('adminForm.startDate')" align="center">
									</TD>
								</TR>

								<TR align="left">
									<TD align="left" valign="top" class="ColumnBackground">
									&nbsp;<bean:message key="endDate" />
									</TD>
									<TD align="left" class="TableData"> 
									<html:text property="endDate" size="20" maxlength="10" alt="end Date" name="adminForm"/>&nbsp&nbsp<IMG src="images/CalendarIcon.gif" width="20" onClick="showCalendar('adminForm.endDate')" align="center">
									</TD>
								</TR>

								<TR align="left">
									<TD align="left" valign="top" class="ColumnBackground">
									&nbsp;<font color="#FF0000" size="2">*</font><bean:message key="shortTermPLR" />
									</TD>
									<TD align="left" class="TableData"> 
									<html:text property="shortTermPLR" size="20" onkeypress="return decimalOnly(this, event,2)" onkeyup="isValidDecimal(this)" maxlength="5" alt="short Term PLR" name="adminForm"/>&nbsp&nbsp<bean:message key="in" />
									</TD>
								</TR>

								<TR align="left">
									<TD align="left" valign="top" class="ColumnBackground">
									&nbsp;<font color="#FF0000" size="2">*</font><bean:message key="mediumTermPLR" />
									</TD>
									<TD align="left" class="TableData"> 
									<html:text property="mediumTermPLR" size="20" onkeypress="return decimalOnly(this, event,2)" onkeyup="isValidDecimal(this)" maxlength="5" alt="medium Term PLR" name="adminForm"/>&nbsp&nbsp<bean:message key="in" />
									</TD>
								</TR>

								<TR align="left">
									<TD align="left" valign="top" class="ColumnBackground">
									&nbsp;<font color="#FF0000" size="2">*</font><bean:message key="longTermPLR" />
									</TD>
									<TD align="left" class="TableData"> 
									<html:text property="longTermPLR" size="20" onkeypress="return decimalOnly(this, event,2)" onkeyup="isValidDecimal(this)" maxlength="5" alt="long Term PLR" name="adminForm"/>&nbsp&nbsp<bean:message key="in" />
									</TD>
								</TR>

								<TR align="left">
									<TD align="left" valign="top" class="ColumnBackground">
										&nbsp;<font color="#FF0000" size="2">*</font><bean:message key="shortTermPeriod" />
									</TD>
									<TD align="left" class="TableData"> 
										<html:text property="shortTermPeriod" size="20" onkeypress="return numbersOnly(this, event)" onkeyup="isValidNumber(this)" maxlength="3" alt="short Term Period" name="adminForm"/>&nbsp&nbsp<bean:message key="inDays" />
									</TD>
								</TR>

								<TR align="left">
									<TD align="left" valign="top" class="ColumnBackground">
										&nbsp;<font color="#FF0000" size="2">*</font><bean:message key="mediumTermPeriod" />
									</TD>
									<TD align="left" class="TableData"> 
										<html:text property="mediumTermPeriod" size="20" onkeypress="return numbersOnly(this, event)" onkeyup="isValidNumber(this)" maxlength="3" alt="medium Term Period" name="adminForm"/>&nbsp&nbsp<bean:message key="years" />
									</TD>
								</TR>

								<TR align="left">
									<TD align="left" valign="top" class="ColumnBackground">
										&nbsp;<font color="#FF0000" size="2">*</font><bean:message key="longTermPeriod" />
									</TD>
									<TD align="left" class="TableData"> 
										<html:text property="longTermPeriod" size="20" onkeypress="return numbersOnly(this, event)" onkeyup="isValidNumber(this)" maxlength="3" alt="long Term Period" name="adminForm"/>&nbsp&nbsp<bean:message key="years" />
									</TD>
								</TR>
								<TR align="left">
									<TD align="left" valign="top" class="ColumnBackground">
										&nbsp;<font color="#FF0000" size="2">*</font><bean:message key="PLR"/>							
									</TD>
									<TD align="left" class="TableData">
										<html:radio name="adminForm" value="B" property="PLR" onclick="choosePLR(this)" ><bean:message key="BPLR" /></html:radio>&nbsp;&nbsp;

										<html:radio name="adminForm" value="T" property="PLR" onclick="choosePLR(this)" ><bean:message key="TPLR" /></html:radio>
									</TD>
								</TR>
								<TR align="left">
									<TD align="left" valign="top" class="ColumnBackground">
										&nbsp;<bean:message key="BPLR" />
									</TD>
									<TD align="left" class="TableData"> 
										<html:text property="BPLR" size="20" alt="BPLR" maxlength="6" name="adminForm" onkeypress="return decimalOnly(this, event,2)" onkeyup="isValidDecimal(this)"/>
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
						<TD align="center" valign="baseline" colspan="2">
							<DIV align="center">
							<A href="javascript:submitForm('addPLR.do?method=addPLR')"><IMG src="images/Save.gif" alt="Save" width="49" height="37" border="0"></A>
								<A href="javascript:document.adminForm.reset()">
									<IMG src="images/Reset.gif" alt="Cancel" width="49" height="37" border="0"></A>
							<A href="subHome.do?method=getSubMenu&menuIcon=<%=session.getAttribute("menuIcon")%>&mainMenu=<%=session.getAttribute("mainMenu")%>"><IMG src="images/Cancel.gif" alt="Cancel" width="49" height="37" border="0"></A>									
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
<body/>
<HTML/>




						





			










         
