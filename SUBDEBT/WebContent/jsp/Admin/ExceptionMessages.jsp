<%@ page language="java"%>
<%@ taglib uri="/WEB-INF/TLD/struts-html.tld" prefix="html" %>
<%@ taglib uri="/WEB-INF/TLD/struts-bean.tld" prefix="bean" %>
<% session.setAttribute("CurrentPage","showExceptionMessages.do?method=showExceptionMessages");%>
<%@ include file="/jsp/SetMenuInfo.jsp" %>

<HTML>
	<BODY onLoad="exceptionOption(this)">
		<TABLE width="725" border="0" cellpadding="0" cellspacing="0">
			<html:errors />
			<html:form action="addExceptionMessages.do" method="POST">
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
				<A HREF="javascript:submitForm('helpException.do?method=helpException')">
			    HELP</A>
			</DIV>
									<TABLE width="100%" border="0" cellspacing="1" cellpadding="1">
										<TR>
											<TD colspan="4"> 
												<TABLE width="100%" border="0" cellpadding="0" cellspacing="0">
													<TR>
														<TD width="31%" class="Heading"><bean:message key="exceptionMessagesHeader" /></TD>
														<TD><IMG src="images/TriangleSubhead.gif" width="19" height="19"></TD>
													</TR>
													<TR>
														<TD colspan="3" class="Heading"><IMG src="images/Clear.gif" width="5" height="5"></TD>
													</TR>

												</TABLE>
											</TD>
										</TR>

										<TR align="left">
											<TD align="left" valign="top" class="ColumnBackground">
												&nbsp;<font color="#FF0000" size="2">*</font><bean:message key="exceptionTitle" />
											</TD>
											<TD align="left" class="TableData">
												<html:select property="exceptionTitle" name="adminForm" onchange="getExceptionDetails(this,'showExceptionMessages.do?method=getExceptionMessage');exceptionOption(this)" >
													<html:option value="">Select</html:option>
													<html:options property="exceptionTitles" name="adminForm"/> 
												</html:select>
											</TD>
											<!-- <TD align="left" class="TableData"><bean:message key="exceptionEnter" />
												<html:text property="newExceptionTitle" size="20" alt="Enter" name="adminForm"/>
											</TD>		 -->							
										</TR>
										<TR align="left">
											<TD align="left" valign="top" class="ColumnBackground">
												&nbsp;<font color="#FF0000" size="2">*</font><bean:message key="exceptionMessage" />
											</TD>
											<TD align="left" class="TableData" colspan="3"> 
												<html:textarea property="exceptionMessage" cols="35" rows="5" alt="Message"name="adminForm"/>&nbsp;&nbsp;&nbsp;&nbsp;
											&nbsp;	&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;	&nbsp;&nbsp;
										<font color="#FF0000" size="2">*</font><bean:message key="exceptionType" />
												<html:select property="exceptionType" name="adminForm">
													<html:option value="">Select</html:option>
													<html:option value="W">Warning</html:option>
													<html:option value="X">Exception</html:option>
													<html:option value="E">Error</html:option>
												</html:select>									
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

									<A href="javascript:submitForm('addExceptionMessages.do?method=addExceptionMessages')"><IMG src="images/Save.gif" alt="Save" width="49" height="37" border="0"></A>	
										<A href="javascript:document.adminForm.reset()">
											<IMG src="images/Reset.gif" alt="Cancel" width="49" height="37" border="0"></A>
																	
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