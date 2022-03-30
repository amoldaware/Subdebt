<%@ page language="java"%>
<%@ taglib uri="/WEB-INF/TLD/struts-html.tld" prefix="html" %>
<%@ taglib uri="/WEB-INF/TLD/struts-bean.tld" prefix="bean" %>
<%@ taglib uri="/WEB-INF/TLD/struts-logic.tld" prefix="logic" %>
<%@page import ="java.text.SimpleDateFormat"%>
<%SimpleDateFormat dateFormat=new SimpleDateFormat("dd/MM/yyyy");%>

<%@ include file="/jsp/SetMenuInfo.jsp" %>
<% session.setAttribute("CurrentPage","documentDetailsSearch.do?method=documentDetailsSearch");%>


<TABLE width="725" border="0" cellpadding="0" cellspacing="0">
	<html:errors />
	<html:form action="documentDetailsSearch.do?method=documentDetailsSearch" method="POST" enctype="multipart/form-data">
		<TR> 
			<TD width="20" align="right" valign="bottom"><IMG src="images/TableLeftTop.gif" width="20" height="31"></TD>
			<TD background="images/TableBackground1.gif"><IMG src="images/InwardOutwardHeading.gif" width="121" height="25"></TD>
			<TD width="20" align="left" valign="bottom"><IMG src="images/TableRightTop.gif" width="23" height="31"></TD>
		</TR>
		<TR>
			<TD width="20" background="images/TableVerticalLeftBG.gif">&nbsp;</TD>
			<TD>
				<TABLE width="100%" border="0" align="left" cellpadding="0" cellspacing="0">
					<TR>
						<TD>
							<TABLE width="100%" border="0" cellspacing="1" cellpadding="1">
								<TR>
									<TD colspan="4"> 
										<TABLE width="100%" border="0" cellpadding="0" cellspacing="0">
											<TR>
												<TD width="31%" class="Heading"><bean:message key="documentDetailsHeader" /></TD>
												<TD><IMG src="images/TriangleSubhead.gif" width="19" height="19"></TD>
											</TR>
											<TR>
												<TD colspan="3" class="Heading"><IMG src="images/Clear.gif" width="5" height="5"></TD>
											</TR>

										</TABLE>
									</TD>
								</TR>

							
								<TR align="left" valign="top">
									<TD align="left" valign="top" class="ColumnBackground">
										&nbsp;<bean:message key="fileNumber"/>
									</TD>
									<TD align="left" class="TableData"> 

											<bean:write property="search.fileNumber" name="ioForm"/>
									</TD>
									<TD align="left" valign="top" class="ColumnBackground">
										&nbsp;<bean:message key="fileTitle"/>
									</TD>
									<TD align="left" class="TableData"> 
								
										<bean:write property="search.documentTitle" name="ioForm"/>
									</TD>
								</TR>

					
								<TR>
									<TD align="left" valign="top" class="ColumnBackground">
										&nbsp;<bean:message key="subject" />
									</TD>
									<TD align="left" valign="top" class="TableData" colspan="3"> 
									<bean:write property="search.subject" name="ioForm"/>
									</TD>
								</TR>

								<TR>
									<TD align="left" valign="top" class="ColumnBackground">
										&nbsp;<bean:message key="category"  />
									</TD>
									<TD align="left" valign="top" class="TableData"> 
								
										<bean:write property="search.category"name="ioForm"/>
									</TD>
									<TD align="left" valign="top" class="ColumnBackground">
										&nbsp;<bean:message key="referenceId"/>
									</TD>
									<TD align="left" valign="top" class="TableData"> 
								
										<bean:write property="search.inResponseToID" name="ioForm"/>
									</TD>
								</TR>


								<TR>
									<TD align="left" valign="top" class="ColumnBackground">
										&nbsp;<bean:message key="documentDate" />
									</TD>
									<TD align="left" valign="top" class="TableData" colspan="3"> 
									<!-- To fix bug 07092004-01 -->									
									<bean:define id="date" name="ioForm" property="search" type="com.cgtsi.knowledge.Document"/>       
								  <%String date1 = null; 
								  java.util.Date newApplicationDate = date.getDateOfDocument();
								  if(newApplicationDate != null)           
								  {
									   date1 = dateFormat.format(newApplicationDate);
								  }
								  else
								  {
									   date1 = ""; 
								  }
								  %>
								  <%=date1%>
								  <!-- Fix is completed. -->

									</TD>
								</TR>

								<TR>
									<TD align="left" valign="top" class="ColumnBackground">
										&nbsp;<bean:message key="file" />
									</TD>
									<TD align="left" valign="top" class="TableData" colspan="3"> 
									<bean:define id="documentDescription" name="ioForm" property="search"  type="com.cgtsi.knowledge.Document"/>
									<%
									String path = documentDescription.getDocumentDescription();
									if(path != null)
									{
									int index1 = path.lastIndexOf(".");
									int index2 = path.lastIndexOf("/");
									int i = index2 + 1;
									String fileName = path.substring(i,index1);
									String filePath = (String)session.getAttribute("file");
									%>
									<html:link href="<%= filePath%>" ><%=fileName%>	
									</html:link>
									<%
									}
									else
									{
										%>
									<TD>
									</TD>
									<%
									}
									%>

									</TD>
								</TR>
								<TR>
									<TD align="left" valign="top" class="ColumnBackground">
										&nbsp;<bean:message key="remarks" />
									</TD>
									<TD align="left" valign="top" class="TableData" colspan="3"> 
										<bean:write property="search.remarks" name="ioForm"/>
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
					<TR>
						<TD align="center" valign="baseline" >
							<DIV align="center">
							<A href="searchDocument.do?method=searchDocument">
									<IMG src="images/OK.gif" alt="OK" width="49" height="37" border="0"></A>
								<A href="javascript:history.back()">
									<IMG src="images/Back.gif" alt="Back" width="49" height="37" border="0"></A>
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

