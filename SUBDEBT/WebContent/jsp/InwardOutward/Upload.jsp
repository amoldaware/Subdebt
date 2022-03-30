<%@ page language="java"%>
<%@ taglib uri="/WEB-INF/TLD/struts-html.tld" prefix="html" %>
<%@ taglib uri="/WEB-INF/TLD/struts-bean.tld" prefix="bean" %>
<%@ include file="/jsp/SetMenuInfo.jsp" %>
<% session.setAttribute("CurrentPage","uploadDocument.do?method=uploadDocument");%>

<%
String focusField="documentDescription";
org.apache.struts.action.ActionErrors errors = (org.apache.struts.action.ActionErrors)request.getAttribute(org.apache.struts.Globals.ERROR_KEY);
if (errors!=null && !errors.isEmpty())
{
	focusField="test";
}
%>


<html:form action="uploadResult.do?method=uploadResult" method="POST" enctype="multipart/form-data" focus="<%=focusField%>">
<html:hidden name="ioForm" property="test"/>
	<html:errors />
	

<TABLE width="725" border="0" cellpadding="0" cellspacing="0">
		<TR> 
			<TD width="20" align="right" valign="bottom"><IMG src="images/TableLeftTop.gif" width="20" height="31"></TD>
			<TD background="images/TableBackground1.gif"><IMG src="images/InwardOutwardHeading.gif" width="121" height="25"></TD>
			<TD width="20" align="left" valign="bottom"><IMG src="images/TableRightTop.gif" width="23" height="31"></TD>
		</TR>
		<TR>
			<TD width="20" background="images/TableVerticalLeftBG.gif">&nbsp;</TD>
			<TD>
			 <DIV align="right">			
			<A HREF="uploadHelp.do?method=uploadHelp">
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
									<TD colspan="4"> 
										<TABLE width="100%" border="0" cellpadding="0" cellspacing="0">
											<TR>
												<TD width="31%" class="Heading"><bean:message key="uploadHeader" /></TD>
												<TD><IMG src="images/TriangleSubhead.gif" width="19" height="19"></TD>
											</TR>
											<TR>
												<TD colspan="3" class="Heading"><IMG src="images/Clear.gif" width="5" height="5"></TD>
											</TR>

										</TABLE>
									</TD>

									<TR>
									<TD width="20%" align="left" valign="top" class="ColumnBackground">
										<font color="#FF0000" size="2">*</font>
											&nbsp;<bean:message key="documentType"/>
										
									</TD>
									<TD width="27%" align="left" valign="top" class="TableData">
										<html:select property="documentDescription"  alt = "Document Type"  name="ioForm">
											<html:option value="">Select </html:option>
											<html:options property="documentTypes" name="ioForm"/>
										</html:select>
									</TD>
								</TR>

								<TR>
									<TD width="25%" align="left" valign="top" class="ColumnBackground">
										<font color="#FF0000" size="2">*</font>
											&nbsp;<bean:message key="file" />
										
									</TD>
									<TD width="75%" colspan="3" align="left" valign="top" class="TableData">
										
										<html:file property="filePath" maxlength = "200" name="ioForm"/>
										&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
										&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
										&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
										&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
										&nbsp;&nbsp;
									</TD>
								</TR>

									<TR>
									<TD align="left" valign="top" class="ColumnBackground"><font color="#FF0000" size="2">*</font>
										&nbsp;<bean:message key="fileNumber" />
									</TD>
									<TD colspan="3" align="left" valign="top" class="TableData">
										
										<html:text property="fileNumber" size="20"  alt="File Number" maxlength="20"  name="ioForm"/>
									</TD>
								</TR>	

								<TR>
									<TD align="left" valign="top" class="ColumnBackground"><font color="#FF0000" size="2">*</font>
										&nbsp;<bean:message key="fileTitle" />
									</TD>
									<TD colspan="3" align="left" valign="top" class="TableData">
										
										<html:text property="documentTitle" size="20"  alt="File Title" maxlength="50" name="ioForm"/>
									</TD>
								</TR>

								<TR>
									<TD align="left" valign="top" class="ColumnBackground"><font color="#FF0000" size="2">*</font>
										&nbsp;<bean:message key="subject" />
									</TD>
									<TD colspan="3" align="left" valign="top" class="TableData">
										
										<html:text property="subject" size="20"  alt="Subject" maxlength="50" name="ioForm"/>
									</TD>
								</TR>
							
								
							<TR>
									<TD width="20%" align="left" valign="top" class="ColumnBackground">
										<font color="#FF0000" size="2">*</font>
											&nbsp;<bean:message key="category" />
										
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
										&nbsp;&nbsp;&nbsp;<bean:message key="referenceId" />
									</TD>
									<TD colspan="3" align="left" valign="top" class="TableData">
										<html:text property="inResponseToID" size="20"  alt="inResponseToID" maxlength="16"  name="ioForm"/>
									</TD>
								</TR>

								<TR>
									<TD align="left" valign="top" class="ColumnBackground"><font color="#FF0000" size="2">*</font>
										  &nbsp;<bean:message key="documentDate" /> 
									</TD>
									  <TD align="left" valign="center" class="TableData">
										 
										  <html:text property="dateOfDocument" size="20"  maxlength = "10" alt="Reference" name="ioForm"/>
										  <IMG src="images/CalendarIcon.gif" width="20" onClick="showCalendar('ioForm.dateOfDocument')" align="center">
								  </TD>
								</TR>

								<TR>
									<TD align="left" valign="top" class="ColumnBackground">
										&nbsp;&nbsp;&nbsp;<bean:message key="remarks" />
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
							<A href="javascript:submitForm('uploadResult.do?method=uploadResult')">
								<IMG src="images/Save.gif" alt="Save" width="49" height="37" border="0"></A>
								<A href="javascript:document.ioForm.reset()">
								<IMG src="images/Reset.gif" alt="Cancel" width="49" height="37" border="0"></A>
								
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

