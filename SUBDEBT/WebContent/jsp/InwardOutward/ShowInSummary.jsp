<%@ page language="java"%>
<%@ page import="java.util.ArrayList"%>
<%@ taglib uri="/WEB-INF/TLD/struts-html.tld" prefix="html" %>
<%@ taglib uri="/WEB-INF/TLD/struts-bean.tld" prefix="bean" %>
<%@ taglib uri="/WEB-INF/TLD/struts-logic.tld" prefix="logic" %>
<%@ include file="/jsp/SetMenuInfo.jsp" %>
<% session.setAttribute("CurrentPage","showInwardSummary.do?method=showInwardSummary");%>


<TABLE width="725" border="0" cellpadding="0" cellspacing="0">
	<html:errors />
	<html:form action="showInwardSummary.do?method=showInwardSummary" method="POST" enctype="multipart/form-data">
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
									<TD colspan="8"> 
										<TABLE width="100%" border="0" cellpadding="0" cellspacing="0">
											<TR>
												<TD width="20%" class="Heading"><bean:message key="inwardSummaryHeader" /></TD>
												<TD><IMG src="images/TriangleSubhead.gif" width="19" height="19"></TD>
											</TR>
											<TR>
												<TD colspan="3" class="Heading"><IMG src="images/Clear.gif" width="5" height="5"></TD>
											</TR>

										</TABLE>
									</TD>

									<TR>
									<TD width="15%" align="left" valign="top" class="ColumnBackground">
										<bean:message key="inwardId" />
									</TD>
									<TD width="10%" align="left" valign="top" class="ColumnBackground">
										<bean:message key="documentType" />
									</TD>
									<TD width="10%" align="left" valign="top" class="ColumnBackground">
										<bean:message key="processedBy" />
									</TD>
									<TD width="10%" align="left" valign="top" class="ColumnBackground">
										<bean:message key="outwardIds"/>
									</TD>
									</TR>	



									<TR>
									<logic:iterate id="object" name="ioForm" property="inwardSummary">
									<%
									com.cgtsi.inwardoutward.InwardSummary inwardSummary =(com.cgtsi.inwardoutward.InwardSummary)object;

									ArrayList outwardIds=inwardSummary.getMappedOutwardID();
									int size=outwardIds.size();
										if (size==0)
										{
										%>
										
											<TR>
											<TD width="15%" align="left" valign="top" class="ColumnBackground1" rowspan="<%= size%>">
												<%String inwardId = inwardSummary.getInwardId();
												String url="documentDetailsOutward1.do?method=documentDetailsOutward1&inwardID="+inwardId;%>
												<html:link href="<%=url%>"><%= inwardId%></html:link>

											</TD>
											<TD width="10%" align="left" valign="top" class="ColumnBackground1"  rowspan="<%= size%>">
											<%= inwardSummary.getDocumentType()%>
											</TD>
											<TD width="10%" align="left" valign="top" class="ColumnBackground1"  rowspan="<%= size%>">
											<%= inwardSummary.getProcessedBy()%>
											</TD>
											<TD width="10%" align="left" valign="top" class="ColumnBackground1">						
											</TD>
											</TR>
										<%
										}
									for(int i=0;i<size;i++)
									{
										String outwardId=(String)outwardIds.get(i);
										String outward = outwardId;
										if (i==0)
										{
										%>
										</TR>
								
											<TR>
											<TD width="15%" align="left" valign="top" class="ColumnBackground1" rowspan="<%= size%>">
												<%String inwardId = inwardSummary.getInwardId();
												String url="documentDetailsOutward1.do?method=documentDetailsOutward1&inwardID="+inwardId;%>
												<html:link href="<%=url%>"><%= inwardId%></html:link>

											</TD>
											<TD width="10%" align="left" valign="top" class="ColumnBackground1"  rowspan="<%= size%>">
											<%= inwardSummary.getDocumentType()%>
											</TD>
											<TD width="10%" align="left" valign="top" class="ColumnBackground1"  rowspan="<%= size%>">
											<%= inwardSummary.getProcessedBy()%>
											</TD>
											<TD width="10%" align="left" valign="top" class="ColumnBackground1">						
												<%String url1="documentDetailsInward.do?method=documentDetailsInward&outwardID="+outwardId;%>
												<html:link href="<%=url1%>"><%= outwardId%></html:link>


											</TD>
											</TR>
										<%
										}
											else
										{
												%>
											<TR>
													<TD width="10%" align="left" valign="top" class="ColumnBackground1">
													<%String url2="documentDetailsInward.do?method=documentDetailsInward&outwardID="+outwardId;%>
													<html:link href="<%=url2%>"><%= outwardId%>
													</html:link>
											</TD>
													</TR>
													<%
										}
									}

									%>
									</logic:iterate>
									</TABLE>
						</TD>
					</TR>
					<TR >
						<TD height="20" >
							&nbsp;
						</TD>
					</TR>
					<TR >
						<TD align="center" valign="baseline">
						<A href="subHome.do?method=getSubMenu&menuIcon=<%=session.getAttribute("menuIcon")%>&mainMenu=<%=session.getAttribute("mainMenu")%>">
									<IMG src="images/OK.gif" alt="OK" width="49" height="37" border="0"></A>
							<DIV align="center">
								
								
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

