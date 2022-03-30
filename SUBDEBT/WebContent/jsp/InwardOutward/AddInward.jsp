<%@ page language="java"%>
<%@ taglib uri="/WEB-INF/TLD/struts-html.tld" prefix="html" %>
<%@ taglib uri="/WEB-INF/TLD/struts-bean.tld" prefix="bean" %>
<%@ include file="/jsp/SetMenuInfo.jsp" %>
<% session.setAttribute("CurrentPage","showAddInward.do?method=showAddInward");%>


<%
String focusField="sourceType";
org.apache.struts.action.ActionErrors errors = (org.apache.struts.action.ActionErrors)request.getAttribute(org.apache.struts.Globals.ERROR_KEY);
if (errors!=null && !errors.isEmpty())
{
	focusField="test";
}
%>

	<html:form action="addInward.do?method=addInward" method="POST" enctype="multipart/form-data" focus="<%=focusField%>">
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
			<A HREF="inwardHelp.do?method=inwardHelp">
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
												<TD width="31%" class="Heading"><bean:message key="inwardHeader" /></TD>
												<TD><IMG src="images/TriangleSubhead.gif" width="19" height="19"></TD>
											</TR>
											<TR>
												<TD colspan="3" class="Heading"><IMG src="images/Clear.gif" width="5" height="5"></TD>
											</TR>

										</TABLE>
									</TD>

								</TR>
								<TR>
									<TD colspan="4" class="SubHeading">
										<DIV align="left">
											<BR>
											<bean:message key="sourceDetails" />
										</DIV>
									</TD>
								</TR>
								<TR align="left" valign="top">
									<TD width="20%" valign="top" class="ColumnBackground"> 
										<DIV align="left"><font color="#FF0000" size="2">*</font>
											&nbsp;<bean:message key="sourceType" />
										</DIV>
									</TD>
									<TD align="left" valign="top" class="TableData">
									
										<html:select property="sourceType" onchange="setSourceId(this)"  name="ioForm">
											<html:option value="">Select </html:option>
											<html:option value="MLI">MLI </html:option>
											<html:option value="GOI">GOI </html:option>
											<html:option value="Vendor">Vendor </html:option>
											<html:option value="Supplier">Supplier </html:option>
											<html:option value="SEBI">SEBI </html:option>
										</html:select>
									</TD>
									<TD align="left" valign="top" class="ColumnBackground"><font color="#FF0000" size="2">*</font>
										&nbsp;<bean:message key="sourceId" />
									</TD>
									<TD align="left" class="TableData"> 
										<html:text property="sourceId" size="20" readonly="true" alt="Source ID" name="ioForm"/>
									</TD>
								</TR>

								<TR align="left" valign="top">
									<TD align="left" valign="top" class="ColumnBackground"> 
										<font color="#FF0000" size="2">*</font>
											&nbsp;<bean:message key="sourceName" />
										
									</TD>
									<TD align="left" class="TableData"> 
										<html:text property="sourceName" size="20"  alt="Source Name"  maxlength = "100" name="ioForm"/>
										&nbsp;
									</TD>
									<TD align="left" valign="top" class="ColumnBackground"><font color="#FF0000" size="2">*</font>
										&nbsp;<bean:message key="sourceRef" />
									</TD>
									<TD align="left" valign="top" class="TableData"> 
										<html:text property="sourceRef" size="20"  alt="Reference"  maxlength = "50" name="ioForm"/>
									</TD>
								</TR>

									<TR>
									<TD colspan="4" class="SubHeading">
										<DIV align="left">
											<BR>
											<bean:message key="documentDetails" />
										</DIV>
									</TD>
								</TR>

								<TR>
									<TD width="20%" align="left" valign="top" class="ColumnBackground"><font color="#FF0000" size="2">*</font>
										
											&nbsp;<bean:message key="documentType" />
										
									</TD>
									<TD width="27%" align="left" valign="top" class="TableData">
										<html:select  property="documentType"  name="ioForm">
											<html:option value="">Select</html:option>
											<html:options property="documentTypes" name="ioForm"/>
										</html:select>
									</TD>
									<TD width="17%" align="left" valign="top" class="ColumnBackground"><font color="#FF0000" size="2">*</font>
										&nbsp;<bean:message key="modeOfReceipt" />
									</TD>
									<TD width="36%" align="left" valign="top" class="TableData">
										<DIV align="left"> 
											<html:select property="modeOfReceipt"  name="ioForm">
											<html:option value="">Select </html:option>
											<html:option value="Letter">Letter </html:option>
											<html:option value="Courier">Courier</html:option>
											<html:option value="Post">Post </html:option>
											<html:option value="Speed Post ">Speed Post </html:option>
											<html:option value="Fax">Fax </html:option>
											<html:option value="Email">Email </html:option>
											<html:option value="Hand Delivery">Hand Delivery</html:option>
										</html:select>
										</DIV>
									</TD>
								</TR>

								<TR>
									<TD align="left" valign="top" class="ColumnBackground"><font color="#FF0000" size="2">*</font>
										  &nbsp;<bean:message key="documentDate" /> 
									</TD>
									  <TD align="left" valign="center" class="TableData">
										   <html:text property="dateOfDocument" size="20"  alt="Reference" name="ioForm" maxlength = "10" />
										  <IMG src="images/CalendarIcon.gif" width="20" onClick="showCalendar('ioForm.dateOfDocument')" align="center">
									  </TD>
									  <TD align="left" valign="top" class="ColumnBackground"><font color="#FF0000" size="2">*</font>
										  &nbsp;<bean:message key="language" />
									  </TD>
									  <TD align="left" valign="top" class="TableData"> 
										    <html:text property="language" size="20" maxlength = "30" alt="Language" name="ioForm"/>
									  </TD>
								</TR>

								<TR>
									<TD align="left" valign="top" class="ColumnBackground"><font color="#FF0000" size="2">*</font>
										&nbsp;<bean:message key="subject" />
									</TD>
									<TD colspan="3" align="left" valign="top" class="TableData">
										<html:text property="subject" size="70"  alt="Subject"  maxlength = "50" name="ioForm"/>
									</TD>
								</TR>

								<TR>
									<TD width="25%" align="left" valign="top" class="ColumnBackground">
										
											&nbsp;<bean:message key="file" />
										
									</TD>
									<TD width="75%" colspan="3" align="left" valign="top" class="TableData">
										<html:file property="filePathInward" name="ioForm" maxlength = "200"/>
										&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
										&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
										&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
										&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
										&nbsp;&nbsp;
									</TD>
								</TR>

								<TR>
									<TD align="left" valign="top" class="ColumnBackground">
										&nbsp;&nbsp;<bean:message key="outwardId" />
									</TD>
									<TD colspan="3" align="left" valign="top" class="TableData">
										<html:text property="mappedOutwardID" size="20"  name="ioForm"/>
										</TD>
								</TR>

								<TR>
									<TD align="left" valign="top" class="ColumnBackground">
										&nbsp;<bean:message key="remarks" />
									</TD>
									<TD align="left" valign="top" class="TableData" colspan="3"> 
										<DIV align="left">
											<html:textarea property="remarks" cols="70" rows="3"  name="ioForm"/>
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
							<A href="javascript:submitForm('addInward.do?method=addInward')">
									<IMG src="images/Save.gif" alt="Save" width="49" height="37" border="0"></A>
								<A href="javascript:document.ioForm.reset()">
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

