<%@ page language="java"%>
<%@ taglib uri="/WEB-INF/TLD/struts-html.tld" prefix="html" %>
<%@ taglib uri="/WEB-INF/TLD/struts-bean.tld" prefix="bean" %>
<% session.setAttribute("CurrentPage","searchDocument.do?method=searchDocument");%>
<%@ include file="/jsp/SetMenuInfo.jsp" %>



<TABLE width="725" border="0" cellpadding="0" cellspacing="0">
	<html:errors />
	<html:form action="searchResult.do?method=searchResult" method="POST" enctype="multipart/form-data">
		<TR> 
			<TD width="20" align="right" valign="bottom"><IMG src="images/TableLeftTop.gif" width="20" height="31"></TD>
			<TD background="images/TableBackground1.gif"><IMG src="images/InwardOutwardHeading.gif" width="121" height="25"></TD>
			<TD width="20" align="left" valign="bottom"><IMG src="images/TableRightTop.gif" width="23" height="31"></TD>
		</TR>
		<TR>
			<TD width="20" background="images/TableVerticalLeftBG.gif">&nbsp;</TD>
			<TD>
			<DIV align="right">			
			<A HREF="searchHelp.do?method=searchHelp">
			HELP</A>
				<TABLE width="100%" border="0" align="left" cellpadding="0" cellspacing="0">
					<TR>
						<TD>
							<TABLE width="100%" border="0" cellspacing="1" cellpadding="1">
								<TR>
									<TD colspan="4"> 
										<TABLE width="100%" border="0" cellpadding="0" cellspacing="0">
											<TR>
												<TD width="31%" class="Heading"><bean:message key="searchHeader" /></TD>
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
										&nbsp;<bean:message key="fileNumber" />
									</TD>
									<TD colspan="3" align="left" valign="top" class="TableData">
										<html:text property="fileNumber" size="20"  alt="File Number" maxlength="20"  name="ioForm"/>
									</TD>
								</TR>	

								<TR>
									<TD align="left" valign="top" class="ColumnBackground">
										&nbsp;<bean:message key="fileTitle" />
									</TD>
									<TD colspan="3" align="left" valign="top" class="TableData">
										<html:text property="fileTitle" size="20"  alt="File Title" maxlength="50"  name="ioForm"/>
									</TD>
								</TR>

								<TR>
									<TD align="left" valign="top" class="ColumnBackground">
										&nbsp;<bean:message key="subject" />
									</TD>
									<TD colspan="3" align="left" valign="top" class="TableData">
										<html:text property="subject" size="20"  alt="Subject" maxlength="50"  name="ioForm"/>
									</TD>
								</TR>

							<TR>
									<TD width="20%" align="left" valign="top" class="ColumnBackground">
										<DIV align="left">
											&nbsp;<bean:message key="category" />
										</DIV>
									</TD>
									<TD width="27%" align="left" valign="top" class="TableData">
										<html:select property="category" name="ioForm">
											<html:option value="">Select</html:option>
											<html:option value="Inward">Inward</html:option>
											<html:option value="Outward">Outward</html:option>
											<html:option value="Others">Others</html:option>
										</html:select>
									</TD>
								</TR>

								<TR>
									<TD align="left" valign="top" class="ColumnBackground">
										  &nbsp;<bean:message key="documentDate" /> 
									</TD>
									  <TD align="left" valign="center" class="TableData">
										    <html:text property="dateOfTheDocument" size="20"  value="" maxlength = "10" alt="Reference" name="ioForm"/>
										  <IMG src="images/CalendarIcon.gif" width="20" onClick="showCalendar('ioForm.dateOfTheDocument')" align="center">
								  </TD>
								</TR>


								<TR>
									<TD align="left" valign="top" class="ColumnBackground">
										&nbsp;<bean:message key="remarks" />
									</TD>
									<TD align="left" valign="top" class="TableData" colspan="3"> 
										<DIV align="left">
											<html:textarea property="remarks" cols="43" rows="2"  name="ioForm"/>
										</DIV>
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
								
								<A href="javascript:submitForm('searchResult.do?method=searchResult')">
									<IMG src="images/Search.gif" alt="Search" width="49" height="37" border="0"></A>
								<A href="home.do?method=getMainMenu&menuIcon=<%=session.getAttribute("menuIcon")%>&mainMenu=<%=session.getAttribute("mainMenu")%>">
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

